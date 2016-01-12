drop table if exists User;
create table User (
  id bigint(15) not null auto_increment,
  principal varchar(255) not null,
  password varchar(255) default null,
  source varchar(255) not null,
  email varchar(255) not null,
  createdBy varchar(255) not null,
  updatedBy varchar(255) null,
  createdAt timestamp not null,
  updatedAt timestamp null,
  primary key (id),
  unique key uni_idx_principal (principal),
  key idx_user_src (source)
) engine=innodb  default charset=utf8;
 

drop table if exists AccessToken;
create table AccessToken (
  id bigint(15) not null auto_increment,
  tokenStr varchar(255) not null, 
  lifespan bigint(15) not null comment 'units: second' , 
  expiresAt datetime not null,
  userId bigint(15) not null,
  refreshTokenStr varchar(255) not null, 
  createdBy varchar(255) not null,
  updatedBy varchar(255) null,
  createdAt timestamp not null,
  updatedAt timestamp null ,
   primary key (id),
   unique key uni_idx_token (tokenStr),
   unique key uni_idx_refresh_token (refreshTokenStr)
) engine=innodb  default charset=utf8;


drop table if exists RandomLoginCode;
create table RandomLoginCode (
  id bigint(15) not null auto_increment,
  codeStr varchar(255) not null, 
  expiresAt datetime not null,
  userId bigint(15) not null,
  createdBy varchar(255) not null,
  updatedBy varchar(255) null,
  createdAt timestamp not null,
  updatedAt timestamp null ,
  primary key (id),
  unique key uni_uid(userId)
) engine=innodb  default charset=utf8;



drop table if exists Post;
create table Post (
  id bigint(15) not null auto_increment,
  userId bigint(15) not null,
  content varchar(500) not null, 
  createdBy varchar(255) not null,
  updatedBy varchar(255) null,
  createdAt timestamp not null,
  updatedAt timestamp null ,
  primary key (id)
) engine=innodb  default charset=utf8;
