package org.harper.bookstore.ui.store;

import java.util.List;

import org.harper.bookstore.domain.store.StockAlert;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class StockAlertBean extends AbstractBean {

	private List<StockAlert> alerts;

	public List<StockAlert> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<StockAlert> alerts) {
		List<StockAlert> old = getAlerts();
		this.alerts = alerts;
		firePropertyChange("alerts", old, alerts);
	}

}
