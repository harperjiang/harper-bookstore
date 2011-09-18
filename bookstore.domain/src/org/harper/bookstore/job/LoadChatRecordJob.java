package org.harper.bookstore.job;

import java.text.SimpleDateFormat;

import org.harper.frm.job.Job;
import org.harper.frm.job.JobMonitor;
import org.harper.frm.top.session.TOPSession;
import org.harper.frm.top.session.TOPSessionManager;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.domain.Chatpeer;
import com.taobao.api.domain.Msg;
import com.taobao.api.request.WangwangEserviceChatlogGetRequest;
import com.taobao.api.request.WangwangEserviceChatpeersGetRequest;
import com.taobao.api.response.WangwangEserviceChatlogGetResponse;
import com.taobao.api.response.WangwangEserviceChatpeersGetResponse;

public class LoadChatRecordJob implements Job {

	@Override
	public Object call(JobMonitor monitor) {
		if (null != monitor)
			monitor.start();
		TOPSession ssn = TOPSessionManager.getInstance().getSession();

		DefaultTaobaoClient client = (DefaultTaobaoClient) ssn.getClient();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		WangwangEserviceChatpeersGetRequest cpreq = new WangwangEserviceChatpeersGetRequest();
		cpreq.setChatId("cntaobaocndebbie");
		
		String date = "2011-05-23";
		
		cpreq.setStartDate(date);
		cpreq.setEndDate(date);
		WangwangEserviceChatpeersGetResponse cpresp = null;
		try {
			cpresp = client.execute(cpreq);
		} catch (ApiException e1) {
			e1.printStackTrace();
		}
		for (Chatpeer cp : cpresp.getChatpeers()) {

			WangwangEserviceChatlogGetRequest req = new WangwangEserviceChatlogGetRequest();
			req.setFromId("cntaobaocndebbie");
			req.setToId(cp.getUid());
			req.setStartDate(date);
			req.setEndDate(date);

			try {
				WangwangEserviceChatlogGetResponse response = client.execute(
						req, null);
				for (Msg msg : response.getMsgs()) {
					System.out.println(cp.getUid().substring(8) + ":"
							+ msg.getDirection() + ":" + msg.getTime() + ":"
							+ msg.getContent());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public static void main(String[] args) {
		LoadChatRecordJob job = new LoadChatRecordJob();
		job.call(null);
	}
}
