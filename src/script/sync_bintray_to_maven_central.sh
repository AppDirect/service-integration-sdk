#!/usr/bin/env bash

#Note: All environment variables not defined in this build are assumed to be already present. Check your Travis build settings
export BINTRAY_REPOSITORY_NAME="maven"
export BINTRAY_PACKAGE_NAME="service-integration-sdk"
export BINTRAY_PACKAGE_VERSION=$(mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version | grep -v "^\[" | grep -v "Listening")

curl -i \
	--user $CI_DEPLOY_USERNAME:$CI_DEPLOY_PASSWORD \
	--header "Accept: application/json" \
	--header "Content-Type: application/json" \
	--request POST \
	--data '{"username": "'$SONAR_USER'", "password": "'$SONAR_USER_PASSWORD'", "close": "1"}' \
	https://api.bintray.com/maven_central_sync/$CI_DEPLOY_USERNAME/$BINTRAY_REPOSITORY_NAME/$BINTRAY_PACKAGE_NAME/versions/$BINTRAY_PACKAGE_VERSION
