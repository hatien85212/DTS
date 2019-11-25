package dts.com.adminportal.model;

import java.util.Hashtable;

public class AddVariant {
	public static final String TITLE="//*[@id='add-variant-form']/fieldset/div[1]";
	public static final String SALESFORCE_ID = "//*[@id='salesforce-id']";
	public static final String EAN = "//*[@id='ean']";
	public static final String UPC = "//*[@id='upc']";
	public static final String NAME = "//*[@id='display-name']";
	public static final String LANGUAGE_DROPDOWN = "//*[@id='lang-div-container']/div[1]/select";
	public static final String LANGUAGE_CONTAINER = "//*[@id='lang-div-container']";
	public static final String LANGUAGE_NAME_TEXT = "//*[@id='lang-div-container']/div[1]/input";
	public static final String SECOND_LANGUAGE = "//*[@id='lang-div-container']/div[2]/select";
	public static final String DESCRIPTOR = "//*[@id='descriptor']";
	public static final String RELEASE_DATE = "//*[@id='releaseDate']";
    public static final String NAME_ERROR_MESSAGE = "//*[@id='display-name-msg']";
    public static final String MODEL_TUNING_FILE = "//*[@id='model-tuning-files']";
	public static final String SAVE = "//*[@id='save-variant']";
	public static final String CANCEL = "//*[@id='cancel-addVariant']";
    public static final String TRASH_ICON="//*[@id='lang-div-container']/div[1]/i";
	public static final String INPUT_SPECIFICATIONS = "//*[@id='input-specifications']";
	public static final String UPC_ERROR_MESSAGE = "//*[@id='upc-msg']";
	public static final String EAN_ERROR_MESSAGE = "//*[@id='ean-msg']";
    
    //Wired connection
    public static final String  WIRED_CONTAINER  = "//*[@id='input-specifications-div']//tr[2]";
  	public static final String  WIRED_CHECKBOX  = "//*[@id='checkboxWired']";
  	public static final String  WIRED_DROPBOX  = "//*[@id='input-specifications-div']//tr[2]//button";
  	public static final String WIRED_MESSAGE = "//*[@id='display-channel-msg-Wired']";
  	public static final String ALL_CHANNEL = "//*[@id='input-specifications-div']//li[1]";
  	public static final String MONO_CHANNEL = "//*[@id='input-specifications-div']//li[2]";
  	public static final String STEREO_CHANNEL = "//*[@id='input-specifications-div']//li[3]";
  	
  	//Bluetooth connection
  	public static final String  BLUETOOTH_CONTAINER  = "//*[@id='input-specifications-div']//tr[3]";
  	public static final String  BLUETOOTH_CHECKBOX  = "//*[@id='checkboxBluetooth']";
  	public static final String  BLUETOOTH_DROPBOX  = "//*[@id='input-specifications-div']//tr[3]//button";
  	public static final String BLUETOOTH_MESSAGE = "//*[@id='display-channel-msg-Bluetooth']";
  	public static final String CONNECTION_REQUIRE_MESSAGE = "//*[@id='display-connectiontype-msg']";
	
  	// Tuning
	public static final String UPLOAD_CUSTOM_TUNNING = "//*[@id='upload-custom-tuning']";
	public static final String ADD_TUNNING = "//*[@id='add-tuning-material-btn']/span";
	public static final String PARENT_TUNNING_CHECKBOX = "//*[@id='parent-tuning']";
	public static final String TUNING_RATING = "//*[@id='headphone-xtuningrating']";
	public static final String TUNING_RATING_DISABLE = "//*[@id='headphone-tuning-rating']";
	public static final String TUNNING_DIV = "//*[contains(@id,'tuning-fileupload')]"; 
	public static final String DELETE_TUNING = "//img[contains(@onclick,'confirmDeleteTuningFile')]"; 
	public static final String UPLOADED_TUNING = "//*[@id='uploaded-tuning-material']//tbody";
	public static final String UPLOADED_TUNING_NAME = "//*[@id='uploaded-tuning-material']//tbody//a/span";
	public static final String TUNING_UPLOAD_MESSAGE = "//*[contains(@id,'tuning-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String TUNING_DRAG_DROP_AREA = "//*[@id='dropzone-tuning']"; 
	public static final String RETRY_UPLOAD_TUNING = "//img[contains(@onclick,'retryUploadTuning')]";
	public static final String CANCEL_UPLOAD_TUNING = "//*[@id='tuning-progress']//img[@class='icon-close-icon']";
	
