package org.harper.bookstore.ui.tbinterface;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.harper.bookstore.service.InterfaceService;
import org.harper.bookstore.service.TaobaoOrderStatus;
import org.harper.bookstore.service.bean.TaobaoItemBean;
import org.harper.bookstore.service.bean.TaobaoOrderBean;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.core.logging.LogManager;

import com.oocl.frm.data.ITable;
import com.oocl.frm.data.parser.CSV2TableParser;

public class TaobaoImportController extends Controller {

	private TaobaoImportFrame frame;

	public TaobaoImportController() {
		super();

		frame = new TaobaoImportFrame();
		frame.setController(this);
	}

	public void importData() {
		File orderFile = frame.getImportOrderField().getSelectedFile();
		File itemFile = frame.getImportItemField().getSelectedFile();

		if (null == orderFile)
			throw new IllegalArgumentException("Please choose the order file");
		if (null == itemFile)
			throw new IllegalArgumentException("Please choose the item file");

		CSV2TableParser parser = new CSV2TableParser();
		parser.setCharSet("gb18030");
		parser.setHeaderExists(true);

		try {
			ITable orderTable = (ITable) parser.parse(new FileInputStream(
					orderFile));
			ITable itemTable = (ITable) parser.parse(new FileInputStream(
					itemFile));
			List<TaobaoItemBean> items = convertItemBean(itemTable);
			List<TaobaoOrderBean> orders = convertOrderBean(orderTable);

			Map<String, TaobaoOrderBean> orderMap = new HashMap<String, TaobaoOrderBean>();
			Map<String, List<TaobaoItemBean>> itemMap = new HashMap<String, List<TaobaoItemBean>>();
			for (TaobaoOrderBean order : orders) {
				orderMap.put(order.getUid(), order);
				itemMap.put(order.getUid(), new ArrayList<TaobaoItemBean>());
			}
			for (TaobaoItemBean item : items) {
				itemMap.get(item.getOrderUid()).add(item);
			}

			for (Entry<String, List<TaobaoItemBean>> entry : itemMap.entrySet()) {
				List<TaobaoItemBean> list = entry.getValue();
				TaobaoItemBean[] array = new TaobaoItemBean[list.size()];
				list.toArray(array);
				orderMap.get(entry.getKey()).setItems(array);
			}

			new InterfaceService().importTaobaoOrder(orders);
		} catch (Exception e) {
			LogManager.getInstance().getLogger(getClass()).error("", e);
			throw new IllegalArgumentException(
					"Failed to open file or illegal format", e);
		}
	}

	protected List<TaobaoOrderBean> convertOrderBean(ITable orderTable) {
		List<TaobaoOrderBean> orders = new ArrayList<TaobaoOrderBean>();
		for (int i = 0; i < orderTable.getRowCount(); i++) {
			TaobaoOrderBean iob = new TaobaoOrderBean();
			iob.setCustomerId((String) orderTable.getValueAt(i, 1));
			iob.setTotalAmount(new BigDecimal((String) orderTable.getValueAt(i,
					6)));
			iob.setTransFeeAmount(new BigDecimal((String) orderTable
					.getValueAt(i, 4)));
			iob.setUid((String) orderTable.getValueAt(i, 0));
			iob.setRemark1((String) orderTable.getValueAt(i, 11));
			iob.setRemark2((String) orderTable.getValueAt(i, 23));
			iob.setCreateTime(parseTaobaoDate((String) orderTable.getValueAt(i,
					17)));
			iob.setName((String) orderTable.getValueAt(i, 12));
			iob.setAddress((String) orderTable.getValueAt(i, 13));
			iob.setMobile((String) orderTable.getValueAt(i, 16));
			iob.setPhone((String) orderTable.getValueAt(i, 15));
			iob.setStatus(translateOrderStatus(((String) orderTable.getValueAt(
					i, 10))));
			orders.add(iob);
		}
		return orders;
	}

	static final String[] STATUS_MAP = new String[] { "没有创建支付宝交易",
			"TRADE_NO_CREATE_PAY", "等待买家确认货到付款订单信息", "WAIT_BUYER_PAY",
			"等待买家付款", "WAIT_BUYER_PAY", "买家已付款，等待卖家发货",
			"WAIT_SELLER_SEND_GOODS", "买家已确认，等待卖家发货", "WAIT_SELLER_SEND_GOODS",
			"卖家已发货，等待买家确认", "WAIT_BUYER_CONFIRM_GOODS", "卖家已发货，等待买家确认收货并付款",
			"WAIT_BUYER_CONFIRM_GOODS", "买家已签收", "TRADE_BUYER_SIGNED",
			"买家已签收，回款至卖家", "TRADE_BUYER_SIGNED", "交易成功", "TRADE_FINISHED",
			"交易关闭", "TRADE_CLOSED", "交易被淘宝关闭", "TRADE_CLOSED_BY_TAOBAO()" };

	protected TaobaoOrderStatus translateOrderStatus(String status) {
		for (int i = 0; i < STATUS_MAP.length; i += 2) {
			if (STATUS_MAP[i].equals(status))
				return TaobaoOrderStatus.valueOf(STATUS_MAP[i + 1]);
		}
		throw new IllegalArgumentException("Unrecognized Status:" + status);
	}

	protected List<TaobaoItemBean> convertItemBean(ITable itemTable) {
		List<TaobaoItemBean> items = new ArrayList<TaobaoItemBean>();
		for (int i = 0; i < itemTable.getRowCount(); i++) {
			TaobaoItemBean iib = new TaobaoItemBean();
			iib.setCount(Integer.parseInt((String) itemTable.getValueAt(i, 3)));
			iib.setItemId((String) itemTable.getValueAt(i, 9));
			iib.setOrderUid((String) itemTable.getValueAt(i, 0));
			iib
					.setUnitPrice(new BigDecimal((String) itemTable.getValueAt(
							i, 2)));
			iib.setName((String) itemTable.getValueAt(i, 1));
			items.add(iib);
		}
		return items;
	}

	protected static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	protected static Date parseTaobaoDate(String input) {
		return new SimpleDateFormat(DATE_FORMAT).parse(input,
				new ParsePosition(0));
	}
}
