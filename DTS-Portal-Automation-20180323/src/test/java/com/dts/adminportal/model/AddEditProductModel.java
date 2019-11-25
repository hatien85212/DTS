package com.dts.adminportal.model;

import java.util.ArrayList;
import java.util.Hashtable;


public class AddEditProductModel {

	public static final String PRODUCT_TITLE = "//*[@id='add-accessory-form']/fieldset/div[1]";
	public static final String VARIANT_TITLE="//*[@id='add-variant-form']/fieldset/div[1]";
	public static final String TRACKING_ID = "//*[contains(@id,'dts-tracking-id')]";
	public static final String COMPANY = "//*[@id='companyList']";
	public static final String BRAND = "//select[contains(@id,'brand')]";
	public static final String SALEFORCE_ID = "//*[@id='salesforce-id']";
	public static final String MODEL_NAME = "//*[@id='display-name']";
	public static final String MODEL_NUMBER = "//*[@id='display-number']";
	public static final String EAN = "//*[@id='ean']";
	public static final String UPC = "//*[@id='upc']";

	public static final String LANGUAGE_DROPDOWN(int position) {

		if (position == 0) {
			return "//*[@id='lang-div-container']/div/select";
		} else {
			return "//*[@id='lang-div-container']/div[" + position + "]/select";
		}

	}

	public static final String LANGUAGE_CONTAINER = "//*[@id='lang-div-container']";
	public static final String MODEL_TYPE = "//*[@id='modelType']";
	public static final String INPUT_SPECIFICATIONS_TABLE = "//*[@id='input-specifications-div']/table";
	public static final String RELEASE_DATE = "//*[@id='releaseDate']";
	public static final String ALIASES = "//*[@id='aliases']";
	public static final String DESCRIPTION = "//*[@id='description']";
	public static final String DESCRIPTOR = "//*[@id='descriptor']"; 		// NOTE: In variant, it's descriptor, not description
	public static final String SAVE_PRODUCT = "//*[@id='save-accessory']";
	public static final String CANCEL_PRODUCT = "//*[@id='cancel-accessory']";
	public static final String SAVE_VARIANT = "//*[@id='save-variant']";
	public static final String CANCEL_VARIANT = "//*[@id='cancel-addVariant']";
	public static final String NAME_ERROR_MESSAGE = "//*[@id='display-name-msg']";
	public static final String LANGUAGE_DEFAULT_OPTIONS = "//*[@id='lang-div-container']/div/select/option[1]";
	public static final String EMPTY_LANGUAGE_FIELD = "//*[@id='lang-div-container']/div/input";

	public static final String LANG_ERROR_MSG(int position) {
		return "//*[@id='lang-div-container']/div[" + position + "]/span[1]";

	}

	public static final String OTHER_LANGUAGE_NAME(int position) {
		return "//*[@id='lang-div-container']/div[" + position + "]/input";
	}

	public static final String TRASH_ICON(int position) {
		return "//*[@id='lang-div-container']/div[" + position + "]/i";
	}

	public static final String NOISE_CANCELLING = "//*[@id='noise-cancel']";
	public static final String INPUT_SPECIFICATIONS = "//*[@id='input-specifications-div']";
	public static final String VARIANT_INPUT_SPECIFICATIONS = "//*[@id='input-specifications']";
	public static final String MODEL_NAME_ERR = "//*[@id='display-name-msg']";
	public static final String UPC_ERROR_MESSAGE = "//*[@id='upc-msg']";
	public static final String EAN_ERROR_MESSAGE = "//*[@id='ean-msg']";

	// Wired connection
	public static final String CONTENT_CHANNEL_WIRED_DROPDOWN = "//*[@id='input-specifications-div']/table/tbody/tr[2]/td[2]/div";
	public static final String CONTENT_CHANNEL_BLUET_DROPDOWN = "//*[@id='input-specifications-div']/table/tbody/tr[3]/td[2]/div";
	public static final String CONNECTION_TYPE_MESSAGE = "//*[@id='display-connectiontype-msg']/span";
	public static final String WIRED_CONTAINER = "//*[@id='input-specifications-div']//tr[2]";
	public static final String WIRED_CHECKBOX = "//*[@id='checkboxWired']";
	public static final String WIRED_DROPBOX = "//*[@id='input-specifications-div']//tr[2]//button[1]";
	public static final String WIRED_OPTIONS = "//*[@id='input-specifications-div']//ul";
	public static final String WIRED_MESSAGE = "//*[@id='display-channel-msg-Wired']";
	public static final String ALL_CHANNEL = "//*[@id='input-specifications-div']//li[1]";
	public static final String MONO_CHANNEL = "//*[@id='input-specifications-div']//li[2]";
	public static final String STEREO_CHANNEL = "//*[@id='input-specifications-div']//li[3]";
	public static final String BRAND_LIST = "//*[@id='brandList']";
	public static final String REQUEST_TUNING_REVIEW = "//*[@id='requestTuningAction']";
//	public static final String CONNECTION_TYPE_STRING = "Connection Type is required";
	public static final String INPUT_CHANNEL_ERROR_STRING = "You have to select at least an item";

