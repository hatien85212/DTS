package com.dts.adminportal.partner.test;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.DTSUtil;

import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.PartnerListUser;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.UsersList;
import dts.com.adminportal.model.Xpath;

public class PartnerUser080PUserList extends CreatePage{
	private HomeController home;
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}

	@BeforeMethod
	public void loginBeforeTest() {
		home.login(superpartneruser, superpartnerpassword);
	}
	
	/*
	 Verify that the "Users" tab will be hidden when partner user does not have "Add and manage users" rights.
	 */
	@Test
	public void TC080PUL_01(){
		result.addLog("ID : TC080PUL_01 : Verify that the 'Users' tab will be hidden when partner user does not have 'Add and manage users' rights.");
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
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Select a user from users table
		home.selectUserByEmail(partneruser);
		// VP: Verify that the 090D User Info page is displayed
		Assert.assertTrue(home.isElementPresent(UserMgmt.USER_INFO_PAGE_TITLE));
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Unselect "Add and manage users" option in "Site Privileges" section
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal under above user's account
		home.login(partneruser, password);
		// The "Users" tab is not displayed.
		Assert.assertFalse(home.isElementPresent(Xpath.LINK_PARTNER_USER));
	}
	
	/*
	 * Verify that the "Users" is showed up when partner user has "Add and manage users" rights.
	 */
	@Test
	public void TC080PUL_02(){
		result.addLog("ID : TC080PUL_02 : Verify that the 'Users' is showed up when partner user has 'Add and manage users' rights.");
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
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Select a user from users table
		home.selectUserByEmail(partneruser);
		// VP: Verify that the 090D User Info page is displayed
		Assert.assertTrue(home.isElementPresent(UserMgmt.USER_INFO_PAGE_TITLE));
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Select "Add and manage users" option in "Site Privileges" section
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal under above user's account
		home.login(partneruser, password);
		// The "Users" tab is displayed.
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_PARTNER_USER));
	}
	
	/*
	 * Verify that the partner's user page displays properly.
	 */
	@Test
	public void TC080PUL_03(){
		result.addLog("ID : TC080PUL_03 : Verify that the partner's user page displays properly.");
		/*
		 	Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		/* Verify that the 080P Users page is displayed which including: 
		 * Account filter dropdown, 
		 * Account users table, 
		 * Actions module, 
		 * a pagination below Account users table and total of users in users table correctly.
		 */
		Assert.assertEquals(home.existsElement(UsersList.getListElementPartnerUser()).getResult(), "Pass");
	}
	
	/*
	 * Verify that the users table in "Users" page shows up "First Name", "Last Name", "Title", "Phone" and "Email" columns.
	 */
	@Test
	public void TC080PUL_04(){
		result.addLog("ID : TC080PUL_04 : Verify that the users table in 'Users' page shows up 'First Name', 'Last Name', 'Title', 'Phone' and 'Email' columns.");
		/*
		 	Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		/*
		 * Verify that the users table is displayed which including 
		 * "First Name", 
		 * "Last Name", 
		 * "Title", 
		 * "Phone" and 
		 * "Email" columns.
		 */
		ArrayList<String> list =  home.getAllColumnHeader(UsersList.USER_TABLE);
		Assert.assertTrue(DTSUtil.containsAll(list, UsersList.theadsPartner));
	}
	
	/*
	 * Verify that there are 3 items "All Accounts", "Active" and "Invited" in Account filter dropdown.
	 */
	
	/*
	Verify that the users table in "Users" page shows up maximum 50 user accounts per page.
	*/
	@Test
	public void TC080PUL_05(){
		result.addLog("ID : TC080PUL_05 : Verify that the users table in 'Users' page shows up maximum 50 user accounts per page.");
		/*
		 Pre-condition: User has "Add and Manage Users" rights. There are at least 50 registered users account.

			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Click "Users" tab
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		 // Verify that the users table is displayed maximum 50 user accounts per page.
		Assert.assertTrue(home.checkItemAmountDisplayOnTable(UsersList.TABLE_INFO, 50));
	}
	
	/*
	 * Verify that there are 3 items "All Accounts", "Active" and "Invited" in Account filter dropdown.
	 */
	@Test
	public void TC080PUL_06(){
		result.addLog("ID : TC080PUL_06 : Verify that there are 3 items 'All Accounts', 'Active' and 'Invited' in Account filter dropdown.");
		/*
		 	Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Click Account filter dropdown to show all of its items.
		 */
		
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Click Account filter dropdown to show all of its items.
		home.click(PartnerListUser.FILTER);
		// The Account filter dropdown displays three items "All Accounts", "Active" and "Invited".
		ArrayList<String> filters = home.getAllOption(PartnerListUser.FILTER);
		Assert.assertTrue(DTSUtil.containsAll(filters, PartnerListUser.filters));
	}
	
	/*
	 * Verify that the users table displays only activated users when the Account filter dropdown is filtered for "Active"
	 */
	@Test
	public void TC080PUL_07(){
		result.addLog("ID : TC080PUL_07 : Verify that the users table displays only activated users when the Account filter dropdown is filtered for 'Active'");
		/*
		 Pre-condition: partner user has "Add and Manage Users" rights. 
		 1. Navigate to DTS portal 
		 2. Log into DTS portal as a partner user successfully 
		 3. Click "Users" tab 
		 4. Set the Account filter dropdown to "Active"
		 */
		// 3. Click "Users" tab 
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Set the Account filter dropdown to "Active"
		//String user_status = "Active";
		home.selectReportsFilterByText(UsersList.FILTER_USER, UsersList.company_filter[1]);
		//home.selectFilterByName(UsersList.FILTER_USER, user_status);
		/*
		 * The users table shows up only activated users and all their information are correctly
		 */
		Assert.assertTrue(home.checkAllUserStatus(UsersList.company_filter[1]));
	}
	
	/*
	 * Verify that the users table displays only invited users when the Account filter dropdown is filtered for "Invited"
	 */
	@Test
	public void TC080PUL_08(){
		result.addLog("ID : TC080PUL_08 : Verify that the users table displays only invited users when the Account filter dropdown is filtered for 'Invited'");
		/*
		 	Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Set the Account filter dropdown to "Invited"
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Set the Account filter dropdown to "Active"
		//String user_status = "Active";
		home.selectReportsFilterByText(UsersList.FILTER_USER, UsersList.company_filter[2]);
		//home.selectFilterByName(UsersList.FILTER_USER, user_status);
		/*
		 * The users table shows up only activated users and all their information are correctly
		 */
		Assert.assertTrue(home.checkAllUserStatus(UsersList.company_filter[2]));
	}
		
	/*
	 * Verify that the users table displays all activated and invited users when the Account filter dropdown is filtered for "All Accounts"
	 */
	@Test
	public void TC080PUL_09(){
		result.addLog("ID : TC080PUL_09 : Verify that the users table displays all activated and invited users when the Account filter dropdown is filtered for 'All Accounts'");
		/*
		 Pre-condition: partner user has "Add and Manage Users" rights. 
		 1. Navigate to DTS portal 
		 2. Log into DTS portal as a partner user successfully 
		 3. Click "Users" tab 
		 4. Set the Account filter dropdown to "All Accounts"
		 */
		
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Set the Account filter dropdown to "Active"
		//String user_status = "Active";
		home.selectReportsFilterByText(UsersList.FILTER_USER, UsersList.company_filter[0]);
		//home.selectFilterByName(UsersList.FILTER_USER, user_status);
		/*
		 * The users table shows up only activated users and all their information are correctly
		 */
		Assert.assertTrue(home.checkAllUserStatus(UsersList.company_filter[0]));
	}
		
	/*
	 * Verify that the "Actions" module shows up "Add New User" link
	 */
	@Test
	public void TC080PUL_10(){
		result.addLog("ID : TC080PUL_10 : Verify that the 'Actions' module shows up 'Add New User' link");
		/*
		 	Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// The "Actions" module shows up "Add New User" link
		Assert.assertTrue(home.isElementPresent(UsersList.ADD_USER));
	}
}
