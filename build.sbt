name := "ScalaCypher"

version := "0.1"

scalaVersion := "2.11.0"

libraryDependencies ++= Seq(
  "me.manishkatoch" % "scala-cypher-dsl" % "0.4.6",
  "org.neo4j.driver" % "neo4j-java-driver" % "1.0.0",
  "org.typelevel" %% "cats-effect" % "2.0.0"
)
