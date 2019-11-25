package dts.com.adminportal.model;

import java.util.Hashtable;

public class AddAccessory {
	public static final String TITLE="//*[@id='add-accessory-form']/fieldset/div[1]";
	public static final String TRACKING_ID = "//*[contains(@id,'dts-tracking-id')]";
	public static final String COMPANY = "//*[@id='companyList']";
	public static final String BRAND = "//select[contains(@id,'brand')]";
	public static final String  ID = "//*[@id='salesforce-id']";
	public static final String  NAME = "//*[@id='display-name']";
	public static final String  MODEL_NUMBER = "//*[@id='display-number']";
	public static final String  EAN = "//*[@id='ean']";
	public static final String  UPC = "//*[@id='upc']";
	public static final String  LANGUAGE_DROPDOWN = "//*[@id='lang-div-container']/div/select";
	public static final String  LANGUAGE_CONTAINER = "//*[@id='lang-div-container']";
	public static final String  TYPE = "//*[@id='modelType']";
	public static final String  DATE = "//*[@id='releaseDate']";
	public static final String  ALIASES = "//*[@id='aliases']";
	public static final String  DESCRIPTION = "//*[@id='description']";
	public static final String SAVE = "//*[@id='save-accessory']";
	public static final String CANCEL = "//*[@id='cancel-accessory']";
	public static final String  NAME_ERROR_MESSAGE = "//*[@id='display-name-msg']";
	public static final String DUPLICATE_WARNING_MESSAGE = "Language is duplicated";
	public static final String LANGUAGE_DEFAULT_OPTIONS = "//*[@id='lang-div-container']/div/select/option[1]";
	public static final String EMPTY_LANGUAGE_FIELD = "//*[@id='lang-div-container']/div/input";
	public static final String OTHER_LANGUAGE_NAME = "//*[@id='lang-div-container']/div[1]/input";
	public static final String TRASH_ICON = "//*[@id='lang-div-container']/div[1]/i";
	public static final String  NOISE_CANCELLING = "//*[@id='noise-cancel']";
	public static final String  INPUT_SPECIFICATIONS = "//*[@id='input-specifications-div']";
	public static final String 	NAME_ERR = "//*[@id='display-name-msg']";
	public static final String UPC_ERROR_MESSAGE = "//*[@id='upc-msg']";
	public static final String EAN_ERROR_MESSAGE = "//*[@id='ean-msg']";
	
	//Wired connection
	public static final String CONTENT_CHANNEL_WIRED_DROPDOWN ="//*[@id='input-specifications-div']/table/tbody/tr[2]/td[2]/div";
	public static final String CONTENT_CHANNEL_BLUET_DROPDOWN ="//*[@id='input-specifications-div']/table/tbody/tr[3]/td[2]/div";
	public static final String CONNECTION_TYPE_MESSAGE ="//*[@id='display-connectiontype-msg']/span";
	public static final String  WIRED_CONTAINER  = "//*[@id='input-specifications-div']//tr[2]";
	public static final String  WIRED_CHECKBOX  = "//*[@id='checkboxWired']";
	public static final String  WIRED_DROPBOX = "//*[@id='input-specifications-div']//tr[2]//button[1]";
	public static final String  WIRED_OPTIONS  = "//*[@id='input-specifications-div']//ul";
	public static final String WIRED_MESSAGE = "//*[@id='display-channel-msg-Wired']";
	public static final String ALL_CHANNEL = "//*[@id='input-specifications-div']//li[1]";
	public static final String MONO_CHANNEL = "//*[@id='input-specifications-div']//li[2]";
	public static final String STEREO_CHANNEL = "//*[@id='input-specifications-div']//li[3]";
	public static final String BRAND_LIST="//*[@id='brandList']";
	public static final String REQUEST_TUNING_REVIEW = "//*[@id='requestTuningAction']";
	public static final String CONNECTION_TYPE_STRING = "Connection Type is required";
	public static final String INPUT_CHANNEL_ERROR_STRING ="You have to select at least an item";
	
