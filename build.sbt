name := "Game board"

version := "1.0"

scalaVersion := "2.9.0-1"

libraryDependencies ++= Seq(
	"org.scala-lang" % "scala-swing" % "2.9.0-1",
//	"org.scala-tools.testing" %% "scalacheck" % "1.9" % "test",
	"org.scala-tools.testing" %% "specs" % "1.6.8" % "test", 
	"org.mockito" % "mockito-core" % "1.9.0-rc1" % "test",
	"junit" % "junit" % "4.7" % "test"
	 )