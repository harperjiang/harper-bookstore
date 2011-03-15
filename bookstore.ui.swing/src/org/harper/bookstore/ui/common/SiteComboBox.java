package org.harper.bookstore.ui.common;

import java.util.List;

import javax.swing.JComboBox;

import org.harper.bookstore.cache.Cache;
import org.harper.bookstore.domain.store.StoreSite;

public class SiteComboBox extends JComboBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1963257440581798326L;

	public SiteComboBox(boolean hasNullVal, boolean sellonly) {
		super();

		setRenderer(new SiteListRenderer());
		if (hasNullVal)
			addItem(null);
		List<StoreSite> sites = sellonly ? Cache.getInstance()
				.getValidSellSites() : Cache.getInstance().getValidSites();
		for (StoreSite site : sites)
			addItem(site);
	}
}
