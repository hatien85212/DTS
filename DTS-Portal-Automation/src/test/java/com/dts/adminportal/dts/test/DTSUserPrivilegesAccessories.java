package com.dts.adminportal.dts.test;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AccessoryInfo;
import dts.com.adminportal.model.AccessoryMain;
import dts.com.adminportal.model.AddAccessory;
import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.AddVariant;
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.PartnerAccessoryInfo;
import dts.com.adminportal.model.PartnerAddAccessory;
import dts.com.adminportal.model.PartnerVariantInfo;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.TuningRequestForm;
import dts.com.adminportal.model.UserEdit;
import dts.com.adminportal.model.UserInfo;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.VariantInfo;
import dts.com.adminportal.model.Xpath;

public class DTSUserPrivilegesAccessories extends CreatePage{
	private HomeController home;
	
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}
	
	@BeforeMethod
	public void loginBeforeTest() {
		home.login(superUsername, superUserpassword);
	}
		
	/*
	 * Verify that the DTS user can add accessories successfully when the  "Add and manage accessories” privilege is enabled
	 */
	@Test
	public void TCPDA_01(){
		result.addLog("ID : TCPDA_01 : Verify that the DTS user can add accessories successfully when the  'Add and manage accessories' privilege is enabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the “Add and manage accessories” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			9. Navigate to “Home” page
			10. Navigate to “Accessories” page
			11. Click on “Add Accessory” link
			12. Fill valid value into all required fields
			13. Click “Save” link
		*/
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Add and manage accessories” privilege
		home.enableAllPrivilege();
		home.click(AddUser.SAVE);
		//7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, password);
		// 9. Navigate to “Home” page
		// 10. Navigate to “Accessories” page
		home.click(Xpath.linkAccessories);
		// 11. Click on “Add Accessory” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 12. Fill valid value into all required fields
		// 13. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * Verify that the new product is displayed in products table
		 */
		home.click(Xpath.linkAccessories);
		Assert.assertTrue(home.checkAnAccessoryExistByName(data.get("name")));
		// Delete the added accessory
		home.selectAnaccessorybyName(data.get("name"));
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 Verify that the DTS user can edit accessories successfully when the "Add and manage accessories” privilege is enabled.
	 */
	@Test
	public void TCPDA_02(){
		result.addLog("ID : TCPDA_02 : Verify that the DTS user can edit accessories successfully when the  'Add and manage accessories' privilege is enabled.");
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
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * **********************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Add and manage accessories” privilege
		home.enablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE,Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner admin
		home.login(dtsUser, dtsPass);
		// 9. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 10. Select an accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that 040P Accessory Detail page is displayed with "Edit" link
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), data.get("name"));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.EDIT_MODE));
		// 11. Click "Edit" link
		home.click(AccessoryInfo.EDIT_MODE);
		// 12. Verify that 051P Accessory Edit Page is displayed
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(), "Pass");
		// 13. Change any of accessory's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		home.editData(AddAccessory.NAME, newName);
		// 14. Click "Save" link
		home.click(AddAccessory.SAVE);
		/*
		 * Verify that new product's information is displayed correctly
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), newName);
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 Verify that the DTS user can add accessories variant successfully when the  "Add and manage accessories” privilege is enabled
	 */
	@Test
	public void TCPDA_03(){
		result.addLog("ID : TCPDA_03 : Verify that the DTS user can add accessories variant successfully when the  'Add and manage accessories' privilege is enabled");
		
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the “Add and manage products” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			9. Navigate to “products” page
			10. Select a published product from products table
			VP. Verify that the “Add product Variant” link displayed in product detail page
			11. Click on “Add product Variant” link in product detail page
			VP. Verify that the adding new product variant page is displayed
			12. Fill valid value into all required fields
			13. Click “Save” link
		 */
		
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Add and manage accessories” privilege
		home.enablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE,Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner admin
		home.login(dtsUser, dtsPass);
		// 9. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 10. Select a published accessory from accessories table
		home.selectAnAccessory();
		/*
		 * VP. Verify that the “Add product Variant” link displayed in product detail page
		 */
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.ADD_VARIANT));
		// 11. Click on “Add Accessory Variant” link in accessory detail page
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the adding new product variant page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 12. Fill valid value into all required fields
		// 13. Click “Save” link
		Hashtable<String,String> data = TestData.variantDraft();
		home.addVariant(AddVariant.getHash(), data);
		/*
		 * Verify that New product variant could be save successfully
		 */
		Assert.assertEquals(home.existsElement(VariantInfo.getElementsInfo()).getResult(), "Pass");
	}
	
	/*
	 * Verify that the DTS user can edit accessories variant successfully when the  "Add and manage accessories” privilege is enabled
	 */
	@Test
	public void TCPDA_04(){
		result.addLog("ID : TCPDA_04 : Verify that the DTS user can edit accessories variant successfully when the  'Add and manage accessories' privilege is enabled");
		
		/*
		  	1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the “Add and manage products” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			9. Navigate to “products” page
			10. Select an product from products table which has at least one variant
			11. Select an product variant from variant list in product detail page
			VP: Verify that the product variant detail page is displayed and the “Edit” link is displayed too
			12. Click “Edit” link
			13. Change product variant's information
			14. Click “Save” link
		 */
		
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Add and manage accessories” privilege
		home.enablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE,Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner admin
		home.login(dtsUser, dtsPass);
		// 9. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 10. Select an product from products table which has at least one variant
		home.selectAnAccessory();
		// 11. Select an product variant from variant list in product detail page
		Hashtable<String,String> data = TestData.variantDraft();
		home.click(AccessoryInfo.ADD_VARIANT);
		home.addVariant(AddVariant.getHash(), data);
		/*
		 * VP: Verify that the product variant detail page is displayed and the “Edit” link is displayed too
		 */
		Assert.assertEquals(home.existsElement(VariantInfo.getElementsInfo()).getResult(), "Pass");
		Assert.assertTrue(home.isElementPresent(VariantInfo.EDIT_VARIANT));
		// 12. Click “Edit” link
		home.click(VariantInfo.EDIT_VARIANT);
		// 13. Change product variant's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		home.editData(AddVariant.NAME, newName);
		// 14. Click “Save” link
		home.click(AddVariant.SAVE);
		/*
		 * Verify that The product variant's information is changed successfully
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.DISPLAYNAME_DEFAULT), newName);
	}
	
	/*
	 * Verify that the DTS user can view accessory's published version info when the "Add and manage accessories” privilege is either enabled or disabled.
	 */
	@Test
	public void TCPDA_05(){
		result.addLog("ID : TCPDA_05 : Verify that the DTS user can view accessory's published version info when the 'Add and manage accessories' privilege is either enabled or disabled.");
		
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the “Add and manage products” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			9. Navigate to “products” page
			10. Select a product that has at least one published version from products table
			VP: The “View Published Version” link is displayed.
			11. Log out DTS portal
			12. Perform from step 2 to 5 again
			13. Disabled the “Add and manage products” privilege
			14. Perform from step 7 to 10 again
		 */
		/*
		 * Pre-Condition: Create product has at least one published version
		 */
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create publish accessory
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), data);
		// Create new version
		home.click(AccessoryInfo.UPDATE_PRODUCT_INFO);
		home.switchWindowClickOption("Ok");
		home.click(AddAccessory.SAVE);
		/*
		 * ******************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Add and manage accessories” privilege
		home.enablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE,Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner admin
		home.login(dtsUser, dtsPass);
		// 9. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 10. Select a product that has at least one published version from products table
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 *  VP: The “View Published Version” link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.VIEW_PUBLISHED_MODEL));
		// 11. Log out DTS portal
		home.logout();
		// 12. Perform from step 2 to 5 again
		home.login(superUsername, superUserpassword);
		//Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
	    // Select a user from users table
		home.dtsSelectUserByEmail(dtsUser);
		// Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 13. Disabled the “Add and manage products” privilege
		home.disablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// 14. Perform from step 7 to 10 again
		// Log out DTS portal
		home.logout();
		// Log into DTS portal as above partner admin
		home.login(dtsUser, dtsPass);
		// Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// Select a published accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 *  VP: The “View Published Version” link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.VIEW_PUBLISHED_MODEL));
	}
	
	/*
	 Verify that the DTS user can view accessory variant's published version info when the "Add and manage accessories” privilege is either enabled or disabled.
	 */
	@Test
	public void TCPDA_06(){
		result.addLog("ID : TCPDA_06 : Verify that the DTS user can view accessory variant's published version info when the 'Add and manage accessories' privilege is either enabled or disabled.");
		
		/*
		  	1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the “Add and manage accessories” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS\ user successfully
			9. Navigate to “Accessories” page
			10. Select a published accessory from accessories table which have at least one accessory variant is also published
			11. Select the published accessory variant from variant list in accessory detail page
			VP: The “View Published Version” link is displayed in accessory variant detail page
			12. Log out DTS portal
			13. Perform from step 2 to 5 again
			14. Disabled the “Add and manage accessories” privilege
			15. Perform from step 7 to 11 again
		 */
		/*
		 * Pre-condition: Create published product which has at least one published version variant
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create published product
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), data);
		// Click Add variant link
		home.click(AccessoryInfo.ADD_VARIANT);
		// Create publish variant
		Hashtable<String,String> dataVariant = TestData.variantPublish();
		home.createVariantPublish(AddVariant.getHash(), dataVariant);
		// Create new version
		home.click(VariantInfo.CREATE_NEW_VERSION);
		home.switchWindowClickOption("Ok");
		home.click(AddVariant.SAVE);
		/*
		 * *********************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Enable "Add and manage accessories" site privilege for above user
		home.enablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner admin
		home.login(dtsUser, dtsPass);
		// 9. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 10. Select a published accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		// 11. Select the published accessory variant from variant list in accessory detail page
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: The “View Published Version” link is displayed in accessory variant detail page
		 */
		Assert.assertTrue(home.isElementPresent(VariantInfo.VIEW_PUBLISHED_VERSION));
		// 12. Log out DTS portal
		home.logout();
		// 13. Perform from step 2 to 5 again
		home.login(superUsername, superUserpassword);
		// Navigate to user page
		home.click(Xpath.LINK_USERS);
		// Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 14. Disabled the “Add and manage accessories” privilege
		home.disablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// 15. Perform from step 7 to 11 again
		home.logout();
		// Log into DTS portal as above partner admin
		home.login(dtsUser, dtsPass);
		// Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// Select a published accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		// Select the published accessory variant from variant list in accessory detail page
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: The “View Published Version” link is displayed in accessory variant detail page
		 */
		Assert.assertTrue(home.isElementPresent(VariantInfo.VIEW_PUBLISHED_VERSION));
		// Delete product
		home.click(VariantInfo.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user can only create a new version but not to edit a published accessory when the "Add and manage accessories” privilege is enabled
	 */
	@Test
	public void TCPDA_07(){
		result.addLog("ID : TCPDA_07 : Verify that the DTS user can only create a new version but not to edit a published accessory when the 'Add and manage accessories' privilege is enabled");
		/*
		 	Pre-condition: The  "Add and manage products” privilege is enabled.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “products” page
			4. Click “Add product” link
			5. Fill valid value into all required fields
			6. Pass through two steps of Publication Process
			7. Click “Publish” button in step 3 of Publication Process
		 */
		// 3. Navigate to “products” page
		home.click(Xpath.linkAccessories);
		// 4. Click “Add product” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields
		// 6. Pass through two steps of Publication Process
		// 7. Click “Publish” button in step 3 of Publication Process
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), data);
		/*
		 * Verify that The Edit Link disappears and the “Create New Version” is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.UPDATE_PRODUCT_INFO), "Update Product Info");
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user can only create a new version but not to edit a published accessory variant when the "Add and manage accessories” privilege is enabled
	 */
	@Test
	public void TCPDA_08(){
		result.addLog("ID : TCPDA_08 : Verify that the DTS user can only create a new version but not to edit a published accessory variant when the 'Add and manage accessories' privilege is enabled");
		/*
		  	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “Accessories” page
			4. Select a published accessory from the accessories table
			5. Click “Add new variant” link
			6. Fill valid value into all required fields
			7. Pass through two steps of Publication Process
			8. Click “Publish” button in step 3 of Publication Process
		 */
		// 3. Navigate to “Accessories” page
		home.click(Xpath.linkAccessories);
		// 4. Select a published accessory from the accessories table
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create published product
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), data);
		// 5. Click “Add new variant” link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 6. Fill valid value into all required fields
		// 7. Pass through two steps of Publication Process
		// 8. Click “Publish” button in step 3 of Publication Process
		Hashtable<String,String> dataVariant = TestData.variantPublish();
		home.createVariantPublish(AddVariant.getHash(), dataVariant);
		/*
		 * Verify that The Edit Link disappears and the “Create New Version” is displayed
		 */
		Assert.assertFalse(home.isElementPresent(VariantInfo.EDIT_VARIANT));
		Assert.assertTrue(home.isElementPresent(VariantInfo.CREATE_NEW_VERSION));
		// Delete product
		home.click(VariantInfo.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 Verify that the DTS user cannot add new or edit accessories or accessories variant when the "Add and manage accessories” privilege is disabled.
	 */
	@Test
	public void TCPDA_09(){
		result.addLog("ID : TCPDA_09 : Verify that the DTS user cannot add new or edit accessories or accessories variant when the 'Add and manage accessories' privilege is disabled");
		
		/*
		 * 	Pre-condition: The  "Add and manage accessories” privilege is disabled.
		  	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “Accessories” page
			4. Select a non published accessory from the accessories table which has at least one accessory variant
			VP: Verify that there is no edit link displayed
			5. Select an accessory variant from the variant list
			VP: Verify that there is no edit link displayed for an accessory variant
		 */
		/*
		 * Pre-Conditions : The "Add and manage accessories” privilege is disabled and product which has at least one variant is created
		 */
		// Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// Select a user from users table
		home.dtsSelectUserByEmail(dtsUser);
		// Click "Edit" link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage accessories" site privilege for above user
		home.disablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Click Add variant link
		home.click(AccessoryInfo.ADD_VARIANT);
		// Create variant
		Hashtable<String,String> dataVariant = TestData.variantDraft();
		home.addVariant(AddVariant.getHash(), dataVariant);
		// Log out
		home.logout();
		/*
		 * *********************************************************************
		 */
		// 2. Log into DTS portal as a DTS user successfully
		home.login(dtsUser, dtsPass);
		// 3. Navigate to “Accessories” page
		home.click(Xpath.linkAccessories);
		// 4. Select a non published accessory from the accessories table which has at least one accessory variant
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that there is no edit link displayed
		 */
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.EDIT_MODE));
		// 5. Select an accessory variant from the variant list
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that there is no edit link displayed for an accessory variant
		 */
		Assert.assertFalse(home.isElementPresent(VariantInfo.EDIT_VARIANT));
		// Delete product
		home.click(VariantInfo.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 Verify that the DTS user cannot create new version for an accessory or accessory variant when the "Add and manage accessories” privilege is disabled
	 */
	@Test
	public void TCPDA_10(){
		result.addLog("ID : TCPDA_10 : Verify that the DTS user cannot create new version for an accessory or accessory variant when the 'Add and manage accessories' privilege is disabled");
		/*
			Pre-condition: The  "Add and manage products” privilege is disabled.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “products” page
			4. Select a published product from the products table which has at least one published product variant
			VP: Verify that there is no “Create New Version” link displayed for a published product
			5. Select a published product variant from the variant list
			VP: Verify that there is no “Create New Version” link displayed for a published product variant
		 */
		/*
		 * Pre-Conditions : The "Add and manage accessories” privilege is disabled and published product which has at least one published variant is created
		 */
		// Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// Select a user from users table
		home.dtsSelectUserByEmail(dtsUser);
		// Click "Edit" link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage accessories" site privilege for above user
		home.enableAllPrivilege();
		home.disablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create published product
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), data);
		// Click Add variant link
		home.click(AccessoryInfo.ADD_VARIANT);
		// Create published variant
		Hashtable<String,String> dataVariant = TestData.variantPublish();
		home.createVariantPublish(AddVariant.getHash(), dataVariant);
		// Log out
		home.logout();
		/*
		 * ******************************************************************
		 */
		// 2. Log into DTS portal as a DTS user successfully
		home.login(dtsUser, dtsPass);
		// 3. Navigate to “products” page
		home.click(Xpath.linkAccessories);
		// 4. Select a published product from the products table which has at least one published product variant
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that there is no “Create New Version” link displayed for a published product
		 */
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.UPDATE_PRODUCT_INFO));
		// 5. Select a published product variant from the variant list
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that there is no “Create New Version” link displayed for a published product variant
		 */
		Assert.assertFalse(home.isElementPresent(VariantInfo.CREATE_NEW_VERSION));
	}
	
	/*
	 * Verify that the DTS user could publish accessory successfully when the “Publish and suspend accessories” privilege is enabled
	 */
	@Test
	public void TCPDA_11(){
		result.addLog("ID : TCPDA_11 : Verify that the DTS user could publish accessory successfully when the “Publish and suspend accessories” privilege is enabled");
		/*
		  	Pre-condition: The “Publish and suspend accessories” privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “Accessories” page
			4. Click “Add Accessory” link
			5. Fill valid value into all required fields
			6. Upload all valid necessary files
			7. Click “Save” link
			8. Pass through two steps of Approval Process
			VP The “Published” button in step 3 “Publishing” of “Approval Process” is enabled 
			9. Click “Publish” button
		 */
		// 3. Navigate to “Accessories” page
		home.click(Xpath.linkAccessories);
		// 4. Click “Add Accessory” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields
		// 6. Upload all valid necessary files
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// 8. Pass through two steps of Approval Process
		// Approve tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Approve marketing
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		home.click(AccessoryInfo.APPROVE_MARKETING);
		/*
		 * VP: The “Published” button in step 3 “Publishing” of “Approval Process” is enabled 
		 */
		Assert.assertEquals(home.getAtribute(AccessoryInfo.PUBLISH, "class"), "button button-warning");
		// 9. Click “Publish” button
		home.click(AccessoryInfo.PUBLISH);
		/*
		 * Verify that New product could be upload successfully
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.PUBLISH_STATUS), "PUBLISHED");
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that DTS user could not publish accessory when the “Publish and suspend accessories” privilege is disabled
	 */
	@Test
	public void TCPDA_12(){
		result.addLog("ID : TCPDA_12 : Verify that DTS user could not publish accessory when the “Publish and suspend accessories” privilege is disabled");
		/*
		  	Pre-condition: The “Publish and suspend accessories” privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “Accessories” page
			4. Click “Add Accessory” link
			5. Fill valid value into all required fields
			6. Upload all valid necessary files
			7. Click “Save” link
			8. Pass through two steps of Approval Process
		 */
		/*
		 * Pre-Conditions : The "Publish and suspend accessories” privilege is disabled
		 */
		// Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// Select a user from users table
		home.dtsSelectUserByEmail(dtsUser);
		// Click "Edit" link
		home.click(UserMgmt.EDIT);
		// Disable "Publish and suspend accessories" site privilege for above user
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Publish_and_suspend_products);
		home.click(AddUser.SAVE);
		// Log out
		home.logout();
		/*
		 * ***************************************************************
		 */
		// 2. Log into DTS portal as a DTS user successfully
		home.login(dtsUser, dtsPass);
		// 3. Navigate to “Accessories” page
		home.click(Xpath.linkAccessories);
		// 4. Click “Add Accessory” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields
		// 6. Upload all valid necessary files
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// 8. Pass through two steps of Approval Process
		// Approve tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Log out
		home.logout();
		// Log in DTS portal as partner user
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select accessory created above
		home.selectAnaccessorybyName(data.get("name"));
		// Click Request marketing review link
		home.click(PartnerAccessoryInfo.REQUEST_MARKETING_REVIEW);
		// Log out
		home.logout();
		// Log in DTS portal as DTS user
		home.login(dtsUser, dtsPass);
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Select created product above
		home.selectAnaccessorybyName(data.get("name"));
		// Approve marketing
		home.click(AccessoryInfo.APPROVE_MARKETING);
		/*
		 * Verify that The “Published” button in step 3 “Publishing” of “Approval Process” is disabled
		 */
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.PUBLISH));
	}
	
	/*
	 * Verify that DTS user could publish product variant successfully when the “Publish and suspend products” privilege is enabled
	 */
	@Test
	public void TCPDA_13(){
		result.addLog("ID : TCPDA_13 : Verify that DTS user could publish product variant successfully when the “Publish and suspend products” privilege is enabled");
		/*
		  	Pre-condition: The “Publish and suspend products” privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “products” page
			4. Select a published product from products table
			5. Click “Add Variant” link
			6. Fill valid value into all required fields
			7. Upload all valid necessary files
			8. Click “Save” link
			9. Pass through two steps of Approval Process of product variant
			VP The “Published” button in step 3 “Publishing” of “Approval Process” is enabled 
			10 Click “Publish” button
		 */
		/*
		 * Pre-Conditions : The "Publish and suspend accessories” privilege is enable and published product is created
		 */
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create publish product
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), data);
		/*
		 * ***************************************************************
		 */
		// 5. Click “Add Variant” link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 6. Fill valid value into all required fields
		// 7. Upload all valid necessary files
		// 8. Click “Save” link
		Hashtable<String,String> dataVariant = TestData.variantPublishUseParent();
		home.addVariant(AddVariant.getHash(), dataVariant);
		// 9. Pass through two steps of Approval Process of product variant
		// Approve marketing
		home.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		home.click(VariantInfo.REQUEST_MARKETING_REVIEW);
		home.click(VariantInfo.APPROVE_MARKETING);
		/*
		 * VP The “Published” button in step 3 “Publishing” of “Approval Process” is enabled 
		 */
		Assert.assertEquals(home.getAtribute(VariantInfo.PUBLISH, "class"), "button button-warning");
		// 10 Click “Publish” button
		home.click(VariantInfo.PUBLISH);
		/*
		 * Verify that New product variant could be published  successfully
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.PUBLISHED_STATUS), "Published");
		// Delete product
		home.click(VariantInfo.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that DTS user could not publish accessory when the “Publish and suspend accessories” privilege is disabled
	 */
	@Test
	public void TCPDA_14(){
		result.addLog("ID : TCPDA_14 : Verify that DTS user could not publish accessory when the “Publish and suspend accessories” privilege is disabled");
		/*
		  	Pre-condition: The “Publish and suspend accessories” privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “Accessories” page
			4. Select a published accessory from accessories table
			5. Click “Add Variant” link
			6. Fill valid value into all required fields
			7. Upload all valid necessary files
			8. Click “Save” link
			9. Pass through two steps of Approval Process of accessory variant
		 */
		/*
		 * Pre-Conditions : The "Publish and suspend accessories” privilege is disabled and one published product is created
		 */
		// Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// Select a user from users table
		home.dtsSelectUserByEmail(dtsUser);
		// Click "Edit" link
		home.click(UserMgmt.EDIT);
		// Disable "Publish and suspend accessories" site privilege for above user
		home.enableAllPrivilege();
		home.disablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.Publish_and_suspend_products);
		home.click(AddUser.SAVE);
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create published product
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), data);
		// Log out
		home.logout();
		/*
		 * ***************************************************************
		 */
		// 2. Log into DTS portal as a DTS user successfully
		home.login(dtsUser, dtsPass);
		// 3. Navigate to “Accessories” page
		home.click(Xpath.linkAccessories);
		// 4. Select a published accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		// 5. Click “Add Variant” link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 6. Fill valid value into all required fields
		// 7. Upload all valid necessary files
		// 8. Click “Save” link
		Hashtable<String,String> dataVariant = TestData.variantPublishUseParent();
		home.addVariant(AddVariant.getHash(), dataVariant);
		// 9. Pass through two steps of Approval Process of accessory variant
		home.logout();
		// Log in DTS user as partner user
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select product created above
		home.selectAnaccessorybyName(data.get("name"));
		// Select variant created above
		home.selectAVariantbyName(dataVariant.get("name"));
		// Click Request marketing review link
		home.click(PartnerVariantInfo.REQUEST_MARKETING_REVIEW);
		// Log out
		home.logout();
		// Log in DTS portal as DTS user above
		home.login(dtsUser, dtsPass);
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Select product created above
		home.selectAnaccessorybyName(data.get("name"));
		// Select variant created above
		home.selectAVariantbyName(dataVariant.get("name"));
		// Approve marketing
		home.click(VariantInfo.APPROVE_MARKETING);
		/*
		 * Verify that The “Published” button in step 3 “Publishing” of “Approval Process” is disabled 
		 */
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.PUBLISH));
	}
	
	/*
	 Verify that DTS user could delete accessory and its variant successfully when the “Publish and suspend accessories” privilege is enabled.
	 */
	@Test
	public void TCPDA_15(){
		result.addLog("ID : TCPDA_15 : Verify that DTS user could delete accessory and its variant successfully when the “Publish and suspend accessories” privilege is enabled");
		
		/*
		  	Pre-condition: The “Publish and suspend accessories” privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “Accessories” page
			4. Select an accessory from accessories table which has at least one variant
			5. Select a variant from variant list
			6. Click “Delete” link in accessory variant detail page
			VP: Verify that the accessory variant is deleted successfully and the accessory detail page is displayed
			7. Click “Delete” link in accessory detail page
			VP: Verify that the accessory is deleted successfully.
		 */
		/*
		 * Pre-condition: Product which has at least one variant is created
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Click Add new variant link
		home.click(AccessoryInfo.ADD_VARIANT);
		// Create variant
		Hashtable<String,String> dataVariant = TestData.variantDraft();
		home.addVariant(AddVariant.getHash(), dataVariant);
		/*
		 * ******************************************************************
		 */
		// 3. Navigate to “Accessories” page
		home.click(Xpath.linkAccessories);
		// 4. Select an accessory from accessories table which has at least one variant
		home.selectAnaccessorybyName(data.get("name"));
		// 5. Select a variant from variant list
		home.selectAVariantbyName(dataVariant.get("name"));
		// 6. Click “Delete” link in accessory variant detail page
		home.doDelete(VariantInfo.DELETE_VARIANT);
		/*
		 * VP: Verify that the accessory variant is deleted successfully and the accessory detail page is displayed
		 */
		// 7. Click “Delete” link in accessory detail page
		home.doDelete(AccessoryInfo.DELETE);
		/*
		 * VP: Verify that the accessory is deleted successfully
		 */
		Assert.assertFalse(home.checkAnAccessoryExistByName(data.get("name")));
	}
	
	/*
	 * Verify that DTS user could not delete accessory and its variant successfully when the “Publish and suspend accessories” privilege is disabled.
	 */
	@Test
	public void TCPDA_16(){
		result.addLog("ID : TCPDA_16 : Verify that DTS user could not delete accessory and its variant successfully when the “Publish and suspend accessories” privilege is disabled");
		/*
		  	Pre-condition: The “Publish and suspend Products” privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “Products” page
			4. Select an product from Products table which has at least one variant
			VP: Verify that the “Delete” link is not displayed in product detail page
			5. Select a variant from variant list
			VP: Verify that the “Delete” link is not displayed in product variant detail page
		 */
		/*
		 * Pre-condition: The “Publish and suspend Products” privilege is
		 * disabled and a product which has at least one variant is created
		 */
		// Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// Click “Edit” link
		home.click(UserInfo.EDIT);
		// Disable "Publish and suspend Products" privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE,Privileges.Publish_and_suspend_products);
		home.click(AddUser.SAVE);
		// Navigate to “Product” page
		home.click(Xpath.linkAccessories);
		// Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> dataProduct = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), dataProduct);
		// Click Add new variant link
		home.click(AccessoryInfo.ADD_VARIANT);
		// Create new variant
		Hashtable<String,String> dataVariant = TestData.variantDraft();
		home.addVariant(AddVariant.getHash(), dataVariant);
		// Log out
		home.logout();
		/*
		 * ****************************************************************
		 */
		// 2. Log into DTS portal as a DTS user successfully
		home.login(dtsUser, dtsPass);
		// 3. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 4. Select an product from Products table which has at least one variant
		home.selectAnaccessorybyName(dataProduct.get("name"));
		/*
		 * VP: Verify that the “Delete” link is not displayed in product detail page
		 */
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.DELETE));
		// 5. Select a variant from variant list
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that the “Delete” link is not displayed in product variant detail page
		 */
		Assert.assertFalse(home.isElementPresent(VariantInfo.DELETE_VARIANT));
	}
		
	/*
	 * Verify that DTS user could suspend accessory successfully when the “Publish and suspend accessories” privilege is enabled.
	 */
	@Test
	public void TCPDA_17(){
		result.addLog("ID : TCPDA_17 : Verify that DTS user could suspend accessory successfully when the “Publish and suspend accessories” privilege is enabled.");
		/*
			Pre-condition: The “Publish and suspend Products” privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “Products” page
			4. Click “Add product” link
			5. Fill valid value into all required fields
			6. Upload all valid necessary files
			7. Click “Save” link
			VP: Verify that the new product detail page is displayed and the “Suspend” link is not displayed
			8. Pass through three steps of Approval Process
			9. Click “Publish” button
			VP: Verify that the product is published successfully and the “Suspend” link is displayed
			10. Click “Suspend” link
		 */
		// 3. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 4. Click “Add product” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields
		// 6. Upload all valid necessary files
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: Verify that the new product detail page is displayed and the “Suspend” link is not displayed
		 */
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 8. Pass through three steps of Approval Process
		// Approve tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Approve marketing
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		home.click(AccessoryInfo.APPROVE_MARKETING);
		// 9. Click “Publish” button
		home.click(AccessoryInfo.PUBLISH);
		/*
		 *  VP: Verify that the product is published successfully and the “Suspend” link is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.PUBLISH_STATUS), "PUBLISHED");
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.SUSPEND));
		// 10. Click “Suspend” link
		home.click(AccessoryInfo.SUSPEND);
		home.selectConfirmationDialogOption("Suspend");
		/*
		 * Verify that the product is suspended successfully
		 */
		home.selectFilterByName(AccessoryMain.PRODUCT_FILTER, "Suspended");
		Assert.assertTrue(home.checkAnAccessoryExistByName(data.get("name")));
		// Delete product
		home.selectAnaccessorybyName(data.get("name"));
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that DTS user could suspend accessory variant successfully when the “Publish and suspend accessories” privilege is enabled.
	 */
	@Test
	public void TCPDA_18(){
		result.addLog("ID : TCPDA_18 : Verify that DTS user could suspend accessory variant successfully when the “Publish and suspend accessories” privilege is enabled.");
		/*
		  	Pre-condition: The “Publish and suspend Products” privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “Products” page
			4. Select a published product from Products table
			5. Click “Add Variant” link in product detail page
			6. Fill valid value into all required fields
			7. Upload all valid necessary files
			8. Click “Save” link
			VP: Verify that the new product variant detail page is displayed and the “Suspend” link is not displayed
			9. Pass through three steps of Approval Process
			10. Click “Publish” button
			VP: Verify that the product variant is published successfully and the “Suspend” link is displayed
			11. Click “Suspend” link
		 */
		/*
		 * Pre-condition: Create a published product
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create published product
		Hashtable<String,String> dataProduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct);
		/*
		 * *****************************************************************
		 */
		// 3. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 4. Select a published product from Products table
		home.selectAnaccessorybyName(dataProduct.get("name"));
		// 5. Click “Add Variant” link in product detail page
		home.click(AccessoryInfo.ADD_VARIANT);
		// 6. Fill valid value into all required fields
		// 7. Upload all valid necessary files
		// 8. Click “Save” link
		Hashtable<String,String> dataVariant = TestData.variantPublishUseParent();
		home.addVariant(AddVariant.getHash(), dataVariant);
		/*
		 * VP: Verify that the new product variant detail page is displayed and the “Suspend” link is not displayed
		 */
		Assert.assertEquals(home.existsElement(VariantInfo.getElementsInfo()).getResult(), "Pass");
		Assert.assertFalse(home.isElementPresent(VariantInfo.SUSPEND));
		// 9. Pass through three steps of Approval Process
		// Approve marketing
		home.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		home.click(VariantInfo.REQUEST_MARKETING_REVIEW);
		home.click(VariantInfo.APPROVE_MARKETING);
		// 10. Click “Publish” button
		home.click(VariantInfo.PUBLISH);
		/*
		 * VP: Verify that the product variant is published successfully and the “Suspend” link is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.PUBLISHED_STATUS), "Published");
		Assert.assertTrue(home.isElementPresent(VariantInfo.SUSPEND));
		// 11. Click “Suspend” link
		home.click(VariantInfo.SUSPEND);
		home.selectConfirmationDialogOption("Suspend");
		/*
		 * Verify that the product variant is suspended successfully
		 */
		home.selectAnaccessorybyName(dataProduct.get("name"));
		home.selectAVariantbyName(dataVariant.get("name"));
		Assert.assertEquals(home.getTextByXpath(VariantInfo.PUBLISHED_STATUS), "Suspended");
		// Delete product
		home.click(VariantInfo.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that DTS user could not suspend accessory and accessory variant successfully when the “Publish and suspend accessories” privilege is disabled.
	 */
	@Test
	public void TCPDA_19(){
		result.addLog("ID : TCPDA_19 : Verify that DTS user could not suspend accessory and accessory variant successfully when the “Publish and suspend accessories” privilege is disabled.");
		/*
		  	Pre-condition: The “Publish and suspend Products” privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “Products” page
			4. Select a published product from Products table which have at least one published product variant
			VP: Verify that the “Suspend” link is not displayed in product detail page
			5. Select a published product variant from product variants list
			VP: Verify that the “Suspend” link is not displayed in product variant detail page
		 */
		/*
		 * Pre-condition: The “Publish and suspend Products” privilege is
		 * disabled and a published product which has at least one published
		 * variant is created
		 */
		// Navigate to user page
		home.click(Xpath.LINK_USERS);
		// Select a dts user from table
		home.dtsSelectUserByEmail(dtsUser);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable “Publish and suspend Products” privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Publish_and_suspend_products);
		home.click(AddUser.SAVE);
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create published product
		Hashtable<String,String> dataProduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct);
		// Click Add new variant link
		home.click(AccessoryInfo.ADD_VARIANT);
		// Create published variant
		Hashtable<String,String> dataVariant = TestData.variantPublish();
		home.createVariantPublish(AddVariant.getHash(), dataVariant);
		// Log out
		home.logout();
		/*
		 * ********************************************************************
		 */
		// 2. Log into DTS portal as a DTS user successfully
		home.login(dtsUser, dtsPass);
		// 3. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 4. Select a published product from Products table which have at least one published product variant
		home.selectAnaccessorybyName(dataProduct.get("name"));
		/*
		 * VP: Verify that the “Suspend” link is not displayed in product detail page
		 */
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.SUSPEND));
		// 5. Select a published product variant from product variants list
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that the “Suspend” link is not displayed in product variant detail page
		 */
		Assert.assertFalse(home.isElementPresent(VariantInfo.SUSPEND));
	}
	
	/*
	 * Verify that the DTS user could request DTS tuning under Partner role when the “Request accessory tunings” privilege is enabled.
	 */
	@Test
	public void TCPDA_20(){
		result.addLog("ID : TCPDA_20 : Verify that the DTS user could request DTS tuning under Partner role when the “Request accessory tunings” privilege is enabled.");
		/*
		  	Pre-condition: The “Request product tunings” privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “Products” page
			4. Click “Add product” link
			5. Fill valid value into all required fields
			6. Do not upload tuning file
			7. Click “Save” link
			VP: Verify that the product detail page is displayed, the tuning approval state is “Unsubmitted”, the action link “Request DTS tuning” displays and belongs to “Partner Actions” link
			8. Expand the “Partner Actions” link
			9. Click “Request DTS tuning” link
			10. Submit for the request tuning form successfully
		 */
		// 3. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 4. Click “Add product” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields
		// 6. Do not upload tuning file
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: Verify that the product detail page is displayed, the tuning
		 * approval state is “Unsubmitted”, the action link “Request DTS tuning”
		 * displays and belongs to “Partner Actions” link
		 */
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "UNSUBMITTED");
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_TUNING), AccessoryInfo.REQUEST_DTS_TUNING_ACTION_STRING);
		// 8. Expand the “Partner Actions” link
		// 9. Click “Request DTS tuning” link
		home.click(AccessoryInfo.REQUEST_TUNING);
		// 10. Submit for the request tuning form successfully
		home.click(TuningRequestForm.AGREE);
		home.click(TuningRequestForm.SUBMIT);
		/*
		 * Verify that the tuning approval state is changed to “DTS Request ”,
		 * below the tuning status is “Upload Tuning” link and the action link
		 * which belongs to “Partner Actions” link is “Cancel Request”
		 */
		home.click(TuningRequestForm.PRODUCT_LINK);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "DTS REQUEST PENDING");
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.UPLOAD_TUNING));
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.CANCEL_REQUEST_TUNING));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user could not request DTS tuning under partner role when the “Request accessory tunings” privilege is disabled.
	 */
	@Test
	public void TCPDA_21(){
		result.addLog("ID : TCPDA_21 : Verify that the DTS user could request DTS tuning under Partner role when the “Request accessory tunings” privilege is enabled.");
		/*
		  	Pre-condition: The “Request product tunings” privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “Products” page
			4. Click “Add product” link
			5. Fill valid value into all required fields
			6. Do not upload tuning file
			7. Click “Save” link
		 */
		/*
		 * Pre-condition: The “Request accessory tunings” privilege is disabled
		 */
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a DTS user from table
		home.selectUserByEmail(dtsUser);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable “Request accessory tunings” privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Request_product_tunings);
		home.click(AddUser.SAVE);
		// Log out
		home.logout();
		/*
		 * ******************************************************
		 */
		// 2. Log into DTS portal as a DTS user successfully
		home.login(dtsUser, dtsPass);
		// 3. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 4. Click “Add product” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields
		// 6. Do not upload tuning file
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * Verify that the tuning approval state is changed to “DTS Request ”
		 * there is no more action below the status
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "UNSUBMITTED");
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.PARTNER_ACTIONS_STEP_1));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user could approve the Partner tuning file when the “Approve accessory tunings” privilege is enabled.
	 */
	@Test
	public void TCPDA_22(){
		result.addLog("ID : TCPDA_22 : Verify that the DTS user could approve the Partner tuning file when the “Approve accessory tunings” privilege is enabled");
		/*
		 	Pre-condition: The “Approve product tunings” privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to “Products” page
			4. Click “Add product” link
			5. Fill valid value into all required fields
			6. Upload tuning file successfully
			7. Click “Save” link
			VP: Verify that the product detail page is displayed, the tuning status is “Unsubmitted” and the action link is “Request DTS Review”
			8. Click “Request DTS Review” link
			VP: Verify that the tuning status is now changed to “ DTS Review” and below is “Cancel Request” link
			9. Log out DTS portal
			10. Log into DTS portal as a DTS user successfully
			11. Navigate to “Products” page
			12. Select above product from the Products table
			VP: Verify that the tuning approval status is “ DTS REVIEW”, below the status is “Approve Tuning” and “Decline Tuning”, “Edit HeadPhone:X Rating” links and the “Cancel Request” link belongs the “Partner Actions” link
			13. Click “Approve Tuning” link
		 */
		// Log out
		home.logout();
		// 2. Log into DTS portal as a Partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 3. Navigate to “Products” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 4. Click “Add product” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields
		// 6. Upload tuning file successfully
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: Verify that the product detail page is displayed, the tuning
		 * status is “Unsubmitted” and the action link is “Request DTS Review”
		 */
		Assert.assertEquals(home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(PartnerAccessoryInfo.TUNING_STATUS), "UNSUBMITTED");
		Assert.assertEquals(home.getTextByXpath(PartnerAccessoryInfo.TUNING_ACTION), "Request Tuning Review");
		// 8. Click “Request DTS Review” link
		home.click(PartnerAccessoryInfo.REQUEST_TUNING);
		/*
		 * VP: Verify that the tuning status is now changed to “ DTS Review” and
		 * below is “Cancel Request” link
		 */
		Assert.assertEquals(home.getTextByXpath(PartnerAccessoryInfo.TUNING_STATUS), "PENDING DTS APPROVAL");
		Assert.assertEquals(home.getTextByXpath(PartnerAccessoryInfo.TUNING_ACTION), "Cancel Request");
		// 9. Log out DTS portal
		home.logout();
		// 10. Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// 11. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 12. Select above product from the Products table
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the tuning approval status is “ DTS REVIEW”, below
		 * the status is “Approve Tuning” and “Decline Tuning”, “Edit
		 * HeadPhone:X Rating” links and the “Cancel Request” link belongs the
		 * “Partner Actions” link
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "PENDING DTS APPROVAL");
		Assert.assertEquals(home.getTextByXpath(PartnerAccessoryInfo.TUNING_ACTION), "Approve Tuning");
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.DECLINE_TUNING));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TUNING_RATING_WARNING_PARTNER));
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.CANCEL_REQUEST_TUNING));
		// Select tuning rating
		home.click(AccessoryInfo.EDIT_MODE);
		home.selectFilterByName(AddAccessory.TUNING_RATING, "A");
		home.click(AddAccessory.SAVE);
		// 13. Click “Approve Tuning” link
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * Verify that the tuning state is now changed to “Approved”, below is
		 * “Edit Headphone: X Certification”, “Revoke Tuning” links, the
		 * “Download Certification Badges” link belongs to “Partner Actions”
		 * link
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.APPROVED);
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.EDIT_HEADPHONE_CERTIFICATION));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.REVOKE_TUNING));
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DOWNLOAD_CERTIFICATE), AccessoryInfo.DOWNLOAD_CERT_BADGES_ACTION_STRING);
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user could decline the DTS tuning file when the “Approve accessory tunings” privilege is enabled
	 */
	@Test
	public void TCPDA_23(){
		result.addLog("ID : TCPDA_23 : Verify that the DTS user could decline the DTS tuning file when the “Approve accessory tunings” privilege is enabled");
		/*
		  	Pre-condition: The “Approve product tunings” privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to “Products” page
			4. Click “Add product” link
			5. Fill valid value into all required fields
			6. Upload tuning file successfully
			7. Click “Save” link
			VP: Verify that the product detail page is displayed, the tuning status is “Unsubmitted” and the action link is “Request DTS Review”
			8. Click “Request DTS Review” link
			VP: Verify that the tuning status is now changed to “PENDING DTS APPROVAL” and below is “Cancel Request” link
			9. Log out DTS portal
			10. Log into DTS portal as a DTS user successfully
			11. Navigate to “Products” page
			12. Select above product from the Products table
			VP: Verify that the tuning approval status is “ DTS REVIEW”, below the status is “Approve Tuning” and “Decline Tuning”, “Edit HeadPhone:X Rating” links and the “Cancel Request” link belongs the “Partner Actions” link
			13. Click “Decline Tuning” link
		 */
		// Log out
		home.logout();
		// 2. Log into DTS portal as a Partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 3. Navigate to “Products” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 4. Click “Add product” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields
		// 6. Upload tuning file successfully
		// 7. Click “Save” link
		Hashtable<String, String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: Verify that the product detail page is displayed, the tuning
		 * status is “Unsubmitted” and the action link is “Request DTS Review”
		 */
		Assert.assertEquals(home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(PartnerAccessoryInfo.TUNING_STATUS), "UNSUBMITTED");
		Assert.assertEquals(home.getTextByXpath(PartnerAccessoryInfo.TUNING_ACTION), "Request Tuning Review");
		// 8. Click “Request DTS Review” link
		home.click(PartnerAccessoryInfo.REQUEST_TUNING);
		/*
		 * VP: Verify that the tuning status is now changed to “PENDING DTS APPROVAL” and
		 * below is “Cancel Request” link
		 */
		Assert.assertEquals(home.getTextByXpath(PartnerAccessoryInfo.TUNING_STATUS), "PENDING DTS APPROVAL");
		Assert.assertEquals(home.getTextByXpath(PartnerAccessoryInfo.TUNING_ACTION), "Cancel Request");
		// 9. Log out DTS portal
		home.logout();
		// 10. Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// 11. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 12. Select above product from the Products table
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the tuning approval status is “PENDING DTS APPROVAL”, below
		 * the status is “Approve Tuning” and “Decline Tuning”, “Edit
		 * HeadPhone:X Rating” links and the “Cancel Request” link belongs the
		 * “Partner Actions” link
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "PENDING DTS APPROVAL");
		Assert.assertEquals(home.getTextByXpath(PartnerAccessoryInfo.TUNING_ACTION), "Approve Tuning");
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.DECLINE_TUNING));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TUNING_RATING_WARNING_PARTNER));
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.CANCEL_REQUEST_TUNING));
		// 13. Click “Decline Tuning” link
		home.click(AccessoryInfo.DECLINE_TUNING);
		/*
		 * Verify that the tuning state is now changed to “Declined”, and there
		 * is no more action below the status
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "DECLINED");
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.PARTNER_ACTIONS_STEP_1));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	 /*
	  * Verify that the DTS user could not approve or decline the DTS tuning file when the “Approve accessory tunings” privilege is disabled.
	  */
	@Test
	public void TCPDA_24(){
		result.addLog("ID : TCPDA_24 : Verify that the DTS user could not approve or decline the DTS tuning file when the “Approve accessory tunings” privilege is disabled");
		/*
			Pre-condition: The “Approve product tunings” privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to “Products” page
			4. Click “Add product” link
			5. Fill valid value into all required fields
			6. Upload tuning file successfully
			7. Click “Save” link
			VP: Verify that the product detail page is displayed, the tuning status is “Unsubmitted” and the action link is “Request DTS Review”
			8. Click “Request DTS Review” link
			VP: Verify that the tuning status is now changed to “PENDING DTS APPROVAL” and below is “Cancel Request” link
			9. Log out DTS portal
			10. Log into DTS portal as a DTS user successfully
			11. Navigate to “Products” page
			12. Select above product from the Products table
		 */
		/*
		 * Pre-condition: The “Approve product tunings” privilege is disabled
		 */
		// Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// Click “Edit” link
		home.click(UserInfo.EDIT);
		// Disable “Approve product tunings” privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Approve_product_tuning);
		home.click(AddUser.SAVE);
		// Log out DTS portal
		home.logout();
		/*
		 * *****************************************************************
		 */
		//2. Log into DTS portal as a Partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		//3. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		//4. Click “Add Accessory” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields
		// 6. Upload tuning file successfully
		// 7. Click “Save” link
		Hashtable<String, String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: Verify that the product detail page is displayed, the tuning
		 * status is “Unsubmitted” and the action link is “Request Tuning Review”
		 */
		Assert.assertEquals(home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(PartnerAccessoryInfo.TUNING_STATUS), "UNSUBMITTED");
		Assert.assertEquals(home.getTextByXpath(PartnerAccessoryInfo.TUNING_ACTION), "Request Tuning Review");
		// 8. Click “Request DTS Review” link
		home.click(PartnerAccessoryInfo.REQUEST_TUNING);
		/*
		 * VP: Verify that the tuning status is now changed to “PENDING DTS APPROVAL” and below is “Cancel Request” link
		 */
		Assert.assertEquals(home.getTextByXpath(PartnerAccessoryInfo.TUNING_STATUS), "PENDING DTS APPROVAL");
		Assert.assertEquals(home.getTextByXpath(PartnerAccessoryInfo.TUNING_ACTION), "Cancel Request");
		// 9. Log out DTS portal
		home.logout();
		// 10. Log into DTS portal as a DTS user successfully
		home.login(dtsUser, dtsPass);
		// 11. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 12. Select above product from the Products table
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * Verify that the tuning approval status is “PENDING DTS APPROVAL”, the “Approve
		 * Tuning” and “Decline Tuning” are not displayed but the “Cancel
		 * Request” link which belongs to the “Partner Actions” link is
		 * displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "PENDING DTS APPROVAL");
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.APPROVE_TUNING));
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.DECLINE_TUNING));
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.CANCEL_REQUEST_TUNING));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user could approve the Marketing material when the “Approve marketing information” privilege is enabled
	 */
	@Test
	public void TCPDA_25(){
		result.addLog("ID : TCPDA_25 : Verify that the DTS user could approve the Marketing material when the “Approve marketing information” privilege is enabled");
		/*
		  	Pre-condition: The “Approve marketing information” privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “Products” page
			4. Click “Add product” link
			5. Fill valid value into all required fields
			6. Upload tuning file and marketing materials successfully
			7. Click “Save” link
			8. Complete step 1 “Tuning Approval” of Publishing Process successfully.
			VP: Verify that after the tuning is approved, the status of step 2 “Marketing Approval” is changed to “UNSUBMITTED” and the “Partner Actions” link includes “Request Marketing Review” link
			9. Click “Request Marketing Review” link
			VP: Verify that the status of step 2 “Marketing Approval” is changed to “ DTS REVIEW”, the “Edit” link is locked by the lock icon and there is a “Approve Marketing”  and a “Decline Marketing” link displayed.
			10. Click “Approve Marketing” link
		 */
		// 3. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 4. Click “Add product” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields
		// 6. Upload tuning file and marketing materials successfully
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryTuning();
		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// 8. Complete step 1 “Tuning Approval” of Publishing Process successfully
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * VP: Verify that after the tuning is approved, the status of step 2
		 * “Marketing Approval” is changed to “UNSUBMITTED” and the “Partner
		 * Actions” link includes “Request Marketing Review” link
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "UNSUBMITTED");
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_ACTION), "Request Marketing Review");
		// 9. Click “Request Marketing Review” link
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		/*
		 * VP: Verify that the status of step 2 “Marketing Approval” is changed
		 * to “PENDING DTS REVIEW”, the “Edit” link is locked by the lock icon and
		 * there is a “Approve Marketing” and a “Decline Marketing” link
		 * displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "PENDING DTS REVIEW");
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.EDIT_MODE));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.LOCK_ICON));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.APPROVE_MARKETING));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.DECLINE_MARKETING));
		// 10. Click “Approve Marketing” link
		home.click(AccessoryInfo.APPROVE_MARKETING);
		/*
		 * Verify that the status of marketing approval is changed to “Approved”
		 * and the “Revoke Marketing Approval” is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "APPROVED");
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_ACTION), "Revoke Marketing Approval");
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user could approve the Marketing material when the “Decline marketing information” privilege is enabled.
	 */
	@Test
	public void TCPDA_26(){
		result.addLog("ID : TCPDA_26 : Verify that the DTS user could approve the Marketing material when the “Decline marketing information” privilege is enabled");
		/*
			Pre-condition: The “Approve marketing information” privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “Products” page
			4. Click “Add product” link
			5. Fill valid value into all required fields
			6. Upload tuning file and marketing materials successfully
			7. Click “Save” link
			8. Complete step 1 “Tuning Approval” of Publishing Process successfully.
			VP: Verify that after the tuning is approved, the status of step 2 “Marketing Approval” is changed to “UNSUBMITTED” and the “Partner Actions” link includes “Request Marketing Review” link
			9. Click “Request Marketing Review” link
			VP: Verify that the status of step 2 “Marketing Approval” is changed to “PENDING DTS REVIEW”, the “Edit” link is locked by the lock icon and there is a “Approve Marketing”  and a “Decline Marketing” link displayed.
			10. Click “Decline Marketing” link
		 */
		// 3. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 4. Click “Add product” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields.
		// 6. Upload tuning file and marketing materials successfully
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryTuning();
		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// 8. Complete step 1 “Tuning Approval” of Publishing Process successfully
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * VP: Verify that after the tuning is approved, the status of step 2
		 * “Marketing Approval” is changed to “UNSUBMITTED” and the “Partner
		 * Actions” link includes “Request Marketing Review” link
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "UNSUBMITTED");
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_ACTION), "Request Marketing Review");
		// 9. Click “Request Marketing Review” link
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		/*
		 * VP: Verify that the status of step 2 “Marketing Approval” is changed
		 * to “PENDING DTS REVIEW”, the “Edit” link is locked by the lock icon
		 * and there is a “Approve Marketing” and a “Decline Marketing” link
		 * displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "PENDING DTS REVIEW");
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.EDIT_MODE));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.LOCK_ICON));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.APPROVE_MARKETING));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.DECLINE_MARKETING));
		// 10. Click “Decline Marketing” link
		home.click(AccessoryInfo.DECLINE_MARKETING);
		/*
		 * Verify that the status of marketing approval is changed to “Declined”
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "DECLINED");
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user could not approve or decline the Marketing material when the “Decline marketing information” privilege is disabled.
	 */
	@Test
	public void TCPDA_27(){
		result.addLog("ID : TCPDA_27 : Verify that the DTS user could not approve or decline the Marketing material when the “Decline marketing information” privilege is disabled.");
		/*
			Pre-condition: The “Approve marketing information” privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “Products” page
			4. Click “Add product” link
			5. Fill valid value into all required fields
			6. Upload tuning file and marketing materials successfully
			7. Click “Save” link
			8. Complete step 1 “Tuning Approval” of Publishing Process successfully.
			VP: Verify that after the tuning is approved, the status of step 2 “Marketing Approval” is changed to “UNSUBMITTED” and the “Partner Actions” link includes “Request Marketing Review” link
			9. Click “Request Marketing Review” link
		 */
		
		/*
		 * Pre-condition: The “Approve marketing information” privilege is disabled
		 */
		// Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// Click “Edit” link
		home.click(UserMgmt.EDIT);
		// Disable “Approve marketing information” privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Approve_marketing_info);
		home.click(AddUser.SAVE);
		// Log out
		home.logout();
		/*
		 * *******************************************************************
		 */
		// 2. Log into DTS portal as a DTS user successfully
		home.login(dtsUser, dtsPass);
		// 3. Navigate to “Accessories” page
		home.click(Xpath.linkAccessories);
		// 4. Click “Add Accessory” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields.
		// 6. Upload tuning file and marketing materials successfully
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryTuning();
		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// 8. Complete step 1 “Tuning Approval” of Publishing Process successfully.
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * VP: Verify that after the tuning is approved, the status of step 2 “Marketing Approval” is changed to “UNSUBMITTED” and the “Partner Actions” link includes “Request Marketing Review” link
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "APPROVED");
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "UNSUBMITTED");
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_ACTION), "Request Marketing Review");
		// 9. Click “Request Marketing Review” link
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		/*
		 * Verify that the status of step 2 “Marketing Approval” is changed to “ DTS REVIEW”, the “Edit” link is locked by the lock icon and there are no any action links displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "PENDING DTS REVIEW");
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.EDIT_MODE));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.LOCK_ICON));
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.APPROVE_MARKETING));
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.DECLINE_MARKETING));
		// Delete accessory
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the “Upload Tuning” link does not display when the “Add and manage accessories” is disabled.
	 */
	@Test
	public void TCPDA_28(){
		result.addLog("ID : TCPDA_28 : Verify that the “Upload Tuning” link does not display when the “Add and manage accessories” is disabled");
		/*
			Pre-condition: The “Add and manage accessories” privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Navigate to “Accessories” page
			4. Click “Add Accessory” link
			5. Fill valid value into all required fields
			6. Do not upload tuning file
			7. Click “Save” link
			8. Request for the DTS Tuning file
			9. Log out DTS portal
			10. Log into DTS portal as a DTS user successfully
			11. Navigate to “Accessories” page
			12. Select above accessory from accessories table
		 */
		/*
		 * Pre-condition: The “Add and manage accessories” privilege is disabled
		 */
		// Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// Click “Edit” link
		home.click(UserInfo.EDIT);
		// Disable “Add and manage accessories” privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE,Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// Log out
		home.logout();
		/*
		 * *****************************************************************
		 */
		// 2. Log into DTS portal as a partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 3. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 4. Click “Add Accessory” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields.
		// 6. Do not upload tuning file
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(PartnerAddAccessory.getHash(), data);
		// 8. Request for the DTS Tuning file
		home.click(PartnerAccessoryInfo.REQUEST_TUNING);
		// Tick to check "I understand the terms of this submission" checkbox
		home.click(TuningRequestForm.AGREE);
		// Click "Submit" link
		home.click(TuningRequestForm.SUBMIT);
		// 9. Log out DTS portal
		home.logout();
		// 10. Log into DTS portal as a DTS user successfully
		home.login(dtsUser, dtsPass);
		// 11. Navigate to “Accessories” page
		home.click(Xpath.linkAccessories);
		// 12. Select above accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * Verify that the “Upload Tuning” link does not display in step 1: Tuning Approval module
		 */
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.UPLOAD_TUNING));
		// Delete accessory
		home.doDelete(AccessoryInfo.DELETE);
	}	
		
	/*
	 * Verify that the DTS user can upload tuning file via “Upload Tuning” link when the “Add and manage accessories” is enabled
	 */
	@Test
	public void TCPDA_29(){
		result.addLog("ID : TCPDA_29 : Verify that the DTS user can upload tuning file via “Upload Tuning” link when the “Add and manage accessories” is enabled");
		/*
			Pre-condition: The “Add and manage accessories” privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Navigate to “Accessories” page
			4. Click “Add Accessory” link
			5. Fill valid value into all required fields
			6. Do not upload tuning file
			7. Click “Save” link
			8. Request for the DTS Tuning file
			9. Log out DTS portal
			10. Log into DTS portal as a DTS user successfully
			11. Navigate to “Accessories” page
			12. Select above accessory from accessories table
			VP: Verify that the “Upload Tuning” link is displayed in step 1: Tuning Approval module
			13. Click “Upload Tuning” link
			VP: Verify that the accessory edit page is displayed
			14. Upload a tuning file successfully
			15. Click “Save” link
		 */
		// Log out
		home.logout();
		// 2. Log into DTS portal as a partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 3. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 4. Click “Add Accessory” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields.
		// 6. Do not upload tuning file
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(PartnerAddAccessory.getHash(), data);
		// 8. Request for the DTS Tuning file
		home.click(PartnerAccessoryInfo.REQUEST_TUNING);
		// Tick to check "I understand the terms of this submission" checkbox
		home.click(TuningRequestForm.AGREE);
		// Click "Submit" link
		home.click(TuningRequestForm.SUBMIT);
		// 9. Log out DTS portal
		home.logout();
		// 10. Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// 11. Navigate to “Accessories” page
		home.click(Xpath.linkAccessories);
		// 12. Select above accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the “Upload Tuning” link is displayed in step 1: Tuning Approval module
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.UPLOAD_TUNING));
		// 13. Click “Upload Tuning” link
		home.click(AccessoryInfo.UPLOAD_TUNING);
		/*
		 * VP: Verify that the accessory edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(), "Pass");
		// 14. Upload a tuning file successfully
		home.uploadFile(AddAccessory.ADD_TUNNING, Constant.tuning_hpxtt);
		// 15. Click “Save” link
		home.click(AddAccessory.SAVE);
		/*
		 * Verify that the DTS tuning file is uploaded and saved successfully
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.UPLOADED_TUNING));
		// Delete accessory
		home.doDelete(AccessoryInfo.DELETE);
	}
	
		
}
