#!/usr/bin/env bash
set -x

target="cf api $API_ENDPOINT --skip-ssl-validation"
#echo $target
eval $target

echo "Login....."
login="cf auth $USERNAME $PASSWORD"
#echo $login
eval $login

echo "Target Org and Space"
org_space="cf target -o $ORG -s $SPACE"
eval $org_space

echo "push the app"
push="cf push sample-spring-cloud-svc -n $HOST -p sample-spring-cloud-svc-release/sample-spring-cloud-svc-ci-*.jar -m 512m"
#echo $push
eval $push
