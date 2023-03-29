package ru.tpu

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route, RouteConcatenation}
import ru.tpu.controllers.{ResponseController, TestController}

import scala.concurrent.ExecutionContext

object ManageCourse extends App with Configuration {

  implicit val system : ActorSystem = ActorSystem("Web")
  implicit val context : ExecutionContext = ExecutionContext.global
  val controllers = Seq(new TestController, new ResponseController)

  Http().newServerAt(getServer.host, getServer.port).bind(Directives.withoutSizeLimit {
    val cors: Route = Directives.options(CORSHandler.corsHandler(Directives.complete(StatusCodes.OK)))
    val routes: Route = RouteConcatenation.concat(controllers.map(_.routes): _*)

    Directives.pathPrefix("api") {
      RouteConcatenation.concat(routes, cors)
    }
  })

}
