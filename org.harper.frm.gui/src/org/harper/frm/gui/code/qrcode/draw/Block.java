package org.harper.frm.gui.code.qrcode.draw;

import java.awt.Point;
import java.util.Arrays;

public class Block {

	Point points[];

	Block() {
		points = new Point[8];
	}
	
	public Block(Point[] points) {
		this.points = points;
	}

	public Point getPoint(int i) {
		return points[i];
	}
	
	public Iterable<Point> getPoints() {
		return Arrays.asList(points);
	}

	public static class VerticalUpBlock extends Block {

		public VerticalUpBlock(int x, int y) {
			super();
			for (int i = 0; i < 8; i++) {
				points[i] = new Point(x - i % 2, y - i / 2);
			}
		}
	}

	public static class VerticalDownBlock extends Block {

		public VerticalDownBlock(int x, int y) {
			super();
			for (int i = 0; i < 8; i++) {
				points[i] = new Point(x - i % 2, y + i / 2);
			}
		}
	}
}
