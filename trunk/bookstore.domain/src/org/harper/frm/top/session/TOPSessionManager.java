package org.harper.frm.top.session;

public class TOPSessionManager {

	private static TOPSessionManager instance;

	public synchronized static TOPSessionManager getInstance() {
		if (null == instance)
			instance = new TOPSessionManager();
		return instance;
	}

	protected TOPSessionManager() {

	}

	private TOPSession session;

	public synchronized TOPSession getSession() {
		if (null == session) {
			// Create Session
			session = new TOPSession();
		}
		if (!session.isLoggedIn()) {
			session.login();
		}
		return session;
	}
}
