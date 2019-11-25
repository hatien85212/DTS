package com.dts.adminportal.dts.test;

import java.util.Hashtable;

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
import dts.com.adminportal.model.DTSHome;
import dts.com.adminportal.model.DTSUserHomePage;
import dts.com.adminportal.model.PartnerHomePage;
import dts.com.adminportal.model.Xpath;

public class DTSUserHome020DDTSView extends CreatePage{
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
	 * Verify that the text "View as" is displayed on top navigation bar when logging to DTS portal as DTS user
	 */
	@Test
	public void TC020DV_01(){
		result.addLog("ID : TC020DV_01 : Verify that the text 'View as' is displayed on top navigation bar when logging to DTS portal as DTS user");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
		*/
		// Log out
		home.logout();
		// 2. Log into DTS portal as a DTS user successfully
		home.login(dtsUser, dtsPass);
		/*
		 * The text "View as" is displayed on top navigation bar
		 */
		String text = home.getTextByXpath(DTSUserHomePage.VIEW_AS);
		// Check if PDPP-169 found
		if(text.equals("")){
			result.addErrorLog("PDPP-169: Page Banner: The text 'View as' is not displayed when logging in portal under DTS admin role");
		}
		Assert.assertEquals(text, "View As:");
	}
	
	/*
	 Verify that the "Partner/Company" dropdown is 
	 displayed on top navigation bar when logging to DTS portal as DTS user
	 */
	@Test
	public void TC020DV_02(){
		result.addLog("ID : TC020DV_02 : Verify that the 'Partner/Company' dropdown is  displayed on top navigation bar when logging to DTS portal as DTS user" );
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
		 */
		// The "Partner/Company" is displayed on top navigation bar
		Boolean flag = home.isElementPresent(DTSUserHomePage.PARTNERS_LIST);
		Assert.assertTrue(flag);
	}
	
	/*
	 * Verify that the "Partner/Company" dropdown displays the company's name of DTS user correctly on top navigation bar
	 */
	@Test
	public void TC020DV_03(){
		result.addLog("ID : TC020DV_03 : Verify that the 'Partner/Company' dropdown displays the company's name of DTS user correctly on top navigation bar");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
		*/
		/*
		 * Verify that the "Partner/Company" dropdown displays the company's name of DTS user correctly on top navigation bar
		 */
		
		String company_name= home.getAtribute(DTSUserHomePage.PARTNERS_LIST, "title");
		Assert.assertEquals(company_name, "DTS Inc.");
	}
	
	/*
	 * Verify that the DTS user's email is displayed correctly on top navigation bar
	 */
	@Test
	public void TC020DV_04(){
		result.addLog("ID : TC020DV_04 : Verify that the DTS user's email is displayed correctly on top navigation bar");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
		*/
		/*
		 * Verify that the DTS user's email is displayed correctly on top navigation bar
		 */
		
		String email = home.getTextByXpath(DTSUserHomePage.SIGNIN_EMAIL);
		Assert.assertEquals(email, superUsername);
	}
	
	/*
	 * Verify that the user menu is displayed including "User Account" and "Sign Out" items when clicking on DTS use's email 
	 */
	@Test
	public void TC020DV_05(){
		result.addLog("ID : TC020DV_05 : Verify that the user menu is displayed including 'User Account' and 'Sign Out' items when clicking on DTS use's email");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click on DTS user's mail on top navigation bar
		*/
		/*
		 * Verify that the user menu is displayed including 'User Account' and 'Sign Out' items when clicking on DTS use's email
		 */
		home.click(DTSUserHomePage.SIGNIN_EMAIL);
		
		//Verify that the user menu is displayed including 'User Account' and 'Log Out'
		Assert.assertTrue(home.isElementPresent(DTSUserHomePage.USER_ACCOUNT) && home.isElementPresent(DTSUserHomePage.LOGOUT));
	}
	
	/*
	 * Verify that the DTS portal is redirected to default "Home" page when clicking on DTS logo
	 */
	@Test
	public void TC020DV_06(){
		result.addLog("ID : TC020DV_06 : Verify that the DTS portal is redirected to default 'Home' page when clicking on DTS logo");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to another page other than "Home" page
			4. Click on DTS logo1. Navigate to DTS portal
		*/
		/*
		 * Verify that the DTS portal is redirected to default 'Home' page when clicking on DTS logo
		 */
		home.click(Xpath.LINK_AUDIO_ROUTES);
		home.click(Xpath.logoImg);
			
		//Verify that All Controls in Homepage are displayed
		Assert.assertTrue(home.isElementPresent(DTSUserHomePage.CATALOG_ADD_ACCESSORY));
	}
	
