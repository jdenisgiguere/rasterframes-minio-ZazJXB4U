package io.anagraph.zazjxb4u

import java.io.File
import java.sql.Timestamp
import java.time.ZonedDateTime

import org.locationtech.rasterframes._
import org.locationtech.rasterframes.datasource.DataSourceOptions
import org.locationtech.rasterframes.rules._
import org.locationtech.rasterframes.util._
import geotrellis.proj4.LatLng
import geotrellis.raster._
import geotrellis.raster.resample.NearestNeighbor
import geotrellis.spark._
import geotrellis.spark.store._
import geotrellis.store.LayerId
//import geotrellis.spark.io._
//import geotrellis.spark.io.avro.AvroRecordCodec
//import geotrellis.spark.io.avro.codecs.Implicits._
//import geotrellis.spark.io.index.ZCurveKeyIndexMethod
//import geotrellis.spark.tiling.ZoomedLayoutScheme
import geotrellis.vector._
import org.apache.avro.generic._
import org.apache.avro.{Schema, SchemaBuilder}
import org.apache.hadoop.fs.FileUtil
import org.apache.spark.sql.functions.{udf => sparkUdf}
import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.storage.StorageLevel
//import org.locationtech.rasterframes.TestEnvironment
//import org.scalatest.{BeforeAndAfterAll, Inspectors}

import scala.math.{max, min}

import java.net.URI
import org.locationtech.rasterframes.datasource._
import org.locationtech.rasterframes.datasource.raster._
import org.locationtech.rasterframes.datasource.geotrellis._
import org.locationtech.rasterframes._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.SparkConf

object RfBisReader {
  val s3AccessKey: String = "minio"
  val s3SecretKey: String = "minio123"
  val s3Url: String = "http://192.168.2.133:9000"
  val bucket: String = "geoimagery"
  val geotrellisPrefix: String = "geoimagery_geotrellis"
  val layerName: String = "geoimagery_2002"
  val layerZoomLevel: Int = 0
  //val layerId: LayerId = LayerId(layerName, layerZoomLevel)

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf()
      .set("spark.hadoop.fs.s3a.endpoint", s3Url)
      .set("spark.hadoop.fs.s3a.access.key", s3AccessKey)
      .set("spark.hadoop.fs.s3a.secret.key", s3SecretKey)
      .set("spark.hadoop.fs.s3a.path.style.access", "true")
      .set("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.kryo.registrator", "org.locationtech.rasterframes.util.RFKryoRegistrator")
      .setAppName("Rasteframes0.8.5 reader")
      .setMaster("local[1]")

    implicit val spark: SparkSession = SparkSession.builder().config(sparkConf).withKryoSerialization.getOrCreate().withRasterFrames

    import spark.implicits._

    // Reading parquet is working.
    // We wil get an error later if in rasterframes if we don't do this first
    val someMetadata = spark.read.parquet("s3a://geoimagery/metadata_data_frame/geoimagery_2002.parquet")
    someMetadata.show(1)

    // Reading Geotrellis is not working
    val catalogUri = new URI("s3a://geoimagery/geotrellis_geoimagery/")
    val catalog = spark.read.geotrellisCatalog(catalogUri)
    //println(s"Catalog is ${catalog}")

    val layer = Layer(catalogUri, LayerId(layerName, layerZoomLevel))

    //val layerDataFrame = spark.read.geotrellis.loadLayer(layer)
    //layerDataFrame.show(1)




    println("Done!")
  }
}
