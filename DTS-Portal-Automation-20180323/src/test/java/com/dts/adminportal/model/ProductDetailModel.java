package com.dts.adminportal.model;

public class ProductDetailModel {
	
	public static final String TITLE_NAME = "//*[@id='display-model']";
	public static final String EDIT_MODE = "//*[@id='edit-model']";
	public static final String UPDATE_PRODUCT_INFO = "//*[@id='edit-model']";
	public static final String VIEW_PUBLISHED_MODEL = "//*[@id='view-published-model']";
	public static final String VIEW_CURRENT_MODEL = "//*[@id='view-current-model']";
	public static final String PUBLICATION_STATUS = "//*[@id='publication-status']";
	public static final String DTS_TRACKING_ID = "//*[@id='dts-tracking-id']";
	public static final String SALESFORCE_ID = "//*[@id='salesforce-id']";
	public static final String COMPANY_NAME = "//*[@id='company-name']";
	public static final String BRAND_NAME = "//*[@id='brand-name']";
	public static final String PRODUCT_NAME = "//*[@id='model-name']/div/span[2]";
	public static final String DESCRIPTION = "//*[@id='description']";
	public static final String MODEL_NUMBER = "//*[@id='model-number']";
	public static final String EAN = "//*[@id='ean-number']";
	public static final String UPC = "//*[@id='upc-number']";
	public static final String BT_DEVICE_NAMES_VIEW = ".//*[@id='bt-device-name-placeholder']/div/div";
	public static final String RELEASE_DATE = "//*[@id='release-date']";
	public static final String TYPE = "//*[@id='type']";
	public static final String RENDER = "//*[@id='dts-tracking-id']";
	public static final String NOISE_CANCELING = "//*[@id='noise-canceling']";
	public static final String UPLOADED_TUNING = "//*[@id='uploaded-tunings']/a";
	public static final String TUNING_RATING = "//*[@id='headphone-tuning-rating']";
	public static final String HEADPHONEX_INSIDE = "//*[@id='headphone-inside']";
	public static final String IMAGE250 = "//*[@id='logo124']";
	public static final String IMAGE500 = "//*[@id='logo256']";
	public static final String IMAGE1000 = "//*[@id='logo512']";
	public static final String IMAGE_CATALOG = "//*[@id='primary-catalog-image']";	//should remove this - unused
	public static final String BRAND_LOGO_TABLE = "//*[@id='brandLogoTable']";
	public static final String MARKETING_MATERIALS = "//*[@id='marketing-materials']";
	public static final String USB_DEVICE_NAME  = "//*[@id='usb-device-name-placeholder']";
	
	public static final String TUNING_APPROVAL_PROCESS = "//*[@id='tuning-title']";
	public static final String MARKETING_APPROVAL_PROCESS = "//*[@id='marketing-title']";
	public static final String TUNING_STATUS = "//*[@id='tuning-title']/span";
	public static final String MARKETING_STATUS = "//*[@id='marketing-title']/span";
	public static final String PUBLISH_STATUS = "//*[@id='publishing-title']/span";
	
	public static final String STEREO_CAPABILITY = "//*[@id='stereo-capability']";
	public static final String CURRENTLY_SOLD_IN_STORE = "//*[@id='soldin-store']";
	
	
	
	public static final String DOWLOAD_FILE = "//*[@id='marketing-materials']/div/div";
	public static final String MARKETING_DOWNLOAD_LINK ="//*[@id='marketing-materials']/div/table/tbody/tr/td[2]/a";
	
