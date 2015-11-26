package eu.inn.servicecontrol.api

trait ConsoleControl {
  def serviceEntry()
}

trait ConsoleControllerComponent {
  this: ConsoleIOComponent
    with ServiceControlComponent  ⇒

  def consoleControl: ConsoleControl
}