	//Blue-tooth connection
	public static final String  BLUETOOTH_CONTAINER  = "//*[@id='input-specifications-div']//tr[3]";
	public static final String  BLUETOOTH_CHECKBOX  = "//*[@id='checkboxBluetooth']";
	public static final String  BLUETOOTH_DROPBOX  = "//*[@id='input-specifications-div']//tr[3]//button";
	public static final String BLUETOOTH_MESSAGE = "//*[@id='display-channel-msg-Bluetooth']";
	public static final String CONNECTION_REQUIRE_MESSAGE = "//*[@id='display-connectiontype-msg']";
	
	// Tuning
	public static final String TUNING_FILE_CONTAINER = "//*[@id='tuning-material-div']"; 
	public static final String  ADD_TUNNING = "//*[@id='add-tuning-material-btn']/span";
	public static final String UPLOADED_TUNING = "//*[@id='uploaded-tuning-material']//tbody";
	public static final String DELETE_UPLOADED_TUNING = "//img[contains(@onclick,'confirmDeleteTuningFile')]";
	public static final String TUNING_RATING = "//*[@id='headphone-xtuningrating']";
	public static final String TUNING_RATING_DISABLE = "//*[@id='headphone-tuning-rating']";
	public static final String HEADPHONEX_INSIDE = "//*[@id='headphone-inside']";
	public static final String HEADPHONEX_INSIDE_DISABLE = "//*[@id='headphone-tuning-inside']";
	public static final String TUNING_UPLOAD_MESSAGE = "//*[contains(@id,'tuning-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String RETRY_UPLOAD_TUNING = "//img[contains(@onclick,'retryUploadTuning')]";
	public static final String CANCEL_UPLOAD_TUNING = "//*[@id='tuning-progress']//img[@class='icon-close-icon']";
	public static final String TUNING_DRAG_DROP_AREA = "//*[@id='dropzone-tuning']"; 

