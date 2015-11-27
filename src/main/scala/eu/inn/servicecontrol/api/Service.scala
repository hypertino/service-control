package eu.inn.servicecontrol.api

trait Service {
  def stopService(controlBreak: Boolean): Unit
}
