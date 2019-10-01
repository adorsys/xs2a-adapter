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

# push latest to openshift dev env
if [ "$1" == "develop" ]; then
  docker login -u image-pusher -p $OPENSHIFT_TOKEN $OPENSHIFT_REGISTRY
  docker build -t "$OPENSHIFT_IMAGE_NAME:develop" .
  docker push $OPENSHIFT_IMAGE_NAME:develop
  mvn sonar:sonar -Dsonar.host.url=$SONAR_HOST -Dsonar.login=$SONAR_TOKEN
# push latest to opensift integ env
elif [ "$1" == "integ" ]; then
  docker login -u builder -p $OPENSHIFT_TOKEN_INTEG $OPENSHIFT_REGISTRY
  docker build -t "$OPENSHIFT_IMAGE_NAME_INTEG:develop" .
  docker push $OPENSHIFT_IMAGE_NAME_INTEG:develop
  # push tags to dockerhub
elif [ "$1" == "banking-gateway-dev" ]; then
  docker login -u github-image-pusher -p $OPENSHIFT_TOKEN_BGT_DEV $OPENSHIFT_REGISTRY
  docker build -t "$OPENSHIFT_IMAGE_NAME_BGT_DEV:latest" .
  docker push $OPENSHIFT_IMAGE_NAME_BGT_DEV:latest
  # push tags to dockerhub
elif checkSemver $(git2dockerTag $1); then
  echo $GPG_SECRET_KEY | base64 --decode | $GPG_EXECUTABLE --import || true
  echo $GPG_OWNERTRUST | base64 --decode | $GPG_EXECUTABLE --import-ownertrust || true
  mvn --settings scripts/mvn-release-settings.xml -Prelease -DskipTests -B -U deploy
# but nothing else
else
  echo "ERROR We only deploy 'develop' or release tags ('v1.2.3') but got '$1'" 1>&2
  exit 1
fi
