package com.dts.adminportal.model;

import java.util.ArrayList;

public class ActiveBrand {
	public static final String DOWNLOAD_REPORT_BRAND = "//*[@id='download-report-brand']";
	public static final String TITLE_ACTIVE_BRAND = "//*[@id='active-brand-part']/div[1]/div[1]/span";
	public static final String ACTIVE_BRAND_TABLE_INFO = "//*[@id='active-brand-table_info']";
	public static final String ACTIVE_BRAND_THREAD = "//*[@id='active-brand-table']/thead/tr";
	
	
	public static String ACTIVE_BRAND = "Active Brands";
	
	
	
	// Active Brands header
	public static final String COMPANY = "Company";
	public static final String BRAND = "Brand";
	public static final String PRODUCTS = "Products";
	public static final String LAST_UPDATED = "Last Updated";
	
	
	public static final ArrayList<String> getHeaderActiveBrandsTableDtsUser(){
		ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add(COMPANY);
			arrayList.add(BRAND);
			arrayList.add(PRODUCTS);
			arrayList.add(LAST_UPDATED);
			return arrayList;
	 }
}
