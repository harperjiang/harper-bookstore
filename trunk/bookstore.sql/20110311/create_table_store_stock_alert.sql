CREATE  TABLE IF NOT EXISTS `store_stock_alert` (
  `oid` INT(11) NOT NULL ,
  `site` INT(11) NULL ,
  `book_oid` INT(11) NOT NULL ,
  `warn_threshold` INT(10) NOT NULL ,
  `error_threshold` INT(10) NOT NULL ,
  PRIMARY KEY (`oid`) ,
  INDEX `ssa_fk_book` (`book_oid` ASC) ,
  INDEX `ssa_fk_site` (`site` ASC) ,
  CONSTRAINT `ssa_fk_book`
    FOREIGN KEY (`book_oid` )
    REFERENCES `profile_book` (`oid` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `ssa_fk_site`
    FOREIGN KEY (`site` )
    REFERENCES `store_site` (`oid` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

insert into sys_sequence (table_name,seq) values ('store_stock_alert',1);