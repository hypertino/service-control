package eu.inn.servicecontrol

import scala.util.control.Breaks._

trait ControlledServiceComponent extends api.ServiceComponent{
  this:api.ConsoleComponent  ⇒

  trait ControlledService extends api.Service {
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
        for (cmd ← console.inputIterator())
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
      console.writeln("Available commands are: quit")
    }

    def unknownCommand(command: String): Unit = {
      console.writeln(s"Unknown command: $command")
      help()
    }
  }
}
