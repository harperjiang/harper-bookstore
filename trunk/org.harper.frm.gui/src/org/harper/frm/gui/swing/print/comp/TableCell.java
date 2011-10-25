package org.harper.frm.gui.swing.print.comp;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.harper.frm.gui.swing.print.Component;

public class TableCell extends Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5934975939734758632L;

	private int posx;

	private int posy;

	private int rowSpan;

	private int cellSpan;

	private TextLabel renderer;

	public TableCell(Object content) {
		this(String.valueOf(content));
	}

	public TableCell(String content) {
		super();
		renderer = new TextLabel(content);
		renderer.setTextWrap(true);
	}

	@Override
	public void setFont(Font font) {
		super.setFont(font);
		getRenderer().setFont(font);
	}
	
	@Override
	public Dimension calcPreferredSize(Graphics2D g2d) {
		Rectangle pos = getPosition();
		renderer.setPosition(pos);
		return renderer.calcPreferredSize(g2d);
	}

	@Override
	public void draw(Graphics2D g2d) {
		renderer.setPosition(new Rectangle(0, 0, getPosition().width,
				getPosition().height));
		renderer.paint(g2d);
	}

	public TextLabel getRenderer() {
		return renderer;
	}

	public int getPosx() {
		return posx;
	}

	public void setPosx(int posx) {
		this.posx = posx;
	}

	public int getPosy() {
		return posy;
	}

	public void setPosy(int posy) {
		this.posy = posy;
	}

	public int getRowSpan() {
		return rowSpan;
	}

	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}

	public int getCellSpan() {
		return cellSpan;
	}

	public void setCellSpan(int cellSpan) {
		this.cellSpan = cellSpan;
	}

	public void setAlign(int alignCenter) {
		getRenderer().setAlign(alignCenter);
	}
}
