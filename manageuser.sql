-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.1.38-community - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for manageuser_luuthanhsang
DROP DATABASE IF EXISTS `manageuser_luuthanhsang`;
CREATE DATABASE IF NOT EXISTS `manageuser_luuthanhsang` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `manageuser_luuthanhsang`;

-- Dumping structure for table manageuser_luuthanhsang.mst_group
DROP TABLE IF EXISTS `mst_group`;
CREATE TABLE IF NOT EXISTS `mst_group` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(255) NOT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Dumping data for table manageuser_luuthanhsang.mst_group: ~4 rows (approximately)
/*!40000 ALTER TABLE `mst_group` DISABLE KEYS */;
INSERT INTO `mst_group` (`group_id`, `group_name`) VALUES
	(1, 'Phòng phát triển số 1'),
	(2, 'Phòng phát triển số 2'),
	(3, 'Phòng phát triển số 3'),
	(4, 'Phòng phát triển số 4');
/*!40000 ALTER TABLE `mst_group` ENABLE KEYS */;

-- Dumping structure for table manageuser_luuthanhsang.mst_japan
DROP TABLE IF EXISTS `mst_japan`;
CREATE TABLE IF NOT EXISTS `mst_japan` (
  `code_level` varchar(15) NOT NULL,
  `name_level` varchar(255) NOT NULL,
  PRIMARY KEY (`code_level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table manageuser_luuthanhsang.mst_japan: ~5 rows (approximately)
/*!40000 ALTER TABLE `mst_japan` DISABLE KEYS */;
INSERT INTO `mst_japan` (`code_level`, `name_level`) VALUES
	('N1', 'Trình độ tiếng nhật cấp 1'),
	('N2', 'Trình độ tiếng nhật cấp 2'),
	('N3', 'Trình độ tiếng nhật cấp 3'),
	('N4', 'Trình độ tiếng nhật cấp 4'),
	('N5', 'Trình độ tiếng nhật cấp 5');
/*!40000 ALTER TABLE `mst_japan` ENABLE KEYS */;

-- Dumping structure for table manageuser_luuthanhsang.tbl_detail_user_japan
DROP TABLE IF EXISTS `tbl_detail_user_japan`;
CREATE TABLE IF NOT EXISTS `tbl_detail_user_japan` (
  `detail_user_japan_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `code_level` varchar(15) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `total` int(11) NOT NULL,
  PRIMARY KEY (`detail_user_japan_id`),
  KEY `user_id` (`user_id`),
  KEY `code_level` (`code_level`),
  CONSTRAINT `tbl_detail_user_japan_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`user_id`),
  CONSTRAINT `tbl_detail_user_japan_ibfk_2` FOREIGN KEY (`code_level`) REFERENCES `mst_japan` (`code_level`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- Dumping data for table manageuser_luuthanhsang.tbl_detail_user_japan: ~14 rows (approximately)
/*!40000 ALTER TABLE `tbl_detail_user_japan` DISABLE KEYS */;
INSERT INTO `tbl_detail_user_japan` (`detail_user_japan_id`, `user_id`, `code_level`, `start_date`, `end_date`, `total`) VALUES
	(2, 4, 'N3', '2005-05-20', '2006-05-20', 100),
	(5, 5, 'N5', '2005-07-08', '2006-07-08', 90),
	(6, 2, 'N1', '2005-05-20', '2006-05-20', 167),
	(8, 8, 'N3', '2017-08-02', '2017-11-02', 101),
	(9, 9, 'N3', '2017-06-03', '2017-11-19', 102),
	(10, 10, 'N4', '2017-10-11', '2018-02-04', 103),
	(11, 11, 'N4', '2017-03-30', '2018-10-28', 104),
	(12, 12, 'N4', '2016-08-28', '2018-02-28', 105),
	(14, 14, 'N5', '2015-09-28', '2016-12-28', 109),
	(18, 1, 'N1', '2005-07-24', '2006-07-08', 105),
	(23, 19, 'N4', '2003-11-23', '2012-11-23', 300),
	(27, 22, 'N5', '2017-11-28', '2018-11-28', 97),
	(28, 23, 'N5', '2017-11-29', '2018-11-29', 99),
	(29, 26, 'N5', '2017-11-30', '2018-11-30', 123);
/*!40000 ALTER TABLE `tbl_detail_user_japan` ENABLE KEYS */;

-- Dumping structure for table manageuser_luuthanhsang.tbl_user
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE IF NOT EXISTS `tbl_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `login_name` varchar(15) NOT NULL,
  `password` varchar(50) NOT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) NOT NULL,
  `full_name_kana` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `tel` varchar(15) NOT NULL,
  `birthday` date NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `group_id` (`group_id`),
  CONSTRAINT `tbl_user_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `mst_group` (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- Dumping data for table manageuser_luuthanhsang.tbl_user: ~18 rows (approximately)
/*!40000 ALTER TABLE `tbl_user` DISABLE KEYS */;
INSERT INTO `tbl_user` (`user_id`, `group_id`, `login_name`, `password`, `salt`, `full_name`, `full_name_kana`, `email`, `tel`, `birthday`) VALUES
	(1, 1, 'ntmhuong', '29b7a50e39029bf73850bd692a5428b', 'SJHFSUIGJGSDBUTFGIU', 'Nguyễn Thị Mai Hương\\', '', 'aaaa@ab.ab', '123-456-789', '1983-07-07'),
	(2, 1, 'hieudt', '6E30950CF840EDB00179E87982A4D1850E821818', 'SJHFSUIGJGSDBUTFGIU', '\' AND SELECT * ;-- --_\\\\[a-c]!%\\%_', '', 'hieu@gmali.com', '987-456-123', '1983-08-08'),
	(4, 2, 'dungdv', '123456', 'SJHFSUIGJGSDBUTFGIU', 'Đỗ Văn Dũng', '', 'dung@gmail.com', '7892-123-456', '1983-10-08'),
	(5, 3, 'phuongnv', '123456', 'SJHFSUIGJGSDBUTFGIU', 'Nguyễn Việt Phương', '', 'phuong@gmail.com', '456-987-213', '1983-11-08'),
	(8, 2, 'nva2', '61A853280E3F190E8DA6DC5BA0064C67264D6500', 'KAKSDKFLASDFKASDFK', 'Nguyễn Văn A 2', 'A2 Kana', 'a2@gmail.com', '123456789', '1995-01-02'),
	(9, 2, 'nva3', '123456', 'KAKSDKFAKSDFLASDFK', 'Nguyễn Văn A 3', '', 'a3@gmail.com', '123-456-788', '1995-01-03'),
	(10, 1, 'nva4', '123456', 'KAKKAKAKAKKAKAKAKA', 'Nguyễn Văn A 4', '', 'a4@gmail.com', '1-1-1', '1995-01-04'),
	(11, 3, 'nva5', '123456', 'KAKAKAKAKAKAKAKAKA', 'Nguyễn Văn A 5', 'A5 Kana', 'a5@gmail.com', '123456789', '1995-01-05'),
	(12, 3, 'nva6', '123456', 'KAKAKAKAKAKAKAKAKA', 'Nguyễn Văn A 6', 'A6 Kana', 'a6@gmail.com', '123456789', '1995-01-06'),
	(14, 4, 'nva8', '123456', 'KAKAKAKAKAKAKAKAKA', 'Nguyễn Văn A 8', 'A8 Kana', 'a8@gmail.com', '123456789', '1995-01-08'),
	(19, 1, 'DinhChien', '950778281f23a9ac5604416408b7e3ea', '1511407902316', 'Đinh Văn Chiến', '', 'abc@gmail.com', '888-888-888', '1994-05-09'),
	(22, 1, 'longvc', '8F489EA6F7D44560855F9CA505A329675453C0FF', '1511841079719', 'Vũ Cao Long', '', 'longvc@gmail.com', '3123-1243-12', '1997-11-28'),
	(23, 3, 'minhtrinh', '8F0BFF2D724F4FDF359190A4ED324B70A4A405CF', '1511926017797', 'Trịnh Minh', '', 'minhtr@gmail.com', '1-3-4', '1993-07-03'),
	(24, 1, 'phucminh', 'A6554F2F0F146AC44491804F2A8EBBB5BB730D', '1511928991391', 'Phúc Minh', '', 'phucminh@gmail.com', '123-144-123', '1995-04-30'),
	(25, 1, 'quanld', 'BC608B11801A7B9544439C281560488D1F4C0CC1', '1511930549064', 'Quân Long', '', 'quanld@gmail.com', '1-2-3', '1990-11-29'),
	(26, 2, 'ewrter', '9BC3C2152D8F343F48147E17869382A44249ABA', '1512006005484', 'dgfdgf', '', 'dsgfd@fd.com', '1-1-1', '2017-11-30'),
	(27, 1, 'fshgf', 'F65C06E10C81D7A3550271E7707A1225CE6BB68E', '1512006080638', 'dsgfdh', '', 'fdsf@e.com', '1-1-1', '2017-11-30'),
	(28, 1, 'hjhgk', '438A2E89E51BD92557EF15AA0AFA7BFE4AA68DCB', '1512006140797', 'jgfjhj', '', 'jgfj@rfe.ocm', '11-1-1', '2017-11-30');
/*!40000 ALTER TABLE `tbl_user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
