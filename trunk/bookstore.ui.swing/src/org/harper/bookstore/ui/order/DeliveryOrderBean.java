package org.harper.bookstore.ui.order;

import org.harper.bookstore.domain.deliver.DeliveryOrder;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class DeliveryOrderBean extends AbstractBean {
	private DeliveryOrder delivery;

	private String poNumber;
	
	public DeliveryOrderBean() {
		super();
		delivery = new DeliveryOrder();
	}

	public DeliveryOrder getDelivery() {
		return delivery;
	}

	public void setDelivery(DeliveryOrder delivery) {
		this.delivery = delivery;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

}
