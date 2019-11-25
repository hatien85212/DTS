package com.dts.adminportal.dts.test;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AudioRoutes;
import com.dts.adminportal.model.AudioRoutesEdit;
import com.dts.adminportal.model.AudioRoutesInfo;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.RoomModelEdit;
import com.dts.adminportal.util.DateUtil;
import com.dts.adminportal.util.FileUtil;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.StringUtil;

public class DTSUserAudioRoutes615DAudioRouteDetail extends BasePage{

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
	}
	
	@BeforeMethod
	public void loginBeforeTest() {
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
	}
	
	/*
	 * Verify that the Display Name section displays the audio route name with appropriate languages.
	 * Verify the Audio Route Detail page 
	 * Verify that "Edit Version","View Published Version" link is displayed and "Notes" Section is displayed as readonly
	 * Verify that 621D Room Model Edit Page is displayed when clicking on "Edit Version" link
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
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Select an audio route item
		audioControl.selectAnAudioRouteByName(AudioRoutes.STANDARD_ROUTES[0]);
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
		audioControl.uploadFile(AudioRoutesEdit.ADD_IMAGE250, Constant.IMG_DRAG_DROP_AREA[1]);
		audioControl.uploadFile(AudioRoutesEdit.ADD_IMAGE500, Constant.IMG_DRAG_DROP_AREA[2]);
		audioControl.uploadFile(AudioRoutesEdit.ADD_IMAGE1000, Constant.IMG_DRAG_DROP_AREA[3]);
		// 8. Upload new tuning file
		// Delete tuning file if exist
		// 9. Click "Save" link
		audioControl.uploadFileTuning(RoomModelEdit.ADD_TUNING,AddEditProductModel.FileUpload.Over_Ear_Headphones.getName());
		audioControl.click(AudioRoutesEdit.SAVE);
		String audioname = audioControl.getTextByXpath(AudioRoutes.AUDIO_DETAIL_TUNING_FILE);
		// Get all languages name display
		ArrayList<String> displayedLanguages = audioControl.getAudioRouteName(AudioRoutesInfo.ALL_DISPLAY_NAME);
		/*
		 * Verify that The 615D Audio Route Detail page is displayed and the Display Name section is displayed with all languages
		 */
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutesInfo.DISPLAY_MODEL), AudioRoutes.STANDARD_ROUTES[0]);
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
		
		Assert.assertFalse(audioControl.canEdit(AudioRoutes.AUDIO_DETAILS_DISPLAY_NAME));
		Assert.assertFalse(audioControl.canEdit(AudioRoutes.AUDIO_DETAILS_ROUTE_ID));
		Assert.assertFalse(audioControl.canEdit(AudioRoutes.AUDIO_DETAILS_TYPE));
		Assert.assertFalse(audioControl.canEdit(AudioRoutes.AUDIO_DETAILS_INPUT_SPECIFICATIONS));
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
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Select a Stereo Room Model
		audioControl.selectAnAudioRouteByName(AudioRoutes.STEREO_ROOM_MODELS[0]);
		//VP: The 616D Room Model Detail page is displayed
		//VP:  The information in Salesforce ID, Name, Notes fields are displayed correctly.
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_UUID),"a7ef49b3-fadd-4269-9047-947987e5910c");
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_ID),"StereoRoom0");
		//VP: The Tuning File Link is displayed and the uploaded date is displayed next to "Upload Tuning" link.
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.AUDIO_DETAIL_TUNING_FILE));
		// 5. Click "Edit Version" link
		audioControl.editVersion();
//		VP: The 621D Room Model Edit page is displayed
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.EDIT_AUDIO_FORM));
		Assert.assertTrue(audioControl.isElementEditable(AudioRoutes.EDIT_AUDIO_SALESFORCE_ID));
		Assert.assertTrue(audioControl.isElementEditable(AudioRoutes.EDIT_AUDIO_DISPLAY_NAME));
		Assert.assertTrue(audioControl.isElementEditable(AudioRoutes.EDIT_AUDIO_DESCRITION_INPUT));
		//make sure file tuning is uploaded
		audioControl.uploadFileTuning(RoomModelEdit.ADD_TUNING,AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		audioControl.click(AudioRoutes.EDIT_AUDIO_SAVE);
		audioControl.editVersion();
//		6. Click "Cancel" Link
		audioControl.click(AudioRoutes.EDIT_AUDIO_CANCEL);
//		VP: The 616D Room Model Detail page is displayed
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_UUID),"a7ef49b3-fadd-4269-9047-947987e5910c");
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_ID),"StereoRoom0");
//		7. Click in "Tuning File" link
		// Click on download link
		audioControl.donwloadFilesuccess();
	}
	
	/*
	 * Verify the information in Stereo Room Model Detail page.
	 */
	@Test
	public void TC616DRD_02() {
		audioControl.addLog("ID : TC616DRD_01 : Verify the information in Stereo Room Model Detail page");
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
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Select a Stereo Room Model
		audioControl.click(AudioRoutes.MCROOM0);
		//VP: The 616D Room Model Detail page is displayed
		//VP:  The information in Salesforce ID, Name, Notes fields are displayed correctly.
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_UUID),"f08a4c48-0887-11e4-9191-0800200c9a25");
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_ID),"McRoom0");
		//VP: The Tuning File Link is displayed and the uploaded date is displayed next to "Upload Tuning" link.
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.AUDIO_DETAIL_TUNING_FILE));
		// 5. Click "Edit Version" link
		audioControl.editVersion();
//		VP: The 621D Room Model Edit page is displayed
		Assert.assertTrue(audioControl.isElementPresent(AudioRoutes.EDIT_AUDIO_FORM));
		Assert.assertTrue(audioControl.isElementEditable(AudioRoutes.EDIT_AUDIO_SALESFORCE_ID));
		Assert.assertTrue(audioControl.isElementEditable(AudioRoutes.EDIT_AUDIO_DISPLAY_NAME));
		Assert.assertTrue(audioControl.isElementEditable(AudioRoutes.EDIT_AUDIO_DESCRITION_INPUT));
		//make sure file tuning is uploaded
		audioControl.uploadFileTuning(RoomModelEdit.ADD_TUNING,AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		audioControl.click(AudioRoutes.EDIT_AUDIO_SAVE);
		audioControl.editVersion();
//		6. Click "Cancel" Link
		audioControl.click(AudioRoutes.EDIT_AUDIO_CANCEL);
//		VP: The 616D Room Model Detail page is displayed
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_UUID),"f08a4c48-0887-11e4-9191-0800200c9a25");
		Assert.assertEquals(audioControl.getTextByXpath(AudioRoutes.STEREO_ROOM_ID),"McRoom0");
//		7. Click in "Tuning File" link
		// Click on download link
		audioControl.donwloadFilesuccess();
	}
}
