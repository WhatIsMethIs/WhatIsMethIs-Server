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
-- Table structure for table `medication`
--

DROP TABLE IF EXISTS `medication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medication` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `medicine_image` varchar(255) DEFAULT NULL,
  `medicine_name` varchar(255) NOT NULL,
  `notification_time` time(6) DEFAULT NULL,
  `take_before_after` enum('AFTER','BEFORE') NOT NULL,
  `take_capacity` int NOT NULL,
  `take_cycle` int NOT NULL,
  `take_end_date` date NOT NULL,
  `take_meal_time` enum('BREAKFAST','DINNER','LUNCH') NOT NULL,
  `take_start_date` date NOT NULL,
  `medicine_id` varchar(10) NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs4lsm0dbi69nrddjsdtedh0tv` (`medicine_id`),
  KEY `FK84e35egwwmf59ufn9q1r95ycd` (`user_id`),
  CONSTRAINT `FK84e35egwwmf59ufn9q1r95ycd` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKs4lsm0dbi69nrddjsdtedh0tv` FOREIGN KEY (`medicine_id`) REFERENCES `medicine` (`item_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medication`
--

LOCK TABLES `medication` WRITE;
/*!40000 ALTER TABLE `medication` DISABLE KEYS */;
INSERT INTO `medication` VALUES (30,'매일 점심 식후에 먹어야 하는 약!','https://nedrug.mfds.go.kr/pbp/cmn/itemImageDownload/1NOwp2F66kj','타치온정50밀리그램(글루타티온(환원형))','19:20:10.000000','AFTER',2,1,'2023-09-24','LUNCH','2023-09-03','197100015',4),(31,'2일에 한 번씩 아침 식후에 먹어야 하는 약!',NULL,'활명수','08:20:10.000000','BEFORE',1,2,'2023-09-10','BREAKFAST','2023-09-06','195700020',4),(32,'저녁에 먹기','https://nedrug.mfds.go.kr/pbp/cmn/itemImageDownload/152035092098000085','아네모정','19:20:10.000000','BEFORE',1,1,'2023-09-10','DINNER','2023-09-08','195900043',4),(33,'아침에 먹기',NULL,'겔포스현탁액(인산알루미늄겔)','19:20:10.000000','AFTER',3,1,'2023-09-10','BREAKFAST','2023-09-08','197400207',4),(34,'점심에 먹기',NULL,'일양노이겔현탁액(규산알루민산마그네슘)','16:20:00.000000','BEFORE',2,1,'2023-09-11','LUNCH','2023-09-08','197700189',4),(37,'점심 식전에 먹기','https://nedrug.mfds.go.kr/pbp/cmn/itemImageDownload/148609556259100153','페니라민정(클로르페니라민말레산염)','12:20:00.000000','BEFORE',4,3,'2023-09-18','LUNCH','2023-09-08','196000011',4),(42,'햇빛 대신 비타민 D를 꼭꼭 챙겨먹자.',NULL,'비타민 D','20:05:00.000000','AFTER',1,1,'2023-09-11','DINNER','2023-09-07','0',4);
/*!40000 ALTER TABLE `medication` ENABLE KEYS */;
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

-- Dump completed on 2023-10-23 13:10:31
