#!/usr/bin/env bash

cd $TRAVIS_BUILD_DIR 

#Note: All environment variables not defined in this build are assumed to be already present. Check your Travis build settings
export BINTRAY_REPOSITORY_NAME="maven"
export BINTRAY_PACKAGE_NAME="service-integration-sdk"
export BINTRAY_SUBJECT="appdirect-opensource"
export BINTRAY_PACKAGE_VERSION=$(mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version | grep -v "^\[" | grep -v "Listening")

echo "Publishing build artifacts to Bintray..."
mvn source:jar javadoc:jar deploy --settings settings.xml

echo "Syncing project version ${BINTRAY_PACKAGE_VERSION} with maven central ..."
export BINTRAY_REQUEST_URL="https://api.bintray.com/maven_central_sync/${BINTRAY_SUBJECT}/${BINTRAY_REPOSITORY_NAME}/${BINTRAY_PACKAGE_NAME}/versions/${BINTRAY_PACKAGE_VERSION}"
echo "Bintray request URL: [$BINTRAY_REQUEST_URL]"
curl -i \
	--user ${CI_DEPLOY_USERNAME}:${CI_DEPLOY_PASSWORD} \
	--header "Accept: application/json" \
	--header "Content-Type: application/json" \
	--request POST \
	--data '{"username": "'${SONAR_USER}'", "password": "'${SONAR_USER_PASSWORD}'", "close": "1"}' \
	${BINTRAY_REQUEST_URL}


