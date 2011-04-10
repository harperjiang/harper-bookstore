package org.harper.bookstore.job;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import org.harper.frm.job.Job;
import org.harper.frm.job.JobMonitor;
import org.harper.frm.top.session.TOPSession;
import org.harper.frm.top.session.TOPSessionManager;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.request.WangwangEserviceChatrecordGetRequest;
import com.taobao.api.response.WangwangEserviceChatrecordGetResponse;

public class LoadChatLogJob implements Job {

	@Override
	public Object call(JobMonitor monitor) {
		if (null != monitor)
			monitor.start();
		TOPSession ssn = TOPSessionManager.getInstance().getSession();

		DefaultTaobaoClient client = (DefaultTaobaoClient) ssn.getClient();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		WangwangEserviceChatrecordGetRequest req = new WangwangEserviceChatrecordGetRequest();
		req.setServiceStaffId("cntaobaocndebbie");
		req.setStartDate(sdf.parse("2011-04-01", new ParsePosition(0)));
		req.setEndDate(sdf.parse("2011-04-08", new ParsePosition(0)));

		try {
			WangwangEserviceChatrecordGetResponse response = client.execute(
					req, null);
			System.out.println(response.getLogFileUrl());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void main(String[] args) {
		LoadChatLogJob job = new LoadChatLogJob();
		job.call(null);
	}
}
