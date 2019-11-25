package com.dts.adminportal.util;

import java.util.Hashtable;
import com.dts.adminportal.util.CreatePage;

import org.apache.commons.lang3.RandomStringUtils;

import dts.com.adminportal.model.AddCompany;
import dts.com.adminportal.model.Constant;

public class TestData {
	
	/**
	 * sample product data for testing
	 * @param companyName String name of company
	 * @param brandName String name of brand
	 * @param tuning boolean Add tuning data when true
	 * @param marketing boolean add marketing data when true
	 * @param publish boolean add image data when true
	 * @return 
	 */
	public static Hashtable<String,String> productData(String companyName, String brandName, boolean tuning, boolean marketing, boolean publish){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("company", companyName);
		data.put("brand", brandName);
		data.put("name", "Test" + RandomStringUtils.randomNumeric(7));
		data.put("number", RandomStringUtils.randomNumeric(10));
		data.put("ean", RandomStringUtils.randomNumeric(13));
		data.put("upc", RandomStringUtils.randomNumeric(12));
		data.put("type", "Ear-buds");
		data.put("wired", "1 (Single Channel)");
		data.put("description", RandomStringUtils.randomAlphanumeric(20));
		if (tuning) {
			data.put("add tunning", Constant.tuning_hpxtt);
			data.put("tuning rating", "A");
		}
		if (marketing) {
			data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
		}
		if(publish) {
			data.put("img1", Constant.IMG_NAME[0]);
			data.put("img2", Constant.IMG_NAME[1]);
			data.put("img3", Constant.IMG_NAME[2]);
		}
		data.put("save", "yes");
		return data;
	}
	
//	public static Hashtable<String,String> productDataPublish(String companyName, String brandName){
//		Hashtable<String,String> data = productDataMarketing(companyName, brandName);
//		data.put("img1", Constant.IMG_NAME[0]);
//		data.put("img2", Constant.IMG_NAME[1]);
//		data.put("img3", Constant.IMG_NAME[2]);
//		data.put("save", "yes");
//		return data;
//	}
//	
//	public static Hashtable<String,String> productDataMarketing(String companyName, String brandName){
//		Hashtable<String,String> data = productDataTuning(companyName, brandName);
//		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
//		return data;
//	}
//	
//	public static Hashtable<String,String> productDataTuning(String companyName, String brandName){
//		Hashtable<String,String> data = productDataDraft(companyName, brandName);
//		data.put("add tunning", Constant.tuning_hpxtt);
//		data.put("tuning rating", "A");
//		return data;
//	}
//	
//	public static Hashtable<String,String> productDataDraft(String companyName, String brandName){
//		Hashtable<String,String> data = new Hashtable<String,String>();
//		data.put("id", RandomStringUtils.randomNumeric(10));
//		data.put("company", companyName);
//		data.put("brand", brandName);
//		data.put("name", "Test" + RandomStringUtils.randomNumeric(7));
//		data.put("number", RandomStringUtils.randomNumeric(10));
//		data.put("ean", RandomStringUtils.randomNumeric(13));
//		data.put("upc", RandomStringUtils.randomNumeric(12));
//		data.put("type", "Ear-buds");
//		data.put("wired", "1 (Single Channel)");
//		data.put("description", RandomStringUtils.randomAlphanumeric(20));
//		data.put("save", "yes");
//		return data;
//	}
	
	/**
	 * sample variant data for testing
	 * @param tuning boolean Add tuning data when true
	 * @param marketing boolean add marketing data when true
	 * @param publish boolean add image data when true
	 * @return 
	 */
	public static Hashtable<String,String> variantData(boolean tuning, boolean marketing, boolean publish){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("name", "Test" + RandomStringUtils.randomNumeric(7));
		data.put("ean", RandomStringUtils.randomNumeric(13));
		data.put("upc", RandomStringUtils.randomNumeric(12));
		data.put("descriptor", RandomStringUtils.randomAlphanumeric(20));
		if (tuning) {
			data.put("add tunning", Constant.tuning_hpxtt);
			data.put("tuning rating", "A");
		}
		if (marketing) {
			data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
		}
		if(publish) {
			data.put("img1", Constant.IMG_NAME[0]);
			data.put("img2", Constant.IMG_NAME[1]);
			data.put("img3", Constant.IMG_NAME[2]);
		}
		data.put("save", "yes");
		return data;
	}
	
