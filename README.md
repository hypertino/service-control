# service-conrtol

**service-control** is a bootstrap helper library for Scala services. It provides a basic DI, entry point and console control.

A plain example how-to use a library:
```
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
```


## License

Product licensed under BSD 3-clause as stated in file LICENSE
