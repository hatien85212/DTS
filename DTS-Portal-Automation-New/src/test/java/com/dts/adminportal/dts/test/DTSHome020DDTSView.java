package com.dts.adminportal.dts.test;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.DTSHome;
import com.dts.adminportal.model.DTSUserHomePage;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PartnerHomePage;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.util.ListUtil;

public class DTSHome020DDTSView extends BasePage{
	
	@Override
	protected void initData() {
	}	

//	@BeforeMethod
//	public void loginBeforeTest() {
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//	}
	
	/*
	 * Verify that the home page under DTS's view displays properly
	 */
	@Test
	public void TC020DV_01(){
		userControl.addLog("ID : TC020DV_01 : Verify that the home page under DTS's view displays properly");
		userControl.addErrorLog("PDPP-169: Page Banner: The text 'View as' is not displayed when logging in portal under DTS admin role");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			 VP: The text "View as" is displayed on top navigation bar
			 VP: The "Partner/Company" is displayed on top navigation bar
			 VP: The "Partner/Company" dropdown displays the company's name of DTS user correctly on top navigation bar and next to partner's email
			 VP: The DTS user's email is displayed correctly on top navigation bar
			3. Click on DTS user's mail on top navigation bar
			VP: The user menu is displayed which including "User Account" and "Sign Out" items.
		*/
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP: The text "View as" is displayed on top navigation bar
		 */
		String text = userControl.getTextByXpath(DTSUserHomePage.VIEW_AS);
		// Check if PDPP-169 found
		if(text.equals("")){
			userControl.addErrorLog("PDPP-169: Page Banner: The text 'View as' is not displayed when logging in portal under DTS admin role");
		}
		Assert.assertEquals(text, "View As:");
		//VP: The "Partner/Company" is displayed on top navigation bar
		//VP: The "Partner/Company" dropdown displays the company's name of DTS user correctly on top navigation bar and next to partner's email
		boolean flag = userControl.isElementPresent(DTSUserHomePage.PARTNERS_LIST);
		Assert.assertTrue(flag);
		String company_name= userControl.getAtributeValue(DTSUserHomePage.DTS_TITLE, "title");
		Assert.assertEquals(company_name, "DTS Inc.");
		//VP: The DTS user's email is displayed correctly on top navigation bar
		String email = userControl.getTextByXpath(DTSUserHomePage.SIGNIN_EMAIL);
		Assert.assertEquals(email, DTS_USER);
		//3. Click on DTS user's mail on top navigation bar
		userControl.click(DTSUserHomePage.SIGNIN_EMAIL);
		//VP: The user menu is displayed which including "User Account" and "Sign Out" items.
		Assert.assertTrue(userControl.isElementPresent(DTSUserHomePage.USER_ACCOUNT) && userControl.isElementPresent(DTSUserHomePage.LOGOUT));
	}
	
	
	/*
	 * Verify that the DTS portal is redirected to default "Home" page when clicking on DTS logo
	 */
	@Test
	public void TC020DV_06(){
		userControl.addLog("ID : TC020DV_06 : Verify that the DTS portal is redirected to default 'Home' page when clicking on DTS logo");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to another page other than "Home" page
			4. Click on DTS logo1. Navigate to DTS portal
		*/
		/*
		 * Verify that the DTS portal is redirected to default 'Home' page when clicking on DTS logo
		 */
		loginControl.login(DTS_USER, DTS_PASSWORD);
		userControl.click(PageHome.linkAudioroutes);
		userControl.click(PageHome.logoImg);
			
		//Verify that the DTS portal is redirected to default 'Home' page when clicking on DTS logo
		//Assert.assertTrue(userControl.isElementPresent(DTSUserHomePage.CATALOG_ADD_ACCESSORY));
		Assert.assertTrue(userControl.isElementPresent(DTSUserHomePage.PRODUCT_ROW));
		Assert.assertTrue(userControl.isElementPresent(DTSUserHomePage.COMPANIES_ROW));
	}
	
