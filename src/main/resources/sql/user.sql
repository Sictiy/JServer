CREATE TABLE IF NOT  EXISTS `u_user`(
    `userId` BIGINT(22) NOT NULL DEFAULT 0,
    `password` varchar(32) NOT NULL DEFAULT "",
    `userName` varchar(255) NOT NULL DEFAULT "",
    `createDate` DATE,
    PRIMARY KEY (`userId`)
) ENGINE=innoDB DEFAULT CHARSET=utf8;