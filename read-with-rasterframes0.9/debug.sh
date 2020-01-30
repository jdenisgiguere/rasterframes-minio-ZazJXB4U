#!/bin/bash
spark-submit --conf spark.driver.extraJavaOptions=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005 target/scala-2.11/read-with-rasterframes09-ZazJXB4U-assembly-0.1.jar  --conf spark.executor.extraJavaOptions=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005

