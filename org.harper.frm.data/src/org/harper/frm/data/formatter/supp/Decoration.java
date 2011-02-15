package org.harper.frm.data.formatter.supp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Decoration {

	public static final String FEATURE_COLUMN = "IFontSupporter.Feature.Column";

	public static final String FEATURE_TH = "IFontSupporter.Feature.TableHeader";

	private DecorationInfo basic;

	private Map<String, List<DecorationInfo>> features;

	public Decoration() {
		basic = new DecorationInfo();
		features = new HashMap<String, List<DecorationInfo>>();
	}

	public DecorationInfo getBasic() {
		return basic;
	}

	public void setFeatureInfos(List<DecorationInfo> infos, String feature,
			int index) {
		List<DecorationInfo> finfos = features.get(feature);
		if (finfos == null) {
			finfos = new ArrayList<DecorationInfo>();
			features.put(feature, finfos);
		}
		finfos.addAll(infos);
	}

	public List<DecorationInfo> getFeatureInfo(String feature) {
		return features.get(feature);
	}
	
	public DecorationInfo getFeatureInfo(String feature, int index) {
		return features.get(feature).get(index);
	}
}
