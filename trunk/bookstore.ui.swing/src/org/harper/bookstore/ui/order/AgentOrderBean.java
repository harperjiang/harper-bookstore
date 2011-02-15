package org.harper.bookstore.ui.order;

import java.util.List;

import org.harper.bookstore.domain.order.OrderItem;
import org.harper.bookstore.domain.profile.Source;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class AgentOrderBean extends AbstractBean {

	private String customerSource = Source.TAOBAO.name();

	private String customerId;

	private String supplierSource = Source.TAOBAO.name();

	private String supplierId;

	private List<OrderItem> items;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}

	public String getSupplierSource() {
		return supplierSource;
	}

	public void setSupplierSource(String supplierSource) {
		this.supplierSource = supplierSource;
	}

}
