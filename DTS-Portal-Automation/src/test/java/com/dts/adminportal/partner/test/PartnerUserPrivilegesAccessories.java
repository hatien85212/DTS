package com.dts.adminportal.partner.test;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
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
import dts.com.adminportal.model.PartnerAddVariant;
import dts.com.adminportal.model.PartnerHomePage;
import dts.com.adminportal.model.PartnerVariantInfo;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.TuningRequestForm;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.VariantInfo;
import dts.com.adminportal.model.Xpath;

public class PartnerUserPrivilegesAccessories extends CreatePage{
	private HomeController home;
	
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}
	
	/*
	 * Verify that the partner user can add accessories successfully when the  "Add and manage accessories” privilege is enabled.
	 */
	@Test
	public void TCPPA_01(){
		result.addLog("ID : TCPPA_01: Verify that the partner user can add accessories successfully when the  'Add and manage accessories' privilege is enabled.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Enable the “Add and manage accessories” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP. Verify that the “Accessories” page is displayed
			9. Navigate to “Home” page
			VP. The “Add Accessory” link is displayed in “Catalog” module
			10. Navigate to “Accessories” page
			VP. Verify that the “Add Accessory” link is displayed inside “Actions” module and the accessories table is also displayed.
			11. Click on “Add Accessory” link
			12. Fill valid value into all required fields
			13. Click “Save” link
		 */
		// 2. Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.dtsSelectUserByEmail(partneruser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Add and manage accessories” privilege
		home.enableAllPrivilege();
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner user successfully
		home.login(partneruser, password);
		// VP. Verify that the “Accessories” page is displayed
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_PARTNER_ACCESSORIES));
		// 9. Navigate to “Home” page
		home.click(PartnerHomePage.LINK_PARTNER_HOME);
		// VP. The “Add Accessory” link is displayed in “Catalog” module
		Assert.assertTrue(home.isElementPresent(PartnerHomePage.PARTNER_CATALOG_ADD_ACCESSORY));
		// 10. Navigate to “Accessories” page
		home.click(PartnerHomePage.LINK_PARTNER_ACCESSORIES);
		// VP. Verify that the “Add Accessory” link is displayed inside “Actions” module and the accessories table is also displayed.
		Assert.assertTrue(home.isElementPresent(AccessoryMain.ADD_ACCESSORY));
		Assert.assertTrue(home.isElementPresent(AccessoryMain.ACCESSORY_TABLE));
		// 11. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 12. Fill valid value into all required fields.
		// 13. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(PartnerAddAccessory.getHash(), data);
		/*
		 * Verify that the new product is displayed in Products table
		 */
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		Assert.assertTrue(home.checkAnAccessoryExistByName(data.get("name")));
		// Clean Up
		home.selectAnaccessorybyName(data.get("name"));
		home.doDelete(PartnerAccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the partner user can edit accessories successfully when the  "Add and manage accessories” privilege is enabled.
	 */
	@Test
	public void TCPPA_02(){
		result.addLog("ID : TCPPA_02: Verify that the partner user can edit accessories successfully when the  'Add and manage accessories' privilege is enabled.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Enable the “Add and manage accessories” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			9. Navigate to “Accessories” page
			10. Select an accessory from accessories table
			11. Click on “Edit” link in accessory detail page
			12. Change accessory's information
			13. Click “Save” link
		 */
		/*
		 * PreCondition: Create new product
		 */
		// Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * ****************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.dtsSelectUserByEmail(partneruser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Add and manage accessories” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner user successfully
		home.login(partneruser, password);
		// 9. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 10. Select an accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		// 11. Click on “Edit” link in accessory detail page
		home.click(AccessoryInfo.EDIT_MODE);
		// 12. Change accessory's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		home.editData(PartnerAddAccessory.NAME, newName);
		// 13. Click “Save” link
		home.click(PartnerAddAccessory.SAVE);
		/*
		 * Verify that new product's information is displayed correctly
		 */
		Assert.assertEquals(home.getTextByXpath(PartnerAccessoryInfo.ACCESSORY_NAME), newName);
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
			
	/*
	 * Verify that the partner user can add Products variant successfully when the  "Add and manage Products” privilege is enabled
	 */
	@Test
	public void TCPPA_03(){
		result.addLog("ID : TCPPA_03: Verify that the partner user can add Products variant successfully when the  ”Add and manage Products” privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Enable the “Add and manage Products” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			9. Navigate to “Accessories” page
			10. Select a published product from Products table
			VP. Verify that the “Add product Variant” link displayed in product detail page
			11. Click on “Add product Variant” link in product detail page
			VP. Verify that the adding new product variant page is displayed
			12. Fill valid value into all required fields
			13. Click “Save” link
		 */
		// 2. Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.dtsSelectUserByEmail(partneruser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Add and manage Products” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner user successfully
		home.login(partneruser, password);
		// 9. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 10. Select a published accessory from accessories table
		home.selectAnAccessory();
		// VP. Verify that the “Add Accessory Variant” link displayed in accessory detail page
		Assert.assertEquals(home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult(), "Pass");
		Assert.assertTrue(home.isElementPresent(PartnerAccessoryInfo.ADD_VARIANT));
		// 11. Click on “Add product Variant” link in product detail page
		home.click(PartnerAccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the adding new product variant page is displayed
		 */
		Assert.assertEquals(home.existsElement(PartnerAddVariant.getElementsInfo()).getResult(), "Pass");
		// 12. Fill valid value into all required fields
		// 13. Click “Save” link
		Hashtable<String,String> data = TestData.variantDraft();
		home.addVariant(PartnerAddVariant.getHash(), data);
		/*
		 * Verify that New product variant could be save successfully
		 */
		Assert.assertEquals(home.existsElement(PartnerVariantInfo.getElementsInfo()).getResult(), "Pass");
	}
	
	/*
	 * Verify that the partner user can edit accessories variant successfully when the  "Add and manage accessories” privilege is enabled.
	 */
	@Test
	public void TCPPA_04(){
		result.addLog("ID : TCPPA_04: Verify that the partner user can edit accessories variant successfully when the  'Add and manage accessories' privilege is enabled.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Enable the “Add and manage accessories” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			9. Navigate to “Accessories” page
			10. Select an accessory from accessories table which has at least one variant
			11. Select an accessory variant from variant list in accessory detail page
			VP: Verify that the accessory variant detail page is displayed and the “Edit” link is displayed too
			12. Click “Edit” link
			13. Change accessory variant's information
			14. Click “Save” link
		 */
		/*
		 * Pre-condition: Create accessory which has at least one variant	
		 */
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// Click add Accessories link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create accessory
		Hashtable<String,String> dataProduct = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), dataProduct);
		// Click on Add new variant link
		home.click(AccessoryInfo.ADD_VARIANT);
		// Create new variant
		Hashtable<String,String> dataVariant = TestData.variantDraft();
		home.addVariant(AddVariant.getHash(), dataVariant);
		/*
		 * *******************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.dtsSelectUserByEmail(partneruser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Add and manage accessories” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner user successfully
		home.login(partneruser, password);
		// 9. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 10. Select an accessory from accessories table which has at least one variant
		home.selectAnaccessorybyName(dataProduct.get("name"));
		// 11. Select an accessory variant from variant list in accessory detail page
		home.selectAVariantbyName(dataVariant.get("name"));
		// VP: Verify that the accessory variant detail page is displayed and the “Edit” link is displayed too
		Assert.assertEquals(home.getTextByXpath(VariantInfo.TITLE_NAME), dataVariant.get("name"));
		Assert.assertTrue(home.isElementPresent(PartnerVariantInfo.EDIT_VARIANT));
		// 12. Click “Edit” link
		home.click(VariantInfo.EDIT_VARIANT);
		// 13. Change accessory variant's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(9);
		home.editData(AddVariant.NAME, newName);
		// 14. Click “Save” link
		home.click(AddVariant.SAVE);
		/*
		 * Verify that The product variant's information is changed successfully
		 */
		Assert.assertEquals(home.getTextByXpath(PartnerVariantInfo.DISPLAYNAME_DEFAULT), newName);
		// Delete product
		home.click(PartnerVariantInfo.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the partner user can view accessory's published version info when the "Add and manage accessories”, 
	 * “Publish and suspend accessories” and “Request accessory tunings” privileges are disabled.
	 */
	@Test
	public void TCPPA_05(){
		result.addLog("ID : TCPPA_05: Verify that the partner user can view product's published version info when the ”Add and manage Products”, “Publish and suspend Products” and “Request product tunings” privileges are disabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Enable the  "Add and manage Products”, “Publish and suspend Products” and “Request product tunings” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			9. Navigate to “Accessories” page
			10. Select a published product from Products table.
			VP. The “Update product info” link is displayed
			11. Create new version for the published product successfully
			VP: The “View Published Version” link is displayed.
			12. Log out DTS portal
			13. Perform from step 2 to 5 again
			14. Disabled the "Add and manage Products”, “Publish and suspend Products” and “Request product tunings” privileges
			15. Perform from step 7 to 10 again
		 */
		/*
		 * Pre-condition: Create a published product
		 */
		// Login as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to accessory page
		home.click(Xpath.linkAccessories);
		// Click Add Accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create accessory 
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), data);
		/*
		 * *****************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.dtsSelectUserByEmail(partneruser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6.Enable the "Add and manage accessories”, “Publish and suspend accessories” and “Request accessory tunings” privileges
		home.enableAllPrivilege();
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner user successfully
		home.login(partneruser, password);
		// 9. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 10. Select a published product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		// VP. The “Update product info” link is displayed
		Assert.assertTrue(home.isElementPresent(PartnerAccessoryInfo.UPDATE_PRODUCT_INFO));
		// 11. Create new version for the published product successfully
		home.click(PartnerAccessoryInfo.UPDATE_PRODUCT_INFO);
		home.selectConfirmationDialogOption("Ok");
		home.click(PartnerAddAccessory.SAVE);
		/*
		 * VP: The “View Published Version” link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(PartnerAccessoryInfo.VIEW_PUBLISHED_MODEL));
		// 12. Log out DTS portal
		home.logout();
		// 13. Perform from step 2 to 5 again
		// Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// Select a partner user from the Users table
		home.dtsSelectUserByEmail(partneruser);
		// Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 14. Disabled the "Add and manage accessories”, “Publish and suspend accessories” and “Request accessory tunings” privileges
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Publish_and_suspend_products);
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Request_product_tunings);
		home.click(AddUser.SAVE);
		// 15. Perform from step 7 to 10 again
		// Log out DTS portal
		home.logout();
		// Log into DTS portal as above partner user successfully
		home.login(partneruser, password);
		// Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select an accessory from accessories table which has at least one version
		home.selectAnaccessorybyName(data.get("name"));
		// VP: The “View Published Version” link is displayed.
		Assert.assertTrue(home.isElementPresent(PartnerAccessoryInfo.VIEW_PUBLISHED_MODEL));
	}
	
	/*
	 * Verify that the partner user can view accessory variant published version info when the "Add and manage accessories”, “Publish and suspend accessories” and “Request accessory tunings” privileges are disabled
	 */
	@Test
	public void TCPPA_06(){
		result.addLog("ID : TCPPA_06: Verify that the partner user can view accessory variant published version info when the “Add and manage accessories”, “Publish and suspend accessories” and “Request accessory tunings” privileges are disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Enable the  "Add and manage Products”, “Publish and suspend Products” and “Request product tunings” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			9. Navigate to “Accessories” page
			10. Select a published product from Products table which have at least one product variant is also published
			11. Select the published product variant from variant list in product detail page
			VP. The “Update Variant Info” link is displayed
			12. Create new version for variant successfully
			VP: The “View Published Version” link is displayed in product variant detail page
			13. Log out DTS portal
			14. Perform from step 2 to 5 again
			15. Disable "Add and manage Products”, “Publish and suspend Products” and “Request product tunings” privileges
			16. Perform from step 7 to 11 again
		*/
		/*
		 * Pre-condition: Create a published accessory which has at least one published accessory variant
		 */
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to accessory page
		home.click(Xpath.linkAccessories);
		// Click Add Accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create published accessory
		Hashtable<String,String> dataProduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct);
		// Click Add variant link
		home.click(AccessoryInfo.ADD_VARIANT);
		// Create published variant
		Hashtable<String,String> dataVariant = TestData.variantPublish();
		home.createVariantPublish(AddVariant.getHash(),dataVariant);
		/*
		 * ***************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a partner user from the Users table
		home.dtsSelectUserByEmail(partneruser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  "Add and manage Products”, “Publish and suspend Products” and “Request product tunings” privileges
		home.enableAllPrivilege();
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner user successfully
		home.login(partneruser, password);
		// 9. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 10. Select a published accessory from accessories table which have at least one accessory variant is also published
		home.selectAnaccessorybyName(dataProduct.get("name"));
		// 11. Select the published accessory variant from variant list in accessory detail page
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP. The “Update Variant Info” link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(VariantInfo.CREATE_NEW_VERSION));
		// 12. Create new version for variant successfully
		home.click(VariantInfo.CREATE_NEW_VERSION);
		home.selectConfirmationDialogOption("Ok");
		home.click(AddVariant.SAVE);
		/*
		 * 	VP: The “View Published Version” link is displayed in accessory variant detail page
		 */
		Assert.assertTrue(home.isElementPresent(VariantInfo.VIEW_PUBLISHED_VERSION));
		// 13. Log out DTS portal
		home.logout();
		// 14. Perform from step 2 to 5 again
		// Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// Select a partner user from the Users table
		home.dtsSelectUserByEmail(partneruser);
		// Click edit link
		home.click(UserMgmt.EDIT);
		// 15. Disable the "Add and manage accessories”, “Publish and suspend accessories” and “Request accessory tunings” privileges
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Publish_and_suspend_products);
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Request_product_tunings);
		home.click(AddUser.SAVE);
		// 16. Perform from step 7 to 11 again
		// Log out DTS portal
		home.logout();
		// Log into DTS portal as above partner user successfully
		home.login(partneruser, password);
		// Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select a published accessory from accessories table which have at least one accessory variant is also published
		home.selectAnaccessorybyName(dataProduct.get("name"));
		// Select the published accessory variant from variant list in accessory detail page
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * Verify that the “View Published Version” link is displayed in accessory variant detail page either the  "Add and manage accessories” privilege is enabled or disabled
		 */
		Assert.assertTrue(home.isElementPresent(VariantInfo.VIEW_PUBLISHED_VERSION));
	}
	
	/*
	 * Verify that the partner user can only create a new version but not to edit a published accessory when the "Add and manage accessories” privilege is enabled.
	 */
	@Test
	public void TCPPA_07(){
		result.addLog("ID : TCPPA_07: Verify that the partner user can only create a new version but not to edit a published accessory when the 'Add and manage accessories' privilege is enabled");
		/*
			Pre-condition: The  "Add and manage accessories” privilege is enabled.
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to “Accessories” page
			4. Click “Add Accessory” link
			5. Fill valid value into all required fields
			6. Pass through two steps of Publication Process
			7. Click “Publish” button in step 3 of Publication Process
		 */
		// 2. Log into DTS portal as a Partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 3. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 4. Click “Add Accessory” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// 6. Pass through two steps of Publication Process
		// Logout user
		home.logout();
		// Re-login as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to Accessories page
		home.click(Xpath.linkAccessories);
		// Select created accessory above
		home.selectAnaccessorybyName(data.get("name"));
		// Select tuning rating
		home.click(AccessoryInfo.EDIT_MODE);
		home.selectFilterByName(AddAccessory.TUNING_RATING, "A");
		home.click(AddAccessory.SAVE);
		//Approve Tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.REQUEST_TUNING);
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Approve Marketing
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		home.click(AccessoryInfo.APPROVE_MARKETING);
		// Logout user
		home.logout();
		// Re-login as Partner user
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to Accessories page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select created accessory above
		home.selectAnaccessorybyName(data.get("name"));
		// 7. Click “Publish” button in step 3 of Publication Process
		home.click(AccessoryInfo.PUBLISH);
		/*
		 * Verify that The Edit Link disappears and the “Create New Version” is displayed.
		 */
		Assert.assertEquals(home.getTextByXpath(PartnerAccessoryInfo.EDIT_MODE), "Update Product Info");
		// Delete product
		home.doDelete(PartnerAccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the partner user can only create a new version but not to edit a published accessory variant when the "Add and manage accessories” privilege is enabled
	 */
	@Test
	public void TCPPA_08(){
		result.addLog("ID : TCPPA_08: Verify that the partner user can only create a new version but not to edit a published accessory variant when the 'Add and manage accessories' privilege is enabled.");
		/*
			Pre-condition: The  "Add and manage accessories” privilege is enabled.
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to “Accessories” page
			4. Select a published accessory from the accessories table
			5. Click “Add new variant” link
			6. Fill valid value into all required fields
			7. Pass through two steps of Publication Process
			8. Click “Publish” button in step 3 of Publication Process
		 */
		
		/*
		 * Pre-condition: Create a published product
		 */
		// Login as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to accessory page
		home.click(Xpath.linkAccessories);
		// Click Add Accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create accessory 
		Hashtable<String,String> dataProduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct);
		// Logout user
		home.logout();
		/*
		 * **************************************************************
		 */
		// 2. Log into DTS portal as a Partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 3. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 4. Select a published accessory from the accessories table
		home.selectAnaccessorybyName(dataProduct.get("name"));
		// 5. Click “Add new variant” link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 6. Fill valid value into all required fields
		Hashtable<String, String> dataVariant = TestData.variantPublishUseParent();
		home.addVariant(AddVariant.getHash(), dataVariant);
		// 7. Pass through two steps of Publication Process
		// Logout user
		home.logout();
		// Re-login as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to accessory page
		home.click(Xpath.linkAccessories);
		// Select accessory above
		home.selectAnaccessorybyName(dataProduct.get("name"));
		// Select variant
		home.selectAVariantbyName(dataVariant.get("name"));
		// Select Tuning Rating
		home.click(VariantInfo.EDIT_VARIANT);
		home.selectFilterByName(AddVariant.TUNING_RATING, "A");
		home.click(AddVariant.SAVE);
		// Approve marketing
		home.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		home.click(VariantInfo.REQUEST_MARKETING_REVIEW);
		home.click(VariantInfo.APPROVE_MARKETING);
		// Logout user
		home.logout();
		// Re-login user as Partner user
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select accessory above
		home.selectAnaccessorybyName(dataProduct.get("name"));
		// Select variant
		home.selectAVariantbyName(dataVariant.get("name"));
		// 8. Click “Publish” button in step 3 of Publication Process
		home.click(VariantInfo.PUBLISH);
		/*
		 * Verify that The Edit Link disappears and the “Create New Version” is displayed.
		 */
		Assert.assertFalse(home.isElementPresent(VariantInfo.EDIT_VARIANT));
		Assert.assertTrue(home.isElementPresent(VariantInfo.CREATE_NEW_VERSION));
		// Delete product
		home.click(PartnerVariantInfo.PRODUCT_LINK);
		home.doDelete(PartnerAccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the partner user cannot add new or edit accessories or accessories variant when the "Add and manage accessories” privilege is disabled
	 */
	@Test
	public void TCPPA_09(){
		result.addLog("ID : TCPPA_09: Verify that the partner user cannot add new or edit accessories or accessories variant when the 'Add and manage accessories' privilege is disabled");
		/*
			Pre-condition: The  "Add and manage accessories” privilege is disabled.
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to “Home” page
			VP: The “Add Accessory” link is disappears
			4. Navigate to “Accessories” page
			VP: The “Add Accessory” link is disappears
			5. Select a non published accessory from the accessories table which has at least one accessory variant
			VP: Verify that there is no edit link displayed
			6. Select an accessory variant from the variant list
			VP: Verify that there is no edit link displayed for an accessory variant
		*/
		
		/*
		 * Pre-condition: The  "Add and manage accessories” privilege is disabled and a product has at least one variant is created
		 */
		// Log into portal as DTS user
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to user page
		home.click(Xpath.LINK_PARTNER_USER);
		// Select a user
		home.selectUserByEmail(partneruser);
		// Click edit link
		home.click(UserMgmt.EDIT);
		// Disable privilege "Add and manage accessories”
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// Navigate to accessory page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add Accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create accessory
		Hashtable<String,String> dataProduct = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), dataProduct);
		// Click add variant link
		home.click(AccessoryInfo.ADD_VARIANT);
		// Create variant
		Hashtable<String,String> dataVariant = TestData.variantDraft();
		home.addVariant(AddVariant.getHash(), dataVariant);
		// Logout user
		home.logout();
		/*
		 * ******************************************************************
		 */
		// 2. Log into DTS portal as a Partner user successfully
		home.login(partneruser, password);
		// 3. Navigate to “Home” page
		home.click(Xpath.LINK_PARTNER_HOME);
		/*
		 * VP: The “Add Accessory” link is disappears
		 */
		Assert.assertFalse(home.isElementPresent(PartnerHomePage.PARTNER_CATALOG_ADD_ACCESSORY));
		// 4. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		/*
		 * VP: The “Add Accessory” link is disappears
		 */
		Assert.assertFalse(home.isElementPresent(AccessoryMain.ADD_ACCESSORY));
		// 5. Select a non published accessory from the accessories table which has at least one accessory variant
		home.selectAnaccessorybyName(dataProduct.get("name"));
		/*
		 * VP: Verify that there is no edit link displayed
		 */
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.EDIT_MODE));
		// 6. Select an accessory variant from the variant list
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that there is no edit link displayed for an accessory variant
		 */
		Assert.assertFalse(home.isElementPresent(VariantInfo.EDIT_VARIANT));
		// Delete product
		home.click(PartnerVariantInfo.PRODUCT_LINK);
		home.doDelete(PartnerAccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the partner user cannot create new version for an accessory or accessory variant when the "Add and manage accessories” privilege is disabled
	 */
	@Test
	public void TCPPA_10(){
		result.addLog("ID : TCPPA_10: Verify that the partner user cannot create new version for an accessory or accessory variant when the 'Add and manage accessories' privilege is disabled");
		/*
			Pre-condition: The  "Add and manage accessories” privilege is disabled.
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to “Accessories” page
			4. Select a published accessory from the accessories table which has at least one published accessory variant
			VP: Verify that there is no “Create New Version” link displayed for a published accessory
			5. Select a published accessory variant from the variant list
			VP: Verify that there is no “Create New Version” link displayed for a published accessory variant
		*/
		/*
		 * Pre-condition: The  "Add and manage accessories” privilege is disabled and a published product which has at least one published variant is created
		 */
		// Log into DTS portal
		home.login(superUsername, superUserpassword);
		// Navigate to user page
		home.click(Xpath.LINK_USERS);
		// Select a user
		home.dtsSelectUserByEmail(partneruser);
		// Click edit link
		home.click(UserMgmt.EDIT);
		// Disable privilege "Add and manage accessories”
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// Navigate to accessory page
		home.click(Xpath.linkAccessories);
		// Click Add Accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create published product
		Hashtable<String,String> dataProduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct);
		// Click Add variant link
		home.click(AccessoryInfo.ADD_VARIANT);
		// Create variant
		Hashtable<String,String> dataVariant = TestData.variantPublish();
		home.createVariantPublish(AddVariant.getHash(), dataVariant);
		// Logout user
		home.logout();
		/*
		 * *****************************************************************
		 */
		// 2. Log into DTS portal as a Partner user successfully
		home.login(partneruser, password);
		// 3. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 4. Select a published accessory from the accessories table which has at least one published accessory variant
		home.selectAnaccessorybyName(dataProduct.get("name"));
		/*
		 * VP: Verify that there is no “Create New Version” link displayed for a published accessory
		 */
		Assert.assertFalse(home.isElementPresent(PartnerAccessoryInfo.UPDATE_PRODUCT_INFO));
		// 5. Select a published accessory variant from the variant list
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that there is no “Create New Version” link displayed for a published accessory variant
		 */
		Assert.assertFalse(home.isElementPresent(VariantInfo.CREATE_NEW_VERSION));
		// Delete product
		home.click(PartnerVariantInfo.PRODUCT_LINK);
		home.doDelete(PartnerAccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that user could publish accessory successfully when the “Publish and suspend accessories” privilege is enabled
	 */
	@Test
	public void TCPPA_11(){
		result.addLog("ID : TCPPA_11: Verify that user could publish accessory successfully when the 'Publish and suspend accessories' privilege is enabled");
		/*
			Pre-condition: The “Publish and suspend accessories” privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to “Accessories” page
			4. Click “Add Accessory” link
			5. Fill valid value into all required fields
			6. Upload all valid necessary files
			7. Click “Save” link
			8. Pass through two steps of Approval Process
			VP The “Published” button in step 3 “Publishing” of “Approval Process” is enabled 
			9. Click “Publish” button
		*/
		// 2. Log into DTS portal as a Partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 3. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 4. Click “Add Accessory” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields
		// 6. Upload all valid necessary files
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// 8. Pass through two steps of Approval Process
		// Logout user
		home.logout();
		// Re-login as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to Accessories page
		home.click(Xpath.linkAccessories);
		// Select accessory created above
		home.selectAnaccessorybyName(data.get("name"));
		// Select tuning rating
		home.click(AccessoryInfo.EDIT_MODE);
		home.selectFilterByName(AddAccessory.TUNING_RATING, "A");
		home.click(AddAccessory.SAVE);
		//Approve Tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.REQUEST_TUNING);
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Approve Marketing
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		home.click(AccessoryInfo.APPROVE_MARKETING);
		// Logout user
		home.logout();
		// Re-login as partner user above
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select accessory created above
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP The “Published” button in step 3 “Publishing” of “Approval Process” is enabled 
		 */
		Assert.assertEquals(home.getAtribute(PartnerAccessoryInfo.PUBLISH, "class"), "button button-warning");
		// 9. Click “Publish” button
		home.click(PartnerAccessoryInfo.PUBLISH);
		/*
		 * Verify that New accessory could be upload successfully
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.PUBLISH_STATUS), "PUBLISHED");
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}	
	
	/*
	 * Verify that user could not publish product when the “Publish and suspend Products” privilege is disabled
	 */
	@Test
	public void TCPPA_12(){
		result.addLog("ID : TCPPA_12: Verify that user could not publish product when the “Publish and suspend Products” privilege is disabled");
		/*
			Pre-condition: The “Publish and suspend Products” privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to “Accessories” page
			4. Click “Add product” link
			5. Fill valid value into all required fields
			6. Upload all valid necessary files
			7. Click “Save” link
			8. Pass through two steps of Approval Process
		*/
		/*
		 * Pre-condition: The “Publish and suspend Products” privilege is disabled
		 */
		// Log in DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to user page
		home.click(Xpath.LINK_USERS);
		// Select a user from table
		home.dtsSelectUserByEmail(partneruser);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable “Publish and suspend Products” privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Publish_and_suspend_products);
		home.click(AddUser.SAVE);
		// Log out
		home.logout();
		/*
		 * *******************************************************************
		 */
		// 2. Log into DTS portal as a Partner user successfully
		home.login(partneruser, password);
		// 3. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 4. Click “Add product” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields
		// 6. Upload all valid necessary files
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// 8. Pass through two steps of Approval Process
		home.click(AccessoryInfo.REQUEST_TUNING);
		// Log out
		home.logout();
		// Log in DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Select product created above
		home.selectAnaccessorybyName(data.get("name"));
		// Select tuning rating
		home.click(AccessoryInfo.EDIT_MODE);
		home.selectOptionByName(AddAccessory.TUNING_RATING, "A");
		home.click(AddAccessory.SAVE);
		// Approve tuning
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Approve marketing
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		home.click(AccessoryInfo.APPROVE_MARKETING);
		// Log out
		home.logout();
		// Log in DTS portal as Partner user above
		home.login(partneruser, password);
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select product created above
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * The “Published” button in step 3 “Publishing” of “Approval Process” is disabled 
		 */
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.PUBLISH));
	}
	
	/*
	 * Verify that user could publish accessory variant successfully when the “Publish and suspend accessories” privilege is enabled
	 */
	@Test
	public void TCPPA_13(){
		result.addLog("ID : TCPPA_13: Verify that user could publish accessory variant successfully when the “Publish and suspend accessories” privilege is enabled");
		/*
			Pre-condition: The “Publish and suspend accessories” privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to “Accessories” page
			4. Select a published accessory from accessories table
			5. Click “Add Variant” link
			6. Fill valid value into all required fields
			7. Upload all valid necessary files
			8. Click “Save” link
			9. Pass through two steps of Approval Process of accessory variant
			VP The “Published” button in step 3 “Publishing” of “Approval Process” is enabled 
			10 Click “Publish” button
		*/
		/*
		 * Pre-condition: Create a published accessory
		 */
		// Login as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to accessory page
		home.click(Xpath.linkAccessories);
		// Click Add Accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create published product 
		Hashtable<String,String> dataProduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(),dataProduct);
		// Create Add new variant link
		home.click(PartnerAccessoryInfo.ADD_VARIANT);
		// Create variant
		Hashtable<String, String> dataVariant = TestData.variantPublishUseParent();
		home.addVariant(AddVariant.getHash(), dataVariant);
		// Approve marketing
		home.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		home.click(VariantInfo.REQUEST_MARKETING_REVIEW);
		home.click(VariantInfo.APPROVE_MARKETING);
		// Logout user
		home.logout();
		/*
		 * ***********************************************************************
		 */
		// 2. Log into DTS portal as a Partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 3. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 4. Select a published accessory from the accessories table
		home.selectAnaccessorybyName(dataProduct.get("name"));
		// 5. Click “Add Variant” link
		// 6. Fill valid value into all required fields
		// 7. Upload all valid necessary files
		// 8. Click “Save” link
		// 9. Pass through two steps of Approval Process of accessory variant
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP The “Published” button in step 3 “Publishing” of “Approval Process” is enabled
		 */
		Assert.assertEquals(home.getAtribute(VariantInfo.PUBLISH, "class"), "button button-warning");
		// 10 Click “Publish” button
		home.click(VariantInfo.PUBLISH);
		/*
		 * Verify new accessory variant could be upload successfully
		 */
		Assert.assertEquals(home.getTextByXpath(PartnerVariantInfo.PUBLISHED_STATUS), "Published");
		// Delete product
		home.click(PartnerVariantInfo.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that user could not publish product when the “Publish and suspend Products” privilege is disabled
	 */
	@Test
	public void TCPPA_14(){
		result.addLog("ID : TCPPA_14: Verify that user could not publish product when the “Publish and suspend Products” privilege is disabled");
		/*
			Pre-condition: The “Publish and suspend Products” privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to “Accessories” page
			4. Select a published product from Products table
			5. Click “Add Variant” link
			6. Fill valid value into all required fields
			7. Upload all valid necessary files
			8. Click “Save” link
			9. Pass through two steps of Approval Process of product variant
		*/
		/*
		 * Pre-condition: The “Publish and suspend Products” privilege is disabled and one published product is created
		 */
		// Log in DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to user page
		home.click(Xpath.LINK_USERS);
		// Select a user from table
		home.dtsSelectUserByEmail(partneruser);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable “Publish and suspend Products” privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Publish_and_suspend_products);
		home.click(AddUser.SAVE);
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create publish product
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), data);
		// Log out
		home.logout();
		/*
		 * *******************************************************************
		 */
		// 2. Log into DTS portal as a Partner user successfully
		home.login(partneruser, password);
		// 3. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 4. Select a published product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		// 5. Click “Add Variant” link
		home.click(PartnerAccessoryInfo.ADD_VARIANT);
		// 6. Fill valid value into all required fields
		// 7. Upload all valid necessary files
		// 8. Click “Save” link
		Hashtable<String, String> dataVariant = TestData.variantPublishUseParent();
		home.addVariant(AddVariant.getHash(), dataVariant);
		// 9. Pass through two steps of Approval Process of product variant
		home.logout();
		// Log in DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Select published product created above
		home.selectAnaccessorybyName(data.get("name"));
		// Select variant created above
		home.selectAVariantbyName(dataVariant.get("name"));
		// Select tuning rating
		home.click(VariantInfo.EDIT_VARIANT);
		home.selectOptionByName(AddVariant.TUNING_RATING, "A");
		home.click(AddVariant.SAVE);
		// Approve marketing
		home.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		home.click(VariantInfo.REQUEST_MARKETING_REVIEW);
		home.click(VariantInfo.APPROVE_MARKETING);
		// Log out
		home.logout();
		// Log in DTS portal as Partner user above
		home.login(partneruser, password);
		// Navigate to product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select published product created above
		home.selectAnaccessorybyName(data.get("name"));
		// Select variant created above
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * Verify that The “Published” button in step 3 “Publishing” of “Approval Process” is disabled
		 */
		Assert.assertFalse(home.isElementPresent(VariantInfo.PUBLISH));
	}
	
	/*
	 * Verify that user could delete accessory and its variant successfully when the “Publish and suspend accessories” privilege is enabled.
	 */
	@Test
	public void TCPPA_15(){
		result.addLog("ID : TCPPA_15: Verify that user could delete accessory and its variant successfully when the “Publish and suspend accessories” privilege is enabled.");
		/*
		 	Pre-condition: The “Publish and suspend Products” privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to “Accessories” page
			4. Select an product from Products table which has at least one variant
			VP: Verify that the “Delete” link is displayed in product detail page
			5. Select a variant from variant list
			VP: Verify that the “Delete” link is displayed in product variant detail page
			6. Click “Delete” link in product variant detail page
			VP: Verify that the product variant is deleted successfully and the product detail page is displayed
			7. Click “Delete” link in product detail page
			VP: Verify that the product is deleted successfully.
		 */
		/*
		 *  Pre-Condition: Create new accessory has one variant 
		 */
		// Log into DTS portal as partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click add Accessories link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create accessory
		Hashtable<String,String> dataProduct = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), dataProduct);
		// Click on Add New Variant link
		home.click(AccessoryInfo.ADD_VARIANT);
		// Create variant
		Hashtable<String,String> dataVariant = TestData.variantDraft();
		home.addVariant(AddVariant.getHash(), dataVariant);
		/*
		 * ****************************************************************
		 */
		// 3. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 4. Select an accessory from accessories table which has at least one variant
		home.selectAnaccessorybyName(dataProduct.get("name"));
		/*
		 * VP: Verify that the “Delete” link is displayed in accessory detail page
		 */
		Assert.assertTrue(home.existsElement(AccessoryInfo.DELETE));
		// 5. Select a variant from variant list
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that the “Delete” link is displayed in accessory variant detail page
		 */
		Assert.assertTrue(home.existsElement(PartnerVariantInfo.DELETE_VARIANT));
		// 6. Click “Delete” link in accessory variant detail page
		home.doDelete(PartnerVariantInfo.DELETE_VARIANT);
		/*
		 * VP: Verify that the accessory variant is deleted successfully and the accessory detail page is displayed
		 */
		Assert.assertFalse(home.checkVariantExistbyName(AccessoryInfo.MODEL_VARIANTS, dataVariant.get("name")));
		// 7. Click “Delete” link in accessory detail page
		home.doDelete(AccessoryInfo.DELETE);
		/*
		 * VP: Verify that the accessory is deleted successfully.
		 */
		Assert.assertFalse(home.checkAnAccessoryExistByName(dataProduct.get("name")));
	}
	
	/*
	 * Verify that user could not delete accessory and its variant successfully when the “Publish and suspend accessories” privilege is disabled
	 */
	@Test
	public void TCPPA_16(){
		result.addLog("ID : TCPPA_16: Verify that user could not delete accessory and its variant successfully when the “Publish and suspend accessories” privilege is disabled");
		/*
			Pre-condition: The “Publish and suspend accessories” privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to “Accessories” page
			4. Select an accessory from accessories table which has at least one variant
			VP: Verify that the “Delete” link is not displayed in accessory detail page
			5. Select a variant from variant list
			VP: Verify that the “Delete” link is not displayed in accessory variant detail page
		*/
		/*
		 * Pre-condition: The “Publish and suspend accessories” privilege is disabled and a product which has at lease one variant is created
		 */
		// Log into DTS portal
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to user page
		home.click(Xpath.LINK_PARTNER_USER);
		// Select a user
		home.selectUserByEmail(partneruser);
		// Click edit link
		home.click(UserMgmt.EDIT);
		// Disable privilege "Publish and suspend accessories”
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Publish_and_suspend_products);
		home.click(AddUser.SAVE);
		// Navigate to accessory page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add Accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create accessory
		Hashtable<String, String> dataProduct = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(),dataProduct);
		// Click Add variant link
		home.click(AccessoryInfo.ADD_VARIANT);
		// Create variant
		Hashtable<String,String> dataVariant = TestData.variantDraft();
		home.addVariant(AddVariant.getHash(), dataVariant);
		// Logout user
		home.logout();
		/*
		 * **************************************************************
		 */
		// 2. Log into DTS portal as a Partner user successfully
		home.login(partneruser, password);
		// 3. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 4. Select an accessory from accessories table which has at least one variant
		home.selectAnaccessorybyName(dataProduct.get("name"));
		/*
		 * VP: Verify that the “Delete” link is not displayed in accessory detail page
		 */
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.DELETE));
		// 5. Select a variant from variant list
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that the “Delete” link is not displayed in accessory variant detail page
		 */
		Assert.assertFalse(home.isElementPresent(VariantInfo.DELETE_VARIANT));
	}	
	
	/*
	 * Verify that user could suspend accessory successfully when the “Publish and suspend accessories” privilege is enabled
	 */
	@Test
	public void TCPPA_17(){
		result.addLog("ID : TCPPA_17: Verify that user could suspend accessory successfully when the “Publish and suspend accessories” privilege is enabled");
		/*
			Pre-condition: The “Publish and suspend accessories” privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to “Accessories” page
			4. Click “Add Accessory” link
			5. Fill valid value into all required fields
			6. Upload all valid necessary files
			7. Click “Save” link
			VP: Verify that the new accessory detail page is displayed and the “Suspend” link is not displayed
			8. Pass through three steps of Approval Process
			9. Click “Publish” button
			VP: Verify that the accessory is published successfully and the “Suspend” link is displayed
			10. Click “Suspend” link
		*/
		// 2. Log into DTS portal as a Partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 3. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 4. Click “Add Accessory” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields
		// 6. Upload all valid necessary files
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: Verify that the new accessory detail page is displayed and the “Suspend” link is not displayed
		 */
		Assert.assertEquals(home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult(), "Pass");
		Assert.assertFalse(home.isElementPresent(PartnerAccessoryInfo.SUSPEND));
		// 8. Pass through three steps of Approval Process
		// Logout user
		home.logout();
		// Re-login as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to Accessories page
		home.click(Xpath.linkAccessories);
		// Select accessory created above
		home.selectAnaccessorybyName(data.get("name"));
		// Select tuning rating
		home.click(AccessoryInfo.EDIT_MODE);
		home.selectFilterByName(AddAccessory.TUNING_RATING, "A");
		home.click(AddAccessory.SAVE);
		// Approve Tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.REQUEST_TUNING);
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Approve Marketing
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		home.click(AccessoryInfo.APPROVE_MARKETING);
		// Logout user
		home.logout();
		// Re-login as partner user above
		home.login(superpartneruser,superpartnerpassword);
		// Navigate to Accessories page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select accessory created above
		home.selectAnaccessorybyName(data.get("name"));
		// 9. Click “Publish” button
		home.click(AccessoryInfo.PUBLISH);
		/*
		 * VP: Verify that the accessory is published successfully and the “Suspend” link is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.PUBLISH_STATUS), "PUBLISHED");
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.SUSPEND));
		// 10. Click “Suspend” link
		home.click(AccessoryInfo.SUSPEND);
		home.switchWindowClickOption("Suspend");
		/*
		 * Verify that the accessory is suspended successfully
		 */
		home.selectFilterByName(AccessoryMain.PRODUCT_FILTER, "Suspended");
		Assert.assertTrue(home.checkAnAccessoryExistByName(data.get("name")));
		// Delete product
		home.selectAnaccessorybyName(data.get("name"));
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that user could suspend accessory variant successfully when the “Publish and suspend accessories” privilege is enabled
	 */
	@Test
	public void TCPPA_18(){
		result.addLog("ID : TCPPA_18: Verify that user could suspend accessory variant successfully when the “Publish and suspend accessories” privilege is enabled");
		/*
			Pre-condition: The “Publish and suspend accessories” privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to “Accessories” page
			4. Select a published accessory from accessories table
			5. Click “Add Variant” link in accessory detail page
			6. Fill valid value into all required fields
			7. Upload all valid necessary files
			8. Click “Save” link
			VP: Verify that the new accessory variant detail page is displayed and the “Suspend” link is not displayed
			9. Pass through three steps of Approval Process
			10. Click “Publish” button
			VP: Verify that the accessory variant is published successfully and the “Suspend” link is displayed
			11. Click “Suspend” link
		*/
		/*
		 * Pre-condition: Create a published product 
		 */
		// Log into portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to accessory page
		home.click(Xpath.linkAccessories);
		// Click Add Accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create accessory
		Hashtable<String,String> dataProduct = TestData.accessoryPublish();
		String AccessoryName = dataProduct.get("name");
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct);
		// Logout user
		home.logout();
		/*
		 * **************************************************************
		 */
		// 2. Log into DTS portal as a Partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 3. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 4. Select a published accessory from accessories table
		home.selectAnaccessorybyName(AccessoryName);
		// 5. Click “Add Variant” link in accessory detail page
		home.click(AccessoryInfo.ADD_VARIANT);
		// 6. Fill valid value into all required fields
		// 7. Upload all valid necessary files
		// 8. Click “Save” link
		Hashtable<String, String> dataVariant = TestData.variantPublishUseParent();
		String variantName = dataVariant.get("name");
		home.addVariant(AddVariant.getHash(), dataVariant);
		/*
		 * VP: Verify that the new accessory variant detail page is displayed and the “Suspend” link is not displayed
		 */
		Assert.assertEquals(home.existsElement(PartnerVariantInfo.getElementsInfo()).getResult(), "Pass");
		Assert.assertFalse(home.isElementPresent(PartnerVariantInfo.SUSPEND));
		// 9. Pass through three steps of Approval Process
		// Logout user
		home.logout();
		// Re-login to DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to accessory page
		home.click(Xpath.linkAccessories);
		// Select variant accessory created above
		home.selectAnaccessorybyName(AccessoryName);
		home.selectAVariantbyName(variantName);
		// Select tuning rating
		home.click(VariantInfo.EDIT_VARIANT);
		home.selectFilterByName(AddVariant.TUNING_RATING, "A");
		home.click(AddVariant.SAVE);
		// Approve Marketing
		home.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		home.click(VariantInfo.REQUEST_MARKETING_REVIEW);
		home.click(VariantInfo.APPROVE_MARKETING);
		// Logout user
		home.logout();
		// Re-login to DTS portal as Partner user above
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to Accessories page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select accessory variant that has passed two steps of Approval process
		home.selectAnaccessorybyName(AccessoryName);
		home.selectAVariantbyName(variantName);
		// 10. Click “Publish” button
		home.click(VariantInfo.PUBLISH);
		/*
		 * VP: Verify that the accessory variant is published successfully and the “Suspend” link is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.PUBLISHED_STATUS), "Published");
		Assert.assertTrue(home.isElementPresent(VariantInfo.SUSPEND));
		// 11. Click “Suspend” link
		home.click(VariantInfo.SUSPEND);
		home.selectConfirmationDialogOption("Suspend");
		/*
		 * Verify that the accessory variant is suspended successfully
		 */
		home.selectAnaccessorybyName(AccessoryName);
		home.selectAVariantbyName(variantName);
		Assert.assertEquals(home.getTextByXpath(VariantInfo.PUBLISHED_STATUS), "Suspended");
		// Delete product
		home.click(VariantInfo.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that user could not suspend accessory and accessory variant successfully when the “Publish and suspend accessories” privilege is disabled
	 */
	@Test
	public void TCPPA_19(){
		result.addLog("ID : TCPPA_19: Verify that user could not suspend accessory and accessory variant successfully when the “Publish and suspend accessories” privilege is disabled");
		/*
			Pre-condition: The “Publish and suspend accessories” privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to “Accessories” page
			4. Select a published accessory from accessories table which have at least one published accessory variant
			VP: Verify that the “Suspend” link is not displayed in accessory detail page
			5. Select a published accessory variant from accessory variants list
			VP: Verify that the “Suspend” link is not displayed in accessory variant detail page
		*/
		/*
		 * Pre-condition: The “Publish and suspend accessories” privilege is disabled and a publish product which has at least one variant is created
		 */
		// Log into DTS portal
		home.login(superUsername, superUserpassword);
		// Navigate to user page
		home.click(Xpath.LINK_USERS);
		// Select a user
		home.dtsSelectUserByEmail(partneruser);
		// Click edit link
		home.click(UserMgmt.EDIT);
		// Disable privilege "Publish and suspend accessories”
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Publish_and_suspend_products);
		home.click(AddUser.SAVE);
		// Navigate to accessory page
		home.click(Xpath.linkAccessories);
		// Click Add Accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create published product
		Hashtable<String,String> dataProduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct);
		// Click Add variant link
		home.click(AccessoryInfo.ADD_VARIANT);
		// Create published variant
		Hashtable<String,String> dataVariant = TestData.variantPublish();
		home.createVariantPublish(AddVariant.getHash(),dataVariant);
		// Logout user
		home.logout();
		/*
		 * **************************************************************
		 */
		// 2. Log into DTS portal as a Partner user successfully
		home.login(partneruser, password);
		// 3. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 4. Select a published accessory from accessories table which have at least one published accessory variant
		home.selectAnaccessorybyName(dataProduct.get("name"));
		/*
		 * VP: Verify that the “Suspend” link is not displayed in accessory detail page
		 */
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.SUSPEND));
		// 5. Select a published accessory variant from accessory variants list
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that the “Suspend” link is not displayed in accessory variant detail page
		 */
		Assert.assertFalse(home.isElementPresent(VariantInfo.SUSPEND));
	}
	
	/*
	 * Verify that the partner user could request DTS tuning when the “Request accessory tunings” privilege is enabled
	 */
	@Test
	public void TCPPA_20(){
		result.addLog("ID : TCPPA_20: Verify that the partner user could request DTS tuning when the “Request accessory tunings” privilege is enabled");
		/*
			Pre-condition: The “Request accessory tunings” privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to “Accessories” page
			4. Click “Add Accessory” link
			5. Fill valid value into all required fields
			6. Do not upload tuning file
			7. Click “Save” link
			VP: Verify that the accessory detail page is displayed, the tuning approval state is “Unsubmitted” and the action link is “Request DTS tuning”
			8. Click “Request DTS tuning” link
			9. Submit for the request tuning form successfully
		*/
		// 2. Log into DTS portal as a Partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 3. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 4. Click “Add Accessory” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields
		// 6. Do not upload tuning file
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: Verify that the accessory detail page is displayed, the tuning approval state is “Unsubmitted” and the action link is “Request DTS tuning”
		 */
		Assert.assertEquals(home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "UNSUBMITTED");
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_TUNING), "Request DTS Tuning");
		// 8. Click “Request DTS tuning” link
		home.click(AccessoryInfo.REQUEST_TUNING);
		// 9. Submit for the request tuning form successfully
		home.click(TuningRequestForm.AGREE);
		home.click(TuningRequestForm.SUBMIT);
		/*
		 * Verify that the tuning approval state is changed to “DTS Request Pending” and the action link is “Cancel Request”
		 */
		home.click(TuningRequestForm.PRODUCT_LINK);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "DTS REQUEST PENDING");
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.CANCEL_REQUEST_TUNING), "Cancel Request");
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the partner user could not request DTS tuning when the “Request accessory tunings” privilege is disabled
	 */
	@Test
	public void TCPPA_21(){
		result.addLog("ID : TCPPA_21: Verify that the partner user could not request DTS tuning when the “Request accessory tunings” privilege is disabled");
		/*
			Pre-condition: The “Request accessory tunings” privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Navigate to “Accessories” page
			4. Click “Add Accessory” link
			5. Fill valid value into all required fields
			6. Do not upload tuning file
			7. Click “Save” link
		*/
		/*
		 * Pre-condition: The “Request accessory tunings” privilege is disabled
		 */
		// Log into DTS portal as Partner user
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to user page
		home.click(Xpath.LINK_PARTNER_USER);
		// Select a user
		home.selectUserByEmail(partneruser);
		// Click edit link
		home.click(UserMgmt.EDIT);
		// Disable privilege "Request accessory tunings”
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Request_product_tunings);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		/*
		 * *************************************************************
		 */
		// 2. Log into DTS portal as a Partner user successfully
		home.login(partneruser, password);
		// 3. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 4. Click “Add Accessory” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields
		// 6. Do not upload tuning file
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * Verify that the accessory detail page is displayed, the tuning approval state is “Unsubmitted” and there is no action link displayed below.
		 */
		Assert.assertEquals(home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.UNSUBMITTED);
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.PARTNER_ACTIONS_STEP_1));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the partner user could approve the DTS tuning file when the “Request accessory tunings” privilege is enabled
	 */
	@Test
	public void TCPPA_22(){
		result.addLog("ID : TCPPA_22: Verify that the partner user could approve the DTS tuning file when the “Request accessory tunings” privilege is enabled");
		/*
			Pre-condition: The “Request accessory tunings” privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “Accessories” page
			4. Click “Add Accessory” link
			5. Fill valid value into all required fields
			6. Upload tuning file successfully
			7. Click “Save” link
			VP: Verify that the accessory detail page is displayed
			8. Log out DTS portal
			9. Log into DTS portal as a Partner user successfully
			10. Navigate to “Accessories” page
			11. Select above accessory from the accessories table
			VP: Verify that the tuning approval status is “PENDING PARTNER APPROVAL” and its action links are “Approve Tuning” and “Decline Tuning”
			12. Click “Approve Tuning” link
		*/
		// 2. Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// 3. Navigate to “Accessories” page
		home.click(Xpath.linkAccessories);
		// 4. Click “Add Accessory” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields
		// 6. Upload tuning file successfully
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: Verify that the accessory detail page is displayed
		 */
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as a Partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 10. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 11. Select above accessory from the accessories table
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the tuning approval status is “PENDING PARTNER APPROVAL” and its action links are “Approve Tuning” and “Decline Tuning”
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.PENDING_PARTNER_APPROVAL);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.APPROVE_TUNING), AccessoryInfo.APPROVE_TUNING_ACTION_STRING);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DECLINE_TUNING), AccessoryInfo.DECLINE_TUNING_ACTION_STRING);
		// 12. Click “Approve Tuning” link
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * Verify that the tuning approval state is changed to “Approved” and the action links are “Download Certification Badges”
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.APPROVED);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DOWNLOAD_CERTIFICATE), AccessoryInfo.DOWNLOAD_CERT_BADGES_ACTION_STRING);
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the partner user could decline the DTS tuning file when the “Request accessory tunings” privilege is enabled
	 */
	@Test
	public void TCPPA_23(){
		result.addLog("ID : TCPPA_23: Verify that the partner user could decline the DTS tuning file when the “Request accessory tunings” privilege is enabled");
		/*
			Pre-condition: The “Request accessory tunings” privilege is enabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “Accessories” page
			4. Click “Add Accessory” link
			5. Fill valid value into all required fields
			6. Upload tuning file successfully
			7. Click “Save” link
			VP: Verify that the accessory detail page is displayed
			8. Log out DTS portal
			9. Log into DTS portal as a Partner user successfully
			10. Navigate to “Accessories” page
			11. Select above accessory from the accessories table
			VP: Verify that the tuning approval status is “PENDING PARTNER REVIEW” and its action links are “Approve Tuning” and “Decline Tuning”
			12. Click “Decline Tuning” link
		*/
		// 2. Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// 3. Navigate to “Accessories” page
		home.click(Xpath.linkAccessories);
		// 4. Click “Add Accessory” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields
		// 6. Upload tuning file successfully
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: Verify that the accessory detail page is displayed
		 */
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as a Partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 10. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 11. Select above accessory from the accessories table
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the tuning approval status is “PENDING PARTNER APPROVAL” and its action links are “Approve Tuning” and “Decline Tuning”
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.PENDING_PARTNER_APPROVAL);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.APPROVE_TUNING), AccessoryInfo.APPROVE_TUNING_ACTION_STRING);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DECLINE_TUNING), AccessoryInfo.DECLINE_TUNING_ACTION_STRING);
		// 12. Click “Decline Tuning” link
		home.click(AccessoryInfo.DECLINE_TUNING);
		/*
		 * Verify that the tuning approval state is changed to “Declined” and the action links is “Request Revised Tuning"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.DECLINED);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_REVISED_TUNING), AccessoryInfo.TUNING_REQUEST_REVISED);
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);}
	
	/*
	 * Verify that the partner user could not approve or decline the DTS tuning file when the “Request accessory tunings” privilege is disabled
	 */
	@Test
	public void TCPPA_24(){
		result.addLog("ID : TCPPA_24: Verify that the partner user could not approve or decline the DTS tuning file when the “Request accessory tunings” privilege is disabled");
		/*
			Pre-condition: The “Request accessory tunings” privilege is disabled
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “Accessories” page
			4. Click “Add Accessory” link
			5. Fill valid value into all required fields
			6. Upload tuning file successfully
			7. Click “Save” link
			VP: Verify that the accessory detail page is displayed
			8. Log out DTS portal
			9. Log into DTS portal as a Partner user successfully
			10. Navigate to “Accessories” page
			11. Select above accessory from the accessories table
		*/
		
		/*
		 * Pre-condition: The “Request accessory tunings” privilege is disabled
		 */
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to user page
		home.click(Xpath.LINK_USERS);
		// Select a user
		home.dtsSelectUserByEmail(partneruser);
		// Click edit link
		home.click(UserMgmt.EDIT);
		// Disable privilege "Request accessory tunings”
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Request_product_tunings);
		home.click(AddUser.SAVE);
		/*
		 * ********************************************************
		 */
		// 3. Navigate to “Accessories” page
		home.click(Xpath.linkAccessories);
		// 4. Click “Add Accessory” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Fill valid value into all required fields
		// 6. Upload tuning file successfully
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: Verify that the accessory detail page is displayed
		 */
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as a Partner user successfully
		home.login(partneruser, password);
		// 10. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 11. Select above accessory from the accessories table
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * Verify that the tuning approval status is “PENDING PARTNER APPROVAL” but the “Approve Tuning” and “Decline Tuning” links are not displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "PENDING PARTNER APPROVAL");
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.APPROVE_TUNING));
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.DECLINE_TUNING));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	
	/*
	 * Verify that the partner user is unable to add, edit and create new version of product and variant of  unassigned brand when the  "Add and manage Products” privilege is enabled.
	 */
	@Test
	public void TCPPA_25(){
		result.addLog("ID : TCPPA_25: Verify that the partner user is unable to add, edit and create new version of product and variant of  unassigned brand when the  'Add and manage Products' privilege is enabled.");
		/*
		 	Pre-condition: The "Add and manage products" privilege of partner user is enabled but only assign for only one specific brand
			
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Enable the “Add and manage Products” privilege but assign for a specific brand
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			9. Navigate to "Products" page
			VP. Verify that the “Add product” link is displayed inside “Actions” module and the Products table is also displayed.
			10. Click on “Add product” link
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
		home.login(superUsername, superUserpassword);
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.dtsSelectUserByEmail(partneruser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Add and manage Products” privilege but assign for a specific brand
		home.enableAllPrivilege();
		home.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, partner_brand_name_2);
		home.click(AddUser.SAVE);
		//Create draft & pulished products/variants
		home.click(Xpath.linkAccessories);
		home.click(AccessoryMain.ADD_ACCESSORY);
		//Create draft product
		Hashtable<String,String> drafproduct = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), drafproduct);
		//Create published product
		home.click(Xpath.linkAccessories);
		home.click(AccessoryMain.ADD_ACCESSORY);
		Hashtable<String,String> publishproduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), publishproduct);
		//Create draft variants
		home.click(Xpath.linkAccessories);
		home.selectAnaccessorybyName(publishproduct.get("name"));
		home.click(AccessoryInfo.ADD_VARIANT);
		Hashtable<String,String> drafvariant = TestData.variantDraft();
		home.addVariant(AddVariant.getHash(), drafvariant);
		//Create a published variant
		home.click(VariantInfo.PRODUCT_LINK);
		home.click(AccessoryInfo.ADD_VARIANT);
		Hashtable<String,String> publishvariant = TestData.variantPublish();
		home.createVariantPublish(AddVariant.getHash(), publishvariant);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner user successfully
		home.login(partneruser, password);
		// 9. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 10. Click on “Add product” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// VP. Verify that the Brand dropdown is not displayed
		Assert.assertFalse(home.isElementPresent(AddAccessory.BRAND));
		// 11. Click Cancel link
		home.click(AddAccessory.CANCEL);
		// 12. Select a draft product from products list which does not belong to the management of partner user
		home.selectAnaccessorybyName(drafproduct.get("name"));
		// VP. Verify that the Edit link is not displayed
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.EDIT_MODE));
		// 13. Navigate to Products list page again
		home.click(Xpath.linkAccessories);
		// 14. Select a published product from products list which does not belong to the management of partner user
		home.selectAnaccessorybyName(publishproduct.get("name"));
		// VP: Verify that the Create new version link is not displayed.
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.EDIT_MODE));
		// 15. Select a draft variant from variant list
		home.selectAVariantbyName(drafvariant.get("name"));
		// VP: The Edit link is not displayed in variant info page
		Assert.assertFalse(home.isElementPresent(VariantInfo.EDIT_VARIANT));
		// 16. Navigate back to product info page
		home.click(VariantInfo.PRODUCT_LINK);
		// 17. Select a published variant from variants list
		home.selectAVariantbyName(publishvariant.get("name"));
		// Verify that the Create new version link is not displayed.
		Assert.assertFalse(home.isElementPresent(VariantInfo.CREATE_NEW_VERSION));
		
		//Teardown: Delete products and variants
		home.logout();
		home.login(superUsername, superUserpassword);
		home.deleteAnaccessorybyName(drafproduct.get("name"));
		home.deleteAnaccessorybyName(publishproduct.get("name"));
		home.click(Xpath.LINK_USERS);
		home.dtsSelectUserByEmail(partneruser);
		home.click(UserMgmt.EDIT);
		home.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, all_brand);
	}
	
	/*
	 * Verify that the partner user is unable to publish, suspend, restore or delete product and variant of  unassigned brand when the  "Publish and suspend products” privilege is enabled.
	 */
	@Test
	public void TCPPA_26(){
		result.addLog("ID : TCPPA_26: Verify that the partner user is unable to publish, suspend, restore or delete product and variant of  unassigned brand when the  'Publish and suspend products' privilege is enabled.");
		/*
		 	Pre-condition: Pre-condition: The "Publish and suspend products" privilege of partner user is enabled but only assign for only one specific brand
			
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Enable the “Publish and suspend products” privilege but assign for a specific brand
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
		
		// 2. Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.dtsSelectUserByEmail(partneruser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Publish and suspend products” privilege but assign for a specific brand
		home.enableAllPrivilege();
		home.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, partner_brand_name_2);
		home.click(AddUser.SAVE);
		//Create ready to publish, published and suspended products/variants
		home.click(Xpath.linkAccessories);
		home.click(AccessoryMain.ADD_ACCESSORY);
		//Create products and variants
		//Create ready to publish product
		home.click(Xpath.linkAccessories);
		home.click(AccessoryMain.ADD_ACCESSORY);
		Hashtable<String,String> readypublishproduct = TestData.accessoryPublish();
		home.addAccessoriesPartner(AddAccessory.getHash(), readypublishproduct);
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		home.click(AccessoryInfo.APPROVE_MARKETING);
		
		//Suspend a published product
		home.click(Xpath.linkAccessories);
		home.click(AccessoryMain.ADD_ACCESSORY);
		Hashtable<String,String> suspendedproduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), suspendedproduct);
		home.click(AccessoryInfo.SUSPEND);
		home.selectConfirmationDialogOption("Suspend");
		
		//Create a published product
		home.click(Xpath.linkAccessories);
		home.click(AccessoryMain.ADD_ACCESSORY);
		Hashtable<String,String> publisedhproduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), publisedhproduct);
		// Create a ready to publish variant
		home.click(AccessoryInfo.ADD_VARIANT);
		Hashtable<String,String> readypublishvariant = TestData.variantPublishUseParent();
		home.addVariant(AddVariant.getHash(), readypublishvariant);
		home.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		home.click(VariantInfo.REQUEST_MARKETING_REVIEW);
		home.click(VariantInfo.APPROVE_MARKETING);
		
		//Suspend a published variant
		home.click(VariantInfo.PRODUCT_LINK);
		home.click(AccessoryInfo.ADD_VARIANT);
		Hashtable<String,String> suspendedvariant = TestData.variantPublishUseParent();
		home.addVariant(AddVariant.getHash(), suspendedvariant);
		home.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		home.click(VariantInfo.REQUEST_MARKETING_REVIEW);
		home.click(VariantInfo.APPROVE_MARKETING);
		home.click(VariantInfo.PUBLISH);
		home.click(VariantInfo.SUSPEND);
		home.selectConfirmationDialogOption("Suspend");
		
		//Create a published variant
		home.selectAnaccessorybyName(publisedhproduct.get("name"));
		home.click(AccessoryInfo.ADD_VARIANT);
		Hashtable<String,String> publishedvariant = TestData.variantPublishUseParent();
		home.addVariant(AddVariant.getHash(), publishedvariant);
		home.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		home.click(VariantInfo.REQUEST_MARKETING_REVIEW);
		home.click(VariantInfo.APPROVE_MARKETING);
		home.click(VariantInfo.PUBLISH);
		
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner user successfully
		home.login(partneruser, password);
		// 9. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 10. Select a ready to publish product which is out of brand privilege from products list
		home.selectAnaccessorybyName(readypublishproduct.get("name"));
		// VP. Verify that the "Publish" button is disabled and the "Delete" link is not displayed
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.PUBLISH));
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.DELETE));
		// 11. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 12. Set the product filter to "Suspended"
		home.selectFilterByName(AccessoryMain.PRODUCT_FILTER, Constant.ACCESSORY_SUSPENDED);
		// 13. Select a publish product which is out of brand privilege from products list
		home.selectAnaccessorybyName(suspendedproduct.get("name"));
		// VP. Verify that the "Restore" link is not displayed.
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.UNSUSPEND));
		// 14. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 15. Select a published product which is out of brand privilege from products list
		home.selectAnaccessorybyName(publisedhproduct.get("name"));
		// VP. Verify that the "Suspend" and the "Delete" links are not displayed
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.SUSPEND));
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.DELETE));
		// 16. Select a ready to publish variant from variants list
		home.selectAVariantbyName(readypublishvariant.get("name"));
		// VP. Verify that the "Publish" button is disabled and the "Delete" link is not displayed
		Assert.assertFalse(home.isElementPresent(VariantInfo.PUBLISH));
		Assert.assertFalse(home.isElementPresent(VariantInfo.DELETE_VARIANT));
		// 17. Back to product detail page
		home.click(VariantInfo.PRODUCT_LINK);
		// 18. Select a suspended variant from variants list
		home.selectAVariantbyName(suspendedvariant.get("name"));
		// VP. Verify that the "Un-suspend" link is not displayed
		Assert.assertFalse(home.isElementPresent(VariantInfo.UNSUSPEND));
		// 19. Back to product detail page
		home.click(VariantInfo.PRODUCT_LINK);
		//20. Select a published variant from variants list
		home.selectAVariantbyName(publishedvariant.get("name"));
		//VP. Verify that the "Suspend" and the "Delete" links are not displayed
		Assert.assertFalse(home.isElementPresent(VariantInfo.SUSPEND));
		Assert.assertFalse(home.isElementPresent(VariantInfo.DELETE_VARIANT));
		
		//Teardown: Delete products and variants
		home.logout();
		home.login(superUsername, superUserpassword);
		home.deleteAnaccessorybyName(readypublishproduct.get("name"));
		home.deleteAnaccessorybyName(publisedhproduct.get("name"));
		home.deleteAnaccessorybyName(suspendedproduct.get("name"));
		home.click(Xpath.LINK_USERS);
		home.dtsSelectUserByEmail(partneruser);
		home.click(UserMgmt.EDIT);
		home.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, all_brand);
	}
	
	/*
	 * Verify that the partner user is unable to request, approve, decline or revoke tuning of product and variant for unassigned brand when the  "Can request DTS tune products” privilege is enabled.
	 */
	@Test
	public void TCPPA_27(){
		result.addLog("ID : TCPPA_27: Verify that the partner user is unable to request, approve, decline or revoke tuning of product and variant for unassigned brand when the 'Can request DTS tune products' privilege is enabled.");
		/*
			Pre-condition: The "Can request DTS tune products" privilege of partner user is enabled but only assign for only one specific brand

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Enable the “Can request DTS tune products” privilege but assign for a specific brand
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
		
		// 2. Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.dtsSelectUserByEmail(partneruser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Publish and suspend products” privilege but assign for a specific brand
		home.enableAllPrivilege();
		home.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, partner_brand_name_2);
		home.click(AddUser.SAVE);
		//Create ready to publish, published and suspended products/variants
		home.click(Xpath.linkAccessories);
		home.click(AccessoryMain.ADD_ACCESSORY);
		//Create products and variants
		//Create an un-uploaded tuning product which is out of brand privilege from products list
		home.click(Xpath.linkAccessories);
		home.click(AccessoryMain.ADD_ACCESSORY);
		Hashtable<String,String> draftproduct= TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), draftproduct);
		
		//Create a product which tuning is up uploaded by DTS and request partner to approve
		home.click(Xpath.linkAccessories);
		home.click(AccessoryMain.ADD_ACCESSORY);
		Hashtable<String,String> tuninguploadedproduct = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), tuninguploadedproduct);
		
		//Create a approved tuning product
		home.click(Xpath.linkAccessories);
		home.click(AccessoryMain.ADD_ACCESSORY);
		Hashtable<String,String> tuningapprovedproduct = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), tuningapprovedproduct);
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		
		//Create a a declined tuning product 
		home.click(Xpath.linkAccessories);
		home.click(AccessoryMain.ADD_ACCESSORY);
		Hashtable<String,String> tuningdeclinedproduct = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), tuningdeclinedproduct);
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.DECLINE_TUNING);
		
		// Create an un-uploaded tuning variant which is out of brand privilege from variants list
		home.click(AccessoryInfo.ADD_VARIANT);
		String draftvariant = RandomStringUtils.randomNumeric(10);
		home.editData(AddVariant.NAME,draftvariant );
		home.click(AddVariant.UPLOAD_CUSTOM_TUNNING);
		home.click(AddVariant.SAVE);
		
		//Create a variant which tuning is up uploaded by DTS and request partner to approve
		home.click(VariantInfo.PRODUCT_LINK);
		home.click(AccessoryInfo.ADD_VARIANT);
		Hashtable<String,String> tuninguploadedvariant = TestData.variantTuning();
		home.addVariant(AddVariant.getHash(), tuninguploadedvariant);
		
		//Create an approved variant
		home.click(VariantInfo.PRODUCT_LINK);
		home.click(AccessoryInfo.ADD_VARIANT);
		Hashtable<String,String> tuningapprovevariant = TestData.variantTuning();
		home.addVariant(AddVariant.getHash(), tuningapprovevariant);
		home.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		home.click(VariantInfo.APPROVE_TUNING);
		
		//Create a a declined tuning variant 
		home.click(VariantInfo.PRODUCT_LINK);
		home.click(AccessoryInfo.ADD_VARIANT);
		Hashtable<String,String> declinedtuningvariant = TestData.variantTuning();
		home.addVariant(AddVariant.getHash(), declinedtuningvariant);
		home.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		home.click(VariantInfo.DECLINE_TUNING);
		
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner user successfully
		home.login(partneruser, password);
		// 9. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 10. Select an un-uploaded tuning product which is out of brand privilege from products list
		home.selectAnaccessorybyName(draftproduct.get("name"));
		// VP.Verify that the "Request DTS Tuning" link is not displayed
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.REQUEST_TUNING));
		// 11. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 12. Select a product which tuning is up uploaded by DTS and request partner to approve which is out of brand privilege from products list
		home.selectAnaccessorybyName(tuninguploadedproduct.get("name"));
		// VP. Verify that the "Approve Tuning" and "Decline Tuning" link are not displayed.
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.APPROVE_TUNING));
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.DECLINE_TUNING));
		// 13. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 14. Select a approved tuning product which is out of brand privilege from products list
		home.selectAnaccessorybyName(tuningapprovedproduct.get("name"));
		// VP. Verify that the "Revoke Tuning" link is not displayed
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.REVOKE_TUNING));
		// 15. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		//16. Select a declined tuning product which is out of brand privilege from products list
		home.selectAnaccessorybyName(tuningdeclinedproduct.get("name"));
		// VP. Verify that the "Request Revised Tuning" link is not displayed
		Assert.assertFalse(home.isElementPresent(VariantInfo.REQUEST_REVISED_TUNING));
		// 17. Navigate back to product detail page
		home.click(VariantInfo.PRODUCT_LINK);
		// 18. Select an unuploaded tuing variant
		home.selectAVariantbyName(draftvariant);
		// VP. Verify that the "Request DTS Tuning" link is not displayed
		Assert.assertFalse(home.isElementPresent(VariantInfo.REQUEST_DTS_TUNING));
		// 19. Back to product detail page
		home.click(VariantInfo.PRODUCT_LINK);
		//20.  Select a variant which tuning is up uploaded by DTS and request partner to approve
		home.selectAVariantbyName(tuninguploadedvariant.get("name"));
		//VP. VP: Verify that the "Approve Tuning" and "Decline Tuning" link are not displayed.
		Assert.assertFalse(home.isElementPresent(VariantInfo.APPROVE_TUNING));
		Assert.assertFalse(home.isElementPresent(VariantInfo.DECLINE_TUNING));
		//21. Navigate back to product info page
		home.click(VariantInfo.PRODUCT_LINK);
		//22. Select a approved tuning variant from variants list
		home.selectAVariantbyName(tuningapprovevariant.get("name"));
		//VP: Verify that the "Revoke Tuning" link are not displayed.
		Assert.assertFalse(home.isElementPresent(VariantInfo.REVOKE_TUNING));
		//23. Select a declined tuning variant which is out of brand privilege from products list
		home.click(VariantInfo.PRODUCT_LINK);
		home.selectAVariantbyName(declinedtuningvariant.get("name"));
		//VP. Verify that the "Request Revised Tuning" link is not displayed
		Assert.assertFalse(home.isElementPresent(VariantInfo.REQUEST_REVISED_TUNING));
		
		//Teardown: Delete products and variants
		home.logout();
		home.login(superUsername, superUserpassword);
		home.deleteAnaccessorybyName(tuningapprovedproduct.get("name"));
		home.deleteAnaccessorybyName(draftproduct.get("name"));
		home.deleteAnaccessorybyName(tuningdeclinedproduct.get("name"));
		home.deleteAnaccessorybyName(tuninguploadedproduct.get("name"));
		home.click(Xpath.LINK_USERS);
		home.dtsSelectUserByEmail(partneruser);
		home.click(UserMgmt.EDIT);
		home.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, all_brand);
	}
}	
