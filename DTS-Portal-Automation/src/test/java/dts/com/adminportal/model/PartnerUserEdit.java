package dts.com.adminportal.model;

import java.util.ArrayList;
import java.util.Hashtable;

public class PartnerUserEdit {
	public static final String GIVEN_NAME = "//*[@id='firstName']";
	public static final String FAMILY_NAME = "//*[@id='lastName']";
	public static final String TITLE = "//*[@id='title']";
	public static final String EMAIL = "//*[@id='email']";
	public static final String CODE = "//*[@id='countryCode']";
	public static final String PHONE = "//*[@id='phone']";
	public static final String BRAND_PERMISSION = "//*[@id='view-brand-permission']/option";
	public static final String SITE_PRIVILEGES = "//*[@id='SitePrivileges-table']";

	public static final Hashtable<String, String> getHash(){
		Hashtable<String, String> user = new Hashtable<String, String>();
		user.put("firstName", GIVEN_NAME);
		user.put("lastName", FAMILY_NAME);
		user.put("title", TITLE);
		user.put("email", EMAIL);
		user.put("code", CODE);
		user.put("phone", PHONE);
		user.put("brand", BRAND_PERMISSION);
		user.put("site privileges", SITE_PRIVILEGES);
		return user;
	}
	
	public static final String SAVE = "//*[@id='edit-save-account']";
	public static final String CANCEL = "//*[@id='cancel-new-account']";
	public static final ArrayList<String> getActions(){
		ArrayList<String> actions = new ArrayList<String>();
		actions.add(SAVE);
		actions.add(CANCEL);
		
		return actions;
	}
}
