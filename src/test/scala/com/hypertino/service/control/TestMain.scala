package com.hypertino.service.control

import com.hypertino.service.control.api.{Console, Service, ServiceController}

import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration

class MyService(console: Console) extends api.Service {
  console.writeln("MyService started!")

  def stopService(controlBreak: Boolean, timeout: FiniteDuration): Future[Unit] = {
    Future.successful(console.writeln("MyService stopped."))
  }
}

object TestMain extends ConsoleModule {
  bind [Service] to injected [MyService]

  def main(args: Array[String]): Unit = {
    inject[ServiceController].run()
  }
}

