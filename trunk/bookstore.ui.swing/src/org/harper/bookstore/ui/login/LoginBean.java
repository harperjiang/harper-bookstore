package org.harper.bookstore.ui.login;

import org.harper.frm.gui.swing.manager.AbstractBean;

public class LoginBean extends AbstractBean {

	private String userName;

	private String password;

	private int errorCode = -1;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		String oldName = getUserName();
		this.userName = userName;
		firePropertyChange("userName", oldName, userName);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		String oldPass = getPassword();
		this.password = password;
		firePropertyChange("password", oldPass, password);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
