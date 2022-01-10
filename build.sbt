ThisBuild / scalaVersion     := "2.13.7"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.caesars"
ThisBuild / organizationName := "Caesars"
ThisBuild / name             := "todo"

scalacOptions ++= Seq("-source:future")

lazy val depinject =
  (project in file("."))
    .settings(publish / skip := true)
    .aggregate()

lazy val springbootApi =
  project

lazy val taglessfinalApi =
  project

lazy val zioApi =
  project
    .settings(
      libraryDependencies ++= Seq(
        "dev.zio" %% "zio" % "1.0.13",
        "io.github.kitlangton" %% "zio-magic" % "0.3.11",
        "com.softwaremill.sttp.tapir" %% "tapir-core" % "0.19.3",
        "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % "0.19.3",
        "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % "0.19.3",
        "dev.zio" %% "zio-interop-cats" % "3.2.9.0"
      )
    )