
/*
name=YiBi
app_version=1.0.0
organization=com.ngs
scala_version=2.12.4
akka_http_version=10.0.9
akka_version=2.5.3
scala_test_version=3.0.1
akka_teskit_version=2.5.6
cassandra_driver_core_version=2.1.6
phantom_dsl_version=2.15.5
*/

lazy val appVersion = "1.0.0"
lazy val appName = "yibi"
lazy val appOrganization = "com.ngs"
lazy val scalaversion = "2.12.4"

lazy val akkaHttpVersion = "10.0.9"
lazy val akkaVersion    = "2.5.3"

lazy val scalaTestVersion = "3.0.1"
lazy val akkaTestkitVersion = "2.5.6"

lazy val cassandraVersion= "2.1.6"
lazy val phantomVersion = "2.15.5"

lazy val root = (project in file(".")).
  settings(
    inThisBuild( List(
      version         := appVersion,
      organization    := appOrganization,
      scalaVersion    := scalaversion
    ) ),
    name := appName,
    libraryDependencies ++= Seq(

      "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-xml"        % akkaHttpVersion,

      "com.typesafe.akka" %% "akka-actor"           % akkaVersion,
      "com.typesafe.akka" %% "akka-stream"          % akkaVersion,

      "com.datastax.cassandra"  % "cassandra-driver-core"   % cassandraVersion,
      "com.outworkers"          %% "phantom-dsl"            % phantomVersion,

      "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion     % Test,
      "org.scalatest"     %% "scalatest"            % scalaTestVersion    % Test,
      "com.typesafe.akka" %% "akka-testkit"         % akkaTestkitVersion  % Test

    )
  )

        