package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PartnerUserMgmt;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.UserInfo;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.model.UsersList;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.MailUtil;
import com.dts.adminportal.util.TestData;

public class DTSUser090DUserInfo extends BasePage{

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
	}
	
	/*
	 * Verify that the 090D User Info page displays  user's information properly
	 */
	@Test
	public void TC090DUI_01(){
		userControl.addLog("ID : TC090DUI_01 : Verify that the 090D User Info page displays  user's information properly");
		/*
		Pre-condition: User has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a user from users table
			"VP: The 090D User Info page displays:
			User's first name, last name, title, email, phone, status, site privileges, notification and action module
			VP: In 090 User Info page, the ""Actions"" module shows up  ""Edit"", ""Delete"", ""Reset Password""and ""Suspend"" links."
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Users" tab
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a user from users table
		userControl.dtsSelectUserByEmail(PARTNER_USER);
		Assert.assertEquals(true, userControl.existsElement(UserMgmt.getElementsInfo()));
		
		ArrayList<String> actions = userControl.getTextListByXpaths(UserMgmt.getActions());
		Assert.assertTrue(ListUtil.containsAll(userControl.getAllLinkA(UserMgmt.ACTION_MODULE),actions));
		Assert.assertFalse(userControl.isElementPresent(UserMgmt.RESTORE));
	}
	
	/*
	 * Verify that the site privileges info displayed correctly according to user's assigned site privileges
	 */
	@Test
	public void TC090DUI_03(){
		userControl.addLog("ID : TC090DUI_03 : Verify that the site privileges info displayed correctly according to user's assigned site privileges");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a user from users table
			5. Click "Edit" link
			6. Select some options in "Site Privileges" section
			7. Log out DTS portal
			8. Log into DTS portal as a above admin who have the same company with above user
			9. Click "Users" tab
			10. Select above user from users table
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a user from users table
		userControl.dtsSelectUserByEmail(PARTNER_USER);
		// 5. Click "Edit" link
		userControl.click(UserInfo.EDIT);
		// 6. Select some options in "Site Privileges" section
		userControl.disableAllPrivileges(AddUser.PRIVILEGES_TABLE);
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		userControl.logout();
		// 8. Log into DTS portal as a above admin who have the same company with above user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 9. Click "Users" tab
		userControl.click(PageHome.LINK_PARTNER_USER);
		// 10. Select above user from users table
		userControl.selectUserInfoByEmail(PARTNER_USER);
		/*
		 * The 090D User Info page displays and the user's site privilege also display correctly of what are set
		 */
		Assert.assertEquals(userControl.existsElement(PartnerUserMgmt.getElementsInfo()), true);
		ArrayList<String> privileges = userControl.getPrivileges(PartnerUserMgmt.SITE_PRIVILEGES_TABLE);
		Assert.assertTrue(privileges.contains(Privileges.Add_and_manage_user));
		// Assign full privileges for above user again
		userControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		userControl.click(AddUser.SAVE);
	}
	
	/*
	 * Verify that user account is not deleted when clicking "Cancel" button on delete confirmation dialog.
	 */
	@Test
	public void TC090DUI_05(){
		userControl.addLog("ID : TC090DUI_05 : Verify that user account is not deleted "
				+ "when clicking 'Cancel' button on delete confirmation dialog.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a user from users table
			5. Click "Delete Account" link in "Actions" module
			VP: Verify that there is a delete confirmation dialog is displayed with "Delete" and "Cancel" button
			6. Click "Cancel" button in delete confirmation dialog
			The delete confirmation dialog disappeared and the portal stays at 090D User Info page.
		 */
		/*
		 * Precondition: Create a new user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Click "Users" tab
		userControl.click(PageHome.LINK_USERS);
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.dtsUser();
		userControl.addUser(AddUser.getDTSUser(), data);
		/*
		 * *******************************************************************
		 */
		// 3. Click "Users" tab
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a user from users table
		userControl.dtsSelectUserByEmail(data.get("email"));
		// 5. Click "Delete Account" link in "Actions" module 
		userControl.click(UserMgmt.DELETE_ACCOUNT);
		userControl.waitForElementClickable(PageHome.BTN_CONFIRMATION_DANGER);
		/*
		 * Verify that there is a delete confirmation dialog is displayed with "Delete" and "Cancel" button
		 */
		Assert.assertEquals(userControl.getTextByXpath(PageHome.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(userControl.getTextByXpath(PageHome.BTN_CONFIRMATION_CANCEL), "Cancel");

		userControl.selectConfirmationDialogOption("Cancel");
		/*
		 * The delete confirmation dialog disappeared and the portal stays at 090D User Info page.
		 */
		Assert.assertFalse(userControl.isElementPresent(PageHome.BTN_CONFIRMATION_DANGER));
		Assert.assertFalse(userControl.isElementPresent(PageHome.BTN_CONFIRMATION_CANCEL));
		
		// Tear-down: Delete user
		userControl.click(UserMgmt.DELETE_ACCOUNT);
		userControl.waitForElementClickable(PageHome.BTN_CONFIRMATION_DANGER);
		userControl.selectConfirmationDialogOption("Delete");
	}
	
	/*
	 * Verify that user account is not suspended when clicking "Cancel" button on suspend confirmation dialog.
	 */
	@Test
	public void TC090DUI_06(){
		userControl.addLog("ID : TC090DUI_06 : Verify that user account is not suspended when "
				+ "clicking 'Cancel' button on suspend confirmation dialog.");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a user from users table whose status is "Active"
			5. Click "Suspend" link in "Actions" module
			VP: Verify that there is a suspend confirmation dialog is displayed with "Suspend" and "Cancel" button
			6. Click "Cancel" button in suspend confirmation dialog
			The suspend confirmation dialog disappeared, portal stays at 090D User Info page and the status of user is "Active"
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Users" tab
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a user from users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click "Suspend" link in "Actions" module
		Assert.assertEquals(userControl.getTextByXpath(UserMgmt.STATUS), "Active");
		userControl.click(UserMgmt.SUSPEND);
		userControl.waitForElementClickable(PageHome.BTN_CONFIRMATION_DANGER);
		/*
		 * Verify that there is a delete confirmation dialog is displayed with "Delete" and "Cancel" button
		 */
		Assert.assertEquals(userControl.getTextByXpath(PageHome.BTN_CONFIRMATION_DANGER), "Suspend");
		Assert.assertEquals(userControl.getTextByXpath(PageHome.BTN_CONFIRMATION_CANCEL), "Cancel");
		
		userControl.selectConfirmationDialogOption("Cancel");
		/*
		 * The suspend confirmation dialog disappeared, portal stays at 090D User Info page and the status of user is "Active"
		 */
		Assert.assertFalse(userControl.isElementPresent(PageHome.BTN_CONFIRMATION_DANGER));
		Assert.assertFalse(userControl.isElementPresent(PageHome.BTN_CONFIRMATION_CANCEL));
		Assert.assertTrue(userControl.existsElement(UserMgmt.getElementsInfo()));
		Assert.assertEquals(userControl.getTextByXpath(UserMgmt.STATUS), "Active");
	}
	
	/*
	 * Verify that suspended account is restored successfully when clicking "Restore" link in "Actions" module
	 */
	@Test
	public void TC090DUI_09(){
		userControl.addLog("ID : TC090DUI_09 : Verify that suspended account is restored successfully when clicking 'Restore' link in 'Actions' module");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a user from users table whose status is "Suspended"
			5. Click "Restore" link in "Actions" module
			VP: 080D User List is displayed
			6. Select above user in users table again
		*/
		/*
		 * Precondition: Create suspended user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Delete user if exist
		userControl.deleteUserByEmail(PARTNER_DTS_EMAIL);
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.partnerUser();
		data.put("email", PARTNER_DTS_EMAIL);
		userControl.addUser(AddUser.getDTSUser(), data);
		// Active user
		MailUtil.waitForNewInboxMessage(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD, messageCount);
		userControl.activeNewUserViaEmail(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL,EMAIL_PASSWORD, NEW_ACTIVE_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select user above
		userControl.selectUserInfoByEmail(data.get("email"));
		// Suspend user
		userControl.click(UserMgmt.SUSPEND);
		userControl.selectConfirmationDialogOption("Suspend");
		/*
		 * *************************************************************
		 */
		// 4. Select a user from users table whose status is "Suspended"
		userControl.click(PageHome.LINK_USERS);
		userControl.selectFilterByName(UsersList.FILTER_USER, "Suspended");
		// Check if PDPP-1191 found
		if(!userControl.checkUserExistByEmail(data.get("email"))){
			userControl.addErrorLog("PDPP-1191: 080 User List: the suspended users are not displayed in suspended list with all companies filtering");
			Assert.assertTrue(false);
		}
		userControl.selectUserInfoByEmail(data.get("email"));
		// 5. Click "Restore" link in "Actions" module
		userControl.click(UserMgmt.RESTORE);
		userControl.selectConfirmationDialogOption("Restore");
		/*
		 * VP: 080D User List is displayed
		 */
		Assert.assertEquals(userControl.existsElement(UsersList.getListElementUser()), true);
		// 6. Select above user in users table again
		userControl.selectUserInfoByEmail(data.get("email"));
		/*
		 * Verify that 090D User Info page is displayed and the status of user is "Status: Active"
		 */
		Assert.assertEquals(userControl.getTextByXpath(UserMgmt.STATUS), "Active");
		// Delete user
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*
	 * Verify that the site privileges table in 090D User Info page is read only.
	 */
	@Test
	public void TC090DUI_10(){
		userControl.addLog("ID : TC090DUI_10 : Verify that the site privileges table in 090D User Info page is read only");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a user from users table
			VP. Verify that 090D User Info page is displayed
			6. Try to edit the user's site privileges table
			The user's site privileges table  and notification are uneditable.
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Users" tab
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a user from users table
		userControl.selectUserInfoByEmail(PARTNER_USER);
		// 5. Verify that 090D User Info page is displayed
		Assert.assertEquals(loginControl.existsElement(UserMgmt.getElementsInfo()), true);
		// 6. Try to edit the user's site privileges table
		/*
		 Verify that the notification table in 090D User Info page is read only.
		
		 */
		Assert.assertTrue(userControl.isAllCheckboxDisabled(UserMgmt.SITE_PRIVILEGES_TABLE));
		Assert.assertTrue(userControl.isAllNotificationCheckboxDisable(UserMgmt.SITE_PRIVILEGES_TABLE));
	}
	
}
