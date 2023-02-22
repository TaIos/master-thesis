name := """impl"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.10"

libraryDependencies ++= Seq(
  guice,
  "org.projectlombok" % "lombok" % "1.18.26",
  "net.sf.oval" % "oval" % "3.2.1",
  "org.apache.commons" % "commons-text" % "1.10.0",
  "org.mariuszgromada.math" % "MathParser.org-mXparser" % "5.2.1"
)
