import sbt.Keys._

name := "service-control"

version := "0.2"

organization := "eu.inn"

scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.11.8")

libraryDependencies ++= Seq(
  "org.scaldi" %% "scaldi" % "0.5.6",
  "org.slf4j" % "slf4j-api" % "1.7.21",
  "org.mockito" % "mockito-all" % "1.10.19" % "test",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test"
)
