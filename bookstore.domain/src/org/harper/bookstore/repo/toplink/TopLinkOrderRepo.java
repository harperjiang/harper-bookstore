package org.harper.bookstore.repo.toplink;

import java.util.Date;
import java.util.List;

import oracle.toplink.expressions.Expression;
import oracle.toplink.expressions.ExpressionBuilder;
import oracle.toplink.queryframework.ReadAllQuery;
import oracle.toplink.queryframework.ReportQuery;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.harper.bookstore.domain.deliver.DeliveryOrder;
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
			Date stop, int[] status, int[] expStatus, String partyId) {
		Validate.notNull(type);
		Validate.notNull(status);
		ExpressionBuilder builder = new ExpressionBuilder();
		Expression exp = builder;
		if (null != number) {
			if (number.contains(";"))
				exp = exp.and(builder.get("number").in(number.split(";"))
						.or(builder.get("refno").in(number.split(";"))));
			else
				exp = exp.and(builder.get("number")
						.likeIgnoreCase("%" + number + "%")
						.or(builder.get("refno").equal(number)));
		}
		if (null != status)
			exp = exp.and(builder.get("status").in(status));
		if (null != expStatus)
			exp = exp.and(builder.get("deliveryStatus").in(expStatus));

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
		Expression exp = builder
				.get("refno")
				.notNull()
				.and(builder.get("status").equal(
						PurchaseOrder.Status.DRAFT.ordinal()));
		return getSession().readAllObjects(PurchaseOrder.class, exp);
	}

	@Override
	public List<DeliveryOrder> searchDeliveryOrder(Date fromDate, Date toDate,
			String poNumber, String consigneeName, String poCustomerId) {
		ExpressionBuilder builder = new ExpressionBuilder();
		Expression exp = builder;

		exp = builder.get("valid").equal(true);
		if (null != fromDate)
			exp = exp.and(builder.get("createDate").greaterThanEqual(fromDate));
		if (null != toDate)
			exp = exp.and(builder.get("createDate").lessThanEqual(toDate));
		if (!StringUtils.isEmpty(consigneeName))
			exp = exp.and(builder.get("contact").get("name")
					.containsSubstring(consigneeName));

		ExpressionBuilder poBuilder = new ExpressionBuilder();
		Expression poExp = poBuilder;
		if (!StringUtils.isEmpty(poNumber))
			poExp = poExp.and(poBuilder.get("number").equal(poNumber));
		if (!StringUtils.isEmpty(poCustomerId))
			poExp = poExp.and(poBuilder.get("customer").get("id")
					.equalsIgnoreCase(poCustomerId));
		if (poExp != poBuilder) {

			poExp = poExp.and(poBuilder.anyOf("deliveryOrders").equal(builder));

			ReportQuery poRq = new ReportQuery(PurchaseOrder.class, poBuilder);
			poRq.addAttribute("oid");
			poRq.setSelectionCriteria(poExp);
			exp = exp.and(builder.exists(poRq));
		}

		ReadAllQuery raq = new ReadAllQuery(DeliveryOrder.class, exp);
		raq.addOrdering(builder.get("createDate"));

		return (List<DeliveryOrder>) TransactionContext.getSession()
				.executeQuery(raq);
	}
}
