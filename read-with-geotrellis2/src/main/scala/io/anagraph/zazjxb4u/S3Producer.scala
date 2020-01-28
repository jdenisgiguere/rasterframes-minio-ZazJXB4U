package io.anagraph.zazjxb4u

import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import geotrellis.spark.io.s3.AmazonS3Client

class S3Producer(s3AccessKey: String, s3SecretKey: String, s3Url: String) extends Serializable {
  val region = Regions.US_EAST_1.name()

  val awsCredentials: BasicAWSCredentials =
    new BasicAWSCredentials(s3AccessKey, s3SecretKey)
  val credentialsProvider: AWSStaticCredentialsProvider =
    new AWSStaticCredentialsProvider(awsCredentials)

  val clientConfiguration = new ClientConfiguration()
  clientConfiguration.setSignerOverride("AWSS3V4SignerType")

  val amazonS3Client = AmazonS3ClientBuilder
    .standard()
    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3Url, region))
    .withPathStyleAccessEnabled(true)
    .withClientConfiguration(clientConfiguration)
    .withCredentials(credentialsProvider)
    .build()

  val specialS3Client: AmazonS3Client = new AmazonS3Client(amazonS3Client)

  def produce(): AmazonS3Client = specialS3Client
}
