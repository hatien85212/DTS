package com.dts.adminportal.model;

import java.util.ArrayList;

public class PartnerCompanyEdit extends AddCompany {
	public static final ArrayList<String> getListXpath() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(OFFICIAL_CORP_NAME);
		arrayList.add(ADDRESS1);
		arrayList.add(ADDRESS2);
		arrayList.add(ADDRESS3);
		arrayList.add(CITY);
		arrayList.add(STATE);
		arrayList.add(ZIP);
		arrayList.add(BTN_COUNTRY);
		arrayList.add(PARTNERSHIPS_TABLE);
		arrayList.add(INSADD1);
		arrayList.add(INSADD2);
		arrayList.add(INSADD3);
		return arrayList;
	}
	
	public static String INSADD1_TEXT = "Street address, P.O. Box, c/o";
	public static String INSADD2_TEXT = "Suite, unit, building, floor, etc";
	public static String INSADD3_TEXT = "Attn, other info";
}
