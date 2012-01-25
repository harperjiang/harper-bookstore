package org.harper.frm.gui.code.qrcode.codemodel;

public enum ECLevel {
	L(1),M(0),Q(3),H(2);
	
	ECLevel(int code) {
		this.code = code;
	}
	
	private int code;
	
	public int getCode(){
		return code;
	}
}
