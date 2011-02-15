package org.harper.frm.data.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
/**
 * 
 * @author Harper Jiang
 * @since ips.frm.component 1.0
 * @version 1.0 Jun 10, 2009
 */
public class SectionSplitter<T extends ISizable> {

	private T[] files;

	private int split;

	public SectionSplitter(T[] files, int split) {
		Validate.noNullElements(files);

		this.files = files;
		this.split = split;
	}

	public List<List<Section<T>>> split() {
		List<List<Section<T>>> groups = new ArrayList<List<Section<T>>>();

		// Prepare storage;
		for (int i = 0; i < split; i++)
			groups.add(new ArrayList<Section<T>>());
		// Get average
		long total = 0;
		for (int i = 0; i < files.length; i++)
			total += files[i].getSize();

		long part = total / split;

		int group = split - 1;
		long groupleft = part;
		long curoffset = 0;
		for (int i = 0; i < files.length; i++) {
			if (groupleft + curoffset >= files[i].getSize()) {
				groups.get(group)
						.add(
								new Section<T>(files[i], curoffset, files[i].getSize()
										- curoffset));
				groupleft -= files[i].getSize() - curoffset;
				curoffset = 0;
			} else {
				if (group == 0) {// Final Group
					groups.get(group).add(
							new Section<T>(files[i], curoffset, files[i].getSize()
									- curoffset));
					for (int j = i + 1; j < files.length; j++)
						groups.get(group).add(
								new Section<T>(files[j], 0, files[j].getSize()));
					break;
				} else {
					groups.get(group).add(
							new Section<T>(files[i], curoffset, groupleft));
					group--;
					curoffset += groupleft;
					groupleft = part;
					i--;
				}
			}
		}
		return groups;
	}

//	public static class Section<TT> implements Comparable<Section<TT>> {
//		private TT file;
//		private long offset;
//		private long length;
//
//		public Section(TT file, long off, long lengt) {
//			this.file = file;
//			this.offset = off;
//			this.length = lengt;
//		}
//
//		public TT getFile() {
//			return file;
//		}
//
//		public void setFile(TT file) {
//			this.file = file;
//		}
//
//		public long getOffset() {
//			return offset;
//		}
//
//		public void setOffset(long offset) {
//			this.offset = offset;
//		}
//
//		public long getLength() {
//			return length;
//		}
//
//		public void setLength(long length) {
//			this.length = length;
//		}
//
//		public String toString() {
//			return String.valueOf(getFile()) + ":" + getOffset() + ","
//					+ getLength();
//		}
//
//		public int compareTo(Section<TT> o) {
//			return (int) (1 * Math.signum(getOffset() - o.getOffset()));
//		}
//	}

}