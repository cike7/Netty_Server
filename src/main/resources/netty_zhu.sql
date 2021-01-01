/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE IF NOT EXISTS `netty_zhu` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `netty_zhu`;

CREATE TABLE IF NOT EXISTS `chat_record` (
  `hostId` int(6) NOT NULL,
  `guestId` int(6) NOT NULL,
  `type` int(2) DEFAULT NULL,
  `message` text,
  `regtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  KEY `index1` (`hostId`,`guestId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='聊天记录';

/*!40000 ALTER TABLE `chat_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat_record` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `relation` (
  `hostId` int(6) NOT NULL COMMENT '用户id',
  `guestId` int(6) NOT NULL COMMENT '好友id',
  `active` int(2) DEFAULT NULL COMMENT '在线状态',
  `attest` int(2) DEFAULT NULL COMMENT '认证状态',
  `remarks` text COMMENT '备注',
  KEY `Index 1` (`hostId`,`guestId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='通讯录';

/*!40000 ALTER TABLE `relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `relation` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `account` char(20) NOT NULL COMMENT '账号',
  `password` char(12) NOT NULL COMMENT '密码',
  `name` text NOT NULL COMMENT '名字',
  `phone` int(15) DEFAULT NULL COMMENT '手机',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `sex` int(3) DEFAULT NULL COMMENT '性别',
  `address` text COMMENT '地区',
  `sign` text COMMENT '签名',
  `regtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  PRIMARY KEY (`id`),
  KEY `Index 2` (`account`,`password`)
) ENGINE=InnoDB AUTO_INCREMENT=11565 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息';

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
REPLACE INTO `user` (`id`, `account`, `password`, `name`, `phone`, `age`, `sex`, `address`, `sign`, `regtime`) VALUES
	(10001, '10001', '123', '12', 123, 20, 1, '151', '45', '2020-10-09 20:34:02'),
	(11563, '10002', '123', '13', NULL, NULL, NULL, NULL, NULL, '2020-10-09 20:34:16'),
	(11564, '10003', '123', '14', NULL, NULL, NULL, NULL, NULL, '2020-10-09 20:48:38');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
