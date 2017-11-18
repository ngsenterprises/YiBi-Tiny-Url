package com.ngs.yibi.dao

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.datastax.driver.core.{Cluster, Session, ResultSet}
import scala.concurrent.ExecutionContext
import com.typesafe.config.{Config, ConfigFactory}
import scala.util.{Failure, Success, Try}
import com.ngs.yibi.baseconfig.BaseConfig

trait Dao {

  implicit def system: ActorSystem
  implicit def materializer: ActorMaterializer
  implicit def executionContext: ExecutionContext
  lazy val baseconfig = BaseConfig.conf

  lazy val host = baseconfig.getString("cassandra.host")
  //println(s"host = ${host}")
  lazy val keyspace = baseconfig.getString("cassandra.keyspace")
  lazy val columnfamily = baseconfig.getString("cassandra.columnfamily")
  //println(s"keyspace = ${keyspace}")

  val (cluster, session) = clusterSession( host )

  val ks_res = createUrlKeyspace( session )

  val cf_res = createUrlTable( session )


  def getUrl( url: String ): ResultSet = getUrl( session, url )

  def getUrl( session: Session, url: String ): ResultSet = {
    session.execute( "USE URL" )
    val cq = s"SELECT orgurl FROM url.yibi WHERE orgurl = ${url}"
    session.execute( cq )
  }

  def getLastRecordAded( ): ResultSet = getLastRecordAded( session: Session )

  def getLastRecordAded( session: Session ): ResultSet = {
    session.execute( "USE URL" )
    val cq = "SELECT seedid FROM url.yibi LIMIT 1"
    session.execute( cq )
  }

  def createUrlTable( session: Session ): ResultSet = {
    session.execute( "USE URL" )
    val cf = s"CREATE TABLE IF NOT EXISTS yibi " +
    s"( orgurl text, seedid int, PRIMARY KEY ( orgurl ) ) "
    session.execute( cf )
  }

  def createUrlKeyspace( session: Session ): ResultSet = {
    val ks = s"CREATE KEYSPACE IF NOT EXISTS url WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};"
    session.execute( ks )
  }


  def clusterSession( host: String ): (Cluster, Session) = {
    {
      for {
        bdr <- Try( Cluster.builder )
        ahost <- Try( bdr.addContactPoint(host) )
        cluster <- Try( ahost.build )
        session <- Try( cluster.connect )
      } yield ( (cluster, session) )
    } match {
      case Failure( f ) => throw new RuntimeException("failed to create cassandra Cluster.")
      case Success( p ) => p
    }
  }


}
