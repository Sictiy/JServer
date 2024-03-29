DROP TABLE IF EXISTS `u_level`;
CREATE TABLE IF NOT EXISTS `u_level`
(
    `id`     BIGINT(22) NOT NULL DEFAULT 0,
    `userId` BIGINT(22) NOT NULL DEFAULT 0,
    `type`   INT(11)    NOT NULL DEFAULT 0,
    `value`  INT(11)    NOT NULL DEFAULT 0,
    `level`  INT(11)    NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    KEY (`userId`)
) ENGINE = innoDB
  DEFAULT CHARSET = utf8 COMMENT = '等级'