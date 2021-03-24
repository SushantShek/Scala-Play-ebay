name := "scalaShowandTell"

version := "0.1"

//sbt.version=1.4.7

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

scalaVersion := "2.13.4"

/*
  Project dependencies
 */
//resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
libraryDependencies ++= Seq(
  "org.springframework.boot" % "spring-boot-starter-web" % "1.5.4.RELEASE",
  "org.springframework.boot" % "spring-boot-configuration-processor" % "1.5.4.RELEASE",
  "org.projectlombok" % "lombok" % "1.16.16",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.12.1",
)

/*
  Packaging plugin
 */

// enable the Java app packaging archetype and Ash script (for Alpine Linux, doesn't have Bash)
//enablePlugins(JavaAppPackaging, AshScriptPlugin)

// set the main entrypoint to the application that is used in startup scripts
mainClass in Compile := Some("com.mp.showandtell.MyServiceApplication")
