package io.anagraph.zazjxb4u


import geotrellis.spark.io.s3.{S3AttributeStore, S3Client}

class MinioAttributeStore(
    override val bucket: String,
    override val prefix: String,
    val s3Producer: S3Producer
) extends S3AttributeStore(bucket, prefix) {
  @transient lazy val amazonS3Client: S3Client = s3Producer.produce()
  override def s3Client: S3Client              = amazonS3Client
}
