ALTER TABLE `profile_book_set` ADD COLUMN `count` INT(5) NULL DEFAULT 1  AFTER `book`   ADD COLUMN `percent` DECIMAL(10,2) NULL DEFAULT 1  AFTER `count` ;
 