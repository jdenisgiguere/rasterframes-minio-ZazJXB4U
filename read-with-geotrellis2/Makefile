all:
	sbt assembly
	cp target/scala-2.11/read-with-geotrellis2-ZazJXB4U-assembly-0.1.jar docker-context
	docker-compose build
