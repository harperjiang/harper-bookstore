package org.harper.bookstore.domain.taobao;

/**
 * Taobao defined order status
 * @author Harper
 *
 */
public enum TradeQueryStatus {
	TRADE_NO_CREATE_PAY,
	WAIT_BUYER_PAY,
	WAIT_SELLER_SEND_GOODS,
	WAIT_BUYER_CONFIRM_GOODS,
	TRADE_BUYER_SIGNED,
	TRADE_FINISHED,
	TRADE_CLOSED,
	TRADE_CLOSED_BY_TAOBAO,
	ALL_WAIT_PAY,//(包含：WAIT_BUYER_PAY、TRADE_NO_CREATE_PAY)
	ALL_CLOSED//(包含：TRADE_CLOSED、TRADE_CLOSED_BY_TAOBAO)
}
