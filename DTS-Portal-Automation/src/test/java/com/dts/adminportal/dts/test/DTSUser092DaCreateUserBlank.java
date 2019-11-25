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
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.DTSSitePrivilege;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.UsersList;
import dts.com.adminportal.model.Xpath;

public class DTSUser092DaCreateUserBlank extends CreatePage{
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
	 Verify that the DTS Site Privilege table is displayed with correct option.
	 */
	@Test
	public void TC092DaCUB_01(){
		result.addLog("ID : TC092DaCUB_01 : Verify that the DTS Site Privilege table is displayed with correct option.");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid value into all required fields
			6. Select "DTS Inc." item in "Company" field
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill valid value into all required fields
		// 6. Select "DTS Inc." item in "Company" field
		Hashtable<String,String> data = TestData.dtsUser();
		data.remove("save");
		home.addUser(AddUser.getDTSUser(), data);
		/* 
		  	The DTS Site Privilege is displayed with following options:
			Approve product tunings
			Approve marketing information
			Manage audio routes
			Add and manage apps and devices
			Publish and suspend apps and devices
			Add and manage promotions
			Add and manage company accounts
			Add and manage DTS users
		 */
		ArrayList<String> privileges = home.getPrivileges(AddUser.DTS_PRIVILEGES_TABLE);
		Assert.assertTrue(DTSUtil.containsAll(privileges, AddUser.DTS_PRIVILEGES));
	}
	
	/*
	 Verify that the similar DTS site privilege notification is also enable when selecting DTS site privilege.
	 */
	@Test
	public void TC092DaCUB_02(){
		result.addLog("ID : TC092DaCUB_02 : Verify that the similar DTS site privilege notification is also enable when selecting DTS site privilege.");
		
		/*
			Pre-condition: User has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid value into all required fields
			6. Select "DTS Inc." item in "Company" field
			VP: Verify that the DTS site privilege table is displayed
			7. Select a DTS site privilege
		 */
		
		//3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// init info
		Hashtable<String, String> user = new Hashtable<String, String>();
		user.put("firstName", RandomStringUtils.randomAlphabetic(10));
		user.put("lastName", RandomStringUtils.randomAlphabetic(5));
		user.put("company", "DTS Inc.");
		user.put("title", RandomStringUtils.randomAlphabetic(20));
		user.put("email", RandomStringUtils.randomNumeric(10)+"@infonam.com");
		user.put("code", RandomStringUtils.randomNumeric(5));
		user.put("phone", RandomStringUtils.randomNumeric(10));
		Assert.assertTrue(home.fillDataAddUser(AddUser.getDTSUser(), user));
		// VP: Verify that the DTS site privilege table is displayed
		Assert.assertTrue(home.isElementPresent(AddUser.DTS_PRIVILEGES_TABLE));
		// 7. Select a DTS site privilege
		ArrayList<DTSSitePrivilege> list = home.getDTSSitePrivileges(AddUser.DTS_PRIVILEGES_TABLE);
		Assert.assertTrue(list.size() > 0);
		home.enableDTSSitePrivilege(list.get(0));
		/*
		 * The similar DTS site privilege notification is enabled.
		 */
		Assert.assertTrue(home.isCheckBoxEnable(list.get(0).notifications));
	}
	/*
	 Verify that the similar DTS site privilege notification is disabled when un-selecting DTS site privilege.
	 */
	@Test
	public void TC092DaCUB_03(){
		result.addLog("ID : TC092DaCUB_03 : Verify that the similar DTS site privilege notification is disabled when un-selecting DTS site privilege.");
		
		/*
			Pre-condition: User has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid value into all required fields
			6. Select "DTS Inc." item in "Company" field
			VP: Verify that the DTS site privilege table is displayed
			7. Select a DTS site privilege
			VP: The similar notification is enabled
			8. Un-select a DTS site privilege
		 */
		
		//3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// init info
		Hashtable<String, String> user = new Hashtable<String, String>();
		user.put("firstName", RandomStringUtils.randomAlphabetic(10));
		user.put("lastName", RandomStringUtils.randomAlphabetic(5));
		user.put("company", "DTS Inc.");
		user.put("title", RandomStringUtils.randomAlphabetic(20));
		user.put("email", RandomStringUtils.randomNumeric(10)+"@infonam.com");
		user.put("code", RandomStringUtils.randomNumeric(5));
		user.put("phone", RandomStringUtils.randomNumeric(10));
		Assert.assertTrue(home.fillDataAddUser(AddUser.getDTSUser(), user));
		// VP: Verify that the DTS site privilege table is displayed
		Assert.assertTrue(home.isElementPresent(AddUser.DTS_PRIVILEGES_TABLE));
		// 7. Select a DTS site privilege
		ArrayList<DTSSitePrivilege> list = home.getDTSSitePrivileges(AddUser.DTS_PRIVILEGES_TABLE);
		Assert.assertTrue(list.size() > 0);
		home.enableDTSSitePrivilege(list.get(0));
		/*
		 * The similar DTS site privilege notification is enabled.
		 */
		Assert.assertTrue(home.isCheckBoxEnable(list.get(0).notifications));
		// 8. Un-select a DTS site privilege
		home.disableDTSSitePrivilege(list.get(0));
		/*
		 * The similar DTS site privilege notification is disabled.
		 */
		Assert.assertFalse(home.isCheckBoxEnable(list.get(0).notifications));
	}
	/*
	 Verify that the "Given Name", "Family Name", "Email", "Company" and "Phone" are required when creating new users
	 */
	@Test
	public void TC092DaCUB_04(){
		result.addLog("ID : TC092DaCUB_04 : Verify that the 'Given Name', 'Family Name', 'Email', 'Company' and 'Phone' are required when creating new users");
		
		/*
			Pre-condition: User has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Click "Save" link without filling information into  
			"Given Name", "Family Name", "Email", "Company" and "Phone"
		 */
		
		//3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		home.selectOptionByName(AddUser.BTN_COMPANY, "Select Company");
		// 5. Click "Save"
		home.click(AddUser.SAVE);
		/*
		 * There is an error message  displayed next to 
		 * "Given Name", 
		 * "Family Name", 
		 * "Email", 
		 * "Company" and 
		 * "Phone" 
		 * which mentions to requirement of information.
		 */
		Assert.assertTrue(home.existsElement(AddUser.REQUIRES));
	}
	
