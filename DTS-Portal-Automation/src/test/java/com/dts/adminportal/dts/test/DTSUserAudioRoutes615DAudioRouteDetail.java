package com.dts.adminportal.dts.test;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.DTSUtil;
import com.dts.adminportal.util.DateUtil;

import dts.com.adminportal.model.AudioRoutes;
import dts.com.adminportal.model.AudioRoutesEdit;
import dts.com.adminportal.model.AudioRoutesInfo;
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.Xpath;

public class DTSUserAudioRoutes615DAudioRouteDetail extends CreatePage{
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
	 * Verify that the Display Name section displays the audio route name with appropriate languages
	 */
	@Test
	public void TC615DAD_01() {
		result.addLog("ID : TC615DAD_01 : Verify that the Display Name section displays the audio route name with appropriate languages");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			5. Click "Edit Version" link
			6. Set the Audio Route's display name for all languages
			7. Click "Save" link
		 */
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		// Get default display name
		String defaultName = home.getTextByXpath(AudioRoutesInfo.DEFAULT_DISPLAY_NAME);
		// 5. Click "Edit Version" link
		home.click(AudioRoutesInfo.EDIT);
		// 6. Set the Audio Route's display name for all languages
		ArrayList<String> allLanguages = home.addAllLanguage(AudioRoutesEdit.LANGUAGE_CONTAINER);
		allLanguages.add(0, defaultName);
		// 7. Click "Save" link
		home.click(AudioRoutesEdit.SAVE);
		// Get all languages name display
		ArrayList<String> displayedLanguages = home.getAudioRouteName(AudioRoutesInfo.ALL_DISPLAY_NAME);
		/*
		 * Verify that The 615D Audio Route Detail page is displayed and the Display Name section is displayed with all languages
		 */
		Assert.assertEquals(home.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.STANDARD_ROUTES[0]);
		Assert.assertTrue(DTSUtil.containsAll(allLanguages, displayedLanguages));
	}
	
	/*
	 * Verify that the Primary Catalog Image section is displayed in ordered from left to right
	 */
	@Test
	public void TC615DAD_02() {
		result.addLog("ID : TC615DAD_02 : Verify that the Primary Catalog Image section is displayed in ordered from left to right");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			5. Click "Edit Version" link
			6. Upload three kind of Primary Catalog Image.
			7. Click "Save" link
		 */
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Click on an audio routes link
		home.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		// 5. Click on Edit Version link
		home.click(AudioRoutesInfo.EDIT);
		// Delete image if exist
		home.deleteAllUploadedImage(AudioRoutesEdit.IMAGE_CATALOG);
		// 6.Upload three kind of Primary Catalog Image
		home.uploadFile(AudioRoutesEdit.ADD_IMAGE250, Constant.IMG_NAME[0]);
		home.uploadFile(AudioRoutesEdit.ADD_IMAGE500, Constant.IMG_NAME[1]);
		home.uploadFile(AudioRoutesEdit.ADD_IMAGE1000, Constant.IMG_NAME[2]);
		// 7. Click "Save" link
		home.click(AudioRoutesEdit.SAVE);
		/*
		 * Verify that The Audio Route Details page is displayed and the primary catalog image dislays in ordered from left to right: 250x250,500x500 and 1000x1000
		 */
		Assert.assertTrue(home.isImageDisplayLeftToRight(AudioRoutesInfo.IMAGE_CATALOG));
	}

	/*
	 * Verify that the updated date of tuning file is displayed next to "Tuning File" link
	 */
	@Test
	public void TC615DAD_03() {
		result.addLog("ID : TC615DAD_03 : Verify that the updated date of tuning file is displayed next to 'Tuning File' link");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			5. Click "Edit Version" link
			6. Upload the tuning file
			7. Click "Save" link
		 */
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Click on an audio routes link
		home.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		// 5. Click on Edit Version link
		home.click(AudioRoutesInfo.EDIT);
		// Delete tuning file if exist
		if (home.isElementPresent(AudioRoutesEdit.DELETE_TUNING_ICON)){
			home.doDelete(AudioRoutesEdit.DELETE_TUNING_ICON);
		}	
		// 6.Upload the tuning file
		home.uploadFile(AudioRoutesEdit.ADD_TUNING, Constant.AUDIO_ROUTE_FILE);
		home.click(AudioRoutesEdit.SAVE);
		/*
		 * Verify that the updated date of tuning file is displayed next to "Tuning File" link in the edit page
		 */
		String date = DateUtil.getADateGreaterThanToday("dd MMM yyyy", 0);
		Assert.assertTrue(home.getTextByXpath(AudioRoutesInfo.ROUTER_TUNING).equals(Constant.AUDIO_ROUTE_FILE + " (" + date + ")"));
	}
	
	/*
	 * Verify that the Display Name, Audio Route ID, Type, Input Specification are displayed as read only
	 */
	@Test
	public void TC615DAD_04() {
		result.addLog("ID : TC615DAD_04 : Verify that the Display Name, Audio Route ID, Type, Input Specification are displayed as read only");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
		 */
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);		
		// 4. Click on an audio routes link
		home.click(AudioRoutes.LINK_STANDARD_AUDIO);
		// Get the value of Display name , Audio Routes ID , Type , Input Specification

		/*
		 * Verify that the Display Name, Audio Route ID, Type, Input Specification are displayed as read only
		 */
		
		Assert.assertFalse(home.canEdit(AudioRoutes.AUDIO_DETAILS_DISPLAY_NAME));
		Assert.assertFalse(home.canEdit(AudioRoutes.AUDIO_DETAILS_ROUTE_ID));
		Assert.assertFalse(home.canEdit(AudioRoutes.AUDIO_DETAILS_TYPE));
		Assert.assertFalse(home.canEdit(AudioRoutes.AUDIO_DETAILS_INPUT_SPECIFICATIONS));
	}
}
