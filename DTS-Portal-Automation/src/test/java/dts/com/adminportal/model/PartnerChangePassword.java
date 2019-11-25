package dts.com.adminportal.model;

import java.util.ArrayList;
import java.util.Hashtable;
public class PartnerChangePassword {
	public static final String OLD_PASSWORD = "//*[@id='old-password']";
	public static final String NEW_PASSWORD = "//*[@id='new-password']";
	public static final String RETYPE_PASSWORD  = "//*[@id='retype-password']";
	
	

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
}
