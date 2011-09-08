name := "Game board"

version := "1.0"

scalaVersion := "2.9.0-1"

seq(webSettings :_*)

libraryDependencies ++= Seq(
	"org.scala-lang" % "scala-swing" % "2.9.0-1" withSources () ,
	"org.scala-tools.testing" %% "specs" % "1.6.8" % "test" withSources (), 
	"org.mockito" % "mockito-core" % "1.9.0-rc1" % "test" withSources () ,
	"junit" % "junit" % "4.7" % "test" withSources () ,
	"net.liftweb" %% "lift-webkit" % "2.4-M3" % "compile" withSources () ,
	"org.eclipse.jetty" % "jetty-webapp" % "7.3.0.v20110203" % "jetty" withSources () , 
	"ch.qos.logback" % "logback-classic" % "0.9.24" % "compile"
	 )