	/*
	 * Verify that "Home" page displays quick overview of company's catalog entries by 
	 * seven modules 
	 * "Catalog", 
	 * "Tuning Status", 
	 * "Marketing Approval Status", 
	 * "Model Allocation Status", 
	 * "Companies", 
	 * "Devices" and 
	 * "Featured Accessory Promotions" modules.
	 */
	@Test
	public void TC020DV_07(){
		result.addLog("ID : TC020DV_07 : Verify that 'Home' page displays 7 modules");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Home" page
		*/
		/*
		 * Verify that "Home" page displays quick overview of company's catalog entries by 
		 * seven modules 
		 * "Catalog", 
		 * "Tuning Status", 
		 * "Marketing Approval Status", 
		 * "Model Allocation Status", 
		 * "Companies", 
		 * "Devices" and 
		 * "Featured Accessory Promotions" modules.
		 */
		result = home.existsElement(DTSHome.getTab());
		Assert.assertEquals("Pass", result.getResult());
	}
	
	/*
	Verify that the "Catalog" module always displays 
	"Add Accessory", 
	"Recently Added", 
	"Draft", 
	"Ready to Publish", 
	"Overdue" and 
	"Recently Published" links.
	 */
	@Test
	public void TC020DV_08(){
		result.addLog("ID : TC020DV_08 : Verify that the 'Catalog' module");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Home" page
		*/
		/*
		Verify that the "Catalog" module always displays 
		"Add Accessory", 
		"Recently Added", 
		"Draft", 
		"Ready to Publish", 
		"Overdue" and 
		"Recently Published" links.
		 */
		result = home.existsElement(DTSHome.getCatalog());
		Assert.assertEquals("Pass", result.getResult());
	}
	
	/*
	 * Verify that "Needs Attention" and "Suspended" links in "Catalog" module only display when their item count is larger than 0.
	*/
	@Test
	public void TC020DV_09(){
		result.addLog("Verify that 'Needs Attention' and 'Suspended' links in 'Catalog' module only display when their item count is larger than 0.");
		/*
		 	Pre-Condition:
			_To make "Needs Attention" link displays item count, there is at least one accessory's tuning  is declined or revoked. (Please see test case ……. for more details)
			_To make "Suspended" link displays item count, there is at least one accessory's published status is suspended (Please see test case .... for more details)

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Home" page
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
		/*
		 * The "Needs Attention" and "Suspended" links with their item count are displayed in "Catalog" module
		 */
		Assert.assertTrue(home.isElementPresent(DTSHome.CATALOG_SUSPENDED));
		Assert.assertTrue(home.isElementPresent(DTSHome.CATALOG_NEED_ATTENTION));
	}
	
	/*
	 * Verify that "Needs Attention" and "Suspended" links in "Catalog" module is hidden when their item count is 0
	 */
	@Test
	public void TC020DV_10(){
		result.addLog("Verify that 'Needs Attention' and 'Suspended' links in 'Catalog' module is hidden when their item count is 0");
		/*
		 	Pre-Condition:
			_To make "Needs Attention" link displays item count, there is at least one accessory's tuning  is declined or revoked. (Please see test case ……. for more details)
			_To make "Suspended" link displays item count, there is at least one accessory's published status is suspended (Please see test case .... for more details)
			
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Home" page
		*/
		/*
		 * Precondition: There is no "Suspended" and "Need Attention" product
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Delete all suspended products
		home.deleteAllProductWithPublishedStatus("Suspended");
		home.deleteAllProductWithPublishedStatus("Needs Attention");
		/*
		 * ***********************************************************
		 */
		// 3. Navigate to "Home" page
		home.click(Xpath.linkHome);
		/*
		 * Verify that The "Needs Attention" and "Suspended" links are hidden in "Accessories" module
		 */
		Assert.assertFalse(home.isElementPresent(DTSHome.CATALOG_SUSPENDED));
		Assert.assertFalse(home.isElementPresent(DTSHome.CATALOG_NEED_ATTENTION));
	}
	
	/*
	 * Verify that the portal redirects to “Home” page when navigating to log in page directly without logging out
	 */
	@Test
	public void TC020DV_11(){
		result.addLog("ID : TC020DV_11 : Verify that the portal redirects to “Home” page when navigating to log in page directly without logging out");
		/*
		 	1. Launch a web browser 
			2. Navigate to DTS portal
			3. Log into DTS portal as a partner user successfully
			4. Type “http://%DTS_Portal_Server/saap”
		*/
		// Log out
		home.logout();
		// 2. Navigate to DTS portal
		// 3. Log into DTS portal as a partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 4. Type “http://%DTS_Portal_Server/saap”
		String url = "http://devportal.dts.com/saap";
		driver.get(url);
		/*
		 * Verify that The “Home” page should display
		 */
		Assert.assertEquals(home.existsElement(PartnerHomePage.getElementInfo()).getResult(), "Pass");
	}
}
