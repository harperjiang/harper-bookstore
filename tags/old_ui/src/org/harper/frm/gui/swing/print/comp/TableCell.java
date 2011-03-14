package org.harper.frm.gui.swing.print.comp;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class TableCell extends TextLabel {

	private int posx;

	private int posy;

	private int rowSpan;

	private int cellSpan;

	public TableCell(String content) {
		super(content);
	}

	@Override
	public Dimension calcPreferredSize(Graphics2D g2d) {
		setPreferredSize(getImageableArea().getSize());
		extend(g2d, getImageableArea().y, getText());
		return getPreferredSize(g2d);
	}

	protected void extend(Graphics2D graphic, int y, String now) {
		Rectangle2D fontRect = graphic.getFont().getStringBounds(now,
				graphic.getFontRenderContext());

		fontRect = new Rectangle(getImageableArea().x, y, (int) Math
				.ceil(fontRect.getWidth()), (int) Math.ceil(fontRect
				.getHeight()));
		Dimension pref = getPreferredSize(graphic);
		// CRLF...
		for (int i = now.length(); i > 0; i--) {
			Rectangle2D subRect = graphic.getFont().getStringBounds(
					now.substring(0, i), graphic.getFontRenderContext());
			if (subRect.getWidth() < pref.width) {
				setPreferredSize(new Dimension(pref.width, (int) Math.ceil(Math
						.max(pref.height, y + fontRect.getHeight()))));
				extend(graphic,
						(int) (y + subRect.getHeight() + getRowPadding()), now
								.substring(i));
				break;
			}
		}
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

}
