package org.harper.frm.gui.swing.comp.tree;

import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

import org.harper.frm.gui.swing.manager.ComponentBinding;

public class TwoLevelTreeBinding extends ComponentBinding {

	public TwoLevelTreeBinding(JTree tree, String attribute) {
		super();
		setComponent(tree);
		setAttribute(attribute);
	}

	private TreeModelListener listener = new TreeModelListener() {

		public void treeNodesChanged(TreeModelEvent e) {
		}

		public void treeNodesInserted(TreeModelEvent e) {
		}

		public void treeNodesRemoved(TreeModelEvent e) {
		}

		public void treeStructureChanged(TreeModelEvent e) {
		}
	};

	@Override
	public void setComponent(JComponent component) {
		// Add listener
		super.setComponent(component);
		if (!(component instanceof JTree))
			throw new IllegalArgumentException();
		((JTree) component).getModel().addTreeModelListener(listener);
	}

	@Override
	public void setValue(final Object value) {
		super.setValue(value);
		JTree tree = (JTree) getComponent();
		// Remove Listener
		final TwoLevelTreeModel tltm = (TwoLevelTreeModel) tree.getModel();
		tltm.removeTreeModelListener(listener);
		// Set Value
		tltm.setRoot((Map) value);

		// Add Listener
		tltm.addTreeModelListener(listener);
	}
}
