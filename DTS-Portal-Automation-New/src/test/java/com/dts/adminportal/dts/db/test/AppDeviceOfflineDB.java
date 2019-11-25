package com.dts.adminportal.dts.db.test;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import junit.framework.Assert;

import org.bytedeco.javacpp.opencv_stitching.HomographyBasedEstimator;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddBrand;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AddPromotion;
import com.dts.adminportal.model.AudioRoutes;
import com.dts.adminportal.model.AudioRoutesEdit;
import com.dts.adminportal.model.AudioRoutesInfo;
import com.dts.adminportal.model.BrandInfo;
import com.dts.adminportal.model.CompanyInfo;
import com.dts.adminportal.model.DTSHome;
import com.dts.adminportal.model.DeviceEdit;
import com.dts.adminportal.model.DeviceInfo;
import com.dts.adminportal.model.DeviceList;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.PromotionInfo;
import com.dts.adminportal.model.PromotionList;
import com.dts.adminportal.util.DateUtil;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.SQLiteDbUtil;
import com.dts.adminportal.util.TestData;
import com.dts.adminportal.util.StringUtil;

public class AppDeviceOfflineDB extends BasePage{
	
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
	}
	
	/*
	 * Include newly created brands that may not have any products associated with them in the DEVICE_BRAND association table in the offline headphone DB
	 */
	@Test
	public void TCHPX_2379(){
		Reporter.log(" TCHPX_2379: Verify that Offline database include newly created brands that not have any products associated with them in the DEVICE_BRAND association table");
		
		/*
		1. Log into devportal as an admin user	
		2. Navigate to Companies page	
		3. Select a company from company list	
		4. Create a new brand	
		5. Navigate to App/Device page	
		6. Create a new app/device with all brand (All brands in the DTS catalog)	
		7. Publish this app/device successfully	
		8. Refresh Offline DB successfully
		9. Download the Offline DB of this app/device
		VP: New brand is included in the Brand table and also in the DEVICE_BRAND table.
		*/
	
		
		//1. Log into devportal as an admin user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to Companies page
		appDeviceControl.click(PageHome.LINK_COMPANY);
		//3. Select a company from company list
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		//4. Create a new brand
		appDeviceControl.click(CompanyInfo.ADD_BRAND);
		Hashtable<String,String> newBrand = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(),newBrand);
		//5. Navigate to App/Device page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//6. Create a new app/device with all brand (All brands in the DTS catalog)
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		//7. Publish this app/device successfully
		appDeviceControl.click(DeviceInfo.PUBLISH);
		//8. Refresh Offline DB successfully
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		//9. Download the Offline DB of this app/device
		boolean isDownloaded = appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE);
		/*
		 * VP: New brand is included in the Brand table and also in the DEVICE_BRAND table.
		 */
		Assert.assertTrue(isDownloaded);
		Assert.assertTrue(appDeviceControl.isIncludedBrand(appDeviceControl.getOfflineDBName(dts_id),newBrand.get("name")));

//		// Delete data
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		appDeviceControl.click(PageHome.LINK_COMPANY);
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		companyControl.selectABrandByName(newBrand.get("name"));
		companyControl.doDelete(BrandInfo.DELETE_LINK);
	}
	
	/*
	 * Validation : Validate that the uploaded tuning file for headphones includes all sample rates for 3D HPEQ and AEQ coefficients
	 */
//	@Test
//	public void TCHPX_2375(){
//		Reporter.log("TCHPX_2375: Validation : Validate that the uploaded tuning file for headphones includes all sample rates for 3D HPEQ and AEQ coefficients");
//	
//		/*
//		 * 1. Log into devportal as an admin user	
//			2. Navigate to Products page	
//			3. Click 'Add Product' link	
//			4. Upload a hpxtt tuning file that the 3DHPEQ contains only 44.1k coefficient in 'model {headphone{' section	
//			VP: The error message is displayed
//			5. Upload a hpxtt tuning file that the 3DHPEQ contains only 48k coefficient in 'model {headphone{' section	
//			VP: The error message is displayed
//			6. Upload a hpxtt tuning file that the OEM EQ contains only 44.1k coefficient in 'model {aeq{' section	
//			VP: The error message is displayed
//			7. Upload a hpxtt tuning file that the OEM EQ contains only 48k coefficient in 'model {aeq{' section	
//			VP: The error message is displayed
//
//		 */
//		
//		//1. Log into devportal as an admin user
//		loginControl.login(SUPER_USER_NAME,SUPER_USER_PASSWORD);
//		//2. Navigate to Products page	
//		appDeviceControl.click(PageHome.linkAccessories);
//		//3. Click 'Add Product' link
//		appDeviceControl.click(ProductModel.ADD_PRODUCT);
//		//4. Upload a hpxtt tuning file that the 3DHPEQ contains only 44.1k coefficient in 'model {headphone{' section
//		//appDeviceControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload);
//		/*
//		 * VP: The error message is displayed
//		 */
//		
//		//5. Upload a hpxtt tuning file that the 3DHPEQ contains only 48k coefficient in 'model {headphone{' section	
//		/*
//		 * VP: The error message is displayed
//		 */
//		//6. Upload a hpxtt tuning file that the OEM EQ contains only 44.1k coefficient in 'model {aeq{' section	
//		/*
//		 * VP: The error message is displayed
//		 */
//		//7. Upload a hpxtt tuning file that the OEM EQ contains only 48k coefficient in 'model {aeq{' section	
//		/*
//		 * VP: The error message is displayed
//		 */
//	}
	
	/*
	 * Validation : Validate that the uploaded custom tuning files for Apps/Device page have sample 44100 and 48000 sample rate coefficients IF the 3D HPEQ and/or AEQ coefficients are present.
	 */
	@Test
	public void TCHPX_2376(){
		Reporter.log("TCHPX_2376: Validation : Validate that the uploaded custom tuning files for Apps/Device page have sample 44100 and 48000 sample rate coefficients IF the 3D HPEQ and/or AEQ coefficients are present.");
	
		/*
		 * 1. Log into devportal as an admin user	
			2. Navigate to Apps & Devices page	
			3. Click Add App or Device link	
			4. Set the Audio Route to Line-out0 - Line Out and the Standard Accessories to 'External Speaker'	
			5. Upload a custom tuning file that the 3DHPEQ contains only 44.1k coefficient in 'model {headphone{' section	
			VP: The error message '44.1k and/or 48k coefficients for 3D Headphone EQ are missing.' is displayed
			6. Upload a custom tuning file that the 3DHPEQ contains only 48k coefficient in 'model {headphone{' section	
			VP: The error message '44.1k and/or 48k coefficients for 3D Headphone EQ are missing.' is displayed
			7. Upload a custom tuning file that the OEM EQ contains only 44.1k coefficient in 'model {aeq{' section	
			VP: The error message '44.1k and/or 48k coefficients for AEQ are missing.' is displayed
			8. Upload a custom tuning file that the OEM EQ contains only 48k coefficient in 'model {aeq{' section	
			VP: The error message '44.1k and/or 48k coefficients for AEQ are missing.' is displayed
			9. Repeat from step 4 to 8 for the Audio Route: Bluetooth0 - Bluetooth	
			VP: Verify error message same as above for each step.
			10. Repeat from step 5 to 8 for the default tuning file	
			VP: Verify error message same as above for each step.


		 */
		//1. Log into devportal as an admin user	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to Apps & Devices page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Click Add App or Device link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		//4. Set the Audio Route to Line-out0 - Line Out and the Standard Accessories to 'External Speaker'	
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Line_out0.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.External_Speakers.getName());
		//5. Upload a custom tuning file that the 3DHPEQ contains only 44.1k coefficient in 'model {headphone{' section	
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.External_Speakers_Lineout_3DHPEQ_missing_48k.getName());
		/*
		 * VP: The error message '44.1k and/or 48k coefficients for 3D Headphone EQ are missing.' is displayed
		 */
		Assert.assertEquals(DeviceEdit.requires.Invalid_EQ_is_missing.getName(),appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS));
		//6. Upload a custom tuning file that the 3DHPEQ contains only 48k coefficient in 'model {headphone{' section
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.External_Speakers_Lineout_3DHPEQ_missing_44k.getName());
		/*
		 * VP: The error message '44.1k and/or 48k coefficients for 3D Headphone EQ are missing.' is displayed
		 */
		Assert.assertEquals(DeviceEdit.requires.Invalid_EQ_is_missing.getName(),appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS));
		//7. Upload a custom tuning file that the OEM EQ contains only 44.1k coefficient in 'model {aeq{' section	
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.External_Speakers_Lineout_AEQ_missing_48k.getName());
		/*
		 * VP: The error message '44.1k and/or 48k coefficients for AEQ are missing.' is displayed
		 */
		Assert.assertEquals(DeviceEdit.requires.Invalid_AEQ_is_missing.getName(),appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS));
		//8. Upload a custom tuning file that the OEM EQ contains only 48k coefficient in 'model {aeq{' section	
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.External_Speakers_Lineout_AEQ_missing_44k.getName());
		/*
		 * VP: The error message '44.1k and/or 48k coefficients for AEQ are missing.' is displayed
		 */
		Assert.assertEquals(DeviceEdit.requires.Invalid_AEQ_is_missing.getName(),appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS));
		//9. Repeat from step 4 to 8 for the Audio Route: Bluetooth0 - Bluetooth
		
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Bluetooth0.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.External_Speakers.getName());
		//Upload a custom tuning file that the 3DHPEQ contains only 44.1k coefficient in 'model {headphone{' section	
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.External_Speakers_Bluetooth_3DHPEQ_missing_48k.getName());
		/*
		 * VP: The error message '44.1k and/or 48k coefficients for 3D Headphone EQ are missing.' is displayed
		 */
		Assert.assertEquals(DeviceEdit.requires.Invalid_EQ_is_missing.getName(),appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS));
		//Upload a custom tuning file that the 3DHPEQ contains only 48k coefficient in 'model {headphone{' section
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.External_Speakers_Bluetooth_3DHPEQ_missing_44k.getName());
		/*
		 * VP: The error message '44.1k and/or 48k coefficients for 3D Headphone EQ are missing.' is displayed
		 */
		Assert.assertEquals(DeviceEdit.requires.Invalid_EQ_is_missing.getName(),appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS));
		//Upload a custom tuning file that the OEM EQ contains only 44.1k coefficient in 'model {aeq{' section	
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.External_Speakers_Bluetooth_AEQ_missing_48k.getName());
		/*
		 * VP: The error message '44.1k and/or 48k coefficients for AEQ are missing.' is displayed
		 */
		Assert.assertEquals(DeviceEdit.requires.Invalid_AEQ_is_missing.getName(),appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS));
		//Upload a custom tuning file that the OEM EQ contains only 48k coefficient in 'model {aeq{' section	
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.External_Speakers_Bluetooth_AEQ_missing_44k.getName());
		/*
		 * VP: The error message '44.1k and/or 48k coefficients for AEQ are missing.' is displayed
		 */
		Assert.assertEquals(DeviceEdit.requires.Invalid_AEQ_is_missing.getName(),appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS));
		//10. Repeat from step 5 to 8 for the default tuning file
		
		//Upload a default tuning file that the 3DHPEQ contains only 44.1k coefficient in 'model {headphone{' section	
		appDeviceControl.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE,AddEditProductModel.FileUpload.External_Speakers_Bluetooth_3DHPEQ_missing_48k.getName());
		/*
		 * VP: The error message '44.1k and/or 48k coefficients for 3D Headphone EQ are missing.' is displayed
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE).contains(DeviceEdit.requires.Invalid_EQ_is_missing.getName()));
		//Upload a default tuning file that the 3DHPEQ contains only 48k coefficient in 'model {headphone{' section
		appDeviceControl.uploadFile(DeviceEdit.RETRY_DEFAULT_AUDIO_ROUTE,AddEditProductModel.FileUpload.External_Speakers_Bluetooth_3DHPEQ_missing_44k.getName());
		/*
		 * VP: The error message '44.1k and/or 48k coefficients for 3D Headphone EQ are missing.' is displayed
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE).contains(DeviceEdit.requires.Invalid_EQ_is_missing.getName()));
		//Upload a default tuning file that the OEM EQ contains only 44.1k coefficient in 'model {aeq{' section	
		appDeviceControl.uploadFile(DeviceEdit.RETRY_DEFAULT_AUDIO_ROUTE,AddEditProductModel.FileUpload.External_Speakers_Bluetooth_AEQ_missing_48k.getName());
		/*
		 * VP: The error message '44.1k and/or 48k coefficients for AEQ are missing.' is displayed
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE).contains(DeviceEdit.requires.Invalid_AEQ_is_missing.getName()));
		//Upload a default tuning file that the OEM EQ contains only 48k coefficient in 'model {aeq{' section	
		appDeviceControl.uploadFile(DeviceEdit.RETRY_DEFAULT_AUDIO_ROUTE,AddEditProductModel.FileUpload.External_Speakers_Bluetooth_AEQ_missing_44k.getName());
		/*
		 * VP: The error message '44.1k and/or 48k coefficients for AEQ are missing.' is displayed
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE).contains(DeviceEdit.requires.Invalid_AEQ_is_missing.getName()));
		
	}
	
	/*
	 * Validation : Validate that the generated tuning file size associated to the  "Complete Profile" link on Apps/Device page does not exceed 1MB
	 */
	@Test
	public void TCHPX_2377(){
		Reporter.log("TCHPX_2377 Validation : Validate that the generated tuning file size associated to the  'Complete Profile' link on Apps/Device page does not exceed 1MB");
	
		/*1. Log into devportal as an admin user	
		2. Navigate to Apps & Devices page	
		3. Click Add App or Device link	
		4. Upload a valid upload tuing file 	
		5. Fill valid value into all required fields	
		6. Published new device successfully	
		7. Download the published profile	
		VP: The Complete profile is downloaded successfully
		VP: The downloaded Complete profile files size does not exceed 1MB
		*/
		
		//1. Log into devportal as an admin user
		loginControl.login(SUPER_USER_NAME,SUPER_USER_PASSWORD);
		//2. Navigate to Apps & Devices page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Click Add App or Device link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		//4. Upload a valid upload tuning file 
		//5. Fill valid value into all required fields
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		//6. Published new device successfully	
		appDeviceControl.click(DeviceInfo.PUBLISH);
		//7. Download the published profile
		String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		boolean isDownloaded = appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK);
		String profileName = "profile_"+dts_id+".dtscs";
		/*
		 * VP: The Complete profile is downloaded successfully
		 */
		Assert.assertTrue(isDownloaded);
		/*
		 * VP: The downloaded Complete profile files size does not exceed 1MB
		 */
		Assert.assertTrue(appDeviceControl.isNotExceed1MB(profileName));
		// Delete device
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Validation : Validate that the uploaded custom tuning files for Audio page have sample 44100 and 48000 sample rate coefficients IF the 3D HPEQ and/or AEQ coefficients are present.
	 */
	@Test
	public void TCHPX_2434(){
		Reporter.log("TCHPX_2434: Validation : Validate that the uploaded custom tuning files for Audio page have sample 44100 and 48000 sample rate coefficients IF the 3D HPEQ and/or AEQ coefficients are present.");
	/*
	 	1. Log into devportal as an admin user	
		2. Navigate to Audio Page	
		3. Select a audio route in "Standard Accessories" section	
		4. Click "Edit Version" link	
		5. Upload a tuning file that 3DHPEQ includes only 44.1K sample rates coefficients in " model { headhones {" section	
		VP: The error message '44.1k and/or 48k coefficients for 3D Headphone EQ are missing.' is displayed
		6. Upload a tuning file that 3DHPEQ includes only 48K sample rates coefficients in " model { headhones {" section	
		VP: The error message '44.1k and/or 48k coefficients for 3D Headphone EQ are missing.' is displayed
		7. Upload a tuning file that AEQ  includes only 44.1K sample rates coefficients in " model { aeq {" section	
		VP: The error message '44.1k and/or 48k coefficients for AEQ are missing.' is displayed
		8. Upload a tuning file that AEQ  includes only 48K sample rates coefficients in " model { aeq {" section	
		VP: The error message '44.1k and/or 48k coefficients for AEQ are missing.' is displayed
		9. Upload a valid tuning file that includes full sample rates coefficients for 3DHPEQ and AEQ 	
		VP: The tuning file is uploaded successfully
		10. Click "Save" link	
		VP: The audio route is saved successfully
		11. Repeat from step 2 to step 10 with a audio route in "Other Audio Routes" section	

	 */
		//1. Log into devportal as an admin user
		loginControl.login(SUPER_USER_NAME,SUPER_USER_PASSWORD);
		//2. Navigate to Audio Page
		audioControl.click(PageHome.linkAudioroutes);
		//3. Select a audio route in "Standard Accessories" section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName()); // Over-Ear Headphone
		//4. Click "Edit Version" link
		audioControl.editVersion();
		//5. Upload a tuning file that 3DHPEQ includes only 44.1K sample rates coefficients in " model { headhones {" section	
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING,AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined_hpeq_44100.getName());
		/*
		 * VP: The error message '44.1k and/or 48k coefficients for 3D Headphone EQ are missing.' is displayed
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(AudioRoutesEdit.INVALID_MESSAGE).contains(AudioRoutesEdit.Upload_File_Message.Invalid_file_44_1k_48k_for_3D.getName()));
		//6. Upload a tuning file that 3DHPEQ includes only 48K sample rates coefficients in " model { headhones {" section	
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING,AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined_hpeq_48000.getName());
		/*
		 * VP: The error message '44.1k and/or 48k coefficients for 3D Headphone EQ are missing.' is displayed
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(AudioRoutesEdit.INVALID_MESSAGE).contains(AudioRoutesEdit.Upload_File_Message.Invalid_file_44_1k_48k_for_3D.getName()));
		//7. Upload a tuning file that AEQ  includes only 44.1K sample rates coefficients in " model { aeq {" section	
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING,AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined_aeq_44100.getName());
		/*
		 * VP: The error message '44.1k and/or 48k coefficients for AEQ are missing.' is displayed
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(AudioRoutesEdit.INVALID_MESSAGE).contains(AudioRoutesEdit.Upload_File_Message.Invalid_file_44_1k_48k_for_AEQ.getName()));
		//8. Upload a tuning file that AEQ  includes only 48K sample rates coefficients in " model { aeq {" section	
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING,AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined_aeq_48000.getName());
		/*
		 * VP: The error message '44.1k and/or 48k coefficients for AEQ are missing.' is displayed
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(AudioRoutesEdit.INVALID_MESSAGE).contains(AudioRoutesEdit.Upload_File_Message.Invalid_file_44_1k_48k_for_AEQ.getName()));
		//9. Upload a valid tuning file that includes full sample rates coefficients for 3DHPEQ and AEQ 	
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING,AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined.getName());
		/*
		 * VP: The tuning file is uploaded successfully
		 */
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesEdit.UPLOADED_AUDIO_ROUTE));
		//10. Click "Save" link	
		audioControl.click(AudioRoutesEdit.SAVE);
		/*
		 * VP: The audio route is saved successfully
		 */
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesInfo.DISPLAY_MODEL));
		//11. Repeat from step 2 to step 10 with a audio route in "Other Audio Routes" sectionaudioControl.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[3]); // External Speaker
		audioControl.click(PageHome.linkAudioroutes);
		// Select a audio route in "Standard Accessories" section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Other_Routes.Attached4_Internal_Speakers.getName()); // Attached5 - Internal Speakers (mode 4)
		// Click "Edit Version" link
		audioControl.editVersion();
		// Upload a tuning file that 3DHPEQ includes only 44.1K sample rates coefficients in " model { headhones {" section	
