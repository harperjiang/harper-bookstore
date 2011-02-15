package org.harper.bookstore.domain.library;

import java.util.Date;

import org.harper.bookstore.domain.Entity;
import org.harper.bookstore.domain.Item;
import org.harper.bookstore.domain.profile.Book;

public class RecordItem extends Entity implements Item{

	public static enum Status {
		OST, PARTIAL_OST, BALANCED, ERROR
	}

	private Record header;

	private int status;

	private Book book;

	private int count;

	private int outstanding;

	private RecordItem balanceTo;

	private Date closeDate;

	public Record getHeader() {
		return header;
	}

	public void setHeader(Record header) {
		this.header = header;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
		evaluate();
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public int getOutstanding() {
		return outstanding;
	}

	public void setOutstanding(int outstanding) {
		this.outstanding = outstanding;
		evaluate();
	}

	protected void evaluate() {
		if (this.outstanding > count)
			throw new IllegalArgumentException();
		if (this.outstanding == count) {
			status = Status.OST.ordinal();
		} else if (this.outstanding == 0)
			status = Status.BALANCED.ordinal();
		else
			status = Status.PARTIAL_OST.ordinal();
	}

	public RecordItem getBalanceTo() {
		return balanceTo;
	}

	public void setBalanceTo(RecordItem balanceTo) {
		this.balanceTo = balanceTo;
	}

	public boolean isOutstanding() {
		return Status.OST.ordinal() == status
				|| Status.PARTIAL_OST.ordinal() == status;
	}
}
