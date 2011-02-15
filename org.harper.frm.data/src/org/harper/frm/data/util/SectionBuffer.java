package org.harper.frm.data.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.harper.frm.core.tools.insert.BinaryInserter;
/**
 * 
 * @author Harper Jiang
 * @since ips.frm.component 1.0
 * @version 1.0 Jun 10, 2009
 */
public abstract class SectionBuffer<T extends ISizable, I, O> {

	protected Map<T, List<Section<T>>> blocks = new HashMap<T, List<Section<T>>>();

	protected Lock lock = new ReentrantLock();

	public O getBuffer(Section<T> section) {
		try {
			lock.lock();

			List<Section<T>> sections = null;
			if (blocks.containsKey(section.getContent())) {
				sections = blocks.get(section.getContent());
			} else {
				sections = new ArrayList<Section<T>>();
				blocks.put(section.getContent(), sections);
			}
			new BinaryInserter().insert(section, sections);

			return bufferForWrite(section, section.getLength());
		} finally {
			lock.unlock();
		}
	}

	public abstract Map<T, I> organize();
	
	protected abstract O bufferForWrite(Object key, long size);

	protected abstract I bufferForRead(Object key);
}
