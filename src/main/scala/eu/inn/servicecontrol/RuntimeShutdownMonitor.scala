package eu.inn.servicecontrol

import eu.inn.servicecontrol.api.ShutdownMonitor

import scala.collection.concurrent.TrieMap

class RuntimeShutdownMonitor extends ShutdownMonitor {
  val handlers = new TrieMap[() ⇒ Unit,String]()

  Runtime.getRuntime.addShutdownHook(new Thread() {
    override def run() {
      handlers.keys.foreach(_())
    }
  })

  override def registerHandler(handler: () ⇒ Unit): Unit = handlers.put(handler, null)
  override def unregisterHandler(handler: () ⇒ Unit): Unit = handlers.remove(handler)
}
