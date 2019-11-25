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
	public static enum requires {
		old_password("//*[@id='old-password-msg']"),
		new_password("//*[@id='new-password-msg']"),
		retype_password("//*[@id='retype-password-msg']");
		
		private final String msg;

		requires (String msg) {
			this.msg = msg;
		}

		public String getName() {
			return this.msg;
		}
	};
	public static enum CHANGING_PASSWORD_MESSAGE {
		Old_password_is_required("Old password is required."),
		Password_is_required("Password is required."),
		Retype_New_Password_not_match("Re-type New Password does not match New Password."),
		Old_Password_Not_Match("The Old Password is not matched!");
		
		private final String message;

		CHANGING_PASSWORD_MESSAGE (String message) {
			this.message = message;
		}

		public String getName() {
			return this.message;
		}
	};
	
}
