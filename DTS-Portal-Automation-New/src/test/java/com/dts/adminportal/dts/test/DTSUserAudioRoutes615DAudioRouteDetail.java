package com.dts.adminportal.dts.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AudioRoutes;
import com.dts.adminportal.model.AudioRoutesEdit;
import com.dts.adminportal.model.AudioRoutesInfo;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.RoomModelEdit;
import com.dts.adminportal.model.RoomModelInfo;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.StringUtil;

public class DTSUserAudioRoutes615DAudioRouteDetail extends BasePage{

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
	}
	
//	@BeforeMethod
//	public void loginBeforeTest() {
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//	}
	
	/*
	 * Verify that the Display Name section displays the audio route name with appropriate languages.
	 */
	@Test
	public void TC615DAD_01() {
		audioControl.addLog("ID : TC615DAD_01 : Verify that the Display Name section displays the audio route name with appropriate languages");
		audioControl.addErrorLog("PDPP-1421: Display name of external languages don't change after edited");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select an audio route item
			5. Click "Edit Version" link
			VP: The Display Name, Audio Route ID, Type, Input Specification are displayed as read only.
			6. Set the Audio Route's display name for all languages
			7. Upload three kind of Primary Catalog Image.
			8. Upload new tuning file
			9. Click "Save" link
			VP: The 615D Audio Route Detail page is displayed and the Display Name section is displayed with all languages.
			VP: The Audio Route Details page is displayed and the primary catalog image dislays in ordered from left to right: 250x250,500x500 and 1000x1000
			VP: The audio route tuning file is uploaded and the uploaded date is displayed next to "Upload Tuning" link.
		 */
		//2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.linkAudioroutes);
		// 4. Select an audio route item
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName()); //Over-Ear Headphones
		// Get default display name
		String defaultName = audioControl.getTextByXpath(AudioRoutesInfo.DEFAULT_DISPLAY_NAME);
		// 5. Click "Edit Version" link
		audioControl.editVersion();
		// 6. Set the Audio Route's display name for all languages
		ArrayList<String> allLanguages = audioControl.addAllLanguage(AudioRoutesEdit.LANGUAGE_CONTAINER);
		allLanguages.add(0, defaultName);
		// 7. Upload three kind of Primary Catalog Image.
		// Delete image if exist
		audioControl.deleteAllUploadedImage(AudioRoutesEdit.IMAGE_CATALOG);		
		audioControl.uploadFile(AudioRoutesEdit.ADD_IMAGE250, Constant.IMG_DRAG_DROP_AREA.Image_Drag_Drop_Area_250.getName());
		audioControl.uploadFile(AudioRoutesEdit.ADD_IMAGE500, Constant.IMG_DRAG_DROP_AREA.Image_Drag_Drop_Area_500.getName());
		audioControl.uploadFile(AudioRoutesEdit.ADD_IMAGE1000, Constant.IMG_DRAG_DROP_AREA.Image_Drag_Drop_Area_1000.getName());
		// 8. Upload new tuning file
		// Delete tuning file if exist
		// 9. Click "Save" link
		audioControl.selectACheckbox(AudioRoutesEdit.BLUETOOTH_CHECKBOX);
		audioControl.selectACheckbox(AudioRoutesEdit.WIRED_CHECKBOX);
		audioControl.uploadFileTuning(RoomModelEdit.ADD_TUNING,AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined.getName());
		audioControl.click(AudioRoutesEdit.SAVE);
		String audioname = audioControl.getTextByXpath(AudioRoutesInfo.ROUTER_TUNING);
		// Get all languages name display
		ArrayList<String> displayedLanguages = audioControl.getAudioRouteName(AudioRoutesInfo.ALL_DISPLAY_NAME);
		/*
		 * Verify that The 615D Audio Route Detail page is displayed and the Display Name section is displayed with all languages
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		Assert.assertTrue(ListUtil.containsAll(allLanguages, displayedLanguages));
		//VP: The Audio Route Details page is displayed and the primary catalog image dislays in ordered from left to right: 250x250,500x500 and 1000x1000
		Assert.assertTrue(audioControl.isImageDisplayLeftToRight(AudioRoutesInfo.IMAGE_CATALOG));
		//VP: The audio route tuning file is uploaded and the uploaded date is displayed next to "Upload Tuning" link.
		DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		Date date = new Date();
		String datecurrent = dateFormat.format(date).toString();
		String datetuning = StringUtil.getDateFromText(audioname);
		Assert.assertEquals(datecurrent,datetuning);
		/*
		 * Verify that the Display Name, Audio Route ID, Type, Input Specification are displayed as read only
		 */
		
		Assert.assertFalse(audioControl.canEdit(AudioRoutesInfo.DISPLAY_MODEL));
		Assert.assertFalse(audioControl.canEdit(AudioRoutesInfo.AUDIO_ROUTE_ID));
		Assert.assertFalse(audioControl.canEdit(AudioRoutesInfo.TYPE));
		Assert.assertFalse(audioControl.canEdit(AudioRoutesInfo.INPUT_SPECIFICATION));
	}
	
	/*
	 * Verify the Audio Route Detail page 
	 */
	@Test
	public void TC615DAD_02() {
		audioControl.addLog("ID : TC615DAD_02 : Verify the Audio Route Detail page ");
		
		/*	1. Navigate to DTS portal	
			2. Log into DTS portal as a DTS user successfully	
			3. Navigate to "Audio" page	
			4. Select an audio route item	
			5 . Click on "Edit Verdion" link	
			6. Upload a tuning file successfully	
			7. Upload three kind of Primary Catalog Image.	
			8. Click "Save" link	
			VP: the 615D Audio Route Detail page is displayed
			VP: Audio Route ID is displayed correctly
			VP: Type is displayed correctly
			VP: Input Sepcifications is displayed
			VP: Tuning File Link is displayed and the uploaded date is displayed next to ""Upload Tuning"" link.
			VP:  the primary catalog image dislays in ordered from left to right: 160x160, 290x290 and 664x664.
			9. Click "Edit Version" link	
			VP: the 620D Audio Route Edit page is displayed
			10. Click "Cancel" Link	VP: the 615D Audio Route Detail page is displayed
			11. Click in 160x160 image	
			VP: 160x160 image is displayed as thumbnail
			12. Click in "Close" icon	
			13. Click in 290x290 image	
			VP: 290x290 image is displayed as thumbnail
			14. Click in "Close" icon	
			15. Click in 664x664 image	
			VP: 664x664 image is displayed as thumbnail
			16. Click in "Close" icon	
			17. Click in Tuning file link	
			VP: Tuning file is downloaded successully

		*/
		
		//1. Navigate to DTS portal
		//2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//3. Navigate to "Audio" page
		audioControl.click(PageHome.linkAudioroutes);
		//4. Select an audio route item
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName()); // Over-Ear Headphones
		//5 . Click on "Edit Verdion" link
		audioControl.editVersion();
		//6. Upload a tuning file successfully
		audioControl.selectACheckbox(AudioRoutesEdit.BLUETOOTH_CHECKBOX);
		audioControl.selectACheckbox(AudioRoutesEdit.WIRED_CHECKBOX);
		audioControl.uploadFileTuning(AudioRoutesEdit.UPLOADED_AUDIO_ROUTE,AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined.getName());
		//7. Upload three kind of Primary Catalog Image.
		audioControl.uploadImages();
		//8. Click "Save" link
		audioControl.click(AudioRoutesEdit.SAVE);
		/*
		 * VP: the 615D Audio Route Detail page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(AudioRoutesInfo.getElementsInfo()),true);
		/*
		 * VP: Audio Route ID is displayed correctly
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.AUDIO_ROUTE_ID),"Line-out0, Bluetooth0");
		/*
		 * VP: Type is displayed correctly
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.TYPE),"Over-Ear");
		/*
		 * VP: Input Sepcifications is displayed
		 */
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesInfo.INPUT_SPECIFICATION));
		/*
		 * VP: Tuning File Link is displayed and the uploaded date is displayed next to "Upload Tuning" link.
		 */
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesInfo.TUNING_FILE));
		/*
		 * VP:  the primary catalog image dislays in ordered from left to right: 160x160, 290x290 and 664x664. "
		 */
		Assert.assertTrue(audioControl.isImageDisplayLeftToRight(AudioRoutesInfo.IMAGE_CATALOG));
		//5. Click "Edit Version" link
		audioControl.click(AudioRoutesInfo.EDIT);
		/*
		 * VP: the 620D Audio Route Edit page is displayed
		 */
		//6. Click "Cancel" Link
		audioControl.click(AudioRoutesEdit.CANCEL);
		/*
		 * VP: the 615D Audio Route Detail page is displayed
		 */
		//7. Click in 160x160 image
		audioControl.click(AudioRoutesInfo.IMAGE250_DISPLAY);
		audioControl.waitForElementClickable(AudioRoutesInfo.LIGHTBOX_STYLE_IMAGE);
		/*
		 * VP: 160x160 image is displayed as thumbnail
		 */
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesInfo.LIGHTBOX_STYLE_IMAGE));
		//8. Click in "Close" icon
		audioControl.click(AudioRoutesInfo.LIGHTBOX_CLOSE);
		//9. Click in 290x290 image	
		audioControl.click(AudioRoutesInfo.IMAGE500_DISPLAY);
		audioControl.waitForElementClickable(AudioRoutesInfo.LIGHTBOX_STYLE_IMAGE);
		/*
		 * VP: 290x290 image is displayed as thumbnail
		 */
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesInfo.LIGHTBOX_STYLE_IMAGE));
		//10. Click in "Close" icon
		audioControl.click(AudioRoutesInfo.LIGHTBOX_CLOSE);
		//11. Click in 664x664 image	
		audioControl.click(AudioRoutesInfo.IMAGE1000_DISPLAY);
		audioControl.waitForElementClickable(AudioRoutesInfo.LIGHTBOX_STYLE_IMAGE);
		/*
		 * VP: 664x664 image is displayed as thumbnail
		 */
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesInfo.LIGHTBOX_STYLE_IMAGE));
		//12. Click in "Close" icon	
		audioControl.click(AudioRoutesInfo.LIGHTBOX_CLOSE);
		//13. Click in Tuning file link	
		/*
		 * VP: Tuning file is downloaded successully
		 */
		Assert.assertTrue(audioControl.downloadFile(AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined.getName()));

	}
	/*
	 * Verify that "Edit Version","View Published Version" link is displayed and "Notes" Section is displayed as readonly
	 */
	@Test
	public void TC615DAD_03() {
		audioControl.addLog("ID : TC615DAD_03 :Verify that 'Edit Version','View Published Version' link is displayed and 'Notes' Section is displayed as readonly");
		
		/*1. Navigate to DTS portal	
		2. Log into DTS portal as a DTS user successfully	
		3. Navigate to "Audio Routes" page	
		4. Select a room model item	
		VP: The "Edit Version" link is displayed and "Notes" Section is displayed as readonly
		*/
		
		//1. Navigate to DTS portal	
		//2. Log into DTS portal as a DTS user successfully	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//3. Navigate to "Audio Routes" page	
		audioControl.click(PageHome.linkAudioroutes);
		//4. Select a room model item	
		audioControl.click(AudioRoutes.MCROOM0);
		/*
		 * VP: The "Edit Version" link is displayed and "Notes" Section is displayed as readonly
		 */
		Assert.assertTrue(audioControl.isElementPresent(RoomModelInfo.EDIT));
		Assert.assertFalse(audioControl.isElementEditable(AudioRoutesInfo.NOTES));
	}
	
	/*
	 * Verify that 621D Room Model Edit Page is displayed when clicking on "Edit Version" link
	 */
	@Test
	public void TC615DAD_04() {
		audioControl.addLog("ID : TC615DAD_04 : Verify that 621D Room Model Edit Page is displayed when clicking on 'Edit Version' link");
		
		/*
		 *  1. Navigate to DTS portal	
			2. Log into DTS portal as a DTS user successfully	
			3. Navigate to "Audio Routes" page	
			4. Select a room model item	
			5 . Click on "Edit Verdion" link	
			VP: The 621D Room Model Edit Page is displayed when clicking on "Edit Version" link

		 */
		
		//1. Navigate to DTS portal	
		//2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.linkAudioroutes);
		//4. Select a room model item
		audioControl.click(AudioRoutes.MCROOM0);
		//5 . Click on "Edit Verdion" link
		audioControl.editVersion();
		/*
		 * VP: The 621D Room Model Edit Page is displayed when clicking on "Edit Version" link
		 */
		Assert.assertEquals(audioControl.existsElement(RoomModelEdit.getElementsInfo()),true);
	}
	/*
	 * Verify the information in Stereo Room Model Detail page.
	 */
	@Test
	public void TC616DRD_01() {
		audioControl.addLog("ID : TC616DRD_01 : Verify the information in Stereo Room Model Detail page");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio" page
			4. Select a Stereo Room Model
			VP: The 616D Room Model Detail page is displayed
			VP:  The information in Salesforce ID, Name, Notes fields are displayed correctly.
			VP: The Tuning File Link is displayed and the uploaded date is displayed next to "Upload Tuning" link.
			5. Click "Edit Version" link 
			VP: The 621D Room Model Edit page is displayed
			6. Click "Cancel" Link
			VP: The 616D Room Model Detail page is displayed
			7. Click in "Tuning File" link
			VP: Tuning file is downloaded successfully

		 */
		//2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.linkAudioroutes);
		// 4. Select a Stereo Room Model
		audioControl.selectAnAudioRouteByName(AudioRoutes.Stereo_Room_Models.Wide.getName());
		//VP: The 616D Room Model Detail page is displayed
		//VP:  The information in Salesforce ID, Name, Notes fields are displayed correctly.
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.ROOMMODEL_UUID),AudioRoutesEdit.AudioUUIDs.StereoRoom0.getUUID());
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_ID),"StereoRoom0");
		//VP: The Tuning File Link is displayed and the uploaded date is displayed next to "Upload Tuning" link.
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesInfo.ROUTER_TUNING));
		// 5. Click "Edit Version" link
		audioControl.editVersion();
