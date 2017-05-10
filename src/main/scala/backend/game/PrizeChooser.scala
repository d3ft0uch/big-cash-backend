package backend.game

import backend.dao.{GameInfo, GameTry, GameDAO}
import backend.utils.{RandomUtils, ServiceConfig}
import org.json4s.Formats

/**
 * Created by d3ft0uch.
 */
object PrizeChooser {
  implicit def formats: Formats = org.json4s.DefaultFormats

  def prizes = {
    org.json4s.jackson.JsonMethods.parse(ServiceConfig.getPrizes).extract[Map[String, Int]] map {
      case (amount, count) => List.fill(count)(amount.toInt)
    } reduce (_ ++ _)
  }

  def newPrize = RandomUtils.getRandomSublist(prizes, 1, 3)

  def getPrize(uuid: String, action: String) = {
    action.toLowerCase match {
      case "continue" =>
        GameDAO.read(Map("_id" -> uuid)) match {
          case Some(x) => if (!x.fin && x.results.size < 4) {
            val prize = newPrize
            val isFin = x.results.size == 3
            GameDAO.update(x.copy(results = x.results ++ List(GameTry(System.currentTimeMillis(), prize)), fin = isFin))
            Some(Result(isFin, prize))
          }
          else {
            GameDAO.update(x.copy(fin = true))
            Some(Result(fin = true, x.results.sortBy(_.t).last.prizes))
          }
          case None =>
            val prize = newPrize
            GameDAO.create(GameInfo(uuid, false, List(GameTry(System.currentTimeMillis(), prize))))
            Some(Result(fin = false, prize))
        }
      case "stop" =>
        val gameInfo = GameDAO.read(Map("_id" -> uuid)).getOrElse(throw new RuntimeException("Unexpected behaviour"))
        GameDAO.update(gameInfo.copy(fin = true))
        None
    }

  }
}

case class Result(fin: Boolean, prize: List[Int])
