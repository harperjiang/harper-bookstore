package org.harper.bookstore.domain.order;

import java.util.ArrayList;
import java.util.List;

public class CollectPlan {

	private List<CollectItem> items;

	public CollectPlan() {
		super();
		items = new ArrayList<CollectItem>();
	}
	
	public List<CollectItem> getItems() {
		return items;
	}

	public void setItems(List<CollectItem> items) {
		this.items = items;
	}

}
