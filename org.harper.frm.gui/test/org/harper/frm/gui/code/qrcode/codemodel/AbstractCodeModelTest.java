package org.harper.frm.gui.code.qrcode.codemodel;

import static org.junit.Assert.assertEquals;

import org.harper.frm.gui.code.qrcode.draw.Block;
import org.junit.Test;

public class AbstractCodeModelTest {

	static AbstractCodeModel model = new AbstractCodeModel(){
		public int getSize() {
			return 0;
		}
		public Iterable<Block> getBlocks() {
			return null;
		}
		@Override
		protected int getCodewordCount() {
			return 0;
		}
		@Override
		protected int getDataCodewordCount(ECLevel level) {
			return 0;
		}
		public int getVersion() {
			// TODO Auto-generated method stub
			return 0;
		}
	};
	
	@Test
	public void testGetFormat() {
		model.setEcLevel(ECLevel.M);
		model.setMaskType(5);
		assertEquals(16590,model.getFormat());
		
		model.setEcLevel(ECLevel.L);
		model.setMaskType(0);
		assertEquals(30660,model.getFormat());
	}

}
