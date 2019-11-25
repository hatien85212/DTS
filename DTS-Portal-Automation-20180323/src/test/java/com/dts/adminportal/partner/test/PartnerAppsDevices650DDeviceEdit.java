package com.dts.adminportal.partner.test;

import java.util.Hashtable;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.data.type.LicenseeAuditStatus;
import com.dts.adminportal.data.type.LicenseeProductType;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.DeviceEdit;
import com.dts.adminportal.model.DeviceInfo;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.TestData;

public class PartnerAppsDevices650DDeviceEdit extends BasePage {

	@Override
	protected void initData() {
		
	}

	@Test(groups="MPG-6318")
	public void TCPPU_12() {
		appDeviceControl.addLog("ID TCPPU_12 : Verify Licensee Edit page donot have Eagle Version box when create new device");
		/*
			1. Log into DTS portal as license user
			2. Navigate to "Apps & Devices" page
			3. Click to "Add App or Device" link
			VP: Verify Licensee Edit page do not have Eagle Version box when create new device 
		*/
		
		// 1. Log into DTS portal as a licensee user successfully
		loginControl.login(PARTNER_LICENSSEE_USER, PARTNER_LICENSSEE_PASSWORD);
		// 2. Click "Apps & Devices" tab
		// 3. Click "Add App or Device" link
		appDeviceControl.addNewDevicePage();
		
		// VP: Verify Licensee Edit page do not have Eagle Version box when create new device
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.EAGLE_VERSION));
	}	

	@Test(groups="MPG-6318")
	public void TCPPU_13() {
		appDeviceControl.addLog("ID TCPPU_12 : Product type have those options on drop down box");
		/*
			1. Log into DTS portal as license user
			2. Navigate to "Apps & Devices" page
			3. Click to "Add App or Device" link
			4. Navigate the Product Type drop down box.
			VP: Product type have those option on drop down box:
				+ Select 
				+ DTS Ultra
				+ DTS Premium
				+ DTS Audio Processing
				+ DTS Headphone:X Beijing
				+ DTS Headphone:X Kansas City
				+ DTS Sound
		*/
		
		// 1. Log into DTS portal as a licensee user successfully
		loginControl.login(PARTNER_LICENSSEE_USER, PARTNER_LICENSSEE_PASSWORD);
		// 2. Click "Apps & Devices" tab
		// 3. Click "Add App or Device" link
		appDeviceControl.addNewDevicePage();
		//4. Navigate the Product Type drop down box.
		appDeviceControl.clickObject(DeviceEdit.PRODUCT_TYPE);
		
		// VP: Product type have those option on drop down box
		Assert.assertTrue(appDeviceControl.checkFullLicensedProductCombobox(DeviceEdit.PRODUCT_TYPE));
	}
	
	@Test(groups="MPG-6318")
	public void TCPPU_14() {
		appDeviceControl.addLog("ID TCPPU_14 : Verify when choosing one of those product type :DTS Ultra, DTS Premium, DTS Audio Processing the Audio Routes will show up Eagle 2.0 format");
		/*
			1. Log into DTS portal as license user
			2. Navigate to "Apps & Devices" page
			3. Click to "Add App or Device" link
			4. Navigate the Product Type drop down box.
			5. Choose "DTS:X Ultra" on drop down box
			6. Navigate the custom tuning on Audio Route
			VP: The Audio Route  have Ealge 2.0 format 
			7. Choose "DTS Premium" on drop down box
			8. Navigate the custom tuning on Audio Route
			VP: The Audio Route  have Ealge 2.0 format 
			9. Choose "DTS Audio Processing" on drop down box
			10.Navigate the custom tuning on Audio Route
			VP: The Audio Route  have Ealge 2.0 format 
		*/
		
		// 1. Log into DTS portal as a license user successfully
		loginControl.login(PARTNER_LICENSSEE_USER, PARTNER_LICENSSEE_PASSWORD);
		// 2. Click "Apps & Devices" tab
		// 3. Click "Add App or Device" link
		appDeviceControl.addNewDevicePage();
		// 4. Choose "DTS:X Ultra" on drop down box
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, LicenseeProductType.HPX_HIGH_2.getName());
		
		// VP: The Audio Route  have Ealge 2.0 format
		Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE),DeviceEdit.Standard_Accessories.getNames()));

		// 5. Choose "DTS:X Premium" on drop down box
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, LicenseeProductType.HPX_MEDIUM_2.getName());
		
		// VP: The Audio Route  have Ealge 2.0 format
		Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE),DeviceEdit.Standard_Accessories.getNames()));

		// 6. Choose "DTS:X Ultra" on drop down box
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, LicenseeProductType.HPX_LOW_2.getName());
		
		// VP: The Audio Route  have Ealge 2.0 format
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE));
	}

	@Test(groups="MPG-6318")
	public void TCPPU_15() {
		appDeviceControl.addLog("ID TCPPU_15 : Verify when choosing one of those product type: DTS Headphone:X Beijing, DTS Headphone:X Kansas City, DTS Sound the Audio Routes will show up Eagle 1.4 format ");
		/*
			1. Log into DTS portal as license user
			2. Navigate to "Apps & Devices" page
			3. Click to "Add App or Device" link
			4. Navigate the Product Type drop down box.
			5. Choose "DTS Headphone:X Beijing" on drop down box
			6. Navigate the custom tuning on Audio Route
			VP: The Audio Route  have Ealge 1.0 format 
			7. Choose "DTS Headphone:X Kansas City" on drop down box
			8. Navigate the custom tuning on Audio Route
			VP: The Audio Route  have Ealge 1.0 format 
			9. Choose "DTS Sound" on drop down box
			10.Navigate the custom tuning on Audio Route
			VP: The Audio Route  have Ealge 1.0 format 
		*/
		
		// 1. Log into DTS portal as a license user successfully
		loginControl.login(PARTNER_LICENSSEE_USER, PARTNER_LICENSSEE_PASSWORD);
		// 2. Click "Apps & Devices" tab
		// 3. Click "Add App or Device" link
		appDeviceControl.addNewDevicePage();
		// 4. Choose "DTS:X Ultra" on drop down box
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, LicenseeProductType.HPX_HIGH_1.getName());
		
		// VP: The Audio Route  have Ealge 1.0 format
		Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE),
				DeviceEdit.routes.getNames()));

		// 5. Choose "DTS:X Premium" on drop down box
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, LicenseeProductType.HPX_MEDIUM_1.getName());
		
		// VP: The Audio Route  have Ealge 1.0 format
		Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE),
				DeviceEdit.routes_medium.getNames()));

		// 6. Choose "DTS:X Ultra" on drop down box
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, LicenseeProductType.HPX_LOW_1.getName());
		
		// VP: The Audio Route  have Ealge 1.0 format
		Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE),
				DeviceEdit.routes_low.getNames()));
	}

	@Test(groups="MPG-6318")
	public void TCPPU_16() {
		appDeviceControl.addLog("ID TCPPU_16 : Verify invalid tuning file message \"Please upload an appropriate Product tuning file\" when upload wrong tuning for DTS Ultra");
		/*
			1. Log into DTS portal as license user
			2. Navigate to "Apps & Devices" page
			3. Click to "Add App or Device" link
			4. Navigate the Product Type drop down box.
			5. Choose "DTS:X Ultra" on drop down box
			6. Navigate the custom tuning on Audio Route
			7. Choose the "Over-Ear Headphones" on Standard Accessories
			8. Upload Invalid tuning file
			VP: Invalid tuning message: Please upload an appropriate Product tuning file.
		*/
		
		// 1. Log into DTS portal as a license user successfully
		loginControl.login(PARTNER_LICENSSEE_USER, PARTNER_LICENSSEE_PASSWORD);
		// 2. Click "Apps & Devices" tab
		// 3. Click "Add App or Device" link
		appDeviceControl.addNewDevicePage();
		// 4. Choose "DTS:X Ultra" on drop down box
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, LicenseeProductType.HPX_HIGH_2.getName());
		// 7. Choose the "Over-Ear Headphones" on Standard Accessories
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,
				DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
		// 8. Upload Invalid tuning file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,
				AddEditProductModel.FileUpload.Over_Ear_Headphones_Lineout.getName());
		
		// VP: Invalid tuning message: Please upload an appropriate Product tuning file.
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS)
				.contains(DeviceEdit.requires.Invalid_file_Please_upload_the_Eagle_v1_1_tuning_file.getName()));
	}

	@Test(groups="MPG-6318")
	public void TCPPU_17() {
		appDeviceControl.addLog("ID TCPPU_17 : Verify invalid tuning file message \"Please upload an appropriate Product tuning file\" when upload wrong tuning for DTS Premium");
		/*
			1. Log into DTS portal as license user
			2. Navigate to "Apps & Devices" page
			3. Click to "Add App or Device" link
			4. Navigate the Product Type drop down box.
			5. Choose "DTS:X Premium" on drop down box
			6. Navigate the custom tuning on Audio Route
			7. Choose the "Over-Ear Headphones" on Standard Accessories
			8. Upload Invalid tuning file
			VP: Invalid tuning message: Please upload an appropriate Product tuning file.
		*/
		
		// 1. Log into DTS portal as a license user successfully
		loginControl.login(PARTNER_LICENSSEE_USER, PARTNER_LICENSSEE_PASSWORD);
		// 2. Click "Apps & Devices" tab
		// 3. Click "Add App or Device" link
		appDeviceControl.addNewDevicePage();
		// 4. Choose "DTS:X Premium" on drop down box
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, LicenseeProductType.HPX_MEDIUM_2.getName());
		// 7. Choose the "Over-Ear Headphones" on Standard Accessories
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,
				DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
		// 8. Upload Invalid tuning file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,
				AddEditProductModel.FileUpload.Over_Ear_Headphones_Lineout.getName());
		
		// VP: Invalid tuning message: Please upload an appropriate Product tuning file.
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS)
				.contains(DeviceEdit.requires.Invalid_file_Please_upload_the_Eagle_v1_1_tuning_file.getName()));
	}

	@Test(groups="MPG-6318")
	public void TCPPU_19() {
		appDeviceControl.addLog("ID TCPPU_19 : Verify invalid tuning file message \"Please upload an appropriate Product tuning file\" when upload wrong tuning for DTS Headphone:X Beijing");
		/*
			1. Log into DTS portal as license user
			2. Navigate to "Apps & Devices" page
			3. Click to "Add App or Device" link
			4. Navigate the Product Type drop down box.
			5. Choose "DTS Headphone:X Beijing" on drop down box
			6. Navigate the custom tuning on Audio Route
			7. Choose the "Over-Ear Headphones" on Standard Accessories
			8. Upload Invalid tuning file
			VP: Invalid tuning message: Please upload an appropriate Product tuning file.
		*/
		
		// 1. Log into DTS portal as a license user successfully
		loginControl.login(PARTNER_LICENSSEE_USER, PARTNER_LICENSSEE_PASSWORD);
		// 2. Click "Apps & Devices" tab
		// 3. Click "Add App or Device" link
		appDeviceControl.addNewDevicePage();
		// 4. Choose "DTS Headphone:X Beijing" on drop down box
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, LicenseeProductType.HPX_HIGH_1.getName());
		// 7. Choose the "Over-Ear Headphones" on Standard Accessories
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Line_out0.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,
				DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
		// 8. Upload Invalid tuning file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,
				AddEditProductModel.FileUpload.Over_Ear_Headphones_Lineout_EagleV20.getName());
		
		// VP: Invalid tuning message: Please upload an appropriate Product tuning file.
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS)
				.contains(DeviceEdit.requires.Invalid_file_Please_upload_the_Eagle_v1_1_tuning_file.getName()));
	}

	@Test(groups="MPG-6318")
	public void TCPPU_20() {
		appDeviceControl.addLog("ID TCPPU_20 : Verify invalid tuning file message \"Please upload an appropriate Product tuning file\" when upload wrong tuning for DTS Headphone:X Kansas City");
		/*
			1. Log into DTS portal as license user
			2. Navigate to "Apps & Devices" page
			3. Click to "Add App or Device" link
			4. Navigate the Product Type drop down box.
			5. Choose "DTS Headphone:X Kansas City" on drop down box
			6. Navigate the custom tuning on Audio Route
			7. Choose the "Over-Ear Headphones" on Standard Accessories
			8. Upload Invalid tuning file
			VP: Invalid tuning message: Please upload an appropriate Product tuning file.
		*/
		
		// 1. Log into DTS portal as a license user successfully
		loginControl.login(PARTNER_LICENSSEE_USER, PARTNER_LICENSSEE_PASSWORD);
		// 2. Click "Apps & Devices" tab
		// 3. Click "Add App or Device" link
		appDeviceControl.addNewDevicePage();
		// 4. Choose "DTS Headphone:X Kansas City" on drop down box
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, LicenseeProductType.HPX_MEDIUM_1.getName());
		// 7. Choose the "Over-Ear Headphones" on Standard Accessories
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Line_out0.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,
				DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
		// 8. Upload Invalid tuning file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,
				AddEditProductModel.FileUpload.Over_Ear_Headphones_Lineout_EagleV20.getName());
		
		// VP: Invalid tuning message: Please upload an appropriate Product tuning file.
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS)
				.contains(DeviceEdit.requires.Invalid_file_Please_upload_the_Eagle_v1_1_tuning_file.getName()));
	}

	@Test(groups="MPG-6318")
	public void TCPPU_21() {
		appDeviceControl.addLog("ID TCPPU_21 : Verify invalid tuning file message \"Please upload an appropriate Product tuning file\" when upload wrong tuning for DTS Headphone:X Kansas City");
		/*
			1. Log into DTS portal as license user
			2. Navigate to "Apps & Devices" page
			3. Click to "Add App or Device" link
			4. Navigate the Product Type drop down box.
			5. Choose "DTS Sound" on drop down box
			6. Navigate the custom tuning on Audio Route
			7. Choose the "Attached1 - Internal Speakers (Off)" on Audio Route
			8. Upload Invalid tuning file
			VP: Invalid tuning message: Please upload an appropriate Product tuning file.
		*/
		
		// 1. Log into DTS portal as a license user successfully
		loginControl.login(PARTNER_LICENSSEE_USER, PARTNER_LICENSSEE_PASSWORD);
		// 2. Click "Apps & Devices" tab
		// 3. Click "Add App or Device" link
		appDeviceControl.addNewDevicePage();
		// 4. Choose "DTS Sound" on drop down box
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, LicenseeProductType.HPX_LOW_1.getName());
		// 7. Choose the "Attached1 - Internal Speakers (Off)" on Audio Route
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,
				DeviceEdit.routes_low.Attached1_Internal_Speakers_Off.getName());
		// 8. Upload Invalid tuning file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,
				AddEditProductModel.FileUpload.Attached_1_Bypass_Off_Eagle2_0.getName());
		
		// VP: Invalid tuning message: Please upload an appropriate Product tuning file.
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS)
				.contains(DeviceEdit.requires.Invalid_file_Please_upload_the_Eagle_v1_1_tuning_file.getName()));
	}
	
	@Test(groups="MPG-6318")
	public void TCPPU_27(){
		appDeviceControl.addLog("ID TCPPU_27 : Verify Licensee Detail page show up correct information when create device for DTS Ultra");
		/*
			1. Click to "Add App or Device"
			2. Navigate the Product Type drop down box.
			3. Choose "DTS Ultra" on drop down box
			4. Fill valid value into all required fields
			5. Click to "Save" link
			6. Publish the device
			VP: Verify Licensee Detail page show up correct information when create device for DTS Ultra.

		 */
		// 1. Log into DTS portal as a license user successfully
		loginControl.login(PARTNER_LICENSSEE_USER, PARTNER_LICENSSEE_PASSWORD);
		// 3. Click on "Add App or Device"
		appDeviceControl.addNewDevicePage();
		// 4. Save and publish the device
		Hashtable<String, String> appDevice = TestData.appDeviceLicensePublishEagleV2();
		appDevice.remove("save");
		appDeviceControl.createNewLicenseeDeviceEagle20(DeviceEdit.getHash(), appDevice);
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: The product name: "DTS Ultra"
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.urlContains("/deviceView"));
		wait.until(ExpectedConditions.textToBe(By.xpath(DeviceInfo.NAME), appDevice.get("name")));
		wait.until(ExpectedConditions.textToBe(By.xpath(DeviceInfo.PRODUCT_TYPE),
				LicenseeProductType.HPX_HIGH_2.getName()));
		String url = appDeviceControl.getCurrentURL();
		//Publishing process
		appDeviceControl.click(DeviceInfo.REQUEST_DTS_REVIEW);
		Pattern p = Pattern.compile("[.\n]*" + LicenseeAuditStatus.PENDING_DTS_REVIEW.getName() + "$");
		wait.until(ExpectedConditions.textMatches(By.xpath(DeviceInfo.UNSUBMITTED_STATE), p));
		
		// DTS need to approved before publish a device
		appDeviceControl.dtsApproveAuditLicenseeDevice(loginControl, SUPER_USER_NAME, SUPER_USER_PASSWORD, url);

		// 1. Log into DTS portal as a license user successfully
		loginControl.login(PARTNER_LICENSSEE_USER, PARTNER_LICENSSEE_PASSWORD);
		// 2. View the device
		appDeviceControl.navigateToDeviceDetailPage(url);
		// Publish the device
		appDeviceControl.click(DeviceInfo.PUBLISH_LICENSE);
		wait.until(ExpectedConditions.textToBe(By.xpath(DeviceInfo.PUBLISH_LICENSE_STATE),
				LicenseeAuditStatus.PUBLISHED.getName()));
		
		appDeviceControl.click(DeviceInfo.DELETE);
	}

	@Test(groups="MPG-6318")
	public void TCPPU_28(){
		appDeviceControl.addLog("ID TCPPU_28 : Verify Licensee Detail page show up correct information when create device for DTS Premium");
		/*
			1. Click to "Add App or Device"
			2. Navigate the Product Type drop down box.
			3. Choose "DTS Premium" on drop down box
			4. Fill valid value into all required fields
			5. Click to "Save" link
			6. Publish the device
			VP: Verify Licensee Detail page show up correct information when create device for DTS Premium.

		 */
		// 1. Log into DTS portal as a license user successfully
		loginControl.login(PARTNER_LICENSSEE_USER, PARTNER_LICENSSEE_PASSWORD);
		// 3. Click on "Add App or Device"
		appDeviceControl.addNewDevicePage();
		// 4. Save and publish the device
		Hashtable<String, String> appDevice = TestData.appDeviceLicensePublishEagleV2();
		appDevice.put("product type", LicenseeProductType.HPX_MEDIUM_2.getName());
		appDevice.remove("save");
		appDeviceControl.createNewLicenseeDeviceEagle20(DeviceEdit.getHash(), appDevice);
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: The product name: "DTS Premium"
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.urlContains("/deviceView"));
		wait.until(ExpectedConditions.textToBe(By.xpath(DeviceInfo.NAME), appDevice.get("name")));
		wait.until(ExpectedConditions.textToBe(By.xpath(DeviceInfo.PRODUCT_TYPE),
				LicenseeProductType.HPX_MEDIUM_2.getName()));
		String url = appDeviceControl.getCurrentURL();
		//Publishing process
		appDeviceControl.click(DeviceInfo.REQUEST_DTS_REVIEW);
		Pattern p = Pattern.compile("[.\n]*" + LicenseeAuditStatus.PENDING_DTS_REVIEW.getName() + "$");
		wait.until(ExpectedConditions.textMatches(By.xpath(DeviceInfo.UNSUBMITTED_STATE), p));
		
		// DTS need to approved before publish a device
		appDeviceControl.dtsApproveAuditLicenseeDevice(loginControl, SUPER_USER_NAME, SUPER_USER_PASSWORD, url);

		// 1. Log into DTS portal as a license user successfully
		loginControl.login(PARTNER_LICENSSEE_USER, PARTNER_LICENSSEE_PASSWORD);
		// 2. View the device
		appDeviceControl.navigateToDeviceDetailPage(url);
		// Publish the device
		appDeviceControl.click(DeviceInfo.PUBLISH_LICENSE);
		wait.until(ExpectedConditions.textToBe(By.xpath(DeviceInfo.PUBLISH_LICENSE_STATE),
				LicenseeAuditStatus.PUBLISHED.getName()));
		
		appDeviceControl.click(DeviceInfo.DELETE);
	}
	
	@Test(groups="MPG-6318")
	public void TCPPU_29(){
		appDeviceControl.addLog("ID TCPPU_29 : Verify Licensee Detail page show up correct information when create device for DTS Audio Processing");
		/*
			1. Click to "Add App or Device"
			2. Navigate the Product Type drop down box.
			3. Choose "DTS Audio Processing" on drop down box
			4. Fill valid value into all required fields
			5. Click to "Save" link
			6. Publish the device
			VP: Verify Licensee Detail page show up correct information when create device for DTS Audio Processing.

		 */
		// 1. Log into DTS portal as a license user successfully
		loginControl.login(PARTNER_LICENSSEE_USER, PARTNER_LICENSSEE_PASSWORD);
		// 3. Click on "Add App or Device"
		appDeviceControl.addNewDevicePage();
		// 4. Save and publish the device
		Hashtable<String, String> appDevice = TestData.appDeviceLicensePublishEagleV2();
		appDevice.put("product type", LicenseeProductType.HPX_LOW_2.getName());
		appDevice.remove("save");
		appDeviceControl.createNewLicenseeDeviceEagle20(DeviceEdit.getHash(), appDevice);
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: The product name: "DTS Audio Processing"
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.urlContains("/deviceView"));
		wait.until(ExpectedConditions.textToBe(By.xpath(DeviceInfo.NAME), appDevice.get("name")));
		wait.until(ExpectedConditions.textToBe(By.xpath(DeviceInfo.PRODUCT_TYPE),
				LicenseeProductType.HPX_LOW_2.getName()));
		String url = appDeviceControl.getCurrentURL();
		//Publishing process
		appDeviceControl.click(DeviceInfo.REQUEST_DTS_REVIEW);
		Pattern p = Pattern.compile("[.\n]*" + LicenseeAuditStatus.PENDING_DTS_REVIEW.getName() + "$");
		wait.until(ExpectedConditions.textMatches(By.xpath(DeviceInfo.UNSUBMITTED_STATE), p));
		
		// DTS need to approved before publish a device
		appDeviceControl.dtsApproveAuditLicenseeDevice(loginControl, SUPER_USER_NAME, SUPER_USER_PASSWORD, url);

		// 1. Log into DTS portal as a license user successfully
		loginControl.login(PARTNER_LICENSSEE_USER, PARTNER_LICENSSEE_PASSWORD);
		// 2. View the device
		appDeviceControl.navigateToDeviceDetailPage(url);
		// Publish the device
		appDeviceControl.click(DeviceInfo.PUBLISH_LICENSE);
		wait.until(ExpectedConditions.textToBe(By.xpath(DeviceInfo.PUBLISH_LICENSE_STATE),
				LicenseeAuditStatus.PUBLISHED.getName()));
		
		appDeviceControl.click(DeviceInfo.DELETE);
	}
	
	@Test(groups="MPG-6318")
	public void TCPPU_30(){
		appDeviceControl.addLog("ID TCPPU_30 : Verify Licensee Detail page show up correct information when create device for DTS Headphone:X Beijing");
		/*
			1. Click to "Add App or Device"
			2. Navigate the Product Type drop down box.
			3. Choose "DTS Headphone:X Beijing" on drop down box
			4. Fill valid value into all required fields
			5. Click to "Save" link
			6. Publish the device
			VP: Verify Licensee Detail page show up correct information when create device for DTS Headphone:X Beijing.

		 */
		// 1. Log into DTS portal as a license user successfully
		loginControl.login(PARTNER_LICENSSEE_USER, PARTNER_LICENSSEE_PASSWORD);
		// 3. Click on "Add App or Device"
		appDeviceControl.addNewDevicePage();
		// 4. Save and publish the device
		Hashtable<String, String> appDevice = TestData.appDevicePublishLow();
		appDevice.put("product type", LicenseeProductType.HPX_HIGH_1.getName());
		appDevice.put("brand", BasePage.BRAND_NAME_LICENSEE);
		appDevice.remove("save");
		appDeviceControl.createNewLicenseeDeviceEagle14(DeviceEdit.getHash(), appDevice);
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: The product name: "DTS Headphone:X Beijing"
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.urlContains("/deviceView"));
		wait.until(ExpectedConditions.textToBe(By.xpath(DeviceInfo.NAME), appDevice.get("name")));
		wait.until(ExpectedConditions.textToBe(By.xpath(DeviceInfo.PRODUCT_TYPE),
				LicenseeProductType.HPX_HIGH_1.getName()));
		String url = appDeviceControl.getCurrentURL();
		//Publishing process
		appDeviceControl.click(DeviceInfo.REQUEST_DTS_REVIEW);
		Pattern p = Pattern.compile("[.\n]*" + LicenseeAuditStatus.PENDING_DTS_REVIEW.getName() + "$");
		wait.until(ExpectedConditions.textMatches(By.xpath(DeviceInfo.UNSUBMITTED_STATE), p));
		
		// DTS need to approved before publish a device
		appDeviceControl.dtsApproveAuditLicenseeDevice(loginControl, SUPER_USER_NAME, SUPER_USER_PASSWORD, url);

		// 1. Log into DTS portal as a license user successfully
		loginControl.login(PARTNER_LICENSSEE_USER, PARTNER_LICENSSEE_PASSWORD);
		// 2. View the device
		appDeviceControl.navigateToDeviceDetailPage(url);
		// Publish the device
		appDeviceControl.click(DeviceInfo.PUBLISH_LICENSE);
		wait.until(ExpectedConditions.textToBe(By.xpath(DeviceInfo.PUBLISH_LICENSE_STATE),
				LicenseeAuditStatus.PUBLISHED.getName()));
		
		appDeviceControl.click(DeviceInfo.DELETE);
	}
	
	@Test(groups="MPG-6318")
	public void TCPPU_31(){
		appDeviceControl.addLog("ID TCPPU_30 : Verify Licensee Detail page show up correct information when create device for DTS Headphone:X Kansas City");
		/*
			1. Click to "Add App or Device"
			2. Navigate the Product Type drop down box.
			3. Choose "DTS Headphone:X Kansas City" on drop down box
			4. Fill valid value into all required fields
			5. Click to "Save" link
			6. Publish the device
			VP: Verify Licensee Detail page show up correct information when create device for DTS Headphone:X Kansas City.

		 */
		// 1. Log into DTS portal as a license user successfully
		loginControl.login(PARTNER_LICENSSEE_USER, PARTNER_LICENSSEE_PASSWORD);
		// 3. Click on "Add App or Device"
		appDeviceControl.addNewDevicePage();
		// 4. Save and publish the device
		Hashtable<String, String> appDevice = TestData.appDevicePublishLow();
		appDevice.put("product type", LicenseeProductType.HPX_MEDIUM_1.getName());
		appDevice.put("brand", BasePage.BRAND_NAME_LICENSEE);
		appDevice.remove("save");
		appDeviceControl.createNewLicenseeDeviceEagle14(DeviceEdit.getHash(), appDevice);
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: The product name: "DTS Headphone:X Kansas City"
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.urlContains("/deviceView"));
		wait.until(ExpectedConditions.textToBe(By.xpath(DeviceInfo.NAME), appDevice.get("name")));
		wait.until(ExpectedConditions.textToBe(By.xpath(DeviceInfo.PRODUCT_TYPE),
				LicenseeProductType.HPX_MEDIUM_1.getName()));
		String url = appDeviceControl.getCurrentURL();
		//Publishing process
		appDeviceControl.click(DeviceInfo.REQUEST_DTS_REVIEW);
		Pattern p = Pattern.compile("[.\n]*" + LicenseeAuditStatus.PENDING_DTS_REVIEW.getName() + "$");
		wait.until(ExpectedConditions.textMatches(By.xpath(DeviceInfo.UNSUBMITTED_STATE), p));
		
		// DTS need to approved before publish a device
		appDeviceControl.dtsApproveAuditLicenseeDevice(loginControl, SUPER_USER_NAME, SUPER_USER_PASSWORD, url);

		// 1. Log into DTS portal as a license user successfully
		loginControl.login(PARTNER_LICENSSEE_USER, PARTNER_LICENSSEE_PASSWORD);
		// 2. View the device
		appDeviceControl.navigateToDeviceDetailPage(url);
		// Publish the device
		appDeviceControl.click(DeviceInfo.PUBLISH_LICENSE);
		wait.until(ExpectedConditions.textToBe(By.xpath(DeviceInfo.PUBLISH_LICENSE_STATE),
				LicenseeAuditStatus.PUBLISHED.getName()));
		
		appDeviceControl.click(DeviceInfo.DELETE);
	}
	
	@Test(groups="MPG-6318")
	public void TCPPU_32(){
		appDeviceControl.addLog("ID TCPPU_32 : Verify Licensee Detail page show up correct information when create device for DTS Sound");
		/*
			1. Click to "Add App or Device"
			2. Navigate the Product Type drop down box.
			3. Choose "DTS Sound" on drop down box
			4. Fill valid value into all required fields
			5. Click to "Save" link
			6. Publish the device
			VP: Verify Licensee Detail page show up correct information when create device for DTS Sound.

		 */
		// 1. Log into DTS portal as a license user successfully
		loginControl.login(PARTNER_LICENSSEE_USER, PARTNER_LICENSSEE_PASSWORD);
		// 3. Click on "Add App or Device"
		appDeviceControl.addNewDevicePage();
		// 4. Save and publish the device
		Hashtable<String, String> appDevice = TestData.appDevicePublishLow();
		appDevice.put("product type", LicenseeProductType.HPX_LOW_1.getName());
		appDevice.put("brand", BasePage.BRAND_NAME_LICENSEE);
		appDevice.remove("save");
		appDeviceControl.createNewLicenseeDeviceEagle14(DeviceEdit.getHash(), appDevice);
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: The product name: "DTS Sound"
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.urlContains("/deviceView"));
		wait.until(ExpectedConditions.textToBe(By.xpath(DeviceInfo.NAME), appDevice.get("name")));
		wait.until(ExpectedConditions.textToBe(By.xpath(DeviceInfo.PRODUCT_TYPE),
				LicenseeProductType.HPX_LOW_1.getName()));
		String url = appDeviceControl.getCurrentURL();
		//Publishing process
		appDeviceControl.click(DeviceInfo.REQUEST_DTS_REVIEW);
		Pattern p = Pattern.compile("[.\n]*" + LicenseeAuditStatus.PENDING_DTS_REVIEW.getName() + "$");
		wait.until(ExpectedConditions.textMatches(By.xpath(DeviceInfo.UNSUBMITTED_STATE), p));
		
		// DTS need to approved before publish a device
		appDeviceControl.dtsApproveAuditLicenseeDevice(loginControl, SUPER_USER_NAME, SUPER_USER_PASSWORD, url);

		// 1. Log into DTS portal as a license user successfully
		loginControl.login(PARTNER_LICENSSEE_USER, PARTNER_LICENSSEE_PASSWORD);
		// 2. View the device
		appDeviceControl.navigateToDeviceDetailPage(url);
		// Publish the device
		appDeviceControl.click(DeviceInfo.PUBLISH_LICENSE);
		wait.until(ExpectedConditions.textToBe(By.xpath(DeviceInfo.PUBLISH_LICENSE_STATE),
				LicenseeAuditStatus.PUBLISHED.getName()));
		
		appDeviceControl.click(DeviceInfo.DELETE);
	}
}
