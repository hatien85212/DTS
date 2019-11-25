package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.DTSUtil;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AccessoryInfo;
import dts.com.adminportal.model.AccessoryMain;
import dts.com.adminportal.model.AddAccessory;
import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.DTSHome;
import dts.com.adminportal.model.PartnerHomePage;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.Xpath;

public class DTSUserHome020DSkullCandyView extends CreatePage{
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
	 * Verify that "Home" page displays a welcome statement at the top of page
	 */
	@Test
	public void TC020DPSV_01(){
		result.addLog("ID : TC020DPSV_01 : Verify that 'Home' page displays a welcome statement  at the top of page");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as DTS user successfully
			3. Navigate to "Home" page
			4. Set "View As" dropdown to another item other than DTS Inc
		*/
		// 4. Set "View As" dropdown to another item other than DTS Inc
		home.selectOptionInDropdown(DTSHome.PARTNERS_LIST_DROPDOWN, partner_company_name);
		/*
		 * Verify that the Welcome message is displayed with pattern “Welcome, %Partner_Company_Name”
		 */
		Assert.assertEquals(home.getTextByXpath(PartnerHomePage.PAGE_HEADER),"Welcome, " + partner_company_name);
	}
	
	/*
	 * Verify that "Home" page displays  DTS HPX logo in a good size
	 */
	@Test
	public void TC020DPSV_02(){
		result.addLog("ID : TC020DPSV_02 : Verify that 'Home' page displays  DTS HPX logo in a good size");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Navigate to "Home" page
			4. Select a partner from "View as" dropdown
		*/
		// 4. Set "View As" dropdown to another item other than DTS Inc
		home.selectOptionInDropdown(DTSHome.PARTNERS_LIST_DROPDOWN, partner_company_name);
		// Get Welcome message position
		int welcomeMessage_Pos = driver.findElement(By.xpath(PartnerHomePage.PAGE_HEADER)).getLocation().y;
		result.addLog("Welcome Message position: " + welcomeMessage_Pos);
		// Get DTS Logo position
		int dtsLogo_Pos = driver.findElement(By.xpath(PartnerHomePage.HOME_LOGO)).getLocation().y;
		result.addLog("DTS Logo position: " + dtsLogo_Pos);
		/*
		 * Verify that the DTS HPX logo displays below the welcome message		
		 */
		Assert.assertTrue(welcomeMessage_Pos < dtsLogo_Pos);
	}
	
	/*
	 * Verify that the "Catalog" module always displays "Add product", "Draft" and "Ready to Publish" links	
	 */
	@Test
	public void TC020DPSV_03(){
		result.addLog("ID : TC020DPSV_03 : Verify that the 'Catalog' module always displays 'Add product', 'Draft' and 'Ready to Publish' links");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Home" page
			4. Set "View As" dropdown to another item other than DTS Inc
		*/
		// 4. Set "View As" dropdown to another item other than DTS Inc
		home.selectOptionInDropdown(DTSHome.PARTNERS_LIST_DROPDOWN, partner_company_name);
		/*
		Verify that the Catalog module always displays 
		"Add Accessory", 
		"Draft" and 
		"Ready to Publish" links.
		 */
		Assert.assertEquals("Pass", home.existsElement(PartnerHomePage.getListCatalog()).getResult());
	}
	