//		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING,AddEditProductModel.FileUpload.External_Speakers_Bluetooth_3DHPEQ_missing_48k.getName());
//		/*
//		 * VP: The error message '44.1k and/or 48k coefficients for 3D Headphone EQ are missing.' is displayed
//		 */
//		Assert.assertTrue(appDeviceControl.getTextByXpath(AudioRoutesEdit.INVALID_MESSAGE).contains(DeviceEdit.requires[11]));
//		// Upload a tuning file that 3DHPEQ includes only 48K sample rates coefficients in " model { headhones {" section	
//		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING,AddEditProductModel.FileUpload.External_Speakers_Bluetooth_3DHPEQ_missing_44k.getName());
//		/*
//		 * VP: The error message '44.1k and/or 48k coefficients for 3D Headphone EQ are missing.' is displayed
//		 */
//		Assert.assertTrue(appDeviceControl.getTextByXpath(AudioRoutesEdit.INVALID_MESSAGE).contains(DeviceEdit.requires[11]));
		// Upload a tuning file that AEQ  includes only 44.1K sample rates coefficients in " model { aeq {" section	
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING,AddEditProductModel.FileUpload.Attached5_InternalSpeakersVoice_aeq_44100.getName());
		/*
		 * VP: The error message '44.1k and/or 48k coefficients for AEQ are missing.' is displayed
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(AudioRoutesEdit.INVALID_MESSAGE).contains(AudioRoutesEdit.Upload_File_Message.Invalid_file_44_1k_48k_for_AEQ.getName()));
		// Upload a tuning file that AEQ  includes only 48K sample rates coefficients in " model { aeq {" section	
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING,AddEditProductModel.FileUpload.Attached5_InternalSpeakersVoice_aeq_48000.getName());
		/*
		 * VP: The error message '44.1k and/or 48k coefficients for AEQ are missing.' is displayed
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(AudioRoutesEdit.INVALID_MESSAGE).contains(AudioRoutesEdit.Upload_File_Message.Invalid_file_44_1k_48k_for_AEQ.getName()));
		// Upload a valid tuning file that includes full sample rates coefficients for 3DHPEQ and AEQ 	
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING,AddEditProductModel.FileUpload.Attached5InternalSpeakersVoice_Fallback.getName());
		/*
		 * VP: The tuning file is uploaded successfully
		 */
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesEdit.UPLOADED_AUDIO_ROUTE));
		// Click "Save" link	
		audioControl.click(AudioRoutesEdit.SAVE);
		/*
		 * VP: The audio route is saved successfully
		 */
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesInfo.DISPLAY_MODEL));
	
	}
	
	/*
	 * HP:X AEQ not applied
	 */
	@Test
	public void TCHPX_2666(){
		Reporter.log("HP:X AEQ not applied");
		/*
		 * 
		 */
		//1. Log into devportal as an admin user
		loginControl.login(SUPER_USER_NAME,SUPER_USER_PASSWORD);
		//2. Navigate to Product Page
		audioControl.click(PageHome.linkAccessories);
		//3. Add new product
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME,PARTNER_BRAND_NAME_1,false,true,true);
		dataProduct.remove("wired");
		dataProduct.remove("save");
		dataProduct.put("tuning rating", "A");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),dataProduct);
		//4. Set "Bluetooth" headphone in "Connection Type"
		productControl.selectACheckbox(AddEditProductModel.BLUETOOTH_CHECKBOX);
		//5. Upload tuning file for bluetooth which has "oemEqPreference":"hpEq"
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.BluetoothOnly_hpEq.getName());
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		//6. Publish above product
		productWf.approveTuning();
		productWf.approveMarketing();
		productControl.click(ProductDetailModel.PUBLISH);
		//7. Download Published Product Profile
		boolean isDownloaded = productControl.downloadProfile();
		/*
		 * VP: The Product Profile is downloaded successfully
		 */
		Assert.assertTrue(isDownloaded);
