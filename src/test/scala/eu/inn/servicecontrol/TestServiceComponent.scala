package eu.inn.servicecontrol

import org.scalatest._

class ComponentRepositoryMock(commandSeq: Seq[String]) extends ControlledServiceComponent
  with api.ConsoleComponent {
  var started = false
  var stopped = false
  val service = new ControlledService {
    override def startService(): Unit = {
      started = true
    }
    override def stopService(controlBreak: Boolean): Unit = {
      stopped = true
    }
  }

  val console: api.Console = new api.Console {

    override def inputIterator(): Iterator[Option[String]] = commandSeq.map(Some(_)).toIterator
    override def write(o: Any): Unit = {}
    override def writeln(o: Any): Unit = {}
    override def writeln(): Unit = {}
  }
}

class TestServiceComponent extends FlatSpec with Matchers {

  "Service component" should " start service on main" in {
    val r = new ComponentRepositoryMock(Seq())
    r.service.mainEntryPoint()
    r.started should equal(true)
    r.stopped should equal(false)
  }

  "Service component" should " start on main and stop on quit command" in {
    val r = new ComponentRepositoryMock(Seq("quit"))
    r.service.mainEntryPoint()
    r.started should equal(true)
    r.stopped should equal(true)
  }
}
