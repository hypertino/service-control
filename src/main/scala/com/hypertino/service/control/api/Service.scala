package com.hypertino.service.control.api

import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration

trait Service {
  def stopService(controlBreak: Boolean, timeout: FiniteDuration): Future[Unit]
}
