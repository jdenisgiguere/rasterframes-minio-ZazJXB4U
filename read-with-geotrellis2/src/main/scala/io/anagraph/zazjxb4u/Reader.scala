package io.anagraph.zazjxb4u

import java.net.URI

import geotrellis.vector._
import geotrellis.util._
import geotrellis.raster._
import geotrellis.spark._
import geotrellis.spark.io._
import geotrellis.spark.io.s3._
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


object Reader {
  val s3AccessKey: String = "minio"
  val s3SecretKey: String = "minio123"
  val s3Url: String = "http://192.168.2.133:9000"
  val bucket: String = "geoimagery"
  val geotrellisPrefix: String = "geoimagery_geotrellis"
  val layerName: String = "geoimagery_2002"
  val layerZoomLevel: Int = 0
  val layerId: LayerId = LayerId(layerName, layerZoomLevel)

  def s3Producer(): S3Producer = {
    new S3Producer(s3AccessKey, s3SecretKey, s3Url)
  }

  def minioAttributeStore(): MinioAttributeStore = {
    new MinioAttributeStore(
      bucket, geotrellisPrefix, Reader.s3Producer()
    )
  }

  def minioLayerReader(sc: SparkContext): MinioLayerReader = {
    new MinioLayerReader(Reader.minioAttributeStore(),
      "geoimagery",
      "geoimagery_geotrellis",
      sc)
  }

  def main(args: Array[String]): Unit = {

    // We can find layer available in the attribute store
    println(s"Layer Ids are ${Reader.minioAttributeStore().layerIds}")

    // We can read layer metadata from attribute store
    println(s"Layer geoimagery_2002, 0 metadata: ${Reader.minioAttributeStore().readMetadata[TileLayerMetadata[SpatialKey]](layerId)}")

    val sparkConf: SparkConf = new SparkConf()
      .set("spark.hadoop.fs.s3a.endpoint", s3Url)
      .set("spark.hadoop.fs.s3a.access.key", s3AccessKey)
      .set("spark.hadoop.fs.s3a.secret.key", s3SecretKey)
      .set("spark.hadoop.fs.s3a.path.style.access", "true")
      .set("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
      .setAppName("Geotrellis2 reader")
      .setMaster("local[1]")

    val sc: SparkContext = SparkContext.getOrCreate(sparkConf)


    // We can read data from GT layer through rdd
    val reader = Reader.minioLayerReader(sc)
    val rdd: RDD[(SpatialKey, MultibandTile)] with Metadata[TileLayerMetadata[SpatialKey]] =
  reader.read[SpatialKey, MultibandTile, TileLayerMetadata[SpatialKey]](layerId)



    println(s"Layer geoimagery_2002, 0 first data is ${rdd.first()._2.band(0).toArray()(0)}")


    println("Done!")
  }

}
