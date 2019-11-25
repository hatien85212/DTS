package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.DTSHome;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.TuningRequestForm;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.TestData;

public class DTSProducts030DAllActive extends BasePage{

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
	}
	
	/*
	 * Verify that the report Filter dropdown contains correct items.
	 */
	@Test
	public void TC030DCAA_05(){
		productControl.addLog("ID : TC030DCAA_05 : Verify that the report Filter dropdown contains correct items.");
		/*
		 * 	1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			"VP: 030P All Active page displays, and default item of report filter is ""All Active"".
			VP: Verify that the report filter includes: All Active, Recently Published, Recently Added, Draft, Tuning Request , Tuning Request - Overdue, Partner Tuning Review, Tuning Declined, Tuning Revoked, Headphone:X Rating A+, Headphone:X Rating A, Ready for Marketing, DTS Marketing Review, DTS Marketing Review - Overdue, Marketing Declined, Marketing Revoked, Ready to Publish, Ready to Published - Overdue, Need Attention, Suspended.
			VP: Verify that the default items of ""Brand"" filter dropdown is ""All Brands""."
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(DTSHome.LINK_ACCESSORIES);
		// 030P All Active page displays, 
		// and default item of report filter is "All Active".
		Assert.assertEquals(productControl.checkItemSelectedByXpath(PageHome.accessoryFilterSelect, "All Active"), true);
		ArrayList<String> arrayList = productControl.getOptionList(PageHome.accessoryFilterSelect);
		/*
		 * Verify that the report filter includes: 
		 * All Active, 
		 * Recently Published, 
		 * Recently Added, 
		 * Draft, 
		 * Tuning Request Pending, 
		 * Tuning Request - Overdue, 
		 * Partner Tuning Review, 
		 * Tuning Declined, 
		 * Tuning Revoked, 
		 * Headphone:X Rating A+, 
		 * Headphone:X Rating A, 
		 * Ready for Marketing, 
		 * DTS Marketing Review,
		 * DTS Marketing Review - Overdue, 
		 * Marketing Declined, 
		 * Marketing Revoked, 
		 * Ready to Publish, 
		 * Ready to Published - Overdue, 
		 * Need Attention, 
		 * Suspended.
		 */
		Assert.assertTrue(Constant.getListFilterDtsReports().containsAll(arrayList));
		/*
		 * The Brand filter dropdown is displayed and the default item is "All Brands"
		 */
		Assert.assertEquals(productControl.getItemSelected(ProductModel.BRAND_FILTER), "All Brands");
	}
	
	/*
	 * Verify that the accessories table lists maximum 50 items per page.
	 */
	@Test
	public void TC030DCAA_08(){
		productControl.addLog("ID : TC030DCAA_08 : Verify that the accessories table lists maximum 50 items per page.");
		/*
		 	1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		/*
		 * Verify that the accessories table list maximum 50 items per page.
		 */
		Assert.assertTrue(productControl.checkAmountOfDisplayedItemOnTable(ProductModel.PRODUCT_TABLE_INFO, 50));
	}
	
	/*
	 * Verify that the accessories table is hidden and displays text "No models match these criteria" when the report filter is empty.
	 */
	@Test
	public void TC030DCAA_10(){
		productControl.addLog("ID : TC030DCAA_10 : Verify that the accessories table is hidden and displays text 'No models match these criteria' when the report filter is empty");
		/*
		 	1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// Clean all product with suspended status
		productControl.deleteAllProductWithPublishedStatus("Suspended");
		/*
		 * Verify that the accessories table is hidden and displays text "No models match these criteria" when the report filter is empty.
		 */
		Assert.assertEquals(productControl.getTotalElement(PageHome.BRAND_ACCESSORY_TABLE_INFO), 0);
		Assert.assertTrue(productControl.checkMessageDisplay("No models match these criteria"));
	}
	
	/*
	 * Verify that the accessories table display eight columns properly
	 */
	@Test
	public void TC030DCAA_11(){
		productControl.addLog("ID : TC030DCAA_11 : Verify that the accessories table display eight columns properly");
		/*
		 * 	1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
		 */
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(DTSHome.LINK_ACCESSORIES);
		/*Verify that the accessories table have eight columns 
		 * "Picture", 
		 * "Model", 
		 * "Variant", 
		 * "Published", 
		 * "Version", 
		 * "Tuning Status", 
		 * "Headphone X Rating"
		 * "Marketing Status"
		 * "Brand"
		 * "Render Entity UUID"
		 */
		ArrayList<String> colums = productControl.getTableHeader(PageHome.ACCESSORY_THREAD);
		Boolean match = ListUtil.containsAll(colums, Constant.getHeaderAccessoriesTableDtsUser());
		Assert.assertTrue(match);
	}
	
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Draft".
	 */
	@Test
	public void TC030DCAA_12(){
		productControl.addLog("ID : TC030DCAA_12 : Verify that the accessories table lists out correct items when filtering with 'Draft'.");
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Change report filter to "Draft"
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(DTSHome.LINK_ACCESSORIES);
		// 3. Change report filter to "Draft"
		productControl.selectReportsFilterByText(PageHome.accessoryFilterSelect, "Draft");
		/*
		 * The accessories table only lists out accessories which version status is Draft.
		 */
		Assert.assertTrue(productControl.verifyValueColumn(PageHome.BRAND_ACCESSORIES, Constant.VERSION, Constant.ACCESSORY_DRAFT));
	}
	
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Tuning Request Pending".
	 */
	@Test
	public void TC030DCAA_13(){
		productControl.addLog("ID : TC030DCAA_13 : Verify that the accessories table lists out correct items when filtering with 'Tuning Request Pending'");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Change report filter to "Tuning Request Pending"
		 */
		/*
		 * PreCondition: Create product has status "Tuning Request Pending"
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create new product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Expand partner action link
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		// Click Request DTS Tuning link
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		// Approve submit form
		productControl.selectACheckbox(TuningRequestForm.AGREE);
		productControl.click(TuningRequestForm.SUBMIT);
		/*
		 * ***************************************************************
		 */
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 3. Change report filter to "Tuning Request Pending"
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, Constant.TUNING_REQUEST_PENDING);
		/* 
		 * The accessories table only lists out accessories which 
		 * version status is Draft and the 
		 * Tuning status is DTS request pending.
		 */
		// version status is Draft
