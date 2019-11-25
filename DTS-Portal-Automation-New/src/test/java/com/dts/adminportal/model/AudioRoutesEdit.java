package com.dts.adminportal.model;

import java.util.Hashtable;

public class AudioRoutesEdit {

	public static final String DISPLAY_MODEL  = "//*[@id='display-model']";
	public static final String UUID  = "//*[@id='entity-uuid']";
	public static final String SALESFORCE_ID  = "//*[@id='input-salesforceid']";
	public static final String DISPLAY_NAME  = "//*[@id='display-name']";
	public static final String LANGUAGE_CONTAINER  = "//*[@id='lang-div-container']";
	public static final String LANGUAGE_COMBOBOX = "//*[@id='lang-div-container']/div/select";
	public static final String LANGUAGE_INPUT  = "//*[@id='lang-div-container']/div[1]/input";
	public static final String LANGUAGE_TRASH = "//*[@id='lang-div-container']/div[1]/i";
	public static final String AUDIO_ROUTE_ID  = "//*[@id='router-routeid']";
	public static final String TYPE  = "//*[@id='router-type']";
	public static final String INPUT_SPECIFICATION  = "//*[@id='input-specifications-div']";
	public static final String  WIRED_CONTAINER  = "//*[@id='input-specifications-div']//tr[2]";
	public static final String WIRED_CHECKBOX  = "//*[@id='checkboxWired']";
	public static final String WIRED_DROPBOX = "//*[@id='input-specifications-div']//tr[2]//td[2]//button";
	public static final String  BLUETOOTH_CONTAINER  = "//*[@id='input-specifications-div']//tr[3]";
	public static final String BLUETOOTH_DROPBOX  = "//*[@id='input-specifications-div']//tr[3]//td[2]//button";
	public static final String BLUETOOTH_CHECKBOX  = "//*[@id='checkboxBluetooth']";
	public static final String ROUTER_TUNING  = "//*[@id='tuning-title-label']";
	public static final String ACTION_MODULE  = "//*[@id='audio-route-action']";
	public static final String EDIT  = "//*[@id='edit-model']";
	public static final String SAVE  = "//*[@id='save_action']";
	public static final String CANCEL  = "//*[@id='cancel_action']";
	public static final String POPUP_OK = "html/body/div[6]/div[2]/a[2]";
	public static final String USB_CONTAINER  = "//*[@id='input-specifications-div']/table/tbody/tr[4]";
	public static final String USB_CHECKBOX  = "//*[@id='checkboxUSB']";
	public static final String USB_DROPBOX  = "//*[@id='input-specifications-div']/table/tbody/tr[4]/td[2]/div/button";
	
	//Audio route
	public static final String DELETE_TUNING_ICON  = "//img[contains(@onclick,'confirmDeleteTuningFile')]";
	public static final String ADD_TUNING  = "//*[@id='add-tuning-material-btn']/span";
	public static final String AUDIOROUTE_UPLOAD_MESSAGE = "//*[contains(@id,'tuning-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String RETRY_UPLOAD_AUDIOROUTE = "//img[contains(@onclick,'retryUploadTuning')]";
	public static final String CANCEL_UPLOAD_AUDIOROUTE = "//*[@id='tuning-progress']//img[@class='icon-close-icon']";
	public static final String AUDIOROUTE_DRAG_DROP_AREA = "//*[@id='dropzone-tuning']"; 
	public static final String UPLOADED_AUDIO_ROUTE = "//*[@id='uploaded-tuning-material']"; 
	public static final String INVALID_MESSAGE = "//*[@id='tuning-fileupload--1']/table/tbody/tr[2]/td/p/span";
 
	// Image
	public static final String IMAGE_CATALOG  = "//div[@id='product-image']/parent::div";
	public static final String ADD_IMAGE250  = "//*[@id='add-logo124Image-btn']/span";
	public static final String ADD_IMAGE500  = "//*[@id='add-logo256Image-btn']/span";
	public static final String ADD_IMAGE1000  = "//*[@id='add-logo512Image-btn']/span";
	public static final String IMAGE250_DISPLAY  = "//*[@id='logo124ImageShow']";
	public static final String IMAGE500_DISPLAY  = "//*[@id='logo256ImageShow']";
	public static final String IMAGE1000_DISPLAY  = "//*[@id='logo512ImageShow']";
	public static final String CATALOG_IMAGE_UPLOAD_THUMBNAIL_250="//div[@id='uploaded-logo124Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/th/a/img";
	public static final String CATALOG_IMAGE_UPLOAD_THUMBNAIL_500="//div[@id='uploaded-logo256Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/th/a/img";
	public static final String CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000="//div[@id='uploaded-logo512Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/th/a/img";
	public static final String CATALOG_IMAGE_UPLOAD_MESSAGE_250 ="//div[@id='uploaded-logo124Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String CATALOG_IMAGE_UPLOAD_MESSAGE_500="//div[@id='uploaded-logo256Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String CATALOG_IMAGE_UPLOAD_MESSAGE_1000="//div[@id='uploaded-logo512Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/p/span";
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
	public static final String LIGHTBOX_CLOSE ="//*[@id='lightbox']/div[2]/div/div[2]/a";
	public static final String LIGHTBOX_STYLE_IMAGE = "//img[@class = 'lb-image']";
	public static final String CANCEL_UPLOAD_MARKETING = "//*[@id='marketing-progress']//img[@class = 'icon-close-icon']";
	
	public enum img_name {
		IMG250_JPG("250x250.jpg"),
		IMG500_JPG("500x500.jpg"),
		IMG1000_JPG("1000x1000.jpg"),
		IMG250_PNG("250x250.png"),
		IMG500_PNG("500x500.png"),
		IMG1000_PNG("1000x1000.png");
		
