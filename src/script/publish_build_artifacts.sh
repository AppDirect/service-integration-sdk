#!/usr/bin/env bash

mvn deploy --settings settings.xml && ./src/script/sync_bintray_to_maven_central.sh

