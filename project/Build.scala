import sbt.Keys._
import sbt._


object Resolvers {
  val typeSafeRepo = Classpaths.typesafeReleases
  val sprayRepo = "spray repo" at "http://repo.spray.io"
  def commonResolvers = Seq(typeSafeRepo,sprayRepo)
}

object Dependencies {

  private val akkaVersion = "2.5.1"


  //database
  val casbah = "org.mongodb" %% "casbah" % "2.8.0"

  //serialization
  val json4sJackson = "org.json4s" %% "json4s-jackson" % "3.3.0"
  val json4sExt = "org.json4s" %% "json4s-ext" % "3.3.0"

  //akka
  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akkaRemote = "com.typesafe.akka" %% "akka-remote" % akkaVersion

}

object BuildSettings {
  val Organization = "test"
  val Name = "big-cash-backend"
  val Version = "0.0.1"
  val ScalaVersion = "2.11.8"

  val buildSettings = Seq(
    organization := Organization,
    name := Name,
    version := Version,
    scalaVersion := ScalaVersion,
    fork in Test := true,
    parallelExecution in Test := false,
    javaOptions in Test := Seq("-DSCALA_ENV=test")
  )
}

object MainBuild extends Build {

  import BuildSettings._
  import Dependencies._
  import Resolvers._

  val deps = Seq(casbah, akkaActor, akkaRemote, json4sExt, json4sJackson)

  javacOptions ++= Seq("-encoding", "UTF-8")

  lazy val main = Project(
    Name,
    file("."),
    settings = buildSettings ++ Defaults.coreDefaultSettings ++
      Seq(
        libraryDependencies ++= deps,
        resolvers := commonResolvers
      ))
}
