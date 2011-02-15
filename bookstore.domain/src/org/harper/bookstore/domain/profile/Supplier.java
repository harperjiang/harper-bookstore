package org.harper.bookstore.domain.profile;

import org.harper.bookstore.domain.order.SupplyOrder;
import org.harper.bookstore.repo.CommonRepo;
import org.harper.bookstore.repo.RepoFactory;

public class Supplier extends Counterpart {

	public SupplyOrder createOrder(SupplyOrder info) {
		SupplyOrder order = null == info ? new SupplyOrder() : info;
		order.setNumber(RepoFactory.INSTANCE.getCommonRepo().getNumber(
				CommonRepo.NUMBER_TYPE_SO));
		order.setSupplier(this);

		return order;
	}
}
