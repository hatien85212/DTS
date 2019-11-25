package com.dts.adminportal.model;

import java.util.ArrayList;

public class UsersList {
	public static final String ADD_USER = "//*[@id='create-new-user']";
	public static final String USER_INFO_TITLE="//*[@id='CompanyListTable']/tbody/tr[3]/td";
	public static final String FILTER_USER = "//*[@id='userFilterSelect']";
	public static final String FILTER_COMPANY = "//*[@id='company-list']";
	public static final String PAGINATE = "//*[@id='AdminUserListTable_paginate']";
	public static final String INDEX = "//*[@id='AdminUserListTable_paginate']/span";
	public static final String USER_TABLE = "//*[@id='AdminUserListTable']";
	public static final String TABLE_INFO = "//*[@id='AdminUserListTable_info']";
	public static final String THEAD = "//*[@id='AdminUserListTable']/thead";
	public static final ArrayList<String> getListElementUser() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(FILTER_USER);
		arrayList.add(FILTER_COMPANY);
		arrayList.add(USER_TABLE);
		arrayList.add(PAGINATE);
		arrayList.add(TABLE_INFO);
		return arrayList;
	}
	public static ArrayList<String> getListElementPartnerUser() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(FILTER_USER);
		arrayList.add(PAGINATE);
		arrayList.add(USER_TABLE);
		arrayList.add(TABLE_INFO);
		return arrayList;
	}
	public static final String theads[] = {
		"First Name",
		"Last Name",
		"Company",
		"Title",
		"Phone",
		"Email"
	};
	
	public static final String theadsPartner[] = {
		"First Name",
		"Last Name",
		"Title",
		"Phone",
		"Email"
	};
	
	public static enum company_Filter{
		All_Accounts("All Accounts"),
		Active("Active"),  
		Invited("Invited"), 
		Suspended("Suspended");
		
		private final String filter;
		
		private company_Filter(String filter) {
			this.filter =  filter;
		}
		
		public String getName(){
			return this.filter;
		}
		
		public static String[] getNames(){
			company_Filter[] companyFilter = values();
			String[] result = new String[companyFilter.length];
			for(int i=0;i<companyFilter.length;i++){
				result[i] = companyFilter[i].getName();
			}
			return result;
		}
	}
	
	public static final String ADD_USER_ACTION = "//*[@id='create-new-user-action']";
	
}
