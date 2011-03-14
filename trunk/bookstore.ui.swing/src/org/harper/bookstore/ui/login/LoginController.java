package org.harper.bookstore.ui.login;

import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import org.apache.commons.lang.StringUtils;
import org.harper.bookstore.cache.Cache;
import org.harper.bookstore.domain.user.User;
import org.harper.bookstore.service.ProfileService;
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
		bean.setErrorMsg(validateBean());
		if (!StringUtils.isEmpty(bean.getErrorMsg())) {
			dialog.loginComplete();
			return;
		}
		// Backdoor
		if ("ADMIN".equals(bean.getUserName())) {
			User admin = new User();
			admin.setId("ADMIN");
			Cache.getInstance().setCurrentUser(admin);
			dialog.loginComplete();
			return;
		}
		new SwingWorker<User, Object>() {
			@Override
			protected User doInBackground() throws Exception {
				return new ProfileService().login(bean.getUserName(),
						bean.getPassword());
			}

			@Override
			protected void done() {
				try {
					User usr = get();
					Cache.getInstance().setCurrentUser(usr);
					getBean().setErrorMsg(null);
				} catch (ExecutionException e) {
					getBean().setErrorMsg(e.getCause().getMessage());
				} catch (Exception e) {
					getBean().setErrorMsg(e.getMessage());
				}
				dialog.loginComplete();
			}
		}.execute();
	}

	private String validateBean() {
		if (StringUtils.isEmpty(bean.getUserName()))
			return "Please input user name";
		if (StringUtils.isEmpty(bean.getPassword()))
			return "Please input password";
		return null;
	}

	public LoginBean getBean() {
		return bean;
	}
}