//		VP: The 621D Room Model Edit page is displayed
		Assert.assertEquals(audioControl.existsElement(RoomModelEdit.getElementsInfo()),true);
		//make sure file tuning is uploaded
		audioControl.uploadFileTuning(RoomModelEdit.ADD_TUNING,AddEditProductModel.FileUpload.Super_Stereo_Wide.getName());
		audioControl.click(RoomModelEdit.SAVE);
		audioControl.editVersion();
//		6. Click "Cancel" Link
		audioControl.click(RoomModelEdit.SAVE);
//		VP: The 616D Room Model Detail page is displayed
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.ROOMMODEL_UUID),AudioRoutesEdit.AudioUUIDs.StereoRoom0.getUUID());
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_ID),"StereoRoom0");
//		7. Click in "Tuning File" link
		// Click on download link
		audioControl.donwloadFilesuccess();
	}
	
	/*
	 * Verify the information in Multi-Channel Room Model Detail page
	 */
	@Test
	public void TC616DRD_02() {
		audioControl.addLog("ID : TC616DRD_02 : Verify the information in Multi-Channel Room Model Detail page");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio" page
			4. Select a Multi-Channel Room Model
			VP: The 616D Room Model Detail page is displayed
			VP:  The information in Salesforce ID, Name, Notes fields are displayed correctly.
			VP: The Tuning File Link is displayed and the uploaded date is displayed next to "Upload Tuning" link.
			5. Click "Edit Version" link 
			VP: The 621D Room Model Edit page is displayed
			6. Click "Cancel" Link
			VP: The 616D Room Model Detail page is displayed
			7. Click in "Tuning File" link
			VP: Tuning file is downloaded successfully

		 */
		//2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.linkAudioroutes);
		// 4. Select a Multi-Channel Room Model
		audioControl.click(AudioRoutes.MCROOM0);
		//VP: The 616D Room Model Detail page is displayed
		//VP:  The information in Salesforce ID, Name, Notes fields are displayed correctly.
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.ROOMMODEL_UUID),AudioRoutesEdit.AudioUUIDs.McRoom0.getUUID());
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_ID),"McRoom0");
		//VP: The Tuning File Link is displayed and the uploaded date is displayed next to "Upload Tuning" link.
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutesInfo.ROUTER_TUNING));
		// 5. Click "Edit Version" link
		audioControl.editVersion();
