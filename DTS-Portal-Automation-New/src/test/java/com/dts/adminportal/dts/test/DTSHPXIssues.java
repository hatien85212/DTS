package com.dts.adminportal.dts.test;

import java.util.Hashtable;

import junit.framework.Assert;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddBrand;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AudioRoutes;
import com.dts.adminportal.model.AudioRoutesEdit;
import com.dts.adminportal.model.AudioRoutesInfo;
import com.dts.adminportal.model.BrandInfo;
import com.dts.adminportal.model.CompanyInfo;
import com.dts.adminportal.model.DeviceEdit;
import com.dts.adminportal.model.DeviceInfo;
import com.dts.adminportal.model.DeviceList;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.util.TestData;

public class DTSHPXIssues extends BasePage{
	
	@Override
	protected void initData() {
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
		appDeviceControl.createADeviceOneBrand(DeviceEdit.getHash(),deviceData, newBrand.get("name"));
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
	 * Validation : Relax validation checks for 44.1 kHZ co-efficients in speaker tunings uploaded to the Partner Portal tool
	 */
	@Test
	public void TCMPG_3000_1(){
		Reporter.log("TCMPG_3000_1: Validation : Relax validation checks for 44.1 kHZ co-efficients in speaker tunings uploaded to the Partner Portal tool");
	
		/*
		 	1. Log into devportal as an admin user
			2. Navigate to Apps & Devices page
			3. Click Add App or Device link
			4. Set the Audio Route to Line-out0 - Line Out and the Standard Accessories to 'External Speaker'
			5. Upload a custom tuning file that the 3DHPEQ contains only 44.1k coefficient in 'model {headphone{' section
			VP: The error message ' Invalid tuning file: 48k coefficients for 3D Headphone EQ is missing.' is displayed
			6. Upload a custom tuning file that the OEM EQ contains only 44.1k coefficient in 'model {aeq{' section
			VP: The error message 'Invalid tuning file: 48k coefficients for AEQ is missing in iirCoefs.' is displayed
			7. Upload a custom tuning file that the 3DHPEQ contains only 48k coefficient in 'model {headphone{' section
			VP: The custom tuning is uploaded successgully
			8. Upload a custom tuning file that the OEM EQ contains only 48k coefficient in 'model {aeq{' section
			VP: The custom tuning is uploaded successgully
			9. Repeat from step 4 to 8 for the Audio Route: Bluetooth0 - Bluetooth
			VP: Verify error message same as above for each step.
			10. Repeat from step 5 to 8 for the default tuning file
			VP: Verify error message same as above for each step.

		 */
		// 1. Log into devportal as an admin user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to Apps & Devices page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click Add App or Device link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Set the Audio Route to Line-out0 - Line Out and the Standard Accessories to 'External Speaker'	
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Line_out0.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.External_Speakers.getName());	
		// 5. Upload a custom tuning file that the 3DHPEQ contains only 44.1k coefficient in 'model {headphone{' section
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.External_Speakers_Lineout_3DHPEQ_missing_48k.getName());
		// VP: The error message ' Invalid tuning file: 48k coefficients for 3D Headphone EQ is missing.' is displayed
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS).contains(DeviceEdit.requires.Invalid_EQ_is_missing.getName()));
		
