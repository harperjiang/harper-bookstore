package org.harper.mediator;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MediatorManager implements Mediator {

	private static MediatorManager instance;

	public synchronized static Mediator getInstance() {
		if (null == instance)
			instance = new MediatorManager();
		return instance;
	}

	private Map<String, PropertyChangeSupport> maps;

	private ReentrantReadWriteLock lock;

	private MediatorManager() {
		maps = new HashMap<String, PropertyChangeSupport>();
		lock = new ReentrantReadWriteLock();
	}

	@Override
	public void sendMessage(String ns, PropertyChangeEvent msg) {
		try {
			lock.readLock().lock();
			for (Map.Entry<String, PropertyChangeSupport> entry : maps
					.entrySet())
				if (entry.getKey().startsWith(ns))
					entry.getValue().firePropertyChange(msg);
		} finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public void addListener(String ns, PropertyChangeListener listener) {
		try {
			lock.writeLock().lock();

			if (!maps.containsKey(ns))
				maps.put(ns, new PropertyChangeSupport(this));
			maps.get(ns).addPropertyChangeListener(listener);
		} finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public void removeListener(String ns, PropertyChangeListener listener) {
		try {
			lock.writeLock().lock();
			if (maps.containsKey(ns))
				maps.get(ns).removePropertyChangeListener(listener);
		} finally {
			lock.writeLock().unlock();
		}
	}

	public static PropertyChangeListener probe = new PropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			MediatorManager.getInstance().sendMessage(
					evt.getSource().getClass().getName(), evt);
		}
	};

	public static final String PROPERTY_NEW = "new";

	public static final String PROPERTY_DELETE = "delete";
}