	// Blue-tooth connection
	public static final String BLUETOOTH_CONTAINER = "//*[@id='input-specifications-div']//tr[3]";
	public static final String BLUETOOTH_CHECKBOX = "//*[@id='checkboxBluetooth']";
	public static final String BLUETOOTH_DROPBOX = "//*[@id='input-specifications-div']//tr[3]//button";
	public static final String BLUETOOTH_MESSAGE = "//*[@id='display-channel-msg-Bluetooth']";
	public static final String CONNECTION_REQUIRE_MESSAGE = "//*[@id='display-connectiontype-msg']";
	public static final String BT_DEVICE_NAME_TITLE = "//*[@id='bt-device-name']/div[1]";
	public static final String BT_DEVICE_NAME_INS = "//*[@id='bt-device-name-intro']";
	public static final String BT_DEVICE_NAME_TEXTINS = "Enter one or more strings which can uniquely identify this product based on its Bluetooths "+'"'+"Device Name"+ '"' +" metadata value.";
	public static final String BT_DEVICE_NAME_FIELD = "//*[@id='bt-device-name-input']";
	public static final String BT_DEVICE_NAME_ADD = "//*[@id='bt-device-name-add']";
	public static final String BT_DEVICES_SETECTED = "//*[contains(@id,'index-icon-trash-')]/div";
	public static final String BT_DEVICES_MESSAGE = ".//*[@id='bt-device-name-existed-message']";
	
	// USB connection
	public static final String USB_DEVICE_NAME_TITLE = "//*[@id='usb-device-name']/div[1]";
	public static final String USB_DEVICE_NAME_INS = "//*[@id='usb-device-name-intro']";
	public static final String USB_CONTAINER = "//*[@id='input-specifications-div']/table/tbody/tr[4]";
	public static final String USB_CHECKBOX = "//*[@id='checkboxUSB']";
	public static final String USB_DEVICE_NAME_FIELD = "//*[@id='usb-device-name-input']";
	public static final String USB_DEVICE_NAME_ADD = "//*[@id='usb-device-name-add']";
	public static final String USB_DEVICE_NAME_TEXTINS = "Enter one or more strings which can uniquely identify this product based on its USBs "+'"'+"Device Name"+ '"' +" metadata value.";
	public static final String USB_DEVICES_MESSAGE = ".//*[@id='usb-device-name-existed-message']";
	public static final String USB_DEVICE_NAME  = "//*[@id='selected-usb-device-name-placeholder']";

	// Tuning
	public static final String TUNING_FILE_CONTAINER = "//*[@id='tuning-material-div']";
	public static final String ADD_TUNNING = "//*[@id='add-tuning-material-btn']/span";
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
	public static final String TUNING_MESSAGE = "//*[@id='tuning-fileupload--1']/table/tbody/tr[2]/td/p/span";
	public static final String TUNING_MESSAGE_POPUP = "html/body/div[6]/div[1]";

	public static final String PARENT_TUNNING_LINK = "//*[@id='parent-tuning']";
	public static final String UPLOAD_CUSTOM_TUNNING = "//*[@id='upload-custom-tuning']";
	
	
	// Image - Edit
	public static final String IMAGE_CATALOG = "//*[@id='product-images-div']";
	public static final String SELECT_FILE_IMAGE_250 = "//*[@id='add-logo124Image-btn']/span";
	public static final String SELECT_FILE_IMAGE_500 = "//*[@id='add-logo256Image-btn']/span";
	public static final String SELECT_FILE_IMAGE_1000 = "//*[@id='add-logo512Image-btn']/span";
	public static final String IMAGE250_DISPLAY = "//*[@id='uploaded-logo124Image-material']/div/div[2]";
	public static final String IMAGE500_DISPLAY = "//*[@id='uploaded-logo256Image-material']/div/div[2]";
	public static final String IMAGE1000_DISPLAY = "//*[@id='uploaded-logo512Image-material']/div/div[2]";
	public static final String CATALOG_IMAGE_UPLOADER_250 = "//*[@id='logo124Image-material-div']";
	public static final String CATALOG_IMAGE_UPLOADER_500 = "//*[@id='logo256Image-material-div']";
	public static final String CATALOG_IMAGE_UPLOADER_1000 = "//*[@id='logo512Image-material-div']";
	public static final String CATALOG_IMAGE_UPLOAD_MESSAGE_250 = "//div[@id='uploaded-logo124Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String CATALOG_IMAGE_UPLOAD_MESSAGE_500 = "//div[@id='uploaded-logo256Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String CATALOG_IMAGE_UPLOAD_MESSAGE_1000 = "//div[@id='uploaded-logo512Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String CATALOG_IMAGE_UPLOAD_THUMBNAIL_250 = "//div[@id='uploaded-logo124Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/th/a/img";
	public static final String CATALOG_IMAGE_UPLOAD_THUMBNAIL_500 = "//div[@id='uploaded-logo256Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/th/a/img";
	public static final String CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000 = "//div[@id='uploaded-logo512Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/th/a/img";
	public static final String CATALOG_IMAGE_UPLOAD_INFO_250 = "//div[@id='uploaded-logo124Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td";
	public static final String CATALOG_IMAGE_UPLOAD_INFO_500 = "//div[@id='uploaded-logo256Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td";
	public static final String CATALOG_IMAGE_UPLOAD_INFO_1000 = "//div[@id='uploaded-logo512Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td";
	public static final String CATALOG_IMAGE_TITLE_LINK_250 = "//span[@class='maupload-logo124Image']";
	public static final String CATALOG_IMAGE_TITLE_LINK_500 = "//span[@class='maupload-logo256Image']";
	public static final String CATALOG_IMAGE_TITLE_LINK_1000 = "//span[@class='maupload-logo512Image']";
	public static final String CATALOG_IMAGE_FILE_SIZE_250 = "//div[@id='uploaded-logo124Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/span";
	public static final String CATALOG_IMAGE_FILE_SIZE_500 = "//div[@id='uploaded-logo256Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/span";
	public static final String CATALOG_IMAGE_FILE_SIZE_1000 = "//div[@id='uploaded-logo512Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/span";
	public static final String RETRY_UPLOAD_IMAGE_250 = "//div[@id='uploaded-logo124Image-material']//img[contains(@onclick, 'retryUploadImage')]";
	public static final String RETRY_UPLOAD_IMAGE_500 = "//div[@id='uploaded-logo256Image-material']//img[contains(@onclick, 'retryUploadImage')]";
	public static final String RETRY_UPLOAD_IMAGE_1000 = "//div[@id='uploaded-logo512Image-material']//img[contains(@onclick, 'retryUploadImage')]";
	public static final String CANCEL_UPLOAD_IMAGE_250 = "//*[@id='imglogo124Image-progress']//img[@class = 'icon-close-icon']";
	public static final String CANCEL_UPLOAD_IMAGE_500 = "//*[@id='imglogo256Image-progress']//img[@class = 'icon-close-icon']";
	public static final String CANCEL_UPLOAD_IMAGE_1000 = "//*[@id='imglogo512Image-progress']//img[@class = 'icon-close-icon']";
	public static final String IMAGE250_DRAG_DROP_AREA = "//*[@id='dropzone-logo124Image']";
	public static final String IMAGE500_DRAG_DROP_AREA = "//*[@id='dropzone-logo256Image']";
	public static final String IMAGE1000_DRAG_DROP_AREA = "//*[@id='dropzone-logo512Image']";
	public static final String CATALOG_IMAGE_TRASH_250 ="//div[@id='uploaded-logo124Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/td[2]/img";
	public static final String CATALOG_IMAGE_TRASH_500 ="//div[@id='uploaded-logo256Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/td[2]/img";
	public static final String CATALOG_IMAGE_TRASH_1000 ="//div[@id='uploaded-logo512Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/td[2]/img";
	public static final String VARIANT_UPLOADED_TUNING_NAME = "//*[@id='uploaded-tuning-material']//tbody//a/span";
	
	
	// Marketing
	public static final String MARKETING_MATERIAL_DIV = "//*[@id='marketing-material-div']";
	public static final String MARKETING_FILE_CONTAINER = "//*[@id='marketing-material-section']";
	public static final String ADD_MARKETING = "//*[@id='add-marketing-material-btn']/span";
	public static final String MARKETING_METERIAL_TYPE = "//*[contains(@id,'marketingType')]";
	public static final String MARKETING_UPLOAD_MESSAGE = "[contains(@id,'marketing-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String LIGHTBOX_CLOSE = "//*[@id='lightbox']/div[2]/div/div[2]/a";
	public static final String LIGHTBOX_STYLE_IMAGE = "//img[@class = 'lb-image']";
	public static final String CANCEL_UPLOAD_MARKETING = "//*[@id='marketing-progress']//img[@class = 'icon-close-icon']";
	public static final String MARKETING_DRAG_DROP_AREA = "//*[@id='dropzone-marketing']";
	public static final String RETRY_UPLOAD_MARKETING = "//img[contains(@onclick, 'retryUploadMarketing')]";
	public static final String DELETE_UPLOAD_MARKETING = "//img[contains(@onclick, 'confirmDeleteMarketingFile')]";
	
