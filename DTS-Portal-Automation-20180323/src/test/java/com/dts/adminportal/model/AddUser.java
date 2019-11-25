package com.dts.adminportal.model;

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
	public static final String BRAND_PRIVILEGES = ".//*[@id='site-privileges']/div[1]/div";
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
		user.put("company",BTN_COMPANY);
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
	
	public static final String GIVEN_NAME_MESSAGE = "//*[@id='firstNameSpanMsg']";
	public static final String FAMILY_NAME_MESSAGE = "//*[@id='lastNameSpanMsg']";
	public static final String EMAIL_MESSAGE = "//*[@id='emailSpanMsg']";
	public static final String COMPANY_MESSAGE = "//*[@id='user-companySpanMsg']";
	public static final String PHONE_MESSAGE = "//*[@id='phoneSpanMsg']";
	
	public static enum messages{
		Required_Company("Company is required."),
		Account_Existed("! Account with this email address already exists"),
		Invalid_Format_Email("! Email is not correct format same to example@mail.com."),
		Success("Success"),
		Success_Email_Invite("We have sent an email inviting this person to sign in and verify their account."),
		Success_Email_Info("If there is a delay, ask the user to check their junk mail or spam folder.");
		
		private String mess;
		
		private messages(String mess) {
			this.mess =  mess;
		}
		
		public String getName(){
			return this.mess;
		}
	}
	
	public static enum New_email_element {
		NewEmail("NewEmail"),
		Mailinator("@mailinator.com");
		
		private String mail;
		
		private New_email_element (String mail) {
			this.mail =  mail;
		}
		
		public String getName(){
			return this.mail;
		}
	}; 	
}
