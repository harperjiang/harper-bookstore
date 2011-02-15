package org.harper.bookstore.domain.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import oracle.toplink.indirection.ValueHolder;
import oracle.toplink.indirection.ValueHolderInterface;

import org.apache.commons.lang.Validate;
import org.harper.bookstore.domain.deliver.DeliveryOrder;
import org.harper.bookstore.domain.profile.BookUnit;
import org.harper.bookstore.domain.profile.Customer;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.repo.RepoFactory;
import org.harper.bookstore.repo.StoreRepo;
import org.harper.frm.ValidateException;
import org.springframework.util.CollectionUtils;

public class PurchaseOrder extends Order {

	public static enum Status {
		NEW, DRAFT, CONFIRM, FINISH, CANCEL;
	}

	private Date payDate;

	private Date finishDate;

	private Customer customer;

	// External Status
	private String refStatus;

	// External Item
	private List<DisplayItem> dispItems;

	public static enum DeliveryStatus {
		NOT_SENT, FULLY_SENT, PARTIAL_SENT
	}

	private int deliveryStatus;

	private ValueHolderInterface delivery;

	private List<DeliveryOrder> deliveryOrders;

	public PurchaseOrder() {
		super();
		customer = new Customer();
		dispItems = new ArrayList<DisplayItem>();
		
		DeliveryOrder infoDo = new DeliveryOrder();
		infoDo.setValid(false);
		delivery = new ValueHolder(infoDo);
	}

	public void addDispItem(DisplayItem item) {
		this.dispItems.add(item);
		item.setOrder(this);
	}

	public void removeDispItem(DisplayItem item) {
		this.dispItems.remove(item);
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getRefStatus() {
		return refStatus;
	}

	public void setRefStatus(String refStatus) {
		this.refStatus = refStatus;
	}

	public Customer getParty() {
		return customer;
	}

	public Status getOrderStatus() {
		return Status.values()[super.getStatus()];
	}

	public String getOrderExpressStatus() {
		ResourceBundle rb = ResourceBundle
				.getBundle("org.harper.bookstore.domain.order.DeliveryStatus");
		return rb
				.getString(DeliveryStatus.values()[getDeliveryStatus()].name());
	}

	public CollectPlan getCollectPlan() {
		return null;
	}

	public void place() {
		Validate.isTrue(getOrderStatus() == Status.NEW);
		setStatus(Status.DRAFT.ordinal());
		lockStorage();
	}

	public void confirm() {
		Validate.isTrue(getOrderStatus() == Status.DRAFT);
		setStatus(Status.CONFIRM.ordinal());
		collectStorage();
	}

	public void cancel() {
		int oldStatus = getStatus();
		setStatus(Status.CANCEL.ordinal());
		if (oldStatus == Status.DRAFT.ordinal()) {
			releaseStorage();
		}
		if (oldStatus == Status.CONFIRM.ordinal()) {
			returnStorage();
		}
	}

	public void lockStorage() {

		// Retrieve Item from StoreSite
		for (OrderItem item : getItems()) {
			if (item.isAgent())
				// Agent Item is retrieved from others rather than own store, no
				// need to lock
				continue;
			StoreSite site = getSite();
			for (BookUnit book : item.getBook().getContent()) {
				int remains = item.getCount() * book.getCount();
				remains -= site.lock(book.getBook(), remains);
				if (remains > 0)
					throw ValidateException.notEnoughBook(book.getBook(), item
							.getCount()
							- remains);
			}
		}
	}

	public void collectStorage() {
		StoreRepo storeRepo = RepoFactory.INSTANCE.getStoreRepo();

		// Retrieve Item from StoreSite
		for (OrderItem item : getItems()) {
			if (item.isAgent())
				continue;
			BigDecimal sum = BigDecimal.ZERO;
			StoreSite site = getSite();
			for (BookUnit book : item.getBook().getContent()) {

				int remains = item.getCount() * book.getCount();
				BookUnit unit = site.retrieve(book.getBook(), remains);
				if (null != unit) {
					remains -= unit.getCount();
					sum = sum.add(unit.getSum());
				}
				if (remains > 0)
					throw ValidateException.notEnoughBook(book.getBook(), item
							.getCount()
							- remains);

			}
			if (0 != item.getCount())
				item.setUnitCost(sum.divide(new BigDecimal(item.getCount()),
						BigDecimal.ROUND_HALF_UP));
		}
	}

	public void releaseStorage() {
		StoreRepo storeRepo = RepoFactory.INSTANCE.getStoreRepo();

		// Retrieve Item from StoreSite
		for (OrderItem item : getItems()) {
			if (item.isAgent())
				continue;
			StoreSite site = getSite();
			for (BookUnit book : item.getBook().getContent()) {

				int remains = item.getCount() * book.getCount();
				remains -= site.cancel(book.getBook(), remains);
				if (remains > 0)
					throw ValidateException.notEnoughBook(book.getBook(), item
							.getCount()
							- remains);
			}
		}
	}

	public void returnStorage() {
		// Retrieve Item from StoreSite
		for (OrderItem item : getItems()) {
			if (item.isAgent())
				continue;
			BigDecimal[] splitPrice = CalcHelper.split(item.getUnitCost(), item
					.getBook().getContent());
			for (int i = 0; i < item.getBook().getContent().size(); i++) {
				BookUnit book = item.getBook().getContent().get(i);
				StoreSite site = getSite();
				site.putInto(book.getBook(), item.getCount() * book.getCount(),
						splitPrice[i]);
			}
		}
	}

	public void removeAllDispItems() {
		dispItems.clear();
	}

	public List<DisplayItem> getDispItems() {
		return dispItems;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public DeliveryOrder getDelivery() {
		if(null == delivery.getValue()) 
			delivery.setValue(new DeliveryOrder());
		return (DeliveryOrder)delivery.getValue();
	}

	public void setDelivery(DeliveryOrder delivery) {
		this.delivery.setValue(delivery);
	}

	public List<DeliveryOrder> getDeliveryOrders() {
		return deliveryOrders;
	}

	public void setDeliveryOrders(List<DeliveryOrder> deliveryOrders) {
		this.deliveryOrders = deliveryOrders;
	}

	public int getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(int deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getDeliveryNumber() {
		String doNumber = null == getDelivery() ? "" : getDelivery()
				.getNumber();
		if (null != getDeliveryOrders() && getDeliveryOrders().size() > 1)
			doNumber += "+";
		return doNumber;
	}

	/**
	 * Make a quick, full delivery order for this PO
	 */
	public void quickDeliver(DeliveryOrder dorder) {

	}

	public void partialDeliver() {

	}

	public void makeDelivery() {
		Validate.isTrue(null != getDelivery()
				|| !CollectionUtils.isEmpty(getDeliveryOrders()));
	}
}
