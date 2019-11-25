package com.dts.adminportal.model;

import java.util.Hashtable;

public class AudioRoutes {
	public static final String HEADER  = "//*[@id='router-audio-brand-info']/div/span";
	public static final String DESCRIPTION  = "//*[@id='router-audio-brand-info']/b[contain(text(),'There are the ultimate fallback']";
	public static final String STANDARD_ACCESSORIES_LABEL  = "//*[@id='router-audio-brand-info']/h5[1]";
	public static final String INTERNAL_SPEAKER_LABEL  = "//*[@id='router-audio-brand-info']/h5[2]";
	public static final String OTHER_AUDIO_ROUTES_LABEL  = "//*[@id='router-audio-brand-info']/h5[3]";
	public static final String HEADER_STRING = "Audio Routes";
	public static final String DESCRIPTION_STRING = "There are the ultimate fallback tunings for all audio routes supported by Headphone:X Partner Portal";
	public static final String STANDARD_ACCESSORIES_STRING = "Standard Accessories: Line-out0 and Bluetooth0";
	public static final String INTERNAL_SPEAKER_STRING = "Internal speaker: Attached0 (Portrait) and Attached1 (Landscape)";
	public static final String OTHER_AUDIO_ROUTES_STRING  = "Other Audio Routes";
	public static final String LINK_OTHER_AUDIO = "//*[@id='other_router']/a";
	public static final String LINK_STANDARD_AUDIO = "//*[@id='standard_router']/a";
	public static final String LINK_STEREO_ROOM = "//*[@id='stereo_room']/a";
	public static final String LINK_MCROOM = "//*[@id='multi_channel_room']/a";
	public static final String MULTI_CHANNEL_CONTAINER = "//*[@id='multi_channel_room']";
	public static final String STANDARD_ROUTE_CONTAINER = "//*[@id='standard_router']";
	public static final String OTHER_ROUTE_CONTAINER = "//*[@id='other_router']";
	public static final String STEREO_ROOM_MODELS_CONTAINER = "//*[@id='stereo_room']";
	public static final String MCROOM0 = "//*[@id='multi_channel_room']/a[1]";
	public static final String MCROOM1 = "//*[@id='multi_channel_room']/a[2]";
	public static final String MCROOM2 = "//*[@id='multi_channel_room']/a[3]";
	public static final String POPUP_OK = "html/body/div[6]/div[2]/a[2]";
	//Audio Details
	
	public static final String AUDIO_DETAILS_HEADER  = "//*[@id='display-model']";
	public static final String AUDIO_DETAILS_INFO  = "//*[@id='edit-route-form']/fieldset/div[1]/span[1]/b";
	public static final String AUDIO_DETAILS_PUBLISH_STATUS  = "//*[@id='publication-status']";
	public static final String AUDIO_DETAILS_UUID  = "//*[@id='entity-uuid']";
	public static final String AUDIO_DETAILS_SALESFORCE_ID  = "//*[@id='salesforce-id']";
	public static final String AUDIO_DETAILS_DISPLAY_NAME  = "//*[@id='router-localname']";
	public static final String AUDIO_DETAILS_DEFAULT_NAME  = "//*[@id='display-name']";
	public static final String AUDIO_DETAILS_ROUTE_ID  = "//*[@id='router-routeid']";
	public static final String AUDIO_DETAILS_TYPE  = "//*[@id='router-type']";
	public static final String AUDIO_DETAILS_INPUT_SPECIFICATIONS  = "//*[@id='input-specifications-div']";
	public static final String AUDIO_DETAILS_PRODUCT_IMAGES  = "//*[@id='edit-route-form']/fieldset/div[9]/span[1]";
	public static final String AUDIO_DETAILS_EDIT_VERSION = "//*[@id='edit-model']";
	public static final String AUDIO_DETAILS_SIZE_LABEL_LOGO_1 = "//*[@id='brandLogoTable']/tbody/tr[1]/td[1]";
	public static final String AUDIO_DETAILS_SIZE_LABEL_LOGO_2 = "//*[@id='brandLogoTable']/tbody/tr[1]/td[2]";
	public static final String AUDIO_DETAILS_SIZE_LABEL_LOGO_3 = "//*[@id='brandLogoTable']/tbody/tr[1]/td[3]";
	public static final String AUDIO_DETAILS_TUNNING_LINK  = "//*[@class='uploadedTuningFiles']";
	public static final String AUDIO_DETAILS_SIZE_LABEL_1 = "160X160";
	public static final String AUDIO_DETAILS_SIZE_LABEL_2 = "290X290";
	public static final String AUDIO_DETAILS_SIZE_LABEL_3 = "664X664";
	public static final String AUDIO_DETAILS_TUNNING_FILE = "default-headphone-1.1.0.dtscs";
	public static final String AUDIO_DETAILS_PUBLISHING_MODULE  = "//*[@id='create-accessory-action']";
	public static final String AUDIO_DETAILS_PUBLISH_LINK  = "//*[@id='publish_action']";
	public static final String AUDIO_DETAILS_DELETE_LINK  = "//*[@id='delete_action']";
	public static final String AUDIO_DETAILS_DELETE_BUTTON  = "/html/body/div[4]/div[2]/a[2]";
	public static final String AUDIO_DETAILS_CANCEL_BUTTON  = "/html/body/div[4]/div[2]/a[1]";
	public static final String AUDIO_DETAIL_TUNING_FILE  = "//*[@id='router-tuning']";
	
