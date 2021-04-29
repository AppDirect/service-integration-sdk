#!/bin/bash

sed '/<\/settings>/{ 
  r src/script/gpgprofile
  a \</settings>
  d 
}' mavenSettings | sed -e "s/GPG_KEY/$GPG_KEY/" > settings.xml
