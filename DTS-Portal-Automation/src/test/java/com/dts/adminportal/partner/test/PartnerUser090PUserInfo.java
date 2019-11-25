package com.dts.adminportal.partner.test;

import java.util.ArrayList;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;

import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.PartnerListUser;
import dts.com.adminportal.model.PartnerUserInfo;
import dts.com.adminportal.model.PartnerUserMgmt;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UserInfo;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.Xpath;

public class PartnerUser090PUserInfo extends CreatePage{
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
	/*
	 Verify that the 090D User Info page is displayed when user the partner select a user from users table
	 */
	@Test
	public void TC090PUI_01(){
		result.addLog("ID : TC090PUI_01 : Verify that the 090P User Info page is displayed when user the partner select a user from users table");
		
		/*
		Pre-condition: User has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Click "Users" tab
			4. Select a user from users table
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		
		// 4. Select a user from users table
		result = home.selectRowAt(PartnerListUser.TBODY, 0, PartnerUserMgmt.EDIT);
		Assert.assertEquals("Pass", result.getResult());
		// verify that The 090 User Info page is displayed
		Assert.assertTrue(home.isElementPresent(UserMgmt.USER_INFO_PAGE_TITLE));
	}
	
	/*
	 Verify that the 090P User Info page displays  user's information properly
	 */
	@Test
	public void TC090PUI_02(){
		result.addLog("ID : TC090PUI_02 : Verify that the 090P User Info page displays  user's information properly");
		
		/*
		Pre-condition: User has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a user from users table
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// get user info
		ArrayList<PartnerUserInfo> PartnerUser = home.getPartnerUsers(PartnerListUser.TBODY);
		// 4. Select a user from users table
		result = home.selectRowAt(PartnerListUser.TBODY, 0,	PartnerUserMgmt.EDIT);
		Assert.assertEquals("Pass", result.getResult());
		// verify user info
		result = home.checkPartnerUserInfo(PartnerUser.get(0), PartnerUserMgmt.getElementsInfo());
		Assert.assertEquals("Pass", result.getResult());
	}
	
	/*
	 Verify that the site privileges info displayed correctly according to user's assigned site privileges
	 */
	@Test
	public void TC090PUI_03(){
		result.addLog("ID : TC090PUI_03 : Verify that the site privileges info displayed correctly according to user's assigned site privileges");
		
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
		
		//3.Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);		
		// 4. Select a user from users table
		home.selectUserByEmail(partneruser);
		// 5. Click "Edit" link
		home.click(UserInfo.EDIT);
		// 6. Select some options in "Site Privileges" section
		home.disableAllPrivilege(AddUser.PRIVILEGES_TABLE);
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		// Save
		home.click(AddUser.SAVE);;
		// 7. Log out DTS portal
		home.logout();
		//8. Log into DTS portal as a partner admin who have the same company with above user
		home.login(partneruser, password);
		//9.Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);	
		// 10. Select above user from users table
		home.selectUserByEmail(partneruser);
		/*
		 * Verify that the site privileges info displayed correctly according to user's assigned site privileges
		 */
		//get user's privileges
		ArrayList<String> userprivilege= home.getPrivileges(UserMgmt.SITE_PRIVILEGES_TABLE);
		Assert.assertTrue(userprivilege.contains(Privileges.Add_and_manage_products));
		Assert.assertTrue(userprivilege.contains(Privileges.Add_and_manage_user));
		
		//Restore user's privileges
		home.logout();
		home.login(superpartneruser, superpartnerpassword);
		home.click(Xpath.LINK_PARTNER_USER);
		home.selectUserByEmail(partneruser);
		home.click(UserInfo.EDIT);
		home.enableAllPrivilege();
		home.click(AddUser.SAVE);
	}
	
	/*
	 Verify that the "Actions" module shows up "Edit", "Delete Account" and "Reset Password" links
	 */
	@Test
	public void TC090PUI_04(){
		result.addLog("ID : TC090PUI_04 : Verify that the 'Actions' module shows up 'Edit', 'Cancel', 'Delete Account', 'Reset Password', 'Restore' and 'Suspend' links.");
		
		/*
		Pre-condition: partner user has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Select a user from users table
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// get user info
		ArrayList<PartnerUserInfo> users = home.getPartnerUsers(PartnerListUser.TBODY);
		// select user at first
		result = home.selectRowAt(PartnerListUser.TBODY, 0, PartnerUserMgmt.EDIT);
		Assert.assertEquals("Pass", result.getResult());
		// verify user info 
		result = home.checkPartnerUserInfo(users.get(0), PartnerUserMgmt.getElementsInfo());
		Assert.assertEquals("Pass", result.getResult());
		
		System.out.println("step2");
		/*
		 Verify that the "Actions" module shows up "Edit", "Delete Account" and "Reset Password" links
		 */
		result = home.existsElement(PartnerUserMgmt.getActions());
		Assert.assertEquals("Pass", result.getResult());
	}
	
	/*
	 Verify that there is a delete confirmation dialog with 'Delete' and 'Cancel' button displayed when deleting a user
	 */
	
	@Test
	public void TC090PUI_05(){
		result.addLog("ID : TC090PUI_05 : Verify that there is a delete confirmation dialog with 'Delete' and 'Cancel' button displayed when deleting a user.");
		
		/*
		Pre-condition: partner user has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Select a user from users table
			5. Click "Delete Account" link in "Actions" module
		 */
		
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// get user info
		ArrayList<PartnerUserInfo> users = home.getPartnerUsers(PartnerListUser.TBODY);
		// 4. Select a user from users table
		result = home.selectRowAt(PartnerListUser.TBODY, 0, PartnerUserMgmt.EDIT);
		Assert.assertEquals("Pass", result.getResult());
		// verify user info 
		result = home.checkPartnerUserInfo(users.get(0), PartnerUserMgmt.getElementsInfo());
		Assert.assertEquals("Pass", result.getResult());
		// check Actions
		result = home.existsElement(PartnerUserMgmt.getActions());
		Assert.assertEquals("Pass", result.getResult());
		// 5. Click "Delete Account" link in "Actions" module
		home.click(PartnerUserMgmt.DELETE_ACCOUNT);
		/*
		 Verify that there is a delete confirmation dialog with 'Delete' and 'Cancel' button displayed when deleting a user
		 */
		String confirm[] = home.getConfirm();
		System.out.println("confirm : "+ confirm[0] + " : " + confirm[1]);
		Boolean isCorrectConfirm = confirm[1].equals("Delete") && confirm[0].equals("Cancel");
		Assert.assertTrue(isCorrectConfirm);
	}
	
	/*
	 Verify that user account is not deleted when clicking "Cancel" button on delete confirmation dialog.
	 */
	@Test
	public void TC090PUI_08(){
		result.addLog("ID : TC090PUI_08 : Verify that user account is not deleted when clicking 'Cancel' button on delete confirmation dialog.");
		
		/*
			Pre-condition: User has "Add and Manage Users" rights.

			
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Select a user from users table
			5. Click "Delete Account" link in "Actions" module
			6. Click "Cancel" button in delete confirmation dialog
		 */
		
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// get user info
		ArrayList<PartnerUserInfo> users = home.getPartnerUsers(PartnerListUser.TBODY);
		// 4. Select a user from users table
		result = home.selectRowAt(PartnerListUser.TBODY, 0, PartnerUserMgmt.EDIT);
		Assert.assertEquals("Pass", result.getResult());
		// verify user info 
		result = home.checkPartnerUserInfo(users.get(0), PartnerUserMgmt.getElementsInfo());
		Assert.assertEquals("Pass", result.getResult());
		// check Actions
		result = home.existsElement(PartnerUserMgmt.getActions());
		Assert.assertEquals("Pass", result.getResult());
		// 5. Click "Delete Account" link in "Actions" module
		home.click(PartnerUserMgmt.DELETE_ACCOUNT);
		// 6. Click "Cancel" button in delete confirmation dialog
		home.switchWindowClickOption("Cancel");
		/*
		 * The delete confirmation dialog disappeared and the portal stays at 090P User Info page.
		 */
		String confirm[] = home.getConfirm();
		// The delete confirmation dialog disappeared
		Assert.assertTrue(confirm[0] == null);
		// the portal stays at 090P User Info page.
		Boolean isUserInfPage = home.isElementPresent(PartnerUserMgmt.FIRSTNAME);
		Assert.assertTrue(isUserInfPage);
	}
	
	/*
	 Verify that the site privileges table in 090D User Info page is read only.
	 */
	@Test
	public void TC090PUI_11(){
		

		result.addLog("ID : TC090PUI_11 : Verify that the site privileges table in 090P User Info page is read only");
		

		/*
		Pre-condition: partner user has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Select a user from users table
			5. Verify that 090P User Info page is displayed
			6. Try to edit the user's site privileges table
		*/
		

		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		ArrayList<PartnerUserInfo> users = home.getPartnerUsers(PartnerListUser.TBODY);
		// 4. Select a user from users table
		result = home.selectRowAt(PartnerListUser.TBODY, 0, PartnerUserMgmt.EDIT);
		Assert.assertEquals("Pass", result.getResult());
		// 5. Verify that 090P User Info page is displayed 
		result = home.checkPartnerUserInfo(users.get(0), PartnerUserMgmt.getElementsInfo());
		Assert.assertEquals("Pass", result.getResult());
		/*
		 The user's site privileges table is uneditable.
		 */
		Assert.assertFalse(home.canEdit(PartnerUserMgmt.SITE_PRIVILEGES));	
	}
	

	/*
	 * Verify that the notification table in 090D User Info page is read only.
	 */
	@Test
	public void TC090PUI_12(){
		result.addLog("ID : TC090PUI_12 : Verify that the notification table in 090D User Info page is read only");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Select a user from users table
			5. Verify that 090P User Info page is displayed
			6. Try to edit the user's site notification table
		*/
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Select a user from users table
		home.selectUserByEmail(partneruser);
		// 5. Verify that 090P User Info page is displayed 
		Assert.assertEquals(home.existsElement(PartnerUserMgmt.getElementsInfo()).getResult(), "Pass");
		/*
		 * The user's notification table is un-editable
		 */
		Assert.assertTrue(home.isAllNotificationCheckboxDisable(UserMgmt.SITE_PRIVILEGES_TABLE));
	}
	
}
