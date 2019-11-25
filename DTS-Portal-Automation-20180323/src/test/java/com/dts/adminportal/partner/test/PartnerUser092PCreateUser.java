package com.dts.adminportal.partner.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PageLogin;
import com.dts.adminportal.model.PartnerHomePage;
import com.dts.adminportal.model.PartnerUserMgmt;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.UsersList;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.MailUtil;
import com.dts.adminportal.util.TestData;

public class PartnerUser092PCreateUser extends BasePage{
		
	@Override
	protected void initData() {
	}	

	/*
	 * Verify that the "Given Name", "Family Name", "Email" and "Phone" are required when creating new users
	 */
	@Test
	public void TC092PaCUB_01(){
		userControl.addLog("ID : TC092PaCUB_01 : Verify that the 'Given Name', 'Family Name', 'Email' and 'Phone' are required when creating new users");
		/*
			Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Click "Save" link without filling information into  "Given Name", "Family Name", "Email" and "Phone"
			VP: There is an error message  displayed next to "Given Name", "Family Name", "Email" and "Phone" that mention to these fields are required information.

			VP: 092Pa Create User Blank is displayed. The site privileges have following rights: Add and manage Products, Publish and suspend Products, Request product tunings, View Model Allocation Info, Add and manage devices, Publish and suspend devices, Add and manage apps, Publish and suspend apps, Edit Company Info, Edit brand info and Add and manage users.
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Click "Users" tab
		userControl.click(PageHome.LINK_PARTNER_USER);
		// 4. Click "Add New User" link
		userControl.click(UsersList.ADD_USER);
		// 5. Click "Save" link without filling information into  "Given Name", "Family Name", "Email" and "Phone"
		userControl.click(AddUser.SAVE);
		/*
		 * Verify that There is an error message  displayed next to "Given Name", "Family Name", "Email" and "Phone" that mention to these fields are required information
		 */
		Assert.assertTrue(userControl.isElementPresent(AddUser.GIVEN_NAME_MESSAGE));
		Assert.assertTrue(userControl.isElementPresent(AddUser.FAMILY_NAME_MESSAGE));
		Assert.assertTrue(userControl.isElementPresent(AddUser.EMAIL_MESSAGE));
		Assert.assertTrue(userControl.isElementPresent(AddUser.PHONE_MESSAGE));
		//VP: 092Pa Create User Blank is displayed. The site privileges have following rights: Add and manage Products, Publish and suspend Products, Request product tunings, View Model Allocation Info, Add and manage devices, Publish and suspend devices, Add and manage apps, Publish and suspend apps, Edit Company Info, Edit brand info and Add and manage users.
		ArrayList<String> privileges = userControl.getPrivileges(AddUser.PRIVILEGES_TABLE);
		Assert.assertTrue(ListUtil.containsAll(privileges, Privileges.privileges.getNames()));
	}
	
	
	/*
	 * Verify that new user could not be created with already exists email address.
	 */
	@Test
	public void TC092PaCUB_03(){
		userControl.addLog("ID : TC092PaCUB_07 : Verify that new user could not be created with already exists email address.");
		
		/*
			Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill all valid values into required fields except Email
			6. Fill an email address into "Email" text field which is assigned to another user's account 
			7. Click "Save" link
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Click "Users" tab
		userControl.click(PageHome.LINK_PARTNER_USER);
		// 4. Click "Add New User" link
		userControl.click(UsersList.ADD_USER);
		// 5. Fill all valid values into required fields except Email
		// 6. Fill an email address into "Email" text field which is assigned to another user's account
		Hashtable<String,String> data = TestData.partnerUser();
		data.remove("company");
		data.put("email", PARTNER_USER);
		data.remove("save");
		userControl.addUser(AddUser.getPartnerUser(), data);
		// 7. Click "Save"
		userControl.click(AddUser.SAVE);
		/*
		 * Verify that There is an error message displayed which mentions to an already exists email address
		 */
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.messages.Account_Existed.getName()));
		
	}
	
	/*
	 * Verify that partner user could not add new user with invalid email address format.
	 */
	@Test
	public void TC092PaCUB_04(){
		userControl.addLog("ID : TC092PaCUB_04 : Verify that partner user could not add new user with invalid email address format.");
		/*
			Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill all valid values into required fields except Email
			6. Click "Save" link
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Click "Users" tab
		userControl.click(PageHome.LINK_PARTNER_USER);
		// 4. Click "Add New User" link
		userControl.click(UsersList.ADD_USER);
		// 5. Fill all valid values into required fields except Email
		Hashtable<String,String> data = TestData.partnerUser();
		data.put("email", RandomStringUtils.randomAlphanumeric(20) + ".example.com");
		data.remove("company");
		data.remove("save");
		userControl.addUser(AddUser.getPartnerUser(), data);
		// 6. Click "Save"
		userControl.click(AddUser.SAVE);
		/*
		 * Verify that There is an error message displayed which mention to the incorrect format of email address
		 */
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.messages.Invalid_Format_Email.getName()));
	}
	
	/*
	 * Verify that the user's site privilege works properly
	 */
	@Test 
	public void TC092PbCUF_02(){
		userControl.addLog("ID : TC092PbCUF_03 : Verify that new user could be created without assigning site notifications");
		userControl.addLog("PDPP-1454 - 092 Add new user: New user is able to created without assigning privileges.");
		/*		
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click "Users" tab
		4. Click "Add New User" link
		5. Fill valid values into required fields
		6. Assign all site privilege for new user
		VP: The similar site notification of assigned site privilege are enabled.
		7. Disable all site privilege
		VP: The similar site notification of assigned site privilege are also disabled.
		8. Click "Save" link
		VP: New user could be created successfully
		9. Repeat from step 3 to 5
		10. Enable a site privilege
		11. Disable site notification of above privilege
		12.  Click "Save" link
		VP: New user could be created successfully without assigning site notification
		*/
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Click "Users" tab
		userControl.click(PageHome.LINK_PARTNER_USER);
		// 4. Click "Add New User" link
		userControl.click(PageHome.createNewUser);
		// 5. Fill valid values into required fields
		Hashtable <String, String> userdata= TestData.partnerUser();
		userdata.remove("company");
		userdata.remove("save");
		userControl.addUser(AddUser.getPartnerUser(), userdata);
		//6.  Assign all site privilege for new user
		//userControl.enableAllPrivileges();
		//	VP: The similar site notification of assigned site privilege is enabled.
		Assert.assertTrue(userControl.isNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Add_and_manage_products.getName()));
		Assert.assertTrue(userControl.isNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_brand_info.getName()));
		Assert.assertTrue(userControl.isNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Publish_and_suspend_products.getName()));
