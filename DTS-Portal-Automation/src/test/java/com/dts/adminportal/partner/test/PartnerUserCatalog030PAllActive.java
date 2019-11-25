package com.dts.adminportal.partner.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.DTSUtil;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AccessoryInfo;
import dts.com.adminportal.model.AccessoryMain;
import dts.com.adminportal.model.AddAccessory;
import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.PartnerAccessoryInfo;
import dts.com.adminportal.model.PartnerAddAccessory;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UserEdit;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.Xpath;

public class PartnerUserCatalog030PAllActive extends CreatePage{
	private HomeController home;
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}
	
	/*
	 Verify that partner user who does not have "Add and manage accessories" right can not add new accessory
	 */
	@Test
	public void TC030PCAA_01(){
		result.addLog("ID : TC030PCAA_01 : Verify that partner user who does not have 'Add and manage accessories' right can not add new accessory");
		/*
		 * 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a user from users table
			VP: Verify that the 090D User Info page is displayed
			5. Click "Edit" link
			6. Disable "Add and manage accessories" site privilege for above user
			7. Log out DTS portal
			8. Log into DTS portal as above partner admin
			9. Navigate to "Accessories" page
		 */
		// 2. Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table
		home.dtsSelectUserByEmail(partneruser);
		// VP: Verify that the 090D User Info page is displayed
		Assert.assertTrue(home.isElementPresent(UserMgmt.USER_INFO_PAGE_TITLE));
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Disable "Add and manage accessories" site privilege for above user
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner admin
		home.login(partneruser, password);
		// 9. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Verify that 030P All Active page is displayed without "Add Accessory" link.
		Assert.assertFalse(home.isElementPresent(AccessoryMain.ADD_ACCESSORY));
	}
	
	/*
	 * Verify that partner user who does not have "Add and manage accessories" right can not edit accessories.
	 */
	@Test
	public void TC030PCAA_02(){
		result.addLog("Verify that partner user who does not have 'Add and manage accessories' right can not edit accessories");
		/*
		 * 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a user from users table
			VP: Verify that the 090D User Info page is displayed
			5. Click "Edit" link
			6. Disable "Add and manage accessories" site privilege for above user
			7. Log out DTS portal
			8. Log into DTS portal as above partner admin
			9. Navigate to "Accessories" page
			10. Select an accessory from accessories table
		 */
		
		// 2. Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table
		home.dtsSelectUserByEmail(partneruser);
		// VP: Verify that the 090D User Info page is displayed
		Assert.assertTrue(home.isElementPresent(UserMgmt.USER_INFO_PAGE_TITLE));
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Disable "Add and manage accessories" site privilege for above user
		home.disablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.privileges[0]);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner admin
		home.login(partneruser, password);
		// 9. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 10. Select an accessory from accessories table
		home.selectAnAccessory();
		// Verify that 040P Accessory Detail page is displayed without "Edit" link.
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		Assert.assertFalse(home.isElementPresent(PartnerAccessoryInfo.EDIT_MODE));
	}
	/*
	 * Verify that partner user who has "Add and manage accessories" right can create new accessories.
	 */
	@Test
	public void TC030PCAA_03(){
		result.addLog("ID : TC030PCAA_03 : Verify that partner user who has 'Add and manage accessories' right can create new accessories");
		/*
		 * 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a user from users table
			VP: Verify that the 090D User Info page is displayed
			5. Click "Edit" link
			6. Enable "Add and manage accessories" site privilege for above user
			7. Log out DTS portal
			8. Log into DTS portal as above partner admin
			9. Navigate to "Accessories" page
			VP: Verify that the "Add Accessory" link is displayed
			10. Click "Add Accessory" link
			VP: Verify that the "Adding New Model" page is displayed
			11. Fill valid value into all required fields
			12. Click "Save" link
		 */
		// 2. Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table
		home.dtsSelectUserByEmail(partneruser);
		// VP: Verify that the 090D User Info page is displayed
		Assert.assertTrue(home.isElementPresent(UserMgmt.USER_INFO_PAGE_TITLE));
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Enable "Add and manage accessories" site privilege for above user
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner admin
		home.login(partneruser, password);
		// 9. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// VP: Verify that the "Add Accessory" link is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryMain.ADD_ACCESSORY));
		// 10. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// VP: Verify that the "Adding New Model" page is displayed
		Assert.assertEquals(home.existsElement(PartnerAddAccessory.getElementInfo()).getResult(), "Pass");
		// 11. Fill valid value into all required fields
		// 12. Click "Save" link
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(PartnerAddAccessory.getHash(), data);
		/*
		 * Verify that New accessory is created successfully.
		 */
		Assert.assertEquals(home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// Delete product
		home.doDelete(PartnerAccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that partner user who has "Add and manage accessories" right can edit accessories.
	 */
	@Test
	public void TC030PCAA_04(){
		result.addLog("ID : TC030PCAA_04 : Verify that partner user who has 'Add and manage accessories' right can edit accessories");
		/*
		 * 	1. Navigate to DTS portal
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
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * ***********************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table
		home.dtsSelectUserByEmail(partneruser);
		// VP: Verify that the 090D User Info page is displayed
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Enable "Add and manage accessories" site privilege for above user
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner admin
		home.login(partneruser, password);
		// 9. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// VP: Verify that the "Add Accessory" link is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryMain.ADD_ACCESSORY));
		// 10. Select an accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		// VP: Verify that 040P Accessory Detail page is displayed with "Edit" link
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.EDIT_MODE));
		// 11. Click "Edit" link
		home.click(PartnerAccessoryInfo.EDIT_MODE);
		// 12. Verify that 051P Accessory Edit Page is displayed
		Assert.assertTrue(home.isElementPresent(AddAccessory.TITLE));
		// 13. Change any of accessory's information
		String new_name = RandomStringUtils.randomAlphabetic(10);
		home.editData(AddAccessory.NAME, new_name);
		// 14. Click "Save" link
		home.click(AddAccessory.SAVE);
		/*
		 *  Verify that 040P Accessory Detail page is displayed with new accessory's information
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), new_name);
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the default catalog page displays with "All Active" report filter.
	 */
	@Test
	public void TC030PCAA_05(){
		result.addLog("ID : TC030PCAA_05 : Verify that the default catalog page displays with 'All Active' report filter.");
		/*
		 * 	1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
		 */
		
		// 1. Log into DTS portal as a partner admin
		home.login(partneruser, password);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		/*
		 * Verify that 030P All Active page displays, and default item of report filter is "All Active"
		 */
		String filter = home.getItemSelectedByXpath(Xpath.accessoryFilterSelect).getResult();
		Assert.assertEquals(filter, "All Active");
	}
	
	/*
	 * Verify that the report filter dropdown contains correct items.
	 */
	@Test
	public void TC030PCAA_06(){
		result.addLog("ID : TC030PCAA_06 : Verify that the report filter dropdown contains correct items.");
		/*
		 * 	1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. List out all items of report filter dropdown
		 */
		
		// 1. Log into DTS portal as a partner admin
		home.login(partneruser, password);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. List out all items of report filter dropdown
		ArrayList<String> arrayList = home.getAllOption(Xpath.accessoryFilterSelect);
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
	}
	
	/*
	 * Verify that the accessories table lists maximum 50 items per page
	 */
	@Test
	public void TC030PCAA_07(){
		result.addLog("ID : TC030PCAA_07 : Verify that the accessories table lists maximum 50 items per page");
		/*
		 Pre-condition: the partner user has at least 50 accessories. 
		 1. Log into DTS portal as a partner admin 
		 2. Navigate to "Accessories" page
		 */
		
		// 1. Log into DTS portal as a partner admin
		home.login(partneruser, password);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		/*
		 * Verify that the accessories table list maximum 50 items per page
		 */
		Assert.assertTrue(home.checkItemAmountDisplayOnTable(Xpath.TABLE_INFO, 50));
	}		

	/*
	 * Verify that the accessories table is hidden and displays text "No models match these criteria" when the report filter is empty
	 */
	@Test
	public void TC030PCAA_08(){
		result.addLog("ID : TC030PCAA_08 : Verify that the accessories table is hidden and displays text 'No models match these criteria' when the report filter is empty");
		/*
		 Pre-condition: The partner has no accessory
		 1. Log into DTS portal as a partner admin 
		 2. Navigate to "Accessories" page
		 */
		
		// 1. Log into DTS portal as a partner admin
		home.login(partneruser, password);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Delete all product has published status "Suspended"
		String status = "Suspended";
		home.deleteAllProductWithPublishedStatus(status);
		/*
		 * Verify that The accessories table is replaced by text "No models match these criteria"	
		 */
		String message = "No models match these criteria";
		Assert.assertFalse(home.isElementPresent(AccessoryMain.ACCESSORY_TABLE));
		Assert.assertTrue(home.checkMessageDisplay(message));
	}
	
	/*
	 * Verify that the accessories table display eight columns properly
	 */
	@Test
	public void TC030PCAA_09(){
		result.addLog("ID : TC030PCAA_09 : Verify that the accessories table display eight columns properly");
		/*
		 1. Log into DTS portal as a partner admin
		 2. Navigate to "Accessories" page
		 */
		
		// 1. Log into DTS portal as a partner admin
		home.login(partneruser, password);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
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
		ArrayList<String> colums = home.getHeadColumByXpath(Xpath.ACCESSORY_THREAD);
		Boolean match = DTSUtil.containsAll(colums, Constant.getHeaderAccessoriesTable());
		Assert.assertTrue(match);
	}
}
