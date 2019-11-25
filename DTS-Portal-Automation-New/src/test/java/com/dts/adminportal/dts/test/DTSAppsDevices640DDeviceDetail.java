package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AudioRoutesEdit;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.AddBrand;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AddPromotion;
import com.dts.adminportal.model.BrandInfo;
import com.dts.adminportal.model.CompanyInfo;
import com.dts.adminportal.model.DeviceEdit;
import com.dts.adminportal.model.DeviceInfo;
import com.dts.adminportal.model.DeviceList;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PromotionInfo;
import com.dts.adminportal.model.PromotionList;
import com.dts.adminportal.util.DbUtil;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.SQLiteDbUtil;
import com.dts.adminportal.util.TestData;
import com.ziclix.python.sql.DataHandlerTest;

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
			6. Download the complete profile	
			VP: The complete profile size is less than 1MB
			7. Click on Create New Version link
			8. Click Save button
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
		/*
		 * The "Suspend" link is displayed in "Actions" module after publishing
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.SUSPEND));
		//VP: The ""Create New Version"" link is displayed.
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.CREATE_NEW_VERSION));
		//VP: The ""Suspend"" instead of ""Publish"" link is displayed.
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.PUBLISH));
		//VP: The  ""Complete Profile"" and ""Init Database"" links are enabled
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.COMPLETE_PROFILE_LINK));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.INIT_DATABASE_LINK));
		//6. Download the complete profile	
		String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		boolean isDownloaded = appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK);
		Assert.assertTrue(isDownloaded);
		String profileName = "profile_"+dts_id+".dtscs";
		/*
		 * VP: The complete profile size is less than 1MB
		 */
		Assert.assertTrue(appDeviceControl.isNotExceed1MB(profileName));
		//7. Click on Create New Version link
		appDeviceControl.click(DeviceInfo.CREATE_NEW_VERSION);
		appDeviceControl.selectConfirmationDialogOption("OK");
		//8. Click Save button
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
			3. Click Ã¯Â¿Â½Add App or DeviceÃ¯Â¿Â½ link
			4. Fill valid value into all required fields
			5. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link with default Global Promotion state is  ON
			VP: New app/device is created successfully
			6. Navigate to Ã¯Â¿Â½PromotionsÃ¯Â¿Â½ page
			7. Click Ã¯Â¿Â½Add PromoÃ¯Â¿Â½ link
			8. Select a product from products dropdown
			9. Set the promotion type to Ã¯Â¿Â½DeviceÃ¯Â¿Â½
			10. Set the target to above app/device
			11. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link
			VP: New device promotion is created successfully
			12. Click Ã¯Â¿Â½PublishÃ¯Â¿Â½ link
			VP: The device promotion is published successfully
			13. Navigate to Ã¯Â¿Â½Apps & DevicesÃ¯Â¿Â½ page
			14. Select above app/device from apps/devices table
			VP: The device promotion is displayed under Device Promotion section
			15. Click on Ã¯Â¿Â½EditÃ¯Â¿Â½ link
			16. Change the Global Promotion state to OFF
			17. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link
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
		String DTS_ID = appDeviceControl.getTextByXpath(PromotionInfo.DTS_ID);
		System.out.println("DTS_ID: " + DTS_ID);
		/*
		 * ********************************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.linkDevice);
		// 3. Click Ã¯Â¿Â½Add App or DeviceÃ¯Â¿Â½ link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		// 5. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link with default Global Promotion state is ON
		Hashtable<String,String> dataDevice = TestData.appDeviceDraft();
		dataDevice.remove("save");
		ArrayList<String> dataActual = appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice);
		//appDeviceControl.editData(DeviceEdit.PROMO_SLOT,"25");
		appDeviceControl.editData(DeviceEdit.PROMOTION_SLOT_1, DTS_ID);
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: New app/device is created successfully
		 */
		Assert.assertTrue(ListUtil.containsAll(dataDevice, dataActual));
		// 6. Navigate to Ã¯Â¿Â½PromotionsÃ¯Â¿Â½ page
		appDeviceControl.click(PageHome.linkPromotions);
		// 7. Click Ã¯Â¿Â½Add PromoÃ¯Â¿Â½ link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// 8. Select a product from products dropdown
		// 9. Set the promotion type to Ã¯Â¿Â½DeviceÃ¯Â¿Â½
		// 10. Set the target to above app/device
		// 11. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link
		Hashtable<String,String> dataDevicePromotion = TestData.promotionAppDevice(dataProduct2.get("brand") + " " + dataProduct2.get("name"), dataDevice.get("brand"), dataDevice.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataDevicePromotion);
		/*
		 * VP: New device promotion is created successfully
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(PromotionInfo.PROMOTION_NAME), dataDevicePromotion.get("name"));
		// 12. Click Ã¯Â¿Â½PublishÃ¯Â¿Â½ link
		appDeviceControl.click(PromotionInfo.PUBLISH);
		/*
		 *  VP: The device promotion is published successfully
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(PromotionInfo.PUBLISH_STATUS), "PUBLISHED");
		// 13. Navigate to Ã¯Â¿Â½Apps & DevicesÃ¯Â¿Â½ page
		appDeviceControl.click(PageHome.linkDevice);
		// 14. Select above app/device from apps/devices table
		appDeviceControl.selectADeviceByName(dataDevice.get("name"));
		/*
		 * VP: The device promotion is displayed under Device Promotion section
		 */
		Assert.assertTrue(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion.get("name").replace(" ", " - ")));
//		appDeviceControl.click(DeviceInfo.EDIT);
//		appDeviceControl.click(DeviceEdit.SWITCH_PROMO);
//		appDeviceControl.click(DeviceEdit.SAVE);
//		Assert.assertTrue(appDeviceControl.checkADevicePromotionExistByName(dataDevicePromotion.get("name").replace(" ", " - ")));
		// 15. Click on Ã¯Â¿Â½EditÃ¯Â¿Â½ link
		appDeviceControl.click(DeviceInfo.EDIT);
		// 16. Change the Global Promotion state to OFF
		appDeviceControl.click(DeviceEdit.GLOBAL_PROMOTIONS_ON);
		// 17. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link
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
			3. Click Ã¯Â¿Â½Add App or DeviceÃ¯Â¿Â½ link
			4. Fill valid value into all required fields
			5. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link
			VP: New app/device is created successfully
			6. Navigate to Ã¯Â¿Â½PromotionsÃ¯Â¿Â½ page
			7. Click Ã¯Â¿Â½Add PromoÃ¯Â¿Â½ link
			8. Select a product from products dropdown
			9. Set the promotion type to Ã¯Â¿Â½DeviceÃ¯Â¿Â½
			10. Set the target to above app/device
			11. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link
			VP: New device promotion is created successfully
			12. Click Ã¯Â¿Â½PublishÃ¯Â¿Â½ link
			VP: The device promotion is published successfully
			13. Navigate to Ã¯Â¿Â½Apps & DevicesÃ¯Â¿Â½ page
			14. Select above app/device from apps/devices table
			VP: The device promotion is displayed under Device Promotion section
			15. Click on Ã¯Â¿Â½Apps & DevicesÃ¯Â¿Â½ tab
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
		// 3. Click Ã¯Â¿Â½Add App or DeviceÃ¯Â¿Â½ link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		// 5. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link
		Hashtable<String,String> dataDevice2 = TestData.appDeviceDraft();
		dataDevice2.put("global promotion", "OFF");
		ArrayList<String> dataActual = appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice2);
		/*
		 * VP: New app/device is created successfully
		 */
		Assert.assertTrue(ListUtil.containsAll(dataDevice2, dataActual));
		// 6. Navigate to Ã¯Â¿Â½PromotionsÃ¯Â¿Â½ page
		appDeviceControl.click(PageHome.linkPromotions);
		// 7. Click Ã¯Â¿Â½Add PromoÃ¯Â¿Â½ link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// 8. Select a product from products dropdown
		// 9. Set the promotion type to Ã¯Â¿Â½DeviceÃ¯Â¿Â½
		// 10. Set the target to above app/device
		// 11. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link
		Hashtable<String,String> dataDevicePromotion = TestData.promotionAppDevice(dataProduct.get("brand") + " " + dataProduct.get("name"), dataDevice2.get("brand"), dataDevice2.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataDevicePromotion);
		/*
		 * VP: New device promotion is created successfully
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(PromotionInfo.PROMOTION_NAME).replaceAll("\\s", " "),dataDevicePromotion.get("name").replaceAll("\\s", " "));
		// 12. Click Ã¯Â¿Â½PublishÃ¯Â¿Â½ link
		appDeviceControl.click(PromotionInfo.PUBLISH);
		/*
		 *  VP: The device promotion is published successfully
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(PromotionInfo.PUBLISH_STATUS), "PUBLISHED");
		// 13. Navigate to Ã¯Â¿Â½Apps & DevicesÃ¯Â¿Â½ page
		appDeviceControl.click(PageHome.linkDevice);
		// 14. Select above app/device from apps/devices table
		appDeviceControl.selectADeviceByName(dataDevice2.get("name"));
		/*
		 * VP: The device promotion is displayed under Device Promotion section
		 */
		System.out.println("name: " + dataDevicePromotion.get("name"));
		Assert.assertTrue(appDeviceControl.checkADevicePromotionExistByName(dataDevicePromotion.get("name").replace(" ", " - ")));
//		Assert.assertTrue(appDeviceControl.checkADevicePromotionExistByName(dataDevicePromotion.get("name")));
		// 15. Click on Ã¯Â¿Â½Apps & DevicesÃ¯Â¿Â½ tab				
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
			3. Click Ã¯Â¿Â½Add App or DeviceÃ¯Â¿Â½ link
			4. Fill valid value into all required fields
			5. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link with default Global Promotion state
			VP: New app/device is created successfully and the published promotions are added into Device Promotions section
			6. Click Ã¯Â¿Â½EditÃ¯Â¿Â½ link
			7. Click a Ã¯Â¿Â½DeleteÃ¯Â¿Â½ link which is next to the promotion slots
			8. Click Ã¯Â¿Â½OKÃ¯Â¿Â½ on delete confirmation dialog
			9. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link
			VP: The manual deleted promotion is not displayed under Device promotion section
			10. Click Ã¯Â¿Â½EditÃ¯Â¿Â½ link
			11. Turn the Global Promotion feature OFF
			12. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link
			VP: All global promotions are not displayed under Device Promotion section
			13. Click Ã¯Â¿Â½EditÃ¯Â¿Â½ link
			14. Turn the global promotion feature ON
			15. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link
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
		String DTS_ID1 = appDeviceControl.getTextByXpath(PromotionInfo.DTS_ID);
		// Navigate to Promotion page
		appDeviceControl.click(PageHome.linkPromotions);
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create 2nd Global promotion
		Hashtable<String,String> dataGlobalPromotion2 = TestData.promotionGlobal(dataProduct2.get("brand") + " " + dataProduct2.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataGlobalPromotion2);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		String DTS_ID2 = appDeviceControl.getTextByXpath(PromotionInfo.DTS_ID);
		/*
		 * *******************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.linkDevice);
		// 3. Click Ã¯Â¿Â½Add App or DeviceÃ¯Â¿Â½ link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		// 5. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link with default Global Promotion state
		Hashtable<String,String> dataDevice = TestData.appDeviceDraft();
		dataDevice.remove("save");
		ArrayList<String> dataActual = appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice);
		appDeviceControl.editData(DeviceEdit.PROMOTION_SLOT_1, DTS_ID1);
		appDeviceControl.editData(DeviceEdit.PROMOTION_SLOT_2, DTS_ID2);
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: New app/device is created successfully and the published promotions are added into Device Promotions section
		 */
		Assert.assertTrue(ListUtil.containsAll(dataDevice, dataActual));
		Assert.assertTrue(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion1.get("name").replace(" ", " - ")));
		Assert.assertTrue(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion2.get("name").replace(" ", " - ")));
		// 6. Click Ã¯Â¿Â½EditÃ¯Â¿Â½ link
		appDeviceControl.click(DeviceInfo.EDIT);
		// 7. Click a Ã¯Â¿Â½DeleteÃ¯Â¿Â½ link which is next to the promotion slots
		// 8. Click Ã¯Â¿Â½OKÃ¯Â¿Â½ on delete confirmation dialog
		promotionControl.deleteADevicePromotionByName(DeviceEdit.PROMOTION_CONTAINER, dataGlobalPromotion1.get("name"));
		// 9. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The manual deleted promotion is not displayed under Device promotion section
		 */
		Assert.assertFalse(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion1.get("name").replace(" ", " - ")));
		// 10. Click Ã¯Â¿Â½EditÃ¯Â¿Â½ link
		appDeviceControl.click(DeviceInfo.EDIT);
		// 11. Turn the Global Promotion feature OFF
		appDeviceControl.click(DeviceEdit.GLOBAL_PROMOTIONS_ON);
		// 12. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: All global promotions are not displayed under Device Promotion section
		 */
		Assert.assertFalse(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion1.get("name").replace(" ", " - ")));
		Assert.assertFalse(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion2.get("name").replace(" ", " - ")));
		// 13. Click Ã¯Â¿Â½EditÃ¯Â¿Â½ link
		appDeviceControl.click(DeviceInfo.EDIT);
		// 14. Turn the Global Promotion feature OFF
		appDeviceControl.click(DeviceEdit.GLOBAL_PROMOTIONS_ON);
		// 15. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * The manual deleted promotions are not added back to promotion slots
		 */
		Assert.assertFalse(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion1.get("name").replace(" ", " - ")));
//		Assert.assertTrue(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion2.get("name").replace(" ", " - ")));
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
			3. Click Ã¯Â¿Â½Add App or DeviceÃ¯Â¿Â½ link
			4. Fill valid value into all required fields
			5. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link with default Global Promotion state
			VP: New app/device is created successfully and the published promotions are added into Device Promotions section
			6. Click Ã¯Â¿Â½EditÃ¯Â¿Â½ link
			7. Click a Ã¯Â¿Â½DeleteÃ¯Â¿Â½ link which is next to the promotion slots
			8. Click Ã¯Â¿Â½OKÃ¯Â¿Â½ on delete confirmation dialog
			9. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link
			VP: The manual deleted promotion is not displayed under Device promotion section
			10. Click Ã¯Â¿Â½PublishÃ¯Â¿Â½ link
			VP: The app/device is published successfully
			11. Click Ã¯Â¿Â½Update Product InfoÃ¯Â¿Â½ link
			12. Click Ã¯Â¿Â½OKÃ¯Â¿Â½ button on confirmation dialog
			VP: The app/device edit page is displayed
			13. Change some value of app/device
			14. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link
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
		String DTS_ID1 = appDeviceControl.getTextByXpath(PromotionInfo.DTS_ID);
		/*
		 * *************************************************************
		 */
		// 2. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.linkDevice);
		// 3. Click Ã¯Â¿Â½Add App or DeviceÃ¯Â¿Â½ link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill valid value into all required fields
		// 5. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link with default Global Promotion state
		Hashtable<String,String> dataDevice = TestData.appDevicePublish();
		dataDevice.remove("global promotion");
		dataDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice);
		appDeviceControl.editData(DeviceEdit.PROMOTION_SLOT_1, DTS_ID1);
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: New app/device is created successfully and the published promotions are added into Device Promotions section
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), dataDevice.get("name"));
		Assert.assertTrue(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion.get("name").replace(" ", " - ")));
		// 6. Click Ã¯Â¿Â½EditÃ¯Â¿Â½ link
		appDeviceControl.click(DeviceInfo.EDIT);
		// 7. Click a Ã¯Â¿Â½DeleteÃ¯Â¿Â½ link which is next to the promotion slots
		// 8. Click Ã¯Â¿Â½OKÃ¯Â¿Â½ on delete confirmation dialog
		promotionControl.deleteADevicePromotionByName(DeviceEdit.PROMOTION_CONTAINER, dataGlobalPromotion.get("name"));
		// 9. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The manual deleted promotion is not displayed under Device promotion section
		 */
		Assert.assertFalse(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion.get("name").replace(" ", " - ")));
		// 10. Click Ã¯Â¿Â½PublishÃ¯Â¿Â½ link
		appDeviceControl.click(DeviceInfo.PUBLISH);
		/*
		 * VP: The app/device is published successfully
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.PUBLICATION_STATUS), "Published");
		// 11. Click Ã¯Â¿Â½Update Product InfoÃ¯Â¿Â½ link
		appDeviceControl.click(DeviceInfo.CREATE_NEW_VERSION);
		// 12. Click Ã¯Â¿Â½OKÃ¯Â¿Â½ button on confirmation dialog
		appDeviceControl.selectConfirmationDialogOption("OK");
		/*
		 * VP: The app/device edit page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()), true);
		// 13. Change some value of app/device
		String newName = "NewDevice" + RandomStringUtils.randomNumeric(7);
		appDeviceControl.editData(DeviceEdit.NAME, newName);
		// 14. Click Ã¯Â¿Â½SaveÃ¯Â¿Â½ link
		appDeviceControl.click(DeviceEdit.SAVE);
		// Check if PDPP-1168 found
		if(appDeviceControl.checkADevicePromotionExistByName(dataGlobalPromotion.get("name").replace(" ", " - "))){
			appDeviceControl.addErrorLog("PDPP-1168: 640 Product Detail: The manual deleted promotions in previous app/device version are auto added back to newest version");
			productControl.deleteAccessorybyName(dataProduct.get("name"));
			Assert.assertTrue(false);
		}
		
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
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
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Hashtable<String,String> device = TestData.appDeviceDraft();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),device);
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
		
		// delete device
		appDeviceControl.doDelete(DeviceInfo.DELETE);
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
		VP: The "Refresh offline database" button is disabled and a refresh icon is displayed, "Edit","Update","Delete" and "Suspend" link are disable
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
		data.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		appDeviceControl.click(DeviceEdit.SAVE);
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
		appDeviceControl.waitForElementClickable(DeviceInfo.POPUP_REFRESH_CONFIRM);
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.POPUP_REFRESH_CONFIRM));
		//6. Choose "Refresh" button in "Refresh Database?" popup.
//		WebElement refreshOption = driver.findElement(By.xpath("//*[@href='javascript:;' and text() = 'Refresh']"));
//		appDeviceControl.click(refreshOption);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		/*
		 * VP: The "Refresh offline database" button is disabled and a refresh icon is displayed, 
		 * "Edit","Update","Delete" and "Suspend" link are disable
		 */
//		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.INDICATOR_LOADING));
//		Assert.assertEquals(ExpectedConditions.elementToBeClickable(By.xpath(DeviceInfo.REFRESH_OFFLINE_DATABASE)),"visible");
//		Assert.assertEquals(ExpectedConditions.elementToBeClickable(By.xpath(DeviceInfo.SUSPEND)),"visible");
//		Assert.assertEquals(ExpectedConditions.elementToBeClickable(By.xpath(DeviceInfo.DELETE)),"visible");
//		Assert.assertEquals(ExpectedConditions.elementToBeClickable(By.xpath(DeviceInfo.CREATE_NEW_VERSION)),"visible");
		//Delete device
		appDeviceControl.doDelete(DeviceInfo.DELETE);
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// Click "Add app or device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDevicePublish();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// 3. Navigate to "Apps & Devices" page
		// 4. Select a "Draft" app/device from apps/devices table
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
		4. Select a "Draft" app/device from apps/devices table
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// Click "Add app or device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDevicePublish();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// 3. Navigate to "Apps & Devices" page
		// 4. Select a "Draft" app/device from apps/devices table
		//VP: The "Refresh offline database" button is not displayed
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE));
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
//		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.INDICATOR_LOADING));
		// VP: The ""Refresh offline database"" buttom, ""Edit"",""Update"",""Delete"" and ""Suspend"" link are disable
