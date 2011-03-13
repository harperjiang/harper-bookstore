package org.harper.bookstore.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.harper.bookstore.domain.order.ListPrice;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.BookSet;
import org.harper.bookstore.domain.store.StockAlert;
import org.harper.bookstore.domain.store.StockTaking;
import org.harper.bookstore.domain.store.StoreEntry;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.domain.store.Transfer;
import org.harper.bookstore.repo.CommonRepo;
import org.harper.bookstore.repo.RepoFactory;
import org.harper.bookstore.service.bean.BookReportBean;
import org.harper.bookstore.service.bean.BookReportItemBean;
import org.harper.bookstore.service.bean.BookReportItemBean2;
import org.harper.frm.ValidateException;
import org.harper.frm.core.tools.sort.HeapSorter;
import org.springframework.util.CollectionUtils;

public class StoreSiteService extends Service {

	public List<StoreSite> getAvailableSite(boolean forSell) {
		startTransaction();
		try {
			return getRepoFactory().getStoreRepo().getAvailableStores(forSell);
		} finally {
			releaseTransaction();
		}
	}

	public List<StoreSite> getAllSites() {
		startTransaction();
		try {
			return getRepoFactory().getStoreRepo().getAllStores();
		} finally {
			releaseTransaction();
		}
	}

	public void saveSites(List<StoreSite> sites) {
		startTransaction();
		try {
			getRepoFactory().getCommonRepo().store(sites);
			commitTransaction();
		} catch (Exception e) {
			releaseTransaction();
		}
	}

	public List<StockAlert> getAlerts(StoreSite site, Book book) {
		startTransaction();
		try {
			return getRepoFactory().getStoreRepo().getStockAlerts(site, book);
		} finally {
			releaseTransaction();
		}
	}

	public StockAlert saveAlert(StockAlert alert) {
		startTransaction();
		try {
			List<StockAlert> existed = getAlerts(alert.getSite(),
					alert.getBook());
			if (!CollectionUtils.isEmpty(existed) && !existed.contains(alert))
				throw new IllegalArgumentException("Same Alert already existed");
			StockAlert result = getRepoFactory().getCommonRepo().store(alert);
			commitTransaction();
			return result;
		} catch (Exception e) {
			releaseTransaction();
			if (e instanceof RuntimeException)
				throw (RuntimeException) e;
			throw new RuntimeException(e);
		}
	}

	public List<Transfer> searchTransfers(Transfer.Status status, Date from,
			Date to, StoreSite fromSite, StoreSite toSite) {
		startTransaction();
		try {
			return getRepoFactory().getStoreRepo().searchTransfers();
		} finally {
			releaseTransaction();
		}
	}

	public List<StockTaking> searchStockTakings(Date from, Date to,
			StockTaking.Status status, StoreSite site) {
		startTransaction();
		try {
			return getRepoFactory().getStoreRepo().searchStockTaking(from, to,
					site, status);
		} finally {
			releaseTransaction();
		}
	}

	public BookReportBean getBookReport(String bookIsbn) {
		startTransaction();
		try {
			BookReportBean bean = new BookReportBean();
			if (StringUtils.isEmpty(bookIsbn))
				return bean;
			Book book = getRepoFactory().getProfileRepo().findBook(bookIsbn);
			if (null == book)
				throw ValidateException.noSuchBook(bookIsbn);

			bean.setBook(book);

			ListPrice listPrice = getRepoFactory().getOrderRepo().getListPrice(
					book);

			bean.setListPrice(listPrice.getPrice());

			if (book instanceof BookSet) {
				return bean;
			} else {

				// List<SupplyOrder> orders = getRepoFactory().getOrderRepo()
				// .getSupplyOrder(book);

				List<StoreSite> sites = getRepoFactory().getStoreRepo()
						.getStoreSiteWithBook(book);
				bean.setBookName(book.getName());

				// if (null != orders) {
				// BigDecimal sumAmount = BigDecimal.ZERO;
				// int count = 0;
				// for (SupplyOrder order : orders) {
				// if (SupplyOrder.Status.CONFIRM.ordinal() != order
				// .getStatus())
				// continue;
				// OrderItem item = order.getItem(book);
				// sumAmount = sumAmount.add(item.getUnitPrice().multiply(
				// new BigDecimal(item.getCount())));
				// count += item.getCount();
				// }
				// sumAmount = sumAmount.setScale(2);
				// bean.setAverageCost(0 == count ? BigDecimal.ZERO
				// : sumAmount.divide(new BigDecimal(count),
				// BigDecimal.ROUND_HALF_UP));
				// bean.setTotalCount(count);
				// }

				int totalCount = 0;
				BigDecimal subtotal = BigDecimal.ZERO;
				if (null != sites) {
					List<BookReportItemBean> itemBeans = new ArrayList<BookReportItemBean>();
					for (StoreSite site : sites) {
						StoreEntry entry = site.getEntry(book);
						BookReportItemBean item = new BookReportItemBean();
						item.setCount(entry.getCount());
						item.setAvailable(entry.getAvailable());
						item.setSiteName(site.getName());
						itemBeans.add(item);

						totalCount += item.getCount();
						subtotal = subtotal.add(entry.getUnitPrice().multiply(
								new BigDecimal(entry.getCount())));
					}
					bean.setItems(itemBeans);
				}
				if (totalCount == 0) {
					bean.setAverageCost(BigDecimal.ZERO);
				} else {
					bean.setAverageCost(subtotal.divide(new BigDecimal(
							totalCount), BigDecimal.ROUND_HALF_UP));
				}
				bean.setTotalCount(totalCount);

				return bean;
			}
		} finally {
			releaseTransaction();
		}
	}

