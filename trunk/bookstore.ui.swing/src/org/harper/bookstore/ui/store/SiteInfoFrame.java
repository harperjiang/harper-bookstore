package org.harper.bookstore.ui.store;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.ui.common.ISBNTextField;
import org.harper.bookstore.ui.common.ISBNTextField2;
import org.harper.bookstore.ui.common.ISBNTextField.Callback;
import org.harper.bookstore.ui.common.ISBNTextField2.Callback2;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.textfield.NumTextField;
import org.springframework.util.CollectionUtils;

public class SiteInfoFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6630023311494115771L;

	ISBNTextField isbnTextField;

	JTable bookReportTable;

	JTextField bookNameTextField;

	JTextField averageCostTextField;

	JTextField totalCountTextField;
	
	NumTextField listPriceField;
	
	JButton saveListPriceButton;

	SiteInfoController controller;

	ISBNTextField2 multiIsbnField;

	JTable multiBookTable;

	public SiteInfoFrame() {
		super();
		setTitle("Site Info");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());

		JTabbedPane mainTab = new JTabbedPane();
		add(mainTab, BorderLayout.CENTER);

		JPanel singlePanel = new JPanel();
		singlePanel.setLayout(new BorderLayout());
		mainTab.addTab("Single Book", singlePanel);

		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout());

		JLabel isbnLabel = new JLabel("Input ISBN to check Storage:");
		headerPanel.add(isbnLabel);

		isbnTextField = new ISBNTextField();
		isbnTextField.setPreferredSize(new Dimension(200, 20));
		headerPanel.add(isbnTextField);
		isbnTextField.setCallback(new Callback() {

			@Override
			public void call(Book isbn) {
				controller.load(isbn.getIsbn());
			}

			@Override
			public void call(List<Book> books) {
				if (CollectionUtils.isEmpty(books))
					return;
				if (books.size() > 1)
					throw new UnsupportedOperationException(
							"Cannot Support multiple books");
				call(books.get(0));
			}

			@Override
			public void exception(Exception e) {
				JOptionPane.showMessageDialog(SiteInfoFrame.this, e
						.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		});

		singlePanel.add(headerPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel();
		singlePanel.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout());

		JPanel basicInfoPanel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		basicInfoPanel.setLayout(layout);
		centerPanel.add(basicInfoPanel, BorderLayout.NORTH);

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.0;

		JLabel bookNameLabel = new JLabel("Book Name");
		layout.setConstraints(bookNameLabel, c);
		basicInfoPanel.add(bookNameLabel);

		c.insets = new Insets(10, 10, 10, 10);
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;

		bookNameTextField = new JTextField();
		layout.setConstraints(bookNameTextField, c);
		bookNameTextField.setEditable(false);
		basicInfoPanel.add(bookNameTextField);

		JLabel averageCostLabel = new JLabel("Average Cost");
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0.0;
		layout.setConstraints(averageCostLabel, c);
		basicInfoPanel.add(averageCostLabel);

		averageCostTextField = new JTextField();
		averageCostTextField.setEditable(false);
		c.gridx = 3;
		c.weightx = 1.0;
		layout.setConstraints(averageCostTextField, c);
		basicInfoPanel.add(averageCostTextField);

		JLabel bookCountLabel = new JLabel("Total Count");
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.0;
		layout.setConstraints(bookCountLabel, c);
		basicInfoPanel.add(bookCountLabel);

		totalCountTextField = new JTextField();
		totalCountTextField.setEditable(false);
		c.gridx = 1;
		c.weightx = 1.0;
		layout.setConstraints(totalCountTextField, c);
		basicInfoPanel.add(totalCountTextField);
		
		JLabel listPriceLabel = new JLabel("List Price");
		c.gridx = 2;
		c.gridy = 1;
		c.weightx = 0.0;
		layout.setConstraints(listPriceLabel, c);
		basicInfoPanel.add(listPriceLabel);
		
		listPriceField = new NumTextField();
		c.gridx = 3;
		c.gridy = 1;
		c.weightx = 1.0;
		layout.setConstraints(listPriceField, c);
		basicInfoPanel.add(listPriceField);
		
		saveListPriceButton = new JButton("Save");
		c.gridx = 4;
		c.gridy = 1;
		c.weightx = 0.0;
		layout.setConstraints(saveListPriceButton, c);
		basicInfoPanel.add(saveListPriceButton);
		
		saveListPriceButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getController().saveListPrice();
			}
		});
		
		bookReportTable = new JTable();
		bookReportTable.setDefaultRenderer(String.class,
				new DefaultTableCellRenderer());
		bookReportTable.setDefaultRenderer(Integer.TYPE,
				new DefaultTableCellRenderer());
		CommonTableModel model = new CommonTableModel();
		model.initialize(BookReportItemTableData.class);
		bookReportTable.setModel(model);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(bookReportTable);
		centerPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel multiPanel = new JPanel();
		multiPanel.setLayout(new BorderLayout());
		mainTab.addTab("Multiple Books", multiPanel);

		JPanel multiHeaderPanel = new JPanel();
		multiPanel.add(multiHeaderPanel, BorderLayout.NORTH);
		multiHeaderPanel.setLayout(new FlowLayout());
		multiHeaderPanel.add(new JLabel("Input Book Name or ISBN"));

		multiIsbnField = new ISBNTextField2();
		multiIsbnField.setCallback(new Callback2() {

			@Override
			public void call(List<Book> books) {
				getController().loadMulti(books);
			}

			@Override
			public void exception(Exception e) {
			}
		});
		multiIsbnField.setPreferredSize(new Dimension(180, 20));
		multiHeaderPanel.add(multiIsbnField);

		JScrollPane multiBookScrollPane = new JScrollPane();
		multiBookTable = new JTable();
		multiBookScrollPane.setViewportView(multiBookTable);

		CommonTableModel ctm2 = new CommonTableModel();
		ctm2.initialize(BookReportItemTableData2.class);
		multiBookTable.setModel(ctm2);

		multiBookTable.setDefaultRenderer(Integer.TYPE,
				new DefaultTableCellRenderer());

		multiPanel.add(multiBookScrollPane, BorderLayout.CENTER);

		setVisible(true);
	}

	public JTextField getIsbnTextField() {
		return isbnTextField;
	}

	public JTable getBookReportTable() {
		return bookReportTable;
	}

	public JTextField getBookNameTextField() {
		return bookNameTextField;
	}

	public JTextField getAverageCostTextField() {
		return averageCostTextField;
	}

	public JTextField getTotalCountTextField() {
		return totalCountTextField;
	}

	public SiteInfoController getController() {
		return controller;
	}

	public void setController(SiteInfoController controller) {
		this.controller = controller;
	}

	public static void main(String[] args) {
		new SiteInfoFrame();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public NumTextField getListPriceField() {
		return listPriceField;
	}
}
