package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.model.UsersList;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.MailUtil;
import com.dts.adminportal.util.TestData;

public class DTSUser080DUserList extends BasePage {

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
	}
	
	/*
	 * Verify that the users table in "Users" page shows up "First Name", "Last Name", "Company", "Title", "Phone" and "Email" columns.
	 */
	@Test
	public void TC080DUL_04(){
		userControl.addLog("ID : TC080DUL_04 : Verify that the users table in 'Users' page shows up 'First Name', 'Last Name', 'Company', 'Title', 'Phone' and 'Email' columns");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			"VP: Verify that the users table is displayed which including ""First Name"", ""Last Name"", ""Title"",""Company"", ""Phone"" and ""Email"" columns.
			VP: Verify that the users table is displayed maximum 100 user accounts per page.
			VP: The Account filter dropdown displays three items ""All Accounts"", ""Active"" ""Active"",  ""Invited"" and ""Suspended"".
			VP: The ""Actions"" module shows up ""Add New User"" link
			VP: The default item of company filter is ""All Companies""."
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		/*
		 * Verify that the users table is displayed which including "First Name", "Last Name", "Title","Company", "Phone" and "Email" columns
		 */
		ArrayList<String> theads =  userControl.getAllColumnHeader(UsersList.USER_TABLE);
		Assert.assertTrue(ListUtil.containsAll(theads, UsersList.theads));
		
		Assert.assertTrue(userControl.checkAmountOfDisplayedItemOnTable(UsersList.TABLE_INFO, 100));
		
		userControl.click(UsersList.FILTER_USER);
		/*
		 * The Account filter dropdown displays three items "All Accounts", "Active" "Active",  "Invited" and "Suspended"
		 */
		ArrayList<String> options = userControl.getAllComboboxOption(UsersList.FILTER_USER);
		Assert.assertTrue(ListUtil.containsAll(options, UsersList.company_filter));
		
		//Verify that the "Actions" module shows up "Add New User" link
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		
		/*
		Verify that the default item of company filter is "All Companies".
		*/
		Select comboBox = new Select(driver.findElement(By.id("company-list")));
		String selectedComboValue = comboBox.getFirstSelectedOption().getText();
		Assert.assertEquals("All companies", selectedComboValue);
	}
	
	/*
	 * Verify that the users table displays only activated users when the Account filter dropdown is filtered for "Active"
	 */
	@Test
	public void TC080DUL_07(){
		userControl.addLog("ID : TC080DUL_07 : Verify that the users table displays only activated users when the Account filter dropdown is filtered for 'Active'");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Set the Account filter dropdown to "Active"
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Users" tab 
		userControl.click(PageHome.LINK_USERS);
		// 4. Set the Account filter dropdown to "Active"
		String user_status = "Active";
		userControl.selectFilterByName(UsersList.FILTER_USER, user_status);
		//Assert.assertTrue(userControl.checkUserExistByEmail("anccncn@cbs.com"));
		/*
		 * The users table shows up only activated users and all their information are correctly
		 */
		Assert.assertTrue(userControl.checkAllUserStatus(user_status));
	}
	
	/*
	 * Verify that the users table displays only invited users when the Account filter dropdown is filtered for "Invited"
	 */
	@Test
	public void TC080DUL_08(){
		userControl.addLog("ID : TC080DUL_08 : Verify that the users table displays only invited users when the Account filter dropdown is filtered for 'Invited'");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Set the Account filter dropdown to "Invited"
		 */
		/*
		 * PreCondition: Create new user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Click Add user link
		userControl.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.dtsUser();
		userControl.addUser(AddUser.getDTSUser(), data);
		/*
		 * *************************************************************
		 */
		// 3. Click "Users" tab 
		userControl.click(PageHome.LINK_USERS);
		// 4. Set the Account filter dropdown to "Active"
		String user_status = "Invited";
		userControl.selectFilterByName(UsersList.FILTER_USER, user_status);
		/*
		 * The users table shows up only activated users and all their information are correctly
		 */
		Assert.assertTrue(userControl.checkAllUserStatus(user_status));
		// Delete user
		userControl.selectUserInfoByEmail(data.get("email"));
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
		
	/*
	 * Verify that the users table displays all activated, suspended and invited users when the Account filter dropdown is filtered for "All Accounts"
	 */
	@Test
	public void TC080DUL_09(){
		userControl.addLog("ID : TC080DUL_09 : Verify that the users table displays all activated, suspended and invited users when the Account filter dropdown is filtered for 'All Accounts'");
		/*
			"Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Set the Account filter dropdown to "All Accounts"
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Users" tab
		userControl.click(PageHome.LINK_USERS);
		// 4. Set the Account filter dropdown to "Active"
		String user_status = "All Accounts";
		userControl.selectFilterByName(UsersList.FILTER_USER, user_status);
		/*
		 * The users table shows up all invited, suspended and activated users and their information are correctly
		 */
		Assert.assertTrue(userControl.checkAllUserStatus(user_status));
	}
	
	/*
	 * Verify that the users table displays only suspended users when the Account filter dropdown is filtered for "Suspended".
	 */
	@Test
	public void TC080DUL_10(){
		userControl.addLog("ID : TC080DUL_10 : Verify that the users table displays only suspended users when the Account filter dropdown is filtered for 'Suspended'.");
		/*
		 	Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Set the Account filter dropdown to "Suspended"
		 */
		/*
		 * PreCondition: Create a suspended user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(YAHOO_IMAP_SERVER, DTS_EMAIL, EMAIL_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Delete user if exist
		userControl.deleteUserByEmail(DTS_EMAIL);
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.dtsUser();
		data.put("email", DTS_EMAIL);
		userControl.addUser(AddUser.getDTSUser(), data);
		// Active user
		MailUtil.waitForNewInboxMessage(YAHOO_IMAP_SERVER, DTS_EMAIL, EMAIL_PASSWORD, messageCount);
		userControl.activeNewUserViaEmail(YAHOO_IMAP_SERVER, DTS_EMAIL,EMAIL_PASSWORD, NEW_ACTIVE_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select user above
		userControl.dtsSelectUserByEmail(data.get("email"));
		// Suspend user
		userControl.click(UserMgmt.SUSPEND);
		userControl.selectConfirmationDialogOption("Suspend");
		/*
		 * ****************************************************************
		 */
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Set the Account filter dropdown to "Suspended"
		String user_status = "Suspended";
		userControl.selectFilterByName(UsersList.FILTER_USER, user_status);
		// Check if PDPP-1191 found
		if(!userControl.checkUserExistByEmail(data.get("email"))){
			userControl.addErrorLog("PDPP-1191 - 080 User List: the suspended users are not displayed in suspended list with all companies filtering");
			Assert.assertTrue(false);
		}
		/*
		 * Verify that The users table shows up only suspended users and all their information are correctly
		 */
		Assert.assertTrue(userControl.checkAllUserStatus(user_status));
	}
	
	/*
	Verify that the users table only displays users of filtered company
	*/
	@Test
	public void TC080DUL_13(){
		userControl.addLog("ID : TC080DUL_13 : Verify that the 'Actions' module shows up 'Add New User' link");
		/*
		 Pre-condition: User has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a company from Company filter dropdown
		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a company from Company filter dropdown
		
		//Verify that the users table only displays users of filtered company
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		//COmment
	}
	
}
