name := """user_management"""
organization := "vn.daoanhthanh"

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    PlayKeys.playDefaultPort := 9090,
    scalaVersion := "2.13.7",
    PlayKeys.fileWatchService := play.dev.filewatch.FileWatchService
      .jdk7(play.sbt.run.toLoggerProxy(sLog.value)),
    libraryDependencies ++= Seq(
      guice,
//      "com.h2database" % "h2" % "1.4.199",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
      jdbc,
      javaJdbc,
      javaWs,
      "mysql" % "mysql-connector-java" % "8.0.11",
      "org.scalikejdbc" %% "scalikejdbc" % "3.5.0",
      "org.scalikejdbc" %% "scalikejdbc-config" % "3.5.0",
      "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.8.0-scalikejdbc-3.5",
      "org.playframework.anorm" %% "anorm" % "2.6.10",
      "org.apache.commons" % "commons-dbcp2" % "2.9.0",
//      "org.json4s" %% "json4s-jackson" % "3.5.3"
    )
  )