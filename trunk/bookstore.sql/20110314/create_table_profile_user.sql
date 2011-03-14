CREATE  TABLE IF NOT EXISTS `profile_user` (
  `oid` INT(11) NOT NULL ,
  `role` INT(11) NOT NULL ,
  `id` VARCHAR(20) NOT NULL ,
  `ency_pass` VARCHAR(128) NOT NULL ,
  `last_login_date` DATETIME NULL ,
  PRIMARY KEY (`oid`) ,
  UNIQUE INDEX `uk_id` USING BTREE (`id` ASC) )
ENGINE = InnoDB;

insert into sys_sequence(table_name,seq) values ('profile_user',1);