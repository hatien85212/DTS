package com.dts.adminportal.model;

import java.util.ArrayList;

public class DeviceInfo {
	
	// Product Information
	public static final String TITLE_COMPANY = "//*[@id='user-panel']/div/form/fieldset/div[1]/span[1]/a";
	public static final String TITLE_BRAND = "//*[@id='user-panel']/div/form/fieldset/div/span[contains(@data-bind, 'text: brandName')]";
	public static final String TITLE_NAME = "//*[@id='user-panel']/div/form/fieldset/div/span[contains(@data-bind, 'text: name')]";
	public static final String PRODUCT_INFORMATION = "//*[@id='user-panel']/div/form/fieldset/div[2]";
	public static final String EDIT = "//*[@id='edit-product']";
	public static final String CREATE_NEW_VERSION = "//*[@id='create-new-version']";
	public static final String VIEW_PUBLISHED_VERSION = "//a[contains(text(),'View Published Version')]";
	public static final String VIEW_CURRENT_VERSION = "//a[contains(text(),'View Current Version')]";
	public static final String PUBLICATION_STATUS = "//*[@id='device-status']";
	public static final String DTS_TRACKING_ID = "//*[@id='user-panel']/div/form/fieldset/div/span[contains(@data-bind, 'text: dtsId')]";
	public static final String SALESFORCE_ID = "//*[@id='user-panel']/div/form/fieldset/div/span[contains(@data-bind, 'text: salesforceId')]";
	public static final String TYPE = "//*[@id='user-panel']/div/form/fieldset/div/span[contains(@data-bind, 'text: type')]";
	public static final String EAGLE_VERSION = "//*[@id='user-panel']/div/form/fieldset/div/span[contains(@data-bind, 'text: eagleVersion')]";
	public static final String PRODUCT_TYPE = "//*[@id='user-panel']/div/form/fieldset/div/span[contains(@data-bind, 'text: deviceProductType')]";
	public static final String COMPANY = "//*[@id='user-panel']/div/form/fieldset/div/span[contains(@data-bind, 'text: companyName')]";
	public static final String BRAND = "//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'attr: {title : brandNameTitle}')]/span[contains(@data-bind, 'text: brandName')]";
	public static final String NAME = "//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'attr: {title : nameTitle}')]/span[contains(@data-bind, 'text: name')]";
	public static final String OS = "//*[@id='user-panel']/div/form/fieldset/div/span[contains(@data-bind, 'text: platform')]";

	// Headphone: X Licensing Info
	public static final String HEADPHONE_X ="//*[@id='user-panel']/div/form/fieldset/div[contains(text(),'Headphone:X Licensing Info')]";
	public static final String LICENSE_CONTAINER = "//*[@id='user-panel']/div/form/fieldset/div[contains(@class, 'license-containner')]/div";
	public static final String INIT_KEY_NAME = "//*[@id='user-panel']/div/form/fieldset/div[contains(@class, 'license-containner')]/div/div[3]/span";
	public static final String COMPLETE_PROFILE_LINK = "//*[@id='user-panel']/div/form/fieldset/div[contains(@class, 'detail-element')]/a[contains(@class, 'complete-profile')]";
	public static final String INIT_DATABASE_LINK = "//*[@id='user-panel']/div/form/fieldset/div[contains(@class, 'detail-element')]/a[contains(@class, 'init-database')]";
	
	//Offline Database
	public static final String OFFLINE_DATABASE = "//*[@id='user-panel']/div/form/fieldset/div[contains(text(),'Offline Database')]";
	public static final String REFRESH_OFFLINE_DATABASE = "//*[@id='refresh-offline-database']";
	public static final String REFRESH_OFFLINE_DATABASE_APO = "//*[@id='refresh-offline-database-apo']";
	public static final String POPUP_REFRESH_CONFIRM = "html/body/div[6]/div[1]";
	public static final String CANCEL_REFRESH_BUTTON = "html/body/div[6]/div[2]/a[text()='Cancel']";
	public static final String REFRESH_BUTTON = "html/body/div[6]/div[2]/a[text()='Refresh']";
	public static final String INDICATOR_LOADING = "//*[@id='refresh-offline-database-image']";
	public static final String DOWNLOAD_DATABASE= "//*[@id='hrefOfflineDatabase']";
	public static final String DOWNLOAD_DATABASE_APO_UI = "//*[@id='hrefOfflineDatabaseUI']";
	public static final String DOWNLOAD_DATABASE_APO_TUNING = "//*[@id='hrefOfflineDatabaseTuning']";
	public static final String OFFLINE_DATABASE_UI_REFRESHED_DATE="//*[@id='user-panel']/div/form/fieldset/div[contains(@class, 'detail-element')]/span[contains(@data-bind, 'dateString: offlineDatabaseAPO()[0].updatedOn')]";
	public static final String OFFLINE_DATABASE_TUNING_REFRESHED_DATE="//*[@id='user-panel']/div/form/fieldset/div[contains(@class, 'detail-element')]/span[contains(@data-bind, 'dateString: offlineDatabaseAPO()[1].updatedOn')]";
	
