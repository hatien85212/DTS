package com.dts.adminportal.dts.test;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PartnerVariantInfo;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.TuningRequestForm;
import com.dts.adminportal.model.UserEdit;
import com.dts.adminportal.model.UserInfo;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.model.VariantInfo;
import com.dts.adminportal.util.TestData;

public class DTSPrivilegesAccessories extends BasePage{
	
	@Override
	protected void initData() {
	}	
	/*
	 * Verify that the DTS user can add accessories successfully when the  "Add and manage accessories� privilege is enabled
	 */
	@Test
	public void TCPDA_01(){
		productControl.addLog("ID : TCPDA_01 : Verify that the DTS user can add accessories successfully when the  'Add and manage accessories' privilege is enabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the �Add and manage accessories� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			9. Navigate to �Home� page
			10. Navigate to �Accessories� page
			11. Click on �Add Accessory� link
			12. Fill valid value into all required fields
			13. Click �Save� link
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		productControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		productControl.click(UserMgmt.EDIT);
		// 6. Enable the �Add and manage accessories� privilege
		userControl.enableAllPrivileges();
		productControl.click(AddUser.SAVE);
		//7. Log out DTS portal
		productControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, PARTNER_PASSWORD);
		// 9. Navigate to �Home� page
		// 10. Navigate to �Accessories� page
		productControl.click(PageHome.linkAccessories);
		// 11. Click on �Add Accessory� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 12. Fill valid value into all required fields
		// 13. Click �Save� link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * Verify that the new product is displayed in products table
		 */
		productControl.click(PageHome.linkAccessories);
		Assert.assertTrue(productControl.checkAnAccessoryExistByName(data.get("name")));
		// Delete the added accessory
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 Verify that the DTS user can edit accessories successfully when the "Add and manage accessories� privilege is enabled.
	 */
	@Test
	public void TCPDA_02(){
		productControl.addLog("ID : TCPDA_02 : Verify that the DTS user can edit accessories successfully when the  'Add and manage accessories' privilege is enabled.");
		/*
		  	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a user from users table
			VP: Verify that the 090D User Info page is displayed
			5. Click "Edit" link
			6. Enable "Add and manage accessories" site privilege for above user
			7. Log out DTS portal
			8. Log into DTS portal as above partner admin
			9. Navigate to "Accessories" page
			10. Select an accessory from accessories table
			VP: Verify that 040P Accessory Detail page is displayed with "Edit" link
			11. Click "Edit" link
			12. Verify that 051P Accessory Edit Page is displayed
			13. Change any of accessory's information
			14. Click "Save" link
		 */
		/*
		 * PreCondition: Create new product
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create new product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * **********************************************************
		 */
		// 3. Navigate to "Users" page
		productControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		productControl.click(UserMgmt.EDIT);
		// 6. Enable the �Add and manage accessories� privilege
		userControl.enablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE,Privileges.Add_and_manage_products);
		productControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		productControl.logout();
		// 8. Log into DTS portal as above partner admin
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 9. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 10. Select an accessory from accessories table
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that 040P Accessory Detail page is displayed with "Edit" link
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), data.get("name"));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.EDIT_MODE));
		// 11. Click "Edit" link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 12. Verify that 051P Accessory Edit Page is displayed
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getProductElementFieldIds()), true);
		// 13. Change any of accessory's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		productControl.editData(AddEditProductModel.MODEL_NAME, newName);
		// 14. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * Verify that new product's information is displayed correctly
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), newName);
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 Verify that the DTS user can add accessories variant successfully when the  "Add and manage accessories� privilege is enabled
	 */
	@Test
	public void TCPDA_03(){
		productControl.addLog("ID : TCPDA_03 : Verify that the DTS user can add accessories variant successfully when the  'Add and manage accessories' privilege is enabled");
		
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the �Add and manage products� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			9. Navigate to �products� page
			10. Select a published product from products table
			VP. Verify that the �Add product Variant� link displayed in product detail page
			11. Click on �Add product Variant� link in product detail page
			VP. Verify that the adding new product variant page is displayed
			12. Fill valid value into all required fields
			13. Click �Save� link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		productControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		productControl.click(UserMgmt.EDIT);
		// 6. Enable the �Add and manage accessories� privilege
		userControl.enablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE,Privileges.Add_and_manage_products);
		productControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		productControl.logout();
		// 8. Log into DTS portal as above partner admin
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 9. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 10. Select a published accessory from accessories table
		productControl.selectAnAccessory();
		/*
		 * VP. Verify that the �Add product Variant� link displayed in product detail page
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.ADD_VARIANT));
		// 11. Click on �Add Accessory Variant� link in accessory detail page
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
		Assert.assertEquals(productControl.existsElement(VariantInfo.getElementsInfo()), true);
	}
	
	/*
	 * Verify that the DTS user can edit accessories variant successfully when the  "Add and manage accessories� privilege is enabled
	 */
	@Test
	public void TCPDA_04(){
		productControl.addLog("ID : TCPDA_04 : Verify that the DTS user can edit accessories variant successfully when the  'Add and manage accessories' privilege is enabled");
		
		/*
		  	1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the �Add and manage products� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			9. Navigate to �products� page
			10. Select an product from products table which has at least one variant
			11. Select an product variant from variant list in product detail page
			VP: Verify that the product variant detail page is displayed and the �Edit� link is displayed too
			12. Click �Edit� link
			13. Change product variant's information
			14. Click �Save� link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		productControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		productControl.click(UserMgmt.EDIT);
		// 6. Enable the �Add and manage accessories� privilege
		userControl.enablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE,Privileges.Add_and_manage_products);
		productControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		productControl.logout();
		// 8. Log into DTS portal as above partner admin
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 9. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 10. Select an product from products table which has at least one variant
		productControl.selectAnAccessory();
		// 11. Select an product variant from variant list in product detail page
		Hashtable<String,String> data = TestData.variantData(false, false, false);
		productControl.click(ProductDetailModel.ADD_VARIANT);
		productControl.addVariant(AddEditProductModel.getVariantHash(), data);
		/*
		 * VP: Verify that the product variant detail page is displayed and the �Edit� link is displayed too
		 */
		Assert.assertEquals(productControl.existsElement(VariantInfo.getElementsInfo()), true);
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.EDIT_VARIANT));
		// 12. Click �Edit� link
		productControl.click(VariantInfo.EDIT_VARIANT);
		// 13. Change product variant's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		productControl.editData(AddEditProductModel.MODEL_NAME, newName);
		// 14. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * Verify that The product variant's information is changed successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.DISPLAYNAME_DEFAULT), newName);
	}
	
	/*
	 * Verify that the DTS user can view accessory's published version info when the "Add and manage accessories� privilege is either enabled or disabled.
	 */
	@Test
	public void TCPDA_05(){
		productControl.addLog("ID : TCPDA_05 : Verify that the DTS user can view accessory's published version info when the 'Add and manage accessories' privilege is either enabled or disabled.");
		
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the �Add and manage products� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			9. Navigate to �products� page
			10. Select a product that has at least one published version from products table
			VP: The �View Published Version� link is displayed.
			11. Log out DTS portal
			12. Perform from step 2 to 5 again
			13. Disabled the �Add and manage products� privilege
			14. Perform from step 7 to 10 again
		 */
		/*
		 * Pre-Condition: Create product has at least one published version
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create publish accessory
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Create new version
		productControl.click(ProductDetailModel.UPDATE_PRODUCT_INFO);
		productControl.switchWindowClickOption("Ok");
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * ******************************************************************
		 */
		// 3. Navigate to "Users" page
		productControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		productControl.click(UserMgmt.EDIT);
		// 6. Enable the �Add and manage accessories� privilege
		userControl.enablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE,Privileges.Add_and_manage_products);
		productControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		productControl.logout();
		// 8. Log into DTS portal as above partner admin
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 9. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 10. Select a product that has at least one published version from products table
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 *  VP: The �View Published Version� link is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.VIEW_PUBLISHED_MODEL));
		// 11. Log out DTS portal
		productControl.logout();
		// 12. Perform from step 2 to 5 again
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//Navigate to "Users" page
		productControl.click(PageHome.LINK_USERS);
	    // Select a user from users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// Click "Edit" link
		productControl.click(UserMgmt.EDIT);
		// 13. Disabled the �Add and manage products� privilege
		userControl.disablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		productControl.click(AddUser.SAVE);
		// 14. Perform from step 7 to 10 again
		// Log out DTS portal
		productControl.logout();
		// Log into DTS portal as above partner admin
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// Select a published accessory from accessories table
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 *  VP: The �View Published Version� link is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.VIEW_PUBLISHED_MODEL));
	}
	
	/*
	 Verify that the DTS user can view accessory variant's published version info when the "Add and manage accessories� privilege is either enabled or disabled.
	 */
	@Test
	public void TCPDA_06(){
		productControl.addLog("ID : TCPDA_06 : Verify that the DTS user can view accessory variant's published version info when the 'Add and manage accessories' privilege is either enabled or disabled.");
		
		/*
		  	1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the �Add and manage accessories� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS\ user successfully
			9. Navigate to �Accessories� page
			10. Select a published accessory from accessories table which have at least one accessory variant is also published
			11. Select the published accessory variant from variant list in accessory detail page
			VP: The �View Published Version� link is displayed in accessory variant detail page
			12. Log out DTS portal
			13. Perform from step 2 to 5 again
			14. Disabled the �Add and manage accessories� privilege
			15. Perform from step 7 to 11 again
		 */
		/*
		 * Pre-condition: Create published product which has at least one published version variant
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create published product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Click Add variant link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// Create publish variant
		Hashtable<String,String> dataVariant = TestData.variantData(true, true, true);
		productWf.createVariantPublish(AddEditProductModel.getVariantHash(), dataVariant);
		// Create new version
		productControl.click(VariantInfo.CREATE_NEW_VERSION);
		productControl.switchWindowClickOption("Ok");
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * *********************************************************************
		 */
		// 3. Navigate to "Users" page
		productControl.click(PageHome.LINK_USERS);
		// 4. Select a user from users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click "Edit" link
		productControl.click(UserMgmt.EDIT);
		// 6. Enable "Add and manage accessories" site privilege for above user
		userControl.enablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		productControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		productControl.logout();
		// 8. Log into DTS portal as above partner admin
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 9. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 10. Select a published accessory from accessories table
		productControl.selectAccessorybyName(data.get("name"));
		// 11. Select the published accessory variant from variant list in accessory detail page
		productControl.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: The �View Published Version� link is displayed in accessory variant detail page
		 */
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.VIEW_PUBLISHED_VERSION));
		// 12. Log out DTS portal
		productControl.logout();
		// 13. Perform from step 2 to 5 again
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to user page
		productControl.click(PageHome.LINK_USERS);
		// Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// Click �Edit� link
		productControl.click(UserMgmt.EDIT);
		// 14. Disabled the �Add and manage accessories� privilege
		userControl.disablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		productControl.click(AddUser.SAVE);
		// 15. Perform from step 7 to 11 again
		productControl.logout();
		// Log into DTS portal as above partner admin
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// Select a published accessory from accessories table
		productControl.selectAccessorybyName(data.get("name"));
		// Select the published accessory variant from variant list in accessory detail page
		productControl.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: The �View Published Version� link is displayed in accessory variant detail page
		 */
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.VIEW_PUBLISHED_VERSION));
		// Delete product
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the DTS user can only create a new version but not to edit a published accessory when the "Add and manage accessories� privilege is enabled
	 */
	@Test
	public void TCPDA_07(){
		productControl.addLog("ID : TCPDA_07 : Verify that the DTS user can only create a new version but not to edit a published accessory when the 'Add and manage accessories' privilege is enabled");
		/*
		 	Pre-condition: The  "Add and manage products� privilege is enabled.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to �products� page
			4. Click �Add product� link
			5. Fill valid value into all required fields
			6. Pass through two steps of Publication Process
			7. Click �Publish� button in step 3 of Publication Process
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to �products� page
		productControl.click(PageHome.linkAccessories);
		// 4. Click �Add product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 5. Fill valid value into all required fields
		// 6. Pass through two steps of Publication Process
		// 7. Click �Publish� button in step 3 of Publication Process
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * Verify that The Edit Link disappears and the �Create New Version� is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.UPDATE_PRODUCT_INFO), "Update Product Info");
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the DTS user can only create a new version but not to edit a published accessory variant when the "Add and manage accessories� privilege is enabled
	 */
	@Test
	public void TCPDA_08(){
		productControl.addLog("ID : TCPDA_08 : Verify that the DTS user can only create a new version but not to edit a published accessory variant when the 'Add and manage accessories' privilege is enabled");
		/*
		  	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to �Accessories� page
			4. Select a published accessory from the accessories table
			5. Click �Add new variant� link
			6. Fill valid value into all required fields
			7. Pass through two steps of Publication Process
			8. Click �Publish� button in step 3 of Publication Process
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to �Accessories� page
		productControl.click(PageHome.linkAccessories);
		// 4. Select a published accessory from the accessories table
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create published product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 5. Click �Add new variant� link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 6. Fill valid value into all required fields
		// 7. Pass through two steps of Publication Process
		// 8. Click �Publish� button in step 3 of Publication Process
		Hashtable<String,String> dataVariant = TestData.variantData(true, true, true);
		productWf.createVariantPublish(AddEditProductModel.getVariantHash(), dataVariant);
		/*
		 * Verify that The Edit Link disappears and the �Create New Version� is displayed
		 */
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.EDIT_VARIANT));
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.CREATE_NEW_VERSION));
		// Delete product
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 Verify that the DTS user cannot add new or edit accessories or accessories variant when the "Add and manage accessories� privilege is disabled.
	 */
	@Test
	public void TCPDA_09(){
		productControl.addLog("ID : TCPDA_09 : Verify that the DTS user cannot add new or edit accessories or accessories variant when the 'Add and manage accessories' privilege is disabled");
		
		/*
		 * 	Pre-condition: The  "Add and manage accessories� privilege is disabled.
		  	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to �Accessories� page
			4. Select a non published accessory from the accessories table which has at least one accessory variant
			VP: Verify that there is no edit link displayed
			5. Select an accessory variant from the variant list
			VP: Verify that there is no edit link displayed for an accessory variant
		 */
		/*
		 * Pre-Conditions : The "Add and manage accessories� privilege is disabled and product which has at least one variant is created
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Users" page
		productControl.click(PageHome.LINK_USERS);
		// Select a user from users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// Click "Edit" link
		productControl.click(UserMgmt.EDIT);
		// Disable "Add and manage accessories" site privilege for above user
		userControl.disablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		productControl.click(AddUser.SAVE);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Click Add variant link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// Create variant
		Hashtable<String,String> dataVariant = TestData.variantData(false, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		// Log out
		productControl.logout();
		/*
		 * *********************************************************************
		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 3. Navigate to �Accessories� page
		productControl.click(PageHome.linkAccessories);
		// 4. Select a non published accessory from the accessories table which has at least one accessory variant
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that there is no edit link displayed
		 */
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.EDIT_MODE));
		// 5. Select an accessory variant from the variant list
		productControl.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that there is no edit link displayed for an accessory variant
		 */
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.EDIT_VARIANT));
		// Delete product
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 Verify that the DTS user cannot create new version for an accessory or accessory variant when the "Add and manage accessories� privilege is disabled
	 */
	@Test
	public void TCPDA_10(){
		productControl.addLog("ID : TCPDA_10 : Verify that the DTS user cannot create new version for an accessory or accessory variant when the 'Add and manage accessories' privilege is disabled");
		/*
			Pre-condition: The  "Add and manage products� privilege is disabled.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to �products� page
			4. Select a published product from the products table which has at least one published product variant
			VP: Verify that there is no �Create New Version� link displayed for a published product
			5. Select a published product variant from the variant list
			VP: Verify that there is no �Create New Version� link displayed for a published product variant
		 */
		/*
		 * Pre-Conditions : The "Add and manage accessories� privilege is disabled and published product which has at least one published variant is created
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Users" page
		productControl.click(PageHome.LINK_USERS);
		// Select a user from users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// Click "Edit" link
		productControl.click(UserMgmt.EDIT);
		// Disable "Add and manage accessories" site privilege for above user
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		productControl.click(AddUser.SAVE);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create published product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Click Add variant link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// Create published variant
		Hashtable<String,String> dataVariant = TestData.variantData(true, true, true);
		productWf.createVariantPublish(AddEditProductModel.getVariantHash(), dataVariant);
		// Log out
		productControl.logout();
		/*
		 * ******************************************************************
		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 3. Navigate to �products� page
		productControl.click(PageHome.linkAccessories);
		// 4. Select a published product from the products table which has at least one published product variant
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that there is no �Create New Version� link displayed for a published product
		 */
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.UPDATE_PRODUCT_INFO));
		// 5. Select a published product variant from the variant list
		productControl.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that there is no �Create New Version� link displayed for a published product variant
		 */
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.CREATE_NEW_VERSION));
	}
	
	/*
	 * Verify that the DTS user could publish accessory successfully when the �Publish and suspend accessories� privilege is enabled
	 */
	@Test
	public void TCPDA_11(){
		productControl.addLog("ID : TCPDA_11 : Verify that the DTS user could publish accessory successfully when the �Publish and suspend accessories� privilege is enabled");
		/*
		  	Pre-condition: The �Publish and suspend accessories� privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to �Accessories� page
			4. Click �Add Accessory� link
			5. Fill valid value into all required fields
			6. Upload all valid necessary files
			7. Click �Save� link
			8. Pass through two steps of Approval Process
			VP The �Published� button in step 3 �Publishing� of �Approval Process� is enabled 
			9. Click �Publish� button
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to �Accessories� page
		productControl.click(PageHome.linkAccessories);
		// 4. Click �Add Accessory� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 5. Fill valid value into all required fields
		// 6. Upload all valid necessary files
		// 7. Click �Save� link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 8. Pass through two steps of Approval Process
		// Approve tuning
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		// Approve marketing
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		/*
		 * VP: The �Published� button in step 3 �Publishing� of �Approval Process� is enabled 
		 */
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button button-warning");
		// 9. Click �Publish� button
		productControl.click(ProductDetailModel.PUBLISH);
		/*
		 * Verify that New product could be upload successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PUBLISH_STATUS), "PUBLISHED");
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that DTS user could not publish accessory when the �Publish and suspend accessories� privilege is disabled
	 */
	@Test
	public void TCPDA_12(){
		productControl.addLog("ID : TCPDA_12 : Verify that DTS user could not publish accessory when the �Publish and suspend accessories� privilege is disabled");
		/*
		  	Pre-condition: The �Publish and suspend accessories� privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to �Accessories� page
			4. Click �Add Accessory� link
			5. Fill valid value into all required fields
			6. Upload all valid necessary files
			7. Click �Save� link
			8. Pass through two steps of Approval Process
		 */
		/*
		 * Pre-Conditions : The "Publish and suspend accessories� privilege is disabled
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Users" page
		productControl.click(PageHome.LINK_USERS);
		// Select a user from users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// Click "Edit" link
		productControl.click(UserMgmt.EDIT);
		// Disable "Publish and suspend accessories" site privilege for above user
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Publish_and_suspend_products);
		productControl.click(AddUser.SAVE);
		// Log out
		productControl.logout();
		/*
		 * ***************************************************************
		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 3. Navigate to �Accessories� page
		productControl.click(PageHome.linkAccessories);
		// 4. Click �Add Accessory� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 5. Fill valid value into all required fields
		// 6. Upload all valid necessary files
		// 7. Click �Save� link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 8. Pass through two steps of Approval Process
		// Approve tuning
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		// Log out
		productControl.logout();
		// Log in DTS portal as partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select accessory created above
		productControl.selectAccessorybyName(data.get("name"));
		// Click Request marketing review link
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		// Log out
		productControl.logout();
		// Log in DTS portal as DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Select created product above
		productControl.selectAccessorybyName(data.get("name"));
		// Approve marketing
		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		/*
		 * Verify that The �Published� button in step 3 �Publishing� of �Approval Process� is disabled
		 */
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.PUBLISH));
	}
	
	/*
	 * Verify that DTS user could publish product variant successfully when the �Publish and suspend products� privilege is enabled
	 */
	@Test
	public void TCPDA_13(){
		productControl.addLog("ID : TCPDA_13 : Verify that DTS user could publish product variant successfully when the �Publish and suspend products� privilege is enabled");
		/*
		  	Pre-condition: The �Publish and suspend products� privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to �products� page
			4. Select a published product from products table
			5. Click �Add Variant� link
			6. Fill valid value into all required fields
			7. Upload all valid necessary files
			8. Click �Save� link
			9. Pass through two steps of Approval Process of product variant
			VP The �Published� button in step 3 �Publishing� of �Approval Process� is enabled 
			10 Click �Publish� button
		 */
		/*
		 * Pre-Conditions : The "Publish and suspend accessories� privilege is enable and published product is created
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create publish product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * ***************************************************************
		 */
		// 5. Click �Add Variant� link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 6. Fill valid value into all required fields
		// 7. Upload all valid necessary files
		// 8. Click �Save� link
		Hashtable<String,String> dataVariant = TestData.variantData(false, true, true);
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		// 9. Pass through two steps of Approval Process of product variant
		// Approve marketing
		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		productControl.click(VariantInfo.REQUEST_MARKETING_REVIEW);
		productControl.click(VariantInfo.APPROVE_MARKETING);
		/*
		 * VP The �Published� button in step 3 �Publishing� of �Approval Process� is enabled 
		 */
		Assert.assertEquals(productControl.getAtributeValue(VariantInfo.PUBLISH, "class"), "button button-warning");
		// 10 Click �Publish� button
		productControl.click(VariantInfo.PUBLISH);
		/*
		 * Verify that New product variant could be published  successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.PUBLISHED_STATUS), "Published");
		// Delete product
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that DTS user could not publish accessory when the �Publish and suspend accessories� privilege is disabled
	 */
	@Test
	public void TCPDA_14(){
		productControl.addLog("ID : TCPDA_14 : Verify that DTS user could not publish accessory when the �Publish and suspend accessories� privilege is disabled");
		/*
		  	Pre-condition: The �Publish and suspend accessories� privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to �Accessories� page
			4. Select a published accessory from accessories table
			5. Click �Add Variant� link
			6. Fill valid value into all required fields
			7. Upload all valid necessary files
			8. Click �Save� link
			9. Pass through two steps of Approval Process of accessory variant
		 */
		/*
		 * Pre-Conditions : The "Publish and suspend accessories� privilege is disabled and one published product is created
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Users" page
		productControl.click(PageHome.LINK_USERS);
		// Select a user from users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// Click "Edit" link
		productControl.click(UserMgmt.EDIT);
		// Disable "Publish and suspend accessories" site privilege for above user
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.Publish_and_suspend_products);
		productControl.click(AddUser.SAVE);
		// Navigate to Product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create published product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Log out
		productControl.logout();
		/*
		 * ***************************************************************
		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 3. Navigate to �Accessories� page
		productControl.click(PageHome.linkAccessories);
		// 4. Select a published accessory from accessories table
		productControl.selectAccessorybyName(data.get("name"));
		// 5. Click �Add Variant� link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 6. Fill valid value into all required fields
		// 7. Upload all valid necessary files
		// 8. Click �Save� link
		Hashtable<String,String> dataVariant = TestData.variantData(false, true, true);
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		// 9. Pass through two steps of Approval Process of accessory variant
		productControl.logout();
		// Log in DTS user as partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select product created above
		productControl.selectAccessorybyName(data.get("name"));
		// Select variant created above
		productControl.selectAVariantbyName(dataVariant.get("name"));
		// Click Request marketing review link
		productControl.click(PartnerVariantInfo.REQUEST_MARKETING_REVIEW);
		// Log out
		productControl.logout();
		// Log in DTS portal as DTS user above
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Select product created above
		productControl.selectAccessorybyName(data.get("name"));
		// Select variant created above
		productControl.selectAVariantbyName(dataVariant.get("name"));
		// Approve marketing
		productControl.click(VariantInfo.APPROVE_MARKETING);
		/*
		 * Verify that The �Published� button in step 3 �Publishing� of �Approval Process� is disabled 
		 */
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.PUBLISH));
	}
	
	/*
	 Verify that DTS user could delete accessory and its variant successfully when the �Publish and suspend accessories� privilege is enabled.
	 */
	@Test
	public void TCPDA_15(){
		productControl.addLog("ID : TCPDA_15 : Verify that DTS user could delete accessory and its variant successfully when the �Publish and suspend accessories� privilege is enabled");
		
		/*
		  	Pre-condition: The �Publish and suspend accessories� privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to �Accessories� page
			4. Select an accessory from accessories table which has at least one variant
			5. Select a variant from variant list
			6. Click �Delete� link in accessory variant detail page
			VP: Verify that the accessory variant is deleted successfully and the accessory detail page is displayed
			7. Click �Delete� link in accessory detail page
			VP: Verify that the accessory is deleted successfully.
		 */
		/*
		 * Pre-condition: Product which has at least one variant is created
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Click Add new variant link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// Create variant
		Hashtable<String,String> dataVariant = TestData.variantData(false, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		/*
		 * ******************************************************************
		 */
		// 3. Navigate to �Accessories� page
		productControl.click(PageHome.linkAccessories);
		// 4. Select an accessory from accessories table which has at least one variant
		productControl.selectAccessorybyName(data.get("name"));
		// 5. Select a variant from variant list
		productControl.selectAVariantbyName(dataVariant.get("name"));
		// 6. Click �Delete� link in accessory variant detail page
		productControl.doDelete(VariantInfo.DELETE_VARIANT);
		/*
		 * VP: Verify that the accessory variant is deleted successfully and the accessory detail page is displayed
		 */
		// 7. Click �Delete� link in accessory detail page
		productControl.doDelete(ProductDetailModel.DELETE);
		/*
		 * VP: Verify that the accessory is deleted successfully
		 */
		Assert.assertFalse(productControl.checkAnAccessoryExistByName(data.get("name")));
	}
	
	/*
	 * Verify that DTS user could not delete accessory and its variant successfully when the �Publish and suspend accessories� privilege is disabled.
	 */
	@Test
	public void TCPDA_16(){
		productControl.addLog("ID : TCPDA_16 : Verify that DTS user could not delete accessory and its variant successfully when the �Publish and suspend accessories� privilege is disabled");
		/*
		  	Pre-condition: The �Publish and suspend Products� privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to �Products� page
			4. Select an product from Products table which has at least one variant
			VP: Verify that the �Delete� link is not displayed in product detail page
			5. Select a variant from variant list
			VP: Verify that the �Delete� link is not displayed in product variant detail page
		 */
		/*
		 * Pre-condition: The �Publish and suspend Products� privilege is
		 * disabled and a product which has at least one variant is created
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Users" page
		productControl.click(PageHome.LINK_USERS);
		// Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// Click �Edit� link
		productControl.click(UserInfo.EDIT);
		// Disable "Publish and suspend Products" privilege
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE,Privileges.Publish_and_suspend_products);
		productControl.click(AddUser.SAVE);
		// Navigate to �Product� page
		productControl.click(PageHome.linkAccessories);
		// Click "Add product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create new product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		// Click Add new variant link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// Create new variant
		Hashtable<String,String> dataVariant = TestData.variantData(false, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		// Log out
		productControl.logout();
		/*
		 * ****************************************************************
		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 3. Navigate to �Products� page
		productControl.click(PageHome.linkAccessories);
		// 4. Select an product from Products table which has at least one variant
		productControl.selectAccessorybyName(dataProduct.get("name"));
		/*
		 * VP: Verify that the �Delete� link is not displayed in product detail page
		 */
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.DELETE));
		// 5. Select a variant from variant list
		productControl.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that the �Delete� link is not displayed in product variant detail page
		 */
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.DELETE_VARIANT));
	}
		
	/*
	 * Verify that DTS user could suspend accessory successfully when the �Publish and suspend accessories� privilege is enabled.
	 */
	@Test
	public void TCPDA_17(){
		productControl.addLog("ID : TCPDA_17 : Verify that DTS user could suspend accessory successfully when the �Publish and suspend accessories� privilege is enabled.");
		/*
			Pre-condition: The �Publish and suspend Products� privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to �Products� page
			4. Click �Add product� link
			5. Fill valid value into all required fields
			6. Upload all valid necessary files
			7. Click �Save� link
			VP: Verify that the new product detail page is displayed and the �Suspend� link is not displayed
			8. Pass through three steps of Approval Process
			9. Click �Publish� button
			VP: Verify that the product is published successfully and the �Suspend� link is displayed
			10. Click �Suspend� link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to �Products� page
		productControl.click(PageHome.linkAccessories);
		// 4. Click �Add product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 5. Fill valid value into all required fields
		// 6. Upload all valid necessary files
		// 7. Click �Save� link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * VP: Verify that the new product detail page is displayed and the �Suspend� link is not displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 8. Pass through three steps of Approval Process
		// Approve tuning
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		// Approve marketing
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		// 9. Click �Publish� button
		productControl.click(ProductDetailModel.PUBLISH);
		/*
		 *  VP: Verify that the product is published successfully and the �Suspend� link is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PUBLISH_STATUS), "PUBLISHED");
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.SUSPEND));
		// 10. Click �Suspend� link
		productControl.click(ProductDetailModel.SUSPEND);
		productControl.selectConfirmationDialogOption("Suspend");
		/*
		 * Verify that the product is suspended successfully
		 */
		productControl.selectFilterByName(ProductModel.PRODUCT_FILTER, "Suspended");
		Assert.assertTrue(productControl.checkAnAccessoryExistByName(data.get("name")));
		// Delete product
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that DTS user could suspend accessory variant successfully when the �Publish and suspend accessories� privilege is enabled.
	 */
	@Test
	public void TCPDA_18(){
		productControl.addLog("ID : TCPDA_18 : Verify that DTS user could suspend accessory variant successfully when the �Publish and suspend accessories� privilege is enabled.");
		/*
		  	Pre-condition: The �Publish and suspend Products� privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to �Products� page
			4. Select a published product from Products table
			5. Click �Add Variant� link in product detail page
			6. Fill valid value into all required fields
			7. Upload all valid necessary files
			8. Click �Save� link
			VP: Verify that the new product variant detail page is displayed and the �Suspend� link is not displayed
			9. Pass through three steps of Approval Process
			10. Click �Publish� button
			VP: Verify that the product variant is published successfully and the �Suspend� link is displayed
			11. Click �Suspend� link
		 */
		/*
		 * Pre-condition: Create a published product
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create published product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		/*
		 * *****************************************************************
		 */
		// 3. Navigate to �Products� page
		productControl.click(PageHome.linkAccessories);
		// 4. Select a published product from Products table
		productControl.selectAccessorybyName(dataProduct.get("name"));
		// 5. Click �Add Variant� link in product detail page
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 6. Fill valid value into all required fields
		// 7. Upload all valid necessary files
		// 8. Click �Save� link
		Hashtable<String,String> dataVariant = TestData.variantData(false, true, true);
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		/*
		 * VP: Verify that the new product variant detail page is displayed and the �Suspend� link is not displayed
		 */
		Assert.assertEquals(productControl.existsElement(VariantInfo.getElementsInfo()), true);
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.SUSPEND));
		// 9. Pass through three steps of Approval Process
		// Approve marketing
		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		productControl.click(VariantInfo.REQUEST_MARKETING_REVIEW);
		productControl.click(VariantInfo.APPROVE_MARKETING);
		// 10. Click �Publish� button
		productControl.click(VariantInfo.PUBLISH);
		/*
		 * VP: Verify that the product variant is published successfully and the �Suspend� link is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.PUBLISHED_STATUS), "Published");
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.SUSPEND));
		// 11. Click �Suspend� link
		productControl.click(VariantInfo.SUSPEND);
		productControl.selectConfirmationDialogOption("Suspend");
		/*
		 * Verify that the product variant is suspended successfully
		 */
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.selectAVariantbyName(dataVariant.get("name"));
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.PUBLISHED_STATUS), "Suspended");
		// Delete product
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that DTS user could not suspend accessory and accessory variant successfully when the �Publish and suspend accessories� privilege is disabled.
	 */
	@Test
	public void TCPDA_19(){
		productControl.addLog("ID : TCPDA_19 : Verify that DTS user could not suspend accessory and accessory variant successfully when the �Publish and suspend accessories� privilege is disabled.");
		/*
		  	Pre-condition: The �Publish and suspend Products� privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to �Products� page
			4. Select a published product from Products table which have at least one published product variant
			VP: Verify that the �Suspend� link is not displayed in product detail page
			5. Select a published product variant from product variants list
			VP: Verify that the �Suspend� link is not displayed in product variant detail page
		 */
		/*
		 * Pre-condition: The �Publish and suspend Products� privilege is
		 * disabled and a published product which has at least one published
		 * variant is created
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to user page
		productControl.click(PageHome.LINK_USERS);
		// Select a dts user from table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// Click Edit link
		productControl.click(UserMgmt.EDIT);
		// Disable �Publish and suspend Products� privilege
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Publish_and_suspend_products);
		productControl.click(AddUser.SAVE);
		// Navigate to Product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create published product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		// Click Add new variant link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// Create published variant
		Hashtable<String,String> dataVariant = TestData.variantData(true, true, true);
		productWf.createVariantPublish(AddEditProductModel.getVariantHash(), dataVariant);
		// Log out
		productControl.logout();
		/*
		 * ********************************************************************
		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 3. Navigate to �Products� page
		productControl.click(PageHome.linkAccessories);
		// 4. Select a published product from Products table which have at least one published product variant
		productControl.selectAccessorybyName(dataProduct.get("name"));
		/*
		 * VP: Verify that the �Suspend� link is not displayed in product detail page
		 */
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.SUSPEND));
		// 5. Select a published product variant from product variants list
		productControl.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that the �Suspend� link is not displayed in product variant detail page
		 */
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.SUSPEND));
	}
	
	/*
	 * Verify that the DTS user could request DTS tuning under Partner role when the �Request accessory tunings� privilege is enabled.
	 */
	@Test
	public void TCPDA_20(){
		productControl.addLog("ID : TCPDA_20 : Verify that the DTS user could request DTS tuning under Partner role when the �Request accessory tunings� privilege is enabled.");
		/*
		  	Pre-condition: The �Request product tunings� privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to �Products� page
			4. Click �Add product� link
			5. Fill valid value into all required fields
			6. Do not upload tuning file
			7. Click �Save� link
			VP: Verify that the product detail page is displayed, the tuning approval state is �Unsubmitted�, the action link �Request DTS tuning� displays and belongs to �Partner Actions� link
			8. Expand the �Partner Actions� link
			9. Click �Request DTS tuning� link
			10. Submit for the request tuning form successfully
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to �Products� page
		productControl.click(PageHome.linkAccessories);
		// 4. Click �Add product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 5. Fill valid value into all required fields
		// 6. Do not upload tuning file
		// 7. Click �Save� link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * VP: Verify that the product detail page is displayed, the tuning
		 * approval state is �Unsubmitted�, the action link �Request DTS tuning�
		 * displays and belongs to �Partner Actions� link
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "UNSUBMITTED");
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_TUNING), ProductDetailModel.ProductActions.REQUEST_DTS_TUNING.getName());
		// 8. Expand the �Partner Actions� link
		// 9. Click �Request DTS tuning� link
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		// 10. Submit for the request tuning form successfully
		productControl.click(TuningRequestForm.AGREE);
		productControl.click(TuningRequestForm.SUBMIT);
		/*
		 * Verify that the tuning approval state is changed to �DTS Request �,
		 * below the tuning status is �Upload Tuning� link and the action link
		 * which belongs to �Partner Actions� link is �Cancel Request�
		 */
		productControl.click(TuningRequestForm.PRODUCT_LINK);
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "DTS REQUEST PENDING");
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.UPLOAD_TUNING));
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.CANCEL_REQUEST_TUNING));
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the DTS user could not request DTS tuning under partner role when the �Request accessory tunings� privilege is disabled.
	 */
	@Test
	public void TCPDA_21(){
		productControl.addLog("ID : TCPDA_21 : Verify that the DTS user could request DTS tuning under Partner role when the �Request accessory tunings� privilege is enabled.");
		/*
		  	Pre-condition: The �Request product tunings� privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to �Products� page
			4. Click �Add product� link
			5. Fill valid value into all required fields
			6. Do not upload tuning file
			7. Click �Save� link
		 */
		/*
		 * Pre-condition: The �Request accessory tunings� privilege is disabled
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		productControl.click(PageHome.LINK_USERS);
		// Select a DTS user from table
		userControl.selectUserInfoByEmail(DTS_USER);
		// Click Edit link
		productControl.click(UserMgmt.EDIT);
		// Disable �Request accessory tunings� privilege
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Request_product_tunings);
		productControl.click(AddUser.SAVE);
		// Log out
		productControl.logout();
		/*
		 * ******************************************************
		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 3. Navigate to �Products� page
		productControl.click(PageHome.linkAccessories);
		// 4. Click �Add product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 5. Fill valid value into all required fields
		// 6. Do not upload tuning file
		// 7. Click �Save� link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * Verify that the tuning approval state is changed to �DTS Request �
		 * there is no more action below the status
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "UNSUBMITTED");
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.PARTNER_ACTIONS_STEP_1));
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the DTS user could approve the Partner tuning file when the �Approve accessory tunings� privilege is enabled.
	 */
	@Test
	public void TCPDA_22(){
		productControl.addLog("ID : TCPDA_22 : Verify that the DTS user could approve the Partner tuning file when the �Approve accessory tunings� privilege is enabled");
		/*
		 	Pre-condition: The �Approve product tunings� privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to �Products� page
			4. Click �Add product� link
			5. Fill valid value into all required fields
			6. Upload tuning file successfully
			7. Click �Save� link
			VP: Verify that the product detail page is displayed, the tuning status is �Unsubmitted� and the action link is �Request DTS Review�
			8. Click �Request DTS Review� link
			VP: Verify that the tuning status is now changed to � DTS Review� and below is �Cancel Request� link
			9. Log out DTS portal
			10. Log into DTS portal as a DTS user successfully
			11. Navigate to �Products� page
			12. Select above product from the Products table
			VP: Verify that the tuning approval status is � DTS REVIEW�, below the status is �Approve Tuning� and �Decline Tuning�, �Edit HeadPhone:X Rating� links and the �Cancel Request� link belongs the �Partner Actions� link
			13. Click �Approve Tuning� link
		 */
		// Log out
		productControl.logout();
		// 2. Log into DTS portal as a Partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Navigate to �Products� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 4. Click �Add product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 5. Fill valid value into all required fields
		// 6. Upload tuning file successfully
		// 7. Click �Save� link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * VP: Verify that the product detail page is displayed, the tuning
		 * status is �Unsubmitted� and the action link is �Request DTS Review�
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "UNSUBMITTED");
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_TUNING), "Request Tuning Review");
		// 8. Click �Request DTS Review� link
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		/*
		 * VP: Verify that the tuning status is now changed to � DTS Review� and
		 * below is �Cancel Request� link
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "PENDING DTS APPROVAL");
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_TUNING), "Cancel Request");
		// 9. Log out DTS portal
		productControl.logout();
		// 10. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 11. Navigate to �Products� page
		productControl.click(PageHome.linkAccessories);
		// 12. Select above product from the Products table
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the tuning approval status is � DTS REVIEW�, below
		 * the status is �Approve Tuning� and �Decline Tuning�, �Edit
		 * HeadPhone:X Rating� links and the �Cancel Request� link belongs the
		 * �Partner Actions� link
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "PENDING DTS APPROVAL");
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.APPROVE_TUNING), "Approve Tuning");
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.DECLINE_TUNING));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TUNING_RATING_WARNING_PARTNER));
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.CANCEL_REQUEST_TUNING));
		// Select tuning rating
		productControl.click(ProductDetailModel.EDIT_MODE);
		productControl.selectFilterByName(AddEditProductModel.TUNING_RATING, "A");
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// 13. Click �Approve Tuning� link
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		/*
		 * Verify that the tuning state is now changed to �Approved�, below is
		 * �Edit Headphone: X Certification�, �Revoke Tuning� links, the
		 * �Download Certification Badges� link belongs to �Partner Actions�
		 * link
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.APPROVED.getName());
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.EDIT_HEADPHONE_CERTIFICATION));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.REVOKE_TUNING));
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.DOWNLOAD_CERTIFICATE), ProductDetailModel.ProductActions.DOWNLOAD_CERT_BADGES.getName());
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the DTS user could decline the DTS tuning file when the �Approve accessory tunings� privilege is enabled
	 */
	@Test
	public void TCPDA_23(){
		productControl.addLog("ID : TCPDA_23 : Verify that the DTS user could decline the DTS tuning file when the �Approve accessory tunings� privilege is enabled");
		/*
		  	Pre-condition: The �Approve product tunings� privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to �Products� page
			4. Click �Add product� link
			5. Fill valid value into all required fields
			6. Upload tuning file successfully
			7. Click �Save� link
			VP: Verify that the product detail page is displayed, the tuning status is �Unsubmitted� and the action link is �Request DTS Review�
			8. Click �Request DTS Review� link
			VP: Verify that the tuning status is now changed to �PENDING DTS APPROVAL� and below is �Cancel Request� link
			9. Log out DTS portal
			10. Log into DTS portal as a DTS user successfully
			11. Navigate to �Products� page
			12. Select above product from the Products table
			VP: Verify that the tuning approval status is � DTS REVIEW�, below the status is �Approve Tuning� and �Decline Tuning�, �Edit HeadPhone:X Rating� links and the �Cancel Request� link belongs the �Partner Actions� link
			13. Click �Decline Tuning� link
		 */
		// Log out
		productControl.logout();
		// 2. Log into DTS portal as a Partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Navigate to �Products� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 4. Click �Add product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 5. Fill valid value into all required fields
		// 6. Upload tuning file successfully
		// 7. Click �Save� link
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * VP: Verify that the product detail page is displayed, the tuning
		 * status is �Unsubmitted� and the action link is �Request DTS Review�
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "UNSUBMITTED");
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_TUNING), "Request Tuning Review");
		// 8. Click �Request DTS Review� link
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		/*
		 * VP: Verify that the tuning status is now changed to �PENDING DTS APPROVAL� and
		 * below is �Cancel Request� link
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "PENDING DTS APPROVAL");
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_TUNING), "Cancel Request");
		// 9. Log out DTS portal
		productControl.logout();
		// 10. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 11. Navigate to �Products� page
		productControl.click(PageHome.linkAccessories);
		// 12. Select above product from the Products table
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the tuning approval status is �PENDING DTS APPROVAL�, below
		 * the status is �Approve Tuning� and �Decline Tuning�, �Edit
		 * HeadPhone:X Rating� links and the �Cancel Request� link belongs the
		 * �Partner Actions� link
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "PENDING DTS APPROVAL");
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.APPROVE_TUNING), "Approve Tuning");
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.DECLINE_TUNING));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TUNING_RATING_WARNING_PARTNER));
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.CANCEL_REQUEST_TUNING));
		// 13. Click �Decline Tuning� link
		productControl.click(ProductDetailModel.DECLINE_TUNING);
		/*
		 * Verify that the tuning state is now changed to �Declined�, and there
		 * is no more action below the status
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "DECLINED");
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.PARTNER_ACTIONS_STEP_1));
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	 /*
	  * Verify that the DTS user could not approve or decline the DTS tuning file when the �Approve accessory tunings� privilege is disabled.
	  */
	@Test
	public void TCPDA_24(){
		productControl.addLog("ID : TCPDA_24 : Verify that the DTS user could not approve or decline the DTS tuning file when the �Approve accessory tunings� privilege is disabled");
		/*
			Pre-condition: The �Approve product tunings� privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to �Products� page
			4. Click �Add product� link
			5. Fill valid value into all required fields
			6. Upload tuning file successfully
			7. Click �Save� link
			VP: Verify that the product detail page is displayed, the tuning status is �Unsubmitted� and the action link is �Request DTS Review�
			8. Click �Request DTS Review� link
			VP: Verify that the tuning status is now changed to �PENDING DTS APPROVAL� and below is �Cancel Request� link
			9. Log out DTS portal
			10. Log into DTS portal as a DTS user successfully
			11. Navigate to �Products� page
			12. Select above product from the Products table
		 */
		/*
		 * Pre-condition: The �Approve product tunings� privilege is disabled
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Users" page
		productControl.click(PageHome.LINK_USERS);
		// Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// Click �Edit� link
		productControl.click(UserInfo.EDIT);
		// Disable �Approve product tunings� privilege
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Approve_product_tuning);
		productControl.click(AddUser.SAVE);
		// Log out DTS portal
		productControl.logout();
		/*
		 * *****************************************************************
		 */
		//2. Log into DTS portal as a Partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		//3. Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		//4. Click �Add Accessory� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 5. Fill valid value into all required fields
		// 6. Upload tuning file successfully
		// 7. Click �Save� link
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * VP: Verify that the product detail page is displayed, the tuning
		 * status is �Unsubmitted� and the action link is �Request Tuning Review�
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "UNSUBMITTED");
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_TUNING), "Request Tuning Review");
		// 8. Click �Request DTS Review� link
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		/*
		 * VP: Verify that the tuning status is now changed to �PENDING DTS APPROVAL� and below is �Cancel Request� link
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "PENDING DTS APPROVAL");
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.CANCEL_REQUEST_TUNING), "Cancel Request");
		// 9. Log out DTS portal
		productControl.logout();
		// 10. Log into DTS portal as a DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 11. Navigate to �Products� page
		productControl.click(PageHome.linkAccessories);
		// 12. Select above product from the Products table
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * Verify that the tuning approval status is �PENDING DTS APPROVAL�, the �Approve
		 * Tuning� and �Decline Tuning� are not displayed but the �Cancel
		 * Request� link which belongs to the �Partner Actions� link is
		 * displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "PENDING DTS APPROVAL");
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.APPROVE_TUNING));
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.DECLINE_TUNING));
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.CANCEL_REQUEST_TUNING));
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the DTS user could approve the Marketing material when the �Approve marketing information� privilege is enabled
	 */
	@Test
	public void TCPDA_25(){
		productControl.addLog("ID : TCPDA_25 : Verify that the DTS user could approve the Marketing material when the �Approve marketing information� privilege is enabled");
		/*
		  	Pre-condition: The �Approve marketing information� privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to �Products� page
			4. Click �Add product� link
			5. Fill valid value into all required fields
			6. Upload tuning file and marketing materials successfully
			7. Click �Save� link
			8. Complete step 1 �Tuning Approval� of Publishing Process successfully.
			VP: Verify that after the tuning is approved, the status of step 2 �Marketing Approval� is changed to �UNSUBMITTED� and the �Partner Actions� link includes �Request Marketing Review� link
			9. Click �Request Marketing Review� link
			VP: Verify that the status of step 2 �Marketing Approval� is changed to � DTS REVIEW�, the �Edit� link is locked by the lock icon and there is a �Approve Marketing�  and a �Decline Marketing� link displayed.
			10. Click �Approve Marketing� link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to �Products� page
		productControl.click(PageHome.linkAccessories);
		// 4. Click �Add product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 5. Fill valid value into all required fields
		// 6. Upload tuning file and marketing materials successfully
		// 7. Click �Save� link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		data.put("add marketing", AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 8. Complete step 1 �Tuning Approval� of Publishing Process successfully
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		/*
		 * VP: Verify that after the tuning is approved, the status of step 2
		 * �Marketing Approval� is changed to �UNSUBMITTED� and the �Partner
		 * Actions� link includes �Request Marketing Review� link
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "UNSUBMITTED");
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_ACTION), "Request Marketing Review");
		// 9. Click �Request Marketing Review� link
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		/*
		 * VP: Verify that the status of step 2 �Marketing Approval� is changed
		 * to �PENDING DTS REVIEW�, the �Edit� link is locked by the lock icon and
		 * there is a �Approve Marketing� and a �Decline Marketing� link
		 * displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "PENDING DTS REVIEW");
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.EDIT_MODE));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.LOCK_ICON));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.APPROVE_MARKETING));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.DECLINE_MARKETING));
		// 10. Click �Approve Marketing� link
		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		/*
		 * Verify that the status of marketing approval is changed to �Approved�
		 * and the �Revoke Marketing Approval� is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "APPROVED");
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_ACTION), "Revoke Marketing Approval");
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the DTS user could approve the Marketing material when the �Decline marketing information� privilege is enabled.
	 */
	@Test
	public void TCPDA_26(){
		productControl.addLog("ID : TCPDA_26 : Verify that the DTS user could approve the Marketing material when the �Decline marketing information� privilege is enabled");
		/*
			Pre-condition: The �Approve marketing information� privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to �Products� page
			4. Click �Add product� link
			5. Fill valid value into all required fields
			6. Upload tuning file and marketing materials successfully
			7. Click �Save� link
			8. Complete step 1 �Tuning Approval� of Publishing Process successfully.
			VP: Verify that after the tuning is approved, the status of step 2 �Marketing Approval� is changed to �UNSUBMITTED� and the �Partner Actions� link includes �Request Marketing Review� link
			9. Click �Request Marketing Review� link
			VP: Verify that the status of step 2 �Marketing Approval� is changed to �PENDING DTS REVIEW�, the �Edit� link is locked by the lock icon and there is a �Approve Marketing�  and a �Decline Marketing� link displayed.
			10. Click �Decline Marketing� link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to �Products� page
		productControl.click(PageHome.linkAccessories);
		// 4. Click �Add product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 5. Fill valid value into all required fields.
		// 6. Upload tuning file and marketing materials successfully
		// 7. Click �Save� link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		data.put("add marketing", AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 8. Complete step 1 �Tuning Approval� of Publishing Process successfully
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		/*
		 * VP: Verify that after the tuning is approved, the status of step 2
		 * �Marketing Approval� is changed to �UNSUBMITTED� and the �Partner
		 * Actions� link includes �Request Marketing Review� link
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "UNSUBMITTED");
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_ACTION), "Request Marketing Review");
		// 9. Click �Request Marketing Review� link
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		/*
		 * VP: Verify that the status of step 2 �Marketing Approval� is changed
		 * to �PENDING DTS REVIEW�, the �Edit� link is locked by the lock icon
		 * and there is a �Approve Marketing� and a �Decline Marketing� link
		 * displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "PENDING DTS REVIEW");
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.EDIT_MODE));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.LOCK_ICON));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.APPROVE_MARKETING));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.DECLINE_MARKETING));
		// 10. Click �Decline Marketing� link
		productControl.click(ProductDetailModel.DECLINE_MARKETING);
		/*
		 * Verify that the status of marketing approval is changed to �Declined�
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "DECLINED");
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the DTS user could not approve or decline the Marketing material when the �Decline marketing information� privilege is disabled.
	 */
	@Test
	public void TCPDA_27(){
		productControl.addLog("ID : TCPDA_27 : Verify that the DTS user could not approve or decline the Marketing material when the �Decline marketing information� privilege is disabled.");
		/*
			Pre-condition: The �Approve marketing information� privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to �Products� page
			4. Click �Add product� link
			5. Fill valid value into all required fields
			6. Upload tuning file and marketing materials successfully
			7. Click �Save� link
			8. Complete step 1 �Tuning Approval� of Publishing Process successfully.
			VP: Verify that after the tuning is approved, the status of step 2 �Marketing Approval� is changed to �UNSUBMITTED� and the �Partner Actions� link includes �Request Marketing Review� link
			9. Click �Request Marketing Review� link
		 */
		
		/*
		 * Pre-condition: The �Approve marketing information� privilege is disabled
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Users" page
		productControl.click(PageHome.LINK_USERS);
		// Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// Click �Edit� link
		productControl.click(UserMgmt.EDIT);
		// Disable �Approve marketing information� privilege
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Approve_marketing_info);
		productControl.click(AddUser.SAVE);
		// Log out
		productControl.logout();
		/*
		 * *******************************************************************
		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 3. Navigate to �Accessories� page
		productControl.click(PageHome.linkAccessories);
		// 4. Click �Add Accessory� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 5. Fill valid value into all required fields.
		// 6. Upload tuning file and marketing materials successfully
		// 7. Click �Save� link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		data.put("add marketing", AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 8. Complete step 1 �Tuning Approval� of Publishing Process successfully.
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		/*
		 * VP: Verify that after the tuning is approved, the status of step 2 �Marketing Approval� is changed to �UNSUBMITTED� and the �Partner Actions� link includes �Request Marketing Review� link
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.APPROVED.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "UNSUBMITTED");
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_ACTION), "Request Marketing Review");
		// 9. Click �Request Marketing Review� link
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		/*
		 * Verify that the status of step 2 �Marketing Approval� is changed to � DTS REVIEW�, the �Edit� link is locked by the lock icon and there are no any action links displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "PENDING DTS REVIEW");
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.EDIT_MODE));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.LOCK_ICON));
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.APPROVE_MARKETING));
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.DECLINE_MARKETING));
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the �Upload Tuning� link does not display when the �Add and manage accessories� is disabled.
	 */
	@Test
	public void TCPDA_28(){
		productControl.addLog("ID : TCPDA_28 : Verify that the �Upload Tuning� link does not display when the �Add and manage accessories� is disabled");
		/*
			Pre-condition: The �Add and manage accessories� privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Navigate to �Accessories� page
			4. Click �Add Accessory� link
			5. Fill valid value into all required fields
			6. Do not upload tuning file
			7. Click �Save� link
			8. Request for the DTS Tuning file
			9. Log out DTS portal
			10. Log into DTS portal as a DTS user successfully
			11. Navigate to �Accessories� page
			12. Select above accessory from accessories table
		 */
		/*
		 * Pre-condition: The �Add and manage accessories� privilege is disabled
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Users" page
		productControl.click(PageHome.LINK_USERS);
		// Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// Click �Edit� link
		productControl.click(UserInfo.EDIT);
		// Disable �Add and manage accessories� privilege
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE,Privileges.Add_and_manage_products);
		productControl.click(AddUser.SAVE);
		// Log out
		productControl.logout();
		/*
		 * *****************************************************************
		 */
		// 2. Log into DTS portal as a partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 4. Click �Add Accessory� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 5. Fill valid value into all required fields.
		// 6. Do not upload tuning file
		// 7. Click �Save� link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 8. Request for the DTS Tuning file
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		// Tick to check "I understand the terms of this submission" checkbox
		productControl.click(TuningRequestForm.AGREE);
		// Click "Submit" link
		productControl.click(TuningRequestForm.SUBMIT);
		// 9. Log out DTS portal
		productControl.logout();
		// 10. Log into DTS portal as a DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 11. Navigate to �Accessories� page
		productControl.click(PageHome.linkAccessories);
		// 12. Select above accessory from accessories table
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * Verify that the �Upload Tuning� link does not display in step 1: Tuning Approval module
		 */
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.UPLOAD_TUNING));
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
	}	
		
	/*
	 * Verify that the DTS user can upload tuning file via �Upload Tuning� link when the �Add and manage accessories� is enabled
	 */
	@Test
	public void TCPDA_29(){
		productControl.addLog("ID : TCPDA_29 : Verify that the DTS user can upload tuning file via �Upload Tuning� link when the �Add and manage accessories� is enabled");
		/*
			Pre-condition: The �Add and manage accessories� privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Navigate to �Accessories� page
			4. Click �Add Accessory� link
			5. Fill valid value into all required fields
			6. Do not upload tuning file
			7. Click �Save� link
			8. Request for the DTS Tuning file
			9. Log out DTS portal
			10. Log into DTS portal as a DTS user successfully
			11. Navigate to �Accessories� page
			12. Select above accessory from accessories table
			VP: Verify that the �Upload Tuning� link is displayed in step 1: Tuning Approval module
			13. Click �Upload Tuning� link
			VP: Verify that the accessory edit page is displayed
			14. Upload a tuning file successfully
			15. Click �Save� link
		 */
		// Log out
		productControl.logout();
		// 2. Log into DTS portal as a partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 4. Click �Add Accessory� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 5. Fill valid value into all required fields.
		// 6. Do not upload tuning file
		// 7. Click �Save� link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 8. Request for the DTS Tuning file
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		// Tick to check "I understand the terms of this submission" checkbox
		productControl.click(TuningRequestForm.AGREE);
		// Click "Submit" link
		productControl.click(TuningRequestForm.SUBMIT);
		// 9. Log out DTS portal
		productControl.logout();
		// 10. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 11. Navigate to �Accessories� page
		productControl.click(PageHome.linkAccessories);
		// 12. Select above accessory from accessories table
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the �Upload Tuning� link is displayed in step 1: Tuning Approval module
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.UPLOAD_TUNING));
		// 13. Click �Upload Tuning� link
		productControl.click(ProductDetailModel.UPLOAD_TUNING);
		/*
		 * VP: Verify that the accessory edit page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getProductElementFieldIds()), true);
		// 14. Upload a tuning file successfully
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		// 15. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * Verify that the DTS tuning file is uploaded and saved successfully
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.UPLOADED_TUNING));
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
		
}
