package com.dts.adminportal.model;

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
	public static final String RETRY_DEFAULT_AUDIO_ROUTE = ".//*[@id='tuning-fileupload--1']/table/tbody/tr[1]/td[2]/a";
	public static final String ADD_AUDIO_ROUTE_CONTAINER = "//*[@id='defaulttuning-material-div']";
	public static final String UPLOADED_DEFAULT_AUDIO_ROUTE = "//*[@id='uploaded-defaulttuning-material']";
	public static final String DELETE_AUDIO_ROUTE_ICON = "//img[contains(@onclick,'confirmDeleteTuningFile')]";
	public static final String ANOTHER_ROUTE = "//*[@id='another_route']";
	public static final String ADD_CUSTOM_TUNING = "//*[@id='add-custom-tuning-btn']";
	public static final String AUDIO_ROUTE_TYPE = "//*[@id='audioroutetype']";
	public static final String AUDIO_ROUTE = "//*[@id='audioroute']";
	public static final String UPLOADED_CUSTOM_TUNNING_NAME = "//*[@id='another_route']/div[1]/a[@name='custom-tuning-media']";
	public static final String DELETE_CUSTOM_TUNNING_ICON = "//*[@id='another_route']/div[1]/a[@class='btn btn-link btn-delete-tuning-link']";
	public static final String AUDIO_ROUTE_INVALID_MESSAGE = "//*[contains(@id,'tuning-fileupload')]/table/tbody/tr[2]/td/p/span";

	// In-box Headphones
	public static final String INBOX_HEADPHONES = ".//*[@id='inboxHeadphoneContainer']/h5";
	public static final String INBOX_INSTRUCTION = ".//*[@id='inboxHeadphoneContainer']/label";
	public static final String INBOX_FIELD = ".//*[@id='inboxHeadphoneFilter']";
	public static final String INBOX_PRODUCT_NAME = ".//*[@id='inboxHeadphoneName']";
	public static final String DELETE_INBOX = ".//*[@id='deleteInboxHeadphone']";
	// Featured Accessory Promotions
	public static final String PROMO_SLOT = "//*[@id='numPromoSlots']";
	public static final String GLOBAL_PROMOTIONS_CONTAINER = "//div[starts-with(@class,'has-switch')]";
	public static final String GLOBAL_PROMOTIONS_ON = "//*[@id='globalPromo']/div/div/span[1]";
	public static final String GLOBAL_PROMOTIONS_OFF = "//*[@id='globalPromo']/div/div/span[2]";
	
	// Device Promotions
	public static final String PROMOTION_CONTAINER = "//*[@id='promotion_container']";
	public static final String INSTRUCTION_TEXT = "//*[@id='device-form']/fieldset/div[16]/label";
	public static final String PROMOTION_1 = "//*[@id='0']/div/input[2]";
	public static final String PROMOTION_2 = "//*[@id='1']/div/input[2]";
	public static final String PROMOTION_3 = "//*[@id='2']/div/input[2]";
	public static final String DELETE_1 = "//*[@id='0']/div/a";
	public static final String LABEL_7 = ".//*[@id='6']/div[1]/label";
	public static final String PROMOTION_7 = "//*[@id='6']/div[2]/input[2]";
	public static final String DELETE_7 = ".//*[@id='6']/div[2]/a";
	
	// Action Module
	public static final String ACTION_MODULE = "//*[@id='add-device-action']";
	public static final String SAVE = "//*[@id='btnSaveRecord']";
	public static final String CANCEL = "//*[@id='add-device-action']/a[2]";
	
	// Title default display if company name = ''
	public static final String TITLE_DEFAULT = "//*[@id='device-form']/fieldset/div[1]/span[1]";
	public static final String DEVICE_TILLE = "//*[@id='device-form']/fieldset/div[1]";
	public static final String TUNING_MESSAGE = "//*[@id='model-tuning-files-msg']";
	public static final String CUSTOM_TUNING_MESS = "//*[@id='custom-tuningfile-error-message']";
		
	// Default audio route
	public static final String DELETE_DEFAULT_AUDIO_ROUTE = "//*[@id='idnull-null']/a[2]";
	public static final String DEFAULT_AUDIO_ROUTE_FILE = "//*[@id='930']";
	
	// Multi-channel room models
	public static final String  MCROOM_1ST= "//*[@id='room_rodels_group']/label[1]/span";
	public static final String MCROOM_2ND = "//*[@id='room_rodels_group']/label[2]/span";
	public static final String MCROOM_3TH = "//*[@id='room_rodels_group']/label[3]/span";
	public static final String CHECK_BOX_MCROOM = "//*[@id='roommodels']";
	
	// Headphone Image Sizes
	public static final String LARGE_IMAGE = ".//*[@id='deviceImageSizes' and @value='IMAGE_LARGE']";
	public static final String MEDIUM_IMAGE = ".//*[@id='deviceImageSizes' and @value='IMAGE_MEDIUM']";
	public static final String SMALL_IMAGE = ".//*[@id='deviceImageSizes' and @value='IMAGE_SMALL']";
	public static final String HEADPHONE_IMAGE_SIZES_INSTRUCTION = ".//*[@id='device-form']/fieldset/label[2]";
	
	// Headphone Brands
	public static final String ALL_BRANDS = "//*[@id='headphoneBrandSelectionAllBrands']";
	public static final String ONLY_BRANDS = "//*[@id='headphoneBrandSelectionOnlyBrands']";
	public static final String BRAND_FILTER = "//*[@id='brandFilter']";
	public static final String FIRST_BRANDS = "//*[@id='ui-id-54']";
	
	public static final String BRAND_LABEL = "//*[@id='brand-id-icon-trash-11']/div/div[1]";
	public static final String ICON_TRASH = "//*[@id='icon-trash-11']";
	
	// Warning messages	
	public static final String TYPE_REQUIRE = "//*[@id='type-error-message']";
	public static final String COMPANY_REQUIRE = "//*[@id='partner-error-message']";
	public static final String BRAND_REQUIRE = "//*[@id='brand-error-message']";
	public static final String NAME_REQUIRE = "//*[@id='name-error-message']";
	public static final String KEY_NAME_REQUIRE = "//*[@id='licensing-keyname-error-message']";
	public static final String ROOM_MODEL_REQUIRE = "//*[@id='roommodels-error-message']";
	public static final String IMAGE_SIZE_REQUIRE = ".//*[@id='deviceimagesizes-error-message']";
	public static final String HEADPHONE_BRAND_REQUIRE = "//*[@id='brand-empty-message']";
	public static final String PROMOTION_ERROR = "//*[@id='0']/div/span[2]";
	
	public static String promotionslotlabel(int promotionslot){
		//promotion slot ID begin with 0, so when filling the slot number, the id should minus 1
		promotionslot = promotionslot -1;
		return ".//*[@id='"+promotionslot +"']/div[1]/label";
	}
	
	public static String reorder_icon(int promotionslot){
		//promotion slot ID begin with 0, so when filling the slot number, the id should minus 1
		promotionslot = promotionslot -1;
		return ".//*[@id='"+promotionslot +"']/div[1]/img";
	}
	
	public static String promotionslot(int promotionslot){
		//promotion slot ID begin with 0, so when filling the slot number, the id should minus 1
		promotionslot = promotionslot -1;
		return ".//*[@id='"+promotionslot +"'and @class='control-group ui-sortable-handle']";
		
	}
	
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
		data.put("global promotion", GLOBAL_PROMOTIONS_ON);
		data.put("MC room models", MCROOM_1ST);
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
		"Attached0 - Internal Speakers (Default)", 
		"Attached1 - Internal Speakers (Off)",
		"Attached2 - Internal Speakers (mode 1)",
		"Attached3 - Internal Speakers (mode 2)",
		"Attached4 - Internal Speakers (mode 3)",
		"Attached5 - Internal Speakers (mode 4)", 
		"Combo0 - Dual Audio"
	};
	
	public static final String Standard_Accessories[] = {
		"- Select -", 
		"Over-Ear Headphones", 
		"Earbuds", 
		"Ear-piece", 
		"External Speakers", 
		"Car Audio", 
	};
	
	public static final String requires[] = {
		"Type is required", // 0
		"Company is required",
		"Brand is required", // 2
		"Name is required",
		"Key is required" ,// 4
		"Custom Tunning File is required",
		"Headphone image sizes is required" //6
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
