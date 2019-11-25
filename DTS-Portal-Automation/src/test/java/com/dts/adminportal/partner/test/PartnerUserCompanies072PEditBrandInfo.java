package com.dts.adminportal.partner.test;

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
import dts.com.adminportal.model.PartnerCompanyInfo;
import dts.com.adminportal.model.PartnerEditBrands;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.Xpath;

public class PartnerUserCompanies072PEditBrandInfo extends CreatePage{
	private HomeController home;
	
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}
	
	@BeforeMethod
	public void loginBeforeTest() {
		home.login(superpartneruser, superpartnerpassword);
	}
	
	/*
	 *Verify that the Edit Brand Info page displays properly.
	 */
	@Test
	public void TC072PEBI_01(){
		result.addLog("ID : TC072PEBI_01:Verify that the Edit Brand Info page displays properly.");
		/*
		 	
			Pre-condition: partner user has "Edit Company Info" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			VP: Verify that the "Add" link is displayed next to "Brands" label
			4. Click "Add" link
		 */
		
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		//VP: Verify that the "Add" link is displayed next to "Brands" label
		Assert.assertTrue(home.isElementPresent(PartnerCompanyInfo.ADD_BRAND));
		//4.Click "Add" link 
		home.click(PartnerCompanyInfo.ADD_BRAND);
		//VP: Verify that the Edit Brand Info page displays properly.
		Assert.assertEquals("Pass", home.existsElement(PartnerEditBrands.getelement()).getResult());
				
	}
	
	/*
	 * Verify that partner user can add new brand successfully from Edit Brand Info page with valid values
	 */
	@Test
	public void TC072PEBI_02(){
		result.addLog("ID : TC072PEBI_02 : Verify that partner user can add new brand successfully from Edit Brand Info page with valid values");
		/*
			Pre-condition: partner user has "Edit Company Info" rights only.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			VP: Verify that the "Add" link is displayed next to "Brands" label
			4. Click "Add" link
			VP: Verify that the "Edit Brand Info" page is displayed
			5. Fill valid value into all fields of "Edit Brand Info" page
			6. Click "Save" link
		 */
		/*
		 * Pre-condition: partner user has "Edit Company Info" rights only
		 */
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		// Select a partner user
		home.selectUserByEmail(partneruser);
		// Click on Edit link
		home.click(UserMgmt.EDIT);
		// Disable all privileges
		home.disableAllPrivilege(AddUser.PRIVILEGES_TABLE);
		// Enable "Edit Company Info" privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.click(AddUser.SAVE);
		// Log out
		home.logout();
		/*
		 * ********************************************************************
		 */
		// 2. Log into DTS portal as a partner user successfully
		home.login(partneruser, password);
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		/*
		 * VP: Verify that the "Add" link is displayed next to "Brands" label
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.ADD_BRAND));
		// 4. Click "Add" link
		home.click(CompanyInfo.ADD_BRAND);
		/*
		 * VP: Verify that the "Edit Brand Info" page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddBrand.getHash()).getResult(), "Pass");
		// 5. Fill valid value into all fields of "Edit Brand Info" page
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.brandDraft();
		home.addBrand(AddBrand.getHash(), data);
		/*
		 * Verify that The 063P Brand Ingo Page is displayed with new information correctly without "Edit" and "Delete" link
		 */
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(), "Pass");
		Assert.assertFalse(home.isElementPresent(BrandInfo.EDIT_BRAND));
		Assert.assertFalse(home.isElementPresent(BrandInfo.DELETE_LINK));
	}
	
	/*
	 * Verify that new brand is not added when user selects "Cancel" link in Actions module of Edit Brand Info page.
	 */
	@Test
	public void TC072PEBI_03(){
		result.addLog("ID : TC072PEBI_03:Verify that new brand is not added when user selects 'Cancel' link in Actions module of Edit Brand Info page.");
		/*
			Pre-condition: partner user has "Edit Company Info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			VP: Verify that the "Add" link is displayed next to "Brands" label
			4. Click "Add" link
			VP: Verify that the "Edit Brand Info" page is displayed
			5. Fill valid value into all fields of "Edit Brand Info" page
			6. Click "Cancel" link in the Action module
		 */
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// VP: Verify that the "Add" link is displayed next to "Brands" label
		Assert.assertTrue(home.isElementPresent(PartnerCompanyInfo.ADD_BRAND));
		// 4.Click "Add" link 
		home.click(PartnerCompanyInfo.ADD_BRAND);
		/*
		 * VP: Verify that the "Edit Brand Info" page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddBrand.getListAddBrand()).getResult(), "Pass");
		// 5. Fill valid value into all fields of "Edit Brand Info" page
		Hashtable<String,String> data = TestData.brandDraft();
		data.remove("save");
		home.addBrand(AddBrand.getHash(), data);
		// 6. Click "Cancel" link in the Action module
		home.click(AddBrand.CANCEL);
		/*
		 * The 061P Company Page is displayed and there is no new brand's name and logo added
		 */
		Assert.assertFalse(home.checkBrandExist(data.get("name")));		
	}
	
	/*
	 * Verify that the Consumer Brand Logo could be uploaded with three resolution kinds successfully
	 */
	@Test
	public void TC072PEBI_04(){
		result.addLog("ID : TC072PEBI_04 : Verify that the Consumer Brand Logo could be uploaded with three resolution kinds successfully");
		/*
			Pre-condition: partner user has "Edit Company Info" rights only.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			VP: Verify that the "Add" link is displayed next to "Brands" label
			4. Click "Add" link
			VP: Verify that the "Edit Brand Info" page is displayed
			5. Fill valid value into all fields of "Edit Brand Info" page
			6. Upload image for 160x160 resolution type
			VP: Verify that the image for 160x160 is uploaded successfully
			7. Upload image for 290x290 resolution type
			VP: Verify that the image for 290x290 is uploaded successfully
			8. Upload image for 664x664 resolution type
			VP: Verify that the image for 664x664 is uploaded successfully
			9. Click "Save" link
		*/
		/*
		 * Pre-condition: partner user has "Edit Company Info" rights only.
		 */
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		// Select a user
		home.selectUserByEmail(partneruser);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable all privileges
		home.disableAllPrivilege(AddUser.PRIVILEGES_TABLE);
		// Enable "Edit Company Info" privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		/*
		 * End Pre-condition
		 */
		// 2. Log into DTS portal as a partner user successfully
		home.login(partneruser, password);
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		/*
		 * VP: Verify that the "Add" link is displayed next to "Brands" label
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.ADD_BRAND));
		// 4. Click "Add" link
		home.click(CompanyInfo.ADD_BRAND);
		/*
		 * VP: Verify that the "Edit Brand Info" page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddBrand.getHash()).getResult(),	"Pass");
		// 5. Fill valid value into all fields of "Edit Brand Info" page
		// 6. Upload image for 160x160 resolution type
		// 7. Upload image for 290x290 resolution type
		// 8. Upload image for 664x664 resolution type
		Hashtable<String,String> data = TestData.brandFull();
		data.remove("save");
		home.addBrand(AddBrand.getHash(), data);
		/*
		 * VP: Verify that the image for 160x160 is uploaded successfully
		 */
		Assert.assertTrue(home.getAtribute(AddBrand.IMAGE250_DISPLAY, "src").contains(data.get("img1")));
		/*
		 * VP: Verify that the image for 290x290 is uploaded successfully
		 */
		Assert.assertTrue(home.getAtribute(AddBrand.IMAGE500_DISPLAY, "src").contains(data.get("img2")));
		/*
		 * VP: Verify that the image for 664x664 is uploaded successfully
		 */
		Assert.assertTrue(home.getAtribute(AddBrand.IMAGE1000_DISPLAY, "src").contains(data.get("img3")));
		// 9. Click "Save" link
		home.click(AddBrand.SAVE);
		/*
		 * Verify that The 063P Brand Info Page is displayed with new brand
		 * information and three type of logos correctly without "Edit" and
		 * "Delete" link
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
		home.selectUserByEmail(partneruser);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Enable all privileges
		home.enableAllPrivilege();
		home.click(AddUser.SAVE);
	}

	/*
	 *Verify that three resolution types of CONSUMER Brand Logos could be deleted successfully right after uploaded.
	 */
	@Test
	public void TC072PEBI_05(){
		result.addLog("ID : TC072PEBI_05:Verify that three resolution types of CONSUMER Brand Logos could be deleted successfully right after uploaded.");
		/*
			Pre-condition: partner user has "Edit Company Info" rights only.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			VP: Verify that the "Add" link is displayed next to "Brands" label
			4. Click "Add" link
			VP: Verify that the "Edit Brand Info" page is displayed
			5. Fill valid value into all fields of "Edit Brand Info" page
			6. Upload image for 160x160 resolution type
			VP: Verify that the image for 160x160 is uploaded successfully
			7. Upload image for 290x290 resolution type
			VP: Verify that the image for 290x290 is uploaded successfully
			8. Upload image for 664x664 resolution type
			VP: Verify that the image for 664x664 is uploaded successfully
			9. Delete all brand's logo types
		 */
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// VP: Verify that the "Add" link is displayed next to "Brands" label
		Assert.assertTrue(home.isElementPresent(CompanyInfo.ADD_BRAND));
		// 4.Click "Add" link 
		home.click(CompanyInfo.ADD_BRAND);
		// VP: Verify that the Edit Brand Info page displays properly.
		Assert.assertEquals(home.existsElement(AddBrand.getHash()).getResult(), "Pass");
		// 5. Fill valid value into all fields of "Edit Brand Info" page
		// 6. Upload image for 160x160 resolution type
		home.uploadFile(AddBrand.IMAGE250, Constant.IMG_NAME[0]);
		// VP: Verify that the image for 160x160 is uploaded successfully
		Assert.assertTrue(home.getAtribute(AddBrand.IMAGE250_DISPLAY, "src").contains(Constant.IMG_NAME[0]));
		// 7. Upload image for 290x290 resolution type
		home.uploadFile(AddBrand.IMAGE500, Constant.IMG_NAME[1]);
		// VP: Verify that the image for 290x290 is uploaded successfully
		Assert.assertTrue(home.getAtribute(AddBrand.IMAGE500_DISPLAY, "src").contains(Constant.IMG_NAME[1]));
		// 8. Upload image for 664x664 resolution type
		home.uploadFile(AddBrand.IMAGE1000, Constant.IMG_NAME[2]);
		//VP: Verify that the image for 664x664 is uploaded successfully
		Assert.assertTrue(home.getAtribute(AddBrand.IMAGE1000_DISPLAY, "src").contains(Constant.IMG_NAME[2]));
		//9. Delete all brand's logo types
		home.click(AddBrand.DELELE_IMAGE250);
		home.click(AddBrand.DELELE_IMAGE500);
		home.click(AddBrand.DELELE_IMAGE1000);
		// Verify that three resolution types of CONSUMER Brand Logos could be deleted successfully right after uploaded.
		Assert.assertFalse(home.isElementPresent(AddBrand.IMAGE250_DISPLAY));
		Assert.assertFalse(home.isElementPresent(AddBrand.IMAGE500_DISPLAY));
		Assert.assertFalse(home.isElementPresent(AddBrand.IMAGE1000_DISPLAY));
	}
	
	/*
	 * Verify that three resolution types of CONSUMER Brand Logos could be uploaded again after deleted.
	 */
	@Test
	public void TC072PEBI_06(){
		result.addLog("ID : TC072PEBI_06: Verify that three resolution types of CONSUMER Brand Logos could be uploaded again after deleted");
		/*
			Pre-condition: partner user has "Edit Company Info" rights only.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			VP: Verify that the "Add" link is displayed next to "Brands" label
			4. Click "Add" link
			VP: Verify that the "Edit Brand Info" page is displayed
			5. Fill valid value into all fields of "Edit Brand Info" page
			6. Upload image for 160x160 resolution type
			VP: Verify that the image for 160x160 is uploaded successfully
			7. Upload image for 290x290 resolution type
			VP: Verify that the image for 290x290 is uploaded successfully
			8. Upload image for 664x664 resolution type
			VP: Verify that the image for 664x664 is uploaded successfully
			9. Delete all brand's logo types
			10. Repeat from step 6 to 8
		 */
		
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		//VP: Verify that the "Add" link is displayed next to "Brands" label
		Assert.assertTrue(home.isElementPresent(PartnerCompanyInfo.ADD_BRAND));
		// 4.Click "Add" link 
		home.click(PartnerCompanyInfo.ADD_BRAND);
		//VP: Verify that the Edit Brand Info page displays properly.
		Assert.assertEquals(home.existsElement(AddBrand.getListAddBrand()).getResult(), "Pass");
		// 5. Fill valid value into all fields of "Edit Brand Info" page
		// 6. Upload image for 160x160 resolution type
		home.uploadFile(AddBrand.IMAGE250, Constant.IMG_NAME[0]);
		/*
		 * VP: Verify that the image for 160x160 is uploaded successfully
		 */
		Assert.assertTrue(home.getAtribute(AddBrand.IMAGE250_DISPLAY, "src").contains(Constant.IMG_NAME[0]));
		// 7. Upload image for 290x290 resolution type
		home.uploadFile(AddBrand.IMAGE500, Constant.IMG_NAME[1]);
		/*
		 * VP: Verify that the image for 290x290 is uploaded successfully
		 */
		Assert.assertTrue(home.getAtribute(AddBrand.IMAGE500_DISPLAY, "src").contains(Constant.IMG_NAME[1]));
		// 8. Upload image for 664x664 resolution type
		home.uploadFile(AddBrand.IMAGE1000, Constant.IMG_NAME[2]);
		/*
		 * VP: Verify that the image for 664x664 is uploaded successfully
		 */
		Assert.assertTrue(home.getAtribute(AddBrand.IMAGE1000_DISPLAY, "src").contains(Constant.IMG_NAME[2]));
		//9. Delete all brand's logo types
		home.click(AddBrand.DELELE_IMAGE250);
		home.click(AddBrand.DELELE_IMAGE500);
		home.click(AddBrand.DELELE_IMAGE1000);
		//10. Repeat from step 6 to 8
		home.uploadFile(AddBrand.IMAGE250, Constant.IMG_NAME[0]);
		home.uploadFile(AddBrand.IMAGE500, Constant.IMG_NAME[1]);
		home.uploadFile(AddBrand.IMAGE1000, Constant.IMG_NAME[2]);
		/*
		 * Verify that All three logo types are uploaded successfully
		 */
		Assert.assertTrue(home.getAtribute(AddBrand.IMAGE250_DISPLAY, "src").contains(Constant.IMG_NAME[0]));
		Assert.assertTrue(home.getAtribute(AddBrand.IMAGE500_DISPLAY, "src").contains(Constant.IMG_NAME[1]));
		Assert.assertTrue(home.getAtribute(AddBrand.IMAGE1000_DISPLAY, "src").contains(Constant.IMG_NAME[2]));
	}
	
	/*
	 * Verify that the lightbox style popup is displayed when clicking on three size of brand logo
	 */
	@Test
	public void TC072PEBI_07(){
		result.addLog("ID : TC072PEBI_07: Verify that the lightbox style popup is displayed when clicking on three size of brand logo");
		/*
			Pre-condition: User has "Edit Company Info" rights only.

		1. Navigate to DTS portal
		2. Log into DTS portal as a Partner user successfully
		3. Click "Companies" tab
		VP: Verify that the "Add" link is displayed next to "Brands" label
		4. Click "Add" link
		VP: Verify that the "Edit Brand Info" page is displayed
		5. Fill valid value into all fields of "Edit Brand Info" page
		6. Upload image for both three size of brand logo successfully
		7. Click on each brand logo image
		 */
		
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// 4.Click "Add" link 
		home.click(PartnerCompanyInfo.ADD_BRAND);
		// 6. Upload image for 160x160 resolution type
		home.uploadFile(AddBrand.IMAGE250, Constant.IMG_NAME[0]);
		// 7. Upload image for 290x290 resolution type
		home.uploadFile(AddBrand.IMAGE500, Constant.IMG_NAME[1]);
		// 8. Upload image for 664x664 resolution type
		home.uploadFile(AddBrand.IMAGE1000, Constant.IMG_NAME[2]);
		// Click on picture of each primary catalog image
		// 250x250
		home.click(AddBrand.IMAGE250_DISPLAY);
		home.waitForElementClickable(AddBrand.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AddBrand.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddBrand.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[0].replaceAll(".jpg", "")));
		home.click(AddBrand.LIGHTBOX_CLOSE);
		//500x500
		home.click(AddBrand.IMAGE500_DISPLAY);
		home.waitForElementClickable(AddBrand.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AddBrand.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddBrand.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[1].replaceAll(".jpg", "")));
		home.click(AddBrand.LIGHTBOX_CLOSE);
		//1000x1000
		home.click(AddBrand.IMAGE1000_DISPLAY);
		home.waitForElementClickable(AddBrand.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AddBrand.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddBrand.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[2].replaceAll(".jpg", "")));
		home.click(AddBrand.LIGHTBOX_CLOSE);
	}
	
	/*
	 * Verify that the lightbox style popup is displayed when clicking on three size of brand logo
	 */
	@Test
	public void TC072PEBI_08(){
		result.addLog("ID : TC072PEBI_08: Verify that partner user is unable to edit and delete brand which is not assigned to manage when the 'Edit brand info' privilege is enabled.");
		/*
			Pre-condition: Partner user has "Edit Brand Info" privilege is enabled but only assigned to manage only one specific brand

			1. Navigate to DTS portal
			2. Log into DTS portal as a Partner user successfully
			3. Click "Company" tab
			4. Select a brand which is out of management of partner user
			VP: The "Edit" and "Delete" link are not displayed.
		 */
		//Tear-up: Partner user has "Edit Brand Info" privilege is enabled but only assigned to manage only one specific brand
		// Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// Select a partner user from the Users table
		home.dtsSelectUserByEmail(partneruser);
		// Click "Edit" link
		home.click(UserMgmt.EDIT);
		// Enable the “Publish and suspend products” privilege but assign for a specific brand
		home.enableAllPrivilege();
		home.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, partner_brand_name_2);
		home.click(AddUser.SAVE);
		home.logout();
		
		//	1. Navigate to DTS portal
		// 2. Log into DTS portal as a Partner user successfully
		home.login(partneruser,password);
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// 4.Select a brand which is out of management of partner user
		home.selectABrandByName(partner_brand_name_1);
		// VP: The "Edit" and "Delete" link are not displayed.
		Assert.assertFalse(home.isElementPresent(BrandInfo.EDIT_BRAND));
		Assert.assertFalse(home.isElementPresent(BrandInfo.DELETE_LINK));
		
		//Teardown:
		home.logout();
		home.login(superUsername, superUserpassword);
		// Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// Select a partner user from the Users table
		home.dtsSelectUserByEmail(partneruser);
		// Click "Edit" link
		home.click(UserMgmt.EDIT);
		// Enable the “Publish and suspend products” privilege but assign for a specific brand
		home.enableAllPrivilege();
		home.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, all_brand);
	}
}
