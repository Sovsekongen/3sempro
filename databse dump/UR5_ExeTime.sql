-- MySQL dump 10.13  Distrib 5.7.24, for Linux (x86_64)
--
-- Host: localhost    Database: UR5
-- ------------------------------------------------------
-- Server version	5.7.24-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ExeTime`
--

DROP TABLE IF EXISTS `ExeTime`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ExeTime` (
  `throwNr` int(11) NOT NULL,
  `openCVTime` int(11) DEFAULT NULL,
  `pickUpTime` int(11) DEFAULT NULL,
  `throwTime` int(11) DEFAULT NULL,
  `cycleTime` int(11) DEFAULT NULL,
  PRIMARY KEY (`throwNr`),
  CONSTRAINT `ExeTime_ibfk_1` FOREIGN KEY (`throwNr`) REFERENCES `PickUpLoc` (`ThrowNr`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ExeTime`
--

LOCK TABLES `ExeTime` WRITE;
/*!40000 ALTER TABLE `ExeTime` DISABLE KEYS */;
INSERT INTO `ExeTime` VALUES (10,23,NULL,NULL,NULL),(11,23,NULL,NULL,NULL),(12,24,NULL,NULL,NULL),(13,24,NULL,NULL,NULL),(14,24,NULL,NULL,NULL),(15,24,NULL,NULL,NULL),(16,24,NULL,NULL,NULL),(17,24,NULL,NULL,NULL),(18,24,NULL,NULL,NULL),(19,24,NULL,NULL,NULL),(20,24,NULL,NULL,NULL),(21,24,NULL,NULL,NULL),(22,24,NULL,NULL,NULL),(23,24,NULL,NULL,NULL),(24,24,NULL,NULL,NULL),(25,24,123,321,NULL),(26,24,123,321,NULL),(27,24,123,321,NULL),(28,24,123,321,NULL),(29,24,123,321,468),(30,24,123,321,468),(31,24,123,321,468),(32,24,123,321,468),(33,24,123,321,468),(34,24,123,321,468),(35,12,123,321,456),(36,12,123,321,456),(37,12,123,321,456),(38,12,123,321,456),(39,12,123,321,456),(40,12,NULL,NULL,NULL),(41,12,472,992,1476),(42,12,472,992,1476),(43,12,NULL,NULL,NULL),(44,12,472,992,1476),(45,12,472,992,1476),(46,12,472,992,1476),(47,1889,NULL,NULL,NULL),(48,1889,NULL,NULL,NULL),(49,1889,NULL,NULL,NULL),(50,1889,NULL,NULL,NULL),(51,1889,NULL,NULL,NULL),(52,1889,NULL,NULL,NULL),(53,1889,327,205,2421),(54,1889,327,205,2421),(55,1889,327,205,2421),(56,1889,327,205,2421);
/*!40000 ALTER TABLE `ExeTime` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-22  9:00:18
