alter table order_receive add delivery int(11);

alter table order_receive add constraint or_fk_delivery foreign key (delivery) references order_delivery(oid);