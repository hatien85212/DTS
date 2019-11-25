package dts.com.adminportal.model;

import java.util.ArrayList;
import java.util.Hashtable;

public class AccessoryInfo {
	
	public static final String VIEW_PUBLISHED_MODEL = "//*[@id='view-published-model']";
	public static final String EDIT_MODE  = "//*[@id='edit-model']";
	public static final String UPDATE_PRODUCT_INFO  = "//*[@id='edit-model']";
	public static final String VERSION_STATUS = "//*[@id='publication-status']";
	public static final String DTS_TRACKING_ID = "//*[@id='dts-tracking-id']";
	public static final String SALESFORCE_ID = "//*[@id='salesforce-id']";
	public static final String DISPLAY_NAME = "//*[@id='model-name']/div/div/div[2]/div/span";
	public static final String MODEL_NUMBER = "//*[@id='model-number']";
	public static final String EAN = "//*[@id='ean-number']";
	public static final String UPC = "//*[@id='upc-number']";
	public static final String TYPE = "//*[@id='type']";
	public static final String STEREO_CAPABILITY = "//*[@id='stereo-capability']";
	public static final String RELEASE_DATE = "//*[@id='release-date']";
	public static final String DESCRIPTION = "//*[@id='description']";
	public static final String CURRENTLY_SOLD_IN_STORE = "//*[@id='soldin-store']";
	public static final String TUNING_RATING = "//*[@id='headphone-tuning-rating']";
	public static final String HEADPHONEX_INSIDE = "//*[@id='headphone-inside']";
	public static final String IMAGE_CATALOG = "//*[@id='primary-catalog-image']";
	public static final String MARKETING_MATERIALS = "//*[@id='marketing-materials']";
	public static final String TUNING_APPROVAL_PROCESS = "//*[@id='tuning-title']";
	public static final String MARKETING_APPROVAL_PROCESS = "//*[@id='marketing-title']";
	public static final String  IMAGE124 = "//*[@id='logo124Image']";
	public static final String  IMAGE290 = "//*[@id='logo256Image']";
	public static final String  IMAGE664 = "//*[@id='logo512Image']";
	public static final String DOWLOAD_FILE="//*[@id='marketing-materials']/div/div";
	public static final String BRAND_NAME="//*[@id='brand-name']";
	public static final String TITLE_NAME="//*[@id='display-model']";
	public static final String TUNING_STATUS="//*[@id='tuning-title']/span";
	public static final String MARKETING_STATUS="//*[@id='marketing-title']/span";
	public static final String PUBLISH_STATUS="//*[@id='publishing-title']/span";
	public static final String IMAGE_TABLE ="//*[@id='brandLogoTable']";
	public static final String UPLOADED_TUNING = "//*[@id='uploaded-tunings']";
	public static final String COMPANY = "//*[@id='company-name']";
	public static final String NOISE_CANCELING = "//*[@id='noise-canceling']";
	public static final String UPLOAD_TUNING = "//*[@id='uploadTuningAction']";
	public static final String LIGHTBOX_STYLE_IMAGE = "//img[@class = 'lb-image']";
	public static final String PUBLISHED_ACCESSORY_PROFILE = "//*[@id='publishedAccessoryProfile']";
	public static final String PUBLISH_PRODUCT = "//*[@id='publishModel']";
	public static final String TUNING_RATING_WARNING_PARTNER = "//*[@id='warningrating-msgPARTER']";
	public static final String TUNING_RATING_WARNING_DTS = "//*[@id='warningrating-msgDTS']";
	public static final String LOCK_ICON = "//i[@class='icon icon-lock']";
	public static final String CATALOG_IMAGE_THUMBNAIL_250 ="//span[@id='logo124ImageShow']//img[@class='uploadimagealign']";
	public static final String CATALOG_IMAGE_THUMBNAIL_500 ="//span[@id='logo256ImageShow']//img[@class='uploadimagealign']";
	public static final String CATALOG_IMAGE_THUMBNAIL_1000 ="//span[@id='logo512ImageShow']//img[@class='uploadimagealign']";
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
		HEADPHONEX_INSIDE,
		IMAGE_CATALOG,
		MARKETING_MATERIALS 
	};
	public static final String PUBLISHING_PROCESS = "//*[@id='create-accessory-action']";
	public static final String MODEL_ACTIONS = "//*[@id='add-accessory-resources']";
	public static final String MODEL_VARIANTS = "//*[@id='model-variants']";
	public static final String ADD_VARIANT = "//*[contains(@id,'addNewVariant')]";
	public static final String actions[] = {
		PUBLISHING_PROCESS,
		MODEL_ACTIONS,
		MODEL_VARIANTS
	};
	
	//Tuning section
	public static final String DELETE = "//*[@id='deleteModel']";
	public static final String PROCESS_STEP_1 = "//*[@id='process-step-1']";
	public static final String PARTNER_ACTIONS_STEP_1 = "//*[@id='accordion2']/div/div[1]/a";
	public static final String APPROVE_TUNING = "//*[@id='requestTuningAction']";
	public static final String REQUEST_TUNING = "//*[@id='requestTuningAction']";
	public static final String REQUEST_TUNING_REVIEW = "//*[@id='requestTuningAction'][@action='sendDTSRequestTuningReview']";
	public static final String REQUEST_REVISED_TUNING = "//*[@action='partnerRequestRevisedTuning']";
	public static final String DECLINE_TUNING = "//*[@id='requestTuningAction2']";
	public static final String SEND_REMINDER = "//*[@id='send-remainder-to-partner']";
	public static final String EDIT_HEADPHONE_CERTIFICATION = "//*[@id='edit-x-rating-for-phone']";
	public static final String CANCEL_REQUEST_TUNING = "//*[@action='cancelRequestDTSTuning']";
	public static final String REVOKE_TUNING = "//*[@id='requestRevokeAction']";
	public static final String DOWNLOAD_CERTIFICATE= "//*[@id='requestTuningAction'][@action='downloadCertificationBadges']";
	
	//Marketing section
	public static final String PROCESS_STEP_2 = "//*[@id='process-step-2']";
	public static final String PARTNER_ACTIONS_STEP_2 = "//*[@id='accordion3']/div/div[1]/a";
	public static final String MARKETING_ACTION = "//*[@id='marketingAction1']";
	public static final String MARKETING_CANCEL_REQUEST = "//*[@id='marketingAction1']";
	public static final String REQUEST_MARKETING_REVIEW = "//*[@id='marketingAction1'][@action='requestMarketingReview']";
	public static final String REVOKE_MARKETING_APPROVAL = "//*[@id='marketingAction1'][@action='revokeMarketingApproval']";
	public static final String APPROVE_MARKETING = "//*[@id='approve-marketing-material']";
	public static final String DECLINE_MARKETING = "//*[@id='decline-marketing-material']";
	
	//Publish section
	public static final String PUBLISH = "//*[@id='publishModel']";
	public static final String UNSUSPEND = "//*[@id='publishModel']";
	public static final String SUSPEND = "//*[@id='suspendModel']";
	public static final String SUSPEND_VARIANT = ".//*[@id='suspendVariant']";
	public static final String DELETE_VARIANT = ".//*[@id='deleteVariant']";
	public static final String PROCESS_STEP_3 = "//*[@id='process-step-3']";
	public static final String ERROR_MESS_STEP_3 = "//*[@id='publish-actions']/font[1]";
	
	public static final Hashtable<String, String> getElementsInfo(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("version status", VERSION_STATUS);
		data.put("DTS tracking id", DTS_TRACKING_ID);
		data.put("company", COMPANY);
		data.put("brand name", BRAND_NAME);
		data.put("display name", DISPLAY_NAME);
		data.put("model number", MODEL_NUMBER);
		data.put("ean", EAN);
		data.put("upc", UPC);
		data.put("type", TYPE);
		data.put("noise canceling", NOISE_CANCELING);
		data.put("release date", RELEASE_DATE);
		data.put("description", DESCRIPTION);
		data.put("tuning upload", UPLOADED_TUNING);
		data.put("tuning rating", TUNING_RATING);
		data.put("inside", HEADPHONEX_INSIDE);
		data.put("image", IMAGE_CATALOG);
		data.put("marketing materials", MARKETING_MATERIALS );
		return data;
	}
	
	 public static final String VERSION = "Version";
	 public static final String MARKETING_STATE = "Marketing Status";
	 public static final String HEADPHONE_X_RATING = "Headphone X Rating";
	 // Tuning Status
	 public static final String UNSUBMITTED = "UNSUBMITTED";
	 public static final String REMOVED = "REMOVED";
	 public static final String TUNING_REQUEST_REVISED = "Request Revised Tuning";
	 public static final String DECLINED = "DECLINED";
	 public static final String REVOKED = "REVOKED";
	 public static final String CLOSED = "CLOSED";
	 public static final String PENDING_PARTNER_APPROVAL = "PENDING PARTNER APPROVAL";
	 public static final String PENDING_DTS_APPROVAL = "PENDING DTS APPROVAL";
	 public static final String APPROVED = "APPROVED";
	 public static final String DTS_REQUEST_PENDING = "DTS REQUEST PENDING";
	 public static final String REVOKED_BY_DTS = "Revoked by DTS";
	 public static final String PARTNER_DECLINED = "Partner Declined";
	 public static final String DTS_DECLINED = "DTS Declined";
	 // Action string
	 public static final String APPROVE_TUNING_ACTION_STRING = "Approve Tuning";
	 public static final String DECLINE_TUNING_ACTION_STRING = "Decline Tuning";
	 public static final String REQUEST_DTS_TUNING_ACTION_STRING = "Request DTS Tuning";
	 public static final String REQUEST_MARKETING_REVIEW_ACTION_STRING = "Request Marketing Review";
	 public static final String REQUEST_TUNING_REVIEW_ACTION_STRING = "Request Tuning Review";
	 public static final String TUNING_CANCEL_REQUEST_ACTION_STRING = "Cancel Request";
	 public static final String MARKETING_CANCEL_REQUEST_ACTION_STRING = "Cancel Request";
	 public static final String EDIT_CERTIFICATION_ACTION_STRING = "Edit Headphone:X Certification";
	 public static final String DOWNLOAD_CERT_BADGES_ACTION_STRING = "Download Certification Badges";
	 
	 
	//step 3: Publishing
	 public static final String ERROR_MESS_IMG ="* Please upload the product catalog images to proceed with product publish.";
	 
	
	 public static final ArrayList<String> getListTuningDeclined() {
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add(PARTNER_DECLINED);
			arrayList.add(DTS_DECLINED);
			return arrayList;
	 }
	 
		public static final String IMG_NAME[]={
			"250x250.jpg",
			"500x500.jpg",
			"1000x1000.jpg",
			"250x250.png",
			"500x500.png",
			"1000x1000.png"
		};
	
	
	
}
