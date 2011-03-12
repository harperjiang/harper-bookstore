package org.harper.frm.job.mediator;

import java.beans.PropertyChangeEvent;
import java.util.List;

import oracle.toplink.changesets.ChangeRecord;
import oracle.toplink.changesets.DirectToFieldChangeRecord;
import oracle.toplink.changesets.ObjectChangeSet;
import oracle.toplink.descriptors.DescriptorEvent;
import oracle.toplink.descriptors.DescriptorEventAdapter;
import oracle.toplink.queryframework.UpdateObjectQuery;

import org.harper.mediator.MediatorManager;

public class MediatorDescriptorEventListener extends DescriptorEventAdapter {

	public static MediatorDescriptorEventListener INSTANCE = new MediatorDescriptorEventListener();

	@Override
	public void postInsert(DescriptorEvent event) {
		Object obj = event.getObject();
		PropertyChangeEvent pe = new PropertyChangeEvent(obj,
				MediatorManager.PROPERTY_NEW, null, obj);
		MediatorManager.getInstance().sendMessage(obj.getClass().getName(), pe);
	}

	@Override
	public void postDelete(DescriptorEvent event) {
		Object obj = event.getObject();
		PropertyChangeEvent pe = new PropertyChangeEvent(obj,
				MediatorManager.PROPERTY_DELETE, null, obj);
		MediatorManager.getInstance().sendMessage(obj.getClass().getName(), pe);
	}

	@Override
	public void postUpdate(DescriptorEvent event) {
		Object obj = event.getObject();
		ObjectChangeSet changes = ((UpdateObjectQuery)event.getQuery()).getObjectChangeSet();
		for (String attr : (List<String>) changes.getChangedAttributeNames()) {

			// changes.get
			ChangeRecord cr = changes.getChangesForAttributeNamed(attr);
			if (cr instanceof DirectToFieldChangeRecord) {
				DirectToFieldChangeRecord dcr = (DirectToFieldChangeRecord) cr;
				PropertyChangeEvent pe = new PropertyChangeEvent(obj, attr,
						null, dcr.getNewValue());
				MediatorManager.getInstance().sendMessage(
						obj.getClass().getName(), pe);
			}
		}
	}
}