		private final String img;

		img_name (String img) {
			this.img = img;
		}

		public String getName() {
			return this.img;
		}
	};
	
	public static final String SUPPORTED_INPUT_CHANNELS[]={
		"Select all that apply",
		"1 (Mono)",
		"2 (Stereo)"
	};
	
	public enum Upload_File_Message {
		File_upload_successful("File upload successful"), //0
		Invalid_tuning_file("! Invalid tuning file"),
		Invalid_file("! Invalid file"),
		File_upload_canceled("File upload canceled"),//2
		File_upload_error("File upload error"),
		Automatically_resize_250("Automatically resize. Original 250x250 px"), // 4
		Automatically_resize_500("Automatically resize. Original 500x500 px"),
		Automatically_resize_1000("Automatically resize. Original 1000x1000 px"), // 6
//		Invalid_file_contents("Invalid file contents: Route, it must appropriate the Audio Route"),
		Invalid_file_contents("Invalid file: The Route object does not match the Audio Route selection"),
		Invalid_file_48k_for_3D("Invalid file: 48k coefficients for 3D Headphone EQ is missing."),//8
		Invalid_file_48k_for_AEQ(" Invalid file: 48k coefficients for AEQ is missing in iirCoefs."),
		Invalid_file_44_1k_48k_for_3D("Invalid file: 44.1k and/or 48k coefficients for 3D Headphone EQ are missing."), // 10
		Invalid_file_44_1k_48k_for_AEQ("Invalid file: 44.1k and/or 48k coefficients for AEQ are missing in iirCoefs."),
		Invalid_file_Please_upload_the_completed_DTS_Headphone_X_tuning_file("! Invalid file: Please upload the completed DTS Headphone:X tuning file."),
		Invalid_file_48k_coefficients_is_missing_in_TBHDX_MBHL("! Invalid file: 48k coefficients is missing in TBHDX and/or MBHL."),
		Invalid_file_The_MBHL_isnot_applied_to_this_tuning_type("! Invalid file: The MBHL isn't applied to this tuning type. Please upload another tuning file without MBHL data.");
		private final String message;
		Upload_File_Message (String message) {
			this.message = message;
		}
		public String getName() {
			return this.message;
		}
	};

	public static final Hashtable<String, String> getElementsInfo(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("model", DISPLAY_MODEL);
		data.put("uuid", UUID);
		data.put("salesforceid", SALESFORCE_ID);
		data.put("audio route id", AUDIO_ROUTE_ID);
		//data.put("type", TYPE);
		data.put("input specification", INPUT_SPECIFICATION);
		data.put("router tuning", ROUTER_TUNING);
		data.put("image catalog", IMAGE_CATALOG);
		return data;
	}
	public static enum err_message {
		Name_is_required("Display Name is required"),
		Name_with_character_limit("Display Name exceeds 255 characters limit");
		
		private final String mes;

		err_message (String mes) {
			this.mes = mes;
		}

		public String getName() {
			return this.mes;
		}
	};
	
	public static enum OTHER_ROUTE_DISPLAYNAME {
		Speakers_Default("Internal Speakers (Default)"), 
		Speakers_Off("Internal Speakers (Off)"),
		Speakers_Mode_1("Internal Speakers (mode 1)"),
		Speakers_Mode_2("Internal Speakers (mode 2)"),
		Speakers_Mode_3("Internal Speakers (mode 3)"),
		Speakers_Mode_4("Internal Speakers (mode 4)"),
		Dual_Audio("Dual Audio"),
		External_Audio("External Audio");
		
		private final String name;

		OTHER_ROUTE_DISPLAYNAME (String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	};
	
	public static final String getDeleteLangIcon(int numOfLang){
		return ".//*[@id='lang-div-container']/div["+numOfLang+"]/i";
	}
	
	// Audio UUID
	public static enum AudioUUIDs {
		OverEar_Headphones("f08a4c48-0887-11e4-9191-0800200c9a11"),Earbuds("f08a4c48-0887-11e4-9191-0800200c9a13"),Ear_piece(" f08a4c48-0887-11e4-9191-0800200c9a15"),Car_Audio("f08a4c48-0887-11e4-9191-0800200c9a12"),External_Speakers("f08a4c48-0887-11e4-9191-0800200c9a14"),
		StereoRoom0("a7ef49b3-fadd-4269-9047-947987e5910c"),StereoRoom1("98b232ac-973e-404c-9a0d-4b0cb9ae3f43"),
		Attached0("f08a4c48-0887-11e4-9191-0800200c9a19"),Attached1("f08a4c48-0887-11e4-9191-0800200c9a20"),Attached2(" f08a4c48-0887-11e4-9191-0800200c9a16"),Attached3("f08a4c48-0887-11e4-9191-0800200c9a28"),
		Attached4("f08a4c48-0887-11e4-9191-0800200c9a29"),Attached5("f08a4c48-0887-11e4-9191-0800200c9a30"),Combo0("f08a4c48-0887-11e4-9191-0800200c9a17"),Default_External_Audio("f08a4c48-0887-11e4-9191-0800200c9a18"),
		McRoom0("f08a4c48-0887-11e4-9191-0800200c9a25"),McRoom1("f08a4c48-0887-11e4-9191-0800200c9a26"),McRoom2("f08a4c48-0887-11e4-9191-0800200c9a27");

		private final String audio;

		AudioUUIDs(String audio) {
			this.audio = audio;
		}

		public String getUUID() {
			return this.audio;
		}
	}
	public static final String AudiRouteID  = "Line-out0, Bluetooth0, USB";
}