	/*
	 * Verify that "Needs Attention" and "Suspended" links in "Products" module only display when their item count is larger than 0
	 */
	@Test
	public void TC020DPSV_04(){
		result.addLog("ID : TC020DPSV_04 : Verify that 'Needs Attention' and 'Suspended' links in 'Products' module only display when their item count is larger than 0");
		/*
		 	Pre-Condition:
			_To make "Needs Attention" link displays item count, there is at least one product's tuning  is declined or revoked.
			_To make "Suspended" link displays item count, there is at least one product's published status is suspended.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Navigate to "Home" page
			4. Select a partner from "View as" dropdown
		*/
		/*
		 * Precondition: Create a "Needs Attention" and "Suspend" product
		 */
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create published product
		Hashtable<String,String> dataSuspend = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataSuspend);
		// Suspend product
		home.click(AccessoryInfo.SUSPEND);
		home.selectConfirmationDialogOption("Suspend");
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create Need attention product
		Hashtable<String,String> dataNeedAttention = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), dataNeedAttention);
		// Approve tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Revoke tuning
		home.click(AccessoryInfo.REVOKE_TUNING);
		/*
		 * *******************************************************************
		 */
		// 3. Navigate to "Home" page
		home.click(Xpath.linkHome);
		// 4. Select a partner from "View as" dropdown
		home.selectOptionInDropdown(DTSHome.PARTNERS_LIST_DROPDOWN, partner_company_name);
		/*
		 * The "Needs Attention" and "Suspended" links with their item count are displayed in "Catalog" module
		 */
		Assert.assertTrue(home.isElementPresent(DTSHome.CATALOG_SUSPENDED));
		Assert.assertTrue(home.isElementPresent(DTSHome.CATALOG_NEED_ATTENTION));
	}
	
	/*
	 * Verify that "Needs Attention" and "Suspended" links in "Products" module is hidden when their item count is 0
	 */
	@Test
	public void TC020DPSV_05(){
		result.addLog("ID : TC020DPSV_05 : Verify that 'Needs Attention' and 'Suspended' links in 'Products' module is hidden when their item count is 0");
		/*
		 	Pre-Condition:
			_For "Needs Attention" link: Make sure that the Tuning or Marketing status does not include one of following: Tuning is Declined or Revoked, Marketing is Declined or Revoked. Tuning is Approved but Headphone X Rating is in "Undetermined" state.
			_For "Suspended" link: Make sure that there is no product's published status Suspended.
			
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Navigate to "Home" page
			4. Select a partner from "View as" dropdown.
		*/
		/*
		 * Precondition: There is no "Suspended" and "Need Attention" product
		 */
		// Select a partner from View As dropdown
		home.selectOptionInDropdown(DTSHome.PARTNERS_LIST_DROPDOWN, partner_company_name);
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Delete all suspended products
		home.deleteAllProductWithPublishedStatus("Suspended");
		home.deleteAllProductWithPublishedStatus("Needs Attention");
		/*
		 * ***********************************************************
		 */
		// 3. Navigate to "Home" page
		// 4. Select a partner from "View as" dropdown
		home.click(Xpath.LINK_PARTNER_HOME);
		/*
		 * Verify that The "Needs Attention" and "Suspended" links are hidden in "Accessories" module
		 */
		Assert.assertFalse(home.isElementPresent(DTSHome.CATALOG_SUSPENDED));
		Assert.assertFalse(home.isElementPresent(DTSHome.CATALOG_NEED_ATTENTION));
	}
	
	/*
	 * Verify that “Home” page displays a brief overview of the Headphone X platform
	 */
	@Test
	public void TC020DPSV_06(){
		result.addLog("ID : TC020DPSV_06 : Verify that “Home” page displays a brief overview of the Headphone X platform");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Navigate to "Home" page
			4. Select a partner from "View as" dropdown
		*/
		// 4. Select a partner from "View as" dropdown
		home.selectOptionInDropdown(DTSHome.PARTNERS_LIST_DROPDOWN, partner_company_name);
		/*
		 * Verify that The brief overview of the Headphone X platform displays below DTS HPX logo with content
		 */
		ArrayList<String> content = home.getTextByXpath(PartnerHomePage.listContentXpath());
		Assert.assertTrue(DTSUtil.containsAll(content, PartnerHomePage.getContentMessage()));
	}
		
	/*
	 * Verify that the “Add Product” link is not displayed in “Products” module when the user has no “Add and manage Products” privilege
	 */
	@Test
	public void TC020DPSV_07(){
		result.addLog("ID : TC020DPSV_07 : Verify that the “Add Product” link is not displayed in “Products” module when the user has no “Add and manage Products” privilege");
		/*
		 	Precondition: The DTS user has no “Add and manage product” privilege.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Home" page
			4. Select a partner from "View as" dropdown.
		*/
		/*
		 * Precondition: The DTS user has no “Add and manage product” privilege
		 */
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a DTS user
		home.selectUserByEmail(dtsUser);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage products" privilege
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// Log out
		home.logout();
		/*
		 * **********************************************************
		 */
		// 2. Log into DTS portal as a DTS user successfully
		home.login(dtsUser, dtsPass);
		// 3. Navigate to "Home" page
		// 4. Select a partner from "View as" dropdown.
		home.selectOptionInDropdown(DTSHome.PARTNERS_LIST_DROPDOWN, partner_company_name);
		/*
		 * Verify that The “Add Products” link is not displayed
		 */
		Assert.assertFalse(home.isElementPresent(PartnerHomePage.PARTNER_CATALOG_ADD_ACCESSORY));
	}
		
		
	
	
	
}
