package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.xalan.xsltc.compiler.sym;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AudioRoutes;
import com.dts.adminportal.model.AudioRoutesEdit;
import com.dts.adminportal.model.AudioRoutesInfo;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AddPromotion;
import com.dts.adminportal.model.Companies;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.DeviceEdit;
import com.dts.adminportal.model.DeviceInfo;
import com.dts.adminportal.model.DeviceList;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PromotionInfo;
import com.dts.adminportal.model.PromotionList;
import com.dts.adminportal.model.PromotionPackage;
import com.dts.adminportal.model.RoomModelEdit;
import com.dts.adminportal.model.RoomModelInfo;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.TestData;
import com.gargoylesoftware.htmlunit.Page;
import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;

public class DTSAppsDevices650DDeviceEdit extends BasePage{

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
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
		Assert.assertEquals("Product", appDeviceControl.getTextByXpath(DeviceEdit.TITLE_DEFAULT));
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
		appDeviceControl.clickOptionByIndex(DeviceEdit.AUDIO_ROUTE_TYPE, 1);
		ArrayList<String> audioRoutes = appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE);
		ArrayList<String> standardAccessories = appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE);
		/*
		 *VP: The ""Audio Route"" combobox contains ""-Select-"", ""Over-Ear Headphones (Line-out0)"", 
				""Earbuds (Line-out0)"", ""Earpiece (Line-out0)"", ""Car Audio (Line-out0)"", 
				""External Speakers (Line-out0)"",""Other product (Line-out0)"", ""Attached0 (Portail)"", 
				""Attached1 (Landscape)"", ""Attached2 (Interal Earpiece)""  and ""Combo0 (Duo Audio)"" items 
		 */
		Assert.assertTrue(ListUtil.containsAll(audioRoutes, DeviceEdit.routes));
		Assert.assertTrue(ListUtil.containsAll(standardAccessories, DeviceEdit.Standard_Accessories));
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
		appDeviceControl.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
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
		// 4. Select "Choose File" button in "Custom Tuning" section
		// 5. Upload valid a tuning file successfully
		appDeviceControl.clickOptionByIndex(DeviceEdit.AUDIO_ROUTE_TYPE, 1);
		appDeviceControl.clickOptionByIndex(DeviceEdit.AUDIO_ROUTE, 1);
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones.getName());
		/*
		 * The custom uploaded tuning file is displayed with a delete icon above the Custom Tuning module
		 */
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Over_Ear_Headphones.getName().replaceAll(".dtscs", "")));
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
		// 4. Select "Choose File" button in "Custom Tuning" section
		// 5. Upload valid a tuning file successfully
		appDeviceControl.clickOptionByIndex(DeviceEdit.AUDIO_ROUTE_TYPE, 1);
		appDeviceControl.clickOptionByIndex(DeviceEdit.AUDIO_ROUTE, 1);
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones.getName());
		/*
		 * VP: The custom uploaded tuning file is displayed with a delete icon above the Custom Tuning module
		 */
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Over_Ear_Headphones.getName().replaceAll(".dtscs", "")));
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
		appDeviceControl.clickOptionByIndex(DeviceEdit.AUDIO_ROUTE_TYPE, 1);
		appDeviceControl.clickOptionByIndex(DeviceEdit.AUDIO_ROUTE, 1);
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones.getName());
		// 6. Click on uploaded tuning link
		Boolean isDownloaded = appDeviceControl.downloadFile(AddEditProductModel.FileUpload.Over_Ear_Headphones.getName());
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
	public void TC650DaDEN_23(){
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
		// 4. Increase the number of "Promo Slot Available" to 7
		appDeviceControl.editData(DeviceEdit.PROMO_SLOT, "7");
		appDeviceControl.autoTool.send("{ENTER}",false);
		/*
		 * The "Device Promotions" diplays 7 fields.
		 */
		Assert.assertEquals(promotionControl.getTotalPromotionContainers(DeviceEdit.PROMOTION_CONTAINER), 7);
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
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes[3]);
		// 6. Upload a tuning file for above audio route item successfully
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached0_Internal_Speakers_Default.getName());
		// 7. List out the audio route combobox
		ArrayList<String> options = appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE);
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
			VP: The error message ï¿½Promotion does not existï¿½ is displayed
			6. Add a global promotion ID which is not published yet to a promotion slot
			VP: The error message ï¿½Promotion is not published yetï¿½ is displayed
			7. Add a gl
			obal promotion ID which is published to another promotion slot
			VP: Name of global promotion is displayed next to promotion ID and there is no error message displayed
			8. Add the same global promotion ID which is published to another promotion slot
			VP: The error message ï¿½Promotion is duplicateï¿½ is displayed
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
		ArrayList<PromotionPackage> packages = promotionControl.getPromotionPackage(DeviceEdit.PROMOTION_CONTAINER);
		appDeviceControl.editData(packages.get(0).Txt_PromotionID, RandomStringUtils.randomAlphanumeric(20));
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The error message ï¿½Promotion does not existï¿½ is displayed
		 */
		Assert.assertEquals(appDeviceControl.getElementText(packages.get(0).warningMessage), PromotionPackage.MESSAGE[0]);
		// 6. Add a global promotion ID which is not published yet to a promotion slot
		appDeviceControl.editData(packages.get(0).Txt_PromotionID, promotionID1);
		/*
		 * VP: The error message ï¿½Promotion is not published yetï¿½ is displayed
		 */
		Assert.assertEquals(appDeviceControl.getElementText(packages.get(0).warningMessage), PromotionPackage.MESSAGE[1]);
		// 7. Add a global promotion ID which is published to another promotion slot
		appDeviceControl.editData(packages.get(0).Txt_PromotionID, promotionID2);
		/*
		 * VP: Name of global promotion is displayed next to promotion ID and there is no error message displayed
		 */
		Assert.assertTrue(appDeviceControl.getElementText(packages.get(0).promotionName).contains(dataGlobalPromotion2.get("name").replace(" ", " - ")));
		Assert.assertEquals(appDeviceControl.getElementText(packages.get(0).warningMessage), "");
		// 8. Add the same global promotion ID which is published to another promotion slot
		appDeviceControl.editData(packages.get(1).Txt_PromotionID, promotionID2);
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The error message ï¿½Promotion is duplicateï¿½ is displayed
		 */
		Assert.assertEquals(appDeviceControl.getElementText(packages.get(1).warningMessage), PromotionPackage.MESSAGE[2]);
		// 9. Add published device promotion ID which target is assigned to another app/device to another promotion slot
		appDeviceControl.editData(packages.get(1).Txt_PromotionID, promotionID3);
		/*
		 * The error message ï¿½Promotion doesn't apply for this deviceï¿½ is displayed
		 */
		Assert.assertEquals(appDeviceControl.getElementText(packages.get(1).warningMessage), PromotionPackage.MESSAGE[3]);
		// Delete product
		productControl.deleteAccessorybyName(dataProduct1.get("name"));
		productControl.deleteAccessorybyName(dataProduct2.get("name"));
		productControl.deleteAccessorybyName(dataProduct3.get("name"));
	}
	
	/*
	 * Verify that the limitation of  “Promotion Slots Available” is from 0 to 25
	 */
	@Test
	public void TC650DaDEN_29(){
		appDeviceControl.addLog("ID : TC650DaDEN_29 : Verify that the limitation of  “Promotion Slots Available” is from 0 to 25");
		/*
		 	1. Log into DTS portal as a DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			VP: Verify that the 650Da Device New page is displayed
			4. Fill valid value into all required fields
			5. Try to add negative numbers into ï¿½Promotion Slots Availableï¿½ field 
			VP: It's unable to add add negative numbers
			6. Set the ï¿½Promotion Slots Availableï¿½ to 6
			VP: Value 6 could be added
			7. Click ï¿½Saveï¿½ link
			VP: Verify that new app/device is created successfully
			8. Click ï¿½Editï¿½ link
			9. Try to change the ï¿½Promotion Slots Availableï¿½ to 26
			VP: It's unable to add value 26
			10. Change the ï¿½Promotion Slots Availableï¿½ to 25
			VP: value 25 is added successfully and there are 25 promotion slots are displayed
			11. Click ï¿½Saveï¿½ link
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
		// 5. Try to add negative numbers into ï¿½Promotion Slots Availableï¿½ field 
		appDeviceControl.editData(DeviceEdit.PROMO_SLOT, "-1");
		appDeviceControl.autoTool.send("{ENTER}", false);
		/*
		 * VP: It's unable to add add negative numbers
		 * 
		 */
		String promoSlot = appDeviceControl.getAtributeValue(DeviceEdit.PROMO_SLOT, "value");
		Assert.assertNotEquals(promoSlot, "-1");
		// 6. Set the ï¿½Promotion Slots Availableï¿½ to 0
		appDeviceControl.editData(DeviceEdit.PROMO_SLOT, "6");
		appDeviceControl.autoTool.send("{ENTER}", false);
		/*
		 * VP: Value 6 could be added
		 */
		Assert.assertEquals(promotionControl.getTotalPromotionContainers(DeviceEdit.PROMOTION_CONTAINER), 6);
		// 7. Click ï¿½Saveï¿½ link
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
		// 8. Click ï¿½Editï¿½ link
		appDeviceControl.click(DeviceInfo.EDIT);
		// 9. Try to change the ï¿½Promotion Slots Availableï¿½ to 26
		appDeviceControl.editData(DeviceEdit.PROMO_SLOT, "26");
		appDeviceControl.autoTool.send("{ENTER}", false);
		/*
		 * VP: It's unable to add value 26
		 */
		Assert.assertEquals(promotionControl.getTotalPromotionContainers(DeviceEdit.PROMOTION_CONTAINER),25);
		//10. Change the ï¿½Promotion Slots Availableï¿½ to 25
		appDeviceControl.editData(DeviceEdit.PROMO_SLOT, "25");
		appDeviceControl.autoTool.send("{ENTER}", false);
		/*
		 * VP: value 25 is added successfully and there are 25 promotion slots are displayed  
		 */
		Assert.assertEquals(promotionControl.getTotalPromotionContainers(DeviceEdit.PROMOTION_CONTAINER),25);
		//11. Click ï¿½Saveï¿½ link
		appDeviceControl.click(DeviceEdit.SAVE);
		
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
		appDeviceControl.editData(DeviceEdit.BRAND_FILTER,"T");
		appDeviceControl.click(DeviceEdit.FIRST_BRANDS);
		// 7. Select "All brands"
		appDeviceControl.click(DeviceEdit.ALL_BRANDS);
		// VP: Any individual brands entered will become lost after save
		appDeviceControl.click(DeviceEdit.SAVE);
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.HEADPHONE_BRAND_LABEL));
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
			9. Repeat from step 2 to step 5
			10.Enter non-existent brand name in "Enter a brand name" field
			11. Click "Save" link
			VP: This app/device cannot save and show error "Brand is required" in "Headphone Brands" section.
			12.Enter valid brand names in "Enter a brand name" field
			13. Choose the brand names in auto suggest field
			14. Click “Save” link
			15.Click  "Edit Version" link
			16. Select "All brands in the DTS catalog" option in "Headphone Brands" section.
			VP: The brand name list is disabled in "Headphone Brands" section.
			17. Select "Only the following brands" option in "Headphone Brands" section.	
			VP: The brands name list is displayed and not disable in "Headphone Brands" section

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
		// 9. Repeat from step 2 to step 5
		// Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.linkDevice);
		// Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Fill valid value into all required fields
		Hashtable<String,String> data2 = TestData.appDeviceDraft();
		data2.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data2);
		// Select "Only the following brands"
		appDeviceControl.click(DeviceEdit.ONLY_BRANDS);
		// 10.Enter non-existent brand name in "Enter a brand name" field 
		appDeviceControl.editData(DeviceEdit.BRAND_FILTER,"Non-Existent-Brand");
		// 11. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: This app/device cannot save and show error "Brand is required" in "Headphone Brands" section.
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.HEADPHONE_BRAND_REQUIRE));
		// 12.Enter valid brand names in "Enter a brand name" field
		appDeviceControl.editData(DeviceEdit.BRAND_FILTER,PARTNER_BRAND_NAME_1);
		// 13. Choose the brand names in auto suggest field
		WebElement brand_filter = driver.findElement(By.xpath(DeviceEdit.BRAND_FILTER));
		brand_filter.sendKeys(Keys.PAGE_DOWN);
		appDeviceControl.autoTool.send("{ENTER}", false);
		// appDeviceControl.click(DeviceEdit.FIRST_BRANDS);
		// 14. Click “Save” link
		appDeviceControl.click(DeviceEdit.SAVE);
		// 15.Click  "Edit Version" link
		appDeviceControl.click(DeviceInfo.EDIT);
		// 16. Select "All brands in the DTS catalog" option in "Headphone Brands" section.
		appDeviceControl.click(DeviceEdit.ALL_BRANDS);
		/*
		 * VP: The brand name list is disabled in "Headphone Brands" section.
		 */
		appDeviceControl.click("//*[@id='icon-trash-1957']");
		Assert.assertTrue(appDeviceControl.isElementPresent("//*[@id='icon-trash-1957']"));
		//Assert.assertEquals(audioControl.getAtributeValue("//*[@id='icon-trash-1423']","disabled"),"true");
		//17. Select "Only the following brands" option in "Headphone Brands" section.
		appDeviceControl.click(DeviceEdit.ONLY_BRANDS);
		/*
		 * VP: The brands name list is displayed and not disable in "Headphone Brands" section
		 */
		appDeviceControl.click("//*[@id='icon-trash-1957']");
		Assert.assertFalse(appDeviceControl.isElementPresent("//*[@id='icon-trash-1957']"));
	}
	
	/*
	 * Verify that new promo slot is displayed correctly
	 */
	@Test
	public void TC650DaDEN_32(){
		appDeviceControl.addLog("ID : TC650DaDEN_32 : Verify that new promo slot is diaplayed correctly");
		/*
		 	1. Log into DTS portal as a DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			VP: The default promo sot is 6
			4. Change the “Promotion Slots Available” to 7
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
		appDeviceControl.click(PageHome.linkDevice);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// VP: The default promo sot is 6
		String default_slot = appDeviceControl.getAttributesOfElement(DeviceEdit.PROMO_SLOT,"value");
		Assert.assertEquals(default_slot,"6");
		// 4. Change the “Promotion Slots Available” to 7
		appDeviceControl.editData(DeviceEdit.PROMO_SLOT,"7");
		// VP: The new promo slot is added with label, input field and ""Delete"" link
		appDeviceControl.click(DeviceEdit.GLOBAL_PROMOTIONS_CONTAINER);
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.LABEL_7));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.PROMOTION_7));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_7));
		// VP: for a promotion user may choose any product from the DTS catalog, 
		//even if  it's not listed in the ""headphone brands"" below
		appDeviceControl.editData(DeviceEdit.PROMOTION_1,DTS_ID);
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.PROMOTION_ERROR));
	}
	
	/*
	 * Verify that DTS user can select one or more options in "Multi-Channel Room Models" section
	 */
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
		appDeviceControl.click(DeviceEdit.MCROOM_1ST);
		appDeviceControl.click(DeviceEdit.SAVE);
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.ROOM_MODEL_REQUIRE));
		// Verify user can select 1 room model
		appDeviceControl.click(DeviceEdit.MCROOM_3TH);
		appDeviceControl.click(DeviceEdit.SAVE);
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.ROOM_MODEL_REQUIRE));
		// Verify user can select 2 room models
		appDeviceControl.click(DeviceEdit.MCROOM_1ST);
		appDeviceControl.click(DeviceEdit.SAVE);
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceEdit.ROOM_MODEL_REQUIRE));
		// Verify user can select 3 room models
		appDeviceControl.click(DeviceEdit.MCROOM_2ND);
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
		// and Room0 of "Multi-Channel Room Models" are selected by default
		// We will unselect room models
		appDeviceControl.selectRoomModel(DeviceEdit.CHECK_BOX_MCROOM);
		appDeviceControl.click(DeviceEdit.MCROOM_1ST);
		appDeviceControl.click(DeviceEdit.MCROOM_2ND);
		appDeviceControl.click(DeviceEdit.MCROOM_3TH);
		// 6. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: a warning message should be displayed to notify user must be selected at least one option 
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.ROOM_MODEL_REQUIRE));
		// 7. Select option in "Multi-Channel Room Models"  and do not select options in "Headphone Image Sizes"
		appDeviceControl.click(DeviceEdit.MCROOM_1ST);
		//appDeviceControl.click(DeviceEdit.LARGE_IMAGE);
		//appDeviceControl.click(DeviceEdit.MEDIUM_IMAGE);
		//appDeviceControl.click(DeviceEdit.SMALL_IMAGE);
		// 8. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: a warning message should be displayed to notify user must be selected at least one option 
		//Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.IMAGE_SIZE_REQUIRE));
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
		appDeviceControl.editData(DeviceEdit.BRAND_FILTER,PARTNER_BRAND_NAME_1);
		// 7. Choose the brand names in auto suggest field
		WebElement brand_filter = driver.findElement(By.xpath(DeviceEdit.BRAND_FILTER));
		brand_filter.sendKeys(Keys.PAGE_DOWN);
		appDeviceControl.autoTool.send("{ENTER}", false);
		// appDeviceControl.click(DeviceEdit.FIRST_BRANDS);;
		// VP: The brand names is displayed in "Headphone Brands" section.
		Assert.assertEquals(appDeviceControl.getTextByXpath("//*[@id='brand-id-icon-trash-1957']/div/div[1]"), "TestCorp-Brand8");
		// 8. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: Verify that the 640D Device Details Page is displayed
		//Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo()),"true");
		// 9.Click  "Edit Version" link
		appDeviceControl.click(DeviceInfo.EDIT);
		// VP: Verify that the 650Da Device New page is displayed
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
		// 10. Delete several  brand name in "Headphone Brands" section.
		appDeviceControl.click("//*[@id='icon-trash-1957']");
		// VP: The brand names are deleted in "Headphone Brands" section.
		Assert.assertFalse(appDeviceControl.isElementPresent("//*[@id='icon-trash-1957']"));
		appDeviceControl.click(DeviceEdit.ALL_BRANDS);
		// 11. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: This app/device can save successfully
		Assert.assertTrue(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")));
		
	}
	
	/*
	 * Verify that when user attempts to save the record, at least one option on "Headphone Image Sizes" must be selected.
	 */
	@Test
	public void TC650DaDEN_38(){
		appDeviceControl.addLog("ID : TC650DaDEN_38: Verify that when user attempts to save the record, at least one option on 'Headphone Image Sizes' must be selected.");

		/*
		 *  1. Log into DTS portal as a DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click "Add App or Device" link
			4. Fill valid value into all required fields
			5. Do not select options in "Headphone Image Sizes" section.
			6. Click "Save" link
			VP: A warning message is displayed to notify user must be selected at least one option.

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.appDeviceDraft();
		data.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// 5. Do not select options in "Headphone Image Sizes" section.
		appDeviceControl.uncheckACheckbox(DeviceEdit.SMALL_IMAGE);
		appDeviceControl.uncheckACheckbox(DeviceEdit.LARGE_IMAGE);
		appDeviceControl.uncheckACheckbox(DeviceEdit.MEDIUM_IMAGE);
		// 6. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: A warning message is displayed to notify user must be selected at least one option.
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.IMAGE_SIZE_REQUIRE),DeviceEdit.requires[6]);
		
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
			5. Navigate to “Apps & Devices” page
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
		appDeviceControl.click(PageHome.LINK_AUDIO_ROUTES);
		// Publish "Attached0 - Internal Speakers (Default)" Audio Route
		appDeviceControl.click(AudioRoutes.LINK_OTHER_AUDIO);
		audioControl.publishAudioRoute(AddEditProductModel.FileUpload.Attached0_Internal_Speakers_Default.getName());
		// 4. Select an published audio route link in "Other Audio Routes" section. (such as: "Attached0 - Internal Speakers (Default)" option)
		/*
		 * VP: The "Publication Status" is displayed "Published".
		 */
		String publish_status = audioControl.getTextByXpath(AudioRoutes.AUDIO_DETAILS_PUBLISH_STATUS);
		Assert.assertTrue(publish_status.equals("Published"));
		// 5. Navigate to “Apps & Devices” page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 6. Click on "Add App & Device" link.
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 7. Click on drop down button in "Audio Route" section.
		appDeviceControl.click(DeviceEdit.AUDIO_ROUTE_TYPE);
		/*
		 * VP: The "Attached0 - Internal Speakers (Default)" audio route is listed in drop down.
		 */
		ArrayList<String> audiotype = appDeviceControl.getOptionList(DeviceEdit.AUDIO_ROUTE_TYPE);
		Assert.assertTrue(ListUtil.checkListContain(audiotype,"Attached0 - Internal Speakers (Default) Update"));
		// 8. Navigate to "Audio" page
		appDeviceControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 9. Select "Attached0 - Internal Speakers (Default)" link in "Other Audio Routes" section
		appDeviceControl.click(AudioRoutes.LINK_OTHER_AUDIO);
		// 10. Click "Edit Version" link
		appDeviceControl.click(AudioRoutes.AUDIO_DETAILS_EDIT_VERSION);
		appDeviceControl.waitForElementClickable(AudioRoutes.POPUP_OK);
		appDeviceControl.click(AudioRoutes.POPUP_OK);
		// 11. Input value "Internal Speakers (Default) Update" into "Name" field
		appDeviceControl.editData(AudioRoutes.AUDIO_DETAILS_DEFAULT_NAME,"Internal Speakers (Default) Update");
		// 12. Click "Save" link
		appDeviceControl.click(AudioRoutesEdit.SAVE);
		/*
		 * VP: The "Publication Status" is displayed "Draft".
		 */
		String publish_status2 = audioControl.getTextByXpath(AudioRoutes.AUDIO_DETAILS_PUBLISH_STATUS);
		Assert.assertTrue(publish_status2.equals("Draft"));
		// 13. Repeat step 5 to step 7
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		appDeviceControl.click(DeviceEdit.AUDIO_ROUTE_TYPE);
		/*
		 * VP: The "Attached0 - Internal Speakers (Default)" audio route is listed in drop down.
		 */
		ArrayList<String> audiotype2 = appDeviceControl.getOptionList(DeviceEdit.AUDIO_ROUTE_TYPE);
		Assert.assertTrue(ListUtil.checkListContain(audiotype2,"Attached0 - Internal Speakers (Default) Update"));
		// 14. Navigate to "Audio" page
		appDeviceControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 15. Select "Attached0 - Internal Speakers (Default) Update" link in "Other Audio Routes" section
		appDeviceControl.click(AudioRoutes.LINK_OTHER_AUDIO);
		// 16. Publish above "Attached0 - Internal Speakers (Default) Update" audio route successfully
		appDeviceControl.click(AudioRoutes.AUDIO_DETAILS_PUBLISH_LINK);
		/*
		 * VP: The "Publication Status" is displayed "Published".
		 */
		String publish_status3 = audioControl.getTextByXpath(AudioRoutes.AUDIO_DETAILS_PUBLISH_STATUS);
		Assert.assertTrue(publish_status3.equals("Published"));
		// 17. Repeat from step 5 to step 7
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		appDeviceControl.click(DeviceEdit.AUDIO_ROUTE_TYPE);
		/*
		 * VP: The "Attached0 - Internal Speakers (Default) Update" audio route is listed in drop down.
		 */
		ArrayList<String> audiotype3 = appDeviceControl.getOptionList(DeviceEdit.AUDIO_ROUTE_TYPE);
		Assert.assertTrue(ListUtil.checkListContain(audiotype3,"Attached0 - Internal Speakers (Default) Update"));
		
		// Set default name back
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		audioControl.click(AudioRoutes.LINK_OTHER_AUDIO);
		audioControl.click(AudioRoutesInfo.EDIT);
		audioControl.waitForElementClickable(AudioRoutesEdit.POPUP_OK);
		audioControl.click(AudioRoutesEdit.POPUP_OK);
		audioControl.editData(AudioRoutesEdit.DISPLAY_NAME,"Internal Speakers (Default)");
		audioControl.click(AudioRoutesEdit.SAVE);
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
		audioControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes[3]);
		//7. Click "Save" link
		audioControl.click(DeviceEdit.SAVE);
		/*
		 * VP: A error message is dispalyed like  error message "Custom Tunning File is required".
		 */
		Assert.assertEquals(audioControl.getTextByXpath(DeviceEdit.CUSTOM_TUNING_MESS),DeviceEdit.requires[5]);
		//8. Click "Choose file" link and upload a tuning file successfully 
		audioControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached0_Internal_Speakers_Default.getName());
		//9. Click "Save" link
		audioControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The tuning file is uploaded successfully and App/Device is created successfully.
		 */
		Assert.assertEquals(driver.findElement(By.partialLinkText(DeviceEdit.routes[3])).getText(),DeviceEdit.routes[3]);
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
		appDeviceControl.click(PageHome.LINK_AUDIO_ROUTES);
		appDeviceControl.click(AudioRoutes.MCROOM0);
		audioControl.publishAudioRoute(AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		String published_mcroom = audioControl.getTextByXpath(RoomModelInfo.DISPLAY_NAME);
		// Draft McRoom 
		appDeviceControl.click(PageHome.LINK_AUDIO_ROUTES);
		appDeviceControl.click(AudioRoutes.MCROOM1);
		audioControl.editVersion();
		audioControl.editData(RoomModelEdit.DISPLAY_NAME,"Published version");
		audioControl.click(RoomModelEdit.SAVE);
		audioControl.publishAudioRoute(AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
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
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.MCROOM_1ST),"McRoom0("+published_mcroom+")");
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.MCROOM_2ND),("McRoom1("+published_version+")"));
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
		7. Input "25" in Promotion Device Available
		8. Turn on in Global Promotions
		9. Click "Save" link
		10. Click "Edit" link
		11. Change the order of list promotion slots by drag and drop (1st)
		VP: user can drag and drop promotion slots to order normally
		12. Click "Save" link
		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 11)
		13. Click "Publish" link
		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 11)
		14. Click "Update Product Info" link
		15. Click "OK" button
		16. Change the order of list promotion slots by drag and drop (2nd)
		17. Click "Save" link
		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 16)
		18. Click “Edit” link
		19. Change the order of list promotion slots by drag and drop (3th)
		20. Click "Save" link
		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 19)
		21. Click "Publish" link
		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 19)

		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		4. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		5. Fill all valid values into required fields
