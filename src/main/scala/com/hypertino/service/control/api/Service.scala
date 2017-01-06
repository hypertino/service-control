package com.hypertino.service.control.api

trait Service {
  def stopService(controlBreak: Boolean): Unit
}