	public Transfer placeTransfer(Transfer order) {
		startTransaction();
		Transfer orderToEdit = null;
		Validate.isTrue(!order.getFromSite().equals(order.getToSite()),
				"From Site and To Site should not be the same");
		if (order.getOid() == 0) {
			// New Order
			orderToEdit = new Transfer();
			orderToEdit.setNumber(RepoFactory.INSTANCE.getCommonRepo()
					.getNumber(CommonRepo.NUMBER_TYPE_TR));
			orderToEdit.setItems(order.getItems());
			orderToEdit.setFromSite(order.getFromSite());
			orderToEdit.setToSite(order.getToSite());

			orderToEdit = (Transfer) getRepoFactory().getCommonRepo().store(
					orderToEdit);
			orderToEdit.plan();
		} else {
			// Existing Order
			orderToEdit = RepoFactory.INSTANCE.getOrderRepo().readObject(
					Transfer.class, order.getOid());
			// Only Draft Order Could be edited;
			Validate.isTrue(Transfer.Status.DRAFT == orderToEdit.getStatus(),
					"Only Draft Order can be edited");
			orderToEdit.releaseStore();
			orderToEdit = (Transfer) getRepoFactory().getCommonRepo().store(
					order);
			orderToEdit.lockStore();
		}
		commitTransaction();
		return orderToEdit;
	}

	public Transfer operateTransfer(Transfer input, Transfer.Status newStatus) {
		startTransaction();
		try {
			Transfer trans = RepoFactory.INSTANCE.getOrderRepo().readObject(
					Transfer.class, input.getOid());
			switch (newStatus) {
			case CONFIRM:
				trans.confirm();
				break;
			case CANCEL:
				trans.cancel();
				break;
			default:
				throw new IllegalStateException(trans.getStatus().name() + ":"
						+ newStatus.name());
			}
			return trans;
		} finally {
			commitTransaction();
		}
	}

	public StockTaking saveStockTaking(StockTaking st) {
		startTransaction();
		try {
			StockTaking orderToEdit = null;
			if (st.getOid() == 0) {
				// New Order
				if (StringUtils.isEmpty(st.getNumber()))
					st.setNumber(RepoFactory.INSTANCE.getCommonRepo()
							.getNumber(CommonRepo.NUMBER_TYPE_ST));
				orderToEdit = (StockTaking) getRepoFactory().getCommonRepo()
						.store(st);
				orderToEdit.create();
			} else {
				// Existing Order
				orderToEdit = RepoFactory.INSTANCE.getOrderRepo().readObject(
						StockTaking.class, st.getOid());
				// Only Draft Order Could be edited;
				Validate.isTrue(
						StockTaking.Status.DRAFT.ordinal() == orderToEdit
								.getStatus(), "Only Draft Order can be edited");
				orderToEdit = (StockTaking) getRepoFactory().getCommonRepo()
						.store(st);
			}
			commitTransaction();
			return orderToEdit;
		} catch (Exception e) {
			releaseTransaction();
			throw new RuntimeException(e);
		}
	}

	public StockTaking operateStockTaking(StockTaking st, int to) {
		startTransaction();
		try {
			StockTaking trans = RepoFactory.INSTANCE.getOrderRepo().readObject(
					StockTaking.class, st.getOid());
			StockTaking.Status toStatus = StockTaking.Status.values()[to];
			switch (toStatus) {
			case CONFIRM:
				st.confirm();
				break;
			case CANCEL:
				trans.cancel();
				break;
			default:
				throw new IllegalStateException(
						StockTaking.Status.values()[st.getStatus()].name()
								+ ":" + toStatus.name());
			}
			return trans;
		} finally {
			commitTransaction();
		}
	}

	public BookReportBean getBookReports(List<Book> books) {
		startTransaction();

		try {
			BookReportBean bean = new BookReportBean();
			List<StoreSite> sites = getRepoFactory().getStoreRepo()
					.getAvailableStores(false);
			List<BookReportItemBean2> itemBeans = new ArrayList<BookReportItemBean2>();
			for (Book book : books) {
				if (book instanceof BookSet)
					continue;
				BookReportItemBean2 bb = new BookReportItemBean2();
				bb.setBook(book);
				BookReportBean brb = getBookReport(book.getIsbn());
				bb.setCost(brb.getAverageCost());
				bb.setCount(brb.getTotalCount());
				itemBeans.add(bb);
			}
			bean.setItem2s(new HeapSorter().sort(itemBeans,
					new Comparator<BookReportItemBean2>() {

						@Override
						public int compare(BookReportItemBean2 o2,
								BookReportItemBean2 o1) {
							return ((Integer) o1.getCount()).compareTo(o2
									.getCount());
						}

					}));

			return bean;
		} finally {
			releaseTransaction();
		}
	}
}
