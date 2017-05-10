package backend.dao

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.MongoCollection
import org.json4s.Formats
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization._

/**
 * Created by d3ft0uch.
 */

abstract class CRUD[T <: AnyRef : Manifest] {

  type MSA = Map[String, Any]

  implicit def formats: Formats = org.json4s.DefaultFormats

  implicit def todbObj(obj: T): DBObject = com.mongodb.util.JSON.parse(serialize(obj)).asInstanceOf[DBObject]

  private def serialize(o: AnyRef) = write(o)

  private def toCC(obj: DBObject) = parse(obj.toString).extract[T]

  def col: MongoCollection

  def create(obj: T) = col.save(obj)

  def read(q: MSA) = col.findOne(q).map(obj => toCC(obj))

  def list = col.find().toList.map(toCC)

  def update = create _

  def delete(q: MSA) = col.remove(q)
}
