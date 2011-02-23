package org.harper.bookstore.job.tb;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.harper.bookstore.domain.taobao.TradeQueryStatus;
import org.harper.bookstore.job.AbstractJob;
import org.harper.bookstore.service.InterfaceService;
import org.harper.bookstore.service.bean.TaobaoOrderBean;
import org.harper.bookstore.task.tb.ConvertTaobaoOrderTask;
import org.harper.frm.core.logging.LogManager;
import org.harper.frm.top.session.TOPSession;
import org.harper.frm.top.session.TOPSessionManager;

import com.taobao.api.TaobaoApiException;
import com.taobao.api.TaobaoRestClient;
import com.taobao.api.model.Trade;
import com.taobao.api.model.TradeGetRequest;
import com.taobao.api.model.TradeGetResponse;
import com.taobao.api.model.TradesGetResponse;
import com.taobao.api.model.TradesSoldGetRequest;

public class ImportTaobaoOrderJob extends AbstractJob {

	private Date start;

	private Date stop;

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getStop() {
		return stop;
	}

	public void setStop(Date stop) {
		this.stop = stop;
	}

	private TradeQueryStatus status = TradeQueryStatus.WAIT_BUYER_CONFIRM_GOODS;

	public TradeQueryStatus getStatus() {
		return status;
	}

	public void setStatus(TradeQueryStatus status) {
		this.status = status;
	}

	@Override
	protected Object execute() {
		TOPSession ssn = TOPSessionManager.getInstance().getSession();

		TaobaoRestClient client = ssn.getClient();

		TradesSoldGetRequest req = new TradesSoldGetRequest();

		req.setFields(TaobaoJobConstants.TRADE_INCREGET_FIELDS);
		req.setStartCreated(start);
		req.setEndCreated(stop);
		req.setPageSize(TaobaoJobConstants.PAGE_SIZE);
		req.setStatus(status.name());

		Map<String, Trade> result = new HashMap<String, Trade>();
		try {
			TradesGetResponse resp = client.tradesSoldGet(req,
					ssn.getSessionId());
			List<Trade> trads = resp.getTrades();

			for (Trade td : trads) {
				result.put(td.getTid(), getTradeFullInfo(td));
			}

			int total = resp.getTotalResults();
			int maxPage = (int) Math.ceil((float) total
					/ (float) req.getPageSize());
			for (int i = 1; i < maxPage; i++) {
				req.setPageNo(i + 1);
				try {
					List<Trade> nextPage = client.tradesSoldGet(req,
							ssn.getSessionId()).getTrades();
					for (Trade td : nextPage) {
						result.put(td.getTid(), getTradeFullInfo(td));
					}
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
