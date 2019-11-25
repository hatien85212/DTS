package com.dts.adminportal.dts.test;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddBrand;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.BrandInfo;
import com.dts.adminportal.model.CompanyInfo;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.util.TestData;

public class DTSCompanies072DEditBrandInfoNew extends BasePage {
	
	@Override
	protected void initData() {
	}	
	
	/*
	 * Verify that new brand is not added when user selects "Cancel" link in Actions module of Edit Brand Info page.
	 */
	@Test
	public void TC072DEBI_01() {
		companyControl.addLog("ID TC072DEBI_01 : Verify that new brand is not added when user "
				+ "selects 'Cancel' link in Actions module of Edit Brand Info page.");
		/*
			Pre-condition: User has "Edit Company Info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			VP: Verify that the "Add" link is displayed next to "Brands" label
			4. Click "Add" link
			"VP: Verify that the ""Edit Brand Info"" page is displayed which including:
			Languages dropdown, Brand name text field, Brand Tag Line text field, Consumer 
			Brand Aliases text field, Web Site text field, Brand Overview text field, Copyright 
			and Trademark Notice text field and Consumer Brand Logo thumbnail with three 
			types( 250x250, 500x500, 1000x1000)"
			5. Fill valid value into all fields of "Edit Brand Info" page
			6. Click "Cancel" link in the Action module
			The 061P Company Page is displayed and there is no new brand's name and logo added.
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Select a company on table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		/*
		 * VP: Verify that the "Add" link is displayed next to "Brands" label
		 */
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.ADD_BRAND));
		// 5. Click "Add" link
		companyControl.click(CompanyInfo.ADD_BRAND);
		/*
		 * Verify that the "Edit Brand Info" page is displayed
		 */
		Assert.assertEquals(companyControl.existsElement(AddBrand.getHash()), true);
		Hashtable<String,String> data = TestData.brandDraft();
		data.remove("save");
		companyControl.addBrand(AddBrand.getHash(), data);		
		// 7. Click "Save" link
		companyControl.click(AddBrand.CANCEL);
		/*
		 * Verify that The 061D Company Page is displayed and there is no new brand's name and logo added
		 */
		Assert.assertEquals(companyControl.existsElement(CompanyInfo.getListElement()), true);
		Assert.assertFalse(companyControl.checkBrandExist(data.get("name")));
	}
	
	/*
	 * Verify that the brand name is unique when adding new brand		
	 */
	@Test
	public void TC072DEBI_02() {
		companyControl.addLog("ID TC072DEBI_02 : Verify that the brand name is unique when adding new brand");
		/*
		1. Navigate to DTS portal
		2. Log into DTS portal as a DTS user successfully	
		3. Click "Companies" tab	
		4. Select a company from companies list	
		5. Click "Add" link	
		6. Fill valid value into all fields of "Edit Brand Info" page	
		7. Input an existed brand name into Brand name field	
		8. Click "Save" link	
		VP: An error message "Brand name must be unique in the system"  is displayed
		9. Click "Cancel" link in the Action module	
		The 061P Company Page is displayed and there is no new brand added
		*/
		
//		2. Log into DTS portal as a DTS user successfully	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		3. Click "Companies" tab	
		companyControl.click(PageHome.LINK_COMPANY);
//		4. Select a company from companies list	
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
//		5. Click "Add" link	
		companyControl.click(CompanyInfo.ADD_BRAND);
//		6. Fill valid value into all fields of "Edit Brand Info" page	
//		7. Input an existed brand name into Brand name field	
		companyControl.editData(AddBrand.NAME,PARTNER_BRAND_NAME_1);
//		8. Click "Save" link	
		companyControl.click(AddBrand.SAVE);
//		VP: An error message "Brand name must be unique in the system."  is displayed
		Assert.assertTrue(companyControl.getTextByXpath(AddBrand.ERROR_NAME).equals(AddBrand.BrandMessage.UNIQUE_BRAND_NAME.getName()));
//		9. Click "Cancel" link in the Action module	
		companyControl.click(AddBrand.CANCEL);
//		VP: The 061P Company Page is displayed and there is no new brand added
		Assert.assertTrue(companyControl.existsElement(CompanyInfo.getHash()));
	}
	/*
	 * Verify that user is able to upload wrong demension for brand logo images	
	 */
	@Test
	public void TC072DEBI_03() {
		companyControl.addLog("ID TC072DEBI_03 :Verify that user is able to upload wrong demension for brand logo images");
		
		/*
		1. Log into DTS portal as a DTSadmin	
		2. Navigate to "Companies" page	
		3. Select a company from companies list	
		4. Click “Add” link	
		5. Upload a wrong image demension for 250x250 brand logo image section	
		5. Upload a wrong image demension for 500x500 brand logo image section	
		6. Upload a wrong image demension for 1000x100 brand logo image section	
		VP: Demenson of three size of brand logo images are automatically rezied and upload successfully and the message 
		"Automaticall resized. Original XX x YY px" is displayed
		*/
		
//		1. Log into DTS portal as a DTSadmin	
		loginControl.login(SUPER_USER_NAME,SUPER_PARTNER_PASSWORD);
//		2. Navigate to "Companies" page	
		companyControl.click(PageHome.LINK_COMPANY);
//		3. Select a company from companies list	
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
//		4. Click “Add” link	
		companyControl.click(CompanyInfo.ADD_BRAND);
//		5. Upload a wrong image demension for 250x250 brand logo image section	
		companyControl.uploadFile(AddBrand.ADD_IMAGE250,AddEditProductModel.FileUpload.IMG_500_JPG.getName());
//		5. Upload a wrong image demension for 500x500 brand logo image section	
		companyControl.uploadFile(AddBrand.ADD_IMAGE500,AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
//		6. Upload a wrong image demension for 1000x100 brand logo image section	
		companyControl.uploadFile(AddBrand.ADD_IMAGE1000,AddEditProductModel.FileUpload.IMG_250_JPG.getName());
//		VP: Demenson of three size of brand logo images are automatically rezied and upload successfully and the message 
//		"Automaticall resized. Original XX x YY px" is displayed
		Assert.assertTrue(companyControl.getTextByXpath(AddBrand.BRAND_IMAGE_UPLOAD_MESSAGE_250).contains(AddBrand.BrandMessage.FILE_500_PX.getName()));
		Assert.assertTrue(companyControl.getTextByXpath(AddBrand.BRAND_IMAGE_UPLOAD_MESSAGE_500).contains(AddBrand.BrandMessage.FILE_1000_PX.getName()));
		Assert.assertTrue(companyControl.getTextByXpath(AddBrand.BRAND_IMAGE_UPLOAD_MESSAGE_1000).contains(AddBrand.BrandMessage.FILE_250_PX.getName()));
	}
	/*
	 * Verify that the Consumer Brand Logo is validated for uploading
	 */
	@Test
	public void TC072DEBI_04() {
		companyControl.addLog("ID TC072DEBI_04 : Verify that the Consumer Brand Logo is validated for uploading");
		/*
			Pre-condition: User has "Edit Company Info" and "Edit Brand Info" rights.
			1. Navigate to DTS portal	
			2. Log into DTS portal as a DTS user successfully	
			3. Click "Companies" tab	
			VP: Verify that the "Add" link is displayed next to "Brands" label
			4. Select a company from companies list	
			5. Click "Add" link	
			VP: Verify that the "Edit Brand Info" page is displayed
			6. Fill valid value into all fields of "Edit Brand Info" page	
			7. Upload an invalid file for each brand logo image 	
			VP: An error message and a retry link are displayed.
			8. Upload image for 250x250 resolution type	
			VP: Verify that the image for 250x250 is uploaded successfully
			9. Upload image for 500x500 resolution type	
			VP: Verify that the image for 500x500 is uploaded successfully
			10. Upload image for 1000x1000 resolution type	
			VP: Verify that the image for 1000x1000 is uploaded successfully
			11. Click on each brand logo image	
			VP: The lightbox style popup with the correct picture showing in full size is displayed 
			12. Click "Save" link	
			VP: The 063P Brand Ingo Page is displayed with new brand information and three type of logos correctly without "Edit" and "Delete" link. 
			13. Click "Edit" link	
			14. Delete all brand's logo types	
			15. Click "Save" link	
			VP: All three logo types are deleted successfully.
			16. Click "Edit" link	
			17. Repeat from step 8 to 10	
			18. Click "Save" link	
			All three logo types are uploaded successfully.

		*/
		/*
		 * Pre-condition: User has "Edit Company Info" and "Edit Brand Info" rights
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		companyControl.click(PageHome.LINK_USERS);
		// Select a user from table
		userControl.selectUserInfoByEmail(DTS_USER);
		// Click Edit link
		companyControl.click(UserMgmt.EDIT);
		// Disable all privileges
		userControl.disableAllPrivileges(AddUser.PRIVILEGES_TABLE);
		// Enable "Edit Company Info" privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_Company_Info.getName());
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_brand_info.getName());
		companyControl.click(AddUser.SAVE);
		// Logout
		companyControl.logout();
		/*
		 * *********************************************************
		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Select a company on table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		/*
		 * VP: Verify that the "Add" link is displayed next to "Brands" label
		 */
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.ADD_BRAND));
		// 5. Click "Add" link
		companyControl.click(CompanyInfo.ADD_BRAND);
		/*
		 * Verify that the "Edit Brand Info" page is displayed
		 */
		Assert.assertEquals(companyControl.existsElement(AddBrand.getHash()), true);
		// 6. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.brandDraft();
		data.remove("save");
		companyControl.addBrand(AddBrand.getHash(), data);
