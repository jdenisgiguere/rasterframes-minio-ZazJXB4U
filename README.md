# Reading Geotrellis layer stored in minio with pyrasterframes

This repo contains data and source code to document issue with reading of
a Geotrellis Layer using pyrasterframes.

## Current state

* docker-compose to serve a Geotrellis Layer trough minio (`Docker` folder)
* working exemple of reading Geotrellis Layer through minio with Geotrellis (`read-with-geotrellis2` folder)
* broken code / configuration of attempt of reading Geotrellis Layer with rasterframes 0.8.5 in scala (`read-with-rasteframes0.8.5` folder)

## Setup

### Deploy Minio server with raster data

```bash
pushd Docker
docker-compose up -d
popd
``

### Build base image of Spark 2.4.4 with Hadoop 3.1.3

```bash
pushd Docker
make
popd
```

### Read Geotrellis Layer with Geotrellis

See `read-with-geotrellis2/README.md`.

### Read Geotrellis Layer with rasterframes 0.8.5 (broken)

See `read-with-rasteframes0.8.5/README.md`.


