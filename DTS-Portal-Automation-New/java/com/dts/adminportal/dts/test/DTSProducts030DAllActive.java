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
		Assert.assertFalse(productControl.isElementPresent(ProductModel.PRODUCT_TABLE));
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
		 * "Headphone X Rating" and 
		 * "Marketing Status"
		 * 
		 */
		ArrayList<String> colums = productControl.getTableHeader(PageHome.ACCESSORY_THREAD);
		Boolean match = ListUtil.containsAll(colums, Constant.getHeaderAccessoriesTable());
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
		Assert.assertTrue(productControl.verifyValueColumn(ProductModel.PRODUCT_TABLE, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		// Tuning status is DTS request pending.
		Assert.assertTrue(productControl.verifyValueColumn(ProductModel.PRODUCT_TABLE, Constant.TUNNING_STATUS, Constant.DTS_REQUEST_PENDING));
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
		Assert.assertTrue(productControl.verifyValueColumn(PageHome.BRAND_ACCESSORIES, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		Assert.assertTrue(productControl.verifyValueColumn(PageHome.BRAND_ACCESSORIES, Constant.TUNNING_STATUS, Constant.PENDING_PARTNER_APPROVAL));
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
		Assert.assertTrue(productControl.verifyValueColumn(ProductModel.PRODUCT_TABLE, "Version", "Draft"));
		Assert.assertTrue(productControl.verifyValueColumn(ProductModel.PRODUCT_TABLE, "Tuning Status", "Pending DTS Review"));
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
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
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
		Assert.assertTrue(productControl.verifyValueColumn(ProductModel.PRODUCT_TABLE, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		Assert.assertTrue(productControl.verifyValueColumn(ProductModel.PRODUCT_TABLE, Constant.TUNNING_STATUS, Constant.APPROVED));
		Assert.assertTrue(productControl.verifyValueColumn(ProductModel.PRODUCT_TABLE, Constant.MARKETING_STATUS, Constant.UNSUBMITTED));
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
		Assert.assertTrue(productControl.verifyValueColumn(ProductModel.PRODUCT_TABLE, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		Assert.assertTrue(productControl.verifyValueColumn(ProductModel.PRODUCT_TABLE, Constant.TUNNING_STATUS, Constant.APPROVED));
		Assert.assertTrue(productControl.verifyValueColumn(ProductModel.PRODUCT_TABLE, Constant.MARKETING_STATUS, Constant.PENDING_DTS_MARKETING_REVIEW));
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
		Assert.assertTrue(productControl.verifyValueColumn(ProductModel.PRODUCT_TABLE, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		Assert.assertTrue(productControl.verifyValueColumn(ProductModel.PRODUCT_TABLE, Constant.TUNNING_STATUS, Constant.APPROVED));
		Assert.assertTrue(productControl.verifyValueColumn(ProductModel.PRODUCT_TABLE, Constant.MARKETING_STATUS, Constant.APPROVED));
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
		Assert.assertTrue(productControl.verifyValueColumn(ProductModel.PRODUCT_TABLE, Constant.PUBLISHED, Constant.PUBLISHED));
		// Delete product
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
		include one of following: Tuning is Declined or Revoked, Marketing is Declined or Revoked. And 
		Tuning is Approved but Headphone: X Rating is in "Undetermined" state.
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Accessories" page
		//Assert.assertEquals(true, productControl.click(DTSHome.LINK_ACCESSORIES));
		productControl.click(DTSHome.LINK_ACCESSORIES);
		// 3. Change report filter to "Ready to Publish"
		productControl.selectReportsFilterByText(PageHome.accessoryFilterSelect, "Needs Attention");
		// Check Tunning status is Declined or Revoked
		// Check Marketing status is Declined or Revoked
		ArrayList<String> Array_Tuning = userControl.getDataByHeaderText(PageHome.BRAND_ACCESSORIES, Constant.TUNNING_STATUS);
		ArrayList<String> Array_Market = userControl.getDataByHeaderText(PageHome.BRAND_ACCESSORIES, Constant.MARKETING_STATUS);
		System.out.println(Array_Tuning);
		System.out.println(Array_Market);
		int size = Array_Tuning.size();
	//	for (int i = 0; i <= size ;i++) {
//			if (Array_Tuning.get(i) == "Revoked"){
//				   Assert.assertEquals("Unsubmitted" , Array_Market.get(i));
//				productControl.addLog("Passed Tuning Revoked - Marketing Unsubmitted");
//			if (Array_Tuning.get(i) == "Approved"){
//				Assert.assertEquals("Revoked" , Array_Market.get(i));
//				productControl.addLog("Passed Tuning Approved - Marketing Revoked");}
//			
//				else {
//					Assert.assertEquals("Declined" , Array_Market.get(i));
//					productControl.addLog("Passed Tuning Approved - Marketing Declined");
//				}
//			if (Array_Tuning.get(i) == "DTS Declined"){
//				Assert.assertEquals("Unsubmitted" , Array_Market.get(i)); 
//				productControl.addLog("Passed Tuning DTS Declined - Marketing Unsubmitted");
//			if (Array_Tuning.get(i) == "Partner Declined"){
//				Assert.assertEquals("Unsubmitted" , Array_Market.get(i));	
//				productControl.addLog("Passed Tuning Partner Declined - Marketing Unsubmitted");
//				}
//			}
//			}
		for (int i = 0; i < size ; i++) {
		if ((Array_Tuning.get(i).equals("Revoked")) && (Array_Market.get(i).equals("Unsubmitted"))){
			productControl.addLog("Passed Tuning Revoked - Marketing Unsubmitted");
		}
		else if ((Array_Tuning.get(i).equals("Approved")) && (Array_Market.get(i).equals("Revoked"))){
			productControl.addLog("Passed Tuning Approved - Marketing Revoked");
		}
			else { if ((Array_Tuning.get(i).equals("Approved")) && (Array_Market.get(i).equals("Declined"))){
				productControl.addLog("Passed Tuning Approved - Marketing Declined");
		}
			
				else {if ((Array_Tuning.get(i).equals("DTS Declined")) && (Array_Market.get(i).equals("Unsubmitted"))){
					productControl.addLog("Passed Tuning DTS Declined - Marketing Unsubmitted");
		}
					else {if ((Array_Tuning.get(i).equals("Partner Declined")) && (Array_Market.get(i).equals("Unsubmitted"))){
						productControl.addLog("Passed Tuning Partner Declined - Marketing Unsubmitted");
		} 
					
		}
				}
		}
		}
//			Assert.assertEquals(Array_Tuning.get(i) == "Revoked" , Array_Market.get(i)== "Unsubmitted"); 
//			productControl.addLog("Passed Tuning Revoked - Marketing Unsubmitted");
//			Assert.assertEquals(Array_Tuning.get(i) == "Approved" , Array_Market.get(i)== "Revoked");  
//			productControl.addLog("Passed Tuning Approved - Marketing Revoked");
//			Assert.assertEquals(Array_Tuning.get(i) == "Approved" , Array_Market.get(i)== "Declined");  
//			productControl.addLog("Passed Tuning Approved - Marketing Declined");
//			Assert.assertEquals(Array_Tuning.get(i) == "DTS Declined" , Array_Market.get(i)== "Unsubmitted"); 
//			productControl.addLog("Passed Tuning DTS Declined - Marketing Unsubmitted");
//			Assert.assertEquals(Array_Tuning.get(i) == "Partner Declined" , Array_Market.get(i)== "Unsubmitted");  
//			productControl.addLog("Passed Tuning Partner Declined - Marketing Unsubmitted");
		
		
			
			}
//				productControl.addLog("Passed Tuning Approved - Marketing Revoked");
//				while  (Array_Tuning.get(i) == "Approved" && Array_Market.get(i)== "Revoked"){
//					productControl.addLog("Passed Tuning Approved - Marketing Revoked");
			//if (Array_Tuning.get(i) == "Revoked" && Array_Market.get(i)== "Unsubmitted"){
				
			//}
		
		
	//	boolean isPass = productControl.verifyValueColumn(PageHome.BRAND_ACCESSORIES, Constant.TUNNING_STATUS, Constant.getListTuningDeclined());
		//Assert.assertEquals(true, isPass);
		
		//isPass = productControl.verifyValueColumn(PageHome.BRAND_ACCESSORIES, Constant.MARKETING_STATUS, Constant.getListNeedsAttention());
		//Assert.assertEquals("Pass", isPass);
//	}
	
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
		Assert.assertTrue(productControl.verifyValueColumn(ProductModel.PRODUCT_TABLE, Constant.PUBLISHED, Constant.ACCESSORY_SUSPENDED));
		// Delete product
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);		
	}

}