	/*
	 Verify that the site privileges display rights correctly.
	 */
	@Test
	public void TC092DaCUB_05(){
		result.addLog("ID : TC092DaCUB_05 : Verify that the site privileges display rights correctly.");
		
		/*
			Pre-condition: User has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
		 */
		
		//3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// init info
		Hashtable<String, String> user = new Hashtable<String, String>();
		user.put("firstName", RandomStringUtils.randomAlphabetic(10));
		user.put("lastName", RandomStringUtils.randomAlphabetic(5));
		user.put("company", "DTS Inc.");
		user.put("title", RandomStringUtils.randomAlphabetic(20));
		user.put("email", RandomStringUtils.randomNumeric(10)+"@infonam.com");
		user.put("code", RandomStringUtils.randomNumeric(5));
		user.put("phone", RandomStringUtils.randomNumeric(10));
		Assert.assertTrue(home.fillDataAddUser(AddUser.getDTSUser(), user));
		/*
		 * 092Da Create User Blank is displayed. 
		 * The site privileges have following rights: 
		 	Add and manage accessories
 			Publish and suspend accessories
 			Request accessory tunings
			View publication credits
			Edit Company Info
			Edit brand info
			Add and manage users
		 */
		ArrayList<String> privileges = home.getPrivileges(AddUser.PRIVILEGES_TABLE);
		Assert.assertTrue(DTSUtil.containsAll(privileges, AddUser.PRIVILEGES));
	}
	
