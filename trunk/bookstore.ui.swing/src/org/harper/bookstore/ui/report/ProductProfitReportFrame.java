package org.harper.bookstore.ui.report;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.ui.common.ISBNTextField;
import org.harper.bookstore.ui.common.ISBNTextField.Callback;
import org.harper.frm.gui.swing.comp.textfield.DateTextField;
import org.harper.frm.gui.swing.comp.window.JPowerWindowEditor;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

public class ProductProfitReportFrame extends JPowerWindowEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4088209175174137830L;

	private DateTextField fromDateField;

	private DateTextField toDateField;
	
	private ISBNTextField isbnField;
	
	private JTextField bookNameField;

	private JButton searchButton;

	private JFreeChart chart;

	private JTable dataTable;
	
	private ProductProfitReportController controller;

	public ProductProfitReportFrame() {
		super("产品销售利润表");
		setSize(800, 600);
		// setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);

		topPanel.add(new JLabel(Messages
				.getString("ReportFrame.label_start_time"))); //$NON-NLS-1$
		fromDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd")); //$NON-NLS-1$
		fromDateField.setPreferredSize(new Dimension(120, 25));
		topPanel.add(fromDateField);
		topPanel.add(new JLabel(Messages
				.getString("ReportFrame.label_stop_time"))); //$NON-NLS-1$
		toDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd")); //$NON-NLS-1$
		toDateField.setPreferredSize(new Dimension(120, 25));
		topPanel.add(toDateField);

		isbnField = new ISBNTextField();
		isbnField.setCallback(new Callback() {
			@Override
			public void call(Book book) {
				getController().getBean().setBook(book);
			}

			@Override
			public void call(List<Book> books) {
				if(books.size() ==1)
					call(books.get(0));
			}

			@Override
			public void exception(Exception e) {
			}	
		});
		
		searchButton = new JButton(Messages.getString("ReportFrame.btn_search")); //$NON-NLS-1$
		topPanel.add(searchButton);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 1, 5, 5));
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(centerPanel, BorderLayout.CENTER);

		chart = createChart();
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setDoubleBuffered(true);
		chartPanel.setDomainZoomable(true);
		chartPanel.setFillZoomRectangle(true);
		chartPanel.setMouseWheelEnabled(true);

		centerPanel.add(chartPanel);

		dataTable = createTable();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(dataTable);
		centerPanel.add(scrollPane);
	}

	protected JFreeChart createChart() {
		return null;
	}

	protected JTable createTable() {
		return null;
	}

	public DateTextField getFromDateField() {
		return fromDateField;
	}

	public DateTextField getToDateField() {
		return toDateField;
	}

	public JFreeChart getChart() {
		return chart;
	}

	public JButton getSearchButton() {
		return searchButton;
	}

	public JTable getDataTable() {
		return dataTable;
	}

	public ProductProfitReportController getController() {
		return controller;
	}

	public void setController(ProductProfitReportController controller) {
		this.controller = controller;
	}

	public JTextField getBookNameField() {
		return bookNameField;
	}
}
