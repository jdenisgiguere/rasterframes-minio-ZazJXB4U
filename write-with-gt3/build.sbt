name := "write-with-gt3-ZazJXB4U"

scalaVersion := "2.11.12"

resolvers ++= Seq(
    "Locationtech" at "https://repo.locationtech.org/content/repositories/releases/",
  "azavea" at "https://dl.bintray.com/azavea/geotrellis/"
)
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.4.4" % Provided,
  "org.apache.spark" %% "spark-sql" % "2.4.4" % Provided,
  "org.locationtech.geotrellis" %% "geotrellis-store" % "3.2.0",
  "org.locationtech.geotrellis" %% "geotrellis-spark" % "3.2.0",
  "org.locationtech.geotrellis" %% "geotrellis-raster" % "3.2.0",
  "org.locationtech.geotrellis" %% "geotrellis-vector" % "3.2.0",
  "org.locationtech.geotrellis" %% "geotrellis-s3" % "3.2.0",
  "org.locationtech.geotrellis" %% "geotrellis-s3-spark" % "3.2.0"
)

scalacOptions ++= Seq("-Xmax-classfile-name", "78")



