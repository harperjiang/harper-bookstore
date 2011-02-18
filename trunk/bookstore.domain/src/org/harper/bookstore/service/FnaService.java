package org.harper.bookstore.service;

import org.harper.bookstore.domain.finance.Accounting;

public class FnaService extends Service {

	public void addAccountingRecord(Accounting record) {
		startTransaction();
		try {
			getRepoFactory().getCommonRepo().store(record);
			commitTransaction();
		} catch (Exception e) {
			releaseTransaction();
			throw new RuntimeException(e);
		}
	}
}
