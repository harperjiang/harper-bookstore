package org.harper.bookstore.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;
import org.harper.bookstore.domain.library.BorrowRecord;
import org.harper.bookstore.domain.library.Record;
import org.harper.bookstore.domain.library.RecordItem;
import org.harper.bookstore.domain.library.ReturnRecord;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.Borrower;
import org.harper.bookstore.service.bean.LibraryReportBean;

public class LibraryService extends Service {

	public Borrower findOrCreateBorrower(Borrower br) {
		startTransaction();
		try {
			Borrower ebr = null;
			if (br.getOid() > 0)
				ebr = getRepoFactory().getCommonRepo().readObject(
						Borrower.class, br.getOid());
			if (null == ebr) {
				ebr = getRepoFactory().getLibraryRepo().findBorrower(
						br.getName());
			}
			if (null == ebr)
				ebr = getRepoFactory().getCommonRepo().store(br);
			return ebr;
		} finally {
			releaseTransaction();
		}
	}

	public List<Borrower> findBorrowers(String name, String company) {
		startTransaction();
		try {
			return getRepoFactory().getLibraryRepo().findBorrowers(name,
					company);
		} finally {
			releaseTransaction();
		}
	}

	public Record borrowBook(Borrower borrower, Record record) {

		Validate.isTrue(
				BorrowRecord.Status.NEW.ordinal() == record.getStatus(),
				"You cannot modify or save this record as it had been saved.");

		try {
			startTransaction();
			Borrower bor = findOrCreateBorrower(borrower);
			BorrowRecord br = bor.borrow();
			br.setAccountDate(record.getAccountDate());
			br.setRemark(record.getRemark());
			br.setSite(record.getSite());
			br.setStatus(Record.Status.OST.ordinal());
			br.setItems(record.getItems());
			br.proceed();
			BorrowRecord ret = getRepoFactory().getCommonRepo().store(br);
			commitTransaction();
			return ret;
		} catch (Exception e) {
			releaseTransaction();
			throw new RuntimeException(e);
			
		}
	}

	public ReturnRecord returnBook(Borrower borrower, Record rec) {
		try {
			startTransaction();

			ReturnRecord ret = borrower.recordReturn();
			ret.setAccountDate(rec.getAccountDate());
			ret.setRemark(rec.getRemark());
			ret.setSite(rec.getSite());
			ret.setStatus(ReturnRecord.Status.COMPLETE.ordinal());
			ret.setItems(rec.getItems());


			ret.proceed();
			// Still Book exists
			ret.sweep();
			ReturnRecord recd = getRepoFactory().getCommonRepo().store(ret);
			commitTransaction();
			return recd;
		} catch (Exception e) {
			releaseTransaction();
			throw new RuntimeException(e);
			
		}
	}

	public List<LibraryReportBean> getLibraryReport(String book,
			String borrowerInfo) {
		startTransaction();
		try {
			List<RecordItem> items = getRepoFactory().getLibraryRepo()
					.findOstRecord(book, borrowerInfo);
			Map<Book, Map<Borrower, Integer>> res = new HashMap<Book, Map<Borrower, Integer>>();

			for (RecordItem item : items) {
				if (res.containsKey(item.getBook())) {
					Map<Borrower, Integer> ibt = res.get(item.getBook());
					Borrower br = item.getHeader().getBorrower();
					if (ibt.containsKey(br)) {
						Integer val = ibt.get(br);
						ibt.put(br, val + item.getOutstanding());
					} else {
						ibt.put(br, item.getOutstanding());
					}
				} else {
					Map<Borrower, Integer> newIbt = new HashMap<Borrower, Integer>();
					res.put(item.getBook(), newIbt);
					newIbt.put(item.getHeader().getBorrower(), item
							.getOutstanding());
				}
			}

			List<LibraryReportBean> beans = new ArrayList<LibraryReportBean>();

			for (Entry<Book, Map<Borrower, Integer>> entry : res.entrySet()) {

				for (Entry<Borrower, Integer> ine : entry.getValue().entrySet()) {
					LibraryReportBean lrb = new LibraryReportBean();
					lrb.setBook(entry.getKey());
					lrb.setBorrower(ine.getKey());
					lrb.setOutstanding(ine.getValue());
					beans.add(lrb);
				}
			}

			return beans;
		} finally {
			releaseTransaction();
		}
	}
}