	public static Hashtable<String,String> accessoryPublish(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("company", CreatePage.partner_company_name);
		data.put("brand", CreatePage.partner_brand_name_1);
		data.put("name", "Test" + RandomStringUtils.randomNumeric(7));
		data.put("number", RandomStringUtils.randomNumeric(10));
		data.put("ean", RandomStringUtils.randomNumeric(13));
		data.put("upc", RandomStringUtils.randomNumeric(12));
		data.put("type", "Ear-buds");
		data.put("wired", "1 (Single Channel)");
		data.put("description", RandomStringUtils.randomAlphanumeric(20));
		data.put("add tunning", Constant.tuning_hpxtt);
		data.put("tuning rating", "A");
		data.put("img1", Constant.IMG_NAME[0]);
		data.put("img2", Constant.IMG_NAME[1]);
		data.put("img3", Constant.IMG_NAME[2]);
		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
		data.put("save", "yes");
		return data;
	}
	
	public static Hashtable<String,String> accessoryDraft(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("company", CreatePage.partner_company_name);
		data.put("brand", CreatePage.partner_brand_name_1);
		data.put("name", "Test" + RandomStringUtils.randomNumeric(7));
		data.put("number", RandomStringUtils.randomNumeric(10));
		data.put("ean", RandomStringUtils.randomNumeric(13));
		data.put("upc", RandomStringUtils.randomNumeric(12));
		data.put("type", "Ear-buds");
		data.put("wired", "1 (Single Channel)");
		data.put("description", RandomStringUtils.randomAlphanumeric(20));
		data.put("save", "yes");
		return data;
	}
	
	public static Hashtable<String,String> accessoryTuning(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("company", CreatePage.partner_company_name);
		data.put("brand", CreatePage.partner_brand_name_1);
		data.put("name", "Test" + RandomStringUtils.randomNumeric(7));
		data.put("number", RandomStringUtils.randomNumeric(10));
		data.put("ean", RandomStringUtils.randomNumeric(13));
		data.put("upc", RandomStringUtils.randomNumeric(12));
		data.put("type", "Ear-buds");
		data.put("wired", "1 (Single Channel)");
		data.put("description", RandomStringUtils.randomAlphanumeric(20));
		data.put("add tunning", Constant.tuning_hpxtt);
		data.put("tuning rating", "A");
		data.put("save", "yes");
		return data;
	}
	
	public static Hashtable<String,String> accessoryImage(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("company", CreatePage.partner_company_name);
		data.put("brand", CreatePage.partner_brand_name_1);
		data.put("name", "Test" + RandomStringUtils.randomNumeric(7));
		data.put("number", RandomStringUtils.randomNumeric(10));
		data.put("ean", RandomStringUtils.randomNumeric(13));
		data.put("upc", RandomStringUtils.randomNumeric(12));
		data.put("type", "Ear-buds");
		data.put("wired", "1 (Single Channel)");
		data.put("description", RandomStringUtils.randomAlphanumeric(20));
		data.put("img1", Constant.IMG_NAME[0]);
		data.put("img2", Constant.IMG_NAME[1]);
		data.put("img3", Constant.IMG_NAME[2]);
		data.put("save", "yes");
		return data;
	}
	
	public static Hashtable<String,String> accessoryMarketing(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("company", CreatePage.partner_company_name);
		data.put("brand", CreatePage.partner_brand_name_1);
		data.put("name", "Test" + RandomStringUtils.randomNumeric(7));
		data.put("number", RandomStringUtils.randomNumeric(10));
		data.put("ean", RandomStringUtils.randomNumeric(13));
		data.put("upc", RandomStringUtils.randomNumeric(12));
		data.put("type", "Ear-buds");
		data.put("wired", "1 (Single Channel)");
		data.put("description", RandomStringUtils.randomAlphanumeric(20));
		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
		data.put("save", "yes");
		return data;
	}
	
