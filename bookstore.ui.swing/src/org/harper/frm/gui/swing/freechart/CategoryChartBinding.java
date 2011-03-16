package org.harper.frm.gui.swing.freechart;

import java.util.Collection;

import org.harper.frm.gui.swing.manager.ComponentBinding;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;

public class CategoryChartBinding extends ComponentBinding {

	private DefaultCategoryDataset dataSet;

	public CategoryChartBinding(DefaultCategoryDataset dataset, String attribute) {
		super();
		setAttribute(attribute);
		setDataSet(dataset);
	}

	public CategoryDataset getDataSet() {
		return dataSet;
	}

	private DatasetChangeListener changeListener = new DatasetChangeListener() {
		@Override
		public void datasetChanged(DatasetChangeEvent event) {
			// DO NOTHING BY NOW
		}
	};

	public void setDataSet(DefaultCategoryDataset dataSet) {
		this.dataSet = dataSet;
		dataSet.addChangeListener(changeListener);
	}

	@Override
	public void setValue(Object value) {
		super.setValue(value);
		dataSet.removeChangeListener(changeListener);
		// Translate data, data should be a collection of CategoryData
		if (null != value) {
			Collection<CategoryData> datas = (Collection<CategoryData>) value;
			for (CategoryData data : datas)
				dataSet.setValue(data.getY(), data.getCategory(), data.getX());
		}
		dataSet.addChangeListener(changeListener);
	}
}
