package org.harper.bookstore.domain.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.harper.bookstore.repo.RepoFactory;

public class BorrowRecord extends Record {

	public void proceed() {
		setStatus(Status.OST.ordinal());
		for (RecordItem item : getItems()) {
			LibraryEntry entry = RepoFactory.INSTANCE.getLibraryRepo()
					.getEntry(item.getBook());
			if (null == entry) {
				entry = RepoFactory.INSTANCE.getCommonRepo().store(
						new LibraryEntry());
			}
			entry.borrow(item.getCount());
			item.setOutstanding(item.getCount());
			getSite().lock(item.getBook(), item.getCount());
		}
	}

	public void balance(ReturnRecord ret) {
		Map<String, RecordItem> osts = new HashMap<String, RecordItem>();
		for (RecordItem item : getItems()) {
			if (item.isOutstanding())
				osts.put(item.getBook().getIsbn(), item);
		}
		List<RecordItem> newItems = new ArrayList<RecordItem>();
		for (RecordItem balanceItem : ret.getItems()) {
			if (balanceItem.getOutstanding() == 0)
				continue;
			RecordItem ostItem = osts.get(balanceItem.getBook().getIsbn());
			if (null == ostItem)
				continue;
			LibraryEntry entry = RepoFactory.INSTANCE.getLibraryRepo()
					.getEntry(ostItem.getBook());

			int balance = Math.min(balanceItem.getOutstanding(), ostItem
					.getOutstanding());

			entry.ret(balance);
			getSite().cancel(balanceItem.getBook(), balance);
			ostItem.setOutstanding(ostItem.getOutstanding() - balance);

			if (balanceItem.getOutstanding() > balance) {
				RecordItem newItem = new RecordItem();
				newItem.setCount(balanceItem.getOutstanding() - balance);
				newItem.setOutstanding(newItem.getCount());
				newItems.add(newItem);
				balanceItem.setCount(balance);
				balanceItem.setOutstanding(balance);
			} else {
				balanceItem.setOutstanding(0);
			}
			balanceItem.setBalanceTo(ostItem);
		}
		updateStatus();
		if (getStatus() == Status.MATCH_CLOSE.ordinal())
			setCloseDate(ret.getAccountDate());
		for (RecordItem newItem : newItems) {
			ret.addItem(newItem);
		}
	}

	public void forceClose(String reason) {
		super.forceClose(reason);

		for (RecordItem item : getItems()) {
			if (RecordItem.Status.ERROR.ordinal() == item.getStatus()) {
				LibraryEntry entry = RepoFactory.INSTANCE.getLibraryRepo()
						.getEntry(item.getBook());
				entry.lose(item.getOutstanding());
				getSite().retrieve(item.getBook(), item.getCount());
			}
		}
	}
}
