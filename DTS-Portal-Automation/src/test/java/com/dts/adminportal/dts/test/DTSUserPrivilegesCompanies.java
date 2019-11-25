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
import dts.com.adminportal.model.AddCompany;
import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.BrandEdit;
import dts.com.adminportal.model.BrandInfo;
import dts.com.adminportal.model.Companies;
import dts.com.adminportal.model.CompanyContact;
import dts.com.adminportal.model.CompanyInfo;
import dts.com.adminportal.model.DTSHome;
import dts.com.adminportal.model.PartnerCompanyInfo;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UserEdit;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.Xpath;

public class DTSUserPrivilegesCompanies extends CreatePage {
	private HomeController home;
	
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
		
	}
	
	@BeforeMethod
	public void loginBeforeTest() {
		result = home.login(superUsername, superUserpassword);
	}
	/*
	 *Verify that the DTS user can view the company and brand info when the “Edit Company Info”, “Edit brand info” and “Add and manage company accounts” are disabled.
	 */
	@Test
	public void PDC_01(){
		result.addLog("ID : PDC_01 : Verify that the DTS user can view the company and brand info when the “Edit Company Info”, “Edit brand info” and “Add and manage company accounts” are disabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the  “Edit Company Info”, “Edit brand info” and “Add and manage company accounts” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Companies” page is displayed
			9. Navigate to “Companies” page
			VP: Verify that the companies list page is displayed
			10. Select a company from companies table
			VP: Verify that the company page display the DTS's company info correctly
			11. Click on a brand name
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);	
		// 6.Disable the  “Edit Company Info”, “Edit brand info” and “Add and manage company accounts” privileges
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_brand_info);
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_company_account);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 9. Navigate to “Companies” page
		home.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		result = home.existsElement(Companies.getListElements());
		Assert.assertEquals("Pass", result.getResult());
		// 10. Select a company from companies table
		home.selectACompanyByName(partner_company_name);
		// VP: Verify that the company page display the DTS's company info correctly
		result = home.existsElement(CompanyInfo.getListElement());
		Assert.assertEquals("Pass", result.getResult());
		// 11. Click on a brand name
		home.selectABrand(CompanyInfo.BRAND_LIST);
		// Verify that the DTS's company brand info display correctly.
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(), "Pass");
	}
	
	/*
	 * Verify that DTS user can add new brand when the “Edit Company Info” privilege is enabled
	 */
	@Test
	public void PDC_02(){
		result.addLog("ID : PDC_02 : Verify that DTS user can add new brand when the “Edit Company Info” privilege is enabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Edit Company Info” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Companies” tab is displayed
			9. Navigate to “Companies” page
			VP: Verify that the companies list page is displayed
			10. Select a company from companies table
			VP: Verify that the company info page displays  and the “Edit”, “Change” and “Add New Brand” link are also displayed.
			11. Click “Add” link
			VP: Verify that the “Edit Brand Info” page is displayed.
			12. Fill valid value into all required fields
			13. Click “Save” link
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.selectUserByEmail(dtsUser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);	
		// 6. Enable the  “Edit Company Info” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// VP. Verify that the “Companies” tab is displayed
		Assert.assertTrue(home.isElementPresent(DTSHome.LINK_COMPANY));
		// 9. Navigate to “Companies” page
		home.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals("Pass", home.existsElement(Companies.getListElements()).getResult());
		// 10. Select a company from companies table
		home.selectACompanyByName(partner_company_name);
		// VP: Verify that the company info page displays  and the “Edit”, “Change” and “Add New Brand” link are also displayed.
		Assert.assertEquals("Edit", home.getTextByXpath(CompanyInfo.EDIT));
		Assert.assertEquals("Change", home.getTextByXpath(CompanyInfo.CHANGE_PARTNER_CONTACT));
		Assert.assertEquals("Add New Brand", home.getTextByXpath(CompanyInfo.ADD_BRAND));
		// 11. Click “Add” link
		home.click(CompanyInfo.ADD_BRAND);
		// VP: Verify that the “Edit Brand Info” page is displayed.
		Assert.assertEquals("Pass", home.existsElement(BrandEdit.getListAddBrand()).getResult());
		// 12. Fill valid value into all required fields
		// 13. Click “Save” link
		Hashtable<String,String> data = TestData.brandDraft();
		home.addBrand(AddBrand.getHash(), data);
		// Verify that new brand is added successfully.
		Assert.assertTrue(home.getTextByXpath(BrandInfo.NAME).contains(data.get("name")));
	}
	
	/*
	 * Verify that DTS user cannot edit company, add new brand and change primary contact when the “Edit Company Info” privilege is disabled
	 */
	@Test
	public void PDC_03(){
		result.addLog("ID : PDC_03 : Verify that DTS user cannot edit company, add new brand and change primary contact when the “Edit Company Info” privilege is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the  “Edit Company Info” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Companies” tab is displayed
			9. Navigate to “Companies” page
			VP: Verify that the companies list page is displayed
			10. Select a company from companies table
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);	
		// 6. Enable the “Edit Company Info” privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		//VP. Verify that the “Companies” tab is displayed
		Assert.assertTrue(home.isElementPresent(DTSHome.LINK_COMPANY));
		// 9. Navigate to “Companies” page
		home.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals("Pass", home.existsElement(Companies.getListElements()).getResult());
		// 10. Select a company from companies table
		home.selectACompanyByName(partner_company_name);
		// Verify that the company info page is displayed, but the “Edit”, “Change” and “Add New Brand” link are not displayed.
		Assert.assertEquals("Pass", home.existsElement(CompanyInfo.getListElement()).getResult());
		Assert.assertFalse(home.isElementPresent(CompanyInfo.EDIT));
		Assert.assertFalse(home.isElementPresent(CompanyInfo.CHANGE_PARTNER_CONTACT));
		Assert.assertFalse(home.isElementPresent(CompanyInfo.ADD_BRAND));
	}
	/*
	 *Verify that DTS user can edit company info when the “Edit Company Info” privilege is enabled.
	 */
	@Test
	public void PDC_04(){
		result.addLog("ID : PDC_04 : Verify that DTS user can edit company info when the “Edit Company Info” privilege is enabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Edit Company Info” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Companies” page is displayed
			9. Navigate to “Companies” page
			VP: Verify that the companies list page is displayed
			10. Select a company from companies table
			VP: Verify that the company info page displays  and the “Edit”, “Change” and “Add” link are also displayed.
			11. Click “Edit” link
			VP: Verify that the Company edit page is displayed.
			12. Change some company info
			13. Click “Save” link
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.selectUserByEmail(dtsUser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);	
		// 6. Enable the  “Edit Company Info” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		//VP. Verify that the “Companies” tab is displayed
		Assert.assertTrue(home.isElementPresent(DTSHome.LINK_COMPANY));
		// 9. Navigate to “Companies” page
		home.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals("Pass", home.existsElement(Companies.getListElements()).getResult());
		// 10. Select a company from companies table
		home.selectACompanyByName(partner_company_name);
		// Verify that the company info page is displayed, but the “Edit”, “Change” and “Add New Brand” link are not displayed.
		Assert.assertEquals("Edit", home.getTextByXpath(CompanyInfo.EDIT));
		Assert.assertEquals("Change", home.getTextByXpath(CompanyInfo.CHANGE_PARTNER_CONTACT));
		Assert.assertEquals("Add New Brand", home.getTextByXpath(CompanyInfo.ADD_BRAND));
		//11. Click “Edit” link
		home.click(CompanyInfo.EDIT);
		//VP: Verify that the Company edit page is displayed.
		Assert.assertEquals("Pass", home.existsElement(AddCompany.getHash()).getResult());
		//12. Change some company info
		String address1 = RandomStringUtils.randomAlphabetic(10);
		home.editData(AddCompany.ADDRESS1, address1);
		//13. Click “Save” link
		home.click(AddCompany.SAVE);
		//Verify that company info page is displayed with new info
		Assert.assertEquals(home.getTextByXpath(CompanyInfo.ADDRESS1),address1);
	}
	
	/*
	 *Verify that DTS user can change the primary contact info when the “Edit Company Info” privilege is enabled.
	 */
	@Test
	public void PDC_05(){
		result.addLog("ID : PDC_05 : Verify that DTS user can change the primary contact info when the “Edit Company Info” privilege is enabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Edit Company Info” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Companies” page is displayed
			9. Navigate to “Companies” page
			VP: Verify that the companies list page is displayed
			10. Select a company from companies table
			VP: Verify that the company info page displays  and the “Edit”, “Change” and “Add” link are also displayed.
			11. Click “Change” link
			VP: Verify that the Change Primary Partner Contact page is displayed.
			12. Change the primary contact info to another one
			13. Click “Save” link
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.selectUserByEmail(dtsUser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);	
		// 6. Enable the  “Edit Company Info” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		//VP. Verify that the “Companies” tab is displayed
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_COMPANY));
		// 9. Navigate to “Companies” page
		home.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals("Pass", home.existsElement(Companies.getListElements()).getResult());
		// 10. Select a company from companies table
		home.selectACompanyByName(partner_company_name);
		// VP: Verify that the company info page displays  and the “Edit”, “Change” and “Add” link are also displayed.
		Assert.assertEquals(home.getTextByXpath(CompanyInfo.EDIT), "Edit");
		Assert.assertEquals(home.getTextByXpath(CompanyInfo.CHANGE_PARTNER_CONTACT), "Change");
		Assert.assertEquals(home.getTextByXpath(CompanyInfo.ADD_BRAND), "Add New Brand");
		// 11. Click “Change” link
		home.click(CompanyInfo.CHANGE_PARTNER_CONTACT);
		// VP: Verify that the Change Primary Partner Contact page is displayed.
		Assert.assertEquals("Pass", home.existsElement(CompanyContact.getElementInfo()).getResult());
		// 12. Change the primary contact info to another one
		home.selectUserByEmail(partneruser);
		// 13. Click “Save” link
		String newContact = home.getTextByXpath(CompanyContact.NEW_CONTACT);
		home.click(CompanyContact.SAVE);
		// Verify that company info page is displayed with new primary contact info
		ArrayList<String> list = new ArrayList<String>();
		list.add(home.getTextByXpath(PartnerCompanyInfo.CONTACT_NAME));
		list.add(home.getTextByXpath(PartnerCompanyInfo.PHONE).replaceAll("\\+\\s", "\\+"));
		list.add(home.getTextByXpath(PartnerCompanyInfo.EMAIL));
		Assert.assertEquals("Pass", home.existsElement(CompanyInfo.getListElement()).getResult());
		Assert.assertTrue(DTSUtil.containsListText(newContact, list));
	}
	
	/*
	 * Verify that DTS user can edit the company brand info when the “Edit brand info” privilege is enabled.
	 */
	@Test
	public void PDC_06(){
		result.addLog("ID : PDC_06 : Verify that DTS user can edit the company brand info when the “Edit brand info” privilege is enabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Edit brand info” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Companies” tab is displayed
			9. Navigate to “Companies” page
			VP: Verify that the companies list page is displayed
			10. Select a company from companies table
			VP: Verify that the company info page is displayed
			11. Click on a brand name link
			VP: Verify that the “Edit” link is displayed in the Brand info page
			12. Click on “Edit” link
			13. Change some brand info in Brand edit page
			14. Click “Save” link
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.selectUserByEmail(dtsUser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);	
		// 6. Enable the  “Edit brand info” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_brand_info);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		//VP. Verify that the “Companies” tab is displayed
		Assert.assertTrue(home.isElementPresent(DTSHome.LINK_COMPANY));
		// 9. Navigate to “Companies” page
		home.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals("Pass", home.existsElement(Companies.getListElements()).getResult());
		// 10. Select a company from companies table
		home.selectACompanyByName(partner_company_name);
		// VP: Verify that the company info page displays
		Assert.assertEquals("Pass", home.existsElement(CompanyInfo.getListElement()).getResult());
		// 11. Click on a brand name link
		home.selectABrand(CompanyInfo.BRAND_LIST);
		// VP: Verify that the “Edit” link is displayed in the Brand info page
		Assert.assertTrue(home.isElementPresent(BrandInfo.EDIT_BRAND));
		// 12. Click on “Edit” link
		home.click(BrandInfo.EDIT_BRAND);
		// 13. Change some brand info in Brand edit page
		String brandOverview = RandomStringUtils.randomAlphabetic(10);
		home.type(BrandInfo.BRAND_OVERVIEW, brandOverview);
		// 14. Click “Save” link
		home.click(BrandInfo.SAVE);
		// Verify that the brand info page is displayed with new value.
		Assert.assertEquals(home.getTextByXpath(BrandInfo.BRAND_OVERVIEW), brandOverview);
	}
	
	/*
	 * Verify that DTS user can delete the company brand when the “Edit brand info” privilege is enabled.
	 */
	@Test
	public void PDC_07(){
		result.addLog("ID : PDC_07 : Verify that DTS user can delete the company brand when the “Edit brand info” privilege is enabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Edit brand info” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Companies” page is displayed
			9. Navigate to “Companies” page
			VP: Verify that the companies list page is displayed
			10. Select a company from companies table
			VP: Verify that the company info page is displayed
			11. Click on a brand name link
			VP: Verify that the “Delete” link is displayed in the Brand info page
			12. Click on “Delete” link
			13. Click “Delete” on the delete confirmation dialog
		 */
		/*
		 * Pre-condition: Create a new brand
		 */
		home.click(Xpath.LINK_COMPANY);
		// Select a company from table
		home.selectACompanyByName(partner_company_name);
		// Click Add brand link
		home.click(CompanyInfo.ADD_BRAND);
		// Create brand
		Hashtable<String,String> data = TestData.brandDraft();
		home.addBrand(AddBrand.getHash(), data);
		/*
		 * *****************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.selectUserByEmail(dtsUser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);	
		// 6. Enable the  “Edit brand info” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_brand_info);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		//VP. Verify that the “Companies” tab is displayed
		Assert.assertTrue(home.isElementPresent(DTSHome.LINK_COMPANY));
		// 9. Navigate to “Companies” page
		home.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals("Pass", home.existsElement(Companies.getListElements()).getResult());
		// 10. Select a company from companies table
		home.selectACompanyByName(partner_company_name);
		// VP: Verify that the company info page displays
		Assert.assertEquals("Pass", home.existsElement(CompanyInfo.getListElement()).getResult());
		// 11. Click on a brand name link
		home.selectABrandByName( data.get("name"));
		// VP: Verify that the “Delete” link is displayed in the Brand info page
		Assert.assertTrue(home.isElementPresent(BrandInfo.DELETE_LINK));
		// 12. Click on “Delete” link
		// 13. Click “Delete” on the delete confirmation dialog
		home.doDelete(BrandInfo.DELETE_LINK);
		// Verify that the company info page is displayed without the deleted brand.
		Assert.assertFalse(home.checkBrandExist( data.get("name")));
	}
	
	/*
	 * Verify that DTS user cannot edit or delete the company brand when the “Edit brand info” privilege is disabled.
	 */
	@Test
	public void PDC_08(){
		result.addLog("ID : PDC_08 : Verify that DTS user cannot edit or delete the company brand when the “Edit brand info” privilege is disabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the  “Edit brand info” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Companies” page is displayed
			9. Navigate to “Companies” page
			VP: Verify that the companies list page is displayed
			10. Select a company from companies table
			VP: Verify that the company info page is displayed
			11. Click on a brand name link
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.selectUserByEmail(dtsUser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);	
		// 6. Enable the  “Edit brand info” privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE,Privileges.Edit_brand_info);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		//VP. Verify that the “Companies” tab is displayed
		Assert.assertTrue(home.isElementPresent(DTSHome.LINK_COMPANY));
		// 9. Navigate to “Companies” page
		home.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals("Pass", home.existsElement(Companies.getListElements()).getResult());
		// 10. Select a company from companies table
		home.selectACompanyByName(partner_company_name);
		// VP: Verify that the company info page displays
		Assert.assertEquals("Pass", home.existsElement(CompanyInfo.getListElement()).getResult());
		//11. Click on a brand name link
		home.selectABrand(CompanyInfo.BRAND_LIST);
		// Check if PDPP-902 found
		if(home.isElementPresent(BrandInfo.EDIT_BRAND)){
			result.addErrorLog("PDPP-902: 063D Brand Info: DTS user is able to manage brands although the 'Edit brand info' privilege is disabled");
			Assert.assertTrue(false);
		}
		/*
		 * Verify that the brand info page is displayed without the “Edit” and “Delete” link
		 */
		Assert.assertEquals("Pass", home.existsElement(BrandInfo.getAllField()).getResult());
		Assert.assertFalse(home.isElementPresent(BrandInfo.EDIT_BRAND));
		Assert.assertFalse(home.isElementPresent(BrandInfo.DELETE_LINK));
	}	
	
	/*
	 * Verify that DTS user can add new company when the “Add and manage company accounts” privilege is enabled
	 */
	@Test
	public void PDC_09(){
		result.addLog("ID : PDC_09 : Verify that DTS user can add new company when the “Add and manage company accounts” privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage company accounts” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Companies” page is displayed
			9. Navigate to “Companies” page
			VP: Verify that the “Add Company” link is displayed
			10. Click on “Add Company” link
			11. Process through 3 steps of creating new company
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage company accounts” privilege
		home.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.Add_manage_company_account);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		/*
		 * VP. Verify that the “Companies” page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_COMPANY));
		// 9. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		/*
		 * VP: Verify that the “Add Company” link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Companies.ADD_COMPANY));
		// 10. Click on “Add Company” link
		home.click(Companies.ADD_COMPANY);
		// 11. Process through 3 steps of creating new company
		Hashtable<String,String> data = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), data);
		/*
		 * Verify that the new company is created successfully
		 */
		home.click(Xpath.LINK_COMPANY);
		Assert.assertTrue(home.checkACompanyExistByName( data.get("name")));
		// Delete company
		home.deleteACompanyByName(data.get("name"));
	}
	
	/*
	 * Verify that DTS user cannot add new company when the “Add and manage company accounts” privilege is disabled.
	 */
	@Test
	public void PDC_10(){
		result.addLog("ID : PDC_10 : Verify that DTS user cannot add new company when the “Add and manage company accounts” privilege is disabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the  “Add and manage company accounts” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Companies” tab is displayed
			9. Navigate to “Companies” page
			
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);	
		// 6. Disable the  “Add and manage company accounts” privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_company_account);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		//VP. Verify that the “Companies” tab is displayed
		Assert.assertTrue(home.isElementPresent(DTSHome.LINK_COMPANY));
		// 9. Navigate to “Companies” page
		home.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed but "Add Company" link is not displayed
		Assert.assertEquals("Pass", home.existsElement(Companies.getListElements()).getResult());
		Assert.assertFalse(home.isElementPresent(Companies.ADD_COMPANY));
	}
	
	/*
	 * Verify that DTS user can suspend company when the “Add and manage company accounts” privilege is enabled.
	 */
	@Test
	public void PDC_11(){
		result.addLog("ID : PDC_11 : Verify that DTS user can suspend  company when the “Add and manage company accounts” privilege is enabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage company accounts” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Companies” page is displayed
			9. Navigate to “Companies” page
			10. Select a company from companies table
			VP: Verify that the “Suspend” link is displayed
			11. Click on “Suspend” link
			VP: Verify that the suspend confirmation dialog is displayed
			12. Click on “Suspend” button on suspend confirmation dialog
		 */
		/*
		 * Pre-condition: Create a new company
		 */
		home.click(Xpath.LINK_COMPANY);
		// Click add company link
		home.click(Companies.ADD_COMPANY);
		// Create new company
		Hashtable<String,String> data = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), data);
		/*
		 * ***************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);	
		// 6. Enable the  “Add and manage company accounts” privilege
		home.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE,Privileges.Add_manage_company_account);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		//VP. Verify that the “Companies” tab is displayed
		Assert.assertTrue(home.isElementPresent(DTSHome.LINK_COMPANY));
		// 9. Navigate to “Companies” page
		home.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals("Pass", home.existsElement(Companies.getListElements()).getResult());
		// 10. Select a company from companies table
		home.selectACompanyByName(data.get("name"));
		// VP: Verify that the “Suspend” link is displayed
		Assert.assertTrue(home.isElementPresent(CompanyInfo.SUSPEND));
		// 11. Click on “Suspend” link
		home.click(CompanyInfo.SUSPEND);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		/*
		 * VP: Verify that the Suspend confirmation dialog is displayed
		 */
		Assert.assertTrue(home.checkConfirmationDialog(data.get("name"), "Suspend"));
		//12. Click on “Suspend” button on suspend confirmation dialog
		home.selectConfirmationDialogOption("Suspend");
		/*
		 * Verify that the company is suspended
		 */
		home.selectOptionByName(Companies.FILTER, "Suspended");
		Assert.assertTrue(home.checkACompanyExistByName( data.get("name")));
		// Delete company
		home.selectACompanyByName(data.get("name"));
		home.doDelete(CompanyInfo.DELETE);
	}
	
	/*
	 * Verify that DTS user cannot suspend company when the “Add and manage company accounts” privilege is disabled.
	 */
	@Test
	public void PDC_12(){
		result.addLog("ID : PDC_12 : Verify that DTS user cannot suspend company when the “Add and manage company accounts” privilege is disabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage company accounts” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Companies” page is displayed
			9. Navigate to “Companies” page
			10. Select a company from companies table
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);	
		// 6. Disable the  “Add and manage company accounts” privilege
		home.enableAllPrivilege();
		home.disablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE,Privileges.Add_manage_company_account);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		//VP. Verify that the “Companies” tab is displayed
		Assert.assertTrue(home.isElementPresent(DTSHome.LINK_COMPANY));
		// 9. Navigate to “Companies” page
		home.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals("Pass", home.existsElement(Companies.getListElements()).getResult());
		// 10. Select a company from companies table
		home.selectACompanyByName(partner_company_name);
		// Verify that the “Suspend” link is not displayed in company info page
		Assert.assertEquals(home.existsElement(CompanyInfo.getListElement()).getResult(), "Pass");
		Assert.assertFalse(home.isElementPresent(CompanyInfo.SUSPEND));	
	}
	
	/*
	 * Verify that DTS user can delete company when the “Add and manage company accounts” privilege is enabled.
	 */
	@Test
	public void PDC_13(){
		result.addLog("ID : PDC_13 : Verify that DTS user can delete  company when the “Add and manage company accounts” privilege is enabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage company accounts” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Companies” page is displayed
			9. Navigate to “Companies” page
			10. Select a company from companies table
			VP: Verify that the “Delete” link is displayed
			11. Click on “Delete” link
			VP: Verify that the delete confirmation dialog is displayed
			12. Click on “Delete” button on suspend confirmation dialog
		 */
		/*
		 * Pre-condition: Create a new company
		 */
		home.click(Xpath.LINK_COMPANY);
		// Click add company link
		home.click(Companies.ADD_COMPANY);
		// Create new company
		Hashtable<String,String> data = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), data);
		/*
		 * ***************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);	
		// 6. Enable the  “Add and manage company accounts” privilege
		home.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.Add_manage_company_account);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		//VP. Verify that the “Companies” tab is displayed
		Assert.assertTrue(home.isElementPresent(DTSHome.LINK_COMPANY));
		// 9. Navigate to “Companies” page
		home.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals("Pass", home.existsElement(Companies.getListElements()).getResult());
		// 10. Select a company from companies table
		home.selectACompanyByName(data.get("name"));
		// VP: Verify that the “Delete” link is displayed
		Assert.assertTrue(home.isElementPresent(CompanyInfo.DELETE));
		// 11. Click on “Delete” link
		home.click(CompanyInfo.DELETE);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		// VP: Verify that the delete confirmation dialog is displayed
		Assert.assertTrue(home.checkConfirmationDialog(data.get("name"), "Delete"));
		//12. Click on “Delete” button on suspend confirmation dialog
		home.selectConfirmationDialogOption("Delete");
		// Verify that the deleted company is not displayed in the companies list.
		Assert.assertFalse(home.checkACompanyExistByName( data.get("name")));
	}
	
	/*
	 * Verify that DTS user cannot delete company when the “Add and manage company accounts” privilege is disabled.
	 */
	@Test
	public void PDC_14(){
		result.addLog("ID : PDC_14 : Verify that DTS user cannot delete company when the “Add and manage company accounts” privilege is disabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the  “Add and manage company accounts” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Companies” tab is displayed
			9. Navigate to “Companies” page
			10. Select a company from companies table
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);	
		// 6. Disable the  “Add and manage company accounts” privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_company_account);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		//VP. Verify that the “Companies” tab is displayed
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_COMPANY));
		// 9. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals("Pass", home.existsElement(Companies.getListElements()).getResult());
		// 10. Select a company from companies table
		home.selectACompanyByName(partner_company_name);
		// VP : Verify that the “Delete” link is not displayed in company info page
		Assert.assertEquals(home.existsElement(CompanyInfo.getListElement()).getResult(), "Pass");
		Assert.assertFalse(home.isElementPresent(CompanyInfo.DELETE));
	}
	
}
