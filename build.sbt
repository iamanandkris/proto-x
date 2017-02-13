import sbt.Keys._

name := "PersistenceQueryExample"

version := "1.0"

scalaVersion := "2.12.1"

sbtVersion := "0.13.13"

resolvers ++= Seq("Akka Snapshot Repository" at "http://repo.akka.io/snapshots/",
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/releases",
  "krasserm at bintray" at "http://dl.bintray.com/krasserm/maven",
  "logger" at "https://mvnrepository.com/artifact")


organization := "test"

PB.targets in Compile := Seq(scalapb.gen() -> (sourceManaged in Compile).value)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.16",
  "com.typesafe.akka" %% "akka-http-core" % "10.0.1",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.1",
  "com.typesafe.akka" %% "akka-remote" % "2.4.16",
  "com.typesafe.akka" %% "akka-cluster" % "2.4.16",
  "com.typesafe.akka" %% "akka-cluster-tools" % "2.4.16",
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.16",
  "com.typesafe.akka" %% "akka-persistence-cassandra" % "0.22",
  "com.github.romix.akka" %% "akka-kryo-serialization" % "0.5.1",
  "com.typesafe.akka" %% "akka-stream" % "2.4.16",
  "org.scalaz" %% "scalaz-core" % "7.2.8",
  "org.typelevel" %% "cats" % "0.8.1",
  "com.typesafe.akka" %% "akka-persistence-query-experimental" % "2.4.16",
  "com.trueaccord.scalapb" %% "scalapb-runtime" % com.trueaccord.scalapb.compiler.Version.scalapbVersion % "protobuf"
)



// Assembly settings
mainClass in Global := Some("voice.ReadServer")

//jarName in assembly := "persistence-examples.jar"
