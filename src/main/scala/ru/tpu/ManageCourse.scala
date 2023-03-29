package ru.tpu

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import ru.tpu.controllers.TestController
import scala.concurrent.ExecutionContext

object ManageCourse extends App with Configuration {

  implicit val system : ActorSystem = ActorSystem("Web")
  implicit val context : ExecutionContext = ExecutionContext.global
  val controllers = new TestController

  Http().newServerAt(getServer.host, getServer.port).bind(controllers.routes)
}
