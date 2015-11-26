package eu.inn.servicecontrol.api

trait ServiceControl {
  def startService(): Unit
  def stopService(controlBreak: Boolean): Unit
}

trait ServiceControlComponent {
  def serviceControl: ServiceControl
}

