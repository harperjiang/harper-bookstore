package org.harper.bookstore.domain.finance;

import java.util.Date;
import java.util.List;

import org.harper.bookstore.domain.Entity;

public class FinanceRecord extends Entity {

	public static enum Status {
		NEW, DRAFT, CONFIRM, FINISH
	}
	
	private Account account;

	private Date createDate;

	private Date recognizeDate;

	private List<ChargeItem> items;

	private int status;
}
