package com.dts.adminportal.model;

import java.util.ArrayList;

public class Reports {
	public static final String LABEL = "//*[@id='report-part']/div[1]";
	public static final String ACTIVE_BRANDS = "//*[@id='report-active-brand']";
	public static final String NUMBER_ACTIVE_BRANDS = "//*[@id='num-active-brand']";
	public static final String PUBLISHED_PRODUCTS = "//*[@id='report-published-product']";
	public static final String NUMBER_PUBLISHED_PRODUCTS = "//*[@id='num-published-product']";
	public static final String LAST_UPDATED_LABEL = "//*[@id='report-part']/div[2]/div[2]/div[3]/span[1]";
	public static final String REPORT_UPDATED = "//*[@id='report-last-updated']";

	public static String REPORTS_PAGE = "Reports";
	
	
	public static final ArrayList<String> getListInfoReport(){
		ArrayList<String> data = new ArrayList<String>();
		data.add(ACTIVE_BRANDS);
		data.add(NUMBER_ACTIVE_BRANDS);
		data.add(PUBLISHED_PRODUCTS);
		data.add(NUMBER_PUBLISHED_PRODUCTS);
		data.add(LAST_UPDATED_LABEL);
		return data;
	}
	
}
