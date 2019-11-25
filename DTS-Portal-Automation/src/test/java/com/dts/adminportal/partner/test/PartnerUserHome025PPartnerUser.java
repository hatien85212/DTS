package com.dts.adminportal.partner.test;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;

import dts.com.adminportal.model.DTSUserHomePage;
import dts.com.adminportal.model.PartnerHomePage;
import dts.com.adminportal.model.Xpath;

public class PartnerUserHome025PPartnerUser extends CreatePage{
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
	 * Verify that the text "View as" is not displayed on top navigation bar when logging to DTS portal as partner user
	 */
	@Test
	public void TC025PPU_01(){
		result.addLog("ID : TC025PPU_01 : Verify that the text 'View as' is not displayed on top navigation bar when logging to DTS portal as partner user" );
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
		 */
		// The text "View as" is not displayed on top navigation bar
		Assert.assertFalse(home.isElementPresent(DTSUserHomePage.VIEW_AS));
	}
	
	/*
	 * Verify that the "Partner/Company" dropdown is not displayed on top navigation bar when logging to DTS portal as partner user
	 */
	@Test
	public void TC025PPU_02(){
		result.addLog("ID : TC025PPU_02 : Verify that the 'Partner/Company' dropdown is not displayed on top navigation bar when logging to DTS portal as partner user" );
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
		 */
		// The "Partner/Company" is not displayed on top navigation bar
		Assert.assertFalse(home.isElementPresent(DTSUserHomePage.PARTNERS_LIST));
	}
	
	/*
	 * Verify that the company's name of partner user displayed correctly on top navigation bar
	 */
	@Test
	public void TC025PPU_03(){
		result.addLog("ID : TC025PPU_03 : Verify that the company's name of partner user displayed correctly on top navigation bar");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
		 */
		// The company’s name of partner user correctly on top navigation bar and next to partner's email
		Assert.assertEquals(home.getTextByXpath(PartnerHomePage.DTS_PARTNER_NAME), partner_company_name);
	}
	
	/*
	 * Verify that the partner's email is displayed correctly on top navigation bar
	 */
	@Test
	public void TC025PPU_04(){
		result.addLog("ID : TC025PPU_04 : Verify that the partner's email is displayed correctly on top navigation bar");
		/*
			 1. Navigate to DTS portal
			 2. Log into DTS portal as a partner user successfully
		 */
		// The partner's email is displayed correctly on top navigation bar
		Assert.assertEquals(home.getTextByXpath(PartnerHomePage.EMAIL), superpartneruser);
	}
	
	/*
	 * Verify that the user menu is displayed including "User Account" and "Sign Out" items when clicking on partner's email 
	 */
	@Test
	public void TC025PPU_05(){
		result.addLog("ID : TC025PPU_05 : Verify that the user menu is displayed including 'User Account' and 'Sign Out' items when clicking on partner's email");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click on partner's mail on top navigation bar
		 */
		// 3. Click on partner's mail on top navigation bar
		home.click(PartnerHomePage.EMAIL);
		/*
		 * Verify that The user menu is displayed which including "User Account" and "Sign Out" items
		 */
		HashMap<String, String> menu = home.getItemUserMenu(PartnerHomePage.DROP_DOWN_MENU);
		Assert.assertEquals(menu.get("option 1"), PartnerHomePage.GET_USER_MENU().get("option 1"));
		Assert.assertEquals(menu.get("option 2"), PartnerHomePage.GET_USER_MENU().get("option 2"));
	}
	
	/*
	 * Verify that the DTS portal is redirected to default "Home" page when clicking on DTS logo.
	 */
	@Test
	public void TC025PPU_06(){
		result.addLog("ID : TC025PPU_06 : Verify that the DTS portal is redirected to default'Home' page when clicking on DTS logo.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Navigate to another page other than "Home" page
			4. Click on DTS logo
		 */
		// 3. Navigate to another page other than "Home" page
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Click on DTS logo
		home.click(PartnerHomePage.LOGO);
		/*
		 * Verify that The DTS portal is redirected to default "Home" page
		 */
		Assert.assertEquals(home.existsElement(PartnerHomePage.getElementInfo()).getResult(), "Pass");
	}
	
	/*
	 * Verify that the portal redirects to “Home” page when navigating to log in page directly without logging out
	 */
	@Test
	public void TC025PPU_07(){
		result.addLog("ID : TC025PPU_07 : Verify that the portal redirects to “Home” page when navigating to log in page directly without logging out");
		/*
		 	1. Launch a web browser 
			2. Navigate to DTS portal
			3. Log into DTS portal as a partner user successfully
			4. Type “http://%DTS_Portal_Server/saap”
		 */
		// 4. Type “http://%DTS_Portal_Server/saap”
		driver.get(siteBase.toString());
		/*
		 * Verify that The “Home” page should display
		 */
		Assert.assertEquals(home.existsElement(PartnerHomePage.getElementInfo()).getResult(), "Pass");
	}
}
