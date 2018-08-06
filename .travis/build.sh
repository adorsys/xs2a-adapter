#!/usr/bin/env bash
set -e

mvn --settings .travis/settings.xml clean package -DUGLY_KEYSTORE_CACHE -B -V
