package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.DTSUtil;
import com.dts.adminportal.util.MailUtil;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.PartnerUserMgmt;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UserInfo;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.UsersList;
import dts.com.adminportal.model.Xpath;

public class DTSUser090DUserInfo extends CreatePage{
	private HomeController home;
	
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}
	
	@BeforeMethod
	public void loginBeforeTest() {
		home.login(superUsername, superUserpassword);
	}

	/*
	 * Verify that the 090D User Info page is displayed when selecting user from users table
	 */
	@Test
	public void TC090DUI_01(){
		result.addLog("ID : TC090DUI_01 : Verify that the 090D User Info page is displayed when selecting user from users table");
		/*
		Pre-condition: User has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a user from users table
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table
		home.dtsSelectUserByEmail(partneruser);
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(),"Pass");
	}
	
	/*
	 Verify that the 090D User Info page displays  user's information properly
	 */
	@Test
	public void TC090DUI_02(){
		result.addLog("ID : TC090DUI_02 : Verify that the 090D User Info page displays user's information properly");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a user from users table
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table
		ArrayList<String> currentUserInfo = home.selectUserByEmail(partneruser);
		/*
		 * The 090D User Info page displays: 
		 * User's first name, last name, title, email, phone, status, 
		 * site privileges, notification and action module
		 */
		ArrayList<String> userInfo = home.getTextByXpath(UserMgmt.getUserInfo());
		Assert.assertTrue(DTSUtil.containsAll(userInfo, currentUserInfo));
	}
	
	/*
	 * Verify that the site privileges info displayed correctly according to user's assigned site privileges
	 */
	@Test
	public void TC090DUI_03(){
		result.addLog("ID : TC090DUI_03 : Verify that the site privileges info displayed correctly according to user's assigned site privileges");
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
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table
		home.dtsSelectUserByEmail(partneruser);
		// 5. Click "Edit" link
		home.click(UserInfo.EDIT);
		// 6. Select some options in "Site Privileges" section
		home.disableAllPrivilege(AddUser.PRIVILEGES_TABLE);
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as a above admin who have the same company with above user
		home.login(superpartneruser, superpartnerpassword);
		// 9. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// 10. Select above user from users table
		home.selectUserByEmail(partneruser);
		/*
		 * The 090D User Info page displays and the user's site privilege also display correctly of what are set
		 */
		Assert.assertEquals(home.existsElement(PartnerUserMgmt.getElementsInfo()).getResult(), "Pass");
		ArrayList<String> privileges = home.getPrivileges(PartnerUserMgmt.SITE_PRIVILEGES_TABLE);
		Assert.assertTrue(privileges.contains(Privileges.Add_and_manage_user));
		// Assign full privileges for above user again
		home.click(UserMgmt.EDIT);
		home.enableAllPrivilege();
		home.click(AddUser.SAVE);
	}
	
	/*
	 * Verify that the "Actions" module shows up "Edit", "Delete", "Reset Password", and "Suspend" links
	 */
	@Test
	public void TC090DUI_04(){
		result.addLog("ID : TC090DUI_04 : Verify that the 'Actions' module shows up 'Edit', 'Delete', 'Reset Password' and 'Suspend' links");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a user from users table
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		//4. Select a user from users
		home.selectUserByEmail(partneruser);
		/*
		 * Verify that In 090 User Info page, the "Actions" module shows up  "Edit", "Delete", "Reset Password"and "Suspend" links
		 */
		ArrayList<String> actions = home.getTextByXpath(UserMgmt.getActions());
		Assert.assertTrue(DTSUtil.containsAll(home.getAllLinkA(UserMgmt.ACTION_MODULE),actions));
		Assert.assertFalse(home.isElementPresent(UserMgmt.RESTORE));
	}
	
	/*
	 * Verify that there is a delete confirmation dialog with 'Delete' and 'Cancel' button displayed when deleting a user
	 */
	@Test
	public void TC090DUI_05(){
		result.addLog("ID : TC090DUI_05 : Verify that there is a delete confirmation dialog with 'Delete' and 'Cancel' button displayed when deleting a user");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a user from users table
			5. Click "Delete Account" link in "Actions" module 
		 */
		/*
		 * Precondition: Create a new user
		 */
		// Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.dtsUser();
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * *******************************************************************
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table
		home.dtsSelectUserByEmail(data.get("email"));
		// 5. Click "Delete Account" link in "Actions" module 
		home.click(UserMgmt.DELETE_ACCOUNT);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		/*
		 * Verify that there is a delete confirmation dialog is displayed with "Delete" and "Cancel" button
		 */
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_CANCEL), "Cancel");
		// Delete user
		home.selectConfirmationDialogOption("Delete");
	}
	
	/*
	 * Verify that there is a suspend confirmation dialog with "Suspend" and "Cancel" button displayed when deleting a user.
	 */
	@Test
	public void TC090DUI_06(){
		result.addLog("ID : TC090DUI_06 : Verify that there is a suspend confirmation dialog with 'Suspend' and 'Cancel' button displayed when deleting a user.");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a user from users table
			5. Click "Suspend" link in "Actions" module
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click "Suspend" link in "Actions" module
		home.click(UserMgmt.SUSPEND);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		/*
		 * Verify that there is a delete confirmation dialog is displayed with "Delete" and "Cancel" button
		 */
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_DANGER), "Suspend");
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_CANCEL),	"Cancel");
	}
	
	/*
	 * Verify that user account is not deleted when clicking "Cancel" button on delete confirmation dialog.
	 */
	@Test
	public void TC090DUI_07(){
		result.addLog("ID : TC090DUI_07 : Verify that user account is not deleted when clicking 'Cancel' button on delete confirmation dialog.");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a user from users table
			5. Click "Delete Account" link in "Actions" module
			6. Click "Cancel" button in delete confirmation dialog
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click "Delete Account" link in "Actions" module
		home.click(UserMgmt.DELETE_ACCOUNT);
		// 6. Click "Cancel" button in delete confirmation dialog
		home.selectConfirmationDialogOption("Cancel");
		/*
		 * The delete confirmation dialog disappeared and the portal stays at 090D User Info page.
		 */
		Assert.assertFalse(home.isElementPresent(Xpath.BTN_CONFIRMATION_DANGER));
		Assert.assertFalse(home.isElementPresent(Xpath.BTN_CONFIRMATION_CANCEL));
	}
	
	/*
	 * Verify that user account is not suspended when clicking "Cancel" button on suspend confirmation dialog.
	 */
	@Test
	public void TC090DUI_08(){
		result.addLog("ID : TC090DUI_08 : Verify that user account is not suspended when clicking 'Cancel' button on suspend confirmation dialog..");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a user from users table whose status is "Active"
			5. Click "Suspend" link in "Actions" module
			6. Click "Cancel" button in suspend confirmation dialog
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table whose status is "Active"
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click "Suspend" link in "Actions" module
		home.click(UserMgmt.SUSPEND);
		// 6. Click "Cancel" button in suspend confirmation dialog.
		home.selectConfirmationDialogOption("Cancel");
		/*
		 * The suspend confirmation dialog disappeared, portal stays at 090D User Info page and the status of user is "Active"
		 */
		Assert.assertFalse(home.isElementPresent(Xpath.BTN_CONFIRMATION_DANGER));
		Assert.assertFalse(home.isElementPresent(Xpath.BTN_CONFIRMATION_CANCEL));
	}
	
	/*
	 * Verify that suspended account is restored successfully when clicking "Restore" link in "Actions" module
	 */
	@Test
	public void TC090DUI_09(){
		result.addLog("ID : TC090DUI_09 : Verify that suspended account is restored successfully when clicking 'Restore' link in 'Actions' module");
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
		// Active user
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email, email_password, messageCount);
		home.activeNewUserViaEmail(yahoo_imap_server, pdts_email,email_password, new_active_user_password);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select user above
		home.selectUserByEmail(data.get("email"));
		// Suspend user
		home.click(UserMgmt.SUSPEND);
		home.selectConfirmationDialogOption("Suspend");
		/*
		 * *************************************************************
		 */
		// 4. Select a user from users table whose status is "Suspended"
		home.selectFilterByName(UsersList.FILTER_USER, "Suspended");
		// Check if PDPP-1191 found
		if(!home.checkUserExistByEmail(data.get("email"))){
			result.addErrorLog("PDPP-1191: 080 User List: the suspended users are not displayed in suspended list with all companies filtering");
			Assert.assertTrue(false);
		}
		home.selectUserByEmail(data.get("email"));
		// 5. Click "Restore" link in "Actions" module
		home.click(UserMgmt.RESTORE);
		home.selectConfirmationDialogOption("Restore");
		/*
		 * VP: 080D User List is displayed
		 */
		Assert.assertEquals(home.existsElement(UsersList.getListElementUser()).getResult(), "Pass");
		// 6. Select above user in users table again
		home.selectUserByEmail(data.get("email"));
		/*
		 * Verify that 090D User Info page is displayed and the status of user is "Status: Active"
		 */
		Assert.assertEquals(home.getTextByXpath(UserMgmt.STATUS), "Active");
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*
	 * Verify that the site privileges table in 090D User Info page is read only
	 */
	@Test
	public void TC090DUI_10(){
		result.addLog("ID : TC090DUI_10 : Verify that the site privileges table in 090D User Info page is read only");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a user from users table
			5. Verify that 090D User Info page is displayed
			6. Try to edit the user's site privileges table
		*/
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table
		home.selectUserByEmail(partneruser);
		// 5. Verify that 090D User Info page is displayed
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
		// 6. Try to edit the user's site privileges table
		/*
		 Verify that the notification table in 090D User Info page is read only.
		 */
		Boolean isAllCheckboxDisabled = home.isAllCheckboxDisabled(UserMgmt.SITE_PRIVILEGES_TABLE);
		Assert.assertTrue(isAllCheckboxDisabled);
	}
	
	/*
	 * Verify that the notification table in 090D User Info page is read only
	 */
	@Test
	public void TC090DUI_11(){
		result.addLog("ID : TC090DUI_10 : Verify that the notification table in 090D User Info page is read only");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a user from users table
			5. Verify that 090D User Info page is displayed
			6. Try to edit the user's notification table
		*/
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table
		home.selectUserByEmail(partneruser);
		// 5. Verify that 090D User Info page is displayed
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
		// 6. Try to edit the user's site privileges table
		/*
		 * Verify that the notification table in 090D User Info page is read only.
		 */
		Assert.assertTrue(home.isAllNotificationCheckboxDisable(UserMgmt.SITE_PRIVILEGES_TABLE));
	}
	
	/*
	 * Verify that the DTS user who has "Add and manage users" rights could delete a user successfully
	 */
	@Test
	public void TC090DUI_12(){
		result.addLog("ID : TC090DUI_12 : Verify that the DTS user who has 'Add and manage users' rights could delete a user successfully.");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a user from users table
			5. Click "Delete Account" link in "Actions" module
			6. Click "Delete" button in delete confirmation dialog
		 */
		/*
		 * Precondition: Create new user
		 */
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.partnerUser();
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * *******************************************************
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table
		home.selectUserByEmail(data.get("email"));
		// 5. Click "Delete Account" link in "Actions" module
		// 6. Click "Delete" button in delete confirmation dialog
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
		/*
		 * 080P User List is displayed and the deleted user does not exist in users table.
		 */
		Assert.assertFalse(home.checkUserExistByEmail(data.get("email")));
	}
	
	/*
	 * Verify that the DTS user who has "Add and manage users" rights could suspend a user successfully
	 */
	@Test
	public void TC090DUI_13(){
		result.addLog("ID : TC090DUI_13 : Verify that the DTS user who has 'Add and manage users' rights could suspend a user successfully");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a user from users table whose status is "Active"
			5. Click "Suspend" link in "Actions" module
			6. Click "Suspend" button in suspend confirmation dialog.
			VP: 080P User List is displayed
			7. Select above user in users table again
		 */
		/*
		 * Precondition: Create an active user
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
		// Active user
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email, email_password, messageCount);
		home.activeNewUserViaEmail(yahoo_imap_server, pdts_email,email_password, new_active_user_password);
		/*
		 * ****************************************************
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table whose status is "Active"
		home.dtsSelectUserByEmail(data.get("email"));
		// 5. Click "Suspend" link in "Actions" module
		home.click(UserMgmt.SUSPEND);
		// 6. Click "Suspend" button in suspend confirmation dialog.
		home.selectConfirmationDialogOption("Suspend");
		// VP: 080P User List is displayed
		Assert.assertEquals(home.existsElement(UsersList.getListElementUser()).getResult(), "Pass");
		// 7. Select above user in users table again
		home.selectFilterByName(UsersList.FILTER_USER, "Suspended");
		// Check if PDPP-1191 found
		if(!home.checkUserExistByEmail(data.get("email"))){
			result.addErrorLog("PDPP-1191: 080 User List: the suspended users are not displayed in suspended list with all companies filtering");
			Assert.assertTrue(false);
		}
		home.selectUserByEmail(data.get("email"));
		/*
		 * 090D User Info page is displayed and the status of user is "Status: Suspended"
		 */
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(UserMgmt.STATUS), "Suspended");
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
}
