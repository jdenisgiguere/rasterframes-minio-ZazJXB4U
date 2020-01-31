name := "write-with-gt3-ZazJXB4U"

scalaVersion := "2.11.12"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")
scalacOptions ++= Seq("-target:jvm-1.8")
scalacOptions ++= Seq("-Xmax-classfile-name", "78")
initialize := {
  val _ = initialize.value
  val javaVersion = sys.props("java.specification.version")
  if (javaVersion != "1.8")
    sys.error("Java 1.8 is required for this project. Found " + javaVersion + " instead")
}


resolvers ++= Seq(
    "Locationtech" at "https://repo.locationtech.org/content/repositories/releases/",
  "azavea" at "https://dl.bintray.com/azavea/geotrellis/"
)
libraryDependencies ++= Seq(
  "org.apache.httpcomponents" % "httpclient" % "4.5.9",
  "org.apache.httpcomponents" % "httpcore" % "4.4.11",
  "org.apache.spark" %% "spark-core" % "2.4.4" % Provided,
  "org.apache.spark" %% "spark-sql" % "2.4.4" % Provided,
  "org.locationtech.geotrellis" %% "geotrellis-store" % "3.2.0",
  "org.locationtech.geotrellis" %% "geotrellis-spark" % "3.2.0",
  "org.locationtech.geotrellis" %% "geotrellis-raster" % "3.2.0",
  "org.locationtech.geotrellis" %% "geotrellis-vector" % "3.2.0",
  "org.locationtech.geotrellis" %% "geotrellis-s3" % "3.2.0",
  "org.locationtech.geotrellis" %% "geotrellis-s3-spark" % "3.2.0"
)


assemblyMergeStrategy in assembly := {
  case PathList("org", "aopalliance", xs @ _*)      => MergeStrategy.last
  case PathList("javax", "inject", xs @ _*)         => MergeStrategy.last
  case PathList("javax", "servlet", xs @ _*)        => MergeStrategy.last
  case PathList("javax", "activation", xs @ _*)     => MergeStrategy.last
  case PathList("javax", "annotation", xs @ _*)     => MergeStrategy.last
  case PathList("javax", "xml", xs @ _*)            => MergeStrategy.last
  case PathList("javax", "ws", xs @ _*)             => MergeStrategy.last
  case PathList("org", "apache", xs @ _*)           => MergeStrategy.last
  case PathList("com", "google", xs @ _*)           => MergeStrategy.last
  case PathList("com", "esotericsoftware", xs @ _*) => MergeStrategy.last
  case PathList("com", "codahale", xs @ _*)         => MergeStrategy.last
  case PathList("com", "yammer", xs @ _*)           => MergeStrategy.last
  case PathList("com", "sun", "research", xs @ _*)  => MergeStrategy.last
  case PathList("io", "netty", xs @ _*)             => MergeStrategy.last
  case PathList("com", "amazonaws", xs @ _*)        => MergeStrategy.last
  case "about.html"                                 => MergeStrategy.rename
  case "META-INF/ECLIPSEF.RSA"                      => MergeStrategy.last
  case "META-INF/mailcap"                           => MergeStrategy.last
  case "META-INF/mimetypes.default"                 => MergeStrategy.last
  case "plugin.properties"                          => MergeStrategy.last
  case "log4j.properties"                           => MergeStrategy.last
  case "META-INF/io.netty.versions.properties"      => MergeStrategy.last
  case "git.properties"                             => MergeStrategy.last
  case "mozilla/public-suffix-list.txt"             => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

dependencyOverrides += "org.apache.httpcomponents" % "httpclient" % "4.5.9"
dependencyOverrides += "org.apache.httpcomponents" % "httpcore" % "4.4.11"




