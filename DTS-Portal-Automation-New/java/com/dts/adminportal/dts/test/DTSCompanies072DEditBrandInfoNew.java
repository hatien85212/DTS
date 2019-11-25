package com.dts.adminportal.dts.test;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddBrand;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.BrandEdit;
import com.dts.adminportal.model.BrandInfo;
import com.dts.adminportal.model.CompanyInfo;
import com.dts.adminportal.model.Constant;
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
			4. Click "Add" link
			VP: Verify that the "Edit Brand Info" page is displayed
			5. Fill valid value into all fields of "Edit Brand Info" page
			6. Upload image for 250x250 resolution type
			VP: Verify that the image for 250x250 is uploaded successfully
			7. Upload image for 500x500 resolution type
			VP: Verify that the image for 500x500 is uploaded successfully
			8. Upload image for 1000x1000 resolution type
			VP: Verify that the image for 1000x1000 is uploaded successfully
			9. Click on each brand logo image
			VP: The lightbox style popup with the correct picture showing in full size is displayed 
			10. Click "Save" link
			VP: The 063P Brand Ingo Page is displayed with new brand information and three type of 
			logos correctly without "Edit" and "Delete" link. 
			11. Click "Edit" link
			12. Delete all brand's logo types
			13. Click "Save" link
			VP: All three logo types are deleted successfully.
			14. Click "Edit" link
			15. Repeat from step 6 to 8
			16. Click "Save" link
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
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_brand_info);
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
		// 7. Upload image for 160x160 resolution type
		companyControl.uploadFile(AddBrand.IMAGE250, AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		/*
		 * VP: Verify that the image for 160x160 is uploaded successfully
		 */
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE250_DISPLAY, "src").contains(AddEditProductModel.FileUpload.IMG_250_JPG.getName()));
		// 8. Upload image for 290x290 resolution type
		companyControl.uploadFile(AddBrand.IMAGE500, AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		/*
		 * VP: Verify that the image for 290x290 is uploaded successfully
		 */
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE500_DISPLAY, "src").contains(AddEditProductModel.FileUpload.IMG_500_JPG.getName()));
		// 9. Upload image for 664x664 resolution type
		companyControl.uploadFile(AddBrand.IMAGE1000, AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		/*
		 * VP: Verify that the image for 664x664 is uploaded successfully
		 */
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE1000_DISPLAY, "src").contains(AddEditProductModel.FileUpload.IMG_1000_JPG.getName()));
		
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
		
		// 10. Click "Save" link
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
		Assert.assertFalse(companyControl.isElementPresent(BrandEdit.DELELE_IMAGE124));
		Assert.assertFalse(companyControl.isElementPresent(BrandEdit.DELELE_IMAGE290));
		Assert.assertFalse(companyControl.isElementPresent(BrandEdit.DELELE_IMAGE664));
		Assert.assertFalse(companyControl.isElementPresent(BrandEdit.IMAGE124));
		Assert.assertFalse(companyControl.isElementPresent(BrandEdit.IMAGE290));
		Assert.assertFalse(companyControl.isElementPresent(BrandEdit.IMAGE664));
//		Assert.assertFalse(companyControl.isElementPresent(BrandInfo.EDIT_BRAND));
//		Assert.assertFalse(companyControl.isElementPresent(BrandInfo.DELETE_LINK));
		
		companyControl.click(BrandInfo.EDIT_BRAND);
		// 12. Delete all brand's logo types
		companyControl.click(AddBrand.DELELE_IMAGE250);
		companyControl.click(AddBrand.DELELE_IMAGE500);
		companyControl.click(AddBrand.DELELE_IMAGE1000);
		// 13. Click "Save" link
		companyControl.click(AddBrand.SAVE);
		/*
		 * Verify that All three logo types are deleted successfully
		 */
		Assert.assertFalse(companyControl.isElementPresent(BrandInfo.BRAND_LOGO_250));
		Assert.assertFalse(companyControl.isElementPresent(BrandInfo.BRAND_LOGO_500));
		Assert.assertFalse(companyControl.isElementPresent(BrandInfo.BRAND_LOGO_1000));
		
		companyControl.click(BrandInfo.EDIT_BRAND);
		// 15. Repeat from step 7 to 10
		/*
		 * All three logo types are uploaded successfully
		 */
		companyControl.uploadFile(AddBrand.IMAGE250, AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE250_DISPLAY, "src").contains(AddEditProductModel.FileUpload.IMG_250_JPG.getName()));
		companyControl.uploadFile(AddBrand.IMAGE500, AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE500_DISPLAY, "src").contains(AddEditProductModel.FileUpload.IMG_500_JPG.getName()));
		companyControl.uploadFile(AddBrand.IMAGE1000, AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE1000_DISPLAY, "src").contains(AddEditProductModel.FileUpload.IMG_1000_JPG.getName()));
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
	
}
