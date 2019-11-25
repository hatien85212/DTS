package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PageLogin;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.UserEdit;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.model.UsersList;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.MailUtil;
import com.dts.adminportal.util.TestData;

public class DTSPrivilegesUsers extends BasePage{
	
	@Override
	protected void initData() {
	}	
	/*
	 * Verify that the DTS user can view user's info when �Add and manage users� privilege is enabled and �Add and manage DTS users� privilege is disabled
	 */
	@Test
	public void TCPUSR_01() {
		userControl.addLog("ID : TCPUSR_01 : Verify that the DTS user can view user's info when �Add and manage users� privilege is enabled and �Add and manage DTS users� privilege is disabled.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the  �Add and manage DTS users� privilege
			7. Enable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			VP: Verify that the �Users� tab is displayed
			10. Click �Users� tab
			VP: Verify that the Users list is displayed in Users page
			11. Select a user from user list
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Disable the  �Add and manage DTS users� privilege
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// VP: Verify that the �Users� tab is displayed
		Assert.assertTrue(userControl.isElementPresent(PageHome.LINK_USERS));
		// 10. Click �Users� tab
		userControl.click(PageHome.LINK_USERS);
		// VP: Verify that the Users list is displayed in Users page
		Assert.assertEquals(true, userControl.existsElement(UsersList.getListElementUser()));
		// 11. Select a user from user list
		userControl.selectUserInfoByEmail(SUPER_PARTNER_USER);
		/*
		 * Verify that the User's info page is displayed
		 */
		Assert.assertEquals(userControl.existsElement(UserMgmt.getElementsInfo()), true);
	}
	
	/*
	 * Verify that the DTS user can view user's info when �Add and manage users� privilege is disabled and �Add and manage DTS users� privilege is enabled
	 */
	@Test
	public void TCPUSR_02() throws InterruptedException {
		userControl.addLog("ID : TCPUSR_02 : Verify that the DTS user can view user's info when �Add and manage users� privilege is disabled and �Add and manage DTS users� privilege is enabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage DTS users� privilege
			7. Disable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			VP: Verify that the �Users� tab is displayed
			10. Click �Users� tab
			VP: Verify that the Users list is displayed in Users page
			11. Select a user from user list
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// Add thread sleep to avoid issue User page load 2 times
		Thread.sleep(5000);
		// 6. Enable the  �Add and manage DTS users� privilege
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Disable the �Add and manage users� privilege
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// VP: Verify that the �Users� tab is displayed
		Assert.assertTrue(userControl.isElementPresent(PageHome.LINK_USERS));
		// 10. Click �Users� tab
		userControl.click(PageHome.LINK_USERS);
		// VP: Verify that the Users list is displayed in Users page
		Assert.assertEquals(true, userControl.existsElement(UsersList.getListElementUser()));
		// 11. Select a user from user list
		userControl.selectUserInfoByEmail(DTS_USER);
		// Verify that the User's info page is displayed.
		Assert.assertEquals(userControl.existsElement(UserMgmt.getElementsInfo()), true);
	}
	
	/*
	 * Verify that the DTS user cannot manage �Users� page when both �Add and manage users� and �Add and manage DTS users� privileges are disabled
	 */
	@Test
	public void TCPUSR_03() throws InterruptedException {
		userControl.addLog("ID : TCPUSR_03 : Verify that the DTS user cannot manage �Users� page when both �Add and manage users� and �Add and manage DTS users� privileges are disabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the  �Add and manage DTS users� privilege
			7. Disable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// Add thread sleep to avoid issue User page load 2 times
		Thread.sleep(5000);
		// 6. Disable the  �Add and manage DTS users� privilege
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Disable the �Add and manage users� privilege
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(UserEdit.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// Verify that the �Users� tab is not displayed in Global Navigation bar
		Assert.assertFalse(userControl.isElementPresent(PageHome.LINK_USERS));
	}
	
	/*
	 * Verify that both DTS and Partner user are displayed on Users list when both �Add and manage users� and �Add and manage DTS users� privileges are enabled
	 */
	@Test
	public void TCPUSR_04() {
		userControl.addLog("ID : TCPUSR_04 : Verify that both DTS and Partner user are displayed on Users list when both �Add and manage users� and �Add and manage DTS users� privileges are enabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage DTS users� privilege
			7. Enable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the  �Add and manage DTS users� privilege
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Click �Users� tab
		userControl.click(PageHome.LINK_USERS);
		/*
		 * Verify that the user list displays both Partner and DTS user
		 */
		Assert.assertTrue(userControl.checkUserExistByEmail(DTS_USER));
		Assert.assertTrue(userControl.checkUserExistByEmail(PARTNER_USER));
	}
	
	/*
	 * Verify that  DTS user can only add new DTS user when the �Add and manage users� privilege is disabled and �Add and manage DTS users� privilege is enabled.
	 */
	@Test
	public void TCPUSR_05() {
		userControl.addLog("ID : TCPUSR_05 : Verify that DTS user can only add new DTS user when the �Add and manage users� privilege is disabled and �Add and manage DTS users� privilege is enabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage DTS users� privilege
			7. Disable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the �Add New User� link is displayed while Users list only display DTS users
			11. Click �Add New User� link
			VP: In Add new user page, the Company combobox only contains �DTS Inc.� item and both �Site Privilege� and �DTS Site Privilege� module are displayed.
			12. Fill valid value into all required fields
			13. Select at least one privilege
			14. Click �Save� link		
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the  �Add and manage DTS users� privilege
		userControl.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Disable the �Add and manage users� privilege
		userControl.disablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Click �Users� tab
		userControl.click(PageHome.LINK_USERS);
		// VP: Verify that the �Add New User� link is displayed while Users list only display DTS users
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		// Users list only display DTS users
		Assert.assertTrue(userControl.verifyValueColumn(UsersList.USER_TABLE, "Company", "DTS Inc."));
		// 11. Click �Add New User� link
		userControl.click(UsersList.ADD_USER);
		// Get Company option
		ArrayList<String> options = userControl.getAllComboboxOption(AddUser.BTN_COMPANY);
		/*
		 * VP: In Add new user page, the Company combobox only contains �DTS Inc.� item and both �Site Privilege� and �DTS Site Privilege� module are displayed
		 */
		Assert.assertTrue(options.size() == 2 && ListUtil.checkListContain(options, DTS_COMPANY_NAME));
		Assert.assertTrue(userControl.isElementPresent(AddUser.PRIVILEGES_TABLE));
		Assert.assertTrue(userControl.isElementPresent(AddUser.DTS_PRIVILEGES_TABLE));
		// 12. Fill valid value into all required fields
		// 13. Select at least one privilege
		// 14. Click �Save� link
		Hashtable<String,String> data = TestData.dtsUser();
		userControl.addUser(AddUser.getDTSUser(), data);
		/*
		 * Verify that a new DTS user is created successfully
		 */
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.Success_Message[2]));
		// Delete user
		userControl.click(PageHome.LINK_USERS);
		userControl.dtsSelectUserByEmail(data.get("email"));
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can only add new Partner user when the �Add and manage users� privilege is enabled and �Add and manage DTS users� privilege is disabled
	 */
	@Test
	public void TCPUSR_06() {
		userControl.addLog("ID : TCPUSR_06 : Verify that  DTS user can only add new Partner user when the �Add and manage users� privilege is enabled and �Add and manage DTS users� privilege is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the  �Add and manage DTS users� privilege
			7. Enable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the �Add New User� link is displayed while Users list only display partner users
			11. Click �Add New User� link
			VP: In Add new user page, the Company combobox contains all company except �DTS Inc.�
			12. Select a company from company combobox
			VP: Verify that only �Site Privilege� module is displayed
			13. Fill valid value into all required fields
			14. Select at least one privilege
			15. Click �Save� link		
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Disable the  �Add and manage DTS users� privilege
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Navigate to Users page
		userControl.click(PageHome.LINK_USERS);		
		/*
		 * VP: Verify that the �Add New User� link is displayed while Users list only display partner users
		 */
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(userControl.verifyColumnNotContainValue(UsersList.USER_TABLE, "Company", "DTS Inc."));
		// 11. Click �Add New User� link
		userControl.click(UsersList.ADD_USER);
		/*
		 * VP: In Add new user page, the Company combobox contains all company except �DTS Inc.�
		 */
		ArrayList<String> listCompany = userControl.getOptionList(AddUser.BTN_COMPANY);
		Assert.assertTrue(listCompany.size() > 1);
		Assert.assertFalse(ListUtil.checkListContain(listCompany, "DTS Inc."));
		// 12. Select a company from company combobox
		userControl.selectOptionByName(AddUser.BTN_COMPANY, PARTNER_COMPANY_NAME);
		/*
		 * VP: Verify that only �Site Privilege� module is displayed
		 */
		Assert.assertTrue(userControl.isElementPresent(AddUser.PRIVILEGES_TABLE));
		Assert.assertFalse(userControl.isElementPresent(AddUser.DTS_PRIVILEGES_TABLE));
		// 13. Fill valid value into all required fields
		// 14. Select at least one privilege
		// 15. Click �Save� link
		Hashtable<String,String> data = TestData.partnerUser();
		userControl.addUser(AddUser.getPartnerUser(), data);
		/*
		 * Verify that a new Partner user is created successfully
		 */
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.Success_Message[2]));
		// Delete user
		userControl.click(PageHome.LINK_USERS);
		userControl.deleteUserByEmail(data.get("email"));
	}	
	
	/*	
	 * Verify that DTS user can add new both Partner and DTS users when the �Add and manage users� and �Add and manage DTS users� privileges are enabled
	 */
	@Test
	public void TCPUSR_07() {
		userControl.addLog("ID : TCPUSR_07 : Verify that DTS user can add new both Partner and DTS users when the �Add and manage users� and �Add and manage DTS users� privileges are enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage DTS users� privilege
			7. Enable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the �Add New User� link is displayed while Users list displays both DTS and Partner users
			11. Click �Add New User� link
			VP: In Add new user page, the Company combobox contains �DTS Inc.� and all other companies
			12. Select a company from company combobox
			VP: Verify that only �Site Privilege� module is displayed
			13. Fill valid value into all required fields
			14. Select at least one privilege
			15. Click �Save� link
			VP: Verify that a new Partner user is created successfully
			16. Repeat from step 11 to step 15 with �DTS Inc.� company		
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the  �Add and manage DTS users� privilege
		userControl.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the �Add and manage users� privilege
		userControl.enablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Navigate to Users page
		userControl.click(PageHome.LINK_USERS);
		ArrayList<String> companyColumn = userControl.getDataByHeaderText(UsersList.USER_TABLE, "Company");
		/*
		 * VP: Verify that the �Add New User� link is displayed while Users list displays both DTS and Partner users
		 */
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(ListUtil.checkListContain(companyColumn, DTS_COMPANY_NAME) && ListUtil.checkListContain(companyColumn, PARTNER_COMPANY_NAME));
		// 11. Click �Add New User� link
		userControl.click(UsersList.ADD_USER);
		ArrayList<String> companyOptions = userControl.getAllComboboxOption(AddUser.BTN_COMPANY);
		/*
		 * VP: In Add new user page, the Company combobox contains �DTS Inc.� and all other companies
		 */
		Assert.assertTrue(ListUtil.checkListContain(companyOptions, DTS_COMPANY_NAME) && ListUtil.checkListContain(companyOptions, PARTNER_COMPANY_NAME));
		// 12. Select a company from company combobox
		userControl.selectOptionByName(AddUser.BTN_COMPANY, PARTNER_COMPANY_NAME);
		/*
		 * VP: Verify that only �Site Privilege� module is displayed
		 */
		Assert.assertTrue(userControl.isElementPresent(AddUser.PRIVILEGES_TABLE));
		Assert.assertFalse(userControl.isElementPresent(AddUser.DTS_PRIVILEGES_TABLE));
		// 13. Fill valid value into all required fields
		// 14. Select at least one privilege
		// 15. Click �Save� link
		Hashtable<String, String> data = TestData.partnerUser();
		userControl.addUser(AddUser.getPartnerUser(), data);
		/*
		 * VP: Verify that a new Partner user is created successfully
		 */
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.Success_Message[2]));
		// 16. Repeat from step 11 to step 15 with �DTS Inc.� company
		userControl.click(PageHome.LINK_USERS);
		// Click �Add New User� link
		userControl.click(UsersList.ADD_USER);
		// Select a company from company combobox
		userControl.selectOptionByName(AddUser.BTN_COMPANY, DTS_COMPANY_NAME);
		// Fill valid value into all required fields
		// Select at least one privilege
		// Click �Save� link
		data = TestData.dtsUser();
		userControl.addUser(AddUser.getPartnerUser(), data);
		/*
		 * VP: Verify that a new DTS user is created successfully
		 */
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.Success_Message[2]));
	}
	
	/*	
	 * Verify that  DTS user can only edit Partner users when the �Add and manage users� privilege is enabled and �Add and manage DTS users� privilege is disabled
	 */
	@Test
	public void TCPUSR_08() {
		userControl.addLog("ID : TCPUSR_08 : Verify that  DTS user can only edit Partner users when the �Add and manage users� privilege is enabled and �Add and manage DTS users� privilege is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the  �Add and manage DTS users� privilege
			7. Enable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the �Add New User� link is displayed while Users list displays only Partner users
			11. Select a user from users list
			VP: Verify that the �Edit� link is displayed
			12. Click �Edit� link
			VP: Verify that the user edit page is displayed
			13. Change some user's value
			14. Click �Save� link	
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Disable the �Add and manage DTS users� privilege
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Navigate to Users page
		userControl.click(PageHome.LINK_USERS);		
		/*
		 * VP: Verify that the �Add New User� link is displayed while Users list only display partner users
		 */
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(userControl.verifyColumnNotContainValue(UsersList.USER_TABLE, "Company", "DTS Inc."));
		// 11. Select a user from users list
		userControl.click(PageHome.LINK_USERS);	
		userControl.dtsSelectUserByEmail(PARTNER_USER);
		/*
		 * VP: Verify that the �Edit� link is displayed
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.EDIT));
		// 12. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		/*
		 * VP: Verify that the user edit page is displayed
		 */
		Assert.assertEquals(userControl.existsElement(AddUser.getPartnerUser()), true);
		// 13. Change some user's value
		String newTitle = RandomStringUtils.randomAlphabetic(20);
		userControl.editData(UserEdit.TITLE, newTitle);
		// 14. Click �Save� link	
		userControl.click(UserEdit.SAVE);
		/*
		 * Verify that the partner user info page is displayed with new updated information correctly
		 */
		Assert.assertEquals(userControl.getTextByXpath(UserMgmt.TITLE), newTitle);
	}	
	
	/*	
	 * Verify that  DTS user can only edit DTS users when the �Add and manage users� privilege is disabled and �Add and manage DTS users� privilege is enabled
	 */
	@Test
	public void TCPUSR_09() {
		userControl.addLog("ID : TCPUSR_09 : Verify that  DTS user can only edit DTS users when the �Add and manage users� privilege is disabled and �Add and manage DTS users� privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage DTS users� privilege
			7. Disable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the �Add New User� link is displayed while Users list displays only DTS users
			11. Select a user from users list
			VP: Verify that the �Edit� link is displayed
			12. Click �Edit� link
			VP: Verify that the user edit page is displayed
			13. Change some user's value
			14. Click �Save� link
		 */
		
		/*
		 * Pre-condition: Create new DTS user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.dtsUser();
		userControl.addUser(AddUser.getDTSUser(), data);
		/*
		 * ****************************************************************
		 */
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the  �Add and manage DTS users� privilege
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Disable the �Add and manage users� privilege
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Navigate to Users page
		userControl.click(PageHome.LINK_USERS);		
		/*
		 * VP: Verify that the �Add New User� link is displayed while Users list displays only DTS users
		 */
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(userControl.verifyValueColumn(UsersList.USER_TABLE, "Company", DTS_COMPANY_NAME));
		// 11. Select a user from users list
		userControl.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that the �Edit� link is displayed
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.EDIT));
		// 12. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		/*
		 * VP: Verify that the user edit page is displayed
		 */
		Assert.assertEquals(userControl.existsElement(AddUser.getDTSUser()), true);
		// 13. Change some user's value
		String newTitle = RandomStringUtils.randomAlphabetic(20);
		userControl.editData(AddUser.TITLE, newTitle);
		// 14. Click �Save� link
		userControl.click(AddUser.SAVE);
		/*
		 * Verify that the DTS user info page is displayed with new updated information correctly
		 */
		Assert.assertEquals(userControl.existsElement(UserMgmt.getElementsInfo()), true);
		Assert.assertEquals(userControl.getTextByXpath(UserMgmt.TITLE), newTitle);
		// Delete user
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that DTS user can edit both DTS and Partner users when both �Add and manage users� and �Add and manage DTS users� privileges are enabled
	 */
	@Test
	public void TCPUSR_10() {
		userControl.addLog("ID : TCPUSR_10 : Verify that DTS user can edit both DTS and Partner users when both �Add and manage users� and �Add and manage DTS users� privileges are enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage DTS users� privilege
			7. Enable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the �Add New User� link is displayed while Users list displays both DTS and Partner users
			11. Select a Partner user from users list
			VP: Verify that the �Edit� link is displayed
			12. Click �Edit� link
			VP: Verify that the user edit page is displayed
			13. Change some user's value
			14. Click �Save� link
			VP: Verify that the DTS user info page is displayed with new updated information correctly.
			15. Repeat from step 10 to step 14 with DTS user
		 */
		/*
		 * Pre-condition: Create new DTS user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.dtsUser();
		userControl.addUser(AddUser.getDTSUser(), data);
		/*
		 * ****************************************************************
		 */
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the  �Add and manage DTS users� privilege
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Navigate to Users page
		userControl.click(PageHome.LINK_USERS);
		ArrayList<String> companyColumn = userControl.getDataByHeaderText(UsersList.USER_TABLE, "Company");
		/*
		 * VP: Verify that the �Add New User� link is displayed while Users list displays both DTS and Partner users
		 */
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(ListUtil.checkListContain(companyColumn, DTS_COMPANY_NAME) && ListUtil.checkListContain(companyColumn, PARTNER_COMPANY_NAME));
		// 11. Select a Partner user from users list
		userControl.dtsSelectUserByEmail(PARTNER_USER);
		/*
		 *  VP: Verify that the �Edit� link is displayed
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.EDIT));
		// 12. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		/*
		 * VP: Verify that the user edit page is displayed
		 */
		Assert.assertEquals(userControl.existsElement(AddUser.getDTSUser()), true);
		// 13. Change some user's value
		String newTitle = RandomStringUtils.randomAlphabetic(20);
		userControl.editData(AddUser.TITLE, newTitle);
		// 14. Click �Save� link
		userControl.click(AddUser.SAVE);
		/*
		 * VP: Verify that the DTS user info page is displayed with new updated information correctly
		 */
		Assert.assertEquals(userControl.existsElement(UserMgmt.getElementsInfo()), true);
		Assert.assertEquals(userControl.getTextByXpath(UserMgmt.TITLE), newTitle);
		// 15. Repeat from step 10 to step 14 with DTS user
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner user from users list
		userControl.dtsSelectUserByEmail(data.get("email"));
		// Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// Change some user's value
		userControl.editData(AddUser.TITLE, newTitle);
		// Click �Save� link
		userControl.click(AddUser.SAVE);
		/*
		 * VP: Verify that the DTS user info page is displayed with new updated information correctly
		 */
		Assert.assertEquals(userControl.existsElement(UserMgmt.getElementsInfo()), true);
		Assert.assertEquals(userControl.getTextByXpath(UserMgmt.TITLE), newTitle);
		// Delete user
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can only reset password for Partner users when the �Add and manage users� privilege is enabled and �Add and manage DTS users� privilege is disabled
	 */
	@Test
	public void TCPUSR_11() {
		userControl.addLog("ID : TCPUSR_11 : Verify that  DTS user can only reset password for Partner users when the �Add and manage users� privilege is enabled and �Add and manage DTS users� privilege is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the  �Add and manage DTS users� privilege
			7. Enable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that Users list displays only Partner users
			11. Select a user from users list
			VP: Verify that the �Reset Password� link is displayed
			12. Click �Reset Password� link
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
		userControl.deleteUserByEmail(PARTNER_DTS_EMAIL);
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.partnerUser();
		data.put("email", PARTNER_DTS_EMAIL);
		userControl.addUser(AddUser.getDTSUser(), data);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Disable the  �Add and manage DTS users� privilege
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Navigate to Users page
		userControl.click(PageHome.LINK_USERS);
		/*
		 * VP: Verify that the �Add New User� link is displayed while Users list displays only Partner users
		 */
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(userControl.verifyColumnNotContainValue(UsersList.USER_TABLE, "Company", DTS_COMPANY_NAME));
		// 11. Select a user from users list
		userControl.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that the �Reset Password� link is displayed
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.RESET_PASSWORD));
		// 12. Click �Reset Password� link
		userControl.click(UserMgmt.RESET_PASSWORD);
		userControl.switchWindowClickOption("Reset");
		/*
		 * Verified that a reset password email is sent to user
		 */
		MailUtil.waitForNewInboxMessage(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD, messageCount + 1);
		Assert.assertTrue(MailUtil.getNewEmailSubject(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD).contains("Reset password"));
		// Delete user
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can only reset password DTS users when the �Add and manage users� privilege is disabled and �Add and manage DTS users� privilege is enabled
	 */
	@Test
	public void TCPUSR_12() {
		userControl.addLog("ID : TCPUSR_12 : Verify that  DTS user can only reset password DTS users when the �Add and manage users� privilege is disabled and �Add and manage DTS users� privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage DTS users� privilege
			7. Disable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the �Add New User� link is displayed while Users list displays only DTS users
			11. Select a user from users list
			VP: Verify that the �Reset Password� link is displayed
			12. Click �Reset Password� link
		 */
		/*
		 * Pre-condition: Create new DTS user
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
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the �Add and manage DTS users� privilege
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Disable the �Add and manage users� privilege
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Navigate to Users page
		userControl.click(PageHome.LINK_USERS);
		/*
		 * VP: Verify that the �Add New User� link is displayed while Users list displays only DTS users
		 */
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(userControl.verifyValueColumn(UsersList.USER_TABLE, "Company", DTS_COMPANY_NAME));
		// 11. Select a user from users list
		userControl.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that the �Reset Password� link is displayed
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.RESET_PASSWORD));
		// 12. Click �Reset Password� link
		userControl.click(UserMgmt.RESET_PASSWORD);
		userControl.switchWindowClickOption("Reset");
		/*
		 * Verified that a reset password email is sent to user
		 */
		MailUtil.waitForNewInboxMessage(YAHOO_IMAP_SERVER, DTS_EMAIL, EMAIL_PASSWORD, messageCount + 1);
		Assert.assertTrue(MailUtil.getNewEmailSubject(YAHOO_IMAP_SERVER, DTS_EMAIL, EMAIL_PASSWORD).contains("Reset password"));
		// Delete user
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can reset password for both DTS and Partner users when both �Add and manage users� and �Add and manage DTS users� privileges are enabled
	 */
	@Test
	public void TCPUSR_13() {
		userControl.addLog("ID : TCPUSR_13 : Verify that  DTS user can reset password for both DTS and Partner users when both �Add and manage users� and �Add and manage DTS users� privileges are enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage DTS users� privilege
			7. Enable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the �Add New User� link is displayed while Users list displays both DTS and Partner users
			11. Select a Partner user from users list
			VP: Verify that the �Reset Password� link is displayed
			12. Click �Reset Password� link
			VP: A reset password email is sent to user
			13. Repeat from step 10 to 12 with DTS user
		 */
		/*
		 * Pre-condition: Create new DTS user and Partner user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get email message count before create user
		int messageCountPartner = MailUtil.getEmailMessageCount(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD);
		int messageCountDTS = MailUtil.getEmailMessageCount(YAHOO_IMAP_SERVER, DTS_EMAIL, EMAIL_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Delete user if exist
		userControl.deleteUserByEmail(PARTNER_DTS_EMAIL);
		userControl.deleteUserByEmail(DTS_EMAIL);
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create DTS user
		Hashtable<String,String> dataDts = TestData.dtsUser();
		dataDts.put("email", DTS_EMAIL);
		userControl.addUser(AddUser.getDTSUser(), dataDts);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create Partner user
		Hashtable<String,String> dataPartner = TestData.partnerUser();
		dataPartner.put("email", PARTNER_DTS_EMAIL);
		userControl.addUser(AddUser.getDTSUser(), dataPartner);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the �Add and manage DTS users� privilege
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Navigate to Users page
		userControl.click(PageHome.LINK_USERS);
		ArrayList<String> companyColumn = userControl.getDataByHeaderText(UsersList.USER_TABLE, "Company");
		/*
		 * VP: Verify that the �Add New User� link is displayed while Users list
		 * displays both DTS and Partner users
		 */
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(ListUtil.checkListContain(companyColumn, DTS_COMPANY_NAME) && ListUtil.checkListContain(companyColumn, PARTNER_COMPANY_NAME));
		// 11. Select a Partner user from users list
		userControl.dtsSelectUserByEmail(dataPartner.get("email"));
		/*
		 * VP: Verify that the �Reset Password� link is displayed
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.RESET_PASSWORD));
		// 12. Click �Reset Password� link
		userControl.click(UserMgmt.RESET_PASSWORD);
		userControl.switchWindowClickOption("Reset");
		/*
		 * VP: A reset password email is sent to user
		 */
		MailUtil.waitForNewInboxMessage(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD, messageCountPartner + 1);
		Assert.assertTrue(MailUtil.getNewEmailSubject(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD).contains("Reset password"));
		// Delete user
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
		// 13. Repeat from step 10 to 12 with DTS user
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner user from users list
		userControl.dtsSelectUserByEmail(dataDts.get("email"));
		// Click �Reset Password� link
		userControl.click(UserMgmt.RESET_PASSWORD);
		userControl.switchWindowClickOption("Reset");
		/*
		 * Verified that a reset password email is sent to user
		 */
		MailUtil.waitForNewInboxMessage(YAHOO_IMAP_SERVER, DTS_EMAIL, EMAIL_PASSWORD, messageCountDTS + 1);
		Assert.assertTrue(MailUtil.getNewEmailSubject(YAHOO_IMAP_SERVER, DTS_EMAIL, EMAIL_PASSWORD).contains("Reset password"));
		// Delete user
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can only delete Partner users when the �Add and manage users� privilege is enabled and �Add and manage DTS users� privilege is disabled
	 */
	@Test
	public void TCPUSR_14() {
		userControl.addLog("ID : TCPUSR_14 : Verify that  DTS user can only delete Partner users when the �Add and manage users� privilege is enabled and �Add and manage DTS users� privilege is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the  �Add and manage DTS users� privilege
			7. Enable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the �Add New User� link is displayed while Users list displays only Partner users
			11. Select a user from users list
			VP: Verify that the �Delete� link is displayed
			12. Click �Delete� link
			13. Click �Delete� button on delete confirmation dialog
		 */
		/*
		 * Pre-condition: Create new Partner user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.partnerUser();
		userControl.addUser(AddUser.getDTSUser(), data);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Disable the  �Add and manage DTS users� privilege
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Navigate to Users page
		userControl.click(PageHome.LINK_USERS);
		/*
		 * VP: Verify that the �Add New User� link is displayed while Users list displays only Partner users
		 */
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(userControl.verifyColumnNotContainValue(UsersList.USER_TABLE, "Company", DTS_COMPANY_NAME));
		// 11. Select a user from users list
		userControl.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that the �Delete� link is displayed
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.DELETE_ACCOUNT));
		// 12. Click �Delete� link
		// 13. Click �Delete� button on delete confirmation dialog
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
		/*
		 * Verify that the portal redirects to Users list page and the deleted user is not displayed in Users list
		 */
		Assert.assertEquals(userControl.existsElement(UsersList.getListElementUser()), true);
		Assert.assertFalse(userControl.checkUserExistByEmail(data.get("email")));
	}
	
	/*	
	 * Verify that DTS user can only delete DTS users when the �Add and manage users� privilege is disabled and �Add and manage DTS users� privilege is enabled
	 */
	@Test
	public void TCPUSR_15() {
		userControl.addLog("ID : TCPUSR_15 : Verify that  DTS user can only delete DTS users when the �Add and manage users� privilege is disabled and �Add and manage DTS users� privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage DTS users� privilege
			7. Disable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the �Add New User� link is displayed while Users list displays only DTS users
			11. Select a user from users list
			VP: Verify that the �Delete� link is displayed
			12. Click �Delete� link
			13. Click �Delete� button on delete confirmation dialog
		 */
		/*
		 * Pre-condition: Create new DTS user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.dtsUser();
		userControl.addUser(AddUser.getDTSUser(), data);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the  �Add and manage DTS users� privilege
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Disable the �Add and manage users� privilege
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Navigate to Users page
		userControl.click(PageHome.LINK_USERS);
		/*
		 * VP: Verify that the �Add New User� link is displayed while Users list displays only DTS users
		 */
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(userControl.verifyValueColumn(UsersList.USER_TABLE, "Company", DTS_COMPANY_NAME));
		// 11. Select a user from users list
		userControl.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that the �Delete� link is displayed
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.DELETE_ACCOUNT));
		// 12. Click �Delete� link
		// 13. Click �Delete� button on delete confirmation dialog
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
		/*
		 * Verify that the portal redirects to Users list page and the deleted user is not displayed in Users list
		 */
		Assert.assertEquals(userControl.existsElement(UsersList.getListElementUser()), true);
		Assert.assertFalse(userControl.checkUserExistByEmail(data.get("email")));
	}
	
	/*	
	 * Verify that DTS user can delete both DTS and Partner users when both �Add and manage users� and �Add and manage DTS users� privileges are enabled
	 */
	@Test
	public void TCPUSR_16() {
		userControl.addLog("ID : TCPUSR_16 : Verify that DTS user can delete both DTS and Partner users when both �Add and manage users� and �Add and manage DTS users� privileges are enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage DTS users� privilege
			7. Enable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the �Add New User� link is displayed while Users list displays both DTS and Partner users
			11. Select a Partner user from users list
			VP: Verify that the �Delete� link is displayed
			12. Click �Delete� link
			13. Click �Delete� button on delete confirmation dialog
			VP: Verify that the portal redirects to Users list page and the deleted user is not displayed in Users list.
			14. Repeat from step 10 to step 14 with DTS user
		 */
		/*
		 * Pre-condition: Create new DTS user and Partner user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> dataDts = TestData.dtsUser();
		userControl.addUser(AddUser.getDTSUser(), dataDts);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> dataPartner = TestData.dtsUser();
		userControl.addUser(AddUser.getDTSUser(), dataPartner);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the  �Add and manage DTS users� privilege
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Navigate to Users page
		userControl.click(PageHome.LINK_USERS);
		ArrayList<String> companyColumn = userControl.getDataByHeaderText(UsersList.USER_TABLE, "Company");
		/*
		 * VP: Verify that the �Add New User� link is displayed while Users list displays both DTS and Partner users
		 */
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(ListUtil.checkListContain(companyColumn, DTS_COMPANY_NAME) && ListUtil.checkListContain(companyColumn, PARTNER_COMPANY_NAME));
		// 11. Select a Partner user from users list
		userControl.dtsSelectUserByEmail(dataPartner.get("email"));
		/*
		 * VP: Verify that the �Delete� link is displayed
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.DELETE_ACCOUNT));
		// 12. Click �Delete� link
		// 13. Click �Delete� button on delete confirmation dialog
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
		/*
		 * VP: Verify that the portal redirects to Users list page and the deleted user is not displayed in Users list
		 */
		Assert.assertEquals(userControl.existsElement(UsersList.getListElementUser()), true);
		Assert.assertFalse(userControl.checkUserExistByEmail(dataPartner.get("email")));
		// 14. Repeat from step 10 to step 14 with DTS user
		// Select a DTS user from users list
		userControl.dtsSelectUserByEmail(dataDts.get("email"));
		// Click �Delete� link
		// Click �Delete� button on delete confirmation dialog
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
		/*
		 * VP: Verify that the portal redirects to Users list page and the deleted user is not displayed in Users list
		 */
		Assert.assertEquals(userControl.existsElement(UsersList.getListElementUser()), true);
		Assert.assertFalse(userControl.checkUserExistByEmail(dataDts.get("email")));
	}
	
	/*	
	 * Verify that  DTS user can only suspend Partner users when the �Add and manage users� privilege is enabled and �Add and manage DTS users� privilege is disabled
	 */
	@Test
	public void TCPUSR_17() {
		userControl.addLog("ID : TCPUSR_17 : Verify that  DTS user can only suspend Partner users when the �Add and manage users� privilege is enabled and �Add and manage DTS users� privilege is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the  �Add and manage DTS users� privilege
			7. Enable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the �Add New User� link is displayed while Users list displays only Partner users
			11. Select a user from users list
			VP: Verify that the �Suspend� link is displayed but the �Restore� link is not
			12. Click �Suspend� link
			13. Click �Suspend� button on suspend confirmation dialog
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
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Disable the  �Add and manage DTS users� privilege
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Navigate to Users page
		userControl.click(PageHome.LINK_USERS);
		/*
		 * VP: Verify that the �Add New User� link is displayed while Users list displays only Partner users
		 */
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(userControl.verifyColumnNotContainValue(UsersList.USER_TABLE, "Company", DTS_COMPANY_NAME));
		// 11. Select a user from users list
		userControl.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that the �Suspend� link is displayed but the �Restore� link is not
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.SUSPEND));
		Assert.assertFalse(userControl.isElementPresent(UserMgmt.RESTORE));
		// 12. Click �Suspend� link
		userControl.click(UserMgmt.SUSPEND);
		// 13. Click �Suspend� button on suspend confirmation dialog
		userControl.selectConfirmationDialogOption("Suspend");
		/*
		 * Verify that the portal redirects to Users list page and the suspended user is not displayed in Active User list but in Suspended users list
		 */
		Assert.assertEquals(userControl.existsElement(UsersList.getListElementUser()), true);
		userControl.selectFilterByName(UsersList.FILTER_USER, "Suspended");
		// Check if PDPP-1191 found
		if(!userControl.checkUserExistByEmail(data.get("email"))){
			userControl.addErrorLog("PDPP-1191: 080 User List: the suspended users are not displayed in suspended list with all companies filtering");
			Assert.assertTrue(false);
		}
		Assert.assertTrue(userControl.checkUserExistByEmail(data.get("email")));
		// Delete user
		userControl.dtsSelectUserByEmail(data.get("email"));
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can only suspend DTS users when the �Add and manage users� privilege is disabled and �Add and manage DTS users� privilege is enabled
	 */
	@Test
	public void TCPUSR_18() {
		userControl.addLog("ID : TCPUSR_18 : Verify that  DTS user can only suspend DTS users when the �Add and manage users� privilege is disabled and �Add and manage DTS users� privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage DTS users� privilege
			7. Disable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the �Add New User� link is displayed while Users list displays only DTS users
			11. Select a user from users list
			VP: Verify that the �Suspend� link is displayed but the �Restore� link is not
			12. Click �Suspend� link
			13. Click �Suspend� button on suspend confirmation dialog
		 */
		/*
		 * Pre-condition: Create new DTS user
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
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the  �Add and manage DTS users� privilege
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Disable the �Add and manage users� privilege
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Navigate to Users page
		userControl.click(PageHome.LINK_USERS);
		/*
		 * VP: Verify that the �Add New User� link is displayed while Users list displays only DTS users
		 */
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(userControl.verifyValueColumn(UsersList.USER_TABLE, "Company", DTS_COMPANY_NAME));
		// 11. Select a user from users list
		userControl.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that the �Suspend� link is displayed but the �Restore� link is not
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.SUSPEND));
		Assert.assertFalse(userControl.isElementPresent(UserMgmt.RESTORE));
		// 12. Click �Suspend� link
		userControl.click(UserMgmt.SUSPEND);
		// 13. Click �Suspend� button on suspend confirmation dialog
		userControl.switchWindowClickOption("Suspend");
		/*
		 * Verify that the portal redirects to Users list page and the suspended
		 * user is not displayed in Active User list but in Suspended users list
		 */
		Assert.assertEquals(userControl.existsElement(UsersList.getListElementUser()), true);
		userControl.selectFilterByName(UsersList.FILTER_USER, "Suspended");
		Assert.assertTrue(userControl.checkUserExistByEmail(data.get("email")));
		// Delete user
		userControl.dtsSelectUserByEmail(data.get("email"));
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can suspend both DTS and Partner users when both �Add and manage users� and �Add and manage DTS users� privileges are enabled
	 */
	@Test
	public void TCPUSR_19() {
		userControl.addLog("ID : TCPUSR_19 : Verify that  DTS user can suspend both DTS and Partner users when both �Add and manage users� and �Add and manage DTS users� privileges are enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage DTS users� privilege
			7. Enable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the �Add New User� link is displayed while Users list displays both DTS and Partner users
			11. Select a Partner user from users list
			VP: Verify that the �Suspend� link is displayed but the �Restore� link is not
			12. Click �Suspend� link
			13. Click �Suspend� button on suspend confirmation dialog
			VP: Verify that the portal redirects to Users list page and the suspended user is not displayed in Active User list but in Suspended users list
			14. Repeat from step 10 to step 13 with DTS user
		 */
		/*
		 * Pre-condition: Create new DTS user and Partner user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get email message count before create user
		int messageCountPartner = MailUtil.getEmailMessageCount(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD);
		int messageCountDTS = MailUtil.getEmailMessageCount(YAHOO_IMAP_SERVER, DTS_EMAIL, EMAIL_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Delete user if exist
		userControl.deleteUserByEmail(PARTNER_DTS_EMAIL);
		userControl.deleteUserByEmail(DTS_EMAIL);
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create DTS user
		Hashtable<String,String> dataDts = TestData.dtsUser();
		dataDts.put("email", DTS_EMAIL);
		userControl.addUser(AddUser.getDTSUser(), dataDts);
		// Active DTS user
		MailUtil.waitForNewInboxMessage(YAHOO_IMAP_SERVER, DTS_EMAIL, EMAIL_PASSWORD, messageCountDTS);
		userControl.activeNewUserViaEmail(YAHOO_IMAP_SERVER, DTS_EMAIL, EMAIL_PASSWORD, NEW_ACTIVE_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create Partner user
		Hashtable<String,String> dataPartner = TestData.partnerUser();
		dataPartner.put("email", PARTNER_DTS_EMAIL);
		userControl.addUser(AddUser.getDTSUser(), dataPartner);
		// Active Partner user
		MailUtil.waitForNewInboxMessage(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD, messageCountPartner);
		userControl.activeNewUserViaEmail(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD, NEW_ACTIVE_USER_PASSWORD);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the �Add and manage DTS users� privilege
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Navigate to Users page
		userControl.click(PageHome.LINK_USERS);
		ArrayList<String> companyColumn = userControl.getDataByHeaderText(UsersList.USER_TABLE, "Company");
		/*
		 * VP: Verify that the �Add New User� link is displayed while Users list
		 * displays both DTS and Partner users
		 */
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(ListUtil.checkListContain(companyColumn, DTS_COMPANY_NAME) && ListUtil.checkListContain(companyColumn, PARTNER_COMPANY_NAME));
		// 11. Select a Partner user from users list
		userControl.dtsSelectUserByEmail(dataPartner.get("email"));
		/*
		 * VP: Verify that the �Suspend� link is displayed but the �Restore� link is not
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.SUSPEND));
		Assert.assertFalse(userControl.isElementPresent(UserMgmt.RESTORE));
		// 12. Click �Suspend� link
		userControl.click(UserMgmt.SUSPEND);
		// 13. Click �Suspend� button on suspend confirmation dialog
		userControl.selectConfirmationDialogOption("Suspend");
		/*
		 * VP: Verify that the portal redirects to Users list page and the
		 * suspended user is not displayed in Active User list but in Suspended
		 * users list
		 */
		Assert.assertEquals(userControl.existsElement(UsersList.getListElementUser()), true);
		userControl.selectFilterByName(UsersList.FILTER_USER, "Suspended");
		// Check if PDPP-1191 found
		if(!userControl.checkUserExistByEmail(dataPartner.get("email"))){
			userControl.addErrorLog("PDPP-1191: 080 User List: the suspended users are not displayed in suspended list with all companies filtering");
			Assert.assertTrue(false);
		}
		Assert.assertTrue(userControl.checkUserExistByEmail(dataPartner.get("email")));
		// Delete user
		userControl.dtsSelectUserByEmail(dataPartner.get("email"));
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
		// 14. Repeat from step 10 to step 13 with DTS user
		userControl.dtsSelectUserByEmail(dataDts.get("email"));
		// Click �Suspend� link
		userControl.click(UserMgmt.SUSPEND);
		// Click �Suspend� button on suspend confirmation dialog
		userControl.switchWindowClickOption("Suspend");
		/*
		 * VP: Verify that the portal redirects to Users list page and the
		 * suspended user is not displayed in Active User list but in Suspended
		 * users list
		 */
		Assert.assertEquals(userControl.existsElement(UsersList.getListElementUser()), true);
		userControl.selectFilterByName(UsersList.FILTER_USER, "Suspended");
		Assert.assertTrue(userControl.checkUserExistByEmail(dataDts.get("email")));
		// Delete user
		userControl.dtsSelectUserByEmail(dataDts.get("email"));
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can only restore Partner users when the �Add and manage users� privilege is enabled and �Add and manage DTS users� privilege is disabled
	 */
	@Test
	public void TCPUSR_20() {
		userControl.addLog("ID : TCPUSR_20 : Verify that DTS user can only restore Partner users when the �Add and manage users� privilege is enabled and �Add and manage DTS users� privilege is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the  �Add and manage DTS users� privilege
			7. Enable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the �Add New User� link is displayed while Users list displays only Partner users
			11. Select a user from suspended users list
			VP: Verify that the �Restore� link is displayed but the �suspend� link is not
			12. Click �Restore� link
		 */
		/*
		 * Pre-condition: Create new suspended Partner user
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
		userControl.dtsSelectUserByEmail(data.get("email"));
		// Suspend user
		userControl.click(UserMgmt.SUSPEND);
		userControl.selectConfirmationDialogOption("Suspend");
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Disable the �Add and manage DTS users� privilege
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE,	Privileges.Add_manage_DTS_user);
		// 7. Enable the �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Navigate to Users page
		userControl.click(PageHome.LINK_USERS);
		/*
		 * VP: Verify that the �Add New User� link is displayed while Users list
		 * displays only Partner users
		 */
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(userControl.verifyColumnNotContainValue(UsersList.USER_TABLE, "Company", DTS_COMPANY_NAME));
		// 11. Select a user from suspended users list
		userControl.selectFilterByName(UsersList.FILTER_USER, "Suspended");
		// Check if PDPP-1191 found
		if(!userControl.checkUserExistByEmail(data.get("email"))){
			userControl.addErrorLog("PDPP-1191: 080 User List: the suspended users are not displayed in suspended list with all companies filtering");
			Assert.assertTrue(false);
		}
		userControl.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that the �Restore� link is displayed but the �suspend� link is not
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.RESTORE));
		Assert.assertFalse(userControl.isElementPresent(UserMgmt.SUSPEND));
		// 12. Click �Restore� link
		userControl.click(UserMgmt.RESTORE);
		/*
		 * Verify that the portal redirects to Users list page and the restored user is displayed in Active User list
		 */
		Assert.assertEquals(userControl.existsElement(UsersList.getListElementUser()), true);
		Assert.assertTrue(userControl.checkUserExistByEmail(data.get("email")));
		// Delete user
		userControl.dtsSelectUserByEmail(data.get("email"));
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can only restore DTS users when the �Add and manage users� privilege is disabled and �Add and manage DTS users� privilege is enabled
	 */
	@Test
	public void TCPUSR_21() {
		userControl.addLog("ID : TCPUSR_21 : Verify that  DTS user can only restore DTS users when the �Add and manage users� privilege is disabled and �Add and manage DTS users� privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage DTS users� privilege
			7. Disable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the �Add New User� link is displayed while Users list displays only DTS users
			11. Select a user from suspended users list
			VP: Verify that the �Restore� link is displayed but the Suspend� link is not
			12. Click �Restore� link
		 */
		/*
		 * Pre-condition: Create new suspended DTS user
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
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the  �Add and manage DTS users� privilege
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Disable the �Add and manage users� privilege
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Navigate to Users page
		userControl.click(PageHome.LINK_USERS);
		/*
		 * VP: Verify that the �Add New User� link is displayed while Users list displays only DTS users
		 */
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(userControl.verifyValueColumn(UsersList.USER_TABLE, "Company", DTS_COMPANY_NAME));
		// 11. Select a user from suspended users list
		userControl.selectFilterByName(UsersList.FILTER_USER, "Suspended");
		// Check if PDPP-1191 found
		if(!userControl.checkUserExistByEmail(data.get("email"))){
			userControl.addErrorLog("PDPP-1191: 080 User List: the suspended users are not displayed in suspended list with all companies filtering");
			Assert.assertTrue(false);
		}
		userControl.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that the �Restore� link is displayed but the �suspend�
		 * link is not
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.RESTORE));
		Assert.assertFalse(userControl.isElementPresent(UserMgmt.SUSPEND));
		// 12. Click �Restore� link
		userControl.click(UserMgmt.RESTORE);
		/*
		 * Verify that the portal redirects to Users list page and the restored user is displayed in Active User list
		 */
		Assert.assertEquals(userControl.existsElement(UsersList.getListElementUser()), true);
		Assert.assertTrue(userControl.checkUserExistByEmail(data.get("email")));
		// Delete user
		userControl.dtsSelectUserByEmail(data.get("email"));
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can restore both DTS and Partner users when both �Add and manage users� and �Add and manage DTS users� privileges are enabled
	 */
	@Test
	public void TCPUSR_22() {
		userControl.addLog("ID : TCPUSR_22 : Verify that  DTS user can restore both DTS and Partner users when both �Add and manage users� and �Add and manage DTS users� privileges are enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage DTS users� privilege
			7. Enable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the �Add New User� link is displayed while Users list displays both DTS and Partner users
			11. Select a Partner user from users list
			VP: Verify that the �Restore� link is displayed but the �Suspend� link is not
			12. Click �Restore� link
			VP: Verify that the portal redirects to Users list page and the restored user is displayed in Active User list
			13. Repeat from step 10 to step 12 with DTS user
		 */
		/*
		 * Pre-condition: Create new suspended DTS user and Partner user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get email message count before create user
		int messageCountPartner = MailUtil.getEmailMessageCount(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD);
		int messageCountDTS = MailUtil.getEmailMessageCount(YAHOO_IMAP_SERVER, DTS_EMAIL, EMAIL_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Delete user if exist
		userControl.deleteUserByEmail(PARTNER_DTS_EMAIL);
		userControl.deleteUserByEmail(DTS_EMAIL);
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create DTS user
		Hashtable<String,String> dataDts = TestData.dtsUser();
		dataDts.put("email", DTS_EMAIL);
		userControl.addUser(AddUser.getDTSUser(), dataDts);
		// Active DTS user
		MailUtil.waitForNewInboxMessage(YAHOO_IMAP_SERVER, DTS_EMAIL, EMAIL_PASSWORD, messageCountDTS);
		userControl.activeNewUserViaEmail(YAHOO_IMAP_SERVER, DTS_EMAIL, EMAIL_PASSWORD, NEW_ACTIVE_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select user above
		userControl.dtsSelectUserByEmail(dataDts.get("email"));
		// Suspend DTS user
		userControl.click(UserMgmt.SUSPEND);
		userControl.selectConfirmationDialogOption("Suspend");
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create Partner user
		Hashtable<String,String> dataPartner = TestData.partnerUser();
		dataPartner.put("email", PARTNER_DTS_EMAIL);
		userControl.addUser(AddUser.getDTSUser(), dataPartner);
		// Active Partner user
		MailUtil.waitForNewInboxMessage(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD, messageCountPartner);
		userControl.activeNewUserViaEmail(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD, NEW_ACTIVE_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select user above
		userControl.dtsSelectUserByEmail(dataPartner.get("email"));
		// Suspend partner user
		userControl.click(UserMgmt.SUSPEND);
		userControl.selectConfirmationDialogOption("Suspend");
		/*
		 * ********************************************************************
		 */
		// 3. Navigate to "Users" page
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the �Add and manage DTS users� privilege
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Navigate to Users page
		userControl.click(PageHome.LINK_USERS);
		ArrayList<String> companyColumn = userControl.getDataByHeaderText(UsersList.USER_TABLE, "Company");
		/*
		 * VP: Verify that the �Add New User� link is displayed while Users list
		 * displays both DTS and Partner users
		 */
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(ListUtil.checkListContain(companyColumn, DTS_COMPANY_NAME) && ListUtil.checkListContain(companyColumn, PARTNER_COMPANY_NAME));
		// 11. Select a Partner user from users list
		userControl.dtsSelectUserByEmail(dataPartner.get("email"));
		/*
		 * VP: Verify that the �Restore� link is displayed but the �Suspend� link is not
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.RESTORE));
		Assert.assertFalse(userControl.isElementPresent(UserMgmt.SUSPEND));
		// 12. Click �Restore� link
		userControl.click(UserMgmt.RESTORE);
		/*
		 * VP: Verify that the portal redirects to Users list page and the restored user is displayed in Active User list
		 */
		Assert.assertEquals(userControl.existsElement(UsersList.getListElementUser()), true);
		Assert.assertTrue(userControl.checkUserExistByEmail(dataPartner.get("email")));
		// Delete Partner user
		userControl.dtsSelectUserByEmail(dataPartner.get("email"));
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
		// 13. Repeat from step 10 to step 12 with DTS user
		// Select a DTS user from users list
		userControl.dtsSelectUserByEmail(dataDts.get("email"));
		// Click �Restore� link
		userControl.click(UserMgmt.RESTORE);
		/*
		 * Verify that the portal redirects to Users list page and the restored user is displayed in Active User list
		 */
		Assert.assertEquals(userControl.existsElement(UsersList.getListElementUser()), true);
		Assert.assertTrue(userControl.checkUserExistByEmail(dataDts.get("email")));
		// Delete DTS user
		userControl.dtsSelectUserByEmail(dataDts.get("email"));
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that DTS user can only edit Partner user's privilege when the �Add and manage users� privilege is enabled and �Add and manage DTS users� privilege is disabled
	 */
	@Test
	public void TCPUSR_23() {
		userControl.addLog("ID : TCPUSR_23 : Verify that  DTS user can only edit Partner user's privilege when the �Add and manage users� privilege is enabled and �Add and manage DTS users� privilege is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the  �Add and manage DTS users� privilege
			7. Enable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the �Add New User� link is displayed while Users list displays only Partner users
			11. Select a user from users list
			VP: Verify that only �Site Privilege� module is displayed
			12. Uncheck some privilege
			13. Click �Save� link
		 */
		/*
		 * Pre-condition: Create new Partner user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.partnerUser();
		userControl.addUser(AddUser.getDTSUser(), data);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Disable the  �Add and manage DTS users� privilege
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE,Privileges.Add_manage_DTS_user);
		// 7. Enable the �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE,Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Navigate to Users page
		userControl.click(PageHome.LINK_USERS);
		/*
		 * VP: Verify that the �Add New User� link is displayed while Users list displays only Partner users
		 */
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(userControl.verifyColumnNotContainValue(UsersList.USER_TABLE, "Company", DTS_COMPANY_NAME));
		// 11. Select a user from users list
		userControl.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that only �Site Privilege� module is displayed
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.SITE_PRIVILEGES_TABLE));
		// Check if PDPP-894 found
		if(userControl.isElementPresent(UserMgmt.DTS_SITE_PRIVILEGES_TABLE)){
			userControl.addErrorLog("PDPP-894: 090D User Info: The 'DTS Site Privilege' table is displayed in the detail info page of partner user");
			Assert.assertTrue(false);
		}
		Assert.assertFalse(userControl.isElementPresent(UserMgmt.DTS_SITE_PRIVILEGES_TABLE));
		// 12. Uncheck some privilege
		// 13. Click �Save� link
		userControl.click(UserMgmt.EDIT);
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		userControl.click(AddUser.SAVE);
		/*
		 * Verify that the portal redirects to User's info page and only display the checked privilege of user
		 */
		Assert.assertEquals(userControl.existsElement(UserMgmt.getElementsInfo()), true);
		ArrayList<String> privileges = userControl.getPrivileges(UserMgmt.SITE_PRIVILEGES_TABLE);
		Assert.assertFalse(ListUtil.checkListContain(privileges,Privileges.Add_and_manage_products));
		// Delete user
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can only edit DTS user's privileges when the �Add and manage users� privilege is disabled and �Add and manage DTS users� privilege is enabled
	 */
	@Test
	public void TCPUSR_24() {
		userControl.addLog("ID : TCPUSR_24 : Verify that  DTS user can only edit DTS user's privileges when the �Add and manage users� privilege is disabled and �Add and manage DTS users� privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage DTS users� privilege
			7. Disable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the �Add New User� link is displayed while Users list displays only DTS users
			11. Select a user from users list
			VP: Verify that both �Site Privilege� and �DTS Site Privilege� modules are displayed
			12. Uncheck some privilege
			13. Click �Save� link
		 */
		/*
		 * Pre-condition: Create new Partner user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to DTS page
		userControl.click(PageHome.LINK_USERS);
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> data = TestData.dtsUser();
		userControl.addUser(AddUser.getDTSUser(), data);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the  �Add and manage DTS users� privilege
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE,Privileges.Add_manage_DTS_user);
		// 7. Disable the �Add and manage users� privilege
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE,Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Navigate to Users page
		userControl.click(PageHome.LINK_USERS);
		/*
		 * VP: Verify that the �Add New User� link is displayed while Users list displays only DTS users
		 */
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(userControl.verifyValueColumn(UsersList.USER_TABLE, "Company", DTS_COMPANY_NAME));
		// 11. Select a user from users list
		userControl.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: Verify that both �Site Privilege� and �DTS Site Privilege� modules are displayed
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.SITE_PRIVILEGES_TABLE));
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.DTS_SITE_PRIVILEGES_TABLE));
		// 12. Uncheck some privilege
		// 13. Click �Save� link
		userControl.click(UserMgmt.EDIT);
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		userControl.click(AddUser.SAVE);
		/*
		 * Verify that the portal redirects to User's info page and only display the checked privilege of user
		 */
		Assert.assertEquals(userControl.existsElement(UserMgmt.getElementsInfo()), true);
		ArrayList<String> privileges = userControl.getPrivileges(UserMgmt.DTS_SITE_PRIVILEGES_TABLE);
		Assert.assertFalse(ListUtil.checkListContain(privileges,Privileges.Add_manage_DTS_user));
		// Delete user
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*	
	 * Verify that  DTS user can edit both DTS and Partner user's privileges when both �Add and manage users� and �Add and manage DTS users� privileges are enabled
	 */
	@Test
	public void TCPUSR_25() {
		userControl.addLog("ID : TCPUSR_25 : Verify that  DTS user can edit both DTS and Partner user's privileges when both �Add and manage users� and �Add and manage DTS users� privileges are enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage DTS users� privilege
			7. Enable the �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above DTS user successfully
			10. Navigate to Users page
			VP: Verify that the �Add New User� link is displayed while Users list displays both DTS and Partner users
			11. Select a DTS user from users list
			VP: Verify that both �Site Privilege� and �DTS Site Privilege� modules are displayed
			12. Uncheck some privilege
			13. Click �Save� link
			VP: Verify that the portal redirects to User's info page and only display the checked privilege of DTS user
			14. Repeat from step 10 to step 13 with Partner user
		 */
		/*
		 * Pre-condition: Create new DTS user and Partner user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> dataDts = TestData.dtsUser();
		userControl.addUser(AddUser.getDTSUser(), dataDts);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String,String> dataPartner = TestData.partnerUser();
		userControl.addUser(AddUser.getDTSUser(), dataPartner);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the �Add and manage DTS users� privilege
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE,Privileges.Add_manage_DTS_user);
		// 7. Enable the �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE,Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Navigate to Users page
		userControl.click(PageHome.LINK_USERS);
		ArrayList<String> companyColumn = userControl.getDataByHeaderText(UsersList.USER_TABLE, "Company");
		/*
		 * VP: Verify that the �Add New User� link is displayed while Users list
		 * displays both DTS and Partner users
		 */
		Assert.assertTrue(userControl.isElementPresent(UsersList.ADD_USER));
		Assert.assertTrue(ListUtil.checkListContain(companyColumn, DTS_COMPANY_NAME) && ListUtil.checkListContain(companyColumn, PARTNER_COMPANY_NAME));
		// 11. Select a DTS user from users list
		userControl.dtsSelectUserByEmail(dataDts.get("email"));
		/*
		 * VP: Veriy that both �Site Privilege� and �DTS Site Privilege� modules are displayed
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.SITE_PRIVILEGES_TABLE));
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.DTS_SITE_PRIVILEGES_TABLE));
		// 12. Uncheck some privilege
		// 13. Click �Save� link
		userControl.click(UserMgmt.EDIT);
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		userControl.click(AddUser.SAVE);
		/*
		 * VP: Verify that the portal redirects to User's info page and only display the checked privilege of DTS user
		 */
		Assert.assertEquals(userControl.existsElement(UserMgmt.getElementsInfo()), true);
		ArrayList<String> privileges = userControl.getPrivileges(UserMgmt.DTS_SITE_PRIVILEGES_TABLE);
		Assert.assertFalse(ListUtil.checkListContain(privileges,Privileges.Add_manage_DTS_user));
		// Delete user
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
		// 14. Repeat from step 10 to step 13 with Partner user
		// Select a Partner user from users list
		userControl.dtsSelectUserByEmail(dataPartner.get("email"));
		// Uncheck some privilege
		// Click �Save� link
		userControl.click(UserMgmt.EDIT);
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		userControl.click(AddUser.SAVE);
		/*
		 * Verify that the portal redirects to User's info page and only display the checked privilege of user
		 */
		Assert.assertEquals(userControl.existsElement(UserMgmt.getElementsInfo()), true);
		privileges = userControl.getPrivileges(UserMgmt.SITE_PRIVILEGES_TABLE);
		Assert.assertFalse(ListUtil.checkListContain(privileges,Privileges.Add_and_manage_products));
		// Delete user
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);		
	}
	
	/*	
	 * Verify that the DTS users can edit their own info including privileges notification but not for Site privileges even when the �Add and manage users� privilege is enabled or disabled
	 */
	@Test
	public void TCPUSR_27() {
		userControl.addLog("ID : TCPUSR_27 : Verify that  DTS user can edit both DTS and Partner user's privileges when both �Add and manage users� and �Add and manage DTS users� privileges are enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage users� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			9. Click on DTS user's email on top right corner
			10. Select �User Account� item
			VP: Verify that the �Edit� link is displayed in  �Account Information� page
			11. Notice for the current privilege of user
			12. Click �Edit� link
			VP: Verify that the �Edit Account� page is displayed, the given name, family name, title, email, country code, the phone fields and the Site privilege notification are editable. But with the Site site privileges section, only display what privileges are displayed in Account info page and all of them are un-editable.
			13. Log out DTS portal
			14. Repeat from step 2 to 5
			15. Disable the  �Add and manage users� privilege
			16. Repeat from step 7 to 12
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the  �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		userControl.logout();
		// 8. Log into DTS portal as above partner user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 9. Click on DTS user's email on top right corner
		userControl.click(PageHome.lbLogout);
		// 10. Select �User Account� item
		userControl.click(PageHome.loginAccount);
		/*
		 * VP: Verify that the �Edit� link is displayed in  �Account Information� page
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.EDIT));
		// 11. Notice for the current privilege of user
		// 12. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		/*
		 * VP: Verify that the �Edit Account� page is displayed, the given name,
		 * family name, title, email, country code, the phone fields and the
		 * Site privilege notification are editable. But with the Site site
		 * privileges section, only display what privileges are displayed in
		 * Account info page and all of them are un-editable.
		 */
		Assert.assertTrue(userControl.isElementEditable(AddUser.getUserInfo()));
		Assert.assertTrue(userControl.isAllNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE));
		Assert.assertTrue(userControl.isAllPrivilegeDisable(AddUser.PRIVILEGES_TABLE));
		// 13. Log out DTS portal
		userControl.logout();
		// 14. Repeat from step 2 to 5
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 15. Disable the  �Add and manage users� privilege
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 16. Repeat from step 7 to 12
		userControl.logout();
		// Log into DTS portal as above partner user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// Click on DTS user's email on top right corner
		userControl.click(PageHome.lbLogout);
		// Select �User Account� item
		userControl.click(PageHome.loginAccount);
		// Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		/*
		 * Verify that the �Edit Account� page is displayed, the given name,
		 * family name, title, email, country code, the phone fields and the
		 * Site privilege notification are editable. But with the Site site
		 * privileges section, only display what privileges are displayed in
		 * Account info page and all of them are un-editable
		 */
		Assert.assertTrue(userControl.isElementEditable(AddUser.getUserInfo()));
		Assert.assertTrue(userControl.isAllNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE));
		Assert.assertTrue(userControl.isAllPrivilegeDisable(AddUser.PRIVILEGES_TABLE));
	}
	
	/*	
	 * Verify that the DTS users can edit their own info including privileges notification but not for DTS Site privileges even when the �Add and manage DTS users� privilege is enabled or disabled
	 */
	@Test
	public void TCPUSR_28() {
		userControl.addLog("ID : TCPUSR_28 : Verify that the DTS users can edit their own info including privileges notification but not for DTS Site privileges even when the �Add and manage DTS users� privilege is enabled or disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage DTS users� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			9. Click on DTS user's email on top right corner
			10. Select �User Account� item
			VP: Verify that the �Edit� link is displayed in  �Account Information� page
			11. Notice for the current privilege of user
			12. Click �Edit� link
			VP: Verify that the �Edit Account� page is displayed, the given name, family name, title, email, country code, the phone fields and the DTS Site privilege notification are editable. But with the DTS Site site privileges section, only display what privileges are displayed in Account info page and all of them are un-editable.
			13. Log out DTS portal
			14. Repeat from step 2 to 5
			15. Disable the  �Add and manage DTS users� privilege
			16. Repead from step 7 to 12
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the  �Add and manage DTS users� privilege
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		userControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		userControl.logout();
		// 8. Log into DTS portal as above partner user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 9. Click on DTS user's email on top right corner
		userControl.click(PageHome.lbLogout);
		// 10. Select �User Account� item
		userControl.click(PageHome.loginAccount);
		/*
		 * VP: Verify that the �Edit� link is displayed in  �Account Information� page
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.EDIT));
		// 11. Notice for the current privilege of user
		// 12. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		/*
		 * VP: Verify that the �Edit Account� page is displayed, the given name,
		 * family name, title, email, country code, the phone fields and the DTS
		 * Site privilege notification are editable. But with the DTS Site site
		 * privileges section, only display what privileges are displayed in
		 * Account info page and all of them are un-editable.
		 */
		Assert.assertTrue(userControl.isElementEditable(AddUser.getUserInfo()));
		Assert.assertTrue(userControl.isAllNotificationCheckboxEnable(AddUser.DTS_PRIVILEGES_TABLE));
		Assert.assertTrue(userControl.isAllPrivilegeDisable(AddUser.DTS_PRIVILEGES_TABLE));
		// 13. Log out DTS portal
		userControl.logout();
		// 14. Repeat from step 2 to 5
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 15. Disable the  �Add and manage DTS users� privilege
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		userControl.click(AddUser.SAVE);
		// 16. Repeat from step 7 to 12
		userControl.logout();
		// Log into DTS portal as above partner user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// Click on DTS user's email on top right corner
		userControl.click(PageHome.lbLogout);
		// Select �User Account� item
		userControl.click(PageHome.loginAccount);
		// Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		/*
		 * VP: Verify that the �Edit Account� page is displayed, the given name,
		 * family name, title, email, country code, the phone fields and the DTS
		 * Site privilege notification are editable. But with the DTS Site site
		 * privileges section, only display what privileges are displayed in
		 * Account info page and all of them are un-editable.
		 */
		Assert.assertTrue(userControl.isElementEditable(AddUser.getUserInfo()));
		Assert.assertTrue(userControl.isAllNotificationCheckboxEnable(AddUser.DTS_PRIVILEGES_TABLE));
		Assert.assertTrue(userControl.isAllPrivilegeDisable(AddUser.DTS_PRIVILEGES_TABLE));
	}
	
	/*	
	 * Verify that the DTS users can edit their own info including privileges notification but not for Site or DTS Site privilege  even when both �Add and manage DTS users� and �Add and manage DTS users� privileges are enabled or disabled
	 */
	@Test
	public void TCPUSR_29() {
		userControl.addLog("ID : TCPUSR_29 : Verify that the DTS users can edit their own info including privileges notification but not for Site or DTS Site privilege  even when both �Add and manage DTS users� and �Add and manage DTS users� privileges are enabled or disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage DTS users� privilege
			7. Enable the  �Add and manage users� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above partner user successfully
			10. Click on DTS user's email on top right corner
			11. Select �User Account� item
			VP: Verify that the �Edit� link is displayed in  �Account Information� page
			12. Notice for the current privilege of user
			13. Click �Edit� link
			VP: Verify that the �Edit Account� page is displayed, the given name, family name, title, email, country code, the phone fields and the Site or DTS Site privilege notification are editable. But with the Site and DTS Site site privileges section, only display what privileges are displayed in Account info page and all of them are un-editable.
			14. Log out DTS portal
			15. Repeat from step 2 to 5
			16. Disable the  �Add and manage DTS users� privilege
			17. Disable the  �Add and manage users� privilege
			18. Repeat from step 8 to 13
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the �Add and manage DTS users� privilege
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above partner user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Click on DTS user's email on top right corner
		userControl.click(PageHome.lbLogout);
		// 11. Select �User Account� item
		userControl.click(PageHome.loginAccount);
		/*
		 * VP: Verify that the �Edit� link is displayed in  �Account Information� page
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.EDIT));
		// 12. Notice for the current privilege of user
		// 13. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		/*
		 * VP: Verify that the �Edit Account� page is displayed, the given name,
		 * family name, title, email, country code, the phone fields and the
		 * Site or DTS Site privilege notification are editable. But with the
		 * Site and DTS Site site privileges section, only display what
		 * privileges are displayed in Account info page and all of them are
		 * un-editable.
		 */
		Assert.assertTrue(userControl.isElementEditable(AddUser.getUserInfo()));
		Assert.assertTrue(userControl.isAllNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE));
		Assert.assertTrue(userControl.isAllPrivilegeDisable(AddUser.PRIVILEGES_TABLE));
		Assert.assertTrue(userControl.isAllNotificationCheckboxEnable(AddUser.DTS_PRIVILEGES_TABLE));
		Assert.assertTrue(userControl.isAllPrivilegeDisable(AddUser.DTS_PRIVILEGES_TABLE));
		// 14. Log out DTS portal
		userControl.logout();
		// 15. Repeat from step 2 to 5
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 16. Disable the  �Add and manage DTS users� privilege
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 17. Disable the  �Add and manage users� privilege
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 18. Repeat from step 8 to 13
		userControl.logout();
		// Log into DTS portal as above partner user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// Click on DTS user's email on top right corner
		userControl.click(PageHome.lbLogout);
		// Select �User Account� item
		userControl.click(PageHome.loginAccount);
		// Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		/*
		 * VP: Verify that the �Edit Account� page is displayed, the given name,
		 * family name, title, email, country code, the phone fields and the
		 * Site or DTS Site privilege notification are editable. But with the
		 * Site and DTS Site site privileges section, only display what
		 * privileges are displayed in Account info page and all of them are
		 * un-editable.
		 */
		Assert.assertTrue(userControl.isElementEditable(AddUser.getUserInfo()));
		Assert.assertTrue(userControl.isAllNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE));
		Assert.assertTrue(userControl.isAllPrivilegeDisable(AddUser.PRIVILEGES_TABLE));
		Assert.assertTrue(userControl.isAllNotificationCheckboxEnable(AddUser.DTS_PRIVILEGES_TABLE));
		Assert.assertTrue(userControl.isAllPrivilegeDisable(AddUser.DTS_PRIVILEGES_TABLE));
	}
	
	/*	
	 * Verify that user is unable to change his own email address when both "Add and manage user" and "Add and manage DTS user" privilege are disabled.
	 */
	@Test
	public void TCPUSR_30() {
		userControl.addLog("ID : TCPUSR_30 : Verify that user is unable to change his own email address when both 'Add and manage user' and 'Add and manage DTS user' privilege are disabled.");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Disable the �Add and manage DTS users� privilege
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the �Add and manage users� privilege
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above partner user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		//VP: Verify that the users tab is not displayed
		Assert.assertFalse(userControl.isElementPresent(PageHome.LINK_USERS));
		// 10. Click on DTS user's email on top right corner
		userControl.click(PageHome.lbLogout);
		// 11. Select �User Account� item
		userControl.click(PageHome.loginAccount);
		// 12. Notice for the current privilege of user
		// 13. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		/*
			VP: Verify that the email address field is disabled
		 */
		Assert.assertFalse(userControl.isElementEditable(AddUser.EMAIL));
	}
	
	/*	
	 * Verify that user is able to change his own email address when "Add and manage user" privilege is enabled but "Add and manage DTS" user privilege is disabled
	 */
	@Test
	public void TCPUSR_31() {
		userControl.addLog("ID : TCPUSR_31 : Verify that user is able to change his own email address when 'Add and manage DTS user' privilege is disabled but 'Add and manage user' user privilege is enabled");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Disable the �Add and manage DTS users� privilege
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Enable the �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above partner user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Click on DTS user's email on top right corner
		userControl.click(PageHome.lbLogout);
		// 11. Select �User Account� item
		userControl.click(PageHome.loginAccount);
		// 12. Notice for the current privilege of user
		// 13. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		/*
			VP: Verify that the email address field is enabled
		 */
		Assert.assertTrue(userControl.isElementEditable(AddUser.EMAIL));
		//10. Change user own email address to another
		//11. Click "Save" link
		String newemail="dtsauto1@mailinator.com";
		userControl.editData(AddUser.EMAIL, newemail);
		userControl.click(AddUser.SAVE);
		
		//VP: Verify that user is kicked out the portal after the change is saved
		Assert.assertTrue(userControl.isElementPresent(PageLogin.SIGN_IN));
		//12. Log into DTS portal with old email address
		loginControl.login(DTS_USER, DTS_PASSWORD);
		//VP: User is unable to log into DTS portal with old email address
		Assert.assertTrue(userControl.isElementPresent(PageLogin.SIGN_IN));
		
		
		//Teardown
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(newemail);
		// Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// enable the  �Add and manage DTS users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		//change email address back to normal
		userControl.editData(AddUser.EMAIL, DTS_USER);
		userControl.click(AddUser.SAVE);
	}
	
	
	/*	
	 * Verify that user is able to change his own email address when "Add and manage user" privilege is disabled but "Add and manage DTS" user privilege is enabled
	 */
	@Test
	public void TCPUSR_32() {
		userControl.addLog("ID : TCPUSR_32 : Verify that user is able to change his own email address when 'Add and manage user' privilege is disabled but 'Add and manage DTS' user privilege is enabled");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the �Add and manage DTS users� privilege
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		// 7. Disable the �Add and manage users� privilege
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above partner user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 10. Click on DTS user's email on top right corner
		userControl.click(PageHome.lbLogout);
		// 11. Select �User Account� item
		userControl.click(PageHome.loginAccount);
		// 12. Notice for the current privilege of user
		// 13. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		/*
			VP: Verify that the email address field is enabled
		 */
		Assert.assertTrue(userControl.isElementEditable(AddUser.EMAIL));
		//10. Change user own email address to another
		//11. Click "Save" link
		String newemail="dtsauto1@mailinator.com";
		userControl.editData(AddUser.EMAIL, newemail);
		userControl.click(AddUser.SAVE);
		
		//VP: Verify that user is kicked out the portal after the change is saved
		Assert.assertTrue(userControl.isElementPresent(PageLogin.SIGN_IN));
		//12. Log into DTS portal with old email address
		loginControl.login(DTS_USER, DTS_PASSWORD);
		//VP: User is unable to log into DTS portal with old email address
		Assert.assertTrue(userControl.isElementPresent(PageLogin.SIGN_IN));
		
		
		//Teardown
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(newemail);
		// Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// Disable the  �Add and manage users� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		//change email address back to normal
		userControl.editData(AddUser.EMAIL, DTS_USER);
		userControl.click(AddUser.SAVE);
	}
	
}
