import sbt.Keys._

name := "service-control"

version := "0.1"

organization := "eu.inn"

scalaVersion := "2.11.7"

crossScalaVersions := Seq("2.11.7", "2.10.5")

libraryDependencies += "org.mockito" % "mockito-all" % "1.10.19" % "test"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"