	public static final String UPLOAD_TUNING = "//*[@id='uploadTuningAction']";
	public static final String LIGHTBOX_STYLE_IMAGE = "//img[@class = 'lb-image']";
	public static final String PUBLISHED_ACCESSORY_PROFILE = "//*[@id='publishedAccessoryProfile']";
	public static final String PUBLISH_PRODUCT = "//*[@id='publishModel']";
	public static final String TUNING_RATING_WARNING_PARTNER = "//*[@id='warningrating-msgPARTER']";
	public static final String TUNING_RATING_WARNING_DTS = "//*[@id='warningrating-msgDTS']";
	public static final String LOCK_ICON = "//i[@class='icon icon-lock']";
	public static final String CATALOG_IMAGE_THUMBNAIL_250 = "//span[@id='logo124ImageShow']//img[@class='uploadimagealign']";
	public static final String CATALOG_IMAGE_THUMBNAIL_500 = "//span[@id='logo256ImageShow']//img[@class='uploadimagealign']";
	public static final String CATALOG_IMAGE_THUMBNAIL_1000 = "//span[@id='logo512ImageShow']//img[@class='uploadimagealign']";
	public static final String LIGHTBOX_CLOSE = "//*[@id='lightbox']/div[2]/div/div[2]/a";
	public static final String INPUT_SPECIFICATION_PANEL = "//*[@id='input-specifications']";

//	public static final String allXpath[] = {
//		EDIT_MODE,
//		EDIT_MODE,
//		PUBLICATION_STATUS,
//		DTS_TRACKING_ID,
//		SALESFORCE_ID,
//		PRODUCT_NAME,
//		MODEL_NUMBER,
//		EAN,
//		UPC,
//		TYPE,
//		STEREO_CAPABILITY,
//		RELEASE_DATE,
//		DESCRIPTION,
//		CURRENTLY_SOLD_IN_STORE,
//		TUNING_RATING,
//		HEADPHONEX_INSIDE,
//		IMAGE_CATALOG,
//		MARKETING_MATERIALS 
//	};
	public static final String PUBLISHING_PROCESS = "//*[@id='create-accessory-action']";
	public static final String MODEL_ACTIONS = "//*[@class='show-accessory-resources']";
	public static final String MODEL_VARIANTS = "//*[@class='show-model-variant']";
	public static final String ADD_VARIANT = "//*[contains(@id,'addNewVariant')]";
	public static final String LIST_VARIANT = "//*[@id='model-variants']";
	public static final String VARIANT = "//*[@id='list-variants']/a/span";
	public static final String PUBLISHED_VARIANT_PROFILE = "//*[@id='publishedVariantProfile']";
	
//	public static final String actions[] = {
//		PUBLISHING_PROCESS,
//		MODEL_ACTIONS,
//		MODEL_VARIANTS
//	};
	
	public static final String MODEL_TUNING_ACTION_DISPLAY = ".//*[@id='model-tuning-action-display']/a/span";
	public static final String DECLINE_TUNING_REQUIRED_MESS = ".//*[@id='additionaInfo']";
	public static final String AGREE_SUBMISSION = ".//*[@id='agree']";
	
	//Tuning section
	public static final String DELETE = "//*[@id='deleteModel']";
	public static final String PROCESS_STEP_1 = "//*[@id='process-step-1']";
	public static final String PARTNER_ACTIONS_STEP_1 = "//*[@id='accordion2']/div/div[1]/a";
	public static final String APPROVE_TUNING = "//*[@id='requestTuningAction']"; 
	public static final String APPROVE_TUNING_SUBMIT = ".//*[@id='requestTuningSubmit']";
	public static final String APPROVE_TUNING_SUBMIT_THEN_BACK = ".//*[@id='model-tuning-action-display']/a/span";
	public static final String REQUEST_TUNING = "//*[@id='requestTuningAction']";
	public static final String REQUEST_TUNING_REVIEW = ".//*[@id='requestTuningAction'][@action='sendDTSRequestTuningReview']";
	public static final String REQUEST_REVISED_TUNING = ".//*[@id='requestTuningAction']";
	public static final String DECLINE_TUNING = "//*[@id='requestTuningAction2']";
	public static final String SEND_REMINDER = "//*[@id='send-remainder-to-partner']";
	public static final String EDIT_HEADPHONE_CERTIFICATION = "//*[@id='edit-x-rating-for-phone']";
	public static final String CANCEL_REQUEST_TUNING ="//*[@id='requestCancelAction']";
	public static final String CANCEL_REQUEST_TUNING_PARTNER = ".//*[@id='requestTuningAction']";
	public static final String REVOKE_TUNING = "//*[@id='requestRevokeAction']";
	public static final String DOWNLOAD_CERTIFICATE= "//*[@id='requestTuningAction'][@action='downloadCertificationBadges']";
	public static final String REQUEST_TUNING_SUBMIT = ".//*[@id='requestTuningSubmit']";
	public static final String REQUEST_TUNING_CANCEL =".//*[@id='requestCancelAction']";
	public static final String RATING_ERROR_MESS ="* Headphone:X Tuning Rating is required.";
	public static final String TUNING_RATING_A = "//*[@id='a']";
	 
	
	//Marketing section
	public static final String PARTNER_ACTIONS_MARKETING_STEP_1 = "//*[@id='accordion3']/div/div[1]/a";
	public static final String PROCESS_STEP_2 = "//*[@id='process-step-2']";
	public static final String PARTNER_ACTIONS_STEP_2 = "//*[@id='accordion3']/div/div[1]/a";
	public static final String MARKETING_ACTION = "//*[@id='marketingAction1']";
	public static final String MARKETING_CANCEL_REQUEST = "//*[@id='marketingAction1']";
	public static final String REQUEST_MARKETING_REVIEW = "//*[@id='marketingAction1'][@action='requestMarketingReview']";
	public static final String REVOKE_MARKETING_APPROVAL = "//*[@id='marketingAction1'][@action='revokeMarketingApproval']";
	public static final String APPROVE_MARKETING = "//*[@id='approve-marketing-material']";
	public static final String DECLINE_MARKETING = "//*[@id='decline-marketing-material']";
	public static final String REQUEST_MARKETING_SUBMIT = "//*[@id='requestMarketingSubmit']";
	
