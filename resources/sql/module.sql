DROP TABLE IF EXISTS `u_module`;
CREATE TABLE IF NOT EXISTS `u_module`
(
    `id`         BIGINT(22) NOT NULL DEFAULT 0,
    `userId`     BIGINT(22) NOT NULL DEFAULT 0,
    `moduleType` INT(11)    NOT NULL DEFAULT 0,
    `isOpen`     TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE = innoDB
  DEFAULT CHAR SET = utf8 COMMENT ='模块开启'