	public static Hashtable<String,String> variantPublish(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("name", "Test" + RandomStringUtils.randomNumeric(7));
		data.put("ean", RandomStringUtils.randomNumeric(13));
		data.put("upc", RandomStringUtils.randomNumeric(12));
		data.put("descriptor", RandomStringUtils.randomAlphanumeric(20));
		data.put("add tunning", Constant.tuning_hpxtt);
		data.put("tuning rating", "A");
		data.put("img1", Constant.IMG_NAME[0]);
		data.put("img2", Constant.IMG_NAME[1]);
		data.put("img3", Constant.IMG_NAME[2]);
		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
		data.put("save", "yes");
		return data;
	}
	
	public static Hashtable<String,String> variantPublishUseParent(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("name", "Test" + RandomStringUtils.randomNumeric(7));
		data.put("ean", RandomStringUtils.randomNumeric(13));
		data.put("upc", RandomStringUtils.randomNumeric(12));
		data.put("descriptor", RandomStringUtils.randomAlphanumeric(20));
		data.put("add tunning", "use parent");
		data.put("img1", "use parent");
		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
		data.put("save", "yes");
		return data;
	}
	
	public static Hashtable<String,String> variantDraft(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("name", "Test" + RandomStringUtils.randomNumeric(7));
		data.put("ean", RandomStringUtils.randomNumeric(13));
		data.put("upc", RandomStringUtils.randomNumeric(12));
		data.put("descriptor", RandomStringUtils.randomAlphanumeric(20));
		data.put("save", "yes");
		return data;
	}
	
	public static Hashtable<String,String> variantTuning(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("name", "Test" + RandomStringUtils.randomNumeric(7));
		data.put("ean", RandomStringUtils.randomNumeric(13));
		data.put("upc", RandomStringUtils.randomNumeric(12));
		data.put("descriptor", RandomStringUtils.randomAlphanumeric(20));
		data.put("add tunning", Constant.tuning_hpxtt);
		data.put("tuning rating", "A");
		data.put("save", "yes");
		return data;
	}
	public static Hashtable<String,String> variantTuningParent(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("name", "Test" + RandomStringUtils.randomNumeric(7));
		data.put("ean", RandomStringUtils.randomNumeric(13));
		data.put("upc", RandomStringUtils.randomNumeric(12));
		data.put("descriptor", RandomStringUtils.randomAlphanumeric(20));
		data.put("add tunning", "use parent");
		data.put("tuning rating", "A");
		data.put("save", "yes");
		return data;
	}
	
	public static Hashtable<String,String> variantImage(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("name", "Test" + RandomStringUtils.randomNumeric(7));
		data.put("ean", RandomStringUtils.randomNumeric(13));
		data.put("upc", RandomStringUtils.randomNumeric(12));
		data.put("descriptor", RandomStringUtils.randomAlphanumeric(20));
		data.put("img1", Constant.IMG_NAME[0]);
		data.put("img2", Constant.IMG_NAME[1]);
		data.put("img3", Constant.IMG_NAME[2]);
		data.put("save", "yes");
		return data;
	}
	
	public static Hashtable<String,String> variantImageParent(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("name", "Test" + RandomStringUtils.randomNumeric(7));
		data.put("ean", RandomStringUtils.randomNumeric(13));
		data.put("upc", RandomStringUtils.randomNumeric(12));
		data.put("descriptor", RandomStringUtils.randomAlphanumeric(20));
		data.put("img1", "use parent");
		data.put("save", "yes");
		return data;
	}
	
	public static Hashtable<String,String> variantMarketing(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("name", "Test" + RandomStringUtils.randomNumeric(7));
		data.put("ean", RandomStringUtils.randomNumeric(13));
		data.put("upc", RandomStringUtils.randomNumeric(12));
		data.put("descriptor", RandomStringUtils.randomAlphanumeric(20));
		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
		data.put("save", "yes");
		return data;
	}
	
	public static Hashtable<String,String> brandFull(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("name", "Test" + RandomStringUtils.randomNumeric(6));
		data.put("tag", RandomStringUtils.randomNumeric(20));
		data.put("alias", RandomStringUtils.randomAlphabetic(20));
		data.put("website", RandomStringUtils.randomAlphanumeric(20));
		data.put("overview", RandomStringUtils.randomAlphabetic(20));
		data.put("notice", RandomStringUtils.randomAlphabetic(20));
		data.put("img1", Constant.IMG_NAME[0]);
		data.put("img2", Constant.IMG_NAME[1]);
		data.put("img3", Constant.IMG_NAME[2]);
		data.put("save", "yes");
		return data;
	}
	
