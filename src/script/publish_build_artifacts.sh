#!/usr/bin/env bash

cd $TRAVIS_BUILD_DIR
mvn source:jar javadoc:jar deploy --settings settings.xml

