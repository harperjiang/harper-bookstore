package org.harper.bookstore.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.harper.bookstore.domain.taobao.TradeQueryStatus;
import org.harper.bookstore.service.InterfaceService;
import org.harper.bookstore.service.bean.TaobaoOrderBean;
import org.harper.bookstore.task.tb.ConvertTaobaoOrderTask;
import org.harper.frm.core.logging.LogManager;
import org.harper.frm.job.AbstractJob;
import org.harper.frm.job.JobMonitor;
import org.harper.frm.top.session.TOPSession;
import org.harper.frm.top.session.TOPSessionManager;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.TradeGetRequest;
import com.taobao.api.request.TradesSoldIncrementGetRequest;
import com.taobao.api.response.TradeGetResponse;
import com.taobao.api.response.TradesSoldIncrementGetResponse;

public class IncreImportTaobaoOrderJob extends AbstractJob {

	private int hour = 24;

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	@Override
	public Object execute(JobMonitor monitor) {
		TOPSession ssn = TOPSessionManager.getInstance().getSession();

		TaobaoClient client = ssn.getClient();

		TradesSoldIncrementGetRequest req = new TradesSoldIncrementGetRequest();

		req.setFields(TaobaoJobConstants.TRADE_INCREGET_FIELDS);
		Date current = new Date();
		req.setStartModified(new Date(current.getTime() - getHour() * 3600000));
		req.setEndModified(current);
		req.setPageSize(TaobaoJobConstants.PAGE_SIZE);

		req.setStatus(TradeQueryStatus.WAIT_SELLER_SEND_GOODS.name());

		Map<Long, Trade> result = new HashMap<Long, Trade>();
		try {
			TradesSoldIncrementGetResponse resp = client.execute(req,
					ssn.getSessionId());
			List<Trade> trads = resp.getTrades();
			// 详细信息无法直接获取，再次调用trade.get获取详细信息
			for (Trade td : trads) {
				result.put(td.getTid(), getTradeFullInfo(td));
			}

			long total = resp.getTotalResults();
			int maxPage = (int) Math.ceil((float) total
					/ (float) req.getPageSize());
			for (int i = 1; i < maxPage; i++) {
				req.setPageNo((long) (i + 1));
				try {
					List<Trade> nextPage = client.execute(req,
							ssn.getSessionId()).getTrades();

					for (Trade td : nextPage)
						result.put(td.getTid(), getTradeFullInfo(td));
				} catch (ApiException e) {
					LogManager.getInstance().getLogger(getClass())
							.error("Cannot Fetch Order", e);
				}
			}
		} catch (ApiException e) {
			LogManager.getInstance().getLogger(getClass())
					.error("Cannot Fetch Order", e);
		}

		List<TaobaoOrderBean> orders = new ConvertTaobaoOrderTask()
				.convert(result.values());
		return new InterfaceService().importTaobaoOrder(orders);
	}

	protected Trade getTradeFullInfo(Trade trade) throws ApiException {
		TOPSession ssn = TOPSessionManager.getInstance().getSession();

		TaobaoClient client = ssn.getClient();

		TradeGetRequest req = new TradeGetRequest();

		req.setFields(TaobaoJobConstants.TRADE_ADDI_FIELDS);
		req.setTid(trade.getTid());

		TradeGetResponse resp = client.execute(req);

		Trade addiInfo = resp.getTrade();
		trade.setBuyerNick(addiInfo.getBuyerNick());
		trade.setBuyerMessage(addiInfo.getBuyerMessage());
		trade.setBuyerMemo(addiInfo.getBuyerMemo());
		trade.setSellerMemo(addiInfo.getSellerMemo());
		trade.setSellerFlag(addiInfo.getSellerFlag());
		trade.setBuyerEmail(addiInfo.getBuyerEmail());

		return trade;
	}
}
