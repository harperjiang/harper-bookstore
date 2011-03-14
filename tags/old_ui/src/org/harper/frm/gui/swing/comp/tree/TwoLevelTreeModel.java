package org.harper.frm.gui.swing.comp.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.harper.frm.core.tools.sort.HeapSorter;

public class TwoLevelTreeModel<P extends Comparable<P>, C> implements TreeModel {

	private Class<P> parentClass;

	private Class<C> childClass;

	private Map<P, List<C>> root;

	private List<P> headers;

	protected EventListenerList listenerList = new EventListenerList();

	public TwoLevelTreeModel(Class<P> parent, Class<C> children) {
		this.parentClass = parent;
		this.childClass = children;

		this.headers = new ArrayList<P>();
		this.root = new HashMap<P, List<C>>();
	}

	@Override
	public Object getRoot() {
		return root;
	}

	public void setRoot(Map<P, List<C>> newData) {
		Object oldRoot = getRoot();
		this.root = newData;
		this.headers.clear();
		this.headers.addAll(root.keySet());
		new HeapSorter(true).sort(headers);
		if (null == oldRoot && null != newData)
			fireTreeStructureChanged(this, null);
		else
			fireTreeStructureChanged(this, new Object[] { newData }, null, null);
	}

	@Override
	public Object getChild(Object parent, int index) {
		if (root == parent) {
			return headers.get(index);
		}
		if (parentClass.isInstance(parent)) {
			return root.get(parent).get(index);
		}
		return null;
	}

	@Override
	public int getChildCount(Object parent) {
		if (root == parent) {
			return headers.size();
		}
		if (parentClass.isInstance(parent)) {
			return root.get(parent).size();
		}
		return 0;
	}

	@Override
	public boolean isLeaf(Object node) {
		return childClass.isInstance(node);
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if (root == parent) {
			return headers.indexOf(child);
		}
		if (parentClass.isInstance(parent)) {
			return root.get(parent).indexOf(child);
		}
		return 0;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		listenerList.add(TreeModelListener.class, l);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		listenerList.remove(TreeModelListener.class, l);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// Do nothing and don't store the data
	}

	protected void fireTreeNodesChanged(Object source, Object[] path,
			int[] childIndices, Object[] children) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e = new TreeModelEvent(source, path, childIndices, children);
				((TreeModelListener) listeners[i + 1]).treeNodesChanged(e);
			}
		}
	}

	protected void fireTreeNodesInserted(Object source, Object[] path,
			int[] childIndices, Object[] children) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e = new TreeModelEvent(source, path, childIndices, children);
				((TreeModelListener) listeners[i + 1]).treeNodesInserted(e);
			}
		}
	}

	protected void fireTreeNodesRemoved(Object source, Object[] path,
			int[] childIndices, Object[] children) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e = new TreeModelEvent(source, path, childIndices, children);
				((TreeModelListener) listeners[i + 1]).treeNodesRemoved(e);
			}
		}
	}

	protected void fireTreeStructureChanged(Object source, Object[] path,
			int[] childIndices, Object[] children) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e = new TreeModelEvent(source, path, childIndices, children);
				((TreeModelListener) listeners[i + 1]).treeStructureChanged(e);
			}
		}
	}

	private void fireTreeStructureChanged(Object source, TreePath path) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e = new TreeModelEvent(source, path);
				((TreeModelListener) listeners[i + 1]).treeStructureChanged(e);
			}
		}
	}

}
