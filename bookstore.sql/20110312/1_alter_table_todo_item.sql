alter table todo_item add biz_key varchar(40);

alter table todo_item add INDEX `indx_bizkey` USING BTREE (`biz_key` ASC);