package org.harper.frm.data.formatter.supp;


public class DecorationInfo {

	public ColorBean foreground;
	
	public ColorBean background;
	
	public FontBean font;
	
	public DimensionBean dimension;
	
	public String name;
	
	public DecorationInfo() {
		super();
		foreground = new ColorBean();
		background = new ColorBean();
		font = new FontBean();
		dimension = new DimensionBean();
	}
}
