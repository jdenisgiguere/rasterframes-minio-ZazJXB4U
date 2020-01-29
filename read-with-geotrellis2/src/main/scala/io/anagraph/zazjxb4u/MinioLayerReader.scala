package io.anagraph.zazjxb4u

import geotrellis.spark.io.s3.{S3Client, S3LayerReader, S3LayerWriter, S3RDDReader, S3RDDWriter}
import org.apache.spark.SparkContext
class MinioLayerReader(
    override val attributeStore: MinioAttributeStore,
    val bucket: String,
    val keyPrefix: String,
    implicit val sc: SparkContext
) extends S3LayerReader(attributeStore) {

  override def rddReader: S3RDDReader = MinioRddReader
}
