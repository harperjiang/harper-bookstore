package org.harper.mediator;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public interface Mediator {

	public void sendMessage(String ns, PropertyChangeEvent msg);

	public void addListener(String ns, PropertyChangeListener listener);

	public void removeListener(String ns, PropertyChangeListener listener);
}
