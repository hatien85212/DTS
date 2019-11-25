package com.dts.adminportal.partner.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;

import dts.com.adminportal.model.ForgotSignIn;
import dts.com.adminportal.model.Xpath;

public class PartnerUserSignIn001ForgotSignIn extends CreatePage{
	private HomeController home;
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}

	/*
	 * Verify that the Forgot Sign In page displays DTS logo, Headphone:X Portal title and instruction for resetting password.
	 */
	@Test
	public void TC001FS_01() {
		result.addLog("ID : TC001FS_01 : Verify that the Forgot Sign In page displays DTS logo, Headphone:X Portal title and instruction for resetting password. ");
		/*
		 1. Navigate to DTS login portal 
		 2. Click on "Forgot sign in info?" link 
		 VP. Verify that the Forgot Sign In page displays DTS logo, Headphone:X Portal title and instruction for resetting password.
		 */
		
		// 1. Navigate to DTS login portal 
		// 2. Click on "Forgot sign in info?" link
		home.click(Xpath.forgot);
		// Verify that the Forgot Sign In page displays DTS logo, Headphone:X Portal title and instruction for resetting password.
		Assert.assertTrue(home.isElementPresent(ForgotSignIn.LOGO));
		Assert.assertEquals(home.getTextByXpath(ForgotSignIn.TITLE),ForgotSignIn.TITLE_CONTENT);
		Assert.assertEquals(home.getTextByXpath(ForgotSignIn.INSTRUCT),ForgotSignIn.INSTRUCTION_CONTENT);
		
	}
	
	@Test
	public void TC001FS_02() {
		result.addLog("ID : TC001FS_02 : Verify that clicking on DTS logo will redirect to DTS login portal page ");
		/*
		1. Navigate to DTS login portal
		2. Click on "Forgot sign in info?" link
		3. Verify that the "Forgot Sign In" page is displayed
		4. Click on DTS logo
		VP: The DTS login portal page is displayed.
		 */
		
		// 1. Navigate to DTS login portal 
		// 2. Click on "Forgot sign in info?" link
		home.click(Xpath.forgot);
		// 3. Verify that the "Forgot Sign In" page is displayed 
		Assert.assertTrue(home.isElementPresent(ForgotSignIn.LOGO));
		Assert.assertEquals(home.getTextByXpath(ForgotSignIn.TITLE),ForgotSignIn.TITLE_CONTENT);
		Assert.assertEquals(home.getTextByXpath(ForgotSignIn.INSTRUCT),ForgotSignIn.INSTRUCTION_CONTENT);
		// 4. Click on DTS logo
		home.click(Xpath.logoImgHome);
		/*
		 * Verify that The DTS login portal page is displayed
		 */
		Assert.assertEquals(home.existsElement(Xpath.getListElementOnLogin()).getResult(), "Pass");
	}
}
