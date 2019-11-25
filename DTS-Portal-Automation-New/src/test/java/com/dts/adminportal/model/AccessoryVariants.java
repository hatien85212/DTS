package com.dts.adminportal.model;

import java.util.ArrayList;

public class AccessoryVariants {
	public static final String ACCESSORY_VARIANT_LABEL = "//*[@id='catalog-std']/div[1]/span[contains(text(),'Accessory Variants')]";
	public static final String PARENT_ACCESSORY_LABLE = "//*[@id='catalog-std']/div[2]/span[contains(text(),'Parent Accessory')]";
	public static final String VARIANT_LABLE  = "//*[@id='title-variant']/span[contains(text(),'Variants')]";
	public static final String VARIANT_FILTER = "//*[@id='variantFilterSelect']";
	public static final String VARIANT_TABLE_INFO = "//*[@id='VariantTable_info']";
	public static final String VARIANT_TABLE_DATA = "//*[@id='VariantTable']/tbody";
	public static final String VARIANT_TABLE = "//*[@id='VariantTable']";
	
	public enum VariantTblHeader {
		PICTURE("Picture"), PRODUCT("Product"), PUBLISHED("Published"), VERSION("Version"),
		TUNING_STATUS("Tuning Status"), HEADPHONE_X_RATING("Headphone X Rating"), MARKETING_STATUS("Marketing Status");
		private final String name;

		VariantTblHeader(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public static String[] getNames() {
			VariantTblHeader[] names = values();
			String[] result = new String[names.length];
			for (int i = 0; i < names.length; i++) {
				result[i] = names[i].getName();
			}
			return result;
		}
	}
	
	public enum VariantStatus {
		ALL_ACTIVE("All Active"), RECENTLY_PUBLISHED("Recently Published"), RECENTLY_ADDED("Recently Added"), VERSION_DRAFT("Draft"), TUNING_REQUEST_PENDING("Tuning Request Pending"),
		TUNING_REQUEST_OVERDUE("Tuning Request - Overdue"), PARTNER_TUNING_REVIEW("Partner Tuning Review"), PARTNER_TUNING_REVIEW_OVERDUE("Partner Tuning Review - Overdue"), 
		DTS_TUNING_REVIEW("DTS Tuning Review"), DTS_TUNING_REVIEW_OVERDUE("DTS Tuning Review - Overdue"), TUNING_DECLINED("Tuning Declined"), TUNING_REVOKED("Tuning Revoked"),
		HEADPHONE_X_RATING_AA("Headphone:X Rating A+"), HEADPHONE_X_RATING_A("Headphone:X Rating A"), READY_FOR_MARKETING("Ready for Marketing"), DTS_MARKETING_REVIEW("DTS Marketing Review"),
		DTS_MARKETING_REVIEW_OVERDUE("DTS Marketing Review - Overdue"), MARKETING_DECLINED("Marketing Declined"), MARKETING_REVOKED("Marketing Revoked"), READY_TO_PUBLISHED("Ready to Published"),
		READY_TO_PUBLISHED_OVERDUE("Ready to Published - Overdue"), NEEDS_ATTENTION("Needs Attention"), SUSPENDED("Suspended");
		private final String name;

		VariantStatus(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public static String[] getNames() {
			VariantStatus[] names = values();
			String[] result = new String[names.length];
			for (int i = 0; i < names.length; i++) {
				result[i] = names[i].getName();
			}
			return result;
		}
	}
	
	public enum VariantStatusPartner {
		ALL_ACTIVE("All Active"), VERSION_DRAFT("Draft"), TUNING_REQUEST_PENDING("Tuning Request Pending"),
		PARTNER_TUNING_REVIEW("Partner Tuning Review"), DTS_TUNING_REVIEW("DTS Tuning Review"),
		READY_FOR_MARKETING("Ready for Marketing"), DTS_MARKETING_REVIEW("DTS Marketing Review"),
		READY_TO_PUBLISHED("Ready to Published"), PUBLISHED("Published"), NEEDS_ATTENTION("Needs Attention"), SUSPENDED("Suspended"), TUNING_REVOKED("Tuning Revoked"), MARKETING_REVOKED("Marketing Revoked");
		private final String name;

		VariantStatusPartner(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public static String[] getNames() {
			VariantStatusPartner[] names = values();
			String[] result = new String[names.length];
			for (int i = 0; i < names.length; i++) {
				result[i] = names[i].getName();
			}
			return result;
		}
	}
	
	public static final ArrayList<String> getListXpath(){
		ArrayList<String> list = new ArrayList<String>();
//		list.add(BRAND_LIST);
//		list.add(TYPE_LABEL);
//		list.add(TYPE_LIST);
//		list.add(TABLE);
//		list.add(FOOTER);
		return list;
	}
	
	public enum TuningStatus {
		APPROVED("Approved"), REVOKED("Revoked"), PARTNER_DECLINED("Partner Declined"), VERSION_DRAFT("Draft"), DTS_REQUEST_PENDING("DTS Request Pending"),
		PENDING_PARTNER_REVIEW("Pending Partner Review"), PENDING_DTS_REVIEW("Pending DTS Review");
		private final String name;

		TuningStatus(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public static String[] getNames() {
			TuningStatus[] names = values();
			String[] result = new String[names.length];
			for (int i = 0; i < names.length; i++) {
				result[i] = names[i].getName();
			}
			return result;
		}
	}
	
	public enum MarketingStatus {
		APPROVED("Approved"), REVOKED("Revoked"), DECLINED("Declined"), VERSION_DRAFT("Draft"), DTS_UPLOADED("DTS Uploaded"),
		UNSUBMITTED("Unsubmitted"), DTS_MARKETING_REQUEST_REVIEW("DTS Marketing Request Review");
		private final String name;

		MarketingStatus(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public static String[] getNames() {
			MarketingStatus[] names = values();
			String[] result = new String[names.length];
			for (int i = 0; i < names.length; i++) {
				result[i] = names[i].getName();
			}
			return result;
		}
	}

	public static final String No_Models_Match_These_Criteria = "No models match these criteria";
	public static final String PUBLISHED = "Published";
}
