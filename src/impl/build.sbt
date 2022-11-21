name := """impl"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.10"

libraryDependencies ++= Seq(
  guice,
  "org.projectlombok" % "lombok" % "1.18.24",
  "net.sf.oval" % "oval" % "3.2.1",
)
