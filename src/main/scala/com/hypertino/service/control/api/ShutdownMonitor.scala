package com.hypertino.service.control.api

trait ShutdownMonitor {
  def registerHandler(handler: () ⇒ Unit): Unit
  def unregisterHandler(handler: () ⇒ Unit): Unit
}
