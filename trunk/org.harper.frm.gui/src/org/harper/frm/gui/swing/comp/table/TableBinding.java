package org.harper.frm.gui.swing.comp.table;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.harper.frm.core.tools.bean.BeanAccess;
import org.harper.frm.gui.swing.comp.table.data.TableData;
import org.harper.frm.gui.swing.manager.ComponentBinding;

public class TableBinding extends ComponentBinding {

	public TableBinding(JTable table, String attribute) {
		super();
		setComponent(table);
		setAttribute(attribute);
	}

	private TableModelListener listener = new TableModelListener() {

		public void tableChanged(TableModelEvent e) {
			Object oldValue = value;
			CommonTableModel model = (CommonTableModel) ((JTable) getComponent())
					.getModel();
			List newValue = BeanAccess.getInstance().extract(model.getData(),
					"bean");
			itemChangeCallback(newValue, e);
			firePropertyChange(null, newValue);
		}
	};

	@Override
	public void setComponent(JComponent component) {
		// Add listener
		super.setComponent(component);
		if (!(component instanceof JTable))
			throw new IllegalArgumentException();
		((JTable) component).getModel().addTableModelListener(listener);
	}

	@Override
	public void setValue(Object value) {
		super.setValue(value);
		CommonTableModel model = (CommonTableModel) ((JTable) getComponent())
				.getModel();
		model.removeTableModelListener(listener);
		model.setData((List<TableData>) BeanAccess.getInstance().wrap(
				(List<Object>) value, model.refClass, Object.class));

		model.addTableModelListener(listener);
	}

	protected void itemChangeCallback(List<Object> vals, TableModelEvent e) {

	}
}
