package org.harper.bookstore.repo.toplink;

import java.util.Date;
import java.util.List;

import oracle.toplink.expressions.Expression;
import oracle.toplink.expressions.ExpressionBuilder;
import oracle.toplink.queryframework.ReadAllQuery;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.harper.bookstore.domain.order.ListPrice;
import org.harper.bookstore.domain.order.Order;
import org.harper.bookstore.domain.order.PurchaseOrder;
import org.harper.bookstore.domain.order.SupplyOrder;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.repo.OrderRepo;
import org.harper.bookstore.service.TransactionContext;
import org.harper.frm.toplink.SessionManager;

public class TopLinkOrderRepo extends TopLinkRepo implements OrderRepo {

	@Override
	public List<SupplyOrder> getSupplyOrder(Book book) {
		ExpressionBuilder builder = new ExpressionBuilder();
		Expression exp = builder.anyOf("items").get("book").equal(book);
		return (List<SupplyOrder>) SessionManager.getInstance().getSession()
				.readAllObjects(SupplyOrder.class, exp);
	}

	@Override
	public List<Order> searchOrder(String number, String type, Date start,
			Date stop, String status, String partyId) {
		Validate.notNull(type);
		Validate.notNull(status);
		ExpressionBuilder builder = new ExpressionBuilder();
		Expression exp = builder;
		if (null != number) {
			if (number.contains(";"))
				exp = exp.and(builder.get("number").in(number.split(";")).or(
						builder.get("refno").in(number.split(";"))));
			else
				exp = exp.and(builder.get("number").likeIgnoreCase(
						"%" + number + "%").or(
						builder.get("refno").equal(number)));
		}
		if (!"ALL".equals(status))
			exp = exp.and(builder.get("status").equal(
					Order.Status.valueOf(status).ordinal()));
		if (start != null)
			exp = exp.and(builder.get("createDate").greaterThanEqual(start));
		if (stop != null)
			exp = exp.and(builder.get("createDate").lessThanEqual(stop));

		ReadAllQuery query = null;
		if ("PO".equals(type)) {
			query = new ReadAllQuery(PurchaseOrder.class);
			if (!StringUtils.isEmpty(partyId))
				exp = exp.and(builder.get("customer").get("id").equal(partyId));
		}
		if ("SO".equals(type)) {
			query = new ReadAllQuery(SupplyOrder.class);
			if (!StringUtils.isEmpty(partyId))
				exp = exp.and(builder.get("supplier").get("id").equal(partyId));
		}
		query.setSelectionCriteria(exp);
		query.addOrdering(builder.get("createDate").descending());
		query.addOrdering(builder.get("number").descending());
		return (List<Order>) TransactionContext.getSession()
				.executeQuery(query);
	}

	@Override
	public PurchaseOrder getPurchaseOrderByRefno(String refno) {
		if (StringUtils.isEmpty(refno))
			return null;
		return (PurchaseOrder) TransactionContext.getSession().readObject(
				PurchaseOrder.class,
				new ExpressionBuilder().get("refno").equal(refno));
	}

	@Override
	public ListPrice getListPrice(Book book) {
		ListPrice price = (ListPrice) TransactionContext.getSession()
				.readObject(ListPrice.class,
						new ExpressionBuilder().get("book").equal(book));
		return price;
	}

	@Override
	public List<PurchaseOrder> getExternalOutstandingOrder() {
		ExpressionBuilder builder = new ExpressionBuilder();
		Expression exp = builder.get("refno").notNull().and(
				builder.get("status").equal(
						PurchaseOrder.Status.DRAFT.ordinal()));
		return getSession().readAllObjects(PurchaseOrder.class, exp);
	}
}
