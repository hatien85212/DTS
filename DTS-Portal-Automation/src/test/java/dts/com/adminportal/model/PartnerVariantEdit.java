package dts.com.adminportal.model;

import java.util.ArrayList;

public class PartnerVariantEdit {
	public static final String TUNING_FILE = "/html/body/div[3]/div[1]/div/div/div[1]/form/fieldset/div[8]/div";

	public static final ArrayList<String> variantDetail() {
		ArrayList<String> arrayLists = new ArrayList<String>();
		arrayLists.add(TUNING_FILE);
		return arrayLists;
	}
}
