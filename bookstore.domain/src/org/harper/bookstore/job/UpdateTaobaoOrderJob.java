package org.harper.bookstore.job;

import java.util.ArrayList;
import java.util.List;

import org.harper.bookstore.domain.order.PurchaseOrder;
import org.harper.bookstore.repo.RepoFactory;
import org.harper.bookstore.service.InterfaceService;
import org.harper.bookstore.service.bean.TaobaoOrderBean;
import org.harper.bookstore.task.tb.ConvertTaobaoOrderTask;
import org.harper.frm.job.AbstractJob;
import org.harper.frm.job.JobMonitor;
import org.harper.frm.top.session.TOPSession;
import org.harper.frm.top.session.TOPSessionManager;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.TradeGetRequest;

public class UpdateTaobaoOrderJob extends AbstractJob {

	@Override
	protected Object execute(JobMonitor monitor) {
		TOPSession ssn = TOPSessionManager.getInstance().getSession();

		TaobaoClient client = ssn.getClient();

		List<PurchaseOrder> ostOrders = RepoFactory.INSTANCE.getOrderRepo()
				.getExternalOutstandingOrder();

		InterfaceService is = new InterfaceService();

		List<TaobaoOrderBean> orders = new ArrayList<TaobaoOrderBean>();

		ConvertTaobaoOrderTask task = new ConvertTaobaoOrderTask();
		for (PurchaseOrder po : ostOrders) {
			TradeGetRequest req = new TradeGetRequest();
			req.setTid(Long.valueOf(po.getRefno()));
			req.setFields(TaobaoJobConstants.TRADE_INCREGET_FIELDS);
			try {
				Trade trade = client.execute(req).getTrade();
				orders.add(task.convert(trade));
			} catch (ApiException e) {
				// TODO
			}
		}

		is.importTaobaoOrder(orders);
		return null;
	}
}
