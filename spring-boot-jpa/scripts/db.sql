create table if not exists transaction_tracking (
  id bigint primary key auto_increment,
  trackingId varchar(64) not null,
  type varchar(32) not null,
  status varchar(10) not null,
  cause varchar(2048),
  payload varchar(2048) not null,
  createTime datetime not null
) engine=InnoDB default charset=utf8;

