package io.anagraph.zazjxb4u

import geotrellis.spark.io.s3.{S3Client, S3RDDReader}

object MinioRddReader extends S3RDDReader {
  val s3AccessKey: String = "minio"
  val s3SecretKey: String = "minio123"
  val s3Url: String       = "http://192.168.2.133:9000/"

  val s3Producer: S3Producer = new S3Producer(s3AccessKey, s3SecretKey, s3Url)

  def getS3Client: () => S3Client = () => s3Producer.produce()

}
