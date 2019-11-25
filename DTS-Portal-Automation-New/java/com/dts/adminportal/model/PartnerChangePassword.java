package com.dts.adminportal.model;

import java.util.ArrayList;
import java.util.Hashtable;
public class PartnerChangePassword {
	public static final String OLD_PASSWORD = "//*[@id='old-password']";
	public static final String NEW_PASSWORD = "//*[@id='new-password']";
	public static final String RETYPE_PASSWORD  = "//*[@id='retype-password']";
	public static String INPUT_OLD_PASSWORD = "dts999";
	public static String INPUT_NEW_PASSWORD = "dts111";
	public static String INPUT_RETYPE_PASSWORD = "dts000";
	

	public static final Hashtable<String, String> getHash(){
		Hashtable<String, String> changepassword = new Hashtable<String, String>();
		changepassword.put("old password",OLD_PASSWORD );
		changepassword.put("new password", NEW_PASSWORD);
		changepassword.put("retype password", RETYPE_PASSWORD);
		return changepassword;
	}
	public static final Hashtable<String, String> getPage(){
		Hashtable<String, String> changepassword = new Hashtable<String, String>();
		changepassword.put("old password",OLD_PASSWORD );
		
		return changepassword;
	}
	
	public static final String SAVE = "//*[@id='save-password-action']";
	public static final String CANCEL = "//*[@id='cancel-change-password']";
	//TODO below xpath should be moved to another general model page when refactoring
	public static final String GLOBAL_ALERT = ".//*[@id='global-alert']/span";
	
	public static final ArrayList<String> getActions(){
		ArrayList<String> actions = new ArrayList<String>();
		actions.add(SAVE);
		actions.add(CANCEL);
		return actions;
	}
	public static final String requires[] = {
		"//*[@id='old-password-msg']",
		"//*[@id='new-password-msg']",

		"//*[@id='retype-password-msg']",	
		
		};
	public static String[] CHANGING_PASSWORD_MESSAGE ={
		"Old password is required.",
		"Password is required.",
		"Re-type New Password does not match New Password.",
		"The Old Password is not matched!"
	};
	
}
