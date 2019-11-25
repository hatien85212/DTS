package dts.com.adminportal.model;

import java.util.ArrayList;

public class DeviceInfo {
	
	// Product Information
	public static final String TITLE_COMPANY = "//*[@id='user-panel']/div/form/fieldset/h4/span[1]/a";
	public static final String TITLE_BRAND = "//*[@id='user-panel']/div/form/fieldset/h4/span[3]";
	public static final String TITLE_NAME = "//*[@id='user-panel']/div/form/fieldset/h4/span[4]";
	public static final String PRODUCT_INFORMATION = "//*[@id='user-panel']/div/form/fieldset/h5[1]";
	public static final String EDIT = "//*[@id='edit-product']";
	public static final String CREATE_NEW_VERSION = "//*[@id='create-new-version']";
	public static final String VIEW_PUBLISHED_VERSION = "//a[contains(text(),'View Published Version')]";
	public static final String PUBLICATION_STATUS = "//*[@id='device-status']";
	public static final String DTS_TRACKING_ID = "//span[@data-bind = 'text: dtsId']";
	public static final String SALESFORCE_ID = "//span[@data-bind = 'text: salesforceId']";
	public static final String TYPE = "//span[@data-bind = 'text: type']";
	public static final String COMPANY = "//span[@data-bind = 'text: companyName']";
	public static final String BRAND = "//span[@data-bind = 'text: brandName']";
	public static final String NAME = "//span[@data-bind = 'text: name']";
	public static final String OS = "//span[@data-bind = 'text: platform']";

	// Headphone: X Licensing Info
	public static final String HEADPHONE_X ="//*[@id='user-panel']/div/form/fieldset/h5[2]";
	public static final String LICENSE_CONTAINER = "//*[@class = 'license']";
	public static final String INIT_KEY_NAME = "//span[@data-bind = 'text: initKeyName']";
	public static final String COMPLETE_PROFILE_LINK = "//*[@class='complete-profile']";
	public static final String INIT_DATABASE_LINK = "//*[@class='init-database']";

	// Audio routes
	public static final String AUDIO_ROUTES = "//*[@id='user-panel']/div/form/fieldset/h5[3]";
	public static final String DEFAULT_AUDIO_ROUTE = "//*[@id='user-panel']/div/form/fieldset/div[2]";
	public static final String DEFAULT_AUDIO_ROUTE_FILE = "//*[@id='user-panel']/div/form/fieldset/div[2]/a";
	public static final String CUSTOM_TUNING_FILE = "//*[@id='user-panel']/div/form/fieldset/div[3]/a[1]";
	
	// Featured Accessory Promotions
	public static final String FEATURED_ACCESSORY_PROMOTIONS = "//*[@id='user-panel']/div/form/fieldset/h5[4]";
	public static final String PROMOTION_SLOT = "//span[@data-bind = 'text: numPromoSlots']";
	public static final String GLOBAL_PROMOTIONS_STATUS = "//*[@id='user-panel']/div/form/fieldset/div[5]/label/span";

	// Device promotions
	public static final String DEVICE_PROMOTIONS_SECTION = "//*[@id='user-panel']/div/form/fieldset/h5[5]";
	public static final String DEVICE_PROMOTIONS_CONTAINER = "//*[@id='user-panel']/div/form/fieldset/h5[5]";

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
	
	public static ArrayList<String> getElementInfo(){
		ArrayList<String> data = new ArrayList<String>();
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
