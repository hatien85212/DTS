package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddCompany;
import com.dts.adminportal.model.Companies;
import com.dts.adminportal.model.CompanyContact;
import com.dts.adminportal.model.CompanyInfo;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PageLogin;
import com.dts.adminportal.model.PartnerCompanyInfo;
import com.dts.adminportal.model.XPartnerShipPackage;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.TestData;

public class DTSCompanies070DEditCompany extends BasePage {
	
	@Override
	protected void initData() {
	}	

	/*
	 * Verify that the "Edit Company Info" page displays company's info correctly
	 */
	@Test
	public void TC070DAEC_01() {
		companyControl.addLog("ID TC070DAEC_01 : Verify that the 'Edit Company Info' page displays company's info correctly");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Select a company on table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		/*
		 * VP: Verify that the "Edit" link is displayed next to "Company Info" label
		 */
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.EDIT));
		// 5. Click "Edit" link
		companyControl.click(CompanyInfo.EDIT);
		/*
		 * VP: Verify that the "Edit Company Info" page is displayed
		 */
		Assert.assertEquals(true, companyControl.existsElement(AddCompany.getHash()));
	}
	
	/*
	 * Verify that the company's info could be changed successfully when clicking "Save" link in "Edit Company Info" page.
	 */
	@Test
	public void TC070DAEC_02() {
		companyControl.addLog("ID TC070DAEC_02 : Verify that the company's info could be"
				+ " changed successfully when clicking 'Save' link in 'Edit Company Info' page.");
		/*
	  		Pre-condition: User has "Edit Company Info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			VP: Verify that the "Edit" link is displayed next to "Company Info" label
			4. Click "Edit" link
			VP: Verify that the "Edit Company Info" page is displayed
			6. Leave the "Official Corporate Name" field blank
			7. Click "Save" link
			VP: User is unable to save due to an error message which mentioning that Official Corporate Name is required.
			8. Change any value of company's info
			9. Click "Cancel" link
			VP: The 061P Company Page is displayed with previous company's information.
			10. Click "Edit" link again
			11. Change any value of company's info
			12. Click "Save" link
			The 061P Company Page is displayed with correct info changed.
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Select a company on table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		/*
		 * VP: Verify that the "Edit" link is displayed next to "Company Info" label
		 */
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.EDIT));
		// 5. Click "Edit" link
		companyControl.click(CompanyInfo.EDIT);
		/*
		 * VP: Verify that the "Edit Company Info" page is displayed
		 */
		Assert.assertEquals(companyControl.existsElement(AddCompany.getHash()), true);
		// 6. Leave the "Official Corporate Name" field blank
		companyControl.editData(AddCompany.OFFICIAL_CORP_NAME, "");
		// 7. Click "Save" link
		companyControl.click(AddCompany.SAVE);
		/*
		 * Verify that User is unable to save due to an error message which mentioning that Official Corporate Name is required
		 */
		Assert.assertTrue(companyControl.checkMessageDisplay("Corporate Name is required"));
		
		String oldAddress = companyControl.getAtributeValue(AddCompany.ADDRESS1, "value");
		String newAddress = "New Address" + RandomStringUtils.randomNumeric(10);
		companyControl.editData(AddCompany.ADDRESS1, newAddress);
		// 7. Click "Cancel" link
		companyControl.click(AddCompany.CANCEL);
		/*
		 * Verify that The 061P Company Page is displayed with previous company's information
		 */
		Assert.assertEquals(companyControl.getTextByXpath(CompanyInfo.ADDRESS1), oldAddress);
		
		companyControl.click(CompanyInfo.EDIT);
		companyControl.editData(AddCompany.ADDRESS1, newAddress);
		companyControl.click(AddCompany.SAVE);
		Assert.assertEquals(companyControl.getTextByXpath(CompanyInfo.ADDRESS1), newAddress);
	}
	
	/*
	 * Verify that the generic international friendly address form based on Amazon.com's form design is used for company's Corporate Address
	 */
	@Test
	public void TC070DAEC_05() {
		companyControl.addLog("ID TC070DAEC_05 : Verify that the generic international friendly address form based on Amazon.com's form design is used for company's Corporate Address");
		/*
	  		Pre-condition: User has "Edit Company Info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			VP: Verify that the "Edit" link is displayed next to "Company Info" label
			4. Click "Edit" link
			VP: Verify that the "Edit Company Info" page is displayed
			The company's Corporate Address includes:
			Address Line 1 
			Address Line 2
			Address Line 3
			City/Town
			State/Province/Region
			ZIP/Postal Code text fields 
			and Country dropdown list.
			Below each address line has an instruction:
			Address Line 1: Street address, P.O Box, c/o
			Address Line 2: Suite, unit, building, floor, etc.
			Address Line 3: Attn, other info.
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// Selete a company on table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		/*
		 * VP: Verify that the "Edit" link is displayed next to "Company Info" label
		 */
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.EDIT));
		// 4. Click "Edit" link
		companyControl.click(CompanyInfo.EDIT);
		/*
		 * VP: Verify that the "Edit Company Info" page is displayed
		 */
		Assert.assertEquals(companyControl.existsElement(AddCompany.getHash()), true);
		companyControl.addErrorLog("PDPP-726: 070 Edit Company: The company's corporate address is not implemented as amazon's friendly address form");
		Assert.assertTrue(false);
		
	}
	
	/*
	 * Verify that the user can edit Headphone:X Partnerships type
	 */
	@Test
	public void TC070DAEC_07() {
		companyControl.addLog("ID TC070DAEC_07 : Verify that the user can not edit Headphone:X Partnerships type");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// Click Add Company link
		companyControl.click(Companies.ADD_COMPANY);
		// Create company
		Hashtable<String,String> data = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), data);
		/*
		 * *************************************************************
		 */
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Select a company on table
		companyControl.selectACompanyByName(data.get("name"));
		/*
		 * VP: Verify that the "Edit" link is displayed next to "Company Info" label
		 */
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.EDIT));
		// 5. Click "Edit" link
		companyControl.click(CompanyInfo.EDIT);
		// 6. Add more Headphone:X Partnerships
		companyControl.selectOptionByName(AddCompany.PARTNERSHIPS, AddCompany.PARTNERSHIP_LIST[1]);
		companyControl.click(AddCompany.ADD);
		// Get all Partnerships
		ArrayList<XPartnerShipPackage> partnershipPackages = companyControl.getPartnershipPackage(AddCompany.PARTNERSHIPS_TABLE);
		/*
		 * VP: The Headphone:X Partnerships is able to add more
		 */
		Assert.assertEquals(partnershipPackages.get(partnershipPackages.size() - 1).type, AddCompany.PARTNERSHIP_LIST[1]);
		// 7. Click “Delete” link
		companyControl.click(partnershipPackages.get(partnershipPackages.size() - 1).deletelink);
		/*
		 * VP: The Headphone:X Partnerships is able to delete
		 */
		Assert.assertTrue(!companyControl.getTextByXpath(AddCompany.PARTNERSHIPS_TABLE).contains(AddCompany.PARTNERSHIP_LIST[1]));
		// Delete company
		companyControl.click(AddCompany.CANCEL);
		companyControl.doDelete(CompanyInfo.DELETE);
	}
	
	/*
	 * Verify that the "Edit Company Info" page displays company's info correctly
	 */
	@Test
	public void TC070DBEC_01() {
		companyControl.addLog("ID TC070DBEC_01 : Verify that the 'Edit Company Info' page displays company's info correctly");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3.Click tab Companies
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Select a company on table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		/*
		 * VP: Verify that the "Edit" link is displayed next to "Company Info" label
		 */
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.EDIT));
		// 5. Click "Edit" link
		companyControl.click(CompanyInfo.EDIT);
		/*
		 * VP: Verify that the "Edit Company Info" page is displayed
		 */
		Assert.assertEquals(true, companyControl.existsElement(AddCompany.getHash()));
	}
	
	/*
	 * Verify that the "New Contact Person" module displays correct information when selecting a contact from active users table
	 */
	@Test
	public void TC070DBEC_02() {
		companyControl.addLog("ID TC070DBEC_02 : Verify that the 'Edit Company Info' page displays company's info correctly");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3.Click tab Companies
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Select a company on table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		/*
		 * VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		 */
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.CHANGE_PARTNER_CONTACT));
		// 5. Click "Change" link
		companyControl.click(CompanyInfo.CHANGE_PARTNER_CONTACT);
		/*
		 * VP: Verify that the "Change Primary Partner Contact" page is displayed
		 */
		Assert.assertEquals(companyControl.existsElement(CompanyContact.getElementInfo()), true);
		// 6. Select a different user from active users table
		userControl.selectUserInfoByEmail(SUPER_PARTNER_USER);
		// Get user info
		int row_index = companyControl.getRowIndexByEmail(CompanyContact.CONTACT_TABLE, SUPER_PARTNER_USER);
		ArrayList<String> user_info = companyControl.getUserInfoByIndex(CompanyContact.CONTACT_TABLE, row_index);
		// Get info in "New Contact Person" module
		String new_contact_info = companyControl.getTextByXpath(CompanyContact.NEW_CONTACT);
		/*
		 * Verify that The contact name, email and phone number of selected user is displayed correctly in "New Contact Person: module
		 */
		Assert.assertTrue(ListUtil.containsListText(new_contact_info, user_info));
	}
	
	/*
	 * Verify that the partner's Primary Partner Contact info is not changed when user cancels saving change after selecting new contact in "Change Primary Partner Contact" page
	 */
	@Test
	public void TC070DBEC_04() {
		companyControl.addLog("ID TC070DBEC_04 : Verify that the partner's Primary Partner Contact info is not changed when user cancels saving change after selecting new contact in 'Change Primary Partner Contact' page");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3.Click tab Companies
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Select a company on table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		/*
		 * VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		 */
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.CHANGE_PARTNER_CONTACT));
		// Get primary contact info
		String contact_name = companyControl.getTextByXpath(PartnerCompanyInfo.CONTACT_NAME);
		String phone_number = companyControl.getTextByXpath(PartnerCompanyInfo.PHONE);
		String email = companyControl.getTextByXpath(PartnerCompanyInfo.EMAIL);
		// 5. Click "Change" link
		companyControl.click(CompanyInfo.CHANGE_PARTNER_CONTACT);
		/*
		 * VP: Verify that the "Change Primary Partner Contact" page is displayed
		 */
		Assert.assertEquals(companyControl.existsElement(CompanyContact.getElementInfo()), true);
		// 6. Select new contact from active users table
		userControl.selectPartnerUserByEmail(SUPER_PARTNER_USER);
		// 7. Click "Cancel" link
		companyControl.click(CompanyContact.CANCEL);
		/*
		 * Verify that The 061D Company Page is displayed with previous primary partner contact info
		 */
		Assert.assertEquals(companyControl.getTextByXpath(PartnerCompanyInfo.CONTACT_NAME), contact_name);
		Assert.assertEquals(companyControl.getTextByXpath(PartnerCompanyInfo.PHONE), phone_number);
		Assert.assertEquals(companyControl.getTextByXpath(PartnerCompanyInfo.EMAIL), email);
	}
	
	/*
	 * Verify that the Headphone:X Partnerships is required at least one partnership type when adding new companies
	 */
	@Test
	public void TC070DBEC_05() {
		companyControl.addLog("ID TC070DBEC_05 : Verify that the Headphone:X Partnerships is required at least one partnership type when adding new companies");
		/*
	 		Pre-condition: User has "Edit Company Info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Click "Add Company" link
			5. Fill valid value into all required fields
			6. Do not add partnership type
			7. Click ï¿½Saveï¿½ link.
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Click "Add Company" link
		companyControl.click(Companies.ADD_COMPANY);
		// 5. Fill valid value into all required fields
		// 6. Do not add partnership type
		// 7. Click ï¿½Saveï¿½ link
		Hashtable<String,String> data = TestData.validCompany();
		data.remove("partnerships");
		companyControl.addCompany(AddCompany.getHash(), data);
		/*
		 * There is an error message which notifying that at least one partnership type is required when adding new company
		 */
		Assert.assertTrue(companyControl.checkMessageDisplay(PageLogin.errMessage[4]));
	}
}
