CREATE  TABLE IF NOT EXISTS `todo_item` (
  `oid` INT(11) NOT NULL ,
  `subject` VARCHAR(60) NOT NULL ,
  `content` TEXT NULL ,
  `solution` TEXT NULL ,
  `privilege` INT(2) NOT NULL ,
  `status` INT(1) NOT NULL ,
  `create_date` DATETIME NOT NULL ,
  `due_date` DATETIME NOT NULL ,
  `resolve_date` DATETIME NULL ,
  PRIMARY KEY (`oid`) )
ENGINE = InnoDB;

insert into sys_sequence (table_name,seq) values ('todo_item',0);