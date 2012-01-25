package org.harper.frm.gui.code.qrcode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;

import org.harper.frm.gui.code.qrcode.draw.Block;

public class CodeDrawer {

	private int pixelPerLine = 10;

	private CodeModel model;

	private Color darkColor = Color.BLACK;

	private Color lightColor = Color.WHITE;

	private transient boolean[][] bitmap;

	public BufferedImage draw(CodeModel model) throws IOException {
		this.model = model;
		this.bitmap = new boolean[model.getSize()][model.getSize()];

		BufferedImage image = new BufferedImage(getPixelPerLine()
				* (model.getSize() + 8), getPixelPerLine()
				* (model.getSize() + 8), ColorSpace.TYPE_RGB);

		Graphics graphics = image.getGraphics();
		// Fill With White
		drawSquare(graphics, 0, 0, model.getSize() + 8, false);
		graphics.translate(4 * getPixelPerLine(), 4 * getPixelPerLine());

		drawFinderPattern(graphics);

		drawTimingPattern(graphics);

		// Draw Data
		drawData(graphics);

		drawMask(graphics);

		drawFormat(graphics);

		return image;
	}

	private void drawFormat(Graphics graphics) {
		int format = model.getFormat();
		for (int i = 0; i < 6; i++) {
			drawPoint(graphics, i, 8, ((1 << 14 - i) & format) != 0);
			drawPoint(graphics, 8, i, ((1 << i) & format) != 0);
		}
		drawPoint(graphics, 7, 8, ((1 << 8) & format) != 0);
		drawPoint(graphics, 8, 8, ((1 << 7) & format) != 0);
		drawPoint(graphics, 8, 7, ((1 << 6) & format) != 0);

		for (int i = 0; i < 7; i++) {
			drawPoint(graphics, 8, 20 - i, ((1 << (14 - i)) & format) != 0);
		}
		drawPoint(graphics, 8, 13, true);

		for (int i = 0; i < 9; i++) {
			drawPoint(graphics, 20 - i, 8, ((1 << i) & format) != 0);
		}
	}

	private void drawMask(Graphics graphics) {
		// TODO Try a mask with lowest penalty
		// Now always use 011: (i+j) mod 3 = 0
		// 100: ((i div 2) + (j div 3)) mod 2 = 0
		// 101: (i j) mod 2 + (i j) mod 3 = 0
		int maskMode = model.getMaskType();
		for (Block block : model.getBlocks()) {
			for (Point point : block.getPoints()) {
				switch (maskMode) {
				case 0:
					if ((point.x + point.y) % 2 == 0) {
						drawPoint(graphics, point.x, point.y,
								!bitmap[point.x][point.y]);
					}
					break;
				case 1:
					if ((point.x) % 2 == 0) {
						drawPoint(graphics, point.x, point.y,
								!bitmap[point.x][point.y]);
					}
					break;
				case 2:
					if ((point.y) % 3 == 0) {
						drawPoint(graphics, point.x, point.y,
								!bitmap[point.x][point.y]);
					}
					break;
				case 3:
					if ((point.x + point.y) % 3 == 0) {
						drawPoint(graphics, point.x, point.y,
								!bitmap[point.x][point.y]);
					}
					break;
				case 4:
					if ((point.x / 2 + point.y / 3) % 2 == 0) {
						drawPoint(graphics, point.x, point.y,
								!bitmap[point.x][point.y]);
					}
					break;
				case 5:
					if ((point.x * point.y % 2) + (point.x * point.y % 3) == 0) {
						drawPoint(graphics, point.x, point.y,
								!bitmap[point.x][point.y]);
					}
					break;
				case 6:
					if (((point.x * point.y % 2) + (point.x * point.y % 3) % 2) == 0) {
						drawPoint(graphics, point.x, point.y,
								!bitmap[point.x][point.y]);
					}
					break;
				case 7:
					if (((point.x * point.y % 3) + ((point.x + point.y) % 2) % 2) == 0) {
						drawPoint(graphics, point.x, point.y,
								!bitmap[point.x][point.y]);
					}
					break;
				default:
					throw new IllegalArgumentException(
							"Unrecognized Mask Pattern");
				}
			}
		}
	}

	private void drawData(Graphics graphics) throws IOException {
		int currentByte = -1;
		Iterator<Block> blocks = model.getBlocks().iterator();
		while ((currentByte = model.getInputStream().read()) != -1) {
			Block block = blocks.next();
			if (null != block)
				for (int i = 0; i < 8; i++) {
					Point current = block.getPoint(i);
					drawPoint(graphics, current.x, current.y,
							((1 << 7 - i) & currentByte) > 0);
				}
		}

	}

	private void drawTimingPattern(Graphics graphics) {
		// Vertical
		for (int i = 8; i < model.getSize() - 8; i++) {
			drawPoint(graphics, 6, i, i % 2 == 0);
		}
		// Horizontal
		for (int i = 8; i < model.getSize() - 8; i++) {
			drawPoint(graphics, i, 6, i % 2 == 0);
		}
	}

	protected void drawFinderPattern(Graphics graphics) {

		// Left Top
		drawSquare(graphics, 0, 0, 7, true);
		drawSquare(graphics, 1, 1, 5, false);
		drawSquare(graphics, 2, 2, 3, true);

		// Left Bottom
		drawSquare(graphics, 0, 14, 7, true);
		drawSquare(graphics, 1, 15, 5, false);
		drawSquare(graphics, 2, 16, 3, true);

		// Right Top
		drawSquare(graphics, 14, 0, 7, true);
		drawSquare(graphics, 15, 1, 5, false);
		drawSquare(graphics, 16, 2, 3, true);
	}

	protected void drawSquare(Graphics graphics, int x, int y, int width,
			boolean black) {
		graphics.setColor(black ? darkColor : lightColor);
		graphics.fillRect(x * getPixelPerLine(), y * getPixelPerLine(), width
				* getPixelPerLine(), width * getPixelPerLine());

		// graphics.fillRoundRect(x * getPixelPerLine(), y * getPixelPerLine(),
		// width * getPixelPerLine(), width * getPixelPerLine(), 3, 3);
		for (int i = x; i < x + width; i++) {
			for (int j = y; j < y + width; j++) {
				if (i < bitmap.length && j < bitmap[i].length)
					bitmap[i][j] = black;
			}
		}
	}

	protected void drawPoint(Graphics graphics, int x, int y, boolean black) {
		graphics.setColor(black ? darkColor : lightColor);
		graphics.fillRect(x * getPixelPerLine(), y * getPixelPerLine(),
				getPixelPerLine(), getPixelPerLine());
		// graphics.fillRoundRect(x * getPixelPerLine(), y * getPixelPerLine(),
		// getPixelPerLine(), getPixelPerLine(), 3, 3);
		bitmap[x][y] = black;
	}

	public int getPixelPerLine() {
		return pixelPerLine;
	}

	public void setPixelPerLine(int pixelPerLine) {
		this.pixelPerLine = pixelPerLine;
	}

}