  	// Image - Edit
	public static final String  IMAGE_CATALOG = "//*[@id='product-images-div']";
  	public static final String  UPLOAD_CUSTOM_IMAGE = "//*[@id='upload-custom-image']";
	public static final String PARENT_IMAGE_CHECKBOX = "//*[@id='parent-image']";
  	public static final String  IMAGE_TABLE = "//*[@id='brandLogoTable']";
	public static final String  SELECT_FILE_IMAGE_250 = "//*[@id='add-logo124Image-btn']/span";
	public static final String  SELECT_FILE_IMAGE_500 = "//*[@id='add-logo256Image-btn']/span";
	public static final String  SELECT_FILE_IMAGE_1000 = "//*[@id='add-logo512Image-btn']/span";
	public static final String IMAGE250_DISPLAY = "//*[@id='uploaded-logo124Image-material']";
	public static final String IMAGE500_DISPLAY = "//*[@id='uploaded-logo256Image-material']";
	public static final String IMAGE1000_DISPLAY = "//*[@id='uploaded-logo512Image-material']";
	public static final String IMAGETABLE = "//*[@id='brandLogoTable']";
	public static final String CATALOG_IMAGE_UPLOAD_MESSAGE_250 ="//div[@id='uploaded-logo124Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String CATALOG_IMAGE_UPLOAD_MESSAGE_500="//div[@id='uploaded-logo256Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String CATALOG_IMAGE_UPLOAD_MESSAGE_1000="//div[@id='uploaded-logo512Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String CATALOG_IMAGE_UPLOADER_250 ="//*[@id='logo124Image-material-div']";
	public static final String CATALOG_IMAGE_UPLOADER_500 ="//*[@id='logo256Image-material-div']";
	public static final String CATALOG_IMAGE_UPLOADER_1000 ="//*[@id='logo512Image-material-div']";
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
	public static final String CATALOG_IMAGE_TRASH_250 ="//div[@id='uploaded-logo124Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/td[2]/img";
	public static final String CATALOG_IMAGE_TRASH_500 ="//div[@id='uploaded-logo256Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/td[2]/img";
	public static final String CATALOG_IMAGE_TRASH_1000 ="//div[@id='uploaded-logo512Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/td[2]/img";
	public static final String RETRY_UPLOAD_IMAGE_250 ="//div[@id='uploaded-logo124Image-material']//img[contains(@onclick, 'retryUploadImage')]";
	public static final String RETRY_UPLOAD_IMAGE_500 ="//div[@id='uploaded-logo256Image-material']//img[contains(@onclick, 'retryUploadImage')]";
	public static final String RETRY_UPLOAD_IMAGE_1000 ="//div[@id='uploaded-logo512Image-material']//img[contains(@onclick, 'retryUploadImage')]";
	public static final String CANCEL_UPLOAD_IMAGE_250 ="//*[@id='imglogo124Image-progress']//img[@class = 'icon-close-icon']";
	public static final String CANCEL_UPLOAD_IMAGE_500 ="//*[@id='imglogo256Image-progress']//img[@class = 'icon-close-icon']";
	public static final String CANCEL_UPLOAD_IMAGE_1000 ="//*[@id='imglogo512Image-progress']//img[@class = 'icon-close-icon']";
	public static final String LIGHTBOX_STYLE_IMAGE = "//img[@class = 'lb-image']";
	public static final String LIGHTBOX_CLOSE ="//*[@id='lightbox']/div[2]/div/div[2]/a";
	public static final String IMAGE250_DRAG_DROP_AREA ="//*[@id='dropzone-logo124Image']";
	public static final String IMAGE500_DRAG_DROP_AREA ="//*[@id='dropzone-logo256Image']";
	public static final String IMAGE1000_DRAG_DROP_AREA ="//*[@id='dropzone-logo512Image']";

