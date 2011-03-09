CREATE  TABLE IF NOT EXISTS `store_stock_take_item` (
  `oid` INT(11) NOT NULL ,
  `header` INT(11) NOT NULL ,
  `current_count` INT(10) NOT NULL ,
  `expect_count` INT(10) NOT NULL ,
  `unit_price` DECIMAL(10,2) NOT NULL ,
  `book_oid` INT(11) NOT NULL ,
  PRIMARY KEY (`oid`) ,
  INDEX `stoi_fk_header` (`header` ASC) ,
  INDEX `stoi_fk_book` (`book_oid` ASC) ,
  CONSTRAINT `stoi_fk_header`
    FOREIGN KEY (`header` )
    REFERENCES `store_stock_take` (`oid` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `stoi_fk_book`
    FOREIGN KEY (`book_oid` )
    REFERENCES `profile_book` (`oid` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB