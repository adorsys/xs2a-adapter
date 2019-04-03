#!/bin/bash

function git2dockerTag {
  case $1 in
    v*)
      stripVersionPrefix $1
      ;;
    *)
      $1
      ;;
  esac
}

# remove leading 'v'
function stripVersionPrefix {
  echo ${1#*v};
}

# Returns snapshot version as text
function snapshotVersion {
  echo "$1-SNAPSHOT"
}

# append 'v' to version
function releaseTag {
  echo "v$1"
}

# match input vs semver regex
function checkSemver {
  echo "DEBUG check if $1 follows the semver format" 1>&2
  [[ $1 =~ ^([[:digit:]])+\.([[:digit:]])+.([[:digit:]])(-([^+[:space:]]+))?(\+([^[:space:]]+))?$ ]]
  return $?
}