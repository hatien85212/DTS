package com.dts.adminportal.dts.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.AudioRoutes;
import com.dts.adminportal.model.AudioRoutesEdit;
import com.dts.adminportal.model.AudioRoutesInfo;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.RoomModelEdit;
import com.dts.adminportal.model.RoomModelInfo;
import com.dts.adminportal.model.UserMgmt;

public class DTSPrivilegesAudioRoutes extends BasePage{
	
	@Override
	protected void initData() {
	}	
	
	/*
	 * Verify that the DTS user can view the audio route info even when the ï¿½Manage audio routesï¿½ privilege is disabled
	 */
	@Test
	public void TCPAR_01() {
		audioControl.addLog("ID : TCPAR_01 : Verify that the DTS user can view the audio route info even when the ï¿½Manage audio routesï¿½ privilege is disabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click ï¿½Editï¿½ link
			6. Disable the  ï¿½Manage audio routesï¿½ privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the ï¿½Audio Routesï¿½ page is displayed
			9. Navigate to ï¿½Audio Routesï¿½ page
			VP: Verify that the audio list page is displayed
			10. Select  a room model from "Multi-Channel Room Models"	
			Verify that the room model info page is displayed
			11. Select an audio route from "Standard Accessories: Line-out0 and Bluetooth0 "	
			Verify that the audio route info page is displayed


		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		audioControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click ï¿½Editï¿½ link
		audioControl.click(UserMgmt.EDIT);
		// 6. Disable the  ï¿½Manage audio routesï¿½ privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Manage_audio_routes.getName());
		audioControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		audioControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// VP. Verify that the ï¿½Audio Routesï¿½ page is displayed
		Assert.assertTrue(audioControl.isElementPresent(PageHome.linkAudioroutes));
		// 9. Navigate to ï¿½Audio Routesï¿½ page
		audioControl.click(PageHome.linkAudioroutes);
		// VP: Verify that the audio list page is displayed
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.HEADER));
		//10. Select  a room model from "Multi-Channel Room Models"	
		appDeviceControl.click(AudioRoutes.MCROOM0);
		/*
		 * Verify that the room model info page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(RoomModelInfo.getElementsInfo()),true);
		appDeviceControl.click(PageHome.linkAudioroutes);
		//11. Select an audio route from "Standard Accessories: Line-out0 and Bluetooth0 "	
		/*
		 * Verify that the audio route info page is displayed
		 */
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		/*
		 * Verify that the audio route info page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(AudioRoutesInfo.getElementsInfo()), true);
	}
	
	
	/*
	 * Verify that the DTS user can edit the audio route info when the ï¿½Manage audio routesï¿½ privilege is enabled
	 */
	@Test
	public void TCPAR_02() {
		audioControl.addLog("ID : TCPAR_02 : Verify that the DTS user can edit the audio route info when the ï¿½Manage audio routesï¿½ privilege is enabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click ï¿½Editï¿½ link
			6. Enable the  ï¿½Manage audio routesï¿½ privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the ï¿½Audio Routesï¿½ page is displayed
			9. Navigate to ï¿½Audio Routesï¿½ page
			VP: Verify that the audio list page is displayed
			10. Select an audio route from audio routes list
			VP: Verify that the ï¿½Edit Versionï¿½ link is displayed in audio route info page
			11. Click ï¿½Editï¿½ link
			12. Change some information of audio route
			13. Click ï¿½Saveï¿½ link
			Verify that the room model info page is displayed with new information correctly
			14. Repeat from step 10 to step 13 with room model from room model list
			Verify that the room model info page is displayed with new information correctly

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		audioControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click ï¿½Editï¿½ link
		audioControl.click(UserMgmt.EDIT);
		// 6. Enable the ï¿½Manage audio routesï¿½ privileges
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Manage_audio_routes.getName());
		audioControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		audioControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// VP. Verify that the ï¿½Audio Routesï¿½ page is displayed
		Assert.assertTrue(audioControl.isElementPresent(PageHome.linkAudioroutes));
		// 9. Navigate to ï¿½Audio Routesï¿½ page
		audioControl.click(PageHome.linkAudioroutes);
		// VP: Verify that the audio list page is displayed
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.HEADER));
		// 10. Select an audio route from audio routes list
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		// Verify that the audio route info page is displayed
		Assert.assertEquals(audioControl.existsElement(AudioRoutesInfo.getElementsInfo()), true);
		// 11. Click ï¿½Editï¿½ link
		audioControl.click(AudioRoutesInfo.EDIT);
		// 12. Change some information of audio route
		String newSaleforceId = RandomStringUtils.randomNumeric(10);
		audioControl.editData(AudioRoutesEdit.SALESFORCE_ID, newSaleforceId);
		// 13. Click ï¿½Saveï¿½ link
		audioControl.click(AudioRoutesEdit.SAVE);
		/*
		 * Verify that the audio route info page is displayed with new information correctly
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.SALESFORCE_ID), newSaleforceId);
		//14. Repeat from step 10 to step 13 with room model from room model list
		appDeviceControl.click(PageHome.linkAudioroutes);
		audioControl.click(AudioRoutes.MCROOM0);
		// Verify that the room model info page is displayed
		Assert.assertEquals(audioControl.existsElement(RoomModelInfo.getElementsInfo()), true);
		//Click "Edit" link
		audioControl.editVersion();
		//Change some information of audio route
		String newSaleforceId2 = RandomStringUtils.randomNumeric(10);
		audioControl.editData(AudioRoutesEdit.SALESFORCE_ID, newSaleforceId2);
		//Click "Save"link
		audioControl.click(RoomModelEdit.SAVE);
		/*
		 * Verify that the room model info page is displayed with new information correctly
		 */
		Assert.assertEquals(audioControl.getTextByXpath(RoomModelInfo.SALESFORCE_ID), newSaleforceId2);
	}
	
	/*
	 * Verify that the DTS user cannot edit the audio route info when the ï¿½Manage audio routesï¿½ privilege is disabled
	 */
	@Test
	public void TCPAR_05() {
		audioControl.addLog("ID : TCPAR_05 : Verify that the DTS user cannot edit the audio route info when the ï¿½Manage audio routesï¿½ privilege is disabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click ï¿½Editï¿½ link
			6. Disable the  ï¿½Manage audio routesï¿½ privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the ï¿½Audio Routesï¿½ page is displayed
			9. Navigate to ï¿½Audio Routesï¿½ page
			VP: Verify that the audio list page is displayed
			10. Select an audio route from audio routes list
			VP: Verify that the audio route info page is displayed but the “Edit Version” link is not displayed.
			11. Select a room model from room model list	
			VP: Verify that the room model info page is displayed but the “Edit Version” link is not displayed.

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		audioControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click ï¿½Editï¿½ link
		audioControl.click(UserMgmt.EDIT);
		// 6. Disable the  ï¿½Manage audio routesï¿½ privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Manage_audio_routes.getName());
		audioControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		audioControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// VP. Verify that the ï¿½Audio Routesï¿½ page is displayed
		Assert.assertTrue(audioControl.isElementPresent(PageHome.linkAudioroutes));
		// 9. Navigate to ï¿½Audio Routesï¿½ page
		audioControl.click(PageHome.linkAudioroutes);
		// VP: Verify that the audio list page is displayed
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.HEADER));
		// 10. Select an audio route from audio routes list
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		/*
		* Verify that the audio route info page is displayed but the ï¿½Edit Versionï¿½ link is not displayed
		*/
		Assert.assertFalse(audioControl.isElementPresent(AudioRoutesInfo.EDIT));
		//11. Select a room model from room model list	
		appDeviceControl.click(PageHome.linkAudioroutes);
		appDeviceControl.click(AudioRoutes.MCROOM0);
		/*
		 * VP: Verify that the room model info page is displayed but the “Edit Version” link is not displayed.
		 */
		Assert.assertFalse(audioControl.isElementPresent(RoomModelInfo.EDIT));
		
	}
	
	/*
	 * Verify that the DTS user can view the Room Model info even when the “Manage Audio” privilege is disabled
	 */
	@Test
	public void TCPAR_06() {
		Reporter.log("ID : TCPAR_06 : Verify that the DTS user can view the Room Model info even when the “Manage Audio” privilege is disabled");
	
		/*
		1. Navigate to DTS portal
		2. Log into DTS portal as a super Admin successfully
		3. Navigate to "Users" page
		4. Select a DTS user from the Users table
		5. Click “Edit” link
		6. Disable the  “Manage Audio” privileges
		7. Log out DTS portal
		8. Log into DTS portal as above DTS user successfully
		VP. Verify that the “Audio” page is displayed
		9. Navigate to “Audio” page
		VP: Verify that the audio list page is displayed
		10. Select a Stereo Room Model item
		VP: Verify that the 616D Room Model Detail  page is displayed
		11. Select a Multi-Channel Room Model item	
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
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Manage_audio_routes.getName());
		audioControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		audioControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// VP. Verify that the "Audio Routes" page is displayed
		Assert.assertTrue(audioControl.isElementPresent(PageHome.linkAudioroutes));
		// 9. Navigate to "Audio Routes" page
		audioControl.click(PageHome.linkAudioroutes);
		// VP: Verify that the audio list page is displayed
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.HEADER));
		// 10. Select a Stereo Room Model item
		audioControl.selectAnAudioRouteByName(AudioRoutes.Stereo_Room_Models.Wide.getName());
		/*
		 * Verify that the 616D Room Model Detail page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(RoomModelInfo.getElementsInfo()), true);
		//11. Select a Multi-Channel Room Model item	
		appDeviceControl.click(PageHome.linkAudioroutes);
		appDeviceControl.click(AudioRoutes.MCROOM0);
		/*
		 * VP: Verify that the 616D Room Model Detail  page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(RoomModelInfo.getElementsInfo()), true);
	}
	
	/*
	 * Verify that the DTS user can edit Room Model info when the “Manage Audio” privilege is enabled
	 */
	@Test
	public void TCPAR_07() {
		Reporter.log("ID : TCPAR_07 : Verify that the DTS user can edit Room Model info when the “Manage Audio” privilege is enabled");
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
			14. Repeat from step 10 to step 13 with  Multi-Channel Room Model item
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
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Manage_audio_routes.getName());
		audioControl.waitForAjax();
		audioControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		audioControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// VP. Verify that the Audio Routes page is displayed
		Assert.assertTrue(audioControl.isElementPresent(PageHome.linkAudioroutes));
		// 9. Navigate to Audio Routes page
		audioControl.click(PageHome.linkAudioroutes);
		// VP: Verify that the audio list page is displayed
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.HEADER));
		// 10. Select a Stereo Room Model item
		audioControl.selectAnAudioRouteByName(AudioRoutes.Stereo_Room_Models.Wide.getName());
		// Verify that the audio route info page is displayed
		Assert.assertEquals(audioControl.existsElement(AudioRoutesInfo.getElementsDetail()), true);
		// 11. Click Edit link
		audioControl.editVersion();
		//12. Change some information of Stereo Room Model above
		String newSaleforceId = RandomStringUtils.randomNumeric(10);
		audioControl.editData(AudioRoutesEdit.SALESFORCE_ID, newSaleforceId);
		// 13. Click Save link
		audioControl.click(AudioRoutesEdit.SAVE);
		/*
		 * Verify that the 616D Room Model Detail page is displayed with new information correctly
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.SALESFORCE_ID), newSaleforceId);
		//14. Repeat from step 10 to step 13 with  Multi-Channel Room Model item
		appDeviceControl.click(PageHome.linkAudioroutes);
		audioControl.click(AudioRoutes.MCROOM0);
		// Verify that the audio route info page is displayed
		Assert.assertEquals(audioControl.existsElement(AudioRoutesInfo.getElementsDetail()), true);
		// Click Edit link
		audioControl.editVersion();
		//Change some information of Stereo Room Model above
		String newSaleforceId2 = RandomStringUtils.randomNumeric(10);
		audioControl.editData(AudioRoutesEdit.SALESFORCE_ID, newSaleforceId2);
		// 13. Click Save link
		audioControl.click(AudioRoutesEdit.SAVE);
		/*
		 * Verify that the 616D Room Model Detail page is displayed with new information correctly
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.SALESFORCE_ID), newSaleforceId2);
	}
	
	/*
	 * Verify that the DTS user cannot edit the Room Model info when the “Manage Audio” privilege is disabled
	 */
	@Test
	public void TCPAR_08() {
		Reporter.log("ID : TCPAR_08 : Verify that the DTS user cannot edit the Room Model info when the “Manage Audio” privilege is disabled");
	
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
			VP: Verify that the 616D Room Model Detail page is displayed but the “Edit Version” link is not displayed.
			11. Select a Multi-Channel Room Model item
			VP: Verify that the 616D Room Model Detail page is displayed but the “Edit Version” link is not displayed.

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
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Manage_audio_routes.getName());
		audioControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		audioControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// VP. Verify that the Audio Routes page is displayed
		Assert.assertTrue(audioControl.isElementPresent(PageHome.linkAudioroutes));
		// 9. Navigate to Audio Routes page
		audioControl.click(PageHome.linkAudioroutes);
		// VP: Verify that the audio list page is displayed
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.HEADER));
		// 10. Select a Stereo Room Model item
		audioControl.selectAnAudioRouteByName(AudioRoutes.Stereo_Room_Models.Wide.getName());
		/*
		 * Verify that the 616D Room Model Detail page is displayed but the “Edit Version” link is not displayed.
		 */
		Assert.assertFalse(audioControl.isElementPresent(AudioRoutesInfo.EDIT));
		//11. Select a Multi-Channel Room Model item
		audioControl.click(PageHome.linkAudioroutes);
		audioControl.click(AudioRoutes.MCROOM0);
		/*
		 * VP: Verify that the 616D Room Model Detail page is displayed but the “Edit Version” link is not displayed.
		 */
		Assert.assertFalse(audioControl.isElementPresent(RoomModelInfo.EDIT));
	}
	
	/*
	 * Verify that the DTS user can view the Multi-Channel Room Model info even when the “Manage Audio” privilege is disabled
	 */
	@Test
	public void TCPAR_09() {
		Reporter.log("ID : TCPAR_09 : Verify that the DTS user can view the Multi-Channel Room Model info even when the “Manage Audio” privilege is disabled");
	
		/*
		1. Navigate to DTS portal
		2. Log into DTS portal as a super Admin successfully
		3. Navigate to "Users" page
		4. Select a DTS user from the Users table
		5. Click “Edit” link
		6. Disable the  “Manage Audio” privileges
		7. Log out DTS portal
		8. Log into DTS portal as above DTS user successfully
		VP. Verify that the “Audio” page is displayed
		9. Navigate to “Audio” page
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
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Manage_audio_routes.getName());
		audioControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		audioControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// VP. Verify that the "Audio Routes" page is displayed
		Assert.assertTrue(audioControl.isElementPresent(PageHome.linkAudioroutes));
		// 9. Navigate to "Audio Routes" page
		audioControl.click(PageHome.linkAudioroutes);
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
	 * Verify that the DTS user can edit Multi-Channel Room Model info when the “Manage Audio” privilege is enabled
	 */
	@Test
	public void TCPAR_10() {
		Reporter.log("ID : TCPAR_10 : Verify that the DTS user can edit Multi-Channel Room Model info when the “Manage Audio” privilege is enabled");
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
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Manage_audio_routes.getName());
		audioControl.waitForAjax();
		audioControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		audioControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// VP. Verify that the Audio Routes page is displayed
		Assert.assertTrue(audioControl.isElementPresent(PageHome.linkAudioroutes));
		// 9. Navigate to Audio Routes page
		audioControl.click(PageHome.linkAudioroutes);
		// VP: Verify that the audio list page is displayed
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.HEADER));
		// 10. Select a Stereo Room Model item
		audioControl.click(AudioRoutes.MCROOM0);
		// Verify that the audio route info page is displayed
		Assert.assertEquals(audioControl.existsElement(AudioRoutesInfo.getElementsDetail()), true);
		// 11. Click Edit link
		audioControl.editVersion();
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
	
	/*
	 * Verify that the DTS user cannot edit the Multi-Channel Room Model info when the “Manage Audio” privilege is disabled
	 */
	@Test
	public void TCPAR_11() {
		Reporter.log("ID : TCPAR_11 : Verify that the DTS user cannot edit the Multi-Channel Room Model info when the “Manage Audio” privilege is disabled");
	
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
			VP: Verify that the 616D Room Model Detail page is displayed but the “Edit Version” link is not displayed.
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
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Manage_audio_routes.getName());
		audioControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		audioControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// VP. Verify that the Audio Routes page is displayed
		Assert.assertTrue(audioControl.isElementPresent(PageHome.linkAudioroutes));
		// 9. Navigate to Audio Routes page
		audioControl.click(PageHome.linkAudioroutes);
		// VP: Verify that the audio list page is displayed
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.HEADER));
		// 10. Select a Multi-Channel Room Model item
		audioControl.click(AudioRoutes.MCROOM0);
		/*
		 * Verify that the 616D Room Model Detail page is displayed but the “Edit Version” link is not displayed.
		 */
		Assert.assertFalse(audioControl.isElementPresent(AudioRoutesInfo.EDIT));
	}
}
