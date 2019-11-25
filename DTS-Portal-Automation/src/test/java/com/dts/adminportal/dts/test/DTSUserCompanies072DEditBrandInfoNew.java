package com.dts.adminportal.dts.test;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AddBrand;
import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.BrandInfo;
import dts.com.adminportal.model.CompanyInfo;
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.Xpath;

public class DTSUserCompanies072DEditBrandInfoNew extends CreatePage {
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
	 * Verify that the Edit Brand Info page displays properly
	 */
	@Test
	public void TC072DEBI_01() {
		result.addLog("ID TC072DEBI_01 : Verify that the Edit Brand Info page displays properly");
		/*
			Pre-condition: User has "Edit Company Info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			VP: Verify that the "Add" link is displayed next to "Brands" label
			5. Click "Add" link
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		/*
		 * VP: Verify that the "Add" link is displayed next to "Brands" label
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.ADD_BRAND));
		// 5. Click "Add" link
		home.click(CompanyInfo.ADD_BRAND);
		/*
		 * Verify that the "Edit Brand Info" page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddBrand.getHash()).getResult(), "Pass");
	}
	
	/*
	 * Verify that the user can add new brand successfully from Edit Brand Info page with valid values
	 */
	@Test
	public void TC072DEBI_02() {
		result.addLog("ID TC072DEBI_02 : Verify that the user can add new brand successfully from Edit Brand Info page with valid values");
		/*
			Pre-condition: User has "Edit Company Info" rights only.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			VP: Verify that the "Add" link is displayed next to "Brands" label
			5. Click "Add" link
			VP: Verify that the "Edit Brand Info" page is displayed
			6. Fill valid value into all fields of "Edit Brand Info" page
			7. Click "Save" link
		*/
		/*
		 * Pre-condition: User has "Edit Company Info" rights only
		 */
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a user from table
		home.selectUserByEmail(dtsUser);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable all privileges
		home.disableAllPrivilege(AddUser.PRIVILEGES_TABLE);
		// Enable "Edit Company Info" privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.click(AddUser.SAVE);
		// Logout
		home.logout();
		/*
		 * *********************************************************
		 */
		// 2. Log into DTS portal as a DTS user successfully
		home.login(dtsUser, dtsPass);
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		/*
		 * VP: Verify that the "Add" link is displayed next to "Brands" label
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.ADD_BRAND));
		// 5. Click "Add" link
		home.click(CompanyInfo.ADD_BRAND);
		/*
		 * Verify that the "Edit Brand Info" page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddBrand.getHash()).getResult(), "Pass");
		// 6. Fill valid value into all fields of "Edit Brand Info" page
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.brandDraft();
		home.addBrand(AddBrand.getHash(), data);
		// Check if PDPP-902 found
		if(home.isElementPresent(BrandInfo.EDIT_BRAND)){
			result.addErrorLog("PDPP-902: 063D Brand Info: DTS user is able to manage brands although the 'Edit brand info' privilege is disabled");
			Assert.assertTrue(false);
		}
		/*
		 * Verify that The 063P Brand Ingo Page is displayed with new information correctly without "Edit" and "Delete" link
		 */
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(), "Pass");
		Assert.assertFalse(home.isElementPresent(BrandInfo.EDIT_BRAND));
		Assert.assertFalse(home.isElementPresent(BrandInfo.DELETE_LINK));
	}
	
	/*
	 * Verify that new brand is not added when user selects "Cancel" link in Actions module of Edit Brand Info page
	 */
	@Test
	public void TC072DEBI_03() {
		result.addLog("ID TC072DEBI_03 : Verify that new brand is not added when user selects 'Cancel' link in Actions module of Edit Brand Info page");
		/*
			Pre-condition: User has "Edit Company Info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			VP: Verify that the "Add" link is displayed next to "Brands" label
			5. Click "Add" link
			VP: Verify that the "Edit Brand Info" page is displayed
			6. Fill valid value into all fields of "Edit Brand Info" page
			7. Click "Cancel" link in the Action module
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		/*
		 * VP: Verify that the "Add" link is displayed next to "Brands" label
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.ADD_BRAND));
		// 5. Click "Add" link
		home.click(CompanyInfo.ADD_BRAND);
		/*
		 * Verify that the "Edit Brand Info" page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddBrand.getHash()).getResult(), "Pass");
		// 6. Fill valid value into all fields of "Edit Brand Info" page
		Hashtable<String,String> data = TestData.brandDraft();
		data.remove("save");
		home.addBrand(AddBrand.getHash(), data);		
		// 7. Click "Save" link
		home.click(AddBrand.CANCEL);
		/*
		 * Verify that The 061D Company Page is displayed and there is no new brand's name and logo added
		 */
		Assert.assertEquals(home.existsElement(CompanyInfo.getListElement()).getResult(), "Pass");
		Assert.assertFalse(home.checkBrandExist(data.get("name")));
	}
	
