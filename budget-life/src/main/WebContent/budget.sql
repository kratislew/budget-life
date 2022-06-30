CREATE TABLE `userValues` (
  `uuid` char(40) COLLATE utf8_unicode_ci NOT NULL,
  `title` char(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `content` text COLLATE utf8_unicode_ci,
  `createTimestamp` date DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `logs` WRITE;
INSERT INTO `logs` VALUES ('ac299eb1-599e-4599-b22b-95e889448793','Two','Another content is 2',NULL),('d2bbd408-2836-4c96-92b2-0d44210e8502','One','One content',NULL);
UNLOCK TABLES;