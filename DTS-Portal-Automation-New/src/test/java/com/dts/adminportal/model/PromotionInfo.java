package com.dts.adminportal.model;

import java.util.ArrayList;

public class PromotionInfo {
	
	// Promotion info
	public static final String PROMOTION_NAME = "//*[@id='promotionName']";
	public static final String EDIT = "//*[@id='edit-promotion']/a";
	public static final String PUBLISH_STATUS = "//*[@class='publishStatus']";
	public static final String CREATED_DATE = "//*[@class='createdOn']";
	public static final String DTS_ID = "//*[@class='dtsId']";
	public static final String SALESFORCE_ID = "//*[@class='salesforceId']";
	public static final String PROMOTION_TYPE = "//*[@class='promotionType']";
	public static final String DEVICE_NAME = "//*[@data-bind='attr:{href: deviceLink}']";
	public static final String ACTION_MODULE = "//*[@id='view-promition-action']";
	public static final String PUBLISH ="//*[@id='btnPublishRecord']";
	public static final String DELETE = ".//*[@id='btnDeleteRecord']";
	
	// Product info
	public static final String PRODUCT_BRAND_NAME = "html/body/div[3]/div/div[1]/div/div[contains(@class, 'promotion-product-view')]/div/div[1]/span[2]";
	public static final String PRODUCT_NAME = "html/body/div[3]/div/div[1]/div/div[contains(@class, 'promotion-product-view')]/div/div[2]/a";
	public static final String PRODUCT_TYPE = "html/body/div[3]/div/div[1]/div/div[contains(@class, 'promotion-product-view')]/div/div[3]/span[2]";
	public static final String PRODUCT_THUMBNAIL = "//img[@class='promotion-product-thumbnail']";
	public static final String PRODUCT_UDID = "html/body/div[3]/div/div[1]/div/div[12]/span[2]";

	
	public static ArrayList<String> getElementInfo(){
		ArrayList<String> list = new ArrayList<String>();
		list.add(PROMOTION_NAME);
		list.add(DTS_ID);
		list.add(PUBLISH_STATUS);
		list.add(CREATED_DATE);
		list.add(DTS_ID);
		list.add(SALESFORCE_ID);
		list.add(PROMOTION_TYPE);
		list.add(PRODUCT_BRAND_NAME);
		list.add(PRODUCT_NAME);
		list.add(PRODUCT_TYPE);
		list.add(PRODUCT_THUMBNAIL);
		list.add(PRODUCT_UDID);
		return list;
	}


}
