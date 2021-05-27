FROM openjdk:8-jre

# com.spotify.dockerfile-maven-plugin插件传递过来的参数
ARG jarFile
ADD target/${jarFile} /usr/share/com.future.demo/app.jar

ENTRYPOINT ["java", "-jar", "/usr/share/com.future.demo/app.jar"]