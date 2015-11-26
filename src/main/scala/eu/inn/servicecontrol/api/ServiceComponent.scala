package eu.inn.servicecontrol.api

trait Service {
  def mainEntryPoint(): Unit
  def startService(): Unit
  def stopService(controlBreak: Boolean): Unit
}

trait ServiceComponent {
  def service: Service
}
