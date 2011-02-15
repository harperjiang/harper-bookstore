package org.harper.bookstore.domain.store;

import java.math.BigDecimal;

import org.apache.commons.lang.Validate;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.BookUnit;
import org.harper.bookstore.domain.setting.StoreSettingBean;

public class StoreEntry {

	private int oid;

	private StoreSite site;

	private Book book;

	private int count;

	private int lockedCount;

	private BigDecimal unitPrice;

	public StoreSite getSite() {
		return site;
	}

	public void setSite(StoreSite site) {
		this.site = site;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getAvailable() {
		return getCount() - getLockedCount();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public int getLockedCount() {
		return lockedCount;
	}

	public void setLockedCount(int lockedCount) {
		this.lockedCount = lockedCount;
	}

	public void lock(int tryCount) {
		if (!StoreSettingBean.get().isAllowNegativeStock())
			Validate.isTrue(count - lockedCount >= tryCount);
		lockedCount += tryCount;
	}

	public void add(int count, BigDecimal unitPrice) {
		if (0 == count)
			return;
		BigDecimal newUnitPrice = unitPrice.multiply(new BigDecimal(count))
				.add(getUnitPrice().multiply(new BigDecimal(getCount())))
				.divide(new BigDecimal(count + getCount()),
						BigDecimal.ROUND_HALF_UP);
		setCount(getCount() + count);
		setUnitPrice(newUnitPrice);
	}

	public BookUnit consume(int tryCount) {
		Validate.isTrue(tryCount <= lockedCount);
		lockedCount -= tryCount;
		count -= tryCount;
		return new BookUnit(book, tryCount, unitPrice);
	}

	public void cancelLock(int tryCount) {
		Validate.isTrue(tryCount <= lockedCount);
		lockedCount -= tryCount;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

}