	//Stereo Room
	public static final String STEREO_ROOM0  = ".//*[@id='13968']";
	public static final String STEREO_ROOM_UUID  = "//*[@id='entity-uuid']";
	public static final String STEREO_ROOM_ID  = "//*[@id='router-routeid']";
	
	public static final Hashtable<String, String> getHash(){
		Hashtable<String, String> detail = new Hashtable<String, String>();
		detail.put("audio details header", AUDIO_DETAILS_HEADER);
		detail.put("audio details info", AUDIO_DETAILS_INFO);
		detail.put("audio details uuid", AUDIO_DETAILS_UUID);
		detail.put("audio details salesforce id", AUDIO_DETAILS_SALESFORCE_ID);
		detail.put("audio details display name", AUDIO_DETAILS_DISPLAY_NAME);
		detail.put("audio details route id", AUDIO_DETAILS_ROUTE_ID);
		detail.put("audio details type", AUDIO_DETAILS_TYPE);
		detail.put("audio details input specifications", AUDIO_DETAILS_INPUT_SPECIFICATIONS);
		detail.put("audio details product images", AUDIO_DETAILS_PRODUCT_IMAGES);
		return detail;
	}
	
	//Edit Audio
	
	public static final String EDIT_AUDIO_FORM  = "//*[@id='edit-route-form']/fieldset";
	public static final String EDIT_AUDIO_SALESFORCE_ID  = "//*[@id='input-salesforceid']";
	public static final String EDIT_AUDIO_DISPLAY_NAME  = "//*[@id='display-name']";
	public static final String EDIT_AUDIO_DESCRITION_INPUT  = "//*[@id='description-input']";
	public static final String EDIT_LANGUAGE_SELECT  = "//*[@id='lang-div-container']/div/select";
	public static final String EDIT_LANGUAGE_INPUT  = "//*[@id='lang-div-container']/div[1]/input";
	public static final String EDIT_LANGUAGE_TRASH = "//*[@id='lang-div-container']/div[1]/i";
	public static final String EDIT_LANGUAGE_CONTAINER  = "//*[@id='lang-div-container']";
	public static final String EDIT_AUDIO_WIRED_SIZE  = "//*[@id='checkboxWired']";
	public static final String EDIT_UPLOAD_TUNNING  = "//*[@id='add-tuning-model-btn']";
	public static final String EDIT_DELETE_TUNNING  = "//*[contains(@id,'delete-tuning-model-btn')]";
	public static final String EDIT_TUNNING_MESSAGE  = "//*[@id='model-tuning-files-msg']";
	public static final String EDIT_BRAND_LOGO_TABLE  = "//*[@id='brandLogoTable']";
	public static final String EDIT_UPLOAD_IMAGE1  = "//*[@id='logo124Image']";
	public static final String EDIT_UPLOAD_IMAGE2  = "//*[@id='logo256Image']";
	public static final String EDIT_UPLOAD_IMAGE3  = "//*[@id='logo512Image']";
	public static final String EDIT_AUDIO_SAVE  = "//*[@id='save_action']";
	public static final String EDIT_AUDIO_CANCEL  = "//*[@id='cancel_action']";
	public static final String EDIT_TYPE  = "//*[@id='router-type']/span";
	public static final String EDIT_AUDIO_ROUTE_ID  = "//*[@id='router-routeid']/span";
	
	public static final String  TUNING_FILE = "//*[@id='model-tuning-files']";
	public static final String EDIT_AUDIO_DETAILS_HEADER  = "//*[@id='display-model']";
	public static final String SIZE_LIST[] = {
		"160X160",									
		"290X290", 
		"664X664",
	};
	
	public static final String getLang_selectxpath(int numoflang){
		return ".//*[@id='lang-div-container']/div["+numoflang+"]/select";
	}
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
	
	public static final String NAME_LIST[] = {
		"",
		"å¤–éƒ¨æ‰¬å£°å™¨",									
		"å¤–éƒ¨æ�šè�²å™¨", 
		"Haut-parleur externe",
		"externe Lautsprecher",									
		"altoparlante esterno", 
		"å¤–éƒ¨ã‚¹ãƒ�?ãƒ¼ã‚«ãƒ¼",
		"오버 �?�어 헤드�?�",									
		"�?’�?½�?µÑˆ�?½�?¸�?¹ �?´�?¸�?½�?°�?¼�?¸�?º", 
		"altavoz externo",
	};
	
	public static final String STANDARD_ROUTES[] = {
		"Over-Ear Headphones",									
		"Earbuds", 
		"Ear-piece",
		"External Speaker",									
		"Car Audio"
	};
	
	public static final String OTHER_ROUTES[] = {
		"Attached0 - Internal Speakers (Default)",
		"Attached1 - Internal Speakers (Off)",
		"Attached2 - Internal Speakers (mode 1)",
		"Attached3 - Internal Speakers (mode 2)",
		"Attached4 - Internal Speakers (mode 3)",
		"Attached5 - Internal Speakers (mode 4)",
		"Combo0 - Dual Audio",
		"Default - External Audio"
	};
	

	public static final String STEREO_ROOM_MODELS[] = {
		"StereoRoom0 - Wide",									
		"StereoRoom1 - In-front", 
	};
	
	public static final String MC_ROOM[] = {
		"McRoom0 -",
		"McRoom1 -",
		"McRoom2 -"
	};
}
