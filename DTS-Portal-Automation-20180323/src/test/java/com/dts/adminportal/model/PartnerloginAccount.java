package com.dts.adminportal.model;

import java.util.ArrayList;
import java.util.Hashtable;
public class PartnerloginAccount {
	public static final String FULL_NAME = "//*[@id='fullName']/span";
	//public static final String FAMILY_NAME = "//*[@id='lastName']";
	public static final String TITLE = "//*[@id='user-title']";
	public static final String EMAIL = "//*[@id='user-email']";
	public static final String CODE = "//*[@id='user-panel']/div/table/tbody/tr/td[2]/span";
	public static final String PHONE = "//*[@id='user-panel']/div/table/tbody/tr/td[3]/span/span";
	public static final String BRAND_PERMISSION = "//*[@id='brand-permission-detail']";
	//public static final String SITE_PRIVILEGES = "//*[@id='user-panel']/div/div";
	public static final String SITE_PRIVILEGES = "//*[@id='SitePrivileges-table']";
	

	
	

	public static final Hashtable<String, String> getHash(){
		Hashtable<String, String> user = new Hashtable<String, String>();
		user.put("fullname", FULL_NAME);
		user.put("title", TITLE);
		user.put("email", EMAIL);
		user.put("phone", PHONE);
		user.put("brand", BRAND_PERMISSION);
		user.put("site privileges", SITE_PRIVILEGES);
		return user;
	}
	
	public static final String EDIT = "//*[@id='edit-account']";
	public static final String CHANGE_PASSWORD = "//*[@id='change-password']";
	
	public static final ArrayList<String> getActions(){
		ArrayList<String> actions = new ArrayList<String>();
		actions.add(EDIT);
		actions.add(CHANGE_PASSWORD);
		return actions;
	}
}
