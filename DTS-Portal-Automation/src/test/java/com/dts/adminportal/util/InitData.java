package com.dts.adminportal.util;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;

import dts.com.adminportal.model.Constant;

public class InitData {
	public final static Hashtable<String, String> initAccessory(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("company", Constant.FRENDS);
		data.put("brand", "FRENDSfasdf");
		data.put("id", RandomStringUtils.randomNumeric(9));
		String displayName = "Test"+RandomStringUtils.randomNumeric(4);
		data.put("name", displayName);
		data.put("number", RandomStringUtils.randomNumeric(5));
		data.put("upc", RandomStringUtils.randomNumeric(5));
		data.put("type", "In-Ear");
		data.put("date", "");
		data.put("aliases", "Test ALIASES");
		data.put("description", "Test DESCRIPTION");
		data.put("add tunning", "");
		data.put("img", "");
		data.put("add marketing", "");
		return data;
	}
}
