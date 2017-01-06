package com.hypertino.service.control

import com.hypertino.service.control.api.{Console, Service, ShutdownMonitor}
import org.slf4j.{Logger, LoggerFactory}
import scaldi.{Injectable, Injector}

import scala.util.control.Breaks._
import scala.util.control.NonFatal

class ConsoleServiceController(implicit injector: Injector) extends Injectable with api.ServiceController  {
  protected val log: Logger = LoggerFactory.getLogger(getClass)
  private val shutdownMonitor = inject[ShutdownMonitor]
  protected val console: Console = inject[Console]

  @volatile protected var isStopping: Boolean = false
  shutdownMonitor.registerHandler(onShutdown)

  private val service: Service = try {
    inject[Service]
  }
  catch {
    case NonFatal(e) ⇒
      log.error(s"Service can't start!", e)
      throw e
  }
  def run() {
    breakable {
      for (cmd ← console.inputIterator())
        cmd.foreach { commandString ⇒
          try {
            executeCommand(commandString)
          }
          catch {
            case NonFatal(e) ⇒ handleException(e)
          }
        }
    }
  }

  protected def onShutdown(): Unit = {
    if (!isStopping && service != null) {
      isStopping = true
      log.info(s"Shutting down service...")
      service.stopService(controlBreak = true)
    }
  }

  def executeCommand: PartialFunction[String,Unit] = customCommand orElse defaultCommand
  def customCommand: PartialFunction[String,Unit] = PartialFunction.empty

  def defaultCommand: PartialFunction[String, Unit] = {
    case "quit" ⇒
      if (!isStopping) {
        isStopping = true
        log.info(s"Quiting service...")
        service.stopService(controlBreak = false)
      }
      break()

    case "" | "help" ⇒ help()
    case other: String ⇒ unknownCommand(other)
  }

  def help(): Unit = {
    console.writeln("Available commands are: quit")
  }

  def unknownCommand(command: String): Unit = {
    console.writeln(s"Unknown command: $command")
    log.warn(s"Unknown command: $command")
    help()
  }

  def handleException(throwable: Throwable): Unit = {
    console.writeln(throwable.toString)
    log.error(s"Service command failed with exception", throwable)
  }
}
