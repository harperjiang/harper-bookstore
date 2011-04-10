package org.harper.bookstore.job;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.harper.frm.job.AbstractJob;
import org.harper.frm.job.JobMonitor;
import org.harper.frm.top.session.TOPSession;
import org.harper.frm.top.session.TOPSessionManager;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Shipping;
import com.taobao.api.request.LogisticsOrdersGetRequest;
import com.taobao.api.response.LogisticsOrdersGetResponse;

public class LoadLogisticsRecordJob extends AbstractJob {

	@Override
	protected Object execute(JobMonitor monitor) {

		TOPSession ssn = TOPSessionManager.getInstance().getSession();

		TaobaoClient client = ssn.getClient();

		LogisticsOrdersGetRequest req = new LogisticsOrdersGetRequest();
		req.setFields("tid,seller_nick,buyer_nick,delivery_start,company_name,created,out_sid");
		Date dateTime = SimpleDateFormat.getDateTimeInstance().parse(
				"2011-04-05 00:00:00", new ParsePosition(0));
		req.setStartCreated(dateTime);
		dateTime = SimpleDateFormat.getDateTimeInstance().parse(
				"2011-04-08 00:00:00", new ParsePosition(0));
		req.setEndCreated(dateTime);
		req.setPageSize(40L);
		LogisticsOrdersGetResponse response;
		try {
			response = client.execute(req, null);

			List<Shipping> shippings = response.getShippings();
			System.out.println(shippings.size());
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		LoadLogisticsRecordJob job = new LoadLogisticsRecordJob();
		job.call(null);
	}
}
