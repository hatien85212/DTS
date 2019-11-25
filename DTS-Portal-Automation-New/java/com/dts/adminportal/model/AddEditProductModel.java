package com.dts.adminportal.model;

import java.util.ArrayList;
import java.util.Hashtable;

import org.testng.Assert;

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
	public static final String RELEASE_DATE = "//*[@id='releaseDate']";
	public static final String ALIASES = "//*[@id='aliases']";
	public static final String DESCRIPTION = "//*[@id='description']";
	public static final String DESCRIPTOR = "//*[@id='descriptor']"; 		// NOTE: In variant, it's descriptor, not description
	public static final String SAVE_PRODUCT = "//*[@id='save-accessory']";
	public static final String CANCEL_PRODUCT = "//*[@id='cancel-accessory']";
	public static final String SAVE_VARIANT = "//*[@id='save-variant']";
	public static final String CANCEL_VARIANT = "//*[@id='cancel-addVariant']";
	public static final String NAME_ERROR_MESSAGE = "//*[@id='display-name-msg']";
	public static final String DUPLICATE_WARNING_MESSAGE = "Language is duplicated";
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
	public static final String CONNECTION_TYPE_STRING = "Connection Type is required";
	public static final String INPUT_CHANNEL_ERROR_STRING = "You have to select at least an item";

	// Blue-tooth connection
	public static final String BLUETOOTH_CONTAINER = "//*[@id='input-specifications-div']//tr[3]";
	public static final String BLUETOOTH_CHECKBOX = "//*[@id='checkboxBluetooth']";
	public static final String BLUETOOTH_DROPBOX = "//*[@id='input-specifications-div']//tr[3]//button";
	public static final String BLUETOOTH_MESSAGE = "//*[@id='display-channel-msg-Bluetooth']";
	public static final String CONNECTION_REQUIRE_MESSAGE = "//*[@id='display-connectiontype-msg']";

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
	public static final String ADD_MARKETING = "//*[@id='add-marketing-material-btn']";
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
		USE_PARENT("Use Parent's Images will discard the custom files.");
		
		
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
		WIRED("Wired (3.5 mm)"), BLUETOOTH("Bluetooth");
		
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
		SELECT_ALL("Select all that apply"), SINGLE_AND_STEREO("1 (Single Channel), 2 (Stereo)"), 
		SINGLE_CHANNEL("1 (Single Channel)"), STEREO("2 (Stereo)");
		
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
		IMG_250_JPG("250x250.jpg"), IMG_500_JPG("500x500.jpg"), 
		IMG_1000_JPG("1000x1000.jpg"), IMG_250_PNG("250x250.png"), IMG_500_PNG("500x500.png"),
		IMG_1000_PNG("1000x1000.png"),Tuning_Invalid("tuning-based-on-JSON.zip"),AUDIO_ROUTE_FILE("default-headphone-1.1.0.dtscs"),
		Tuning_HPXTT("bt-tuning.hpxtt"), Tuning_ZIP("bt-tuning.zip"), File_Upload_Cancel("Test-upload-cancel.dts"),
		X_Cancel_Upload("Cancel-upload.PNG"),Attached0_Internal_Speakers_Default("Attached0-InternalSpeakers(Default).dtscs"),
		Attached1_Internal_Speakers_Off("Attached1-InternalSpeakers(Off).dtscs"),Attached2_Internal_Speakers_mode1("Attached2-InternalSpeakers(mode1).dtscs"),
		Attached3_Internal_Speakers_mode2("Attached3-InternalSpeakers(mode2).dtscs"),Attached4_Internal_Speakers_mode3("Attached4-InternalSpeakers(mode3).dtscs"),
		Attached5_Internal_Speakers_mode4("Attached5-InternalSpeakers(mode4).dtscs"),Default_External_Audio("Default-ExternalAudio.dtscs"),
		Earbuds("Earbuds.dtscs"),Earpiece("Earpiece.dtscs"),External_Speakers("External-Speakers.dtscs"),Over_Ear_Headphones("Over-Ear-Headphones.dtscs"),Car_Audio("Car-Audio.dtscs");
		
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
	
	public static final String SIZE_LIST[] = { "250x250", "500x500", "1000x1000", };
	public static final String PARTNER_SIZE_LIST[] = { "250x250", "500x500", "1000x1000", };

	public static final String DIV_STATUS[] = { "style=\"display: block;\"", "style=\"display: none;\"", };

	public static final String IMG_NAME[] = { "250x250.jpg", "500x500.jpg", "1000x1000.jpg", "250x250.png",
			"500x500.png", "1000x1000.png" };

	public static final String ModelName[] = { "Model name is required.", "Language is duplicated",
			"inherits default" };

	public static final String UPLOAD_FRAME_STATUS[] = { "display: block;", "display: none;", };

	public static String inputSpecificationHeader[] = { "Connection Type", "Supported Input Channels" };

	public static String tuningname[] = {
			"Partner Tuning",
			"DTS Tuning"
	};
	
	
}
