package com.dts.adminportal.model;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.postgresql.Driver;

import com.dts.adminportal.controller.AppDeviceController;
import com.dts.adminportal.controller.BaseController;

public class DeviceInfo {
	
	// Product Information
	public static final String TITLE_COMPANY = ".//*[@id='user-panel']/div/form/fieldset/div[1]/span[1]/a";
	public static final String TITLE_BRAND = "//*[@id='user-panel']/div/form/fieldset/h4/span[3]";
	public static final String TITLE_NAME = "//*[@id='user-panel']/div/form/fieldset/h4/span[4]";
	public static final String PRODUCT_INFORMATION = "//*[@id='user-panel']/div/form/fieldset/div[2]";
	public static final String EDIT = "//*[@id='edit-product']";
	public static final String CREATE_NEW_VERSION = "//*[@id='create-new-version']";
	public static final String DEVICE_INFO = "//*[@id='product_table']/tbody/tr[1]/td[3]/a";
	public static final String VIEW_PUBLISHED_VERSION = "//a[contains(text(),'View Published Version')]";
	public static final String VIEW_CURRENT_VERSION = "//*[@id='user-panel']/div/form/fieldset/div[2]/span/a";
	public static final String PUBLICATION_STATUS = "//*[@id='device-status']";
	public static final String DTS_TRACKING_ID = "//*[@id='user-panel']/div/form/fieldset/div[4]/span";
	public static final String SALESFORCE_ID = ".//*[@id='user-panel']/div/form/fieldset/div[5]/span";
	public static final String TYPE = "//*[@id='user-panel']/div/form/fieldset/div[6]/span";
	public static final String COMPANY = "//*[@id='user-panel']/div/form/fieldset/div[7]/span";
	public static final String BRAND = "//*[@id='user-panel']/div/form/fieldset/div[8]/span";
	public static final String NAME = "//*[@id='user-panel']/div/form/fieldset/div[9]/span";
	public static final String OS = "//*[@id='user-panel']/div/form/fieldset/div[10]/span";

	// Headphone: X Licensing Info
	public static final String HEADPHONE_X ="//*[@id='user-panel']/div/form/fieldset/div[11]";
	public static final String LICENSE_CONTAINER = "//*[@id='user-panel']/div/form/fieldset/div[12]/div";
	public static final String INIT_KEY_NAME = "//*[@id='user-panel']/div/form/fieldset/div[12]/div/div[3]/span";
	public static final String COMPLETE_PROFILE_LINK = "//*[@id='user-panel']/div/form/fieldset/div[13]/a[1]";
	public static final String INIT_DATABASE_LINK = "//*[@id='user-panel']/div/form/fieldset/div[13]/a[2]";
	
	//Offline Database
	public static final String REFRESH_OFFLINE_DATABASE = "//*[@id='refresh-offline-database']";
	public static final String POPUP_REFRESH_CONFIRM = "html/body/div[6]/div[1]";
	public static final String CANCEL_REFRESH_BUTTON = "html/body/div[6]/div[2]/a[1]";
	public static final String REFRESH_BUTTON = "html/body/div[6]/div[2]/a[2]";
	public static final String INDICATOR_LOADING = "//*[@id='refresh-offline-database-image']";
	public static final String DOWLOAD_DATABASE= "//*[@id='hrefOfflineDatabase']";
	
	// Audio routes
	public static final String AUDIO_ROUTES = ".//*[@id='user-panel']/div/form/fieldset/div[13]";
	public static final String AUDIO_ROUTES_PUBLISHED = ".//*[@id='user-panel']/div/form/fieldset/div[16]";
	public static final String DEFAULT_AUDIO_ROUTE = "//*[@id='user-panel']/div/form/fieldset/div[18]";
	public static final String DEFAULT_AUDIO_ROUTE_PUBLISHED = ".//*[@id='user-panel']/div/form/fieldset/div[17]";
	public static final String DEFAULT_AUDIO_ROUTE_FILE = "//*[@id='user-panel']/div/form/fieldset/div[14]/a";
	public static final String DEFAULT_AUDIO_ROUTE_FILE_PUBLISHED = ".//*[@id='user-panel']/div/form/fieldset/div[17]/a";
	public static final String CUSTOM_TUNING_FILE = "//*[@id='user-panel']/div/form/fieldset/div[15]/a";
	public static final String CUSTOM_TUNING_FILE_PUBLISHED = ".//*[@id='user-panel']/div/form/fieldset/div[18]/a";
	public static final String CUSTOM_TUNING_CONTAINER = "//*[@id='user-panel']/div/form/fieldset/div[15]";
	public static final String CUSTOM_TUNING_CONTAINER_PUBLISHED = ".//*[@id='user-panel']/div/form/fieldset/div[18]";
	

