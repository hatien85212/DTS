package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AudioRoutes;
import com.dts.adminportal.model.AudioRoutesEdit;
import com.dts.adminportal.model.AudioRoutesInfo;
import com.dts.adminportal.model.LanguagePackage;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.security.util.APIData;
import com.dts.adminportal.security.util.APIUtil;
import com.dts.adminportal.util.ListUtil;

// TC615DAD_02 : Failed due to PDFF220 catalog image

public class DTSAudioRoutes620DAudioRouteEdit extends BasePage{

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
	}
	
	/*
	 * Verify that the invalid tuning file could not be uploaded
	 */
	@Test
	public void TC620DAE_01() {
		audioControl.addLog("ID : TC620DAE_01 : Verify that the invalid tuning file could not be uploaded");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Click "Edit" link
			6. Upload an invalid tuning file
			  VP: An error message is displayed and the invalid tuning file is not added.

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.linkAudioroutes);
		// 4. Click on an audio routes link
		audioControl.click(AudioRoutes.LINK_STANDARD_AUDIO);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		// 5. Click on Edit Version link
		audioControl.editVersion();
		// 6. Upload an invalid tuning file
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName());
		/*
		 * VP: An error message is displayed and the invalid tuning file is not added.
		 */
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.AUDIOROUTE_UPLOAD_MESSAGE).contains(AudioRoutesEdit.Upload_File_Message.Invalid_file.getName()));
		
		
	}
	
	/*
	 * Verify that the "Type" and "Audio Route ID" section are displayed as read only
	 */
	@Test
	public void TC620DAE_02() {
		audioControl.addLog("ID : TC620DAE_02 : Verify that the 'Type' and 'Audio Route ID' section are displayed as read only");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Click "Edit" link
			6. Try to edit "Type" and "Audio Route ID" section
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.linkAudioroutes);
		// 4. Click on an audio routes link
		audioControl.click(AudioRoutes.LINK_STANDARD_AUDIO);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		// 5. Click on Edit Version link
		audioControl.editVersion();
		// 6. Try to edit "Type" and "Audio Route ID" section
		/*
		 * VP: The "Type" and "Audio Route ID" section are displayed as readonly
		 */
		Assert.assertFalse(audioControl.canEdit(AudioRoutesEdit.TYPE));
		Assert.assertFalse(audioControl.canEdit(AudioRoutesEdit.AUDIO_ROUTE_ID));
	}
	
	/*
	 * Verify that the Display Name language dropdown contains 10 items properly
	 */
	@Test
	public void TC620DAE_04() {
		audioControl.addLog("ID : TC620DAE_04 : Verify that the Display Name language dropdown contains 10 items properly");	
		/* 
		 1. Navigate to DTS portal
		 2. Log into DTS portal as a DTS user successfully
		 3. Navigate to "Audio Routes" page
		 4. Select an audio route item
		 VP: The 615D Audio Route Detail page is displayed
		 5. Click "Edit" link
		 VP: The Display Name language dropdown contains items: - Select -, Chinese - Simplified, Chinese - Traditional, French, German, Italian, Japanese, Korean, Russian and Spanish.
		 6. Select another language for Display Name
		"VP: The Display Name language text field is enabled and the hint text ""Inherits default"" is displayed in Display Name text field.
		 VP: Another language dropdown is displayed"
		 7. Select the same item for second language dropdown
		 VP: A warning message is displayed which notifying for the duplicating of languages.
		 8. Set the first language dropdown back to "Select"
		 VP: The Display Name language text field is disabled.
		 9. Type value into model name input field
		 10. Click on Trashcan icon
		 VP: The language dropdown is restore to default "Select" value and the input field is cleared 
		 */
		  loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		  // 3. Navigate to "Audio Routes" page
		  audioControl.click(PageHome.linkAudioroutes);
		  // 4. Select an audio route item
		  audioControl.click(AudioRoutes.LINK_OTHER_AUDIO);
		  // VP: The 615D Audio Route Detail page is displayed
		  Assert.assertEquals(audioControl.existsElement(AudioRoutesInfo.getElementsInfo()),true);
		  // Delete all languages before
		  audioControl.editVersion();
		  List<LanguagePackage> before = audioControl.getLanguagePackage(AudioRoutesEdit.LANGUAGE_CONTAINER);
		  audioControl.deleteAllLang(before);
		  before.clear();
		  audioControl.click(AudioRoutesEdit.SAVE);
		  // 5. Click "Edit" link
		  audioControl.editVersion();
		  before = audioControl.getLanguagePackage(AudioRoutesEdit.LANGUAGE_CONTAINER);
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
		  ArrayList<String> languages = audioControl.getOptionList(AudioRoutesEdit.LANGUAGE_COMBOBOX);
		  Assert.assertTrue(ListUtil.containsAll(languages, AddEditProductModel.ProductLanguage.getNames()));
		  // 6. Select another language for Display Name
		  audioControl.selectOptionByName(AudioRoutes.getLang_selectxpath(before.size()),AddEditProductModel.ProductLanguage.ITALIAN.getName());
		  /*
		    VP: The Display Name language text field is enabled and the hint text
		    "Inherits default" is displayed in Display Name text field.
		   */
		  Assert.assertEquals(audioControl.getAtributeValue(AudioRoutesEdit.LANGUAGE_INPUT, "placeholder"),"inherits default");
		  //audioControl.selectOptionByName(AudioRoutes.EDIT_LANGUAGE_SELECT, AddEditProductModel.ProductLanguage.ITALIAN.getName());
		  List<LanguagePackage> after = audioControl.getLanguagePackage(AudioRoutesEdit.LANGUAGE_CONTAINER);
		  /*
		   * VP: Another language dropdown is displayed
		   */
		  Assert.assertTrue(after.size() == before.size() + 1);
		  List<LanguagePackage> languagepackage = audioControl.getLanguagePackage(AudioRoutesEdit.LANGUAGE_CONTAINER);
		  // 7.Select the language at the next dropbox as same as the first one
		  audioControl.selectOptionByName(languagepackage.get(languagepackage.size()-1).languagedropbox,AddEditProductModel.ProductLanguage.ITALIAN.getName());
		  // Get the language package
		  languagepackage.clear();
		  languagepackage = audioControl.getLanguagePackage(AudioRoutesEdit.LANGUAGE_CONTAINER);
		  //  Get text of the  warning message is displayed at the first package
		  String warning = languagepackage.get(0).warningmessage;
		  System.out.println(warning);
		  /*
		   Verify that a warning message is displayed for duplicating language dropdowns
		   */
		  Assert.assertTrue(warning.contains("Language is duplicated"));
		  // 8. Set language dropdown back to "Select"
		  audioControl.selectOptionByName(AudioRoutesEdit.LANGUAGE_COMBOBOX,AddEditProductModel.ProductLanguage.SELECT.getName());
		    /*
		     * VP The Display Name language text field is disabled.
		     */
		  Assert.assertEquals(audioControl.getAtributeValue(AddEditProductModel.EMPTY_LANGUAGE_FIELD, "disabled"),"true");
		  //9. Type value into model name input field
		  audioControl.selectOptionByName(AudioRoutesEdit.LANGUAGE_COMBOBOX,AddEditProductModel.ProductLanguage.ITALIAN.getName());
		  audioControl.editData(AudioRoutesEdit.LANGUAGE_INPUT, RandomStringUtils.randomAlphabetic(10));
		  // 10. Click on Trashcan icon
		  audioControl.click(AudioRoutesEdit.LANGUAGE_TRASH);
		  /*
		  * The language dropdown is restore to default "Select" value and the input field is cleared 
		  */
		  Assert.assertEquals(audioControl.getItemSelected(AudioRoutesEdit.LANGUAGE_COMBOBOX), AddEditProductModel.ProductLanguage.SELECT.getName());
		   }
	
	/*
	 * Verify that Primary Catalog Image is validated after uploading.
	 */
	@Test
	public void TC620DAE_11() throws InterruptedException{
		audioControl.addLog("ID : TC620DAE_11 : Verify that Primary Catalog Image is validated after uploading." );
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Click Edit link
			6. Upload image for each size of Primary Catalog Image successfully
			"VP: The correct image thumbnail is displayed next to image name. 
			VP: The uploaded date and file size are displayed below the image name for each size of Primary Catalog Image."
			7. Click on image thumbnail/file name of each size
			VP: A lightbox style popup with the picture showing in full size is displayed
			8. Delete the uploaded image by clicking on trashcan icon
			9. Upload image again
			VP: Verify that new image is uploaded successfully
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.linkAudioroutes);
		// 4. Select an audio route item
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		// 5. Click Edit link
		audioControl.editVersion();
		// Delete all image if exist
		audioControl.deleteAllUploadedImage(AudioRoutesEdit.IMAGE_CATALOG);
		// 6. Upload image for each size of Primary Catalog Image successfully
		audioControl.uploadFile(AudioRoutesEdit.ADD_IMAGE250, AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		audioControl.uploadFile(AudioRoutesEdit.ADD_IMAGE500, AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		audioControl.uploadFile(AudioRoutesEdit.ADD_IMAGE1000, AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		/*
		 * Verify that The correct image thumbnail is displayed next to image name
		 */
		Assert.assertTrue(audioControl.isElementDisplayHorizontal(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250, AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_INFO_250));
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_INFO_250).contains(AddEditProductModel.FileUpload.IMG_250_JPG.getName()));
		Assert.assertTrue(audioControl.isElementDisplayHorizontal(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_THUMBNAIL_500, AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_INFO_500));
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_INFO_500).contains(AddEditProductModel.FileUpload.IMG_500_JPG.getName()));
		Assert.assertTrue(audioControl.isElementDisplayHorizontal(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000, AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_INFO_1000));
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_INFO_1000).contains(AddEditProductModel.FileUpload.IMG_1000_JPG.getName()));
		/*
		 * VP: The uploaded date and file size are displayed below the image name for each size of Primary Catalog Image.
		 */
		Assert.assertTrue(audioControl.isElementDisplayVertically(AudioRoutesEdit.CATALOG_IMAGE_TITLE_LINK_250, AudioRoutesEdit.CATALOG_IMAGE_FILE_SIZE_250));
		Assert.assertTrue(audioControl.isElementDisplayVertically(AudioRoutesEdit.CATALOG_IMAGE_TITLE_LINK_500, AudioRoutesEdit.CATALOG_IMAGE_FILE_SIZE_500));
		Assert.assertTrue(audioControl.isElementDisplayVertically(AudioRoutesEdit.CATALOG_IMAGE_TITLE_LINK_500, AudioRoutesEdit.CATALOG_IMAGE_FILE_SIZE_500));
		//7. Click on image thumbnail/file name of each size
		/*
		 * VP: A lightbox style popup with the picture showing in full size is displayed
		 */
		//250x250
		audioControl.click(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250);
		//wait for ligtbox display
		audioControl.waitForElementClickable(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(audioControl.getAtributeValue(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE, "src").contains(AudioRoutesEdit.img_name.IMG250_JPG.getName().replaceAll(".jpg", "")));
		
		//Close the lightbox fot 250x250
		audioControl.click(AudioRoutesEdit.LIGHTBOX_CLOSE);
		Thread.sleep(3000);

		//Verify lightbox image is displayed for 500x500	
		audioControl.click(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_THUMBNAIL_500);
		//wait for ligtbox display
		audioControl.waitForElementClickable(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(audioControl.getAtributeValue(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE, "src").contains(AudioRoutesEdit.img_name.IMG500_JPG.getName().replaceAll(".jpg", "")));
		
		//Close the lightbox fot 500x500
		audioControl.click(AudioRoutesEdit.LIGHTBOX_CLOSE);
		Thread.sleep(3000);
		
		//Verify lightbox image is displayed for 1000x1000
		audioControl.click(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000);
		//wait for ligtbox display
		audioControl.waitForElementClickable(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(audioControl.getAtributeValue(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE, "src").contains(AudioRoutesEdit.img_name.IMG1000_JPG.getName().replaceAll(".jpg", "")));
		audioControl.click(AudioRoutesEdit.LIGHTBOX_CLOSE);
		//8. Delete the uploaded image by clicking on trashcan icon
		audioControl.deleteAllUploadedImage(AudioRoutesEdit.IMAGE_CATALOG);
		//9. Upload image again
		audioControl.uploadFile(AudioRoutesEdit.ADD_IMAGE250, AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		audioControl.uploadFile(AudioRoutesEdit.ADD_IMAGE500, AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		audioControl.uploadFile(AudioRoutesEdit.ADD_IMAGE1000, AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		/*
		 * Verify that new image is uploaded successfully
		 */
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AudioRoutesEdit.Upload_File_Message.File_upload_successful.getName()));
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AudioRoutesEdit.Upload_File_Message.File_upload_successful.getName()));
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AudioRoutesEdit.Upload_File_Message.File_upload_successful.getName()));
	}
	
	/*
	 * Verify that the connection type for the standard accessories are disabled.
	 */
	@Test
	public void TC620DAE_16(){
		audioControl.addLog("ID : TC620DAE_16 : Verify that at least one connection type is required when editing an audio route.");
		/*
		 	1. Navigate to DTS portal	
			2. Log into DTS portal as a DTS user successfully	
			3. Navigate to "Audio Routes" page	
			4. Select a standard accessory audio route item	
			5. Click "Edit Version" link	
			VP: The connection type for standard accessory is disabled but the Support Input Channels of both Wired and Bluetooth section are still enabled.
			6. Upload a tuinng file which contains lineout0 / bluetooth0 route type only.	
			VP: The error message 'Invalid tuning file: Invalid file contents: Route, it must appropriate the Audio Route' is displayed after uploading
			7. Click Retry link	
			8. Upload another tuning file which contains both lineout0 and bluetooth0 route type	
			VP: The uploaded tuning file is uploaded successfully
			9. Click 'Save' link	
			10. Publish this standard audio route successfully	
			11. Send a product api request like:
			http://devws.dts.com/dws/api/resource/products/<audio UUID>/details?accessToken=874495320a0436beae4b130e1a3adbe3e44e7f5c4fcf69d9b420f83e9416e2bc	
			VP: The response returned with 200OK and the productAudioRoute contains Line-Out and Bluetooth value


		*/
		//2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.linkAudioroutes);
		//4. Select a standard accessory audio route item	
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.External_Speakers.getName()); // External Speaker
		String audioUUID = audioControl.getTextByXpath(AudioRoutesInfo.UUID);
		//5. Click "Edit" link
		audioControl.editVersion();
		/*
		 * VP: The connection type for standard accessory is disabled
		 * but the Support Input Channels of both Wired and Bluetooth section are still enabled.
		 */
		WebElement wired_checkbox = driver.findElement(By.xpath(AudioRoutesEdit.WIRED_CHECKBOX));
		WebElement blue_checkbox = driver.findElement(By.xpath(AudioRoutesEdit.BLUETOOTH_CHECKBOX));
		WebElement wired_channel = driver.findElement(By.xpath(AudioRoutesEdit.WIRED_DROPBOX));
		WebElement blue_channel = driver.findElement(By.xpath(AudioRoutesEdit.BLUETOOTH_DROPBOX));
		Assert.assertTrue(wired_channel.isEnabled());
		Assert.assertTrue(blue_channel.isEnabled());
		Assert.assertFalse(wired_checkbox.isEnabled());
		Assert.assertFalse(blue_checkbox.isEnabled());
		//6. Upload a tuinng file which contains lineout0 or bluetooth0 route type only.
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING,AddEditProductModel.FileUpload.External_Speakers_Bluetooth.getName());
		/*
		 * VP: The error message 'Invalid tuning file: Invalid file contents: Route, it must appropriate the Audio Route' is displayed after uploading
		 */
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.INVALID_MESSAGE).contains(AudioRoutesEdit.Upload_File_Message.Invalid_file_contents.getName()));
		//7. Click Retry link
		//8. Upload another tuning file which contains both lineout0 and bluetooth0 route type	
		audioControl.uploadFileTuning(AudioRoutesEdit.RETRY_UPLOAD_AUDIOROUTE,AddEditProductModel.FileUpload.External_Speakers_Combined.getName());
		/*
		 * VP: The uploaded tuning file is uploaded successfully
		 */
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesEdit.UPLOADED_AUDIO_ROUTE));
		//9. Click 'Save' link
		audioControl.click(AudioRoutesEdit.SAVE);
		//10. Publish this standard audio route successfully
		audioControl.publishAudioRoute("");
