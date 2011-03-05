package org.harper.bookstore.service;

import org.harper.bookstore.domain.finance.AccountEntry;

public class FnaService extends Service {

	public void addAccountingRecord(AccountEntry record) {
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
