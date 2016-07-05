#!/usr/bin/env bash
set -e

export GRADLE_OPTS=-Dorg.gradle.native=false
version=`cat version/number`
cd sample-spring-cloud-svc-repo
#echo $version
./gradlew assemble -Pversion=$version
mv build/libs/sample-spring-cloud-svc-ci-*.jar ../build/
#ls build/libs/
