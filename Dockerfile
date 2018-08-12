#
# Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
#
# Licensed under MIT license
#
FROM openjdk:8-jre-alpine
VOLUME /var/ptm
COPY codebase/ptm_rest/target/*.jar app.jar
ENTRYPOINT ["java","-Dptm.filestore=/var/ptm", "-jar","/app.jar"]
