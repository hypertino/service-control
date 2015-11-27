package eu.inn.servicecontrol.api

trait ShutdownMonitor {
  def registerHandler(handler: () ⇒ Unit): Unit
  def unregisterHandler(handler: () ⇒ Unit): Unit
}
