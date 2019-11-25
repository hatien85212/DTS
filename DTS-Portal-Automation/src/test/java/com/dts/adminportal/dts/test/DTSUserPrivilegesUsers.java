package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
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
import dts.com.adminportal.model.LoginPage;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UserEdit;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.UsersList;
import dts.com.adminportal.model.Xpath;

public class DTSUserPrivilegesUsers extends CreatePage{
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
	 * Verify that the DTS user can view user's info when “Add and manage users” privilege is enabled and “Add and manage DTS users” privilege is disabled
	 */
	@Test
	public void TCPUSR_01() {
		result.addLog("ID : TCPUSR_01 : Verify that the DTS user can view user's info when “Add and manage users” privilege is enabled and “Add and manage DTS users” privilege is disabled.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the  “Add and manage DTS users” privilege
			7. Enable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			VP: Verify that the “Users” tab is displayed
			10. Click “Users” tab
			VP: Verify that the Users list is displayed in Users page
			11. Select a user from user list
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the  “Add and manage DTS users” privilege
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// VP: Verify that the “Users” tab is displayed
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_USERS));
		// 10. Click “Users” tab
		home.click(Xpath.LINK_USERS);
		// VP: Verify that the Users list is displayed in Users page
		Assert.assertEquals("Pass", home.existsElement(UsersList.getListElementUser()).getResult());
		// 11. Select a user from user list
		home.selectUserByEmail(superpartneruser);
		/*
		 * Verify that the User's info page is displayed
		 */
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
	}
	
	/*
	 * Verify that the DTS user can view user's info when “Add and manage users” privilege is disabled and “Add and manage DTS users” privilege is enabled
	 */
	@Test
	public void TCPUSR_02() {
		result.addLog("ID : TCPUSR_02 : Verify that the DTS user can view user's info when “Add and manage users” privilege is disabled and “Add and manage DTS users” privilege is enabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage DTS users” privilege
			7. Disable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			VP: Verify that the “Users” tab is displayed
			10. Click “Users” tab
			VP: Verify that the Users list is displayed in Users page
			11. Select a user from user list
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage DTS users” privilege
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Disable the “Add and manage users” privilege
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// VP: Verify that the “Users” tab is displayed
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_USERS));
		// 10. Click “Users” tab
		home.click(Xpath.LINK_USERS);
		// VP: Verify that the Users list is displayed in Users page
		Assert.assertEquals("Pass", home.existsElement(UsersList.getListElementUser()).getResult());
		// 11. Select a user from user list
		home.selectUserByEmail(dtsUser);
		// Verify that the User's info page is displayed.
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
	}
	
	/*
	 * Verify that the DTS user cannot manage “Users” page when both “Add and manage users” and “Add and manage DTS users” privileges are disabled
	 */
	@Test
	public void TCPUSR_03() {
		result.addLog("ID : TCPUSR_03 : Verify that the DTS user cannot manage “Users” page when both “Add and manage users” and “Add and manage DTS users” privileges are disabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the  “Add and manage DTS users” privilege
			7. Disable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the  “Add and manage DTS users” privilege
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Disable the “Add and manage users” privilege
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(UserEdit.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// Verify that the “Users” tab is not displayed in Global Navigation bar
		Assert.assertFalse(home.isElementPresent(Xpath.LINK_USERS));
	}
	
	/*
	 * Verify that both DTS and Partner user are displayed on Users list when both “Add and manage users” and “Add and manage DTS users” privileges are enabled
	 */
	@Test
	public void TCPUSR_04() {
		result.addLog("ID : TCPUSR_04 : Verify that both DTS and Partner user are displayed on Users list when both “Add and manage users” and “Add and manage DTS users” privileges are enabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage DTS users” privilege
			7. Enable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage DTS users” privilege
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 10. Click “Users” tab
		home.click(Xpath.LINK_USERS);
		/*
		 * Verify that the user list displays both Partner and DTS user
		 */
		Assert.assertTrue(home.checkUserExistByEmail(dtsUser));
		Assert.assertTrue(home.checkUserExistByEmail(partneruser));
	}
	
	/*
	 * Verify that  DTS user can only add new DTS user when the “Add and manage users” privilege is disabled and “Add and manage DTS users” privilege is enabled.
	 */
	@Test
	public void TCPUSR_05() {
		result.addLog("ID : TCPUSR_05 : Verify that DTS user can only add new DTS user when the “Add and manage users” privilege is disabled and “Add and manage DTS users” privilege is enabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage DTS users” privilege
			7. Disable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the “Add New User” link is displayed while Users list only display DTS users
			11. Click “Add New User” link
			VP: In Add new user page, the Company combobox only contains “DTS Inc.” item and both “Site Privilege” and “DTS Site Privilege” module are displayed.
			12. Fill valid value into all required fields
			13. Select at least one privilege
			14. Click “Save” link		
		 */
		
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage DTS users” privilege
		home.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Disable the “Add and manage users” privilege
		home.disablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 10. Click “Users” tab
		home.click(Xpath.LINK_USERS);
		// VP: Verify that the “Add New User” link is displayed while Users list only display DTS users
		Assert.assertTrue(home.existsElement(UsersList.ADD_USER));
		// Users list only display DTS users
		Assert.assertTrue(home.verifyValueColumn(UsersList.USER_TABLE, "Company", "DTS Inc."));
		// 11. Click “Add New User” link
		home.click(UsersList.ADD_USER);
		// Get Company option
		ArrayList<String> options = home.getAllComboboxOption(AddUser.BTN_COMPANY);
		/*
		 * VP: In Add new user page, the Company combobox only contains “DTS Inc.” item and both “Site Privilege” and “DTS Site Privilege” module are displayed
		 */
		Assert.assertTrue(options.size() == 2 && DTSUtil.checkListContain(options, dts_company_name));
		Assert.assertTrue(home.isElementPresent(AddUser.PRIVILEGES_TABLE));
		Assert.assertTrue(home.isElementPresent(AddUser.DTS_PRIVILEGES_TABLE));
		// 12. Fill valid value into all required fields
		// 13. Select at least one privilege
		// 14. Click “Save” link
		Hashtable<String,String> data = TestData.dtsUser();
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * Verify that a new DTS user is created successfully
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// Delete user
		home.click(Xpath.LINK_USERS);
		home.dtsSelectUserByEmail(data.get("email"));
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can only add new Partner user when the “Add and manage users” privilege is enabled and “Add and manage DTS users” privilege is disabled
	 */
	@Test
	public void TCPUSR_06() {
		result.addLog("ID : TCPUSR_06 : Verify that  DTS user can only add new Partner user when the “Add and manage users” privilege is enabled and “Add and manage DTS users” privilege is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the  “Add and manage DTS users” privilege
			7. Enable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the “Add New User” link is displayed while Users list only display partner users
			11. Click “Add New User” link
			VP: In Add new user page, the Company combobox contains all company except “DTS Inc.”
			12. Select a company from company combobox
			VP: Verify that only “Site Privilege” module is displayed
			13. Fill valid value into all required fields
			14. Select at least one privilege
			15. Click “Save” link		
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the  “Add and manage DTS users” privilege
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 10. Navigate to Users page
		home.click(Xpath.LINK_USERS);		
		/*
		 * VP: Verify that the “Add New User” link is displayed while Users list only display partner users
		 */
		Assert.assertTrue(home.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(home.verifyColumnNotContainValue(UsersList.USER_TABLE, "Company", "DTS Inc."));
		// 11. Click “Add New User” link
		home.click(UsersList.ADD_USER);
		/*
		 * VP: In Add new user page, the Company combobox contains all company except “DTS Inc.”
		 */
		ArrayList<String> listCompany = home.getAllOption(AddUser.BTN_COMPANY);
		Assert.assertTrue(listCompany.size() > 1);
		Assert.assertFalse(DTSUtil.checkListContain(listCompany, "DTS Inc."));
		// 12. Select a company from company combobox
		home.selectOptionByName(AddUser.BTN_COMPANY, partner_company_name);
		/*
		 * VP: Verify that only “Site Privilege” module is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddUser.PRIVILEGES_TABLE));
		Assert.assertFalse(home.isElementPresent(AddUser.DTS_PRIVILEGES_TABLE));
		// 13. Fill valid value into all required fields
		// 14. Select at least one privilege
		// 15. Click “Save” link
		Hashtable<String,String> data = TestData.partnerUser();
		home.addUser(AddUser.getPartnerUser(), data);
		/*
		 * Verify that a new Partner user is created successfully
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// Delete user
		home.click(Xpath.LINK_USERS);
		home.deleteUserByEmail(data.get("email"));
	}	
	
	/*	
	 * Verify that DTS user can add new both Partner and DTS users when the “Add and manage users” and “Add and manage DTS users” privileges are enabled
	 */
	@Test
	public void TCPUSR_07() {
		result.addLog("ID : TCPUSR_07 : Verify that DTS user can add new both Partner and DTS users when the “Add and manage users” and “Add and manage DTS users” privileges are enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage DTS users” privilege
			7. Enable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the “Add New User” link is displayed while Users list displays both DTS and Partner users
			11. Click “Add New User” link
			VP: In Add new user page, the Company combobox contains “DTS Inc.” and all other companies
			12. Select a company from company combobox
			VP: Verify that only “Site Privilege” module is displayed
			13. Fill valid value into all required fields
			14. Select at least one privilege
			15. Click “Save” link
			VP: Verify that a new Partner user is created successfully
			16. Repeat from step 11 to step 15 with “DTS Inc.” company		
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage DTS users” privilege
		home.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the “Add and manage users” privilege
		home.enablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 10. Navigate to Users page
		home.click(Xpath.LINK_USERS);
		ArrayList<String> companyColumn = home.getDataByHeaderText(UsersList.USER_TABLE, "Company");
		/*
		 * VP: Verify that the “Add New User” link is displayed while Users list displays both DTS and Partner users
		 */
		Assert.assertTrue(home.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(DTSUtil.checkListContain(companyColumn, dts_company_name) && DTSUtil.checkListContain(companyColumn, partner_company_name));
		// 11. Click “Add New User” link
		home.click(UsersList.ADD_USER);
		ArrayList<String> companyOptions = home.getAllComboboxOption(AddUser.BTN_COMPANY);
		/*
		 * VP: In Add new user page, the Company combobox contains “DTS Inc.” and all other companies
		 */
		Assert.assertTrue(DTSUtil.checkListContain(companyOptions, dts_company_name) && DTSUtil.checkListContain(companyOptions, partner_company_name));
		// 12. Select a company from company combobox
		home.selectOptionByName(AddUser.BTN_COMPANY, partner_company_name);
		/*
		 * VP: Verify that only “Site Privilege” module is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddUser.PRIVILEGES_TABLE));
		Assert.assertFalse(home.isElementPresent(AddUser.DTS_PRIVILEGES_TABLE));
		// 13. Fill valid value into all required fields
		// 14. Select at least one privilege
		// 15. Click “Save” link
		Hashtable<String, String> data = TestData.partnerUser();
		home.addUser(AddUser.getPartnerUser(), data);
		/*
		 * VP: Verify that a new Partner user is created successfully
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// 16. Repeat from step 11 to step 15 with “DTS Inc.” company
		home.click(Xpath.LINK_USERS);
		// Click “Add New User” link
		home.click(UsersList.ADD_USER);
		// Select a company from company combobox
		home.selectOptionByName(AddUser.BTN_COMPANY, dts_company_name);
		// Fill valid value into all required fields
		// Select at least one privilege
		// Click “Save” link
		data = TestData.dtsUser();
		home.addUser(AddUser.getPartnerUser(), data);
		/*
		 * VP: Verify that a new DTS user is created successfully
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
	}
	
	/*	
	 * Verify that  DTS user can only edit Partner users when the “Add and manage users” privilege is enabled and “Add and manage DTS users” privilege is disabled
	 */
	@Test
	public void TCPUSR_08() {
		result.addLog("ID : TCPUSR_08 : Verify that  DTS user can only edit Partner users when the “Add and manage users” privilege is enabled and “Add and manage DTS users” privilege is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the  “Add and manage DTS users” privilege
			7. Enable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the “Add New User” link is displayed while Users list displays only Partner users
			11. Select a user from users list
			VP: Verify that the “Edit” link is displayed
			12. Click “Edit” link
			VP: Verify that the user edit page is displayed
			13. Change some user's value
			14. Click “Save” link	
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the “Add and manage DTS users” privilege
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 10. Navigate to Users page
		home.click(Xpath.LINK_USERS);		
		/*
		 * VP: Verify that the “Add New User” link is displayed while Users list only display partner users
		 */
		Assert.assertTrue(home.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(home.verifyColumnNotContainValue(UsersList.USER_TABLE, "Company", "DTS Inc."));
		// 11. Select a user from users list
		home.click(Xpath.LINK_USERS);	
		home.dtsSelectUserByEmail(partneruser);
		/*
		 * VP: Verify that the “Edit” link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.EDIT));
		// 12. Click “Edit” link
		home.click(UserMgmt.EDIT);
		/*
		 * VP: Verify that the user edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddUser.getPartnerUser()).getResult(), "Pass");
		// 13. Change some user's value
		String newTitle = RandomStringUtils.randomAlphabetic(20);
		home.editData(UserEdit.TITLE, newTitle);
		// 14. Click “Save” link	
		home.click(UserEdit.SAVE);
		/*
		 * Verify that the partner user info page is displayed with new updated information correctly
		 */
		Assert.assertEquals(home.getTextByXpath(UserMgmt.TITLE), newTitle);
	}	
	
	/*	
	 * Verify that  DTS user can only edit DTS users when the “Add and manage users” privilege is disabled and “Add and manage DTS users” privilege is enabled
	 */
	@Test
	public void TCPUSR_09() {
		result.addLog("ID : TCPUSR_09 : Verify that  DTS user can only edit DTS users when the “Add and manage users” privilege is disabled and “Add and manage DTS users” privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage DTS users” privilege
			7. Disable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the “Add New User” link is displayed while Users list displays only DTS users
			11. Select a user from users list
			VP: Verify that the “Edit” link is displayed
			12. Click “Edit” link
			VP: Verify that the user edit page is displayed
			13. Change some user's value
			14. Click “Save” link
		 */
		
		/*
		 * Pre-condition: Create new DTS user
		 */
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.dtsUser();
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * ****************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage DTS users” privilege
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Disable the “Add and manage users” privilege
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 10. Navigate to Users page
		home.click(Xpath.LINK_USERS);		
		/*
		 * VP: Verify that the “Add New User” link is displayed while Users list displays only DTS users
		 */
		Assert.assertTrue(home.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(home.verifyValueColumn(UsersList.USER_TABLE, "Company", dts_company_name));
		// 11. Select a user from users list
		home.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that the “Edit” link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.EDIT));
		// 12. Click “Edit” link
		home.click(UserMgmt.EDIT);
		/*
		 * VP: Verify that the user edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddUser.getDTSUser()).getResult(), "Pass");
		// 13. Change some user's value
		String newTitle = RandomStringUtils.randomAlphabetic(20);
		home.editData(AddUser.TITLE, newTitle);
		// 14. Click “Save” link
		home.click(AddUser.SAVE);
		/*
		 * Verify that the DTS user info page is displayed with new updated information correctly
		 */
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(UserMgmt.TITLE), newTitle);
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that DTS user can edit both DTS and Partner users when both “Add and manage users” and “Add and manage DTS users” privileges are enabled
	 */
	@Test
	public void TCPUSR_10() {
		result.addLog("ID : TCPUSR_10 : Verify that DTS user can edit both DTS and Partner users when both “Add and manage users” and “Add and manage DTS users” privileges are enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage DTS users” privilege
			7. Enable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the “Add New User” link is displayed while Users list displays both DTS and Partner users
			11. Select a Partner user from users list
			VP: Verify that the “Edit” link is displayed
			12. Click “Edit” link
			VP: Verify that the user edit page is displayed
			13. Change some user's value
			14. Click “Save” link
			VP: Verify that the DTS user info page is displayed with new updated information correctly.
			15. Repeat from step 10 to step 14 with DTS user
		 */
		/*
		 * Pre-condition: Create new DTS user
		 */
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.dtsUser();
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * ****************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage DTS users” privilege
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 10. Navigate to Users page
		home.click(Xpath.LINK_USERS);
		ArrayList<String> companyColumn = home.getDataByHeaderText(UsersList.USER_TABLE, "Company");
		/*
		 * VP: Verify that the “Add New User” link is displayed while Users list displays both DTS and Partner users
		 */
		Assert.assertTrue(home.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(DTSUtil.checkListContain(companyColumn, dts_company_name) && DTSUtil.checkListContain(companyColumn, partner_company_name));
		// 11. Select a Partner user from users list
		home.dtsSelectUserByEmail(partneruser);
		/*
		 *  VP: Verify that the “Edit” link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.EDIT));
		// 12. Click “Edit” link
		home.click(UserMgmt.EDIT);
		/*
		 * VP: Verify that the user edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddUser.getDTSUser()).getResult(), "Pass");
		// 13. Change some user's value
		String newTitle = RandomStringUtils.randomAlphabetic(20);
		home.editData(AddUser.TITLE, newTitle);
		// 14. Click “Save” link
		home.click(AddUser.SAVE);
		/*
		 * VP: Verify that the DTS user info page is displayed with new updated information correctly
		 */
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(UserMgmt.TITLE), newTitle);
		// 15. Repeat from step 10 to step 14 with DTS user
		home.click(Xpath.LINK_USERS);
		// Select a Partner user from users list
		home.dtsSelectUserByEmail(data.get("email"));
		// Click “Edit” link
		home.click(UserMgmt.EDIT);
		// Change some user's value
		home.editData(AddUser.TITLE, newTitle);
		// Click “Save” link
		home.click(AddUser.SAVE);
		/*
		 * VP: Verify that the DTS user info page is displayed with new updated information correctly
		 */
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(UserMgmt.TITLE), newTitle);
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can only reset password for Partner users when the “Add and manage users” privilege is enabled and “Add and manage DTS users” privilege is disabled
	 */
	@Test
	public void TCPUSR_11() {
		result.addLog("ID : TCPUSR_11 : Verify that  DTS user can only reset password for Partner users when the “Add and manage users” privilege is enabled and “Add and manage DTS users” privilege is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the  “Add and manage DTS users” privilege
			7. Enable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that Users list displays only Partner users
			11. Select a user from users list
			VP: Verify that the “Reset Password” link is displayed
			12. Click “Reset Password” link
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
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the  “Add and manage DTS users” privilege
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 10. Navigate to Users page
		home.click(Xpath.LINK_USERS);
		/*
		 * VP: Verify that the “Add New User” link is displayed while Users list displays only Partner users
		 */
		Assert.assertTrue(home.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(home.verifyColumnNotContainValue(UsersList.USER_TABLE, "Company", dts_company_name));
		// 11. Select a user from users list
		home.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that the “Reset Password” link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.RESET_PASSWORD));
		// 12. Click “Reset Password” link
		home.click(UserMgmt.RESET_PASSWORD);
		home.switchWindowClickOption("Reset");
		/*
		 * Verified that a reset password email is sent to user
		 */
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email, email_password, messageCount + 1);
		Assert.assertTrue(MailUtil.getNewEmailSubject(yahoo_imap_server, pdts_email, email_password).contains("Reset password"));
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can only reset password DTS users when the “Add and manage users” privilege is disabled and “Add and manage DTS users” privilege is enabled
	 */
	@Test
	public void TCPUSR_12() {
		result.addLog("ID : TCPUSR_12 : Verify that  DTS user can only reset password DTS users when the “Add and manage users” privilege is disabled and “Add and manage DTS users” privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage DTS users” privilege
			7. Disable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the “Add New User” link is displayed while Users list displays only DTS users
			11. Select a user from users list
			VP: Verify that the “Reset Password” link is displayed
			12. Click “Reset Password” link
		 */
		/*
		 * Pre-condition: Create new DTS user
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
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Add and manage DTS users” privilege
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Disable the “Add and manage users” privilege
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 10. Navigate to Users page
		home.click(Xpath.LINK_USERS);
		/*
		 * VP: Verify that the “Add New User” link is displayed while Users list displays only DTS users
		 */
		Assert.assertTrue(home.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(home.verifyValueColumn(UsersList.USER_TABLE, "Company", dts_company_name));
		// 11. Select a user from users list
		home.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that the “Reset Password” link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.RESET_PASSWORD));
		// 12. Click “Reset Password” link
		home.click(UserMgmt.RESET_PASSWORD);
		home.switchWindowClickOption("Reset");
		/*
		 * Verified that a reset password email is sent to user
		 */
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, dts_email, email_password, messageCount + 1);
		Assert.assertTrue(MailUtil.getNewEmailSubject(yahoo_imap_server, dts_email, email_password).contains("Reset password"));
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can reset password for both DTS and Partner users when both “Add and manage users” and “Add and manage DTS users” privileges are enabled
	 */
	@Test
	public void TCPUSR_13() {
		result.addLog("ID : TCPUSR_13 : Verify that  DTS user can reset password for both DTS and Partner users when both “Add and manage users” and “Add and manage DTS users” privileges are enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage DTS users” privilege
			7. Enable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the “Add New User” link is displayed while Users list displays both DTS and Partner users
			11. Select a Partner user from users list
			VP: Verify that the “Reset Password” link is displayed
			12. Click “Reset Password” link
			VP: A reset password email is sent to user
			13. Repeat from step 10 to 12 with DTS user
		 */
		/*
		 * Pre-condition: Create new DTS user and Partner user
		 */
		// Get email message count before create user
		int messageCountPartner = MailUtil.getEmailMessageCount(yahoo_imap_server, pdts_email, email_password);
		int messageCountDTS = MailUtil.getEmailMessageCount(yahoo_imap_server, dts_email, email_password);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Delete user if exist
		home.deleteUserByEmail(pdts_email);
		home.deleteUserByEmail(dts_email);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create DTS user
		Hashtable<String,String> dataDts = TestData.dtsUser();
		dataDts.put("email", dts_email);
		home.addUser(AddUser.getDTSUser(), dataDts);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create Partner user
		Hashtable<String,String> dataPartner = TestData.partnerUser();
		dataPartner.put("email", pdts_email);
		home.addUser(AddUser.getDTSUser(), dataPartner);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Add and manage DTS users” privilege
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 10. Navigate to Users page
		home.click(Xpath.LINK_USERS);
		ArrayList<String> companyColumn = home.getDataByHeaderText(UsersList.USER_TABLE, "Company");
		/*
		 * VP: Verify that the “Add New User” link is displayed while Users list
		 * displays both DTS and Partner users
		 */
		Assert.assertTrue(home.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(DTSUtil.checkListContain(companyColumn, dts_company_name) && DTSUtil.checkListContain(companyColumn, partner_company_name));
		// 11. Select a Partner user from users list
		home.dtsSelectUserByEmail(dataPartner.get("email"));
		/*
		 * VP: Verify that the “Reset Password” link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.RESET_PASSWORD));
		// 12. Click “Reset Password” link
		home.click(UserMgmt.RESET_PASSWORD);
		home.switchWindowClickOption("Reset");
		/*
		 * VP: A reset password email is sent to user
		 */
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email, email_password, messageCountPartner + 1);
		Assert.assertTrue(MailUtil.getNewEmailSubject(yahoo_imap_server, pdts_email, email_password).contains("Reset password"));
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
		// 13. Repeat from step 10 to 12 with DTS user
		home.click(Xpath.LINK_USERS);
		// Select a Partner user from users list
		home.dtsSelectUserByEmail(dataDts.get("email"));
		// Click “Reset Password” link
		home.click(UserMgmt.RESET_PASSWORD);
		home.switchWindowClickOption("Reset");
		/*
		 * Verified that a reset password email is sent to user
		 */
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, dts_email, email_password, messageCountDTS + 1);
		Assert.assertTrue(MailUtil.getNewEmailSubject(yahoo_imap_server, dts_email, email_password).contains("Reset password"));
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can only delete Partner users when the “Add and manage users” privilege is enabled and “Add and manage DTS users” privilege is disabled
	 */
	@Test
	public void TCPUSR_14() {
		result.addLog("ID : TCPUSR_14 : Verify that  DTS user can only delete Partner users when the “Add and manage users” privilege is enabled and “Add and manage DTS users” privilege is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the  “Add and manage DTS users” privilege
			7. Enable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the “Add New User” link is displayed while Users list displays only Partner users
			11. Select a user from users list
			VP: Verify that the “Delete” link is displayed
			12. Click “Delete” link
			13. Click “Delete” button on delete confirmation dialog
		 */
		/*
		 * Pre-condition: Create new Partner user
		 */
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.partnerUser();
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the  “Add and manage DTS users” privilege
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 10. Navigate to Users page
		home.click(Xpath.LINK_USERS);
		/*
		 * VP: Verify that the “Add New User” link is displayed while Users list displays only Partner users
		 */
		Assert.assertTrue(home.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(home.verifyColumnNotContainValue(UsersList.USER_TABLE, "Company", dts_company_name));
		// 11. Select a user from users list
		home.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that the “Delete” link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.DELETE_ACCOUNT));
		// 12. Click “Delete” link
		// 13. Click “Delete” button on delete confirmation dialog
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
		/*
		 * Verify that the portal redirects to Users list page and the deleted user is not displayed in Users list
		 */
		Assert.assertEquals(home.existsElement(UsersList.getListElementUser()).getResult(), "Pass");
		Assert.assertFalse(home.checkUserExistByEmail(data.get("email")));
	}
	
	/*	
	 * Verify that DTS user can only delete DTS users when the “Add and manage users” privilege is disabled and “Add and manage DTS users” privilege is enabled
	 */
	@Test
	public void TCPUSR_15() {
		result.addLog("ID : TCPUSR_15 : Verify that  DTS user can only delete DTS users when the “Add and manage users” privilege is disabled and “Add and manage DTS users” privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage DTS users” privilege
			7. Disable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the “Add New User” link is displayed while Users list displays only DTS users
			11. Select a user from users list
			VP: Verify that the “Delete” link is displayed
			12. Click “Delete” link
			13. Click “Delete” button on delete confirmation dialog
		 */
		/*
		 * Pre-condition: Create new DTS user
		 */
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.dtsUser();
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage DTS users” privilege
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Disable the “Add and manage users” privilege
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 10. Navigate to Users page
		home.click(Xpath.LINK_USERS);
		/*
		 * VP: Verify that the “Add New User” link is displayed while Users list displays only DTS users
		 */
		Assert.assertTrue(home.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(home.verifyValueColumn(UsersList.USER_TABLE, "Company", dts_company_name));
		// 11. Select a user from users list
		home.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that the “Delete” link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.DELETE_ACCOUNT));
		// 12. Click “Delete” link
		// 13. Click “Delete” button on delete confirmation dialog
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
		/*
		 * Verify that the portal redirects to Users list page and the deleted user is not displayed in Users list
		 */
		Assert.assertEquals(home.existsElement(UsersList.getListElementUser()).getResult(), "Pass");
		Assert.assertFalse(home.checkUserExistByEmail(data.get("email")));
	}
	
	/*	
	 * Verify that DTS user can delete both DTS and Partner users when both “Add and manage users” and “Add and manage DTS users” privileges are enabled
	 */
	@Test
	public void TCPUSR_16() {
		result.addLog("ID : TCPUSR_16 : Verify that DTS user can delete both DTS and Partner users when both “Add and manage users” and “Add and manage DTS users” privileges are enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage DTS users” privilege
			7. Enable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the “Add New User” link is displayed while Users list displays both DTS and Partner users
			11. Select a Partner user from users list
			VP: Verify that the “Delete” link is displayed
			12. Click “Delete” link
			13. Click “Delete” button on delete confirmation dialog
			VP: Verify that the portal redirects to Users list page and the deleted user is not displayed in Users list.
			14. Repeat from step 10 to step 14 with DTS user
		 */
		/*
		 * Pre-condition: Create new DTS user and Partner user
		 */
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> dataDts = TestData.dtsUser();
		home.addUser(AddUser.getDTSUser(), dataDts);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> dataPartner = TestData.dtsUser();
		home.addUser(AddUser.getDTSUser(), dataPartner);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage DTS users” privilege
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 10. Navigate to Users page
		home.click(Xpath.LINK_USERS);
		ArrayList<String> companyColumn = home.getDataByHeaderText(UsersList.USER_TABLE, "Company");
		/*
		 * VP: Verify that the “Add New User” link is displayed while Users list displays both DTS and Partner users
		 */
		Assert.assertTrue(home.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(DTSUtil.checkListContain(companyColumn, dts_company_name) && DTSUtil.checkListContain(companyColumn, partner_company_name));
		// 11. Select a Partner user from users list
		home.dtsSelectUserByEmail(dataPartner.get("email"));
		/*
		 * VP: Verify that the “Delete” link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.DELETE_ACCOUNT));
		// 12. Click “Delete” link
		// 13. Click “Delete” button on delete confirmation dialog
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
		/*
		 * VP: Verify that the portal redirects to Users list page and the deleted user is not displayed in Users list
		 */
		Assert.assertEquals(home.existsElement(UsersList.getListElementUser()).getResult(), "Pass");
		Assert.assertFalse(home.checkUserExistByEmail(dataPartner.get("email")));
		// 14. Repeat from step 10 to step 14 with DTS user
		// Select a DTS user from users list
		home.dtsSelectUserByEmail(dataDts.get("email"));
		// Click “Delete” link
		// Click “Delete” button on delete confirmation dialog
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
		/*
		 * VP: Verify that the portal redirects to Users list page and the deleted user is not displayed in Users list
		 */
		Assert.assertEquals(home.existsElement(UsersList.getListElementUser()).getResult(), "Pass");
		Assert.assertFalse(home.checkUserExistByEmail(dataDts.get("email")));
	}
	
	/*	
	 * Verify that  DTS user can only suspend Partner users when the “Add and manage users” privilege is enabled and “Add and manage DTS users” privilege is disabled
	 */
	@Test
	public void TCPUSR_17() {
		result.addLog("ID : TCPUSR_17 : Verify that  DTS user can only suspend Partner users when the “Add and manage users” privilege is enabled and “Add and manage DTS users” privilege is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the  “Add and manage DTS users” privilege
			7. Enable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the “Add New User” link is displayed while Users list displays only Partner users
			11. Select a user from users list
			VP: Verify that the “Suspend” link is displayed but the “Restore” link is not
			12. Click “Suspend” link
			13. Click “Suspend” button on suspend confirmation dialog
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
		// Active user
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email, email_password, messageCount);
		home.activeNewUserViaEmail(yahoo_imap_server, pdts_email,email_password, new_active_user_password);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the  “Add and manage DTS users” privilege
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 10. Navigate to Users page
		home.click(Xpath.LINK_USERS);
		/*
		 * VP: Verify that the “Add New User” link is displayed while Users list displays only Partner users
		 */
		Assert.assertTrue(home.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(home.verifyColumnNotContainValue(UsersList.USER_TABLE, "Company", dts_company_name));
		// 11. Select a user from users list
		home.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that the “Suspend” link is displayed but the “Restore” link is not
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.SUSPEND));
		Assert.assertFalse(home.isElementPresent(UserMgmt.RESTORE));
		// 12. Click “Suspend” link
		home.click(UserMgmt.SUSPEND);
		// 13. Click “Suspend” button on suspend confirmation dialog
		home.selectConfirmationDialogOption("Suspend");
		/*
		 * Verify that the portal redirects to Users list page and the suspended user is not displayed in Active User list but in Suspended users list
		 */
		Assert.assertEquals(home.existsElement(UsersList.getListElementUser()).getResult(), "Pass");
		home.selectFilterByName(UsersList.FILTER_USER, "Suspended");
		// Check if PDPP-1191 found
		if(!home.checkUserExistByEmail(data.get("email"))){
			result.addErrorLog("PDPP-1191: 080 User List: the suspended users are not displayed in suspended list with all companies filtering");
			Assert.assertTrue(false);
		}
		Assert.assertTrue(home.checkUserExistByEmail(data.get("email")));
		// Delete user
		home.dtsSelectUserByEmail(data.get("email"));
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can only suspend DTS users when the “Add and manage users” privilege is disabled and “Add and manage DTS users” privilege is enabled
	 */
	@Test
	public void TCPUSR_18() {
		result.addLog("ID : TCPUSR_18 : Verify that  DTS user can only suspend DTS users when the “Add and manage users” privilege is disabled and “Add and manage DTS users” privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage DTS users” privilege
			7. Disable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the “Add New User” link is displayed while Users list displays only DTS users
			11. Select a user from users list
			VP: Verify that the “Suspend” link is displayed but the “Restore” link is not
			12. Click “Suspend” link
			13. Click “Suspend” button on suspend confirmation dialog
		 */
		/*
		 * Pre-condition: Create new DTS user
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
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage DTS users” privilege
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Disable the “Add and manage users” privilege
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 10. Navigate to Users page
		home.click(Xpath.LINK_USERS);
		/*
		 * VP: Verify that the “Add New User” link is displayed while Users list displays only DTS users
		 */
		Assert.assertTrue(home.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(home.verifyValueColumn(UsersList.USER_TABLE, "Company", dts_company_name));
		// 11. Select a user from users list
		home.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that the “Suspend” link is displayed but the “Restore” link is not
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.SUSPEND));
		Assert.assertFalse(home.isElementPresent(UserMgmt.RESTORE));
		// 12. Click “Suspend” link
		home.click(UserMgmt.SUSPEND);
		// 13. Click “Suspend” button on suspend confirmation dialog
		home.switchWindowClickOption("Suspend");
		/*
		 * Verify that the portal redirects to Users list page and the suspended
		 * user is not displayed in Active User list but in Suspended users list
		 */
		Assert.assertEquals(home.existsElement(UsersList.getListElementUser()).getResult(), "Pass");
		home.selectFilterByName(UsersList.FILTER_USER, "Suspended");
		Assert.assertTrue(home.checkUserExistByEmail(data.get("email")));
		// Delete user
		home.dtsSelectUserByEmail(data.get("email"));
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can suspend both DTS and Partner users when both “Add and manage users” and “Add and manage DTS users” privileges are enabled
	 */
	@Test
	public void TCPUSR_19() {
		result.addLog("ID : TCPUSR_19 : Verify that  DTS user can suspend both DTS and Partner users when both “Add and manage users” and “Add and manage DTS users” privileges are enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage DTS users” privilege
			7. Enable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the “Add New User” link is displayed while Users list displays both DTS and Partner users
			11. Select a Partner user from users list
			VP: Verify that the “Suspend” link is displayed but the “Restore” link is not
			12. Click “Suspend” link
			13. Click “Suspend” button on suspend confirmation dialog
			VP: Verify that the portal redirects to Users list page and the suspended user is not displayed in Active User list but in Suspended users list
			14. Repeat from step 10 to step 13 with DTS user
		 */
		/*
		 * Pre-condition: Create new DTS user and Partner user
		 */
		// Get email message count before create user
		int messageCountPartner = MailUtil.getEmailMessageCount(yahoo_imap_server, pdts_email, email_password);
		int messageCountDTS = MailUtil.getEmailMessageCount(yahoo_imap_server, dts_email, email_password);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Delete user if exist
		home.deleteUserByEmail(pdts_email);
		home.deleteUserByEmail(dts_email);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create DTS user
		Hashtable<String,String> dataDts = TestData.dtsUser();
		dataDts.put("email", dts_email);
		home.addUser(AddUser.getDTSUser(), dataDts);
		// Active DTS user
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, dts_email, email_password, messageCountDTS);
		home.activeNewUserViaEmail(yahoo_imap_server, dts_email, email_password, new_active_user_password);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create Partner user
		Hashtable<String,String> dataPartner = TestData.partnerUser();
		dataPartner.put("email", pdts_email);
		home.addUser(AddUser.getDTSUser(), dataPartner);
		// Active Partner user
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email, email_password, messageCountPartner);
		home.activeNewUserViaEmail(yahoo_imap_server, pdts_email, email_password, new_active_user_password);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Add and manage DTS users” privilege
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 10. Navigate to Users page
		home.click(Xpath.LINK_USERS);
		ArrayList<String> companyColumn = home.getDataByHeaderText(UsersList.USER_TABLE, "Company");
		/*
		 * VP: Verify that the “Add New User” link is displayed while Users list
		 * displays both DTS and Partner users
		 */
		Assert.assertTrue(home.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(DTSUtil.checkListContain(companyColumn, dts_company_name) && DTSUtil.checkListContain(companyColumn, partner_company_name));
		// 11. Select a Partner user from users list
		home.dtsSelectUserByEmail(dataPartner.get("email"));
		/*
		 * VP: Verify that the “Suspend” link is displayed but the “Restore” link is not
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.SUSPEND));
		Assert.assertFalse(home.isElementPresent(UserMgmt.RESTORE));
		// 12. Click “Suspend” link
		home.click(UserMgmt.SUSPEND);
		// 13. Click “Suspend” button on suspend confirmation dialog
		home.selectConfirmationDialogOption("Suspend");
		/*
		 * VP: Verify that the portal redirects to Users list page and the
		 * suspended user is not displayed in Active User list but in Suspended
		 * users list
		 */
		Assert.assertEquals(home.existsElement(UsersList.getListElementUser()).getResult(), "Pass");
		home.selectFilterByName(UsersList.FILTER_USER, "Suspended");
		// Check if PDPP-1191 found
		if(!home.checkUserExistByEmail(dataPartner.get("email"))){
			result.addErrorLog("PDPP-1191: 080 User List: the suspended users are not displayed in suspended list with all companies filtering");
			Assert.assertTrue(false);
		}
		Assert.assertTrue(home.checkUserExistByEmail(dataPartner.get("email")));
		// Delete user
		home.dtsSelectUserByEmail(dataPartner.get("email"));
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
		// 14. Repeat from step 10 to step 13 with DTS user
		home.dtsSelectUserByEmail(dataDts.get("email"));
		// Click “Suspend” link
		home.click(UserMgmt.SUSPEND);
		// Click “Suspend” button on suspend confirmation dialog
		home.switchWindowClickOption("Suspend");
		/*
		 * VP: Verify that the portal redirects to Users list page and the
		 * suspended user is not displayed in Active User list but in Suspended
		 * users list
		 */
		Assert.assertEquals(home.existsElement(UsersList.getListElementUser()).getResult(), "Pass");
		home.selectFilterByName(UsersList.FILTER_USER, "Suspended");
		Assert.assertTrue(home.checkUserExistByEmail(dataDts.get("email")));
		// Delete user
		home.dtsSelectUserByEmail(dataDts.get("email"));
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can only restore Partner users when the “Add and manage users” privilege is enabled and “Add and manage DTS users” privilege is disabled
	 */
	@Test
	public void TCPUSR_20() {
		result.addLog("ID : TCPUSR_20 : Verify that DTS user can only restore Partner users when the “Add and manage users” privilege is enabled and “Add and manage DTS users” privilege is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the  “Add and manage DTS users” privilege
			7. Enable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the “Add New User” link is displayed while Users list displays only Partner users
			11. Select a user from suspended users list
			VP: Verify that the “Restore” link is displayed but the “suspend” link is not
			12. Click “Restore” link
		 */
		/*
		 * Pre-condition: Create new suspended Partner user
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
		home.dtsSelectUserByEmail(data.get("email"));
		// Suspend user
		home.click(UserMgmt.SUSPEND);
		home.selectConfirmationDialogOption("Suspend");
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the “Add and manage DTS users” privilege
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE,	Privileges.Add_manage_DTS_user);
		// 7. Enable the “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 10. Navigate to Users page
		home.click(Xpath.LINK_USERS);
		/*
		 * VP: Verify that the “Add New User” link is displayed while Users list
		 * displays only Partner users
		 */
		Assert.assertTrue(home.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(home.verifyColumnNotContainValue(UsersList.USER_TABLE, "Company", dts_company_name));
		// 11. Select a user from suspended users list
		home.selectFilterByName(UsersList.FILTER_USER, "Suspended");
		// Check if PDPP-1191 found
		if(!home.checkUserExistByEmail(data.get("email"))){
			result.addErrorLog("PDPP-1191: 080 User List: the suspended users are not displayed in suspended list with all companies filtering");
			Assert.assertTrue(false);
		}
		home.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that the “Restore” link is displayed but the “suspend” link is not
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.RESTORE));
		Assert.assertFalse(home.isElementPresent(UserMgmt.SUSPEND));
		// 12. Click “Restore” link
		home.click(UserMgmt.RESTORE);
		/*
		 * Verify that the portal redirects to Users list page and the restored user is displayed in Active User list
		 */
		Assert.assertEquals(home.existsElement(UsersList.getListElementUser()).getResult(), "Pass");
		Assert.assertTrue(home.checkUserExistByEmail(data.get("email")));
		// Delete user
		home.dtsSelectUserByEmail(data.get("email"));
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can only restore DTS users when the “Add and manage users” privilege is disabled and “Add and manage DTS users” privilege is enabled
	 */
	@Test
	public void TCPUSR_21() {
		result.addLog("ID : TCPUSR_21 : Verify that  DTS user can only restore DTS users when the “Add and manage users” privilege is disabled and “Add and manage DTS users” privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage DTS users” privilege
			7. Disable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the “Add New User” link is displayed while Users list displays only DTS users
			11. Select a user from suspended users list
			VP: Verify that the “Restore” link is displayed but the Suspend” link is not
			12. Click “Restore” link
		 */
		/*
		 * Pre-condition: Create new suspended DTS user
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
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage DTS users” privilege
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Disable the “Add and manage users” privilege
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 10. Navigate to Users page
		home.click(Xpath.LINK_USERS);
		/*
		 * VP: Verify that the “Add New User” link is displayed while Users list displays only DTS users
		 */
		Assert.assertTrue(home.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(home.verifyValueColumn(UsersList.USER_TABLE, "Company", dts_company_name));
		// 11. Select a user from suspended users list
		home.selectFilterByName(UsersList.FILTER_USER, "Suspended");
		// Check if PDPP-1191 found
		if(!home.checkUserExistByEmail(data.get("email"))){
			result.addErrorLog("PDPP-1191: 080 User List: the suspended users are not displayed in suspended list with all companies filtering");
			Assert.assertTrue(false);
		}
		home.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that the “Restore” link is displayed but the “suspend”
		 * link is not
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.RESTORE));
		Assert.assertFalse(home.isElementPresent(UserMgmt.SUSPEND));
		// 12. Click “Restore” link
		home.click(UserMgmt.RESTORE);
		/*
		 * Verify that the portal redirects to Users list page and the restored user is displayed in Active User list
		 */
		Assert.assertEquals(home.existsElement(UsersList.getListElementUser()).getResult(), "Pass");
		Assert.assertTrue(home.checkUserExistByEmail(data.get("email")));
		// Delete user
		home.dtsSelectUserByEmail(data.get("email"));
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can restore both DTS and Partner users when both “Add and manage users” and “Add and manage DTS users” privileges are enabled
	 */
	@Test
	public void TCPUSR_22() {
		result.addLog("ID : TCPUSR_22 : Verify that  DTS user can restore both DTS and Partner users when both “Add and manage users” and “Add and manage DTS users” privileges are enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage DTS users” privilege
			7. Enable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the “Add New User” link is displayed while Users list displays both DTS and Partner users
			11. Select a Partner user from users list
			VP: Verify that the “Restore” link is displayed but the “Suspend” link is not
			12. Click “Restore” link
			VP: Verify that the portal redirects to Users list page and the restored user is displayed in Active User list
			13. Repeat from step 10 to step 12 with DTS user
		 */
		/*
		 * Pre-condition: Create new suspended DTS user and Partner user
		 */
		// Get email message count before create user
		int messageCountPartner = MailUtil.getEmailMessageCount(yahoo_imap_server, pdts_email, email_password);
		int messageCountDTS = MailUtil.getEmailMessageCount(yahoo_imap_server, dts_email, email_password);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Delete user if exist
		home.deleteUserByEmail(pdts_email);
		home.deleteUserByEmail(dts_email);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create DTS user
		Hashtable<String,String> dataDts = TestData.dtsUser();
		dataDts.put("email", dts_email);
		home.addUser(AddUser.getDTSUser(), dataDts);
		// Active DTS user
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, dts_email, email_password, messageCountDTS);
		home.activeNewUserViaEmail(yahoo_imap_server, dts_email, email_password, new_active_user_password);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select user above
		home.dtsSelectUserByEmail(dataDts.get("email"));
		// Suspend DTS user
		home.click(UserMgmt.SUSPEND);
		home.selectConfirmationDialogOption("Suspend");
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create Partner user
		Hashtable<String,String> dataPartner = TestData.partnerUser();
		dataPartner.put("email", pdts_email);
		home.addUser(AddUser.getDTSUser(), dataPartner);
		// Active Partner user
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email, email_password, messageCountPartner);
		home.activeNewUserViaEmail(yahoo_imap_server, pdts_email, email_password, new_active_user_password);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select user above
		home.dtsSelectUserByEmail(dataPartner.get("email"));
		// Suspend partner user
		home.click(UserMgmt.SUSPEND);
		home.selectConfirmationDialogOption("Suspend");
		/*
		 * ********************************************************************
		 */
		// 3. Navigate to "Users" page
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Add and manage DTS users” privilege
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 10. Navigate to Users page
		home.click(Xpath.LINK_USERS);
		ArrayList<String> companyColumn = home.getDataByHeaderText(UsersList.USER_TABLE, "Company");
		/*
		 * VP: Verify that the “Add New User” link is displayed while Users list
		 * displays both DTS and Partner users
		 */
		Assert.assertTrue(home.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(DTSUtil.checkListContain(companyColumn, dts_company_name) && DTSUtil.checkListContain(companyColumn, partner_company_name));
		// 11. Select a Partner user from users list
		home.dtsSelectUserByEmail(dataPartner.get("email"));
		/*
		 * VP: Verify that the “Restore” link is displayed but the “Suspend” link is not
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.RESTORE));
		Assert.assertFalse(home.isElementPresent(UserMgmt.SUSPEND));
		// 12. Click “Restore” link
		home.click(UserMgmt.RESTORE);
		/*
		 * VP: Verify that the portal redirects to Users list page and the restored user is displayed in Active User list
		 */
		Assert.assertEquals(home.existsElement(UsersList.getListElementUser()).getResult(), "Pass");
		Assert.assertTrue(home.checkUserExistByEmail(dataPartner.get("email")));
		// Delete Partner user
		home.dtsSelectUserByEmail(dataPartner.get("email"));
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
		// 13. Repeat from step 10 to step 12 with DTS user
		// Select a DTS user from users list
		home.dtsSelectUserByEmail(dataDts.get("email"));
		// Click “Restore” link
		home.click(UserMgmt.RESTORE);
		/*
		 * Verify that the portal redirects to Users list page and the restored user is displayed in Active User list
		 */
		Assert.assertEquals(home.existsElement(UsersList.getListElementUser()).getResult(), "Pass");
		Assert.assertTrue(home.checkUserExistByEmail(dataDts.get("email")));
		// Delete DTS user
		home.dtsSelectUserByEmail(dataDts.get("email"));
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that DTS user can only edit Partner user's privilege when the “Add and manage users” privilege is enabled and “Add and manage DTS users” privilege is disabled
	 */
	@Test
	public void TCPUSR_23() {
		result.addLog("ID : TCPUSR_23 : Verify that  DTS user can only edit Partner user's privilege when the “Add and manage users” privilege is enabled and “Add and manage DTS users” privilege is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the  “Add and manage DTS users” privilege
			7. Enable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the “Add New User” link is displayed while Users list displays only Partner users
			11. Select a user from users list
			VP: Verify that only “Site Privilege” module is displayed
			12. Uncheck some privilege
			13. Click “Save” link
		 */
		/*
		 * Pre-condition: Create new Partner user
		 */
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.partnerUser();
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the  “Add and manage DTS users” privilege
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE,Privileges.Add_manage_DTS_user);
		// 7. Enable the “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE,Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 10. Navigate to Users page
		home.click(Xpath.LINK_USERS);
		/*
		 * VP: Verify that the “Add New User” link is displayed while Users list displays only Partner users
		 */
		Assert.assertTrue(home.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(home.verifyColumnNotContainValue(UsersList.USER_TABLE, "Company", dts_company_name));
		// 11. Select a user from users list
		home.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that only “Site Privilege” module is displayed
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.SITE_PRIVILEGES_TABLE));
		// Check if PDPP-894 found
		if(home.isElementPresent(UserMgmt.DTS_SITE_PRIVILEGES_TABLE)){
			result.addErrorLog("PDPP-894: 090D User Info: The 'DTS Site Privilege' table is displayed in the detail info page of partner user");
			Assert.assertTrue(false);
		}
		Assert.assertFalse(home.isElementPresent(UserMgmt.DTS_SITE_PRIVILEGES_TABLE));
		// 12. Uncheck some privilege
		// 13. Click “Save” link
		home.click(UserMgmt.EDIT);
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		/*
		 * Verify that the portal redirects to User's info page and only display the checked privilege of user
		 */
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
		ArrayList<String> privileges = home.getPrivileges(UserMgmt.SITE_PRIVILEGES_TABLE);
		Assert.assertFalse(DTSUtil.checkListContain(privileges,Privileges.Add_and_manage_products));
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can only edit DTS user's privileges when the “Add and manage users” privilege is disabled and “Add and manage DTS users” privilege is enabled
	 */
	@Test
	public void TCPUSR_24() {
		result.addLog("ID : TCPUSR_24 : Verify that  DTS user can only edit DTS user's privileges when the “Add and manage users” privilege is disabled and “Add and manage DTS users” privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage DTS users” privilege
			7. Disable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the “Add New User” link is displayed while Users list displays only DTS users
			11. Select a user from users list
			VP: Verify that both “Site Privilege” and “DTS Site Privilege” modules are displayed
			12. Uncheck some privilege
			13. Click “Save” link
		 */
		/*
		 * Pre-condition: Create new Partner user
		 */
		// Navigate to DTS page
		home.click(Xpath.LINK_USERS);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.dtsUser();
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage DTS users” privilege
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE,Privileges.Add_manage_DTS_user);
		// 7. Disable the “Add and manage users” privilege
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE,Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 10. Navigate to Users page
		home.click(Xpath.LINK_USERS);
		/*
		 * VP: Verify that the “Add New User” link is displayed while Users list displays only DTS users
		 */
		Assert.assertTrue(home.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(home.verifyValueColumn(UsersList.USER_TABLE, "Company", dts_company_name));
		// 11. Select a user from users list
		home.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that both “Site Privilege” and “DTS Site Privilege” modules are displayed
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.SITE_PRIVILEGES_TABLE));
		Assert.assertTrue(home.isElementPresent(UserMgmt.DTS_SITE_PRIVILEGES_TABLE));
		// 12. Uncheck some privilege
		// 13. Click “Save” link
		home.click(UserMgmt.EDIT);
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		home.click(AddUser.SAVE);
		/*
		 * Verify that the portal redirects to User's info page and only display the checked privilege of user
		 */
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
		ArrayList<String> privileges = home.getPrivileges(UserMgmt.DTS_SITE_PRIVILEGES_TABLE);
		Assert.assertFalse(DTSUtil.checkListContain(privileges,Privileges.Add_manage_DTS_user));
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can edit both DTS and Partner user's privileges when both “Add and manage users” and “Add and manage DTS users” privileges are enabled
	 */
	@Test
	public void TCPUSR_25() {
		result.addLog("ID : TCPUSR_25 : Verify that  DTS user can edit both DTS and Partner user's privileges when both “Add and manage users” and “Add and manage DTS users” privileges are enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage DTS users” privilege
			7. Enable the “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the “Add New User” link is displayed while Users list displays both DTS and Partner users
			11. Select a DTS user from users list
			VP: Verify that both “Site Privilege” and “DTS Site Privilege” modules are displayed
			12. Uncheck some privilege
			13. Click “Save” link
			VP: Verify that the portal redirects to User's info page and only display the checked privilege of DTS user
			14. Repeat from step 10 to step 13 with Partner user
		 */
		/*
		 * Pre-condition: Create new DTS user and Partner user
		 */
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> dataDts = TestData.dtsUser();
		home.addUser(AddUser.getDTSUser(), dataDts);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> dataPartner = TestData.partnerUser();
		home.addUser(AddUser.getDTSUser(), dataPartner);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Add and manage DTS users” privilege
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE,Privileges.Add_manage_DTS_user);
		// 7. Enable the “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE,Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 10. Navigate to Users page
		home.click(Xpath.LINK_USERS);
		ArrayList<String> companyColumn = home.getDataByHeaderText(UsersList.USER_TABLE, "Company");
		/*
		 * VP: Verify that the “Add New User” link is displayed while Users list
		 * displays both DTS and Partner users
		 */
		Assert.assertTrue(home.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(DTSUtil.checkListContain(companyColumn, dts_company_name) && DTSUtil.checkListContain(companyColumn, partner_company_name));
		// 11. Select a DTS user from users list
		home.dtsSelectUserByEmail(dataDts.get("email"));
		/*
		 * VP: Veriy that both “Site Privilege” and “DTS Site Privilege” modules are displayed
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.SITE_PRIVILEGES_TABLE));
		Assert.assertTrue(home.isElementPresent(UserMgmt.DTS_SITE_PRIVILEGES_TABLE));
		// 12. Uncheck some privilege
		// 13. Click “Save” link
		home.click(UserMgmt.EDIT);
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		home.click(AddUser.SAVE);
		/*
		 * VP: Verify that the portal redirects to User's info page and only display the checked privilege of DTS user
		 */
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
		ArrayList<String> privileges = home.getPrivileges(UserMgmt.DTS_SITE_PRIVILEGES_TABLE);
		Assert.assertFalse(DTSUtil.checkListContain(privileges,Privileges.Add_manage_DTS_user));
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
		// 14. Repeat from step 10 to step 13 with Partner user
		// Select a Partner user from users list
		home.dtsSelectUserByEmail(dataPartner.get("email"));
		// Uncheck some privilege
		// Click “Save” link
		home.click(UserMgmt.EDIT);
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		/*
		 * Verify that the portal redirects to User's info page and only display the checked privilege of user
		 */
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
		privileges = home.getPrivileges(UserMgmt.SITE_PRIVILEGES_TABLE);
		Assert.assertFalse(DTSUtil.checkListContain(privileges,Privileges.Add_and_manage_products));
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);		
	}
	
	/*	
	 * Verify that the DTS users can edit their own info including privileges notification but not for Site privileges even when the “Add and manage users” privilege is enabled or disabled
	 */
	@Test
	public void TCPUSR_27() {
		result.addLog("ID : TCPUSR_27 : Verify that  DTS user can edit both DTS and Partner user's privileges when both “Add and manage users” and “Add and manage DTS users” privileges are enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage users” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			9. Click on DTS user's email on top right corner
			10. Select “User Account” item
			VP: Verify that the “Edit” link is displayed in  “Account Information” page
			11. Notice for the current privilege of user
			12. Click “Edit” link
			VP: Verify that the “Edit Account” page is displayed, the given name, family name, title, email, country code, the phone fields and the Site privilege notification are editable. But with the Site site privileges section, only display what privileges are displayed in Account info page and all of them are un-editable.
			13. Log out DTS portal
			14. Repeat from step 2 to 5
			15. Disable the  “Add and manage users” privilege
			16. Repeat from step 7 to 12
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner user successfully
		home.login(dtsUser, dtsPass);
		// 9. Click on DTS user's email on top right corner
		home.click(Xpath.lbLogout);
		// 10. Select “User Account” item
		home.click(Xpath.loginAccount);
		/*
		 * VP: Verify that the “Edit” link is displayed in  “Account Information” page
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.EDIT));
		// 11. Notice for the current privilege of user
		// 12. Click “Edit” link
		home.click(UserMgmt.EDIT);
		/*
		 * VP: Verify that the “Edit Account” page is displayed, the given name,
		 * family name, title, email, country code, the phone fields and the
		 * Site privilege notification are editable. But with the Site site
		 * privileges section, only display what privileges are displayed in
		 * Account info page and all of them are un-editable.
		 */
		Assert.assertTrue(home.isElementEditable(AddUser.getUserInfo()));
		Assert.assertTrue(home.isAllNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE));
		Assert.assertTrue(home.isAllPrivilegeDisable(AddUser.PRIVILEGES_TABLE));
		// 13. Log out DTS portal
		home.logout();
		// 14. Repeat from step 2 to 5
		home.login(superUsername, superUserpassword);
		// Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 15. Disable the  “Add and manage users” privilege
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 16. Repeat from step 7 to 12
		home.logout();
		// Log into DTS portal as above partner user successfully
		home.login(dtsUser, dtsPass);
		// Click on DTS user's email on top right corner
		home.click(Xpath.lbLogout);
		// Select “User Account” item
		home.click(Xpath.loginAccount);
		// Click “Edit” link
		home.click(UserMgmt.EDIT);
		/*
		 * Verify that the “Edit Account” page is displayed, the given name,
		 * family name, title, email, country code, the phone fields and the
		 * Site privilege notification are editable. But with the Site site
		 * privileges section, only display what privileges are displayed in
		 * Account info page and all of them are un-editable
		 */
		Assert.assertTrue(home.isElementEditable(AddUser.getUserInfo()));
		Assert.assertTrue(home.isAllNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE));
		Assert.assertTrue(home.isAllPrivilegeDisable(AddUser.PRIVILEGES_TABLE));
	}
	
	/*	
	 * Verify that the DTS users can edit their own info including privileges notification but not for DTS Site privileges even when the “Add and manage DTS users” privilege is enabled or disabled
	 */
	@Test
	public void TCPUSR_28() {
		result.addLog("ID : TCPUSR_28 : Verify that the DTS users can edit their own info including privileges notification but not for DTS Site privileges even when the “Add and manage DTS users” privilege is enabled or disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage DTS users” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			9. Click on DTS user's email on top right corner
			10. Select “User Account” item
			VP: Verify that the “Edit” link is displayed in  “Account Information” page
			11. Notice for the current privilege of user
			12. Click “Edit” link
			VP: Verify that the “Edit Account” page is displayed, the given name, family name, title, email, country code, the phone fields and the DTS Site privilege notification are editable. But with the DTS Site site privileges section, only display what privileges are displayed in Account info page and all of them are un-editable.
			13. Log out DTS portal
			14. Repeat from step 2 to 5
			15. Disable the  “Add and manage DTS users” privilege
			16. Repead from step 7 to 12
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage DTS users” privilege
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner user successfully
		home.login(dtsUser, dtsPass);
		// 9. Click on DTS user's email on top right corner
		home.click(Xpath.lbLogout);
		// 10. Select “User Account” item
		home.click(Xpath.loginAccount);
		/*
		 * VP: Verify that the “Edit” link is displayed in  “Account Information” page
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.EDIT));
		// 11. Notice for the current privilege of user
		// 12. Click “Edit” link
		home.click(UserMgmt.EDIT);
		/*
		 * VP: Verify that the “Edit Account” page is displayed, the given name,
		 * family name, title, email, country code, the phone fields and the DTS
		 * Site privilege notification are editable. But with the DTS Site site
		 * privileges section, only display what privileges are displayed in
		 * Account info page and all of them are un-editable.
		 */
		Assert.assertTrue(home.isElementEditable(AddUser.getUserInfo()));
		Assert.assertTrue(home.isAllNotificationCheckboxEnable(AddUser.DTS_PRIVILEGES_TABLE));
		Assert.assertTrue(home.isAllPrivilegeDisable(AddUser.DTS_PRIVILEGES_TABLE));
		// 13. Log out DTS portal
		home.logout();
		// 14. Repeat from step 2 to 5
		home.login(superUsername, superUserpassword);
		// Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 15. Disable the  “Add and manage DTS users” privilege
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		home.click(AddUser.SAVE);
		// 16. Repeat from step 7 to 12
		home.logout();
		// Log into DTS portal as above partner user successfully
		home.login(dtsUser, dtsPass);
		// Click on DTS user's email on top right corner
		home.click(Xpath.lbLogout);
		// Select “User Account” item
		home.click(Xpath.loginAccount);
		// Click “Edit” link
		home.click(UserMgmt.EDIT);
		/*
		 * VP: Verify that the “Edit Account” page is displayed, the given name,
		 * family name, title, email, country code, the phone fields and the DTS
		 * Site privilege notification are editable. But with the DTS Site site
		 * privileges section, only display what privileges are displayed in
		 * Account info page and all of them are un-editable.
		 */
		Assert.assertTrue(home.isElementEditable(AddUser.getUserInfo()));
		Assert.assertTrue(home.isAllNotificationCheckboxEnable(AddUser.DTS_PRIVILEGES_TABLE));
		Assert.assertTrue(home.isAllPrivilegeDisable(AddUser.DTS_PRIVILEGES_TABLE));
	}
	
	/*	
	 * Verify that the DTS users can edit their own info including privileges notification but not for Site or DTS Site privilege  even when both “Add and manage DTS users” and “Add and manage DTS users” privileges are enabled or disabled
	 */
	@Test
	public void TCPUSR_29() {
		result.addLog("ID : TCPUSR_29 : Verify that the DTS users can edit their own info including privileges notification but not for Site or DTS Site privilege  even when both “Add and manage DTS users” and “Add and manage DTS users” privileges are enabled or disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage DTS users” privilege
			7. Enable the  “Add and manage users” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above partner user successfully
			10. Click on DTS user's email on top right corner
			11. Select “User Account” item
			VP: Verify that the “Edit” link is displayed in  “Account Information” page
			12. Notice for the current privilege of user
			13. Click “Edit” link
			VP: Verify that the “Edit Account” page is displayed, the given name, family name, title, email, country code, the phone fields and the Site or DTS Site privilege notification are editable. But with the Site and DTS Site site privileges section, only display what privileges are displayed in Account info page and all of them are un-editable.
			14. Log out DTS portal
			15. Repeat from step 2 to 5
			16. Disable the  “Add and manage DTS users” privilege
			17. Disable the  “Add and manage users” privilege
			18. Repeat from step 8 to 13
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Add and manage DTS users” privilege
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above partner user successfully
		home.login(dtsUser, dtsPass);
		// 10. Click on DTS user's email on top right corner
		home.click(Xpath.lbLogout);
		// 11. Select “User Account” item
		home.click(Xpath.loginAccount);
		/*
		 * VP: Verify that the “Edit” link is displayed in  “Account Information” page
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.EDIT));
		// 12. Notice for the current privilege of user
		// 13. Click “Edit” link
		home.click(UserMgmt.EDIT);
		/*
		 * VP: Verify that the “Edit Account” page is displayed, the given name,
		 * family name, title, email, country code, the phone fields and the
		 * Site or DTS Site privilege notification are editable. But with the
		 * Site and DTS Site site privileges section, only display what
		 * privileges are displayed in Account info page and all of them are
		 * un-editable.
		 */
		Assert.assertTrue(home.isElementEditable(AddUser.getUserInfo()));
		Assert.assertTrue(home.isAllNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE));
		Assert.assertTrue(home.isAllPrivilegeDisable(AddUser.PRIVILEGES_TABLE));
		Assert.assertTrue(home.isAllNotificationCheckboxEnable(AddUser.DTS_PRIVILEGES_TABLE));
		Assert.assertTrue(home.isAllPrivilegeDisable(AddUser.DTS_PRIVILEGES_TABLE));
		// 14. Log out DTS portal
		home.logout();
		// 15. Repeat from step 2 to 5
		home.login(superUsername, superUserpassword);
		// Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 16. Disable the  “Add and manage DTS users” privilege
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 17. Disable the  “Add and manage users” privilege
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 18. Repeat from step 8 to 13
		home.logout();
		// Log into DTS portal as above partner user successfully
		home.login(dtsUser, dtsPass);
		// Click on DTS user's email on top right corner
		home.click(Xpath.lbLogout);
		// Select “User Account” item
		home.click(Xpath.loginAccount);
		// Click “Edit” link
		home.click(UserMgmt.EDIT);
		/*
		 * VP: Verify that the “Edit Account” page is displayed, the given name,
		 * family name, title, email, country code, the phone fields and the
		 * Site or DTS Site privilege notification are editable. But with the
		 * Site and DTS Site site privileges section, only display what
		 * privileges are displayed in Account info page and all of them are
		 * un-editable.
		 */
		Assert.assertTrue(home.isElementEditable(AddUser.getUserInfo()));
		Assert.assertTrue(home.isAllNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE));
		Assert.assertTrue(home.isAllPrivilegeDisable(AddUser.PRIVILEGES_TABLE));
		Assert.assertTrue(home.isAllNotificationCheckboxEnable(AddUser.DTS_PRIVILEGES_TABLE));
		Assert.assertTrue(home.isAllPrivilegeDisable(AddUser.DTS_PRIVILEGES_TABLE));
	}
	
	/*	
	 * Verify that user is unable to change his own email address when both "Add and manage user" and "Add and manage DTS user" privilege are disabled.
	 */
	@Test
	public void TCPUSR_30() {
		result.addLog("ID : TCPUSR_30 : Verify that user is unable to change his own email address when both 'Add and manage user' and 'Add and manage DTS user' privilege are disabled.");
		/*
			Pre-condition: The "Add and Manage Users" and "Add and Manage DTS Users" privilege are disabled.

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			VP: Verify that the User page is not displayed
			3. Click user's email label on top right corner
			VP: Verify that the user account popup is displayed
			4. Click "User Account" item
			5. Click "Edit" link
			VP: Verify that the email address field is disabled.
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the “Add and manage DTS users” privilege
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the “Add and manage users” privilege
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above partner user successfully
		home.login(dtsUser, dtsPass);
		//VP: Verify that the users tab is not displayed
		Assert.assertFalse(home.isElementPresent(Xpath.LINK_USERS));
		// 10. Click on DTS user's email on top right corner
		home.click(Xpath.lbLogout);
		// 11. Select “User Account” item
		home.click(Xpath.loginAccount);
		// 12. Notice for the current privilege of user
		// 13. Click “Edit” link
		home.click(UserMgmt.EDIT);
		/*
			VP: Verify that the email address field is disabled
		 */
		Assert.assertFalse(home.isElementEditable(AddUser.EMAIL));
	}
	
	/*	
	 * Verify that user is able to change his own email address when "Add and manage user" privilege is enabled but "Add and manage DTS" user privilege is disabled
	 */
	@Test
	public void TCPUSR_31() {
		result.addLog("ID : TCPUSR_31 : Verify that user is able to change his own email address when 'Add and manage DTS user' privilege is disabled but 'Add and manage user' user privilege is enabled");
		/*
			Pre-condition: "Add and manage user" privilege is enabled but "Add and manage DTS" user privilege is disabled

			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to Users page
			4. Select a DTS account from users list
			5. Enable the "Add and manage user" privilege and disable the "Add and manage DTS user" privilege of DTS user above successfully
			6. Log into DTS portal under above DTS user successfully
			7. Click user's email label on top right corner
			8. Click "User Account" item
			9. Click "Edit" link
			VP: Verify that the email address field is enabled
			10. Change user own email address to another
			11. Click "Save" link
			VP: Verify that user is kicked out the portal after the change is saved
			12. Log into DTS portal with new email address
			VP: User is unable to log into DTS portal with old email address
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the “Add and manage DTS users” privilege
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above partner user successfully
		home.login(dtsUser, dtsPass);
		// 10. Click on DTS user's email on top right corner
		home.click(Xpath.lbLogout);
		// 11. Select “User Account” item
		home.click(Xpath.loginAccount);
		// 12. Notice for the current privilege of user
		// 13. Click “Edit” link
		home.click(UserMgmt.EDIT);
		/*
			VP: Verify that the email address field is enabled
		 */
		Assert.assertTrue(home.isElementEditable(AddUser.EMAIL));
		//10. Change user own email address to another
		//11. Click "Save" link
		String newemail="dtsauto1@mailinator.com";
		home.editData(AddUser.EMAIL, newemail);
		home.click(AddUser.SAVE);
		
		//VP: Verify that user is kicked out the portal after the change is saved
		Assert.assertTrue(home.isElementPresent(LoginPage.SIGN_IN));
		//12. Log into DTS portal with old email address
		home.login(dtsUser, dtsPass);
		//VP: User is unable to log into DTS portal with old email address
		Assert.assertTrue(home.isElementPresent(LoginPage.SIGN_IN));
		
		
		//Teardown
		home.login(superUsername, superUserpassword);
		// Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// Select a DTS user from the Users table
		home.dtsSelectUserByEmail(newemail);
		// Click “Edit” link
		home.click(UserMgmt.EDIT);
		// enable the  “Add and manage DTS users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		//change email address back to normal
		home.editData(AddUser.EMAIL, dtsUser);
		home.click(AddUser.SAVE);
	}
	
	
	/*	
	 * Verify that user is able to change his own email address when "Add and manage user" privilege is disabled but "Add and manage DTS" user privilege is enabled
	 */
	@Test
	public void TCPUSR_32() {
		result.addLog("ID : TCPUSR_32 : Verify that user is able to change his own email address when 'Add and manage user' privilege is disabled but 'Add and manage DTS' user privilege is enabled");
		/*
			Pre-condition: "Add and manage user" privilege is disabled but "Add and manage DTS" user privilege is ensabled

			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to Users page
			4. Select a DTS account from users list
			5. Disable the "Add and manage user" privilege and Enable the "Add and manage DTS user" privilege of DTS user above successfully
			6. Log into DTS portal under above DTS user successfully
			7. Click user's email label on top right corner
			8. Click "User Account" item
			9. Click "Edit" link
			VP: Verify that the email address field is enabled
			10. Change user own email address to another
			11. Click "Save" link
			VP: Verify that user is kicked out the portal after the change is saved
			12. Log into DTS portal with old email address
			VP: User is unable to log into DTS portal with old email address
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Add and manage DTS users” privilege
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Disable the “Add and manage users” privilege
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above partner user successfully
		home.login(dtsUser, dtsPass);
		// 10. Click on DTS user's email on top right corner
		home.click(Xpath.lbLogout);
		// 11. Select “User Account” item
		home.click(Xpath.loginAccount);
		// 12. Notice for the current privilege of user
		// 13. Click “Edit” link
		home.click(UserMgmt.EDIT);
		/*
			VP: Verify that the email address field is enabled
		 */
		Assert.assertTrue(home.isElementEditable(AddUser.EMAIL));
		//10. Change user own email address to another
		//11. Click "Save" link
		String newemail="dtsauto1@mailinator.com";
		home.editData(AddUser.EMAIL, newemail);
		home.click(AddUser.SAVE);
		
		//VP: Verify that user is kicked out the portal after the change is saved
		Assert.assertTrue(home.isElementPresent(LoginPage.SIGN_IN));
		//12. Log into DTS portal with old email address
		home.login(dtsUser, dtsPass);
		//VP: User is unable to log into DTS portal with old email address
		Assert.assertTrue(home.isElementPresent(LoginPage.SIGN_IN));
		
		
		//Teardown
		home.login(superUsername, superUserpassword);
		// Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// Select a DTS user from the Users table
		home.dtsSelectUserByEmail(newemail);
		// Click “Edit” link
		home.click(UserMgmt.EDIT);
		// Disable the  “Add and manage users” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		//change email address back to normal
		home.editData(AddUser.EMAIL, dtsUser);
		home.click(AddUser.SAVE);
	}
	
}
