#!/bin/bash

sed '/<\/settings>/{ 
  r src/script/settings
  a \</settings>
  d 
}' mavenSettings | sed -e "s/GPG_PASSPHRASE/$GPG_PASSPHRASE/" -e "s/OSSRH_USERNAME/$OSSRH_USERNAME/" -e "s/OSSRH_PASSWORD/$OSSRH_PASSWORD/" > settings.xml
