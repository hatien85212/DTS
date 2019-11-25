package com.dts.adminportal.model;

import java.util.Hashtable;

public class AddCompany {
	
	public static final String SALEFORCE_ID = "//input[@type='text' and contains(@id,force-id)]";
	public static final String BTN_PARTNER_TYPE = "//*[@id='partner-type-select']";
	public static final String PARTNER_TYPE_MESSAGE = "//*[@id='partner-type-msg']";
	public static final String btn_Date = "//*[@id='expDate']";
	public static final String DATEPICKER_TABLE = "//*[@class='datepicker-days']";
	public static final String OFFICIAL_CORP_NAME = "//*[@id='official-corp-name']";
	public static final String ADDRESS1 = "//*[@id='company-address1']";
	public static final String ADDRESS_ERROR_MESSAGE = "//*[@id='company-address1-div-msg']";
	public static final String ADDRESS2 = "//*[@id='company-address2']";
	public static final String ADDRESS3 = "//*[@id='company-address3']";
	public static final String CITY = "//*[@id='company-city']";
	public static final String STATE = "//*[@id='company-state']";
	public static final String ZIP = "//*[@id='company-zip']";
	public static final String BTN_COUNTRY = "//*[@id='company-country']";
	public static final String PARTNERSHIPS = "//*[@id='company-partnership']";
	public static final String PARTNERSHIPS_TABLE = "//*[@id='company-partnership-value']";
	public static final String ADD = "//*[@id='add-company-partnership-btn']";
	public static final String SAVE = "//*[@id='save-new-company']";
	public static final String CANCEL_EDIT = "//*[@id='cancel-edit-company']";
	public static final String CANCEL = "//*[@class='cancel-new-company']";
	
	
	public static final String PARTNERSHIP_LIST[] = {
		"Audio Accessories", 
		"Devices Supporting Headphone:X Playback", 
		"Apps Integrating Headphone:X APIs"
	};
	
	
	public static final String PARTNER_TYPE[] = {
		"Partner", 
		"Non-Partner", 
	};
	public static final Hashtable<String, String> getHash(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("id", SALEFORCE_ID);
		data.put("type", BTN_PARTNER_TYPE);
		data.put("date", btn_Date);
		data.put("name", OFFICIAL_CORP_NAME);
		data.put("address", ADDRESS1);
		data.put("city", CITY);
		data.put("state", STATE);
		data.put("zip", ZIP);
		data.put("country", BTN_COUNTRY);
		data.put("partnerships", PARTNERSHIPS);
		data.put("add", ADD);
		data.put("save", SAVE);
		return data;
	}
}
