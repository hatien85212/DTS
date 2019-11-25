package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
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
import dts.com.adminportal.model.PartnerHomePage;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.UsersList;
import dts.com.adminportal.model.Xpath;

public class DTSUser080DUserList extends CreatePage {
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
	 * Verify that the "Users" tab will be hidden when DTS user does not have "Add and manage users" rights.
	 */
	@Test
	public void TC080DUL_01(){
		result.addLog("ID : TC080DUL_01 : Verify that the 'Users' tab will be hidden when DTS user does not have 'Add and manage users' rights.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a user from users table
			VP: Verify that the 090D User Info page is displayed
			5. Click "Edit" link
			6. Unselect "Add and manage users" option in "Site Privileges" section
			7. Log out DTS portal
			8. Log into DTS portal under above user's account
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table
		home.dtsSelectUserByEmail(dtsUser);
		// VP: Verify that the 090D User Info page is displayed
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Unselect "Add and manage users" option in "Site Privileges" section
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal under above user's account
		home.login(dtsUser, dtsPass);
		/*
		 * The "Users" tab is not displayed on global navigation bar.
		 */
		Assert.assertFalse(home.isElementPresent(Xpath.LINK_USERS));
	}
	
	/*
	 * Verify that the "Users" tab is showed up when DTS user has "Add and manage users" rights.
	 */
	@Test
	public void TC080DUL_02(){
		result.addLog("ID : TC080DUL_02 : Verify that the 'Users' tab is showed up when DTS user has 'Add and manage users' rights..");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a user from users table
			VP: Verify that the 090D User Info page is displayed
			5. Click "Edit" link
			6. Select "Add and manage users" option in "Site Privileges" section
			7. Log out DTS portal
			8. Log into DTS portal under above user's account
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table
		home.dtsSelectUserByEmail(partneruser);
		// VP: Verify that the 090D User Info page is displayed
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Select "Add and manage users" option in "Site Privileges" section
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal under above user's account
		home.login(partneruser, password);
		/*
		 * Verify that The "Users" tab is displayed on global navigation bar
		 */
		Assert.assertTrue(home.isElementPresent(PartnerHomePage.LINK_PARTNER_USER));
	}
	
	/*
	 * Verify that the 080D user list page displays properly.
	 */
	@Test
	public void TC080DUL_03(){
		result.addLog("ID : TC080DUL_03 : Verify that the 080D user list page displays properly.");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
		 */
		//3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		/*
		 * Verify that the 080D Users List  page is displayed which including: Account filter dropdown, Company filter dropdown, Account users table, Actions module, a pagination below Account users table and total of users in users table correctly.
		 */
		Assert.assertEquals(home.existsElement(UsersList.getListElementUser()).getResult(), "Pass");
		
	}
	/*
	 * Verify that the users table in "Users" page shows up "First Name", "Last Name", "Company", "Title", "Phone" and "Email" columns
	 */
	@Test
	public void TC080DUL_04(){
		result.addLog("ID : TC080DUL_04 : Verify that the users table in 'Users' page shows up 'First Name', 'Last Name', 'Company', 'Title', 'Phone' and 'Email' columns");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		/*
		 * Verify that the users table is displayed which including "First Name", "Last Name", "Title","Company", "Phone" and "Email" columns
		 */
		ArrayList<String> theads =  home.getAllColumnHeader(UsersList.USER_TABLE);
		Assert.assertTrue(DTSUtil.containsAll(theads, UsersList.theads));
	}
	
	/*
	 * Verify that the users table in "Users" page shows up maximum 100 user accounts per page.
	 */
	@Test
	public void TC080DUL_05(){
		result.addLog("ID : TC080DUL_06 : Verify that the users table in 'Users' page shows up maximum 100 user accounts per page.");
		/*
		 	Pre-condition: User has "Add and Manage Users" rights. There are at least 100 registered users account.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
		 */
		//3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		/*
		 * Verify that the users table is displayed maximum 100 user accounts per page.
		 */
		Assert.assertTrue(home.checkItemAmountDisplayOnTable(UsersList.TABLE_INFO, 100));
	}
	
	/*
	 * Verify that there are fours items "All Accounts", "Active",  "Invited" and "Suspended" in Account filter dropdown
	 */
	@Test
	public void TC080DUL_06(){
		result.addLog("ID : TC080DUL_06 : Verify that there are fours items 'All Accounts', 'Active',  'Invited' and 'Suspended' in Account filter dropdown");
		/*
		 	Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click Account filter dropdown to show all of its items.
		 */
		//3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Click Account filter dropdown to show all of its items.
		home.click(UsersList.FILTER_USER);
		/*
		 * The Account filter dropdown displays three items "All Accounts", "Active" "Active",  "Invited" and "Suspended"
		 */
		ArrayList<String> options = home.getAllComboboxOption(UsersList.FILTER_USER);
		Assert.assertTrue(DTSUtil.containsAll(options, UsersList.company_filter));
	}
	
	/*
	 * Verify that the users table displays only activated users when the Account filter dropdown is filtered for "Active"
	 */
	@Test
	public void TC080DUL_07(){
		result.addLog("ID : TC080DUL_07 : Verify that the users table displays only activated users when the Account filter dropdown is filtered for 'Active'");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Set the Account filter dropdown to "Active"
		 */
	
		// 3. Click "Users" tab 
		home.click(Xpath.LINK_USERS);
		// 4. Set the Account filter dropdown to "Active"
		String user_status = "Active";
		home.selectFilterByName(UsersList.FILTER_USER, user_status);
		/*
		 * The users table shows up only activated users and all their information are correctly
		 */
		Assert.assertTrue(home.checkAllUserStatus(user_status));
	}
	
	/*
	 * Verify that the users table displays only invited users when the Account filter dropdown is filtered for "Invited"
	 */
	@Test
	public void TC080DUL_08(){
		result.addLog("ID : TC080DUL_08 : Verify that the users table displays only invited users when the Account filter dropdown is filtered for 'Invited'");
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
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Click Add user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.dtsUser();
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * *************************************************************
		 */
		// 3. Click "Users" tab 
		home.click(Xpath.LINK_USERS);
		// 4. Set the Account filter dropdown to "Active"
		String user_status = "Invited";
		home.selectFilterByName(UsersList.FILTER_USER, user_status);
		/*
		 * The users table shows up only activated users and all their information are correctly
		 */
		Assert.assertTrue(home.checkAllUserStatus(user_status));
		// Delete user
		home.selectUserByEmail(data.get("email"));
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
		
	/*
	 * Verify that the users table displays all activated, suspended and invited users when the Account filter dropdown is filtered for "All Accounts"
	 */
	@Test
	public void TC080DUL_09(){
		result.addLog("ID : TC080DUL_09 : Verify that the users table displays all activated, suspended and invited users when the Account filter dropdown is filtered for 'All Accounts'");
		/*
			"Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Set the Account filter dropdown to "All Accounts"
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Set the Account filter dropdown to "Active"
		String user_status = "All Accounts";
		home.selectFilterByName(UsersList.FILTER_USER, user_status);
		/*
		 * The users table shows up all invited, suspended and activated users and their information are correctly
		 */
		Assert.assertTrue(home.checkAllUserStatus(user_status));
	}
	
	/*
	 * Verify that the users table displays only suspended users when the Account filter dropdown is filtered for "Suspended".
	 */
	@Test
	public void TC080DUL_10(){
		result.addLog("ID : TC080DUL_10 : Verify that the users table displays only suspended users when the Account filter dropdown is filtered for 'Suspended'.");
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
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server, dts_email, email_password);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Delete user if exist
		home.deleteUserByEmail(dts_email);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.dtsUser();
		data.put("email", dts_email);
		home.addUser(AddUser.getDTSUser(), data);
		// Active user
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, dts_email, email_password, messageCount);
		home.activeNewUserViaEmail(yahoo_imap_server, dts_email,email_password, new_active_user_password);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select user above
		home.dtsSelectUserByEmail(data.get("email"));
		// Suspend user
		home.click(UserMgmt.SUSPEND);
		home.selectConfirmationDialogOption("Suspend");
		/*
		 * ****************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Set the Account filter dropdown to "Suspended"
		String user_status = "Suspended";
		home.selectFilterByName(UsersList.FILTER_USER, user_status);
		// Check if PDPP-1191 found
		if(!home.checkUserExistByEmail(data.get("email"))){
			result.addErrorLog("PDPP-1191 - 080 User List: the suspended users are not displayed in suspended list with all companies filtering");
			Assert.assertTrue(false);
		}
		/*
		 * Verify that The users table shows up only suspended users and all their information are correctly
		 */
		Assert.assertTrue(home.checkAllUserStatus(user_status));
	}
	
