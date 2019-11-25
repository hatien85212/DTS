package com.dts.adminportal.partner.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.DTSUserHomePage;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PartnerHomePage;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.TestData;

public class PartnerHome025PPartner extends BasePage{

	@Override
	protected void initData() {
	}

	
//	@BeforeMethod
//	public void loginBeforeTest() {
//		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//	}
	
	/*
	 * Verify that the partner user's menu display properly.
	 */
	@Test
	public void TC025PPU_01(){
		userControl.addLog("ID : TC025PPU_01 : Verify that the partner user's menu display properly." );
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			VP: VP: The text "View as" is not displayed on top navigation bar
			VP: The "Partner/Company" is not displayed on top navigation bar
			VP: The company’s name of partner user displays correctly on top navigation bar and next to partner's email
			VP: The partner's email is displayed correctly on top navigation bar
			3. Click on partner's mail on top navigation bar
			VP: The user menu is displayed which including "User Account" and "Sign Out" items.
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		//VP: The text "View as" is not displayed on top navigation bar
		Assert.assertFalse(userControl.isElementPresent(DTSUserHomePage.VIEW_AS));
		//VP: The "Partner/Company" is not displayed on top navigation bar
		Assert.assertFalse(userControl.isElementPresent(DTSUserHomePage.PARTNERS_LIST));
		//VP: The company’s name of partner user displays correctly on top navigation bar and next to partner's email
		Assert.assertEquals(userControl.getTextByXpath(PartnerHomePage.DTS_PARTNER_NAME), PARTNER_COMPANY_NAME);
		//VP: The partner's email is displayed correctly on top navigation bar
		Assert.assertEquals(userControl.getTextByXpath(PartnerHomePage.EMAIL), SUPER_PARTNER_USER);
		
		// 3. Click on partner's mail on top navigation bar
		userControl.click(PartnerHomePage.EMAIL);
		//VP: The user menu is displayed which including "User Account" and "Sign Out" items.
		HashMap<String, String> menu = userControl.getItemUserMenu(PartnerHomePage.DROP_DOWN_MENU);
		Assert.assertEquals(menu.get("option 1"), PartnerHomePage.GET_USER_MENU().get("option 1"));
		Assert.assertEquals(menu.get("option 2"), PartnerHomePage.GET_USER_MENU().get("option 2"));
	}
	

