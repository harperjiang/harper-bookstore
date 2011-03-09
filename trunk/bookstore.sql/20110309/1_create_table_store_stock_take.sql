CREATE  TABLE IF NOT EXISTS `store_stock_take` (
  `oid` INT(11) NOT NULL ,
  `site` INT(11) NOT NULL ,
  `st_number` VARCHAR(30) NOT NULL ,
  `status` INT(1) NOT NULL ,
  `create_date` DATETIME NOT NULL ,
  `confirm_date` DATETIME NULL ,
  `version` INT(10) NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`oid`) ,
  INDEX `store_st_fk_site` (`site` ASC) ,
  CONSTRAINT `store_st_fk_site`
    FOREIGN KEY (`site` )
    REFERENCES `store_site` (`oid` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;