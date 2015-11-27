package eu.inn.servicecontrol

import eu.inn.servicecontrol.api.{Console, ServiceController, ShutdownMonitor}
import org.mockito.Mockito._
import org.scalatest._
import org.scalatest.mock.MockitoSugar.mock
import scaldi.{Injectable, Module}

trait ServiceMock {
  def started()
  def stopped()
  def customCommand()
}

class MyServiceMock(console: Console, mock: ServiceMock) extends api.Service{
  mock.started()
  def stopService(controlBreak: Boolean): Unit = {
    mock.stopped()
  }
  def customCommand(): Unit = {
    mock.customCommand()
  }
}

class ConsoleMock(commandSeq: Seq[String]) extends Console {
  def inputIterator(): Iterator[Option[String]] = commandSeq.map(Some(_)).toIterator
  def write(o: Any): Unit = {}
  def writeln(o: Any): Unit = {}
  def writeln(): Unit = {}
  def accept: Unit = {}
}

class CustomServiceController(console: Console, service: MyServiceMock, shutdownMonitor: ShutdownMonitor)
  extends ConsoleServiceController(service, console, shutdownMonitor)  {

  override def customCommand = {
    case "custom" ⇒ service.customCommand()
  }
}

class TestModuleMock(commandSeq: Seq[String], mock: ServiceMock) extends Module {
  bind [ServiceMock] to mock
  bind [Console] to new ConsoleMock(commandSeq)
  bind [MyServiceMock] to injected [MyServiceMock]
  bind [ServiceController] to injected [CustomServiceController]
  bind [ShutdownMonitor] to injected [RuntimeShutdownMonitor]
}

class TestServiceComponent extends FlatSpec with Matchers {

  "Service component" should " start service on main" in {
    import Injectable._
    val m = mock[ServiceMock]
    implicit val injector = new TestModuleMock(Seq(), m)
    val launcher = inject[api.ServiceController]
    launcher.run()
    verify(m).started()
    verifyNoMoreInteractions(m)
  }

  "Service component" should " start on main and stop on quit command" in {
    import Injectable._
    val m = mock[ServiceMock]
    implicit val injector = new TestModuleMock(Seq("quit"), m)
    val launcher = inject[api.ServiceController]
    launcher.run()
    verify(m).started()
    verify(m).stopped()
    verifyNoMoreInteractions(m)
  }

  "Service component" should " start service and execute custom command" in {
    import Injectable._
    val m = mock[ServiceMock]
    implicit val injector = new TestModuleMock(Seq("custom"), m)
    val launcher = inject[api.ServiceController]
    launcher.run()
    verify(m).started()
    verify(m).customCommand()
    verifyNoMoreInteractions(m)
  }
}
