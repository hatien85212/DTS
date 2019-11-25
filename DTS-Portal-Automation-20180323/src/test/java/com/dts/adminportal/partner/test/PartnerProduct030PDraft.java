package com.dts.adminportal.partner.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.TuningRequestForm;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.TestData;

public class PartnerProduct030PDraft extends BasePage{
	
	@Override
	protected void initData() {
	}	

	/*
	 * Verify that the default catalog page displays with "All Active" report filter.
	 */
	@Test
	public void TC030PCAA_01(){
		productControl.addLog("ID : TC030PCAA_01 : Verify that the default catalog page displays with 'All Active' report filter.");
		/*
		 * 	1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. List out all items of report filter dropdown
		 */
		
		// 1. Log into DTS portal as a partner admin
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		/*
		 * Verify that 030P All Active page displays, and default item of report filter is "All Active"
		 */
		Assert.assertEquals(productControl.checkItemSelectedByXpath(PageHome.accessoryFilterSelect, "All Active"), true);
		// 3. List out all items of report filter dropdown
		ArrayList<String> arrayList = productControl.getAllComboboxOption(PageHome.accessoryFilterSelect);
		/*
		 * Verify that the report filter includes: 
			 * All Active, 
			 * Draft, 
			 * Tuning Request Pending, 
			 * Partner Tuning Review, 
			 * DTS Tuning Review, 
			 * Ready for Marketing, 
			 * DTS Marketing Review, 
			 * Ready for Published, 
			 * Published, 
			 * Needs Attention, 
			 * Suspended, 
		 */
		Boolean match = Constant.getListPartnerFilterDtsReports().containsAll(arrayList);
		Assert.assertTrue(match);
		/*
		 * Verify that the accessories table list maximum 50 items per page
		 */
		Assert.assertTrue(productControl.checkAmountOfDisplayedItemOnTable(PageHome.BRAND_ACCESSORY_TABLE_INFO, 50));
		
		// Delete all product has published status "Suspended"
		String status = "Suspended";
		productControl.deleteAllProductWithPublishedStatus(status);
		/*
		 * Verify that The accessories table is replaced by text "No models match these criteria"	
		 */
		String message = "No models match these criteria";
//		Assert.assertFalse(productControl.isElementPresent(ProductModel.PRODUCT_TABLE));
		Assert.assertTrue(productControl.checkMessageDisplay(message));
		
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		/*Verify that the accessories table have eight columns 
		 * "Picture", 
		 * "Model", 
		 * "Variant", 
		 * "Published", 
		 * "Version", 
		 * "Tuning Status", 
		 * "Headphone X Rating" and 
		 * "Marketing Status"
		 * 
		 */
		ArrayList<String> colums = productControl.getTableHeader(PageHome.ACCESSORY_THREAD);
		Boolean match1 = ListUtil.containsAll(colums, Constant.getHeaderAccessoriesTable());
		Assert.assertTrue(match1);
		// Verify that the Products table have no columns "Render Entity UUID"
		Assert.assertFalse(productControl.getTextByXpath(PageHome.ACCESSORY_THREAD).contains(Constant.RENDER));
	}
	
