package backend.akka

import akka.actor.Actor
import akka.event.Logging
import akka.io.Tcp.{Received, PeerClosed, Write}
import akka.util.ByteString
import backend.game.PrizeChooser
import org.json4s.Formats
import org.json4s.jackson.Serialization._

/**
 * Created by d3ft0uch.
 */

class GameHandler extends Actor {
  val log = Logging(context.system, this)
  val uuid = java.util.UUID.randomUUID().toString

  implicit def formats: Formats = org.json4s.DefaultFormats

  def receive = {
    case Received(data) =>
      val action = data.utf8String
      PrizeChooser.getPrize(uuid, action).map {
        case msg => sender() ! Write(ByteString(write(msg)))
      }
    case PeerClosed => context stop self
  }
}