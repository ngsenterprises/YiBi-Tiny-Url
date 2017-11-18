package com.ngs.yibi.routes

import scala.concurrent.{ExecutionContext, Future}
import akka.actor.{ActorRef, ActorSystem}
import akka.event.Logging
import akka.stream.ActorMaterializer

import scala.concurrent.duration._
import akka.http.scaladsl.model._

import scala.concurrent.Future
import akka.util.Timeout
import com.ngs.yibi.html.HtmlContent
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.HttpResponse
import com.ngs.yibi.dao.Dao
import com.ngs.yibi.util.Util

trait UserRoutes extends Dao {

  implicit def system: ActorSystem
  implicit def materializer: ActorMaterializer
  implicit def executionContext: ExecutionContext

  //lazy val log = Logging(system, classOf[UserRoutes])
  //implicit lazy val timeout = Timeout(5.seconds) // usually we'd obtain the timeout from the system's configuration

  val requestHandler: HttpRequest => Future[HttpResponse] = {
    case HttpRequest( GET, _, _, _, _) =>
      Future( HttpResponse(entity = HttpEntity(ContentTypes.`text/html(UTF-8)`, HtmlContent.getDefault("YiBi" ) ) ) )

    case HttpRequest( POST , _, _, c, _) =>
      val url =
      c.asInstanceOf[HttpEntity.Strict].data.utf8String.split("=") match {
        case arr if ( arr.length < 2 ) => ""
        case arr => arr.last
      }

      //fake shrunk url
      val k = scala.util.Random.nextInt( Int.MaxValue )
      val shrunkid = Util.urlEncode( k )
      val newurl = s"127.0.0,1:8080/${shrunkid}"
      
      Future( HttpResponse( entity = HttpEntity(ContentTypes.`text/html(UTF-8)`, HtmlContent.getResult("YiBi", newurl ) ) ) )
  }

}
