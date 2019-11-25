package com.dts.adminportal.dts.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.AudioRoutes;
import com.dts.adminportal.model.AudioRoutesEdit;
import com.dts.adminportal.model.AudioRoutesInfo;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.UserMgmt;

public class DTSPrivilegesAudioRoutes extends BasePage{
	
	@Override
	protected void initData() {
	}	
	
	/*
	 * Verify that the DTS user can view the audio route info even when the �Manage audio routes� privilege is disabled
	 */
	@Test
	public void TCPAR_01() {
		audioControl.addLog("ID : TCPAR_01 : Verify that the DTS user can view the audio route info even when the �Manage audio routes� privilege is disabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the  �Manage audio routes� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Audio Routes� page is displayed
			9. Navigate to �Audio Routes� page
			VP: Verify that the audio list page is displayed
			10. Select an audio route from audio routes list
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		audioControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		audioControl.click(UserMgmt.EDIT);
		// 6. Disable the  �Manage audio routes� privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Manage_audio_routes);
		audioControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		audioControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// VP. Verify that the �Audio Routes� page is displayed
		Assert.assertTrue(audioControl.isElementPresent(PageHome.LINK_AUDIO_ROUTES));
		// 9. Navigate to �Audio Routes� page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// VP: Verify that the audio list page is displayed
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.HEADER));
		// 10. Select an audio route from audio routes list
		audioControl.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		/*
		 * Verify that the audio route info page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(AudioRoutesInfo.getElementsInfo()), true);
	}
	
	
	/*
	 * Verify that the DTS user can edit the audio route info when the �Manage audio routes� privilege is enabled
	 */
	@Test
	public void TCPAR_02() {
		audioControl.addLog("ID : TCPAR_02 : Verify that the DTS user can edit the audio route info when the �Manage audio routes� privilege is enabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Manage audio routes� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Audio Routes� page is displayed
			9. Navigate to �Audio Routes� page
			VP: Verify that the audio list page is displayed
			10. Select an audio route from audio routes list
			VP: Verify that the �Edit Version� link is displayed in audio route info page
			11. Click �Edit� link
			12. Change some information of audio route
			13. Click �Save� link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		audioControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		audioControl.click(UserMgmt.EDIT);
		// 6. Enable the �Manage audio routes� privileges
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Manage_audio_routes);
		audioControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		audioControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// VP. Verify that the �Audio Routes� page is displayed
		Assert.assertTrue(audioControl.isElementPresent(PageHome.LINK_AUDIO_ROUTES));
		// 9. Navigate to �Audio Routes� page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// VP: Verify that the audio list page is displayed
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.HEADER));
		// 10. Select an audio route from audio routes list
		audioControl.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		// Verify that the audio route info page is displayed
		Assert.assertEquals(audioControl.existsElement(AudioRoutesInfo.getElementsInfo()), true);
		// 11. Click �Edit� link
		audioControl.click(AudioRoutesInfo.EDIT);
		// 12. Change some information of audio route
		String newSaleforceId = RandomStringUtils.randomNumeric(10);
		audioControl.editData(AudioRoutesEdit.SALESFORCE_ID, newSaleforceId);
		// 13. Click �Save� link
		audioControl.click(AudioRoutesEdit.SAVE);
		/*
		 * Verify that the audio route info page is displayed with new information correctly
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.SALESFORCE_ID), newSaleforceId);
	}
	
	/*
	 * Verify that the DTS user cannot edit the audio route info when the �Manage audio routes� privilege is disabled
	 */
	@Test
	public void TCPAR_05() {
		audioControl.addLog("ID : TCPAR_05 : Verify that the DTS user cannot edit the audio route info when the �Manage audio routes� privilege is disabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the  �Manage audio routes� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Audio Routes� page is displayed
			9. Navigate to �Audio Routes� page
			VP: Verify that the audio list page is displayed
			10. Select an audio route from audio routes list
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		audioControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		audioControl.click(UserMgmt.EDIT);
		// 6. Disable the  �Manage audio routes� privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Manage_audio_routes);
		audioControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		audioControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// VP. Verify that the �Audio Routes� page is displayed
		Assert.assertTrue(audioControl.isElementPresent(PageHome.LINK_AUDIO_ROUTES));
		// 9. Navigate to �Audio Routes� page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// VP: Verify that the audio list page is displayed
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.HEADER));
		// 10. Select an audio route from audio routes list
		audioControl.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		/*
		 * Verify that the audio route info page is displayed but the �Edit Version� link is not displayed
		 */
		Assert.assertFalse(audioControl.isElementPresent(AudioRoutesInfo.EDIT));
	}
	
	/*
	 * Verify that the DTS user can view the Stereo Room Model info even when the �Manage Audio� privilege is disabled
	 */
	@Test
	public void TCPAR_06() {
		Reporter.log("ID : TCPAR_06 : Verify that the DTS user can view the Stereo Room Model info even when the �Manage Audio� privilege is disabled");
	
		/*
		1. Navigate to DTS portal
		2. Log into DTS portal as a super Admin successfully
		3. Navigate to "Users" page
		4. Select a DTS user from the Users table
		5. Click �Edit� link
		6. Disable the  �Manage Audio� privileges
		7. Log out DTS portal
		8. Log into DTS portal as above DTS user successfully
		VP. Verify that the �Audio� page is displayed
		9. Navigate to �Audio� page
		VP: Verify that the audio list page is displayed
		10. Select a Stereo Room Model item
		VP: Verify that the 616D Room Model Detail  page is displayed
		 */
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		audioControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click "Edit" link
		audioControl.click(UserMgmt.EDIT);
		// 6. Disable the  "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Manage_audio_routes);
		audioControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		audioControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// VP. Verify that the "Audio Routes" page is displayed
		Assert.assertTrue(audioControl.isElementPresent(PageHome.LINK_AUDIO_ROUTES));
		// 9. Navigate to "Audio Routes" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// VP: Verify that the audio list page is displayed
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.HEADER));
		// 10. Select a Stereo Room Model item
		audioControl.selectAnAudioRouteByName(AudioRoutes.STEREO_ROOM_MODELS[0]);
		/*
		 * Verify that the 616D Room Model Detail page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(AudioRoutesInfo.getElementsDetail()), true);
	}
	
	/*
	 * Verify that the DTS user can edit Stereo Room Model info when the �Manage Audio� privilege is enabled
	 */
	@Test
	public void TCPAR_07() {
		Reporter.log("ID : TCPAR_07 : Verify that the DTS user can edit Stereo Room Model info when the �Manage Audio� privilege is enabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click "Edit" link
			6. Enable the "Manage audio routes" privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the "Audio Routes" page is displayed
			9. Navigate to "Audio Routes" page
			VP: Verify that the audio list page is displayed
			10. Select a Stereo Room Model item
			VP: Verify that the "Edit Version" link is displayed in audio route info page
			11. Click "Edit" link
			12. Change some information of Stereo Room Model above
			13. Click "Save" link
			VP: Verify that the 616D Room Model Detail page is displayed with new information correctly
		 */
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		audioControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click Edit link
		audioControl.click(UserMgmt.EDIT);
		audioControl.waitForAjax();
		// 6. Enable the Manage audio routes privileges
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Manage_audio_routes);
		audioControl.waitForAjax();
		audioControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		audioControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// VP. Verify that the Audio Routes page is displayed
		Assert.assertTrue(audioControl.isElementPresent(PageHome.LINK_AUDIO_ROUTES));
		// 9. Navigate to Audio Routes page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// VP: Verify that the audio list page is displayed
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.HEADER));
		// 10. Select a Stereo Room Model item
		audioControl.selectAnAudioRouteByName(AudioRoutes.STEREO_ROOM_MODELS[0]);
		// Verify that the audio route info page is displayed
		Assert.assertEquals(audioControl.existsElement(AudioRoutesInfo.getElementsDetail()), true);
		// 11. Click Edit link
		String ispushlished = audioControl.getTextByXpath(AudioRoutes.AUDIO_DETAILS_EDIT_VERSION);
		if (ispushlished.contentEquals("Update Product Info"))
		{
		audioControl.click(AudioRoutesInfo.EDIT);
		audioControl.selectConfirmationDialogOption("OK");
		String newSaleforceId = RandomStringUtils.randomNumeric(10);
		audioControl.editData(AudioRoutesEdit.SALESFORCE_ID, newSaleforceId);
		// 13. Click Save link
		audioControl.click(AudioRoutesEdit.SAVE);
		/*
		 * Verify that the 616D Room Model Detail page is displayed with new information correctly
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.SALESFORCE_ID), newSaleforceId);
		}
		else
		{
		audioControl.click(AudioRoutesInfo.EDIT);
		// 12. Change some information of Stereo Room Model above
		String newSaleforceId = RandomStringUtils.randomNumeric(10);
		audioControl.editData(AudioRoutesEdit.SALESFORCE_ID, newSaleforceId);
		// 13. Click Save link
		audioControl.click(AudioRoutesEdit.SAVE);
		/*
		 * Verify that the 616D Room Model Detail page is displayed with new information correctly
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.SALESFORCE_ID), newSaleforceId);
		}
	}
	
	/*
	 * Verify that the DTS user cannot edit the Stereo Room Model info when the �Manage Audio� privilege is disabled
	 */
	@Test
	public void TCPAR_08() {
		Reporter.log("ID : TCPAR_08 : Verify that the DTS user cannot edit the Stereo Room Model info when the �Manage Audio� privilege is disabled");
	
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click Edit link
			6. Disable the  Manage audio routes privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the Audio Routes page is displayed
			9. Navigate to Audio Routes page
			VP: Verify that the audio list page is displayed
			10. Select a Stereo Room Model item
			VP: Verify that the 616D Room Model Detail page is displayed but the �Edit Version� link is not displayed.
		 */
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		audioControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click Edit link
		audioControl.click(UserMgmt.EDIT);
		// 6. Disable the  Manage audio routes privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Manage_audio_routes);
		audioControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		audioControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// VP. Verify that the Audio Routes page is displayed
		Assert.assertTrue(audioControl.isElementPresent(PageHome.LINK_AUDIO_ROUTES));
		// 9. Navigate to Audio Routes page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// VP: Verify that the audio list page is displayed
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.HEADER));
		// 10. Select a Stereo Room Model item
		audioControl.selectAnAudioRouteByName(AudioRoutes.STEREO_ROOM_MODELS[0]);
		/*
		 * Verify that the 616D Room Model Detail page is displayed but the �Edit Version� link is not displayed.
		 */
		Assert.assertFalse(audioControl.isElementPresent(AudioRoutesInfo.EDIT));
	}
	
	/*
	 * Verify that the DTS user can view the Multi-Channel Room Model info even when the �Manage Audio� privilege is disabled
	 */
	@Test
	public void TCPAR_09() {
		Reporter.log("ID : TCPAR_09 : Verify that the DTS user can view the Multi-Channel Room Model info even when the �Manage Audio� privilege is disabled");
	
		/*
		1. Navigate to DTS portal
		2. Log into DTS portal as a super Admin successfully
		3. Navigate to "Users" page
		4. Select a DTS user from the Users table
		5. Click �Edit� link
		6. Disable the  �Manage Audio� privileges
		7. Log out DTS portal
		8. Log into DTS portal as above DTS user successfully
		VP. Verify that the �Audio� page is displayed
		9. Navigate to �Audio� page
		VP: Verify that the audio list page is displayed
		10. Select a Multi-Channel Room Model item
		VP: Verify that the 616D Room Model Detail  page is displayed
		 */
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		audioControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click "Edit" link
		audioControl.click(UserMgmt.EDIT);
		// 6. Disable the  "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Manage_audio_routes);
		audioControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		audioControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// VP. Verify that the "Audio Routes" page is displayed
		Assert.assertTrue(audioControl.isElementPresent(PageHome.LINK_AUDIO_ROUTES));
		// 9. Navigate to "Audio Routes" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// VP: Verify that the audio list page is displayed
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.HEADER));
		// 10. Select a Multi-Channel Room Model item
		audioControl.click(AudioRoutes.MCROOM0);
		/*
		 * Verify that the 616D Room Model Detail page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(AudioRoutesInfo.getElementsDetail()), true);
	}
	
	/*
	 * Verify that the DTS user can edit Multi-Channel Room Model info when the �Manage Audio� privilege is enabled
	 */
	@Test
	public void TCPAR_10() {
		Reporter.log("ID : TCPAR_10 : Verify that the DTS user can edit Multi-Channel Room Model info when the �Manage Audio� privilege is enabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click "Edit" link
			6. Enable the "Manage audio routes" privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the "Audio Routes" page is displayed
			9. Navigate to "Audio Routes" page
			VP: Verify that the audio list page is displayed
			10. Select a Multi-Channel Room Model item
			VP: Verify that the "Edit Version" link is displayed in audio route info page
			11. Click "Edit" link
			12. Change some information of Multi-Channel Room Model above
			13. Click "Save" link
			VP: Verify that the 616D Room Model Detail page is displayed with new information correctly
		 */
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		audioControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click Edit link
		audioControl.click(UserMgmt.EDIT);
		audioControl.waitForAjax();
		// 6. Enable the Manage audio routes privileges
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Manage_audio_routes);
		audioControl.waitForAjax();
		audioControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		audioControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// VP. Verify that the Audio Routes page is displayed
		Assert.assertTrue(audioControl.isElementPresent(PageHome.LINK_AUDIO_ROUTES));
		// 9. Navigate to Audio Routes page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// VP: Verify that the audio list page is displayed
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.HEADER));
		// 10. Select a Stereo Room Model item
		audioControl.click(AudioRoutes.MCROOM0);
		// Verify that the audio route info page is displayed
		Assert.assertEquals(audioControl.existsElement(AudioRoutesInfo.getElementsDetail()), true);
		// 11. Click Edit link
		String ispushlished = audioControl.getTextByXpath(AudioRoutes.AUDIO_DETAILS_EDIT_VERSION);
		if (ispushlished.contentEquals("Update Product Info"))
		{
		audioControl.click(AudioRoutesInfo.EDIT);
		audioControl.selectConfirmationDialogOption("OK");
		String newSaleforceId = RandomStringUtils.randomNumeric(10);
		audioControl.editData(AudioRoutesEdit.SALESFORCE_ID, newSaleforceId);
		// 13. Click Save link
		audioControl.click(AudioRoutesEdit.SAVE);
		/*
		 * Verify that the 616D Room Model Detail page is displayed with new information correctly
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.SALESFORCE_ID), newSaleforceId);
		}
		else
		{
		audioControl.click(AudioRoutesInfo.EDIT);
		// 12. Change some information of Stereo Room Model above
		String newSaleforceId = RandomStringUtils.randomNumeric(10);
		audioControl.editData(AudioRoutesEdit.SALESFORCE_ID, newSaleforceId);
		// 13. Click Save link
		audioControl.click(AudioRoutesEdit.SAVE);
		/*
		 * Verify that the 616D Room Model Detail page is displayed with new information correctly
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.SALESFORCE_ID), newSaleforceId);
		}
	}
	
	/*
	 * Verify that the DTS user cannot edit the Multi-Channel Room Model info when the �Manage Audio� privilege is disabled
	 */
	@Test
	public void TCPAR_11() {
		Reporter.log("ID : TCPAR_11 : Verify that the DTS user cannot edit the Multi-Channel Room Model info when the �Manage Audio� privilege is disabled");
	
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click Edit link
			6. Disable the  Manage audio routes privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the Audio Routes page is displayed
			9. Navigate to Audio Routes page
			VP: Verify that the audio list page is displayed
			10. Select a Multi-Channel Room Model item
			VP: Verify that the 616D Room Model Detail page is displayed but the �Edit Version� link is not displayed.
		 */
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		audioControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click Edit link
		audioControl.click(UserMgmt.EDIT);
		// 6. Disable the  Manage audio routes privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Manage_audio_routes);
		audioControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		audioControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// VP. Verify that the Audio Routes page is displayed
		Assert.assertTrue(audioControl.isElementPresent(PageHome.LINK_AUDIO_ROUTES));
		// 9. Navigate to Audio Routes page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// VP: Verify that the audio list page is displayed
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.HEADER));
		// 10. Select a Multi-Channel Room Model item
		audioControl.click(AudioRoutes.MCROOM0);
		/*
		 * Verify that the 616D Room Model Detail page is displayed but the �Edit Version� link is not displayed.
		 */
		Assert.assertFalse(audioControl.isElementPresent(AudioRoutesInfo.EDIT));
	}
}
