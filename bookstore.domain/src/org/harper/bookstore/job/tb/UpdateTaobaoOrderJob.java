package org.harper.bookstore.job.tb;

import java.util.ArrayList;
import java.util.List;

import org.harper.bookstore.domain.order.PurchaseOrder;
import org.harper.bookstore.job.AbstractJob;
import org.harper.bookstore.job.JobMonitor;
import org.harper.bookstore.repo.RepoFactory;
import org.harper.bookstore.service.InterfaceService;
import org.harper.bookstore.service.bean.TaobaoOrderBean;
import org.harper.bookstore.task.tb.ConvertTaobaoOrderTask;
import org.harper.frm.top.session.TOPSession;
import org.harper.frm.top.session.TOPSessionManager;

import com.taobao.api.TaobaoApiException;
import com.taobao.api.TaobaoRestClient;
import com.taobao.api.model.Trade;
import com.taobao.api.model.TradeGetRequest;

public class UpdateTaobaoOrderJob extends AbstractJob {

	@Override
	protected Object execute(JobMonitor monitor) {
		TOPSession ssn = TOPSessionManager.getInstance().getSession();

		TaobaoRestClient client = ssn.getClient();

		List<PurchaseOrder> ostOrders = RepoFactory.INSTANCE.getOrderRepo()
				.getExternalOutstandingOrder();

		InterfaceService is = new InterfaceService();

		List<TaobaoOrderBean> orders = new ArrayList<TaobaoOrderBean>();

		ConvertTaobaoOrderTask task = new ConvertTaobaoOrderTask();
		for (PurchaseOrder po : ostOrders) {
			TradeGetRequest req = new TradeGetRequest();
			req.setTid(po.getRefno());
			req.setFields(TaobaoJobConstants.TRADE_INCREGET_FIELDS);
			try {
				Trade trade = client.tradeGet(req).getTrade();
				orders.add(task.convert(trade));
			} catch (TaobaoApiException e) {
				// TODO
			}
		}

		is.importTaobaoOrder(orders);
		return null;
	}
}
