FROM openjdk:8-jre

ARG jarFile
ADD target/${jarFile} /usr/share/yyda/app.jar

ENV JAVA_OPTS=""

ENTRYPOINT java ${JAVA_OPTS} -jar /usr/share/yyda/app.jar