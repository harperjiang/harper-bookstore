package org.harper.bookstore.domain.finance;

import java.math.BigDecimal;
import java.util.Date;

import org.harper.bookstore.domain.Entity;

public class Accounting extends Entity {

	private Date createDate;
	
	private String subject;

	private BigDecimal amount;

	private String refType;

	private String refNumber;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	public String getRefNumber() {
		return refNumber;
	}

	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}

}
