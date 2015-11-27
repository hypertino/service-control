package eu.inn.servicecontrol

import eu.inn.servicecontrol.api.{Service, Console}
import scaldi.Module

class MyService(console: Console) extends ControlledService(console) {
  def startService(): Unit = {
    console.writeln("Service is started")
  }
  def stopService(controlBreak: Boolean): Unit = {
    console.writeln("Service is stopped")
  }
}

class TestModule extends Module {
  bind [Console] to new StdConsole
  bind [Service] to injected [MyService]
}

object TestMain extends TestModule {
  def main(args: Array[String]): Unit = {
    val service = inject[Service]
    service.mainEntryPoint()
  }
}
