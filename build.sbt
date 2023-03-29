import java.nio.file.Files

organization := "ru.alinadk"
name := "test"

mainClass in assembly := Some("ru.alinadk.ManageCourse")
mainClass in (Compile,run) := Some("ru.alinadk.ManageCourse")

scalaVersion := "2.13.7"
version := "0.1-develop"
val AkkaHttpVersion = "10.2.10"
libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.4.2",
  "org.slf4j" % "slf4j-log4j12" % "1.7.36",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.6.16",
  "com.typesafe.akka" %% "akka-http" % "10.2.9",
  "com.typesafe.akka" %% "akka-stream" % "2.6.16",
  "javax.xml.bind" % "jaxb-api" % "2.3.1",
  "com.sun.xml.bind" % "jaxb-impl" % "2.3.1",

  "org.json4s" %% "json4s-native" % "3.6.10",
  "org.json4s" %% "json4s-jackson" % "3.6.10",
  "org.json4s" %% "json4s-ext" % "3.6.10",
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,

  "org.apache.poi" % "poi" % "5.2.3",
  "org.apache.poi" % "poi-ooxml" % "5.2.3",
  "fr.opensagres.xdocreport" % "fr.opensagres.xdocreport.document" % "2.0.4",
  "fr.opensagres.xdocreport" % "fr.opensagres.xdocreport.document.docx" % "2.0.4",
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "com.typesafe.slick" %% "slick-codegen" % "3.3.3",
  "org.postgresql" % "postgresql" % "42.4.1"
)

// - Assembly
lazy val libraries = TaskKey[Unit]("dependencies", "Copy dependencies")
libraries := {
  val path = (baseDirectory.value / "target" / "libraries").toPath

  if(Files.exists(path)) {
    Files.walk(path).forEach(path => path.toFile.delete)
  }
  val target = Files.createDirectories(path)
  (externalDependencyClasspath in Compile).value.files.foreach { file =>
    Files.copy(file.toPath, target.toAbsolutePath / file.name)
  }
}
assembly := assembly.dependsOn(libraries).value
assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = true, includeDependency = false)
packageOptions in assembly += {
  val classpath = (externalDependencyClasspath in Compile).value.files.map(file => s"libraries/${file.name}").mkString(" ")
  Package.ManifestAttributes(
    "Class-Path" -> classpath
  )
}