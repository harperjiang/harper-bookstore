package org.harper.bookstore.ui.tbinterface;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.LogManager;
import org.harper.bookstore.ui.common.DirectoryChooseField;

public class TaobaoImportFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7082287979626118629L;

	private DirectoryChooseField importItemField;

	private DirectoryChooseField importOrderField;

	private TaobaoImportController controller;

	public TaobaoImportFrame() {
		super();
		setTitle("Import Taobao Data");
		setSize(600, 200);

		setLayout(new BorderLayout());

		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
		add(mainPanel, BorderLayout.CENTER);

		GridLayout layout = new GridLayout(5, 1);
		layout.setVgap(5);
		layout.setHgap(4);

		mainPanel.setLayout(layout);

		mainPanel.add(new JLabel("Order File"));

		importOrderField = new DirectoryChooseField();
		mainPanel.add(importOrderField);

		mainPanel.add(new JLabel("Item File"));

		importItemField = new DirectoryChooseField();
		mainPanel.add(importItemField);

		JButton importButton = new JButton("Import Data");
		importButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread() {
					public void run() {
						try {

							getController().importData();
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									JOptionPane.showMessageDialog(
											TaobaoImportFrame.this, "Imported");
								}
							});
						} catch (final Exception exception) {
							LogManager.getLogger(TaobaoImportFrame.class)
									.error("Error while importing orders",
											exception);
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									JOptionPane.showMessageDialog(
											TaobaoImportFrame.this, exception
													.getMessage(), "Exception",
											JOptionPane.ERROR_MESSAGE);
								}
							});
						}
					}
				}.start();
			}
		});
		mainPanel.add(importButton);

		setVisible(true);
	}

	public TaobaoImportController getController() {
		return controller;
	}

	public void setController(TaobaoImportController controller) {
		this.controller = controller;
	}

	public DirectoryChooseField getImportItemField() {
		return importItemField;
	}

	public DirectoryChooseField getImportOrderField() {
		return importOrderField;
	}

}
