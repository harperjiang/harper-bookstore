package org.harper.bookstore.domain.profile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.harper.bookstore.domain.Entity;

public class Book extends Entity {

	private String isbn;

	private String name;

	private String desc;

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public boolean equals(Object input) {
		if (input instanceof Book)
			return (null != getIsbn())
					&& getIsbn().equals(((Book) input).getIsbn());
		return super.equals(input);
	}

	@Override
	public int hashCode() {
		return null == getIsbn() ? super.hashCode() : getIsbn().hashCode();
	}

	public List<BookUnit> getContent() {
		ArrayList<BookUnit> content = new ArrayList<BookUnit>();
		BookUnit unit = new BookUnit(this, 1, BigDecimal.ONE);
		content.add(unit);
		return content;
	}
}
