package com.dts.adminportal.util;


import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.data.type.LicenseeProductType;
import com.dts.adminportal.model.AddCompany;
import com.dts.adminportal.model.AddEditProductModel;

public class TestData {
	
	/**
	 * sample product data for testing
	 * @param companyName <String> name of company
	 * @param brandName <String> name of brand
	 * @param tuning <boolean> Add tuning data when true
	 * @param marketing <boolean> add marketing data when true
	 * @param publish <boolean> add image data when true
	 * @return 
	 */
	
	public static Hashtable<String,String> productData(String companyName, String brandName, boolean tuning, boolean marketing, boolean publish){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("company", companyName);
		data.put("brand", brandName);
		data.put("name","SQATest"+ RandomStringUtils.randomNumeric(7));
		data.put("number", RandomStringUtils.randomNumeric(10));
		data.put("ean", RandomStringUtils.randomNumeric(13));
		data.put("upc", RandomStringUtils.randomNumeric(12));
		data.put("type", "Ear-buds");
		data.put("wired", "1 (Mono)");
		data.put("description", RandomStringUtils.randomAlphanumeric(20));
		if (tuning) {
			data.put("add tunning", AddEditProductModel.FileUpload.Tuning_HPXTT_BothBlueLineUSB.getName());
			data.put("tuning rating", "A");
		}
		if (marketing) {
			data.put("add marketing", AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		}
		if(publish) {
			data.put("img1", AddEditProductModel.FileUpload.IMG_250_JPG.getName());
			data.put("img2", AddEditProductModel.FileUpload.IMG_500_JPG.getName());
			data.put("img3", AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		}
		data.put("save", "yes");
		return data;
	}

	/**
	 * sample variant data for testing
	 * @param tuning <boolean> Add tuning data when true
	 * @param marketing <boolean> add marketing data when true
	 * @param publish <boolean> add image data when true
	 * @return 
	 */	
	public static Hashtable<String,String> variantData(boolean tuning, boolean marketing, boolean publish){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("name", "Variant" + RandomStringUtils.randomNumeric(7));
		data.put("ean", RandomStringUtils.randomNumeric(13));
		data.put("upc", RandomStringUtils.randomNumeric(12));
		data.put("descriptor", RandomStringUtils.randomAlphanumeric(20));
		data.put("add tunning", "use parent");
		if (tuning) {
			data.put("add tunning", AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName());
			data.put("tuning rating", "A");
		}
		if (marketing) {
			data.put("add marketing", AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		}
		if(publish) {
			data.put("img1", AddEditProductModel.FileUpload.IMG_250_JPG.getName());
			data.put("img2", AddEditProductModel.FileUpload.IMG_500_JPG.getName());
			data.put("img3", AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		}
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
		data.put("img1", AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		data.put("img2", AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		data.put("img3", AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
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
		String date = DateUtil.getADateGreaterThanToday("dd MMM yyyy", 3);
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("type", "Partner");
		data.put("date", date);
		data.put("name", companyName);
		data.put("address", RandomStringUtils.randomAlphanumeric(20));
		data.put("city", RandomStringUtils.randomAlphabetic(10));
		data.put("state", RandomStringUtils.randomAlphabetic(10));
		data.put("country", "Vietnam");
		data.put("partnerships", AddCompany.Partnership_List.Audio_Accessories.getName());
		return data;
	}
	public static Hashtable<String,String> licenseCompany(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		String companyName = "Test" + RandomStringUtils.randomNumeric(5);
		String date = DateUtil.getADateGreaterThanToday("dd MMM yyyy", 3);
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("type", "Partner");
		data.put("date", date);
		data.put("name", companyName);
		data.put("address", RandomStringUtils.randomAlphanumeric(20));
		data.put("city", RandomStringUtils.randomAlphabetic(10));
		data.put("state", RandomStringUtils.randomAlphabetic(10));
		data.put("country", "Vietnam");
		data.put("partnerships", AddCompany.Partnership_List.Audio_Accessories.getName());
		data.put("licensedProduct", LicenseeProductType.HPX_LOW_2.getName());
		// data.put("save", "yes");
		return data;
	}
	
	public static Hashtable<String,String> partnerUser(){
		
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("firstName", "first"+ RandomStringUtils.randomAlphanumeric(10));
		data.put("lastName", "last"+ RandomStringUtils.randomAlphanumeric(10));
		data.put("title", RandomStringUtils.randomAlphanumeric(20));
		data.put("company", "TestCorp-Auto14");
		data.put("email", RandomStringUtils.randomAlphanumeric(20) + "@mailinator.com");
		data.put("code", "+" + RandomStringUtils.randomNumeric(3));
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
		data.put("code", "+" + RandomStringUtils.randomNumeric(3));
		data.put("phone", RandomStringUtils.randomNumeric(10));
		data.put("brand", "All brands");
		data.put("save", "yes");
		return data;
	}
	
	public static Hashtable<String,String> appDevicePublish(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("type", "Device");
		data.put("eagle version", "Eagle 1.4");
		data.put("product type", "HPX High");
		data.put("company", BasePage.PARTNER_COMPANY_NAME);
		data.put("brand", BasePage.PARTNER_BRAND_NAME_1);
		data.put("name", "Device" + RandomStringUtils.randomNumeric(6));
		data.put("os", "iOS");
		data.put("license", RandomStringUtils.randomAlphanumeric(20));
		data.put("audio route", AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		data.put("global promotion", "OFF");
		data.put("save", "yes");
		return data;
	}
	public static Hashtable<String,String> appDeviceLicensePublish(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("type", "Device");
		data.put("eagle version", "Eagle 1.4");
		data.put("product type", "HPX High");
		data.put("brand", BasePage.PARTNER_BRAND_NAME_1);
		data.put("name", "Device" + RandomStringUtils.randomNumeric(6));
		data.put("os", "iOS");
		data.put("license", RandomStringUtils.randomAlphanumeric(20));
		data.put("audio route", AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		data.put("global promotion", "ON");
		data.put("save", "yes");
		return data;
	}
	public static Hashtable<String,String> appDeviceLicenseMediumPublish(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("type", "Device");
		data.put("eagle version", "Eagle 1.4");
		data.put("product type", "HPX Medium");
		data.put("brand", BasePage.PARTNER_BRAND_NAME_1);
		data.put("name", "Device" + RandomStringUtils.randomNumeric(6));
		data.put("os", "iOS");
		data.put("license", RandomStringUtils.randomAlphanumeric(20));
		data.put("audio route", AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		data.put("global promotion", "ON");
		data.put("save", "yes");
		return data;
	}
	public static Hashtable<String,String> appDeviceLicenseLowPublish(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("type", "Device");
		data.put("eagle version", "Eagle 1.4");
		data.put("product type", "HPX Low");
		data.put("brand", BasePage.PARTNER_BRAND_NAME_1);
		data.put("name", "Device" + RandomStringUtils.randomNumeric(6));
		data.put("os", "iOS");
		data.put("license", RandomStringUtils.randomAlphanumeric(20));
		data.put("audio route", AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		data.put("global promotion", "ON");
		data.put("save", "yes");
		return data;
	}
	public static Hashtable<String,String> appDevicePublishMedium(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("type", "Device");
		data.put("eagle version", "Eagle 1.4");
		data.put("product type", "HPX Medium");
		data.put("company", BasePage.PARTNER_COMPANY_NAME);
		data.put("brand", BasePage.PARTNER_BRAND_NAME_1);
		data.put("name", "Device" + RandomStringUtils.randomNumeric(6));
		data.put("os", "iOS");
		data.put("license", RandomStringUtils.randomAlphanumeric(20));
		data.put("audio route", AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		data.put("global promotion", "OFF");
		data.put("save", "yes");
		return data;
	}
	public static Hashtable<String,String> appDevicePublishLow(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("type", "Device");
		data.put("eagle version", "Eagle 1.4");
		data.put("product type", "HPX Low");
		data.put("company", BasePage.PARTNER_COMPANY_NAME);
		data.put("brand", BasePage.PARTNER_BRAND_NAME_1);
		data.put("name", "Device" + RandomStringUtils.randomNumeric(6));
		data.put("os", "iOS");
		data.put("license", RandomStringUtils.randomAlphanumeric(20));
		data.put("audio route", AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		data.put("save", "yes");
		return data;
	}
	public static Hashtable<String,String> appDevicePublishEagleV2(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("type", "Device");
		data.put("eagle version", "Eagle 2.0");
		data.put("product type", "DTS:X Ultra");
		data.put("company", BasePage.PARTNER_COMPANY_NAME);
		data.put("brand", BasePage.PARTNER_BRAND_NAME_1);
		data.put("name", "AA_V2_Device" + RandomStringUtils.randomNumeric(6));
		data.put("os", "iOS");
		//data.put("license", RandomStringUtils.randomAlphanumeric(20));
		//data.put("audio route", AddEditProductModel.FileUpload.Default_External_Audio_EagleV20.getName());
		// update to MPG-5927
		data.put("audio route", AddEditProductModel.FileUpload.Default_Audio_Tuning_Exptune_Files.getName());
		data.put("global promotion", "OFF");
		data.put("save", "yes");
		return data;
	}
	public static Hashtable<String,String> appDeviceLicensePublishEagleV2(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("type", "Device");
		data.put("eagle version", "Eagle 2.0");
		data.put("product type", "DTS:X Ultra");
		data.put("brand", BasePage.BRAND_NAME_LICENSEE);
		data.put("name", "AA_V2_Device" + RandomStringUtils.randomNumeric(6));
		data.put("os", "iOS");
		data.put("audio route", AddEditProductModel.FileUpload.Default_Audio_Tuning_Exptune_Files.getName());
		data.put("save", "yes");
		return data;
	}
	public static Hashtable<String,String> appDeviceLicensePublishPremium(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("type", "Device");
		data.put("eagle version", "Eagle 2.0");
		data.put("product type", "DTS:X Premium");
		data.put("brand", BasePage.PARTNER_BRAND_NAME_1);
		data.put("name", "AA_V2_Device" + RandomStringUtils.randomNumeric(6));
		data.put("os", "iOS");
		data.put("audio route", AddEditProductModel.FileUpload.Default_Audio_Tuning_Exptune_Files.getName());
		data.put("save", "yes");
		return data;
	}
	public static Hashtable<String,String> appDeviceLicensePublishAudioProcessing(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("type", "Device");
		data.put("eagle version", "Eagle 2.0");
		data.put("product type", "DTS Audio Processing");
		data.put("brand", BasePage.PARTNER_BRAND_NAME_1);
		data.put("name", "AA_V2_Device" + RandomStringUtils.randomNumeric(6));
		data.put("os", "iOS");
		data.put("audio route", AddEditProductModel.FileUpload.Default_Audio_Tuning_Exptune_Files.getName());
		data.put("save", "yes");
		return data;
	}
	public static Hashtable<String,String> appDeviceDTSPublishAudioProcessing(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("type", "Device");
		data.put("eagle version", "Eagle 2.0");
		data.put("company", BasePage.PARTNER_COMPANY_NAME);
		data.put("product type", "DTS Audio Processing");
		data.put("brand", BasePage.PARTNER_BRAND_NAME_1);
		data.put("name", "AA_V2_Device" + RandomStringUtils.randomNumeric(6));
		data.put("os", "iOS");
		data.put("audio route", AddEditProductModel.FileUpload.Default_Audio_Tuning_Exptune_Files.getName());
		data.put("save", "yes");
		return data;
	}

	public static Hashtable<String,String> appDevicePublishSingleFile(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("type", "Device");
		data.put("eagle version", "Eagle 2.0");
		data.put("product type", "DTS:X Ultra");
		data.put("company", BasePage.DTS_COMPANY_NAME);
		data.put("brand", BasePage.DTS_BRAND_NAME);
		data.put("os", "iOS");
		data.put("name", "V2_Device" + RandomStringUtils.randomNumeric(6));
		//data.put("license", RandomStringUtils.randomAlphanumeric(20));
		data.put("audio route", AddEditProductModel.FileUpload.Default_Single_File_Audio.getName());
		data.put("global promotion", "OFF");
		data.put("save", "yes");
		return data;
	}
	public static Hashtable<String,String> appDevicePublishSingleFilePremium(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("type", "Device");
		data.put("eagle version", "Eagle 2.0");
		data.put("product type", "DTS:X Premium");
		data.put("company", BasePage.DTS_COMPANY_NAME);
		data.put("brand", BasePage.DTS_BRAND_NAME);
		data.put("name", "V2_Device" + RandomStringUtils.randomNumeric(6));
		data.put("os", "iOS");
		//data.put("license", RandomStringUtils.randomAlphanumeric(20));
		data.put("audio route", AddEditProductModel.FileUpload.Default_Single_File_Audio.getName());
		data.put("global promotion", "OFF");
		data.put("save", "yes");
		return data;
	}
	public static Hashtable<String,String> appDevicePublishSingleFileAudioProcessing(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("type", "Device");
		data.put("eagle version", "Eagle 2.0");
		data.put("product type", "DTS Audio Processing");
		data.put("company", BasePage.DTS_COMPANY_NAME);
		data.put("brand", BasePage.DTS_BRAND_NAME);
		data.put("name", "V2_Device" + RandomStringUtils.randomNumeric(6));
		data.put("os", "iOS");
		//data.put("license", RandomStringUtils.randomAlphanumeric(20));
		data.put("audio route", AddEditProductModel.FileUpload.Default_Single_File_Audio.getName());
		data.put("save", "yes");
		return data;
	}

	public static void setDefaultAudioTuning(Hashtable<String,String> data, String newTuningFileName){
		data.remove("audio route");
		data.put("audio route", newTuningFileName);
	}

	
	public static Hashtable<String,String> appDeviceDraft(){
		Hashtable<String,String> data = new Hashtable<String,String>();
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("type", "Device");
		data.put("eagle version", "Eagle 1.4");
		data.put("product type", "HPX High");
		data.put("company", BasePage.PARTNER_COMPANY_NAME);
		data.put("brand", BasePage.PARTNER_BRAND_NAME_1);
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