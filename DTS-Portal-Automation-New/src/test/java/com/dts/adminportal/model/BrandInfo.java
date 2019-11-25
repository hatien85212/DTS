package com.dts.adminportal.model;

import java.util.ArrayList;

public class BrandInfo {
	public static final String NAME = "//*[@id='brandName']/span";
	public static final String EDIT_BRAND = "//*[@id='edit-company-brand']";
	public static final String BRAND_NAME_LIST = "//*[@id='brandName']";
	public static final String COMPANY_NAME = "//*[@id='cancel-company-brand']";
	public static final String BRAND_TAG_LINE = "//*[@id='brandTagLine']";
	public static final String CONSUMER_BRAND_ALIASES = "//*[@id='consumerAlias']";
	public static final String WEBSITE = "//*[@id='webSite']";
	public static final String BRAND_OVERVIEW = "//*[@id='brandOverview']";
	public static final String COPYRIGHT = "//*[@id='copyrightAndTrademarkNotice']";
	public static final String BRANDLOGO_TABLE = "//*[@id='brandLogoTable']";
	public static final String ACTION_MODULE = "//*[@id='create-new-user-action']";
	public static final String DELETE_LINK = "//*[@id='delete-company-brand']";
	public static final String NAME_BRAND = "//*[@id='brandName']";
	public static final String SAVE = "//*[@id='create-company-brand']";
	public static final String BRAND_LOGO_250 = "//*[@id='logo124ImageShow']/div/a/img";
	public static final String BRAND_LOGO_500 = "//*[@id='logo256ImageShow']/div/a/img";
	public static final String BRAND_LOGO_1000 = "//*[@id='logo512ImageShow']/div/a/img";
	public static final String LIGHTBOX_STYLE_IMAGE = "//img[@class = 'lb-image']";
	public static final String LIGHTBOX_CLOSE ="//*[@id='lightbox']/div[2]/div/div[2]/a";
	public static final String DTS_TRACKING_ID = "//*[@id='brandDtsID']";
	
	public static final ArrayList<String> getListXpath(){
		ArrayList<String> list = new ArrayList<String>();
		list.add(NAME);
		list.add(EDIT_BRAND);
		list.add(BRAND_NAME_LIST);
		return list;
	}
	
	public static final ArrayList<String> getBrandInfo(){
		ArrayList<String> list = new ArrayList<String>();
		list.add(NAME);
		list.add(BRAND_TAG_LINE);
		list.add(CONSUMER_BRAND_ALIASES);
		list.add(WEBSITE);
		list.add(BRAND_OVERVIEW);
		list.add(COPYRIGHT);
		return list;
	}
	
	public static final ArrayList<String> getAllField(){
		ArrayList<String> list = new ArrayList<String>();
		list.add(NAME);
		list.add(BRAND_NAME_LIST);
		list.add(COMPANY_NAME);
		list.add(BRAND_TAG_LINE);
		list.add(CONSUMER_BRAND_ALIASES);
		list.add(WEBSITE);
		list.add(BRAND_OVERVIEW);
		list.add(COPYRIGHT);
		list.add(BRANDLOGO_TABLE);
		return list;
	}
}


