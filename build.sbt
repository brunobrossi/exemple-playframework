name := """produtos-api"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
  "mysql" % "mysql-connector-java" % "5.1.36",
  "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final",
  "com.adrianhurt" % "play-bootstrap_2.11" % "1.0-P25-B3",
  "com.typesafe.play" % "play-mailer_2.11" % "5.0.0-M1"
)

EclipseKeys.preTasks := Seq(compile in Compile)
EclipseKeys.projectFlavor := EclipseProjectFlavor.Java
EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.ManagedClasses, EclipseCreateSrc.ManagedResources)
