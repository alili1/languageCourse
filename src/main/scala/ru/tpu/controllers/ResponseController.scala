package ru.tpu.controllers

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.{Directives, Route}
import org.json4s.{DefaultFormats, Formats}

import scala.concurrent.ExecutionContext

class ResponseController (implicit system : ActorSystem, context: ExecutionContext)
  extends Controller {
  override val routes: Route = this.getResponses

  private implicit val formatJson: Formats = DefaultFormats

  def getResponses: Route = post {
    path("getResponse") {
      entity(as[String]) { request =>
        complete(HttpResponse(
          status = StatusCodes.OK,
          entity = HttpEntity(ContentTypes.`application/json`, "ok")
        ))
      }
    }
  }
}

