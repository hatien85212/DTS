package com.dts.adminportal.partner.test;

import java.util.ArrayList;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PageLogin;
import com.dts.adminportal.model.PartnerListUser;
import com.dts.adminportal.model.PartnerUserInfo;
import com.dts.adminportal.model.PartnerUserMgmt;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.UserEdit;
import com.dts.adminportal.model.UserInfo;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.model.UsersList;
import com.dts.adminportal.util.ListUtil;

public class PartnerUser080List_090_095Info extends BasePage{
	
	String email = "Email"+ RandomStringUtils.randomNumeric(10)+"@infonam.com";
	
	@Override
	protected void initData() {
	}	

	/*
	 * Verify that the partner's user page displays properly.
	 */
	@Test
	public void TC080PUL_03(){
		userControl.addLog("ID : TC080PUL_03 : Verify that the partner's user page displays properly.");
		/*
		 	Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			VP: Verify that the 080P Users page is displayed which including: Account filter dropdown, Account users table, Actions module, a pagination below Account users table and total of users in users table correctly.
			VP: Verify that the users table is displayed which including "First Name", "Last Name", "Title", "Phone" and "Email" columns.
			VP: Verify that the users table is displayed maximum 50 user accounts per page.
			VP: The Account filter dropdown displays three items "All Accounts", "Active" and "Invited".
		 */
		
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Click "Users" tab
		userControl.click(PageHome.LINK_PARTNER_USER);
		/* Verify that the 080P Users page is displayed which including: 
		 * Account filter dropdown, 
		 * Account users table, 
		 * Actions module, 
		 * a pagination below Account users table and total of users in users table correctly.
		 */
		Assert.assertEquals(userControl.existsElement(UsersList.getListElementPartnerUser()), true);
		//VP: Verify that the users table is displayed which including "First Name", "Last Name", "Title", "Phone" and "Email" columns.
		ArrayList<String> list =  userControl.getAllColumnHeader(UsersList.USER_TABLE);
		Assert.assertTrue(ListUtil.containsAll(list, UsersList.theadsPartner));
		//VP: Verify that the users table is displayed maximum 50 user accounts per page.
		Assert.assertTrue(userControl.checkAmountOfDisplayedItemOnTable(UsersList.TABLE_INFO, 50));
		//VP: The Account filter dropdown displays three items "All Accounts", "Active" and "Invited".
		ArrayList<String> filters = userControl.getAllComboboxOption(PartnerListUser.FILTER);
		Assert.assertTrue(ListUtil.containsAll(filters, PartnerListUser.filters));
	}
	
	
	/*
	 * Verify that the users table displays only activated users when the Account filter dropdown is filtered for "Active".
	 */
	@Test
	public void TC080PUL_07(){
		userControl.addLog("ID : TC080PUL_07 : Verify that the users table displays only activated users when the Account filter dropdown is filtered for 'Active'.");
		/*
		 Pre-condition: partner user has "Add and Manage Users" rights. 
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click "Users" tab
		4. Set the Account filter dropdown to "Active"
		VP: The users table shows up only activated users and all their information are correctly.
		5. Set the Account filter dropdown to "Invited"
		VP: The users table shows up only invited users and all their information are correctly.
		6. Set the Account filter dropdown to "All Accounts"
		 */
		
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Click "Users" tab 
		userControl.click(PageHome.LINK_PARTNER_USER);
		// 4. Set the Account filter dropdown to "Active"
		//String user_status = "Active";
		productControl.selectReportsFilterByText(UsersList.FILTER_USER, UsersList.company_Filter.Active.getName());
		//home.selectFilterByName(UsersList.FILTER_USER, user_status);
		/*
		 * The users table shows up only activated users and all their information are correctly
		 */
		Assert.assertTrue(userControl.checkAllUserStatus(UsersList.company_Filter.Active.getName()));
		//5. Set the Account filter dropdown to "Invited"
		productControl.selectReportsFilterByText(UsersList.FILTER_USER, UsersList.company_Filter.Invited.getName());
		/*
		 * The users table shows up only activated users and all their information are correctly
		 */
		Assert.assertTrue(userControl.checkAllUserStatus(UsersList.company_Filter.Invited.getName()));
		
		//6. Set the Account filter dropdown to "All Accounts"
		productControl.selectReportsFilterByText(UsersList.FILTER_USER, UsersList.company_Filter.All_Accounts.getName());
		//home.selectFilterByName(UsersList.FILTER_USER, user_status);
		/*
		 * The users table shows up only activated users and all their information are correctly
		 */
		Assert.assertTrue(userControl.checkAllUserStatus(UsersList.company_Filter.All_Accounts.getName()));
		
	}
	