	/*
	 * Verify that the Consumer Brand Logo could be uploaded with three resolution kinds successfully
	 */
	@Test
	public void TC072DEBI_04() {
		result.addLog("ID TC072DEBI_04 : Verify that the Consumer Brand Logo could be uploaded with three resolution kinds successfully");
		/*
			Pre-condition: User has "Edit Company Info" rights only.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			VP: Verify that the "Add" link is displayed next to "Brands" label
			5. Click "Add" link
			VP: Verify that the "Edit Brand Info" page is displayed
			6. Fill valid value into all fields of "Edit Brand Info" page
			7. Upload image for 250x250 resolution type
			VP: Verify that the image for 250x250 is uploaded successfully
			8. Upload image for 500x500 resolution type
			VP: Verify that the image for 500x500 is uploaded successfully
			9. Upload image for 1000x1000 resolution type
			VP: Verify that the image for 1000x1000 is uploaded successfully
			10. Click "Save" link
		*/
		/*
		 * Pre-condition: User has "Edit Company Info" rights only
		 */
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a user from table
		home.selectUserByEmail(dtsUser);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable all privileges
		home.disableAllPrivilege(AddUser.PRIVILEGES_TABLE);
		// Enable "Edit Company Info" privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.click(AddUser.SAVE);
		// Logout
		home.logout();
		/*
		 * *********************************************************
		 */
		// 2. Log into DTS portal as a DTS user successfully
		home.login(dtsUser, dtsPass);
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		/*
		 * VP: Verify that the "Add" link is displayed next to "Brands" label
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.ADD_BRAND));
		// 5. Click "Add" link
		home.click(CompanyInfo.ADD_BRAND);
		/*
		 * Verify that the "Edit Brand Info" page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddBrand.getHash()).getResult(), "Pass");
		// 6. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.brandDraft();
		data.remove("save");
		home.addBrand(AddBrand.getHash(), data);
		// 7. Upload image for 160x160 resolution type
		home.uploadFile(AddBrand.IMAGE250, Constant.IMG_NAME[0]);
		/*
		 * VP: Verify that the image for 160x160 is uploaded successfully
		 */
		Assert.assertTrue(home.getAtribute(AddBrand.IMAGE250_DISPLAY, "src").contains(Constant.IMG_NAME[0]));
		// 8. Upload image for 290x290 resolution type
		home.uploadFile(AddBrand.IMAGE500, Constant.IMG_NAME[1]);
		/*
		 * VP: Verify that the image for 290x290 is uploaded successfully
		 */
		Assert.assertTrue(home.getAtribute(AddBrand.IMAGE500_DISPLAY, "src").contains(Constant.IMG_NAME[1]));
		// 9. Upload image for 664x664 resolution type
		home.uploadFile(AddBrand.IMAGE1000, Constant.IMG_NAME[2]);
		/*
		 * VP: Verify that the image for 664x664 is uploaded successfully
		 */
		Assert.assertTrue(home.getAtribute(AddBrand.IMAGE1000_DISPLAY, "src").contains(Constant.IMG_NAME[2]));
		// 10. Click "Save" link
		home.click(AddBrand.SAVE);
		// Check if PDPP-902 found
		if(home.isElementPresent(BrandInfo.EDIT_BRAND)){
			result.addErrorLog("PDPP-902: 063D Brand Info: DTS user is able to manage brands although the 'Edit brand info' privilege is disabled");
			Assert.assertTrue(false);
		}
		/*
		 * Verify that The 063P Brand Ingo Page is displayed with new brand information and three type of logos correctly without "Edit" and "Delete" link
		 */
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(), "Pass");
		Assert.assertFalse(home.isElementPresent(BrandInfo.EDIT_BRAND));
		Assert.assertFalse(home.isElementPresent(BrandInfo.DELETE_LINK));
		/*
		 *  PostCondition: Re-Enable all privilege for user above
		 */
		// Logout
		home.logout();
		// Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a user from table
		home.selectUserByEmail(dtsUser);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Enable all privileges
		home.enableAllPrivilege();
		home.click(AddUser.SAVE);
	}
	
	/*
	 * Verify that three resolution types of Consumer Brand Logos could be deleted successfully right after uploaded
	 */
	@Test
	public void TC072DEBI_05() {
		result.addLog("ID TC072DEBI_05 : Verify that three resolution types of Consumer Brand Logos could be deleted successfully right after uploaded");
		/*
			Pre-condition: User has "Edit Company Info" rights only.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			VP: Verify that the "Add" link is displayed next to "Brands" label
			5. Click "Add New Brand" link
			VP: Verify that the "Edit Brand Info" page is displayed
			6. Fill valid value into all fields of "Edit Brand Info" page
			7. Upload image for 250x250 resolution type
			8. Upload image for 500x500 resolution type
			9. Upload image for 1000x1000 resolution type
			10. Click "Save" link
			VP: All images are uploaded successfully
			11. Click "Edit" link
			12. Delete all brand's logo types
			13. Click "Save" link
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		/*
		 * VP: Verify that the "Add" link is displayed next to "Brands" label
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.ADD_BRAND));
		// 5. Click "Add New Brand" link
		home.click(CompanyInfo.ADD_BRAND);
		/*
		 * VP: Verify that the "Edit Brand Info" page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddBrand.getHash()).getResult(), "Pass");
		// 6. Fill valid value into all required fields
		// 7. Upload image for 160x160 resolution type
		// 8. Upload image for 290x290 resolution type
		// 9. Upload image for 664x664 resolution type
		// 10. Click "Save" link
		Hashtable<String,String> data = TestData.brandFull();
		home.addBrand(AddBrand.getHash(), data);
		/*
		 * VP: All images are uploaded successfully
		 */
		Assert.assertTrue(home.getAtribute(BrandInfo.BRAND_LOGO_250, "src").contains(Constant.IMG_NAME[0]));
		Assert.assertTrue(home.getAtribute(BrandInfo.BRAND_LOGO_500, "src").contains(Constant.IMG_NAME[1]));
		Assert.assertTrue(home.getAtribute(BrandInfo.BRAND_LOGO_1000, "src").contains(Constant.IMG_NAME[2]));
		// 11. Click "Edit" link
		home.click(BrandInfo.EDIT_BRAND);
		// 12. Delete all brand's logo types
		home.click(AddBrand.DELELE_IMAGE250);
		home.click(AddBrand.DELELE_IMAGE500);
		home.click(AddBrand.DELELE_IMAGE1000);
		// 13. Click "Save" link
		home.click(AddBrand.SAVE);
		/*
		 * Verify that All three logo types are deleted successfully
		 */
		Assert.assertFalse(home.isElementPresent(BrandInfo.BRAND_LOGO_250));
		Assert.assertFalse(home.isElementPresent(BrandInfo.BRAND_LOGO_500));
		Assert.assertFalse(home.isElementPresent(BrandInfo.BRAND_LOGO_1000));
		// Delete brand
		home.doDelete(BrandInfo.DELETE_LINK);
	}
	
	/*
	 * Verify that three resolution types of Consumer Brand Logos could be uploaded again after deleted
	 */
	@Test
	public void TC072DEBI_06() {
		result.addLog("ID TC072DEBI_06 : Verify that three resolution types of Consumer Brand Logos could be uploaded again after deleted");
		/*
			Pre-condition: User has "Edit Company Info" rights only.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			VP: Verify that the "Add" link is displayed next to "Brands" label
			5. Click "Add New Brand" link
			VP: Verify that the "Edit Brand Info" page is displayed
			6. Fill valid value into all fields of "Edit Brand Info" page
			7. Upload image for 250x250 resolution type
			8. Upload image for 500x500 resolution type
			9. Upload image for 1000x1000 resolution type
			10. Click "Save" link
			VP: All images are uploaded successfully
			11. Click "Edit" link
			12. Delete all brand's logo types
			13. Click "Save" link
			VP: All three logo types are deleted successfully
			14. Click "Edit" link
			15. Repeat from step 7 to 10
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		/*
		 * VP: Verify that the "Add" link is displayed next to "Brands" label
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.ADD_BRAND));
		// 5. Click "Add New Brand" link
		home.click(CompanyInfo.ADD_BRAND);
		/*
		 * VP: Verify that the "Edit Brand Info" page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddBrand.getHash()).getResult(), "Pass");
		// 6. Fill valid value into all required fields
		// 7. Upload image for 160x160 resolution type
		// 8. Upload image for 290x290 resolution type
		// 9. Upload image for 664x664 resolution type
		// 10. Click "Save" link
		Hashtable<String,String> data = TestData.brandFull();
		home.addBrand(AddBrand.getHash(), data);
		/*
		 * VP: All images are uploaded successfully
		 */
		Assert.assertTrue(home.getAtribute(BrandInfo.BRAND_LOGO_250, "src").contains(Constant.IMG_NAME[0]));
		Assert.assertTrue(home.getAtribute(BrandInfo.BRAND_LOGO_500, "src").contains(Constant.IMG_NAME[1]));
		Assert.assertTrue(home.getAtribute(BrandInfo.BRAND_LOGO_1000, "src").contains(Constant.IMG_NAME[2]));
		// 11. Click "Edit" link
		home.click(BrandInfo.EDIT_BRAND);
		// 12. Delete all brand's logo types
		home.click(AddBrand.DELELE_IMAGE250);
		home.click(AddBrand.DELELE_IMAGE500);
		home.click(AddBrand.DELELE_IMAGE1000);
		// 13. Click "Save" link
		home.click(AddBrand.SAVE);
		/*
		 * VP: All three logo types are deleted successfully
		 */
		Assert.assertFalse(home.isElementPresent(BrandInfo.BRAND_LOGO_250));
		Assert.assertFalse(home.isElementPresent(BrandInfo.BRAND_LOGO_500));
		Assert.assertFalse(home.isElementPresent(BrandInfo.BRAND_LOGO_1000));
		// 14. Click "Edit" link
		home.click(BrandInfo.EDIT_BRAND);
		// 15. Repeat from step 7 to 10
		home.uploadFile(AddBrand.IMAGE250, Constant.IMG_NAME[0]);
		home.uploadFile(AddBrand.IMAGE500, Constant.IMG_NAME[1]);
		home.uploadFile(AddBrand.IMAGE1000, Constant.IMG_NAME[2]);
		home.click(AddBrand.SAVE);
		/*
		 * All three logo types are uploaded successfully
		 */
		Assert.assertTrue(home.getAtribute(BrandInfo.BRAND_LOGO_250, "src").contains(Constant.IMG_NAME[0]));
		Assert.assertTrue(home.getAtribute(BrandInfo.BRAND_LOGO_500, "src").contains(Constant.IMG_NAME[1]));
		Assert.assertTrue(home.getAtribute(BrandInfo.BRAND_LOGO_1000, "src").contains(Constant.IMG_NAME[2]));
		// Delete brand
		home.doDelete(BrandInfo.DELETE_LINK);
	}
	
	/*
	 * Verify that the lightbox style popup is displayed when clicking on three size of brand logo
	 */
	@Test
	public void TC072DEBI_07() {
		result.addLog("ID TC072DEBI_07 : Verify that the lightbox style popup is displayed when clicking on three size of brand logo");
		/*
			Pre-condition: User has "Edit Company Info" rights only.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			VP: Verify that the "Add" link is displayed next to "Brands" label
			5. Click "Add New Brand" link
			VP: Verify that the "Edit Brand Info" page is displayed
			6. Fill valid value into all fields of "Edit Brand Info" page
			7. Upload image for both three size of brand logo successfully
			8. Click on each brand logo image
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		/*
		 * VP: Verify that the "Add" link is displayed next to "Brands" label
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.ADD_BRAND));
		// 5. Click "Add New Brand" link
		home.click(CompanyInfo.ADD_BRAND);
		/*
		 * VP: Verify that the "Edit Brand Info" page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddBrand.getHash()).getResult(), "Pass");
		// 6. Fill valid value into all fields of "Edit Brand Info" page
		// 7. Upload image for both three size of brand logo successfully	
		home.uploadFile(AddBrand.IMAGE250, Constant.IMG_NAME[0]);
		home.uploadFile(AddBrand.IMAGE500, Constant.IMG_NAME[1]);
		home.uploadFile(AddBrand.IMAGE1000, Constant.IMG_NAME[2]);
		// 8. Click on brand logo 250
		home.click(AddBrand.IMAGE250_DISPLAY);
		home.waitForElementClickable(AddBrand.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(AddBrand.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddBrand.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[0].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(AddBrand.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(AddBrand.LIGHTBOX_STYLE_IMAGE);
		// Click on brand logo 500
		home.click(AddBrand.IMAGE500_DISPLAY);
		home.waitForElementClickable(AddBrand.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(AddBrand.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddBrand.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[1].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(AddBrand.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(AddBrand.LIGHTBOX_STYLE_IMAGE);
		// Click on brand logo 1000
		home.click(AddBrand.IMAGE1000_DISPLAY);
		home.waitForElementClickable(AddBrand.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(AddBrand.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddBrand.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[2].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(AddBrand.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(AddBrand.LIGHTBOX_STYLE_IMAGE);
	}
}
