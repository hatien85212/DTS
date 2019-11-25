package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BaseController;
import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AudioRoutes;
import com.dts.adminportal.model.AudioRoutesEdit;
import com.dts.adminportal.model.AudioRoutesInfo;
import com.dts.adminportal.model.DeviceInfo;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.RoomModelEdit;
import com.dts.adminportal.model.RoomModelInfo;
import com.thoughtworks.selenium.webdriven.commands.GetConfirmation;
import com.thoughtworks.selenium.webdriven.commands.IsConfirmationPresent;
import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;

public class DTSAudioRoutes621DRoomEdit extends BasePage{

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
	}
	
	/*
	 * Verify that the "Display Name" and  "UUID" and "Room ID" section are displayed as readonly in Stereo Room Model items
	 */
	@Test
	public void TC621DRE_01() {
		Reporter.log("Verify that the 'Display Name' and  'UUID' and 'Room ID' section are displayed as readonly in Stereo Room Model items");
		
		/*	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio" page
			4. Select a Stereo Room Model item
			VP: The 616D Room Model page is displayed
			5. Click "Edit" link
			VP: The 621D Room Model Edit page is displayed
			6. Try to edit  "Display Name" and  "UUID" and "Room Model ID" section
			VP: The "Display Name" and  "UUID" and "Room Model ID" section are displayed as readonly.

		*/
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Select a Stereo Room Model item
		audioControl.click(AudioRoutes.LINK_STEREO_ROOM);
		/*
		 * VP: The 616D Room Model page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(RoomModelInfo.getElementsInfo()),true);
		// 5. Click "Edit" link
		audioControl.editVersion();
		/*
		 * VP: The 621D Room Model Edit page is displayed
		 */
		Assert.assertEquals(true,audioControl.existsElement(RoomModelEdit.getElementsInfo()));
		// 6. Try to edit  "Display Name" and  "UUID" and "Room Model ID" section
		/*
		 * VP: The "Display Name" and  "UUID" and "Room Model ID" section are displayed as readonly.
		 */
		Assert.assertFalse(audioControl.canEdit(RoomModelEdit.DISPLAY_MODEL));
		Assert.assertFalse(audioControl.canEdit(RoomModelEdit.UUID));
		Assert.assertFalse(audioControl.canEdit(RoomModelEdit.ROOM_MODEL_ID));

	}
	
	/*
	 * Verify that the Stereo Room Model could be changed successfully.
	 */
	@Test
	public void TC621DRE_02() {
		Reporter.log("Verify that the Stereo Room Model could be changed successfully.");
		
		/*1. Navigate to DTS portal
		2. Log into DTS portal as a DTS user successfully
		3. Navigate to "Audio" page
		4. Select a Stereo Room Model item
		VP: The 616D Room Model Detail page is displayed
		5. Click Edit link
		VP: The 621D Room Model Eitd page is displayed
		6. Change The new value in Salesforce ID, Name, Notes fields
		7. Click "Save" link
		VP: The portal is redirected to 616D Audio Route Detail page and the Salesforce ID, Name, Notes fields are changed successfully
		*/

		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Select a Stereo Room Model item
		audioControl.click(AudioRoutes.LINK_STEREO_ROOM);
		/*
		 * VP: The 616D Room Model Detail page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(RoomModelInfo.getElementsInfo()),true);
		// 5. Click Edit link
		audioControl.editVersion();
		/*
		 * VP: The 621D Room Model Eitd page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(RoomModelEdit.getElementsInfo()),true);
		// 6. Change The new value in Salesforce ID, Name, Notes fields
		String saleforce = RandomStringUtils.randomNumeric(10);
		audioControl.editData(RoomModelEdit.SALESFORCE_ID,saleforce);
		String display_name = RandomStringUtils.randomNumeric(10);
		audioControl.editData(RoomModelEdit.DISPLAY_NAME,display_name);
		String notes = RandomStringUtils.randomNumeric(10);
		audioControl.editData(RoomModelEdit.NOTES,notes);
		// 7. Click "Save" link
		audioControl.click(RoomModelEdit.SAVE);
		/*
		 * VP: The portal is redirected to 616D Audio Route Detail page and the Salesforce ID, Name, Notes fields are changed successfully
		 */
		Assert.assertEquals(audioControl.existsElement(RoomModelInfo.getElementsInfo()),true);
		Assert.assertTrue(audioControl.getTextByXpath(RoomModelInfo.SALESFORCE_ID).contains(saleforce));
		Assert.assertTrue(audioControl.getTextByXpath(RoomModelInfo.DISPLAY_NAME).contains(display_name));
		Assert.assertTrue(audioControl.getTextByXpath(RoomModelInfo.NOTES).contains(notes));
		
		//Set default name back
		audioControl.click(RoomModelInfo.EDIT);
		audioControl.editData(RoomModelEdit.DISPLAY_NAME,"Wide");
		audioControl.click(RoomModelEdit.SAVE);
		
	}
	
	/*
	 * Verify that the new change of Stereo Room Model is not saved when clicking "Cancel" link of "Actions" module.
	 */
	@Test
	public void TC621DRE_03() {
		Reporter.log("Verify that the new change of Stereo Room Model is not saved when clicking 'Cancel' link of 'Actions' module.");
		
		/*1. Navigate to DTS portal
		2. Log into DTS portal as a DTS user successfully
		3. Navigate to "Audio" page
		4. Select a Stereo Room Model item
		VP: The 616D Room Model Detail page is displayed
		5. Click Edit link
		VP: The 621D Room Model Edit page is displayed
		6. Upload a tuning file successfully.
		7. Click "Cancel" link of "Actions" module
		VP: The portal is redirected to 616D Room Model Detail page and the tuning file is not added.

		*/
		
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Select a Stereo Room Model item
		audioControl.click(AudioRoutes.LINK_STEREO_ROOM);
		/*
		 * VP: The 616D Room Model Detail page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(RoomModelInfo.getElementsInfo()),true);
		// Get value
		String old_value = audioControl.getTextByXpath(RoomModelInfo.ROUTER_TUNING);
		// 5. Click Edit link
		audioControl.editVersion();
		/*
		 * VP: The 621D Room Model Edit page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(RoomModelEdit.getElementsInfo()),true);
		// 6. Upload a tuning file successfully.
		audioControl.uploadFileTuning(RoomModelEdit.ADD_TUNING,AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		// 7. Click "Cancel" link of "Actions" module
		audioControl.click(RoomModelEdit.CANCEL);
		/*
		 * VP: The portal is redirected to 616D Room Model Detail page and the tuning file is not added.
		 */
		Assert.assertEquals(audioControl.getTextByXpath(RoomModelInfo.ROUTER_TUNING),old_value);

	}
	
	/*
	 * Verify that the invalid tuning file could not be uploaded in Stereo Room Model item
	 */
	@Test
	public void TC621DRE_04() {
		Reporter.log("ID : TC621DRE_04 : Verify that the invalid tuning file could not be uploaded in Stereo Room Model item");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select a Stereo Room Model item
			VP: The 616D Room Model Detail page is displayed
			5. Click "Edit" link
			VP: The 621D Room Model page is displayed
			6. Upload an invalid tuning file
			  VP: An error message is displayed and the invalid tuning file is not added.

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Select a Stereo Room Model item
		audioControl.click(AudioRoutes.STEREO_ROOM0);
		/*
		 * VP: The 616D Room Model Detail page is displayed
		 */
		Assert.assertEquals(true,audioControl.existsElement(RoomModelInfo.getElementsInfo()));
		// 5. Click on Edit Version link
		audioControl.editVersion();
		// VP: The 621D Room Model page is displayed
		Assert.assertEquals(audioControl.existsElement(RoomModelEdit.getElementsInfo()),true);
		//6. Upload an invalid tuning file
		audioControl.doDelete(AudioRoutesEdit.DELETE_TUNING_ICON);
		audioControl.uploadFile(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		/*
		 * VP: An error message is displayed and the invalid tuning file is not added.
		 */
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.AUDIOROUTE_UPLOAD_MESSAGE).contains(AudioRoutesEdit.UPLOAD_FILE_MESSSAGE[1]));
		
		
	}
	/*
	 * Verify that the tuning file could be downloaded when clicking on tuning link in Stereo Room Model item.
	 */
	
	@Test
	public void TC621DRE_05(){
		audioControl.addLog("ID : TC621DRE_05 : Verify that the tuning file could be downloaded when clicking on tuning link in Stereo Room Model item." );
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio Routes" page
			4. Select a Stereo Room Model item
			VP: The 616D Room Model Detail page is displayed
			5. Click Edit link
			VP: The 621D Room Model page is displayed
			6. Upload a tuning file successfully
			7. Click on above tuning link after uploaded
			8. Try to download the tuning file
			VP: The tuning file could be download successfully.
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio Routes" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Select a Stereo Room Model item
		audioControl.click(AudioRoutes.STEREO_ROOM0);
		/*
		 * VP: The 616D Room Model Detail page is displayed
		 */
		Assert.assertEquals(true,audioControl.existsElement(RoomModelInfo.getElementsInfo()));
		// 5. Click Edit link
		audioControl.editVersion();
		//VP: The 621D Room Model page is displayed
		Assert.assertEquals(audioControl.existsElement(RoomModelEdit.getElementsInfo()),true);
		// 6. Upload a tuning file successfully
		audioControl.uploadFileTuning(RoomModelEdit.ADD_TUNING,AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		// 7. Click on above tuning link after uploaded
		// 8. Try to download the tuning file
		/*
		 * The tuning file could be download successfully
		 */
		audioControl.donwloadFilesuccess();
	}

	/*
	 * Verify that the tuning file could be deleted successfully when clicking "Delete" button in delete confirmation dialog in Stereo Room Model item
	 */
	@Test
	public void TC621DRE_06() {
		Reporter.log("ID : TC621DRE_06 : Verify that the tuning file could be deleted successfully when clicking Delete button in delete confirmation dialog in Stereo Room Model item");
		
		/*	
		 *  1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio" page
			4. Select a Stereo Room Model item
			VP: The 616D Room Model Detail page is displayed
			5. Click "Edit" link
			VP: The 621D Room Model page is displayed
			6. Upload a tuning file successfully
			7. Click on Trash can icon of tuning section
			VP: Verify that the delete confirmation dialog is displayed
			8. Click "Cancel" button on delete confirmation dialog
			VP: The tuning file still remains.
			9. Click "Delete" button on delete confirmation dialog
			VP: the tuning file is deleted successfully
		*/
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Select a Stereo Room Model item
		audioControl.click(AudioRoutes.LINK_STEREO_ROOM);
		/*
		 * VP: The 616D Room Model page is displayed
		 */
		Assert.assertEquals(true,audioControl.existsElement(RoomModelInfo.getElementsInfo()));
		// 5. Click "Edit" link
		audioControl.editVersion();
		/*
		 * VP: The 621D Room Model Edit page is displayed
		 */
		Assert.assertEquals(true,audioControl.existsElement(RoomModelEdit.getElementsInfo()));
		// 6. Upload a tuning file successfully
		audioControl.uploadFileTuning(RoomModelEdit.ADD_TUNING,AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		// 7. Click on Trash can icon of tuning section
		WebElement delete_icon = driver.findElement(By.xpath("//*[contains(@id,'tuning-fileupload-')]/table/tbody/tr[1]/td[2]/img"));
		String Namefirst = driver.findElement(By.xpath("//*[contains(@id,'tuning-fileupload-')]/table/tbody/tr[2]/td")).getText();
		audioControl.click(delete_icon);		
		// VP: Verify that the delete confirmation dialog is displayed
		audioControl.isElementPresent(PageHome.BTN_CONFIRMATION_CANCEL);
		// 8. Click "Cancel" button on delete confirmation dialog
		audioControl.selectConfirmationDialogOption("Cancel");
		// VP: The tuning file still remains.
		String Namelater = driver.findElement(By.xpath("//*[contains(@id,'tuning-fileupload-')]/table/tbody/tr[2]/td")).getText();
		Assert.assertEquals(Namefirst, Namelater);
		// 9. Click "Delete" button on delete confirmation dialog
		audioControl.click(delete_icon);
		audioControl.selectConfirmationDialogOption("Delete");
		// VP: the tuning file is deleted successfully
		Assert.assertTrue(audioControl.isElementPresent(RoomModelEdit.ADD_TUNING));
	}
	
	
	/*
	 * Verify that the Display Name and  UUID and Room ID section are displayed as readonly in Multi-Channel Room Models
	 */
	@Test
	public void TC621DRE_07() {
		Reporter.log("ID: 621DRE_07: Verify that the Display Name and  UUID and Room ID section are displayed as readonly in Multi-Channel Room Models");
		/*	
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio" page
			4. Select a Multi-Channel Room Model item
			VP: The 616D Room Model page is displayed
			5. Click "Edit" link
			VP: The 621D Room Model Edit page is displayed
			6. Try to edit  "Display Name" and  "UUID" and "Room Model ID" section
			VP: The "Display Name" and  "UUID" and "Room Model ID" section are displayed as readonly.
		*/
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Select a Multi-Channel Room Model item
		audioControl.click(AudioRoutes.MCROOM1);
		/*
		 * VP: The 616D Room Model page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(RoomModelInfo.getElementsInfo()),true);
		// 5. Click "Edit" link
		audioControl.editVersion();
		/*
		 * VP: The 621D Room Model Edit page is displayed
		 */
		Assert.assertEquals(true,audioControl.existsElement(RoomModelEdit.getElementsInfo()));
		// 6. Try to edit  "Display Name" and  "UUID" and "Room Model ID" section
		/*
		 * VP: The "Display Name" and  "UUID" and "Room Model ID" section are displayed as readonly.
		 */
		Assert.assertFalse(audioControl.canEdit(RoomModelEdit.DISPLAY_MODEL));
		Assert.assertFalse(audioControl.canEdit(RoomModelEdit.UUID));
		Assert.assertFalse(audioControl.canEdit(RoomModelEdit.ROOM_MODEL_ID));
	}
	/*
	 * Verify that the Multi-Channel Room Model could be changed successfully.
	 */
	@Test
	public void TC621DRE_08() {
		Reporter.log("ID: 621DRE_08: Verify that the Multi-Channel Room Model could be changed successfully.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio" page
			4. Select a Multi-Channel Room Model item
			VP: The 616D Room Model page is displayed
			5. Click Edit link
			VP: The 621D Room Model Edit page is displayed
			6. Change The new value in Salesforce ID, Name, Notes fields
			7. Click "Save" link
		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Select a Multi-Channel Room Model item
		audioControl.click(AudioRoutes.MCROOM1);
		/*
		 * VP: The 616D Room Model page is displayed
		 */
		Assert.assertEquals(true,audioControl.existsElement(RoomModelInfo.getElementsInfo()));
		// 5. Click "Edit" link
		audioControl.editVersion();
		/*
		 * VP: The 621D Room Model Edit page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(RoomModelEdit.getElementsInfo()), true);
		
		// 6. Change The new value in Salesforce ID, Name, Notes fields
		String salesforce = RandomStringUtils.randomNumeric(10);
		audioControl.editData(RoomModelEdit.SALESFORCE_ID, salesforce);
		String name = RandomStringUtils.randomNumeric(10);
		audioControl.editData(RoomModelEdit.DISPLAY_NAME, name);
		String notes = RandomStringUtils.randomNumeric(10);
		audioControl.editData(RoomModelEdit.NOTES, notes);
		// 7. Click "Save" link
		audioControl.click(RoomModelEdit.SAVE);
		
		Assert.assertEquals(audioControl.existsElement(RoomModelInfo.getElementsInfo()), true);
		/*
		 * Set default name back
		 */	
		audioControl.editVersion();
		audioControl.editData(RoomModelEdit.DISPLAY_NAME, "wide");
		audioControl.click(RoomModelEdit.SAVE);		
	}
	/*
	 * Verify that the new change of Room Model is not saved when clicking "Cancel" link of "Actions" module in Multi-Channel Room Models
	 */
	@Test
	public void TC621DRE_09() {
		Reporter.log("ID: 621DRE_09: Verify that the new change of Room Model is not saved when clicking Cancel link of Actions module in Multi-Channel Room Models");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio" page
			4. Select a Multi-Channel Room Model item
			VP: The 616D Room Model page is displayed
			5. Click Edit link
			VP: The 621D Room Model Edit page is displayed
			6. Upload a tuning file successfully.
			7. Click "Cancel" link of "Actions" module
		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Select a Multi-Channel Room Model item
		audioControl.click(AudioRoutes.MCROOM1);
		/*
		 * VP: The 616D Room Model page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(RoomModelInfo.getElementsInfo()), true);
		// 5. Click Edit link
		audioControl.editVersion();
		/*
		 * VP: The 621D Room Model Edit page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(RoomModelEdit.getElementsInfo()), true);
		// 6. Upload a tuning file successfully.
		audioControl.uploadFileTuning(RoomModelEdit.ADD_TUNING,AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		// 7. Click "Cancel" link of "Actions" module
		audioControl.click(RoomModelEdit.CANCEL);
	}
	/*
	 * Verify that the invalid tuning file could not be uploaded in Multi-Channel Room Model item
	 */
	@Test
	public void TC621DRE_10() {
		Reporter.log("ID: 621DRE_10: Verify that the invalid tuning file could not be uploaded in Multi-Channel Room Model item");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio" page
			4. Select a Multi-Channel Room Model item
			VP: The 616D Room Model page is displayed
			5. Click "Edit" link
			VP: The 621D Room Model Edit page is displayed
			6. Upload an invalid tuning file
		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Select a Multi-Channel Room Model item
		audioControl.click(AudioRoutes.MCROOM1);
		/*
		 * VP: The 616D Room Model page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(RoomModelInfo.getElementsInfo()), true);
		// 5. Click Edit link
		audioControl.editVersion();
		/*
		 * VP: The 621D Room Model Edit page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(RoomModelEdit.getElementsInfo()), true);
		// 6. Upload an invalid tuning file
		audioControl.doDelete(AudioRoutesEdit.DELETE_TUNING_ICON);
		audioControl.uploadFile(AudioRoutesEdit.ADD_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		/*
		 * VP: An error message is displayed and the invalid tuning file is not added.
		 */
		Assert.assertTrue(audioControl.getTextByXpath(AudioRoutesEdit.AUDIOROUTE_UPLOAD_MESSAGE).contains(AudioRoutesEdit.UPLOAD_FILE_MESSSAGE[1]));
		
}
	/*
	 * Verify that the tuning file could be downloaded when clicking on tuning link in Multi-Channel Room Model item
	 */
	@Test
	public void TC621DRE_11() {
		Reporter.log("ID: 621DRE_11: Verify that the tuning file could be downloaded when clicking on tuning link in Multi-Channel Room Model item");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Audio" page
			4. Select a Multi-Channel Room Model item
			VP: The 616D Room Model page is displayed
			5. Click Edit link
			VP: The 621D Room Model Edit page is displayed
			6. Upload a tuning file successfully
			7. Click on above tuning link after uploaded
			8. Try to download the tuning file
		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Select a Multi-Channel Room Model item
		audioControl.click(AudioRoutes.MCROOM0);
		/*
		 * VP: The 616D Room Model page is displayed
		 */
		Assert.assertEquals(true,audioControl.existsElement(RoomModelInfo.getElementsInfo()));
		// 5. Click Edit link
		audioControl.editVersion();
		/*
		 * VP: The 621D Room Model Edit page is displayed
		 */
		Assert.assertEquals(audioControl.existsElement(RoomModelEdit.getElementsInfo()), true);
		// 6. Upload a tuning file successfully
		audioControl.uploadFileTuning(RoomModelEdit.ADD_TUNING,AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		// 7. Click on above tuning link after uploaded
		// 8. Try to download the tuning file
		/*
		 * The tuning file could be download successfully
		 */
		audioControl.donwloadFilesuccess();
	}
	
	
	/*
	 * Verify that the tuning file could be deleted successfully when clicking "Delete" button 
	 * in delete confirmation dialog in Multi-Channel Room Model item
	 */
	
	@Test
	public void TC621DRE_12() {
		Reporter.log("Verify that the tuning file could be deleted successfully when clicking 'Delete' button in delete confirmation dialog in Multi-Channel Room Model item");
	
		/*1. Navigate to DTS portal
		2. Log into DTS portal as a DTS user successfully
		3. Navigate to "Audio" page
		4. Select a Multi-Channel Room Model item
		VP: The 616D Room Model Detail page is displayed
		5. Click "Edit" link
		VP: The 621D Room Model page is displayed
		6. Upload a tuning file successfully
		7. Click on Trashcan icon of tuning secion
		VP: Verify that the delete confirmation dialog is displayed
		8. Click "Cancel" button on delete confirmation dialog
		VP: The tuning file still remains
		9. Click "Delete" button on delete confirmation dialog
		VP: The tuning file is deleted successfully
		*/
		
		//2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//3. Navigate to "Audio" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		//4. Select a Multi-Channel Room Model item
		audioControl.click(AudioRoutes.LINK_MCROOM);
		/*
		 * VP: The 616D Room Model Detail page is displayed
		 */
		Assert.assertTrue(audioControl.existsElement(RoomModelInfo.getElementsInfo()));
		//5. Click "Edit" link
		audioControl.editVersion();
		/*
		 * VP: The 621D Room Model page is displayed
		 */
		Assert.assertTrue(audioControl.existsElement(RoomModelEdit.getElementsInfo()));
		//6. Upload a tuning file successfully
		audioControl.uploadFileTuning(RoomModelEdit.ADD_TUNING,AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		//7. Click on Trashcan icon of tuning secion
		WebElement delete_icon = driver.findElement(By.xpath("//*[contains(@id,'tuning-fileupload-')]/table/tbody/tr[1]/td[2]/img"));
		audioControl.click(delete_icon);
		audioControl.waitForLoadElement(PageHome.BTN_CONFIRMATION_DANGER);
		/*
		 * VP: Verify that the delete confirmation dialog is displayed
		 */
		//Assert.assertTrue(audioControl.isElementPresent(PageHome.BTN_CONFIRMATION_DANGER));
		//8. Click "Cancel" button on delete confirmation dialog
		audioControl.selectConfirmationDialogOption("Cancel");
		/*
		 * VP: The tuning file still remains
		 */
		WebElement tuning_file = driver.findElement(By.xpath("//*[contains(@id,'tuning-fileupload-')]/table/tbody/tr[2]/td"));
		Assert.assertTrue(audioControl.isElementPresent(tuning_file));
		//9. Click "Delete" button on delete confirmation dialog
		audioControl.click(delete_icon);
		audioControl.waitForLoadElement(PageHome.BTN_CONFIRMATION_DANGER);
		audioControl.selectConfirmationDialogOption("Delete");
		/*
		 * VP: The tuning file is deleted successfully
		 */
		Assert.assertTrue(audioControl.isElementPresent(RoomModelEdit.ADD_TUNING));

	}
	
	/*
	 * Verify that the "Cancel" link work well in the 621D Stereo Room Model Item 
	 */
	@Test
	public void TC621DRE_13() {
		Reporter.log("Verify that the 'Cancel' link work well in the 621D Stereo Room Model Item ");
	
		/*
		Pre-Condition:The Room Model has uploaded tuning file
		1. Navigate to DTS portal.
		2. Log into DTS portal as a DTS user successfully.
		3. Navigate to "Audio" page.
		4. Select a stereo room model item.
		VP: The 616D Audio Route Detail page is displayed.
		5. Click "Edit Version" link.
		VP: The 621D Room Model Edit Page is displayed.
		6. Click on "Cancel" button in the 621D Room Model Edit Page .
		VP: The 616D Audio Route Detail page is displayed.
		7. Click "Edit Version" link.
		8. Click on Trashcan icon of tuning section.
		9. Click "Delete" button on delete confirmation dialog.
		VP: The tuning file is deleted successful in the 621D Room Model Edit Page.
		10. Click on "Cancel" button in the 621D Room Model Edit Page .
		VP: The tunning file is displayed in the 616D Audio Route Detail page .
		11. Repeat from step 7 to step 9.
		12. Upload a valid tuning file.
		VP: The valid tuning file is uploaded successfully.
		13. Click on "Cancel" button in the 621D Room Model Edit Page .
		VP: The old tunning file is displayed in the 616D Audio Route Detail page.
		14. Repeat step 11 and 12.
		15. Refresh the current page.
		16. Log into DTS portal as a DTS user successfully.
		17. Navigate to "Audio" page.
		18. Select the stereo room model item above.
		VP: The old tunning file is displayed in the 616D Audio Route Detail page.

		*/
		//Pre-Condition:The Room Model has uploaded tuning file
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		audioControl.click(AudioRoutes.LINK_STEREO_ROOM);
		audioControl.editVersion();
		audioControl.uploadFileTuning(RoomModelEdit.ADD_TUNING,AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		audioControl.click(RoomModelEdit.SAVE);
		//3. Navigate to "Audio" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		//4. Select a stereo room model item.
		audioControl.click(AudioRoutes.LINK_STEREO_ROOM);
		//Get old tunning file
		String old_tuning = audioControl.getTextByXpath(RoomModelInfo.ROUTER_TUNING);
		/*
		 * VP: The 616D Audio Route Detail page is displayed.
		 */
		Assert.assertTrue(audioControl.existsElement(RoomModelInfo.getElementsInfo()));
		//5. Click "Edit Version" link.
		audioControl.editVersion();
		/*
		 * VP: The 621D Room Model Edit Page is displayed.
		 */
		Assert.assertTrue(audioControl.existsElement(RoomModelEdit.getElementsInfo()));
		//6. Click on "Cancel" button in the 621D Room Model Edit Page .
		audioControl.click(RoomModelEdit.CANCEL);
		/*
		 * VP: The 616D Audio Route Detail page is displayed.
		 */
		Assert.assertTrue(audioControl.existsElement(RoomModelInfo.getElementsInfo()));
		//7. Click "Edit Version" link.
		audioControl.editVersion();
		//8. Click on Trashcan icon of tuning section.
		WebElement delete_icon = driver.findElement(By.xpath("//*[contains(@id,'tuning-fileupload-')]/table/tbody/tr[1]/td[2]/img"));
		audioControl.click(delete_icon);
		//9. Click "Delete" button on delete confirmation dialog.
		audioControl.selectConfirmationDialogOption("Delete");
		/*
		 * VP: The tuning file is deleted successful in the 621D Room Model Edit Page.
		 */
		Assert.assertTrue(audioControl.isElementPresent(RoomModelEdit.ADD_TUNING));
		//10. Click on "Cancel" button in the 621D Room Model Edit Page .
		audioControl.click(RoomModelEdit.CANCEL);
		/*
		 * VP: The tunning file is displayed in the 616D Audio Route Detail page .
		 */
		Assert.assertEquals(audioControl.getTextByXpath(RoomModelInfo.ROUTER_TUNING),old_tuning);
		//11. Repeat from step 7 to step 9.
		//12. Upload a valid tuning file.
		audioControl.editVersion();
		audioControl.uploadFileTuning(RoomModelEdit.ADD_TUNING,AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		/*
		 * VP: The valid tuning file is uploaded successfully.
		 */
		Assert.assertTrue(driver.findElement(By.partialLinkText(".dtscs")).isDisplayed());
		//13. Click on "Cancel" button in the 621D Room Model Edit Page .
		audioControl.click(RoomModelEdit.CANCEL);
		/*
		 * VP: The old tunning file is displayed in the 616D Audio Route Detail page.
		 */
		Assert.assertEquals(audioControl.getTextByXpath(RoomModelInfo.ROUTER_TUNING),old_tuning);
		//14. Repeat step 11 and 12.
		//15. Refresh the current page.
		//16. Log into DTS portal as a DTS user successfully.
		//17. Navigate to "Audio" page.
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		//18. Select the stereo room model item above.
		audioControl.click(AudioRoutes.LINK_STEREO_ROOM);
		/*
		 * VP: The old tunning file is displayed in the 616D Audio Route Detail page.
		 */
		Assert.assertEquals(audioControl.getTextByXpath(RoomModelInfo.ROUTER_TUNING),old_tuning);


	}
	
	/*
	 * Verify that the "Cancel" link work well in the 621D Multi-Channel Room Model Item 
	 */
	@Test
	public void TC621DRE_14() {

		Reporter.log("Verify that the 'Cancel' link work well in the 621D Multi-Channel Room Model Item ");
		
		/*
		Pre-Condition:The Room Model has uploaded tuning file
		1. Navigate to DTS portal.
		2. Log into DTS portal as a DTS user successfully.
		3. Navigate to "Audio" page.
		4. Select a multi-channel room model item.
		VP: The 616D Audio Route Detail page is displayed.
		5. Click "Edit Version" link.
		VP: The 621D Room Model Edit Page is displayed.
		6. Click on "Cancel" button in the 621D Room Model Edit Page .
		VP: The 616D Audio Route Detail page is displayed.
		7. Click "Edit Version" link.
		8. Click on Trashcan icon of tuning section.
		9. Click "Delete" button on delete confirmation dialog.
		VP: The tuning file is deleted successful in the 621D Room Model Edit Page.
		10. Click on "Cancel" button in the 621D Room Model Edit Page .
		VP: The tunning file is displayed in the 616D Audio Route Detail page .
		11. Repeat from step 7 to step 9.
		12. Upload a valid tuning file.
		VP: The valid tuning file is uploaded successfully.
		13. Click on "Cancel" button in the 621D Room Model Edit Page .
		VP: The old tunning file is displayed in the 616D Audio Route Detail page.
		14. Repeat step 11 and 12.
		15. Refresh the current page.
		16. Log into DTS portal as a DTS user successfully.
		17. Navigate to "Audio" page.
		18. Select the multi-channel room model item above.
		VP: The old tunning file is displayed in the 616D Audio Route Detail page.

		*/
		//Pre-Condition:The Room Model has uploaded tuning file
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		audioControl.click(AudioRoutes.LINK_MCROOM);
		audioControl.editVersion();
		audioControl.uploadFileTuning(RoomModelEdit.ADD_TUNING,AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		audioControl.click(RoomModelEdit.SAVE);
		//3. Navigate to "Audio" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		//4. Select a multi-channel room model item.
		audioControl.click(AudioRoutes.LINK_MCROOM);
		//Get old tunning file
		String old_tuning = audioControl.getTextByXpath(RoomModelInfo.ROUTER_TUNING);
		/*
		 * VP: The 616D Audio Route Detail page is displayed.
		 */
		Assert.assertTrue(audioControl.existsElement(RoomModelInfo.getElementsInfo()));
		//5. Click "Edit Version" link.
		audioControl.editVersion();
		/*
		 * VP: The 621D Room Model Edit Page is displayed.
		 */
		Assert.assertTrue(audioControl.existsElement(RoomModelEdit.getElementsInfo()));
		//6. Click on "Cancel" button in the 621D Room Model Edit Page .
		audioControl.click(RoomModelEdit.CANCEL);
		/*
		 * VP: The 616D Audio Route Detail page is displayed.
		 */
		Assert.assertTrue(audioControl.existsElement(RoomModelInfo.getElementsInfo()));
		//7. Click "Edit Version" link.
		audioControl.editVersion();
		//8. Click on Trashcan icon of tuning section.
		WebElement delete_icon = driver.findElement(By.xpath("//*[contains(@id,'tuning-fileupload-')]/table/tbody/tr[1]/td[2]/img"));
		audioControl.click(delete_icon);
		//9. Click "Delete" button on delete confirmation dialog.
		audioControl.selectConfirmationDialogOption("Delete");
		/*
		 * VP: The tuning file is deleted successful in the 621D Room Model Edit Page.
		 */
		Assert.assertTrue(audioControl.isElementPresent(RoomModelEdit.ADD_TUNING));
		//10. Click on "Cancel" button in the 621D Room Model Edit Page .
		audioControl.click(RoomModelEdit.CANCEL);
		/*
		 * VP: The tunning file is displayed in the 616D Audio Route Detail page .
		 */
		Assert.assertEquals(audioControl.getTextByXpath(RoomModelInfo.ROUTER_TUNING),old_tuning);
		//11. Repeat from step 7 to step 9.
		//12. Upload a valid tuning file.
		audioControl.editVersion();
		audioControl.uploadFileTuning(RoomModelEdit.ADD_TUNING,AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		/*
		 * VP: The valid tuning file is uploaded successfully.
		 */
		Assert.assertTrue(driver.findElement(By.partialLinkText(".dtscs")).isDisplayed());
		//13. Click on "Cancel" button in the 621D Room Model Edit Page .
		audioControl.click(RoomModelEdit.CANCEL);
		/*
		 * VP: The old tunning file is displayed in the 616D Audio Route Detail page.
		 */
		Assert.assertEquals(audioControl.getTextByXpath(RoomModelInfo.ROUTER_TUNING),old_tuning);
		//14. Repeat step 11 and 12.
		//15. Refresh the current page.
		//16. Log into DTS portal as a DTS user successfully.
		//17. Navigate to "Audio" page.
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		//18. Select the multi-channel room model item above.
		audioControl.click(AudioRoutes.LINK_MCROOM);
		/*
		 * VP: The old tunning file is displayed in the 616D Audio Route Detail page.
		 */
		Assert.assertEquals(audioControl.getTextByXpath(RoomModelInfo.ROUTER_TUNING),old_tuning);
	}
	
	/*
	 * Verify that the "Tunning" section is validated in "Stereo Room Model Edit Page"
	 */
	@Test
	public void TC621DRE_15() {
		Reporter.log("Verify that the 'Tunning' section is validated in 'Stereo Room Model Edit Page'");
		
		/*1. Navigate to DTS portal
		2. Log into DTS portal as a DTS user successfully
		3. Navigate to "Audio" page
		4. Select a stereo room model item
		5. Click "Edit Version" link
		6. Upload an invalid tuning file
		VP: Verify that the "Invalid File" message and Retry link are displayed
		7. Click "Retry" link in "Tuning" section
		8. Upload an valid tuning file.
		VP: The valid tuning file is uploaded successfully.
		9. Click "Save" link
		10. Select the model item above
		VP: The valid tuning file is diplay in the 616D Room Model Detail page.
		*/
		
		//2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//3. Navigate to "Audio" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		//4. Select a stereo room model item
		audioControl.click(AudioRoutes.LINK_STEREO_ROOM);
		//5. Click "Edit Version" link
		audioControl.editVersion();
		//6. Upload an invalid tuning file
		audioControl.uploadInvalidFileTuning(RoomModelEdit.ADD_TUNING);
		/*
		 * VP: Verify that the "Invalid File" message and Retry link are displayed
		 */
		Assert.assertEquals(audioControl.getTextByXpath(RoomModelEdit.INVALID_ERR),RoomModelEdit.err_message[2]);
		//7. Click "Retry" link in "Tuning" section
		//8. Upload an valid tuning file.
		audioControl.uploadFileTuning(RoomModelEdit.RETRY_ICON,AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		String tuning_file = driver.findElement(By.partialLinkText(".dtscs")).getText();
		/*
		 * VP: The valid tuning file is uploaded successfully.
		 */
		Assert.assertTrue(driver.findElement(By.partialLinkText(".dtscs")).isDisplayed());
		//9. Click "Save" link
		audioControl.click(RoomModelEdit.SAVE);
		//10. Select the model item above
		/*
		 * VP: The valid tuning file is diplay in the 616D Room Model Detail page.
		 */
		Assert.assertEquals(driver.findElement(By.partialLinkText(".dtscs")).getText(),tuning_file);
	}
	
	/*
	 * Verify that the "Name" field is validated when editing any options in "Stereo Room Model" section, 
	 * "Other Audio Routes"section and "Multi-Channel Room Model" section.
	 */
	@Test
	public void TC621DRE_16() {
		Reporter.log("Verify that the 'Name' field is validated when editing any options in 'Stereo Room Model' section, 'Other Audio Routes' section and 'Multi-Channel Room Model' section.");
		
		/*1. Navigate to DTS portal
		2. Log into DTS portal as a DTS user successfully
		3. Navigate to "Audio" page
		4. Select a stereo room model link in "Stereo Room Model" section
		5. Click "Edit Version" link
		6. Input minimum value character into "Name" field
		7. Cick "Save" link
			VP: The Stereo Room Model is saved successfully.
		8. Repeat from step 4 to step 7 and change name value with maximum value characters
			VP: The Stereo Room Model is saved successfully.
		9. Repeat from step 4 to step 7 and put empty value into "Name" field
			VP: A error message is displayed like "Name is required" and the Stereo Room Model cannot saved.
		10. Repeat from step 4 to step 7 and change name value with out of maximum value characters
			VP: A error message is dispalyed like "Name exceeds 255 characters limit" and the Stereo Room Model cannot saved.
		11. Repeat from step 4 to step 10 with "Other Audio Routes" and "Multi-Channel Room Model" sections
			VP: The Stereo Room Model is saved successfully ín step 7.
			VP: The Stereo Room Model is saved successfully in step 8.
			VP: A error message is dispalyed like ""Name is required"" and the Stereo Room Model cannot saved in step 9.
			VP: A error message is dispalyed like ""Name exceeds 255 characters limit"" and the Stereo Room Model cannot saved in step 10.
		*/
		
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Audio" page
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		// 4. Select a stereo room model link in "Stereo Room Model" section
		audioControl.click(AudioRoutes.LINK_STEREO_ROOM);
		// 5. Click "Edit Version" link
		audioControl.editVersion();
		// 6. Input minimum value character into "Name" field
		audioControl.editData(RoomModelEdit.DISPLAY_NAME,"N");
		// 7. Cick "Save" link
		audioControl.click(RoomModelEdit.SAVE);
			/*
			 * VP: The Stereo Room Model is saved successfully.
			 */
		Assert.assertEquals(true,audioControl.existsElement(RoomModelInfo.getElementsInfo()));
		// 8. Repeat from step 4 to step 7 and change name value with maximum value characters
		//Click "Edit Version" link
		audioControl.click(RoomModelInfo.EDIT);
		//Input maximum value characters into "Name" field
		audioControl.editData(RoomModelEdit.DISPLAY_NAME,RandomStringUtils.randomNumeric(255));
		//Cick "Save" link
		audioControl.click(RoomModelEdit.SAVE);
			/*
			 * VP: The Stereo Room Model is saved successfully.
			 */
		Assert.assertEquals(true,audioControl.existsElement(RoomModelInfo.getElementsInfo()));
		// 9. Repeat from step 4 to step 7 and put empty value into "Name" field
		//Click "Edit Version" link
		audioControl.click(RoomModelInfo.EDIT);
		//Input maximum value characters into "Name" field
		audioControl.editData(RoomModelEdit.DISPLAY_NAME,"");
		//Cick "Save" link
		audioControl.click(RoomModelEdit.SAVE);
		/*
		* VP: A error message is dispalyed like "Name is required" and the Stereo Room Model cannot saved.
		*/
		Assert.assertEquals(audioControl.getTextByXpath(RoomModelEdit.ERROR_MESSAGE),RoomModelEdit.err_message[0]);
		audioControl.click(RoomModelEdit.CANCEL);
		// 10. Repeat from step 4 to step 7 and change name value with out of maximum value characters
		//Click "Edit Version" link
		audioControl.click(RoomModelInfo.EDIT);
		//Input out of maximum value characters into "Name" field
		audioControl.editData(RoomModelEdit.DISPLAY_NAME,RandomStringUtils.randomNumeric(300));
		//Cick "Save" link
		audioControl.click(RoomModelEdit.SAVE);
		/*
		* VP: A error message is dispalyed like "Name exceeds 255 characters limit" and the Stereo Room Model cannot saved.
		*/
		Assert.assertEquals(audioControl.getTextByXpath(RoomModelEdit.ERROR_MESSAGE),RoomModelEdit.err_message[1]);
		// 11. Repeat from step 4 to step 10 with "Other Audio Routes" and "Multi-Channel Room Model" sections
		
		// Other Audio Routes
		// Select a stereo room model link in "Other Audio Routes" section
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		audioControl.click(AudioRoutes.LINK_OTHER_AUDIO);
		// Click "Edit Version" link
		audioControl.editVersion();
		// Input minimum value character into "Name" field
		audioControl.editData(AudioRoutesEdit.DISPLAY_NAME,"N");
		// Cick "Save" link
		audioControl.click(RoomModelEdit.SAVE);
		/*
		 * VP: The Stereo Room Model is saved successfully.
		*/
		Assert.assertEquals(true,audioControl.existsElement(AudioRoutesInfo.getElementsInfo()));
		// Repeat from step 4 to step 7 and change name value with maximum value characters
		//Click "Edit Version" link
		audioControl.click(RoomModelInfo.EDIT);
		//Input maximum value characters into "Name" field
		audioControl.editData(AudioRoutesEdit.DISPLAY_NAME,RandomStringUtils.randomNumeric(255));
		//Cick "Save" link
		audioControl.click(AudioRoutesEdit.SAVE);
		/*
		* VP: The Stereo Room Model is saved successfully.
		*/
		Assert.assertEquals(true,audioControl.existsElement(AudioRoutesInfo.getElementsInfo()));
		// Repeat from step 4 to step 7 and put empty value into "Name" field
		//Click "Edit Version" link
		audioControl.click(RoomModelInfo.EDIT);
		//Input maximum value characters into "Name" field
		audioControl.editData(AudioRoutesEdit.DISPLAY_NAME,"");
		//Cick "Save" link
		audioControl.click(AudioRoutesEdit.SAVE);
		/*
		* VP: A error message is dispalyed like "Name is required" and the Stereo Room Model cannot saved.
		*/
		Assert.assertEquals(audioControl.getTextByXpath(RoomModelEdit.ERROR_MESSAGE),AudioRoutesEdit.err_message[0]);
		audioControl.click(RoomModelEdit.CANCEL);
		//Repeat from step 4 to step 7 and change name value with out of maximum value characters
		//Click "Edit Version" link
		audioControl.click(RoomModelInfo.EDIT);
		//Input out of maximum value characters into "Name" field
		audioControl.editData(RoomModelEdit.DISPLAY_NAME,RandomStringUtils.randomNumeric(300));
		//Cick "Save" link
		audioControl.click(RoomModelEdit.SAVE);
		/*
		 * VP: A error message is displayed like "Name exceeds 255 characters limit" and the Stereo Room Model cannot saved.
		*/
		Assert.assertEquals(audioControl.getTextByXpath(RoomModelEdit.ERROR_MESSAGE),AudioRoutesEdit.err_message[1]);	
		
		/*
		 * Set default name back
		 */	
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		audioControl.click(AudioRoutes.LINK_OTHER_AUDIO);
		audioControl.editVersion();
		audioControl.editData(RoomModelEdit.DISPLAY_NAME, "Internal Speakers (Default)");
		audioControl.click(RoomModelEdit.SAVE);
		audioControl.click(PageHome.LINK_AUDIO_ROUTES);
		audioControl.click(AudioRoutes.LINK_STEREO_ROOM);
		audioControl.editVersion();
		audioControl.editData(RoomModelEdit.DISPLAY_NAME, "Wide");
		audioControl.click(RoomModelEdit.SAVE);
	}
	
}
