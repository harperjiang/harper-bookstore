package org.harper.bookstore.domain.store;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.harper.bookstore.domain.Entity;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.BookUnit;
import org.harper.bookstore.domain.setting.StoreSettingBean;
import org.springframework.util.CollectionUtils;

public class StoreSite extends Entity {

	private String name;

	private int privilege;

	private String description;

	// Is this storesite available to sale books
	private boolean forOutput;

	private List<StoreEntry> entries;

	public StoreSite() {
		super();
		entries = new ArrayList<StoreEntry>();
	}

	public List<StoreEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<StoreEntry> entries) {
		getEntries().clear();
		if (null != entries)
			for (StoreEntry entry : entries)
				addEntry(entry);
	}

	public void addEntry(StoreEntry newEntry) {
		getEntries().add(newEntry);
		newEntry.setSite(this);
		fireNewItem(newEntry);
	}

	public int getPrivilege() {
		return privilege;
	}

	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isForOutput() {
		return forOutput;
	}

	public void setForOutput(boolean forOutput) {
		this.forOutput = forOutput;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public StoreEntry putInto(Book book, int count, BigDecimal unitPrice) {
		StoreEntry entry = getEntry(book);
		if (null == entry) {
			entry = new StoreEntry();
			entry.setBook(book);
			entry.setSite(this);
			entry.setUnitPrice(unitPrice);
			addEntry(entry);
		}
		entry.add(count, unitPrice);
		return entry;
	}

	public int lock(Book book, int count) {
		// Validate.isTrue(isForOutput(),
		// "This store should not be used for output");
		StoreEntry entry = getEntry(book);
		if (StoreSettingBean.get().isAllowNegativeStock()) {
			if (null == entry)
				entry = putInto(book, 0, BigDecimal.ZERO);
			entry.lock(count);
			return count;
		} else {
			if (null == entry)
				return 0;
			// If allowing negative storage, always output requested book count
			int val = Math.min(count, entry.getAvailable());
			entry.lock(val);
			return val;
		}
	}

	public BookUnit retrieve(Book book, int count) {
		// Validate.isTrue(isForOutput(),
		// "This store should not be used for output");

		StoreEntry entry = getEntry(book);
		if (StoreSettingBean.get().isAllowNegativeStock()) {
			if (null == entry)
				return null;
			return entry.consume(count);
		} else {
			if (null == entry)
				return null;
			int val = Math.min(count, entry.getLockedCount());
			return entry.consume(val);
		}
	}

	public int cancel(Book book, int count) {
		Validate.isTrue(isForOutput(),
				"This store should not be used for output");
		StoreEntry entry = getEntry(book);
		if (null == entry)
			return 0;
		int val = Math.min(count, entry.getLockedCount());
		entry.cancelLock(val);
		return val;
	}

	public void enable() {
		Validate.isTrue(!isValid(), "StoreSite alread enabled");
		setValid(true);
	}

	public void disable() {
		Validate.isTrue(isValid(), "StoreSite already disabled");
		if (!CollectionUtils.isEmpty(getEntries()))
			for (StoreEntry entry : getEntries()) {
				if (entry.getCount() > 0)
					throw new IllegalStateException("Not an empty Store");
			}
		setValid(false);
	}

	public StoreEntry getEntry(Book book) {
		if (null == entries)
			return null;
		for (StoreEntry entry : entries)
			if (book.equals(entry.getBook()))
				return entry;
		return null;
	}
}
