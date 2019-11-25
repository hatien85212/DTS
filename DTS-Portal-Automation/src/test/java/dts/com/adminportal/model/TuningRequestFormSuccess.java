package dts.com.adminportal.model;

import java.util.ArrayList;

public class TuningRequestFormSuccess {
	
	
	public static final String PRODUCT_LINK ="//*[@id='model-tuning-action-display']/a/span";
	public static final String FORM_NAME = "//*[@id='model-tuning-shipping']";
	public static final String SUCCESS_MESSAGE = "//*[@id='add-accessory']/fieldset/div[1]";
	public static final String ACCESSORY_INFO = "//*[@id='add-accessory']/fieldset/div[2]";
	//public static final String PRODUCT_LINK = "//*[@id='model-tuning-action-display']/a";
	
	public static final String FROM_NAME = "//*[@id='add-accessory']/fieldset/div[2]/span[1]";
	public static final String FROM_COMPANY = "//*[@id='add-accessory']/fieldset/div[2]/span[2]";
	public static final String MODEL_NUMBER = "//*[@id='add-accessory']/fieldset/div[2]/span[3]";
	public static final String UUID ="//*[@id='add-accessory']/fieldset/div[2]/span[4]";
	public static final String EAN = "//*[@id='add-accessory']/fieldset/div[2]/span[5]";
	public static final String UPC = "//*[@id='add-accessory']/fieldset/div[2]/span[6]";
	public static final String ADDITIONAL_INFO = "//*[@id='add-accessory']/fieldset/div[2]/span[7]";
		
	
	public static final ArrayList<String> getSuccessPageInfo() {
		ArrayList<String> arrayLists = new ArrayList<String>();
		//arrayLists.add(TUNING_PROCESS);
		arrayLists.add(FORM_NAME);
		arrayLists.add(PRODUCT_LINK);
		arrayLists.add(SUCCESS_MESSAGE);
		arrayLists.add(ACCESSORY_INFO);
		return arrayLists;
	}

}
