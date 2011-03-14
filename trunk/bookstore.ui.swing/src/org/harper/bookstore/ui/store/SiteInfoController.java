package org.harper.bookstore.ui.store;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.JComponent;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.service.OrderService;
import org.harper.bookstore.service.StoreSiteService;
import org.harper.bookstore.service.bean.BookReportBean;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JTextBinding;

public class SiteInfoController extends Controller {

	private SiteInfoFrame frame;

	private BookReportBean reportBean;

	public SiteInfoController() {
		super();
		this.frame = new SiteInfoFrame();
		frame.setController(this);

		initLink();
		initManager();
	}

	private void initManager() {
		reportBean = new BookReportBean();
		manager = new BindingManager(reportBean);
		manager.addBinding(new JTextBinding(getFrame().getBookNameTextField(),
				"bookName"));
		manager.addBinding(new JTextBinding(getFrame()
				.getAverageCostTextField(), "averageCost"));
		manager.addBinding(new JTextBinding(
				getFrame().getTotalCountTextField(), "totalCount"));
		manager.addBinding(new TableBinding(getFrame().getBookReportTable(),
				"items"));
		manager.addBinding(new TableBinding(getFrame().multiBookTable, "item2s"));
		manager.addBinding(getFrame().getListPriceField().new NumTextBinding(
				"listPrice"));
	}

	private void initLink() {

	}

	public BookReportBean getReportBean() {
		return reportBean;
	}

	public void setReportBean(BookReportBean reportBean) {
		this.reportBean = reportBean;
		getManager().setBean(reportBean);
	}

	public SiteInfoFrame getFrame() {
		return frame;
	}

	public BindingManager getManager() {
		return manager;
	}

	public void load(String isbn) {
		setReportBean(new StoreSiteService().getBookReport(isbn));
	}

	public void saveListPrice() {
		Book book = reportBean.getBook();
		BigDecimal listPrice = reportBean.getListPrice();
		if (null == book || null == listPrice)
			return;
		new OrderService().updateListPrice(book, listPrice);
	}

	public void loadMulti(List<Book> books) {
		setReportBean(new StoreSiteService().getBookReports(books));
	}

	public static void main(String[] args) {
		new SiteInfoController();
	}

	@Override
	public JComponent getComponent() {
		return getFrame();
	}
}
