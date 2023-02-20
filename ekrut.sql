-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: ekrut
-- ------------------------------------------------------
-- Server version	8.0.31

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

--
-- Table structure for table `app_installation`
--

DROP TABLE IF EXISTS `app_installation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_installation` (
  `installType` varchar(45) NOT NULL,
  `machineNumber` int DEFAULT NULL,
  PRIMARY KEY (`installType`),
  KEY `machineNumber_idx` (`machineNumber`),
  CONSTRAINT `machineNumber` FOREIGN KEY (`machineNumber`) REFERENCES `device` (`deviceID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_installation`
--

LOCK TABLES `app_installation` WRITE;
/*!40000 ALTER TABLE `app_installation` DISABLE KEYS */;
INSERT INTO `app_installation` VALUES ('merged',3);
/*!40000 ALTER TABLE `app_installation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_report`
--

DROP TABLE IF EXISTS `customer_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_report` (
  `reportID` int NOT NULL AUTO_INCREMENT,
  `region` varchar(255) DEFAULT NULL,
  `year` int DEFAULT NULL,
  `month` int DEFAULT NULL,
  `deviceID` int DEFAULT NULL,
  `activityLevel` int DEFAULT NULL,
  `orderAverage` int DEFAULT NULL,
  `numberOfClientOrdered` int DEFAULT NULL,
  PRIMARY KEY (`reportID`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_report`
--

LOCK TABLES `customer_report` WRITE;
/*!40000 ALTER TABLE `customer_report` DISABLE KEYS */;
INSERT INTO `customer_report` VALUES (13,'region',2022,12,2,1000,3,330);
/*!40000 ALTER TABLE `customer_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customergeneratereport`
--

DROP TABLE IF EXISTS `customergeneratereport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customergeneratereport` (
  `reportID` int NOT NULL AUTO_INCREMENT,
  `year` int DEFAULT NULL,
  `month` int DEFAULT NULL,
  `below3orders` int DEFAULT NULL,
  `4to8orders` int DEFAULT NULL,
  `9to12orders` int DEFAULT NULL,
  `13to15orders` int DEFAULT NULL,
  `16oraboveorders` int DEFAULT NULL,
  `region` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`reportID`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customergeneratereport`
--

LOCK TABLES `customergeneratereport` WRITE;
/*!40000 ALTER TABLE `customergeneratereport` DISABLE KEYS */;
INSERT INTO `customergeneratereport` VALUES (1,2022,12,50,100,150,200,500,'region');
/*!40000 ALTER TABLE `customergeneratereport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `credit card number` varchar(255) DEFAULT NULL,
  `approved customer` tinyint DEFAULT '0',
  `notify` tinyint DEFAULT '0',
  PRIMARY KEY (`username`,`id`),
  KEY `username_idx` (`username`),
  KEY `id1` (`id`),
  CONSTRAINT `customers_username` FOREIGN KEY (`username`) REFERENCES `users` (`username`),
  CONSTRAINT `id1` FOREIGN KEY (`id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery`
--

DROP TABLE IF EXISTS `delivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delivery` (
  `orderID` int NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `estimatedDeliveryTime` datetime DEFAULT NULL,
  `status` enum('WAITING','PROCESSING','ARRIVED','DONE') DEFAULT 'WAITING',
  `deviceID` int NOT NULL,
  `notes` varchar(1024) DEFAULT NULL,
  `isNotifyDeliveryConfirmation` tinyint DEFAULT '0',
  PRIMARY KEY (`orderID`,`deviceID`),
  KEY `delivery_deviceID_idx` (`deviceID`),
  CONSTRAINT `delivery_deviceID` FOREIGN KEY (`deviceID`) REFERENCES `device` (`deviceID`),
  CONSTRAINT `delivery_orderID` FOREIGN KEY (`orderID`) REFERENCES `orders` (`orderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery`
--

LOCK TABLES `delivery` WRITE;
/*!40000 ALTER TABLE `delivery` DISABLE KEYS */;
INSERT INTO `delivery` VALUES (57,'hertzel 7, Zefat, Israel','2023-01-22 19:29:41','ARRIVED',7,'',0),(61,'yy 7, yyt, UAE',NULL,'WAITING',9,'',0),(62,'ggg 7, y, Israel',NULL,'WAITING',9,'',0),(65,'rgb 8, hgy, Israel','2023-01-23 21:28:19','PROCESSING',9,'',1),(71,'tryhr 7, rtyhtr, UAE',NULL,'WAITING',8,'ghrtyhtrhj',0);
/*!40000 ALTER TABLE `delivery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `device` (
  `deviceID` int NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `threshold` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`deviceID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device`
--

LOCK TABLES `device` WRITE;
/*!40000 ALTER TABLE `device` DISABLE KEYS */;
INSERT INTO `device` VALUES (1,'haifa','4','north'),(2,'dubai','4','UAE'),(3,'zefat','4','north'),(4,'abu dabi','4','UAE'),(5,'dimona','4','south'),(6,'eilat','4','south'),(7,'delivery center','4','north'),(8,'delivery center','4','south'),(9,'delivery center','4','UAE');
/*!40000 ALTER TABLE `device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `employeeID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`employeeID`),
  KEY `employee_username_idx` (`username`),
  CONSTRAINT `employees_username` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `external_users`
--

DROP TABLE IF EXISTS `external_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `external_users` (
  `id` varchar(255) NOT NULL,
  `first name` varchar(255) NOT NULL,
  `last name` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone number` varchar(255) NOT NULL,
  `isLogged` tinyint DEFAULT '0',
  `userPermission` enum('customer','pending customer','member','ceo','regional manager','delivery manager','service employee','marketing manager','marketing employee','logistics employee') DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `credit card number` varchar(255) DEFAULT NULL,
  `notify` tinyint DEFAULT '0',
  `madePurchase` tinyint DEFAULT '0',
  PRIMARY KEY (`username`,`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `external_users`
--

LOCK TABLES `external_users` WRITE;
/*!40000 ALTER TABLE `external_users` DISABLE KEYS */;
INSERT INTO `external_users` VALUES ('132132132','guy','banbo','ceo','123456','guy@gmail.com','023151565',0,'ceo',NULL,NULL,0,0),('200204142','israel','israeli','customer','123456','israel@gmail.com','0518184848',0,'customer',NULL,'1321321216565',1,0),('43534543','walt','disney','dmnorth','123456','walt@disney.com','052515616',0,'delivery manager','north',NULL,0,0),('34234324','haim','cohen','dmsouth','123456','haim@cohen.com','0548818',0,'delivery manager','south',NULL,0,0),('21321321','elior','malik','dmuae','123456','elior@gmail.com','056411981',0,'delivery manager','UAE',NULL,0,0),('324234332','eyal','shani','log','123456','eyal@shani.com','054156156',0,'logistics employee',NULL,NULL,0,0),('0','already','logged','loggedin','123456','logged@hmail.com','0528888888',1,'customer',NULL,'000000000000',0,0),('5415665','bob','constructor','member','123456','bob@constructor','0451561',0,'member',NULL,'156165165',0,0),('7897898787','elon','musk','menorth','123456','elon@musk.com','054156115',0,'marketing employee','north',NULL,0,0),('93248023','jeff','bezos','mesouth','123456','jeff@bezos','05156165156',0,'marketing employee','south',NULL,0,0),('564564565','rami','levi','meuae','123456','rami@levi.com','02341165',0,'marketing employee','UAE',NULL,0,0),('2165156','bar','matzliah','mm','123456','bar@gmail.com','02515516165',0,'marketing manager',NULL,NULL,0,0),('65745546','bill','gates','pending','123456','bill@microsoft.com','032156165',0,'pending customer',NULL,'132132132213',0,0),('23232221','yehuda','bitton','rgmnorth','123456','yehuda@gmail.com','050158181',0,'regional manager','north',NULL,0,0),('24323232','ofir','shahaf','rgmsouth','123456','ofir@gmail.com','051851891',0,'regional manager','south',NULL,0,0),('3232232','omer','adam','rgmuae','123456','omer@adam.com','054181818',0,'regional manager','UAE',NULL,0,0),('626256165','bar','malka','se','123465','bar@malka.com','056415115',0,'service employee',NULL,NULL,0,0),('4354343','ahron','ahron','user','123456','ahron@didi.com','0564165165',0,NULL,NULL,NULL,0,0);
/*!40000 ALTER TABLE `external_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory_call`
--

DROP TABLE IF EXISTS `inventory_call`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory_call` (
  `callID` int NOT NULL AUTO_INCREMENT,
  `deviceID` int DEFAULT NULL,
  `employeeID` int DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `status` enum('OPEN','DONE') DEFAULT NULL,
  `creatorUsername` varchar(255) DEFAULT NULL,
  `isNotify` tinyint DEFAULT '0',
  PRIMARY KEY (`callID`),
  KEY `invetory_call_deviceID_idx` (`deviceID`),
  KEY `inventroy_call_employeeID_idx` (`employeeID`),
  CONSTRAINT `inventroy_call_employeeID` FOREIGN KEY (`employeeID`) REFERENCES `employees` (`employeeID`),
  CONSTRAINT `invetory_call_deviceID` FOREIGN KEY (`deviceID`) REFERENCES `device` (`deviceID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory_call`
--

LOCK TABLES `inventory_call` WRITE;
/*!40000 ALTER TABLE `inventory_call` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventory_call` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventorygeneratereport`
--

DROP TABLE IF EXISTS `inventorygeneratereport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventorygeneratereport` (
  `reportID` int NOT NULL AUTO_INCREMENT,
  `year` int DEFAULT NULL,
  `month` int DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `item` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `deviceID` int DEFAULT NULL,
  PRIMARY KEY (`reportID`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventorygeneratereport`
--

LOCK TABLES `inventorygeneratereport` WRITE;
/*!40000 ALTER TABLE `inventorygeneratereport` DISABLE KEYS */;
INSERT INTO `inventorygeneratereport` VALUES (1,2022,12,'north','doritos',17,1),(2,2022,12,'north','water',13,1),(3,2022,12,'north','pepsi',1,1),(4,2022,12,'UAE','doritos',8,4),(5,2022,12,'UAE','water',52,4),(54,2022,12,'UAE','pepsi',3,4),(55,2022,12,'UAE','doritos',37,2),(56,2022,12,'UAE','water',2,2),(57,2022,12,'UAE','pepsi',3,2),(58,2022,12,'north','doritos',1,3),(59,2022,12,'north','water',2,3),(60,2022,12,'north','pepsi',3,3),(61,2022,12,'south','doritos',20,5),(62,2022,12,'south','water',52,5),(63,2022,12,'south','pepsi',8,5),(64,2022,12,'south','doritos',23,6),(65,2022,12,'south','water',13,6),(66,2022,12,'south','pepsi',17,6);
/*!40000 ALTER TABLE `inventorygeneratereport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invetory_report`
--

DROP TABLE IF EXISTS `invetory_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invetory_report` (
  `reportID` int NOT NULL AUTO_INCREMENT,
  `region` varchar(255) DEFAULT NULL,
  `year` int DEFAULT NULL,
  `month` int DEFAULT NULL,
  `deviceID` int DEFAULT NULL,
  `threshold` int DEFAULT NULL,
  `items` varchar(5000) DEFAULT NULL,
  `itemsInShortage` int DEFAULT NULL,
  `itemsNotInShortage` int DEFAULT NULL,
  PRIMARY KEY (`reportID`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invetory_report`
--

LOCK TABLES `invetory_report` WRITE;
/*!40000 ALTER TABLE `invetory_report` DISABLE KEYS */;
INSERT INTO `invetory_report` VALUES (15,'south',2022,12,6,4,'cola',0,3),(29,'north',2022,12,1,4,'items',1,2),(30,'UAE',2022,12,2,4,'items',2,1),(31,'south',2022,12,5,4,'cola',0,3),(32,'north',2022,12,3,4,'items',3,0),(33,'UAE',2022,12,4,4,'items',1,2);
/*!40000 ALTER TABLE `invetory_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `serialNumber` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`serialNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1,'Pepsi','drink',2,'/GuiAssets/pepsi.jpg'),(2,'Water','drink',2,'/GuiAssets/water.jpg'),(3,'Donuts','sweet',4,'/GuiAssets/Donuts.jpg'),(4,'Doritos','food',3,'/GuiAssets/Doritos.jpg'),(5,'Lollypop','sweet',1,'/GuiAssets/lollypop.jpg'),(6,'Pringles','food',2,'/GuiAssets/pringles.jpg'),(7,'Snyders','food',4,'/GuiAssets/Snyders.jpg'),(8,'Coffee Shot','drink',3,'/GuiAssets/Coffecan.jpg'),(9,'Fanta','drink',2,'/GuiAssets/fanta.jpg'),(10,'Orbit White','food',3,'/GuiAssets/bubblegum.jpg');
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_in_device`
--

DROP TABLE IF EXISTS `item_in_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_in_device` (
  `serialNumber` int NOT NULL,
  `deviceID` int NOT NULL,
  `amount` int DEFAULT '2147483647',
  `status` enum('NOT_AVAILABLE','AVAILABLE') DEFAULT NULL,
  `thresholdNotify` tinyint DEFAULT '0',
  PRIMARY KEY (`serialNumber`,`deviceID`),
  KEY `itemID_idx` (`serialNumber`),
  KEY `deviceID_idx` (`deviceID`),
  CONSTRAINT `deviceID` FOREIGN KEY (`deviceID`) REFERENCES `device` (`deviceID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `serialNumber` FOREIGN KEY (`serialNumber`) REFERENCES `item` (`serialNumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_in_device`
--

LOCK TABLES `item_in_device` WRITE;
/*!40000 ALTER TABLE `item_in_device` DISABLE KEYS */;
INSERT INTO `item_in_device` VALUES (1,1,0,'NOT_AVAILABLE',0),(1,2,29,'AVAILABLE',0),(1,7,2147483647,'AVAILABLE',0),(1,8,2147483647,'AVAILABLE',0),(1,9,2147483647,'AVAILABLE',0),(2,1,973,'AVAILABLE',0),(2,7,2147483647,'AVAILABLE',0),(2,8,2147483647,'AVAILABLE',0),(2,9,2147483647,'AVAILABLE',0),(3,1,20,'AVAILABLE',0),(3,2,459,'AVAILABLE',0),(3,7,2147483647,'AVAILABLE',0),(3,8,2147483647,'AVAILABLE',0),(3,9,2147483647,'AVAILABLE',0),(4,4,9,'AVAILABLE',0),(4,5,6,'AVAILABLE',0),(4,6,5,'AVAILABLE',0),(5,4,9,'AVAILABLE',0),(5,5,8,'AVAILABLE',0),(5,6,22,'AVAILABLE',0),(6,4,0,'NOT_AVAILABLE',1),(6,5,0,'NOT_AVAILABLE',0),(6,6,11,'AVAILABLE',0),(7,4,10,'AVAILABLE',0),(7,5,8,'AVAILABLE',0),(7,6,1,'AVAILABLE',0),(8,4,10,'AVAILABLE',0),(8,5,1,'AVAILABLE',0),(8,6,21,'AVAILABLE',0),(9,4,9,'AVAILABLE',0),(9,5,6,'AVAILABLE',0),(9,6,0,'NOT_AVAILABLE',0);
/*!40000 ALTER TABLE `item_in_device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_in_order`
--

DROP TABLE IF EXISTS `item_in_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_in_order` (
  `orderID` int NOT NULL,
  `serialNumber` int NOT NULL,
  `deviceID` int NOT NULL,
  `amount` int DEFAULT NULL,
  PRIMARY KEY (`orderID`,`serialNumber`,`deviceID`),
  KEY `item_in_order_orderID_idx` (`orderID`),
  KEY `item_in_order_deviceID_idx` (`deviceID`),
  KEY `item_in_order_serialNumber_idx` (`serialNumber`),
  CONSTRAINT `item_in_order_deviceID` FOREIGN KEY (`deviceID`) REFERENCES `item_in_device` (`deviceID`),
  CONSTRAINT `item_in_order_orderID` FOREIGN KEY (`orderID`) REFERENCES `orders` (`orderID`),
  CONSTRAINT `item_in_order_serialNumber` FOREIGN KEY (`serialNumber`) REFERENCES `item_in_device` (`serialNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_in_order`
--

LOCK TABLES `item_in_order` WRITE;
/*!40000 ALTER TABLE `item_in_order` DISABLE KEYS */;
INSERT INTO `item_in_order` VALUES (21,1,1,1),(21,2,1,1),(21,3,1,1),(22,3,1,1),(23,1,1,1),(23,3,1,1),(24,1,1,1),(25,2,1,1),(25,3,1,1),(26,1,1,1),(27,3,1,1),(28,2,1,1),(28,3,1,1),(29,3,1,1),(30,2,1,1),(30,3,1,1),(31,3,1,1),(32,1,2,1),(32,3,2,1),(33,3,2,1),(34,1,2,5),(34,3,2,1),(35,1,2,1),(35,3,2,2),(36,3,2,1),(37,1,1,1),(37,3,1,1),(38,1,1,56),(39,1,5,1),(39,2,5,1),(39,3,5,1),(40,1,5,3),(40,2,5,3),(40,3,5,2),(41,1,1,1),(41,2,1,1),(41,3,1,1),(42,1,2,6),(42,3,2,7),(43,3,1,1),(44,3,2,1),(45,3,1,1),(46,1,6,1),(46,2,6,1),(46,3,6,1),(47,1,4,1),(47,2,4,1),(47,3,4,26),(48,1,2,1),(48,3,2,2),(49,1,2,1),(49,3,2,2),(50,2,1,1),(51,1,4,1),(51,2,4,1),(51,3,4,4),(52,1,6,3),(52,2,6,3),(52,3,6,8),(53,1,4,1),(54,3,1,1),(55,1,2,1),(56,4,4,1),(56,5,4,1),(56,6,4,1),(56,9,4,1),(57,2,7,1),(57,3,7,1),(58,1,2,1),(58,3,2,1),(59,1,2,1),(59,3,2,1),(60,4,5,1),(60,5,5,1),(60,6,5,12),(61,1,9,2),(61,2,9,1),(61,3,9,1),(62,1,9,3),(62,2,9,2),(62,3,9,2),(63,1,1,2),(63,2,1,2),(64,1,1,1),(64,2,1,2),(64,3,1,1),(65,1,9,7),(65,2,9,8),(65,3,9,6),(66,1,1,2),(66,2,1,3),(66,3,1,1),(67,1,1,1),(67,2,1,1),(67,3,1,1),(68,1,1,1),(68,2,1,1),(68,3,1,1),(69,1,1,4),(69,2,1,3),(69,3,1,3),(70,4,5,1),(70,7,5,3),(70,8,5,6),(71,1,8,2),(71,2,8,2),(72,1,2,3),(72,3,2,21),(73,6,4,1),(74,2,1,1),(74,3,1,1),(75,2,1,1),(75,3,1,1),(76,2,1,1),(76,3,1,1);
/*!40000 ALTER TABLE `item_in_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `member number` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `madePurchase` tinyint DEFAULT '0',
  `notify` tinyint DEFAULT '0',
  PRIMARY KEY (`member number`),
  KEY `username_idx` (`username`),
  CONSTRAINT `member_username` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordergeneratereport`
--

DROP TABLE IF EXISTS `ordergeneratereport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordergeneratereport` (
  `reportID` int NOT NULL AUTO_INCREMENT,
  `deviceID` int DEFAULT NULL,
  `totalOrders` int DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `year` int DEFAULT NULL,
  `month` int DEFAULT NULL,
  `deviceName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`reportID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordergeneratereport`
--

LOCK TABLES `ordergeneratereport` WRITE;
/*!40000 ALTER TABLE `ordergeneratereport` DISABLE KEYS */;
INSERT INTO `ordergeneratereport` VALUES (1,1,70,'north',2022,12,'haifa'),(2,3,60,'north',2022,12,'zefat'),(3,5,60,'south',2022,12,'dimona'),(4,6,90,'south',2022,12,'eilat'),(21,4,100,'UAE',2022,12,'abu dabi'),(22,2,150,'UAE',2022,12,'dubai');
/*!40000 ALTER TABLE `ordergeneratereport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `orderID` int NOT NULL AUTO_INCREMENT,
  `totalAmount` float DEFAULT NULL,
  `createdTime` datetime DEFAULT NULL,
  `deviceID` int DEFAULT NULL,
  `debitDate` datetime DEFAULT NULL,
  `customerID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`orderID`),
  KEY `orders_deviceID_idx` (`deviceID`),
  CONSTRAINT `orders_deviceID` FOREIGN KEY (`deviceID`) REFERENCES `device` (`deviceID`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (56,8,'2023-01-15 19:00:20',4,'2023-01-15 19:00:20','200204142'),(57,6,'2023-01-15 19:00:53',7,'2023-01-15 19:00:53','200204142'),(58,4.8,'2023-01-15 19:01:30',2,'2023-01-31 00:00:00','5415665'),(59,6,'2023-01-15 19:15:37',2,'2023-01-31 00:00:00','5415665'),(60,28,'2023-01-16 20:31:41',5,'2023-01-31 00:00:00','5415665'),(61,4.5,'2023-01-16 20:47:23',9,'2023-01-31 00:00:00','5415665'),(62,10,'2023-01-16 20:48:52',9,'2023-01-31 00:00:00','5415665'),(63,8,'2023-01-16 21:09:44',1,'2023-01-16 21:09:44','200204142'),(64,10,'2023-01-16 21:11:39',1,'2023-01-16 21:11:39','200204142'),(65,54,'2023-01-16 21:14:40',9,'2023-01-16 21:14:40','200204142'),(66,11.2,'2023-01-16 21:18:34',1,'2023-01-31 00:00:00','5415665'),(67,8,'2023-01-16 21:19:50',1,'2023-01-31 00:00:00','5415665'),(68,8,'2023-01-16 21:19:57',1,'2023-01-31 00:00:00','5415665'),(69,20.8,'2023-01-16 21:22:11',1,'2023-01-31 00:00:00','5415665'),(70,16,'2023-01-17 12:10:27',5,'2023-01-31 00:00:00','4354343'),(71,4,'2023-01-17 12:14:39',8,'2023-01-31 00:00:00','4354343'),(72,48,'2023-01-17 12:16:38',2,'2023-01-31 00:00:00','4354343'),(73,2,'2023-01-17 12:18:29',4,'2023-01-31 00:00:00','4354343'),(74,6,'2023-01-17 12:49:56',1,'2023-01-31 00:00:00','4354343'),(75,6,'2023-01-17 12:52:02',1,'2023-01-31 00:00:00','4354343'),(76,6,'2023-01-17 12:53:45',1,'2023-01-31 00:00:00','4354343');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders_report`
--

DROP TABLE IF EXISTS `orders_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders_report` (
  `reportID` int NOT NULL AUTO_INCREMENT,
  `region` varchar(255) DEFAULT NULL,
  `year` int DEFAULT NULL,
  `month` int DEFAULT NULL,
  `orderByDevice` varchar(5000) DEFAULT NULL,
  `topSeller` varchar(255) DEFAULT NULL,
  `leastSeller` varchar(255) DEFAULT NULL,
  `totalOrders` int DEFAULT NULL,
  PRIMARY KEY (`reportID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_report`
--

LOCK TABLES `orders_report` WRITE;
/*!40000 ALTER TABLE `orders_report` DISABLE KEYS */;
INSERT INTO `orders_report` VALUES (1,'north',2022,12,'1','haifa','zefat',130),(14,'south',2022,12,'5','eilat','dimona',150),(15,'UAE',2022,12,'4','dubai','abu dabi',250);
/*!40000 ALTER TABLE `orders_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales` (
  `saleID` int NOT NULL AUTO_INCREMENT,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `discountSize` int DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `days` varchar(255) DEFAULT NULL,
  `startHour` time DEFAULT '00:00:00',
  `endHour` time DEFAULT '23:59:59',
  `saleType` enum('discountSize','1+1','discountByTime') DEFAULT NULL,
  `isActive` tinyint DEFAULT NULL,
  PRIMARY KEY (`saleID`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--

LOCK TABLES `sales` WRITE;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
INSERT INTO `sales` VALUES (19,'2023-01-16','2023-01-26',0,'south','sun, mon, tue, wed, thu, fri, sat','00:00:00','23:59:59','1+1',0),(20,'2023-01-16','2023-01-28',55,'UAE','sun, mon, tue, wed, thu, fri, sat','00:00:00','23:59:59','discountSize',0),(21,'2023-01-16','2023-01-28',0,'UAE','sun, mon, tue, wed, thu, fri, sat','00:00:00','23:59:59','1+1',1),(22,'2023-01-16','2023-01-31',0,'south','sun, mon, tue, wed, thu, fri, sat','09:00:00','15:00:00','1+1',1),(23,'2023-01-16','2023-01-31',0,'south','sun, mon, tue, wed, thu, fri, sat','00:00:00','06:00:00','1+1',1);
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `takeaway`
--

DROP TABLE IF EXISTS `takeaway`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `takeaway` (
  `orderID` int NOT NULL,
  `orderCode` int DEFAULT NULL,
  `collected` tinyint DEFAULT '0',
  PRIMARY KEY (`orderID`),
  CONSTRAINT `orderID` FOREIGN KEY (`orderID`) REFERENCES `orders` (`orderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `takeaway`
--

LOCK TABLES `takeaway` WRITE;
/*!40000 ALTER TABLE `takeaway` DISABLE KEYS */;
INSERT INTO `takeaway` VALUES (56,56,0),(58,58,1),(64,64,0),(70,70,1),(72,72,0),(73,73,0),(74,74,1),(75,75,1),(76,76,1);
/*!40000 ALTER TABLE `takeaway` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` varchar(255) NOT NULL,
  `first name` varchar(255) NOT NULL,
  `last name` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone number` varchar(255) NOT NULL,
  `isLogged` tinyint DEFAULT '0',
  `userPermission` enum('customer','pending customer','member','ceo','regional manager','delivery manager','service employee','marketing manager','marketing employee','logistics employee') DEFAULT NULL,
  PRIMARY KEY (`username`,`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-17 17:26:56
