
create table StaffUser (
  id bigint(15) not null auto_increment,
  username varchar(255) not null,
  password varchar(255) not null,
  lastLoginDate datetime(3) null,
  createdBy varchar(255) not null,
  updatedBy varchar(255) null,
  createdAt timestamp(3) not null default current_timestamp(3),
  updatedAt timestamp(3) null default null on update current_timestamp(3),
  primary key (id),
  unique key uni_idx_username (username)
) engine=innodb  default charset=utf8;

