package com.dts.adminportal.partner.test;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.MailUtil;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.DTSHome;
import dts.com.adminportal.model.PartnerHomePage;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.UsersList;
import dts.com.adminportal.model.Xpath;

public class PartnerUserPrivilegesUsers extends CreatePage{
	private HomeController home;
	
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}
	@BeforeMethod
	public void loginBeforeTest() {
		result = home.login(superUsername, superUserpassword);
		
	}
	/*
	 Verify that the “Users” tab is not displayed when the “Add and manage users” privilege is disabled.
	*/
	@Test
	public void TCPPU_01() {
		result.addLog("ID : TCPPU_01 : Verify that the “Users” tab is not displayed when the “Add and manage users” privilege is disabled. ");
		/*
		    1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Disable the  “Add and manage users” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
		*/
		//3. Navigate to "Users" page
		home.click(DTSHome.LINK_USERS);
		//4. Select a partner user from the Users table
		home.selectUserByEmail(partneruser);
		//5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		//6. Disable the  “Add and manage users” privilege
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		//7. Log out DTS portal
		home.logout();
		//8. Log into DTS portal as above partner user successfully
		home.login(partneruser, password);
		//Verify that the “Users” tab is not displayed.
		Assert.assertFalse(home.isElementPresent(PartnerHomePage.LINK_PARTNER_USER), "Users tab is not exist");
	}
	
	/*
	 Verify that the partner user can add new user when the “Add and manage users” privilege is enabled.
	*/
	@Test
	public void TCPPU_02() {
		result.addLog("ID : TCPPU_02 : Verify that the partner user can add new user when the “Add and manage users” privilege is enabled.");
		/*
		    1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage users” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP: Verify that the “Users” tab is displayed.
			9. Navigate to “Users” page
			VP: Verify that the “Add New User” link is displayed in User list page
			10. Click “Add New User” link
			11. Fill valid value into all required fields
			12. Click “Save” link
			VP: Verify that the 093P New User Created Success Message page is displayed.
			13. Click on “Users” tab
		*/
		// 3. Navigate to "Users" page
		home.click(DTSHome.LINK_USERS);
		// 4. Select a partner user from the Users table
		home.selectUserByEmail(partneruser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner user successfully
		home.login(partneruser, password);
		//VP: Verify that the “Users” tab is displayed.
		Assert.assertTrue(home.isElementPresent(PartnerHomePage.LINK_PARTNER_USER), "Users tab is exist");
		// 9. Navigate to “Users” page
		home.click(PartnerHomePage.LINK_PARTNER_USER);
		// VP: Verify that the “Add New User” link is displayed in User list page
		Assert.assertTrue(home.isElementPresent(UsersList.ADD_USER));
		// 10. Click “Add New User” link
		home.click(UsersList.ADD_USER);
		// 11. Fill valid value into all required fields
		// 12. Click “Save” link
		Hashtable<String,String> data = TestData.partnerUser();
		home.addUser(AddUser.getPartnerUser(), data);
		// VP: Verify that the 093P New User Created Success Message page is displayed.
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// 13. Click on “Users” tab
		home.click(Xpath.LINK_PARTNER_USER);
		// Verify new use is added into user list successfully.
		home.checkUserExistByEmail(data.get("email"));
		//Delete user after add user successfully
		home.selectUserByEmail(data.get("email"));
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*
	 * Verify that the partner user can edit user info when the “Add and manage users” privilege is enabled.
	 */
	@Test
	public void TCPPU_03() throws InterruptedException {
		result.addLog("ID : TCPPU_03 : Verify that the partner user can edit user info when the “Add and manage users” privilege is enabled.");
		/*
		    1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage users” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP: Verify that the “Users” tab is displayed.
			9. Navigate to “Users” page
			10. Select a user from users list
			VP: Verify that the “Edit” link is displayed in  User edit page
			11. Click “Edit” link
			12. Change some user's info in User edit page
			13. Click “Save” link
		*/
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a partner user from the Users table
		home.selectUserByEmail(partneruser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner user successfully
		home.login(partneruser, password);
		// VP: Verify that the “Users” tab is displayed.
		Assert.assertTrue(home.isElementPresent(PartnerHomePage.LINK_PARTNER_USER), "Users tab is exist");
		// 9. Navigate to “Users” page
		home.click(PartnerHomePage.LINK_PARTNER_USER);
		// 10. Select a user from users list
		home.selectUserByEmail(superpartneruser);
		// VP: Verify that the “Edit” link is displayed in  User edit page
		Assert.assertTrue(home.isElementPresent(UserMgmt.EDIT));
		// 11. Click “Edit” link
		home.click(UserMgmt.EDIT);
		//12. Change some user's info in User edit page
		String newTitle = "New title" + RandomStringUtils.randomNumeric(10);
		Thread.sleep(5000);
		home.editData(AddUser.TITLE, newTitle);
		//13. Click “Save” link
		home.click(AddUser.SAVE);
		/*
		 * Verify that the user info page is displayed with new user's info
		 */
		Assert.assertEquals(home.getTextByXpath(UserMgmt.TITLE), newTitle);
	}
	
	/*
	 * Verify that the partner user can reset password of other user when the “Add and manage users” privilege is enabled
	 */
	@Test
	public void TCPPU_04() {
		result.addLog("ID : TCPPU_04 : Verify that the partner user can reset password of other user when the “Add and manage users” privilege is enabled");
		/*
		   	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage users” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP: Verify that the “Users” tab is displayed.
			9. Navigate to “Users” page
			10. Select a user from users list
			VP: Verify that the “Reset Password” link is displayed in  User edit page
			11. Click “Reset Password” link
		*/
		/*
		 * Pre-condition: Create new Partner user
		 */
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server, pdts_email, email_password);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Delete user if exist
		home.deleteUserByEmail(pdts_email);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.partnerUser();
		data.put("email", pdts_email);
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a partner user from the Users table
		// Work around due to issue
		home.selectFilterByName(UsersList.FILTER_COMPANY, partner_company_name);
		// Select partner user
		home.selectUserByEmail(partneruser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner user successfully
		home.login(partneruser, password);
		/*
		 * VP: Verify that the “Users” tab is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_PARTNER_USER));
		// 9. Navigate to “Users” page
		home.click(Xpath.LINK_PARTNER_USER);
		// 10. Select a user from users list
		home.selectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that the “Reset Password” link is displayed in  User edit page
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.RESET_PASSWORD));
		// 11. Click “Reset Password” link
		home.click(UserMgmt.RESET_PASSWORD);
		home.switchWindowClickOption("Reset");
		/*
		 * Verified that a reset password email is sent to user
		 */
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email, email_password, messageCount);
		Assert.assertTrue(MailUtil.getNewEmailSubject(yahoo_imap_server, pdts_email, email_password).contains("Reset password"));
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*
	 * Verify that the partner user can delete another user when the “Add and manage users” privilege is enabled.
	 */
	@Test
	public void TCPPU_05() {
		result.addLog("ID : TCPPU_05 : Verify that the partner user can delete another user when the “Add and manage users” privilege is enabled");
		/*
		    1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage users” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP: Verify that the “Users” tab is displayed.
			9. Navigate to “Users” page
			10. Select a user from users list
			VP: Verify that the “Delete” link is displayed in  User edit page
			11. Click “Delete” link
			12. Click “Delete” button in delete confirmation dialog
		*/
		/*
		 * PreConditin: Create new user
		 */
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Click Add user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.partnerUser();
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a partner user from the Users table
		home.dtsSelectUserByEmail(partneruser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner user successfully
		home.login(partneruser, password);
		// VP: Verify that the “Users” tab is displayed.
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_PARTNER_USER));
		// 9. Navigate to “Users” page
		home.click(Xpath.LINK_PARTNER_USER);
		// 10. Select a user from users list
		home.selectUserByEmail(data.get("email"));
		// VP: Verify that the “Delete” link is displayed in User edit page
		Assert.assertTrue(home.isElementPresent(UserMgmt.DELETE_ACCOUNT));
		// 11. Click “Delete” link
		// 12. Click “Delete” button in delete confirmation dialog
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
		/*
		 * Verify that the User list page is displayed without the deleted user
		 */
		Assert.assertEquals(home.existsElement(UsersList.getListElementPartnerUser()).getResult(), "Pass");
		Assert.assertFalse(home.checkUserExistByEmail(data.get("email")));
	}
	
	/*
	 * Verify that the partner users can edit their own info and privileges when the “Add and manage users” privilege is enabled
	 */
	@Test
	public void TCPPU_06() {
		result.addLog("ID : TCPPU_06 : Verify that the partner users can edit their own info and privileges when the “Add and manage users” privilege is enabled");
		/*
		    1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage users” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP: Verify that the “Users” tab displayed.
			9. Click on partner user's email on top right corner
			10. Select “User Account” item
			VP: Verify that the “Edit” and “Change Password” link are displayed in  “Account Information” page
			11. Click “Edit” link
		*/
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a partner user from the Users table
		home.selectUserByEmail(partneruser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner user successfully
		home.login(partneruser, password);
		/*
		 * VP: Verify that the “Users” tab is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_PARTNER_USER));
		// 9. Click on partner user's email on top right corner
		home.click(Xpath.lbLogout);
		// 10. Select “User Account” item
		home.click(Xpath.loginAccount);
		home.waitForElementClickable(UserMgmt.EDIT);
		/*
		 *  VP: Verify that the “Edit” and “Change Password” link are displayed in  “Account Information” page
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.EDIT));
		Assert.assertTrue(home.isElementPresent(UserMgmt.CHANGE_PASSWORD));
		// 11. Click “Edit” link
		home.click(UserMgmt.EDIT);
		/*
		 * Verify that the “Edit Account” page is displayed, the given name,
		 * family name, title, email, country code, the phone fields and the
		 * privilege notification are editable. But with the site privileges
		 * section, only display what privileges are displayed in Account info
		 * page and all of them are un-editable.
		 */
		Assert.assertTrue(home.isElementEditable(AddUser.getUserInfo()));
		Assert.assertTrue(home.isAllNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE));
		Assert.assertTrue(home.isAllPrivilegeDisable(AddUser.PRIVILEGES_TABLE));
	}
	
	/*
	 * Verify that the partner users can edit their own info including privileges notification but not for privileges when the “Add and manage users” privilege is disabled
	 */
	@Test
	public void TCPPU_08() {
		result.addLog("ID : TCPPU_08 : Verify that the partner users can edit their own info including privileges notification but not for privileges when the “Add and manage users” privilege is disabled");
		/*
		  	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Disable the  “Add and manage users” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP: Verify that the “Users” tab is not displayed.
			9. Click on partner user's email on top right corner
			10. Select “User Account” item
			VP: Verify that the “Edit” and “Change Password” link are displayed in  “Account Information” page
			11. Notice for the current privilege of user
			12. Click “Edit” link
		*/
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a partner user from the Users table
		home.selectUserByEmail(partneruser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the  “Add and manage users” privilege
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner user successfully
		home.login(partneruser, password);
		/*
		 * VP: Verify that the “Users” tab is not displayed
		 */
		Assert.assertFalse(home.isElementPresent(Xpath.LINK_USERS));
		// 9. Click on partner user's email on top right corner
		home.click(Xpath.lbLogout);
		// 10. Select “User Account” item
		home.click(Xpath.loginAccount);
		/*
		 * VP: Verify that the “Edit” and “Change Password” link are displayed in  “Account Information” page
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.EDIT));
		Assert.assertTrue(home.isElementPresent(UserMgmt.CHANGE_PASSWORD));
		// 11. Notice for the current privilege of user
		// 12. Click “Edit” link
		home.click(UserMgmt.EDIT);
		/*
		 * Verify that the “Edit Account” page is displayed, the given name,
		 * family name, title, email, country code, the phone fields and the
		 * privilege notification are editable. But with the site privileges
		 * section, only display what privileges are displayed in Account info
		 * page and all of them are un-editable
		 */
		Assert.assertTrue(home.isElementEditable(AddUser.getUserInfo()));
		Assert.assertTrue(home.isAllNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE));
		Assert.assertTrue(home.isAllPrivilegeDisable(AddUser.PRIVILEGES_TABLE));
	}
}
