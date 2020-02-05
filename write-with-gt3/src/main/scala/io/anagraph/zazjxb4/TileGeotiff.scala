package io.anagraph.zazjxb4

import java.net.URI

import geotrellis.raster.{Tile, TileFeature}
import geotrellis.vector._
import geotrellis.layer._
import geotrellis.raster.geotiff._
import geotrellis.spark.RasterSourceRDD
import geotrellis.spark.ingest.MultibandIngest
import geotrellis.store._
import geotrellis.spark.store._
import geotrellis.spark.store.hadoop._
import geotrellis.store.hadoop.{HadoopAttributeStore, SerializableConfiguration}
import geotrellis.store.index.ZCurveKeyIndexMethod
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.spark.sql.SparkSession

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
        .set("spark.hadoop.fs.s3a.endpoint", s3Url)
      .set("spark.hadoop.fs.s3a.access.key", s3AccessKey)
      .set("spark.hadoop.fs.s3a.secret.key", s3SecretKey)
      .set("spark.hadoop.fs.s3a.path.style.access", "true")
      .set("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")


    implicit val sc: SparkContext = SparkContext.getOrCreate(conf)
    val session: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    val source = sc.hadoopMultibandGeoTiffRDD("s3a://geoimagery/tif_files/geoimagery_2002/1080056352.tif")

    // Reading parquet is working.
    // We wil get an error later if in rasterframes if we don't do this first
    //val someMetadata = session.read.parquet("s3a://geoimagery/metadata_data_frame/geoimagery_2002.parquet")
    //someMetadata.show(1)


    val geoTiffPath: GeoTiffPath = GeoTiffPath("s3a://geoimagery/tif_files/geoimagery_2002/1080056352.tif")
    val rasterSource = GeoTiffRasterSource(geoTiffPath)
    val crs = rasterSource.crs
    val scheme = FloatingLayoutScheme(1006, 1006)
    val layout = scheme.levelFor(rasterSource.extent, rasterSource.cellSize).layout

    val reprojectedSource = rasterSource.reprojectToGrid(crs, layout)

    val rdd = RasterSourceRDD.spatial(reprojectedSource, layout)

    val actualKeys = rdd.keys.collect().sortBy { key => (key.col, key.row) }
    println(actualKeys)

   val inputRdd = RasterSourceRDD.spatial(rasterSource, layout)
    val layerId = LayerId("my_layer", 0)
    layerWriter.write(layerId, inputRdd, ZCurveKeyIndexMethod)
//    MultibandIngest[ProjectedExtent, SpatialKey](source, crs, scheme){
//      (bidon, id) => {
//        layerWriter.write[](bidon)
//      }
//    }
//


    println("Done!")
  }


}
