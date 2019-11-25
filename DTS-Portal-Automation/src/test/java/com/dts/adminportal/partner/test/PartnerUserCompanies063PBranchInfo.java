package com.dts.adminportal.partner.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.DTSUtil;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AddBrand;
import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.BrandEdit;
import dts.com.adminportal.model.BrandInfo;
import dts.com.adminportal.model.CompanyInfo;
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.PartnerCompanyInfo;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UserInfo;
import dts.com.adminportal.model.Xpath;

public class PartnerUserCompanies063PBranchInfo extends CreatePage {
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
	 * Verify that the 063P Brand Info page is displayed when clicking on brand's name in 061P Company Info
	 */
	@Test
	public void TC063PBI_01() {
		result.addLog("ID : 063PBI_01 : Verify that the 063P Brand Info page is displayed when clicking on brand's name in 061P Company Info");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			4. Click on name of Brand
		 */
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// 4. Click on name of Brand
		home.selectABrand(CompanyInfo.BRAND_LIST);
		/*
		 * Verify that the 063P Brand Info page is displayed
		 */
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(), "Pass");
	}

	/*
	 * Verify that the portal is redirected to 061P Company Info page when clicking on Company name link in 063P Brand Info page
	 */
	@Test
	public void TC063PBI_02() {
		result.addLog("ID : 063PBI_02 : Verify that the portal is redirected to 061P Company Info page when clicking on Company name link in 063P Brand Info page");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			4. Click on name of Brand
			5. Verify the the company name link is displayed in 063P Brand Info page
			6. Click on Company name link
		 */
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// 4. Click on name of Brand
		home.selectABrand(CompanyInfo.BRAND_LIST);
		/*
		 * Verify the the company name link is displayed in 063P Brand Info page
		 */
		Assert.assertTrue(home.isElementPresent(BrandInfo.COMPANY_NAME));
		// 6. Click on Company name link
		home.click(BrandInfo.COMPANY_NAME);
		/*
		 * Verify that The portal is redirected to 061P Company Info page
		 */
		Assert.assertEquals(home.existsElement(PartnerCompanyInfo.getListXpath()).getResult(), "Pass");
	}

	/*
	 * Verify that the 063P Brand Info page displays company's brand info properly
	 */
	@Test
	public void TC063PBI_03() {
		result.addLog("ID : 063PBI_03 : Verify that the 063P Brand Info page displays company's brand info properly");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			4. Click on name of Brand
			5. Verify the the 063P Brand Info page is displayed
		 */
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// 4. Click on name of Brand
		home.selectABrand(CompanyInfo.BRAND_LIST);
		// 5. Verify the the 063P Brand Info page is displayed
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(), "Pass");
		Assert.assertTrue(home.isElementPresent(BrandInfo.ACTION_MODULE));
	}

	/*
	 * Verify that the "edit" and "Delete" link will displays in Brand Info page if user has "Edit brand info" rights
	 */
	@Test
	public void TC063PBI_04() {
		result.addLog("ID : 063PBI_04 : Verify that the 'edit' and 'Delete' link will displays in Brand Info page if user has 'Edit brand info' rights");
		/*
			Pre-Condition: Partner user has "Edit brand info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			4. Click on name of Brand
			5. Verify the the 063P Brand Info page is displayed
		 */
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// 4. Click on name of Brand
		home.selectABrand(CompanyInfo.BRAND_LIST);
		// 5. Verify the the 063P Brand Info page is displayed
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(), "Pass");
		Assert.assertTrue(home.isElementPresent(BrandInfo.EDIT_BRAND));
		Assert.assertTrue(home.isElementPresent(BrandInfo.DELETE_LINK));
	}

	/*
	 * Verify that partner user who has "Edit brand info" rights could delete company's brand successfully
	 */
	@Test
	public void TC063PBI_05() {
		result.addLog("ID : 063PBI_05 : Verify that partner user who has 'Edit brand info' rights could delete company's brand successfully");
		/*
		 	Pre-Condition: Partner user has "Edit brand info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			4. Click on name of Brand
			VP:Verify the the 063P Brand Info page is displayed
			5. Click "Delete" link
			VP: Verify that the confirmation popup is showed up with "Cancel" and "Delete" button
			6. Click "Delete" button
		 */
		/*
		 * PreCondition: Create new brand
		 */
		// Navigate to Company page
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// Click Add brand link
		home.click(CompanyInfo.ADD_BRAND);
		// Create brand
		Hashtable<String,String> data = TestData.brandDraft();
		home.addBrand(AddBrand.getHash(), data);
		/*
		 * ********************************************************
		 */
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// 4. Click on name of Brand
		home.selectABrandByName(data.get("name"));
		/*
		 * VP:Verify the the 063P Brand Info page is displayed
		 */
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(), "Pass");
		// 5. Click "Delete" link
		home.click(BrandInfo.DELETE_LINK);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		/*
		 * VP: Verify that the confirmation popup is showed up with "Cancel" and "Delete" button
		 */
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_CANCEL), "Cancel");
		// 6. Click "Delete" button
		home.selectConfirmationDialogOption("Delete");
		/*
		 * The portal redirects to 061 Company Info page and the company's brand
		 * is deleted successfully
		 */
		Assert.assertEquals(home.existsElement(PartnerCompanyInfo.getListXpath()).getResult(), "Pass");
		Assert.assertFalse(home.checkBrandExist( data.get("name")));
	}

	/*
	 * Verify that the confirmation popup with "Cancel" and "Delete" button is displayed when deleting a company' brand
	 */
	@Test
	public void TC063PBI_06() {
		result.addLog("ID : 063PBI_06 : Verify that the confirmation popup with 'Cancel' and 'Delete' button is displayed when deleting a company' brand");
		/*
		 	Pre-Condition: Partner user has "Edit brand info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			4. Click on name of Brand
			VP:Verify the the 063P Brand Info page is displayed
			5. Click "Delete" link
		 */
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// 4. Click on name of Brand
		home.selectABrand(CompanyInfo.BRAND_LIST);
		/*
		 * Verify that the 063P Brand Info page is displayed
		 */
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(), "Pass");
		// 5. Click "Delete" link
		home.click(BrandInfo.DELETE_LINK);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		/*
		 * A confirmation popup with "Cancel" and "Delete" button is displayed.
		 */
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_CANCEL), "Cancel");
	}

	/*
	 * Verify that the company's brand will not be deleted when clicking "Cancel" button on delete confirmation popup in 063P Brand Info page
	 */
	@Test
	public void TC063PBI_07() {
		result.addLog("ID : 063PBI_07 : Verify that the company's brand will not be deleted when clicking 'Cancel' button on delete confirmation popup in 063P Brand Info page");
		/*
		 	Pre-Condition: Partner user has "Edit brand info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			4. Click on name of Brand
			VP:Verify the the 063P Brand Info page is displayed
			5. Click "Delete" link
			VP: Verify that the confirmation popup is showed up with "Cancel" and "Delete" button
			6. Click "Cancel" button
		 */
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// 4. Click on name of Brand
		home.selectABrand(CompanyInfo.BRAND_LIST);
		/*
		 * Verify that the 063P Brand Info page is displayed
		 */
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(), "Pass");
		// 5. Click "Delete" link
		home.click(BrandInfo.DELETE_LINK);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		/*
		 * A confirmation popup with "Cancel" and "Delete" button is displayed.
		 */
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_CANCEL), "Cancel");
		// 6. Click "Cancel" button
		home.selectConfirmationDialogOption("Cancel");
		/*
		 * The delete confirmation popup disappears, the portal still stays at
		 * 063P Brand Info and the company's brand will not be deleted.
		 */
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(), "Pass");
	}
	
	/*
	 * Verify that the existing brand information will displays correctly in 072P Edit Brand Info page
	 */
	@Test
	public void TC063PBI_08() {
		result.addLog("ID : 063PBI_08 : Verify that the existing brand information will displays correctly in 072P Edit Brand Info page");
		/*
			Pre-Condition: Partner user has "Edit brand info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			4. Click on name of Brand
			VP:Verify the the 063P Brand Info page is displayed
			5. Get all company' brand info
			6. Click "Edit" link
		 */
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// 4. Click on name of Brand
		home.selectABrand(CompanyInfo.BRAND_LIST);
		/*
		 * VP:Verify the the 063P Brand Info page is displayed
		 */
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(), "Pass");
		// 5. Get all company' brand info
		ArrayList<String> brandInfo = home.getTextByXpath(BrandInfo.getBrandInfo());
		// 6. Click "Edit" link
		home.click(BrandInfo.EDIT_BRAND);
		// Get all brand info in Edit page
		ArrayList<String> brandEdit = new ArrayList<String>();
		brandEdit.add("Default (English): " + home.getAtribute(AddBrand.NAME, "value"));
		brandEdit.add(home.getAtribute(AddBrand.BRAND_TAG_LINE, "value"));
		brandEdit.add(home.getAtribute(AddBrand.CONSUMER_ALIAS, "value"));
		brandEdit.add(home.getAtribute(AddBrand.WEBSITE, "value"));
		brandEdit.add(home.getAtribute(AddBrand.BRAND_OVERVIEW, "value"));
		brandEdit.add(home.getAtribute(AddBrand.COPYRIGHT_AND_TRADEMARK_NOTICE, "value"));
		/*
		 * Verify that the 072P Edit Brand Info page is displayed, all brand's information is displayed in correct position
		 */
		Assert.assertTrue(DTSUtil.containsAll(brandInfo, brandEdit));
	}	
	
	/*
	 * Verify that the partner user who has "Edit brand info" rights could edit company's brand info successfully.
	 */
	@Test
	public void TC063PBI_09() {
		result.addLog("ID : 063PBI_09 : Verify that the partner user who has 'Edit brand info' rights could edit company's brand info successfully.");
		/*
		Pre-Condition: Partner user has "Edit brand info" rights.

		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click "Companies" tab
		4. Click on name of Brand
		VP:Verify the the 063P Brand Info page is displayed
		5. Get all company' brand info
		6. Click "Edit" link
		VP: Verify that the 072P Edit Brand Info page is displayed
		7. Set new info for company's brand
		8.  Click "Save" link.
		 */
		// 3. Click "Companies" tab
		//Precondition:
		home.click(Xpath.LINK_USERS);
		home.selectUserByEmail(partneruser);
		home.click(UserInfo.EDIT);
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_brand_info);
		home.click(AddUser.SAVE);
		home.logout();
		// Log into DTS portal as above partner user
		home.login(partneruser, password);
		// Navigate to company page		
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// 4. Click on name of Brand
		home.selectABrand(CompanyInfo.BRAND_LIST);
		//VP:Verify the the 063P Brand Info page is displayed
		Assert.assertTrue(home.isElementPresent(BrandInfo.NAME));
		// 5. Get all company' brand info
	
		// 6. Click "Edit" link
		home.click(BrandInfo.EDIT_BRAND);
		// Verify that the 072P Edit Brand Info page is displayed
		Assert.assertTrue(home.isElementPresent(BrandEdit.NAME));
		//Change brand info
		Hashtable<String,String> newBrandData= TestData.brandDraft();
		newBrandData.remove("name");
		newBrandData.remove("save");
		home.editData(BrandEdit.BRAND_TAG_LINE, newBrandData.get("tag"));
		home.editData(BrandEdit.CONSUMER_ALIAS, newBrandData.get("alias"));
		home.editData(BrandEdit.WEBSITE, newBrandData.get("website"));
		home.editData(BrandEdit.BRAND_OVERVIEW, newBrandData.get("overview"));
		home.editData(BrandEdit.COPYRIGHT_AND_TRADEMARK_NOTICE, newBrandData.get("notice"));
		// Click Save link
		home.click(BrandEdit.SAVE);
		// Get new brand info
		ArrayList<String> brandInfo = home.getTextByXpath(BrandInfo.getBrandInfo());
		brandInfo.remove("name");
		 // Verify that the 072P Edit Brand Info page is displayed, all brand's information is displayed in correct position
		Assert.assertTrue(DTSUtil.containsAll(brandInfo,newBrandData ));
	}	
	
	/*
	 * Verify that the partner user who has "Edit brand info" rights could edit company's brand info successfully.
	 */
	@Test
	public void TC063PBI_10() {
		result.addLog("ID : 063PBI_10 : Verify that the lightbox style popup is displayed when clicking on three size of brand logo");
		/*
		Pre-condition: User has "Edit Company Info" rights only.

		1. Navigate to DTS portal
		2. Log into DTS portal as a Partner user successfully
		3. Navigate to companies page
		4. Select a company from companies table which have at least one brand with three size of brand logo
		5. Click on on brand logo
		 */
		// 3. Click "Companies" tab
		//Precondition:
		// Navigate to company page		
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// Create new company's brand
		home.click(CompanyInfo.ADD_BRAND);
		Hashtable<String,String> brandData= TestData.brandFull();
		home.addBrand(AddBrand.getHash(), brandData);
		// Verify that new brand is created successfully
		Assert.assertEquals(home.getTextByXpath(BrandInfo.NAME_BRAND), "Default (English): " + brandData.get("name"));
		// Verify the lightbox popup
		// Click on picture of each primary catalog image
		// 250x250
		home.click(BrandInfo.BRAND_LOGO_250);
		home.waitForElementClickable(BrandInfo.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(BrandInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(BrandInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[0].replaceAll(".jpg", "")));
		home.click(BrandInfo.LIGHTBOX_CLOSE);
		//500x500
		home.click(BrandInfo.BRAND_LOGO_500);
		home.waitForElementClickable(BrandInfo.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(BrandInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(BrandInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[1].replaceAll(".jpg", "")));
		home.click(BrandInfo.LIGHTBOX_CLOSE);
		//1000x1000
		home.click(BrandInfo.BRAND_LOGO_1000);
		home.waitForElementClickable(BrandInfo.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(BrandInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(BrandInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[2].replaceAll(".jpg", "")));
		home.click(BrandInfo.LIGHTBOX_CLOSE);
		
		//Teardown: Delete brand
		home.doDelete(BrandInfo.DELETE_LINK);
		
	}	
}
