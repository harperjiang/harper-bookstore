package org.harper.bookstore.repo.mem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.harper.bookstore.domain.Entity;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.store.StockAlert;
import org.harper.bookstore.domain.store.StockTaking;
import org.harper.bookstore.domain.store.StockTaking.Status;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.domain.store.Transfer;
import org.harper.bookstore.repo.StoreRepo;

public class MemoryStoreRepo implements StoreRepo {

	private MemoryCommonRepo commonRepo;

	public MemoryCommonRepo getCommonRepo() {
		return commonRepo;
	}

	public void setCommonRepo(MemoryCommonRepo commonRepo) {
		this.commonRepo = commonRepo;
	}

	@Override
	public List<StoreSite> getStoreSiteWithBook(Book book) {
		List<StoreSite> result = new ArrayList<StoreSite>();
		for (Object site : getCommonRepo().get(StoreSite.class)) {
			StoreSite ss = (StoreSite) site;
			if (null != ss.getEntry(book))
				result.add(ss);
		}
		return result;
	}

	@Override
	public int checkBookExistance(Book book) {
		return 0;
	}

	@Override
	public StoreSite getDefaultOutputSite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StoreSite> getAllStores() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StoreSite> getAvailableStores(boolean forSell) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transfer> searchTransfers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockTaking> searchStockTaking(Date from, Date to,
			StoreSite site, Status status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockAlert> getStockAlerts(StoreSite site, Book book) {
		List<StockAlert> result = new ArrayList<StockAlert>();
		List<Entity> alerts = getCommonRepo().getStores().get(StockAlert.class);
		for (Entity alert : alerts) {
			StockAlert sa = (StockAlert) alert;
			if (((site == null && null == sa.getSite()) || (null != sa
					.getSite() && sa.getSite().equals(site)))
					&& ((book == null && null == sa.getBook()) || (null != sa
							.getBook() && sa.getBook().equals(book)))) {
				result.add(sa);
			}
		}
		return result;
	}

}
