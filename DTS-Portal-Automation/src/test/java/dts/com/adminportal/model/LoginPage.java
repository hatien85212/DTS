package dts.com.adminportal.model;

import java.util.ArrayList;
import java.util.Hashtable;

public class LoginPage {
	public static final String LOGO = "/html/body/div/div/a/img";
	public static final String LABEL = "/html/body/div/form/h3";
	public static final String LABEL_EMAIL = "/html/body/div/form/div[2]/label";
	public static final String USERNAME = "//*[@id='username']";
	public static final String LABLE_PASSWORD = "/html/body/div/form/div[3]/label";
	public static final String PASSWORD = "//*[@id='password']";
	public static final String CONFIRM_PASSWORD = "//*[@id='confirmpassword']";
	public static final String SIGN_IN = "//*[@type='submit']";
	public static final String FORGOT = "//*[@id='frmlogin']/div[5]/div/a";
	public static final String ERROR_MESSAGE = "//*[@id='frmlogin']/div[1]/p";
	
	public static final Hashtable<String, String> getHash(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("logo", LOGO);
		data.put("label", LABEL);
		data.put("label email", LABEL_EMAIL);
		data.put("username", USERNAME);
		data.put("label password", LABLE_PASSWORD);
		data.put("password", PASSWORD);
		data.put("sign in", SIGN_IN);
		data.put("forgot", FORGOT);
		return data;
	}
	
	public static final ArrayList<String> getActivationInfoPage(){
		ArrayList<String> data = new ArrayList<String>();
		data.add(LOGO);
		data.add(LABEL);
		data.add(USERNAME);
		data.add(PASSWORD);
		data.add(CONFIRM_PASSWORD);
		data.add(SIGN_IN);
		return data;
	}
	
	public static final String[] errMessage = {
		"Cannot activate the account. The link may be invalid or expired",
		"Cannot reset password. The link may be invalid or expired",
		"Password does not matched",
		"Invalid email or password."
	};
	
	
}
