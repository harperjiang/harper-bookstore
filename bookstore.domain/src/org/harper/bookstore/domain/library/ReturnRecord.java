package org.harper.bookstore.domain.library;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.harper.bookstore.repo.RepoFactory;

public class ReturnRecord extends Record {
	public static enum Status {
		NEW, COMPLETE;
	};

	public boolean isMatched() {
		for (RecordItem item : getItems()) {
			if (item.isOutstanding())
				return false;
		}
		return true;
	}

	public void proceed() {

		Map<String, RecordItem> changes = new HashMap<String, RecordItem>();
		for (RecordItem item : getItems()) {
			item.setOutstanding(item.getCount());
			changes.put(item.getBook().getIsbn(), item);
		}
		List<BorrowRecord> existed = RepoFactory.INSTANCE.getLibraryRepo()
				.getOutstandingRecords(getBorrower());

		for (BorrowRecord br : existed)
			if (!isMatched())
				br.balance(this);
	}

	public void sweep() {
		for (RecordItem item : getItems()) {
			if (item.isOutstanding()) {
				LibraryEntry entry = RepoFactory.INSTANCE.getLibraryRepo()
						.getEntry(item.getBook());
				entry.ret(item.getOutstanding());
				getSite().putInto(item.getBook(), item.getCount(),
						BigDecimal.ZERO);
			}
		}
	}

	public void forceClose(String reason) {
		// A return record should not be force closed;
		throw new UnsupportedOperationException();
	}
}
