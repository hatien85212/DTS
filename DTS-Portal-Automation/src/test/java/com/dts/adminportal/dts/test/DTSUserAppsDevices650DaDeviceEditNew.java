package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.DTSUtil;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AccessoryMain;
import dts.com.adminportal.model.AddAccessory;
import dts.com.adminportal.model.AddPromotion;
import dts.com.adminportal.model.Companies;
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.DeviceEdit;
import dts.com.adminportal.model.DeviceInfo;
import dts.com.adminportal.model.DeviceList;
import dts.com.adminportal.model.PromotionInfo;
import dts.com.adminportal.model.PromotionList;
import dts.com.adminportal.model.PromotionPackage;
import dts.com.adminportal.model.Xpath;

public class DTSUserAppsDevices650DaDeviceEditNew extends CreatePage{
	private HomeController home;
	
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}
	
	@BeforeMethod
	public void loginBeforeTest() {
		home.login(superUsername, superUserpassword);
	}
	
	/*
	 * Verify that the word "Product" is displayed as the page title when adding new device.
	 */
	@Test
	public void TC650DaDEN_01(){
		result.addLog("ID : TC650DaDEN_01 : Verify that the word 'Product' is displayed as the page title when adding new device.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized productS" privilege.
			1. Log into DTS portal as above DTS admin user
			2. Navgate to "Apps & Devices" page
			3. Click "Add App or Device" link
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * The 650Da Device Edit - New page is displayed and its title is "Product".
		 */
		Assert.assertEquals(home.existsElement(DeviceEdit.getHash()).getResult(), "Pass");
		Assert.assertEquals("Product", home.getTextByXpath(DeviceEdit.TITLE_DEFAULT));
	}
	
	/*
	 * Verify that the "Type", "Company", "Brand", "Name" and "OS" fields are required when adding new device.
	 */
	@Test
	public void TC650DaDEN_02(){
		result.addLog("ID : TC650DaDEN_02 : Verify that the 'Type', 'Company', 'Brand', 'Name' and 'OS' fields are required when adding new device.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Click "Save' link without enter or select value of "Type", "Company", "Brand", "Name" and "Init Key Name" fields
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Click "Save' link without enter or select value of "Type", "Company", "Brand", "Name" and "OS" fields
		home.click(DeviceEdit.SAVE);
		// Get error messages
		ArrayList<String> errorMessages = home.getTextByXpath(DeviceEdit.getErroMessageXpath());
		/*
		 * Error messages are displayed which inform that the "Type", "Company", "Brand", "Name" and "OS" fields are required.
		 */
		Assert.assertTrue(DTSUtil.containsAll(errorMessages, DeviceEdit.requires));
	}
	
	/*
	 * Verify that the "Type" combobox contains "-Select-", "App" and "Device" items
	 */
	@Test
	public void TC650DaDEN_03(){
		result.addLog("ID : TC650DaDEN_03 : Verify that the 'Type' combobox contains '-Select-', 'App' and 'Device' items");
		/*
			Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			VP: The 650Da Device Edit - New page is displayed
			4. List out all items of "Type" combobox
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// VP: The 650Da Device Edit - New page is displayed
		Assert.assertEquals(home.existsElement(DeviceEdit.getHash()).getResult(), "Pass");
		// 4. List out all items of "Type" combobox
		/*
		 * The "Type" combobox contains "-Select-", "App" and "Device" items
		 */
		ArrayList<String> typeList = home.getAllComboboxOption(DeviceEdit.TYPE);
		Assert.assertTrue(DTSUtil.containsAll(typeList, DeviceEdit.types));
	}
	
	/*
	 * Verify that the "OS" combobox contains "-Select-", "Android", "iOS", "Windows Phone", "Windows", "Linux"  and "Other" items
	 */
	@Test
	public void TC650DaDEN_04(){
		result.addLog("ID : TC650DaDEN_04 : Verify that the 'OS' combobox contains '-Select-', 'Android', 'iOS', 'Windows Phone', 'Windows', 'Linux'  and 'Other' items");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized productS" privilege.
			1. Log into DTS portal as above DTS admin user
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			VP: The 650Da Device Edit - New page is displayed
			4. List out all items of "OS" combobox
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// VP: The 650Da Device Edit - New page is displayed
		Assert.assertEquals(home.existsElement(DeviceEdit.getHash()).getResult(), "Pass");
		// 4. List out all items of "OS" combobox
		ArrayList<String> options = home.getAllOptionsInDropDown(DeviceEdit.OS);
		/*
		 * The "OS" combobox contains 
		 * "-Select-", 
		 * "Android", 
		 * "iOS", 
		 * "Windows Phone", 
		 * "Windows", 
		 * "Linux"  and 
		 * "Other" items
		 */
		Assert.assertTrue(DTSUtil.containsAll(options, DeviceEdit.os));
	}
	
	/*
	 * Verify that the "Audio Routes" combobox contains items properly.
	 */
	@Test
	public void TC650DaDEN_05(){
		result.addLog("ID : TC650DaDEN_05 : Verify that the 'Audio Routes' combobox contains items properly.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			VP: The 650Da Device Edit - New page is displayed
			4. List out all items of "Audio Route" combobox in Custom Tuing module.
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// VP: The 650Da Device Edit - New page is displayed
		Assert.assertEquals(home.existsElement(DeviceEdit.getHash()).getResult(), "Pass");
		// 4. List out all items of "Audio Route" combobox in Custom Tuing module.
		home.clickOptionByIndex(DeviceEdit.AUDIO_ROUTE_TYPE, 1);
		ArrayList<String> audioRoutes = home.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE);
		ArrayList<String> standardAccessories = home.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE);
		/*
		 * Verify that the 'Audio Routes' combobox contains items properly
		 */
		Assert.assertTrue(DTSUtil.containsAll(audioRoutes, DeviceEdit.routes));
		Assert.assertTrue(DTSUtil.containsAll(standardAccessories, DeviceEdit.Standard_Accessories));
	}
	
	/*
	 * Verify that the product name is trimmed for white spaces. 
	 */
	@Test
	public void TC650DaDEN_06(){
		result.addLog("ID : TC650DaDEN_06 : Verify that the product name is trimmed for white spaces");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized productS" privilege.
			1. Log into DTS portal as above DTS admin user
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Fill valid values into all requires fields except "Name" field
			5. Type characters into "Name" field which including some leading and trailing white spaces.
			6. Click "Save" link
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid values into all requires fields
		// 5. Type characters into "Name" field which including some leading and trailing white spaces.
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.appDeviceDraft();
		String deviceName = data.get("name");
		data.put("name", " " + deviceName + " ");
		home.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * Verify that The leading and trailing white spaces of "Name" field are trimmed
		 */
		Assert.assertEquals(home.getTextByXpath(DeviceInfo.NAME), deviceName);
		// Delete device
		home.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the DCC Serect is generated and displayed as read only when adding new device.
	 */
	@Test
	public void TC650DaDEN_07(){
		result.addLog("ID : TC650DaDEN_07 : Verify that the DCC Serect is generated and displayed as read only when adding new device.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized productS" privilege.

			1. Log into DTS portal as above DTS admin user
			2. Navgate to "Apps & Devices" page
			3. Click "Add App or Device" link
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * The DCC Serect in "Headphone:X Licensing Info" section is generated and displayed as Read Only.
		 */
		String dccSecret = home.getTextByXpath(DeviceEdit.DCC_SECRET);
		Boolean canEdit = home.canEdit(DeviceEdit.DCC_SECRET);
		System.out.println("DCC Secret : "+ dccSecret);
		Assert.assertTrue(!canEdit && dccSecret.length() > 0);
	}
	
	/*
	 * Verify that the "Audio Routes" module includes "Default Audio Route" and "Custom Tuning" sections when adding new devices.
	 */
	@Test
	public void TC650DaDEN_08(){
		result.addLog("ID : TC650DaDEN_08 : Verify that the 'Audio Routes' module includes 'Default Audio Route' and 'Custom Tuning' sections when adding new devices.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized productS" privilege.

			1. Log into DTS portal as above DTS admin user
			2. Navgate to "Apps & Devices" page
			3. Click "Add App or Device" link
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * "Audio Routes" module includes "Default Audio Route" and "Custom Tuning" sections
		 */
		Assert.assertTrue(home.checkAllStringPresent(DeviceEdit.Audio_Routes));
	}
	
	/*
	 * Verify that the invalid "Default Audio Route" file could not be uploaded.
	 */
	@Test
	public void TC650DaDEN_10(){
		result.addLog("ID : TC650DaDEN_10 : Verify that the invalid 'Default Audio Route' file could not be uploaded");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Try to upload invalid audio route tuning file in "Default Audio Route" module
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Try to upload invalid audio route tuning file in "Default Audio Route" module
		home.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, Constant.tuning_hpxtt);
		/*
		 * An error message is displayed and the invalid audio route tuning file is not added.
		 */
		Assert.assertEquals(home.getTextByXpath(DeviceEdit.AUDIO_ROUTE_UPLOAD_MESSAGE), "! Invalid file");
	}
	
	/*
	 * Verify that the "Custom Tuning" file is optional when adding new device
	 */
	@Test
	public void TC650DaDEN_11(){
		result.addLog("ID : TC650DaDEN_11 : Verify that the 'Custom Tuning' file is optional when adding new device");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Fill, select and upload valid value into all required fields
			5. Do not upload custom tuning file in "Custom Tuning" section
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill, select and upload valid value into all required fields
		// 5. Do not upload custom tuning file in "Custom Tuning" section
		Hashtable<String,String> data = TestData.appDeviceDraft();
		ArrayList<String> deviceInfo = home.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * Verify that New device is added successfully
		 */
		Assert.assertTrue(DTSUtil.containsAll(data, deviceInfo));
		// Delete device
		home.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the Default Audio Route file is limited by 1 when adding new device.
	 */
	@Test
	public void TC650DaDEN_12(){
		result.addLog("ID : TC650DaDEN_12 : Verify that the Default Audio Route file is limited by 1 when adding new device.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Select "Choose File" button in "Default Audio Route" section
			5. Upload valid audio route tuning file successfully
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Select "Choose File" button in "Default Audio Route" section
		// 5. Upload valid audio route tuning file successfully
		home.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, Constant.AUDIO_ROUTE_FILE);
		/*
		 * Verify that The default Audio Route box is disappeared and user can not upload another audio route file
		 */
		Assert.assertFalse(home.isElementPresent(DeviceEdit.ADD_AUDIO_ROUTE_CONTAINER));
	}
	
	/*
	 * Verify that the delete confirmation dialog with "Delete" and "Cancel" button is displayed when deleting a default audio route file.
	 */
	@Test
	public void TC650DaDEN_13(){
		result.addLog("ID : TC650DaDEN_13 : Verify that the delete confirmation dialog with 'Delete' and 'Cancel' button is displayed when deleting a default audio route file.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Select "Choose File" button in "Default Audio Route" section
			5. Upload valid audio route tuning file successfully
			VP: The "Choose File" button is disappeared
			6. Click on trash-can icon to delete the uploaded file
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Select "Choose File" button in "Default Audio Route" section
		// 5. Upload valid audio route tuning file successfully
		home.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, Constant.AUDIO_ROUTE_FILE);
		/*
		 * VP: The "Choose File" button is disappeared
		 */
		Assert.assertFalse(home.isElementPresent(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE));
		// 6. Click on trash-can icon to delete the uploaded file
		home.click(DeviceEdit.DELETE_AUDIO_ROUTE_ICON);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		/*
		 * Verify that Delete confirmation dialog with "Delete" and "Cancel" button is displayed 
		 */
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_CANCEL), "Cancel");
	}
	
	/*
	 * Verify that the default audio route file could be deleted successfully.
	 */
	@Test
	public void TC650DaDEN_14(){
		result.addLog("ID : TC650DaDEN_14 : Verify that the default audio route file could be deleted successfully.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Select "Choose File" button in "Default Audio Route" section
			5. Upload valid audio route tuning file successfully
			VP: The "Choose File" button is disappeared
			6. Click on trashcan icon to delete the uploaded file
			7. Click "Delete" button in delete confirmation dialog
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Select "Choose File" button in "Default Audio Route" section
		// 5. Upload valid audio route tuning file successfully
		home.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, Constant.AUDIO_ROUTE_FILE);
		/*
		 * VP: The "Choose File" button is disappeared
		 */
		Assert.assertFalse(home.isElementPresent(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE));
		// 6. Click on trash-can icon to delete the uploaded file
		// 7. Click "Delete" button in delete confirmation dialog
		home.doDelete(DeviceEdit.DELETE_AUDIO_ROUTE_ICON);
		/*
		 * Verify that The default uploaded audio route is deleted successfully
		 */
		Assert.assertTrue(home.isElementPresent(DeviceEdit.ADD_AUDIO_ROUTE_CONTAINER));
	}
	
	/*
	 * Verify that the default audio route file is not deleted when clicking "Cancel" in delete confirmation dialog.
	 */
	@Test
	public void TC650DaDEN_15(){
		result.addLog("ID : TC650DaDEN_15 : Verify that the default audio route file is not deleted when clicking 'Cancel' in delete confirmation dialog.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Select "Choose File" button in "Default Audio Route" section
			5. Upload valid audio route tuning file successfully
			VP: The "Choose File" button is disappeared
			6. Click on trashcan icon to delete the uploaded file
			7. Click "Cancel" button in delete confirmation dialog
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Select "Choose File" button in "Default Audio Route" section
		// 5. Upload valid audio route tuning file successfully
		home.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, Constant.AUDIO_ROUTE_FILE);
		/*
		 * VP: The "Choose File" button is disappeared
		 */
		Assert.assertFalse(home.isElementPresent(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE));
		// 6. Click on trashcan icon to delete the uploaded file
		home.click(DeviceEdit.DELETE_AUDIO_ROUTE_ICON);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		// 7. Click "Cancel" button in delete confirmation dialog
		home.click(Xpath.BTN_CONFIRMATION_CANCEL);
		/*
		 * Verify that The default uploaded audio route is not deleted
		 */
		Assert.assertNotNull(home.getTextByXpath(DeviceEdit.UPLOADED_DEFAULT_AUDIO_ROUTE));
	}
	
	/*
	 * Verify that the file upload box is displayed again after deleting default audio route.
	 */
	@Test
	public void TC650DaDEN_16(){
		result.addLog("ID : TC650DaDEN_16 : Verify that the file upload box is displayed again after deleting default audio route.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Select "Choose File" button in "Default Audio Route" section
			5. Upload valid audio route tuning file successfully
			VP: The "Choose File" button is disappeared
			6. Click on trashcan icon to delete the uploaded file successfully
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Select "Choose File" button in "Default Audio Route" section
		// 5. Upload valid audio route tuning file successfully
		home.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, Constant.AUDIO_ROUTE_FILE);
		/*
		 * VP: The "Choose File" button is disappeared
		 */
		Assert.assertFalse(home.isElementPresent(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE));
		// 6. Click on trashcan icon to delete the uploaded file successfully
		home.doDelete(DeviceEdit.DELETE_AUDIO_ROUTE_ICON);
		/*
		 * Verify that The file upload box is displayed again
		 */
		Assert.assertTrue(home.isElementPresent(DeviceEdit.ADD_AUDIO_ROUTE_CONTAINER));
	}
	
	/*
	 * Verify that the new Default Audio Route file could be uploaded successfully after deleting the existing file.
	 */
	@Test
	public void TC650DaDEN_17(){
		result.addLog("ID : TC650DaDEN_17 : Verify that the new Default Audio Route file could be uploaded successfully after deleting the existing file.");
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
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Select "Choose File" button in "Default Audio Route" section
		// 5. Upload valid audio route tuning file successfully
		home.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, Constant.AUDIO_ROUTE_FILE);
		/*
		 * VP: The "Choose File" button is disappeared
		 */
		Assert.assertFalse(home.isElementPresent(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE));
		// 6. Click on trashcan icon to delete the uploaded file successfully
		home.doDelete(DeviceEdit.DELETE_AUDIO_ROUTE_ICON);
		/*
		 * VP: The file upload box is displayed again	
		 */
		Assert.assertTrue(home.isElementPresent(DeviceEdit.ADD_AUDIO_ROUTE_CONTAINER));
		// 7. Try to upload another default audio route file
		home.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, Constant.AUDIO_ROUTE_FILE);
		/*
		 * Verify that The new default audio route file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath(DeviceEdit.UPLOADED_DEFAULT_AUDIO_ROUTE).contains("Default Audio"));
	}
	
	/*
	 * Verify that the custom uploaded tuning file is displayed above the Custom Tuning module with a delete icon after uploaded.
	 */
	@Test
	public void TC650DaDEN_18(){
		result.addLog("ID : TC650DaDEN_18 : Verify that the custom uploaded tuning file is displayed above the Custom Tuning module with a delete icon after uploaded.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Select "Choose File" button in "Custom Tuning" section
			5. Upload valid a tuning file successfully
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Select "Choose File" button in "Custom Tuning" section
		// 5. Upload valid a tuning file successfully
		home.clickOptionByIndex(DeviceEdit.AUDIO_ROUTE_TYPE, 1);
		home.clickOptionByIndex(DeviceEdit.AUDIO_ROUTE, 1);
		home.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, Constant.AUDIO_ROUTE_FILE);
		/*
		 * The custom uploaded tuning file is displayed with a delete icon above the Custom Tuning module
		 */
		Assert.assertTrue(home.getAtribute(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(Constant.AUDIO_ROUTE_FILE.replaceAll(".dtscs", "")));
		Assert.assertTrue(home.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
	}
	
	/*
	 * Verify that the delete confirmation dialog with "Delete" and "Cancel" buttons are displayed when deleteing custom tuning file
	 */
	@Test
	public void TC650DaDEN_19(){
		result.addLog("ID : TC650DaDEN_19 : Verify that the delete confirmation dialog with 'Delete' and 'Cancel' buttons are displayed when deleteing custom tuning file");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Select "Choose File" button in "Custom Tuning" section
			5. Upload valid a tuning file successfully
			VP: The custom uploaded tuning file is displayed with a delete icon above the Custom Tuning module
			6. Click on trashcan icon
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Select "Choose File" button in "Custom Tuning" section
		// 5. Upload valid a tuning file successfully
		home.clickOptionByIndex(DeviceEdit.AUDIO_ROUTE_TYPE, 1);
		home.clickOptionByIndex(DeviceEdit.AUDIO_ROUTE, 1);
		home.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, Constant.AUDIO_ROUTE_FILE);
		/*
		 * VP: The custom uploaded tuning file is displayed with a delete icon above the Custom Tuning module
		 */
		Assert.assertTrue(home.getAtribute(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(Constant.AUDIO_ROUTE_FILE.replaceAll(".dtscs", "")));
		Assert.assertTrue(home.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		// 6. Click on trashcan icon
		home.click(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		/*
		 * The delete confirmation dialog with "Delete" and "Cancel" buttons are displayed
		 */
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_CANCEL), "Cancel");
	}
	
	/*
	 * Verify that the custom tuning file could be deleted successfully.
	 */
	@Test
	public void TC650DaDEN_20(){
		result.addLog("ID : TC650DaDEN_20 : Verify that the custom tuning file could be deleted successfully.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized productS" privilege.

			1. Log into DTS portal as above DTS admin user
			2. Navgate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Select "Choose File" button in "Custom Tuning" section
			5. Upload valid a tuning file successfully
			VP: The custom uploaded tuning file is displayed with a delete icon above the Custom Tuning module
			6. Click on trashcan icon
			7. Click "Delete" button in delete confirmation dialog
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Select "Choose File" button in "Custom Tuning" section
		// 5. Upload valid a tuning file successfully
		home.clickOptionByIndex(DeviceEdit.AUDIO_ROUTE_TYPE, 1);
		home.clickOptionByIndex(DeviceEdit.AUDIO_ROUTE, 1);
		home.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, Constant.AUDIO_ROUTE_FILE);
		/*
		 * VP: The custom uploaded tuning file is displayed with a delete icon above the Custom Tuning module
		 */
		Assert.assertTrue(home.getAtribute(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(Constant.AUDIO_ROUTE_FILE.replaceAll(".dtscs", "")));
		Assert.assertTrue(home.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		// 6. Click on trashcan icon
		// 7. Click "Delete" button in delete confirmation dialog
		home.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
		/*
		 * Verify that The Custom tuning is deleted successfully
		 */
		Assert.assertEquals(home.getTextByXpath(DeviceEdit.ANOTHER_ROUTE), "");
	}
	
	/*
	 * Verify that user can download custom tuning file when clicking on tuning link.
	 */
	@Test
	public void TC650DaDEN_21(){
		result.addLog("ID : TC650DaDEN_21 : Verify that user can download custom tuning file when clicking on tuning link.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Select "Choose File" button in "Custom Tuning" section
			5. Upload valid a tuning file successfully
			6. Click on uploaded tuning link
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Select "Choose File" button in "Custom Tuning" section
		// 5. Upload valid a tuning file successfully
		home.clickOptionByIndex(DeviceEdit.AUDIO_ROUTE_TYPE, 1);
		home.clickOptionByIndex(DeviceEdit.AUDIO_ROUTE, 1);
		home.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, Constant.AUDIO_ROUTE_FILE);
		// 6. Click on uploaded tuning link
		Boolean isDownloaded = home.downloadFile(Constant.AUDIO_ROUTE_FILE);
		/*
		 * Verify that The download file dialog is displayed and user can download tuning file successfully
		 */
		Assert.assertTrue(isDownloaded);
	}
	
	/*
	 * Verify that the "Promo Slots Avaiable" is "6" and the "Global Promotions" is "ON" by default when adding new device.
	 */
	@Test
	public void TC650DaDEN_22(){
		result.addLog("ID : TC650DaDEN_22 : Verify that the 'Promo Slots Avaiable' is '6' and the 'Global Promotions' is 'ON' by default when adding new device.");
		/*
			Pre-Condition: the DTS user has "Add and manage authorized productS" privilege.

			1. Log into DTS portal as above DTS admin user
			2. Navgate to "Apps & Devices" page
			3. Click "Add App or Device" link
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * The  "Promo Slots Avaiable" is "6" and the 
		 * "Global Promotions" is "ON" by default in "Featured Accessory Promotions" module.
		 */
		String slots = home.getAtribute(DeviceEdit.PROMO_SLOT, "value");
		Assert.assertEquals("6", slots);
		
		String status = home.getStatuSwitch(DeviceEdit.GLOBAL_PROMOTIONS_SWITCH);
		Assert.assertEquals(status, "ON");
	}
	
	/*
	 * Verify that the number of "Device Promotions" field displays based on number of "Promo Slot Available".
	 */
	@Test
	public void TC650DaDEN_23(){
		result.addLog("ID : TC650DaDEN_23 : Verify that the number of 'Device Promotions' field displays based on number of 'Promo Slot Available'.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized productS" privilege.

			1. Log into DTS portal as above DTS admin user
			2. Navgate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Increase the number of "Promo Slot Available" to 7
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Increase the number of "Promo Slot Available" to 7
		home.editData(DeviceEdit.PROMO_SLOT, "7");
		home.AutoItX.send("{ENTER}", false);
		/*
		 * The "Device Promotions" diplays 7 fields.
		 */
		Assert.assertEquals(home.getPromotionContainerIndex(DeviceEdit.PROMOTION_CONTAINER), 7);
	}
	
	/*
	 * Verify that the new product could be saved successfully.
	 */
	@Test
	public void TC650DaDEN_24(){
		result.addLog("ID : TC650DaDEN_24 : Verify that the new product could be saved successfully");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Fill valid values into all requires fields
			5. Click "Save" link
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid values into all requires fields
		// 5. Click "Save" link
		Hashtable<String,String> data = TestData.appDeviceDraft();
		ArrayList<String> deviceInfo = home.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * Verify that The portal is redirected to 640D Device Detail page and new product is saved successfully with correct information
		 */
		Assert.assertTrue(DTSUtil.containsAll(data, deviceInfo));
		// Delete device
		home.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the portal is redirected to 630D Devices List when canceling adding new product.
	 */
	@Test
	public void TC650DaDEN_25(){
		result.addLog("ID : TC650DaDEN_25 : Verify that the portal is redirected to 630D Devices List when canceling adding new product.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Fill valid values into all requires fields
			5. Click "Cancel" link
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid values into all requires fields
		Hashtable<String,String> data = TestData.appDeviceDraft();
		data.remove("save");
		home.createNewDevice(DeviceEdit.getHash(), data);
		// 5. Click "Cancel" link
		home.click(DeviceEdit.CANCEL);
		/*
		 * Verify that The portal is redirected to 630D Devices List page and new product is not added
		 */
		Assert.assertEquals(home.existsElement(DeviceList.getListXpath()).getResult(), "Pass");
	}
	
	/*
	 * Verify that items in audio route combobox is removed after uploading the custom tuning file.
	 */
	@Test
	public void TC650DaDEN_26(){
		result.addLog("ID : TC650DaDEN_26 : Verify that items in audio route combobox is removed after uploading the custom tuning file.");
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
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid values into all requires fields
		// 5. Select an item in audio route combobox
		home.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes[3]);
		// 6. Upload a tuning file for above audio route item successfully
		home.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, Constant.AUDIO_ROUTE_FILE);
		// 7. List out the audio route combobox
		ArrayList<String> options = home.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE);
		/*
		 * Verify that The audio route item of uploaded tuning file is removed from audio route combobox
		 */
		Assert.assertTrue(!options.contains(DeviceEdit.routes[3]));
	}
	
	/*
	 * Verify that the Non partner is not displayed in Company dropdown.
	 */
	@Test
	public void TC650DaDEN_27(){
		result.addLog("ID : TC650DaDEN_27 : Verify that the Non partner is not displayed in Company dropdown");
		/*
		 	Pre-Condition: a new non partner is created successfully

			1. Log into DTS portal as DTS admin
			2. Navgate to "Apps & Devices" page
			3. Click "Add App or Device" link
			VP: The 650Da Device Edit Page - New page is displayed
			4. List out the Company dropdown
		*/
		// get all Non-Partner 
			// Navigate Companies
			home.click(Xpath.LINK_COMPANY);
			home.selectFilterByName(Companies.FILTER, "Non-Partners");
			ArrayList<String> nonPartner = home.getColumsByIndex(1);
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link 
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. List out the Company dropdown
		home.click(DeviceEdit.COMPANY);
		ArrayList<String> listCompany = home.getAllOption(DeviceEdit.COMPANY);
		listCompany.retainAll(nonPartner);
		if(listCompany.size() > 0){
			System.err.println("Non-partner displayed : ");
			for (String item : listCompany) {
				System.err.println("\t"+item);
			}
		}
		Assert.assertTrue(listCompany.size() == 0);
	}
	
	/*
	 * Verify that the promotion slots is validated when adding manually
	 */
	@Test
	public void TC650DaDEN_28(){
		result.addLog("ID : TC650DaDEN_28 : Verify that the promotion slots is validated when adding manually");
		/*
		 	1. Log into DTS portal as a DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			VP: Verify that the 650Da Device New page is displayed
			4. Fill valid value into all required fields
			5. Add a global promotion ID which is not valid to a promotion slot
			VP: The error message “Promotion does not exist” is displayed
			6. Add a global promotion ID which is not published yet to a promotion slot
			VP: The error message “Promotion is not published yet” is displayed
			7. Add a global promotion ID which is published to another promotion slot
			VP: Name of global promotion is displayed next to promotion ID and there is no error message displayed
			8. Add the same global promotion ID which is published to another promotion slot
			VP: The error message “Promotion is duplicate” is displayed
			9. Add published device promotion ID which target is assigned to another app/device to another promotion slot
		*/
		/*
		 * PreCondition: Create new global promotion
		 */
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create 1st published product
		Hashtable<String,String> dataProduct1 = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct1);
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create 2nd published product
		Hashtable<String,String> dataProduct2 = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct2);
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create 3rd published product
		Hashtable<String,String> dataProduct3 = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct3);
		// Navigate to Promotion page
		home.click(Xpath.linkPromotions);
		// Click Add promo link
		home.click(PromotionList.ADD_PROMO);
		// Create 1st Global promotion
		Hashtable<String,String> dataGlobalPromotion1 = TestData.promotionGlobal(dataProduct1.get("brand") + " " + dataProduct1.get("name"));
		home.addPromotion(AddPromotion.getHash(), dataGlobalPromotion1);
		// Get promotion ID
		String promotionID1 = home.getTextByXpath(PromotionInfo.DTS_ID);
		// Navigate to Promotion page
		home.click(Xpath.linkPromotions);
		// Click Add promo link
		home.click(PromotionList.ADD_PROMO);
		// Create 2nd Global promotion
		Hashtable<String,String> dataGlobalPromotion2 = TestData.promotionGlobal(dataProduct2.get("brand") + " " + dataProduct2.get("name"));
		home.addPromotion(AddPromotion.getHash(), dataGlobalPromotion2);
		// Publish promotion
		home.click(PromotionInfo.PUBLISH);
		// Get promotion ID
		String promotionID2 = home.getTextByXpath(PromotionInfo.DTS_ID);
		// Navigate to App & Devices page
		home.click(Xpath.linkDevice);
		// Click Add app or device link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> dataDevice1 = TestData.appDeviceDraft();
		home.createNewDevice(DeviceEdit.getHash(), dataDevice1);
		// Navigate to Promotion page
		home.click(Xpath.linkPromotions);
		// Click Add promo link
		home.click(PromotionList.ADD_PROMO);
		// Create new Device promotion
		Hashtable<String,String> dataDevicePromotion = TestData.promotionAppDevice(dataProduct3.get("brand") + " " + dataProduct3.get("name"), dataDevice1.get("brand"), dataDevice1.get("name"));
		home.addPromotion(AddPromotion.getHash(), dataDevicePromotion);
		// Publish promotion
		home.click(PromotionInfo.PUBLISH);
		// Get promotion ID
		String promotionID3 = home.getTextByXpath(PromotionInfo.DTS_ID);
		/*
		 * *******************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.linkDevice);
		// 3. Click "Add App or Device" link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: Verify that the 650Da Device New page is displayed
		 */
		Assert.assertEquals(home.existsElement(DeviceEdit.getHash()).getResult(), "Pass");
		// 4. Fill valid value into all required fields
		Hashtable<String,String> dataDevice2 = TestData.appDeviceDraft();
		dataDevice2.remove("save");
		home.createNewDevice(DeviceEdit.getHash(), dataDevice2);
		// 5. Add a global promotion ID which is not valid to a promotion slot
		ArrayList<PromotionPackage> packages = home.getPromotionPackage(DeviceEdit.PROMOTION_CONTAINER);
		home.editData(packages.get(0).Txt_PromotionID, RandomStringUtils.randomAlphanumeric(20));
		home.click(DeviceEdit.SAVE);
		/*
		 * VP: The error message “Promotion does not exist” is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(packages.get(0).warningMessage), PromotionPackage.MESSAGE[0]);
		// 6. Add a global promotion ID which is not published yet to a promotion slot
		home.editData(packages.get(0).Txt_PromotionID, promotionID1);
		/*
		 * VP: The error message “Promotion is not published yet” is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(packages.get(0).warningMessage), PromotionPackage.MESSAGE[1]);
		// 7. Add a global promotion ID which is published to another promotion slot
		home.editData(packages.get(0).Txt_PromotionID, promotionID2);
		/*
		 * VP: Name of global promotion is displayed next to promotion ID and there is no error message displayed
		 */
		Assert.assertTrue(home.getTextByXpath(packages.get(0).promotionName).contains(dataGlobalPromotion2.get("name").replace(" ", " - ")));
		Assert.assertEquals(home.getTextByXpath(packages.get(0).warningMessage), "");
		// 8. Add the same global promotion ID which is published to another promotion slot
		home.editData(packages.get(1).Txt_PromotionID, promotionID2);
		home.click(DeviceEdit.SAVE);
		/*
		 * VP: The error message “Promotion is duplicate” is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(packages.get(1).warningMessage), PromotionPackage.MESSAGE[2]);
		// 9. Add published device promotion ID which target is assigned to another app/device to another promotion slot
		home.editData(packages.get(1).Txt_PromotionID, promotionID3);
		/*
		 * The error message “Promotion doesn't apply for this device” is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(packages.get(1).warningMessage), PromotionPackage.MESSAGE[3]);
		// Delete product
		home.deleteAnaccessorybyName(dataProduct1.get("name"));
		home.deleteAnaccessorybyName(dataProduct2.get("name"));
		home.deleteAnaccessorybyName(dataProduct3.get("name"));
	}
	
	/*
	 * Verify that the limitation of “Promotion Slots Available” is from 0 to 25
	 */
	@Test
	public void TC650DaDEN_29(){
		result.addLog("ID : TC650DaDEN_29 : Verify that the limitation of  “Promotion Slots Available” is from 0 to 25");
		/*
		 	1. Log into DTS portal as a DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			VP: Verify that the 650Da Device New page is displayed
			4. Fill valid value into all required fields
			5. Try to add negative numbers into “Promotion Slots Available” field 
			VP: It's unable to add add negative numbers
			6. Set the “Promotion Slots Available” to 0
			VP: Value 0 could be added
			7. Click “Save” link
			VP: Verify that new app/device is created successfully
			8. Click “Edit” link
			9. Try to change the “Promotion Slots Available” to 26
			VP: It's unable to add value 26
			10. Change the “Promotion Slots Available” to 25
			VP: value 25 is added successfully and there are 25 promotion slots are displayed
			11. Click “Save” link
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.linkDevice);
		// 3. Click "Add App or Device" link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: Verify that the 650Da Device New page is displayed
		 */
		Assert.assertEquals(home.existsElement(DeviceEdit.getHash()).getResult(), "Pass");
		// 4. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.appDeviceDraft();
		data.remove("save");
		home.createNewDevice(DeviceEdit.getHash(), data);
		// 5. Try to add negative numbers into “Promotion Slots Available” field 
		home.editData(DeviceEdit.PROMO_SLOT, "-1");
		home.AutoItX.send("{ENTER}", false);
		/*
		 * VP: It's unable to add add negative numbers
		 */
		String promoSlot = home.getAtribute(DeviceEdit.PROMO_SLOT, "value");
		Assert.assertNotEquals(promoSlot, "-1");
		// 6. Set the “Promotion Slots Available” to 0
		home.editData(DeviceEdit.PROMO_SLOT, "0");
		home.AutoItX.send("{ENTER}", false);
		/*
		 * VP: Value 0 could be added
		 */
		Assert.assertEquals(home.getPromotionContainerIndex(DeviceEdit.PROMOTION_CONTAINER), 0);
		// 7. Click “Save” link
		home.click(DeviceEdit.SAVE);
		// Check if PDPP-1186 found
		if(home.checkMessageDisplay("Internal Server Error")){
			result.addErrorLog("PDPP-1186 - 650Db Product Edit: Error message \"Internal Server Error\" is displayed when saving app/device with the Promotions Slots Available is set to 0");
			Assert.assertTrue(false);
		}
		/*
		 * VP: Verify that new app/device is created successfully
		 */
		Assert.assertEquals(home.getTextByXpath(DeviceInfo.NAME), data.get("name"));
		// 8. Click “Edit” link
		home.click(DeviceInfo.EDIT);
		// 9. Try to change the “Promotion Slots Available” to 26
		home.editData(DeviceEdit.PROMO_SLOT, "26");
		home.AutoItX.send("{ENTER}", false);
		
	}
}
