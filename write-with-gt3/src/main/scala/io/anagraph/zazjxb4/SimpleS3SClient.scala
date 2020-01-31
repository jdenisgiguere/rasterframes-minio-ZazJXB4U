package io.anagraph.zazjxb4

import software.amazon.awssdk.auth.credentials.{AwsBasicCredentials, StaticCredentialsProvider}
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.regions.Region

import java.net.URI
object SimpleS3SClient {
    def apply(): S3Client = {
    val cred = AwsBasicCredentials.create("minio", "minio123")
    val credProvider = StaticCredentialsProvider.create(cred)
    S3Client.builder()
      .endpointOverride(new URI("http://192.168.2.133:9000"))
      .credentialsProvider(credProvider)
      .region(Region.US_EAST_1)
      .build()
  }

  @transient lazy val instance: S3Client = apply()

}
