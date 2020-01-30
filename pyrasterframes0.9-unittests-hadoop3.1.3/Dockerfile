FROM jeandenisatanagraph/spark244-hadoop313:latest

RUN curl -sL --retry 3 \
    "https://github.com/locationtech/rasterframes/archive/0.9.0-RC2.tar.gz" \
    | gunzip \
    | tar -x -C /usr/

WORKDIR /usr/rasterframes-0.9.0-RC2

CMD ["sbt", "pyTest"]