//		Assert.assertTrue(appDeviceControl.isElementEditable(DeviceInfo.REFRESH_OFFLINE_DATABASE));
//		Assert.assertTrue(appDeviceControl.isElementEditable(DeviceInfo.EDIT));
//		Assert.assertTrue(appDeviceControl.isElementEditable(DeviceInfo.CREATE_NEW_VERSION));
//		Assert.assertTrue(appDeviceControl.isElementEditable(DeviceInfo.DELETE));
//		Assert.assertTrue(appDeviceControl.isElementEditable(DeviceInfo.SUSPEND));
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
		5. Click "Download offline database" button
		VP: Offline Database is downloaded successfully
		/*
		 * Pre-condition: Create a new Draft app/device
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		appDeviceControl.click(PageHome.LINK_COMPANY);
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		appDeviceControl.click(CompanyInfo.ADD_BRAND);
		Hashtable<String,String> newBrand = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(),newBrand);
		// Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// Click "Add app or device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDevicePublish();
		data.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		appDeviceControl.click(DeviceEdit.SAVE);
		appDeviceControl.click(DeviceInfo.PUBLISH);
		// 3. Navigate to "Apps & Devices" page
		// 4. Select a published app/device from apps/devices table
		//VP: The "Refresh offline database" button is not displayed
		//Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE));
		// 5. Click "Refresh offline database" buttom
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		// VP: Offline Database is downloaded successfully
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		appDeviceControl.click(PageHome.LINK_COMPANY);
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		companyControl.selectABrandByName(newBrand.get("name"));
		companyControl.doDelete(BrandInfo.DELETE_LINK);
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
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Combo0.getName());
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Combo0.getName());
		/*
		 * VP: The tuning file is uploaded successfully.
		 */
		Assert.assertTrue(driver.findElement(By.partialLinkText(DeviceEdit.routes.Combo0.getName())).isDisplayed());
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached1_Internal_Speakers_Off.getName());
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached1InternalSpeakersOff_Custom.getName());
		/*
		 * VP: The tuning file is uploaded successfully.
		 */
		Assert.assertTrue(driver.findElement(By.partialLinkText(DeviceEdit.routes.Attached1_Internal_Speakers_Off.getName())).isDisplayed());
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
		6. Input "6" in Promotion Slots Available
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
		//6. Input "6" in Promotion Slots Available
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
		appDeviceControl.waitForElementClickable(PromotionInfo.PUBLISH);
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
		appDeviceControl.waitForElementClickable(PromotionInfo.PUBLISH);
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
		appDeviceControl.dragDropPromo(1,2);
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
		
		// Delete device
		appDeviceControl.selectADeviceByName(device_name);
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.deleteAccessorybyName(dataProduct1.get("name"));
		productControl.deleteAccessorybyName(dataProduct2.get("name"));
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
		6. Input "6" in Promotion Slots Available
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
		//6. Input "6" in Promotion Slots Available
		Hashtable<String,String> data = TestData.appDevicePublish();
		data.remove("global promotion");
		data.put("global promotion","ON");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),data);
		//7. Input some valid Global Promotions ID which are published
		appDeviceControl.click(DeviceInfo.EDIT);
		//8. Drag and Drop Promotion ID by list order
		ArrayList<String> before_list1= appDeviceControl.getListOrder();
		appDeviceControl.dragDropPromo(1,2);
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
		/*
		 * VP: In Device Promotions , System correctly displays the list order of Promotions ID which were dragged and dropped by user before (step 8)
		 */
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Published"),after_list1));
		//17. Click "View Current Version" link
		appDeviceControl.click(DeviceInfo.VIEW_CURRENT_VERSION);
		/*VP: In Device Promotions section, System correctly displays the 
		list order of Promotions ID which were dragged and dropped by user before (step 8)
		*/
		Assert.assertTrue(appDeviceControl.checkListOrder(appDeviceControl.getListPromo("Draft"),after_list1));
		
		// Delete device
		appDeviceControl.doDelete(DeviceInfo.DELETE);

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
		6. Input "6" in Promotion Slots Available
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
		//6. Input "6" in Promotion Slots Available
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
		appDeviceControl.dragDropPromo(1,2);
		ArrayList<String> after_list1= appDeviceControl.getListOrder();
		/*
		 * VP: user can drag and drop promotion slots 
		 */
		Assert.assertTrue(appDeviceControl.isPromotionOrder(1,2,before_list1,after_list1));
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
		
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.deleteAccessorybyName(dataProduct1.get("name"));
		productControl.deleteAccessorybyName(dataProduct2.get("name"));
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
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached2_Internal_Speakers_Mode_1.getName());
		//7. Click "Choose fille" to upload custom tuning file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached2InternalSpeakersMusic_Custom.getName());
		/*
		 * VP: The custom tuning file is uploaded successfully.
		 */
		Assert.assertTrue(driver.findElement(By.partialLinkText(DeviceEdit.routes.Attached2_Internal_Speakers_Mode_1.getName())).isDisplayed());
		//8. Select "Attached4 - Internal Speakers (mode 3)" in Audio Route
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached4_Internal_Speakers_Mode_3.getName());
		//9. Click "Choose fille" to upload custom tuning file 
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached4InternalSpeakersGames_Custom.getName());
		/*
		 * VP: The custom tuning file is uploaded successfully.
		 */
		Assert.assertTrue(driver.findElement(By.partialLinkText(DeviceEdit.routes.Attached4_Internal_Speakers_Mode_3.getName())).isDisplayed());
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
		Assert.assertTrue(driver.findElement(By.partialLinkText(DeviceEdit.routes.Attached2_Internal_Speakers_Mode_1.getName())).isDisplayed());
		Assert.assertTrue(driver.findElement(By.partialLinkText(DeviceEdit.routes.Attached4_Internal_Speakers_Mode_3.getName())).isDisplayed());
		
		
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
		11. Click "Save" link
		VP: "Medium (500x500px)" option is displayed in "Headphone Image Sizes" section
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
		Assert.assertFalse(appDeviceControl.getTextByXpath(DeviceInfo.MEDIUM_IMAGE).contains("Medium (500x500 px)"));
		//8. Click "publish" link
		appDeviceControl.click(DeviceInfo.PUBLISH);
		/*
		 * VP: "Medium (500x500px)" option is not displayed in "Headphone Image Sizes" section
		 */
		Assert.assertFalse(appDeviceControl.getTextByXpath(DeviceInfo.MEDIUM_IMAGE_PUBLISHED).contains("Medium (500x500 px)"));
		//9. Click "Edit Version" link
		appDeviceControl.editVersion();
		//10. Reselect ""Medium (500x500px)"" option in ""Headphone Image Sizes"
		appDeviceControl.selectACheckbox(DeviceEdit.MEDIUM_IMAGE);
		//11. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: "Medium (500x500px)" option is displayed in "Headphone Image Sizes" section
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceInfo.MEDIUM_IMAGE).contains("Medium (500x500 px)"));
		//12. Click "Publish" link
		appDeviceControl.click(DeviceInfo.PUBLISH);
		/*
		 * VP: "Medium (500x500px)" option is displayed in "Headphone Image Sizes" section
		 */
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceInfo.MEDIUM_IMAGE_PUBLISHED).contains("Medium (500x500 px)"));

	}
	
	
	/*
	 * Verify that the system automatically add promotions from brands selected in "Headphone Brands" list below
	 */
	@Test
	public void TC640DADD_32(){
		Reporter.log("Verify that the system automatically add promotions from brands selected in 'Headphone Brands' list below");

		/*
		 * Pre-Condition: There are some promotions belong to specified brands
		 	1. Navigate to DTS portal	
			2. Log into DTS portal as a DTS user successfully	
			3. Navigate to "Apps & Devices" page	
			4. Click "Add App or Device" link	
			VP: The Device Edit Page is displayed
			5. Fill all valid values into required fields	
			6. Switch to ON in "Global Promotions"	
			7. Select "Only the following brands" in "Headphone Brands" section	
			8. List some brands in "Only the following brands" field	
			9. Click "Save" link	
			VP: The "Device Promotions" section displays only promotions from brands selected in "Headphone Brands" section

		 */
		
		//2. Log into DTS portal as a DTS user successfully	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		/*
		 * Pre-Condition: There are some promotions belong to specified brands
		 */
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
		Assert.assertTrue(promotionControl.checkPromotionExistByName(PromotionList.PROMOTION_TABLE,dataGlobalPromotion1.get("name")));
		// Click Add promo link
		appDeviceControl.click(PromotionList.ADD_PROMO);
		// Create 2nd Global promotion
		Hashtable<String,String> dataGlobalPromotion2 = TestData.promotionGlobal(dataProduct2.get("brand") + " " + dataProduct2.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataGlobalPromotion2);
		// Publish promotion
		appDeviceControl.click(PromotionInfo.PUBLISH);
		
		//3. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//4. Click "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * VP: The Device Edit Page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()),true);
		//5. Fill all valid values into required fields	
		//6. Switch to ON in "Global Promotions"
		Hashtable<String,String> deviceData = TestData.appDeviceDraft();
		deviceData.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		//7. Select "Only the following brands" in "Headphone Brands" section
		appDeviceControl.click(DeviceEdit.ONLY_BRANDS);
		//8. List some brands in "Only the following brands" field
//		appDeviceControl.editData(DeviceEdit.BRAND_FILTER,PARTNER_BRAND_NAME_1);
//		WebElement brand_filter = driver.findElement(By.xpath(DeviceEdit.BRAND_FILTER));
//		brand_filter.sendKeys(Keys.PAGE_DOWN);
//		appDeviceControl.autoTool.send("{ENTER}",false);
		appDeviceControl.inputData(DeviceEdit.BRAND_FILTER, PARTNER_BRAND_NAME_1);
		//9. Click "Save" link	
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * VP: The "Device Promotions" section displays only promotions from brands selected in "Headphone Brands" section
		 */
		ArrayList<String> listPromo = appDeviceControl.getListPromo("Draft");
		Assert.assertTrue(ListUtil.checkListContainSubString(listPromo, PARTNER_BRAND_NAME_1));
		
		// Teardown
		appDeviceControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(dataProduct1.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		productControl.selectAccessorybyName(dataProduct2.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the "Init Database" is downloaded successfully when clicking on the "Init Database" link
	 */
	@Test
	public void TC640DADD_33(){
		Reporter.log("Verify that the Init Database is downloaded successfully when clicking on the 'Init Database' link");
		
		/*1. Navigate to DTS portal	
		2. Log into DTS portal as a DTS user successfully	
		3. Navigate to "Apps & Devices" page	
		4. Click "Add App or Device" link	
		5. Fill all valid values into required fields	
		6. Upload a default tuning file successfully	
		7. Click "Save" link	
		8. Click "Publish" link	
		9. Click "Init Database" link	
		VP: The init database is downloaded successfully
		*/
		

//		2. Log into DTS portal as a DTS user successfully	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		3. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		4. Click "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		5. Fill all valid values into required fields	
//		6. Upload a default tuning file successfully
//		7. Click "Save" link	
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
//		8. Click "Publish" link	
		appDeviceControl.click(DeviceInfo.PUBLISH);
//		9. Click "Init Database" link	
//		VP: The init database is downloaded successfully
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.INIT_DATABASE_LINK));
		
		// Delete device
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that Device Headphone tuning is displayed correctly in Audio Route section
	 */
	@Test
	public void TC640DADD_34(){
		Reporter.log("Verify that Device Headphone tuning is displayed correctly in Audio Route section");
		
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			VP: The Device Edit page is displayed
			4. Fill valid value into all required fields
			5. Leave the Product Type panel as â€œHPX Lowâ€�
			6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
			7. Select â€œMode 2â€� in â€œContent Modesâ€� dropdown list
			8. Click on "Select file" button in "Custom Tuning" section
			9. Upload the valid custom tuning DTSCS file
			VP: Verify that the valid tuning DTSCS file is uploaded successfully
			10. Click "Save" link
			VP: Verify that the custom tuning file is displayed in â€œAudio Routeâ€� section
		 
		*/
		

		// 1. Log into DTS portal as a DTS user successfully	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill all valid values into required fields
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		deviceData.remove("save");
		// 5. Leave the Product Type panel as â€œHPX Lowâ€�
		deviceData.put("eagle version", "Eagle 1.4");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		// 6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		// 7. Select â€œMode 2â€� in â€œContent Modesâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_2.getType());
		// 8. Click on "Select file" button in "Custom Tuning" section
		// 9. Upload the valid custom tuning DTSCS file
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Mode2.getName());
		// VP: Verify that the valid tuning DTSCS file is uploaded successfully
		Assert.assertTrue(appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Mode2.getName().replaceAll(".dtscs", "")));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON));
		// 10. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: Verify that the custom tuning file is displayed in â€œAudio Routeâ€� section
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceInfo.CUSTOM_TUNING_FILE).contains(DeviceEdit.routes.Device_Headphone.getName()));
		// Delete device
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that â€œEagle Versionâ€� feature is displayed correctly in 640D Device Detail page
	 */
	@Test
	public void TC640DADD_35(){
		Reporter.log("Verify that â€œEagle Versionâ€� feature is displayed correctly in 640D Device Detail page");
		
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devicesâ€� page
			3. Click â€œAdd App & Deviceâ€� link
			4. Fill valid value into all required fields
			5. Leave the Eagle Version panel as â€œEagle 1.4â€�
			6. Click â€œSaveâ€� link
			VP: Verify that Eagle Version panel as â€œEagle 1.4â€� is displayed in 640D Device page
			7. Click â€œEditâ€� link
			8. Change the Eagle Version panel to â€œEagle 2.0â€�
			9. Click â€œSaveâ€� link
			VP: Verify that Eagle Version panel as â€œEagle 2.0â€� is displayed in 640D Device page
		 
		*/
		

		// 1. Log into DTS portal as a DTS user successfully	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill all valid values into required fields
		Hashtable<String,String> deviceData = TestData.appDeviceDraft();
		deviceData.remove("save");
		// 5. Leave the Eagle Version panel as â€œEagle 1.4â€�
		deviceData.put("eagle version", "Eagle 1.4");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		// 6. Click â€œSaveâ€� link
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: Verify that Eagle Version panel as â€œEagle 1.4â€� is displayed in 640D Device page
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceInfo.EAGLE_VERSION).equals(DeviceEdit.Eagle_Version.Eagle1_4.getName()));
		// 7. Click â€œEditâ€� link
		appDeviceControl.click(DeviceInfo.EDIT);
		// 8. Change the Eagle Version panel to â€œEagle 2.0â€�
		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
		// 9. Click â€œSaveâ€� link
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: Verify that Eagle Version panel as â€œEagle 2.0â€� is displayed in 640D Device page
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceInfo.EAGLE_VERSION).equals(DeviceEdit.Eagle_Version.Eagle2_0.getName()));
		// Delete device
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that Device Eagle Version is 1.4, the offline DB only include Eagle settings data match with Device's Eagle version
	 */
	@Test
	public void TC640DADD_36(){
		Reporter.log("Verify that Device Eagle Version is 1.4, the offline DB only include Eagle settings data match with Device's Eagle version");
		
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devicesâ€� page
			3. Click â€œAdd App & Deviceâ€� link
			4. Fill valid value into all required fields
			5. Leave the Eagle Version panel as â€œEagle 1.4â€�
			6. Click â€œSaveâ€� link
			7. Publish above device
			8. Refresh offline database
			9. Click "Download Offline Database" link
			10. Open the offline databse file by SQLite
			11. Navigate to Asset table in SQLite
			VP: Verify that the tuning Blog data only include Eagle settings data 1.4
			12. Check eagle_version of above device on DB
			VP: Verify that eagle_version of above device is 1.4
		 
		*/
		

		// 1. Log into DTS portal as a DTS user successfully	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill all valid values into required fields
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		deviceData.remove("save");
		// 5. Leave the Eagle Version panel as â€œEagle 1.4â€�
		deviceData.put("eagle version", "Eagle 1.4");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		// 6. Click â€œSaveâ€� link
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: Verify that Eagle Version panel as â€œEagle 1.4â€� is displayed in 640D Device page
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceInfo.EAGLE_VERSION).equals(DeviceEdit.Eagle_Version.Eagle1_4.getName()));
		// 7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 8. Refresh offline database
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		// 9. Click "Download Offline Database" link
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
		// 10. Open the offline databse file by SQLite
		// 11. Navigate to Asset table in SQLite
		// VP: Verify that the tuning Blog data only include Eagle settings data 1.4
		// Navigate to Product table in SQLite
		// Get DTSCSAssetId from Product where Type = Headphone or Type = Standard
		SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
		ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where Type = 'Headphone' or Type = 'Standard'", "DTSCSAssetId", null);
		for (int i = 0; i < ColumnData.size(); i++) {
			Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
			Assert.assertTrue(appDeviceControl.isMapEagleVersion(device_UUID, DeviceEdit.Eagle_Version.Eagle1_4.getName()));
		}
		// 12. Check eagle_version of above device on DB
		// VP: Verify that eagle_version of above device is 1.4
		Assert.assertTrue(DbUtil.selectStatment("Select eagle_version from device where dts_id = '" + device_UUID + "'").contains("Eagle_v1_4"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that Device Eagle Version is 2.0, the offline DB only include Eagle settings data match with Device's Eagle version
	 */
	@Test
	public void TC640DADD_37(){
		Reporter.log("Verify that Device Eagle Version is 2.0, the offline DB only include Eagle settings data match with Device's Eagle version");
		
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devicesâ€� page
			3. Click â€œAdd App & Deviceâ€� link
			4. Fill valid value into all required fields
			5. Leave the Eagle Version panel as â€œEagle 2.0â€�
			6. Click â€œSaveâ€� link
			7. Publish above device
			8. Refresh offline database
			9. Click "Download Offline Database" link
			10. Open the offline databse file by SQLite
			11. Navigate to Config table in SQLite
			VP: Verify that Config table will contain Device Version 2.0
			12. Navigate to Asset table in SQLite
			VP: Verify that the tuning Blog data only include Eagle settings data 2.0
		 
		*/
		

		// 1. Log into DTS portal as a DTS user successfully	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill all valid values into required fields
		// 5. Leave the Eagle Version panel as â€œEagle 2.0â€�
		Hashtable<String,String> deviceData = TestData.appDevicePublishSingleFile();
		deviceData.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		// 6. Click â€œSaveâ€� link
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: Verify that Eagle Version panel as â€œEagle 2.0â€� is displayed in 640D Device page
		// 7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 8. Refresh offline database
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		// 9. Click "Download Offline Database" link
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
		// 10. Open the offline databse file by SQLite
		// 11. Navigate to Asset table in SQLite
		// VP: Verify that the tuning Blog data only include Eagle settings data 2.0
		SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
		ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where Type = 'Headphone' or Type = 'Standard'", "DTSCSAssetId", null);
		for (int i = 0; i < ColumnData.size(); i++) {
			Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
			Assert.assertTrue(appDeviceControl.isMapEagleVersion(device_UUID, DeviceEdit.Eagle_Version.Eagle2_0.getName()));
		}
		// 12. Check eagle_version of above device on DB
		// VP: Verify that eagle_version of above device is 2.0
		Assert.assertTrue(DbUtil.selectStatment("Select eagle_version from device where dts_id = '" + device_UUID + "'").contains("Eagle_v2_0"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that Device Eagle Version is 1.4, the offline DB include inbox headphone tuning which have the Eagle settings data 1.4
	 */
	@Test
	public void TC640DADD_38(){
		Reporter.log("Verify that Device Eagle Version is 1.4, the offline DB include inbox headphone tuning which have the Eagle settings data 1.4");
		
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devicesâ€� page
			3. Click â€œAdd App & Deviceâ€� link
			4. Fill valid value into all required fields
			5. Leave the Eagle Version panel as â€œEagle 1.4â€�
			6. Enter a headphone name into In-box Headphones section
			7. Click â€œSaveâ€� link
			8. Publish above device
			9. Refresh offline database
			10. Click "Download Offline Database" link
			11. Open the offline databse file by SQLite
			12. Navigate to Asset table in SQLite
			VP: Verify that the inbox headphone's tuning Blog data only include Eagle settings data 1.4
		 
		*/
		

		// 1. Log into DTS portal as a DTS user successfully	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Create a published product
		appDeviceControl.click(PageHome.linkAccessories);
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndPublish(dataProduct);
		String UUID = appDeviceControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
		// 2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill all valid values into required fields
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		deviceData.remove("save");
		// 5. Leave the Eagle Version panel as â€œEagle 1.4â€�
		deviceData.put("eagle version", "Eagle 1.4");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		// 6. Enter a headphone name into In-box Headphones section
		appDeviceControl.inputInboxHP(DeviceEdit.INBOX_FIELD, dataProduct.get("name"));
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		// 7. Click â€œSaveâ€� link
		appDeviceControl.click(DeviceEdit.SAVE);
		// 8. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 9. Refresh offline database
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		// 10. Click "Download Offline Database" link
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
		// 11. Open the offline databse file by SQLite
		// Navigate to Product table in SQLite
		// Get DTSCSAssetId from Product where Uuid = UUID
		SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
		ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where Uuid = '" + UUID + "'", "DTSCSAssetId", null);
		Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(0).toString() + "'", "Data", device_UUID));
		// 12. Navigate to Asset table in SQLite
		// VP: Verify that the inbox headphone's tuning Blog data only include Eagle settings data 1.4
		Assert.assertTrue(appDeviceControl.isMapEagleVersion(device_UUID, DeviceEdit.Eagle_Version.Eagle1_4.getName()));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	
	/*
	 * Verify that Device Eagle Version is 2.0, the offline DB include inbox headphone tuning which have the Eagle settings data 2.0
	 */
	@Test
	public void TC640DADD_39(){
		Reporter.log("Verify that Device Eagle Version is 2.0, the offline DB include inbox headphone tuning which have the Eagle settings data 2.0");
		
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devicesâ€� page
			3. Click â€œAdd App & Deviceâ€� link
			4. Fill valid value into all required fields
			5. Leave the Eagle Version panel as â€œEagle 2.0â€�
			6. Enter a headphone name into In-box Headphones section
			7. Click â€œSaveâ€� link
			8. Publish above device
			9. Refresh offline database
			10. Click "Download Offline Database" link
			11. Open the offline databse file by SQLite
			12. Navigate to Asset table in SQLite
			VP: Verify that the inbox headphone's tuning Blog data only include Eagle settings data 2.0

		*/
		

/*		// 1. Log into DTS portal as a DTS user successfully	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Create a published product
		appDeviceControl.click(PageHome.linkAccessories);
		appDeviceControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndPublish(dataProduct);
		String UUID = appDeviceControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
		// 2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill all valid values into required fields
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		deviceData.remove("save");
		deviceData.put("audio route", AddEditProductModel.FileUpload.Default_External_Audio_EagleV20.getName());
		// 5. Leave the Eagle Version panel as â€œEagle 2.0â€�
		deviceData.put("eagle version", "Eagle 2.0");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		// 6. Enter a headphone name into In-box Headphones section
		//appDeviceControl.inputInboxHP(DeviceEdit.INBOX_FIELD, dataProduct.get("name"));
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		// 7. Click â€œSaveâ€� link
		appDeviceControl.click(DeviceEdit.SAVE);
		// 8. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 9. Refresh offline database
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		// 10. Click "Download Offline Database" link
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
		// 11. Open the offline databse file by SQLite
		// Navigate to Product table in SQLite
		// Get DTSCSAssetId from Product where Uuid = UUID
		SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
		ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where Uuid = '" + UUID + "'", "DTSCSAssetId", null);
		Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(0).toString() + "'", "Data", device_UUID));
		// 12. Navigate to Asset table in SQLite
		// VP: Verify that the inbox headphone's tuning Blog data only include Eagle settings data 1.4
		Assert.assertTrue(appDeviceControl.isMapEagleVersion(device_UUID, DeviceEdit.Eagle_Version.Eagle2_0.getName()));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}*/
	
	/*
	 * Verify that Device Eagle Version 1.4, the Complete Profile only include Eagle settings data 1.4
	 */
	}
	@Test
	public void TC640DADD_40(){
		Reporter.log("Verify that Device Eagle Version 1.4, the Complete Profile only include Eagle settings data 1.4");
		
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devicesâ€� page
			3. Click â€œAdd App & Deviceâ€� link
			4. Fill valid value into all required fields
			5. Leave the Eagle Version panel as â€œEagle 1.4â€�
			6. Click â€œSaveâ€� link
			7. Publish above device
			8. Click â€œComplete Profileâ€� link
			9. Open file Complete Profile
			VP: Verify that the Complete Profile only include Eagle settings data 1.4

		*/
		

		// 1. Log into DTS portal as a DTS user successfully	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill all valid values into required fields
		Hashtable<String,String> deviceData = TestData.appDevicePublish();
		deviceData.remove("save");
		deviceData.put("audio route", AddEditProductModel.FileUpload.Default_External_Audio.getName());
		// 5. Leave the Eagle Version panel as â€œEagle 1.4â€�
		deviceData.put("eagle version", "Eagle 1.4");
		deviceData.put("global promotion", "ON");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		// Upload the custom tuning file
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Line_out0.getName());
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones_Lineout.getName());
		// 6. Click â€œSaveâ€� link
		appDeviceControl.click(DeviceEdit.SAVE);
		// 7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String DTS_ID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 8. Click â€œComplete Profileâ€� link
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
		// 9. Open file Complete Profile
		// VP: Verify that the Complete Profile only include Eagle settings data 1.4
		Assert.assertTrue(appDeviceControl.isMapEagleVersion(DTS_ID, DeviceEdit.Eagle_Version.Eagle1_4.getName()));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that Device Eagle Version 2.0, the Complete Profile only include Eagle settings data 2.0
	 */
	@Test
	public void TC640DADD_41(){
		Reporter.log("Verify that Device Eagle Version 2.0, the Complete Profile only include Eagle settings data 2.0");
		
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Apps or Devicesâ€� page
			3. Click â€œAdd App & Deviceâ€� link
			4. Fill valid value into all required fields
			5. Leave the Eagle Version panel as â€œEagle 2.0â€�
			6. Click â€œSaveâ€� link
			7. Publish above device
			8. Click â€œComplete Profileâ€� link
			9. Open file Complete Profile
			VP: Verify that the Complete Profile only include Eagle settings data 2.0

		*/
		

		// 1. Log into DTS portal as a DTS user successfully	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Apps & Devices" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click "Add App or Device" link	
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 4. Fill all valid values into required fields
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
		Hashtable<String,String> deviceData = TestData.appDevicePublishSingleFile();
		//deviceData.put("audio route", AddEditProductModel.FileUpload.Default_External_Audio_EagleV20.getName());
		deviceData.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), deviceData);
		// 5. Leave the Eagle Version panel as â€œEagle 2.0â€�
		deviceData.put("eagle version", "Eagle 2.0");
		deviceData.put("global promotion", "ON");
		// Upload the custom tuning file
		//appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Line_out0.getName());
		//appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
		//appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones_Lineout_EagleV20.getName());
		// 6. Click â€œSaveâ€� link
		appDeviceControl.click(DeviceEdit.SAVE);
		// 7. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String DTS_ID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 8. Click â€œComplete Profileâ€� link
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
		// 9. Open file Complete Profile
		// VP: Verify that the Complete Profile only include Eagle settings data 2.0
		Assert.assertTrue(appDeviceControl.isMapEagleVersion(DTS_ID, DeviceEdit.Eagle_Version.Eagle2_0.getName()));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
//	} prefer ticket MPG-5927
//	@Test
//	public void TC640DADD_42(){
//		Reporter.log("Verify that 640D Device Detail page display Device Tuning file when uploading valid CTC/QPEG tuning blob file");
//	/*
//	 	1. Log into DTS portal as a DTS user
//		2. Navigate to "Apps or Devicesâ€� page
//		3. Click â€œAdd App & Deviceâ€� link
//		4. Fill valid value into all required fields
//		5. Leave â€œEagle versionâ€� section is â€œEagle 2.0â€�
//		6. Select â€œGPEQ tuningâ€� in Device Tuning combobox
//		7. Upload the valid GPEQ tuning blob file
//		8. Select â€œCTC tuningâ€� in Device Tuning combobox
//		9. Upload the valid CTC tuning blob file
//		10. Click â€œSaveâ€� link
//	 */
//		
//		// 1. Log into DTS portal as a DTS user
//		loginControl.login(DTS_USER, DTS_PASSWORD);
//		// 2. Navigate to "Products" page	
//		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		// 3. Click â€œAdd App or Deviceâ€� link
//		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		// 4. Fill valid value into all required fields
//		Hashtable<String, String> newDevice = TestData.appDevicePublish();
//		newDevice.remove("save");
//		newDevice.put("audio route", AddEditProductModel.FileUpload.Default_External_Audio_EagleV20.getName());
//		// 5. Leave the Eagle Version panel as â€œEagle 2.0â€�
//		newDevice.put("eagle version", "Eagle 2.0");
//		appDeviceControl.createNewDevice(DeviceEdit.getHash(),newDevice);
//		// 6. Select â€œGPEQ tuningâ€� from Device Tuning combobox
//		appDeviceControl.selectOptionByName(DeviceEdit.DEVICE_TUNING, DeviceEdit.Device_Tunings.GPEQ_Tuning.getName());
//		// 7. Upload the valid GPEQ tuning blob
//		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_DEVICE_TUNING, AddEditProductModel.FileUpload.Device_Tuning_GPEQ.getName());
//		// 8. Select â€œCTC tuningâ€� in Device Tuning combobox
//		appDeviceControl.selectOptionByName(DeviceEdit.DEVICE_TUNING, DeviceEdit.Device_Tunings.CTC_Tuning.getName());
//		// 9. Upload the valid CTC tuning blob file
//		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_DEVICE_TUNING, AddEditProductModel.FileUpload.Device_Tuning_CTC.getName());
//		// 10. Click â€œSaveâ€� link
//		appDeviceControl.click(DeviceEdit.SAVE);
//		// Verify that 640D Device Detail page display GPEQ and CTC file
//		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceInfo.DEVICE_TUNING_CONTAINER).contains(DeviceEdit.Device_Tunings.GPEQ_Tuning.getName()));
//		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceInfo.DEVICE_TUNING_CONTAINER).contains(DeviceEdit.Device_Tunings.CTC_Tuning.getName()));
//	}prefer ticket MPG-5927
//	
//	@Test
//	public void TC640DADD_43(){
//		Reporter.log("Verify that when uploading GPEQ tuning, the offline DB will have a new entry in the Product table with Type as â€œDeviceTuningâ€� and SubType as â€œGPEQâ€�");
//	/*
//	 	1. Log into DTS portal as a DTS user
//		2. Navigate to "Apps or Devicesâ€� page
//		3. Click â€œAdd App & Deviceâ€� link
//		4. Fill valid value into all required fields
//		5. Leave â€œEagle versionâ€� section is â€œEagle 2.0â€�
//		6. Select â€œGPEQ tuningâ€� in Device Tuning combobox
//		7. Upload the valid GPEQ tuning blob file
//		8. Click â€œSaveâ€� link
//		9. Publish a device above
//		10. Refresh offline DB
//		11. Click "Download Offline Database" link
//		12. Open the offline databse file by SQLite
//		13. Navigate to Product table in SQLite
//
//	 */
//		
//		// 1. Log into DTS portal as a DTS user
//		loginControl.login(DTS_USER, DTS_PASSWORD);
//		// 2. Navigate to "Products" page	
//		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		// 3. Click â€œAdd App or Deviceâ€� link
//		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		// 4. Fill valid value into all required fields
//		Hashtable<String, String> newDevice = TestData.appDevicePublish();
//		newDevice.remove("save");
//		newDevice.put("audio route", AddEditProductModel.FileUpload.Default_External_Audio_EagleV20.getName());
//		// 5. Leave the Eagle Version panel as â€œEagle 2.0â€�
//		newDevice.put("eagle version", "Eagle 2.0");
//		appDeviceControl.createNewDevice(DeviceEdit.getHash(),newDevice);
//		// 6. Select â€œGPEQ tuningâ€� from Device Tuning combobox
//		appDeviceControl.selectOptionByName(DeviceEdit.DEVICE_TUNING, DeviceEdit.Device_Tunings.GPEQ_Tuning.getName());
//		// 7. Upload the valid GPEQ tuning blob
//		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_DEVICE_TUNING, AddEditProductModel.FileUpload.Device_Tuning_GPEQ.getName());
//		// 8. Click â€œSaveâ€� link
//		appDeviceControl.click(DeviceEdit.NO_IMAGES );
//		appDeviceControl.click(DeviceEdit.SAVE);
//		// 9. Publish a device above
//		appDeviceControl.click(DeviceInfo.PUBLISH);
//		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		// 10. Refresh offline DB
//		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
//		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		// 11. Click "Download Offline Database" link
//		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//		// 12. Open the offline databse file by SQLite		
//		// 13. Navigate to Product table in SQLite
//		// VP: Verify that Product table has a new entry with Type as â€œDeviceTuningâ€� and SubType as â€œGPEQâ€�
//		Assert.assertTrue(appDeviceControl.isDBTableContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product", "Type", "SubType", "DeviceTuning", "GPEQ"));

//	} prefer ticket MPG-5927
//	
//
//	@Test
//	public void TC640DADD_44(){
//		Reporter.log("Verify that when uploading CTC tuning, the offline DB will have a new entry in the Product table with Type as â€œDeviceTuningâ€� and SubType as â€œCTCâ€�");
//	/*
//	 	1. Log into DTS portal as a DTS user
//		2. Navigate to "Apps or Devicesâ€� page
//		3. Click â€œAdd App & Deviceâ€� link
//		4. Fill valid value into all required fields
//		5. Leave â€œEagle versionâ€� section is â€œEagle 2.0â€�
//		6. Select â€œCTC tuningâ€� in Device Tuning combobox
//		7. Upload the valid CTC tuning blob file
//		8. Click â€œSaveâ€� link
//		9. Publish a device above
//		10. Refresh offline DB
//		11. Click "Download Offline Database" link
//		12. Open the offline databse file by SQLite
//		13. Navigate to Product table in SQLite
//
//	 */
//		// 1. Log into DTS portal as a DTS user
//		loginControl.login(DTS_USER, DTS_PASSWORD);
//		// 2. Navigate to "Products" page	
//		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		// 3. Click â€œAdd App or Deviceâ€� link
//		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		// 4. Fill valid value into all required fields
//		Hashtable<String, String> newDevice = TestData.appDevicePublish();
//		newDevice.remove("save");
//		newDevice.put("audio route", AddEditProductModel.FileUpload.Default_External_Audio_EagleV20.getName());
//		// 5. Leave the Eagle Version panel as â€œEagle 2.0â€�
//		newDevice.put("eagle version", "Eagle 2.0");
//		appDeviceControl.createNewDevice(DeviceEdit.getHash(),newDevice);
//		// 6. Select â€œCTC tuningâ€� from Device Tuning combobox
//		appDeviceControl.selectOptionByName(DeviceEdit.DEVICE_TUNING, DeviceEdit.Device_Tunings.CTC_Tuning.getName());
//		// 7. Upload the valid CTC tuning blob
//		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_DEVICE_TUNING, AddEditProductModel.FileUpload.Device_Tuning_CTC.getName());
//		// 8. Click â€œSaveâ€� link
//		appDeviceControl.click(DeviceEdit.NO_IMAGES );
//		appDeviceControl.click(DeviceEdit.SAVE);
//		// 9. Publish a device above
//		appDeviceControl.click(DeviceInfo.PUBLISH);
//		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		// 10. Refresh offline DB
//		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
//		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		// 11. Click "Download Offline Database" link
//		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//		// 12. Open the offline databse file by SQLite		
//		// 13. Navigate to Product table in SQLite
//		// VP: Verify that Product table has a new entry with Type as â€œDeviceTuningâ€� and SubType as â€œGPEQâ€�
//		Assert.assertTrue(appDeviceControl.isDBTableContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product", "Type", "SubType", "DeviceTuning", "CTC"));

//	} prefer ticket MPG-5927
//	
//	@Test
//	public void TC640DADD_45(){
//		Reporter.log("Verify that when uploading GPEQ tuning, the offline DB will include GPEQ tuning blob in Asset table");
//	/*
//	 	1. Log into DTS portal as a DTS user
//		2. Navigate to "Apps or Devicesâ€� page
//		3. Click â€œAdd App & Deviceâ€� link
//		4. Fill valid value into all required fields
//		5. Leave â€œEagle versionâ€� section is â€œEagle 2.0â€�
//		6. Select â€œGPEQ tuningâ€� in Device Tuning combobox
//		7. Upload the valid GPEQ tuning blob file
//		8. Click â€œSaveâ€� link
//		9. Publish a device above
//		10. Refresh offline DB
//		11. Click "Download Offline Database" link
//		12. Open the offline databse file by SQLite
//		13. Navigate to Asset table in SQLite
//	 */
//		
//		// 1. Log into DTS portal as a DTS user
//		loginControl.login(DTS_USER, DTS_PASSWORD);
//		// 2. Navigate to "Products" page	
//		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		// 3. Click â€œAdd App or Deviceâ€� link
//		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		// 4. Fill valid value into all required fields
//		Hashtable<String, String> newDevice = TestData.appDevicePublish();
//		newDevice.remove("save");
//		newDevice.put("audio route", AddEditProductModel.FileUpload.Default_External_Audio_EagleV20.getName());
//		// 5. Leave the Eagle Version panel as â€œEagle 2.0â€�
//		newDevice.put("eagle version", "Eagle 2.0");
//		appDeviceControl.createNewDevice(DeviceEdit.getHash(),newDevice);
//		// 6. Select â€œGPEQ tuningâ€� from Device Tuning combobox
//		appDeviceControl.selectOptionByName(DeviceEdit.DEVICE_TUNING, DeviceEdit.Device_Tunings.GPEQ_Tuning.getName());
//		// 7. Upload the valid GPEQ tuning blob
//		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_DEVICE_TUNING, AddEditProductModel.FileUpload.Device_Tuning_GPEQ.getName());
//		appDeviceControl.click(DeviceEdit.NO_IMAGES);
//		// 8. Click â€œSaveâ€� link
//		appDeviceControl.click(DeviceEdit.SAVE);
//		// 9. Publish a device above
//		appDeviceControl.click(DeviceInfo.PUBLISH);
//		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		// 10. Refresh offline DB
//		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
//		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		// 11. Click "Download Offline Database" link
//		appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE);
//		// 12. Open the offline database file by SQLite		
//		// 13. Navigate to Asset table in SQLite
//		SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
//		// VP: Verify that Asset table has QPEQ tuning blob, which has only Eagle data 2.0
//		ArrayList<String> rs = dbUtil.getColumnData("Select DTSCSAssetId from Product where Type = 'DeviceTuning' and SubType = 'GPEQ'" , "DTSCSAssetId", null);
//		Assert.assertTrue(rs.size()>0);
//		for (int i = 0; i < rs.size(); i++) {
//			Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + rs.get(i).toString() + "'", "Data", device_UUID));
//			Assert.assertTrue(appDeviceControl.isMapEagleVersion(device_UUID, DeviceEdit.Eagle_Version.Eagle2_0.getName()));
//		}
//	} prefer ticket MPG-5927
//	@Test
//	public void TC640DADD_46(){
//		Reporter.log("Verify that when uploading CTC tuning, the offline DB will include CTC tuning blob in Asset table");
//	/*
//	 	1. Log into DTS portal as a DTS user
//		2. Navigate to "Apps or Devicesâ€� page
//		3. Click â€œAdd App & Deviceâ€� link
//		4. Fill valid value into all required fields
//		5. Leave â€œEagle versionâ€� section is â€œEagle 2.0â€�
//		6. Select â€œCTC tuningâ€� in Device Tuning combobox
//		7. Upload the valid CTC tuning blob file
//		8. Click â€œSaveâ€� link
//		9. Publish a device above
//		10. Refresh offline DB
//		11. Click "Download Offline Database" link
//		12. Open the offline databse file by SQLite
//		13. Navigate to Asset table in SQLite
//	 */
//		
//		// 1. Log into DTS portal as a DTS user
//		loginControl.login(DTS_USER, DTS_PASSWORD);
//		// 2. Navigate to "Products" page	
//		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//		// 3. Click â€œAdd App or Deviceâ€� link
//		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//		// 4. Fill valid value into all required fields
//		Hashtable<String, String> newDevice = TestData.appDevicePublishEagleV2();
//		newDevice.remove("save");
//		appDeviceControl.createNewDevice(DeviceEdit.getHash(),newDevice);
//		// 5. Leave the Eagle Version panel as â€œEagle 2.0â€�
//		appDeviceControl.selectOptionByName(DeviceEdit.EAGLE_VERSION, DeviceEdit.Eagle_Version.Eagle2_0.getName());
//		// 6. Select â€œCTC tuningâ€� from Device Tuning combobox
//		appDeviceControl.selectOptionByName(DeviceEdit.DEVICE_TUNING, DeviceEdit.Device_Tunings.CTC_Tuning.getName());
//		// 7. Upload the valid CTC tuning blob
//		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_DEVICE_TUNING, AddEditProductModel.FileUpload.Device_Tuning_CTC.getName());
//		appDeviceControl.click(DeviceEdit.NO_IMAGES);
//		// 8. Click â€œSaveâ€� link
//		appDeviceControl.click(DeviceEdit.SAVE);
//		// 9. Publish a device above
//		appDeviceControl.click(DeviceInfo.PUBLISH);
//		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//		// 10. Refresh offline DB
//		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
//		appDeviceControl.selectConfirmationDialogOption("Refresh");
//		// 11. Click "Download Offline Database" link
//		appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE);
//		// 12. Open the offline database file by SQLite		
//		// 13. Navigate to Asset table in SQLite
//		SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
//		// VP: Verify that Asset table has CTC tuning blob, which has only Eagle data 2.0
//		ArrayList<String> rs = dbUtil.getColumnData("select DTSCSAssetId from Product where Type = 'DeviceTuning' and SubType = 'CTC'" , "DTSCSAssetId", null);
//		for (int i = 0; i < rs.size(); i++) {
//			Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + rs.get(i).toString() + "'", "Data", device_UUID));
//			Assert.assertTrue(appDeviceControl.isMapEagleVersion(device_UUID, DeviceEdit.Eagle_Version.Eagle2_0.getName()));
//		appDeviceControl.doDelete(DeviceInfo.DELETE);
//		}
	}
	
	@Test
	public void TC640DADD_53(){// This testcase cannot be done due to Attach2 include in Default Audio Route.
		Reporter.log("Verify that Device Headphone tuning is displayed correctly in Audio Route section with''DTS:X Master'' option");
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click â€œAdd App or Deviceâ€� link
		4. Fill valid value into all required fields
		5. Leave the Product Type panel as ''DTS:X Master''
		6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
		7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
		8. Click on "Select file" button in "Custom Tuning" section
		9. Upload the valid custom tuning DTSCS file
		10. Click "Save" link
		 
		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
		// 4. Fill valid value into all required fields
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDevice.remove("save");
		// 5. Leave the Eagle Version panel as â€œEagle 2.0â€�
		// 6. Leave the Product Type panel as â€œDTS:X Masterâ€�
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
		// 7. Select Device Headphone in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		// 8. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
		// 9. Upload the valid custom tuning DTSCS file which have the Eagle settings data Version 2.0
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached_2_Music_Eagle2_0.getName());
		// 10. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: Verify that the custom tuning file is displayed in â€œAudio Routeâ€� section
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceInfo.CUSTOM_TUNING_CONTAINER_PUBLISHED).contains(DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName()));*/
		
		
	}
	@Test
	public void TC640DADD_54(){// This testcase cannot be done due to Attach2 include in Default Audio Route.
		Reporter.log("Verify that Device Headphone tuning is displayed correctly in Audio Route section with''DTS:X Premium'' option");
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click â€œAdd App or Deviceâ€� link
		4. Fill valid value into all required fields
		5. Leave the Product Type panel as ''DTS:X Premium''
		6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
		7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
		8. Click on "Select file" button in "Custom Tuning" section
		9. Upload the valid custom tuning DTSCS file
		10. Click "Save" link
		 
		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
		// 4. Fill valid value into all required fields
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
		// 5. Leave the Eagle Version panel as â€œEagle 2.0â€�
		// 6. Leave the Product Type panel as â€œDTS:X Premiumâ€�
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
		// 7. Select Device Headphone in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		// 8. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
		// 9. Upload the valid custom tuning DTSCS file which have the Eagle settings data Version 2.0
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached_2_Music_Eagle2_0.getName());
		// 10. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		// VP: Verify that the custom tuning file is displayed in â€œAudio Routeâ€� section
		Assert.assertTrue(appDeviceControl.getTextByXpath(DeviceInfo.CUSTOM_TUNING_CONTAINER_PUBLISHED).contains(DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName()));
	*/	
		
	}
	
	@Test
	public void TC640DADD_55(){
		Reporter.log("Verify that Product Type is ''DTS:X Master'' , when uploading Device Headphone route, the offline DB will have a new entry in the Product table with Type and SubType data correctly");
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click â€œAdd App or Deviceâ€� link
		4. Fill valid value into all required fields
		5. Leave the Product Type panel as ''DTS:X Master''
		6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
		7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
		8. Click on "Select file" button in "Custom Tuning" section
		9. Upload the valid custom tuning DTSCS file
		10. Click â€œSaveâ€� link
		11. Publish above device
		12. Refresh offline database
		13. Click "Download Offline Database" link
		14. Open the offline databse file by SQLite
		15. Navigate to Product table in SQLite
		VP: Verify that Product table display new entry with Type as â€œDeviceHeadphoneâ€� and SubType as â€œAttached2â€�
		 */
		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
		// 4. Fill valid value into all required fields
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDevice.remove("save");
		// 5. Leave the Eagle Version panel as â€œEagle 2.0â€�
		// 6. Leave the Product Type panel as â€œDTS:X Masterâ€�
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
/*		// 7. Select Device Headphone in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		// 8. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
		// 9. Upload the valid custom tuning DTSCS file which have the Eagle settings data Version 2.0
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached_2_Music_Eagle2_0.getName());*/
		// 10. Click "Save" link
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		appDeviceControl.click(DeviceEdit.SAVE);
		// 11. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 12. Refresh offline database
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		// 13. Click "Download Offline Database" link
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
		// 14. Open the offline databse file by SQLite
		// 15. Navigate to Product table in SQLite
		// VP: Verify that Product table display new entry with Type as â€œDeviceHeadphoneâ€� and SubType as â€œAttached2â€�
		Assert.assertTrue(appDeviceControl.isDBTableContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product", "Type", "SubType", "DeviceHeadphone", "Attached2"));
	}
	@Test
	public void TC640DADD_56(){
		Reporter.log("Verify that Product Type is ''DTS:X Premium'' , when uploading Device Headphone route, the offline DB will have a new entry in the Product table with Type and SubType data correctly");
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click â€œAdd App or Deviceâ€� link
		4. Fill valid value into all required fields
		5. Leave the Product Type panel as ''DTS:X Master''
		6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
		7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
		8. Click on "Select file" button in "Custom Tuning" section
		9. Upload the valid custom tuning DTSCS file
		10. Click â€œSaveâ€� link
		11. Publish above device
		12. Refresh offline database
		13. Click "Download Offline Database" link
		14. Open the offline databse file by SQLite
		15. Navigate to Product table in SQLite
		VP: Verify that Product table display new entry with Type as â€œDeviceHeadphoneâ€� and SubType as â€œAttached2â€�
		 */
		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "Products" page	
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 3. Click â€œAdd App or Deviceâ€� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
		// 4. Fill valid value into all required fields
		// 5. Leave the Eagle Version panel as â€œEagle 2.0â€�
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
		// 6. Leave the Product Type panel as â€œDTS:X Premiumâ€�
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
/*		// 7. Select Device Headphone in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		// 8. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
		// 9. Upload the valid custom tuning DTSCS file which have the Eagle settings data Version 2.0
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached_2_Music_Eagle2_0.getName());*/
		// 10. Click "Save" link
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		appDeviceControl.click(DeviceEdit.SAVE);
		// 11. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 12. Refresh offline database
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		// 13. Click "Download Offline Database" link
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
		// 14. Open the offline databse file by SQLite
		// 15. Navigate to Product table in SQLite
		// VP: Verify that Product table display new entry with Type as â€œDeviceHeadphoneâ€� and SubType as â€œAttached2â€�
		Assert.assertTrue(appDeviceControl.isDBTableContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product", "Type", "SubType", "DeviceHeadphone", "Attached2"));
	}
	
	@Test
	public void TC640DADD_57(){
		Reporter.log("Verify that when uploading Device Headphone tuning, the offline DB will contain above tuning in Asset table as Product Type ''DTS:X Master''");
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click â€œAdd App or Deviceâ€� link
		4. Fill valid value into all required fields
		5. Leave the Product Type panel as ''DTS:X Master''
		6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
		7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
		8. Click on "Select file" button in "Custom Tuning" section
		9. Upload the valid custom tuning DTSCS file
		10. Click â€œSaveâ€� link
		11. Publish above device
		12. Refresh offline database
		13. Click "Download Offline Database" link
		14. Open the offline databse file by SQLite
		15. Navigate to Asset table in SQLite
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
		// 5. Leave the Eagle Version panel as â€œEagle 2.0â€�
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
		// 6. Leave the Product Type panel as â€œDTS:X Masterâ€�
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
/*		// 7. Select Device Headphone in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		// 8. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
		// 9. Upload the valid custom tuning DTSCS file which have the Eagle settings data Version 2.0
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached_2_Music_Eagle2_0.getName());*/
		// 10. Click "Save" link
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		appDeviceControl.click(DeviceEdit.SAVE);
		// 11. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 12. Refresh offline database
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		// 13. Click "Download Offline Database" link
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
		// 14. Open the offline databse file by SQLite
		// 15. Navigate to Asset table in SQLite
		// VP: Verify that Asset table will contain Device Headphone tuning
		SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
		ArrayList<String> rs = dbUtil.getColumnData("Select DTSCSAssetId from Product where Type = 'DeviceHeadphone' and SubType = 'Attached2'" , "DTSCSAssetId", null);
		Assert.assertTrue(rs.size()>0);
		for (int i = 0; i < rs.size(); i++) {
			Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + rs.get(i).toString() + "'", "Data", device_UUID));
		}
	}
	
	@Test
	public void TC640DADD_58(){
		Reporter.log("Verify that when uploading Device Headphone tuning, the offline DB will contain above tuning in Asset table as Product Type ''DTS:X Master''");
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Apps & Devices" page
		3. Click â€œAdd App or Deviceâ€� link
		4. Fill valid value into all required fields
		5. Leave the Product Type panel as ''DTS:X Premium''
		6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
		7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
		8. Click on "Select file" button in "Custom Tuning" section
		9. Upload the valid custom tuning DTSCS file
		10. Click â€œSaveâ€� link
		11. Publish above device
		12. Refresh offline database
		13. Click "Download Offline Database" link
		14. Open the offline databse file by SQLite
		15. Navigate to Asset table in SQLite
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
		// 5. Leave the Eagle Version panel as â€œEagle 2.0â€�
		Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
		appDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
		// 6. Leave the Product Type panel as â€œDTS:X Premiumâ€�
		appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
/*		// 7. Select Device Headphone in â€œAudio Routeâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
		// 8. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
		// 9. Upload the valid custom tuning DTSCS file which have the Eagle settings data Version 2.0
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Attached_2_Music_Eagle2_0.getName());*/
		// 10. Click "Save" link
		appDeviceControl.click(DeviceEdit.NO_IMAGES);
		appDeviceControl.click(DeviceEdit.SAVE);
		// 11. Publish above device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
		// 12. Refresh offline database
		appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
		appDeviceControl.selectConfirmationDialogOption("Refresh");
		// 13. Click "Download Offline Database" link
		Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
		// 14. Open the offline databse file by SQLite
		// 15. Navigate to Asset table in SQLite
		// VP: Verify that Asset table will contain Device Headphone tuning
		SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
		ArrayList<String> rs = dbUtil.getColumnData("Select DTSCSAssetId from Product where Type = 'DeviceHeadphone' and SubType = 'Attached2'" , "DTSCSAssetId", null);
		Assert.assertTrue(rs.size()>0);
		for (int i = 0; i < rs.size(); i++) {
			Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + rs.get(i).toString() + "'", "Data", device_UUID));
		}
	}
		@Test
		public void TC640DADD_47(){
			Reporter.log("Verify that in 640D Device Detail page is displayed correctly Attached 6 , 7, 8 and not show in Audio Route option.''");
			
/*			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Fill valid value into all required fields include upload the file Attached 6 , 7, 8
			5. Click â€œSaveâ€� link
			6. click to â€œupdate infoâ€�
			7. Navigate 650D Device Edit page
			VP: Verify that Audio Route panel did not displayed ''choose select'' Attached 6 , 7 and 8 in 640D Device page"
			 
			 CANNOT DO THIS TEST CASE DUE TO 
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Apps & Devices" page
			loginControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// 4. Fill valid value into all required fields include upload the file Attached 6 , 7, 8
			Hashtable<String, String> appDevice = TestData.appDevicePublishSingleFile();
			appDevice.remove("save");
			appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
			appDeviceControl.click(DeviceEdit.NO_IMAGES);
			// Click to ''Audio Route''
			loginControl.click(DeviceEdit.AUDIO_ROUTE_TYPE);
			// Click on "Choose file" button in "Custom Tuning" section
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached6_Internal_Speakers_Mode_5.getName());
			// Upload the valid custom tuning DTSCS file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached_6_Game_2_Eagle2_0.getName());
			// Click on "Choose file" button in "Custom Tuning" section
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached7_Internal_Speakers_Mode_6.getName());
			// Upload the valid custom tuning DTSCS file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached_7_Game_3_Eagle2_0.getName());
			// Click on "Choose file" button in "Custom Tuning" section
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached8_Internal_Speakers_Mode_7.getName());
			// Upload the valid custom tuning DTSCS file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached_8_Custom_Eagle2_0.getName());
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceEdit.AUDIO_ROUTE_TYPE));
			// 5. Click "Save" link
			appDeviceControl.click(DeviceEdit.SAVE);
			// 6. Click  "Update Product Info" link
			appDeviceControl.click(DeviceInfo.EDIT);;
			// Click to ''Audio Route''
			loginControl.click(DeviceEdit.AUDIO_ROUTE_TYPE);
			// VP: Verify that Audio Route panel did not displayed ''choose select'' Attached 6 , 7 and 8 in 640D Device page"
			ArrayList<String> list = appDeviceControl.getAllComboboxOption(DeviceEdit.AUDIO_ROUTE_TYPE);
			Assert.assertFalse(ListUtil.containOnlyString(list,DeviceEdit.routes.Attached6_Internal_Speakers_Mode_5.getName()));*/
		}
		@Test
		public void TC640DADD_48(){// This test case cannot be run due to DTS Audio Processing reproduct donot have Attached 6,Attached 7, Attached 8
			Reporter.log("Verify that Additional modes (Attached 6 , 7 and 8 ) displayed on the complete profile with Product type â€œDTS Audio Processing''");
			/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Fill valid value into all required fields
			5. Leave the Product Type panel as â€œDTS Audio Processingâ€�
			6. Select Attached6- Internal Speakers (Game2) in  â€œAudio Routeâ€� dropdown list
			7. Upload the valid custom tuning DTSCS file
			8. Repeat step 6, 7 with select Attached7- Internal Speakers (Game3), Attached8- Internal Speakers (Custom)
			9. Click â€œSaveâ€� link
			10. Publish above device
			11. Download â€œComplete Profileâ€�
			VP: Verify that the Complete Profile include route type is AR_OUT_ATTACHED6,AR_OUT_ATTACHED7,AR_OUT_ATTACHED8
			 */
/*			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Apps & Devices" page
			loginControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// 4. Fill valid value into all required
			Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
			appDevice.remove("save");
			appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
			// 5. Leave the Product Type panel as â€œDTS Audio Processingâ€�
			appDevice.put("product type", "HPX Low");
			// 6. Select Attached6- Internal Speakers (Game2) in  â€œAudio Routeâ€� dropdown list
			loginControl.click(DeviceEdit.AUDIO_ROUTE_TYPE);
			// Click on "Choose file" button in "Custom Tuning" section
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached6_Internal_Speakers_Mode_5.getName());
			// Upload the valid custom tuning DTSCS file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached_6_Game_2_Eagle2_0.getName());
			// Click on "Choose file" button in "Custom Tuning" section
			// 8. Repeat step 6, 7 with select Attached7- Internal Speakers (Game3), Attached8- Internal Speakers (Custom)
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached7_Internal_Speakers_Mode_6.getName());
			// Upload the valid custom tuning DTSCS file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached_7_Game_3_Eagle2_0.getName());
			// Click on "Choose file" button in "Custom Tuning" section
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached8_Internal_Speakers_Mode_7.getName());
			// Upload the valid custom tuning DTSCS file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached_8_Custom_Eagle2_0.getName());
			// 9. Click "Save" link
			appDeviceControl.click(DeviceEdit.SAVE);
			// 10. Publish above device
			appDeviceControl.click(DeviceInfo.PUBLISH);
			// 11. Download the complete profile	
			String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
			boolean isDownloaded = appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK);
			Assert.assertTrue(isDownloaded);
			String profileName = "profile_"+dts_id+".dtscs";
			
//			 * VP: The complete profile size is less than 1MB
			 
			Assert.assertTrue(appDeviceControl.isNotExceed1MB(profileName));*/
		}
