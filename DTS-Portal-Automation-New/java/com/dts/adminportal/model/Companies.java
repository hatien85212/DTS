package com.dts.adminportal.model;

import java.util.ArrayList;

public class Companies {
	public static final String option[] = {	
											"All Active", 
											"Recently Added", 
											"Pending Invitations", 
											"Non-Partners", 
											"Watch List", 
											"Contract Ending Soon", 
											"Inactive for 90 Days", 
											"Excessive Aging", 
											"Suspended"
										  };
	public static final String LABEL = "//*[@id='company-header']";
	public static final String REPORT = "//*[@id='company-filter-view']/span[1]";
	public static final String FIRST_COMPANY = "//*[@id='company-filter-view']/span[1]";
	public static final String FILTER = "//*[@id='companyFilterSelect']";
	public static final String TABLE = "//*[@id='CompanyListTable']";
	public static final String TABLE_INFO = "//*[@id='CompanyListTable_info']";
	public static final String PAGINATE = "//*[@id='CompanyListTable_paginate']";
	public static final String PAGES = "//*[@id='CompanyListTable_paginate']/span";
	public static final String FIRST = "//*[@id='CompanyListTable_first']";
	public static final String PREVIOUS = "//*[@id='CompanyListTable_previous']";
	public static final String NEXT = "//*[@id='CompanyListTable_next']";
	public static final String LAST = "//*[@id='CompanyListTable_last']";
	public static final String ACTION_MODULE = "//*[@id='create-new-company-action']";
	public static final String ADD_COMPANY = "//*[@id='create-new-company']";
	
	public static final ArrayList<String> getListElements() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(FILTER);
		arrayList.add(TABLE);
		arrayList.add(PAGINATE);
		arrayList.add(TABLE_INFO);
		return arrayList;
	}
	
	// table body
	public static final String TABLE_BODY = "//*[@id='CompanyListTable']/tbody";
	
	
	
	
	
	
	
	
	
	
}
