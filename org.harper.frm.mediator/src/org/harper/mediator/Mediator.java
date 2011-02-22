package org.harper.mediator;

public interface Mediator {

	public void sendMessage(MediatorEvent msg);

	public void addListener(String event, MediatorListener listener);

	public void removeListener(String event, MediatorListener listener);
}
