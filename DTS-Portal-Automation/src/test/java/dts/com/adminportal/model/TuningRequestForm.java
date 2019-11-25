package dts.com.adminportal.model;

import java.util.ArrayList;

public class TuningRequestForm {
	public static final String SUBMIT = "//*[@id='requestTuningSubmit']";
	public static final String CANCEL = "//*[@id='requestTuningCancel']";
	public static final String ADDITIONALINFO = "//*[@id='additionaInfo']";
	public static final String MODEL_NAME = "//*[@id='dts-request-form']/fieldset/div[4]/span[1]";
	public static final String FORM_NAME = "//*[@id='model-tuning-shipping']";
	public static final String MESSAGE = "//*[@id='dts-request-form']/fieldset/div[1]";
	public static final String ERROR_MESSAGE = "//*[@id='agreement-message']/span[2]";
	public static final String AGREE = "//*[@id='agree']";
	public static final String ACCESSORY_INFO = "//*[@id='add-accessory']/fieldset/div[2]";
	public static final String PRODUCT_LINK ="//*[@id='model-tuning-action-display']/a/span";
	public static final String FROM_NAME = "//*[@id='dts-request-form']/fieldset/div[3]/span[1]";
	public static final String FROM_COMPANY = "//*[@id='dts-request-form']/fieldset/div[3]/span[2]";
	public static final String MODEL_NUMBER = "//*[@id='dts-request-form']/fieldset/div[4]/span[2]";
	public static final String EAN = "//*[@id='dts-request-form']/fieldset/div[4]/span[3]";
	public static final String UPC = "//*[@id='dts-request-form']/fieldset/div[4]/span[4]";
	public static final String TIP = "//*[@id='create-accessory-action']/div[2]";
	
	
	
	
		
	public static final ArrayList<String> APPROVE_TUNNING_FORM() {
		ArrayList<String> arrayLists = new ArrayList<String>();
		//arrayLists.add(TUNING_PROCESS);
		arrayLists.add(SUBMIT);
		arrayLists.add(CANCEL);
		arrayLists.add(ADDITIONALINFO);
		arrayLists.add(AGREE);
		arrayLists.add(MODEL_NAME);
		arrayLists.add(FORM_NAME);
		arrayLists.add(MESSAGE);
		return arrayLists;
	}
	

	
	public static final ArrayList<String> APPROVE_TUNNING_FORM2() {
		ArrayList<String> arrayLists = new ArrayList<String>();
		//arrayLists.add(TUNING_PROCESS);
		arrayLists.add(SUBMIT);
	//	arrayLists.add(CANCEL);
//		arrayLists.add(ADDITIONALINFO);
		arrayLists.add(AGREE);
//		arrayLists.add(MODEL_NAME);C:\Users\an.nguyen.INI\Desktop\source\report\tutorial-selenium-26-11\tutorial-selenium-21-11\tutorial-selenium\asset\tuning-based-on-GPB.zip
		
		return arrayLists;
	}
}
