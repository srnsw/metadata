CREATE DATABASE  IF NOT EXISTS `metadata` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `metadata`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: metadata
-- ------------------------------------------------------
-- Server version	5.5.22

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
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `added` datetime DEFAULT NULL,
  `added_by` varchar(255) DEFAULT NULL,
  `choose_from` varchar(255) DEFAULT NULL,
  `deprecated` datetime DEFAULT NULL,
  `deprecated_by` varchar(255) DEFAULT NULL,
  `label` varchar(255) NOT NULL,
  `last_modified` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `maximum_occurrence` int(11) NOT NULL,
  `minimum_occurrence` int(11) NOT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `resource_type` int(11) DEFAULT NULL,
  `resourceuri` varchar(255) DEFAULT NULL,
  `standalone` int(11) DEFAULT NULL,
  `syntax_encoding_scheme` varchar(255) DEFAULT NULL,
  `uri_encoding_scheme` varchar(255) DEFAULT NULL,
  `usage_note` varchar(255) NOT NULL,
  `value_type` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `category` bigint(20) DEFAULT NULL,
  `use_with` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKED8E89A9A9CB6A5` (`use_with`),
  KEY `FKED8E89A948B17D43` (`category`),
  CONSTRAINT `FKED8E89A948B17D43` FOREIGN KEY (`category`) REFERENCES `profile_category` (`id`),
  CONSTRAINT `FKED8E89A9A9CB6A5` FOREIGN KEY (`use_with`) REFERENCES `profile` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `profile_category`
--

DROP TABLE IF EXISTS `profile_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `term`
--

DROP TABLE IF EXISTS `term`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `term` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `coined` datetime DEFAULT NULL,
  `coined_by` varchar(255) DEFAULT NULL,
  `deprecated` datetime DEFAULT NULL,
  `deprecated_by` varchar(255) DEFAULT NULL,
  `domainuri` varchar(255) DEFAULT NULL,
  `label` varchar(255) NOT NULL,
  `last_modified` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `rangeuri` varchar(255) DEFAULT NULL,
  `resource_type` int(11) DEFAULT NULL,
  `see_also` varchar(255) DEFAULT NULL,
  `usage_note` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-08-02 12:47:08
