resolvers ++= Seq(
	"Web plugin repo" at "http://siasia.github.com/maven2",
	"sonatype.repo" at "https://oss.sonatype.org/content/groups/public",
	Resolver.url("Typesafe repository", new java.net.URL("http://typesafe.artifactoryonline.com/typesafe/ivy-releases/"))(Resolver.defaultIvyPatterns), 
	ScalaToolsSnapshots  // for eclipsify
)

libraryDependencies <+= sbtVersion(v => "com.github.siasia" %% "xsbt-web-plugin" % ("0.1.0-"+v))

libraryDependencies <+= sbtVersion(v => "eu.getintheloop" %% "sbt-cloudbees-plugin" % ("0.3.1_"+v))

libraryDependencies += "de.element34" %% "sbt-eclipsify" % "0.10.0-SNAPSHOT"