//		audioControl.click(AudioRoutesInfo.PUBLISH);
		//11. Send a product api request like:
		JSONObject requestResult = APIUtil.sendGetWS(APIData.GET_PRODUCT_DETAIL_URL(audioUUID));
		/*
		 * http://devws.dts.com/dws/api/resource/products/<audio UUID>/details?accessToken=874495320a0436beae4b130e1a3adbe3e44e7f5c4fcf69d9b420f83e9416e2bc	
		 */
		/*
		 * VP: The response returned with 200OK and the productAudioRoute contains Line-Out and Bluetooth value
		 */
		Assert.assertTrue(requestResult.toString().contains("\"productAudioRoute\":\"Line-Out,Bluetooth\""));
	}
	/*
	 * Verify that the Display Name section displays the audio route name with appropriate languages
	 */
	@Test
	public void TC620DAE_21(){
		audioControl.addLog("ID : TC620DAE_21 : Verify that the Display Name section displays the audio route name with appropriate languages");
		audioControl.addErrorLog("PDPP-1421: Display name of external languages don't change after edited");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			5. Click "Edit Version" link
			6. Set the Audio Route's display name for all languages
			7. Click "Save" link
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.linkAudioroutes);
		// 4. Select an audio route item
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		// Get default display name
		String defaultName = audioControl.getTextByXpath(AudioRoutesInfo.DEFAULT_DISPLAY_NAME);
		// 5. Click "Edit Version" link
		audioControl.editVersion();
		// 6. Set the Audio Route's display name for all languages
		ArrayList<String> allLanguages = audioControl.addAllLanguage(AudioRoutesEdit.LANGUAGE_CONTAINER);
		allLanguages.add(0, defaultName);
		// 7. Click "Save" link
		audioControl.click(AudioRoutesEdit.SAVE);
		// Get all languages name display
		ArrayList<String> displayedLanguages = audioControl.getAudioRouteName(AudioRoutesInfo.ALL_DISPLAY_NAME);
		/*
		 * Verify that The 615D Audio Route Detail page is displayed and the Display Name section is displayed with all languages
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		Assert.assertTrue(ListUtil.containsAll(allLanguages, displayedLanguages));
	}
	
	/*
	 * Verify that the tuning file could be downloaded when clicking on tuning link.
	 */
	@Test
	public void TC620DAE_22(){
		audioControl.addLog("ID : TC620DAE_22 : Verify that the tuning file could be downloaded when clicking on tuning link." );
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.linkAudioroutes);
		// 4. Select an audio route item
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName()); // Over-Ear Headphones
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL).contains(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName()));
		// 5. Click Edit link
		audioControl.editVersion();
		// 6. Upload a tuning file successfully
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING,AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined.getName());
		// 7. Click on above tuning link after uploaded
		// 8. Try to download the tuning file
		Boolean isDownloaded = audioControl.downloadFile(AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined.getName());
		/*
		 * The tuning file could be download successfully
		 */
		Assert.assertTrue(isDownloaded);
	}
	
	/*
	 * Verify that the tuning file could be deleted successfully when clicking "Delete" button in delete confirmation dialog
	 */
	@Test
	public void TC620DAE_24(){
		audioControl.addLog("ID : TC620DAE_24 : Verify that the tuning file could be deleted successfully when clicking 'Delete' button in delete confirmation dialog");
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
			VP: The tuning file still remains.
			9. Click "Delete" button on delete confirmation dialog
			VP:The tuning file is deleted successfully

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.linkAudioroutes);
		// 4. Select an audio route item
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		// 5. Click "Edit" link
		audioControl.editVersion();
		// 6. Upload a tuning file successfully
		// 7. Click on Trashcan icon of tuning section
		
		if(audioControl.isElementPresent(AudioRoutesEdit.ADD_TUNING)){
			audioControl.uploadTuningAudioRoute(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined.getName());
		}
		audioControl.click(AudioRoutesEdit.DELETE_TUNING_ICON);
		audioControl.waitForElementClickable(PageHome.BTN_CONFIRMATION_DANGER);
		/*
		 * VP: Verify that the delete confirmation dialog is displayed
		 */
		Assert.assertEquals(audioControl.getTextByXpath(PageHome.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(audioControl.getTextByXpath(PageHome.BTN_CONFIRMATION_CANCEL), "Cancel");
		// 8. Click "Cancel" button on delete confirmation dialog
		audioControl.selectConfirmationDialogOption("Cancel");
		/*
		* The tuning file still remains
		*/
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesEdit.UPLOADED_AUDIO_ROUTE));
		// 9. Click "Delete" button on delete confirmation dialog
		//Click on Trashcan icon of tuning section
		audioControl.click(AudioRoutesEdit.DELETE_TUNING_ICON);
		audioControl.waitForElementClickable(PageHome.BTN_CONFIRMATION_DANGER);
		audioControl.selectConfirmationDialogOption("Delete");
		/*
		 * The tuning file is deleted successfully
		 */
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesEdit.ADD_TUNING));
	}
	@Test
	public void TC620DAE_26(){
		audioControl.addLog("ID : TC620DAE_26 : Verify that the audio route info could be changed successfully");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.linkAudioroutes);
		// 4. Select an audio route item
		audioControl.click(AudioRoutes.LINK_STANDARD_AUDIO);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(AudioRoutesInfo.getElementsInfo()), true);
		// 5. Click Edit link
		audioControl.editVersion();
		// 6. Change the Display Name of audio route
		String newName = "New" + RandomStringUtils.randomNumeric(5);
		String currentName = audioControl.getAtributeValue(AudioRoutesEdit.DISPLAY_NAME, "value");
		audioControl.editData(AudioRoutesEdit.DISPLAY_NAME, newName);
		// 7. Click "Save" link
		audioControl.click(AudioRoutesEdit.SAVE);
		/*
		 * Verify that The portal is redirected to 615D Audio Route Detail page and the new audio route display name is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(AudioRoutesInfo.getElementsInfo()), true);
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), newName);
		// Change audio route name back to standard name
		audioControl.click(AudioRoutesInfo.EDIT);
		audioControl.editData(AudioRoutesEdit.DISPLAY_NAME, currentName);
		audioControl.click(AudioRoutesEdit.SAVE);
	}
	
	/*
	 Verify that the new change of audio route is not saved when clicking "Cancel" link of "Actions" module.
	 */
	@Test
	public void TC620DAE_27(){
		audioControl.addLog("ID : TC620DAE_27 : Verify that the new change of audio route is not saved when clicking 'Cancel' link of 'Actions' module." );
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			5. Upload a tuning file successfully
			6. Click "Cancel" link of "Actions" module
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Pre-Conditions :
		audioControl.click(PageHome.linkAudioroutes);
		// 4. Select an audio route item
		audioControl.click(AudioRoutes.LINK_STANDARD_AUDIO);
		// Click on Edit Version link
		audioControl.editVersion();
		// 5. Upload a tuning file successfully
		audioControl.editData(AudioRoutesEdit.DISPLAY_NAME,"New");
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING,AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined.getName());
		// cancel
		audioControl.click(AudioRoutesEdit.CANCEL);
		Assert.assertFalse(audioControl.isElementPresent(AudioRoutesEdit.CANCEL));
		Assert.assertFalse(audioControl.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL).equals("New"));
	}
	
	/*
	 * Verify that the valid route tuning file is uploaded successfully in Audio Route item when edit Audio Route
	 */
	@Test
	public void TC620DAE_28(){
		audioControl.addLog("ID : TC620DAE_28 : Verify that the invalid route tuning file is uploaded successfully in Audio Route item when edit Audio Route" );
		
		/*1. Log into DTS portal as DTS admin	
		2. Navigate to "Audio" page	
		3. Select any audio route in "Other Audio Routes" section	
		4. Click on " Update Product Info" link 	
		5. Click on "OK" button in pop-up	
		VP: The "Audio Route Edit" page is displayed
		6. Click on "Delete" icon in tuning section 	
		7. Click on "Delete" button in pop-up	
		VP:  The old tuning is deleted
		8. Click on " Select file" link	
		9. Upload the valid tuning DTSCS file 	
		VP: Verify that valid tuning DTSCS file is uploaded successfully
		*/
		
		//1. Log into DTS portal as DTS admin	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Audio" page	
		appDeviceControl.click(PageHome.linkAudioroutes);
		//3. Select any audio route in "Other Audio Routes" section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Other_Routes.Attached2_Internal_Speakers.getName()); //Attached2 - Internal Speakers (mode 1)
		//4. Click on " Update Product Info" link 	
		//5. Click on "OK" button in pop-up	
		audioControl.editVersion();
		/*
		 * VP: The "Audio Route Edit" page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(AudioRoutesEdit.getElementsInfo()),true);
		//6. Click on "Delete" icon in tuning section 	
		//7. Click on "Delete" button in pop-up	
		//8. Click on " Select file" link	
		//9. Upload the valid tuning DTSCS file 	
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING,AddEditProductModel.FileUpload.Attached2InternalSpeakersMusic_Fallback.getName());
		/*
		 * VP: Verify that valid tuning DTSCS file is uploaded successfully
		 */
		Assert.assertFalse(audioControl.isElementPresent(AudioRoutesEdit.ADD_TUNING));
	}
	
	/*
	 * Verify that the invalid route tuning file is uploaded unsuccessfully in Audio Route item when edit Audio Route 
	 */
	@Test
	public void TC620DAE_29(){
		audioControl.addLog("ID : TC620DAE_29 : Verify that the invalid route tuning file is uploaded unsuccessfully in Audio Route item when edit Audio Route " );
		
		/*1. Log into DTS portal as DTS admin	
		2. Navigate to "Audio" page	
		3. Select any audio route in "Other Audio Routes" section	
		4. Click on " Update Product Info" link 	
		5. Click on "OK" button in pop-up	
		VP: The "Audio Route Edit" page is displayed
		6. Click on "Delete" icon in tuning section 	
		7. Click on "Delete" button in pop-up	
		VP: The old tuning is deleted
		8. Click on " Select file" link	
		9. Upload the custom tuning DTSCS file that having route not match 	
		VP: A "Invalid route" message is displayed in "Custom Tuning" section
		*/
		
		//1. Log into DTS portal as DTS admin	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Audio" page	
		audioControl.click(PageHome.linkAudioroutes);
		//3. Select any audio route in "Other Audio Routes" section	
		audioControl.selectAnAudioRouteByName(AudioRoutes.Other_Routes.Attached5_Internal_Speakers.getName()); //Attached5 - Internal Speakers (mode 4)
		//4. Click on " Update Product Info" link 	
		//5. Click on "OK" button in pop-up	
		audioControl.editVersion();
		/*
		 * VP: The "Audio Route Edit" page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(AudioRoutesEdit.getElementsInfo()),true);
		//6. Click on "Delete" icon in tuning section 	
		//7. Click on "Delete" button in pop-up	
		//8. Click on " Select file" link	
		//9. Upload the custom tuning DTSCS file that having route not match 
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING,AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Fallback.getName());
		/*
		 * VP: A "! Invalid file" message is displayed in "Custom Tuning" section
		 */
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.AUDIOROUTE_UPLOAD_MESSAGE).contains(AudioRoutesEdit.Upload_File_Message.Invalid_file.getName()));

	}
	
	/*
	 * Verify that “Input Specification” secsion is displayed correctly in 620D Audio Edit page 
	 */
	@Test
	public void TC620DAE_30(){
		audioControl.addLog("ID : TC620DAE_30 : Verify that “Input Specification” secsion is displayed correctly in 620D Audio Edit page" );
		
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Audio" page
			3. Select a Standard Accessory audio route
			4. Click "Edit Version" link
			VP: Verify that "Connection Type" in "Input Specification” section include : “Wire”, “Bluetooth” and “USB” as read only
		
		*/
		
		// 1. Log into DTS portal as DTS admin	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Audio" page	
		audioControl.click(PageHome.linkAudioroutes);
		// 3. Select a Standard Accessory audio route
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		// 4. Click "Edit Version" link
		audioControl.editVersion();
		// VP: Verify that "Connection Type" in "Input Specification” section include : “Wire”, “Bluetooth” and “USB” as read only
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesEdit.WIRED_CONTAINER));
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesEdit.BLUETOOTH_CONTAINER));
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesEdit.USB_CONTAINER));
		Assert.assertFalse(audioControl.isElementEditable(AudioRoutesEdit.WIRED_CHECKBOX));
		Assert.assertFalse(audioControl.isElementEditable(AudioRoutesEdit.BLUETOOTH_CHECKBOX));
		Assert.assertFalse(audioControl.isElementEditable(AudioRoutesEdit.USB_CHECKBOX));
		
	}
	
	/*
	 * Verify that Audio Edit page validate for uploading with connection type include “Wired”, “Bluetooth” and “USB”
	 */
	@Test
	public void TC620DAE_31(){
		audioControl.addLog("ID : TC620DAE_31 : Verify that Audio Edit page validate for uploading with connection type include “Wired”, “Bluetooth” and “USB”" );
		
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Audio" page
			3. Select “Over Ear Headphones” route in Standard Accessory secsion
			4. Click "Edit Version" link
			5. Upload a dtscs file contains only Lineout and Bluetooth
			VP: The error message is shown.
			6. Upload a dtscs file contains only Lineout and USB
			VP: The error message is shown.
			7. Upload a dtscs file contains Lineout, Bluetooth and USB
			VP: Verify that a dtscs file is uploaded successfully
			8. Repeat from step 3 to step 7 with “Earbuds” route

		*/
		
		// 1. Log into DTS portal as DTS admin	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Audio" page	
		audioControl.click(PageHome.linkAudioroutes);
		// 3. Select a Standard Accessory audio route
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		// 4. Click "Edit Version" link
		audioControl.editVersion();
		// 5. Upload a dtscs file contains only Lineout and Bluetooth
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones_Lineout_Blue.getName());
		// VP: The error message is shown.
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.INVALID_MESSAGE).contains(AudioRoutesEdit.Upload_File_Message.Invalid_file_contents.getName()));
		// 6. Upload a dtscs file contains only Lineout and USB
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones_Lineout_USB.getName());
		// VP: The error message is shown.
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.INVALID_MESSAGE).contains(AudioRoutesEdit.Upload_File_Message.Invalid_file_contents.getName()));
		// 7. Upload a dtscs file contains Lineout, Bluetooth and USB
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined.getName());
		// VP: Verify that a dtscs file is uploaded successfully
		Assert.assertFalse(audioControl.isElementPresent(AudioRoutesEdit.ADD_TUNING));
		// 8. Repeat from step 3 to step 7 with “Earbuds” route
		audioControl.click(PageHome.linkAudioroutes);
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Earbuds.getName());
		audioControl.editVersion();
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Earbuds_Lineout_Blue.getName());
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.INVALID_MESSAGE).contains(AudioRoutesEdit.Upload_File_Message.Invalid_file_contents.getName()));
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Earbuds_Lineout_USB.getName());
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.INVALID_MESSAGE).contains(AudioRoutesEdit.Upload_File_Message.Invalid_file_contents.getName()));
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Earbuds_Combined.getName());
		Assert.assertFalse(audioControl.isElementPresent(AudioRoutesEdit.ADD_TUNING));
	}
	
	/*
	 * Verify that  "Audio Route ID" section displays correctly
	 */
	@Test
	public void TC620DAE_32(){
		audioControl.addLog("ID : TC620DAE_32 : Verify that  'Audio Route ID' section displays correctly");
		
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Audio" page
			3. Select a Standard Accessory audio route
			4. Click "Edit Version" link
			VP: Verify that “Audio Route ID” section displays "Line-out0, Bluetooth0, USB"

		*/
		
		// 1. Log into DTS portal as DTS admin	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Audio" page	
		audioControl.click(PageHome.linkAudioroutes);
		// 3. Select a Standard Accessory audio route
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.External_Speakers.getName());
		// 4. Click "Edit Version" link
		audioControl.editVersion();
		// VP: Verify that “Audio Route ID” section displays "Line-out0, Bluetooth0, USB"
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesEdit.AUDIO_ROUTE_ID), AudioRoutesEdit.AudiRouteID);
	}	
	/*
	 * Verify that the "Type" and "Audio Route ID" Section are displayed as expected
	 */
	@Test
	public void TC620DAE_33(){
		audioControl.addLog("ID : TC620DAE_33 : Verify that the Type and Audio Route ID Section are displayed as expected");
		
		/*
		 	1. Log into DTS portal as a DTS user
			2. Log into DTS portal as a DTS User successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item Attached6, Attached7, Attached8
			5. Click "Edit" link
			6. Check "Type" and "Audio Route ID" Section
			VP: The "Type" and "Audio Route ID" section Are displayed as expected
			7. Repeat test case with Attached 7 and Attached 8
			VP: The "Type" and "Audio Route ID" section Are displayed as expected
		*/
		
		// 1. Log into DTS portal as a DTS user
		// 2. Log into DTS portal as a DTS User successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio" page
		audioControl.click(PageHome.linkAudioroutes);
		// 4. Select “Attached6 - Internal Speakers (mode5)” route in Other Audio Routes section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Other_Routes.Attached6_Internal_Speakers.getName());
		// 5. Click on Edit Version link
		audioControl.editVersion();
		// 6. Check "Type" and "Audio Route ID" Section
		/*
		 * VP: The "Type" and "Audio Route ID" section are displayed as readonly
		 */
		Assert.assertFalse(audioControl.canEdit(AudioRoutesEdit.TYPE));
		Assert.assertFalse(audioControl.canEdit(AudioRoutesEdit.AUDIO_ROUTE_ID));
		// 7. Repeat test case with Attached 7
		// Navigate to "Audio" page
		audioControl.click(PageHome.linkAudioroutes);
		// Select “Attached7 - Internal Speakers (mode6)” route in Other Audio Routes section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Other_Routes.Attached7_Internal_Speakers.getName());
		// Click on Edit Version link
		audioControl.editVersion();
		// Check "Type" and "Audio Route ID" Section
		/*
		 * VP: The "Type" and "Audio Route ID" section are displayed as readonly
		 */
		Assert.assertFalse(audioControl.canEdit(AudioRoutesEdit.TYPE));
		Assert.assertFalse(audioControl.canEdit(AudioRoutesEdit.AUDIO_ROUTE_ID));
		// Repeat test case with Attached 8
		// Navigate to "Audio" page
		audioControl.click(PageHome.linkAudioroutes);
		// Select “Attached8 - Internal Speakers (mode7)” route in Other Audio Routes section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Other_Routes.Attached8_Internal_Speakers.getName());
		// Click on Edit Version link
		audioControl.editVersion();
		// Check "Type" and "Audio Route ID" Section
		/*
		 * VP: The "Type" and "Audio Route ID" section are displayed as readonly
		 */
		Assert.assertFalse(audioControl.canEdit(AudioRoutesEdit.TYPE));
		Assert.assertFalse(audioControl.canEdit(AudioRoutesEdit.AUDIO_ROUTE_ID));
	}	
	/*
	 * Verify that the valid ''Attached6, Attached7, Attached8'' ,route tuning file is uploaded successfully in Other Audio Route
	 */
	@Test
	public void TC620DAE_34(){
		audioControl.addLog("ID : TC620DAE_34 : Verify that the valid ''Attached6, Attached7, Attached8'' ,route tuning file is uploaded successfully in Other Audio Route");
		
		/*
		 	1. Log into DTS portal as DTS admin
			2. Navigate to "Audio" page
			3. Select  ''Attached6'' audio route in "Other Audio Routes" Section
			4. Edit above audio route
			5. Click on " Select file" link
			6. Upload the valid tuning DTSCS file
			VP: Verify that tuning DTSCS file is displayed successfully
			7. Repeat test case for Attached7 and Attached8
			VP: A "Invalid route" message is  not Displayed in "Custom Tuning" section
		*/
		
		// 1. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Audio" page
		audioControl.click(PageHome.linkAudioroutes);
		// 3. Select “Attached6 - Internal Speakers (mode5)” route in Other Audio Routes section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Other_Routes.Attached6_Internal_Speakers.getName());
		// 4. Click on Edit Version link
		audioControl.editVersion();
		// 5. Click on " Select file" link
		// 6. Upload an invalid tuning file
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Attached_6_Game_2_Eagle2_0.getName());
		/*
		 * VP: A message is displayed and the valid tuning file upload successfully.
		 */
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.AUDIOROUTE_UPLOAD_MESSAGE).contains(AudioRoutesEdit.Upload_File_Message.File_upload_successful.getName()));
		// 7. Repeat test case for Attached7
		// Navigate to "Audio" page
		audioControl.click(PageHome.linkAudioroutes);
		// Select “Attached7 - Internal Speakers (mode6)” route in Other Audio Routes section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Other_Routes.Attached7_Internal_Speakers.getName());
		// Click on Edit Version link
		audioControl.editVersion();
		// Upload an invalid tuning file
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Attached_7_Game_3_Eagle2_0.getName());
		/*
		 * VP: A message is displayed and the valid tuning file upload successfully.
		 */
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.AUDIOROUTE_UPLOAD_MESSAGE).contains(AudioRoutesEdit.Upload_File_Message.File_upload_successful.getName()));
		// Repeat test case for Attached8
		// Navigate to "Audio" page
		audioControl.click(PageHome.linkAudioroutes);
		// Select “Attached8 - Internal Speakers (mode7)” route in Other Audio Routes section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Other_Routes.Attached8_Internal_Speakers.getName());
		// Click on Edit Version link
		audioControl.editVersion();
		// Upload an invalid tuning file
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Attached_8_Custom_Eagle2_0.getName());
		/*
		 * VP: A message is displayed and the valid tuning file upload successfully.
		 */
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.AUDIOROUTE_UPLOAD_MESSAGE).contains(AudioRoutesEdit.Upload_File_Message.File_upload_successful.getName()));
	}
	/*
	 * Verify that the invalid ''Attached6, Attached7, Attached8'' ,route tuning file is uploaded unsuccessfully in Orther Audio Route
	 */
	@Test
	public void TC620DAE_35(){
		audioControl.addLog("ID : TC620DAE_35 : Verify that the invalid ''Attached6, Attached7, Attached8'' ,route tuning file is uploaded unsuccessfully in Orther Audio Route");
		
		/*
		 	1. Log into DTS portal as DTS admin
			2. Navigate to "Audio" page
			3. Select  ''Attached6'' audio route in "Other Audio Routes" Section
			4. Click on " Update Product Info" link
			5. Click on "OK" button in pop-up
			6. Click on " Select file" link
			7. Upload the valid tuning DTSCS file
			VP: A "Invalid route" message is Displayed in "Custom Tuning" section
			8. Repeat testcase for Attached7 and Attached8
			VP: A "Invalid route" message is Displayed in "Custom Tuning" section
		*/
		
		// 1. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Audio" page
		audioControl.click(PageHome.linkAudioroutes);
		// 3. Select “Attached6 - Internal Speakers (mode5)” route in Other Audio Routes section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Other_Routes.Attached6_Internal_Speakers.getName());
		// 5. Click on Edit Version link
		audioControl.editVersion();
		// 6. Upload an invalid tuning file
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Attached_7_Game_3_Eagle2_0.getName());
		/*
		 * VP: A "Invalid route" message is Displayed in "Custom Tuning" section.
		 */
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.AUDIOROUTE_UPLOAD_MESSAGE).contains(AudioRoutesEdit.Upload_File_Message.Invalid_file.getName()));
		// 7. Repeat test case for Attached7
		// Navigate to "Audio" page
		audioControl.click(PageHome.linkAudioroutes);
		// Select “Attached7 - Internal Speakers (mode6)” route in Other Audio Routes section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Other_Routes.Attached7_Internal_Speakers.getName());
		// Click on Edit Version link
		audioControl.editVersion();
		// Upload an invalid tuning file
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Attached_6_Game_2_Eagle2_0.getName());
		/*
		 * VP: A "Invalid route" message is Displayed in "Custom Tuning" section
		 */
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.AUDIOROUTE_UPLOAD_MESSAGE).contains(AudioRoutesEdit.Upload_File_Message.Invalid_file.getName()));
		// Repeat test case for Attached8
		// Navigate to "Audio" page
		audioControl.click(PageHome.linkAudioroutes);
		// Select “Attached8 - Internal Speakers (mode7)” route in Other Audio Routes section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Other_Routes.Attached8_Internal_Speakers.getName());
		// Click on Edit Version link
		audioControl.editVersion();
		// Upload an invalid tuning file
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Attached_6_Game_2_Eagle2_0.getName());
		/*
		 * VP: A "Invalid route" message is Displayed in "Custom Tuning" section.
		 */
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.AUDIOROUTE_UPLOAD_MESSAGE).contains(AudioRoutesEdit.Upload_File_Message.Invalid_file.getName()));
	}
}
