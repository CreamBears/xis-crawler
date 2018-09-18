import Dependencies._

lazy val root = (project in file("."))
  .dependsOn(crawler)
  .settings(
    inThisBuild(List(
      organization := "service.xis.crawler",
      scalaVersion := "2.12.6",
      version      := "0.1.0"
    )),
    name := "xis-crawler",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.12",
    libraryDependencies += "joda-time" % "joda-time" % "2.10"
  )
