# Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
#
# Licensed under MIT license
#
# SPDX-License-Identifier: MIT

FROM openjdk:11-jre
VOLUME /var/ptm
COPY ptm_rest-1.3.jar app.jar
ENTRYPOINT ["java","-Dptm.filestore=/var/ptm", "-jar","/app.jar"]
