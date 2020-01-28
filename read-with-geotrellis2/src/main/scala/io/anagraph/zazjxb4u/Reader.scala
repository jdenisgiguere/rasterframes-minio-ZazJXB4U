package io.anagraph.zazjxb4u

import java.net.URI

import geotrellis.vector._
import geotrellis.util._
import geotrellis.raster._
import geotrellis.spark._
import geotrellis.spark.io._
import geotrellis.spark.io.s3._


object Reader {

  def s3Producer(): S3Producer = {
    new S3Producer("minio", "minio123", "http://127.0.0.1:9000/")
  }

  def minioAttributeStore(): MinioAttributeStore = {
    new MinioAttributeStore(
      "geoimagery",
      "geoimagery_geotrellis",
      Reader.s3Producer()
    )
  }

  def main(args: Array[String]): Unit = {

    // We can find layer available in the attribute store
    println(s"Layer Ids are ${Reader.minioAttributeStore().layerIds}")

    // We can read layer metadata from attribute store
    println(s"Layer geoimagery_2002, 0 metadata: ${Reader.minioAttributeStore().readMetadata[TileLayerMetadata[SpatialKey]](LayerId("geoimagery_2002", 0))}")

    println("Done!")
  }

}
