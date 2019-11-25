package com.dts.adminportal.dts.test;

import java.util.Hashtable;

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

public class DTSUserPromotions680DbPromotionsDetail extends CreatePage {
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
	 * Verify that the display name of promotion is the combination of brand product name
	 */
	@Test
	public void TC680DbPD_01(){
		result.addLog("ID : TC680DbPD_01 : Verify that the display name of promotion is the combination of brand product name");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Select a promotion from promotions table
		 */
		// Click Add product link
		home.click(Xpath.linkAccessories);
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create 1st published product
		Hashtable<String,String> dataProduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct);
		// Navigate to Promotion page
		home.click(Xpath.linkPromotions);
		// Click Add promo link
		home.click(PromotionList.ADD_PROMO);
		// Create Global promotion
		Hashtable<String,String> dataGlobalPromotion = TestData.promotionGlobal(dataProduct.get("brand") + " " + dataProduct.get("name"));
		home.addPromotion(AddPromotion.getHash(), dataGlobalPromotion);
		
		//The 680Db Promotion detail page is displayed and the page title is the combination of brand and product name
		Assert.assertTrue(home.getTextByXpath(PromotionInfo.PROMOTION_NAME).contains(dataProduct.get("brand") + " " + dataProduct.get("name")));
		
		//Teardown: delete product
		home.deleteAnaccessorybyName(dataProduct.get("name"));
	}
	
	
	/*
	 * Verify that the portal redirects to 690Db Promo Edit page when clicking on “Edit” link
	 */
	@Test
	public void TC680DbPD_02(){
		result.addLog("ID : TC680DbPD_02 : Verify that the portal redirects to 690Db Promo Edit page when clicking on “Edit” link");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Select a promotion from promotions table
			5. Click on “Edit” link
		 */
		// 3. Navigate to "Promotions" page
		home.click(Xpath.linkPromotions);
		// 4. Select a promotion from promotions table
		home.selectAPromotion();
		// 5. Click on “Edit” link
		home.click(PromotionInfo.EDIT);
		/*
		 * The 690Db Promotion Edit page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AddPromotion.PAGE_TITLE), "Edit Promotion");
	}
	
	/*
	 * Verify that the portal redirects to 061D Company Info page when clicking on the brand link
	 */
	@Test
	public void TC680DbPD_03(){
		result.addLog("ID : TC680DbPD_03 : Verify that the portal redirects to 061D Company Info page when clicking on the brand link");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Select a promotion from promotions table
			5. Click on the brand link of product info section
		 */
		// 3. Navigate to "Promotions" page
		home.click(Xpath.linkPromotions);
		// 4. Select a promotion from promotions table
		home.selectAPromotion();
		// 5. Click on the brand link of product info section
		home.click(PromotionInfo.PRODUCT_BRAND_NAME);
		// Check if PDPP-941 found
		if(home.isElementPresent(PromotionInfo.PRODUCT_BRAND_NAME)){
			result.addErrorLog("PDPP-941: The 680D Promo Info page is not implemented as spec");
			Assert.assertTrue(false);
		}
	}
	
	/*
	 * Verify that the portal redirects to the latest product version 040D Product Detail page when clicking on the product display name link
	 */
	@Test
	public void TC680DbPD_04(){
		result.addLog("ID : TC680DbPD_04 : Verify that the portal redirects to the latest product version 040D Product Detail page when clicking on the product display name link");
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
		// 5. Click on the product display name link of product info section 
		Assert.assertTrue(home.isElementPresent(PromotionInfo.DEVICE_NAME));
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
	
	/*
	 * Verify that the portal redirects to 685D Product Promotions List page when clicking “Active Promos” link
	 */
	@Test
	public void TC680DbPD_05(){
		result.addLog("ID : TC680DbPD_05 : Verify that the portal redirects to 685D Product Promotions List page when clicking “Active Promos” link");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Select a promotion from promotions table
			5. Click on “Active Promos” link
		 */
		// 3. Navigate to "Promotions" page
		home.click(Xpath.linkPromotions);
		// 4. Select a promotion from promotions table
		home.selectAPromotion();
		// 5. Click on “Active Promos” link
		result.addErrorLog("PDPP-941: The 680D Promo Info page is not implemented as spec");
		Assert.assertTrue(false);
	}
	
	/*
	 * Verify that user is able to delete promotions successfully when clicking on “Delete” link
	 */
	@Test
	public void TC680DbPD_06(){
		result.addLog("ID : TC680DbPD_06 : Verify that user is able to delete promotions successfully when clicking on “Delete” link");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Select a promotion from promotions table
			5. Click on “Delete” link
		 */
		/*
		 * PreCondition: Create new Global promotion
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
		/*
		 * *********************************************************
		 */
		// 3. Navigate to "Promotions" page
		home.click(Xpath.linkPromotions);
		// 4. Select a promotion from promotions table
		home.selectAPromotionByName(PromotionList.PROMOTION_TABLE, dataGlobalPromotion.get("name"));
		// 5. Click on “Delete” link
		home.doDelete(PromotionInfo.DELETE);
		/*
		 * Portal redirects to 660 Promos List page and the deleted promotion is not displayed in promotions list
		 */
		Assert.assertEquals(home.existsElement(PromotionList.getElementInfo()).getResult(), "Pass");
		Assert.assertFalse(home.checkPromotionExistByName(PromotionList.PROMOTION_TABLE, dataGlobalPromotion.get("name")));
		// Delete product
		home.deleteAnaccessorybyName(dataProduct.get("name"));
	}
}
