package com.hypertino.service.control

import com.hypertino.service.control.api.{Console, ServiceController, ShutdownMonitor}
import org.scalamock.scalatest.MockFactory
import org.scalatest._
import scaldi.{Injectable, Injector, Module}

trait ServiceMock {
  def started()
  def stopped()
  def customCommand()
}

class MyServiceMock(console: Console, mock: ServiceMock) extends api.Service{
  def serviceName = "MyServiceMock"
  mock.started()
  def stopService(controlBreak: Boolean): Unit = {
    mock.stopped()
  }
  def customCommand(): Unit = {
    mock.customCommand()
  }
  def failingCommand(): Unit = {
    throw new RuntimeException("Command failed")
  }
}

class ConsoleMock(commandSeq: Seq[String]) extends Console {
  def inputIterator(): Iterator[Option[String]] = commandSeq.map(Some(_)).toIterator
  def write(o: Any): Unit = {}
  def writeln(o: Any): Unit = {}
  def writeln(): Unit = {}
}

class CustomServiceController(implicit injector: Injector)
  extends ConsoleServiceController  {
  private val service = inject[MyServiceMock]

  override def customCommand = {
    case "custom" ⇒ service.customCommand()
    case "fail" ⇒ service.failingCommand()
  }
}

class TestModuleMock(commandSeq: Seq[String], mock: ServiceMock) extends Module {
  bind [ServiceMock] to mock
  bind [Console] to new ConsoleMock(commandSeq)
  bind [MyServiceMock] to injected [MyServiceMock]
  bind [ServiceController] to injected [CustomServiceController]
  bind [ShutdownMonitor] to injected [RuntimeShutdownMonitor]
}

class TestServiceComponent extends FlatSpec with Matchers with MockFactory {

  "Service component" should " start on main, run custom command and stop on quit command" in {
    import Injectable._
    val m = stub[ServiceMock]
    implicit val injector = new TestModuleMock(Seq("custom", "quit"), m)
    val launcher = inject[api.ServiceController]
    launcher.run()

    inSequence {
      m.started _ verify()
      m.customCommand _ verify()
      m.stopped _ verify()
    }
  }

  "Service component" should " handle command exception" in {
    import Injectable._
    val m = stub[ServiceMock]
    implicit val injector = new TestModuleMock(Seq("fail", "quit"), m)
    val launcher = inject[api.ServiceController]
    launcher.run()
    inSequence {
      m.started _ verify()
      m.stopped _ verify()
    }
  }
}
