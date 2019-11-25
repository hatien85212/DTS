package com.dts.adminportal.model;

import java.util.Hashtable;

public class UserEdit {
	public static final String GIVEN_NAME = "//*[@id='firstName']";
	public static final String FAMILY_NAME = "//*[@id='lastName']";
	public static final String TITLE = "//*[@id='title']";
	public static final String COMPANY = "//*[@id='user-company']";
	public static final String EMAIL = "//*[@id='email']";
	public static final String CODE = "//*[@id='countryCode']";
	public static final String PHONE = "//*[@id='phone']";
	public static final String BRAND_PERMISSION = "//*[@id='view-brand-permission']/option";
	public static final String SAVE = "//*[@id='edit-save-account']";
	public static final String CANCEL = "//*[@id='cancel-new-account']";
	
	public static final String BRAND_PRIVILEGES = "//*[@id='site-privileges']/div[1]/div";
	public static final String SITE_PRIVILEGES_TABLE= "//*[@id='SitePrivileges-table']";
	public static final String DTS_SITE_PRIVILEGES_TABLE = "//*[@id='DTSSitePrivileges-table']";

	public static final Hashtable<String, String> getHash() {
		Hashtable<String, String> user = new Hashtable<String, String>();
		user.put("firstName", GIVEN_NAME);
		user.put("lastName", FAMILY_NAME);
		user.put("title", TITLE);
		user.put("email", EMAIL);
		user.put("code", CODE);
		user.put("phone", PHONE);
		user.put("privileges", SITE_PRIVILEGES_TABLE);
		user.put("save", SAVE);
		user.put("cancel", CANCEL);
		return user;
	}

}