	public static final String  UPLOAD_CUSTOM_IMAGE = "//*[@id='upload-custom-image']";
	public static final String PARENT_IMAGE_LINK = "//*[@id='parent-image']";
	public static final String BRAND_LOGO_TABLE = "//*[@id='brandLogoTable']";
	
	public static final String POPUP_ERROR = "html/body/div[6]";
	public static final String CLOSE_POPUP = "html/body/div[6]/div[2]/a";
	public static final String MESSAGE_ERROR = "html/body/div[6]/div[1]/h4";
	
	public static final String MESSAGE_TUNING = "Tuning upload is still in progress!";
	public static final String MESSAGE_IMAGE = "Image upload is still in progress!";
	public static final String MESSAGE_MARKETING = "Marketing material upload is still in progress!";
	
	public static enum ProductType {
		SELECT("Select Type"), EAR_BUDS("Ear-buds"), IN_EAR("In-Ear"), ON_EAR("On-Ear"), OVER_EAR("Over-Ear");

		private final String type;

		ProductType(String type) {
			this.type = type;
		}

		public String getName() {
			return this.type;
		}

		public static String[] getNames() {
			ProductType[] types = values();
			String[] result = new String[types.length];
			for (int i = 0; i < types.length; i++) {
				result[i] = types[i].getName();
			}
			return result;
		}
	}
	public static final ArrayList<String> getProductType() {
	ArrayList<String> arrayList = new ArrayList<String>();
	arrayList.add(ProductType.EAR_BUDS.getName());
	arrayList.add(ProductType.IN_EAR.getName());
	arrayList.add(ProductType.ON_EAR.getName());
	arrayList.add(ProductType.OVER_EAR.getName());
	return arrayList;
}

	public static enum ProductLanguage {
		SELECT("- Select -"), CHINESE_SIM("Chinese (Simplified)"), CHINESE_TRA("Chinese (Traditional)"), 
		FRENCH("French (France)"), GERMAN("German"), ITALIAN("Italian"), 
		JAPANESE("Japanese"), KOREAN("Korean"), RUSSIAN("Russian"), SPANISH("Spanish (Spain)");

		private final String language;

		ProductLanguage(String language) {
			this.language = language;
		}

		public String getName() {
			return this.language;
		}

		public static String[] getNames() {
			ProductLanguage[] languages = values();
			String[] result = new String[languages.length];
			for (int i = 0; i < languages.length; i++) {
				result[i] = languages[i].getName();
			}
			return result;
		}

	}

	public static enum ProductModelName {
		MODEL_NAME_REQUIRED("Model name is required."), 
		LANGUAGE_DUPLICATE("Language is duplicated"), 
		INHERIT_DEFAULT("inherits default");

		private final String name;

		ProductModelName(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public static String[] getNames() {
			ProductModelName[] names = values();
			String[] result = new String[names.length];
			for (int i = 0; i < names.length; i++) {
				result[i] = names[i].getName();
			}
			return result;
		}

	}
	