	/*
	 Verify that new user could not be created with without selecting company.
	 */
	@Test
	public void TC092DaCUB_06(){
		result.addLog("ID : TC092DaCUB_06 : Verify that new user could not be created with without selecting company.");
		
		/*
			Pre-condition: User has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill all valid values into required fields except Email
			6. Do not select company
			7. Click "Save" link
		 */
		
		//3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill all valid values into required fields except Email
		// init info
		Hashtable<String, String> user = new Hashtable<String, String>();
		user.put("firstName", RandomStringUtils.randomAlphabetic(10));
		user.put("lastName", RandomStringUtils.randomAlphabetic(5));
		user.put("company", "DTS Inc.");
		user.put("title", RandomStringUtils.randomAlphabetic(20));
		user.put("email", RandomStringUtils.randomNumeric(10)+"@infonam.com");
		user.put("code", RandomStringUtils.randomNumeric(5));
		user.put("phone", RandomStringUtils.randomNumeric(10));
		Assert.assertTrue(home.fillDataAddUser(AddUser.getDTSUser(), user));
		// 6. Do not select company
		home.selectOptionByName(AddUser.BTN_COMPANY, "Select Company");
		// 7. Click "Save"
		home.click(AddUser.SAVE);
		/*
		 There is an error message displayed which mentions requirement of selecting company.
		 */
		Assert.assertTrue(home.existsElement(AddUser.REQUIRES[2]));
	}
	/*
	 Verify that new user could not be created with already exists email address.
	 */
	@Test
	public void TC092DaCUB_07(){
		result.addLog("ID : TC092DaCUB_07 : Verify that new user could not be created with already exists email address.");
		
		/*
			Pre-condition: User has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill all valid values into required fields except Email
			6. Fill an email address into "Email" text field which is assigned to another user's account 
			7. Click "Save" link
		 */
		
		//3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill all valid values into required fields except Email
		// init info
		Hashtable<String, String> user = new Hashtable<String, String>();
		user.put("firstName", RandomStringUtils.randomAlphabetic(10));
		user.put("lastName", RandomStringUtils.randomAlphabetic(5));
		user.put("company", "DTS Inc.");
		user.put("title", RandomStringUtils.randomAlphabetic(20));
		user.put("email", RandomStringUtils.randomNumeric(10)+"@infonam.com");
		user.put("code", RandomStringUtils.randomNumeric(5));
		user.put("phone", RandomStringUtils.randomNumeric(10));
		Assert.assertTrue(home.fillDataAddUser(AddUser.getDTSUser(), user));
		// 6. Fill an email address into "Email" text field which is assigned to another user's account
		home.editData(AddUser.EMAIL, dtsUser);
		// 7. Click "Save"
		home.click(AddUser.SAVE);
		/*
		 There is an error message displayed which mentions to 
		 an already exists email address. 
		 The message look like "An account with this email address already exists"
		 */
		// Select a user from users table
		Assert.assertTrue(home.isElementPresent(AddUser.CODE));
	}
	
	/*
	 Verify that the DTS Site Privileges only displays when the company is "DTS Inc.".
	 */
	@Test
	public void TC092DaCUB_08(){
		result.addLog("ID : TC092DaCUB_08 : Verify that the DTS Site Privileges only displays when the company is 'DTS Inc'.");
		
		/*
			Pre-condition: User has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			VP: Verify that the "DTS Site Privileges" is not displayed
			5. Set the company dropdown to "DTS Inc."
		 */
		
		//3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		home.selectOptionByName(AddUser.BTN_COMPANY, "Select Company");
		// VP: Verify that the "DTS Site Privileges" is not displayed
		Assert.assertTrue(!home.existsElement(AddUser.DTS_PRIVILEGES_TABLE));
		// 5. Set the company dropdown to "DTS Inc."
		home.selectOptionByName(AddUser.BTN_COMPANY, "DTS Inc.");
		/*
		 * The "DTS Site Privileges" table is displayed.
		 */
		Assert.assertTrue(home.existsElement(AddUser.DTS_PRIVILEGES_TABLE));
	}
	/*
	 Verify that all Site and DTS Site Privileges are selected by default when creating new users.
	 */
	@Test
	public void TC092DaCUB_09(){
		result.addLog("ID : TC092DaCUB_09 : Verify that all Site and DTS Site Privileges are selected by default when creating new users.");
		
		/*
			Pre-condition: User has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Set the company dropdown to "DTS Inc."
		 */
		
		//3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Set the company dropdown to "DTS Inc."
		home.selectOptionByName(AddUser.BTN_COMPANY, "DTS Inc.");
		/*
		 * All Site and DTS Site Privileges are selected.
		 */
		ArrayList<DTSSitePrivilege> list = home.getDTSSitePrivileges(AddUser.DTS_PRIVILEGES_TABLE);
		Assert.assertTrue(home.isSelectedAllPrivileges(list));
	}
	
	/*
	 * Verify that the user could not add new user with invalid email address format
	 */
	@Test
	public void TC092DaCUB_10(){
		result.addLog("ID : TC092DaCUB_10 : Verify that the user could not add new user with invalid email address format");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill all valid values into required fields except Email
			6. Click "Save" link
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill all valid values into required fields except Email
		Hashtable<String,String> data = TestData.dtsUser();
		data.put("email", RandomStringUtils.randomAlphanumeric(20) + ".example.com");
		data.remove("save");
		home.addUser(AddUser.getDTSUser(), data);
		// 6. Click "Save" link
		home.click(AddUser.SAVE);
		/*
		 * Verify that There is an error message displayed which mention to the incorrect format of email address
		 */
		Assert.assertTrue(home.checkMessageDisplay("! Email is not right"));
	}
	