/*		@Test
		public void TC640DADD_49(){cannot do automation code due to single file version
			Reporter.log("Verify that Additional modes (Attached 6 , 7 and 8 ) displayed on the complete profile with Product type â€œDTS:X Premium");
			
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Fill valid value into all required fields
			5. Leave the Product Type panel as â€œDTS:X Premiumâ€�
			6. Select Attached6- Internal Speakers (Game2) in  â€œAudio Routeâ€� dropdown list
			7. Upload the valid custom tuning DTSCS file
			8. Repeat step 6, 7 with select Attached7- Internal Speakers (Game3), Attached8- Internal Speakers (Custom)
			9. Click â€œSaveâ€� link
			10. Publish above device
			11. Download â€œComplete Profileâ€�
			VP: Verify that the Complete Profile include route type is AR_OUT_ATTACHED6,AR_OUT_ATTACHED7,AR_OUT_ATTACHED8
			 
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Apps & Devices" page
			loginControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// 4. Fill valid value into all required
			Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
			appDevice.remove("save");
			appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
			// 5. Leave the Product Type panel as â€œDTS Audio Processingâ€�
			appDevice.put("product type", "HPX Medium");
			// 6. Select Attached6- Internal Speakers (Game2) in  â€œAudio Routeâ€� dropdown list
			loginControl.click(DeviceEdit.AUDIO_ROUTE_TYPE);
			// Click on "Choose file" button in "Custom Tuning" section
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached6_Internal_Speakers_Mode_5.getName());
			// Upload the valid custom tuning DTSCS file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached_6_Game_2_Eagle2_0.getName());
			// 8. Repeat step 6, 7 with select Attached7- Internal Speakers (Game3), Attached8- Internal Speakers (Custom)
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached7_Internal_Speakers_Mode_6.getName());
			// Upload the valid custom tuning DTSCS file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached_7_Game_3_Eagle2_0.getName());
			// Click on "Choose file" button in "Custom Tuning" section
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached8_Internal_Speakers_Mode_7.getName());
			// Upload the valid custom tuning DTSCS file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached_8_Custom_Eagle2_0.getName());
			// 9. Click "Save" link
			appDeviceControl.click(DeviceEdit.SAVE);
			// 10. Publish above device
			appDeviceControl.click(DeviceInfo.PUBLISH);
			// 11. Download the complete profile	
			String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
			boolean isDownloaded = appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK);
			Assert.assertTrue(isDownloaded);
			String profileName = "profile_"+dts_id+".dtscs";
			
			 * VP: The complete profile size is less than 1MB
			 
			Assert.assertTrue(appDeviceControl.isNotExceed1MB(profileName));
		}
		@Test
		public void TC640DADD_50(){ cannot do automation code due to single file version
			Reporter.log("Verify that Additional modes (Attached 6 , 7 and 8 ) displayed on the complete profile with Product type â€œDTS:X Master");
			
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Fill valid value into all required fields
			5. Leave the Product Type panel as â€œDTS:X Masterâ€�
			6. Select Attached6- Internal Speakers (Game2) in  â€œAudio Routeâ€� dropdown list
			7. Upload the valid custom tuning DTSCS file
			8. Repeat step 6, 7 with select Attached7- Internal Speakers (Game3), Attached8- Internal Speakers (Custom)
			9. Click â€œSaveâ€� link
			10. Publish above device
			11. Download â€œComplete Profileâ€�
			VP: Verify that the Complete Profile include route type is AR_OUT_ATTACHED6,AR_OUT_ATTACHED7,AR_OUT_ATTACHED8
			 
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Apps & Devices" page
			loginControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// 4. Fill valid value into all required
			Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
			appDevice.remove("save");
			appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
			// 5. Leave the Product Type panel as â€œDTS Audio Processingâ€�
			appDevice.put("product type", "HPX High");
			// 6. Select Attached6- Internal Speakers (Game2) in  â€œAudio Routeâ€� dropdown list
			loginControl.click(DeviceEdit.AUDIO_ROUTE_TYPE);
			// Click on "Choose file" button in "Custom Tuning" section
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached6_Internal_Speakers_Mode_5.getName());
			// Upload the valid custom tuning DTSCS file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached_6_Game_2_Eagle2_0.getName());
			// 8. Repeat step 6, 7 with select Attached7- Internal Speakers (Game3), Attached8- Internal Speakers (Custom)
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached7_Internal_Speakers_Mode_6.getName());
			// Upload the valid custom tuning DTSCS file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached_7_Game_3_Eagle2_0.getName());
			// Click on "Choose file" button in "Custom Tuning" section
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached8_Internal_Speakers_Mode_7.getName());
			// Upload the valid custom tuning DTSCS file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached_8_Custom_Eagle2_0.getName());
			// 9. Click "Save" link
			appDeviceControl.click(DeviceEdit.SAVE);
			// 10. Publish above device
			appDeviceControl.click(DeviceInfo.PUBLISH);
			// 11. Download the complete profile	
			String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
			boolean isDownloaded = appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK);
			Assert.assertTrue(isDownloaded);
			String profileName = "profile_"+dts_id+".dtscs";
			
			 * VP: The complete profile size is less than 1MB
			 
			Assert.assertTrue(appDeviceControl.isNotExceed1MB(profileName));
		}
		@Test
		public void TC640DADD_51(){ cannot do automation code due to single file version
			Reporter.log("Verify that when uploading Device Headphone tuning internal speaker audio route (Attached 6 , 7 and 8 ), the offline DB will have a new entry in the Product table with Type as â€œAttached 6 , 7 and 8â€� and SubType as â€œAttached 6 , 7 and 8");
			
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Select Internal Speakers (select Attached 6) in "Audio Route" dropdown list
			5. Click on "Choose file" button in "Custom Tuning" sectionâ€�
			6. Upload the valid custom tuning DTSCS file
			7. Repeat step 4, 5, 6 with select Attached 7 and Attached 8
			8. Fill valid value into all required fields
			9. Click â€œSaveâ€� link
			10. Publish above device
			11. Refresh offline database
			12. Click "Download Offline Database" 
			13. Open the offline database file By SQLite Link
			14. Navigate to product table in SQLite
			VP: Verify that Audio Route will contain Device Headphone tuning(Attached 6 , 7 and 8 )
			 
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Apps & Devices" page
			loginControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// 4. Select Attached6- Internal Speakers (Game2) in  â€œAudio Routeâ€� dropdown list
			loginControl.click(DeviceEdit.AUDIO_ROUTE_TYPE);
			// 5. Click on "Choose file" button in "Custom Tuning" section
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached6_Internal_Speakers_Mode_5.getName());
			// 6. Upload the valid custom tuning DTSCS file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached_6_Game_2_Eagle2_0.getName());
			// 7. Repeat step 6, 7 with select Attached7- Internal Speakers (Game3), Attached8- Internal Speakers (Custom)
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached7_Internal_Speakers_Mode_6.getName());
			// Upload the valid custom tuning DTSCS file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached_7_Game_3_Eagle2_0.getName());
			// Click on "Choose file" button in "Custom Tuning" section
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached8_Internal_Speakers_Mode_7.getName());
			// Upload the valid custom tuning DTSCS file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached_8_Custom_Eagle2_0.getName());
			// 8. Fill valid value into all required
			Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
			appDevice.remove("save");
			appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
			// 9. Click "Save" link
			appDeviceControl.click(DeviceEdit.SAVE);
			// 10. Publish above device
			appDeviceControl.click(DeviceInfo.PUBLISH);
			String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
			// 11. Refresh offline database	
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE));
			appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
			//VP: The "Refresh database?" dialog is displayed
			appDeviceControl.waitForElementClickable(DeviceInfo.POPUP_REFRESH_CONFIRM);
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.POPUP_REFRESH_CONFIRM));
			appDeviceControl.selectConfirmationDialogOption("Refresh");
			// 12. Click "Download Offline Database" link
			Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
			// 13. Open the offline database file by SQLite		
			// 14. Navigate to asset table in SQLite
			// VP: Verify that Audio Route will contain Device Headphone tuning(Attached 6 , 7 and 8 )
			Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product where Name = 'Internal Speakers (mode 5)'", "SubType", "Attached6"));
			Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product where Name = 'Internal Speakers (mode 6)'", "SubType", "Attached7"));
			Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product where Name = 'Internal Speakers (mode 7)'", "SubType", "Attached8"));
			
		}
		@Test
		public void TC640DADD_52(){ cannot do automation code due to single file version
			Reporter.log("Verify that when uploading Device Headphone tuning internal speaker audio route (Attached 6 , 7 and 8 ), the offline DB will contain above tuning in Asset table");
			
			1. Log into DTS portal as a DTS user
			2. Navigate to "Apps & Devices" page
			3. Click â€œAdd App or Deviceâ€� link
			4. Select Internal Speakers (select Attached 6) in "Audio Route" dropdown list
			5. Click on "Choose file" button in "Custom Tuning" sectionâ€�
			6. Upload the valid custom tuning DTSCS file
			7. Repeat step 4, 5, 6 with select Attached 7 and Attached 8
			8. Fill valid value into all required fields
			9. Click â€œSaveâ€� link
			10. Publish above device
			11. Refresh offline database
			12. Click "Download Offline Database" 
			13. Open the offline database file By SQLite Link
			14. Navigate to asset table in SQLite
			VP:  Verify that Attached 6-8 tuning is contained in Asset table
			 
			// 1. Log into DTS portal as a DTS user
			loginControl.login(DTS_USER, DTS_PASSWORD);
			// 2. Navigate to "Apps & Devices" page
			loginControl.click(PageHome.LINK_APP_DEVICES);
			// 3. Click â€œAdd App or Deviceâ€� link
			appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
			Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
			// 4. Select Attached6- Internal Speakers (Game2) in  â€œAudio Routeâ€� dropdown list
			loginControl.click(DeviceEdit.AUDIO_ROUTE_TYPE);
			// 5. Click on "Choose file" button in "Custom Tuning" section
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached6_Internal_Speakers_Mode_5.getName());
			// 6. Upload the valid custom tuning DTSCS file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached_6_Game_2_Eagle2_0.getName());
			// 7. Repeat step 6, 7 with select Attached7- Internal Speakers (Game3), Attached8- Internal Speakers (Custom)
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached7_Internal_Speakers_Mode_6.getName());
			// Upload the valid custom tuning DTSCS file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached_7_Game_3_Eagle2_0.getName());
			// Click on "Choose file" button in "Custom Tuning" section
			appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached8_Internal_Speakers_Mode_7.getName());
			// Upload the valid custom tuning DTSCS file
			appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached_8_Custom_Eagle2_0.getName());
			// 8. Fill valid value into all required
			Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
			appDevice.remove("save");
			appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
			// 9. Click "Save" link
			appDeviceControl.click(DeviceEdit.NO_IMAGES );
			appDeviceControl.click(DeviceEdit.SAVE);
			// 10. Publish above device
			appDeviceControl.click(DeviceInfo.PUBLISH);
			String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
			// 11. Refresh offline database	
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.REFRESH_OFFLINE_DATABASE));
			appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
			//VP: The "Refresh database?" dialog is displayed
			appDeviceControl.waitForElementClickable(DeviceInfo.POPUP_REFRESH_CONFIRM);
			Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.POPUP_REFRESH_CONFIRM));
			appDeviceControl.selectConfirmationDialogOption("Refresh");
			// 12. Click "Download Offline Database" link
			Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
			// 13. Open the offline database file by SQLite		
			// 14. Navigate to product table in SQLite
			// VP: Verify that asset in Audio Route will contain Device Headphone tuning(Attached 6 , 7 and 8 )
			SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
			ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where Name = 'Internal Speakers (mode 5)' and Type = 'Other' and SubType = 'Attached6'", "DTSCSAssetId", null);
			for (int i = 0; i < ColumnData.size(); i++) {
				Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
			}
			ArrayList<String> ColumnData_1 = sqlite.getColumnData("select DTSCSAssetId from Product where Name = 'Internal Speakers (mode 6)' and Type = 'Other' and SubType = 'Attached7'", "DTSCSAssetId", null);
			for (int i = 0; i < ColumnData_1.size(); i++) {
				Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
			}
			ArrayList<String> ColumnData_2 = sqlite.getColumnData("select DTSCSAssetId from Product where Name = 'Internal Speakers (mode 7)' and Type = 'Other' and SubType = 'Attached8'", "DTSCSAssetId", null);
			for (int i = 0; i < ColumnData_2.size(); i++) {
				Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
		}}
			
			 * Verify that complete profile with eagle version 1.4 is displayed correctly when did not upload Device Headphone tuning in Audio Route section
			 
			@Test
			public void TC640DADD_59(){cannot do automation code due to single file version
				Reporter.log("Verify that complete profile with eagle version 1.4 is displayed correctly when did not upload Device Headphone tuning in Audio Route section");
				
				
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Fill valid value into all required fields with choosing eagle version 1.4.
					5. Leave the Product Type panel as ''DTS:X Master''
					6. Click â€œSaveâ€� link
					7. Publish above device
					8. Download the complete profile
					9. Open file Complete Profile
					VP: Verify that the Complete Profile did not have route ''AR_OUT_DEVICE_ACCESSORY''
					10. Repeat test case with leave product type panel as â€œDTS:X Premium'' and ''DTS Audio Processing''

				
				

				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 1.4.
				// 5. Leave the Product Type panel as ''DTS:X Master''
				// 6. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice = TestData.appDevicePublish();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				appDeviceControl.click(DeviceEdit.SAVE);
				// 7. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 8. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 9. Open file Complete Profile
				// VP: Verify that the Complete Profile did not have route ''AR_OUT_DEVICE_ACCESSORY''
				Assert.assertFalse(appDeviceControl.checkRouteFile(DTS_ID, "AR_OUT_DEVICE_ACCESSORY"));	
				appDeviceControl.doDelete(DeviceInfo.DELETE);
				// 10. Repeat test case with leave product type panel as â€œDTS:X Premium'' and ''DTS Audio Processing''
				// 1a. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2a. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3a. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4a. Fill valid value into all required fields with choosing eagle version 1.4.
				// 5a. Leave the Product Type panel as ''DTS:X Premium''
				// 6a. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice_1 = TestData.appDevicePublish();
				appDevice_1.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice_1);
				appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
				appDeviceControl.click(DeviceEdit.SAVE);
				// 7a. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID_1 = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 8a. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 9a. Open file Complete Profile
				// VP: Verify that the Complete Profile did not have route ''AR_OUT_DEVICE_ACCESSORY''
				Assert.assertFalse(appDeviceControl.checkRouteFile(DTS_ID_1, "AR_OUT_DEVICE_ACCESSORY"));	
				appDeviceControl.doDelete(DeviceInfo.DELETE);
				// 1b. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2b. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3b. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4b. Fill valid value into all required fields with choosing eagle version 1.4.
				// 5b. Leave the Product Type panel as ''DTS Audio Processing''
				// 6b. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice_2 = TestData.appDevicePublish();
				appDevice_2.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice_2);
				appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
				appDeviceControl.click(DeviceEdit.SAVE);
				// 7b. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID_2 = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 8b. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 9b. Open file Complete Profile
				// VP: Verify that the Complete Profile did not have route ''AR_OUT_DEVICE_ACCESSORY''
				Assert.assertFalse(appDeviceControl.checkRouteFile(DTS_ID_2, "AR_OUT_DEVICE_ACCESSORY"));	
				appDeviceControl.doDelete(DeviceInfo.DELETE);

			}
			
			 * Verify that complete profile with eagle version 2.0 is displayed correctly when did not upload Device Headphone tuning in Audio Route section
			 
			@Test
			public void TC640DADD_60(){ cannot do automation code due to single file version
				Reporter.log("Verify that complete profile with eagle version 2.0 is displayed correctly when did not upload Device Headphone tuning in Audio Route section");
				
				
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Fill valid value into all required fields with choosing eagle version 2.0.
					5. Leave the Product Type panel as ''DTS:X Master''
					6. Click â€œSaveâ€� link
					7. Publish above device
					8. Download the complete profile
					9. Open file Complete Profile
					VP: Verify that the Complete Profile did not have route ''AR_OUT_DEVICE_ACCESSORY''

				
				

				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5. Leave the Product Type panel as ''DTS:X Master''
				// 6. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				appDeviceControl.click(DeviceEdit.SAVE);
				// 7. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 8. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 9. Open file Complete Profile
				// VP: Verify that the Complete Profile did not have route ''AR_OUT_DEVICE_ACCESSORY''
				Assert.assertFalse(appDeviceControl.checkRouteFile(DTS_ID, "AR_OUT_DEVICE_ACCESSORY"));	
				appDeviceControl.doDelete(DeviceInfo.DELETE);
				// 10. Repeat test case with leave product type panel as â€œDTS:X Premium'' and ''DTS Audio Processing''
				// 1a. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2a. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3a. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4a. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5a. Leave the Product Type panel as ''DTS:X Premium''
				// 6a. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice_1 = TestData.appDevicePublishEagleV2();
				appDevice_1.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice_1);
				appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
				appDeviceControl.click(DeviceEdit.SAVE);
				// 7a. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID_1 = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 8a. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 9a. Open file Complete Profile
				// VP: Verify that the Complete Profile did not have route ''AR_OUT_DEVICE_ACCESSORY''
				Assert.assertFalse(appDeviceControl.checkRouteFile(DTS_ID_1, "AR_OUT_DEVICE_ACCESSORY"));	
				appDeviceControl.doDelete(DeviceInfo.DELETE);
				// 1b. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2b. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3b. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4b. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5b. Leave the Product Type panel as ''DTS:X Audio Processing''
				// 6b. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice_2 = TestData.appDevicePublishEagleV2();
				appDevice_2.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice_2);
				appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
				appDeviceControl.click(DeviceEdit.SAVE);
				// 7b. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID_2 = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 8b. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 9b. Open file Complete Profile
				// VP: Verify that the Complete Profile did not have route ''AR_OUT_DEVICE_ACCESSORY''
				Assert.assertFalse(appDeviceControl.checkRouteFile(DTS_ID_2, "AR_OUT_DEVICE_ACCESSORY"));	
				appDeviceControl.doDelete(DeviceInfo.DELETE);
			}
			
			 * Verify that complete profile with eagle version 1.4 is displayed correctly when upload one attach2 on Device Headphone tuning in Audio Route section and product type â€œDTS:X Premium'' â€œDTS:X Master''
			 
			@Test
			public void TC640DADD_61(){ cannot do automation code due to single file version
				Reporter.log("Verify that complete profile with eagle version 1.4 is displayed correctly when upload one attach2 on Device Headphone tuning in Audio Route section and product type â€œDTS:X Premium'' â€œDTS:X Master''");
				
				
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Fill valid value into all required fields with choosing eagle version 1.4.
					5. Leave the Product Type panel as ''DTS:X Master''
					6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
					7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
					8. Upload the valid custom tuning DTSCS file
					9. Click â€œSaveâ€� link				
					10. Publish above device
					11. Download the complete profile
					12. Open file Complete Profile
					VP: Verify that complete profile will have Type as ''attach2'' with route ''AR_OUT_DEVICE_ACCESSORY''
					13. Repeat test case with leave product type panel as â€œDTS:X Premium''
					VP: Verify that complete profile will have Type as ''attach2'' with route ''AR_OUT_DEVICE_ACCESSORY''

				
				

				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 1.4.
				// 5. Leave the Product Type panel as ''DTS:X Master''
				// 6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
				// 7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
				// 8. Upload the valid custom tuning DTSCS file
				// 9. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice = TestData.appDevicePublish();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach2.getName());
				appDeviceControl.click(DeviceEdit.SAVE);
				// 10. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 11. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 12. Open file Complete Profile
				// VP: Verify that complete profile will have Type as ''attach2'' with route ''AR_OUT_DEVICE_ACCESSORY''
				Assert.assertTrue(appDeviceControl.checkRouteFile(DTS_ID, "AR_OUT_DEVICE_ACCESSORY"));	
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached2"));
				appDeviceControl.doDelete(DeviceInfo.DELETE);
				// 13. Repeat test case with leave product type panel as â€œDTS:X Premium''
				// 1a. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2a. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3a. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4a. Fill valid value into all required fields with choosing eagle version 1.4.
				// 5a. Leave the Product Type panel as ''DTS:X Premium''
				// 6a. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
				// 7a. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
				// 8a. Upload the valid custom tuning DTSCS file
				// 9a. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice_1 = TestData.appDevicePublish();
				appDevice_1.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice_1);
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach2.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
				appDeviceControl.click(DeviceEdit.SAVE);
				// 10a. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID_1 = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 11a. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 12a. Open file Complete Profile
				// VP: Verify that complete profile will have Type as ''attach2'' with route ''AR_OUT_DEVICE_ACCESSORY''
				Assert.assertTrue(appDeviceControl.checkRouteFile(DTS_ID_1, "AR_OUT_DEVICE_ACCESSORY"));	
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached2"));
				appDeviceControl.doDelete(DeviceInfo.DELETE);
			}
			
			 * Verify that complete profile with eagle version 1.4 is displayed correctly when upload one attach2,attach3,attach4,attach5,attach6,attach7,attach8, on Device Headphone tuning in Audio Route section and product type â€œDTS:X Premium'' â€œDTS:X Master''
			 
			@Test
			public void TC640DADD_62(){ cannot do automation code due to single file version
				Reporter.log("Verify that complete profile with eagle version 1.4 is displayed correctly when upload one attach2,attach3,attach4,attach5,attach6,attach7,attach8, on Device Headphone tuning in Audio Route section and product type â€œDTS:X Premium'' â€œDTS:X Master''");
				
				
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Fill valid value into all required fields with choosing eagle version 1.4.
					5. Leave the Product Type panel as ''DTS:X Master''
					6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
					7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
					8. Repeat step 6 and 7 for attach3, attach4,attach5,attach6,attach7,attach8
					9. Upload the valid custom tuning DTSCS file
					10. Click â€œSaveâ€� link				
					11. Publish above device
					12. Download the complete profile
					13. Open file Complete Profile
					VP: VP: Verify that complete profile will have Type as ''attach2'' ,''attach3'',''attach4'',''attach5'',''attach6'',''attach7'',''attach8'' with route ''AR_OUT_DEVICE_ACCESSORY'''
					14. Repeat test case with leave product type panel as â€œDTS:X Premium''
					VP: Verify that complete profile will have Type as ''attach2'' ,''attach3'',''attach4'',''attach5'',''attach6'',''attach7'',''attach8'' with route ''AR_OUT_DEVICE_ACCESSORY'''

				
				

				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 1.4.
				// 5. Leave the Product Type panel as ''DTS:X Master''
				// 6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
				// 7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
				// 8. Repeat step 6 and 7 for attach3, attach4,attach5,attach6,attach7,attach8
				// 9. Upload the valid custom tuning DTSCS file
				// 10. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice = TestData.appDevicePublish();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach2.getName());
				// Select â€œAttached 3â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_3_Movies.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach3.getName());
				// Select â€œAttached 4â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_4_Voice.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach4.getName());
				// Select â€œAttached 5â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_5_Game_1.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach5.getName());
				// Select â€œAttached 6â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_6_Game_2.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach6.getName());
				// Select â€œAttached 7â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_7_Game_3.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach7.getName());
				// Select â€œAttached 8â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_8_Custom.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach8.getName());
				appDeviceControl.click(DeviceEdit.SAVE);
				// 11. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 12. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 13. Open file Complete Profile
				// VP: Verify that complete profile will have Type as ''attach2'' with route ''AR_OUT_DEVICE_ACCESSORY''
				Assert.assertTrue(appDeviceControl.checkRouteFile(DTS_ID, "AR_OUT_DEVICE_ACCESSORY"));	
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached2"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached3"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached4"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached5"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached6"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached7"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached8"));
				appDeviceControl.doDelete(DeviceInfo.DELETE);
				// 14. Repeat test case with leave product type panel as â€œDTS:X Premium''
				// 1a. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2a. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3a. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4a. Fill valid value into all required fields with choosing eagle version 1.4.
				// 5a. Leave the Product Type panel as ''DTS:X Premium''
				// 6a. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
				// 7a. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
				// 8a. Repeat step 6 and 7 for attach3, attach4,attach5,attach6,attach7,attach8
				// 9a. Upload the valid custom tuning DTSCS file
				// 10a. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice_1 = TestData.appDevicePublish();
				appDevice_1.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice_1);
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach2.getName());
				// Select â€œAttached 3â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_3_Movies.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach3.getName());
				// Select â€œAttached 4â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_4_Voice.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach4.getName());
				// Select â€œAttached 5â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_5_Game_1.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach5.getName());
				// Select â€œAttached 6â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_6_Game_2.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach6.getName());
				// Select â€œAttached 7â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_7_Game_3.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach7.getName());
				// Select â€œAttached 8â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_8_Custom.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach8.getName());
				appDeviceControl.click(DeviceEdit.SAVE);
				// 11a. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID_1 = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 12a. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 13a. Open file Complete Profile
				// VP: Verify that complete profile will have Type as ''attach2'' ,''attach3'',''attach4'',''attach5'',''attach6'',''attach7'',''attach8'' with route ''AR_OUT_DEVICE_ACCESSORY'''
				Assert.assertTrue(appDeviceControl.checkRouteFile(DTS_ID_1, "AR_OUT_DEVICE_ACCESSORY"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID_1, "attached2"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached3"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached4"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached5"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached6"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached7"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached8"));

				appDeviceControl.doDelete(DeviceInfo.DELETE);
			}
			
			 * Verify that complete profile with eagle version 1.4 is displayed correctly when upload one attach2 on Device Headphone tuning in Audio Route section and product type ''DTS Audio Processing''
			 
			@Test
			public void TC640DADD_63(){ cannot do automation code due to single file version
				Reporter.log("Verify that complete profile with eagle version 1.4 is displayed correctly when upload one attach2 on Device Headphone tuning in Audio Route section and product type ''DTS Audio Processing''");
				
				
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Fill valid value into all required fields with choosing eagle version 1.4.
					5. Leave the Product Type panel as ''DTS Audio Processing''
					6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
					7. Select â€œmode1â€� in â€œContent Modesâ€� dropdown list
					8. Upload the valid custom tuning DTSCS file
					9. Click â€œSaveâ€� link				
					10. Publish above device
					11. Download the complete profile
					12. Open file Complete Profile
					VP: Verify that profile display new entry with Type as â€œDeviceHeadphoneâ€� and SubType as â€œmode1''

				
				

				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 1.4.
				// 5. Leave the Product Type panel as ''DTS Audio Processing''
				// 6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
				// 7. Select â€œmode1â€� in â€œContent Modesâ€� dropdown list
				// 8. Upload the valid custom tuning DTSCS file
				// 9. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice = TestData.appDevicePublish();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_1.getType());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Mode1.getName());
				appDeviceControl.click(DeviceEdit.SAVE);
				// 10. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 11. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 12. Open file Complete Profile
				// VP: Verify that profile display new entry with Type as â€œDeviceHeadphoneâ€� and SubType as â€œmode1''
				Assert.assertTrue(appDeviceControl.checkRouteFile(DTS_ID, "AR_OUT_DEVICE_ACCESSORY"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "mode1"));	
				appDeviceControl.doDelete(DeviceInfo.DELETE);
			}
			
			 * Verify that complete profile with eagle version 1.4 is displayed correctly when upload one mode1,mode2,mode3 on Device Headphone tuning in Audio Route section and product type ''DTS Audio Processing''
			 */
			@Test
			public void TC640DADD_64(){
				Reporter.log("Verify that complete profile with eagle version 1.4 is displayed correctly when upload one mode1,mode2,mode3 on Device Headphone tuning in Audio Route section and product type ''DTS Audio Processing''");
				
				/*
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Fill valid value into all required fields with choosing eagle version 1.4.
					5. Leave the Product Type panel as ''HPX Low''
					6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
					7. Select â€œmode1â€� in â€œContent Modesâ€� dropdown list
					8. Repeat step 6 and 7 for mode2 and mode3
					9. Upload the valid custom tuning DTSCS file
					10. Click â€œSaveâ€� link				
					11. Publish above device
					12. Download the complete profile
					13. Open file Complete Profile
					VP: Verify that complete profile will have Type as ''mode1'', ''mode2'', ''mode3'' with route ''AR_OUT_DEVICE_ACCESSORY''

				*/
				

				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 1.4.
				// 5. Leave the Product Type panel as ''HPX Low''
				// 6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
				// 7. Select â€œmode1â€� in â€œContent Modesâ€� dropdown list
				// 8. Repeat step 6 and 7 for mode2 and mode3
				// 9. Upload the valid custom tuning DTSCS file
				// 10. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice = TestData.appDevicePublish();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types_Eagle_1_4.HPX_Low.getType());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_1.getType());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Mode1.getName());
				// Repeat step 6 and 7 for mode2 and mode3
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_2.getType());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Mode2.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_3.getType());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Mode3.getName());
				appDeviceControl.click(DeviceEdit.NO_IMAGES);
				appDeviceControl.click(DeviceEdit.SAVE);
				// 10. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 11. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 12. Open file Complete Profile
				// VP: Verify that complete profile will have Type as ''mode1'', ''mode2'', ''mode3'' with route ''AR_OUT_DEVICE_ACCESSORY''
				Assert.assertTrue(appDeviceControl.checkRouteFile(DTS_ID, "AR_OUT_DEVICE_ACCESSORY"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached3"));	
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached2"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached4"));
				appDeviceControl.doDelete(DeviceInfo.DELETE);
}
			/*
			 * Verify that complete profile with eagle version 2.0 is displayed correctly when upload one attach2 on Device Headphone tuning in Audio Route section and product type â€œDTS:X Premium'' â€œDTS:X Master''
			 */
			@Test
			public void TC640DADD_65(){
				Reporter.log("Verify that complete profile with eagle version 2.0 is displayed correctly when upload one attach2 on Device Headphone tuning in Audio Route section and product type â€œDTS:X Premium'' â€œDTS:X Master''");
				
				/*
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Fill valid value into all required fields with choosing eagle version 2.0.
					5. Leave the Product Type panel as ''DTS:X Master''
					6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
					7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
					8. Upload the valid custom tuning DTSCS file
					9. Click â€œSaveâ€� link				
					10. Publish above device
					11. Download the complete profile
					12. Open file Complete Profile
					VP: Verify that complete profile will have Type as ''attach2'' with route ''AR_OUT_DEVICE_ACCESSORY''
					13. Repeat test case with leave product type panel as â€œDTS:X Premium''
					VP: Verify that complete profile will have Type as ''attach2'' with route ''AR_OUT_DEVICE_ACCESSORY''

				*/
				

				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 2.0
				// 5. Leave the Product Type panel as ''DTS:X Master''
				// 6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
				// 7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
				// 8. Upload the valid custom tuning DTSCS file
				// 9. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice = TestData.appDevicePublishSingleFilePremium();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
/*				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach2_Eagle2_0.getName());
*/				appDeviceControl.click(DeviceEdit.SAVE);
				// 10. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 11. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 12. Open file Complete Profile
				// VP: Verify that complete profile will have Type as ''attach2'' with route ''AR_OUT_DEVICE_ACCESSORY''
				Assert.assertTrue(appDeviceControl.checkRouteFile(DTS_ID, "AR_OUT_DEVICE_ACCESSORY"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached2"));
				appDeviceControl.doDelete(DeviceInfo.DELETE);
				// 13. Repeat test case with leave product type panel as â€œDTS:X Premium''
				// 1a. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2a. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3a. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4a. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5a. Leave the Product Type panel as ''DTS:X Premium''
				// 6a. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
				// 7a. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
				// 8a. Upload the valid custom tuning DTSCS file
				// 9a. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice_1 = TestData.appDevicePublishEagleV2();
				appDevice_1.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice_1);
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach2_Eagle2_0.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
				appDeviceControl.click(DeviceEdit.SAVE);
				// 10a. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID_1 = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 11a. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 12a. Open file Complete Profile
				// VP: Verify that complete profile will have Type as ''attach2'' with route ''AR_OUT_DEVICE_ACCESSORY''
				Assert.assertTrue(appDeviceControl.checkRouteFile(DTS_ID_1, "AR_OUT_DEVICE_ACCESSORY"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID_1, "attached2"));
				appDeviceControl.doDelete(DeviceInfo.DELETE);
			}
			/*
			 * Verify that complete profile with eagle version 2.0 is displayed correctly when upload one attach2,attach3,attach4,attach5,attach6,attach7,attach8, on Device Headphone tuning in Audio Route section and product type â€œDTS:X Premium'' â€œDTS:X Master''
			 */
			@Test
			public void TC640DADD_66(){
				Reporter.log("Verify that complete profile with eagle version 2.0 is displayed correctly when upload one attach2,attach3,attach4,attach5,attach6,attach7,attach8, on Device Headphone tuning in Audio Route section and product type â€œDTS:X Premium'' â€œDTS:X Master''");
				
				/*
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Fill valid value into all required fields with choosing eagle version 2.0.
					5. Leave the Product Type panel as ''DTS:X Master''
					6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
					7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
					8. Repeat step 6 and 7 for attach3, attach4,attach5,attach6,attach7,attach8
					9. Upload the valid custom tuning DTSCS file
					10. Click â€œSaveâ€� link				
					11. Publish above device
					12. Download the complete profile
					13. Open file Complete Profile
					VP: Verify that complete profile will have Type as ''attach2'' with route ''AR_OUT_DEVICE_ACCESSORY''
					14. Repeat test case with leave product type panel as â€œDTS:X Premium''
					VP: Verify that complete profile will have Type as ''attach2'' with route ''AR_OUT_DEVICE_ACCESSORY''

				*/
				

				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5. Leave the Product Type panel as ''DTS:X Master''
				// 6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
				// 7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
				// 8. Repeat step 6 and 7 for attach3, attach4,attach5,attach6,attach7,attach8
				// 9. Upload the valid custom tuning DTSCS file
				// 10. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				// Attached0 to Attached 8 already have in Default Audio route
/*				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach2_Eagle2_0.getName());
				// Select â€œAttached 3â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_3_Movies.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach3_Eagle2_0.getName());
				// Select â€œAttached 4â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_4_Voice.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach4_Eagle2_0.getName());
				// Select â€œAttached 5â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_5_Game_1.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach5_Eagle2_0.getName());
				// Select â€œAttached 6â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_6_Game_2.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach6_Eagle2_0.getName());
				// Select â€œAttached 7â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_7_Game_3.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach7_Eagle2_0.getName());
				// Select â€œAttached 8â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_8_Custom.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach8_Eagle2_0.getName());*/
				appDeviceControl.click(DeviceEdit.NO_IMAGES);
				appDeviceControl.click(DeviceEdit.SAVE);
				// 11. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 12. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 13. Open file Complete Profile
				// VP: Verify that complete profile will have Type as ''attach2'' ,''attach3'',''attach4'',''attach5'',''attach6'',''attach7'',''attach8'', ''attach0'' with route ''AR_OUT_DEVICE_ACCESSORY'''
				Assert.assertTrue(appDeviceControl.checkRouteFile(DTS_ID, "AR_OUT_DEVICE_ACCESSORY"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached2"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached3"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached4"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached5"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached6"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached7"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached8"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached0"));
				appDeviceControl.doDelete(DeviceInfo.DELETE);
				// 14. Repeat test case with leave product type panel as â€œDTS:X Premium''
				// 1a. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2a. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3a. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4a. Fill valid value into all required fields with choosing eagle version 1.4.
				// 5a. Leave the Product Type panel as ''DTS:X Premium''
				// 6a. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
				// 7a. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
				// 8a. Repeat step 6 and 7 for attach3, attach4,attach5,attach6,attach7,attach8
				// 9a. Upload the valid custom tuning DTSCS file
				// 10a. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice_1 = TestData.appDevicePublishSingleFilePremium();
				appDevice_1.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice_1);
				// Attached0 to Attached 4 already have in Default Audio route
/*				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach2_Eagle2_0.getName());
				// Select â€œAttached 3â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_3_Movies.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach3_Eagle2_0.getName());
				// Select â€œAttached 4â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_4_Voice.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach4_Eagle2_0.getName());
				// Select â€œAttached 5â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_5_Game_1.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach5_Eagle2_0.getName());
				// Select â€œAttached 6â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_6_Game_2.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach6_Eagle2_0.getName());
				// Select â€œAttached 7â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_7_Game_3.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach7_Eagle2_0.getName());
				// Select â€œAttached 8â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_8_Custom.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach8_Eagle2_0.getName());*/
				appDeviceControl.click(DeviceEdit.NO_IMAGES);
				appDeviceControl.click(DeviceEdit.SAVE);
				// 11a. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID_1 = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 12a. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 13a. Open file Complete Profile
				// VP: Verify that complete profile will have Type as ''attach2'' ,''attach3'',''attach4'',''attach0' with route ''AR_OUT_DEVICE_ACCESSORY'''
				Assert.assertTrue(appDeviceControl.checkRouteFile(DTS_ID_1, "AR_OUT_DEVICE_ACCESSORY"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached2"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached3"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached4"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached0"));	
				appDeviceControl.doDelete(DeviceInfo.DELETE);
			}
			/*
			 * Verify that complete profile with eagle version 2.0 is displayed correctly when upload one mode1 on Device Headphone tuning in Audio Route section and product type ''DTS Audio Processing''
			 */
			@Test
			public void TC640DADD_67(){// testcase TC640DADD_68 already cover this testcase
				Reporter.log("Verify that complete profile with eagle version 2.0 is displayed correctly when upload one mode1 on Device Headphone tuning in Audio Route section and product type ''DTS Audio Processing''");
				
				/*
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Fill valid value into all required fields with choosing eagle version 2.0.
					5. Leave the Product Type panel as ''DTS Audio Processing''
					6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
					7. Select â€œmode1â€� in â€œContent Modesâ€� dropdown list
					8. Upload the valid custom tuning DTSCS file
					9. Click â€œSaveâ€� link				
					10. Publish above device
					11. Download the complete profile
					12. Open file Complete Profile
					VP: Verify that profile display new entry with Type as â€œDeviceHeadphoneâ€� and SubType as â€œmode1''

				*/
				

/*				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5. Leave the Product Type panel as ''DTS Audio Processing''
				// 6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
				// 7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
				// 8. Upload the valid custom tuning DTSCS file
				// 9. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				// Device headphone Attached2 to Attached 4 already have in Default Audio route
				appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_1.getType());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Mode1_Eagle2_0.getName());
				appDeviceControl.click(DeviceEdit.NO_IMAGES);
				appDeviceControl.click(DeviceEdit.SAVE);
				// 10. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 11. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 12. Open file Complete Profile
				// VP: Verify that profile display new entry with Type as â€œDeviceHeadphoneâ€� and SubType as â€œmode1''
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "mode1"));	
				Assert.assertTrue(appDeviceControl.checkRouteFile(DTS_ID, "AR_OUT_DEVICE_ACCESSORY"));	
//				Assert.assertTrue(appDeviceControl.isMapEagleVersion(DTS_ID, DeviceEdit.Eagle_Version.Eagle1_4.getName()));
				appDeviceControl.doDelete(DeviceInfo.DELETE);*/
			}
			/*
			 * Verify that complete profile with eagle version 2.0 is displayed correctly when upload all mode1,mode2,mode3 on Device Headphone tuning in Audio Route section and product type ''DTS Audio Processing''
			 */
			@Test
			public void TC640DADD_68(){
				Reporter.log("Verify that complete profile with eagle version 2.0 is displayed correctly when upload all mode1,mode2,mode3 on Device Headphone tuning in Audio Route section and product type ''DTS Audio Processing''");
				
				/*
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Fill valid value into all required fields with choosing eagle version 2.0.
					5. Leave the Product Type panel as ''DTS Audio Processing''
					6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
					7. Select â€œmode1â€� in â€œContent Modesâ€� dropdown list
					8. Repeat step 6 and 7 for mode2 and mode3
					9. Upload the valid custom tuning DTSCS file
					10. Click â€œSaveâ€� link				
					11. Publish above device
					12. Download the complete profile
					13. Open file Complete Profile
					VP: Verify that complete profile will have Type as ''mode1'', ''mode2'', ''mode3'' with route ''AR_OUT_DEVICE_ACCESSORY''

				*/
				

				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5. Leave the Product Type panel as ''DTS Audio Processing''
				// 6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
				// 7. Select â€œmode1â€� in â€œContent Modesâ€� dropdown list
				// 8. Repeat step 6 and 7 for mode2 and mode3
				// 9. Upload the valid custom tuning DTSCS file
				// 10. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
				// Device headphone Attached2 to Attached 4 already have in Default Audio route
/*				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_1.getType());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Mode1_Eagle2_0.getName());
				// Repeat step 6 and 7 for mode2 and mode3
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_2.getType());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Mode2_Eagle2_0.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_3.getType());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Mode3_Eagle2_0.getName());*/
				appDeviceControl.click(DeviceEdit.NO_IMAGES);
				appDeviceControl.click(DeviceEdit.SAVE);
				// 10. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 11. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 12. Open file Complete Profile
				// VP: Verify that complete profile will have Type as ''mode1'', ''mode2'', ''mode3'' with route ''AR_OUT_DEVICE_ACCESSORY''
				Assert.assertTrue(appDeviceControl.checkRouteFile(DTS_ID, "AR_OUT_DEVICE_ACCESSORY"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached2"));	
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached3"));
				Assert.assertTrue(appDeviceControl.checkTypeFile(DTS_ID, "attached4"));
				appDeviceControl.doDelete(DeviceInfo.DELETE);
}
			/*
			 * Verify that complete profile with eagle version 2.0 is displayed correctly when remove one attach on Device Headphone tuning in Audio Route section and product type â€œDTS:X Premium'' â€œDTS:X Master''
			 */
			@Test
			public void TC640DADD_69(){// Cannot do this testcase due to device Headphone attach add in Default audio route.
				Reporter.log("Verify that complete profile with eagle version 2.0 is displayed correctly when remove one attach on Device Headphone tuning in Audio Route section and product type â€œDTS:X Premium'' â€œDTS:X Master''");
				
				/*
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Fill valid value into all required fields with choosing eagle version 2.0.
					5. Leave the Product Type panel as ''DTS:X Master''
					6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
					7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
					8. Repeat step 6 and 7 for attach3, attach4,attach5,attach6,attach7,attach8
					9. Upload the valid custom tuning DTSCS file
					10. Click â€œSaveâ€� link				
					11. Publish above device
					12. Click to ''Update Info''
					13. Remove ''Attached2'' on Audio route
					14. Click "Save" link
					15. Click "Publish" link
					16. Download the complete profile
					17. Open file Complete Profile
					VP: Verify that complete profile NOT have Type as ''attach2''
					18. Repeat test case with leave product type panel as â€œDTS:X Premium''
					VP: Verify that complete profile NOT have Type as ''attach2''

				*/
				

/*				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5. Leave the Product Type panel as ''DTS:X Master''
				// 6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
				// 7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
				// 8. Repeat step 6 and 7 for attach3, attach4,attach5,attach6,attach7,attach8
				// 9. Upload the valid custom tuning DTSCS file
				// 10. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach2_Eagle2_0.getName());
				// Select â€œAttached 3â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_3_Movies.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach3_Eagle2_0.getName());
				// Select â€œAttached 4â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_4_Voice.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach4_Eagle2_0.getName());
				// Select â€œAttached 5â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_5_Game_1.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach5_Eagle2_0.getName());
				// Select â€œAttached 6â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_6_Game_2.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach6_Eagle2_0.getName());
				// Select â€œAttached 7â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_7_Game_3.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach7_Eagle2_0.getName());
				// Select â€œAttached 8â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_8_Custom.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach8_Eagle2_0.getName());
				appDeviceControl.click(DeviceEdit.SAVE);
				// 11. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				// 12. Click to ''Update Info''
				appDeviceControl.click(DeviceInfo.CREATE_NEW_VERSION);
				// appDeviceControl.getTextByXpath(DeviceEdit.SWITCH_PRODUCT_TYPE).contains(DeviceEdit.requires.Switch_Product_Type.getName());
				appDeviceControl.selectConfirmationDialogOption("OK");
				// 13. Remove ''Attached2'' on Audio route
				appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Attach2_Eagle2_0.getName().replaceAll(".dtscs", ""));
				appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				// 14. Click "Save" link
				appDeviceControl.click(DeviceEdit.SAVE);
				// 15. Click "Publish" link
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 16. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 17. Open file Complete Profile
				// VP: Verify that complete profile NOT have Type as ''attach2''
				Assert.assertFalse(appDeviceControl.checkTypeFile(DTS_ID, "attached2"));	
				appDeviceControl.doDelete(DeviceInfo.DELETE);
				// 18. Repeat test case with leave product type panel as â€œDTS:X Premium''
				// 1a. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2a. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3a. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4a. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5a. Leave the Product Type panel as ''DTS:X Master''
				// 6a. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
				// 7a. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
				// 8a. Repeat step 6 and 7 for attach3, attach4,attach5,attach6,attach7,attach8
				// 9a. Upload the valid custom tuning DTSCS file
				// 10a. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice_1 = TestData.appDevicePublishEagleV2();
				appDevice_1.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice_1);
				appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach2_Eagle2_0.getName());
				// Select â€œAttached 3â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_3_Movies.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach3_Eagle2_0.getName());
				// Select â€œAttached 4â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_4_Voice.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach4_Eagle2_0.getName());
				// Select â€œAttached 5â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_5_Game_1.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach5_Eagle2_0.getName());
				// Select â€œAttached 6â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_6_Game_2.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach6_Eagle2_0.getName());
				// Select â€œAttached 7â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_7_Game_3.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach7_Eagle2_0.getName());
				// Select â€œAttached 8â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_8_Custom.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach8_Eagle2_0.getName());
				appDeviceControl.click(DeviceEdit.SAVE);
				// 11a. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				// 12a. Click to ''Update Info''
				appDeviceControl.click(DeviceInfo.CREATE_NEW_VERSION);
				// appDeviceControl.getTextByXpath(DeviceEdit.SWITCH_PRODUCT_TYPE).contains(DeviceEdit.requires.Switch_Product_Type.getName());
				appDeviceControl.selectConfirmationDialogOption("OK");
				// 13a. Remove ''Attached2'' on Audio route
				appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Attach2_Eagle2_0.getName().replaceAll(".dtscs", ""));
				appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				// 14a. Click "Save" link
				appDeviceControl.click(DeviceEdit.SAVE);
				// 15a. Click "Publish" link
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID_1 = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 16a. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 17a. Open file Complete Profile
				// VP: Verify that complete profile NOT have Type as ''attach2''
				Assert.assertFalse(appDeviceControl.checkTypeFile(DTS_ID_1, "attached2"));	
				appDeviceControl.doDelete(DeviceInfo.DELETE);*/
			}
			/*
			 * Verify that complete profile with eagle version 2.0 is displayed correctly when remove one mode on Device Headphone tuning in Audio Route section and product type ''DTS Audio Processing''
			 */
			@Test
			public void TC640DADD_70(){// This testcase cannot be run due to complete profife on DTS Audio Processing donot have device headphones.
				Reporter.log("Verify that complete profile with eagle version 2.0 is displayed correctly when remove one mode on Device Headphone tuning in Audio Route section and product type ''DTS Audio Processing''");
				
				/*
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Fill valid value into all required fields with choosing eagle version 2.0.
					5. Leave the Product Type panel as ''DTS Audio Processing''
					6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
					7. Select â€œmodeâ€� in â€œContent Modesâ€� dropdown list
					8. Repeat step 6 and 7 for 'mode2'' and ''mode3''
					9. Upload the valid custom tuning DTSCS file
					10. Click â€œSaveâ€� link				
					11. Publish above device
					12. Click to ''Update Info''
					13. Remove ''mode1'' on Audio route
					14. Click "Save" link
					15. Click "Publish" link
					16. Download the complete profile
					17. Open file Complete Profile
					VP: Verify that complete profile NOT have Type as ''mode1''

				
				
				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5. Leave the Product Type panel as ''DTS Audio Processing''
				// 6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
				// 7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
				// 8. Repeat step 6 and 7 for attach3, attach4,attach5,attach6,attach7,attach8
				// 9. Upload the valid custom tuning DTSCS file
				// 10. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_1.getType());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Mode1_Eagle2_0.getName());
				// Select â€œmode2â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_2.getType());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Mode2_Eagle2_0.getName());
				// Select â€œmode3â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_3.getType());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Mode3_Eagle2_0.getName());
				appDeviceControl.click(DeviceEdit.SAVE);
				// 11. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				// 12. Click to ''Update Info''
				appDeviceControl.click(DeviceInfo.CREATE_NEW_VERSION);
				// appDeviceControl.getTextByXpath(DeviceEdit.SWITCH_PRODUCT_TYPE).contains(DeviceEdit.requires.Switch_Product_Type.getName());
				appDeviceControl.selectConfirmationDialogOption("OK");
				// 13. Remove ''mode1'' on Audio route
				appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Mode1_Eagle2_0.getName().replaceAll(".dtscs", ""));
				appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				// 14. Click "Save" link
				appDeviceControl.click(DeviceEdit.SAVE);
				// 15. Click "Publish" link
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 16. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 17. Open file Complete Profile
				// VP: Verify that complete profile NOT have Type as ''mode1''
				Assert.assertFalse(appDeviceControl.checkTypeFile(DTS_ID, "mode1"));	
				appDeviceControl.doDelete(DeviceInfo.DELETE);*/
			}
			/*
			 * Verify that complete profile with eagle version 2.0 is displayed correctly when remove all attach on Device Headphone tuning in Audio Route section and product type â€œDTS:X Premium'' â€œDTS:X Master'
			 */
			@Test
			public void TC640DADD_71() throws InterruptedException{// This testcase cannot be run due to complete profife on DTS Audio Processing donot have device headphones.
				Reporter.log("Verify that complete profile with eagle version 2.0 is displayed correctly when remove all attach on Device Headphone tuning in Audio Route section and product type â€œDTS:X Premium'' â€œDTS:X Master'");
				
				/*
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Fill valid value into all required fields with choosing eagle version 2.0.
					5. Leave the Product Type panel as ''DTS:X Master''
					6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
					7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
					8. Repeat step 6 and 7 for attach3, attach4,attach5,attach6,attach7,attach8
					9. Upload the valid custom tuning DTSCS file
					10. Click â€œSaveâ€� link				
					11. Publish above device
					12. Click to ''Update Info''
					13. Remove all ''attached'' on Audio route
					14. Click "Save" link
					15. Click "Publish" link
					16. Download the complete profile
					17. Open file Complete Profile
					VP: Verify that complete profile NOT have Type as ''attach2',''attach3',''attach4',''attach5',''attach6',''attach7',''attach8'
					18. Repeat test case with leave product type panel as â€œDTS:X Premium''
					VP: Verify that complete profile NOT have Type as ''attach2',''attach3',''attach4',''attach5',''attach6',''attach7',''attach8'

				*/
				

/*				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5. Leave the Product Type panel as ''DTS:X Master''
				// 6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
				// 7. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
				// 8. Repeat step 6 and 7 for attach3, attach4,attach5,attach6,attach7,attach8
				// 9. Upload the valid custom tuning DTSCS file
				// 10. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach2_Eagle2_0.getName());
				// Select â€œAttached 3â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_3_Movies.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach3_Eagle2_0.getName());
				// Select â€œAttached 4â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_4_Voice.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach4_Eagle2_0.getName());
				// Select â€œAttached 5â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_5_Game_1.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach5_Eagle2_0.getName());
				// Select â€œAttached 6â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_6_Game_2.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach6_Eagle2_0.getName());
				// Select â€œAttached 7â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_7_Game_3.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach7_Eagle2_0.getName());
				// Select â€œAttached 8â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_8_Custom.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach8_Eagle2_0.getName());
				appDeviceControl.click(DeviceEdit.NO_IMAGES);
				appDeviceControl.click(DeviceEdit.SAVE);
				// 11. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				// 12. Click to ''Update Info''
				appDeviceControl.click(DeviceInfo.CREATE_NEW_VERSION);
				// appDeviceControl.getTextByXpath(DeviceEdit.SWITCH_PRODUCT_TYPE).contains(DeviceEdit.requires.Switch_Product_Type.getName());
				appDeviceControl.selectConfirmationDialogOption("OK");
				// 13. Remove ''Attached2'' on Audio route
				appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Attach2_Eagle2_0.getName().replaceAll(".dtscs", ""));
				appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				Thread.sleep(2000);
				// Remove ''Attached3'' on Audio route
				appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Attach3_Eagle2_0.getName().replaceAll(".dtscs", ""));
				appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				Thread.sleep(2000);
				// Remove ''Attached4'' on Audio route
				appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Attach4_Eagle2_0.getName().replaceAll(".dtscs", ""));
				appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				Thread.sleep(2000);
				// Remove ''Attached5'' on Audio route
				appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Attach5_Eagle2_0.getName().replaceAll(".dtscs", ""));
				appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				Thread.sleep(2000);
				// Remove ''Attached6'' on Audio route
				appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Attach6_Eagle2_0.getName().replaceAll(".dtscs", ""));
				appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				Thread.sleep(2000);
				// Remove ''Attached7'' on Audio route
				appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Attach7_Eagle2_0.getName().replaceAll(".dtscs", ""));
				appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				Thread.sleep(2000);
				// Remove ''Attached8'' on Audio route
				appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Attach8_Eagle2_0.getName().replaceAll(".dtscs", ""));
				appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				Thread.sleep(2000);
				// 14. Click "Save" link
				appDeviceControl.click(DeviceEdit.SAVE);
				// 15. Click "Publish" link
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 16. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 17. Open file Complete Profile
				// VP: Verify that complete profile NOT have Type as ''attach2',''attach3',''attach4',''attach5',''attach6',''attach7',''attach8'
				Assert.assertFalse(appDeviceControl.checkTypeFile(DTS_ID, "attached2"));
				Assert.assertFalse(appDeviceControl.checkTypeFile(DTS_ID, "attached3"));
				Assert.assertFalse(appDeviceControl.checkTypeFile(DTS_ID, "attached4"));
				Assert.assertFalse(appDeviceControl.checkTypeFile(DTS_ID, "attached5"));
				Assert.assertFalse(appDeviceControl.checkTypeFile(DTS_ID, "attached6"));
				Assert.assertFalse(appDeviceControl.checkTypeFile(DTS_ID, "attached7"));
				Assert.assertFalse(appDeviceControl.checkTypeFile(DTS_ID, "attached8"));	
				appDeviceControl.doDelete(DeviceInfo.DELETE);
				// 18. Repeat test case with leave product type panel as â€œDTS:X Premium''
				// 1a. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2a. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3a. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4a. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5a. Leave the Product Type panel as ''DTS:X Master''
				// 6a. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
				// 7a. Select â€œAttached 2â€� in â€œContent Modesâ€� dropdown list
				// 8a. Repeat step 6 and 7 for attach3, attach4,attach5,attach6,attach7,attach8
				// 9a. Upload the valid custom tuning DTSCS file
				// 10a. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice_1 = TestData.appDevicePublishEagleV2();
				appDevice_1.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice_1);
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_2_Music.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach2_Eagle2_0.getName());
				// Select â€œAttached 3â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_3_Movies.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach3_Eagle2_0.getName());
				// Select â€œAttached 4â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_4_Voice.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach4_Eagle2_0.getName());
				// Select â€œAttached 5â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_5_Game_1.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach5_Eagle2_0.getName());
				// Select â€œAttached 6â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_6_Game_2.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach6_Eagle2_0.getName());
				// Select â€œAttached 7â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_7_Game_3.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach7_Eagle2_0.getName());
				// Select â€œAttached 8â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_8_Custom.getName());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach8_Eagle2_0.getName());
				appDeviceControl.click(DeviceEdit.NO_IMAGES);
				appDeviceControl.click(DeviceEdit.SAVE);
				// 11a. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				// 12a. Click to ''Update Info''
				appDeviceControl.click(DeviceInfo.CREATE_NEW_VERSION);
				// appDeviceControl.getTextByXpath(DeviceEdit.SWITCH_PRODUCT_TYPE).contains(DeviceEdit.requires.Switch_Product_Type.getName());
				appDeviceControl.selectConfirmationDialogOption("OK");
				// 13a. Remove ''Attached2'' on Audio route
				appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Attach2_Eagle2_0.getName().replaceAll(".dtscs", ""));
				appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				Thread.sleep(2000);
				// Remove ''Attached3'' on Audio route
				appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Attach3_Eagle2_0.getName().replaceAll(".dtscs", ""));
				appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				Thread.sleep(2000);
				// Remove ''Attached4'' on Audio route
				appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Attach4_Eagle2_0.getName().replaceAll(".dtscs", ""));
				appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				Thread.sleep(2000);
				// Remove ''Attached5'' on Audio route
				appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Attach5_Eagle2_0.getName().replaceAll(".dtscs", ""));
				appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				Thread.sleep(2000);
				// Remove ''Attached6'' on Audio route
				appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Attach6_Eagle2_0.getName().replaceAll(".dtscs", ""));
				appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				Thread.sleep(2000);
				// Remove ''Attached7'' on Audio route
				appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Attach7_Eagle2_0.getName().replaceAll(".dtscs", ""));
				appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				Thread.sleep(2000);
				// Remove ''Attached8'' on Audio route
				appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Attach8_Eagle2_0.getName().replaceAll(".dtscs", ""));
				appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				Thread.sleep(2000);
				// 14a. Click "Save" link
				appDeviceControl.click(DeviceEdit.SAVE);
				// 15a. Click "Publish" link
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID_1 = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 16a. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 17a. Open file Complete Profile
				// VP: Verify that complete profile NOT have Type as ''attach2',''attach3',''attach4',''attach5',''attach6',''attach7',''attach8'
				Assert.assertFalse(appDeviceControl.checkTypeFile(DTS_ID_1, "attached2"));
				Assert.assertFalse(appDeviceControl.checkTypeFile(DTS_ID_1, "attached3"));
				Assert.assertFalse(appDeviceControl.checkTypeFile(DTS_ID_1, "attached4"));
				Assert.assertFalse(appDeviceControl.checkTypeFile(DTS_ID_1, "attached5"));
				Assert.assertFalse(appDeviceControl.checkTypeFile(DTS_ID_1, "attached6"));
				Assert.assertFalse(appDeviceControl.checkTypeFile(DTS_ID_1, "attached7"));
				Assert.assertFalse(appDeviceControl.checkTypeFile(DTS_ID_1, "attached8"));	
				appDeviceControl.doDelete(DeviceInfo.DELETE);*/
			}
			/*
			 * Verify that complete profile with eagle version 2.0 is displayed correctly when remove all mode on Device Headphone tuning in Audio Route section and product type ''DTS Audio Processing''
			 */
			@Test
			public void TC640DADD_72(){// This testcase cannot be run due to complete profife on DTS Audio Processing donot have device headphones.
				Reporter.log("Verify that complete profile with eagle version 2.0 is displayed correctly when remove all mode on Device Headphone tuning in Audio Route section and product type ''DTS Audio Processing''");
				
				/*
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Fill valid value into all required fields with choosing eagle version 2.0.
					5. Leave the Product Type panel as ''DTS Audio Processing''
					6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
					7. Select â€œmodeâ€� in â€œContent Modesâ€� dropdown list
					8. Repeat step 6 and 7 for 'mode2'' and ''mode3''
					9. Upload the valid custom tuning DTSCS file
					10. Click â€œSaveâ€� link				
					11. Publish above device
					12. Click to ''Update Info''
					13. Remove all ''mode'' on Audio route
					14. Click "Save" link
					15. Click "Publish" link
					16. Download the complete profile
					17. Open file Complete Profile
					VP: Verify that complete profile NOT have Type as ''mode1' , ''mode2'', mode3''

				*/
				
			/*	// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5. Leave the Product Type panel as ''DTS:X Master''
				// 6. Select Device Headphone route in â€œAudio Routeâ€� dropdown list
				// 7. Select â€œmode1â€� in â€œContent Modesâ€� dropdown list
				// 8. Repeat step 6 and 7 for mode2 , mode3
				// 9. Upload the valid custom tuning DTSCS file
				// 10. Click â€œSaveâ€� link
				Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_1.getType());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Mode1_Eagle2_0.getName());
				// Select â€œmode2â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_2.getType());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Mode2_Eagle2_0.getName());
				// Select â€œmode3â€�
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_3.getType());
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Mode3_Eagle2_0.getName());
				appDeviceControl.click(DeviceEdit.SAVE);
				// 11. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				// 12. Click to ''Update Info''
				appDeviceControl.click(DeviceInfo.CREATE_NEW_VERSION);
				// appDeviceControl.getTextByXpath(DeviceEdit.SWITCH_PRODUCT_TYPE).contains(DeviceEdit.requires.Switch_Product_Type.getName());
				appDeviceControl.selectConfirmationDialogOption("OK");
				// 13. Remove ''mode1'' on Audio route
				appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Mode1_Eagle2_0.getName().replaceAll(".dtscs", ""));
				appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				// Remove ''mode2'' on Audio route
				appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Mode2_Eagle2_0.getName().replaceAll(".dtscs", ""));
				appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				// Remove ''mode3'' on Audio route
				appDeviceControl.getAtributeValue(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME, "href").contains(AddEditProductModel.FileUpload.Device_Headphone_Mode3_Eagle2_0.getName().replaceAll(".dtscs", ""));
				appDeviceControl.isElementPresent(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				appDeviceControl.doDelete(DeviceEdit.DELETE_CUSTOM_TUNNING_ICON);
				// 14. Click "Save" link
				appDeviceControl.click(DeviceEdit.SAVE);
				// 15. Click "Publish" link
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String DTS_ID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 16. Download the complete profile
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
				// 17. Open file Complete Profile
				// VP: Verify that complete profile NOT have Type as ''mode1' , ''mode2'', mode3''
				Assert.assertFalse(appDeviceControl.checkTypeFile(DTS_ID, "mode1"));	
				Assert.assertFalse(appDeviceControl.checkTypeFile(DTS_ID, "mode2"));
				Assert.assertFalse(appDeviceControl.checkTypeFile(DTS_ID, "mode3"));
				appDeviceControl.doDelete(DeviceInfo.DELETE);*/
			}
			/*
			 * Verify that the DTS:X Ultra will displayed when ''save'' and public profile
			 */
			@Test
			public void TC640DADD_73(){
				Reporter.log("Verify that the DTS:X Ultra will displayed when ''save'' and public profile");
				
				/*
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Leave on Product Type combo box as ''DTS:X Ultra''
					5. Fill valid value into all required fields
					6. Click "Save" link
					VP: Verify Multi channel room and Headphone:X Licensing NOT displayed
						Product type name: DTS:X Ultra
					7. Click "Publish" link
					VP: Verify Multi channel room and Headphone:X Licensing NOT displayed
						Product type name: DTS:X Ultra


				*/
				
				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5. Leave the Product Type panel as ''DTS:X Ultra''
				Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				// 6. Click "Save" link
				appDeviceControl.click(DeviceEdit.SAVE);
				//	VP: Verify Multi channel room and Headphone:X Licensing NOT displayed
				Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.HEADPHONE_X));
				Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.MULTI_CHANNEL));
				//Product type name: DTS:X Ultra
				Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.PRODUCT_INFORMATION));
				// 11. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				//	VP: Verify Multi channel room and Headphone:X Licensing NOT displayed
				Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.HEADPHONE_X));
				Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.MULTI_CHANNEL));
				//	Product type name: DTS:X Ultra
				Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.PRODUCT_INFORMATION));
				appDeviceControl.doDelete(DeviceInfo.DELETE);
			}
			/*
			 * Verify that ''DeviceMultiChannelRoomModel'' table show up null value on offline DB
			 */
			@Test
			public void TC640DADD_74(){
				Reporter.log("Verify that ''DeviceMultiChannelRoomModel'' table show up null value on offline DB");
				
				/*
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Leave on Product Type combo box as ''DTS:X Ultra''
					5. Fill valid value into all required fields
					6. Click "Save" link
					7. Click "Publish" link
					8.Click "Refresh offline database" button
					9. Click "Download offline database" button
					VP: Offline Database is downloaded successfully
					10.Open the offline databse file by SQLite
					11.Navigate to ''DeviceMultiChannelRoomModel''table in SQLite
					VP: Verify that  ''DeviceMultiChannelRoomModel'' table show up null value


				*/
				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5. Leave the Product Type panel as ''DTS:X Ultra''
				Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				appDeviceControl.click(DeviceEdit.NO_IMAGES);
				// 6. Click "Save" link
				appDeviceControl.click(DeviceEdit.SAVE);
				// 11. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 8.Click "Refresh offline database" button
				appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
				appDeviceControl.selectConfirmationDialogOption("Refresh");
				// 9. Click "Download offline database" button
				boolean isDownloaded = appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE);
				Assert.assertTrue(isDownloaded);
				//Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
				// 10.Open the offline databse file by SQLite
				// 11.Navigate to ''DeviceMultiChannelRoomModel''table in SQLite
				Assert.assertTrue(appDeviceControl.isColumnEmptyData(appDeviceControl.getOfflineDBName(device_UUID), "select Id from DeviceMultiChannelRoomModel", "Id"));
				appDeviceControl.doDelete(DeviceInfo.DELETE);
		}
			/*
			 * Verify ''Type'' column on  ''Product'' table not show value ''McRoom'' on offline DB
			 */
			@Test
			public void TC640DADD_75(){
				Reporter.log("Verify ''Type'' column on  ''Product'' table not show value ''McRoom'' on offline DB");
				
				/*
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Leave on Product Type combo box as ''DTS:X Ultra''
					5. Fill valid value into all required fields
					6. Click "Save" link
					7. Click "Publish" link
					8.Click "Refresh offline database" button
					9. Click "Download offline database" button
					VP: Offline Database is downloaded successfully
					10.Open the offline databse file by SQLite
					11.Navigate to ''Type'' column on ''Product'' table in SQLite
					VP: Verify that value ''McRoom'' NOT display


				*/
				
				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5. Leave the Product Type panel as ''DTS:X Ultra''
				Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				appDeviceControl.click(DeviceEdit.NO_IMAGES);
				// 6. Click "Save" link
				appDeviceControl.click(DeviceEdit.SAVE);
				// 11. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 8.Click "Refresh offline database" button
				appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
				appDeviceControl.waitForElementClickable(DeviceInfo.POPUP_REFRESH_CONFIRM);
				Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.POPUP_REFRESH_CONFIRM));
				appDeviceControl.selectConfirmationDialogOption("Refresh");
				// 9. Click "Download offline database" button
				boolean isDownloaded = appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE);
				Assert.assertTrue(isDownloaded);
				// 10.Open the offline databse file by SQLite
				// 11.Navigate to ''DeviceMultiChannelRoomModel''table in SQLite
				Assert.assertFalse(appDeviceControl.isDBTableContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select Type, SubType from Product", "Type", "SubType", "McRoom", "McRoom0"));
				appDeviceControl.doDelete(DeviceInfo.DELETE);
		}
			/*
			 * Verify that Device Headphone tuning is displayed correctly in Audio Route section with''DTS:X Ultra'' option
			 */
			@Test
			public void TC640DADD_78(){ //Cannot do this ticket due to the device headphone already added in default audio route with Eagle 2.0 single file 
				Reporter.log("Verify that Device Headphone tuning is displayed correctly in Audio Route section with''DTS:X Ultra'' option");
				
				/*
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Leave on Product Type combo box as ''DTS:X Ultra''
					5. Fill valid value into all required fields
					6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
					7. Select â€œAttached 0 - Default Modeâ€� in â€œContent Modesâ€� dropdown list
					8. Click on "Select file" button in "Custom Tuning" section
					9. Upload the valid custom tuning DTSCS file
					VP: Verify that the valid tuning DTSCS file is uploaded successfully
					10. Click "Save" link
					11. Click "Publish" link
					VP: Verify that the custom tuning file â€œAttached 0 - Default Modeâ€� is displayed in â€œAudio Routeâ€� section
				
				
				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5. Leave the Product Type panel as ''DTS:X Ultra''
				Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				// 6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
				appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				// 7. Select â€œAttached 0 - Default Modeâ€� in â€œContent Modesâ€� dropdown list
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_0_Default_Mode.getName());
				// 8. Click on "Select file" button in "Custom Tuning" section
				// 9. Upload the valid custom tuning DTSCS file
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Attach0_Eagle2_0.getName());
				appDeviceControl.click(DeviceEdit.NO_IMAGES);
				// 10. Click "Save" link
				appDeviceControl.click(DeviceEdit.SAVE);
				// 11. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				// VP: Verify that the custom tuning file â€œAttached 0 - Default Modeâ€� is displayed in â€œAudio Routeâ€� section
				Assert.assertTrue(driver.findElement(By.partialLinkText(DeviceEdit.Content_Modes_Hig.Attached_0_Default_Mode.getName())).isDisplayed());
				appDeviceControl.doDelete(DeviceInfo.DELETE);*/
		}
			/*
			 * Verify that Device Headphone tuning is displayed correctly in Audio Route section with ''DTS:X Premium'' option
			 */
			@Test
			public void TC640DADD_79(){//Cannot do this ticket due to the device headphone already added in default audio route with Eagle 2.0 single file 
				Reporter.log("Verify that Device Headphone tuning is displayed correctly in Audio Route section with ''DTS:X Premium'' option");
				
				/*
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Leave on Product Type combo box as ''DTS:X Premium''
					5. Fill valid value into all required fields
					6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
					7. Select â€œAttached 0 - Default Modeâ€� in â€œContent Modesâ€� dropdown list
					8. Click on "Select file" button in "Custom Tuning" section
					9. Upload the valid custom tuning DTSCS file
					VP: Verify that the valid tuning DTSCS file is uploaded successfully
					10. Click "Save" link
					11. Click "Publish" link
					VP: Verify that the custom tuning file â€œAttached 0 - Default Modeâ€� is displayed in â€œAudio Routeâ€� section
				*/
				
/*				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5. Leave the Product Type panel as ''DTS:X Premium''
				Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				// 6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
				appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				// 7. Select â€œAttached 0 - Default Modeâ€� in â€œContent Modesâ€� dropdown list
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_0_Default_Mode.getName());
				// 8. Click on "Select file" button in "Custom Tuning" section
				// 9. Upload the valid custom tuning DTSCS file
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Attach0_Eagle2_0.getName());
				appDeviceControl.click(DeviceEdit.NO_IMAGES);
				// 10. Click "Save" link
				appDeviceControl.click(DeviceEdit.SAVE);
				// 11. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				// VP: Verify that the custom tuning file â€œAttached 0 - Default Modeâ€� is displayed in â€œAudio Routeâ€� section
				Assert.assertTrue(driver.findElement(By.partialLinkText(DeviceEdit.Content_Modes_Hig.Attached_0_Default_Mode.getName())).isDisplayed());
				appDeviceControl.doDelete(DeviceInfo.DELETE);*/
		}
			/*
			 * Verify that Device Headphone tuning is displayed correctly in Audio Route section with ''DTS Audio Processing'' option
			 */
			@Test
			public void TC640DADD_80(){// The Device headphone already add on Default Audio route, so this case cannot be run due to change on ticketMPG-5927
				Reporter.log("Verify that Device Headphone tuning is displayed correctly in Audio Route section with ''DTS Audio Processing'' option");
				
				/*
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Leave on Product Type combo box as ''DTS Audio Processing''
					5. Fill valid value into all required fields
					6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
					7. Select â€œDefault Modeâ€� in â€œContent Modesâ€� dropdown list
					8. Click on "Select file" button in "Custom Tuning" section
					9. Upload the valid custom tuning DTSCS file
					VP: Verify that the valid tuning DTSCS file is uploaded successfully
					10. Click "Save" link
					11. Click "Publish" link
					VP: Verify that the custom tuning file â€œAttached 0 - Default Modeâ€� is displayed in â€œAudio Routeâ€� section
				*/
				
/*				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5. Leave the Product Type panel as ''DTS Audio Processing''
				Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				// 6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
				appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				// 7. Select â€œDefault Modeâ€� in â€œContent Modesâ€� dropdown list
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_Default.getType());
				// 8. Click on "Select file" button in "Custom Tuning" section
				// 9. Upload the valid custom tuning DTSCS file
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Default_Mode_Eagle2_0.getName());
				appDeviceControl.click(DeviceEdit.NO_IMAGES);
				// 10. Click "Save" link
				appDeviceControl.click(DeviceEdit.SAVE);
				// 11. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				// VP: Verify that the custom tuning file is displayed in â€œAudio Routeâ€� section
				Assert.assertTrue(driver.findElement(By.partialLinkText(DeviceEdit.Content_Modes.Mode_1.getType())).isDisplayed());
				appDeviceControl.doDelete(DeviceInfo.DELETE);*/
		}
			/*
			 * Verify that Product Type is ''DTS:X Ultra'' , when uploading "Attached 0 - Default Mode" on Device Headphone route, the offline DB will have a new entry in the Product table with Type and SubType data correctly
			 */
			@Test
			public void TC640DADD_81(){
Reporter.log("Verify that Device Headphone tuning is displayed correctly in Audio Route section with''DTS:X Ultra'' option");
				
				/*
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Leave on Product Type combo box as ''DTS:X Ultra''
					5. Fill valid value into all required fields
					6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
					7. Select â€œAttached 0 - Default Modeâ€� in â€œContent Modesâ€� dropdown list
					8. Click on "Select file" button in "Custom Tuning" section
					9. Upload the valid custom tuning DTSCS file
					VP: Verify that the valid tuning DTSCS file is uploaded successfully
					10. Click "Save" link
					11. Click "Publish" link
					12. Refresh offline database
					13. Click "Download Offline Database" link
					14. Open the offline databse file by SQLite
					15. Navigate to Product table in SQLite
					VP: Verify that Product table display new entry with Type as â€œDeviceHeadphoneâ€� and SubType as â€œAttached0
				*/
				
				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5. Leave the Product Type panel as ''DTS:X Ultra''
				Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				// 6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
/*				// 7. Select â€œAttached 0 - Default Modeâ€� in â€œContent Modesâ€� dropdown list
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_0_Default_Mode.getName());
				// 8. Click on "Select file" button in "Custom Tuning" section
				// 9. Upload the valid custom tuning DTSCS file
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Attach0_Eagle2_0.getName());*/
				appDeviceControl.click(DeviceEdit.NO_IMAGES);
				// 10. Click "Save" link
				appDeviceControl.click(DeviceEdit.SAVE);
				// 11. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 12. Refresh offline database
				appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
				appDeviceControl.selectConfirmationDialogOption("Refresh");
				// 9. Click "Download Offline Database" link
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
				// 10. Open the offline databse file by SQLite
				// 11. Navigate to Asset table in SQLite
				// VP: Verify that the tuning Blog data only include Eagle settings data 2.0
				SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
				ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where Type = 'DeviceHeadphone' and SubType = 'Attached0'", "DTSCSAssetId", null);
				Assert.assertTrue(ColumnData.size()>0);
				appDeviceControl.doDelete(DeviceInfo.DELETE);
			}
			/*
			 * Verify that Product Type is ''DTS:X Premium'' , when uploading "Attached 0 - Default Mode" on  Device Headphone route, the offline DB will have a new entry in the Product table with Type and SubType data correctly
			 */
			@Test
			public void TC640DADD_82(){
Reporter.log("Verify that Product Type is ''DTS:X Premium'' , when uploading Attached 0 - Default Mode on  Device Headphone route, the offline DB will have a new entry in the Product table with Type and SubType data correctly");
				
				/*
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Leave on Product Type combo box as ''DTS:X Premium''
					5. Fill valid value into all required fields
					6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
					7. Select â€œAttached 0 - Default Modeâ€� in â€œContent Modesâ€� dropdown list
					8. Click on "Select file" button in "Custom Tuning" section
					9. Upload the valid custom tuning DTSCS file
					VP: Verify that the valid tuning DTSCS file is uploaded successfully
					10. Click "Save" link
					11. Click "Publish" link
					12. Refresh offline database
					13. Click "Download Offline Database" link
					14. Open the offline databse file by SQLite
					15. Navigate to Product table in SQLite
					VP: Verify that Product table display new entry with Type as â€œDeviceHeadphoneâ€� and SubType as â€œAttached0
				*/
				
				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5. Leave the Product Type panel as ''DTS:X Premium''
				Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				// 6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
				appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
/*				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				// 7. Select â€œAttached 0 - Default Modeâ€� in â€œContent Modesâ€� dropdown list
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_0_Default_Mode.getName());
				// 8. Click on "Select file" button in "Custom Tuning" section
				// 9. Upload the valid custom tuning DTSCS file
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Attach0_Eagle2_0.getName());*/
				appDeviceControl.click(DeviceEdit.NO_IMAGES);
				// 10. Click "Save" link
				appDeviceControl.click(DeviceEdit.SAVE);
				// 11. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 12. Refresh offline database
				appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
				appDeviceControl.selectConfirmationDialogOption("Refresh");
				// 9. Click "Download Offline Database" link
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
				// 10. Open the offline databse file by SQLite
				// 11. Navigate to Asset table in SQLite
				// VP: Verify that the tuning Blog data only include Eagle settings data 2.0
				SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
				ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where Type = 'DeviceHeadphone' and SubType = 'Attached2'", "DTSCSAssetId", null);
				Assert.assertTrue(ColumnData.size()>0);
				appDeviceControl.doDelete(DeviceInfo.DELETE);
			}
			/*
			 * Verify that Product Type is ''DTS Audio Processing'' , when uploading "Attached 0 - Default Mode" on  Device Headphone route, the offline DB will have a new entry in the Product table with Type and SubType data correctly
			 */
			@Test
			public void TC640DADD_83(){
				Reporter.log("Verify that Product Type is DTS Audio Processing , when uploading Default Mode on  Device Headphone route, the offline DB will have a new entry in the Product table with Type and SubType data correctly");
				
				/*
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Leave on Product Type combo box as ''DTS Audio Processing''
					5. Fill valid value into all required fields
					6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
					7. Select â€œDefault Modeâ€� in â€œContent Modesâ€� dropdown list
					8. Click on "Select file" button in "Custom Tuning" section
					9. Upload the valid custom tuning DTSCS file
					VP: Verify that the valid tuning DTSCS file is uploaded successfully
					10. Click "Save" link
					11. Click "Publish" link
					12. Refresh offline database
					13. Click "Download Offline Database" link
					14. Open the offline databse file by SQLite
					15. Navigate to Product table in SQLite
					VP: Verify that Product table display new entry with Type as â€œDeviceHeadphoneâ€� and SubType as â€œAttached0
				*/
				
				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5. Leave the Product Type panel as ''DTS Audio Processing''
				Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				// 6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
				appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
/*				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				// 7. Select â€œAttached 0 - Default Modeâ€� in â€œContent Modesâ€� dropdown list
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_Default.getType());
				// 8. Click on "Select file" button in "Custom Tuning" section
				// 9. Upload the valid custom tuning DTSCS file
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Default_Mode_Eagle2_0.getName());*/
				appDeviceControl.click(DeviceEdit.NO_IMAGES);
				// 10. Click "Save" link
				appDeviceControl.click(DeviceEdit.SAVE);
				// 11. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 12. Refresh offline database
				appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
				appDeviceControl.selectConfirmationDialogOption("Refresh");
				// 9. Click "Download Offline Database" link
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
				// 10. Open the offline databse file by SQLite
				// 11. Navigate to Asset table in SQLite
				// VP: Verify that the tuning Blog data only include Eagle settings data 2.0
				SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
				ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where Type = 'DeviceHeadphone' and SubType = 'Attached2'", "DTSCSAssetId", null);
				Assert.assertTrue(ColumnData.size()>0);
				appDeviceControl.doDelete(DeviceInfo.DELETE);
			}
			/*
			 * Verify that when uploading ''Attached 0 - Default Mode" on Device Headphone tuning, the offline DB will contain above tuning in Asset table with Product Type ''DTS:X Ultra''
			 */
			@Test
			public void TC640DADD_84(){
				Reporter.log("Verify that when uploading Attached 0 - Default Mode on Device Headphone tuning, the offline DB will contain above tuning in Asset table with Product Type ''DTS:X Ultra''");
				
				/*
				 	1. Log into DTS portal as a DTS user
					2. Navigate to "Apps or Devicesâ€� page
					3. Click â€œAdd App & Deviceâ€� link
					4. Leave on Product Type combo box as ''DTS:X Ultra''
					5. Fill valid value into all required fields
					6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
					7. Select â€œAttached 0 - Default Modeâ€� in â€œContent Modesâ€� dropdown list
					8. Click on "Select file" button in "Custom Tuning" section
					9. Upload the valid custom tuning DTSCS file
					VP: Verify that the valid tuning DTSCS file is uploaded successfully
					10. Click "Save" link
					11. Click "Publish" link
					12. Refresh offline database
					13. Click "Download Offline Database" link
					14. Open the offline databse file by SQLite
					15. Navigate to Asset table in SQLite
					VP: Verify that Asset table will contain Attached 0 - Default Mode in Device Headphone tuning
				*/
				
				// 1. Log into DTS portal as a DTS user successfully	
				loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
				// 2. Navigate to "Apps & Devices" page	
				appDeviceControl.click(PageHome.LINK_APP_DEVICES);
				// 3. Click "Add App or Device" link	
				appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
				// 4. Fill valid value into all required fields with choosing eagle version 2.0.
				// 5. Leave the Product Type panel as ''DTS:X Ultra''
				Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
				appDevice.remove("save");
				appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
				// 6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
				appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
				// 7. Select â€œAttached 0 - Default Modeâ€� in â€œContent Modesâ€� dropdown list
				appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_0_Default_Mode.getName());
				// 8. Click on "Select file" button in "Custom Tuning" section
				// 9. Upload the valid custom tuning DTSCS file
				appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Attach0_Eagle2_0.getName());
				appDeviceControl.click(DeviceEdit.NO_IMAGES);
				// 10. Click "Save" link
				appDeviceControl.click(DeviceEdit.SAVE);
				// 11. Publish above device
				appDeviceControl.click(DeviceInfo.PUBLISH);
				String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
				// 12. Refresh offline database
				appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
				appDeviceControl.selectConfirmationDialogOption("Refresh");
				// 9. Click "Download Offline Database" link
				Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
				// 10. Open the offline databse file by SQLite
				// 11. Navigate to Asset table in SQLite
				// VP: Verify that Asset table will contain Attached 0 - Default Mode in Device Headphone tuning
				SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
				ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where Type = 'DeviceHeadphone' and SubType = 'Attached0'", "DTSCSAssetId", null);
				for (int i = 0; i < ColumnData.size(); i++) {
					Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
				appDeviceControl.doDelete(DeviceInfo.DELETE);
				}
			}
				/*
				 * Verify that when uploading ''Attached 0 - Default Mode" on Device Headphone tuning, the offline DB will contain above tuning in Asset table with Product Type ''DTS:X Ultra''
				 */
				@Test
				public void TC640DADD_85(){
					Reporter.log("Verify that when uploading Attached 0 - Default Mode on Device Headphone tuning, the offline DB will contain above tuning in Asset table with Product Type ''DTS:X Premium''");
					
					/*
					 	1. Log into DTS portal as a DTS user
						2. Navigate to "Apps or Devicesâ€� page
						3. Click â€œAdd App & Deviceâ€� link
						4. Leave on Product Type combo box as ''DTS:X Premium''
						5. Fill valid value into all required fields
						6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
						7. Select â€œAttached 0 - Default Modeâ€� in â€œContent Modesâ€� dropdown list
						8. Click on "Select file" button in "Custom Tuning" section
						9. Upload the valid custom tuning DTSCS file
						VP: Verify that the valid tuning DTSCS file is uploaded successfully
						10. Click "Save" link
						11. Click "Publish" link
						12. Refresh offline database
						13. Click "Download Offline Database" link
						14. Open the offline databse file by SQLite
						15. Navigate to Asset table in SQLite
						VP: Verify that Asset table will contain Attached 0 - Default Mode in Device Headphone tuning
					*/
					
					// 1. Log into DTS portal as a DTS user successfully	
					loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
					// 2. Navigate to "Apps & Devices" page	
					appDeviceControl.click(PageHome.LINK_APP_DEVICES);
					// 3. Click "Add App or Device" link	
					appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
					// 4. Fill valid value into all required fields with choosing eagle version 2.0.
					// 5. Leave the Product Type panel as ''DTS:X Premium''
					Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
					appDevice.remove("save");
					appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
					// 6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
					appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
					appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
					// 7. Select â€œAttached 0 - Default Modeâ€� in â€œContent Modesâ€� dropdown list
					appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_0_Default_Mode.getName());
					// 8. Click on "Select file" button in "Custom Tuning" section
					// 9. Upload the valid custom tuning DTSCS file
					appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Attach0_Eagle2_0.getName());
					appDeviceControl.click(DeviceEdit.NO_IMAGES);
					// 10. Click "Save" link
					appDeviceControl.click(DeviceEdit.SAVE);
					// 11. Publish above device
					appDeviceControl.click(DeviceInfo.PUBLISH);
					String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
					// 12. Refresh offline database
					appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
					appDeviceControl.selectConfirmationDialogOption("Refresh");
					// 9. Click "Download Offline Database" link
					Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
					// 10. Open the offline databse file by SQLite
					// 11. Navigate to Asset table in SQLite
					// VP: Verify that Asset table will contain Attached 0 - Default Mode in Device Headphone tuning
					SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
					ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where Type = 'DeviceHeadphone' and SubType = 'Attached0'", "DTSCSAssetId", null);
					for (int i = 0; i < ColumnData.size(); i++) {
						Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
					appDeviceControl.doDelete(DeviceInfo.DELETE);
				}
				}
					/*
					 * Verify that when uploading ''Attached 0 - Default Mode" on Device Headphone tuning, the offline DB will contain above tuning in Asset table with Product Type ''DTS Audio Processing''
					 */
					@Test
					public void TC640DADD_86(){
						Reporter.log("Verify that when uploading Attached 0 - Default Mode on Device Headphone tuning, the offline DB will contain above tuning in Asset table with Product Type ''DTS Audio Processing''");
						
						/*
						 	1. Log into DTS portal as a DTS user
							2. Navigate to "Apps or Devicesâ€� page
							3. Click â€œAdd App & Deviceâ€� link
							4. Leave on Product Type combo box as ''DTS Audio Processing''
							5. Fill valid value into all required fields
							6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
							7. Select â€œDefault Modeâ€� in â€œContent Modesâ€� dropdown list
							8. Click on "Select file" button in "Custom Tuning" section
							9. Upload the valid custom tuning DTSCS file
							VP: Verify that the valid tuning DTSCS file is uploaded successfully
							10. Click "Save" link
							11. Click "Publish" link
							12. Refresh offline database
							13. Click "Download Offline Database" link
							14. Open the offline databse file by SQLite
							15. Navigate to Asset table in SQLite
							VP: Verify that Asset table will contain Attached 0 - Default Mode in Device Headphone tuning
						*/
						
						// 1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
						// 2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
						// 3. Click "Add App or Device" link	
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
						// 4. Fill valid value into all required fields with choosing eagle version 2.0.
						// 5. Leave the Product Type panel as ''DTS Audio Processing''
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
						// 6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
/*						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
						// 7. Select â€œAttached 0 - Default Modeâ€� in â€œContent Modesâ€� dropdown list
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_Default.getType());
						// 8. Click on "Select file" button in "Custom Tuning" section
						// 9. Upload the valid custom tuning DTSCS file
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Default_Mode_Eagle2_0.getName());*/
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						// 10. Click "Save" link
						appDeviceControl.click(DeviceEdit.SAVE);
						// 11. Publish above device
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
						// 12. Refresh offline database
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption("Refresh");
						// 9. Click "Download Offline Database" link
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
						// 10. Open the offline databse file by SQLite
						// 11. Navigate to Asset table in SQLite
						// VP: Verify that Asset table will contain Default Mode in Device Headphone tuning
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where Type = 'DeviceHeadphone' and SubType = 'Attached0'", "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
			}
					/*
					 * Verify that when uploading ''Attached 0 - Default Mode" on Device Headphone tuning, the complete profile will contain above tuning in Asset table with Product Type ''DTS:X Ultra''
					 */
					@Test
					public void TC640DADD_87(){
						Reporter.log("Verify that when uploading Attached 0 - Default Mode on Device Headphone tuning, the complete profile will contain above tuning in Asset table with Product Type ''DTS:X Ultra''");
						/*
						1. Log into DTS portal as a DTS user
						2. Navigate to "Apps & Devices" page
						3. Click â€œAdd App or Deviceâ€� link
						4. Fill valid value into all required fields
						5. Leave the Product Type panel as â€œDTS:X Ultraâ€�
						6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
						7. Select â€œDefault Modeâ€� in â€œContent Modesâ€� dropdown list
						8. Click on "Select file" button in "Custom Tuning" section
						9. Upload the valid custom tuning DTSCS file
						VP: Verify that the valid tuning DTSCS file is uploaded successfully
						10. Click "Save" link
						11. Click "Publish" link
						12. Download â€œComplete Profileâ€�
						VP: The complete profile have name: "Device Headphone Settings" with type â€œAttached 0â€�
						 */
						// 1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
						// 2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
						// 3. Click "Add App or Device" link	
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
						// 4. Fill valid value into all required fields with choosing eagle version 2.0.
						// 5. Leave the Product Type panel as ''DTS:X Ultra''
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
						// 6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
						// 7. Select â€œAttached 0 - Default Modeâ€� in â€œContent Modesâ€� dropdown list
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_0_Default_Mode.getName());
						// 8. Click on "Select file" button in "Custom Tuning" section
						// 9. Upload the valid custom tuning DTSCS file
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Attach0_Eagle2_0.getName());
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						// 10. Click "Save" link
						appDeviceControl.click(DeviceEdit.SAVE);
						// 11. Publish above device
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
						// 11. Download the complete profile	
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
						// VP: Verify that the Complete Profile did not have route ''AR_OUT_DEVICE_ACCESSORY''
						Assert.assertTrue(appDeviceControl.checkRouteFile(device_UUID, "AR_OUT_DEVICE_ACCESSORY"));
						Assert.assertTrue(appDeviceControl.checkTypeFile(device_UUID, "attached0"));
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
					/*
					 * Verify that when uploading ''Attached 0 - Default Mode" on Device Headphone tuning, the complete profile will contain above tuning in Asset table with Product Type ''DTS:X Premium''
					 */
					@Test
					public void TC640DADD_88(){
						Reporter.log("Verify that when uploading Attached 0 - Default Mode on Device Headphone tuning, the complete profile will contain above tuning in Asset table with Product Type ''DTS:X Ultra''");
						/*
						1. Log into DTS portal as a DTS user
						2. Navigate to "Apps & Devices" page
						3. Click â€œAdd App or Deviceâ€� link
						4. Fill valid value into all required fields
						5. Leave the Product Type panel as â€œDTS:X Ultraâ€�
						6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
						7. Select â€œDefault Modeâ€� in â€œContent Modesâ€� dropdown list
						8. Click on "Select file" button in "Custom Tuning" section
						9. Upload the valid custom tuning DTSCS file
						VP: Verify that the valid tuning DTSCS file is uploaded successfully
						10. Click "Save" link
						11. Click "Publish" link
						12. Download â€œComplete Profileâ€�
						VP: The complete profile have name: "Device Headphone Settings" with type â€œAttached 0â€�
						 */
						// 1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
						// 2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
						// 3. Click "Add App or Device" link	
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
						// 4. Fill valid value into all required fields with choosing eagle version 2.0.
						// 5. Leave the Product Type panel as ''DTS:X Premium''
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
						// 6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
/*						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
						// 7. Select â€œAttached 0 - Default Modeâ€� in â€œContent Modesâ€� dropdown list
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Hig.Attached_0_Default_Mode.getName());
						// 8. Click on "Select file" button in "Custom Tuning" section
						// 9. Upload the valid custom tuning DTSCS file
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Attach0_Eagle2_0.getName());*/
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						// 10. Click "Save" link
						appDeviceControl.click(DeviceEdit.SAVE);
						// 11. Publish above device
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
						// 11. Download the complete profile	
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
						// VP: Verify that the Complete Profile did not have route ''AR_OUT_DEVICE_ACCESSORY''
						Assert.assertTrue(appDeviceControl.checkRouteFile(device_UUID, "AR_OUT_DEVICE_ACCESSORY"));
						Assert.assertTrue(appDeviceControl.checkTypeFile(device_UUID, "attached0"));
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
					/*
					 * Verify that when uploading ''Default Mode" on Device Headphone tuning, the complete profile will contain above tuning in Asset table with Product Type '''DTS Audio Processing''
					 */
					@Test
					public void TC640DADD_89(){
						Reporter.log("Verify that when uploading Attached 0 - Default Mode on Device Headphone tuning, the complete profile will contain above tuning in Asset table with Product Type '''DTS Audio Processing''");
						/*
						1. Log into DTS portal as a DTS user
						2. Navigate to "Apps & Devices" page
						3. Click â€œAdd App or Deviceâ€� link
						4. Fill valid value into all required fields
						5. Leave the Product Type panel as â€œ'DTS Audio Processingâ€�
						6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
						7. Select â€œDefault Modeâ€� in â€œContent Modesâ€� dropdown list
						8. Click on "Select file" button in "Custom Tuning" section
						9. Upload the valid custom tuning DTSCS file
						VP: Verify that the valid tuning DTSCS file is uploaded successfully
						10. Click "Save" link
						11. Click "Publish" link
						12. Download â€œComplete Profileâ€�
						VP: The complete profile have name: "Device Headphone Settings" with type â€œAttached 0â€�
						 */ /*cannot do this testcase due to channging profile with DTS Audio Processing*/
/*						// 1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
						// 2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
						// 3. Click "Add App or Device" link	
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
						// 4. Fill valid value into all required fields with choosing eagle version 2.0.
						// 5. Leave the Product Type panel as ''DTS Audio Processing''
						Hashtable<String, String> appDevice = TestData.appDeviceDTSPublishAudioProcessing();
						appDevice.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
						// 6. Select Device Headphone in â€œAudio Routeâ€� dropdown list
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE, DeviceEdit.routes.Device_Headphone.getName());
						// 7. Select â€œAttached 0 - Default Modeâ€� in â€œContent Modesâ€� dropdown list
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes.Mode_Default.getType());
						// 8. Click on "Select file" button in "Custom Tuning" section
						// 9. Upload the valid custom tuning DTSCS file
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Device_Headphone_Default_Mode_Eagle2_0.getName());
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						// 10. Click "Save" link
						appDeviceControl.click(DeviceEdit.SAVE);
						// 11. Publish above device
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
						// 11. Download the complete profile	
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK));
						// VP: Verify that the Complete Profile did not have route ''AR_OUT_DEVICE_ACCESSORY''
						Assert.assertTrue(appDeviceControl.checkRouteFile(device_UUID, "AR_OUT_DEVICE_ACCESSORY"));
						Assert.assertTrue(appDeviceControl.checkTypeFile(device_UUID, "default"));
						appDeviceControl.doDelete(DeviceInfo.DELETE);*/
					}
					@Test
					public void TC640DADD_106(){
						Reporter.log("Verify offline databases when create new Ultra device with at least uploaded single tuning files");
						/*
						 * 1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS:X Ultra
							6. Enter data on all remain necessary fields
							7. Upload at least single tuning file
							8. Download Android db, APO db
							9. Check those offline databases
							VP: The tuning is stored correctly on android db and APO db

						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Ultra
//						6. Enter data on all remain necessary fields
//						7. Upload at least single tuning file
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						TestData.setDefaultAudioTuning(appDevice, AddEditProductModel.FileUpload.Speaker_Attached_0_Exptune.getName());
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db, APO db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Check those offline databases
//						VP: The tuning is stored correctly on android db and APO db
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where Type = 'Headphone' or Type = 'Standard' or Type ='Other'" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
							Assert.assertTrue(appDeviceControl.isMapEagleVersion(device_UUID, DeviceEdit.Eagle_Version.Eagle2_0.getName()));
						}
						
//						Click on "Refresh offline database (APO) " button
				        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
//						Click "Download Offline Database (Tuning)" link
						appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
				        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//						Open the tuning offline database  file by SQLite
//						Navigate to Assert table in SQLite
//						VP:The Uri column at Asset table contains all file names:
//							+ [uuid]_lineout_44k.bin
//							+ [uuid]_lineout_48k.bin
//							+ [uuid]_bluetooth_44k.bin
//							+ [uuid]_bluetooth_48k.bin
//							+ [uuid]_usb_44k.bin
//							+ [uuid]_usb_48k.bin
				        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
				        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset", "Uri", null);
//				         Just verify that the tuning data can decrypt 
				        for (int i = 0; i < binFilesActual.size(); i++) {
				        	System.out.println(binFilesActual.get(i));
				        	 Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBNameTuning(device_UUID), "select Data from Asset where Uri = '"+binFilesActual.get(i)+"'","Data", device_UUID));	
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_107(){
						Reporter.log("Verify offline databases when create new premium device with at least uploaded single tuning files");
						/*
						 * 1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS:X Premium
							6. Enter data on all remain necessary fields
							7. Upload at least single tuning file
							8. Download Android db, APO db
							9. Check those offline databases
							VP: The tuning is stored correctly on android db and APO db

						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Premium
//						6. Enter data on all remain necessary fields
//						7. Upload at least single tuning file
						Hashtable<String, String> appDevice = TestData.appDevicePublishSingleFilePremium();
						appDevice.remove("save");
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
						TestData.setDefaultAudioTuning(appDevice, AddEditProductModel.FileUpload.Speaker_Attached_0_Exptune.getName());
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db, APO db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Check those offline databases
//						VP: The tuning is stored correctly on android db and APO db
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where Type = 'Headphone' or Type = 'Standard' or Type ='Other'" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
							Assert.assertTrue(appDeviceControl.isMapEagleVersion(device_UUID, DeviceEdit.Eagle_Version.Eagle2_0.getName()));
						}
//						Click on "Refresh offline database (APO) " button
				        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
//						Click "Download Offline Database (Tuning)" link
						appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
				        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//						Open the tuning offline database  file by SQLite
//						Navigate to Assert table in SQLite
//						VP:The Uri column at Asset table contains all file names:
//							+ [uuid]_lineout_44k.bin
//							+ [uuid]_lineout_48k.bin
//							+ [uuid]_bluetooth_44k.bin
//							+ [uuid]_bluetooth_48k.bin
//							+ [uuid]_usb_44k.bin
//							+ [uuid]_usb_48k.bin
				        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
				        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset", "Uri", null);
//				         Just verify that the tuning data can decrypt 
				        for (int i = 0; i < binFilesActual.size(); i++) {
				        	System.out.println(binFilesActual.get(i));
				        	 Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBNameTuning(device_UUID), "select Data from Asset where Uri = '"+binFilesActual.get(i)+"'","Data", device_UUID));	
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_108(){
						Reporter.log("Verify offline databases when create new DTS Audio Processing device with at least uploaded single tuning files");
						/*
						 * 1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS Audio Processing
							6. Enter data on all remain necessary fields
							7. Upload at least single tuning file
							8. Download Android db, APO db
							9. Check those offline databases
							VP: The tuning is stored correctly on android db and APO db

						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS Audio Processing
//						6. Enter data on all remain necessary fields
//						7. Upload at least single tuning file
						Hashtable<String, String> appDevice = TestData.appDevicePublishSingleFileAudioProcessing();
						appDevice.remove("save");
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
						TestData.setDefaultAudioTuning(appDevice, AddEditProductModel.FileUpload.Speaker_Attached_0_Exptune.getName());
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db, APO db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Check those offline databases
//						VP: The tuning is stored correctly on android db and APO db
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where Type = 'Headphone' or Type = 'Standard' or Type ='Other'" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
							Assert.assertTrue(appDeviceControl.isMapEagleVersion(device_UUID, DeviceEdit.Eagle_Version.Eagle2_0.getName()));
							//Assert.assertTrue(appDeviceControl.checkRouteFile(device_UUID, "AR_OUT_ATTACHED2"));
						}
						
//						Click on "Refresh offline database (APO) " button
				        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
//						Click "Download Offline Database (Tuning)" link
						appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
				        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//						Open the tuning offline database  file by SQLite
//						Navigate to Assert table in SQLite
//						VP:The Uri column at Asset table contains all file names:
//							+ [uuid]_lineout_44k.bin
//							+ [uuid]_lineout_48k.bin
//							+ [uuid]_bluetooth_44k.bin
//							+ [uuid]_bluetooth_48k.bin
//							+ [uuid]_usb_44k.bin
//							+ [uuid]_usb_48k.bin
				        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
				        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset", "Uri", null);
//				         Just verify that the tuning data can decrypt 
				        for (int i = 0; i < binFilesActual.size(); i++) {
				        	System.out.println(binFilesActual.get(i));
				        	 Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBNameTuning(device_UUID), "select Data from Asset where Uri = '"+binFilesActual.get(i)+"'","Data", device_UUID));	
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_109(){
						Reporter.log("Verify  Ultra android offline db stores medata data and tuning data correctly when uploading speakser tunings attached 0 ~ 8");
						/*
						 * 1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS: X Ultra
							6. Enter data on all remain necessary fields
							7. Upload uploading speaker tunings attached 0 ~ 8
							8. Download Android db and open it
							9. Navigate to Device table and get the DTSCSAssetId
							10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: That device completed profile contains speaker tunings attached 0 ~ 8 correctly.
							11. Navigate to Product table and get the DTSCSAssetId of attached 0 ~ 8
							12. Navigate to Asset table and search those  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: The speaker tunings attached 0 ~ 8 is stored correctly and match with tunings uploaded


						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Ultra
//						6. Enter data on all remain necessary fields
//						7. Upload at least single tuning file
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						TestData.setDefaultAudioTuning(appDevice, AddEditProductModel.FileUpload.Default_Speaker_Tuning_Exptune_Files.getName());
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db, APO db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Check those offline databases
//						VP: The tuning is stored correctly on android db and APO db
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where Type = 'Headphone' or Type = 'Standard' or Type ='Other'" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
							Assert.assertTrue(appDeviceControl.isMapEagleVersion(device_UUID, DeviceEdit.Eagle_Version.Eagle2_0.getName()));
						}
						
//						Click on "Refresh offline database (APO) " button
				        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
//						Click "Download Offline Database (Tuning)" link
						appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
				        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//						Open the tuning offline database  file by SQLite
//						Navigate to Assert table in SQLite
//						VP:The Uri column at Asset table contains all file names:
//							+ [uuid]_lineout_44k.bin
//							+ [uuid]_lineout_48k.bin
//							+ [uuid]_bluetooth_44k.bin
//							+ [uuid]_bluetooth_48k.bin
//							+ [uuid]_usb_44k.bin
//							+ [uuid]_usb_48k.bin
				        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
				        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset", "Uri", null);
//				         Just verify that the tuning data can decrypt 
				        for (int i = 0; i < binFilesActual.size(); i++) {
				        	System.out.println(binFilesActual.get(i));
				        	 Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBNameTuning(device_UUID), "select Data from Asset where Uri = '"+binFilesActual.get(i)+"'","Data", device_UUID));	
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_110(){
						Reporter.log("Verify DTS Premium android offline db stores medata data and tuning data correctly when uploading all speakser tunings ");
						/*
						 * 1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS: X Premium
							6. Enter data on all remain necessary fields
							7. Upload uploading speaker tunings attached 0 ~ 8
							8. Download Android db and open it
							9. Navigate to Device table and get the DTSCSAssetId
							10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: That device completed profile contains speaker tunings attached 0 ~ 8 correctly.
							11. Navigate to Product table and get the DTSCSAssetId of attached 0 ~ 8
							12. Navigate to Asset table and search those  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: The speaker tunings attached 0 ~ 8 is stored correctly and match with tunings uploaded


						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Premium
//						6. Enter data on all remain necessary fields
//						7. Upload at least single tuning file
						Hashtable<String, String> appDevice = TestData.appDevicePublishSingleFilePremium();
						appDevice.remove("save");
						TestData.setDefaultAudioTuning(appDevice, AddEditProductModel.FileUpload.Default_Speaker_Tuning_Exptune_Files.getName());
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db, APO db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Check those offline databases
//						VP: The tuning is stored correctly on android db and APO db
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where Type = 'Headphone' or Type = 'Standard' or Type ='Other'" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
							Assert.assertTrue(appDeviceControl.isMapEagleVersion(device_UUID, DeviceEdit.Eagle_Version.Eagle2_0.getName()));
						}
						
//						Click on "Refresh offline database (APO) " button
				        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
//						Click "Download Offline Database (Tuning)" link
						appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
				        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//						Open the tuning offline database  file by SQLite
//						Navigate to Assert table in SQLite
//						VP:The Uri column at Asset table contains all file names:
//							+ [uuid]_lineout_44k.bin
//							+ [uuid]_lineout_48k.bin
//							+ [uuid]_bluetooth_44k.bin
//							+ [uuid]_bluetooth_48k.bin
//							+ [uuid]_usb_44k.bin
//							+ [uuid]_usb_48k.bin
				        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
				        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset", "Uri", null);
//				         Just verify that the tuning data can decrypt 
				        for (int i = 0; i < binFilesActual.size(); i++) {
				        	System.out.println(binFilesActual.get(i));
				        	 Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBNameTuning(device_UUID), "select Data from Asset where Uri = '"+binFilesActual.get(i)+"'","Data", device_UUID));	
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_111(){
						Reporter.log("Verify DTS Audio Processing android offline db stores medata data and tuning data correctly when uploading all speakser tunings ");
						/*
						 * 1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS Audio Processing
							6. Enter data on all remain necessary fields
							7. Upload uploading speaker tunings attached 0 ~ 8
							8. Download Android db and open it
							9. Navigate to Device table and get the DTSCSAssetId
							10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: That device completed profile contains speaker tunings attached 0 ~ 8 correctly.
							11. Navigate to Product table and get the DTSCSAssetId of attached 0 ~ 8
							12. Navigate to Asset table and search those  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: The speaker tunings attached 0 ~ 8 is stored correctly and match with tunings uploaded


						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS Audio Processing
//						6. Enter data on all remain necessary fields
//						7. Upload at least single tuning file
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						TestData.setDefaultAudioTuning(appDevice, AddEditProductModel.FileUpload.Default_Speaker_Tuning_Exptune_Files.getName());
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db, APO db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Check those offline databases
//						VP: The tuning is stored correctly on android db and APO db
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where Type = 'Headphone' or Type = 'Standard' or Type ='Other'" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
							Assert.assertTrue(appDeviceControl.isMapEagleVersion(device_UUID, DeviceEdit.Eagle_Version.Eagle2_0.getName()));
						}
						
//						Click on "Refresh offline database (APO) " button
				        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
//						Click "Download Offline Database (Tuning)" link
						appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
				        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//						Open the tuning offline database  file by SQLite
//						Navigate to Assert table in SQLite
//						VP:The Uri column at Asset table contains all file names:
//							+ [uuid]_lineout_44k.bin
//							+ [uuid]_lineout_48k.bin
//							+ [uuid]_bluetooth_44k.bin
//							+ [uuid]_bluetooth_48k.bin
//							+ [uuid]_usb_44k.bin
//							+ [uuid]_usb_48k.bin
				        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
				        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset", "Uri", null);
//				         Just verify that the tuning data can decrypt 
				        for (int i = 0; i < binFilesActual.size(); i++) {
				        	System.out.println(binFilesActual.get(i));
				        	 Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBNameTuning(device_UUID), "select Data from Asset where Uri = '"+binFilesActual.get(i)+"'","Data", device_UUID));	
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_112(){
						Reporter.log("Verify Ultral android offline db stores medata data and tuning data correctly when uploading all device headphone tunings");
						/*
						 * 1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS: X Ultra
							6. Enter data on all remain necessary fields
							7. Upload uploading device headphone tunings attached 0 ~ 8
							8. Download Android db and open it
							9. Navigate to Device table and get the DTSCSAssetId
							10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: That device completed profile contains speaker tunings attached 0 ~ 8 correctly.
							11. Navigate to Product table and get the DTSCSAssetId of attached 0 ~ 8
							12. Navigate to Asset table and search those  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: The speaker tunings attached 0 ~ 8 is stored correctly and match with tunings uploaded


						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Ultra
//						6. Enter data on all remain necessary fields
//						7. Upload at least single device headphone tuning files
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						TestData.setDefaultAudioTuning(appDevice, AddEditProductModel.FileUpload.Default_Headphone_Tuning_Exptune_Files.getName());
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db, APO db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Check those offline databases
//						VP: the db stores enough Device headphone tuning 
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> deviceHeadphones = sqlite.getColumnData("select SubType from Product where Type = 'DeviceHeadphone'" , "SubType", null);
						Assert.assertTrue(ListUtil.isEquals(deviceHeadphones, appDeviceControl.getDeviceHPListOnDB(DeviceEdit.Product_Types.HPX_High.getType())));
//						VP: The device headphone tunings are stored correctly on android db
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where  Type ='DeviceHeadphone'" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
						}
						
//						Click on "Refresh offline database (APO) " button
				        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
//						Click "Download Offline Database (Tuning)" link
						appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
				        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//						Open the tuning offline database  file by SQLite
//						Navigate to Assert table in SQLite
//						VP:The Uri column at Asset table contains all file names:
//							+ [uuid]_lineout_44k.bin
//							+ [uuid]_lineout_48k.bin
//							+ [uuid]_bluetooth_44k.bin
//							+ [uuid]_bluetooth_48k.bin
//							+ [uuid]_usb_44k.bin
//							+ [uuid]_usb_48k.bin
				        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
				        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset", "Uri", null);
//				         Just verify that the all tuning data can decrypt 
				        for (int i = 0; i < binFilesActual.size(); i++) {
				        	System.out.println(binFilesActual.get(i));
				        	 Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBNameTuning(device_UUID), "select Data from Asset where Uri = '"+binFilesActual.get(i)+"'","Data", device_UUID));	
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_113(){
						Reporter.log("Verify Premium android offline db stores medata data and tuning data correctly when uploading device headphone tunings attached 0 ~ 8");
						/*
						 * 1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS: X Premium
							6. Enter data on all remain necessary fields
							7. Upload uploading device headphone tunings attached 0 ~ 8
							8. Download Android db and open it
							9. Navigate to Device table and get the DTSCSAssetId
							10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: That device completed profile contains speaker tunings attached 0 ~ 8 correctly.
							11. Navigate to Product table and get the DTSCSAssetId of attached 0 ~ 8
							12. Navigate to Asset table and search those  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: The speaker tunings attached 0 ~ 8 is stored correctly and match with tunings uploaded


						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Premium
//						6. Enter data on all remain necessary fields
//						7. Upload at least single device headphone tuning files
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						TestData.setDefaultAudioTuning(appDevice, AddEditProductModel.FileUpload.Default_Headphone_Tuning_Exptune_Files.getName());
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db, APO db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Check those offline databases
//						VP: the db stores enough Device headphone tuning 
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> deviceHeadphones = sqlite.getColumnData("select SubType from Product where Type = 'DeviceHeadphone'" , "SubType", null);
						Assert.assertTrue(ListUtil.isEquals(deviceHeadphones, appDeviceControl.getDeviceHPListOnDB(DeviceEdit.Product_Types.HPX_Medium.getType())));
//						VP: The device headphone tunings are stored correctly on android db
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where  Type ='DeviceHeadphone'" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
						}
						
//						Click on "Refresh offline database (APO) " button
				        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
//						Click "Download Offline Database (Tuning)" link
						appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
				        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//						Open the tuning offline database  file by SQLite
//						Navigate to Assert table in SQLite
//						VP:The Uri column at Asset table contains all file names:
//							+ [uuid]_lineout_44k.bin
//							+ [uuid]_lineout_48k.bin
//							+ [uuid]_bluetooth_44k.bin
//							+ [uuid]_bluetooth_48k.bin
//							+ [uuid]_usb_44k.bin
//							+ [uuid]_usb_48k.bin
				        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
				        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset", "Uri", null);
//				         Just verify that the all tuning data can decrypt 
				        for (int i = 0; i < binFilesActual.size(); i++) {
				        	System.out.println(binFilesActual.get(i));
				        	 Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBNameTuning(device_UUID), "select Data from Asset where Uri = '"+binFilesActual.get(i)+"'","Data", device_UUID));	
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_114(){
						Reporter.log("Verify Audio Processing  android offline db stores medata data and tuning data correctly when uploading all device headphone tunings ");
						/*
						 * 1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS: X Premium
							6. Enter data on all remain necessary fields
							7. Upload uploading device headphone tunings attached 0 ~ 8
							8. Download Android db and open it
							9. Navigate to Device table and get the DTSCSAssetId
							10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: That device completed profile contains speaker tunings attached 0 ~ 8 correctly.
							11. Navigate to Product table and get the DTSCSAssetId of attached 0 ~ 8
							12. Navigate to Asset table and search those  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: The speaker tunings attached 0 ~ 8 is stored correctly and match with tunings uploaded


						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Premium
//						6. Enter data on all remain necessary fields
//						7. Upload at least single device headphone tuning files
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						TestData.setDefaultAudioTuning(appDevice, AddEditProductModel.FileUpload.Default_Headphone_Tuning_Exptune_Files.getName());
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db, APO db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Check those offline databases
//						VP: the db stores enough Device headphone tuning 
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> deviceHeadphones = sqlite.getColumnData("select SubType from Product where Type = 'DeviceHeadphone'" , "SubType", null);
						Assert.assertTrue(ListUtil.isEquals(deviceHeadphones, appDeviceControl.getDeviceHPListOnDB(DeviceEdit.Product_Types.HPX_Low.getType())));
//						VP: The device headphone tunings are stored correctly on android db
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where  Type ='DeviceHeadphone'" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
						}
						
//						Click on "Refresh offline database (APO) " button
				        appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE_APO);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
//						Click "Download Offline Database (Tuning)" link
						appDeviceControl.waitElement(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
				        appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE_APO_TUNING);
//						Open the tuning offline database  file by SQLite
//						Navigate to Assert table in SQLite
//						VP:The Uri column at Asset table contains all file names:
//							+ [uuid]_lineout_44k.bin
//							+ [uuid]_lineout_48k.bin
//							+ [uuid]_bluetooth_44k.bin
//							+ [uuid]_bluetooth_48k.bin
//							+ [uuid]_usb_44k.bin
//							+ [uuid]_usb_48k.bin
				        SQLiteDbUtil dbUtil = new SQLiteDbUtil(appDeviceControl.getOfflineDBNameTuning(device_UUID));
				        ArrayList<String> binFilesActual = dbUtil.getColumnData("Select * from Asset", "Uri", null);
//				         Just verify that the all tuning data can decrypt 
				        for (int i = 0; i < binFilesActual.size(); i++) {
				        	System.out.println(binFilesActual.get(i));
				        	 Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBNameTuning(device_UUID), "select Data from Asset where Uri = '"+binFilesActual.get(i)+"'","Data", device_UUID));	
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_115(){
						Reporter.log("Verify Ultra android offline db stores medata data and tuning data correctly when uploading CTC tuning");
						/*
						 * 1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS: X Ultra
							6. Enter data on all remain necessary fields
							7. Upload CTC tunings
							8. Download Android db and open it
							9. Navigate to Product table
			                VP:Verify that Product table has a new entry with Type as Ô„eviceTuningÔ and SubType as "CTC"

						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Ultra
//						6. Enter data on all remain necessary fields
//						7. Upload at CTC tuning files
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						TestData.setDefaultAudioTuning(appDevice, AddEditProductModel.FileUpload.Default_CTC_Tuning_Exptune_File.getName());
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Navigate to Product table
//						VP: Verify that Product table has a new entry with Type as Ô„eviceTuningÔ and SubType as "CTC" 
						Assert.assertTrue(appDeviceControl.isDBTableContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product", "Type", "SubType", "DeviceTuning", "CTC"));
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_116(){
						Reporter.log("Verify Premium android offline db stores medata data and tuning data correctly when uploading CTC tuning");
						/*
						 * 1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS: X Ultra
							6. Enter data on all remain necessary fields
							7. Upload CTC tunings
							8. Download Android db and open it
							9. Navigate to Product table
			                VP:Verify that Product table has a new entry with Type as Ô„eviceTuningÔ and SubType as "CTC"

						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Premium
//						6. Enter data on all remain necessary fields
//						7. Upload at CTC tuning files
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						TestData.setDefaultAudioTuning(appDevice, AddEditProductModel.FileUpload.Default_CTC_Tuning_Exptune_File.getName());
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Navigate to Product table
//						VP: Verify that Product table has a new entry with Type as Ô„eviceTuningÔ and SubType as "CTC" 
						Assert.assertTrue(appDeviceControl.isDBTableContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product", "Type", "SubType", "DeviceTuning", "CTC"));
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_117(){
						Reporter.log("Verify Audio processing android offline db stores medata data and tuning data correctly when uploading CTC tuning");
						/*
						 * 1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS Audio processing
							6. Enter data on all remain necessary fields
							7. Upload CTC tunings
							8. Download Android db and open it
							9. Navigate to Product table
			                VP:Verify that Product table has a new entry with Type as Ô„eviceTuningÔ and SubType as "CTC"

						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS Audio processing
//						6. Enter data on all remain necessary fields
//						7. Upload at CTC tuning files
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						TestData.setDefaultAudioTuning(appDevice, AddEditProductModel.FileUpload.Default_CTC_Tuning_Exptune_File.getName());
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Navigate to Product table
//						VP: Verify that Product table has a new entry with Type as Ô„eviceTuningÔ and SubType as "CTC" 
						Assert.assertTrue(appDeviceControl.isDBTableContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product", "Type", "SubType", "DeviceTuning", "CTC"));
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_118(){
						Reporter.log("Verify Ultra android offline db stores medata data and tuning data correctly when uploading BEQ tuning");
						/*
						 * 1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS: X Ultra
							6. Enter data on all remain necessary fields
							7. Upload BEQ tunings
							8. Download Android db and open it
							9. Navigate to Product table
			                VP:Verify that Product table has a new entry with Type as Ô„eviceTuningÔ and SubType as "GPEQ"

						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Ultra
//						6. Enter data on all remain necessary fields
//						7. Upload at BEQ tuning files
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						TestData.setDefaultAudioTuning(appDevice, AddEditProductModel.FileUpload.Default_BEQ_Tuning_Exptune_File.getName());
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Navigate to Product table
//						VP: Verify that Product table has a new entry with Type as Ô„eviceTuningÔ and SubType as "GPEQ" 
						Assert.assertTrue(appDeviceControl.isDBTableContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product", "Type", "SubType", "DeviceTuning", "GPEQ"));
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_119(){
						Reporter.log("Verify Premium android offline db stores medata data and tuning data correctly when uploading BEQ tuning");
						/*
						 * 1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS: X Premium
							6. Enter data on all remain necessary fields
							7. Upload BEQ tunings
							8. Download Android db and open it
							9. Navigate to Product table
			                VP:Verify that Product table has a new entry with Type as Ô„eviceTuningÔ and SubType as "GPEQ"

						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Premium
//						6. Enter data on all remain necessary fields
//						7. Upload at BEQ tuning files
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						TestData.setDefaultAudioTuning(appDevice, AddEditProductModel.FileUpload.Default_BEQ_Tuning_Exptune_File.getName());
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Navigate to Product table
//						VP: Verify that Product table has a new entry with Type as Ô„eviceTuningÔ and SubType as "GPEQ" 
						Assert.assertTrue(appDeviceControl.isDBTableContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product", "Type", "SubType", "DeviceTuning", "GPEQ"));
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_120(){
						Reporter.log("Verify Audio Processing android offline db stores medata data and tuning data correctly when uploading BEQ tuning");
						/*
						 * 1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS Audio Processing
							6. Enter data on all remain necessary fields
							7. Upload BEQ tunings
							8. Download Android db and open it
							9. Navigate to Product table
			                VP:Verify that Product table has a new entry with Type as Ô„eviceTuningÔ and SubType as "GPEQ"

						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Premium
//						6. Enter data on all remain necessary fields
//						7. Upload at BEQ tuning files
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						TestData.setDefaultAudioTuning(appDevice, AddEditProductModel.FileUpload.Default_BEQ_Tuning_Exptune_File.getName());
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Navigate to Product table
//						VP: Verify that Product table has a new entry with Type as Ô„eviceTuningÔ and SubType as "GPEQ" 
						Assert.assertTrue(appDeviceControl.isDBTableContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product", "Type", "SubType", "DeviceTuning", "GPEQ"));
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					
					@Test
					public void TC640DADD_121(){
						// This testcase also covers TC640DADD_96
						Reporter.log("Verify Ultral android offline db stores medata data and tuning data correctly when uploading standard accessory  Over ear headphone tuning");
						/*
						 *  1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS: X Ultra
							6. Enter data on all remain necessary fields
							7. At Standard Accessories combobox, select " Over ear headphones" and then upload the single tunings
							7a. Save and publish the device
							8. Download Android db and open it
							9. Navigate to Device table and get the DTSCSAssetId
							10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: That device completed profle contains that tuning correctly.

						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Ultral
//						6. Enter data on all remain necessary fields
//						7. At Standard Accessories combobox, select " Over ear headphones" and then upload the single tunings
//						7a. Save and publish the device
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
						appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_LineOut_BT_USB_ver2.getName());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Navigate to Device table and get the DTSCSAssetId
//						10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
//						VP: That device completed profle contains that tuning correctly.
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Device" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName() , AR_OUT_LINEOUT));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName() , AR_OUT_BLUETOOTH));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName() , AR_OUT_USB));
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_122(){
						Reporter.log("Verify Medium android offline db stores medata data and tuning data correctly when uploading standard accessory  Over ear headphone tuning");
						/*
						 *  1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS: X Premium
							6. Enter data on all remain necessary fields
							7. At Standard Accessories combobox, select " Over ear headphones" and then upload the single tunings
							7a. Save and publish the device
							8. Download Android db and open it
							9. Navigate to Device table and get the DTSCSAssetId
							10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: That device completed profle contains that tuning correctly.

						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Premium
//						6. Enter data on all remain necessary fields
//						7. At Standard Accessories combobox, select " Over ear headphones" and then upload the single tunings
//						7a. Save and publish the device
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
						appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_LineOut_BT_USB_ver2.getName());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Navigate to Device table and get the DTSCSAssetId
//						10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
//						VP: That device completed profle contains that tuning correctly.
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Device" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName() , AR_OUT_LINEOUT));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName() , AR_OUT_BLUETOOTH));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName() , AR_OUT_USB));
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_123(){
						// invalid due to MPG-6633
						/*Reporter.log("Verify Audio Processing android offline db stores medata data and tuning data correctly when uploading standard accessory  Over ear headphone tuning");
						/*
						 *  1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS Audio Processing
							6. Enter data on all remain necessary fields
							7. At Standard Accessories combobox, select " Over ear headphones" and then upload the single tunings
							7a. Save and publish the device
							8. Download Android db and open it
							9. Navigate to Device table and get the DTSCSAssetId
							10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: That device completed profle contains that tuning correctly.

						 
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS Audio Processing
//						6. Enter data on all remain necessary fields
//						7. At Standard Accessories combobox, select " Over ear headphones" and then upload the single tunings
//						7a. Save and publish the device
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
						appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_3routes_ver2.getName());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Navigate to Device table and get the DTSCSAssetId
//						10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
//						VP: That device completed profle contains that tuning correctly.
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Device" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName() , AR_OUT_LINEOUT));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName() , AR_OUT_BLUETOOTH));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName() , AR_OUT_USB));
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
						*/
					}
					@Test
					public void TC640DADD_124(){
						// This testcase also cover 640DADD_97
						Reporter.log("Verify Ultral android offline db stores medata data and tuning data correctly when uploading standard accessory Earbuds tuning");
						/*
						 *  1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS: X Ultra
							6. Enter data on all remain necessary fields
							7. At Standard Accessories combobox, select "Earbuds" and then upload the single tunings
							7a. Save and publish the device
							8. Download Android db and open it
//							9. Navigate to Device table and get the DTSCSAssetId
							10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: That device completed profle contains that tuning correctly.

						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Ultral
//						6. Enter data on all remain necessary fields
//						7. At Standard Accessories combobox, select "Earbuds" and then upload the single tunings
//						7a. Save and publish the device
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Earbuds.getName());
						appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_LineOut_BT_USB_ver2.getName());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Navigate to Device table and get the DTSCSAssetId
//						10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
//						VP: That device completed profle contains that tuning correctly.
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Device" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Earbuds.getName() , AR_OUT_LINEOUT));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Earbuds.getName() , AR_OUT_BLUETOOTH));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Earbuds.getName() , AR_OUT_USB));
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_125(){
						Reporter.log("Verify Medium android offline db stores medata data and tuning data correctly when uploading standard accessory Earbuds tuning");
						/*
						 *  1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS: X Premium
							6. Enter data on all remain necessary fields
							7. At Standard Accessories combobox, select "Earbuds" and then upload the single tunings
							7a. Save and publish the device
							8. Download Android db and open it
							9. Navigate to Device table and get the DTSCSAssetId
							10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: That device completed profle contains that tuning correctly.

						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Premium
//						6. Enter data on all remain necessary fields
//						7. At Standard Accessories combobox, select "Earbuds" and then upload the single tunings
//						7a. Save and publish the device
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Earbuds.getName());
						appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_LineOut_BT_USB_ver2.getName());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Navigate to Device table and get the DTSCSAssetId
//						10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
//						VP: That device completed profle contains that tuning correctly.
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Device" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Earbuds.getName() , AR_OUT_LINEOUT));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Earbuds.getName() , AR_OUT_BLUETOOTH));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Earbuds.getName() , AR_OUT_USB));
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_126(){
						// invalid due to MPG-6633
						/*Reporter.log("Verify Audio Processing android offline db stores medata data and tuning data correctly when uploading standard accessory Earbuds tuning");
						/*
						 *  1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS Audio Processing
							6. Enter data on all remain necessary fields
							7. At Standard Accessories combobox, select "Earbuds" and then upload the single tunings
							7a. Save and publish the device
							8. Download Android db and open it
							9. Navigate to Device table and get the DTSCSAssetId
							10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: That device completed profle contains that tuning correctly.

						 
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS Audio Processing
//						6. Enter data on all remain necessary fields
//						7. At Standard Accessories combobox, select "Earbudss" and then upload the single tunings
//						7a. Save and publish the device
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Earbuds.getName());
						appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_3routes_ver2.getName());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Navigate to Device table and get the DTSCSAssetId
//						10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
//						VP: That device completed profle contains that tuning correctly.
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Device" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Earbuds.getName() , AR_OUT_LINEOUT));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Earbuds.getName() , AR_OUT_BLUETOOTH));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Earbuds.getName() , AR_OUT_USB));
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
						*/
					}
					@Test
					public void TC640DADD_127(){
						// This testcase also cover 640DADD_98
						Reporter.log("Verify Ultral android offline db stores medata data and tuning data correctly when uploading standard accessory  Ear-piece tuning");
						/*
						 *  1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS: X Ultra
							6. Enter data on all remain necessary fields
							7. At Standard Accessories combobox, select "Ear-piece" and then upload the single tunings
							7a. Save and publish the device
							8. Download Android db and open it
							9. Navigate to Device table and get the DTSCSAssetId
							10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: That device completed profle contains that tuning correctly.

						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Ultral
//						6. Enter data on all remain necessary fields
//						7. At Standard Accessories combobox, select "Ear-piece" and then upload the single tunings
//						7a. Save and publish the device
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Ear_piece.getName());
						appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_LineOut_BT_USB_ver2.getName());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Navigate to Device table and get the DTSCSAssetId
//						10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
//						VP: That device completed profle contains that tuning correctly.
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Device" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Ear_piece.getName() , AR_OUT_LINEOUT));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Ear_piece.getName() , AR_OUT_BLUETOOTH));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Ear_piece.getName() , AR_OUT_USB));
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_128(){
						Reporter.log("Verify Medium android offline db stores medata data and tuning data correctly when uploading standard accessory Ear-piece tuning");
						/*
						 *  1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS: X Premium
							6. Enter data on all remain necessary fields
							7. At Standard Accessories combobox, select "Ear-pieces" and then upload the single tunings
							7a. Save and publish the device
							8. Download Android db and open it
							9. Navigate to Device table and get the DTSCSAssetId
							10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: That device completed profle contains that tuning correctly.

						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Premium
//						6. Enter data on all remain necessary fields
//						7. At Standard Accessories combobox, select "Ear-piece" and then upload the single tunings
//						7a. Save and publish the device
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Ear_piece.getName());
						appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_LineOut_BT_USB_ver2.getName());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Navigate to Device table and get the DTSCSAssetId
//						10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
//						VP: That device completed profle contains that tuning correctly.
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Device" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Ear_piece.getName() , AR_OUT_LINEOUT));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Ear_piece.getName() , AR_OUT_BLUETOOTH));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Ear_piece.getName() , AR_OUT_USB));
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_129(){
						// invalid due to MPG-6633
						/*Reporter.log("Verify Audio Processing android offline db stores medata data and tuning data correctly when uploading standard accessory  Over ear headphone tuning");
						/*
						 *  1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS Audio Processing
							6. Enter data on all remain necessary fields
							7. At Standard Accessories combobox, select "Ear-piece" and then upload the single tunings
							7a. Save and publish the device
							8. Download Android db and open it
							9. Navigate to Device table and get the DTSCSAssetId
							10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: That device completed profle contains that tuning correctly.

						 
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS Audio Processing
//						6. Enter data on all remain necessary fields
//						7. At Standard Accessories combobox, select "Ear-piece" and then upload the single tunings
//						7a. Save and publish the device
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Ear_piece.getName());
						appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_3routes_ver2.getName());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Navigate to Device table and get the DTSCSAssetId
//						10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
//						VP: That device completed profle contains that tuning correctly.
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Device" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Ear_piece.getName() , AR_OUT_LINEOUT));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Ear_piece.getName() , AR_OUT_BLUETOOTH));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Ear_piece.getName() , AR_OUT_USB));
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
						*/
					}
					@Test
					public void TC640DADD_130(){
						// This testcase also cover 640DADD_100
						Reporter.log("Verify Ultral android offline db stores medata data and tuning data correctly when uploading standard accessory External Speakers tuning");
						/*
						 *  1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS: X Ultra
							6. Enter data on all remain necessary fields
							7. At Standard Accessories combobox, select "External Speakers" and then upload the single tunings
							7a. Save and publish the device
							8. Download Android db and open it
							9. Navigate to Device table and get the DTSCSAssetId
							10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: That device completed profle contains that tuning correctly.

						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Ultral
//						6. Enter data on all remain necessary fields
//						7. At Standard Accessories combobox, select "External Speakers" and then upload the single tunings
//						7a. Save and publish the device
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.External_Speakers.getName());
						appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_LineOut_BT_USB_ver2.getName());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Navigate to Device table and get the DTSCSAssetId
//						10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
//						VP: That device completed profle contains that tuning correctly.
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Device" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.External_Speakers.getName() , AR_OUT_LINEOUT));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.External_Speakers.getName() , AR_OUT_BLUETOOTH));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.External_Speakers.getName() , AR_OUT_USB));
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_131(){
						Reporter.log("Verify Medium android offline db stores medata data and tuning data correctly when uploading standard accessory External Speakers tuning");
						/*
						 *  1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS: X Premium
							6. Enter data on all remain necessary fields
							7. At Standard Accessories combobox, select "External Speakers" and then upload the single tunings
							7a. Save and publish the device
							8. Download Android db and open it
							9. Navigate to Device table and get the DTSCSAssetId
							10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: That device completed profle contains that tuning correctly.

						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Premium
//						6. Enter data on all remain necessary fields
//						7. At Standard Accessories combobox, select "External Speakers" and then upload the single tunings
//						7a. Save and publish the device
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.External_Speakers.getName());
						appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_LineOut_BT_USB_ver2.getName());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Navigate to Device table and get the DTSCSAssetId
//						10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
//						VP: That device completed profle contains that tuning correctly.
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Device" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.External_Speakers.getName() , AR_OUT_LINEOUT));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.External_Speakers.getName() , AR_OUT_BLUETOOTH));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.External_Speakers.getName() , AR_OUT_USB));
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_132(){
						// invalid due to MPG-6633
						/*Reporter.log("Verify Audio Processing android offline db stores medata data and tuning data correctly when uploading standard accessory External Speakers tuning");
						/*
						 *  1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS Audio Processing
							6. Enter data on all remain necessary fields
							7. At Standard Accessories combobox, select "External Speakers" and then upload the single tunings
							7a. Save and publish the device
							8. Download Android db and open it
							9. Navigate to Device table and get the DTSCSAssetId
							10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: That device completed profle contains that tuning correctly.

						 
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS Audio Processing
//						6. Enter data on all remain necessary fields
//						7. At Standard Accessories combobox, select " External Speakers" and then upload the single tunings
//						7a. Save and publish the device
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.External_Speakers.getName());
						appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_3routes_ver2.getName());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Navigate to Device table and get the DTSCSAssetId
//						10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
//						VP: That device completed profle contains that tuning correctly.
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Device" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.External_Speakers.getName() , AR_OUT_LINEOUT));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.External_Speakers.getName() , AR_OUT_BLUETOOTH));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.External_Speakers.getName() , AR_OUT_USB));
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
						*/
					}
					@Test
					public void TC640DADD_133(){
						// This testcase also cover 640DADD_99
						Reporter.log("Verify Ultral android offline db stores medata data and tuning data correctly when uploading standard accessory Car-Audio tuning");
						/*
						 *  1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS: X Ultra
							6. Enter data on all remain necessary fields
							7. At Standard Accessories combobox, select "Car-Audio" and then upload the single tunings
							7a. Save and publish the device
							8. Download Android db and open it
							9. Navigate to Device table and get the DTSCSAssetId
							10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: That device completed profle contains that tuning correctly.

						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Ultral
//						6. Enter data on all remain necessary fields
//						7. At Standard Accessories combobox, select "Car-Audio" and then upload the single tunings
//						7a. Save and publish the device
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Car_Audio.getName());
						appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_LineOut_BT_USB_ver2.getName());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Navigate to Device table and get the DTSCSAssetId
//						10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
//						VP: That device completed profle contains that tuning correctly.
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Device" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Car_Audio.getName() , AR_OUT_LINEOUT));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Car_Audio.getName() , AR_OUT_BLUETOOTH));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Car_Audio.getName() , AR_OUT_USB));
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_134(){
						Reporter.log("Verify Medium android offline db stores medata data and tuning data correctly when uploading standard accessory Car-Audio tuning");
						/*
						 *  1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS: X Premium
							6. Enter data on all remain necessary fields
							7. At Standard Accessories combobox, select "Car-Audio" and then upload the single tunings
							7a. Save and publish the device
							8. Download Android db and open it
							9. Navigate to Device table and get the DTSCSAssetId
							10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: That device completed profle contains that tuning correctly.

						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Premium
//						6. Enter data on all remain necessary fields
//						7. At Standard Accessories combobox, select "Car-Audio" and then upload the single tunings
//						7a. Save and publish the device
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Car_Audio.getName());
						appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_LineOut_BT_USB_ver2.getName());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Navigate to Device table and get the DTSCSAssetId
//						10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
//						VP: That device completed profle contains that tuning correctly.
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Device" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Car_Audio.getName() , AR_OUT_LINEOUT));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Car_Audio.getName() , AR_OUT_BLUETOOTH));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Car_Audio.getName() , AR_OUT_USB));
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_135(){
						// invalid due to MPG-6633
						/*Reporter.log("Verify Audio Processing android offline db stores medata data and tuning data correctly when uploading standard accessory Car-Audio tuning");
						/*
						 *  1. Log into DTS portal
							2. Navigate to "Apps & Devices" page
							3. Click on "Add App or Device"
							4. Select  Eagle Version : Eagle 2.0
							5. Select Product type: DTS Audio Processing
							6. Enter data on all remain necessary fields
							7. At Standard Accessories combobox, select "Car-Audio" and then upload the single tunings
							7a. Save and publish the device
							8. Download Android db and open it
							9. Navigate to Device table and get the DTSCSAssetId
							10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
							VP: That device completed profle contains that tuning correctly.

						 
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS Audio Processing
//						6. Enter data on all remain necessary fields
//						7. At Standard Accessories combobox, select " Car-Audio" and then upload the single tunings
//						7a. Save and publish the device
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Car_Audio.getName());
						appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_3routes_ver2.getName());
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						8. Download Android db
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption(Refresh);
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						9. Navigate to Device table and get the DTSCSAssetId
//						10. Navigate to Asset table and search that  DTSCSAssetId above and then get data at Data column, after that convert this data to .txt file
//						VP: That device completed profle contains that tuning correctly.
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Device" , "DTSCSAssetId", null);
						for (int i = 0; i < ColumnData.size(); i++) {
							Assert.assertTrue(appDeviceControl.getBinFile(appDeviceControl.getOfflineDBName(device_UUID), "select Data from Asset where Type = 'TUNING' and Id = '" + ColumnData.get(i).toString() + "'", "Data", device_UUID));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Car_Audio.getName() , AR_OUT_LINEOUT));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Car_Audio.getName() , AR_OUT_BLUETOOTH));
							Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Car_Audio.getName() , AR_OUT_USB));
						}
						
						appDeviceControl.click(DeviceInfo.DELETE);
						*/
					}
					@Test
					public void TC640DADD_91(){
						Reporter.log("Verify App & Device page UI  for uploading single DTSCS file");
						/*
						 *  1. Log into DTS portal as a DTS user
							2. Navigate to "Apps & Devices" page
							3. Click  on ''Add App or Device''
							4. Leave the Product Type combo box as ''DTS:X Ultra''
							5. Fill valid value into all required fields
							6. Select Over ear headphone at Audio Route combo box
							7. Upload a valid single tuning
							8. Click on Save button
							VP: Single tuning uploaded, is displayed on Device detail page as a link
							9. Click on Public  button
							VP: Single tuning uploaded, is displayed on Device detail page as a link

						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Ultra
//						6. Enter data on all remain necessary fields
//						7. At Standard Accessories combobox, select " Over ear headphones" and then upload the single tunings
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
						appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_LineOut_BT_USB_ver2.getName());
//						8. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						VP: Single tuning uploaded, is displayed on Device detail page as a link
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME_VER20), DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
//						9. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
//						VP: Single tuning uploaded, is displayed on Device detail page as a link
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.UPLOADED_CUSTOM_TUNNING_NAME_VER20), DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_92(){
						Reporter.log("Verify that after uploading single turning include 3 route line-out, bluetooth and usb on complete profile");
						/*
						 *  1. Log into DTS portal as a DTS user
							2. Navigate to "Apps & Devices" page
							3. Click  on ''Add App or Device''
							4. Leave the Product Type combo box as ''DTS:X Ultra''
							5. Fill valid value into all required fields
							6. Select Line out at Audio Route combo box
							6. Select Over ear headphone at Audio Route combo box
							7. Upload a valid single tuning file with 3 route line-out, bluetooth and usb
							8. Click on Save button
							9. Click on Public  button
							10. Click "Complete profile" link
							11. Open complete by using convert tools
							VP: The complete profile include type : "over-ear" and route: AR_OUT_LINEOUT, AR_OUT_BLUETOOTH,AR_OUT_USB


						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Ultra
//						6. Enter data on all remain necessary fields
//						7. At Standard Accessories combobox, select " Over ear headphones" and then Upload a valid single tuning file with 3 route line-out, bluetooth and usb
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
						appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_LineOut_BT_USB_ver2.getName());
//						8. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						VP: Single tuning uploaded, is displayed on Device detail page as a link
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME_VER20), DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
//						9. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						10. Click "Complete profile" link
						appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK);
//						11. Open complete by using convert tools
//						VP: The complete profile include type : "over-ear" and route: AR_OUT_LINEOUT, AR_OUT_BLUETOOTH,AR_OUT_USB
						appDeviceControl.click(DeviceInfo.DELETE);
						Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName() , AR_OUT_LINEOUT));
						Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName() , AR_OUT_BLUETOOTH));
						Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName() , AR_OUT_USB));
					}
					@Test
					public void TC640DADD_93(){
						Reporter.log("Verify that after uploading single turning include 2 route line-out, bluetooth on complete profile");
						/*
						 *  1. Log into DTS portal as a DTS user
							2. Navigate to "Apps & Devices" page
							3. Click  on ''Add App or Device''
							4. Leave the Product Type combo box as ''DTS:X Ultra''
							5. Fill valid value into all required fields
							6. Select Line out at Audio Route combo box
							6. Select Over ear headphone at Audio Route combo box
							7. Upload a valid single tuning file with routes line-out, bluetooth 
							8. Click on Save button
							9. Click on Public  button
							10. Click "Complete profile" link
							11. Open complete by using convert tools
							VP: The complete profile include type : "over-ear" and route: AR_OUT_LINEOUT, AR_OUT_BLUETOOTH


						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Ultra
//						6. Enter data on all remain necessary fields
//						7. At Standard Accessories combobox, select " Over ear headphones" and then Upload a valid single tuning file with 2 route line-out, bluetooth 
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
						appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_LineOut_BT_ver2.getName());
//						8. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						VP: Single tuning uploaded, is displayed on Device detail page as a link
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME_VER20), DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
//						9. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						10. Click "Complete profile" link
						appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK);
//						11. Open complete by using convert tools
//						VP: The complete profile include type : "over-ear" and route: AR_OUT_LINEOUT, AR_OUT_BLUETOOTH
						appDeviceControl.click(DeviceInfo.DELETE);
						Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName() , AR_OUT_LINEOUT));
						Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName() , AR_OUT_BLUETOOTH));
					}
					@Test
					public void TC640DADD_95(){
						Reporter.log("Verify that after uploading single turning include 3 route  bluetooth and usb on complete profile");
						/*
						 *  1. Log into DTS portal as a DTS user
							2. Navigate to "Apps & Devices" page
							3. Click  on ''Add App or Device''
							4. Leave the Product Type combo box as ''DTS:X Ultra''
							5. Fill valid value into all required fields
							6. Select Line out at Audio Route combo box
							6. Select Over ear headphone at Audio Route combo box
							7. Upload a valid single tuning file with 3 route  bluetooth and usb
							8. Click on Save button
							9. Click on Public  button
							10. Click "Complete profile" link
							11. Open complete by using convert tools
							VP: The complete profile include type : "over-ear" and route:  AR_OUT_BLUETOOTH,AR_OUT_USB


						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Ultra
//						6. Enter data on all remain necessary fields
//						7. At Standard Accessories combobox, select " Over ear headphones" and then Upload a valid single tuning file with 3 route  bluetooth and usb
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
						appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_BT_USB_ver2.getName());
//						8. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						VP: Single tuning uploaded, is displayed on Device detail page as a link
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME_VER20), DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
//						9. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						10. Click "Complete profile" link
						appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK);
//						11. Open complete by using convert tools
//						VP: The complete profile include type : "over-ear" and route: AR_OUT_LINEOUT, AR_OUT_BLUETOOTH,AR_OUT_USB
						appDeviceControl.click(DeviceInfo.DELETE);
						Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName() , AR_OUT_BLUETOOTH));
						Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName() , AR_OUT_USB));
					}
					@Test
					public void TC640DADD_94(){
						Reporter.log("Verify that after uploading single turning include 3 route line-out and usb on complete profile");
						/*
						 *  1. Log into DTS portal as a DTS user
							2. Navigate to "Apps & Devices" page
							3. Click  on ''Add App or Device''
							4. Leave the Product Type combo box as ''DTS:X Ultra''
							5. Fill valid value into all required fields
							6. Select Line out at Audio Route combo box
							6. Select Over ear headphone at Audio Route combo box
							7. Upload a valid single tuning file with 3 route line-out and usb
							8. Click on Save button
							9. Click on Public  button
							10. Click "Complete profile" link
							11. Open complete by using convert tools
							VP: The complete profile include type : "over-ear" and route: AR_OUT_LINEOUT,AR_OUT_USB


						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Select  Eagle Version : Eagle 2.0
//						5. Select Product type: DTS:X Ultra
//						6. Enter data on all remain necessary fields
//						7. At Standard Accessories combobox, select " Over ear headphones" and then Upload a valid single tuning file with 3 route line-out and usb
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_High.getType());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
						appDeviceControl.uploadFile(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Standard_Accessories_Tuning_Combined_LineOut_USB_ver2.getName());
//						8. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						VP: Single tuning uploaded, is displayed on Device detail page as a link
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceEdit.UPLOADED_CUSTOM_TUNNING_NAME_VER20), DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName());
//						9. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						10. Click "Complete profile" link
						appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK);
//						11. Open complete by using convert tools
//						VP: The complete profile include type : "over-ear" and route: AR_OUT_LINEOUT, AR_OUT_BLUETOOTH,AR_OUT_USB
						appDeviceControl.click(DeviceInfo.DELETE);
						Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName() , AR_OUT_LINEOUT));
						Assert.assertTrue(appDeviceControl.checkModelNameAndRoute(device_UUID,DeviceEdit.Standard_Accessories.Over_Ear_Headphones.getName() , AR_OUT_USB));
					}
					
					@Test
					public void ACraeteDevice20(){
						// 1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
						// 2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
						// 3. Click  on an earlier versions device supported eagle v1 and Ultra product type	
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
						Hashtable<String, String> appDevice = TestData.appDevicePublishEagleV2();
						appDevice.remove("save");
						appDeviceControl.createNewDeviceEagle20(DeviceEdit.getHash(), appDevice);
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						appDeviceControl.click(DeviceInfo.DELETE);
					}
					@Test
					public void ACraeteDevice14(){
						// 1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
						// 2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
						// 3. Click  on an earlier versions device supported eagle v1 and Ultra product type	
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
						Hashtable<String, String> appDevice = TestData.appDevicePublish();
						appDevice.remove("save");
					//	appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), appDevice);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						appDeviceControl.click(DeviceEdit.SAVE);
						appDeviceControl.click(DeviceInfo.PUBLISH);
						Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), appDevice.get("name"));
						appDeviceControl.click(DeviceInfo.DELETE);
					}	
					@Test
					public void TC640DADD_136_137_138_139_140(){ // this ticket include those test case: 136, 137, 138, 1391, 140
						
						Reporter.log("Verify with eagle 2.0 new design, product type ''DTS:X Premium' have Default Audio Route include Speaker mode Attached0: Default, Attached 1: Bypass/off, Attached 2: Music, Attached 3: Movies, Attached 4: Game");
						/*
						 *  1. Log into DTS portal as a DTS user
							2. Navigate to "Apps & Devices" page
							3. Click  on ''Add App or Device''
							4. Leave the Product Type combo box as ''DTS:X Premium'
							5. Uploading default audio route have Speaker mode: Attached0: Default,Attached 1: Bypass/off, Attached 2: Music, Attached 3: Movies, Attached 4: Game tuning
							6. Fill valid value into all required fields
							7. Click on Save button
							8. Click on Public  button
							9. Check those offline databases
							10. Click "Download Offline Database" link
							11. Open the offline databse file by SQLite
							12. Navigate to Product table in SQLite
							VP: Have Name Attached0: Default,Attached 1: Bypass/off, Attached 2: Music, Attached 3: Movies, Attached 4: Game
							13. Navigate to Asset table in SQLite (cannot be do this step)
							VP: Get Blob file of Attached0: Default and check blob file include route AR_OUT_ATTACHED0
							


						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Leave the Product Type combo box as ''DTS:X Premium'
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
//						5. Uploading default audio route have Speaker mode: Attached0: Default,Attached 1: Bypass/off, Attached 2: Music, Attached 3: Movies, Attached 4: Game tuning
//						appDeviceControl.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, AddEditProductModel.FileUpload.Default_Headphone_Tuning_Exptune_Files.getName());
//						6. Fill valid value into all required fields
						Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
						Hashtable<String,String> deviceData = TestData.appDevicePublishSingleFile();
						deviceData.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), deviceData);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
//						7. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						8. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						9. Check those offline databases
//						10. Click "Download Offline Database" link
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption("Refresh");
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						11.Open the offline databse file by SQLite
//						12. Navigate to Product table in SQLite
//						VP: Have Name Attached0: Default,Attached 1: Bypass/off, Attached 2: Music, Attached 3: Movies, Attached 4: Game
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where Name = 'Internal Speakers (Default)' and SubType = 'Attached0'", "DTSCSAssetId", null);
						Assert.assertTrue(ColumnData.size()>0);
						ArrayList<String> ColumnData_1 = sqlite.getColumnData("select DTSCSAssetId from Product where Name = 'Internal Speakers (Off)' and SubType = 'Attached1'", "DTSCSAssetId", null);
						Assert.assertTrue(ColumnData_1.size()>0);
						ArrayList<String> ColumnData_2 = sqlite.getColumnData("select DTSCSAssetId from Product where Name = 'Internal Speakers (mode 1)' and SubType = 'Attached2'", "DTSCSAssetId", null);
						Assert.assertTrue(ColumnData_2.size()>0);
						ArrayList<String> ColumnData_3 = sqlite.getColumnData("select DTSCSAssetId from Product where Name = 'Internal Speakers (mode 2)' and SubType = 'Attached3'", "DTSCSAssetId", null);
						Assert.assertTrue(ColumnData_3.size()>0);
						ArrayList<String> ColumnData_4 = sqlite.getColumnData("select DTSCSAssetId from Product where Name = 'Internal Speakers (mode 3)' and SubType = 'Attached4'", "DTSCSAssetId", null);
						Assert.assertTrue(ColumnData_4.size()>0);
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_141_142_143_144(){ // this ticket include those test case: 141, 142, 143, 144
						
						Reporter.log("Verify with eagle 2.0 new design,product type ''DTS:X Premium' have Default Audio Route include Headphone mode Attached0: Default, Attached 2: Music, Attached 3: Movies, Attached 4: Game");
						/*
						 *  1. Log into DTS portal as a DTS user
							2. Navigate to "Apps & Devices" page
							3. Click  on ''Add App or Device''
							4. Leave the Product Type combo box as ''DTS:X Premium'
							5. Uploading default audio route have Speaker mode: Attached0: Default, Attached 2: Music, Attached 3: Movies, Attached 4: Game tuning
							6. Fill valid value into all required fields
							7. Click on Save button
							8. Click on Public  button
							9. Check those offline databases
							10. Click "Download Offline Database" link
							11. Open the offline databse file by SQLite
							12. Navigate to Product table in SQLite
							VP: Have Name Attached0: Default, Attached 2: Music, Attached 3: Movies, Attached 4: Game
							13. Navigate to Asset table in SQLite (cannot be do this step)
							VP: Get Blob file of Attached0: Default and check blob file include route AR_OUT_ATTACHED0
							


						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Leave the Product Type combo box as ''DTS:X Premium'
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
//						5. Uploading default audio route have Speaker mode: Attached0: Default, Attached 2: Music, Attached 3: Movies, Attached 4: Game tuning
//						appDeviceControl.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, AddEditProductModel.FileUpload.Default_Headphone_Tuning_Exptune_Files.getName());
//						6. Fill valid value into all required fields
						Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
						Hashtable<String,String> deviceData = TestData.appDevicePublishSingleFile();
						deviceData.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), deviceData);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
//						7. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						8. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						9. Check those offline databases
//						10. Click "Download Offline Database" link
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption("Refresh");
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						11.Open the offline databse file by SQLite
//						12. Navigate to Product table in SQLite
//						VP: Have Name Attached0: Default, Attached 2: Music, Attached 3: Movies, Attached 4: Game
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where Name = 'Device Headphone (Attached 2 - Music)' and SubType = 'Attached2'", "DTSCSAssetId", null);
						Assert.assertTrue(ColumnData.size()>0);
						ArrayList<String> ColumnData_1 = sqlite.getColumnData("select DTSCSAssetId from Product where Name = 'Device Headphone (Attached 3 - Movies)' and SubType = 'Attached3'", "DTSCSAssetId", null);
						Assert.assertTrue(ColumnData_1.size()>0);
						ArrayList<String> ColumnData_2 = sqlite.getColumnData("select DTSCSAssetId from Product where Name = 'Device Headphone (Attached 4 - Game)' and SubType = 'Attached4'", "DTSCSAssetId", null);
						Assert.assertTrue(ColumnData_2.size()>0);
						ArrayList<String> ColumnData_3 = sqlite.getColumnData("select DTSCSAssetId from Product where Name = 'Device Headphone (Attached 0 - Default)' and SubType = 'Attached0'", "DTSCSAssetId", null);
						Assert.assertTrue(ColumnData_3.size()>0);
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_145(){
						appDeviceControl.addLog("ID : TC640DADD_145 : Verify with eagle 2.0 new design, product type ''DTS:X Premium' include new Speaker modes and Headphone modes on complete profile");
						/*
						 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
							1. Log into DTS portal as DTS admin
							2. Navigate to "Apps & Devices" page
							3. Click “Add App or Device”
							4. Choose product type ''DTS:X Premium'
							5. Uploading default audio route include new Speaker modes and Headphone modes
							6. Fill valid value into all required fields	
							7. Click on Save button
							8. Click on Public  button
							9. Click on “Complete profile” link
							VP: The complete profile include those speaker route as below:
								AR_OUT_ATTACHED_DEFAULT
								AR_OUT_ATTACHED1
								AR_OUT_ATTACHED2
								AR_OUT_ATTACHED3
								AR_OUT_ATTACHED4
							And headphone route:
								AR_OUT_DEVICE_ACCESSORY for Attached0: DefaultAR_OUT_DEVICE_ACCESSORY for Attached2: Music
								AR_OUT_DEVICE_ACCESSORY for Attached3: Movies
								AR_OUT_DEVICE_ACCESSORY for Attached4: Game

						*/
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Leave the Product Type combo box as ''DTS:X Premium'
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
//						5. Uploading default audio route include new Speaker modes and Headphone modes
//						6. Fill valid value into all required fields
						Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
						Hashtable<String,String> deviceData = TestData.appDevicePublishSingleFile();
						deviceData.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), deviceData);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