//		Assert.assertTrue(productControl.verifyValueColumn(ProductModel.PRODUCT_TABLE, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(ProductModel.PRODUCT_TABLE, ProductModel.ProductTblHeader.VERSION.getName(), ProductModel.ProductStatus.VERSION_DRAFT.getName()));
		// Tuning status is DTS request pending.
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(ProductModel.PRODUCT_TABLE, Constant.TUNNING_STATUS, Constant.DTS_REQUEST_PENDING));
		// Delete product
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the Products table lists out correct items when filtering with "Partner Tuning Review".
	 */
	@Test
	public void TC030DCAA_14(){
		productControl.addLog("ID : TC030DCAA_14 : Verify that the Products table lists out correct items when filtering with 'Partner Tuning Review'.");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Change report filter to "Partner Tuning Review"
		 */
		/*
		 * PreCondition: Create product has status "Partner Tuning Review"
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create new product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * ***************************************************************
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Change report filter to "Partner Tuning Review"
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, Constant.partnerPartnerTuningReview);
		/* 
		 The accessories table only lists out accessories which 
		 version status is Draft and the 
		 Tuning status is Pending Partner Approval.

		 */
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(PageHome.BRAND_ACCESSORIES, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(PageHome.BRAND_ACCESSORIES, Constant.TUNNING_STATUS, Constant.PENDING_PARTNER_REVIEWS));
		// Delete product
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	
	}
	
	/*
	 * Verify that the products table lists out correct items when filtering with "DTS Tuning Review".
	 */
	@Test
	public void TC030DCAA_15(){
		productControl.addLog("ID : TC030DCAA_15 : Verify that the products table lists out correct items when filtering with 'DTS Tuning Review'.");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Change report filter to "DTS Tuning Review"
		 */
		/*
		 * Precondition: Create product has status "DTS Tuning Review"
		 */
		// Log into DTS portal as partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Click Request tuning review link
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		// Log out
		productControl.logout();
		/*
		 * ****************************************************************
		 */
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 3. Change report filter to "DTS Tuning Review"
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, "DTS Tuning Review");
		/* 
		 * The Products table only lists out Products which version status is Draft and the Tuning status is  DTS Approval		
		 */
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(ProductModel.PRODUCT_TABLE, "Version", "Draft"));
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(ProductModel.PRODUCT_TABLE, "Tuning Status", "Pending DTS Review"));
		// Delete product
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Ready for Marketing".
	 */
	@Test
	public void TC030DCAA_16(){
		productControl.addLog("ID : TC030DCAA_16 : Verify that the accessories table lists out correct items when filtering with 'Ready for Marketing'.");
		productControl.addErrorLog("PDPP-1222: 030 Catalog: All Active Model: The 'Ready for Marketing' filtering does not filter products that marketing material is uploaded");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Change report filter to"Ready for Marketing"
		 */
		/*
		 * Precondition: Create product has status "Ready for Marketing"
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		data.put("add marketing", AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Approve tuning
		productWf.approveTuning();
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 3. Change report filter to "Ready for Marketing"
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, Constant.READY_FOR_MARKETING);
		// Check if PDPP-1222 found
		if(!productControl.checkAnAccessoryExistByName(data.get("name"))){
			productControl.addErrorLog("Failed by bug ID PDPP-1222: 030 Catalog: All Active Model: The 'Ready for Marketing' filtering does not filter products that marketing material is uploaded");
			Assert.assertTrue(false);
		}
		/* 
		 * The Products table only lists out Products which version status is Draft, Tuning status must be Approved and Marketing status must be Unsubmitted
		 */
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(ProductModel.PRODUCT_TABLE, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(ProductModel.PRODUCT_TABLE, Constant.TUNNING_STATUS, Constant.APPROVED));
		
		// Delete product
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the accessories table lists out correct items when filtering with "DTS Marketing Review".
	 */
	@Test
	public void TC030DCAA_17(){
		productControl.addLog("ID : TC030DCAA_17 : Verify that the accessories table lists out correct items when filtering with 'DTS Marketing Review'.");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Change report filter to  "DTS Marketing Review"
		 */
		/*
		 * PreCondition: Create product has status "DTS Marketing Review"
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		data.put("add marketing", AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Approve tuning
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.APPROVE_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.APPROVE_TUNING_SUBMIT_THEN_BACK);
		// Request marketing review
		//userControl.logout();
		//loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		/*
		 * *****************************************************************
		 */
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 3. Change report filter to "DTS Marketing Review"
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, "DTS Marketing Review");
		/* 
		 * The Products table only lists out Products which version status is Draft, Tuning status is Approved and Marketing status is  DTS Marketing Review
		 */
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(ProductModel.PRODUCT_TABLE, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(ProductModel.PRODUCT_TABLE, Constant.TUNNING_STATUS, Constant.APPROVED));
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(ProductModel.PRODUCT_TABLE, Constant.MARKETING_STATUS, Constant.PENDING_DTS_MARKETING_REVIEW));
		// Delete product
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Ready to Publish".
	 */
	@Test
	public void TC030DCAA_18(){
		productControl.addLog("ID : TC030DCAA_18 : Verify that the accessories table lists out correct items when filtering with 'Ready to Publish'.");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Change report filter to "Ready to Publish"
		 */
		/*
		 * Precondition: Create product has status "Ready to Publish"
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Approve tuning
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.APPROVE_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.APPROVE_TUNING_SUBMIT_THEN_BACK);
		// Approve marketing
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		productControl.click(ProductDetailModel.REQUEST_MARKETING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 3. Change report filter to "Ready to Publish"
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, "Ready to Published");
		/* 
		 * The Products table only lists out Products which version status is Draft, Tuning and Marketing status must both be Approved
		 */
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(ProductModel.PRODUCT_TABLE, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(ProductModel.PRODUCT_TABLE, Constant.TUNNING_STATUS, Constant.APPROVED));
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(ProductModel.PRODUCT_TABLE, Constant.MARKETING_STATUS, Constant.APPROVED));
		// Delete product
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the Products table lists out correct items when filtering with "Published"
	 */
	@Test
	public void TC030DCAA_19(){
		productControl.addLog("ID : TC030DCAA_19 : Verify that the accessories table lists out correct items when filtering with 'Published'");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Change report filter to"Published"
		 */
		/*
		 * Precondition: Create product has status "Published"
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 3. Change report filter to "Ready to Publish"
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, "Recently Published");
		/* 
		 * The Products table only lists out Products which Published status is Published
		 */
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(ProductModel.PRODUCT_TABLE, Constant.PUBLISHED, Constant.PUBLISHED));
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);		
	}
	
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Need Attention".
	 */
	@Test
	public void TC030DCAA_20(){
		productControl.addLog("ID : TC030DCAA_20 : Verify that the accessories table lists out correct items when filtering with 'Need Attention'.");
		productControl.addErrorLog("PDPP-1290 :Failed by Bug");
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Accessories" page
		3. Change report filter to "Need Attention"
		VP: The Products table only lists out Products which Published status is any, Tuning and Marketing status 
		include one of following: Tuning is Declined or Revoked, Marketing is Declined or Revoked.
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Create a product which Tuning is Revoked and Marketing is Unsubmitted
		productControl.click(DTSHome.LINK_ACCESSORIES);
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> revUnsubPro = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true,true,false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),revUnsubPro);
		productWf.approveTuning();
		productWf.revokeTuning();
		// Create a product which Tuning is Revoked and Marketing is DTS Uploaded
		productControl.click(DTSHome.LINK_ACCESSORIES);
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> revDTSUpPro = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true,true,true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),revDTSUpPro);
		productWf.approveTuning();
		productWf.revokeTuning();
		// Create a product which Tuning is Approved and Marketing is Revoked
		productControl.click(DTSHome.LINK_ACCESSORIES);
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> appRevPro = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true,true,true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),appRevPro);
		productWf.approveTuning();
		productWf.approveMarketing();
