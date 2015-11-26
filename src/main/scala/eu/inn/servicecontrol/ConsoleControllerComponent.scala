package eu.inn.servicecontrol

import scala.util.control.Breaks._

trait ConsoleControllerComponent extends api.ConsoleControllerComponent{
  this:api.ConsoleIOComponent
    with api.ServiceControlComponent  ⇒

  val consoleControl = new ConsoleControl

  class ConsoleControl extends api.ConsoleControl {
    @volatile var isStopping: Boolean = false

    def serviceEntry() = {
      Runtime.getRuntime.addShutdownHook(new Thread() {
        override def run() {
          if (!isStopping) {
            isStopping = true
            serviceControl.stopService(controlBreak = true)
          }
        }
      })

      serviceControl.startService()

      breakable {
        for (cmd ← consoleIO.inputIterator())
          executeCommand(cmd)
      }
    }

    def executeCommand: PartialFunction[Any, Unit] = {
      case Some("quit") ⇒
        if (!isStopping) {
          isStopping = true
          serviceControl.stopService(controlBreak = false)
        }
        break()

      case Some("") | Some("help") ⇒ help()
      case None ⇒ // unattended mode
      case Some(other: String) ⇒ unknownCommand(other)
    }

    def help(): Unit = {
      consoleIO.writeln("Available commands are: quit")
    }

    def unknownCommand(command: String): Unit = {
      consoleIO.writeln(s"Unknown command: $command")
      help()
    }
  }
}
