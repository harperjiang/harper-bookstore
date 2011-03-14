package org.harper.frm.gui.swing.comp.tree;

import java.util.Vector;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class CommonTreeModel implements TreeModel {

	private EventListenerList eventListeners = new EventListenerList();

	private CommonTreeNode root;

	private boolean rootVisible;

	public CommonTreeModel() {
		super();
		root = new CommonTreeNode("root");
	}

	public boolean isRootVisible() {
		return rootVisible;
	}

	public void setRootVisible(boolean vis) {
		rootVisible = vis;
	}

	public CommonTreeNode getRoot() {
		return root;
	}

	public void setRoot(CommonTreeNode newRoot) {
		root = newRoot;
		fireNodeStructureChanged(newRoot);
	}

	public CommonTreeNode getChild(Object parent, int index) {
		return ((CommonTreeNode) parent).getChildAt(index);
	}

	public int getChildCount(Object parent) {
		return ((CommonTreeNode) parent).getChildCount();
	}

	public boolean isLeaf(Object node) {
		return ((CommonTreeNode) node).isLeaf();
	}

	public int getIndexOfChild(CommonTreeNode parent, CommonTreeNode child) {
		return parent.children().indexOf(child);
	}

	public void changeNode(CommonTreeNode treeNode, Object newValue) {
		treeNode.setUserObject(newValue);
		fireNodeChanged(treeNode);
	}

	public void insertNode(CommonTreeNode parentNode, CommonTreeNode newNode) {
		newNode.setParent(parentNode);
		fireNodeInserted(newNode);
	}

	public void removeNode(CommonTreeNode parentNode, CommonTreeNode removing) {
		removing.setParent(null);
		fireNodeRemoved(removing);
	}

	public void refreshNode(CommonTreeNode node) {
		if (node != null)
			fireNodeStructureChanged(node);
	}

	protected void fireNodeChanged(CommonTreeNode node) {
		TreeModelListener[] listeners = eventListeners
				.getListeners(TreeModelListener.class);
		TreeModelEvent evt = new TreeModelEvent(this, getParentPath(node),
				new int[] { node.getParent().getIndex(node) },
				new Object[] { node });
		for (int i = 0; i < listeners.length; i++)
			listeners[i].treeNodesChanged(evt);
	}

	protected void fireNodeRemoved(CommonTreeNode theNode) {
		TreeModelListener[] listeners = eventListeners
				.getListeners(TreeModelListener.class);
		TreeModelEvent evt = new TreeModelEvent(this, getParentPath(theNode),
				new int[] { theNode.getParent().getIndex(theNode) },
				new Object[] { theNode });
		for (int i = 0; i < listeners.length; i++)
			listeners[i].treeNodesRemoved(evt);
	}

	protected void fireNodeInserted(CommonTreeNode newNode) {
		TreeModelListener[] listeners = eventListeners
				.getListeners(TreeModelListener.class);
		TreeModelEvent evt = new TreeModelEvent(this, getParentPath(newNode),
				new int[] { newNode.getParent().getIndex(newNode) },
				new Object[] { newNode });
		for (int i = 0; i < listeners.length; i++)
			listeners[i].treeNodesInserted(evt);
	}

	protected void fireNodeStructureChanged(CommonTreeNode node) {
		TreeModelListener[] listeners = eventListeners
				.getListeners(TreeModelListener.class);
		TreeModelEvent evt = new TreeModelEvent(this, getPath(node), null, null);

		for (int i = 0; i < listeners.length; i++)
			listeners[i].treeStructureChanged(evt);
	}

	public void addTreeModelListener(TreeModelListener l) {
		eventListeners.add(TreeModelListener.class, l);
	}

	public void removeTreeModelListener(TreeModelListener l) {
		eventListeners.remove(TreeModelListener.class, l);
	}

	private TreePath getParentPath(CommonTreeNode node) {
		return getPath(node.getParent());
	}

	private TreePath getPath(CommonTreeNode node) {
		if (node == null)
			return null;
		Vector path = new Vector();
		CommonTreeNode next = node;
		while (next != null) {
			path.add(0, next);
			next = next.getParent();
		}
		if (path.size() != 0)
			return new TreePath(path.toArray());
		return null;
	}

	public int getIndexOfChild(Object parent, Object child) {
		return getIndexOfChild((CommonTreeNode)parent,(CommonTreeNode)child);
	}

	public void valueForPathChanged(TreePath path, Object newValue) {
		return;
	}

}
