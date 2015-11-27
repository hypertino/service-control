package eu.inn.servicecontrol.api

trait Console {
  def inputIterator(): Iterator[Option[String]]
  def write(o: Any)
  def writeln(o: Any)
  def writeln()
}
