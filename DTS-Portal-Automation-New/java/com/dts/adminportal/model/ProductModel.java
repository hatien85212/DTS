package com.dts.adminportal.model;

public class ProductModel {
	
	public static final String ADD_PRODUCT = "//*[@id='create-new-accessory']";
	public static final String PRODUCT_TABLE = "//*[@id='BrandAccessoriesTable']";
	public static final String PRODUCT_TABLE_INFO = "//*[@id='BrandAccessoriesTable_info']";
	public static final String DUPLICATE_WARNING_MSG = "Language is duplicated";
	public static final String BRAND_FILTER = "//*[@id='brandFilterSelect']";
	public static final String PRODUCT_FILTER = "//*[@id='accessoryFilterSelect']";
	
	public enum ProductTblHeader {
		PICTURE("Picture"), MODEL("Model"), BRAND("Brand"), VARIANTS("Variants"), PUBLISHED("Published"), VERSION("Version"),
		TUNING_STATUS("Tunning Status"), HEADPHONE_X_RATING("Headphone X Rating"), MARKETING_STATUS("Marketing Status");
		private final String name;

		ProductTblHeader(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public static String[] getNames() {
			ProductTblHeader[] names = values();
			String[] result = new String[names.length];
			for (int i = 0; i < names.length; i++) {
				result[i] = names[i].getName();
			}
			return result;
		}
	}
	
	public enum ProductStatus {
		PUBLISH_ACTIVE("Active"),PUBLISH_PUBLISHED("Published"), PUBLISH_SUSPENDED("Suspended"), VERSION_DRAFT("Draft"), VERSION_PUBLISHED("Published"),
		TUNING_UNSUBMITTED("Unsubmitted"), TUNING_PENDING_APPROVAL("Pending Partner Approval"),  TUNING_PARTNER_UPLOADED("Partner Tuning Uploaded"),
		TUNING_DTS_REQUEST("DTS Request Pending"), TUNING_PENDING_REVIEW("Pending DTS Review"), TUNING_APPROVED("Approved"), TUNING_REVOKED("Revoked"),
		MARKETING_UNSUBMITTED("Unsubmitted"), MARKETING_APPROVED("Approved"), MARKETING_PARTNER_UPLOAD("Partner Uploaded"),
		MARKETING_DTS_REQUEST_REVIEW("DTS Marketing Request Review"), MARKETING_DTS_UPLOAD("DTS Uploaded"), MARKETING_REVOKED("Revoked"), MARKETING_(""),  
		HEADPHONE_UNDETERMINED("Undetermined"), HEADPHONE_A("A"), HEADPHONE_NO_CERT("No Certification"), HEADPHONE_A_PLUS("A+");  
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
	
}
