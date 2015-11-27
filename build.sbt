import sbt.Keys._

name := "service-control"

version := "0.1"

organization := "eu.inn"

scalaVersion := "2.11.7"

crossScalaVersions := Seq("2.11.7")

libraryDependencies += "org.scaldi" %% "scaldi" % "0.5.6"

libraryDependencies += "org.mockito" % "mockito-all" % "1.10.19" % "test"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"
