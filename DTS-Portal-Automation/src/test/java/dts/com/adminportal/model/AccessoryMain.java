package dts.com.adminportal.model;

import java.util.ArrayList;

public class AccessoryMain {
	public static final String ADD_ACCESSORY = "//*[@id='create-new-accessory']";
	public static final String ACCESSORY_TABLE = "//*[@id='BrandAccessoriesTable']";
	public static final String ACCESSORY_TABLE_INFO = "//*[@id='BrandAccessoriesTable_info']";
	public static final String DUPLICATE_WARNING_MESSAGE = "Language is duplicated";
	public static final String BRAND_FILTER = "//*[@id='brandFilterSelect']";
	public static final String PRODUCT_FILTER = "//*[@id='accessoryFilterSelect']";
	
	
	public static final String VERSION = "Version";
	 public static final String MARKETING_STATUS = "Marketing Status";
	 public static final String HEADPHONE_X_RATING = "Headphone X Rating";
	 // Tuning Status
	 public static final String PENDING_PARTNER_APPROVAL = "Pending Partner Approval";
	 public static final String PENDING_DTS_APPROVAL = "Pending DTS Review";
	 public static final String APPROVED = "Approved";
	 public static final String REVOKED_BY_DTS = "Revoked by DTS";
	 public static final String PARTNER_DECLINED = "Partner Declined";
	 public static final String DTS_DECLINED = "DTS Declined";
	 public static final ArrayList<String> getListTuningDeclined() {
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add(PARTNER_DECLINED);
			arrayList.add(DTS_DECLINED);
			return arrayList;
	 }
	
}
