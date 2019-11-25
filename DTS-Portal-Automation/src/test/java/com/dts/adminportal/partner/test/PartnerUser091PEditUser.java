package com.dts.adminportal.partner.test;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.PartnerListUser;
import dts.com.adminportal.model.PartnerUserEdit;
import dts.com.adminportal.model.PartnerUserInfo;
import dts.com.adminportal.model.UserInfo;
import dts.com.adminportal.model.PartnerUserMgmt;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.UsersList;
import dts.com.adminportal.model.Xpath;

@Test
public class PartnerUser091PEditUser extends CreatePage{
	private HomeController home;
	String email = "Email"+ RandomStringUtils.randomNumeric(10)+"@infonam.com";
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}
	@BeforeMethod
	public void loginBeforeTest() {
		result = home.login(superpartneruser, superpartnerpassword);
	}
	/*Verify that the 091 Edit User page displays user's information correctly
	 */
	public void TC091PEU_01(){
		result.addLog("ID : TC091PEU_01 : Verify that the 091 Edit User page displays user's information correctly");
		
		/*
		Pre-condition: partner user has "Add and Manage Users" rights.

		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click "Users" tab
		4. Select a user from users table
		5. Click "Edit" link
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Select a user from users table
		result = home.selectRowAt(PartnerListUser.TBODY, 0, PartnerUserMgmt.EDIT);
		Assert.assertEquals("Pass", result.getResult());
		// 5. Click "Edit" link
		home.click(UserInfo.EDIT);
		// verify user info 
		result = home.existsElement(PartnerUserEdit.getHash());
		Assert.assertEquals("Pass", result.getResult());
				
			}
	/*Verify that the site privileges table in 091P Edit User page is editable.
	 */
	public void TC091PEU_02(){
		result.addLog("ID : TC091PEU_02 : Verify that the site privileges table in 091P Edit User page is editable.");
		
		/*
		Pre-condition: partner user has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Select a user from users table
			5. Try to edit the user's site privileges table
		 */
				// 3. Click "Users" tab
				home.click(Xpath.LINK_PARTNER_USER);
				// 4. Select a user from users table
				home.selectUserByEmail(partneruser);
				// 5. Click "Edit" link
				home.click(UserInfo.EDIT);
				// verify user info 
				result = home.existsElement(PartnerUserEdit.getHash());
				Assert.assertEquals("Pass", result.getResult());
				/*
				 * Verify that the site privileges table in 091P Edit User page is editable.
				 */
				Assert.assertTrue(home.isAllPrivilegeEnable(AddUser.PRIVILEGES_TABLE));
			
			}
	
	/*
	 * Verify that the site privileges table in 091P Edit User page is editable.
	 */
	public void TC091PEU_03(){
		result.addLog("ID : TC091PEU_03 : Verify that the notification table in 091P Edit User page is editable.");
		/*
			Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Select a user from users table
			5. Click "Edit" link
			6. Try to edit the user's notification table
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Select a user from users table
		home.selectUserByEmail(partneruser);
		// 5. Click "Edit" link
		home.click(UserInfo.EDIT);
		// verify user info 
		result = home.existsElement(PartnerUserEdit.getHash());
		Assert.assertEquals("Pass", result.getResult());
		// enable all privilege
		home.enableAllPrivilege();
		// 6. Try to edit the user's notification table
		home.enableAllnotification();
	}
	
	public void TC091PEU_04(){
		result.addLog("ID : TC091PEU_04 : Verify that the 'Actions' module is displayed with 'Save' and 'Cancel' link");
		
		/*
		Pre-condition: partner user has "Add and Manage Users" rights.

		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click "Users" tab
		4. Select a user from users table
		5. Click "Edit" link
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Select a user from users table
		home.selectUserByEmail(partneruser);
		// 5. Click "Edit" link
		home.click(UserInfo.EDIT);
		// verify user info
		result = home.existsElement(PartnerUserEdit.getActions());
		Assert.assertEquals("Pass", result.getResult());

	}
	
	/*
	 * 	Verify that the partner user who has "Add and manage users" rights could edit company's brand info successfully.
	 */
	public void TC091PEU_05() throws InterruptedException{
		result.addLog("ID : TC091PEU_05 : Verify that the partner user who has 'Add and manage users' rights could edit company's brand info successfully.");
		/*
		  	Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Select a user from users table
			5. Click "edit" link
			6. Change some user's information
			7. Click "Save" link
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Select a user from users table
		home.selectUserByEmail(partneruser);
		// 5. Click "edit" link
		home.click(UserMgmt.EDIT);
		//Sleep to wait for data displayed before deleted
		Thread.sleep(5000);		
		// 6. Change some user's information
		String newTitle = "New title" + RandomStringUtils.randomNumeric(5);
		home.editData(AddUser.TITLE, newTitle);
		// 7. Click "Save" link
		home.click(AddUser.SAVE);
		/*
		 * Verify that The portal is redirected to 090P User info page with new user's information
		 */
		Assert.assertEquals(home.getTextByXpath(UserMgmt.TITLE), newTitle);
	}

	/*
	 * Verify that new change is not effected when user cancels editing user's info.
	 */
	public void TC091PEU_06(){
		result.addLog("ID : TC091PEU_06 : Verify that new change is not effected when user cancels editing user's info ");
		/*
		  	Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Select a user from users table
			5. chick "edit" link
			6. Change some user's information
			7. Click "Cancel" link
		 */
			//3. Click "Users" tab
			home.click(Xpath.LINK_PARTNER_USER);
			// click add user
			// 4. Select a user from users table
			home.selectUserByEmail(partneruser);
			String title = home.getTextByXpath(PartnerUserMgmt.TITLE);
			//5. chick "edit" link
			home.click(UserInfo.EDIT);
			//6. Change some user's information
			home.type(PartnerUserEdit.TITLE, title + RandomStringUtils.randomAlphabetic(10));
			//7. Click "Cancel" link
			home.click(PartnerUserEdit.CANCEL);
			String titleInfo = home.getTextByXpath(PartnerUserInfo.TITLE);
			Assert.assertEquals(title, titleInfo);
	}
	
	/*
	 * Verify that the brand privileges are displayed correctly when editing user
	 */
	public void TC091PEU_07(){
		result.addLog("ID : TC091PEU_07 : Verify that the brand privileges are displayed correctly when editing user");
		/*
		  	Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
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
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Click “Add New User” link
		home.click(UsersList.ADD_USER);
		// 5. Fill valid value into all required fields
		// 6. Assign a specific brand for each user's privileges
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.partnerUser();
		home.addUser(AddUser.getPartnerUser(), data);
		/*
		 * VP: Verify that new user is created successfully
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// 8. Navigate to “Users” page again
		home.click(Xpath.LINK_PARTNER_USER);
		// 9. Select the new user from Users table
		home.selectUserByEmail(data.get("email"));
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
