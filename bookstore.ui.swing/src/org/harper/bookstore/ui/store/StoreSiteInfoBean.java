package org.harper.bookstore.ui.store;

import java.util.List;

import org.harper.bookstore.domain.store.StoreSite;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class StoreSiteInfoBean extends AbstractBean {

	private List<StoreSite> sites;

	public List<StoreSite> getSites() {
		return sites;
	}

	public void setSites(List<StoreSite> sites) {
		List<StoreSite> oldSites = getSites();
		this.sites = sites;
		firePropertyChange("sites", oldSites, sites);
	}

}
