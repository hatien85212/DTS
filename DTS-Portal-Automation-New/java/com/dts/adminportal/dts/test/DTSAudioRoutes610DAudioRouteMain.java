package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.AudioRouteController;
import com.dts.adminportal.controller.BaseController;
import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AudioRoutes;
import com.dts.adminportal.model.AudioRoutesEdit;
import com.dts.adminportal.model.AudioRoutesInfo;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.RoomModelEdit;
import com.dts.adminportal.model.RoomModelInfo;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.StringUtil;

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
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// Get all items in Standard route section
		String standardRoutes = audioControl.getTextByXpath(AudioRoutes.STANDARD_ROUTE_CONTAINER);
		// Get all items in Other route section
		String otherRoutes = audioControl.getTextByXpath(AudioRoutes.OTHER_ROUTE_CONTAINER);
		String stereoroommodels = audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_MODELS_CONTAINER);
		/*
		 * Verify that the "Standard Products" and "Other Audio Routes" sections display in "Audio Routes" page
		 */
		Assert.assertTrue(ListUtil.containsListText(standardRoutes, AudioRoutes.STANDARD_ROUTES));
		Assert.assertTrue(ListUtil.containsListText(otherRoutes, AudioRoutes.OTHER_ROUTES));
		Assert.assertTrue(ListUtil.containsListText(stereoroommodels, AudioRoutes.STEREO_ROOM_MODELS));
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.MCROOM0));
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.MCROOM1));
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.MCROOM2));
	}
	
	/*
	 * Verify that the audio route's lasted updated date is displayed next to its link.
	 */
	@Test
	public void TC610DAM_02() {
		audioControl.addLog("ID : TC610DAM_02 : Verify that the audio route's lasted updated date is displayed next to its link");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Click on an audio routes link
			5. Click on Edit Version link
			6. Edit any information of Audio route
			7. Save Changes
			8. Navigate to "Audio Routes" page 
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Click on an audio routes link
		audioControl.click(AudioRoutes.LINK_STANDARD_AUDIO);
		// 5. Click on Edit Version link
		String audioname = audioControl.getTextByXpath(AudioRoutes.AUDIO_DETAILS_HEADER);
		audioControl.editVersion();
		// 6. Edit any information on Audio route
		audioControl.type(AudioRoutes.EDIT_AUDIO_SALESFORCE_ID, RandomStringUtils.randomNumeric(5));
		// 7. Save Changes
		audioControl.click(AudioRoutes.EDIT_AUDIO_SAVE);
		// 8. Navigate to "Audio Routes" page		
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		/*
		 * Verify that the audio route's lasted updated date is displayed next to its link
		 */	
		String all_text = audioControl.getTextByXpath("//*[@id='standard_router']");
		String date = StringUtil.getDateFromText(all_text);
		System.out.println(date);
		System.out.println(audioname + " ("+ date + ")");
		Assert.assertTrue(all_text.contains(audioname + " ("+ date + ")"));
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
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Click on an other audio routes link
		audioControl.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		/*
		 * Verify that The 615D Audio Route Detail page is displayed 
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.STANDARD_ROUTES[0]);
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
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Click on a Stereo Room Model Link
		audioControl.selectAnAudioRouteByName(AudioRoutes.STEREO_ROOM_MODELS[0]);
		/*
		 * Verify that The 616D Room Model Detail page is displayed 
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_UUID),"a7ef49b3-fadd-4269-9047-947987e5910c");
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_ID),"StereoRoom0");
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		audioControl.selectAnAudioRouteByName(AudioRoutes.STEREO_ROOM_MODELS[1]);
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_UUID),"98b232ac-973e-404c-9a0d-4b0cb9ae3f43");
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
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Click on a Multi - Channel Room Model Link
		audioControl.click(AudioRoutes.MCROOM0);
		/*
		 * Verify that The 616D Room Model Detail page is displayed 
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_UUID),"f08a4c48-0887-11e4-9191-0800200c9a25");
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_ID),"McRoom0");
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		audioControl.click(AudioRoutes.MCROOM1);
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_UUID),"f08a4c48-0887-11e4-9191-0800200c9a26");
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_ID),"McRoom1");
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		audioControl.click(AudioRoutes.MCROOM2);
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_UUID),"f08a4c48-0887-11e4-9191-0800200c9a27");
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
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		/*
		 * The "Other Audio Routes", "Stereo Room Models", "Multi-Channel Room Models" sections are displayed "<sub_type> - <model_name>"
		 */
		// Other Audio Routes
		ArrayList<String> otheraudioroute = audioControl.getAudioRouteName(AudioRoutes.OTHER_ROUTE_CONTAINER);
		Assert.assertTrue(ListUtil.containsAll(otheraudioroute, AudioRoutes.OTHER_ROUTES));
		// Stereo Room Models
		ArrayList<String> stereorm = audioControl.getAudioRouteName(AudioRoutes.STEREO_ROOM_MODELS_CONTAINER);
		Assert.assertTrue(ListUtil.containsAll(stereorm, AudioRoutes.STEREO_ROOM_MODELS));
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
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Click an audio route link in "Stereo Room Models" section
		audioControl.click(AudioRoutes.LINK_STEREO_ROOM);
		// 5. Click "Edit Version" link
		audioControl.editVersion();
		// 6. Input new value into "Name" field
		audioControl.editData(AudioRoutesEdit.DISPLAY_NAME,"New name");
		// 7. Click "Save" link
		audioControl.click(AudioRoutes.EDIT_AUDIO_SAVE);
		// 8. Navigate to “Audio” page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
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
		audioControl.click(AudioRoutes.EDIT_AUDIO_SAVE);
		// Navigate to “Audio” page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
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
		audioControl.click(AudioRoutes.EDIT_AUDIO_SAVE);
		// For Stereo Room Model
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		audioControl.click(AudioRoutes.LINK_STEREO_ROOM);
		audioControl.click(RoomModelInfo.EDIT);
		audioControl.editData(AudioRoutesEdit.DISPLAY_NAME,"Wide");
		audioControl.click(RoomModelEdit.SAVE);
		
	
	}



	/*
	 * Verify that the 616D Room Model Detail page is displayed when clicking on room model links.
	 */
	@Test
	public void TC610DAM_10() {
		audioControl.addLog("ID : TC610DAM_10 : Verify that the 616D Room Model Detail page is displayed when clicking on room model links.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Click on a room model link
			VP: The 616D Room Model  Detail page is displayed
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Click on a room model link
		audioControl.click(AudioRoutes.MCROOM0);
		/*
		 * Verify that The 616D Audio Route Detail page is displayed 
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_UUID),"f08a4c48-0887-11e4-9191-0800200c9a25");
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_ID),"McRoom0");
	}
	
	/*
	 * Verify that the room model's lasted updated date is displayed next to its link.
	 */
	@Test
	public void TC610DAM_11() {
		audioControl.addLog("ID : TC610DAM_11 : Verify that the room model's lasted updated date is displayed next to its link.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Pickup a room model item which the latest update date is from previous date
			5. Edit above room model successfully
			6. Navigate to "Audio Routes" page again
			VP: The latest updated date is displayed next to its item after updating.
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		String text = driver.findElement(By.xpath(AudioRoutes.STEREO_ROOM_MODELS_CONTAINER)).getText();
		// 4. Pickup a room model item which the latest update date is from previous date
		audioControl.click(AudioRoutes.STEREO_ROOM0);
		// 5. Edit above room model successfully
		audioControl.editVersion();
		audioControl.click(AudioRoutes.EDIT_AUDIO_SAVE);
		//String audioname = audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM0);
		//6. Navigate to "Audio Routes" page again
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		/*
		 * VP: The latest updated date is displayed next to its item after updating.
		 */
		DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		Date date = new Date();
		String datecurrent = dateFormat.format(date).toString();
		String datetuning = StringUtil.getDateFromText(text);
		Assert.assertEquals(datecurrent,datetuning);
		}
	}

