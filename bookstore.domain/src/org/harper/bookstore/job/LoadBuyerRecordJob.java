package org.harper.bookstore.job;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import org.harper.frm.job.AbstractJob;
import org.harper.frm.job.JobMonitor;
import org.harper.frm.top.session.TOPSession;
import org.harper.frm.top.session.TOPSessionManager;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TradesBoughtGetRequest;
import com.taobao.api.response.TradesBoughtGetResponse;

public class LoadBuyerRecordJob extends AbstractJob {

	@Override
	protected Object execute(JobMonitor monitor) {
		
		TOPSession ssn = TOPSessionManager.getInstance().getSession();

		TaobaoClient client = ssn.getClient();
		
		TradesBoughtGetRequest req = new TradesBoughtGetRequest();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		req.setStartCreated(sdf.parse("2011-03-01",new ParsePosition(0)));
		req.setEndCreated(sdf.parse("2011-04-11", new ParsePosition(0)));
		req.setFields(" seller_nick, buyer_nick, title, type," +
				" created, sid, tid, seller_rate, buyer_rate, " +
				"status, payment, discount_fee, adjust_fee, " +
				"post_fee, total_fee, pay_time, end_time, modified, " +
				"consign_time, buyer_obtain_point_fee, point_fee, " +
				"real_point_fee, received_payment, commission_fee, " +
				"pic_path, num_iid, num, price, cod_fee, cod_status, " +
				"shipping_type, receiver_name, receiver_state, " +
				"receiver_city, receiver_district, receiver_address, " +
				"receiver_zip, receiver_mobile, receiver_phone");
		
		try {
			TradesBoughtGetResponse resp = client.execute(req);
			
			Long resCount = resp.getTotalResults();
			System.out.println(resCount);
			
		} catch (ApiException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static void main(String[] args) {
		LoadBuyerRecordJob job = new LoadBuyerRecordJob();
		job.call(null);
	}
}
