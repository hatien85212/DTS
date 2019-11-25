package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.DTSUtil;

import dts.com.adminportal.model.AddAccessory;
import dts.com.adminportal.model.AudioRoutes;
import dts.com.adminportal.model.AudioRoutesEdit;
import dts.com.adminportal.model.AudioRoutesInfo;
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.LanguagePackage;
import dts.com.adminportal.model.Xpath;

// TC615DAD_02 : Failed due to PDFF220 catalog image

public class DTSUserAudioRoutes620DAudioRouteEdit extends CreatePage{
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
	 * Verify that the invalid tuning file could not be uploaded
	 */
	@Test
	public void TC620DAE_01() {
		result.addLog("ID : TC620DAE_01 : Verify that the invalid tuning file could not be uploaded");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Click "Edit" link
			6. Upload an invalid tuning file
		 */
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Click on an audio routes link
		home.click(AudioRoutes.LINK_STANDARD_AUDIO);
		// 5. Click on Edit Version link
		home.click(AudioRoutes.AUDIO_DETAILS_EDIT_VERSION);
		//6. Upload an invalid tuning file
		home.doDelete(AudioRoutesEdit.DELETE_TUNING_ICON);
		home.uploadFile(AudioRoutesEdit.ADD_TUNING, Constant.tuning_hpxtt);
		Assert.assertTrue(home.getTextByXpath(AudioRoutesEdit.AUDIOROUTE_UPLOAD_MESSAGE).contains(AudioRoutesEdit.UPLOAD_FILE_MESSSAGE[1]));
		
		
	}
	
	/*
	 * Verify that the "Type" and "Audio Route ID" section are displayed as readonly
	 */
	@Test
	public void TC620DAE_02() {
		result.addLog("ID : TC620DAE_02 : Verify that the 'Type' and 'Audio Route ID' section are displayed as readonly");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Click "Edit" link
			6. Try to edit "Type" and "Audio Route ID" section
		 */
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Click on an audio routes link
		home.click(AudioRoutes.LINK_STANDARD_AUDIO);
		// 5. Click on Edit Version link
		home.click(AudioRoutes.AUDIO_DETAILS_EDIT_VERSION);
		// 6. Try to edit "Type" and "Audio Route ID" section
		Assert.assertFalse(home.canEdit(AudioRoutes.EDIT_TYPE));
		Assert.assertFalse(home.canEdit(AudioRoutes.EDIT_AUDIO_ROUTE_ID));
	}
	