//		productControl.click(ProductDetailModel.REVOKE_MARKETING_APPROVAL);
		productWf.revokeMarketing();
		// Create a product which Tuning is Approved and Marketing is Declined
		productControl.click(DTSHome.LINK_ACCESSORIES);
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> appDecPro = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true,true,true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),appDecPro);
		productWf.approveTuning();
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
//		productControl.click(ProductDetailModel.DECLINE_MARKETING);
		productWf.declineMarketing();
		// Create a product which Tuning is Partner Declined and Marketing is Unsubmitted
		productControl.click(DTSHome.LINK_ACCESSORIES);
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> partrevUnsubPro = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true,false,true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),partrevUnsubPro);
		loginControl.logout();
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Create a product which Tuning is DTS Declined and Marketing is Unsubmitted
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dtsdecUnsubPro = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true,false,true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),dtsdecUnsubPro);
		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(partrevUnsubPro.get("name"));
		productWf.declineTuning();
		loginControl.logout();
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(DTSHome.LINK_ACCESSORIES);
		productControl.selectAccessorybyName(dtsdecUnsubPro.get("name"));
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productWf.declineTuning();
		// 3. Change report filter to "Needs Attention"
		productControl.click(DTSHome.LINK_ACCESSORIES);
		productControl.selectReportsFilterByText(PageHome.accessoryFilterSelect, "Needs Attention");
		// Check Tunning status is Declined or Revoked
		// Check Marketing status is Declined or Revoked
