package org.harper.frm.job.tb;

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

import com.taobao.api.TaobaoApiException;
import com.taobao.api.TaobaoRestClient;
import com.taobao.api.model.Trade;
import com.taobao.api.model.TradeGetRequest;
import com.taobao.api.model.TradeGetResponse;
import com.taobao.api.model.TradesGetResponse;
import com.taobao.api.model.TradesSoldIncrementGetRequest;

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

		TaobaoRestClient client = ssn.getClient();

		TradesSoldIncrementGetRequest req = new TradesSoldIncrementGetRequest();

		req.setFields(TaobaoJobConstants.TRADE_INCREGET_FIELDS);
		Date current = new Date();
		req.setStartModified(new Date(current.getTime() - getHour() * 3600000));
		req.setEndModified(current);
		req.setPageSize(TaobaoJobConstants.PAGE_SIZE);

		req.setStatus(TradeQueryStatus.WAIT_SELLER_SEND_GOODS.name());

		Map<String, Trade> result = new HashMap<String, Trade>();
		try {
			TradesGetResponse resp = client.tradesSoldIncrementGet(req,
					ssn.getSessionId());
			List<Trade> trads = resp.getTrades();
			// 详细信息无法直接获取，再次调用trade.get获取详细信息
			for (Trade td : trads) {
				result.put(td.getTid(), getTradeFullInfo(td));
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
						result.put(td.getTid(), getTradeFullInfo(td));
				} catch (TaobaoApiException e) {
					LogManager.getInstance().getLogger(getClass())
							.error("Cannot Fetch Order", e);
				}
			}
		} catch (TaobaoApiException e) {
			LogManager.getInstance().getLogger(getClass())
					.error("Cannot Fetch Order", e);
		}

		List<TaobaoOrderBean> orders = new ConvertTaobaoOrderTask()
				.convert(result.values());
		return new InterfaceService().importTaobaoOrder(orders);
	}

	protected Trade getTradeFullInfo(Trade trade) throws TaobaoApiException {
		TOPSession ssn = TOPSessionManager.getInstance().getSession();

		TaobaoRestClient client = ssn.getClient();

		TradeGetRequest req = new TradeGetRequest();

		req.setFields(TaobaoJobConstants.TRADE_ADDI_FIELDS);
		req.setTid(trade.getTid());

		TradeGetResponse resp = client.tradeGet(req);

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