//		6. Upload an valid default audio route file
//		7. Input "25" in Promotion Device Available
//		8. Turn on in Global Promotions
//		9. Click "Save" link
		Hashtable<String,String> data = TestData.appDevicePublish();
		data.remove("global promotion");
		data.put("global promotion", "ON");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),data);
		String device_name = appDeviceControl.getTextByXpath(DeviceInfo.NAME);
//		10. Click "Edit" link
		appDeviceControl.editVersion();
		//appDeviceControl.click(DeviceInfo.EDIT);
//		11. Change the order of list promotion slots by drag and drop (1st)
		// Get list before Drad & Drop
		ArrayList<String> before_list1 = appDeviceControl.getListOrder();
		appDeviceControl.dragAndDrop(DeviceEdit.promotionslotlabel(2), DeviceEdit.promotionslotlabel(1));
		ArrayList<String> after_list1 = appDeviceControl.getListOrder();
//		VP: user can drag and drop promotion slots to order normally
		Assert.assertTrue(appDeviceControl.isPromotionOrder(2,1,before_list1,after_list1));
//		12. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
//		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 11)
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Draft"),after_list1));
//		13. Click "Publish" link
		appDeviceControl.click(DeviceInfo.PUBLISH);
//		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 11)
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Published"),after_list1));
//		14. Click "Update Product Info" link
//		15. Click "OK" button
		appDeviceControl.editVersion();
