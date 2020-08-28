CREATE DATABASE IF NOT EXISTS websocket_online_detection CHARACTER SET utf8 COLLATE utf8_general_ci;

USE websocket_online_detection;

create table if not exists socket_session_connected (
   id bigint primary key auto_increment,
   sessionId varchar(100) not null unique,
   instanceId varchar(100) not null,
   createTime datetime not null,
   unique(sessionId, instanceId)
) engine=InnoDB default charset=utf8;

create table if not exists socket_session_online (
  id bigint primary key auto_increment,
  sessionId varchar(100) not null unique,
  instanceId varchar(100) not null,
  latestActiveTime datetime,
  createTime datetime not null,
  unique(sessionId, instanceId)
) engine=InnoDB default charset=utf8;