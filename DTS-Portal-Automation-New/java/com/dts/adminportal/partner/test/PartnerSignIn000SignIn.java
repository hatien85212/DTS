package com.dts.adminportal.partner.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.ForgotSignIn;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PageLogin;
import com.dts.adminportal.model.PartnerHomePage;

public class PartnerSignIn000SignIn extends BasePage{
		
	@Override
	protected void initData() {
	}	

	/*
	 * Verify that the Login page display DTS logo, 
	 * "Email" and "Password" fields, Sign in button and "Forgot sign in info?" link correctly
	 * 1. Launch a web browser
	 * 2. Navigate to DTS portal
	 */
	@Test
	public void TC000SI_01(){
		userControl.addLog("ID : TC000SI_01 : Verify Login page");
		/*
		 * The Login page display DTS logo, "Email" and "Password" fields, "Sign in" button and "Forgot sign in info?" link
		 */
		Assert.assertEquals(true, userControl.existsElement(PageLogin.getHash()));
	}
	
	/*
	 * Verify that valid credential is granted and invalid credential is not granted to access to partner portal
	 */
	@Test
	public void TC000SI_02(){
		userControl.addLog("ID : TC000SI_02 : Verify that valid credential is granted and invalid credetial is not granted to access to partner portal");
		/*
		1. Navigate to DTS portal
		2. Leave the "Email" and "Password" field blank
		3. Click "Sign in" button
		VP:The error message is displayed which inform that the email and password are invalid.
		4. Type valid email into "Email" field
		5. Type invalid password into "Password" field
		6. Click "Sign in" button
		VP: The error message is displayed which inform that the email and password are invalid.
		7. Type invalid email into "Email" field
		8. Type valid password into "Password" field
		9. Click "Sign in" button
		VP: The error message is displayed which inform that the email and password are invalid.
		10. Type valid email into "Email" field
		11. Type valid password into "Password" field
		12. Click "Sign in" button
		*/
		// 2. Leave the "Email" and "Password" field blank
		// 3. Click "Sign in" button
		userControl.click(PageLogin.SIGN_IN);
		//VP: Verify that The error message is displayed which inform that the email and password are invalid. 
		Assert.assertTrue(userControl.checkMessageDisplay(PageLogin.loginError));
		
		// 4. Type valid email into "Email" field
		userControl.type(PageLogin.USERNAME, SUPER_PARTNER_USER);
		// 5. Type invalid password into "Password" field
		userControl.type(PageLogin.PASSWORD, SUPER_PARTNER_PASSWORD + RandomStringUtils.randomNumeric(5));
		// 6. Click "Sign in" button
		userControl.click(PageLogin.SIGN_IN);
		//VP: Verify that The error message is displayed which inform that the email and password are invalid.
		Assert.assertTrue(userControl.checkMessageDisplay(PageLogin.loginError));	
		
		// 7. Type valid email into "Email" field
		userControl.type(PageLogin.USERNAME, SUPER_PARTNER_USER + RandomStringUtils.randomAlphabetic(5));
		// 8. Type invalid password into "Password" field
		userControl.type(PageLogin.PASSWORD, SUPER_PARTNER_PASSWORD);
		// 9. Click "Sign in" button
		userControl.click(PageLogin.SIGN_IN);
		//Verify that The error message is displayed which inform that the email and password are invalid.
		Assert.assertTrue(userControl.checkMessageDisplay(PageLogin.loginError));
		
		// 10. Type valid email into "Email" field
		userControl.type(PageLogin.USERNAME, SUPER_PARTNER_USER);
		// 11. Type valid password into "Password" field
		userControl.type(PageLogin.PASSWORD, SUPER_PARTNER_PASSWORD);
		// 12. Click "Sign in" button
		userControl.click(PageLogin.SIGN_IN);
		//VP:  The default DTS page is displayed
		Assert.assertEquals(true, userControl.existsElement(PartnerHomePage.getElementInfo()));
	}
	
	/*
	 * Verify that the "Forgot Sign In" feature works correctly. 
	 */
	@Test
	public void TC000SI_03(){
		userControl.addLog("ID : TC000SI_03 : Verify that the 'Forgot Sign In' feature works correctly.");
		/*
			1. Navigate to DTS login portal
			2. Click on "Forgot sign in info?" link
			VP: The "Forgot Sign In" page is displayed. And The DTS logo, the Headphone:X Portal title and message “If you've forgotten your sign in information, please contact your company's internal Headphone:X coordinator. He or she will be able to provide additional assistance.” are displayed in Forgot Sign In page
			3. Click on DTS logo
			VP:The DTS login portal page is displayed. 

		 */
		// 2. Click on "Forgot sign in info?" link
		userControl.click(PageLogin.FORGOT);
		//VP: The "Forgot Sign In" page is displayed. And The DTS logo, the Headphone:X Portal title and message “If you've forgotten your sign in information, please contact your company's internal Headphone:X coordinator. He or she will be able to provide additional assistance.” are displayed in Forgot Sign In page
		Assert.assertEquals(userControl.existsElement(ForgotSignIn.getHash()), true);
		Assert.assertTrue(userControl.isElementPresent(ForgotSignIn.LOGO));
		Assert.assertEquals(userControl.getTextByXpath(ForgotSignIn.TITLE),ForgotSignIn.TITLE_CONTENT);
		Assert.assertEquals(userControl.getTextByXpath(ForgotSignIn.INSTRUCT),ForgotSignIn.INSTRUCTION_CONTENT);
		//3. Click on DTS logo
		userControl.click(PageHome.logoImgHome);
		//VP:The DTS login portal page is displayed. 
		Assert.assertEquals(userControl.existsElement(PageLogin.getListElementOnLogin()), true);
	}
	
	/*
	 * Verify that clicking on DTS logo will redirect to "dts.com" page
	 */
	@Test
	public void TC000SI_04(){
		userControl.addLog("ID : TC000SI_04 : Verify that clicking on DTS logo will redirect to 'dts.com' page");
		/*
		 	1. Navigate to DTS portal
			2. Click on DTS logo
		 */
		// 2. Click on DTS logo
		userControl.click(PageLogin.LOGO);
		/*
		 * Verify that The "dts.com" page is displayed
		 */
		String mainWindow = driver.getWindowHandle();
		userControl.switchWindow();
		Assert.assertEquals(userControl.getCurrentURL(), PageLogin.urlIcon);
		// Close "dts.com" page and switch to default window
		driver.close();
		driver.switchTo().window(mainWindow);
	}
}
