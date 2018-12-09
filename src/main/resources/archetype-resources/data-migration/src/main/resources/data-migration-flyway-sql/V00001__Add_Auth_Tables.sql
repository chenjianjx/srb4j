create table User
(
  id            bigint(15) not null auto_increment,
  principal     varchar(255) not null,
  password      varchar(255)          default null,
  source        varchar(255) not null,
  email         varchar(255) not null,
  emailVerified boolean      not null default false,
  createdBy     varchar(255) not null,
  updatedBy     varchar(255) null,
  createdAt     timestamp(3) not null default current_timestamp(3),
  updatedAt     timestamp(3) null default null on update current_timestamp (3),
  primary key (id),
  unique key uni_idx_principal (principal),
  unique key uni_idx_user_email (email),
  key           idx_user_src (source)
) engine=innodb default charset=utf8;



create table AccessToken
(
  id              bigint(15) not null auto_increment,
  tokenStr        varchar(255) not null,
  lifespan        bigint(15) not null comment 'units: second',
  expiresAt       datetime(3) not null,
  userId          bigint(15) not null,
  refreshTokenStr varchar(255) not null,
  createdBy       varchar(255) not null,
  updatedBy       varchar(255) null,
  createdAt       timestamp(3) not null default current_timestamp(3),
  updatedAt       timestamp(3) null default null on update current_timestamp (3),
  primary key (id),
  unique key uni_idx_token (tokenStr),
  unique key uni_idx_refresh_token (refreshTokenStr)
) engine=innodb default charset=utf8;



create table RandomLoginCode
(
  id        bigint(15) not null auto_increment,
  codeStr   varchar(255) not null,
  expiresAt datetime(3) not null,
  userId    bigint(15) not null,
  createdBy varchar(255) not null,
  updatedBy varchar(255) null,
  createdAt timestamp(3) not null default current_timestamp(3),
  updatedAt timestamp(3) null default null on update current_timestamp (3),
  primary key (id),
  unique key uni_random_code_uid(userId)
) engine=innodb default charset=utf8;


create table ForgetPasswordVerifyCode
(
  id        bigint(15) not null auto_increment,
  codeStr   varchar(255) not null,
  expiresAt datetime(3) not null,
  userId    bigint(15) not null,
  createdBy varchar(255) not null,
  updatedBy varchar(255) null,
  createdAt timestamp(3) not null default current_timestamp(3),
  updatedAt timestamp(3) null default null on update current_timestamp (3),
  primary key (id),
  unique key uni_forget_password_verify_code_uid(userId)
) engine=innodb default charset=utf8;



create table EmailVerificationDigest
(
  id        bigint(15) not null auto_increment,
  digestStr varchar(255) not null,
  expiresAt datetime(3) not null,
  userId    bigint(15) not null,
  createdBy varchar(255) not null,
  updatedBy varchar(255) null,
  createdAt timestamp(3) not null default current_timestamp(3),
  updatedAt timestamp(3) null default null on update current_timestamp (3),
  primary key (id),
  unique key uni_email_verification_digest_uid(userId),
  unique key uni_email_verification_digest_digest(digestStr)
) engine=innodb default charset=utf8;