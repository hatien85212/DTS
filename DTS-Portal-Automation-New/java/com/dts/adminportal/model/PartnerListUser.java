package com.dts.adminportal.model;

import java.util.ArrayList;

public class PartnerListUser {
	public static final String ADD_USER = "//*[@id='create-new-user']";
	public static final String FILTER = "//*[@id='userFilterSelect']";
	public static final String LIST_TABLE = "//*[@id='AdminUserListTable']/thead";
	public static final String TBODY = "//*[@id='AdminUserListTable']/tbody";
	public static final String FIRST = "//*[@id='AdminUserListTable_first']";
	public static final String PREVIOUS = "//*[@id='AdminUserListTable_previous']";
	public static final String NEXT = "//*[@id='AdminUserListTable_next']";
	public static final String LAST = "//*[@id='AdminUserListTable_last']";
	public static final String INFO = "//*[@id='AdminUserListTable_info']";
	public static final String ACTION = "//*[@id='create-new-user-action-header']/strong";
	public static final ArrayList<String> getAllXpath(){
		ArrayList<String> xpaths = new ArrayList<String>();
		xpaths.add(FILTER);
		xpaths.add(LIST_TABLE);
		xpaths.add(TBODY);
		xpaths.add(FIRST);
		xpaths.add(PREVIOUS);
		xpaths.add(NEXT);
		xpaths.add(LAST);
		xpaths.add(INFO);
		xpaths.add(ACTION);
		return xpaths;
	}
	public static final String columns[] = {
												"First Name",
												"Last Name",
												"Title",
												"Phone",
												"Email"
											};
	
	public static final String filters[] = {
												"All Accounts",
												"Active",
												"Invited"
											};
}
