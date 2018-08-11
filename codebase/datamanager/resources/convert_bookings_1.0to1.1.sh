#!/bin/bash
# Converts .booking files from version 1.0 to .booking files in version 1.1 and later
# Background: Change of serialization from Gson to Jackson Json library
# Version for Mac
for file in *.booking
do
  sed -i.f10 -e 's/{"year":/\[/g' $file
  sed -i.f10 -e 's/,"month":/,/g' $file
  sed -i.f10 -e 's/,"day":/,/g' $file
  sed -i.f10 -e 's/},"sta/\],"sta/g' $file
  sed -i.f10 -e 's/{"hour":/\[/g' $file
  sed -i.f10 -e 's/,"minute":/,/g' $file
  sed -i.f10 -e 's/,"second":0,"nano":0}/\]/g' $file
done
