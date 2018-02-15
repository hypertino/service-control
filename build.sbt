name := "service-control"

version := "0.5-SNAPSHOT"

organization := "com.hypertino"

crossScalaVersions := Seq("2.12.4", "2.11.12")

scalaVersion := crossScalaVersions.value.head

libraryDependencies ++= Seq(
  "org.scaldi" %% "scaldi" % "0.5.8",
  "org.slf4j" % "slf4j-api" % "1.7.25",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.5.0" % "test"
)

resolvers ++= Seq(
	Resolver.sonatypeRepo("public")
)