//		VP: The 621D Room Model Edit page is displayed
		Assert.assertEquals(audioControl.existsElement(RoomModelEdit.getElementsInfo()),true);
		//make sure file tuning is uploaded
		audioControl.uploadFileTuning(RoomModelEdit.ADD_TUNING,AddEditProductModel.FileUpload.McRoom0_RC1_5p1_7p1.getName());
		audioControl.click(RoomModelEdit.SAVE);
		audioControl.editVersion();
//		6. Click "Cancel" Link
		audioControl.click(RoomModelEdit.SAVE);
//		VP: The 616D Room Model Detail page is displayed
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.ROOMMODEL_UUID),AudioRoutesEdit.AudioUUIDs.McRoom0.getUUID());
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_ID),"McRoom0");
//		7. Click in "Tuning File" link
		// Click on download link
		audioControl.donwloadFilesuccess();
	}
	
	/*
	 * Verify that “Input Specification” secsion is displayed correctly in 615D Audio Route Detail page
	 */
	@Test
	public void TC615DAD_05() {
		audioControl.addLog("ID : TC615DAD_05 : Verify that “Input Specification” secsion is displayed correctly in 615D Audio Route Detail page");
		
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Audio" page
			3. Select a Standard Accessory audio route
			VP: Verify that “Input Specification” secsion includes: “Wire”, “Bluetooth” and “USB” as read only
			VP: Verify that “Audio Route ID” section displays ""Line-out0, Bluetooth0, USB"" as read only

	
		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Audio" page
		audioControl.click(PageHome.linkAudioroutes);
		// 3. Select a Standard Accessory audio route
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Ear_piece.getName());
		// VP: Verify that “Input Specification” section includes: “Wire”, “Bluetooth” and “USB” as read only
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesInfo.INPUT_SPECIFICATION).contains(AudioRoutesInfo.ConnectionType.WIRED.getName()));
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesInfo.INPUT_SPECIFICATION).contains(AudioRoutesInfo.ConnectionType.BLUETOOTH.getName()));
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesInfo.INPUT_SPECIFICATION).contains(AudioRoutesInfo.ConnectionType.USB.getName()));
		Assert.assertFalse(audioControl.canEdit(AudioRoutesInfo.INPUT_SPECIFICATION));
	}
	
	/*
	 * Verify that Complete Profile will include route type AR_OUT_USB
	 */
	@Test
	public void TC615DAD_06() {
		audioControl.addLog("ID : TC615DAD_06 : Verify that Complete Profile will include route type AR_OUT_USB");
		
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Audio" page
			3. Select “Over Ear Headphones” route in Standard Accessory section
			4. Click "Edit Version" link
			5. Upload a dtscs file contains Lineout, Bluetooth and USB
			VP: Verify that a dtscs file is uploaded successfully
			6. Click “Save” link
			7. Publish above route
			8. Download “Complete Profile”  link
			Verify that Complete Profile will include route type AR_OUT_USB
	
		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Audio" page
		audioControl.click(PageHome.linkAudioroutes);
		// 3. Select “Over Ear Headphones” route in Standard Accessory section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		// 4. Click "Edit Version" link
		audioControl.editVersion();
		// 5. Upload a dtscs file contains Lineout, Bluetooth and USB
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined.getName());
		// VP: Verify that a dtscs file is uploaded successfully
		Assert.assertFalse(audioControl.isElementPresent(AudioRoutesEdit.ADD_TUNING));
		// 6. Click “Save” link
		audioControl.click(AudioRoutesEdit.SAVE);
		// 7. Publish above route
		audioControl.click(AudioRoutesInfo.PUBLISH);
		String dts_id = appDeviceControl.getTextByXpath(AudioRoutesInfo.UUID);
		// 8. Download “Complete Profile”  link
