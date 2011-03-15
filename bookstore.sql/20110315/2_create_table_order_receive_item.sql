CREATE  TABLE IF NOT EXISTS `order_receive_item` (
  `oid` INT(11) NOT NULL ,
  `header` INT(11) NOT NULL ,
  `book_oid` INT(11) NOT NULL ,
  `count` INT(10) NOT NULL ,
  PRIMARY KEY (`oid`) ,
  INDEX `ori_fk_header` (`header` ASC) ,
  INDEX `ori_fk_book` (`book_oid` ASC) ,
  CONSTRAINT `ori_fk_header`
    FOREIGN KEY (`header` )
    REFERENCES `bookstore`.`order_receive` (`oid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `ori_fk_book`
    FOREIGN KEY (`book_oid` )
    REFERENCES `bookstore`.`profile_book` (`oid` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

insert into sys_sequence (table_name,seq) values ('order_receive_item',1);