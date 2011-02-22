package org.harper.mediator;

import java.util.EventListener;

public interface MediatorListener extends EventListener {

	public void eventOccur(MediatorEvent event);
}
