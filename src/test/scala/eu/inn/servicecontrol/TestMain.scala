package eu.inn.servicecontrol

class ComponentRepository
  extends ControlledServiceComponent
  with StdConsoleComponent {

  val service = new ControlledService {
    def startService(): Unit = {
      console.writeln("Service is started")
    }
    def stopService(controlBreak: Boolean): Unit = {
      console.writeln("Service is stopped")
    }
  }
}

object TestMain extends ComponentRepository {
  def main(args: Array[String]): Unit = {
    service.mainEntryPoint()
  }
}
