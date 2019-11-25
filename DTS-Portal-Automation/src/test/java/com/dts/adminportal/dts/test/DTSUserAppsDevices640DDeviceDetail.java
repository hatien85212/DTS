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
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.DeviceEdit;
import dts.com.adminportal.model.DeviceInfo;
import dts.com.adminportal.model.DeviceList;
import dts.com.adminportal.model.PromotionInfo;
import dts.com.adminportal.model.PromotionList;
import dts.com.adminportal.model.Xpath;

public class DTSUserAppsDevices640DDeviceDetail extends CreatePage{
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
	 * Verify that the 640D Device detail page shows up "Product Information", "Headphone:X Licensing Info", "Audio Routes" and "Featured Accessory Promotions" sections.
	 */
	@Test
	public void TC640DADD_01(){
		result.addLog("ID : TC640DADD_01 : Verify that the 640D Device detail page shows up 'Product Information', 'Headphone:X Licensing Info', 'Audio Routes' and 'Featured Accessory Promotions' sections.");
		/*
		 	PreCondition: the DTS user has "Add and manage authorized productS" privilege.
			1. Log into DTS portal as above DTS admin user
			2. Navigate to "Apps & Devices" page
			3. Select a device from app & device table
		*/
		/*
		 * PreCondition: Create new app/device
		 */
		// Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// Click Add App or Device link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// Create app/device
		Hashtable<String,String> data = TestData.appDeviceDraft();
		home.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * ****************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Select a device from app & device table
		home.selectADeviceByName(data.get("name"));
		/*
		 * The 640D Device Detail page is displayed an contains 
		 * "Product Information", 
		 * "Headphone:X Licensing Info", 
		 * "Audio Routes" and 
		 * "Featured Accessory Promotions" sections.
		 */
		Assert.assertEquals("Pass", home.existsElement(DeviceInfo.getSections()).getResult());
		// Delete device
		home.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that  the suspend confirmation dialog is displayed when suspending an app or device.
	 */
	@Test
	public void TC640DADD_02(){
		result.addLog("ID : TC640DADD_02 : Verify that  the suspend confirmation dialog is displayed when susepending an app or device.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized productS" privilege.
			1. Log into DTS portal as above DTS admin user
			2. Navgate to "Apps & Devices" page
			3. Select a published model from app & device table
			VP: The 640D Device Detail page is displayed
			4. Click "Suspend" link
		*/
		/*
		 * Pre-condition: Create a new published app/device
		 */
		// Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// Click "Add app or device" link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDevicePublish();
		home.createNewDevice(DeviceEdit.getHash(), data);
		// Publish device
		home.click(DeviceInfo.PUBLISH);
		home.waitForElementClickable(Xpath.LINK_APP_DEVICES);
		/*
		 * ***********************************************************
		 */
		// 2. Navgate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Select a published model from app & device table
		home.selectADeviceByName(data.get("name"));
		/*
		 * VP: The 640D Device Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(DeviceInfo.NAME), data.get("name"));
		// 4. Click "Suspend" link
		home.click(DeviceInfo.SUSPEND);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		/*
		 * The suspend confirmation dialog is displayed with "Suspend" and "Cancel" buttons
		 */
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_DANGER), "Suspend");
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_CANCEL), "Cancel");
		// Delete device
		home.click(Xpath.BTN_CONFIRMATION_CANCEL);
		home.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the delete confirmation dialog is displayed when deleting an app or device.
	 */
	@Test
	public void TC640DADD_03(){
		result.addLog("ID : TC640DADD_03 : Verify that  the delete confirmation dialog is displayed when deleting an app or device.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized productS" privilege.
			1. Log into DTS portal as above DTS admin user
			2. Navgate to "Apps & Devices" page
			3. Select a device from app & device table
			VP: The 640D Device Detail page is displayed
			4. Click "Delete" link
		*/
		/*
		 * Pre-condition: Create a new app/device
		 */
		// Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// Click "Add app or device" link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDevicePublish();
		home.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * ***********************************************************
		 */
		// 2. Navgate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Select a published model from app & device table
		home.selectADeviceByName(data.get("name"));
		/*
		 * VP: The 640D Device Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(DeviceInfo.NAME), data.get("name"));
		// 4. Click "Delete" link
		home.click(DeviceInfo.DELETE);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		/*
		 * The delete confirmation dialog is displayed with "Delete" and "Cancel" buttons
		 */
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_CANCEL), "Cancel");
		// Delete device
		home.click(Xpath.BTN_CONFIRMATION_DANGER);
	}
	
	/*
	 * Verify that the DTS user could delete an app or device successfully.
	 */
	@Test
	public void TC640DADD_04(){
		result.addLog("ID : TC640DADD_04 : Verify that the DTS user could delete an app or device successfully.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Select a device from app & device table
			VP: The 640D Device Detail page is displayed
			4. Click "Delete" link
			VP: The delete confirmation dialog is displayed with "Delete" and "Cancel" link
			5. Click "Delete" button
		*/
		/*
		 * PreCondition: Create new app/device
		 */
		// Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// Click Add App or Device link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// Create app/device
		Hashtable<String,String> data = TestData.appDeviceDraft();
		home.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * ****************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Select a device from app & device table
		home.selectADeviceByName(data.get("name"));
		/*
		 * VP: The 640D Device Detail page is displayed
		 */
		Assert.assertEquals(home.existsElement(DeviceInfo.getElementInfo()).getResult(), "Pass");
		// 4. Click "Delete" link
		home.click(DeviceInfo.DELETE);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		/*
		 * VP: The delete confirmation dialog is displayed with "Delete" and "Cancel" link
		 */
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_CANCEL), "Cancel");
		// 5. Click "Delete" button
		home.selectConfirmationDialogOption("Delete");
		/*
		 * The 630D Device List page is displayed and the deleted app or device is not displayed in apps & devices table
		 */
		Assert.assertEquals(home.existsElement(DeviceList.getListXpath()).getResult(), "Pass");
		Assert.assertFalse(home.checkAnAppDeviceExistByName(data.get("name")));
	}
	
	/*
	 * Verify that the DTS user could suspend an app or device successfully.
	 */
	@Test
	public void TC640DADD_05(){
		result.addLog("ID : TC640DADD_05 : Verify that the DTS user could suspend an app or device successfully.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Select a published model from app & device table
			VP: The 640D Device Detail page is displayed
			4. Click "Suspend" link
			VP: The suspend confirmation dialog is displayed with "Suspend" and "Cancel" link
			5. Click "Suspend" button
		*/
		/*
		 * PreCondition: Create new published app/device
		 */
		// Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// Click Add App or Device link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// Create app/device
		Hashtable<String,String> data = TestData.appDevicePublish();
		home.createNewDevice(DeviceEdit.getHash(), data);
		// Publish device
		home.click(DeviceInfo.PUBLISH);
		home.waitForElementClickable(Xpath.LINK_APP_DEVICES);
		/*
		 * ****************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Select a published model from app & device table
		home.selectADeviceByName(data.get("name"));
		/*
		 * VP: The 640D Device Detail page is displayed
		 */
		Assert.assertEquals(home.existsElement(DeviceInfo.getElementInfo()).getResult(), "Pass");
		// 4. Click "Suspend" link
		home.click(DeviceInfo.SUSPEND);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		/*
		 * VP: The suspend confirmation dialog is displayed with "Suspend" and "Cancel" link
		 */
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_DANGER), "Suspend");
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_CANCEL), "Cancel");
		// 5. Click "Suspend" button
		home.selectConfirmationDialogOption("Suspend");
		/*
		 * The 630D Device List page is displayed and the suspended app or device is suspended
		 */
		Assert.assertEquals(home.existsElement(DeviceList.getListXpath()).getResult(), "Pass");
		home.selectADeviceByName(data.get("name"));
		Assert.assertEquals(home.getTextByXpath(DeviceInfo.PUBLICATION_STATUS), "Suspended");
		// Delete device
		home.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the "Suspend " link is not displayed for an un-published model
	 */
	@Test
	public void TC640DADD_06(){
		result.addLog("ID : TC640DADD_06 : Verify that the 'Suspend' link is not displayed for an un-published model");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			VP: The 630D Device List page is displayed with "Add app or Device" link
			3. Add an app or device successfully
			4. Review the added app or device in 640D Device Detail page
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		/*
		 * VP: The 630D Device List page is displayed with "Add app or Device" link
		 */
		Assert.assertEquals(home.existsElement(DeviceList.getListXpath()).getResult(), "Pass");
		Assert.assertTrue(home.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
		// 3. Add an app or device successfully
		// Click on Add App or Device link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDeviceDraft();
		home.createNewDevice(DeviceEdit.getHash(), data);
		// 4. Review the added app or device in 640D Device Detail page
		/*
		 * Verify that The "Suspend" link is not displayed in "Actions" module
		 */
		Assert.assertFalse(home.isElementPresent(DeviceInfo.SUSPEND));
		// Delete device
		home.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the "Suspend" link is displayed for a published model.
	 */
	@Test
	public void TC640DADD_07(){
		result.addLog("ID : TC640DADD_07 : Verify that the 'Suspend' link is displayed for a published model.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			VP: The 630D Device List page is displayed with "Add app or Device" link
			3. Add an app or device successfully
			4. Review the added app or device in 640D Device Detail page
			5. Click "Publish" link
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		/*
		 * VP: The 630D Device List page is displayed with "Add app or Device" link
		 */
		Assert.assertEquals(home.existsElement(DeviceList.getListXpath()).getResult(), "Pass");
		Assert.assertTrue(home.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
		// 3. Add an app or device successfully
		// Click Add App or Device link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// Create app/device
		Hashtable<String, String> data = TestData.appDevicePublish();
		home.createNewDevice(DeviceEdit.getHash(), data);
		// 4. Review the added app or device in 640D Device Detail page
		// 5. Click "Publish" link
		home.click(DeviceInfo.PUBLISH);
		home.waitForElementClickable(Xpath.LINK_APP_DEVICES);
		/*
		 * The "Suspend" link is displayed in "Actions" module after publishing
		 */
		Assert.assertTrue(home.isElementPresent(DeviceInfo.SUSPEND));
		// Delete device
		home.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the "Create New Version" link is displayed after publishing an app or device.
	 */
	@Test
	public void TC640DADD_08(){
		result.addLog("ID : TC640DADD_08 : Verify that the 'Create New Version' link is displayed after publishing an app or device.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized productS" privilege.
			1. Log into DTS portal as above DTS admin user
			2. Navgate to "Apps & Devices" page
			VP: The 630D Device List page is displayed with "Add app or Device" link
			3. Add an app or device successfully
			4. Review the added app or device in 640D Device Detail page
			5. Click "Publish" link
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		/*
		 * VP: The 630D Device List page is displayed with "Add app or Device" link
		 */
		Assert.assertEquals(home.existsElement(DeviceList.getListXpath()).getResult(), "Pass");
		Assert.assertTrue(home.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
		// 3. Add an app or device successfully
		// Click Add App or Device link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// Create app/device
		Hashtable<String, String> data = TestData.appDevicePublish();
		home.createNewDevice(DeviceEdit.getHash(), data);
		// 4. Review the added app or device in 640D Device Detail page
		// 5. Click "Publish" link
		home.click(DeviceInfo.PUBLISH);		
		home.waitForElementClickable(Xpath.LINK_APP_DEVICES);
		/*
		 * The "Create New Version" link is displayed.
		 */
		Assert.assertTrue(home.isElementPresent(DeviceInfo.CREATE_NEW_VERSION));
		// Delete device
		home.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the "View Published Version" link is displayed after Create New Version of an published app or device
	 */
	@Test
	public void TC640DADD_09(){
		result.addLog("ID : TC640DADD_09 : Verify that the 'View Published Version' link is displayed after Create New Version of an published app or device");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			VP: The 630D Device List page is displayed with "Add app or Device" link
			3. Add an app or device successfully
			4. Review the added app or device in 640D Device Detail page
			5. Click "Publish" link
			6. Click on Create New Version link
			7. Click Save button
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		/*
		 * VP: The 630D Device List page is displayed with "Add app or Device" link
		 */
		Assert.assertEquals(home.existsElement(DeviceList.getListXpath()).getResult(), "Pass");
		Assert.assertTrue(home.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
		// 3. Add an app or device successfully
		// Click Add App or Device link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// Create app/device
		Hashtable<String, String> data = TestData.appDevicePublish();
		home.createNewDevice(DeviceEdit.getHash(), data);
		// 4. Review the added app or device in 640D Device Detail page
		// 5. Click "Publish" link
		home.click(DeviceInfo.PUBLISH);
		home.waitForElementClickable(Xpath.LINK_APP_DEVICES);
		// 6. Click on Create New Version link
		home.click(DeviceInfo.CREATE_NEW_VERSION);
		home.selectConfirmationDialogOption("OK");
		// 7. Click Save button
		home.click(DeviceEdit.SAVE);
		/*
		 * The "View Published Version" link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(DeviceInfo.VIEW_PUBLISHED_VERSION));
		// Delete device
		home.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the "Publish" link is disappeared after publishing an app or device
	 */
	@Test
	public void TC640DADD_10(){
		result.addLog("ID : TC640DADD_10 : Verify that the 'Publish' link is disappeared after publishing an app or device");
		/*
			Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			VP: The 630D Device List page is displayed with "Add app or Device" link
			3. Add an app or device successfully
			4. Review the added app or device in 640D Device Detail page
			5. Click "Publish" link
		*/
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		/*
		 * VP: The 630D Device List page is displayed with "Add app or Device" link
		 */
		Assert.assertEquals(home.existsElement(DeviceList.getListXpath()).getResult(), "Pass");
		Assert.assertTrue(home.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
		// 3. Add an app or device successfully
		// Click Add App or Device link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// Create app/device
		Hashtable<String, String> data = TestData.appDevicePublish();
		home.createNewDevice(DeviceEdit.getHash(), data);
		// 4. Review the added app or device in 640D Device Detail page
		// 5. Click "Publish" link
		home.click(DeviceInfo.PUBLISH);
		home.waitForElementClickable(Xpath.LINK_APP_DEVICES);
		/*
		 * The "Suspend" instead of "Publish" link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(DeviceInfo.SUSPEND));
		Assert.assertFalse(home.isElementPresent(DeviceInfo.PUBLISH));
		// Delete device
		home.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify the the "Complete Profile" and "Init Database" links are enabled for a published device
	 */
	@Test
	public void TC640DADD_11(){
		result.addLog("ID : TC640DADD_11 : Verify the the 'Complete Profile' and 'Init Database' links are enabled for a published device");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navgate to "Apps & Devices" page
			VP: The 630D Device List page is displayed with "Add app or Device" link
			3. Add an app or device successfully
			4. Review the added app or device in 640D Device Detail page and the  "Complete Profile" and "Init Database" links are not visible
			5. Click "Publish" link
		*/
		// 2. Navgate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		/*
		 * VP: The 630D Device List page is displayed with "Add app or Device" link
		 */
		Assert.assertEquals(home.existsElement(DeviceList.getListXpath()).getResult(), "Pass");
		Assert.assertTrue(home.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
		// 3. Add an app or device successfully
		// Click on Add App or Device link
		// Create new device
		home.click(DeviceList.CREATE_NEW_DEVICE);
		Hashtable<String,String> data = TestData.appDevicePublish();
		ArrayList<String> deviceInfo = home.createNewDevice(DeviceEdit.getHash(), data);
		// 4. Review the added app or device in 640D Device Detail page and the  "Complete Profile" and "Init Database" links are not visible
		Assert.assertEquals(home.existsElement(DeviceInfo.getElementInfo()).getResult(), "Pass");
		Assert.assertTrue(DTSUtil.containsAll(data, deviceInfo));
		Assert.assertFalse(home.isElementPresent(DeviceInfo.COMPLETE_PROFILE_LINK));
		Assert.assertFalse(home.isElementPresent(DeviceInfo.INIT_DATABASE_LINK));
		// 5. Click "Publish" link
		home.click(DeviceInfo.PUBLISH);
		home.waitForElementClickable(Xpath.LINK_APP_DEVICES);
		/*
		 * Verify that The "Complete Profile" and "Init Database" links are enabled
		 */
		Assert.assertTrue(home.isElementPresent(DeviceInfo.COMPLETE_PROFILE_LINK));
		Assert.assertTrue(home.isElementPresent(DeviceInfo.INIT_DATABASE_LINK));
		// Delete device
		home.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the "Default Audio Route" link is displayed first in "Audio Routes" section.
	 */
	@Test
	public void TC640DADD_12(){
		result.addLog("ID : TC640DADD_12 : Verify that the 'Default Audio Route' link is displayed first in 'Audio Routes' section.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Select a model from apps & devices table
		*/
		/*
		 * PreCondition: Create new device
		 */
		// Navigate to Apps & Devices page
		home.click(Xpath.LINK_APP_DEVICES);
		// Click Add App or Device link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDevicePublish();
		data.put("custom tuning", Constant.AUDIO_ROUTE_FILE);
		home.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * *********************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Select a model from apps & devices table
		home.selectADeviceByName(data.get("name"));
		/*
		 * Verify that The 640D Device Detail page is displayed and "Default Audio Route" link in "Audio Routes" link is displayed above all others
		 */
		Assert.assertEquals(home.getTextByXpath(DeviceInfo.NAME), data.get("name"));
		Assert.assertTrue(home.isElementDisplayVertically(DeviceInfo.DEFAULT_AUDIO_ROUTE_FILE, DeviceInfo.CUSTOM_TUNING_FILE));
		// Delete device
		home.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the portal is redirected to 630Db Device List page and the Products is filetered for specific company when clicking on the company link
	 */
	@Test
	public void TC640DADD_13(){
		result.addLog("ID : TC640DADD_13 : Verify that the portal is redirected to 630Db Device List page and the Products is filetered for specific company when clicking on the company link");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Select a model from apps & devices table
			VP: The 640D Device Detail page is displayed and the hyperlink company name is also displayed on top of page
			4. Click on the hyperlink company name
		*/
		/*
		 * PreCondition: Create new app/device
		 */
		// Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// Click Add App or Device link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// Create app/device
		Hashtable<String,String> data = TestData.appDeviceDraft();
		home.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * ****************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 3. Select a model from apps & devices table
		home.selectADeviceByName(data.get("name"));
		// VP: The 640D Device Detail page is displayed and the hyperlink company name is also displayed on top of page
		Assert.assertEquals(home.existsElement(DeviceInfo.getElementInfo()).getResult(), "Pass");
		Assert.assertTrue(home.isElementPresent(DeviceInfo.TITLE_COMPANY));
		// 4. Click on the hyperlink company name
		home.click(DeviceInfo.TITLE_COMPANY);
		/*
		 * 630Db Device List page and the Products is filetered for specific company 
		 */
		Assert.assertEquals(home.existsElement(DeviceList.getListXpath()).getResult(), "Pass");
		Assert.assertTrue(home.verifyValueColumn(DeviceList.TABLE, "Company", partner_company_name));
	}
	
	/*
	 * Verify that the device promotion is displayed for a  target app/device even when the Global promotion is ON or OFF
	 */
	@Test
	public void TC640DADD_14() {
		result.addLog("ID : TC640DADD_14 : Verify that the device promotion is displayed for a  target app/device even when the Global promotion is ON or OFF");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Fill valid value into all required fields
			5. Click “Save” link with default Global Promotion state is  ON
			VP: New app/device is created successfully
			6. Navigate to “Promotions” page
			7. Click “Add Promo” link
			8. Select a product from products dropdown
			9. Set the promotion type to “Device”
			10. Set the target to above app/device
			11. Click “Save” link
			VP: New device promotion is created successfully
			12. Click “Publish” link
			VP: The device promotion is published successfully
			13. Navigate to “Apps & Devices” page
			14. Select above app/device from apps/devices table
			VP: The device promotion is displayed under Device Promotion section
			15. Click on “Edit” link
			16. Change the Global Promotion state to OFF
			17. Click “Save” link
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
		// Navigate to Promotion page
		home.click(Xpath.linkPromotions);
		// Click Add promo link
		home.click(PromotionList.ADD_PROMO);
		// Create Global promotion
		Hashtable<String,String> dataGlobalPromotion = TestData.promotionGlobal(dataProduct1.get("brand") + " " + dataProduct1.get("name"));
		home.addPromotion(AddPromotion.getHash(), dataGlobalPromotion);
		// Publish promotion
		home.click(PromotionInfo.PUBLISH);
		/*
		 * ********************************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.linkDevice);
		// 3. Click “Add App or Device” link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		// 5. Click “Save” link with default Global Promotion state is ON
		Hashtable<String,String> dataDevice = TestData.appDeviceDraft();
		ArrayList<String> dataActual = home.createNewDevice(DeviceEdit.getHash(), dataDevice);
		/*
		 * VP: New app/device is created successfully
		 */
		Assert.assertTrue(DTSUtil.containsAll(dataDevice, dataActual));
		// 6. Navigate to “Promotions” page
		home.click(Xpath.linkPromotions);
		// 7. Click “Add Promo” link
		home.click(PromotionList.ADD_PROMO);
		// 8. Select a product from products dropdown
		// 9. Set the promotion type to “Device”
		// 10. Set the target to above app/device
		// 11. Click “Save” link
		Hashtable<String,String> dataDevicePromotion = TestData.promotionAppDevice(dataProduct2.get("brand") + " " + dataProduct2.get("name"), dataDevice.get("brand"), dataDevice.get("name"));
		home.addPromotion(AddPromotion.getHash(), dataDevicePromotion);
		/*
		 * VP: New device promotion is created successfully
		 */
		Assert.assertEquals(home.getTextByXpath(PromotionInfo.PROMOTION_NAME), dataDevicePromotion.get("name"));
		// 12. Click “Publish” link
		home.click(PromotionInfo.PUBLISH);
		/*
		 *  VP: The device promotion is published successfully
		 */
		Assert.assertEquals(home.getTextByXpath(PromotionInfo.PUBLISH_STATUS), "PUBLISHED");
		// 13. Navigate to “Apps & Devices” page
		home.click(Xpath.linkDevice);
		// 14. Select above app/device from apps/devices table
		home.selectADeviceByName(dataDevice.get("name"));
		/*
		 * VP: The device promotion is displayed under Device Promotion section
		 */
		Assert.assertTrue(home.checkADevicePromotionExistByName(dataGlobalPromotion.get("name").replace(" ", " - ")));
		Assert.assertTrue(home.checkADevicePromotionExistByName(dataDevicePromotion.get("name").replace(" ", " - ")));
		// 15. Click on “Edit” link
		home.click(DeviceInfo.EDIT);
		// 16. Change the Global Promotion state to OFF
		home.click(DeviceEdit.GLOBAL_PROMOTIONS_SWITCH);
		// 17. Click “Save” link
		home.click(DeviceEdit.SAVE);
		/*
		 * The device promotion is still displayed and global promotions are disappeared
		 */
		Assert.assertFalse(home.checkADevicePromotionExistByName(dataGlobalPromotion.get("name").replace(" ", " - ")));
		Assert.assertTrue(home.checkADevicePromotionExistByName(dataDevicePromotion.get("name").replace(" ", " - ")));
		// Delete device
		home.doDelete(DeviceInfo.DELETE);
		// Delete product
		home.deleteAnaccessorybyName(dataProduct1.get("name"));
		home.deleteAnaccessorybyName(dataProduct2.get("name"));
	}
	
	/*
	 * Verify that the device promotion is only displayed for targeted device
	 */
	@Test
	public void TC640DADD_15() {
		result.addLog("ID : TC640DADD_15 : Verify that the device promotion is only displayed for targeted device");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Fill valid value into all required fields
			5. Click “Save” link
			VP: New app/device is created successfully
			6. Navigate to “Promotions” page
			7. Click “Add Promo” link
			8. Select a product from products dropdown
			9. Set the promotion type to “Device”
			10. Set the target to above app/device
			11. Click “Save” link
			VP: New device promotion is created successfully
			12. Click “Publish” link
			VP: The device promotion is published successfully
			13. Navigate to “Apps & Devices” page
			14. Select above app/device from apps/devices table
			VP: The device promotion is displayed under Device Promotion section
			15. Click on “Apps & Devices” tab
			16. Select another app/device from apps/devices table
		*/
		/*
		 * PreCondition: Create new published product and device
		 */
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create published product
		Hashtable<String,String> dataProduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct);
		// Navigate to App & Devices page
		home.click(Xpath.linkDevice);
		// Click Add app or device link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> dataDevice1 = TestData.appDeviceDraft();
		home.createNewDevice(DeviceEdit.getHash(), dataDevice1);
		/*
		 * **********************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.linkDevice);
		// 3. Click “Add App or Device” link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		// 5. Click “Save” link
		Hashtable<String,String> dataDevice2 = TestData.appDeviceDraft();
		ArrayList<String> dataActual = home.createNewDevice(DeviceEdit.getHash(), dataDevice2);
		/*
		 * VP: New app/device is created successfully
		 */
		Assert.assertTrue(DTSUtil.containsAll(dataDevice2, dataActual));
		// 6. Navigate to “Promotions” page
		home.click(Xpath.linkPromotions);
		// 7. Click “Add Promo” link
		home.click(PromotionList.ADD_PROMO);
		// 8. Select a product from products dropdown
		// 9. Set the promotion type to “Device”
		// 10. Set the target to above app/device
		// 11. Click “Save” link
		Hashtable<String,String> dataDevicePromotion = TestData.promotionAppDevice(dataProduct.get("brand") + " " + dataProduct.get("name"), dataDevice2.get("brand"), dataDevice2.get("name"));
		home.addPromotion(AddPromotion.getHash(), dataDevicePromotion);
		/*
		 * VP: New device promotion is created successfully
		 */
		Assert.assertEquals(home.getTextByXpath(PromotionInfo.PROMOTION_NAME), dataDevicePromotion.get("name"));
		// 12. Click “Publish” link
		home.click(PromotionInfo.PUBLISH);
		/*
		 *  VP: The device promotion is published successfully
		 */
		Assert.assertEquals(home.getTextByXpath(PromotionInfo.PUBLISH_STATUS), "PUBLISHED");
		// 13. Navigate to “Apps & Devices” page
		home.click(Xpath.linkDevice);
		// 14. Select above app/device from apps/devices table
		home.selectADeviceByName(dataDevice2.get("name"));
		/*
		 * VP: The device promotion is displayed under Device Promotion section
		 */
		Assert.assertTrue(home.checkADevicePromotionExistByName(dataDevicePromotion.get("name").replace(" ", " - ")));
		// 15. Click on “Apps & Devices” tab				
		home.click(Xpath.linkDevice);		
		// 16. Select another app/device from apps/devices table
		home.selectADeviceByName(dataDevice1.get("name"));
		/*
		 * The device promotion is not displayed for un-targeted app/device
		 */
		Assert.assertFalse(home.checkADevicePromotionExistByName(dataDevicePromotion.get("name").replace(" ", " - ")));
		// Delete device
		home.doDelete(DeviceInfo.DELETE);
		// Delete product
		home.deleteAnaccessorybyName(dataProduct.get("name"));
	}
	
	/*
	 * Verify that the manual deleted promotions are not added back to promotion slots when turning Global Promotion feature ON
	 */
	@Test
	public void TC640DADD_16() {
		result.addLog("ID : TC640DADD_16 : Verify that the manual deleted promotions are not added back to promotion slots when turning Global Promotion feature ON");
		/*
		 	Pre-Condition: There are some promotions already published.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Fill valid value into all required fields
			5. Click “Save” link with default Global Promotion state
			VP: New app/device is created successfully and the published promotions are added into Device Promotions section
			6. Click “Edit” link
			7. Click a “Delete” link which is next to the promotion slots
			8. Click “OK” on delete confirmation dialog
			9. Click “Save” link
			VP: The manual deleted promotion is not displayed under Device promotion section
			10. Click “Edit” link
			11. Turn the Global Promotion feature OFF
			12. Click “Save” link
			VP: All global promotions are not displayed under Device Promotion section
			13. Click “Edit” link
			14. Turn the global promotion feature ON
			15. Click “Save” link
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
		// Navigate to Promotion page
		home.click(Xpath.linkPromotions);
		// Click Add promo link
		home.click(PromotionList.ADD_PROMO);
		// Create 1st Global promotion
		Hashtable<String,String> dataGlobalPromotion1 = TestData.promotionGlobal(dataProduct1.get("brand") + " " + dataProduct1.get("name"));
		home.addPromotion(AddPromotion.getHash(), dataGlobalPromotion1);
		// Publish promotion
		home.click(PromotionInfo.PUBLISH);
		// Navigate to Promotion page
		home.click(Xpath.linkPromotions);
		// Click Add promo link
		home.click(PromotionList.ADD_PROMO);
		// Create 2nd Global promotion
		Hashtable<String,String> dataGlobalPromotion2 = TestData.promotionGlobal(dataProduct2.get("brand") + " " + dataProduct2.get("name"));
		home.addPromotion(AddPromotion.getHash(), dataGlobalPromotion2);
		// Publish promotion
		home.click(PromotionInfo.PUBLISH);
		/*
		 * *******************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.linkDevice);
		// 3. Click “Add App or Device” link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		// 5. Click “Save” link with default Global Promotion state
		Hashtable<String,String> dataDevice = TestData.appDeviceDraft();
		ArrayList<String> dataActual = home.createNewDevice(DeviceEdit.getHash(), dataDevice);
		/*
		 * VP: New app/device is created successfully and the published promotions are added into Device Promotions section
		 */
		Assert.assertTrue(DTSUtil.containsAll(dataDevice, dataActual));
		Assert.assertTrue(home.checkADevicePromotionExistByName(dataGlobalPromotion1.get("name").replace(" ", " - ")));
		Assert.assertTrue(home.checkADevicePromotionExistByName(dataGlobalPromotion2.get("name").replace(" ", " - ")));
		// 6. Click “Edit” link
		home.click(DeviceInfo.EDIT);
		// 7. Click a “Delete” link which is next to the promotion slots
		// 8. Click “OK” on delete confirmation dialog
		home.deleteADevicePromotionByName(DeviceEdit.PROMOTION_CONTAINER, dataGlobalPromotion1.get("name"));
		// 9. Click “Save” link
		home.click(DeviceEdit.SAVE);
		/*
		 * VP: The manual deleted promotion is not displayed under Device promotion section
		 */
		Assert.assertFalse(home.checkADevicePromotionExistByName(dataGlobalPromotion1.get("name").replace(" ", " - ")));
		// 10. Click “Edit” link
		home.click(DeviceInfo.EDIT);
		// 11. Turn the Global Promotion feature OFF
		home.click(DeviceEdit.GLOBAL_PROMOTIONS_SWITCH);
		// 12. Click “Save” link
		home.click(DeviceEdit.SAVE);
		/*
		 * VP: All global promotions are not displayed under Device Promotion section
		 */
		Assert.assertFalse(home.checkADevicePromotionExistByName(dataGlobalPromotion1.get("name").replace(" ", " - ")));
		Assert.assertFalse(home.checkADevicePromotionExistByName(dataGlobalPromotion2.get("name").replace(" ", " - ")));
		// 13. Click “Edit” link
		home.click(DeviceInfo.EDIT);
		// 14. Turn the Global Promotion feature OFF
		home.click(DeviceEdit.GLOBAL_PROMOTIONS_SWITCH);
		// 15. Click “Save” link
		home.click(DeviceEdit.SAVE);
		/*
		 * The manual deleted promotions are not added back to promotion slots
		 */
		Assert.assertFalse(home.checkADevicePromotionExistByName(dataGlobalPromotion1.get("name").replace(" ", " - ")));
		Assert.assertTrue(home.checkADevicePromotionExistByName(dataGlobalPromotion2.get("name").replace(" ", " - ")));
		// Delete device
		home.doDelete(DeviceInfo.DELETE);
		// Delete product
		home.deleteAnaccessorybyName(dataProduct1.get("name"));
		home.deleteAnaccessorybyName(dataProduct2.get("name"));
	}
	
	/*
	 * Verify that the manual deleted promotions are not added back to promotion slots when the new version of app/device is created
	 */
	@Test
	public void TC640DADD_17() {
		result.addLog("ID : TC640DADD_17 : Verify that the manual deleted promotions are not added back to promotion slots when the new version of app/device is created");
		/*
		 	Pre-Condition: There are some promotions already published.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click “Add App or Device” link
			4. Fill valid value into all required fields
			5. Click “Save” link with default Global Promotion state
			VP: New app/device is created successfully and the published promotions are added into Device Promotions section
			6. Click “Edit” link
			7. Click a “Delete” link which is next to the promotion slots
			8. Click “OK” on delete confirmation dialog
			9. Click “Save” link
			VP: The manual deleted promotion is not displayed under Device promotion section
			10. Click “Publish” link
			VP: The app/device is published successfully
			11. Click “Update Product Info” link
			12. Click “OK” button on confirmation dialog
			VP: The app/device edit page is displayed
			13. Change some value of app/device
			14. Click “Save” link
		*/
		/*
		 * PreCondition: Create new global promotion
		 */
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create published product
		Hashtable<String,String> dataProduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct);
		// Navigate to Promotion page
		home.click(Xpath.linkPromotions);
		// Click Add promo link
		home.click(PromotionList.ADD_PROMO);
		// Create Global promotion
		Hashtable<String,String> dataGlobalPromotion = TestData.promotionGlobal(dataProduct.get("brand") + " " + dataProduct.get("name"));
		home.addPromotion(AddPromotion.getHash(), dataGlobalPromotion);
		// Publish promotion
		home.click(PromotionInfo.PUBLISH);
		/*
		 * *************************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		home.click(Xpath.linkDevice);
		// 3. Click “Add App or Device” link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		// 5. Click “Save” link with default Global Promotion state
		Hashtable<String,String> dataDevice = TestData.appDevicePublish();
		dataDevice.remove("global promotion");
		home.createNewDevice(DeviceEdit.getHash(), dataDevice);
		/*
		 * VP: New app/device is created successfully and the published promotions are added into Device Promotions section
		 */
		Assert.assertEquals(home.getTextByXpath(DeviceInfo.NAME), dataDevice.get("name"));
		Assert.assertTrue(home.checkADevicePromotionExistByName(dataGlobalPromotion.get("name").replace(" ", " - ")));
		// 6. Click “Edit” link
		home.click(DeviceInfo.EDIT);
		// 7. Click a “Delete” link which is next to the promotion slots
		// 8. Click “OK” on delete confirmation dialog
		home.deleteADevicePromotionByName(DeviceEdit.PROMOTION_CONTAINER, dataGlobalPromotion.get("name"));
		// 9. Click “Save” link
		home.click(DeviceEdit.SAVE);
		/*
		 * VP: The manual deleted promotion is not displayed under Device promotion section
		 */
		Assert.assertFalse(home.checkADevicePromotionExistByName(dataGlobalPromotion.get("name").replace(" ", " - ")));
		// 10. Click “Publish” link
		home.click(DeviceInfo.PUBLISH);
		/*
		 * VP: The app/device is published successfully
		 */
		Assert.assertEquals(home.getTextByXpath(DeviceInfo.PUBLICATION_STATUS), "Published");
		// 11. Click “Update Product Info” link
		home.click(DeviceInfo.CREATE_NEW_VERSION);
		// 12. Click “OK” button on confirmation dialog
		home.selectConfirmationDialogOption("OK");
		/*
		 * VP: The app/device edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(DeviceEdit.getHash()).getResult(), "Pass");
		// 13. Change some value of app/device
		String newName = "NewDevice" + RandomStringUtils.randomNumeric(7);
		home.editData(DeviceEdit.NAME, newName);
		// 14. Click “Save” link
		home.click(DeviceEdit.SAVE);
		// Check if PDPP-1168 found
		if(home.checkADevicePromotionExistByName(dataGlobalPromotion.get("name").replace(" ", " - "))){
			result.addErrorLog("PDPP-1168: 640 Product Detail: The manual deleted promotions in previous app/device version are auto added back to newest version");
			home.deleteAnaccessorybyName(dataProduct.get("name"));
			Assert.assertTrue(false);
		}
	}
	
}
