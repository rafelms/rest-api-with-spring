CREATE TABLE IF NOT EXISTS `books` (
     `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `title` varchar(80) NOT NULL,
    `type` varchar(20) NOT NULL,
    `author` varchar(80) NOT NULL,
    PRIMARY KEY (`id`)
    );