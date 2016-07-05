#!/usr/bin/env bash
set -ex

version=`cat version/number`
git tag -a $version
git push origin $version