	// Image - Edit
	public static final String  IMAGE_CATALOG = "//*[@id='product-images-div']";
	public static final String  SELECT_FILE_IMAGE_250 = "//*[@id='add-logo124Image-btn']/span";
	public static final String  SELECT_FILE_IMAGE_500 = "//*[@id='add-logo256Image-btn']/span";
	public static final String  SELECT_FILE_IMAGE_1000 = "//*[@id='add-logo512Image-btn']/span";
	public static final String IMAGE250_DISPLAY = "//*[@id='uploaded-logo124Image-material']/div/div[2]";
	public static final String IMAGE500_DISPLAY = "//*[@id='uploaded-logo256Image-material']/div/div[2]";
	public static final String IMAGE1000_DISPLAY = "//*[@id='uploaded-logo512Image-material']/div/div[2]";
	public static final String CATALOG_IMAGE_UPLOADER_250 ="//*[@id='logo124Image-material-div']";
	public static final String CATALOG_IMAGE_UPLOADER_500 ="//*[@id='logo256Image-material-div']";
	public static final String CATALOG_IMAGE_UPLOADER_1000 ="//*[@id='logo512Image-material-div']";
	public static final String CATALOG_IMAGE_UPLOAD_MESSAGE_250 ="//div[@id='uploaded-logo124Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String CATALOG_IMAGE_UPLOAD_MESSAGE_500="//div[@id='uploaded-logo256Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String CATALOG_IMAGE_UPLOAD_MESSAGE_1000="//div[@id='uploaded-logo512Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String CATALOG_IMAGE_UPLOAD_THUMBNAIL_250="//div[@id='uploaded-logo124Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/th/a/img";
	public static final String CATALOG_IMAGE_UPLOAD_THUMBNAIL_500="//div[@id='uploaded-logo256Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/th/a/img";
	public static final String CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000="//div[@id='uploaded-logo512Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/th/a/img";
	public static final String CATALOG_IMAGE_UPLOAD_INFO_250 = "//div[@id='uploaded-logo124Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td";
	public static final String CATALOG_IMAGE_UPLOAD_INFO_500 = "//div[@id='uploaded-logo256Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td";
	public static final String CATALOG_IMAGE_UPLOAD_INFO_1000 = "//div[@id='uploaded-logo512Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td";
	public static final String CATALOG_IMAGE_TITLE_LINK_250 ="//span[@class='maupload-logo124Image']";
	public static final String CATALOG_IMAGE_TITLE_LINK_500 ="//span[@class='maupload-logo256Image']";
	public static final String CATALOG_IMAGE_TITLE_LINK_1000 ="//span[@class='maupload-logo512Image']";
	public static final String CATALOG_IMAGE_FILE_SIZE_250 ="//div[@id='uploaded-logo124Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/span";
	public static final String CATALOG_IMAGE_FILE_SIZE_500 ="//div[@id='uploaded-logo256Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/span";
	public static final String CATALOG_IMAGE_FILE_SIZE_1000 ="//div[@id='uploaded-logo512Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/span";
	public static final String RETRY_UPLOAD_IMAGE_250 ="//div[@id='uploaded-logo124Image-material']//img[contains(@onclick, 'retryUploadImage')]";
	public static final String RETRY_UPLOAD_IMAGE_500 ="//div[@id='uploaded-logo256Image-material']//img[contains(@onclick, 'retryUploadImage')]";
	public static final String RETRY_UPLOAD_IMAGE_1000 ="//div[@id='uploaded-logo512Image-material']//img[contains(@onclick, 'retryUploadImage')]";
	public static final String CANCEL_UPLOAD_IMAGE_250 ="//*[@id='imglogo124Image-progress']//img[@class = 'icon-close-icon']";
	public static final String CANCEL_UPLOAD_IMAGE_500 ="//*[@id='imglogo256Image-progress']//img[@class = 'icon-close-icon']";
	public static final String CANCEL_UPLOAD_IMAGE_1000 ="//*[@id='imglogo512Image-progress']//img[@class = 'icon-close-icon']";
	public static final String IMAGE250_DRAG_DROP_AREA ="//*[@id='dropzone-logo124Image']";
	public static final String IMAGE500_DRAG_DROP_AREA ="//*[@id='dropzone-logo256Image']";
	public static final String IMAGE1000_DRAG_DROP_AREA ="//*[@id='dropzone-logo512Image']";

	// Marketing
	public static final String  MARKETING_MATERIAL_DIV = "//*[@id='marketing-material-div']";
	public static final String  MARKETING_FILE_CONTAINER = "//*[@id='marketing-material-section']";
	public static final String  ADD_MARKETING = "//*[@id='add-marketing-material-btn']";
	public static final String  MARKETING_METERIAL_TYPE = "//*[contains(@id,'marketingType')]";
	public static final String MARKETING_UPLOAD_MESSAGE="[contains(@id,'marketing-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String LIGHTBOX_CLOSE ="//*[@id='lightbox']/div[2]/div/div[2]/a";
	public static final String LIGHTBOX_STYLE_IMAGE = "//img[@class = 'lb-image']";
	public static final String CANCEL_UPLOAD_MARKETING = "//*[@id='marketing-progress']//img[@class = 'icon-close-icon']";
	public static final String MARKETING_DRAG_DROP_AREA = "//*[@id='dropzone-marketing']"; 
	public static final String RETRY_UPLOAD_MARKETING ="//img[contains(@onclick, 'retryUploadMarketing')]";

	
	
	
	public static final String TYPE_LIST[] = {
											"Select Type",									
											"Ear-buds", 
											"In-Ear", 
											"On-Ear", 
											"Over-Ear"
										};
	
	public static final String LANGUAGE_LIST[] = {
											"- Select -", 
											"Chinese (Simplified)", 
											"Chinese (Traditional)", 
											"French (France)",
											"German", 
											"Italian", 
											"Japanese",
											"Korean", 
											"Russian", 
											"Spanish (Spain)"
										};
	
