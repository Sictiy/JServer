DROP TABLE IF EXISTS `u_item`;
CREATE TABLE IF NOT EXISTS `u_item`
(
    `id`         BIGINT(22) NOT NULL DEFAULT 0 COMMENT '主键',
    `userId`     BIGINT(22) NOT NULL DEFAULT 0,
    `tempId`     INT(11)    NOT NULL DEFAULT 0 COMMENT '模板id',
    `count`      INT(11)    NOT NULL DEFAULT 0 COMMENT '数量',
    `index`      INT(11)    NOT NULL DEFAULT 0 COMMENT '位置',
    `expireTime` DATE                DEFAULT NULL COMMENT '过期时间',
    `exist`      TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否存在',
    PRIMARY KEY (`id`),
    KEY (`userId`)
) ENGINE = innoDB
  DEFAULT CHARSET = utf8 COMMENT = '背包物品'