package com.dts.adminportal.partner.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;

import dts.com.adminportal.model.CompanyContact;
import dts.com.adminportal.model.CompanyInfo;
import dts.com.adminportal.model.PartnerCompanyInfo;
import dts.com.adminportal.model.PartnerContact;
import dts.com.adminportal.model.PartnerContactinfo;
import dts.com.adminportal.model.PrimaryPartnerContact;
import dts.com.adminportal.model.Xpath;

public class PartnerUserCompanies071PaContactUnselected extends CreatePage{
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
	 * Verify that the "Change Primary Partner Contact" page displays "Current Contact Person", "New Contact Person", an active users table and "Actions" module.
	 */
	@Test
	public void TC071PaCU_01(){
		result.addLog("ID : TC071PaCU_01: Verify that the 'Change Primary Partner Contact' page displays 'Current Contact Person', "
				+ "'New Contact Person', an active users table and 'Actions' module.");
		/*
		 	
			Pre-condition: partner user has "Edit Company Info" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
			4. Click "Change" link
		 */
		
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		//VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		result = home.existsElement(PartnerCompanyInfo.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
		//4. Click "Change" link
		home.click(PartnerCompanyInfo.CHANGE_PARTNER_CONTACT);
		//VP:  Verify that the "Change Primary Partner Contact" page displays "Current Contact Person", 
		//+ "New Contact Person", an active users table and "Actions" module.
		result = home.existsElement(PartnerContactinfo.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
				
	}
	
	/*
	 * Verify that the "Current Contact Person" module in "Change Primary Partner Contact" page displays contact name, email and phone number correctly.
	 */
	@Test
	public void TC071PaCU_02(){
		result.addLog("ID : TC071PaCU_02: Verify that the 'Current Contact Person' module in 'Change Primary Partner Contact' page displays contact name, email and phone number correctly.");
		/*
		 	
			Pre-condition: partner user has "Edit Company Info" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
			4. Click "Change" link
		 */
		
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		//VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		result = home.existsElement(PartnerCompanyInfo.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
		String name_primary=home.getTextByXpath(PartnerCompanyInfo.CONTACT_NAME);
		String email_primary=home.getTextByXpath(PartnerCompanyInfo.EMAIL);
		String phone_primary=home.getTextByXpath(PartnerCompanyInfo.PHONE);
		//4. Click "Change" link
		home.click(PartnerCompanyInfo.CHANGE_PARTNER_CONTACT);
		//VP:  Verify that the "Current Contact Person" module in "Change Primary Partner Contact" page displays contact name, email and phone number correctly.
		Assert.assertEquals(home.existsElement(CompanyContact.getElementInfo()).getResult(), "Pass");
		String current_contact = home.getTextByXpath(CompanyContact.CURRENT_CONTACT);
		Assert.assertTrue(current_contact.contains(name_primary)
				&& current_contact.contains(email_primary)
				&& current_contact.contains(phone_primary));
	}

	/*
	 * Verify that the "New Contact Person" displays a message which notify user to select a new person from active users table
	 */
	@Test
	public void TC071PaCU_03(){
		result.addLog("ID : TC071PaCU_03: Verify that the 'New Contact Person' displays a message which notify user to select a new person from active users table");
		/*
		Pre-condition: partner user has "Edit Company Info" rights.
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click "Companies" tab
		VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		4. Click "Change" link
		*/
		
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		/*
		 * VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		 */
		Assert.assertTrue(home.isElementPresent(PartnerCompanyInfo.PRIMARY_PARTNER_CONTACT));
		Assert.assertTrue(home.isElementPresent(PartnerCompanyInfo.CHANGE_PARTNER_CONTACT));
		// 4. Click "Change" link
		home.click(PartnerCompanyInfo.CHANGE_PARTNER_CONTACT);
		/*
		 * The "New Contact Person" module displays a notification message which mention user should select a new person from active users table such as "Select a person from the list below"
		 */
		String message = "Select a different user from the list of active user accounts";
		Assert.assertTrue(home.checkMessageDisplay(message));
		
		
		
		
	}
	
	/*
	 * Verify that the active users table in "Change Primary Partner Contact " page displays "First Name", "Last Name", "Title", "Phone" and "Email" columns.
	 */
	@Test
	public void TC071PaCU_04(){
		result.addLog("ID : TC071PaCU_04: Verify that the active users table in 'Change Primary Partner Contact ' page displays 'First Name', 'Last Name', 'Title', 'Phone' and 'Email' columns..");
		/*
			Pre-condition: partner user has "Edit Company Info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
			4. Click "Change" link
		 */
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		//VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		result = home.existsElement(PartnerCompanyInfo.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
		//4. Click "Change" link
		home.click(PartnerCompanyInfo.CHANGE_PARTNER_CONTACT);
		//VP:  Verify that the active users table in "Change Primary Partner Contact " page displays "First Name", "Last Name", "Title", "Phone" and "Email" columns.
		result = home.existsElement(PartnerContactinfo.gettableactive());
		Assert.assertEquals("Pass", result.getResult());
	}
	
	/*
	 * Verify that the active users table shows up to 50 users
	 */
	@Test
	public void TC071PaCU_05(){
		result.addLog("ID : TC071PaCU_05: Verify that the active users table shows up to 50 users");
		/*
		 	Pre-condition: partner user has "Edit Company Info" rights and there are at least 50 users activated.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
			4. Click "Change" link
		 */
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		/*
		 * VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.CHANGE_PARTNER_CONTACT));
		// 4. Click "Change" link
		home.click(CompanyInfo.CHANGE_PARTNER_CONTACT);
		//result.addErrorLog("PDPP-568: 071 Change Partner Contact: The contact person table does not show up to 50 users per page");
		Assert.assertTrue(home.checkItemAmountDisplayOnTable(PrimaryPartnerContact.TOTAL_ITEM,50));
	}
	
	/*
	 * Verify that the partner's Primary Partner Contact info is not changed when clicking on "Cancel" link in "Change Primary Partner Contact" page.
	 */
	@Test
	public void TC071PaCU_06(){
		result.addLog("ID : TC071PaCU_06: Verify that the partner's Primary Partner Contact info is not changed when clicking on 'Cancel' link in 'Change Primary Partner Contact' page.");
		/*
		 	
		Pre-condition: partner user has "Edit Company Info" rights.

		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click "Companies" tab
		VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		4. Click "Change" link
		VP: Verify that the "Change Primary Partner Contact" page is displayed
		5. Click "Cancel" link
		 */
			
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		String Phone_primary=home.getTextByXpath(PartnerCompanyInfo.PHONE);
		//VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		result = home.existsElement(PartnerCompanyInfo.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
		//4. Click "Change" link
		home.click(PartnerCompanyInfo.CHANGE_PARTNER_CONTACT);
		//VP: Verify that the "Change Primary Partner Contact" page is displayed
		result = home.existsElement(PartnerContactinfo.getListXpath());
		//5. Click "Cancel" link
		PartnerContact contact = home.getPartnerContact(PartnerContactinfo.Current_Contact_body);
		
		home.click(PartnerContactinfo.CANCEL);
		//VP:  Verify that the partner's Primary Partner Contact info is not changed when clicking on "Cancel" link in "Change Primary Partner Contact" page.
		Assert.assertEquals(Phone_primary, contact.phone);		
	}
	
	/*
	 * Verify that the warning message displays correctly when clicking "Save "link without selecting another contact from active users table
	 */
	@Test
	public void TC071PaCU_07(){
		result.addLog("ID : TC071PaCU_07: Verify that the warning message displays correctly when clicking 'Save' link without selecting another contact from active users table");
		/*
		Pre-condition: partner user has "Edit Company Info" rights.
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click "Companies" tab
		VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		4. Click "Change" link
		VP: Verify that the "Change Primary Partner Contact" page is displayed
		5. Click "Save" link
		*/
		
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		/*
		 * VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		 */
		Assert.assertTrue(home.isElementPresent(PartnerCompanyInfo.PRIMARY_PARTNER_CONTACT));
		Assert.assertTrue(home.isElementPresent(PartnerCompanyInfo.CHANGE_PARTNER_CONTACT));
		// 4. Click "Change" link
		home.click(PartnerCompanyInfo.CHANGE_PARTNER_CONTACT);
		/*
		 * VP: Verify that the "Change Primary Partner Contact" page is displayed
		 */
		Assert.assertEquals(home.existsElement(CompanyContact.getElementInfo()).getResult(), "Pass");
		// 5. Click "Save" link
		home.click(CompanyContact.SAVE);
		/*
		 * Verify that The warning message displays to inform user select a new contact person
		 */
		String message = "Please select user";
		Assert.assertTrue(home.checkMessageDisplay(message));
	}
	
}
