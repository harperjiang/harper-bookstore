package org.harper.frm.data.util;
/**
 * 
 * @author Harper Jiang
 * @since ips.frm.component 1.0
 * @version 1.0 Jun 10, 2009
 */
public class Section<T extends ISizable> implements Comparable<Section<T>> {

	private T content;

	private long offset;

	private long length;

	public Section(T content, long off, long lengt) {
		this.content = content;
		this.offset = off;
		this.length = lengt;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public String toString() {
		return String.valueOf(getContent()) + ":" + getOffset() + ","
				+ getLength();
	}

	public int compareTo(Section<T> o) {
		return (int) (1 * Math.signum(getOffset() - o.getOffset()));
	}
}