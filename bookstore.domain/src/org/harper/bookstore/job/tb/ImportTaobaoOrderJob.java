package org.harper.bookstore.job.tb;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.harper.bookstore.job.Job;
import org.harper.bookstore.service.InterfaceService;
import org.harper.bookstore.service.bean.TaobaoOrderBean;
import org.harper.bookstore.task.tb.ConvertTaobaoOrderTask;
import org.harper.frm.core.logging.LogManager;
import org.harper.frm.top.session.TOPSession;
import org.harper.frm.top.session.TOPSessionManager;

import com.taobao.api.TaobaoApiException;
import com.taobao.api.TaobaoRestClient;
import com.taobao.api.model.Trade;
import com.taobao.api.model.TradesGetResponse;
import com.taobao.api.model.TradesSoldIncrementGetRequest;

public class ImportTaobaoOrderJob implements Job {

	@Override
	public void run() {
		TOPSession ssn = TOPSessionManager.getInstance().getSession();

		TaobaoRestClient client = ssn.getClient();

		TradesSoldIncrementGetRequest req = new TradesSoldIncrementGetRequest();
		req.setFields(TaobaoJobConstants.TRADE_FIELDS);
		Date current = new Date();
		req.setStartModified(new Date(current.getTime() - 3600000));
		req.setEndModified(current);
		req.setPageSize(TaobaoJobConstants.PAGE_SIZE);

		Map<String, Trade> result = new HashMap<String, Trade>();
		try {
			TradesGetResponse resp = client.tradesSoldIncrementGet(req, ssn
					.getSessionId());
			List<Trade> trads = resp.getTrades();

			for (Trade td : trads) {
				result.put(td.getTid(), td);
			}

			int total = resp.getTotalResults();
			int maxPage = (int) Math.ceil((float) total
					/ (float) req.getPageSize());
			for (int i = 1; i < maxPage; i++) {
				req.setPageNo(i + 1);
				try {
					List<Trade> nextPage = client.tradesSoldIncrementGet(req,
							ssn.getSessionId()).getTrades();

					for (Trade td : nextPage)
						result.put(td.getTid(), td);
				} catch (TaobaoApiException e) {
					LogManager.getInstance().getLogger(getClass()).error(
							"Cannot Fetch Order", e);
				}
			}
		} catch (TaobaoApiException e) {
			LogManager.getInstance().getLogger(getClass()).error(
					"Cannot Fetch Order", e);
		}

		List<TaobaoOrderBean> orders = new ConvertTaobaoOrderTask()
				.convert(result.values());
		new InterfaceService().importTaobaoOrder(orders);

	}
}
