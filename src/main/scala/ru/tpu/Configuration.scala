package ru.tpu

import com.typesafe.config.ConfigFactory

trait Configuration {
  private val config = ConfigFactory.load()
  println(config.getConfig("server"))
  def getServer: Configuration.Server = {
    val server =  config.getConfig("server")
    Configuration.Server(
      host = server.getString("host"),
      port = server.getInt("port")
    )
  }
}

object Configuration {
  case class Server(host: String, port: Int)
}