package org.harper.bookstore.service.bean.report;

import java.math.BigDecimal;

import org.harper.bookstore.domain.profile.Book;

public class ProductProfitResultBean {

	private ProductProfitItemBean items[];

	public ProductProfitItemBean[] getItems() {
		return items;
	}

	public void setItems(ProductProfitItemBean[] items) {
		this.items = items;
	}

	public static class ProductProfitItemBean {

		private Book book;

		private int count;

		private BigDecimal profitRate;

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

		public BigDecimal getProfitRate() {
			return profitRate;
		}

		public void setProfitRate(BigDecimal profitRate) {
			this.profitRate = profitRate;
		}
	}
}
