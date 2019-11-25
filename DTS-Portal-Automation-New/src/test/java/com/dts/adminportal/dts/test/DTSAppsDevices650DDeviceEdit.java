package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AudioRoutes;
import com.dts.adminportal.model.AudioRoutesEdit;
import com.dts.adminportal.model.AudioRoutesInfo;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.AddBrand;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AddPromotion;
import com.dts.adminportal.model.Companies;
import com.dts.adminportal.model.CompanyInfo;
import com.dts.adminportal.model.DeviceEdit;
import com.dts.adminportal.model.DeviceInfo;
import com.dts.adminportal.model.DeviceList;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PromotionInfo;
import com.dts.adminportal.model.PromotionList;
import com.dts.adminportal.model.PromotionPackage;
import com.dts.adminportal.model.RoomModelEdit;
import com.dts.adminportal.model.RoomModelInfo;
import com.dts.adminportal.util.DbUtil;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.SQLiteDbUtil;
import com.dts.adminportal.util.TestData;
import com.ziclix.python.sql.DataHandlerTest;


public class DTSAppsDevices650DDeviceEdit extends BasePage{

	@Override
	protected void initData() {
	}
	
	/*
	 * Verify that the Add new app/device page display proper information
	 */
	@Test
	public void TC650DaDEN_01(){
		appDeviceControl.addLog("ID : TC650DaDEN_01 : Verify that the Add new app/device page display proper information");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized productS" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
				VP: The 650Da Device Edit - New page is displayed and its title is ""Product""
				VP: The ""Type"" combobox contains ""-Select-"", ""App"" and ""Device"" items
				VP: The ""OS"" combobox contains ""Select all that apply"", ""Android"", ""iOS"", ""Windows Phone"", 
				""Windows Desktop"", ""Linux""  and ""Other"" items
				VP: The ""Audio Route"" combobox contains ""-Select-"", ""Over-Ear Headphones (Line-out0)"", 
				""Earbuds (Line-out0)"", ""Earpiece (Line-out0)"", ""Car Audio (Line-out0)"", 
				""External Speakers (Line-out0)"",""Other product (Line-out0)"", ""Attached0 (Portail)"", 
				""Attached1 (Landscape)"", ""Attached2 (Interal Earpiece)""  and ""Combo0 (Duo Audio)"" items
				VP: The DCC Serect in ""Headphone:X Licensing Info"" section is generated and displayed as Read Only.
				VP: The  ""Promo Slots Avaiable"" is ""6"" and the ""Global Promotions"" is ""ON"" 
				by default in ""Featured product Promotions"" module.
				VP: ""Audio Routes"" module includes ""Default Audio Route"" and ""Custom Tuning"" sections"
			4. Click "Save' link without enter or select value of "Type", "Company", "Brand", "Name" 
			and "Init Key Name" fields
				VP: Error messages are displayed which inform that the "Type", "Company", 
				"Brand", "Name" and "Init Key Name" fields are required.

		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: The 650Da Device Edit - New page is displayed and its title is "Product".
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()), true);
		Assert.assertEquals("DTS Inc.", appDeviceControl.getTextByXpath(DeviceEdit.TITLE_DEFAULT));
		//List out all items of "Type" combobox
		ArrayList<String> typeList = appDeviceControl.getAllComboboxOption(DeviceEdit.TYPE);
		/*
		 * VP: The ""Type"" combobox contains ""-Select-"", ""App"" and ""Device"" items
		 */
		Assert.assertTrue(ListUtil.containsAll(typeList, DeviceEdit.types));
		//List out all items of "OS" combobox
		ArrayList<String> options = appDeviceControl.getAllOptionsInDropDown(DeviceEdit.OS);
		/*
		 * VP: The ""OS"" combobox contains ""Select all that apply"", ""Android"", ""iOS"", ""Windows Phone"", 
				""Windows Desktop"", ""Linux""  and ""Other"" items
		 */
		
		Assert.assertTrue(ListUtil.containsAll(options, DeviceEdit.os));
		//List out all items of "Audio Route" combobox in Custom Tuing module.
//		appDeviceControl.clickOptionByIndex(DeviceEdit.AUDIO_ROUTE_TYPE, 1);
		ArrayList<String> audioRoutes = appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE);
//		ArrayList<String> standardAccessories = appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE);
		/*
		 *VP: The ""Audio Route"" combobox contains ""-Select-"", ""Over-Ear Headphones (Line-out0)"", 
				""Earbuds (Line-out0)"", ""Earpiece (Line-out0)"", ""Car Audio (Line-out0)"", 
				""External Speakers (Line-out0)"",""Other product (Line-out0)"", ""Attached0 (Portail)"", 
				""Attached1 (Landscape)"", ""Attached2 (Interal Earpiece)""  and ""Combo0 (Duo Audio)"" items 
		 */
		Assert.assertTrue(ListUtil.containsAll(audioRoutes, DeviceEdit.routes.getNames()));
//		Assert.assertTrue(ListUtil.containsAll(standardAccessories, DeviceEdit.Standard_Accessories));
		/*
		 * VP: The DCC Serect in ""Headphone:X Licensing Info"" section is generated and displayed as Read Only.
		 */
		/*
		 * VP: The  ""Promo Slots Avaiable"" is ""6"" and the ""Global Promotions"" is ""ON"" 
				by default in ""Featured product Promotions"" module.
		 */
		String slots = appDeviceControl.getAtributeValue(DeviceEdit.PROMO_SLOT, "value");
		Assert.assertEquals("6", slots);
		
		String status = appDeviceControl.getTextByXpath(DeviceEdit.GLOBAL_PROMOTIONS_ON);
		Assert.assertEquals(status, "ON");
		/*
		 * VP: ""Audio Routes"" module includes ""Default Audio Route"" and ""Custom Tuning"" sections"
		 */
		Assert.assertTrue(appDeviceControl.checkAllStringPresent(DeviceEdit.Audio_Routes));
		//4. Click "Save' link without enter or select value of "Type", "Company", "Brand", "Name" and "Init Key Name" fields
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: Error messages are displayed which inform that the "Type", "Company", 
		 * "Brand", "Name" and "Init Key Name" fields are required.
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.getErroMessageXpath().get(0)));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.getErroMessageXpath().get(1)));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.getErroMessageXpath().get(2)));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.getErroMessageXpath().get(3)));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.getErroMessageXpath().get(4)));
	}
	/*
	 * Verify that the product name is trimmed for white spaces. 
	 */
	@Test
	public void TC650DaDEN_06(){
		appDeviceControl.addLog("ID : TC650DaDEN_06 : Verify that the product name is trimmed for white spaces");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized productS" privilege.
			1. Log into DTS portal as above DTS admin user
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Fill valid values into all requires fields except "Name" field
			5. Type characters into "Name" field which including some leading and trailing white spaces.
			6. Click "Save" link
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid values into all requires fields
		// 5. Type characters into "Name" field which including some leading and trailing white spaces.
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.appDeviceDraft();
		String deviceName = data.get("name");
		data.put("name", " " + deviceName + " ");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * Verify that The leading and trailing white spaces of "Name" field are trimmed
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), deviceName);
		// Delete device
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	/*
	 * Verify that the invalid "Default Audio Route" file could not be uploaded.
	 */
	@Test
	public void TC650DaDEN_10(){
		appDeviceControl.addLog("ID : TC650DaDEN_10 : Verify that the invalid 'Default Audio Route' file could not be uploaded");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Try to upload invalid audio route tuning file in "Default Audio Route" module
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Try to upload invalid audio route tuning file in "Default Audio Route" module
		appDeviceControl.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName());
		/*
		 * An error message is displayed and the invalid audio route tuning file is not added.
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE));
	}
	
	/*
	 * Verify that the "Custom Tuning" file is optional when adding new device
	 */
	@Test
	public void TC650DaDEN_11(){
		appDeviceControl.addLog("ID : TC650DaDEN_11 : Verify that the 'Custom Tuning' file is optional when adding new device");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Fill, select and upload valid value into all required fields
			5. Do not upload custom tuning file in "Custom Tuning" section
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill, select and upload valid value into all required fields
		// 5. Do not upload custom tuning file in "Custom Tuning" section
		Hashtable<String,String> data = TestData.appDeviceDraft();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * Verify that New device is added successfully
		 */
		Assert.assertTrue(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")));
		// Delete device
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the Default Audio Route file is limited by 1 when adding new device.
	 */
	@Test
	public void TC650DaDEN_12(){
		appDeviceControl.addLog("ID : TC650DaDEN_12 : Verify that the Default Audio Route file is limited by 1 when adding new device.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Select "Choose File" button in "Default Audio Route" section
			5. Upload valid audio route tuning file successfully
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Select "Choose File" button in "Default Audio Route" section
		// 5. Upload valid audio route tuning file successfully
		appDeviceControl.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		/*
		 * Verify that The default Audio Route box is disappeared and user can not upload another audio route file
		 */
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.ADD_AUDIO_ROUTE_CONTAINER));
	}
	
	/*
	 * Verify that the default audio route file is not deleted when clicking "Cancel" in delete confirmation dialog.
	 */
	@Test
	public void TC650DaDEN_13(){
		appDeviceControl.addLog("ID : TC650DaDEN_13 : Verify that the default audio route file is not deleted when clicking 'Cancel' in delete confirmation dialog.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Select "Choose File" button in "Default Audio Route" section
			5. Upload valid audio route tuning file successfully
				VP: The "Choose File" button is disappeared
			6. Click on trashcan icon to delete the uploaded file
				VP: The delete confirmation dialog with "Delete" and "Cancel" button is displayed 
			7. Click "Cancel" button in delete confirmation dialog
				VP: Verify that The default uploaded audio route is not deleted

		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Select "Choose File" button in "Default Audio Route" section
		// 5. Upload valid audio route tuning file successfully
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		appDeviceControl.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		/*
		 * VP: The "Choose File" button is disappeared
		 */
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE));
		// 6. Click on trash-can icon to delete the uploaded file
		appDeviceControl.click(DeviceEdit.DELETE_AUDIO_ROUTE_ICON);
		appDeviceControl.waitForElementClickable(PageHome.BTN_CONFIRMATION_DANGER);
		/*
		 * Verify that Delete confirmation dialog with "Delete" and "Cancel" button is displayed 
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(PageHome.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(appDeviceControl.getTextByXpath(PageHome.BTN_CONFIRMATION_CANCEL), "Cancel");
		//7. Click "Cancel" button in delete confirmation dialog
		appDeviceControl.click(PageHome.BTN_CONFIRMATION_CANCEL);
		/*
		 * Verify that The default uploaded audio route is not deleted
		 */
		Assert.assertNotNull(appDeviceControl.getTextByXpath(DeviceEdit.UPLOADED_DEFAULT_AUDIO_ROUTE));
	}
	/*
	 * Verify that the new Default Audio Route file could be uploaded successfully after deleting the existing file.
	 */
	@Test
	public void TC650DaDEN_16(){
		appDeviceControl.addLog("ID : TC650DaDEN_16 : Verify that the new Default Audio Route file could be uploaded successfully after deleting the existing file.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Select "Choose File" button in "Default Audio Route" section
			5. Upload valid audio route tuning file successfully
				VP: The "Choose File" button is disappeared
			6. Click on trashcan icon to delete the uploaded file successfully
				VP: The file upload box is displayed again
			7. Try to upload another default audio route file
				VP: The new default audio route file is uploaded successfully

		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		// 4. Select "Choose File" button in "Default Audio Route" section
		// 5. Upload valid audio route tuning file successfully
		appDeviceControl.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		/*
		 * VP: The "Choose File" button is disappeared
		 */
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE));
		// 6. Click on trashcan icon to delete the uploaded file successfully
		appDeviceControl.doDelete(DeviceEdit.DELETE_AUDIO_ROUTE_ICON);
		/*
		 * Verify that The file upload box is displayed again
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.ADD_AUDIO_ROUTE_CONTAINER));
		//7. Try to upload another default audio route file
		appDeviceControl.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		/*
		 * VP: The new default audio route file is uploaded successfully
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.UPLOADED_DEFAULT_AUDIO_ROUTE).contains("Default Audio"));
	}
	
	/*
	 * Verify that the custom uploaded tuning file is displayed above the Custom Tuning module with a delete icon after uploaded.
	 */
	@Test
	public void TC650DaDEN_18(){
		appDeviceControl.addLog("ID : TC650DaDEN_18 : Verify that the custom uploaded tuning file is displayed above the Custom Tuning module with a delete icon after uploaded.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Select "Choose File" button in "Custom Tuning" section
			5. Upload valid a tuning file successfully
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		// 4. Select "Choose File" button in "Custom Tuning" section
		// 5. Upload valid a tuning file successfully
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Line_out0.getName()); // LineOut0
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Earbuds.getName()); // Earbuds
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Earbuds_Lineout.getName());
		/*
		 * The custom uploaded tuning file is displayed with a delete icon above the Custom Tuning module
		 */
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Earbuds_Lineout.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
	}
	
	/*
	 * Verify that the custom tuning file could be deleted successfully.
	 */
	@Test
	public void TC650DaDEN_19(){
		appDeviceControl.addLog("ID : TC650DaDEN_19 : Verify that the custom tuning file could be deleted successfully.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Select "Choose File" button in "Custom Tuning" section
			5. Upload valid a tuning file successfully
				VP: The custom uploaded tuning file is displayed with a delete icon above the Custom Tuning module
			6. Click on trashcan icon
				VP: The delete confirmation dialog with "Delete" and "Cancel" buttons are displayed
			7. Click "Delete" button in delete confirmation dialog
				VP: The Custom tuning is deleted successfully.

		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_High.getType());
		// 4. Select "Choose File" button in "Custom Tuning" section
		// 5. Upload valid a tuning file successfully
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Bluetooth0.getName()); // Bluethooth0
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Earbuds.getName()); // Earbuds
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Earbuds_Bluetooth.getName());
		/*
		 * VP: The custom uploaded tuning file is displayed with a delete icon above the Custom Tuning module
		 */
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Earbuds_Bluetooth.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		// 6. Click on trashcan icon
		appDeviceControl.click(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
		appDeviceControl.waitForElementClickable(PageHome.BTN_CONFIRMATION_DANGER);
		/*
		 * The delete confirmation dialog with "Delete" and "Cancel" buttons are displayed
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(PageHome.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(appDeviceControl.getTextByXpath(PageHome.BTN_CONFIRMATION_CANCEL), "Cancel");
		//7. Click "Delete" button in delete confirmation dialog
		appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
		/*
		 * VP: The Custom tuning is deleted successfully.
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.ANOTHER_ROUTE), "");
		 
	}
	/*
	 * Verify that user can download custom tuning file when clicking on tuning link.
	 */
	@Test
	public void TC650DaDEN_21(){
		appDeviceControl.addLog("ID : TC650DaDEN_21 : Verify that user can download custom tuning file when clicking on tuning link.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			
			3. Click "Add App or Device" link
			4. Select "Choose File" button in "Custom Tuning" section
			5. Upload valid a tuning file successfully
			6. Click on uploaded tuning link
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Select "Choose File" button in "Custom Tuning" section
		// 5. Upload valid a tuning file successfully
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_High.getType());
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Bluetooth0.getName()); // Bluethooth0
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.External_Speakers.getName()); // External Speakers
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.External_Speakers_Bluetooth.getName());
		// 6. Click on uploaded tuning link
		Boolean isDownloaded = appDeviceControl.downloadFile(AddEditProductModel.FileUpload.External_Speakers_Bluetooth.getName());
		//boolean isDownloaded = productControl.downloadFile(AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		
		/*
		 * Verify that The download file dialog is displayed and user can download tuning file successfully
		 */
		Assert.assertTrue(isDownloaded);
	}
	
	/*
	 * Verify that the number of "Device Promotions" field displays based on number of "Promo Slot Available".
	 */
	@Test
	public void TC650DaDEN_23()throws InterruptedException{
		appDeviceControl.addLog("ID : TC650DaDEN_23 : Verify that the number of 'Device Promotions' field displays based on number of 'Promo Slot Available'.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized productS" privilege.

			1. Log into DTS portal as above DTS admin user
			2. Navgate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Increase the number of "Promo Slot Available" to 7
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
		// 4. Increase the number of "Promo Slot Available" to 7
		appDeviceControl.editData(DeviceEdit.PROMO_SLOT, "7");
		appDeviceControl.autoTool.send("{ENTER}",false);
		Thread.sleep(5000);
		/*
		 * The "Device Promotions" diplays 7 fields.
		 */
		Assert.assertEquals(promotionControl.getTotalPromotionContainers(DeviceEdit.PROMOTION_CONTAINER), 5);
	}
	
	/*
	 * Verify that items in audio route combobox is removed after uploading the custom tuning file.
	 */
	@Test
	public void TC650DaDEN_26(){
		appDeviceControl.addLog("ID : TC650DaDEN_26 : Verify that items in audio route combobox is removed after uploading the custom tuning file.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Fill valid values into all requires fields
			5. Select an item in audio route combobox
			6. Upload a tuning file for above audio route item successfully
			7. List out the audio route combobox
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid values into all requires fields
		// 5. Select an item in audio route combobox
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached0_Internal_Speakers.getName());
		// 6. Upload a tuning file for above audio route item successfully
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Custom.getName());
		// 7. List out the audio route combobox
		ArrayList<String> options = appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE);
		/*
		 * Verify that The audio route item of uploaded tuning file is removed from audio route combobox
		 */
		Assert.assertTrue(!options.contains(DeviceEdit.routes.Attached0_Internal_Speakers.getClass()));
	}
	
	/*
	 * Verify that the Non partner is not displayed in Company dropdown.
	 */
	@Test
	public void TC650DaDEN_27(){
		appDeviceControl.addLog("ID : TC650DaDEN_27 : Verify that the Non partner is not displayed in Company dropdown");
		/*
		 	Pre-Condition: a new non partner is created successfully

			1. Log into DTS portal as DTS admin
			2. Navgate to "Apps & Devices" page
			3. Click "Add App or Device" link
			VP: The 650Da Device Edit Page - New page is displayed
			4. List out the Company dropdown
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// get all Non-Partner 
		// Navigate Companies
	    appDeviceControl.click(PageHome.LINK_COMPANY);
		appDeviceControl.selectFilterByName(Companies.FILTER, "Non-Partners");
		ArrayList<String> nonPartner = appDeviceControl.getColumsByIndex(1);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. List out the Company dropdown
		appDeviceControl.click(DeviceEdit.COMPANY);
		Assert.assertTrue(appDeviceControl.isNotContainsNonPartner(nonPartner));
	}
	
	/*
	 * Verify that the promotion slots is validated when adding manually
	 */
	@Test
	public void TC650DaDEN_28(){
		appDeviceControl.addLog("ID : TC650DaDEN_28 : Verify that the promotion slots is validated when adding manually");
		/*
		 	1. Log into DTS portal as a DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			VP: Verify that the 650Da Device
			
			 New page is displayed
			4. Fill valid value into all required fields
			5. Add a global promotion ID which is not valid to a promotion slot
			VP: The error message Ã¯Â¿Â½Promotion does not existÃ¯Â¿Â½ is displayed
			6. Add a global promotion ID which is not published yet to a promotion slot
			VP: The error message Ã¯Â¿Â½Promotion is not published yetÃ¯Â¿Â½ is displayed
			7. Add a gl
			obal promotion ID which is published to another promotion slot
			VP: Name of global promotion is displayed next to promotion ID and there is no error message displayed
			8. Add the same global promotion ID which is published to another promotion slot
			VP: The error message Ã¯Â¿Â½Promotion is duplicateÃ¯Â¿Â½ is displayed
			9. Add published device promotion ID which target is assigned to another app/device to another promotion slot
		*/
		/*
		 * PreCondition: Create new global promotion
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		appDeviceControl.click(PageHome.linkAccessories);
		// Click Add product link
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
		// Create 1st published product
		Hashtable<String,String> dataProduct1 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct1);
		// Navigate to Product page
		appDeviceControl.click(PageHome.linkAccessories);
		// Click Add product link
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
		// Create 2nd published product
		Hashtable<String,String> dataProduct2 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct2);
		// Navigate to Product page
		appDeviceControl.click(PageHome.linkAccessories);
		// Click Add product link
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
		// Create 3rd published product
		Hashtable<String,String> dataProduct3 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct3);
		// Navigate to Promotion page
		appDeviceControl.click(PageHome.linkPromotions);
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create 1st Global promotion
		Hashtable<String,String> dataGlobalPromotion1 = TestData.promotionGlobal(dataProduct1.get("brand") + " " + dataProduct1.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataGlobalPromotion1);
		// Get promotion ID
		String promotionID1 = appDeviceControl.getTextByXpath(PromotionInfo.DTS_ID);
		// Navigate to Promotion page
		appDeviceControl.click(PageHome.linkPromotions);
		Assert.assertTrue(promotionControl.checkPromotionExistByName(PromotionList.PROMOTION_TABLE,dataGlobalPromotion1.get("name")));
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create 2nd Global promotion
		Hashtable<String,String> dataGlobalPromotion2 = TestData.promotionGlobal(dataProduct2.get("brand") + " " + dataProduct2.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataGlobalPromotion2);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		// Get promotion ID
		String promotionID2 = appDeviceControl.getTextByXpath(PromotionInfo.DTS_ID);
		// Navigate to App & Devices page
		appDeviceControl.click(PageHome.linkDevice);
		// Click Add app or device link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> dataDevice1 = TestData.appDeviceDraft();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice1);
		// Navigate to Promotion page
		appDeviceControl.click(PageHome.linkPromotions);
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create new Device promotion
		Hashtable<String,String> dataDevicePromotion = TestData.promotionAppDevice(dataProduct3.get("brand") + " " + dataProduct3.get("name"), dataDevice1.get("brand"), dataDevice1.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataDevicePromotion);
		// Publish promotion
		appDeviceControl.waitForElementClickable(PromotionInfo.PUBLISH);
		appDeviceControl.click(PromotionInfo.PUBLISH);
		// Get promotion ID
		String promotionID3 = appDeviceControl.getTextByXpath(PromotionInfo.DTS_ID);
		/*
		 * *******************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.linkDevice);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: Verify that the 650Da Device New page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()), true);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> dataDevice2 = TestData.appDeviceDraft();
		dataDevice2.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice2);
		// 5. Add a global promotion ID which is not valid to a promotion slot
//		ArrayList<PromotionPackage> packages = promotionControl.getPromotionPackage(DeviceEdit.PROMOTION_CONTAINER);
//		appDeviceControl.editData(packages.get(0).Txt_PromotionID, RandomStringUtils.randomAlphanumeric(20));
		appDeviceControl.editData(DeviceEdit.PROMOTION_SLOT_1, RandomStringUtils.randomAlphanumeric(20));
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The error message Ã¯Â¿Â½Promotion does not existÃ¯Â¿Â½ is displayed
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.MESSAGE_PRO_1), PromotionPackage.Messages.Promotion_NotExist.getName());
		// 6. Add a global promotion ID which is not published yet to a promotion slot
		appDeviceControl.editData(DeviceEdit.PROMOTION_SLOT_1, promotionID1);
		/*
		 * VP: The error message Ã¯Â¿Â½Promotion is not published yetÃ¯Â¿Â½ is displayed
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.MESSAGE_PRO_1), PromotionPackage.Messages.Promotion_NotPublished.getName());
//		Assert.assertEquals(appDeviceControl.getElementText(packages.get(0).warningMessage), PromotionPackage.Messages.Promotion_NotPublished.getName());
		// 7. Add a global promotion ID which is published to another promotion slot
		appDeviceControl.editData(DeviceEdit.PROMOTION_SLOT_1, promotionID2);
		/*
		 * VP: Name of global promotion is displayed next to promotion ID and there is no error message displayed
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.PROMOTION_NAME_1).contains(dataGlobalPromotion2.get("name").replace(" ", " - ")));
//		Assert.assertEquals(appDeviceControl.getElementText(packages.get(0).warningMessage), "");
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.MESSAGE_PRO_1), "");
		// 8. Add the same global promotion ID which is published to another promotion slot
		appDeviceControl.editData(DeviceEdit.PROMOTION_SLOT_2, promotionID2);
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The error message Ã¯Â¿Â½Promotion is duplicateÃ¯Â¿Â½ is displayed
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.MESSAGE_PRO_2), PromotionPackage.Messages.Promotion_Duplicate.getName());
		// 9. Add published device promotion ID which target is assigned to another app/device to another promotion slot
		appDeviceControl.editData(DeviceEdit.PROMOTION_SLOT_2, promotionID3);
		/*
		 * The error message Ã¯Â¿Â½Promotion doesn't apply for this deviceÃ¯Â¿Â½ is displayed
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.MESSAGE_PRO_2), PromotionPackage.Messages.Promotion_NotApplyDevice.getName());
		// Delete product
		productControl.deleteAccessorybyName(dataProduct1.get("name"));
		productControl.deleteAccessorybyName(dataProduct2.get("name"));
		productControl.deleteAccessorybyName(dataProduct3.get("name"));
	}
	
	/*
	 * Verify that the limitation of  â€œPromotion Slots Availableâ€� is from 6 to 25
	 */
	@Test
	public void TC650DaDEN_29(){
		appDeviceControl.addLog("ID : TC650DaDEN_29 : Verify that the limitation of  â€œPromotion Slots Availableâ€� is from 0 to 25");
		/*
		 	1. Log into DTS portal as a DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			VP: Verify that the 650Da Device New page is displayed
			4. Fill valid value into all required fields
			5. Try to add negative numbers into Ã¯Â¿Â½Promotion Slots AvailableÃ¯Â¿Â½ field 
			VP: It's unable to add add negative numbers
			6. Set the Ã¯Â¿Â½Promotion Slots AvailableÃ¯Â¿Â½ to 6
			VP: Value 6 could be added
			7. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link
			VP: Verify that new app/device is created successfully
			8. Click Ã¯Â¿Â½EditÃ¯Â¿Â½ link
			9. Try to change the Ã¯Â¿Â½Promotion Slots AvailableÃ¯Â¿Â½ to 26
			VP: It's unable to add value 26
			10. Change the Ã¯Â¿Â½Promotion Slots AvailableÃ¯Â¿Â½ to 25
			VP: value 25 is added successfully and there are 25 promotion slots are displayed
			11. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.linkDevice);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: Verify that the 650Da Device New page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()), true);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.appDeviceDraft();
		data.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// 5. Try to add negative numbers into Ã¯Â¿Â½Promotion Slots AvailableÃ¯Â¿Â½ field 
		appDeviceControl.editData(DeviceEdit.PROMO_SLOT, "-1");
		appDeviceControl.autoTool.send("{ENTER}", false);
		/*
		 * VP: It's unable to add add negative numbers
		 * 
		 */
		String promoSlot = appDeviceControl.getAtributeValue(DeviceEdit.PROMO_SLOT, "value");
		Assert.assertNotEquals(promoSlot, "-1");
		// 6. Set the Ã¯Â¿Â½Promotion Slots AvailableÃ¯Â¿Â½ to 6
		appDeviceControl.editData(DeviceEdit.PROMO_SLOT, "6");
		appDeviceControl.autoTool.send("{ENTER}", false);
		/*
		 * VP: Value 6 could be added
		 */
		Assert.assertEquals(promotionControl.getTotalPromotionContainers(DeviceEdit.PROMOTION_CONTAINER), 4);
		// 7. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link
		appDeviceControl.click(DeviceEdit.SAVE);
		// Check if PDPP-1186 found
		if(appDeviceControl.checkMessageDisplay("Internal Server Error")){
			appDeviceControl.addErrorLog("PDPP-1186 - 650Db Product Edit: Error message \"Internal Server Error\" is displayed when saving app/device with the Promotions Slots Available is set to 0");
			Assert.assertTrue(false);
		}
		/*
		 * VP: Verify that new app/device is created successfully
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), data.get("name"));
		// 8. Click Ã¯Â¿Â½EditÃ¯Â¿Â½ link
		appDeviceControl.click(DeviceInfo.EDIT);
		// 9. Try to change the Ã¯Â¿Â½Promotion Slots AvailableÃ¯Â¿Â½ to 26
		appDeviceControl.editData(DeviceEdit.PROMO_SLOT, "26");
		appDeviceControl.autoTool.send("{ENTER}", false);
		/*
		 * VP: It's unable to add value 26
		 */
		Assert.assertEquals(promotionControl.getTotalPromotionContainers(DeviceEdit.PROMOTION_CONTAINER),1);
		//10. Change the Ã¯Â¿Â½Promotion Slots AvailableÃ¯Â¿Â½ to 25
		appDeviceControl.editData(DeviceEdit.PROMO_SLOT, "25");
		appDeviceControl.autoTool.send("{ENTER}", false);
		/*
		 * VP: value 25 is added successfully and there are 25 promotion slots are displayed  
		 */
		Assert.assertEquals(promotionControl.getTotalPromotionContainers(DeviceEdit.PROMOTION_CONTAINER),23);
		//11. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link
		appDeviceControl.click(DeviceEdit.SAVE);
		appDeviceControl.click(DeviceInfo.PUBLISH );
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		
	}
	
	/*
	 * Verify that any individual brands entered will become lost after switching from "Only the following brands" to "All brands" in Headphone Brands
	 */
	@Test
	public void TC650DaDEN_30(){
		appDeviceControl.addLog("ID : TC650DaDEN_30 : Verify that any individual brands entered will become lost after switching from 'Only the following brands' to 'All brands' in Headphone Brands");
		/*
		 	1. Log into DTS portal as a DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Fill valid value into all required fields
			5. Select "Only the following brands"
			6. List brands
			7. Select "All brands"
			VP: Any individual brands entered will become lost
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.linkDevice);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.appDeviceDraft();
		data.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// 5. Select "Only the following brands"
		appDeviceControl.click(DeviceEdit.ONLY_BRANDS);
		// 6. List brands
		appDeviceControl.editData(DeviceEdit.BRAND_FILTER,PARTNER_BRAND_NAME_1);
		WebElement brand_filter = driver.findElement(By.xpath(DeviceEdit.BRAND_FILTER));
		brand_filter.sendKeys(Keys.PAGE_DOWN);
		appDeviceControl.autoTool.send("{ENTER}",false);
		// 7. Select "All brands"
		appDeviceControl.click(DeviceEdit.ALL_BRANDS);
		// VP: Any individual brands entered will become lost after save
		appDeviceControl.click(DeviceEdit.SAVE);
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.HEADPHONE_BRAND_LABEL_TEXT));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that "Only the following brands" option and "All Brands" option are validated in Headphone Brands
	 */
	@Test
	public void TC650DaDEN_31(){
		appDeviceControl.addLog("ID : TC650DaDEN_31 : Verify that 'Only the following brands' option and 'All Brands' option are validated in Headphone Brands");
		/*
		 	1. Log into DTS portal as a DTS admin	
			2. Navigate to "Apps & Devices" page	
			3. Click "Add App or Device" link	
			4. Fill valid value into all required fields	
			5. Select "Only the following brands" option in "Headphone Brands" section.	
			6. Click "Save" link	
			VP: This app/device cannot save and show error "Brand is required" in "Headphone Brands" section.
			7. Select "All brands in the DTS catalog" option in "Headphone Brands" section.	
			8. Click "Save" link	
			VP: This app/device can save successfully.
			9. Click "Edit Version" link	
			10. Select "Only the following brands" option in "Headphone Brands" section again	
			11.Enter non-existent brand name in "Enter a brand name" field	
			12. Click "Save" link	
			VP: This app/device cannot save and show error "Brand is required" in "Headphone Brands" section.
			13.Enter valid brand names in "Enter a brand name" field	
			14. Choose the brand names in auto suggest field	
			15. Click â€œSaveâ€� link	
			16.Click  "Edit Version" link	
			17. Select "All brands in the DTS catalog" option in "Headphone Brands" section.	
			VP: The brand name list is disabled in "Headphone Brands" section.

		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.linkDevice);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.appDevicePublish();
		data.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// 5. Select "Only the following brands"
		appDeviceControl.click(DeviceEdit.ONLY_BRANDS);
		// 6. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: This app/device cannot save and show error "Brand is required" in "Headphone Brands" section.
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.HEADPHONE_BRAND_REQUIRE));
		// 7. Select "All brands in the DTS catalog" option in "Headphone Brands" section.
		appDeviceControl.click(DeviceEdit.ALL_BRANDS);
		// 8. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: This app/device can save successfully. 
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.PRODUCT_INFORMATION));
		//9. Click "Edit Version" link
		appDeviceControl.click(DeviceInfo.EDIT);
		//10. Select "Only the following brands" option in "Headphone Brands" section again	
		appDeviceControl.click(DeviceEdit.ONLY_BRANDS);
		//11.Enter non-existent brand name in "Enter a brand name" field
		appDeviceControl.editData(DeviceEdit.BRAND_FILTER,"Non-Existent-Brand");
		//12. Click "Save" link	
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: This app/device cannot save and show error "Brand is required" in "Headphone Brands" section.
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.HEADPHONE_BRAND_REQUIRE));
		//13.Enter valid brand names in "Enter a brand name" field	
//		appDeviceControl.editData(DeviceEdit.BRAND_FILTER,PARTNER_BRAND_NAME_1);
//		//14. Choose the brand names in auto suggest field	
//		WebElement brand_filter = driver.findElement(By.xpath(DeviceEdit.BRAND_FILTER));
//		brand_filter.sendKeys(Keys.PAGE_DOWN);
//		appDeviceControl.autoTool.send("{ENTER}",false);
		appDeviceControl.inputBrand(DeviceEdit.BRAND_FILTER, PARTNER_BRAND_NAME_1);
		//15. Click â€œSaveâ€� link	
		appDeviceControl.click(DeviceEdit.SAVE);
		//16.Click  "Edit Version" link	
		appDeviceControl.waitForElementClickable(DeviceInfo.EDIT);
		appDeviceControl.click(DeviceInfo.EDIT);
		//17. Select "All brands in the DTS catalog" option in "Headphone Brands" section.	
		appDeviceControl.click(DeviceEdit.ALL_BRANDS);
		/*
		 * VP: The brand name list is disabled in "Headphone Brands" section.
		 */
		appDeviceControl.click(DeviceEdit.BRAND_ICON_TRASH);
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.BRAND_ICON_TRASH));
		//17. Select "Only the following brands" option in "Headphone Brands" section.
		appDeviceControl.click(DeviceEdit.ONLY_BRANDS);
		/*
		 * VP: The brands name list is displayed and not disable in "Headphone Brands" section
		 */
		appDeviceControl.click(DeviceEdit.BRAND_ICON_TRASH);
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.BRAND_ICON_TRASH));
		
		// Delete device
		appDeviceControl.click(DeviceEdit.CANCEL);
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		
		
	}
	
	/*
	 * Verify that new promo slot is displayed correctly
	 */
	@Test
	public void TC650DaDEN_32(){
		appDeviceControl.addLog("ID : TC650DaDEN_32 : Verify that new promo slot is displayed correctly");
		/*
		 	1. Log into DTS portal as a DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			VP: The default promo sot is 6
			4. Change the â€œPromotion Slots Availableâ€� to 7
			VP: The new promo slot is added with label, input field and ""Delete"" link
			VP: for a promotion user may choose any product from the DTS catalog, 
			even if  it's not listed in the ""headphone brands"" below
		*/
		//Pre-Condition: There is at least one published Global promotion.
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Create a published product
		appDeviceControl.click(PageHome.linkAccessories);
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> productdata = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(),productdata);
		// Create a published Global promotion
		appDeviceControl.click(PageHome.linkPromotions);
		appDeviceControl.click(PromotionList.ADD_PROMO);
		Hashtable<String,String> globalpromo = TestData.promotionGlobal(productdata.get("brand")+" "+productdata.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(),globalpromo);
		appDeviceControl.click(PromotionInfo.PUBLISH);
		String DTS_ID = appDeviceControl.getTextByXpath(PromotionInfo.DTS_ID);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		appDeviceControl.waitElement(DeviceEdit.PRODUCT_TYPE);
		// VP: The default promo sot is 6
		String default_slot = appDeviceControl.getAttributesOfElement(DeviceEdit.PROMO_SLOT,"value");
		System.out.println(default_slot);
		Assert.assertEquals(default_slot,"6");
		Assert.assertEquals(promotionControl.getTotalPromotionContainers(DeviceEdit.PROMOTION_CONTAINER),6);
		// 4. Change the â€œPromotion Slots Availableâ€� to 7
		appDeviceControl.editData(DeviceEdit.PROMO_SLOT,"7");
		// VP: The new promo slot is added with label, input field and ""Delete"" link
		appDeviceControl.click(DeviceEdit.PROMOTION_1);
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.LABEL_7));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.PROMOTION_7));
//		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_7));
//		 VP: for a promotion user may choose any product from the DTS catalog, 
//		even if  it's not listed in the ""headphone brands"" below
		appDeviceControl.editData(DeviceEdit.PROMOTION_1,DTS_ID);
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.PROMOTION_ERROR));
		
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.deleteAccessorybyName(productdata.get("name"));
	}
	
	/*
	 * Verify that DTS user can select one or more options in "Multi-Channel Room Models" section
	 */
	@Test
	public void abc(){
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
				appDeviceControl.click(PageHome.linkDevice);
				// 3. Click "Add App or Device" link
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// VP: The default promo sot is 6
				String default_slot = appDeviceControl.getAttributesOfElement(DeviceEdit.PROMO_SLOT,"value");
				System.out.println(default_slot);
				Assert.assertEquals(default_slot,"6");
	}
	@Test
	public void TC650DaDEN_34(){
		appDeviceControl.addLog("ID : TC650DaDEN_34 : Verify that DTS user can select one or more options in 'Multi-Channel Room Models' section");
		/*
		 	Pre-Condition: DTS user has "Add and manage Apps and Devices" privilege
			1. Log into DTS portal as a DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Select options in "Multi-Channel Room Models" section
			VP: DTS user can select one or more options in this section
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.linkDevice);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Select options in "Multi-Channel Room Models" section
		// VP: DTS user can select one or more options in this section
		// Room0 is selected by default
		WebElement mcRoom0 = driver.findElement(By.xpath(DeviceEdit.CHECK_BOX_MCROOM_1ST));
		Assert.assertTrue(mcRoom0.isSelected());
		// Verify user can select 1 room model
		appDeviceControl.click(DeviceEdit.SAVE);
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.ROOM_MODEL_REQUIRE));
		// Verify user can select 2 room models
		appDeviceControl.click(DeviceEdit.CHECK_BOX_MCROOM_2ND);
		appDeviceControl.click(DeviceEdit.SAVE);
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.ROOM_MODEL_REQUIRE));
		// Verify user can select 3 room models
		appDeviceControl.click(DeviceEdit.CHECK_BOX_MCROOM_3TH);
		appDeviceControl.click(DeviceEdit.SAVE);
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.ROOM_MODEL_REQUIRE));
	}
	
	/*
	 * Verify that DTS user can select one or more options in "Headphone Image Sizes" section
	 */
	@Test
	public void TC650DaDEN_35(){
		appDeviceControl.addLog("ID : TC650DaDEN_35 : Verify that DTS user can select one or more options in 'Headphone Image Sizes' section");
		/*
		 	Pre-Condition: DTS user has "Add and manage Apps and Devices" privilege
			1. Log into DTS portal as a DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Select options in "Headphone Image Sizes" section
			VP: DTS user can select one or more options in this section
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.linkDevice);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Select options in "Headphone Image Sizes" section
		// VP: DTS user can select one or more options in this section
		// All image sizes are selected by default
		appDeviceControl.click(DeviceEdit.SAVE);
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.IMAGE_SIZE_REQUIRE));
		// Verify user can select 2 image sizes
		appDeviceControl.click(DeviceEdit.LARGE_IMAGE);
		appDeviceControl.click(DeviceEdit.SAVE);
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.IMAGE_SIZE_REQUIRE));
		// Verify user can select 1 image size
		appDeviceControl.click(DeviceEdit.SMALL_IMAGE);
		appDeviceControl.click(DeviceEdit.SAVE);
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.IMAGE_SIZE_REQUIRE));
		// Verifying can't save if user didn't select any image size
		appDeviceControl.click(DeviceEdit.MEDIUM_IMAGE);
		appDeviceControl.click(DeviceEdit.SAVE);
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.IMAGE_SIZE_REQUIRE));
	}
	
	/*
	 * Verify that when user attempts to save the record, at least one option on "Multi-Channel Room Models" and "Headphone Image Sizes" must be selected
	 */
	@Test
	public void TC650DaDEN_36(){
		appDeviceControl.addLog("ID : TC650DaDEN_36 : Verify that when user attempts to save the record, at least one option on 'Multi-Channel Room Models' and 'Headphone Image Sizes' must be selected");
		/*
		 	Pre-Condition: DTS user has "Add and manage Apps and Devices" privilege
			1. Log into DTS portal as a DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Fill valid value into all required fields
			5. Select option in "Headphone Image Sizes" and do not select options in "Multi-Channel Room Models" 
			VP : "Headphone Image Sizes" is selected all by default
			6. Click "Save" link
			VP: a warning message should be displayed to notify user must be selected at least one option 
			7. Select option in "Multi-Channel Room Models"  and do not select options in "Headphone Image Sizes"
			8. Click "Save" link
			VP: a warning message should be displayed to notify user must be selected at least one option 
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.linkDevice);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.appDeviceDraft();
		data.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// 5. Select option in "Headphone Image Sizes" and do not select options in "Multi-Channel Room Models" 
		// Due to all options in "Headphone Image Sizes"
		/*
		 * VP : "Headphone Image Sizes" is selected all by default
		 */
		WebElement large_image = driver.findElement(By.xpath(DeviceEdit.LARGE_IMAGE));
		WebElement medium_image = driver.findElement(By.xpath(DeviceEdit.MEDIUM_IMAGE));
		WebElement small_image = driver.findElement(By.xpath(DeviceEdit.SMALL_IMAGE));
		Assert.assertTrue(large_image.isSelected());
		Assert.assertTrue(medium_image.isSelected());
		Assert.assertTrue(small_image.isSelected());
		/*
		 *  VP:  Room0 of "Multi-Channel Room Models" are selected by default
		 */
		WebElement mcRoom0 = driver.findElement(By.xpath(DeviceEdit.CHECK_BOX_MCROOM_1ST));
		Assert.assertTrue(mcRoom0.isSelected());
		// Unselect room models
		appDeviceControl.uncheckACheckbox(DeviceEdit.CHECK_BOX_MCROOM_1ST);
		appDeviceControl.uncheckACheckbox(DeviceEdit.CHECK_BOX_MCROOM_2ND);
		appDeviceControl.uncheckACheckbox(DeviceEdit.CHECK_BOX_MCROOM_3TH);
		// 6. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: a warning message should be displayed to notify user must be selected at least one option 
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.ROOM_MODEL_REQUIRE));
		// 7. Select option in "Multi-Channel Room Models"  and do not select options in "Headphone Image Sizes"
		appDeviceControl.click(DeviceEdit.CHECK_BOX_MCROOM_1ST);
		appDeviceControl.uncheckACheckbox(DeviceEdit.LARGE_IMAGE);
		appDeviceControl.uncheckACheckbox(DeviceEdit.MEDIUM_IMAGE);
		appDeviceControl.uncheckACheckbox(DeviceEdit.SMALL_IMAGE);
		// 8. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 *  VP: a warning message should be displayed to notify user must be selected at least one option 
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.IMAGE_SIZE_REQUIRE));
		
		
	}
	
	/*
	 * Verify that "Headphone Brands" section can create and edit with "Only the following brands" options.
	 */
	@Test
	public void TC650DaDEN_37(){
		appDeviceControl.addLog("ID : TC650DaDEN_37 : Verify that 'Headphone Brands' section can create and edit with 'Only the following brands' options");
		/*
		 	1. Log into DTS portal as a DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			VP: Verify that the 650Da Device New page is displayed
			4. Fill valid value into all required fields
			5. Select "Only the following brands" option in "Headphone Brands" section
			6.Enter valid brand names in "Enter a brand name" field
			VP: This field shows a popup with list with matches.
			7. Choose the brand names in auto suggest field
			VP: The brand names is displayed in "Headphone Brands" section.
			8. Click "Save" link
			VP: Verify that the 640D Device Details Page is displayed
			9.Click  "Edit Version" link
			VP: Verify that the 650Da Device New page is displayed
			10. Delete several  brand name in "Headphone Brands" section.
			VP: The brand names are deleted in "Headphone Brands" section.
			11. Click "Save" link
			VP: This app/device can save successfully
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.linkDevice);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.appDeviceDraft();
		data.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// 5. Select "Only the following brands"
		appDeviceControl.click(DeviceEdit.ONLY_BRANDS);
		// 6.Enter valid brand names in "Enter a brand name" field
//		appDeviceControl.editData(DeviceEdit.BRAND_FILTER,PARTNER_BRAND_NAME_1);
//		// 7. Choose the brand names in auto suggest field
//		WebElement brand_filter = driver.findElement(By.xpath(DeviceEdit.BRAND_FILTER));
//		brand_filter.sendKeys(Keys.PAGE_DOWN);
//		appDeviceControl.autoTool.send("{ENTER}",false);
		appDeviceControl.inputBrand(DeviceEdit.BRAND_FILTER ,PARTNER_BRAND_NAME_1);
		// VP: The brand names is displayed in "Headphone Brands" section.
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.BRAND_LABEL));
		// 8. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: Verify that the 640D Device Details Page is displayed
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")),true);
		// 9.Click  "Edit Version" link
		appDeviceControl.click(DeviceInfo.EDIT);
		// VP: Verify that the 650Da Device New page is displayed
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
		// 10. Delete several  brand name in "Headphone Brands" section.
		appDeviceControl.click(DeviceEdit.BRAND_ICON_TRASH);
		// VP: The brand names are deleted in "Headphone Brands" section.
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.BRAND_ICON_TRASH));
		appDeviceControl.click(DeviceEdit.ALL_BRANDS);
		// 11. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: This app/device can save successfully
		Assert.assertTrue(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	

	/*
	 * Verify that the drop down buttom in "Audio Route" section show only published "Other Audio Routes" item.
	 */
	@Test
	public void TC650DaDEN_39(){
		appDeviceControl.addLog("ID : TC650DaDEN_39: Verify that the drop down buttom in 'Audio Route' section show only published 'Other Audio Routes' item.");
		/*
		 * Pre-Condition : There is at least one published Audio Route in "Other Audio Routes" section (such as "Attached0 - Internal Speakers (Default)" )
			1. Navigate to DTS portal.
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio" page.
			4. Select an published audio route link in "Other Audio Routes" section. (such as: "Attached0 - Internal Speakers (Default)" option)
			VP: The "Publication Status" is displayed "Published".
			5. Navigate to â€œApps & Devicesâ€� page
			6. Click on "Add App & Device" link.
			7. Click on drop down button in "Audio Route" section.
			VP: The "Attached0 - Internal Speakers (Default)" audio route is listed in drop down.
			8. Navigate to "Audio" page
			9. Select "Attached0 - Internal Speakers (Default)" link in "Other Audio Routes" section
			10. Click "Edit Version" link
			11. Input value "Internal Speakers (Default) Update" into "Name" field
			12. Click "Save" link
			VP: The "Publication Status" is displayed "Draft".
			13. Repeat step 5 to step 7
			VP: The "Attached0 - Internal Speakers (Default)" audio route is listed in drop down.
			14. Navigate to "Audio" page
			15. Select "Attached0 - Internal Speakers (Default) Update" link in "Other Audio Routes" section
			16. Publish above "Attached0 - Internal Speakers (Default) Update" audio route successfully
			VP: The "Publication Status" is displayed "Published".
			17. Repeat from step 5 to step 7
			VP: The "Attached0 - Internal Speakers (Default) Update" audio route is listed in drop down.

		 */
		// Pre-Condition : There is at least one published Audio Route in "Other Audio Routes" section (such as "Attached0 - Internal Speakers (Default)" )
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio" page.
		appDeviceControl.click(PageHome.linkAudioroutes);
		// Publish "Attached0 - Internal Speakers (Default)" Audio Route
		appDeviceControl.click(AudioRoutes.LINK_OTHER_AUDIO);
		audioControl.publishAudioRoute(AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Fallback.getName());
		// 4. Select an published audio route link in "Other Audio Routes" section. (such as: "Attached0 - Internal Speakers (Default)" option)
		/*
		 * VP: The "Publication Status" is displayed "Published".
		 */
		String publish_status = audioControl.getTextByXpath(AudioRoutesInfo.PUBLISH_STATUS);
		Assert.assertTrue(publish_status.equals("Published"));
		// 5. Navigate to â€œApps & Devicesâ€� page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 6. Click on "Add App & Device" link.
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 7. Click on drop down button in "Audio Route" section.
		appDeviceControl.click(DeviceEdit.AUDIO_ROUTE_TYPE);
		/*
		 * VP: The "Attached0 - Internal Speakers (Default)" audio route is listed in drop down.
		 */
		ArrayList<String> audiotype = appDeviceControl.getOptionList(DeviceEdit.AUDIO_ROUTE_TYPE);
		Assert.assertTrue(ListUtil.checkListContain(audiotype,AudioRoutes.Other_Routes.Attached0_Internal_Speakers.getName()));
		// 8. Navigate to "Audio" page
		appDeviceControl.click(PageHome.linkAudioroutes);
		// 9. Select "Attached0 - Internal Speakers (Default)" link in "Other Audio Routes" section
		appDeviceControl.click(AudioRoutes.LINK_OTHER_AUDIO);
		// 10. Click "Edit Version" link
		audioControl.editVersion();
		// 11. Input value "Internal Speakers (Default) Update" into "Name" field
		appDeviceControl.editData(AudioRoutesEdit.DISPLAY_NAME,AudioRoutesEdit.OTHER_ROUTE_DISPLAYNAME.Speakers_Default.getName() +" Update");
		// 12. Click "Save" link
		appDeviceControl.click(AudioRoutesEdit.SAVE);
		/*
		 * VP: The "Publication Status" is displayed "Draft".
		 */
		String publish_status2 = audioControl.getTextByXpath(AudioRoutesInfo.PUBLISH_STATUS);
		Assert.assertTrue(publish_status2.equals("Draft"));
		// 13. Repeat step 5 to step 7
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		appDeviceControl.click(DeviceEdit.AUDIO_ROUTE_TYPE);
		/*
		 * VP: The "Attached0 - Internal Speakers (Default)" audio route is listed in drop down.
		 */
		ArrayList<String> audiotype2 = appDeviceControl.getOptionList(DeviceEdit.AUDIO_ROUTE_TYPE);
		Assert.assertTrue(ListUtil.checkListContain(audiotype2,AudioRoutes.Other_Routes.Attached0_Internal_Speakers.getName()));
		// 14. Navigate to "Audio" page
		appDeviceControl.click(PageHome.linkAudioroutes);
		// 15. Select "Attached0 - Internal Speakers (Default) Update" link in "Other Audio Routes" section
		appDeviceControl.click(AudioRoutes.LINK_OTHER_AUDIO);
		// 16. Publish above "Attached0 - Internal Speakers (Default) Update" audio route successfully
		appDeviceControl.click(AudioRoutesInfo.PUBLISH);
		/*
		 * VP: The "Publication Status" is displayed "Published".
		 */
		String publish_status3 = audioControl.getTextByXpath(AudioRoutesInfo.PUBLISH_STATUS);
		Assert.assertTrue(publish_status3.equals("Published"));
		// 17. Repeat from step 5 to step 7
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		appDeviceControl.click(DeviceEdit.AUDIO_ROUTE_TYPE);
		/*
		 * VP: The "Attached0 - Internal Speakers (Default) Update" audio route is listed in drop down.
		 */
		ArrayList<String> audiotype3 = appDeviceControl.getOptionList(DeviceEdit.AUDIO_ROUTE_TYPE);
		Assert.assertTrue(ListUtil.checkListContain(audiotype3,AudioRoutes.Other_Routes.Attached0_Internal_Speakers.getName()+" Update"));
		
		// Set default name back
		audioControl.click(PageHome.linkAudioroutes);
		audioControl.click(AudioRoutes.LINK_OTHER_AUDIO);
		audioControl.click(AudioRoutesInfo.EDIT);
		audioControl.waitForElementClickable(AudioRoutesEdit.POPUP_OK);
		audioControl.click(AudioRoutesEdit.POPUP_OK);
		audioControl.editData(AudioRoutesEdit.DISPLAY_NAME,AudioRoutesEdit.OTHER_ROUTE_DISPLAYNAME.Speakers_Default.getName());
		audioControl.click(AudioRoutesEdit.SAVE);
		audioControl.click(AudioRoutesInfo.PUBLISH);
	}
	
	/*
	 * Verify that Custom Tuning is validated in "Audio Routes" section.
	 */
	@Test
	public void TC650DaDEN_40(){
		appDeviceControl.addLog("ID : TC650DaDEN_40: Verify that Custom Tuning is validated in 'Audio Routes' section.");
	
		/*
		 * 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully.
			3. Navigate to "Apps & Devices" page
			4. Click "Add App or Device" link
			5. Fill all valid values into required fields
			6. Choose an audio route item in Audio Route dropdown list 
			7. Click "Save" link
			VP: A error message is dispalyed like  error message "Custom Tunning File is required".
			8. Click "Choose file" link and upload a tuning file successfully 
			9. Click "Save" link
			VP: The tuning file is uploaded successfully and App/Device is created successfully.
		 */
		
		//2. Log into DTS portal as a DTS user successfully.
	    loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//4. Click "Add App or Device" link
		audioControl.click(DeviceList.CREATE_NEW_DEVICE);
		//5. Fill all valid values into required fields
		Hashtable<String,String> data = TestData.appDeviceDraft();
		data.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		//6. Choose an audio route item in Audio Route dropdown list 
		audioControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached1_Internal_Speakers_Off.getName());
		//7. Click "Save" link
		audioControl.click(DeviceEdit.SAVE);
		/*
		 * VP: A error message is dispalyed like  error message "Custom Tunning File is required".
		 */
		Assert.assertEquals(audioControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS1),DeviceEdit.requires.Custom_Tunning_File_is_required.getName());
		//8. Click "Choose file" link and upload a tuning file successfully 
		audioControl.uploadFileTuning(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached1InternalSpeakersOff_Fallback.getName());
		//9. Click "Save" link
		audioControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The tuning file is uploaded successfully and App/Device is created successfully.
		 */
		Assert.assertEquals(driver.findElement(By.partialLinkText(DeviceEdit.routes.Attached1_Internal_Speakers_Off.getName())).getText(),DeviceEdit.routes.Attached1_Internal_Speakers_Off.getName());
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the "Multi-Channel Room Models" section shows only published Room Models in "Audio" page.
	 */
	@Test
	public void TC650DaDEN_41(){
		appDeviceControl.addLog("ID : TC650DaDEN_41: Verify that the 'Multi-Channel Room Models' section shows only published Room Models in 'Audio' page.");
		
		/*
		 * Pre-Condition : There is at least one published Audio Route in "Multi-Channel Room Models" section
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully.
			3. Navigate to "Apps & Devices" page
			4. Click "Add App or Device" link
			VP: The "Multi-Channel Room Models" section shows only published Room Models in "Audio" page.

		 */
		// Pre-Condition : There is at least one published Audio Route in "Multi-Channel Room Models" section
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Publish a McRoom
		appDeviceControl.click(PageHome.linkAudioroutes);
		appDeviceControl.click(AudioRoutes.MCROOM0);
		audioControl.publishAudioRoute(AddEditProductModel.FileUpload.McRoom0_RC1_5p1_7p1.getName());
		String published_mcroom = audioControl.getTextByXpath(RoomModelInfo.DISPLAY_NAME);
		// Draft McRoom 
		appDeviceControl.click(PageHome.linkAudioroutes);
		appDeviceControl.click(AudioRoutes.MCROOM1);
		audioControl.editVersion();
		audioControl.editData(RoomModelEdit.DISPLAY_NAME,"Published version");
		audioControl.click(RoomModelEdit.SAVE);
		audioControl.publishAudioRoute(AddEditProductModel.FileUpload.McRoom0_RC1_5p1_7p1.getName());
		String published_version = audioControl.getTextByXpath(RoomModelInfo.DISPLAY_NAME);
		System.out.println(published_version);
		audioControl.editVersion();
		audioControl.editData(RoomModelEdit.DISPLAY_NAME,"Draft version");
		audioControl.click(RoomModelEdit.SAVE);
		// 3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 4. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: The "Multi-Channel Room Models" section shows only published Room Models in "Audio" page.
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.MCROOM0),"McRoom0("+published_mcroom+")");
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.MCROOM1),"McRoom1("+published_version+")");
	}
	
	
	/*
	 *Verify that DTS user can edit the list order of Global Device Promotions
	 */
	@Test
	public void TC650DaDEN_42(){
		appDeviceControl.addLog("ID : TC650DaDEN_42: Verify that DTS user can edit the list order of Global Device Promotions");
		
		/*
		1. Navigate to DTS portal.	
		2. Log into DTS portal as a DTS user successfully.	
		3. Navigate to "Apps & Devices" page	
		4. Click "Add App or Device" link	
		5. Fill all valid values into required fields	
		6. Upload an valid default audio route file	
		7. Input "6" in Promotion Device Available	
		8. Turn on in Global Promotions	
		9. Click "Save" link	
		10. Click "Edit" link	
		11. Change the order of list promotion slots by drag and drop	
		VP: user can drag and drop promotion slots to order normally
		12. Click "Save" link	
		VP: The device promotion is arranged after dagging and dropping
		 */
		
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		4. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		5. Fill all valid values into required fields
//		6. Upload an valid default audio route file
//		7. Input "6" in Promotion Device Available
//		8. Turn on in Global Promotions
//		9. Click "Save" link
		Hashtable<String,String> data = TestData.appDevicePublish();
		data.remove("global promotion");
		data.put("global promotion", "ON");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),data);
		String device_name = appDeviceControl.getTextByXpath(DeviceInfo.NAME);
//		10. Click "Edit" link
		appDeviceControl.editVersion();
//		11. Change the order of list promotion slots by drag and drop
		// Get list before Drad & Drop
		ArrayList<String> before_list1 = appDeviceControl.getListOrder();
//		appDeviceControl.dragAndDrop(appDeviceControl.getPromoSlot(1),appDeviceControl.getPromoSlot(2));
		appDeviceControl.dragDropPromo(1,2);
		ArrayList<String> after_list1 = appDeviceControl.getListOrder();
//		VP: user can drag and drop promotion slots to order normally
		Assert.assertTrue(appDeviceControl.isPromotionOrder(1,2,before_list1,after_list1));
//		12. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The device promotion is arranged after dagging and dropping
		 */
		ArrayList<String> listPromo = appDeviceControl.getListPromo("Draft");
		Assert.assertTrue(appDeviceControl.checkListOrder(listPromo,after_list1));
		
		// Delete device
		appDeviceControl.selectADeviceByName(device_name);
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that DTS user can edit the list order of Apps or Devices Promotions
	 */
	@Test
	public void TC650DaDEN_43() throws InterruptedException{
		Reporter.log("Verify that DTS user can edit the list order of Apps or Devices Promotions");
		
		/*
		 	1. Navigate to DTS portal.	
			2. Log into DTS portal as a DTS user successfully.	
			3. Navigate to "Apps & Devices" page	
			4. Click "Add App or Device" link	
			5. Fill all valid values into required fields 	
			6. Input "6" in Promotion Device Available	
			7. Input some valid Apps/Devices Promotions ID which are published and are applied for this app and device	
			8. Turn off in Global Promotions	
			9. Click "Save" link	
			10. Click "Edit" link	
			11. Change the order of list promotion slots by drag and drop	
			VP: user can drag and drop promotion slots to order normally
			12. Click "Save" link	
			VP: The device promotion is arranged after dagging and dropping

		*/
		/*
		 * PreCondition: Create new Device promotion
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to App & Devices page
		appDeviceControl.click(PageHome.linkDevice);
		// Click Add app or device link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> dataDevice = TestData.appDevicePublish();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice);
		String deviceName = appDeviceControl.getTextByXpath(DeviceInfo.NAME);
		// Navigate to Product page
		appDeviceControl.click(PageHome.linkAccessories);
		// Click Add product link
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
		// Create 1st published product
		Hashtable<String,String> dataProduct1 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct1);
		// Navigate to Product page
		appDeviceControl.click(PageHome.linkAccessories);
		// Click Add product link
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
		// Create 2nd published product
		Hashtable<String,String> dataProduct2 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct2);
		// Navigate to Product page
		appDeviceControl.click(PageHome.linkAccessories);
		// Click Add product link
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
		// Create 3rd published product
		Hashtable<String,String> dataProduct3 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct3);
		// Navigate to Promotion page
		appDeviceControl.click(PageHome.linkPromotions);
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create 1st Device promotion
		Hashtable<String,String> dataDevicePromotion1 = TestData.promotionAppDevice(dataProduct1.get("brand") + " " + dataProduct1.get("name"),PARTNER_BRAND_NAME_1,deviceName);
		promotionControl.addPromotion(AddPromotion.getHash(), dataDevicePromotion1);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		// Navigate to Promotion page
		appDeviceControl.click(PageHome.linkPromotions);
		Assert.assertTrue(promotionControl.checkPromotionExistByName(PromotionList.PROMOTION_TABLE,dataDevicePromotion1.get("name")));
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create 2nd Device promotion
		Hashtable<String,String> dataDevicePromotion2 = TestData.promotionAppDevice(dataProduct2.get("brand") + " " + dataProduct2.get("name"),PARTNER_BRAND_NAME_1,deviceName);
		promotionControl.addPromotion(AddPromotion.getHash(), dataDevicePromotion2);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		// Navigate to Promotion page
		appDeviceControl.click(PageHome.linkPromotions);
		Assert.assertTrue(promotionControl.checkPromotionExistByName(PromotionList.PROMOTION_TABLE,dataDevicePromotion2.get("name")));
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create 3rd Device promotion
		Hashtable<String,String> dataDevicePromotion3 = TestData.promotionAppDevice(dataProduct3.get("brand") + " " + dataProduct3.get("name"),PARTNER_BRAND_NAME_1,deviceName);
		promotionControl.addPromotion(AddPromotion.getHash(), dataDevicePromotion3);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		// Navigate to Promotion page
		appDeviceControl.click(PageHome.linkPromotions);
		Assert.assertTrue(promotionControl.checkPromotionExistByName(PromotionList.PROMOTION_TABLE,dataDevicePromotion3.get("name")));
        /*
         * *******************************************
         */
//		3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		4. Click "Add App or Device" link
//		5. Fill all valid values into required fields (Include upload an valid default audio route file)
//		6. Input "6" in Promotion Device Available
//		7. Input some valid Apps/Devices Promotions ID which are published and are applied for this app and device
//		8. Turn off in Global Promotions
//		9. Click "Save" link
//		10. Click "Edit" link
//		appDeviceControl.selectADeviceByName("Device380607");
		appDeviceControl.selectADeviceByName(deviceName);
		appDeviceControl.editVersion();
//		11. Change the order of list promotion slots by drag and drop
		// Get list before Drad & Drop
		ArrayList<String> before_list1 = appDeviceControl.getListOrder();
		System.out.println("before: " + before_list1);
//		appDeviceControl.dragPromotion(1);
		Thread.sleep(2000);
//		appDeviceControl.dropPromotions(4);
		appDeviceControl.dragDropPromo(1,3);
//		appDeviceControl.dragDropPromotion();
//		Thread.sleep(10000);
		ArrayList<String> after_list1 = appDeviceControl.getListOrder();
		System.out.println("after: " + after_list1);
		Thread.sleep(2000);
//		VP: user can drag and drop promotion slots to order normally
		Assert.assertTrue(appDeviceControl.isPromotionOrder(1,3,before_list1,after_list1));
		//12. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The device promotion is arranged after dagging and dropping
		 */
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Draft"),after_list1));

		appDeviceControl.doDelete(DeviceInfo.DELETE);
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.deleteAccessorybyName(dataProduct1.get("name"));
		productControl.deleteAccessorybyName(dataProduct2.get("name"));
		productControl.deleteAccessorybyName(dataProduct3.get("name"));
	}
	
	/*
	 * Verify that DTS user can edit the list order of Global Apps and Devices Promotions
	 */
	@Test
	public void TC650DaDEN_44(){
		Reporter.log("Verify that DTS user can edit the list order of Global Apps and Devices Promotions");
		
		/*1. Navigate to DTS portal.	
		2. Log into DTS portal as a DTS user successfully.	
		3. Navigate to "Apps & Devices" page	
		4. Click "Add App or Device" link	
		5. Fill all valid values into required fields 	
		6. Input "6" in Promotion Device Available	
		7. Input some valid Global/Apps/Devices Promotions ID which are published and are applied for this app and device	
		8. Turn off in Global Promotions	
		9. Click "Save" link	
		10. Click "Edit" link	
		11. Change the order of list promotion slots by drag and drop	
		VP: user can drag and drop promotion slots to order normally
		12. Click "Save" link	
		VP: The device promotion is arranged after dagging and dropping

		*/
		
		/*
		 * PreCondition: Create new Device promotion
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to App & Devices page
		appDeviceControl.click(PageHome.linkDevice);
		// Click Add app or device link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> dataDevice = TestData.appDevicePublish();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice);
		String deviceName = appDeviceControl.getTextByXpath(DeviceInfo.NAME);
		
		// Navigate to Product page
		appDeviceControl.click(PageHome.linkAccessories);
		// Click Add product link
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
		// Create 1st published product
		Hashtable<String,String> dataProduct1 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct1);
		// Navigate to Product page
		appDeviceControl.click(PageHome.linkAccessories);
		// Click Add product link
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
		// Create 2nd published product
		Hashtable<String,String> dataProduct2 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct2);
//		// Navigate to Product page
//		appDeviceControl.click(PageHome.linkAccessories);
//		// Click Add product link
//		appDeviceControl.click(ProductModel.ADD_PRODUCT);
//		// Create 3th published product
//		Hashtable<String,String> dataProduct3 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
//		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct3);
	
		// Navigate to Promotion page
		appDeviceControl.click(PageHome.linkPromotions);
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create 1st Device promotion
		Hashtable<String,String> dataDevicePromotion1 = TestData.promotionAppDevice(dataProduct1.get("brand") + " " + dataProduct1.get("name"),PARTNER_BRAND_NAME_1,deviceName);
		promotionControl.addPromotion(AddPromotion.getHash(), dataDevicePromotion1);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		// Navigate to Promotion page
		appDeviceControl.click(PageHome.linkPromotions);
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create 2nd Device promotion
		Hashtable<String,String> dataDevicePromotion2 = TestData.promotionAppDevice(dataProduct2.get("brand") + " " + dataProduct2.get("name"),PARTNER_BRAND_NAME_1,deviceName);
		promotionControl.addPromotion(AddPromotion.getHash(), dataDevicePromotion2);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		
        /*
         * *******************************************
         */
//		3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		4. Click "Add App or Device" link
//		5. Fill all valid values into required fields (Include upload an valid default audio route file)
//		6. Input "6" in Promotion Device Available
//		7. Input some valid Apps/Devices Promotions ID which are published and are applied for this app and device
//		8. Turn off in Global Promotions
//		9. Click "Save" link
//		10. Click "Edit" link
		appDeviceControl.selectADeviceByName(deviceName);
		appDeviceControl.editVersion();
		appDeviceControl.click(DeviceEdit.SWITCH_PROMO);
		appDeviceControl.click(DeviceEdit.SAVE);
		appDeviceControl.editVersion();
//		11. Change the order of list promotion slots by drag and drop (1st)
		// Get list before Drad & Drop
		ArrayList<String> before_list1 = appDeviceControl.getListOrder();
		appDeviceControl.dragDropPromo(1,2);
		ArrayList<String> after_list1 = appDeviceControl.getListOrder();
//		VP: user can drag and drop promotion slots to order normally
		Assert.assertTrue(appDeviceControl.isPromotionOrder(1,2,before_list1,after_list1));
		//12. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The device promotion is arranged after dagging and dropping
		 */
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Draft"),after_list1));
		
		// Delete device
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.deleteAccessorybyName(dataProduct1.get("name"));
		productControl.deleteAccessorybyName(dataProduct2.get("name"));
//		productControl.deleteAccessorybyName(dataProduct3.get("name"));
	}
	
	/*
	 * Verify the list order is included in the offline database
	 */
	@Test
	public void TC650DaDEN_45(){
		Reporter.log("Verify the list order is included in the offline database");
	
		/*
		1. Navigate to DTS portal.	
		2. Log into DTS portal as a DTS user successfully.	
		3. Navigate to "Apps & Devices" page	
		4. Click "Add App or Device" link	
		5. Fill all valid values into required fields	
		6. Upload an valid default audio route file	
		7. Input "6" in Promotion Device Available	
		8. Turn on in Global Promotions	
		9. Click "Save" link	
		10. Click "Edit" link	
		11. Change the order of list promotion slots by drag and drop	
		12. Click "Save" link	
		13. Click "Publish" link	
		14. Click "Refresh offline database" button	
		15. Click "Refresh" button	
		16. Click "Download Offline Database" link	
		VP: the offline database file is downloaded successful
		17. Open the offline databse file by SQLite	
		18. Navigate to DeviceFeaturedProduct table in SQLite	
		19. Compare with JSON in UUX	
		VP: make sure the list order is the same in both UUX and offline databse
		*/

//		2. Log into DTS portal as a DTS user successfully.
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		appDeviceControl.click(PageHome.LINK_COMPANY);
//		companyControl.selectACompanyByName(COMPANY_NAME_TEST);
//		appDeviceControl.click(CompanyInfo.ADD_BRAND);
//		Hashtable<String,String> newBrand = TestData.brandDraft();
//		companyControl.addBrand(AddBrand.getHash(),newBrand);
//		3. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		4. Click "Add App or Device" link
//		appDeviceControl.selectADeviceByName("anhanahanh");
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		5. Fill all valid values into required fields
//		6. Upload an valid default audio route file
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		deviceData.remove("save");
		deviceData.remove("global promotion");
		deviceData.put("global promotion","ON");
//		appDeviceControl.createNewDevice(DeviceEdit.getHash(), deviceData);
		appDeviceControl.createADeviceOneBrand(DeviceEdit.getHash(), deviceData, BRAND_NAME_TEST);
//		7. Input "10" in Promotion Device Available
		appDeviceControl.editData(DeviceEdit.PROMO_SLOT,"6");
//		8. Turn on in Global Promotions	
//		9. Click "Save" link	
		appDeviceControl.click(DeviceEdit.SAVE);
		String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		10. Click "Edit" link	
		appDeviceControl.click(DeviceInfo.EDIT);
//		11. Change the order of list promotion slots by drag and drop
		appDeviceControl.dragDropPromo(4,2);
//		12. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
//		13. Click "Publish" link	
		appDeviceControl.click(DeviceInfo.PUBLISH);
		ArrayList<String> listPromo = appDeviceControl.getListPromo("Published");
////		14. Click "Refresh offline database" button
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
////		15. Click "Refresh" button	
		appDeviceControl.selectConfirmationDialogOption("Refresh");
////		16. Click "Download Offline Database" link	
		boolean isDownloaded = appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE);
		/*
		 * VP: the offline database file is downloaded successful
		 */
		Assert.assertTrue(isDownloaded);
//		17. Open the offline databse file by SQLite	
//		18. Navigate to DeviceFeaturedProduct table in SQLite	
//		19. Compare with JSON in UUX
		/*
		 * VP: make sure the list order is the same in both UUX and offline databse
		 */
		Assert.assertTrue(appDeviceControl.isDBContainListOrder(appDeviceControl.getOfflineDBName(dts_id),listPromo));
		
		// Delete device
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the text "Drag to reorder promotion list" and reorder icon are displayed correctly
	 */
	@Test
	public void TC650DaDEN_46(){
		Reporter.log("Verify that the text 'Drag to reorder promotion list' and reorder icon are displayed correctly");
		
		/*1. Navigate to DTS portal
		2. Log into DTS portal as a DTS user successfully
		3. Navigate to "Apps & Devices" page
		4. Click "Add App or Device" link
		VP: The text "Drag to reorder promotion list." is displayed under "Device Promotions" text 
		and the reorder icon is displayed next to each label Promo slot on the left

		*/
//		2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		4. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: The text "Drag to reorder promotion list." is displayed under "Device Promotions" text 
		and the reorder icon is displayed next to each label Promo slot on the left
		*/
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.INSTRUCTION_TEXT),DeviceEdit.Instruction_Text);
		Assert.assertTrue(appDeviceControl.isReorderIconNextToPromo());
	}
	
	
	/*
	 * Verify that the visual indicator is displayed correctly while draging a promo item.
	 */
	@Test
	public void TC650DaDEN_47() throws InterruptedException{
		Reporter.log("Verify that the visual indicator is displayed correctly while draging a promo item.");
		
		/*1. Navigate to DTS portal
		2. Log into DTS portal as a DTS user successfully
		3. Navigate to "Apps & Devices" page
		4. Click "Add App or Device" link
		5. Fill valid values into all required fields
		6. Click "Save" link
		7. Click "Edit Version" link
		8. Drag a promo
		VP: The thin line is displayed
		9. Drop above promo
		VP: The thin line is not displayed
		*/
		
		//2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//4. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		//5. Fill valid values into all required fields
		Hashtable<String,String> devicedata = TestData.appDevicePublish();
		devicedata.remove("global promotion");
		devicedata.put("global promotion","ON");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),devicedata);
		//6. Click "Save" link
		// Get device name
		String device_name = appDeviceControl.getTextByXpath(DeviceInfo.NAME);
//		int promoS = Integer.parseInt(appDeviceControl.getTextByXpath(DeviceInfo.PROMO_SLOT));
		//7. Click "Edit Version" link
		appDeviceControl.selectADeviceByName(device_name);
		appDeviceControl.editVersion();
		Thread.sleep(2000);
		// 8. Drag "Promo 1" to new location between "Promo 2" and "Promo 3"
		// 9. Drop above Promo
		ArrayList<String> before_list1 = appDeviceControl.getListOrder();
		appDeviceControl.dragDropPromo(1,3);
		ArrayList<String> after_list1 = appDeviceControl.getListOrder();
		// VP: The promo is displayed correct location
		Thread.sleep(2000);
		Assert.assertTrue(appDeviceControl.isPromotionOrder(1,3,before_list1,after_list1));
		Thread.sleep(2000);
		// 10. Drag "Promo 4" to new location before "Promo 1"
		// 11. Drop above Promo
		ArrayList<String> before_list2 = appDeviceControl.getListOrder();
		appDeviceControl.dragDropPromo(4,1);
		ArrayList<String> after_list2 = appDeviceControl.getListOrder();
		// VP: The promo is displayed correct location
		Assert.assertTrue(appDeviceControl.isPromotionOrder(4,1,before_list2,after_list2));
		Thread.sleep(2000);
//		// 12. Drag "Promo 2" to new location after the final Promo
//		// 13. Drop above Promo
//		ArrayList<String> before_list3 = appDeviceControl.getListOrder();
//		appDeviceControl.dragDropPromo(2,333);
//		ArrayList<String> after_list3 = appDeviceControl.getListOrder();
//		// VP: The promo is displayed correct location
//		Assert.assertTrue(appDeviceControl.isPromotionOrder(2,promoS,before_list3,after_list3));
		
		// Delete device
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.selectADeviceByName(device_name);
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		Thread.sleep(3000);
	}
	/*
	 * Verify that the "Headphone Image Sizes" is validated in edit page
	 */
	@Test
	public void TC650DaDEN_49(){
		Reporter.log("Verify that the 'Headphone Image Sizes' is validated in edit page");
		
		/*1. Log into DTS portal as DTS admin
		2. Navigate to "Apps & Devices" page
		3. Click "Add App or Device" link
		4. Fill all valid values into required fields
		VP: The 650Da Device Edit page is displayed
		5. Unselect all options in "Headphone Image Sizes"
		6. Click "Save" link
		VP: An error message is displayed
		7. Click "Cancel" link
		VP: The portal is redirected to 640D Device Detail page and the product's information that set are correct.
		*/
		
		//1.Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		//4. Fill all valid values into required fields
		Hashtable<String,String> dataDevice = TestData.appDevicePublish();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice);
		/*
		 * VP: The 650Da Device Edit page is displayed
		 */
		//5. Unselect all options in "Headphone Image Sizes"
		appDeviceControl.editVersion();
		appDeviceControl.uncheckACheckbox(DeviceEdit.LARGE_IMAGE);
		appDeviceControl.uncheckACheckbox(DeviceEdit.MEDIUM_IMAGE);
		appDeviceControl.uncheckACheckbox(DeviceEdit.SMALL_IMAGE);
		//6. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: An error message is displayed
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.IMAGE_SIZE_REQUIRE),"Headphone image sizes is required");
		//7. Click "Cancel" link
		appDeviceControl.click(DeviceEdit.CANCEL);
		/*
		 * VP: The portal is redirected to 640D Device Detail page and the product's information that set are correct.
		 */
		Assert.assertTrue(ListUtil.containsAll(dataDevice,appDeviceControl.getTextListByXpaths(DeviceInfo.getDeviceInfo())));

	}
	
	/*
	 * Verify that "Headphone Image Sizes" is displayed correctlly when adding new app/device
	 */
	@Test
	public void TC650DaDEN_50(){
		Reporter.log("Verify that 'Headphone Image Sizes' is displayed correctlly when adding new app/device");
		
		/*1. Log into DTS portal as DTS admin
		2. Navigate to "Apps & Devices" page
		3. Click "Add App or Device" link
		"VP: All image sizes are selected by default in ""Headphone Image Sizes"" section.
		VP: The intruction ""Select which headphone image sizes will be made available to users.For on-device databases, only one size is recommended."
		is displayed under ""Headphone Image Sizes"" section.
		*/
		
		//1. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: All image sizes are selected by default in ""Headphone Image Sizes"" section.
		 */
		WebElement lagre_image = driver.findElement(By.xpath(DeviceEdit.LARGE_IMAGE));
		Assert.assertTrue(lagre_image.isSelected());
		WebElement medium_image = driver.findElement(By.xpath(DeviceEdit.MEDIUM_IMAGE));
		Assert.assertTrue(medium_image.isSelected());
		WebElement small_image = driver.findElement(By.xpath(DeviceEdit.SMALL_IMAGE));
		Assert.assertTrue(small_image.isSelected());
		/*
		 * VP: The intruction "Select which headphone image sizes will be made available to users.For on-device databases, only one size is recommended."
		   is displayed under "Headphone Image Sizes" section.
		*/
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.HEADPHONE_IMAGE_SIZES_INSTRUCTION),DeviceEdit.requires.Select_headphone_available.getName());

	}
	
	/*
	 * Verify that the "In-box headphone" displays correctly as the design
	 */
	@Test
	public void TC650DaDEN_51(){
		Reporter.log("Verify that the 'In-box headphone' displays correctly as the design");
		
		/*  1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Select add app or device 
			4.Scroll down to "In-box headphone" section
			VP: The  text input field has a similar UX
			*/
		//1. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Select add app or device 
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		//4.Scroll down to "In-box headphone" section
		appDeviceControl.selectOptionByName(DeviceEdit.TYPE,"Device");
		/*
		 * VP: The  text input field has a similar UX
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.INBOX_HEADPHONES));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.INBOX_INSTRUCTION));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.INBOX_FIELD));
		
	}
	
	/*
	 * Verify the "Headphone Render Entity UUID" field when inputing value 
	 */
	@Test
	public void TC650DaDEN_52(){
		Reporter.log("Verify the 'Headphone Render Entity UUID' field when inputing value ");
		
		/*
		 *  1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Select add app or device 
			4. Scroll down to "In-box headphone" section
			5. Input Product Name to text field
			VP: The input value must be validated to an active, published headphone model
			VP: the headphone model name is displayed near the text field
		 */
	
		//1. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Create a new product 
		appDeviceControl.click(PageHome.linkAccessories);
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		//2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Select add app or device 
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		//4. Scroll down to "In-box headphone" section
		Hashtable<String,String> deviceData = TestData.appDeviceDraft();
		deviceData.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		//5. Input headphone render entity UUID to text field
//		appDeviceControl.editData(DeviceEdit.INBOX_FIELD,dataProduct.get("name"));
//		WebElement inboxField = driver.findElement(By.xpath(DeviceEdit.INBOX_FIELD));
//		inboxField.sendKeys(Keys.PAGE_DOWN);
//		appDeviceControl.autoTool.send("{ENTER}", false);
		appDeviceControl.inputInboxHP(DeviceEdit.INBOX_FIELD,dataProduct.get("name"));
		/*
		 * VP: the headphone model name is displayed near the text field
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.INBOX_PRODUCT_NAME).contains(dataProduct.get("name")));

		// Delete products
		appDeviceControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		appDeviceControl.doDelete(ProductDetailModel.DELETE);
		
		
	}
	
	/*
	 * Verify the "Product Name" field auto trim white space when inputing value 
	 */
	@Test
	public void TC650DaDEN_53(){
		Reporter.log("Verify the 'Product Name' field auto trim white space when inputing value ");
		
		/*
		 * 1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Select add app or device 
			4.Scroll down to "In-box headphone" section
			5. Input Product Name to text field (the product name is included white space)
			VP: Text field auto trim the white space in product name

		 */
		
		//1. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Create a new product 
		appDeviceControl.click(PageHome.linkAccessories);
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		//2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Select add app or device 
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		appDeviceControl.selectOptionByName(DeviceEdit.TYPE,"Device");
		//4.Scroll down to "In-box headphone" section
		//5. Input Product Name to text field (the product name is included white space)
//		appDeviceControl.editData(DeviceEdit.INBOX_FIELD,"          "+dataProduct.get("name")+"        ");
//		WebElement inboxField = driver.findElement(By.xpath(DeviceEdit.INBOX_FIELD));
//		inboxField.sendKeys(Keys.PAGE_DOWN);
//		appDeviceControl.autoTool.send("{ENTER}", false);
		appDeviceControl.inputInboxHP(DeviceEdit.INBOX_FIELD,dataProduct.get("name"));
		/*
		 * VP: Text field auto trim the white space in product name
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.INBOX_PRODUCT_NAME).contains(dataProduct.get("name")));
		
		// Delete products
		appDeviceControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		appDeviceControl.doDelete(ProductDetailModel.DELETE);
	}
	
	
	/*
	 * Verify the "delete" link
	 */
	@Test
	public void TC650DaDEN_54(){
		Reporter.log("Verify the 'delete' link");
		
		/*
		 *  1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Select add app or device 
			4. Scroll down to "In-box headphone" section
			5. Input Product Name to text field
			VP: The delete link displays near the headphone model name
			6. Delete  Product Name in text field
			VP: The delete link is invisibled
		 */
		
		//1. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Create a new product 
		appDeviceControl.click(PageHome.linkAccessories);
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		//2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Select add app or device 
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		//4.Scroll down to "In-box headphone" section
		appDeviceControl.selectOptionByName(DeviceEdit.TYPE,"Device");
		//5. Input headphone render entity UUID to text field
//		appDeviceControl.editData(DeviceEdit.INBOX_FIELD,dataProduct.get("name"));
//		WebElement inboxField = driver.findElement(By.xpath(DeviceEdit.INBOX_FIELD));
//		inboxField.sendKeys(Keys.PAGE_DOWN);
//		appDeviceControl.autoTool.send("{ENTER}", false);
		appDeviceControl.inputInboxHP(DeviceEdit.INBOX_FIELD,dataProduct.get("name"));
		/*
		 * VP: The delete link displays near the headphone model name
		 */
		appDeviceControl.click(DeviceEdit.DELETE_INBOX);
		appDeviceControl.selectConfirmationDialogOption("Delete");
		//6. Delete  Product Name in text field
		/*
		 * VP: The delete link is invisibled
		 */
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.DELETE_INBOX));
		
		// Delete products
		appDeviceControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		appDeviceControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify the "delete confirmation dialog"
	 */
	@Test
	public void TC650DaDEN_55(){
		Reporter.log("Verify the 'delete confirmation dialog' ");
		
		/*
		 *  1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Select add app or device 
			4. Scroll down to "In-box headphone" section
			5. Input Product Name to text field
			6. Click "Delete" link
			VP: The delete confirmation is show if there's a value in the field
			7. Click "Cancel"
			VP: Cancel delete the headphone model
			8. Click "Delete" link
			9. Click "Delete"
			VP: The headphone model is deleted correctly

		 */
		 //1. Log into DTS portal as DTS admin
		 loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		 // Create a new published product
		 appDeviceControl.click(PageHome.linkAccessories);
		 appDeviceControl.click(ProductModel.ADD_PRODUCT);
		 Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		 productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		 //String productName = appDeviceControl.getTextByXpath(DeviceInfo.NAME);
		 //2. Navigate to "Apps & Devices" page
		 appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		 //3. Select add app or device 
		 appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		 //4.Scroll down to "In-box headphone" section
		 appDeviceControl.selectOptionByName(DeviceEdit.TYPE,"Device");
		 //5. Input headphone render entity UUID to text field
//		 appDeviceControl.editData(DeviceEdit.INBOX_FIELD,dataProduct.get("name"));
//		 WebElement inboxField = driver.findElement(By.xpath(DeviceEdit.INBOX_FIELD));
//		 inboxField.sendKeys(Keys.PAGE_DOWN);
//		 appDeviceControl.autoTool.send("{ENTER}", false);
		 appDeviceControl.inputInboxHP(DeviceEdit.INBOX_FIELD,dataProduct.get("name"));
	     //6. Click "Delete" link
		 appDeviceControl.click(DeviceEdit.DELETE_INBOX);
		 /*
		 * VP: The delete confirmation is show if there's a value in the field
		 */
		 //7. Click "Cancel"
		 appDeviceControl.selectConfirmationDialogOption("Cancel");
		 /*
		  * VP: Cancel delete the headphone model
		  */
		 Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_INBOX));
		 //8. Click "Delete" link
		 appDeviceControl.click(DeviceEdit.DELETE_INBOX);
		 appDeviceControl.selectConfirmationDialogOption("Delete");
		 //9. Click "Delete"
		 /*
		 * VP: The headphone model is deleted correctly
		 */
		 Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.DELETE_INBOX));
		 
		 // Delete products
		 appDeviceControl.click(PageHome.linkAccessories);
		 productControl.selectAccessorybyName(dataProduct.get("name"));
		 appDeviceControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the in-box headphone model selection is optional (An empty value is ok)
	 */
	@Test
	public void TC650DaDEN_56(){
		Reporter.log("Verify that the in-box headphone model selection is optional (An empty value is ok)");
		
		/*
		 *  1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Select add app or device 
			4. Input value to all section
			5. Do not input "Product Name" to text field of "In-box headphone" section  
			6. Click "Save" button
			VP: In the 640 Device Detail Page , the section "in-box headphone model" 
			display as optional, the text "Unspecified" is displayed  

		 */
		
		//1. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Select add app or device 
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		//4. Input value to all section
		Hashtable<String,String> deviceData = TestData.appDeviceDraft();
		deviceData.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		//5. Do not input "Product Name" to text field of "In-box headphone" section 
		//6. Click "Save" button
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: In the 640 Device Detail Page , the section "in-box headphone model" 
		display as optional, the text "Unspecified" is displayed */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.INBOX_UNSPECIFIED),"Unspecified");
		
		// Delete app/device
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		
		
	}
	
	/*
	 * Verify that the "in-box headphone model" section displays correctly
	 */
	@Test
	public void TC650DaDEN_57() throws InterruptedException{
		Reporter.log("Verify that the 'in-box headphone model' section displays correctly");
		
		/*1. Log into DTS portal as DTS admin
		2. Navigate to "Apps & Devices" page
		3. Select add app or device 
		4. Input value to all section
		5. Input "Product Name" to text field of "In-box headphone" section
		6. Click "Save" button
		VP: Section ""in-box headphone model"" displays, the headphone model and date are displayed correctly.
		VP: the date is the time that the value was last changed

		*/
		
		//1. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Create a new published product
		appDeviceControl.click(PageHome.linkAccessories);
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		String productName = appDeviceControl.getTextByXpath(DeviceInfo.NAME);
		//2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Select add app or device 
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		//4. Input value to all section
		Hashtable<String,String> deviceData = TestData.appDeviceDraft();
		deviceData.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		//5. Input headphone render entity UUID to text field (the publication status of headphone is Draft)
//		appDeviceControl.editData(DeviceEdit.INBOX_FIELD,productName);
//		Thread.sleep(1000);
//		appDeviceControl.autoTool.send("{ENTER}", false);
		appDeviceControl.inputInboxHP(DeviceEdit.INBOX_FIELD, dataProduct.get("name"));
		//6. Click "Save" button
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: Section ""in-box headphone model"" displays, the headphone model and date are displayed correctly.
		 * VP: the date is the time that the value was last changed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.INBOX_HEADPHONES));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.INBOX_INSTRUCTION));
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.getInboxHeadphoneXpath(productName)),productName);
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.INBOX_DATE));
		
		// Delete products
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		appDeviceControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		appDeviceControl.doDelete(ProductDetailModel.DELETE);
		
	}
	
	/*
	 * Verify that the "headphone model " always links to the currently published version data
	 */
	@Test
	public void TC650DaDEN_58() throws InterruptedException{
		Reporter.log("Verify that the 'headphone model' always links to the currently published version data");
		
		/*
		 *  1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Select add app or device 
			4. Input value to all section
			5. Input headphone render entity UUID to text field (the publication status of headphone is Draft)
			6. Click "Save" button
			7. Click the headphone model in section "in-box headphone"
			VP: the "headphone model " always links to the currently published version data

		 */
		
		 //1. Log into DTS portal as DTS admin
		 loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		 appDeviceControl.click(PageHome.linkAccessories);
		 appDeviceControl.click(ProductModel.ADD_PRODUCT);
		 Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		 productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		 //2. Navigate to "Apps & Devices" page
		 appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		 //	3. Select add app or device 
		 appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		 //	4. Input value to all section
		 Hashtable<String,String> deviceData = TestData.appDeviceDraft();
		 deviceData.remove("save");
		 appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		 //	5. Input headphone render entity UUID to text field (the publication status of headphone is Draft)
//		 appDeviceControl.editData(DeviceEdit.INBOX_FIELD,dataProduct.get("name"));
//		 Thread.sleep(1000);
//		 appDeviceControl.autoTool.send("{ENTER}", false);
		 appDeviceControl.inputInboxHP(DeviceEdit.INBOX_FIELD,dataProduct.get("name"));
		 //	6. Click "Save" button
		 appDeviceControl.click(DeviceEdit.SAVE);
		 //	7. Click the headphone model in section "in-box headphone"
		 appDeviceControl.click(DeviceInfo.getInboxHeadphoneXpath(dataProduct.get("name")));
		 /*
		  * VP: the "headphone model " always links to the currently published version data
		  */
		 Assert.assertEquals(appDeviceControl.getTextByXpath(ProductDetailModel.PUBLICATION_STATUS),"Published");
		 
		// Delete products
		appDeviceControl.doDelete(ProductDetailModel.DELETE);
		// Delete device
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.selectADeviceByName(deviceData.get("name"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		
	}
	
	
	/*
	 * Verify that offline DB add a new column "InboxUuid" into "Device" table to store its product Render Entity UUID.
	 */
	@Test
	public void TC650DaDEN_59() throws InterruptedException{
		Reporter.log("Verify that offline DB add a new column 'InboxUuid' into 'Device' table to store its product Render Entity UUID.");
		
		/*1. Log into DTS portal as DTS admin	
		2. Navigate to "Apps & Devices" page	
		3. Select add app or device 	
		4. Input value to all section	
		5. Input "Product Name" to text field 	
		6. Click "Save" button	
		7. Click "Refresh offline db"	
		8. Download offline db	
		9. Open offline db, go to Device table	
		VP: The new column "InboxUuid" is added into "Device" table 
		VP: the product Render Entity UUID is stored in this column
		*/
		
//		1. Log into DTS portal as DTS admin	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		
//		appDeviceControl.click(PageHome.LINK_COMPANY);
		//3. Select a company from company list
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		//4. Create a new brand
//		appDeviceControl.click(CompanyInfo.ADD_BRAND);
//		Hashtable<String,String> newBrand = TestData.brandDraft();
//		companyControl.addBrand(AddBrand.getHash(),newBrand);
//		
		// Create published product 
		appDeviceControl.click(PageHome.linkAccessories);
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true,true,true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(),productData);
		String inbox_UUID = appDeviceControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
//		2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		3. Select add app or device 
//		appDeviceControl.selectADeviceByName("Sony C4");
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		4. Input value to all section	
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		deviceData.remove("save");
//		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		appDeviceControl.createADeviceOneBrand(DeviceEdit.getHash(),deviceData, BRAND_NAME_TEST);
//		5. Input "Product Name" to text field 	
		appDeviceControl.editData(DeviceEdit.INBOX_FIELD,productData.get("name"));
		Thread.sleep(1000);
		appDeviceControl.autoTool.send("{ENTER}", false);
//		6. Click "Save" button and publish device
		appDeviceControl.click(DeviceEdit.SAVE);
		String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		7. Click "Refresh offline db"	
		appDeviceControl.click(DeviceInfo.PUBLISH);
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		8. Download offline db	
		appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE);
//		9. Open offline db, go to Device table
		/*
		 * VP: The new column "InboxUuid" is added into "Device" table 
		 */
		/*
		 * VP: the product Render Entity UUID is stored in this column
		 */
		Assert.assertTrue(appDeviceControl.isDBContainsInbox(appDeviceControl.getOfflineDBName(dts_id),inbox_UUID));
		
		// Delete product
		appDeviceControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(productData.get("name"));
		appDeviceControl.doDelete(ProductDetailModel.DELETE);
		// Delete device
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.selectADeviceByName(deviceData.get("name"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the offline DB need include data of the associate product If in-box headphone was specified in Partner Portal.
	 */
	@Test
	public void TC650DaDEN_60() throws InterruptedException{
		Reporter.log("Verify that the offline DB need include data of the associate product If in-box headphone was specified in Partner Portal.");
	
		/*1. Log into DTS portal as DTS admin	
		2. Navigate to "Apps & Devices" page	
		3. Select add app or device 	
		4. Input value to all section	
		5. Input "Product Name" to text field	
		6. Click "Save" button	
		7. Click "Refresh offline db"	
		8. Download offline db	
		9. Open offline db, go to "Device" table to get "Product Render Entity UUID"	
		10. Go to tables: "device, asset, brand, product, product audio route, product localization" and search that Uuid	
		VP: Verify data of inbox headphone that stored in those tables are correctly
		*/
		
//		1. Log into DTS portal as DTS admin	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		
//		appDeviceControl.click(PageHome.LINK_COMPANY);
//		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
//		// Create a new brand
//		appDeviceControl.click(CompanyInfo.ADD_BRAND);
//		Hashtable<String,String> newBrand = TestData.brandDraft();
//		companyControl.addBrand(AddBrand.getHash(),newBrand);
		
		// Create published product 
		appDeviceControl.click(PageHome.linkAccessories);
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true,true,true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(),productData);
		String inbox_UUID = appDeviceControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
//		2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		3. Select add app or device 	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		4. Input value to all section	
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		deviceData.remove("save");
//		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		appDeviceControl.createADeviceOneBrand(DeviceEdit.getHash(),deviceData, BRAND_NAME_TEST);
//		5. Input "Product Name" to text field	
		appDeviceControl.editData(DeviceEdit.INBOX_FIELD,productData.get("name"));
//		WebElement inboxField = driver.findElement(By.xpath(DeviceEdit.INBOX_FIELD));
//		inboxField.sendKeys(Keys.PAGE_DOWN);
		Thread.sleep(1000);
		appDeviceControl.autoTool.send("{ENTER}", false);
//		6. Click "Save" button
		appDeviceControl.click(DeviceEdit.SAVE);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		7. Click "Refresh offline db"	
		appDeviceControl.click(DeviceInfo.PUBLISH);
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		8. Download offline db
		appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE);
//		9. Open offline db, go to "Device" table to get "Product Render Entity UUID"	
//		10. Go to tables: "device, asset, brand, product, product audio route, product localization" and search that Uuid
		/*
		 * VP: Verify data of inbox headphone that stored in those tables are correctly
		 */
		Assert.assertTrue(appDeviceControl.isDBContainsInbox(appDeviceControl.getOfflineDBName(device_UUID),inbox_UUID));
		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(device_UUID),"select Name from Product where Uuid in ( select InboxUuid from Device )","Name",productData.get("name")));

		// Delete product
		appDeviceControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(productData.get("name"));
		appDeviceControl.doDelete(ProductDetailModel.DELETE);
		// Delete device
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.selectADeviceByName(deviceData.get("name"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		
	}
	
	/*
	 * Verify that Multi Channel Room Models are displayed correctly
	 */
	@Test
	public void TC650DaDEN_61(){
		Reporter.log("Verify that Multi Channel Room Models are displayed correctly");
		
		/*
		Pre-Condition : There are at least one published McRoom and one draft McRoom in Audio page
		1. Log into DTS portal as a DTS admin
		2. Navigate to "Apps & Devices" page
		3. Click "Add App or Device" link
		4. Focus on Multi Channel Room Models
		VP: The "Multi Channel Room Models" section displays published version McRooms

		*/
		/*
		 * Pre-Condition : There are at least one published McRoom and one draft McRoom in Audio page
		 */
		//1. Log into DTS portal as a DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Publish a McRoom
		appDeviceControl.click(PageHome.linkAudioroutes);
		appDeviceControl.click(AudioRoutes.MCROOM0);
		audioControl.editVersion();
		appDeviceControl.editData(RoomModelEdit.DISPLAY_NAME,"Published McRoom");
		appDeviceControl.click(RoomModelEdit.SAVE);
		audioControl.publishAudioRoute(AddEditProductModel.FileUpload.McRoom0_RC1_5p1_7p1.getName());
		// Draft McRoom
		appDeviceControl.click(PageHome.linkAudioroutes);
		appDeviceControl.click(AudioRoutes.MCROOM1);
		audioControl.editVersion();
		appDeviceControl.editData(RoomModelEdit.DISPLAY_NAME,"Draft McRoom");
		appDeviceControl.click(RoomModelEdit.SAVE);
		//2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		//4. Focus on Multi Channel Room Models
		/*
		 * VP: The "Multi Channel Room Models" section displays published version McRooms
		 */
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_High.getType());
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.MCROOM0).contains("Published McRoom"));
		Assert.assertFalse(appDeviceControl.getTextByXpath(DeviceEdit.MCROOM1).contains("Draft McRoom"));
		
	}
	/*
	 * Verify that the invalid route tuning file is uploaded unsuccessfully when selecting internal speaker audio route   
	 */
	@Test
	public void TC650DaDEN_62(){
		Reporter.log("Verify that the invalid route tuning file is uploaded unsuccessfully when selecting internal speaker audio route ");
		
		/*	1. Log into DTS portal as DTS admin	
			2. Navigate to "Apps & Devices" page	
			3. Click on "Add App or Device" link	
			VP: The Device Edit page is displayed
			4. Select Internal Speakers (select 1 in 6 attached) in "Audio Route" dropdown list	
			5. Click on "Choose file" button in "Custom Tuning" section	
			6. Upload the custom tuning DTSCS file that having route not match 	
			VP: A "Invalid file contents: Route, it must appropriate the Audio Route" message is displayed in "Custom Tuning" section

		*/
		
		//1. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Click on "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		/*
		 * VP: The Device Edit page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()),true);
		//4. Select Internal Speakers (select 1 in 6 attached) in "Audio Route" dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached1_Internal_Speakers_Off.getName()); // (off)
		//5. Click on "Choose file" button in "Custom Tuning" section
		//6. Upload the custom tuning DTSCS file that having route not match 	     
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached2InternalSpeakersMusic_Custom.getName());
		/*
		 * VP: A "Invalid file contents: Route, it must appropriate the Audio Route" message is displayed in "Custom Tuning" section
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS),DeviceEdit.requires.Invalid_file_contents.getName());
		

	}
	
	/*
	 * Verify that the valid tuning file is uploaded successfully  when selecting internal speaker audio route  
	 */
	@Test
	public void TC650DaDEN_63(){
		Reporter.log("Verify that the invalid route tuning file is uploaded unsuccessfully when selecting Line Out audio route ");
		
		/*1. Log into DTS portal as DTS admin	
		2. Navigate to "Apps & Devices" page	
		3. Click on "Add App or Device" link	
		VP: The Device Edit page is displayed
		4. Select Internal Speakers (select 1 in 6 attached) in "Audio Route" dropdown list	
		5. Click on "Choose file" button in "Custom Tuning" section	
		6. Upload the valid custom tuning DTSCS file 	
		VP: Verify that the valid custom tuning DTSCS file is upload successfully
		VP: A "Invalid file contents: Route, it must appropriate the Audio Route" message is not displayed in "Custom Tuning" section

		*/
		
		//1. Log into DTS portal as DTS admin	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Click on "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: The Device Edit page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()),true);
		//4. Select Internal Speakers (select 1 in 6 attached) in "Audio Route" dropdown list	
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached3_Internal_Speakers_Mode_2.getName()); // (mode 2)
		//5. Click on "Choose file" button in "Custom Tuning" section	
		//6. Upload the valid custom tuning DTSCS file 	
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached3InternalSpeakersMovies_Custom.getName());
		/*
		 * VP: Verify that the valid custom tuning DTSCS file is upload successfully
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME),DeviceEdit.routes.Attached3_Internal_Speakers_Mode_2.getName());
		/*
		 * VP: A "Invalid file contents: Route, it must appropriate the Audio Route" message is not displayed in "Custom Tuning" section
		 */
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.CUSTOM_TUNING_MESS));
	}
	
	/*
	 * Verify that the invalid route tuning file is uploaded unsuccessfully when selecting Line Out audio route 
	 */
	@Test
	public void TC650DaDEN_64(){
		Reporter.log("Verify that the invalid route tuning file is uploaded unsuccessfully when selecting Line Out audio route ");
		
		/*1. Log into DTS portal as DTS admin	
		2. Navigate to "Apps & Devices" page	
		3. Click on "Add App or Device" link	
		VP: The Device Edit page is displayed
		4. Select Line Out  in "Audio Route" dropdown list	
		5. Select any type headphone in "Standard Accessories" dropdown list	
		6. Click on "Choose file" button in "Custom Tuning" section	
		7. Upload the custom tuning DTSCS file that having route not match 	
		VP: A "Invalid file contents: Route, it must appropriate the Audio Route" message is displayed in "Custom Tuning" section

		*/
		
		//1. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Click on "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		/*
		 * VP: The Device Edit page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()),true);
		//4. Select Line Out  in "Audio Route" dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Line_out0.getName()); // "Lite out"
		//5. Select any type headphone in "Standard Accessories" dropdown list	
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName()); //"Over-Ear Headphones"
		//6. Click on "Choose file" button in "Custom Tuning" section	
		//7. Upload the custom tuning DTSCS file that having route not match
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Custom.getName());
		/*
		 * VP: A "Invalid file contents: Route, it must appropriate the Audio Route" message is displayed in "Custom Tuning" section
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS),DeviceEdit.requires.Invalid_file_contents.getName());
	}
	
	/*
	 * Verify that the valid tuning file is uploaded unsuccessfully when selecting Line Out audio route 
	 */
	@Test
	public void TC650DaDEN_65(){
		Reporter.log("Verify that the valid tuning file is uploaded unsuccessfully when selecting Line Out audio route");
		
		/*1. Log into DTS portal as DTS admin	
		2. Navigate to "Apps & Devices" page	
		3. Click on "Add App or Device" link	
		VP: The Device Edit page is displayed
		4. Select Line Out  in "Audio Route" dropdown list	
		5. Select any type headphone in "Standard Accessories" dropdown list	
		6. Click on "Choose file" button in "Custom Tuning" section	
		7. Upload the valid custom tuning DTSCS file 	
		VP: Verify that the valid tuning DTSCS file is uploaded successfully

		*/
		
		//1. Log into DTS portal as DTS admin	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Click on "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: The Device Edit page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()),true);
		//4. Select Line Out  in "Audio Route" dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Line_out0.getName()); // "Lite out"
		//5. Select any type headphone in "Standard Accessories" dropdown list	
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.External_Speakers.getName()); // "External Speakers"
		//6. Click on "Choose file" button in "Custom Tuning" section	
		//7. Upload the valid custom tuning DTSCS file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.External_Speakers_Lineout.getName());
		/*
		 * VP: Verify that the valid custom tuning DTSCS file is upload successfully
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME));
		/*
		 * VP: A "Invalid file contents: Route, it must appropriate the Audio Route" message is not displayed in "Custom Tuning" section
		 */
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.CUSTOM_TUNING_MESS));

	}
	
	/*
	 * Verify that the invalid route tuning file is uploaded unsuccessfully when selecting Bluetooth audio route 
	 */
	@Test
	public void TC650DaDEN_66(){
		Reporter.log("Verify that the invalid route tuning file is uploaded unsuccessfully when selecting Bluetooth audio route ");
		
		/*1. Log into DTS portal as DTS admin	
		2. Navigate to "Apps & Devices" page	
		3. Click on "Add App or Device" link	
		VP: The Device Edit page is displayed
		4. Select Bluetooth  in "Audio Route" dropdown list	
		5. Select any type headphone in "Standard Accessories" dropdown list	
		6. Click on "Choose file" button in "Custom Tuning" section	
		7. Upload the custom tuning DTSCS file that having route not match 	
		VP: A "Invalid file contents: Route, it must appropriate the Audio Route" message is displayed in "Custom Tuning" section

		*/
		
		//1. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Click on "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: The Device Edit page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()),true);
		//4. Select Bluetooth  in "Audio Route" dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Bluetooth0.getName()); // "Bluetooth0 - Bluetooth"
		//5. Select any type headphone in "Standard Accessories" dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Ear_piece.getName()); // "Ear-piece"
		//6. Click on "Choose file" button in "Custom Tuning" section	
		//7. Upload the custom tuning DTSCS file that having route not match 	
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached2InternalSpeakersMusic_Custom.getName());
		/*
		 * VP: A "Invalid file contents: Route, it must appropriate the Audio Route" message is displayed in "Custom Tuning" section
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS),DeviceEdit.requires.Invalid_file_contents.getName());
	}
	
	/*
	 * Verify that the valid tuning file is upload successfully when selecting Bluetooth audio route
	 */
	@Test
	public void TC650DaDEN_67(){
		Reporter.log("Verify that the valid tuning file is upload successfully when selecting Bluetooth audio route");
	
		
		/*1. Log into DTS portal as DTS admin	
		2. Navigate to "Apps & Devices" page	
		3. Click on "Add App or Device" link	
		VP: The Device Edit page is displayed
		4. Select Bluetooth  in "Audio Route" dropdown list	
		5. Select any type headphone in "Standard Accessories" dropdown list	
		6. Click on "Choose file" button in "Custom Tuning" section	
		7. Upload the valid custom tuning DTSCS file 	
		VP: Verify that the valid tuning DTSCS file is uploaded successfully
		VP: A "Invalid file contents: Route, it must appropriate the Audio Route" message is not displayed in ""Custom Tuning"" section"

		*/

		//1. Log into DTS portal as DTS admin	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Click on "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: The Device Edit page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()),true);
		//4. Select Bluetooth  in "Audio Route" dropdown list	
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Bluetooth0.getName()); // "Bluetooth0 - Bluetooth"
		//5. Select any type headphone in "Standard Accessories" dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Car_Audio.getName()); // "Car Audio"	
		//6. Click on "Choose file" button in "Custom Tuning" section	
		//7. Upload the valid custom tuning DTSCS file 
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Car_Audio_Bluetooth.getName());
		/*
		 * VP: Verify that the valid tuning DTSCS file is uploaded successfully
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME));
		/*
		 * VP: A "Invalid file contents: Route, it must appropriate the Audio Route" message is not displayed in ""Custom Tuning"" section"
		 */
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.CUSTOM_TUNING_MESS));

	}
	
	/*
	 * Verify that McRoom0 is checked for the publish status when publishing an app/device
	 */
	@Test
	public void TC650DaDEN_68(){
		Reporter.log("Verify that McRoom0 is checked for the publish status when publishing an app/device");
		
		/*1. Log into DTS portal as DTS admin	
		2. Navigate to "Apps & Devices" page	
		3. Click on "Add App or Device" link	
		VP: The Device Edit page is displayed
		4. Fill valid values into all required fields	
		5. Select McRoom0 in "Multi-Channel Room Models" section	
		6. Click on "Save" button	
		VP: The Device Detail Page is displayed
		7. Click on "Publish" button	
		VP: Verify that app/device is published successfully and don't have error message "Room Models should be republished." is pop-up in the current page */
		
		//1. Log into DTS portal as DTS admin	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Click on "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: The Device Edit page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()),true);
		//4. Fill valid values into all required fields	
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		deviceData.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		//5. Select McRoom0 in "Multi-Channel Room Models" section
		appDeviceControl.selectACheckbox(DeviceEdit.CHECK_BOX_MCROOM_1ST);
		//6. Click on "Save" button	
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The Device Detail Page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")),true);
		//7. Click on "Publish" button	
		appDeviceControl.click(DeviceInfo.PUBLISH);
		/*
		 * VP: Verify that app/device is published successfully and don't have error message "Room Models should be republished." is pop-up in the current page
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.PUBLICATION_STATUS),"Published");
		
		// Delete app/device
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.selectADeviceByName(deviceData.get("name"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);

	}
	
	/*
	 * Verify that McRoom1 is checked for the publish status when publishing an app/device
	 */
	@Test
	public void TC650DaDEN_69(){
		Reporter.log("Verify that McRoom1 is checked for the publish status when publishing an app/device");
		
		/*1. Log into DTS portal as DTS admin	
		2. Navigate to "Apps & Devices" page	
		3. Click on "Add App or Device" link	
		VP: The Device Edit page is displayed
		4. Fill valid values into all required fields	
		5. Select McRoom1 in "Multi-Channel Room Models" section	
		6. Click on "Save" button	
		VP: The Device Detail Page is displayed
		7. Click on "Publish" button	
		VP: Verify that app/device is published successfully and don't have error message "Room Models should be republished." is pop-up in the current page */
		
		
		//1. Log into DTS portal as DTS admin	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Click on "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: The Device Edit page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()),true);
		//4. Fill valid values into all required fields	
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		deviceData.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		//5. Select McRoom1 in "Multi-Channel Room Models" section
		appDeviceControl.selectACheckbox(DeviceEdit.CHECK_BOX_MCROOM_2ND);
		//6. Click on "Save" button	
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The Device Detail Page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")),true);
		//7. Click on "Publish" button	
		appDeviceControl.click(DeviceInfo.PUBLISH);
		/*
		 * VP: Verify that app/device is published successfully and don't have error message "Room Models should be republished." is pop-up in the current page
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.PUBLICATION_STATUS),"Published");
		
		// Delete app/device
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.selectADeviceByName(deviceData.get("name"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that McRoom2 is checked for the publish status when publishing an app/device
	 */
	@Test
	public void TC650DaDEN_70(){
		Reporter.log("Verify that McRoom2 is checked for the publish status when publishing an app/device");
		
		/*1. Log into DTS portal as DTS admin	
		2. Navigate to "Apps & Devices" page	
		3. Click on "Add App or Device" link	
		VP: The Device Edit page is displayed
		4. Fill valid values into all required fields	
		5. Select McRoom2 in "Multi-Channel Room Models" section	
		6. Click on "Save" button	
		VP: The Device Detail Page is displayed
		7. Click on "Publish" button	
		VP: Verify that app/device is published successfully and don't have error message "Room Models should be republished." is pop-up in the current page */
		
		//1. Log into DTS portal as DTS admin	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Click on "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: The Device Edit page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()),true);
		//4. Fill valid values into all required fields	
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		deviceData.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		//5. Select McRoom2 in "Multi-Channel Room Models" section
		appDeviceControl.selectACheckbox(DeviceEdit.CHECK_BOX_MCROOM_3TH);
		//6. Click on "Save" button	
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The Device Detail Page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")),true);
		//7. Click on "Publish" button	
		appDeviceControl.click(DeviceInfo.PUBLISH);
		/*
		 * VP: Verify that app/device is published successfully and don't have error message "Room Models should be republished." is pop-up in the current page
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.PUBLICATION_STATUS),"Published");
		
		// Delete app/device
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.selectADeviceByName(deviceData.get("name"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		
	}
	
	/*
	 * Verify that McRoom0 and McRoom1 are checked for the publish status when publishing an app/device
	 */
	@Test
	public void TC650DaDEN_71(){
		Reporter.log("Verify that McRoom0 and McRoom1 are checked for the publish status when publishing an app/device");
		
		/*1. Log into DTS portal as DTS admin	
		2. Navigate to "Apps & Devices" page	
		3. Click on "Add App or Device" link	
		VP: The Device Edit page is displayed
		4. Fill valid values into all required fields 	
		5. Select McRoom0 and McRoom1 in "Multi-Channel Room Models" section	
		6. Click on "Save" button	
		VP: The Device Detail Page is displayed
		7. Click on "Publish" button	
		VP: Verify that app/device is published successfully and don't have error message "Room Models should be republished." is pop-up in the current page*/
		
		//1. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Click on "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: The Device Edit page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()),true);
		//4. Fill valid values into all required fields	
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		deviceData.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		//5. Select McRoom0 and McRoom1 in "Multi-Channel Room Models" section
		appDeviceControl.selectACheckbox(DeviceEdit.CHECK_BOX_MCROOM_1ST);
		appDeviceControl.selectACheckbox(DeviceEdit.CHECK_BOX_MCROOM_2ND);
		//6. Click on "Save" button	
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The Device Detail Page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")),true);
		//7. Click on "Publish" button	
		appDeviceControl.click(DeviceInfo.PUBLISH);
		/*
		 * VP: Verify that app/device is published successfully and don't have error message "Room Models should be republished." is pop-up in the current page
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.PUBLICATION_STATUS),"Published");	
		
		// Delete app/device
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.selectADeviceByName(deviceData.get("name"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);

	}
	
	/*
	 * Verify that McRoom0 and McRoom2 are checked for the publish status when publishing an app/device
	 */
	@Test
	public void TC650DaDEN_72(){
		Reporter.log("Verify that McRoom0 and McRoom2 are checked for the publish status when publishing an app/device");

		/*1. Log into DTS portal as DTS admin	
		2. Navigate to "Apps & Devices" page	
		3. Click on "Add App or Device" link	
		VP: The Device Edit page is displayed
		4. Fill valid values into all required fields 	
		5. Select McRoom0 and McRoom2 in "Multi-Channel Room Models" section	
		6. Click on "Save" button	
		VP: The Device Detail Page is displayed
		7. Click on "Publish" button	
		VP: Verify that app/device is published successfully and don't have error message "Room Models should be republished." is pop-up in the current page*/
		
		
		//1. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Click on "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: The Device Edit page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()),true);
		//4. Fill valid values into all required fields	
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		deviceData.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		//5. Select McRoom0 and McRoom2 in "Multi-Channel Room Models" section
		appDeviceControl.selectACheckbox(DeviceEdit.CHECK_BOX_MCROOM_1ST);
		appDeviceControl.selectACheckbox(DeviceEdit.CHECK_BOX_MCROOM_3TH);
		//6. Click on "Save" button	
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The Device Detail Page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")),true);
		//7. Click on "Publish" button	
		appDeviceControl.click(DeviceInfo.PUBLISH);
		/*
		 * VP: Verify that app/device is published successfully and don't have error message "Room Models should be republished." is pop-up in the current page
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.PUBLICATION_STATUS),"Published");
		
		// Delete app/device
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.selectADeviceByName(deviceData.get("name"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that McRoom1 and McRoom2  are checked for the publish status when publishing an app/device
	 */
	@Test
	public void TC650DaDEN_73(){
		Reporter.log("Verify that McRoom1 and McRoom2  are checked for the publish status when publishing an app/device");
		
		/*1. Log into DTS portal as DTS admin	
		2. Navigate to "Apps & Devices" page	
		3. Click on "Add App or Device" link	
		VP: The Device Edit page is displayed
		4. Fill valid values into all required fields 	
		5. Select McRoom1 and McRoom2 in "Multi-Channel Room Models" section	
		6. Click on "Save" button	
		VP: The Device Detail Page is displayed
		7. Click on "Publish" button	
		VP: Verify that app/device is published successfully and don't have error message "Room Models should be republished." is pop-up in the current page*/
		
		//1. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Click on "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: The Device Edit page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()),true);
		//4. Fill valid values into all required fields	
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		deviceData.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		//5. Select McRoom1 and McRoom2 in "Multi-Channel Room Models" section
		appDeviceControl.selectACheckbox(DeviceEdit.CHECK_BOX_MCROOM_2ND);
		appDeviceControl.selectACheckbox(DeviceEdit.CHECK_BOX_MCROOM_3TH);
		//6. Click on "Save" button	
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The Device Detail Page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")),true);
		//7. Click on "Publish" button	
		appDeviceControl.click(DeviceInfo.PUBLISH);
		/*
		 * VP: Verify that app/device is published successfully and don't have error message "Room Models should be republished." is pop-up in the current page
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.PUBLICATION_STATUS),"Published");
		
		// Delete app/device
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.selectADeviceByName(deviceData.get("name"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that all McRoom are checked for the publish status when publishing an app/device
	 */
	@Test
	public void TC650DaDEN_74(){
		Reporter.log("Verify that all McRoom are checked for the publish status when publishing an app/device");
		
		/*1. Log into DTS portal as DTS admin	
		2. Navigate to "Apps & Devices" page	
		3. Click on "Add App or Device" link	
		VP: The Device Edit page is displayed
		4. Fill valid values into all required fields	
		5. Select McRoom0, McRoom1 and McRoom2 in "Multi-Channel Room Models" section	
		6. Click on "Save" button	
		VP: The Device Variant Edit Page is displayed
		7. Click on "Publish" button	
		VP: Verify that app/device is published successfully and don't have error message "Room Models should be republished." is pop-up in the current page*/
		
		
		//1. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Click on "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: The Device Edit page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()),true);
		//4. Fill valid values into all required fields	
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		deviceData.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		//5. Select McRoom0, McRoom1 and McRoom2 in "Multi-Channel Room Models" section
		appDeviceControl.selectACheckbox(DeviceEdit.CHECK_BOX_MCROOM_1ST);
		appDeviceControl.selectACheckbox(DeviceEdit.CHECK_BOX_MCROOM_2ND);
		appDeviceControl.selectACheckbox(DeviceEdit.CHECK_BOX_MCROOM_3TH);
		//6. Click on "Save" button	
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The Device Detail Page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")),true);
		//7. Click on "Publish" button	
		appDeviceControl.click(DeviceInfo.PUBLISH);
		/*
		 * VP: Verify that app/device is published successfully and don't have error message "Room Models should be republished." is pop-up in the current page
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.PUBLICATION_STATUS),"Published");
		
		// Delete app/device
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.selectADeviceByName(deviceData.get("name"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);

	}
	
	/*
	 * Verify that offline DB add a new table "BluetoothLookup" to store infomations of BT device name associated product
	 */
	@Test
	public void TC650DaDEN_75(){
		Reporter.log("Verify that offline DB add a new table 'BluetoothLookup' to store infomations of BT device name associated product");
		
		/*
		1. Log into DTS portal as DTS admin	
		2. Navigate to "Products" page	
		3. Click "Add product" link	
		4. Add some new BT products of different brands with some BT device names successfully	
		5. Publish above product successfully	
		6. Navigate to "Apps & Devices" page	
		7. Select "Add app or device" 	
		8. Input value to all section	
		9. Click "Save" button	
		10. Click "Publish" link	
		11. Click "Refresh offline db" link	
		12. Download offline db	
		13. Open offline db, go to "BluetoothLookup" table	
		VP: Verify data of "BlueLookup" that stored the correct BT device name of product belongs to all brand
		14. Click "Edit" link	
		15. Select "Only the following brands" in Headphone Brands section	
		16. Input a specified brand into "Only the following brands" field	
		17. Click "Save" link	
		18. Repeat from step 10 to step 13	
		VP: Verify data of "BlueLookup" that stored correct BT device name of product belongs to specified brand
		19. Click "Edit" link	
		20. Input in-box headphone that has BT device name but not belong to specified brand above	
		21. Repeat from step 17 to step 18	
		VP: Verify data of "BlueLookup" that stored correct BT device name of product belongs to specified brand and in-box headphone 
		22. Click "Edit" link	
		23. Delete specified brand in "Only the following brands"	
		24. Input other brand into "Only the following brands" field	
		25. Click "Save" link	
		26. Repeat from step 10 to step 13	
		VP: Verify data of "BlueLookup" that stored correct BT device name of product belongs to specified brand, associated promotion and in-box headphone 

		 */
		
//		1. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		appDeviceControl.click(PageHome.LINK_COMPANY);
//		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
//		appDeviceControl.click(CompanyInfo.ADD_BRAND);
//		Hashtable<String,String> newBrand = TestData.brandDraft();
//		companyControl.addBrand(AddBrand.getHash(),newBrand);
//		2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.linkAccessories);
//		3. Click "Add product" link	
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
//		4. Add some new BT products of different brands with some BT device names successfully
//		5. Publish above product successfully
		Hashtable<String,String> partnerBT1 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, true, true);
		partnerBT1.remove("wired");
		partnerBT1.remove("save");
		partnerBT1.put("bluetooth","1 (Mono)");
		partnerBT1.put("add tunning", AddEditProductModel.FileUpload.Tuning_HPXTT_Bluetooth.getName());
		partnerBT1.put("tuning rating", "A");
		partnerBT1.remove("save");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),partnerBT1);
		appDeviceControl.editData(AddEditProductModel.BT_DEVICE_NAME_FIELD,"Bluetooth Device Name 1");
		appDeviceControl.click(AddEditProductModel.BT_DEVICE_NAME_ADD);
		appDeviceControl.editData(AddEditProductModel.BT_DEVICE_NAME_FIELD,"Bluetooth Device Name 2");
		appDeviceControl.click(AddEditProductModel.BT_DEVICE_NAME_ADD);
		appDeviceControl.editData(AddEditProductModel.BT_DEVICE_NAME_FIELD,"Bluetooth Device Name 3");
		appDeviceControl.click(AddEditProductModel.BT_DEVICE_NAME_ADD);
		appDeviceControl.click(AddEditProductModel.SAVE_PRODUCT);
		productWf.approveTuning();
		productWf.approveMarketing();
		appDeviceControl.click(ProductDetailModel.PUBLISH);
		
		appDeviceControl.click(PageHome.linkAccessories);
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dtsBT = TestData.productData(DTS_COMPANY_NAME,DTS_BRAND_NAME, false, true, true);
		dtsBT.remove("wired");
		dtsBT.remove("save");
		dtsBT.put("bluetooth","1 (Mono)");
		dtsBT.put("add tunning", AddEditProductModel.FileUpload.Tuning_HPXTT_Bluetooth.getName());
		dtsBT.put("tuning rating", "A");
		dtsBT.remove("save");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),dtsBT);
		appDeviceControl.editData(AddEditProductModel.BT_DEVICE_NAME_FIELD,"Bluetooth Device Name 4");
		appDeviceControl.click(AddEditProductModel.BT_DEVICE_NAME_ADD);
		appDeviceControl.editData(AddEditProductModel.BT_DEVICE_NAME_FIELD,"Bluetooth Device Name 5");
		appDeviceControl.click(AddEditProductModel.BT_DEVICE_NAME_ADD);
		appDeviceControl.editData(AddEditProductModel.BT_DEVICE_NAME_FIELD,"Bluetooth Device Name 6");
		appDeviceControl.click(AddEditProductModel.BT_DEVICE_NAME_ADD);
		appDeviceControl.click(AddEditProductModel.SAVE_PRODUCT);
		productWf.approveTuning();
		productWf.approveMarketing();
		appDeviceControl.click(ProductDetailModel.PUBLISH);
		
		appDeviceControl.click(PageHome.linkAccessories);
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> partnerBT2 = TestData.productData(COMPANY_NAME_TEST, BRAND_NAME_TEST, false, true, true);
		partnerBT2.remove("wired");
		partnerBT2.remove("save");
		partnerBT2.put("bluetooth","1 (Mono)");
		partnerBT2.put("add tunning", AddEditProductModel.FileUpload.Tuning_HPXTT_Bluetooth.getName());
		partnerBT2.put("tuning rating", "A");
		partnerBT2.remove("save");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),partnerBT2);
		appDeviceControl.editData(AddEditProductModel.BT_DEVICE_NAME_FIELD,"Bluetooth Device Name 7");
		appDeviceControl.click(AddEditProductModel.BT_DEVICE_NAME_ADD);
		appDeviceControl.editData(AddEditProductModel.BT_DEVICE_NAME_FIELD,"Bluetooth Device Name 8");
		appDeviceControl.click(AddEditProductModel.BT_DEVICE_NAME_ADD);
		appDeviceControl.editData(AddEditProductModel.BT_DEVICE_NAME_FIELD,"Bluetooth Device Name 9");
		appDeviceControl.click(AddEditProductModel.BT_DEVICE_NAME_ADD);
		appDeviceControl.click(AddEditProductModel.SAVE_PRODUCT);
		productWf.approveTuning();
		productWf.approveMarketing();
		appDeviceControl.click(ProductDetailModel.PUBLISH);
		
//		6. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		7. Select "Add app or device" 
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		8. Input value to all section
//		9. Click "Save" button
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
//		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		appDeviceControl.createADeviceOneBrand(DeviceEdit.getHash(), deviceData, BRAND_NAME_TEST);
//		10. Click "Publish" link
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String deviceUuid = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		11. Click "Refresh offline db" link	
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		12. Download offline db	
		appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE);
//		13. Open offline db, go to "BluetoothLookup" table
		/*
		 * VP: Verify data of "BlueLookup" that stored the correct BT device name of product belongs to all brand
		 */
//		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(deviceUuid),"select p.Name as ProductName,b.Name as BluetoothDieviceName from Product p join BluetoothLookup b on b.Uuid = p.Uuid","ProductName",partnerBT1.get("name")));
//		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(deviceUuid),"select p.Name as ProductName,b.Name as BluetoothDieviceName from Product p join BluetoothLookup b on b.Uuid = p.Uuid","ProductName",partnerBT2.get("name")));
//		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(deviceUuid),"select p.Name as ProductName,b.Name as BluetoothDieviceName from Product p join BluetoothLookup b on b.Uuid = p.Uuid","ProductName",dtsBT.get("name")));
//		//		14. Click "Edit" link
//		appDeviceControl.editVersion();
////		15. Select "Only the following brands" in Headphone Brands section
////		16. Input a specified brand into "Only the following brands" field
//		appDeviceControl.click(DeviceEdit.GLOBAL_PROMOTIONS_ON);
//		appDeviceControl.click(DeviceEdit.ONLY_BRANDS);
//		appDeviceControl.inputSpecifiedBrand(DTS_BRAND_NAME);
////		17. Click "Save" link	
//		appDeviceControl.click(DeviceEdit.SAVE);
////		18. Repeat from step 10 to step 13
//		appDeviceControl.click(DeviceInfo.PUBLISH);
//		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
//		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		appDeviceControl.downloadFile(DeviceInfo.DOWLOAD_DATABASE);
		/*
		 * VP: Verify data of "BlueLookup" that stored correct BT device name of product belongs to specified brand
		 */
		Assert.assertFalse(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(deviceUuid),"select p.Name as ProductName,b.Name as BluetoothDieviceName from Product p join BluetoothLookup b on b.Uuid = p.Uuid","ProductName",partnerBT1.get("name")));
		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(deviceUuid),"select p.Name as ProductName,b.Name as BluetoothDieviceName from Product p join BluetoothLookup b on b.Uuid = p.Uuid","ProductName",partnerBT2.get("name")));
		Assert.assertFalse(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(deviceUuid),"select p.Name as ProductName,b.Name as BluetoothDieviceName from Product p join BluetoothLookup b on b.Uuid = p.Uuid","ProductName",dtsBT.get("name")));
//		19. Click "Edit" link
		appDeviceControl.editVersion();
//		20. Input in-box headphone that has BT device name but not belong to specified brand above
		appDeviceControl.inputInboxHP(DeviceEdit.INBOX_FIELD, partnerBT1.get("name"));
		appDeviceControl.click(DeviceEdit.SAVE);
//		21. Repeat from step 17 to step 18
		appDeviceControl.click(DeviceInfo.PUBLISH);
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE);
		/*
		 * VP: Verify data of "BlueLookup" that stored correct BT device name of product belongs to specified brand and in-box headphone 
		 */
		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(deviceUuid),"select p.Name as ProductName,b.Name as BluetoothDieviceName from Product p join BluetoothLookup b on b.Uuid = p.Uuid","ProductName",partnerBT2.get("name")));
		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(deviceUuid),"select p.Name as ProductName,b.Name as BluetoothDieviceName from Product p join BluetoothLookup b on b.Uuid = p.Uuid","ProductName",partnerBT1.get("name")));
		Assert.assertFalse(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(deviceUuid),"select p.Name as ProductName,b.Name as BluetoothDieviceName from Product p join BluetoothLookup b on b.Uuid = p.Uuid","ProductName",dtsBT.get("name")));
//		22. Click "Edit" link
//		appDeviceControl.editVersion();
////		23. Delete specified brand in "Only the following brands"
//		appDeviceControl.doDelete(DeviceEdit.BRAND_ICON_TRASH);
////		24. Input other brand into "Only the following brands" field
//		appDeviceControl.inputSpecifiedBrand(PARTNER_BRAND_NAME_2);
////		25. Click "Save" link
//		appDeviceControl.click(DeviceEdit.SAVE);
////		26. Repeat from step 10 to step 13	
//		appDeviceControl.click(DeviceInfo.PUBLISH);
//		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
//		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		appDeviceControl.downloadFile(DeviceInfo.DOWLOAD_DATABASE);
//		/*
//		 * VP: Verify data of "BlueLookup" that stored correct BT device name of product belongs to specified brand, associated promotion and in-box headphone 
//		 */
//		
	
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		appDeviceControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(partnerBT1.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		productControl.selectAccessorybyName(partnerBT2.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		productControl.selectAccessorybyName(dtsBT.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that USB audio route will be added to the Audio Route dropdown list
	 */
	@Test
	public void TC650DaDEN_76(){
		Reporter.log("Verify that USB audio route will be added to the Audio Route dropdown list");
		/*
		1. Log into DTS portal as a DTS user	
		2. Navigate to "Apps & Devices" page	
		3. Click â€œAdd App or Deviceâ€� link	
		VP: The ""Audio Route"" combobox contains: 
		""-Select-"",
		â€œLine-out0 â€“ Lineoutâ€�,
		""Bluetooth0 â€“ Bluetooth"",
		â€œUSB0 â€“ USBâ€�,
		""Attached0 - Internal Speakers (Default)"", 
		""Attached1 - Internal Speakers (Off)"",
		""Attached2 - Internal Speakers (mode 1)"",
		""Attached3 - Internal Speakers (mode 2)"",
		""Attached4 - Internal Speakers (mode 3)"",
		""Attached5 - Internal Speakers (mode 4)"", 
		""Combo0 - Dual Audio"" items"
		4. Select USB audio route in â€œAudio Routeâ€� dropdown list	
		VP: Verify that â€œStandard Accessoriesâ€� dropdown includes: â€œOver-Ear Headphonesâ€�, 
		â€œEarbudsâ€�, â€œEar-pieceâ€�, â€œExternal Speakersâ€�, â€œCar-Audioâ€�
		 */
		
//		1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
//		2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		/*
		VP: The ""Audio Route"" combobox contains: 
		"-Select-",
		â€œLine-out0 â€“ Lineoutâ€�,
		""Bluetooth0 â€“ Bluetooth"",
		â€œUSB0 â€“ USBâ€�,
		""Attached0 - Internal Speakers (Default)"", 
		""Attached1 - Internal Speakers (Off)"",
		""Attached2 - Internal Speakers (mode 1)"",
		""Attached3 - Internal Speakers (mode 2)"",
		""Attached4 - Internal Speakers (mode 3)"",
		""Attached5 - Internal Speakers (mode 4)"", 
		""Combo0 - Dual Audio"" items"
		 */
		Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE),DeviceEdit.routes.getNames()));
//		4. Select USB audio route in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.USB0.getName());
		/*
		 VP: Verify that â€œStandard Accessoriesâ€� dropdown includes: â€œOver-Ear Headphonesâ€�, â€œEarbudsâ€�, 
		 â€œEar-pieceâ€�, â€œExternal Speakersâ€�, â€œCar-Audioâ€�
		 */
		Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE),DeviceEdit.Standard_Accessories.getNames()));
	}
	
	/*
	 * Verify that the invalid route tuning file is uploaded unsuccessfully when selecting USB audio route
	 */
	@Test
	public void TC650DaDEN_77(){
		Reporter.log("Verify that the invalid route tuning file is uploaded unsuccessfully when selecting USB audio route");
		/*
		1. Log into DTS portal as a DTS user	
		2. Navigate to "Apps & Devices" page	
		3. Click â€œAdd App or Deviceâ€� link	
		VP: The Device Edit page is displayed
		4. Select USB audio route in â€œAudio Routeâ€� dropdown list	
		5. Select â€œOver-Ear Headphonesâ€� in â€œStandard Accessoriesâ€� dropdown list	
		6. Click on "Select file" button in "Custom Tuning" section	
		7. Upload the custom tuning DTSCS file that having route not match	
		VP: Verify that â€œ! Invalid file: The Route object does not match the Audio Route selection.â€� message is displayed
		 */
		
//		1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: The Device Edit page is displayed
		 */
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_High.getType());
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
//		4. Select USB audio route in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.USB0.getName());
//		5. Select â€œOver-Ear Headphonesâ€� in â€œStandard Accessoriesâ€� dropdown list	
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
//		6. Click on "Select file" button in "Custom Tuning" section
//		7. Upload the custom tuning DTSCS file that having route not match
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Over_Ear_Headphones_Bluetooth.getName());
		/*
		 * VP: Verify that '! Invalid file: The Route object does not match the Audio Route selection.' message is displayed
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS).contains(DeviceEdit.requires.Invalid_file_contents.getName()));
	}
	
	/*
	 * Verify that the valid route tuning file is uploaded successfully when selecting USB audio route
	 */
	@Test
	public void TC650DaDEN_78(){
		Reporter.log("Verify that the valid route tuning file is uploaded successfully when selecting USB audio route");
		/*
		1. Log into DTS portal as a DTS user	
		2. Navigate to "Apps & Devices" page	
		3. Click â€œAdd App or Deviceâ€� link	
		VP: The Device Edit page is displayed
		4. Select USB audio route in â€œAudio Routeâ€� dropdown list	
		5. Select â€œOver-Ear Headphonesâ€� in â€œStandard Accessoriesâ€� dropdown list	
		6. Click on "Select file" button in "Custom Tuning" section	
		7. Upload the valid custom tuning DTSCS file	
		VP: Verify that the valid tuning DTSCS file is uploaded successfully
		 */
		
//		1. Log into DTS portal as a DTS use
		loginControl.login(DTS_USER,DTS_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: The Device Edit page is displayed
		 */
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_High.getType());
//		4. Select USB audio route in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.USB0.getName());
//		5. Select â€œOver-Ear Headphonesâ€� in â€œStandard Accessoriesâ€� dropdown list	
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
//		6. Click on "Select file" button in "Custom Tuning" section	
//		7. Upload the valid custom tuning DTSCS file	
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Over_Ear_Headphones_USB.getName());
		/*
		 * VP: Verify that the valid tuning DTSCS file is uploaded successfully
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME));
	}
	
	/*
	 * Verify that the Complete Profile include USB with route type is AR_OUT_USB
	 */
	@Test
	public void TC650DaDEN_79(){
		Reporter.log("Verify that the Complete Profile include USB with route type is AR_OUT_USB");
		/*
		1. Log into DTS portal as a DTS user	
		2. Navigate to "Apps & Devices" page	
		3. Click â€œAdd App or Deviceâ€� link	
		VP: The Device Edit page is displayed
		4. Fill valid value into all required fields	
		5. Leave the Product Type panel as â€œHPX Highâ€�	
		6. Select USB audio route in â€œAudio Routeâ€� dropdown list	
		7. Select â€œOver-Ear Headphonesâ€� in â€œStandard Accessoriesâ€� dropdown list	
		8. Upload the valid custom tuning DTSCS file	
		9. Click â€œSaveâ€� link	
		10. Publish above device	
		11. Download â€œComplete Profileâ€�	
		VP: Verify that the Complete Profile include USB with route type is AR_OUT_USB
		 */
		
//		1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
//		2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: The Device Edit page is displayed
		 */
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
//		4. Fill valid value into all required fields
//		5. Leave the Product Type panel as â€œHPX Highâ€�	
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		deviceData.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
//		6. Select USB audio route in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.USB0.getName());
//		7. Select â€œOver-Ear Headphonesâ€� in â€œStandard Accessoriesâ€� dropdown list	
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
//		8. Upload the valid custom tuning DTSCS file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Over_Ear_Headphones_USB.getName());
//		9. Click â€œSaveâ€� link
		appDeviceControl.click(DeviceEdit.SAVE);
//		10. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
//		11. Download â€œComplete Profileâ€�
		String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK);
		/*
		 * VP: Verify that the Complete Profile include USB with route type is AR_OUT_USB
		 */
		Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id,"AR_OUT_USB"));
		
	}
	
	/*
	 * Verify that the Offline DB include USB in the AudioRouteEnum table.
	 */
	@Test
	public void TC650DaDEN_80(){
		Reporter.log("Verify that the Offline DB include USB in the AudioRouteEnum table.");
		/*
		1. Log into DTS portal as a DTS user	
		2. Navigate to "Apps & Devices" page	
		3. Click â€œAdd App or Deviceâ€� link	
		VP: The Device Edit page is displayed
		4. Fill valid value into all required fields	
		5. Leave the Product Type panel as â€œHPX Highâ€�	
		6. Click â€œSaveâ€� link	
		7. Publish above device	
		8. Refresh offline DB	
		9. Click "Download Offline Database" link	
		10. Open the offline databse file by SQLite	
		11. Navigate to AudioRouteEnum table in SQLite	
		VP: Verify that AudioRouteEnum table contains Line-Out, Bluetooth and USB
		 */
		
//		1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click â€œAdd App or Deviceâ€� link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: The Device Edit page is displayed
		 */
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
//		4. Fill valid value into all required fields
//		5. Leave the Product Type panel as â€œHPX Highâ€�
//		6. Click â€œSaveâ€� link
		Hashtable<String,String> dataDevice = TestData.appDevicePublish();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),dataDevice);
//		7. Publish above device	
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String dts_db = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		8. Refresh offline DB	
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		9. Click "Download Offline Database" link
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//		10. Open the offline database file by SQLite
//		11. Navigate to AudioRouteEnum table in SQLite	
		/*
		 * VP: Verify that AudioRouteEnum table contains Line-Out, Bluetooth and USB
		 */
		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_db),"Select Value from AudioRouteEnum","Value","Line-Out"));
		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_db),"Select Value from AudioRouteEnum","Value","Bluetooth"));
		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_db),"Select Value from AudioRouteEnum","Value","USB"));
		
		// Delete data
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the Offline DB include USB in the ProductAudioRoute table.
	 */
	@Test
	public void TC650DaDEN_81(){
		Reporter.log("Verify that the Offline DB include USB in the ProductAudioRoute table.");
		/*
		1. Log into DTS portal as a DTS user	
		2. Navigate to "Products" page	
		3. Click â€œAdd Productâ€� link	
		4. Fill valid value into all required fields	
		5. Select "USB" in "Connection Type"	
		6. Input a USB device name into "USB Device Name" field	
		7. Click "Add" button	
		8. Click â€œSaveâ€� link	
		9. Publish above product	
		10. Navigate to "Apps & Devices" page	
		11. Click â€œAdd App or Deviceâ€� link	
		VP: The Device Edit page is displayed
		12. Fill valid value into all required fields	
		13. Leave the Product Type panel as â€œHPX Highâ€�	
		14. Input above product into â€œIn-box Headphonesâ€� section	
		15. Click â€œSaveâ€� link	
		16. Publish above device	
		17. Refresh offline DB	
		18. Click "Download Offline Database" link	
		19. Open the offline databse file by SQLite	
		20. Navigate to ProductAudioRoute table in SQLite	
		VP: Verify that this table contains above product's USB Enum Id
		VP: Verify that this table contains Standard Accessories are Line-Out, Bluetooth and USB Enum Id
		 */
		
//		1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
//		2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.linkAccessories);
//		3. Click â€œAdd Productâ€� link	
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
//		4. Fill valid value into all required fields
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productData.remove("save");
		productData.remove("wired");
		productData.put("usb","1 (Mono)");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),productData);
//		5. Select "USB" in "Connection Type"	
//		6. Input a USB device name into "USB Device Name" field	
//		7. Click "Add" button	
		appDeviceControl.editData(AddEditProductModel.USB_DEVICE_NAME_FIELD,"USB Device Name 1");
		appDeviceControl.click(AddEditProductModel.USB_DEVICE_NAME_ADD);
		appDeviceControl.editData(AddEditProductModel.USB_DEVICE_NAME_FIELD,"USB Device Name 2");
		appDeviceControl.click(AddEditProductModel.USB_DEVICE_NAME_ADD);
		appDeviceControl.editData(AddEditProductModel.USB_DEVICE_NAME_FIELD,"USB Device Name 3");
		appDeviceControl.click(AddEditProductModel.USB_DEVICE_NAME_ADD);
//		8. Click â€œSaveâ€� link	
		appDeviceControl.click(AddEditProductModel.SAVE_PRODUCT);
//		9. Publish above product
		productWf.approveTuning();
		productWf.approveMarketing();
		productControl.click(ProductDetailModel.PUBLISH);
//		10. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		11. Click â€œAdd App or Deviceâ€� link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: The Device Edit page is displayed
		 */
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
//		12. Fill valid value into all required fields	
//		13. Leave the Product Type panel as â€œHPX Highâ€�	
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		deviceData.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
//		14. Input above product into â€œIn-box Headphonesâ€� section
		appDeviceControl.inputInboxHP(DeviceEdit.INBOX_FIELD,productData.get("name"));
//		15. Click â€œSaveâ€� link
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		appDeviceControl.click(DeviceEdit.SAVE);
//		16. Publish above device	
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String dts_db = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		17. Refresh offline DB	
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		18. Click "Download Offline Database" link	
		appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE);
//		19. Open the offline databse file by SQLite	
//		20. Navigate to ProductAudioRoute table in SQLite	
		/*
		 * VP: Verify that this table contains above product's USB Enum Id
		 */
		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_db),"Select AudioRouteEnumId from ProductAudioRoute p, Product pr where p.ProductId = pr.Id and pr.Name ='" + productData.get("name")+"'","AudioRouteEnumId","14"));
		/*
		 * VP: Verify that this table contains Standard Accessories are Line-Out, Bluetooth and USB Enum Id
		 */
		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_db),"Select AudioRouteEnumId from ProductAudioRoute p, Product pr where p.ProductId = pr.Id and pr.Name ='Over-Ear Headphones'","AudioRouteEnumId","14"));
		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_db),"Select AudioRouteEnumId from ProductAudioRoute p, Product pr where p.ProductId = pr.Id and pr.Name ='Over-Ear Headphones'","AudioRouteEnumId","2"));
		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_db),"Select AudioRouteEnumId from ProductAudioRoute p, Product pr where p.ProductId = pr.Id and pr.Name ='Over-Ear Headphones'","AudioRouteEnumId","3"));
		
		// Delete data
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		appDeviceControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(productData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		
	}
	
	/*
	 * Verify that the Offline DB include USB Device Names in the UsbLookup table.
	 */
	@Test
	public void TC650DaDEN_82(){
		Reporter.log("Verify that the Offline DB include USB Device Names in the UsbLookup table.");
		/*
		1. Log into DTS portal as a DTS user	
		2. Navigate to "Products" page	
		3. Click â€œAdd Productâ€� link	
		4. Fill valid value into all required fields	
		5. Select "USB" in "Connection Type"	
		6. Input a USB device name into "USB Device Name" field	
		7. Click "Add" button	
		8. Click â€œSaveâ€� link	
		9. Publish above product	
		10. Navigate to "Apps & Devices" page	
		11. Click â€œAdd App or Deviceâ€� link	
		12. Leave the Product Type panel as â€œHPX Highâ€�	
		13. Fill valid value into all required fields	
		14. Input above product into â€œIn-box Headphonesâ€� section	
		15. Click â€œSaveâ€� link	
		16. Publish above device	
		17. Refresh offline DB	
		18. Click "Download Offline Database" link	
		19. Open the offline databse file by SQLite	
		20. Navigate to UsbLookup table in SQLite	
		VP: Verify that USB device name above is stored in UsbLookup table
		 */
		
//		1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
//		2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.linkAccessories);
//		3. Click â€œAdd Productâ€� link	
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
//		4. Fill valid value into all required fields
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productData.remove("save");
		productData.remove("wired");
		productData.put("usb","1 (Mono)");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),productData);
//		5. Select "USB" in "Connection Type"	
//		6. Input a USB device name into "USB Device Name" field	
//		7. Click "Add" button	
		appDeviceControl.editData(AddEditProductModel.USB_DEVICE_NAME_FIELD,"USB Device Name 1");
		appDeviceControl.click(AddEditProductModel.USB_DEVICE_NAME_ADD);
		appDeviceControl.editData(AddEditProductModel.USB_DEVICE_NAME_FIELD,"USB Device Name 2");
		appDeviceControl.click(AddEditProductModel.USB_DEVICE_NAME_ADD);
		appDeviceControl.editData(AddEditProductModel.USB_DEVICE_NAME_FIELD,"USB Device Name 3");
		appDeviceControl.click(AddEditProductModel.USB_DEVICE_NAME_ADD);
//		8. Click â€œSaveâ€� link	
		appDeviceControl.click(AddEditProductModel.SAVE_PRODUCT);
		String uuidPro = appDeviceControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
//		9. Publish above product	
		productWf.approveTuning();
		productWf.approveMarketing();
		productControl.click(ProductDetailModel.PUBLISH);
//		10. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		11. Click â€œAdd App or Deviceâ€� link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		12. Leave the Product Type panel as â€œHPX Highâ€�	
//		13. Fill valid value into all required fields	
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		deviceData.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
//		14. Input above product into â€œIn-box Headphonesâ€� section	
		appDeviceControl.inputInboxHP(DeviceEdit.INBOX_FIELD,productData.get("name"));
//		15. Click â€œSaveâ€� link	
		appDeviceControl.click(DeviceEdit.SAVE);
//		16. Publish above device	
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String dts_db = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		17. Refresh offline DB	
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		18. Click "Download Offline Database" link	
		appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE);
//		19. Open the offline databse file by SQLite	
//		20. Navigate to UsbLookup table in SQLite	
		/*
		 * VP: Verify that USB device name above is stored in UsbLookup table
		 */
		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_db),"Select Name from USBLookup where Uuid='"+uuidPro+"'","Name","USB Device Name 1"));
		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_db),"Select Name from USBLookup where Uuid='"+uuidPro+"'","Name","USB Device Name 2"));
		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_db),"Select Name from USBLookup where Uuid='"+uuidPro+"'","Name","USB Device Name 3"));
	
		// Delete data
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		appDeviceControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(productData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}

	/*
	 * Verify that the app/device's information displayed in edit page correctly.
	 */
	@Test
	public void TC650DbADE_01(){
		appDeviceControl.addLog("ID : TC650DbADE_01 : Verify that the title of 650Db Device Edit page is generated by Company, Brand and Model Name.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Select a device in apps & devices table
			4.Click "Edit Version" link
			VP: The title of 650Db Device Edit page is generated by Company + Brand + Model Name.
			VP: All information of app/device in app/device info page displayed in app/device edit page correctly
		*/
		/*
		 * PreCondition: Create new device
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to App & Devices page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// Click Create new device link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDeviceDraft();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * *******************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Select a device in apps & devices table
		ArrayList<String> deviceInfo = appDeviceControl.selectADeviceByName(data.get("name"));
		// 4.Click "Edit Version" link
		appDeviceControl.click(DeviceInfo.EDIT);
		/*
		 * Verify that The title of 650Db Device Edit page is generated by Company + Brand + Model Name
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.DEVICE_TILLE), deviceInfo.get(0) + " " + deviceInfo.get(1) + " " + deviceInfo.get(2));
		
		/*
		 * VP: All information of app/device in app/device info page displayed in app/device edit page correctly
		 */
		//List out all items of "Type" combobox
		ArrayList<String> optionsType = appDeviceControl.getAllComboboxOption(DeviceEdit.TYPE);
		/*
		 * Veriry that The "Type" combobox contains "-Select-", "App" and "Device" items
		 */
		Assert.assertTrue(ListUtil.containsAll(optionsType, DeviceEdit.types));
		//List out all items of "OS" combobox
		ArrayList<String> optionsOS = appDeviceControl.getAllOptionsInDropDown(DeviceEdit.OS);
		/*
		 * Verify that The "OS" combobox contains "-Select-", "Android", "iOS", "Windows Phone", "Windows", "Linux"  and "Other" items
		 */
		Assert.assertTrue(ListUtil.containsAll(optionsOS, DeviceEdit.os));
		//List out all items of "Audio Route" combobox in Custom Tuing module
		appDeviceControl.clickOptionByIndex(DeviceEdit.AUDIO_ROUTE_TYPE, 1);
		ArrayList<String> audioRoutes = appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE);
		productControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Line_out0.getName());
		ArrayList<String> standardAccessories = appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE);
		/*
		 * Verify that the 'Audio Routes' combobox contains items properly
		*/
		Assert.assertTrue(ListUtil.containsAll(audioRoutes, DeviceEdit.routes.getNames()));
		Assert.assertTrue(ListUtil.containsAll(standardAccessories, DeviceEdit.Standard_Accessories.getNames()));
		// Delete device
		appDeviceControl.click(DeviceEdit.CANCEL);
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	/*
	 * Verify that the "Default Audio Route" file is validated correctly in edit page
	 */
	@Test
	public void TC650DbADE_05(){
		appDeviceControl.addLog("ID : TC650DbADE_05 : Verify that the 'Default Audio Route' file is validated correctly in edit page");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Select a device in apps & devices table
			4.Click "Edit Version" link
			5. Delete the existing audio route file
				VP: The default uploaded audio route is deleted successfully.
			6. Try to upload invalid audio route tuning file in "Default Audio Route" module.
				VP: An error message is displayed and the invalid audio route tuning file is not added.
			7. Try to upload another default audio route file
				VP: Verify that new valid audio route file is uploaded successfully


		*/
		/*
		 * PreCondition: Create new device
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to App & Devices page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// Click Create new device link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDevicePublish();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * *******************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Select a device in apps & devices table 
		appDeviceControl.selectADeviceByName(data.get("name"));
		// 4. Click "Edit Version" link
		appDeviceControl.click(DeviceInfo.EDIT);
		// 5. Delete the existing audio route file
		appDeviceControl.click(DeviceEdit.DELETE_AUDIO_ROUTE_ICON);
		appDeviceControl.waitForElementClickable(PageHome.BTN_CONFIRMATION_DANGER);
		//Click "Delete" button in delete confirmation dialog
		appDeviceControl.click(PageHome.POPUP_DELETE);
		/*
		 * VP: The default uploaded audio route is deleted successfully.
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.ADD_AUDIO_ROUTE_CONTAINER));
		// 6. Try to upload invalid audio route tuning file in "Default Audio Route" module.
		appDeviceControl.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName());
		/*
		 * VP: Verify that An error message is displayed and the invalid audio route tuning file is not added
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE));
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE),DeviceEdit.requires.Invalid_file_type.getName());
		//7. Try to upload another default audio route file
		appDeviceControl.uploadFile(DeviceEdit.RETRY_DEFAULT_AUDIO_ROUTE, AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		/*
		 * VP: Verify that new valid audio route file is uploaded successfully
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.UPLOADED_DEFAULT_AUDIO_ROUTE).contains("Default Audio"));
		// Delete device
		appDeviceControl.click(DeviceEdit.CANCEL);
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}	
	/*
	 * Verify that the custom tuning file could be deleted successfully
	 */
	@Test
	public void TC650DbADE_14(){
		appDeviceControl.addLog("ID : TC650DbADE_14 : Verify that the custom tuning file could be deleted successfully");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as  DTS admin user
			2. Navigate to "Apps & Devices" page
			3. Select a device in apps & devices table
			4. Click "Edit Version" link
			5. Select "Choose File" button in "Custom Tuning" section
			6. Upload valid a tuning file successfully
			VP: The custom uploaded tuning file is displayed with a delete icon above the Custom Tuning module
			7. Click on trashcan icon
			8. Click "Delete" button in delete confirmation dialog
		*/
		/*
		 * PreCondition: Create new device
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to App & Devices page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// Click Create new device link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDeviceDraft();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * *******************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Select a device in apps & devices table 
		appDeviceControl.selectADeviceByName(data.get("name"));
		// 4.Click "Edit Version" link
		appDeviceControl.click(DeviceInfo.EDIT);
		// 5. Select "Choose File" button in "Custom Tuning" section
		// 6. Upload valid a tuning file successfully
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Bluetooth0.getName()); // Bluetooth0
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Ear_piece.getName()); // Ear-piece
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Earpiece_Bluetooth.getName());
		/*
		 * VP: The custom uploaded tuning file is displayed with a delete icon above the Custom Tuning module
		 */
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Earpiece_Bluetooth.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		// 7. Click on trashcan icon
		// 8. Click "Delete" button in delete confirmation dialog
		appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
		/*
		 * Verify that The Custom tuning is deleted successfully
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.ANOTHER_ROUTE), "");
		// Delete device
		appDeviceControl.click(DeviceEdit.CANCEL);
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that user can download custom tuning file when clicking on tuning link
	 */
	@Test
	public void TC650DbADE_15(){
		appDeviceControl.addLog("ID : TC650DbADE_15 : Verify that user can download custom tuning file when clicking on tuning link");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as  DTS admin user
			2. Navigate to "Apps & Devices" page
			3. Select a device in apps & devices table
			4. Click "Edit Version" link
			5. Select "Choose File" button in "Custom Tuning" section
			6. Upload a valid tuning file successfully
			7. Click on uploaded tuning link
		*/
		/*
		 * PreCondition: Create new device
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to App & Devices page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// Click Create new device link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDeviceDraft();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * *******************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Select a device in apps & devices table 
		appDeviceControl.selectADeviceByName(data.get("name"));
		// 4. Click "Edit Version" link
		appDeviceControl.click(DeviceInfo.EDIT);
		// 5. Select "Choose File" button in "Custom Tuning" section
		// 6. Upload a valid tuning file successfully
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Bluetooth0.getName()); // Bluetooth0
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Car_Audio.getName()); // Over-Ear Headphones
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Car_Audio_Bluetooth.getName());
		// 7. Click on uploaded tuning link
		Boolean isDownloaded = appDeviceControl.downloadFile(AddEditProductModel.FileUpload.Car_Audio_Bluetooth.getName());
		/*
		 * Verify that The download file dialog is displayed and user can download tuning file successfully
		 */
		Assert.assertTrue(isDownloaded);
		// Delete device
		appDeviceControl.click(DeviceEdit.CANCEL);
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the number of "Device Promotions" field displays based on number of "Promo Slot Available"
	 */
	@Test
	public void TC650DbADE_16(){
		appDeviceControl.addLog("ID : TC650DbADE_16 : Verify that the number of 'Device Promotions' field displays based on number of 'Promo Slot Available'");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as a DTS admin user
			2. Navigate to "Apps & Devices" page
			3. Select a device in apps & devices table
			4.Click "Edit Version" link
			5. Increase the number of "Promo Slot Available" to 7
		*/
		/*
		 * PreCondition: Create new device
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to App & Devices page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// Click Create new device link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDeviceDraft();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * *******************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Select a device in apps & devices table 
		appDeviceControl.selectADeviceByName(data.get("name"));
		// 4. Click "Edit Version" link
		appDeviceControl.click(DeviceInfo.EDIT);
		// 5. Increase the number of "Promo Slot Available" to 6
		appDeviceControl.editData(DeviceEdit.PROMO_SLOT, "6");
		appDeviceControl.autoTool.send("{ENTER}", false);
		/*
		 * Verify that The "Device Promotions" diplays 6 fields
		 */
		Assert.assertEquals(promotionControl.getTotalPromotionContainers(DeviceEdit.PROMOTION_CONTAINER), 4);
		// Delete device
		appDeviceControl.click(DeviceEdit.CANCEL);
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the new product's information could be saved successfully.
	 */
	@Test
	public void TC650DbADE_17(){
		appDeviceControl.addLog("ID : TC650DbADE_17 : Verify that the new product's information could be saved successfully.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as a DTS admin user
			2. Navigate to "Apps & Devices" page
			3. Select a device in apps & devices table
			4.Click "Edit Version" link
			5. Change product's information
			6. Click "Cancel" link
			VP: The portal is redirected to 640D Device Detail page and the product's information is not changed.
			7.Click "Edit Version" link
			8. Change product's information
			9. Click "Save" link
			VP: The 640D Device Detail page is displayed with new information.


		*/
		/*
		 * PreCondition: Create new device
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to App & Devices page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// Click Create new device link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDeviceDraft();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * *******************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Select a device in apps & devices table 
		appDeviceControl.selectADeviceByName(data.get("name"));
		// 4. Click "Edit Version" link
		appDeviceControl.click(DeviceInfo.EDIT);
		/*
		 * VP: The 650Db Device Edit page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()), true);
		// 5. Change product's information
		String newName5 = "Edit" + RandomStringUtils.randomNumeric(6);
		appDeviceControl.editData(DeviceEdit.NAME, newName5);
		// 6. Click "Cancel" link
		appDeviceControl.click(DeviceEdit.CANCEL);
		/*
		 * The portal is redirected to 640D Device Detail page and the product's information is not changed
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), data.get("name"));
		//7.Click "Edit Version" link
		appDeviceControl.click(DeviceInfo.EDIT);
		//8. Change product's information
		String newName8 = "Edit" + RandomStringUtils.randomNumeric(6);
		appDeviceControl.editData(DeviceEdit.NAME, newName8);
		//9. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * The 640D Device Detail page is displayed with new information
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), newName8);
		// Delete device
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that items in audio route combobox is removed after uploading the custom tuning file.
	 */
	@Test
	public void TC650DbADE_19(){
		appDeviceControl.addLog("ID : TC650DbADE_19 : Verify that items in audio route combobox is removed after uploading the custom tuning file.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as a DTS admin user
			2. Navigate to "Apps & Devices" page
			3. Select a device in apps & devices table
			4. Click "Edit Version" link
			VP: The 650Db Device Edit page is displayed
			5. Select an item in audio route combobox
			6. Upload a tuning file for above audio route item successfully
			7. List out the audio route combobox
		*/
		/*
		 * PreCondition: Create new device
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to App & Devices page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// Click Create new device link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDeviceDraft();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * *******************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Select a device in apps & devices table 
		appDeviceControl.selectADeviceByName(data.get("name"));
		// 4. Click "Edit Version" link
		appDeviceControl.click(DeviceInfo.EDIT);
		/*
		 * VP: The 650Db Device Edit page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()), true);
		// 5. Select an item in audio route combobox
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached0_Internal_Speakers.getName());
		// 6. Upload a tuning file for above audio route item successfully
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Custom.getName());
		// 7. List out the audio route combobox
		ArrayList<String> options = appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE);
		/*
		 * The audio route item of uploaded tuning file is removed from audio route combobox
		 */
		Assert.assertTrue(!options.contains(DeviceEdit.routes.Attached0_Internal_Speakers.getClass()));
		// Delete device
		appDeviceControl.click(DeviceEdit.CANCEL);
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the default audio route is required before publication
	 */
	@Test
	public void TC650DbADE_20(){
		appDeviceControl.addLog("ID : TC650DbADE_20 : Verify that the default audio route is required before publication");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as a DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			VP: Verify that the 650Da Device New page is displayed
			4. Fill valid value into all required fields
			5. Do not upload default audio route tuning file
			6. Click "Save" link
			VP: The 640D Device Detail page is displayed and new product is saved successfully
			7. Click "Publish" link
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: Verify that the 650Da Device New page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()), true);
		// 4. Fill valid value into all required fields
		// 5. Do not upload default audio route tuning file
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.appDeviceDraft();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * VP: The 640D Device Detail page is displayed and new product is saved successfully
		 */
		Assert.assertTrue(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")));
		// 7. Click "Publish" link
		appDeviceControl.click(DeviceInfo.PUBLISH);
		appDeviceControl.waitForElementClickable("//*[@href='javascript:;' and text() = 'OK']");
		/*
		 * The new product could not be published due to there is no default audio route tuning uploaded
		 */
		Assert.assertTrue(appDeviceControl.checkMessageDisplay("The Default Audio Route is required for all products"));
		// Delete device
		appDeviceControl.selectConfirmationDialogOption("OK");
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the "Multi-Channel Room Models", "Headphone Image Sizes" and "Headphone Brands" are validated in edit page
	 */
	@Test
	public void TC650DbADE_22(){
		appDeviceControl.addLog("ID : TC650DbADE_22 : Verify that the 'Multi-Channel Room Models', 'Headphone Image Sizes' and 'Headphone Brands' are validated in edit page");
		
		/*1. Log into DTS portal as DTS admin	
		2. Navigate to "Apps & Devices" page	
		3. Select a device in apps & devices table	
		4.Click "Edit Version" link
		VP: The 650Db Product Edit page is displayed
		5. Unselect all options in "Multi-Channel Room Models", "Headphone Image Sizes" and "Headphone Brands"	
		6. Click "Save" link
		VP: An error message is displayed
		7. Click "Cancel" link
		VP: The portal is redirected to 640D Device Detail page and the product's information that set are correct.
		*/
		
		//1. Log into DTS portal as DTS admin	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);	
		// Click Create new device link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDeviceDraft();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		//3. Select a device in apps & devices table
		appDeviceControl.selectADeviceByName(data.get("name"));
		//4.Click "Edit Version" link
		appDeviceControl.editVersion();
		/*
		 * VP: The 650Db Product Edit page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()),true);
		//5. Unselect all options in "Multi-Channel Room Models", "Headphone Image Sizes" and "Headphone Brands"
		appDeviceControl.uncheckACheckbox(DeviceEdit.CHECK_BOX_MCROOM_1ST);
		appDeviceControl.uncheckACheckbox(DeviceEdit.CHECK_BOX_MCROOM_2ND);
		appDeviceControl.uncheckACheckbox(DeviceEdit.CHECK_BOX_MCROOM_3TH);
		appDeviceControl.uncheckACheckbox(DeviceEdit.LARGE_IMAGE);
		appDeviceControl.uncheckACheckbox(DeviceEdit.MEDIUM_IMAGE);
		appDeviceControl.uncheckACheckbox(DeviceEdit.SMALL_IMAGE);
		appDeviceControl.click(DeviceEdit.ONLY_BRANDS);
		//6. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: An error message is displayed
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.ROOM_MODEL_REQUIRE),DeviceEdit.requires.Room_Models_is_required.getName());
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.IMAGE_SIZE_REQUIRE),DeviceEdit.requires.Headphone_image_sizes_is_required.getName());
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.HEADPHONE_BRAND_REQUIRE),DeviceEdit.requires.Brand_is_required.getName());
		//7. Click "Cancel" link
		appDeviceControl.click(DeviceEdit.CANCEL);
		/*
		 * VP: The portal is redirected to 640D Device Detail page and the product's information that set are correct.
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.SALESFORCE_ID),data.get("id"));
		
		// Delete app/device
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.selectADeviceByName(data.get("name"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);

	}
	
	/*
	 * Verify that the default audio route is required before publication
	 */
	@Test
	public void TC650DDEPT_01(){
		appDeviceControl.addLog("ID : TC650DDEPT_01 : Verify that Product Type feature is displayed correctly");
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			VP: The "Product Type" combobox contains: â€œ-Select-â€�, â€œHPX Highâ€�, â€œHPX Mediumâ€�, â€œHPX Lowâ€� items
			VP: The default value in "Product Type" combobox is "- Select -"
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: The "Product Type" combobox contains: â€œ-Select-â€�, â€œHPX Highâ€�, â€œHPX Mediumâ€�, â€œHPX Lowâ€� items
		 */
		ArrayList<String> typeList = appDeviceControl.getAllComboboxOption(DeviceEdit.PRODUCT_TYPE);
		Assert.assertTrue(ListUtil.containsAll(typeList, DeviceEdit.Product_Types.getNames()));
		/*
		 * VP: The default value in "Product Type" combobox is "- Select -"
		 */
		Assert.assertEquals(appDeviceControl.getItemSelected(DeviceEdit.PRODUCT_TYPE), "- Select -");
	}
	
	/*
	 * Verify that the default audio route is required before publication
	 */
	@Test
	public void TC650DDEPT_02(){// Default product type of any device depend on product type what we chose.
		appDeviceControl.addLog("ID : TC650DDEPT_02 : Verify that with the current device, the default Product Type will be updated to hpx_high.");
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Select a device in apps & devices table
			VP: Verify that 640D Device Detail page display Product Type panel as â€œHPX Highâ€�
		*/
/*		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Select a device in apps & devices table
		appDeviceControl.selectAnAppDevice();
		
		 * VP: Verify that 640D Device Detail page display Product Type panel as â€œHPX Highâ€�
		 
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceInfo.PRODUCT_TYPE).equals(DeviceInfo.Product_Types.HPX_High.getName()));*/
		
	}
	
	/*
	 * Verify that the default audio route is required before publication
	 */
	@Test
	public void TC650DDEPT_03(){
		appDeviceControl.addLog("ID : TC650DDEPT_03 : Verify that Product Type is HPX Low, the offline DB will not include featuredHeadphones, generic_accessories, stereo_preference and multi channel room data.");
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Fill valid value into all required fields
			5. Leave the Product Type panel as â€œHPX Lowâ€�
			6. Click â€œSaveâ€� link
			7. Publish device above
			8. Refresh offline database
			9. Click "Download Offline Database" link
			10. Open the offline database file by SQLite
			11. Navigate to DeviceFeaturedProduct table in SQLite
			VP: Verify that table doesn't display any featuredHeadphones
			12. Navigate to DeviceMultiChannelRoomModel table in SQLite
			VP: Verify that table doesn't display any multi channel room data.
			13. Navigate to Product table in SQLite
			VP: Verify that table doesn't display generic_accessories and stereo_preference and feature accessory

		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.appDevicePublishLow();
		data.remove("save");
		// 5. Leave the Product Type panel as â€œHPX Lowâ€�
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// 6. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 7. Publish device above
		appDeviceControl.click(DeviceInfo.PUBLISH);
		// 8. Refresh offline database
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		// 9. Click "Download Offline Database" link
		boolean isDownloaded = appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE);
		Assert.assertTrue(isDownloaded);
		// 10. Open the offline database file by SQLite
		// 11. Navigate to DeviceFeaturedProduct table in SQLite
		// VP: Verify that table doesn't display any featuredHeadphones
		Assert.assertTrue(appDeviceControl.isDBEmptyFeaturedHP(appDeviceControl.getOfflineDBName(dts_id)));
		// 12. Navigate to DeviceMultiChannelRoomModel table in SQLite
		// VP: Verify that table doesn't display any multi channel room data.
		Assert.assertFalse(appDeviceControl.isContainMultiCHRoom(appDeviceControl.getOfflineDBName(dts_id)));
		// 13. Navigate to Product table in SQLite
		// VP: Verify that table doesn't display generic_accessories and stereo_preference and feature accessory
		Assert.assertFalse(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_id), "select Type from Product", "Type", "Standard"));
		Assert.assertFalse(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_id), "select Type from Product", "Type", "Headphone"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the default audio route is required before publication
	 */
	@Test
	public void TC650DDEPT_04(){
		appDeviceControl.addLog("ID : TC650DDEPT_04 : Verify that Product Type is HPX Low, the offline DB will include In-box Headphones in product table");
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Fill valid value into all required fields
			5. Leave the Product Type panel as â€œHPX Lowâ€�
			6. Input an product to text field
			7. Click â€œSaveâ€� link
			8. Publish device above
			9. Refresh offline database
			10. Click "Download Offline Database" link
			11. Open the offline databse file by SQLite
			12. Navigate to Product table in SQLite
			VP: Verify data of inbox headphone that stored in  table are correctly

		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		
		// Create a published product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndPublish(dataProduct);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.appDevicePublishLow();
		data.remove("save");
		// 5. Leave the Product Type panel as â€œHPX Lowâ€�
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// 6. Input an product to text field
		appDeviceControl.inputInboxHP(DeviceEdit.INBOX_FIELD, dataProduct.get("name"));
		// 7. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 8. Publish device above
		appDeviceControl.click(DeviceInfo.PUBLISH);
		// 9. Refresh offline database
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		// 10. Click "Download Offline Database" link
		boolean isDownloaded = appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE);
		Assert.assertTrue(isDownloaded);
		// 11. Open the offline database file by SQLite
		// 12. Navigate to Product table in SQLite
		// VP: Verify data of inbox headphone that stored in  table are correctly
		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_id), "select Name from Product", "Name", dataProduct.get("name")));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the default audio route is required before publication
	 */
	@Test
	public void TC650DDEPT_05(){
		appDeviceControl.addLog("ID : TC650DDEPT_05 : Verify that Product Type is HPX Medium, the offline DB will not include headphone brands and multi channel room data.");
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Fill valid value into all required fields
			5. Leave the Product Type panel as â€œHPX Mediumâ€�
			6. Click â€œSaveâ€� link
			7. Publish device above
			8. Refresh offline database
			9. Click "Download Offline Database" link
			10. Open the offline databse file by SQLite
			11. Navigate to DeviceBrand table in SQLite
			VP: Verify that table doesn't display any headphone brands
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.appDevicePublishMedium();
		data.remove("save");
		// 5. Leave the Product Type panel as â€œHPX Mediumâ€�
		data.put("global promotion", "ON");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// 6. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 7. Publish device above
		appDeviceControl.click(DeviceInfo.PUBLISH);
		// 8. Refresh offline database
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		// 9. Click "Download Offline Database" link
		boolean isDownloaded = appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE);
		Assert.assertTrue(isDownloaded);
		// 10. Open the offline database file by SQLite
		// 11. Navigate to DeviceFeaturedProduct table in SQLite
		// VP: VP: Verify that table doesn't display any headphone brands
		Assert.assertTrue(appDeviceControl.isDBTableEmptyData(appDeviceControl.getOfflineDBName(dts_id), "select BrandId from DeviceBrand", "BrandId"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the default audio route is required before publication
	 */
	@Test
	public void TC650DDEPT_06(){
		appDeviceControl.addLog("ID : TC650DDEPT_06 : Verify that Product Type is HPX Medium, the offline DB will include featuredHeadphones, generic_accessories, inbox_accessory and stereo_preference");
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Fill valid value into all required fields
			5. Leave the Product Type panel as â€œHPX Mediumâ€�
			6. Input a product to text field
			7. Click â€œSaveâ€� link
			8. Publish device above
			9. Refresh offline database
			10. Click "Download Offline Database" link
			11. Open the offline database file by SQLite
			12. Navigate to DeviceMultiChannelRoomModel table in SQLite
			VP:Verify that table display one default Multi-channel room model
			13. Navigate to DeviceFeaturedProduct table in SQLite
			VP: Verify that table display  featuredHeadphones correctly
			14. Navigate to Product table in SQLite
			VP: Verify that table display generic_accessories, inbox_accessory and stereo_preference correctly

		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Create a published product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndPublish(dataProduct);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.appDevicePublishMedium();
		data.remove("save");
		// 5. Leave the Product Type panel as â€œHPX Mediumâ€�
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// 6. Input an product to text field
		appDeviceControl.inputInboxHP(DeviceEdit.INBOX_FIELD, dataProduct.get("name"));
		// 7. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 8. Publish device above
		appDeviceControl.click(DeviceInfo.PUBLISH);
		//ArrayList<String> listPromo = appDeviceControl.getListPromo("Published");
		// 9. Refresh offline database
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		// 10. Click "Download Offline Database" link
		boolean isDownloaded = appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE);
		Assert.assertTrue(isDownloaded);
		// 11. Open the offline database file by SQLite
		// 12. Navigate to DeviceMultiChannelRoomModel table in SQLite
		// VP:Verify that table display one default Multi-channel room model
		Assert.assertEquals(appDeviceControl.getSizeMultiCHRoom(appDeviceControl.getOfflineDBName(dts_id)), 1);
		// 13. Navigate to DeviceFeaturedProduct table in SQLite
		// VP: Verify that table display featuredHeadphones correctly
		//Assert.assertTrue(appDeviceControl.isDBContainListOrder(appDeviceControl.getOfflineDBName(dts_id), listPromo));
		// 14. Navigate to Product table in SQLite
		// VP: Verify that table display generic_accessories, inbox_accessory and stereo_preference correctly
		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_id), "select Type from Product", "Type", "Standard"));
		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_id), "select Type from Product", "Type", "StereoRoom"));
		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_id), "select Name from Product", "Name", dataProduct.get("name")));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the default audio route is required before publication
	 */
	@Test
	public void TC650DDEPT_07(){
		appDeviceControl.addLog("ID : TC650DDEPT_07 : Verify that Product Type is HPX High, the offline DB will include featuredHeadphones, generic_accessories, inbox_accessory and stereo_preference, headphone brands and multi channel room data");
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Fill valid value into all required fields
			5. Leave the Product Type panel as â€œHPX Highâ€�
			6. Input a product to text field
			7. Click â€œSaveâ€� link
			8. Publish device above
			9. Refresh offline database
			10. Click "Download Offline Database" link
			11. Open the offline databse file by SQLite
			12. Navigate to DeviceFeaturedProduct table in SQLite
			VP: Verify that table display  featuredHeadphones correctly
			13. Navigate to DeviceMultiChannelRoomModel table in SQLite
			VP: Verify that table display   Multi-Channel Room correctly
			14. Navigate to Product table in SQLite
			VP: Verify that table display Headphones of the Headphone Brands,Standard Accessories,  In-box Headphones, Stereo Room Models  and Other Audio Routes correctly

		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Create a published product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndPublish(dataProduct);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.appDevicePublish();
		data.remove("save");
		// 5. Leave the Product Type panel as â€œHPX Highâ€�
		data.put("global promotion", "ON");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// 6. Input an product to text field
		appDeviceControl.inputInboxHP(DeviceEdit.INBOX_FIELD, dataProduct.get("name"));
		appDeviceControl.click(DeviceEdit.ONLY_BRANDS);
		appDeviceControl.inputData(DeviceEdit.BRAND_FILTER, BRAND_NAME_TEST);
		// 7. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 8. Publish device above
		appDeviceControl.click(DeviceInfo.PUBLISH);
		ArrayList<String> listPromo = appDeviceControl.getListPromo("Published");
		// 9. Refresh offline database
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		// 10. Click "Download Offline Database" link
		boolean isDownloaded = appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE);
		Assert.assertTrue(isDownloaded);
		// 11. Open the offline database file by SQLite
		// 12. Navigate to DeviceFeaturedProduct table in SQLite
		// VP: Verify that table display featuredHeadphones correctly
		Assert.assertTrue(appDeviceControl.isDBContainListOrder(appDeviceControl.getOfflineDBName(dts_id), listPromo));
		// 13. Navigate to DeviceMultiChannelRoomModel table in SQLite
		// VP: Verify that table display Multi-Channel Room correctly
		Assert.assertTrue(appDeviceControl.isContainMultiCHRoom(appDeviceControl.getOfflineDBName(dts_id)));
		// 14. Navigate to Product table in SQLite
		// VP: Verify that table display Headphones of the Headphone Brands,Standard Accessories,  In-box Headphones, Stereo Room Models  and Other Audio Routes correctly
		//Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_id), "select BrandId from Product", "BrandId", "1648"));
		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_id), "select Type from Product", "Type", "Standard"));
		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_id), "select Type from Product", "Type", "StereoRoom"));
		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_id), "select Name from Product", "Name", dataProduct.get("name")));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the default audio route is required before publication
	 */
	@Test
	public void TC650DDEPT_08(){
		appDeviceControl.addLog("ID : TC650DDEPT_08 : Verify that Product Type is validated when creating app/device");
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Fill valid value into all required fields except Product Type section
			5. Click â€œSaveâ€� link
			VP: An error message "Product Type is required" is displayed
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.appDeviceDraft();
		data.remove("save");
		data.remove("product type");
		data.put("global promotion", "ON");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// 5. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.PRODUCT_TYPE_REQUIRE),DeviceEdit.requires.Product_type_is_required.getName());
	}
	
	
	/*
	 * Verify that the default audio route is required before publication
	 */
	@Test
	public void TC650DDEPT_09(){
		appDeviceControl.addLog("ID : TC650DDEPT_09 : Verify that Product Type is HPX Low, the published profile will not include any featured headphones");
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Fill valid value into all required fields
			5. Leave the Product Type panel as â€œHPX Lowâ€�
			6. Select "Attached0 - Internal Speakers (Default)" in Audio Route
			7. Click "Choose file" to upload custom tuning file
			8. Click â€œSaveâ€� link
			9. Publish device above
			10. Click â€œComplete Profileâ€� link
			11. Open file Published Profile
			VP: Verify that the published profile include Speaker Modes
			VP: Verify that the published profile will not include any featured headphones
			
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.appDevicePublishLow();
		data.remove("save");
		// 5. Leave the Product Type panel as â€œHPX Lowâ€�
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// 6. Select "Attached0 - Internal Speakers (Default)" in Audio Route
		/*appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached0_Internal_Speakers.getName());
		// 7. Click "Choose file" to upload custom tuning file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Custom.getName());*/
		// 8. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 9. Publish device above
		appDeviceControl.click(DeviceInfo.PUBLISH);
		ArrayList<String> listPromo = appDeviceControl.getListPromo("Published");
		System.out.println("List Promo: " + listPromo);
		// 10. Click â€œComplete Profileâ€� link
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
		// 11. Open file Published Profile
		// VP: Verify that the published profile include Speaker Modes
		//Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_ATTACHED0"));
		// VP: Verify that the published profile will not include any featured headphones
		Assert.assertEquals(appDeviceControl.checkNumberofPromotion(dts_id), 0);
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that Product Type is HPX Medium, the published profile will include Line-out, Bluetooth, USB, Speaker Modes and featured headphones similar with device promotions on App/Device Info page
	 */
	@Test
	public void TC650DDEPT_10(){
		appDeviceControl.addLog("ID : TC650DDEPT_10 : Verify that Product Type is HPX Medium, the published profile will include Line-out, Bluetooth, USB, Speaker Modes and featured headphones similar with device promotions on App/Device Info page");
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Fill valid value into all required fields
			5. Leave the Product Type panel as â€œHPX Mediumâ€�
			6. Select "Attached0 - Internal Speakers (Default)" in Audio Route dropdown list
			7. Click "Choose file" to upload custom tuning file
			8. Select "Line-out0 â€“ Line Out" in Audio Route dropdown list
			9. Select "Over-Ear Headphones" in Standard Accessories dropdown list
			10. Click "Choose file" to upload custom tuning file
			11. Select "Bluetooth0 â€“ Bluetooth" in Audio Route dropdown list
			12. Select "Earbuds" in Standard Accessories dropdown list
			13. Click "Choose file" to upload custom tuning file
			14. Select "USB0 â€“ USB" in Audio Route dropdown list
			15. Select â€œExternal Speakersâ€� in Standard Accessories dropdown list
			16. Click "Choose file" to upload custom tuning file
			17. Click â€œSaveâ€� link
			18. Publish device above
			19. Notice device promotions
			20. Click â€œComplete Profileâ€� link
			21. Open file Published Profile
			VP: Verify that the published profile include Line-out, Bluetooth, USB and Speaker Modes
			VP: Verify that the published profile will include featured headphones similar with device promotions above

		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.appDevicePublishMedium();
		data.remove("save");
		// 5. Leave the Product Type panel as â€œHPX Mediumâ€�
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// 6. Select "Attached0 - Internal Speakers (Default)" in Audio Route dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached0_Internal_Speakers.getName());
		// 7. Click "Choose file" to upload custom tuning file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Custom.getName());
		// 8. Select "Line-out0 â€“ Line Out" in Audio Route dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Line_out0.getName());
		// 9. Select "Over-Ear Headphones" in Standard Accessories dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
		// 10. Click "Choose file" to upload custom tuning file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones_Lineout.getName());
		// 11. Select "Bluetooth0 â€“ Bluetooth" in Audio Route dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Bluetooth0.getName());
		// 12. Select "Earbuds" in Standard Accessories dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Earbuds.getName());
		// 13. Click "Choose file" to upload custom tuning file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Earbuds_Bluetooth.getName());
		// 14. Select "USB0 â€“ USB" in Audio Route dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.USB0.getName());
		// 15. Select â€œExternal Speakersâ€� in Standard Accessories dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Ear_piece.getName());
		// 16. Click "Choose file" to upload custom tuning file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Earpiece_USB.getName());
		// 17. Click â€œSaveâ€� link
		appDeviceControl.click(DeviceEdit.SAVE);
		// 18. Publish device above
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 19. Notice device promotions
		ArrayList<String> listPromo = appDeviceControl.getListPromo("Published");
		System.out.println("List: " + listPromo);
		// 20. Click â€œComplete Profileâ€� link
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
		// 21. Open file Published Profile
		/*
		 * VP: Verify that the published profile include Line-out, Bluetooth, USB and Speaker Modes
		 */
		Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_BLUETOOTH"));
		Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_LINEOUT"));
		Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_USB"));
		Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_ATTACHED0"));
		/*
		 * VP: Verify that the published profile will include featured headphones similar with device promotions above
		 */
		Assert.assertTrue(appDeviceControl.checkPromotion(listPromo,dts_id));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that Product Type is HPX High, the published profile will include Line-out, Bluetooth, USB, Speaker Modes and featured headphones similar with device promotions on App/Device Info page
	 */
	@Test
	public void TC650DDEPT_11(){
		appDeviceControl.addLog("ID : TC650DDEPT_11 : Verify that Product Type is HPX High, the published profile will include Line-out, Bluetooth, USB, Speaker Modes and featured headphones similar with device promotions on App/Device Info page");
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Fill valid value into all required fields
			5. Leave the Product Type panel as â€œHPX Highâ€�
			6. Select "Attached0 - Internal Speakers (Default)" in Audio Route dropdown list
			7. Click "Choose file" to upload custom tuning file
			8. Select "Line-out0 â€“ Line Out" in Audio Route dropdown list
			9. Select "Over-Ear Headphones" in Standard Accessories dropdown list
			10. Click "Choose file" to upload custom tuning file
			11. Select "Bluetooth0 â€“ Bluetooth" in Audio Route dropdown list
			12. Select "Earbuds" in Standard Accessories dropdown list
			13. Click "Choose file" to upload custom tuning file
			14. Select "USB0 â€“ USB" in Audio Route dropdown list	
			15. Select â€œExternal Speakersâ€� in Standard Accessories dropdown list	
			16. Click "Choose file" to upload custom tuning file
			17. Click â€œSaveâ€� link	
			18. Publish device above	
			19. Notice device promotions	
			20. Click â€œComplete Profileâ€� link	
			21. Open file Published Profile	
			VP: Verify that the published profile include Line-out, Bluetooth, USB and Speaker Modes
			VP: Verify that the published profile will include featured headphones similar with device promotions above

		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.appDevicePublish();
		data.remove("save");
		// 5. Leave the Product Type panel as â€œHPX Highâ€�
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// 6. Select "Attached0 - Internal Speakers (Default)" in Audio Route dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached0_Internal_Speakers.getName());
		// 7. Click "Choose file" to upload custom tuning file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Custom.getName());
		// 8. Select "Line-out0 â€“ Line Out" in Audio Route dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Line_out0.getName());
		// 9. Select "Over-Ear Headphones" in Standard Accessories dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
		// 10. Click "Choose file" to upload custom tuning file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones_Lineout.getName());
		// 11. Select "Bluetooth0 â€“ Bluetooth" in Audio Route dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Bluetooth0.getName());
		// 12. Select "Earbuds" in Standard Accessories dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Earbuds.getName());
		// 13. Click "Choose file" to upload custom tuning file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Earbuds_Bluetooth.getName());
		// 14. Select "USB0 â€“ USB" in Audio Route dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.USB0.getName());
		// 15. Select â€œExternal Speakersâ€� in Standard Accessories dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.External_Speakers.getName());
		// 16. Click "Choose file" to upload custom tuning file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.External_Speakers_USB.getName());
		// 17. Click â€œSaveâ€� link	
		appDeviceControl.click(DeviceEdit.SAVE);
		// 18. Publish device above	
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 19. Notice device promotions	
		ArrayList<String> listPromo = appDeviceControl.getListPromo("Published");
		System.out.println("List: " + listPromo);
		// 20. Click â€œComplete Profileâ€� link	
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
		// 21. Open file Published Profile	
		// VP: Verify that the published profile include Line-out, Bluetooth, USB and Speaker Modes
		Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_BLUETOOTH"));
		Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_LINEOUT"));
		Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_USB"));
		Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_ATTACHED0"));
//		String txt_file = appDeviceControl.changeFileDtscsToTxt(file_dtscs);
		// VP: Verify that the published profile will include featured headphones similar with device promotions above
//		Assert.assertEquals(appDeviceControl.checkNumberofPromotion(file_dtscs), 6);
		Assert.assertTrue(appDeviceControl.checkPromotion(listPromo, dts_id));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that Product Type is HPX High, the 650D Device Edit page and the 640D Device Detail page display full of secsions
	 */
	@Test
	public void TC650DDEPT_12(){
		appDeviceControl.addLog("ID : TC650DDEPT_12 : Verify that Product Type is HPX High, the 650D Device Edit page and the 640D Device Detail page display full of secsions");
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Fill valid value into all required fields
			5. Leave the Product Type panel as â€œHPX Highâ€�
			VP: Verify that the 650D Device Edit page display full of secsions
			6. Click â€œSaveâ€� link
			VP: Verify that the 640D Device Detail page is displayed contains "Product Information", "Headphone:X Licensing Info", "Offline Database", "Multi-Channel Room Models", "Audio Routes", â€œIn-box Headphonesâ€�, "Featured Accessory  Promotions", "Device Promotions", "Headphone Images Sizes", and "Headphone Brands" sections.
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.appDevicePublish();
		data.remove("save");
		// 5. Leave the Product Type panel as â€œHPX Highâ€�
		// VP: Verify that the 650D Device Edit page display full of secsions
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()),true);
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// 6. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: Verify that the 640D Device Detail page is displayed contains "Product Information", "Headphone:X Licensing Info", "Offline Database", "Multi-Channel Room Models", "Audio Routes", â€œIn-box Headphonesâ€�, "Featured Accessory  Promotions", "Device Promotions", "Headphone Images Sizes", and "Headphone Brands" sections.
		Assert.assertEquals(true, appDeviceControl.existsElement(DeviceInfo.getHighSections()));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that Product Type is HPX High, the 650D Device Edit page and the 640D Device Detail page display full of secsions
	 */
	@Test
	public void TC650DDEPT_13(){
		appDeviceControl.addLog("ID : TC650DDEPT_13 : Verify that Product Type is HPX Medium, the 650D Device Edit page and the 640D Device Detail page display correctly");
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Fill valid value into all required fields
			5. Leave the Product Type panel as â€œHPX Mediumâ€�
			VP: Verify that the 650D Device Edit page doesn't display "Multi-Channel Room Models" and â€œHeadphone Brandsâ€� section
			6. Click â€œSaveâ€� link
			VP: Verify that the 640D Device Detail page is displayed contains "Product Information", "Headphone:X Licensing Info", "Offline Database", "Audio Routes", â€œIn-box Headphonesâ€�,  "Featured Accessory  Promotions", "Device Promotions" and "Headphone Images Sizes" sections
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.appDevicePublishMedium();
		data.remove("save");
		// 5. Leave the Product Type panel as â€œHPX Mediumâ€�
		// VP: Verify that the 650D Device Edit page doesn't display "Multi-Channel Room Models" and â€œHeadphone Brandsâ€� section
//		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()),true);
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.MULTI_CH_ROOM));
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.HEADPHONE_BRAND));
		// 6. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: Verify that the 640D Device Detail page is displayed contains "Product Information", "Headphone:X Licensing Info", "Offline Database", "Multi-Channel Room Models", "Audio Routes", â€œIn-box Headphonesâ€�, "Featured Accessory  Promotions", "Device Promotions", "Headphone Images Sizes", and "Headphone Brands" sections.
		Assert.assertEquals(true, appDeviceControl.existsElement(DeviceInfo.getMediumSections()));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that Product Type is HPX High, the 650D Device Edit page and the 640D Device Detail page display full of secsions
	 */
	@Test
	public void TC650DDEPT_14(){
		appDeviceControl.addLog("ID : TC650DDEPT_14 : Verify that Product Type is HPX Low, the 650D Device Edit page and the 640D Device Detail page display correctly");
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Fill valid value into all required fields
			5. Leave the Product Type panel as â€œHPX Lowâ€�
			VP: Verify that the 650D Device Edit page doesn't display "Multi-Channel Room Models", "Featured product Promotions", "Device Promotions",  â€œHeadphone Brandsâ€� sections
			6. Click â€œSaveâ€� link
			VP: Verify that the 640D Device Detail page is displayed contains "Product Information", "Headphone:X Licensing Info", "Offline Database",  "Audio Routes", â€œIn-box Headphonesâ€� and "Headphone Images Sizes" secsions

		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.appDevicePublishLow();
		data.remove("save");
		// 5. Leave the Product Type panel as â€œHPX Lowâ€�
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// VP: Verify that the 650D Device Edit page doesn't display "Multi-Channel Room Models", "Featured product Promotions", "Device Promotions",  â€œHeadphone Brandsâ€� sections
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.MULTI_CH_ROOM));
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.HEADPHONE_BRAND));
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.FEATURED_HEADPHONE));
		// 6. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: Verify that the 640D Device Detail page is displayed contains "Product Information", "Headphone:X Licensing Info", "Offline Database", "Multi-Channel Room Models", "Audio Routes", â€œIn-box Headphonesâ€�, "Featured Accessory  Promotions", "Device Promotions", "Headphone Images Sizes", and "Headphone Brands" sections.
		Assert.assertEquals(true, appDeviceControl.existsElement(DeviceInfo.getLowSections()));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that checkbox "No Images"is displayedin â€œHeadphone Image Sizesâ€�section
	 */
	@Test
	public void TC650DbADE_23(){
		appDeviceControl.addLog("ID : TC650DbADE_23 : Verify that checkbox 'No Images' is displayed in â€œHeadphone Image Sizesâ€�section");
		
		/*
		1. Log into DTS portal as a DTS user	
		2. Navigate to "Apps & Devices" page	
		3. Click â€œAdd App or Deviceâ€� link	
		VP: Verify that â€œHeadphone Image Sizesâ€� section displays 4 checkboxes: Large (1000x1000 px), 
		Medium (500x500 px),
		Small (250x250 px),
		No Images"
		4. Fill valid value into all required fields	
		5. Unselect all options in Headphone Image Sizesâ€� section	
		6. Click â€œSaveâ€� link	
		VP: An error message "Headphone image sizes is required" is displayed
		7. Select "No Images" option in "Headphone Image Sizes" section	
		8. Click â€œSaveâ€� link	
		VP: Verify that â€œ640D Device Detailâ€� page displays â€œNo Imagesâ€� on â€œHeadphone Image Sizesâ€� section
		*/
		
//		1. Log into DTS portal as a DTS user	
		loginControl.login(DTS_USER, DTS_PASSWORD);
//		2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click â€œAdd App or Deviceâ€� link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		VP: Verify that â€œHeadphone Image Sizesâ€� section displays 4 checkboxes: 
		Large (1000x1000 px),
		Medium (500x500 px),
		Small (250x250 px),
		No Images
		*/
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.LARGE_IMAGE));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.MEDIUM_IMAGE));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.SMALL_IMAGE));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.NO_IMAGES));
		/*
		 * VP: Three images ( Large (1000x1000 px), 
			Medium (500x500 px),
			Small (250x250 px) ) sizes are selected by default in "Headphone Image Sizes" section
		 */
		WebElement lagre_image = driver.findElement(By.xpath(DeviceEdit.LARGE_IMAGE));
		Assert.assertTrue(lagre_image.isSelected());
		WebElement medium_image = driver.findElement(By.xpath(DeviceEdit.MEDIUM_IMAGE));
		Assert.assertTrue(medium_image.isSelected());
		WebElement small_image = driver.findElement(By.xpath(DeviceEdit.SMALL_IMAGE));
		Assert.assertTrue(small_image.isSelected());
//		4. Fill valid value into all required fields
		Hashtable<String,String> deviceData = TestData.appDeviceDraft();
		deviceData.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
//		5. Unselect all options in Headphone Image Sizesâ€� section"
		appDeviceControl.uncheckACheckbox(DeviceEdit.LARGE_IMAGE);
		appDeviceControl.uncheckACheckbox(DeviceEdit.MEDIUM_IMAGE);
		appDeviceControl.uncheckACheckbox(DeviceEdit.SMALL_IMAGE);
//		6. Click â€œSaveâ€� link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: An error message "Headphone image sizes is required" is displayed
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.IMAGE_SIZE_REQUIRE),DeviceEdit.requires.Headphone_image_sizes_is_required.getName());
//		7. Select "No Images" option in "Headphone Image Sizes" section
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
//		8. Click â€œSaveâ€� link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: Verify that â€œ640D Device Detailâ€� page displays â€œNo Imagesâ€� on â€œHeadphone Image Sizesâ€� section
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceInfo.NO_IMAGES).contains("No Images"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the user select "No Images" check box, the other checkboxes will be unchecked and otherwise.
	 */
	@Test
	public void TC650DbADE_24(){
		appDeviceControl.addLog("ID : TC650DbADE_24 : Verify that the user select 'No Images' check box, the other checkboxes will be unchecked and otherwise.");
	
		/*
		1. Log into DTS portal as a DTS user	
		2. Navigate to "Apps & Devices" page	
		3. Click â€œAdd App or Deviceâ€� link	
		4. Select Large (1000x1000 px), 
		Medium (500x500 px) and
		Small (250x250 px) options in â€œHeadphone Image Sizesâ€� section
		5. Select â€œNo Imageâ€� options in â€œHeadphone Image Sizesâ€� section	
		VP: Verify that other checkboxes will be unchecked
		6. Select Large (1000x1000 px) option in â€œHeadphone Image Sizesâ€� section	
		VP: Verify that â€œNo Imageâ€� option will be unchecked
		*/
		
//		1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click â€œAdd App or Deviceâ€� link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		4. Select Large (1000x1000 px), Medium (500x500 px) and Small (250x250 px) options in â€œHeadphone Image Sizesâ€� section
//		5. Select â€œNo Imageâ€� options in â€œHeadphone Image Sizesâ€� section
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		/*
		 * VP: Verify that other checkboxes will be unchecked
		 */
		WebElement lagre_image = driver.findElement(By.xpath(DeviceEdit.LARGE_IMAGE));
		Assert.assertFalse(lagre_image.isSelected());
		WebElement medium_image = driver.findElement(By.xpath(DeviceEdit.MEDIUM_IMAGE));
		Assert.assertFalse(medium_image.isSelected());
		WebElement small_image = driver.findElement(By.xpath(DeviceEdit.SMALL_IMAGE));
		Assert.assertFalse(small_image.isSelected());
//		6. Select Large (1000x1000 px) option in â€œHeadphone Image Sizesâ€� section
		appDeviceControl.click(DeviceEdit.LARGE_IMAGE);
		/*
		 * VP: Verify that â€œNo Imageâ€� option will be unchecked
		 */		
		WebElement no_image = driver.findElement(By.xpath(DeviceEdit.NO_IMAGES));
		Assert.assertFalse(no_image.isSelected());
	}
	
	/*
	 * Verify that Product Type is HPX Low, the offline DB will not include any images if user checks â€œNo Imagesâ€� on â€œHeadphone Image Sizesâ€� section.
	 */
	@Test
	public void TC650DbADE_25(){
		appDeviceControl.addLog("ID : TC650DbADE_25 : Verify that Product Type is HPX Low, the offline DB will not include any images if user checks 'No Images' on 'Headphone Image Sizes' section.");
		
		/*
		1. Log into DTS portal as a DTS user	
		2. Navigate to "Apps & Devices" page	
		3. Click â€œAdd App or Deviceâ€� link	
		4. Fill valid value into all required fields	
		5. Leave the Product Type section as â€œHPX Lowâ€�	
		6. Click â€œSaveâ€� link	
		7. Publish device above	
		8. Refresh offline database	
		9. Click â€œDownload offline databaseâ€� link	
		10. Open the offline database file by SQLite	
		11. Navigate to â€œAssetâ€� table in SQLite	
		VP: Verify that the table doesn't include any images
		*/
		
//		1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		4. Fill valid value into all required fields	
//		5. Leave the Product Type section as â€œHPX Lowâ€�	
//		6. Click â€œSaveâ€� link
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		deviceData.remove("save");
		//deviceData.remove("product type");
		deviceData.put("product type",DeviceInfo.Product_Types.HPX_Low.getName());
		deviceData.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		appDeviceControl.click(DeviceEdit.SAVE);
		String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		7. Publish device above	
		appDeviceControl.click(DeviceInfo.PUBLISH);
//		8. Refresh offline database
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		9. Click â€œDownload offline databaseâ€� link	
		appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE);
//		10. Open the offline database file by SQLite
//		11. Navigate to â€œAssetâ€� table in SQLite	
		/*
		 * VP: Verify that the table doesn't include any images
		 */
		Assert.assertFalse(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_id), "select Type from Asset", "Type", "IMAGE_SMALL"));
		Assert.assertFalse(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_id), "select Type from Asset", "Type", "IMAGE_MEDIUM"));
		Assert.assertFalse(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_id), "select Type from Asset", "Type", "IMAGE_LARGE"));
	}
	
	/*
	 * Verify that Product Type is HPX Medium, the offline DB will not include any images if user checks â€œNo Imagesâ€� on â€œHeadphone Image Sizesâ€� section.
	 */
	@Test
	public void TC650DbADE_26(){
		appDeviceControl.addLog("ID : TC650DbADE_26 : Verify that Product Type is HPX Medium, the offline DB will not include any images if user checks â€œNo Imagesâ€� on â€œHeadphone Image Sizesâ€� section.");
		/*
		1. Log into DTS portal as a DTS user	
		2. Navigate to "Apps & Devices" page	
		3. Click â€œAdd App or Deviceâ€� link	
		4. Fill valid value into all required fields	
		5. Leave the Product Type section as â€œHPX Mediumâ€�	
		6. Click â€œSaveâ€� link	
		7. Publish device above	
		8. Refresh offline database	
		9. Click â€œDownload offline databaseâ€� link	
		10. Open the offline database file by SQLite	
		11. Navigate to â€œAssetâ€� table in SQLite	
		VP: Verify that the table doesn't include any images
		*/
		
//		1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		4. Fill valid value into all required fields	
//		5. Leave the Product Type section as â€œHPX Mediumâ€�	
//		6. Click â€œSaveâ€� link
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		deviceData.remove("save");
		deviceData.remove("product type");
		deviceData.put("product type", DeviceInfo.Product_Types.HPX_Medium.getName());
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		appDeviceControl.selectACheckbox(DeviceEdit.NO_IMAGES);
		appDeviceControl.click(DeviceEdit.SAVE);
		String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		7. Publish device above	
		appDeviceControl.click(DeviceInfo.PUBLISH);
//		8. Refresh offline database
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		9. Click â€œDownload offline databaseâ€� link	
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//		10. Open the offline database file by SQLite
//		11. Navigate to â€œAssetâ€� table in SQLite	
		
		/*
		 * VP: Verify that the table doesn't include any images
		 */
		Assert.assertFalse(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_id), "select Type from Asset", "Type", "IMAGE_SMALL"));
		Assert.assertFalse(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_id), "select Type from Asset", "Type", "IMAGE_MEDIUM"));
		Assert.assertFalse(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_id), "select Type from Asset", "Type", "IMAGE_LARGE"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that Product Type is HPX High, the offline DB will not include any images if user checks â€œNo Imagesâ€� on â€œHeadphone Image Sizesâ€� section.
	 */
	@Test
	public void TC650DbADE_27(){
		appDeviceControl.addLog("ID : TC650DbADE_27 : Verify that Product Type is HPX High, the offline DB will not include any images if user checks â€œNo Imagesâ€� on â€œHeadphone Image Sizesâ€� section.");
		/*
		1. Log into DTS portal as a DTS user	
		2. Navigate to "Apps & Devices" page	
		3. Click â€œAdd App or Deviceâ€� link	
		4. Fill valid value into all required fields	
		5. Leave the Product Type section as â€œHPX Highâ€�	
		6. Click â€œSaveâ€� link	
		7. Publish device above	
		8. Refresh offline database	
		9. Click â€œDownload offline databaseâ€� link	
		10. Open the offline database file by SQLite	
		11. Navigate to â€œAssetâ€� table in SQLite	
		VP: Verify that the table doesn't include any images
		*/
		
//		1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		4. Fill valid value into all required fields	
//		5. Leave the Product Type section as â€œHPX Lowâ€�	
//		6. Click â€œSaveâ€� link
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		deviceData.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		appDeviceControl.selectACheckbox(DeviceEdit.NO_IMAGES);
		appDeviceControl.click(DeviceEdit.SAVE);
		String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		7. Publish device above	
		appDeviceControl.click(DeviceInfo.PUBLISH);
//		8. Refresh offline database
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		9. Click â€œDownload offline databaseâ€� link	
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//		10. Open the offline database file by SQLite
//		11. Navigate to â€œAssetâ€� table in SQLite	
		
		/*
		 * VP: Verify that the table doesn't include any images
		 */
		Assert.assertFalse(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_id), "select Type from Asset", "Type", "IMAGE_SMALL"));
		Assert.assertFalse(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_id), "select Type from Asset", "Type", "IMAGE_MEDIUM"));
		Assert.assertFalse(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_id), "select Type from Asset", "Type", "IMAGE_LARGE"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that user checks â€œHeadphone Image Sizesâ€� section is â€œNo Imagesâ€�, the offline DB will not include any brand's images
	 */
	@Test
	public void TC650DbADE_28(){
		appDeviceControl.addLog("ID : TC650DbADE_28 : Verify that user checks â€œHeadphone Image Sizesâ€� section is â€œNo Imagesâ€�, the offline DB will not include any brand's images");
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Fill valid value into all required fields
			5. Leave the Product Type section as â€œHPX Highâ€�
			6. Click â€œSaveâ€� link
			7. Publish device above
			8. Refresh offline database
			9. Click â€œDownload offline databaseâ€� link
			10. Open the offline database file by SQLite
			11. Navigate to â€œBrandâ€� table in SQLite
			VP: Verify that the table doesn't include any ImageAssetId

		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		// 5. Leave the Product Type panel as â€œHPX Highâ€�
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.appDevicePublish();
		data.remove("save");
		appDeviceControl.selectACheckbox(DeviceEdit.NO_IMAGES);
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		appDeviceControl.click(DeviceEdit.SAVE);
		String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 7. Publish device above
		appDeviceControl.click(DeviceInfo.PUBLISH);
		// 8. Refresh offline database
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		// 9. Click "Download Offline Database" link
		boolean isDownloaded = appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE);
		Assert.assertTrue(isDownloaded);
		// 10. Open the offline database file by SQLite
		// 11. Navigate to â€œBrandâ€� table in SQLite
		// VP: Verify that the table doesn't include any ImageAssetId
		Assert.assertTrue(appDeviceControl.isColumnEmptyData(appDeviceControl.getOfflineDBName(dts_id), "select ImageAssetIdSmall from Brand", "ImageAssetIdSmall"));
		Assert.assertTrue(appDeviceControl.isColumnEmptyData(appDeviceControl.getOfflineDBName(dts_id), "select ImageAssetIdMedium from Brand", "ImageAssetIdMedium"));
		Assert.assertTrue(appDeviceControl.isColumnEmptyData(appDeviceControl.getOfflineDBName(dts_id), "select ImageAssetIdLarge from Brand", "ImageAssetIdLarge"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that user checks â€œHeadphone Image Sizesâ€� section is â€œNo Imagesâ€�, the offline DB will not include any Product's images
	 */
	@Test
	public void TC650DbADE_29(){
		appDeviceControl.addLog("ID : TC650DbADE_29 : Verify that user checks â€œHeadphone Image Sizesâ€� section is â€œNo Imagesâ€�, the offline DB will not include any Product's images");
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Fill valid value into all required fields
			5. Select "No Images" option in "Headphone Image Sizes" section
			6. Input a product to In-box Headphones section
			7. Click â€œSaveâ€� link
			8. Publish device above
			9. Refresh offline database
			10. Click â€œDownload offline databaseâ€� link
			11. Open the offline database file by SQLite
			12. Navigate to â€œProductâ€� table in SQLite
			VP: Verify that the table doesn't include any ImageAssetId

		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Create a published product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndPublish(dataProduct);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		// 5. Select "No Images" option in "Headphone Image Sizes" section
		Hashtable<String,String> data = TestData.appDevicePublish();
		data.remove("save");
		data.put("global promotion", "ON");
		appDeviceControl.selectACheckbox(DeviceEdit.NO_IMAGES);
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// 6. Input a product to In-box Headphones section
		appDeviceControl.inputInboxHP(DeviceEdit.INBOX_FIELD, dataProduct.get("name"));
		// 7. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 8. Publish device above
		appDeviceControl.click(DeviceInfo.PUBLISH);
		// 9. Refresh offline database
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		// 10. Click "Download Offline Database" link
		boolean isDownloaded = appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE);
		Assert.assertTrue(isDownloaded);
		// 11. Open the offline database file by SQLite
		// 12. Navigate to â€œBrandâ€� table in SQLite
		// VP: Verify that the table doesn't include any ImageAssetId
		Assert.assertTrue(appDeviceControl.isColumnEmptyData(appDeviceControl.getOfflineDBName(dts_id), "select ImageAssetIdSmall from Product", "ImageAssetIdSmall"));
		Assert.assertTrue(appDeviceControl.isColumnEmptyData(appDeviceControl.getOfflineDBName(dts_id), "select ImageAssetIdMedium from Product", "ImageAssetIdMedium"));
		Assert.assertTrue(appDeviceControl.isColumnEmptyData(appDeviceControl.getOfflineDBName(dts_id), "select ImageAssetIdLarge from Product", "ImageAssetIdLarge"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		appDeviceControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that Device Headphone is added to the Audio Route dropdown list
	 */
	@Test
	public void TC650DaDEN_83(){
		Reporter.log("Verify that Device Headphone is added to the Audio Route dropdown list");
		/*
		 * Note: this testcase is updated to adding 'Attached 0 - Default Mode' (at MPG-6477). 
		 * So with this adding also cover 650DaDEN_126
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Leave the Product Type panel as â€œHPX Lowâ€�
			5. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
			VP: The "Content Modes" combobox is displayed.
			VP: The â€œContent Modesâ€� combobox contains: 
			"-Select-",
			Default Mode
			â€œMode 1â€�,
			"Mode 2",
			â€œMode 3â€�
			6. Leave the Product Type panel as â€œHPX Highâ€�
			7. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
			VP: The â€œContent Modesâ€� combobox contains: 
			"-Select-",
			Attached 0 - Default Mode
			Attached 2 - Mode1
			Attached 3 â€“ Mode2
			Attached 4 - Mode3
			Attached 5 â€“ Mode4
			Attached 6 â€“ Mode5
			Attached 7 â€“ Mode6
			Attached 8 â€“ Mode7
			8. Leave the Product Type panel as â€œHPX Mediumâ€�
			9 . Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list			
			VP: The â€œContent Modesâ€� combobox contains: 
			"-Select-",
			Attached 0 - Default Mode
			Attached 2 - Mode1
			Attached 3 â€“ Mode2
			Attached 4 - Mode3
			Attached 5 â€“ Mode4
			Attached 6 â€“ Mode5
			Attached 7 â€“ Mode6
			Attached 8 â€“ Mode7
		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Leave the Product Type panel as â€œHPX Lowâ€�
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_Low.getType());
		// 5. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		// VP: The "Content Modes" combobox is displayed.
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.SUB_DROPDOWN_AUDIO_ROUTE));
		/* VP: The â€œContent Modesâ€� combobox contains: 
			"-Select-",
			Default Mode
			â€œMode 1â€�,
			"Mode 2",
			â€œMode 3â€�
		*/
		ArrayList<String> typeList = appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE);
		Assert.assertTrue(ListUtil.containsAll(typeList, DeviceEdit.Content_Modes_Low.getNames()));
		// 6. Leave the Product Type panel as â€œHPX Highâ€�
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_High.getType());
		// 7. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		// VP: The â€œContent Modesâ€� combobox contains: 
//		"-Select-",
//		Attached 0 - Default Mode
//		Attached 2 - Mode1
//		Attached 3 â€“ Mode2
//		Attached 4 - Mode3
//		Attached 5 â€“ Mode4
//		Attached 6 â€“ Mode5
//		Attached 7 â€“ Mode6
//		Attached 8 â€“ Mode7
		 Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE),DeviceEdit.Content_Modes_Hig.getNames()));
		// 8. Leave the Product Type panel as â€œHPX Mediumâ€�
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
		//9 . Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list	
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		// VP: The â€œContent Modesâ€� combobox contains: 
//		"-Select-",
//		Attached 0 - Default Mode
//		Attached 2 - Mode1
//		Attached 3 â€“ Mode2
//		Attached 4 - Mode3
//		Attached 5 â€“ Mode4
//		Attached 6 â€“ Mode5
//		Attached 7 â€“ Mode6
//		Attached 8 â€“ Mode7
		 Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE),DeviceEdit.Content_Modes_Medium.getNames()));
	}
	
	/*
	 * Verify that Audio Route dropdown is displayed correctly when changing from Low Product to High/Medium product and otherwise
	 */
	@Test
	public void TC650DaDEN_84() throws InterruptedException{
		Reporter.log("Verify that Audio Route dropdown is displayed correctly when changing from Low Product to High/Medium product and otherwise");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Leave the Eagle Version panel as â€œEagle 1.4â€�
			5. Leave the Product Type panel as DTS Audio Processing
			6. Select â€œDevice Headphoneâ€� in Audio Route
			7. Select â€œMode 1â€� in â€œContent Modesâ€� dropdown list
			8. Click "Choose fille" to upload Device Headphone tuning file
			9. Change the filter in Product Type panel to ''DTS:X Master''
			VP: Verify that the confirmation popup is showed up
			10. Click â€œChangeâ€� button
			VP: Above Device Headphone is removed in Audio Route section
			11. Select Device Headphone in â€œAudio Routeâ€� dropdown list
			11.a Select Attached 2 - Mode 1 in â€œContent Modesâ€� dropdown list
			12. Click "Choose fille" to upload Device Headphone tuning file
			13. Change the filter in Product Type panel to ''DTS:X Premium''
			VP: Device Headphone isn't removed in Audio Route section
			14. Change the filter in Product Type panel to â€œDTS Audio Processingâ€�
			VP: Verify that the confirmation popup is showed up
			15. Click â€œChangeâ€� button
			VP: Device Headphone is removed in Audio Route section
		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Leave the Eagle Version panel as â€œEagle 1.4â€�
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		// 5. Leave the Product Type panel as â€œHPX Lowâ€�
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_Low.getType());
		// 6. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		// 7. Select â€œMode 1â€� in â€œContent Modesâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_1.getType());
		// 8. Click "Choose fille" to upload Device Headphone tuning file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Mode1.getName());
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Mode1.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		// 9. Change the filter in Product Type panel to â€œHPX Highâ€�
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_High.getType());
		// VP: Verify that the confirmation popup is showed up
		System.out.println(appDeviceControl.getTextByXpath(DeviceEdit.SWITCH_PRODUCT_TYPE));
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.SWITCH_PRODUCT_TYPE).contains(DeviceEdit.requires.Switch_Product_Type.getName()));
		// 10. Click â€œOKâ€� button
		appDeviceControl.click(DeviceEdit.OK_BUTTON);
		// VP: Above Device Headphone is removed in Audio Route section
		Assert.assertFalse(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Mode1.getName().replaceAll(".dtscs", "")));
		// 11. Select Device Headphone in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		//11.a Select Attached 2 - Mode1 in â€œContent Modesâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
		// 12. Click "Choose fille" to upload Device Headphone tuning file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Default.getName());
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Default.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		// 13. Change the filter in Product Type panel to â€œHPX Mediumâ€�
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_Medium.getType());
		// VP: Device Headphone isn't removed in Audio Route section
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Default.getName().replaceAll(".dtscs", "")));
		// 14. Change the filter in Product Type panel to â€œHPX Lowâ€�
		appDeviceControl.selectOptionByName(DeviceEdit.TYPE, DeviceEdit.types[1]);
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_Low.getType());
		Thread.sleep(1000);
		// VP: Verify that the confirmation popup is showed up
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.SWITCH_PRODUCT_TYPE).contains(DeviceEdit.requires.Switch_Product_Type.getName()));
		// 15. Click â€œOKâ€� button
		appDeviceControl.click(DeviceEdit.OK_BUTTON);
		// VP: Device Headphone is removed in Audio Route section
		Assert.assertFalse(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Default.getName().replaceAll(".dtscs", "")));
	}
	
	/*
	 * Verify that Product Type is HPX High, the invalid route tuning file is uploaded unsuccessfully when selecting Device Headphone
	 */
	@Test
	public void TC650DaDEN_85(){
		Reporter.log("Verify that Product Type is HPX High, the invalid route tuning file is uploaded unsuccessfully when selecting Device Headphone");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			VP: The Device Edit page is displayed
			4. Leave the Product Type panel as â€œHPX Highâ€�
			5. Select Device Headphone in â€œAudio Routeâ€� dropdown list
			5.a Select Attached 2 - Mode 1 in â€œContent Modesâ€� dropdown list
			6. Click on "Select file" button in "Custom Tuning" section
			7. Upload the custom tuning DTSCS file that having route not match
			VP: Verify that â€œ! Invalid file: The Route object does not match the Audio Route selection.â€� message is displayed

		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
		// 4. Leave the Eagle Version panel as â€œEagle 1.4â€�
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		// 5. Leave the Product Type panel as â€œHPX Highâ€�
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
		// 6. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		// 6.a Select Attached 2 - Mode 1 in â€œContent Modesâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
		// 7. Click on "Select file" button in "Custom Tuning" section
		// 8. Upload the custom tuning DTSCS file that having route not match
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Earbuds_Lineout.getName());
		// VP: Verify that â€œ! Invalid file: The Route object does not match the Audio Route selection.â€� message is displayed
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS).contains(DeviceEdit.requires.Invalid_file_contents.getName()));
	}
	
	/*
	 * Verify that Product Type is HPX High, the valid route tuning file is uploaded successfully when selecting Device Headphone route
	 */
	@Test
	public void TC650DaDEN_86(){
		Reporter.log("Verify that Product Type is HPX High, the valid route tuning file is uploaded successfully when selecting Device Headphone route");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			VP: The Device Edit page is displayed
			4. Leave the Product Type panel as â€œHPX Highâ€�
			5. Select Device Headphone in â€œAudio Routeâ€� dropdown list
			5.a Select Attached 2 - Mode 1 in â€œContent Modesâ€� dropdown list
			6. Click on "Select file" button in "Custom Tuning" section
			7. Upload the valid custom tuning DTSCS file
			VP: Verify that the valid tuning DTSCS file is uploaded successfully

		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
		// 4. Leave the Eagle Version panel as â€œEagle 1.4â€�
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		// 5. Leave the Product Type panel as â€œHPX Highâ€�
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_High.getType());
		// 6. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		// 6.a Select Attached 2 - Mode 1 in â€œContent Modesâ€� dropdown list
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
		// 7. Click on "Select file" button in "Custom Tuning" section
		// 8. Upload the custom tuning DTSCS file that having route not match
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Default.getName());
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Default.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
	}
	
	/*
	 * Verify that when uploading Device Headphone tuning, the offline DB will contain above tuning in Asset table
	 */
	@Test
	public void TC650DaDEN_87(){
		Reporter.log("Verify that when uploading Device Headphone tuning, the offline DB will contain above tuning in Asset table");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			VP: The Device Edit page is displayed
			4. Fill valid value into all required fields
			5. Leave the Product Type panel as â€œHPX Highâ€�
			6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
			6a. Select â€œAttached2â€� in â€œContent Modesâ€� dropdown list
			7. Click on "Select file" button in "Custom Tuning" section
			8. Upload the valid custom tuning DTSCS file
			VP: Verify that the valid tuning DTSCS file is uploaded successfully
			9. Click â€œSaveâ€� link
			10. Publish above device
			11. Refresh offline database
			12. Click "Download Offline Database" link
			13. Open the offline databse file by SQLite
			14. Navigate to Asset table in SQLite
			VP: Verify that Asset table will contain Device Headphone tuning

		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
		// 4. Fill valid value into all required fields
		Hashtable<String,String> dataDevice = TestData.appDevicePublish();
		dataDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice);
		// 5. Leave the Product Type panel as â€œHPX Highâ€�
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
		// 6. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		// 6a. Select â€œAttached2â€� in â€œContent Modesâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
		// 7. Click on "Select file" button in "Custom Tuning" section
		// 8. Upload the valid custom tuning DTSCS file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Default.getName());
		// VP: Verify that the valid tuning DTSCS file is uploaded successfully
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Default.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		// 9. Click â€œSaveâ€� link
		appDeviceControl.click(DeviceEdit.SAVE);
		// 10. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String dts_db = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 11. Refresh offline database
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		// 12. Click "Download Offline Database" link
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
		// 13. Open the offline databse file by SQLite
		// Navigate to Product table in SQLite
		// select DTSCSAssetId from Product where Type = 'DeviceHeadphone' and SubType = 'Default'
//		String DTSCSAssetId = DbUtil.selectStatment("Select DTSCSAssetId from Product where Type = 'DeviceHeadphone' and SubType = 'Default'");
		SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(dts_db));
		ArrayList<String> DTSCSAssetId = sqlite.getColumnData("Select DTSCSAssetId from Product where Type = 'DeviceHeadphone' and SubType = 'Attached2'", "DTSCSAssetId", null);
		// 14. Navigate to Asset table in SQLite
		// VP: Verify that Asset table will contain Device Headphone tuning
		Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(dts_db), "Select Type from Asset where Id = " + DTSCSAssetId.get(0) + "", "Type", "TUNING"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that Product Type is HPX High, when uploading Device Headphone route, the offline DB will have a new entry in the Product table with Type as 'DeviceHeadphone' and SubType as 'Default'
	 */
	@Test
	public void TC650DaDEN_88(){
		Reporter.log("Verify that Product Type is HPX High, when uploading Device Headphone route, the offline DB will have a new entry in the Product table with Type as 'DeviceHeadphone' and SubType as 'â€œAttached2â€�'");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			VP: The Device Edit page is displayed
			4. Fill valid value into all required fields
			5. Leave the Product Type panel as â€œHPX Highâ€�
			6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
			6a. Select â€œAttached2â€� in â€œContent Modesâ€� dropdown list
			7. Click on "Select file" button in "Custom Tuning" section
			8. Upload the valid custom tuning DTSCS file
			"VP: Verify that the valid tuning DTSCS file is uploaded successfully
			VP: Verify that â€œDevice Headphoneâ€� will disappear in â€œAudio Routeâ€� dropdown list"
			9. Click â€œSaveâ€� link
			10. Publish above device
			11. Refresh offline database
			12. Click "Download Offline Database" link
			13. Open the offline databse file by SQLite
			14. Navigate to Product table in SQLite
			"VP: Verify that Product table display new entry with Type as â€œDeviceHeadphoneâ€� and SubType as â€œâ€œAttached2â€�â€�"

		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
		// 4. Fill valid value into all required fields
		Hashtable<String,String> dataDevice = TestData.appDevicePublish();
		dataDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice);
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		// 5. Leave the Product Type panel as â€œHPX Highâ€�
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
		// 6. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		//6a. Select â€œAttached2â€� in â€œContent Modesâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
		// 7. Click on "Select file" button in "Custom Tuning" section
		// 8. Upload the valid custom tuning DTSCS file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Default.getName());
		// VP: Verify that the valid tuning DTSCS file is uploaded successfully
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Default.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		// VP: Verify that â€œDevice Headphoneâ€� will disappear in â€œAudio Routeâ€� dropdown list
		// 9. Click â€œSaveâ€� link
		appDeviceControl.click(DeviceEdit.SAVE);
		// 10. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String dts_db = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 11. Refresh offline database
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		// 12. Click "Download Offline Database" link
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
		// 13. Open the offline databse file by SQLite
		// 14. Navigate to Product table in SQLite
		// VP: Verify that Product table display new entry with Type as â€œDeviceHeadphoneâ€� and SubType as â€œDefaultâ€�
		Assert.assertTrue(appDeviceControl.isDBTableContainsData(appDeviceControl.getOfflineDBName(dts_db), "Select Type, SubType from Product", "Type", "SubType", "DeviceHeadphone", "Attached2"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that Product Type is HPX Meidum, when uploading Device Headphone route, the offline DB will have a new entry in the Product table with Type as â€œDeviceHeadphoneâ€� and SubType as â€œDefaultâ€�
	 */
	@Test
	public void TC650DaDEN_89(){
		Reporter.log("Verify that Product Type is HPX Medium, when uploading Device Headphone route, the offline DB will have a new entry in the Product table with Type as 'DeviceHeadphone' and SubType as 'â€œAttached2â€�'");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			VP: The Device Edit page is displayed
			4. Fill valid value into all required fields
			5. Leave the Product Type panel as â€œHPX Mediumâ€�
			6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
			6a. Select â€œAttached2â€� in â€œContent Modesâ€� dropdown list
			7. Click on "Select file" button in "Custom Tuning" section
			8. Upload the valid custom tuning DTSCS file
			"VP: Verify that the valid tuning DTSCS file is uploaded successfully
			VP: Verify that â€œDevice Headphoneâ€� will disappear in â€œAudio Routeâ€� dropdown list"
			9. Click â€œSaveâ€� link
			10. Publish above device
			11. Refresh offline database
			12. Click "Download Offline Database" link
			13. Open the offline databse file by SQLite
			14. Navigate to Product table in SQLite
			"VP: Verify that Product table display new entry with Type as â€œDeviceHeadphoneâ€� and SubType as â€œâ€œAttached2â€�â€�"

		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
		// 4. Fill valid value into all required fields
		Hashtable<String,String> dataDevice = TestData.appDevicePublish();
		dataDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice);
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		// 5. Leave the Product Type panel as â€œHPX Mediumâ€�
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
		// 6. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		//6a. Select â€œAttached2â€� in â€œContent Modesâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
		// 7. Click on "Select file" button in "Custom Tuning" section
		// 8. Upload the valid custom tuning DTSCS file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Default.getName());
		// VP: Verify that the valid tuning DTSCS file is uploaded successfully
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Default.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		// VP: Verify that â€œDevice Headphoneâ€� will disappear in â€œAudio Routeâ€� dropdown list
		// 9. Click â€œSaveâ€� link
		appDeviceControl.click(DeviceEdit.SAVE);
		// 10. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String dts_db = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 11. Refresh offline database
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		// 12. Click "Download Offline Database" link
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
		// 13. Open the offline databse file by SQLite
		// 14. Navigate to Product table in SQLite
		// VP: Verify that Product table display new entry with Type as â€œDeviceHeadphoneâ€� and SubType as â€œDefaultâ€�
		Assert.assertTrue(appDeviceControl.isDBTableContainsData(appDeviceControl.getOfflineDBName(dts_db), "Select Type, SubType from Product", "Type", "SubType", "DeviceHeadphone", "Attached2"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that Product Type is HPX Low, Device Headphone route can be uploaded 3 times
	 */
	@Test
	public void TC650DaDEN_90(){
		Reporter.log("Verify that Product Type is HPX Low, Device Headphone route can be uploaded 3 times");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Leave the Eagle Version panel as â€œEagle 1.4â€�
			5. Leave the Product Type panel as â€œHPX Lowâ€�
			6. Select â€œDevice Headphoneâ€� in Audio Route
			7. Select â€œMode 1â€� in â€œContent Modesâ€� dropdown list
			8. Click "Choose fille" to upload custom tuning file
			"VP: Verify that the valid tuning DTSCS file is uploaded successfully
			VP: Verify that â€œDevice Headphoneâ€� will not disappear in â€œAudio Routeâ€� dropdown list"
			9. Select â€œDevice Headphoneâ€� in Audio Route
			10. Select â€œMode 2â€� in â€œContent Modesâ€� dropdown list
			11. Click "Choose fille" to upload custom tuning file
			"VP: Verify that the valid tuning DTSCS file is uploaded successfully
			VP: Verify that â€œDevice Headphoneâ€� will not disappear in â€œAudio Routeâ€� dropdown list"
			12. Select â€œDevice Headphoneâ€� in Audio Route
			13. Select â€œMode 3â€� in â€œContent Modesâ€� dropdown list
			14. Click "Choose fille" to upload custom tuning file
			"VP: Verify that the valid tuning DTSCS file is uploaded successfully
			VP: Verify that â€œDevice Headphoneâ€� will disappear in â€œAudio Routeâ€� dropdown list"

		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Leave the Eagle Version panel as â€œEagle 1.4â€�
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		// 5. Leave the Product Type panel as â€œHPX Lowâ€�
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_Low.getType());
		// 6. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		// 7. Select â€œMode 1â€� in â€œContent Modesâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_1.getType());
		// 8. Click "Choose fille" to upload Device Headphone tuning file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Mode1.getName());
		// VP: Verify that the valid tuning DTSCS file is uploaded successfully
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Mode1.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		// VP: Verify that â€œDevice Headphoneâ€� will not disappear in â€œAudio Routeâ€� dropdown list"
		ArrayList<String> typeList = appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE);
		Assert.assertTrue(typeList.contains(DeviceEdit.routes.Device_Headphone.getName()));
		// 9. Select â€œDevice Headphoneâ€� in Audio Route
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		// 10. Select â€œMode 2â€� in â€œContent Modesâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_2.getType());
		// 11. Click "Choose fille" to upload custom tuning file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Mode2.getName());
		// "VP: Verify that the valid tuning DTSCS file is uploaded successfully
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME_2, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Mode2.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		// VP: Verify that â€œDevice Headphoneâ€� will not disappear in â€œAudio Routeâ€� dropdown list"
		ArrayList<String> typeList1 = appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE);
		Assert.assertTrue(typeList1.contains(DeviceEdit.routes.Device_Headphone.getName()));
		// 12. Select â€œDevice Headphoneâ€� in Audio Route
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		// 13. Select â€œMode 3â€� in â€œContent Modesâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_3.getType());
		// 14. Click "Choose fille" to upload custom tuning file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Mode3.getName());
		// "VP: Verify that the valid tuning DTSCS file is uploaded successfully
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME_3, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Mode3.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		// VP: Verify that â€œDevice Headphoneâ€� will disappear in â€œAudio Routeâ€� dropdown list"
		ArrayList<String> typeList2 = appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE);
		Assert.assertFalse(typeList2.contains(DeviceEdit.routes.Device_Headphone.getName()));
	}
	
	/*
	 * Verify that Product Type is HPX Low, the invalid route tuning file is uploaded unsuccessfully when selecting Device Headphone and Content Mode
	 */
	@Test
	public void TC650DaDEN_91(){
		Reporter.log("Verify that Product Type is HPX Low, the invalid route tuning file is uploaded unsuccessfully when selecting Device Headphone and Content Mode");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			VP: The Device Edit page is displayed
			4. Leave the Eagle Version panel as â€œEagle 1.4â€�
			5. Leave the Product Type panel as â€œHPX Lowâ€�
			6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
			7. Select â€œMode 2â€� in â€œContent Modesâ€� dropdown list
			8. Click on "Select file" button in "Custom Tuning" section
			9. Upload the custom tuning DTSCS file that having route not match
			VP: Verify that â€œ! Invalid file: The Route object does not match the Audio Route selection.â€� message is displayed

		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
		// 4. Leave the Eagle Version panel as â€œEagle 1.4â€�
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		// 5. Leave the Product Type panel as â€œHPX Lowâ€�
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_Low.getType());
		// 6. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		// 7. Select â€œMode 1â€� in â€œContent Modesâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_1.getType());
		// 8. Click on "Select file" button in "Custom Tuning" section
		// 9. Upload the custom tuning DTSCS file that having route not match
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Earbuds_Lineout.getName());
		// VP: Verify that â€œ! Invalid file: The Route object does not match the Audio Route selection.â€� message is displayed
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS).contains(DeviceEdit.requires.Invalid_file_contents.getName()));
		
	}
	
	/*
	 * Verify that Product Type is HPX Low, the valid route tuning file is uploaded successfully when selecting Device Headphone and Content Mode
	 */
	@Test
	public void TC650DaDEN_92(){
		Reporter.log("Verify that Product Type is HPX Low, the valid route tuning file is uploaded successfully when selecting Device Headphone and Content Mode");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			VP: The Device Edit page is displayed
			4. Leave the Eagle Version panel as â€œEagle 1.4â€�
			5. Leave the Product Type panel as â€œHPX Lowâ€�
			6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
			7. Select â€œMode 2â€� in â€œContent Modesâ€� dropdown list
			8. Click on "Select file" button in "Custom Tuning" section
			9. Upload the valid custom tuning DTSCS file
			VP: Verify that the valid tuning DTSCS file is uploaded successfully

		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
		// 4. Leave the Eagle Version panel as â€œEagle 1.4â€�
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		// 5. Leave the Product Type panel as â€œHPX Lowâ€�
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_Low.getType());
		// 6. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		// 7. Select â€œMode 1â€� in â€œContent Modesâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_2.getType());
		// 8. Click on "Select file" button in "Custom Tuning" section
		// 9. Upload the custom tuning DTSCS file that having route not match
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Mode2.getName());
		// VP: Verify that the valid tuning DTSCS file is uploaded successfully
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Mode2.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
	}
	
	/*
	 * Verify that Product Type is HPX Low , when uploading Device Headphone route, the offline DB will have a new entry in the Product table with Type and SubType data correctly
	 */
	@Test
	public void TC650DaDEN_93(){
		Reporter.log("Verify that Product Type is HPX Low , when uploading Device Headphone route, the offline DB will have a new entry in the Product table with Type and SubType data correctly");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			VP: The Device Edit page is displayed
			4. Fill valid value into all required fields
			5. Leave the Product Type panel as â€œHPX Lowâ€�
			6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
			7. Select â€œMode 1â€� in â€œContent Modesâ€� dropdown list
			8. Click on "Select file" button in "Custom Tuning" section
			9. Upload the valid custom tuning DTSCS file
			VP: Verify that the valid tuning DTSCS file is uploaded successfully
			10. Click â€œSaveâ€� link
			11. Publish above device
			12. Refresh offline database
			13. Click "Download Offline Database" link
			14. Open the offline databse file by SQLite
			15. Navigate to Product table in SQLite
			"VP: Verify that Product table display new entry with Type as â€œDeviceHeadphoneâ€� and SubType as â€œAttached2â€�"

		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
		// 4. Fill valid value into all required fields
		Hashtable<String,String> dataDevice = TestData.appDevicePublishLow();
		dataDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice);
		// 5. Leave the Product Type panel as â€œHPX Lowâ€�
		// 6. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		// 7. Select â€œMode 1â€� in â€œContent Modesâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_1.getType());
		// 8. Click on "Select file" button in "Custom Tuning" section
		// 9. Upload the valid custom tuning DTSCS file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Mode1.getName());
		// VP: Verify that the valid tuning DTSCS file is uploaded successfully
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Mode1.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		// 10. Click â€œSaveâ€� link
		appDeviceControl.click(DeviceEdit.SAVE);
		// 11. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String dts_db = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 12. Refresh offline database
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		// 13. Click "Download Offline Database" link
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
		// 14. Open the offline databse file by SQLite
		// 15. Navigate to Product table in SQLite
		// VP: Verify that Product table display new entry with Type as â€œDeviceHeadphoneâ€� and SubType as â€œAttached2â€�
		Assert.assertTrue(appDeviceControl.isDBTableContainsData(appDeviceControl.getOfflineDBName(dts_db), "Select Type, SubType from Product", "Type", "SubType", "DeviceHeadphone", "Attached2"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that â€œEagle Versionâ€� feature is displayed correctly
	 */
	@Test
	public void TC650DaDEN_94(){
		Reporter.log("Verify that â€œEagle Versionâ€� feature is displayed correctly");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devicesâ€� page
			3. Click â€œAdd App & Deviceâ€� link
			VP: The "Eagle Version" combobox contains: â€œEagle 1.4â€�, â€œEagle 2.0â€�
			VP: The default value in "Eagle Version" combobox is â€œEagle 2.0"

		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// VP: The "Eagle Version" combobox contains: â€œEagle 1.4â€�, â€œEagle 2.0â€�
		ArrayList<String> typeList = appDeviceControl.getAllComboboxOption(DeviceEdit.EAGLE_VERSION);
		Assert.assertTrue(ListUtil.containsAll(typeList, DeviceEdit.Eagle_Version.getNames()));
		// VP: The default value in "Eagle Version" combobox is â€œEagle 2.0"
		Assert.assertEquals(appDeviceControl.getItemSelected(DeviceEdit.EAGLE_VERSION), DeviceEdit.Eagle_Version.Eagle2_0.getName());
	}
	
	/*
	 * Verify that Device Eagle Version is 1.4,  the custom tuning have the Eagle settings data Version 2.0 is uploaded unsuccessfully
	 */
	@Test
	public void TC650DaDEN_95(){
		Reporter.log("Verify that Device Eagle Version is 1.4,  the custom tuning have the Eagle settings data Version 2.0 is uploaded unsuccessfully");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devicesâ€� page
			3. Click â€œAdd App & Deviceâ€� link
			4. Leave the Eagle Version panel as â€œEagle 1.4â€�
			5. Select "Bluetooth0 â€“ Bluetooth" in Audio Route dropdown list
			6. Select "Earbuds" in Standard Accessories dropdown list
			7. Click on "Select file" button in "Custom Tuning" section
			8. Upload the valid custom tuning DTSCS file which have the Eagle settings data Version 2.0
			VP: Verify that the custom tuning file is uploaded unsuccessfully

		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Leave the Eagle Version panel as â€œEagle 1.4â€�
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_High.getType());
		// 5. Select "Bluetooth0 â€“ Bluetooth" in Audio Route dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Bluetooth0.getName());
		// 6. Select "Earbuds" in Standard Accessories dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Earbuds.getName());
		// 7. Click on "Select file" button in "Custom Tuning" section
		// 8. Upload the valid custom tuning DTSCS file which have the Eagle settings data Version 2.0
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Earbuds_Bluetooth_EagleV20.getName());
		// VP: Verify that the custom tuning file is uploaded unsuccessfully
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS).contains(DeviceEdit.requires.Invalid_file_Please_upload_the_Eagle_v1_1_tuning_file.getName()));
	}
	
	/*
	 * Verify that Device Eagle Version is 2.0,  the custom tuning have the Eagle settings data Version 1.4 is uploaded unsuccessfully
	 */
	@Test
	public void TC650DaDEN_96(){
		Reporter.log("Verify that Device Eagle Version is 2.0,  the custom tuning have the Eagle settings data Version 1.4 is uploaded unsuccessfully");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devicesâ€� page
			3. Click â€œAdd App & Deviceâ€� link
			4. Leave the Eagle Version panel as â€œEagle 2.0â€�
			5. Select "Bluetooth0 â€“ Bluetooth" in Audio Route dropdown list
			6. Select "Earbuds" in Standard Accessories dropdown list
			7. Click on "Select file" button in "Custom Tuning" section
			8. Upload the valid custom tuning DTSCS file which have the Eagle settings data Version 1.4
			VP: Verify that the custom tuning file is uploaded unsuccessfully

		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Leave the Eagle Version panel as â€œEagle 1.4â€�
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
		// 5. Select "Bluetooth0 â€“ Bluetooth" in Audio Route dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Bluetooth0.getName());
		// 6. Select "Earbuds" in Standard Accessories dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Earbuds.getName());
		// 7. Click on "Select file" button in "Custom Tuning" section
		// 8. Upload the valid custom tuning DTSCS file which have the Eagle settings data Version 1.4
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Earbuds_Bluetooth.getName());
		// VP: Verify that the custom tuning file is uploaded unsuccessfully
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS).contains(DeviceEdit.requires.Invalid_file_Please_upload_the_Eagle_v2_0_tuning_file.getName()));
	}
	
	/*
	 * Verify that Device Eagle Version is 1.4, the custom tuning have the Eagle settings data match with Device's Eagle version is uploaded successfully
	 */
	@Test
	public void TC650DaDEN_97(){
		Reporter.log("Verify that Device Eagle Version is 1.4, the custom tuning have the Eagle settings data match with Device's Eagle version is uploaded successfully");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devicesâ€� page
			3. Click â€œAdd App & Deviceâ€� link
			4. Leave the Eagle Version panel as â€œEagle 1.4â€�
			5. Select "Line-out0 â€“ Line Out" in Audio Route dropdown list
			6. Select "Over-Ear Headphones" in Standard Accessories dropdown list
			7. Click on "Select file" button in "Custom Tuning" section
			8. Upload the valid custom tuning DTSCS file which have the Eagle settings data Version 1.4
			VP: Verify that the custom tuning file is uploaded successfully

		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Leave the Eagle Version panel as â€œEagle 1.4â€�
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		// 5. Select "Bluetooth0 â€“ Bluetooth" in Audio Route dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Line_out0.getName());
		// 6. Select "Earbuds" in Standard Accessories dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
		// 7. Click on "Select file" button in "Custom Tuning" section
		// 8. Upload the valid custom tuning DTSCS file which have the Eagle settings data Version 1.4
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones_Lineout.getName());
		// VP: Verify that the custom tuning file is uploaded successfully
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.CUSTOM_TUNING_MESS));
	}
	
	/*
	 * Verify that Device Eagle Version is 2.0, the custom tuning have the Eagle settings data match with Device's Eagle version is uploaded successfully
	 */
	@Test
	public void TC650DaDEN_98(){
		Reporter.log("Verify that Device Eagle Version is 2.0, the custom tuning have the Eagle settings data match with Device's Eagle version is uploaded successfully");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devicesâ€� page
			3. Click â€œAdd App & Deviceâ€� link
			4. Leave the Eagle Version panel as â€œEagle 2.0â€�
			5. Select "Line-out0 â€“ Line Out" in Audio Route dropdown list
			6. Select "Over-Ear Headphones" in Standard Accessories dropdown list
			7. Click on "Select file" button in "Custom Tuning" section
			8. Upload the valid custom tuning DTSCS file which have the Eagle settings data Version 2.0
			VP: Verify that the custom tuning file is uploaded successfully

		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Leave the Eagle Version panel as â€œEagle 2.0â€�
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
		// 5. Select "Bluetooth0 â€“ Bluetooth" in Audio Route dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Line_out0.getName());
		// 6. Select "Earbuds" in Standard Accessories dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
		// 7. Click on "Select file" button in "Custom Tuning" section
		// 8. Upload the valid custom tuning DTSCS file which have the Eagle settings data Version 2.0
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones_Lineout_EagleV20.getName());
		// VP: Verify that the custom tuning file is uploaded successfully
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.CUSTOM_TUNING_MESS));
	}
	
	@Test
	public void TC650DaDEN_99(){// due to ticket MPG-5927, The Device Tuning already changed
		Reporter.log("Verify that â€œDevice Tuningâ€� section is displayed correctly ");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devicesâ€� page
			3. Click â€œAdd App & Deviceâ€� link
			4. Leave â€œEagle versionâ€� section is â€œEagle 2.0â€�
			VP: Verify that â€œDevice Tuningâ€� section is displayed in 650D Device Edit page
		 */
		
/*		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
		// 4. Leave the Eagle Version panel as â€œEagle 2.0â€�
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
		//VP: Verify that â€œDevice Tuningâ€� section is displayed in 650D Device Edit page
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DEVICE_TUNING_SECTION_LABEL));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DEVICE_TUNING));

		//VP: Verify that â€œDevice Tuningâ€� combobox contains: 
		// â€œ- Select -â€�, â€œGPEQ tuningâ€�, â€œCTC tuningâ€�
		ArrayList<String> deviceTungins = appDeviceControl.getAllComboboxOption(DeviceEdit.DEVICE_TUNING);
		Assert.assertTrue(ListUtil.containsAll(deviceTungins, DeviceEdit.Device_Tunings.getNames()));

		//VP: Verify that the default  value in â€œDevice Tuningâ€� combobox is â€œ- Select -â€�
		Assert.assertEquals(appDeviceControl.getItemSelected(DeviceEdit.DEVICE_TUNING), DeviceEdit.Device_Tunings.Select.getName());
	*/	
	}
	@Test
	public void TC650DaDEN_100(){// This testcase cannot berun due to ticket MPG-5927, the CTC and GPEQ already add in Default Audio route
		Reporter.log("Verify that CTC/GPEQ only support for Eagle 2.0 ");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devicesâ€� page
			3. Click â€œAdd App & Deviceâ€� link
			4. Leave â€œEagle versionâ€� section is â€œEagle 2.0â€�
			5. Leave â€œEagle versionâ€� section is â€œEagle 1.4â€�
			6. Leave â€œEagle versionâ€� section is â€œEagle 2.0â€� again
			7. Select â€œGPEQ tuningâ€� from Device Tuning combobox
			8. Upload the valid GPEQ tuning blob
			9. Leave â€œEagle versionâ€� section is â€œEagle 1.4â€� again
			10. Click â€œOKâ€� button
		 */
		
/*		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
		// 4. Leave the Eagle Version panel as â€œEagle 2.0â€�
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
		//VP: Verify that â€œDevice Tuningâ€� section is displayed in 650D Device Edit page
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DEVICE_TUNING_SECTION_LABEL));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DEVICE_TUNING));
		//5. Leave â€œEagle versionâ€� section is â€œEagle 1.4â€�
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION , DeviceEdit.Eagle_Version.Eagle1_4.getName());
		//VP: Verify that â€œDevice Tuningâ€� section is not displayed in 650D Device Edit page
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.DEVICE_TUNING_SECTION_LABEL));
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.DEVICE_TUNING));
		//6. Leave â€œEagle versionâ€� section is â€œEagle 2.0â€� again
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
		//7. Select â€œGPEQ tuningâ€� from Device Tuning combobox
		appDeviceControl.selectOptionByName(DeviceEdit.DEVICE_TUNING, DeviceEdit.Device_Tunings.GPEQ_Tuning.getName());
		//8. Upload the valid GPEQ tuning blob
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_DEVICE_TUNING, AddEditProductModel.FileUpload.Device_Tuning_GPEQ.getName());
		//9. Leave â€œEagle versionâ€� section is â€œEagle 1.4â€� again
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION , DeviceEdit.Eagle_Version.Eagle1_4.getName());
		//VP: Verify that the confirmation popup is showed up
		Assert.assertTrue(appDeviceControl.checkMessageDisplay(DeviceEdit.requires.Switch_Eagle_Version.getName()));
		//10. Click â€œOKâ€� button
		appDeviceControl.click(DeviceEdit.OK_BUTTON);
		//VP: Verify that above blob tuning is removed
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.UPLOADED_DEVICE_TUNING_NAME));
		*/
	}
	/* invalid test case dute to MPG-5927
	@Test
	public void TC650DaDEN_101(){
		Reporter.log("Verify that the invalid GPEQ tuning blob file is uploaded unsuccessfully in 650D Device Edit page");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devicesâ€� page
			3. Click â€œAdd App & Deviceâ€� link
			4. Leave â€œEagle versionâ€� section is â€œEagle 2.0â€�
			5. Select â€œGPEQ tuningâ€� in Device Tuning combobox
			6. Upload the invalid GPEQ tuning blob file
		 
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
		// 4. Leave the Eagle Version panel as â€œEagle 2.0â€�
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
		// 5. Select â€œGPEQ tuningâ€� from Device Tuning combobox
		appDeviceControl.selectOptionByName(DeviceEdit.DEVICE_TUNING, DeviceEdit.Device_Tunings.GPEQ_Tuning.getName());
		// 6. Upload the invalid GPEQ tuning blob
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_DEVICE_TUNING, AddEditProductModel.FileUpload.Earbuds_Lineout.getName());
		// VP: Verify that the invalid GPEQ tuning blob file is upload unsuccessfully
		Assert.assertTrue(appDeviceControl.checkMessageDisplay(DeviceEdit.requires.Invalid_file_contents.getName()));
		
	}
*/
	@Test
	public void TC650DaDEN_102(){ //this testcase cannot be run due to ticket MPG-5927, GPEQ already added in Default Audio route
		Reporter.log("Verify that the valid GPEQ tuning blob file is uploaded successfully in 650D Device Edit page");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devicesâ€� page
			3. Click â€œAdd App & Deviceâ€� link
			4. Leave â€œEagle versionâ€� section is â€œEagle 2.0â€�
			5. Select â€œGPEQ tuningâ€� in Device Tuning combobox
			6. Upload the valid GPEQ tuning blob file
		 */
		
/*		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
		// 4. Leave the Eagle Version panel as â€œEagle 2.0â€�
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
		// 5. Select â€œGPEQ tuningâ€� from Device Tuning combobox
		appDeviceControl.selectOptionByName(DeviceEdit.DEVICE_TUNING, DeviceEdit.Device_Tunings.GPEQ_Tuning.getName());
		// 6. Upload the valid GPEQ tuning blob
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_DEVICE_TUNING, AddEditProductModel.FileUpload.Device_Tuning_GPEQ.getName());
		// VP: Verify that the valid GPEQ tuning blob file is upload 	successfully
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_DEVICE_TUNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Tuning_GPEQ.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_DEVICE_TUNNING_ICON));
	*/	
	}
	
	@Test
	public void TC650DaDEN_103(){// Due to ticket MPG-5927 CTC did not show up on UI, already added on Default Audio route.
		Reporter.log("Verify that the invalid CTC tuning blob file is uploaded unsuccessfully in 650D Device Edit page");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devicesâ€� page
			3. Click â€œAdd App & Deviceâ€� link
			4. Leave â€œEagle versionâ€� section is â€œEagle 2.0â€�
			5. Select â€œCTC tuningâ€� in Device Tuning combobox
			6. Upload the invalid CTC tuning blob file
		 */
		
/*		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
		// 4. Leave the Eagle Version panel as â€œEagle 2.0â€�
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
		// 5. Select â€œGPEQ tuningâ€� from Device Tuning combobox
		appDeviceControl.selectOptionByName(DeviceEdit.DEVICE_TUNING, DeviceEdit.Device_Tunings.CTC_Tuning.getName());
		// 6. Upload the invalid CTC tuning blob
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_DEVICE_TUNING, AddEditProductModel.FileUpload.Earbuds_Lineout.getName());
		// VP: Verify that the invalid CTC tuning blob file is upload unsuccessfully
		Assert.assertTrue(appDeviceControl.checkMessageDisplay(DeviceEdit.requires.Invalid_file_contents.getName()));*/
		
	}
	@Test
	public void TC650DaDEN_104(){// This testcase cannot berun due to ticket MPG-5927, the CTC already add in Default Audio route
		Reporter.log("Verify that the valid CTC tuning blob file is uploaded successfully in 650D Device Edit page");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devicesâ€� page
			3. Click â€œAdd App & Deviceâ€� link
			4. Leave â€œEagle versionâ€� section is â€œEagle 2.0â€�
			5. Select â€œCTC tuningâ€� in Device Tuning combobox
			6. Upload the valid CTC tuning blob file
		 */
		
/*		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
		// 4. Leave the Eagle Version panel as â€œEagle 2.0â€�
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
		// 5. Select â€œGPEQ tuningâ€� from Device Tuning combobox
		appDeviceControl.selectOptionByName(DeviceEdit.DEVICE_TUNING, DeviceEdit.Device_Tunings.CTC_Tuning.getName());
		// 6. Upload the valid CTC tuning blob
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_DEVICE_TUNING, AddEditProductModel.FileUpload.Device_Tuning_CTC.getName());
		// VP: Verify that the valid CTC tuning blob file is upload 	successfully
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_DEVICE_TUNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Tuning_CTC.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_DEVICE_TUNNING_ICON));
	*/	
	}
	
	@Test
	public void TC650DaDEN_105(){//this testcase cannot be run due to ticket MPG-5927, Att6 to 8 already added in Default Audio route
		Reporter.log("Verify that Other Attached 6-8 are displayed in 650D Device Edit page");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devicesâ€� page
			3. Click “Add App & Device” link
		 */
/*		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Apps or Devicesâ€� page	
		loginControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click “Add App & Device” link
		loginControl.click(DeviceList.CREATE_NEW_DEVICE);
		VP: The "Audio Route" combobox contains: 
		"-Select-",
		“Line-out0 – Lineout”,
		"Bluetooth0 – Bluetooth",
		“USB0 – USB”,
		"Attached0 - Internal Speakers (Default)", 
		"Attached1 - Internal Speakers (Off)",
		"Attached2 - Internal Speakers (mode 1)",
		"Attached3 - Internal Speakers (mode 2)",
		"Attached4 - Internal Speakers (mode 3)",
		"Attached5 - Internal Speakers (mode 4)",
		"Attached6 - Internal Speakers (mode 5)", 
		"Attached7 - Internal Speakers (mode 6)", 
		"Attached8 - Internal Speakers (mode 7)",
		"Combo0 - Dual Audio", 
		"Device Headphone Settings"  items
		
		Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE),DeviceEdit.routes.getNames()));
*/
	}
	@Test
	public void TC650DaDEN_106(){// Due to ticket MPG-5927 the eagle version 2.0 attached 6-8 already add in Default Audio route
		Reporter.log("Verify that Attached 6-8 will support for both Eagle 1.4 and Eagle 2.0");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devicesâ€� page
			3. Click â€œAdd App & Deviceâ€� link
			4. Leave the Eagle Version panel as â€œEagle 1.4â€�
			5. Select to ''Audio Route''
			VP: Verify that Audio Route will contain Device Headphone tuning(Attached 6 , 7 and 8 )
			6. Change the Eagle Version panel to â€œEagle 2.0â€�
			7. Select to ''Audio Route''
			VP: Verify that Audio Route will contain  Device Headphone tuning  (Attached 6 , 7 and 8 )
			
		 */
/*		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Apps or Devicesâ€� page	
		loginControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App & Deviceâ€� link
		loginControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Leave the Eagle Version panel as â€œEagle 1.4â€�
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		// VP: Verify that Audio Route will contain Device Headphone tuning(Attached 6 , 7 and 8 )
		Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE),DeviceEdit.routes.getNames()));
		// 5. Select to ''Audio Route''
		//VP: Verify that Audio Route will contain Device Headphone tuning(Attached 6 , 7 and 8 )
		Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE),DeviceEdit.routes.getNames()));
		// 6. Change the Eagle Version panel to â€œEagle 2.0â€�
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
		// 7. Select to ''Audio Route''
        // VP: Verify that Audio Route will contain Device Headphone tuning(Attached 6 , 7 and 8 )
		Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE),DeviceEdit.routes.getNames()));
		*/
	}
	
	@Test
	public void TC650DaDEN_107(){// This testcase cannot berun due to ticket MPG-5927, the attched6-8 already added in Default Audio route
		Reporter.log("Verify that the valid tuning file is uploaded successfully  when selecting internal speaker audio route (Attached 6 , 7 and 8 )");
		/*
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click on "Add App or Device" link
			4. Select Internal Speakers (select Attached 6) in "Audio Route" drop down list
			5. Click on "Choose file" button in "Custom Tuning" section
			6. Upload the valid custom tuning DTSCS file
			"""VP: Verify that the valid custom tuning DTSCS file is upload successfully
			VP: A ""Invalid route"" message is not displayed in """"Custom Tuning"""" section"""
			7. Repeat step 4, 5, 6 with select Attached 7 and Attached 8
		 */
/*		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Apps or Devicesâ€� page	
		loginControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App & Deviceâ€� link
		loginControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Select Internal Speakers (select Attached 6) in "Audio Route" drop down list
		loginControl.click(DeviceEdit.AUDIO_ROUTE_TYPE);
		// 5. Click on "Choose file" button in "Custom Tuning" section
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached6_Internal_Speakers_Mode_5.getName());
		// 6. Upload the valid custom tuning DTSCS file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached_6_Game_2_Eagle2_0.getName());
		// """VP: Verify that the valid custom tuning DTSCS file is upload successfully
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME));
		// 7. Repeat step 4, 5, 6 with select Attached 7 and Attached 8
		// Click on "Choose file" button in "Custom Tuning" section
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached7_Internal_Speakers_Mode_6.getName());
		// Upload the valid custom tuning DTSCS file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached_7_Game_3_Eagle2_0.getName());
		// """VP: Verify that the valid custom tuning DTSCS file is upload successfully
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME));
		// Click on "Choose file" button in "Custom Tuning" section
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached8_Internal_Speakers_Mode_7.getName());
		// Upload the valid custom tuning DTSCS file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached_8_Custom_Eagle2_0.getName());
		// """VP: Verify that the valid custom tuning DTSCS file is upload successfully
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME));*/
	}

@Test
public void TC650DaDEN_108(){
	Reporter.log("Verify that the invalid tuning file is uploaded unsuccessfully  when selecting internal speaker audio route (Attached 6 , 7 and 8 )");
	/*
		1. Log into DTS portal as DTS admin
		2. Navigate to "Apps & Devices" page
		3. Click on "Add App or Device" link
		4. Select Internal Speakers (select Attached 6) in "Audio Route" drop down list
		5. Click on "Choose file" button in "Custom Tuning" section
		6. Upload the valid custom tuning DTSCS file
		"""VP: Verify that the valid custom tuning DTSCS file is upload successfully
		VP: A ""Invalid route"" message is not displayed in """"Custom Tuning"""" section"""
		7. Repeat step 4, 5, 6 with select Attached 7 and Attached 8
	 */
	// 1. Log into DTS portal as a DTS user
	loginControl.login(DTS_USER, DTS_PASSWORD);
	// 2. Navigate to "Apps or Devicesâ€� page	
	loginControl.click(PageHome.LINK_APP_DEVICES);
	// 3. Click â€œAdd App & Deviceâ€� link
	loginControl.click(DeviceList.CREATE_NEW_DEVICE);
	appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
	appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_High.getType());
	// 4. Select Internal Speakers (select Attached 6) in "Audio Route" drop down list
	loginControl.click(DeviceEdit.AUDIO_ROUTE_TYPE);
	// 5. Click on "Choose file" button in "Custom Tuning" section
	appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached6_Internal_Speakers_Mode_5.getName());
	// 6. Upload the valid custom tuning DTSCS file
	appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached_7_Game_3_Eagle2_0.getName());
	/*
	 * VP: A "Invalid file contents: Route, it must appropriate the Audio Route" message is displayed in "Custom Tuning" section
	 */
	Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE));
	// 7. Repeat step 4, 5, 6 with select Attached 7 and Attached 8
	// Click on "Choose file" button in "Custom Tuning" section
	appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached7_Internal_Speakers_Mode_6.getName());
	// Upload the valid custom tuning DTSCS file
	appDeviceControl.uploadFile(DeviceEdit.RETRY_DEFAULT_AUDIO_ROUTE, AddEditProductModel.FileUpload.Attached_6_Game_2_Eagle2_0.getName());
	/*
	* VP: A "Invalid file contents: Route, it must appropriate the Audio Route" message is displayed in "Custom Tuning" section
	*/
	Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE));
	// Click on "Choose file" button in "Custom Tuning" section
	appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached8_Internal_Speakers_Mode_7.getName());
	// Upload the valid custom tuning DTSCS file
	appDeviceControl.uploadFile(DeviceEdit.RETRY_DEFAULT_AUDIO_ROUTE, AddEditProductModel.FileUpload.Attached7InternalSpeakersVoice_Fallback.getName());
	/*
	 * VP: A "Invalid file contents: Route, it must appropriate the Audio Route" message is displayed in "Custom Tuning" section
	 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE));   
}
	
	@Test
	public void TC650DaDEN_109(){
		Reporter.log("Verify that Device Headphone is added to the Audio Route dropdown list with product Type ''High'' and ''Medium''");
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click â€œAdd App or Deviceâ€� link
		4. Leave the Product Type panel as â€œHPX Highâ€�
		5. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
		6. Leave the Product Type panel as â€œHPX Mediumâ€�
		7. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
		 */
//		1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
//		2. Navigate to "Apps & Devices" page
		loginControl.click(PageHome.LINK_APP_DEVICES);
//		3. Click â€œAdd App or Deviceâ€� link
		loginControl.click(DeviceList.CREATE_NEW_DEVICE);
//		4. Leave the Product Type panel as â€œHPX Highâ€�
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_High.getType());
//		5. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
//		VP: The "Content Modes" combobox is displayed.
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE));
//		VP: The â€œContent Modesâ€� combobox contains: 
//		o  Attacehd 0 â€“ Default
//		oâ€‚â€‚Attached 1 â€“ Bypass/Off
//		oâ€‚â€‚Attached 2 - Music
//		oâ€‚â€‚Attached 3 â€“ Movies
//		oâ€‚â€‚Attached 4 - Voice
//		oâ€‚â€‚Attached 5 â€“ Game 1
//		oâ€‚â€‚Attached 6 â€“ Game 2
//		oâ€‚â€‚Attached 7 â€“ Game 3
//		oâ€‚â€‚Attached 8 â€“ Custom
	    Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE),DeviceEdit.Content_Modes_Hig.getNames()));
//		6. Leave the Product Type panel as â€œHPX Mediumâ€�
	    appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_Medium.getType());
//		7. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
	    appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
//		VP: The "Content Modes" combobox is displayed.
	    Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE));
//		VP: The â€œContent Modesâ€� combobox contains: 
//		o  Attacehd 0 â€“ Default
//		oâ€‚â€‚Attached 1 â€“ Bypass/Off
//		oâ€‚â€‚Attached 2 - Music
//		oâ€‚â€‚Attached 3 â€“ Movies
//		oâ€‚â€‚Attached 4 - Voice
//		oâ€‚â€‚Attached 5 â€“ Game 1
//		oâ€‚â€‚Attached 6 â€“ Game 2
//		oâ€‚â€‚Attached 7 â€“ Game 3
//		oâ€‚â€‚Attached 8 â€“ Custom
	    Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE),DeviceEdit.Content_Modes_Medium.getNames()));
//	    6. Leave the Product Type panel as â€œHPX Lowâ€�
	    appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_Low.getType());
//		7. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
	    appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
//	    VP: The "Content Modes" combobox is only contain  Mode 1, Mode 2, Mode 3.
	    Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE), DeviceEdit.Content_Modes_Low.getNames()));
	}	
	    @Test
		public void TC650DaDEN_110(){
			Reporter.log("Verify that Product Type is ''DTS:X Master'' Device Headphone route can be uploaded 7 times");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Leave the Product Type panel as â€œDTS:X Masterâ€�
			5. Select â€œDevice Headphoneâ€� in Audio Route
			6. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
			7. Click "Choose fille" to upload custom tuning file
			8. Select â€œAttached 3â€� in â€œContent Modesâ€� dropdown list
			9. Click "Choose fille" to upload custom tuning file
			10. Select â€œAttached 4â€� in â€œContent Modesâ€� dropdown list
			11. Click "Choose fille" to upload custom tuning file
			12. Select â€œAttached 5â€� in â€œContent Modesâ€� dropdown list
			13. Click "Choose fille" to upload custom tuning file
			14. Select â€œAttached 6â€� in â€œContent Modesâ€� dropdown list
			15. Click "Choose fille" to upload custom tuning file
			18. Select â€œAttached 6â€� in â€œContent Modesâ€� dropdown list
			19. Click "Choose fille" to upload custom tuning file
			20. Select â€œAttached 7â€� in â€œContent Modesâ€� dropdown list
			21. Click "Choose fille" to upload custom tuning file
			22. Select â€œAttached 8â€� in â€œContent Modesâ€� dropdown list
			23. Click "Choose fille" to upload custom tuning file


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			loginControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click â€œAdd App or Deviceâ€� link
			loginControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type panel as â€œHPX Highâ€�
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
//			5. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
//			6. Select â€œAttached 2 - Musicâ€� in â€œContent Modesâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());			
//			7. Click "Choose fille" to upload custom tuning file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached_2_Music_Eagle2_0.getName());
//			VP: Verify that the valid tuning DTSCS file is uploaded successfully
			Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Attached_2_Music_Eagle2_0.getName().replaceAll(".dtscs", "")));
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
//			8. Select â€œAttached 3 â€“ Moviesâ€� in â€œContent Modesâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_3_Movies.getName());			
//			9. Click "Choose fille" to upload custom tuning file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached_3_Movies_Eagle2_0.getName());
//			VP: Verify that the valid tuning DTSCS file is uploaded successfully
			Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME_2, "href").contains(AddEditProductModel.FileUpload.Attached_3_Movies_Eagle2_0.getName().replaceAll(".dtscs", "")));
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
//			10. Select â€œAttached 4 - Voiceâ€� in â€œContent Modesâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_4_Voice.getName());			
//			11. Click "Choose fille" to upload custom tuning file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached_4_Voice_Eagle2_0.getName());
//			VP: Verify that the valid tuning DTSCS file is uploaded successfully
			Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME_3, "href").contains(AddEditProductModel.FileUpload.Attached_4_Voice_Eagle2_0.getName().replaceAll(".dtscs", "")));
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
//			12. Select â€œAttached 5 â€“ Game 1â€� in â€œContent Modesâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_5_Game_1.getName());			
//			13. Click "Choose fille" to upload custom tuning file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached_5_Game_1_Eagle2_0.getName());
//			VP: Verify that the valid tuning DTSCS file is uploaded successfully
			Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME_4, "href").contains(AddEditProductModel.FileUpload.Attached_5_Game_1_Eagle2_0.getName().replaceAll(".dtscs", "")));
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
//			14. Select â€œAttached 6 â€“ Game 2â€� in â€œContent Modesâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_6_Game_2.getName());			
//			15. Click "Choose fille" to upload custom tuning file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached_6_Game_2_Eagle2_0.getName());
//			VP: Verify that the valid tuning DTSCS file is uploaded successfully
			Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME_5, "href").contains(AddEditProductModel.FileUpload.Attached_6_Game_2_Eagle2_0.getName().replaceAll(".dtscs", "")));
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
//			16. Select â€œAttached 7 â€“ Game 3â€� in â€œContent Modesâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_7_Game_3.getName());			
//			17. Click "Choose fille" to upload custom tuning file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached_7_Game_3_Eagle2_0.getName());
//			VP: Verify that the valid tuning DTSCS file is uploaded successfully
			Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME_6, "href").contains(AddEditProductModel.FileUpload.Attached_7_Game_3_Eagle2_0.getName().replaceAll(".dtscs", "")));
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
//			18. Select â€œAttached 8 â€“ Customâ€� in â€œContent Modesâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_8_Custom.getName());			
//			19. Click "Choose fille" to upload custom tuning file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached_8_Custom_Eagle2_0.getName());
//			VP: Verify that the valid tuning DTSCS file is uploaded successfully
			Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME_7, "href").contains(AddEditProductModel.FileUpload.Attached_8_Custom_Eagle2_0.getName().replaceAll(".dtscs", "")));
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
	}
	    @Test
	    public void TC650DaDEN_111(){
	    	
	    }
	    @Test
		public void  TC650DaDEN_112(){
			Reporter.log("Verify that Product Type is HPX High, the invalid route tuning file is uploaded unsuccessfully when selecting Device Headphone");
			/*
				1. Log into DTS portal as a DTS user
				2. Navigate to "Apps & Devices" page
				3. Click â€œAdd App or Deviceâ€� link
				VP: The Device Edit page is displayed
				4. Leave the Product Type panel as â€œHPX Highâ€�
				5. Select Device Headphone in â€œAudio Routeâ€� dropdown list
				6. Select Attach 1 in "Content Modes" dropdown list
				7. Click on "Select file" button in "Custom Tuning" section
				8. Upload the custom tuning DTSCS file that having route not match
				VP: Verify that â€œ! Invalid file: The Route object does not match the Audio Route selection.â€� message is displayed

			 */
			
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Products" page	
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// 4. Leave the Eagle Version panel as â€œEagle 2.0â€�
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			// 5. Leave the Product Type panel as â€œHPX Highâ€�
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
			// 6. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
			// 6. Select Attach 1 in "Content Modes" dropdown list
			//appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
			// 7. Click on "Select file" button in "Custom Tuning" section
			// 8. Upload the custom tuning DTSCS file that having route not match
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached_0_Default_Eagle2_0.getName());
			// VP: Verify that â€œ! Invalid file: The Route object does not match the Audio Route selection.â€� message is displayed
			Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS).contains(DeviceEdit.requires.Invalid_file_contents.getName()));
		}
	    @Test
	    public void TC650DaDEN_113(){
			Reporter.log("Verify that Product Type is HPX Medium, the invalid route tuning file is uploaded unsuccessfully when selecting Device Headphone");
			/*
				1. Log into DTS portal as a DTS user
				2. Navigate to "Apps & Devices" page
				3. Click â€œAdd App or Deviceâ€� link
				VP: The Device Edit page is displayed
				4. Leave the Product Type panel as â€œHPX Mediumâ€�
				5. Select Device Headphone in â€œAudio Routeâ€� dropdown list
				6. Select Attach 1 in "Content Modes" dropdown list
				7. Click on "Select file" button in "Custom Tuning" section
				8. Upload the custom tuning DTSCS file that having route not match
				VP: Verify that â€œ! Invalid file: The Route object does not match the Audio Route selection.â€� message is displayed

			 */
			
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Products" page	
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// 4. Leave the Eagle Version panel as â€œEagle 2.0â€�
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			// 5. Leave the Product Type panel as â€œHPX Mediumâ€�
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
			// 6. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
			// 6. Select Attach 1 in "Content Modes" dropdown list
			//appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
			// 7. Click on "Select file" button in "Custom Tuning" section
			// 8. Upload the custom tuning DTSCS file that having route not match
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached_0_Default_Eagle2_0.getName());
			// VP: Verify that â€œ! Invalid file: The Route object does not match the Audio Route selection.â€� message is displayed
			Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS).contains(DeviceEdit.requires.Invalid_file_contents.getName()));
		}
	    
	    @Test
	    public void TC650DaDEN_114(){
			Reporter.log("Verify that Device Eagle Version is 2.0, the custom tuning have the Eagle settings data Version 1.4 is uploaded unsuccessfully");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devicesâ€� page
			3. Click â€œAdd App & Deviceâ€� link
			4. Leave the Eagle Version panel as â€œEagle 2.0â€�
			"5. Leave the Product Type panel 
			As ''DTS:X Premium''"
			6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
			7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
			8. Upload the valid custom tuning DTSCS file which have the Eagle settings data Version 1.4
            */
			
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Products" page	
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// 4. Leave the Eagle Version panel as â€œEagle 2.0â€�
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			// 5. Leave the Product Type panel as â€œDTS:X Premiumâ€�
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
//			6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
//			7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
			//appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
//			8. Upload the valid custom tuning DTSCS file which have the Eagle settings data Version 1.4
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Default.getName());
//			VP: Verify that the custom tuning file is uploaded unsuccessfully
			Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS).contains(DeviceEdit.requires.Invalid_file_Please_upload_the_Eagle_v2_0_tuning_file.getName()));
	    }
	    
	    @Test
	    public void TC650DaDEN_115(){
			Reporter.log("Verify that Device Eagle Version is 2.0,  and information display correctly when switching to eagle version 1.4");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devicesâ€� page
			3. Click â€œAdd App & Deviceâ€� link
			4. Leave the Eagle Version panel as â€œEagle 2.0â€�
			"5. Leave the Product Type panel As ''DTS:X Premium''"
			6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
			7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
			8. Upload the valid custom tuning DTSCS file which have the Eagle settings data Version 2.0
			9. Leave the Eagle Version panel as â€œEagle 1.4â€�

            */
			
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Products" page	
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// 4. Leave the Eagle Version panel as â€œEagle 2.0â€�
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			// 5. Leave the Product Type panel as â€œDTS:X Premiumâ€�
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
//			6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
//			7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
			//appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
//			8. Upload the valid custom tuning DTSCS file which have the Eagle settings data Version 2.0
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones_Lineout_EagleV20.getName());
//			VP: Verify that the custom tuning file is uploaded successfully
			Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Over_Ear_Headphones_Lineout_EagleV20.getName().replaceAll(".dtscs", "")));
//			9. Leave the Eagle Version panel as â€œEagle 1.4â€�	
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
//			VP: Verify that the confirmation pop-up is showed up
			Assert.assertTrue(appDeviceControl.checkMessageDisplay(DeviceEdit.requires.Switch_Eagle_Version.getName()));
	    }
	    @Test
	    public void TC650DaDEN_116(){
			Reporter.log("Verify that a confirm popup is showed up when switching from  ''DTS:X Master''  to  ''DTS Audio Processing''.");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Leave the Product Type panel as ''DTS:X Master''
			5. Select Device Headphone in â€œAudio Routeâ€� dropdown list
			6. Select Over Ear Headphones in â€œContent Modesâ€� dropdown list
			7. Click on "Select file" button in "Custom Tuning" section
			8. Click "Choose fille" to upload custom tuning file
			9. Leave the Product Type panel as ''DTS Audio Processing''

            */
			
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Products" page	
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// 4. Leave the Eagle Version panel as â€œEagle 2.0â€�
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			// 5. Leave the Product Type panel as â€œDTS:X Masterâ€�
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
//			6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
//			7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
			//appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
//			8. Upload the valid custom tuning DTSCS file which have the Eagle settings data Version 2.0
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones_Lineout_EagleV20.getName());
//			VP: Verify that the custom tuning file is uploaded successfully
			Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Over_Ear_Headphones_Lineout_EagleV20.getName().replaceAll(".dtscs", "")));
//			9. Leave the Product Type panel as ''DTS Audio Processing''
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
//			VP: Verify that the confirmation popup is showed up
			Assert.assertTrue(appDeviceControl.checkMessageDisplay(DeviceEdit.requires.Switch_Product_Type.getName()));
			
	    }
	    @Test
	    public void TC650DaDEN_117(){
			Reporter.log("Verify that a confirm popup is showed up when switching from  DTS:X Premium  to  ''DTS Audio Processing''.");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Leave the Product Type panel as 'DTS:X Premium'
			5. Select Device Headphone in â€œAudio Routeâ€� dropdown list
			6. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
			7. Click on "Select file" button in "Custom Tuning" section
			8. Click "Choose fille" to upload custom tuning file
			9. Leave the Product Type panel as ''DTS Audio Processing''

            */
			
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Products" page	
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// 4. Leave the Eagle Version panel as â€œEagle 2.0â€�
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			// 5. Leave the Product Type panel as â€œDTS:X Premiumâ€�
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
//			6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
/*//			7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
//			8. Upload the valid custom tuning DTSCS file which have the Eagle settings data Version 2.0
*/			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones_Lineout_EagleV20.getName());
//			VP: Verify that the custom tuning file is uploaded successfully
			Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Over_Ear_Headphones_Lineout_EagleV20.getName().replaceAll(".dtscs", "")));
//			9. Leave the Product Type panel as ''DTS Audio Processing''
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
//			VP: Verify that the confirmation popup is showed up
			Assert.assertTrue(appDeviceControl.checkMessageDisplay(DeviceEdit.requires.Switch_Product_Type.getName()));
			
	    }
	    @Test
	    public void TC650DaDEN_118(){
			Reporter.log("Verify that  the product type names are displayed: DTS:X Ultra, DTS:X Premium, DTS Audio Processing When choosing Eagle Version 2.0");
			/*
			1. Log into DTS portal as a Partner super user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Click on Product Type combo box
			VP: Verify that product type display names:
				DTS:X Ultra
				DTS:X Premium
				DTS Audio Processing

            */
			
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Products" page	
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// 			VP: Verify that product type display names:
			//				DTS:X Ultra
			//				DTS:X Premium
			//				DTS Audio Processing
			ArrayList<String> typeList = appDeviceControl.getAllComboboxOption(DeviceEdit.PRODUCT_TYPE);
			Assert.assertTrue(ListUtil.containsAll(typeList, DeviceEdit.Product_Types.getNames()));			
	    }
	    @Test
	    public void TC650DaDEN_119(){
			Reporter.log("Verify that  the product type names are displayed: DTS:X Ultra, DTS:X Premium, DTS Audio Processing When choosing Eagle Version 1.4");
			/*
			1. Log into DTS portal as a Partner super user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Leave the Eagle Version combo box as “Eagle 1.4”
			5. Click on Product Type combo box
			VP: Verify that product type display names:
				HPX High
				HPX Medium
				HPX Low
            */
			
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Products" page	
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// 4. Leave the Eagle Version combo box as “Eagle 1.4”
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
			// 5. Click on Product Type combo box
			// 		VP: Verify that product type display names:
			//				DTS:X Ultra
			//				DTS:X Premium
			//				DTS Audio Processing
			ArrayList<String> typeList = appDeviceControl.getAllComboboxOption(DeviceEdit.PRODUCT_TYPE);
			Assert.assertTrue(ListUtil.containsAll(typeList, DeviceEdit.Product_Types_Eagle_1_4.getNames()));			
	    }
	    @Test
	    public void TC650DaDEN_120(){
			Reporter.log("Verify that the Headphone:X Licensing box not appear when creation new apps & devices");
			/*
			1. Log into DTS portal as a Partner super user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			VP: verify that the Headphone:X Licensing box NOT appear
            */
			
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Products" page	
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// VP: verify that the Headphone:X Licensing box NOT appear
			Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.INIT_KEY_NAME));
	    }
	    @Test
	    public void TC650DaDEN_121(){
			Reporter.log("Verify that the Multi channel room sections box not appear when creation new apps & devices");
			/*
			1. Log into DTS portal as a Partner super user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			VP: Multi channel room sections box NOT appear
            */
			
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Products" page	
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// VP: Multi channel room sections box NOT appear
			Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.CHECK_BOX_MCROOM));
	    }
	    @Test
	    public void TC650DaDEN_122(){
			Reporter.log("Verify that the Multi channel room sections box not appear when choosing product type “DTS:X Ultra");
			/*
			1. Log into DTS portal as a Partner super user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Leave on Product Type combo box as ''DTS:X Ultra''
			VP: Multi channel room sections box NOT appear
            */
			
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Products" page	
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			// 4. Leave on Product Type combo box as ''DTS:X Ultra''
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// VP: Multi channel room sections box NOT appear
			Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.CHECK_BOX_MCROOM));
	    }
	    @Test
	    public void TC650DaDEN_123(){
			Reporter.log("Verify that the Multi channel room section appear when choosing eagle verion 1.4");
			/*
			1. Log into DTS portal as a Partner super user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Leave the Eagle Version combo box as “Eagle 1.4”
			VP: Verify Multi channel room displayed
            */
			// this testcase already edited. When choosing eagle 1.4, the Check box McRoom didnot show up 
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Products" page	
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// 4. Leave the Eagle Version combo box as “Eagle 1.4”
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
			// VP: Verify Multi channel room displayed
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.CHECK_BOX_MCROOM));
	    }
	    @Test
	    public void TC650DaDEN_124(){
			Reporter.log("Verify that the Headphone:X Licensing appear when choosing eagle verion 1.4");
			/*
			1. Log into DTS portal as a Partner super user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Leave the Eagle Version combo box as “Eagle 1.4”
			VP: Verify Headphone:X Licensing displayed
            */
			
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Products" page	
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// 4. Leave the Eagle Version combo box as “Eagle 1.4”
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
			// VP: Verify Headphone:X Licensing displayed
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.INIT_KEY_NAME));
	    }
	    @Test
	    public void TC650DaDEN_125(){
			Reporter.log("Verify that the Multi channel room sections box and Headphone:X Licensing disappear when choosing eagle verion 1.4 to 2.0");
			/*
			1. Log into DTS portal as a Partner super user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Leave the Eagle Version combo box as “Eagle 1.4”
			VP: Verify Multi channel room and Headphone:X Licensing displayed
			5. Leave the Eagle Version combo box as “Eagle 2.0”
			VP: Verify Multi channel room and Headphone:X Licensing NOT displayed
            */
			
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Products" page	
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// 4. Leave the Eagle Version combo box as “Eagle 1.4”
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
			// VP: Verify Multi channel room and Headphone:X Licensing displayed
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.INIT_KEY_NAME));
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.CHECK_BOX_MCROOM));
			// 5. Leave the Eagle Version combo box as “Eagle 2.0”
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			// VP: Verify Multi channel room and Headphone:X Licensing NOT displayed
			Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.INIT_KEY_NAME));
			Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.CHECK_BOX_MCROOM));
	    }
	    
		
		/*
		 * Verify that Product Type is HPX High, the valid route tuning file is uploaded successfully when selecting Device Headphone route
		 */
		@Test
		public void TC650DaDEN_127(){
			Reporter.log("Verify uploading Attached 0 - Default Mode tuning successfully");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type panel as “DTS:X Ultra”
			5. Select “Device Headphone” in Audio Route
			6. Select “Attached 0 - Default ” in “Content Modes” dropdown list
			7. Click "Choose fille" to upload vaild custom tuning file
			VP: Verify that the valid tuning DTSCS file is uploaded successfully
			VP: Verify that “Attached 0 - Default ” will disappear in “Audio Route” dropdown list
			8. Leave the Product Type panel as “DTS:X Premium”
			9. Select “Device Headphone” in Audio Route
			10. Click "Choose fille" to upload vaild custom tuning file
			VP: Verify that the valid tuning DTSCS file is uploaded successfully
			VP: Verify that “Attached 0 - Default ”  will disappear in “Audio Route” dropdown list
			11. Leave the Product Type panel as “DTS Audio Processing”
			12. Select “Device Headphone” in Audio Route
			13. Select “Attached 0 - Default ” in “Content Modes” dropdown list
			14. Click "Choose fille" to upload vaild custom tuning file
			VP: Verify that the valid tuning DTSCS file is uploaded successfully
			VP: Verify that “Attached 0 - Default ” will disappear in “Audio Route” dropdown list


			 */
			
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Products" page	
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// 4. Leave the Eagle Version panel as â€œEagle 1.4â€�
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
			// 5. Leave the Product Type panel as â€œHPX Highâ€�
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
			// 6. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			// 6.a Select “Attached 0 - Default ” in “Content Modes” dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Content_Modes_Hig.Attached_0_Default_Mode.getName());
			// 7. Click on "Select file" button in "Custom Tuning" section
			//VP: Verify that the valid tuning DTSCS file is uploaded successfully
			//VP: Verify that “Attached 0 - Default ” will disappear in “Audio Route” dropdown list
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Default.getName());
			Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Default.getName().replaceAll(".dtscs", "")));
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
			// Remove attach “Attached 0 - Default ” uploaded on High product type
			appDeviceControl.click(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
			appDeviceControl.click(PageHome.BTN_CONFIRMATION_DANGER);
			//8. Leave the Product Type panel as “DTS:X Premium”
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
			//9. Select “Device Headphone” in Audio Route
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			// 9.a Select “Attached 0 - Default ” in “Content Modes” dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Content_Modes_Hig.Attached_0_Default_Mode.getName());
			//10. Click "Choose fille" to upload vaild custom tuning file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Default.getName());
			//VP: Verify that the valid tuning DTSCS file is uploaded successfully
			//VP: Verify that “Attached 0 - Default ”  will disappear in “Audio Route” dropdown list
			Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Default.getName().replaceAll(".dtscs", "")));
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
			//11. Leave the Product Type panel as “DTS Audio Processing”
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
			appDeviceControl.click(DeviceEdit.OK_BUTTON);
			//12. Select “Device Headphone” in Audio Route
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			///13. Select “Default Mode ” in “Content Modes” dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Content_Modes.Mode_Default.getType());
			//14. Click "Choose fille" to upload vaild custom tuning file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Default.getName());
			//VP: Verify that the valid tuning DTSCS file is uploaded successfully
			//VP: Verify that “Default Mode”  will disappear in “Audio Route” dropdown list
			Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Default.getName().replaceAll(".dtscs", "")));
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		}
		@Test
		public void TC650DaDEN_128(){
			Reporter.log("Verify uploading Attached 0 - Default Mode tuning unsuccessfully");
			/*
				1. Log into DTS portal as a DTS user
				2. Navigate to "Apps & Devices" page
				3. Click â€œAdd App or Deviceâ€� link
				VP: The Device Edit page is displayed
				4. Leave the Product Type panel as â€œHPX Highâ€�
				5. Select Device Headphone in â€œAudio Routeâ€� dropdown list
				5.a Select Attached 0 - Default Mode in â€œContent Modesâ€� dropdown list
				6. Click on "Select file" button in "Custom Tuning" section
				7. Upload the custom tuning DTSCS file that having route not match
				VP: Verify that â€œ! Invalid file: The Route object does not match the Audio Route selection.â€� message is displayed

			 */
			
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Products" page	
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// 4. Leave the Eagle Version panel as â€œEagle 1.4â€�
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
			// 5. Leave the Product Type panel as â€œHPX Highâ€�
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_High.getType());
			// 6. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			// 6.a Select Attached 0 - Default Mode  in â€œContent Modesâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Content_Modes_Hig.Attached_0_Default_Mode.getName());
			// 7. Click on "Select file" button in "Custom Tuning" section
			// 8. Upload the custom tuning DTSCS file that having route not match
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Earbuds_Lineout.getName());
			// VP: Verify that â€œ! Invalid file: The Route object does not match the Audio Route selection.â€� message is displayed
			Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS).contains(DeviceEdit.requires.Invalid_file_contents.getName()));
		}
		@Test
		public void TC650DaDEN_129(){
			Reporter.log("Verify that Device Eagle Version is 1.4,  the custom tuning have the Eagle settings data Version 2.0 is uploaded unsuccessfully");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devices” page
			3. Click “Add App & Device” link
			4. Leave the Eagle Version panel as “Eagle 2.0”
			"5. Leave the Product Type panel 
			As ''DTS:X Premium''"
			6. Select Device Headphone in “Audio Route” dropdown list
			7. Select “Attached 0” in “Content Modes” dropdown list
			8. Upload the valid custom tuning DTSCS file which have the Eagle settings data Version 1.4
			VP: Verify that the custom tuning file is uploaded unsuccessfully

			 */
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Products" page	
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// 4. Leave the Eagle Version panel as â€œEagle 1.4â€�
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
			// 5. Leave the Product Type panel as â€œHPX Highâ€�
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
			// 6. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			// 6.a Select Attached 2 - Mode 1 in â€œContent Modesâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Content_Modes_Hig.Attached_0_Default_Mode.getName());
			// 7. Click on "Select file" button in "Custom Tuning" section
			// 8. Upload the custom tuning DTSCS file that having route not match
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached_0_Default_Eagle2_0.getName());
			// VP: Verify that â€œ! Invalid file: The Route object does not match the Audio Route selection.â€� message is displayed
			Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS).contains(DeviceEdit.requires.Invalid_file_Please_upload_the_Eagle_v1_1_tuning_file.getName()));			
		}
		@Test
		public void TC650DaDEN_130(){
			Reporter.log("Verify that Device Eagle Version is 2.0,  the custom tuning have the Eagle settings data Version 1.4 is uploaded unsuccessfully");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devices” page
			3. Click “Add App & Device” link
			4. Leave the Eagle Version panel as “Eagle 2.0”
			"5. Leave the Product Type panel 
			As ''DTS:X Premium''"
			6. Select Device Headphone in “Audio Route” dropdown list
			7. Select “Attached 0” in “Content Modes” dropdown list
			8. Upload the valid custom tuning DTSCS file which have the Eagle settings data Version 1.4
			VP: Verify that the custom tuning file is uploaded unsuccessfully

			 */
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Products" page	
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// 4. Leave the Eagle Version panel as â€œEagle 1.4â€�
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			// 5. Leave the Product Type panel as â€œHPX Highâ€�
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
			// 6. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			// 6.a Select Attached 2 - Mode 1 in â€œContent Modesâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Content_Modes_Hig.Attached_0_Default_Mode.getName());
			// 7. Click on "Select file" button in "Custom Tuning" section
			// 8. Upload the custom tuning DTSCS file that having route not match
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Attach2.getName());
			// VP: Verify that â€œ! Invalid file: The Route object does not match the Audio Route selection.â€� message is displayed
			Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS).contains(DeviceEdit.requires.Invalid_file_Please_upload_the_Eagle_v1_1_tuning_file.getName()));			
		}
		@Test
		public void TC650DaDEN_131() throws InterruptedException{
			Reporter.log("Verify that Audio Route dropdown is displayed correctly when changing from Low Product to Medium product and otherwise");
			/*
			 * Note that: this testcase also cover 650DaDEN_132
				1. Log into DTS portal as a DTS user
				2. Navigate to "Apps & Devices" page
				3. Click â€œAdd App or Deviceâ€� link
				4. Leave the Eagle Version panel as â€œEagle 1.4â€�
				5. Leave the Product Type panel as DTS Audio Processing
				6. Select â€œDevice Headphoneâ€� in Audio Route
				7. Select Default Mode Mode in Content Modesâ€� dropdown list
				8. Click "Choose fille" to upload Device Headphone tuning file
				9. Change the filter in Product Type panel to ''DTS:X Master''
				VP: Verify that the confirmation popup is showed up
				10. Click â€œChangeâ€� button
				VP: Above Device Headphone is removed in Audio Route section
				11. Select Device Headphone in â€œAudio Routeâ€� dropdown list
				11.a Select Attached 0 - Default Mode in â€œContent Modesâ€� dropdown list
				12. Click "Choose fille" to upload Device Headphone tuning file
				13. Change the filter in Product Type panel to ''DTS:X Premium''
				VP: Device Headphone isn't removed in Audio Route section
				14. Change the filter in Product Type panel to â€œDTS Audio Processingâ€�
				VP: Verify that the confirmation popup is showed up
				15. Click â€œChangeâ€� button
				VP: Device Headphone is removed in Audio Route section
			 */
			
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Products" page	
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			// 4. Leave the Eagle Version panel as â€œEagle 1.4â€�
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
			// 5. Leave the Product Type panel as â€œHPX Lowâ€�
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_Low.getType());
			// 6. Select â€œDevice Headphoneâ€� route in â€œAudio Routeâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			// 7. Select Default Mode in â€œContent Modesâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_1.getType());
			// 8. Click "Choose fille" to upload Device Headphone tuning file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Mode1.getName());
			Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Mode1.getName().replaceAll(".dtscs", "")));
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
			// 9. Change the filter in Product Type panel to â€œHPX Highâ€�
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_High.getType());
			// VP: Verify that the confirmation popup is showed up
			System.out.println(appDeviceControl.getTextByXpath(DeviceEdit.SWITCH_PRODUCT_TYPE));
			Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.SWITCH_PRODUCT_TYPE).contains(DeviceEdit.requires.Switch_Product_Type.getName()));
			// 10. Click â€œOKâ€� button
			appDeviceControl.click(DeviceEdit.OK_BUTTON);
			// VP: Above Device Headphone is removed in Audio Route section
			Assert.assertFalse(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Mode1.getName().replaceAll(".dtscs", "")));
			// 11. Select Device Headphone in â€œAudio Routeâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			//11.a Select Attached 0 - Default Mode in â€œContent Modesâ€� dropdown list
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_0_Default_Mode.getName());
			// 12. Click "Choose fille" to upload Device Headphone tuning file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Default.getName());
			Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Default.getName().replaceAll(".dtscs", "")));
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
			// 13. Change the filter in Product Type panel to â€œHPX Mediumâ€�
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_Medium.getType());
			// VP: Device Headphone isn't removed in Audio Route section
			Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Default.getName().replaceAll(".dtscs", "")));
			// 14. Change the filter in Product Type panel to â€œHPX Lowâ€�
			appDeviceControl.selectOptionByName(DeviceEdit.TYPE, DeviceEdit.types[1]);
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_Low.getType());
			Thread.sleep(1000);
			// VP: Verify that the confirmation popup is showed up
			Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.SWITCH_PRODUCT_TYPE).contains(DeviceEdit.requires.Switch_Product_Type.getName()));
			// 15. Click â€œOKâ€� button
			appDeviceControl.click(DeviceEdit.OK_BUTTON);
			// VP: Device Headphone is removed in Audio Route section
			Assert.assertFalse(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Default.getName().replaceAll(".dtscs", "")));
		}
		@Test
		public void TC650DaDEN_133(){
			Reporter.log("Verify that portal will remove Device Tuning section ( GPEQ & CTC )");
			/*
			 * 1. Log into DTS portal as a DTS user
				2. Navigate to "Apps & Devices" page
				3. Click “Add App or Device” link
				VP: Portal will remove Device Tuning section ( GPEQ & CTC )
				4. Fill valid value into all required fields
				5.  Click “Save” link
				VP: Portal will remove Device Tuning section ( GPEQ & CTC )

			 */
			
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Apps & Devices" page	
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// 4. Leave the Eagle Version panel as â€œEagle 2.0â€�
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			//VP: Portal will remove Device Tuning section ( GPEQ & CTC )
			Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.DEVICE_TUNING_SECTION_LABEL));
			Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.DEVICE_TUNING));
			Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
			appDevice.remove("save");
			appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
			appDeviceControl.click(DeviceEdit.NO_IMAGES);
			appDeviceControl.click(DeviceEdit.SAVE);
			Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.DEVICE_TUNING_SECTION_LABEL));
			Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.DEVICE_TUNING));
			
		}
		@Test
		public void TC650DaDEN_134(){
			Reporter.log("Verify that portal still supports uploading Standard Accessories ( Car Audio, Earbuds, Earpiece, External Speakers, and Over-Ear Headphones ) associated Line out, Bluetooth & USB as it currently does.");
		/*
		 * 1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click on an  earlier versions  device and then click on 'Update infor" link
			4. Leave the Product Type combo box as ''DTS:X Ultra'
			5. Select Line Out  in "Audio Route" drop down list
			6. Select any type headphone in "Standard Accessories" drop down list
			7.Click on "Choose file" button in "Custom Tuning" section
			8.Upload the valid custom tuning DTSCS
			9. Select Bluetooth in "Audio Route" drop down list
			10. Select any type headphone in "Standard Accessories" drop down list
			11.Click on "Choose file" button in "Custom Tuning" section
			11a.Upload the valid custom tuning DTSCS
			12. Select USB in "Audio Route" drop down list
			13. Select any type headphone in "Standard Accessories" drop down list
			14.Click on "Choose file" button in "Custom Tuning" section
			15.Upload the valid custom tuning DTSCS
*/			
		//1. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Click on "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
		//4. Leave the Product Type combo box as ''DTS:X Ultra'
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
		//5. Select Line Out  in "Audio Route" dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Line_out0.getName()); // "Lite out"
		//6. Select any type headphone in "Standard Accessories" dropdown list	
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName()); //"Over-Ear Headphones"
		//7. Click on "Choose file" button in "Custom Tuning" section	
		//8. Upload the custom tuning DTSCS file that having route not match
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Over_Ear_Headphones_Lineout.getName());
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Over_Ear_Headphones_Lineout.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		appDeviceControl.click(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON); 
		appDeviceControl.selectConfirmationDialogOption(Delete);
		//9. Select Bluetooth  in "Audio Route" dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Bluetooth0.getName()); 
		//10. Select any type headphone in "Standard Accessories" dropdown list	
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName()); //"Over-Ear Headphones"
		//12. Click on "Choose file" button in "Custom Tuning" section	
		//11a. Upload the custom tuning DTSCS file that having route not match
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Over_Ear_Headphones_Bluetooth.getName());
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Over_Ear_Headphones_Bluetooth.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		appDeviceControl.click(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON); 
		appDeviceControl.selectConfirmationDialogOption(Delete);
		//12. Select USB  in "Audio Route" dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.USB0.getName()); 
		//13. Select any type headphone in "Standard Accessories" dropdown list	
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName()); //"Over-Ear Headphones"
		//14. Click on "Choose file" button in "Custom Tuning" section	
		//15. Upload the custom tuning DTSCS file that having route not match
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Over_Ear_Headphones_USB.getName());
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Over_Ear_Headphones_USB.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		}
		@Test
		public void TC650DaDEN_137(){
			Reporter.log("Verify uploading single Default Audio Route unsuccessfully with invalid format file");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Ultra''
			5. Fill valid value into all required fields
			7. Upload a  invalid single Default Audio Route
			VP: There is an error message is displayed.


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Ultra''
//			5. Fill valid value into all required fields
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
//			7. Upload a  invalid single Default Audio Route
			appDeviceControl.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, AddEditProductModel.FileUpload.Attached_0_Default_Eagle2_0.getName());
//			VP: There is an error message is displayed.
			Assert.assertTrue(appDeviceControl.checkMessageDisplay(DeviceEdit.requires.Invalid_file_Please_upload_a_exptune_tuning_file.getName()));
			
		}
		@Test
		public void TC650DaDEN_138(){
			Reporter.log("Verify uploading single Default Audio Route successfully with valid format file");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Ultra''
			5. Fill valid value into all required fields
			7. Upload a  valid single Default Audio Route
			VP: The tuning can be able to upload successfully.


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Ultra''
//			5. Fill valid value into all required fields
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
//			7. Upload a  invalid single Default Audio Route
			appDeviceControl.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, AddEditProductModel.FileUpload.Default_Audio_Tuning_Exptune_Files.getName());
//			VP: The tuning can be able to upload successfully.
			Assert.assertFalse(appDeviceControl.checkMessageDisplay(DeviceEdit.requires.Invalid_file_Please_upload_a_exptune_tuning_file.getName()));
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.UPLOADED_DEFAULT_AUDIO_NAME));
		}
		@Test
		public void TC650DaDEN_139(){
			Reporter.log("Verify uploading single Over ear headphones tuning unsuccessfully with invalid format file");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Ultra''
			5. Fill valid value into all required fields
			6. Select Over ear heaphone at Audio Route combo box
			7. Upload a  invalid single Default Audio Route
			VP: There is an error message is displayed.


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Ultra''
//			5. Fill valid value into all required fields
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
//			6. Select Over ear heaphone at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
//			7. Upload a  invalid single Default Audio Route
			appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Default_Audio_Tuning_Exptune_Files.getName());
//			VP: There is an error message is displayed.
			Assert.assertTrue(appDeviceControl.checkMessageDisplay(DeviceEdit.requires.Invalid_file_type.getName()));
			
		}
		@Test
		public void TC650DaDEN_140(){
			Reporter.log("Verify uploading single Over ear headphones tuning successfully with valid format file");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Ultra''
			5. Fill valid value into all required fields
			6. Select Over ear heaphone at Audio Route combo box
			7. Upload a valid single Default Audio Route
			VP: The tuning can be able to upload successfully.


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Ultra''
//			5. Fill valid value into all required fields
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
//			6. Select Over ear heaphone at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
//			7. Upload a  invalid single Default Audio Route
			appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_LineOut_BT_USB_ver2.getName());
//			VP: The tuning can be able to upload successfully.
			Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME), DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
			
		}
		@Test
		public void TC650DaDEN_141(){
			Reporter.log("Verify uploading single Earbuds headphones tuning unsuccessfully with invalid format file");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Ultra''
			5. Fill valid value into all required fields
			6. Select Earbuds at Audio Route combo box
			7. Upload a  invalid single Default Audio Route
			VP: There is an error message is displayed.


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Ultra''
//			5. Fill valid value into all required fields
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
//			6. Select Earbuds at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Earbuds.getName());
//			7. Upload a  invalid single Default Audio Route
			appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Default_Audio_Tuning_Exptune_Files.getName());
//			VP: There is an error message is displayed.
			Assert.assertTrue(appDeviceControl.checkMessageDisplay(DeviceEdit.requires.Invalid_file_type.getName()));
			
		}
		@Test
		public void TC650DaDEN_142(){
			Reporter.log("Verify uploading single Earbuds tuning successfully with valid format file");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Ultra''
			5. Fill valid value into all required fields
			6. Select Earbuds at Audio Route combo box
			7. Upload a valid single Default Audio Route
			VP: The tuning can be able to upload successfully.


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Ultra''
//			5. Fill valid value into all required fields
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
//			6. Select Earbuds at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Earbuds.getName());
//			7. Upload a  invalid single Default Audio Route
			appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_LineOut_BT_USB_ver2.getName());
//			VP: The tuning can be able to upload successfully.
			Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME), DeviceEdit.Standard_Accessories.Earbuds.getName());
			
		}
		@Test
		public void TC650DaDEN_143(){
			Reporter.log("Verify uploading single Ear-piece headphones tuning unsuccessfully with invalid format file");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Ultra''
			5. Fill valid value into all required fields
			6. Select Ear-piece at Audio Route combo box
			7. Upload a  invalid single Default Audio Route
			VP: There is an error message is displayed.


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Ultra''
//			5. Fill valid value into all required fields
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
//			6. Select Ear-piece at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Ear_piece.getName());
//			7. Upload a  invalid single Default Audio Route
			appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Default_Audio_Tuning_Exptune_Files.getName());
//			VP: There is an error message is displayed.
			Assert.assertTrue(appDeviceControl.checkMessageDisplay(DeviceEdit.requires.Invalid_file_type.getName()));
			
		}
		@Test
		public void TC650DaDEN_144(){
			Reporter.log("Verify uploading single Ear-piece tuning successfully with valid format file");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Ultra''
			5. Fill valid value into all required fields
			6. Select Ear-piece at Audio Route combo box
			7. Upload a valid single Default Audio Route
			VP: The tuning can be able to upload successfully.


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Ultra''
//			5. Fill valid value into all required fields
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
//			6. Select Earbuds at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Ear_piece.getName());
//			7. Upload a  invalid single Default Audio Route
			appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_LineOut_BT_USB_ver2.getName());
//			VP: The tuning can be able to upload successfully.
			Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME), DeviceEdit.Standard_Accessories.Ear_piece.getName());
			
		}
		@Test
		public void TC650DaDEN_145(){
			Reporter.log("Verify uploading single External speaker  tuning unsuccessfully with invalid format file");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Ultra''
			5. Fill valid value into all required fields
			6. Select  External speaker at Audio Route combo box
			7. Upload a  invalid single Default Audio Route
			VP: There is an error message is displayed.


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Ultra''
//			5. Fill valid value into all required fields
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
//			6. Select  External speaker at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.External_Speakers.getName());
//			7. Upload a  invalid single Default Audio Route
			appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Default_Audio_Tuning_Exptune_Files.getName());
//			VP: There is an error message is displayed.
			Assert.assertTrue(appDeviceControl.checkMessageDisplay(DeviceEdit.requires.Invalid_file_type.getName()));
			
		}
		@Test
		public void TC650DaDEN_146(){
			Reporter.log("Verify uploading single Earbuds tuning successfully with valid format file");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Ultra''
			5. Fill valid value into all required fields
			6. Select  External speaker at Audio Route combo box
			7. Upload a valid single Default Audio Route
			VP: The tuning can be able to upload successfully.


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Ultra''
//			5. Fill valid value into all required fields
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
//			6. Select  External speaker at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.External_Speakers.getName());
//			7. Upload a  invalid single Default Audio Route
			appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_LineOut_BT_USB_ver2.getName());
//			VP: The tuning can be able to upload successfully.
			Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME), DeviceEdit.Standard_Accessories.External_Speakers.getName());
			
		}
		@Test
		public void TC650DaDEN_147(){
			Reporter.log("Verify uploading single Car-Audio headphones tuning unsuccessfully with invalid format file");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Ultra''
			5. Fill valid value into all required fields
			6. Select Car-Audio at Audio Route combo box
			7. Upload a  invalid single Default Audio Route
			VP: There is an error message is displayed.


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Ultra''
//			5. Fill valid value into all required fields
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
//			6. Select Car-Audio at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Car_Audio.getName());
//			7. Upload a  invalid single Default Audio Route
			appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Default_Audio_Tuning_Exptune_Files.getName());
//			VP: There is an error message is displayed.
			Assert.assertTrue(appDeviceControl.checkMessageDisplay(DeviceEdit.requires.Invalid_file_type.getName()));
			
		}
		@Test
		public void TC650DaDEN_148(){
			Reporter.log("Verify uploading single Car-Audio tuning successfully with valid format file");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Ultra''
			5. Fill valid value into all required fields
			6. Select Car-Audio at Audio Route combo box
			7. Upload a valid single Default Audio Route
			VP: The tuning can be able to upload successfully.


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Ultra''
//			5. Fill valid value into all required fields
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
//			6. Select Car-Audio at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Car_Audio.getName());
//			7. Upload a  invalid single Default Audio Route
			appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_LineOut_BT_USB_ver2.getName());
//			VP: The tuning can be able to upload successfully.
			Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME), DeviceEdit.Standard_Accessories.Car_Audio.getName());
			
		}
		@Test
		public void TC650DaDEN_149(){
			Reporter.log("Verify when single audio can be delete after uploading");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Ultra''
			5. Fill valid value into all required fields
			6. Select Car-Audio at Audio Route combo box
			7. Upload a valid single Default Audio Route
			VP: The tuning can be able to upload successfully.
			8. Navigate Audio Routes table
			VP:  Delete icon displayed with the tuning has just been upload
			9. Click to ''delete'' icon
			VP: Pop-up is displayed with ''cancel'' and ''delete'' button


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Ultra''
//			5. Fill valid value into all required fields
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
//			6. Select Car-Audio at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Car_Audio.getName());
//			7. Upload a  invalid single Default Audio Route
			appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_LineOut_BT_USB_ver2.getName());
//			VP: The tuning can be able to upload successfully.
			Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME), DeviceEdit.Standard_Accessories.Car_Audio.getName());
//			8. Navigate Audio Routes table
//			VP:  Delete icon displayed with the tuning has just been upload
//			9. Click to ''delete'' icon
			appDeviceControl.click(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
			appDeviceControl.waitForElementClickable(PageHome.BTN_CONFIRMATION_DANGER);
			/*
			 * VP: Pop-up is displayed with ''cancel'' and ''delete'' button
			 */
			Assert.assertEquals(appDeviceControl.getTextByXpath(PageHome.BTN_CONFIRMATION_DANGER), "Delete");
			Assert.assertEquals(appDeviceControl.getTextByXpath(PageHome.BTN_CONFIRMATION_CANCEL), "Cancel");
			//7. Click "Delete" button in delete confirmation dialog
			appDeviceControl.click(PageHome.BTN_CONFIRMATION_DANGER);
			
		}
		@Test
		public void TC650DaDEN_150(){
			Reporter.log("Verify uploading single file include 2 tuning bluetooth and usb successfully");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Ultra''
			5. Fill valid value into all required fields
			6. Select Car-Audio at Audio Route combo box
			7. Upload a valid tuning include route 2 tuning bluetooth and usb on the file
			VP: The tuning can be able to upload successfully.


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Ultra''
//			5. Fill valid value into all required fields
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
//			6. Select Car-Audio at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Car_Audio.getName());
//			7. Upload a valid tuning include route 2 tuning bluetooth and usb on the file
			appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_BT_USB_ver2.getName());
//			VP: The tuning can be able to upload successfully.
			Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME), DeviceEdit.Standard_Accessories.Car_Audio.getName());
			
		}
		@Test
		public void TC650DaDEN_151(){
			Reporter.log("Verify uploading single file include 2 tuning lineout and usb successfully");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Ultra''
			5. Fill valid value into all required fields
			6. Select Car-Audio at Audio Route combo box
			7. Upload a valid tuning include route 2 tuning lineout and usb on the file
			VP: The tuning can be able to upload successfully.


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Ultra''
//			5. Fill valid value into all required fields
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
//			6. Select Car-Audio at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Car_Audio.getName());
//			7. Upload a valid tuning include route 2 tuning lineout and usb on the file
			appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_LineOut_USB_ver2.getName());
//			VP: The tuning can be able to upload successfully.
			Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME), DeviceEdit.Standard_Accessories.Car_Audio.getName());
			
		}
		@Test
		public void TC650DaDEN_152(){
			Reporter.log("Verify uploading single file include 2 tuning lineout and bluetooth successfully");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Ultra''
			5. Fill valid value into all required fields
			6. Select Car-Audio at Audio Route combo box
			7. Upload a valid tuning include route 2 tuning lineout and bluetooth on the file
			VP: The tuning can be able to upload successfully.


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Ultra''
//			5. Fill valid value into all required fields
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
//			6. Select Car-Audio at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Car_Audio.getName());
//			7. Upload a valid tuning include route 2 tuning lineout and bluetooth on the file
			appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_LineOut_BT_ver2.getName());
//			VP: The tuning can be able to upload successfully.
			Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME), DeviceEdit.Standard_Accessories.Car_Audio.getName());
			
		}
		@Test
		public void TC650DaDEN_153(){
			Reporter.log("Verify when switching from eagle 2.0 to eagle 1.4 the UI change to old version");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Eagle Version combo box as 'Eagle 1.4''
			VP: Do not have the option ''Switch to uploading single DTSCS file'' and have Audio route include:
			Line-out0 - Line Out
			Bluetooth0 – Bluetooth
			USB0 – USB
			Attached0 - Internal Speakers (Default)
			Attached1 - Internal Speakers (Off)
			Attached2 - Internal Speakers (mode 1)
			Attached3 - Internal Speakers (mode 2)
			Attached4 - Internal Speakers (mode 3)
			Attached5 - Internal Speakers (mode 4)
			Attached6 - Internal Speakers (mode 5)
			Attached7 - Internal Speakers (mode 6)
			Attached8 - Internal Speakers (mode 7)
			Combo0 - Dual Audio
			Device Headphone Settings

			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Eagle Version combo box as 'Eagle 1.4''
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
			/*VP: Do not have the option ''Switch to uploading single DTSCS file'' and have Audio route include:
				Line-out0 - Line Out
				Bluetooth0 – Bluetooth
				USB0 – USB
				Attached0 - Internal Speakers (Default)
				Attached1 - Internal Speakers (Off)
				Attached2 - Internal Speakers (mode 1)
				Attached3 - Internal Speakers (mode 2)
				Attached4 - Internal Speakers (mode 3)
				Attached5 - Internal Speakers (mode 4)
				Attached6 - Internal Speakers (mode 5)
				Attached7 - Internal Speakers (mode 6)
				Attached8 - Internal Speakers (mode 7)
				Combo0 - Dual Audio
				Device Headphone Settings*/
			Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE),DeviceEdit.routes.getNames()));
			
		}
		@Test
		public void TC650DaDEN_154(){
			Reporter.log("Verify when switching from eagle 2.0 to eagle 1.4 the UI change to old version");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Eagle Version combo box as 'Eagle 1.4''
			VP: Do not have the option ''Switch to uploading single DTSCS file'' and have Audio route include:
			Line-out0 - Line Out
			Bluetooth0 – Bluetooth
			USB0 – USB
			Attached0 - Internal Speakers (Default)
			Attached1 - Internal Speakers (Off)
			Attached2 - Internal Speakers (mode 1)
			Attached3 - Internal Speakers (mode 2)
			Attached4 - Internal Speakers (mode 3)
			Attached5 - Internal Speakers (mode 4)
			Attached6 - Internal Speakers (mode 5)
			Attached7 - Internal Speakers (mode 6)
			Attached8 - Internal Speakers (mode 7)
			Combo0 - Dual Audio
			Device Headphone Settings
			5. Leave the Eagle Version combo box as 'Eagle 2.0'' again
			There is an upload of this single DTSCS file ( Default Audio Route module ).
			VP: Standard accessories include :
			+  - Select -
			+ Over-ear headphones
			+ Earbuds 
			+ Ear-piece
			+ External speaker
			+ Car -Audio

			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Eagle Version combo box as 'Eagle 1.4''
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
			/*VP: Do not have the option ''Switch to uploading single DTSCS file'' and have Audio route include:
				Line-out0 - Line Out
				Bluetooth0 – Bluetooth
				USB0 – USB
				Attached0 - Internal Speakers (Default)
				Attached1 - Internal Speakers (Off)
				Attached2 - Internal Speakers (mode 1)
				Attached3 - Internal Speakers (mode 2)
				Attached4 - Internal Speakers (mode 3)
				Attached5 - Internal Speakers (mode 4)
				Attached6 - Internal Speakers (mode 5)
				Attached7 - Internal Speakers (mode 6)
				Attached8 - Internal Speakers (mode 7)
				Combo0 - Dual Audio
				Device Headphone Settings*/
			Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE),DeviceEdit.routes.getNames()));
//			5. Leave the Eagle Version combo box as 'Eagle 2.0'' again
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
/*			VP: Standard accessories include :
			+  - Select -
			+ Over-ear headphones
			+ Earbuds 
			+ Ear-piece
			+ External speaker
			+ Car -Audio*/
			Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE),DeviceEdit.Standard_Accessories.getNames()));
			
		}
		@Test
		public void TC650DaDEN_155(){
			Reporter.log("Verify warning box will appear when uploading tuning without choosing product type");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Select Car-Audio at Audio Route combo box
			VP: The warning box content ''Please select the Product Type before selecting the Audio Route'' appear


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Select Car-Audio at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Car_Audio.getName());
//			VP: The warning box content ''Please select the Product Type before selecting the Audio Route'' appear.
			//Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceEdit.SELECT_PRODUCT_TYPE_BEFORE_UPLOAD).contains(DeviceEdit.requires.Select_Product_Type_Before_Upload.getName()));
			Assert.assertTrue(appDeviceControl.checkMessageDisplay(DeviceEdit.requires.Select_Product_Type_Before_Upload.getName()));
		} 
		@Test
		public void TC650DaDEN_155a(){
			Reporter.log("Verify tuning of DTS Audio Processing will show up when switching from eagle 2.0 new design to eagle 1.4");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS Audio Processing''
			5. Leave Eagle Version combo box to ''Eagle 1.4''
			6. Navigate Audio route
			VP: custom tuning box will show up when switching from Eagle 2.0 to eagle 1.4


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS Audio Processing''
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
//			5. Leave Eagle Version combo box to ''Eagle 1.4''
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
//			6. Navigate Audio route
//			VP: custom tuning box will show up when switching from Eagle 2.0 to eagle 1.4
			Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE),DeviceEdit.routes_low.getNames()));
		}
		@Test
		public void TC650DaDEN_156(){
			Reporter.log("Check the eagle version 1.4 Verify the UI on DTS:X Premium of Audio route custom tuning include those option:Line-out0 – Line Out, Bluetooth0 - Bluetooth,USB0 – USBAttached0: Default,Attached1: Bypass/off,Attached2: Music,Attached3: Movies,Attached4: Game,Device Headphone Settings");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Premium''
			5. Leave Eagle Version combo box to ''Eagle 1.4''
			6. On the custom tuning, check the Audio combo box
			VP: The Audio combo box include those option:
				Line-out0 – Line Out
				Bluetooth0 - Bluetooth
				USB0 - USB
				Attached0: Default
				Attached1: Bypass/off
				Attached2: Music
				Attached3: Movies
				Attached4: Game
				Device Headphone Settings


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Premium''
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
//			5. Leave Eagle Version combo box to ''Eagle 1.4''
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
//			6. On the custom tuning, check the Audio combo box
/*			VP: The Audio combo box include those option:
			Line-out0 – Line Out
			Bluetooth0 - Bluetooth
			USB0 - USB
			Attached0: Default
			Attached1: Bypass/off
			Attached2: Music
			Attached3: Movies
			Attached4: Game
			Device Headphone Settings*/
			Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE),DeviceEdit.routes_medium.getNames()));
		}
/*		@Test
		public void TC650DaDEN_157(){ // This test case donot do the automation test due to eagle 2.0 old design
			Reporter.log("Check the eagle version 2.0 old design Verify the UI on DTS:X Premium of Audio route custom tuning include those option:Line-out0 – Line Out, Bluetooth0 - Bluetooth,USB0 – USBAttached0: Default,Attached1: Bypass/off,Attached2: Music,Attached3: Movies,Attached4: Game,Device Headphone Settings");
			
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click on old device have Eagle verion 2.0 old design
			4. Click  on  "Update infor" Link
			5. Leave the Product Type combo box as ''DTS:X Premium'
			6. On the custom tuning, check the Audio combo box
			VP: The Audio combo box include those option:
				Line-out0 – Line Out
				Bluetooth0 - Bluetooth
				USB0 - USB
				Attached0: Default
				Attached1: Bypass/off
				Attached2: Music
				Attached3: Movies
				Attached4: Game
				Device Headphone Settings


			 
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click on old device have Eagle verion 2.0 old design
//			4. Click  on  "Update infor" Link
//			5. Leave the Product Type combo box as ''DTS:X Premium'
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
//			6. On the custom tuning, check the Audio combo box
			VP: The Audio combo box include those option:
			Line-out0 – Line Out
			Bluetooth0 - Bluetooth
			USB0 - USB
			Attached0: Default
			Attached1: Bypass/off
			Attached2: Music
			Attached3: Movies
			Attached4: Game
			Device Headphone Settings
			Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE),DeviceEdit.routes_medium.getNames()));
		}*/
		@Test
		public void TC650DaDEN_158(){
			Reporter.log("Check the eagle version 1.4 Verify the UI on DTS:X Premium option Device Headphone Settings include:Attached0: Default, Attached2: Music, Attached3: Movies,Attached4: Game");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Premium''
			5. Leave Eagle Version combo box to ''Eagle 1.4''
			6. On the custom tuning, check the Audio combo box
			VP: The 'content Modes'' combo box include those option:
				Attached0: Default
				Attached2: Music
				Attached3: Movies
				Attached4: Game


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Premium''
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
//			5. Leave Eagle Version combo box to ''Eagle 1.4''
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
//			6. Choose option ''Device Headphone Settings'' on the Audio combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.SUB_DROPDOWN_AUDIO_ROUTE));
			/* VP: The 'content Modes'' combo box include those option:
				Attached0: Default
				Attached2: Music
				Attached3: Movies
				Attached4: Game
			*/
			ArrayList<String> typeList = appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE);
			Assert.assertTrue(ListUtil.containsAll(typeList, DeviceEdit.Content_Modes_Medium.getNames()));
	}
/*				@Test
		public void TC650DaDEN_159(){ // This test case donot do the automation test due to eagle 2.0 old design
			Reporter.log("Check the eagle version 1.4 Verify the UI on DTS:X Premium option Device Headphone Settings include:Attached0: Default, Attached2: Music, Attached3: Movies,Attached4: Game");
			
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click on old device have Eagle verion 2.0 old design
			4. Click  on  "Update infor" Link
			5. Leave the Product Type combo box as ''DTS:X Premium'
			6. On the custom tuning, check the Audio combo box
			VP: The 'content Modes'' combo box include those option:
				Attached0: Default
				Attached2: Music
				Attached3: Movies
				Attached4: Game


			 
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click on old device have Eagle verion 2.0 old design
//			4. Click  on  "Update infor" Link
//			5. Leave the Product Type combo box as ''DTS:X Premium'
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType())
//			6. On the custom tuning, check the Audio combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.SUB_DROPDOWN_AUDIO_ROUTE));
			/* VP: The 'content Modes'' combo box include those option:
				Attached0: Default
				Attached2: Music
				Attached3: Movies
				Attached4: Game
			
			ArrayList<String> typeList = appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE);
			Assert.assertTrue(ListUtil.containsAll(typeList, DeviceEdit.Content_Modes_Medium.getNames()));
		}*/
		@Test
		public void TC650DaDEN_160(){
			Reporter.log("Verify Eagle 2.0 design can be upload new default audio .exptune file include new speaker modes and headphone modes with product type DTS:X Premium");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Premium'
			5. Choose Eagle 2.0 on Eagle Version combo box
			6. Upload new default audio route
			VP:  The exptune file can be able to upload successfully.


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Premium''
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
//			5. Leave Eagle Version combo box to ''Eagle 1.4''
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
//			6. Upload new default audio route
//			VP:  The exptune file can be able to upload successfully.
			appDeviceControl.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, AddEditProductModel.FileUpload.Default_Headphone_Tuning_Exptune_Files.getName());
			/*
			 * Verify that The default Audio Route box is disappeared and user can not upload another audio route file
			 */
			Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.ADD_AUDIO_ROUTE_CONTAINER));
	}
		@Test
		public void TC650DaDEN_161(){
			Reporter.log("Verify with Eagle version 1.4 , product type ''DTS:X Premium' canbe upload  new speaker modes valid file successfully");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Premium'
			5. Choose Eagle 1.4 on Eagle Version combo box
			6. Select ''Attached0: Default'' at Audio Route combo box
			7. Upload a valid tuning
			VP: The tuning can be able to upload successfully
			8. Select ''Attached1: Bypass/off'' at Audio Route combo box
			9. Upload a valid tuning
			VP: The tuning can be able to upload successfully.
			10. Select ''Attached2: Music'' at Audio Route combo box
			11. Upload a valid tuning
			VP: The tuning can be able to upload successfully.
			12. Select ''Attached3: Movies'' at Audio Route combo box
			13. Upload a valid tuning
			VP: The tuning can be able to upload successfully.
			14. Select  ''Attached4: Game'' at Audio Route combo box
			15. Upload a valid tuning
			VP: The tuning can be able to upload successfully.
			


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Premium''
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
//			5. Leave Eagle Version combo box to ''Eagle 1.4''
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
//			6. Select ''Attached0: Default'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached0_Internal_Speakers.getName());
//			7. Upload a valid tuning
//			VP: The tuning can be able to upload successfully
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Custom.getName());
//			8. Select ''Attached0: Default'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached1_Internal_Speakers_Off.getName());
//			9. Upload a valid tuning
//			VP: The tuning can be able to upload successfully
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached1InternalSpeakersOff_Custom.getName());
//			10. Select ''Attached1: Bypass/off'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached2_Internal_Speakers_Mode_1.getName());
//			11. Upload a valid tuning
//			VP: The tuning can be able to upload successfully
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached2InternalSpeakersMusic_Custom.getName());
//			12. Select ''Attached0: Default'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached3_Internal_Speakers_Mode_2.getName());
//			13. Upload a valid tuning
//			VP: The tuning can be able to upload successfully
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached3InternalSpeakersMovies_Custom.getName());
//			14. Select ''Attached0: Default'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached4_Internal_Speakers_Mode_3.getName());
//			15. Upload a valid tuning
//			VP: The tuning can be able to upload successfully
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached4InternalSpeakersGames_Custom.getName());
	}
		@Test
		public void TC650DaDEN_162(){
			Reporter.log("Verify with Eagle version 1.4 , product type ''DTS:X Premium' canbe upload new speaker modes invalid file unsuccessfully");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Premium'
			5. Choose Eagle 1.4 on Eagle Version combo box
			6. Select ''Attached0: Default'' at Audio Route combo box
			7. Upload a invalid tuning
			VP: VP: The tuning can be able to upload unsuccessfully.
			8. Select ''Attached1: Bypass/off'' at Audio Route combo box
			9. Upload a invalid tuning
			VP: VP: The tuning can be able to upload unsuccessfully.
			10. Select ''Attached2: Music'' at Audio Route combo box
			11. Upload a invalid tuning
			VP: VP: The tuning can be able to upload unsuccessfully.
			12. Select ''Attached3: Movies'' at Audio Route combo box
			13. Upload a invalid tuning
			VP: VP: The tuning can be able to upload unsuccessfully..
			14. Select  ''Attached4: Game'' at Audio Route combo box
			15. Upload a invalid tuning
			VP: VP: The tuning can be able to upload unsuccessfully.
			


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Premium''
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
//			5. Leave Eagle Version combo box to ''Eagle 1.4''
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
//			6. Select ''Attached0: Default'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached0_Internal_Speakers.getName());
//			7. Upload a invalid tuning
//			VP: The tuning can be able to upload unsuccessfully.
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached1InternalSpeakersOff_Custom.getName());
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE));
//			Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS),DeviceEdit.requires.Invalid_file_contents.getName());
//			8. Select ''Attached1: Bypass/off'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached1_Internal_Speakers_Off.getName());
//			9. Upload a invalid tuning
//			VP: The tuning can be able to upload unsuccessfully.
			appDeviceControl.uploadFile(DeviceEdit.RETRY_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Fallback_out_of_range_value.getName());
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE));
//			10. Select ''Attached2: Music'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached2_Internal_Speakers_Mode_1.getName());
//			11. Upload a invalid tuning
//			VP: The tuning can be able to upload unsuccessfully.
			appDeviceControl.uploadFile(DeviceEdit.RETRY_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Fallback_out_of_range_value.getName());
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE));
//			12. Select ''Attached3: Movies'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached3_Internal_Speakers_Mode_2.getName());
//			13. Upload a invalid tuning
//			VP: The tuning can be able to upload unsuccessfully.
			appDeviceControl.uploadFile(DeviceEdit.RETRY_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Fallback_out_of_range_value.getName());
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE));
//			14. Select ''Attached4: Game'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached4_Internal_Speakers_Mode_3.getName());
//			15. Upload a invalid tuning
//			VP: The tuning can be able to upload unsuccessfully.
			appDeviceControl.uploadFile(DeviceEdit.RETRY_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Fallback_out_of_range_value.getName());
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE));
		}
/*		@Test
		public void TC650DaDEN_163(){ this testcase donot do automation due to related with eagle 2.0 old design
			Reporter.log("Verify with Eagle version 2.0 , product type ''DTS:X Premium' canbe upload new speaker modes invalid file unsuccessfully");			
/*
}
 */
		/*		@Test
		public void TC650DaDEN_164(){ this testcase donot do automation due to related with eagle 2.0 old design
			Reporter.log("Verify with Eagle version 2.0 , product type ''DTS:X Premium' canbe upload new speaker modes invalid file unsuccessfully");			
/*
}
 */
		@Test
		public void TC650DaDEN_165(){
			Reporter.log("Verify with Eagle version 1.4 , product type ''DTS:X Premium' canbe upload new Headphone modes valid file successfully");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Premium'
			5. Choose Eagle 1.4 on Eagle Version combo box
			6. Select ''Device Headphone Settings'' at Audio Route combo box
			7. Select ''Attached0: Default'' at content modes combo box
			8. Upload a valid tuning
			VP: The tuning can be able to upload successfully.
			9. Select ''Attached2: Music'' at content modes combo box
			10. Upload a invalid tuning
			VP: The tuning can be able to upload unsuccessfully.
			11. Select ''Attached3: Movies'' at content modes combo box
			12. Upload a invalid tuning
			VP: VP: The tuning can be able to upload unsuccessfully.
			13. Select  ''Attached4: Game'' at content modes combo box
			14. Upload a invalid tuning
			VP: The tuning can be able to upload unsuccessfully..
			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Premium''
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
//			5. Leave Eagle Version combo box to ''Eagle 1.4''
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
//			6. Select ''Device Headphone Settings'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
//			7. Select ''Attached0: Default'' at content modes combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_0_Default.getName());
//			8. Upload a valid tuning
//			VP: The tuning can be able to upload successfully
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Attach0.getName());
//			9. Select ''Attached2: Music'' at content modes combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_2_Music.getName());
//			10. Upload a valid tuning
//			VP: The tuning can be able to upload successfully
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Attach2.getName());
//			11. Select ''Attached3: Movies'' at content modes combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_3_Movies.getName());
//			12. Upload a valid tuning
//			VP: The tuning can be able to upload successfully
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Attach3.getName());
//			13. Select  ''Attached4: Game'' at content modes combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_4_Game.getName());
//			14. Upload a valid tuning
//			VP: The tuning can be able to upload successfully
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Attach4.getName());
		}
		@Test
		public void TC650DaDEN_166(){
			Reporter.log("Verify with Eagle version 1.4 , product type ''DTS:X Premium' canbe upload new Headphone modes invalid file unsuccessfully");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Premium'
			5. Choose Eagle 1.4 on Eagle Version combo box
			6. Select ''Device Headphone Settings'' at Audio Route combo box
			7. Select ''Attached0: Default'' at content modes combo box
			8. Upload a invalid tuning
			VP: The tuning can be able to upload unsuccessfully.
			9. Select ''Attached2: Music'' at content modes combo box
			10. Upload a invalid tuning
			VP: The tuning can be able to upload unsuccessfully.
			11. Select ''Attached3: Movies'' at content modes combo box
			12. Upload a invalid tuning
			VP: The tuning can be able to upload unsuccessfully.
			13. Select  ''Attached4: Game'' at content modes combo box
			14. Upload a invalid tuning
			VP: The tuning can be able to upload unsuccessfully.
			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Premium''
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
//			5. Leave Eagle Version combo box to ''Eagle 1.4''
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
//			6. Select ''Device Headphone Settings'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
//			7. Select ''Attached0: Default'' at content modes combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_0_Default.getName());
//			8. Upload a invalid tuning
//			VP: The tuning can be able to upload unsuccessfully.
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Custom.getName());
//			9. Select ''Attached2: Music'' at content modes combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_2_Music.getName());
//			10. Upload a invalid tuning
//			VP: The tuning can be able to upload unsuccessfully.
			appDeviceControl.uploadFile(DeviceEdit.RETRY_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Fallback_out_of_range_value.getName());
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE));
//			11. Select ''Attached3: Movies'' at content modes combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_3_Movies.getName());
//			12. Upload a invalid tuning
//			VP: The tuning can be able to upload unsuccessfully.
			appDeviceControl.uploadFile(DeviceEdit.RETRY_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Fallback_out_of_range_value.getName());
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE));
//			13. Select  ''Attached4: Game'' at content modes combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_4_Game.getName());
//			14. Upload a invalid tuning
//			VP: The tuning can be able to upload unsuccessfully.
			appDeviceControl.uploadFile(DeviceEdit.RETRY_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Fallback_out_of_range_value.getName());
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE));
		}
/*		@Test
		public void TC650DaDEN_167(){this case donot do automation test due to eagle 2.0 old design
			Reporter.log("Verify with Eagle version 2.0 old design , product type ''DTS:X Premium' canbe upload new Headphone modes valid file successfully");
		@Test
		public void TC650DaDEN_168(){this case donot do automation test due to eagle 2.0 old design
			Reporter.log("Verify with Eagle version 2.0 old design , product type ''DTS:X Premium' canbe upload new Headphone modes invalid file unsuccessfully");*/	
		@Test
		public void TC650DaDEN_169(){
			Reporter.log("Verify with Eagle version 1.4 , with product type ''DTS:X Premium'', new speaker modes can be delete after uploading");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Premium'
			5. Choose Eagle 1.4 on Eagle Version combo box
			6. Select ''Attached0: Default'' at Audio Route combo box
			7. Upload a valid tuning
			VP: The tuning can be able to upload successfully.
			8. Click to button ''Delete'' with speaker route has just been upload
			VP: Tuning can be delete.
			*/
			// 1. Log into DTS portal as DTS admin
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			// 2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click "Add App or Device" link 
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			// 4. Leave the Product Type combo box as ''DTS:X Premium'
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
			// 5. Choose Eagle 1.4 on Eagle Version combo box
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
			// 6. Select ''Attached0: Default'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached0_Internal_Speakers.getName());
			// 7. Upload a valid tuning
			// VP: The tuning can be able to upload successfully
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Custom.getName());
			//8. Click to button ''Delete'' with speaker route has just been upload
			appDeviceControl.click(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
			appDeviceControl.waitForElementClickable(PageHome.BTN_CONFIRMATION_DANGER);
			/*
			 * Verify that Delete confirmation dialog with "Delete" and "Cancel" button is displayed 
			 */
			Assert.assertEquals(appDeviceControl.getTextByXpath(PageHome.BTN_CONFIRMATION_DANGER), "Delete");
			Assert.assertEquals(appDeviceControl.getTextByXpath(PageHome.BTN_CONFIRMATION_CANCEL), "Cancel");
			appDeviceControl.click(PageHome.BTN_CONFIRMATION_DANGER);
		}
		@Test
		public void TC650DaDEN_170(){
			Reporter.log("Verify with Eagle version 1.4 , with product type ''DTS:X Premium'', new Headphone modes can be delete after uploading");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS:X Premium'
			5. Choose Eagle 1.4 on Eagle Version combo box
			6. Select ''Device Headphone Settings'' at Audio Route combo box
			7. Select ''Attached0: Default'' at Audio Route combo box
			8. Upload a valid single tuning
			VP: The tuning can be able to upload successfully.
			9. Click to button ''Delete'' with speaker route has just been upload
			VP: Tuning can be delete.
			*/
			// 1. Log into DTS portal as DTS admin
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			// 2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click "Add App or Device" link 
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			// 4. Leave the Product Type combo box as ''DTS:X Premium'
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
			// 5. Choose Eagle 1.4 on Eagle Version combo box
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
			// 6. Select ''Device Headphone Settings'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			// 7. Select ''Attached0: Default'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_0_Default.getName());
			// 8. Upload a valid single tuning
			// VP: The tuning can be able to upload successfully
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Attach0.getName());
			// 9. Click to button ''Delete'' with speaker route has just been upload
			appDeviceControl.click(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
			appDeviceControl.waitForElementClickable(PageHome.BTN_CONFIRMATION_DANGER);
			/*
			 * Verify that Delete confirmation dialog with "Delete" and "Cancel" button is displayed 
			 */
			Assert.assertEquals(appDeviceControl.getTextByXpath(PageHome.BTN_CONFIRMATION_DANGER), "Delete");
			Assert.assertEquals(appDeviceControl.getTextByXpath(PageHome.BTN_CONFIRMATION_CANCEL), "Cancel");
			appDeviceControl.click(PageHome.BTN_CONFIRMATION_DANGER);
		}
/*		@Test
		public void TC650DaDEN_171(){this case donot do automation test due to eagle 2.0 old design
			Reporter.log("Verify with Eagle version 2.0 old design , with product type ''DTS:X Premium'', new speaker modes can be delete after uploading");
		@Test
		public void TC650DaDEN_172(){this case donot do automation test due to eagle 2.0 old design
			Reporter.log("Verify with Eagle version 2.0 old design , with product type ''DTS:X Premium'', new Headphone modes can be delete after uploading");*/
		@Test
		public void TC650DaDEN_173(){
			Reporter.log("Verify with Eagle version 1.4 , with product type ''DTS:X Premium'', display correctly when switch off to order product");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Choose Eagle 1.4 on Eagle Version combo box
			5. Leave the Product Type combo box as ''DTS:X Ultra'
			6. Leave the Product Type combo box as ''DTS:X Premium'
			VP: The Audio combo box include those option:
			Attached0: Default
			Attached1: Bypass/off
			Attached2: Music
			Attached3: Movies
			Attached4: Game
			Device Headphone Settings
			*/
			// 1. Log into DTS portal as DTS admin
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			// 2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click "Add App or Device" link 
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			// 4. Choose Eagle 1.4 on Eagle Version combo box
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
			// 5. Leave the Product Type combo box as ''HPX_High'
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_High.getType());
			// 6. Leave the Product Type combo box as ''HPX_Medium'
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_Medium.getType());
			// VP: The Audio combo box include those option:
/*			Attached0: Default
			Attached1: Bypass/off
			Attached2: Music
			Attached3: Movies
			Attached4: Game
			Device Headphone Settings*/
			Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE),DeviceEdit.routes_medium.getNames()));
}
/*		@Test
		public void TC650DaDEN_174(){this case donot do automation test due to eagle 2.0 old design
			Reporter.log("Verify with Eagle version 2.0 old design , with product type ''DTS:X Premium'', display correctly when switch off to order product");*/
		@Test
		public void TC650DaDEN_175(){
			Reporter.log("Verify with Eagle version 1.4 , with product type ''DTS:X Premium'', display correctly when switch off from eagle version 2.0 to 1.4");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Choose Eagle 2.0 on Eagle Version combo box
			5. Leave the Product Type combo box as ''DTS:X Premium'
			6. Choose Eagle 1.4 on Eagle Version combo box
			7. Check the Audio route combo box
			VP: The Audio combo box include those option:
			Attached0: Default
			Attached1: Bypass/off
			Attached2: Music
			Attached3: Movies
			Attached4: Game
			Device Headphone Settings
			*/
			// 1. Log into DTS portal as DTS admin
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			// 2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click "Add App or Device" link 
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			// 4. Choose Eagle 2.0 on Eagle Version combo box
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			// 5. Leave the Product Type combo box as ''DTS:X Premium'
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
			// 6. Choose Eagle 1.4 on Eagle Version combo box
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
			// 7. Check the Audio route combo box
			// VP: The Audio combo box include those option:
/*			Attached0: Default
			Attached1: Bypass/off
			Attached2: Music
			Attached3: Movies
			Attached4: Game
			Device Headphone Settings*/
			Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE),DeviceEdit.routes_medium.getNames()));
		}
		/*		@Test
		public void TC650DaDEN_174(){this case donot do automation test due to eagle 2.0 old design
			Reporter.log("Verify with Eagle version 2.0 old design , with product type ''DTS:X Premium'', display correctly when switch off to order product");*/
		@Test
		public void TC650DaDEN_175a(){
			Reporter.log("Verify default custom tuning show right information");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Choose Eagle 1.4 on Eagle Version combo box
			5. Leave the Product Type combo box as ''type'
			6. Select the combo box at custom tuning
			VP: Default customer tuning will show those option as below:
         	Line-out0 – Line out
			Bluetooth0- Bluetooth
			USB0- USBAttached0: Default
			Attached1: Bypass/off
			Attached2: Mode2
			Attached3: Mode3
			Attached4: Mode4
			Attached5: Mode5
			Attached6: Mode6
			Attached7: Mode7
			Attached8: Mode8
			Combo0-Dual Audio
			Device Headphone Settings
			*/
			// 1. Log into DTS portal as DTS admin
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			// 2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click "Add App or Device" link 
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			// 4. Choose Eagle 1.4 on Eagle Version combo box
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
			// 5. LLeave the Product Type combo box as ''type'
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.Select.getType());
			// 6. Select the combo box at custom tuning
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
/*			VP: Default customer tuning will show those option as below:
	         	Line-out0 – Line out
				Bluetooth0- Bluetooth
				USB0- USBAttached0: Default
				Attached1: Bypass/off
				Attached2: Mode2
				Attached3: Mode3
				Attached4: Mode4
				Attached5: Mode5
				Attached6: Mode6
				Attached7: Mode7
				Attached8: Mode8
				Combo0-Dual Audio
				Device Headphone Settings*/
			Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE),DeviceEdit.routes.getNames()));
		}
		@Test
		public void TC650DaDEN_176(){
			Reporter.log("Check the eagle version 1.4 Verify the UI on HPX Low of Audio route custom tuning include those option::Attached1: Bypass/off,Attached2: Music,Attached3: Movies,Attached4: Game,Device Headphone Settings");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''HPX Low''
			5. Leave Eagle Version combo box to ''Eagle 1.4''
			6. On the custom tuning, check the Audio combo box
			VP: The Audio combo box include those option:
				Attached1: Bypass/off
				Attached2: Music
				Attached3: Movies
				Attached4: Game
				Device Headphone Settings


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Premium''
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
//			5. Leave Eagle Version combo box to ''Eagle 1.4''
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
//			6. On the custom tuning, check the Audio combo box
/*			VP: The Audio combo box include those option:
			Attached1: Bypass/off
			Attached2: Music
			Attached3: Movies
			Attached4: Game
			Device Headphone Settings*/
			Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE),DeviceEdit.routes_low.getNames()));
		}
/*		@Test
		public void TC650DaDEN_177(){ // This test case donot do the automation test due to eagle 2.0 old design
			Reporter.log("Check the eagle version 2.0 old design. Verify the UI of DTS Audio Processing on Device Headphone Settings include those option:Line-out0 – Line Out, Bluetooth0 - Bluetooth,USB0 – USBAttached0: Default,Attached1: Bypass/off,Attached2: Music,Attached3: Movies,Attached4: Game,Device Headphone Settings");
		}*/
		@Test
		public void TC650DaDEN_178(){
			Reporter.log("Check the eagle version 1.4 Verify the UI on DTS Audio Processing option Device Headphone Settings include: Attached2: Music, Attached3: Movies,Attached4: Game");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as HPX Low
			5. Leave Eagle Version combo box to ''Eagle 1.4''
			6. On the custom tuning, check the Audio combo box
			VP: The 'content Modes'' combo box include those option:
				Attached2: Music
				Attached3: Movies
				Attached4: Game


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as HPX Low
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
//			5. Leave Eagle Version combo box to ''Eagle 1.4''
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
//			6. Choose option ''Device Headphone Settings'' on the Audio combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.SUB_DROPDOWN_AUDIO_ROUTE));
			/* VP: The 'content Modes'' combo box include those option:
				Attached2: Music
				Attached3: Movies
				Attached4: Game
			*/
			ArrayList<String> typeList = appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE);
			Assert.assertTrue(ListUtil.containsAll(typeList, DeviceEdit.Content_Modes_Low.getNames()));
	}
/*				@Test
		public void TC650DaDEN_179(){ // This test case donot do the automation test due to eagle 2.0 old design
			Reporter.log("Check the eagle version 2.0 Verify the UI on DTS Audio Processing option Device Headphone Settings include: Attached2: Music, Attached3: Movies,Attached4: Game");
			
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click on old device have Eagle verion 2.0 old design
			4. Click  on  "Update infor" Link
			5. Leave the Product Type combo box as ''HPX Low'
			6. On the custom tuning, check the Audio combo box
			VP: The 'content Modes'' combo box include those option:
				Attached2: Music
				Attached3: Movies
				Attached4: Game
		}*/
		@Test
		public void TC650DaDEN_180(){
			Reporter.log("Verify Eagle 2.0 new design can be upload new default audio .exptune file include new speaker modes and headphone modes with product type DTS Audio Processing");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS Audio Processing'
			5. Choose Eagle 2.0 on Eagle Version combo box
			6. Upload new default audio route
			VP:  The exptune file can be able to upload successfully.


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS Audio Processingm''
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
//			5. Leave Eagle Version combo box to ''Eagle 1.4''
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
//			6. Upload new default audio route
//			VP:  The exptune file can be able to upload successfully.
			appDeviceControl.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, AddEditProductModel.FileUpload.Default_Headphone_Tuning_Exptune_Files.getName());
			/*
			 * Verify that The default Audio Route box is disappeared and user can not upload another audio route file
			 */
			Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.ADD_AUDIO_ROUTE_CONTAINER));
	}
		@Test
		public void TC650DaDEN_181(){
			Reporter.log("Verify with Eagle version 1.4 , product type ''DTS Audio Processing' canbe upload  new speaker modes valid file successfully");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''HPX Low'
			5. Choose Eagle 1.4 on Eagle Version combo box
			6. Select ''Attached1: Bypass/off'' at Audio Route combo box
			7. Upload a valid tuning
			VP: The tuning can be able to upload successfully.
			8. Select ''Attached2: Music'' at Audio Route combo box
			9. Upload a valid tuning
			VP: The tuning can be able to upload successfully.
			10. Select ''Attached3: Movies'' at Audio Route combo box
			11. Upload a valid tuning
			VP: The tuning can be able to upload successfully.
			12. Select  ''Attached4: Game'' at Audio Route combo box
			13. Upload a valid tuning
			VP: The tuning can be able to upload successfully.
			


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Premium''
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
//			5. Leave Eagle Version combo box to ''Eagle 1.4''
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
//			6. Select ''Attached1: Bypass/off'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached1_Internal_Speakers_Off.getName());
//			7. Upload a valid tuning
//			VP: The tuning can be able to upload successfully
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached1InternalSpeakersOff_Custom.getName());
//			8. Select ''Attached2: Music'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached2_Internal_Speakers_Mode_1.getName());
//			9. Upload a valid tuning
//			VP: The tuning can be able to upload successfully
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached2InternalSpeakersMusic_Custom.getName());
//			10. Select ''Attached3: Movies'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached3_Internal_Speakers_Mode_2.getName());
//			11. Upload a valid tuning
//			VP: The tuning can be able to upload successfully
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached3InternalSpeakersMovies_Custom.getName());
//			12. Select ''Attached4: Game'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached4_Internal_Speakers_Mode_3.getName());
//			13. Upload a valid tuning
//			VP: The tuning can be able to upload successfully
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached4InternalSpeakersGames_Custom.getName());
	}
		@Test
		public void TC650DaDEN_182(){
			Reporter.log("Verify with Eagle version 1.4 , product type ''DTS Audio Processing' canbe upload new speaker modes invalid file unsuccessfully");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''HPX Low'
			5. Choose Eagle 1.4 on Eagle Version combo box
			6. Select ''Attached0: Default'' at Audio Route combo box
			7. Upload a invalid tuning
			VP: VP: The tuning can be able to upload unsuccessfully.
			8. Select ''Attached1: Bypass/off'' at Audio Route combo box
			9. Upload a invalid tuning
			VP: VP: The tuning can be able to upload unsuccessfully.
			10. Select ''Attached2: Music'' at Audio Route combo box
			11. Upload a invalid tuning
			VP: VP: The tuning can be able to upload unsuccessfully.
			12. Select ''Attached3: Movies'' at Audio Route combo box
			13. Upload a invalid tuning
			VP: VP: The tuning can be able to upload unsuccessfully..
			14. Select  ''Attached4: Game'' at Audio Route combo box
			15. Upload a invalid tuning
			VP: VP: The tuning can be able to upload unsuccessfully.
			


			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Premium''
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
//			5. Leave Eagle Version combo box to ''Eagle 1.4''
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
//			6. Select ''Attached1: Bypass/off'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached1_Internal_Speakers_Off.getName());
//			7. Upload a invalid tuning
//			VP: The tuning can be able to upload unsuccessfully.
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Custom.getName());
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE));
//			8. Select ''Attached2: Music'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached2_Internal_Speakers_Mode_1.getName());
//			9. Upload a invalid tuning
//			VP: The tuning can be able to upload unsuccessfully.
			appDeviceControl.uploadFile(DeviceEdit.RETRY_CUSTOM_TUNING, AddEditProductModel.FileUpload.Combo0.getName());
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE));
//			10. Select ''Attached3: Movies'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached3_Internal_Speakers_Mode_2.getName());
//			11. Upload a invalid tuning
//			VP: The tuning can be able to upload unsuccessfully.
			appDeviceControl.uploadFile(DeviceEdit.RETRY_CUSTOM_TUNING, AddEditProductModel.FileUpload.Combo0.getName());
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE));
//			12. Select ''Attached4: Game'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached4_Internal_Speakers_Mode_3.getName());
//			13. Upload a invalid tuning
//			VP: The tuning can be able to upload unsuccessfully.
			appDeviceControl.uploadFile(DeviceEdit.RETRY_CUSTOM_TUNING, AddEditProductModel.FileUpload.Combo0.getName());
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE));
		}
/*		@Test
		public void TC650DaDEN_183(){ this testcase donot do automation due to related with eagle 2.0 old design
			Reporter.log("Verify with Eagle version 2.0 old design , product type ''DTS Audio Processing' canbe upload  new speaker modes valid file successfully");			
/*
}
 */
		/*		@Test
		public void TC650DaDEN_184(){ this testcase donot do automation due to related with eagle 2.0 old design
			Reporter.log("Verify with Eagle version 2.0 old design , product type ''DTS Audio Processing' canbe upload new speaker modes invalid file unsuccessfully");			
/*
}
 */
		@Test
		public void TC650DaDEN_185(){
			Reporter.log("Verify with Eagle version 1.4 , product type ''DTS Audio Processing' canbe upload new Headphone modes valid file successfully");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS Audio Processing'
			5. Choose Eagle 1.4 on Eagle Version combo box
			6. Select ''Device Headphone Settings'' at Audio Route combo box
			7. Select ''Attached2: Music'' at content modes combo boxz
			8. Upload a invalid tuning
			VP: The tuning can be able to upload unsuccessfully.
			9. Select ''Attached3: Movies'' at content modes combo box
			10. Upload a invalid tuning
			VP: VP: The tuning can be able to upload unsuccessfully.
			11. Select  ''Attached4: Game'' at content modes combo box
			12. Upload a invalid tuning
			VP: The tuning can be able to upload unsuccessfully..
			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Premium''
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
//			5. Leave Eagle Version combo box to ''Eagle 1.4''
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
//			6. Select ''Device Headphone Settings'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
//			7. Select ''Attached2: Music'' at content modes combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_2_Music.getName());
//			8. Upload a valid tuning
//			VP: The tuning can be able to upload successfully
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Attach2.getName());
//			9. Select ''Attached3: Movies'' at content modes combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_3_Movies.getName());
//			10. Upload a valid tuning
//			VP: The tuning can be able to upload successfully
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Attach3.getName());
//			11. Select  ''Attached4: Game'' at content modes combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_4_Game.getName());
//			12. Upload a valid tuning
//			VP: The tuning can be able to upload successfully
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Attach4.getName());
		}
		@Test
		public void TC650DaDEN_186(){
			Reporter.log("Verify with Eagle version 1.4 , product type ''DTS Audio Processing' canbe upload new Headphone modes invalid file unsuccessfully");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS Audio Processing'
			5. Choose Eagle 1.4 on Eagle Version combo box
			6. Select ''Device Headphone Settings'' at Audio Route combo box
			7. Select ''Attached2: Music'' at content modes combo box
			8. Upload a invalid tuning
			VP: The tuning can be able to upload unsuccessfully.
			9. Select ''Attached3: Movies'' at content modes combo box
			10. Upload a invalid tuning
			VP: The tuning can be able to upload unsuccessfully.
			11. Select  ''Attached4: Game'' at content modes combo box
			12. Upload a invalid tuning
			VP: The tuning can be able to upload unsuccessfully.
			 */
//			1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//			3. Click “Add App or Device” link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//			4. Leave the Product Type combo box as ''DTS:X Premium''
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
//			5. Leave Eagle Version combo box to ''Eagle 1.4''
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
//			6. Select ''Device Headphone Settings'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
//			7. Select ''Attached2: Music'' at content modes combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_2_Music.getName());
//			8. Upload a invalid tuning
//			VP: The tuning can be able to upload unsuccessfully.
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Combo0.getName());
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE));
//			9. Select ''Attached3: Movies'' at content modes combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_3_Movies.getName());
//			10. Upload a invalid tuning
//			VP: The tuning can be able to upload unsuccessfully.
			appDeviceControl.uploadFile(DeviceEdit.RETRY_CUSTOM_TUNING, AddEditProductModel.FileUpload.Combo0.getName());
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE));
//			11. Select  ''Attached4: Game'' at content modes combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_4_Game.getName());
//			12. Upload a invalid tuning
//			VP: The tuning can be able to upload unsuccessfully.
			appDeviceControl.uploadFile(DeviceEdit.RETRY_CUSTOM_TUNING, AddEditProductModel.FileUpload.Combo0.getName());
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE));
		}
/*		@Test
		public void TC650DaDEN_187(){this case donot do automation test due to eagle 2.0 old design
			Reporter.log("Verify with Eagle version 2.0 old design , product type ''DTS Audio Processing' canbe upload new Headphone modes valid file successfully");
		@Test
		public void TC650DaDEN_188(){this case donot do automation test due to eagle 2.0 old design
			Reporter.log("Verify with Eagle version 2.0 old design , product type ''DTS Audio Processing' canbe upload new Headphone modes invalid file unsuccessfully");*/	
		@Test
		public void TC650DaDEN_189(){
			Reporter.log("Verify with Eagle version 1.4 , with product type ''DTS Audio Processing'', new speaker modes can be delete after uploading");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS Audio Processing''
			5. Choose Eagle 1.4 on Eagle Version combo box
			6. Select ''Attached4: Game'' at Audio Route combo box
			7. Upload a valid tuning
			VP: The tuning can be able to upload successfully.
			8. Click to button ''Delete'' with speaker route has just been upload
			VP: Tuning can be delete.
			*/
			// 1. Log into DTS portal as DTS admin
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			// 2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click "Add App or Device" link 
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			// 4. Leave the Product Type combo box as ''DTS Audio Processing'
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
			// 5. Choose Eagle 1.4 on Eagle Version combo box
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
			// 6. Select ''Attached4: Game'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Attached4_Internal_Speakers_Mode_3.getName());
			// 7. Upload a valid tuning
			// VP: The tuning can be able to upload successfully
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached4InternalSpeakersGames_Custom.getName());
			//8. Click to button ''Delete'' with speaker route has just been upload
			appDeviceControl.click(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
			appDeviceControl.waitForElementClickable(PageHome.BTN_CONFIRMATION_DANGER);
			/*
			 * Verify that Delete confirmation dialog with "Delete" and "Cancel" button is displayed 
			 */
			Assert.assertEquals(appDeviceControl.getTextByXpath(PageHome.BTN_CONFIRMATION_DANGER), "Delete");
			Assert.assertEquals(appDeviceControl.getTextByXpath(PageHome.BTN_CONFIRMATION_CANCEL), "Cancel");
			appDeviceControl.click(PageHome.BTN_CONFIRMATION_DANGER);
		}
		@Test
		public void TC650DaDEN_190(){
			Reporter.log("Verify with Eagle version 1.4 , with product type ''DTS Audio Processing'', new Headphone modes can be delete after uploading");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Leave the Product Type combo box as ''DTS Audio Processing'
			5. Choose Eagle 1.4 on Eagle Version combo box
			6. Select ''Device Headphone Settings'' at Audio Route combo box
			7. Select ''Attached4: Game'' at Audio Route combo box
			8. Upload a valid single tuning
			VP: The tuning can be able to upload successfully.
			9. Click to button ''Delete'' with speaker route has just been upload
			VP: Tuning can be delete.
			*/
			// 1. Log into DTS portal as DTS admin
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			// 2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click "Add App or Device" link 
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			// 4. Leave the Product Type combo box as ''DTS Audio Processing'
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
			// 5. Choose Eagle 1.4 on Eagle Version combo box
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
			// 6. Select ''Device Headphone Settings'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
			// 7. Select ''Attached4: Game'' at Audio Route combo box
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_4_Game.getName());
			// 8. Upload a valid single tuning
			// VP: The tuning can be able to upload successfully
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Attach0.getName());
			// 9. Click to button ''Delete'' with speaker route has just been upload
			appDeviceControl.click(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
			appDeviceControl.waitForElementClickable(PageHome.BTN_CONFIRMATION_DANGER);
			/*
			 * Verify that Delete confirmation dialog with "Delete" and "Cancel" button is displayed 
			 */
			Assert.assertEquals(appDeviceControl.getTextByXpath(PageHome.BTN_CONFIRMATION_DANGER), "Delete");
			Assert.assertEquals(appDeviceControl.getTextByXpath(PageHome.BTN_CONFIRMATION_CANCEL), "Cancel");
			appDeviceControl.click(PageHome.BTN_CONFIRMATION_DANGER);
		}
/*		@Test
		public void TC650DaDEN_191(){this case donot do automation test due to eagle 2.0 old design
			Reporter.log("Verify with Eagle version 2.0 old design , with product type ''DTS Audio Processing'', new speaker modes can be delete after uploading");
		@Test
		public void TC650DaDEN_192(){this case donot do automation test due to eagle 2.0 old design
			Reporter.log("Verify with Eagle version 2.0 old design , with product type ''DTS Audio Processing'', new Headphone modes can be delete after uploading");*/
		@Test
		public void TC650DaDEN_193(){
			Reporter.log("Verify with Eagle version 1.4 , with product type ''DTS Audio Processing'', display correctly when switch off to order product");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Choose Eagle 1.4 on Eagle Version combo box
			5. Leave the Product Type combo box as ''DTS:X Ultra'
			6. Leave the Product Type combo box as ''DTS Audio Processing'
			VP: The Audio combo box include those option:
			Attached1: Bypass/off
			Attached2: Music
			Attached3: Movies
			Attached4: Game
			Device Headphone Settings
			*/
			// 1. Log into DTS portal as DTS admin
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			// 2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click "Add App or Device" link 
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			// 4. Choose Eagle 1.4 on Eagle Version combo box
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
			// 5. Leave the Product Type combo box as ''DTS:X Ultra'
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
			// 6. Leave the Product Type combo box as ''DTS:X Premium'
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
			// VP: The Audio combo box include those option:
//			Attached1: Bypass/off
//			Attached2: Music
//			Attached3: Movies
//			Attached4: Game
//			Device Headphone Settings*
			Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE),DeviceEdit.routes_low.getNames()));
}
/*		@Test
		public void TC650DaDEN_194(){this case donot do automation test due to eagle 2.0 old design
			Reporter.log("Verify with Eagle version 2.0 old design , with product type ''DTS Audio Processing'', display correctly when switch off to order product");*/
		@Test
		public void TC650DaDEN_195(){
			Reporter.log("Verify with Eagle version 1.4 , with product type ''DTS Audio Processing'', display correctly when switch off from eagle version 2.0 to 1.4");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Choose Eagle 2.0 on Eagle Version combo box
			5. Leave the Product Type combo box as ''DTS Audio Processing'
			6. Choose Eagle 1.4 on Eagle Version combo box
			7. Check the Audio route combo box
			VP: The Audio combo box include those option:
			Attached1: Bypass/off
			Attached2: Music
			Attached3: Movies
			Attached4: Game
			Device Headphone Settings
			*/
			// 1. Log into DTS portal as DTS admin
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			// 2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click "Add App or Device" link 
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			// 4. Choose Eagle 2.0 on Eagle Version combo box
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
			// 5. Leave the Product Type combo box as ''DTS Audio Processing'
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
			// 6. Choose Eagle 1.4 on Eagle Version combo box
			appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle1_4.getName());
			// 7. Check the Audio route combo box
			// VP: The Audio combo box include those option:
//			Attached1: Bypass/off
//			Attached2: Music
//			Attached3: Movies
//			Attached4: Game
//			Device Headphone Settings*/
			Assert.assertTrue(ListUtil.containsAll(appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE),DeviceEdit.routes_low.getNames()));}
		@Test
		public void TC650DaDEN_196(){
			Reporter.log("Verify the DTS user donot have new part: Additional Device Promotion");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Choose the ''DTS:X Ultra'' on "Product Type" combo box
			5. Navigate the "Inbox headphone"
			VP: Donot have Additional Device Promotion on Featured Headphone 
			*/
			// 1. Log into DTS portal as DTS admin
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			// 2. Navigate to "Apps & Devices" page
			appDeviceControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click "Add App or Device" link 
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			// 4. Choose the ''DTS:X Ultra'' on "Product Type" combo box
			appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
			// 5. Navigate the "Device Promotions"
			// VP: Donot have Additional Device Promotion on Device Promotions
			Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.INSTRUCTION_TEXT_ADDITION_PROMOTION), "Additional Device Promotion is not existed");
}
		}