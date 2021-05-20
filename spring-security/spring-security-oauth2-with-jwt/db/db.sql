create database if not exists `oauth2-with-jwt` default character set utf8mb4 collate utf8mb4_general_ci;

use `oauth2-with-jwt`;

create table if not exists `oauth2_client_details` (
    `id` int primary key not null auto_increment,
    `clientId` varchar(32) not null unique,
    `clientSecret` varchar(256) not null,
    `resourceIds` varchar(1024) default null,
    `scopes` varchar(1024) default null,
    `authorizedGrantTypes` varchar(256) not null,
    `redirectUris` varchar(1024) default null,
    `authorities` varchar(1024) default null,
    `createTime` datetime not null
) engine=innodb default charset=utf8mb4 collate=utf8mb4_general_ci comment '客户端详情';

/* 默认密码是123456 */
insert into `oauth2_client_details`(`clientId`,`clientSecret`,`resourceIds`,`scopes`,`authorizedGrantTypes`,`redirectUris`,`authorities`,`createTime`)
select 'client1','$2a$10$9mlIDTPwZOBRfsro83aEdOdTibF12s/u9n5GdOqvn3W3etbGdnCdS',NULL,'all','authorization_code,implicit','https://www.baidu.com',NULL,now() from dual where not exists(
    select id from `oauth2_client_details` where `clientId`='client1'
);
insert into `oauth2_client_details`(`clientId`,`clientSecret`,`resourceIds`,`scopes`,`authorizedGrantTypes`,`redirectUris`,`authorities`,`createTime`)
select 'client2','$2a$10$9mlIDTPwZOBRfsro83aEdOdTibF12s/u9n5GdOqvn3W3etbGdnCdS',NULL,'write','password',NULL,NULL,now() from dual where not exists(
    select id from `oauth2_client_details` where `clientId`='client2'
);
insert into `oauth2_client_details`(`clientId`,`clientSecret`,`resourceIds`,`scopes`,`authorizedGrantTypes`,`redirectUris`,`authorities`,`createTime`)
select 'client3','$2a$10$9mlIDTPwZOBRfsro83aEdOdTibF12s/u9n5GdOqvn3W3etbGdnCdS',NULL,'all','client_credentials',NULL,NULL,now() from dual where not exists(
    select id from `oauth2_client_details` where `clientId`='client3'
);
insert into `oauth2_client_details`(`clientId`,`clientSecret`,`resourceIds`,`scopes`,`authorizedGrantTypes`,`redirectUris`,`authorities`,`createTime`)
select 'order-service-resource','$2a$10$9mlIDTPwZOBRfsro83aEdOdTibF12s/u9n5GdOqvn3W3etbGdnCdS',NULL,'all','client_credentials',NULL,NULL,now() from dual where not exists(
    select id from `oauth2_client_details` where `clientId`='order-service-resource'
);

create table if not exists `user` (
    `id` bigint primary key not null auto_increment,
    `username` varchar(128) not null unique,
    `password` varchar(512) not null,
    `authorities` varchar(1024) not null,
    `createTime` datetime not null
) engine=innodb default charset=utf8mb4 collate=utf8mb4_general_ci comment '用户';

insert into `user`(`username`,`password`,`authorities`,`createTime`)
select 'user1','$2a$10$9mlIDTPwZOBRfsro83aEdOdTibF12s/u9n5GdOqvn3W3etbGdnCdS','sys:admin',now() from dual where not exists(
    select id from `user` where `username`='user1'
);
insert into `user`(`username`,`password`,`authorities`,`createTime`)
select 'user2','$2a$10$9mlIDTPwZOBRfsro83aEdOdTibF12s/u9n5GdOqvn3W3etbGdnCdS','sys:nothing',now() from dual where not exists(
    select id from `user` where `username`='user2'
);