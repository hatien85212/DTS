package com.dts.adminportal.partner.test;

import java.util.HashMap;
import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PartnerChangePassword;
import com.dts.adminportal.model.PartnerHomePage;
import com.dts.adminportal.model.PartnerListUser;
import com.dts.adminportal.model.PartnerUserEdit;
import com.dts.adminportal.model.PartnerUserInfo;
import com.dts.adminportal.model.PartnerUserMgmt;
import com.dts.adminportal.model.PartnerloginAccount;
import com.dts.adminportal.model.UserInfo;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.model.UsersList;
import com.dts.adminportal.util.TestData;

@Test
public class PartnerUser091PEdit_096ChangePassword extends BasePage{
	
	String email = "Email"+ RandomStringUtils.randomNumeric(10)+"@infonam.com";

	@Override
	protected void initData() {
	}
	

	/*Verify that the 091 Edit User page displays user's information correctly
	 */
	public void TC091PEU_01(){
		userControl.addLog("ID : TC091PEU_01 : Verify that the 091 Edit User page displays user's information correctly");
		
		/*
		Pre-condition: partner user has "Add and Manage Users" rights.

		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click "Users" tab
		4. Select a user from users table
		5. Click "Edit" link
		VP: The 091 Edit User page is displayed with correct given name, family name, title, email, country node, phone, site privileges, brand privileges, notification of the selected user from users table.
		VP: Verify that the 091P Edit Users is displayed and the "Actions" module contains "Save" and "Cancel" link
		6. Try to edit the user's site privileges and notification table
		VP: The user's site privileges and notification table is editable.
		 */
		
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Click "Users" tab
		userControl.click(PageHome.LINK_PARTNER_USER);
		// 4. Select a user from users table
		Assert.assertEquals(true, userControl.selectRowAt(PartnerListUser.TBODY, 0, PartnerUserMgmt.EDIT));
		// 5. Click "Edit" link
		userControl.click(UserInfo.EDIT);
		//VP: The 091 Edit User page is displayed with correct given name, family name, title, email, country node, phone, site privileges, brand privileges, notification of the selected user from users table.
		//VP: Verify that the 091P Edit Users is displayed and the "Actions" module contains "Save" and "Cancel" link
		Assert.assertEquals(true, userControl.existsElement(PartnerUserEdit.getHash()));	
		Assert.assertEquals(true, userControl.existsElement(PartnerUserEdit.getActions()));

		//6. Try to edit the user's site privileges and notification table
		//VP: The user's site privileges and notification table is editable.
		Assert.assertTrue(userControl.isAllPrivilegeEnable(AddUser.PRIVILEGES_TABLE));
		Assert.assertTrue(userControl.enableAllnotification());
	}
	
