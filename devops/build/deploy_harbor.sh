#!/usr/bin/env bash
set -e
TAG=$(date +%Y%m%d%H%M)
echo "TAG=$TAG"
echo "user=$HARBOR_USER"
docker login ${HARBOR_REGISTRY} -u ${HARBOR_USER} -p ${HARBOR_TOKEN}
docker build -t ${HARBOR_REGISTRY}/${HARBOR_NAMESPACE}/xs2a-adapter:${TAG} .
docker push ${HARBOR_REGISTRY}/${HARBOR_NAMESPACE}/xs2a-adapter:${TAG}
