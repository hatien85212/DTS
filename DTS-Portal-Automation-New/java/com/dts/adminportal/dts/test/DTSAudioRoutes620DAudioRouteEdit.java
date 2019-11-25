package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AudioRoutes;
import com.dts.adminportal.model.AudioRoutesEdit;
import com.dts.adminportal.model.AudioRoutesInfo;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.LanguagePackage;
import com.dts.adminportal.model.PageHome;
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
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Click on an audio routes link
		audioControl.click(AudioRoutes.LINK_STANDARD_AUDIO);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.STANDARD_ROUTES[0]);
		// 5. Click on Edit Version link
		audioControl.click(AudioRoutes.AUDIO_DETAILS_EDIT_VERSION);
		//6. Upload an invalid tuning file
		audioControl.doDelete(AudioRoutesEdit.DELETE_TUNING_ICON);
		audioControl.uploadFile(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		/*
		 * VP: An error message is displayed and the invalid tuning file is not added.
		 */
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.AUDIOROUTE_UPLOAD_MESSAGE).contains(AudioRoutesEdit.UPLOAD_FILE_MESSSAGE[1]));
		
		
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
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Click on an audio routes link
		audioControl.click(AudioRoutes.LINK_STANDARD_AUDIO);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.STANDARD_ROUTES[0]);
		// 5. Click on Edit Version link
		audioControl.click(AudioRoutes.AUDIO_DETAILS_EDIT_VERSION);
		// 6. Try to edit "Type" and "Audio Route ID" section
		/*
		 * VP: The "Type" and "Audio Route ID" section are displayed as readonly
		 */
		Assert.assertFalse(audioControl.canEdit(AudioRoutes.EDIT_TYPE));
		Assert.assertFalse(audioControl.canEdit(AudioRoutes.EDIT_AUDIO_ROUTE_ID));
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
		  audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		  // 4. Select an audio route item
		  audioControl.click(AudioRoutes.LINK_OTHER_AUDIO);
		  // VP: The 615D Audio Route Detail page is displayed
		  Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.AUDIO_DETAILS_DISPLAY_NAME));
		  // 5. Click "Edit" link
		  audioControl.click(AudioRoutes.AUDIO_DETAILS_EDIT_VERSION);
		  List<LanguagePackage> before = audioControl.getLanguagePackage(AudioRoutes.EDIT_LANGUAGE_CONTAINER);
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
		  ArrayList<String> languages = audioControl.getOptionList(AudioRoutes.EDIT_LANGUAGE_SELECT);
		  //Assert.assertTrue(ListUtil.containsAll(languages, AddEditProductModel.LANGUAGE_LIST));
		  Assert.assertTrue(ListUtil.containsAll(languages, AddEditProductModel.ProductLanguage.getNames()));
		  //List<LanguagePackage> before = audioControl.getLanguagePackage(AudioRoutes.EDIT_LANGUAGE_CONTAINER);
		  // 6. Select another language for Display Name
		  audioControl.selectOptionByName(AudioRoutes.getLang_selectxpath(before.size()),AddEditProductModel.ProductLanguage.ITALIAN.getName());
		  /*
		    VP: The Display Name language text field is enabled and the hint text
		    "Inherits default" is displayed in Display Name text field.
		   */
		  Assert.assertEquals(audioControl.getAtributeValue(AudioRoutes.EDIT_LANGUAGE_INPUT, "placeholder"),"inherits default");
		  //audioControl.selectOptionByName(AudioRoutes.EDIT_LANGUAGE_SELECT, AddEditProductModel.ProductLanguage.ITALIAN.getName());
		  List<LanguagePackage> after = audioControl.getLanguagePackage(AudioRoutes.EDIT_LANGUAGE_CONTAINER);
		  /*
		   * VP: Another language dropdown is displayed
		   */
		  Assert.assertTrue(after.size() == before.size() + 1);
		  List<LanguagePackage> languagepackage = audioControl.getLanguagePackage(AudioRoutes.EDIT_LANGUAGE_CONTAINER);
		  // 7.Select the language at the next dropbox as same as the first one
		  audioControl.selectOptionByName(languagepackage.get(languagepackage.size()-1).languagedropbox, 
		    AddEditProductModel.ProductLanguage.ITALIAN.getName());
		  // Get the language package
		  languagepackage.clear();
		  languagepackage = audioControl.getLanguagePackage(AudioRoutes.EDIT_LANGUAGE_CONTAINER);
		  //  Get text of the  warning message is displayed at the first package
		  String warning = languagepackage.get(0).warningmessage;
		  System.out.println(warning);
		  /*
		   Verify that a warning message is displayed for duplicating language dropdowns
		   */
		  Assert.assertTrue(warning.contains("Language is duplicated"));
		  // 8. Set language dropdown back to "Select"
		  audioControl.selectOptionByName(AudioRoutes.EDIT_LANGUAGE_SELECT,AddEditProductModel.ProductLanguage.SELECT.getName());
		    /*
		     * VP The Display Name language text field is disabled.
		     */
		  Assert.assertEquals(audioControl.getAtributeValue(AddEditProductModel.EMPTY_LANGUAGE_FIELD, "disabled"),"true");
		  //9. Type value into model name input field
		  audioControl.selectOptionByName(AudioRoutes.EDIT_LANGUAGE_SELECT,AddEditProductModel.ProductLanguage.ITALIAN.getName());
		  audioControl.editData(AudioRoutes.EDIT_LANGUAGE_INPUT, RandomStringUtils.randomAlphabetic(10));
		  // 10. Click on Trashcan icon
		  audioControl.click(AudioRoutes.EDIT_LANGUAGE_TRASH);
		  /*
		  * The language dropdown is restore to default "Select" value and the input field is cleared 
		  */
		  Assert.assertEquals(audioControl.getItemSelected(AudioRoutes.EDIT_LANGUAGE_SELECT), AddEditProductModel.ProductLanguage.SELECT.getName());
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
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		audioControl.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.STANDARD_ROUTES[0]);
		// 5. Click Edit link
		audioControl.click(AudioRoutesInfo.EDIT);
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
		Assert.assertTrue(audioControl.getAtributeValue(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE, "src").contains(AudioRoutesEdit.IMG_NAME[0].replaceAll(".jpg", "")));
		
		//Close the lightbox fot 250x250
		audioControl.click(AudioRoutesEdit.LIGHTBOX_CLOSE);
		Thread.sleep(3000);

		//Verify lightbox image is displayed for 500x500	
		audioControl.click(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_THUMBNAIL_500);
		//wait for ligtbox display
		audioControl.waitForElementClickable(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(audioControl.getAtributeValue(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE, "src").contains(AudioRoutesEdit.IMG_NAME[1].replaceAll(".jpg", "")));
		
		//Close the lightbox fot 500x500
		audioControl.click(AudioRoutesEdit.LIGHTBOX_CLOSE);
		Thread.sleep(3000);
		
		//Verify lightbox image is displayed for 1000x1000
		audioControl.click(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000);
		//wait for ligtbox display
		audioControl.waitForElementClickable(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(audioControl.getAtributeValue(AudioRoutesEdit.LIGHTBOX_STYLE_IMAGE, "src").contains(AudioRoutesEdit.IMG_NAME[2].replaceAll(".jpg", "")));
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
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AudioRoutesEdit.UPLOAD_FILE_MESSSAGE[0]));
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AudioRoutesEdit.UPLOAD_FILE_MESSSAGE[0]));
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AudioRoutesEdit.UPLOAD_FILE_MESSSAGE[0]));
	}
	
	/*
	 * Verify that at least one connection type is required when editing an audio route.
	 */
	@Test
	public void TC620DAE_16(){
		audioControl.addLog("ID : TC620DAE_16 : Verify that at least one connection type is required when editing an audio route.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			VP: The 615D Audio Route Detail page is displayed
			VP: The Input Specifications section inludes "Connection Type" and "Support Input Channels" module.
			5. Enable a connection type.
				"VP: Verify that the similar Supported Input Channels is enabled.
				VP: The supported input channels combobox support multi-selecting items including:
				Select all that apply
				1 (Single Channel)
				2 (Stereo)"
			6. Disable a connection type
			VP: Verify that the similar Supported Input Channels is disabled.
			7. Uncheck all connection type.
			VP:Verify that An error message displays which notifying user to select at least one connection type

		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		audioControl.click(AudioRoutes.LINK_STANDARD_AUDIO);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(AudioRoutesInfo.getElementsInfo()), true);
		// Click Edit link
		audioControl.click(AudioRoutesInfo.EDIT);
		/*
		 * VP: The Input Specifications section inludes "Connection Type" and "Supported Input Channels" module.
		 */
		ArrayList<String> list = audioControl.getInputSpecificationHeader(AudioRoutesEdit.INPUT_SPECIFICATION);
		Assert.assertTrue(list.contains("Connection Type") && list.contains("Supported Input Channels"));
		//5. Enable a connection type.
		audioControl.selectACheckbox(AudioRoutesEdit.WIRED_CHECKBOX);
		/*
		 * Verify that the similar Supported Input Channels is enabled
		 */
		Assert.assertTrue(audioControl.isElementEditable(AudioRoutesEdit.WIRED_DROPBOX));
		/*
		 * VP: The supported input channels combobox support multi-selecting items including:
			Select all that apply
			1 (Mono)
			2 (Stereo)"
		 */
		audioControl.selectInputChannel(AudioRoutesEdit.WIRED_CONTAINER,"Select all that apply");
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.WIRED_DROPBOX).contains(AudioRoutesEdit.SUPPORTED_INPUT_CHANNELS[1] + ", " + AudioRoutesEdit.SUPPORTED_INPUT_CHANNELS[2]));
		//6. Disable a connection type
		audioControl.uncheckACheckbox(AudioRoutesEdit.WIRED_CHECKBOX);
		/*
		 * Verify that the similar Supported Input Channels is disabled
		 */
		Assert.assertFalse(audioControl.isElementEditable(AudioRoutesEdit.WIRED_DROPBOX));
		//7. Uncheck all connection type.
		audioControl.uncheckACheckbox(AudioRoutesEdit.WIRED_CHECKBOX);
		audioControl.uncheckACheckbox(AudioRoutesEdit.BLUETOOTH_CHECKBOX);
		//Click save link
		audioControl.click(AudioRoutesEdit.SAVE);
		/*
		* Verify that An error message displays which notifying user to select at least one connection type
		 */
		String message = "Connection Type is required";
		Assert.assertTrue(audioControl.checkMessageDisplay(message));
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
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		audioControl.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		// Get default display name
		String defaultName = audioControl.getTextByXpath(AudioRoutesInfo.DEFAULT_DISPLAY_NAME);
		// 5. Click "Edit Version" link
		audioControl.click(AudioRoutesInfo.EDIT);
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
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.STANDARD_ROUTES[0]);
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
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		audioControl.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.STANDARD_ROUTES[0]);
		// 5. Click Edit link
		audioControl.click(AudioRoutesInfo.EDIT);
		// 6. Upload a tuning file successfully
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING,AddEditProductModel.FileUpload.Over_Ear_Headphones.getName());
		// 7. Click on above tuning link after uploaded
		// 8. Try to download the tuning file
		Boolean isDownloaded = audioControl.downloadFile(AddEditProductModel.FileUpload.Over_Ear_Headphones.getName());
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
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		audioControl.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.STANDARD_ROUTES[0]);
		// 5. Click "Edit" link
		audioControl.click(AudioRoutesInfo.EDIT);
		// 6. Upload a tuning file successfully
		// 7. Click on Trashcan icon of tuning section
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
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		audioControl.click(AudioRoutes.LINK_STANDARD_AUDIO);
		/*
		 * VP: The 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(AudioRoutesInfo.getElementsInfo()), true);
		// 5. Click Edit link
		audioControl.click(AudioRoutesInfo.EDIT);
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
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		audioControl.click(AudioRoutes.LINK_STANDARD_AUDIO);
		// Click on Edit Version link
		audioControl.click(AudioRoutes.AUDIO_DETAILS_EDIT_VERSION);
		// 5. Upload a tuning file successfully
		if (audioControl.isElementPresent(AudioRoutes.EDIT_DELETE_TUNNING)){
			// Delete Tuning file
			audioControl.doDelete(AudioRoutes.EDIT_DELETE_TUNNING);
		}	
		// 5.Upload the tuning file
		audioControl.uploadFile(AudioRoutes.EDIT_UPLOAD_TUNNING, AudioRoutes.AUDIO_DETAILS_TUNNING_FILE);
		// cancel
		audioControl.click(AudioRoutes.EDIT_AUDIO_CANCEL);
		Assert.assertFalse(audioControl.isElementPresent(AudioRoutes.EDIT_AUDIO_CANCEL));
	}
}
