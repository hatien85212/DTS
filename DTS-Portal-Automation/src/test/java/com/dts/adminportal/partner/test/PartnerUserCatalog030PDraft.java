package com.dts.adminportal.partner.test;

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
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.PartnerAccessoryInfo;
import dts.com.adminportal.model.PartnerAddAccessory;
import dts.com.adminportal.model.TuningRequestForm;
import dts.com.adminportal.model.Xpath;

public class PartnerUserCatalog030PDraft extends CreatePage{
	private HomeController home;
	
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}
	@BeforeMethod
	public void loginBeforeTest() {
		result = home.login(superpartneruser, superpartnerpassword);
	}
	/*
	 Verify that the accessories table lists out correct items when filtering with "Draft".
	 */
	@Test
	public void TC030PCD_01(){
		result.addLog("ID : TC030PCD_01 : Verify that the accessories table lists out correct items when filtering with 'Draft'.");
		/*
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Change report filter to "Draft"
		 */
		// Create new product
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Create new product
		home.click(AccessoryMain.ADD_ACCESSORY);
		Hashtable<String, String> data = TestData.accessoryDraft();
		// Save new product
		home.addAccessoriesPartner(PartnerAddAccessory.getHash(), data);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Change report filter to "Draft"
		home.selectReportsFilterByText(Xpath.accessoryFilterSelect, "Draft");
		// The accessories table only lists out accessories which version status is Draft.
		Assert.assertTrue(home.checkAnAccessoryExistByName(data.get("name")));
		Assert.assertTrue(home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		
		//Teardown: Delete product
		home.deleteAnaccessorybyName(data.get("name"));
	}
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Tuning Request Pending".
	 */
	@Test
	public void TC030PCD_02(){
		result.addLog("ID : TC030PCD_02 : Verify that the accessories table lists out correct items when filtering with 'Tuning Request Pending'.");
		/*
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Change report filter to "Tuning Request Pending"
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Create new product
		home.click(AccessoryMain.ADD_ACCESSORY);
		Hashtable<String, String> data = TestData.accessoryDraft();
		// Save new product
		home.addAccessoriesPartner(PartnerAddAccessory.getHash(), data);
		// Request DTS Tuning
		home.click(PartnerAccessoryInfo.REQUEST_TUNING);
		// Submit tuning request form
		home.selectACheckbox(TuningRequestForm.AGREE);
		home.click(TuningRequestForm.SUBMIT);
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);

		// 3. Change report filter to "Tunning Request"
		home.selectReportsFilterByText(Xpath.accessoryFilterSelect, Constant.TUNING_REQUEST_PENDING);
		/* The accessories table only lists out accessories which 
		 * version status is Draft and the 
		 * Tuning status is DTS request pending.
		 */
		// version status is Draft
		Assert.assertTrue(home.checkAnAccessoryExistByName(data.get("name")));
		Assert.assertTrue(home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		// Tuning status is DTS request pending.
		Assert.assertTrue(home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.TUNNING_STATUS, Constant.DTS_REQUEST_PENDING));
		
		//Teardown: Delete product
		home.deleteAnaccessorybyName(data.get("name"));

	}
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Partner Tuning Review".
	 */
	@Test
	public void TC030PCD_03(){
		result.addLog("ID : TC030PCD_03 : Verify that the accessories table lists out correct items when filtering with 'Partner Tuning Review'.");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Change report filter to "Partner Tuning Review"
		 */
		
		/*
		 * Create accessory has tuning status is Pending Partner Approval
		 */
		// Login DTS portal as dts user
		home.logout();
		home.login(superUsername, superUserpassword);
		// Navigate to accessories page
		home.click(Xpath.linkAccessories);
		// Click Add Product link
		// Init data
		home.click(AccessoryMain.ADD_ACCESSORY);
		Hashtable<String, String> data = TestData.accessoryTuning();
		// Save new product
		home.addAccessoriesPartner(PartnerAddAccessory.getHash(), data);
		home.logout();// Log out DTS portal
		/*
		 * *******************************************************************
		 */
		// 1. Log into DTS portal as a partner admin
		home.login(superpartneruser, superpartnerpassword);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Change report filter to "Partner Tuning Review"
		home.selectReportsFilterByText(Xpath.accessoryFilterSelect, Constant.partnerPartnerTuningReview);
		/* 
		 * Verify that The accessories table only lists out accessories which version status is Draft and the Tuning status is Pending Partner Approval
		 */
		Assert.assertTrue(home.checkAnAccessoryExistByName(data.get("name")));
		Assert.assertTrue(home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		Assert.assertTrue(home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.TUNNING_STATUS, Constant.PENDING_PARTNER_APPROVAL));

		//Teardown: Delete product
		home.deleteAnaccessorybyName(data.get("name"));

	}
	/*
	 * Verify that the accessories table lists out correct items when filtering with "DTS Tuning Review".
	 */
	@Test
	public void TC030PCD_04(){
		result.addLog("ID : TC030PCD_04 : Verify that the accessories table lists out correct items when filtering with 'DTS Tuning Review'");
		/*
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Change report filter to "DTS Tuning Review"		
		*/
		
		/*
		 * Create accessory has tuning status is Pending DTS Approval
		 */
		// Navigate to accessories page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add Product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Init data
		Hashtable<String, String> data = TestData.accessoryTuning();
		// Save new product
		home.addAccessoriesPartner(PartnerAddAccessory.getHash(), data);
		// Click Request Tuning Review
		home.click(PartnerAccessoryInfo.REQUEST_TUNING);
		/*
		 ********************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Change report filter to "DTS Tuning Review"
		home.selectReportsFilterByText(Xpath.accessoryFilterSelect, Constant.DTS_TUNING_REVIEW);
		/* 
		 * The accessories table only lists out accessories which version status is Draft and the Tuning status is Pending DTS Approval
		 */
		Assert.assertTrue(home.checkAnAccessoryExistByName(data.get("name")));
		Assert.assertTrue(home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		Assert.assertTrue(home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.TUNNING_STATUS, Constant.PENDING_DTS_APPROVAL));
		
		//Teardown: Remove product
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		home.deleteAnaccessorybyName(data.get("name"));

	}
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Ready for Marketing".
	 */
	@Test
	public void TC030PCD_05(){
		result.addLog("ID : TC030PCD_05 : Verify that the accessories table lists out correct items when filtering with 'Ready for Marketing'.");
		/*
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Change report filter to "Ready for Marketing"		
		*/
		// Create new product
		home.logout();
		home.login(superUsername, superUserpassword);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// Add new product
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Init data
		Hashtable<String, String> data = TestData.accessoryTuning();
		// Save new product
		home.addAccessoriesPartner(PartnerAddAccessory.getHash(), data);
		//Approve for the tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Request marketing review

		// Log out DTS
		home.logout();
		
		//1. Log into DTS portal as Partner user
		home.login(partneruser, password);
		//2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Change report filter to "Ready for Marketing"
		home.selectReportsFilterByText(Xpath.accessoryFilterSelect, Constant.READY_FOR_MARKETING);
		/* The accessories table only lists out accessories which 
		 * version status is Draft, 
		 * Tuning status must be Approved and 
		 * Marketing status must be Unsubmitted.
		 */
		// version status is Draft
		Assert.assertTrue(home.checkAnAccessoryExistByName(data.get("name")));
		Assert.assertTrue(home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		// Tuning status is Approved
		Assert.assertTrue(home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.TUNNING_STATUS, Constant.APPROVED));
		// Marketing status must be Unsubmitted.
		Assert.assertTrue(home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.MARKETING_STATUS, Constant.UNSUBMITTED));
		
		//Teardown: Delete products
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		home.deleteAnaccessorybyName(data.get("name"));

	}
	/*
	 * Verify that the accessories table lists out correct items when filtering with "DTS Marketing Review".	
	 */
	@Test
	public void TC030PCD_06(){
		result.addLog("ID : TC030PCD_06 : Verify that the accessories table lists out correct items when filtering with 'DTS Marketing Review'.");
		/*
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Change report filter to "DTS Marketing Review"		
		*/
		// 2. Navigate to "Accessories" page
		home.logout();
		home.login(superUsername, superUserpassword);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// Add new product
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Init data
		Hashtable<String, String> data = TestData.accessoryPublish();
		// Save new product
		home.addAccessoriesPartner(PartnerAddAccessory.getHash(), data);
		//Approve for the tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Request marketing review
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);

		// Log out DTS
		home.logout();
		
		//1. Log into DTS portal as Partner user
		home.login(partneruser, password);
		//2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Change report filter to "DTS Marketing Review"
		home.selectReportsFilterByText(Xpath.accessoryFilterSelect, Constant.DTS_MARKETING_REVIEW);
		/* The accessories table only lists out accessories which 
		 * version status is Draft, 
		 * Tuning status is Approved and 
		 * Marketing status is Pending DTS Marketing Review.
		 */
		// version status is Draft
		Assert.assertTrue(home.checkAnAccessoryExistByName(data.get("name")));
		Assert.assertTrue(home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		// Tuning status is Approved
		Assert.assertTrue(home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.TUNNING_STATUS, Constant.APPROVED));
		// Marketing status must be Pending DTS Marketing Review.
		Assert.assertTrue(home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.MARKETING_STATUS, Constant.PENDING_DTS_MARKETING_REVIEW));
		
		//Teardown: Delete products
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		home.deleteAnaccessorybyName(data.get("name"));

	}
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Ready to Publish".	
	 */
	@Test
	public void TC030PCD_07(){
		result.addLog("ID : TC030PCD_07 : Verify that the accessories table lists out correct items when filtering with 'Ready to Publish'.");
		/*
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Change report filter to "Ready to Publish"		
		*/
		home.logout();
		home.login(superUsername, superUserpassword);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// Add new product
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Init data
		Hashtable<String, String> data = TestData.accessoryPublish();
		// Save new product
		home.addAccessoriesPartner(PartnerAddAccessory.getHash(), data);
		//Approve for the tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Request marketing review
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		// Approve for the marketing
		home.click(AccessoryInfo.APPROVE_MARKETING);
		//Log out
		home.logout();
		
		// 1. Log into DTS portal as Partner user
		home.login(superpartneruser, superpartnerpassword);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Change report filter to "Ready to Publish"
		home.selectReportsFilterByText(Xpath.accessoryFilterSelect, Constant.ACCESSORY_READY_TO_PUBLISH);
		/* The accessories table only lists out accessories which 
		 * version status is Draft, 
		 * Tuning and Marketing status must both be Approved.
		 */
		// version status is Draft
		Assert.assertTrue(home.checkAnAccessoryExistByName(data.get("name")));
		Assert.assertTrue(home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		// Tuning status is Approved
		Assert.assertTrue(home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.TUNNING_STATUS, Constant.APPROVED));
		// Marketing status must be Pending DTS Marketing Review.
		Assert.assertTrue(home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.MARKETING_STATUS, Constant.APPROVED));
		
		//Teardown: Delete products
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		home.deleteAnaccessorybyName(data.get("name"));
	}
	
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Published".	
	 */
	@Test
	public void TC030PCD_08(){
		result.addLog("ID : TC030PCD_08 : Verify that the accessories table lists out correct items when filtering with 'Published'.");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Change report filter to "Published"	
		*/
		home.logout();
		home.login(superUsername, superUserpassword);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// Add new product
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Init data
		Hashtable<String, String> data = TestData.accessoryPublish();
		// Save new product
		home.addAccessoriesPartner(PartnerAddAccessory.getHash(), data);
		//Approve for the tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Request marketing review
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		// Approve for the marketing
		home.click(AccessoryInfo.APPROVE_MARKETING);
		// Publish a product
		home.click(AccessoryInfo.PUBLISH);
		//
		//Log out
		home.logout();
		
		// 1. Log into DTS portal as Partner user
		home.login(superpartneruser, superpartnerpassword);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Change report filter to "Published"
		home.selectReportsFilterByText(Xpath.accessoryFilterSelect, Constant.ACCESSORY_PUBLISHED);
		/*
		 * The accessories table only lists out accessories which 
		 * Published status is Published.
		 */
		Assert.assertTrue(home.checkAnAccessoryExistByName(data.get("name")));
		Assert.assertTrue(home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.PUBLISHED, Constant.PUBLISHED));
		
		//Teardown: Delete products
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		home.deleteAnaccessorybyName(data.get("name"));
	}
	
	/*
	 * Verify that the Products table lists out correct items when filtering with "Needs Attention"
	 */
	@Test
	public void TC030PCD_09(){
		result.addLog("ID : TC030PCD_09 : Verify that the Products table lists out correct items when filtering with 'Needs Attention'");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Change report filter to "Needs Attention"	
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Change report filter to "Needs Attention"	
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, Constant.accessoryNeedAttention);
		result.addErrorLog("PDPP-1290: 030 Catalog All Active Models: The \"Need Attention\" filtering does not display products which Tuning is Approved but the HeadphoneX: Tuning Rating is Undetermined");
		Assert.assertTrue(false);
	}
	
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Suspended".	
	 */
	@Test
	public void TC030PCD_10(){
		result.addLog("ID : TC030PCD_10 : Verify that the accessories table lists out correct items when filtering with 'Suspended'.");
		/*
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Change report filter to "Suspended".		
		*/
		home.logout();
		home.login(superUsername, superUserpassword);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// Add new product
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Init data
		Hashtable<String, String> data = TestData.accessoryPublish();
		// Save new product
		home.addAccessoriesPartner(PartnerAddAccessory.getHash(), data);
		//Approve for the tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Request marketing review
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		// Approve for the marketing
		home.click(AccessoryInfo.APPROVE_MARKETING);
		// Publish a product
		home.click(AccessoryInfo.PUBLISH);
		//
		//Log out
		home.logout();
		
		// 1. Log into DTS portal as Partner user
		home.login(superpartneruser, superpartnerpassword);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select the published product from product table
		home.selectAnaccessorybyName(data.get("name"));
		//Suspend this product
		home.click(PartnerAccessoryInfo.SUSPEND);
		home.selectConfirmationDialogOption("Suspend");
		// navigate to product list again
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Change report filter to "Suspended"
		home.selectReportsFilterByText(Xpath.accessoryFilterSelect, Constant.ACCESSORY_SUSPENDED);
		/*
		 * The accessories table only lists out accessories which 
		 * Published status is Suspended.
		 */
		Assert.assertTrue(home.checkAnAccessoryExistByName(data.get("name")));
		Assert.assertTrue(home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.PUBLISHED, "Suspended"));
		
		//Teardown: Delete products
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		home.deleteAnaccessorybyName(data.get("name"));
	}
}
