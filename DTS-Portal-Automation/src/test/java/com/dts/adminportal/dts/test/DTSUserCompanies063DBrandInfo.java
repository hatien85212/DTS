package com.dts.adminportal.dts.test;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.DTSUtil;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AddBrand;
import dts.com.adminportal.model.BrandInfo;
import dts.com.adminportal.model.CompanyInfo;
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.Xpath;

public class DTSUserCompanies063DBrandInfo extends CreatePage {
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
	 * Verify that the 063D Brand Info page is displayed when clicking on brand's name in 061P Company Info
	 */
	@Test
	public void TC063DBI_01() {
		result.addLog("ID TC063DBI_01 : Verify that the 063D Brand Info page is displayed when clicking on brand's name in 061P Company Info");
		/*
		  	Pre-condition: User has full site privileges rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			5. Click on a brand name
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		// 5. Click on a brand name
		home.selectABrandByName( partner_brand_name_1);
		/*
		 * Verify that the 063D Brand Info page is displayed
		 */
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(),"Pass");
	}
	
	/*
	 * Verify that the portal is redirected to 061P Company Info page when clicking on Company name link in 063D Brand Info page
	 */
	@Test
	public void TC063DBI_02() {
		result.addLog("ID TC063DBI_02 : Verify that the portal is redirected to 061P Company Info page when clicking on Company name link in 063D Brand Info page");
		/*
	  		1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			5. Click on name of Brand
			6. Verify the the company name link is displayed in 063D Brand Info page
			7. Click on Company name link
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		// 5. Click on name of Brand
		home.selectABrandByName( partner_brand_name_1);
		// 6. Verify the the company name link is displayed in 063D Brand Info page
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(),"Pass");
		// 7. Click on Company name link
		home.click(BrandInfo.COMPANY_NAME);
		/*
		 * Verify that The portal is redirected to 061P Company Info page
		 */
		Assert.assertEquals(home.existsElement(CompanyInfo.getListElement()).getResult(),"Pass");
	}
	
	/*
	 * Verify that the 063D Brand Info page displays company's brand info properly
	 */
	@Test
	public void TC063DBI_03() {
		result.addLog("ID TC063DBI_03 : Verify that the 063D Brand Info page displays company's brand info properly");
		/*
		  	Pre-condition: User has full site privileges rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			5. Click on name of Brand
			6. Verify the the 063D Brand Info page is displayed
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		// 5. Click on name of Brand
		home.selectABrandByName( partner_brand_name_1);
		// 6. Verify the the 063D Brand Info page is displayed
		/*
		 * The 063D Brand Info page displays: Company name, Brand name, Brand
		 * Tag Line, Consumer Brand Aliases, Website, Brand Overview, Copyright
		 * and Trademark Notice, Consumer Brand Logo and Actions module
		 */
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(),"Pass");
		Assert.assertTrue(home.isElementPresent(BrandInfo.ACTION_MODULE));
	}
	
	/*
	 * Verify that the "edit" and "Delete" link will displays in Brand Info page if user has "Edit brand info" rights
	 */
	@Test
	public void TC063DBI_04() {
		result.addLog("ID TC063DBI_04 : Verify that the 'edit' and 'Delete' link will displays in Brand Info page if user has 'Edit brand info' rights");
		/*
			Pre-Condition: User has "Edit brand info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			5. Click on name of Brand
			6. Verify the the 063D Brand Info page is displayed
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		// 5. Click on name of Brand
		home.selectABrandByName( partner_brand_name_1);
		// 6. Verify the the 063D Brand Info page is displayed
		/*
		 * The "edit" link displays on top of brand info and the "Actions" module with "Delete" link also be displayed
		 */
		Assert.assertTrue(home.isElementPresent(BrandInfo.EDIT_BRAND));
		Assert.assertTrue(home.isElementPresent(BrandInfo.DELETE_LINK));
	}
	
	/*
	 * Verify that the user who has "Edit brand info" rights could delete company's brand successfully
	 */
	@Test
	public void TC063DBI_05() {
		result.addLog("ID TC063DBI_05 : Verify that the user who has 'Edit brand info' rights could delete company's brand successfully");
		/*
			Pre-Condition: User has "Edit brand info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			5. Click on name of Brand
			VP:Verify the the 063D Brand Info page is displayed
			6. Click "Delete" link
			VP: Verify that the confirmation pop-up is showed up with "Cancel" and "Delete" button
			7. Click "Delete" button
		*/
		/*
		 * PreCondition: Create a new brand
		 */
		// Navigate to Companies page
		home.click(Xpath.LINK_COMPANY);
		// Select a company on table
		home.selectACompanyByName(partner_company_name);
		// Click Add brand link
		home.click(CompanyInfo.ADD_BRAND);
		// Create new brand
		Hashtable<String,String> data = TestData.brandDraft();
		home.addBrand(AddBrand.getHash(), data);
		/*
		 * *******************************************************
		 */
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		// 5. Click on name of Brand
		home.selectABrandByName( data.get("name"));
		/*
		 * VP:Verify the the 063D Brand Info page is displayed
		 */
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(), "Pass");
		// 6. Click "Delete" link
		home.click(BrandInfo.DELETE_LINK);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		/*
		 * VP: Verify that the confirmation pop-up is showed up with "Cancel" and "Delete" button
		 */
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_CANCEL), "Cancel");
		// 7. Click "Delete" button
		home.selectConfirmationDialogOption("Delete");
		/*
		 * The portal redirects to 061 Company Info page and the company's brand is deleted successfully
		 */
		Assert.assertEquals(home.existsElement(CompanyInfo.getListElement()).getResult(), "Pass");
		Assert.assertFalse(home.checkBrandExist( data.get("name")));
	}
	
	/*
	 * Verify that the confirmation popup with "Cancel" and "Delete" button is displayed when deleting a company' brand
	 */
	@Test
	public void TC063DBI_06() {
		result.addLog("ID TC063DBI_06 : Verify that the confirmation popup with 'Cancel' and 'Delete' button is displayed when deleting a company' brand");
		/*
	  		Pre-Condition: User has "Edit brand info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			5. Click on name of Brand
			VP:Verify the the 063D Brand Info page is displayed
			6. Click "Delete" link
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		// 5. Click on name of Brand
		home.selectABrandByName( partner_brand_name_1);
		/*
		 * VP:Verify the the 063D Brand Info page is displayed
		 */
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(), "Pass");
		// 6. Click "Delete" link
		home.click(BrandInfo.DELETE_LINK);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		/*
		 * Verify that A confirmation popup with "Cancel" and "Delete" button is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_CANCEL), "Cancel");
	}
	
	/*
	 * Verify that the company's brand will not be deleted when clicking "Cancel" button on delete confirmation popup in 063D Brand Info page
	 */
	@Test
	public void TC063DBI_07() {
		result.addLog("ID TC063DBI_07 : Verify that the company's brand will not be deleted when clicking 'Cancel' button on delete confirmation popup in 063D Brand Info page");
		/*
	  		Pre-Condition: User has "Edit brand info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			5. Click on name of Brand
			VP:Verify the the 063D Brand Info page is displayed
			6. Click "Delete" link
			VP: Verify that the confirmation pop-up is showed up with "Cancel" and "Delete" button
			7. Click "Cancel" button
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		// 5. Click on name of Brand
		home.selectABrandByName( partner_brand_name_1);
		/*
		 * VP:Verify the the 063D Brand Info page is displayed
		 */
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(), "Pass");
		// 6. Click "Delete" link
		home.click(BrandInfo.DELETE_LINK);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		/*
		 * VP: Verify that the confirmation pop-up is showed up with "Cancel" and "Delete" button
		 */
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_DANGER),	"Delete");
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_CANCEL), "Cancel");
		// 7. Click "Cancel" button
		home.selectConfirmationDialogOption("Cancel");
		/*
		 * The delete confirmation popup disappears, the portal still stays at 063D Brand Info and the company's brand will not be deleted
		 */
		Assert.assertFalse(home.isElementPresent(Xpath.BTN_CONFIRMATION_DANGER));
		Assert.assertFalse(home.isElementPresent(Xpath.BTN_CONFIRMATION_CANCEL));
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(), "Pass");
	}
	
	/*
	 * Verify that the existing brand information will displays correctly in 072P Edit Brand Info page
	 */
	@Test
	public void TC063DBI_08() {
		result.addLog("ID TC063DBI_08 : Verify that the existing brand information will displays correctly in 072P Edit Brand Info page");
		/*
	  		Pre-Condition: User has "Edit brand info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			5. Click on name of Brand
			VP:Verify the the 063D Brand Info page is displayed
			6. Get all company' brand info
			7. Click "Edit" link
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		// 5. Click on name of Brand
		home.selectABrandByName( partner_brand_name_1);
		/*
		 * VP:Verify the the 063D Brand Info page is displayed
		 */
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(), "Pass");
		// 6. Get all company' brand info
		ArrayList<String> brandInfo = home.getTextByXpath(BrandInfo.getBrandInfo());
		// 7. Click "Edit" link
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
	 * Verify that the DTS user user who has "Edit brand info" rights could edit company's brand info successfully
	 */
	@Test
	public void TC063DBI_09() {
		result.addLog("ID TC063DBI_09 : Verify that the DTS user user who has 'Edit brand info' rights could edit company's brand info successfully");
		/*
			Pre-Condition: User has "Edit brand info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			5. Click on name of Brand
			VP:Verify the the 063D Brand Info page is displayed
			6. Get all company' brand info
			7. Click "Edit" link
			VP: Verify that the 072P Edit Brand Info page is displayed
			8. Set new info for company's brand
			9.  Click "Save" link.
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		// 5. Click on name of Brand
		home.selectABrandByName( partner_brand_name_1);
		/*
		 * VP:Verify the the 063D Brand Info page is displayed
		 */
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(), "Pass");
		// 6. Get all company' brand info
		ArrayList<String> brandInfo = home.getTextByXpath(BrandInfo.getBrandInfo());
		// 7. Click "Edit" link
		home.click(BrandInfo.EDIT_BRAND);
		// Get all brand info in Edit page
		ArrayList<String> brandEdit = new ArrayList<String>();
		brandEdit.add("Default (English): " + home.getAtribute(AddBrand.NAME, "value"));
		brandEdit.add(home.getAtribute(AddBrand.BRAND_TAG_LINE, "value"));
		brandEdit.add(home.getAtribute(AddBrand.CONSUMER_ALIAS, "value"));
		brandEdit.add(home.getAtribute(AddBrand.WEBSITE, "value"));
		brandEdit.add(home.getAtribute(AddBrand.BRAND_OVERVIEW, "value"));
		brandEdit.add(home.getAtribute(AddBrand.COPYRIGHT_AND_TRADEMARK_NOTICE,	"value"));
		/*
		 * VP: Verify that the 072P Edit Brand Info page is displayed
		 */
		Assert.assertTrue(DTSUtil.containsAll(brandInfo, brandEdit));
		// 8. Set new info for company's brand
		String newTagLine = RandomStringUtils.randomNumeric(10);
		home.editData(AddBrand.BRAND_TAG_LINE, newTagLine);
		// 9.  Click "Save" link
		home.click(AddBrand.SAVE);
		/*
		 * Verify that the 063D Brand Info page is displayed with new information
		 */
		Assert.assertEquals(home.getTextByXpath(BrandInfo.BRAND_TAG_LINE), newTagLine);
	}
	
	/*
	 * Verify that the lightbox style popup is displayed when clicking on three size of brand logo
	 */
	@Test
	public void TC063DBI_10() {
		result.addLog("ID TC063DBI_10 : Verify that the lightbox style popup is displayed when clicking on three size of brand logo");
		/*
			Pre-condition: User has "Edit Company Info" rights only.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to companies page
			4. Select a company from companies table which have at least one brand with three size of brand logo
			5. Click on on brand logo
		*/
		/*
		 * PreCondition: Company which has at lease one brand with three size of brand logo
		 */
		// Navigate to Companies page
		home.click(Xpath.LINK_COMPANY);
		// Select a company on table
		home.selectACompanyByName(partner_company_name);
		// Click Add brand link
		home.click(CompanyInfo.ADD_BRAND);
		// Create brand
		Hashtable<String, String> data = TestData.brandFull();
		home.addBrand(AddBrand.getHash(), data);
		/*
		 * ************************************************************
		 */
		// 3. Navigate to companies page
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company from companies table which have at least one brand with three size of brand logo
		home.selectACompanyByName(partner_company_name);
		home.selectABrandByName( data.get("name"));
		// 5. Click on brand logo 250
		home.click(BrandInfo.BRAND_LOGO_250);
		home.waitForElementClickable(BrandInfo.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(BrandInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(BrandInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[0].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(BrandInfo.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(BrandInfo.LIGHTBOX_STYLE_IMAGE);
		// Click on brand logo 500
		home.click(BrandInfo.BRAND_LOGO_500);
		home.waitForElementClickable(BrandInfo.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(BrandInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(BrandInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[1].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(BrandInfo.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(BrandInfo.LIGHTBOX_STYLE_IMAGE);
		// Click on brand logo 1000
		home.click(BrandInfo.BRAND_LOGO_1000);
		home.waitForElementClickable(BrandInfo.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(BrandInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(BrandInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[2].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(BrandInfo.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(BrandInfo.LIGHTBOX_STYLE_IMAGE);
	}
	
}
