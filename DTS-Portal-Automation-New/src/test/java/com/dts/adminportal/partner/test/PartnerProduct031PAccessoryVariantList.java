package com.dts.adminportal.partner.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AccessoryVariants;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.TuningRequestForm;
import com.dts.adminportal.model.UserInfo;
import com.dts.adminportal.model.VariantInfo;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.TestData;

public class PartnerProduct031PAccessoryVariantList extends BasePage{
	
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
	}
	
	/*
	 Verify that the "Reports" dropdown contains correct items.
	 */
	@Test
	public void TC031PAVL_01(){
		userControl.addLog("ID : TC031PAVL_01 : Verify that the 'Reports' dropdown contains correct items.");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click on number of variants link
			4. Click on "Reports" combo-box 
			VP: The variants table is displayed, and default item of "Reports" filter is "All Active".
			VP: Verify that the "Reports" filter includes: All Active, Recently Published, Recently Added, Draft, Tuning Request , Tuning Request - Overdue, Partner Tuning Review, Tuning Declined, Tuning Revoked, Headphone:X Rating A+, 
			Headphone:X Rating A, Ready for Marketing, DTS Marketing Review, DTS Marketing Review - Overdue, Marketing Declined, Marketing Revoked, Ready to Publish, Ready to Published - Overdue, Need Attention, Suspended.

		 */
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		userControl.click(PageHome.LINK_USERS);
		userControl.dtsSelectUserByEmail(PARTNER_USER);
		userControl.click(UserInfo.EDIT);
		userControl.enableAllPrivileges();
		userControl.click(AddUser.SAVE);
		loginControl.logout();
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Create a product
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		// Create a variant
		productControl.click(ProductDetailModel.ADD_VARIANT);
		Hashtable<String,String> dataVariant = TestData.variantData(false, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click on number of variants link
		productControl.clickOnNumberOfVariants(ProductModel.PRODUCT_TABLE, ProductModel.ProductTblHeader.VARIANTS.getName(), dataProduct.get("name"));
		// 4. Click on "Reports" combo-box
		// VP: The variants table is displayed, and default item of "Reports" filter is "All Active".
		Assert.assertEquals(appDeviceControl.getItemSelected(AccessoryVariants.VARIANT_FILTER), AccessoryVariants.VariantStatus.ALL_ACTIVE.getName());
		/* 
		 * Verify that the "Reports" filter includes: All Active, Recently Published, Recently Added, Draft, Tuning Request , Tuning Request - Overdue, Partner Tuning Review, Tuning Declined, Tuning Revoked, Headphone:X Rating A+,
		 * Headphone:X Rating A, Ready for Marketing, DTS Marketing Review, DTS Marketing Review - Overdue, Marketing Declined, Marketing Revoked, Ready to Publish, Ready to Published - Overdue, Need Attention, Suspended. 
		 */
		ArrayList<String> typeList = productControl.getAllComboboxOption(AccessoryVariants.VARIANT_FILTER);
		Assert.assertTrue(ListUtil.containsAll(typeList, AccessoryVariants.VariantStatusPartner.getNames()));
		// Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		
	}
	
	/*
	 Verify that the number of variant link is unable to click on when it's value is zero
	 */
	@Test
	public void TC031PAVL_02(){
		userControl.addLog("ID : TC031PAVL_02 : Verify that the number of variant link is unable to click on when it's value is zero");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click on number of variants link
			VP: The 031P Variant list page is not displayed
		 */
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		userControl.click(PageHome.LINK_USERS);
		userControl.dtsSelectUserByEmail(PARTNER_USER);
		userControl.click(UserInfo.EDIT);
		userControl.enableAllPrivileges();
		userControl.click(AddUser.SAVE);
		loginControl.logout();
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Create a product
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click on number of variants link
		productControl.clickOnNumberOfVariants(ProductModel.PRODUCT_TABLE, ProductModel.ProductTblHeader.VARIANTS.getName(), dataProduct.get("name"));
		Assert.assertFalse(productControl.isElementPresent(AccessoryVariants.ACCESSORY_VARIANT_LABEL));
		// Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		
	}
	
	/*
	 Verify that the Variants table is hidden and displays text "No models match these criteria" when the report filter is empty.
	 */
	@Test
	public void TC031PAVL_03(){
		userControl.addLog("ID : TC031PAVL_03 : Verify that the Variants table is hidden and displays text 'No models match these criteria' when the report filter is empty.");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click on number of variants link
			4. Switch to another option in Reports filter
			VP: The Variants table is replaced by text "No models match these criteria".
			
		 */
		// 1. Log into DTS portal as a DTS user
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		userControl.click(PageHome.LINK_USERS);
//		userControl.dtsSelectUserByEmail(PARTNER_USER);
//		userControl.click(UserInfo.EDIT);
//		userControl.enableAllPrivileges();
//		userControl.click(AddUser.SAVE);
//		loginControl.logout();
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Create a product
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		// Create a variant
		productControl.click(ProductDetailModel.ADD_VARIANT);
		Hashtable<String,String> dataVariant = TestData.variantData(false, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click on number of variants link
		productControl.clickOnNumberOfVariants(ProductModel.PRODUCT_TABLE, ProductModel.ProductTblHeader.VARIANTS.getName(), dataProduct.get("name"));
		productControl.selectFilterByName(AccessoryVariants.VARIANT_FILTER, AccessoryVariants.VariantStatus.SUSPENDED.getName());
		// VP: The Variants table is replaced by text "No models match these criteria".
		Assert.assertTrue(productControl.getTextByXpath(AccessoryVariants.VARIANT_TABLE_DATA).equals(AccessoryVariants.No_Models_Match_These_Criteria));
		// Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		
	}
	
	/*
	 Verify that the Variants table display seven columns properly
	 */
	@Test
	public void TC031PAVL_04(){
		userControl.addLog("ID : TC031PAVL_04 : Verify that the Variants table display seven columns properly");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click on number of variants link
			VP: Verify that the Variants table have seven columns "Picture", "Product", "Published", "Version", "Tuning Status", "Headphone X Rating" and "Marketing Status"
			
		 */
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		userControl.click(PageHome.LINK_USERS);
		userControl.dtsSelectUserByEmail(PARTNER_USER);
		userControl.click(UserInfo.EDIT);
		userControl.enableAllPrivileges();
		userControl.click(AddUser.SAVE);
		loginControl.logout();
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Create a product
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		// Create a variant
		productControl.click(ProductDetailModel.ADD_VARIANT);
		Hashtable<String,String> dataVariant = TestData.variantData(false, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click on number of variants link
		productControl.clickOnNumberOfVariants(ProductModel.PRODUCT_TABLE, ProductModel.ProductTblHeader.VARIANTS.getName(), dataProduct.get("name"));
		// VP: Verify that the Variants table have seven columns "Picture", "Product", "Published", "Version", "Tuning Status", "Headphone X Rating" and "Marketing Status"
		ArrayList <String> headers = productControl.getAllColumnHeader(AccessoryVariants.VARIANT_TABLE);
		Assert.assertTrue(ListUtil.containsAll(headers, AccessoryVariants.VariantTblHeader.getNames()));
		// Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 Verify that the Variants table lists out correct items when filtering with "Draft".
	 */
	@Test
	public void TC031PAVL_05(){
		userControl.addLog("ID : TC031PAVL_05 : Verify that the Variants table lists out correct items when filtering with 'Draft'.");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click on number of variants link
			4. Change report filter to "Draft"
			The Variants table only lists out Products which version status is Draft.
			
		 */
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		userControl.click(PageHome.LINK_USERS);
		userControl.dtsSelectUserByEmail(PARTNER_USER);
		userControl.click(UserInfo.EDIT);
		userControl.enableAllPrivileges();
		userControl.click(AddUser.SAVE);
		loginControl.logout();
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Create a product
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		// Create a variant
		productControl.click(ProductDetailModel.ADD_VARIANT);
		Hashtable<String,String> dataVariant = TestData.variantData(false, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click on number of variants link
		productControl.clickOnNumberOfVariants(ProductModel.PRODUCT_TABLE, ProductModel.ProductTblHeader.VARIANTS.getName(), dataProduct.get("name"));
		productControl.selectFilterByName(AccessoryVariants.VARIANT_FILTER, AccessoryVariants.VariantStatus.VERSION_DRAFT.getName());
		Assert.assertTrue(productControl.checkAllValueOfColumnVariant(AccessoryVariants.VARIANT_TABLE, AccessoryVariants.VariantTblHeader.VERSION.getName(), AccessoryVariants.VariantStatus.VERSION_DRAFT.getName()));
		// Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 Verify that the Variants table lists out correct items when filtering with "Tuning Request ".
	 */
	@Test
	public void TC031PAVL_06(){
		userControl.addLog("ID : TC031PAVL_06 : Verify that the Variants table lists out correct items when filtering with 'Tuning Request '.");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click on number of variants link
			4. Change report filter to "Tuning Request "
			VP: The Variants table only lists out Products which version status is Draft and the Tuning status is DTS request .
			
		 */
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Create a product
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		// Create a variant
		productControl.click(ProductDetailModel.ADD_VARIANT);
		Hashtable<String,String> dataVariant = TestData.variantData(false, false, false);
		dataVariant.remove("save");
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_TUNNING);
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		productControl.click(ProductDetailModel.REQUEST_TUNING);
//		productControl.selectACheckbox(TuningRequestForm.AGREE);
//		productControl.click(TuningRequestForm.SUBMIT);
		// Submit tuning form
		productControl.selectACheckbox(TuningRequestForm.AGREE);
		productControl.click(TuningRequestForm.SUBMIT);

		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click on number of variants link
		productControl.clickOnNumberOfVariants(ProductModel.PRODUCT_TABLE, ProductModel.ProductTblHeader.VARIANTS.getName(), dataProduct.get("name"));
		// 4. Change report filter to "Tuning Request "
		productControl.selectFilterByName(AccessoryVariants.VARIANT_FILTER, AccessoryVariants.VariantStatus.TUNING_REQUEST_PENDING.getName());
		// VP: The Variants table only lists out Products which version status is Draft and the Tuning status is DTS request .
		Assert.assertTrue(productControl.checkAllValueOfColumnVariant(AccessoryVariants.VARIANT_TABLE, AccessoryVariants.VariantTblHeader.VERSION.getName(), AccessoryVariants.VariantStatus.VERSION_DRAFT.getName()));
		Assert.assertTrue(productControl.checkAllValueOfColumnVariant(AccessoryVariants.VARIANT_TABLE, AccessoryVariants.VariantTblHeader.TUNING_STATUS.getName(), AccessoryVariants.TuningStatus.DTS_REQUEST_PENDING.getName()));
		// Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 Verify that the Variants table lists out correct items when filtering with "Partner Tuning Review".
	 */
	@Test
	public void TC031PAVL_07(){
		userControl.addLog("ID : TC031PAVL_07 : Verify that the Variants table lists out correct items when filtering with 'Partner Tuning Review'.");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click on number of variants link
			4. Change report filter to "Partner Tuning Review"
			VP: The Variants table only lists out Products which version status is Draft and the Tuning status is  Partner Approval.
			
		 */
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		userControl.click(PageHome.LINK_USERS);
//		userControl.dtsSelectUserByEmail(PARTNER_USER);
//		userControl.click(UserInfo.EDIT);
//		userControl.enableAllPrivileges();
//		userControl.click(AddUser.SAVE);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// Create a product
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		// Create a variant
		productControl.click(ProductDetailModel.ADD_VARIANT);
		Hashtable<String,String> dataVariant = TestData.variantData(true, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		loginControl.logout();
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click on number of variants link
		productControl.clickOnNumberOfVariants(ProductModel.PRODUCT_TABLE, ProductModel.ProductTblHeader.VARIANTS.getName(), dataProduct.get("name"));
		productControl.selectFilterByName(AccessoryVariants.VARIANT_FILTER, AccessoryVariants.VariantStatus.PARTNER_TUNING_REVIEW.getName());
		Assert.assertTrue(productControl.checkAllValueOfColumnVariant(AccessoryVariants.VARIANT_TABLE, AccessoryVariants.VariantTblHeader.VERSION.getName(), AccessoryVariants.VariantStatus.VERSION_DRAFT.getName()));
		Assert.assertTrue(productControl.checkAllValueOfColumnVariant(AccessoryVariants.VARIANT_TABLE, AccessoryVariants.VariantTblHeader.TUNING_STATUS.getName(), AccessoryVariants.TuningStatus.PENDING_PARTNER_REVIEW.getName()));
		// Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 Verify that the Variants table lists out correct items when filtering with "DTS Tuning Review".
	 */
	@Test
	public void TC031PAVL_08(){
		userControl.addLog("ID : TC031PAVL_08 : Verify that the Variants table lists out correct items when filtering with 'DTS Tuning Review'.");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click on number of variants link
			4. Change report filter to "DTS Tuning Review"
			VP: The Variants table only lists out Products which version status is Draft and the Tuning status is  DTS Approval.
			
		 */
		// Log into DTS portal as a partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Create a product
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		// Create a variant
		productControl.click(ProductDetailModel.ADD_VARIANT);
		Hashtable<String,String> dataVariant = TestData.variantData(true, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(VariantInfo.APPROVE_TUNING);
		// 1. Log into DTS portal as a Partner user
		loginControl.logout();
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 3. Click on number of variants link
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.clickOnNumberOfVariants(ProductModel.PRODUCT_TABLE, ProductModel.ProductTblHeader.VARIANTS.getName(), dataProduct.get("name"));
		productControl.selectFilterByName(AccessoryVariants.VARIANT_FILTER, AccessoryVariants.VariantStatus.DTS_TUNING_REVIEW.getName());
		Assert.assertTrue(productControl.checkAllValueOfColumnVariant(AccessoryVariants.VARIANT_TABLE, AccessoryVariants.VariantTblHeader.VERSION.getName(), AccessoryVariants.VariantStatus.VERSION_DRAFT.getName()));
		Assert.assertTrue(productControl.checkAllValueOfColumnVariant(AccessoryVariants.VARIANT_TABLE, AccessoryVariants.VariantTblHeader.TUNING_STATUS.getName(), AccessoryVariants.TuningStatus.PENDING_DTS_REVIEW.getName()));
		// Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 Verify that the Variants table lists out correct items when filtering with "Ready for Marketing".
	 */
	@Test
	public void TC031PAVL_09(){
		userControl.addLog("ID : TC031PAVL_09 : Verify that the Variants table lists out correct items when filtering with 'Ready for Marketing'.");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click on number of variants link
			4. Change report filter to"Ready for Marketing"
			VP: The Variants table only lists out Products which version status is Draft, Tuning status must be Approved and Marketing status must be Unsubmitted.
			
		 */
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// Create a product
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		// Create a variant
		productControl.click(ProductDetailModel.ADD_VARIANT);
		Hashtable<String,String> dataVariant = TestData.variantData(true, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		productWf.approveTuning();
		loginControl.logout();
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click on number of variants link
		productControl.clickOnNumberOfVariants(ProductModel.PRODUCT_TABLE, ProductModel.ProductTblHeader.VARIANTS.getName(), dataProduct.get("name"));
		productControl.selectFilterByName(AccessoryVariants.VARIANT_FILTER, AccessoryVariants.VariantStatus.READY_FOR_MARKETING.getName());
		Assert.assertTrue(productControl.checkAllValueOfColumnVariant(AccessoryVariants.VARIANT_TABLE, AccessoryVariants.VariantTblHeader.VERSION.getName(), AccessoryVariants.VariantStatus.VERSION_DRAFT.getName()));
		Assert.assertTrue(productControl.checkAllValueOfColumnVariant(AccessoryVariants.VARIANT_TABLE, AccessoryVariants.VariantTblHeader.TUNING_STATUS.getName(), AccessoryVariants.TuningStatus.APPROVED.getName()));
		Assert.assertTrue(productControl.checkAllValueOfColumnVariant(AccessoryVariants.VARIANT_TABLE, AccessoryVariants.VariantTblHeader.MARKETING_STATUS.getName(), AccessoryVariants.MarketingStatus.UNSUBMITTED.getName()));
		// Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 Verify that the Variants table lists out correct items when filtering with "DTS Marketing Review".
	 */
	@Test
	public void TC031PAVL_10(){
		userControl.addLog("ID : TC031PAVL_10 : Verify that the Variants table lists out correct items when filtering with 'DTS Marketing Review'.");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click on number of variants link
			4. Change report filter to  "DTS Marketing Review"
			VP: The Variants table only lists out Products which version status is Draft, Tuning status is Approved and Marketing status is  DTS Marketing Review.

			
		 */
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// Create a product
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		// Create a variant
		productControl.click(ProductDetailModel.ADD_VARIANT);
		Hashtable<String,String> dataVariant = TestData.variantData(true, true, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		productWf.approveTuning();
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_MARKETING_STEP_1);
		productControl.click(ProductDetailModel.MARKETING_ACTION);
		loginControl.logout();
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click on number of variants link
		productControl.clickOnNumberOfVariants(ProductModel.PRODUCT_TABLE, ProductModel.ProductTblHeader.VARIANTS.getName(), dataProduct.get("name"));
		productControl.selectFilterByName(AccessoryVariants.VARIANT_FILTER, AccessoryVariants.VariantStatus.DTS_MARKETING_REVIEW.getName());
		Assert.assertTrue(productControl.checkAllValueOfColumnVariant(AccessoryVariants.VARIANT_TABLE, AccessoryVariants.VariantTblHeader.VERSION.getName(), AccessoryVariants.VariantStatus.VERSION_DRAFT.getName()));
		Assert.assertTrue(productControl.checkAllValueOfColumnVariant(AccessoryVariants.VARIANT_TABLE, AccessoryVariants.VariantTblHeader.TUNING_STATUS.getName(), AccessoryVariants.TuningStatus.APPROVED.getName()));
		Assert.assertTrue(productControl.checkAllValueOfColumnVariant(AccessoryVariants.VARIANT_TABLE, AccessoryVariants.VariantTblHeader.MARKETING_STATUS.getName(), AccessoryVariants.MarketingStatus.DTS_MARKETING_REQUEST_REVIEW.getName()));
		// Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 Verify that the Variants table lists out correct items when filtering with "Ready to Publish".
	 */
	@Test
	public void TC031PAVL_11(){
		userControl.addLog("ID : TC031PAVL_11 : Verify that the Variants table lists out correct items when filtering with 'Ready to Publish'.");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click on number of variants link
			4. Change report filter to"Ready to Publish"
			The Variants table only lists out Products which version status is Draft, Tuning and Marketing status must both be Approved.
			
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Create a product
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		// Create a variant
		productControl.click(ProductDetailModel.ADD_VARIANT);
		Hashtable<String,String> dataVariant = TestData.variantData(true, true, true);
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		productWf.approveTuning();
		productWf.approveMarketing();
		loginControl.logout();
		// 1. Log into DTS portal as a Partner user
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click on number of variants link
		productControl.clickOnNumberOfVariants(ProductModel.PRODUCT_TABLE, ProductModel.ProductTblHeader.VARIANTS.getName(), dataProduct.get("name"));
		productControl.selectFilterByName(AccessoryVariants.VARIANT_FILTER, AccessoryVariants.VariantStatus.READY_TO_PUBLISHED.getName());
		Assert.assertTrue(productControl.checkAllValueOfColumnVariant(AccessoryVariants.VARIANT_TABLE, AccessoryVariants.VariantTblHeader.VERSION.getName(), AccessoryVariants.VariantStatus.VERSION_DRAFT.getName()));
		Assert.assertTrue(productControl.checkAllValueOfColumnVariant(AccessoryVariants.VARIANT_TABLE, AccessoryVariants.VariantTblHeader.TUNING_STATUS.getName(), AccessoryVariants.TuningStatus.APPROVED.getName()));
		Assert.assertTrue(productControl.checkAllValueOfColumnVariant(AccessoryVariants.VARIANT_TABLE, AccessoryVariants.VariantTblHeader.MARKETING_STATUS.getName(), AccessoryVariants.MarketingStatus.APPROVED.getName()));
		// Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 Verify that the Variants table lists out correct items when filtering with "Published".
	 */
	@Test
	public void TC031PAVL_12(){
		userControl.addLog("ID : TC031PAVL_12 : Verify that the Variants table lists out correct items when filtering with 'Published'.");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click on number of variants link
			4. Change report filter to"Published"
			The Variants table only lists out Products which Published status is Published.
			
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		productControl.click(PageHome.linkAccessories);
		// Create a product
//		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndPublish(dataProduct);
		// Create a variant
//		productControl.click(ProductDetailModel.ADD_VARIANT);
		Hashtable<String,String> dataVariant = TestData.variantData(true, true, true);
		productWf.addVariantAndPublish(dataVariant);
		loginControl.logout();
		// 1. Log into DTS portal as a Partner user
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click on number of variants link
		productControl.clickOnNumberOfVariants(ProductModel.PRODUCT_TABLE, ProductModel.ProductTblHeader.VARIANTS.getName(), dataProduct.get("name"));
		productControl.selectFilterByName(AccessoryVariants.VARIANT_FILTER, AccessoryVariants.VariantStatus.RECENTLY_PUBLISHED.getName());
		Assert.assertTrue(productControl.checkAllValueOfColumnVariant(AccessoryVariants.VARIANT_TABLE, AccessoryVariants.VariantTblHeader.PUBLISHED.getName(), AccessoryVariants.PUBLISHED));
		// Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 Verify that the Variants table lists out correct items when filtering with "Needs Attention".
	 */
	@Test
	public void TC031PAVL_13(){
		userControl.addLog("ID : TC031PAVL_13 : Verify that the Variants table lists out correct items when filtering with 'Needs Attention'.");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click on number of variants link
			4. Change report filter to"Needs Attention"
			VP: The Variants table only lists out Products which Published status is any, Tuning and Marketing status include one of following: Tuning is Declined or Revoked, Marketing is Declined or Revoked. Tuning is Approved but Headphone: X Rating is in "Undetermined" state.
			
		 */
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productControl.click(PageHome.linkAccessories);
		// Create a product
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		
		// Create a variant with tuning is revoked
		productControl.click(ProductDetailModel.ADD_VARIANT);
		Hashtable<String,String> RevokeTuningVariant = TestData.variantData(true, true, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), RevokeTuningVariant);
		productWf.approveTuning();
		productWf.revokeTuning();
		// Create a variant with tuning is declined
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.click(ProductDetailModel.ADD_VARIANT);
		Hashtable<String,String> DeclineTuningVariant = TestData.variantData(true, true, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), DeclineTuningVariant);
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productWf.declineTuning();
		// Create a variant with marketing is declined
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.click(ProductDetailModel.ADD_VARIANT);
		Hashtable<String,String> DeclineMarketingVariant = TestData.variantData(true, true, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), DeclineMarketingVariant);
		productWf.approveTuning();
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_MARKETING_STEP_1);
		productControl.click(ProductDetailModel.MARKETING_ACTION);
		productWf.declineMarketing();
		// Create a variant with marketing is revoked
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.click(ProductDetailModel.ADD_VARIANT);
		Hashtable<String,String> RevokeMarketingVariant = TestData.variantData(true, true, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), RevokeMarketingVariant);
		productWf.approveTuning();
		productWf.approveMarketing();
		productWf.revokeMarketing();
		
		// Create a variant with tuning is approved, but Headphone: X Rating is in "Undetermined" state.
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.click(ProductDetailModel.ADD_VARIANT);
		Hashtable<String,String> dataVariant = TestData.variantData(true, true, false);
		dataVariant.remove("save");
		dataVariant.remove("tuning rating");
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		loginControl.logout();
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.selectAVariantbyName(dataVariant.get("name"));
		// approve tuning
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		
		loginControl.logout();
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click on number of variants link
		productControl.clickOnNumberOfVariants(ProductModel.PRODUCT_TABLE, ProductModel.ProductTblHeader.VARIANTS.getName(), dataProduct.get("name"));
		productControl.selectFilterByName(AccessoryVariants.VARIANT_FILTER, AccessoryVariants.VariantStatus.NEEDS_ATTENTION.getName());
		Assert.assertTrue(productControl.checkReportNeedAttentions());
		ArrayList<String> NeedsAttentions = userControl.getDataByHeaderText(AccessoryVariants.VARIANT_TABLE, AccessoryVariants.VariantTblHeader.PRODUCT.getName());
		Assert.assertTrue(ListUtil.checkListContain(NeedsAttentions, RevokeTuningVariant.get("name")));
		Assert.assertTrue(ListUtil.checkListContain(NeedsAttentions, DeclineTuningVariant.get("name")));
		Assert.assertTrue(ListUtil.checkListContain(NeedsAttentions, RevokeMarketingVariant.get("name")));
		Assert.assertTrue(ListUtil.checkListContain(NeedsAttentions, DeclineMarketingVariant.get("name")));
		Assert.assertTrue(ListUtil.checkListContain(NeedsAttentions, dataVariant.get("name")));

		// Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 Verify that the Variants table lists out correct items when filtering with "Suspended".
	 */
	@Test
	public void TC031PAVL_14(){
		userControl.addLog("ID : TC031PAVL_14 : Verify that the Variants table lists out correct items when filtering with 'Suspended'.");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click on number of variants link
			4. Change report filter to "Suspended"
			The Variants table only lists out Products which Published status is Suspened.
			
		 */
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		productControl.click(PageHome.linkAccessories);
		// Create a product
//		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		productWf.addProductAndPublish(dataProduct);
		// Create a variant
		Hashtable<String,String> dataVariant = TestData.variantData(true, true, true);
		productWf.addVariantAndPublish(dataVariant);
		productControl.doSuspend(ProductDetailModel.SUSPEND_VARIANT);
//		productControl.click(ProductDetailModel.SUSPEND_VARIANT);
		loginControl.logout();
		// 1. Log into DTS portal as a Partner user
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click on number of variants link
		productControl.clickOnNumberOfVariants(ProductModel.PRODUCT_TABLE, ProductModel.ProductTblHeader.VARIANTS.getName(), dataProduct.get("name"));
		productControl.selectFilterByName(AccessoryVariants.VARIANT_FILTER, AccessoryVariants.VariantStatus.SUSPENDED.getName());
		Assert.assertTrue(productControl.checkAllValueOfColumnVariant(AccessoryVariants.VARIANT_TABLE, AccessoryVariants.VariantTblHeader.PUBLISHED.getName(), AccessoryVariants.VariantStatus.SUSPENDED.getName()));
		// Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
}