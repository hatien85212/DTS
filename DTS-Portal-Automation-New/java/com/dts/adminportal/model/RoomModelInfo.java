package com.dts.adminportal.model;

import java.util.Hashtable;

public class RoomModelInfo {

	public static final String DISPLAY_MODEL  = "//*[@id='display-model']";
	public static final String EDIT  = "//*[@id='edit-model']";
	public static final String PUBLISH_STATUS  = "//*[@id='publication-status']";
	public static final String UUID  = "//*[@id='entity-uuid']";
	public static final String SALESFORCE_ID  = "//*[@id='salesforce-id']";
	public static final String DISPLAY_NAME  = ".//*[@id='mcRoom-name']";
	public static final String ROOM_MODEL_ID  = "//*[@id='router-routeid']";
	public static final String NOTES  = "//*[@id='description']";
	public static final String ROUTER_TUNING  = "//*[@id='router-tuning']";
	public static final String VIEW_PUBLISHED_VERSION = ".//*[@id='view-publish-model']";
	//public static final String POPUP_OK  = "html/body/div[6]/div[2]/a[2]";

	public static final Hashtable<String,String> getElementsInfo(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("model", DISPLAY_MODEL);
		data.put("publish status", PUBLISH_STATUS);
		data.put("uuid", UUID);
		data.put("salesforceid", SALESFORCE_ID);
		data.put("display name",DISPLAY_NAME);
		data.put("room model id", ROOM_MODEL_ID);
		data.put("notes",NOTES);
		data.put("router tuning", ROUTER_TUNING);
		return data;
	}
}
