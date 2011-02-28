package org.harper.bookstore.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.harper.bookstore.domain.deliver.DeliveryItem;
import org.harper.bookstore.domain.deliver.DeliveryOrder;
import org.harper.bookstore.domain.deliver.ExpressCompany;
import org.harper.bookstore.domain.order.ListPrice;
import org.harper.bookstore.domain.order.Order;
import org.harper.bookstore.domain.order.OrderItem;
import org.harper.bookstore.domain.order.PurchaseOrder;
import org.harper.bookstore.domain.order.PurchaseOrder.DeliveryStatus;
import org.harper.bookstore.domain.order.SupplyOrder;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.Customer;
import org.harper.bookstore.domain.profile.Supplier;
import org.harper.bookstore.repo.RepoFactory;
import org.springframework.util.CollectionUtils;

public class OrderService extends Service {

	public PurchaseOrder savePurchaseOrder(PurchaseOrder order) {
		try {
			startTransaction();
			Customer cust = getRepoFactory().getProfileRepo().getCustomer(
					order.getCustomer().getSource(),
					null == order.getCustomer().getId() ? null : order
							.getCustomer().getId().trim());
			if (null == cust) {
				cust = RepoFactory.INSTANCE.getCommonRepo().store(
						order.getCustomer());
			}

			PurchaseOrder orderToEdit = null;

			if (order.getOid() == 0) {
				// New Order
				orderToEdit = cust.placeOrder(order);
				orderToEdit = (PurchaseOrder) getRepoFactory().getCommonRepo()
						.store(orderToEdit);
				orderToEdit.place();
			} else {
				// Existing Order
				orderToEdit = RepoFactory.INSTANCE.getOrderRepo().readObject(
						PurchaseOrder.class, order.getOid());
				// Only Draft Order Could be edited;
				if (PurchaseOrder.Status.DRAFT == orderToEdit.getOrderStatus()) {
					// orderToEdit.releaseStorage();

					orderToEdit = (PurchaseOrder) getRepoFactory()
							.getCommonRepo().store(order);

					// orderToEdit.lockStorage();
				} else {
					// Allow Delivery Info to be updated when order is not sent
					if (orderToEdit.getDeliveryStatus() == DeliveryStatus.NOT_SENT
							.ordinal())
						orderToEdit
								.setDelivery((DeliveryOrder) getRepoFactory()
										.getCommonRepo().store(
												order.getDelivery()));
				}
			}

			commitTransaction();
			return orderToEdit;
		} catch (Exception e) {
			releaseTransaction();
			throw new RuntimeException(e);
		}
	}

	public PurchaseOrder operateOrder(PurchaseOrder order,
			PurchaseOrder.Status newStatus) {
		startTransaction();
		try {
			PurchaseOrder orderToEdit = RepoFactory.INSTANCE.getOrderRepo()
					.readObject(PurchaseOrder.class, order.getOid());
			switch (newStatus) {
			case CONFIRM:
				orderToEdit.confirm();
				break;
			case CANCEL:
				orderToEdit.cancel();
				break;
			default:
				throw new IllegalStateException(order.getOrderStatus().name()
						+ ":" + newStatus.name());
			}
			commitTransaction();
			return orderToEdit;
		} catch (Exception e) {
			releaseTransaction();
			throw new RuntimeException(e);
		}
	}

