#!/bin/bash

# Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
#
# Licensed under MIT license
#
# SPDX-License-Identifier: MIT

# Converts .booking files from version 1.1 to .booking files in version 1.2
# Background: Change of serialization of activity references in bookings
# Version for Mac
for file in ./*.booking
do
  	echo "Converting file: $file"
  	perl -p -i -e 's/"activity":\{"activityName":"[a-zA-Z0-9 _-]+","bookingNumber":"[a-zA-Z0-9 _-]+","hidden":[a-z]+,"id":([0-9]+)\}/"activity":$1/g' $file
done