//						7. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						8. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
//						9. Download the complete profile	
						String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
						boolean isDownloaded = appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK);
						Assert.assertTrue(isDownloaded);
//						String profileName = "profile_"+dts_id+".dtscs";
						/*VP: The complete profile include those speaker route as below:
							AR_OUT_ATTACHED_DEFAULT
							AR_OUT_ATTACHED1
							AR_OUT_ATTACHED2
							AR_OUT_ATTACHED3
							AR_OUT_ATTACHED4
						And headphone route:
							AR_OUT_DEVICE_ACCESSORY for Attached0: DefaultAR_OUT_DEVICE_ACCESSORY for Attached2: Music
							AR_OUT_DEVICE_ACCESSORY for Attached3: Movies
							AR_OUT_DEVICE_ACCESSORY for Attached4: Game*/
						Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_DEVICE_ACCESSORY"));
						Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_ATTACHED1"));
						Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_ATTACHED2"));
						Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_ATTACHED3"));
						Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_ATTACHED4"));
						//Delete device
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_146(){
						appDeviceControl.addLog("ID : TC640DADD_146 : Verify with eagle 1.4, product type ''DTS:X Premium' include new Speaker modes and Headphone modes on complete profile");
						/*
							1. Log into DTS portal as DTS admin
							2. Navigate to "Apps & Devices" page
							3. Click “Add App or Device”
							4. Choose product type ''HPX Medium'
							5. Leave the Eagle version 1.4
							6. Select all route Speaker modes and Headphone modes at Audio Route combo box
							7. Upload a valid single tuning
							8. Fill valid value into all required fields
							9. Click on Save button
							10. Click on Public  button
							11. Click on “Complete profile” link
							VP: The complete profile include those speaker route as below:
								AR_OUT_ATTACHED_DEFAULT
								AR_OUT_ATTACHED1
								AR_OUT_ATTACHED2
								AR_OUT_ATTACHED3
								AR_OUT_ATTACHED4
							And headphone route:
								AR_OUT_DEVICE_ACCESSORY for Attached0: DefaultAR_OUT_DEVICE_ACCESSORY for Attached2: Music
								AR_OUT_DEVICE_ACCESSORY for Attached3: Movies
								AR_OUT_DEVICE_ACCESSORY for Attached4: Game

						*/
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Leave the Product Type combo box as ''HPX Medium'
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
//						5. Leave the Eagle version 1.4
//						6. Fill valid value into all required fields
						Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
						Hashtable<String,String> deviceData = TestData.appDevicePublishMedium();
						deviceData.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), deviceData);
