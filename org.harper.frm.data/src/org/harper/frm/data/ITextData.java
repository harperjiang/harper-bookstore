/**
 * 
 */
package org.harper.frm.data;

/**
 * Text only data model
 * 
 * @author Harper Jiang
 * @version 1.0
 * @since frm.component 1.0
 */
public interface ITextData extends IDataModel {

	public String getText();

	public void setText(String text);
}