//		ArrayList<String> Array_Tuning = userControl.getDataByHeaderText(PageHome.BRAND_ACCESSORIES, Constant.TUNNING_STATUS);
//		ArrayList<String> Array_Market = userControl.getDataByHeaderText(PageHome.BRAND_ACCESSORIES, Constant.MARKETING_STATUS);
//		ArrayList<String> Array_Rating = userControl.getDataByHeaderText(PageHome.BRAND_ACCESSORIES, Constant.HEADPHONE_X_RATING);
		/*
		 * VP: The Products table only lists out Products which Published status is any, Tuning and Marketing status 
		include one of following: Tuning is Declined or Revoked, Marketing is Declined or Revoked. And 
		Tuning is Approved but Headphone: X Rating is in "Undetermined" state.
		 */
//		Assert.assertTrue(productControl.isNeedsAttention(Array_Tuning, Array_Market, Array_Rating));
		// Check if PDPP-1290 : 030 Products All Active Models: The "Need Attention" filtering does not display products which Tuning is Approved but the HeadphoneX: Tuning Rating is Undetermined
		ArrayList<String> NeedsAttentions = userControl.getDataByHeaderText(PageHome.BRAND_ACCESSORIES, Constant.MODEL);
		Assert.assertTrue(ListUtil.checkListContain(NeedsAttentions,revUnsubPro.get("name")));
		Assert.assertTrue(ListUtil.checkListContain(NeedsAttentions,revDTSUpPro.get("name")));
		Assert.assertTrue(ListUtil.checkListContain(NeedsAttentions,appRevPro.get("name")));
		Assert.assertTrue(ListUtil.checkListContain(NeedsAttentions,appDecPro.get("name")));
		Assert.assertTrue(ListUtil.checkListContain(NeedsAttentions,dtsdecUnsubPro.get("name")));
		Assert.assertTrue(ListUtil.checkListContain(NeedsAttentions,partrevUnsubPro.get("name")));

		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(revUnsubPro.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		productControl.selectAccessorybyName(revDTSUpPro.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		productControl.selectAccessorybyName(appRevPro.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		productControl.selectAccessorybyName(appDecPro.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		productControl.selectAccessorybyName(dtsdecUnsubPro.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		productControl.selectAccessorybyName(partrevUnsubPro.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Suspended"
	 */
	@Test
	public void TC030DCAA_21(){
		productControl.addLog("ID : TC030DCAA_21 : Verify that the accessories table lists out correct items when filtering with 'Suspended'");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Change report filter to "Suspended"
		 */
		/*
		 * Precondition: Create product has status "Suspended"
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Suspend product
		productControl.click(ProductDetailModel.SUSPEND);
		productControl.selectConfirmationDialogOption("Suspend");
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 3. Change report filter to "Ready to Publish"
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, "Suspended");
		/* 
		 * The Products table only lists out Products which Published status is Published
		 */
		Assert.assertTrue(productControl.checkAllValueOfColumnProduct(ProductModel.PRODUCT_TABLE, Constant.PUBLISHED, Constant.ACCESSORY_SUSPENDED));
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, "Suspended");
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);		
	}
	
	/*
	 * Verify that "DTS Tracking ID" column displays correct value of product's DTS UUID
	 */
	@Test
	public void TC030DCAA_22(){
		productControl.addLog("ID : TC030DCAA_22 : Verify that 'DTS Tracking ID' column displays correct value of product's DTS UUID");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Products" page
			4. Click "Add Product" link
			5. Fill all values into required fields
			6. Click "Save" link
			7. Notice "Render Entity UUID" of product
			8. Navigate to "Products" page
			9. Find above product
			VP: Verify that "Render Entity UUID" column displays product's Render Entity UUID matching with correct model's name
		 */
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		String UUIDvalue = productControl.getTextByXpath(ProductDetailModel.RENDER);
		// 8. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 9. Find above product
		// VP: Verify that "Render Entity UUID" column displays product's Render Entity UUID matching with correct model's name
		Assert.assertTrue(productControl.checkValueOfProductTable(data.get("name"), UUIDvalue, 9));
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);		
	}

}
