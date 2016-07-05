#!/usr/bin/env bash
set -e

export GRADLE_OPTS=-Dorg.gradle.native=false
version=`cat version/number`
cd sample-spring-cloud-svc-repo
#echo $version
gradle assemble -Pversion=$version
#ls build/libs/