	/*
	 * Verify that "Home" page displays quick overview of company's catalog entries by 2 modules "Product Catalog" and "Companies� modules.
	 */
	@Test
	public void TC020DV_07(){
		userControl.addLog("ID : TC020DV_07 : Verify that 'Home' page displays quick overview of company's catalog"
				+ " entries by 2 modules 'Product Catalog' and 'Companies' modules.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Home" page
		 	VP: Verify that "Home" page displays quick overview of company's catalog entries by 2 modules "Product Catalog" and "Companies� modules.
		 	VP: Verify that the Catalog module always displays "Add product", "Recently Added", "Draft", "Ready to Publish", "Overdue" and "Recently Published" links.
		 */
		//2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//VP: Verify that "Home" page displays quick overview of company's catalog entries by 2 modules "Product Catalog" and "Companies� modules.
		Assert.assertEquals(true, userControl.existsElement(DTSHome.getTab()));
		Assert.assertTrue(userControl.isElementPresent(DTSUserHomePage.PRODUCT_ROW));
		Assert.assertTrue(userControl.isElementPresent(DTSUserHomePage.COMPANIES_ROW));
		//VP: Verify that the Catalog module always displays "Add product", "Recently Added", "Draft", "Ready to Publish", "Overdue" and "Recently Published" links.
		//Assert.assertEquals("Pass", userControl.existsElement(DTSHome.getCatalog()));
		Assert.assertTrue(userControl.isElementPresent(DTSUserHomePage.ADD_PRODUCT));
		Assert.assertTrue(userControl.isElementPresent(DTSUserHomePage.RECENTLY_ADD));
		Assert.assertTrue(userControl.isElementPresent(DTSUserHomePage.DRAFT));
		Assert.assertTrue(userControl.isElementPresent(DTSUserHomePage.READY_PUBLISH));
		Assert.assertTrue(userControl.isElementPresent(DTSUserHomePage.OVERDUE));
		Assert.assertTrue(userControl.isElementPresent(DTSUserHomePage.RECENTLY_PUSLISHED));
	}
	
	/*
	 * Verify that "Needs Attention" and "Suspended" links in "Catalog" module is hidden when their item count is 0
	 */
	@Test
	public void TC020DV_10(){
		userControl.addLog("Verify that 'Needs Attention' and 'Suspended' links in 'Catalog' module is hidden when their item count is 0");
		userControl.addErrorLog("PDPP-1405 - 040 Product Detail: The error message 'Error! timeout' is displayed and the "
				+ "portal is not redirected to product list page when deleting a published product.");
		/*
		 	Pre-Condition:
			_To make "Needs Attention" link displays item count, there is at least one accessory's tuning  is declined or revoked. (Please see test case ��. for more details)
			_To make "Suspended" link displays item count, there is at least one accessory's published status is suspended (Please see test case .... for more details)
			
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Home" page
			Verify that The "Needs Attention" and "Suspended" links are hidden in "Catalog" module

		*/
		
		/*
		 * Precondition: There is no "Suspended" and "Need Attention" product
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		companyControl.click(PageHome.LINK_USERS);
		// Select a user from table
		userControl.selectUserInfoByEmail(DTS_USER);
		// Click Edit link
		companyControl.click(UserMgmt.EDIT);
		// Enable all privileges
		userControl.enableAllPrivileges();
		companyControl.click(AddUser.SAVE);
		loginControl.logout();
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// Navigate to product page
		userControl.click(PageHome.linkAccessories);
		// Delete all suspended products
		productControl.deleteAllProductWithPublishedStatus("Suspended");
		productControl.deleteAllProductWithPublishedStatus("Needs Attention");
		/*
		 * ***********************************************************
		 */
		// 3. Navigate to "Home" page
		userControl.click(PageHome.linkHome);
		/*
		 * Verify that The "Needs Attention" and "Suspended" links are hidden in "Catalog" module
		 */
		Assert.assertFalse(userControl.isElementPresent(DTSHome.CATALOG_SUSPENDED));
		Assert.assertFalse(userControl.isElementPresent(DTSHome.CATALOG_NEED_ATTENTION));
	}
	
	/*
	 * Verify that the portal redirects to �Home� page when navigating to log in page directly without logging out
	 */
	@Test
	public void TC020DV_11(){
		userControl.addLog("ID : TC020DV_11 : Verify that the portal redirects to �Home� page when navigating to log in page directly without logging out");
		/*
		 	1. Launch a web browser 
			2. Navigate to DTS portal
			3. Log into DTS portal as a partner user successfully
			4. Type �http://%DTS_Portal_Server/saap�
		*/
		// 3. Log into DTS portal as a partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 4. Type �http://%DTS_Portal_Server/saap�
		String url = "http://devportal.dts.com/saap";
		driver.get(url);
		/*
		 * Verify that The �Home� page should display
		 */
		Assert.assertEquals(userControl.existsElement(PartnerHomePage.getElementInfo()), true);
	}
	
	/*
	 * Verify that the home page view displays properly
	 */
	@Test
	public void TC020DPSV_01(){
		userControl.addLog("ID : TC020DPSV_01 : Verify that the home page view displays properly");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Home" page
			4. Select a partner from "View as" dropdown.
			"VP: Verify that the Welcome message is displayed with parttern �Welcome, %Partner_Company_Name�
			VP: Verify that the Product module displays ""Add Product"", ""Draft"" and 
			""Ready to Publish"" links."
			VP: The brief overview of the Headphone X platform displays below DTS HPX logo with content:
			�DTS Headphone:X + Your Headphones = Incredible, mind-blowing sound!
			
			Upload your company's headphone data here. The global Headphone:X technology platform delivers the latest tunings, pictures, and related meta-data to consumers around the world in real time. Only DTS Headphone:X brings immsersive cinematic audio up to 11.1 channels over your headphones.
			
			What is DTS Headphone:X?
			"Headphone:X makes all of your content sound better."
			
			Movies: Headphone:X delivers an immersive, cinematic audio experience with your headphones. An experience that consumers have come to expect from a full, 11.1 channel surround sound movie theater. But it doesn't just put a home theater audio experience in everyone's pockets, it works with music and games, too.
			
			Music: If audio tracks are recorded in Headphone:X surround sound, consumers can enjoy the full benefit of Headphone:X technology. But it can also improve the overall quality of everyone's existing music as well. And your "OEM EQ" ensures that the consumer's audio experience with their existing music sounds exactly how you intended.
			
			Gaming: Deeper and more immersive gaming experiences. Headphone:X gives gamers an unfair advantage by giving a new dimension to their hearing. So now gamers can hear their enemy before they see them.�
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 4. Set "View As" dropdown to another item other than DTS Inc
		userControl.selectOptionInDropdown(DTSHome.PARTNERS_LIST_DROPDOWN, PARTNER_COMPANY_NAME);
		/*
		 * Verify that the Welcome message is displayed with pattern Welcome, Partner_Company_Name
		 */
		Assert.assertEquals(userControl.getTextByXpath(PartnerHomePage.PAGE_HEADER),"Welcome, " + PARTNER_COMPANY_NAME);
		/*
		Verify that the Catalog module always displays 
		"Add Accessory", 
		"Draft" and 
		"Ready to Publish" links.
		 */
		Assert.assertEquals(true, userControl.existsElement(PartnerHomePage.getListCatalog()));
		/*
		 * VP: The brief overview of the Headphone X platform displays below DTS HPX logo with content
		 */
		ArrayList<String> content = userControl.getTextListByXpaths(PartnerHomePage.listContentXpath());
		Assert.assertTrue(ListUtil.containsAll(content, PartnerHomePage.getContentMessage()));
		
	}
	
	/*
//	 * Verify that "Needs Attention" and "Suspended" links in "Products" module only display when their item count is larger than 0
//	 */
//	@Test
//	public void TC020DPSV_04(){
//		userControl.addLog("ID : TC020DPSV_04 : Verify that 'Needs Attention' and 'Suspended' links in 'Products' module only display when their item count is larger than 0");
//		userControl.addErrorLog("PDPP-181");
//		/*
//		 	Pre-Condition:
//			_To make "Needs Attention" link displays item count, there is at least one product's tuning  is declined or revoked.
//			_To make "Suspended" link displays item count, there is at least one product's published status is suspended.
//			1. Navigate to DTS portal
//			2. Log into DTS portal as a partner user successfully
//			3. Navigate to "Home" page
//			4. Select a partner from "View as" dropdown
//		*/
//		/*
//		 * Precondition: Create a "Needs Attention" and "Suspend" product
//		 */
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// Navigate to Product page
//		userControl.click(PageHome.linkAccessories);
//		// Click Add product link
//		userControl.click(ProductModel.ADD_PRODUCT);
//		// Create published product
//		Hashtable<String,String> dataSuspend = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataSuspend);
//		// Suspend product
//		userControl.click(ProductDetailModel.SUSPEND);
//		userControl.selectConfirmationDialogOption("Suspend");
//		// Click Add product link
//		userControl.click(ProductModel.ADD_PRODUCT);
//		// Create Need attention product
//		Hashtable<String,String> dataNeedAttention = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataNeedAttention);
//		// Approve tuning
//		userControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
//		userControl.click(ProductDetailModel.APPROVE_TUNING);
//		// Revoke tuning
//		userControl.click(ProductDetailModel.REVOKE_TUNING);
//		/*
//		 * *******************************************************************
//		 */
//		// 3. Navigate to "Home" page
//		userControl.click(PageHome.linkHome);
//		// 4. Select a partner from "View as" dropdown
//		userControl.selectOptionInDropdown(DTSHome.PARTNERS_LIST_DROPDOWN, PARTNER_COMPANY_NAME);
//		/*
//		 * The "Needs Attention" and "Suspended" links with their item count are displayed in "Catalog" module
//		 */
//		Assert.assertTrue(userControl.isElementPresent(DTSHome.CATALOG_SUSPENDED));
//		Assert.assertTrue(userControl.isElementPresent(DTSHome.CATALOG_NEED_ATTENTION));
//	}
	
	/*
	 * Verify that "Needs Attention" and "Suspended" links in "Products" module is hidden when their item count is 0
	 */
	@Test
	public void TC020DPSV_05(){
		userControl.addLog("ID : TC020DPSV_05 : Verify that 'Needs Attention' and 'Suspended' links in 'Products' module is hidden when their item count is 0");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Select a partner from View As dropdown
		userControl.selectOptionInDropdown(DTSHome.PARTNERS_LIST_DROPDOWN, PARTNER_COMPANY_NAME);
		// Navigate to product page
		//userControl.click(PageHome.linkAccessories);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Delete all suspended products
		productControl.deleteAllProductWithPublishedStatus("Suspended");
		productControl.deleteAllProductWithPublishedStatus("Needs Attention");
		/*
		 * ***********************************************************
		 */
		// 3. Navigate to "Home" page
		// 4. Select a partner from "View as" dropdown
		userControl.click(PageHome.LINK_PARTNER_HOME);
		/*
		 * Verify that The "Needs Attention" and "Suspended" links are hidden in "Accessories" module
		 */
		Assert.assertFalse(userControl.isElementPresent(DTSHome.CATALOG_SUSPENDED));
		Assert.assertFalse(userControl.isElementPresent(DTSHome.CATALOG_NEED_ATTENTION));
	}
		
	/*
	 * Verify that the �Add Product� link is not displayed in �Products� module when the user has no �Add and manage Products� privilege
	 */
	@Test
	public void TC020DPSV_07(){
		userControl.addLog("ID : TC020DPSV_07 : Verify that the �Add Product� link is not displayed in �Products� module when the user has no �Add and manage Products� privilege");
		/*
		 	Precondition: The DTS user has no �Add and manage product� privilege.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Home" page
			4. Select a partner from "View as" dropdown.
		*/
		/*
		 * Precondition: The DTS user has no �Add and manage product� privilege
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a DTS user
		userControl.selectUserInfoByEmail(DTS_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Add and manage products" privilege
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Add_and_manage_products.getName());
		userControl.click(AddUser.SAVE);
		// Log out
		userControl.logout();
		/*
		 * **********************************************************
		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 3. Navigate to "Home" page
		// 4. Select a partner from "View as" dropdown.
		userControl.selectOptionInDropdown(DTSHome.PARTNERS_LIST_DROPDOWN, PARTNER_COMPANY_NAME);
		/*
		 * Verify that The �Add Products� link is not displayed
		 */
		Assert.assertFalse(userControl.isElementPresent(PartnerHomePage.PARTNER_CATALOG_ADD_ACCESSORY));
	}
}
