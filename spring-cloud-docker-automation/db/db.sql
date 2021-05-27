create database if not exists demo_docker_automation default character set utf8mb4 collate utf8mb4_general_ci;

use demo_docker_automation;

create table if not exists docker_automation_product(
    id bigint primary key auto_increment,
    name varchar(512) not null,
    createTime datetime not null
) engine=innodb default charset=utf8mb4 collate=utf8mb4_general_ci comment '商品';
