package com.dts.adminportal.partner.test;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PartnerHomePage;
import com.dts.adminportal.model.PartnerVariantInfo;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.TuningRequestForm;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.model.VariantInfo;
import com.dts.adminportal.util.TestData;

public class PartnerPrivilegesProducts extends BasePage{
	
	@Override
	protected void initData() {
	}	

	
	/*
	 * Verify that the partner user can add accessories successfully when the  "Add and manage accessories� privilege is enabled.
	 */
	@Test
	public void TCPPA_01(){
		productControl.addLog("ID : TCPPA_01: Verify that the partner user can add accessories successfully when the  'Add and manage accessories' privilege is enabled.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Enable the �Add and manage accessories� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP. Verify that the �Accessories� page is displayed
			9. Navigate to �Home� page
			VP. The �Add Accessory� link is displayed in �Catalog� module
			10. Navigate to �Accessories� page
			VP. Verify that the �Add Accessory� link is displayed inside �Actions� module and the accessories table is also displayed.
			11. Click on �Add Accessory� link
			12. Fill valid value into all required fields
			13. Click �Save� link
		 */
		// 2. Log into DTS portal as a DTS user successfully
		userWf.loginAndEnableAllPrivilege(PARTNER_USER);
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// 3. Navigate to "Users" page
//		productControl.click(PageHome.LINK_USERS);
//		// 4.Select a partner user from the Users table
//		userControl.dtsSelectUserByEmail(PARTNER_USER);
//		// 5. Click "Edit" link
//		productControl.click(UserMgmt.EDIT);
//		// 6. Enable the �Add and manage accessories� privilege
//		userControl.enableAllPrivileges();
//		productControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		productControl.logout();
		// 8. Log into DTS portal as above partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// VP. Verify that the �Accessories� page is displayed
		Assert.assertTrue(productControl.isElementPresent(PageHome.LINK_PARTNER_ACCESSORIES));
		// 9. Navigate to �Home� page
		productControl.click(PartnerHomePage.LINK_PARTNER_HOME);
		// VP. The �Add Accessory� link is displayed in �Catalog� module
		Assert.assertTrue(productControl.isElementPresent(PartnerHomePage.PARTNER_CATALOG_ADD_ACCESSORY));
		// 10. Navigate to �Accessories� page
		productControl.click(PartnerHomePage.LINK_PARTNER_ACCESSORIES);
		// VP. Verify that the �Add Accessory� link is displayed inside �Actions� module and the accessories table is also displayed.
		Assert.assertTrue(productControl.isElementPresent(ProductModel.ADD_PRODUCT));
		Assert.assertTrue(productControl.isElementPresent(ProductModel.PRODUCT_TABLE));
		// 11. Click "Add Accessory" link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 12. Fill valid value into all required fields.
		// 13. Click �Save� link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * Verify that the new product is displayed in Products table
		 */
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		Assert.assertTrue(productControl.checkAnAccessoryExistByName(data.get("name")));
		// Clean Up
		productControl.logout();
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		productControl.click(PartnerHomePage.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the partner user can edit accessories successfully when the  "Add and manage accessories� privilege is enabled.
	 */
	@Test
	public void TCPPA_02(){
		productControl.addLog("ID : TCPPA_02: Verify that the partner user can edit accessories successfully when the  'Add and manage accessories' privilege is enabled.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Enable the �Add and manage accessories� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			9. Navigate to �Accessories� page
			10. Select an accessory from accessories table
			11. Click on �Edit� link in accessory detail page
			12. Change accessory's information
			13. Click �Save� link
		 */
		/*
		 * PreCondition: Create new product
		 */
		// Log into DTS portal as a DTS user successfully
		userWf.loginAndEnableAllPrivilege(PARTNER_USER);
//		
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// Navigate to Product page
//		productControl.click(PageHome.linkAccessories);
//		// Click Add product link
//		productControl.click(ProductModel.ADD_PRODUCT);
		// Create new product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productWf.addNewProduct(data);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * ****************************************************************
		 */
//		// 3. Navigate to "Users" page
//		productControl.click(PageHome.LINK_USERS);
//		// 4.Select a partner user from the Users table
//		userControl.dtsSelectUserByEmail(PARTNER_USER);
//		// 5. Click "Edit" link
//		productControl.click(UserMgmt.EDIT);
//		// 6. Enable the �Add and manage accessories� privilege
//		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
//		productControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		productControl.logout();
		// 8. Log into DTS portal as above partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 9. Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 10. Select an accessory from accessories table
		productControl.selectAccessorybyName(data.get("name"));
		// 11. Click on �Edit� link in accessory detail page
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 12. Change accessory's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		productControl.editData(AddEditProductModel.MODEL_NAME, newName);
		// 13. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * Verify that new product's information is displayed correctly
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), newName);
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
			
	/*
	 * Verify that the partner user can add Products variant successfully when the  "Add and manage Products� privilege is enabled
	 */
	@Test
	public void TCPPA_03(){
		productControl.addLog("ID : TCPPA_03: Verify that the partner user can add Products variant successfully when the  �Add and manage Products� privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Enable the �Add and manage Products� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			9. Navigate to �Accessories� page
			10. Select a published product from Products table
			VP. Verify that the �Add product Variant� link displayed in product detail page
			11. Click on �Add product Variant� link in product detail page
			VP. Verify that the adding new product variant page is displayed
			12. Fill valid value into all required fields
			13. Click �Save� link
		 */
		userWf.loginAndEnableAllPrivilege(PARTNER_USER);
		// 2. Log into DTS portal as a DTS user successfully
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// 3. Navigate to "Users" page
//		productControl.click(PageHome.LINK_USERS);
//		// 4.Select a partner user from the Users table
//		userControl.dtsSelectUserByEmail(PARTNER_USER);
//		// 5. Click "Edit" link
//		productControl.click(UserMgmt.EDIT);
//		// 6. Enable the �Add and manage Products� privilege
//		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
//		productControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		// Create a new product 
		productControl.click(PageHome.linkAccessories);
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),productData);
		productWf.approveTuning();
		productWf.approveMarketing();
		productControl.click(ProductDetailModel.PUBLISH);
		productControl.logout();
		// 8. Log into DTS portal as above partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 9. Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 10. Select a published accessory from accessories table
		productControl.selectAccessorybyName(productData.get("name"));
		// VP. Verify that the �Add Accessory Variant� link displayed in accessory detail page
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.ADD_VARIANT));
		// 11. Click on �Add product Variant� link in product detail page
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP. Verify that the adding new product variant page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getVariantElementsInfo()), true);
		// 12. Fill valid value into all required fields
		// 13. Click �Save� link
		Hashtable<String,String> data = TestData.variantData(false, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), data);
		/*
		 * Verify that New product variant could be save successfully
		 */
		Assert.assertEquals(productControl.existsElement(PartnerVariantInfo.getElementsInfo()), true);
		
		// Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(productData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the partner user can edit accessories variant successfully when the  "Add and manage accessories� privilege is enabled.
	 */
	@Test
	public void TCPPA_04(){
		productControl.addLog("ID : TCPPA_04: Verify that the partner user can edit accessories variant successfully when the  'Add and manage accessories' privilege is enabled.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Enable the �Add and manage accessories� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			9. Navigate to �Accessories� page
			10. Select an accessory from accessories table which has at least one variant
			11. Select an accessory variant from variant list in accessory detail page
			VP: Verify that the accessory variant detail page is displayed and the �Edit� link is displayed too
			12. Click �Edit� link
			13. Change accessory variant's information
			14. Click �Save� link
		 */
		/*
		 * Pre-condition: Create accessory which has at least one variant	
		 */
		
		// Log into DTS portal as DTS user
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// Navigate to "Accessories" page
//		productControl.click(PageHome.linkAccessories);
//		// Click add Accessories link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		// Create accessory
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		userWf.loginAndEnableAllPrivilege(PARTNER_USER);
		productWf.addNewProduct(dataProduct);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		// Click on Add new variant link
//		productControl.click(ProductDetailModel.ADD_VARIANT);
		// Create new variant
		Hashtable<String,String> dataVariant = TestData.variantData(false, false, false);
//		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		productWf.addNewVariant(dataVariant);
		/*
		 * *******************************************************************
		 */
//		// 3. Navigate to "Users" page
//		productControl.click(PageHome.LINK_USERS);
//		// 4.Select a partner user from the Users table
//		userControl.dtsSelectUserByEmail(PARTNER_USER);
//		// 5. Click "Edit" link
//		productControl.click(UserMgmt.EDIT);
//		// 6. Enable the �Add and manage accessories� privilege
//		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
//		productControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		productControl.logout();
		// 8. Log into DTS portal as above partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 9. Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 10. Select an accessory from accessories table which has at least one variant
		productControl.selectAccessorybyName(dataProduct.get("name"));
		// 11. Select an accessory variant from variant list in accessory detail page
		productControl.selectAVariantbyName(dataVariant.get("name"));
		// VP: Verify that the accessory variant detail page is displayed and the �Edit� link is displayed too
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TITLE_NAME), dataVariant.get("name"));
		Assert.assertTrue(productControl.isElementPresent(PartnerVariantInfo.EDIT_VARIANT));
		// 12. Click �Edit� link
		productControl.click(VariantInfo.EDIT_VARIANT);
		// 13. Change accessory variant's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(9);
		productControl.editData(AddEditProductModel.MODEL_NAME, newName);
		// 14. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * Verify that The product variant's information is changed successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(PartnerVariantInfo.DISPLAYNAME_DEFAULT), newName);
		// Delete product
		productControl.click(PartnerVariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the partner user can view accessory's published version info when the "Add and manage accessories�, 
	 * �Publish and suspend accessories� and �Request accessory tunings� privileges are disabled.
	 */
	@Test
	public void TCPPA_05() throws InterruptedException{
		productControl.addLog("ID : TCPPA_05: Verify that the partner user can view product's published version "
				+ "info when the \"Add and manage Products\", \"Publish and suspend Products\" and \"Request product tunings\" privileges are disabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Enable the  "Add and manage Products�, �Publish and suspend Products� and �Request product tunings� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			9. Navigate to �Accessories� page
			10. Select a published product from Products table.
			VP. The �Update product info� link is displayed
			11. Create new version for the published product successfully
			VP: The �View Published Version� link is displayed.
			12. Log out DTS portal
			13. Perform from step 2 to 5 again
			14. Disabled the "Add and manage Products�, �Publish and suspend Products� and �Request product tunings� privileges
			15. Perform from step 7 to 10 again
		 */
		/*
		 * Pre-condition: Create a published product
		 */
		// Login as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to accessory page
		productControl.click(PageHome.linkAccessories);
		// Click Add Accessory link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create accessory 
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		productWf.addProductAndPublish(data);
		/*
		 * *****************************************************************
		 */
		userWf.enableAllPrivilegeOfUser(PARTNER_USER);
		productControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		productControl.logout();
		// 8. Log into DTS portal as above partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 9. Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 10. Select a published product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// VP. The �Update product info� link is displayed
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.UPDATE_PRODUCT_INFO));
		// 11. Create new version for the published product successfully
		productControl.click(ProductDetailModel.UPDATE_PRODUCT_INFO);
		productControl.selectConfirmationDialogOption("Ok");
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: The �View Published Version� link is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.VIEW_PUBLISHED_MODEL));
		// 12. Log out DTS portal
		productControl.logout();
		// 13. Perform from step 2 to 5 again
		// Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Users" page
		productControl.click(PageHome.LINK_USERS);
		// Select a partner user from the Users table
		userControl.dtsSelectUserByEmail(PARTNER_USER);
		// Click "Edit" link
		productControl.click(UserMgmt.EDIT);
		// 14. Disabled the "Add and manage accessories�, �Publish and suspend accessories� and �Request accessory tunings� privileges
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Add_and_manage_products.getName());
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Publish_and_suspend_products.getName());
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Can_request_DTS_tune_products.getName());
		productControl.click(AddUser.SAVE);
		// 15. Perform from step 7 to 10 again
		// Log out DTS portal
		productControl.logout();
		// Log into DTS portal as above partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		Thread.sleep(3000);
		// Select an accessory from accessories table which has at least one version
		productControl.selectAccessorybyName(data.get("name"));
		Thread.sleep(2000);
		// VP: The �View Published Version� link is displayed.
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.VIEW_PUBLISHED_MODEL));
		
		// Tear down
		loginControl.logout();
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		
	}
	
	/*
	 * Verify that the partner user can view accessory variant published version info when the "Add and manage accessories�, �Publish and suspend accessories� and �Request accessory tunings� privileges are disabled
	 */
	@Test
	public void TCPPA_06(){
		productControl.addLog("ID : TCPPA_06: Verify that the partner user can view accessory variant published version info when the �Add and manage accessories�, �Publish and suspend accessories� and �Request accessory tunings� privileges are disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Enable the  "Add and manage Products�, �Publish and suspend Products� and �Request product tunings� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			9. Navigate to �Accessories� page
			10. Select a published product from Products table which have at least one product variant is also published
			11. Select the published product variant from variant list in product detail page
			VP. The �Update Variant Info� link is displayed
			12. Create new version for variant successfully
			VP: The �View Published Version� link is displayed in product variant detail page
			13. Log out DTS portal
			14. Perform from step 2 to 5 again
			15. Disable "Add and manage Products�, �Publish and suspend Products� and �Request product tunings� privileges
			16. Perform from step 7 to 11 again
		*/
		/*
		 * Pre-condition: Create a published accessory which has at least one published accessory variant
		 */
		// Login as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to accessory page
		productControl.click(PageHome.linkAccessories);
		// Click Add Accessory link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create accessory 
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndPublish(productData);
		
		// Create published variant
		Hashtable<String,String> variantData = TestData.variantData(true, true, true);
		productWf.addVariantAndPublish(variantData);
		/*
		 * ***************************************************************
		 */
		// 3. Navigate to "Users" page
		productControl.click(PageHome.LINK_USERS);
		// 4. Select a partner user from the Users table
		userControl.dtsSelectUserByEmail(PARTNER_USER);
		// 5. Click �Edit� link
		productControl.click(UserMgmt.EDIT);
		// 6. Enable the  "Add and manage Products�, �Publish and suspend Products� and �Request product tunings� privileges
		userControl.enableAllPrivileges();
		productControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		productControl.logout();
		// 8. Log into DTS portal as above partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 9. Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 10. Select a published accessory from accessories table which have at least one accessory variant is also published
		productControl.selectAccessorybyName(productData.get("name"));
		// 11. Select the published accessory variant from variant list in accessory detail page
		productControl.selectAVariantbyName(variantData.get("name"));
		/*
		 * VP. The �Update Variant Info� link is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.CREATE_NEW_VERSION));
		// 12. Create new version for variant successfully
		productControl.click(VariantInfo.CREATE_NEW_VERSION);
		productControl.selectConfirmationDialogOption("Ok");
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * 	VP: The �View Published Version� link is displayed in accessory variant detail page
		 */
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.VIEW_PUBLISHED_VERSION));
		// 13. Log out DTS portal
		productControl.logout();
		// 14. Perform from step 2 to 5 again
		// Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Users" page
		productControl.click(PageHome.LINK_USERS);
		// Select a partner user from the Users table
		userControl.dtsSelectUserByEmail(PARTNER_USER);
		// Click edit link
		productControl.click(UserMgmt.EDIT);
		// 15. Disable the "Add and manage accessories�, �Publish and suspend accessories� and �Request accessory tunings� privileges
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Add_and_manage_products.getName());
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Publish_and_suspend_products.getName());
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Can_request_DTS_tune_products.getName());
		productControl.click(AddUser.SAVE);
		// 16. Perform from step 7 to 11 again
		// Log out DTS portal
		productControl.logout();
		// Log into DTS portal as above partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select a published accessory from accessories table which have at least one accessory variant is also published
		productControl.selectAccessorybyName(productData.get("name"));
		// Select the published accessory variant from variant list in accessory detail page
		productControl.selectAVariantbyName(variantData.get("name"));
		/*
		 * Verify that the �View Published Version� link is displayed in accessory variant detail page either the  "Add and manage accessories� privilege is enabled or disabled
		 */
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.VIEW_PUBLISHED_VERSION));
		
		// Tear down
		productControl.logout();
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(productData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		
	}
	
	/*
	 * Verify that the partner user can only create a new version but not to edit a published accessory when the "Add and manage accessories� privilege is enabled.
	 */
	@Test
	public void TCPPA_07(){
		productControl.addLog("ID : TCPPA_07: Verify that the partner user can only create a new version but not to edit a published accessory when the 'Add and manage accessories' privilege is enabled");
		/*
			Pre-condition: The  "Add and manage accessories� privilege is enabled.
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to �Accessories� page
			4. Click �Add Accessory� link
			5. Fill valid value into all required fields
			6. Pass through two steps of Publication Process
			7. Click �Publish� button in step 3 of Publication Process
		 */
		// 2. Log into DTS portal as a Partner user successfully
//		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//		// 3. Navigate to �Accessories� page
//		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//		// 4. Click �Add Accessory� link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		// 5. Fill valid value into all required fields
//		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//		// 6. Pass through two steps of Publication Process
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
//		productControl.selectFilterByName(AddEditProductModel.TUNING_RATING, "A");
//		productControl.click(AddEditProductModel.SAVE);
//		//Approve Tuning
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
//		productControl.click(ProductDetailModel.REQUEST_TUNING);
//		productControl.click(ProductDetailModel.APPROVE_TUNING);
//		// Approve Marketing
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
//		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
//		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		userWf.loginAndEnableAllPrivilege(PARTNER_USER);
		productWf.addProductAndTunningAndMarketing(data, true);
		
		// Logout user
		productControl.logout();
		// Re-login as Partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to Accessories page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select created accessory above
		productControl.selectAccessorybyName(data.get("name"));
		// 7. Click �Publish� button in step 3 of Publication Process
		productControl.click(ProductDetailModel.PUBLISH);
		/*
		 * Verify that The Edit Link disappears and the �Create New Version� is displayed.
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.EDIT_MODE), ProductDetailModel.EDIT_UPDATE_PRODUCT_INFO);
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the partner user can only create a new version but not to edit a published accessory variant when the "Add and manage accessories� privilege is enabled
	 */
	@Test
	public void TCPPA_08(){
		productControl.addLog("ID : TCPPA_08: Verify that the partner user can only create a new version but not to edit a published accessory variant when the 'Add and manage accessories' privilege is enabled.");
		/*
			Pre-condition: The  "Add and manage accessories� privilege is enabled.
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to �Accessories� page
			4. Select a published accessory from the accessories table
			5. Click �Add new variant� link
			6. Fill valid value into all required fields
			7. Pass through two steps of Publication Process
			8. Click �Publish� button in step 3 of Publication Process
		 */
		
		/*
		 * Pre-condition: Create a published product
		 */
		// Login as DTS user
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// Navigate to accessory page
//		productControl.click(PageHome.linkAccessories);
//		// Click Add Accessory link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		// Create accessory 
//		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
////		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
//		productWf.addProductAndPublish(dataProduct);
//		// Logout user
//		productControl.logout();
//		/*
//		 * **************************************************************
//		 */
//		// 2. Log into DTS portal as a Partner user successfully
//		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//		// 3. Navigate to �Accessories� page
//		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//		// 4. Select a published accessory from the accessories table
//		productControl.selectAccessorybyName(dataProduct.get("name"));
//		// 5. Click �Add new variant� link
//		productControl.click(ProductDetailModel.ADD_VARIANT);
//		// 6. Fill valid value into all required fields
//		Hashtable<String, String> dataVariant = TestData.variantData(false, true, true);
//		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
//		// 7. Pass through two steps of Publication Process
//		// Logout user
//		productControl.logout();
//		// Re-login as DTS user
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// Navigate to accessory page
//		productControl.click(PageHome.linkAccessories);
//		// Select accessory above
//		productControl.selectAccessorybyName(dataProduct.get("name"));
//		// Select variant
//		productControl.selectAVariantbyName(dataVariant.get("name"));
//		// Select Tuning Rating
//		productControl.click(VariantInfo.EDIT_VARIANT);
//		productControl.selectFilterByName(AddVariant.TUNING_RATING, "A");
//		productControl.click(AddEditProductModel.SAVE_VARIANT);
//		// Approve marketing
//		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
//		productControl.click(VariantInfo.REQUEST_MARKETING_REVIEW);
//		productControl.click(VariantInfo.APPROVE_MARKETING);
		
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		Hashtable<String, String> dataVariant = TestData.variantData(false, true, true);
		userWf.loginAndEnableAllPrivilege(PARTNER_USER);
		productWf.addProductAndPublish(dataProduct);
		productWf.addVariantAndTunningAndMarketing(dataVariant, true);
		
		// Logout user
		productControl.logout();
		// Re-login user as Partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select accessory above
		productControl.selectAccessorybyName(dataProduct.get("name"));
		// Select variant
		productControl.selectAVariantbyName(dataVariant.get("name"));
		// 8. Click �Publish� button in step 3 of Publication Process
		productControl.click(VariantInfo.PUBLISH);
		/*
		 * Verify that The Edit Link disappears and the �Create New Version� is displayed.
		 */
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.EDIT_VARIANT));
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.CREATE_NEW_VERSION));
		// Delete product
		productControl.click(PartnerVariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the partner user cannot add new or edit accessories or accessories variant when the "Add and manage accessories� privilege is disabled
	 */
	@Test
	public void TCPPA_09(){
		productControl.addLog("ID : TCPPA_09: Verify that the partner user cannot add new or edit accessories "
				+ "or accessories variant when the 'Add and manage accessories' privilege is disabled");
		/*
			Pre-condition: The  "Add and manage accessories� privilege is disabled.
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to �Home� page
			VP: The �Add Accessory� link is disappears
			4. Navigate to �Accessories� page
			VP: The �Add Accessory� link is disappears
			5. Select a non published accessory from the accessories table which has at least one accessory variant
			VP: Verify that there is no edit link displayed
			6. Select an accessory variant from the variant list
			VP: Verify that there is no edit link displayed for an accessory variant
		*/
		
		/*
		 * Pre-condition: The  "Add and manage accessories� privilege is disabled and a product has at least one variant is created
		 */
		// Log into portal as DTS user
		
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		Hashtable<String,String> dataVariant = TestData.variantData(false, false, false);
		userWf.loginAndDisablePrivilege(PARTNER_USER, Privileges.privileges.Add_and_manage_products.getName(), false);
		productWf.addNewProduct(dataProduct);
		productWf.addNewVariant(dataVariant);
		// Logout user
		productControl.logout();
		/*
		 * ******************************************************************
		 */
		// 2. Log into DTS portal as a Partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 3. Navigate to �Home� page
		productControl.click(PageHome.LINK_PARTNER_HOME);
		/*
		 * VP: The �Add Accessory� link is disappears
		 */
		Assert.assertFalse(productControl.isElementPresent(PartnerHomePage.PARTNER_CATALOG_ADD_ACCESSORY));
		// 4. Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		/*
		 * VP: The �Add Accessory� link is disappears
		 */
		Assert.assertFalse(productControl.isElementPresent(ProductModel.ADD_PRODUCT));
		// 5. Select a non published accessory from the accessories table which has at least one accessory variant
		productControl.selectAccessorybyName(dataProduct.get("name"));
		/*
		 * VP: Verify that there is no edit link displayed
		 */
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.EDIT_MODE));
		// 6. Select an accessory variant from the variant list
		productControl.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that there is no edit link displayed for an accessory variant
		 */
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.EDIT_VARIANT));
		// Delete product
		productControl.click(PartnerVariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the partner user cannot create new version for an accessory or accessory variant when the "Add and manage accessories� privilege is disabled
	 */
	@Test
	public void TCPPA_10(){
		productControl.addLog("ID : TCPPA_10: Verify that the partner user cannot create new version "
				+ "for an accessory or accessory variant when the 'Add and manage accessories' privilege is disabled");
		/*
			Pre-condition: The  "Add and manage accessories� privilege is disabled.
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to �Accessories� page
			4. Select a published accessory from the accessories table which has at least one published accessory variant
			VP: Verify that there is no �Create New Version� link displayed for a published accessory
			5. Select a published accessory variant from the variant list
			VP: Verify that there is no �Create New Version� link displayed for a published accessory variant
		*/
		/*
		 * Pre-condition: The  "Add and manage accessories� privilege is disabled and a published product which has at least one published variant is created
		 */
		// Log into DTS portal
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// Navigate to user page
//		productControl.click(PageHome.LINK_USERS);
//		// Select a user
//		userControl.dtsSelectUserByEmail(PARTNER_USER);
//		// Click edit link
//		productControl.click(UserMgmt.EDIT);
//		// Disable privilege "Add and manage accessories�
//		userControl.enableAllPrivileges();
//		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
//		productControl.click(AddUser.SAVE);
//		// Navigate to accessory page
//		productControl.click(PageHome.linkAccessories);
//		// Click Add Accessory link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		// Create published product
//		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
//		// Click Add variant link
//		productControl.click(ProductDetailModel.ADD_VARIANT);
//		// Create variant
//		Hashtable<String,String> dataVariant = TestData.variantData(true, true, true);
//		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		Hashtable<String,String> dataVariant = TestData.variantData( true, true, true);
		userWf.loginAndDisablePrivilege(PARTNER_USER, Privileges.privileges.Add_and_manage_products.getName(), false);
		productWf.addProductAndPublish(dataProduct);
		productWf.addVariantAndPublish(dataVariant);
		
		// Logout user
		productControl.logout();
		/*
		 * *****************************************************************
		 */
		// 2. Log into DTS portal as a Partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 3. Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 4. Select a published accessory from the accessories table which has at least one published accessory variant
		productControl.selectAccessorybyName(dataProduct.get("name"));
		/*
		 * VP: Verify that there is no �Create New Version� link displayed for a published accessory
		 */
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.UPDATE_PRODUCT_INFO));
		// 5. Select a published accessory variant from the variant list
		productControl.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that there is no �Create New Version� link displayed for a published accessory variant
		 */
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.CREATE_NEW_VERSION));
		// Delete product
		productControl.click(PartnerVariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that user could publish accessory successfully when the �Publish and suspend accessories� privilege is enabled
	 */
	@Test
	public void TCPPA_11(){
		productControl.addLog("ID : TCPPA_11: Verify that user could publish accessory successfully when "
				+ "the 'Publish and suspend accessories' privilege is enabled");
		/*
			Pre-condition: The �Publish and suspend accessories� privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to �Accessories� page
			4. Click �Add Accessory� link
			5. Fill valid value into all required fields
			6. Upload all valid necessary files
			7. Click �Save� link
			8. Pass through two steps of Approval Process
			VP The �Published� button in step 3 �Publishing� of �Approval Process� is enabled 
			9. Click �Publish� button
		*/
		// 2. Log into DTS portal as a Partner user successfully
//		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//		// 3. Navigate to �Accessories� page
//		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//		// 4. Click �Add Accessory� link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		// 5. Fill valid value into all required fields
//		// 6. Upload all valid necessary files
//		// 7. Click �Save� link
//		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//		// 8. Pass through two steps of Approval Process
//		// Logout user
//		productControl.logout();
//		// Re-login as DTS user
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// Navigate to Accessories page
//		productControl.click(PageHome.linkAccessories);
//		// Select accessory created above
//		productControl.selectAccessorybyName(data.get("name"));
//		// Select tuning rating
//		productControl.click(ProductDetailModel.EDIT_MODE);
//		productControl.selectFilterByName(AddEditProductModel.TUNING_RATING, "A");
//		productControl.click(AddEditProductModel.SAVE);
//		//Approve Tuning
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
//		productControl.click(ProductDetailModel.REQUEST_TUNING);
//		productControl.click(ProductDetailModel.APPROVE_TUNING);
//		// Approve Marketing
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
//		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
//		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		userWf.loginAndEnableAllPrivilege(PARTNER_USER);
		productWf.addProductAndTunningAndMarketing(data, true);
		
		// Logout user
		productControl.logout();
		// Re-login as partner user above
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select accessory created above
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP The �Published� button in step 3 �Publishing� of �Approval Process� is enabled 
		 */
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), ProductDetailModel.BUTTON_WARNING);
		// 9. Click �Publish� button
		productControl.click(ProductDetailModel.PUBLISH);
		/*
		 * Verify that New accessory could be upload successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PUBLISH_STATUS), ProductDetailModel.PUBLISHED_STRING);
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}	
	
	/*
	 * Verify that user could not publish product when the �Publish and suspend Products� privilege is disabled
	 */
	@Test
	public void TCPPA_12(){
		productControl.addLog("ID : TCPPA_12: Verify that user could not publish product when the �Publish and suspend Products� privilege is disabled");
		/*
			Pre-condition: The �Publish and suspend Products� privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to �Accessories� page
			4. Click �Add product� link
			5. Fill valid value into all required fields
			6. Upload all valid necessary files
			7. Click �Save� link
			8. Pass through two steps of Approval Process
		*/
		/*
		 * Pre-condition: The �Publish and suspend Products� privilege is disabled
		 */
//		// Log in DTS portal as DTS user
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// Navigate to user page
//		productControl.click(PageHome.LINK_USERS);
//		// Select a user from table
//		userControl.dtsSelectUserByEmail(PARTNER_USER);
//		// Click Edit link
//		productControl.click(UserMgmt.EDIT);
//		// Disable �Publish and suspend Products� privilege
//		userControl.enableAllPrivileges();
//		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Publish_and_suspend_products);
//		productControl.click(AddUser.SAVE);
//		// Log out
//		productControl.logout();
//		/*
//		 * *******************************************************************
//		 */
//		// 2. Log into DTS portal as a Partner user successfully
//		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
//		// 3. Navigate to �Accessories� page
//		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//		// 4. Click �Add product� link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		// 5. Fill valid value into all required fields
//		// 6. Upload all valid necessary files
//		// 7. Click �Save� link
//		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//		// 8. Pass through two steps of Approval Process
//		productControl.click(ProductDetailModel.REQUEST_TUNING);
//		// Log out
//		productControl.logout();
//		// Log in DTS portal as DTS user
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// Navigate to Product page
//		productControl.click(PageHome.linkAccessories);
//		// Select product created above
//		productControl.selectAccessorybyName(data.get("name"));
//		// Select tuning rating
//		productControl.click(ProductDetailModel.EDIT_MODE);
//		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, "A");
//		productControl.click(AddEditProductModel.SAVE);
//		// Approve tuning
//		productControl.click(ProductDetailModel.APPROVE_TUNING);
//		// Approve marketing
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
//		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
//		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		userWf.loginAndDisablePrivilege(PARTNER_USER, Privileges.privileges.Publish_and_suspend_products.getName(), false);
		productWf.addProductAndTunningAndMarketing(data, true);
		// Log out
		productControl.logout();
		// Log in DTS portal as Partner user above
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select product created above
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * The �Published� button in step 3 �Publishing� of �Approval Process� is disabled 
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PUBLISH_DISABLE));
		
		// Tear down
		loginControl.logout();
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that user could publish accessory variant successfully when the �Publish and suspend accessories� privilege is enabled
	 */
	@Test
	public void TCPPA_13(){
		productControl.addLog("ID : TCPPA_13: Verify that user could publish accessory variant successfully when the �Publish and suspend accessories� privilege is enabled");
		/*
			Pre-condition: The �Publish and suspend accessories� privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to �Accessories� page
			4. Select a published accessory from accessories table
			5. Click �Add Variant� link
			6. Fill valid value into all required fields
			7. Upload all valid necessary files
			8. Click �Save� link
			9. Pass through two steps of Approval Process of accessory variant
			VP The �Published� button in step 3 �Publishing� of �Approval Process� is enabled 
			10 Click �Publish� button
		*/
		/*
		 * Pre-condition: Create a published accessory
		 */
		userWf.loginAndEnableAllPrivilege(PARTNER_USER);
		
		// Navigate to accessory page
		productControl.click(PageHome.linkAccessories);
		// Click Add Accessory link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create published product 
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndPublish(dataProduct);

		// Create variant
		Hashtable<String, String> dataVariant = TestData.variantData(false, true, true);
		productWf.addVariantAndTunningAndMarketing(dataVariant, true);
		
		// Logout user
		productControl.logout();
		/*
		 * ***********************************************************************
		 */
		// 2. Log into DTS portal as a Partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 3. Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 4. Select a published accessory from the accessories table
		productControl.selectAccessorybyName(dataProduct.get("name"));
		// 5. Click �Add Variant� link
		// 6. Fill valid value into all required fields
		// 7. Upload all valid necessary files
		// 8. Click �Save� link
		// 9. Pass through two steps of Approval Process of accessory variant
		productControl.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP The �Published� button in step 3 �Publishing� of �Approval Process� is enabled
		 */
		Assert.assertEquals(productControl.getAtributeValue(VariantInfo.PUBLISH, "class"), VariantInfo.BUTTON_WARNING);
		// 10 Click �Publish� button
		productControl.click(VariantInfo.PUBLISH);
		/*
		 * Verify new accessory variant could be upload successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(PartnerVariantInfo.PUBLISHED_STATUS), PartnerVariantInfo.PUBLISHED_STRING);
		// Delete product
		productControl.click(PartnerVariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that user could not publish variant when the �Publish and suspend Products� privilege is disabled
	 */
	@Test
	public void TCPPA_14(){
		productControl.addLog("ID : TCPPA_14: Verify that user could not publish variant when the �Publish and suspend Products� privilege is disabled");
		/*
			Pre-condition: The �Publish and suspend Products� privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to �Accessories� page
			4. Select a published product from Products table
			5. Click �Add Variant� link
			6. Fill valid value into all required fields
			7. Upload all valid necessary files
			8. Click �Save� link
			9. Pass through two steps of Approval Process of product variant
		*/
		/*
		 * Pre-condition: The �Publish and suspend Products� privilege is disabled and one published product is created
		 */
		
		userWf.loginAndDisablePrivilege(PARTNER_USER, Privileges.privileges.Publish_and_suspend_products.getName(), false);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create publish product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		
		productWf.addProductAndPublish(dataProduct);
		// 5. Click �Add Variant� link
		// 6. Fill valid value into all required fields
		// 7. Upload all valid necessary files
		// 8. Click �Save� link
		Hashtable<String, String> dataVariant = TestData.variantData(false, true, true);
		productWf.addVariantAndTunningAndMarketing(dataVariant, true);		
		productControl.logout();
		// Log in DTS portal as Partner user above
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select published product created above
		productControl.selectAccessorybyName(dataProduct.get("name"));
		// Select variant created above
		productControl.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * Verify that The �Published� button in step 3 �Publishing� of �Approval Process� is disabled
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PUBLISH_DISABLE));
		
		// Tear down
		loginControl.logout();
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that user could delete accessory and its variant successfully when the �Publish and suspend accessories� privilege is enabled.
	 */
	@Test
	public void TCPPA_15(){
		productControl.addLog("ID : TCPPA_15: Verify that user could delete accessory and its variant successfully when the �Publish and suspend accessories� privilege is enabled.");
		/*
		 	Pre-condition: The �Publish and suspend Products� privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to �Accessories� page
			4. Select an product from Products table which has at least one variant
			VP: Verify that the �Delete� link is displayed in product detail page
			5. Select a variant from variant list
			VP: Verify that the �Delete� link is displayed in product variant detail page
			6. Click �Delete� link in product variant detail page
			VP: Verify that the product variant is deleted successfully and the product detail page is displayed
			7. Click �Delete� link in product detail page
			VP: Verify that the product is deleted successfully.
		 */
		/*
		 *  Pre-Condition: Create new accessory has one variant 
		 */
		// Log into DTS portal as partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Click add Accessories link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create accessory
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		// Click on Add New Variant link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// Create variant
		Hashtable<String,String> dataVariant = TestData.variantData(false, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		/*
		 * ****************************************************************
		 */
		// 3. Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 4. Select an accessory from accessories table which has at least one variant
		productControl.selectAccessorybyName(dataProduct.get("name"));
		/*
		 * VP: Verify that the �Delete� link is displayed in accessory detail page
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.DELETE));
		// 5. Select a variant from variant list
		productControl.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that the �Delete� link is displayed in accessory variant detail page
		 */
		Assert.assertTrue(productControl.isElementPresent(PartnerVariantInfo.DELETE_VARIANT));
		// 6. Click �Delete� link in accessory variant detail page
		productControl.doDelete(PartnerVariantInfo.DELETE_VARIANT);
		/*
		 * VP: Verify that the accessory variant is deleted successfully and the accessory detail page is displayed
		 */
		Assert.assertFalse(productControl.checkVariantExistbyName(ProductDetailModel.MODEL_VARIANTS, dataVariant.get("name")));
		// 7. Click �Delete� link in accessory detail page
		productControl.doDelete(ProductDetailModel.DELETE);
		/*
		 * VP: Verify that the accessory is deleted successfully.
		 */
		Assert.assertFalse(productControl.checkAnAccessoryExistByName(dataProduct.get("name")));
	}
	
	/*
	 * Verify that user could not delete accessory and its variant successfully when the �Publish and suspend accessories� privilege is disabled
	 */
	@Test
	public void TCPPA_16(){
		productControl.addLog("ID : TCPPA_16: Verify that user could not delete accessory and its variant successfully when the �Publish and suspend accessories� privilege is disabled");
		/*
			Pre-condition: The �Publish and suspend accessories� privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to �Accessories� page
			4. Select an accessory from accessories table which has at least one variant
			VP: Verify that the �Delete� link is not displayed in accessory detail page
			5. Select a variant from variant list
			VP: Verify that the �Delete� link is not displayed in accessory variant detail page
		*/
		/*
		 * Pre-condition: The �Publish and suspend accessories� privilege is disabled and a product which has at lease one variant is created
		 */
		Hashtable<String, String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		Hashtable<String,String> dataVariant = TestData.variantData(false, false, false);
		userWf.loginAndDisablePrivilege(PARTNER_USER, Privileges.privileges.Publish_and_suspend_products.getName(), false);
		productWf.addNewProduct(dataProduct);
		productWf.addNewVariant(dataVariant);
		
		// Logout user
		productControl.logout();
		/*
		 * **************************************************************
		 */
		// 2. Log into DTS portal as a Partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 3. Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 4. Select an accessory from accessories table which has at least one variant
		productControl.selectAccessorybyName(dataProduct.get("name"));
		/*
		 * VP: Verify that the �Delete� link is not displayed in accessory detail page
		 */
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.DELETE));
		// 5. Select a variant from variant list
		productControl.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that the �Delete� link is not displayed in accessory variant detail page
		 */
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.DELETE_VARIANT));
		
		// Tear down
		loginControl.logout();
		loginControl.login(SUPER_USER_NAME,SUPER_USER_PASSWORD);
		userWf.enableAllPrivilegeOfUser(PARTNER_USER);
		userControl.click(AddUser.SAVE);
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}	
	
	/*
	 * Verify that user could suspend accessory successfully when the �Publish and suspend accessories� privilege is enabled
	 */
	@Test
	public void TCPPA_17(){
		productControl.addLog("ID : TCPPA_17: Verify that user could suspend accessory successfully when the �Publish and suspend accessories� privilege is enabled");
		/*
			Pre-condition: The �Publish and suspend accessories� privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to �Accessories� page
			4. Click �Add Accessory� link
			5. Fill valid value into all required fields
			6. Upload all valid necessary files
			7. Click �Save� link
			VP: Verify that the new accessory detail page is displayed and the �Suspend� link is not displayed
			8. Pass through three steps of Approval Process
			9. Click �Publish� button
			VP: Verify that the accessory is published successfully and the �Suspend� link is displayed
			10. Click �Suspend� link
		*/
//		// 2. Log into DTS portal as a Partner user successfully
//		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//		// 3. Navigate to �Accessories� page
//		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//		// 4. Click �Add Accessory� link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		// 5. Fill valid value into all required fields
//		// 6. Upload all valid necessary files
//		// 7. Click �Save� link
//		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//		/*
//		 * VP: Verify that the new accessory detail page is displayed and the �Suspend� link is not displayed
//		 */
//		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
//		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.SUSPEND));
//		// 8. Pass through three steps of Approval Process
//		// Logout user
//		productControl.logout();
//		// Re-login as DTS user
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// Navigate to Accessories page
//		productControl.click(PageHome.linkAccessories);
//		// Select accessory created above
//		productControl.selectAccessorybyName(data.get("name"));
//		// Select tuning rating
//		productControl.click(ProductDetailModel.EDIT_MODE);
//		productControl.selectFilterByName(AddEditProductModel.TUNING_RATING, "A");
//		productControl.click(AddEditProductModel.SAVE);
//		// Approve Tuning
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
//		productControl.click(ProductDetailModel.REQUEST_TUNING);
//		productControl.click(ProductDetailModel.APPROVE_TUNING);
//		// Approve Marketing
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
//		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
//		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		userWf.loginAndEnableAllPrivilege(PARTNER_USER);
		productWf.addProductAndPublish(dataProduct);
		
		// Logout user
		productControl.logout();
		// Re-login as partner user above
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// Navigate to Accessories page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select accessory created above
		productControl.selectAccessorybyName(dataProduct.get("name"));
		// 9. Click �Publish� button
//		productControl.click(ProductDetailModel.PUBLISH);
		/*
		 * VP: Verify that the accessory is published successfully and the �Suspend� link is displayed
		 */
//		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PUBLISH_STATUS), "PUBLISHED");
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.SUSPEND));
		// 10. Click �Suspend� link
		productControl.click(ProductDetailModel.SUSPEND);
		productControl.switchWindowClickOption("Suspend");
		/*
		 * Verify that the accessory is suspended successfully
		 */
		productControl.selectFilterByName(ProductModel.PRODUCT_FILTER, "Suspended");
		Assert.assertTrue(productControl.checkAnAccessoryExistByName(dataProduct.get("name")));
		// Delete product
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that user could suspend accessory variant successfully when the �Publish and suspend accessories� privilege is enabled
	 */
	@Test
	public void TCPPA_18(){
		productControl.addLog("ID : TCPPA_18: Verify that user could suspend accessory variant "
				+ "successfully when the �Publish and suspend accessories� privilege is enabled");
		/*
			Pre-condition: The "Publish and suspend accessories" privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to �Accessories� page
			4. Select a published accessory from accessories table
			5. Click �Add Variant� link in accessory detail page
			6. Fill valid value into all required fields
			7. Upload all valid necessary files
			8. Click �Save� link
			VP: Verify that the new accessory variant detail page is displayed and the �Suspend� link is not displayed
			9. Pass through three steps of Approval Process
			10. Click �Publish� button
			VP: Verify that the accessory variant is published successfully and the �Suspend� link is displayed
			11. Click �Suspend� link
		*/
		/*
		 * Pre-condition: Create a published product 
		 */
		// Log into portal as DTS user
		userWf.loginAndEnableAllPrivilege(PARTNER_USER);
		// Navigate to accessory page
		productControl.click(PageHome.linkAccessories);
		// Click Add Accessory link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create accessory
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);

		String productName = dataProduct.get("name");
		productWf.addProductAndPublish(dataProduct);
		
		Hashtable<String, String> dataVariant = TestData.variantData(false, true, true);
		String variantName = dataVariant.get("name");
		productWf.addVariantAndPublish(dataVariant);
		
		// Logout user
		productControl.logout();
		/*
		 * **************************************************************
		 */
		// 2. Log into DTS portal as a Partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 3. Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 4. Select a published accessory from accessories table
		productControl.selectAccessorybyName(productName);
		productControl.selectAVariantbyName(variantName);
		// Select tuning rating
		/*
		 * VP: Verify that the accessory variant is published successfully and the �Suspend� link is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.PUBLISHED_STATUS), VariantInfo.PUBLISHED_STRING);
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.SUSPEND));
		// 11. Click �Suspend� link
		productControl.click(VariantInfo.SUSPEND);
		productControl.selectConfirmationDialogOption("Suspend");
		/*
		 * Verify that the accessory variant is suspended successfully
		 */
		productControl.selectAccessorybyName(productName);
		productControl.selectAVariantbyName(variantName);
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.PUBLISHED_STATUS), VariantInfo.SUSPENDED_STRING);
		// Delete product
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that user could not suspend accessory and accessory variant successfully when the �Publish and suspend accessories� privilege is disabled
	 */
	@Test
	public void TCPPA_19(){
		productControl.addLog("ID : TCPPA_19: Verify that user could not suspend accessory "
				+ "and accessory variant successfully when the �Publish and suspend accessories� privilege is disabled");
		/*
			Pre-condition: The �Publish and suspend accessories� privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to �Accessories� page
			4. Select a published accessory from accessories table which have at least one published accessory variant
			VP: Verify that the �Suspend� link is not displayed in accessory detail page
			5. Select a published accessory variant from accessory variants list
			VP: Verify that the �Suspend� link is not displayed in accessory variant detail page
		*/
		/*
		 * Pre-condition: The �Publish and suspend accessories� privilege is disabled and a publish product which has at least one variant is created
		 */
		// Log into DTS portal
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// Navigate to user page
//		productControl.click(PageHome.LINK_USERS);
//		// Select a user
//		userControl.dtsSelectUserByEmail(PARTNER_USER);
//		// Click edit link
//		productControl.click(UserMgmt.EDIT);
//		// Disable privilege "Publish and suspend accessories�
//		userControl.enableAllPrivileges();
//		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Publish_and_suspend_products);
//		productControl.click(AddUser.SAVE);
//		// Navigate to accessory page
//		productControl.click(PageHome.linkAccessories);
//		// Click Add Accessory link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		// Create published product
//		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
//		// Click Add variant link
//		productControl.click(ProductDetailModel.ADD_VARIANT);
//		// Create published variant
//		Hashtable<String,String> dataVariant = TestData.variantData(true, true, true);
//		productControl.addVariant(AddEditProductModel.getVariantHash(),dataVariant);
		
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		Hashtable<String,String> dataVariant = TestData.variantData(true, true, true);
		userWf.loginAndDisablePrivilege(PARTNER_USER, Privileges.privileges.Publish_and_suspend_products.getName(), false);
		productWf.addProductAndPublish(dataProduct);
		productWf.addVariantAndPublish(dataVariant);
		// Logout user
		productControl.logout();
		/*
		 * **************************************************************
		 */
		// 2. Log into DTS portal as a Partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 3. Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 4. Select a published accessory from accessories table which have at least one published accessory variant
		productControl.selectAccessorybyName(dataProduct.get("name"));
		/*
		 * VP: Verify that the �Suspend� link is not displayed in accessory detail page
		 */
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.SUSPEND));
		// 5. Select a published accessory variant from accessory variants list
		productControl.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that the �Suspend� link is not displayed in accessory variant detail page
		 */
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.SUSPEND));
		
		// Tear down
		loginControl.logout();
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the partner user could request DTS tuning when the �Request accessory tunings� privilege is enabled
	 */
	@Test
	public void TCPPA_20(){
		productControl.addLog("ID : TCPPA_20: Verify that the partner user could request"
				+ " DTS tuning when the �Request accessory tunings� privilege is enabled");
		/*
			Pre-condition: The �Request accessory tunings� privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to �Accessories� page
			4. Click �Add Accessory� link
			5. Fill valid value into all required fields
			6. Do not upload tuning file
			7. Click �Save� link
			VP: Verify that the accessory detail page is displayed, the tuning approval state is �Unsubmitted� and the action link is �Request DTS tuning�
			8. Click �Request DTS tuning� link
			9. Submit for the request tuning form successfully
		*/
		// 2. Log into DTS portal as a Partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 6. Do not upload tuning file
		// 7. Click �Save� link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
//		productWf.addNewProduct(data);
		productControl.click(ProductModel.ADD_PRODUCT);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * VP: Verify that the accessory detail page is displayed, the tuning approval state is �Unsubmitted� and the action link is �Request DTS tuning�
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_TUNING), ProductDetailModel.ProductActions.REQUEST_DTS_TUNING.getName());
		// 8. Click �Request DTS tuning� link
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		// 9. Submit for the request tuning form successfully
		productControl.click(TuningRequestForm.AGREE);
		productControl.click(TuningRequestForm.SUBMIT);
		/*
		 * Verify that the tuning approval state is changed to �DTS Request Pending� and the action link is �Cancel Request�
		 */
		productControl.click(TuningRequestForm.PRODUCT_LINK);
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.DTS_REQUEST_PENDING.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.CANCEL_REQUEST_TUNING_PARTNER), ProductDetailModel.ProductActions.TUNING_CANCEL_REQUEST.getName());
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the partner user could not request DTS tuning when the �Request accessory tunings� privilege is disabled
	 */
	@Test
	public void TCPPA_21(){
		productControl.addLog("ID : TCPPA_21: Verify that the partner user could not request"
				+ " DTS tuning when the �Request accessory tunings� privilege is disabled");
		/*
			Pre-condition: The �Request accessory tunings� privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to �Accessories� page
			4. Click �Add Accessory� link
			5. Fill valid value into all required fields
			6. Do not upload tuning file
			7. Click �Save� link
		*/
		/*
		 * Pre-condition: The �Request accessory tunings� privilege is disabled
		 */
//		// Log into DTS portal as Partner user
//		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//		// Navigate to user page
//		productControl.click(PageHome.LINK_PARTNER_USER);
//		// Select a user
//		userControl.selectUserByEmail(PARTNER_USER);
//		// Click edit link
//		productControl.click(UserMgmt.EDIT);
//		// Disable privilege "Request accessory tunings�
//		userControl.enableAllPrivileges();
//		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Request_product_tunings);
//		productControl.click(AddUser.SAVE);
		
		userWf.loginAndDisablePrivilege(PARTNER_USER, Privileges.privileges.Can_request_DTS_tune_products.getName(), false);
		// Logout user
		productControl.logout();
		/*
		 * *************************************************************
		 */
		// 2. Log into DTS portal as a Partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 3. Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 4. Click �Add Accessory� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 5. Fill valid value into all required fields
		// 6. Do not upload tuning file
		// 7. Click �Save� link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * Verify that the accessory detail page is displayed, the tuning approval state is �Unsubmitted� and there is no action link displayed below.
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.PARTNER_ACTIONS_STEP_1));
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the partner user could approve the DTS tuning file when the �Request accessory tunings� privilege is enabled
	 */
	@Test
	public void TCPPA_22(){
		productControl.addLog("ID : TCPPA_22: Verify that the partner user could approve "
				+ "the DTS tuning file when the �Request accessory tunings� privilege is enabled");
		/*
			Pre-condition: The �Request accessory tunings� privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to �Accessories� page
			4. Click �Add Accessory� link
			5. Fill valid value into all required fields
			6. Upload tuning file successfully
			7. Click �Save� link
			VP: Verify that the accessory detail page is displayed
			8. Log out DTS portal
			9. Log into DTS portal as a Partner user successfully
			10. Navigate to �Accessories� page
			11. Select above accessory from the accessories table
			VP: Verify that the tuning approval status is �PENDING PARTNER APPROVAL� and its action links are �Approve Tuning� and �Decline Tuning�
			12. Click �Approve Tuning� link
		*/
		// 2. Log into DTS portal as a DTS user successfully
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// 3. Navigate to �Accessories� page
//		productControl.click(PageHome.linkAccessories);
//		// 4. Click �Add Accessory� link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		// 5. Fill valid value into all required fields
//		// 6. Upload tuning file successfully
//		// 7. Click �Save� link
//		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//		/*
//		 * VP: Verify that the accessory detail page is displayed
//		 */
//		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		
		userWf.loginAndEnableAllPrivilege(PARTNER_USER);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productWf.addNewProduct(dataProduct);
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 8. Log out DTS portal
		productControl.logout();
		// 9. Log into DTS portal as a Partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 10. Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 11. Select above accessory from the accessories table
		productControl.selectAccessorybyName(dataProduct.get("name"));
		/*
		 * VP: Verify that the tuning approval status is �PENDING PARTNER APPROVAL� and its action links are �Approve Tuning� and �Decline Tuning�
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), 
				ProductDetailModel.ProductStatus.PENDING_PARTNER_APPROVAL.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.APPROVE_TUNING), 
				ProductDetailModel.ProductActions.APPROVE_TUNING.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.DECLINE_TUNING), 
				ProductDetailModel.ProductActions.DECLINE_TUNING.getName());
		// 12. Click �Approve Tuning� link
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		/*
		 * Verify that the tuning approval state is changed to �Approved� and the action links are �Download Certification Badges�
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), 
				ProductDetailModel.ProductStatus.APPROVED.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.DOWNLOAD_CERTIFICATE), 
				ProductDetailModel.ProductActions.DOWNLOAD_CERT_BADGES.getName());
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the partner user could decline the DTS tuning file when the �Request accessory tunings� privilege is enabled
	 */
	@Test
	public void TCPPA_23(){
		productControl.addLog("ID : TCPPA_23: Verify that the partner user could decline the "
				+ "DTS tuning file when the �Request accessory tunings� privilege is enabled");
		/*
			Pre-condition: The �Request accessory tunings� privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to �Accessories� page
			4. Click �Add Accessory� link
			5. Fill valid value into all required fields
			6. Upload tuning file successfully
			7. Click �Save� link
			VP: Verify that the accessory detail page is displayed
			8. Log out DTS portal
			9. Log into DTS portal as a Partner user successfully
			10. Navigate to �Accessories� page
			11. Select above accessory from the accessories table
			VP: Verify that the tuning approval status is �PENDING PARTNER REVIEW� and its action links are �Approve Tuning� and �Decline Tuning�
			12. Click �Decline Tuning� link
		*/
		// 2. Log into DTS portal as a DTS user successfully
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// 3. Navigate to �Accessories� page
//		productControl.click(PageHome.linkAccessories);
//		// 4. Click �Add Accessory� link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		// 5. Fill valid value into all required fields
//		// 6. Upload tuning file successfully
//		// 7. Click �Save� link
//		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);

		userWf.loginAndEnableAllPrivilege(PARTNER_USER);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productWf.addNewProduct(dataProduct);
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 8. Log out DTS portal
		productControl.logout();
		// 9. Log into DTS portal as a Partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 10. Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 11. Select above accessory from the accessories table
		productControl.selectAccessorybyName(dataProduct.get("name"));
		/*
		 * VP: Verify that the tuning approval status is �PENDING PARTNER APPROVAL� and its action links are �Approve Tuning� and �Decline Tuning�
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), 
				ProductDetailModel.ProductStatus.PENDING_PARTNER_APPROVAL.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.APPROVE_TUNING), 
				ProductDetailModel.ProductActions.APPROVE_TUNING.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.DECLINE_TUNING), 
				ProductDetailModel.ProductActions.DECLINE_TUNING.getName());
		// 12. Click �Decline Tuning� link
//		productWf.dtsDeclineTuning();
		productWf.declineTuning();
//		productControl.editData(ProductDetailModel.DECLINE_TUNING_REQUIRED_MESS, "sample mess for decline tuning");
//		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
//		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		/*
		 * Verify that the tuning approval state is changed to �Declined� and the action links is �Request Revised Tuning"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), 
				ProductDetailModel.ProductStatus.DECLINED.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_REVISED_TUNING), 
				ProductDetailModel.ProductStatus.TUNING_REQUEST_REVISED.getName());
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);}
	
	/*
	 * Verify that the partner user could not approve or decline the DTS tuning file when the �Request accessory tunings� privilege is disabled
	 */
	@Test
	public void TCPPA_24(){
		productControl.addLog("ID : TCPPA_24: Verify that the partner user could not approve"
				+ " or decline the DTS tuning file when the �Request accessory tunings� privilege is disabled");
		/*
			Pre-condition: The �Request accessory tunings� privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to �Accessories� page
			4. Click �Add Accessory� link
			5. Fill valid value into all required fields
			6. Upload tuning file successfully
			7. Click �Save� link
			VP: Verify that the accessory detail page is displayed
			8. Log out DTS portal
			9. Log into DTS portal as a Partner user successfully
			10. Navigate to �Accessories� page
			11. Select above accessory from the accessories table
		*/
		
		/*
		 * Pre-condition: The �Request accessory tunings� privilege is disabled
		 */
//		// Log into DTS portal as DTS user
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// Navigate to user page
//		productControl.click(PageHome.LINK_USERS);
//		// Select a user
//		userControl.dtsSelectUserByEmail(PARTNER_USER);
//		// Click edit link
//		productControl.click(UserMgmt.EDIT);
//		// Disable privilege "Request accessory tunings�
//		userControl.enableAllPrivileges();
//		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Request_product_tunings);
//		productControl.click(AddUser.SAVE);
//		/*
//		 * ********************************************************
//		 */
//		// 3. Navigate to �Accessories� page
//		productControl.click(PageHome.linkAccessories);
//		// 4. Click �Add Accessory� link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		// 5. Fill valid value into all required fields
//		// 6. Upload tuning file successfully
//		// 7. Click �Save� link
//		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * VP: Verify that the accessory detail page is displayed
		 */
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		userWf.loginAndDisablePrivilege(PARTNER_USER, Privileges.privileges.Can_request_DTS_tune_products.getName(), false);
		productWf.addNewProduct(dataProduct);
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 8. Log out DTS portal
		productControl.logout();
		// 9. Log into DTS portal as a Partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 10. Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 11. Select above accessory from the accessories table
		productControl.selectAccessorybyName(dataProduct.get("name"));
		/*
		 * Verify that the tuning approval status is �PENDING PARTNER APPROVAL� but the �Approve Tuning� and �Decline Tuning� links are not displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS),
				ProductDetailModel.ProductStatus.PENDING_PARTNER_APPROVAL.getName());
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.APPROVE_TUNING));
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.DECLINE_TUNING));
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	
	/*
	 * Verify that the partner user is unable to add, edit and create new version of product and variant of  unassigned brand when the  "Add and manage Products� privilege is enabled.
	 */
	@Test
	public void TCPPA_25(){
		productControl.addLog("ID : TCPPA_25: Verify that the partner user is unable to add, edit"
				+ " and create new version of product and variant of  unassigned brand when"
				+ " the  'Add and manage Products' privilege is enabled.");
		/*
		 	Pre-condition: The "Add and manage products" privilege of partner user is enabled but only assign for only one specific brand
			
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Enable the �Add and manage Products� privilege but assign for a specific brand
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			9. Navigate to "Products" page
			VP. Verify that the �Add product� link is displayed inside �Actions� module and the Products table is also displayed.
			10. Click on �Add product� link
			VP. Verify that the Brand dropdown is not displayed
			11. Click Cancel link
			12. Select a draf product from products list which does not belong to the management of partner user
			VP. Verify that the Edit link is not displayed
			13. Navigate to Products list page again
			14.Select a published product from products list which does not belong to the management of partner user
			VP: Verify that the Create new version link is not displayed.
			15. Select a draf variant from variant list
			VP: The Edit link is not displayed in variant info page
			16. Navigate back to product info page
			17. Select a published variant from variants list
		 */
		
		// 2. Log into DTS portal as a DTS user successfully
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		userWf.enableAllPrivilegeOfUser(PARTNER_USER);
		userControl.selectBrandPrivilege( AddUser.BRAND_PRIVILEGES, BasePage.PARTNER_BRAND_NAME_2,true);
		// 3. Navigate to "Users" page
//		productControl.click(PageHome.LINK_USERS);
//		// 4.Select a partner user from the Users table
//		userControl.dtsSelectUserByEmail(PARTNER_USER);
//		// 5. Click "Edit" link
//		productControl.click(UserMgmt.EDIT);
//		// 6. Enable the �Add and manage Products� privilege but assign for a specific brand
//		userControl.enableAllPrivileges();
//		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, PARTNER_BRAND_NAME_2);
		productControl.click(AddUser.SAVE);
		//Create draft & pulished products/variants
		productControl.click(PageHome.linkAccessories);
		productControl.click(ProductModel.ADD_PRODUCT);
		//Create draft product
		Hashtable<String,String> draftProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), draftProduct);
		//Create published product
		productControl.click(PageHome.linkAccessories);
		Hashtable<String,String> publishProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndPublish(publishProduct);
		//Create draft variants
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(publishProduct.get("name"));
		productControl.click(ProductDetailModel.ADD_VARIANT);
		Hashtable<String,String> draftVariant = TestData.variantData(false, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), draftVariant);
		//Create a published variant
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.click(ProductDetailModel.ADD_VARIANT);
		Hashtable<String,String> publishvariant = TestData.variantData(true, true, true);
		productControl.addVariant(AddEditProductModel.getVariantHash(), publishvariant);
		// 7. Log out DTS portal
		productControl.logout();
		// 8. Log into DTS portal as above partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 9. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 10. Click on �Add product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// VP. Verify that the Brand dropdown is not displayed
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.BRAND));
		// 11. Click Cancel link
		productControl.click(AddEditProductModel.CANCEL_PRODUCT);
		// 12. Select a draft product from products list which does not belong to the management of partner user
		productControl.selectAccessorybyName(draftProduct.get("name"));
		// VP. Verify that the Edit link is not displayed
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.EDIT_MODE));
		// 13. Navigate to Products list page again
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 14. Select a published product from products list which does not belong to the management of partner user
		productControl.selectAccessorybyName(publishProduct.get("name"));
		// VP: Verify that the Create new version link is not displayed.
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.EDIT_MODE));
		// 15. Select a draft variant from variant list
		productControl.selectAVariantbyName(draftVariant.get("name"));
		// VP: The Edit link is not displayed in variant info page
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.EDIT_VARIANT));
		// 16. Navigate back to product info page
		productControl.click(VariantInfo.PRODUCT_LINK);
		// 17. Select a published variant from variants list
		productControl.selectAVariantbyName(publishvariant.get("name"));
		// Verify that the Create new version link is not displayed.
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.CREATE_NEW_VERSION));
		
		//Teardown: Delete products and variants
		productControl.logout();
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productControl.deleteAccessorybyName(draftProduct.get("name"));
		productControl.deleteAccessorybyName(publishProduct.get("name"));
