package org.harper.frm.gui.swing.freechart;

import java.util.Collection;

import org.harper.frm.gui.swing.manager.ComponentBinding;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DefaultPieDataset;

public class ChartBinding extends ComponentBinding {

	private Dataset dataSet;

	public ChartBinding(Dataset dataset, String attribute) {
		super();
		setAttribute(attribute);
		setDataSet(dataset);
	}

	public Dataset getDataSet() {
		return dataSet;
	}

	private DatasetChangeListener changeListener = new DatasetChangeListener() {
		public void datasetChanged(DatasetChangeEvent event) {
			// DO NOTHING BY NOW
		}
	};

	public void setDataSet(Dataset dataSet) {
		this.dataSet = dataSet;
		dataSet.addChangeListener(changeListener);
	}

	@Override
	public void setValue(Object value) {
		super.setValue(value);
		dataSet.removeChangeListener(changeListener);
		// Translate data, data should be a collection of CategoryData
		if (null != value) {
			if (getDataSet() instanceof DefaultCategoryDataset) {
				DefaultCategoryDataset dcd = (DefaultCategoryDataset) getDataSet();
				Collection<CategoryData> datas = (Collection<CategoryData>) value;
				for (CategoryData data : datas)
					dcd.setValue(data.getY(), data.getCategory(), data.getX());
			}
			if (getDataSet() instanceof DefaultPieDataset) {
				DefaultPieDataset dpd = (DefaultPieDataset) getDataSet();
				Collection<PieData> pieDatas = (Collection<PieData>) getValue();
				for (PieData pieData : pieDatas)
					dpd.setValue(pieData.getKey(), pieData.getValue());
			}
		}
		dataSet.addChangeListener(changeListener);
	}
}