	//Multi-Channel Room Models
	public static final String MULTI_CHANNEL = "//*[@id='user-panel']/div/form/fieldset/div[16]";
	public static final String MULTI_CHANNEL_PUBLISHED = ".//*[@id='user-panel']/div/form/fieldset/div[19]";
	public static final String MULTI_CHANNEL_FIELD = ".//*[@id='user-panel']/div/form/fieldset/div[17]";
	public static final String MULTI_CHANNEL_FIELD_PUBLISHED = ".//*[@id='user-panel']/div/form/fieldset/div[20]";
	
	// In-box Headphones 
	public static final String INBOX_HEADPHONES = ".//*[@id='user-panel']/div/form/fieldset/div[18]";
	public static final String INBOX_INSTRUCTION = ".//*[@id='user-panel']/div/form/fieldset/label";
	public static final String INBOX_UNSPECIFIED = ".//*[@id='user-panel']/div/form/fieldset/div[19]/span";
	public static final String INBOX_PRODUCT_NAME = ".//*[@id='user-panel']/div/form/fieldset/div[19]/a";
	public static final String INBOX_DATE = ".//*[@id='user-panel']/div/form/fieldset/div[19]/span";

	// Featured Accessory Promotions
	public static final String FEATURED_ACCESSORY_PROMOTIONS = ".//*[@id='user-panel']/div/form/fieldset/div[18]";
	public static final String FEATURED_ACCESSORY_PROMOTIONS_PUBLISHED = ".//*[@id='user-panel']/div/form/fieldset/div[21]";
	public static final String PROMOTION_SLOT = ".//*[@id='user-panel']/div/form/fieldset/div[21]/div[1]";
	public static final String PROMOTION_SLOT_PUBLISHED = "//*[@id='user-panel']/div/form/fieldset/div[24]/div[1]";
	public static final String GLOBAL_PROMOTIONS_STATUS = ".//*[@id='user-panel']/div/form/fieldset/div[21]/div[2]";
	public static final String GLOBAL_PROMOTIONS_STATUS_PUBLISHED = ".//*[@id='user-panel']/div/form/fieldset/div[24]/div[2]";

	// Device promotions
	public static final String DEVICE_PROMOTIONS_SECTION = ".//*[@id='user-panel']/div/form/fieldset/div[20]";
	public static final String DEVICE_PROMOTIONS_SECTION_PUBLISHED = ".//*[@id='user-panel']/div/form/fieldset/div[23]";
	public static final String DEVICE_PROMOTIONS_CONTAINER = ".//*[@id='user-panel']/div/form/fieldset/div[21]";
	public static final String DEVICE_PROMOTIONS_CONTAINER_PUBLISHED = "//*[@id='user-panel']/div/form/fieldset/div[24]";
	
	public static final String devicePromtionsContainer(String publish_status){
		if(publish_status.equals("Published")){
			return "//*[@id='user-panel']/div/form/fieldset/div[24]";
		}
		return "//*[@id='user-panel']/div/form/fieldset/div[21]";
	}
	
	//Headphone Image Sizes
	public static final String HEADPHONE_IMAGE_SIZES =".//*[@id='user-panel']/div/form/fieldset/div[22]";
	public static final String HEADPHONE_IMAGE_SIZES_PUBLISHED =".//*[@id='user-panel']/div/form/fieldset/div[25]";
	public static final String HEADPHONE_IMAGE_SIZES_FIELD =".//*[@id='user-panel']/div/form/fieldset/div[23]";
	public static final String HEADPHONE_IMAGE_SIZES_FIELD_PUBLISHED = ".//*[@id='user-panel']/div/form/fieldset/div[26]";
	public static final String LARGE_IMAGE = ".//*[@id='user-panel']/div/form/fieldset/div[23]/span[1]";
	public static final String MEDIUM_IMAGE = ".//*[@id='user-panel']/div/form/fieldset/div[23]/span[2]";
	public static final String SMALL_IMAGE = ".//*[@id='user-panel']/div/form/fieldset/div[23]/span[3]";
	
