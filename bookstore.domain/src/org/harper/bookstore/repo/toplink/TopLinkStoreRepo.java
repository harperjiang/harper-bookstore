package org.harper.bookstore.repo.toplink;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import oracle.toplink.expressions.Expression;
import oracle.toplink.expressions.ExpressionBuilder;
import oracle.toplink.expressions.ExpressionMath;
import oracle.toplink.queryframework.ReadAllQuery;
import oracle.toplink.queryframework.ReportQuery;
import oracle.toplink.queryframework.ReportQueryResult;
import oracle.toplink.queryframework.SQLCall;
import oracle.toplink.sessions.Session;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.store.StockTaking;
import org.harper.bookstore.domain.store.StockTaking.Status;
import org.harper.bookstore.domain.store.StoreEntry;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.domain.store.Transfer;
import org.harper.bookstore.repo.StoreRepo;
import org.harper.bookstore.service.TransactionContext;
import org.harper.frm.toplink.SessionManager;

public class TopLinkStoreRepo extends TopLinkRepo implements StoreRepo {

	@Override
	public int checkBookExistance(Book book) {
		ExpressionBuilder builder = new ExpressionBuilder();
		ReportQuery entryReport = new ReportQuery(StoreEntry.class, builder);
		entryReport.setSelectionCriteria(builder.get("book").equal(book));
		entryReport.addAttribute(
				"sum",
				ExpressionMath.subtract(builder.get("count"),
						builder.get("lockedCount")).sum());

		List<ReportQueryResult> result = (List<ReportQueryResult>) SessionManager
				.getInstance().getSession().executeQuery(entryReport);

		BigDecimal count = (BigDecimal) result.get(0).get("sum");
		if (null == count)
			return 0;
		return count.intValue();
	}

	@Override
	public List<StoreSite> getStoreSiteWithBook(Book book) {
		Session uow = (Session) TransactionContext.getSession();
		if (null == uow)
			uow = SessionManager.getInstance().getSession();

		ReadAllQuery query = new ReadAllQuery(StoreSite.class);
		ExpressionBuilder builder = new ExpressionBuilder();
		query.setSelectionCriteria(builder.anyOf("entries").get("book")
				.equal(book));
		query.addOrdering(builder.get("privilege"));
		return (List<StoreSite>) uow.executeQuery(query);
	}

	@Override
	public List<StoreSite> getAvailableStores(boolean forSell) {
		ExpressionBuilder builder = new ExpressionBuilder();
		Expression exp = builder.get("valid").equal(true);
		if (forSell) {
			exp = exp.and(builder.get("forOutput").equal(true));
		}
		return (List<StoreSite>) TransactionContext.getSession()
				.readAllObjects(StoreSite.class, exp);
	}

	@Override
	public List<StoreSite> getAllStores() {
		return (List<StoreSite>) TransactionContext.getSession()
				.readAllObjects(StoreSite.class);
	}

	@Override
	public List<Transfer> searchTransfers() {

		ReadAllQuery raq = new ReadAllQuery(Transfer.class);
		return (List<Transfer>) getSession().executeQuery(raq);
	}

	@Override
	public StoreSite getDefaultOutputSite() {
		return (StoreSite) TransactionContext
				.getSession()
				.readObject(
						StoreSite.class,
						new SQLCall(
								"select * from store_site where for_output = 1 having pref_seq = min(pref_seq)"));
	}

	@Override
	public List<StockTaking> searchStockTaking(Date from, Date to,
			StoreSite site, Status status) {
		ExpressionBuilder builder = new ExpressionBuilder();
		Expression exp = builder;
		if (null != from)
			exp = exp.and(builder.get("createDate").greaterThanEqual(from));
		if (null != to)
			exp = exp.and(builder.get("createDate").lessThanEqual(to));
		if (null != site)
			exp = exp.and(builder.get("site").equal(site));
		if (null != status)
			exp = exp.and(builder.get("status").equal(status.ordinal()));
		ReadAllQuery raq = new ReadAllQuery();
		raq.setReferenceClass(StockTaking.class);
		raq.setSelectionCriteria(exp);
		raq.addOrdering(builder.get("createDate").descending());
		return (List) TransactionContext.getSession().executeQuery(raq);
	}

}
