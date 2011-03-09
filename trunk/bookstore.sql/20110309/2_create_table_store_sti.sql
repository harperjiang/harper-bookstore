CREATE  TABLE IF NOT EXISTS `store_trans_item` (
  `oid` INT(11) NOT NULL ,
  `header` INT(11) NOT NULL ,
  `count` INT(11) NOT NULL ,
  `book_oid` INT(11) NOT NULL ,
  PRIMARY KEY (`oid`) ,
  INDEX `fk_header` (`header` ASC) ,
  INDEX `sti_fk_book` (`book_oid` ASC) ,
  CONSTRAINT `fk_header`
    FOREIGN KEY (`header` )
    REFERENCES `store_transfer` (`oid` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `sti_fk_book`
    FOREIGN KEY (`book_oid` )
    REFERENCES `profile_book` (`oid` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;