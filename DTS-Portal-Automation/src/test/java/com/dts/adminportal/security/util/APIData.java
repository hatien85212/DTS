package com.dts.adminportal.security.util;

import java.util.Calendar;

public class APIData {
	
	// Audio routes url
	public static final String UPDATE_AUDIO_ROUTES_URL = "http://devportal.dts.com/saap/api/route";
	public static final String GET_AUDIO_ROUTES_LIST_URL = "http://devportal.dts.com/saap/api/route";
	public static final String AUDIO_ROUTE_DETAIL_URL = "http://devportal.dts.com/saap/api/route/154?revision=0";
	
	// Promotion url
	public static final String ADD_PROMOTION_URL = "http://devportal.dts.com/saap/api/promotion";
	public static final String EDIT_PROMOTION_URL = "http://devportal.dts.com/saap/api/promotion";
	
	public static String PUBLISH_PROMOTION_URL(String promotionID){
		return "http://devportal.dts.com/saap/api/publishPromo/" + promotionID;
	}
	
	public static String DELETE_PROMOTION_URL(String promotionID){
		return "http://devportal.dts.com/saap/api/delPromo/" + promotionID;
	}
	
	public static final String VIEW_LIST_PROMOTION_URL = "http://devportal.dts.com/saap/api/getAllPromotions?status=Active";
	
	// App & Device url
	public static final String ADD_DEVICE_URL = "http://devportal.dts.com/saap/api/devices";
	public static final String EDIT_DEVICE_URL = "http://devportal.dts.com/saap/api/devices";
	public static final String GET_LIST_DEVICE_URL = "http://devportal.dts.com/saap/api/devices";
	
	public static String GET_DEVICE_DETAIL_URL(String deviceID){
		return "http://devportal.dts.com/saap/api/devices/" + deviceID + "?revision=0";
	}
	public static String PUBLISH_DEVICE_URL(String deviceID){
		return "http://devportal.dts.com/saap/api/publishDevice?devId=" + deviceID;
	}
	
	public static String SUSPEND_DEVICE_URL(String deviceID){
		return "http://devportal.dts.com/saap/api/susDevice/" + deviceID + "?revision=0";
	}

	public static String RESTORE_DEVICE_URL(String deviceID){
		return "http://devportal.dts.com/saap/api/restoreDevice/" + deviceID + "?revision=0";
	}
	
	public static String DELETE_DEVICE_URL(String deviceID){
		return "http://devportal.dts.com/saap/api/delDevice/" + deviceID + "?revision=0";
	}
	
	// Company url
	public static final String ADD_COMPANY_URL = "http://devportal.dts.com/saap/api/partner";
	public static final String EDIT_COMPANY_URL = "http://devportal.dts.com/saap/api/partner";
	
	public static String CHANGE_PRIMARY_CONTACT_URL(String companyID, String userID){
		return "http://devportal.dts.com/saap/api/partners/changeMainContact?time=1436377936810&partnerid=" + companyID + "&userId=" + userID;
	}
	
	public static String SUSPEND_COMPANY_URL(String companyID){
		return "http://devportal.dts.com/saap/api/suspartner/" + companyID + "?time=1436387161026&id=" + companyID;
	}
	
	public static String RESTORE_COMPANY_URL(String companyID){
		return "http://devportal.dts.com/saap/api/respartner/" + companyID + "?time=1436389197051&id=" + companyID;
	}
	
	public static String DELETE_COMPANY_URL(String companyID){
		return "http://devportal.dts.com/saap/api/delpartner/" + companyID + "?time=1436389197051&id=" + companyID;
	}
	
	//User URL
	public static final String ADD_USER_URL = "http://devportal.dts.com/saap/api/account";
	public static final String GET_USER_LIST_URL = "http://devportal.dts.com/saap/api/users";
	public static final String GET_USER_DETAIL_URL = "http://devportal.dts.com/saap/api/users/34?time=1437724011930&userId=34";
	
	public static String RESET_USER_PASSWORD_URL(String userID){
		return "http://devportal.dts.com/saap/api/resetUserPassWord/" + userID;
	}
	
	public static String SUSPEND_USER_URL(String userID){
		return "http://devportal.dts.com/saap/api/suspendUser/" + userID;
	}
	
