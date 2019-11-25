package com.dts.adminportal.model;

import java.util.Hashtable;


public class VariantInfo {
	public static final String PRODUCT_LINK = "//*[@id='display-model']";
	public static final String CREATE_NEW_VERSION = "//*[@id='create-new-variant-version']";
	public static final String VIEW_PUBLISHED_VERSION = "//*[@id='view-published-version']";
	public static final String EDIT_VARIANT = "//*[@id='edit-variant']";
	public static final String SALESFORCE_ID = "//*[@id='salesforce-id']";
	public static final String TRACKING_ID = "//*[@id='dts-id']";
	public static final String PUBLISHED_STATUS = "//*[@id='variant-publication-status']";
	public static final String COMPANY_NAME = "//*[@id='company-name']";
	public static final String BRAND_NAME = "//*[@id='brand-name']";
	public static final String TITLE_NAME = "//*[@id='display-variant-title']/span";
	public static final String DISPLAYNAME_DEFAULT = "//*[@id='display-variant-name']//span";
	public static final String EAN = "//*[@id='variant-ean-number']";
	public static final String UPC = "//*[@id='variant-upc-number']";
	public static final String NOISE_CANCELLING = "//*[@id='noise-cancel']";
	public static final String INPUT_SPECIFICATIONS = "//*[@id='input-specifications']";
	public static final String RELEASE_DATE = "//*[@id='variant-releaseDate']";
	public static final String DESCRIPTOR = "//*[@id='variant-descriptor']";
	public static final String PUBLISHING_PROCESS_CONTAINER = "//*[@id='create-accessory-action']";
	public static final String LIGHTBOX_STYLE_IMAGE = "//img[@class='lb-image']";
	
	// Tuning
	public static final String UPLOADED_TUNING = "//*[@id='variant-uploaded-tunings']";
	public static final String TUNING_RATING = "//*[@id='variant-headphone-cert-id']";
	public static final String TUNING_RATING_WARNING_DTS = "//*[@id='warningrating-msgDTS']";
	public static final String TUNING_RATING_WARNING_PARTNER = "//*[@id='warningrating-msgPARTNER']";

	// Image
	public static final String IMAGE_TABLE = "//*[@id='variant-product-uploaded-image']";
	
	// Marketing
	public static final String MARKETING_MATERIALS = "//*[@id='marketing-materials']";
	
	// Publishing process step 1
	public static final String PARTNER_ACTIONS_STEP_1 = "//*[@id='accordion2']//a";
	//public static final String TUNING_ACTION = "//*[@id='requestTuningAction']";
	public static final String REQUEST_TUNING_REVIEW = "//*[@id='requestTuningAction'][@action='sendDTSRequestTuningReview']";
	public static final String DOWNLOAD_CERTIFICATE= "//*[@id='requestTuningAction'][@action='downloadCertificationBadges']";
	public static final String REQUEST_DTS_TUNING = "//*[@id='requestTuningAction'][@action='requestDTSTuning']";
	public static final String APPROVE_TUNING = "//*[@id='requestTuningAction']";
	public static final String DECLINE_TUNING = "//*[@id='requestTuningAction2']";
	public static final String TUNING_STATUS = "//*[@id='tuning-title']/span";
	public static final String REVOKE_TUNING = "//*[@id='requestRevokeAction']";
	public static final String REQUEST_REVISED_TUNING = "//*[@id='requestTuningAction'][@action='partnerRequestRevisedTuning']";
	public static final String CANCEL_REQUEST_TUNING = "//*[@id='requestTuningAction'][@action='cancelDTSPendingReview']";
	public static final String SEND_REMINDER = "//*[@id='send-remainder-to-partner']";
	public static final String EDIT_HPX_CER = "//*[@id='edit-x-rating-for-phone']";
	
	// Publishing process step 2
	public static final String PARTNER_ACTIONS_STEP_2 = "//*[@id='accordion3']//a";
	public static final String REQUEST_MARKETING_REVIEW = "//*[@id='marketingAction1'][@action='requestMarketingReview']";
	public static final String APPROVE_MARKETING = "//*[@id='approve-marketing-material'][@action='dtsApprove']";
	public static final String MARKETING_STATUS = "//*[@id='marketing-title']/span";
	public static final String DECLINE_MARKETING = "//*[@id='decline-marketing-material'][@action='dtsDecline']";
	public static final String REVOKE_MARKETING = "//*[@id='marketingAction1'][@action='revokeMarketingApproval']";
	public static final String CANCEL_REQUEST_MARKETING = "//*[@id='marketingAction1'][@action='cancelDTSMarketingRequest']";