	// Marketing
	public static final String  ADD_MARKETING = "//*[@id='add-marketing-material-btn']";
	public static final String  MARKETING_FILE_CONTAINER = "//*[@id='marketing-material-section']";
	public static final String  MARKETING_METERIAL_TYPE = "//*[contains(@id,'marketingType')]";
	public static final String MARKETING_UPLOAD_MESSAGE="[contains(@id,'marketing-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String MARKETING_DRAG_DROP_AREA = "//*[@id='dropzone-marketing']"; 
	public static final String RETRY_UPLOAD_MARKETING ="//img[contains(@onclick, 'retryUploadMarketing')]";
	public static final String CANCEL_UPLOAD_MARKETING = "//*[@id='marketing-progress']//img[@class = 'icon-close-icon']";

	
	public static final String DUPLICATE_WARNING_MESSAGE = "Language is duplicated";
	public static final String REQUIRE_NAME_MESSAGE = "Display name is required ";
	
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
		Hashtable<String, String> variant = new Hashtable<String, String>();
		variant.put("id", SALESFORCE_ID);
		variant.put("name", NAME);
		variant.put("ean", EAN);
		variant.put("upc", UPC);
		variant.put("descriptor", DESCRIPTOR);
		variant.put("add tunning", ADD_TUNNING);
		variant.put("tuning rating", TUNING_RATING);
		variant.put("img1", SELECT_FILE_IMAGE_250);
		variant.put("img2", SELECT_FILE_IMAGE_500);
		variant.put("img3", SELECT_FILE_IMAGE_1000);
		variant.put("add marketing", ADD_MARKETING);
		variant.put("marketing meterial type", MARKETING_METERIAL_TYPE);
		variant.put("save", SAVE);
		return variant;
	}
	
	public static Hashtable<String, String> getElementsInfo(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("name", NAME);
		data.put("languages", LANGUAGE_CONTAINER);
		data.put("ean", EAN);
		data.put("upc", UPC);
		data.put("descriptor", DESCRIPTOR);
		data.put("release date", RELEASE_DATE);
		data.put("add tuning", UPLOAD_CUSTOM_TUNNING);
		data.put("use parent tuning", PARENT_TUNNING_CHECKBOX);
		data.put("tuning rating", TUNING_RATING_DISABLE);
		data.put("add custom img", UPLOAD_CUSTOM_IMAGE);
		data.put("img1", SELECT_FILE_IMAGE_250);
		data.put("img2", SELECT_FILE_IMAGE_500);
		data.put("img3", SELECT_FILE_IMAGE_1000);
		data.put("use parent img", PARENT_IMAGE_CHECKBOX);
		data.put("add marketing", ADD_MARKETING );
		data.put("save", SAVE);
		data.put("cancel", CANCEL);
		return data;
	}
	
	public static final String LANGUAGE_DEFAULT_OPTIONS = "//*[@id='lang-div-container']/div/select/option[1]";
	public static final String EMPTY_LANGUAGE_FIELD = "//*[@id='lang-div-container']/div/input";
	public static final String OTHER_LANGUAGE_NAME = "//*[@id='lang-div-container']/div[1]/input";

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
		"250X250",									
		"500X500", 
		"1000X1000"
	};
	
	public static final String UPLOAD_FRAME_STATUS[]={
		"display: block;",
		"display: none;",
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
	public static final String IMG_NAME[]={
		"250x250.jpg",
		"500x500.jpg",
		"1000x1000.jpg",
		"250x250.png",
		"500x500.png",
		"1000x1000.png"
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
		public static String tuningRatingOption[] = {
		"Parent's Rating",
		"Undetermined",
		"No Certification",
		"A",
		"A+"
	};
}
