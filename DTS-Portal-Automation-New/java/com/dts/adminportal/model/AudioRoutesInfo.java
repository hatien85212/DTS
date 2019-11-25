package com.dts.adminportal.model;

import java.util.Hashtable;

public class AudioRoutesInfo {
	
	public static final String DISPLAY_MODEL  = "//*[@id='display-model']";
	public static final String EDIT  = "//*[@id='edit-model']";
	public static final String PUBLISH_STATUS  = "//*[@id='publication-status']";
	public static final String UUID  = "//*[@id='entity-uuid']";
	public static final String SALESFORCE_ID  = "//*[@id='salesforce-id']";
	public static final String ALL_DISPLAY_NAME  = "//*[@id='router-localname']";
	public static final String DEFAULT_DISPLAY_NAME  = "//*[@id='router-localname']/span[1]";
	public static final String MCROOM_NAME  = "//*[@id='mcRoom-name']";
	public static final String NOTES  = "//*[@id='description']";
	public static final String AUDIO_ROUTE_ID  = "//*[@id='router-routeid']"; 
	public static final String INPUT_SPECIFICATION  = "//*[@id='input-specifications-div']";
	public static final String ROUTER_TUNING  = "//*[@id='router-tuning']";
	public static final String IMAGE_CATALOG  = "//*[@id='router_image']";

	public static final Hashtable<String, String> getElementsInfo(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("model", DISPLAY_MODEL);
		data.put("publish status", PUBLISH_STATUS);
		data.put("uuid", UUID);
		data.put("salesforceid", SALESFORCE_ID);
		data.put("name", DEFAULT_DISPLAY_NAME);
		data.put("audio route id", AUDIO_ROUTE_ID);
		data.put("input specification", INPUT_SPECIFICATION);
		data.put("router tuning", ROUTER_TUNING);
		data.put("image catalog", IMAGE_CATALOG);
		return data;
	}
	
	public static final Hashtable<String, String> getElementsDetail(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("publsh status", PUBLISH_STATUS);
		data.put("uuid", UUID);
		data.put("salesforceid", SALESFORCE_ID);
		data.put("name", MCROOM_NAME);
		data.put("audio route id", AUDIO_ROUTE_ID);
		data.put("notes", NOTES);
		data.put("router tuning", ROUTER_TUNING);
		return data;
	}


}
