package dts.com.adminportal.model;

import java.util.ArrayList;
import java.util.Hashtable;

public class DeviceEdit {
	
	// Product
	public static final String SALESFORCE_ID = "//*[@id='salesforceId']";
	public static final String COMPANY = "//*[@id='companies']";
	public static final String BRAND = "//*[@id='brand']";
	public static final String NAME = "//*[@id='name']";
	public static final String TYPE = "//*[@id='type']";
	public static final String OS = "//*[@id='device-form']/fieldset/div[8]/div/div";

	// Headphone:X Licensing Info
	public static final String DCC_SECRET = "//*[@id='device-form']/fieldset/fieldset/div/div[2]/div/span";
	public static final String INIT_KEY_NAME = "//*[@id='licensing-keyname']";
	public static final String INIT_KEY_VALUE = "//*[@id='licensing-keyvalue']";
	
	// Audio Routes
	public static final String ADD_DEFAULT_AUDIO_ROUTE = "//*[@id='add-tuning-material-btn']/span";
	public static final String ADD_AUDIO_ROUTE_CONTAINER = "//*[@id='defaulttuning-material-div']";
	public static final String UPLOADED_DEFAULT_AUDIO_ROUTE = "//*[@id='uploaded-defaulttuning-material']";
	public static final String DELETE_AUDIO_ROUTE_ICON = "//img[contains(@onclick,'confirmDeleteTuningFile')]";
	public static final String ANOTHER_ROUTE = "//*[@id='another_route']";
	public static final String ADD_CUSTOM_TUNING = "//*[@id='add-custom-tuning-btn']";
	public static final String AUDIO_ROUTE_TYPE = "//*[@id='audioroutetype']";
	public static final String AUDIO_ROUTE = "//*[@id='audioroute']";
	public static final String UPLOADED_CUSTOM_TUNNING_NAME = "//*[@id='another_route']/div[1]/a[@name='custom-tuning-media']";
	public static final String DELETE_CUSTOM_TUNNING_ICON = "//*[@id='another_route']/div[1]/a[@class='btn btn-link btn-delete-tuning-link']";
	public static final String AUDIO_ROUTE_UPLOAD_MESSAGE = "//*[contains(@id,'tuning-fileupload')]/table/tbody/tr[2]/td/p/span";


	// Featured Accessory Promotions
	public static final String PROMO_SLOT = "//*[@id='numPromoSlots']";
	public static final String GLOBAL_PROMOTIONS_CONTAINER = "//div[starts-with(@class,'has-switch')]";
	public static final String GLOBAL_PROMOTIONS_SWITCH = "//div[starts-with(@class,'has-switch')]//span[1]";
	
	// Device Promotions
	public static final String PROMOTION_CONTAINER = "//*[@id='promotion_container']";
	public static final String PROMOTION_1 = "//*[@id='0']/div/input[2]";
	public static final String DELETE_1 = "//*[@id='0']/div/a";
	
	// Action Module
	public static final String ACTION_MODULE = "//*[@id='add-device-action']";
	public static final String SAVE = "//*[@id='btnSaveRecord']";
	public static final String CANCEL = "//*[@id='add-device-action']/a[2]";
	
	// Title default display if company name = ''
	public static final String TITLE_DEFAULT = "//*[@id='device-form']/fieldset/div[1]/span[1]";
	public static final String DEVICE_TILLE = "//*[@id='device-form']/fieldset/div[1]";
	public static final String TUNING_MESSAGE = "//*[@id='model-tuning-files-msg']";
		
	// Default audio route
	public static final String DELETE_DEFAULT_AUDIO_ROUTE = "//*[@id='idnull-null']/a[2]";
	public static final String DEFAULT_AUDIO_ROUTE_FILE = "//*[@id='930']";

	public static final Hashtable<String, String> getHash(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("id", SALESFORCE_ID);
		data.put("type", TYPE);
		data.put("company", COMPANY);
		data.put("brand", BRAND);
		data.put("name", NAME);
		data.put("os", OS);
		data.put("license",INIT_KEY_NAME);
		data.put("audio route", ADD_DEFAULT_AUDIO_ROUTE);
		data.put("custom tuning", ADD_CUSTOM_TUNING);
		data.put("promotion slot",PROMO_SLOT);
		data.put("global promotion", GLOBAL_PROMOTIONS_SWITCH);
		data.put("save", SAVE);
		return data;
	}
	
	public static final String types[] = {
		"- Select -",
		"Device",
		"App"
	};
	
	public static final String OS_LIST = "//*[@id='platform-list']";
	public static final String os[] = {
		"Select all that apply", 
		"Android", 
		"iOS", 
		"Windows Phone", 
		"Windows Desktop", 
		"Linux", 
		"Other"
	};
	
	public static final String routes[] = {
		"- Select -", 
		"Line-out0 - Line Out", 
		"Bluetooth0 - Bluetooth", 
		"Attached0 - Internal Speakers (Portrait)", 
		"Attached1 - Internal Speakers (Landscape)", 
		"Attached2 - Phone Earpiece", 
		"Combo0 - Dual Audio"
	};
	
	public static final String Standard_Accessories[] = {
		"- Select -", 
		"Over-Ear Headphones", 
		"Earbuds", 
		"Earpiece", 
		"External Speaker", 
		"Car Audio", 
	};
	
	public static final String requires[] = {
		"Type is required", // 0
		"Company is required",
		"Brand is required", // 2
		"Name is required",
		"Key is required" // 4
	};
	public static ArrayList<String> getErroMessageXpath(){
		ArrayList<String> list = new ArrayList<String>();
		list.add("//*[@id='type-error-message']");
		list.add("//*[@id='partner-error-message']");
		list.add("//*[@id='brand-error-message']");
		list.add("//*[@id='name-error-message']");
		list.add("//*[@id='licensing-keyname-error-message']");
		return list;
	}
	
	public static final String DEFAULT_AUDIO_ERROR_MESSAGE = "//*[@id='default-audio-error-message']";
	public static final String Audio_Routes[] = {
		"Default Audio Route",
		"Custom Tuning"
	};
	
	
}