	/*
	 * Verify that new user could be created successfully with a site privilege.
	 */
	@Test
	public void TC092DaCUB_12(){
		result.addLog("ID : TC092DaCUB_12 : Verify that new user could be created successfully with a site privilege.");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign any site privilege for new user
			7. Click "Save" link
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill valid values into required fields but not assign any site privilege
		// 6. Assign any site privilege for new user
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.dtsUser();
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * Verify that New user could be created successfully
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// Delete user
		home.click(Xpath.LINK_USERS);
		home.deleteUserByEmail(data.get("email"));
	}
	
	/*
	 * Verify that new user could be created without assigning site notifications
	 */
	@Test
	public void TC092DaCUB_13(){
		result.addLog("ID : TC092DaCUB_13 : Verify that new user could be created without assigning site notifications");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign any site privilege for new user
			7. Do not assign any site notification
			8. Click "Save" link
		 */
		//3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill valid values into required fields
		// 6. Assign any site privilege for new user
		Hashtable<String,String> data = TestData.partnerUser();
		data.remove("save");
		home.addUser(AddUser.getDTSUser(), data);
		// 7. Do not assign any site notification
		home.uncheckAllNotification(AddUser.PRIVILEGES_TABLE);
		// 8. Click "Save" link
		home.click(AddUser.SAVE);
		/*
		 * Verify that New user could be created successfully without assigning site notification
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// Delete user
		home.click(Xpath.LINK_USERS);
		home.deleteUserByEmail(data.get("email"));
	}
	
	/*
	 * Verify that the similar site notification is also be enabled when assigning a site privilege.
	 */
	@Test
	public void TC092DaCUB_14(){
		result.addLog("ID : TC092DaCUB_14 : Verify that the similar site notification is also be enabled when assigning a site privilege.");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign a site privilege for new user
		 */
		//3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill valid values into required fields
		Hashtable<String,String> data = TestData.partnerUser();
		data.remove("save");
		home.addUser(AddUser.getDTSUser(), data);
		// 6. Assign any site privilege for new user
		home.disableAllPrivilege(AddUser.PRIVILEGES_TABLE);
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		/*
		 * Verify that The similar site notification of assigned site privilege is enabled
		 */
		Assert.assertTrue(home.isNotificationCheckboxSelected(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products));
	}
	
	/*
	 * Verify that the similar site notification is also be disabled when disabling a site privilege.
	 */
	@Test
	public void TC092DaCUB_15(){
		result.addLog("ID : TC092DaCUB_15 : Verify that the similar site notification is also be disabled when disabling a site privilege.");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign a site privilege for new user
			VP: The similar site notification is also checked
			7. Disable above site privilege
		 */
		//3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill valid values into required fields
		// 6. Assign a site privilege for new user
		Hashtable<String,String> data = TestData.partnerUser();
		data.remove("save");
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * VP: The similar site notification is also checked
		 */
		Assert.assertTrue(home.isAllNotificationCheckboxSelected(AddUser.PRIVILEGES_TABLE));
		// 7. Disable above site privilege
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		/*
		 * The similar site notification of assigned site privilege is also disabled
		 */
		Assert.assertFalse(home.isNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products));
	}
	
	/*
	 * Verify that the brand privileges are displayed correctly when editing user
	 */
	@Test
	public void TC092DaCUB_16(){
		result.addLog("ID : TC092DaCUB_16 : Verify that the brand privileges are displayed correctly when editing user");
		/*
			Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click “Add New User” link
			5. Fill valid value into all required fields
			6. Assign a specific brand for each user's privileges
			7. Click "Save" link
			VP: Verify that new user is created successfully
			8. Navigate to “Users” page again
			9. Select the new user from Users table
			10. Click “Edit” link
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Click “Add New User” link
		home.click(UsersList.ADD_USER);
		// 5. Fill valid value into all required fields
		// 6. Assign a specific brand for each user's privileges
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.dtsUser();
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * VP: Verify that new user is created successfully
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// 8. Navigate to “Users” page again
		home.click(Xpath.LINK_USERS);
		// 9. Select the new user from Users table
		home.dtsSelectUserByEmail(data.get("email"));
		// 10. Click “Edit” link
		home.click(UserMgmt.EDIT);
		/*
		 * Verify that The assigned brands for each privilege of user are displayed correctly
		 */
		Assert.assertTrue(home.isBrandPrivilegeSelected(AddUser.BRAND_PRIVILEGES, data.get("brand")));
		// Delete user
		home.click(AddUser.CANCEL);
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	
	
}



