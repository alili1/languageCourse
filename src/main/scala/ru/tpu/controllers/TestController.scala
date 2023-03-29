package ru.tpu.controllers

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.{Directives, Route}
import org.json4s.{DefaultFormats, Formats}

import scala.concurrent.{ExecutionContext, Future}

class TestController(implicit system : ActorSystem, context: ExecutionContext)
  extends Controller {
  override val routes: Route = this.test ~ this.getResponses

  private implicit val formatJson: Formats = DefaultFormats

  def test: Route = post {
    path("test") {
      entity(as[String]) { request =>
            complete(HttpResponse(
              status = StatusCodes.OK,
              entity = HttpEntity(ContentTypes.`application/json`, "ok")
            ))
          }
      }
    }
  def getResponses: Route = post {
    path("getTest") {
      entity(as[String]) { request =>
        complete(HttpResponse(
          status = StatusCodes.OK,
          entity = HttpEntity(ContentTypes.`application/json`, "ok")
        ))
      }
    }
  }
}
