package org.harper.frm.data.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
/**
 * 
 * @author Harper Jiang
 * @since ips.frm.component 1.0
 * @version 1.0 Jun 10, 2009
 */
public class MemoryByteSectionBuffer<T extends ISizable> extends
		SectionBuffer<T, byte[], byte[]> {

	private Map<Object, byte[]> buffers = new HashMap<Object, byte[]>();

	@Override
	protected byte[] bufferForRead(Object key) {
		return buffers.get(key);
	}

	@Override
	protected byte[] bufferForWrite(Object key, long size) {
		byte[] buffer = new byte[(int) size];
		buffers.put(key, buffer);
		return buffer;
	}

	@Override
	public Map<T, byte[]> organize() {
		try {
			lock.lock();
			
			Map<T,byte[]> result = new HashMap<T,byte[]>();
			
			for (Entry<T, List<Section<T>>> entry : blocks.entrySet()) {
				List<Section<T>> list = entry.getValue();
				int count = 0;
				for (Section<T> sec : list)
					count += sec.getLength();
				byte[] bufferT = new byte[count];
				for (Section<T> sec : list)
					System.arraycopy(bufferForRead(sec), 0, bufferT, (int)sec
							.getOffset(), (int)sec.getLength());
				result.put(entry.getKey(), bufferT);
				
			}
			return result;
		} finally {
			lock.unlock();
		}
	}
}
