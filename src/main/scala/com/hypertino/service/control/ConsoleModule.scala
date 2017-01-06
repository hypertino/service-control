package com.hypertino.service.control

import com.hypertino.service.control.api.{Console, ServiceController, ShutdownMonitor}
import scaldi.Module

class ConsoleModule extends Module {
  bind [Console] to new StdConsole
  bind [ServiceController] to injected [ConsoleServiceController]
  bind [ShutdownMonitor] to injected [RuntimeShutdownMonitor]
}
