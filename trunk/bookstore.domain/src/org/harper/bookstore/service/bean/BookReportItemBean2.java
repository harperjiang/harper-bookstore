package org.harper.bookstore.service.bean;

import java.math.BigDecimal;

import org.harper.bookstore.domain.profile.Book;

public class BookReportItemBean2 {

	private Book book;

	private int count;
	
	private BigDecimal cost;

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
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	
	
}
