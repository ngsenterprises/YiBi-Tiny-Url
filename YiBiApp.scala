package com.ngs.yibi

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.ngs.yibi.YiBiApp.requestHandler

import scala.concurrent.{ExecutionContext, Future}
import scala.io.StdIn
import com.ngs.yibi.routes.UserRoutes
import com.ngs.yibi.dao.Dao
import com.ngs.yibi.baseconfig.BaseConfig
import com.typesafe.config.{Config, ConfigFactory}
import akka.http.scaladsl.model.HttpRequest
import akka.event.Logging

object YiBiApp extends App with UserRoutes with Dao {

  implicit val system: ActorSystem = ActorSystem("YiBiUrlServer")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher
  lazy val conf = BaseConfig.conf

  lazy val httpHost = conf.getString("akka.host")//"localhost"
  lazy val httpPort = conf.getInt("akka.port")//8080

  //lazy val log = Logging(system, classOf[UrlShortenActor])

  val serverBindingFuture: Future[ServerBinding] = Http().bindAndHandleAsync( requestHandler, httpHost, httpPort )

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine()

  serverBindingFuture
    .flatMap(_.unbind())
    .onComplete { done =>
      done.failed.map { ex => println(ex, "Failed unbinding") }
      system.terminate()
    }

}
