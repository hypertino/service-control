# service-control

[![Build Status](https://travis-ci.org/InnovaCo/service-control.svg?branch=master)](https://travis-ci.org/InnovaCo/service-control)

[ Latest Releases ](https://oss.sonatype.org/#nexus-search;gav~eu.inn~service-control_*~~~)

**service-control** is a bootstrap helper library for Scala services. It provides a basic DI, entry point and console control.

A plain example how-to use a library:
```
class MyService(console: Console) extends api.Service {
  console.writeln("Service is started")

  def stopService(controlBreak: Boolean): Unit = {
    console.writeln("Service is stopped")
  }
}

object TestMain extends ConsoleModule {
  bind [Service] to injected [MyService]

  def main(args: Array[String]): Unit = {
    inject[ServiceController].run()
  }
}
```


## License

Product licensed under BSD 3-clause as stated in file LICENSE
