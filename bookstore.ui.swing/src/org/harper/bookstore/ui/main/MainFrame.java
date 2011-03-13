package org.harper.bookstore.ui.main;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JToolBar;

import org.harper.bookstore.ui.common.UIStandard;
import org.harper.bookstore.ui.delivery.DOController;
import org.harper.bookstore.ui.delivery.QueryDOController;
import org.harper.bookstore.ui.library.BorrowBookController;
import org.harper.bookstore.ui.library.ReturnBookController;
import org.harper.bookstore.ui.library.ViewLibraryController;
import org.harper.bookstore.ui.order.POController;
import org.harper.bookstore.ui.order.SOController;
import org.harper.bookstore.ui.order.ViewOrderController;
import org.harper.bookstore.ui.order.ViewPurchaseOrderController;
import org.harper.bookstore.ui.print.PrintExpressOrderController;
import org.harper.bookstore.ui.profile.BookManagerController;
import org.harper.bookstore.ui.profile.BookSetManageController;
import org.harper.bookstore.ui.setting.ContactInfoController;
import org.harper.bookstore.ui.store.ManageStoreController;
import org.harper.bookstore.ui.store.QueryStockTakingController;
import org.harper.bookstore.ui.store.SiteInfoController;
import org.harper.bookstore.ui.store.StockTakingController;
import org.harper.bookstore.ui.store.TransferPlanController;
import org.harper.bookstore.ui.store.ViewTransferController;
import org.harper.bookstore.ui.tbinterface.TOPImportController;
import org.harper.bookstore.ui.tbinterface.TaobaoCSVImportController;
import org.harper.bookstore.ui.todo.ViewTodoController;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -308971708613871242L;

	public MainFrame() {
		super();
		setTitle(Messages.getString("MainFrame.title")); //$NON-NLS-1$
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new GridLayout(9, 1));
		setSize(new Dimension(450, 300));
		setResizable(false);

		JToolBar orderToolBar = new JToolBar();
		orderToolBar.setFloatable(false);
		add(orderToolBar);

		orderToolBar.add(new ControllerAction(Messages
				.getString("MainFrame.create_purchase_order"), //$NON-NLS-1$
				POController.class));
		orderToolBar.add(new ControllerAction(Messages
				.getString("MainFrame.create_supply_order"), //$NON-NLS-1$
				SOController.class));
		orderToolBar.add(new ControllerAction(Messages
				.getString("MainFrame.query_orders"), //$NON-NLS-1$
				ViewOrderController.class));
		orderToolBar.add(new ControllerAction(Messages
				.getString("MainFrame.query_po"), //$NON-NLS-1$
				ViewPurchaseOrderController.class));

		JToolBar deliveryToolBar = new JToolBar();
		deliveryToolBar.setFloatable(false);
		add(deliveryToolBar);
		deliveryToolBar.add(new ControllerAction(Messages
				.getString("MainFrame.create_do"), DOController.class)); //$NON-NLS-1$
		deliveryToolBar.add(new ControllerAction(Messages
				.getString("MainFrame.query_do"), //$NON-NLS-1$
				QueryDOController.class));

		JToolBar reportToolBar = new JToolBar();
		reportToolBar.setFloatable(false);
		add(reportToolBar);
		
		reportToolBar.add(new ControllerAction(Messages
				.getString("MainFrame.store_info"), //$NON-NLS-1$
				SiteInfoController.class));
		reportToolBar.add(new ControllerAction(Messages
				.getString("MainFrame.manage_store"), //$NON-NLS-1$
				ManageStoreController.class));
		

		JToolBar storeToolBar = new JToolBar();
		storeToolBar.setFloatable(false);
		add(storeToolBar);
		
		storeToolBar.add(new ControllerAction(Messages
				.getString("MainFrame.store_transfer"), //$NON-NLS-1$
				TransferPlanController.class));
		storeToolBar.add(new ControllerAction(Messages
				.getString("MainFrame.view_transfer"), //$NON-NLS-1$
				ViewTransferController.class));
		storeToolBar.add(new ControllerAction(Messages
				.getString("MainFrame.stock_taking"), //$NON-NLS-1$
				StockTakingController.class));
		storeToolBar.add(new ControllerAction(Messages
				.getString("MainFrame.view_stock_taking"), //$NON-NLS-1$
				QueryStockTakingController.class));
		
		JToolBar profileToolBar = new JToolBar();
		profileToolBar.setFloatable(false);
		add(profileToolBar);

		profileToolBar.add(new ControllerAction(Messages
				.getString("MainFrame.book_manage"), //$NON-NLS-1$
				BookManagerController.class));
		profileToolBar.add(new ControllerAction(Messages
				.getString("MainFrame.book_set_manage"), //$NON-NLS-1$
				BookSetManageController.class));

		JToolBar todoToolBar = new JToolBar();
		todoToolBar.setFloatable(false);
		add(todoToolBar);

		todoToolBar.add(new ControllerAction(Messages.getString("MainFrame.view_todo"), //$NON-NLS-1$
				ViewTodoController.class));

		JToolBar libraryToolBar = new JToolBar();
		libraryToolBar.setFloatable(false);
		add(libraryToolBar);

		libraryToolBar.add(new ControllerAction(Messages
				.getString("MainFrame.borrow_book"), //$NON-NLS-1$
				BorrowBookController.class));
		libraryToolBar.add(new ControllerAction(Messages
				.getString("MainFrame.return_book"), //$NON-NLS-1$
				ReturnBookController.class));
		libraryToolBar.add(new ControllerAction(Messages
				.getString("MainFrame.view_library"), //$NON-NLS-1$
				ViewLibraryController.class));
		// libraryToolBar.add(new
		// ControllerAction("Manage Borrower",ManageBorrowerController.class));

		JToolBar importToolBar = new JToolBar();
		importToolBar.setFloatable(false);
		add(importToolBar);

		importToolBar.add(new ControllerAction(Messages
				.getString("MainFrame.import_taobao_order"), //$NON-NLS-1$
				TaobaoCSVImportController.class));
		importToolBar.add(new ControllerAction(Messages
				.getString("MainFrame.import_top"), //$NON-NLS-1$
				TOPImportController.class));

		JToolBar printToolBar = new JToolBar();
		printToolBar.setFloatable(false);
		add(printToolBar);

		printToolBar.add(new ControllerAction(Messages
				.getString("MainFrame.print_express"), //$NON-NLS-1$
				PrintExpressOrderController.class));
		printToolBar.add(new ControllerAction(Messages
				.getString("MainFrame.set_contact_info"), //$NON-NLS-1$
				ContactInfoController.class));
		this.setSize(getPreferredSize());
		UIStandard.standardFrame(this);
		setVisible(true);

	}

	public static void main(String[] args) {
		// UIManager.put("InternalFrame", new Font("隶书",Font.PLAIN,24));
		// for(Entry entry :UIManager.getLookAndFeelDefaults().entrySet()) {
		// if(String.valueOf(entry.getKey()).contains("font"))
		// System.out.println(entry.getKey());
		// }
		new MainFrame();
	}
}
