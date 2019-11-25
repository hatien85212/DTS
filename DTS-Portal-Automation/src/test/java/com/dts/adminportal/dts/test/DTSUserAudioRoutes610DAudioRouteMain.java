package com.dts.adminportal.dts.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.DTSUtil;
import com.dts.adminportal.util.StringUtilDts;

import dts.com.adminportal.model.AudioRoutes;
import dts.com.adminportal.model.AudioRoutesInfo;
import dts.com.adminportal.model.Xpath;

public class DTSUserAudioRoutes610DAudioRouteMain extends CreatePage{
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
	 * Verify that the Verify that the "Standard Accessories", "Internal speaker" and "Other Audio Routes" sections in "Audio Routes" page
	 */
	@Test
	public void TC610DAM_01() {
		result.addLog("ID : TC610DAM_01 : Verify that the Verify that the 'Standard Accessories', 'Internal speaker' and 'Other Audio Routes' sections in 'Audio Routes' page");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
		 */
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// Get all items in Standard route section
		String standardRoutes = home.getTextByXpath(AudioRoutes.STANDARD_ROUTE_CONTAINER);
		// Get all items in Other route section
		String otherRoutes = home.getTextByXpath(AudioRoutes.OTHER_ROUTE_CONTAINER);
		/*
		 * Verify that the "Standard Products" and "Other Audio Routes" sections display in "Audio Routes" page
		 */
		Assert.assertTrue(DTSUtil.containsListText(standardRoutes, AudioRoutes.STANDARD_ROUTES));
		Assert.assertTrue(DTSUtil.containsListText(otherRoutes, AudioRoutes.OTHER_ROUTES));
	}
	
	/*
	 * Verify that the audio route's lasted updated date is displayed next to its link.
	 */
	@Test
	public void TC610DAM_02() {
		result.addLog("ID : TC610DAM_02 : Verify that the audio route's lasted updated date is displayed next to its link");
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
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Click on an audio routes link
		home.click(AudioRoutes.LINK_STANDARD_AUDIO);
		// 5. Click on Edit Version link
		String audioname = home.getTextByXpath(AudioRoutes.AUDIO_DETAILS_HEADER);
		home.click(AudioRoutes.AUDIO_DETAILS_EDIT_VERSION);
		// 6. Edit any information on Audio route
		home.type(AudioRoutes.EDIT_AUDIO_SALESFORCE_ID, RandomStringUtils.randomNumeric(5));
		// 7. Save Changes
		home.click(AudioRoutes.EDIT_AUDIO_SAVE);
		// 8. Navigate to "Audio Routes" page		
		home.click(Xpath.LINK_AUDIO_ROUTES);
		/*
		 * Verify that the audio route's lasted updated date is displayed next to its link
		 */	
		String all_text = home.getTextByXpath("//*[@id='standard_router']");
		String date = StringUtilDts.getDateFromText(all_text);
		System.out.println(date);
		System.out.println(audioname + " ("+ date + ")");
		Assert.assertTrue(all_text.contains(audioname + " ("+ date + ")"));
	}
	
	/*
	 * Verify that the Audio Route Detail page is displayed when clicking on audio route link.
	 */
	@Test
	public void TC610DAM_03() {
		result.addLog("ID : TC610DAM_03 : Verify that the 615D Audio Route Detail page is displayed when clicking on audio route link");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Click on an other audio routes link
		 */
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Click on an other audio routes link
		home.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		/*
		 * Verify that The 615D Audio Route Detail page is displayed 
		 */
		Assert.assertEquals(home.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.STANDARD_ROUTES[0]);
	}
	
	
}