	// Publishing process step 3
	public static final String PUBLISH = "//*[@id='publishModel']";
	public static final String UNSUSPEND = "//*[@id='publishModel']";
	
	// Action container
	public static final String ACTION_CONTAINER = "//*[@id='add-accessory-resources']";
	public static final String PUBLISHED_VARIANT_PROFILE_LINK = "//*[@id='publishedVariantProfile']";
	public static final String DELETE_VARIANT = "//*[@id='deleteVariant']";
	public static final String SUSPEND = "//*[@id='suspendVariant']";
		
	public static final String SIZE_LIST[] = {
		"250X250",									
		"500X500", 
		"1000X1000",
	};
	
	public static Hashtable<String, String> getElementsInfo(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("published staus", PUBLISHED_STATUS);
		data.put("company", COMPANY_NAME);
		data.put("brand", BRAND_NAME);
		data.put("name", TITLE_NAME);
		data.put("ean", EAN);
		data.put("upc", UPC);
		data.put("noise cancelling", NOISE_CANCELLING);
		data.put("input specifications", INPUT_SPECIFICATIONS);
		data.put("release date", RELEASE_DATE);
		data.put("descriptor", DESCRIPTOR);
		data.put("uploaded tuning", UPLOADED_TUNING);
		data.put("tuning rating", TUNING_RATING);
		data.put("img table", IMAGE_TABLE);
		data.put("marketing materials", MARKETING_MATERIALS);
		data.put("publishing process container", PUBLISHING_PROCESS_CONTAINER);
		return data;

	}
	
	 public static final String VERSION = "Version";
	 public static final String MARKETING_STATE = "Marketing Status";
	 public static final String HEADPHONE_X_RATING = "Headphone X Rating";
	 // Tuning Status
	 public static final String UNSUBMITTED = "UNSUBMITTED";
	 public static final String DECLINED = "DECLINED";
	 public static final String REVOKED = "REVOKED";
	 public static final String CLOSED = "CLOSED";
	 public static final String PENDING_PARTNER_APPROVAL = "PENDING PARTNER APPROVAL";
	 public static final String PENDING_DTS_APPROVAL = "PENDING DTS APPROVAL";
	 public static final String APPROVED = "APPROVED";
	 public static final String REVOKED_BY_DTS = "Revoked by DTS";
	 public static final String PARTNER_DECLINED = "Partner Declined";
	 public static final String DTS_DECLINED = "DTS Declined";
	 public static final String USING_PARENT_TUNING = "USING PARENT'S MODEL";
	 public static final String DTS_REQUEST_PENDING_STRING = "DTS REQUEST PENDING";
	 
	 // Action string
	 public static final String REQUEST_DTS_TUNING_ACTION_STRING = "Request DTS Tuning";
	 public static final String REQUEST_MARKETING_REVIEW_ACTION_STRING = "Request Marketing Review";
	 public static final String REQUEST_TUNING_REVIEW_ACTION_STRING = "Request Tuning Review";
	 public static final String TUNING_CANCEL_REQUEST_ACTION_STRING = "Cancel Request";
	 public static final String MARKETING_CANCEL_REQUEST_ACTION_STRING = "Cancel Request";
	 public static final String EDIT_CERTIFICATION_ACTION_STRING = "Edit Headphone:X Certification";
	 public static final String DOWNLOAD_CERT_BADGES_ACTION_STRING = "Download Certification Badges";
	 public static final String REVOKE_TUNING_ACTION_STRING = "Revoke Tuning";
	 
	 //Primary catalog image
	 public static final String CATALOG_IMAGE_THUMBNAIL_250 ="//span[@id='logo124ImageShow']//img[@class='uploadimagealign']";
	 public static final String CATALOG_IMAGE_THUMBNAIL_500 ="//span[@id='logo256ImageShow']//img[@class='uploadimagealign']";
	 public static final String CATALOG_IMAGE_THUMBNAIL_1000 ="//span[@id='logo512ImageShow']//img[@class='uploadimagealign']";
	 public static final String LIGHTBOX_CLOSE ="//*[@id='lightbox']/div[2]/div/div[2]/a";
	 
	 
	//step 3: Publishing
	 public static final String ERROR_MESS_IMG ="* Please upload the product catalog images to proceed with product publish.";
	 public static final String PUBLISHED_STRING = "Published";
	
	
}
