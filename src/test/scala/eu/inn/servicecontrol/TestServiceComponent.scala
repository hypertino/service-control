package eu.inn.servicecontrol

import org.scalatest._

class ComponentRepository(commandSeq: Seq[String]) extends ServiceComponent
  with api.ConsoleIOComponent {
  var started = false
  var stopped = false
  val service = new Service {
    override def startService(): Unit = {
      started = true
    }
    override def stopService(controlBreak: Boolean): Unit = {
      stopped = true
    }
  }

  val consoleIO: api.ConsoleIO = new api.ConsoleIO {

    override def inputIterator(): Iterator[Option[String]] = commandSeq.map(Some(_)).toIterator
    override def write(o: Any): Unit = {}
    override def writeln(o: Any): Unit = {}
    override def writeln(): Unit = {}
  }
}

class TestServiceComponent extends FlatSpec with Matchers {

  "Service component" should " start service on main" in {
    val r = new ComponentRepository(Seq())
    r.service.mainEntryPoint()
    r.started should equal(true)
    r.stopped should equal(false)
  }

  "Service component" should " start on main and stop on quit command" in {
    val r = new ComponentRepository(Seq("quit"))
    r.service.mainEntryPoint()
    r.started should equal(true)
    r.stopped should equal(true)
  }
}
