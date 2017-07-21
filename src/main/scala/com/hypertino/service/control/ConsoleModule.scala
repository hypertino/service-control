package com.hypertino.service.control

import com.hypertino.service.control.api.{Console, ServiceController, ShutdownMonitor}
import scaldi.Module

class ConsoleModule(serviceIdentifier: Option[String] = None) extends Module {
  bind [Console] to new StdConsole
  bind [ServiceController] to new ConsoleServiceController(serviceIdentifier)
  bind [ShutdownMonitor] to injected [RuntimeShutdownMonitor]
}
