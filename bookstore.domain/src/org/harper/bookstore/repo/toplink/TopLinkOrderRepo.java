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
import org.harper.bookstore.domain.order.DisplayItem;
import org.harper.bookstore.domain.order.ListPrice;
import org.harper.bookstore.domain.order.Order;
import org.harper.bookstore.domain.order.OrderItem;
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
			Date stop, int[] status, int[] expStatus, String partyId, String pws) {
		Validate.notNull(type);
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

		// Search Remarks, Items and Contacts
		if (!StringUtils.isEmpty(pws)) {
			Expression pwsexp = null;
			// Search all items and display items
			ExpressionBuilder itemBuilder = new ExpressionBuilder();
			Expression itemExp = itemBuilder
					.get("order")
					.equal(builder)
					.and(itemBuilder.get("book").get("name")
							.containsSubstringIgnoringCase(pws));
			ReportQuery itemQuery = new ReportQuery(OrderItem.class,
					itemBuilder);
			itemQuery.setSelectionCriteria(itemExp);
			itemQuery.addAttribute("oid");

			ExpressionBuilder ditemBuilder = new ExpressionBuilder();
			Expression ditemExp = ditemBuilder
					.get("order")
					.equal(builder)
					.and(ditemBuilder.get("name")
							.containsSubstringIgnoringCase(pws));
			ReportQuery ditemQuery = new ReportQuery(DisplayItem.class,
					ditemBuilder);
			ditemQuery.setSelectionCriteria(ditemExp);
			ditemQuery.addAttribute("oid");

			if ("PO".equals(type)) {
				pwsexp = builder.exists(itemQuery).or(
						builder.exists(ditemQuery));
			} else {
				pwsexp = builder.exists(itemQuery);
			}

			// Search Remarks and memos
			pwsexp = pwsexp.or(builder.get("remark")
					.containsSubstringIgnoringCase(pws));
			pwsexp = pwsexp.or(builder.get("memo")
					.containsSubstringIgnoringCase(pws));
			pwsexp = pwsexp.or(builder.get("contact").get("name")
					.containsSubstringIgnoringCase(pws));
			pwsexp = pwsexp.or(builder.get("contact").get("address")
					.containsSubstringIgnoringCase(pws));

			exp = exp.and(pwsexp);
		}
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
			String poNumber, String consigneeName, String poCustomerId,
			DeliveryOrder.Status status) {
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
		if (null != status) {
			exp = exp.and(builder.get("status").equal(status.ordinal()));
		}
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
		raq.addOrdering(builder.get("createDate").descending());

		return (List<DeliveryOrder>) TransactionContext.getSession()
				.executeQuery(raq);
	}
}