//		7. Upload an invalid file for each brand logo image 
		companyControl.uploadFile(AddBrand.ADD_IMAGE250, AddEditProductModel.FileUpload.Default_External_Audio.getName());
		companyControl.uploadFile(AddBrand.ADD_IMAGE500, AddEditProductModel.FileUpload.Default_External_Audio.getName());
		companyControl.uploadFile(AddBrand.ADD_IMAGE1000, AddEditProductModel.FileUpload.Default_External_Audio.getName());
		/*
		 * VP: An error message and a retry link are displayed.
		 */
		Assert.assertTrue(companyControl.getTextByXpath(AddBrand.BRAND_IMAGE_UPLOAD_MESSAGE_250).contains(AddBrand.BrandMessage.INVALID_FILE.getName()));
		Assert.assertTrue(companyControl.getTextByXpath(AddBrand.BRAND_IMAGE_UPLOAD_MESSAGE_500).contains(AddBrand.BrandMessage.INVALID_FILE.getName()));
		Assert.assertTrue(companyControl.getTextByXpath(AddBrand.BRAND_IMAGE_UPLOAD_MESSAGE_1000).contains(AddBrand.BrandMessage.INVALID_FILE.getName()));
		Assert.assertTrue(companyControl.isElementPresent(AddBrand.RETRY_UPLOAD_IMAGE_250));
		Assert.assertTrue(companyControl.isElementPresent(AddBrand.RETRY_UPLOAD_IMAGE_500));
		Assert.assertTrue(companyControl.isElementPresent(AddBrand.RETRY_UPLOAD_IMAGE_1000));
		// 8. Upload image for 250x250 resolution type
		companyControl.uploadFile(AddBrand.RETRY_UPLOAD_IMAGE_250, AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		/*
		 * VP: Verify that the image for 250x250 is uploaded successfully
		 */
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE250_DISPLAY, "src").contains(AddEditProductModel.FileUpload.IMG_250_JPG.getName().replaceAll(".jpg", "")));
		// 9. Upload image for 500x500 resolution type
		companyControl.uploadFile(AddBrand.RETRY_UPLOAD_IMAGE_500, AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		/*
		 * VP: Verify that the image for 500x500 is uploaded successfully
		 */
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE500_DISPLAY, "src").contains(AddEditProductModel.FileUpload.IMG_500_JPG.getName().replaceAll(".jpg", "")));
		// 10. Upload image for 1000x1000 resolution type
		companyControl.uploadFile(AddBrand.RETRY_UPLOAD_IMAGE_1000, AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		/*
		 * VP: Verify that the image for 1000x1000 is uploaded successfully
		 */
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE1000_DISPLAY, "src").contains(AddEditProductModel.FileUpload.IMG_1000_JPG.getName().replaceAll(".jpg", "")));
		// 11. Click on each brand logo image
		// Click on brand logo 250
		companyControl.click(AddBrand.IMAGE250_DISPLAY);
		companyControl.waitForElementClickable(AddBrand.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(companyControl.isElementPresent(AddBrand.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_250_JPG.getName().replaceAll(".jpg", "")));
		// Close lightbox image
		companyControl.click(AddBrand.LIGHTBOX_CLOSE);
		companyControl.waitForElementDisappear(AddBrand.LIGHTBOX_STYLE_IMAGE);
		// Click on brand logo 500
		companyControl.click(AddBrand.IMAGE500_DISPLAY);
		companyControl.waitForElementClickable(AddBrand.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(companyControl.isElementPresent(AddBrand.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_500_JPG.getName().replaceAll(".jpg", "")));
		// Close lightbox image
		companyControl.click(AddBrand.LIGHTBOX_CLOSE);
		companyControl.waitForElementDisappear(AddBrand.LIGHTBOX_STYLE_IMAGE);
		// Click on brand logo 1000
		companyControl.click(AddBrand.IMAGE1000_DISPLAY);
		companyControl.waitForElementClickable(AddBrand.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(companyControl.isElementPresent(AddBrand.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_1000_JPG.getName().replaceAll(".jpg", "")));
		// Close lightbox image
		companyControl.click(AddBrand.LIGHTBOX_CLOSE);
		companyControl.waitForElementDisappear(AddBrand.LIGHTBOX_STYLE_IMAGE);
		
		// 12. Click "Save" link
		companyControl.click(AddBrand.SAVE);
		// Check if PDPP-902 found
//		if(companyControl.isElementPresent(BrandInfo.EDIT_BRAND)){
//			companyControl.addErrorLog("PDPP-902: 063D Brand Info: DTS user is able to manage brands although the 'Edit brand info' privilege is disabled");
//			Assert.assertTrue(false);
//		}
		/*
		 * Verify that The 063P Brand Ingo Page is displayed with new brand information and three type of logos correctly without "Edit" and "Delete" link
		 */
		Assert.assertEquals(companyControl.existsElement(BrandInfo.getAllField()), true);
		Assert.assertFalse(companyControl.isElementPresent(AddBrand.DELELE_IMAGE250));
		Assert.assertFalse(companyControl.isElementPresent(AddBrand.DELELE_IMAGE500));
		Assert.assertFalse(companyControl.isElementPresent(AddBrand.DELELE_IMAGE1000));
		// 13. Click "Edit" link
		companyControl.click(BrandInfo.EDIT_BRAND);
		// 14. Delete all brand's logo types
		companyControl.click(AddBrand.DELELE_IMAGE250);
		companyControl.selectConfirmationDialogOption("Delete");
		companyControl.click(AddBrand.DELELE_IMAGE500);
		companyControl.selectConfirmationDialogOption("Delete");
		companyControl.click(AddBrand.DELELE_IMAGE1000);
		companyControl.selectConfirmationDialogOption("Delete");
		// 15. Click "Save" link
		companyControl.click(AddBrand.SAVE);
		/*
		 * Verify that All three logo types are deleted successfully
		 */
		Assert.assertFalse(companyControl.isElementPresent(BrandInfo.BRAND_LOGO_250));
		Assert.assertFalse(companyControl.isElementPresent(BrandInfo.BRAND_LOGO_500));
		Assert.assertFalse(companyControl.isElementPresent(BrandInfo.BRAND_LOGO_1000));
		// 16. Click "Edit" link
		companyControl.click(BrandInfo.EDIT_BRAND);
		// 17. Repeat from step 8 to 10
		/*
		 * All three logo types are uploaded successfully
		 */
		companyControl.uploadFile(AddBrand.ADD_IMAGE250, AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE250_DISPLAY, "src").contains(AddEditProductModel.FileUpload.IMG_250_JPG.getName().replaceAll(".jpg", "")));
		companyControl.uploadFile(AddBrand.ADD_IMAGE500, AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE500_DISPLAY, "src").contains(AddEditProductModel.FileUpload.IMG_500_JPG.getName().replaceAll(".jpg", "")));
		companyControl.uploadFile(AddBrand.ADD_IMAGE1000, AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE1000_DISPLAY, "src").contains(AddEditProductModel.FileUpload.IMG_1000_JPG.getName().replaceAll(".jpg", "")));
		// 18. Click "Save" link 
		companyControl.click(AddBrand.SAVE);
		// Delete brand
		companyControl.doDelete(BrandInfo.DELETE_LINK);
		/*
		 *  PostCondition: Re-Enable all privilege for user above
		 */
		// Logout
		companyControl.logout();
		// Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		companyControl.click(PageHome.LINK_USERS);
		// Select a user from table
		userControl.selectUserInfoByEmail(DTS_USER);
		// Click Edit link
		companyControl.click(UserMgmt.EDIT);
		// Enable all privileges
		userControl.enableAllPrivileges();
		companyControl.click(AddUser.SAVE);
	}
	
	/*
	 * Verify that the brand logo images could be uploaded successfully by dragging and dropping
	 */
	@Test
	public void TC072DEBI_05() {
		companyControl.addLog("ID TC072DEBI_05 : Verify that the brand logo images could be uploaded successfully by dragging and dropping");
		/*	1. Log into DTS portal as a DTS user 	
			2. Navigate to "Companies" page 	
			3. Select a company from companies list	
			4, Click "Add" link	
			5. Drag and drop invalid files into three brand logo images	
			VP: An error message is displayed and the invalid image file is not uploaded.
			6. Drag and drop images into three brand logo images	
			All three logo types are uploaded successfully.
			7. Drag and drop images with incorrect size into three brand logo images	
			Demenson of three size of brand logo images are automatically rezied and upload successfully and the message 
			"Automaticall resized. Original XX x YY px" is displayed
			*/
		
//		1. Log into DTS portal as a DTS user 
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Companies" page 
		companyControl.click(PageHome.LINK_COMPANY);
//		3. Select a company from companies list	
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
//		4, Click "Add" link	
		companyControl.click(CompanyInfo.ADD_BRAND);
//		5. Drag and drop invalid files into three brand logo images	
		companyControl.dragDropBrandImage(AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName(),AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		companyControl.dragDropBrandImage(AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName(),AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		companyControl.dragDropBrandImage(AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName(),AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
//		VP: An error message is displayed and the invalid image file is not uploaded.
		Assert.assertTrue(companyControl.getTextByXpath(AddBrand.BRAND_IMAGE_UPLOAD_MESSAGE_250).contains(AddBrand.BrandMessage.INVALID_FILE.getName()));
		Assert.assertTrue(companyControl.getTextByXpath(AddBrand.BRAND_IMAGE_UPLOAD_MESSAGE_500).contains(AddBrand.BrandMessage.INVALID_FILE.getName()));
		Assert.assertTrue(companyControl.getTextByXpath(AddBrand.BRAND_IMAGE_UPLOAD_MESSAGE_1000).contains(AddBrand.BrandMessage.INVALID_FILE.getName()));
		Assert.assertTrue(companyControl.isElementPresent(AddBrand.RETRY_UPLOAD_IMAGE_250));
		Assert.assertTrue(companyControl.isElementPresent(AddBrand.RETRY_UPLOAD_IMAGE_500));
		Assert.assertTrue(companyControl.isElementPresent(AddBrand.RETRY_UPLOAD_IMAGE_1000));
		companyControl.click(AddBrand.CANCEL);
		companyControl.click(CompanyInfo.ADD_BRAND);
//		6. Drag and drop images into three brand logo images
		companyControl.dragDropBrandImage(AddEditProductModel.FileUpload.IMG_250_JPG.getName(),AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		companyControl.dragDropBrandImage(AddEditProductModel.FileUpload.IMG_500_JPG.getName(),AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		companyControl.dragDropBrandImage(AddEditProductModel.FileUpload.IMG_1000_JPG.getName(),AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
//		VP: All three logo types are uploaded successfully.
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE250_DISPLAY, "src").contains(AddEditProductModel.FileUpload.IMG_250_JPG.getName().replaceAll(".jpg", "")));
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE500_DISPLAY, "src").contains(AddEditProductModel.FileUpload.IMG_500_JPG.getName().replaceAll(".jpg", "")));
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE1000_DISPLAY, "src").contains(AddEditProductModel.FileUpload.IMG_1000_JPG.getName().replaceAll(".jpg", "")));
		companyControl.click(AddBrand.CANCEL);
		companyControl.click(CompanyInfo.ADD_BRAND);
//		7. Drag and drop images with incorrect size into three brand logo images	
		companyControl.dragDropBrandImage(AddEditProductModel.FileUpload.IMG_500_JPG.getName(),AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		companyControl.dragDropBrandImage(AddEditProductModel.FileUpload.IMG_1000_JPG.getName(),AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		companyControl.dragDropBrandImage(AddEditProductModel.FileUpload.IMG_250_JPG.getName(),AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
//		VP: Demenson of three size of brand logo images are automatically rezied and upload successfully and the message 
//		"Automaticall resized. Original XX x YY px" is displayed
		Assert.assertTrue(companyControl.getTextByXpath(AddBrand.BRAND_IMAGE_UPLOAD_MESSAGE_250).contains(AddBrand.BrandMessage.FILE_500_PX.getName()));
		Assert.assertTrue(companyControl.getTextByXpath(AddBrand.BRAND_IMAGE_UPLOAD_MESSAGE_500).contains(AddBrand.BrandMessage.FILE_1000_PX.getName()));
		Assert.assertTrue(companyControl.getTextByXpath(AddBrand.BRAND_IMAGE_UPLOAD_MESSAGE_1000).contains(AddBrand.BrandMessage.FILE_250_PX.getName()));
		
	}
	/*
	 * Verify that the three size of Brand Logo Image display in an order list with thumbnail	
	 */
	@Test
	public void TC072DEBI_06() {
		companyControl.addLog("ID TC072DEBI_06 : Verify that the three size of Brand Logo Image display in an order list with thumbnail");
		/*
		1. Log into DTS portal as a DTS user	
		2. Navigate to "Companies" page 	
		3. Select a company from companies list	
		4. Click “Add” link	
		5. Upload three size of brand logo images successfully	
		VP: The correct image thumbnail is displayed next to image name. 
		VP: Three size of Brand Logo Images display in vertical order list: 250x250 pixels, 500x500 pixels, 1000x1000 pixels."
		*/
		
//		1. Log into DTS portal as a DTS user	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Companies" page 	
		companyControl.click(PageHome.LINK_COMPANY);
//		3. Select a company from companies list	
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
//		4. Click “Add” link	
		companyControl.click(CompanyInfo.ADD_BRAND);
//		5. Upload three size of brand logo images successfully	
		companyControl.uploadFile(AddBrand.ADD_IMAGE250,AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		companyControl.uploadFile(AddBrand.ADD_IMAGE500,AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		companyControl.uploadFile(AddBrand.ADD_IMAGE1000,AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
//		VP: The correct image thumbnail is displayed next to image name. 
		Assert.assertTrue(companyControl.isElementDisplayHorizontal(AddBrand.IMAGE250_DISPLAY,AddBrand.IMAGE250_NAME));
//		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE250_DISPLAY,"src").contains(companyControl.getTextByXpath(AddBrand.IMAGE250_NAME)));
//		VP: Three size of Brand Logo Images display in vertical order list: 250x250 pixels, 500x500 pixels, 1000x1000 pixels."
		Assert.assertTrue(companyControl.isElementDisplayVertically(AddBrand.IMAGE250_DISPLAY,AddBrand.IMAGE500_DISPLAY));
		Assert.assertTrue(companyControl.isElementDisplayVertically(AddBrand.IMAGE500_DISPLAY,AddBrand.IMAGE1000_DISPLAY));
	}
	
	/*
	 * Verify that user is able to upload agian brand logo image when the file being upload occurs error
	 */
	@Test
	public void TC072DEBI_07() {
		companyControl.addLog("ID TC072DEBI_07 : Verify that user is able to upload agian brand logo image when the file being upload occurs error");
		/*
		1. Log into DTS portal as a DTS admin	
		2. Navigate to "Companies" page	
		3. Select a company from companies list	
		4. Click “Add” link	
		5. Upload image for 250x250 resolution type	
		6. Corrupt the network while the file is uploading	
		VP: Verify that the "File upload error" message and  "Retry" link are displayed
		7. Connect network successfully	
		8. Try to upload another tuning file	
		VP: The valid image file is uploaded successfully
		9. Repeat from step 5 to 8 for 500x500, 1000x1000 brand logo images	
		The brand logo images could be uploaded again.
		*/
		
//		1. Log into DTS portal as a DTS admin	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Companies" page	
		companyControl.click(PageHome.LINK_COMPANY);
//		3. Select a company from companies list	
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
//		4. Click “Add” link	
		companyControl.click(CompanyInfo.ADD_BRAND);
//		5. Upload image for 250x250 resolution type	
		productControl.interuptNetwork();
		companyControl.uploadFile(AddBrand.ADD_IMAGE250,AddEditProductModel.FileUpload.IMG_250_JPG.getName());
//		6. Corrupt the network while the file is uploading	
//		VP: Verify that the "File upload error" message and  "Retry" link are displayed
		Assert.assertTrue(companyControl.getTextByXpath(AddBrand.BRAND_IMAGE_UPLOAD_MESSAGE_250).contains(AddBrand.BrandMessage.UPLOAD_ERROR.getName()));
		Assert.assertTrue(companyControl.isElementPresent(AddBrand.RETRY_UPLOAD_IMAGE_250));
//		7. Connect network successfully	
		productControl.connectNetwork();	
//		8. Try to upload another tuning file	
		companyControl.uploadFile(AddBrand.RETRY_UPLOAD_IMAGE_250,AddEditProductModel.FileUpload.IMG_250_JPG.getName());
//		VP: The valid image file is uploaded successfully
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE250_DISPLAY, "src").contains(AddEditProductModel.FileUpload.IMG_250_JPG.getName().replaceAll(".jpg", "")));
//		9. Repeat from step 5 to 9 for 500x500, 1000x1000 brand logo images	
//		The brand logo images could be uploaded again.
		// 500x500
		productControl.interuptNetwork();
		companyControl.uploadFile(AddBrand.ADD_IMAGE500,AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		Assert.assertTrue(companyControl.getTextByXpath(AddBrand.BRAND_IMAGE_UPLOAD_MESSAGE_500).contains(AddBrand.BrandMessage.UPLOAD_ERROR.getName()));
		Assert.assertTrue(companyControl.isElementPresent(AddBrand.RETRY_UPLOAD_IMAGE_500));
		productControl.connectNetwork();
		companyControl.uploadFile(AddBrand.RETRY_UPLOAD_IMAGE_500,AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE500_DISPLAY, "src").contains(AddEditProductModel.FileUpload.IMG_500_JPG.getName().replaceAll(".jpg", "")));
		// 1000x1000
		productControl.interuptNetwork();
		companyControl.uploadFile(AddBrand.ADD_IMAGE1000,AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		Assert.assertTrue(companyControl.getTextByXpath(AddBrand.BRAND_IMAGE_UPLOAD_MESSAGE_1000).contains(AddBrand.BrandMessage.UPLOAD_ERROR.getName()));
		Assert.assertTrue(companyControl.isElementPresent(AddBrand.RETRY_UPLOAD_IMAGE_1000));
		productControl.connectNetwork();
		companyControl.uploadFile(AddBrand.RETRY_UPLOAD_IMAGE_1000,AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE1000_DISPLAY, "src").contains(AddEditProductModel.FileUpload.IMG_1000_JPG.getName().replaceAll(".jpg", "")));
	}
	/*
	 *Verify that the brand logo images could upload again after canceling uploading images 
	 */
	@Test
	public void TC072DEBI_08() {
		companyControl.addLog("ID TC072DEBI_08 : Verify that the brand logo images could upload again after canceling uploading images");
		/*1. Log into DTS portal as a DTS admin	
		2. Navigate to "Companies" page 	
		3. Select a company from companies list	
		4. Click “Add” link	
		5. Upload image for 250x250 resolution type	
		6. Cancel uploading file	
		VP: The message "File upload canceled" and Retry link is displayed
		7. Click "Retry" link	
		8. Try to upload new tuning file again	
		VP: New image is uploaded successfully
		19. Repeat from step 5 to 8 for 500x500, 1000x1000 brand logo images	
		The brand logo images could be uploaded again.
		*/
		
//		1. Log into DTS portal as a DTS admin	
		loginControl.login(SUPER_USER_NAME,SUPER_USER_PASSWORD);
//		2. Navigate to "Companies" page 	
		companyControl.click(PageHome.LINK_COMPANY);
//		3. Select a company from companies list	
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
//		4. Click “Add” link	
		companyControl.click(CompanyInfo.ADD_BRAND);
//		5. Upload image for 250x250 resolution type	
//		productControl.scrollMouseUntilImageVisible("BrandImageDragDropArea250x250.jpg");
		companyControl.uploadFileInterupt(AddBrand.ADD_IMAGE250,AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
//		6. Cancel uploading file	
		productControl.clickImage(AddEditProductModel.FileUpload.XBrand_Cancel_Upload.getName(), "jpg");
//		VP: The message "File upload canceled" and Retry link is displayed
		Assert.assertTrue(companyControl.getTextByXpath(AddBrand.BRAND_IMAGE_UPLOAD_MESSAGE_250).contains(AddBrand.BrandMessage.UPLOAD_CANCELED.getName()));
//		7. Click "Retry" link	
//		8. Try to upload new tuning file again	
		companyControl.uploadFile(AddBrand.RETRY_UPLOAD_IMAGE_250,AddEditProductModel.FileUpload.IMG_250_JPG.getName());
//		VP: New image is uploaded successfully
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE250_DISPLAY,"src").contains(AddEditProductModel.FileUpload.IMG_250_JPG.getName().replaceAll(".jpg", "")));
//		19. Repeat from step 5 to 8 for 500x500, 1000x1000 brand logo images	
//		The brand logo images could be uploaded again.
		// 500x500
		companyControl.uploadFileInterupt(AddBrand.ADD_IMAGE500,AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		productControl.clickImage(AddEditProductModel.FileUpload.XBrand_Cancel_Upload.getName(), "jpg");
		Assert.assertTrue(companyControl.getTextByXpath(AddBrand.BRAND_IMAGE_UPLOAD_MESSAGE_500).contains(AddBrand.BrandMessage.UPLOAD_CANCELED.getName()));
		companyControl.uploadFile(AddBrand.RETRY_UPLOAD_IMAGE_500,AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE500_DISPLAY,"src").contains(AddEditProductModel.FileUpload.IMG_500_JPG.getName().replaceAll(".jpg", "")));
		// 1000x1000
		companyControl.uploadFileInterupt(AddBrand.ADD_IMAGE1000,AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		productControl.clickImage(AddEditProductModel.FileUpload.XBrand_Cancel_Upload.getName(), "jpg");
		Assert.assertTrue(companyControl.getTextByXpath(AddBrand.BRAND_IMAGE_UPLOAD_MESSAGE_1000).contains(AddBrand.BrandMessage.UPLOAD_CANCELED.getName()));
		companyControl.uploadFile(AddBrand.RETRY_UPLOAD_IMAGE_1000,AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE1000_DISPLAY,"src").contains(AddEditProductModel.FileUpload.IMG_1000_JPG.getName().replaceAll(".jpg", "")));
	}
	
}
