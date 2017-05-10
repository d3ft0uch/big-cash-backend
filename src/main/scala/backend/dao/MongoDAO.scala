package backend.dao

import backend.utils.ServiceConfig
import com.mongodb.ServerAddress
import com.mongodb.casbah.MongoClient

/**
 * Created by d3ft0uch.
 */
trait MongoDAO {

  private val mongoAddresses = ServiceConfig.getMongoAddresses.map { tup =>
    val (host, port) = tup
    new ServerAddress(host, port)
  }

  protected val mongoClient = MongoClient(mongoAddresses)
  protected val db = mongoClient(ServiceConfig.getMongoDBName)

}

