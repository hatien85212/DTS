package com.dts.adminportal.dts.test;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AddPromotion;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PromotionInfo;
import com.dts.adminportal.model.PromotionList;
import com.dts.adminportal.util.TestData;

public class DTSPromotions680DbPromotionDetail extends BasePage {
	
	@Override
	protected void initData() {
	}	
	/*
	 * Verify that the display name of promotion is the combination of brand product name
	 */
	@Test
	public void TC680DbPD_01(){
		promotionControl.addLog("ID : TC680DbPD_01 : Verify that the display name of promotion is the combination of brand product name");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Select a promotion from promotions table
			VP: The 680Db Promotion detail page is displayed with correct information and the page title is the combination of brand and product name
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Promotions" page
		promotionControl.click(PageHome.linkPromotions);
		// 4. Select a promotion from promotions table
		String promotionname = promotionControl.selectAPromotion();
		/* 
		 	VP: The 680Db Promotion detail page is displayed and the page title is the combination of brand and product name
		 */
		//Assert.assertTrue(promotionControl.isElementPresent(PromotionInfo.DEVICE_NAME));	
		Assert.assertEquals(promotionname,promotionControl.getTextByXpath(PromotionInfo.PROMOTION_NAME));
	}
	
	
	/*
	 * Verify that the portal redirects to 061D Company Info page when clicking on the brand link
	 */
	@Test
	public void TC680DbPD_03(){
		promotionControl.addLog("ID : TC680DbPD_03 : Verify that the portal redirects to 061D Company Info page when clicking on the brand link");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Select a promotion from promotions table
			5. Click on the brand link of product info section
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Promotions" page
		promotionControl.click(PageHome.linkPromotions);
		// 4. Select a promotion from promotions table
		promotionControl.selectAPromotion();
		// 5. Click on the brand link of product info section
		promotionControl.click(PromotionInfo.PRODUCT_BRAND_NAME);
		// Check if PDPP-941 found
		if(promotionControl.isElementPresent(PromotionInfo.PRODUCT_BRAND_NAME)){
			promotionControl.addErrorLog("PDPP-941: The 680D Promo Info page is not implemented as spec");
			Assert.assertTrue(false);
		}
	}
	
	/*
	 * Verify that the portal redirects to the latest product version 040D Product Detail page when clicking on the product display name link
	 */
	@Test
	public void TC680DbPD_04(){
		promotionControl.addLog("ID : TC680DbPD_04 : Verify that the portal redirects to the latest product version 040D Product Detail page when clicking on the product display name link");
		promotionControl.addErrorLog("PDPP-1323 - 680Da/Db Promo Detail/Edit: The product name links to incorrect product version.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Select a promotion from promotions table
			5. Click on the product display name link of product info section
			VP: Portal redirects to 040D Product Detail page
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Promotions" page
		promotionControl.click(PageHome.linkPromotions);
		// 4. Select a promotion from promotions table
		promotionControl.selectAPromotion();
		// Get product info to compare with info in Product Detail page
		String productName= promotionControl.getTextByXpath(AddPromotion.PRODUCT_NAME);
		String brand= promotionControl.getTextByXpath(AddPromotion.PRODUCT_BRAND_NAME);
		String uuid = promotionControl.getTextByXpath(AddPromotion.PRODUCT_UDID);
		String type = promotionControl.getTextByXpath(AddPromotion.PRODUCT_TYPE);
		// 5. Click on the product display name link of product info section
		promotionControl.click(AddPromotion.PRODUCT_NAME);
		/*
		  	VP: Portal redirects to 040D Product Detail page
		 */
		Assert.assertTrue(promotionControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME).contains(productName));
		Assert.assertTrue(promotionControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID).contains(uuid));
		Assert.assertTrue(promotionControl.getTextByXpath(ProductDetailModel.BRAND_NAME).contains(brand));
		Assert.assertTrue(promotionControl.getTextByXpath(ProductDetailModel.TYPE).contains(type));
		Assert.assertTrue(promotionControl.getTextByXpath(ProductDetailModel.PUBLICATION_STATUS).contains(Constant.PUBLISHED));
		//Assert.assertTrue(promotionControl.isElementPresent(ProductDetailModel.UPDATE_PRODUCT_INFO));//View Current Version
		
		if(promotionControl.isElementPresent(ProductDetailModel.UPDATE_PRODUCT_INFO)){
			promotionControl.addErrorLog(" The 040D Product Detail page is not implemented as spec");
			Assert.assertTrue(false);
		}
	}
	
	/*
	 * Verify that the portal redirects to 685D Product Promotions List page when clicking “Active Promos” link
	 */
	@Test
	public void TC680DbPD_05(){
		promotionControl.addLog("ID : TC680DbPD_05 : Verify that the portal redirects to 685D Product Promotions List page when clicking “Active Promos” link");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Select a promotion from promotions table
			5. Click on "Active Promos" link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Promotions" page
		promotionControl.click(PageHome.linkPromotions);
		// 4. Select a promotion from promotions table
		promotionControl.selectAPromotion();
		// 5. Click on "Active Promos" link
		promotionControl.addErrorLog("PDPP-941: The 680D Promo Info page is not implemented as spec");
		Assert.assertTrue(false);
	}
}
