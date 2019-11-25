package com.dts.adminportal.model;

import java.util.ArrayList;

public class PublishedProduct {
	public static final String DOWNLOAD_REPORT_PRODUCT = "//*[@id='download-report-published-product']";
	public static final String TITLE_PUBLISHED_PRODUCT = "//*[@id='published-product-part']/div[1]/div[1]/span";
	public static final String PUBLISHED_PRODUCTS_THREAD = "//*[@id='published-product-table']/thead/tr";
	public static final String PUBLISHED_PRODUCT_TABLE_INFO = "//*[@id='published-product-table_info']";
	public static final String BRAND_LIST = "//*[@id='brand-list']";
	
	public static String PUBLISHED_PRODUCT = "Published Products";
	
	
	// Published Products header
	public static final String PICTURE = "Picture";
	public static final String PRODUCT = "Product";
	public static final String BRAND = "Brand";
	public static final String EAN = "EAN";
	public static final String UPC = "UPC";
	public static final String PUBLISHED = "Published";
	
	public static final String MODEL_NUMBER = "Model Number";
	public static final String BLUETOOTH_DEVICE = "Bluetooth Device Name";
	public static final String PRODUCT_TYPE = "Product Type";
	public static final String SPECIFICATION = "Input Specification";
	public static final String NOISE_CANCEL = "Noise Cancelling";
	public static final String HP_RATING = "Headphone:X Tuning Rating";
	public static final String HP_INSIDE = "Headphone:X Inside";
	public static final String RELEASE_DATE = "Release Date";
	public static final ArrayList<String> getHeaderPublishedProductsTableDtsUser(){
		ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add(PICTURE);
			arrayList.add(PRODUCT);
			arrayList.add(BRAND);
			arrayList.add(EAN);
			arrayList.add(UPC);
			arrayList.add(PUBLISHED);
			return arrayList;
	 }
	
	public static final ArrayList<String> allHeaderPublishedProduct(){
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(EAN);
		arrayList.add(UPC);
		arrayList.add(MODEL_NUMBER);
		arrayList.add(BLUETOOTH_DEVICE);
		arrayList.add(PRODUCT_TYPE);
		arrayList.add(SPECIFICATION);
		arrayList.add(NOISE_CANCEL);
		arrayList.add(HP_RATING);
		arrayList.add(HP_INSIDE);
		arrayList.add(RELEASE_DATE);
		return arrayList;
	};
		
}
