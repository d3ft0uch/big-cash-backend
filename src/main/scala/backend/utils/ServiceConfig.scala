package backend.utils

import java.io.FileInputStream
import java.util.Properties


object ServiceConfig {

  private val p = new Properties

  p.load(new FileInputStream("service.properties"))

  def getMongoPort = getIntParam("mongo_port")

  def getPrizes = getStringParam("cash")

  def getServerHost = getStringParam("server_host")

  def getServerPort = getIntParam("server_port")

  def getMongoDBName = getStringParam("mongo_db_name")

  def getMongoAddresses = getStringParam("mongo_addresses").split(";").toList.map(x => (x, getMongoPort))

  private def getStringParam(paramName: String, defaultValue: String = ""): String = {
    val paramValue = p.getProperty(paramName)
    if (paramValue == null) defaultValue else paramValue
  }

  private def getIntParam(paramName: String, defaultValue: Int = 0): Int = {
    val paramValue = p.getProperty(paramName)
    if (paramValue == null) defaultValue else paramValue.toInt
  }


}
