package org.harper.bookstore.service.bean.report;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.harper.bookstore.domain.profile.Book;

public class ProductProfitResultBean {

	private Book book;

	private BigDecimal profitRate;

	private List<ProductProfitItemBean> items;

	public List<ProductProfitItemBean> getItems() {
		return items;
	}

	public void setItems(List<ProductProfitItemBean> items) {
		this.items = items;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public BigDecimal getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(BigDecimal profitRate) {
		this.profitRate = profitRate;
	}

	public static class ProductProfitItemBean {

		private Date date;

		private int count;

		public ProductProfitItemBean(Date d, int c) {
			this.date = d;
			this.count = c;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

	}
}
