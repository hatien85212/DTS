package com.dts.adminportal.model;

import java.util.ArrayList;

public class PromotionList {
	
	public static final String PROMOTION_TABLE = "//*[@id='promo_table']";
	public static final String PROMOTION_TABLE_INFO = "//*[@id='promo_table_info']";
	public static final String TABLE_PAGINATE = "//*[@id='promo_table_paginate']";
	public static final String BRAND_FILTER = "//*[@id='brand-list']";
	public static final String STATUS_FILTER = "//*[@id='status-list']";
	public static final String ACTION_MODULE = "//*[@id='list-promition-action']";
	public static final String ADD_PROMO = "//*[@id='list-promition-action']/span/a";
	public static final String CANCEL = "//*[@id='create-new-promition-action']/span/a[2]";
	
	public static ArrayList<String> getElementInfo(){
		ArrayList<String> data = new ArrayList<String>();
		data.add(PROMOTION_TABLE);
		data.add(PROMOTION_TABLE_INFO);
		data.add(TABLE_PAGINATE);
		data.add(BRAND_FILTER);
//		data.add(STATUS_FILTER);
		return data;
	}
	
	public static String[] columnHeaders = {
		"Promo ID",
		"Brand",
		"Product Name",
		"Target",
		"Published"
	};
	
	

}
