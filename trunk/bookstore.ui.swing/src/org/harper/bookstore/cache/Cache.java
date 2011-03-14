package org.harper.bookstore.cache;

import java.util.ArrayList;
import java.util.List;

import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.domain.user.User;
import org.harper.bookstore.service.StoreSiteService;

public class Cache {

	private List<StoreSite> sites;

	private List<StoreSite> validSites;

	private List<StoreSite> validSellSites;

	private User currentUser;

	private Cache() {
		sites = new ArrayList<StoreSite>();
		validSites = new ArrayList<StoreSite>();
		validSellSites = new ArrayList<StoreSite>();
	}

	public void load() {
		sites = new StoreSiteService().getAllSites();
		validSites = new ArrayList<StoreSite>();
		validSellSites = new ArrayList<StoreSite>();
		for (StoreSite site : sites) {
			if (site.isValid()) {
				validSites.add(site);
			}
			if (site.isValid() && site.isForOutput()) {
				validSellSites.add(site);
			}
		}
	}

	public List<StoreSite> getSites() {
		return sites;
	}

	public List<StoreSite> getValidSites() {
		return validSites;
	}

	public List<StoreSite> getValidSellSites() {
		return validSellSites;
	}

	static Cache instance;

	public synchronized static Cache getInstance() {
		if (null == instance)
			instance = new Cache();
		return instance;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

}
