package org.harper.bookstore.ui.library;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import org.harper.frm.gui.swing.comp.table.CommonTableModel;


public class ViewLibraryFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2315758312377437134L;

	private JTable entryTable;
	
	private JTextField bookNameField;
	
	private JTextField borrowerField;
	
	public ViewLibraryFrame() {
		super();
		setTitle("View Library");
		setSize(600,600);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout());
		add(headerPanel,BorderLayout.NORTH);
		
		headerPanel.add(new JLabel("Book Name or ISBN"));
		bookNameField = new JTextField();
		bookNameField.setPreferredSize(new Dimension(150,20));

		headerPanel.add(bookNameField);
		
		headerPanel.add(new JLabel("Borrower Info"));
		borrowerField = new JTextField();
		borrowerField.setPreferredSize(new Dimension(150,20));
		
		headerPanel.add(borrowerField);
		
		JButton searchButton = new JButton("Search");
		headerPanel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					getController().search();
				} catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(ViewLibraryFrame.this, ee
							.getMessage(), "Exception",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		
		JScrollPane sp = new JScrollPane();
		entryTable = new JTable();
		CommonTableModel ctm = new CommonTableModel();
		ctm.initialize(LibraryReportTableData.class);
		entryTable.setModel(ctm);
		sp.setViewportView(entryTable);
		entryTable.setDefaultRenderer(Integer.TYPE, new DefaultTableCellRenderer());
		add(sp,BorderLayout.CENTER);
		
		setVisible(true);
	}

	private ViewLibraryController controller;

	public ViewLibraryController getController() {
		return controller;
	}

	public void setController(ViewLibraryController controller) {
		this.controller = controller;
	}

	public JTable getEntryTable() {
		return entryTable;
	}

	public JTextField getBookNameField() {
		return bookNameField;
	}

	public JTextField getBorrowerField() {
		return borrowerField;
	}
}
