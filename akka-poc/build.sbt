ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.6"

lazy val akkaVersion = "2.8.4"

lazy val root = (project in file("."))
  .settings(
    name := "akka-poc",
    libraryDependencies ++= Seq(
      // Akka Typed Actor
      "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,

      // Akka Streams
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,

      // Akka Cluster Typed
      "com.typesafe.akka" %% "akka-cluster-typed" % akkaVersion,

      // Logging
      "ch.qos.logback" % "logback-classic" % "1.4.11"
    )
  )
