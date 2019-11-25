package com.dts.adminportal.model;

import java.util.ArrayList;
import java.util.Hashtable;

public class PartnerUserMgmt {
	public static final String FIRSTNAME = "//*[@id='firstName']";
	public static final String LASTNAME = "//*[@id='lastName']";
	public static final String TITLE = "//*[@id='user-title']";
	public static final String EMAIL = "//*[@id='user-email']/a";
	public static final String COUNTRY_CODE = "//*[@id='user-panel']/div/table[2]/tbody/tr/td[2]/span/span";
	public static final String PHONE = "//*[@id='user-panel']/div/table[2]/tbody/tr/td[3]/span/span";
	public static final String STATUS = "//*[@id='user-status']";
	public static final String SITE_PRIVILEGES = "//*[@id='user-panel']/div/div";
	public static final String SITE_PRIVILEGES_TABLE = "//*[@id='SitePrivileges-table']";
	
	public static final Hashtable<String, String> getElementsInfo(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("firstname", FIRSTNAME);
		data.put("lastname", LASTNAME);
		data.put("title", TITLE);
		data.put("email", EMAIL);
		data.put("code", COUNTRY_CODE);
		data.put("phone", PHONE);
		data.put("status", STATUS);
		data.put("site privileges", SITE_PRIVILEGES);
		return data;
		
	}
	
	public static final String EDIT = "//*[@id='edit-account']";
	public static final String DELETE_ACCOUNT = "//*[@id='delete-account']";
	public static final String RESET_PASSWORD = "//*[@id='reset-password']";
	public static final String CHANGE_PASSWORD = "//*[@id='change-password']";
	public static final ArrayList<String> getActions(){
		ArrayList<String> actions = new ArrayList<String>();
		actions.add(EDIT);
		actions.add(DELETE_ACCOUNT);
		actions.add(RESET_PASSWORD);
		return actions;
	}
	public static final ArrayList<String> getUserOwnActions(){
		ArrayList<String> actions = new ArrayList<String>();
		actions.add(EDIT);
		actions.add(CHANGE_PASSWORD);
		return actions;
	}
}
