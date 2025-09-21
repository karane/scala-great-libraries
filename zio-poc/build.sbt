ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.6"

lazy val root = (project in file("."))
  .settings(
    name := "zio-poc",
    version := "0.1.0",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.1.0",
      "dev.zio" %% "zio-streams" % "2.1.0",
      "dev.zio" %% "zio-test" % "2.1.0" % Test,
      "dev.zio" %% "zio-test-sbt" % "2.1.0" % Test
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
