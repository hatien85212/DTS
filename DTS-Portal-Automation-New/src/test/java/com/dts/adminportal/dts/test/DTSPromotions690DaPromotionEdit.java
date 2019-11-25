package com.dts.adminportal.dts.test;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.AddPromotion;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PromotionInfo;
import com.dts.adminportal.model.PromotionList;
import com.dts.adminportal.util.TestData;

public class DTSPromotions690DaPromotionEdit extends BasePage{
	
	@Override
	protected void initData() {
	}	

	/*
	 Verify that the “DTS Tracking ID” is auto created and un-editable when adding new promotion
	 and Verify that the new promotion is not created when user clicking on “Cancel” link
	 */
	@Test
	public void TC690DaPE_01(){
		promotionControl.addLog("ID : TC690DaPE_01 : Verify that the “DTS Tracking ID” is auto created and un-editable when adding new promotion "
				                 + "and Verify that the new promotion is not created when user clicking on “Cancel” link");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Click "Add Promo" link
			VP: The 690Da Promo Edit page is displayed.
			VP: The “DTS Tracking ID” is auto created and un-editable in 690Da Promo Edit page .
			5. Fill valid value into all required fields
			6. Click "Cancel" link
			VP: Portal redirects to 660D Promotion List page and new promotion is not added into promotions table.
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Promotions" page
		promotionControl.click(PageHome.linkPromotions);
		// 4. Click "Add Promo' link
		promotionControl.click(PromotionList.ADD_PROMO);
		/*
		    VP: The 690Da Promo Edit page is displayed.
			VP: The “DTS Tracking ID” is auto created and un-editable in 690Da Promo Edit page .
		 */
		Assert.assertNotNull(promotionControl.getTextByXpath(AddPromotion.DTS_TRACKING_ID));
		Assert.assertFalse(promotionControl.isElementEditable(AddPromotion.DTS_TRACKING_ID));
		// 5. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.promotionGlobal(RandomStringUtils.randomAlphanumeric(20));
		data.remove("save");
		promotionControl.addPromotion(AddPromotion.getHash(), data);
		// 6. Click "Cancel" link
		promotionControl.click(AddPromotion.CANCEL);
		/*
		 	VP: Portal redirects to 660D Promotion List page and new promotion is not added into promotions table
		 */
		Assert.assertTrue(promotionControl.existsElement(PromotionList.getElementInfo()));
	}
	
	/*
	 * Verify that the new promotion is not created when user clicking on "Cancel" link
	 */
//	@Test
//	public void TC690DaPE_04(){
//		promotionControl.addLog("ID : TC690DaPE_04 : Verify that the new promotion is not created when user clicking on “Cancel” link");
//		/*
//			Pre-condition: DTS user has "Add and manage promotions" privilege
//			1. Navigate to DTS portal
//			2. Log into DTS portal as a DTS user successfully
//			3. Navigate to "Promotions" page
//			4. Click "Add Promo" link
//			5. Fill valid value into all required fields
//			6. Click "Cancel" link
//		 */
//		// 3. Navigate to "Promotions" page
//		promotionControl.click(PageHome.linkPromotions);
//		// 4. Click "Add Promo" link
//		promotionControl.click(PromotionList.ADD_PROMO);
//		// 5. Fill valid value into all required fields
//		Hashtable<String,String> data = TestData.promotionGlobal(RandomStringUtils.randomAlphanumeric(20));
//		data.remove("save");
//		promotionControl.addPromotion(AddPromotion.getHash(), data);
//		// 6. Click "Cancel" link
//		promotionControl.click(AddPromotion.CANCEL);
//		/*
//		 * Portal redirects to 660D Promotion List page and new promotion is not added into promotions table
//		 */
//		Assert.assertEquals(promotionControl.existsElement(PromotionList.getElementInfo()), true);
//	}
	
	/*
	 * Verify that the portal redirects to the latest product version 040D Product Detail page when clicking on the product display name link
	 */
	@Test
	public void TC690DaPE_06(){
		promotionControl.addLog("ID : TC690DaPE_06 : Verify that the portal redirects to the latest product version 040D Product Detail page when clicking on the product display name link");
		promotionControl.addErrorLog("PDPP-1323 - 680Da/Db Promo Detail/Edit: The product name links to incorrect product version.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Select a promotion from promotions table
			VP: Verify that the promotion info page is displayed
			5. Click "Edit" link
			VP: The 690Db Promotion Edit page is displayed
			6. Click on the product display name link of product info section
			VP: Verify that the portal redirects to the latest product version 040D Product Detail page when clicking on the product display name link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Promotions" page
		promotionControl.click(PageHome.linkPromotions);
		// 4. Select a promotion from promotions table
		promotionControl.selectAPromotion();
		/* 
		 	VP: Verify that the promotion info page is displayed
		 */
		Assert.assertTrue(promotionControl.existsElement(PromotionInfo.getElementInfo()));
		// 5. Click "Edit" link
		promotionControl.click(PromotionInfo.EDIT);
		/*
		  	VP: The 690Db Promotion Edit page is displayed
		 */
		Assert.assertEquals(promotionControl.getTextByXpath(AddPromotion.PAGE_TITLE), "Edit Promotion");
		String productName= promotionControl.getTextByXpath(AddPromotion.PRODUCT_NAME_EDIT);
		String brand= promotionControl.getTextByXpath(AddPromotion.PRODUCT_BRAND_NAME);
		String uuid = promotionControl.getTextByXpath(AddPromotion.PRODUCT_UDID);
		String type = promotionControl.getTextByXpath(AddPromotion.PRODUCT_TYPE);
		// 6. Click on the product display name link of product info section
		promotionControl.click(AddPromotion.PRODUCT_NAME_EDIT);
		/*
			VP: Verify that the portal redirects to the latest product version 040D Product Detail page when clicking on the product display name link
		 */
		Assert.assertTrue(promotionControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME).contains(productName));
		Assert.assertTrue(promotionControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID).contains(uuid));
		Assert.assertTrue(promotionControl.getTextByXpath(ProductDetailModel.BRAND_NAME).contains(brand));
		Assert.assertTrue(promotionControl.getTextByXpath(ProductDetailModel.TYPE).contains(type));
		Assert.assertTrue(promotionControl.getTextByXpath(ProductDetailModel.PUBLICATION_STATUS).contains(Constant.PUBLISHED));
//		Assert.assertTrue(promotionControl.isElementPresent(ProductDetailModel.VIEW_CURRENT_MODEL)); 
	}
	
	
}
