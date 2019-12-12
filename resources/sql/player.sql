DROP TABLE IF EXISTS `u_player`;
CREATE TABLE IF NOT EXISTS `u_player`
(
    `userId` BIGINT(22)   NOT NULL DEFAULT 0,
    `sex`    TINYINT(1)   NOT NULL DEFAULT 0,
    `icon`   VARCHAR(255) NOT NULL DEFAULT '',
    PRIMARY KEY (`userId`)
) ENGINE = innoDB
  DEFAULT CHARSET = utf8 COMMENT = '玩家信息';