//		productControl.downloadFile(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE);
		String fileName = productControl.getProfileName();
		//8. Convert Product Profile to text file to check "bypass_hpeq" & "aeq_enable" values
		/*
		 * VP: The values of bypass_hpeq[0…6] = 0, aeq_enable[0..6] = 0
		 */
		productControl.convertDtscsText(fileName);
		Assert.assertTrue(productControl.checkValueBypass(fileName,"none","0",ProductDetailModel.TuningTypes.BlueToothOnly.getName()));
		Assert.assertTrue(productControl.checkValueBypass(fileName,"none","0",ProductDetailModel.TuningTypes.BlueToothOnly.getName()));
		//9. Click "Edit Version" link
		productControl.editVersion();
		//10. Repeat from step 5 to step 8 with tuning file for bluetooth which has "oemEqPreference":"bypass"
		// Upload tuning file for bluetooth
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.BluetoothOnly_bypass.getName());
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// Publish above product
		productWf.approveTuning();
		productWf.approveMarketing();
		productControl.click(ProductDetailModel.PUBLISH);
		// Download Published Product Profile
		/*
		 * VP: The Product Profile is downloaded successfully
		 */
		Assert.assertTrue(productControl.downloadProfile());
		// Convert Product Profile to text file to check "bypass_hpeq" & "aeq_enable" values
		/*
		 * VP: The values of bypass_hpeq[0…6] = 1, aeq_enable[0..6] = 0
		 */
		productControl.convertDtscsText(fileName);
		Assert.assertTrue(productControl.checkValueBypass(fileName,"none","1",ProductDetailModel.TuningTypes.BlueToothOnly.getName()));
		Assert.assertTrue(productControl.checkValueBypass(fileName,"none","0",ProductDetailModel.TuningTypes.BlueToothOnly.getName()));
		//11. Repeat from step 9 to step 10 with tuning file for bluetooth which has "oemEqPreference":"oemEq"
		// Click "Edit Version" link
		productControl.editVersion();
		// Upload tuning file for bluetooth
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.BluetoothOnly_oemEq.getName());
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// Publish above product
		productWf.approveTuning();
		productWf.approveMarketing();
		productControl.click(ProductDetailModel.PUBLISH);
		// Download Published Product Profile
		/*
		 * VP: The Product Profile is downloaded successfully
		 */
		Assert.assertTrue(productControl.downloadProfile());
		// Convert Product Profile to text file to check "bypass_hpeq" & "aeq_enable" values
		/*
		 * VP: The values of bypass_hpeq[0…6] = 1, aeq_enable[0..6] = 1
		 */
		productControl.convertDtscsText(fileName);
		Assert.assertTrue(productControl.checkValueBypass(fileName,"none","1",ProductDetailModel.TuningTypes.BlueToothOnly.getName()));
		Assert.assertTrue(productControl.checkValueBypass(fileName,"none","1",ProductDetailModel.TuningTypes.BlueToothOnly.getName()));
		//12. Repeat from step 9 to step 10 with tuning file for bluetooth which has "oemEqPreference":"hpEq+oemEq"
		// Click "Edit Version" link
		productControl.editVersion();
		// Upload tuning file for bluetooth
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.BluetoothOnly_hpEq_oemEq.getName());
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// Publish above product
		productWf.approveTuning();
		productWf.approveMarketing();
		productControl.click(ProductDetailModel.PUBLISH);
		// Download Published Product Profile
		/*
		 * VP: The Product Profile is downloaded successfully
		 */
		Assert.assertTrue(productControl.downloadProfile());
		// Convert Product Profile to text file to check "bypass_hpeq" & "aeq_enable" values
		/*
		 * VP: The values of bypass_hpeq[0…6] = 0, aeq_enable[0..6] = 1
		 */
		productControl.convertDtscsText(fileName);
		Assert.assertTrue(productControl.checkValueBypass(fileName,"none","0",ProductDetailModel.TuningTypes.BlueToothOnly.getName()));
		Assert.assertTrue(productControl.checkValueBypass(fileName,"none","1",ProductDetailModel.TuningTypes.BlueToothOnly.getName()));
		//13. Click "Edit Version" link
		productControl.editVersion();
		//14. Set "Wired" headphone in "Connection Type"
		productControl.selectACheckbox(AddEditProductModel.WIRED_CHECKBOX);
		productControl.uncheckACheckbox(AddEditProductModel.BLUETOOTH_CHECKBOX);
		//15. Upload tuning file for line-out which has "oemEqPreference":"hpEq"
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		//16. Repeat from step 6 to step 8
		/*
		 * VP: The values of bypass_hpeq[0…6] = 0, aeq_enable[0..6] = 0
		 */
		//17. Click "Edit Version" link
		//18. Repeat from step 15 to step 16 with tuning file for line-out which has "oemEqPreference":"bypass"
		/*
		 * VP: The values of bypass_hpeq[0…6] = 1, aeq_enable[0..6] = 0
		 */
		//19. Repeat from step 14 to step 16 with tuning file for line-out which has "oemEqPreference":"oemEq"
		/*
		 * VP: The values of bypass_hpeq[0…6] = 1, aeq_enable[0..6] = 1
		 */
		//20. Repeat from step 14 to step 16 with tuning file for line-out which has "oemEqPreference":"hpEq+oemEq"
		/*
		 * VP: The values of bypass_hpeq[0…6] = 0, aeq_enable[0..6] = 1
		 */
		//21. Repeat from step 13 to step 20 with both wired & bluetooth connection types and tuning file for combined  line-out & buletooth which has the same "oemEqPreference" values
	}
	@Test
	public void TCMPG_5693_01(){
		Reporter.log("Verify that App device page adds two APO download offline database links one link for UI the orther link for tuning and a refesh offline db button");
		/*
		 *  1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			"4. Leave the Product Type panel as ''DTS:X Master''
			"
			5. Fill valid value into all required fields
			6.  Click “Save” link
			7. Publish above device
			VP:  Verify that "Refresh offline database (APO) " button is displayed.
			8. Click on "Refresh offline database (APO) " button
			Verify  that Download offline database (UI) and Download offline database (Tuning)  links is displayed
			9. Click on "Update Infor" button
			10. Leave the Product Type panel as ''DTS:X Premium''
			11.  Click “Save” link
			12. Publish above device
			VP:  Verify that "Refresh offline database (APO) " button is displayed.
			13. Click on "Refresh offline database (APO) " button
			Verify  that Download offline database (UI) and Download offline database (Tuning)  links is displayed
			14. Click on "Update Infor" button
			15. Leave the Product Type panel as ''DTS Audio Processing'' 
			16.  Click “Save” link
			17. Publish above device
			18. Click on "Refresh offline database (APO) " button

		 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click “Add App or Device” link
//		4. Leave the Product Type panel as ''DTS:X Master''
//		5. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		6.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
//		7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		8. Click on "Refresh offline database (APO) " button
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		Verify  that Download offline database (UI) and Download offline database (Tuning)  links is displayed
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.DOWNLOAD_DATABASE_APO_UI));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING));
//		9. Click on "Update Infor" button
		appDeviceControl.click(DeviceInfo.CREATE_NEW_VERSION);
		appDeviceControl.selectConfirmationDialogOption("OK");
//		10. Leave the Product Type panel as ''DTS:X Premium''
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
//		11.  Click “Save” link
		appDeviceControl.click(DeviceEdit.SAVE);
//		12. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
		 Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		13. Click on "Refresh offline database (APO) " button
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		Verify  that Download offline database (UI) and Download offline database (Tuning)  links is displayed
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.DOWNLOAD_DATABASE_APO_UI));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING));
//		14. Click on "Update Infor" button
		appDeviceControl.click(DeviceInfo.CREATE_NEW_VERSION);
		appDeviceControl.selectConfirmationDialogOption("OK");
//		15. Leave the Product Type panel as ''DTS Audio Processing'' 
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
//		16.  Click “Save” link
		appDeviceControl.click(DeviceEdit.SAVE);
//		17. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
		 Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		18. Click on "Refresh offline database (APO) " button
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		Verify  that Download offline database (UI) and Download offline database (Tuning)  links is displayed
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.DOWNLOAD_DATABASE_APO_UI));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING));
		
	}
	@Test
	public void TCMPG_5693_02(){
		Reporter.log("Verify that two APO offline databases should be refreshed after clicking on 'Refresh offline database (APO)'");
		/*
		 *  1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type panel as ''DTS:X Master''
			5. Fill valid value into all required fields
			6.  Click “Save” link
			7. Publish above device
			8. Click on "Refresh offline database (APO) " button
			VP: Confirm that offline db UI and tuning is refresh successfully
			VP: Current date should be labeled at the end of download offline database UI and tuning link
		 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click “Add App or Device” link
//		4. Leave the Product Type panel as ''DTS:X Master''
//		5. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		6.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
//		7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		8. Click on "Refresh offline database (APO) " button
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//        Confirm that offline db UI and tuning is refresh successfully 
//        Current date should be labeled at the end of download offline database UI and tuning link
		String refreshedDate_tuning=appDeviceControl.getTextByXpath(DeviceInfo.OFFLINE_DATABASE_TUNING_REFRESHED_DATE);
		String refreshedDate_ui=appDeviceControl.getTextByXpath(DeviceInfo.OFFLINE_DATABASE_UI_REFRESHED_DATE);
		Assert.assertEquals(refreshedDate_tuning, DateUtil.getCurrent_DDMMMYYYY());
		Assert.assertEquals(refreshedDate_ui, DateUtil.getCurrent_DDMMMYYYY());
		
		
	}
	@Test
	public void TCMPG_5693_03(){
		Reporter.log("Verify that the offline database for UI includes the Product, Asset, Device, Brand, ProductLocalization, ProductAudioRoute, AudioRouteEnum, DeviceBrand, Config, USBLookup, BluetoothLookup, DeviceFeaturedProduct tables.");
	/*
	 *  1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click “Add App or Device” link
		"4. Leave the Product Type panel as ''DTS:X Master''
		5. Fill valid value into all required fields
		6.  Click “Save” link
		7. Publish above device
		8.Refresh APO offline database
		9. Click "Download Offline Database (UI)" link
		10. Open the offline databse  file by SQLite
		VP: VP: The offline database for UI will include the Product, Asset, Device, Brand, ProductLocalization, ProductAudioRoute, AudioRouteEnum, DeviceBrand, Config, USBLookup, BluetoothLookup, DeviceFeaturedProduct tables.
	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click “Add App or Device” link
//		4. Leave the Product Type panel as ''DTS:X Master''
//		5. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		6.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		appDeviceControl.click(DeviceEdit.SAVE);
//		7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		8. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		9. Click "Download Offline Database (UI)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_UI);
		appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_UI);
//		10. Open the offline databse  file by SQLite
//      VP: VP: The offline database for UI will include the Product, Asset, Device, Brand, ProductLocalization, ProductAudioRoute, AudioRouteEnum, DeviceBrand, Config, USBLookup, BluetoothLookup, DeviceFeaturedProduct tables.
       // ArrayList<String> uiTables= new ArrayList<String>();
        String[] uiTables= {"Brand", "DeviceBrand", "ProductAudioRoute", "Config", "Asset", "ProductLocalization", "AudioRouteEnum", "Device", "DeviceFeaturedProduct", "Product", "BluetoothLookup", "USBLookup"};
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameUI(device_UUID));
        System.out.println(device_UUID);
       // Assert.assertTrue(ListUtil.containsAll(dbUtil.getAllTables(), uiTables));
        Assert.assertTrue(ListUtil.isEquals(dbUtil.getAllTables(), uiTables));
	}
	@Test
	public void TCMPG_5693_04(){
		Reporter.log("Verify that at the UI db the Asset table only includes image data related to products.");
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click “Add App or Device” link
		4. Leave the Product Type panel as ''DTS:X Master''
		5. Fill valid value into all required fields
		6.  Click “Save” link
		7. Publish above device
		8.Refresh APO offline database
		9. Click "Download Offline Database (UI)" link
		10. Open the offline database  file by SQLite
		11. Navigate to Asset table in SQLite
 		VP: Confirm that Type column only contains the 'IMAGE_*'  text and Uuid column is added into Asset table
		VP: The Asset table contains Id, Type, Uri, Data, Filesize and Uuid fields
		 */
		
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click “Add App or Device” link
//		4. Leave the Product Type panel as ''DTS:X Master''
//		5. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		6.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		appDeviceControl.click(DeviceEdit.SAVE);
//		7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		8. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		9. Click "Download Offline Database (UI)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_UI);
		appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_UI);
//		10. Open the offline databse  file by SQLite
//      11. Navigate to Asset table in SQLite
//      VP: Confirm that Type column only contains the 'IMAGE_*'  text and Uuid column is added into Asset table
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameUI(device_UUID));
        Assert.assertTrue(dbUtil.getColumnData("Select * from Asset where Type NOT LIKE 'IMAGE_%' ", "Type", null).size()==0);
//		VP: The Asset table contains Id, Type, Uri, Data, Filesize and Uuid fields
        String[] columns = {"Id", "Type", "Uri", "Data", "Filesize", "Uuid"	};
        Assert.assertTrue(ListUtil.isEquals(columns,  dbUtil.getAllColumnName("Select * from Asset")));

	}
	@Test
	public void TCMPG_5693_05(){
		Reporter.log("Verify that the at UI db the DTSCSAssetId field will be removed in Device table");
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click “Add App or Device” link
		4. Leave the Product Type panel as ''DTS:X Master''
		5. Fill valid value into all required fields
		6.  Click “Save” link
		7. Publish above device
		8.Refresh APO offline database
		9. Click "Download Offline Database (UI)" link
		10. Open the offline database  file by SQLite
		11. Navigate to Device table in SQLite
		VP: The Asset table contains Id, Type, Uri, Data, Filesize and Uuid fields
		 */
		
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click “Add App or Device” link
//		4. Leave the Product Type panel as ''DTS:X Master''
//		5. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		6.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		appDeviceControl.click(DeviceEdit.SAVE);
//		7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		8. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		9. Click "Download Offline Database (UI)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_UI);
		appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_UI);
//		10. Open the offline databse  file by SQLite
//      11. Navigate to Device table in SQLite
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameUI(device_UUID));
//		VP: VP: The 'Device' table only contains Id, Uuid, Name, ParentBrandId and InboxUid fields and  DTSCSAssetId field is removed
        String[] columns = {"Id", "Uuid", "Name", "ParentBrandId", "InboxUuid"	};
        Assert.assertTrue(ListUtil.isEquals(columns,  dbUtil.getAllColumnName("Select * from Device")));
	}
	
	@Test
	public void TCMPG_5693_06(){
		Reporter.log("Verify that at  the UI db the ImageAssetIdSmall, ImageAssetIdMedium, ImageAssetIdLarge and DTSCSAssetId fields will be removed in Product table.");
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click “Add App or Device” link
		4. Leave the Product Type panel as ''DTS:X Master''
		5. Fill valid value into all required fields
		6.  Click “Save” link
		7. Publish above device
		8.Refresh APO offline database
		9. Click "Download Offline Database (UI)" link
		10. Open the offline database  file by SQLite
		11. 11. Navigate to Product table in SQLite
 		VP: The Product table only contains Id, Uuid and Name fields. 
        VP: The ImageAssetIdSmall, ImageAssetIdMedium, ImageAssetIdLarge and DTSCSAssetId  fields are removed in Product table
 		VP: The Product table will not include multi channel room products info
		 */
		
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click “Add App or Device” link
//		4. Leave the Product Type panel as ''DTS:X Master''
//		5. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		6.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		appDeviceControl.click(DeviceEdit.SAVE);
//		7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		8. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		9. Click "Download Offline Database (UI)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_UI);
		appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_UI);
//		10. Open the offline databse  file by SQLite
//      11. Navigate to Product table in SQLite
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameUI(device_UUID));
//		VP: The Product table only contains Id, Uuid and Name fields. 
        String[] columns = {"Id", "Uuid", "BrandId", "Name", "Description", "Type", "SubType", "ReleaseDate", "NoiseCancelingSupport" };
        Assert.assertTrue(ListUtil.isEquals(columns,  dbUtil.getAllColumnName("Select * from Product")));
	}
	@Test
	public void TCMPG_5693_07(){
		Reporter.log("Verify that at the UI db the ImageAssetIdSmall, ImageAssetIdMedium, ImageAssetIdLarge fields will be removed in Brand table.");
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click “Add App or Device” link
		4. Leave the Product Type panel as ''DTS:X Master''
		5. Fill valid value into all required fields
		6.  Click “Save” link
		7. Publish above device
		8.Refresh APO offline database
		9. Click "Download Offline Database (UI)" link
		10. Open the offline database  file by SQLite
		11. 11. Navigate to Brand table in SQLite
 		VP: The Brand table only contains Id, Uuid, BrandId, Name, Description, Type, SubType, RleaseDate and NoiseCancelingSuport fields. 
		The ImageAssetIdSmall, ImageAssetIdMedium, ImageAssetIdLarge fields will be removed in Brand  table
		 */
		
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click “Add App or Device” link
//		4. Leave the Product Type panel as ''DTS:X Master''
//		5. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		6.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		appDeviceControl.click(DeviceEdit.SAVE);
//		7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		8. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		9. Click "Download Offline Database (UI)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_UI);
		appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_UI);
