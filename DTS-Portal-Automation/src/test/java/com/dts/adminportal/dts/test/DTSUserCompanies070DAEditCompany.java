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
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AddCompany;
import dts.com.adminportal.model.Companies;
import dts.com.adminportal.model.CompanyInfo;
import dts.com.adminportal.model.XPartnerShipPackage;
import dts.com.adminportal.model.Xpath;

public class DTSUserCompanies070DAEditCompany extends CreatePage {
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
	 * Verify that the "Edit Company Info" page displays company's info correctly
	 */
	@Test
	public void TC070DAEC_01() {
		result.addLog("ID TC070DAEC_01 : Verify that the 'Edit Company Info' page displays company's info correctly");
		/*
	  		Pre-condition: User has "Edit Company Info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			VP: Verify that the "Edit" link is displayed next to "Company Info" label
			5. Click "Edit" link
			VP: Verify that the "Edit Company Info" page is displayed
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		/*
		 * VP: Verify that the "Edit" link is displayed next to "Company Info" label
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.EDIT));
		// 5. Click "Edit" link
		home.click(CompanyInfo.EDIT);
		/*
		 * VP: Verify that the "Edit Company Info" page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddCompany.getHash()).getResult(),"Pass");
	}
	
	/*
	 * Verify that the company's info could not be save when leaving the "Official Corporate Name" empty in "Edit Company Info" page.
	 */
	@Test
	public void TC070DAEC_02() {
		result.addLog("ID TC070DAEC_02 : Verify that the company's info could not be save when leaving the 'Official Corporate Name' empty in 'Edit Company Info' page.");
		/*
	  		Pre-condition: User has "Edit Company Info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			VP: Verify that the "Edit" link is displayed next to "Company Info" label
			5. Click "Edit" link
			VP: Verify that the "Edit Company Info" page is displayed
			6. Leave the "Official Corporate Name" field blank
			7. Click "Save" link
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		/*
		 * VP: Verify that the "Edit" link is displayed next to "Company Info" label
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.EDIT));
		// 5. Click "Edit" link
		home.click(CompanyInfo.EDIT);
		/*
		 * VP: Verify that the "Edit Company Info" page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddCompany.getHash()).getResult(), "Pass");
		// 6. Leave the "Official Corporate Name" field blank
		home.editData(AddCompany.OFFICIAL_CORP_NAME, "");
		// 7. Click "Save" link
		home.click(AddCompany.SAVE);
		/*
		 * Verify that User is unable to save due to an error message which mentioning that Official Corporate Name is required
		 */
		Assert.assertTrue(home.checkMessageDisplay("Corporate Name is required"));
	}
	
	/*
	 * Verify that the company's info could be changed successfully when clicking "Save" link in "Edit Company Info" page.
	 */
	@Test
	public void TC070DAEC_03() {
		result.addLog("ID TC070DAEC_03 : Verify that the company's info could be changed successfully when clicking 'Save' link in 'Edit Company Info' page");
		/*
	  		Pre-condition: User has "Edit Company Info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			VP: Verify that the "Edit" link is displayed next to "Company Info" label
			5. Click "Edit" link
			VP: Verify that the "Edit Company Info" page is displayed
			6. Change any value of company's info
			7. Click "Save" link
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		/*
		 * VP: Verify that the "Edit" link is displayed next to "Company Info" label
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.EDIT));
		// 5. Click "Edit" link
		home.click(CompanyInfo.EDIT);
		/*
		 * VP: Verify that the "Edit Company Info" page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddCompany.getHash()).getResult(), "Pass");
		// 6. Change any value of company's info
		String newAddress = "New Address" + RandomStringUtils.randomNumeric(10);
		home.editData(AddCompany.ADDRESS1, newAddress);
		// 7. Click "Save" link
		home.click(AddCompany.SAVE);
		/*
		 * Verify that The 061P Company Page is displayed with correct info changed
		 */
		Assert.assertEquals(home.getTextByXpath(CompanyInfo.ADDRESS1), newAddress);
	}
	
	/*
	 * Verify that the partner's company info is restored to original when clicking on "Cancel" link in "Edit Company Info" page.
	 */
	@Test
	public void TC070DAEC_04() {
		result.addLog("ID TC070DAEC_04 : Verify that the partner's company info is restored to original when clicking on 'Cancel' link in 'Edit Company Info' page.");
		/*
	  		Pre-condition: User has "Edit Company Info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			VP: Verify that the "Edit" link is displayed next to "Company Info" label
			5. Click "Edit" link
			VP: Verify that the "Edit Company Info" page is displayed
			6. Change any value of company's info
			7. Click "Cancel" link
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		/*
		 * VP: Verify that the "Edit" link is displayed next to "Company Info" label
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.EDIT));
		// 5. Click "Edit" link
		home.click(CompanyInfo.EDIT);
		/*
		 * VP: Verify that the "Edit Company Info" page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddCompany.getHash()).getResult(), "Pass");
		// 6. Change any value of company's info
		String oldAddress = home.getAtribute(AddCompany.ADDRESS1, "value");
		String newAddress = "New Address" + RandomStringUtils.randomNumeric(10);
		home.editData(AddCompany.ADDRESS1, newAddress);
		// 7. Click "Cancel" link
		home.click(AddCompany.CANCEL);
		/*
		 * Verify that The 061P Company Page is displayed with previous company's information
		 */
		Assert.assertEquals(home.getTextByXpath(CompanyInfo.ADDRESS1), oldAddress);
	}
	
	/*
	 * Verify that the generic international friendly address form based on Amazon.com's form design is used for company's Corporate Address
	 */
	@Test
	public void TC070DAEC_05() {
		result.addLog("ID TC070DAEC_05 : Verify that the generic international friendly address form based on Amazon.com's form design is used for company's Corporate Address");
		/*
	  		Pre-condition: User has "Edit Company Info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			VP: Verify that the "Edit" link is displayed next to "Company Info" label
			4. Click "Edit" link
			VP: Verify that the "Edit Company Info" page is displayed
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// Selete a company on table
		home.selectACompanyByName(partner_company_name);
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
	 * Verify that user is able to save Company Info successfully when leaving all Corporate Address's fields blank
	 */
	@Test
	public void TC070DAEC_06() {
		result.addLog("ID TC070DAEC_06 : Verify that user is able to save Company Info successfully when leaving all Corporate Address's fields blank");
		/*
	  		Pre-condition: User has "Edit Company Info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			VP: Verify that the "Edit" link is displayed next to "Company Info" label
			5. Click "Edit" link
			VP: Verify that the "Edit Company Info" page is displayed
			6. Leave all Corporate Address's field blank
			7. Click "Save" link
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		/*
		 * VP: Verify that the "Edit" link is displayed next to "Company Info" label
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.EDIT));
		// 5. Click "Edit" link
		home.click(CompanyInfo.EDIT);
		/*
		 * VP: Verify that the "Edit Company Info" page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddCompany.getHash()).getResult(), "Pass");
		// 6. Leave all Corporate Address's field blank
		home.editData(AddCompany.ADDRESS1, "");
		home.editData(AddCompany.ADDRESS2, "");
		home.editData(AddCompany.ADDRESS3, "");
		// 7. Click "Save" link
		home.click(AddCompany.SAVE);
		/*
		 * Verify that the error messages is displayed
		 */
		Assert.assertTrue(home.checkMessageDisplay("Address is required"));
	}
	
	/*
	 * Verify that the user can edit Headphone:X Partnerships type
	 */
	@Test
	public void TC070DAEC_07() {
		result.addLog("ID TC070DAEC_07 : Verify that the user can not edit Headphone:X Partnerships type");
		/*
		  	Pre-condition: User has full site privileges rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company in companies table
			5. Click “Edit” link
			6. Add more Headphone:X Partnerships
			VP: The Headphone:X Partnerships is able to add more.
			7. Click “Delete” link
			VP: The Headphone:X Partnerships is able to delete.
		*/
		/*
		 * PreCondition: Create new company
		 */
		// Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// Click Add Company link
		home.click(Companies.ADD_COMPANY);
		// Create company
		Hashtable<String,String> data = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), data);
		/*
		 * *************************************************************
		 */
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(data.get("name"));
		/*
		 * VP: Verify that the "Edit" link is displayed next to "Company Info" label
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.EDIT));
		// 5. Click "Edit" link
		home.click(CompanyInfo.EDIT);
		// 6. Add more Headphone:X Partnerships
		home.selectOptionByName(AddCompany.PARTNERSHIPS, AddCompany.PARTNERSHIP_LIST[1]);
		home.click(AddCompany.ADD);
		// Get all Partnerships
		ArrayList<XPartnerShipPackage> partnershipPackages = home.getPartnershipPackage(AddCompany.PARTNERSHIPS_TABLE);
		/*
		 * VP: The Headphone:X Partnerships is able to add more
		 */
		Assert.assertEquals(partnershipPackages.get(partnershipPackages.size() - 1).type, AddCompany.PARTNERSHIP_LIST[1]);
		// 7. Click “Delete” link
		home.click(partnershipPackages.get(partnershipPackages.size() - 1).deletelink);
		/*
		 * VP: The Headphone:X Partnerships is able to delete
		 */
		Assert.assertTrue(!home.getTextByXpath(AddCompany.PARTNERSHIPS_TABLE).contains(AddCompany.PARTNERSHIP_LIST[1]));
		// Delete company
		home.click(AddCompany.CANCEL);
		home.doDelete(CompanyInfo.DELETE);
	}
}