	/*
	 * Verify that new change is not effected when user cancels editing user's info
	 */
	public void TC091PEU_07(){
		userControl.addLog("ID : TC091PEU_07 : Verify that new change is not effected when user cancels editing user's info");
		/*
		  	Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Click �Add New User� link
			5. Fill valid value into all required fields
			6. Assign a specific brand for each user's privileges
			7. Click "Save" link
			VP: Verify that new user is created successfully
			8. Navigate to �Users� page again
			9. Select the new user from Users table
			10. Click �Edit� link
			VP: The assigned brands for each privilege of user are displayed correctly.
			11. Change some user's information
			12.Click "Cancel" link
			VP: The portal is redirected to 090P User info page with original user's information
		 */
		
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Click "Users" tab
		userControl.click(PageHome.LINK_PARTNER_USER);
		// 4. Click 'Add New User' link
		userControl.click(UsersList.ADD_USER);
		// 5. Fill valid value into all required fields
		// 6. Assign a specific brand for each user's privileges
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.partnerUser();
		userControl.addUser(AddUser.getPartnerUser(), data);
		/*
		 * VP: Verify that new user is created successfully
		 */
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.Success_Message[2]));
		// 8. Navigate to �Users� page again
		userControl.click(PageHome.LINK_PARTNER_USER);
		// 9. Select the new user from Users table
		userControl.selectUserInfoByEmail(data.get("email"));
		// 10. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		/*
		 * Verify that The assigned brands for each privilege of user are displayed correctly
		 */
		Assert.assertTrue(userControl.isBrandPrivilegeSelected(AddUser.BRAND_PRIVILEGES, data.get("brand")));
		
		//11. Change some user's information
		String title = RandomStringUtils.randomAlphabetic(10);
		userControl.type(PartnerUserEdit.TITLE, title );
		//12. Click "Cancel" link
		userControl.click(PartnerUserEdit.CANCEL);
		Assert.assertFalse( userControl.getTextByXpath(PartnerUserInfo.TITLE).contains(title));
	
		// Delete user
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*
	 * Verify that user is unable to log into DTS portal with old password
	 */
	@Test
	public void TC096PCP_05(){
		userControl.addLog("ID : TC096PCP_05 : Verify that user is unable to log into DTS portal with old password");
		userControl.addLog("In the failed case, neeed to change back password of pdtsauto@mailinator.com. New password maybe: dts9991");
//		String rq0= "Old password is required.";
//		String rq1= "Password is required.";
//		String rq2= "Re-type New Password does not match New Password.";
//		String old_pass = "dts999";
//		String new_pass = "dts111";
//		String retype_pass =" dts000";
//		String rq0= BasePage.CHANGING_PASSWORD_MESSAGE[0];
//		String rq1= BasePage.CHANGING_PASSWORD_MESSAGE[1];
//		String rq2= BasePage.CHANGING_PASSWORD_MESSAGE[2];
//		String old_pass = BasePage.CHANGING_PASSWORD_MESSAGE[3];
//		String new_pass = BasePage.CHANGING_PASSWORD_MESSAGE[4];
//		String retype_pass =BasePage.CHANGING_PASSWORD_MESSAGE[5];
		
		/*
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click user's email label on top right corner
		VP: Verify that the user account popup is displayed
		4. Click "User Account" item
		VP: verify that 095P User Info page is displayed
		5. Click "Change Password" link in "Actions" module
		VP: The 096P Change Password page is showed up with "Old Password", "New Password" and "Re-type New Password" fields.
		VP: The "Actions" module shows up "Save" and "Cancel" links.
		6. Click "Save" link.
		VP: An error message is showed inline for each "Old password", "New password" and "Re-type New Password" fields.
		7. Type invalid value into "Old Password" field
		8. Type same value into "New Password" and "Re-type New Password" fields
		9. Click "Save" link
		VP: An error message is displayed which mentioning to incorrect old password.
		10. Type valid value into "Old Password" and "New Password" fields
		11. Type valid value into "Re-type New Password" which is difference with value of "New Password"
		12. Click "Save" link
		VP: An error message is displayed and new password can not be saved due to the mis-matching value of "New Password" and "Re-tye New Password" fields. 
		13. Click "Cancel" link
		VP: The portal is redirected to 095P User Info page.
		14. Click "Change Password" link again
		15. Fill valid value into all fields
		16. Click "Save" link
		VP: The message" Success! Password is updated!" is displayed
		17. Log out DTS portal
		18. Log into DTS portal with old password
		VP: User is unable to log into DTS portal with old password
		19. Log into DTS portal with new password
		VP: User is unable to log into DTS portal with old password and unable to log into DTS portal with new password successfully
		 */
		
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 3.Click user's email label on top right corner
		//String error1=CHANGING_PASSWORD_MESSAGE[6];
		userControl.click(PageHome.lbLogout);
		/*
		 * VP: Verify that the user account popup is displayed
		 */
		HashMap<String, String> menu = userControl.getItemUserMenu(PartnerHomePage.DROP_DOWN_MENU);
		Assert.assertEquals(menu.get("option 1"), PartnerHomePage.GET_USER_MENU().get("option 1"));
		Assert.assertEquals(menu.get("option 2"), PartnerHomePage.GET_USER_MENU().get("option 2"));
		//4. Click "User Account" item
		userControl.click(PageHome.loginAccount);
		/*
		 * VP: verify that 095P User Info page is displayed
		 */
		Assert.assertEquals(true, userControl.existsElement(PartnerloginAccount.getHash()));
		//5. Click "Change Password" link in "Actions" module
		userControl.click(PartnerloginAccount.CHANGE_PASSWORD);
		/*
		 * VP: Verify that the 096P Change Password page is displayed
		 */
		Assert.assertEquals(true, userControl.existsElement(PartnerChangePassword.getHash()));
		
		//6. Click "Save" link.
		userControl.click(PartnerChangePassword.SAVE);
		/*
		 * An error message is showed inline for each "Old password", "New password" and "Re-type New Password" fields
		 */
		Assert.assertEquals( userControl.getTextByXpath(PartnerChangePassword.requires[0]), PartnerChangePassword.CHANGING_PASSWORD_MESSAGE[0]);
		Assert.assertEquals( userControl.getTextByXpath(PartnerChangePassword.requires[1]), PartnerChangePassword.CHANGING_PASSWORD_MESSAGE[1]);
		Assert.assertEquals( userControl.getTextByXpath(PartnerChangePassword.requires[2]), PartnerChangePassword.CHANGING_PASSWORD_MESSAGE[2]);		
				
		// 7. Type invalid value into "Old Password" field
		userControl.type(PartnerChangePassword.OLD_PASSWORD, RandomStringUtils.randomNumeric(5));
		// 9. Type same value into "New Password" and "Re-type New Password" fields
		userControl.type(PartnerChangePassword.NEW_PASSWORD, PARTNER_PASSWORD);
		userControl.type(PartnerChangePassword.RETYPE_PASSWORD, PARTNER_PASSWORD);
		// 9. Click "Save" link
		userControl.click(PartnerChangePassword.SAVE);
		/*
		 * Verify that An error message is displayed which mentioning to incorrect old password
		 */
		Assert.assertEquals( userControl.getTextByXpath(PartnerChangePassword.requires[0]), PartnerChangePassword.CHANGING_PASSWORD_MESSAGE[4]);
		
		//10. Type valid value into "Old Password" and "New Password" fields
		userControl.type(PartnerChangePassword.OLD_PASSWORD, PartnerChangePassword.INPUT_OLD_PASSWORD);
		userControl.type(PartnerChangePassword.NEW_PASSWORD, PartnerChangePassword.INPUT_NEW_PASSWORD);
		//11. Type valid value into "Re-type New Password" which is difference with value of "New Password"
		userControl.type(PartnerChangePassword.RETYPE_PASSWORD,PartnerChangePassword.INPUT_RETYPE_PASSWORD);
		//12. Click "Save" link
		userControl.click(PartnerChangePassword.SAVE);
		//VP: An error message is displayed and new password can not be saved due to the mis-matching value of "New Password" and "Re-tye New Password" fields.
		Assert.assertEquals( userControl.getTextByXpath(PartnerChangePassword.requires[2]), PartnerChangePassword.CHANGING_PASSWORD_MESSAGE[2]);
		//13. Click "Cancel" link
		userControl.click(PartnerChangePassword.CANCEL);
		//VP: The portal is redirected to 095P User Info page.
		Assert.assertEquals(true, userControl.existsElement(PartnerloginAccount.getHash()));
		//14. Click "Change Password" link again
		userControl.click(PartnerloginAccount.CHANGE_PASSWORD);
		//15. Fill valid value into all fields
		String newpass=PARTNER_PASSWORD+"1";
		userControl.type(PartnerChangePassword.OLD_PASSWORD, PARTNER_PASSWORD);
		userControl.type(PartnerChangePassword.NEW_PASSWORD, newpass);
		userControl.type(PartnerChangePassword.RETYPE_PASSWORD, newpass);
		//16. Click "Save" link
		userControl.click(PartnerChangePassword.SAVE);
		String success="Password is updated!";
		//VP: The message" Success! Password is updated!" is displayed
		
		String globalAlert = driver.findElement(By.xpath(PartnerChangePassword.GLOBAL_ALERT)).getAttribute("textContent");
		userControl.addLog("Global message: " + globalAlert);
		Assert.assertTrue(globalAlert.contains(success));
		//17. Log out DTS portal
		userControl.logout();
		//18. Log into DTS portal with old password
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		//VP: User is unable to log into DTS portal with old password
		Assert.assertFalse(userControl.isElementPresent(PageHome.LINK_PARTNER_HOME));
		//19. Log into DTS portal with new password
		loginControl.login(PARTNER_USER, newpass);
		//User is unable to log into DTS portal with old password and unable to log into DTS portal with new password successfully
		Assert.assertTrue(userControl.isElementPresent(PageHome.LINK_PARTNER_HOME));
		
		//Teardown:
		//Change useer's password back to normal
		userControl.click(PageHome.lbLogout);
		userControl.click(PageHome.loginAccount);
		userControl.click(PartnerloginAccount.CHANGE_PASSWORD);
		userControl.type(PartnerChangePassword.OLD_PASSWORD, newpass);
		userControl.type(PartnerChangePassword.NEW_PASSWORD, PARTNER_PASSWORD);
		userControl.type(PartnerChangePassword.RETYPE_PASSWORD, PARTNER_PASSWORD);
		userControl.click(PartnerChangePassword.SAVE);
		userControl.logout();
		
	}	
	
}
