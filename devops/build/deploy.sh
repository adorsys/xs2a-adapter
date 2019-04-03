#!/usr/bin/env bash
set -e

SCRIPT_PATH="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# source own helper functions
if [ -f $SCRIPT_PATH/bash_functions.sh ]; then
  source $SCRIPT_PATH/bash_functions.sh
else
  echo "ERROR Could not load '$SCRIPT_PATH/bash_functions.sh'" 1>&2
  exit 1
fi

if [ $# -ne 1 ]; then
  echo 'Usage: deploy.sh <target-tag>' 1>&2
  echo 'For example: deploy.sh develop' 1>&2
  exit 2
fi

# push latest to openshift
if [ "$1" == "latest" ]; then
  docker login -u image-pusher -p $OPENSHIFT_TOKEN $OPENSHIFT_REGISTRY
  docker build -t "$OPENSHIFT_IMAGE_NAME:latest" .
  docker push $OPENSHIFT_IMAGE_NAME:latest
elif [ "$1" == "develop" ]; then
  docker login -u image-pusher -p $OPENSHIFT_TOKEN $OPENSHIFT_REGISTRY
  docker build -t "$OPENSHIFT_IMAGE_NAME:develop" .
  docker push $OPENSHIFT_IMAGE_NAME:develop
# but nothing else
else
  echo "ERROR We only deploy 'latest' but got '$1'" 1>&2
  exit 1
fi