package com.dts.adminportal.model;

import java.util.ArrayList;
import java.util.Hashtable;

public class CompanyInfo {
	public static final String NAME = "//*[@id='partner-name']";
	public static final String EDIT = "//*[@id='edit-company-info']";
	public static final String STATUS = "//*[@id='account-status']";
	public static final String TYPE = "//*[@id='partner-type']";
	
	public static final ArrayList<String> getListInfo() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(NAME);
		arrayList.add(STATUS);
		arrayList.add(TYPE);
		return arrayList;
	}
	
	public static final String COMPANY_INFO = "//*[@id='add-new-company-form']/fieldset/span[2]";
	public static final String TRACKING_ID = "//*[@id='dts-id']";
	public static final String DATE = "//*[@id='exp-date']";
	public static final String SALESFORCE_ID = "//*[@id='salesforce-id']";
	public static final String OFFICIAL_CORPORATE_NAME = "//*[@id='add-new-company-form']/fieldset/div[1]/span[1]";
	public static final String CORP_NAME = "//*[@id='corp-name']";
	public static final String CORP_ADDRESS = "//*[@id='corp-address']";
	public static final String ADDRESS1 = "//*[@id='add1']";
	public static final String ADDRESS2 = "//*[@id='add2']";
	public static final String ADDRESS3 = "//*[@id='add3']";
	public static final String CHANGE_PARTNER_CONTACT = "//*[@id='change-partner-contact']";
	public static final String ADD_BRAND = "//*[@id='add-brand-link']";
	public static final String PRIMARY_PARTNER_CONTACT = "//*[@id='add-new-company-form']/fieldset/div[6]/span";
	public static final String CONTACT_NAME= "//*[@id='primary-contact']";
	public static final String EMAIL = "//*[@id='primary-email']";
	public static final String PHONE = "//*[@id='primary-phone']";
	public static final String BRAND_TABLE = "//*[@id='brandListTable']";
	public static final String BRAND_LIST = "//*[@id='brandList']";
	public static final String HEADPHONE_X_PARTNERSHIPS = "//*[@id='add-new-company-form']/fieldset/div[4]/span";
	public static final String PARTNERSHIPS = "//*[@id='partnerships']";
	// Actions
	public static final String ACTION_MODULE = "//*[@id='create-new-company-action']";
	public static final String SUSPEND = "//*[@id='suspend-company']";
	public static final String RESTORE = "//*[@id='restore-company']";
	public static final String DELETE = "//*[@id='delete-company']";
	public static final String CANCEL = "//*[@id='cancel-new-company']";
	
	public static final Hashtable<String, String> getHash(){
		Hashtable<String, String> info = new Hashtable<String, String>();
		info.put("name", NAME);
		info.put("id", SALESFORCE_ID);
		info.put("phone", PHONE);
		info.put("brand", ADD_BRAND);
		info.put("email", EMAIL);
		info.put("type", TYPE);
		return info;
	}
	
	public static final ArrayList<String> getListElement() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(TRACKING_ID);
		arrayList.add(SALESFORCE_ID);
		arrayList.add(STATUS);
		arrayList.add(TYPE);
		arrayList.add(DATE);
		arrayList.add(CORP_NAME);
		arrayList.add(CORP_ADDRESS);
		arrayList.add(PARTNERSHIPS);
		arrayList.add(CONTACT_NAME);
		arrayList.add(EMAIL);
		arrayList.add(PHONE);
		arrayList.add(BRAND_LIST);
		return arrayList;
	}
	
}
