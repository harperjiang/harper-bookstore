package org.harper.bookstore.ui.store;

import org.harper.bookstore.domain.store.StockTaking;
import org.harper.bookstore.ui.Controller;

public class StockTakingController extends Controller {

	private StockTaking bean;

	private StockTakingFrame frame;

	public StockTakingController(StockTaking bean) {
		super();
		this.bean = null == bean ? new StockTaking() : bean;

		this.frame = new StockTakingFrame();
		this.frame.setController(this);
	}

	public StockTakingController() {
		this(null);
	}

	public static void main(String[] args) {
		new StockTakingController();
	}
}