	/*
	 * Verify that the names of audio route in 615D Audio Route Detail page are displayed in 620D Audio Route Edit page correctly.
	 */
	@Test
	public void TC620DAE_03() {
		result.addLog("ID : TC620DAE_03 : Verify that the names of audio route in 615D Audio Route Detail page "
				+ "are displayed in 620D Audio Route Edit page correctly.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Notice for the name of audio route
			6. Click "Edit" link
		 */
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.click(AudioRoutes.LINK_STANDARD_AUDIO);
		// VP: The 615D Audio Route Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AudioRoutes.AUDIO_DETAILS_DISPLAY_NAME));
		// 5. Notice for the name of audio route
		String name = home.getTextByXpath(AudioRoutes.AUDIO_DETAILS_HEADER);
		// 6. Click "Edit" link
		home.click(AudioRoutes.AUDIO_DETAILS_EDIT_VERSION);
		/*
		 * Names of audio route in 615D Audio Route Detail 
		 * are displayed in 620D Audio Route Edit page correctly.
		 */
		Assert.assertEquals(name, home.getTextByXpath(AudioRoutes.EDIT_AUDIO_DETAILS_HEADER));
	}
	/*
	 * Verify that the Display Name language dropdown contains 10 items properly
	 */
	@Test
	public void TC620DAE_04() {
		result.addLog("ID : TC620DAE_04 : Verify that the Display Name language dropdown contains 10 items properly");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Click "Edit" link
		 */
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.click(AudioRoutes.LINK_STANDARD_AUDIO);
		// VP: The 615D Audio Route Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AudioRoutes.AUDIO_DETAILS_DISPLAY_NAME));
		// 5. Click "Edit" link
		home.click(AudioRoutes.AUDIO_DETAILS_EDIT_VERSION);
		/*
		 * The Display Name language dropdown contains items: 
		 * - Select -, 
		 * Chinese - Simplified, 
		 * Chinese - Traditional, 
		 * French, 
		 * German, 
		 * Italian, 
		 * Japanese, 
		 * Korean, 
		 * Russian and 
		 * Spanish.
		 */
		ArrayList<String> languages = home.getAllOption(AudioRoutes.EDIT_LANGUAGE_SELECT);
		Assert.assertTrue(DTSUtil.containsAll(languages, AddAccessory.LANGUAGE_LIST));
	}
	/*
	 * Verify that the hint text "Inherits default" is displayed for an empty fields of each  display name language.
	 */
	@Test
	public void TC620DAE_05() {
		result.addLog("ID : TC620DAE_05 : Verify that the hint text 'Inherits default' is displayed for an empty fields of each  display name language.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Click "Edit" link
			6. Select another language for Display Name
		 */
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.click(AudioRoutes.LINK_STANDARD_AUDIO);
		// VP: The 615D Audio Route Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AudioRoutes.AUDIO_DETAILS_DISPLAY_NAME));
		// 5. Click "Edit" link
		home.click(AudioRoutes.AUDIO_DETAILS_EDIT_VERSION);
		// 6. Select another language for Display Name
		home.selectOptionByName(AudioRoutes.EDIT_LANGUAGE_SELECT, AddAccessory.LANGUAGE_LIST[5]);
		/*
		 * The hint text "Inherits default" is displayed in Display Name text field.
		 */
		Assert.assertEquals(home.getAtribute(AudioRoutes.EDIT_LANGUAGE_INPUT, "placeholder"), "inherits default");
	}
	/*
	 * Verify that the external language display name text field is disabled when the language dropdown is changed back "Select".
	 */
	@Test
	public void TC620DAE_06() {
		result.addLog("ID : TC620DAE_06 : Verify that the external language display name text field is disabled when the language dropdown is changed back 'Select'.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Click "Edit" link
			6. Set language dropdown to another language
			7. Set language dropdown back to "Select"
		 */
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.click(AudioRoutes.LINK_STANDARD_AUDIO);
		// VP: The 615D Audio Route Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AudioRoutes.AUDIO_DETAILS_DISPLAY_NAME));
		// 5. Click "Edit" link
		home.click(AudioRoutes.AUDIO_DETAILS_EDIT_VERSION);
		// 6. Select another language for Display Name
		home.selectOptionByName(AudioRoutes.EDIT_LANGUAGE_SELECT, AddAccessory.LANGUAGE_LIST[5]);
		// 7. Set language dropdown back to "Select"
		home.selectOptionByName(AudioRoutes.EDIT_LANGUAGE_SELECT, AddAccessory.LANGUAGE_LIST[0]);
		/*
		 * The Display Name language text field is disabled.
		 */
		Assert.assertEquals(home.getAtribute(AddAccessory.EMPTY_LANGUAGE_FIELD, "disabled"),"true");
	}
	/*
	 * Verify that another language dropdown will display when selecting a Display Name language.
	 */
	@Test
	public void TC620DAE_07() {
		result.addLog("ID : TC620DAE_07 : Verify that another language dropdown will display when selecting a Display Name language.");
		/*	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			6. Set language dropdown to another item other than "Select"
		 */
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.click(AudioRoutes.LINK_OTHER_AUDIO);
		// VP: The 615D Audio Route Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AudioRoutes.AUDIO_DETAILS_DISPLAY_NAME));
		// 5. Click "Edit" link
		home.click(AudioRoutes.AUDIO_DETAILS_EDIT_VERSION);
		List<LanguagePackage> before = home.getLanguagePackage(AudioRoutes.EDIT_LANGUAGE_CONTAINER);
		// 6. Select another language for Display Name
		home.selectOptionByName(AudioRoutes.EDIT_LANGUAGE_SELECT, AddAccessory.LANGUAGE_LIST[5]);
		List<LanguagePackage> after = home.getLanguagePackage(AudioRoutes.EDIT_LANGUAGE_CONTAINER);
		/*
		 * Another language dropdown is displayed
		 */
		Assert.assertTrue(after.size() == before.size() + 1);
	}
	/*
	 * Verify that a warning message is displayed for duplicating language dropdowns.
	 */
	@Test
	public void TC620DAE_08() {
		result.addLog("ID : TC620DAE_08 : Verify that a warning message is displayed for duplicating language dropdowns.");
		/*	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Select language dropdown to another item other than "Select"
			VP: Verify that new language dropdown is displayed.
			6. Select the same item for second language dropdown
		 */
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.click(AudioRoutes.LINK_STANDARD_AUDIO);
		// VP: The 615D Audio Route Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AudioRoutes.AUDIO_DETAILS_DISPLAY_NAME));
		// 5. Click "Edit" link
		home.click(AudioRoutes.AUDIO_DETAILS_EDIT_VERSION);
		// 6. Select another language for Display Name
		home.selectOptionByName(AudioRoutes.EDIT_LANGUAGE_SELECT, AddAccessory.LANGUAGE_LIST[5]);
		// Get the language package
		List<LanguagePackage> languagepackage = home.getLanguagePackage(AudioRoutes.EDIT_LANGUAGE_CONTAINER);
		// Select the language at the next dropbox as same as the first one
		home.selectOptionByName(languagepackage.get(languagepackage.size()-1).languagedropbox, AddAccessory.LANGUAGE_LIST[5]);
		// Get the language package
		languagepackage.clear();
		languagepackage = home.getLanguagePackage(AudioRoutes.EDIT_LANGUAGE_CONTAINER);
		// Get text of the  warning message is displayed at the first package
		String warning = languagepackage.get(0).warningmessage;
		System.out.println(warning);
		/*
		 Verify that a warning message is displayed for duplicating language dropdowns
		 */
		Assert.assertTrue(warning.contains("Language is duplicated"));
	}
	/*
	 * Verify that the language dropdown is restore to default "Select" value and the input field is cleared when selecting the Trashcan icon.
	 */
	@Test
	public void TC620DAE_09() {
		result.addLog("ID : TC620DAE_09 : Verify that the language dropdown is restore to default 'Select' value and the input field is cleared when selecting the Trashcan icon.");
		/*	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Select language dropdown to another item other than "Select"
			6. Type value into model nam input field
			7. Click on Trashcan icon
		 */
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.click(AudioRoutes.LINK_STANDARD_AUDIO);
		// VP: The 615D Audio Route Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AudioRoutes.AUDIO_DETAILS_DISPLAY_NAME));
		// 5. Click "Edit" link
		home.click(AudioRoutes.AUDIO_DETAILS_EDIT_VERSION);
		// 6. Select another language for Display Name
		home.selectOptionByName(AudioRoutes.EDIT_LANGUAGE_SELECT, AddAccessory.LANGUAGE_LIST[5]);
		// Type value into model nam input field
		home.editData(AudioRoutes.EDIT_LANGUAGE_INPUT, RandomStringUtils.randomAlphabetic(10));
		// 7. Click on Trashcan icon
		home.click(AudioRoutes.EDIT_LANGUAGE_TRASH);
		/*
		 * The language dropdown is restore to default "Select" value and the input field is cleared 
		 */
		Assert.assertEquals(home.getItemSelected(AudioRoutes.EDIT_LANGUAGE_SELECT), AddAccessory.LANGUAGE_LIST[0]);
	}
	
	/*
	 * Verify that the three size of Primary Catalog Image display in an order list
	 */
	@Test
	public void TC620DAE_10() {
		result.addLog("ID : TC620DAE_10 : Verify that the three size of Primary Catalog Image display in an order list");
		/*	
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			5. Click Edit link
		 */
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		// 5. Click Edit link
		home.click(AudioRoutesInfo.EDIT);
		// Delete all image if exist
		home.deleteAllUploadedImage(AudioRoutesEdit.IMAGE_CATALOG);
		/*
		 * The 615D Audio Route Detail page is displayed and Three size of Primary Catalog Image display display in vertical order list: 250x250 pixels, 500x500 pixels, 1000x1000 pixels
		 */
		Assert.assertTrue(home.isImageDisplayInOrder(AudioRoutesEdit.IMAGE_CATALOG));
	}
	
	/*
	 * Verify that the image thumbnail is displayed correctly for each Primary Catalog Image.
	 */
	@Test
	public void TC620DAE_11(){
		result.addLog("ID : TC620DAE_11 : Verify that the image thumbnail is displayed correcty for each Primary Catalog Image." );
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Click Edit link
			6. Upload image for each size of Primary Catalog Image successfully
		*/
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.STANDARD_ROUTES[0]);
		// 5. Click Edit link
		home.click(AudioRoutesInfo.EDIT);
		// Delete all image if exist
		home.deleteAllUploadedImage(AudioRoutesEdit.IMAGE_CATALOG);
		// 6. Upload image for each size of Primary Catalog Image successfully
		home.uploadFile(AudioRoutesEdit.ADD_IMAGE250, Constant.IMG_NAME[0]);
		home.uploadFile(AudioRoutesEdit.ADD_IMAGE500, Constant.IMG_NAME[1]);
		home.uploadFile(AudioRoutesEdit.ADD_IMAGE1000, Constant.IMG_NAME[2]);
		/*
		 * Verify that The correct image thumbnail is displayed next to image name
		 */
		Assert.assertTrue(home.isElementDisplayHorizontal(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250, AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_INFO_250));
		Assert.assertTrue(home.getTextByXpath(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_INFO_250).contains(Constant.IMG_NAME[0]));
		Assert.assertTrue(home.isElementDisplayHorizontal(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_THUMBNAIL_500, AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_INFO_500));
		Assert.assertTrue(home.getTextByXpath(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_INFO_500).contains(Constant.IMG_NAME[1]));
		Assert.assertTrue(home.isElementDisplayHorizontal(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000, AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_INFO_1000));
		Assert.assertTrue(home.getTextByXpath(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_INFO_1000).contains(Constant.IMG_NAME[2]));
	}
	
	/*
	 * Verify that the uploaded date and file size are displayed below the image name for each size of Primary Catalog Image.
	 */
	@Test
	public void TC620DAE_12(){
		result.addLog("ID : TC620DAE_12 : Verify that the uploaded date and file size are displayed below the image name for each size of Primary Catalog Image." );
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Upload image for each size of Primary Catalog Image successfully
			VP: The uploaded date and file size are displayed below the image name for each size of Primary Catalog Image.
		*/
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.STANDARD_ROUTES[0]);
		// 5. Click Edit link
		home.click(AudioRoutesInfo.EDIT);
		// Delete all image if exist
		home.deleteAllUploadedImage(AudioRoutesEdit.IMAGE_CATALOG);
		// 6. Upload image for each size of Primary Catalog Image successfully
		home.uploadFile(AudioRoutesEdit.ADD_IMAGE250, Constant.IMG_NAME[0]);
		home.uploadFile(AudioRoutesEdit.ADD_IMAGE500, Constant.IMG_NAME[1]);
		home.uploadFile(AudioRoutesEdit.ADD_IMAGE1000, Constant.IMG_NAME[2]);
		/*
		 * The uploaded date and file size are displayed below the image name for each size of Primary Catalog Image.
		 */
		Assert.assertTrue(home.isElementDisplayVertically(AudioRoutesEdit.CATALOG_IMAGE_TITLE_LINK_250, AudioRoutesEdit.CATALOG_IMAGE_FILE_SIZE_250));
		Assert.assertTrue(home.isElementDisplayVertically(AudioRoutesEdit.CATALOG_IMAGE_TITLE_LINK_500, AudioRoutesEdit.CATALOG_IMAGE_FILE_SIZE_500));
		Assert.assertTrue(home.isElementDisplayVertically(AudioRoutesEdit.CATALOG_IMAGE_TITLE_LINK_500, AudioRoutesEdit.CATALOG_IMAGE_FILE_SIZE_500));
	}
	
	/*
	 * Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on inage thumbnail
	 */
	@Test
	public void TC620DAE_13() throws InterruptedException{
		result.addLog("ID : TC620DAE_13 : Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on inage thumbnail" );
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Upload image for each size of Primary Catalog Image successfully
			6. Click on image thumbnail of each size
			VP: A lightbox style popup with the picture showing in full size is displayed
		*/
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.STANDARD_ROUTES[0]);
		// 5. Click Edit link
		home.click(AudioRoutesInfo.EDIT);
		// Delete all image if exist
		home.deleteAllUploadedImage(AudioRoutesEdit.IMAGE_CATALOG);
		// 6. Upload image for each size of Primary Catalog Image successfully
		home.uploadFile(AudioRoutesEdit.ADD_IMAGE250, Constant.IMG_NAME[0]);
		home.uploadFile(AudioRoutesEdit.ADD_IMAGE500, Constant.IMG_NAME[1]);
		home.uploadFile(AudioRoutesEdit.ADD_IMAGE1000, Constant.IMG_NAME[2]);
		/*
		 * A lightbox style popup with the picture showing in full size is displayed
		 */
		//250x250
		home.click(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250);
		//wait for ligtbox display
		home.waitForElementClickable(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE, "src").contains(AudioRoutesEdit.IMG_NAME[0].replaceAll(".jpg", "")));
		