	//Publish section
	public static final String PUBLISH = "//*[@id='publishModel']";
	public static final String PUBLISH_DISABLE = "//*[@id='publishModel' and contains(@class,'disabled')]";
	public static final String UNSUSPEND = "//*[@id='publishModel']";
	public static final String SUSPEND = "//*[@id='suspendModel']";
	public static final String SUSPEND_VARIANT = ".//*[@id='suspendVariant']";
	public static final String DELETE_VARIANT = ".//*[@id='deleteVariant']";
	public static final String PROCESS_STEP_3 = "//*[@id='process-step-3']";
	public static final String ERROR_MESS_STEP_3 = "//*[@id='publish-actions']/font[1]";
	public static final String TUNING_TEMPLATE = "//*[@id='prePopulatedNewTuningProjectFile']";
	
	 public enum ProductStatus {
			UNSUBMITTED("UNSUBMITTED"), REMOVED("REMOVED"), DECLINED("DECLINED"), REVOKED("REVOKED"), CLOSED("CLOSED"),
			APPROVED("APPROVED"), TUNING_REQUEST_REVISED("Request Revised Tuning"), PENDING_PARTNER_APPROVAL("PENDING PARTNER APPROVAL"),
			PENDING_DTS_APPROVAL("PENDING DTS APPROVAL"), DTS_REQUEST_PENDING("DTS REQUEST PENDING"), REVOKED_BY_DTS("Revoked by DTS"), 
			PARTNER_DECLINED("Partner Declined");
			private final String name;

			ProductStatus(String name) {
				this.name = name;
			}

			public String getName() {
				return this.name;
			}

			public static String[] getNames() {
				ProductStatus[] names = values();
				String[] result = new String[names.length];
				for (int i = 0; i < names.length; i++) {
					result[i] = names[i].getName();
				}
				return result;
			}
		}
		
		public enum ProductActions {
			APPROVE_TUNING("Approve Tuning"), DECLINE_TUNING("Decline Tuning"), REQUEST_DTS_TUNING("Request DTS Tuning"), REQUEST_REVISED_TUNING("Request Revised Tuning"),
			REQUEST_MARKETING_REVIEW("Request Marketing Review"), REQUEST_TUNING_REVIEW("Request Tuning Review"),
			TUNING_CANCEL_REQUEST("Cancel Request"), MARKETING_CANCEL_REQUEST("Cancel Request"), EDIT_CERTIFICATION("Edit Headphone:X Certification"),
			DOWNLOAD_CERT_BADGES("Download Certification Badges");
			private final String name;

			ProductActions(String name) {
				this.name = name;
			}

			public String getName() {
				return this.name;
			}

			public static String[] getNames() {
				ProductActions[] names = values();
				String[] result = new String[names.length];
				for (int i = 0; i < names.length; i++) {
					result[i] = names[i].getName();
				}
				return result;
			}
		}
	 
	 
	//step 3: Publishing
	 public static final String PUBLISH_ERROR_MESS ="* Please upload the product catalog images to proceed with product publish.";
	 public static final String PUBLISHED_STRING = "PUBLISHED";
	 
	 public static final String EDIT_UPDATE_PRODUCT_INFO = "Update Product Info";
	 
	 public enum TuningTypes{
		 BlueToothOnly("BluetoothOnly"),LineOutOnly("LineoutOnly"),CombineTuning("ConbineTuning");
		 private final String name;

		 TuningTypes(String name) {
				this.name = name;
			}

			public String getName() {
				return this.name;
			}
	 }
	 
	 public static final String AEQKeys[] = { "bypass_hpeq","aeq_enable" };
	 public static final String BUTTON_WARNING = "button button-warning";
	
}
