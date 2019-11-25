package com.dts.adminportal.partner.test;

import java.util.HashMap;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;




import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.LoginPage;
import dts.com.adminportal.model.PartnerHomePage;
import dts.com.adminportal.model.PartnerUserMgmt;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UserEdit;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.Xpath;
import dts.com.adminportal.model.UserInfo;

@Test
public class PartnerUser095PUserInfo extends CreatePage{
	private HomeController home;
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}
	
	
	/*
	 * Verify that the 095P User Info page displays user's own information properly
	 */
	public void TC095PUI_01(){
		result.addLog("ID : TC095PUI_01 : Verify that the 095P User Info page displays user's own information properly");
		
		/*
		Pre-condition: partner user has "Add and Manage Users" rights.
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click user's email label on top right corner
		VP: Verify that the user account popup is displayed
		4. Click "User Account" item
		 */
		// 2. Log into DTS portal as a partner user successfully
		home.login(partneruser, password);
		// 3.Click user's email label on top right corner
		home.click(Xpath.lbLogout);
		/*
		 * VP: Verify that the user account popup is displayed
		 */
		HashMap<String, String> menu = home
				.getItemUserMenu(PartnerHomePage.DROP_DOWN_MENU);
		Assert.assertEquals(menu.get("option 1"), PartnerHomePage
				.GET_USER_MENU().get("option 1"));
		Assert.assertEquals(menu.get("option 2"), PartnerHomePage
				.GET_USER_MENU().get("option 2"));
		// 4. Click "User Account" item
		home.click(Xpath.loginAccount);
		/*
		 * The 095 User Info page displays: User's first name, last name, title, email, phone, status, site privileges,brands, notification and action module
		 */
		Assert.assertEquals("Pass", home.existsElement(PartnerUserMgmt.getElementsInfo()).getResult());
		Assert.assertEquals("Pass", home.existsElement(PartnerUserMgmt.getUserOwnActions()).getResult());
	}
	
	/*
	 * Verify that the site, brand privileges and notification info displayed correctly according to user's assigned site privileges
	 */
	public void TC095PUI_02(){
		result.addLog("ID : TC095PUI_02 : Verify that the site, brand privileges and notification info displayed correctly according to user's assigned site privileges");
		
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
		 */
		// 2. Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table
		home.dtsSelectUserByEmail(partneruser);
		/*
		 * VP: Verify that the 090D User Info page is displayed
		 */
		Assert.assertEquals(home.existsElement(PartnerUserMgmt.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Set site, brand privilege and notification for user
		home.disableAllPrivilege(AddUser.PRIVILEGES_TABLE);
		home.enablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as a partner admin who have the same company with above user
		home.login(partneruser, password);
		// 9. Click user's email label on top right corner
		home.click(Xpath.lbLogout);
		// 10. Click "User Account" item
		home.click(Xpath.loginAccount);
		/*
		 * The user's site, brand privilege and notification display correctly
		 */
		String PrivilegeTable = home.getTextByXpath(PartnerUserMgmt.SITE_PRIVILEGES_TABLE);
		Assert.assertTrue(PrivilegeTable.contains(Privileges.privileges[0]) && PrivilegeTable.contains("All brands"));
		Assert.assertTrue(home.isNotificationCheckboxSelected(PartnerUserMgmt.SITE_PRIVILEGES_TABLE, Privileges.privileges[0]));
	}
	
	/*
	 * Verify that the site privileges table in 095P User Info page is read only
	 */
	public void TC095PUI_03(){
		result.addLog("ID : TC095PUI_03 : Verify that the site privileges table in 095P User Info page is read only");
		
		/*
		Pre-condition: partner user has "Add and Manage Users" rights.
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click user's email label on top right corner
		VP: Verify that the user account popup is displayed
		4. Click "User Account" item
		 */
		// 2. Log into DTS portal as a partner user successfully
		home.login(partneruser, password);
		// 3. Click user's email label on top right corner
		home.click(Xpath.lbLogout);
		/*
		 * VP: Verify that the user account popup is displayed
		 */
		HashMap<String, String> menu = home
				.getItemUserMenu(PartnerHomePage.DROP_DOWN_MENU);
		Assert.assertEquals(menu.get("option 1"), PartnerHomePage
				.GET_USER_MENU().get("option 1"));
		Assert.assertEquals(menu.get("option 2"), PartnerHomePage
				.GET_USER_MENU().get("option 2"));
		// 4. Click "User Account" item
		home.click(Xpath.loginAccount);
		/*
		 * The user's site privileges table is uneditable
		 */
		Assert.assertTrue(home.isAllNotificationCheckboxDisable(PartnerUserMgmt.SITE_PRIVILEGES_TABLE));
	}
	
	/*
	 * Verify that the "Actions" module only displays "Edit" and "Change Password" link
	 */
	public void TC095PUI_04(){
		result.addLog("ID : TC095PUI_04 : Verify that the 'Actions' module only displays 'Edit' and 'Change Password' link");
		
		/*
		Pre-condition: partner user has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click user's email label on top right corner
			VP: Verify that the user account popup is displayed
			4. Click "User Account" item
		 */
		// 2. Log into DTS portal as a partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 3.Click user's email label on top right corner
		home.click(Xpath.lbLogout);
		/*
		 * VP: Verify that the user account popup is displayed
		 */
		HashMap<String, String> menu = home.getItemUserMenu(PartnerHomePage.DROP_DOWN_MENU);
		Assert.assertEquals(menu.get("option 1"), PartnerHomePage.GET_USER_MENU().get("option 1"));
		Assert.assertEquals(menu.get("option 2"), PartnerHomePage.GET_USER_MENU().get("option 2"));
		//4. Click "User Account" item
		home.click(Xpath.loginAccount);
		/*
		 * The "Actions" module shows up "Change Password" link
		 */
		Assert.assertEquals("Pass", home.existsElement(PartnerUserMgmt.getUserOwnActions()).getResult());
	}
	
	
	/*
	 * Verify that user is unable to change his own email address when the 'Add and manage user' privilege is disabled
	 */
	public void TC095PUI_05(){
		result.addLog("ID : TC095PUI_05 : Verify that user is unable to change his own email address when the 'Add and manage user' privilege is disabled.");
		/*
			Pre-condition: The "Add and Manage Users" privilege is disabled.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			VP: Verify that the User page is not displayed
			3. Click user's email label on top right corner
			VP: Verify that the user account popup is displayed
			4. Click "User Account" item
			5. Click "Edit" link
		 */
		//Precondition: change user privilege
		home.login(superpartneruser, superpartnerpassword);
		home.click(Xpath.LINK_USERS);
		home.selectUserByEmail(partneruser);
		home.click(UserInfo.EDIT);
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		home.logout();
		// *********************************************************
		// 2. Log into DTS portal as a partner user successfully
		home.login(partneruser, password);
		// 3.Click user's email label on top right corner
		home.click(Xpath.lbLogout);
		//4. Click "User Account" item
		home.click(Xpath.loginAccount);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		/*
		 * Verify that user is unable to change his own email address when the "Add and manage user" privilege is disabled.
		 */
		Assert.assertFalse(home.isElementEditable(AddUser.EMAIL));
		
		//Teardown: Enable all privielges
		home.logout();
		home.login(superpartneruser, superpartnerpassword);
		home.click(Xpath.LINK_USERS);
		home.selectUserByEmail(partneruser);
		home.click(UserInfo.EDIT);
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
	}
	
	/*
	 * Verify that user is unable to log into DTS portal with old email address
	 */
	public void TC095PUI_06(){
		result.addLog("ID : TC095PUI_06 : Verify that user is unable to log into DTS portal with old email address.");
		
		/*
			Pre-condition: The "Add and Manage Users" privilege is enabled.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			VP: Verify that the User page is displayed
			3. Click user's email label on top right corner
			4. Click "User Account" item
			5. Click "Edit" link
			VP: Verify that the email address field is enabled
			6. Change user own email address to another
			7. Click "Save" link
			VP: Verify that user is kicked out the portal after the change is saved
			8. Log into DTS portal with old email address
		 */
		// 2. Log into DTS portal as a partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 3.Click user's email label on top right corner
		home.click(Xpath.lbLogout);
		// 4. Click "User Account" item
		home.click(Xpath.loginAccount);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		/*
		 * VP: Verify that the email address field is enabled
		 */
		Assert.assertTrue(home.isElementEditable(AddUser.EMAIL));
		// 6. Change user own email address to another
		String newEmail = "NewEmail" + RandomStringUtils.randomNumeric(5) + "@mailinator.com";
		home.editData(AddUser.EMAIL, newEmail);
		// 7. Click "Save" link
		home.click(AddUser.SAVE);
		// VP: Verify that user is kicked out the portal after the change is saved
		Assert.assertTrue(home.isElementPresent(LoginPage.USERNAME));
		Assert.assertFalse(home.isElementPresent(AddUser.EMAIL));
		// 8. Log into DTS portal with old email address
		home.login(superpartneruser, superpartnerpassword);
		// User is unable to log into DTS portal with old email address
		Assert.assertEquals(home.getTextByXpath(LoginPage.ERROR_MESSAGE), LoginPage.errMessage[3]);
		Assert.assertFalse(home.isElementPresent(Xpath.LINK_PARTNER_HOME));
		
		//Teardown: Change user account email address back to previous;
		home.login(newEmail, superpartnerpassword);
		home.click(Xpath.lbLogout);
		home.click(Xpath.loginAccount);
		home.click(UserInfo.EDIT);
		home.editData(AddUser.EMAIL, superpartneruser);
		home.click(AddUser.SAVE);
	}
		
	/*
	 * Verify that user is able to log into DTS portal with new email address
	 */
	public void TC095PUI_07(){
		result.addLog("ID : TC095PUI_07 : Verify that user is able to log into DTS portal with new email address.");
		/*
			Pre-condition: The "Add and Manage Users" privilege is enabled.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			VP: Verify that the User page is displayed
			3. Click user's email label on top right corner
			4. Click "User Account" item
			5. Click "Edit" link
			VP: Verify that the email address field is enabled
			6. Change user own email address to another
			7. Click "Save" link
			VP: Verify that user is kicked out the portal after the change is saved
			8. Log into DTS portal with new email address
		 */
		// 2. Log into DTS portal as a partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 3.Click user's email label on top right corner
		home.click(Xpath.lbLogout);
		// 4. Click "User Account" item
		home.click(Xpath.loginAccount);
		// 5. Click "Edit" link
		home.click(UserInfo.EDIT);
		/*
		 * VP: Verify that the email address field is enabled
		 */
		Assert.assertTrue(home.isElementEditable(AddUser.EMAIL));
		// 6. Change user own email address to another
		String newEmail = "NewEmail" + RandomStringUtils.randomNumeric(5) + "@mailinator.com";
		home.editData(AddUser.EMAIL, newEmail);
		// 7. Click "Save" link
		home.click(AddUser.SAVE);
		// VP: Verify that user is kicked out the portal after the change is saved
		Assert.assertTrue(home.isElementPresent(LoginPage.USERNAME));
		Assert.assertFalse(home.isElementPresent(AddUser.EMAIL));
		// 8. Log into DTS portal with new email address
		home.login(newEmail, superpartnerpassword);
		//Verify that user is able to log into DTS portal with new email address
		Assert.assertFalse(home.isElementPresent(LoginPage.ERROR_MESSAGE));
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_PARTNER_HOME));
		
		//Teardown: Change user account email address back to previous;
		home.click(Xpath.lbLogout);
		home.click(Xpath.loginAccount);
		home.click(UserInfo.EDIT);
		home.editData(AddUser.EMAIL, superpartneruser);
		home.click(AddUser.SAVE);
	}
}