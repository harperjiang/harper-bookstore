alter table order_delivery add remark text;
alter table order_delivery add send_missed int(1) default 0;
update order_delivery set send_missed = 0;