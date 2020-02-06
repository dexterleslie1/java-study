FROM openjdk:8-jre

# Add Maven dependencies (not shaded into the artifact; Docker-cached)
# ADD target/lib           /usr/share/myservice/lib
# Add the service itself
ARG jarFile
ADD target/${jarFile} /usr/share/com.future.demo/dockerfile-maven-plugin.jar

ENTRYPOINT ["java", "-jar", "/usr/share/com.future.demo/dockerfile-maven-plugin.jar"]