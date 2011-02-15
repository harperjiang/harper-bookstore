package org.harper.bookstore.domain.profile;

import java.util.ResourceBundle;

import org.harper.bookstore.domain.order.PurchaseOrder;
import org.harper.bookstore.repo.CommonRepo;
import org.harper.bookstore.repo.RepoFactory;

public class Customer extends Counterpart {

	public PurchaseOrder placeOrder(PurchaseOrder info) {
		PurchaseOrder order = info == null ? new PurchaseOrder() : info;
		order.setCustomer(this);
		order.setNumber(RepoFactory.INSTANCE.getCommonRepo().getNumber(
				CommonRepo.NUMBER_TYPE_PO));
		return order;
	}

	public static String[] getSources() {
		ResourceBundle res = ResourceBundle
				.getBundle("org.harper.bookstore.domain.profile.cust_source");
		String[] result = new String[4];
		for (int i = 0; i < 4; i++)
			result[i] = res.getString(Source.values()[i].name());
		return result;
	}
}
