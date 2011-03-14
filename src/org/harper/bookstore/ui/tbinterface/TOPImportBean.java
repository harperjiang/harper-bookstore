package org.harper.bookstore.ui.tbinterface;

import java.util.Date;

import org.harper.bookstore.util.Utilities;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class TOPImportBean extends AbstractBean {

	private Date startDate = Utilities.getBeginOfDate(3);

	private Date stopDate = Utilities.getEndOfDate();

	public TOPImportBean() {
		super();
		
		
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStopDate() {
		return stopDate;
	}

	public void setStopDate(Date stopDate) {
		this.stopDate = stopDate;
	}

}
