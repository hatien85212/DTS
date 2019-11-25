package com.dts.adminportal.dts.test;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AudioRoutes;
import com.dts.adminportal.model.AudioRoutesEdit;
import com.dts.adminportal.model.AudioRoutesInfo;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.RoomModelEdit;
import com.dts.adminportal.model.RoomModelInfo;
import com.dts.adminportal.util.DateUtil;
import com.dts.adminportal.util.ListUtil;

public class DTSAudioRoutes610DAudioRouteMain extends BasePage{

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
	}
	
	/*
	 * Verify that the Verify that the "Standard Accessories", "Internal speaker" and "Other Audio Routes" sections in "Audio Routes" page
	 */
	@Test
	public void TC610DAM_01() {
		audioControl.addLog("ID : TC610DAM_01 : Verify that the Verify that the 'Standard Accessories', 'Internal speaker' and 'Other Audio Routes' sections in 'Audio Routes' page");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.linkAudioroutes);
		// Get all items in Standard route section
		String standardRoutes = audioControl.getTextByXpath(AudioRoutes.STANDARD_ROUTE_CONTAINER);
		// Get all items in Other route section
		String otherRoutes = audioControl.getTextByXpath(AudioRoutes.OTHER_ROUTE_CONTAINER);
		String stereoroommodels = audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_MODELS_CONTAINER);
		/*
		 * Verify that the "Standard Products" and "Other Audio Routes" sections display in "Audio Routes" page
		 */
		Assert.assertTrue(ListUtil.containsListText(standardRoutes, AudioRoutes.Standard_Routes.getStandard()));
		Assert.assertTrue(ListUtil.containsListText(otherRoutes, AudioRoutes.Other_Routes.getOther()));
		Assert.assertTrue(ListUtil.containsListText(stereoroommodels, AudioRoutes.Stereo_Room_Models.getNames()));
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.MCROOM0));
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.MCROOM1));
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.MCROOM2));
	}
	
	/*
	 * Verify that the audio route's published date is displayed next to its link.
	 */
	@Test
	public void TC610DAM_02() {
		audioControl.addLog("ID : TC610DAM_02 : Verify that the audio route's published date is displayed next to its link");
		/*
			1. Navigate to DTS portal	
			2. Log into DTS portal as a DTS user successfully	
			3. Navigate to "Audio Routes" page	
			4. Pickup a draft audio route item	
			5. Click "Publish" link	
			6. Navigate to "Audio Routes" page again	
			VP: The published date is displayed next to its item after publishing.

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.linkAudioroutes);
		// 4. Pickup a draft audio route item
		audioControl.click(AudioRoutes.LINK_STANDARD_AUDIO);
		// 5. Click "Publish" link	
		String audioname = audioControl.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL);
		audioControl.editVersion();
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING,AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined.getName());
		if(appDeviceControl.isElementPresent(AudioRoutesEdit.ADD_IMAGE250)){
			appDeviceControl.uploadFile(AudioRoutesEdit.ADD_IMAGE250,AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		};
		if(appDeviceControl.isElementPresent(AudioRoutesEdit.ADD_IMAGE500)){
			appDeviceControl.uploadFile(AudioRoutesEdit.ADD_IMAGE500,AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		};
		if(appDeviceControl.isElementPresent(AudioRoutesEdit.ADD_IMAGE1000)){
			appDeviceControl.uploadFile(AudioRoutesEdit.ADD_IMAGE1000,AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		};
		audioControl.click(AudioRoutesEdit.SAVE);
		audioControl.click(AudioRoutesInfo.PUBLISH);
		// 6. Navigate to "Audio Routes" page again
		audioControl.click(PageHome.linkAudioroutes);
		/*
		 * VP: The published date is displayed next to its item after publishing.
		 */
		String date = DateUtil.getADateGreaterThanToday("dd MMM yyyy", 0);
		String audio_text = audioControl.getTextByXpath(AudioRoutes.STANDARD_ROUTE_CONTAINER);
		System.out.println(date);
		Assert.assertTrue(audio_text.contains(audioname + " ("+ date + ")"));
	}
	
	/*
	 * Verify that the Audio Route Detail page is displayed when clicking on audio route link.
	 */
	@Test
	public void TC610DAM_03() {
		audioControl.addLog("ID : TC610DAM_03 : Verify that the 615D Audio Route Detail page is displayed when clicking on audio route link");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Click on an other audio routes link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.linkAudioroutes);
		// 4. Click on an other audio routes link
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		/*
		 * Verify that The 615D Audio Route Detail page is displayed 
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
	}
	
	/*
	 * Verify that the 616D Room Model Detail page is displayed when clicking on Stereo Room Model link.
	 */
	@Test
	public void TC610DAM_04() {
		audioControl.addLog("ID : TC610DAM_04 : Verify that the 616D Room Model Detail page is displayed when clicking on Stereo Room Model link.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Click on a Stereo Room Model Link
			VP: Verify that The 616D Room Model Detail page is displayed
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.linkAudioroutes);
		// 4. Click on a Stereo Room Model Link
		audioControl.selectAnAudioRouteByName(AudioRoutes.Stereo_Room_Models.Wide.getName());
		/*
		 * Verify that The 616D Room Model Detail page is displayed 
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.ROOMMODEL_UUID),AudioRoutesEdit.AudioUUIDs.StereoRoom0.getUUID());
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_ID),"StereoRoom0");
		audioControl.click(PageHome.linkAudioroutes);
		audioControl.selectAnAudioRouteByName(AudioRoutes.Stereo_Room_Models.In_front.getName());
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.ROOMMODEL_UUID),AudioRoutesEdit.AudioUUIDs.StereoRoom1.getUUID());
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_ID),"StereoRoom1");
	}
	
	/*
	 * Verify that the 616D Room Model Detail page is displayed when clicking on Multi - Channel Room Model link.
	 */
	@Test
	public void TC610DAM_05() {
		audioControl.addLog("ID : TC610DAM_05 : Verify that the 616D Room Model Detail page is displayed when clicking on Multi - Channel Room Model link.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Click on a Multi - Channel Room Model Link
			VP: Verify that The 616D Room Model Detail page is displayed
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.linkAudioroutes);
		// 4. Click on a Multi - Channel Room Model Link
		audioControl.click(AudioRoutes.MCROOM0);
		/*
		 * Verify that The 616D Room Model Detail page is displayed 
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.ROOMMODEL_UUID),AudioRoutesEdit.AudioUUIDs.McRoom0.getUUID());
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_ID),"McRoom0");
		audioControl.click(PageHome.linkAudioroutes);
		audioControl.click(AudioRoutes.MCROOM1);
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.ROOMMODEL_UUID),AudioRoutesEdit.AudioUUIDs.McRoom1.getUUID());
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_ID),"McRoom1");
		audioControl.click(PageHome.linkAudioroutes);
		audioControl.click(AudioRoutes.MCROOM2);
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.ROOMMODEL_UUID),AudioRoutesEdit.AudioUUIDs.McRoom2.getUUID());
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_ID),"McRoom2");
	}
	
	/*
	 * Verify that The "Other Audio Routes", "Stereo Room Models", "Multi-Channel Room Models" sections are displayed "<sub_type> - <model_name>"
	 */

	@Test
	public void TC610DAM_12(){
		audioControl.addLog("ID : TC610DAM_11 : Verify that The 'Other Audio Routes', 'Stereo Room Models', 'Multi-Channel Room Models' sections are displayed '<sub_type> - <model_name>' ");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio" page
			VP: The "Other Audio Routes", "Stereo Room Models", "Multi-Channel Room Models" sections are displayed "<sub_type> - <model_name>"
		*/
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio" page
		audioControl.click(PageHome.linkAudioroutes);
		/*
		 * The "Other Audio Routes", "Stereo Room Models", "Multi-Channel Room Models" sections are displayed "<sub_type> - <model_name>"
		 */
		// Other Audio Routes
		ArrayList<String> otheraudioroute = audioControl.getAudioRouteName(AudioRoutes.OTHER_ROUTE_CONTAINER);
		Assert.assertTrue(ListUtil.containsAll(otheraudioroute, AudioRoutes.Other_Routes.getOther()));
		// Stereo Room Models
		ArrayList<String> stereorm = audioControl.getAudioRouteName(AudioRoutes.STEREO_ROOM_MODELS_CONTAINER);
		Assert.assertTrue(ListUtil.containsAll(stereorm, AudioRoutes.Stereo_Room_Models.getNames()));
		// Multi-Channel Room Models
		ArrayList<String> mcroom = audioControl.getAudioRouteName(AudioRoutes.MULTI_CHANNEL_CONTAINER);
		Assert.assertTrue(ListUtil.checkListContainSubString(mcroom," - "));
	}
	
	
	/*
	 * Verify that The "Other Audio Routes", "Stereo Room Models", "Multi-Channel Room Models" sections are displayed with new value name in "Audio Route" main page when updating "Name" field.
	 */
	@Test
	public void TC610DAM_13() {
		audioControl.addLog("ID : TC610DAM_12: Verify that The 'Other Audio Routes', 'Stereo Room Models', 'Multi-Channel Room Models' sections are displayed with new value name in 'Audio Route' main page when updating 'Name' field.");
		/*
		 *  1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio" page
			4. Click an audio route link in "Stereo Room Models" section 
			5. Click "Edit Version" link
			6. Input new value into "Name" field
			7. Click "Save" link
			8. Navigate to “Audio” page
			VP: The "Stereo Room Models" section is displayed with new value name as format "<sub_type> - <model_name>".
			9. Repeat from step 4 to step 8 with "Other Audio Routes" and "Multi-Channel RoomModels" sections
			VP: The "Other Audio Routes", "Stereo Room Models", "Multi-Channel Room Models" sections 
			are displayed with new value name as format "<sub_type> - <model_name>".

		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio" page
		audioControl.click(PageHome.linkAudioroutes);
		// 4. Click an audio route link in "Stereo Room Models" section
		audioControl.click(AudioRoutes.LINK_STEREO_ROOM);
		// 5. Click "Edit Version" link
		audioControl.editVersion();
		// 6. Input new value into "Name" field
		audioControl.editData(AudioRoutesEdit.DISPLAY_NAME,"New name");
		// 7. Click "Save" link
		audioControl.click(RoomModelEdit.SAVE);
		// 8. Navigate to “Audio” page
		audioControl.click(PageHome.linkAudioroutes);
		/*
		 * VP: The "Stereo Room Models" section
		 * is displayed with new value name as format "<sub_type> - <model_name>".
		 */
		String stereo_new = audioControl.getTextByXpath(AudioRoutes.LINK_STEREO_ROOM);
		Assert.assertTrue(stereo_new.contains(" - New name"));
		// 9. Repeat from step 4 to step 8 with "Other Audio Routes" and "Multi-Channel RoomModels" sections
		// Other Audio Routes
		audioControl.click(AudioRoutes.LINK_OTHER_AUDIO);
		// Click "Edit Version" link
		audioControl.editVersion();
		// Input new value into "Name" field
		audioControl.editData(AudioRoutesEdit.DISPLAY_NAME,"New name");
		// Click "Save" link
		audioControl.click(RoomModelEdit.SAVE);
		// Navigate to “Audio” page
		audioControl.click(PageHome.linkAudioroutes);
		/*
		 * VP: The "Other Audio Routes", "Stereo Room Models", "Multi-Channel Room Models" sections 
		 * are displayed with new value name as format "<sub_type> - <model_name>".
		 */
		String other_new = audioControl.getTextByXpath(AudioRoutes.LINK_OTHER_AUDIO);
		Assert.assertTrue(other_new.contains(" - New name"));
		
		// Set default name back
		// For Other Audio Route
		audioControl.click(AudioRoutes.LINK_OTHER_AUDIO);
		audioControl.click(AudioRoutesEdit.EDIT);
		audioControl.editData(AudioRoutesEdit.DISPLAY_NAME,"Internal Speakers (Default)");
		audioControl.click(RoomModelEdit.SAVE);
		// For Stereo Room Model
		audioControl.click(PageHome.linkAudioroutes);
		audioControl.click(AudioRoutes.LINK_STEREO_ROOM);
		audioControl.click(RoomModelInfo.EDIT);
		audioControl.editData(AudioRoutesEdit.DISPLAY_NAME,"Wide");
		audioControl.click(RoomModelEdit.SAVE);
		
	
	}
	
	/*
	 * Verify that the room model's published date is displayed next to its link.
	 */
	@Test
	public void TC610DAM_11() {
		audioControl.addLog("ID : TC610DAM_11 : Verify that the room model's published date is displayed next to its link.");
		/*
			1. Navigate to DTS portal	
			2. Log into DTS portal as a DTS user successfully	
			3. Navigate to "Audio Routes" page	
			4. Pickup a draft room model item	
			5. Click "Publish" link	
			6. Navigate to "Audio Routes" page again	
			VP: The published date is displayed next to its item after publishing.

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.linkAudioroutes);
		String audioname = audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM0);
		// 4. Pickup a draft room model item
		audioControl.click(AudioRoutes.STEREO_ROOM0);
		// 5. Click "Publish" link
		audioControl.editVersion();
		audioControl.uploadFileTuning(RoomModelEdit.ADD_TUNING,AddEditProductModel.FileUpload.Super_Stereo_Wide.getName());
		audioControl.click(RoomModelEdit.SAVE);
		audioControl.click(AudioRoutesInfo.PUBLISH);
		//6. Navigate to "Audio Routes" page again
		audioControl.click(PageHome.linkAudioroutes);
		/*
		 * VP: The published date is displayed next to its item after publishing.
		 */
		String date = DateUtil.getADateGreaterThanToday("dd MMM yyyy", 0);
		String audio_text = audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_MODELS_CONTAINER);
		System.out.println(date);
		Assert.assertTrue(audio_text.contains(audioname + " ("+ date + ")"));
		}
	
	/*
	 * Verify that the room model's published date is displayed next to its link.
	 */
	@Test
	public void TC610DAM_15() {
		audioControl.addLog("Verify that The Other Audio Routes, sections is displayed Attached 6, 7, 8");
		/*
			1. Navigate to DTS portal	
			2. Log into DTS portal as a DTS user successfully	
			  VP: Verify that Attached 6,7,8 is displayed in  "Other Audio Routes” section	

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.linkAudioroutes);
		/*
		 * Verify that "Other Audio Routes" contain ''Attached 6,7,8''
		 */
		String other_new = audioControl.getTextByXpath(AudioRoutes.Attached6);
		Assert.assertTrue(other_new.contains("Attached6 - Internal Speakers (mode 5)"));
		String other_new_1 = audioControl.getTextByXpath(AudioRoutes.Attached7);
		Assert.assertTrue(other_new_1.contains("Attached7 - Internal Speakers (mode 6)"));
		String other_new_2 = audioControl.getTextByXpath(AudioRoutes.Attached8);
		Assert.assertTrue(other_new_2.contains("Attached8 - Internal Speakers (mode 7)"));
		}
	}

