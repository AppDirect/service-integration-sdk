#!/bin/bash

sed '/<\/servers>/{ 
  r servers
  a \</servers>
  d 
}' mavenSettings | sed '/<\/profiles>/{ 
  r profiles
  a \</profiles>
  d 
}' | sed -e "s/GPG_PASSPHRASE/$GPG_PASSPHRASE/" -e "s/OSSRH_USERNAME/$OSSRH_USERNAME/" -e "s/OSSRH_PASSWORD/$OSSRH_PASSWORD/" | > settings.xml
