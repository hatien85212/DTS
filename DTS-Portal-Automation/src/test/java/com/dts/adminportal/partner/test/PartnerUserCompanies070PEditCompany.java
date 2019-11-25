package com.dts.adminportal.partner.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;

import dts.com.adminportal.model.AddCompany;
import dts.com.adminportal.model.CompanyInfo;
import dts.com.adminportal.model.PartnerCompanyEdit;
import dts.com.adminportal.model.PartnerCompanyInfo;
import dts.com.adminportal.model.Xpath;

public class PartnerUserCompanies070PEditCompany extends CreatePage{
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
	 * Verify that the "Edit Company Info" page displays company's info correctly.
	 */
	@Test
	public void TC070PEC_01(){
		result.addLog("ID : TC070PEC_01 : Verify that the 'Edit Company Info' page displays company's info correctly.");
		/*
		Pre-condition: partner user has "Edit Company Info" rights.
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click "Companies" tab
		VP: Verify that the "Edit" link is displayed next to "Company Info" label
		4. Click "Edit" link
		VP: Verify that the "Edit Company Info" page is displayed
		*/
		
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		//VP: Verify that the "Edit" link is displayed next to "Company Info" label
		result = home.existsElement(PartnerCompanyInfo.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
		//4. Click "Edit" link
		home.click(PartnerCompanyInfo.EDIT);
		//VP: Verify that the "Edit Company Info" page is displayed
		result = home.existsElement(PartnerCompanyEdit.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
	}
	
	/*
	 * Verify that the company's info could not be save when leaving the "Official Corporate Name" empty in "Edit Company Info" page.
	 */
	
	@Test
	public void TC070PEC_02(){
		result.addLog("ID : TC070PEC_02 : Verify that the company's info could not be save when leaving the 'Official Corporate Name' empty in 'Edit Company Info' page.");
		/*
		Pre-condition: partner user has "Edit Company Info" rights.
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click "Companies" tab
		VP: Verify that the "Edit" link is displayed next to "Company Info" label
		4. Click "Edit" link
		VP: Verify that the "Edit Company Info" page is displayed
		6. Leave the "Official Corporate Name" field blank
		7. Click "Save" link
		*/
		
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		//VP: Verify that the "Edit" link is displayed next to "Company Info" label
		result = home.existsElement(PartnerCompanyInfo.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
		//4. Click "Edit" link
		home.click(PartnerCompanyInfo.EDIT);
		//VP: Verify that the "Edit Company Info" page is displayed
		result = home.existsElement(PartnerCompanyEdit.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
		//6. Leave the "Official Corporate Name" field blank
		home.type(PartnerCompanyEdit.OFFICIAL_CORP_NAME, "");
		//7. Click "Save" link
		home.click(PartnerCompanyEdit.SAVE);
		// the company's info could not be save when leaving the "Official Corporate Name" empty in "Edit Company Info" page.
		Assert.assertTrue(home.checkMessageDisplay("Corporate Name is required"));
	}
	
	/*
	 * Verify that the company's info could be changed successfully when clicking "Save" link in "Edit Company Info" page
	 */
	@Test
	public void TC070PEC_03(){
		result.addLog("ID : TC070PEC_03 : Verify that the company's info could be changed successfully when clicking 'Save' link in 'Edit Company Info' page");
		/*
	Pre-condition: partner user has "Edit Company Info" rights.
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click "Companies" tab
		VP: Verify that the "Edit" link is displayed next to "Company Info" label
		4. Click "Edit" link
		VP: Verify that the "Edit Company Info" page is displayed
		5. Change any value of company's info
		6. Click "Save" link
		*/
		
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		/*
		 * VP: Verify that the "Edit" link is displayed next to "Company Info" label
		 */
		Assert.assertTrue(home.isElementPresent(PartnerCompanyInfo.COMPANY_INFO));
		Assert.assertTrue(home.isElementPresent(PartnerCompanyInfo.EDIT));
		// 4. Click "Edit" link
		home.click(PartnerCompanyInfo.EDIT);
		/*
		 * VP: Verify that the "Edit Company Info" page is displayed
		 */
		Assert.assertEquals(home.existsElement(PartnerCompanyEdit.getListXpath()).getResult(), "Pass");
		// 5. Change any value of company's info
		String new_address = "New address " + RandomStringUtils.randomNumeric(5);
		home.editData(PartnerCompanyEdit.ADDRESS1, new_address);
		// 6. Click "Save" link
		home.click(PartnerCompanyEdit.SAVE);
		/*
		 * The 061P Company Page is displayed with correct info changed
		 */
		Assert.assertEquals(home.existsElement(PartnerCompanyInfo.getListXpath()).getResult(), "Pass");
		Assert.assertTrue(home.getTextByXpath(PartnerCompanyInfo.CORP_ADDRESS).contains(new_address));		
	}
	
	/*
	 * Verify that the partner's company info is restored to original when clicking on "Cancel" link in "Edit Company Info" page.
	 */
	@Test
	public void TC070PEC_04(){
		result.addLog("ID : TC070PEC_04 : Verify that the partner's company info is restored to original when clicking on 'Cancel' link in 'Edit Company Info' page.");
		/*
			Pre-condition: partner user has "Edit Company Info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			VP: Verify that the "Edit" link is displayed next to "Company Info" label
			4. Click "Edit" link
			VP: Verify that the "Edit Company Info" page is displayed
			6. Change any value of company's info
			7. Click "Cancel" link
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		String Name = home.getTextByXpath(PartnerCompanyEdit.OFFICIAL_CORP_NAME);
		//VP: Verify that the "Edit" link is displayed next to "Company Info" label
		result = home.existsElement(PartnerCompanyInfo.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
		//4. Click "Edit" link
		home.click(PartnerCompanyInfo.EDIT);
		//VP: Verify that the "Edit Company Info" page is displayed
		result = home.existsElement(PartnerCompanyEdit.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
		//6. Leave the "Official Corporate Name" field blank
		home.type(PartnerCompanyEdit.OFFICIAL_CORP_NAME, RandomStringUtils.randomAlphabetic(10));
		//7. Click "Cancel" link
		home.click(PartnerCompanyEdit.CANCEL);
		// the company's info could not be save when leaving the "Official Corporate Name" empty in "Edit Company Info" page.
		String NameAfter = home.getTextByXpath(PartnerCompanyEdit.OFFICIAL_CORP_NAME);
		Assert.assertEquals(Name, NameAfter);
	}
	
	/*
	 * Verify that the generic international friendly address form based on Amazon.com's form design is used for company's Corporate Address
	 */
	@Test
	public void TC070PEC_05(){
		result.addLog("ID : TC070PEC_05 : Verify that the generic international friendly address form based on Amazon.com's form design is used for company's Corporate Address");
		/*
			Pre-condition: partner user has "Edit Company Info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			VP: Verify that the "Edit" link is displayed next to "Company Info" label
			4. Click "Edit" link
			VP: Verify that the "Edit Company Info" page is displayed
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		/*
		 * VP: Verify that the "Edit" link is displayed next to "Company Info" label
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.EDIT));
		// 4. Click "Edit" link
		home.click(CompanyInfo.EDIT);
		result.addErrorLog("PDPP-726: 070 Edit Company: The company's corporate address is not implemented as amazon's friendly address form");
		Assert.assertTrue(false);
	}
	
	/*
	 * Verify that user is unable to save Company Info successfully when leaving all Corporate Address's fields blank
	 */
	@Test
	public void TC070PEC_06(){
		result.addLog("ID : TC070PEC_06 : Verify that user is unable to save Company Info successfully when leaving all Corporate Address's fields blank");
		/*
		Pre-condition: partner user has "Edit Company Info" rights.
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click "Companies" tab
		VP: Verify that the "Edit" link is displayed next to "Company Info" label
		4. Click "Edit" link
		VP: Verify that the "Edit Company Info" page is displayed
		5. Leave all Corporate Address's field blank
		6. Click "Save" link
		*/
		
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		/*
		 * VP: Verify that the "Edit" link is displayed next to "Company Info" label
		 */
		Assert.assertTrue(home.isElementPresent(PartnerCompanyInfo.COMPANY_INFO));
		Assert.assertTrue(home.isElementPresent(PartnerCompanyInfo.EDIT));
		// 4. Click "Edit" link
		home.click(PartnerCompanyInfo.EDIT);
		/*
		 * VP: Verify that the "Edit Company Info" page is displayed
		 */
		Assert.assertEquals(home.existsElement(PartnerCompanyEdit.getListXpath()).getResult(), "Pass");
		// 5. Leave all Corporate Address's field blank
		home.editData(PartnerCompanyEdit.ADDRESS1, "");
		home.editData(PartnerCompanyEdit.ADDRESS2, "");
		home.editData(PartnerCompanyEdit.ADDRESS3, "");
		// 6. Click "Save" link
		home.click(PartnerCompanyEdit.SAVE);
		/*
		 * Verify that An error message displayed to notify that Address field is missing and the Company info is not saved successfully
		 */
		String message = "Address is required";
		Assert.assertTrue(home.checkMessageDisplay(message));
	}	
	
	/*
	 * Verify that partner user can not edit Headphone:X Partnerships type
	 */
	@Test
	public void TC070PEC_07(){
		result.addLog("ID : TC070PEC_07 : Verify that partner user can not edit Headphone:X Partnerships type");
		/*
		Pre-condition: partner user has "Edit Company Info" rights.
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click "Companies" tab
		4. Click “Edit” link
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// 4. Click “Edit” link
		home.click(CompanyInfo.EDIT);
		/*
		 * Verify that The Headphone:X Partnerships is un-editable
		 */
		Assert.assertFalse(home.isPartnershipsEditable(AddCompany.PARTNERSHIPS_TABLE));
		}
		
	}
	
