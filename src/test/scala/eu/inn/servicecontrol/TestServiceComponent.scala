package eu.inn.servicecontrol

import eu.inn.servicecontrol.api.{Service, Console}
import org.scalatest._
import scaldi.Module

class MyServiceMock(console: Console) extends ControlledService(console) {
  var started = false
  var stopped = false
  def startService(): Unit = {
    started = true
  }
  def stopService(controlBreak: Boolean): Unit = {
    stopped = true
  }
}

class ConsoleMock(commandSeq: Seq[String]) extends Console {
  def inputIterator(): Iterator[Option[String]] = commandSeq.map(Some(_)).toIterator
  def write(o: Any): Unit = {}
  def writeln(o: Any): Unit = {}
  def writeln(): Unit = {}
}

class TestModuleMock(commandSeq: Seq[String]) extends Module {
  bind [Console] to new ConsoleMock(commandSeq)
  bind [Service] to injected [MyServiceMock]

  def service = inject[Service]
}

class TestServiceComponent extends FlatSpec with Matchers {

  "Service component" should " start service on main" in {
    val m = new TestModuleMock(Seq())
    m.service.mainEntryPoint()
    m.service.asInstanceOf[MyServiceMock].started should equal(true)
    m.service.asInstanceOf[MyServiceMock].stopped should equal(false)
  }

  "Service component" should " start on main and stop on quit command" in {
    val m = new TestModuleMock(Seq("quit"))
    m.service.mainEntryPoint()
    m.service.asInstanceOf[MyServiceMock].started should equal(true)
    m.service.asInstanceOf[MyServiceMock].stopped should equal(true)
  }
}
