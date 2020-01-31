#!/bin/bash
export HADOOP_HOME=/opt/hadoop-3.1.3
export SPARK_HOME=/opt/spark-2.4.4-bin-without-hadoop
export PATH=$HADOOP_HOME/bin:$SPARK_HOME/bin:$PATH
export SPARK_DIST_CLASSPATH=$(hadoop classpath):$HADOOP_HOME/share/hadoop/tools/lib/*
export GEOTRELLIS_VERSION=2.3.3
export RF_VERSION=0.8.5
export SPARK_PACKAGES=org.locationtech.rasterframes:rasterframes_2.11:${RF_VERSION}
export SPARK_PACKAGES=$SPARK_PACKAGES,org.locationtech.rasterframes:rasterframes-datasource_2.11:${RF_VERSION}
export SPARK_PACKAGES=$SPARK_PACKAGES,org.locationtech.geotrellis:geotrellis-s3_2.11:${GEOTRELLIS_VERSION}
export SPARK_PACKAGES=$SPARK_PACKAGES,org.locationtech.geotrellis:geotrellis-raster_2.11:${GEOTRELLIS_VERSION}
export SPARK_PACKAGES=$SPARK_PACKAGES,org.locationtech.geotrellis:geotrellis-vector_2.11:${GEOTRELLIS_VERSION}
export SPARK_PACKAGES=$SPARK_PACKAGES,org.locationtech.geotrellis:geotrellis-spark_2.11:${GEOTRELLIS_VERSION}
export SPARK_PACKAGES=$SPARK_PACKAGES,org.locationtech.geotrellis:geotrellis-proj4_2.11:${GEOTRELLIS_VERSION}
TERM=xterm-color spark-shell --packages $SPARK_PACKAGES --driver-memory 8g --executor-memory 8g --master local[2] \
     --conf "spark.hadoop.fs.s3a.endpoint"="http://127.0.0.1:9000/" \
     --conf "spark.hadoop.fs.s3a.impl"="org.apache.hadoop.fs.s3a.S3AFileSystem" \
     --conf "spark.hadoop.fs.s3a.access.key"="minio" \
     --conf "spark.hadoop.fs.s3a.secret.key"="minio123" \
     --conf "spark.hadoop.fs.s3a.path.style.access"="true"

