package com.hypertino.service.control

import com.hypertino.service.control.api.{Console, Service, ServiceController}

class MyService(console: Console) extends api.Service {
  console.writeln("MyService started!")

  def stopService(controlBreak: Boolean): Unit = {
    console.writeln("MyService stopped.")
  }
}

object TestMain extends ConsoleModule {
  bind [Service] to injected [MyService]

  def main(args: Array[String]): Unit = {
    inject[ServiceController].run()
  }
}

