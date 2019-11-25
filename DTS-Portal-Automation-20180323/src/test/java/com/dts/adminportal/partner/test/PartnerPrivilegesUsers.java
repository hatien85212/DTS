package com.dts.adminportal.partner.test;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.DTSHome;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PartnerHomePage;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.model.UsersList;
import com.dts.adminportal.util.MailUtil;
import com.dts.adminportal.util.TestData;

public class PartnerPrivilegesUsers extends BasePage{
	
	@Override
	protected void initData() {
	}	

//	@BeforeMethod
//	public void loginBeforeTest() {
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		
//	}
	/*
	 Verify that the �Users� tab is not displayed when the �Add and manage users� privilege is disabled.
	*/
	@Test
	public void TCPPU_01() {
		userControl.addLog("ID : TCPPU_01 : Verify that the �Users� tab is not displayed when the �Add and manage users� privilege is disabled. ");
		/*
		    1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Disable the  �Add and manage users� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//3. Navigate to "Users" page
		userControl.click(DTSHome.LINK_USERS);
		//4. Select a partner user from the Users table
		userControl.selectUserInfoByEmail(PARTNER_USER);
		//5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		//6. Disable the  �Add and manage users� privilege
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Add_and_manage_users.getName());
		userControl.click(AddUser.SAVE);
		//7. Log out DTS portal
		userControl.logout();
		//8. Log into DTS portal as above partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		//Verify that the �Users� tab is not displayed.
		Assert.assertFalse(userControl.isElementPresent(PartnerHomePage.LINK_PARTNER_USER), "Users tab is not exist");
	}
	
	/*
	 Verify that the partner user can add new user when the �Add and manage users� privilege is enabled.
	*/
	@Test
	public void TCPPU_02() {
		userControl.addLog("ID : TCPPU_02 : Verify that the partner user can add new user when the �Add and manage users� privilege is enabled.");
		/*
		    1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage users� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP: Verify that the �Users� tab is displayed.
			9. Navigate to �Users� page
			VP: Verify that the �Add New User� link is displayed in User list page
			10. Click �Add New User� link
			11. Fill valid value into all required fields
			12. Click �Save� link
			VP: Verify that the 093P New User Created Success Message page is displayed.
			13. Click on �Users� tab
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(DTSHome.LINK_USERS);
		// 4. Select a partner user from the Users table
		userControl.selectUserInfoByEmail(PARTNER_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the  �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Add_and_manage_users.getName());
		userControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		userControl.logout();
		// 8. Log into DTS portal as above partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		//VP: Verify that the �Users� tab is displayed.
		Assert.assertTrue(userControl.isElementPresent(PartnerHomePage.LINK_PARTNER_USER), "Users tab is exist");
		// 9. Navigate to �Users� page
		userControl.click(PartnerHomePage.LINK_PARTNER_USER);
		// VP: Verify that the �Add New User� link is displayed in User list page
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		// 10. Click �Add New User� link
		userControl.click(UsersList.ADD_USER);
		// 11. Fill valid value into all required fields
		// 12. Click �Save� link
		Hashtable<String,String> data = TestData.partnerUser();
		data.remove("company");
		userControl.addUser(AddUser.getPartnerUser(), data);
		// VP: Verify that the 093P New User Created Success Message page is displayed.
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.messages.Success.getName()));
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.messages.Success_Email_Info.getName()));
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.messages.Success_Email_Invite.getName()));
		// 13. Click on �Users� tab
		userControl.click(PageHome.LINK_PARTNER_USER);
		// Verify new use is added into user list successfully.
		userControl.checkUserExistByEmail(data.get("email"));
		//Delete user after add user successfully
		userControl.selectUserInfoByEmail(data.get("email"));
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*
	 * Verify that the partner user can edit user info when the �Add and manage users� privilege is enabled.
	 */
	@Test
	public void TCPPU_03() throws InterruptedException {
		userControl.addLog("ID : TCPPU_03 : Verify that the partner user can edit user info when the �Add and manage users� privilege is enabled.");
		/*
		    1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage users� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP: Verify that the �Users� tab is displayed.
			9. Navigate to �Users� page
			10. Select a user from users list
			VP: Verify that the �Edit� link is displayed in  User edit page
			11. Click �Edit� link
			12. Change some user's info in User edit page
			13. Click �Save� link
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a partner user from the Users table
		userControl.selectUserInfoByEmail(PARTNER_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the  �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Add_and_manage_users.getName());
		userControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		userControl.logout();
		// 8. Log into DTS portal as above partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// VP: Verify that the �Users� tab is displayed.
		Assert.assertTrue(userControl.isElementPresent(PartnerHomePage.LINK_PARTNER_USER), "Users tab is exist");
		// 9. Navigate to �Users� page
		userControl.click(PartnerHomePage.LINK_PARTNER_USER);
		// 10. Select a user from users list
		userControl.selectUserInfoByEmail(SUPER_PARTNER_USER);
		// VP: Verify that the �Edit� link is displayed in  User edit page
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.EDIT));
		// 11. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		//12. Change some user's info in User edit page
		String newTitle = "New title" + RandomStringUtils.randomNumeric(10);
		Thread.sleep(5000);
		userControl.editData(AddUser.TITLE, newTitle);
		//13. Click �Save� link
		userControl.click(AddUser.SAVE);
		/*
		 * Verify that the user info page is displayed with new user's info
		 */
		Assert.assertEquals(userControl.getTextByXpath(UserMgmt.TITLE), newTitle);
	}
	
	/*
	 * Verify that the partner user can reset password of other user when the �Add and manage users� privilege is enabled
	 */
	@Test
	public void TCPPU_04() {
		userControl.addLog("ID : TCPPU_04 : Verify that the partner user can reset password of other user when the �Add and manage users� privilege is enabled");
		/*
		   	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage users� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP: Verify that the �Users� tab is displayed.
			9. Navigate to �Users� page
			10. Select a user from users list
			VP: Verify that the �Reset Password� link is displayed in  User edit page
			11. Click �Reset Password� link
		*/
		/*
		 * Pre-condition: Create new Partner user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Delete user if exist
		userControl.changeEmailOfUser(PARTNER_DTS_EMAIL);
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.partnerUser();
		data.put("email", PARTNER_DTS_EMAIL);
		data.put("company", BasePage.PARTNER_COMPANY_NAME);
		userControl.addUser(AddUser.getDTSUser(), data);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a partner user from the Users table
		// Work around due to issue
		userControl.selectFilterByName(UsersList.FILTER_COMPANY, PARTNER_COMPANY_NAME);
		// Select partner user
		userControl.selectUserInfoByEmail(PARTNER_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the  �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Add_and_manage_users.getName());
		userControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		userControl.logout();
		// 8. Log into DTS portal as above partner user successfully
		loginControl.login(PARTNER_USER,PARTNER_PASSWORD);
		/*
		 * VP: Verify that the �Users� tab is displayed
		 */
		Assert.assertTrue(userControl.isElementPresent(PageHome.LINK_PARTNER_USER));
		// 9. Navigate to �Users� page
		userControl.click(PageHome.LINK_PARTNER_USER);
		// 10. Select a user from users list
		userControl.selectUserInfoByEmail(data.get("email"));
		/*
		 * VP: Verify that the �Reset Password� link is displayed in  User edit page
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.RESET_PASSWORD));
		// 11. Click �Reset Password� link
		userControl.click(UserMgmt.RESET_PASSWORD);
		userControl.switchWindowClickOption("Reset");
		/*
		 * Verified that a reset password email is sent to user
		 */
		MailUtil.waitForNewInboxMessage(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD, messageCount);
		Assert.assertTrue(MailUtil.getNewEmailSubject(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD).contains("Reset password"));
		// Delete user
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*
	 * Verify that the partner user can delete another user when the �Add and manage users� privilege is enabled.
	 */
	@Test
	public void TCPPU_05() {
		userControl.addLog("ID : TCPPU_05 : Verify that the partner user can delete another user when the �Add and manage users� privilege is enabled");
		/*
		    1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage users� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP: Verify that the �Users� tab is displayed.
			9. Navigate to �Users� page
			10. Select a user from users list
			VP: Verify that the �Delete� link is displayed in  User edit page
			11. Click �Delete� link
			12. Click �Delete� button in delete confirmation dialog
		*/
		/*
		 * PreConditin: Create new user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Click Add user link
		userControl.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.partnerUser();
		userControl.addUser(AddUser.getDTSUser(), data);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a partner user from the Users table
		userControl.dtsSelectUserByEmail(PARTNER_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the  �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Add_and_manage_users.getName());
		userControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		userControl.logout();
		// 8. Log into DTS portal as above partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// VP: Verify that the �Users� tab is displayed.
		Assert.assertTrue(userControl.isElementPresent(PageHome.LINK_PARTNER_USER));
		// 9. Navigate to �Users� page
		userControl.click(PageHome.LINK_PARTNER_USER);
		// 10. Select a user from users list
		userControl.selectUserInfoByEmail(data.get("email"));
		// VP: Verify that the �Delete� link is displayed in User edit page
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.DELETE_ACCOUNT));
		// 11. Click �Delete� link
		// 12. Click �Delete� button in delete confirmation dialog
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
		/*
		 * Verify that the User list page is displayed without the deleted user
		 */
		Assert.assertEquals(userControl.existsElement(UsersList.getListElementPartnerUser()), true);
		Assert.assertFalse(userControl.checkUserExistByEmail(data.get("email")));
	}
	
	/*
	 * Verify that the partner users can edit their own info and privileges when the �Add and manage users� privilege is enabled
	 */
	@Test
	public void TCPPU_06() {
		userControl.addLog("ID : TCPPU_06 : Verify that the partner users can edit their own info and privileges when the �Add and manage users� privilege is enabled");
		/*
		    1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage users� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP: Verify that the �Users� tab displayed.
			9. Click on partner user's email on top right corner
			10. Select �User Account� item
			VP: Verify that the �Edit� and �Change Password� link are displayed in  �Account Information� page
			11. Click �Edit� link
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a partner user from the Users table
		userControl.selectUserInfoByEmail(PARTNER_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the  �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Add_and_manage_users.getName());
		userControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		userControl.logout();
		// 8. Log into DTS portal as above partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		/*
		 * VP: Verify that the �Users� tab is displayed
		 */
		Assert.assertTrue(userControl.isElementPresent(PageHome.LINK_PARTNER_USER));
		// 9. Click on partner user's email on top right corner
		userControl.click(PageHome.lbLogout);
		// 10. Select �User Account� item
		userControl.click(PageHome.loginAccount);
		userControl.waitForElementClickable(UserMgmt.EDIT);
		/*
		 *  VP: Verify that the �Edit� and �Change Password� link are displayed in  �Account Information� page
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.EDIT));
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.CHANGE_PASSWORD));
		// 11. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		/*
		 * Verify that the �Edit Account� page is displayed, the given name,
		 * family name, title, email, country code, the phone fields and the
		 * privilege notification are editable. But with the site privileges
		 * section, only display what privileges are displayed in Account info
		 * page and all of them are un-editable.
		 */
		Assert.assertTrue(userControl.isElementEditable(AddUser.getUserInfo()));
		Assert.assertTrue(userControl.isAllNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE));
		Assert.assertTrue(userControl.isAllPrivilegeDisable(AddUser.PRIVILEGES_TABLE));
	}
	
	/*
	 * Verify that the partner users can edit their own info including privileges notification but not for privileges when the �Add and manage users� privilege is disabled
	 */
	@Test
	public void TCPPU_08() {
		userControl.addLog("ID : TCPPU_08 : Verify that the partner users can edit their own info including privileges notification but not for privileges when the �Add and manage users� privilege is disabled");
		/*
		  	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Disable the  �Add and manage users� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP: Verify that the �Users� tab is not displayed.
			9. Click on partner user's email on top right corner
			10. Select �User Account� item
			VP: Verify that the �Edit� and �Change Password� link are displayed in  �Account Information� page
			11. Notice for the current privilege of user
			12. Click �Edit� link
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a partner user from the Users table
		userControl.selectUserInfoByEmail(PARTNER_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Disable the  �Add and manage users� privilege
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Add_and_manage_users.getName());
		userControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		userControl.logout();
		// 8. Log into DTS portal as above partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		/*
		 * VP: Verify that the �Users� tab is not displayed
		 */
		Assert.assertFalse(userControl.isElementPresent(PageHome.LINK_USERS));
		// 9. Click on partner user's email on top right corner
		userControl.click(PageHome.lbLogout);
		// 10. Select �User Account� item
		userControl.click(PageHome.loginAccount);
		/*
		 * VP: Verify that the �Edit� and �Change Password� link are displayed in  �Account Information� page
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.EDIT));
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.CHANGE_PASSWORD));
		// 11. Notice for the current privilege of user
		// 12. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		/*
		 * Verify that the �Edit Account� page is displayed, the given name,
		 * family name, title, email, country code, the phone fields and the
		 * privilege notification are editable. But with the site privileges
		 * section, only display what privileges are displayed in Account info
		 * page and all of them are un-editable
		 */
		Assert.assertTrue(userControl.isElementEditable(AddUser.getUserInfo()));
		Assert.assertFalse(userControl.isElementEditable(AddUser.EMAIL));
		Assert.assertTrue(userControl.isAllNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE));
		Assert.assertTrue(userControl.isAllPrivilegeDisable(AddUser.PRIVILEGES_TABLE));
	}
}
