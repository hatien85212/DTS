package dts.com.adminportal.model;

import java.util.Hashtable;

public class PartnerAccessoryInfo {
	
	public static final String VIEW_PUBLISHED_MODEL = "//*[@id='view-published-model']";
	public static final String EDIT_MODE  = "//*[@id='edit-model']";
	public static final String VERSION_STATUS = "//*[@id='publication-status']";
	public static final String DTS_TRACKING_ID = "//*[@id='dts-tracking-id']";
	public static final String SALESFORCE_ID = "//*[@id='salesforce-id']";
	public static final String DISPLAY_NAME = "//*[@id='model-name']";
	public static final String MODEL_NUMBER = "//*[@id='model-number']";
	public static final String EAN = "//*[@id='ean-number']";
	public static final String UPC = "//*[@id='upc-number']";
	public static final String TYPE = "//*[@id='type']";
	public static final String STEREO_CAPABILITY = "//*[@id='stereo-capability']";
	public static final String RELEASE_DATE = "//*[@id='release-date']";
	public static final String DESCRIPTION = "//*[@id='description']";
	public static final String CURRENTLY_SOLD_IN_STORE = "//*[@id='soldin-store']";
	public static final String UPLOADED_TUNING = "//*[@id='uploaded-tunings']";
	public static final String TUNING_RATING = "//*[@id='headphone-tuning-rating']";
	public static final String INSIDE = "//*[@id='headphone-inside']";
	public static final String IMAGE_CATALOG = "//*[@id='primary-catalog-image']";
	public static final String MARKETING_MATERIALS = "//*[@id='marketing-materials']";
	public static final String TUNING_APPROVAL_PROCESS = "//*[@id='tuning-title']";
	public static final String TUNING_ACTION = "//*[@id='requestTuningAction']";
	public static final String TUNING_STATUS = "//*[@id='tuning-title']/span";
	public static final String MARKETING_APPROVAL_PROCESS = "//*[@id='marketing-title']";
	public static final String MARKETING_ACTION1 = "//*[@id='marketingAction1']";
	public static final String MARKETING_STATUS = "//*[@id='marketing-title']/span";
	public static final String  IMAGE124 = "//*[@id='logo124Image']";
	public static final String  IMAGE290 = "//*[@id='logo256Image']";
	public static final String  IMAGE664 = "//*[@id='logo512Image']";	
	public static final String BRAND_NAME="//*[@id='brand-name']";
	public static final String IMAGE_TABLE ="//*[@id='brandLogoTable']";
	public static final String LIGHTBOX_STYPE_IMAGE = "//img[@class = 'lb-image']";
	public static final String ACCESSORY_NAME="//*[@id='display-model']";
	public static final String UPDATE_PRODUCT_INFO="//*[@id='edit-model']";
	public static final String LIGHTBOX_CLOSE ="//*[@id='lightbox']/div[2]/div/div[2]/a";
	
	
	public static final String allXpath[] = {
		EDIT_MODE,
		EDIT_MODE,
		VERSION_STATUS,
		DTS_TRACKING_ID,
		SALESFORCE_ID,
		DISPLAY_NAME,
		MODEL_NUMBER,
		EAN,
		UPC,
		TYPE,
		STEREO_CAPABILITY,
		RELEASE_DATE,
		DESCRIPTION,
		CURRENTLY_SOLD_IN_STORE,
		TUNING_RATING,
		INSIDE,
		IMAGE_CATALOG,
		MARKETING_MATERIALS 
	};
	public static final Hashtable<String, String> getElementsInfo(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("version status", VERSION_STATUS);
		data.put("DTS tracking id", DTS_TRACKING_ID);
		data.put("brand name", BRAND_NAME);
		data.put("display name", DISPLAY_NAME);
		data.put("model number", MODEL_NUMBER);
		data.put("ean", EAN);
		data.put("upc", UPC);
		data.put("type", TYPE);
		data.put("release date", RELEASE_DATE);
		data.put("description", DESCRIPTION);
		data.put("tuning upload", UPLOADED_TUNING);
		data.put("tuning rating", TUNING_RATING);
		data.put("inside", INSIDE);
		data.put("image", IMAGE_CATALOG);
		data.put("marketing materials", MARKETING_MATERIALS );
		return data;
		
	}
	public static final String PUBLISHING_PROCESS = "//*[@id='create-accessory-action']";
	public static final String MODEL_ACTIONS = "//*[@id='add-accessory-resources']";
	public static final String MODEL_VARIANTS = "//*[@id='model-variants']";
	public static final String ADD_VARIANT = "//*[@id='addNewVariant']";
	public static final String actions[] = {
		PUBLISHING_PROCESS,
		MODEL_ACTIONS,
		MODEL_VARIANTS
	};
	public static final String DELETE = "//*[@id='deleteModel']";
	public static final String PROCESS_STEP_1 = "//*[@id='process-step-1']";
	public static final String PARTNER_ACTIONS_STEP_1 = "//*[@id='accordion2']/div/div[1]/a";
	public static final String APPROVE_TUNING = "//*[@id='requestTuningAction']";
	public static final String REQUEST_TUNING = "//*[@id='requestTuningAction']";
	public static final String DECLINE_TUNING = "//*[@id='requestTuningAction2']";
	public static final String SEND_REMINDER = "//*[@id='send-remainder-to-partner']";
	public static final String EDIT_HEADPHONE_CERTIFICATION = "//*[@id='edit-x-rating-for-phone']";
	public static final String CANCEL_REQUEST_TUNING = "//*[@action='cancelRequestDTSTuning' or @action='cancelDTSPendingReview']";
	public static final String REVOKE_TUNING = "//*[@id='requestRevokeAction']";
	
	public static final String PROCESS_STEP_2 = "//*[@id='process-step-2']";
	public static final String PARTNER_ACTIONS_STEP_2 = "//*[@id='accordion3']/div/div[1]/a";
	public static final String REQUEST_MARKETING_REVIEW = "//*[@id='marketingAction1']";
	public static final String APPROVE_MARKETING = "//*[@id='approve-marketing-material']";
	public static final String DECLINE_MARKETING = "//*[@id='decline-marketing-material']";
	public static final String PUBLISH = "//*[@id='publishModel']";
	public static final String SUSPEND = "//*[@id='suspendModel']";
	
	public static final String PROCESS_STEP_3 = "//*[@id='process-step-3']";
}
