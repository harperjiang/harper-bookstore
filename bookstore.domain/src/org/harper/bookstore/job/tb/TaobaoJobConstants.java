package org.harper.bookstore.job.tb;

public interface TaobaoJobConstants {

	int PAGE_SIZE = 50;

	String TRADE_FIELDS = "tid," + "buyer_nick," + "type," + "created,"
			+ "buyer_message," + "price," + "payment," + "status,"
			+ "total_fee," + "buyer_memo," + "seller_memo," + "pay_time,"
			+ "end_time," + "receiver_name," + "receiver_state,"
			+ "receiver_city," + "receiver_district," + "receiver_address,"
			+ "receiver_zip," + "receiver_mobile," + "receiver_phone,"
			+ "buyer_email," + "commission_fee," + "post_fee," + "orders.oid,"
			+ "orders.iid," + "orders.title," + "orders.price,"
			+ "orders.outer_iid," + "orders.status," + "orders.num,"
			+ "orders.total_fee," + "orders.discount_fee";
}
