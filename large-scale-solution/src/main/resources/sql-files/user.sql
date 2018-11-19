create table if not exists t_user(
  id bigint primary key auto_increment,
  nickname varchar(100) not null,
  phone varchar(50) not null,
  password varchar(50) not null,
  sex tinyint not null default 0,/*0、未知 1、男  2、女*/
  createTime datetime not null,
  ticket varchar(50),
  email varchar(100),/*邮箱，用于找回密码时发送密码重置连接到邮箱*/
  avatar mediumBlob,/*用户小头像*/
  largeAvatar mediumBlob,/*用户大头像*/
  avatarVersion int default 0,/*头像版本号，每上传一次头像自动更新头像版本加1*/
  loginname varchar(50) not null,/*登录名称*/
  isLoginnameReset int default 0/*是否已设置登录名*/
)engine=InnoDB default charset=utf8
partition by hash(id) partitions 500 $$

