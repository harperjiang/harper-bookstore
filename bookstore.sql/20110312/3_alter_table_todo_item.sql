

ALTER TABLE `store_stock_alert` ADD UNIQUE INDEX `uk_booksite` USING BTREE (`site` ASC, `book_oid` ASC) ;