package com.dts.adminportal.partner.test;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PartnerVariantInfo;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.TuningRequestForm;
import com.dts.adminportal.model.VariantInfo;
import com.dts.adminportal.util.TestData;

public class PartnerProduct040DraftPublished extends BasePage{
	
	@Override
	protected void initData() {
	}	
	
//	@BeforeMethod
//	public void loginBeforeTest() {
//		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//	}
	
	/*
	 * Verify that partner user can edit accessory when the tuning status is "UNSUBMITTED".
	 */
	 
	@Test
	public void TC040PDP_01(){
		productControl.addLog("ID : TC040PDP_01 : Verify that partner user can edit accessory when"
				+ " the tuning status is 'UNSUBMITTED'");
		/*
		 *  1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Fill valid value into all required fields
			5. Click "Save" link
			VP: Verify that the tuning status is "UNSUBMITTED" and the "Edit" link is displayed
			6. Click "Edit" link
			7. Change accessory's value
			8. Click "Save" link
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		// 5. Click "Save" link
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * VP: Verify that the tuning status is "UNSUBMITTED" and the "Edit" link is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "UNSUBMITTED");
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.EDIT_MODE));
		// 6. Click "Edit" link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 7.Change accessory's value
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		productControl.editData(AddEditProductModel.MODEL_NAME, newName);
		// 8. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), newName);
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
	 }
	
	/*
	 * Verify that partner user can edit accessory when the tuning status is "Pending DTS Review"
	 */
	@Test
	public void TC040PDP_02(){
		productControl.addLog("ID : TC040PDP_02 :VVerify that partner user can edit accessory when the "
				+ "tuning status is 'Pending DTS Review'");
		/*
		 	Pre-Condition: partner user has "Add and manage Products" rights.
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Click "Add product" link
			4. Fill valid value into all required fields
			5. Upload valid tuning file
			6. Click "Save" link
			7. Click "Request Tuning Review"
			VP: Verify that the tuning status is "Pending DTS Review"
			8. Click "Edit" link
			9. Change product's information
			10. Click "Save" link
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		// 5. Upload valid tuning file
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 7. Click "Request Tuning Review"
		productControl.click(ProductDetailModel.REQUEST_TUNING);
//		productControl.click(TuningRequestForm.AGREE);
//		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		/*
		 * VP: Verify that the tuning status is "Pending DTS Review"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "PENDING DTS APPROVAL");
		// 8. Click "Edit" link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 9. Change product's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		productControl.editData(AddEditProductModel.MODEL_NAME, newName);
		// 10. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * Verify that The product's information is changed successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), newName);
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that partner user can edit accessory when the tuning status is "DTS Request Pending"
	 */
	@Test
	public void TC040PDP_03(){
		productControl.addLog("ID : TC040PDP_03 :Verify that partner user can edit accessory "
				+ "when the tuning status is 'DTS Request Pending'");
		/*
		 	1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Fill valid value into all required fields
			5. Do not upload valid tuning file
			6. Click "Save" link
			7. Click "Request Tuning Review"
			VP: Verify that the �Tuning Request Form� page is displayed
			8. Submit the �Tuning Request Form� page successfully
			VP: Verify that the tuning status is "DTS Request Pending"
			9. Click "Edit" link
			10. Change accessory's information
			11. Click "Save" link
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		// 5. Do not upload valid tuning file
		// 6. Click "Save" link
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);	
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		//7. Click "Request Tuning Review"
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		/*
		 * VP: Verify that the �Tuning Request Form� page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(TuningRequestForm.APPROVE_TUNNING_FORM()), true);
		//8. Submit the �Tuning Request Form� page successfully
		productControl.click(TuningRequestForm.AGREE);
		productControl.click(TuningRequestForm.SUBMIT);
		//VP: Verify that the tuning status is "DTS REQUEST PENDING"
		productControl.click(TuningRequestForm.PRODUCT_LINK);
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "DTS REQUEST PENDING");
		//9. Click "Edit" link
		productControl.click(ProductDetailModel.EDIT_MODE);
		//10. Change accessory's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(5);
		productControl.editData(AddEditProductModel.MODEL_NAME, newName);
		//11. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), newName);	
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that partner user can edit accessory when the tuning status is "APPROVED"
	 */
	@Test
	public void TC040PDP_04(){
		productControl.addLog("ID : TC040PDP_04 :Verify that partner user can edit accessory when the"
				+ " tuning status is 'APPROVED'");
		/*
		  	Pre-Condition: partner user has "Add and manage Products" rights.
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Select an product from Products table which its tuning status is approved
			4. Click "Edit" link
			5. Change product's information
			6. Click "Save" link
		 */
		/*
		 * Pre-condition: Create a product which tuning status is approved
		 */
		// Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
//		productControl.click(PageHome.linkAccessories);
//		// Click Add product link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		// Create product
//		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//		// Approve tuning
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
//		productControl.click(ProductDetailModel.APPROVE_TUNING);
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productWf.addProductAndTunningAndMarketing(data, false);
		
		// Log out
		productControl.logout();
		// Log into DTS portal as Partner user above
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select an product from Products table which its tuning status is approved
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * Verify that tuning status is Approved
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "APPROVED");
		// 4. Click "Edit" link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 5. Change product's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		productControl.editData(AddEditProductModel.MODEL_NAME, newName);
		// 6. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * Verify that The product's information is changed successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), newName);
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that partner user can edit accessory when the tuning status is "DECLINED"
	 */
	@Test
	public void TC040PDP_05(){
		productControl.addLog("ID : TC040PDP_05: Verify that partner user can edit accessory "
				+ "when the tuning status is 'DECLINED'");
		/*
		 	Pre-Condition: partner user has "Add and manage accessories" rights.
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table which its tuning status is declined
			4. Click "Edit" link
			5. Change accessory's information
			6. Click "Save" link
		 */
		
		/*
		 *  Pre-Condition: Create new accessory with tuning status is declined
		 */
		// Log in DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to accessories page
		productControl.click(PageHome.linkAccessories);
		// Click Add Accessory link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create accessory
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Decline tuning
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productWf.declineTuning();
		/*
		 * Verify that tuning status is "DECLINED"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "DECLINED");
		// Log out
		productControl.logout();
		/*
		 * **********************************************************************
		 */
		// 1. Log into DTS portal as a partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table which its tuning status is declined
		productControl.selectAccessorybyName(data.get("name"));
		// 4. Click "Edit" link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 5. Change accessory's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		productControl.editData(AddEditProductModel.MODEL_NAME, newName);
		// 6. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), newName);
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
	}	
	
	/*
	 * Verify that partner user can edit accessory's variant when the variant tuning status is "UNSUBMITTED".
	 */
	@Test
	public void TC040PDP_06(){
		productControl.addLog("ID : TC040PDP_06 :Verify that partner user can edit accessory's "
				+ "variant when the variant tuning status is 'UNSUBMITTED'.");
		/*
		  	Pre-Condition: partner user has "Add and manage Products" rights.
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Click "Add New Variant" link
			5. Fill valid value into all required fields
			6. Click "Save" link
			VP: Verify that the variant tuning status is "UNSUBMITTED" and the "Edit" link is displayed
			7. Click "Edit" link
			8. Change product's value
			9. Click "Save" link
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select an product from Products table
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),productData);
		// 4. Click "Add New Variant" link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 5. Fill valid value into all required fields
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.variantData(true, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), data);
		/*
		 * VP: Verify that the variant tuning status is "UNSUBMITTED" and the "Edit" link is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(PartnerVariantInfo.TUNING_STATUS), "UNSUBMITTED");
		Assert.assertTrue(productControl.isElementPresent(PartnerVariantInfo.EDIT_VARIANT));
		// 7. Click "Edit" link
		productControl.click(PartnerVariantInfo.EDIT_VARIANT);
		// 8. Change product's value
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		productControl.editData(AddEditProductModel.MODEL_NAME, newName);
		// 9. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * Verify that The product's information is changed successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(PartnerVariantInfo.DISPLAYNAME_DEFAULT), newName);
		Assert.assertEquals(productControl.getTextByXpath(PartnerVariantInfo.TITLE_NAME), newName);
		
		// Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(productData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that partner user can edit accessory's variant when the variant tuning status is "Pending DTS Review"
	 */
	@Test
	public void TC040PDP_07(){
		productControl.addLog("ID : TC040PDP_07 :Verify that partner user can edit accessory's "
				+ "variant when the variant tuning status is 'Pending DTS Review'");
		/*
			Pre-Condition: partner user has "Add and manage accessories" rights.
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
			4. Click "Add New Variant" link
			5. Fill valid value into all required fields
			6. Upload variant tuning file
			7. Click "Save" link
			8. Click "Request Tuning Review"
			VP: Verify that the variant tuning status is "Pending DTS Review"
			9. Click "Edit" link
			10. Change accessory's value
			11. Click "Save" link
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),productData);
		// 4. Click "Add New Variant" link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 5. Fill valid value into all required fields
		// 6. Upload variant tuning file
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.variantData(true, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), data);
		//8. Click "Request Tuning Review"
		productControl.click(VariantInfo.REQUEST_TUNING_REVIEW);
		/*
		 * VP: Verify that the variant tuning status is "Pending DTS Review"
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), "PENDING DTS APPROVAL");
		// 9. Click "Edit" link
		productControl.click(VariantInfo.EDIT_VARIANT);
		// 10. Change accessory's value
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		productControl.editData(AddEditProductModel.MODEL_NAME, newName);
		// 11. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.DISPLAYNAME_DEFAULT), newName);
		Assert.assertEquals(productControl.getTextByXpath(PartnerVariantInfo.TITLE_NAME), newName);
		
		// Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(productData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);

	}
	
	/*
	 * Verify that partner user can edit accessory's variant when the variant tuning status is "DTS Request Pending"
	 */
	@Test
	public void TC040PDP_08(){
		productControl.addLog("ID : TC040PDP_08 :Verify that partner user can edit accessory's "
				+ "variant when the variant tuning status is 'DTS Request Pending'");
		/* 
			Pre-Condition: partner user has "Add and manage Products" rights.
			
		 	1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Click "Add New Variant" link
			5. Fill valid value into all required fields
			6. Do not upload variant tuning file
			7. Click "Save" link
			8. Click "Request DTS Tuning"
			VP: Verify that the variant tuning status is "DTS Request Pending"
			9. Click "Edit" link
			10. Change product's information
			11. Click "Save" link
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),productData);
		// 4. Click "Add New Variant" link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 5. Fill valid value into all required fields
		// 6. Do not Upload variant tuning file
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.variantData(false, false, false);
		data.remove("save");
		productControl.addVariant(AddEditProductModel.getVariantHash(), data);
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_TUNNING);
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		//productControl.addVariant(AddEditProductModel.getVariantHash(), data);
		// 8. Click "Request Tuning"
		productControl.click(VariantInfo.REQUEST_DTS_TUNING);
		/*
		 * VP: Verify that the �Tuning Request Form� page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(TuningRequestForm.FORM_NAME));
		// 9. Submit the �Tuning Request Form� page successfully
		productControl.click(TuningRequestForm.AGREE);
		productControl.click(TuningRequestForm.SUBMIT);
		/*
		 * VP: Verify that the variant tuning status is "DTS REQUEST PENDING"
		 */
		productControl.click(TuningRequestForm.PRODUCT_LINK);
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.DTS_REQUEST_PENDING_STRING);
		// 10. Click "Edit" link
		productControl.click(VariantInfo.EDIT_VARIANT);
		// 11. Change accessory's value
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		productControl.editData(AddEditProductModel.MODEL_NAME, newName);
		// 12. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.DISPLAYNAME_DEFAULT),newName);
		Assert.assertEquals(productControl.getTextByXpath(PartnerVariantInfo.TITLE_NAME), newName);
		
		// Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(productData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);

	}
	
	/*
	 * Verify that partner user can edit accessory when the tuning status is "APPROVED"
	 */
	 
	@Test
	public void TC040PDP_09(){
		productControl.addLog("ID : TC040PDP_09 :Verify that partner user can edit accessory"
				+ " when the tuning status is 'APPROVED'");
		/*
		  	Pre-Condition: partner user has "Add and manage accessories" rights. 
		  	The accessory has at least one variant which its variant tuning status is "APPROVED"
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
			4. Select an accessory's variant which its variant tuning status is "APPROVED"
			5. Click "Edit" link
			6. Change accessory's information
			7. Click "Save" link
		 */
		
		/*
		 * Pre-Condition: partner user has "Add and manage accessories" rights. The accessory has at least one variant which its variant tuning status is "APPROVED"
		 */
		// Log in DTS portal as DTS user
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// Navigate to accessories page
//		productControl.click(PageHome.linkAccessories);
//		// Click Add Accessory link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		// Create accessory
//		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//		// Click Add new variant link
//		productControl.click(ProductDetailModel.ADD_VARIANT);
//		// Create new variant
//		Hashtable<String,String> dataVariant = TestData.variantData(true, false, false);
//		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
//		// Approve tuning
//		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
//		productControl.click(VariantInfo.APPROVE_TUNING);
		
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		Hashtable<String,String> dataVariant = TestData.variantData(true, false, false);
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productWf.addProductAndTunningAndMarketing(dataProduct, false);
		productWf.addVariantAndTunningAndMarketing(dataVariant, false);
		// Log out user
		productControl.logout();
		/*
		 * ***************************************************************************************
		 */
		// 1. Log into DTS portal as a partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		productControl.selectAccessorybyName(dataProduct.get("name"));
		// 4. Select an accessory's variant which its variant tuning status is "APPROVED"
		productControl.selectAVariantbyName(dataVariant.get("name"));
		// 5. Click "Edit" link
		productControl.click(VariantInfo.EDIT_VARIANT);
		// 6. Change accessory's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		productControl.editData(AddEditProductModel.MODEL_NAME, newName);
		// 7. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.DISPLAYNAME_DEFAULT), newName);
		Assert.assertEquals(productControl.getTextByXpath(PartnerVariantInfo.TITLE_NAME), newName);
		// Delete accessory
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that partner user can edit accessory when the tuning status is "DECLINED"
	 */
	@Test
	public void TC040PDP_10(){
		productControl.addLog("ID : TC040PDP_10: Verify that partner user can edit accessory when the tuning status is 'DECLINED'");
		/*
		 * Pre-Condition: partner user has "Add and manage accessories" rights. The accessory has at least one variant which its variant tuning status is "DECLINED" 
		 * 1. Log into DTS portal as a partner user 
		 * 2. Navigate to "Accessories" page 
		 * 3. Select an accessory from accessories table 
		 * 4. Select an accessory's variant which its variant tuning status is "DECLINED" 
		 * 5. Click "Edit" link 
		 * 6. Change accessory's information 
		 * 7. Click "Save" link
		 */
		/*
		 *  Pre-Condition: Create new accessory has one variant with tuning status is declined
		 */
		// Log in DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to accessories page
		productControl.click(PageHome.linkAccessories);
		// Click Add Accessory link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create accessory
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Click Add new variant link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// Create new variant
		Hashtable<String,String> dataVariant = TestData.variantData(true, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		// Decline tuning
		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		productWf.declineTuning();
		/*
		 * Verify that tuning status is DECLINED
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), "DECLINED");
		// Log out user
		productControl.logout();
		/*
		 * ****************************************************************
		 */
		// 1. Log into DTS portal as a partner user 
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page 
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		productControl.selectAccessorybyName(data.get("name"));
		// 4. Select an accessory's variant which its variant tuning status is "DECLINED" 
		productControl.selectAVariantbyName(dataVariant.get("name"));
		// 5. Click "Edit" link 
		productControl.click(VariantInfo.EDIT_VARIANT);
		// 6. Change accessory's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		productControl.editData(AddEditProductModel.MODEL_NAME, newName);
		// 7. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.DISPLAYNAME_DEFAULT), newName);
		Assert.assertEquals(productControl.getTextByXpath(PartnerVariantInfo.TITLE_NAME), newName);
		// Delete accessory
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}	
	
	/*
	 * Verify that partner user can edit accessory when the tuning status is "Using parent model's"
	 */
	@Test
	public void TC040PDP_11(){
		productControl.addLog("ID : TC040PDP_11: Verify that partner user can edit accessory when the tuning status is 'Using parent model's'");
		/*
			Pre-Condition: partner user has "Add and manage accessories" rights.
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
			4. Click "Add New Variant" link
			5. Fill valid value into all required fields
			6. Tick to check the "Use Parent's Tuning" checkbox
			7. Click "Save" link
			VP: Verify that the variant tuning status is "Using parent model's"
			8. Click "Edit" link
			9. Change accessory's information
			10. Click "Save" link
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),productData);
		// 4. Click "Add New Variant" link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 5. Fill valid value into all required fields
		// 6. Tick to check the "Use Parent's Tuning" checkbox
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.variantData(false, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), data);
		/*
		 * 	VP: Verify that the variant tuning status is "Using parent's model"
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), "USING PARENT'S MODEL");
		// 8. Click "Edit" link
		productControl.click(VariantInfo.EDIT_VARIANT);
		// 9. Change accessory's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		productControl.editData(AddEditProductModel.MODEL_NAME, newName);
		// 10. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.DISPLAYNAME_DEFAULT), newName);
		Assert.assertEquals(productControl.getTextByXpath(PartnerVariantInfo.TITLE_NAME), newName);
		
		// Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(productData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that partner user can edit accessory when the marketing approval status is "CLOSED"
	 */
	@Test
	public void TC040PDP_12(){
		productControl.addLog("ID : TC040PDP_12: Verify that partner user can edit accessory when the marketing approval status is 'CLOSED'");
		/*
		 	Pre-Condition: partner user has "Add and manage accessories" rights.
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table which its tunining status is any other than "APPROVED"
			VP: Verify that the marketing approval is "CLOSED"
			4. Click "Edit" link
			5. Change accessory's information
			6. Click "Save" link
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table which its tunining status is any other than "APPROVED"
		// Click Add Accessory link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create new accessory
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * VP: Verify that the marketing approval is "CLOSED"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "CLOSED");
		// 4. Click "Edit" link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 5. Change accessory's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		productControl.editData(AddEditProductModel.MODEL_NAME, newName);
		//6. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), newName);
		
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that partner user can edit accessory when the marketing approval status is "UNSUBMITTED"
	 */
	@Test
	public void TC040PDP_13(){
		productControl.addLog("ID : TC040PDP_13: Verify that partner user can edit accessory when the marketing approval status is 'UNSUBMITTED'");
		/*
		 	1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Fill valid value into all required fields
			5. Upload valid tuning file
			6. Click "Save" link
			VP: Verify that the tuning action link is  "Request Tuning Review"
			7. Click  "Request Tuning Review" link
			Submit the �Tuning Request Form� page successfully
			8. Log out DTS portal
			9. Log into DTS portal as DTS admin
			10. Navigate to "Accessories" page
			11. Select above accessory which its tuning is pending for DTS review
			12. Click "Approve Tuning" link
			13. Log out DTS portal
			14. Log into DTS portal as above partner user
			15. Navigate to "Accessories" page
			16. Select and open above accessory
			VP: The marketing approval status is "UNSUBMITTED"
			17. Click "Edit" link
			18. Change accessory's information
			19. Click "Save" link
		 */
//		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//		// 2. Navigate to "Accessories" page
//		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//		// 3. Click "Add Accessory" link
//		productControl.click(PageHome.createNewAccessory);
//		// 4. Fill valid value into all required fields
//		// 5. Upload valid tuning file
//		// 6. Click "Save" link
//		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//		//7. Click "Request Tuning Review"
//		productControl.click(ProductDetailModel.REQUEST_TUNING);
//		//8. Log out DTS portal
//		productControl.logout();
//		//9. Log into DTS portal as DTS admin
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		//10. Navigate to "Accessories" page
//		productControl.click(PageHome.linkAccessories);
//		//11. Select above accessory which its tuning is pending for DTS review
//		productControl.selectAccessorybyName(data.get("name"));
//		// Select tuning rating
//		productControl.click(ProductDetailModel.EDIT_MODE);
//		productControl.clickOptionByIndex(AddEditProductModel.TUNING_RATING, 2);
//		productControl.click(AddEditProductModel.SAVE);
//		//12. Click "Approve Tuning" link
//		productControl.click(VariantInfo.APPROVE_TUNING);
		
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productWf.addProductAndTunningAndMarketing(data, false);
		//13. Log out DTS portal
		productControl.logout();
		//14. Log into DTS portal as above partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		//15. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		//16. Select and open above accessory
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: The marketing approval status is "UNSUBMITTED"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "UNSUBMITTED");
		//17. Click "Edit" link
		productControl.click(ProductDetailModel.EDIT_MODE);
		//18. Change accessory's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		productControl.editData(AddEditProductModel.MODEL_NAME, newName);
		//19. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), newName);
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that partner user can not edit accessory when the marketing approval status is "Pending DTS Review"
	 */
	@Test
	public void TC040PDP_14(){
		productControl.addLog("ID : TC040PDP_14: Verify that partner user can not edit accessory "
				+ "when the marketing approval status is 'Pending DTS Review'");
		/*
		 * Pre-condition: the tuning approval is approved. the partner user has "Publish and suspend accessories" rights.
		 * 1. Log into DTS portal as a partner user
		 * 2. Navigate to "Accessories" page
		 * 3. Click "Add Accessory" link
		 * 4. Fill valid value into all required fields
		 * 5. Upload valid tuning file
		 * 6. Upload marketing material
		 * 7. Click "Save" link
		 * 8. Go through the tuning approval
		 * VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and its action is "Request Marketing Review"
		 * 9. Click "Request Marketing Review" link
		 */
//		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//		// 2. Navigate to "Accessories" page
//		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//		// 3. Click "Add Accessory" link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		// 4. Fill valid value into all required fields
//		// 5. Upload valid tuning file
//		// 6. Upload marketing material
//		// 7. Click "Save" link
//		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//		// 8. Go through the tuning approval
//		// Click Request Tuning Review link
//		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
//		// Logout user
//		productControl.logout();
//		// Re-login as DTS user
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// Navigate to Accessories page
//		productControl.click(PageHome.linkAccessories);
//		// Select created accessory above
//		productControl.selectAccessorybyName(data.get("name"));
//		// Select tuning rating
//		productControl.click(ProductDetailModel.EDIT_MODE);
//		productControl.clickOptionByIndex(AddEditProductModel.TUNING_RATING, 2);
//		productControl.click(AddEditProductModel.SAVE);
//		// Approve tuning
//		productControl.click(ProductDetailModel.APPROVE_TUNING);
		
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, false);
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productWf.addProductAndTunningAndMarketing(data, false);
		
		// Log out dts user
		productControl.logout();
		// Re-login as Partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to Accessories page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select the accessory above that has tuning approval
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and its action is "Request Marketing Review"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "APPROVED");
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "UNSUBMITTED");
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_ACTION), "Request Marketing Review");
		// 9. Click "Request Marketing Review" link
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		/*
		 *  Verify that the marketing approval status is "Peding DTS Review" and there is no "Edit" link displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "PENDING DTS REVIEW");
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.EDIT_MODE));
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that partner user can edit accessory when the marketing approval status is "Approved"
	 */
	@Test
	public void TC040PDP_15(){
		productControl.addLog("ID : TC040PDP_15: Verify that partner user can edit accessory when "
				+ "the marketing approval status is 'Approved'");
		/*
		Pre-condition: the tuning approval is approved. the partner user has "Publish and suspend accessories" rights.
		1. Log into DTS portal as a partner user
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		4. Fill valid value into all required fields
		5. Upload valid tuning file
		6. Upload marketing material
		7. Click "Save" link
		8. Go through the tuning approval
		VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and its action is "Request Marketing Review"
		9. Click "Request Marketing Review" link
		10. Log out DTS portal
		11. Log into DTS portal as DTS admin
		12. Navigate to "Accessories" page
		13. Select and open above accessory
		14. Go through the marketing approval
		15. Log out DTS portal
		16. Log into DTS poratl as above partner user
		17. Select and open avove accessory
		VP: Verify that the marketing approval is "Approved"
		18. Click "Edit" link
		19. Change accessory's information
		20. Click "Save" link
		 */
//		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//		// 2. Navigate to "Accessories" page
//		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//		// 3. Click "Add Accessory" link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		// 4. Fill valid value into all required fields
//		// 5. Upload valid tuning file
//		// 6. Upload marketing material
//		// 7. Click "Save" link
//		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//		// 8. Go through the tuning approval
//		// Click Request Tuning Review link
//		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
//		// Logout user
//		productControl.logout();
//		// Re-login as DTS user
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// Navigate to Accessories page
//		productControl.click(PageHome.linkAccessories);
//		// Select created accessory above
//		productControl.selectAccessorybyName(data.get("name"));
//		// Select tuning rating
//		productControl.click(ProductDetailModel.EDIT_MODE);
//		productControl.clickOptionByIndex(AddEditProductModel.TUNING_RATING, 2);
//		productControl.click(AddEditProductModel.SAVE);
//		// Approve tuning
//		productControl.click(ProductDetailModel.APPROVE_TUNING);
		
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, false);
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productWf.addProductAndTunningAndMarketing(data, false);
		// Logout dts user
		productControl.logout();
		// Re-login as Partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to Accessories page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select the accessory above that has tuning approval
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and its action is "Request Marketing Review"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "APPROVED");
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "UNSUBMITTED");
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_MARKETING_REVIEW), "Request Marketing Review");
		// 9. Click "Request Marketing Review" link
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		// 10. Log out DTS portal
		productControl.logout();
		// 11. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 12. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 13. Select and open above accessory
		productControl.selectAccessorybyName(data.get("name"));
		// 14. Go through the marketing approval
//		productControl.click(ProductDetailModel.APPROVE_MARKETING);
//		productWf.approveMarketing();
		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		productControl.click(ProductDetailModel.REQUEST_MARKETING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		// 15. Log out DTS portal
		productControl.logout();
		// 16. Log into DTS portal as above partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 17. Select and open above accessory
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the marketing approval is "Approved"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "APPROVED");
		// 18. Click "Edit" link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 19. Change accessory's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		productControl.editData(AddEditProductModel.MODEL_NAME, newName);
		// 20. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), newName);
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that partner user can edit accessory when the marketing approval status is "DECLINED"
	 */
	@Test
	public void TC040PDP_16(){
		productControl.addLog("ID : TC040PDP_16: Verify that partner user can edit accessory when"
				+ " the marketing approval status is 'DECLINED'");
		/*
			Pre-condition: the tuning approval is approved. the partner user has "Publish and suspend accessories" rights.
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Fill valid value into all required fields
			5. Upload valid tuning file
			6. Upload marketing material
			7. Click "Save" link
			8. Go through the tuning approval
			VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and its action is "Request Marketing Review"
			9. Click "Request Marketing Review" link
			10. Log out DTS portal
			11. Log into DTS portal as DTS admin
			12. Navigate to "Accessories" page
			13. Select and open above accessory
			14. Decline marketing
			15. Log out DTS portal
			16. Log into DTS portal as above partner user
			17. Select and open above accessory
			VP: Verify that the marketing approval is "DECLINED"
			18. Click "Edit" link
			19. Change accessory's information
			20. Click "Save" link
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		// 5. Upload valid tuning file
		// 6. Upload marketing material
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 8. Go through the tuning approval
		// Click Request Tuning Review link
		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
		// Logout user
		productControl.logout();
		// Re-login as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Accessories page
		productControl.click(PageHome.linkAccessories);
		// Select created accessory above
		productControl.selectAccessorybyName(data.get("name"));
		// Approve tuning
		// change tuning rating before approving
		productControl.click(ProductDetailModel.EDIT_MODE);
		productControl.selectFirstOption(AddEditProductModel.TUNING_RATING);
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		productWf.approveTuning();
		
//		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		productWf.addProductAndTunningAndMarketing(data, false);
		// Logout dts user
		productControl.logout();
		// Re-login as Partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to Accessories page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select the accessory above that has tuning approval
		productControl.selectAccessorybyName(data.get("name"));
//		/*
//		 * VP: Verify that the tuning is approved, the marketing status is
//		 * changed to "UNSUBMITTED" and its action is "Request Marketing Review"
//		 */
//		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS),
//				"APPROVED");
//		Assert.assertEquals(
//				productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS),
//				"UNSUBMITTED");
//		Assert.assertEquals(
//				productControl.getTextByXpath(ProductDetailModel.REQUEST_MARKETING_REVIEW),
//				"Request Marketing Review");
		// 9. Click "Request Marketing Review" link
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		// 10. Log out DTS portal
		productControl.logout();
		// 11. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 12. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 13. Select and open above accessory
		productControl.selectAccessorybyName(data.get("name"));
		// 14. Decline marketing
//		productControl.click(ProductDetailModel.DECLINE_MARKETING);
		productWf.declineMarketing();
		// 15. Log out DTS portal
		productControl.logout();
		// 16. Log into DTS portal as above partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 17. Select and open above accessory
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the marketing approval is "DECLINED"
		 */
		Assert.assertEquals(
				productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "DECLINED");
		// 18. Click "Edit" link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 19. Change accessory's information
		String newData = "Edit"+ RandomStringUtils.randomNumeric(6);
		productControl.editData(AddEditProductModel.MODEL_NAME, newData);
		// 20. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), newData);
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the "PUBLISH" button is only displayed if three type o accessory's primary image is uploaded even when both tuning and marketing approval is approved
	 */
	@Test
	public void TC040PDP_17(){
		productControl.addLog("ID : TC040PDP_17: Verify that the 'PUBLISH' button is only displayed if three type of accessory's primary image is uploaded even when both tuning and marketing approval is approved");
		/*
		Pre-Condition: partner user has "Add and manage accessories" rights.
		1. Log into DTS portal as a partner user
		2. Navigate to "Accessories" page
		3. Select an accessory from accessories table which both tuning and marketing approval are "APPROVED" but three kind of primary images is are not uploaded
		 */
		
		/**
		 ** Create accessory which both tuning and marketing approval are "APPROVED"
		 **/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Accessories" page
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, false);
		productWf.addProductAndTunningAndMarketing(data, true);
		// Log out DTS portal
		productControl.logout();
		// Log into DTS portal as above partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		/**
		 ** Create accessory which both tuning and marketing approval are "APPROVED" done
		 **/
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table which both tuning and marketing approval are "APPROVED" but three kind of primary images is are not uploaded
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * Verify that The "PUBLISH" button is disabled
		 */
		boolean button_disabled = productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class").contains("disabled");
		Assert.assertTrue(button_disabled);
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.ERROR_MESS_STEP_3), ProductDetailModel.PUBLISH_ERROR_MESS);
	
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}

}	
	
	
	
	
	
	
	
	
