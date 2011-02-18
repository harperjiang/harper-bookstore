package org.harper.frm.toplink;

import oracle.toplink.sessions.DatabaseLogin;
import oracle.toplink.sessions.DatabaseSession;
import oracle.toplink.sessions.Project;
import oracle.toplink.sessions.Session;

import org.harper.bookstore.repo.toplink.BookstoreProject;
import org.harper.frm.core.config.ConfigManager;


public class SessionManager {

	private static SessionManager instance;
	
	public synchronized static SessionManager getInstance() {
		if(null == instance)
			instance = new SessionManager();
		return instance;
	}
	
	private Session session;
	
	public synchronized Session getSession() {
		if(null == session) {
			Project proj = new BookstoreProject();
			
			{
				DatabaseLogin login = (DatabaseLogin)proj.getDatasourceLogin();
				login.usePlatform(new oracle.toplink.platform.database.MySQL4Platform());
				login.setDriverClassName("com.mysql.jdbc.Driver");
				login.setConnectionString(ConfigManager.getInstance().getConfigValue("JDBC_CONSTR"));
				login.setUserName(ConfigManager.getInstance().getConfigValue("JDBC_PRINCIPLE"));
				login.setPassword(ConfigManager.getInstance().getConfigValue("JDBC_CREDENTIAL"));
			}
			
			session = proj.createDatabaseSession();
			
			((DatabaseSession)session).login();
		}
		return session;
	}
}
