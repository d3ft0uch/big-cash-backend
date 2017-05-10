package backend

import _root_.akka.actor.{Props, ActorSystem}
import backend.akka.TCPActor
import backend.utils.ServiceConfig

/**
 * Created by d3ft0uch.
 */
object Boot extends App {
  implicit val system = ActorSystem("AS")
  val actor = system.actorOf(TCPActor.props(ServiceConfig.getServerHost, ServiceConfig.getServerPort), name = "tcpActor")
}
