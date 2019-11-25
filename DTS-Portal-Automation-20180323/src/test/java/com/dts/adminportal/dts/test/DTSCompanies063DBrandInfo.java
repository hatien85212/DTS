package com.dts.adminportal.dts.test;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddBrand;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.BrandInfo;
import com.dts.adminportal.model.CompanyInfo;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.util.TestData;

public class DTSCompanies063DBrandInfo extends BasePage {
	
	@Override
	protected void initData() {
	}	
	
	/*
	 * Verify that the portal is redirected to 061P Company Info page when clicking on Company name link in 063D Brand Info page.
	 */
	@Test
	public void TC063DBI_01() {
		companyControl.addLog("ID TC063DBI_01 : Verify that the portal is redirected to 061P "
				+ "Company Info page when clicking on Company name link in 063D Brand Info page.");
		/*
		  	Pre-condition: User has full site privileges rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			5. Click on name of Brand
			6. The 063D Brand Info page displays: Company name, Brand name, Brand Tag Line, Consumer Brand Aliases, Website, Brand Overview, Copyright and Trademark Notice, Consumer Brand Logo and Actions module.
			7. Click on Company name link
			The portal is redirected to 061P Company Info page
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Select a company on table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		// 5. Click on a brand name
		companyControl.selectABrandByName( PARTNER_BRAND_NAME_1);
		/*
		 * Verify that the 063D Brand Info page is displayed
		 */
		Assert.assertTrue(companyControl.existsElement(BrandInfo.getAllField()));
		// 7. Click on Company name link
		companyControl.click(BrandInfo.COMPANY_NAME);
		Assert.assertTrue(companyControl.existsElement(CompanyInfo.getListElement()));
	}
	
	/*
	 * Verify that the company's brand will not be deleted when clicking "Cancel" button on delete confirmation popup in 063D Brand Info page
	 */
	@Test
	public void TC063DBI_06() {
		companyControl.addLog("ID TC063DBI_06 : Verify that the company's brand will not be "
				+ "deleted when clicking 'Cancel' button on delete confirmation popup in 063D Brand Info page");
		/*
	  		Pre-Condition: User has "Edit brand info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			5. Click on name of Brand
			VP:Verify the the 063D Brand Info page is displayed
			6. Click "Delete" link
			VP: Verify that the confirmation popup is showed up with "Cancel" and "Delete" button
			7. Click "Cancel" button
			The delete confirmation popup disappears, the portal still stays at 063D Brand Info and the company's brand will not be deleted.
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Select a company on table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		// 5. Click on name of Brand
		companyControl.selectABrandByName( PARTNER_BRAND_NAME_1);
		/*
		 * VP:Verify the the 063D Brand Info page is displayed
		 */
		Assert.assertEquals(companyControl.existsElement(BrandInfo.getAllField()), true);
		// 6. Click "Delete" link
		companyControl.click(BrandInfo.DELETE_LINK);
		companyControl.waitForElementClickable(PageHome.BTN_CONFIRMATION_DANGER);
		/*
		 * Verify that A confirmation popup with "Cancel" and "Delete" button is displayed
		 */
		Assert.assertEquals(companyControl.getTextByXpath(PageHome.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(companyControl.getTextByXpath(PageHome.BTN_CONFIRMATION_CANCEL), "Cancel");
		// 7. Click "Cancel" button
		companyControl.selectConfirmationDialogOption("Cancel");
		/*
		 * The delete confirmation popup disappears, the portal still stays at 063D Brand Info and the company's brand will not be deleted
		 */
		Assert.assertFalse(companyControl.isElementPresent(PageHome.BTN_CONFIRMATION_DANGER));
		Assert.assertFalse(companyControl.isElementPresent(PageHome.BTN_CONFIRMATION_CANCEL));
		Assert.assertEquals(companyControl.existsElement(BrandInfo.getAllField()), true);
		
	}
	
	/*
	 * Verify that the lightbox style popup is displayed when clicking on three size of brand logo
	 */
	@Test
	public void TC063DBI_10() {
		companyControl.addLog("ID TC063DBI_10 : Verify that the lightbox style popup is displayed when clicking on three size of brand logo");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Companies page
		companyControl.click(PageHome.LINK_COMPANY);
		// Select a company on table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		// Click Add brand link
		companyControl.click(CompanyInfo.ADD_BRAND);
		// Create brand
		Hashtable<String, String> data = TestData.brandFull();
		companyControl.addBrand(AddBrand.getHash(), data);
		/*
		 * ************************************************************
		 */
		// 3. Navigate to companies page
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Select a company from companies table which have at least one brand with three size of brand logo
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		companyControl.selectABrandByName( data.get("name"));
		// 5. Click on brand logo 250
		companyControl.click(BrandInfo.BRAND_LOGO_250);
		companyControl.waitForElementClickable(BrandInfo.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(companyControl.isElementPresent(BrandInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(companyControl.getAtributeValue(BrandInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_250_JPG.getName().replaceAll(".jpg", "")));
		// Close lightbox image
		companyControl.click(BrandInfo.LIGHTBOX_CLOSE);
		companyControl.waitForElementDisappear(BrandInfo.LIGHTBOX_STYLE_IMAGE);
		// Click on brand logo 500
		companyControl.click(BrandInfo.BRAND_LOGO_500);
		companyControl.waitForElementClickable(BrandInfo.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(companyControl.isElementPresent(BrandInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(companyControl.getAtributeValue(BrandInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_500_JPG.getName().replaceAll(".jpg", "")));
		// Close lightbox image
		companyControl.click(BrandInfo.LIGHTBOX_CLOSE);
		companyControl.waitForElementDisappear(BrandInfo.LIGHTBOX_STYLE_IMAGE);
		// Click on brand logo 1000
		companyControl.click(BrandInfo.BRAND_LOGO_1000);
		companyControl.waitForElementClickable(BrandInfo.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(companyControl.isElementPresent(BrandInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(companyControl.getAtributeValue(BrandInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_1000_JPG.getName().replaceAll(".jpg", "")));
		// Close lightbox image
		companyControl.click(BrandInfo.LIGHTBOX_CLOSE);
		companyControl.waitForElementDisappear(BrandInfo.LIGHTBOX_STYLE_IMAGE);
	}
	
}