	public static final Hashtable<String, String> getHash(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("company", COMPANY);
		data.put("brand", BRAND);
		data.put("id", ID);
		data.put("name", NAME);
		data.put("number", MODEL_NUMBER);
		data.put("ean", EAN);
		data.put("upc", UPC);
		data.put("type", TYPE);
		data.put("date", DATE);
		data.put("wired", WIRED_CONTAINER);
		data.put("bluetooth", BLUETOOTH_CONTAINER);
		data.put("aliases", ALIASES);
		data.put("description", DESCRIPTION);
		data.put("add tunning", ADD_TUNNING);
		data.put("tuning rating", TUNING_RATING);
		data.put("img1", SELECT_FILE_IMAGE_250);
		data.put("img2", SELECT_FILE_IMAGE_500);
		data.put("img3", SELECT_FILE_IMAGE_1000);
		data.put("add marketing", ADD_MARKETING);
		data.put("marketing meterial type", MARKETING_METERIAL_TYPE);
		data.put("save", SAVE);
		return data;
		
	}
	
	public static Hashtable<String, String> getElementInfo(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("company", COMPANY);
		data.put("brand", BRAND);
		data.put("id", ID);
		data.put("name", NAME);
		data.put("number", MODEL_NUMBER);
		data.put("ean", EAN);
		data.put("upc", UPC);
		data.put("type", TYPE);
		data.put("noise cancelling", NOISE_CANCELLING);
		data.put("input specifications", INPUT_SPECIFICATIONS);
		data.put("date", DATE);
		data.put("aliases", ALIASES);
		data.put("description", DESCRIPTION);
		data.put("tuning", TUNING_FILE_CONTAINER);
		data.put("image", IMAGE_CATALOG);
		data.put("marketing", MARKETING_MATERIAL_DIV);
		data.put("save", SAVE);
		data.put("cancel", CANCEL);
		return data;
	}
	
	public static final String requires[] = {
	"//*[@id='company-select-msg']",
	"//*[@id='brand-select-msg']",
	"//*[@id='display-name-msg']",
	"//*[@id='model-number-msg']", // 3
	"//*[@id='upc-msg']",
	"//*[@id='model-type-msg']",
	"//*[@id='desc-name-msg']"
	};
	public static final String SIZE_LIST[] = {
		"250x250",									
		"500x500", 
		"1000x1000",
	};
	public static final String PARTNER_SIZE_LIST[] = {
		"250x250",									
		"500x500", 
		"1000x1000",
	};
	
	public static final String DIV_STATUS[]={
		"style=\"display: block;\"",
		"style=\"display: none;\"",
	};
	
	public static final String IMG_NAME[]={
		"250x250.jpg",
		"500x500.jpg",
		"1000x1000.jpg",
		"250x250.png",
		"500x500.png",
		"1000x1000.png"
	};
	
	public static String tuningRatingOption[] = {
		"Undetermined",
		"No Certification",
		"A",
		"A+"
	};
	
	public static final String UPLOAD_FRAME_STATUS[]={
		"display: block;",
		"display: none;",
	};
	
	public static String inputSpecificationHeader[] = {
		"Connection Type",
		"Content Channel Capability"
	};
	
	public static String connectionType[] = {
		"Wired (3.5 mm)",
		"Bluetooth"
	};
	
	public static String contentChannel[] = {
		"Select all that apply",
		"1 (Single Channel), 2 (Stereo)",
		"1 (Single Channel)",
		"2 (Stereo)"
	};
	
	public static String headphoneInsideOption[] = {
		"Not Applicable (N/A)",
		"Embedded in hardware"
	};
	
	public static String eanErrMessage[] = {
		"EAN value must contain only digits.",
		"EAN value exceeds 13 characters limit.",
		"EAN must be unique"
	};
	
	public static String upcErrMessage[] = {
		"UPC value must contain only digits.",
		"UPC value exceeds 12 characters limit.",
		"UPC must be unique"
	};
	
	public static final String UPLOAD_FILE_MESSSAGE[]={
		"File upload successful",
		"Invalid file",
		"File upload canceled",
		"File upload error",
		"Automatically resize. Original 250x250 px",
		"Automatically resize. Original 500x500 px",
		"Automatically resize. Original 1000x1000 px"
	};
	
}
