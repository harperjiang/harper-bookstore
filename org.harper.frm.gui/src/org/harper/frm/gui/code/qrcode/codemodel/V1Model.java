package org.harper.frm.gui.code.qrcode.codemodel;

import java.awt.Point;
import java.util.Arrays;

import org.harper.frm.gui.code.qrcode.draw.Block;
import org.harper.frm.gui.code.qrcode.draw.Block.VerticalDownBlock;
import org.harper.frm.gui.code.qrcode.draw.Block.VerticalUpBlock;

public class V1Model extends AbstractCodeModel {

	private Block[] blocks;

	public V1Model() {
		blocks = new Block[26];
		blocks[0] = new VerticalUpBlock(20, 20);
		blocks[1] = new VerticalUpBlock(20, 16);
		blocks[2] = new VerticalUpBlock(20, 12);
		blocks[3] = new VerticalDownBlock(18, 9);
		blocks[4] = new VerticalDownBlock(18, 13);
		blocks[5] = new VerticalDownBlock(18, 17);
		blocks[6] = new VerticalUpBlock(16, 20);
		blocks[7] = new VerticalUpBlock(16, 16);
		blocks[8] = new VerticalUpBlock(16, 12);
		blocks[9] = new VerticalDownBlock(14, 9);
		blocks[10] = new VerticalDownBlock(14, 13);
		blocks[11] = new VerticalDownBlock(14, 17);

		blocks[12] = new VerticalUpBlock(12, 20);
		blocks[13] = new VerticalUpBlock(12, 16);
		blocks[14] = new VerticalUpBlock(12, 12);

		blocks[15] = new Block(new Point[] { new Point(12, 8),
				new Point(11, 8), new Point(12, 7), new Point(11, 7),
				new Point(12, 5), new Point(11, 5), new Point(12, 4),
				new Point(11, 4) });

		blocks[16] = new VerticalUpBlock(12, 3);
		blocks[17] = new VerticalDownBlock(10, 0);

		blocks[18] = new Block(new Point[] { new Point(10, 4), new Point(9, 4),
				new Point(10, 5), new Point(9, 5), new Point(10, 7),
				new Point(9, 7), new Point(10, 8), new Point(9, 8) });

		blocks[19] = new VerticalDownBlock(10, 9);
		blocks[20] = new VerticalDownBlock(10, 13);
		blocks[21] = new VerticalDownBlock(10, 17);

		blocks[22] = new VerticalUpBlock(8, 12);
		blocks[23] = new VerticalDownBlock(5, 9);
		blocks[24] = new VerticalUpBlock(3, 12);
		blocks[25] = new VerticalDownBlock(1, 9);
	}

	public int getSize() {
		return 21;
	}

	protected int getCodewordCount() {
		return 26;
	}

	protected int getDataCodewordCount(ECLevel level) {
		switch (level) {
		case L:
			return 19;
		case M:
			return 16;
		case Q:
			return 13;
		case H:
			return 9;
		default:
			throw new IllegalArgumentException();
		}
	}

	public Iterable<Block> getBlocks() {
		return Arrays.asList(blocks);
	}

	public int getVersion() {
		return 1;
	}

}