	//Headphone Brands
	public static final String HEADPHONE_BRAND ="//*[@id='user-panel']/div/form/fieldset/h5[7]";
	public static final String HEADPHONE_BRAND_OPTION ="//*[@id='headphone-brands-placeholder']";
	public static final String HEADPHONE_BRAND_LABEL ="//*[@id='headphone-brands-placeholder']/div/div/div";
	public static final String HEADPHONE_BRAND_PUBLISHED =".//*[@id='user-panel']/div/form/fieldset/div[25]";
	public static final String HEADPHONE_BRAND_OPTION_PUBLISHED ="//*[@id='headphone-brands-placeholder']";
	public static final String HEADPHONE_BRAND_LABEL_PUBLISHED ="//*[@id='headphone-brands-placeholder']/div/div/div";
	
	// Action Module
	public static final String ACTIONS_MODULE = "//*[@id='create-new-company-action']";
	public static final String DELETE = "//*[@id='btnDeleteRecord']";
	public static final String SUSPEND = "//*[@id='btnSuspendRecord']";
	public static final String PUBLISH = "//*[@id='btnPublishRecord']";
	public static final String SUSPEND_CONFIRM_DIALOG = "html/body/div[6]/div[2]";
	
	// Other
	public static final String option1 = "/html/body/div[4]/div[2]/a[1]";
	public static final String option2 = "/html/body/div[4]/div[2]/a[@class='btn btn-danger' and text()='Delete']";

	public static final ArrayList<String> getSections(){
		ArrayList<String> list = new ArrayList<String>();
		list.add(PRODUCT_INFORMATION);
		list.add(HEADPHONE_X);
		list.add(AUDIO_ROUTES);
		list.add(FEATURED_ACCESSORY_PROMOTIONS);
		return list;
	}
	
	public static ArrayList<String> getElementInfo(String status){
		ArrayList<String> data = new ArrayList<String>();
		if(status.equals("Published")){
			data.add(PRODUCT_INFORMATION);
			data.add(PUBLICATION_STATUS);
			data.add(DTS_TRACKING_ID);
			data.add(SALESFORCE_ID);
			data.add(TYPE);
			data.add(COMPANY);
			data.add(BRAND);
			data.add(NAME);
			data.add(OS);
			data.add(HEADPHONE_X);
			data.add(LICENSE_CONTAINER);
			data.add(AUDIO_ROUTES_PUBLISHED);
			data.add(FEATURED_ACCESSORY_PROMOTIONS_PUBLISHED);
			data.add(PROMOTION_SLOT_PUBLISHED);
			data.add(GLOBAL_PROMOTIONS_STATUS_PUBLISHED);
			data.add(DEVICE_PROMOTIONS_SECTION_PUBLISHED);
		}
		else{
			data.add(PRODUCT_INFORMATION);
			data.add(PUBLICATION_STATUS);
			data.add(DTS_TRACKING_ID);
			data.add(SALESFORCE_ID);
			data.add(TYPE);
			data.add(COMPANY);
			data.add(BRAND);
			data.add(NAME);
			data.add(OS);
			data.add(HEADPHONE_X);
			data.add(LICENSE_CONTAINER);
			data.add(AUDIO_ROUTES);
			data.add(FEATURED_ACCESSORY_PROMOTIONS);
			data.add(PROMOTION_SLOT);
			data.add(GLOBAL_PROMOTIONS_STATUS);
			data.add(DEVICE_PROMOTIONS_SECTION);
			
		}
		
		return data;
	}
	
	public static ArrayList<String> getDeviceInfo(){
		ArrayList<String> data = new ArrayList<String>();
		data.add(TYPE);
		data.add(COMPANY);
		data.add(BRAND);
		data.add(NAME);
		return data;
	}
	
}