//		Assert.assertTrue(userControl.isNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Request_product_tunings.getName()));
		
		//7. Disable all site privileges
		userControl.disableAllPrivileges(AddUser.PRIVILEGES_TABLE);
		
		//VP: The similar site notification of assigned site privilege is also disabled.
		Assert.assertFalse(userControl.isNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Add_and_manage_products.getName()));
		Assert.assertFalse(userControl.isNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_brand_info.getName()));
		Assert.assertFalse(userControl.isNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Publish_and_suspend_products.getName()));
		Assert.assertFalse(userControl.isNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Can_request_DTS_tune_products.getName()));
		Assert.assertFalse(userControl.isNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_Company_Info.getName()));
		Assert.assertFalse(userControl.isNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_brand_info.getName()));
		Assert.assertFalse(userControl.isNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Add_and_manage_users.getName()));
		
		//8. Click "Save" link
		userControl.click(AddUser.SAVE);
		//VP: New user could not be created successfully
		//TODO: Below section should be optimized again after issue is resolved
		Assert.assertTrue(userControl.isElementPresent(PageHome.userCreateSucess));
		//9. Repeat from step 3 to 5
		userControl.click(PageHome.LINK_PARTNER_USER);
		userControl.click(PageHome.createNewUser);
		Hashtable <String, String> userdata1 = TestData.partnerUser();
		userdata1.remove("company");
		userdata1.remove("save");
		userControl.addUser(AddUser.getPartnerUser(), userdata1);
		//10. Enable a site privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Add_and_manage_products.getName());
		//11. Disable site notification of above privilege
		userControl.isNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Add_and_manage_products.getName());
		//userControl.uncheckAllNotification(AddUser.PRIVILEGES_TABLE);
		//12.  Click "Save" link
		userControl.click(AddUser.SAVE);
		//VP: New user could be created successfully without assigning site notification
		Assert.assertTrue(userControl.isElementPresent(PageHome.userCreateSucess));
		// Teardown
		// Delete user
		userControl.click(PageHome.LINK_PARTNER_USER);
		userControl.deleteUserByEmail(userdata.get("email"));
		userControl.deleteUserByEmail(userdata1.get("email"));
	}
	

	/*
	 * Verify that the activation link is expired after the registered user is activated
	 */
	@Test
	public void TC093PCUS_07() {
		userControl.addLog("ID : TC093PCUS_07 : Verify that the activation link is expired after the registered user is activated");
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
			VP: The Partner activation page is launched with �Email�, �Password�, and �Confirm Password� fields
			12. Fill valid value into all fields
			13. Click on Sign In link
			VP: The portal is redirected to Home Page and the activation user is already signed in
			14. Repeat from step 8 to 13
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		userControl.click(PageHome.LINK_USERS);
		userControl.changeEmailOfUser(PARTNER_DTS_EMAIL);
		userControl.logout();
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD);
		// 3. Click "Users" tab
		userControl.click(PageHome.LINK_PARTNER_USER);		
		// 4. Click "Add New User" link
		userControl.click(UsersList.ADD_USER);
		// 5. Fill valid values into required fields
		// 6. Assign at least one site privilege
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.partnerUser();
		data.put("email", PARTNER_DTS_EMAIL);
		userControl.addUser(AddUser.getPartnerUser(), data);
		/*
		 * VP: The "Success" page is displayed
		 */
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.messages.Success.getName()));
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.messages.Success_Email_Info.getName()));
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.messages.Success_Email_Invite.getName()));
		// 8. Log out DTS portal
		// 9. Open invited user's mailbox which is used to register user
		MailUtil.waitForNewInboxMessage(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD, messageCount);
		String emailTitle = MailUtil.getNewEmailSubject(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD);
		/*
		 * Verify that The invitation message is sent to user
		 */
		String invite_message_title = "Your Invitation to DTS Headphone:X Partner Portal";
		Assert.assertEquals(emailTitle, invite_message_title);
		// 10. Open the invitation email
		String activation_link = MailUtil.getLinkActive(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD);
		/*
		 * VP: The invitation email contains activation link
		 */
		Assert.assertNotNull(activation_link);
		// 11. Click on invitation link
		driver.get(activation_link);
		/*
		 * Verify that The Partner activation page is launched with �Email�,
		 * �Password�, and �Confirm Password� fields
		 */
		Assert.assertEquals(userControl.existsElement(PageLogin.getActivationInfoPage()), true);
		// 12. Fill valid value into all fields
		// 13. Click on Sign In link
		userControl.activeUser(PARTNER_DTS_EMAIL, NEW_ACTIVE_USER_PASSWORD);
		/*
		 * Verify that The portal is redirected to Home Page and the activation
		 * user is already signed in
		 */
		Assert.assertEquals(userControl.existsElement(PartnerHomePage.getElementInfo()), true);
		Assert.assertEquals(userControl.getTextByXpath(PartnerHomePage.EMAIL), PARTNER_DTS_EMAIL);
		// 14. Repeat from step 8 to 13
		driver.get(activation_link);
		// Fill valid value into all fields
		// Click on Sign In link
		userControl.activeUser(PARTNER_DTS_EMAIL, NEW_ACTIVE_USER_PASSWORD);
		/*
		 * Verify that An error message �Cannot active account, the link may be not valid or expried� displays
		 */
		Assert.assertTrue(userControl.checkMessageDisplay(PageLogin.errMessage.Cannot_active_account.getName()));
		/*
		 * Delete user
		 */
		// Navigate to home page
		driver.get(url);
		userControl.waitForAjax();
		// Log out
		userControl.logout();
		// Log into DTS portal as super partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_PARTNER_USER);
		userControl.deleteUserByEmail(data.get("email"));
	}
	
	/*
	 * Verify that the reset password link is expired after user resets password successfully
	 */
	@Test
	public void TC093PCUS_12() {
		userControl.addLog("ID : TC093PCUS_12 : Verify that the reset password link is expired after user resets password successfully");
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
			VP: The Partner activation page is launched with �Email�, �Password�, and �Confirm Password� fields.
			10. Fill valid value into all fields but the value of Password is not matched with Confirm Password
			11. Click on Sign In link
			VP: An error message �Password does not matched� displays and the password is not reset successfully
			12. Fill valid value into all fields but the registered email is not correct
			13. Click on Sign In link
			VP: An error message �Cannot reset password, the link may be not valid or expried� displays and the password is not reset successfully
			14. Fill valid value into all fields
			15. Click on Sign In link
			VP: User password is reset successfully and the portal is redirected to Home Page
			16. Repeat from step 6 to 10
			17. Fill valid value into all required fields
		*/
		/*
		 * Pre-condition: Create new partner user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		userControl.click(PageHome.LINK_USERS);
		userControl.changeEmailOfUser(PARTNER_DTS_EMAIL);
		userControl.logout();
		
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(YAHOO_IMAP_SERVER,PARTNER_DTS_EMAIL, EMAIL_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_PARTNER_USER);
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String, String> data = TestData.partnerUser();
		data.put("email", PARTNER_DTS_EMAIL);
		userControl.addUser(AddUser.getPartnerUser(), data);
		/*
		 * ************************************************
		 */
		// 3. Click "Users" tab
		userControl.click(PageHome.LINK_PARTNER_USER);
		// 4. Select a Partner User on User table
		userControl.selectUserInfoByEmail(data.get("email"));
		/*
		 * VP: The Reset Password link is displayed on User Info page
		 */
		Assert.assertTrue(userControl.isElementPresent(PartnerUserMgmt.RESET_PASSWORD));
		// 5. Click on Reset Password link
		userControl.click(PartnerUserMgmt.RESET_PASSWORD);
		userControl.switchWindowClickOption("Reset");
		// 6. Log out DTS portal
		// 7. Open user mailbox that use to reset password
		MailUtil.waitForNewInboxMessage(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD, messageCount + 1);
		String emailTitle = MailUtil.getNewEmailSubject(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD);
		/*
		 * Verify that A reset password email is sent to user
		 */
		Assert.assertTrue(emailTitle.contains("Reset password"));
		// 8. Open reset password email
		String reset_link = MailUtil.getLinkActive(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD);
		/*
		 * VP: The reset password email contains reset password link
		 */
		Assert.assertNotNull(reset_link);
		
		//Click on the activation link
		driver.get(reset_link);
		//Verify that The Partner activation page is launched
		Assert.assertEquals(userControl.existsElement(PageLogin.getActivationInfoPage()), true);
		
		userControl.activeUser(PARTNER_DTS_EMAIL, NEW_ACTIVE_USER_PASSWORD);
		//VP: User password is reset successfully and the portal is redirected to Home Page
		Assert.assertEquals(userControl.existsElement(PartnerHomePage.getElementInfo()), true);

		Assert.assertTrue(loginControl.logout());
		// 9. Click on reset password link
		driver.get(reset_link);
		/*
		 * Verify that The Partner activation page is launched with �Email�,
		 * �Password�, and �Confirm Password� fields
		 */
		Assert.assertEquals(userControl.existsElement(PageLogin.getActivationInfoPage()), true);
		// 10.  Fill valid value into all fields but the value of Password is not matched with Confirm Password
		userControl.editData(PageLogin.USERNAME, PARTNER_DTS_EMAIL);	
		userControl.editData(PageLogin.PASSWORD, EMAIL_PASSWORD);
		userControl.editData(PageLogin.CONFIRM_PASSWORD,NEW_ACTIVE_USER_PASSWORD);
		// 11. Click on Sign In link
		userControl.click(PageLogin.SIGN_IN);
		/*
		 * An error message 'Password does not matched' displays and the password is not reset successfully
		 */
		Assert.assertTrue(userControl.checkMessageDisplay(PageLogin.errMessage.Password_does_not_matched.getName()));
		
		//12. Fill valid value into all fields but the registered email is not correct
		userControl.editData(PageLogin.USERNAME, PARTNER_USER);	
		userControl.editData(PageLogin.PASSWORD, NEW_ACTIVE_USER_PASSWORD);
		userControl.editData(PageLogin.CONFIRM_PASSWORD,NEW_ACTIVE_USER_PASSWORD);
		//13. Click on Sign In link
		userControl.click(PageLogin.SIGN_IN);
		//VP: An error message �Cannot reset password, the link may be not valid or expried� displays and the password is not reset successfully
		Assert.assertTrue(userControl.checkMessageDisplay(PageLogin.errMessage.Cannot_reset_password.getName()));
		
		//Click on the activation link
		driver.get(reset_link);
		//Verify that The Partner activation page is launched
		Assert.assertEquals(userControl.existsElement(PageLogin.getActivationInfoPage()), true);
		
		//17. Fill valid value into all required fields
		userControl.editData(PageLogin.USERNAME, data.get("email"));	
		userControl.editData(PageLogin.PASSWORD, NEW_ACTIVE_USER_PASSWORD);
		userControl.editData(PageLogin.CONFIRM_PASSWORD,NEW_ACTIVE_USER_PASSWORD);
		// Click on Sign In link
		userControl.click(PageLogin.SIGN_IN);
		//VP: An error message �Cannot reset password, the link may be not valid or expried� displays
		Assert.assertTrue(userControl.checkMessageDisplay(PageLogin.errMessage.Cannot_reset_password_link_expired.getName()));
		driver.get("https://devportal.dts.com/saap");
		// Log into DTS portal as super partner user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_PARTNER_USER);
		Assert.assertTrue(userControl.deleteUserByEmail(data.get("email")));
	}

}
