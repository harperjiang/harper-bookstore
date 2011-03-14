package org.harper.frm.gui.service;

public interface ServiceBroker {
	public Service getService(Class serviceClass);
	
	public void returnService(Service service);
}
