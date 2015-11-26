package eu.inn.servicecontrol

import org.scalatest._

class ComponentRepositary extends ConsoleControllerComponent
  with ConsoleIOComponent
  with api.ServiceControlComponent{
  override val serviceControl = new api.ServiceControl {
    override def startService(): Unit = {
      consoleIO.writeln("Starting...")
    }
    override def stopService(controlBreak: Boolean): Unit = {
      consoleIO.writeln(s"Stopping (controlBreak = $controlBreak)...")
    }
  }
}

class TestMain extends FlatSpec with Matchers {

  "A Main" should " do something" in {
    val r = new ComponentRepositary
    r.consoleControl.serviceEntry()
  }
}
