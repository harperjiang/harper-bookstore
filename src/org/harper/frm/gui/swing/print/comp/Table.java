package org.harper.frm.gui.swing.print.comp;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.harper.frm.gui.swing.print.Layout;
import org.harper.frm.gui.swing.print.Print;
import org.harper.frm.gui.swing.print.border.EmptyBorder;
import org.harper.frm.gui.swing.print.border.LineBorder;

public class Table extends Container {

	public Table(int column) {
		super();
		setLayout(new TableLayout());
		setBorder(new LineBorder(1));
		columnWidth = new float[column];
		columnName = new String[column];
		cells = new ArrayList<TableCell>();
	}

	private int cellPadding = 3;

	private List<TableCell> cells;

	private float[] columnWidth;

	private String[] columnName;

	public float getColumnWidth(int index) {
		return columnWidth[index];
	}

	public void setColumnWidth(int index, float newWidth) {
		this.columnWidth[index] = newWidth;
	}

	public String getColumnName(int index) {
		return columnName[index];
	}

	public void setColumnName(int index, String newName) {
		this.columnName[index] = newName;
	}

	public void setColumnName(String[] name) {
		Validate.isTrue(name.length == columnName.length);
		this.columnName = name;
	}

	public void add(TableCell child) {
		cells.add(child);
		child.setBorder(new EmptyBorder(cellPadding));
		super.add(child);
	}

	@Override
	public void draw(Graphics2D graphic) {
		super.draw(graphic);
		Rectangle imagearea = getImageableArea();
		graphic.translate(imagearea.x, imagearea.y);
		// Draw vertical line
		int xpos = 0;
		for (int i = 0; i < columnWidth.length; i++) {
			if (i > 0)
				graphic.drawLine(xpos, 0, xpos, imagearea.height);
			xpos += columnWidth[i] * imagearea.width;
		}
		// Draw horizontal line
		int ypos = 0;
		int height = 0;
		for (int i = 0; i < cells.size(); i++) {
			if (i % columnWidth.length == 0 && height > 0) {
				ypos += height;
				graphic.drawLine(0, ypos, imagearea.width, ypos);
				height = 0;

			}
			height = (int) Math.max(height, cells.get(i).getPosition().height);
		}
		if (0 != height) {
			ypos += height;
			graphic.drawLine(0, ypos, imagearea.width, ypos);
		}
		graphic.translate(-imagearea.x, -imagearea.y);
	}

	@Override
	public void setLayout(Layout layout) {
		Validate.isTrue(layout instanceof TableLayout);
		super.setLayout(layout);
	}

	@Override
	public void prepare(Graphics2D g2d) {
		// Insert table header cell
		Font boldFont = new Font(getFont().getName(), Font.BOLD, getFont()
				.getSize());
		for (int i = 0; i < columnName.length; i++) {
			TableCell headerCell = new TableCell(columnName[i]);
			headerCell.setAlign(Print.ALIGN_CENTER);
			headerCell.setFont(boldFont);
			getChildren().add(i, headerCell);
			cells.add(i, headerCell);
		}
		layout(g2d);
	}

	protected static class TableLayout implements Layout {

		@Override
		public Dimension getPreferredSize(Container container, Graphics2D g2d) {
			Table table = (Table) container;
			Dimension old = container.getImageableArea().getSize();

			double width[] = new double[table.columnWidth.length];
			for (int i = 0; i < width.length; i++)
				width[i] = Math.floor(old.getWidth() * table.getColumnWidth(i));

			Point point = table.getImageableArea().getLocation();

			double maxRowHeight = 0;
			for (int i = 0; i < table.cells.size(); i++) {
				if (0 == i % table.columnWidth.length) {
					point.x = table.getImageableArea().getLocation().x;
					point.y += maxRowHeight;
					maxRowHeight = 0;
				}
				TableCell cell = table.cells.get(i);
				cell.setPosition(new Rectangle(point.x, point.y, (int) width[i
						% table.columnWidth.length], 0));
				Dimension pref = cell.getPreferredSize(g2d);
				point.x += width[i % table.columnWidth.length];
				maxRowHeight = Math.max(maxRowHeight, pref.getHeight());
				old.height = (int) Math.ceil(point.y + pref.height);
			}

			return old;
		}

		@Override
		public void layout(Container container, Graphics2D g2d) {
			Table table = (Table) container;
			table.getPreferredSize(g2d);
			for (int i = 0; i < table.cells.size(); i++) {
				TableCell cell = table.cells.get(i);
				Dimension pref = cell.getPreferredSize(g2d);
				cell.setPosition(new Rectangle(
						cell.getPosition().getLocation(), pref));
			}
			table.setPosition(new Rectangle(table.getPosition().getLocation(),
					table.getPreferredSize(g2d)));
		}
	}
}