//		16. Change the order of list promotion slots by drag and drop (2nd)
		appDeviceControl.dragAndDrop(DeviceEdit.promotionslotlabel(5), DeviceEdit.promotionslotlabel(4));
		ArrayList<String> after_list2 = appDeviceControl.getListOrder();
//		17. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
//		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 16)
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Draft"),after_list2));
//		18. Click “Edit” link
		appDeviceControl.click(DeviceInfo.EDIT);
//		19. Change the order of list promotion slots by drag and drop (3th)
		appDeviceControl.dragAndDrop(DeviceEdit.promotionslotlabel(2), DeviceEdit.promotionslotlabel(1));
		ArrayList<String> after_list3 = appDeviceControl.getListOrder();
//		20. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
//		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 19)
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Draft"),after_list3));
//		21. Click "Publish" link
		appDeviceControl.click(DeviceInfo.PUBLISH);
//		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 19)
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Published"),after_list3));
		
		// Delete device
		appDeviceControl.selectADeviceByName(device_name);
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that DTS user can edit the list order of Apps or Devices Promotions
	 */
	@Test
	public void TC650DaDEN_43(){
		Reporter.log("Verify that DTS user can edit the list order of Apps or Devices Promotions");
		
		/*1. Navigate to DTS portal.
		2. Log into DTS portal as a DTS user successfully.
		3. Navigate to "Apps & Devices" page
		4. Click "Add App or Device" link
		5. Fill all valid values into required fields (Include upload an valid default audio route file)
		6. Input "25" in Promotion Device Available
		7. Input some valid Apps/Devices Promotions ID which are published and are applied for this app and device
		8. Turn off in Global Promotions
		9. Click "Save" link
		10. Click "Edit" link
		11. Change the order of list promotion slots by drag and drop (1st)
		VP: user can drag and drop promotion slots to order normally		
		12. Click "Save" link
		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 11)
		13. Click "Publish" link
		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 11)
		14. Click "Update Product Info" link
		15. Click "OK" button
		16. Change the order of list promotion slots by drag and drop (2nd)
		17. Click "Save" link
		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 16)
		18. Click “Edit” link
		19. Change the order of list promotion slots by drag and drop (3th)
		20. Click "Save" link
		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 19)
		21. Click "Publish" link
		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 19)
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
		// Navigate to Promotion page
		appDeviceControl.click(PageHome.linkPromotions);
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create 1st Device promotion
		Hashtable<String,String> dataDevicePromotion1 = TestData.promotionAppDevice(dataProduct1.get("brand") + " " + dataProduct1.get("name"),PARTNER_BRAND_NAME_1,deviceName);
		promotionControl.addPromotion(AddPromotion.getHash(), dataDevicePromotion1);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		// Get promotion ID
		String promotionID1 = appDeviceControl.getTextByXpath(PromotionInfo.DTS_ID);
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
		// Get promotion ID
		String promotionID2 = appDeviceControl.getTextByXpath(PromotionInfo.DTS_ID);
        /*
         * *******************************************
         */
//		3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		4. Click "Add App or Device" link
//		5. Fill all valid values into required fields (Include upload an valid default audio route file)
//		6. Input "25" in Promotion Device Available
//		7. Input some valid Apps/Devices Promotions ID which are published and are applied for this app and device
//		8. Turn off in Global Promotions
//		9. Click "Save" link
//		10. Click "Edit" link
		appDeviceControl.selectADeviceByName(deviceName);
		appDeviceControl.click(DeviceInfo.EDIT);
		appDeviceControl.editData(DeviceEdit.PROMOTION_1,promotionID1);
		appDeviceControl.editData(DeviceEdit.PROMOTION_2,promotionID2);
//		11. Change the order of list promotion slots by drag and drop (1st)
		// Get list before Drad & Drop
		ArrayList<String> before_list1 = appDeviceControl.getListOrder();
		appDeviceControl.dragAndDrop(DeviceEdit.promotionslotlabel(2), DeviceEdit.promotionslotlabel(1));
		ArrayList<String> after_list1 = appDeviceControl.getListOrder();
//		VP: user can drag and drop promotion slots to order normally
		Assert.assertTrue(appDeviceControl.isPromotionOrder(2,1,before_list1,after_list1));
		//12. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
//		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 11)
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Draft"),after_list1));
//		13. Click "Publish" link
		appDeviceControl.click(DeviceInfo.PUBLISH);
//		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 11)
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Published"),after_list1));
//		14. Click "Update Product Info" link
//		15. Click "OK" button
		appDeviceControl.editVersion();
//		16. Change the order of list promotion slots by drag and drop (2nd)
		appDeviceControl.dragAndDrop(DeviceEdit.promotionslotlabel(1), DeviceEdit.promotionslotlabel(2));
		ArrayList<String> after_list2 = appDeviceControl.getListOrder();
//		17. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
//		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 16)
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Draft"),after_list2));
//		18. Click “Edit” link
		appDeviceControl.click(DeviceInfo.EDIT);
//		19. Change the order of list promotion slots by drag and drop (3th)
		appDeviceControl.dragAndDrop(DeviceEdit.promotionslotlabel(2), DeviceEdit.promotionslotlabel(1));
		ArrayList<String> after_list3 = appDeviceControl.getListOrder();
//		20. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
//		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 19)
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Draft"),after_list3));
//		21. Click "Publish" link
		appDeviceControl.click(DeviceInfo.PUBLISH);
//		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 19)
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Published"),after_list3));

		// Delete data
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.selectADeviceByName(deviceName);
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		
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
		5. Fill all valid values into required fields (Include upload an valid default audio route file)
		6. Input "25" in Promotion Device Available
		7. Input some valid Global/Apps/Devices Promotions ID which are published and are applied for this app and device
		8. Turn off in Global Promotions
		9. Click "Save" link
		10. Click "Edit" link
		11. Change the order of list promotion slots by drag and drop (1st)
		VP: user can drag and drop promotion slots to order normally		
		12. Click "Save" link
		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 11)
		13. Click "Publish" link
		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 11)
		14. Click "Update Product Info" link
		15. Click "OK" button
		16. Change the order of list promotion slots by drag and drop (2nd)
		17. Click "Save" link
		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 16)
		18. Click “Edit” link
		19. Change the order of list promotion slots by drag and drop (3th)
		20. Click "Save" link
		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 19)
		21. Click "Publish" link
		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 19)
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
		dataDevice.remove("global promotion");
		dataDevice.put("global promotion","ON");
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
		// Create 3th published product
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
		// Get promotion ID
		String promotionID1 = appDeviceControl.getTextByXpath(PromotionInfo.DTS_ID);
		// Navigate to Promotion page
		appDeviceControl.click(PageHome.linkPromotions);
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create 2nd Device promotion
		Hashtable<String,String> dataDevicePromotion2 = TestData.promotionAppDevice(dataProduct2.get("brand") + " " + dataProduct2.get("name"),PARTNER_BRAND_NAME_1,deviceName);
		promotionControl.addPromotion(AddPromotion.getHash(), dataDevicePromotion2);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		// Get promotion ID
		String promotionID2 = appDeviceControl.getTextByXpath(PromotionInfo.DTS_ID);
		// Create Global promotion
		// Navigate to Promotion page
		appDeviceControl.click(PageHome.linkPromotions);
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		Hashtable<String,String> dataGlobalPromotion = TestData.promotionGlobal(dataProduct3.get("brand") + " " + dataProduct3.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataGlobalPromotion);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		// Get promotion ID
		String promotionID3 = appDeviceControl.getTextByXpath(PromotionInfo.DTS_ID);
		
        /*
         * *******************************************
         */
