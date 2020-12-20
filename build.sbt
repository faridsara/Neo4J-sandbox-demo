name := "ScalaCypher"

version := "0.1"

scalaVersion := "2.13.4"

libraryDependencies ++= Seq(
  "org.neo4j.driver" % "neo4j-java-driver" % "1.0.0",
  "org.typelevel" %% "cats-effect" % "2.3.1"
)
