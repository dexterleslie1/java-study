CREATE DATABASE `txlcn-demo`;

USE `txlcn-demo`;

DROP TABLE IF EXISTS `t_demo`;
CREATE TABLE `t_demo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data` varchar(128) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;