//		10. Open the offline databse  file by SQLite
//      11. Navigate to Product table in SQLite
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameUI(device_UUID));
//		VP: The Brand table only contains Id, Uuid, BrandId, Name, Description, Type, SubType, RleaseDate and NoiseCancelingSuport fields.  
        String[] columns  = {"Id", "Uuid", "Name"};
        Assert.assertTrue(ListUtil.isEquals(columns,  dbUtil.getAllColumnName("Select * from Brand")));
	}
	@Test
	public void TCMPG_5693_08(){
		Reporter.log("Verify that the offline database for tuning will include the Product, Asset, Device, DeviceFeaturedProduct, AudioRouteEnum and SampleRateEnum ( new ) tables.");
	/*
	 *  1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click “Add App or Device” link
		"4. Leave the Product Type panel as ''DTS:X Master''
		5. Fill valid value into all required fields
		6.  Click “Save” link
		7. Publish above device
		8.Refresh APO offline database
		9. Click "Download Offline Database (Tuning)" link
		10. Open the offline databse  file by SQLite
		VP: The offline database for tuning will include the Product, Asset, Device, DeviceFeaturedProduct, AudioRouteEnum and SampleRateEnum ( new ) tables.
	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click “Add App or Device” link
//		4. Leave the Product Type panel as ''DTS:X Master''
//		5. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		6.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		appDeviceControl.click(DeviceEdit.SAVE);
//		7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		8. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		9. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
		appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//		10. Open the offline databse  file by SQLite
//      VP:The offline database for tuning will include the Product, Asset, Device, DeviceFeaturedProduct, AudioRouteEnum and SampleRateEnum ( new ) tables.
       // ArrayList<String> uiTables= new ArrayList<String>();
        String[] uiTables= {"Product", "Asset", "Device", "DeviceFeaturedProduct", "AudioRouteEnum", "SampleRateEnum"	};
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
//        Assert.assertEquals(uiTables, dbUtil.getAllTables().toArray());
        Assert.assertTrue(ListUtil.isEquals(dbUtil.getAllTables(), uiTables));
	}
	@Test
	public void TCMPG_5693_09(){
		Reporter.log("Verify  that at the tuning db Product table contains all correctly fields");
	/*
	 *  1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click “Add App or Device” link
		"4. Leave the Product Type panel as ''DTS:X Master''
		5. Fill valid value into all required fields
		6.  Click “Save” link
		7. Publish above device
		8.Refresh APO offline database
		9. Click "Download Offline Database (Tuning)" link
		10. Open the offline databse  file by SQLite
		11. Navigate to Product table in SQLite
		VP: The Product table includes Id and Uuid fields.
	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click “Add App or Device” link
//		4. Leave the Product Type panel as ''DTS:X Master''
//		5. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		6.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
//		7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		8. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		9. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//		10. Open the offline databse  file by SQLite
//      11. Navigate to Product table in SQLite
//      VP:The Product table includes Id and Uuid fields.
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
        String[] columns  = {
        		"Id", "Uuid"
        };
        Assert.assertTrue(ListUtil.isEquals(columns,  dbUtil.getAllColumnName("Select * from Product")));
	}
	@Test
	public void TCMPG_5693_10(){
		Reporter.log("Verify that at the tuning db SampleRateEnum table is added");
	/*
	 *  1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click “Add App or Device” link
		"4. Leave the Product Type panel as ''DTS:X Master''
		5. Fill valid value into all required fields
		6.  Click “Save” link
		7. Publish above device
		8.Refresh APO offline database
		9. Click "Download Offline Database (Tuning)" link
		10. Open the offline databse  file by SQLite
		11. Navigate to SampleRateEnum table in SQLite
		VP: The SampleRateEnum table includes Id and value fields to store Sample Rate Info.
	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click “Add App or Device” link
//		4. Leave the Product Type panel as ''DTS:X Master''
//		5. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		6.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
//		7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		8. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		9. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//		10. Open the offline databse  file by SQLite
//        11. Navigate to SampleRateEnum table in SQLite
//      VP:TThe SampleRateEnum table includes Id and Value fields to store Sample Rate Info.
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
        String[] columns  = {"Id", "Value"};
        Assert.assertTrue(ListUtil.isEquals(columns,  dbUtil.getAllColumnName("Select * from SampleRateEnum ")));
	}
	@Test
	public void TCMPG_5693_11(){
		Reporter.log("Verify  that at the tuning db Asset table contains all correctly fields");
	/*
	 *  1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click “Add App or Device” link
		"4. Leave the Product Type panel as ''DTS:X Master''
		5. Fill valid value into all required fields
		6.  Click “Save” link
		7. Publish above device
		8.Refresh APO offline database
		9. Click "Download Offline Database (Tuning)" link
		10. Open the offline databse  file by SQLite
		11. Navigate to Asset table in SQLite
		VP: The Asset table contains Id, Uri, Data, Filesize, AudioRouteEnum, SampleRateEnum and Uuid fields ( the Type column is removed and  AudioRouteEnumId, SampleRateEnumId and Uuid columns are added)
	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click “Add App or Device” link
//		4. Leave the Product Type panel as ''DTS:X Master''
//		5. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		6.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
//		7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		8. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		9. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//		10. Open the offline databse  file by SQLite
//        11. Navigate to Asset table in SQLite
//      VP:The Asset table contains Id, Uri, Data, Filesize, AudioRouteEnum, SampleRateEnum and Uuid fields ( the Type column is removed and  AudioRouteEnumId, SampleRateEnumId and Uuid columns are added)
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
        String[] columns  = {"Id", "Uri", "Data", "Filesize", "AudioRouteEnum", "SampleRateEnum", "Uuid"};
        Assert.assertTrue(ListUtil.isEquals(columns,  dbUtil.getAllColumnName("Select * from Asset ")));
        
	}
	@Test
	public void TCMPG_5693_12(){
		Reporter.log("Verify that the Asset table only includes tuning data related to products.");
	/*
	 *  1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click “Add App or Device” link
		"4. Leave the Product Type panel as ''DTS:X Master''
		5. Fill valid value into all required fields
		6.  Click “Save” link
		7. Publish above device
		8.Refresh APO offline database
		9. Click "Download Offline Database (Tuning)" link
		10. Open the offline databse  file by SQLite
		11. Navigate to Asset table in SQLite
		VP: The at Asset table Uri column only includes tuning data related to products and no have any speaker tunings
	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click “Add App or Device” link
//		4. Leave the Product Type panel as ''DTS:X Master''
//		5. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		6.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
//		7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		8. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		9. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//		10. Open the offline databse  file by SQLite
//      11. Navigate to Asset table in SQLite
//      VP:The at Asset table Uri column only includes tuning data related to products and no have any speaker tunings
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
        Assert.assertTrue(dbUtil.getColumnData("Select * from Asset where Type Uri LIKE 'speaker%' ", "Uri", null).size()==0);
//        Assert.assertTrue(ListUtil.isEquals(columns,  dbUtil.getAllColumnName("Select * from Asset where Type Uri LIKE 'speaker%' ", "Uri", null)));
        
	}
	@Test
	public void TCMPG_5693_13(){
		Reporter.log("Verify  that at the tuning db Device table contains all correctly fields");
	/*
	 *  1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click “Add App or Device” link
		"4. Leave the Product Type panel as ''DTS:X Master''
		5. Fill valid value into all required fields
		6.  Click “Save” link
		7. Publish above device
		8.Refresh APO offline database
		9. Click "Download Offline Database (Tuning)" link
		10. Open the offline databse  file by SQLite
		11. Navigate to Device table in SQLite
		VP: The 'Device' table contains Id, Uuid, Name, ParentBrandId and InboxUid fields
	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click “Add App or Device” link
//		4. Leave the Product Type panel as ''DTS:X Master''
//		5. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		6.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
//		7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		8. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		9. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//		10. Open the offline databse  file by SQLite
//        11. Navigate to Device table in SQLite
//      VP:The Asset table contains Id, Uri, Data, Filesize, AudioRouteEnum, SampleRateEnum and Uuid fields ( the Type column is removed and  AudioRouteEnumId, SampleRateEnumId and Uuid columns are added)
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
        String[] columns  = { "Id", "Uuid", "Name", "ParentBrandId","InboxUuid"};
        Assert.assertTrue(ListUtil.isEquals(columns,  dbUtil.getAllColumnName("Select * from Device ")));
	}
	
	@Test
	public void TCMPG_5693_14(){
		Reporter.log("Verify that sample rates and audio routes are stored correctly as file names at Asset table when a Over-ear standard headphone supports all routes lineout, bluetooth, usb");
	/*
	 *  1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click “Add App or Device” link
		"4. Leave the Product Type panel as ''DTS:X Master''
		5. Fill valid value into all required fields
		6.  Click “Save” link
		7. Publish above device
		8.Refresh APO offline database
		9. Click "Download Offline Database (Tuning)" link
		10. Navigate to "Audio" tab
		11. Click on "Over-Ear Headphone" standard headphone link
		12. Get the Uuid of this headphone below
		13. Open the tuning offline database  file by SQLite
		14. Navigate to Assert table in SQLite
		VP:The Uri column at Asset table contains all file names:
			+ [uuid]_lineout_44k.bin
			+ [uuid]_lineout_48k.bin
			+ [uuid]_bluetooth_44k.bin
			+ [uuid]_bluetooth_48k.bin
			+ [uuid]_usb_44k.bin
			+ [uuid]_usb_48k.bin

	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click “Add App or Device” link
//		4. Leave the Product Type panel as ''DTS:X Master''
//		5. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		6.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
//		7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		8. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		9. Click "Download Offline Database (Tuning)" link
        appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
		appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//      10. Navigate to "Audio" tab
		audioControl.click(PageHome.linkAudioroutes);
		// VP: Verify that the audio list page is displayed
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.HEADER));
//		11. Click on "Over-Ear Headphone" standard headphone link
        audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());                
//		12. Get the Uuid of this headphone below
        String standard_hd_UUID = appDeviceControl.getTextByXpath(AudioRoutesInfo.UUID);
//		13. Open the tuning offline database  file by SQLite
//		14. Navigate to Assert table in SQLite
//		VP:The Uri column at Asset table contains all file names:
//			+ [uuid]_lineout_44k.bin
//			+ [uuid]_lineout_48k.bin
//			+ [uuid]_bluetooth_44k.bin
//			+ [uuid]_bluetooth_48k.bin
//			+ [uuid]_usb_44k.bin
//			+ [uuid]_usb_48k.bin
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
		String[] binFilesExpected  = { "profile_"+standard_hd_UUID+"_line-out_44k.bin", "profile_"+standard_hd_UUID+"_line-out_48k.bin", "profile_"+standard_hd_UUID+"_bluetooth_44k.bin", "profile_"+standard_hd_UUID+"_bluetooth_48k.bin","profile_"+standard_hd_UUID+"_usb_44k.bin", "profile_"+standard_hd_UUID+"_usb_48k.bin"};
        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset where Uri like '%"+standard_hd_UUID+"%'", "Uri", null);
        Assert.assertTrue(ListUtil.isEquals(binFilesActual, binFilesExpected)); 
        
       
	}
	@Test
	public void TCMPG_5693_15(){
		Reporter.log("Verify that sample rates and audio routes are stored correctly as file names at Asset table when a Earbuds standard headphone supports all routes lineout, bluetooth, usb");
	/*
	 *  1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click “Add App or Device” link
		"4. Leave the Product Type panel as ''DTS:X Master''
		5. Fill valid value into all required fields
		6.  Click “Save” link
		7. Publish above device
		8.Refresh APO offline database
		9. Click "Download Offline Database (Tuning)" link
		10. Navigate to "Audio" tab
		11. Click on "Earbuds Headphone" standard headphone link
		12. Get the Uuid of this headphone below
		13. Open the tuning offline database  file by SQLite
		14. Navigate to Assert table in SQLite
		VP:The Uri column at Asset table contains all file names:
			+ [uuid]_lineout_44k.bin
			+ [uuid]_lineout_48k.bin
			+ [uuid]_bluetooth_44k.bin
			+ [uuid]_bluetooth_48k.bin
			+ [uuid]_usb_44k.bin
			+ [uuid]_usb_48k.bin

	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click “Add App or Device” link
//		4. Leave the Product Type panel as ''DTS:X Master''
//		5. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		6.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
//		7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		8. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		9. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//      10. Navigate to "Audio" tab
        appDeviceControl.click(PageHome.linkAudioroutes);
//		11. Click on "Earbuds Headphone" standard headphone link
        audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Earbuds.getName());                
//		12. Get the Uuid of this headphone below
        String standard_hd_UUID = appDeviceControl.getTextByXpath(AudioRoutesInfo.UUID);
//		13. Open the tuning offline database  file by SQLite
//		14. Navigate to Assert table in SQLite
//		VP:The Uri column at Asset table contains all file names:
//			+ [uuid]_lineout_44k.bin
//			+ [uuid]_lineout_48k.bin
//			+ [uuid]_bluetooth_44k.bin
//			+ [uuid]_bluetooth_48k.bin
//			+ [uuid]_usb_44k.bin
//			+ [uuid]_usb_48k.bin
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
		String[] binFilesExpected  = { "profile_"+standard_hd_UUID+"_line-out_44k.bin", "profile_"+standard_hd_UUID+"_line-out_48k.bin", "profile_"+standard_hd_UUID+"_bluetooth_44k.bin", "profile_"+standard_hd_UUID+"_bluetooth_48k.bin","profile_"+standard_hd_UUID+"_usb_44k.bin", "profile_"+standard_hd_UUID+"_usb_48k.bin"};
        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset where Uri like '%"+standard_hd_UUID+"%'", "Uri", null);
        Assert.assertTrue(ListUtil.isEquals(binFilesActual, binFilesExpected)); 
	}
	@Test
	public void TCMPG_5693_16(){
		Reporter.log("Verify that sample rates and audio routes are stored correctly as file names at Asset table when a  Ear-Piece standard headphone supports all routes lineout, bluetooth, usb");
	/*
	 *  1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click “Add App or Device” link
		"4. Leave the Product Type panel as ''DTS:X Master''
		5. Fill valid value into all required fields
		6.  Click “Save” link
		7. Publish above device
		8.Refresh APO offline database
		9. Click "Download Offline Database (Tuning)" link
		10. Navigate to "Audio" tab
		11. Click on " Ear-Piece Headphone" standard headphone link
		12. Get the Uuid of this headphone below
		13. Open the tuning offline database  file by SQLite
		14. Navigate to Assert table in SQLite
		VP:The Uri column at Asset table contains all file names:
			+ [uuid]_lineout_44k.bin
			+ [uuid]_lineout_48k.bin
			+ [uuid]_bluetooth_44k.bin
			+ [uuid]_bluetooth_48k.bin
			+ [uuid]_usb_44k.bin
			+ [uuid]_usb_48k.bin

	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click “Add App or Device” link
//		4. Leave the Product Type panel as ''DTS:X Master''
//		5. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		6.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
//		7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		8. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		9. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//      10. Navigate to "Audio" tab
        appDeviceControl.click(PageHome.linkAudioroutes);
//		11. Click on " Ear-Piece Headphone" standard headphone link
        audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Ear_piece.getName());                
//		12. Get the Uuid of this headphone below
        String standard_hd_UUID = appDeviceControl.getTextByXpath(AudioRoutesInfo.UUID);
//		13. Open the tuning offline database  file by SQLite
//		14. Navigate to Assert table in SQLite
//		VP:The Uri column at Asset table contains all file names:
//			+ [uuid]_lineout_44k.bin
//			+ [uuid]_lineout_48k.bin
//			+ [uuid]_bluetooth_44k.bin
//			+ [uuid]_bluetooth_48k.bin
//			+ [uuid]_usb_44k.bin
//			+ [uuid]_usb_48k.bin
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
		String[] binFilesExpected  = { "profile_"+standard_hd_UUID+"_line-out_44k.bin", "profile_"+standard_hd_UUID+"_line-out_48k.bin", "profile_"+standard_hd_UUID+"_bluetooth_44k.bin", "profile_"+standard_hd_UUID+"_bluetooth_48k.bin","profile_"+standard_hd_UUID+"_usb_44k.bin", "profile_"+standard_hd_UUID+"_usb_48k.bin"};
        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset where Uri like '%"+standard_hd_UUID+"%'", "Uri", null);
        Assert.assertTrue(ListUtil.isEquals(binFilesActual, binFilesExpected)); 
	}
	@Test
	public void TCMPG_5693_17(){
		Reporter.log("Verify that sample rates and audio routes are stored correctly as file names at Asset table when a  External Speakers standard headphone supports all routes lineout, bluetooth, usb");
	/*
	 *  1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click “Add App or Device” link
		"4. Leave the Product Type panel as ''DTS:X Master''
		5. Fill valid value into all required fields
		6.  Click “Save” link
		7. Publish above device
		8.Refresh APO offline database
		9. Click "Download Offline Database (Tuning)" link
		10. Navigate to "Audio" tab
		11. Click on " External Speakers Headphone" standard headphone link
		12. Get the Uuid of this headphone below
		13. Open the tuning offline database  file by SQLite
		14. Navigate to Assert table in SQLite
		VP:The Uri column at Asset table contains all file names:
			+ [uuid]_lineout_44k.bin
			+ [uuid]_lineout_48k.bin
			+ [uuid]_bluetooth_44k.bin
			+ [uuid]_bluetooth_48k.bin
			+ [uuid]_usb_44k.bin
			+ [uuid]_usb_48k.bin

	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click “Add App or Device” link
//		4. Leave the Product Type panel as ''DTS:X Master''
//		5. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		6.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
//		7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		8. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		9. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//      10. Navigate to "Audio" tab
        appDeviceControl.click(PageHome.linkAudioroutes);
//		11. Click on " External Speakers Headphone" standard headphone link
        audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.External_Speakers.getName());                
//		12. Get the Uuid of this headphone below
        String standard_hd_UUID = appDeviceControl.getTextByXpath(AudioRoutesInfo.UUID);
//		13. Open the tuning offline database  file by SQLite
//		14. Navigate to Assert table in SQLite
//		VP:The Uri column at Asset table contains all file names:
//			+ [uuid]_lineout_44k.bin
//			+ [uuid]_lineout_48k.bin
//			+ [uuid]_bluetooth_44k.bin
//			+ [uuid]_bluetooth_48k.bin
//			+ [uuid]_usb_44k.bin
//			+ [uuid]_usb_48k.bin
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
		String[] binFilesExpected  = { "profile_"+standard_hd_UUID+"_line-out_44k.bin", "profile_"+standard_hd_UUID+"_line-out_48k.bin", "profile_"+standard_hd_UUID+"_bluetooth_44k.bin", "profile_"+standard_hd_UUID+"_bluetooth_48k.bin","profile_"+standard_hd_UUID+"_usb_44k.bin", "profile_"+standard_hd_UUID+"_usb_48k.bin"};
        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset where Uri like '%"+standard_hd_UUID+"%'", "Uri", null);
        Assert.assertTrue(ListUtil.isEquals(binFilesActual, binFilesExpected)); 
	}
	@Test
	public void TCMPG_5693_18(){
		Reporter.log("Verify that sample rates and audio routes are stored correctly as file names at Asset table when a  Car Audio standard headphone supports all routes lineout, bluetooth, usb");
	/*
	 *  1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click “Add App or Device” link
		"4. Leave the Product Type panel as ''DTS:X Master''
		5. Fill valid value into all required fields
		6.  Click “Save” link
		7. Publish above device
		8.Refresh APO offline database
		9. Click "Download Offline Database (Tuning)" link
		10. Navigate to "Audio" tab
		11. Click on " Car Audio Headphone" standard headphone link
		12. Get the Uuid of this headphone below
		13. Open the tuning offline database  file by SQLite
		14. Navigate to Assert table in SQLite
		VP:The Uri column at Asset table contains all file names:
			+ [uuid]_lineout_44k.bin
			+ [uuid]_lineout_48k.bin
			+ [uuid]_bluetooth_44k.bin
			+ [uuid]_bluetooth_48k.bin
			+ [uuid]_usb_44k.bin
			+ [uuid]_usb_48k.bin

	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click “Add App or Device” link
//		4. Leave the Product Type panel as ''DTS:X Master''
//		5. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		6.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
//		7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		8. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		9. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//      10. Navigate to "Audio" tab
        appDeviceControl.click(PageHome.linkAudioroutes);
//		11. Click on " Car Audios Headphone" standard headphone link
        audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Car_Audio.getName());                
//		12. Get the Uuid of this headphone below
        String standard_hd_UUID = appDeviceControl.getTextByXpath(AudioRoutesInfo.UUID);
//		13. Open the tuning offline database  file by SQLite
//		14. Navigate to Assert table in SQLite
//		VP:The Uri column at Asset table contains all file names:
//			+ [uuid]_lineout_44k.bin
//			+ [uuid]_lineout_48k.bin
//			+ [uuid]_bluetooth_44k.bin
//			+ [uuid]_bluetooth_48k.bin
//			+ [uuid]_usb_44k.bin
//			+ [uuid]_usb_48k.bin
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
		String[] binFilesExpected  = { "profile_"+standard_hd_UUID+"_line-out_44k.bin", "profile_"+standard_hd_UUID+"_line-out_48k.bin", "profile_"+standard_hd_UUID+"_bluetooth_44k.bin", "profile_"+standard_hd_UUID+"_bluetooth_48k.bin","profile_"+standard_hd_UUID+"_usb_44k.bin", "profile_"+standard_hd_UUID+"_usb_48k.bin"};
        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset where Uri like '%"+standard_hd_UUID+"%'", "Uri", null);
        Assert.assertTrue(ListUtil.isEquals(binFilesActual, binFilesExpected));
	}
	
	@Test
	public void TCMPG_5693_19(){
		Reporter.log("Verify that sample rates and audio routes are stored correctly as file names at Asset table when a branded headphone supports all routes lineout, bluetooth, usb");
	/*
	 *  1. Log into DTS portal as a DTS user
	 	2. Navigate to "Products" tab
		3. Create a product support  lineout, bluetooth, usb routes
		4. Publish and get the Uuid of this product
		5. Navigate to "Apps & Devices" page
		6. Click “Add App or Device” link
		"7. Leave the Product Type panel as ''DTS:X Master''
		8. Fill valid value into all required fields
		9.  Click “Save” link
		10. Publish above device
		11.Refresh APO offline database
		12. Click "Download Offline Database (Tuning)" link
		13. Open the tuning offline database  file by SQLite
		14. Navigate to Assert table in SQLite
		VP:The Uri column at Asset table contains all file names:
			+ [uuid]_lineout_44k.bin
			+ [uuid]_lineout_48k.bin
			+ [uuid]_bluetooth_44k.bin
			+ [uuid]_bluetooth_48k.bin
			+ [uuid]_usb_44k.bin
			+ [uuid]_usb_48k.bin

	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Products" tab
		productControl.click(PageHome.linkAccessories);
//		3. Create a product support  lineout, bluetooth, usb routes		
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct1 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		dataProduct1.put("bluetooth", "1 (Mono)");
		dataProduct1.put("usb", "1 (Mono)");
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct1);
//		4. Publish and get the Uuid of this product
		String hd_UUID= productControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
//		5. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		6. Click “Add App or Device” link
//		7. Leave the Product Type panel as ''DTS:X Master''
//		8. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		9.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
//		10. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		11. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		12. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//		13. Open the tuning offline database  file by SQLite
//		14. Navigate to Assert table in SQLite
//		VP:The Uri column at Asset table contains all file names:
//			+ [uuid]_lineout_44k.bin
//			+ [uuid]_lineout_48k.bin
//			+ [uuid]_bluetooth_44k.bin
//			+ [uuid]_bluetooth_48k.bin
//			+ [uuid]_usb_44k.bin
//			+ [uuid]_usb_48k.bin
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
        String[] binFilesExpected  = { "profile_"+hd_UUID+"_line-out_44k.bin", "profile_"+hd_UUID+"_line-out_48k.bin", "profile_"+hd_UUID+"_bluetooth_44k.bin", "profile_"+hd_UUID+"_bluetooth_48k.bin","profile_"+hd_UUID+"_usb_44k.bin", "profile_"+hd_UUID+"_usb_48k.bin"};
        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset where Uri LIKE '%"+hd_UUID+"%'", "Uri", null);
        Assert.assertTrue(ListUtil.isEquals(binFilesActual, binFilesExpected));

	}
	@Test
	public void TCMPG_5693_20(){
		Reporter.log("Verify that sample rates and audio routes are stored correctly as file names at Asset table when a branded headphone supports all routes lineout, bluetooth");
	/*
	 *  1. Log into DTS portal as a DTS user
	 	2. Navigate to "Products" tab
		3. Create a product support  lineout, bluetooth routes
		4. Publish and get the Uuid of this product
		5. Navigate to "Apps & Devices" page
		6. Click “Add App or Device” link
		"7. Leave the Product Type panel as ''DTS:X Master''
		8. Fill valid value into all required fields
		9.  Click “Save” link
		10. Publish above device
		11.Refresh APO offline database
		12. Click "Download Offline Database (Tuning)" link
		13. Open the tuning offline database  file by SQLite
		14. Navigate to Assert table in SQLite
		VP:The Uri column at Asset table contains all file names:
			+ [uuid]_lineout_44k.bin
			+ [uuid]_lineout_48k.bin
			+ [uuid]_bluetooth_44k.bin
			+ [uuid]_bluetooth_48k.bin

	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Products" tab
		productControl.click(PageHome.linkAccessories);
//		3. Create a product support  lineout, bluetooth routes		
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct1 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		dataProduct1.put("bluetooth", "1 (Mono)");
//		dataProduct1.put("usb", "1 (Mono)");
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct1);
//		4. Publish and get the Uuid of this product
		String hd_UUID= productControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
//		5. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		6. Click “Add App or Device” link
//		7. Leave the Product Type panel as ''DTS:X Master''
//		8. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		9.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
//		10. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		11. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		12. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//		13. Open the tuning offline database  file by SQLite
//		14. Navigate to Assert table in SQLite
//		VP:The Uri column at Asset table contains all file names:
//			+ [uuid]_lineout_44k.bin
//			+ [uuid]_lineout_48k.bin
//			+ [uuid]_bluetooth_44k.bin
//			+ [uuid]_bluetooth_48k.bin
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
        String[] binFilesExpected  = { "profile_"+hd_UUID+"_line-out_44k.bin", "profile_"+hd_UUID+"_line-out_48k.bin", "profile_"+hd_UUID+"_bluetooth_44k.bin", "profile_"+hd_UUID+"_bluetooth_48k.bin"};
        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset where Uri LIKE '%"+hd_UUID+"%'", "Uri", null);
        Assert.assertTrue(ListUtil.isEquals(binFilesActual, binFilesExpected));

	}
	@Test
	public void TCMPG_5693_21(){
		Reporter.log("Verify that sample rates and audio routes are stored correctly as file names at Asset table when a branded headphone supports all routes lineout, usb");
	/*
	 *  1. Log into DTS portal as a DTS user
	 	2. Navigate to "Products" tab
		3. Create a product support  lineout, usb routes
		4. Publish and get the Uuid of this product
		5. Navigate to "Apps & Devices" page
		6. Click “Add App or Device” link
		"7. Leave the Product Type panel as ''DTS:X Master''
		8. Fill valid value into all required fields
		9.  Click “Save” link
		10. Publish above device
		11.Refresh APO offline database
		12. Click "Download Offline Database (Tuning)" link
		13. Open the tuning offline database  file by SQLite
		14. Navigate to Assert table in SQLite
		VP:The Uri column at Asset table contains all file names:
			+ [uuid]_lineout_44k.bin
			+ [uuid]_lineout_48k.bin
			+ [uuid]_usb_44k.bin
			+ [uuid]_usb_48k.bin

	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Products" tab
		productControl.click(PageHome.linkAccessories);
//		3. Create a product support  lineout, usb routes		
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct1 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
//		dataProduct1.put("bluetooth", "1 (Mono)");
		dataProduct1.put("usb", "1 (Mono)");
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct1);
//		4. Publish and get the Uuid of this product
		String hd_UUID= productControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
//		5. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		6. Click “Add App or Device” link
//		7. Leave the Product Type panel as ''DTS:X Master''
//		8. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		9.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
//		10. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		11. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		12. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//		13. Open the tuning offline database  file by SQLite
//		14. Navigate to Assert table in SQLite
//		VP:The Uri column at Asset table contains all file names:
//			+ [uuid]_lineout_44k.bin
//			+ [uuid]_lineout_48k.bin
//			+ [uuid]_usb_44k.bin
//			+ [uuid]_usb_48k.bin
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
        String[] binFilesExpected  = { "profile_"+hd_UUID+"_line-out_44k.bin", "profile_"+hd_UUID+"_line-out_48k.bin", "profile_"+hd_UUID+"_usb_44k.bin", "profile_"+hd_UUID+"_usb_48k.bin"};
        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset where Uri LIKE '%"+hd_UUID+"%'", "Uri", null);
        Assert.assertTrue(ListUtil.isEquals(binFilesActual, binFilesExpected));

	}
	@Test
	public void TCMPG_5693_22(){
		Reporter.log("Verify that sample rates and audio routes are stored correctly as file names at Asset table when a branded headphone supports all routes bluetooth, usb");
	/*
	 *  1. Log into DTS portal as a DTS user
	 	2. Navigate to "Products" tab
		3. Create a product support  lineout, bluetooth, usb routes
		4. Publish and get the Uuid of this product
		5. Navigate to "Apps & Devices" page
		6. Click “Add App or Device” link
		"7. Leave the Product Type panel as ''DTS:X Master''
		8. Fill valid value into all required fields
		9.  Click “Save” link
		10. Publish above device
		11.Refresh APO offline database
		12. Click "Download Offline Database (Tuning)" link
		13. Open the tuning offline database  file by SQLite
		14. Navigate to Assert table in SQLite
		VP:The Uri column at Asset table contains all file names:
			+ [uuid]_bluetooth_44k.bin
			+ [uuid]_bluetooth_48k.bin
			+ [uuid]_usb_44k.bin
			+ [uuid]_usb_48k.bin

	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Products" tab
		productControl.click(PageHome.linkAccessories);
//		3. Create a product support  lineout, bluetooth, usb routes		
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct1 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		dataProduct1.remove("wired");
		dataProduct1.put("bluetooth", "1 (Mono)");
		dataProduct1.put("usb", "1 (Mono)");
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct1);
//		4. Publish and get the Uuid of this product
		String hd_UUID= productControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
//		5. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		6. Click “Add App or Device” link
//		7. Leave the Product Type panel as ''DTS:X Master''
//		8. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		9.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
//		10. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		11. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		12. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//		13. Open the tuning offline database  file by SQLite
//		14. Navigate to Assert table in SQLite
//		VP:The Uri column at Asset table contains all file names:
//			+ [uuid]_bluetooth_44k.bin
//			+ [uuid]_bluetooth_48k.bin
//			+ [uuid]_usb_44k.bin
//			+ [uuid]_usb_48k.bin
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
        String[] binFilesExpected  = {"profile_"+hd_UUID+"_bluetooth_44k.bin", "profile_"+hd_UUID+"_bluetooth_48k.bin","profile_"+hd_UUID+"_usb_44k.bin", "profile_"+hd_UUID+"_usb_48k.bin"};
        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset where Uri LIKE '%"+hd_UUID+"%'", "Uri", null);
        Assert.assertTrue(ListUtil.isEquals(binFilesActual, binFilesExpected));

	}
	@Test
	public void TCMPG_5693_23(){
		Reporter.log("Verify that sample rates and audio routes are stored correctly as file names at Asset table when a Feature headphone supports all routes lineout, bluetooth, usb");
	/*
	 *  1. Log into DTS portal as a DTS user
	 	2. Navigate to "Products" tab
		3. Create a product support  lineout, bluetooth, usb routes
		4. Publish and get the Uuid of this product
		5. Navigate to "Apps & Devices" page
		6. Click “Add App or Device” link
		"7. Leave the Product Type panel as ''DTS:X Master''
		8. Fill valid value into all required fields
		9.  Click “Save” link
		10. Publish above device
		11.Refresh APO offline database
		12. Click "Download Offline Database (Tuning)" link
		13. Open the tuning offline database  file by SQLite
		14. Navigate to Assert table in SQLite
		VP:The Uri column at Asset table contains all file names:
			+ [uuid]_lineout_44k.bin
			+ [uuid]_lineout_48k.bin
			+ [uuid]_bluetooth_44k.bin
			+ [uuid]_bluetooth_48k.bin
			+ [uuid]_usb_44k.bin
			+ [uuid]_usb_48k.bin

	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Products" tab
		productControl.click(PageHome.linkAccessories);
//		3. Create a product support  lineout, bluetooth, usb routes		
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct1 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		dataProduct1.put("bluetooth", "1 (Mono)");
		dataProduct1.put("usb", "1 (Mono)");
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct1);
//		4. Publish and get the Uuid of this product
		String hd_UUID= productControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
//		5. Create and publish a Promotion for the Product
		appDeviceControl.click(PageHome.linkPromotions);
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create Global promotion
		Hashtable<String,String> dataGlobalPromotion = TestData.promotionGlobal(dataProduct1.get("brand") + " " + dataProduct1.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataGlobalPromotion);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		String DTS_ID = appDeviceControl.getTextByXpath(PromotionInfo.DTS_ID);
		System.out.println("DTS_ID: " + DTS_ID);
//		6. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		7. Click “Add App or Device” link
//		8. Leave the Product Type panel as ''DTS:X Master''
//		9. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		10.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDevice.put("global promotion", "ON");
		appDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
		appDeviceControl.editData(DeviceEdit.PROMO_SLOT,"25");
		appDeviceControl.editData(DeviceEdit.PROMOTION_SLOT_1, DTS_ID);
		appDeviceControl.click(DeviceEdit.SAVE);
//		11. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		12. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		13. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//		14. Open the tuning offline database  file by SQLite
//		15. Navigate to Assert table in SQLite
//		VP:The Uri column at Asset table contains all file names:
//			+ [uuid]_lineout_44k.bin
//			+ [uuid]_lineout_48k.bin
//			+ [uuid]_bluetooth_44k.bin
//			+ [uuid]_bluetooth_48k.bin
//			+ [uuid]_usb_44k.bin
//			+ [uuid]_usb_48k.bin
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
        String[] binFilesExpected  = { "profile_"+hd_UUID+"_line-out_44k.bin", "profile_"+hd_UUID+"_line-out_48k.bin", "profile_"+hd_UUID+"_bluetooth_44k.bin", "profile_"+hd_UUID+"_bluetooth_48k.bin","profile_"+hd_UUID+"_usb_44k.bin", "profile_"+hd_UUID+"_usb_48k.bin"};
        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset where Uri LIKE '%"+hd_UUID+"%'", "Uri", null);
        Assert.assertTrue(ListUtil.isEquals(binFilesActual, binFilesExpected));

	}
	
	@Test
	public void TCMPG_5693_24(){
		Reporter.log("Verify that sample rates and audio routes are stored correctly as file names at Asset table when a Feature headphone supports all routes lineout, bluetooth");
	/*
	 *  1. Log into DTS portal as a DTS user
	 	2. Navigate to "Products" tab
		3. Create a product support  lineout, bluetooth routes
		4. Publish and get the Uuid of this product
		5. Navigate to "Apps & Devices" page
		6. Click “Add App or Device” link
		"7. Leave the Product Type panel as ''DTS:X Master''
		8. Fill valid value into all required fields
		9.  Click “Save” link
		10. Publish above device
		11.Refresh APO offline database
		12. Click "Download Offline Database (Tuning)" link
		13. Open the tuning offline database  file by SQLite
		14. Navigate to Assert table in SQLite
		VP:The Uri column at Asset table contains all file names:
			+ [uuid]_lineout_44k.bin
			+ [uuid]_lineout_48k.bin
			+ [uuid]_bluetooth_44k.bin
			+ [uuid]_bluetooth_48k.bin
			+ [uuid]_usb_44k.bin
			+ [uuid]_usb_48k.bin

	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Products" tab
		productControl.click(PageHome.linkAccessories);
//		3. Create a product support  lineout, bluetooth, usb routes		
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct1 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		dataProduct1.put("bluetooth", "1 (Mono)");
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct1);
//		4. Publish and get the Uuid of this product
		String hd_UUID= productControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
//		5. Create and publish a Promotion for the Product
		appDeviceControl.click(PageHome.linkPromotions);
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create Global promotion
		Hashtable<String,String> dataGlobalPromotion = TestData.promotionGlobal(dataProduct1.get("brand") + " " + dataProduct1.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataGlobalPromotion);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		String DTS_ID = appDeviceControl.getTextByXpath(PromotionInfo.DTS_ID);
		System.out.println("DTS_ID: " + DTS_ID);
//		6. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		7. Click “Add App or Device” link
//		8. Leave the Product Type panel as ''DTS:X Master''
//		9. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		10.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDevice.put("global promotion", "ON");
		appDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
		appDeviceControl.editData(DeviceEdit.PROMO_SLOT,"25");
		appDeviceControl.editData(DeviceEdit.PROMOTION_SLOT_1, DTS_ID);
		appDeviceControl.click(DeviceEdit.SAVE);
//		11. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		12. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		13. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//		14. Open the tuning offline database  file by SQLite
//		15. Navigate to Assert table in SQLite
//		VP:The Uri column at Asset table contains all file names:
//			+ [uuid]_lineout_44k.bin
//			+ [uuid]_lineout_48k.bin
//			+ [uuid]_bluetooth_44k.bin
//			+ [uuid]_bluetooth_48k.bin
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
        String[] binFilesExpected  = { "profile_"+hd_UUID+"_line-out_44k.bin", "profile_"+hd_UUID+"_line-out_48k.bin", "profile_"+hd_UUID+"_bluetooth_44k.bin", "profile_"+hd_UUID+"_bluetooth_48k.bin"};
        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset where Uri LIKE '%"+hd_UUID+"%'", "Uri", null);
        Assert.assertTrue(ListUtil.isEquals(binFilesActual, binFilesExpected));

	}
	@Test
	public void TCMPG_5693_25(){
		Reporter.log("Verify that sample rates and audio routes are stored correctly as file names at Asset table when a Feature headphone supports all routes lineout, usb");
	/*
	 *  1. Log into DTS portal as a DTS user
	 	2. Navigate to "Products" tab
		3. Create a product support  lineout usb routes
		4. Publish and get the Uuid of this product
		5. Navigate to "Apps & Devices" page
		6. Click “Add App or Device” link
		"7. Leave the Product Type panel as ''DTS:X Master''
		8. Fill valid value into all required fields
		9.  Click “Save” link
		10. Publish above device
		11.Refresh APO offline database
		12. Click "Download Offline Database (Tuning)" link
		13. Open the tuning offline database  file by SQLite
		14. Navigate to Assert table in SQLite
		VP:The Uri column at Asset table contains all file names:
			+ [uuid]_lineout_44k.bin
			+ [uuid]_lineout_48k.bin
			+ [uuid]_bluetooth_44k.bin
			+ [uuid]_bluetooth_48k.bin
			+ [uuid]_usb_44k.bin
			+ [uuid]_usb_48k.bin

	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Products" tab
		productControl.click(PageHome.linkAccessories);
//		3. Create a product support  lineout, bluetooth, usb routes		
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct1 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		dataProduct1.put("usb", "1 (Mono)");
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct1);
//		4. Publish and get the Uuid of this product
		String hd_UUID= productControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
//		5. Create and publish a Promotion for the Product
		appDeviceControl.click(PageHome.linkPromotions);
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create Global promotion
		Hashtable<String,String> dataGlobalPromotion = TestData.promotionGlobal(dataProduct1.get("brand") + " " + dataProduct1.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataGlobalPromotion);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		String DTS_ID = appDeviceControl.getTextByXpath(PromotionInfo.DTS_ID);
		System.out.println("DTS_ID: " + DTS_ID);
//		6. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		7. Click “Add App or Device” link
//		8. Leave the Product Type panel as ''DTS:X Master''
//		9. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		10.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDevice.put("global promotion", "ON");
		appDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
		appDeviceControl.editData(DeviceEdit.PROMO_SLOT,"25");
		appDeviceControl.editData(DeviceEdit.PROMOTION_SLOT_1, DTS_ID);
		appDeviceControl.click(DeviceEdit.SAVE);
//		11. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		12. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		13. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//		14. Open the tuning offline database  file by SQLite
//		15. Navigate to Assert table in SQLite
//		VP:The Uri column at Asset table contains all file names:
//			+ [uuid]_lineout_44k.bin
//			+ [uuid]_lineout_48k.bin
//			+ [uuid]_bluetooth_44k.bin
//			+ [uuid]_bluetooth_48k.bin
//			+ [uuid]_usb_44k.bin
//			+ [uuid]_usb_48k.bin
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
        String[] binFilesExpected  = { "profile_"+hd_UUID+"_line-out_44k.bin", "profile_"+hd_UUID+"_line-out_48k.bin","profile_"+hd_UUID+"_usb_44k.bin", "profile_"+hd_UUID+"_usb_48k.bin"};
        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset where Uri LIKE '%"+hd_UUID+"%'", "Uri", null);
        Assert.assertTrue(ListUtil.isEquals(binFilesActual, binFilesExpected));

	}
	@Test
	public void TCMPG_5693_26(){
		Reporter.log("Verify that sample rates and audio routes are stored correctly as file names at Asset table when a Feature headphone supports all routes  bluetooth, usb");
	/*
	 *  1. Log into DTS portal as a DTS user
	 	2. Navigate to "Products" tab
		3. Create a product support   bluetooth, usb routes
		4. Publish and get the Uuid of this product
		5. Navigate to "Apps & Devices" page
		6. Click “Add App or Device” link
		"7. Leave the Product Type panel as ''DTS:X Master''
		8. Fill valid value into all required fields
		9.  Click “Save” link
		10. Publish above device
		11.Refresh APO offline database
		12. Click "Download Offline Database (Tuning)" link
		13. Open the tuning offline database  file by SQLite
		14. Navigate to Assert table in SQLite
		VP:The Uri column at Asset table contains all file names:
			+ [uuid]_bluetooth_44k.bin
			+ [uuid]_bluetooth_48k.bin
			+ [uuid]_usb_44k.bin
			+ [uuid]_usb_48k.bin

	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Products" tab
		productControl.click(PageHome.linkAccessories);
//		3. Create a product support  lineout, bluetooth, usb routes		
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct1 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		dataProduct1.put("bluetooth", "1 (Mono)");
		dataProduct1.put("usb", "1 (Mono)");
		dataProduct1.remove("wired");
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct1);
//		4. Publish and get the Uuid of this product
		String hd_UUID= productControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
//		5. Create and publish a Promotion for the Product
		appDeviceControl.click(PageHome.linkPromotions);
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create Global promotion
		Hashtable<String,String> dataGlobalPromotion = TestData.promotionGlobal(dataProduct1.get("brand") + " " + dataProduct1.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataGlobalPromotion);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		String DTS_ID = appDeviceControl.getTextByXpath(PromotionInfo.DTS_ID);
		System.out.println("DTS_ID: " + DTS_ID);
//		6. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		7. Click “Add App or Device” link
//		8. Leave the Product Type panel as ''DTS:X Master''
//		9. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		10.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDevice.put("global promotion", "ON");
		appDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
		appDeviceControl.editData(DeviceEdit.PROMO_SLOT,"25");
		appDeviceControl.editData(DeviceEdit.PROMOTION_SLOT_1, DTS_ID);
		appDeviceControl.click(DeviceEdit.SAVE);
//		11. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		12. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		13. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//		14. Open the tuning offline database  file by SQLite
//		15. Navigate to Assert table in SQLite
//		VP:The Uri column at Asset table contains all file names:
//			+ [uuid]_bluetooth_44k.bin
//			+ [uuid]_bluetooth_48k.bin
//			+ [uuid]_usb_44k.bin
//			+ [uuid]_usb_48k.bin
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
        String[] binFilesExpected  = {  "profile_"+hd_UUID+"_bluetooth_44k.bin", "profile_"+hd_UUID+"_bluetooth_48k.bin","profile_"+hd_UUID+"_usb_44k.bin", "profile_"+hd_UUID+"_usb_48k.bin"};
        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset where Uri LIKE '%"+hd_UUID+"%'", "Uri", null);
        Assert.assertTrue(ListUtil.isEquals(binFilesActual, binFilesExpected));

	}
	@Test
	public void TCMPG_5693_27(){
		Reporter.log("Verify that sample rates and audio routes are stored correctly as file names at Asset table when a Inbox headphone supports all routes lineout, bluetooth, usb");
	/*
	 *  1. Log into DTS portal as a DTS user
	 	2. Navigate to "Products" tab
		3. Create a product support  lineout, bluetooth, usb routes
		4. Publish and get the Uuid of this product
		5. Navigate to "Apps & Devices" page
		6. Click “Add App or Device” link
		"7. Leave the Product Type panel as ''DTS:X Master''
		8.Fill valid value into all required fields and add the product at step 3 as Inbox headphone
		9.  Click “Save” link
		10. Publish above device
		11.Refresh APO offline database
		12. Click "Download Offline Database (Tuning)" link
		13. Open the tuning offline database  file by SQLite
		14. Navigate to Assert table in SQLite
		VP:The Uri column at Asset table contains all file names:
			+ [uuid]_lineout_44k.bin
			+ [uuid]_lineout_48k.bin
			+ [uuid]_bluetooth_44k.bin
			+ [uuid]_bluetooth_48k.bin
			+ [uuid]_usb_44k.bin
			+ [uuid]_usb_48k.bin

	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Products" tab
		productControl.click(PageHome.linkAccessories);
//		3. Create a product support  lineout, bluetooth, usb routes		
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct1 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		dataProduct1.put("bluetooth", "1 (Mono)");
		dataProduct1.put("usb", "1 (Mono)");
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct1);
//		4. Publish and get the Uuid of this product
		String hd_UUID= productControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
//		5. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		6. Click “Add App or Device” link
//		7. Leave the Product Type panel as ''DTS:X Master''
//		8. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		9.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
		appDeviceControl.inputInboxHP(DeviceEdit.INBOX_FIELD, dataProduct1.get("name"));
		appDeviceControl.click(DeviceEdit.SAVE);
//		10. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		11. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		12. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//		13. Open the tuning offline database  file by SQLite
//		14. Navigate to Assert table in SQLite
//		VP:The Uri column at Asset table contains all file names:
//			+ [uuid]_lineout_44k.bin
//			+ [uuid]_lineout_48k.bin
//			+ [uuid]_bluetooth_44k.bin
//			+ [uuid]_bluetooth_48k.bin
//			+ [uuid]_usb_44k.bin
//			+ [uuid]_usb_48k.bin
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
        String[] binFilesExpected  = { "profile_"+hd_UUID+"_line-out_44k.bin", "profile_"+hd_UUID+"_line-out_48k.bin", "profile_"+hd_UUID+"_bluetooth_44k.bin", "profile_"+hd_UUID+"_bluetooth_48k.bin","profile_"+hd_UUID+"_usb_44k.bin", "profile_"+hd_UUID+"_usb_48k.bin"};
        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset where Uri LIKE '%"+hd_UUID+"%'", "Uri", null);
        Assert.assertTrue(ListUtil.isEquals(binFilesActual, binFilesExpected));

	}
	@Test
	public void TCMPG_5693_28(){
		Reporter.log("Verify that sample rates and audio routes are stored correctly as file names at Asset table when a Inbox headphone supports all routes lineout, bluetooth");
	/*
	 *  1. Log into DTS portal as a DTS user
	 	2. Navigate to "Products" tab
		3. Create a product support  lineout, bluetooth routes
		4. Publish and get the Uuid of this product
		5. Navigate to "Apps & Devices" page
		6. Click “Add App or Device” link
		"7. Leave the Product Type panel as ''DTS:X Master''
		8.Fill valid value into all required fields and add the product at step 3 as Inbox headphone
		9.  Click “Save” link
		10. Publish above device
		11.Refresh APO offline database
		12. Click "Download Offline Database (Tuning)" link
		13. Open the tuning offline database  file by SQLite
		14. Navigate to Assert table in SQLite
		VP:The Uri column at Asset table contains all file names:
			+ [uuid]_lineout_44k.bin
			+ [uuid]_lineout_48k.bin
			+ [uuid]_bluetooth_44k.bin
			+ [uuid]_bluetooth_48k.bin

	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Products" tab
		productControl.click(PageHome.linkAccessories);
//		3. Create a product support  lineout, bluetooth, usb routes		
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct1 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		dataProduct1.put("bluetooth", "1 (Mono)");
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct1);
//		4. Publish and get the Uuid of this product
		String hd_UUID= productControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
//		5. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		6. Click “Add App or Device” link
//		7. Leave the Product Type panel as ''DTS:X Master''
//		8. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		9.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
		appDeviceControl.inputInboxHP(DeviceEdit.INBOX_FIELD, dataProduct1.get("name"));
		appDeviceControl.click(DeviceEdit.SAVE);
//		10. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		11. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		12. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//		13. Open the tuning offline database  file by SQLite
//		14. Navigate to Assert table in SQLite
//		VP:The Uri column at Asset table contains all file names:
//			+ [uuid]_lineout_44k.bin
//			+ [uuid]_lineout_48k.bin
//			+ [uuid]_bluetooth_44k.bin
//			+ [uuid]_bluetooth_48k.bin
//			+ [uuid]_usb_44k.bin
//			+ [uuid]_usb_48k.bin
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
        String[] binFilesExpected  = { "profile_"+hd_UUID+"_line-out_44k.bin", "profile_"+hd_UUID+"_line-out_48k.bin", "profile_"+hd_UUID+"_bluetooth_44k.bin", "profile_"+hd_UUID+"_bluetooth_48k.bin"};
        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset where Uri LIKE '%"+hd_UUID+"%'", "Uri", null);
        Assert.assertTrue(ListUtil.isEquals(binFilesActual, binFilesExpected));

	}
	@Test
	public void TCMPG_5693_29(){
		Reporter.log("Verify that sample rates and audio routes are stored correctly as file names at Asset table when a Inbox headphone supports all routes lineout, bluetooth, usb");
	/*
	 *  1. Log into DTS portal as a DTS user
	 	2. Navigate to "Products" tab
		3. Create a product support  lineout, usb routes
		4. Publish and get the Uuid of this product
		5. Navigate to "Apps & Devices" page
		6. Click “Add App or Device” link
		"7. Leave the Product Type panel as ''DTS:X Master''
		8.Fill valid value into all required fields and add the product at step 3 as Inbox headphone
		9.  Click “Save” link
		10. Publish above device
		11.Refresh APO offline database
		12. Click "Download Offline Database (Tuning)" link
		13. Open the tuning offline database  file by SQLite
		14. Navigate to Assert table in SQLite
		VP:The Uri column at Asset table contains all file names:
			+ [uuid]_lineout_44k.bin
			+ [uuid]_lineout_48k.bin
			+ [uuid]_usb_44k.bin
			+ [uuid]_usb_48k.bin

	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Products" tab
		productControl.click(PageHome.linkAccessories);
//		3. Create a product support  lineout, bluetooth, usb routes		
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct1 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		dataProduct1.put("usb", "1 (Mono)");
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct1);
//		4. Publish and get the Uuid of this product
		String hd_UUID= productControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
//		5. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		6. Click “Add App or Device” link
//		7. Leave the Product Type panel as ''DTS:X Master''
//		8. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		9.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
		appDeviceControl.inputInboxHP(DeviceEdit.INBOX_FIELD, dataProduct1.get("name"));
		appDeviceControl.click(DeviceEdit.SAVE);
//		10. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		11. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		12. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//		13. Open the tuning offline database  file by SQLite
//		14. Navigate to Assert table in SQLite
//		VP:The Uri column at Asset table contains all file names:
//			+ [uuid]_lineout_44k.bin
//			+ [uuid]_lineout_48k.bin
//			+ [uuid]_usb_44k.bin
//			+ [uuid]_usb_48k.bin
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
        String[] binFilesExpected  = { "profile_"+hd_UUID+"_line-out_44k.bin", "profile_"+hd_UUID+"_line-out_48k.bin","profile_"+hd_UUID+"_usb_44k.bin", "profile_"+hd_UUID+"_usb_48k.bin"};
        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset where Uri LIKE '%"+hd_UUID+"%'", "Uri", null);
        Assert.assertTrue(ListUtil.isEquals(binFilesActual, binFilesExpected));

	}
	@Test
	public void TCMPG_5693_30(){
		Reporter.log("Verify that sample rates and audio routes are stored correctly as file names at Asset table when a Inbox headphone supports all routes  bluetooth, usb");
	/*
	 *  1. Log into DTS portal as a DTS user
	 	2. Navigate to "Products" tab
		3. Create a product support  bluetooth, usb routes
		4. Publish and get the Uuid of this product
		5. Navigate to "Apps & Devices" page
		6. Click “Add App or Device” link
		"7. Leave the Product Type panel as ''DTS:X Master''
		8.Fill valid value into all required fields and add the product at step 3 as Inbox headphone
		9.  Click “Save” link
		10. Publish above device
		11.Refresh APO offline database
		12. Click "Download Offline Database (Tuning)" link
		13. Open the tuning offline database  file by SQLite
		14. Navigate to Assert table in SQLite
		VP:The Uri column at Asset table contains all file names:
			+ [uuid]_bluetooth_44k.bin
			+ [uuid]_bluetooth_48k.bin
			+ [uuid]_usb_44k.bin
			+ [uuid]_usb_48k.bin

	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Products" tab
		productControl.click(PageHome.linkAccessories);
//		3. Create a product support bluetooth, usb routes		
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct1 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		dataProduct1.put("bluetooth", "1 (Mono)");
		dataProduct1.put("usb", "1 (Mono)");
		dataProduct1.remove("wired");
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct1);
//		4. Publish and get the Uuid of this product
		String hd_UUID= productControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
//		5. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		6. Click “Add App or Device” link
//		7. Leave the Product Type panel as ''DTS:X Master''
//		8. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		9.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
		appDeviceControl.inputInboxHP(DeviceEdit.INBOX_FIELD, dataProduct1.get("name"));
		appDeviceControl.click(DeviceEdit.SAVE);
//		10. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		11. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		12. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//		13. Open the tuning offline database  file by SQLite
//		14. Navigate to Assert table in SQLite
//		VP:The Uri column at Asset table contains all file names:
//			+ [uuid]_bluetooth_44k.bin
//			+ [uuid]_bluetooth_48k.bin
//			+ [uuid]_usb_44k.bin
//			+ [uuid]_usb_48k.bin
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
        String[] binFilesExpected  = { "profile_"+hd_UUID+"_bluetooth_44k.bin", "profile_"+hd_UUID+"_bluetooth_48k.bin","profile_"+hd_UUID+"_usb_44k.bin", "profile_"+hd_UUID+"_usb_48k.bin"};
        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset where Uri LIKE '%"+hd_UUID+"%'", "Uri", null);
        Assert.assertTrue(ListUtil.isEquals(binFilesActual, binFilesExpected));
	}
	@Test
	public void TCMPG_5693_31(){
		Reporter.log("Verify that eagle data of standard headphones are stored on tuning db match up with complete profile tunings of them");
	/*
	 * 1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click “Add App or Device” link
		4. Leave the Product Type panel as ''DTS:X Master''
		5. Fill valid value into all required fields
		6. Click “Save” link
		7. Publish above device
		8. Refresh APO offline database
		9. Click "Download Offline Database (Tuning)" link
		10. Navigate to "Audio" tab
		11. Click on "Over-Ear Headphone" standard headphone link
		12. Download complete profile and convert to txt file
		13. Get the Uuid of this headphone below
		14. Open the tuning offline database  file by SQLite and select Assert table
		15. Search Uuid at step 13 on colume Uuid column 
		16. Using conversion tool to convert all tuning file on step 15 to txt file
		17. Compare data tuning of each tuning on step 16 with the complete profile on portal at step12
		VP: Eagle data of standard headphones are stored on tuning db match up with complete profile tunings 
	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click “Add App or Device” link
//		4. Leave the Product Type panel as ''DTS:X Master''
//		5. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		6.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
//		7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		8. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		9. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//      10. Navigate to "Audio" tab
		audioControl.click(PageHome.linkAudioroutes);
		// VP: Verify that the audio list page is displayed
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.HEADER));
//		11. Click on "Over-Ear Headphone" standard headphone link
        audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());    
//      12. Download complete profile and convert to txt file
//        appDeviceControl.downloadFile(AudioRoutesInfo.COMPLETE_PROFILE);
//		13. Get the Uuid of this headphone below
        String standard_hd_UUID = appDeviceControl.getTextByXpath(AudioRoutesInfo.UUID);
//		14. Open the tuning offline database  file by SQLite and Navigate to Assert table in SQLite
//		VP:The Uri column at Asset table contains all file names:
//			+ [uuid]_lineout_44k.bin
//			+ [uuid]_lineout_48k.bin
//			+ [uuid]_bluetooth_44k.bin
//			+ [uuid]_bluetooth_48k.bin
//			+ [uuid]_usb_44k.bin
//			+ [uuid]_usb_48k.bin
//      15. Search Uuid at step 13 on colume Uuid column 
//		16. Using conversion tool to convert all tuning file on step 15 to txt file
//		17. Compare data tuning of each tuning on step 16 with the complete profile on portal at step12        
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
		String[] binFilesExpected  = { "profile_"+standard_hd_UUID+"_line-out_44k.bin", "profile_"+standard_hd_UUID+"_line-out_48k.bin", "profile_"+standard_hd_UUID+"_bluetooth_44k.bin", "profile_"+standard_hd_UUID+"_bluetooth_48k.bin","profile_"+standard_hd_UUID+"_usb_44k.bin", "profile_"+standard_hd_UUID+"_usb_48k.bin"};
        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset where Uri like '%"+standard_hd_UUID+"%'", "Uri", null);
        Assert.assertTrue(ListUtil.isEquals(binFilesActual, binFilesExpected)); 
        for (int i = 0; i < binFilesActual.size(); i++) {
        	System.out.println(binFilesActual.get(i));
        	 Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBNameTuning(device_UUID), "select Data from Asset where Uri = '"+binFilesActual.get(i)+"'","Data", device_UUID));	
		}

	}
	
	@Test
	public void TCMPG_5693_32(){
		Reporter.log("Verify that eagle data of feature headphones are stored on tuning db match up with complete profile tunings of them");
	/*
	 *  1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click “Add App or Device” link
		4. Leave the Product Type panel as ''DTS:X Master''
		5. Fill valid value into all required fields
		6.  Click “Save” link
		7. Publish above device
		8.Refresh APO offline database
		9. Click "Download Offline Database (Tuning)" link
		10. Click on a feature headphone 
		11. Click on the product link of the promotion
		12. Download complete profile of product  and convert to txt file
		13. Get the Uuid of this headphone below
		14. Open the tuning offline database  file by SQLite and select Assert table
		15. Search Uuid at step 13 on colume Uuid column 
		16. Using conversion tool to convert all tuning file on step 15 to txt file
		17. Compare data tuning of each tuning on step 16 with the complete profile on portal at step12
		VP: Eagle data of feature headphones are stored on tuning db match up with complete profile tunings 	

	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Products" tab
		productControl.click(PageHome.linkAccessories);
//		3. Create a product support  lineout, bluetooth, usb routes		
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct1 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		dataProduct1.put("bluetooth", "1 (Mono)");
		dataProduct1.put("usb", "1 (Mono)");
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct1);
//		4. Publish and get the Uuid of this product
		String hd_UUID= productControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
//		5. Create and publish a Promotion for the Product
		appDeviceControl.click(PageHome.linkPromotions);
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create Global promotion
		Hashtable<String,String> dataGlobalPromotion = TestData.promotionGlobal(dataProduct1.get("brand") + " " + dataProduct1.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataGlobalPromotion);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		String DTS_ID = appDeviceControl.getTextByXpath(PromotionInfo.DTS_ID);
		System.out.println("DTS_ID: " + DTS_ID);
//		6. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		7. Click “Add App or Device” link
//		8. Leave the Product Type panel as ''DTS:X Master''
//		9. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		10.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDevice.put("global promotion", "ON");
		appDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
		appDeviceControl.editData(DeviceEdit.PROMO_SLOT,"25");
		appDeviceControl.editData(DeviceEdit.PROMOTION_SLOT_1, DTS_ID);
		appDeviceControl.click(DeviceEdit.SAVE);
//		11. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		12. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		13. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//		14. Open the tuning offline database  file by SQLite
//		15. Navigate to Assert table in SQLite
//		VP:The Uri column at Asset table contains all file names:
//			+ [uuid]_lineout_44k.bin
//			+ [uuid]_lineout_48k.bin
//			+ [uuid]_bluetooth_44k.bin
//			+ [uuid]_bluetooth_48k.bin
//			+ [uuid]_usb_44k.bin
//			+ [uuid]_usb_48k.bin
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
        String[] binFilesExpected  = { "profile_"+hd_UUID+"_line-out_44k.bin", "profile_"+hd_UUID+"_line-out_48k.bin", "profile_"+hd_UUID+"_bluetooth_44k.bin", "profile_"+hd_UUID+"_bluetooth_48k.bin","profile_"+hd_UUID+"_usb_44k.bin", "profile_"+hd_UUID+"_usb_48k.bin"};
        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset where Uri LIKE '%"+hd_UUID+"%'", "Uri", null);
        Assert.assertTrue(ListUtil.isEquals(binFilesActual, binFilesExpected));
      // Just verify that the tuning data can decrypt 
        for (int i = 0; i < binFilesActual.size(); i++) {
        	System.out.println(binFilesActual.get(i));
        	 Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBNameTuning(device_UUID), "select Data from Asset where Uri = '"+binFilesActual.get(i)+"'","Data", device_UUID));	
		}
	}
	@Test
	public void TCMPG_5693_33(){
		Reporter.log("Verify that eagle data of brand headphones are stored on tuning db match up with complete profile tunings of them");
	/*
	 * 1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click “Add App or Device” link
		4. Leave the Product Type panel as ''DTS:X Master''
		5. Fill valid value into all required fields (support all brands)
		6.  Click “Save” link
		7. Publish above device
		8.Refresh APO offline database
		9. Click "Download Offline Database (Tuning)" link
		10. Navigate to "Product" tab
		11. Click on a product link
		12. Download complete profile and convert to txt file
		13. Get the Uuid of this headphone below
		14. Open the tuning offline database  file by SQLite and select Assert table
		15. Search Uuid at step 13 on colume Uuid column 
		16. Using conversion tool to convert all tuning file on step 15 to txt file
		17. Compare data tuning of each tuning on step 16 with the complete profile on portal at step12
        VP: Eagle data of brand headphones are stored on tuning db match up with complete profile tunings 

	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Products" tab
		productControl.click(PageHome.linkAccessories);
//		3. Create a product support  lineout, bluetooth, usb routes		
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct1 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		dataProduct1.put("bluetooth", "1 (Mono)");
		dataProduct1.put("usb", "1 (Mono)");
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct1);
//		4. Publish and get the Uuid of this product
		String hd_UUID= productControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
//		5. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		6. Click “Add App or Device” link
//		7. Leave the Product Type panel as ''DTS:X Master''
//		8. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		9.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
//		10. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		11. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		12. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//		13. Open the tuning offline database  file by SQLite
//		14. Navigate to Assert table in SQLite
//		VP:The Uri column at Asset table contains all file names:
//			+ [uuid]_lineout_44k.bin
//			+ [uuid]_lineout_48k.bin
//			+ [uuid]_bluetooth_44k.bin
//			+ [uuid]_bluetooth_48k.bin
//			+ [uuid]_usb_44k.bin
//			+ [uuid]_usb_48k.bin
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
        String[] binFilesExpected  = { "profile_"+hd_UUID+"_line-out_44k.bin", "profile_"+hd_UUID+"_line-out_48k.bin", "profile_"+hd_UUID+"_bluetooth_44k.bin", "profile_"+hd_UUID+"_bluetooth_48k.bin","profile_"+hd_UUID+"_usb_44k.bin", "profile_"+hd_UUID+"_usb_48k.bin"};
        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset where Uri LIKE '%"+hd_UUID+"%'", "Uri", null);
        Assert.assertTrue(ListUtil.isEquals(binFilesActual, binFilesExpected));
        // Just verify that the tuning data can decrypt 
        for (int i = 0; i < binFilesActual.size(); i++) {
        	System.out.println(binFilesActual.get(i));
        	 Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBNameTuning(device_UUID), "select Data from Asset where Uri = '"+binFilesActual.get(i)+"'","Data", device_UUID));	
		}
	}
	@Test
	public void TCMPG_5693_34(){
		Reporter.log("Verify that eagle data of inbox headphones are stored on tuning db match up with complete profile tunings of them");
	/*
	 *  1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click “Add App or Device” link
		4. Leave the Product Type panel as ''DTS:X Master''
		5. Fill valid value into all required fields (support inbox headphone)
		6.  Click “Save” link
		7. Publish above device
		8.Refresh APO offline database
		9. Click "Download Offline Database (Tuning)" link
		10. Click on inbox headphone link
		11. Download complete profile and convert to txt file
		12. Get the Uuid of this headphone below
		13. Open the tuning offline database  file by SQLite and select Assert table
		14. Search Uuid at step 12 on colume Uuid column 
		15. Using conversion tool to convert all tuning file on step 14 to txt file
		16. Compare data tuning of each tuning on step 15 with the complete profile on portal at step11
		VP: Eagle data of standard headphones are stored on tuning db match up with complete profile tunings 
	 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Products" tab
		productControl.click(PageHome.linkAccessories);
//		3. Create a product support  lineout, bluetooth, usb routes		
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct1 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		dataProduct1.put("bluetooth", "1 (Mono)");
		dataProduct1.put("usb", "1 (Mono)");
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct1);
//		4. Publish and get the Uuid of this product
		String hd_UUID= productControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
//		5. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		6. Click “Add App or Device” link
//		7. Leave the Product Type panel as ''DTS:X Master''
//		8. Fill valid value into all required fields
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		9.  Click “Save” link
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
		appDeviceControl.inputInboxHP(DeviceEdit.INBOX_FIELD, dataProduct1.get("name"));
		appDeviceControl.click(DeviceEdit.SAVE);
//		10. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		VP:  Verify that "Refresh offline database (APO) " button is displayed.
        Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO));
//		11. Click on "Refresh offline database (APO) " button
        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		12. Click "Download Offline Database (Tuning)" link
		appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//		13. Open the tuning offline database  file by SQLite
//		14. Navigate to Assert table in SQLite
//		VP:The Uri column at Asset table contains all file names:
//			+ [uuid]_lineout_44k.bin
//			+ [uuid]_lineout_48k.bin
//			+ [uuid]_bluetooth_44k.bin
//			+ [uuid]_bluetooth_48k.bin
//			+ [uuid]_usb_44k.bin
//			+ [uuid]_usb_48k.bin
        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
        String[] binFilesExpected  = { "profile_"+hd_UUID+"_line-out_44k.bin", "profile_"+hd_UUID+"_line-out_48k.bin", "profile_"+hd_UUID+"_bluetooth_44k.bin", "profile_"+hd_UUID+"_bluetooth_48k.bin","profile_"+hd_UUID+"_usb_44k.bin", "profile_"+hd_UUID+"_usb_48k.bin"};
        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset where Uri LIKE '%"+hd_UUID+"%'", "Uri", null);
        Assert.assertTrue(ListUtil.isEquals(binFilesActual, binFilesExpected));
        // VP: Eagle data of standard headphones are stored on tuning db match up with complete profile tunings 
        // Just verify that the tuning data can decrypt 
        for (int i = 0; i < binFilesActual.size(); i++) {
        	System.out.println(binFilesActual.get(i));
        	 Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBNameTuning(device_UUID), "select Data from Asset where Uri = '"+binFilesActual.get(i)+"'","Data", device_UUID));	
		}
	}
}
