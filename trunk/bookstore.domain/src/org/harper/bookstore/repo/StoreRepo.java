package org.harper.bookstore.repo;

import java.util.Date;
import java.util.List;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.store.StockTaking;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.domain.store.Transfer;

public interface StoreRepo {

	public List<StoreSite> getStoreSiteWithBook(Book book);

	public int checkBookExistance(Book book);

	public StoreSite getDefaultOutputSite();

	public List<StoreSite> getAllStores();

	public List<StoreSite> getAvailableStores(boolean forSell);

	public List<Transfer> searchTransfers();

	public List<StockTaking> searchStockTaking(Date from, Date to,
			StoreSite site, StockTaking.Status status);
}
