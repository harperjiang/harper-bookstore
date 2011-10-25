package org.harper.frm.gui.swing.print.comp;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.harper.frm.gui.swing.print.Component;
import org.harper.frm.gui.swing.print.Layout;

public class Container extends Component {

	private List<Component> children;

	private Layout layout;

	public Container() {
		super();
		children = new ArrayList<Component>();
	}

	public void add(Component child) {
		children.add(child);
	}

	public void remove(Component child) {
		children.remove(child);
	}

	public List<Component> getChildren() {
		return children;
	}

	@Override
	public void draw(Graphics2D graphic) {
		for (Component child : children)
			child.paint(graphic);
	}

	/**
	 * 
	 */
	@Override
	public void prepare(Graphics2D g2d) {
		layout(g2d);
		for (Component child : children)
			child.prepare(g2d);
	}

	@Override
	public Dimension calcPreferredSize(Graphics2D g2d) {
		if (null != getLayout())
			return getLayout().getPreferredSize(this, g2d);
		Dimension dim = getPosition().getSize();
		for (Component child : getChildren()) {
			dim.width = (int) Math.max(
					child.getPosition().x + child.getPosition().width,
					dim.width);
			dim.height = (int) Math.max(
					child.getPosition().y + child.getPosition().height,
					dim.height);
		}
		return dim;
	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public void layout(Graphics2D g2d) {
		if (null != getLayout()) {
			getLayout().layout(this, g2d);
		}
	}
}
