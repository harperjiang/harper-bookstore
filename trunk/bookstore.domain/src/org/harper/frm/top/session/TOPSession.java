package org.harper.frm.top.session;

import org.harper.frm.core.config.ConfigManager;

import com.taobao.api.TaobaoApiException;
import com.taobao.api.TaobaoJsonRestClient;
import com.taobao.api.TaobaoRestClient;

public class TOPSession {

	private TOPLogin login;

	private String sessionId;

	private boolean loggedIn;

	public TOPSession() {
		super();
		setSessionId(ConfigManager.getInstance().getConfigValue("TOP_SESSIONKEY"));
	}
	
	public TOPLogin getLogin() {
		return login;
	}

	public void setLogin(TOPLogin login) {
		this.login = login;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void login() {
		loggedIn = true;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	private TaobaoRestClient client;

	public TaobaoRestClient getClient() {
		if (null == client) {
			String url = ConfigManager.getInstance().getConfigValue(
					"TOP_REQ_URL");
			String appkey = ConfigManager.getInstance().getConfigValue(
					"TOP_APPKEY");
			String secret = ConfigManager.getInstance().getConfigValue(
					"TOP_APPSECRET");
			try {
				client = new TaobaoJsonRestClient(url, appkey, secret);
			} catch (TaobaoApiException e) {
				throw new RuntimeException("Failed to create Client", e);
			}
		}
		return client;
	}
}
