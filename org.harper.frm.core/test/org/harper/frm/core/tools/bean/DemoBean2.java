package org.harper.frm.core.tools.bean;

import java.io.Serializable;

public class DemoBean2 implements Serializable{

	private String name;
	private int value;
	private boolean abc;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isAbc() {
		return abc;
	}

	public void setAbc(boolean abc) {
		this.abc = abc;
	}

}
