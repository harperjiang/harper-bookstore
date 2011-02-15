/**
 * 
 */
package org.harper.frm.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple implementation of <code>ITextData</code>
 * 
 * @author Harper Jiang
 * @version 1.0 Jun 15,2009
 * @since frm.component 1.0
 * @see IDataModel
 * @see ITextData
 */
public class SimpleTextData implements ITextData {

	private Map<String, String> properties;

	private String text;

	public SimpleTextData() {
		super();
		properties = new HashMap<String, String>();
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
