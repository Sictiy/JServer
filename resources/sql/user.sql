DROP TABLE IF EXISTS `u_user`;
CREATE TABLE IF NOT EXISTS `u_user`
(
    `userId`     BIGINT(22)   NOT NULL DEFAULT 0 COMMENT 'id',
    `password`   varchar(32)  NOT NULL DEFAULT "" COMMENT '密码',
    `userName`   varchar(255) NOT NULL DEFAULT "" COMMENT '昵称',
    `createDate` DATE                  DEFAULT NULL COMMENT '创建日期',
    PRIMARY KEY (`userId`)
) ENGINE = innoDB
  DEFAULT CHARSET = utf8 COMMENT = '用户信息';