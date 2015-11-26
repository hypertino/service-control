package eu.inn.servicecontrol

import scala.util.control.Breaks._

trait ServiceComponent extends api.ServiceComponent{
  this:api.ConsoleIOComponent  ⇒

  trait Service extends api.Service {
    @volatile var isStopping: Boolean = false

    override def mainEntryPoint() = {
      Runtime.getRuntime.addShutdownHook(new Thread() {
        override def run() {
          if (!isStopping) {
            isStopping = true
            stopService(controlBreak = true)
          }
        }
      })

      startService()

      breakable {
        for (cmd ← consoleIO.inputIterator())
          executeCommand(cmd)
      }
    }

    def executeCommand: PartialFunction[Any, Unit] = {
      case Some("quit") ⇒
        if (!isStopping) {
          isStopping = true
          stopService(controlBreak = false)
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
