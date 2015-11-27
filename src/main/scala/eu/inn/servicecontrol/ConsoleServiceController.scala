package eu.inn.servicecontrol

import eu.inn.servicecontrol.api.{ServiceController, ShutdownMonitor, Service, Console}
import scala.util.control.Breaks._
import scala.util.control.NonFatal

class ConsoleServiceController(service: Service, console: Console, shutdownMonitor: ShutdownMonitor)
  extends api.ServiceController  {
  @volatile protected var isStopping: Boolean = false
  shutdownMonitor.registerHandler(onShutdown)

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
    if (!isStopping) {
      isStopping = true
      service.stopService(controlBreak = true)
    }
  }

  def executeCommand: PartialFunction[String,Unit] = customCommand orElse defaultCommand
  def customCommand: PartialFunction[String,Unit] = PartialFunction.empty

  def defaultCommand: PartialFunction[String, Unit] = {
    case "quit" ⇒
      if (!isStopping) {
        isStopping = true
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
    help()
  }

  def handleException(throwable: Throwable): Unit = {
    console.writeln(throwable.toString)
  }
}