	// Audio routes
	public static final String AUDIO_ROUTES = "//*[@id='user-panel']/div/form/fieldset/div[contains(text(),'Audio Routes')]";
	public static final String AUDIO_ROUTES_PUBLISHED = "//*[@id='user-panel']/div/form/fieldset/div[contains(text(),'Audio Routes')]";
	public static final String DEFAULT_AUDIO_ROUTE = "//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'foreach: routes')][1]";
	public static final String DEFAULT_AUDIO_ROUTE_PUBLISHED = "//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'foreach: routes')][1]";
	public static final String DEFAULT_AUDIO_ROUTE_FILE = "//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'foreach: routes')][1]/a";
	public static final String DEFAULT_AUDIO_ROUTE_FILE_PUBLISHED = "//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'foreach: routes')][1]/a";
	public static final String CUSTOM_TUNING_FILE = "//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'foreach: routes')][2]/a";
	public static final String CUSTOM_TUNING_FILE_PUBLISHED = "//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'foreach: routes')][2]/a";
	public static final String CUSTOM_TUNING_CONTAINER = "//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'foreach: routes')][2]";
	public static final String CUSTOM_TUNING_CONTAINER_PUBLISHED = "//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'foreach: routes')][2]";
	public static final String UPLOADED_CUSTOM_TUNNING_NAME_VER20="//*[@id='user-panel']/div/form/fieldset/div[18]/a";

	// Device Tuning
	public static final String DEVICE_TUNING_CONTAINER= "//div[contains(@data-bind, 'foreach: deviceTunings')]";
	
	//Multi-Channel Room Models
	public static final String MULTI_CHANNEL = "//*[@id='user-panel']/div/form/fieldset/div[contains(text(),'Multi-Channel Room Models')]";
	public static final String MULTI_CHANNEL_PUBLISHED = "//*[@id='user-panel']/div/form/fieldset/div[contains(text(),'Multi-Channel Room Models')]";
	public static final String MULTI_CHANNEL_FIELD = "//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'foreach: deviceRoommodelDTO')]";
	public static final String MULTI_CHANNEL_FIELD_PUBLISHED = "//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'foreach: deviceRoommodelDTO')]";
	
	// In-box Headphones 
	public static final String INBOX_HEADPHONES = "//*[@id='user-panel']/div/form/fieldset/div[contains(text(),'In-box Headphones')]";
	public static final String INBOX_INSTRUCTION = "//*[@id='user-panel']/div/form/fieldset/label";
	public static final String INBOX_UNSPECIFIED = "//*[@id='user-panel']/div/form/fieldset/div/span[text()='Unspecified']";
	public static final String INBOX_PRODUCT_NAME_DRAFT = "//*[@id='user-panel']/div/form/fieldset/div/a[contains(@data-bind, 'attr: {title : inboxHeadphone().modelName, href: inboxHeadphone().uri }, text:inboxHeadphone().modelName')]";
	public static final String INBOX_DATE = "//*[@id='user-panel']/div/form/fieldset/div/span[contains(@data-bind, 'dateString: inboxHeadphone().updatedDate ')]";
	
	public static final String getInboxHeadphoneXpath(String productName){
		return "//a[.='"+productName+"']";
	}

