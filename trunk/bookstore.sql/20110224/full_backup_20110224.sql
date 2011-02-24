-- MySQL dump 10.13  Distrib 5.5.9, for Win64 (x86)
--
-- Host: harper.gotoip1.com    Database: harper
-- ------------------------------------------------------
-- Server version	5.1.49

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
-- Table structure for table `common_contact`
--

DROP TABLE IF EXISTS `common_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `common_contact` (
  `oid` int(11) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  `address` text,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `common_contact`
--

LOCK TABLES `common_contact` WRITE;
/*!40000 ALTER TABLE `common_contact` DISABLE KEYS */;
/*!40000 ALTER TABLE `common_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fna_accounting`
--

DROP TABLE IF EXISTS `fna_accounting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fna_accounting` (
  `oid` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `subject` varchar(100) NOT NULL,
  `ref_type` varchar(45) NOT NULL,
  `ref_number` varchar(100) NOT NULL,
  `amount` decimal(13,2) NOT NULL,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fna_accounting`
--

LOCK TABLES `fna_accounting` WRITE;
/*!40000 ALTER TABLE `fna_accounting` DISABLE KEYS */;
/*!40000 ALTER TABLE `fna_accounting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `library_entry`
--

DROP TABLE IF EXISTS `library_entry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `library_entry` (
  `oid` int(11) NOT NULL,
  `book_oid` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `ost` int(11) NOT NULL,
  PRIMARY KEY (`oid`),
  KEY `le_fk_book` (`book_oid`),
  CONSTRAINT `le_fk_book` FOREIGN KEY (`book_oid`) REFERENCES `profile_book` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `library_entry`
--

LOCK TABLES `library_entry` WRITE;
/*!40000 ALTER TABLE `library_entry` DISABLE KEYS */;
/*!40000 ALTER TABLE `library_entry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `library_record`
--

DROP TABLE IF EXISTS `library_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `library_record` (
  `oid` int(11) NOT NULL,
  `rec_number` varchar(45) NOT NULL,
  `acc_date` datetime NOT NULL,
  `close_date` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `borrower` int(11) NOT NULL,
  `remark` text,
  `close_reason` text,
  `type` varchar(45) NOT NULL,
  `site` int(11) NOT NULL,
  PRIMARY KEY (`oid`),
  KEY `lr_fk_brr` (`borrower`),
  KEY `lr_fk_site` (`site`),
  CONSTRAINT `lr_fk_brr` FOREIGN KEY (`borrower`) REFERENCES `profile_borrower` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `lr_fk_site` FOREIGN KEY (`site`) REFERENCES `store_site` (`oid`) ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `library_record`
--

LOCK TABLES `library_record` WRITE;
/*!40000 ALTER TABLE `library_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `library_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `library_record_item`
--

DROP TABLE IF EXISTS `library_record_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `library_record_item` (
  `oid` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `header` int(11) NOT NULL,
  `book_count` int(11) NOT NULL,
  `book_oid` int(11) NOT NULL,
  `ost` int(11) NOT NULL,
  `balance_to` int(11) DEFAULT NULL,
  `close_date` datetime DEFAULT NULL,
  PRIMARY KEY (`oid`),
  KEY `lri_fk_header` (`header`),
  KEY `lri_fk_balance` (`balance_to`),
  KEY `lri_fk_book` (`book_oid`),
  CONSTRAINT `lri_fk_balance` FOREIGN KEY (`balance_to`) REFERENCES `library_record_item` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `lri_fk_book` FOREIGN KEY (`book_oid`) REFERENCES `profile_book` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `lri_fk_header` FOREIGN KEY (`header`) REFERENCES `library_record` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `library_record_item`
--

LOCK TABLES `library_record_item` WRITE;
/*!40000 ALTER TABLE `library_record_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `library_record_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_common`
--

DROP TABLE IF EXISTS `order_common`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_common` (
  `number` varchar(20) NOT NULL,
  `create_date` datetime NOT NULL,
  `customer` int(11) DEFAULT NULL,
  `oid` int(11) NOT NULL,
  `order_type` varchar(5) NOT NULL,
  `provider` int(11) DEFAULT NULL,
  `status` int(1) NOT NULL DEFAULT '0',
  `site` int(11) DEFAULT NULL,
  `fee_amount` decimal(10,2) DEFAULT NULL,
  `fee_name` varchar(100) DEFAULT NULL,
  `remark` text,
  `refno` varchar(100) DEFAULT NULL,
  `version` int(10) NOT NULL,
  `ref_status` varchar(50) DEFAULT NULL,
  `purchase_deliver` int(11) DEFAULT NULL,
  `total_amt` decimal(10,2) NOT NULL,
  `delivery_status` int(1) NOT NULL DEFAULT '0',
  `memo` text,
  PRIMARY KEY (`oid`),
  UNIQUE KEY `order_type_number` (`order_type`,`number`),
  UNIQUE KEY `order_uk_refno` (`refno`),
  KEY `order_fk_customer` (`customer`),
  KEY `order_fk_provider` (`provider`),
  KEY `order_fk_site` (`site`),
  KEY `order_fk_purchase_deliver` (`purchase_deliver`),
  CONSTRAINT `order_fk_customer` FOREIGN KEY (`customer`) REFERENCES `profile_customer` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_fk_provider` FOREIGN KEY (`provider`) REFERENCES `profile_supplier` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_fk_purchase_deliver` FOREIGN KEY (`purchase_deliver`) REFERENCES `order_delivery` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_fk_site` FOREIGN KEY (`site`) REFERENCES `store_site` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_common`
--

LOCK TABLES `order_common` WRITE;
/*!40000 ALTER TABLE `order_common` DISABLE KEYS */;
INSERT INTO `order_common` VALUES ('SO00000001','2011-02-20 06:15:56',NULL,2,'S',1,1,2,NULL,NULL,NULL,NULL,11,NULL,NULL,79414.00,0,NULL),('SO00000002','2011-02-20 09:05:53',NULL,53,'S',1,2,3,NULL,NULL,NULL,NULL,6,NULL,NULL,100038.50,0,NULL),('SO00000003','2011-02-20 17:14:08',NULL,101,'S',1,2,1,NULL,NULL,NULL,NULL,10,NULL,NULL,25861.50,0,NULL),('SO00000004','2011-02-22 21:38:55',NULL,151,'S',51,2,2,60.00,NULL,NULL,NULL,5,NULL,NULL,5473.00,0,NULL),('PO00000001','2011-02-22 19:37:02',6,201,'B',NULL,1,2,12.00,NULL,NULL,'64737863270351',2,'卖家已发货，等待买家确认',5,82.00,0,'丽丽，德佳代销单明日 （2月23日）顺丰送到－加急 收货地址： 杨秋萍 ，13816119236 ， ，上海 上海市 浦东新区 丁香路1399弄25号301室 ，200135  '),('PO00000002','2011-02-22 11:38:45',1,202,'B',NULL,1,2,10.00,NULL,NULL,'64669954619551',2,'卖家已发货，等待买家确认',6,110.00,0,'丽丽，加张刻录EBOOK，快递只能发中通和韵达，其它不到。最好在星期五下午4点前到货。'),('PO00000005','2011-02-22 13:56:00',4,203,'B',NULL,1,2,0.00,NULL,NULL,'64692668588051',1,'买家已付款，等待卖家发货',4,144.00,0,'丽丽，不要水彩笔，只要四本涂色书。今天一定要发货'),('PO00000003','2011-02-22 17:45:05',5,204,'B',NULL,1,2,24.00,NULL,NULL,'64725549051751',2,'卖家已发货，等待买家确认',1,544.00,0,'丽丽，明天一定发货，东西要全'),('PO00000004','2011-02-21 22:56:04',3,205,'B',NULL,1,2,0.00,NULL,NULL,'64636920431151',2,'卖家已发货，等待买家确认',2,3900.00,0,NULL),('PO00000006','2011-02-22 20:48:27',2,206,'B',NULL,1,2,5.00,NULL,NULL,'64751153264851',2,'卖家已发货，等待买家确认',3,80.00,0,'代销单，不要放单据。'),('PO00000012','2011-02-22 11:09:07',61,251,'B',NULL,1,2,5.00,NULL,NULL,'64665852590751',1,'卖家已发货，等待买家确认',55,465.00,0,'丽丽，先发学生书，其余到货再发。'),('PO00000029','2011-02-20 23:34:11',53,252,'B',NULL,1,2,10.00,NULL,NULL,'64513261795151',1,'卖家已发货，等待买家确认',63,370.00,0,'丽丽，只要学生书，送刻录CD一张。'),('PO00000026','2011-02-22 12:53:57',62,253,'B',NULL,1,2,5.00,NULL,NULL,'64679671093351',1,'卖家已发货，等待买家确认',58,185.00,0,'丽丽'),('PO00000028','2011-02-21 11:41:13',58,254,'B',NULL,1,2,0.00,NULL,NULL,'64539119218551',1,'卖家已发货，等待买家确认',74,299.00,0,'丽丽，今天一定要发货，急要。'),('PO00000025','2011-02-20 16:22:53',57,255,'B',NULL,1,2,5.00,NULL,NULL,'64461742502051',1,'卖家已发货，等待买家确认',67,105.00,0,NULL),('PO00000017','2011-02-21 11:59:10',72,256,'B',NULL,1,2,86.00,NULL,NULL,'64541342217951',1,'卖家已发货，等待买家确认',64,886.00,0,'丽丽，预定，到货再发'),('PO00000018','2011-02-21 23:22:17',56,257,'B',NULL,1,2,0.00,NULL,NULL,'64640214732551',1,'卖家已发货，等待买家确认',72,1400.00,0,'丽丽，sight 要精装650元的'),('PO00000007','2011-02-20 11:39:43',71,258,'B',NULL,1,2,0.00,NULL,NULL,'64424882376951',1,'卖家已发货，等待买家确认',71,340.00,0,'丽丽，总共拍了五套，和之前的一套一起发。急，今天发货。送同级别的刻录律动操DVD。'),('PO00000027','2011-02-20 18:12:29',51,259,'B',NULL,1,2,0.00,NULL,NULL,'64471504371751',1,'卖家已发货，等待买家确认',61,195.00,0,'丽丽，送1B，2B刻录碟各一张'),('PO00000015','2011-02-21 15:44:53',67,260,'B',NULL,1,2,10.00,NULL,NULL,'64579545985451',1,'卖家已发货，等待买家确认',51,375.00,0,'茜茜  代销单  收货地址： 郭靖 ，13863757010 ， ，山东省 济宁市 邹城市 兖矿集团机电设备制造厂科技管理部 ，273500  '),('PO00000011','2011-02-20 18:13:09',64,261,'B',NULL,1,2,0.00,NULL,NULL,'64471567551751',1,'卖家已发货，等待买家确认',65,195.00,0,'丽丽，送1B，2B刻录碟各一张'),('PO00000024','2011-02-21 15:59:57',66,262,'B',NULL,1,2,5.00,NULL,NULL,'64582034542251',1,'卖家已发货，等待买家确认',54,90.00,0,NULL),('PO00000023','2011-02-21 11:59:19',59,263,'B',NULL,1,2,10.00,NULL,NULL,'64541359117951',1,'卖家已发货，等待买家确认',62,2010.00,0,'丽丽，这些有货的先发，soe 1,3,4，5学生书+六年级全套，送K-6年级刻录CD'),('PO00000009','2011-02-21 14:57:16',2,264,'B',NULL,1,2,0.00,NULL,NULL,'64571710424851',1,'卖家已发货，等待买家确认',60,26.00,0,'代销单'),('PO00000031','2011-02-20 09:24:08',54,265,'B',NULL,1,2,0.00,NULL,NULL,'64417101609651',1,'卖家已发货，等待买家确认',73,81.00,0,'丽丽，今天一定要发货'),('PO00000030','2011-02-21 21:02:58',70,266,'B',NULL,1,2,0.00,NULL,NULL,'64617751497951',1,'卖家已发货，等待买家确认',57,299.00,0,'丽丽，送大礼包'),('PO00000022','2011-02-21 13:53:02',52,267,'B',NULL,1,2,0.00,NULL,NULL,'64561481188651',1,'卖家已发货，等待买家确认',70,263.00,0,'丽丽'),('PO00000019','2011-02-20 10:52:31',60,268,'B',NULL,1,2,20.00,NULL,NULL,'64423014885451',1,'卖家已发货，等待买家确认',52,1000.00,0,'丽丽，今天发货'),('PO00000008','2011-02-22 12:38:20',55,269,'B',NULL,1,2,8.00,NULL,NULL,'64677412753151',1,'卖家已发货，等待买家确认',66,89.00,0,'丽丽，今天一定要发货'),('PO00000016','2011-02-20 15:18:46',69,270,'B',NULL,1,2,0.00,NULL,NULL,'64450477593151',1,'卖家已发货，等待买家确认',75,280.00,0,'丽丽，送大礼包'),('PO00000010','2011-02-21 12:25:47',68,271,'B',NULL,1,2,16.00,NULL,NULL,'64548198108651',1,'卖家已发货，等待买家确认',69,500.00,0,'丽丽，随机送些赠品书'),('PO00000020','2011-02-22 14:37:33',63,272,'B',NULL,1,2,0.00,NULL,NULL,'64699497729451',1,'卖家已发货，等待买家确认',59,305.00,0,'丽丽，今天先发一三册，第二册到货再发。'),('PO00000021','2011-02-21 11:42:50',73,273,'B',NULL,1,2,8.00,NULL,NULL,'64539319778551',1,'卖家已发货，等待买家确认',56,367.00,0,'丽丽'),('PO00000014','2011-02-20 21:09:18',2,274,'B',NULL,1,2,10.00,NULL,NULL,'64496239904851',1,'卖家已发货，等待买家确认',53,20.00,0,'代销单'),('PO00000013','2011-02-20 23:07:14',65,275,'B',NULL,1,2,0.00,NULL,NULL,'64510545395151',1,'卖家已发货，等待买家确认',68,305.00,0,'丽丽，先发一三册，送大礼包，第二册到货再补发，补发时短信通知。'),('PO00000034','2011-02-24 00:01:56',104,301,'B',NULL,1,2,30.00,NULL,NULL,'64911665586251',2,'卖家已发货，等待买家确认',104,2770.00,0,'丽丽，今天发货1，3，4，5年级的学生书+六年级学生套装+七个年级的正版CD '),('PO00000032','2011-02-23 14:17:57',102,302,'B',NULL,1,2,0.00,NULL,NULL,'64828449306051',2,'卖家已发货，等待买家确认',103,44.00,0,'丽丽，和之前拍的一起发'),('PO00000033','2011-02-23 13:55:29',101,303,'B',NULL,1,2,10.00,NULL,NULL,'64822326915351',1,'买家已付款，等待卖家发货',105,544.00,0,'丽丽，送大礼包，再送本赠品书。'),('PO00000035','2011-02-23 11:33:36',61,304,'B',NULL,1,2,5.00,NULL,NULL,'64802097810751',1,'买家已付款，等待卖家发货',102,125.00,0,'丽丽，soe一年级正版音频CD'),('PO00000036','2011-02-22 16:08:45',103,305,'B',NULL,1,2,10.00,NULL,NULL,'64714559526051',2,'卖家已发货，等待买家确认',101,93.00,0,'丽丽，之后又拍了两本书没货的换同等价位的男孩子读的书。尽快发货'),('PO00000038','2011-02-23 15:26:57',2,351,'B',NULL,1,2,5.00,NULL,NULL,'64839916004851',1,'卖家已发货，等待买家确认',155,165.00,0,'代销单，不要放清单，请今天一定发货地址：张鸣 ，13564990422 ， ，上海 上海市 静安区 武宁南路442号517室 ，200042 '),('PO00000041','2011-02-23 15:43:05',153,352,'B',NULL,1,2,0.00,NULL,NULL,'64842630127051',1,'卖家已发货，等待买家确认',153,879.00,0,'丽丽'),('PO00000042','2011-02-23 15:17:03',2,353,'B',NULL,1,2,10.00,NULL,NULL,'64838241344851',1,'卖家已发货，等待买家确认',151,130.00,0,'代销单，不要放清单。地址：高楠 ，13661113393 ， ，北京 北京市 海淀区 彰化南路18号　玉北商务楼　7号楼310室 ，100195 '),('PO00000039','2011-02-23 14:05:16',151,354,'B',NULL,1,2,5.00,NULL,NULL,'64826360262851',1,'卖家已发货，等待买家确认',152,86.00,0,'丽丽，送CD'),('PO00000040','2011-02-23 13:36:55',6,355,'B',NULL,1,2,10.00,NULL,NULL,'64819254410351',1,'卖家已发货，等待买家确认',156,160.40,0,'丽丽）德佳代销单，收货地址： 甘慧 ，13922632660 ，0758-8513256 ，广东省 肇庆市 高要市 金渡镇金渡工业园D1区 阿莱斯（高要）化工有限公司 ，526108  '),('PO00000037','2011-02-23 16:24:26',152,356,'B',NULL,1,2,5.00,NULL,NULL,'64849476409851',1,'卖家已发货，等待买家确认',154,85.00,0,'丽丽，稠州宝贝代销单'),('PO00000044','2011-02-24 13:30:52',152,401,'B',NULL,1,2,0.00,NULL,NULL,'64953343589851',1,'卖家已发货，等待买家确认',203,5.00,0,'丽丽，客户换书付的运费，love english starter 1换level 1地址：李虹 ，13958155892 ，0571-86511565 ，浙江省 杭州市 上城区 始版桥花园6幢4单元 ，310016 '),('PO00000047','2011-02-24 15:58:05',2,402,'B',NULL,1,2,5.00,NULL,NULL,'64977530464851',1,'卖家已发货，等待买家确认',202,142.00,0,'代销单，不要放单据。今天发货'),('PO00000043','2011-02-24 13:17:52',202,403,'B',NULL,1,2,14.00,NULL,NULL,'64951245948051',1,'卖家已发货，等待买家确认',205,524.00,0,'丽丽，补发上回收据（1035元）盖公司章这回524元也要开收据。'),('PO00000046','2011-02-23 02:14:49',201,404,'B',NULL,1,2,9.00,NULL,NULL,'64781283794751',1,'卖家已发货，等待买家确认',201,697.00,0,'茜茜'),('PO00000045','2011-02-24 10:13:12',203,405,'B',NULL,1,2,0.00,NULL,NULL,'64926325417951',1,'卖家已发货，等待买家确认',204,616.00,0,'丽丽'),('PO00000052','2011-02-24 16:43:59',205,406,'B',NULL,1,2,9.00,NULL,NULL,'64985004356551',1,'买家已付款，等待卖家发货',207,314.00,0,NULL),('PO00000048','2011-02-24 13:33:09',206,407,'B',NULL,1,2,10.00,NULL,NULL,'64954076617151',1,'买家已付款，等待卖家发货',206,369.00,0,NULL),('PO00000051','2011-02-24 20:12:18',204,408,'B',NULL,1,2,0.00,NULL,NULL,'65009988605651',1,'买家已付款，等待卖家发货',210,28.00,0,'丽丽，美国高中物理教材电子版，发QQ邮箱：810902374'),('PO00000049','2011-02-24 16:43:18',2,409,'B',NULL,1,2,0.00,NULL,NULL,'64984864064851',1,'买家已付款，等待卖家发货',208,187.80,0,'代销单，王英瑛 ，13905769003 ，0576-88552833 ，浙江省 台州市 椒江区 浙江省台州市椒江东海大道788号 台州市食品药品检验所业务科 ，318000 '),('PO00000050','2011-02-24 19:51:10',207,410,'B',NULL,1,2,0.00,NULL,NULL,'65006957150951',1,'买家已付款，等待卖家发货',209,263.00,0,'丽丽，香港朗文1B 3B 5B');
/*!40000 ALTER TABLE `order_common` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_contact`
--

DROP TABLE IF EXISTS `order_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_contact` (
  `order_oid` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `post_code` varchar(20) DEFAULT NULL,
  `phone_mobile` varchar(80) DEFAULT NULL,
  `phone_normal` varchar(80) DEFAULT NULL,
  `address` text,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`order_oid`),
  KEY `order_contact_fk_order` (`order_oid`),
  CONSTRAINT `order_contact_fk_order` FOREIGN KEY (`order_oid`) REFERENCES `order_common` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_contact`
--

LOCK TABLES `order_contact` WRITE;
/*!40000 ALTER TABLE `order_contact` DISABLE KEYS */;
INSERT INTO `order_contact` VALUES (2,NULL,NULL,NULL,NULL,NULL,NULL),(53,NULL,NULL,NULL,NULL,NULL,NULL),(101,NULL,NULL,NULL,NULL,NULL,NULL),(151,NULL,NULL,NULL,NULL,NULL,NULL),(201,'杨秋萍',NULL,'13816119236',NULL,'上海 上海市 浦东新区 丁香路1399弄25号301室    ',NULL),(202,'梁勇',NULL,'15072430121',NULL,'湖北省 武汉市 汉阳区 湖北武汉市沌口经济开发区珠山湖大道653号   商用车技术中心  ',NULL),(203,'袁焱',NULL,'13768861993',NULL,'广西壮族自治区 柳州市 城中区 广西柳州市文昌路8号柳州市人民医院住院部15楼呼吸科',NULL),(204,'江幸芳',NULL,'13980725768',NULL,'四川省 成都市 武侯区 新光路1号观南上域6栋16楼3号',NULL),(205,'鲍乐',NULL,'13775355053',NULL,'江苏省 镇江市 京口区 弥陀寺巷36幢302',NULL),(206,'张贵芳',NULL,'18951678274','025-52625067','江苏省 南京市 秦淮区 大明西路风光里39懂一单元801室 ，  ',NULL),(251,'刘加林',NULL,'13376211699','0510-83752685','江苏省 无锡市 惠山区 西漳天一城3-703',NULL),(252,'李丽星',NULL,'13600401958',NULL,'广东省 深圳市 南山区 后海大道蔚蓝海岸2期23栋28B',NULL),(253,'吴晓红',NULL,'13771969192',NULL,'江苏省 苏州市 金阊区 干将西路1358号苏州三元中学',NULL),(254,'易老师',NULL,'13549662451',NULL,'湖南省 长沙市 岳麓区 荣湾镇新民路，久和物业大厦401',NULL),(255,'范阳',NULL,'13362486442','0574-87553260','浙江省 宁波市 江东区 兴宁路400号二幢406室',NULL),(256,'龙志伟',NULL,'13580563763','020-61023056','广东省 广州市 天河区 天河区华景北路195号3103房',NULL),(257,'冯蓝',NULL,'13682552510',NULL,'广东省 深圳市 罗湖区 深圳罗湖区宝岗路祥福雅居彩虹阁1608',NULL),(258,'王晓艳',NULL,'13065779393',NULL,'浙江省 绍兴市 绍兴县 省:浙江省 市:绍兴市 区:越城区  水木清华12幢103室',NULL),(259,'谢红雁',NULL,'13316352280',NULL,'广东省 惠州市 惠阳区 淡水镇下土湖棕榈岛学校',NULL),(260,'谭欣欣',NULL,'13402041234',NULL,'上海 上海市 浦东新区 浦东新区大同路1010弄26号204室',NULL),(261,'谢红雁',NULL,'13316352280',NULL,'广东省 惠州市 惠阳区 淡水镇下土湖棕榈岛学校',NULL),(262,'沈宏伟',NULL,'18961775026','0510-85070672','江苏省 无锡市 滨湖区 万科城市花园一区75幢301室',NULL),(263,'龙志伟',NULL,'13580563763','020-61023056','广东省 广州市 天河区 天河区华景北路195号3103房',NULL),(264,'Chris',NULL,'13776159906',NULL,'江苏省 苏州市 昆山市 江蘇省昆山市苇城南路1666号清华科技园科技大厦4楼天可软件 ，',NULL),(265,'蒋洪蓉',NULL,'13916216728',NULL,'上海 上海市 闵行区 上海市兴梅路1199弄春申景城二期29号1103室',NULL),(266,'张琳',NULL,'15881049288',NULL,'四川省 成都市 青羊区 下同仁路126号院1单元15楼60号',NULL),(267,'易鑫',NULL,'13435735610',NULL,'广东省 中山市   中山市竹苑天竹街9幢206',NULL),(268,'向京灵',NULL,'13125111870','027-83931982','湖北省 武汉市 东西湖区 武汉市常青花园18号楼55栋2单元301',NULL),(269,'朱庭凯',NULL,'13037114701','027-81813313','湖北省 武汉市 江夏区 纸坊古驿道172号国税局',NULL),(270,'刘宗华',NULL,'13097169696',NULL,'江西省 赣州市 安远县 江西安远一中',NULL),(271,'易鑫',NULL,'13435735610',NULL,'广东省 中山市 null 中山市竹苑天竹街9幢206',NULL),(272,'杨宝琴',NULL,'15257500085','0575-88606557','浙江省 绍兴市 越城区 投醪河路稽山中学高一理科办公室',NULL),(273,'易老师',NULL,'13549662451',NULL,'湖南省 长沙市 岳麓区 荣湾镇新民路，久和物业大厦401',NULL),(274,'李姜',NULL,'13844787441',NULL,'吉林省 延边朝鲜族自治州 延吉市 吉林省 延边朝鲜族自治州 延吉市 建工街道木材小区1号楼1单元301 ，',NULL),(275,'李丽星',NULL,'13600401958',NULL,'广东省 深圳市 南山区 后海大道蔚蓝海岸2期23栋28B',NULL),(301,'Jeff Obadal',NULL,'15334394707','0871-4316005','云南省 昆明市 盘龙区 罗丈村金色西路317号云南韩国国际学校',NULL),(302,'黄娟娟',NULL,'13435411107',NULL,'广东省 佛山市 禅城区 魁奇一路丽日玫瑰商业街PB17号',NULL),(303,'张京芃',NULL,'15207143010',NULL,'湖北省 武汉市 硚口区 古田三路东方花城45-1-601',NULL),(304,'刘加林',NULL,'13376211699','0510-83752685','江苏省 无锡市 惠山区 西漳天一城3-703',NULL),(305,'黄娟娟',NULL,'13435411107',NULL,'广东省 佛山市 禅城区 魁奇一路丽日玫瑰商业街PB17号',NULL),(351,'张鸣',NULL,'13564990422',NULL,'上海 上海市 静安区 上海 上海市 静安区 武宁南路442号517室 ',NULL),(352,'杨慧颖',NULL,'13061626898',NULL,'上海 上海市 浦东新区 上海市 上海浦东桃林路815弄9号302',NULL),(353,'高楠',NULL,'13661113393',NULL,'北京 北京市 海淀区 彰化南路18号　玉北商务楼　7号楼310室 ， ',NULL),(354,'倪小姐收',NULL,'13502994899','0754-88899260','广东省 汕头市 龙湖区 长平东路阳光海岸协和水岸4栋梁1304房',NULL),(355,' 甘慧',NULL,'13922632660','0758-8513256','广东省 肇庆市 高要市 金渡镇金渡工业园D1区 阿莱斯（高要）化工有限公司 ',NULL),(356,'李虹 ',NULL,'13958155892','0571-86511565','浙江省 杭州市 上城区 始版桥花园6幢4单元 ',NULL),(401,'王凌霄',NULL,'15022634189',NULL,'天津 天津市 大港区 学府路60号天津外国语学院滨海外事学院',NULL),(402,'苏珊',NULL,'13861355192','0511-85891001','江苏省 镇江市 丹徒区 辛丰工业园（江苏富林医疗设备有限公司） ，',NULL),(403,'叶嘉静',NULL,'13824238558','0752-3361192','广东省 惠州市 惠阳区 淡水南门大街68号天安商业大厦六楼（麦当劳楼上）',NULL),(404,'黄国华',NULL,'13606358568',NULL,'山东省 聊城市 东昌府区 闸口北步行街D1-24聊城电脑虎有限公司',NULL),(405,'叶立前',NULL,'15295258949','0523-83553765','江苏省 泰州市 兴化市 兴化市安丰镇新世纪花园南侧5米，英语培训中心',NULL),(406,'张舒',NULL,'13083200496',NULL,'安徽省 马鞍山市 雨山区 雨山区法院',NULL),(407,'黄庆菊',NULL,'13377529922','0752-2869336','广东省 惠州市 惠城区 惠州江北邮电新村四栋C座301',NULL),(408,'吴永珍',NULL,'13765202209','0852-8950595','贵州省 遵义市 汇川区 贵州省遵义市汇川区上海路830号 遵义师范学院',NULL),(409,'王英瑛',NULL,'13905769003','0576-88552833','浙江省 台州市 椒江区 浙江省台州市椒江东海大道788号 台州市食品药品检验所业务科 ， ',NULL),(410,'蔡雨浩',NULL,'15029198884',NULL,'江苏省 苏州市 张家港市 玉兰路11号东方明珠艺术培训中心',NULL);
/*!40000 ALTER TABLE `order_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_delivery`
--

DROP TABLE IF EXISTS `order_delivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_delivery` (
  `oid` int(11) NOT NULL,
  `company` varchar(45) NOT NULL,
  `number` varchar(45) DEFAULT NULL,
  `status` int(1) NOT NULL,
  `create_date` datetime NOT NULL,
  `consignee_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `address` text,
  `phone` varchar(45) DEFAULT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  `post_code` varchar(10) DEFAULT NULL,
  `valid` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_delivery`
--

LOCK TABLES `order_delivery` WRITE;
/*!40000 ALTER TABLE `order_delivery` DISABLE KEYS */;
INSERT INTO `order_delivery` VALUES (1,'YUNDA',NULL,0,'2011-02-23 01:15:15',NULL,NULL,NULL,NULL,NULL,NULL,0),(2,'YUNDA',NULL,0,'2011-02-23 01:15:15',NULL,NULL,NULL,NULL,NULL,NULL,0),(3,'YUNDA',NULL,0,'2011-02-23 01:15:16',NULL,NULL,NULL,NULL,NULL,NULL,0),(4,'YUNDA',NULL,0,'2011-02-23 01:15:15',NULL,NULL,NULL,NULL,NULL,NULL,0),(5,'YUNDA',NULL,0,'2011-02-23 01:15:15',NULL,NULL,NULL,NULL,NULL,NULL,0),(6,'YUNDA',NULL,0,'2011-02-23 01:15:15',NULL,NULL,NULL,NULL,NULL,NULL,0),(51,'YUNDA',NULL,0,'2011-02-23 01:16:18',NULL,NULL,NULL,NULL,NULL,NULL,0),(52,'YUNDA',NULL,0,'2011-02-23 01:16:18',NULL,NULL,NULL,NULL,NULL,NULL,0),(53,'YUNDA',NULL,0,'2011-02-23 01:16:17',NULL,NULL,NULL,NULL,NULL,NULL,0),(54,'YUNDA',NULL,0,'2011-02-23 01:16:19',NULL,NULL,NULL,NULL,NULL,NULL,0),(55,'YUNDA',NULL,0,'2011-02-23 01:16:17',NULL,NULL,NULL,NULL,NULL,NULL,0),(56,'YUNDA',NULL,0,'2011-02-23 01:16:19',NULL,NULL,NULL,NULL,NULL,NULL,0),(57,'YUNDA',NULL,0,'2011-02-23 01:16:20',NULL,NULL,NULL,NULL,NULL,NULL,0),(58,'YUNDA',NULL,0,'2011-02-23 01:16:20',NULL,NULL,NULL,NULL,NULL,NULL,0),(59,'YUNDA',NULL,0,'2011-02-23 01:16:19',NULL,NULL,NULL,NULL,NULL,NULL,0),(60,'YUNDA',NULL,0,'2011-02-23 01:16:16',NULL,NULL,NULL,NULL,NULL,NULL,0),(61,'YUNDA',NULL,0,'2011-02-23 01:16:20',NULL,NULL,NULL,NULL,NULL,NULL,0),(62,'YUNDA',NULL,0,'2011-02-23 01:16:19',NULL,NULL,NULL,NULL,NULL,NULL,0),(63,'YUNDA',NULL,0,'2011-02-23 01:16:20',NULL,NULL,NULL,NULL,NULL,NULL,0),(64,'YUNDA',NULL,0,'2011-02-23 01:16:18',NULL,NULL,NULL,NULL,NULL,NULL,0),(65,'YUNDA',NULL,0,'2011-02-23 01:16:17',NULL,NULL,NULL,NULL,NULL,NULL,0),(66,'YUNDA',NULL,0,'2011-02-23 01:16:16',NULL,NULL,NULL,NULL,NULL,NULL,0),(67,'YUNDA',NULL,0,'2011-02-23 01:16:19',NULL,NULL,NULL,NULL,NULL,NULL,0),(68,'YUNDA',NULL,0,'2011-02-23 01:16:17',NULL,NULL,NULL,NULL,NULL,NULL,0),(69,'YUNDA',NULL,0,'2011-02-23 01:16:16',NULL,NULL,NULL,NULL,NULL,NULL,0),(70,'YUNDA',NULL,0,'2011-02-23 01:16:19',NULL,NULL,NULL,NULL,NULL,NULL,0),(71,'YUNDA',NULL,0,'2011-02-23 01:16:16',NULL,NULL,NULL,NULL,NULL,NULL,0),(72,'YUNDA',NULL,0,'2011-02-23 01:16:18',NULL,NULL,NULL,NULL,NULL,NULL,0),(73,'YUNDA',NULL,0,'2011-02-23 01:16:21',NULL,NULL,NULL,NULL,NULL,NULL,0),(74,'YUNDA',NULL,0,'2011-02-23 01:16:20',NULL,NULL,NULL,NULL,NULL,NULL,0),(75,'YUNDA',NULL,0,'2011-02-23 01:16:18',NULL,NULL,NULL,NULL,NULL,NULL,0),(101,'YUNDA',NULL,0,'2011-02-24 00:54:16',NULL,NULL,NULL,NULL,NULL,NULL,0),(102,'YUNDA',NULL,0,'2011-02-24 00:54:11',NULL,NULL,NULL,NULL,NULL,NULL,0),(103,'YUNDA',NULL,0,'2011-02-24 00:53:49',NULL,NULL,NULL,NULL,NULL,NULL,0),(104,'YUNDA',NULL,0,'2011-02-24 00:54:00',NULL,NULL,NULL,NULL,NULL,NULL,0),(105,'YUNDA',NULL,0,'2011-02-24 00:53:54',NULL,NULL,NULL,NULL,NULL,NULL,0),(151,'YUNDA',NULL,0,'2011-02-24 01:02:46',NULL,NULL,NULL,NULL,NULL,NULL,0),(152,'YUNDA',NULL,0,'2011-02-24 01:01:58',NULL,NULL,NULL,NULL,NULL,NULL,0),(153,'YUNDA',NULL,0,'2011-02-24 01:02:45',NULL,NULL,NULL,NULL,NULL,NULL,0),(154,'YUNDA',NULL,0,'2011-02-24 01:01:17',NULL,NULL,NULL,NULL,NULL,NULL,0),(155,'YUNDA',NULL,0,'2011-02-24 01:01:44',NULL,NULL,NULL,NULL,NULL,NULL,0),(156,'YUNDA',NULL,0,'2011-02-24 01:02:12',NULL,NULL,NULL,NULL,NULL,NULL,0),(201,'YUNDA',NULL,0,'2011-02-24 22:09:03',NULL,NULL,NULL,NULL,NULL,NULL,0),(202,'YUNDA',NULL,0,'2011-02-24 22:09:03',NULL,NULL,NULL,NULL,NULL,NULL,0),(203,'YUNDA',NULL,0,'2011-02-24 22:08:59',NULL,NULL,NULL,NULL,NULL,NULL,0),(204,'YUNDA',NULL,0,'2011-02-24 22:09:02',NULL,NULL,NULL,NULL,NULL,NULL,0),(205,'YUNDA',NULL,0,'2011-02-24 22:08:57',NULL,NULL,NULL,NULL,NULL,NULL,0),(206,'YUNDA',NULL,0,'2011-02-24 22:09:17',NULL,NULL,NULL,NULL,NULL,NULL,0),(207,'YUNDA',NULL,0,'2011-02-24 22:09:19',NULL,NULL,NULL,NULL,NULL,NULL,0),(208,'YUNDA',NULL,0,'2011-02-24 22:09:17',NULL,NULL,NULL,NULL,NULL,NULL,0),(209,'YUNDA',NULL,0,'2011-02-24 22:09:18',NULL,NULL,NULL,NULL,NULL,NULL,0),(210,'YUNDA',NULL,0,'2011-02-24 22:09:19',NULL,NULL,NULL,NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `order_delivery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_delivery_item`
--

DROP TABLE IF EXISTS `order_delivery_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_delivery_item` (
  `oid` int(11) NOT NULL,
  `header` int(11) NOT NULL,
  `item_oid` int(11) NOT NULL,
  `book_count` decimal(10,2) NOT NULL,
  `unit_cost` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  KEY `ODI_FK_HEADER` (`header`),
  KEY `ODI_FK_ITEM` (`item_oid`),
  CONSTRAINT `ODI_FK_HEADER` FOREIGN KEY (`header`) REFERENCES `order_delivery` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ODI_FK_ITEM` FOREIGN KEY (`item_oid`) REFERENCES `order_item` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_delivery_item`
--

LOCK TABLES `order_delivery_item` WRITE;
/*!40000 ALTER TABLE `order_delivery_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_delivery_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_delivery_po`
--

DROP TABLE IF EXISTS `order_delivery_po`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_delivery_po` (
  `do_oid` int(11) NOT NULL,
  `po_oid` int(11) NOT NULL,
  PRIMARY KEY (`do_oid`,`po_oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_delivery_po`
--

LOCK TABLES `order_delivery_po` WRITE;
/*!40000 ALTER TABLE `order_delivery_po` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_delivery_po` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_disp_item`
--

DROP TABLE IF EXISTS `order_disp_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_disp_item` (
  `oid` int(11) NOT NULL,
  `name` varchar(500) NOT NULL,
  `count` int(10) NOT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  `order_header` int(11) NOT NULL,
  `actual_price` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`oid`),
  KEY `order_disp_item_fk_header` (`order_header`),
  CONSTRAINT `order_disp_item_fk_header` FOREIGN KEY (`order_header`) REFERENCES `order_common` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_disp_item`
--

LOCK TABLES `order_disp_item` WRITE;
/*!40000 ALTER TABLE `order_disp_item` DISABLE KEYS */;
INSERT INTO `order_disp_item` VALUES (51,'香港朗文原版小学教材Longman Welcome to English 2B 二年级下册',3,81.00,261,195.00),(55,'香港朗文原版小学教材Longman Welcome to English 1B 一年级下册',3,81.00,259,195.00),(58,'easy to read Level1 The Magic Porridge Pot 魔法锅 6',1,85.00,262,85.00),(61,'T#正版台湾幼儿园英文教材love english lv2 小班下3-4岁送DVD CD',4,100.00,258,340.00),(69,'Super Phonics正版台湾东西小学生自然拼读教材 全3册！送大礼包',1,305.00,275,305.00),(73,'正版Phonics kids台湾东西幼儿自然拼读发音教材1-3|现货送大礼包',1,299.00,270,280.00),(77,'香港朗文原版小学教材Longman Welcome to English 1B 一年级下册',1,81.00,265,81.00),(78,'原版美国小学语文数学科学综合学科英语教材SOE一年级(预定)试听',1,460.00,252,360.00),(82,'Houghton Mifflin Reading Frindle 英文阅读 粉灵豆',1,12.00,274,10.00),(83,'T#正版 台湾幼儿园英教材LOVE ENGLISH starter1 2-3岁托班',1,100.00,255,100.00),(86,'香港朗文原版小学教材Longman Welcome to English 1B 一年级下册',14,81.00,268,980.00),(151,'Phonics kids 台湾幼儿园自然拼读发音教材4B 送CD DVD正版秒杀！',3,69.00,271,107.00),(161,'香港朗文原版小学教材Longman Welcome to English 1A 一年级上册',1,81.00,269,81.00),(162,'正版-Phonics Kids 台湾幼儿园自然拼读/拼音经典教材 2A 送AVCD',5,57.00,271,185.00),(168,'东方娃娃绘本英语2010全新套装正版六本一套',1,77.00,264,7.41),(170,'Phonics kids 台湾东西 幼儿园自然拼读发音教材4A 正版秒杀！',3,69.00,271,107.00),(175,'补拍',263,1.00,267,263.00),(176,'正版-Phonics Kids 台湾幼儿园自然拼读/拼音经典教材 2B 送AVCD',5,57.00,271,85.00),(178,'B5-强烈推荐！原版名家绘本小宝宝必备-小小孩系列二本 送美音mp3',1,29.99,264,15.39),(180,'幼儿启蒙颜色 可爱立体 英文书 Curious Kitties 好奇的小猫',1,15.00,264,3.20),(201,'正版台湾启蒙亲子英文《跟小小孩说英文》港台热卖含AVCD送礼包',1,415.00,260,365.00),(202,'T#正版包邮现货秒杀台湾东西图书LOVE ENGLISH全套1-6|量大可批发',1,580.00,352,580.00),(203,'H#美国科普阅读-恐龙大全Dinosaurs-英文原版百科读物',1,58.00,305,58.00),(204,'AP#Up Close-Sharks之神奇鲨鱼|英美中小学畅普读物！考ISEE推荐',1,16.00,305,16.00),(205,'AP#Up Close-Dinosaurs之恐龙部落|原版英国小学科普读物！独家！',1,16.00,305,16.00),(206,'Phonics kids台湾东西幼儿园自然拼读教材4-6套装正版平装秒杀！',1,359.00,273,359.00),(207,'AP#Up Close-T.Rex之恐龙之王-霸王龙|英国小学生科普阅读教材！',1,16.00,305,16.00),(208,'正版-Phonics Kids 台湾幼儿园自然拼读/拼音经典教材 2B 送AVCD',1,57.00,351,40.00),(209,'S#20 英国USBORNE-非常考孩子智力的动物迷宫书-AnimalMazes',1,15.00,302,15.00),(210,'香港朗文原版小学教材Longman Welcome to English 1A 一年级上册',1,81.00,202,100.00),(211,'G#开发孩子想象力-太空中的下午茶-Teatime in space 3-8',1,28.00,257,28.00),(212,'T#正版 台湾幼儿园英教材LOVE ENGLISH starter1 2-3岁托班',1,100.00,353,75.00),(213,'O#原版 龙之诗 Dragon Poem - 献给所有喜欢恐龙的宝宝',1,19.00,305,19.00),(214,'原版美国小学语文数学科学综合学科英语教材SOE一年级(预定)试听',1,460.00,251,460.00),(215,'TFKids - 美国小学原版分级读物 二年级下 套装 科普类',1,60.00,253,60.00),(216,'T# 0-1岁 宝宝英语启蒙 精选原版书籍和儿歌套餐',10,128.00,205,3900.00),(217,'TFKids - 美国小学原版分级读物 二年级上 套装 科普类',1,60.00,253,60.00),(218,'Phonics kids台湾东西幼儿园自然拼读教材4-6套装正版平装秒杀！',1,359.00,257,359.00),(219,'台湾东西出版社Phonics Kids 自然拼读 幼儿园教材3B 送AVCD 正版',1,57.00,351,40.00),(220,'soe，K，2全套，1，3，4，5练习册和测评题，预定',1,800.00,256,800.00),(221,'1，3，4，5年级的学生书+六年级学生套装+七个年级的CD',1,2740.00,301,2740.00),(222,'美国原版幼儿园教材 Yippee Red Level 小班 另配多媒体CD软件',2,260.00,204,520.00),(223,'香港朗文原版小学教材Longman Welcome to English 2A 二年级上册',1,81.00,354,81.00),(224,'正版Phonics kids台湾东西幼儿自然拼读发音教材1-3|现货送大礼包',1,299.00,257,299.00),(225,'G#精装大嘴猴 - Only In Dreams -亚马逊五星推荐 3-8',1,35.00,257,35.00),(226,'正版Phonics kids台湾东西幼儿自然拼读发音教材1-3|现货送大礼包',1,299.00,254,299.00),(227,'Super Phonics正版台湾东西小学生自然拼读教材 全3册！送大礼包',1,305.00,272,305.00),(228,'TFKids - 美国小学原版分级读物 三年级上 套装 科普类',1,60.00,253,60.00),(229,'G#名家绘本-亚马逊五星-Oliver Who Would Not Sleep!3-8',1,29.00,257,29.00),(230,'G#英文I\'m a truck driver我是小小卡车司机！3-8岁小车迷必备',1,29.00,302,29.00),(231,'T#正版 台湾幼儿园英教材LOVE ENGLISH starter1 2-3岁托班',1,100.00,356,80.00),(232,'正版-Phonics Kids 台湾幼儿园自然拼读/拼音经典教材 2A 送AVCD',1,57.00,351,40.00),(233,'正版台湾东西Phonics Kids自然拼读音幼儿园教材1A 量大另有优惠',1,57.00,353,45.00),(234,'T#正版 台湾幼儿园英教材LOVE ENGLISH starter1 2-3岁托班',1,100.00,206,75.00),(235,'台湾东西出版社Phonics Kids 自然拼读 幼儿园教材3A 送AVCD 正版',1,57.00,351,40.00),(236,'香港朗文原版小学教材Longman Welcome to English 2A 二年级上册',1,81.00,201,70.00),(237,'soe 1,3,4，5学生书+六年级全套，送K-6年级刻录CD',1,2000.00,263,2000.00),(238,'正版Phonics kids台湾东西幼儿自然拼读发音教材1-3|现货送大礼包',1,299.00,352,299.00),(239,'AP#Up Close-Space之神秘太空|英美中小学畅销科普读物！',1,16.00,305,16.00),(240,'T#正版Sight Word Kids台湾幼儿教材学习英文常见字全10册送礼包',1,650.00,257,650.00),(241,'美国Macmillan 幼儿园英文教材 Finger Print 3A 适用3-6岁',1,188.00,355,150.40),(242,'正版Phonics kids台湾东西幼儿自然拼读发音教材1-3|现货送大礼包',1,299.00,266,299.00),(243,'T#进口幼儿园英文入门教材LOVE ENGLISH lv3 中班上送DVD CD正版',5,100.00,405,440.00),(244,'B5-强烈推荐！原版名家绘本小宝宝必备-小小孩系列二本 送美音mp3',1,29.99,402,26.39),(245,'B7-原版宝宝英文启蒙书Learning My Numbers认识数字 趣味纸板',1,9.90,402,8.71),(246,'T#台湾幼儿园英文教材LOVE ENGLISH level1小班上 量大另有优惠',1,100.00,401,5.00),(247,'T#正版台湾幼儿园英文教材love english lv2 小班下3-4岁送DVD CD',6,100.00,403,510.00),(248,'T#正版台湾幼儿园英文教材love english lv2 小班下3-4岁送DVD CD',2,100.00,405,176.00),(249,'B8-美国幼儿学单词的第一本书 原版Baby\'s First Snooze送美音mp3',1,16.00,402,13.70),(250,'幼儿启蒙颜色 可爱立体 英文书 Curious Kitties 好奇的小猫',1,15.00,402,13.20),(251,'Phonics Box 字母拼读教具组 台湾东西拼读教师用书 配CD正版',1,688.00,404,688.00),(252,'T#正版 台湾幼儿园英教材LOVE ENGLISH starter1 2-3岁托班',1,100.00,402,75.00),(253,'原版儿童教育指南 幼儿园 What Your Kindergarten Needs to know',1,135.00,303,135.00),(254,'正版Phonics kids台湾东西幼儿自然拼读发音教材1-3|现货送大礼包',1,299.00,303,299.00),(255,'soe 一年级正版音频CD',1,120.00,304,120.00),(256,'台湾东西出版社Phonics Kids 自然拼读 幼儿园教材3B 送AVCD 正版',1,57.00,203,57.00),(257,'S#1.0-原版幼儿园宝宝 创意学习 手工 4本涂色书套餐-送水彩笔',1,39.00,203,30.00),(258,'T#正版 台湾幼儿园英教材LOVE ENGLISH starter1 2-3岁托班',1,100.00,303,100.00),(259,'台湾东西出版社Phonics Kids 自然拼读 幼儿园教材3A 送AVCD 正版',1,57.00,203,57.00),(260,'T#正版 台湾幼儿园英教材LOVE ENGLISH starter1 2-3岁托班',1,100.00,409,75.00),(261,'Phonics kids台湾东西幼儿园自然拼读教材4-6套装正版平装秒杀！',1,359.00,407,359.00),(262,'Super Phonics正版台湾东西小学生自然拼读教材 全3册！送大礼包',1,305.00,406,305.00),(263,'B2-幼儿启蒙英文-小小孩名家绘本2- Say Goodnight！纸板*送mp3',1,16.00,409,14.08),(264,'香港朗文1B  3B  5B',1,263.00,410,263.00),(265,'G#You are what you eat！-平装科普阅读书 - 教给孩子不挑食',1,12.00,409,10.52),(266,'T#台湾幼儿园英文启蒙教材LOVE ENGLISH starter2 2-3岁适用-托班',1,100.00,409,75.00),(267,'B#超有趣！0-4岁英文启蒙故事纸板书Are you My Mommy?小鸭！拟声',1,15.00,409,13.20),(268,'美国高中物理教材',1,28.00,408,28.00);
/*!40000 ALTER TABLE `order_disp_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_item` (
  `oid` int(11) NOT NULL,
  `order_header` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  `unit_cost` decimal(10,2) DEFAULT NULL,
  `book_oid` int(11) NOT NULL,
  `is_agent` int(1) DEFAULT '0',
  `sent_count` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`oid`),
  KEY `item_fk_po` (`order_header`),
  KEY `item_fk_book` (`book_oid`),
  CONSTRAINT `item_fk_book` FOREIGN KEY (`book_oid`) REFERENCES `profile_book` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `item_fk_po` FOREIGN KEY (`order_header`) REFERENCES `order_common` (`oid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (19,2,2,80.00,0.00,47,0,0),(20,2,11,45.00,0.00,259,0,0),(21,2,12,45.00,0.00,52,0,0),(22,2,12,80.00,0.00,256,0,0),(23,2,7,45.00,0.00,55,0,0),(24,2,2,45.00,0.00,50,0,0),(25,2,11,45.00,0.00,257,0,0),(26,2,10,45.00,0.00,255,0,0),(27,2,6,80.00,0.00,54,0,0),(28,2,1,120.00,120.00,252,0,0),(29,2,11,80.00,0.00,254,0,0),(30,2,1,120.00,120.00,253,0,0),(31,2,2,80.00,0.00,48,0,0),(32,2,18,80.00,0.00,53,0,0),(33,2,1,150.00,0.00,260,0,0),(34,2,2,80.00,0.00,49,0,0),(35,2,12,80.00,0.00,258,0,0),(36,2,2,120.00,120.00,51,0,0),(67,2,27,45.00,NULL,38,0,0),(68,2,20,45.00,NULL,35,0,0),(69,2,24,90.00,NULL,43,0,0),(70,2,4,398.00,NULL,301,0,0),(71,2,26,30.00,NULL,29,0,0),(72,2,26,45.00,NULL,36,0,0),(73,2,27,45.00,NULL,40,0,0),(74,2,17,90.00,NULL,42,0,0),(75,2,4,30.00,NULL,33,0,0),(76,2,27,45.00,NULL,39,0,0),(77,2,3,30.00,NULL,34,0,0),(78,2,26,30.00,NULL,32,0,0),(79,2,27,45.00,NULL,37,0,0),(80,2,4,90.00,NULL,41,0,0),(81,2,7,30.00,NULL,30,0,0),(82,2,8,30.00,NULL,31,0,0),(83,2,1,200.00,NULL,3,0,0),(84,2,6,60.00,NULL,2,0,0),(85,2,1,450.00,NULL,45,0,0),(86,2,16,200.00,NULL,1,0,0),(87,2,18,60.00,NULL,302,0,0),(88,2,1,450.00,NULL,46,0,0),(89,2,11,60.00,NULL,23,0,0),(90,2,4,450.00,NULL,306,0,0),(91,2,1,60.00,NULL,303,0,0),(92,2,2,450.00,NULL,44,0,0),(93,2,4,450.00,NULL,307,0,0),(94,2,3,450.00,NULL,305,0,0),(95,2,4,450.00,NULL,304,0,0),(96,2,1,40.00,NULL,310,0,0),(97,2,61,40.00,NULL,308,0,0),(98,2,63,5.00,NULL,309,0,0),(99,2,46,5.00,NULL,312,0,0),(100,2,50,40.00,NULL,311,0,0),(101,2,3,0.00,NULL,340,0,0),(102,2,3,0.00,NULL,323,0,0),(103,2,2,0.00,NULL,344,0,0),(104,2,3,0.00,NULL,328,0,0),(105,2,3,0.00,NULL,324,0,0),(106,2,3,0.00,NULL,341,0,0),(107,2,3,0.00,NULL,321,0,0),(108,2,3,0.00,NULL,339,0,0),(109,2,4,0.00,NULL,317,0,0),(110,2,4,0.00,NULL,320,0,0),(111,2,7,0.00,NULL,345,0,0),(112,2,3,0.00,NULL,342,0,0),(113,2,3,0.00,NULL,332,0,0),(114,2,4,0.00,NULL,319,0,0),(115,2,3,0.00,NULL,314,0,0),(116,2,3,0.00,NULL,334,0,0),(117,2,3,0.00,NULL,335,0,0),(118,2,2,0.00,NULL,337,0,0),(119,2,3,0.00,NULL,331,0,0),(120,2,2,0.00,NULL,313,0,0),(121,2,3,0.00,NULL,336,0,0),(122,2,6,0.00,NULL,329,0,0),(123,2,3,0.00,NULL,315,0,0),(124,2,3,0.00,NULL,316,0,0),(125,2,3,0.00,NULL,333,0,0),(126,2,4,0.00,NULL,325,0,0),(127,2,3,0.00,NULL,322,0,0),(128,2,3,0.00,NULL,330,0,0),(129,2,3,0.00,NULL,327,0,0),(130,2,3,0.00,NULL,343,0,0),(131,2,4,0.00,NULL,318,0,0),(132,2,4,0.00,NULL,326,0,0),(133,2,2,0.00,NULL,338,0,0),(134,2,4,0.00,NULL,353,0,0),(135,2,4,0.00,NULL,354,0,0),(136,2,2,0.00,NULL,350,0,0),(137,2,3,0.00,NULL,356,0,0),(138,2,3,0.00,NULL,346,0,0),(139,2,2,0.00,NULL,349,0,0),(140,2,3,0.00,NULL,348,0,0),(141,2,3,0.00,NULL,352,0,0),(142,2,3,0.00,NULL,351,0,0),(143,2,4,0.00,NULL,355,0,0),(144,2,3,0.00,NULL,347,0,0),(145,2,5,35.00,NULL,370,0,0),(146,2,15,35.00,NULL,364,0,0),(147,2,5,35.00,NULL,371,0,0),(148,2,16,35.00,NULL,365,0,0),(149,2,3,0.00,NULL,357,0,0),(150,2,4,35.00,NULL,367,0,0),(151,2,15,40.00,NULL,360,0,0),(152,2,3,0.00,NULL,358,0,0),(153,2,5,35.00,NULL,369,0,0),(154,2,3,0.00,NULL,359,0,0),(155,2,17,40.00,NULL,363,0,0),(156,2,5,35.00,NULL,368,0,0),(157,2,7,35.00,NULL,366,0,0),(158,2,26,40.00,NULL,362,0,0),(159,2,16,40.00,NULL,361,0,0),(160,2,1,50.00,NULL,377,0,0),(161,2,6,50.00,NULL,379,0,0),(162,2,7,50.00,NULL,378,0,0),(163,2,14,30.00,NULL,373,0,0),(164,2,7,50.00,NULL,381,0,0),(165,2,7,50.00,NULL,380,0,0),(166,2,29,30.00,NULL,376,0,0),(167,2,16,30.00,NULL,372,0,0),(168,2,28,30.00,NULL,374,0,0),(169,2,7,50.00,NULL,382,0,0),(170,2,28,30.00,NULL,375,0,0),(201,2,4,68.00,NULL,413,0,0),(202,2,7,68.00,NULL,407,0,0),(203,2,1,98.00,NULL,402,0,0),(204,2,1,98.00,NULL,405,0,0),(205,2,1,98.00,NULL,403,0,0),(206,2,5,68.00,NULL,412,0,0),(207,2,5,68.00,NULL,410,0,0),(208,2,1,98.00,NULL,404,0,0),(209,2,4,98.00,NULL,401,0,0),(210,2,7,68.00,NULL,409,0,0),(211,2,6,68.00,NULL,408,0,0),(212,2,6,68.00,NULL,411,0,0),(213,2,1,68.00,NULL,406,0,0),(214,2,2,44.00,NULL,418,0,0),(215,2,1,66.00,NULL,425,0,0),(216,2,1,66.00,NULL,421,0,0),(217,2,1,44.00,NULL,424,0,0),(218,2,2,37.00,NULL,419,0,0),(219,2,1,95.00,NULL,416,0,0),(220,2,5,50.00,NULL,414,0,0),(221,2,1,66.00,NULL,422,0,0),(222,2,1,88.00,NULL,417,0,0),(223,2,3,66.00,NULL,426,0,0),(224,2,1,44.00,NULL,420,0,0),(225,2,1,44.00,NULL,423,0,0),(226,2,2,66.00,NULL,415,0,0),(227,2,18,30.00,NULL,431,0,0),(228,2,18,30.00,NULL,435,0,0),(229,2,16,30.00,NULL,428,0,0),(230,2,17,30.00,NULL,433,0,0),(231,2,17,30.00,NULL,429,0,0),(232,2,12,30.00,NULL,430,0,0),(233,2,3,30.00,NULL,427,0,0),(234,2,17,30.00,NULL,432,0,0),(235,2,18,30.00,NULL,434,0,0),(236,2,7,50.00,NULL,436,0,0),(237,2,10,50.00,NULL,441,0,0),(238,2,10,50.00,NULL,439,0,0),(239,2,7,50.00,NULL,437,0,0),(240,2,10,50.00,NULL,443,0,0),(241,2,10,50.00,NULL,442,0,0),(242,2,10,50.00,NULL,440,0,0),(243,2,4,50.00,NULL,444,0,0),(244,2,4,50.00,NULL,445,0,0),(245,2,10,50.00,NULL,438,0,0),(246,2,2,96.00,NULL,448,0,0),(247,2,2,96.00,NULL,450,0,0),(248,2,4,96.00,NULL,447,0,0),(249,2,2,96.00,NULL,451,0,0),(250,2,1,96.00,NULL,449,0,0),(251,2,2,96.00,NULL,446,0,0),(252,2,4,10.00,NULL,454,0,0),(253,2,8,55.00,NULL,65,0,0),(254,2,9,10.00,NULL,66,0,0),(255,2,1,55.00,NULL,70,0,0),(256,2,12,55.00,NULL,60,0,0),(257,2,11,10.00,NULL,68,0,0),(258,2,18,55.00,NULL,62,0,0),(259,2,3,55.00,NULL,63,0,0),(260,2,11,55.00,NULL,59,0,0),(261,2,23,55.00,NULL,64,0,0),(262,2,12,10.00,NULL,67,0,0),(263,2,1,10.00,NULL,69,0,0),(264,2,21,10.00,NULL,61,0,0),(265,2,3,55.00,NULL,453,0,0),(266,2,1,102.00,NULL,452,0,0),(267,2,1,102.00,NULL,58,0,0),(268,2,9,78.00,NULL,479,0,0),(269,2,2,85.00,NULL,456,0,0),(270,2,3,58.00,NULL,462,0,0),(271,2,3,58.00,NULL,470,0,0),(272,2,6,48.00,NULL,478,0,0),(273,2,2,58.00,NULL,467,0,0),(274,2,1,48.00,NULL,480,0,0),(275,2,2,48.00,NULL,472,0,0),(276,2,2,85.00,NULL,457,0,0),(277,2,2,58.00,NULL,464,0,0),(278,2,7,330.00,NULL,455,0,0),(279,2,1,48.00,NULL,476,0,0),(280,2,3,58.00,NULL,469,0,0),(281,2,2,48.00,NULL,477,0,0),(282,2,1,48.00,NULL,473,0,0),(283,2,2,58.00,NULL,463,0,0),(284,2,2,120.00,NULL,460,0,0),(285,2,1,48.00,NULL,475,0,0),(286,2,5,128.00,NULL,459,0,0),(287,2,2,58.00,NULL,466,0,0),(288,2,3,58.00,NULL,465,0,0),(289,2,2,58.00,NULL,468,0,0),(290,2,2,58.00,NULL,461,0,0),(291,2,1,48.00,NULL,481,0,0),(292,2,2,85.00,NULL,458,0,0),(293,2,2,48.00,NULL,471,0,0),(294,2,1,48.00,NULL,474,0,0),(315,53,11,92.50,0.00,43,0,0),(316,53,14,92.50,0.00,42,0,0),(317,53,2,50.00,0.00,443,0,0),(318,53,3,50.00,0.00,440,0,0),(319,53,4,50.00,0.00,437,0,0),(320,53,3,50.00,0.00,441,0,0),(321,53,7,50.00,0.00,438,0,0),(322,53,4,50.00,0.00,436,0,0),(323,53,6,50.00,0.00,442,0,0),(324,53,7,50.00,0.00,439,0,0),(325,53,47,200.00,NULL,1,0,0),(326,53,1,60.00,NULL,2,0,0),(327,53,5,60.00,NULL,13,0,0),(328,53,7,398.00,NULL,301,0,0),(329,53,11,60.00,NULL,302,0,0),(330,53,8,60.00,NULL,10,0,0),(331,53,19,200.00,NULL,251,0,0),(332,53,8,60.00,NULL,9,0,0),(333,53,2,60.00,NULL,7,0,0),(334,53,3,60.00,NULL,12,0,0),(335,53,2,60.00,NULL,6,0,0),(336,53,30,200.00,NULL,11,0,0),(337,53,25,200.00,NULL,14,0,0),(338,53,27,200.00,NULL,5,0,0),(339,53,152,40.00,NULL,362,0,0),(340,53,161,40.00,NULL,363,0,0),(341,53,196,40.00,NULL,361,0,0),(342,53,47,30.00,NULL,29,0,0),(343,53,58,45.00,NULL,39,0,0),(344,53,61,45.00,NULL,38,0,0),(345,53,52,30.00,NULL,34,0,0),(346,53,106,45.00,NULL,35,0,0),(347,53,64,30.00,NULL,33,0,0),(348,53,104,45.00,NULL,36,0,0),(349,53,193,40.00,NULL,360,0,0),(350,53,47,30.00,NULL,32,0,0),(351,53,68,30.00,NULL,30,0,0),(352,53,59,45.00,NULL,37,0,0),(353,53,62,30.00,NULL,31,0,0),(354,53,60,45.00,NULL,40,0,0),(355,53,10,230.00,NULL,482,0,0),(356,53,2,200.00,NULL,483,0,0),(401,101,5,120.00,0.00,460,0,0),(402,101,19,330.00,330.00,455,0,0),(403,101,1,200.00,200.00,483,0,0),(404,101,19,45.00,45.00,40,0,0),(405,101,17,45.00,45.00,39,0,0),(406,101,12,30.00,NULL,34,0,0),(407,101,17,45.00,NULL,38,0,0),(408,101,18,45.00,NULL,37,0,0),(425,101,1,265.00,NULL,103,0,0),(426,101,2,265.00,NULL,106,0,0),(427,101,1,90.00,NULL,42,0,0),(428,101,2,30.00,NULL,32,0,0),(429,101,1,265.00,NULL,101,0,0),(430,101,3,265.00,NULL,105,0,0),(431,101,1,265.00,NULL,102,0,0),(432,101,1,90.00,NULL,43,0,0),(433,101,5,30.00,NULL,31,0,0),(434,101,11,30.00,NULL,30,0,0),(435,101,1,90.00,NULL,41,0,0),(436,101,1,265.00,NULL,104,0,0),(437,101,13,30.00,NULL,29,0,0),(438,101,2,45.00,NULL,36,0,0),(439,101,2,30.00,NULL,33,0,0),(440,101,2,45.00,NULL,35,0,0),(441,101,1,120.00,NULL,25,0,0),(442,101,1,98.00,NULL,371,0,0),(443,101,1,98.00,NULL,367,0,0),(444,101,1,60.00,NULL,2,0,0),(445,101,1,320.00,NULL,8,0,0),(446,101,1,48.00,NULL,343,0,0),(447,101,2,35.00,NULL,362,0,0),(448,101,1,320.00,NULL,16,0,0),(449,101,1,85.00,NULL,328,0,0),(450,101,1,85.00,NULL,317,0,0),(451,101,1,120.00,NULL,201,0,0),(452,101,2,200.00,NULL,1,0,0),(453,101,3,48.00,NULL,329,0,0),(454,101,2,35.00,NULL,361,0,0),(455,101,1,200.00,NULL,3,0,0),(456,101,1,85.00,NULL,342,0,0),(457,101,1,35.00,NULL,366,0,0),(458,101,1,120.00,NULL,158,0,0),(459,101,1,320.00,NULL,15,0,0),(460,101,1,85.00,NULL,341,0,0),(461,101,2,35.00,NULL,363,0,0),(462,101,2,35.00,NULL,360,0,0),(463,101,1,120.00,NULL,28,0,0),(464,101,2,120.00,NULL,26,0,0),(465,101,1,85.00,NULL,318,0,0),(466,101,2,120.00,NULL,27,0,0),(467,101,1,60.00,NULL,4,0,0),(468,101,1,85.00,NULL,319,0,0),(469,101,1,98.00,NULL,370,0,0),(470,101,1,85.00,NULL,327,0,0),(471,101,1,450.00,NULL,44,0,0),(472,101,1,450.00,NULL,45,0,0),(473,101,1,450.00,NULL,46,0,0),(528,101,1,50.00,NULL,378,0,0),(529,101,1,45.00,NULL,52,0,0),(530,101,1,80.00,NULL,53,0,0),(531,101,1,80.00,NULL,49,0,0),(532,101,2,95.00,NULL,509,0,0),(533,101,2,48.00,NULL,510,0,0),(534,101,1,80.00,NULL,48,0,0),(535,101,1,55.00,NULL,453,0,0),(536,101,1,44.00,NULL,420,0,0),(537,101,1,180.00,NULL,505,0,0),(538,101,1,50.00,NULL,377,0,0),(539,101,1,42.00,NULL,503,0,0),(540,101,1,95.00,NULL,506,0,0),(541,101,1,48.00,NULL,512,0,0),(542,101,1,55.00,NULL,70,0,0),(543,101,3,95.00,NULL,416,0,0),(544,101,1,108.00,NULL,58,0,0),(545,101,1,50.00,NULL,380,0,0),(546,101,1,66.00,NULL,426,0,0),(547,101,1,48.00,NULL,471,0,0),(548,101,1,180.00,NULL,501,0,0),(549,101,3,10.00,NULL,61,0,0),(550,101,1,55.00,NULL,65,0,0),(551,101,1,95.00,NULL,507,0,0),(552,101,1,108.00,NULL,56,0,0),(553,101,1,108.00,NULL,57,0,0),(554,101,1,10.00,NULL,69,0,0),(555,101,1,50.00,NULL,382,0,0),(556,101,2,48.00,NULL,477,0,0),(557,101,1,50.00,NULL,414,0,0),(558,101,1,48.00,NULL,473,0,0),(559,101,1,48.00,NULL,511,0,0),(560,101,1,48.00,NULL,474,0,0),(561,101,1,88.00,NULL,417,0,0),(562,101,1,0.00,NULL,68,0,0),(563,101,2,44.00,NULL,504,0,0),(564,101,1,165.00,NULL,51,0,0),(565,101,1,55.00,NULL,60,0,0),(566,101,1,45.00,NULL,50,0,0),(567,101,1,50.00,NULL,379,0,0),(568,101,1,80.00,NULL,47,0,0),(569,101,3,55.00,NULL,62,0,0),(570,101,1,55.00,NULL,453,0,0),(571,101,1,45.00,NULL,54,0,0),(572,101,1,95.00,NULL,508,0,0),(573,101,1,48.00,NULL,513,0,0),(574,101,1,10.00,NULL,66,0,0),(575,101,1,66.00,NULL,425,0,0),(576,101,1,88.00,NULL,502,0,0),(577,101,1,10.00,NULL,59,0,0),(578,101,1,50.00,NULL,381,0,0),(579,101,1,10.00,NULL,67,0,0),(580,101,1,55.00,NULL,64,0,0),(581,101,1,45.00,NULL,55,0,0),(582,101,2,60.00,NULL,152,0,0),(583,101,3,60.00,NULL,157,0,0),(584,101,2,60.00,NULL,153,0,0),(585,101,1,60.00,NULL,154,0,0),(586,101,2,60.00,NULL,151,0,0),(587,101,2,60.00,NULL,156,0,0),(588,101,3,60.00,NULL,155,0,0),(601,101,1,37.50,NULL,435,0,0),(602,101,1,37.50,NULL,430,0,0),(603,101,1,37.50,NULL,432,0,0),(604,101,1,37.50,NULL,428,0,0),(605,101,1,37.50,NULL,429,0,0),(606,101,1,37.50,NULL,431,0,0),(607,101,1,50.00,NULL,436,0,0),(608,101,1,37.50,NULL,434,0,0),(609,101,1,50.00,NULL,437,0,0),(610,101,1,37.50,NULL,433,0,0),(611,101,1,37.50,NULL,427,0,0),(612,101,1,30.00,NULL,374,0,0),(613,101,2,68.00,NULL,408,0,0),(614,101,1,68.00,NULL,409,0,0),(615,101,1,68.00,NULL,413,0,0),(616,101,1,98.00,NULL,402,0,0),(617,101,1,68.00,NULL,411,0,0),(618,101,1,68.00,NULL,406,0,0),(619,101,1,68.00,NULL,407,0,0),(620,101,1,98.00,NULL,403,0,0),(651,2,5,180.00,NULL,24,0,0),(652,151,15,60.00,60.00,64,0,0),(653,151,10,60.00,60.00,453,0,0),(654,151,10,5.00,NULL,61,0,0),(655,151,20,5.00,NULL,454,0,0),(656,151,10,60.00,NULL,70,0,0),(657,151,20,60.00,NULL,63,0,0),(658,151,10,5.00,NULL,69,0,0),(659,151,1,60.00,NULL,65,0,0),(660,151,10,60.00,NULL,62,0,0),(661,151,1,5.00,NULL,66,0,0),(662,151,2,104.00,NULL,651,0,0),(663,151,2,104.00,NULL,58,0,0),(664,151,2,104.00,NULL,56,0,0),(665,151,2,104.00,NULL,452,0,0),(666,151,2,104.00,NULL,57,0,0),(667,151,2,104.00,NULL,652,0,0),(701,203,1,57.00,NULL,33,0,0),(702,203,1,57.00,NULL,34,0,0),(703,201,1,81.00,NULL,362,0,0),(704,202,1,81.00,NULL,360,0,0),(705,206,1,100.00,NULL,64,0,0),(751,265,1,81.00,NULL,361,0,0),(752,260,1,415.00,NULL,455,0,0),(754,258,4,100.00,NULL,63,0,0),(755,271,3,69.00,NULL,35,0,0),(756,261,3,81.00,NULL,363,0,0),(757,255,1,100.00,NULL,64,0,0),(758,259,3,81.00,NULL,361,0,0),(759,271,3,69.00,NULL,36,0,0),(760,271,5,57.00,NULL,31,0,0),(761,251,1,460.00,NULL,1,0,0),(762,269,1,81.00,NULL,360,0,0),(763,268,14,81.00,NULL,361,0,0),(764,271,5,57.00,NULL,32,0,0),(801,303,1,100.00,NULL,64,0,0),(851,353,1,57.00,NULL,29,0,0),(852,351,1,57.00,NULL,33,0,0),(853,356,1,100.00,NULL,64,0,0),(854,351,1,57.00,NULL,31,0,0),(855,351,1,57.00,NULL,32,0,0),(856,353,1,100.00,NULL,64,0,0),(857,354,1,81.00,NULL,362,0,0),(858,351,1,57.00,NULL,34,0,0),(901,252,1,10.00,NULL,552,0,0),(902,252,1,350.00,NULL,1,0,0),(951,402,1,100.00,NULL,64,0,0),(952,405,2,100.00,NULL,704,0,0),(953,403,6,100.00,NULL,704,0,0),(954,401,1,100.00,NULL,703,0,0),(955,405,5,100.00,NULL,705,0,0),(956,407,1,359.00,NULL,702,0,0),(957,409,1,100.00,NULL,453,0,0),(958,409,1,100.00,NULL,64,0,0);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_list_price`
--

DROP TABLE IF EXISTS `order_list_price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_list_price` (
  `book_oid` int(11) NOT NULL,
  `list_price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`book_oid`),
  KEY `order_list_price_pk` (`book_oid`),
  CONSTRAINT `order_list_price_pk` FOREIGN KEY (`book_oid`) REFERENCES `profile_book` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_list_price`
--

LOCK TABLES `order_list_price` WRITE;
/*!40000 ALTER TABLE `order_list_price` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_list_price` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile_book`
--

DROP TABLE IF EXISTS `profile_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile_book` (
  `isbn` varchar(20) NOT NULL,
  `name` varchar(1000) NOT NULL,
  `description` text,
  `oid` int(11) NOT NULL,
  `is_set` int(1) DEFAULT '0',
  PRIMARY KEY (`oid`),
  UNIQUE KEY `uk_isbn` (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile_book`
--

LOCK TABLES `profile_book` WRITE;
/*!40000 ALTER TABLE `profile_book` DISABLE KEYS */;
INSERT INTO `profile_book` VALUES ('9786070601262','SOE G1 Student Book',NULL,1,0),('9786070601286','SOE G1 Assessment',NULL,2,0),('9786070601316','SOE G2 Student Book',NULL,3,0),('9786070601323','SOE G2 Practice Book',NULL,4,0),('9786070601453','SOE G4 Student Book',NULL,5,0),('9786070601477','SOE G4 Assessment',NULL,6,0),('9786070601460','SOE G4 Practice Book',NULL,7,0),('SOE_G4','SOE G4 Student Set',NULL,8,1),('9786070601552','SOE G6 Practice Book',NULL,9,0),('9786070601569','SOE G6 Assessment',NULL,10,0),('9786070601545','SOE G6 Student Book',NULL,11,0),('9786070601514','SOE G5 Practice Book',NULL,12,0),('9786070601521','SOE G5 Assessment',NULL,13,0),('9786070601507','SOE G5 Student Book',NULL,14,0),('SOE_G5','SOE G5 Student Set',NULL,15,1),('SOE_G6','SOE G6 Student Set',NULL,16,1),('SOE_G1_IS','SOE G1 Interactive Software',NULL,17,0),('SOE_G2_IS','SOE G2 Interactive Software',NULL,18,0),('SOE_G3_IS','SOE G3 Interactive Software',NULL,19,0),('SOE_G4_IS','SOE G4 Interactive Software',NULL,20,0),('SOE_G5_IS','SOE G5 Interactive Software',NULL,21,0),('SOE_G6_IS','SOE G6 Interactive Software',NULL,22,0),('9786070601361','SOE GK Activity Pad',NULL,23,0),('9786070601255','SOE GK Student Book',NULL,24,0),('9786070601347','SOE G2 Assessment Teacher\'s Manual',NULL,25,0),('9786070601576','SOE G6 Assessment Teacher\'s Manual',NULL,26,0),('9786070601538','SOE G5 Assessment Teacher\'s Manual',NULL,27,0),('9786070601446','SOE G3 Assessment Teacher\'s Manual',NULL,28,0),('9789867054371','Phonics Kids 1A',NULL,29,0),('9789867054678','Phonics Kids 1B',NULL,30,0),('9789867054425','Phonics Kids 2A',NULL,31,0),('9789867054685','Phonics Kids 2B',NULL,32,0),('9789867054388','Phonics Kids 3A',NULL,33,0),('9789867054692','Phonics Kids 3B',NULL,34,0),('9789861832593','Phonics Kids 4A',NULL,35,0),('9789861832609','Phonics Kids 4B',NULL,36,0),('9789861832616','Phonics Kids 5A',NULL,37,0),('9789861832623','Phonics Kids 5B',NULL,38,0),('9789861832630','Phonics Kids 6A',NULL,39,0),('9789861832647','Phonics Kids 6B',NULL,40,0),('9789861831619','Phonics Kids 4',NULL,41,0),('9789861832388','Phonics Kids 5',NULL,42,0),('9789861832654','Phonics Kids 6',NULL,43,0),('9786070601392','SOE GK Teacher Book',NULL,44,0),('9786070601354','SOE G2 Teacher Book',NULL,45,0),('9786070601309','SOE G1 Teacher Book',NULL,46,0),('9789880056215','3H Phonics Book A',NULL,47,0),('9789880056222','3H Phonics Book B',NULL,48,0),('9789880029318','3H G1 Student Book',NULL,49,0),('9789880029370','3H G1 Workbook',NULL,50,0),('9789880056161','3H G2 Teacher Book',NULL,51,0),('9789880029387','3H G2 Workbook',NULL,52,0),('9789880029325','3H G2 Student Book',NULL,53,0),('9789880029332','3H G3 Student Book',NULL,54,0),('9789880029394','3H G3 Workbook',NULL,55,0),('9789861831251','Love English G3 Teacher Manual',NULL,56,0),('9789861831879','Love English G4 Teacher Manual',NULL,57,0),('9789861832371','Love English G5 Teacher Manual',NULL,58,0),('9789861832357','Love English G5 Student Book',NULL,59,0),('9789861831916','Love English G6 Student Book',NULL,60,0),('4710768495142','Love English G1 Workbook',NULL,61,0),('9789861831046','Love English G1 Student Book',NULL,62,0),('9789861831817','Love English G2 Student Book',NULL,63,0),('9789861832340','Love English S1',NULL,64,0),('9789861831855','Love English G4 Student Book',NULL,65,0),('4710768495494','Love English G4 Workbook',NULL,66,0),('4710768495920','Love English G5 Workbook',NULL,67,0),('4710768495500','Love English G6 Workbook',NULL,68,0),('4710768495159','Love English G3 Workbook',NULL,69,0),('9789861831053','Love English G3 Student Book',NULL,70,0),('9789620100604','Longman WTE 2A Teacher Resource',NULL,101,0),('9789620116544','Longman WTE 5A Teacher Resource',NULL,102,0),('9789620116551','Longman WTE 5B Teacher Resource',NULL,103,0),('9789620116537','Longman WTE 4B Teacher Resource',NULL,104,0),('9789620100598','Longman WTE 1B Teacher Resource',NULL,105,0),('9789620100581','Longman WTE 1A Teacher Resource',NULL,106,0),('7506009806702','SOE G6 Blackline Master',NULL,151,0),('7506009806696','SOE G5 Blackline Master',NULL,152,0),('7506009806689','SOE G4 Blackline Master',NULL,153,0),('7506009806672','SOE G3 Blackline Master',NULL,154,0),('7506009806665','SOE G2 Blackline Master',NULL,155,0),('7506009806658','SOE G1 Blackline Master',NULL,156,0),('7506009806641','SOE GK Blackline Master',NULL,157,0),('9786070601385','SOE GK Assessment Teacher Manual',NULL,158,0),('9786070601293','SOE G1 Assessment Teacher Manual',NULL,201,0),('9786070601408','SOE G3 Student Book',NULL,251,0),('9789880056154','3H G1 Teacher Book',NULL,252,0),('9789880056185','3H G4 Teacher Book',NULL,253,0),('9789880029363','3H G6 Student Book',NULL,254,0),('9789880029424','3H G6 Workbook',NULL,255,0),('9789880029349','3H G4 Student Book',NULL,256,0),('9789880029400','3H G4 Workbook',NULL,257,0),('9789880029356','3H G5 Student Book',NULL,258,0),('9789880029417','3H G5 Workbook',NULL,259,0),('9789880029622','3H G2 Picture Card',NULL,260,0),('PHONICS_BOX','Phonics Box',NULL,301,0),('9786070601279','SOE G1 Practice Book',NULL,302,0),('9786070601378','SOE GK Assessment',NULL,303,0),('9786070601491','SOE G4 Teacher Book',NULL,304,0),('9786070601675','SOE G6 Teacher Book',NULL,305,0),('9786070601668','SOE G5 Teacher Book',NULL,306,0),('9786070601439','SOE G3 Teacher Book',NULL,307,0),('9789570339413','Super Phonics 1 SB',NULL,308,0),('9789570339420','Super Phonics 1 WB',NULL,309,0),('9789570339567','Super Phonics 2 SB',NULL,310,0),('9789570339857','Super Phonics 3 SB',NULL,311,0),('9789570339864','Super Phonics 3 WB',NULL,312,0),('9789880011337','WTE 5A RW',NULL,313,0),('9789880011351','WTE 6A RW',NULL,314,0),('9789880011368','WTE 6B RW',NULL,315,0),('9789880011344','WTE 5B RW',NULL,316,0),('9789880011269','WTE 1B RW',NULL,317,0),('9789880011023','WTE 1B LS',NULL,318,0),('9789880010903','WTE 1B GP',NULL,319,0),('9789880011146','WTE 1B PT',NULL,320,0),('9789880011115','WTE 6A LS',NULL,321,0),('9789880011122','WTE 6B LS',NULL,322,0),('9789880011085','WTE 4B LS',NULL,323,0),('9789880011078','WTE 4A LS',NULL,324,0),('9789880011092','WTE 5A LS',NULL,325,0),('9789880011214','WTE 5A PT',NULL,326,0),('9789880011252','WTE 1A RW',NULL,327,0),('9789880010897','WTE 1A GP',NULL,328,0),('9789880011016','WTE 1A LS',NULL,329,0),('9789880011061','WTE 3B LS',NULL,330,0),('9789880011054','WTE 3A LS',NULL,331,0),('9789880011313','WTE 4A RW',NULL,332,0),('9789880011320','WTE 4B RW',NULL,333,0),('9789880011306','WTE 3B RW',NULL,334,0),('9789880011290','WTE 3A RW',NULL,335,0),('9789880011108','WTE 5B LS',NULL,336,0),('9789880010958','WTE 4A GP',NULL,337,0),('9789880010965','WTE 4B GP',NULL,338,0),('9789880010941','WTE 3B GP',NULL,339,0),('9789880010934','WTE 3A GP',NULL,340,0),('9789880010910','WTE 2A GP',NULL,341,0),('9789880011276','WTE 2A RW',NULL,342,0),('9789880011030','WTE 2A LS',NULL,343,0),('9789880011153','WTE 2A PT',NULL,344,0),('9789880011160','WTE 2B PT',NULL,345,0),('9789880011221','WTE 5B PT',NULL,346,0),('9789880011238','WTE 6A PT',NULL,347,0),('9789880011245','WTE 6B PT',NULL,348,0),('9789880011207','WTE 4B PT',NULL,349,0),('9789880011191','WTE 4A PT',NULL,350,0),('9789880011184','WTE 3B PT',NULL,351,0),('9789880011177','WTE 3A PT',NULL,352,0),('9789880010927','WTE 2B GP',NULL,353,0),('9789880011047','WTE 2B LS',NULL,354,0),('9789880011283','WTE 2B RW',NULL,355,0),('9789880010996','WTE 6A GP',NULL,356,0),('9789880011009','WTE 6B GP',NULL,357,0),('9789880010972','WTE 5A GP',NULL,358,0),('9789880010989','WTE 5B GP',NULL,359,0),('9789880010774','WTE 1A',NULL,360,0),('9789880010781','WTE 1B',NULL,361,0),('9789880010798','WTE 2A',NULL,362,0),('9789880010804','WTE 2B',NULL,363,0),('9789880010811','WTE 3A',NULL,364,0),('9789880010828','WTE 3B',NULL,365,0),('9789880010835','WTE 4A',NULL,366,0),('9789880010842','WTE 4B',NULL,367,0),('9789880010859','WTE 5A',NULL,368,0),('9789880010866','WTE 5B',NULL,369,0),('9789880010873','WTE 6A',NULL,370,0),('9789880010880','WTE 6B',NULL,371,0),('9789867054265','We Sing We Play 1',NULL,372,0),('9789861830919','We Sing We Play 3',NULL,373,0),('9789861830100','We Sing We Play 4',NULL,374,0),('9789861830902','We Sing We Play 5',NULL,375,0),('9789861830117','We Sing We Play 6',NULL,376,0),('SAR1','Sing And Read 1',NULL,377,0),('SAR2','Sing And Read 2',NULL,378,0),('SAR3','Sing And Read 3',NULL,379,0),('SAR4','Sing And Read 4',NULL,380,0),('SAR5','Sing And Read 5',NULL,381,0),('SAR6','Sing And Read 6',NULL,382,0),('9781410881083','Benchmark Early Reader 2B',NULL,401,0),('9781410861184','Benchmark Early Reader 3B',NULL,402,0),('9781410861153','Benchmark Early Reader 3A',NULL,403,0),('9781616723903','Benchmark Early Reader 2A',NULL,404,0),('9781410861283','Benchmark Reader 3B',NULL,405,0),('9781410807359','Build Up Phonics Reader 1A',NULL,406,0),('9781410807441','Build Up Phonics Reader 1B',NULL,407,0),('9781410807533','Build Up Phonics Reader 2A',NULL,408,0),('9781410807625','Build Up Phonics Reader 2B',NULL,409,0),('9781410807717','Build Up Phonics Reader 3A',NULL,410,0),('9781410815286','Build Up Phonics Reader 3B',NULL,411,0),('9781410806987','Start Up Phonics Reader 1A',NULL,412,0),('9781410807113','Start Up Phonics Reader 1B',NULL,413,0),('9789603795179','Yippee Red Student Book',NULL,414,0),('9789604435326','Get Smart 2 Student Book',NULL,415,0),('9789604435296','Get Smart 1 Teacher Book',NULL,416,0),('9789603795315','Yippee Blue Teacher Book',NULL,417,0),('9789604435388','Get Smart 3 Workbook',NULL,418,0),('9789603795124','Yippee Green Fun Book',NULL,419,0),('9789603795063','Yippee Green Student Book',NULL,420,0),('9789604435371','Get Smart 3 Student Book',NULL,421,0),('9789604435524','Get Smart 6 Student Book',NULL,422,0),('9789604435531','Get Smart 6 Workbook',NULL,423,0),('9789604435487','Get Smart 5 Workbook',NULL,424,0),('9789604435470','Get Smart 5 Student Book',NULL,425,0),('9789604435425','Get Smart 4 Student Book',NULL,426,0),('9789867054906','Sight Word Kids 1A',NULL,427,0),('9789867054920','Sight Word Kids 2A',NULL,428,0),('9789867054937','Sight Word Kids 2B',NULL,429,0),('9789861830759','Sight Word Kids 3A',NULL,430,0),('9789861830766','Sight Word Kids 3B',NULL,431,0),('9789861831176','Sight Word Kids 4A',NULL,432,0),('9789861831022','Sight Word Kids 4B',NULL,433,0),('9789861831367','Sight Word Kids 5A',NULL,434,0),('9789861831374','Sight Word Kids 5B',NULL,435,0),('9789861830520','Sight Word Kids H1',NULL,436,0),('9789861830537','Sight Word Kids H2',NULL,437,0),('9789861830544','Sight Word Kids H3',NULL,438,0),('9789861830551','Sight Word Kids H4',NULL,439,0),('9789861830735','Sight Word Kids H5',NULL,440,0),('9789861830742','Sight Word Kids H6',NULL,441,0),('9789861831015','Sight Word Kids H7',NULL,442,0),('9789861831022-2','Sight Word Kids H8',NULL,443,0),('9789861831190','Sight Word Kids H9',NULL,444,0),('9789861831206','Sight Word Kids H10',NULL,445,0),('FP_1A','FingerPrint 1A',NULL,446,0),('FP_1B','FingerPrint 1B',NULL,447,0),('FP_2A','FingerPrint 2A',NULL,448,0),('FP_2B','FingerPrint 2B',NULL,449,0),('FP_3A','FingerPrint 3A',NULL,450,0),('FP_3B','FingerPrint 3B',NULL,451,0),('9789861831923','Love English G6 Teacher Manual',NULL,452,0),('9789861831053-2','Love English S2',NULL,453,0),('4710768495487','Love English G2 Workbook',NULL,454,0),('4710768496163','跟小小孩说英文',NULL,455,0),('9789604439744','Traveller Intermediate SB',NULL,456,0),('9789604439645','Traveller PreIntermediate SB',NULL,457,0),('9789604439546','Traveller Elementary SB',NULL,458,0),('9789604439447','Traveller Beginner SB',NULL,459,0),('9781405336581','DK New Children Encyclopedia',NULL,460,0),('9789868577985','Easy to Read Lv1 Halloween Parade',NULL,461,0),('9789866146008','Easy to Read Lv1 My Tooth is Loose',NULL,462,0),('9789868577909','Easy to Read Lv1 Henny-Penny',NULL,463,0),('4710768496057','Easy to Read Lv1 Henny-Penny WB',NULL,464,0),('4710768496514','Easy to Read Lv1 My Tooth is Loose WB',NULL,465,0),('4710768496491','Easy to Read Lv1 The Ugly Duckling WB',NULL,466,0),('4710768496507','Easy to Read Lv1 Halloween Parade WB',NULL,467,0),('9789868577992','Easy to Read Lv1 The Ugly Duckling ',NULL,468,0),('9789866146015','Easy to Read Lv1 The Magic Porridge Pot',NULL,469,0),('4710768496521','Easy to Read Lv1 The Magic Porridge Pot WB',NULL,470,0),('9789604432998','MM Reader Lv2 Felix and the Fairy',NULL,471,0),('9789603796732','MM Reader Lv1 Jasper\'s Pot of Gold',NULL,472,0),('9789604434688','MM Reader Lv 1 The Princess and the Frog TB',NULL,473,0),('9789603794363','MM Reader Lv 1 Cookie Land',NULL,474,0),('9789604432882','MM Reader Lv1 The Ugly Duckling TB',NULL,475,0),('9789603796756','MM Reader Lv1 Jasper\'s Pot of Gold TB',NULL,476,0),('9789603794349','MM Reader Lv2 The Fox & The Dog',NULL,477,0),('9771008395016','东方娃娃中文绘本',NULL,478,0),('9787888675711','东方娃娃英文绘本',NULL,479,0),('9789604436545','MM Reader Lv3 Sleeping Beauty',NULL,480,0),('9789603794691','MM Reader Lv3 Rumpelstiltskin',NULL,481,0),('9786070601651','SOE GK Big Books',NULL,482,0),('7506009806559','SOE GK Poster Cutout',NULL,483,0),('9789603795353','Yippee Green Flashcards',NULL,501,0),('9789603795131','Yippee Green Teacher Book',NULL,502,0),('9789604781744','Yippee New Blue Fun Book',NULL,503,0),('9789603795292','YIppee Blue Student Book',NULL,504,0),('9789604438112','Get Smart 1 Flashcards',NULL,505,0),('9789604435395','Get Smart 3 Teacher Book',NULL,506,0),('9789604435494','Get Smart 5 Teacher Book',NULL,507,0),('9789604435548','Get Smart 6 Teacher Book',NULL,508,0),('9789604435449','Get Smart 4 Teacher Book',NULL,509,0),('9789603794592','MM Reader Lv 1 Cookie Land TB',NULL,510,0),('9789604432899','MM Reader Lv 4 Alibaba and the 40 thieves',NULL,511,0),('9789604432820','MM Reader Lv 3 Puss in Boos',NULL,512,0),('9789604434671','MM Reader Lv1 The Princess and the Frog',NULL,513,0),('SOE_GK_CD','SOE GK CD',NULL,551,0),('SOE_G1_CD','SOE G1 CD',NULL,552,0),('SOE_GK_IS','SOE GK Interactive Software',NULL,601,0),('SOE_G2_CD','SOE G2 CD',NULL,602,0),('SOE_G3_CD','SOE G3 CD',NULL,603,0),('SOE_G4_CD','SOE G4 CD',NULL,604,0),('SOE_G5_CD','SOE G5 CD',NULL,605,0),('SOE_G6_CD','SOE G6 CD',NULL,606,0),('LOVE_ENG_S1_TB','Love English S1 Teacher Manual',NULL,651,0),('LOVE_ENG_S2_TB','Love English S2 Teacher Manual',NULL,652,0),('PHONICS_KIDS_1_3','Phonics Kids 1-3',NULL,701,1),('PHONICS_KIDS_4_6','Phonics Kids 4-6',NULL,702,1),('LOVE_ENG_G1','Love English G1',NULL,703,1),('LOVE_ENG_G2','Love English G2',NULL,704,1),('LOVE_ENG_G3','Love English G3',NULL,705,1),('LOVE_ENG_G4','Love English G4',NULL,706,1),('LOVE_ENG_G5','Love English G5',NULL,707,1),('LOVE_ENG_G6','Love English G6',NULL,708,1),('LOVE_ENGLISH_1_6','Love English 1-6',NULL,709,1),('LOVE_ENGLISH_1_8','Love English 1-8',NULL,710,1),('9789570339574','Super Phonics 2 WB',NULL,751,0),('SUPER_PHONICS','Super Phonics 1-3',NULL,752,1);
/*!40000 ALTER TABLE `profile_book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile_book_set`
--

DROP TABLE IF EXISTS `profile_book_set`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile_book_set` (
  `book_set` int(11) NOT NULL,
  `book` int(11) NOT NULL,
  `count` int(5) NOT NULL DEFAULT '1',
  `percent` decimal(10,2) NOT NULL DEFAULT '1.00',
  PRIMARY KEY (`book_set`,`book`),
  KEY `fk_book_set` (`book_set`),
  KEY `fk_book` (`book`),
  CONSTRAINT `fk_book` FOREIGN KEY (`book`) REFERENCES `profile_book` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_book_set` FOREIGN KEY (`book_set`) REFERENCES `profile_book` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile_book_set`
--

LOCK TABLES `profile_book_set` WRITE;
/*!40000 ALTER TABLE `profile_book_set` DISABLE KEYS */;
INSERT INTO `profile_book_set` VALUES (8,5,1,1.00),(8,6,1,1.00),(8,7,1,1.00),(15,12,1,1.00),(15,13,1,1.00),(15,14,1,1.00),(16,9,1,1.00),(16,10,1,1.00),(16,11,1,1.00),(701,29,1,1.00),(701,30,1,1.00),(701,31,1,1.00),(701,32,1,1.00),(701,33,1,1.00),(701,34,1,1.00),(702,35,1,1.00),(702,36,1,1.00),(702,37,1,1.00),(702,38,1,1.00),(702,39,1,1.00),(702,40,1,1.00),(703,61,1,1.00),(703,62,1,1.00),(704,63,1,1.00),(704,454,1,1.00),(705,69,1,1.00),(705,70,1,1.00),(706,65,1,1.00),(706,66,1,1.00),(707,59,1,1.00),(707,67,1,1.00),(708,60,1,1.00),(708,68,1,1.00),(709,59,1,1.00),(709,60,1,1.00),(709,61,1,1.00),(709,62,1,1.00),(709,63,1,1.00),(709,65,1,1.00),(709,66,1,1.00),(709,67,1,1.00),(709,68,1,1.00),(709,69,1,1.00),(709,70,1,1.00),(709,454,1,1.00),(710,59,1,1.00),(710,60,1,1.00),(710,61,1,1.00),(710,62,1,1.00),(710,63,1,1.00),(710,64,1,1.00),(710,65,1,1.00),(710,66,1,1.00),(710,67,1,1.00),(710,68,1,1.00),(710,69,1,1.00),(710,70,1,1.00),(710,453,1,1.00),(710,454,1,1.00),(752,308,1,1.00),(752,309,1,1.00),(752,310,1,1.00),(752,311,1,1.00),(752,312,1,1.00),(752,751,1,1.00);
/*!40000 ALTER TABLE `profile_book_set` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile_borrower`
--

DROP TABLE IF EXISTS `profile_borrower`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile_borrower` (
  `oid` int(11) NOT NULL,
  `contact` int(11) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `company` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  KEY `br_fk_ct` (`contact`),
  CONSTRAINT `br_fk_ct` FOREIGN KEY (`contact`) REFERENCES `common_contact` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile_borrower`
--

LOCK TABLES `profile_borrower` WRITE;
/*!40000 ALTER TABLE `profile_borrower` DISABLE KEYS */;
/*!40000 ALTER TABLE `profile_borrower` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile_customer`
--

DROP TABLE IF EXISTS `profile_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile_customer` (
  `oid` int(11) NOT NULL,
  `customer_id` varchar(45) NOT NULL,
  `source` varchar(45) NOT NULL,
  `description` text,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile_customer`
--

LOCK TABLES `profile_customer` WRITE;
/*!40000 ALTER TABLE `profile_customer` DISABLE KEYS */;
INSERT INTO `profile_customer` VALUES (1,'duxinzhu05','TAOBAO',NULL),(2,'ellenliang1103','TAOBAO',NULL),(3,'远和乐','TAOBAO',NULL),(4,'tb8861993_2011','TAOBAO',NULL),(5,'linbo_ma','TAOBAO',NULL),(6,'流星雨又来临25','TAOBAO',NULL),(51,'shelly198252280','TAOBAO',NULL),(52,'cherryixin144','TAOBAO',NULL),(53,'lilx88cl','TAOBAO',NULL),(54,'ctlab','TAOBAO',NULL),(55,'冷买天下','TAOBAO',NULL),(56,'angel_fenglan','TAOBAO',NULL),(57,'violetfy2005','TAOBAO',NULL),(58,'huangjunyixiaoqing','TAOBAO',NULL),(59,'cancan0736','TAOBAO',NULL),(60,'xiangjingling333','TAOBAO',NULL),(61,'shengsiyu88','TAOBAO',NULL),(62,'巧克力之王','TAOBAO',NULL),(63,'绿吧吧','TAOBAO',NULL),(64,'shelly198252280','TAOBAO',NULL),(65,'lilx88cl','TAOBAO',NULL),(66,'kxgz7567','TAOBAO',NULL),(67,'e品清凉','TAOBAO',NULL),(68,'cherryixin144','TAOBAO',NULL),(69,'liubowen81','TAOBAO',NULL),(70,'rain_bloom','TAOBAO',NULL),(71,'lwlxy_2007','TAOBAO',NULL),(72,'cancan0736','TAOBAO',NULL),(73,'huangjunyixiaoqing','TAOBAO',NULL),(101,'萨薇国际时尚','TAOBAO',NULL),(102,'yongmeiwei','TAOBAO',NULL),(103,'yongmeiwei','TAOBAO',NULL),(104,'sheena8806','TAOBAO',NULL),(151,'winne8899','TAOBAO',NULL),(152,'蔓蔓凉1982','TAOBAO',NULL),(153,'杨慧颖','TAOBAO',NULL),(201,'晋源阳阳','TAOBAO',NULL),(202,'yejiajing46','TAOBAO',NULL),(203,'yeliqian2009','TAOBAO',NULL),(204,'ouyang242wyz','TAOBAO',NULL),(205,'tb3200496','TAOBAO',NULL),(206,'ju5688','TAOBAO',NULL),(207,'暗蓝yu夜','TAOBAO',NULL);
/*!40000 ALTER TABLE `profile_customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile_supplier`
--

DROP TABLE IF EXISTS `profile_supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile_supplier` (
  `oid` int(11) NOT NULL,
  `source` varchar(45) NOT NULL,
  `id` varchar(45) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile_supplier`
--

LOCK TABLES `profile_supplier` WRITE;
/*!40000 ALTER TABLE `profile_supplier` DISABLE KEYS */;
INSERT INTO `profile_supplier` VALUES (1,'TAOBAO','void',NULL,NULL),(51,'TAOBAO','普林斯顿',NULL,NULL);
/*!40000 ALTER TABLE `profile_supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store_entry`
--

DROP TABLE IF EXISTS `store_entry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `store_entry` (
  `oid` int(11) NOT NULL,
  `site` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `locked_count` int(11) NOT NULL DEFAULT '0',
  `unit_price` decimal(10,2) NOT NULL,
  `book_oid` int(11) NOT NULL,
  PRIMARY KEY (`oid`),
  KEY `se_fk_site` (`site`),
  KEY `se_fk_book` (`book_oid`),
  CONSTRAINT `se_fk_book` FOREIGN KEY (`book_oid`) REFERENCES `profile_book` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `se_fk_site` FOREIGN KEY (`site`) REFERENCES `store_site` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store_entry`
--

LOCK TABLES `store_entry` WRITE;
/*!40000 ALTER TABLE `store_entry` DISABLE KEYS */;
INSERT INTO `store_entry` VALUES (1,1,1,0,37.50,428),(2,1,1,0,180.00,501),(3,1,1,0,98.00,403),(4,1,1,0,35.00,366),(5,1,1,0,85.00,341),(6,1,1,0,0.00,68),(7,1,1,0,68.00,407),(8,1,2,0,120.00,26),(9,1,1,0,165.00,51),(10,1,2,0,265.00,106),(11,1,2,0,30.00,33),(12,1,2,0,44.00,504),(13,1,2,0,60.00,152),(14,1,1,0,50.00,379),(15,1,1,0,265.00,101),(16,1,1,0,48.00,343),(17,1,1,0,120.00,25),(18,1,2,0,60.00,156),(19,1,2,0,45.00,36),(20,1,5,0,30.00,31),(21,1,1,0,37.50,429),(22,1,12,0,30.00,34),(23,1,1,0,50.00,380),(24,1,1,0,37.50,427),(25,1,1,0,50.00,382),(26,1,1,0,80.00,53),(27,1,3,0,265.00,105),(28,1,2,0,45.00,35),(29,1,3,0,95.00,416),(30,1,1,0,85.00,318),(31,1,1,0,105.60,12),(32,1,1,0,80.00,48),(33,1,1,0,48.00,474),(34,1,1,0,60.00,4),(35,1,1,0,98.00,371),(36,1,1,0,85.00,342),(37,1,19,0,45.00,40),(38,1,1,0,30.00,374),(39,1,3,0,60.00,155),(40,1,2,0,60.00,151),(41,1,1,0,50.00,437),(42,1,1,0,105.60,7),(43,1,1,0,120.00,158),(44,1,1,0,200.00,3),(45,1,1,0,105.60,10),(46,1,1,0,90.00,41),(47,1,1,0,108.00,58),(48,1,1,0,98.00,370),(49,1,1,0,68.00,413),(50,1,3,0,60.00,157),(51,1,1,0,105.60,11),(52,1,2,0,48.00,510),(53,1,1,0,105.60,9),(54,1,1,0,68.00,411),(55,1,3,0,55.00,62),(56,1,1,0,10.00,69),(57,1,1,0,85.00,328),(58,1,11,0,30.00,30),(59,1,1,0,50.00,414),(60,1,1,0,45.00,52),(61,1,13,0,30.00,29),(62,1,1,0,80.00,47),(63,1,1,0,108.00,56),(64,1,1,0,120.00,201),(65,1,3,0,48.00,329),(66,1,1,0,50.00,436),(67,1,2,0,35.00,361),(68,1,1,0,44.00,420),(69,1,5,0,120.00,460),(70,1,2,0,60.00,153),(71,1,1,0,200.00,483),(72,1,1,0,50.00,381),(73,1,1,0,50.00,377),(74,1,1,0,95.00,507),(75,1,1,0,85.00,319),(76,1,1,0,105.60,6),(77,1,1,0,88.00,417),(78,1,1,0,105.60,13),(79,1,1,0,55.00,65),(80,1,1,0,68.00,409),(81,1,1,0,66.00,425),(82,1,1,0,120.00,28),(83,1,1,0,450.00,44),(84,1,2,0,35.00,363),(85,1,1,0,105.60,14),(86,1,2,0,35.00,362),(87,1,1,0,98.00,402),(88,1,1,0,42.00,503),(89,1,1,0,95.00,508),(90,1,1,0,68.00,406),(91,1,1,0,37.50,434),(92,1,1,0,10.00,67),(93,1,17,0,45.00,39),(94,1,1,0,80.00,49),(95,1,1,0,60.00,154),(96,1,2,0,120.00,27),(97,1,1,0,66.00,426),(98,1,1,0,265.00,103),(99,1,1,0,37.50,435),(100,1,1,0,50.00,378),(101,1,3,0,10.00,61),(102,1,1,0,265.00,102),(103,1,17,0,45.00,38),(104,1,2,0,48.00,477),(105,1,1,0,105.60,5),(106,1,1,0,48.00,473),(107,1,1,0,10.00,59),(108,1,1,0,37.50,432),(109,1,1,0,60.00,2),(110,1,2,0,95.00,509),(111,1,1,0,85.00,327),(112,1,1,0,90.00,42),(113,1,2,0,35.00,360),(114,1,1,0,55.00,64),(115,1,1,0,450.00,46),(116,1,2,0,30.00,32),(117,1,1,0,10.00,66),(118,1,1,0,55.00,453),(119,1,1,0,108.00,57),(120,1,1,0,450.00,45),(121,1,1,0,48.00,471),(122,1,1,0,180.00,505),(123,1,1,0,37.50,431),(124,1,1,0,88.00,502),(125,1,1,0,48.00,511),(126,1,1,0,48.00,513),(127,1,1,0,90.00,43),(128,1,1,0,98.00,367),(129,1,1,0,45.00,50),(130,1,1,0,85.00,317),(131,1,1,0,45.00,55),(132,1,1,0,37.50,433),(133,1,1,0,55.00,60),(134,1,1,0,37.50,430),(135,1,1,0,55.00,70),(136,1,18,0,45.00,37),(137,1,1,0,55.00,453),(138,1,1,0,265.00,104),(139,1,1,0,45.00,54),(140,1,1,0,48.00,512),(141,1,2,0,200.00,1),(142,1,2,0,68.00,408),(143,1,19,0,330.00,455),(144,1,1,0,95.00,506),(145,3,7,0,50.00,438),(146,3,3,0,60.00,12),(147,3,64,0,30.00,33),(148,3,52,0,30.00,34),(149,3,193,0,40.00,360),(150,3,58,0,45.00,39),(151,3,5,0,60.00,13),(152,3,47,0,30.00,32),(153,3,2,0,200.00,483),(154,3,152,0,40.00,362),(155,3,47,0,200.00,1),(156,3,30,0,200.00,11),(157,3,161,0,40.00,363),(158,3,68,0,30.00,30),(159,3,106,0,45.00,35),(160,3,8,0,60.00,9),(161,3,14,0,92.50,42),(162,3,7,0,50.00,439),(163,3,11,0,60.00,302),(164,3,3,0,50.00,441),(165,3,27,0,200.00,5),(166,3,19,0,200.00,251),(167,3,10,0,230.00,482),(168,3,62,0,30.00,31),(169,3,2,0,50.00,443),(170,3,60,0,45.00,40),(171,3,4,0,50.00,437),(172,3,2,0,60.00,6),(173,3,61,0,45.00,38),(174,3,59,0,45.00,37),(175,3,4,0,50.00,436),(176,3,25,0,200.00,14),(177,3,1,0,60.00,2),(178,3,104,0,45.00,36),(179,3,7,0,398.00,301),(180,3,6,0,50.00,442),(181,3,47,0,30.00,29),(182,3,196,0,40.00,361),(183,3,8,0,60.00,10),(184,3,11,0,92.50,43),(185,3,2,0,60.00,7),(186,3,3,0,50.00,440),(201,2,2,0,104.00,651),(202,2,20,0,60.00,63),(203,2,10,0,5.00,61),(204,2,2,0,104.00,652),(205,2,15,0,60.00,64),(206,2,2,0,104.00,452),(207,2,1,0,60.00,65),(208,2,10,0,60.00,453),(209,2,20,0,5.00,454),(210,2,10,0,5.00,69),(211,2,2,0,104.00,56),(212,2,1,0,5.00,66),(213,2,2,0,104.00,58),(214,2,10,0,60.00,70),(215,2,2,0,104.00,57),(216,2,10,0,60.00,62);
/*!40000 ALTER TABLE `store_entry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store_site`
--

DROP TABLE IF EXISTS `store_site`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `store_site` (
  `oid` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `pref_seq` int(11) NOT NULL DEFAULT '0',
  `description` varchar(45) DEFAULT NULL,
  `valid` int(1) NOT NULL DEFAULT '1',
  `for_output` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store_site`
--

LOCK TABLES `store_site` WRITE;
/*!40000 ALTER TABLE `store_site` DISABLE KEYS */;
INSERT INTO `store_site` VALUES (1,'办公室',0,'地址:白兰路137弄绿洲广场A座1105室\n电话:021-60525439',1,0),(2,'发货仓',0,'地址:兰溪路21弄12号地下室\n电话:021-33606010',1,1),(3,'存货仓',0,'地址:兰溪路21弄12号地下室',1,0);
/*!40000 ALTER TABLE `store_site` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store_trans_item`
--

DROP TABLE IF EXISTS `store_trans_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `store_trans_item` (
  `oid` int(11) NOT NULL,
  `header` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `book_oid` int(11) NOT NULL,
  PRIMARY KEY (`oid`),
  KEY `fk_header` (`header`),
  KEY `sti_fk_book` (`book_oid`),
  CONSTRAINT `fk_header` FOREIGN KEY (`header`) REFERENCES `store_transfer` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sti_fk_book` FOREIGN KEY (`book_oid`) REFERENCES `profile_book` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store_trans_item`
--

LOCK TABLES `store_trans_item` WRITE;
/*!40000 ALTER TABLE `store_trans_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `store_trans_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store_transfer`
--

DROP TABLE IF EXISTS `store_transfer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `store_transfer` (
  `oid` int(11) NOT NULL,
  `tr_number` varchar(45) NOT NULL,
  `create_date` datetime NOT NULL,
  `expect_action_date` datetime DEFAULT NULL,
  `action_date` datetime DEFAULT NULL,
  `from_site` int(11) NOT NULL,
  `to_site` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`oid`),
  KEY `st_fk_fromsite` (`from_site`),
  KEY `st_fk_tosite` (`to_site`),
  CONSTRAINT `st_fk_fromsite` FOREIGN KEY (`from_site`) REFERENCES `store_site` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `st_fk_tosite` FOREIGN KEY (`to_site`) REFERENCES `store_site` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store_transfer`
--

LOCK TABLES `store_transfer` WRITE;
/*!40000 ALTER TABLE `store_transfer` DISABLE KEYS */;
/*!40000 ALTER TABLE `store_transfer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_num_gen`
--

DROP TABLE IF EXISTS `sys_num_gen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_num_gen` (
  `type` varchar(45) NOT NULL,
  `prefix` varchar(45) DEFAULT NULL,
  `suffix` varchar(45) DEFAULT NULL,
  `current` int(11) NOT NULL,
  `length` int(11) NOT NULL,
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_num_gen`
--

LOCK TABLES `sys_num_gen` WRITE;
/*!40000 ALTER TABLE `sys_num_gen` DISABLE KEYS */;
INSERT INTO `sys_num_gen` VALUES ('BR','BR',NULL,1,10),('PO','PO',NULL,53,10),('SO','SO',NULL,5,10),('TR','TR',NULL,1,10);
/*!40000 ALTER TABLE `sys_num_gen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_sequence`
--

DROP TABLE IF EXISTS `sys_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_sequence` (
  `table_name` varchar(50) NOT NULL,
  `seq` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_sequence`
--

LOCK TABLES `sys_sequence` WRITE;
/*!40000 ALTER TABLE `sys_sequence` DISABLE KEYS */;
INSERT INTO `sys_sequence` VALUES ('order_common',450),('order_item',1000),('order_delivery',250),('order_delivery_item',0),('profile_customer',250),('profile_supplier',100),('store_site',50),('store_entry',250),('store_transfer',0),('store_trans_item',0),('profile_book',800),('library_record',0),('library_record_item',0),('library_entry',0),('profile_borrower',0),('order_disp_item',300),('fna_accounting',0);
/*!40000 ALTER TABLE `sys_sequence` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-02-24 23:17:01