	/*
	 Verify that the 090P User Info page displays  user's information properly
	 */
	@Test
	public void TC090PUI_02(){
		userControl.addLog("ID : TC090PUI_02 : Verify that the 090P User Info page displays  user's information properly");
		
		/*
		Pre-condition: User has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a user from users table
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Click "Users" tab
		userControl.click(PageHome.LINK_PARTNER_USER);
		// get user info
		ArrayList<PartnerUserInfo> PartnerUser = userControl.getPartnerUsers(PartnerListUser.TBODY);
		// 4. Select a user from users table
		Assert.assertEquals(true, userControl.selectRowAt(PartnerListUser.TBODY, 0,PartnerUserMgmt.EDIT));
		// verify user info
		Assert.assertEquals(true, userControl.checkPartnerUserInfo(PartnerUser.get(0), PartnerUserMgmt.getElementsInfo()));
	}
	
	/*
	 Verify that the site privileges info displayed correctly according to user's assigned site privileges
	 */
	@Test
	public void TC090PUI_03(){
		userControl.addLog("ID : TC090PUI_03 : Verify that the site privileges info displayed correctly according to user's assigned site privileges");
		
		/*
			Pre-condition: User has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user which has full privileges successfully.
			3. Navigate to "Users" page
			4. Select a user from users table
			VP: Verify that the 090D User Info page is displayed
			5. Click "Edit" link
			6. Select some options in "Site Privileges" section
			7. Log out DTS portal
			8. Log into DTS portal as a partner admin who have the same company with above user
			9. Click "Users" tab
			10. Select above user from users table
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		//3.Click "Users" tab
		userControl.click(PageHome.LINK_PARTNER_USER);		
		// 4. Select a user from users table
		userControl.selectUserInfoByEmail(PARTNER_USER);
		// 5. Click "Edit" link
		userControl.click(UserInfo.EDIT);
		// 6. Select some options in "Site Privileges" section
		userControl.disableAllPrivileges(AddUser.PRIVILEGES_TABLE);
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Add_and_manage_products.getName());
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Add_and_manage_users.getName());
		// Save
		userControl.click(AddUser.SAVE);;
		// 7. Log out DTS portal
		userControl.logout();
		//8. Log into DTS portal as a partner admin who have the same company with above user
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		//9.Click "Users" tab
		userControl.click(PageHome.LINK_PARTNER_USER);	
		// 10. Select above user from users table
		userControl.selectUserInfoByEmail(PARTNER_USER);
		/*
		 * Verify that the site privileges info displayed correctly according to user's assigned site privileges
		 */
		//get user's privileges
		ArrayList<String> userprivilege= userControl.getPrivileges(UserMgmt.SITE_PRIVILEGES_TABLE);
		Assert.assertTrue(userprivilege.contains(Privileges.privileges.Add_and_manage_products.getName()));
		Assert.assertTrue(userprivilege.contains(Privileges.privileges.Add_and_manage_users.getName()));
		
		//Restore user's privileges
		userControl.logout();
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		userControl.click(PageHome.LINK_PARTNER_USER);
		userControl.selectUserInfoByEmail(PARTNER_USER);
		userControl.click(UserInfo.EDIT);
		userControl.enableAllPrivileges();
		userControl.click(AddUser.SAVE);
	}
	
	/*
	 Verify that user is not deleted when clicking cancel on delete confirmation dialog
	 */
	
	@Test
	public void TC090PUI_05(){
		userControl.addLog("ID : TC090PUI_05 : Verify that user is not deleted when clicking cancel on delete confirmation dialog");
		
		/*
		Pre-condition: partner user has "Add and Manage Users" rights.

		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click "Users" tab
		4. Select a user from users table
		VP: In 090 User Info page, the "Actions" module shows up  "Edit",  "Delete Account", "Reset Password" links.
		5. Click "Delete Account" link in "Actions" module
		VP: Verify that there is a delete confirmation dialog is displayed with "Delete" and "Cancel" button
		6. Click "Cancel" button on delete confirmation dialog
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Click "Users" tab
		userControl.click(PageHome.LINK_PARTNER_USER);
		// get user info
		ArrayList<PartnerUserInfo> users = userControl.getPartnerUsers(PartnerListUser.TBODY);
		// 4. Select a user from users table
		Assert.assertEquals(true, userControl.selectRowAt(PartnerListUser.TBODY, 0, PartnerUserMgmt.EDIT));
		// verify user info 
		Assert.assertEquals(true, userControl.checkPartnerUserInfo(users.get(0), PartnerUserMgmt.getElementsInfo()));
		// check Actions
		Assert.assertEquals(true, userControl.existsElement(PartnerUserMgmt.getActions()));

		// 5. Click "Delete Account" link in "Actions" module
		userControl.click(PartnerUserMgmt.DELETE_ACCOUNT);
		/*
		 Verify that there is a delete confirmation dialog with 'Delete' and 'Cancel' button displayed when deleting a user
		 */
		String confirm[] = userControl.getConfirm();
		System.out.println("confirm : "+ confirm[0] + " : " + confirm[1]);
		boolean isCorrectConfirm = confirm[1].equals("Delete") && confirm[0].equals("Cancel");
		Assert.assertTrue(isCorrectConfirm);
		
		// 6. Click "Cancel" button in delete confirmation dialog
		userControl.switchWindowClickOption("Cancel");
		/*
		 * The delete confirmation dialog disappeared and the portal stays at 090P User Info page.
		 */
		// The delete confirmation dialog disappeared
//		Assert.assertTrue(confirm[0] == null);
		// the portal stays at 090P User Info page.
		Assert.assertTrue(userControl.isElementPresent(PartnerUserMgmt.FIRSTNAME));
	}
	
	
	
	/*
	Verify that the site privileges and notification table in 090P User Info page is read only.
	 */
	@Test
	public void TC090PUI_11(){
		

		userControl.addLog("ID : TC090PUI_11 : Verify that the site privileges and notification table in 090P User Info page is read only.");
		

		/*
		Pre-condition: partner user has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Select a user from users table
			5. Verify that 090P User Info page is displayed
			6. Try to edit the user's site privileges table
			VP: The user's site privileges table is uneditable.
			VP: The user's notification table is uneditable
		*/
		
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Click "Users" tab
		userControl.click(PageHome.LINK_PARTNER_USER);
		ArrayList<PartnerUserInfo> users = userControl.getPartnerUsers(PartnerListUser.TBODY);
		// 4. Select a user from users table
		Assert.assertEquals(true, userControl.selectRowAt(PartnerListUser.TBODY, 0, PartnerUserMgmt.EDIT));
		// 5. Verify that 090P User Info page is displayed 
		Assert.assertEquals(true, userControl.checkPartnerUserInfo(users.get(0), PartnerUserMgmt.getElementsInfo()));
		/*
		 The user's site privileges table is uneditable.
		 The user's notification table is un-editable
		 */
		Assert.assertFalse(productControl.canEdit(PartnerUserMgmt.SITE_PRIVILEGES));	
		Assert.assertTrue(userControl.isAllNotificationCheckboxDisable(UserMgmt.SITE_PRIVILEGES_TABLE));
	}
	

	/*
	 * Verify that the site, brand privileges and notification info displayed correctly according to user's assigned site privileges
	 */
	@Test
	public void TC095PUI_02(){
		userControl.addLog("ID : TC095PUI_02 : Verify that the site, brand privileges and notification info displayed correctly according to user's assigned site privileges");
		
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a user from users table
			VP: Verify that the 090D User Info page is displayed
			5. Click "Edit" link
			6. Set site, brand privilege and notification for user
			7. Log out DTS portal
			8. Log into DTS portal as a partner admin who have the same company with above user
			9. Click user's email label on top right corner
			VP: Verify that the user account popup is displayed
			10. Click "User Account" item
			VP: The 095 User Info page displays:
				User's first name, last name, title, email, phone, status, site privileges,brands, notification and action module
			VP: The user's site, brand privilege and notification display correctly.
			VP: The user's site privileges table is uneditable.
			VP: The "Actions" module shows up "Change Password" link
		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a user from users table
		userControl.dtsSelectUserByEmail(PARTNER_USER);
		/*
		 * VP: Verify that the 090D User Info page is displayed
		 */
		Assert.assertEquals(userControl.existsElement(PartnerUserMgmt.getElementsInfo()), true);
		// 5. Click "Edit" link
		userControl.click(UserMgmt.EDIT);
		// 6. Set site, brand privilege and notification for user
		userControl.disableAllPrivileges(AddUser.PRIVILEGES_TABLE);
		userControl.enablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.privileges.Add_and_manage_products.getName());
		userControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		userControl.logout();
		// 8. Log into DTS portal as a partner admin who have the same company with above user
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 9. Click user's email label on top right corner
		userControl.click(PageHome.lbLogout);
		// 10. Click "User Account" item
		userControl.click(PageHome.loginAccount);
		
		//VP: The 095 User Info page displays:
		//User's first name, last name, title, email, phone, status, site privileges,brands, notification and action module
		Assert.assertEquals(true, userControl.existsElement(PartnerUserMgmt.getElementsInfo()));
		Assert.assertEquals(true, userControl.existsElement(PartnerUserMgmt.getUserOwnActions()));
		
		//VP: The user's site, brand privilege and notification display correctly.
		String PrivilegeTable = userControl.getTextByXpath(PartnerUserMgmt.SITE_PRIVILEGES_TABLE);
		Assert.assertTrue(PrivilegeTable.contains(Privileges.privileges.Add_and_manage_products.getName()) && PrivilegeTable.contains("All brands"));
		Assert.assertTrue(userControl.isNotificationCheckboxSelected(PartnerUserMgmt.SITE_PRIVILEGES_TABLE, Privileges.privileges.Add_and_manage_products.getName()));
		
		//VP: The user's site privileges table is uneditable.
		Assert.assertTrue(userControl.isAllNotificationCheckboxDisable(PartnerUserMgmt.SITE_PRIVILEGES_TABLE));
		
		//VP: The "Actions" module shows up "Change Password" link
		Assert.assertEquals(true, userControl.existsElement(PartnerUserMgmt.getUserOwnActions()));
	}
	
	
	/*
	 * Verify user is able to log into partner portal with new email address
	 */
	@Test
	public void TC095PUI_06(){
		userControl.addLog("ID : TC095PUI_06 : Verify user is able to log into partner portal with new email address.");
		
		/*
			Pre-condition: The "Add and Manage Users" privilege is enabled.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			VP: Verify that the User page is displayed
			3. Click user's email label on top right corner
			4. Click "User Account" item
			5. Click "Change Password" link
			VP: Verify that the email address field is enabled
			6. Change user own email address to another
			7. Click "Save" link
			VP: Verify that user is kicked out the portal and the portal redirects to login page
			8. Log into DTS portal with old email address
			VP: User is unable to log into DTS portal with old email address
			9. Log into DTS portal with new email address
			VP:User is able to log into DTS portal with new email address
		 */
		// 2. Log into DTS portal as a partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3.Click user's email label on top right corner
		userControl.click(PageHome.lbLogout);
		// 4. Click "User Account" item
		userControl.click(PageHome.loginAccount);
		// 5. Click "Edit" link
		userControl.click(UserMgmt.EDIT);
		/*
		 * VP: Verify that the email address field is enabled
		 */
		Assert.assertTrue(userControl.isElementEditable(AddUser.EMAIL));
		
		// 6. Change user own email address to another
		String newEmail = AddUser.New_email_element.NewEmail.getName() + RandomStringUtils.randomNumeric(5) + AddUser.New_email_element.Mailinator.getName();
		userControl.editData(AddUser.EMAIL, newEmail);
		// 7. Click "Save" link
		userControl.click(UserEdit.SAVE);
		// VP: Verify that user is kicked out the portal after the change is saved
		Assert.assertTrue(userControl.isElementPresent(PageLogin.USERNAME));
		Assert.assertFalse(userControl.isElementPresent(AddUser.EMAIL));
		// 8. Log into DTS portal with old email address
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// User is unable to log into DTS portal with old email address
		Assert.assertEquals(userControl.getTextByXpath(PageLogin.ERROR_MESSAGE), PageLogin.errMessage.Invalid_email_or_password.getName());
		Assert.assertFalse(userControl.isElementPresent(PageHome.LINK_PARTNER_HOME));
		
		//9. Log into DTS portal with new email address
		loginControl.login(newEmail, SUPER_PARTNER_PASSWORD);
		//VP: User is able to log into DTS portal with new email address
		Assert.assertTrue(userControl.isElementPresent(PageHome.LINK_PARTNER_HOME));
		
		
		
		//Teardown: Change user account email address back to previous;
		userControl.logout();
		loginControl.login(newEmail, SUPER_PARTNER_PASSWORD);
		userControl.click(PageHome.lbLogout);
		userControl.click(PageHome.loginAccount);
		userControl.click(UserInfo.EDIT);
		userControl.editData(AddUser.EMAIL, SUPER_PARTNER_USER);
		userControl.click(AddUser.SAVE);
	}
}