//		3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		4. Click "Add App or Device" link
//		5. Fill all valid values into required fields (Include upload an valid default audio route file)
//		6. Input "25" in Promotion Device Available
//		7. Input some valid Apps/Devices Promotions ID which are published and are applied for this app and device
//		8. Turn off in Global Promotions
//		9. Click "Save" link
//		10. Click "Edit" link
		appDeviceControl.selectADeviceByName(deviceName);
		appDeviceControl.click(DeviceInfo.EDIT);
		appDeviceControl.editData(DeviceEdit.PROMOTION_1,promotionID1);
		appDeviceControl.editData(DeviceEdit.PROMOTION_2,promotionID2);
		appDeviceControl.editData(DeviceEdit.PROMOTION_3,promotionID3);
//		11. Change the order of list promotion slots by drag and drop (1st)
		// Get list before Drad & Drop
		ArrayList<String> before_list1 = appDeviceControl.getListOrder();
		appDeviceControl.dragAndDrop(DeviceEdit.promotionslotlabel(2), DeviceEdit.promotionslotlabel(1));
		ArrayList<String> after_list1 = appDeviceControl.getListOrder();
//		VP: user can drag and drop promotion slots to order normally
		Assert.assertTrue(appDeviceControl.isPromotionOrder(2,1,before_list1,after_list1));
		//12. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
