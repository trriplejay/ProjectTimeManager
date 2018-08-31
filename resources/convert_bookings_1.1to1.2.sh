#!/bin/bash
# Converts .booking files from version 1.1 to .booking files in version 1.2
# Background: Change of serialization of activity references in bookings
# Version for Mac
for file in ./*.booking
do
  perl -p -i -e 's/"activity":\{"activityName":"[a-zA-Z0-9 _-]+","bookingNumber":"[a-zA-Z0-9 _-]+","id":([0-9]+)\}/"activity":$1/g' $file
done
