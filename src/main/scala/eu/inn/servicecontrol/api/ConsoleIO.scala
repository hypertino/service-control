package eu.inn.servicecontrol.api

trait ConsoleIO {
  def inputIterator(): Iterator[Option[String]]
  def write(o: Any)
  def writeln(o: Any)
  def writeln()
}

trait ConsoleIOComponent {
  def consoleIO: ConsoleIO
}