	public static String RESTORE_USER_URL(String userID){
		return "http://devportal.dts.com/saap/api/restoreUser/" + userID;
	}
	
	public static String DELETE_USER_URL(String userID){
		return "http://devportal.dts.com/saap/api/deleteUser/" + userID;
	}
	
	// Brand url
	public static final String ADD_BRAND_URL = "http://devportal.dts.com/saap/api/brands";
	public static final String EDIT_BRAND_URL = "http://devportal.dts.com/saap/api/brands";

	public static String DELETE_BRAND_URL(String brandID){
		return "http://devportal.dts.com/saap/api/brands/delete/" + brandID;
	}
	
	// Product url
	public static final String ADD_PRODUCT_URL = "http://devportal.dts.com/saap/api/assets";
	
	public static final String ADD_VARIANT_PRODUCT_URL = "http://devportal.dts.com/saap/api/addSKU";
	
	public static String REQUEST_TUNING_URL(String productID, String userId, String partnerId, boolean isProduct){
		String id = "&assetid=" + productID;
		if (!isProduct) {
			id = "&variantid=" + productID;
		}
		long time = Calendar.getInstance().getTime().getTime();
		return "http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus="
				+ "dts_request_pending&revision=0&additionalInfo=&userId=" + userId + "&partnerid=" + partnerId 
				+ id + "&time=" + time;
	}
	
	public static String APPROVE_TUNING_URL(String productID, boolean isProduct){
		String id = "&assetId=" + productID;
		if (!isProduct) {
			id = "&variantId=" + productID;
		}
		long time = Calendar.getInstance().getTime().getTime();
		return "http://devportal.dts.com/saap/api/updateTuningStatus?time=" + time + id + "&revision=0&tuning_status=Approved";
	}

	public static String DECLINE_TUNING_URL(String productID, boolean isProduct){
		String id = "&assetId=" + productID;
		if (!isProduct) {
			id = "&variantId=" + productID;
		}
		long time = Calendar.getInstance().getTime().getTime();
		return "http://devportal.dts.com/saap/api/updateTuningStatus?time=" + time + id + "&revision=0&tuning_status=partner_declined";
	}
	
	public static String REQUEST_REVISE_TUNING_URL(String productID, String userId, String partnerId, boolean isProduct){
		String id = "&assetid=" + productID;
		if (!isProduct) {
			id = "&variantid=" + productID;
		}
		long time = Calendar.getInstance().getTime().getTime();
		return "http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus="
				+ "dts_request_pending&revision=0&additionalInfo=&userId=" + userId + "&partnerid=" + partnerId 
				+ id + "&time=" + time;
	}

	public static String REVOKE_TUNING_URL(String productID, boolean isProduct){
		String id = "&assetId=" + productID;
		if (!isProduct) {
			id = "&variantId=" + productID;
		}
		long time = Calendar.getInstance().getTime().getTime();
		return "http://devportal.dts.com/saap/api/updateTuningStatus?time=" + time + id + "&revision=0&tuning_status=Revoked";
	}
	
	public static String APPROVE_MARKETING_URL(String productID, boolean isProduct){
		String id = "&assetId=" + productID;
		if (!isProduct) {
			id = "&variantId=" + productID;
		}
		long time = Calendar.getInstance().getTime().getTime();
		return "http://devportal.dts.com/saap/api/updateMarketingStatus?time=" + time + id + "&revision=0&marketing_status=Approved";
	}
	
	public static String DECLINE_MARKETING_URL(String productID, boolean isProduct){
		String id = "&assetId=" + productID;
		if (!isProduct) {
			id = "&variantId=" + productID;
		}
		long time = Calendar.getInstance().getTime().getTime();
		return "http://devportal.dts.com/saap/api/updateMarketingStatus?time=" + time + id + "&revision=0&marketing_status=Declined";
	}
	
	public static String REVOKE_MARKETING_URL(String productID, boolean isProduct){
		String id = "&assetId=" + productID;
		if (!isProduct) {
			id = "&variantId=" + productID;
		}
		long time = Calendar.getInstance().getTime().getTime();
		return "http://devportal.dts.com/saap/api/updateMarketingStatus?time=" + time + id + "&revision=0&marketing_status=Revoked";
	}
	