	public SupplyOrder saveSupplyOrder(SupplyOrder order) {
		Validate.notNull(order);
		Validate.notNull(order.getSite(), "Order has no Site selected");
		startTransaction();

		try {
			Supplier sp = getRepoFactory().getProfileRepo().getSupplier(
					order.getSupplier().getSource(),
					null == order.getSupplier().getId() ? null : order
							.getSupplier().getId().trim());
			if (null == sp) {
				sp = getRepoFactory().getCommonRepo()
						.store(order.getSupplier());
			}

			SupplyOrder orderToEdit = null;

			if (order.getOid() == 0) {
				// New Order
				orderToEdit = sp.createOrder(order);
				orderToEdit.create();
				orderToEdit = RepoFactory.INSTANCE.getCommonRepo().store(
						orderToEdit);
			} else {
				// Existing Order
				orderToEdit = RepoFactory.INSTANCE.getOrderRepo().readObject(
						SupplyOrder.class, order.getOid());
				// Only Draft Order Could be edited;
				Validate.isTrue(SupplyOrder.Status.DRAFT == orderToEdit
						.getOrderStatus(), "Only Draft Order can be edited");
				orderToEdit = RepoFactory.INSTANCE.getCommonRepo().store(order);
			}
			commitTransaction();
			return orderToEdit;
		} catch (Exception e) {
			releaseTransaction();
			throw new RuntimeException(e);
		}

	}

	public SupplyOrder operateOrder(SupplyOrder order,
			SupplyOrder.Status newStatus) {
		startTransaction();
		try {
			SupplyOrder orderToEdit = RepoFactory.INSTANCE.getOrderRepo()
					.readObject(SupplyOrder.class, order.getOid());
			switch (newStatus) {
			case CONFIRM:
				orderToEdit.confirm();
				break;
			default:
				throw new IllegalStateException(order.getOrderStatus().name()
						+ ":" + newStatus.name());
			}

			commitTransaction();
			return orderToEdit;

		} catch (Exception e) {
			releaseTransaction();
			throw new RuntimeException(e);
		}
	}

	public List<Order> searchOrder(String num, String type, Date start,
			Date stop, int[] status, int[] expStatus, String partyId, String pws) {
		startTransaction();
		try {
			return RepoFactory.INSTANCE.getOrderRepo().searchOrder(num, type,
					start, stop, status, expStatus, partyId, pws);
		} finally {
			releaseTransaction();
		}
	}

	public void placeAgentOrder(String custSource, String customer,
			String spSource, String supplier, List<OrderItem> items) {

		Validate.isTrue(!StringUtils.isEmpty(customer),
				"Please input Customer Id");
		Validate.isTrue(!StringUtils.isEmpty(supplier),
				"Please input Supplier Id");
		Validate.isTrue(items.size() > 0, "Please add Order Items");

		startTransaction();
		Customer cust = getRepoFactory().getProfileRepo().getCustomer(
				custSource, customer.trim());
		if (null == cust) {
			Customer acust = new Customer();
			acust.setId(customer.trim());
			acust.setSource(custSource);
			cust = RepoFactory.INSTANCE.getCommonRepo().store(acust);
		}

		Supplier sp = getRepoFactory().getProfileRepo().getSupplier(spSource,
				supplier.trim());
		if (null == sp) {
			Supplier asp = new Supplier();
			asp.setId(supplier.trim());
			asp.setSource(spSource);
			sp = RepoFactory.INSTANCE.getCommonRepo().store(asp);
		}

		SupplyOrder so = sp.createOrder(null);
		PurchaseOrder po = cust.placeOrder(null);
		List<OrderItem> soItems = new ArrayList<OrderItem>();
		List<OrderItem> poItems = new ArrayList<OrderItem>();
		for (OrderItem item : items) {
			OrderItem soItem = new OrderItem();
			OrderItem poItem = new OrderItem();
			soItem.setBook(item.getBook());
			poItem.setBook(item.getBook());
			soItem.setCount(item.getCount());
			poItem.setCount(item.getCount());
			soItem.setUnitPrice(item.getUnitCost());
			poItem.setUnitPrice(item.getUnitPrice());
			poItem.setUnitCost(item.getUnitCost());
			soItems.add(soItem);
			poItems.add(poItem);
		}
		so.setItems(soItems);
		po.setItems(poItems);

		so.setStatus(SupplyOrder.Status.CONFIRM.ordinal());
		po.setStatus(PurchaseOrder.Status.CONFIRM.ordinal());

		getRepoFactory().getCommonRepo().store(so);
		getRepoFactory().getCommonRepo().store(po);

		commitTransaction();
	}

