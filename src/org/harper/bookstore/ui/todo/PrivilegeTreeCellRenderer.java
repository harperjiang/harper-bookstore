package org.harper.bookstore.ui.todo;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import org.harper.bookstore.domain.todo.TodoItem;
import org.harper.bookstore.domain.todo.TodoItem.Privilege;

public class PrivilegeTreeCellRenderer extends DefaultTreeCellRenderer
		implements TreeCellRenderer {

	static Color[] FGS = new Color[] { Color.RED, Color.MAGENTA, Color.BLACK,
			Color.GRAY, Color.LIGHT_GRAY };

	static Color[] FGS_SELECTED = new Color[] {};

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		String convertValue = null;
		Color fg = null;
		Color bg = null;
		if (value instanceof Privilege) {
			fg = FGS[((Privilege) value).ordinal()];
			convertValue = ((Privilege) value).name();
		} else if (value instanceof TodoItem) {
			fg = FGS[((TodoItem) value).getPrivilege().ordinal()];
			convertValue = ((TodoItem) value).getSubject();
		} else
			convertValue = String.valueOf(value);
		JLabel superLabel = (JLabel) super.getTreeCellRendererComponent(tree,
				convertValue, sel, expanded, leaf, row, hasFocus);
		superLabel.setForeground(fg);
		return superLabel;
	}
}