	public static enum AddRequiredFieldId {
		COMPANY_ID("//*[@id='company-select-msg']"), BRAND_ID("//*[@id='brand-select-msg']"), NAME_ID(
				"//*[@id='display-name-msg']"), MODEL_NO_ID("//*[@id='model-number-msg']"), UPC_ID(
						"//*[@id='upc-msg']"), MODEL_TYPE_ID("//*[@id='model-type-msg']"), DES_ID(
								"//*[@id='desc-name-msg']");

		private final String id;

		AddRequiredFieldId(String id) {
			this.id = id;
		}

		public String getName() {
			return this.id;
		}

		public static String[] getNames() {
			AddRequiredFieldId[] ids = values();
			String[] result = new String[ids.length];
			for (int i = 0; i < ids.length; i++) {
				result[i] = ids[i].getName();
			}
			return result;
		}

	}

	public static enum UploadFileMessage {
		UPLOAD_SUCCESS("File upload successful"), INVALID_FILE("Invalid file"), UPLOAD_CANCELED("File upload canceled"),
		UPLOAD_ERROR("File upload error"), FILE_250_PX("Automatically resize. Original 250x250 px"), 
		FILE_500_PX("Automatically resize. Original 500x500 px"), FILE_1000_PX("Automatically resize. Original 1000x1000 px"),
		USE_PARENT("Use Parent's Images will discard the custom files."),
		CONNECTION_TYPE_NOTMATHCH("The associated tuning file is invalid because it does not include data for all the routes (Lineout/Bluetooth/USB) defined in input specifications on the product.");
		
		
		private final String msg;

		UploadFileMessage(String msg) {
			this.msg = msg;
		}

		public String getName() {
			return this.msg;
		}

		public static String[] getNames() {
			UploadFileMessage[] msgs = values();
			String[] result = new String[msgs.length];
			for (int i = 0; i < msgs.length; i++) {
				result[i] = msgs[i].getName();
			}
			return result;
		}
		
	}

	public static enum EanUpcErrorMessage {
		EAN_ONLY_DIGITS("EAN value must contain only digits."), EAN_EXCEED_CHAR_LIMIT("EAN value exceeds 13 characters limit."), 
		EAN_UNIQUE("EAN must be unique"), UPC_ONLY_DIGITS("UPC value must contain only digits."),
		UPC_EXCEED_CHAR_LIMIT("UPC value exceeds 12 characters limit."),UPC_UNIQUE("UPC must be unique");
		
		
		private final String msg;

		EanUpcErrorMessage(String msg) {
			this.msg = msg;
		}

		public String getName() {
			return this.msg;
		}

		public static String[] getNames() {
			EanUpcErrorMessage[] msgs = values();
			String[] result = new String[msgs.length];
			for (int i = 0; i < msgs.length; i++) {
				result[i] = msgs[i].getName();
			}
			return result;
		}
		
	}
	
	public static enum TuningRatingOption {
		UNDETERMINED("Undetermined"), NO_CERTIFICATION("No Certification"), 
		A("A"), A_PLUS("A+");
		
		private final String option;

		TuningRatingOption(String option) {
			this.option = option;
		}

		public String getName() {
			return this.option;
		}

		public static String[] getNames() {
			TuningRatingOption[] options = values();
			String[] result = new String[options.length];
			for (int i = 0; i < options.length; i++) {
				result[i] = options[i].getName();
			}
			return result;
		}
	}
	
	public static enum ConnectionType {
		WIRED("Wired (3.5 mm)"), BLUETOOTH("Bluetooth"), USB("USB");;
		
		private final String type;

		ConnectionType(String type) {
			this.type = type;
		}

		public String getName() {
			return this.type;
		}

		public static String[] getNames() {
			ConnectionType[] types = values();
			String[] result = new String[types.length];
			for (int i = 0; i < types.length; i++) {
				result[i] = types[i].getName();
			}
			return result;
		}
	}
	
	public static enum HeadphoneInsideOption {
		NOT_APPLICABLE("Not Applicable (N/A)"), EMBEDDED("Embedded in hardware");
		
		private final String type;

		HeadphoneInsideOption(String type) {
			this.type = type;
		}

		public String getName() {
			return this.type;
		}

		public static String[] getNames() {
			HeadphoneInsideOption[] types = values();
			String[] result = new String[types.length];
			for (int i = 0; i < types.length; i++) {
				result[i] = types[i].getName();
			}
			return result;
		}
	}
	
	
	public static enum InputContentChannel {
		SELECT_ALL("Select all that apply"),SINGLE_CHANNEL("1 (Mono)"), STEREO("2 (Stereo)");
		
		private final String type;

		InputContentChannel(String type) {
			this.type = type;
		}

		public String getName() {
			return this.type;
		}

		public static String[] getNames() {
			InputContentChannel[] types = values();
			String[] result = new String[types.length];
			for (int i = 0; i < types.length; i++) {
				result[i] = types[i].getName();
			}
			return result;
		}
	}
	
	
	private static Hashtable<String, String> productInputFieldIds;
	private static Hashtable<String, String> productElementFieldIds;

	public static final Hashtable<String, String> getProductInputFieldIdsHash() {
		if (productInputFieldIds == null) {
			productInputFieldIds = new Hashtable<String, String>();
			productInputFieldIds.put("company", COMPANY);
			productInputFieldIds.put("brand", BRAND);
		
			productInputFieldIds.put("id", SALEFORCE_ID);
			productInputFieldIds.put("name", MODEL_NAME);
			productInputFieldIds.put("number", MODEL_NUMBER);
			productInputFieldIds.put("ean", EAN);
			productInputFieldIds.put("upc", UPC);
			productInputFieldIds.put("type", MODEL_TYPE);
			productInputFieldIds.put("date", RELEASE_DATE);
			productInputFieldIds.put("wired", WIRED_CONTAINER);
			productInputFieldIds.put("bluetooth", BLUETOOTH_CONTAINER);
			productInputFieldIds.put("usb", USB_CONTAINER);
			productInputFieldIds.put("aliases", ALIASES);
			productInputFieldIds.put("description", DESCRIPTION);
			productInputFieldIds.put("add tunning", ADD_TUNNING);
			productInputFieldIds.put("tuning rating", TUNING_RATING);
			productInputFieldIds.put("img1", SELECT_FILE_IMAGE_250);
			productInputFieldIds.put("img2", SELECT_FILE_IMAGE_500);
			productInputFieldIds.put("img3", SELECT_FILE_IMAGE_1000);
			productInputFieldIds.put("add marketing", ADD_MARKETING);
			productInputFieldIds.put("marketing meterial type", MARKETING_METERIAL_TYPE);
			productInputFieldIds.put("save", SAVE_PRODUCT);
		}
		return productInputFieldIds;

	}
	

