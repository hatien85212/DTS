package com.dts.adminportal.model;

import java.util.ArrayList;
import java.util.Hashtable;


public class AddBrand {
	public static final String NAME = "//*[@id='brandName']";
	public static final String ERROR_NAME = "//*[@id='brandNameSpanMsg']";
	public static final String BRAND_TAG_LINE = "//*[@id='brandTagLine']";
	public static final String CONSUMER_ALIAS = "//*[@id='consumerAlias']";
	public static final String WEBSITE = "//*[@id='webSite']";
	public static final String BRAND_OVERVIEW = "//*[@id='brandOverview']";
	public static final String COPYRIGHT_AND_TRADEMARK_NOTICE = "//*[@id='copyrightAndTrademarkNotice']";
	public static final String  ADD_IMAGE250 = "//*[@id='add-logo124Image-btn']/span";
	public static final String  IMAGE250_DISPLAY = "//div[@id='uploaded-logo124Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/th/a/img";
	public static final String  IMAGE250_NAME = "//div[@id='uploaded-logo124Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/span[1]";
	public static final String  DELELE_IMAGE250 = "//div[@id='uploaded-logo124Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/td[2]/img";
	public static final String  ADD_IMAGE500 = "//*[@id='add-logo256Image-btn']/span";
	public static final String  IMAGE500_DISPLAY = "//div[@id='uploaded-logo256Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/th/a/img";
	public static final String  IMAGE500_NAME = "//div[@id='uploaded-logo256Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/span[1]";
	public static final String  DELELE_IMAGE500 = "//div[@id='uploaded-logo256Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/td[2]/img";
	public static final String  ADD_IMAGE1000 = "//*[@id='add-logo512Image-btn']/span";
	public static final String  IMAGE1000_DISPLAY = "//div[@id='uploaded-logo512Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/th/a/img";
	public static final String  IMAGE1000_NAME = "//div[@id='uploaded-logo512Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/span[1]";
	public static final String  DELELE_IMAGE1000 = "//div[@id='uploaded-logo512Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/td[2]/img";
	public static final String  IMAGETABLE = "//*[@id='brandLogoTable']";
	public static final String LIGHTBOX_STYLE_IMAGE = "//img[@class = 'lb-image']";
	public static final String LIGHTBOX_CLOSE ="//*[@id='lightbox']/div[2]/div/div[2]/a";
	
	public static final ArrayList<String> getListAddBrand() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(NAME);
		arrayList.add(BRAND_TAG_LINE);
		arrayList.add(CONSUMER_ALIAS);
		arrayList.add(WEBSITE);
		arrayList.add(BRAND_OVERVIEW);
		arrayList.add(COPYRIGHT_AND_TRADEMARK_NOTICE);
		return arrayList;
	}
	public static final String SAVE = "//*[@id='create-company-brand']";
	public static final String CANCEL = "//*[@id='cancel-company-brand']";
	
	public static final Hashtable<String, String> getHash(){
		Hashtable<String, String> brand = new Hashtable<String, String>();
		brand.put("name", NAME);
		brand.put("tag", BRAND_TAG_LINE);
		brand.put("alias", CONSUMER_ALIAS);
		brand.put("website", WEBSITE);
		brand.put("overview", BRAND_OVERVIEW);
		brand.put("notice", COPYRIGHT_AND_TRADEMARK_NOTICE);
		brand.put("img1", ADD_IMAGE250);
		brand.put("img2", ADD_IMAGE500);
		brand.put("img3", ADD_IMAGE1000);
		brand.put("save", SAVE);
		brand.put("cancel", CANCEL);
		return brand;
	}
	
	public static final String BRAND_IMAGE_UPLOAD_MESSAGE_250 = "//div[@id='uploaded-logo124Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String BRAND_IMAGE_UPLOAD_MESSAGE_500 = "//div[@id='uploaded-logo256Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String BRAND_IMAGE_UPLOAD_MESSAGE_1000 = "//div[@id='uploaded-logo512Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String RETRY_UPLOAD_IMAGE_250 = "//div[@id='uploaded-logo124Image-material']//img[contains(@onclick, 'retryUploadImage')]";
	public static final String RETRY_UPLOAD_IMAGE_500 = "//div[@id='uploaded-logo256Image-material']//img[contains(@onclick, 'retryUploadImage')]";
	public static final String RETRY_UPLOAD_IMAGE_1000 = "//div[@id='uploaded-logo512Image-material']//img[contains(@onclick, 'retryUploadImage')]";
	
	public static enum BrandMessage {
		UPLOAD_SUCCESS("File upload successful"), INVALID_FILE("Invalid file type"), UPLOAD_CANCELED("File upload canceled"),
		UPLOAD_ERROR("File upload error"), FILE_250_PX("Automatically resize. Original 250x250 px"), 
		FILE_500_PX("Automatically resize. Original 500x500 px"), FILE_1000_PX("Automatically resize. Original 1000x1000 px"),
		UNIQUE_BRAND_NAME("Brand name must be unique in the system.");

		
		private final String msg;

		BrandMessage(String msg) {
			this.msg = msg;
		}

		public String getName() {
			return this.msg;
		}

		public static String[] getNames() {
			BrandMessage[] msgs = values();
			String[] result = new String[msgs.length];
			for (int i = 0; i < msgs.length; i++) {
				result[i] = msgs[i].getName();
			}
			return result;
		}
		
	}
	
	public static final String IMG_NAME[]={
		"Chrysanthemum.jpg",
		"Desert.jpg",
		"Hydrangeas.jpg"
	};
}