//						6. Select all route Speaker modes and Headphone modes at Audio Route combo box
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached1_Internal_Speakers_Off.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached1InternalSpeakersOff_Custom.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached0_Internal_Speakers.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Custom.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached2_Internal_Speakers_Mode_1.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached2InternalSpeakersMusic_Custom.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached3_Internal_Speakers_Mode_2.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached3InternalSpeakersMovies_Custom.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached4_Internal_Speakers_Mode_3.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached4InternalSpeakersGames_Custom.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Device_Headphone.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_0_Default.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach0.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Device_Headphone.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_2_Music.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach2.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Device_Headphone.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_3_Movies.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach3.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Device_Headphone.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_4_Game.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach4.getName());
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
//						7. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						8. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
//						9. Download the complete profile	
						String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
						boolean isDownloaded = appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK);
						Assert.assertTrue(isDownloaded);
//						String profileName = "profile_"+dts_id+".dtscs";
						/*VP: The complete profile include those speaker route as below:
							AR_OUT_ATTACHED_DEFAULT
							AR_OUT_ATTACHED1
							AR_OUT_ATTACHED2
							AR_OUT_ATTACHED3
							AR_OUT_ATTACHED4
						And headphone route:
							AR_OUT_DEVICE_ACCESSORY for Attached0: DefaultAR_OUT_DEVICE_ACCESSORY for Attached2: Music
							AR_OUT_DEVICE_ACCESSORY for Attached3: Movies
							AR_OUT_DEVICE_ACCESSORY for Attached4: Game*/
						Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_DEVICE_ACCESSORY"));
						Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_ATTACHED1"));
						Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_ATTACHED2"));
						Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_ATTACHED3"));
						Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_ATTACHED4"));
						//Delete device
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
/*					@Test
					public void TC640DADD_147(){
						appDeviceControl.addLog("ID : TC640DADD_147 : Verify with eagle 2.0 old design, product type ''DTS:X Premium' include new Speaker modes and Headphone modes on complete profile");*/
					@Test
					public void TC640DADD_148_149_150_151_152(){
						appDeviceControl.addLog("ID : TC640DADD_148_149_150_151_152 : Verify with eagle 1.4 product type ''DTS:X Premium' have Default Audio Route include Speaker mode Attached0: Default, Attached1: Bypass/off, Attached 2: Music, Attached 3: Movies, Attached 4: Game");
						/*
							1. Log into DTS portal as DTS admin
							2. Navigate to "Apps & Devices" page
							3. Click “Add App or Device”
							4. Choose product type ''HPX Medium'
							5. Leave the Eagle version 1.4
							6. Select all speaker mode at Audio Route combo box
							7. Upload a valid single tuning
							8. Fill valid value into all required fields
							9. Click on Save button
							10. Click on Public  button
							11. Check those offline databases
							12. Click "Download Offline Database" link
							13. Open the offline databse file by SQLite
							14. Navigate to Product table  in SQLite
							VP: Have Name Attached0: Default, Attached1: Bypass/off, Attached 2: Music, Attached 3: Movies, Attached 4: Game
							15. Navigate to Asset table in SQLite
							VP: Get Blob file of Attached0: Default and check blob file include route AR_OUT_ATTACHED0
							VP: Get Blob file of Attached1: Bypass/off and check blob file include route AR_OUT_ATTACHED1
							VP: Get Blob file of Attached 2: Music and check blob file include route AR_OUT_ATTACHED2
							VP: Get Blob file of Attached 3: Movies and check blob file include route AR_OUT_ATTACHED3
							VP: Get Blob file of Attached 4: Game and check blob file include route AR_OUT_ATTACHED4

						*/
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Leave the Product Type combo box as ''HPX Medium'
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
//						5. Leave the Eagle version 1.4
//						6. Select all speaker mode at Audio Route combo box
//						7. Upload a valid single tuning
//						8. Fill valid value into all required fields
						Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
						Hashtable<String,String> deviceData = TestData.appDevicePublishMedium();
						deviceData.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), deviceData);
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached1_Internal_Speakers_Off.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached1InternalSpeakersOff_Custom.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached0_Internal_Speakers.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Custom.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached2_Internal_Speakers_Mode_1.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached2InternalSpeakersMusic_Custom.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached3_Internal_Speakers_Mode_2.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached3InternalSpeakersMovies_Custom.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached4_Internal_Speakers_Mode_3.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached4InternalSpeakersGames_Custom.getName());
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
//						9. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						10. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						11. Check those offline databases
//						12. Click "Download Offline Database" link
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption("Refresh");
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						13. Open the offline databse file by SQLite
//						14. Navigate to Product table  in SQLite
//						VP: Have Name Attached0: Default, Attached1: Bypass/off, Attached 2: Music, Attached 3: Movies, Attached 4: Game
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product where Name = 'Internal Speakers (Default)'", "SubType", "Attached0"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product where Name = 'Internal Speakers (Off)'", "SubType", "Attached1"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product where Name = 'Internal Speakers (mode 1)'", "SubType", "Attached2"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product where Name = 'Internal Speakers (mode 2)'", "SubType", "Attached3"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product where Name = 'Internal Speakers (mode 3)'", "SubType", "Attached4"));
						//Delete device
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
/*					public void TC640DADD_153_154_155_156_157(){Donot do automation on this ticket due to eagle 2/0 old design
						appDeviceControl.addLog("ID : TC640DADD_153_154_155_156_157 : Verify with eagle 2.0 old design product type ''DTS:X Premium' have Default Audio Route include Speaker mode Attached0: Default, Attached1: Bypass/off, Attached 2: Music, Attached 3: Movies, Attached 4: Game");
				*/	
					@Test
					public void TC640DADD_158_159_160_161(){
						appDeviceControl.addLog("ID : TC640DADD_158_159_160_161 : Verify with eagle 1.4 product type ''DTS Premium' have  Headphone mode Attached0: Default, Attached 2: Music, Attached 3: Movies, Attached 4: Game");
						/*
							1. Log into DTS portal as DTS admin
							2. Navigate to "Apps & Devices" page
							3. Click “Add App or Device”
							4. Choose product type ''HPX Medium'
							5. Leave the Eagle version 1.4
							6. Select ''Device Headphone Settings'' at Audio Route combo box
							7. Select ''Attached0: Default, Attached 2: Music, Attached 3: Movies, Attached 4: Game'' at content modes combo box
							8. Upload a valid single tuning
							9. Fill valid value into all required fields
							10. Click on Save button
							11. Click on Public  button
							12. Check those offline databases
							13. Click "Download Offline Database" link
							14. Open the offline databse file by SQLite
							15. Navigate to Product table  in SQLite
							VP: Have Name Device Headphone (Attached 0 - Default), Device Headphone (Attached 2 - Music), Device Headphone (Attached 3 - Movies), Device Headphone (Attached 4 - Game)
							15. Navigate to Asset table in SQLite
							VP: Get Blob file of Attached0: Default and check blob file include route AR_OUT_DEVICE_ACCESSORY
							VP: Get Blob file of Attached 2: Music and check blob file include route AR_OUT_DEVICE_ACCESSORY
							VP: Get Blob file of Attached 3: Movies and check blob file include route AR_OUT_DEVICE_ACCESSORY
							VP: Get Blob file of Attached 4: Game and check blob file include route AR_OUT_DEVICE_ACCESSORY

						*/
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Leave the Product Type combo box as ''HPX Medium'
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
//						6. Select ''Device Headphone Settings'' at Audio Route combo box
//						7. Select ''Attached0: Default, Attached 2: Music, Attached 3: Movies, Attached 4: Game'' at content modes combo box
//						8. Upload a valid single tuning
//						9. Fill valid value into all required fields
						Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
						Hashtable<String,String> deviceData = TestData.appDevicePublishMedium();
						deviceData.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), deviceData);
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Device_Headphone.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_0_Default.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach0.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Device_Headphone.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_2_Music.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach2.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Device_Headphone.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_3_Movies.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach3.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Device_Headphone.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_4_Game.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach4.getName());
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
//						10. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						11. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						12. Check those offline databases
//						13. Click "Download Offline Database" link
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption("Refresh");
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						14. Open the offline databse file by SQLite
//						15. Navigate to Product table  in SQLite
//						VP: Have Name Device Headphone (Attached 0 - Default), Device Headphone (Attached 2 - Music), Device Headphone (Attached 3 - Movies), Device Headphone (Attached 4 - Game)
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product where Name = 'Device Headphone (Attached 0 - Default)'", "SubType", "Attached0"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product where Name = 'Device Headphone (Attached 2 - Music)'", "SubType", "Attached2"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product where Name = 'Device Headphone (Attached 3 - Movies)'", "SubType", "Attached3"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product where Name = 'Device Headphone (Attached 4 - Game)'", "SubType", "Attached4"));
						//Delete device
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
/*					@Test
					public void TC640DADD_162_163_164_165(){ Donot do automation on this ticket due to eagle 2/0 old design
						appDeviceControl.addLog("ID : TC640DADD_162_163_164_165 : Verify with eagle 1.4 product type ''DTS Premium' have  Headphone mode Attached0: Default, Attached 2: Music, Attached 3: Movies, Attached 4: Game");*/
					/*					@Test
					public void TC640DADD_162_163_164_165(){ Donot do automation on this ticket due to eagle 2/0 old design
						appDeviceControl.addLog("ID : TC640DADD_162_163_164_165 : Verify with eagle 2.0 old design product type ''DTS Premium' do not have Combo0 on DB");*/
					@Test
					public void TC640DADD_167(){
						appDeviceControl.addLog("ID : TC640DADD_167 : Verify with eagle 1.4 product type ''DTS Premium' do not have Combo0 on DB");
						/*
							1. Log into DTS portal as DTS admin
							2. Navigate to "Apps & Devices" page
							3. Click “Add App or Device”
							4. Choose product type ''HPX Medium'
							5. Leave the Eagle version 1.4
							6. Fill valid value into all required fields
							7. Click on Save button
							8. Click on Public  button
							9. Check those offline databases
							10. Click "Download Offline Database" link
							11. Open the offline databse file by SQLite
							12. Navigate to Product table  in SQLite
							VP: The table do not have type ''order'' and subtype ''Combo0''

						*/
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Leave the Product Type combo box as ''HPX Medium'
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Medium.getType());
//						5. Leave the Eagle version 1.4
//						6. Fill valid value into all required fields
						Hashtable<String,String> deviceData = TestData.appDevicePublishMedium();
						Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
						deviceData.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), deviceData);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
