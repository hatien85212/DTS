package com.dts.adminportal.partner.test;

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
import dts.com.adminportal.model.PartnerHomePage;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.Xpath;
import dts.com.adminportal.model.UserInfo;

public class PartnerUserHome025PPartner extends CreatePage{
	private HomeController home;
	
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}
	
	@BeforeMethod
	public void loginBeforeTest() {
		home.login(superpartneruser, superpartnerpassword);
	}
	
	/*
	 * Verify that "Home" page displays a welcome statement  at the top of page
	 */
	@Test
	public void TC025PP_01(){
		result.addLog("ID : TC025PP_01 : Verify that 'Home' page displays quick overview of company's catalog");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Navigate to "Home" page
		 */
		// Verify that the Welcome message is displayed with pattern “Welcome, %Partner_Company_Name”
		Assert.assertEquals(home.getTextByXpath(PartnerHomePage.PAGE_HEADER), "Welcome, " + partner_company_name);
	}
	
	/*
	 * Verify that "Home" page displays  DTS HPX logo in a good size
	 */
	@Test
	public void TC025PP_02(){
		result.addLog("ID : TC025PP_02 : Verify that 'Home' page displays  DTS HPX logo in a good size");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Navigate to "Home" page
		 */
		// Get Welcome message position
		int welcomeMessage_Pos = driver.findElement(By.xpath(PartnerHomePage.PAGE_HEADER)).getLocation().y;
		result.addLog("Welcome Message position: " + welcomeMessage_Pos);
		// Get DTS Logo position
		int dtsLogo_Pos = driver.findElement(By.xpath(PartnerHomePage.HOME_LOGO)).getLocation().y;
		result.addLog("DTS Logo position: " + dtsLogo_Pos);
		/*
		 *  Verify that the DTS HPX logo displays below the welcome message
		 */
		Assert.assertTrue(welcomeMessage_Pos < dtsLogo_Pos);
	}

	/*
	 * Verify that the "Products" module displays "Add Product", "Draft" and "Ready to Publish" links
	 */
	@Test
	public void TC025PP_03(){
		result.addLog("ID : Verify that the 'Products' module displays 'Add Product', 'Draft' and 'Ready to Publish' links");
		/*
		 	Precondition: The partner user has “Add and manage product” privilege.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Navigate to "Home" page
		 */
		// Verify that the Product module displays "Add Product", "Draft" and "Ready to Publish" links
		Assert.assertEquals("Pass", home.existsElement(PartnerHomePage.getListCatalog()).getResult());
	}
	
	/*
	 * Verify that "Needs Attention" and "Suspended" links in "Accessories" module only display when their item count is larger than 0
	 */
	@Test
	public void TC025PP_04(){
		result.addLog("ID : TC025PP_04 : Verify that 'Needs Attention' and 'Suspended' links in 'Accessories' module only display when their item count is larger than 0");
		/*
		 	Pre-Condition:
			_To make "Needs Attention" link displays item count, there is at least one product's tuning  is declined or revoked.
			_To make "Suspended" link displays item count, there is at least one product's published status is suspended.
			
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Navigate to "Home" page
		 */
		/*
		 * Precondition: Create a "Needs Attention" and "Suspend" product
		 */
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
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
		// Log out
		home.logout();
		/*
		 * *******************************************************************
		 */
		// 2. Log into DTS portal as a partner user successfully
		// 3. Navigate to "Home" page
		home.login(superpartneruser, superpartnerpassword);
		/*
		 * Verify that The "Needs Attention" and "Suspended" links with their item count are displayed in "Accessories" module
		 */
		Assert.assertTrue(home.isElementPresent(PartnerHomePage.PARTNER_CATALOG_SUSPENDED));
		Assert.assertTrue(home.isElementPresent(PartnerHomePage.PARTNER_CATALOG_NEED_ATTENTION));
	}
	
	/*
	 * Verify that "Needs Attention" and "Suspended" links in "Accessories" module is hidden when their item count is 0
	 */
	@Test
	public void TC025PP_05(){
		result.addLog("ID : TC025PP_05 : Verify that 'Needs Attention' and 'Suspended' links in 'Accessories' module is hidden when their item count is 0");
		/*
		 	Pre-Condition:
			_For "Needs Attention" link: Make sure that the Tuning or Marketing status does not include one of following: Tuning is Declined or Revoked, Marketing is Declined or Revoked. Tuning is Approved but Headphone X Rating is in "Undetermined" state.
			_For "Suspended" link: Make sure that there is no product's published status Suspended.
			
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Navigate to "Home" page
		 */
		/*
		 * Precondition: There is no "Suspended" and "Need Attention" product
		 */
		// Navigate to product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Delete all suspended products
		home.deleteAllProductWithPublishedStatus("Suspended");
		home.deleteAllProductWithPublishedStatus("Needs Attention");
		// 3. Navigate to "Home" page
		home.click(Xpath.LINK_PARTNER_HOME);
		/*
		 * Verify that The "Needs Attention" and "Suspended" links are hidden in "Accessories" module
		 */
		Assert.assertFalse(home.isElementPresent(PartnerHomePage.PARTNER_CATALOG_SUSPENDED));
		Assert.assertFalse(home.isElementPresent(PartnerHomePage.PARTNER_CATALOG_NEED_ATTENTION));
	}
	
	/*
	 * Verify that “Home” page displays a brief overview of the Headphone X platform
	 */
	@Test
	public void TC025PP_06(){
		result.addLog("ID : TC025PP_06 : Verify that “Home” page displays a brief overview of the Headphone X platform");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Navigate to "Home" page
		 */
		// Verify that “Home” page displays a brief overview of the Headphone X platform
		ArrayList<String> content = home.getTextByXpath(PartnerHomePage.listContentXpath());
		Assert.assertTrue(DTSUtil.containsAll(content, PartnerHomePage.getContentMessage()));
	}
	
	/*
	 * Verify that “Home” page displays a brief overview of the Headphone X platform
	 */
	@Test
	public void TC025PP_07(){
		result.addLog("ID : TC025PP_06 : Verify that the “Add Product” link is not displayed in 'Products' module when the user has no 'Add and manage Products' privilege.");
		/*
		Precondition: The partner user has no “Add and manage product” privilege.
		
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Navigate to "Home" page
		 */
		//pre-condition: change partner user's privielge
		home.logout();
		home.login(superpartneruser, superpartnerpassword);
		home.click(Xpath.LINK_USERS);
		home.selectUserByEmail(partneruser);
		home.click(UserInfo.EDIT);
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		home.logout();
		
//		1. Navigate to DTS portal		
//		2. Log into DTS portal as a partner user successfully
//		3. Navigate to "Home" page
		home.login(partneruser, password);
		Assert.assertFalse(home.isElementPresent(PartnerHomePage.PARTNER_CATALOG_ADD_ACCESSORY));
		
		//Teardown: re-enable user privileges
		home.logout();
		home.login(superpartneruser, superpartnerpassword);
		home.click(Xpath.LINK_USERS);
		home.selectUserByEmail(partneruser);
		home.click(UserInfo.EDIT);
		home.enableAllPrivilege();
		home.click(AddUser.SAVE);
		
	}
	
	
}
