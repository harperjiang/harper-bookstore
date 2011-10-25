package org.harper.frm.gui.swing.comp.tree;

import java.util.List;
import java.util.Vector;

import org.harper.frm.gui.swing.comp.tree.data.TreeData;

public class CommonTreeNode {

	private CommonTreeNode parent;

	private Vector<CommonTreeNode> children;

	private boolean allowChild;

	private Object userObj;

	private TreeData data;

	public CommonTreeNode(Object obj) {
		super();
		children = new Vector<CommonTreeNode>();
		userObj = obj;
	}

	public CommonTreeNode() {
		this(null);
	}

	public TreeData getData() {
		return data;
	}

	public void setData(TreeData data) {
		this.data = data;
	}

	public void appendChild(CommonTreeNode child) {
		children.add(child);
		child.parent = this;
	}

	public void removeChild(CommonTreeNode child) {
		children.remove(child);
		child.parent = null;
	}

	public CommonTreeNode removeAt(int childIndex) {
		CommonTreeNode ctn = getChildAt(childIndex);
		removeChild(ctn);
		return ctn;
	}

	public CommonTreeNode getChildAt(int childIndex) {
		return children.get(childIndex);
	}

	public int getChildCount() {
		return children.size();
	}

	public CommonTreeNode getParent() {
		return parent;
	}

	public void setParent(CommonTreeNode newParent) {
		if (getParent() != null)
			getParent().removeChild(this);
		this.parent = newParent;
		if (newParent != null)
			newParent.appendChild(this);

	}

	public int getIndex(CommonTreeNode node) {
		return children.indexOf(node);
	}

	public boolean getAllowsChildren() {
		return allowChild;
	}

	public boolean isLeaf() {
		return getChildCount() == 0;
	}

	public List<CommonTreeNode> children() {
		return children;
	}

	public void setUserObject(Object userObject) {
		this.userObj = userObject;
	}

	public Object getUserObject() {
		return this.userObj;
	}

}