	/*
	 Verify that the accessories table lists out correct items when filtering with "Draft".
	 */
	@Test
	public void TC030PCD_01(){
		productControl.addLog("ID : TC030PCD_01 : Verify that the accessories table lists out correct items when filtering with 'Draft'.");
		/*
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Change report filter to "Draft"
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Create new product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Create new product
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		// Save new product
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Change report filter to "Draft"
		productControl.selectReportsFilterByText(PageHome.accessoryFilterSelect, "Draft");
		// The accessories table only lists out accessories which version status is Draft.
		Assert.assertTrue(productControl.checkAnAccessoryExistByName(data.get("name")));
		Assert.assertTrue(productControl.verifyValueColumn(PageHome.BRAND_ACCESSORIES, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		
		//Teardown: Delete product
//		productControl.deleteAccessorybyName(data.get("name"));
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Tuning Request Pending".
	 */
	@Test
	public void TC030PCD_02(){
		productControl.addLog("ID : TC030PCD_02 : Verify that the accessories table lists out correct items when filtering with 'Tuning Request Pending'.");
		/*
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Change report filter to "Tuning Request Pending"
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Create new product
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		// Save new product
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Request DTS Tuning
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		// Submit tuning request form
		productControl.selectACheckbox(TuningRequestForm.AGREE);
		productControl.click(TuningRequestForm.SUBMIT);
		
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);

		// 3. Change report filter to "Tunning Request"
		productControl.selectReportsFilterByText(PageHome.accessoryFilterSelect, Constant.TUNING_REQUEST_PENDING);
		/* The accessories table only lists out accessories which 
		 * version status is Draft and the 
		 * Tuning status is DTS request pending.
		 */
		// version status is Draft
		Assert.assertTrue(productControl.checkAnAccessoryExistByName(data.get("name")));
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(PageHome.BRAND_ACCESSORIES, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		// Tuning status is DTS request pending.
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(PageHome.BRAND_ACCESSORIES, Constant.TUNNING_STATUS, Constant.DTS_REQUEST_PENDING));
		
		//Teardown: Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);

	}
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Partner Tuning Review".
	 */
	@Test
	public void TC030PCD_03(){
		productControl.addLog("ID : TC030PCD_03 : Verify that the accessories table lists out correct items when filtering with 'Partner Tuning Review'.");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Change report filter to "Partner Tuning Review"
		 */
		
		/*
		 * Create accessory has tuning status is Pending Partner Approval
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to accessories page
		productControl.click(PageHome.linkAccessories);
		// Click Add Product link
		// Init data
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		// Save new product
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		productControl.logout();// Log out DTS portal
		/*
		 * *******************************************************************
		 */
		// 1. Log into DTS portal as a partner admin
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Change report filter to "Partner Tuning Review"
		productControl.selectReportsFilterByText(PageHome.accessoryFilterSelect, Constant.partnerPartnerTuningReview);
		/* 
		 * Verify that The accessories table only lists out accessories which version status is Draft and the Tuning status is Pending Partner Approval
		 */
		Assert.assertTrue(productControl.checkAnAccessoryExistByName(data.get("name")));
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(PageHome.BRAND_ACCESSORIES, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(PageHome.BRAND_ACCESSORIES, Constant.TUNNING_STATUS, Constant.PENDING_PARTNER_REVIEWS));

		//Teardown: Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);

	}
	/*
	 * Verify that the accessories table lists out correct items when filtering with "DTS Tuning Review".
	 */
	@Test
	public void TC030PCD_04(){
		productControl.addLog("ID : TC030PCD_04 : Verify that the accessories table lists out correct items when filtering with 'DTS Tuning Review'");
		/*
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Change report filter to "DTS Tuning Review"		
		*/
		
		/*
		 * Create accessory has tuning status is Pending DTS Approval
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to accessories page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Click Add Product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Init data
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		// Save new product
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Click Request Tuning Review
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		/*
		 ********************************************************************
		 */
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Change report filter to "DTS Tuning Review"
		productControl.selectReportsFilterByText(PageHome.accessoryFilterSelect, Constant.DTS_TUNING_REVIEW);
		/* 
		 * The accessories table only lists out accessories which version status is Draft and the Tuning status is Pending DTS Approval
		 */
		Assert.assertTrue(productControl.checkAnAccessoryExistByName(data.get("name")));
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(PageHome.BRAND_ACCESSORIES, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(PageHome.BRAND_ACCESSORIES, Constant.TUNNING_STATUS, Constant.PENDING_DTS_APPROVAL));
		
		//Teardown: Remove product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);

	}
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Ready for Marketing".
	 */
	@Test
	public void TC030PCD_05(){
		productControl.addLog("ID : TC030PCD_05 : Verify that the accessories table lists out correct items when filtering with 'Ready for Marketing'.");
		/*
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Change report filter to "Ready for Marketing"		
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productWf.addProductAndTunningAndMarketing(data, false);

		// Log out DTS
		productControl.logout();
		
		//1. Log into DTS portal as Partner user
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		//2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Change report filter to "Ready for Marketing"
		productControl.selectReportsFilterByText(PageHome.accessoryFilterSelect, Constant.READY_FOR_MARKETING);
		/* The accessories table only lists out accessories which 
		 * version status is Draft, 
		 * Tuning status must be Approved and 
		 * Marketing status must be Unsubmitted.
		 */
		// version status is Draft
		Assert.assertTrue(productControl.checkAnAccessoryExistByName(data.get("name")));
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(PageHome.BRAND_ACCESSORIES, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		// Tuning status is Approved
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(PageHome.BRAND_ACCESSORIES, Constant.TUNNING_STATUS, Constant.APPROVED));
		// Marketing status must be Unsubmitted.
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(PageHome.BRAND_ACCESSORIES, Constant.MARKETING_STATUS, Constant.UNSUBMITTED, Constant.DTS_UPLOADED, Constant.PARTNER_UPLOADED));
		
		//Teardown: Delete products
		loginControl.logout();
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productControl.deleteAccessorybyName(data.get("name"));

	}
	/*
	 * Verify that the accessories table lists out correct items when filtering with "DTS Marketing Review".	
	 */
	@Test
	public void TC030PCD_06(){
		productControl.addLog("ID : TC030PCD_06 : Verify that the accessories table lists out correct items when filtering with 'DTS Marketing Review'.");
		/*
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Change report filter to "DTS Marketing Review"		
		*/
		// 2. Navigate to "Accessories" page
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Init data
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		// Save new product
		productWf.addProductAndTunningAndMarketing(data, false);
		// Request marketing review
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);