	public static Hashtable<String, String> getProductElementFieldIds() {
		if (productElementFieldIds == null) {
			productElementFieldIds = new Hashtable<String, String>();
			productElementFieldIds.put("company", COMPANY);
			productElementFieldIds.put("brand", BRAND);
			productElementFieldIds.put("id", SALEFORCE_ID);
			productElementFieldIds.put("name", MODEL_NAME);
			productElementFieldIds.put("number", MODEL_NUMBER);
			productElementFieldIds.put("ean", EAN);
			productElementFieldIds.put("upc", UPC);
			productElementFieldIds.put("type", MODEL_TYPE);
			productElementFieldIds.put("noise cancelling", NOISE_CANCELLING);
			productElementFieldIds.put("input specifications", INPUT_SPECIFICATIONS);
			productElementFieldIds.put("date", RELEASE_DATE);
			productElementFieldIds.put("aliases", ALIASES);
			productElementFieldIds.put("description", DESCRIPTION);
			productElementFieldIds.put("tuning", TUNING_FILE_CONTAINER);
			productElementFieldIds.put("image", IMAGE_CATALOG);
			productElementFieldIds.put("marketing", MARKETING_MATERIAL_DIV);
			productElementFieldIds.put("save", SAVE_PRODUCT);
			productElementFieldIds.put("cancel", CANCEL_PRODUCT);
		}
		return productElementFieldIds;
	}

	public static Hashtable<String, String> getVariantElementsInfo(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("name", MODEL_NAME);
		data.put("languages", LANGUAGE_CONTAINER);
		data.put("ean", EAN);
		data.put("upc", UPC);
		data.put("descriptor", DESCRIPTOR);
		data.put("release date", RELEASE_DATE);
		data.put("add tuning", UPLOAD_CUSTOM_TUNNING);
		data.put("use parent tuning", PARENT_TUNNING_LINK);
		data.put("tuning rating", TUNING_RATING_DISABLE);
		data.put("add custom img", UPLOAD_CUSTOM_IMAGE);
		data.put("img1", SELECT_FILE_IMAGE_250);
		data.put("img2", SELECT_FILE_IMAGE_500);
		data.put("img3", SELECT_FILE_IMAGE_1000);
		data.put("use parent img", PARENT_IMAGE_LINK);
		data.put("add marketing", ADD_MARKETING );
		data.put("save", SAVE_VARIANT);
		data.put("cancel", CANCEL_VARIANT);
		return data;
	}
	
	public static final Hashtable<String, String> getVariantHash(){
		Hashtable<String, String> variant = new Hashtable<String, String>();
		variant.put("id", SALEFORCE_ID);
		variant.put("name", MODEL_NAME);
		variant.put("ean", EAN);
		variant.put("upc", UPC);
		variant.put("descriptor", DESCRIPTION);
		variant.put("add tunning", ADD_TUNNING);
		variant.put("tuning rating", TUNING_RATING);
		variant.put("img1", SELECT_FILE_IMAGE_250);
		variant.put("img2", SELECT_FILE_IMAGE_500);
		variant.put("img3", SELECT_FILE_IMAGE_1000);
		variant.put("add marketing", ADD_MARKETING);
		variant.put("marketing meterial type", MARKETING_METERIAL_TYPE);
		variant.put("save", SAVE_VARIANT);
		return variant;
	}
	
