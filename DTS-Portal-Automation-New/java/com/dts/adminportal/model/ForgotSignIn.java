package com.dts.adminportal.model;

import java.util.Hashtable;

public class ForgotSignIn {
	public static final String LOGO = "html/body/div[1]/div[1]/a/img";
	public static final String TITLE = "html/body/div[1]/div[2]/h3";
	public static final String INSTRUCT = "/html/body/div/div[2]/div/label";
	
	public static final Hashtable<String, String> getHash(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("logo", LOGO);
		data.put("label", TITLE);
		data.put("label", INSTRUCT);
		return data;
	}
	
	public static final String TITLE_CONTENT="Headphone:X Portal";
	public static final String INSTRUCTION_CONTENT= "If you've forgotten your sign in information, "
			+ "please contact your company's internal Headphone:X coordinator. "
			+ "He or she will be able to provide additional assistance.";
}
