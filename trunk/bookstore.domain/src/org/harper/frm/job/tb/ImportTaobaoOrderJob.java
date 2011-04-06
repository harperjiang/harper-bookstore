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

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.TradeGetRequest;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.TradeGetResponse;
import com.taobao.api.response.TradesSoldGetResponse;

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
	protected Object execute(JobMonitor monitor) {
		if (null != monitor)
			monitor.start();
		TOPSession ssn = TOPSessionManager.getInstance().getSession();

		TaobaoClient client = ssn.getClient();

		TradesSoldGetRequest req = new TradesSoldGetRequest();

		req.setFields(TaobaoJobConstants.TRADE_INCREGET_FIELDS);
		req.setStartCreated(start);
		req.setEndCreated(stop);
		req.setPageSize(TaobaoJobConstants.PAGE_SIZE);
		req.setStatus(status.name());

		Map<Long, Trade> result = new HashMap<Long, Trade>();
		try {
			TradesSoldGetResponse resp = client.execute(req,
					ssn.getSessionId());
			List<Trade> trads = resp.getTrades();

			for (Trade td : trads) {
				result.put(td.getTid(), getTradeFullInfo(td));
			}

			long total = resp.getTotalResults();
			int maxPage = (int) Math.ceil((float) total
					/ (float) req.getPageSize());
			for (int i = 1; i < maxPage; i++) {
				req.setPageNo((long)i + 1);
				try {
					List<Trade> nextPage = client.execute(req,
							ssn.getSessionId()).getTrades();
					for (Trade td : nextPage) {
						result.put(td.getTid(), getTradeFullInfo(td));
					}
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
		InterfaceService is = new InterfaceService();
		int total = 0;
		monitor.stop();
		monitor.start(orders.size());
		for (TaobaoOrderBean orderBean : orders) {
			total += is.importTaobaoOrder(orderBean) ? 1 : 0;
			monitor.progress(1);
		}
		monitor.stop();
		return total;
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