	public static enum FileUpload {
		IMG_250_JPG("Image250x250.jpg"), IMG_500_JPG("Image500x500.jpg"), 
		IMG_1000_JPG("Image1000x1000.jpg"), IMG_250_PNG("250x250.png"), IMG_500_PNG("500x500.png"),
		IMG_1000_PNG("1000x1000.png"),Tuning_Invalid("tuning-based-on-JSON.zip"),AUDIO_ROUTE_FILE("default-headphone-1.1.0.dtscs"),
		File_Upload_Cancel("File-Upload-Cancel-2.dts"), X_Cancel_Upload("X-Cancel-upload.PNG"),XBrand_Cancel_Upload("XBrand-Cancel-upload.PNG"),
		Tuning_ZIP("bt-tuning.zip"),Tuning_HPXTT_LineOut("LineoutOnly.hpxtt"), Tuning_HPXTT_Bluetooth("BluetoothOnly.hpxtt"),Tuning_HPXTT_BothLineBlue("Bluetooth_LineOut.hpxtt"),
		External_Speakers_Bluetooth_3DHPEQ_missing_44k("3dHPEQ-External-Speakers-Bluetooth-missing-44k.dtscs"),External_Speakers_Bluetooth_3DHPEQ_missing_48k("3dHPEQ-External-Speakers-Bluetooth-missing-48k.dtscs"),External_Speakers_Lineout_3DHPEQ_missing_44k("3dHPEQ-External-Speakers-Lineout-missing-44k.dtscs"),External_Speakers_Lineout_3DHPEQ_missing_48k("3dHPEQ-External-Speakers-Lineout-missing-48k.dtscs"),
		External_Speakers_Bluetooth_AEQ_missing_44k("AEQ-External-Speakers-Bluetooth-missing-44k.dtscs"),External_Speakers_Bluetooth_AEQ_missing_48k("AEQ-External-Speakers-Bluetooth-missing-48k.dtscs"),External_Speakers_Lineout_AEQ_missing_44k("AEQ-External-Speakers-Lineout-missing-44k.dtscs"),External_Speakers_Lineout_AEQ_missing_48k("AEQ-External-Speakers-Lineout-missing-48k.dtscs"),
		Over_Ear_Headphones_Combined_hpeq_48000("OverEarHeadphones-Combined-hpeq-48000.dtscs"),Over_Ear_Headphones_Combined_hpeq_44100("OverEarHeadphones-Combined-hpeq-44100.dtscs"),Attached5_InternalSpeakersVoice_aeq_48000("Attached5-InternalSpeakersVoice-aeq-48000.dtscs"),Attached5_InternalSpeakersVoice_aeq_44100("Attached5-InternalSpeakersVoice-aeq-44100.dtscs"),
		Over_Ear_Headphones_Combined_aeq_48000("OverEarHeadphones-Combined-aeq-48000.dtscs"),Over_Ear_Headphones_Combined_aeq_44100("OverEarHeadphones-Combined-aeq-44100.dtscs"),
		BluetoothOnly_bypass("BluetoothOnly_bypass.hpxtt"),BluetoothOnly_hpEq("BluetoothOnly_hpEq.hpxtt"),BluetoothOnly_oemEq("BluetoothOnly_oemEq.hpxtt"),BluetoothOnly_hpEq_oemEq("BluetoothOnly_hpEq+oemEq.hpxtt"),
		LineoutOnly_bypass("Lineout_bypass.hpxtt"),LineoutOnly_hpEq("Lineout_hpeq.hpxtt"),LineoutOnly_oemEq("Lineout_oemEq.hpxtt"),LineoutOnly_hpEq_oemEq("Lineout_hpEq_oemEq.hpxtt"),
		Tuning_HPXTT_USB("USBOnly.hpxtt"),
		Tuning_HPXTT_BothLineUSB("Lineout_USB.hpxtt"),
		Tuning_HPXTT_BothBlueUSB("Bluetooth_USB.hpxtt"),
		Tuning_HPXTT_BothBlueLineUSB("Bluetooth_Lineout_USB.hpxtt"),
		Attached0InternalSpeakersDefault_Custom("Attached0InternalSpeakersDefault_Custom.dtscs"),
		Attached1InternalSpeakersOff_Custom("Attached1InternalSpeakersOff_Custom.dtscs"),
		Attached2InternalSpeakersMusic_Custom("Attached2InternalSpeakersMusic_Custom.dtscs"),
		Attached3InternalSpeakersMovies_Custom("Attached3InternalSpeakersMovies_Custom.dtscs"),
		Attached4InternalSpeakersGames_Custom("Attached4InternalSpeakersGames_Custom.dtscs"),
		Attached5InternalSpeakersVoice_Custom("Attached5InternalSpeakersVoice_Custom.dtscs"),
		Car_Audio_Bluetooth("Car_Audio_Bluetooth.dtscs"),
		Car_Audio_Lineout("Car_Audio_Lineout.dtscs"),
		Car_Audio_USB("Car_Audio_USB.dtscs"),
		Combo0("Combo0.dtscs"),
		Default_External_Audio("Default-External-Audio.dtscs"),
		Earbuds_Bluetooth("Earbuds_Bluetooth.dtscs"),
		Earbuds_Lineout("Earbuds_Lineout.dtscs"),
		Earbuds_USB("Earbuds_USB.dtscs"),
		Earpiece_Bluetooth("Earpiece_Bluetooth.dtscs"),
		Earpiece_Lineout("Earpiece_Lineout.dtscs"),
		Earpiece_USB("Earpiece_USB.dtscs"),
		External_Speakers_Bluetooth("External_Speakers_Bluetooth.dtscs"),
		External_Speakers_Lineout("External_Speakers_Lineout.dtscs"),
		External_Speakers_USB("External_Speakers_USB.dtscs"),
		Over_Ear_Headphones_Bluetooth("Over_Ear_Headphones_Bluetooth.dtscs"),
		Over_Ear_Headphones_Lineout("Over_Ear_Headphones_Lineout.dtscs"),
		Over_Ear_Headphones_USB("Over_Ear_Headphones_USB.dtscs"),
		Attached0InternalSpeakersDefault_Custom_TBHDX_MBHL("Attached0InternalSpeakersDefault_Custom_TBHDX_MBHL.dtscs"),
		Attached5InternalSpeakersVoice_Custom_TBHDX_MBHL_44k("Attached5InternalSpeakersVoice_Custom_TBHDX_MBHL_44k.dtscs"),
		Attached5InternalSpeakersVoice_Custom_out_of_range_value("Attached5InternalSpeakersVoice_Custom_out_of_range_value.dtscs"),
		Attached5InternalSpeakersVoice_Custom_TBHDX_MBHL("Attached5InternalSpeakersVoice_Custom_TBHDX_MBHL.dtscs"),
		Default_External_Audio_TBHDX_MBHL_44k("Default_External_Audio_TBHDX_MBHL_44k.dtscs"),
		Default_External_Audio_out_of_range_value("Default_External_Audio_out_of_range_value.dtscs"),
		Default_External_Audio_TBHDX_MBHL("Default_External_Audio_TBHDX_MBHL.dtscs"),
		External_Speakers_Lineout_TBHDX_44k("External_Speakers_Lineout_TBHDX_44k.dtscs"),
		External_Speakers_Lineout_TBHDX_MBHL("External_Speakers_Lineout_TBHDX_MBHL.dtscs"),
		External_Speakers_Lineout_out_of_range_value("External_Speakers_Lineout_out_of_range_value.dtscs"),
		External_Speakers_Bluetooth_TBHDX_MBHL("External_Speakers_Bluetooth_TBHDX_MBHL.dtscs"),	
		External_Speakers_Bluetooth_TBHDX_44k("External_Speakers_Bluetooth_TBHDX_44k.dtscs"),
		External_Speakers_Bluetooth_out_of_range_value("External_Speakers_Bluetooth_out_of_range_value.dtscs"),
		Earbuds_Lineout_TBHDX_MBHL("Earbuds_Lineout_TBHDX_MBHL.dtscs"),
		Attached0InternalSpeakersDefault_Fallback("Attached0InternalSpeakersDefault_Fallback.dtscs"),
		Attached1InternalSpeakersOff_Fallback("Attached1InternalSpeakersOff_Fallback.dtscs"),
		Attached2InternalSpeakersMusic_Fallback("Attached2InternalSpeakersMusic_Fallback.dtscs"),
		Attached3InternalSpeakersMovies_Fallback("Attached3InternalSpeakersMovies_Fallback.dtscs"),
		Attached4InternalSpeakersGames_Fallback("Attached4InternalSpeakersGames_Fallback.dtscs"),
		Attached5InternalSpeakersVoice_Fallback("Attached5InternalSpeakersVoice_Fallback.dtscs"),
		Attached6InternalSpeakersVoice_Fallback("Attached6-InternalSpeakers(mode5).dtscs"),
		Attached7InternalSpeakersVoice_Fallback("Attached7-InternalSpeakers(mode6).dtscs"),
		Attached8InternalSpeakersVoice_Fallback("Attached8-InternalSpeakers(mode7).dtscs"),
		Car_Audio_Combined_different_BT_LINEOUT("Car_Audio_Combined_different_BT_LINEOUT.dtscs"),
		Earbuds_Combined("Earbuds_Combined.dtscs"),
		Earbuds_Lineout_Blue("Earbuds_Lineout_Blue"),
		Earbuds_Lineout_USB("Earbuds_Lineout_USB"),
		Earpiece_Combined("Earpiece_Combined.dtscs"),
		External_Speakers_Combined("External_Speakers_Combined.dtscs"),
		FirstPersonShooter_Gaming("FirstPersonShooter-Gaming.dtscs"),
		McRoom0_RC1_5p1_7p1("McRoom0_RC1_5p1_7p1.dtscs"),
		MCRoom1("McRoom1-RC1-7p1.dtscs"),
		MCRoom2("McRoom2-RC15p1.dtscs"), 
		Over_Ear_Headphones_Combined("Over_Ear_Headphones_Combined.dtscs"), 
		Over_Ear_Headphones_Lineout_Blue("Over_Ear_Headphones_Lineout_Blue.dtscs"),
		Over_Ear_Headphones_Lineout_USB("Over_Ear_Headphones_Lineout_USB.dtscs"),
		Publish_McRoom2_RC1p2_7p1GameRoom("Publish_McRoom2_RC1p2_7p1GameRoom.dtscs"),
		Super_Stereo_Front_1459466914702_1459770141886("Super_Stereo_Front_1459466914702_1459770141886.dtscs"),
		Super_Stereo_Wide("Super_Stereo_Wide.dtscs"),
		Attached0InternalSpeakersDefault_TBHDX_MBHL_44k("Attached0InternalSpeakersDefault_TBHDX_MBHL_44k.dtscs"),
		Attached0InternalSpeakersDefault_Fallback_out_of_range_value("Attached0InternalSpeakersDefault_Fallback_out_of_range_value.dtscs"),
		Attached0InternalSpeakersDefault_TBHDX_MBHL("Attached0InternalSpeakersDefault_TBHDX_MBHL.dtscs"),
		Over_Ear_Headphones_Combined_TBHDX_44k("Over_Ear_Headphones_Combined_TBHDX_44k.dtscs"),
		Over_Ear_Headphones_Combined_out_of_range_value("Over_Ear_Headphones_Combined_out_of_range_value.dtscs"),
		Over_Ear_Headphones_Combined_TBHDX_MBHL("Over_Ear_Headphones_Combined_TBHDX_MBHL.dtscs"),
		Earbuds_Combined_TBHDX_MBHL("Earbuds_Combined_TBHDX_MBHL.dtscs"),
		Device_Headphone_Default("Device_Headphone_Default.dtscs"),
		Device_Headphone_Mode1("Device_Headphone_Mode1.dtscs"),
		Device_Headphone_Mode2("Device_Headphone_Mode2.dtscs"),
		Device_Headphone_Mode3("Device_Headphone_Mode3.dtscs"),
		Earbuds_Bluetooth_EagleV20("Earbuds_Bluetooth_EagleV20.dtscs"),
		Over_Ear_Headphones_Lineout_EagleV20("Over_Ear_Headphones_Lineout_EagleV20.dtscs"),
		Default_External_Audio_EagleV20("Default_External_Audio_EagleV20.dtscs"),
		Default_Single_File_Audio("Default_Single_File_Audio.exptune"),
		Device_Tuning_CTC ("Over_Ear_Headphones_Lineout_EagleV20.dtscs"),
		Device_Tuning_GPEQ ("Over_Ear_Headphones_Lineout_EagleV20.dtscs"),
		Attached_0_Default_Eagle2_0( "Attacehd_0_Default_Eagle2_0.dtscs"),
		Attached_1_Bypass_Off_Eagle2_0("Attacehd_0_Default_Eagle2_0.dtscs"),
		Attached_2_Music_Eagle2_0("Attacehd_0_Default_Eagle2_0.dtscs"),
		Attached_3_Movies_Eagle2_0("Attacehd_0_Default_Eagle2_0.dtscs"),
		Attached_4_Voice_Eagle2_0("Attacehd_0_Default_Eagle2_0.dtscs"),
		Attached_5_Game_1_Eagle2_0( "Attacehd_0_Default_Eagle2_0.dtscs"),
		Attached_6_Game_2_Eagle2_0 ("Attacehd_6_Default_Eagle2_0.dtscs"),
		Attached_7_Game_3_Eagle2_0 ("Attacehd_7_Default_Eagle2_0.dtscs"),
		Attached_8_Custom_Eagle2_0( "Attacehd_8_Default_Eagle2_0.dtscs"),
		Device_Headphone_Attach0("Device_Headphone_Attach0.dtscs"),
		Device_Headphone_Attach2("Device_Headphone_Attach2.dtscs"),
		Device_Headphone_Attach3("Device_Headphone_Attach3.dtscs"),
		Device_Headphone_Attach4("Device_Headphone_Attach4.dtscs"),
		Device_Headphone_Attach5("Device_Headphone_Attach5.dtscs"),
		Device_Headphone_Attach6("Device_Headphone_Attach6.dtscs"),
		Device_Headphone_Attach7("Device_Headphone_Attach7.dtscs"),
		Device_Headphone_Attach8("Device_Headphone_Attach8.dtscs"),
		Device_Headphone_Attach0_Eagle2_0("Device_Headphone_Attach0_Eagle2_0.dtscs"),
		Device_Headphone_Attach2_Eagle2_0("Device_Headphone_Attach2_Eagle2_0.dtscs"),
		Device_Headphone_Attach3_Eagle2_0("Device_Headphone_Attach3_Eagle2_0.dtscs"),
		Device_Headphone_Attach4_Eagle2_0("Device_Headphone_Attach4_Eagle2_0.dtscs"),
		Device_Headphone_Attach5_Eagle2_0("Device_Headphone_Attach5_Eagle2_0.dtscs"),
		Device_Headphone_Attach6_Eagle2_0("Device_Headphone_Attach6_Eagle2_0.dtscs"),
		Device_Headphone_Attach7_Eagle2_0("Device_Headphone_Attach7_Eagle2_0.dtscs"),
		Device_Headphone_Attach8_Eagle2_0("Device_Headphone_Attach8_Eagle2_0.dtscs"),
		Device_Headphone_Default_Mode_Eagle2_0("Device_Headphone_Attach0_Eagle2_0.dtscs"),
		Device_Headphone_Mode1_Eagle2_0("Device_Headphone_Mode1_Eagle2_0.dtscs"),
		Device_Headphone_Mode2_Eagle2_0("Device_Headphone_Mode2_Eagle2_0.dtscs"),
		Device_Headphone_Mode3_Eagle2_0("Device_Headphone_Mode3_Eagle2_0.dtscs"),
		Default_Audio_Tuning_Exptune_Files("Default_Audio_Tuning_Exptune_File.exptune"),
		Speaker_Attached_0_Exptune("Speaker_Attached_0.exptune"),
		Default_Speaker_Tuning_Exptune_Files("Default_Speaker_Tunings_Exptune.exptune"),
		Default_Headphone_Tuning_Exptune_Files("Default_Headphone_Tunings_Exptune_File.Exptune"),
		Default_CTC_Tuning_Exptune_File("Default_CTC_Tuning_Exptune_File.exptune"),
		Default_BEQ_Tuning_Exptune_File("Default_BEQ_Tuning_Exptune_File.exptune"),
		Standard_Accessories_Tuning_Combined_LineOut_BT_USB_ver2("standard_accessories_tuning_combined_lineout_bt_usb_ver2.dtscs"),
		Standard_Accessories_Tuning_Combined_LineOut_BT_ver2("standard_accessories_tuning_combined_lineout_bt_ver2.dtscs"),
		Standard_Accessories_Tuning_Combined_LineOut_USB_ver2("standard_accessories_tuning_combined_lineout_usb_ver2.dtscs"),
		Standard_Accessories_Tuning_Combined_BT_USB_ver2("standard_accessories_tuning_combined_bt_usb_ver2.dtscs"),
		
