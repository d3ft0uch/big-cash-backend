package backend.akka

import java.net.InetSocketAddress

import akka.actor.{Props, Actor}
import akka.event.Logging
import akka.io.{Tcp, IO}
import akka.io.Tcp._

/**
 * Created by d3ft0uch.
 */

object TCPActor {
  def props(address: String, port: Int) = Props(new TCPActor(address, port))
}

class TCPActor(address: String, port: Int) extends Actor {
  val log = Logging(context.system, this)

  override def preStart(): Unit = {
    log.info("Starting tcp net server")
    import context.system
    val opts = List(SO.KeepAlive(on = true), SO.TcpNoDelay(on = true))
    IO(Tcp) ! Bind(self, new InetSocketAddress(address, port), options = opts)
  }

  def receive = {
    case b@Bound(localAddress) => log.info(s"Bounded on port ${localAddress.getPort}")
    case CommandFailed(_: Bind) => context stop self
    case c@Connected(remote, local) =>
      log.info("New incoming tcp connection on server")
      val connection = sender()
      val handler = context.actorOf(Props[GameHandler])
      connection ! Register(handler)
  }
}
