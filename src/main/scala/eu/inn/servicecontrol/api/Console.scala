package eu.inn.servicecontrol.api

trait Console {
  def inputIterator(): Iterator[Option[String]]
  def write(o: Any): Unit
  def writeln(o: Any): Unit
  def writeln(): Unit
}
