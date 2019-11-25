package com.dts.adminportal.model;

import java.util.Hashtable;



public class RoomModelEdit {
	
	public static final String DISPLAY_MODEL  = "//*[@id='display-model']";
	public static final String SAVE  = "//*[@id='save_action']";
	public static final String CANCEL  = "//*[@id='cancel_action']";
	public static final String UUID  = "//*[@id='entity-uuid']";
	public static final String SALESFORCE_ID  = "//*[@id='input-salesforceid']";
	public static final String DISPLAY_NAME  = "//*[@id='display-name']";
	public static final String ROOM_MODEL_ID  = "//*[@id='router-routeid']";
	public static final String NOTES  = "//*[@id='description-input']";
	public static final String ROUTER_TUNING  = "//*[@id='edit-route-form']/fieldset/div[9]";
	public static final String ERROR_MESSAGE  = "//*[@id='display-error-msg']";
	public static final String ADD_TUNING  = "//*[@id='add-tuning-material-btn']/span";
	public static final String INVALID_ERR = "//*[@id='tuning-fileupload--1']/table/tbody/tr[2]/td/p";
	public static final String RETRY_ICON = "//*[@id='tuning-fileupload--1']/table/tbody/tr[1]/td[2]/img";
	public static final String DELETE_TUNING = "//*[contains(@id,'tuning-fileupload-')]/table/tbody/tr[1]/td[2]/img";
	public static final String TUNING_INFO_CONTAINER = "//*[contains(@id,'tuning-fileupload-')]/table/tbody/tr[2]/td";

	public static final Hashtable<String,String> getElementsInfo(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("model", DISPLAY_MODEL);
		data.put("uuid", UUID);
		data.put("salesforceid", SALESFORCE_ID);
		data.put("display name",DISPLAY_NAME);
		data.put("room model id", ROOM_MODEL_ID);
		data.put("notes",NOTES);
		data.put("router tuning", ROUTER_TUNING);
		return data;
	}
	
	public static enum error_Mess{
		Name_Required("Name is required"),
		Name_255characterslimit("Name exceeds 255 characters limit"),
		Invalid_Tuningfile("! Invalid file");
		
		private final String mess;
		
		private error_Mess(String mess){
			this.mess = mess;
		}
		
		public String getName(){
			return this.mess;
		}
		
		
	}


}
