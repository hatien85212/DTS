package com.dts.adminportal.model;

import java.util.ArrayList;
import java.util.Hashtable;

public class PageLogin {
	public static final String btnLogoutXpath = PageHome.btnLogout;
	public static final String btnSubmitLogoutXpath = PageHome.submitLogout;
	public static final String lblUser = PageHome.lbLogout;
	public static final String urlForgot = "/forgot";
	public static final String loginError ="Invalid email or password.";
	public static final String urlIcon = "http://dts.com/";
	
	// forgot pass
	public static final String xpath_forgot_sign_in_info = "//*[@id='frmlogin']/div[5]/div/a";
	public static final String xpath_username = "//*[@id='username']";
	public static final String xpath_password = "//*[@id='password']";
	public static final String xpath_login_btn = "//*[@id='frmlogin']/div[4]/div/button";
	

	public static final ArrayList<String> getListElementOnLogin() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(xpath_username);
		arrayList.add(xpath_password);
		arrayList.add(xpath_login_btn);
		arrayList.add(xpath_forgot_sign_in_info);
		arrayList.add(PageHome.logoImgHome);
		return arrayList;
	}
	
	public static final String LOGO = "html/body/div[1]/div/div/a/img";
	public static final String LABEL = "//*[@id='first-time-saap-form-signin']/h3";
	public static final String LABEL_EMAIL = "//*[@id='first-time-saap-form-signin']/div[2]/label";
	public static final String USERNAME = "//*[@id='username']";
	public static final String LABLE_PASSWORD = "//*[@id='first-time-saap-form-signin']/div[3]/label";
	public static final String PASSWORD = "//*[@id='password']";
	public static final String CONFIRM_PASSWORD = "//*[@id='confirmpassword']";
	public static final String SIGN_IN = "//*[@type='submit']";
	public static final String FORGOT = "//*[@id='frmlogin']/div[5]/div/a";
	public static final String ERROR_MESSAGE = "//*[@id='frmlogin']/div[1]/p";
	
	
	// login
	public static final String LABEL_login = "//*[@id='frmlogin']/h3[contains(@class,'form-signin-heading')]";
	public static final String LABEL_EMAIL_Login = "//*[@id='frmlogin']/div[2]/label";
	public static final String LABLE_PASSWORD_login = "//*[@id='frmlogin']/div[3]/label";
	
	public static final Hashtable<String, String> getHash(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("logo", LOGO);
		data.put("label", LABEL_login);
		data.put("label email", LABEL_EMAIL_Login);
		data.put("username", USERNAME);
		data.put("label password", LABLE_PASSWORD_login);
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
	
	public static enum errMessage {
		Cannot_active_account("Cannot activate the account. The link was expired."),  //0
		Cannot_recognize_this_email("Cannot activate the account. We don't recognize this email."),
//		"Cannot activate the account. The link may be invalid or expired",
		Cannot_reset_password("Cannot reset password. We don't recognize this email."),
//		"Cannot reset password. The link may be invalid or expired",
		Password_does_not_matched("Password does not matched"),  //2
		Invalid_email_or_password("Invalid email or password."),
		At_least_1_partnership_type("At least 1 partnership type is required for a company"),  //4
		Cannot_reset_password_link_expired("Cannot reset password. The link was expired"),
		At_least_1_license_production_type("At least 1 Licensed Product is required for a company");
		
		private final String mes;

		errMessage (String mes) {
			this.mes = mes;
		}

		public String getName() {
			return this.mes;
		}
	};
	
	
}
