package com.dts.adminportal.partner.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;

import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.ForgotSignIn;
import dts.com.adminportal.model.LoginPage;
import dts.com.adminportal.model.PartnerHomePage;

public class PartnerUserSignIn000SignIn extends CreatePage{
	private HomeController home;
	
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}
	
	/*
	 * Verify that the Login page display DTS logo, 
	 * "Email" and "Password" fields, Sign in button and "Forgot sign in info?" link correctly
	 * 1. Launch a web browser
	 * 2. Navigate to DTS portal
	 */
	@Test
	public void TC000SI_01(){
		result.addLog("ID : TC000SI_01 : Verify Login page");
		/*
		 * The Login page display DTS logo, "Email" and "Password" fields, "Sign in" button and "Forgot sign in info?" link
		 */
		result = home.existsElement(LoginPage.getHash());
		Assert.assertEquals("Pass", result.getResult());
	}
	
	/*
	 * Verify that user is able to log into DTS portal successfully with valid email and password
	 */
	@Test
	public void TC000SI_02(){
		result.addLog("ID : TC000SI_02 : Verify that user is able to log into DTS portal successfully with valid email and password");
		/*
			1. Navigate to DTS portal
			2. Type valid email into "Email" field
			3. Type valid password into "Password" field
			4. Click "Sign in" button
		*/
		// 2. Type valid email into "Email" field
		home.type(LoginPage.USERNAME, superpartneruser);
		// 3. Type valid password into "Password" field
		home.type(LoginPage.PASSWORD, superpartnerpassword);
		// 4. Click "Sign in" button
		home.click(LoginPage.SIGN_IN);
		/*
		 * The default DTS page is displayed
		 */
		Assert.assertEquals("Pass", home.existsElement(PartnerHomePage.getElementInfo()).getResult());
	}
	
	/*
	 * Verify that user is unable to log into DTS portal with blank email and password
	 */
	@Test
	public void TC000SI_03(){
		result.addLog("ID : TC000SI_03 : Verify that user is unable to log into DTS portal with blank email and password.");
		/*
			1. Navigate to DTS portal
			2. Leave the "Email" and "Password" field blank
			3. Click "Sign in" button
		*/
		// 2. Leave the "Email" and "Password" field blank
		// 3. Click "Sign in" button
		home.click(LoginPage.SIGN_IN);
		/*
		 * Verify that The error message is displayed which inform that the email and password are invalid
		 */
		Assert.assertTrue(home.checkMessageDisplay(Constant.loginError));
	}
	
	/*
	 * Verify that user is unable to log into DTS portal with valid email and invalid password
	 */
	@Test
	public void TC000SI_04(){
		result.addLog("ID : TC000SI_04 : Verify that user is unable to log into DTS portal with valid email and invalid password");
		/*
		 	1. Navigate to DTS portal
			2. Type valid email into "Email" field
			3. Type invalid password into "Password" field
			4. Click "Sign in" button
		 */
		// 2. Type valid email into "Email" field
		home.type(LoginPage.USERNAME, superpartneruser);
		// 3. Type invalid password into "Password" field
		home.type(LoginPage.PASSWORD, superpartnerpassword + RandomStringUtils.randomNumeric(5));
		// 4. Click "Sign in" button
		home.click(LoginPage.SIGN_IN);
		/*
		 * Verify that The error message is displayed which inform that the email and password are invalid.
		 */
		Assert.assertTrue(home.checkMessageDisplay(Constant.loginError));
	}
	
	/*
	 * Verify that user is unable to log into DTS portal with invalid email and valid password
	 */
	@Test
	public void TC000SI_05(){
		result.addLog("ID : TC000SI_05 : Verify that user is unable to log into DTS portal with invalid email and valid password");
		/*
		 	1. Navigate to DTS portal
			2. Type invalid email into "Email" field
			3. Type valid password into "Password" field
			4. Click "Sign in" button
		 */
		// 2. Type valid email into "Email" field
		home.type(LoginPage.USERNAME, superpartneruser + RandomStringUtils.randomAlphabetic(5));
		// 3. Type invalid password into "Password" field
		home.type(LoginPage.PASSWORD, superpartnerpassword);
		// 4. Click "Sign in" button
		home.click(LoginPage.SIGN_IN);
		/*
		 * Verify that The error message is displayed which inform that the email and password are invalid.
		 */
		Assert.assertTrue(home.checkMessageDisplay(Constant.loginError));
	}
	
	/*
	 * Verify that the "Forgot Sign In" page is displayed when licking on "Forgot sign in info?"
	 */
	@Test
	public void TC000SI_06(){
		result.addLog("ID : TC000SI_06 : Verify that the 'Forgot Sign In' page is displayed when licking on 'Forgot sign in info?''");
		/*
		 	1. Navigate to DTS login portal
			2. Click on "Forgot sign in info?" link
		 */
		// 2. Click on "Forgot sign in info?" link
		home.click(LoginPage.FORGOT);
		/*
		 * Verify that The "Forgot Sign In" page is displayed
		 */
		Assert.assertEquals(home.existsElement(ForgotSignIn.getHash()).getResult(), "Pass");
	}
	
	/*
	 * Verify that clicking on DTS logo will redirect to "dts.com" page
	 */
	@Test
	public void TC000SI_07(){
		result.addLog("ID : TC000SI_07 : Verify that clicking on DTS logo will redirect to 'dts.com' page");
		/*
		 	1. Navigate to DTS portal
			2. Click on DTS logo
		 */
		// 2. Click on DTS logo
		home.click(LoginPage.LOGO);
		/*
		 * Verify that The "dts.com" page is displayed
		 */
		String mainWindow = driver.getWindowHandle();
		home.switchWindow();
		Assert.assertEquals(home.getCurrentURL(), "http://listen.dts.com/");
		// Close "dts.com" page and switch to default window
		driver.close();
		driver.switchTo().window(mainWindow);
	}
	
	/*
	 * Verify that the portal redirects to “Login” page when navigating directly to  specific page
	 */
	@Test
	public void TC000SI_08(){
		result.addLog("ID : TC000SI_08 : Verify that the portal redirects to “Login” page when navigating directly to  specific page");
		/*
		 	1. Launch a web browser
			2. Type a specific address of DTS portal into web browser address bar
			3. Try to proceed to specific page
		*/
		// 2. Type a specific address of DTS portal into web browser address bar
		// 3. Try to proceed to specific page
		String url = "http://devportal.dts.com/saap/catalog";
		driver.get(url);
		/*
		 * Verify that The DTS login portal is displayed
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getHash()).getResult(), "Pass");
	}
}
