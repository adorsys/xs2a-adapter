#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

# Variables
SOURCE_DIR="docs"
DESTINATION_DIR="tmp"
# Plantuml needs an absolute path for saving generated files
PUML_DESTINATION="$(pwd)/${DESTINATION_DIR}/images"
PUML_SOURCE="${SOURCE_DIR}/**.puml"
ADOC_SOURCE_FILE="${SOURCE_DIR}/arc42/template.adoc"
ADOC_DESTINATION_FILE="${DESTINATION_DIR}/index.html"
ADOC_SOURCE_FOLDER="${SOURCE_DIR}/xs2a_flows/*.adoc"
ADOC_DESTINATION_FOLDER="${DESTINATION_DIR}"
ADOC_WIREMOCK_SOURCE_FILE="${SOURCE_DIR}/wiremock-mode.adoc"
ADOC_WIREMOCK_DESTINATION_FILE="${DESTINATION_DIR}/wiremock-mode.html"

sudo apt-get update
# Plantuml dependency
sudo apt-get install -y graphviz
sudo apt-get install -y asciidoctor
wget -O /tmp/plantuml.jar "http://sourceforge.net/projects/plantuml/files/plantuml.jar/download"

# convert .puml into .png
java -jar /tmp/plantuml.jar -v -tpng -o ${PUML_DESTINATION} ${PUML_SOURCE}

# convert .adoc into .html
# arc42
asciidoctor -o ${ADOC_DESTINATION_FILE} ${ADOC_SOURCE_FILE}
# xs2a_flows
asciidoctor -D ${ADOC_DESTINATION_FOLDER} ${ADOC_SOURCE_FOLDER}
#wiremock mode doc
asciidoctor -o ${ADOC_WIREMOCK_DESTINATION_FILE} ${ADOC_WIREMOCK_SOURCE_FILE}

# copy all existing images
cp docs/arc42/images/* ${PUML_DESTINATION}
