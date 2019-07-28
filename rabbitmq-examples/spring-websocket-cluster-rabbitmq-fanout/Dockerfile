FROM flyceek/centos7-jdk

RUN localedef -c -f UTF-8 -i zh_CN zh_CN.utf8
ENV LANG zh_CN.utf8

RUN rm -f /etc/localtime && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

COPY bootstrap.jar /usr/local/

EXPOSE 8080
EXPOSE 19000

CMD java \
	-Djava.rmi.server.hostname=$hostip \
        -Dcom.sun.management.jmxremote \
        -Dcom.sun.management.jmxremote.port=19000 \
        -Dcom.sun.management.jmxremote.rmi.port=19000 \
        -Dcom.sun.management.jmxremote.ssl=false \
        -Dcom.sun.management.jmxremote.authenticate=false \
        -Dcom.sun.management.jmxremote.local.only=false \
        -server \
        -jar \
	/usr/local/bootstrap.jar
