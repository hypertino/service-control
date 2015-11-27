# service-conrtol

**service-control** is a bootstrap helper library for Scala services. It provides a basic DI, entry point and console control.

A plain example how-to use a library:
```
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
```


## License

Product licensed under BSD 3-clause as stated in file LICENSE
