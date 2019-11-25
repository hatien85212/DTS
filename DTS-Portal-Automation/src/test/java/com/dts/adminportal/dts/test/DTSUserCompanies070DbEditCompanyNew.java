package com.dts.adminportal.dts.test;

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

import dts.com.adminportal.model.AddCompany;
import dts.com.adminportal.model.Companies;
import dts.com.adminportal.model.CompanyContact;
import dts.com.adminportal.model.CompanyInfo;
import dts.com.adminportal.model.PartnerCompanyInfo;
import dts.com.adminportal.model.Xpath;

public class DTSUserCompanies070DbEditCompanyNew extends CreatePage {
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
	public void TC070DBEC_01() {
		result.addLog("ID TC070DBEC_01 : Verify that the 'Edit Company Info' page displays company's info correctly");
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
		// 3.Click tab Companies
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
	 * Verify that the "New Contact Person" module displays correct information when selecting a contact from active users table
	 */
	@Test
	public void TC070DBEC_02() {
		result.addLog("ID TC070DBEC_02 : Verify that the 'Edit Company Info' page displays company's info correctly");
		/*
	  		Pre-condition: User has "Edit Company Info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
			5. Click "Change" link
			VP: Verify that the "Change Primary Partner Contact" page is displayed
			6. Select a different user from active users table
		*/
		// 3.Click tab Companies
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		/*
		 * VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.CHANGE_PARTNER_CONTACT));
		// 5. Click "Change" link
		home.click(CompanyInfo.CHANGE_PARTNER_CONTACT);
		/*
		 * VP: Verify that the "Change Primary Partner Contact" page is displayed
		 */
		Assert.assertEquals(home.existsElement(CompanyContact.getElementInfo()).getResult(), "Pass");
		// 6. Select a different user from active users table
		home.selectUserByEmail(superpartneruser);
		// Get user info
		int row_index = home.getRowIndexByEmail(CompanyContact.CONTACT_TABLE, superpartneruser);
		ArrayList<String> user_info = home.getUserInfoByIndex(CompanyContact.CONTACT_TABLE, row_index);
		// Get info in "New Contact Person" module
		String new_contact_info = home.getTextByXpath(CompanyContact.NEW_CONTACT);
		/*
		 * Verify that The contact name, email and phone number of selected user is displayed correctly in "New Contact Person: module
		 */
		Assert.assertTrue(DTSUtil.containsListText(new_contact_info, user_info));
	}
	
	/*
	 * Verify that the new Primary Partner Contact could be saved successfully
	 */
	@Test
	public void TC070DBEC_03() {
		result.addLog("ID TC070DBEC_03 : Verify that the new Primary Partner Contact could be saved successfully");
		/*
	  		Pre-condition: User has "Edit Company Info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
			5. Click "Change" link
			VP: Verify that the "Change Primary Partner Contact" page is displayed
			6. Select a different user from active users table
			7. Click "Save" link
		*/
		// 3.Click tab Companies
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		/*
		 * VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.CHANGE_PARTNER_CONTACT));
		// 5. Click "Change" link
		home.click(CompanyInfo.CHANGE_PARTNER_CONTACT);
		/*
		 * VP: Verify that the "Change Primary Partner Contact" page is displayed
		 */
		Assert.assertEquals(home.existsElement(CompanyContact.getElementInfo()).getResult(), "Pass");
		// 6. Select a different user from active users table
		home.selectUserByEmail(superpartneruser);
		// Get info in "New Contact Person" module
		String new_contact_info = home.getTextByXpath(CompanyContact.NEW_CONTACT);
		// 7. Click "Save" link
		home.click(CompanyContact.SAVE);
		/*
		 * Verify that The 061P Company Page is displayed with new Primary Partner Contact info
		 */
		ArrayList<String> list = new ArrayList<String>();
		list.add(home.getTextByXpath(PartnerCompanyInfo.CONTACT_NAME));
		list.add(home.getTextByXpath(PartnerCompanyInfo.PHONE).replaceAll("\\+\\s", "\\+"));
		list.add(home.getTextByXpath(PartnerCompanyInfo.EMAIL));
		Assert.assertTrue(DTSUtil.containsListText(new_contact_info, list));
	}
	
	/*
	 * Verify that the partner's Primary Partner Contact info is not changed when user cancels saving change after selecting new contact in "Change Primary Partner Contact" page
	 */
	@Test
	public void TC070DBEC_04() {
		result.addLog("ID TC070DBEC_04 : Verify that the partner's Primary Partner Contact info is not changed when user cancels saving change after selecting new contact in 'Change Primary Partner Contact' page");
		/*
			Pre-condition: User has "Edit Company Info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
			5. Click "Change" link
			VP: Verify that the "Change Primary Partner Contact" page is displayed
			6. Select new contact from active users table
			7. Click "Cancel" link
		*/
		// 3.Click tab Companies
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		/*
		 * VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.CHANGE_PARTNER_CONTACT));
		// Get primary contact info
		String contact_name = home.getTextByXpath(PartnerCompanyInfo.CONTACT_NAME);
		String phone_number = home.getTextByXpath(PartnerCompanyInfo.PHONE);
		String email = home.getTextByXpath(PartnerCompanyInfo.EMAIL);
		// 5. Click "Change" link
		home.click(CompanyInfo.CHANGE_PARTNER_CONTACT);
		/*
		 * VP: Verify that the "Change Primary Partner Contact" page is displayed
		 */
		Assert.assertEquals(home.existsElement(CompanyContact.getElementInfo()).getResult(), "Pass");
		// 6. Select new contact from active users table
		home.selectPartnerUserByEmail(superpartneruser);
		// 7. Click "Cancel" link
		home.click(CompanyContact.CANCEL);
		/*
		 * Verify that The 061D Company Page is displayed with previous primary partner contact info
		 */
		Assert.assertEquals(home.getTextByXpath(PartnerCompanyInfo.CONTACT_NAME), contact_name);
		Assert.assertEquals(home.getTextByXpath(PartnerCompanyInfo.PHONE), phone_number);
		Assert.assertEquals(home.getTextByXpath(PartnerCompanyInfo.EMAIL), email);
	}
	
	/*
	 * Verify that the Headphone:X Partnerships is required at least one partnership type when adding new companies
	 */
	@Test
	public void TC070DBEC_05() {
		result.addLog("ID TC070DBEC_05 : Verify that the Headphone:X Partnerships is required at least one partnership type when adding new companies");
		/*
	 		Pre-condition: User has "Edit Company Info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Click "Add Company" link
			5. Fill valid value into all required fields
			6. Do not add partnership type
			7. Click “Save” link.
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Click "Add Company" link
		home.click(Companies.ADD_COMPANY);
		// 5. Fill valid value into all required fields
		// 6. Do not add partnership type
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.validCompany();
		data.remove("partnerships");
		home.addCompany(AddCompany.getHash(), data);
		/*
		 * There is an error message which notifying that at least one partnership type is required when adding new company
		 */
		Assert.assertTrue(home.checkMessageDisplay("At least 1 partnership type is required for a company"));
	}
	
}
