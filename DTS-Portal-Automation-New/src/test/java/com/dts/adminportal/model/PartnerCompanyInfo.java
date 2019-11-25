package com.dts.adminportal.model;

import java.util.ArrayList;

public class PartnerCompanyInfo extends CompanyInfo{
	public static final ArrayList<String> getListXpath() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(NAME);
		arrayList.add(COMPANY_INFO);
		arrayList.add(CORP_NAME);
		arrayList.add(CORP_ADDRESS);
		arrayList.add(HEADPHONE_X_PARTNERSHIPS);
		arrayList.add(PARTNERSHIPS);
		arrayList.add(PRIMARY_PARTNER_CONTACT);
		arrayList.add(CONTACT_NAME);
		arrayList.add(EMAIL);
		arrayList.add(PHONE);
		arrayList.add(BRAND_LIST);
		return arrayList;
	}
	
	// 
	public static final String ACTIONS = "//*[@id='addCompanyActionPlaceholder']";
	public static final String MODEL_ALLOCATION_INFO[] = {
															"Credits:",
															"In Use:",
															"Balance:",
															"Current Credit:"
	};
}
