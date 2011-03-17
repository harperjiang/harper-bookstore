package org.harper.bookstore.ui.main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

import org.harper.bookstore.res.ResourceLoader;
import org.harper.bookstore.ui.delivery.DOController;
import org.harper.bookstore.ui.delivery.QueryDOController;
import org.harper.bookstore.ui.delivery.QueryROController;
import org.harper.bookstore.ui.delivery.ROController;
import org.harper.bookstore.ui.order.POController;
import org.harper.bookstore.ui.order.SOController;
import org.harper.bookstore.ui.order.ViewOrderController;
import org.harper.bookstore.ui.order.ViewPurchaseOrderController;
import org.harper.bookstore.ui.print.PrintExpressOrderController;
import org.harper.bookstore.ui.profile.BookManagerController;
import org.harper.bookstore.ui.profile.BookSetManageController;
import org.harper.bookstore.ui.report.SellAndProfitReportController;
import org.harper.bookstore.ui.store.ManageStoreController;
import org.harper.bookstore.ui.store.QueryStockTakingController;
import org.harper.bookstore.ui.store.SiteInfoController;
import org.harper.bookstore.ui.store.StockAlertController;
import org.harper.bookstore.ui.store.StockTakingController;
import org.harper.bookstore.ui.store.TransferPlanController;
import org.harper.bookstore.ui.store.ViewTransferController;
import org.harper.bookstore.ui.tbinterface.TOPImportController;
import org.harper.bookstore.ui.todo.ViewTodoFrame;
import org.harper.frm.gui.swing.comp.window.ControllerAction;
import org.harper.frm.gui.swing.comp.window.JPowerWindow;

