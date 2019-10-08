CREATE TABLE IF NOT EXISTS `u_unique`
(
    `type`     INT(11) NOT NULL DEFAULT 0,
    `serverId` INT(11) NOT NULL DEFAULT 0,
    `max`      INT(11) NOT NULL DEFAULT 0,
    PRIMARY KEY (`type`, `serverId`)
) ENGINE = innoDB
  DEFAULT CHARSET = utf8 COMMENT = '唯一id';