//						7. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						8. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						9. Check those offline databases
//						10. Click "Download Offline Database" link
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption("Refresh");
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						11. Open the offline databse file by SQLite
//						12. Navigate to Product table  in SQLite
//						VP: The table do not have type ''order'' and subtype ''Combo0''
						Assert.assertFalse(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product where Type = 'Other)'", "SubType", "Combo0"));
						//Delete device
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_168_169_170_171(){ // this ticket include those test case: 168, 169, 170, 171
						Reporter.log("Verify with eagle 2.0 new design, product type ''DTS Audio Processing'' have Default Audio Route include Speaker mode Attached 1: Bypass/off, Attached 2: Music, Attached 3: Movies, Attached 4: Game");
						/*
						 *  1. Log into DTS portal as a DTS user
							2. Navigate to "Apps & Devices" page
							3. Click  on ''Add App or Device''
							4. Leave the Product Type combo box as ''DTS Audio Processing''
							5. Uploading default audio route have Speaker mode: Attached 1: Bypass/off, Attached 2: Music, Attached 3: Movies, Attached 4: Game tuning
							6. Fill valid value into all required fields
							7. Click on Save button
							8. Click on Public  button
							9. Check those offline databases
							10. Click "Download Offline Database" link
							11. Open the offline databse file by SQLite
							12. Navigate to Product table in SQLite
							VP: Have Name Default,Attached 1: Bypass/off, Attached 2: Music, Attached 3: Movies, Attached 4: Game
							13. Navigate to Asset table in SQLite (cannot be do this step)
						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Leave the Product Type combo box as ''DTS Audio Processing''
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
//						5. Uploading default audio route have Speaker mode: Attached 1: Bypass/off, Attached 2: Music, Attached 3: Movies, Attached 4: Game tuning
//						appDeviceControl.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, AddEditProductModel.FileUpload.Default_Headphone_Tuning_Exptune_Files.getName());
//						6. Fill valid value into all required fields
						Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
						Hashtable<String,String> deviceData = TestData.appDevicePublishSingleFileAudioProcessing();
						deviceData.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), deviceData);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
//						7. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						8. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						9. Check those offline databases
//						10. Click "Download Offline Database" link
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption("Refresh");
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						11.Open the offline databse file by SQLite
//						12. Navigate to Product table in SQLite
//						VP: Have Name Attached 1: Bypass/off, Attached 2: Music, Attached 3: Movies, Attached 4: Game
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData_1 = sqlite.getColumnData("select DTSCSAssetId from Product where Name = 'Internal Speakers (Off)' and SubType = 'Attached1'", "DTSCSAssetId", null);
						Assert.assertTrue(ColumnData_1.size()>0);
						ArrayList<String> ColumnData_2 = sqlite.getColumnData("select DTSCSAssetId from Product where Name = 'Internal Speakers (mode 1)' and SubType = 'Attached2'", "DTSCSAssetId", null);
						Assert.assertTrue(ColumnData_2.size()>0);
						ArrayList<String> ColumnData_3 = sqlite.getColumnData("select DTSCSAssetId from Product where Name = 'Internal Speakers (mode 2)' and SubType = 'Attached3'", "DTSCSAssetId", null);
						Assert.assertTrue(ColumnData_3.size()>0);
						ArrayList<String> ColumnData_4 = sqlite.getColumnData("select DTSCSAssetId from Product where Name = 'Internal Speakers (mode 3)' and SubType = 'Attached4'", "DTSCSAssetId", null);
						Assert.assertTrue(ColumnData_4.size()>0);
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_172_173_174(){ // this ticket include those test case: 172, 173, 174
					
						Reporter.log("Verify with eagle 2.0 new design,product type ''DTS:X Premium' have Default Audio Route include Headphone mode Attached 2: Music, Attached 3: Movies, Attached 4: Game");
						/*
						 *  1. Log into DTS portal as a DTS user
							2. Navigate to "Apps & Devices" page
							3. Click  on ''Add App or Device''
							4. Leave the Product Type combo box as ''DTS Audio Processing'
							5. Uploading default audio route have Speaker mode: Attached 2: Music, Attached 3: Movies, Attached 4: Game tuning
							6. Fill valid value into all required fields
							7. Click on Save button
							8. Click on Public  button
							9. Check those offline databases
							10. Click "Download Offline Database" link
							11. Open the offline databse file by SQLite
							12. Navigate to Product table in SQLite
							VP: Have Name Attached 2: Music, Attached 3: Movies, Attached 4: Game
							13. Navigate to Asset table in SQLite (cannot be do this step)


						 */
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Leave the Product Type combo box as ''DTS:X DTS Audio Processing'
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
//						5. Uploading default audio route have Speaker mode: Attached 2: Music, Attached 3: Movies, Attached 4: Game tuning
//						appDeviceControl.uploadFile(DeviceEdit.ADD_DEFAULT_AUDIO_ROUTE, AddEditProductModel.FileUpload.Default_Headphone_Tuning_Exptune_Files.getName());
//						6. Fill valid value into all required fields
						Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
						Hashtable<String,String> deviceData = TestData.appDevicePublishSingleFileAudioProcessing();
						deviceData.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), deviceData);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