public class MainWindow extends JPowerWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2710860169827411879L;

	public MainWindow() {
		super();
		setTitle(Messages.getString("MainWindow.title")); //$NON-NLS-1$
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setSize(800, 600);

		initComponent();
	}

	protected void createMenuBar(JMenuBar menuBar) {
		JMenu systemMenu = new JMenu(Messages.getString("MainWindow.menu_sys")); //$NON-NLS-1$
		menuBar.add(systemMenu);

		JMenuItem exitItem = new JMenuItem(
				Messages.getString("MainWindow.menu_sys_exit")); //$NON-NLS-1$
		exitItem.setPreferredSize(new Dimension(120, 25));
		systemMenu.add(exitItem);
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainWindow.this.dispose();
			}
		});

		JMenu orderMenu = new JMenu(Messages.getString("MainWindow.menu_order")); //$NON-NLS-1$
		menuBar.add(orderMenu);
		orderMenu
				.add(new NewEditorAction(
						Messages.getString("MainWindow.menu_order_vieworder"), ViewOrderController.class));//$NON-NLS-1$
		orderMenu
				.add(new NewEditorAction(
						Messages.getString("MainWindow.menu_order_viewpo"), ViewPurchaseOrderController.class)); //$NON-NLS-1$));
		orderMenu.add(orderMenu.add(new NewEditorAction(Messages
				.getString("MainWindow.menu_order_createpo"), //$NON-NLS-1$
				POController.class)));
		orderMenu
				.add(new NewEditorAction(
						Messages.getString("MainWindow.menu_order_createso"), SOController.class)); //$NON-NLS-1$

		JMenu deliveryMenu = new JMenu(
				Messages.getString("MainWindow.menu_delivery")); //$NON-NLS-1$
		menuBar.add(deliveryMenu);
		deliveryMenu.add(new NewEditorAction(Messages
				.getString("MainWindow.menu_delivery_createdo"), //$NON-NLS-1$
				DOController.class));
		deliveryMenu.add(new NewEditorAction(Messages
				.getString("MainWindow.menu_delivery_viewdo"), //$NON-NLS-1$
				QueryDOController.class));
		deliveryMenu.addSeparator();
		deliveryMenu
				.add(new NewEditorAction(
						Messages.getString("MainWindow.menu_delivery_retcargo"), ROController.class)); //$NON-NLS-1$
		deliveryMenu
				.add(new NewEditorAction(
						Messages.getString("MainWindow.menu_delivery_query_ro"), QueryROController.class)); //$NON-NLS-1$

		JMenu storeMenu = new JMenu(Messages.getString("MainWindow.menu_store")); //$NON-NLS-1$
		menuBar.add(storeMenu);
		storeMenu.add(new NewEditorAction(Messages
				.getString("MainWindow.menu_store_info"), //$NON-NLS-1$
				SiteInfoController.class));
		storeMenu.add(new NewEditorAction(Messages
				.getString("MainWindow.menu_store_mgrstore"), //$NON-NLS-1$
				ManageStoreController.class));
		storeMenu.add(new NewEditorAction(Messages
				.getString("MainWindow.menu_store_setstockalert"), //$NON-NLS-1$
				StockAlertController.class));
		storeMenu.addSeparator();
		storeMenu.add(new NewEditorAction(Messages
				.getString("MainWindow.menu_store_trans"), //$NON-NLS-1$
				TransferPlanController.class));
		storeMenu.add(new NewEditorAction(Messages
				.getString("MainWindow.menu_store_viewtrans"), //$NON-NLS-1$
				ViewTransferController.class));
		storeMenu.add(new NewEditorAction(Messages
				.getString("MainWindow.menu_store_stocktaking"), //$NON-NLS-1$
				StockTakingController.class));
		storeMenu.add(new NewEditorAction(Messages
				.getString("MainWindow.menu_store_viewstocktaking"), //$NON-NLS-1$
				QueryStockTakingController.class));

		JMenu metaMenu = new JMenu(Messages.getString("MainWindow.menu_meta")); //$NON-NLS-1$
		menuBar.add(metaMenu);

		metaMenu.add(new NewEditorAction(
				Messages.getString("MainWindow.menu_meta_book"), BookManagerController.class)); //$NON-NLS-1$
		metaMenu.add(new NewEditorAction(Messages
				.getString("MainWindow.menu_meta_bookset"), //$NON-NLS-1$
				BookSetManageController.class));
		metaMenu.addSeparator();

		JMenu importMenu = new JMenu(
				Messages.getString("MainWindow.menu_import")); //$NON-NLS-1$
		menuBar.add(importMenu);
		importMenu
				.add(new NewEditorAction(
						Messages.getString("MainWindow.menu_import_top"), //$NON-NLS-1$
						TOPImportController.class,
						ResourceLoader
								.getInstance()
								.createImageIcon(
										"/org/harper/bookstore/res/icon/import_top_small.png"))); //$NON-NLS-1$

		JMenu reportMenu = new JMenu(Messages.getString("MainWindow.menu_report")); //$NON-NLS-1$
		menuBar.add(reportMenu);
		reportMenu.add(new NewEditorAction(Messages.getString("MainWindow.menu_report_sap"), //$NON-NLS-1$
				SellAndProfitReportController.class));

		JMenu utilMenu = new JMenu(Messages.getString("MainWindow.menu_util")); //$NON-NLS-1$
		menuBar.add(utilMenu);
		utilMenu.add(new ControllerAction(
				Messages.getString("MainWindow.menu_util_printexp"), PrintExpressOrderController.class)); //$NON-NLS-1$

		JMenu viewMenu = new JMenu(Messages.getString("MainWindow.menu_view")); //$NON-NLS-1$
		menuBar.add(viewMenu);
		viewMenu.add(new NewViewAction(Messages
				.getString("MainWindow.menu_view_todo"), ViewTodoFrame.class)); //$NON-NLS-1$
		viewMenu.add(new NewViewAction(Messages
				.getString("MainWindow.menu_view_pref"), null, ResourceLoader //$NON-NLS-1$
				.getInstance().createImageIcon(
						"/org/harper/bookstore/res/icon/pref_small.png"))); //$NON-NLS-1$
		// Set Menu Size;
		for (int i = 0; i < menuBar.getMenuCount(); i++) {
			JMenu menu = menuBar.getMenu(i);
			for (int j = 0; j < menu.getItemCount(); j++) {
				JMenuItem item = menu.getItem(j);
				if (null != item)
					item.setPreferredSize(new Dimension(180, 25));
			}
		}
	}

	protected void createToolBar(JToolBar toolBar) {
		// toolBar.add(new JButton(ResourceLoader.getInstance().createImageIcon(
		//				"/org/harper/bookstore/res/icon/storage.png")));//$NON-NLS-1$
		// toolBar.add(new JButton(ResourceLoader.getInstance().createImageIcon(
		//				"/org/harper/bookstore/res/icon/order.png")));//$NON-NLS-1$
		// toolBar.add(new JButton(ResourceLoader.getInstance().createImageIcon(
		//				"/org/harper/bookstore/res/icon/profile.png")));//$NON-NLS-1$
		toolBar.add(new NewEditorButton(
				Messages.getString("MainWindow.menu_import_top"), ResourceLoader.getInstance()//$NON-NLS-1$
						.createImageIcon(
								"/org/harper/bookstore/res/icon/import_top.png"),//$NON-NLS-1$
				TOPImportController.class));

	}

	protected void initComponent() {
		addView(new ViewTodoFrame());
	}

	public static void main(String[] args) {
		new MainWindow().setVisible(true);
	}
}
