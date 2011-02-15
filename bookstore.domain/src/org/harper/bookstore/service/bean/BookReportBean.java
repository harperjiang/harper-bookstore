package org.harper.bookstore.service.bean;

import java.math.BigDecimal;
import java.util.List;

import org.harper.bookstore.domain.profile.Book;
import org.harper.frm.core.IAdaptor;


public class BookReportBean implements IAdaptor {

	private Book book;

	private String bookName;

	private int totalCount;

	private BigDecimal averageCost;

	private BigDecimal listPrice;

	private List<BookReportItemBean> items;

	private List<BookReportItemBean2> item2s;

	@Override
	public Object getAdaptor(Class<?> adapterClass) {
		return null;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public BigDecimal getAverageCost() {
		return averageCost;
	}

	public void setAverageCost(BigDecimal averageCost) {
		this.averageCost = averageCost;
	}

	public List<BookReportItemBean> getItems() {
		return items;
	}

	public void setItems(List<BookReportItemBean> items) {
		this.items = items;
	}

	public List<BookReportItemBean2> getItem2s() {
		return item2s;
	}

	public void setItem2s(List<BookReportItemBean2> item2s) {
		this.item2s = item2s;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public BigDecimal getListPrice() {
		return listPrice;
	}

	public void setListPrice(BigDecimal listPrice) {
		this.listPrice = listPrice;
	}

}
