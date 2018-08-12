#
# Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
#
# Licensed under MIT license
#
FROM openjdk:8-jre-alpine
VOLUME /var/ptm
COPY ptm_rest-1.1.0.jar app.jar
ENTRYPOINT ["java","-Dptm.filestore=/var/ptm", "-jar","/app.jar"]