		// Log out DTS
		productControl.logout();
		
		//1. Log into DTS portal as Partner user
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		//2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Change report filter to "DTS Marketing Review"
		productControl.selectReportsFilterByText(PageHome.accessoryFilterSelect, Constant.DTS_MARKETING_REVIEW);
		/* The accessories table only lists out accessories which 
		 * version status is Draft, 
		 * Tuning status is Approved and 
		 * Marketing status is Pending DTS Marketing Review.
		 */
		// version status is Draft
		Assert.assertTrue(productControl.checkAnAccessoryExistByName(data.get("name")));
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(PageHome.BRAND_ACCESSORIES, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		// Tuning status is Approved
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(PageHome.BRAND_ACCESSORIES, Constant.TUNNING_STATUS, Constant.APPROVED));
		// Marketing status must be Pending DTS Marketing Review.
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(PageHome.BRAND_ACCESSORIES, Constant.MARKETING_STATUS, Constant.PENDING_DTS_MARKETING_REVIEW));
		
		//Teardown: Delete products
		loginControl.logout();
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productControl.deleteAccessorybyName(data.get("name"));
	}
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Ready to Publish".	
	 */
	@Test
	public void TC030PCD_07(){
		productControl.addLog("ID : TC030PCD_07 : Verify that the accessories table lists out correct items when filtering with 'Ready to Publish'.");
		/*
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Change report filter to "Ready to Publish"		
		*/
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Accessories" page
//		productControl.click(PageHome.linkAccessories);
//		// Add new product
//		productControl.click(ProductModel.ADD_PRODUCT);
//		// Init data
//		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
//		// Save new product
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//		//Approve for the tuning
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
//		productControl.click(ProductDetailModel.APPROVE_TUNING);
//		// Request marketing review
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
//		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
//		// Approve for the marketing
//		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndTunningAndMarketing(data, true);
		//Log out
		productControl.logout();
		
		// 1. Log into DTS portal as Partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Change report filter to "Ready to Publish"
		productControl.selectReportsFilterByText(PageHome.accessoryFilterSelect, Constant.ACCESSORY_READY_TO_PUBLISH);
		/* The accessories table only lists out accessories which 
		 * version status is Draft, 
		 * Tuning and Marketing status must both be Approved.
		 */
		// version status is Draft
		Assert.assertTrue(productControl.checkAnAccessoryExistByName(data.get("name")));
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(PageHome.BRAND_ACCESSORIES, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		// Tuning status is Approved
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(PageHome.BRAND_ACCESSORIES, Constant.TUNNING_STATUS, Constant.APPROVED));
		// Marketing status must be Pending DTS Marketing Review.
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(PageHome.BRAND_ACCESSORIES, Constant.MARKETING_STATUS, Constant.APPROVED));
		
		//Teardown: Delete products
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Published".	
	 */
	@Test
	public void TC030PCD_08(){
		productControl.addLog("ID : TC030PCD_08 : Verify that the accessories table lists out correct items when filtering with 'Published'.");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Change report filter to "Published"	
		*/
		// Create a published product
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productControl.click(PageHome.linkAccessories);
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndPublish(data);

		//Log out
		productControl.logout();
		
		// 1. Log into DTS portal as Partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Change report filter to "Published"
		productControl.selectReportsFilterByText(PageHome.accessoryFilterSelect, Constant.ACCESSORY_PUBLISHED);
		/*
		 * The accessories table only lists out accessories which 
		 * Published status is Published.
		 */
		Assert.assertTrue(productControl.checkAnAccessoryExistByName(data.get("name")));
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(PageHome.BRAND_ACCESSORIES, Constant.PUBLISHED, Constant.PUBLISHED));
		
		//Teardown: Delete products
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the Products table lists out correct items when filtering with "Needs Attention"
	 */
	@Test
	public void TC030PCD_09(){
		productControl.addLog("ID : TC030PCD_09 : Verify that the Products table lists out correct items when filtering with 'Needs Attention'");
		productControl.addErrorLog("PDPP-1290: 030 Catalog All Active Models: The \"Need Attention\" filtering does not display products which Tuning is Approved but the HeadphoneX: Tuning Rating is Undetermined");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Change report filter to "Needs Attention"	
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> tuningdeclinedproduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), tuningdeclinedproduct);
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productWf.declineTuning();
		
		//Log out
		productControl.logout();
		// 1. Log into DTS portal as Partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Change report filter to "Needs Attention"	
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, Constant.ACCESSORY_NEEDATTENTION);
		
		Assert.assertTrue(productControl.checkAnAccessoryExistByName(tuningdeclinedproduct.get("name")));
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(PageHome.BRAND_ACCESSORIES, Constant.PUBLISHED, Constant.ACCESSORY_DRAFT));
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(PageHome.BRAND_ACCESSORIES, Constant.VERSION, Constant.ACCESSORY_DRAFT));
	}
	
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Suspended".	
	 */
	@Test
	public void TC030PCD_10(){
		productControl.addLog("ID : TC030PCD_10 : Verify that the accessories table lists out correct items when filtering with 'Suspended'.");
		/*
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Change report filter to "Suspended".		
		*/
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// 2. Navigate to "Accessories" page
//		productControl.click(PageHome.linkAccessories);
//		// Add new product
//		productControl.click(ProductModel.ADD_PRODUCT);
//		// Init data
//		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
//		// Save new product
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//		//Approve for the tuning
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
//		productControl.click(ProductDetailModel.APPROVE_TUNING);
//		// Request marketing review
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
//		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
//		// Approve for the marketing
//		productControl.click(ProductDetailModel.APPROVE_MARKETING);
//		// Publish a product
//		productControl.click(ProductDetailModel.PUBLISH);
//		//
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndPublish(data);
		//Log out
		productControl.logout();
		
		// 1. Log into DTS portal as Partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select the published product from product table
		productControl.selectAccessorybyName(data.get("name"));
		//Suspend this product
		productControl.click(ProductDetailModel.SUSPEND);
		productControl.selectConfirmationDialogOption("Suspend");
		// navigate to product list again
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Change report filter to "Suspended"
		productControl.selectReportsFilterByText(PageHome.accessoryFilterSelect, Constant.ACCESSORY_SUSPENDED);
		/*
		 * The accessories table only lists out accessories which 
		 * Published status is Suspended.
		 */
		Assert.assertTrue(productControl.checkAnAccessoryExistByName(data.get("name")));
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(PageHome.BRAND_ACCESSORIES, Constant.PUBLISHED, "Suspended"));
		
		//Teardown: Delete products
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectReportsFilterByText(PageHome.accessoryFilterSelect, Constant.ACCESSORY_SUSPENDED);
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
//		productControl.deleteAccessorybyName(data.get("name"));
	}

}
