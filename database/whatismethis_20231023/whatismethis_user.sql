CREATE DATABASE  IF NOT EXISTS `whatismethis` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `whatismethis`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: database-whatismethis.cjhnbi27czq0.ap-northeast-2.rds.amazonaws.com    Database: whatismethis
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '';

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '고유 ID',
  `email` varchar(100) NOT NULL COMMENT '이메일 주소',
  `password` varchar(200) DEFAULT NULL COMMENT '비밀번호',
  `name` varchar(10) NOT NULL COMMENT '성명',
  `age` varchar(4) NOT NULL COMMENT '나이',
  `phone_number` varchar(20) NOT NULL COMMENT '휴대폰 번호',
  `login_code` varchar(6) NOT NULL COMMENT '''email'' / ''kakao'' / ''apple''',
  `emergency_contact1` varchar(20) DEFAULT NULL COMMENT '비상연락망 1',
  `emergency_contact2` varchar(20) DEFAULT NULL COMMENT '비상연락망 2',
  `emergency_contact3` varchar(255) DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
  `refresh_token` varchar(255) DEFAULT NULL,
  `device_token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='유저';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'dlwlgh1254@gmail.com','yPpUn2DZLXsfJbl2FEzCMA==','이지호','23','010-7211-0860','email',NULL,NULL,NULL,'2023-08-02 05:24:42','2023-08-05 12:20:12',NULL,NULL),(3,'dlwlgh1254@gmail.com','yPpUn2DZLXsfJbl2FEzCMA==','이지호','23','010-7211-1860','email',NULL,NULL,NULL,'2023-08-02 06:04:21','2023-08-05 12:20:12',NULL,NULL),(4,'email@email.com','OaXEMQtxGTqvJcLsjXqmig==','정지수','20','010-0000-0000','email',NULL,'010-3333-1004',NULL,'2023-08-30 09:21:13','2023-09-06 16:21:53','eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWQiOjQsImlhdCI6MTY5MzQ2ODYzNCwiZXhwIjoxNjk1MTc3MzAyfQ.fa9oWHKQ94Qs7ypGsmghV2J1wnkX_q5jip4tlP9Ucjo',NULL),(7,'email4@email.com','OaXEMQtxGTqvJcLsjXqmig==','4번 보호자','44','010-4444-4444','email',NULL,NULL,NULL,'2023-09-03 06:55:17','2023-09-06 09:44:10','eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWQiOjcsImlhdCI6MTY5MzcyNDExNywiZXhwIjoxNjk1NDMyNzg1fQ.DQ8WT-rxmVgCeZzjNJzbmgEp-KP9JQQMTaHWTG10ImI',NULL),(8,'email5@email.com','OaXEMQtxGTqvJcLsjXqmig==','5번 보호자','44','010-5555-5555','email',NULL,NULL,NULL,'2023-09-03 06:55:27','2023-09-06 09:44:10','eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWQiOjgsImlhdCI6MTY5MzcyNDEyNywiZXhwIjoxNjk1NDMyNzk1fQ.W3h7HbTuOQ4F5DV6nABtjTnat9q14sBqSAafK-QNaGs',NULL),(9,'email6@email.com','OaXEMQtxGTqvJcLsjXqmig==','6번 보호자','44','010-6666-6666','email',NULL,NULL,NULL,'2023-09-03 14:42:33','2023-09-06 09:44:10','eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWQiOjksImlhdCI6MTY5Mzc1MjE1MywiZXhwIjoxNjk1NDYwODIxfQ.MCWvkE67o69lFt4i1uObk7oP6W-nD4chDQg8oYQ-n8Y',NULL),(10,'jisumom@email.com','OaXEMQtxGTqvJcLsjXqmig==','지수보호자1','44','010-1111-1234','email',NULL,NULL,NULL,'2023-09-06 08:02:00','2023-09-06 09:44:10','eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWQiOjEwLCJpYXQiOjE2OTM5ODczMjAsImV4cCI6MTY5NTY5NTk4OH0.AuaQXjTa_eddS5x787_7LQcCXVbcKQ9rw9wvocaHprw',NULL),(11,'ilovejisu@email.com','OaXEMQtxGTqvJcLsjXqmig==','지수보호자2','44','010-2222-5678','email',NULL,NULL,NULL,'2023-09-06 08:02:59','2023-09-06 09:44:10','eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWQiOjExLCJpYXQiOjE2OTM5ODczNzksImV4cCI6MTY5NTY5NjA0N30.50myQtMIVbgl6OVvLBfjYSxOeznXfI7W0Yvcj3tsYwA',NULL),(12,'ilovejisu2@email.com','OaXEMQtxGTqvJcLsjXqmig==','지수보호자3','44','010-3333-1004','email',NULL,NULL,NULL,'2023-09-06 08:03:17','2023-09-06 09:44:10','eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWQiOjEyLCJpYXQiOjE2OTM5ODczOTcsImV4cCI6MTY5NTY5NjA2NX0.0vpZsK9dWKHMPC0Ww2SPNtTzSZMvx8WQ7ZMCoBkb7z4',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-23 13:10:30
