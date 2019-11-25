package com.dts.adminportal.model;

import java.util.ArrayList;
import java.util.Hashtable;

public class UserMgmt {
	public static final String USER_INFO_PAGE_TITLE = "//*[@id='user-panel']/div/div[1]";
	public static final String FIRST_NAME = "//*[@id='firstName']";
	public static final String LAST_NAME = "//*[@id='lastName']/span";
	public static final String COMPANY = "//*[@id='partnerName']/span";
	public static final String TITLE = "//*[@id='user-title']/span";
	public static final String EMAIL = "//*[@id='user-email']/a";
	public static final String COUNTRY_CODE = "//*[@id='user-panel']/div/table/tbody/tr/td[2]/span/span";
	public static final String PHONE = "//*[@id='user-panel']/div/table/tbody/tr/td[3]/span/span";
	public static final String STATUS = "//*[@id='user-status']";
	public static final String BRAND_PERMISSION = "//*[@id='brand-permission-detail']/li/span";
	public static final String SITE_PRIVILEGES = "//*[@id='SitePrivileges-table']/thead/tr/th[1]";
	public static final String SITE_PRIVILEGES_TABLE = "//*[@id='SitePrivileges-table']";
	public static final String DTS_SITE_PRIVILEGES_TABLE = "//*[@id='DTSSitePrivileges-table']";
	public static final String ACTION_MODULE = "//*[@id='show-user-action']";
	public static final String FULLNAME = "//*[@id='fullName']/span";

	
	public static final Hashtable<String, String> getElementsInfo(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("fullname", FULLNAME);
		data.put("company", COMPANY);
		data.put("title", TITLE);
		data.put("email", EMAIL);
		data.put("phone", PHONE);
		data.put("status", STATUS);
		data.put("brand_permission", BRAND_PERMISSION);
		data.put("site privileges", SITE_PRIVILEGES_TABLE);
		return data;
	}
	
	public static final ArrayList<String> getUserInfo(){
		ArrayList<String> data = new ArrayList<String>();
		data.add (FIRST_NAME);
		data.add (LAST_NAME);
		data.add(COMPANY);
		data.add(TITLE);
		data.add(EMAIL);
		data.add (COUNTRY_CODE);
		data.add(PHONE);
		data.add(STATUS);
		return data;
		
	}
	
	public static final String EDIT = "//*[@id='edit-account']";
	public static final String DELETE_ACCOUNT = "//*[@id='delete-account']";
	public static final String RESET_PASSWORD = "//*[@id='reset-password']";
	public static final String RESTORE = "//*[@id='restore-account']";
	public static final String SUSPEND = "//*[@id='suspend-account']";
	public static final String CHANGE_PASSWORD = "//*[@id='change-password']";
	public static final String CANCEL_CONFIRM = "//div[6]/div[2]/a[.='Cancel']";
	public static final String DELETE_CONFIRM = "//div[6]/div[2]/a[.='DELETE']";
	public static final ArrayList<String> getActions(){
		ArrayList<String> actions = new ArrayList<String>();
		actions.add(EDIT);
		actions.add(DELETE_ACCOUNT);
		actions.add(RESET_PASSWORD);
		actions.add(SUSPEND);
		return actions;
	}
	public static final ArrayList<String> actionLinks(){
		ArrayList<String> links = new ArrayList<String>();
		links.add("Edit");
		links.add("Reset Password");
		links.add("Delete");
		links.add("Suspend");
		links.add("Restore");
		return links;
	}
}
