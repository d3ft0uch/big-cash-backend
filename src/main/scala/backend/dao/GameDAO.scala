package backend.dao

/**
 * Created by d3ft0uch.
 */
object GameDAO extends CRUD[GameInfo] with MongoDAO {
  def col = db("games")
}

case class GameInfo(_id: String, fin: Boolean, results: List[GameTry])

case class GameTry(t: Long, prizes: List[Int])
