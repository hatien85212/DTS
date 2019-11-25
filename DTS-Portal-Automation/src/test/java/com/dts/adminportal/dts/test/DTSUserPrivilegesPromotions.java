package com.dts.adminportal.dts.test;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AccessoryMain;
import dts.com.adminportal.model.AddAccessory;
import dts.com.adminportal.model.AddPromotion;
import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.PromotionInfo;
import dts.com.adminportal.model.PromotionList;
import dts.com.adminportal.model.UserEdit;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.Xpath;

public class DTSUserPrivilegesPromotions extends CreatePage{
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
	 * Verify that the DTS user can view a promotion's info when both “Add and manage promotions” privilege is disabled
	 */
	@Test
	public void PPRO_01(){
		result.addLog("ID : PPRO_01 : Verify that the DTS user can view a promotion's info when both “Add and manage promotions” privilege is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the  “Add and manage promotions” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Promotions” page is displayed
			9. Navigate to “Promotions” page
			VP: Verify that the promotion list page is displayed
			10. Select a promotion from promotions list
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.selectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the  “Add and manage promotions” privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_promos);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		/*
		 * VP. Verify that the “Promotions” page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.linkPromotions));
		// 9. Navigate to “Promotions” page
		home.click(Xpath.linkPromotions);
		/*
		 * VP: Verify that the promotion list page is displayed
		 */
		Assert.assertEquals(home.existsElement(PromotionList.getElementInfo()).getResult(), "Pass");
		// 10. Select a promotion from promotions list
		String promotionName = home.selectAPromotion();
		/*
		 * Verify that the promotion's info page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(PromotionInfo.PROMOTION_NAME), promotionName);
		Assert.assertEquals(home.existsElement(PromotionInfo.getElementInfo()).getResult(), "Pass");
	}
	
	/*
	 * Verify that the DTS user cannot add new, edit, publish, suspend or delete promotions when both “Add and manage promotions” privilege is disabled
	 */
	@Test
	public void PPRO_02(){
		result.addLog("ID : PPRO_02 : Verify that the DTS user cannot add new, edit, publish, suspend or delete promotions when both “Add and manage promotions” privilege is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the  “Add and manage promotions” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Promotions” page is displayed
			9. Navigate to “Promotions” page
			VP: Verify that the promotion list page is displayed without “Add Promo” link
			10. Select a promotion from promotions list
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.selectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the  “Add and manage promotions” privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_promos);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		/*
		 * VP. Verify that the “Promotions” page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.linkPromotions));
		// 9. Navigate to “Promotions” page
		home.click(Xpath.linkPromotions);
		/*
		 * VP: Verify that the promotion list page is displayed without “Add Promo” link
		 */
		Assert.assertEquals(home.existsElement(PromotionList.getElementInfo()).getResult(), "Pass");
		Assert.assertFalse(home.isElementPresent(PromotionList.ADD_PROMO));
		// 10. Select a promotion from promotions list
		String promotionName = home.selectAPromotion();
		/*
		 * Verify that the promotion info page is displayed without the “Edit”, “Publish” and “Delete” links
		 */
		Assert.assertEquals(home.getTextByXpath(PromotionInfo.PROMOTION_NAME), promotionName);
		Assert.assertFalse(home.isElementPresent(PromotionInfo.EDIT));
		Assert.assertFalse(home.isElementPresent(PromotionInfo.PUBLISH));
		Assert.assertFalse(home.isElementPresent(PromotionInfo.DELETE));
	}
	
	/*
	 * Verify that the DTS user can add new promotions when the “Add and manage promotions” privilege is enabled
	 */
	@Test
	public void PPRO_03(){
		result.addLog("ID : PPRO_03 : Verify that the DTS user can add new promotions when the “Add and manage promotions” privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage promotions” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Promotions” page is displayed
			9. Navigate to “Promotions” page
			VP: Verify that the promotion list page is displayed with “Add Promo” link
			10. Click “Add Promo” link
			11. Fill valid value into all required fields
			12. Click “Save” link
		 */
		/*
		 * Create a product for promo
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> dataProduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct);
		/*
		 * *********************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage promotions” privileges
		home.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.Add_manage_promos);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		/*
		 * VP. Verify that the “Promotions” page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.linkPromotions));
		// 9. Navigate to “Promotions” page
		home.click(Xpath.linkPromotions);
		/*
		 * VP: Verify that the promotion list page is displayed with “Add Promo” link
		 */
		Assert.assertEquals(home.existsElement(PromotionList.getElementInfo()).getResult(), "Pass");
		Assert.assertTrue(home.isElementPresent(PromotionList.ADD_PROMO));
		// 10. Click “Add Promo” link
		home.click(PromotionList.ADD_PROMO);
		// 11. Fill valid value into all required fields
		// 12. Click “Save” link
		Hashtable<String,String> dataPromotion = TestData.promotionGlobal(dataProduct.get("brand") + " " + dataProduct.get("name"));
		home.addPromotion(AddPromotion.getHash(), dataPromotion);
		/*
		 * Verify that the new promotion info page is displayed correctly
		 */
		Assert.assertEquals(home.existsElement(PromotionInfo.getElementInfo()).getResult(), "Pass");
		// Delete promotion
		home.doDelete(PromotionInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user can edit promotions when the “Add and manage promotions” privilege is enabled
	 */
	@Test
	public void PPRO_04(){
		result.addLog("ID : PPRO_04 : Verify that the DTS user can edit promotions when the “Add and manage promotions” privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage promotions” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Promotions” page is displayed
			9. Navigate to “Promotions” page
			VP: Verify that the promotion list page is displayed
			10. Select a promotion from promotions list
			VP: Verify that the “Edit” link is displayed in promotion info page
			11. Click “Edit” link
			12. Change some promotion's value
			13. Click “Save” link
		 */
		/*
		 * Create a product for promo
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> dataProduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct);
		/*
		 * Create new promotion
		 */
		// Navigate to Promotion page
		home.click(Xpath.linkPromotions);
		// Click Add promo link
		home.click(PromotionList.ADD_PROMO);
		// Create new promotion
		Hashtable<String,String> dataPromotion = TestData.promotionGlobal(dataProduct.get("brand") + " " + dataProduct.get("name"));
		home.addPromotion(AddPromotion.getHash(), dataPromotion);
		/*
		 * *********************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.selectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the “Add and manage promotions” privileges
		home.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.Add_manage_promos);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		/*
		 * VP. Verify that the “Promotions” page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.linkPromotions));
		// 9. Navigate to “Promotions” page
		home.click(Xpath.linkPromotions);
		/*
		 * VP: Verify that the promotion list page is displayed
		 */
		Assert.assertEquals(home.existsElement(PromotionList.getElementInfo()).getResult(), "Pass");
		// 10. Select a promotion from promotions list
		home.selectAPromotionByName(PromotionList.PROMOTION_TABLE, dataPromotion.get("name"));
		/*
		 * VP: Verify that the “Edit” link is displayed in promotion info page
		 */
		Assert.assertEquals(home.getTextByXpath(PromotionInfo.PROMOTION_NAME), dataPromotion.get("name"));
		Assert.assertTrue(home.isElementPresent(PromotionInfo.EDIT));
		// 11. Click “Edit” link
		home.click(PromotionInfo.EDIT);
		// 12. Change some promotion's value
		String newSalesforceID = RandomStringUtils.randomNumeric(9);
		home.editData(AddPromotion.SALESFORCE_ID, newSalesforceID);
		// 13. Click “Save” link
		home.click(AddPromotion.SAVE);
		home.waitForElementClickable(PromotionInfo.DELETE);
		/*
		 * Verify that the promotion info page is displayed with new information correctly
		 */
		Assert.assertEquals(home.getTextByXpath(PromotionInfo.SALESFORCE_ID), newSalesforceID);
		// Delete promotion
		home.deleteAnaccessorybyName(dataProduct.get("name"));
	}
	
	/*
	 * Verify that the DTS user can publish promotions when the “Add and manage promotions” privilege is enabled
	 */
	@Test
	public void PPRO_05(){
		result.addLog("ID : PPRO_05 : Verify that the DTS user can publish promotions when the “Add and manage promotions” privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage promotions” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Promotions” page is displayed
			9. Navigate to “Promotions” page
			VP: Verify that the promotion list page is displayed
			10. Click “Add Promo” link
			11. Fill valid value into all required fields
			12. Click “Save” link
			VP: Verify that the new promotion info page is displayed with “Publish” link
			13. Click “Publish” link
		 */
		/*
		 * Create a product for promo
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> dataProduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct);
		/*
		 * *********************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage promotions” privileges
		home.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.Add_manage_promos);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		/*
		 * VP. Verify that the “Promotions” page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.linkPromotions));
		// 9. Navigate to “Promotions” page
		home.click(Xpath.linkPromotions);
		/*
		 * VP: Verify that the promotion list page is displayed with “Add Promo” link
		 */
		Assert.assertEquals(home.existsElement(PromotionList.getElementInfo()).getResult(), "Pass");
		Assert.assertTrue(home.isElementPresent(PromotionList.ADD_PROMO));
		// 10. Click “Add Promo” link
		home.click(PromotionList.ADD_PROMO);
		// 11. Fill valid value into all required fields
		// 12. Click “Save” link
		Hashtable<String,String> dataPromotion = TestData.promotionGlobal(dataProduct.get("brand") + " " + dataProduct.get("name"));
		home.addPromotion(AddPromotion.getHash(), dataPromotion);
		/*
		 * VP: Verify that the new promotion info page is displayed with “Publish” link
		 */
		Assert.assertEquals(home.existsElement(PromotionInfo.getElementInfo()).getResult(), "Pass");
		Assert.assertTrue(home.isElementPresent(PromotionInfo.PUBLISH));
		// 13. Click “Publish” link
		home.click(PromotionInfo.PUBLISH);
		/*
		 * Verify that the portal redirect to 660 Promos list page and the promotion status is changed to published
		 */
		Assert.assertEquals(home.getTextByXpath(PromotionInfo.PUBLISH_STATUS), "PUBLISHED");
		// Delete promotion
		home.doDelete(PromotionInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user can delete promotions when the “Add and manage promotions” privilege is enabled
	 */
	@Test
	public void PPRO_06(){
		result.addLog("ID : PPRO_06 : Verify that the DTS user can delete promotions when the “Add and manage promotions” privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage promotions” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Promotions” page is displayed
			9. Navigate to “Promotions” page
			VP: Verify that the promotion list page is displayed
			10. Select a promotion from promotions list
			VP: Verify that the “Delete” link is displayed in promotion info page
			11. Click “Delete” link
		 */
		/*
		 * Create a product for promo
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> dataProduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct);
		/*
		 * Create new promotion
		 */
		// Navigate to Promotion page
		home.click(Xpath.linkPromotions);
		// Click Add promo link
		home.click(PromotionList.ADD_PROMO);
		// Create new promotion
		Hashtable<String,String> dataPromotion = TestData.promotionGlobal(dataProduct.get("brand") + " " + dataProduct.get("name"));
		home.addPromotion(AddPromotion.getHash(), dataPromotion);
		/*
		 * *********************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.selectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage promotions” privileges
		home.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.Add_manage_promos);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		/*
		 * VP. Verify that the “Promotions” page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.linkPromotions));
		// 9. Navigate to “Promotions” page
		home.click(Xpath.linkPromotions);
		/*
		 * VP: Verify that the promotion list page is displayed
		 */
		Assert.assertEquals(home.existsElement(PromotionList.getElementInfo()).getResult(), "Pass");
		// 10. Select a promotion from promotions list
		home.selectAPromotionByName(PromotionList.PROMOTION_TABLE, dataPromotion.get("name"));
		/*
		 * VP: Verify that the “Delete” link is displayed in promotion info page
		 */
		Assert.assertTrue(home.isElementPresent(PromotionInfo.DELETE));
		// 11. Click “Delete” link
		home.doDelete(PromotionInfo.DELETE);
		/*
		 * Verify that the portal redirects to promotion main page and the the deleted promotion is not displayed in promotion list
		 */
		Assert.assertEquals(home.existsElement(PromotionList.getElementInfo()).getResult(), "Pass");
		Assert.assertFalse(home.checkPromotionExistByName(PromotionList.PROMOTION_TABLE, dataPromotion.get("name")));
	}

}
