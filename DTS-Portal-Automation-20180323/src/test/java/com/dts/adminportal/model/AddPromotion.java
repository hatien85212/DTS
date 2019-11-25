package com.dts.adminportal.model;

import java.util.Hashtable;

public class AddPromotion {
	
	public static final String PAGE_TITLE = "//*[@class='page-heading']";
	public static final String DTS_TRACKING_ID = "//*[@id='dts-tracking-id']";
	public static final String PRODUCT = "//*[@id='accessoryDtsId']";
	public static final String SALESFORCE_ID = "//*[@id='salesforceId']";
	public static final String PROMOTION_TYPE = "//*[@id='promotionType']";
	public static final String DEVICE_BRAND = "//*[@id='device-brand']";
	public static final String DEVICE_NAME = "//*[@id='device']";
	public static final String ACTION_MODULE = "//*[@id='create-new-promition-action']";
	public static final String SAVE = ".//*[@id='create-new-promition']";
	public static final String SAVE_EDITMODE = "//*[@id='edit-promition']";
	public static final String CANCEL = "//*[@id='create-new-promition-action']/span/a[2]";
	
	// Product Edit
	public static final String PRODUCT_BRAND_NAME = "//*[@data-bind='text: productBrandName']";
	public static final String PRODUCT_NAME_EDIT = "//*[@data-bind='text: productName']";
	public static final String PRODUCT_NAME = "html/body/div[3]/div[1]/div/div[11]/div/div[2]/a";
	public static final String PRODUCT_TYPE = "//*[@data-bind='text: productType']";
	public static final String PRODUCT_THUMBNAIL = "//img[@class='promotion-product-thumbnail']";
	public static final String PRODUCT_UDID = "//*[@id='promotion-form']/fieldset/div[8]/div[2]/span[2]";

	public static Hashtable<String,String> getHash(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("name", PRODUCT);
		data.put("salesforceid", SALESFORCE_ID);
		data.put("promotion type", PROMOTION_TYPE);
		data.put("device brand", DEVICE_BRAND);
		data.put("device name", DEVICE_NAME);
		data.put("save", SAVE);
		return data;
	}

	public static Hashtable<String,String> getElementInfo(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("accessory", PRODUCT);
		data.put("salesforceid", SALESFORCE_ID);
		data.put("promotion type", PROMOTION_TYPE);
		data.put("action module", ACTION_MODULE);
		data.put("save", SAVE);
		data.put("cancel", CANCEL);
		return data;
	}


}
