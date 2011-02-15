package org.harper.bookstore.repo.toplink;

import java.util.List;

import oracle.toplink.expressions.Expression;
import oracle.toplink.expressions.ExpressionBuilder;
import oracle.toplink.queryframework.ReadAllQuery;

import org.apache.commons.lang.StringUtils;
import org.harper.bookstore.domain.library.BorrowRecord;
import org.harper.bookstore.domain.library.LibraryEntry;
import org.harper.bookstore.domain.library.Record;
import org.harper.bookstore.domain.library.RecordItem;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.Borrower;
import org.harper.bookstore.repo.LibraryRepo;
import org.harper.bookstore.repo.RepoFactory;

public class TopLinkLibraryRepo extends TopLinkRepo implements LibraryRepo {

	@Override
	public LibraryEntry getEntry(Book book) {
		LibraryEntry entry = (LibraryEntry) getSession().readObject(
				LibraryEntry.class,
				new ExpressionBuilder().get("book").equal(book));
		if (null == entry) {
			entry = new LibraryEntry();
			entry.setBook(book);
			entry = RepoFactory.INSTANCE.getCommonRepo().store(entry);
		}
		return entry;
	}

	@Override
	public List<RecordItem> getItems(Borrower borrower, Book book) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public List<BorrowRecord> getOutstandingRecords(Borrower borrower) {
		ExpressionBuilder builder = new ExpressionBuilder();
		Expression exp = builder.get("borrower").equal(borrower).and(
				builder.get("status").in(
						new int[] { Record.Status.OST.ordinal(),
								Record.Status.PARTIAL_OST.ordinal() }));
		ReadAllQuery raq = new ReadAllQuery(BorrowRecord.class, exp);
		raq.addOrdering(builder.get("accountDate"));
		return (List<BorrowRecord>) getSession().executeQuery(raq);
	}

	@Override
	public Borrower findBorrower(String id) {
		return (Borrower) getSession().readObject(Borrower.class,
				new ExpressionBuilder().get("name").equal(id));
	}

	@Override
	public List<Borrower> findBorrowers(String name, String company) {
		ExpressionBuilder builder = new ExpressionBuilder();
		Expression exp = builder;
		if (!StringUtils.isEmpty(name))
			exp = exp.and(builder.get("name").like("%" + name + "%"));
		if (!StringUtils.isEmpty(company))
			exp = exp.and(builder.get("company").like("%" + company + "%"));
		return (List<Borrower>) getSession().executeQuery(
				new ReadAllQuery(Borrower.class, exp));
	}

	@Override
	public List<LibraryEntry> findEntries(String book) {
		ExpressionBuilder builder = new ExpressionBuilder();
		Expression exp = StringUtils.isEmpty(book) ? builder : builder.get(
				"book").get("name").like("%" + book + "%").or(
				builder.get("book").get("isbn").like(book + "%"));
		return (List<LibraryEntry>) getSession().executeQuery(
				new ReadAllQuery(LibraryEntry.class, exp));
	}

	@Override
	public List<RecordItem> findOstRecord(String book, String borrower) {
		ExpressionBuilder builder = new ExpressionBuilder();
		Expression exp = StringUtils.isEmpty(book) ? builder : builder.get(
				"book").get("name").like("%" + book + "%").or(
				builder.get("book").get("isbn").like(book + "%"));
		if (!StringUtils.isEmpty(borrower)) {
			exp = exp
					.and(builder.get("header").get("borrower").get("name")
							.like("%" + borrower + "%").or(
									builder.get("header").get("borrower").get(
											"company").like(
											"%" + borrower + "%")));
		}
		exp = exp.and(
				builder.get("status").in(
						new int[] { RecordItem.Status.OST.ordinal(),
								RecordItem.Status.PARTIAL_OST.ordinal() }))
				.and(builder.get("header").getField("type").equal("B"));

		return (List<RecordItem>) getSession().executeQuery(
				new ReadAllQuery(RecordItem.class, exp));
	}
}