	// Featured Accessory Promotions
	public static final String FEATURED_ACCESSORY_PROMOTIONS = "//*[@id='user-panel']/div/form/fieldset/div[contains(text(),'Featured Accessory Promotions')]";
	public static final String FEATURED_ACCESSORY_PROMOTIONS_PUBLISHED = "//*[@id='user-panel']/div/form/fieldset/div[contains(text(),'Featured Accessory Promotions')]";
	public static final String PROMOTION_SLOT = "//*[@id='user-panel']/div/form/fieldset/div/div[1]/span[contains(@data-bind, 'text: numPromoSlots')]";
	public static final String PROMOTION_SLOT_PUBLISHED = "//*[@id='user-panel']/div/form/fieldset/div/div[1]/span[contains(@data-bind, 'text: numPromoSlots')]";
	public static final String GLOBAL_PROMOTIONS_STATUS = "//*[@id='user-panel']/div/form/fieldset/div/div[contains(@data-bind, 'attr : {title :globalPromoTitle}')]/span";
	public static final String GLOBAL_PROMOTIONS_STATUS_PUBLISHED = "//*[@id='user-panel']/div/form/fieldset/div/div[contains(@data-bind, 'attr : {title :globalPromoTitle}')]/span";
	public static final String PROMO_SLOT = "//*[@id='user-panel']/div/form/fieldset/div/div[1]/span[contains(@data-bind, 'text: numPromoSlots')]";

	// Device promotions
	public static final String DEVICE_PROMOTIONS_SECTION = "//*[@id='user-panel']/div/form/fieldset/div[contains(text(),'Device Promotions')]";
	public static final String DEVICE_PROMOTIONS_SECTION_PUBLISHED = "//*[@id='user-panel']/div/form/fieldset/div[contains(text(),'Device Promotions')]";
	public static final String DEVICE_PROMOTIONS_CONTAINER = "//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'foreach: promotions')]";
	public static final String DEVICE_PROMOTIONS_CONTAINER_PUBLISHED = "//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'foreach: promotions')]";
	
	public static final String devicePromtionsContainer(String publish_status){
//		if(publish_status.equals("Published")){
//			return "//*[@id='user-panel']/div/form/fieldset/div[27]";
//		}
		return "//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'foreach: promotions')]";
	}
	
	//Headphone Image Sizes
	public static final String HEADPHONE_IMAGE_SIZES ="//*[@id='user-panel']/div/form/fieldset/div[contains(text(),'Headphone Image Sizes')]";
	public static final String HEADPHONE_IMAGE_SIZES_PUBLISHED ="//*[@id='user-panel']/div/form/fieldset/div[contains(text(),'Headphone Image Sizes')]";
	public static final String HEADPHONE_IMAGE_SIZES_FIELD ="//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'foreach: deviceImageSize')]";
	public static final String HEADPHONE_IMAGE_SIZES_FIELD_PUBLISHED = "//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'foreach: deviceImageSize')]";
	public static final String LARGE_IMAGE = "//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'foreach: deviceImageSize')]/span[1]";
	//public static final String MEDIUM_IMAGE = "//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'foreach: deviceImageSize')]/span[2]";
	public static final String MEDIUM_IMAGE = "//*[@id='user-panel']/div/form/fieldset/div[27]/ul/li[2]/span";
	//public static final String MEDIUM_IMAGE_PUBLISHED = "//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'foreach: deviceImageSize')]/span[2]";
	public static final String MEDIUM_IMAGE_PUBLISHED = "//*[@id='user-panel']/div/form/fieldset/div[30]/ul/li[2]/span";
	public static final String SMALL_IMAGE = "//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'foreach: deviceImageSize')]/span[3]";
	//public static final String NO_IMAGES = "//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'foreach: deviceImageSize')]/span[1]";
	public static final String NO_IMAGES = ".//*[@id='user-panel']/div/form/fieldset/div[27]/ul/li";
	public static final String HEADPHONE_IMAGE = "//*[@id='user-panel']/div/form/fieldset/div[contains(@data-bind, 'foreach: deviceImageSize')]/span";
	
	//Headphone Brands
	public static final String HEADPHONE_BRAND ="//*[@id='user-panel']/div/form/fieldset/div[contains(text(),'Headphone Brands')]";
	public static final String HEADPHONE_BRAND_OPTION ="//*[@id='headphone-brands-placeholder']";
	public static final String HEADPHONE_BRAND_LABEL ="//*[@id='headphone-brands-placeholder']/ul/li/span";
	public static final String HEADPHONE_BRAND_LABEL_TEXT ="//*[@id='headphone-brands-placeholder']/ul/li/span[contains(text(),'Only the following brands')]";
	public static final String HEADPHONE_BRAND_PUBLISHED ="//*[@id='user-panel']/div/form/fieldset/div[contains(text(),'Headphone Brands')]";
	public static final String HEADPHONE_BRAND_OPTION_PUBLISHED ="//*[@id='headphone-brands-placeholder']";
	public static final String HEADPHONE_BRAND_LABEL_PUBLISHED ="//*[@id='headphone-brands-placeholder']/ul/li/span";
	
