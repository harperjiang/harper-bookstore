package org.harper.bookstore.ui.common;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.harper.frm.gui.swing.comp.textfield.DateTextField;
import org.harper.frm.gui.swing.manager.AbstractBean;
import org.harper.frm.gui.swing.manager.BindingManager;

public class ChooseDateDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3023300407124869023L;

	public static class ChooseDateDialogBean extends AbstractBean {

		private Date date;

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			Date oldDate = getDate();
			this.date = date;
			firePropertyChange("date", oldDate, date);
		}

	}

	private ChooseDateDialogBean bean;

	private boolean okay;

	public ChooseDateDialog(JFrame parent, String title, String message,
			Date startDate) {
		super(parent);

		setLocation(parent.getLocation());
		setSize(250, 140);
		setTitle(title);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModal(true);
		setResizable(false);

		setLayout(new GridLayout(3, 1));

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		add(topPanel);

		JLabel messageLabel = new JLabel(message);
		topPanel.add(messageLabel);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());

		add(centerPanel);

		DateTextField dateField = new DateTextField(new SimpleDateFormat(
				"yyyy-MM-dd"));
		dateField.setPreferredSize(new Dimension(150, 20));
		centerPanel.add(dateField);

		JButton okButton = new JButton("Confirm");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				okay = true;
				ChooseDateDialog.this.dispose();
			}
		});

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ChooseDateDialog.this.dispose();
			}
		});

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(okButton);
		bottomPanel.add(cancelButton);

		add(bottomPanel);

		bean = new ChooseDateDialogBean();
		bean.setDate(null == startDate ? new Date() : startDate);

		BindingManager manager = new BindingManager(bean);
		manager.addBinding(dateField.new DateTextBinding("date"));
		manager.loadAll();

		setVisible(true);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ChooseDateDialogBean getBean() {
		return bean;
	}

	public boolean isOkay() {
		return okay;
	}

	public static void main(String[] args) {
		new ChooseDateDialog(null, "Title", "Message", new Date());
	}
}
