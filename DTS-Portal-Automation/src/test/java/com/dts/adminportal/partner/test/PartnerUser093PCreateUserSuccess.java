package com.dts.adminportal.partner.test;

import java.util.Hashtable;

import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.MailUtil;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.DTSHome;
import dts.com.adminportal.model.LoginPage;
import dts.com.adminportal.model.PartnerHomePage;
import dts.com.adminportal.model.PartnerListUser;
import dts.com.adminportal.model.PartnerUserMgmt;
import dts.com.adminportal.model.UserInfo;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.UsersList;
import dts.com.adminportal.model.Xpath;

public class PartnerUser093PCreateUserSuccess extends CreatePage{
	private HomeController home;
	String invite_message_title = "Your Invitation to DTS Headphone:X Partner Portal";

	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}
	
	@BeforeMethod
	public void loginBeforeTest(){
		home.login(superpartneruser, superpartnerpassword);
	}

	/*
	 * Verify that the "Success" page is displayed when a new user is created successfully
	 */
	@Test
	public void TC093PCUS_01() {
		result.addLog("ID : TC093PCUS_01 : Verify that the 'Success' page is displayed when a new user is created successfully");
		/*
			Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign at least one site privilege
			7. Click "Save" link
		*/
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Click "Add New User" link
		home.click(PartnerListUser.ADD_USER);
		// 5. Fill valid values into required fields
		// 6. Assign at least one site privilege
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.partnerUser();
		home.addUser(AddUser.getPartnerUser(), data);
		/*
		 * Verify that The "Success" page is displayed
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// Teardown
		// Delete user
		home.click(Xpath.LINK_PARTNER_USER);
		home.deleteUserByEmail(data.get("email"));

	}
	
	/*
	 * Verify that invitation message is sent to the invited user
	 */
	@Test
	public void TC093PCUS_02() {
		result.addLog("ID : TC093PCUS_02 : Verify that invitation message is sent to the invited user");
		/*
			Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign at least one site privilege
			7. Click "Save" link
			VP: The "Success" page is displayed.
			8. Log out DTS portal
			9. Open invited user's mailbox which is used to register user
		*/
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server, pdts_email, email_password);
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// Delete user if exist
		home.deleteUserByEmail(pdts_email);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill valid values into required fields
		// 6. Assign at least one site privilege
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.partnerUser();
		data.put("email", pdts_email);
		home.addUser(AddUser.getPartnerUser(), data);
		/*
		 * VP: The "Success" page is displayed
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// 8. Log out DTS portal
		// 9. Open invited user's mailbox which is used to register user
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email, email_password, messageCount);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, pdts_email, email_password);
		/*
		 * Verify that The invitation message is sent to user
		 */
		Assert.assertEquals(emailTitle, invite_message_title);
		// Delete user
		home.click(Xpath.LINK_PARTNER_USER);
		home.deleteUserByEmail(data.get("email"));
	}
	
	/*
	 * Verify that browser redirects to Partner activation page when clicking on invitation link
	 */
	@Test
	public void TC093PCUS_03() {
		result.addLog("ID : TC093PCUS_03 : Verify that browser redirects to Partner activation page when clicking on invitation link");
		/*
			Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign at least one site privilege
			7. Click "Save" link
			VP: The "Success" page is displayed.
			8. Log out DTS portal
			9. Open invited user's mailbox which is used to register user
			VP. Verify that the invitation email is sent to user
			10. Open the invitation email
			VP: The invitation email contains activation link
			11. Click on invitation link
		*/
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server, pdts_email, email_password);
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// Delete user if exist
		home.deleteUserByEmail(pdts_email);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill valid values into required fields
		// 6. Assign at least one site privilege
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.partnerUser();
		data.put("email", pdts_email);
		home.addUser(AddUser.getPartnerUser(), data);
		/*
		 * VP: The "Success" page is displayed
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// 8. Log out DTS portal
		// 9. Open invited user's mailbox which is used to register user
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email, email_password, messageCount);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, pdts_email, email_password);
		/*
		 * Verify that The invitation message is sent to user
		 */
		Assert.assertEquals(emailTitle, invite_message_title);
		// 10. Open the invitation email
		String activation_link = MailUtil.getLinkActive(yahoo_imap_server, pdts_email, email_password);
		/*
		 * VP: The invitation email contains activation link
		 */
		Assert.assertNotNull(activation_link);
		// 11. Click on invitation link
		driver.get(activation_link);
		/*
		 * Verify that The Partner activation page is launched with “Email”, “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		/*
		 * Delete user
		 */
		// Navigate to home page
		driver.get(siteBase.toString());
		home.waitForAjax();
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		home.deleteUserByEmail(data.get("email"));
	}
	
	/*
	 * Verify that Partner User can be activated successfully
	 */
	@Test
	public void TC093PCUS_04() {
		result.addLog("ID : TC093PCUS_04 : Verify that Partner User can be activated successfully");
		/*
			Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign at least one site privilege
			7. Click "Save" link
			VP: The "Success" page is displayed.
			8. Log out DTS portal
			9. Open invited user's mailbox which is used to register user
			VP. Verify that the invitation email is sent to user
			10. Open the invitation email
			VP: The invitation email contains activation link
			11. Click on invitation link
			VP: The Partner activation page is launched with “Email”, “Password”, and “Confirm Password” fields
			12. Fill valid value into all fields
			13. Click on Sign In link
		*/
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server,
				pdts_email, email_password);
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// Delete user if exist
		home.deleteUserByEmail(pdts_email);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill valid values into required fields
		// 6. Assign at least one site privilege
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.partnerUser();
		data.put("email", pdts_email);
		home.addUser(AddUser.getPartnerUser(), data);
		/*
		 * VP: The "Success" page is displayed
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// 8. Log out DTS portal
		// 9. Open invited user's mailbox which is used to register user
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email, email_password, messageCount);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, pdts_email, email_password);
		/*
		 * Verify that The invitation message is sent to user
		 */
		Assert.assertEquals(emailTitle, invite_message_title);
		// 10. Open the invitation email
		String activation_link = MailUtil.getLinkActive(yahoo_imap_server, pdts_email, email_password);
		/*
		 * VP: The invitation email contains activation link
		 */
		Assert.assertNotNull(activation_link);
		// 11. Click on invitation link
		driver.get(activation_link);
		/*
		 * Verify that The Partner activation page is launched with “Email”,
		 * “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		// 12. Fill valid value into all fields
		// 13. Click on Sign In link
		home.activeUser(pdts_email, new_active_user_password);
		/*
		 * Verify that The portal is redirected to Home Page and the activation user is already signed in
		 */
		Assert.assertEquals(home.existsElement(PartnerHomePage.getElementInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(PartnerHomePage.EMAIL), pdts_email);
		/*
		 *  Delete user
		 */
		home.logout();
		// Log in DTS portal as super partner user
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		home.deleteUserByEmail(data.get("email"));
	}
	
	/*
	 * Verify that Partner User cannot be activated when the value of Password and Confirm Password fields are not matched
	 */
	@Test
	public void TC093PCUS_05() {
		result.addLog("ID : TC093PCUS_05 : Verify that Partner User cannot be activated when the value of Password and Confirm Password fields are not matched");
		/*
			Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign at least one site privilege
			7. Click "Save" link
			VP: The "Success" page is displayed.
			8. Log out DTS portal
			9. Open invited user's mailbox which is used to register user
			VP. Verify that the invitation email is sent to user
			10. Open the invitation email
			VP: The invitation email contains activation link
			11. Click on invitation link
			VP: The Partner activation page is launched with “Email”, “Password”, and “Confirm Password” fields
			12. Fill valid value into all fields but the value of Password is not matched with Confirm Password
			13. Click on Sign In link
		*/
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server, pdts_email, email_password);
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// Delete user if exist
		home.deleteUserByEmail(pdts_email);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill valid values into required fields
		// 6. Assign at least one site privilege
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.partnerUser();
		data.put("email", pdts_email);
		home.addUser(AddUser.getPartnerUser(), data);
		/*
		 * VP: The "Success" page is displayed
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// 8. Log out DTS portal
		// 9. Open invited user's mailbox which is used to register user
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email, email_password, messageCount);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, pdts_email, email_password);
		/*
		 * Verify that The invitation message is sent to user
		 */
		Assert.assertEquals(emailTitle, invite_message_title);
		// 10. Open the invitation email
		String activation_link = MailUtil.getLinkActive(yahoo_imap_server, pdts_email, email_password);
		/*
		 * VP: The invitation email contains activation link
		 */
		Assert.assertNotNull(activation_link);
		// 11. Click on invitation link
		driver.get(activation_link);
		/*
		 * Verify that The Partner activation page is launched with “Email”,
		 * “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		// 12. Fill valid value into all fields but the value of Password is not matched with Confirm Password
		home.editData(LoginPage.USERNAME, data.get("email"));
		home.editData(LoginPage.PASSWORD, data.get(new_active_user_password));
		home.editData(LoginPage.CONFIRM_PASSWORD, email_password);
		// 13. Click on Sign In link	
		home.click(LoginPage.SIGN_IN);
		/*
		 * Verify that An error message “Password does not matched” displays and
		 * DTS User is not activated successfully
		 */
		Assert.assertTrue(home.checkMessageDisplay(LoginPage.errMessage[2]));
		/*
		 *  Delete user
		 */
		// Navigate to home page
		driver.get(siteBase.toString());
		home.waitForAjax();
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		home.deleteUserByEmail(data.get("email"));
	}
	
	/*
	 * Verify that DTS User cannot be activated when the registered email is not correct
	 */
	@Test
	public void TC093PCUS_06() {
		result.addLog("ID : TC093PCUS_06 : Verify that DTS User cannot be activated when the registered email is not correct");
		/*
			Pre-condition: DTS user has "Add and Manage DTS Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign at least one site privilege
			7. Click "Save" link
			VP: The "Success" page is displayed.
			8. Log out DTS portal
			9. Open invited user's mailbox which is used to register user
			VP. Verify that the invitation email is sent to user
			10. Open the invitation email
			VP: The invitation email contains activation link
			11. Click on invitation link
			VP: The Partner activation page is launched with “Email”, “Password”, and “Confirm Password” fields
			12. Fill valid value into all fields but the registered email is not correct
			13. Click on Sign In link
		*/
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server, pdts_email, email_password);
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// Delete user if exist
		home.deleteUserByEmail(pdts_email);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill valid values into required fields
		// 6. Assign at least one site privilege
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.partnerUser();
		data.put("email", pdts_email);
		home.addUser(AddUser.getPartnerUser(), data);
		/*
		 * VP: The "Success" page is displayed
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// 8. Log out DTS portal
		// 9. Open invited user's mailbox which is used to register user
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email, email_password, messageCount);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, pdts_email, email_password);
		/*
		 * Verify that The invitation message is sent to user
		 */
		Assert.assertEquals(emailTitle, invite_message_title);
		// 10. Open the invitation email
		String activation_link = MailUtil.getLinkActive(yahoo_imap_server, pdts_email, email_password);
		/*
		 * VP: The invitation email contains activation link
		 */
		Assert.assertNotNull(activation_link);
		// 11. Click on invitation link
		driver.get(activation_link);
		/*
		 * Verify that The Partner activation page is launched with “Email”,
		 * “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		// 12. Fill valid value into all fields but the registered email is not correct
		// 13. Click on Sign In link		
		home.activeUser(partneruser, new_active_user_password);
		/*
		 * Verify that An error message “Cannot active account, the link may be not valid or expried” displays and Partner User is not activated successfully
		 */
		Assert.assertTrue(home.checkMessageDisplay(LoginPage.errMessage[0]));
		/*
		 * Delete user
		 */
		// Navigate to home page
		driver.get(siteBase.toString());
		home.waitForAjax();
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		home.deleteUserByEmail(data.get("email"));
	}
	
	/*
	 * Verify that the activation link is expired after the registered user is activated
	 */
	@Test
	public void TC093PCUS_07() {
		result.addLog("ID : TC093PCUS_07 : Verify that the activation link is expired after the registered user is activated");
		/*
			Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign at least one site privilege
			7. Click "Save" link
			VP: The "Success" page is displayed.
			8. Log out DTS portal
			9. Open invited user's mailbox which is used to register user
			VP. Verify that the invitation email is sent to user
			10. Open the invitation email
			VP: The invitation email contains activation link
			11. Click on invitation link
			VP: The Partner activation page is launched with “Email”, “Password”, and “Confirm Password” fields
			12. Fill valid value into all fields
			13. Click on Sign In link
			VP: The portal is redirected to Home Page and the activation user is already signed in
			14. Repeat from step 8 to 13
		*/
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server, pdts_email, email_password);
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// Delete user if exist
		home.deleteUserByEmail(pdts_email);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill valid values into required fields
		// 6. Assign at least one site privilege
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.partnerUser();
		data.put("email", pdts_email);
		home.addUser(AddUser.getPartnerUser(), data);
		/*
		 * VP: The "Success" page is displayed
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// 8. Log out DTS portal
		// 9. Open invited user's mailbox which is used to register user
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email, email_password, messageCount);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, pdts_email, email_password);
		/*
		 * Verify that The invitation message is sent to user
		 */
		Assert.assertEquals(emailTitle, invite_message_title);
		// 10. Open the invitation email
		String activation_link = MailUtil.getLinkActive(yahoo_imap_server, pdts_email, email_password);
		/*
		 * VP: The invitation email contains activation link
		 */
		Assert.assertNotNull(activation_link);
		// 11. Click on invitation link
		driver.get(activation_link);
		/*
		 * Verify that The Partner activation page is launched with “Email”,
		 * “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(
				home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		// 12. Fill valid value into all fields
		// 13. Click on Sign In link
		home.activeUser(pdts_email, new_active_user_password);
		/*
		 * Verify that The portal is redirected to Home Page and the activation
		 * user is already signed in
		 */
		Assert.assertEquals(home.existsElement(PartnerHomePage.getElementInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(PartnerHomePage.EMAIL), pdts_email);
		// 14. Repeat from step 8 to 13
		driver.get(activation_link);
		// Fill valid value into all fields
		// Click on Sign In link
		home.activeUser(pdts_email, new_active_user_password);
		/*
		 * Verify that An error message “Cannot active account, the link may be not valid or expried” displays
		 */
		Assert.assertTrue(home.checkMessageDisplay(LoginPage.errMessage[0]));
		/*
		 * Delete user
		 */
		// Navigate to home page
		driver.get(siteBase.toString());
		home.waitForAjax();
		// Log out
		home.logout();
		// Log into DTS portal as super partner user
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		home.deleteUserByEmail(data.get("email"));
	}
	
	/*
	 * Verify that user status changes to “Active” after the activation is successfully
	 */
	@Test
	public void TC093PCUS_08() {
		result.addLog("ID : TC093PCUS_08 : Verify that user status changes to “Active” after the activation is successfully");
		/*
			Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign at least one site privilege
			7. Click "Save" link
			VP: The "Success" page is displayed.
			8. Log out DTS portal
			9. Open invited user's mailbox which is used to register user
			VP. Verify that the invitation email is sent to user
			10. Open the invitation email
			VP: The invitation email contains activation link
			11. Click on invitation link
			VP: The Partner activation page is launched with “Email”, “Password”, and “Confirm Password” fields
			12. Fill valid value into all fields
			13. Click on Sign In link
			VP: The portal is redirected to Home Page and the activation user is already signed in
			14. Click on partner user's email on top right corner
			15. Select “User Account” item
		*/
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server,	pdts_email, email_password);
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// Delete user if exist
		home.deleteUserByEmail(pdts_email);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill valid values into required fields
		// 6. Assign at least one site privilege
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.partnerUser();
		data.put("email", pdts_email);
		home.addUser(AddUser.getPartnerUser(), data);
		/*
		 * VP: The "Success" page is displayed
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// 8. Log out DTS portal
		// 9. Open invited user's mailbox which is used to register user
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email,
				email_password, messageCount);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server,
				pdts_email, email_password);
		/*
		 * Verify that The invitation message is sent to user
		 */
		Assert.assertEquals(emailTitle, invite_message_title);
		// 10. Open the invitation email
		String activation_link = MailUtil.getLinkActive(yahoo_imap_server, pdts_email, email_password);
		/*
		 * VP: The invitation email contains activation link
		 */
		Assert.assertNotNull(activation_link);
		// 11. Click on invitation link
		driver.get(activation_link);
		/*
		 * Verify that The Partner activation page is launched with “Email”,
		 * “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		// 12. Fill valid value into all fields
		// 13. Click on Sign In link
		home.activeUser(pdts_email, new_active_user_password);
		/*
		 * Verify that The portal is redirected to Home Page and the activation
		 * user is already signed in
		 */
		Assert.assertEquals(home.existsElement(PartnerHomePage.getElementInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(PartnerHomePage.EMAIL), pdts_email);
		// 14. Click on partner user's email on top right corner
		home.click(DTSHome.SIGNIN_EMAIL);
		// 15. Select “User Account” item
		home.click(DTSHome.USER_ACCOUNT);
		/*
		 * Verify that The portal is redirected to User Info page and the user status is changed to “Active”
		 */
		Assert.assertEquals(home.existsElement(PartnerUserMgmt.getElementsInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(UserMgmt.STATUS), "Active");
		/*
		 * Delete user
		 */
		home.logout();
		// Log into DTS portal as super partner user
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		home.deleteUserByEmail(data.get("email"));
	}
	
	/*
	 * Verify that a reset password email is sent when user clicks on reset password link
	 */
	@Test
	public void TC093PCUS_09() {
		result.addLog("ID : TC093PCUS_09 : Verify that a reset password email is sent when user clicks on reset password link");
		/*
			Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Select a Partner User on User table
			VP: The Reset Password link is displayed on User Info page
			5. Click on Reset Password link
			6. Log out DTS portal
			7. Open user mailbox that use to reset password
		*/
		/*
		 * Pre-condition: Create new partner user
		 */
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server,pdts_email, email_password);
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		// Delete user if exist
		home.deleteUserByEmail(pdts_email);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String, String> data = TestData.partnerUser();
		data.put("email", pdts_email);
		home.addUser(AddUser.getPartnerUser(), data);
		/*
		 * ************************************************
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Select a Partner User on User table
		home.selectUserByEmail(data.get("email"));
		/*
		 * VP: The Reset Password link is displayed on User Info page
		 */
		Assert.assertTrue(home.isElementPresent(PartnerUserMgmt.RESET_PASSWORD));
		// 5. Click on Reset Password link
		home.click(PartnerUserMgmt.RESET_PASSWORD);
		home.switchWindowClickOption("Reset");
		// 6. Log out DTS portal
		// 7. Open user mailbox that use to reset password
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email, email_password, messageCount + 1);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, pdts_email, email_password);
		/*
		 * Verify that A reset password email is sent to user
		 */
		Assert.assertTrue(emailTitle.contains("Reset password"));
		// Delete user
		home.click(Xpath.LINK_PARTNER_USER);
		home.deleteUserByEmail(data.get("email"));
	}
	
	/*
	 * Verify that browser redirects to DTS activation page when clicking on reset link in reset password email
	 */
	@Test
	public void TC093PCUS_10() {
		result.addLog("ID : TC093PCUS_10 : Verify that browser redirects to DTS activation page when clicking on reset link in reset password email");
		/*
			Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Select a Partner User on User table
			VP: The Reset Password link is displayed on User Info page
			5. Click on Reset Password link
			6. Log out DTS portal
			7. Open user mailbox that has use to reset password
			VP: A reset password email is sent to user.
			8. Open reset password email
			VP: The reset password email contains reset password link
			9. Click on reset password link
		*/
		/*
		 * Pre-condition: Create new partner user
		 */
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server,pdts_email, email_password);
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		// Delete user if exist
		home.deleteUserByEmail(pdts_email);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String, String> data = TestData.partnerUser();
		data.put("email", pdts_email);
		home.addUser(AddUser.getPartnerUser(), data);
		/*
		 * ************************************************
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Select a Partner User on User table
		home.selectUserByEmail(data.get("email"));
		/*
		 * VP: The Reset Password link is displayed on User Info page
		 */
		Assert.assertTrue(home.isElementPresent(PartnerUserMgmt.RESET_PASSWORD));
		// 5. Click on Reset Password link
		home.click(PartnerUserMgmt.RESET_PASSWORD);
		home.switchWindowClickOption("Reset");
		// 6. Log out DTS portal
		// 7. Open user mailbox that use to reset password
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email, email_password, messageCount + 1);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, pdts_email, email_password);
		/*
		 * Verify that A reset password email is sent to user
		 */
		Assert.assertTrue(emailTitle.contains("Reset password"));
		// 8. Open reset password email
		String reset_link = MailUtil.getLinkActive(yahoo_imap_server, pdts_email, email_password);
		/*
		 * VP: The reset password email contains reset password link
		 */
		Assert.assertNotNull(reset_link);
		// 9. Click on reset password link
		driver.get(reset_link);
		/*
		 * Verify that The Partner activation page is launched with “Email”,
		 * “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		/*
		 *  Delete user
		 */
		// Navigate to home page
		driver.get(siteBase.toString());
		home.waitForAjax();
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		home.deleteUserByEmail(data.get("email"));
	}
	
	/*
	 * Verify that Partner User can reset password successfully
	 */
	@Test
	public void TC093PCUS_11() {
		result.addLog("ID : TC093PCUS_11 : Verify that Partner User can reset password successfully");
		/*
			Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Select a Partner User on User table
			VP: The Reset Password link is displayed on User Info page
			5. Click on Reset Password link
			6. Log out DTS portal
			7. Open user mailbox that has use to reset password
			VP: A reset password email is sent to user.
			8. Open reset password email
			VP: The reset password email contains reset password link
			9. Click on reset password link
			VP: The Partner activation page is launched with “Email”, “Password”, and “Confirm Password” fields.
			10. Fill valid value into all fields
			11. Click on Sign In link
		*/
		/*
		 * Pre-condition: Create new partner user
		 */
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server,pdts_email, email_password);
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		// Delete user if exist
		home.deleteUserByEmail(pdts_email);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String, String> data = TestData.partnerUser();
		data.put("email", pdts_email);
		home.addUser(AddUser.getPartnerUser(), data);
		/*
		 * ************************************************
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Select a Partner User on User table
		home.selectUserByEmail(data.get("email"));
		/*
		 * VP: The Reset Password link is displayed on User Info page
		 */
		Assert.assertTrue(home.isElementPresent(PartnerUserMgmt.RESET_PASSWORD));
		// 5. Click on Reset Password link
		home.click(PartnerUserMgmt.RESET_PASSWORD);
		home.switchWindowClickOption("Reset");
		// 6. Log out DTS portal
		// 7. Open user mailbox that use to reset password
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email, email_password, messageCount + 1);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, pdts_email, email_password);
		/*
		 * Verify that A reset password email is sent to user
		 */
		Assert.assertTrue(emailTitle.contains("Reset password"));
		// 8. Open reset password email
		String reset_link = MailUtil.getLinkActive(yahoo_imap_server, pdts_email, email_password);
		/*
		 * VP: The reset password email contains reset password link
		 */
		Assert.assertNotNull(reset_link);
		// 9. Click on reset password link
		driver.get(reset_link);
		/*
		 * Verify that The Partner activation page is launched with “Email”,
		 * “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		// 10. Fill valid value into all fields
		// 11. Click on Sign In link
		home.activeUser(pdts_email, new_active_user_password);
		/*
		 * Verify that User password is reset successfully and the portal is redirected to Home Page
		 */
		Assert.assertEquals(home.existsElement(PartnerHomePage.getElementInfo()).getResult(), "Pass");
		/*
		 *  Delete user
		 */
		home.logout();
		// Log in DTS portal as super partner user
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		home.deleteUserByEmail(data.get("email"));
	}
	
	/*
	 * Verify that the reset password link is expired after user resets password successfully
	 */
	@Test
	public void TC093PCUS_12() {
		result.addLog("ID : TC093PCUS_12 : Verify that the reset password link is expired after user resets password successfully");
		/*
			Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Select a Partner User on User table
			VP: The Reset Password link is displayed on User Info page
			5. Click on Reset Password link
			6. Log out DTS portal
			7. Open user mailbox that has use to reset password
			VP: A reset password email is sent to user.
			8. Open reset password email
			VP: The reset password email contains reset password link
			9. Click on reset password link
			VP: The Partner activation page is launched with “Email”, “Password”, and “Confirm Password” fields.
			10. Fill valid value into all fields
			11. Click on Sign In link
			VP: User password is reset successfully and the portal is redirected to Home Page
			12. Repeat from step 6 to 11
		*/
		/*
		 * Pre-condition: Create new partner user
		 */
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server,pdts_email, email_password);
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		// Delete user if exist
		home.deleteUserByEmail(pdts_email);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String, String> data = TestData.partnerUser();
		data.put("email", pdts_email);
		home.addUser(AddUser.getPartnerUser(), data);
		/*
		 * ************************************************
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Select a Partner User on User table
		home.selectUserByEmail(data.get("email"));
		/*
		 * VP: The Reset Password link is displayed on User Info page
		 */
		Assert.assertTrue(home.isElementPresent(PartnerUserMgmt.RESET_PASSWORD));
		// 5. Click on Reset Password link
		home.click(PartnerUserMgmt.RESET_PASSWORD);
		home.switchWindowClickOption("Reset");
		// 6. Log out DTS portal
		// 7. Open user mailbox that use to reset password
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email, email_password, messageCount + 1);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, pdts_email, email_password);
		/*
		 * Verify that A reset password email is sent to user
		 */
		Assert.assertTrue(emailTitle.contains("Reset password"));
		// 8. Open reset password email
		String reset_link = MailUtil.getLinkActive(yahoo_imap_server, pdts_email, email_password);
		/*
		 * VP: The reset password email contains reset password link
		 */
		Assert.assertNotNull(reset_link);
		// 9. Click on reset password link
		driver.get(reset_link);
		/*
		 * Verify that The Partner activation page is launched with “Email”,
		 * “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		// 10. Fill valid value into all fields
		// 11. Click on Sign In link
		home.activeUser(pdts_email, new_active_user_password);
		/*
		 * Verify that User password is reset successfully and the portal is redirected to Home Page
		 */
		Assert.assertEquals(home.existsElement(PartnerHomePage.getElementInfo()).getResult(), "Pass");
		// 12. Repeat from step 6 to 11
		driver.get(reset_link);
		// Fill valid value into all fields
		// Click on Sign In link
		home.activeUser(pdts_email, new_active_user_password);
		/*
		 * Verify that An error message “Cannot reset password, the link may be not valid or expried” displays
		 */
		Assert.assertTrue(home.checkMessageDisplay(LoginPage.errMessage[1]));
		/*
		 * Delete user
		 */
		// Navigate to home page
		driver.get(siteBase.toString());
		home.waitForAjax();
		// Log out
		home.logout();
		// Log into DTS portal as super partner user
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		home.deleteUserByEmail(data.get("email"));
	}
	
	/*
	 * Verify that user cannot reset password when the value of Password and Confirm Password fields are not matched
	 */
	@Test
	public void TC093PCUS_13() {
		result.addLog("ID : TC093PCUS_13 : Verify that user cannot reset password when the value of Password and Confirm Password fields are not matched");
		/*
			Pre-condition: partner user has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Select a Partner User on User table
			VP: The Reset Password link is displayed on User Info page
			5. Click on Reset Password link
			6. Log out DTS portal
			7. Open user mailbox that has use to reset password
			VP: A reset password email is sent to user.
			8. Open reset password email
			VP: The reset password email contains reset password link
			9. Click on reset password link
			VP: The Partner activation page is launched with “Email”, “Password”, and “Confirm Password” fields.
			10. Fill valid value into all fields but the value of Password is not matched with Confirm Password
			11. Click on Sign In link
			VP: An error message “Password does not matched” displays and the password is not reset successfully
		*/
		/*
		 * Pre-condition: Create new partner user
		 */
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server,pdts_email, email_password);
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		// Delete user if exist
		home.deleteUserByEmail(pdts_email);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String, String> data = TestData.partnerUser();
		data.put("email", pdts_email);
		home.addUser(AddUser.getPartnerUser(), data);
		/*
		 * ************************************************
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Select a Partner User on User table
		home.selectUserByEmail(data.get("email"));
		/*
		 * VP: The Reset Password link is displayed on User Info page
		 */
		Assert.assertTrue(home.isElementPresent(PartnerUserMgmt.RESET_PASSWORD));
		// 5. Click on Reset Password link
		home.click(PartnerUserMgmt.RESET_PASSWORD);
		home.switchWindowClickOption("Reset");
		// 6. Log out DTS portal
		// 7. Open user mailbox that use to reset password
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email, email_password, messageCount + 1);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, pdts_email, email_password);
		/*
		 * Verify that A reset password email is sent to user
		 */
		Assert.assertTrue(emailTitle.contains("Reset password"));
		// 8. Open reset password email
		String reset_link = MailUtil.getLinkActive(yahoo_imap_server, pdts_email, email_password);
		/*
		 * VP: The reset password email contains reset password link
		 */
		Assert.assertNotNull(reset_link);
		// 9. Click on reset password link
		driver.get(reset_link);
		/*
		 * Verify that The Partner activation page is launched with “Email”,
		 * “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		// 10. Fill valid value into all fields but the value of Password is not matched with Confirm Password

		home.editData(LoginPage.USERNAME, pdts_email);	
		home.editData(LoginPage.PASSWORD, email_password);
		home.editData(LoginPage.CONFIRM_PASSWORD,new_active_user_password);
		// 11. Click on Sign In link
		home.click(LoginPage.SIGN_IN);
		/*
		 * An error message “Password does not matched” displays and the password is not reset successfully
		 */
		Assert.assertTrue(home.checkMessageDisplay(LoginPage.errMessage[2]));
		/*
		 * Teardown: Delete user
		 */
		
		// Navigate to home page
		driver.get(siteBase.toString());
		// Log into DTS portal as super partner user
		//home.login(superpartneruser, superpartnerpassword);
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		home.deleteUserByEmail(data.get("email"));
	}
	
	/*
	 * Verify that user cannot reset password when the registered email is not correct
	 */
	@Test
	public void TC093PCUS_14() throws InterruptedException {
		result.addLog("ID : TC093PCUS_14 : Verify that user cannot reset password when the registered email is not correct");
		/*
		Pre-condition: DTS user has "Add and Manage DTS Users" rights.

		1. Navigate to DTS portal
		2. Log into DTS portal as a Partner user successfully
		3. Click "Users" tab
		4. Select a Partner DTS User on User table
		VP: The Reset Password link is displayed on User Info page
		5. Click on Reset Password link
		6. Log out DTS portal
		7. Open user mailbox that has use to reset password
		VP: A reset password email is sent to user.
		8. Open reset password email
		VP: The reset password email contains reset password link
		9. Click on reset password link
		VP: The Partner activation page is launched with “Email”, “Password”, and “Confirm Password” fields.
		10. Fill valid value into all fields but the registered email is not correct
		11. Click on Sign In link
		VP: An error message “Cannot reset password, the link may be not valid or expried” displays and the password is not reset successfully
		*/
		/*
		 * Pre-condition: Create new partner user
		 */
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server,pdts_email, email_password);
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		// Delete user if exist
		home.deleteUserByEmail(pdts_email);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String, String> data = TestData.partnerUser();
		data.put("email", pdts_email);
		home.addUser(AddUser.getPartnerUser(), data);
		/*
		 * ************************************************
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Select a Partner User on User table
		home.selectUserByEmail(data.get("email"));
		/*
		 * VP: The Reset Password link is displayed on User Info page
		 */
		Assert.assertTrue(home.isElementPresent(PartnerUserMgmt.RESET_PASSWORD));
		// 5. Click on Reset Password link
		home.click(PartnerUserMgmt.RESET_PASSWORD);
		home.switchWindowClickOption("Reset");
		// 6. Log out DTS portal
		// 7. Open user mailbox that use to reset password
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email, email_password, messageCount + 1);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, pdts_email, email_password);
		/*
		 * Verify that A reset password email is sent to user
		 */
		Assert.assertTrue(emailTitle.contains("Reset password"));
		// 8. Open reset password email
		String reset_link = MailUtil.getLinkActive(yahoo_imap_server, pdts_email, email_password);
		/*
		 * VP: The reset password email contains reset password link
		 */
		Assert.assertNotNull(reset_link);
		// 9. Click on reset password link
		driver.get(reset_link);
		/*
		 * Verify that The Partner activation page is launched with “Email”,
		 * “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		// 10. Fill valid value into all fields but the registered email is not correct

		home.editData(LoginPage.USERNAME, partneruser);	
		home.editData(LoginPage.PASSWORD, new_active_user_password);
		home.editData(LoginPage.CONFIRM_PASSWORD,new_active_user_password);
		// 11. Click on Sign In link
		home.click(LoginPage.SIGN_IN);
		/*
		 * An error message “Password does not matched” displays and the password is not reset successfully
		 */
		//Thread.sleep(1000);
		Assert.assertTrue(home.checkMessageDisplay(LoginPage.errMessage[1]));
		/*
		 * Teardown: Delete user
		 */
		
		// Navigate to home page
		driver.get(siteBase.toString());
		// Log into DTS portal as super partner user
		//home.login(superpartneruser, superpartnerpassword);
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		home.deleteUserByEmail(data.get("email"));
	}
	
	/*
	 * Verify that user status changes to “Active” after resetting password successfully
	 */
	@Test
	public void TC093PCUS_15() {
		result.addLog("ID : TC093PCUS_15 : Verify that user status changes to 'Active' after resetting password successfully");
		/*
		Pre-condition: DTS user has "Add and Manage DTS Users" rights.

		1. Navigate to DTS portal
		2. Log into DTS portal as a Partner user successfully
		3. Click "Users" tab
		4. Select a Partner User on User table
		VP: The Reset Password link is displayed on User Info page
		5. Click on Reset Password link
		6. Log out DTS portal
		7. Open user mailbox that has use to reset password
		VP: A reset password email is sent to user.
		8. Open reset password email
		VP: The reset password email contains reset password link
		9. Click on reset password link
		VP: The Partner activation page is launched with “Email”, “Password”, and “Confirm Password” fields.
		10. Fill valid value into all fields
		11. Click on Sign In link
		VP: User password is reset successfully and the portal is redirected to Home Page
		12. Click on partner user's email on top right corner
		13. Select “User Account” item
		VP: The portal is redirected to User Info page and the user status is changed to “Active”.
		*/
		/*
		 * Pre-condition: Create new partner user
		 */
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server,pdts_email, email_password);
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		// Delete user if exist
		home.deleteUserByEmail(pdts_email);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String, String> data = TestData.partnerUser();
		data.put("email", pdts_email);
		home.addUser(AddUser.getPartnerUser(), data);
		/*
		 * ************************************************
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Select a Partner User on User table
		home.selectUserByEmail(data.get("email"));
		/*
		 * VP: The Reset Password link is displayed on User Info page
		 * VP: The user status is "Invited"
		 */
		Assert.assertTrue(home.isElementPresent(PartnerUserMgmt.RESET_PASSWORD));
		Assert.assertTrue(home.getTextByXpath(UserInfo.USER_STATUS).contains("Invited"));
		// 5. Click on Reset Password link
		home.click(PartnerUserMgmt.RESET_PASSWORD);
		home.switchWindowClickOption("Reset");
		// 6. Log out DTS portal
		// 7. Open user mailbox that use to reset password
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email, email_password, messageCount + 1);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, pdts_email, email_password);
		/*
		 * Verify that A reset password email is sent to user
		 */
		Assert.assertTrue(emailTitle.contains("Reset password"));
		// 8. Open reset password email
		String reset_link = MailUtil.getLinkActive(yahoo_imap_server, pdts_email, email_password);
		/*
		 * VP: The reset password email contains reset password link
		 */
		Assert.assertNotNull(reset_link);
		// 9. Click on reset password link
		driver.get(reset_link);
		/*
		 * Verify that The Partner activation page is launched with “Email”,
		 * “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		// 10. Fill valid value into all fields but the registered email is not correct

		home.editData(LoginPage.USERNAME, data.get("email"));	
		home.editData(LoginPage.PASSWORD, new_active_user_password);
		home.editData(LoginPage.CONFIRM_PASSWORD,new_active_user_password);
		// 11. Click on Sign In link
		home.click(LoginPage.SIGN_IN);
		home.click(Xpath.lbLogout);
		home.click(Xpath.loginAccount);
		//The portal is redirected to User Info page and the user status is changed to “Active”.
		Assert.assertTrue(home.getTextByXpath(UserInfo.USER_STATUS).contains("Active"));
		/*
		 * Teardown: Delete user
		 */
		// Log out
		home.logout();
		// Log into DTS portal as super partner user
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		home.deleteUserByEmail(data.get("email"));
	}
	
}