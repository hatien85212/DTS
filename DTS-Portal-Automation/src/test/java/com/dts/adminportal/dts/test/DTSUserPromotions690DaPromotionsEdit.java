package com.dts.adminportal.dts.test;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AccessoryInfo;
import dts.com.adminportal.model.AccessoryMain;
import dts.com.adminportal.model.AddAccessory;
import dts.com.adminportal.model.AddPromotion;
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.PromotionInfo;
import dts.com.adminportal.model.PromotionList;
import dts.com.adminportal.model.Xpath;

public class DTSUserPromotions690DaPromotionsEdit extends CreatePage{
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
	 * Verify that the “DTS Tracking ID” is auto created and un-editable when adding new promotion
	 */
	@Test
	public void TC690DaPE_01(){
		result.addLog("ID : TC690DaPE_01 : Verify that the “DTS Tracking ID” is auto created and un-editable when adding new promotion");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Click “Add Promo' link
		 */
		// 3. Navigate to "Promotions" page
		home.click(Xpath.linkPromotions);
		// 4. Click “Add Promo' link
		home.click(PromotionList.ADD_PROMO);
		/*
		 * The “DTS Tracking ID” is auto created and un-editable in 690Da Promo Edit page
		 */
		Assert.assertNotNull(home.getTextByXpath(AddPromotion.DTS_TRACKING_ID));
		Assert.assertFalse(home.isElementEditable(AddPromotion.DTS_TRACKING_ID));
	}
	
	/*
	 * Verify that the new promotion is not created when user clicking on “Cancel” link
	 */
	@Test
	public void TC690DaPE_04(){
		result.addLog("ID : TC690DaPE_04 : Verify that the new promotion is not created when user clicking on “Cancel” link");
		/*
			Pre-condition: DTS user has “Add and manage promotions” privilege
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Click “Add Promo' link
			5. Fill valid value into all required fields
			6. Click “Cancel” link
		 */
		// 3. Navigate to "Promotions" page
		home.click(Xpath.linkPromotions);
		// 4. Click “Add Promo' link
		home.click(PromotionList.ADD_PROMO);
		// 5. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.promotionGlobal(RandomStringUtils.randomAlphanumeric(20));
		data.remove("save");
		home.addPromotion(AddPromotion.getHash(), data);
		// 6. Click “Cancel” link
		home.click(AddPromotion.CANCEL);
		/*
		 * Portal redirects to 660D Promotion List page and new promotion is not added into promotions table
		 */
		Assert.assertEquals(home.existsElement(PromotionList.getElementInfo()).getResult(), "Pass");
	}
	
	/*
	 * Verify that the new promotion is published when user clicking on “Publish” link
	 */
	@Test
	public void TC690DaPE_05(){
		result.addLog("ID : TC690DaPE_05 : Verify that the new promotion is published when user clicking on “Publish” link");
		/*
			Pre-condition: DTS user has “Add and manage promotions” privilege
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Click “Add Promo' link
			5. Fill valid value into all required fields
			6. Click "Save" link
			VP: Verify that 680 Promotion Detail page is displayed
			7. Click “Publish” link
		 */
		/*
		 * PreCondition: Create new published product
		 */
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create published product
		Hashtable<String,String> dataProduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct);
		/*
		 * *********************************************************
		 */
		// 3. Navigate to "Promotions" page
		home.click(Xpath.linkPromotions);
		// 4. Click “Add Promo' link
		home.click(PromotionList.ADD_PROMO);
		// 5. Fill valid value into all required fields
		// 6. Click "Save" link
		Hashtable<String,String> dataPromotion = TestData.promotionGlobal(dataProduct.get("brand") + " " + dataProduct.get("name"));
		home.addPromotion(AddPromotion.getHash(), dataPromotion);
		/*
		 * VP: Verify that 680 Promotion Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(PromotionInfo.PROMOTION_NAME), dataPromotion.get("name"));
		// 7. Click “Publish” link
		home.click(PromotionInfo.PUBLISH);
		/*
		 * The promotion is published successfully
		 */
		Assert.assertEquals(home.getTextByXpath(PromotionInfo.PUBLISH_STATUS), "PUBLISHED");
		// Delete product
		home.deleteAnaccessorybyName(dataProduct.get("name"));
	}
	
	/*
	 * Verify that the portal redirects to the latest product version 040D Product Detail page when clicking on the product display name link
	 */
	@Test
	public void TC690DaPE_06(){
		result.addLog("ID : TC690DaPE_06 : Verify that the portal redirects to the latest product version 040D Product Detail page when clicking on the product display name link");
		result.addErrorLog("PDPP-1323 - 680Da/Db Promo Detail/Edit: The product name links to incorrect product version.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Select a promotion from promotions table
			5. Click on the product display name link of product info section
		 */
		// 3. Navigate to "Promotions" page
		home.click(Xpath.linkPromotions);
		// 4. Select a promotion from promotions table
		home.selectAPromotion();
		// VP: Verify that the promotion info page is displayed
		Assert.assertTrue(home.isElementPresent(PromotionInfo.DEVICE_NAME));
		// 5. Click "Edit" link
		home.click(PromotionInfo.EDIT);
		// 6. Click on the product display name link of product info section
		String productName= home.getTextByXpath(AddPromotion.PRODUCT_NAME);
		String brand= home.getTextByXpath(AddPromotion.PRODUCT_BRAND_NAME);
		String uuid = home.getTextByXpath(AddPromotion.PRODUCT_UDID);
		String type = home.getTextByXpath(AddPromotion.PRODUCT_TYPE);
		//Click on the product display name link of product info section
		home.click(AddPromotion.PRODUCT_NAME);
		Assert.assertTrue(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME).contains(productName));
		Assert.assertTrue(home.getTextByXpath(AccessoryInfo.DTS_TRACKING_ID).contains(uuid));
		Assert.assertTrue(home.getTextByXpath(AccessoryInfo.BRAND_NAME).contains(brand));
		Assert.assertTrue(home.getTextByXpath(AccessoryInfo.TYPE).contains(type));
		Assert.assertTrue(home.getTextByXpath(AccessoryInfo.VERSION_STATUS).contains(Constant.PUBLISHED));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.UPDATE_PRODUCT_INFO));
		
		
		/*
		 * The 690Db Promotion Edit page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AddPromotion.PAGE_TITLE), "Edit Promotion");
	}
	
	
}
