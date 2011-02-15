package org.harper.bookstore.ui.store;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.service.ProfileService;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JCheckBoxBinding;
import org.harper.frm.gui.swing.manager.JTextBinding;

public class NewStoreSiteDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2055849990441811615L;

	private JTextField nameTextField;

	private JTextArea descArea;

	private JCheckBox forOutputCheckBox;

	private JButton saveButton;

	public NewStoreSiteDialog(JFrame parent) {
		super(parent, true);
		setTitle("New Store Site");
		setSize(400, 480);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel nameLabel = new JLabel("Name");
		nameLabel.setPreferredSize(new Dimension(80, 20));
		add(nameLabel);

		nameTextField = new JTextField();
		nameTextField.setPreferredSize(new Dimension(300, 20));
		add(nameTextField);

		forOutputCheckBox = new JCheckBox();
		forOutputCheckBox.setText("Is For Output");
		add(forOutputCheckBox);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(390, 350));
		descArea = new JTextArea();
		scrollPane.setViewportView(descArea);
		add(scrollPane);

		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StoreSite site = new StoreSite();
				site.setName(bean.getName());
				site.setValid(true);
				site.setForOutput(bean.getForOutput());
				new ProfileService().newStoreSite(site);
				NewStoreSiteDialog.this.dispose();
			}
		});
		add(saveButton);

		bean = new NewStoreSiteBean();
		manager = new BindingManager(bean);
		manager.addBinding(new JTextBinding(nameTextField, "name"));
		manager.addBinding(new JTextBinding(descArea, "desc"));
		manager.addBinding(new JCheckBoxBinding(forOutputCheckBox, "forOutput"));

		setVisible(true);
	}

	private NewStoreSiteBean bean;

	private BindingManager manager;

	public JTextArea getDescArea() {
		return descArea;
	}

	public static void main(String[] args) {
		new NewStoreSiteDialog(null).setVisible(true);
	}
}
