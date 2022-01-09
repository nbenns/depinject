ThisBuild / scalaVersion     := "3.1.0"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "us.wh"
ThisBuild / organizationName := "William Hill"

idePackagePrefix := Some("us.wh.zlayerdemo")

scalacOptions ++= Seq("-source:future")

lazy val root = (project in file("."))
  .settings(
    name := "zlayerdemo",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.0-M6-2",
      "dev.zio" %% "zio-test" % "2.0.0-M6-2" % Test
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
