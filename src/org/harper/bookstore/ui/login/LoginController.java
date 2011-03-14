package org.harper.bookstore.ui.login;

import javax.swing.SwingUtilities;

import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JTextBinding;

public class LoginController extends Controller {

	private LoginBean bean;

	private LoginDialog dialog;

	public LoginController(LoginDialog dlg) {
		super();
		bean = new LoginBean();
		dialog = dlg;
		initManager();
	}

	protected void initManager() {
		manager = new BindingManager(bean);
		manager.addBinding(new JTextBinding(dialog.getUserField(), "userName"));
		manager.addBinding(new JTextBinding(dialog.getPasswordField(),
				"password"));
	}

	public void login() {
		new Thread() {
			public void run() {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						getBean().setErrorCode(0);
						dialog.loginComplete(getBean().getErrorCode());
					}
				});
			}
		}.start();
	}

	public LoginBean getBean() {
		return bean;
	}
}
