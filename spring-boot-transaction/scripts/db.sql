drop database if exists demo_transaction;

create database if not exists demo_transaction character set utf8mb4 collate utf8mb4_unicode_ci;

use demo_transaction;

create table if not exists balance (
  id bigint primary key auto_increment,
  amount decimal(20,5) not null default 0
) engine=InnoDB character set utf8mb4 collate utf8mb4_unicode_ci;

create table if not exists balance_log (
  id bigint primary key auto_increment,
  amount decimal(20,5) not null,
  createTime datetime not null
) engine=InnoDB character set utf8mb4 collate utf8mb4_unicode_ci;