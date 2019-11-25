package com.dts.adminportal.model;

import java.util.Hashtable;

public class PartnerVariantInfo extends VariantInfo {

	public static final Hashtable<String, String> getElementsInfo(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("published staus", PUBLISHED_STATUS);
		data.put("company", COMPANY_NAME);
		data.put("brand", BRAND_NAME);
		data.put("name", TITLE_NAME);
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
}
