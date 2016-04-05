package eu.inn.servicecontrol

import eu.inn.servicecontrol.api.{Console, ServiceController, ShutdownMonitor}
import scaldi.Module

class ConsoleModule extends Module {
  bind [Console] to new StdConsole
  bind [ServiceController] to injected [ConsoleServiceController]
  bind [ShutdownMonitor] to injected [RuntimeShutdownMonitor]
}
