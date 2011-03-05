package org.harper.bookstore.domain.todo;

import java.util.Date;

public class TodoItem {

	public static enum Privilege {
		URGENT, HIGH, NORMAL, LOW, REFERENCE
	}

	public static enum Status {
		OUTSTANDING, RESOLVED, EXPIRED
	}

	private int status;

	private Date createDate;

	private Date dueDate;

	private Date resolveDate;

	private String subject;

	private String content;

	private String solution;

	private int privilege = Privilege.NORMAL.ordinal();

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Privilege getPrivilege() {
		return Privilege.values()[privilege];
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege.ordinal();
	}

	public Status getStatus() {
		return Status.values()[status];
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getResolveDate() {
		return resolveDate;
	}

	public void setResolveDate(Date resolveDate) {
		this.resolveDate = resolveDate;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

}
