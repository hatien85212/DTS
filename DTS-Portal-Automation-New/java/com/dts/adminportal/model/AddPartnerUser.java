package com.dts.adminportal.model;

import java.util.Hashtable;

public class AddPartnerUser {
	public static final String GIVEN_NAME = "//*[@id='firstName']";
	public static final String FAMILY_NAME = "//*[@id='lastName']";
	public static final String TITLE = "//*[@id='title']";
	public static final String EMAIL = "//*[@id='email']";
	public static final String CODE = "//*[@id='countryCode']";
	public static final String PHONE = "//*[@id='phone']";
	public static final String BRAND_PERMISSION = "//*[@id='view-brand-permission']/option";
	public static final String SAVE = "//*[@id='create-new-account']";
	public static final String CANCEL = "//*[@id='cancel-new-account']";
	
	public static final Hashtable<String, String> getHash(){
		Hashtable<String, String> user = new Hashtable<String, String>();
		user.put("firstName", GIVEN_NAME);
		user.put("lastName", FAMILY_NAME);
		user.put("title", TITLE);
		user.put("email", EMAIL);
		user.put("code", CODE);
		user.put("phone", PHONE);
		user.put("brand", BRAND_PERMISSION);
		user.put("brand", PRIVILEGES_TABLE);
		user.put("save", SAVE);
		user.put("cancel", CANCEL);
		return user;
	}
	
	public static final String PRIVILEGES_TABLE = "//*[@id='SitePrivileges-table']";
	public static final String PRIVILEGES[] = {
		"Add and manage accessories",
		"Publish and suspend accessories",
		"Request accessory tunings",
		"View publication credits",
		"Edit Company Info",
		"Edit brand info",
		"Add and manage users"
	};
	public static final String REQUIRES[] = {
		"//*[@id='firstNameSpanMsg']",
		"//*[@id='lastNameSpanMsg']",
		"//*[@id='emailSpanMsg']",
		"//*[@id='phoneSpanMsg']"
	};
}
