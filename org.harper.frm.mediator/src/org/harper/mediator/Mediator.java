package org.harper.mediator;

public interface Mediator {

	public void sendMessage(MediatorMessage msg);
	
	public void addListener(MediatorListener listener);
	
	public void removeListener(MediatorListener listener);
}
