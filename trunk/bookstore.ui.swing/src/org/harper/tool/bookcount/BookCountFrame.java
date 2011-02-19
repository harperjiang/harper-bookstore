package org.harper.tool.bookcount;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.lang.StringUtils;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.tool.bookcount.BookCounter.BookCountTableData;

public class BookCountFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5615690420834868117L;

	private JButton exportButton;

	private JButton importButton;

	private JTextField textField;

	private CommonTableModel tableModel;

	private JTable table;

	private BookCounter bookCount;

	public BookCountFrame() {
		super();
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Book Count");
		setFont(new Font("Calibri", Font.PLAIN, 15));
		setLayout(new BorderLayout());

		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new FlowLayout());

		textField = new JTextField();
		textField.setPreferredSize(new Dimension(200, 20));
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if ('\n' == e.getKeyChar()) {
					bookCount.add(textField.getText());
					textField.setText(null);
				}
			}
		});
		upperPanel.add(textField);

		exportButton = new JButton("Export");
		exportButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				if (!StringUtils.isEmpty(bookCount.getLastDir()))
					chooser
							.setCurrentDirectory(new File(bookCount
									.getLastDir()));
				if (JFileChooser.APPROVE_OPTION == chooser.showDialog(
						BookCountFrame.this, "Choose")) {
					try {
						bookCount.export(chooser.getSelectedFile()
								.getAbsolutePath());
						bookCount.setLastDir(chooser.getSelectedFile()
								.getAbsolutePath());
						JOptionPane.showMessageDialog(BookCountFrame.this,
								"Data Exported");
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(BookCountFrame.this, e1
								.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);
						throw new RuntimeException(e1);
					}
				}

			}

		});
		upperPanel.add(exportButton);

		importButton = new JButton("Import");
		importButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				if (!StringUtils.isEmpty(bookCount.getLastDir()))
					chooser
							.setCurrentDirectory(new File(bookCount
									.getLastDir()));
				if (JFileChooser.APPROVE_OPTION == chooser.showDialog(
						BookCountFrame.this, "Choose")) {
					try {
						bookCount.importData(chooser.getSelectedFile()
								.getAbsolutePath());
						bookCount.setLastDir(chooser.getSelectedFile()
								.getAbsolutePath());
						JOptionPane.showMessageDialog(BookCountFrame.this,
								"Data Imported");
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(BookCountFrame.this, e1
								.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);
						throw new RuntimeException(e1);
					}
				}

			}

		});
		upperPanel.add(importButton);

		add(upperPanel, BorderLayout.NORTH);

		table = new JTable();
		table.setFont(getFont());
		table.getTableHeader().setFont(getFont());
		table.setRowHeight(24);
		final JScrollPane pane = new JScrollPane();
		pane.setPreferredSize(new Dimension(380, 300));
		pane.setViewportView(table);
		add(pane, BorderLayout.CENTER);

		tableModel = new CommonTableModel();
		tableModel.initialize(BookCountTableData.class);
		tableModel.setCellEditable(1, true);
		tableModel.setCellEditable(2, true);
		tableModel.setAutoAdd(false);

		table.setModel(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultRenderer(Integer.TYPE, new DefaultTableCellRenderer());
		table.setDefaultRenderer(String.class, new DefaultTableCellRenderer());
		table.setDefaultEditor(Integer.TYPE, new DefaultCellEditor(
				new JTextField()));
		table.setDefaultEditor(String.class, new DefaultCellEditor(
				new JTextField()));
		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						// System.out.println(e.getFirstIndex()+":"+e.getLastIndex());
						table.scrollRectToVisible(new Rectangle(0, table
								.getRowHeight()
								* table.getSelectedRow(), 10, 10));
					}
				});
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (127 == e.getKeyChar() && -1 != table.getSelectedRow()) {
					bookCount.remove(table.getSelectedRow());
				}
			}
		});
		bookCount = new BookCounter(tableModel, table.getSelectionModel());

		addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				bookCount.savePreference();
			}
		});

		setVisible(true);
	}

	public static void main(String[] args) {
		new BookCountFrame();
	}

}
