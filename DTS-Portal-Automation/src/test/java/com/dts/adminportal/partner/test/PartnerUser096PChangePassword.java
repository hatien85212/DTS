package com.dts.adminportal.partner.test;

import java.util.HashMap;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;

import dts.com.adminportal.model.PartnerChangePassword;
import dts.com.adminportal.model.PartnerHomePage;
import dts.com.adminportal.model.PartnerloginAccount;
import dts.com.adminportal.model.Xpath;

@Test
public class PartnerUser096PChangePassword extends CreatePage{
	private HomeController home;
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
		
	}
	@BeforeMethod
	public void loginBeforeTest() {
		result = home.login(partneruser, password);
	}
	
	/*
	 *Verify that the 096P Change Password is displayed when clicking on "Change Password" link in 095P User Info Page.
	 */
	@Test
	public void TC096PCP_01(){
		result.addLog("ID : TC096PCP_01 :Verify that the 096P Change Password is displayed when clicking on 'Change Password' link in 095P User Info Page.");
		
		/*
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click user's email label on top right corner
		VP: Verify that the user account popup is displayed
		4. Click "User Account" item
		VP: verify that 095P User Info page is displayed
		5. Click "Change Password" link in "Actions" module
		*/
		// 3.Click user's email label on top right corner
		home.click(Xpath.lbLogout);
		/*
		 * VP: Verify that the user account popup is displayed
		 */
		HashMap<String, String> menu = home
				.getItemUserMenu(PartnerHomePage.DROP_DOWN_MENU);
		Assert.assertEquals(menu.get("option 1"), PartnerHomePage
				.GET_USER_MENU().get("option 1"));
		Assert.assertEquals(menu.get("option 2"), PartnerHomePage
				.GET_USER_MENU().get("option 2"));
		// 4. Click "User Account" item
		home.click(Xpath.loginAccount);
		/*
		 * VP: verify that 095P User Info page is displayed
		 */
		Assert.assertEquals("Pass",
				home.existsElement(PartnerloginAccount.getHash()).getResult());
		// 5. Click "Change Password" link in "Actions" module
		home.click(PartnerloginAccount.CHANGE_PASSWORD);
		/*
		 * VP: Verify that the 096P Change Password page is displayed
		 */
		Assert.assertEquals("Pass", home.existsElement(PartnerChangePassword.getHash()).getResult());
		Assert.assertEquals("Pass", home.existsElement(PartnerChangePassword.getActions()).getResult());

	}
	
	/*
	 *Verify that the 096P Change Password page shows up with "Old Password", "New Password" and "Re-type New Password" fields.
	 */
	@Test
	public void TC096PCP_02(){
		result.addLog("ID : TC096PCP_02 :Verify that the 096P Change Password page shows up with 'Old Password', 'New Password' and 'Re-type New Password' fields.");
		
		/*
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click user's email label on top right corner
		VP: Verify that the user account popup is displayed
		4. Click "User Account" item
		VP: verify that 095P User Info page is displayed
		5. Click "Change Password" link in "Actions" module
		 */
		// 3.Click user's email label on top right corner
		home.click(Xpath.lbLogout);
		/*
		 * VP: Verify that the user account popup is displayed
		 */
		HashMap<String, String> menu = home
				.getItemUserMenu(PartnerHomePage.DROP_DOWN_MENU);
		Assert.assertEquals(menu.get("option 1"), PartnerHomePage
				.GET_USER_MENU().get("option 1"));
		Assert.assertEquals(menu.get("option 2"), PartnerHomePage
				.GET_USER_MENU().get("option 2"));
		// 4. Click "User Account" item
		home.click(Xpath.loginAccount);
		/*
		 * VP: verify that 095P User Info page is displayed
		 */
		Assert.assertEquals("Pass",
				home.existsElement(PartnerloginAccount.getHash()).getResult());
		// 5. Click "Change Password" link in "Actions" module
		home.click(PartnerloginAccount.CHANGE_PASSWORD);
		/*
		 * The 096P Change Password page is showed up with "Old Password", "New Password" and "Re-type New Password" fields
		 */
		Assert.assertEquals("Pass",home.existsElement(PartnerChangePassword.getHash()).getResult());
	}

	/*
	 *Verify that the 096P Change Password page shows up with "Old Password", "New Password" and "Re-type New Password" fields.
	 */
	@Test
	public void TC096PCP_03(){
		result.addLog("ID : TC096PCP_03 :Verify that the 096P Change Password page shows up with 'Old Password', 'New Password' and 'Re-type New Password' fields.");
		
		/*
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click user's email label on top right corner
		VP: Verify that the user account popup is displayed
		4. Click "User Account" item
		VP: verify that 095P User Info page is displayed
		5. Click "Change Password" link in "Actions" module
		 */
		// 3.Click user's email label on top right corner
		home.click(Xpath.lbLogout);
		/*
		 * VP: Verify that the user account popup is displayed
		 */
		HashMap<String, String> menu = home
				.getItemUserMenu(PartnerHomePage.DROP_DOWN_MENU);
		Assert.assertEquals(menu.get("option 1"), PartnerHomePage
				.GET_USER_MENU().get("option 1"));
		Assert.assertEquals(menu.get("option 2"), PartnerHomePage
				.GET_USER_MENU().get("option 2"));
		// 4. Click "User Account" item
		home.click(Xpath.loginAccount);
		/*
		 * VP: verify that 095P User Info page is displayed
		 */
		Assert.assertEquals("Pass",
				home.existsElement(PartnerloginAccount.getHash()).getResult());
		// 5. Click "Change Password" link in "Actions" module
		home.click(PartnerloginAccount.CHANGE_PASSWORD);
		/*
		 * The "Actions" module shows up "Save" and "Cancel" links
		 */
		Assert.assertEquals("Pass",home.existsElement(PartnerChangePassword.getActions()).getResult());
	}	
	
	/*
	 *Verify that an error message is showed up inline for each empty field in 096P Change Password page when saving new password.
	 */
	@Test
	public void TC096PCP_04(){
		String rq0= "Old password is required.";
		String rq1= "Password is required.";
		String rq2= "Re-type New Password does not match New Password.";
		result.addLog("ID : TC096PCP_04 :Verify that an error message is showed up inline for each empty field in 096P Change Password page when saving new password.");
		
		/*
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click user's email label on top right corner
		VP: Verify that the user account popup is displayed
		4. Click "User Account" item
		VP: verify that 095P User Info page is displayed
		5. Click "Change Password" link in "Actions" module
		6. Click "Save" link.
		 */
		// 3.Click user's email label on top right corner
		home.click(Xpath.lbLogout);
		/*
		 * VP: Verify that the user account popup is displayed
		 */
		HashMap<String, String> menu = home
				.getItemUserMenu(PartnerHomePage.DROP_DOWN_MENU);
		Assert.assertEquals(menu.get("option 1"), PartnerHomePage
				.GET_USER_MENU().get("option 1"));
		Assert.assertEquals(menu.get("option 2"), PartnerHomePage
				.GET_USER_MENU().get("option 2"));
		// 4. Click "User Account" item
		home.click(Xpath.loginAccount);
		/*
		 * VP: verify that 095P User Info page is displayed
		 */
		Assert.assertEquals("Pass",
				home.existsElement(PartnerloginAccount.getHash()).getResult());
		// 5. Click "Change Password" link in "Actions" module
		home.click(PartnerloginAccount.CHANGE_PASSWORD);
		//6. Click "Save" link.
		home.click(PartnerChangePassword.SAVE);
		/*
		 * An error message is showed inline for each "Old password", "New password" and "Re-type New Password" fields
		 */
		Assert.assertEquals( home.getTextByXpath(PartnerChangePassword.requires[0]), rq0);
		Assert.assertEquals( home.getTextByXpath(PartnerChangePassword.requires[1]), rq1);
		Assert.assertEquals( home.getTextByXpath(PartnerChangePassword.requires[2]), rq2);		
	}
	
	
	
	/*
	 * Verify that user is unable to save new password if the value of "Old Password" is incorrect
	 */
	@Test
	public void TC096PCP_05(){
		result.addLog("ID : TC096PCP_05 : Verify that user is unable to save new password if the value of 'Old Password' is incorrect");
		
		/*
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click user's email label on top right corner
		VP: Verify that the user account popup is displayed
		4. Click "User Account" item
		VP: verify that 095P User Info page is displayed
		5. Click "Change Password" link in "Actions" module
		VP: Verify that the 096P Change Password page is displayed
		6. Type invalid value into "Old Password" field
		7. Type same value into "New Password" and "Re-type New Password" fields
		8. Click "Save" link
		 */
		// 3.Click user's email label on top right corner
		String error1="The Old Password is not matched!";
		home.click(Xpath.lbLogout);
		/*
		 * VP: Verify that the user account popup is displayed
		 */
		HashMap<String, String> menu = home.getItemUserMenu(PartnerHomePage.DROP_DOWN_MENU);
		Assert.assertEquals(menu.get("option 1"), PartnerHomePage.GET_USER_MENU().get("option 1"));
		Assert.assertEquals(menu.get("option 2"), PartnerHomePage.GET_USER_MENU().get("option 2"));
		//4. Click "User Account" item
		home.click(Xpath.loginAccount);
		/*
		 * VP: verify that 095P User Info page is displayed
		 */
		Assert.assertEquals("Pass", home.existsElement(PartnerloginAccount.getHash()).getResult());
		//5. Click "Change Password" link in "Actions" module
		home.click(PartnerloginAccount.CHANGE_PASSWORD);
		/*
		 * VP: Verify that the 096P Change Password page is displayed
		 */
		Assert.assertEquals("Pass", home.existsElement(PartnerChangePassword.getHash()).getResult());
		// 6. Type invalid value into "Old Password" field
		home.type(PartnerChangePassword.OLD_PASSWORD, RandomStringUtils.randomNumeric(5));
		// 7. Type same value into "New Password" and "Re-type New Password" fields
		home.type(PartnerChangePassword.NEW_PASSWORD, password);
		home.type(PartnerChangePassword.RETYPE_PASSWORD, password);
		// 8. Click "Save" link
		home.click(PartnerChangePassword.SAVE);
		/*
		 * Verify that An error message is displayed which mentioning to incorrect old password
		 */
		Assert.assertEquals( home.getTextByXpath(PartnerChangePassword.requires[0]), error1);
	}	
	/*
	 *Verify that user is unable to save new password if the value of "New Password" and "Re-type New Password" fields are not matched.
	 */
	@Test
	public void TC096PCP_06(){
		String old_pass = "dts999";
		String new_pass = "dts111";
		String retype_pass =" dts000";
		String rq2= "Re-type New Password does not match New Password.";
		result.addLog("ID : TC096PCP_06 :Verify that user is unable to save new password if the value of 'New Password' and 'Re-type New Password' fields are not matched.");
		
		/*
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click user's email label on top right corner
		VP: Verify that the user account popup is displayed
		4. Click "User Account" item
		VP: verify that 095P User Info page is displayed
		5. Click "Change Password" link in "Actions" module
		VP: Verify that the 096P Change Password page is displayed
		6. Type valid value into "Old Password" and "New Password" fields
		7. Type valid value into "Re-type New Password" which is difference with value of "New Password"
		8. Click "Save" link
		 */
		// 3.Click user's email label on top right corner
		home.click(Xpath.lbLogout);
		/*
		 * VP: Verify that the user account popup is displayed
		 */
		HashMap<String, String> menu = home
				.getItemUserMenu(PartnerHomePage.DROP_DOWN_MENU);
		Assert.assertEquals(menu.get("option 1"), PartnerHomePage
				.GET_USER_MENU().get("option 1"));
		Assert.assertEquals(menu.get("option 2"), PartnerHomePage
				.GET_USER_MENU().get("option 2"));
		// 4. Click "User Account" item
		home.click(Xpath.loginAccount);
		/*
		 * VP: verify that 095P User Info page is displayed
		 */
		Assert.assertEquals("Pass",
				home.existsElement(PartnerloginAccount.getHash()).getResult());
		// 5. Click "Change Password" link in "Actions" module
		home.click(PartnerloginAccount.CHANGE_PASSWORD);
		/*
		 * VP: Verify that the 096P Change Password page is displayed
		 */
		Assert.assertEquals("Pass", home.existsElement(PartnerChangePassword.getHash()).getResult());
		//6. Type valid value into "Old Password" and "New Password" fields
		home.type(PartnerChangePassword.OLD_PASSWORD, old_pass);
		home.type(PartnerChangePassword.NEW_PASSWORD, new_pass);
		//7. Type valid value into "Re-type New Password" which is difference with value of "New Password"
		home.type(PartnerChangePassword.RETYPE_PASSWORD,retype_pass );
		//8. Click "Save" link
		home.click(PartnerChangePassword.SAVE);
		/*
		 * An error message is displayed and new password can not be saved due to the mis-matching value of "New Password" and "Re-tye New Password" fields
		 */
		Assert.assertEquals( home.getTextByXpath(PartnerChangePassword.requires[2]), rq2);
	}	
	
	/*
	 *Verify that the portal is redirected to 095P User Info page when clicking on "Cancel" link in 096 Change Password page.
	 */
	@Test
	public void TC096PCP_07(){
		result.addLog("ID : TC096PCP_07 :Verify that the portal is redirected to 095P User Info page when clicking on 'Cancel' link in 096 Change Password page.");
		
		/*
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click user's email label on top right corner
		VP: Verify that the user account popup is displayed
		4. Click "User Account" item
		VP: verify that 095P User Info page is displayed
		5. Click "Change Password" link in "Actions" module
		VP: Verify that the 096P Change Password page is displayed
		6. Click "Cancel" link
		 */
		// 3.Click user's email label on top right corner
		home.click(Xpath.lbLogout);
		/*
		 * VP: Verify that the user account popup is displayed
		 */
		HashMap<String, String> menu = home
				.getItemUserMenu(PartnerHomePage.DROP_DOWN_MENU);
		Assert.assertEquals(menu.get("option 1"), PartnerHomePage
				.GET_USER_MENU().get("option 1"));
		Assert.assertEquals(menu.get("option 2"), PartnerHomePage
				.GET_USER_MENU().get("option 2"));
		// 4. Click "User Account" item
		home.click(Xpath.loginAccount);
		/*
		 * VP: verify that 095P User Info page is displayed
		 */
		Assert.assertEquals("Pass",
				home.existsElement(PartnerloginAccount.getHash()).getResult());
		// 5. Click "Change Password" link in "Actions" module
		home.click(PartnerloginAccount.CHANGE_PASSWORD);
		/*
		 * VP: Verify that the 096P Change Password page is displayed
		 */
		Assert.assertEquals("Pass", home.existsElement(PartnerChangePassword.getHash()).getResult());
		//6. Click "Cancel" link
		home.click(PartnerChangePassword.CANCEL);
		/*
		 * Verify that The portal is redirected to 095P User Info page
		 */
		Assert.assertEquals("Pass", home.existsElement(PartnerloginAccount.getHash()).getResult());
	}		
}
