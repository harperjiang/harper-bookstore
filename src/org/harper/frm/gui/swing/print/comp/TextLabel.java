package org.harper.frm.gui.swing.print.comp;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import org.harper.frm.gui.swing.print.Component;
import org.harper.frm.gui.swing.print.Print;

public class TextLabel extends Component {

	private String text;

	private int rowPadding = 5;

	private int align = Print.ALIGN_LEFT | Print.ALIGN_TOP;

	public TextLabel() {
		this("");
	}

	public TextLabel(String content) {
		super();
		this.text = content;
	}

	@Override
	public void draw(Graphics2D graphic) {
		if (null == getText())
			return;
		if (null == graphic.getFont())
			return;
		drawString(graphic, getImageableArea(), text);
	}

	protected void drawString(Graphics2D graphic, Rectangle rect, String now) {
		Rectangle2D fontRect = graphic.getFont().getStringBounds(now,
				graphic.getFontRenderContext());
		if (getImageableArea().getHeight() < fontRect.getHeight())
			return;

		fontRect = new Rectangle(rect.x, rect.y, (int) fontRect.getWidth(),
				(int) fontRect.getHeight());

		if ((align & Print.ALIGN_LEFT) == 0 || (align & Print.ALIGN_TOP) == 0) {
			// Not a left-top print, no CRLF
			int x = 0, y = 0;
			if ((align & Print.ALIGN_LEFT) > 0) {
				x = 0;
			} else if ((align & Print.ALIGN_CENTER) > 0) {
				x = (int) (fontRect.getWidth() > rect.width ? 0
						: (rect.width - fontRect.getWidth()) / 2);
			} else if ((align & Print.ALIGN_RIGHT) > 0) {
				x = (int) (fontRect.getWidth() > rect.width ? 0 : rect.width
						- fontRect.getWidth());
			}
			if ((align & Print.ALIGN_TOP) > 0) {
				y = (int) fontRect.getHeight();
			} else if ((align & Print.ALIGN_CENTER) > 0) {
				y = (int) fontRect.getHeight();
			} else if ((align & Print.ALIGN_BOTTOM) > 0) {
				y = (int) (fontRect.getHeight() > rect.height ? 0 : rect.height);
			}
			graphic.drawString(now, x, y);
			return;
		}

		// CRLF...
		for (int i = now.length(); i > 0; i--) {
			Rectangle2D subRect = graphic.getFont().getStringBounds(
					now.substring(0, i), graphic.getFontRenderContext());
			subRect = new Rectangle(rect.x, rect.y, (int) subRect.getWidth(),
					(int) subRect.getHeight());
			if (rect.contains(subRect)) {
				graphic.drawString(now.substring(0, i), rect.x,
						(int) (rect.y + subRect.getHeight()));
				drawString(graphic,
						new Rectangle(rect.x, (int) (rect.y
								+ subRect.getHeight() + rowPadding),
								rect.width, (int) (rect.height
										- subRect.getHeight() - rowPadding)),
						now.substring(i));
				break;
			}
		}

	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getRowPadding() {
		return rowPadding;
	}

	public void setRowPadding(int rowPadding) {
		this.rowPadding = rowPadding;
	}

	public int getAlign() {
		return align;
	}

	public void setAlign(int align) {
		this.align = align;
	}

	@Override
	public Dimension calcPreferredSize(Graphics2D g2d) {
		if (null == getText())
			return new Dimension(0, 0);
		Font font = getFont() == null ? g2d.getFont() : getFont();
		if (null == font)
			return null;
		Rectangle2D rect = font.getStringBounds(text, g2d
				.getFontRenderContext());
		return new Dimension((int) Math.ceil(rect.getWidth()), (int) Math
				.ceil(rect.getHeight()));
	}
}