//		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 11)
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Draft"),after_list1));
//		13. Click "Publish" link
		appDeviceControl.click(DeviceInfo.PUBLISH);
//		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 11)
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Published"),after_list1));
//		14. Click "Update Product Info" link
//		15. Click "OK" button
		appDeviceControl.editVersion();
//		16. Change the order of list promotion slots by drag and drop (2nd)
		appDeviceControl.dragAndDrop(DeviceEdit.promotionslotlabel(1), DeviceEdit.promotionslotlabel(3));
		ArrayList<String> after_list2 = appDeviceControl.getListOrder();
//		17. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
//		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 16)
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Draft"),after_list2));
//		18. Click “Edit” link
		appDeviceControl.click(DeviceInfo.EDIT);
//		19. Change the order of list promotion slots by drag and drop (3th)
		appDeviceControl.dragAndDrop(DeviceEdit.promotionslotlabel(3), DeviceEdit.promotionslotlabel(2));
		ArrayList<String> after_list3 = appDeviceControl.getListOrder();
//		20. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
//		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 19)
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Draft"),after_list3));
//		21. Click "Publish" link
		appDeviceControl.click(DeviceInfo.PUBLISH);
//		VP: System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 19)
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Published"),after_list3));
		
		// Delete data
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.selectADeviceByName(deviceName);
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
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.INSTRUCTION_TEXT),"Drag to reorder promotion list.");
		Assert.assertTrue(appDeviceControl.isReorderIconNextToPromo());
	}
	
	
	/*
	 * Verify that the visual indicator is displayed correctly while draging a promo item.
	 */
	@Test
	public void TC650DaDEN_47(){
		Reporter.log("Verify that the visual indicator is displayed correctly while draging a promo item.");
		
		/*1. Navigate to DTS portal
		2. Log into DTS portal as a DTS user successfully
		3. Navigate to "Apps & Devices" page
		4. Click "Add App or Device" link
		5. Fill valid values into all required fields
		6. Click "Save" link
		7. Click "Edit Version" link
		8. Drag "Promo 1" to new location between "Promo 2" and "Promo 3"
		VP: The thin line is displayed between "Promo 2" and "Promo 3"
		9. Drop above Promo
		VP: The promo is displayed correct location
		10. Drag "Promo 4" to new location before "Promo 1"
		VP: The thin line is displayed above "Promo 1"
		11. Drop above Promo
		VP: The promo is displayed correct location
		12. Drag "Promo 3" to new location after the final Promo
		VP: The thin line is displayed under the final promo
		13. Drop above Promo
		VP: The promo is displayed correct location
		14. Click "Save" link
		VP: The 640D Device Detail page is displayed and list order of promotions 
		is displayed correctly following the user has dragged &dropped before
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
		//7. Click "Edit Version" link
		appDeviceControl.editVersion();
		//8. Drag "Promo 1" to new location between "Promo 2" and "Promo 3"
		/*
		 * VP: The thin line is displayed between "Promo 2" and "Promo 3"
		 */
		//9. Drop above Promo
		ArrayList<String> before_list1 = appDeviceControl.getListOrder();
		appDeviceControl.dragAndDrop(appDeviceControl.getPromotionSlot(1),appDeviceControl.getPromotionSlot(3));
		ArrayList<String> after_list1 = appDeviceControl.getListOrder();
		/*
		 * VP: The promo is displayed correct location
		 */
		//Assert.assertTrue(appDeviceControl.isPromotionOrder(1,3,before_list1,after_list1));
		//10. Drag "Promo 4" to new location before "Promo 1"
		/*
		 * VP: The thin line is displayed above "Promo 1"
		 */
		//11. Drop above Promo
		appDeviceControl.dragAndDrop(appDeviceControl.getPromotionSlot(4),appDeviceControl.getPromotionSlot(1));
		ArrayList<String> after_list2 = appDeviceControl.getListOrder();
		/*
		 * VP: The promo is displayed correct location
		 */
		//Assert.assertTrue(appDeviceControl.isPromotionOrder(4,1,after_list1,after_list2));
		//12. Drag "Promo 3" to new location after the final Promo
		/*
		 * VP: The thin line is displayed under the final promo
		 */
		//13. Drop above Promo
		appDeviceControl.dragAndDrop(appDeviceControl.getPromotionSlot(3),appDeviceControl.getPromotionSlot(6));
		ArrayList<String> after_list3 = appDeviceControl.getListOrder();
		/*
		 * VP: The promo is displayed correct location 
		 */
		//Assert.assertTrue(appDeviceControl.isPromotionOrder(2,6,after_list2,after_list3));
		//14. Click "Save" link
		// Get list order of promotions after draging & dropping
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The 640D Device Detail page is displayed and list order of promotions 
		is displayed correctly following the user has dragged &dropped before
		*/
		System.out.println(before_list1);
		System.out.println(after_list1);
		System.out.println(after_list2);
		System.out.println(after_list3);
		Assert.assertTrue(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")));
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Draft"),after_list3));
		
		// Delete device
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.selectADeviceByName(device_name);
		appDeviceControl.doDelete(DeviceInfo.DELETE);

	}
	
	/*
	 * Verify that DTS user can select one or more options in "Headphone Image Sizes" section
	 */
	@Test
	public void TC650DaDEN_48(){
		Reporter.log("Verify that DTS user can select one or more options in 'Headphone Image Sizes' section");
		
		/*1. Navigate to DTS portal
		2. Log into DTS portal as a DTS user successfully
		3. Navigate to "Apps & Devices" page
		4. Click "Add App or Device" link
		5. Fill all valid values into required fields
		6. Select only "Medium (500x500px)" option in "Headphone Image Sizes"
		7. Click "Save" link
		VP: The app/device is saved successfully
		8. Click "Edit Version" link
		9. Select ""Large (1000x1000px) "" option in ""Headphone Image Sizes""
		10. Click "Save" link
		VP: The app/device is saved successfully
		*/
		
		//2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//4. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		//5. Fill all valid values into required fields
		Hashtable<String,String> dataDevice = TestData.appDevicePublish();
		dataDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice);
		//6. Select only "Medium (500x500px)" option in "Headphone Image Sizes"
		appDeviceControl.selectACheckbox(DeviceEdit.MEDIUM_IMAGE);
		appDeviceControl.uncheckACheckbox(DeviceEdit.LARGE_IMAGE);
		appDeviceControl.uncheckACheckbox(DeviceEdit.SMALL_IMAGE);
		//7. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The app/device is saved successfully
		 */
		Assert.assertTrue(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")));
		//8. Click "Edit Version" link
		appDeviceControl.click(DeviceInfo.EDIT);
		//9. Select ""Large (1000x1000px) "" option in ""Headphone Image Sizes""
		appDeviceControl.selectACheckbox(DeviceEdit.LARGE_IMAGE);
		//10. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The app/device is saved successfully
		 */
		Assert.assertTrue(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")));

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
		dataDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice);
		/*
		 * VP: The 650Da Device Edit page is displayed
		 */
		//5. Unselect all options in "Headphone Image Sizes"
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
		Assert.assertTrue(ListUtil.containsAll(dataDevice,DeviceInfo.getDeviceInfo()));

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
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.HEADPHONE_IMAGE_SIZES_INSTRUCTION),"Select which headphone image sizes will be made available to users.For on-device databases, only one size is recommended.");

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
		appDeviceControl.editData(DeviceEdit.INBOX_FIELD,dataProduct.get("name"));
		appDeviceControl.autoTool.send("{ENTER}", false);
		appDeviceControl.autoTool.send("{ENTER}", false);
		/*
		 * VP: the headphone model name is displayed near the text field
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.INBOX_PRODUCT_NAME),dataProduct.get("name"));
		
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
		appDeviceControl.editData(DeviceEdit.INBOX_FIELD,"          "+dataProduct.get("name")+"        ");
		appDeviceControl.autoTool.send("{ENTER}", false);
		appDeviceControl.autoTool.send("{ENTER}", false);
		/*
		 * VP: Text field auto trim the white space in product name
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.INBOX_PRODUCT_NAME),dataProduct.get("name"));
		
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
		appDeviceControl.editData(DeviceEdit.INBOX_FIELD,dataProduct.get("name"));
		appDeviceControl.autoTool.send("{ENTER}", false);
		appDeviceControl.autoTool.send("{ENTER}", false);
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
		 String productName = appDeviceControl.getTextByXpath(DeviceInfo.NAME);
		 //2. Navigate to "Apps & Devices" page
		 appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		 //3. Select add app or device 
		 appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		 //4.Scroll down to "In-box headphone" section
		 appDeviceControl.selectOptionByName(DeviceEdit.TYPE,"Device");
		 //5. Input headphone render entity UUID to text field
		 appDeviceControl.editData(DeviceEdit.INBOX_FIELD,productName);
		 appDeviceControl.autoTool.send("{ENTER}", false);
		 appDeviceControl.autoTool.send("{ENTER}", false);
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
	public void TC650DaDEN_57(){
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
		appDeviceControl.editData(DeviceEdit.INBOX_FIELD,productName);
		appDeviceControl.autoTool.send("{ENTER}", false);
		appDeviceControl.autoTool.send("{ENTER}", false);
		//6. Click "Save" button
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: Section ""in-box headphone model"" displays, the headphone model and date are displayed correctly.
		 * VP: the date is the time that the value was last changed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.INBOX_HEADPHONES));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.INBOX_INSTRUCTION));
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.INBOX_PRODUCT_NAME),productName);
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
	public void TC650DaDEN_58(){
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
		 appDeviceControl.click(ProductDetailModel.EDIT_MODE);
		 appDeviceControl.selectConfirmationDialogOption("Ok");
		 String productName = appDeviceControl.getTextByXpath(DeviceInfo.NAME);
		 //2. Navigate to "Apps & Devices" page
		 appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		 //	3. Select add app or device 
		 appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		 //	4. Input value to all section
		 Hashtable<String,String> deviceData = TestData.appDeviceDraft();
		 deviceData.remove("save");
		 appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		 //	5. Input headphone render entity UUID to text field (the publication status of headphone is Draft)
		 appDeviceControl.editData(DeviceEdit.INBOX_FIELD,productName);
		 appDeviceControl.autoTool.send("{ENTER}", false);
		 appDeviceControl.autoTool.send("{ENTER}", false);
		 //	6. Click "Save" button
		 appDeviceControl.click(DeviceEdit.SAVE);
		 //	7. Click the headphone model in section "in-box headphone"
		 appDeviceControl.click(DeviceInfo.INBOX_PRODUCT_NAME);
		 /*
		  * VP: the "headphone model " always links to the currently published version data
		  */
		 Assert.assertEquals(appDeviceControl.getTextByXpath(ProductDetailModel.PUBLICATION_STATUS),"Published");
		 
		// Delete products
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.selectADeviceByName(deviceData.get("name"));
		appDeviceControl.doDelete(ProductDetailModel.DELETE);
		
	    appDeviceControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		appDeviceControl.doDelete(ProductDetailModel.DELETE);
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
		productControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes[1]);
		ArrayList<String> standardAccessories = appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE);
		/*
		 * Verify that the 'Audio Routes' combobox contains items properly
		*/
		Assert.assertTrue(ListUtil.containsAll(audioRoutes, DeviceEdit.routes));
		Assert.assertTrue(ListUtil.containsAll(standardAccessories, DeviceEdit.Standard_Accessories));
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
		appDeviceControl.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		/*
		 * VP: Verify that An error message is displayed and the invalid audio route tuning file is not added
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE));
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.AUDIO_ROUTE_INVALID_MESSAGE),"! Invalid file");
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
		appDeviceControl.clickOptionByIndex(DeviceEdit.AUDIO_ROUTE_TYPE, 1);
		appDeviceControl.clickOptionByIndex(DeviceEdit.AUDIO_ROUTE, 1);
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones.getName());
		/*
		 * VP: The custom uploaded tuning file is displayed with a delete icon above the Custom Tuning module
		 */
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Over_Ear_Headphones.getName().replaceAll(".dtscs", "")));
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
		appDeviceControl.clickOptionByIndex(DeviceEdit.AUDIO_ROUTE_TYPE, 1);
		appDeviceControl.clickOptionByIndex(DeviceEdit.AUDIO_ROUTE, 1);
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones.getName());
		// 7. Click on uploaded tuning link
		Boolean isDownloaded = appDeviceControl.downloadFile(AddEditProductModel.FileUpload.Over_Ear_Headphones.getName());
		//boolean isDownloaded = productControl.downloadFile(AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
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
		// 5. Increase the number of "Promo Slot Available" to 7
		appDeviceControl.editData(DeviceEdit.PROMO_SLOT, "7");
		appDeviceControl.autoTool.send("{ENTER}", false);
		/*
		 * Verify that The "Device Promotions" diplays 7 fields
		 */
		Assert.assertEquals(promotionControl.getTotalPromotionContainers(DeviceEdit.PROMOTION_CONTAINER), 7);
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
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes[3]);
		// 6. Upload a tuning file for above audio route item successfully
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached0_Internal_Speakers_Default.getName());
		// 7. List out the audio route combobox
		ArrayList<String> options = appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE);
		/*
		 * The audio route item of uploaded tuning file is removed from audio route combobox
		 */
		Assert.assertTrue(!options.contains(DeviceEdit.routes[3]));
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
}