	/*
	 * Verify that the DTS portal is redirected to default "Home" page when clicking on DTS logo.
	 */
	@Test
	public void TC025PPU_06(){
		userControl.addLog("ID : TC025PPU_06 : Verify that the DTS portal is redirected to default'Home' page when clicking on DTS logo.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Navigate to another page other than "Home" page
			4. Click on DTS logo
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Navigate to another page other than "Home" page
		userControl.click(PageHome.LINK_PARTNER_USER);
		// 4. Click on DTS logo
		userControl.click(PartnerHomePage.LOGO);
		/*
		 * Verify that The DTS portal is redirected to default "Home" page
		 */
		Assert.assertEquals(userControl.existsElement(PartnerHomePage.getElementInfo()), true);
	}
	
	/*
	 * Verify that the portal redirects to ï¿½Homeï¿½ page when navigating to log in page directly without logging out
	 */
	@Test
	public void TC025PPU_07(){
		userControl.addLog("ID : TC025PPU_07 : Verify that the portal redirects to ï¿½Homeï¿½ page when navigating to log in page directly without logging out");
		/*
		 	1. Launch a web browser 
			2. Navigate to DTS portal
			3. Log into DTS portal as a partner user successfully
			4. Type ï¿½http://%DTS_Portal_Server/saapï¿½
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 4. Type ï¿½http://%DTS_Portal_Server/saapï¿½
		driver.get(url);
		/*
		 * Verify that The ï¿½Homeï¿½ page should display
		 */
		Assert.assertEquals(userControl.existsElement(PartnerHomePage.getElementInfo()), true);
	}
	

	/*
	 * Verify that "Home" page displays correct content as specific
	 */
	@Test
	public void TC025PP_01(){
		userControl.addLog("ID : TC025PP_01 : Verify that 'userControl' page displays correct content as specific");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Navigate to "Home" page
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Verify that the Welcome message is displayed with pattern ï¿½Welcome, %Partner_Company_Nameï¿½
		Assert.assertEquals(userControl.getTextByXpath(PartnerHomePage.PAGE_HEADER), PartnerHomePage.TOP_NAVIGATION_MESSAGE.Welcome.getName() + PARTNER_COMPANY_NAME);
		// Get Welcome message position
		int welcomeMessage_Pos = driver.findElement(By.xpath(PartnerHomePage.PAGE_HEADER)).getLocation().y;
		userControl.addLog(PartnerHomePage.TOP_NAVIGATION_MESSAGE.Welcome_Message_position.getName() + welcomeMessage_Pos);
		// Get DTS Logo position
		int dtsLogo_Pos = driver.findElement(By.xpath(PartnerHomePage.HOME_LOGO)).getLocation().y;
		userControl.addLog(PartnerHomePage.TOP_NAVIGATION_MESSAGE.DTS_Logo_position.getName() + dtsLogo_Pos);
		/*
		 *  Verify that the DTS HPX logo displays below the welcome message
		 */
		Assert.assertTrue(welcomeMessage_Pos < dtsLogo_Pos);
		
		// Verify that the Product module displays "Add Product", "Draft" and "Ready to Publish" links
		Assert.assertEquals(true, userControl.existsElement(PartnerHomePage.getListCatalog()));
		
		// Verify that ï¿½homeControlï¿½ page displays a brief overview of the Headphone X platform
		ArrayList<String> content = userControl.getTextListByXpaths(PartnerHomePage.listContentXpath());
		Assert.assertTrue(ListUtil.containsAll(content, PartnerHomePage.getContentMessage()));
	}
	
	/*
	 * Verify that "Needs Attention" and "Suspended" links in "Accessories" module only display when their item count is larger than 0
	 */
	@Test
	public void TC025PP_02(){
		userControl.addLog("ID : TC025PP_02 : Verify that 'Needs Attention' and 'Suspended' links in 'Accessories' module only display when their item count is larger than 0");
		//userControl.addErrorLog(PartnerHomePage.TOP_NAVIGATION_MESSAGE[3]);
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
		// Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		userControl.click(PageHome.linkAccessories);
		// Click Add product link
		userControl.click(ProductModel.ADD_PRODUCT);
		// Create published product
		Hashtable<String,String> dataSuspend = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndSuspend(dataSuspend);
		// Click Add product link
		userControl.click(PageHome.linkAccessories);
		userControl.click(ProductModel.ADD_PRODUCT);
		// Create Need attention product
		Hashtable<String,String> dataNeedAttention = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productWf.addProductAndTunningAndMarketing(dataNeedAttention, false);
		// Revoke tuning
		//userControl.click(ProductDetailModel.REVOKE_TUNING);
		productWf.revokeTuning();
		// Log out
		userControl.logout();
		/*
		 * *******************************************************************
		 */
		// 2. Log into DTS portal as a partner user successfully
		// 3. Navigate to "Home" page
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		/*
		 * Verify that The "Needs Attention" and "Suspended" links with their item count are displayed in "Accessories" module
		 */
		Assert.assertTrue(userControl.isElementPresent(PartnerHomePage.PARTNER_CATALOG_SUSPENDED));
		Assert.assertTrue(userControl.isElementPresent(PartnerHomePage.PARTNER_CATALOG_NEED_ATTENTION));
		
		// Delete product
		productControl.click(PartnerHomePage.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(dataNeedAttention.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		// Change report filter to "Suspended"
		productControl.selectReportsFilterByText(PageHome.accessoryFilterSelect, Constant.ACCESSORY_SUSPENDED);
		productControl.selectAccessorybyName(dataSuspend.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that "Needs Attention" and "Suspended" links in "Accessories" module is hidden when their item count is 0
	 */
	@Test
	public void TC025PP_03(){
		userControl.addLog("ID : TC025PP_03 : Verify that 'Needs Attention' and 'Suspended' links in 'Accessories' module is hidden when their item count is 0");
		userControl.addErrorLog(PartnerHomePage.TOP_NAVIGATION_MESSAGE.PDPP.getName());
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		userControl.click(PageHome.linkAccessories);
		// Delete all suspended products
		productControl.deleteAllProductWithPublishedStatus(Constant.ACCESSORY_SUSPENDED);		
		productControl.deleteAllProductWithPublishedStatus(Constant.ACCESSORY_NEEDATTENTION);
		// Log out
		userControl.logout();
		//2. Log into DTS portal as a partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Navigate to "Home" page
		userControl.click(PageHome.LINK_PARTNER_HOME);
		/*
		 * Verify that The "Needs Attention" and "Suspended" links are hidden in "Accessories" module
		 */
		Assert.assertFalse(userControl.isElementPresent(PartnerHomePage.PARTNER_CATALOG_SUSPENDED));
		Assert.assertFalse(userControl.isElementPresent(PartnerHomePage.PARTNER_CATALOG_NEED_ATTENTION));
	}

}
