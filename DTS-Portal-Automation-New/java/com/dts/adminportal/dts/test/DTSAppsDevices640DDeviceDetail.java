package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.AppDeviceController;
import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AudioRoutesEdit;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AddPromotion;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.DeviceEdit;
import com.dts.adminportal.model.DeviceInfo;
import com.dts.adminportal.model.DeviceList;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PromotionInfo;
import com.dts.adminportal.model.PromotionList;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.TestData;

public class DTSAppsDevices640DDeviceDetail extends BasePage{

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
	}
	
	/*
	 * Verify that the 640D Device detail page shows up "Product Information", "Headphone:X Licensing Info", "Audio Routes" and "Featured Accessory Promotions" sections.
	 */
	@Test
	public void TC640DADD_01(){
		appDeviceControl.addLog("ID : TC640DADD_01 : Verify that the 640D Device detail page shows up 'Product Information', 'Headphone:X Licensing Info', 'Audio Routes' and 'Featured Accessory Promotions' sections.");
		/*
		 	PreCondition: the DTS user has "Add and manage authorized productS" privilege.
			1. Log into DTS portal as above DTS admin user
			2. Navigate to "Apps & Devices" page
			3. Select a device from app & device table
		*/
		/*
		 * PreCondition: Create new app/device
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// Click Add App or Device link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create app/device
		Hashtable<String,String> data = TestData.appDeviceDraft();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * ****************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Select a device from app & device table
		appDeviceControl.selectADeviceByName(data.get("name"));
		/*
		 * The 640D Device Detail page is displayed an contains 
		 * "Product Information", 
		 * "Headphone:X Licensing Info", 
		 * "Audio Routes" and 
		 * "Featured Accessory Promotions" sections.
		 */
		Assert.assertEquals(true, appDeviceControl.existsElement(DeviceInfo.getSections()));
		// Delete device
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that  the suspend/delete confirmation dialog is displayed when suspending an app or device.
	 */
	@Test
	public void TC640DADD_02(){
		appDeviceControl.addLog("ID : TC640DADD_02 : Verify that  the suspend/delete confirmation dialog is displayed when suspending an app or device.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized productS" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Select a published model from app & device table
				VP: The 640D Device Detail page is displayed
			4. Click "Suspend" link
				VP: The suspend confirmation dialog is displayed with "Suspend" and "Cancel" buttons.
			5. Click "Cancel" button
			6. Click "Delete" link
				VP: The delete confirmation dialog is displayed with "Delete" and "Cancel" buttons.

		*/
		/*
		 * Pre-condition: Create a new published app/device
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// Click "Add app or device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDevicePublish();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// Publish device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		appDeviceControl.waitForElementClickable(PageHome.LINK_APP_DEVICES);
		/*
		 * ***********************************************************
		 */
		// 2. Navgate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Select a published model from app & device table
		appDeviceControl.selectADeviceByName(data.get("name"));
		/*
		 * VP: The 640D Device Detail page is displayed
		 */
		Assert.assertEquals(true,appDeviceControl.existsElement(DeviceInfo.getElementInfo("Published")));
		// 4. Click "Suspend" link
		appDeviceControl.click(DeviceInfo.SUSPEND);
		appDeviceControl.waitForElementClickable(PageHome.BTN_CONFIRMATION_DANGER);
		/*
		 * The suspend confirmation dialog is displayed with "Suspend" and "Cancel" buttons
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(PageHome.BTN_CONFIRMATION_DANGER), "Suspend");
		Assert.assertEquals(appDeviceControl.getTextByXpath(PageHome.BTN_CONFIRMATION_CANCEL), "Cancel");
		//5. Click "Cancel" button
		appDeviceControl.click(PageHome.BTN_CONFIRMATION_CANCEL);
		//6. Click "Delete" link
		appDeviceControl.click(DeviceInfo.DELETE);
		appDeviceControl.waitForElementClickable(PageHome.BTN_CONFIRMATION_DANGER);
		/*
		 * The delete confirmation dialog is displayed with "Delete" and "Cancel" buttons
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(PageHome.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(appDeviceControl.getTextByXpath(PageHome.BTN_CONFIRMATION_CANCEL), "Cancel");
		// Delete device
		appDeviceControl.click(PageHome.BTN_CONFIRMATION_CANCEL);
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	
	/*
	 * Verify that the "Suspend " link is not displayed for an un-published model
	 * Verify for the links displayed after an app/device is published and create new version
	 */
	@Test
	public void TC640DADD_06(){
		appDeviceControl.addLog("ID : TC640DADD_06 : Verify that the 'Suspend' link is not displayed for an un-published model");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			VP: The 630D Device List page is displayed with "Add app or Device" link
			3. Add an app or device successfully
			4. Review the added app or device in 640D Device Detail page
			VP: The "Suspend" link is not displayed in "Actions" module.
			5. Click "Publish" link
			"VP: The ""Suspend"" link is displayed in ""Actions"" module after publishing.
			VP: The ""Create New Version"" link is displayed.
			VP: The ""Suspend"" instead of ""Publish"" link is displayed.
			VP: The  ""Complete Profile"" and ""Init Database"" links are enabled
			VP: The ""View Published Version"" link is not displayed."
			6. Click on Create New Version link
			7. Click Save button
			VP: The "View Published Version" link isdisplayed.

		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		/*
		 * VP: The 630D Device List page is displayed with "Add app or Device" link
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceList.getListXpath()), true);
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
		// 3. Add an app or device successfully
		// Click on Add App or Device link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDevicePublish();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// 4. Review the added app or device in 640D Device Detail page
		/*
		 * Verify that The "Suspend" link is not displayed in "Actions" module
		 */
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.SUSPEND));
		//5. Click "Publish" link
		/*
		 * "VP: The ""Suspend"" link is displayed in ""Actions"" module after publishing.
		VP: The ""Create New Version"" link is displayed.
		VP: The ""Suspend"" instead of ""Publish"" link is displayed.
		VP: The  ""Complete Profile"" and ""Init Database"" links are enabled
		VP: The ""View Published Version"" link is not displayed."
		*/
		appDeviceControl.click(DeviceInfo.PUBLISH);
		appDeviceControl.waitForElementClickable(PageHome.LINK_APP_DEVICES);
		/*
		 * The "Suspend" link is displayed in "Actions" module after publishing
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.SUSPEND));
		//VP: The ""Create New Version"" link is displayed.
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.CREATE_NEW_VERSION));
		//VP: The ""Suspend"" instead of ""Publish"" link is displayed.
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.SUSPEND));
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.PUBLISH));
		//VP: The  ""Complete Profile"" and ""Init Database"" links are enabled
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.COMPLETE_PROFILE_LINK));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.INIT_DATABASE_LINK));
		//6. Click on Create New Version link
		appDeviceControl.click(DeviceInfo.CREATE_NEW_VERSION);
		appDeviceControl.selectConfirmationDialogOption("OK");
		//7. Click Save button
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * The "View Published Version" link is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.VIEW_PUBLISHED_VERSION));
		//Delete device
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the "Default Audio Route" link is displayed first in "Audio Routes" section.
	 */
	@Test
	public void TC640DADD_12(){
		appDeviceControl.addLog("ID : TC640DADD_12 : Verify that the 'Default Audio Route' link is displayed first in 'Audio Routes' section.");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Select a model from apps & devices table
		*/
		/*
		 * PreCondition: Create new device
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Apps & Devices page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// Click Add App or Device link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDevicePublish();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * *********************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Select a model from apps & devices table
		appDeviceControl.selectADeviceByName(data.get("name"));
		/*
		 * Verify that The 640D Device Detail page is displayed and "Default Audio Route" link in "Audio Routes" link is displayed above all others
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), data.get("name"));
		Assert.assertTrue(appDeviceControl.isElementDisplayVertically(DeviceInfo.AUDIO_ROUTES, DeviceInfo.DEFAULT_AUDIO_ROUTE_FILE));
		// Delete device
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the portal is redirected to 630Db Device List page and the Products is filetered for specific company when clicking on the company link
	 */
	@Test
	public void TC640DADD_13(){
		appDeviceControl.addLog("ID : TC640DADD_13 : Verify that the portal is redirected to 630Db Device List page and the Products is filetered for specific company when clicking on the company link");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// Click Add App or Device link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create app/device
		Hashtable<String,String> data = TestData.appDeviceDraft();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * ****************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Select a model from apps & devices table
		appDeviceControl.selectADeviceByName(data.get("name"));
		// VP: The 640D Device Detail page is displayed and the hyperlink company name is also displayed on top of page
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")), true);
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.TITLE_COMPANY));
		// 4. Click on the hyperlink company name
		appDeviceControl.click(DeviceInfo.TITLE_COMPANY);
		/*
		 * 630Db Device List page and the Products is filetered for specific company 
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceList.getListXpath()), true);
		Assert.assertTrue(appDeviceControl.verifyValueColumn(DeviceList.TABLE, "Company", PARTNER_COMPANY_NAME));
	}
	
	/*
	 * Verify that the device promotion is displayed for a  target app/device even when the Global promotion is ON or OFF
	 */
	@Test
	public void TC640DADD_14() {
		appDeviceControl.addLog("ID : TC640DADD_14 : Verify that the device promotion is displayed for a  target app/device even when the Global promotion is ON or OFF");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click �Add App or Device� link
			4. Fill valid value into all required fields
			5. Click �Save� link with default Global Promotion state is  ON
			VP: New app/device is created successfully
			6. Navigate to �Promotions� page
			7. Click �Add Promo� link
			8. Select a product from products dropdown
			9. Set the promotion type to �Device�
			10. Set the target to above app/device
			11. Click �Save� link
			VP: New device promotion is created successfully
			12. Click �Publish� link
			VP: The device promotion is published successfully
			13. Navigate to �Apps & Devices� page
			14. Select above app/device from apps/devices table
			VP: The device promotion is displayed under Device Promotion section
			15. Click on �Edit� link
			16. Change the Global Promotion state to OFF
			17. Click �Save� link
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
		// Navigate to Promotion page
		appDeviceControl.click(PageHome.linkPromotions);
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create Global promotion
		Hashtable<String,String> dataGlobalPromotion = TestData.promotionGlobal(dataProduct1.get("brand") + " " + dataProduct1.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataGlobalPromotion);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		/*
		 * ********************************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.linkDevice);
		// 3. Click �Add App or Device� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		// 5. Click �Save� link with default Global Promotion state is ON
		Hashtable<String,String> dataDevice = TestData.appDeviceDraft();
		ArrayList<String> dataActual = appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice);
		/*
		 * VP: New app/device is created successfully
		 */
		Assert.assertTrue(ListUtil.containsAll(dataDevice, dataActual));
		// 6. Navigate to �Promotions� page
		appDeviceControl.click(PageHome.linkPromotions);
		// 7. Click �Add Promo� link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// 8. Select a product from products dropdown
		// 9. Set the promotion type to �Device�
		// 10. Set the target to above app/device
		// 11. Click �Save� link
		Hashtable<String,String> dataDevicePromotion = TestData.promotionAppDevice(dataProduct2.get("brand") + " " + dataProduct2.get("name"), dataDevice.get("brand"), dataDevice.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataDevicePromotion);
		/*
		 * VP: New device promotion is created successfully
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(PromotionInfo.PROMOTION_NAME), dataDevicePromotion.get("name"));
		// 12. Click �Publish� link
		appDeviceControl.click(PromotionInfo.PUBLISH);
		/*
		 *  VP: The device promotion is published successfully
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(PromotionInfo.PUBLISH_STATUS), "PUBLISHED");
		// 13. Navigate to �Apps & Devices� page
		appDeviceControl.click(PageHome.linkDevice);
		// 14. Select above app/device from apps/devices table
		appDeviceControl.selectADeviceByName(dataDevice.get("name"));
		/*
		 * VP: The device promotion is displayed under Device Promotion section
		 */
		Assert.assertTrue(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion.get("name").replace(" ", " - ")));
		Assert.assertTrue(appDeviceControl.checkADevicePromotionExistByName(dataDevicePromotion.get("name").replace(" ", " - ")));
		// 15. Click on �Edit� link
		appDeviceControl.click(DeviceInfo.EDIT);
		// 16. Change the Global Promotion state to OFF
		appDeviceControl.click(DeviceEdit.GLOBAL_PROMOTIONS_ON);
		// 17. Click �Save� link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * The device promotion is still displayed and global promotions are disappeared
		 */
		Assert.assertFalse(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion.get("name").replace(" ", " - ")));
		Assert.assertTrue(appDeviceControl.checkADevicePromotionExistByName(dataDevicePromotion.get("name").replace(" ", " - ")));
		// Delete device
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		// Delete product
		productControl.deleteAccessorybyName(dataProduct1.get("name"));
		productControl.deleteAccessorybyName(dataProduct2.get("name"));
	}
	
	/*
	 * Verify that the device promotion is only displayed for targeted device
	 */
	@Test
	public void TC640DADD_15() {
		appDeviceControl.addLog("ID : TC640DADD_15 : Verify that the device promotion is only displayed for targeted device");
		/*
		 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click �Add App or Device� link
			4. Fill valid value into all required fields
			5. Click �Save� link
			VP: New app/device is created successfully
			6. Navigate to �Promotions� page
			7. Click �Add Promo� link
			8. Select a product from products dropdown
			9. Set the promotion type to �Device�
			10. Set the target to above app/device
			11. Click �Save� link
			VP: New device promotion is created successfully
			12. Click �Publish� link
			VP: The device promotion is published successfully
			13. Navigate to �Apps & Devices� page
			14. Select above app/device from apps/devices table
			VP: The device promotion is displayed under Device Promotion section
			15. Click on �Apps & Devices� tab
			16. Select another app/device from apps/devices table
		*/
		/*
		 	PreCondition: Create new published product and device
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		appDeviceControl.click(PageHome.linkAccessories);
		// Click Add product link
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
		// Create published product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		// Navigate to App & Devices page
		appDeviceControl.click(PageHome.linkDevice);
		// Click Add app or device link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> dataDevice1 = TestData.appDeviceDraft();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice1);
		/*
		 * **********************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.linkDevice);
		// 3. Click �Add App or Device� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		// 5. Click �Save� link
		Hashtable<String,String> dataDevice2 = TestData.appDeviceDraft();
		ArrayList<String> dataActual = appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice2);
		/*
		 * VP: New app/device is created successfully
		 */
		Assert.assertTrue(ListUtil.containsAll(dataDevice2, dataActual));
		// 6. Navigate to �Promotions� page
		appDeviceControl.click(PageHome.linkPromotions);
		// 7. Click �Add Promo� link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// 8. Select a product from products dropdown
		// 9. Set the promotion type to �Device�
		// 10. Set the target to above app/device
		// 11. Click �Save� link
		Hashtable<String,String> dataDevicePromotion = TestData.promotionAppDevice(dataProduct.get("brand") + " " + dataProduct.get("name"), dataDevice2.get("brand"), dataDevice2.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataDevicePromotion);
		/*
		 * VP: New device promotion is created successfully
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(PromotionInfo.PROMOTION_NAME).replaceAll("\\s", " "),dataDevicePromotion.get("name").replaceAll("\\s", " "));
		// 12. Click �Publish� link
		appDeviceControl.click(PromotionInfo.PUBLISH);
		/*
		 *  VP: The device promotion is published successfully
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(PromotionInfo.PUBLISH_STATUS), "PUBLISHED");
		// 13. Navigate to �Apps & Devices� page
		appDeviceControl.click(PageHome.linkDevice);
		// 14. Select above app/device from apps/devices table
		appDeviceControl.selectADeviceByName(dataDevice2.get("name"));
		/*
		 * VP: The device promotion is displayed under Device Promotion section
		 */
		Assert.assertTrue(appDeviceControl.checkADevicePromotionExistByName(dataDevicePromotion.get("name").replace(" ", " - ")));
		// 15. Click on �Apps & Devices� tab				
		appDeviceControl.click(PageHome.linkDevice);		
		// 16. Select another app/device from apps/devices table
		appDeviceControl.selectADeviceByName(dataDevice1.get("name"));
		/*
		 * The device promotion is not displayed for un-targeted app/device
		 */
		Assert.assertFalse(appDeviceControl.checkADevicePromotionExistByName(dataDevicePromotion.get("name").replace(" ", " - ")));
		// Delete device
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		// Delete product
		productControl.deleteAccessorybyName(dataProduct.get("name"));
	}
	
	/*
	 * Verify that the manual deleted promotions are not added back to promotion slots when turning Global Promotion feature ON
	 */
	@Test
	public void TC640DADD_16() {
		appDeviceControl.addLog("ID : TC640DADD_16 : Verify that the manual deleted promotions are not added back to promotion slots when turning Global Promotion feature ON");
		/*
		 	Pre-Condition: There are some promotions already published.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click �Add App or Device� link
			4. Fill valid value into all required fields
			5. Click �Save� link with default Global Promotion state
			VP: New app/device is created successfully and the published promotions are added into Device Promotions section
			6. Click �Edit� link
			7. Click a �Delete� link which is next to the promotion slots
			8. Click �OK� on delete confirmation dialog
			9. Click �Save� link
			
			VP: The manual deleted promotion is not displayed under Device promotion section
			10. Click �Edit� link
			11. Turn the Global Promotion feature OFF
			12. Click �Save� link
			VP: All global promotions are not displayed under Device Promotion section
			13. Click �Edit� link
			14. Turn the global promotion feature ON
			15. Click �Save� link
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
		// Navigate to Promotion page
		appDeviceControl.click(PageHome.linkPromotions);
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create 1st Global promotion
		Hashtable<String,String> dataGlobalPromotion1 = TestData.promotionGlobal(dataProduct1.get("brand") + " " + dataProduct1.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataGlobalPromotion1);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		// Navigate to Promotion page
		appDeviceControl.click(PageHome.linkPromotions);
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create 2nd Global promotion
		Hashtable<String,String> dataGlobalPromotion2 = TestData.promotionGlobal(dataProduct2.get("brand") + " " + dataProduct2.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataGlobalPromotion2);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		/*
		 * *******************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.linkDevice);
		// 3. Click �Add App or Device� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		// 5. Click �Save� link with default Global Promotion state
		Hashtable<String,String> dataDevice = TestData.appDeviceDraft();
		ArrayList<String> dataActual = appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice);
		/*
		 * VP: New app/device is created successfully and the published promotions are added into Device Promotions section
		 */
		Assert.assertTrue(ListUtil.containsAll(dataDevice, dataActual));
		Assert.assertTrue(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion1.get("name").replace(" ", " - ")));
		Assert.assertTrue(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion2.get("name").replace(" ", " - ")));
		// 6. Click �Edit� link
		appDeviceControl.click(DeviceInfo.EDIT);
		// 7. Click a �Delete� link which is next to the promotion slots
		// 8. Click �OK� on delete confirmation dialog
		promotionControl.deleteADevicePromotionByName(DeviceEdit.PROMOTION_CONTAINER, dataGlobalPromotion1.get("name"));
		// 9. Click �Save� link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The manual deleted promotion is not displayed under Device promotion section
		 */
		Assert.assertFalse(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion1.get("name").replace(" ", " - ")));
		// 10. Click �Edit� link
		appDeviceControl.click(DeviceInfo.EDIT);
		// 11. Turn the Global Promotion feature OFF
		appDeviceControl.click(DeviceEdit.GLOBAL_PROMOTIONS_ON);
		// 12. Click �Save� link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: All global promotions are not displayed under Device Promotion section
		 */
		Assert.assertFalse(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion1.get("name").replace(" ", " - ")));
		Assert.assertFalse(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion2.get("name").replace(" ", " - ")));
		// 13. Click �Edit� link
		appDeviceControl.click(DeviceInfo.EDIT);
		// 14. Turn the Global Promotion feature OFF
		appDeviceControl.click(DeviceEdit.GLOBAL_PROMOTIONS_ON);
		// 15. Click �Save� link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * The manual deleted promotions are not added back to promotion slots
		 */
		Assert.assertFalse(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion1.get("name").replace(" ", " - ")));
		Assert.assertTrue(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion2.get("name").replace(" ", " - ")));
		// Delete device
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		// Delete product
		productControl.deleteAccessorybyName(dataProduct1.get("name"));
		productControl.deleteAccessorybyName(dataProduct2.get("name"));
	}
	
	/*
	 * Verify that the manual deleted promotions are not added back to promotion slots when the new version of app/device is created
	 */
	@Test
	public void TC640DADD_17() {
		appDeviceControl.addLog("ID : TC640DADD_17 : Verify that the manual deleted promotions are not added back to promotion slots when the new version of app/device is created");
		/*
		 	Pre-Condition: There are some promotions already published.
			1. Log into DTS portal as DTS admin
			2. Navigate to "Apps & Devices" page
			3. Click �Add App or Device� link
			4. Fill valid value into all required fields
			5. Click �Save� link with default Global Promotion state
			VP: New app/device is created successfully and the published promotions are added into Device Promotions section
			6. Click �Edit� link
			7. Click a �Delete� link which is next to the promotion slots
			8. Click �OK� on delete confirmation dialog
			9. Click �Save� link
			VP: The manual deleted promotion is not displayed under Device promotion section
			10. Click �Publish� link
			VP: The app/device is published successfully
			11. Click �Update Product Info� link
			12. Click �OK� button on confirmation dialog
			VP: The app/device edit page is displayed
			13. Change some value of app/device
			14. Click �Save� link
		*/
		/*
		  PreCondition: Create new global promotion
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		appDeviceControl.click(PageHome.linkAccessories);
		// Click Add product link
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
		// Create published product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		// Navigate to Promotion page
		appDeviceControl.click(PageHome.linkPromotions);
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create Global promotion
		Hashtable<String,String> dataGlobalPromotion = TestData.promotionGlobal(dataProduct.get("brand") + " " + dataProduct.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataGlobalPromotion);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		/*
		 * *************************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.linkDevice);
		// 3. Click �Add App or Device� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		// 5. Click �Save� link with default Global Promotion state
		Hashtable<String,String> dataDevice = TestData.appDevicePublish();
		dataDevice.remove("global promotion");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice);
		/*
		 * VP: New app/device is created successfully and the published promotions are added into Device Promotions section
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), dataDevice.get("name"));
		Assert.assertTrue(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion.get("name").replace(" ", " - ")));
		// 6. Click �Edit� link
		appDeviceControl.click(DeviceInfo.EDIT);
		// 7. Click a �Delete� link which is next to the promotion slots
		// 8. Click �OK� on delete confirmation dialog
		promotionControl.deleteADevicePromotionByName(DeviceEdit.PROMOTION_CONTAINER, dataGlobalPromotion.get("name"));
		// 9. Click �Save� link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The manual deleted promotion is not displayed under Device promotion section
		 */
		Assert.assertFalse(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion.get("name").replace(" ", " - ")));
		// 10. Click �Publish� link
		appDeviceControl.click(DeviceInfo.PUBLISH);
		/*
		 * VP: The app/device is published successfully
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.PUBLICATION_STATUS), "Published");
		// 11. Click �Update Product Info� link
		appDeviceControl.click(DeviceInfo.CREATE_NEW_VERSION);
		// 12. Click �OK� button on confirmation dialog
		appDeviceControl.selectConfirmationDialogOption("OK");
		/*
		 * VP: The app/device edit page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()), true);
		// 13. Change some value of app/device
		String newName = "NewDevice" + RandomStringUtils.randomNumeric(7);
		appDeviceControl.editData(DeviceEdit.NAME, newName);
		// 14. Click �Save� link
		appDeviceControl.click(DeviceEdit.SAVE);
		// Check if PDPP-1168 found
		if(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion.get("name").replace(" ", " - "))){
			appDeviceControl.addErrorLog("PDPP-1168: 640 Product Detail: The manual deleted promotions in previous app/device version are auto added back to newest version");
			productControl.deleteAccessorybyName(dataProduct.get("name"));
			Assert.assertTrue(false);
		}
	}
	
	/*
	 * Verify that "Multi-Channel Room Models", "Headphone Image Sizes" and "Headphone Brands" Section are displayed as readonly
	 */
	@Test
	public void TC640DADD_20() {
		appDeviceControl.addLog("ID : TC640DADD_20 : Verify that Multi-Channel Room Models, Headphone Image Sizes and Headphone Brands Section are displayed as readonly");
		/*
		 1. Navigate to DTS portal
		 2. Log into DTS portal as a DTS user successfully
		 3. Navigate to "Apps & Devices" page
	 	 4. Select a product name from apps/devices table
		 VP: The 640 Product Detail Page is displayed and The "Multi-Channel Room Models", "Headphone Image Sizes" and "Headphone Brands" Section are displayed as readonly
		/*
		 * *************************************************************
		 */	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.linkDevice);
		// 4. Select a product name from apps/devices table
		appDeviceControl.selectAnAppDevice();
		//VP: The 640 Product Detail Page is displayed and The "Multi-Channel Room Models", "Headphone Image Sizes" and "Headphone Brands" Section are displayed as readonly
		//VP: "Multi-Channel Room Models" is displayed as readonly
		Assert.assertFalse(appDeviceControl.isElementEditable(DeviceInfo.MULTI_CHANNEL));
		Assert.assertFalse(appDeviceControl.isElementEditable(DeviceInfo.MULTI_CHANNEL_FIELD));
		//VP: "Headphone Image Sizes" is displayed as readonly
		Assert.assertFalse(appDeviceControl.isElementEditable(DeviceInfo.HEADPHONE_IMAGE_SIZES));
		Assert.assertFalse(appDeviceControl.isElementEditable(DeviceInfo.HEADPHONE_IMAGE_SIZES_FIELD));
		//VP: "Headphone Brands" is displayed as readonly
		Assert.assertFalse(appDeviceControl.isElementEditable(DeviceInfo.HEADPHONE_BRAND));
		Assert.assertFalse(appDeviceControl.isElementEditable(DeviceInfo.HEADPHONE_BRAND_OPTION));
		}
	
	/*
	 * Verify that DTS user cannot modify any way while the database is refreshing
	 */
	@Test
	public void TC640DADD_21() {
		appDeviceControl.addLog("ID : TC640DADD_21 : Verify that DTS user cannot modify any way while the database is refreshing");
		/*
		1. Navigate to DTS portal
		2. Log into DTS portal as a DTS user successfully
		3. Navigate to "Apps & Devices" page
		4. Select a published app/device from apps/devices table
		VP: The "Refresh offline database" button is displayed
		5. Click "Refresh offline database" button
		VP: The "Refresh database?" dialog is displayed
		6. Choose "Refresh" button in "Refresh Database?" popup.
		VP: The "Refresh offline database" button is disabled and have an animation displays to indeterminate progress indicator, 
		"Edit","Update","Delete" and "Suspend" link are disable
		/*
		 * Pre-condition: Create a new published app/device
		 */
		
		//2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// Click "Add app or device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDevicePublish();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// Publish device 
		appDeviceControl.click(DeviceInfo.PUBLISH);
		appDeviceControl.waitForElementClickable(PageHome.LINK_APP_DEVICES);
		// 3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.linkDevice);
		// 4. Select a product name from apps/devices table
		appDeviceControl.selectADeviceByName(data.get("name"));
		//VP: The "Refresh offline database" button is displayed
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE));
		//5. Click "Refresh offline database" button
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		//VP: The "Refresh database?" dialog is displayed 
		//appDeviceControl.waitForElementClickable(DeviceInfo.REFRESH_BUTTON);	
		//6. Choose "Refresh" button in "Refresh Database?" popup.
		appDeviceControl.click(DeviceInfo.REFRESH_BUTTON);
		//VP: The "Refresh offline database" button is disabled and have an animation displays to indeterminate progress indicator, 
		//"Edit","Update","Delete" and "Suspend" link are disable
        //Assert.assertEquals("visible", ExpectedConditions.elementToBeClickable(By.xpath(DeviceInfo.REFRESH_OFFLINE_DATABASE)));
		appDeviceControl.waitForLoadElement(DeviceInfo.INDICATOR_LOADING);
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.INDICATOR_LOADING));
		
		/*
		 * if (appDeviceControl.waitForElementDisappear(DeviceInfo.REFRESH_OFFLINE_DATABASE)== true)
		{
			appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
			appDeviceControl.click(DeviceInfo.SUSPEND);
			appDeviceControl.click(DeviceInfo.DELETE);
			appDeviceControl.click(DeviceInfo.CREATE_NEW_VERSION);
		}
		 */	
