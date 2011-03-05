package org.harper.mediator;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

public class MediatorManager implements Mediator {

	private static MediatorManager instance;

	public synchronized static Mediator getInstance() {
		if (null == instance)
			instance = new MediatorManager();
		return instance;
	}

	private Map<String, PropertyChangeSupport> maps;

	private MediatorManager() {
		maps = new HashMap<String, PropertyChangeSupport>();
	}

	@Override
	public void sendMessage(String ns, PropertyChangeEvent msg) {
		for (Map.Entry<String, PropertyChangeSupport> entry : maps.entrySet())
			if (entry.getKey().startsWith(ns))
				entry.getValue().firePropertyChange(msg);

	}

	@Override
	public void addListener(String ns, PropertyChangeListener listener) {
		if (!maps.containsKey(ns))
			maps.put(ns, new PropertyChangeSupport(this));
		maps.get(ns).addPropertyChangeListener(listener);
	}

	@Override
	public void removeListener(String ns, PropertyChangeListener listener) {
		if (maps.containsKey(ns))
			maps.get(ns).removePropertyChangeListener(listener);
	}

}
