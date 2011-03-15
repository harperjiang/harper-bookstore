CREATE  TABLE IF NOT EXISTS `order_receive` (
  `oid` INT(11) NOT NULL ,
  `status` INT(1) NOT NULL ,
  `number` VARCHAR(40) NOT NULL ,
  `company` VARCHAR(45) NOT NULL ,
  `create_date` DATETIME NOT NULL ,
  `receive_date` DATETIME NULL ,
  `type` VARCHAR(30) NOT NULL ,
  `ref_number` VARCHAR(45) NOT NULL ,
  `remark` TEXT NULL,
  `sender_name` VARCHAR(40) NULL ,
  `sender_mobile` VARCHAR(45) NULL ,
  `sender_email` VARCHAR(45) NULL ,
  `sender_phone` VARCHAR(45) NULL ,
  PRIMARY KEY (`oid`) )
ENGINE = InnoDB;
insert into sys_sequence(table_name,seq) values('order_receive',1);