	public static Hashtable<String,String> brandDraft(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("name", "Test" + RandomStringUtils.randomNumeric(6));
		data.put("tag", RandomStringUtils.randomNumeric(20));
		data.put("alias", RandomStringUtils.randomAlphabetic(20));
		data.put("website", RandomStringUtils.randomAlphanumeric(20));
		data.put("overview", RandomStringUtils.randomAlphabetic(20));
		data.put("notice", RandomStringUtils.randomAlphabetic(20));
		data.put("save", "yes");
		return data;
	}
	
	public static Hashtable<String,String> validCompany(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		String companyName = "Test" + RandomStringUtils.randomNumeric(5);
		String date = DateUtil.getADateGreaterThanToday("MMMM d, yyyy", 3);
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("type", "Partner");
		data.put("date", date);
		data.put("name", companyName);
		data.put("address", RandomStringUtils.randomAlphanumeric(20));
		data.put("city", RandomStringUtils.randomAlphabetic(10));
		data.put("state", RandomStringUtils.randomAlphabetic(10));
		data.put("country", "Vietnam");
		data.put("partnerships", AddCompany.PARTNERSHIP_LIST[0]);
		return data;
	}
	
	public static Hashtable<String,String> partnerUser(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("firstName", "first"+ RandomStringUtils.randomAlphanumeric(10));
		data.put("lastName", "last"+ RandomStringUtils.randomAlphanumeric(10));
		data.put("title", RandomStringUtils.randomAlphanumeric(20));
		data.put("company", "TestCorp-Auto");
		data.put("email", RandomStringUtils.randomAlphanumeric(20) + "@mailinator.com");
		data.put("code", RandomStringUtils.randomNumeric(3));
		data.put("phone", RandomStringUtils.randomNumeric(10));
		data.put("brand", "All brands");
		data.put("save", "yes");
		return data;
	}
	
	public static Hashtable<String,String> dtsUser(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("firstName", "first"+ RandomStringUtils.randomAlphanumeric(10));
		data.put("lastName", "last"+ RandomStringUtils.randomAlphanumeric(10));
		data.put("title", RandomStringUtils.randomAlphanumeric(20));
		data.put("company", "DTS Inc.");
		data.put("email", RandomStringUtils.randomAlphanumeric(20) + "@mailinator.com");
		data.put("code", RandomStringUtils.randomNumeric(3));
		data.put("phone", RandomStringUtils.randomNumeric(10));
		data.put("brand", "All brands");
		data.put("save", "yes");
		return data;
	}
	
	public static Hashtable<String,String> appDevicePublish(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("type", "Device");
		data.put("company", CreatePage.partner_company_name);
		data.put("brand", CreatePage.partner_brand_name_1);
		data.put("name", "Device" + RandomStringUtils.randomNumeric(6));
		data.put("os", "iOS");
		data.put("license", RandomStringUtils.randomAlphanumeric(20));
		data.put("audio route", Constant.AUDIO_ROUTE_FILE);
		data.put("global promotion", "OFF");
		data.put("save", "yes");
		return data;
	}
	
	public static Hashtable<String,String> appDeviceDraft(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("type", "Device");
		data.put("company", CreatePage.partner_company_name);
		data.put("brand", CreatePage.partner_brand_name_1);
		data.put("name", "Device" + RandomStringUtils.randomNumeric(6));
		data.put("os", "iOS");
		data.put("license", RandomStringUtils.randomAlphanumeric(20));
		data.put("save", "yes");
		return data;
	}
	
	public static Hashtable<String,String> promotionGlobal(String promotionName){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("salesforceid", RandomStringUtils.randomNumeric(9));
		data.put("promotion type", "Global");
		data.put("name", promotionName);
		data.put("save", "yes");
		return data;
	}
	
	public static Hashtable<String,String> promotionAppDevice(String promotionName, String appDeviceBrand, String appDeviceName){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("salesforceid", RandomStringUtils.randomNumeric(9));
		data.put("promotion type", "App/Device");
		data.put("device brand", appDeviceBrand);
		data.put("device name", appDeviceName);
		data.put("name", promotionName);
		data.put("save", "yes");
		return data;
	}
	
}
