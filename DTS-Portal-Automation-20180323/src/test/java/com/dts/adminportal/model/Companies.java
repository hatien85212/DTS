package com.dts.adminportal.model;

import java.util.ArrayList;



public class Companies {
	public static enum option {	
		All_Active("All Active"), //0
		Recently_Added("Recently Added"), 
		Pending_Invitations("Pending Invitations"), //2
		Non_Partners("Non-Partners"), 
		Watch_List("Watch List"), //4
		Contract_Ending_Soon("Contract Ending Soon"), 
		Inactive_for_90_Days("Inactive for 90 Days"), //6
		Excessive_Aging("Excessive Aging"), 
		Suspended("Suspended");  //8
				
		private final String stt;

		option (String stt) {
			this.stt = stt;
		}

		public String getName() {
			return this.stt;
		}
		
		public static String[] getNames() {
			option[] option = values();
			String[] result = new String[option.length];
			for (int i = 0; i < option.length; i++) {
				result[i] = option[i].getName();
			}
			return result;
		}
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
	
	public enum Header {
		COMPANY("Company"), STATUS("Status"), LAST_ACTIVE_DATE("Last Active Date"),
		TIME_ELAPSED("Time Elapsed");
		private final String name;

		Header(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public static String[] getNames() {
			Header[] names = values();
			String[] result = new String[names.length];
			for (int i = 0; i < names.length; i++) {
				result[i] = names[i].getName();
			}
			return result;
		}
	}
	
	// table body
	public static final String TABLE_BODY = "//*[@id='CompanyListTable']/tbody";
	
	
	
	
	
	
	
	
	
	
}
