package org.harper.frm.job.tb;

public interface TaobaoJobConstants {

	long PAGE_SIZE = 50L;

	String TRADE_INCREGET_FIELDS = "tid," + "buyer_nick," + "type,"
			+ "created," + "price," + "payment," + "status," + "total_fee,"
			+ "pay_time," + "end_time," + "receiver_name," + "receiver_state,"
			+ "receiver_city," + "receiver_district," + "receiver_address,"
			+ "receiver_zip," + "receiver_mobile," + "receiver_phone,"
			+ "commission_fee," + "post_fee," + "orders.oid,"
			+ "orders.num_iid," + "orders.title," + "orders.price,"
			+ "orders.payment," + "orders.outer_iid," + "orders.status,"
			+ "orders.num," + "orders.total_fee," + "orders.discount_fee";

	String TRADE_ADDI_FIELDS = "tid," + "buyer_nick," + "buyer_message,"
			+ "buyer_memo," + "seller_memo," + "seller_flag," + "buyer_email,";

	String TRADE_FIELDS = "tid," + "buyer_nick," + "type," + "buyer_message,"
			+ "buyer_memo," + "seller_memo," + "seller_flag," + "buyer_email,"
			+ "created," + "price," + "payment," + "status," + "total_fee,"
			+ "pay_time," + "end_time," + "receiver_name," + "receiver_state,"
			+ "receiver_city," + "receiver_district," + "receiver_address,"
			+ "receiver_zip," + "receiver_mobile," + "receiver_phone,"
			+ "commission_fee," + "post_fee," + "orders.oid,"
			+ "orders.num_iid," + "orders.title," + "orders.price,"
			+ "orders.payment," + "orders.outer_iid," + "orders.status,"
			+ "orders.num," + "orders.total_fee," + "orders.discount_fee";

}
