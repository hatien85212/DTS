package com.dts.adminportal.model;

import java.util.ArrayList;
import java.util.Hashtable;

public class AddCompany {
	
	public static final String SALEFORCE_ID = "//input[@type='text' and contains(@id,force-id)]";
	public static final String BTN_PARTNER_TYPE = "//*[@id='partner-type-select']";
	public static final String PARTNER_TYPE_MESSAGE = "//*[@id='partner-type-msg']";
	public static final String btn_Date = "//*[@id='expDate']";
	public static final String DATEPICKER_TABLE = "//*[@class='datepicker-days']";
	public static final String OFFICIAL_CORP_NAME = "//*[@id='official-corp-name']";
	public static final String ADDRESS1 = "//*[@id='company-address1']";
	public static final String INSADD1 = "//*[@id='company-address1-div']";
	public static final String ADDRESS_ERROR_MESSAGE = "//*[@id='company-address1-div-msg']";
	public static final String ADDRESS2 = "//*[@id='company-address2']";
	public static final String INSADD2 = "//*[@id='company-address2-div']";
	public static final String ADDRESS3 = "//*[@id='company-address3']";
	public static final String INSADD3 = "//*[@id='company-address3-div']";
	public static final String CITY = "//*[@id='company-city']";
	public static final String STATE = "//*[@id='company-state']";
	public static final String ZIP = "//*[@id='company-zip']";
	public static final String BTN_COUNTRY = "//*[@id='company-country']";
	public static final String PARTNERSHIPS = "//*[@id='company-partnership']";
	public static final String LICENSEE_PRODUCT = "//*[@id='partner-licensed-product-select']";
	public static final String PARTNERSHIPS_TABLE = "//*[@id='company-partnership-value']";
	public static final String LISENSEE_PRODUCT_TABLE = "//*[@id='partner-licensed-product-value']";
	public static final String LISENSEE_PRODUCT_DETAIL = "//*[@id='partner-licensed-product-value']/li/span";
	public static final String ADD = "//*[@id='add-company-partnership-btn']";
	public static final String ADD_LICENSEE_PRODUCT = "//*[@id='add-licensed-product-btn']";
	public static final String SAVE = "//*[@id='save-new-company']";
	public static final String CANCEL_EDIT = "//*[@id='cancel-edit-company']";
	public static final String CANCEL = "//*[@class='cancel-new-company']";
	public static final String PROCESS_STEP_2 = "//*[@id='process-step-2']";
	
	public static final String USER_PANEL = "//*[@id='user-panel']/div/form/fieldset/div[2]/ul/li[2]/a";
	public static final String ADD_NEW_USER_PANEL = "//*[@id='tab']/a";
	public static final String DTS_USER_LABEL = "//*[@id='dtsUser']/label";
	public static final String EMAIL_DTS_FILTER = "//*[@id='emailDtsFilter']";
	public static final String COMBO_BOX = "//*[@id='ui-id-1']";
	public static final String COMBO_BOX_USER = "//*[@id='ui-id-1']/li[1]";
	public static final String SELECTED_EMAIL = "//*[@id='selected-email-placeholder']";
	public static final String ICON_REMOVE = "//*[@id='selected-email-placeholder']/div/div/div[2]";
	public static final String COMPANY_NAME = "//*[@id='user-company']/span";
	
	public static final String COMPLETED = "COMPLETED";
	public static final String LICENSEE_CONTROL = "//*[@id='licenseeDeviceEnabled']";
	
	public static final ArrayList<String> getUserTabInfo(){
		ArrayList<String> info = new ArrayList<String>();
		info.add(DTS_USER_LABEL);
		info.add(EMAIL_DTS_FILTER);
		return info;
	}
	
	
	public enum Partnership_List {
		Audio_Accessories("Audio Accessories"), 
		Devices_Supporting_Headphone("Devices Supporting Headphone:X Playback"), 
		Apps_Integrating_Headphone("Apps Integrating Headphone:X APIs");
		
		private final String name;

		Partnership_List(String name) {
			this.name = name;
		}
		public String getName() {
			return this.name;
		}
	};
	public enum Licensee_Product_List {
		HPX_LOW("DTS Audio Processing & HPX Low"), 
		HPX_MEDIUM("DTS:X Premium & HPX Medium"), 
		HPX_HIGH("DTS:X Ultra & HPX High");
			
		private final String name;

		Licensee_Product_List(String name) {
			this.name = name;
			}

		public String getName() {
			return this.name;
		}
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
