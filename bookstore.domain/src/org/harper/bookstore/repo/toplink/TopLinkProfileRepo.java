package org.harper.bookstore.repo.toplink;

import java.util.List;

import oracle.toplink.expressions.Expression;
import oracle.toplink.expressions.ExpressionBuilder;
import oracle.toplink.queryframework.ReadAllQuery;
import oracle.toplink.sessions.UnitOfWork;

import org.apache.commons.lang.StringUtils;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.Customer;
import org.harper.bookstore.domain.profile.Supplier;
import org.harper.bookstore.repo.ProfileRepo;
import org.harper.bookstore.service.TransactionContext;
import org.harper.frm.toplink.SessionManager;

public class TopLinkProfileRepo extends TopLinkRepo implements ProfileRepo {

	@Override
	public Book findBook(String isbn) {
		return (Book) TransactionContext.getSession().readObject(
				Book.class,
				new ExpressionBuilder().get("isbn").equal(isbn.trim()));
	}

	@Override
	public Customer getCustomer(String source, String customerId) {
		ExpressionBuilder builder = new ExpressionBuilder();
		return (Customer) SessionManager.getInstance().getSession().readObject(
				Customer.class,
				builder.get("source").equal(source).and(
						builder.get("id").equal(customerId)));
	}

	@Override
	public Supplier getSupplier(String source, String customerId) {
		ExpressionBuilder builder = new ExpressionBuilder();
		return (Supplier) TransactionContext.getSession().readObject(
				Supplier.class,
				builder.get("source").equal(source).and(
						builder.get("id").equal(customerId)));
	}

	@Override
	public List<Book> getBooks() {
		ReadAllQuery raq = new ReadAllQuery(Book.class);
		raq.addOrdering(new ExpressionBuilder().get("isbn"));
		UnitOfWork uow = SessionManager.getInstance().getSession()
				.acquireUnitOfWork();
		try {
			return (List<Book>) uow.executeQuery(raq);
		} finally {
			uow.release();
		}
	}

	@Override
	public List<Book> findBooks(String isbnOrName) {
		ExpressionBuilder builder = new ExpressionBuilder();
		Expression exp = builder.get("isbn").like(isbnOrName + "%").or(
				builder.get("name").toLowerCase().like(
						"%" + isbnOrName.toLowerCase() + "%"));
		ReadAllQuery raq = new ReadAllQuery(Book.class, exp);
		raq.addOrdering(builder.get("isbn"));
		return (List<Book>) TransactionContext.getSession().executeQuery(raq);
	}

	@Override
	public List<Book> powerFindBooks(String isbnOrName) {
		if (StringUtils.isEmpty(isbnOrName))
			return (List<Book>) TransactionContext.getSession().readAllObjects(
					Book.class);
		ExpressionBuilder builder = new ExpressionBuilder();
		Expression exp = builder;
		String[] inputs = isbnOrName.split("\\s");
		for (String part : inputs) {
			if (part.startsWith("\"") && part.endsWith("\"")) {
				exp = exp.and(builder.get("name").like(
						"%" + part.substring(1, part.length() - 1) + "%"));
			} else if (part.startsWith("-")) {
				exp = exp.and(builder.get("name").notLike(
						"%" + part.substring(1) + "%"));
			} else {
				exp = exp.and(builder.get("name").like("%" + part + "%"));
			}
		}
		ReadAllQuery raq = new ReadAllQuery(Book.class, exp);
		return (List<Book>) TransactionContext.getSession().executeQuery(raq);
	}

}