	public BigDecimal getListPrice(Book book) {
		startTransaction();
		try {
			ListPrice lp = RepoFactory.INSTANCE.getOrderRepo().getListPrice(
					book);
			return lp == null ? null : lp.getPrice();
		} finally {
			releaseTransaction();
		}
	}

	public void updateListPrice(Book book, BigDecimal price) {
		startTransaction();

		ListPrice lp = RepoFactory.INSTANCE.getOrderRepo().getListPrice(book);
		if (null == lp) {
			lp = new ListPrice();
			lp.setBook(book);
		}
		lp.setPrice(price);
		getRepoFactory().getCommonRepo().store(lp);
		commitTransaction();
	}

	public PurchaseOrder sendOrder(PurchaseOrder order) {
		startTransaction();
		try {
			Validate.isTrue(
					order.getDeliveryStatus() == DeliveryStatus.NOT_SENT
							.ordinal(), "Order Already Sent");
			Validate.isTrue(
					order.getDelivery().getCompany() == ExpressCompany.NIL
							|| !StringUtils.isEmpty(order.getDeliveryNumber()),
					"Delivery Order Number should not be empty");
			order.quickDeliver();
			PurchaseOrder retval = getRepoFactory().getCommonRepo()
					.store(order);
			commitTransaction();
			return retval;
		} catch (Exception e) {
			releaseTransaction();
			throw new RuntimeException(e);
		}

	}

	public DeliveryOrder saveDeliveryOrder(DeliveryOrder order) {
		startTransaction();
		try {
			Validate.isTrue(
					order.getStatus() == DeliveryOrder.Status.NEW.ordinal()
							|| order.getStatus() == DeliveryOrder.Status.DRAFT
									.ordinal(),
					"Only Draft Order could be amended");
			if (order.getStatus() == DeliveryOrder.Status.NEW.ordinal())
				order.create();
			Validate.isTrue(!CollectionUtils.isEmpty(order.getItems()),
					"Please input the item to be sent");

			for (int i = 0; i < order.getItems().size(); i++) {
				DeliveryItem item = order.getItems().get(i);
				item.setHeader(order);
				// Remove the items that has 0 or negative count;
				if (0 > item.getCount()) {
					order.getItems().remove(i);
					i--;
					continue;
				}

				// Set Connection with PurchaseOrder
				if (null != item.getOrderItem()) {
					// item.getOrderItem().setSentCount(
					// item.getOrderItem().getSentCount()
					// + item.getCount());

					PurchaseOrder po = (PurchaseOrder) item.getOrderItem()
							.getOrder();
					if (!po.getDelivery().isValid())
						po.setDelivery(order);
					if (!po.getDeliveryOrders().contains(order))
						po.getDeliveryOrders().add(order);
					// po.makeDelivery();
				}
			}
			DeliveryOrder result = getRepoFactory().getCommonRepo()
					.store(order);
			commitTransaction();
			return result;
		} catch (Exception e) {
			releaseTransaction();
			throw new RuntimeException(e);
		}

	}

	public DeliveryOrder operateDelivery(DeliveryOrder order, int status) {
		startTransaction();
		try {
			switch (DeliveryOrder.Status.values()[status]) {
			case DELIVERED:
				order.deliver();
				break;
			case RETURNED:
				order.fallback();
				break;
			case EXCEPTION:
				break;
			default:
			}
			DeliveryOrder result = getRepoFactory().getCommonRepo()
					.store(order);
			commitTransaction();
			return result;
		} catch (Exception e) {
			releaseTransaction();
			throw new RuntimeException(e);
		}
	}

	public List<DeliveryOrder> searchDeliveryOrder(Date fromDate, Date toDate,
			String poNumber, String consigneeName, String poCustomerId,
			DeliveryOrder.Status status) {
		startTransaction();
		try {
			return getRepoFactory().getOrderRepo().searchDeliveryOrder(
					fromDate, toDate, poNumber, consigneeName, poCustomerId,
					status);
		} finally {
			releaseTransaction();
		}
	}
}
