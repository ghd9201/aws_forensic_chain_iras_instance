-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: fchaindb
-- ------------------------------------------------------
-- Server version	5.7.26-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `evidence`
--

DROP TABLE IF EXISTS `evidence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `evidence` (
  `objectId` int(11) NOT NULL AUTO_INCREMENT,
  `registerTime` datetime NOT NULL,
  `registerId` varchar(20) NOT NULL,
  `caseId` varchar(45) NOT NULL,
  `evidenceId` varchar(70) NOT NULL,
  `fileName` varchar(45) NOT NULL,
  `fileSize` int(11) NOT NULL,
  `description` varchar(255) NOT NULL,
  `hash` varchar(255) NOT NULL,
  `filePath` varchar(255) NOT NULL,
  PRIMARY KEY (`objectId`),
  UNIQUE KEY `evidenceId_UNIQUE` (`evidenceId`),
  UNIQUE KEY `hash_UNIQUE` (`hash`),
  KEY `foreign_key_rule1_idx` (`registerId`),
  CONSTRAINT `foreign_key_rule1` FOREIGN KEY (`registerId`) REFERENCES `user` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evidence`
--

LOCK TABLES `evidence` WRITE;
/*!40000 ALTER TABLE `evidence` DISABLE KEYS */;
INSERT INTO `evidence` VALUES (1,'2019-11-30 09:49:31','1234','1','2','bg5.jpg',0,'2019-형제-1-증거-2','73cabbd1c75341e29618e3c150ca010a1367b1d0171fdac24ec33b35bb3e1f6a','테스트 주석'),(3,'2019-11-30 10:01:14','1234','2','3','logo_login.png',0,'2019-형제-2-증거-3','72fa1e2e9a3a0a731e520cf1576f77eed87c48fdd17be8c8f5e92ac71560768e','테스트 주석'),(4,'2019-11-30 10:03:59','1234','2019-형제-4','2019-형제-4-증거-5','logo-single.png',0,'2019-형제-4-증거-5','3e234a48add875b2cff404f303c9254f9701d4aeb3234c23bf4f3a914df171d0','테스트 주석'),(5,'2019-11-30 10:06:00','1234','2019-형제-6','2019-형제-6-증거-7','다크웹_사진.PNG',0,'2019-형제-6-증거-7','6343bf88f36150ba239299d8198cd928ea8fa2b7b303a49532dd2dbed6b7d17d','테스트 주석'),(6,'2019-11-30 10:12:48','1234','11','113','fchain-logo.png',50532,'테스트 주석','fbb6b8d7c1b005abd2af517a8d13093c7c47cd98cf104e89f9f8772d4859185f','upload/20191130/fchain-logo.png'),(7,'2019-11-30 10:15:33','1234','23','44','Downloads.zip',8888,'테스트 주석','82687393399d2a7d4a8e68e29ec58ca1e8bcfb7025d815699e6b87b3e645355f','upload/20191130/Downloads.zip'),(8,'2019-11-30 10:18:33','1234','2019-형제-31','21','small_logo.png',2116,'테스트 주석','b1023bcfeaa246abc53e2cc0d82fdde9ebe63b6c9160a84e90dbd8e48836f46a','upload/20191130/small_logo.png');
/*!40000 ALTER TABLE `evidence` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-30 11:49:58