//		productControl.click(PageHome.LINK_USERS);
//		userControl.dtsSelectUserByEmail(PARTNER_USER);
//		productControl.click(UserMgmt.EDIT);
//		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, ALL_BRANDS);
	}
	
	/*
	 * Verify that the partner user is unable to publish, suspend, restore or delete product and variant of  unassigned brand when the  "Publish and suspend products� privilege is enabled.
	 */
	@Test
	public void TCPPA_26(){
		productControl.addLog("ID : TCPPA_26: Verify that the partner user is unable to "
				+ "publish, suspend, restore or delete product and variant of  unassigned "
				+ "brand when the  'Publish and suspend products' privilege is enabled.");
		/*
		 	Pre-condition: Pre-condition: The "Publish and suspend products" privilege of partner user is enabled but only assign for only one specific brand
			
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Enable the �Publish and suspend products� privilege but assign for a specific brand
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			9. Navigate to "Products" page
			10. Select a ready to publish product which is out of brand privilege from products list
			VP. Verify that the "Publish" button is disabled and the "Delete" link is not displayed
			11. Navigate to "Products" page
			12. Set the product filter to "Suspended"
			13. Select a publish product which is out of brand privilege from products list
			VP: Verify that the "Restore" link is not displayed.
			14. Navigate to "Products" page
			15. Select a published product which is out of brand privilege from products list
			VP. Verify that the "Suspend" and the "Delete" links are not displayed
			16. Select a ready to publish variant from variants list
			VP. Verify that the "Publish" button is disabled and the "Delete" link is not displayed
			17. Back to product detail page
			18. Select a suspended variant from variants list
			VP. Verify that the "Un-suspend" link is not displayed
			19. Back to product detail page
			20. Select a published variant from variants list
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		userWf.enableAllPrivilegeOfUser(PARTNER_USER);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, BasePage.PARTNER_BRAND_NAME_1, false);
		productControl.click(AddUser.SAVE);

		//Create products and variants
		//Create ready to publish product
		Hashtable<String,String> readyToPublishProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndTunningAndMarketing(readyToPublishProduct, true);

		//Suspend a published product
		Hashtable<String,String> suspendedProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndSuspend(suspendedProduct);
//		productControl.selectConfirmationDialogOption("Suspend");
		
		//Create a published product
		Hashtable<String,String> publisedhProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndPublish(publisedhProduct);
		
		// Create a ready to publish variant
		Hashtable<String,String> readyToPublishVariant = TestData.variantData(false, true, true);
		productWf.addVariantAndTunningAndMarketing(readyToPublishVariant, true);
		
		//Suspend a published variant
		productControl.click(VariantInfo.PRODUCT_LINK);
		Hashtable<String,String> suspendedVariant = TestData.variantData(false, true, true);
		productWf.addVariantAndSuspend(suspendedVariant);
//		productControl.selectConfirmationDialogOption("Suspend");
		
		//Create a published variant
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(publisedhProduct.get("name"));
		Hashtable<String,String> publishedVariant = TestData.variantData(false, true, true);
		productWf.addVariantAndPublish(publishedVariant);
		
		// 7. Log out DTS portal
		productControl.logout();
		// 8. Log into DTS portal as above partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 9. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 10. Select a ready to publish product which is out of brand privilege from products list
		productControl.selectAccessorybyName(readyToPublishProduct.get("name"));
		// VP. Verify that the "Publish" button is disabled and the "Delete" link is not displayed
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.PUBLISH));
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.DELETE));
		// 11. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 12. Set the product filter to "Suspended"
		productControl.selectFilterByName(ProductModel.PRODUCT_FILTER, Constant.ACCESSORY_SUSPENDED);
		// 13. Select a publish product which is out of brand privilege from products list
		productControl.selectAccessorybyName(suspendedProduct.get("name"));
		// VP. Verify that the "Restore" link is not displayed.
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.UNSUSPEND));
		// 14. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 15. Select a published product which is out of brand privilege from products list
		productControl.selectAccessorybyName(publisedhProduct.get("name"));
		// VP. Verify that the "Suspend" and the "Delete" links are not displayed
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.SUSPEND));
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.DELETE));
		// 16. Select a ready to publish variant from variants list
		productControl.selectAVariantbyName(readyToPublishVariant.get("name"));
		// VP. Verify that the "Publish" button is disabled and the "Delete" link is not displayed
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.PUBLISH));
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.DELETE_VARIANT));
		// 17. Back to product detail page
		productControl.click(VariantInfo.PRODUCT_LINK);
		// 18. Select a suspended variant from variants list
		productControl.selectAVariantbyName(suspendedVariant.get("name"));
		// VP. Verify that the "Un-suspend" link is not displayed
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.UNSUSPEND));
		// 19. Back to product detail page
		productControl.click(VariantInfo.PRODUCT_LINK);
		//20. Select a published variant from variants list
		productControl.selectAVariantbyName(publishedVariant.get("name"));
		//VP. Verify that the "Suspend" and the "Delete" links are not displayed
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.SUSPEND));
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.DELETE_VARIANT));
		
		//Teardown: Delete products and variants
		productControl.logout();
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productControl.deleteAccessorybyName(readyToPublishProduct.get("name"));
		productControl.deleteAccessorybyName(publisedhProduct.get("name"));
//		productControl.deleteAccessorybyName(suspendedProduct.get("name"));
		productControl.selectFilterByName(ProductModel.PRODUCT_FILTER, Constant.ACCESSORY_SUSPENDED);
		productControl.selectAccessorybyName(suspendedProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		productControl.click(PageHome.LINK_USERS);
		userControl.dtsSelectUserByEmail(PARTNER_USER);
		productControl.click(UserMgmt.EDIT);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, ALL_BRANDS);
	}
	
	/*
	 * Verify that the partner user is unable to request, approve, decline or revoke tuning of product and variant for unassigned brand when the  "Can request DTS tune products� privilege is enabled.
	 */
	@Test
	public void TCPPA_27(){
		productControl.addLog("ID : TCPPA_27: Verify that the partner user is unable to request, "
				+ "approve, decline or revoke tuning of product and variant for unassigned brand "
				+ "when the 'Can request DTS tune products' privilege is enabled.");
		/*
			Pre-condition: The "Can request DTS tune products" privilege of partner user is enabled but only assign for only one specific brand

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Enable the �Can request DTS tune products� privilege but assign for a specific brand
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			9. Navigate to "Products" page
			10. Select an unuploaded tuning product which is out of brand privilege from products list
			VP. Verify that the "Request DTS Tuning" link is not displayed
			11. Navigate to "Products" page
			12. Select a product which tuning is up uploaded by DTS and request partner to approve which is out of brand privilege from products list
			VP: Verify that the "Approve Tuning" and "Decline Tuning" link are not displayed.
			13. Navigate to "Products" page
			14. Select a approved tuning product which is out of brand privilege from products list
			VP: Verify that the "Revoke Tuning" link are not displayed.
			15. Navigate to "Products" page
			16. Select a declined tuning product which is out of brand privilege from products list
			VP. Verify that the "Request Revised Tuning" link is not displayed
			17. Navigate back to product detail page
			18. Select an unuploaded tuing variant
			VP. Verify that the "Request DTS Tuning" link is not displayed
			19. Navigate back to product info page
			20.  Select a variant which tuning is up uploaded by DTS and request partner to approve
			VP: Verify that the "Approve Tuning" and "Decline Tuning" link are not displayed.
			21. Navigate back to product info page
			22. Select a approved tuning variant from variants list
			VP: Verify that the "Revoke Tuning" link are not displayed.
			23. Select a declined tuning variant which is out of brand privilege from products list
			VP. Verify that the "Request Revised Tuning" link is not displayed
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		userWf.enableAllPrivilegeOfUser(PARTNER_USER);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, BasePage.PARTNER_BRAND_NAME_1, false);
		productControl.click(AddUser.SAVE);
		
//		// 2. Log into DTS portal as a DTS user successfully
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// 3. Navigate to "Users" page
//		productControl.click(PageHome.LINK_USERS);
//		// 4.Select a partner user from the Users table
//		userControl.dtsSelectUserByEmail(PARTNER_USER);
//		// 5. Click "Edit" link
//		productControl.click(UserMgmt.EDIT);
//		// 6. Enable the �Publish and suspend products� privilege but assign for a specific brand
//		userControl.enableAllPrivileges();
//		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, PARTNER_BRAND_NAME_2);
//		productControl.click(AddUser.SAVE);
		//Create ready to publish, published and suspended products/variants
		productControl.click(PageHome.linkAccessories);
		productControl.click(ProductModel.ADD_PRODUCT);
		//Create products and variants
		//Create an un-uploaded tuning product which is out of brand privilege from products list
		productControl.click(PageHome.linkAccessories);
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> draftproduct= TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), draftproduct);
		
		//Create a product which tuning is up uploaded by DTS and request partner to approve
		productControl.click(PageHome.linkAccessories);
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> tuninguploadedproduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), tuninguploadedproduct);
		
		//Create a approved tuning product
		productControl.click(PageHome.linkAccessories);
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> tuningapprovedproduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), tuningapprovedproduct);
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		
		//Create a a declined tuning product 
		productControl.click(PageHome.linkAccessories);
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> tuningdeclinedproduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), tuningdeclinedproduct);
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productWf.declineTuning();
		
		// Create an un-uploaded tuning variant which is out of brand privilege from variants list
		productControl.click(ProductDetailModel.ADD_VARIANT);
		String draftvariant = RandomStringUtils.randomNumeric(10);
		productControl.editData(AddEditProductModel.MODEL_NAME,draftvariant );
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_TUNNING);
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		
		//Create a variant which tuning is up uploaded by DTS and request partner to approve
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.click(ProductDetailModel.ADD_VARIANT);
		Hashtable<String,String> tuninguploadedvariant = TestData.variantData(true, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), tuninguploadedvariant);
		
		//Create an approved variant
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.click(ProductDetailModel.ADD_VARIANT);
		Hashtable<String,String> tuningapprovevariant = TestData.variantData(true, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), tuningapprovevariant);
//		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		productWf.approveTuning();
		
		//Create a a declined tuning variant 
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.click(ProductDetailModel.ADD_VARIANT);
		Hashtable<String,String> declinedtuningvariant = TestData.variantData(true, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), declinedtuningvariant);
		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		productControl.click(VariantInfo.DECLINE_TUNING);
		
		// 7. Log out DTS portal
		productControl.logout();
		// 8. Log into DTS portal as above partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 9. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 10. Select an un-uploaded tuning product which is out of brand privilege from products list
		productControl.selectAccessorybyName(draftproduct.get("name"));
		// VP.Verify that the "Request DTS Tuning" link is not displayed
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.REQUEST_TUNING));
		// 11. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 12. Select a product which tuning is up uploaded by DTS and request partner to approve which is out of brand privilege from products list
		productControl.selectAccessorybyName(tuninguploadedproduct.get("name"));
		// VP. Verify that the "Approve Tuning" and "Decline Tuning" link are not displayed.
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.APPROVE_TUNING));
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.DECLINE_TUNING));
		// 13. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 14. Select a approved tuning product which is out of brand privilege from products list
		productControl.selectAccessorybyName(tuningapprovedproduct.get("name"));
		// VP. Verify that the "Revoke Tuning" link is not displayed
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.REVOKE_TUNING));
		// 15. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		//16. Select a declined tuning product which is out of brand privilege from products list
		productControl.selectAccessorybyName(tuningdeclinedproduct.get("name"));
		// VP. Verify that the "Request Revised Tuning" link is not displayed
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.REQUEST_REVISED_TUNING));
		// 17. Navigate back to product detail page
		productControl.click(VariantInfo.PRODUCT_LINK);
		// 18. Select an unuploaded tuing variant
		productControl.selectAVariantbyName(draftvariant);
		// VP. Verify that the "Request DTS Tuning" link is not displayed
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.REQUEST_DTS_TUNING));
		// 19. Back to product detail page
		productControl.click(VariantInfo.PRODUCT_LINK);
		//20.  Select a variant which tuning is up uploaded by DTS and request partner to approve
		productControl.selectAVariantbyName(tuninguploadedvariant.get("name"));
		//VP. VP: Verify that the "Approve Tuning" and "Decline Tuning" link are not displayed.
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.APPROVE_TUNING));
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.DECLINE_TUNING));
		//21. Navigate back to product info page
		productControl.click(VariantInfo.PRODUCT_LINK);
		//22. Select a approved tuning variant from variants list
		productControl.selectAVariantbyName(tuningapprovevariant.get("name"));
		//VP: Verify that the "Revoke Tuning" link are not displayed.
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.REVOKE_TUNING));
		//23. Select a declined tuning variant which is out of brand privilege from products list
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.selectAVariantbyName(declinedtuningvariant.get("name"));
		//VP. Verify that the "Request Revised Tuning" link is not displayed
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.REQUEST_REVISED_TUNING));
		
		//Teardown: Delete products and variants
		productControl.logout();
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productControl.deleteAccessorybyName(tuningapprovedproduct.get("name"));
		productControl.deleteAccessorybyName(draftproduct.get("name"));
		productControl.deleteAccessorybyName(tuningdeclinedproduct.get("name"));
		productControl.deleteAccessorybyName(tuninguploadedproduct.get("name"));
		productControl.click(PageHome.LINK_USERS);
		userControl.dtsSelectUserByEmail(PARTNER_USER);
		productControl.click(UserMgmt.EDIT);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, ALL_BRANDS);
	}
}	
