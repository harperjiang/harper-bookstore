package org.harper.bookstore.task.tb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.harper.bookstore.service.TaobaoOrderStatus;
import org.harper.bookstore.service.bean.TaobaoItemBean;
import org.harper.bookstore.service.bean.TaobaoOrderBean;

import com.taobao.api.domain.Order;
import com.taobao.api.domain.Trade;

public class ConvertTaobaoOrderTask {

	public List<TaobaoOrderBean> convert(Collection<Trade> input) {
		List<TaobaoOrderBean> list = new ArrayList<TaobaoOrderBean>();
		for (Trade trade : input)
			list.add(convert(trade));
		return list;
	}

	public TaobaoOrderBean convert(Trade input) {

		TaobaoOrderBean order = new TaobaoOrderBean();

		order.setUid(String.valueOf(input.getTid()));
		order.setCreateTime(input.getCreated());
		order.setCustomerId(input.getBuyerNick());
		order.setMobile(input.getReceiverMobile());
		order.setPhone(input.getReceiverPhone());
		order.setBuyerMemo(input.getBuyerMemo());
		order.setSellerMemo(input.getSellerMemo());
		order.setTotalAmount(new BigDecimal(input.getPayment()));
		if (!StringUtils.isEmpty(input.getPostFee()))
			order.setTransFeeAmount(new BigDecimal(input.getPostFee()));
		order.setName(input.getReceiverName());
		order.setAddress(input.getReceiverState() + " "
				+ input.getReceiverCity() + " " + input.getReceiverDistrict()
				+ " " + input.getReceiverAddress());
		order.setStatus(TaobaoOrderStatus.valueOf(input.getStatus()));

		order.setItems(new TaobaoItemBean[input.getOrders().size()]);

		for (int i = 0; i < order.getItems().length; i++) {
			Order tbo = input.getOrders().get(i);
			order.getItems()[i] = new TaobaoItemBean();
			order.getItems()[i].setCount(Integer.parseInt(String.valueOf(tbo
					.getNum())));
			order.getItems()[i].setName(tbo.getTitle());
			order.getItems()[i].setOrderUid(String.valueOf(input.getTid()));
			order.getItems()[i].setItemId(tbo.getOuterIid());
			order.getItems()[i].setUnitPrice(new BigDecimal(tbo.getPrice()));
			order.getItems()[i]
					.setActualPrice(new BigDecimal(tbo.getTotalFee()));
		}

		return order;
	}
}