//		String completeProfile = reportsControl.getPath(AudioRoutesInfo.COMPLETE_PROFILE);
		audioControl.downloadFile(AudioRoutesInfo.COMPLETE_PROFILE);
		// Verify that Complete Profile will include route type AR_OUT_USB
		Assert.assertTrue(audioControl.checkRouteFile(dts_id,"AR_OUT_USB"));
		Assert.assertTrue(audioControl.checkRouteFile(dts_id,"AR_OUT_LINEOUT"));
		Assert.assertTrue(audioControl.checkRouteFile(dts_id,"AR_OUT_BLUETOOTH"));
		
	}
	
	/*
	 * Verify that  "Audio Route ID" section displays correctly
	 */
	@Test
	public void TC615DAD_07() {
		audioControl.addLog("ID : TC615DAD_07 : Verify that  'Audio Route ID' section displays correctly");
		
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Audio" page
			3. Select a Standard Accessory audio route
			VP: Verify that “Audio Route ID” section displays ""Line-out0, Bluetooth0, USB"" as read only

	
		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Audio" page
		audioControl.click(PageHome.linkAudioroutes);
		// 3. Select a Standard Accessory audio route
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Earbuds.getName());
		// VP: Verify that “Audio Route ID” section displays ""Line-out0, Bluetooth0, USB"" as read only
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.AUDIO_ROUTE_ID), AudioRoutesInfo.AudiRouteID);
		Assert.assertFalse(audioControl.canEdit(AudioRoutesInfo.AUDIO_ROUTE_ID));
	}
	
	/*
	 * Verify that Complete Profile of Standard Accessory includes both Eagle Version 1.4 and 2.0
	 */
	@Test
	public void TC615DAD_08() {
		audioControl.addLog("ID : TC615DAD_08 : Verify that Complete Profile of Standard Accessory includes both Eagle Version 1.4 and 2.0");
		
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Audio" page
			3. Select “Over Ear Headphones” route in Standard Accessory section
			4. Click "Edit Version" link
			5. Upload valid dtscs combined file
			VP: Verify that a dtscs file is uploaded successfully
			6. Click “Save” link
			7. Publish above route
			8. Download “Complete Profile”  link
			VP: Verify that Complete Profile will includes both Eagle Version 1.4 and 2.0
	
		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Audio" page
		audioControl.click(PageHome.linkAudioroutes);
		// 3. Select “Over Ear Headphones” route in Standard Accessory section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		// 4. Click "Edit Version" link
		audioControl.editVersion();
		// 5. Upload valid dtscs combined file
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Over_Ear_Headphones_Combined.getName());
		// VP: Verify that a dtscs file is uploaded successfully
		// 6. Click “Save” link
		audioControl.click(AudioRoutesEdit.SAVE);
		String UUID = audioControl.getTextByXpath(AudioRoutesInfo.UUID);
		// 7. Publish above route
		audioControl.click(AudioRoutesInfo.PUBLISH);
		// 8. Download “Complete Profile”  link
		Assert.assertTrue(audioControl.downloadFile(AudioRoutesInfo.COMPLETE_PROFILE));
		// VP: Verify that Complete Profile will includes both Eagle Version 1.4 and 2.0
		Assert.assertTrue(productControl.isIncludeTwoEagleVersion(UUID));
	}
	
	/*
	 * Verify that the uploaded tuning has data Eagle 1.4, Complete Profile of Internal Speaker only include Eagle settings data 1.4
	 */
	@Test
	public void TC615DAD_09() {
		audioControl.addLog("ID : TC615DAD_09 : Verify that the uploaded tuning has data Eagle 1.4, Complete Profile of Internal Speaker only include Eagle settings data 1.4");
		
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Audio" page
			3. Select “Attached0 - Internal Speakers (Default)” route in Other Audio Routes section
			4. Click "Edit Version" link
			5. Upload valid dtscs file contains data Eagle 1.4
			VP: Verify that a dtscs file is uploaded successfully
			6. Click “Save” link
			7. Publish above route
			8. Download “Complete Profile”  link
			VP: Verify that the Complete Profile only include Eagle settings data 1.4
	
		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Audio" page
		audioControl.click(PageHome.linkAudioroutes);
		// 3. Select “Attached0 - Internal Speakers (Default)” route in Other Audio Routes section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Other_Routes.Attached0_Internal_Speakers.getName());
		// 4. Click "Edit Version" link
		audioControl.editVersion();
		// 5. Upload valid dtscs file contains data Eagle 1.4
		audioControl.uploadFileTuning(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Attached0InternalSpeakersDefault_Fallback.getName());
		// VP: Verify that a dtscs file is uploaded successfully
		// 6. Click “Save” link
		audioControl.click(AudioRoutesEdit.SAVE);
		String UUID = audioControl.getTextByXpath(AudioRoutesInfo.UUID);
		// 7. Publish above route
		audioControl.click(AudioRoutesInfo.PUBLISH);
		// 8. Download “Complete Profile”  link
		Assert.assertTrue(audioControl.downloadFile(AudioRoutesInfo.COMPLETE_PROFILE));
		// VP: Verify that the Complete Profile only include Eagle settings data 1.4
		Assert.assertEquals(audioControl.checkEalgeVersion(UUID), "Eagle 1.4");
	}
	/*
	 * Verify that "Audio Route ID" section Displays correctly
	 */
	@Test
	public void TC615DAD_10() {
		audioControl.addLog("ID : TC615DAD_10 : Verify that Audio Route ID section Displays correctly");
		
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Audio" page
			3. Select one by one Attached 6, Attached 7 and Attached 8 on ''Other Audio Routes''
			4. Click "Edit Version" link
			VP: Verify that “Audio Route ID” section displays "Attached 6, "Attached 7 ,Attached 8''As read only
		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Audio" page
		audioControl.click(PageHome.linkAudioroutes);
		// 3. Select “Attached6 - Internal Speakers (mode5)” route in Other Audio Routes section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Other_Routes.Attached6_Internal_Speakers.getName());
		// 4. Click "Edit Version" link
		audioControl.editVersion();
		// VP: Verify that “Audio Route ID” section displays "Attached 6, "Attached 7 ,Attached 8''As read only
		String other_new = audioControl.getTextByXpath(AudioRoutesEdit.AUDIO_ROUTE_ID);
		Assert.assertTrue(other_new.contains("Attached6"));
	}
	/*
	 * Verify that "Verify that Complete Profile of Attached 6,7,8 in “Other Audio Route” section will contain valid data
	 */
	@Test
	public void TC615DAD_11() {
		audioControl.addLog("ID : TC615DAD_11 : Verify that Complete Profile of Attached 6,7,8 in “Other Audio Route” section will contain valid data");
		
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Audio" page
			3. Select “Attached 6” route in Other Audio Route
			4. Download “Complete Profile” link
			VP: Verify that Complete Profile will include Route type AR_OUT_ATTACHED6
			5. Repeat step 3 and 4 with select ''Attached 7, Attached 8''
			VP: Verify that Complete Profile will include Route type AR_OUT_ATTACHED7, AR_OUT_ATTACHED8
		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Audio" page
		audioControl.click(PageHome.linkAudioroutes);
		// 3. Select “Attached6 - Internal Speakers (mode5)” route in Other Audio Routes section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Other_Routes.Attached6_Internal_Speakers.getName());
		String dts_id = appDeviceControl.getTextByXpath(AudioRoutesInfo.UUID);
		// 4. Download “Complete Profile” link
		audioControl.downloadFile(AudioRoutesInfo.COMPLETE_PROFILE);
		// Verify that Complete Profile will include route type AR_OUT_ATTACHED6
		Assert.assertTrue(audioControl.checkRouteFile(dts_id,"AR_OUT_ATTACHED6"));
		// 5. Repeat step 3 and 4 with select ''Attached 7, Attached 8''
		// Navigate to "Audio" page
		audioControl.click(PageHome.linkAudioroutes);
		// Select “Attached7 - Internal Speakers (mode6)” route in Other Audio Routes section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Other_Routes.Attached7_Internal_Speakers.getName());
		String dts_id_1 = appDeviceControl.getTextByXpath(AudioRoutesInfo.UUID);
		// Download “Complete Profile” link
		audioControl.downloadFile(AudioRoutesInfo.COMPLETE_PROFILE);
		// Verify that Complete Profile will include route type AR_OUT_ATTACHED7
		Assert.assertTrue(audioControl.checkRouteFile(dts_id_1,"AR_OUT_ATTACHED7"));

		// Navigate to "Audio" page
		audioControl.click(PageHome.linkAudioroutes);
		// Select “Attached8 - Internal Speakers (mode7)” route in Other Audio Routes section
		audioControl.selectAnAudioRouteByName(AudioRoutes.Other_Routes.Attached8_Internal_Speakers.getName());
		String dts_id_2 = appDeviceControl.getTextByXpath(AudioRoutesInfo.UUID);
		// 4. Download “Complete Profile” link
		audioControl.downloadFile(AudioRoutesInfo.COMPLETE_PROFILE);
		// Verify that Complete Profile will include route type AR_OUT_ATTACHED8
		Assert.assertTrue(audioControl.checkRouteFile(dts_id_2,"AR_OUT_ATTACHED8"));
		
	}
	
}
