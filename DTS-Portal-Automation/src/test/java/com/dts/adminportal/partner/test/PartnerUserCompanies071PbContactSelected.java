package com.dts.adminportal.partner.test;

import java.util.ArrayList;

import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.DTSUtil;

import dts.com.adminportal.model.CompanyContact;
import dts.com.adminportal.model.PartnerCompanyInfo;
import dts.com.adminportal.model.Xpath;

public class PartnerUserCompanies071PbContactSelected extends CreatePage{
	private HomeController home;
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}
	@BeforeMethod
	public void loginBeforeTest() {
		home.login(partneruser, password);
	}
	
	/*
	 * Verify that the "New Contact Person" module displays correct information when selecting a contact from active users table
	 */
	@Test
	public void TC071PbCS_01(){
		result.addLog("ID : TC071PbCS_01: Verify that the 'New Contact Person' module displays correct information when selecting a contact from active users table");
		/*
		Pre-condition: partner user has "Edit Company Info" rights.
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click "Companies" tab
		VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		4. Click "Change" link
		VP: Verify that the "Change Primary Partner Contact" page is displayed
		5. Select a different user from active users table
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
		// 5. Select a different user from active users table
		home.selectUserByEmail(partneruser);
		/*
		 * Verify that The contact name, email and phone number of selected user is displayed correctly in "New Contact Person" module
		 */
		// Get user info
		int row_index = home.getRowIndexByEmail(CompanyContact.CONTACT_TABLE, partneruser);
		ArrayList<String> user_info = home.getUserInfoByIndex(CompanyContact.CONTACT_TABLE, row_index);
		// Get info in "New Contact Person" module
		String new_contact_info = home.getTextByXpath(CompanyContact.NEW_CONTACT);
		// Verify
		Assert.assertTrue(DTSUtil.containsListText(new_contact_info, user_info));
	}
	
	/*
	 * Verify that the new Primary Partner Contact could be saved successfully
	 */
	@Test
	public void TC071PbCS_02(){
		result.addLog("ID : TC071PbCS_02: Verify that the new Primary Partner Contact could be saved successfully");
		/*
		Pre-condition: partner user has "Edit Company Info" rights.
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click "Companies" tab
		VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		4. Click "Change" link
		VP: Verify that the "Change Primary Partner Contact" page is displayed
		5. Select a different user from active users table
		6. Click "Save" link
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
		// 5. Select a different user from active users table
		home.selectPartnerUserByEmail(partneruser);
		// Get info in "New Contact Person" module
		String new_contact_info = home.getTextByXpath(CompanyContact.NEW_CONTACT);
		// 6. Click "Save" link
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
	public void TC071PbCS_03(){
		result.addLog("ID : TC071PbCS_03: Verify that the partner's Primary Partner Contact info is not changed when user cancels saving change after selecting new contact in 'Change Primary Partner Contact' page");
		/*
		Pre-condition: partner user has "Edit Company Info" rights.
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click "Companies" tab
		VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		4. Click "Change" link
		VP: Verify that the "Change Primary Partner Contact" page is displayed
		5. Select new contact from active users table
		6. Click "Cancel" link
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		/*
		 * VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		 */
		Assert.assertTrue(home.isElementPresent(PartnerCompanyInfo.PRIMARY_PARTNER_CONTACT));
		Assert.assertTrue(home.isElementPresent(PartnerCompanyInfo.CHANGE_PARTNER_CONTACT));
		// 4. Click "Change" link
		// Get primary contact info
		String contact_name = home.getTextByXpath(PartnerCompanyInfo.CONTACT_NAME);
		String phone_number = home.getTextByXpath(PartnerCompanyInfo.PHONE);
		String email = home.getTextByXpath(PartnerCompanyInfo.EMAIL);
		// Click "Change" link
		home.click(PartnerCompanyInfo.CHANGE_PARTNER_CONTACT);
		/*
		 * VP: Verify that the "Change Primary Partner Contact" page is displayed
		 */
		Assert.assertEquals(home.existsElement(CompanyContact.getElementInfo()).getResult(), "Pass");
		// 5. Select a different user from active users table
		home.selectPartnerUserByEmail(partneruser);
		// 6. Click "Cancel" link
		home.click(CompanyContact.CANCEL);
		/*
		 * Verify that The 061P Company Page is displayed with previous primary partner contact info
		 */
		Assert.assertEquals(home.getTextByXpath(PartnerCompanyInfo.CONTACT_NAME), contact_name);
		Assert.assertEquals(home.getTextByXpath(PartnerCompanyInfo.PHONE), phone_number);
		Assert.assertEquals(home.getTextByXpath(PartnerCompanyInfo.EMAIL), email);
	}
}
