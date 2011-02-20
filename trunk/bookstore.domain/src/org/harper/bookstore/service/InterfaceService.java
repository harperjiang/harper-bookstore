package org.harper.bookstore.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.toplink.sessions.UnitOfWork;

import org.apache.commons.lang.StringUtils;
import org.harper.bookstore.domain.order.DisplayItem;
import org.harper.bookstore.domain.order.OrderItem;
import org.harper.bookstore.domain.order.PurchaseOrder;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.Customer;
import org.harper.bookstore.domain.profile.Source;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.job.tb.ImportTaobaoOrderJob;
import org.harper.bookstore.job.tb.IncreImportTaobaoOrderJob;
import org.harper.bookstore.repo.OrderRepo;
import org.harper.bookstore.repo.RepoFactory;
import org.harper.bookstore.service.bean.TaobaoItemBean;
import org.harper.bookstore.service.bean.TaobaoOrderBean;

public class InterfaceService extends Service {

	public int importTaobaoOrder(List<TaobaoOrderBean> orderTable) {

		startTransaction();

		Map<String, PurchaseOrder> news = new HashMap<String, PurchaseOrder>();
		Map<String, PurchaseOrder> exist = new HashMap<String, PurchaseOrder>();

		OrderRepo repo = RepoFactory.INSTANCE.getOrderRepo();
		for (TaobaoOrderBean orderBean : orderTable) {
			if (StringUtils.isEmpty(orderBean.getUid())
					|| news.containsKey(orderBean.getUid())
					|| TaobaoOrderStatus.TRADE_CLOSED.equals(orderBean
							.getStatus()))
				continue;

			PurchaseOrder po = null;

			if (null != repo.getPurchaseOrderByRefno(orderBean.getUid())) {
				// Already included;
				// Update Status
				po = repo.getPurchaseOrderByRefno(orderBean.getUid());

				po.removeAllDispItems();
				po.setRefStatus(orderBean.getStatus().desc());
				exist.put(orderBean.getUid(), po);
			} else {

				Customer cust = getRepoFactory().getProfileRepo().getCustomer(
						Source.TAOBAO.name(), orderBean.getCustomerId());
				if (null == cust) {
					cust = new Customer();
					cust.setId(orderBean.getCustomerId());
					cust.setSource(Source.TAOBAO.name());
					cust = RepoFactory.INSTANCE.getCommonRepo().store(cust);
				}

				StoreSite defaultSite = getRepoFactory().getStoreRepo()
						.getDefaultSite();

				po = cust.placeOrder(null);
				po.setSite(defaultSite);
				news.put(orderBean.getUid(), po);
			}
			po.setCreateDate(orderBean.getCreateTime());
			po.setTotalAmt(orderBean.getTotalAmount());
			po.setFeeAmount(orderBean.getTransFeeAmount());
			po.setRemark(orderBean.getBuyerMemo());
			po.setMemo(orderBean.getSellerMemo());
			po.setRefno(orderBean.getUid());
			po.getContact().setAddress(orderBean.getAddress());
			po.getContact().setName(orderBean.getName());
			po.getContact().setPhone(orderBean.getPhone());
			po.getContact().setMobile(orderBean.getMobile());
			po.setRefStatus(orderBean.getStatus().desc());

			for (TaobaoItemBean itemBean : orderBean.getItems()) {
				// Display Item
				DisplayItem dispItem = new DisplayItem();
				dispItem.setName(itemBean.getName());
				dispItem.setCount(itemBean.getCount());
				dispItem.setUnitPrice(itemBean.getUnitPrice());
				dispItem.setActualPrice(itemBean.getActualPrice());

				po.addDispItem(dispItem);

				// Only add items for new orders
				if (0 == po.getOid()) {
					// Real Item
					OrderItem item = new OrderItem();
					if (StringUtils.isEmpty(itemBean.getItemId())) {
						continue;
					}
					Book book = RepoFactory.INSTANCE.getProfileRepo().findBook(
							itemBean.getItemId());
					if (null == book)
						continue;
					item.setBook(book);
					item.setCount(itemBean.getCount());
					item.setUnitPrice(itemBean.getUnitPrice());
					po.addItem(item);
				}
			}
		}

		for (PurchaseOrder po : news.values()) {
			po.place();
			if (po.getStatus() != PurchaseOrder.Status.CONFIRM.ordinal()
					&& TaobaoOrderStatus.TRADE_FINISHED
							.equals(TaobaoOrderStatus.getByDesc(po
									.getRefStatus()))) {
				// Auto Confirm
				po.confirm();
			}
		}
		((UnitOfWork) TransactionContext.getSession()).validateObjectSpace();
		getRepoFactory().getCommonRepo().store(exist.values());
		getRepoFactory().getCommonRepo().store(news.values());
		commitTransaction();
		return news.size();
	}

	public int importTOPOrder(Date start, Date stop) {
		ImportTaobaoOrderJob job = new ImportTaobaoOrderJob();
		job.setStart(start);
		job.setStop(stop);
		return (Integer) job.call();
	}

	public int increImportTOPOrder() {
		IncreImportTaobaoOrderJob job = new IncreImportTaobaoOrderJob();
		job.setHour(24);
		return (Integer) job.call();
	}
}