	/*
	Verify that the "Actions" module shows up "Add New User" link
	*/
	@Test
	public void TC080DUL_11(){
		result.addLog("ID : TC080DUL_11 : Verify that the 'Actions' module shows up 'Add New User' link");
		/*
		 Pre-condition: User has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
		 */
		
		//3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		
		//Verify that the "Actions" module shows up "Add New User" link
		
		Assert.assertTrue(home.existsElement(UsersList.ADD_USER));
	}
	
	/*
	Verify that the default item of company filter is "All Companies".
	*/
	@Test
	public void TC080DUL_12(){
		result.addLog("ID : TC080DUL_12 : Verify that the default item of company filter is 'All Companies'.");
		/*
		 Pre-condition: User has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
		 */
		//3. Navigate to "Users" page
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		home.click(Xpath.LINK_USERS);
		
		/*
		Verify that the default item of company filter is "All Companies".
		*/
		Select comboBox = new Select(driver.findElement(By.id("company-list")));
		String selectedComboValue = comboBox.getFirstSelectedOption().getText();
		System.out.println("Selected combo value: " + selectedComboValue);
		
		Assert.assertEquals("All companies", selectedComboValue);
	}
	
	/*
	Verify that the users table only displays users of filtered company
	*/
	@Test
	public void TC080DUL_13(){
		result.addLog("ID : TC080DUL_13 : Verify that the 'Actions' module shows up 'Add New User' link");
		/*
		 Pre-condition: User has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a company from Company filter dropdown
		 */
		
		//3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		
		//Verify that the users table only displays users of filtered company
		Assert.assertTrue(home.existsElement(UsersList.ADD_USER));
		//COmment
	}
	
	/*
	Verify that the users table only displays users of filtered company
	*/
	@Test
	public void TC080DUL_14(){
		result.addLog("ID : TC080DUL_13 : Verify that the 'Actions' module shows up 'Add New User' link");
		/*
		 Pre-condition: User has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a company from Company filter dropdown
		 */
		
		System.out.println(driver.findElement(By.xpath("//meta[@name = '_csrf']")).getAttribute("content"));
		System.out.println(driver.manage().getCookieNamed("JSESSIONID"));

		
	}
	
}
