name := """merge-service"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.6"

crossScalaVersions := Seq("2.11.12", "2.12.6")

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
// https://mvnrepository.com/artifact/org.apache.jena/jena-core
libraryDependencies += "org.apache.jena" % "jena-core" % "3.5.0"
// https://mvnrepository.com/artifact/org.apache.jena/jena-arq
libraryDependencies += "org.apache.jena" % "jena-arq" % "3.5.0"

