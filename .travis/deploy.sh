#!/usr/bin/env bash

set -e

# https://github.com/travis-ci/travis-build/blob/1317b0c9e96f5a56308138d869f0124de1b672b5/lib/travis/build/bash/travis_setup_env.bash#L3
export ANSI_RED="\033[31;1m"
export ANSI_GREEN="\033[32;1m"
export ANSI_YELLOW="\033[33;1m"
export ANSI_RESET="\033[0m"
export ANSI_CLEAR="\033[0K"

# https://github.com/travis-ci/travis-build/blob/4f580b238530108cdd08719c326cd571d4e7b99f/lib/travis/build/bash/travis_retry.bash
travis_retry() {
  local result=0
  local count=1
  while [[ "${count}" -le 10 ]]; do
    [[ "${result}" -ne 0 ]] && {
      echo -e "\\n${ANSI_RED}The command \"${*}\" failed. Retrying, ${count} of 10.${ANSI_RESET}\\n" >&2
    }
    "${@}" && { result=0 && break; } || result="${?}"
    count="$((count + 1))"
    sleep 1
  done

  [[ "${count}" -gt 10 ]] && {
    echo -e "\\n${ANSI_RED}The command \"${*}\" failed 10 times.${ANSI_RESET}\\n" >&2
  }

  return "${result}"
}

travis_retry mvn --settings .travis/settings.xml package gpg:sign deploy -Prelease -DskipTests -B -U
