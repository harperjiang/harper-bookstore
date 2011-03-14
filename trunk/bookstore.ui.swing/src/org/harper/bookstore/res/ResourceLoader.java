package org.harper.bookstore.res;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class ResourceLoader {

	private Map<String, Object> resourceCache;

	private ResourceLoader() {
		resourceCache = new HashMap<String, Object>();
	}

	static ResourceLoader instance;

	public synchronized static ResourceLoader getInstance() {
		if (null == instance)
			instance = new ResourceLoader();
		return instance;
	}

	public ImageIcon createImageIcon(String path) {
		if (!resourceCache.containsKey(path)) {
			java.net.URL imgURL = getClass().getResource(path);
			if (imgURL != null) {
				resourceCache.put(path, new ImageIcon(imgURL));
			} else {
				throw new IllegalArgumentException("Couldn't find file: "
						+ path);
			}
		}
		return (ImageIcon) resourceCache.get(path);
	}

}
