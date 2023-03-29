package ru.tpu.controllers

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.server.{Directives, Route}
import ru.tpu.CORSHandler

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

abstract class Controller(implicit context: ExecutionContext) extends Directives with CORSHandler {
  val routes: Route
  final def complete(response: Try[Future[HttpResponse]]): Route = {
    response match {
      case Success(future) => future.onComplete {
        case Success(result) => result
        case Failure(throwable) => throwable.printStackTrace()
      }
      case Failure(throwable) => throwable.printStackTrace()
    }
    Directives.complete(response)
  }
}