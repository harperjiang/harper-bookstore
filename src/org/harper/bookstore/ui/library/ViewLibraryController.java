package org.harper.bookstore.ui.library;

import org.harper.bookstore.service.LibraryService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JTextBinding;

public class ViewLibraryController extends Controller {

	private ViewLibraryFrame frame;

	private ViewLibraryBean bean;
	
	private BindingManager manager;
	
	public ViewLibraryController() {
		super();

		frame = new ViewLibraryFrame();
		frame.setController(this);
		
		bean = new ViewLibraryBean();
		manager = new BindingManager(bean);
		initManager();
	}
	
	protected void initManager() {
		manager.addBinding(new JTextBinding(frame.getBookNameField(),"bookNameOrIsbn"));
		manager.addBinding(new TableBinding(frame.getEntryTable(),"entries"));
	}
	
	public void search() {
		bean.setEntries(new LibraryService().getLibraryReport(bean.getBookNameOrIsbn(),bean.getBorrowerInfo()));
	}
	
	public static void main(String[] args) {
		new ViewLibraryController();
	}
	
}
