#!/bin/bash

sed '/<\/profiles>/{ 
  r src/script/gpgprofile
  a \</profiles>
  d 
}' mavenSettings | sed -e "s/GPG_KEY/$GPG_KEY/" > settings.xml
