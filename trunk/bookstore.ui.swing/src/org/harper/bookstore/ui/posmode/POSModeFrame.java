package org.harper.bookstore.ui.posmode;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.service.ProfileService;
import org.harper.bookstore.ui.common.CtrlKeyAdapter;
import org.harper.bookstore.ui.common.ReturnKeyListener;
import org.harper.bookstore.ui.common.ReturnKeyListener.Callback;
import org.harper.bookstore.ui.common.UIStandard;
import org.harper.frm.core.logging.LogManager;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;

public class POSModeFrame extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2977626560183368112L;

	private JTextField inputField;

	private JTextField tradeIdField;

	private JTable bookTable;

	private POSModeController controller;

	public POSModeFrame() {
		super();
		setTitle("POS Mode");
//		setUndecorated(true);
		setModal(true);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		UIStandard.maximizedDialog(this);

		setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);

		JLabel titleLabel = new JLabel("扫描单据界面");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 50));
		topPanel.add(titleLabel);
		topPanel.add(new JLabel("按Ctrl+E退出"));
		

		JPanel centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout());

		Font textFont = new Font("微软雅黑", Font.PLAIN, 30);

		JPanel centerTopPanel = new JPanel();
		centerPanel.add(centerTopPanel, BorderLayout.NORTH);

		inputField = new JTextField();
		inputField.setPreferredSize(new Dimension(600, 40));
		inputField.setFont(textFont);
		centerTopPanel.add(inputField);

		tradeIdField = new JTextField();
		tradeIdField.setEditable(false);
		tradeIdField.setPreferredSize(new Dimension(600, 40));
		tradeIdField.setFont(textFont);
		centerTopPanel.add(tradeIdField);

		bookTable = new JTable();
		CommonTableModel ctm = new CommonTableModel();
		ctm.initialize(POSBookTableData.class);
		bookTable.setModel(ctm);
		bookTable.setFont(textFont);
		bookTable.getTableHeader().setFont(textFont);
		bookTable.setDefaultRenderer(Integer.TYPE,
				new DefaultTableCellRenderer());
		bookTable.setRowHeight(40);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(bookTable);
		centerPanel.add(scrollPane, BorderLayout.CENTER);

		inputField.addKeyListener(new CtrlKeyAdapter(KeyEvent.VK_P,
				new Runnable() {
					public void run() {
						System.out.println("Get CTRL-P, WILL PRINT");
					}
				}));
		inputField.addKeyListener(new CtrlKeyAdapter(KeyEvent.VK_S,
				new Runnable() {
					public void run() {
						getController().save();
					}
				}));
		inputField.addKeyListener(new CtrlKeyAdapter(KeyEvent.VK_E,
				new Runnable() {
					public void run() {
						POSModeFrame.this.dispose();
					}
				}));
		inputField.addKeyListener(new ReturnKeyListener(new Callback() {
			@Override
			public void call(final String word) {
				// wordArea.append(word + "\n");
				if (14 == word.length()) {
					try {
						Long.parseLong(word);

						// Trade Id
						getController().getBean().setTradeId(word);
					} catch (NumberFormatException nfe) {

					}
				} else {
					getController().loadBook(word);
				}
			}
		}, true));
	}

	public POSModeController getController() {
		return controller;
	}

	public void setController(POSModeController controller) {
		this.controller = controller;
	}

	public JTextField getTradeIdField() {
		return tradeIdField;
	}

	public JTextField getInputField() {
		return inputField;
	}

	public JTable getBookTable() {
		return bookTable;
	}
}
