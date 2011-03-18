package org.harper.bookstore.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.harper.bookstore.domain.order.OrderItem;
import org.harper.bookstore.domain.order.PurchaseOrder;
import org.harper.bookstore.domain.store.StoreEntry;
import org.harper.bookstore.repo.OrderRepo;
import org.harper.bookstore.repo.RepoFactory;
import org.harper.bookstore.service.bean.PurchaseReportItem;
import org.harper.bookstore.service.bean.report.ProfitRateResultBean;
import org.harper.bookstore.service.bean.report.ProfitRateResultBean.ProfitRateItemBean;
import org.harper.bookstore.service.bean.report.SAPReportResultBean;
import org.harper.bookstore.service.bean.report.SAPReportResultBean.SAPData;
import org.harper.frm.core.tools.sort.HeapSorter;

public class ReportService extends Service {

	public List<PurchaseReportItem> purchaseReport(Date start, Date stop) {

		List<PurchaseReportItem> list = new ArrayList<PurchaseReportItem>();

		return list;
	}

	public SAPReportResultBean salesAndProfitReport(Date start, Date stop) {
		startTransaction();
		try {
			SAPReportResultBean result = new SAPReportResultBean();

			List<PurchaseOrder> pos = (List) getOrderRepo().searchOrder(
					null,
					"PO",
					start,
					stop,
					new int[] { PurchaseOrder.Status.DRAFT.ordinal(),
							PurchaseOrder.Status.CONFIRM.ordinal() }, null,
					null, null);

			Map<String, SAPData> dataMap = new HashMap<String, SAPData>();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			for (PurchaseOrder po : pos) {
				String dateStr = dateFormat.format(po.getCreateDate());
				if (!dataMap.containsKey(dateStr)) {
					dataMap.put(dateStr, new SAPData(po.getCreateDate(),
							BigDecimal.ZERO, BigDecimal.ZERO));
				}
				SAPData data = dataMap.get(dateStr);
				data.setSelling(data.getSelling().add(
						po.getTotalAmt().subtract(po.getFeeAmount())));
				for (OrderItem item : po.getItems()) {
					StoreEntry entry = po.getSite().getEntry(item.getBook());
					if (null != entry) {
						BigDecimal profit = item.getUnitPrice().subtract(
								entry.getUnitPrice());
						data.setProfit(data.getProfit().add(profit));
					}
				}
			}
			List<SAPData> dataList = new ArrayList<SAPData>();
			dataList.addAll(dataMap.values());
			new HeapSorter(true).sort(dataList, new String[] { "time" },
					new boolean[] { true });
			result.setDatas(dataList);
			return result;
		} finally {
			releaseTransaction();
		}
	}

	public ProfitRateResultBean getProfitRate(Date start, Date stop) {
		startTransaction();
		try {
			ProfitRateResultBean result = new ProfitRateResultBean();

			List<PurchaseOrder> pos = (List) getOrderRepo().searchOrder(
					null,
					"PO",
					start,
					stop,
					new int[] { PurchaseOrder.Status.DRAFT.ordinal(),
							PurchaseOrder.Status.CONFIRM.ordinal() }, null,
					null, null);

			BigDecimal[] level = new BigDecimal[] { BigDecimal.ZERO,
					new BigDecimal("0.2"), new BigDecimal("0.4"),
					new BigDecimal("0.6"), new BigDecimal(0.8), BigDecimal.ONE };
			ProfitRateItemBean items[] = new ProfitRateItemBean[5];
			for (int i = 0; i < items.length; i++)
				items[i] = new ProfitRateItemBean(0, level[i], level[i + 1]);
			for (PurchaseOrder po : pos) {
				BigDecimal selling = null == po.getFeeAmount() ? po
						.getTotalAmt() : po.getTotalAmt().subtract(
						po.getFeeAmount());
				BigDecimal profit = BigDecimal.ZERO;
				for (OrderItem item : po.getItems()) {
					StoreEntry entry = po.getSite().getEntry(item.getBook());
					if (null != entry) {
						profit = profit.add(item.getUnitPrice().subtract(
								entry.getUnitPrice()));
					}
				}

				BigDecimal rate = profit.divide(selling, 4,
						BigDecimal.ROUND_HALF_UP);
				for (int i = 0; i < level.length - 1; i++) {
					if (level[i].compareTo(rate) <= 0
							&& level[i + 1].compareTo(rate) >= 0) {
						items[i].setCount(items[i].getCount() + 1);
						items[i].setSelling(items[i].getSelling().add(selling));
						break;
					}
				}
			}

			for (int i = 0; i < items.length; i++)
				result.addItem(items[i]);

			return result;
		} finally {
			releaseTransaction();
		}
	}

	private OrderRepo orderRepo;

	public OrderRepo getOrderRepo() {
		if (null == orderRepo)
			orderRepo = RepoFactory.INSTANCE.getOrderRepo();
		return orderRepo;
	}

	public void setOrderRepo(OrderRepo orderRepo) {
		this.orderRepo = orderRepo;
	}

}
