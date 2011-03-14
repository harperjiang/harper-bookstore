package org.harper.bookstore.ui.login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang.StringUtils;
import org.harper.bookstore.ui.common.UIStandard;

public class LoginDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9048211391443891540L;

	private LoginController controller;

	private JTextField userField;

	private JPasswordField passwordField;

	private JProgressBar progress;

	JButton loginButton;

	boolean pressed;

	public LoginDialog() {
		super();
		setTitle(Messages.getString("LoginDialog.title")); //$NON-NLS-1$
		setSize(300, 180);
		setModal(true);
		UIStandard.standardDialog(this);
		setLayout(new BorderLayout());

		JPanel centerPanel = new JPanel();
		centerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		centerPanel.setLayout(new FlowLayout());
		add(centerPanel, BorderLayout.CENTER);

		JLabel userLabel = new JLabel(
				Messages.getString("LoginDialog.tf_user_name")); //$NON-NLS-1$
		userLabel.setPreferredSize(new Dimension(100, 25));
		centerPanel.add(userLabel);

		userField = new JTextField();
		userField.setPreferredSize(new Dimension(150, 25));
		centerPanel.add(userField);

		JLabel passwordLabel = new JLabel(
				Messages.getString("LoginDialog.tf_password")); //$NON-NLS-1$
		passwordLabel.setPreferredSize(new Dimension(100, 25));
		centerPanel.add(passwordLabel);
		passwordField = new JPasswordField();
		passwordField.setPreferredSize(new Dimension(150, 25));
		centerPanel.add(passwordField);

		JPanel commandPanel = new JPanel();
		add(commandPanel, BorderLayout.SOUTH);
		commandPanel.setPreferredSize(new Dimension(300, 50));
		commandPanel.setLayout(new FlowLayout());

		loginButton = new JButton(Messages.getString("LoginDialog.btn_login")); //$NON-NLS-1$
		commandPanel.add(loginButton);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					loginStart();
					getController().login();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(LoginDialog.this, "Error",
							ex.getMessage(), JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		progress = new JProgressBar();
		progress.setIndeterminate(true);
		progress.setVisible(false);
		progress.setPreferredSize(new Dimension(280, 10));
		commandPanel.add(progress);

		controller = new LoginController(this);
	}

	protected void loginStart() {
		pressed = true;
		userField.setEditable(false);
		passwordField.setEditable(false);
		loginButton.setEnabled(false);
		progress.setVisible(true);
	}

	protected void loginComplete() {
		userField.setEditable(true);
		passwordField.setEditable(true);
		loginButton.setEnabled(true);
		progress.setVisible(false);

		if (StringUtils.isEmpty(getController().getBean().getErrorMsg()))
			this.dispose();
		else
			JOptionPane.showMessageDialog(LoginDialog.this, getController()
					.getBean().getErrorMsg(), "Failed to Login",
					JOptionPane.ERROR_MESSAGE);

	}

	public JTextField getUserField() {
		return userField;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public LoginController getController() {
		return controller;
	}

	public static void main(String[] args) {
		new LoginDialog().setVisible(true);
	}

	public boolean isSuccess() {
		return pressed
				&& StringUtils.isEmpty(getController().getBean().getErrorMsg());
	}
}
