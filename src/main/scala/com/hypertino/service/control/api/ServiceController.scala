package com.hypertino.service.control.api

import scala.concurrent.Future

trait ServiceController {
  def run(): Future[Boolean]
  def stop(controlBreak: Boolean): Unit
}