//						7. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						8. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						9. Check those offline databases
//						10. Click "Download Offline Database" link
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption("Refresh");
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						11.Open the offline databse file by SQLite
//						12. Navigate to Product table in SQLite
//						VP: Have Name Attached 2: Music, Attached 3: Movies, Attached 4: Game
						SQLiteDbUtil sqlite = new SQLiteDbUtil(appDeviceControl.getOfflineDBName(device_UUID));
						ArrayList<String> ColumnData = sqlite.getColumnData("select DTSCSAssetId from Product where Name = 'Device Headphone (Attached 2 - Music)' and SubType = 'Attached2'", "DTSCSAssetId", null);
						Assert.assertTrue(ColumnData.size()>0);
						ArrayList<String> ColumnData_1 = sqlite.getColumnData("select DTSCSAssetId from Product where Name = 'Device Headphone (Attached 3 - Movies)' and SubType = 'Attached3'", "DTSCSAssetId", null);
						Assert.assertTrue(ColumnData_1.size()>0);
						ArrayList<String> ColumnData_2 = sqlite.getColumnData("select DTSCSAssetId from Product where Name = 'Device Headphone (Attached 4 - Game)' and SubType = 'Attached4'", "DTSCSAssetId", null);
						Assert.assertTrue(ColumnData_2.size()>0);
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_175(){
						appDeviceControl.addLog("ID : TC640DADD_175 : Verify with eagle 2.0 new design, product type ''DTS Audio Processing' include new Speaker modes and Headphone modes on complete profile");
						/*
						 	Pre-Condition: the DTS user has "Add and manage authorized Products" privilege.
							1. Log into DTS portal as DTS admin
							2. Navigate to "Apps & Devices" page
							3. Click “Add App or Device”
							4. Choose product type ''DTS Audio Processing'
							5. Uploading default audio route include new Speaker modes and Headphone modes
							6. Fill valid value into all required fields	
							7. Click on Save button
							8. Click on Public  button
							9. Click on “Complete profile” link
							VP: The complete profile include those speaker route as below:
								AR_OUT_ATTACHED1
								AR_OUT_ATTACHED2
								AR_OUT_ATTACHED3
								AR_OUT_ATTACHED4
							And headphone route:
								AR_OUT_DEVICE_ACCESSORY for Attached2: Music
								AR_OUT_DEVICE_ACCESSORY for Attached3: Movies
								AR_OUT_DEVICE_ACCESSORY for Attached4: Game

						*/
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Leave the Product Type combo box as ''DTS Audio Processing'
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
//						5. Uploading default audio route include new Speaker modes and Headphone modes
//						6. Fill valid value into all required fields
						Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
						Hashtable<String,String> deviceData = TestData.appDevicePublishSingleFile();
						deviceData.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), deviceData);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
