package dts.com.adminportal.model;

import java.util.ArrayList;
import java.util.Hashtable;

public class AddUser {
	public static final String GIVEN_NAME = "//*[@id='firstName']";
	public static final String FAMILY_NAME = "//*[@id='lastName']";
	public static final String BTN_COMPANY = "//*[@id='user-company']";
	public static final String TITLE = "//*[@id='title']";
	public static final String EMAIL = "//*[@id='email']";
	public static final String CODE = "//*[@id='countryCode']";
	public static final String PHONE = "//*[@id='phone']";
	public static final String BRAND_PERMISSION = "//*[@id='view-brand-permission']/option";
	public static final String BRAND_PRIVILEGES = "//*[@id='site-privileges']/div[1]/div";
	public static final String SAVE = "//*[@class='create-new-account']";
	public static final String CANCEL = "//*[@id='cancel-new-account']";
	
	public static final Hashtable<String, String> getDTSUser(){
		Hashtable<String, String> user = new Hashtable<String, String>();
		user.put("firstName", GIVEN_NAME);
		user.put("lastName", FAMILY_NAME);
		user.put("company", BTN_COMPANY);
		user.put("title", TITLE);
		user.put("email", EMAIL);
		user.put("code", CODE);
		user.put("phone", PHONE);
		user.put("brand", BRAND_PRIVILEGES);
		user.put("save", SAVE);
		user.put("cancel", CANCEL);
		return user;
	}
	
	public static final Hashtable<String, String> getPartnerUser(){
		Hashtable<String, String> user = new Hashtable<String, String>();
		user.put("firstName", GIVEN_NAME);
		user.put("lastName", FAMILY_NAME);
		user.put("title", TITLE);
		user.put("email", EMAIL);
		user.put("code", CODE);
		user.put("phone", PHONE);
		user.put("brand", BRAND_PRIVILEGES);
		user.put("save", SAVE);
		user.put("cancel", CANCEL);
		return user;
	}
	
	public static final ArrayList<String> getUserInfo(){
		ArrayList<String> user = new ArrayList<String>();
		user.add(GIVEN_NAME);
		user.add(FAMILY_NAME);
		user.add(TITLE);
		user.add(CODE);
		user.add(PHONE);
		return user;
	}
	
	public static final String PRIVILEGES_TABLE = "//*[@id='SitePrivileges-table']";
	public static final String DTS_PRIVILEGES_TABLE = "//*[@id='DTSSitePrivileges-table']";
	public static final String DTS_PRIVILEGES[] = {
		"Approve product tunings",
		"Approve marketing information",
		"Manage audio routes",
		"Add and manage apps and devices",
		"Publish and suspend apps and devices",
		"Add and manage promotions",
		"Add and manage company accounts",
		"Add and manage DTS users"
	};
	public static final String PRIVILEGES[] = {
		"Add and manage products",
		"Publish and suspend products",
		"Can request DTS tune products",
		"Edit Company Info",
		"Edit brand info",
		"Add and manage users"
	};
	public static final String REQUIRES[] = {
		"//*[@id='firstNameSpanMsg']",
		"//*[@id='lastNameSpanMsg']",
		"//*[@id='user-companySpanMsg']",
		"//*[@id='emailSpanMsg']",
		"//*[@id='phoneSpanMsg']"
	};
	
	public static final String REQUIRES_PARTNER[] = {
		"//*[@id='firstNameSpanMsg']",
		"//*[@id='lastNameSpanMsg']",
		"//*[@id='emailSpanMsg']",
		"//*[@id='phoneSpanMsg']"
	};
	
	public static final String GIVEN_NAME_MESSAGE = "//*[@id='firstNameSpanMsg']";
	public static final String FAMILY_NAME_MESSAGE = "//*[@id='lastNameSpanMsg']";
	public static final String EMAIL_MESSAGE = "//*[@id='emailSpanMsg']";
	
	public static String[] Success_Message = {
		"Success",
		"We have sent an email inviting this person to sign in and verify their account.",
		"If there is a delay, ask the user to check their junk mail or spam folder."
	};
	
}
