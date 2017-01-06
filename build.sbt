name := "service-control"

version := "0.3-SNAPSHOT"

organization := "com.hypertino"

scalaVersion := "2.12.1"

crossScalaVersions := Seq("2.12.1", "2.11.8")

libraryDependencies ++= Seq(
  "org.scaldi" %% "scaldi" % "0.5.8",
  "org.slf4j" % "slf4j-api" % "1.7.22",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "com.hypertino" %% "scalamock-scalatest-support" % "3.4-SNAPSHOT" % "test"
)

resolvers ++= Seq(
	Resolver.sonatypeRepo("public")
)