		;
		private final String type;

		FileUpload(String type) {
			this.type = type;
		}

		public String getName() {
			return this.type;
		}

		public static String[] getNames() {
			FileUpload[] types = values();
			String[] result = new String[types.length];
			for (int i = 0; i < types.length; i++) {
				result[i] = types[i].getName();
			}
			return result;
		}
	}
	public static final String IMAGE_SAVE = "imageSave.PNG";
	
	public static final String SIZE_LIST[] = { "250x250", "500x500", "1000x1000", };
	public static final String PARTNER_SIZE_LIST[] = { "250x250", "500x500", "1000x1000", };

	public static final String DIV_STATUS[] = { "style=\"display: block;\"", "style=\"display: none;\"", };

	public static final String IMG_NAME[] = { "250x250.jpg", "500x500.jpg", "1000x1000.jpg", "250x250.png",
			"500x500.png", "1000x1000.png" };

	public static final String ModelName[] = { "Model name is required.", "Language is duplicated",
			"inherits default" };

	public static final String UPLOAD_FRAME_STATUS[] = { "display: block;", "display: none;", };

	public static String inputSpecificationHeader[] = { "Connection Type", "Supported Input Channels" };

	public static enum tuningname {
		Partner_Tuning("Partner Tuning"),
		DTS_Tuning("DTS Tuning");
		
		private final String name;
		tuningname (String name) {
			this.name = name;
		}
		public String getName() {
			return this.name;
		}
	};
	
	public static enum required {
		Connection_Type_is_required("Connection Type is required"),
		Approved_tuning_not_be_with_Undetermined("Approved tuning should not be with tuning rating Undetermined. Please choose another rating is No Certification, A or A+"),
		Device_name_is_already_existed("Device name is already existed");
		private final String name;
		required (String name) {
			this.name = name;
		}
		public String getName() {
			return this.name;
		}

	};
	
}
