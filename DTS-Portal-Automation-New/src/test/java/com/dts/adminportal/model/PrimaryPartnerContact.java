package com.dts.adminportal.model;

import java.util.ArrayList;

public class PrimaryPartnerContact {
	public static final String LABEL = "//*[@id='user-panel']/div[1]/div[1]/form/fieldset/span";
	public static final String SAVE = "//*[@id='save-user-change']";
	public static final String CURRENT_CONTACT_PERSON  = "//*[@id='old-user-action']";
	public static final String NEW_CONTACT_PERSON  = "//*[@id='new-user-action']";
	public static final String CONTACT_LIST = "//*[@id='AdminUserListTable']";
	public static final String TOTAL_ITEM = "//*[@id='AdminUserListTable_info']";
	public static final ArrayList<String> getListPrimaryPartnerContact() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(LABEL);
		arrayList.add(SAVE);
		arrayList.add(CURRENT_CONTACT_PERSON);
		arrayList.add(NEW_CONTACT_PERSON);
		arrayList.add(CONTACT_LIST);
		return arrayList;
	}
}