	public static String PUBLISH_PRODUCT_URL(String productID){
		long time = Calendar.getInstance().getTime().getTime();
		return "http://devportal.dts.com/saap/api/publishAsset?assetId=" + productID + "&assetRevision=0&time=" + time;
	}
	
	public static String PUBLISH_VARIANT_PRODUCT_URL(String variantID){
		long time = Calendar.getInstance().getTime().getTime();
		return "http://devportal.dts.com/saap/api/publishAssetVariant?variantId=" + variantID + "&assetRevision=0&time=" + time;
	}
	
	public static String DELETE_PRODUCT_URL(String productID){
		return "http://devportal.dts.com/saap/api/deleteModel/" + productID;
	}
	
	public static String DELETE_VARIANT_PRODUCT_URL(String productID){
		return "http://devportal.dts.com/saap/api/deleteVariantModel/" + productID;
	}
	
	public static String SUSPEND_PRODUCT_URL(String productID){
		long time = Calendar.getInstance().getTime().getTime();
		return "http://devportal.dts.com/saap/api/suspendModel?assetid=" + productID + "&assetRevision=0&time=" + time;
	}
	
	public static String SUSPEND_VARIANT_PRODUCT_URL(String productID){
		long time = Calendar.getInstance().getTime().getTime();
		return "http://devportal.dts.com/saap/api/publishAssetVariant?variantId=" + productID + "&assetRevision=0&time=" + time;
	}
	
	// Response message
	public static final String ERROR_MESSAGE = "You don't have permission.";
	public static final String ERROR_MESSAGE_CHANGE_USER_OWN_EMAIL = "{\"su\":false,\"dtsUser\":false}";
	
	// Request method
	public static final String GET_METHOD = "GET";
	public static final String POST_METHOD = "POST";
	public static final String PUT_METHOD = "PUT";
	public static final String DELETE_METHOD = "DELETE";

	
	// Json data file
	public static final String JSON_DATA_FILE = "JsonData.json";
	
	// Json data key
	public static final String AUDIO_ROUTE_DATA = "audio route data";
	public static final String PUBLISHED_PRODUCT_DATA = "published product data";
	public static final String GLOBAL_PROMOTION_DATA = "global promotion data";
	public static final String ADD_GLOBAL_PROMOTION_DATA = "add promotion";
	public static final String EDIT_GLOBAL_PROMOTION_DATA = "edit promotion";
	public static final String BRAND_DATA = "brand data";
	public static final String OTHER_BRAND_DATA = "other brand data";
	public static final String ADD_BRAND_DATA = "add brand data";
	public static final String EDIT_BRAND_DATA = "edit brand data";
	public static final String ADD_DEVICE_DATA = "add device";
	public static final String EDIT_DEVICE_DATA = "edit device";
	public static final String DEVICE_DATA = "device data";
	public static final String PUBLISHED_DEVICE_DATA = "published device data";
	public static final String SUSPENDED_DEVICE_DATA = "published device data";
	public static final String ADD_COMPANY_DATA = "add company data";
	public static final String EDIT_COMPANY_DATA = "edit company data";
	public static final String COMPANY_DATA = "company data";
	public static final String OTHER_COMPANY_DATA = "other company data";
	public static final String SUSPENDED_COMPANY_DATA = "suspended company data";
	public static final String PARTNER_USER_DATA = "partner user data";
	public static final String ADD_PARTNER_USER = "add partner user data";
	public static final String ADD_DTS_USER = "add DTS user data";
	public static final String EDIT_DTS_USER = "edit DTS user data";
	public static final String EDIT_PARTNER_USER = "edit partner user data";
	public static final String EDIT_DTS_USER_OWN = "edit DTS user own email";
	public static final String EDIT_PARTNER_USER_OWN = "edit partner user own email";
	public static final String ADD_PRODUCT_DRAFT = "add product draft";
	public static final String ADD_PRODUCT_PUBLISH = "add product publish";
	public static final String ADD_VARIANT_DRAFT = "add variant draft";
	public static final String ADD_VARIANT_PUBLISH = "add variant publish";
	public static final String LOG_IN = "login";

	
	
}