//		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.External_Speakers_Lineout_3DHPEQ_missing_48k.getName().replaceAll(".dtscs", "")));
//		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
//		appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
//		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Line_out0.getName());
//		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.External_Speakers.getName());	
		
		
		// 6. Upload a custom tuning file that the OEM EQ contains only 44.1k coefficient in 'model {aeq{' section	
		appDeviceControl.uploadFile(DeviceEdit.RETRY_CUSTOM_TUNING, AddEditProductModel.FileUpload.External_Speakers_Lineout_AEQ_missing_48k.getName());
		/*
		 * VP: The error message 'Invalid file: 48k coefficients for AEQ is missing in iirCoefs.' is displayed
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS).contains(DeviceEdit.requires.Invalid_AEQ_is_missing.getName()));
		// 7. Upload a custom tuning file that the 3DHPEQ contains only 48k coefficient in 'model {headphone{' section
		appDeviceControl.uploadFile(DeviceEdit.RETRY_CUSTOM_TUNING, AddEditProductModel.FileUpload.External_Speakers_Lineout_3DHPEQ_missing_44k.getName());
		// VP: The custom tuning is uploaded successgully
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.External_Speakers_Lineout_3DHPEQ_missing_44k.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Line_out0.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.External_Speakers.getName());	
		// 8. Upload a custom tuning file that the OEM EQ contains only 48k coefficient in 'model {aeq{' section
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.External_Speakers_Lineout_AEQ_missing_44k.getName());
		// VP: The custom tuning is uploaded successgully
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.External_Speakers_Lineout_AEQ_missing_44k.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
		// 9. Repeat from step 4 to 8 for the Audio Route: Bluetooth0 - Bluetooth	
		/*
		 * VP: Verify error message same as above for each step.
		 */
		// Set the Audio Route to Bluetooth0 - Bluetooth and the Standard Accessories to 'External Speaker'
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Bluetooth0.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.External_Speakers.getName());	
		// Upload a custom tuning file that the 3DHPEQ contains only 44.1k coefficient in 'model {headphone{' section
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.External_Speakers_Bluetooth_3DHPEQ_missing_48k.getName());
		// VP: The error message ' Invalid tuning file: 48k coefficients for 3D Headphone EQ is missing.' is displayed
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS).contains(DeviceEdit.requires.Invalid_EQ_is_missing.getName()));
		// Upload a custom tuning file that the OEM EQ contains only 44.1k coefficient in 'model {aeq{' section
		appDeviceControl.uploadFile(DeviceEdit.RETRY_CUSTOM_TUNING, AddEditProductModel.FileUpload.External_Speakers_Bluetooth_AEQ_missing_48k.getName());
		// VP: The error message 'Invalid tuning file: 48k coefficients for AEQ is missing in iirCoefs.' is displayed
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS).contains(DeviceEdit.requires.Invalid_AEQ_is_missing.getName()));
		// Upload a custom tuning file that the 3DHPEQ contains only 48k coefficient in 'model {headphone{' section
		appDeviceControl.uploadFile(DeviceEdit.RETRY_CUSTOM_TUNING, AddEditProductModel.FileUpload.External_Speakers_Bluetooth_3DHPEQ_missing_44k.getName());
		// VP: The custom tuning is uploaded successfully
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.External_Speakers_Bluetooth_3DHPEQ_missing_44k.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Bluetooth0.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.External_Speakers.getName());	
		// Upload a custom tuning file that the OEM EQ contains only 48k coefficient in 'model {aeq{' section
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.External_Speakers_Bluetooth_AEQ_missing_44k.getName());
		// VP: The custom tuning is uploaded successfully
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.External_Speakers_Bluetooth_AEQ_missing_44k.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
		// 10. Repeat from step 5 to 8 for the default tuning file	
		// Upload a default tuning file that the 3DHPEQ contains only 44.1k coefficient in 'model {headphone{' section
		appDeviceControl.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE,AddEditProductModel.FileUpload.External_Speakers_Lineout_3DHPEQ_missing_48k.getName());
		/*
		 * VP: The error message 'Invalid file: 48k coefficients for 3D Headphone EQ is missing.' is displayed
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.DEFAULT_AUDIO_ERROR_MESSAGE).contains(DeviceEdit.requires.Invalid_EQ_is_missing.getName()));
		// Upload a default tuning file that the OEM EQ contains only 44.1k coefficient in 'model {aeq{' section
		appDeviceControl.uploadFile(DeviceEdit.RETRY_DEFAULT_AUDIO_ROUTE,AddEditProductModel.FileUpload.External_Speakers_Lineout_AEQ_missing_48k.getName());
		/*
		 * VP: The error message 'Invalid file: 48k coefficients for AEQ is missing in iirCoefs.' is displayed
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.DEFAULT_AUDIO_ERROR_MESSAGE).contains(DeviceEdit.requires.Invalid_AEQ_is_missing.getName()));
		// Upload a default tuning file that the 3DHPEQ contains only 48k coefficient in 'model {headphone{' section
		appDeviceControl.uploadFile(DeviceEdit.RETRY_DEFAULT_AUDIO_ROUTE,AddEditProductModel.FileUpload.External_Speakers_Bluetooth_3DHPEQ_missing_44k.getName());
		/*
		 * VP: The custom tuning is uploaded successfully
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.UPLOADED_DEFAULT_AUDIO_ROUTE));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_DEFAULT_AUDIO_ROUTE));
		appDeviceControl.doDelete(DeviceEdit.DELETE_DEFAULT_AUDIO_ROUTE);
		// Upload a custom tuning file that the OEM EQ contains only 48k coefficient in 'model {aeq{' section	
		appDeviceControl.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE,AddEditProductModel.FileUpload.External_Speakers_Bluetooth_AEQ_missing_44k.getName());
		/*
		 * VP: The custom tuning is uploaded successfully
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.UPLOADED_DEFAULT_AUDIO_ROUTE));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_DEFAULT_AUDIO_ROUTE));
		appDeviceControl.click(DeviceEdit.CANCEL);
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
	 * Validation : Relax validation checks for 44.1 kHZ co-efficients in speaker tunings uploaded to the Partner Portal tool
	 */
	@Test
	public void TCMPG_3000_2(){
		Reporter.log("TCMPG_3000_2: Validation : Relax validation checks for 44.1 kHZ co-efficients in speaker tunings uploaded to the Partner Portal tool");
	/*
	 	1. Log into devportal as an admin user
		2. Navigate to Audio Page
		3. Select a audio route in "Other Audio Routes" section
		4. Click "Edit Version" link
		5. Upload a tuning file that 3DHPEQ includes only 44.1K sample rates coefficients in " model { headhones {" section
		VP: The error message ' Invalid tuning file: 48k coefficients for 3D Headphone EQ is missing.' is displayed
		6. Upload a tuning file that AEQ  includes only 44.1K sample rates coefficients in " model { aeq {" section
		VP: The error message 'Invalid tuning file: 48k coefficients for AEQ is missing in iirCoefs.' is displayed
		7. Upload a tuning file that 3DHPEQ includes only 48K sample rates coefficients in " model { headhones {" section
		VP: The custom tuning is uploaded successfully
		8. Upload a tuning file that AEQ  includes only 48K sample rates coefficients in " model { aeq {" section
		VP: The custom tuning is uploaded successfully
		9. Upload a valid tuning file that includes full sample rates coefficients for 3DHPEQ and AEQ
		VP: The tuning file is uploaded successfully
		10. Click "Save" link
		VP: The audio route is saved successfully


	 */
		// 1. Log into devportal as an admin user
		loginControl.login(SUPER_USER_NAME,SUPER_USER_PASSWORD);
		// 2. Navigate to Audio Page
		audioControl.click(PageHome.linkAudioroutes);
		// 3. Select a audio route in "Standard Accessories" section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Other_Routes.Attached5_Internal_Speakers.getName()); // Attached5 - Internal Speakers (mode 4)
		// 4. Click "Edit Version" link
		audioControl.editVersion();
		// 6. Upload a tuning file that AEQ  includes only 44.1K sample rates coefficients in " model { aeq {" section	
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING,AddEditProductModel.FileUpload.Attached5_InternalSpeakersVoice_aeq_44100.getName());
		/*
		 * VP: The error message 'Invalid file: 48k coefficients for AEQ is missing in iirCoefs.' is displayed
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(AudioRoutesEdit.INVALID_MESSAGE).contains(AudioRoutesEdit.Upload_File_Message.Invalid_file_48k_for_AEQ.getName()));
		// 8. Upload a tuning file that AEQ  includes only 48K sample rates coefficients in " model { aeq {" section
		audioControl.uploadFileTuning(AudioRoutesEdit.RETRY_UPLOAD_AUDIOROUTE,AddEditProductModel.FileUpload.Attached5_InternalSpeakersVoice_aeq_48000.getName());
		/*
		 * VP: The tuning is uploaded successfully
		 */
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesEdit.UPLOADED_AUDIO_ROUTE));
		// 10. Click "Save" link	
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
	public void TCHPX_2666() throws InterruptedException{
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
		boolean isDownloaded = productControl.downloadFile(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE);
		/*
		 * VP: The Product Profile is downloaded successfully
		 */
		Assert.assertTrue(isDownloaded);
		String fileName = productControl.getProfileName();
		//8. Convert Product Profile to text file to check "bypass_hpeq" & "aeq_enable" values
		/*
		 * VP: The values of bypass_hpeq[0…6] = 0, aeq_enable[0..6] = 0
		 */
		productControl.convertDtscsText(fileName);
		Thread.sleep(2000);
		Assert.assertTrue(productControl.checkValueBypass(fileName,"none","0",ProductDetailModel.TuningTypes.BlueToothOnly.getName()));
		Assert.assertTrue(productControl.checkValueAEQ(fileName,"none","0",ProductDetailModel.TuningTypes.BlueToothOnly.getName()));
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
		Assert.assertTrue(productControl.downloadFile(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE));
		// Convert Product Profile to text file to check "bypass_hpeq" & "aeq_enable" values
		/*
		 * VP: The values of bypass_hpeq[0…6] = 1, aeq_enable[0..6] = 0
		 */
		productControl.convertDtscsText(fileName);
		Thread.sleep(2000);
		Assert.assertTrue(productControl.checkValueBypass(fileName,"none","1",ProductDetailModel.TuningTypes.BlueToothOnly.getName()));
		Assert.assertTrue(productControl.checkValueAEQ(fileName,"none","0",ProductDetailModel.TuningTypes.BlueToothOnly.getName()));
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
		Assert.assertTrue(productControl.downloadFile(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE));
		// Convert Product Profile to text file to check "bypass_hpeq" & "aeq_enable" values
		/*
		 * VP: The values of bypass_hpeq[0…6] = 1, aeq_enable[0..6] = 1
		 */
		productControl.convertDtscsText(fileName);
		Thread.sleep(2000);
		Assert.assertTrue(productControl.checkValueBypass(fileName,"none","1",ProductDetailModel.TuningTypes.BlueToothOnly.getName()));
		Assert.assertTrue(productControl.checkValueAEQ(fileName,"none","1",ProductDetailModel.TuningTypes.BlueToothOnly.getName()));
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
		Assert.assertTrue(productControl.downloadFile(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE));
		// Convert Product Profile to text file to check "bypass_hpeq" & "aeq_enable" values
		/*
		 * VP: The values of bypass_hpeq[0…6] = 0, aeq_enable[0..6] = 1
		 */
		productControl.convertDtscsText(fileName);
		Thread.sleep(2000);
		Assert.assertTrue(productControl.checkValueBypass(fileName,"none","0",ProductDetailModel.TuningTypes.BlueToothOnly.getName()));
		Assert.assertTrue(productControl.checkValueAEQ(fileName,"none","1",ProductDetailModel.TuningTypes.BlueToothOnly.getName()));
		//13. Click "Edit Version" link
		productControl.editVersion();
		//14. Set "Wired" headphone in "Connection Type"
		productControl.selectACheckbox(AddEditProductModel.WIRED_CHECKBOX);
		productControl.uncheckACheckbox(AddEditProductModel.BLUETOOTH_CHECKBOX);
		//15. Upload tuning file for line-out which has "oemEqPreference":"hpEq"
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		//16. Repeat from step 6 to step 8
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.LineoutOnly_hpEq.getName());
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		//Publish above product
		productWf.approveTuning();
		productWf.approveMarketing();
		productControl.click(ProductDetailModel.PUBLISH);
		//Download Published Product Profile
		/*
		 * VP: The Product Profile is downloaded successfully
		 */
		Assert.assertTrue(productControl.downloadFile(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE));
		// Convert Product Profile to text file to check "bypass_hpeq" & "aeq_enable" values
		productControl.convertDtscsText(fileName);
		Thread.sleep(2000);
		/*
		 * VP: The values of bypass_hpeq[0…6] = 0, aeq_enable[0..6] = 0
		 */
		Assert.assertTrue(productControl.checkValueBypass(fileName,"0","none",ProductDetailModel.TuningTypes.LineOutOnly.getName()));
		Assert.assertTrue(productControl.checkValueAEQ(fileName,"0","none",ProductDetailModel.TuningTypes.LineOutOnly.getName()));
		//17. Click "Edit Version" link
		productControl.editVersion();
		//18. Repeat from step 15 to step 16 with tuning file for line-out which has "oemEqPreference":"bypass"
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		//Repeat from step 6 to step 8
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.LineoutOnly_bypass.getName());
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		//Publish above product
		productWf.approveTuning();
		productWf.approveMarketing();
		productControl.click(ProductDetailModel.PUBLISH);
		//Download Published Product Profile
		/*
		 * VP: The Product Profile is downloaded successfully
		 */
		Assert.assertTrue(productControl.downloadFile(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE));
		// Convert Product Profile to text file to check "bypass_hpeq" & "aeq_enable" values
		productControl.convertDtscsText(fileName);
		Thread.sleep(2000);
		/*
		 * VP: The values of bypass_hpeq[0…6] = 1, aeq_enable[0..6] = 0
		 */
		Assert.assertTrue(productControl.checkValueBypass(fileName,"1","none",ProductDetailModel.TuningTypes.LineOutOnly.getName()));
		Assert.assertTrue(productControl.checkValueAEQ(fileName,"0","none",ProductDetailModel.TuningTypes.LineOutOnly.getName()));
		//19. Repeat from step 14 to step 16 with tuning file for line-out which has "oemEqPreference":"oemEq"
		productControl.editVersion();
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		//Repeat from step 6 to step 8
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.LineoutOnly_oemEq.getName());
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		//Publish above product
		productWf.approveTuning();
		productWf.approveMarketing();
		productControl.click(ProductDetailModel.PUBLISH);
		//Download Published Product Profile
		/*
		 * VP: The Product Profile is downloaded successfully
		 */
		Assert.assertTrue(productControl.downloadFile(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE));
		// Convert Product Profile to text file to check "bypass_hpeq" & "aeq_enable" values
		productControl.convertDtscsText(fileName);
		Thread.sleep(2000);
		/*
		 * VP: The values of bypass_hpeq[0…6] = 1, aeq_enable[0..6] = 1
		 */
		Assert.assertTrue(productControl.checkValueBypass(fileName,"1","none",ProductDetailModel.TuningTypes.LineOutOnly.getName()));
		Assert.assertTrue(productControl.checkValueAEQ(fileName,"1","none",ProductDetailModel.TuningTypes.LineOutOnly.getName()));
		//20. Repeat from step 14 to step 16 with tuning file for line-out which has "oemEqPreference":"hpEq+oemEq"
		productControl.editVersion();
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		//Repeat from step 6 to step 8
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.LineoutOnly_hpEq_oemEq.getName());
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		//Publish above product
		productWf.approveTuning();
		productWf.approveMarketing();
		productControl.click(ProductDetailModel.PUBLISH);
		//Download Published Product Profile
		/*
		 * VP: The Product Profile is downloaded successfully
		 */
		Assert.assertTrue(productControl.downloadFile(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE));
		// Convert Product Profile to text file to check "bypass_hpeq" & "aeq_enable" values
		productControl.convertDtscsText(fileName);
		Thread.sleep(2000);
		/*
		 * VP: The values of bypass_hpeq[0…6] = 0, aeq_enable[0..6] = 1
		 */
		Assert.assertTrue(productControl.checkValueBypass(fileName,"0","none",ProductDetailModel.TuningTypes.LineOutOnly.getName()));
		Assert.assertTrue(productControl.checkValueAEQ(fileName,"1","none",ProductDetailModel.TuningTypes.LineOutOnly.getName()));
		
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
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
	
	}
	
	/*
	 * TCMPG_3722_1: Validation : DTSCS Portal validation (MBHL + updates Trubass) for custom tuning file under App/Device Page
	 */
	@Test
	public void TCMPG_3722_1(){
		Reporter.log("TCMPG_3722_1: Validation : DTSCS Portal validation (MBHL + updates Trubass) for custom tuning file under App/Device Page");
	/*
	 	1. Log into devportal as an admin user
		2. Navigate to Apps & Devices page
		3. Click Add App or Device link
		4. Set the Audio Route to Line-out0 - Line Out and the Standard Accessories to 'External Speaker'
		5. Upload a custom tuning file that the TBHDX contains only 44.1k coefficient
		VP: The custom tuning is uploaded successfully
		6. Upload a custom tuning file that the TBHDX contains only 48k coefficient with some value out of range valid values
		VP: The error message '! Invalid file: Please upload the completed DTS Headphone:X tuning file.' is displayed
		7. Upload a valid custom tuning file that the TBHDX contains only 48k coefficient
		VP: The custom tuning is uploaded successfully
		8. Repeat from step 4 to 10 for the Audio Route: Bluetooth0 - Bluetooth
		VP: Verify error message same as above for each step.
		9. Set the Audio Route to the Other Audio Routes ( Attached5 - Internal Speakers (mode 4) )
		10. Upload a custom tuning file that the TBHDX/MBHL contains only 44.1k coefficient
		VP: The custom tuning is uploaded successfully
		11. Upload a custom tuning file that the TBHDX/MBHL contains only 48k coefficient with some value out of range valid values
		VP: The error message '! Invalid file: Please upload the completed DTS Headphone:X tuning file.' is displayed
		12. Upload a valid custom tuning file that the TBHDX/MBHL contains only 48k coefficient
		VP: The custom tuning is uploaded successfully
		13. Repeat from step 10 to 12 for the default tuning file
		VP: Verify error message same as above for each step.

	 */
		// 1. Log into devportal as an admin user
		loginControl.login(SUPER_USER_NAME,SUPER_USER_PASSWORD);
		// 2. Navigate to Apps & Devices page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click Add App or Device link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Set the Audio Route to Line-out0 - Line Out and the Standard Accessories to 'External Speaker'
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Line_out0.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.External_Speakers.getName());
		// 5. Upload a custom tuning file that the TBHDX contains only 44.1k coefficient
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.External_Speakers_Lineout_TBHDX_44k.getName());
		// VP: The custom tuning is uploaded successfully
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.External_Speakers_Lineout_TBHDX_44k.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		appDeviceControl.click(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
		appDeviceControl.click(PageHome.POPUP_DELETE);
		// 6. Upload a custom tuning file that the TBHDX contains only 48k coefficient with some value out of range valid values
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Line_out0.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.External_Speakers.getName());
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.External_Speakers_Lineout_out_of_range_value.getName());
		// VP: The error message '! Invalid file: Please upload the completed DTS Headphone:X tuning file.' is displayed
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS), DeviceEdit.requires.Invalid_file_Please_upload_the_completed_DTS_Headphone_X_tuning_file.getName());
		// 7. Upload a valid custom tuning file that the TBHDX contains only 48k coefficient
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.RETRY_CUSTOM_TUNING, AddEditProductModel.FileUpload.External_Speakers_Lineout.getName());
		// VP: The custom tuning is uploaded successfully
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.External_Speakers_Lineout.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		appDeviceControl.click(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
		appDeviceControl.click(PageHome.POPUP_DELETE);
		// 8. Repeat from step 4 to 10 for the Audio Route: Bluetooth0 - Bluetooth
		// VP: Verify error message same as above for each step.
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Bluetooth0.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.External_Speakers.getName());
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.External_Speakers_Bluetooth_TBHDX_44k.getName());
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.External_Speakers_Bluetooth_TBHDX_44k.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		appDeviceControl.click(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
		appDeviceControl.click(PageHome.POPUP_DELETE);
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Bluetooth0.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.External_Speakers.getName());
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.External_Speakers_Bluetooth_out_of_range_value.getName());
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS), DeviceEdit.requires.Invalid_file_Please_upload_the_completed_DTS_Headphone_X_tuning_file.getName());
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.RETRY_CUSTOM_TUNING, AddEditProductModel.FileUpload.External_Speakers_Bluetooth.getName());
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.External_Speakers_Bluetooth.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		appDeviceControl.click(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
		appDeviceControl.click(PageHome.POPUP_DELETE);
		// 9. Set the Audio Route to the Other Audio Routes ( Attached5 - Internal Speakers (mode 4) )
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached5_Internal_Speakers_Mode_4.getName());
		// 10. Upload a custom tuning file that the TBHDX/MBHL contains only 44.1k coefficient
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached5InternalSpeakersVoice_Custom_TBHDX_MBHL_44k.getName());
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Attached5InternalSpeakersVoice_Custom_TBHDX_MBHL.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		appDeviceControl.click(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
		appDeviceControl.click(PageHome.POPUP_DELETE);
		// 11. Upload a custom tuning file that the TBHDX/MBHL contains only 48k coefficient with some value out of range valid values
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached5_Internal_Speakers_Mode_4.getName());
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached5InternalSpeakersVoice_Custom_out_of_range_value.getName());
		// VP: The error message '! Invalid file: Please upload the completed DTS Headphone:X tuning file.' is displayed
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS), DeviceEdit.requires.Invalid_file_Please_upload_the_completed_DTS_Headphone_X_tuning_file.getName());
		// 12. Upload a valid custom tuning file that the TBHDX/MBHL contains only 48k coefficient
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.RETRY_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached5InternalSpeakersVoice_Custom_TBHDX_MBHL.getName());
		// VP: The custom tuning is uploaded successfully
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Attached5InternalSpeakersVoice_Custom_TBHDX_MBHL.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		appDeviceControl.click(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
		appDeviceControl.click(PageHome.POPUP_DELETE);
		// 13. Repeat from step 10 to 12 for the default tuning file
		// VP: Verify error message same as above for each step.
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, AddEditProductModel.FileUpload.Default_External_Audio_TBHDX_MBHL_44k.getName());
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_DEFAULT_AUDIO_NAME, "href").contains(AddEditProductModel.FileUpload.Default_External_Audio_TBHDX_MBHL_44k.getName().replaceAll(".dtscs", "")));
		appDeviceControl.click(DeviceEdit.DELETE_DEFAULT_AUDIO_ROUTE);
		appDeviceControl.click(PageHome.POPUP_DELETE);
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, AddEditProductModel.FileUpload.Default_External_Audio_out_of_range_value.getName());
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.DEFAULT_AUDIO_ERROR_MESSAGE), DeviceEdit.requires.Invalid_file_Please_upload_the_completed_DTS_Headphone_X_tuning_file.getName());
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.RETRY_DEFAULT_AUDIO_ROUTE, AddEditProductModel.FileUpload.Default_External_Audio_TBHDX_MBHL.getName());
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_DEFAULT_AUDIO_NAME, "href").contains(AddEditProductModel.FileUpload.Default_External_Audio_TBHDX_MBHL.getName().replaceAll(".dtscs", "")));
		
	}
	
	/*
	 * Validation : Validate that the uploaded custom tuning files for Audio page have sample 44100 and 48000 sample rate coefficients IF the 3D HPEQ and/or AEQ coefficients are present.
	 */
	@Test
	public void TCMPG_3722_2(){
		Reporter.log("TCMPG_3722_2: Validation : DTSCS Portal validation (MBHL + updates Trubass) for tuning file under Audio Page");
	/*
	 	1. Log into devportal as an admin user
		2. Navigate to Audio Page
		3. Select a audio route in "Other Audio Routes" section
		4. Click "Edit Version" link
		5. Upload a tuning file that the TBHDX/MBHL contains only 44.1k coefficient
		VP: The custom tuning is uploaded successfully
		6. Upload a tuning file that the TBHDX/MBHL contains only 48k coefficient with some value out of range valid values
		VP: The error message '! Invalid file: Please upload the completed DTS Headphone:X tuning file.' is displayed
		7. Upload a valid tuning file that the TBHDX/MBHL contains only 48k coefficient
		VP: The custom tuning is uploaded successfully
		8. Click "Save" link
		9. Navigate to Audio Page
		10. Select audio route “Over-Ear Headphones” in “Standard Accessories: Line-out0 and Bluetooth0” section
		11. Click "Edit Version" link
		VP: Verify error message same as above for each step.
		12. Upload a tuning file that the TBHDX contains only 44.1k coefficient
		VP: The custom tuning is uploaded successfully
		13. Upload a tuning file that the TBHDX contains only 48k coefficient with some value out of range valid values
		VP: The error message '! Invalid file: Please upload the completed DTS Headphone:X tuning file.' is displayed
		14. Upload a valid custom tuning file that the TBHDX contains only 48k coefficient
		VP: The custom tuning is uploaded successfully
		15. Click "Save" link
		VP: The audio route is saved successfully

	 */
		// 1. Log into devportal as an admin user
		loginControl.login(SUPER_USER_NAME,SUPER_USER_PASSWORD);
		// 2. Navigate to Audio Page
		audioControl.click(PageHome.linkAudioroutes);
		// 3. Select a audio route in "Other Audio Routes" section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Other_Routes.Attached0_Internal_Speakers.getName());
		// 4. Click "Edit Version" link
		audioControl.editVersion();
		// 5. Upload a tuning file that the TBHDX/MBHL contains only 44.1k coefficient
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_TBHDX_MBHL_44k.getName());
		// VP: The custom tuning is uploaded successfully
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesEdit.UPLOADED_AUDIO_ROUTE));
		// 6. Upload a tuning file that the TBHDX/MBHL contains only 48k coefficient with some value out of range valid values
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Fallback_out_of_range_value.getName());
		// VP: The error message '! Invalid file: Please upload the completed DTS Headphone:X tuning file.' is displayed
		Assert.assertEquals(appDeviceControl.getTextByXpath(AudioRoutesEdit.INVALID_MESSAGE), AudioRoutesEdit.Upload_File_Message.Invalid_file_Please_upload_the_completed_DTS_Headphone_X_tuning_file.getName());
		// 7. Upload a valid tuning file that the TBHDX/MBHL contains only 48k coefficient
		audioControl.uploadFileTuning(AudioRoutesEdit.RETRY_UPLOAD_AUDIOROUTE, AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_TBHDX_MBHL.getName());
		// VP: The custom tuning is uploaded successfully
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesEdit.UPLOADED_AUDIO_ROUTE));
		// 8. Click "Save" link
		audioControl.click(AudioRoutesEdit.SAVE);
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesInfo.ROUTER_TUNING).contains(AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_TBHDX_MBHL.getName()));
		// 9. Navigate to Audio Page
		audioControl.click(PageHome.linkAudioroutes);
		// 10. Select audio route “Over-Ear Headphones” in “Standard Accessories: Line-out0 and Bluetooth0” section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		// 11. Click "Edit Version" link
		audioControl.editVersion();
		// 12. Upload a tuning file that the TBHDX contains only 44.1k coefficient
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined_TBHDX_44k.getName());
		// VP: The custom tuning is uploaded successfully
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesEdit.UPLOADED_AUDIO_ROUTE));
		// 13. Upload a tuning file that the TBHDX contains only 48k coefficient with some value out of range valid values
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined_out_of_range_value.getName());
		// VP: The error message '! Invalid file: Please upload the completed DTS Headphone:X tuning file.' is displayed
		Assert.assertEquals(appDeviceControl.getTextByXpath(AudioRoutesEdit.INVALID_MESSAGE), AudioRoutesEdit.Upload_File_Message.Invalid_file_Please_upload_the_completed_DTS_Headphone_X_tuning_file.getName());
		// 14. Upload a valid custom tuning file that the TBHDX contains only 48k coefficient
		audioControl.uploadFileTuning(AudioRoutesEdit.RETRY_UPLOAD_AUDIOROUTE, AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined.getName());
		// 15. Click "Save" link
		audioControl.click(AudioRoutesEdit.SAVE);
		// VP: The audio route is saved successfully
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesInfo.ROUTER_TUNING).contains(AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined.getName()));
	}
	
	/*
	 * Standard Accessories: Line-out0 and Bluetooth0 tunings(Audio tab) and Custom accessory tunings (Apps and Devices) will not include MBHL block in the dtscs files.
	 */
	@Test
	public void TCMPG_4438_1(){
		Reporter.log("TCMPG_4438_1: Standard Accessories: Line-out0 and Bluetooth0 tunings(Audio tab) and Custom accessory tunings (Apps and Devices) will not include MBHL block in the dtscs files.");
	/*
	 	1. Log into devportal as an admin user
		2. Navigate to Apps & Devices page
		3. Click Add App or Device link
		4. Set the Audio Route to Line-out0 - Line Out and the Standard Accessories to 'External Speaker'
		5. Upload a custom tuning file include MBHL block
		VP: The error message "! Invalid file: The MBHL isn't applied to this tuning type. Please upload another tuning file without MBHL data." is displayed
		6. Upload a custom tuning file does not include MBHL block
		VP: The custom tuning is uploaded successfully
		7. Repeat from step 4 to 6 for the Audio Route: Bluetooth0 – Bluetooth and the Standard Accessories to 'External Speaker'
		VP: Verify error message same as above for each step.
		8. Repeat from step 4 to 6 for the Audio Route: Line-out0 - Line Out and the Standard Accessories to 'Earbuds'
		VP: Verify error message same as above for each step.
		9. Repeat from step 4 to 5 for the Audio Route: Attached0 - Internal Speakers (Default)
		VP: The custom tuning is uploaded successfully

	 */
		// 1. Log into devportal as an admin user
		loginControl.login(SUPER_USER_NAME,SUPER_USER_PASSWORD);
		// 2. Navigate to Apps & Devices page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click Add App or Device link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Set the Audio Route to Line-out0 - Line Out and the Standard Accessories to 'External Speaker'
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Line_out0.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.External_Speakers.getName());
		// 5. Upload a custom tuning file include MBHL block
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.External_Speakers_Lineout_TBHDX_MBHL.getName());
		// VP: The error message "! Invalid file: The MBHL isn't applied to this tuning type. Please upload another tuning file without MBHL data." is displayed
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS), DeviceEdit.requires.Invalid_file_The_MBHL_isnot_applied_to_this_tuning_type.getName());
		// 6. Upload a custom tuning file does not include MBHL block
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.RETRY_CUSTOM_TUNING, AddEditProductModel.FileUpload.External_Speakers_Lineout.getName());
		// VP: The custom tuning is uploaded successfully
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.External_Speakers_Lineout.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		appDeviceControl.click(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
		appDeviceControl.click(PageHome.POPUP_DELETE);
		// 7. Repeat from step 4 to 6 for the Audio Route: Bluetooth0 – Bluetooth and the Standard Accessories to 'External Speaker'
		// VP: Verify error message same as above for each step.
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Bluetooth0.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.External_Speakers.getName());
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.External_Speakers_Bluetooth_TBHDX_MBHL.getName());
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS), DeviceEdit.requires.Invalid_file_The_MBHL_isnot_applied_to_this_tuning_type.getName());
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.RETRY_CUSTOM_TUNING, AddEditProductModel.FileUpload.External_Speakers_Bluetooth.getName());
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.External_Speakers_Bluetooth.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		appDeviceControl.click(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
		appDeviceControl.click(PageHome.POPUP_DELETE);
		// 8. Repeat from step 4 to 6 for the Audio Route: Line-out0 - Line Out and the Standard Accessories to 'Earbuds'
		// VP: Verify error message same as above for each step.
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Line_out0.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Earbuds.getName());
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Earbuds_Lineout_TBHDX_MBHL.getName());
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS), DeviceEdit.requires.Invalid_file_The_MBHL_isnot_applied_to_this_tuning_type.getName());
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.RETRY_CUSTOM_TUNING, AddEditProductModel.FileUpload.Earbuds_Lineout.getName());
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Earbuds_Lineout.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		appDeviceControl.click(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
		appDeviceControl.click(PageHome.POPUP_DELETE);
		// 9. Repeat from step 4 to 5 for the Audio Route: Attached0 - Internal Speakers (Default)
		// VP: The custom tuning is uploaded successfully
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached0_Internal_Speakers.getName());
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Custom_TBHDX_MBHL.getName());
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Custom_TBHDX_MBHL.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		appDeviceControl.click(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
		appDeviceControl.click(PageHome.POPUP_DELETE);
	}
	
	/*
	 * DTSCS Portal validation  for tuning file under Audio Route Page
	 */
	@Test
	public void TCMPG_4438_2(){
		Reporter.log("TCMPG_4438_2: DTSCS Portal validation  for tuning file under Audio Route Page");
	/*
	 	1. Log into devportal as an admin user
		2. Navigate to Audio page
		3. Select audio route: “Over-Ear Headphones” in “Standard Accessories: Line-out0 and Bluetooth0” section
		4. Click "Edit Version" link
		5. Upload a custom tuning file include MBHL block
		VP: The error message "! Invalid file: The MBHL isn't applied to this tuning type. Please upload another tuning file without MBHL data." is displayed
		6. Upload a custom tuning file does not include MBHL block
		VP: The custom tuning is uploaded successfully
		7. Repeat from step 3 to 6 for audio route: “Earbuds”
		VP: The error message "! Invalid file: The MBHL isn't applied to this tuning type. Please upload another tuning file without MBHL data." is displayed
		8. Repeat from step 3 to 5 for audio route: “Attached0 - Internal Speakers (Default)” in “Other Audio Routes” section
		VP: The custom tuning is uploaded successfully

	 */
		// 1. Log into devportal as an admin user
		loginControl.login(SUPER_USER_NAME,SUPER_USER_PASSWORD);
		// 2. Navigate to Audio Page
		audioControl.click(PageHome.linkAudioroutes);
		// 3. Select a audio route in "Other Audio Routes" section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		// 4. Click "Edit Version" link
		audioControl.editVersion();
		// 5. Upload a custom tuning file include MBHL block
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined_TBHDX_MBHL.getName());
		// VP: The error message "! Invalid file: The MBHL isn't applied to this tuning type. Please upload another tuning file without MBHL data." is displayed
		Assert.assertEquals(appDeviceControl.getTextByXpath(AudioRoutesEdit.INVALID_MESSAGE), AudioRoutesEdit.Upload_File_Message.Invalid_file_The_MBHL_isnot_applied_to_this_tuning_type.getName());
		// 6. Upload a custom tuning file does not include MBHL block
		audioControl.uploadFileTuning(AudioRoutesEdit.RETRY_UPLOAD_AUDIOROUTE, AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined.getName());
		// VP: The custom tuning is uploaded successfully
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesEdit.UPLOADED_AUDIO_ROUTE));
		audioControl.click(AudioRoutesEdit.SAVE);
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesInfo.ROUTER_TUNING).contains(AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined.getName()));
		// 7. Repeat from step 3 to 6 for audio route: “Earbuds”
		// VP: The error message "! Invalid file: The MBHL isn't applied to this tuning type. Please upload another tuning file without MBHL data." is displayed
		audioControl.click(PageHome.linkAudioroutes);
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Earbuds.getName());
		audioControl.editVersion();
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Earbuds_Combined_TBHDX_MBHL.getName());
		Assert.assertEquals(appDeviceControl.getTextByXpath(AudioRoutesEdit.INVALID_MESSAGE), AudioRoutesEdit.Upload_File_Message.Invalid_file_The_MBHL_isnot_applied_to_this_tuning_type.getName());
		audioControl.uploadFileTuning(AudioRoutesEdit.RETRY_UPLOAD_AUDIOROUTE, AddEditProductModel.FileUpload.Earbuds_Combined.getName());
		audioControl.click(AudioRoutesEdit.SAVE);
		// VP: The audio route is saved successfully
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesInfo.ROUTER_TUNING).contains(AddEditProductModel.FileUpload.Earbuds_Combined.getName()));
		// 8. Repeat from step 3 to 5 for audio route: “Attached0 - Internal Speakers (Default)” in “Other Audio Routes” section
		// VP: The custom tuning is uploaded successfully
		audioControl.click(PageHome.linkAudioroutes);
		audioControl.selectAnAudioRouteByName(AudioRoutes.Other_Routes.Attached0_Internal_Speakers.getName());
		audioControl.editVersion();
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_TBHDX_MBHL.getName());
		audioControl.click(AudioRoutesEdit.SAVE);
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesInfo.ROUTER_TUNING).contains(AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_TBHDX_MBHL.getName()));
	}
}