//						7. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						8. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
//						9. Download the complete profile	
						String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
						boolean isDownloaded = appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK);
						Assert.assertTrue(isDownloaded);
//						String profileName = "profile_"+dts_id+".dtscs";
						/*VP: The complete profile include those speaker route as below:
							AR_OUT_ATTACHED1
							AR_OUT_ATTACHED2
							AR_OUT_ATTACHED3
							AR_OUT_ATTACHED4
						And headphone route:
							AR_OUT_DEVICE_ACCESSORY for Attached2: Music
							AR_OUT_DEVICE_ACCESSORY for Attached3: Movies
							AR_OUT_DEVICE_ACCESSORY for Attached4: Game*/
						Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_DEVICE_ACCESSORY"));
						Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_ATTACHED1"));
						Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_ATTACHED2"));
						Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_ATTACHED3"));
						Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_ATTACHED4"));
						//Delete device
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_176(){
						appDeviceControl.addLog("ID : TC640DADD_176 : Verify with eagle 1.4, product type ''DTS Audio Processing' include new Speaker modes and Headphone modes on complete profile");
						/*
							1. Log into DTS portal as DTS admin
							2. Navigate to "Apps & Devices" page
							3. Click “Add App or Device”
							4. Choose product type ''HPX Low'
							5. Leave the Eagle version 1.4
							6. Select all route Speaker modes and Headphone modes at Audio Route combo box
							7. Upload a valid single tuning
							8. Fill valid value into all required fields
							9. Click on Save button
							10. Click on Public  button
							11. Click on “Complete profile” link
							VP: The complete profile include those speaker route as below:
								AR_OUT_ATTACHED1
								AR_OUT_ATTACHED2
								AR_OUT_ATTACHED3
								AR_OUT_ATTACHED4
							And headphone route:
								AR_OUT_DEVICE_ACCESSORY for Attached2: Music
								AR_OUT_DEVICE_ACCESSORY for Attached3: Movies
								AR_OUT_DEVICE_ACCESSORY for Attached4: Game

						*/
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Leave the Product Type combo box as ''HPX Low'
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
//						5. Leave the Eagle version 1.4
//						6. Fill valid value into all required fields
						Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
						Hashtable<String,String> deviceData = TestData.appDevicePublishLow();
						deviceData.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), deviceData);
//						6. Select all route Speaker modes and Headphone modes at Audio Route combo box
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached1_Internal_Speakers_Off.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached1InternalSpeakersOff_Custom.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached2_Internal_Speakers_Mode_1.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached2InternalSpeakersMusic_Custom.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached3_Internal_Speakers_Mode_2.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached3InternalSpeakersMovies_Custom.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached4_Internal_Speakers_Mode_3.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached4InternalSpeakersGames_Custom.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Device_Headphone.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Device_Headphone.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_2_Music.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach2.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Device_Headphone.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_3_Movies.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach3.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Device_Headphone.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_4_Game.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach4.getName());
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
//						7. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						8. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
//						9. Download the complete profile	
						String dts_id = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
						boolean isDownloaded = appDeviceControl.downloadFile(DeviceInfo.COMPLETE_PROFILE_LINK);
						Assert.assertTrue(isDownloaded);
//						String profileName = "profile_"+dts_id+".dtscs";
						/*VP: The complete profile include those speaker route as below:
							AR_OUT_ATTACHED1
							AR_OUT_ATTACHED2
							AR_OUT_ATTACHED3
							AR_OUT_ATTACHED4
						And headphone route:
							AR_OUT_DEVICE_ACCESSORY for Attached2: Music
							AR_OUT_DEVICE_ACCESSORY for Attached3: Movies
							AR_OUT_DEVICE_ACCESSORY for Attached4: Game*/
						Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_DEVICE_ACCESSORY"));
						Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_ATTACHED1"));
						Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_ATTACHED2"));
						Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_ATTACHED3"));
						Assert.assertTrue(appDeviceControl.checkRouteFile(dts_id, "AR_OUT_ATTACHED4"));
						//Delete device
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
/*					@Test
					public void TC640DADD_177(){
						appDeviceControl.addLog("ID : TC640DADD_177 : Verify with eagle 2.0 old design, product type ''DTS Audio Processing' include new Speaker modes and Headphone modes on complete profile");*/
					@Test
					public void TC640DADD_178_179_180_181(){
						appDeviceControl.addLog("ID : TC640DADD_178_179_180_181 : Verify with eagle 1.4 product type ''HPX Low' have Default Audio Route include Speaker mode Attached1: Bypass/off, Attached 2: Music, Attached 3: Movies, Attached 4: Game");
						/*
							1. Log into DTS portal as DTS admin
							2. Navigate to "Apps & Devices" page
							3. Click “Add App or Device”
							4. Choose product type ''HPX Low'
							5. Leave the Eagle version 1.4
							6. Select all speaker mode at Audio Route combo box
							7. Upload a valid single tuning
							8. Fill valid value into all required fields
							9. Click on Save button
							10. Click on Public  button
							11. Check those offline databases
							12. Click "Download Offline Database" link
							13. Open the offline databse file by SQLite
							14. Navigate to Product table  in SQLite
							VP: Have Name Attached1: Bypass/off, Attached 2: Music, Attached 3: Movies, Attached 4: Game
							15. Navigate to Asset table in SQLite
							VP: Get Blob file of Attached1: Bypass/off and check blob file include route AR_OUT_ATTACHED1
							VP: Get Blob file of Attached 2: Music and check blob file include route AR_OUT_ATTACHED2
							VP: Get Blob file of Attached 3: Movies and check blob file include route AR_OUT_ATTACHED3
							VP: Get Blob file of Attached 4: Game and check blob file include route AR_OUT_ATTACHED4

						*/
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Leave the Product Type combo box as ''HPX Low'
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
//						5. Leave the Eagle version 1.4
//						6. Select all speaker mode at Audio Route combo box
//						7. Upload a valid single tuning
//						8. Fill valid value into all required fields
						Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
						Hashtable<String,String> deviceData = TestData.appDevicePublishLow();
						deviceData.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), deviceData);
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached1_Internal_Speakers_Off.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached1InternalSpeakersOff_Custom.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached2_Internal_Speakers_Mode_1.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached2InternalSpeakersMusic_Custom.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached3_Internal_Speakers_Mode_2.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached3InternalSpeakersMovies_Custom.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Attached4_Internal_Speakers_Mode_3.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Attached4InternalSpeakersGames_Custom.getName());
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
//						9. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						10. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						11. Check those offline databases
//						12. Click "Download Offline Database" link
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption("Refresh");
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						13. Open the offline databse file by SQLite
//						14. Navigate to Product table  in SQLite
//						VP: Have Name Attached0: Default, Attached1: Bypass/off, Attached 2: Music, Attached 3: Movies, Attached 4: Game
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product where Name = 'Internal Speakers (Off)'", "SubType", "Attached1"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product where Name = 'Internal Speakers (mode 1)'", "SubType", "Attached2"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product where Name = 'Internal Speakers (mode 2)'", "SubType", "Attached3"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product where Name = 'Internal Speakers (mode 3)'", "SubType", "Attached4"));
						//Delete device
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
/*					public void TC640DADD_182_183_184_185(){Donot do automation on this ticket due to eagle 2/0 old design
						appDeviceControl.addLog("ID : TC640DADD_182_183_184_185 : Verify with eagle 2.0 old design product type ''DTS Audio Processing' have Default Audio Route include Speaker mode Attached1: Bypass/off, Attached 2: Music, Attached 3: Movies, Attached 4: Game");
				*/	
					@Test
					public void TC640DADD_186_187_188(){
						appDeviceControl.addLog("ID : TC640DADD_186_187_188 : Verify with eagle 1.4 product type ''HPX Low' have Headphone mode Attached 2: Music, Attached 3: Movies, Attached 4: Game");
						/*
							1. Log into DTS portal as DTS admin
							2. Navigate to "Apps & Devices" page
							3. Click “Add App or Device”
							4. Choose product type ''HPX Low'
							5. Leave the Eagle version 1.4
							6. Select ''Device Headphone Settings'' at Audio Route combo box
							7. Select '' Attached 2: Music, Attached 3: Movies, Attached 4: Game'' at content modes combo box
							8. Upload a valid single tuning
							9. Fill valid value into all required fields
							10. Click on Save button
							11. Click on Public  button
							12. Check those offline databases
							13. Click "Download Offline Database" link
							14. Open the offline databse file by SQLite
							15. Navigate to Product table  in SQLite
							VP: Have Name Device Headphone (Attached 2 - Music), Device Headphone (Attached 3 - Movies), Device Headphone (Attached 4 - Game)
							15. Navigate to Asset table in SQLite
							VP: Get Blob file of Attached 2: Music and check blob file include route AR_OUT_DEVICE_ACCESSORY
							VP: Get Blob file of Attached 3: Movies and check blob file include route AR_OUT_DEVICE_ACCESSORY
							VP: Get Blob file of Attached 4: Game and check blob file include route AR_OUT_DEVICE_ACCESSORY

						*/
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Leave the Product Type combo box as ''HPX Low'
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
//						6. Select ''Device Headphone Settings'' at Audio Route combo box
//						7. Select Attached 2: Music, Attached 3: Movies, Attached 4: Game'' at content modes combo box
//						8. Upload a valid single tuning
//						9. Fill valid value into all required fields
						Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
						Hashtable<String,String> deviceData = TestData.appDevicePublishLow();
						deviceData.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), deviceData);
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Device_Headphone.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Device_Headphone.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_2_Music.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach2.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Device_Headphone.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_3_Movies.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach3.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Device_Headphone.getName());
						appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE, DeviceEdit.Content_Modes_Medium.Attached_4_Game.getName());
						appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING,AddEditProductModel.FileUpload.Device_Headphone_Attach4.getName());
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
//						10. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						11. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						12. Check those offline databases
//						13. Click "Download Offline Database" link
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption("Refresh");
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						14. Open the offline databse file by SQLite
//						15. Navigate to Product table  in SQLite
//						VP: Have Name Device Headphone (Attached 0 - Default), Device Headphone (Attached 2 - Music), Device Headphone (Attached 3 - Movies), Device Headphone (Attached 4 - Game)
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product where Name = 'Device Headphone (Attached 2 - Music)'", "SubType", "Attached2"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product where Name = 'Device Headphone (Attached 3 - Movies)'", "SubType", "Attached3"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product where Name = 'Device Headphone (Attached 4 - Game)'", "SubType", "Attached4"));
						//Delete device
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
/*					@Test
					public void TC640DADD_189_190_191(){ Donot do automation on this ticket due to eagle 2/0 old design
						appDeviceControl.addLog("ID : TC640DADD_189_190_191 : Verify with eagle 1.4 product type ''DTS Audio Processing' have  Headphone mode Attached 2: Music, Attached 3: Movies, Attached 4: Game");*/
					/*					@Test
					public void TC640DADD_192(){ Donot do automation on this ticket due to eagle 2/0 old design
						appDeviceControl.addLog("ID : TC640DADD_192 : Verify with eagle 2.0 old design product type ''DTS Audio Processing' do not have Combo0 on DB");*/
					@Test
					public void TC640DADD_193(){
						appDeviceControl.addLog("ID : TC640DADD_193 : Verify with eagle 1.4 product type ''HPX Low' do not have Combo0 on DB");
						/*
							1. Log into DTS portal as DTS admin
							2. Navigate to "Apps & Devices" page
							3. Click “Add App or Device”
							4. Choose product type ''HPX Low'
							5. Leave the Eagle version 1.4
							6. Fill valid value into all required fields
							7. Click on Save button
							8. Click on Public  button
							9. Check those offline databases
							10. Click "Download Offline Database" link
							11. Open the offline databse file by SQLite
							12. Navigate to Product table  in SQLite
							VP: The table do not have type ''order'' and subtype ''Combo0''

						*/
//						1. Log into DTS portal as a DTS user successfully	
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//						2. Navigate to "Apps & Devices" page	
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
//						3. Click on "Add App or Device"
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
//						4. Leave the Product Type combo box as ''HPX Low'
						appDeviceControl.selectOptionByName(DeviceEdit.PRODUCT_TYPE, DeviceEdit.Product_Types.HPX_Low.getType());
//						5. Leave the Eagle version 1.4
//						6. Fill valid value into all required fields
						Assert.assertTrue(appDeviceControl.existsElement(DeviceEdit.getHash()));
						Hashtable<String,String> deviceData = TestData.appDevicePublishLow();
						deviceData.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), deviceData);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
//						7. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						8. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
//						9. Check those offline databases
//						10. Click "Download Offline Database" link
						appDeviceControl.click(DeviceInfo.REFRESH_OFFLINE_DATABASE);
						appDeviceControl.selectConfirmationDialogOption("Refresh");
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.DOWNLOAD_DATABASE));
//						11. Open the offline databse file by SQLite
//						12. Navigate to Product table  in SQLite
//						VP: The table do not have type ''order'' and subtype ''Combo0''
						Assert.assertFalse(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineDBName(device_UUID), "Select * from Product where Type = 'Other'", "SubType", "Combo0"));
						//Delete device
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_194(){
						appDeviceControl.addLog("ID : TC640DADD_194 : Verify the init database button show up");
						/*
							1. Log into DTS portal as DTS admin
							2. Navigate to "Apps & Devices" page
							3. Add an app or device
							4. Fill valid value into all required fields
							5. Click on Save button
							6. Click on Public  button
							7. Navigate the 640D Device Detail page
							VP: Init database button show up
						*/
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
						// 2. Navigate to "Apps & Devices" page
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
						Assert.assertTrue(appDeviceControl.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
						// 3. Add an app or device successfully
						// Click on Add App or Device link
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
						// 4. Fill valid value into all required fields
						Hashtable<String,String> data = TestData.appDevicePublishSingleFile();
						data.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						// 5. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						// 6. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						// 7. Navigate the 640D Device Detail page
						// VP: Init database button show up
						Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.INIT_DATABASE_LINK));
						//Delete device
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_195(){
						appDeviceControl.addLog("ID : TC640DADD_195 : Verify can download init database successfully with ealge 2.0 single file");
						/*
							1. Log into DTS portal as DTS admin
							2. Navigate to "Apps & Devices" page
							3. Add an app or device
							4. Leave the Eagle version as ''Eagle 2.0''
							5. Fill valid value into all required fields
							6. Click on Save button
							7. Click on Public  button
							8. Navigate the 640D Device Detail page
							VP: Init database button show up
						*/
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
						// 2. Navigate to "Apps & Devices" page
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
						Assert.assertTrue(appDeviceControl.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
						// 3. Add an app or device successfully
						// Click on Add App or Device link
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
						// 4. Fill valid value into all required fields
						Hashtable<String,String> data = TestData.appDevicePublishSingleFile();
						data.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						// 5. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						// 6. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						// 7. Navigate the 640D Device Detail page
						// VP: Canbe download init database successfully
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.INIT_DATABASE_LINK));
						//Delete device
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_196(){
						appDeviceControl.addLog("ID : TC640DADD_195 : Verify can download init database successfully with ealge 2.0 single file");
						/*
							1. Log into DTS portal as DTS admin
							2. Navigate to "Apps & Devices" page
							3. Add an app or device
							4. Leave the Eagle version as ''Eagle 1.4''
							5. Fill valid value into all required fields
							6. Click on Save button
							7. Click on Public  button
							8. Navigate the 640D Device Detail page
							VP: Canbe download init database successfully
						*/
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
						// 2. Navigate to "Apps & Devices" page
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
						Assert.assertTrue(appDeviceControl.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
						// 3. Add an app or device successfully
						// Click on Add App or Device link
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
						// 4. Fill valid value into all required fields
						Hashtable<String,String> data = TestData.appDevicePublish();
						data.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						// 5. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						// 6. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						// 7. Navigate the 640D Device Detail page
						// VP: Canbe download init database successfully
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.INIT_DATABASE_LINK));
						//Delete device
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
/*					@Test
					public void TC640DADD_197(){
						appDeviceControl.addLog("ID : TC640DADD_197 : Verify can download init database successfully with ealge 2.0 old design");*/
					@Test
					public void TC640DADD_198(){
						appDeviceControl.addLog("ID : TC640DADD_198 : Verify init database of DTS:X Ultra with eagle 2.0 single file  have 4 table : Cache, Models, Profiles, Workspace");
						/*
							1. Log into DTS portal as DTS admin
							2. Navigate to "Apps & Devices" page
							3. Add an app or device
							4. Choose product type ''DTS:X Ultra'
							5. Fill valid value into all required fields
							6. Click on Save button
							7. Click on Public  button
							8. Click to ''Init Database'' button
							9.Open the offline databse file by SQLite
							VP: init database have 4 table: Cache, Models, Profiles, Workspace
						*/
						// 1. Log into DTS portal as DTS admin
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
						// 2. Navigate to "Apps & Devices" page
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
						Assert.assertTrue(appDeviceControl.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
						// 3. Add an app or device successfully
						// Click on Add App or Device link
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
						// 4. Choose product type ''DTS:X Ultra'
						// 5. Fill valid value into all required fields
						Hashtable<String,String> data = TestData.appDevicePublishEagleV2();
						data.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						// 6. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						// 7. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
						// 8. Click to ''Init Database'' button
						// 9.Open the offline databse file by SQLite
						// VP: init database have 4 table: Cache, Models, Profiles, Workspace
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.INIT_DATABASE_LINK));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Cache where key = 'https://s3.amazonaws.com/pp.dts.com/cdn/saap/default/roommodels.dtscs'", "mime", "application/octet-stream"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Models where key = 'default'", "mime", "application/json"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Profiles where key = '0570bf57-be6a-4115-bdff-bace2fb1d03d'", "mime", "application/octet-stream"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Workspace where key = '0570bf57-be6a-4115-bdff-bace2fb1d03d'", "mime", "application/octet-stream"));
						//Delete device
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_199(){
						appDeviceControl.addLog("ID : TC640DADD_199 : Verify init database of DTS:X Premium with eagle 2.0 single file  have 4 table : Cache, Models, Profiles, Workspace");
						/*
							1. Log into DTS portal as DTS admin
							2. Navigate to "Apps & Devices" page
							3. Add an app or device
							4. Choose product type ''DTS:X Premium'
							5. Fill valid value into all required fields
							6. Click on Save button
							7. Click on Public  button
							8. Click to ''Init Database'' button
							9.Open the offline databse file by SQLite
							VP: init database have 4 table: Cache, Models, Profiles, Workspace
						*/
						// 1. Log into DTS portal as DTS admin
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
						// 2. Navigate to "Apps & Devices" page
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
						Assert.assertTrue(appDeviceControl.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
						// 3. Add an app or device successfully
						// Click on Add App or Device link
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
						// 4. Choose product type ''DTS:X Premium'
						// 5. Fill valid value into all required fields
						Hashtable<String,String> data = TestData.appDevicePublishSingleFilePremium();
						data.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						// 6. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						// 7. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
						// 8. Click to ''Init Database'' button
						// 9.Open the offline databse file by SQLite
						// VP: init database have 4 table: Cache, Models, Profiles, Workspace
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.INIT_DATABASE_LINK));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Cache where key = 'https://s3.amazonaws.com/pp.dts.com/cdn/saap/default/roommodels.dtscs'", "mime", "application/octet-stream"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Models where key = 'default'", "mime", "application/json"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Profiles where key = '0570bf57-be6a-4115-bdff-bace2fb1d03d'", "mime", "application/octet-stream"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Workspace where key = '0570bf57-be6a-4115-bdff-bace2fb1d03d'", "mime", "application/octet-stream"));
						//Delete device
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_200(){
						appDeviceControl.addLog("ID : TC640DADD_200 : Verify init database of DTS Audio Processing with eagle 2.0 single file  have 4 table : Cache, Models, Profiles, Workspace");
						/*
							1. Log into DTS portal as DTS admin
							2. Navigate to "Apps & Devices" page
							3. Add an app or device
							4. Choose product type ''DTS Audio Processing'
							5. Fill valid value into all required fields
							6. Click on Save button
							7. Click on Public  button
							8. Click to ''Init Database'' button
							9.Open the offline databse file by SQLite
							VP: init database have 4 table: Cache, Models, Profiles, Workspace
						*/
						// 1. Log into DTS portal as DTS admin
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
						// 2. Navigate to "Apps & Devices" page
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
						Assert.assertTrue(appDeviceControl.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
						// 3. Add an app or device successfully
						// Click on Add App or Device link
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
						// 4. Choose product type ''DTS Audio Processing'
						// 5. Fill valid value into all required fields
						Hashtable<String,String> data = TestData.appDevicePublishSingleFileAudioProcessing();
						data.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						// 6. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						// 7. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
						// 8. Click to ''Init Database'' button
						// 9.Open the offline databse file by SQLite
						// VP: init database have 4 table: Cache, Models, Profiles, Workspace
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.INIT_DATABASE_LINK));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Cache where key = 'https://s3.amazonaws.com/pp.dts.com/cdn/saap/default/roommodels.dtscs'", "mime", "application/octet-stream"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Models where key = 'default'", "mime", "application/json"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Profiles where key = '0570bf57-be6a-4115-bdff-bace2fb1d03d'", "mime", "application/octet-stream"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Workspace where key = '0570bf57-be6a-4115-bdff-bace2fb1d03d'", "mime", "application/octet-stream"));
						//Delete device
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_201(){
						appDeviceControl.addLog("ID : TC640DADD_201 : Verify init database of DTS Audio Processing with eagle 1.4  have 4 table : Cache, Models, Profiles, Workspace");
						/*
							1. Log into DTS portal as DTS admin
							2. Navigate to "Apps & Devices" page
							3. Add an app or device
							4. Choose product type ''DTS Audio Processing'
							5. Leave the Eagle 1.4 on the Eagle Vesion combo box
							6. Fill valid value into all required fields
							7. Click on Save button
							8. Click on Public  button
							9. Click to ''Init Database'' button
							10.Open the offline databse file by SQLite
							VP: init database have 4 table: Cache, Models, Profiles, Workspace
						*/
						// 1. Log into DTS portal as DTS admin
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
						// 2. Navigate to "Apps & Devices" page
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
						Assert.assertTrue(appDeviceControl.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
						// 3. Add an app or device successfully
						// Click on Add App or Device link
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
						// 4. Choose product type ''DTS Audio Processing'
						// 5. Leave the Eagle 1.4 on the Eagle Vesion combo box
						// 6. Fill valid value into all required fields
						Hashtable<String,String> data = TestData.appDevicePublishLow();
						data.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						// 7. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						// 8. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
						// 9. Click to ''Init Database'' button
						// 10.Open the offline databse file by SQLite
						// VP: init database have 4 table: Cache, Models, Profiles, Workspace
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.INIT_DATABASE_LINK));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Cache where key = 'https://s3.amazonaws.com/pp.dts.com/cdn/saap/default/roommodels.dtscs'", "mime", "application/octet-stream"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Models where key = 'default'", "mime", "application/json"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Profiles where key = '0570bf57-be6a-4115-bdff-bace2fb1d03d'", "mime", "application/octet-stream"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Workspace where key = '0570bf57-be6a-4115-bdff-bace2fb1d03d'", "mime", "application/octet-stream"));
						//Delete device
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_202(){
						appDeviceControl.addLog("ID : TC640DADD_202 : Verify init database of HPX Medium with eagle 1.4  have 4 table : Cache, Models, Profiles, Workspace");
						/*
							1. Log into DTS portal as DTS admin
							2. Navigate to "Apps & Devices" page
							3. Add an app or device
							4. Choose product type 'HPX Medium'
							5. Leave the Eagle 1.4 on the Eagle Vesion combo box
							6. Fill valid value into all required fields
							7. Click on Save button
							8. Click on Public  button
							9. Click to ''Init Database'' button
							10.Open the offline databse file by SQLite
							VP: init database have 4 table: Cache, Models, Profiles, Workspace
						*/
						// 1. Log into DTS portal as DTS admin
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
						// 2. Navigate to "Apps & Devices" page
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
						Assert.assertTrue(appDeviceControl.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
						// 3. Add an app or device successfully
						// Click on Add App or Device link
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
						// 4. Choose product type ''HPX Medium'
						// 5. Leave the Eagle 1.4 on the Eagle Vesion combo box
						// 6. Fill valid value into all required fields
						Hashtable<String,String> data = TestData.appDevicePublishMedium();
						data.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						// 7. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
						// 8. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
						// 9. Click to ''Init Database'' button
						// 10.Open the offline databse file by SQLite
						// VP: init database have 4 table: Cache, Models, Profiles, Workspace
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.INIT_DATABASE_LINK));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Cache where key = 'https://s3.amazonaws.com/pp.dts.com/cdn/saap/default/roommodels.dtscs'", "mime", "application/octet-stream"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Models where key = 'default'", "mime", "application/json"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Profiles where key = '0570bf57-be6a-4115-bdff-bace2fb1d03d'", "mime", "application/octet-stream"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Workspace where key = '0570bf57-be6a-4115-bdff-bace2fb1d03d'", "mime", "application/octet-stream"));
						//Delete device
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_203(){
						appDeviceControl.addLog("ID : TC640DADD_203 : Verify init database of HPX High with eagle 1.4  have 4 table : Cache, Models, Profiles, Workspace");
						/*
							1. Log into DTS portal as DTS admin
							2. Navigate to "Apps & Devices" page
							3. Add an app or device
							4. Choose product type 'HPX High'
							5. Leave the Eagle 1.4 on the Eagle Vesion combo box
							6. Fill valid value into all required fields
							7. Click on Save button
							8. Click on Public  button
							9. Click to ''Init Database'' button
							10.Open the offline databse file by SQLite
							VP: init database have 4 table: Cache, Models, Profiles, Workspace
						*/
						// 1. Log into DTS portal as DTS admin
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
						// 2. Navigate to "Apps & Devices" page
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
						Assert.assertTrue(appDeviceControl.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
						// 3. Add an app or device successfully
						// Click on Add App or Device link
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
						// 4. Choose product type ''HPX High'
						// 5. Leave the Eagle 1.4 on the Eagle Vesion combo box
						// 6. Fill valid value into all required fields
						Hashtable<String,String> data = TestData.appDevicePublish();
						data.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						// 7. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						// 8. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
						// 9. Click to ''Init Database'' button
						// 10.Open the offline databse file by SQLite
						// VP: init database have 4 table: Cache, Models, Profiles, Workspace
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.INIT_DATABASE_LINK));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Cache where key = 'https://s3.amazonaws.com/pp.dts.com/cdn/saap/default/roommodels.dtscs'", "mime", "application/octet-stream"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Models where key = 'default'", "mime", "application/json"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Profiles where key = '0570bf57-be6a-4115-bdff-bace2fb1d03d'", "mime", "application/octet-stream"));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Workspace where key = '0570bf57-be6a-4115-bdff-bace2fb1d03d'", "mime", "application/octet-stream"));
						//Delete device
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
				/*	@Test
					public void TC640DADD_204(){ ticket form 198 to 203 already include this ticket.
						appDeviceControl.addLog("ID : TC640DADD_204 : Verify cache, Models,Profiles, Workspace table include column: key, mime, data");*/
					@Test
					public void TC640DADD_205(){
						appDeviceControl.addLog("ID : TC640DADD_205 : Verify Models table have default file on key column");
						/*
							1. Log into DTS portal as DTS admin
							2. Navigate to "Apps & Devices" page
							3. Add an app or device
							4. Choose product type 'HPX High'
							5. Leave the Eagle 1.4 on the Eagle Vesion combo box
							6. Fill valid value into all required fields
							7. Click on Save button
							8. Click on Public  button
							9. Click to ''Init Database'' button
							10.Open the offline databse file by SQLite
							VP: the key table have default file
						*/
						// 1. Log into DTS portal as DTS admin
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
						// 2. Navigate to "Apps & Devices" page
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
						Assert.assertTrue(appDeviceControl.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
						// 3. Add an app or device successfully
						// Click on Add App or Device link
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
						// 4. Choose product type ''HPX High'
						// 5. Leave the Eagle 1.4 on the Eagle Vesion combo box
						// 6. Fill valid value into all required fields
						Hashtable<String,String> data = TestData.appDevicePublish();
						data.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						// 7. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						// 8. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
						// 9. Click to ''Init Database'' button
						// 10.Open the offline databse file by SQLite
						// VP: the key table have default file
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.INIT_DATABASE_LINK));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Models where key = 'default'", "mime", "application/json"));
						//Delete device
						appDeviceControl.doDelete(DeviceInfo.DELETE);
			}
					@Test
					public void TC640DADD_206(){
						appDeviceControl.addLog("ID : TC640DADD_206 : Verify Profiles table have default file on key column");
						/*
							1. Log into DTS portal as DTS admin
							2. Navigate to "Apps & Devices" page
							3. Add an app or device
							4. Choose product type 'HPX High'
							5. Leave the Eagle 1.4 on the Eagle Vesion combo box
							6. Fill valid value into all required fields
							7. Click on Save button
							8. Click on Public  button
							9. Click to ''Init Database'' button
							10.Open the offline databse file by SQLite
							VP: the key table have default file
						*/
						// 1. Log into DTS portal as DTS admin
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
						// 2. Navigate to "Apps & Devices" page
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
						Assert.assertTrue(appDeviceControl.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
						// 3. Add an app or device successfully
						// Click on Add App or Device link
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
						// 4. Choose product type ''HPX High'
						// 5. Leave the Eagle 1.4 on the Eagle Vesion combo box
						// 6. Fill valid value into all required fields
						Hashtable<String,String> data = TestData.appDevicePublish();
						data.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						// 7. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						// 8. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
						// 9. Click to ''Init Database'' button
						// 10.Open the offline databse file by SQLite
						// VP: the key table have default file
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.INIT_DATABASE_LINK));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Profiles where key = '0570bf57-be6a-4115-bdff-bace2fb1d03d'", "mime", "application/octet-stream"));
						//Delete device
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
					@Test
					public void TC640DADD_207(){
						appDeviceControl.addLog("ID : TC640DADD_207 : Verify Workspace table have default file on key column");
						/*
							1. Log into DTS portal as DTS admin
							2. Navigate to "Apps & Devices" page
							3. Add an app or device
							4. Choose product type 'HPX High'
							5. Leave the Eagle 1.4 on the Eagle Vesion combo box
							6. Fill valid value into all required fields
							7. Click on Save button
							8. Click on Public  button
							9. Click to ''Init Database'' button
							10.Open the offline databse file by SQLite
							VP: the key table have default file
						*/
						// 1. Log into DTS portal as DTS admin
						loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
						// 2. Navigate to "Apps & Devices" page
						appDeviceControl.click(PageHome.LINK_APP_DEVICES);
						Assert.assertTrue(appDeviceControl.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
						// 3. Add an app or device successfully
						// Click on Add App or Device link
						appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
						// 4. Choose product type ''HPX High'
						// 5. Leave the Eagle 1.4 on the Eagle Vesion combo box
						// 6. Fill valid value into all required fields
						Hashtable<String,String> data = TestData.appDevicePublish();
						data.remove("save");
						appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
						appDeviceControl.click(DeviceEdit.NO_IMAGES);
						// 7. Click on Save button
						appDeviceControl.click(DeviceEdit.SAVE);
//						// 8. Click on Public  button
						appDeviceControl.click(DeviceInfo.PUBLISH);
						String device_UUID = appDeviceControl.getTextByXpath(DeviceInfo.DTS_TRACKING_ID);
						// 9. Click to ''Init Database'' button
						// 10.Open the offline databse file by SQLite
						// VP: the key table have default file
						Assert.assertTrue(appDeviceControl.downloadFile(DeviceInfo.INIT_DATABASE_LINK));
						Assert.assertTrue(appDeviceControl.isDBContainsData(appDeviceControl.getOfflineINITName(device_UUID), "Select * from Workspace where key = '0570bf57-be6a-4115-bdff-bace2fb1d03d'", "mime", "application/octet-stream"));
						//Delete device
						appDeviceControl.doDelete(DeviceInfo.DELETE);
					}
					}
