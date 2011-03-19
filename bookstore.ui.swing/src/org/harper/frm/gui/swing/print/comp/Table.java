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
import org.harper.frm.gui.swing.print.border.LineBorder;

public class Table extends Container {

	public Table(int column) {
		super();
		setLayout(new TableLayout());
		setBorder(new LineBorder(1));
		columnWidth = new float[column];
		columnName = new String[column];
		cells = new ArrayList<TableCell>();
		for (int i = 0; i < column; i++) {
			// Reserved position for header cell
			add((TableCell) null);
		}
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
		setHeaderCell(newName, index);
	}

	public void setColumnName(String[] name) {
		Validate.isTrue(name.length == columnName.length);
		this.columnName = name;
		for (int i = 0; i < columnName.length; i++)
			setHeaderCell(columnName[i], i);
	}

	protected void setHeaderCell(String text, int index) {
		Font boldFont = new Font(getFont().getName(), Font.BOLD, getFont()
				.getSize());

		TableCell headerCell = new TableCell(text);
		headerCell.setAlign(Print.ALIGN_CENTER);
		headerCell.setFont(boldFont);
		getChildren().set(index, headerCell);
		cells.set(index, headerCell);
	}

	public void add(TableCell child) {
		cells.add(child);
		super.add(child);
		if (null != child)
			child.setFont(getFont());
	}

	@Override
	public void setPosition(Rectangle position) {
		super.setPosition(position);
		setPreferredSize(null);
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
		// if (0 != height) {
		// ypos += height;
		// graphic.drawLine(0, ypos, imagearea.width, ypos);
		// }
		graphic.translate(-imagearea.x, -imagearea.y);
	}

	@Override
	public void setLayout(Layout layout) {
		Validate.isTrue(layout instanceof TableLayout);
		super.setLayout(layout);
	}

	protected static class TableLayout implements Layout {

		@Override
		public Dimension getPreferredSize(Container container, Graphics2D g2d) {
			Table table = (Table) container;
			int total = 0;
			double maxRowHeight = 0;
			for (int i = 0; i < table.cells.size(); i++) {
				if (0 == i % table.columnWidth.length) {
					total += maxRowHeight;
					maxRowHeight = 0;
				}
				TableCell cell = table.cells.get(i);
				Dimension pref = cell.getPreferredSize(g2d);
				maxRowHeight = Math.max(maxRowHeight, pref.getHeight() + 2
						* table.cellPadding);
			}
			if (0 != maxRowHeight)
				total += maxRowHeight;
			return new Dimension(container.getPosition().width, total);
		}

		@Override
		public void layout(Container container, Graphics2D g2d) {
			Table table = (Table) container;
			Dimension dim = table.getPreferredSize(g2d);

			double[] width = new double[table.columnWidth.length];
			for (int i = 0; i < table.columnWidth.length; i++)
				width[i] = Math.floor(table.columnWidth[i]
						* table.getPosition().width);
			List<TableCell> cellInARow = new ArrayList<TableCell>();
			int maxRowHeight = 0;
			Point cursor = new Point(0, 0);
			for (int i = 0; i < table.cells.size(); i++) {
				int col = i % table.columnWidth.length;
				if (0 == col) {
					for (TableCell cc : cellInARow)
						cc.getPosition().height = maxRowHeight;
					if (0 != i)
						cursor.y += maxRowHeight;
					maxRowHeight = 0;
					cursor.x = 0;
					cellInARow.clear();
				}
				TableCell cell = table.cells.get(i);
				cell.getPosition().x = cursor.x + table.cellPadding;
				cell.getPosition().y = cursor.y + table.cellPadding;
				cell.getPosition().width = (int) width[col];
				cellInARow.add(cell);
				cursor.x += (int) width[col];
				Dimension pref = cell.getPreferredSize(g2d);
				maxRowHeight = (int) Math.ceil(Math.max(maxRowHeight,
						pref.getHeight() + 2 * table.cellPadding));
			}
			table.setPosition(new Rectangle(table.getPosition().getLocation(),
					table.getPreferredSize(g2d)));
		}
	}
}
