package org.harper.bookstore.domain.order;

import java.math.BigDecimal;

import org.apache.commons.lang.Validate;
import org.harper.bookstore.domain.profile.BookUnit;
import org.harper.bookstore.domain.profile.Supplier;

public class SupplyOrder extends Order {

	public static enum Status {
		NEW, DRAFT, CONFIRM, CANCEL;
	}

	private Supplier supplier;

	public SupplyOrder() {
		super();
		supplier = new Supplier();
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Supplier getParty() {
		return supplier;
	}

	public Status getOrderStatus() {
		return Status.values()[super.getStatus()];
	}

	public void create() {
		setStatus(Status.DRAFT.ordinal());
		for (OrderItem item : getItems()) {
			item.setUnitCost(item.getUnitPrice());
		}
	}

	public void confirm() {
		Validate.isTrue(getOrderStatus() == Status.DRAFT);
		setStatus(Status.CONFIRM.ordinal());
		setTotalAmt(getTotal());
		for (OrderItem item : getItems()) {
			BigDecimal[] splitPrice = CalcHelper.split(item.getUnitPrice(),
					item.getBook().getContent());
			for (int i = 0; i < item.getBook().getContent().size(); i++) {
				BookUnit book = item.getBook().getContent().get(i);
				getSite().putInto(book.getBook(),
						item.getCount() * book.getCount(), splitPrice[i]);
			}
		}
	}

	public void cancel() {
		Validate.isTrue(getOrderStatus() == Status.DRAFT);
		setStatus(Status.CANCEL.ordinal());
	}
}
