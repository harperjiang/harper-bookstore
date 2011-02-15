package org.harper.bookstore.service;

public enum TaobaoOrderStatus {

	TRADE_NO_CREATE_PAY("没有创建支付宝交易"),
	WAIT_BUYER_PAY("等待买家付款"),
	WAIT_SELLER_SEND_GOODS("买家已付款，等待卖家发货"),
	WAIT_BUYER_CONFIRM_GOODS("卖家已发货，等待买家确认"),
	TRADE_BUYER_SIGNED("买家已签收"),
	TRADE_FINISHED("交易成功"),
	TRADE_CLOSED("交易关闭"),
	TRADE_CLOSED_BY_TAOBAO("交易被淘宝关闭"); 
	
	String desc;
	
	TaobaoOrderStatus(String desc) {
		this.desc = desc;
	}
	
	public String desc(){
		return desc;
	}
	
	public static TaobaoOrderStatus getByDesc(String desc) {
		for(TaobaoOrderStatus s : TaobaoOrderStatus.values()) {
			if(s.desc.equals(desc))
				return s;
		}
		return null;
	}
}