		//Close the lightbox fot 250x250
		home.click(AudioRoutesEdit.LIGHTBOX_CLOSE);
		Thread.sleep(3000);

		//Verify lightbox image is displayed for 500x500	
		home.click(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_THUMBNAIL_500);
		//wait for ligtbox display
		home.waitForElementClickable(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE, "src").contains(AudioRoutesEdit.IMG_NAME[1].replaceAll(".jpg", "")));
		
		//Close the lightbox fot 500x500
		home.click(AudioRoutesEdit.LIGHTBOX_CLOSE);
		Thread.sleep(3000);
		
		//Verify lightbox image is displayed for 1000x1000
		home.click(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000);
		//wait for ligtbox display
		home.waitForElementClickable(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE, "src").contains(AudioRoutesEdit.IMG_NAME[2].replaceAll(".jpg", "")));
		home.click(AudioRoutesEdit.LIGHTBOX_CLOSE);
		
		//Cancel saving
		home.click(AudioRoutesEdit.CANCEL);
	}
	
	/*
	 * Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on file name.
	 */
	@Test
	public void TC620DAE_14() throws InterruptedException{
		result.addLog("ID : TC620DAE_14 : Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on file name." );
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Upload image for each size of Primary Catalog Image successfully
			6. Click on file name of each size
			VP: A lightbox style popup with the picture showing in full size is displayed
		*/
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.STANDARD_ROUTES[0]);
		// 5. Click Edit link
		home.click(AudioRoutesInfo.EDIT);
		// Delete all image if exist
		home.deleteAllUploadedImage(AudioRoutesEdit.IMAGE_CATALOG);
		// 6. Upload image for each size of Primary Catalog Image successfully
		home.uploadFile(AudioRoutesEdit.ADD_IMAGE250, Constant.IMG_NAME[0]);
		home.uploadFile(AudioRoutesEdit.ADD_IMAGE500, Constant.IMG_NAME[1]);
		home.uploadFile(AudioRoutesEdit.ADD_IMAGE1000, Constant.IMG_NAME[2]);
		/*
		 * A lightbox style popup with the picture showing in full size is displayed
		 */
		//250x250
		home.click(AudioRoutesEdit.CATALOG_IMAGE_TITLE_LINK_250);
		//wait for ligtbox display
		home.waitForElementClickable(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE, "src").contains(AudioRoutesEdit.IMG_NAME[0].replaceAll(".jpg", "")));
		
		//Close the lightbox fot 250x250
		home.click(AudioRoutesEdit.LIGHTBOX_CLOSE);
		Thread.sleep(3000);

		//Verify lightbox image is displayed for 500x500	
		home.click(AudioRoutesEdit.CATALOG_IMAGE_TITLE_LINK_500);
		//wait for ligtbox display
		home.waitForElementClickable(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE, "src").contains(AudioRoutesEdit.IMG_NAME[1].replaceAll(".jpg", "")));
		
		//Close the lightbox fot 500x500
		home.click(AudioRoutesEdit.LIGHTBOX_CLOSE);
		Thread.sleep(3000);
		
		//Verify lightbox image is displayed for 1000x1000
		home.click(AudioRoutesEdit.CATALOG_IMAGE_TITLE_LINK_1000);
		//wait for ligtbox display
		home.waitForElementClickable(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE, "src").contains(AudioRoutesEdit.IMG_NAME[2].replaceAll(".jpg", "")));
		home.click(AudioRoutesEdit.LIGHTBOX_CLOSE);
		
		//Cancel saving
		home.click(AudioRoutesEdit.CANCEL);
	}
	
	/*
	 * Verify that three size of Primary Catalog Image could be replaced by another successfully
	 */
	@Test
	public void TC620DAE_15(){
		result.addLog("ID : TC620DAE_14 : Verify that three size of Primary Catalog Image could be replaced by another successfully");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Click "Edit" link
			6. Upload image for each size of Primary Catalog Image successfully
			7. Delete the uploaded image by clicking on trashcan icon
			8. Upload image again
		*/
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.STANDARD_ROUTES[0]);
		// 5. Click Edit link
		home.click(AudioRoutesInfo.EDIT);
		// 6. Upload image for each size of Primary Catalog Image successfully
		// 7. Delete the uploaded image by clicking on trashcan icon
		home.deleteAllUploadedImage(AudioRoutesEdit.IMAGE_CATALOG);
		// 8. Upload image again
		home.uploadFile(AudioRoutesEdit.ADD_IMAGE250, Constant.IMG_NAME[0]);
		home.uploadFile(AudioRoutesEdit.ADD_IMAGE500, Constant.IMG_NAME[1]);
		home.uploadFile(AudioRoutesEdit.ADD_IMAGE1000, Constant.IMG_NAME[2]);
		/*
		 * Verify that new image is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AudioRoutesEdit.UPLOAD_FILE_MESSSAGE[0]));
		Assert.assertTrue(home.getTextByXpath(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AudioRoutesEdit.UPLOAD_FILE_MESSSAGE[0]));
		Assert.assertTrue(home.getTextByXpath(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AudioRoutesEdit.UPLOAD_FILE_MESSSAGE[0]));
	}
	
	/*
	 * Verify that the Input Specifications section includes "Connection Type" and "Support Input Channels" module
	 */
	@Test
	public void TC620DAE_16(){
		result.addLog("ID : TC620DAE_16 : Verify that the Input Specifications section inludes 'Connection Type' and 'Support Input Channels' module");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Click Edit link
		*/
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.click(AudioRoutes.LINK_STANDARD_AUDIO);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(home.existsElement(AudioRoutesInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click Edit link
		home.click(AudioRoutesInfo.EDIT);
		/*
		 * VP: The Input Specifications section includes "Connection Type" and "Support Input Channels" module		
		 */
		ArrayList<String> list = home.getInputSpecificationHeader(AudioRoutesEdit.INPUT_SPECIFICATION);
		Assert.assertTrue(list.contains("Connection Type") && list.contains("Content Channel Capability"));
	}
	
	/*
	 * Verify that at least one connection type is required when editing an audio route
	 */
	@Test
	public void TC620DAE_17(){
		result.addLog("ID : TC620DAE_17 : Verify that at least one connection type is required when editing an audio route");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Click Edit link
			6. Uncheck all connection type.
			7. Click save link
		*/
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.click(AudioRoutes.LINK_STANDARD_AUDIO);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(home.existsElement(AudioRoutesInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click Edit link
		home.click(AudioRoutesInfo.EDIT);
		// 6. Uncheck all connection type
		home.uncheckACheckbox(AudioRoutesEdit.WIRED_CHECKBOX);
		home.uncheckACheckbox(AudioRoutesEdit.BLUETOOTH_CHECKBOX);
		// 7. Click save link
		home.click(AudioRoutesEdit.SAVE);
		/*
		 * Verify that An error message displays which notifying user to select at least one connection type
		 */
		String message = "Connection Type is required";
		Assert.assertTrue(home.checkMessageDisplay(message));
	}
	
	/*
	 * Verify that the Supported Input Channels combobox is enabled when the Connection Type is checked
	 */
	@Test
	public void TC620DAE_18(){
		result.addLog("ID : TC620DAE_18 : Verify that the Supported Input Channels combobox is enabled when the Connection Type is checked");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Click Edit link
			6. check a connection type.
		*/
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.click(AudioRoutes.LINK_STANDARD_AUDIO);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(home.existsElement(AudioRoutesInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click Edit link
		home.click(AudioRoutesInfo.EDIT);
		// 6. check a connection type
		home.selectACheckbox(AudioRoutesEdit.WIRED_CHECKBOX);
		/*
		 * Verify that the similar Supported Input Channels is enabled
		 */
		Assert.assertTrue(home.isElementEditable(AudioRoutesEdit.WIRED_DROPBOX));
	}
	
	/*
	 * Verify that the Supported Input Channels combobox is disabled when the Connection Type is unchecked
	 */
	@Test
	public void TC620DAE_19(){
		result.addLog("ID : TC620DAE_19 : Verify that the Supported Input Channels combobox is disabled when the Connection Type is unchecked");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Click Edit link
			6. Uncheck a Connection Type
		*/
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.click(AudioRoutes.LINK_STANDARD_AUDIO);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(home.existsElement(AudioRoutesInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click Edit link
		home.click(AudioRoutesInfo.EDIT);	
		// 6. Uncheck a Connection Type
		home.uncheckACheckbox(AudioRoutesEdit.WIRED_CHECKBOX);
		/*
		 * Verify that the similar Supported Input Channels is disabled
		 */
		Assert.assertFalse(home.isElementEditable(AudioRoutesEdit.WIRED_DROPBOX));
	}
	
	/*
	 * Verify that the supported input channels combobox support multi-selecting items
	 */
	@Test
	public void TC620DAE_20(){
		result.addLog("ID : TC620DAE_20 : Verify that the supported input channels combobox support multi-selecting items");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Click Edit link
			6. Select items in supported input channels combobox
		*/
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.STANDARD_ROUTES[0]);
		// 5. Click Edit link
		home.click(AudioRoutesInfo.EDIT);
		// 6. Select items in supported input channels combobox
		home.selectInputChannel(AudioRoutesEdit.WIRED_CONTAINER, AudioRoutesEdit.CONTENT_CHANNEL_CAPABILITY[0]);
		/*
		 * Verify that The supported input channels combobox support multi-selecting items
		 */
		Assert.assertTrue(home.getTextByXpath(AudioRoutesEdit.WIRED_DROPBOX).contains(AudioRoutesEdit.CONTENT_CHANNEL_CAPABILITY[1] + ", " + AudioRoutesEdit.CONTENT_CHANNEL_CAPABILITY[2]));
	}
	
	/*
	 * Verify that the Display Name section displays the audio route name with appropriate languages
	 */
	@Test
	public void TC620DAE_21(){
		result.addLog("ID : TC620DAE_21 : Verify that the Display Name section displays the audio route name with appropriate languages");
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
	 * Verify that the tuning file could be downloaded when clicking on tuning link.
	 */
	@Test
	public void TC620DAE_22(){
		result.addLog("ID : TC620DAE_22 : Verify that the tuning file could be downloaded when clicking on tuning link." );
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Click Edit link
			6. Upload a tuning file successfully
			7. Click on above tuning link after uploaded
			8. Try to download the tuning file
		 */
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.STANDARD_ROUTES[0]);
		// 5. Click Edit link
		home.click(AudioRoutesInfo.EDIT);
		// 6. Upload a tuning file successfully
		// 7. Click on above tuning link after uploaded
		// 8. Try to download the tuning file
		Boolean isDownloaded = home.downloadFile(Constant.AUDIO_ROUTE_FILE);
		/*
		 * The tuning file could be download successfully
		 */
		Assert.assertTrue(isDownloaded);
	}
	
	/*
	 * Verify that the delete confirmation dialog is displayed when clicking on Trashcan icon
	 */
	@Test
	public void TC620DAE_23(){
		result.addLog("ID : TC620DAE_23 : Verify that the delete confirmation dialog is displayed when clicking on Trashcan icon" );
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Click "Edit" link
			6. Upload a tuning file successfully
			7. Click on Trashcan icon of tuning section
		 */
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.STANDARD_ROUTES[0]);
		// 5. Click "Edit" link
		home.click(AudioRoutesInfo.EDIT);
		// 6. Upload a tuning file successfully
		// 7. Click on Trashcan icon of tuning section
		home.click(AudioRoutesEdit.DELETE_TUNING_ICON);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		/*
		 * The delete confirmation dialog is displayed which contains "Delete" and "Canecel" button
		 */
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_CANCEL), "Cancel");
	}
	
	/*
	 * Verify that the tuning file could be deleted successfully when clicking "Delete" button in delete confirmation dialog
	 */
	@Test
	public void TC620DAE_24(){
		result.addLog("ID : TC620DAE_24 : Verify that the tuning file could be deleted successfully when clicking 'Delete' button in delete confirmation dialog");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Click "Edit" link
			6. Upload a tuning file successfully
			7. Click on Trashcan icon of tuning secion
			VP: Verify that the delete confirmation dialog is displayed
			8. Click "Delete" button on delete confirmation dialog
		 */
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.STANDARD_ROUTES[0]);
		// 5. Click "Edit" link
		home.click(AudioRoutesInfo.EDIT);
		// 6. Upload a tuning file successfully
		// 7. Click on Trashcan icon of tuning section
		home.click(AudioRoutesEdit.DELETE_TUNING_ICON);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		/*
		 * VP: Verify that the delete confirmation dialog is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_CANCEL), "Cancel");
		// 8. Click "Delete" button on delete confirmation dialog
		home.selectConfirmationDialogOption("Delete");
		/*
		 * The tuning file is deleted successfully
		 */
		Assert.assertTrue(home.isElementPresent(AudioRoutesEdit.ADD_TUNING));
	}
	
	/*
	 * Verify that the tuning file is not deleted when clicking "Cancel" button in delete confirmation dialog
	 */
	@Test
	public void TC620DAE_25(){
		result.addLog("ID : TC620DAE_25 : Verify that the tuning file is not deleted when clicking 'Cancel' button in delete confirmation dialog");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Click "Edit" link
			6. Upload a tuning file successfully
			7. Click on Trashcan icon of tuning secion
			VP: Verify that the delete confirmation dialog is displayed
			8. Click "Cancel" button on delete confirmation dialog
		 */
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.STANDARD_ROUTES[0]);
		// 5. Click "Edit" link
		home.click(AudioRoutesInfo.EDIT);
		// 6. Upload a tuning file successfully
		// 7. Click on Trashcan icon of tuning section
		home.click(AudioRoutesEdit.DELETE_TUNING_ICON);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		/*
		 * VP: Verify that the delete confirmation dialog is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_CANCEL), "Cancel");
		// 8. Click "Cancel" button on delete confirmation dialog
		home.selectConfirmationDialogOption("Cancel");
		/*
		 * The tuning file still remains
		 */
		Assert.assertTrue(home.isElementPresent(AudioRoutesEdit.UPLOADED_AUDIO_ROUTE));
	}
		
	/*
	 * Verify that the audio route info could be changed successfully
	 */
	@Test
	public void TC620DAE_26(){
		result.addLog("ID : TC620DAE_26 : Verify that the audio route info could be changed successfully");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Click "Edit Version" link
			6. Change the Display Name of audio route
			7. Click "Save" link
		 */
		// 3. Navigate to "Audio Routes" page
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.click(AudioRoutes.LINK_STANDARD_AUDIO);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(home.existsElement(AudioRoutesInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click Edit link
		home.click(AudioRoutesInfo.EDIT);
		// 6. Change the Display Name of audio route
		String newName = "New" + RandomStringUtils.randomNumeric(5);
		String currentName = home.getAtribute(AudioRoutesEdit.DISPLAY_NAME, "value");
		home.editData(AudioRoutesEdit.DISPLAY_NAME, newName);
		// 7. Click "Save" link
		home.click(AudioRoutesEdit.SAVE);
		/*
		 * Verify that The portal is redirected to 615D Audio Route Detail page and the new audio route display name is displayed
		 */
		Assert.assertEquals(home.existsElement(AudioRoutesInfo.getElementsInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), newName);
		// Change audio route name back to standard name
		home.click(AudioRoutesInfo.EDIT);
		home.editData(AudioRoutesEdit.DISPLAY_NAME, currentName);
		home.click(AudioRoutesEdit.SAVE);
	}
	
	/*
	 Verify that the new change of audio route is not saved when clicking "Cancel" link of "Actions" module.
	 */
	@Test
	public void TC620DAE_27(){
		result.addLog("ID : TC620DAE_27 : Verify that the new change of audio route is not saved when clicking 'Cancel' link of 'Actions' module." );
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Upload a tuning file successfully
			6. Click "Cancel" link of "Actions" module
		 */
		
		// Pre-Conditions :
		home.click(Xpath.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		home.click(AudioRoutes.LINK_STANDARD_AUDIO);
		// Click on Edit Version link
		home.click(AudioRoutes.AUDIO_DETAILS_EDIT_VERSION);
		// 5. Upload a tuning file successfully
		if (home.isElementPresent(AudioRoutes.EDIT_DELETE_TUNNING)){
			// Delete Tuning file
			home.doDelete(AudioRoutes.EDIT_DELETE_TUNNING);
		}	
		// 5.Upload the tuning file
		home.uploadFile(AudioRoutes.EDIT_UPLOAD_TUNNING, AudioRoutes.AUDIO_DETAILS_TUNNING_FILE);
		// cancel
		home.click(AudioRoutes.EDIT_AUDIO_CANCEL);
		Assert.assertFalse(home.isElementPresent(AudioRoutes.EDIT_AUDIO_CANCEL));
	}
}