	// Action Module
	public static final String ACTIONS_MODULE = "//*[@id='create-new-company-action']";
	public static final String DELETE = "//*[@id='btnDeleteRecord']";
	public static final String SUSPEND = "//*[@id='btnSuspendRecord']";
	public static final String PUBLISH = "//*[@id='btnPublishRecord']";
	public static final String SUSPEND_CONFIRM_DIALOG = "html/body/div[6]/div[2]";
	
	// Publishing Process 
	public static final String UNSUBMITTED_STATE = "//*[@id='process-step-1']/div[2]/div[1]";
	public static final String PARTNER_ACTION = "//*[@id='accordion2']/div/div[1]/a";
	public static final String REQUEST_DTS_REVIEW = "//*[@id='requestAuditAction']";
	public static final String LICENSE_CANCEL_DTS_REVIEW = "//*[@id='requestAuditAction']";
	public static final String PENDING_DTS_REVIEW_STATE = "//*[@id='process-step-1']/div[2]/div[1]/span";
	public static final String APPROVE_DTS_REVIEW = "//*[@id='requestAuditAction']";
	public static final String PARTNER_ACTION_PENDING_DTS_REVIEW = "//*[@id='accordion2']/div/div[1]/a";
	public static final String DECLINED_DTS_REVIEW = "//*[@id='requestAuditAction2']";
	public static final String CANCEL_DTS_REVIEW = "//*[@id='partnerActionAudit']";
	public static final String APPROVED_STATE = "//*[@id='requestAuditAction']";
	public static final String DECLINED_STATE = "//*[@id='requestAuditAction2']";
	public static final String REQUEST_REVISED_AUDIT = "//*[@id='requestAuditAction']";
	public static final String REQUEST__AUDIT_STATE = "//*[@id='process-step-1']/div[2]/div[1]/span";
	public static final String CANCEL_REQUEST = "//*[@id='requestAuditAction']";
	public static final String PUBLISH_LICENSE = "//*[@id='btnPublishDevice']";
	public static final String PUBLISH_LICENSE_STATE = "//*[@id='publishing-title']/span";
	
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
	
	public static enum Product_Types {
		Select("- Select -"), // 0
		HPX_High("DTS:X Ultra"), 
		HPX_Medium("DTS:X Premium"),  // 2
		HPX_Low("DTS Audio Processing"); 
		
		private final String type;

		Product_Types (String type) {
			this.type = type;
		}

		public String getName() {
			return this.type;
		}
	};
	
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
//			data.add(PROMOTION_SLOT_PUBLISHED);
//			data.add(GLOBAL_PROMOTIONS_STATUS_PUBLISHED);
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
//			data.add(PROMOTION_SLOT);
//			data.add(GLOBAL_PROMOTIONS_STATUS);
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
	
	public static final ArrayList<String> getHighSections(){
		ArrayList<String> list = new ArrayList<String>();
		list.add(PRODUCT_INFORMATION);
		list.add(HEADPHONE_X);
		list.add(AUDIO_ROUTES);
		list.add(MULTI_CHANNEL);
		list.add(INBOX_HEADPHONES);
		list.add(FEATURED_ACCESSORY_PROMOTIONS);
		list.add(DEVICE_PROMOTIONS_SECTION);
		list.add(HEADPHONE_IMAGE_SIZES);
		list.add(HEADPHONE_BRAND);
		return list;
	}
	
	public static final ArrayList<String> getMediumSections(){
		ArrayList<String> list = new ArrayList<String>();
		list.add(PRODUCT_INFORMATION);
		list.add(HEADPHONE_X);
		list.add(AUDIO_ROUTES);
		list.add(INBOX_HEADPHONES);
		list.add(FEATURED_ACCESSORY_PROMOTIONS);
		list.add(DEVICE_PROMOTIONS_SECTION);
		list.add(HEADPHONE_IMAGE_SIZES);
		return list;
	}
	
	public static final ArrayList<String> getLowSections(){
		ArrayList<String> list = new ArrayList<String>();
		list.add(PRODUCT_INFORMATION);
		list.add(HEADPHONE_X);
		list.add(AUDIO_ROUTES);
		list.add(INBOX_HEADPHONES);
		list.add(HEADPHONE_IMAGE_SIZES);
		return list;
	}

}
