package io.anagraph.zazjxb4

import java.net.URI

import geotrellis.raster.{Tile, TileFeature}
import geotrellis.layer._
import geotrellis.raster.geotiff._
import geotrellis.spark.RasterSourceRDD
import geotrellis.store._
import geotrellis.spark.store._
import geotrellis.spark.store.hadoop._
import geotrellis.store.hadoop.{HadoopAttributeStore, SerializableConfiguration}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path

object TileGeotiff {
  val s3AccessKey: String = "minio"
  val s3SecretKey: String = "minio123"
  val s3Url: String = "http://192.168.2.133:9000"

  val prefix: String = "gt3_catalog"
  lazy val configuration: Configuration = new Configuration()
  lazy val serializableConfiguration: SerializableConfiguration = SerializableConfiguration(configuration)
  val attributeStore = new HadoopAttributeStore("s3a://geoimagery/gt3catalog", serializableConfiguration)
  val path: Path = new Path("s3a://geoimagery/gt3catalog")
  val layerWriter = new HadoopLayerWriter(path, attributeStore)


  def main(args: Array[String]): Unit = {
    val conf =
      new SparkConf()
        .setMaster("local[*]")
        .setAppName("Spark Tiler")
        .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
        .set("spark.kryo.registrator", "geotrellis.spark.io.kryo.KryoRegistrator")
        .set("spark.hadoop.fs.s3a.endpoint", s3Url)
      .set("spark.hadoop.fs.s3a.access.key", s3AccessKey)
      .set("spark.hadoop.fs.s3a.secret.key", s3SecretKey)
      .set("spark.hadoop.fs.s3a.path.style.access", "true")
      .set("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")


    implicit val sc: SparkContext = SparkContext.getOrCreate(conf)

    val geoTiffPath: GeoTiffPath = GeoTiffPath("s3a://geoimagery/tif_files/geoimagery_2002/1080056352.tif")
    val rasterSource = GeoTiffRasterSource(geoTiffPath)
    val crs = rasterSource.crs
    val scheme = FloatingLayoutScheme(1006, 1006)
    val layout = scheme.levelFor(rasterSource.extent, rasterSource.cellSize).layout

    val reprojectedSource = rasterSource.reprojectToGrid(crs, layout)

    val rdd = RasterSourceRDD.spatial(reprojectedSource, layout)

    val actualKeys = rdd.keys.collect().sortBy { key => (key.col, key.row) }
    println(actualKeys)

   // val inputRdd = RasterSourceRDD.spatial(rasterSource, layout)




    println("Done!")
  }


}
