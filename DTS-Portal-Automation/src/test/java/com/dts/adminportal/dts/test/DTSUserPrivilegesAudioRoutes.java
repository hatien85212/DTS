package com.dts.adminportal.dts.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;

import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.AudioRoutes;
import dts.com.adminportal.model.AudioRoutesEdit;
import dts.com.adminportal.model.AudioRoutesInfo;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.Xpath;

public class DTSUserPrivilegesAudioRoutes extends CreatePage{
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
	 * Verify that the DTS user can view the audio route info even when the “Manage audio routes” privilege is disabled
	 */
	@Test
	public void TCPAR_01() {
		result.addLog("ID : TCPAR_01 : Verify that the DTS user can view the audio route info even when the “Manage audio routes” privilege is disabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the  “Manage audio routes” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Audio Routes” page is displayed
			9. Navigate to “Audio Routes” page
			VP: Verify that the audio list page is displayed
			10. Select an audio route from audio routes list
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the  “Manage audio routes” privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Manage_audio_routes);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// VP. Verify that the “Audio Routes” page is displayed
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_AUDIO_ROUTES));
		// 9. Navigate to “Audio Routes” page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// VP: Verify that the audio list page is displayed
		Assert.assertTrue(home.isElementPresent(AudioRoutes.HEADER));
		// 10. Select an audio route from audio routes list
		home.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		/*
		 * Verify that the audio route info page is displayed
		 */
		Assert.assertEquals(home.existsElement(AudioRoutesInfo.getElementsInfo()).getResult(), "Pass");
	}
	
	
	/*
	 * Verify that the DTS user can edit the audio route info when the “Manage audio routes” privilege is enabled
	 */
	@Test
	public void TCPAR_02() {
		result.addLog("ID : TCPAR_02 : Verify that the DTS user can edit the audio route info when the “Manage audio routes” privilege is enabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Manage audio routes” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Audio Routes” page is displayed
			9. Navigate to “Audio Routes” page
			VP: Verify that the audio list page is displayed
			10. Select an audio route from audio routes list
			VP: Verify that the “Edit Version” link is displayed in audio route info page
			11. Click “Edit” link
			12. Change some information of audio route
			13. Click “Save” link
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Manage audio routes” privileges
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Manage_audio_routes);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// VP. Verify that the “Audio Routes” page is displayed
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_AUDIO_ROUTES));
		// 9. Navigate to “Audio Routes” page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// VP: Verify that the audio list page is displayed
		Assert.assertTrue(home.existsElement(AudioRoutes.HEADER));
		// 10. Select an audio route from audio routes list
		home.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		// Verify that the audio route info page is displayed
		Assert.assertEquals(home.existsElement(AudioRoutesInfo.getElementsInfo()).getResult(), "Pass");
		// 11. Click “Edit” link
		home.click(AudioRoutesInfo.EDIT);
		// 12. Change some information of audio route
		String newSaleforceId = RandomStringUtils.randomNumeric(10);
		home.editData(AudioRoutesEdit.SALESFORCE_ID, newSaleforceId);
		// 13. Click “Save” link
		home.click(AudioRoutesEdit.SAVE);
		/*
		 * Verify that the audio route info page is displayed with new information correctly
		 */
		Assert.assertEquals(home.getTextByXpath(AudioRoutesInfo.SALESFORCE_ID), newSaleforceId);
	}
	
	/*
	 * Verify that the DTS user cannot edit the audio route info when the “Manage audio routes” privilege is disabled
	 */
	@Test
	public void TCPAR_05() {
		result.addLog("ID : TCPAR_05 : Verify that the DTS user cannot edit the audio route info when the “Manage audio routes” privilege is disabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the  “Manage audio routes” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Audio Routes” page is displayed
			9. Navigate to “Audio Routes” page
			VP: Verify that the audio list page is displayed
			10. Select an audio route from audio routes list
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the  “Manage audio routes” privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Manage_audio_routes);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// VP. Verify that the “Audio Routes” page is displayed
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_AUDIO_ROUTES));
		// 9. Navigate to “Audio Routes” page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// VP: Verify that the audio list page is displayed
		Assert.assertTrue(home.existsElement(AudioRoutes.HEADER));
		// 10. Select an audio route from audio routes list
		home.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		/*
		 * Verify that the audio route info page is displayed but the “Edit Version” link is not displayed
		 */
		Assert.assertFalse(home.isElementPresent(AudioRoutesInfo.EDIT));
	}
}
