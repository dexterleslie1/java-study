java -jar chat-server.jar --chatVariableInternalRedisIp=192.168.1.223 --chatVariableInternalRedisPort=6379 --chatVariableInternalRedisPassword=

部署redis、jdk
复制chat-server.jar到/usr/local/chat
复制chat-server.service到/usr/lib/systemd/system
启动systemctl start chat-server、systemctl enable chat-server