//		Assert.assertEquals("visible", ExpectedConditions.elementToBeClickable(By.xpath(DeviceInfo.SUSPEND)));
//		Assert.assertEquals("visible", ExpectedConditions.elementToBeClickable(By.xpath(DeviceInfo.DELETE)));
//		Assert.assertEquals("visible", ExpectedConditions.elementToBeClickable(By.xpath(DeviceInfo.CREATE_NEW_VERSION)));/		
		}
	/*
	 * Verify that the "Refresh offline database" button is not displayed in Draft App/Divice Detail page
	 */
	@Test
	public void TC640DADD_22() {
		appDeviceControl.addLog("ID : TC640DADD_22 : Verify that the Refresh offline database button is not displayed in Draft App/Divice Detail page");
		/*
		1. Navigate to DTS portal
		2. Log into DTS portal as a DTS user successfully
		3. Navigate to "Apps & Devices" page
		4. Select a "Draft" app/device from apps/devices table
		VP: The "Refresh offline database" button is not displayed
		*/
		/*
		  	Pre-condition: Create a new Draft app/device
		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// Click "Add app or device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDevicePublish();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// 3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.linkDevice);
		// 4. Select a "Draft" app/device from apps/devices table
		appDeviceControl.selectADeviceByName(data.get("name"));
		//VP: The "Refresh offline database" button is not displayed
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE));
		}
	
	/*
	 * Verify that "Refresh offline database" dialog works well 
	 */
	@Test
	public void TC640DADD_23() {
		appDeviceControl.addLog("ID : TC640DADD_23 : Verify that 'Refresh offline database' dialog works well");
		/*
		1. Navigate to DTS portal
		2. Log into DTS portal as a DTS user successfully
		3. Navigate to "Apps & Devices" page
		4. Select a published app/device from apps/devices table
		VP: The "Refresh offline database" button is not displayed
		5. Click "Refresh offline database" buttom
		VP: The "Refresh database?" dialog is displayed
		6. Choose "Cancel" buttom in Refresh Database Confirmation Dialog
		VP: Offline Database is not refresh
		7. Click again "Refresh offline Database" button
		8. Choose "Refresh" buttom in Refresh Database Confirmation Dialog
		VP: Offline database is refreshing
		VP: The ""Refresh offline database"" buttom, ""Edit"",""Update"",""Delete"" and ""Suspend"" link are disable

		/*
		 * Pre-condition: Create a new Draft app/device
		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// Click "Add app or device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDevicePublish();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// Publish device 
		appDeviceControl.click(DeviceInfo.PUBLISH);
		appDeviceControl.waitForElementClickable(PageHome.LINK_APP_DEVICES);
		// 3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.linkDevice);
		// 4. Select a published app/device from apps/devices table
		appDeviceControl.selectADeviceByName(data.get("name"));
		//VP: The "Refresh offline database" button is not displayed
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE));
		// 5. Click "Refresh offline database" buttom
		// Publish app/device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		// VP: The "Refresh database?" dialog is displayed
		//Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.POPUP_REFRESH_CONFIRM));
		// 6. Choose "Cancel" buttom in Refresh Database Confirmation Dialog
		appDeviceControl.click(DeviceInfo.CANCEL_REFRESH_BUTTON);
		// VP: Offline Database is not refresh
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.INDICATOR_LOADING));
		// 7. Click again "Refresh offline Database" button
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		// 8. Choose "Refresh" buttom in Refresh Database Confirmation Dialog
		appDeviceControl.click(DeviceInfo.REFRESH_BUTTON);
		// VP: Offline database is refreshing
		/*
		 *Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.INDICATOR_LOADING));
		// VP: The ""Refresh offline database"" buttom, ""Edit"",""Update"",""Delete"" and ""Suspend"" link are disable
		Assert.assertTrue(appDeviceControl.isElementEditable(DeviceInfo.REFRESH_OFFLINE_DATABASE));
		Assert.assertTrue(appDeviceControl.isElementEditable(DeviceInfo.EDIT));
		Assert.assertTrue(appDeviceControl.isElementEditable(DeviceInfo.CREATE_NEW_VERSION));
		Assert.assertTrue(appDeviceControl.isElementEditable(DeviceInfo.DELETE));
		Assert.assertTrue(appDeviceControl.isElementEditable(DeviceInfo.SUSPEND));
		 
		 */
		}
	
	/*
	 * Verify that DTS user can download offline database 
	 */
	@Test
	public void TC640DADD_24() {
		appDeviceControl.addLog("ID : TC640DADD_24 : Verify that DTS user can download offline database");
		/*
		1. Navigate to DTS portal
		2. Log into DTS portal as a DTS user successfully
		3. Navigate to "Apps & Devices" page
		4. Select a published app/device from apps/devices table
		5. Click "Refresh offline database" button
		6. Click "Download offline database" button
		VP: Offline Database is downloaded successfully
		/*
		 * Pre-condition: Create a new Draft app/device
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// Click "Add app or device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDevicePublish();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		appDeviceControl.click(DeviceInfo.PUBLISH);
		appDeviceControl.waitForElementClickable(PageHome.LINK_APP_DEVICES);
		// 3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.linkDevice);
		// 4. Select a published app/device from apps/devices table
		appDeviceControl.selectADeviceByName(data.get("name"));
		//VP: The "Refresh offline database" button is not displayed
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE));
		// 5. Click "Refresh offline database" button
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.waitForElementClickable(DeviceInfo.REFRESH_BUTTON);
		// Click "Refresh" button
		appDeviceControl.click(DeviceInfo.REFRESH_BUTTON);
		// 6. Click "Download offline database" button
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		// VP: Offline Database is downloaded successfully
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWLOAD_DATABASE));
	}
	
	/*
	 * Verify that "Custom Audio Route" file is displayed in alphabetical order.
	 */
	@Test
	public void TC640DADD_25() {
		appDeviceControl.addLog("ID : TC640DADD_25 : Verify that 'Custom Audio Route' file is displayed in alphabetical order.");
		
		/*
		 * 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully.
			3. Navigate to "Apps & Devices" page
			4. Click "Add App or Device" link
			5. Fill all valid values into required fields
			6. Upload the first custom tuning file successfully
			VP: The tuning file is uploaded successfully.
			7. Upload the second custom tuning file successfully
			VP: The tuning file is uploaded successfully.
			8. Click "Save" link
			VP: The 640D Device Detail page is displayed and custom audio route files is displayed in alphabetical order.

		 */
		// 2. Log into DTS portal as a DTS user successfully.
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 4. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 5. Fill all valid values into required fields
		Hashtable<String,String> data = TestData.appDeviceDraft();
		data.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// 6. Upload the first custom tuning file successfully
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes[3]);
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		/*
		 * VP: The tuning file is uploaded successfully.
		 */
		Assert.assertTrue(driver.findElement(By.partialLinkText(DeviceEdit.routes[3])).isDisplayed());
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes[4]);
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		/*
		 * VP: The tuning file is uploaded successfully.
		 */
		Assert.assertTrue(driver.findElement(By.partialLinkText(DeviceEdit.routes[4])).isDisplayed());
		// 8. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The 640D Device Detail page is displayed and custom audio route files is displayed in alphabetical order.
		 */
		Assert.assertTrue(appDeviceControl.checkSortByAlphabet(DeviceInfo.CUSTOM_TUNING_CONTAINER));
	}
	
	/*
	 * Verify that App and Device Promotion ID are ordered correctly by DTS user in  640D Device Detail
	 */
	@Test
	public void TC640DADD_27(){
		Reporter.log("Verify that App and Device Promotion ID are ordered correctly by DTS user in  640D Device Detail");
		
		/*1. Navigate to DTS portal
		2. Log into DTS portal as a DTS user successfully
		3. Navigate to "Apps & Devices" page
		4. Click "Add App or Device" link
		5. Fill all valid values into required fields (include uploading valid Default Audio Route  )
		6. Input "25" in Promotion Slots Available
		7. Input some valid  Apps and Devices Promotions ID which are published
		8. Drag and Drop Promotion ID by list order
		VP: user can drag and drop promotion slots 
		9. Click "Save" link
		VP: Apps & Devices is added successful , 640D Device Detail page appears, System correctly displays the 
		list order of Promotions ID which were dragged and dropped by user before (step 8)
		10. Click "Publish" link
		VP: This apps & device is published successfully. And system correctly displays the 
		list order of Promotions ID which were dragged and dropped by user before (step 8)
		11. Click  "Update Product Info" link
		12. Click "Cancel" Button
		VP: System correctly displays the list order of Promotions ID which 
		were dragged and dropped by user before (step 8)
		13. Click  "Update Product Info" link
		14. Click "OK" Button
		15. Click "Save" link
		VP: this app & device is saved successful and is changed to draff status.
		System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 8)
		16. Click "View Published Version" link
		VP: In Device Promotions , System correctly displays the 
		list order of Promotions ID which were dragged and dropped by user before (step 8)
		17. Click "View Current Version" link
		VP: In Device Promotions section, System correctly displays the 
		list order of Promotions ID which were dragged and dropped by user before (step 8)
		*/
		//2. Log into DTS portal as a DTS user successfully	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 4. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		//5. Fill all valid values into required fields (include uploading valid Default Audio Route )
		Hashtable<String,String> data = TestData.appDevicePublish();
		data.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),data);
		//6. Input "25" in Promotion Slots Available
		appDeviceControl.editData(DeviceEdit.PROMO_SLOT,"25");
		appDeviceControl.click(DeviceEdit.SAVE);
		String device_name = appDeviceControl.getTextByXpath(DeviceInfo.NAME);
		//7. Input some valid  Apps and Devices Promotions ID which are published
		//*** Create new device promotions
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
		Hashtable<String,String> dataDevicePromotion1 = TestData.promotionAppDevice(dataProduct1.get("brand") + " " + dataProduct1.get("name"),PARTNER_BRAND_NAME_1,device_name);
		promotionControl.addPromotion(AddPromotion.getHash(), dataDevicePromotion1);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		// Get promotion ID
		//String promotionID1 = appDeviceControl.getTextByXpath(PromotionInfo.DTS_ID);
		// Navigate to Promotion page
		appDeviceControl.click(PageHome.linkPromotions);
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create 2nd Device promotion
		Hashtable<String,String> dataDevicePromotion2 = TestData.promotionAppDevice(dataProduct2.get("brand") + " " + dataProduct2.get("name"),PARTNER_BRAND_NAME_1,device_name);
		promotionControl.addPromotion(AddPromotion.getHash(), dataDevicePromotion2);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		// Get promotion ID
		//String promotionID2 = appDeviceControl.getTextByXpath(PromotionInfo.DTS_ID);
		//*******************
		// Input some valid  Apps and Devices Promotions ID which are published
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.selectADeviceByName(device_name);
		appDeviceControl.click(DeviceInfo.EDIT);
		//appDeviceControl.editData(DeviceEdit.PROMOTION_1,promotionID1);
		//appDeviceControl.editData(DeviceEdit.PROMOTION_1,promotionID2);
		//8. Drag and Drop Promotion ID by list order
		ArrayList<String> before_list1= appDeviceControl.getListOrder();
		appDeviceControl.dragAndDrop(DeviceEdit.promotionslotlabel(1),DeviceEdit.promotionslotlabel(2));
		ArrayList<String> after_list1= appDeviceControl.getListOrder();
		/*
		 * VP: user can drag and drop promotion slots 
		 */
		appDeviceControl.isPromotionOrder(1,2,before_list1,after_list1);
		//9. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: Apps & Devices is added successful , 640D Device Detail page appears, System correctly displays the 
		   list order of Promotions ID which were dragged and dropped by user before (step 8)
		*/
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Draft"),after_list1));
		//10. Click "Publish" link
		appDeviceControl.click(DeviceInfo.PUBLISH);
		/*
		 * VP: This apps & device is published successfully. And system correctly displays the 
		 * list order of Promotions ID which were dragged and dropped by user before (step 8)
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.PUBLICATION_STATUS),"Published");
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Published"),after_list1));
		//11. Click  "Update Product Info" link
		appDeviceControl.click(DeviceInfo.CREATE_NEW_VERSION);;
		//12. Click "Cancel" Button
		appDeviceControl.selectConfirmationDialogOption("Cancel");
		/*VP: System correctly displays the list order of Promotions ID which 
		were dragged and dropped by user before (step 8)
		*/
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Published"),after_list1));
		//13. Click  "Update Product Info" link
		//14. Click "OK" Button
		appDeviceControl.editVersion();
		//15. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*VP: This app & device is saved successful and is changed to draff status.
		System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 8)
		*/
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.PUBLICATION_STATUS),"Draft");
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Draft"),after_list1));
		//16. Click "View Published Version" link
		appDeviceControl.click(DeviceInfo.VIEW_PUBLISHED_VERSION);
		/*VP: In Device Promotions , System correctly displays the 
		list order of Promotions ID which were dragged and dropped by user before (step 8)
		*/
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Published"),after_list1));
		//17. Click "View Current Version" link
		appDeviceControl.click(DeviceInfo.VIEW_CURRENT_VERSION);
		/*VP: In Device Promotions section, System correctly displays the 
		list order of Promotions ID which were dragged and dropped by user before (step 8)
		*/
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Draft"),after_list1));
	}
	
	/*
	 * Verify that Global Promotion ID are ordered correctly by DTS user in  640D Device Detail
	 */
	@Test
	public void TC640DADD_28(){
		Reporter.log("Verify that Global Promotion ID are ordered correctly by DTS user in  640D Device Detail");
		
		/*1. Navigate to DTS portal
		2. Log into DTS portal as a DTS user successfully
		3. Navigate to "Apps & Devices" page
		4. Click "Add App or Device" link
		5. Fill all valid values into required fields (include uploading valid Default Audio Route  )
		6. Input "25" in Promotion Slots Available
		7. Input some valid Global Promotions ID which are published
		8. Drag and Drop Promotion ID by list order
		VP: user can drag and drop promotion slots 
		9. Click "Save" link
		VP: Apps & Devices is added successful , 640D Device Detail page appears, System correctly displays the 
		list order of Promotions ID which were dragged and dropped by user before (step 8)
		10. Click "Publish" link
		VP: This apps & device is published successfully. And system correctly displays the 
		list order of Promotions ID which were dragged and dropped by user before (step 8)
		11. Click  "Update Product Info" link
		12. Click "Cancel" Button
		VP: System correctly displays the list order of Promotions ID which 
		were dragged and dropped by user before (step 8)
		13. Click  "Update Product Info" link
		14. Click "OK" Button
		15. Click "Save" link
		VP: this app & device is saved successful and is changed to draff status.
		System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 8)
		16. Click "View Published Version" link
		VP: In Device Promotions , System correctly displays the 
		list order of Promotions ID which were dragged and dropped by user before (step 8)
		17. Click "View Current Version" link
		VP: In Device Promotions section, System correctly displays the 
		list order of Promotions ID which were dragged and dropped by user before (step 8)
		*/
		
		//2. Log into DTS portal as a DTS user successfully	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 4. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		//5. Fill all valid values into required fields (include uploading valid Default Audio Route )
		//6. Input "25" in Promotion Slots Available
		Hashtable<String,String> data = TestData.appDevicePublish();
		data.remove("global promotion");
		data.put("global promotion","ON");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),data);
		//7. Input some valid Global Promotions ID which are published
		appDeviceControl.click(DeviceInfo.EDIT);
		//8. Drag and Drop Promotion ID by list order
		ArrayList<String> before_list1= appDeviceControl.getListOrder();
		appDeviceControl.dragAndDrop(DeviceEdit.promotionslotlabel(1),DeviceEdit.promotionslotlabel(2));
		ArrayList<String> after_list1= appDeviceControl.getListOrder();
		/*
		 * VP: user can drag and drop promotion slots 
		 */
		appDeviceControl.isPromotionOrder(1,2,before_list1,after_list1);
		//9. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: Apps & Devices is added successful , 640D Device Detail page appears, System correctly displays the 
		   list order of Promotions ID which were dragged and dropped by user before (step 8)
		*/
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Draft"),after_list1));
		//10. Click "Publish" link
		appDeviceControl.click(DeviceInfo.PUBLISH);
		/*
		 * VP: This apps & device is published successfully. And system correctly displays the 
		 * list order of Promotions ID which were dragged and dropped by user before (step 8)
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.PUBLICATION_STATUS),"Published");
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Published"),after_list1));
		//11. Click  "Update Product Info" link
		appDeviceControl.click(DeviceInfo.CREATE_NEW_VERSION);;
		//12. Click "Cancel" Button
		appDeviceControl.selectConfirmationDialogOption("Cancel");
		/*VP: System correctly displays the list order of Promotions ID which 
		were dragged and dropped by user before (step 8)
		*/
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Published"),after_list1));
		//13. Click  "Update Product Info" link
		//14. Click "OK" Button
		appDeviceControl.editVersion();
		//15. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*VP: This app & device is saved successful and is changed to draff status.
		System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 8)
		*/
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.PUBLICATION_STATUS),"Draft");
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Draft"),after_list1));
		//16. Click "View Published Version" link
		appDeviceControl.click(DeviceInfo.VIEW_PUBLISHED_VERSION);
		/*VP: In Device Promotions , System correctly displays the 
		list order of Promotions ID which were dragged and dropped by user before (step 8)
		*/
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Published"),after_list1));
		//17. Click "View Current Version" link
		appDeviceControl.click(DeviceInfo.VIEW_CURRENT_VERSION);
		/*VP: In Device Promotions section, System correctly displays the 
		list order of Promotions ID which were dragged and dropped by user before (step 8)
		*/
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Draft"),after_list1));

	}
	
	/*
	 * Verify that All Global, App, Device Promotion ID are ordered correctly by DTS user in  640D Device Detail
	 */
	@Test
	public void TC640DADD_29(){
		Reporter.log("Verify that All Global, App, Device Promotion ID are ordered correctly by DTS user in  640D Device Detail");
		
		/*1. Navigate to DTS portal
		2. Log into DTS portal as a DTS user successfully
		3. Navigate to "Apps & Devices" page
		4. Click "Add App or Device" link
		5. Fill all valid values into required fields (include uploading valid Default Audio Route  )
		6. Input "25" in Promotion Slots Available
		7. Input some valid Global , Apps , Devices Promotions ID which are published
		8. Drag and Drop Promotion ID by list order
		VP: user can drag and drop promotion slots 
		9. Click "Save" link
		VP: Apps & Devices is added successful , 640D Device Detail page appears, System correctly displays the 
		list order of Promotions ID which were dragged and dropped by user before (step 8)
		10. Click "Publish" link
		VP: This apps & device is published successfully. And system correctly displays the 
		list order of Promotions ID which were dragged and dropped by user before (step 8)
		11. Click  "Update Product Info" link
		12. Click "Cancel" Button
		VP: System correctly displays the list order of Promotions ID which 
		were dragged and dropped by user before (step 8)
		13. Click  "Update Product Info" link
		14. Click "OK" Button
		15. Click "Save" link
		VP: this app & device is saved successful and is changed to draff status.
		System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 8)
		16. Click "View Published Version" link
		VP: In Device Promotions , System correctly displays the 
		list order of Promotions ID which were dragged and dropped by user before (step 8)
		17. Click "View Current Version" link
		VP: In Device Promotions section, System correctly displays the 
		list order of Promotions ID which were dragged and dropped by user before (step 8)
		*/
		
		//2. Log into DTS portal as a DTS user successfully	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 4. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		//5. Fill all valid values into required fields (include uploading valid Default Audio Route )
		Hashtable<String,String> data = TestData.appDevicePublish();
		data.remove("save");
		data.remove("global promotion");
		data.put("global promotion","ON");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),data);
		//6. Input "25" in Promotion Slots Available
		appDeviceControl.editData(DeviceEdit.PROMO_SLOT,"25");
		appDeviceControl.click(DeviceEdit.SAVE);
		String device_name = appDeviceControl.getTextByXpath(DeviceInfo.NAME);
		//7. Input some valid Global , Apps , Devices Promotions ID which are published
		//*** Create new device promotions
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
		Hashtable<String,String> dataDevicePromotion1 = TestData.promotionAppDevice(dataProduct1.get("brand") + " " + dataProduct1.get("name"),PARTNER_BRAND_NAME_1,device_name);
		promotionControl.addPromotion(AddPromotion.getHash(), dataDevicePromotion1);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		// Get promotion ID
		//String promotionID1 = appDeviceControl.getTextByXpath(PromotionInfo.DTS_ID);
		// Navigate to Promotion page
		appDeviceControl.click(PageHome.linkPromotions);
		Assert.assertTrue(promotionControl.checkPromotionExistByName(PromotionList.PROMOTION_TABLE,dataDevicePromotion1.get("name")));
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create 2nd Device promotion
		Hashtable<String,String> dataDevicePromotion2 = TestData.promotionAppDevice(dataProduct2.get("brand") + " " + dataProduct2.get("name"),PARTNER_BRAND_NAME_1,device_name);
		promotionControl.addPromotion(AddPromotion.getHash(), dataDevicePromotion2);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		// Get promotion ID
		//String promotionID2 = appDeviceControl.getTextByXpath(PromotionInfo.DTS_ID);
		//*******************
		// Input some valid  Apps and Devices Promotions ID which are published
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.selectADeviceByName(device_name);
		appDeviceControl.click(DeviceInfo.EDIT);
		//appDeviceControl.editData(DeviceEdit.PROMOTION_1,promotionID1);
		//appDeviceControl.editData(DeviceEdit.PROMOTION_1,promotionID2);
		//8. Drag and Drop Promotion ID by list order
		ArrayList<String> before_list1= appDeviceControl.getListOrder();
		appDeviceControl.dragAndDrop(DeviceEdit.promotionslotlabel(1),DeviceEdit.promotionslotlabel(2));
		appDeviceControl.dragAndDrop(DeviceEdit.promotionslotlabel(4),DeviceEdit.promotionslotlabel(3));
		ArrayList<String> after_list1= appDeviceControl.getListOrder();
		/*
		 * VP: user can drag and drop promotion slots 
		 */
		appDeviceControl.isPromotionOrder(1,2,before_list1,after_list1);
		appDeviceControl.isPromotionOrder(4,3,before_list1,after_list1);
		//9. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: Apps & Devices is added successful , 640D Device Detail page appears, System correctly displays the 
		   list order of Promotions ID which were dragged and dropped by user before (step 8)
		*/
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Draft"),after_list1));
		//10. Click "Publish" link
		appDeviceControl.click(DeviceInfo.PUBLISH);
		/*
		 * VP: This apps & device is published successfully. And system correctly displays the 
		 * list order of Promotions ID which were dragged and dropped by user before (step 8)
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.PUBLICATION_STATUS),"Published");
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Published"),after_list1));
		//11. Click  "Update Product Info" link
		appDeviceControl.click(DeviceInfo.CREATE_NEW_VERSION);;
		//12. Click "Cancel" Button
		appDeviceControl.selectConfirmationDialogOption("Cancel");
		/*VP: System correctly displays the list order of Promotions ID which 
		were dragged and dropped by user before (step 8)
		*/
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Published"),after_list1));
		//13. Click  "Update Product Info" link
		//14. Click "OK" Button
		appDeviceControl.editVersion();
		//15. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*VP: This app & device is saved successful and is changed to draff status.
		System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 8)
		*/
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.PUBLICATION_STATUS),"Draft");
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Draft"),after_list1));
		//16. Click "View Published Version" link
		appDeviceControl.click(DeviceInfo.VIEW_PUBLISHED_VERSION);
		/*VP: In Device Promotions , System correctly displays the 
		list order of Promotions ID which were dragged and dropped by user before (step 8)
		*/
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Published"),after_list1));
		//17. Click "View Current Version" link
		appDeviceControl.click(DeviceInfo.VIEW_CURRENT_VERSION);
		/*VP: In Device Promotions section, System correctly displays the 
		list order of Promotions ID which were dragged and dropped by user before (step 8)
		*/
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Draft"),after_list1));
	}
	
	/*
	 * Verify that speaker modes are displayed correctly in device detail page
	 */
	@Test
	public void TC640DADD_30(){
		Reporter.log("Verify that speaker modes are displayed correctly in device detail page");
		
		/*
		1. Log into DTS portal as DTS admin
		2. Navigate to "Apps & Devices" page
		3. Click "Add App or Device" link
		4. Fill all valid values into required fields
		5. Click "Select file" to upload Default Audio Route tuning file
		VP: The tuning file is uploaded successfully.
		6. Select "Attached2 - Internal Speakers (mode 1)" in Audio Route
		7. Click "Choose fille" to upload custom tuning file
		VP: The custom tuning file is uploaded successfully.
		8. Select "Attached4 - Internal Speakers (mode 3)" in Audio Route
		9. Click "Choose fille" to upload custom tuning file 
		VP: The custom tuning file is uploaded successfully.
		10. Click "Save" link
		VP: The 640D Device Detail page is displayed.
		VP: "Attached2 - Internal Speakers (mode 1)"" and  ""Attached4 - Internal Speakers (mode 3)"" tuning links 
		are displayed under Default Audio Route tuning file.
		*/
		
		//1. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//3. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		//4. Fill all valid values into required fields
		//5. Click "Select file" to upload Default Audio Route tuning file
		Hashtable<String,String> dataDevice = TestData.appDevicePublish();
		dataDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),dataDevice);
		/*
		 * VP: The tuning file is uploaded successfully.
		 */
		Assert.assertTrue(driver.findElement(By.partialLinkText("Default Audio")).isDisplayed());
		//6. Select "Attached2 - Internal Speakers (mode 1)" in Audio Route
		appDeviceControl.selectCheckboxInDropdown(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes[5]);
		//7. Click "Choose fille" to upload custom tuning file
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		/*
		 * VP: The custom tuning file is uploaded successfully.
		 */
		Assert.assertTrue(driver.findElement(By.partialLinkText(DeviceEdit.routes[5])).isDisplayed());
		//8. Select "Attached4 - Internal Speakers (mode 3)" in Audio Route
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes[7]);
		//9. Click "Choose fille" to upload custom tuning file 
		appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		/*
		 * VP: The custom tuning file is uploaded successfully.
		 */
		Assert.assertTrue(driver.findElement(By.partialLinkText(DeviceEdit.routes[7])).isDisplayed());
		//10. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The 640D Device Detail page is displayed.
		 */
		Assert.assertTrue(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")));
		/*
		 * VP: "Attached2 - Internal Speakers (mode 1)"" and  ""Attached4 - Internal Speakers (mode 3)"" tuning links 
		 * are displayed under Default Audio Route tuning file.
		 */
		Assert.assertTrue(driver.findElement(By.partialLinkText(DeviceEdit.routes[5])).isDisplayed());
		Assert.assertTrue(driver.findElement(By.partialLinkText(DeviceEdit.routes[7])).isDisplayed());
		
		
	}
	
	/*
	 * Verify that The "Headphone Image Sizes" displays only selected image sizes by the user in 640D Device Detail Page
	 */
	@Test
	public void TC640DADD_31(){
		Reporter.log("Verify that The 'Headphone Image Sizes' displays only selected image sizes by the user in 640D Device Detail Page");
	
		/*1. Navigate to DTS portal
		2. Log into DTS portal as a DTS user successfully
		3. Navigate to "Apps & Devices" page
		4. Click "Add App or Device" link
		5. Fill all valid values into required fields
		6. Unselect "Medium (500x500px)" option in "Headphone Image Sizes"
		7. Click "Save" link
		VP: "Medium (500x500px)" option is not displayed in "Headphone Image Sizes" section
		8. Click "publish" link
		VP: "Medium (500x500px)" option is not displayed in "Headphone Image Sizes" section
		9. Click "Edit Version" link
		10. Reselect ""Medium (500x500px)"" option in ""Headphone Image Sizes"
		VP: "Medium (500x500px)" option is displayed in "Headphone Image Sizes" section
		11. Click "Save" link
		12. Click "Publish" link
		VP: "Medium (500x500px)" option is displayed in "Headphone Image Sizes" section
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
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),dataDevice);
		//6. Unselect "Medium (500x500px)" option in "Headphone Image Sizes"
		appDeviceControl.uncheckACheckbox(DeviceEdit.MEDIUM_IMAGE);
		//7. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: "Medium (500x500px)" option is not displayed in "Headphone Image Sizes" section
		 */
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.MEDIUM_IMAGE));
		//8. Click "publish" link
		appDeviceControl.click(DeviceInfo.PUBLISH);
		/*
		 * VP: "Medium (500x500px)" option is not displayed in "Headphone Image Sizes" section
		 */
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.MEDIUM_IMAGE));
		//9. Click "Edit Version" link
		appDeviceControl.editVersion();
		//10. Reselect ""Medium (500x500px)"" option in ""Headphone Image Sizes"
		appDeviceControl.selectACheckbox(DeviceEdit.MEDIUM_IMAGE);
		/*
		 * VP: "Medium (500x500px)" option is displayed in "Headphone Image Sizes" section
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.MEDIUM_IMAGE));
		//11. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		//12. Click "Publish" link
		appDeviceControl.click(DeviceEdit.CANCEL);
		/*
		 * VP: "Medium (500x500px)" option is displayed in "Headphone Image Sizes" section
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.MEDIUM_IMAGE));

	}

}
