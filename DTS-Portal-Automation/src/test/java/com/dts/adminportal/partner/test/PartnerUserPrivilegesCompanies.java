package com.dts.adminportal.partner.test;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AddBrand;
import dts.com.adminportal.model.AddCompany;
import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.BrandInfo;
import dts.com.adminportal.model.CompanyContact;
import dts.com.adminportal.model.CompanyInfo;
import dts.com.adminportal.model.PartnerCompanyEdit;
import dts.com.adminportal.model.PartnerCompanyInfo;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UserEdit;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.Xpath;

public class PartnerUserPrivilegesCompanies extends CreatePage{
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
	 * Verify that the partner user can view the company and brand info when the “Edit Company Info”, “Edit brand info”  are disabled.
	 */
	@Test
	public void TCPPC_01(){
		result.addLog("ID : TCPPC_01 : Verify that the partner user can view the company and brand info when the “Edit Company Info”, “Edit brand info”  are disabled.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Disable the  “Edit Company Info”, “Edit brand info” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP. Verify that the “Companies” page is displayed
			9. Navigate to “Companies” page
			VP: Verify that the company page display the partner's company info correctly
			10. Click on a brand name
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.dtsSelectUserByEmail(partneruser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);	
		// 6.Disable the “Edit Company Info”, “Edit brand info” privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_brand_info);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as a partner user successfully
		home.login(partneruser, password);
		/*
		 * VP. Verify that the “Companies” page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_PARTNER_COMPANY));
		// 9. Navigate to “Companies” page
		home.click(Xpath.LINK_PARTNER_COMPANY);
		/*
		 * VP: Verify that the company page display the partner's company info correctly
		 */
		Assert.assertEquals(home.existsElement(PartnerCompanyInfo.getListXpath()).getResult(), "Pass");
		// 10. Click on a brand name
		home.selectABrandByName( partner_brand_name_1);
		/*
		 * Verify that the partner's company brand info display correctly
		 */
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(), "Pass");
	}
	
	/*
	 *Verify that partner user can add new brand when the “Edit Company Info” privilege is enabled.
	 */
	@Test
	public void TCPPC_02(){
		result.addLog("ID : TCPPC_02 : Verify that partner user can add new brand when the “Edit Company Info” privilege is enabled.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Enable the  “Edit Company Info” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP. Verify that the “Companies” page is displayed
			9. Navigate to “Companies” page
			VP: Verify that the company info page displays  and the “Edit”, “Change” and “Add” link are also displayed.
			10. Click “Add” link
			VP: Verify that the “Edit Brand Info” page is displayed.
			11. Fill valid value into all required fields
			12. Click “Save” link
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.dtsSelectUserByEmail(partneruser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Edit Company Info” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as a partner user successfully
		home.login(partneruser, password);
		// 9. Navigate to “Companies” page
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// VP: Verify that the company page display the partner's company info correctly
		home.existsElement(PartnerCompanyInfo.ADD_BRAND);
		// 10.Click “Add” link
		home.click(PartnerCompanyInfo.ADD_BRAND);
		// VP: Verify that the “Edit Brand Info” page is displayed.
		Assert.assertEquals(home.existsElement(AddBrand.getHash()).getResult(), "Pass");
		// 11. Fill valid value into all required fields
		// 12. Click “Save” link
		Hashtable<String,String> data = TestData.brandDraft();
		home.addBrand(AddBrand.getHash(), data);
		// Verify that the 063P Brand Info page is displayed
		Assert.assertEquals(home.existsElement(BrandInfo.getBrandInfo())
				.getResult(), "Pass");
	}
	
	/*
	 * Verify that partner user cannot edit company, add new brand and change primary contact when the “Edit Company Info” privilege is disabled.
	 */
	@Test
	public void TCPPC_03() {
		result.addLog("ID : TCPPC_03 : Verify that partner user cannot edit company, add new brand and change primary contact when the “Edit Company Info” privilege is disabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Disable the  “Edit Company Info” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP. Verify that the “Companies” page is displayed
			9. Navigate to “Companies” page
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.selectUserByEmail(partneruser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Disable the  “Edit Company Info” privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as a partner user successfully
		home.login(partneruser, password);
		// 9. Navigate to “Companies” page
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// partner user cannot edit company, add new brand and change primary contact
		Assert.assertFalse(home.existsElement(PartnerCompanyInfo.EDIT));
		Assert.assertFalse(home.existsElement(PartnerCompanyInfo.ADD_BRAND));
		Assert.assertFalse(home.existsElement(PartnerCompanyInfo.CHANGE_PARTNER_CONTACT));
	}
	
	/*
	 * Verify that partner user can  edit company info when the “Edit Company Info” privilege is enabled
	 */
	@Test
	public void TCPPC_04() {
		result.addLog("ID : TCPPC_04 : Verify that partner user can  edit company info when the “Edit Company Info” privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Enable the  “Edit Company Info” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP. Verify that the “Companies” page is displayed
			9. Navigate to “Companies” page
			VP: Verify that the company info page displays  and the “Edit”, “Change” and “Add” link are also displayed.
			10. Click “Edit” link
			VP: Verify that the Company edit page is displayed.
			11. Change some company info
			12. Click “Save” link
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a partner user from the Users table
		home.selectUserByEmail(partneruser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Edit Company Info” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner user successfully
		home.login(partneruser, password);
		/*
		 * VP. Verify that the “Companies” page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_PARTNER_COMPANY));
		// 9. Navigate to “Companies” page
		home.click(Xpath.LINK_PARTNER_COMPANY);
		/*
		 *  VP: Verify that the company info page displays  and the “Edit”, “Change” and “Add” link are also displayed
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.EDIT));
		Assert.assertTrue(home.isElementPresent(CompanyInfo.CHANGE_PARTNER_CONTACT));
		Assert.assertTrue(home.isElementPresent(CompanyInfo.ADD_BRAND));
		// 10. Click “Edit” link
		home.click(PartnerCompanyInfo.EDIT);
		/*
		 * VP: Verify that the Company edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(PartnerCompanyEdit.getListXpath()).getResult(), "Pass");
		// 11. Change some company info
		String newAdress = "New Address " + RandomStringUtils.randomNumeric(10);
		home.editData(AddCompany.ADDRESS1, newAdress);
		// 12. Click “Save” link
		home.click(AddCompany.SAVE);
		/*
		 * Verify that company info page is displayed with new info
		 */
		Assert.assertEquals(home.getTextByXpath(CompanyInfo.ADDRESS1), newAdress);
	}
	
	/*
	 * Verify that partner user can change the primary contact info when the “Edit Company Info” privilege is enabled
	 */
	@Test
	public void TCPPC_05(){
		
		result.addLog("ID : TCPPC_05 :Verify that partner user can change the primary contact info when the “Edit Company Info” privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Enable the  “Edit Company Info” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP. Verify that the “Companies” page is displayed
			9. Navigate to “Companies” page
			VP: Verify that the company info page displays  and the “Edit”, “Change” and “Add” link are also displayed.
			10. Click “Change” link
			VP: Verify that the Company edit page is displayed.
			11. Change the primary contact info to another one
			12. Click “Save” link
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a partner user from the Users table
		home.selectUserByEmail(partneruser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Edit Company Info” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner user successfully
		home.login(partneruser, password);
		/*
		 * VP: Verify that the “Companies” page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_PARTNER_COMPANY));
		// 9. Navigate to “Companies” page
		home.click(Xpath.LINK_PARTNER_COMPANY);
		/*
		 * VP: Verify that the company info page displays  and the “Edit”, “Change” and “Add” link are also displayed
		 */
		Assert.assertEquals(home.existsElement(PartnerCompanyInfo.getListXpath()).getResult(), "Pass");
		Assert.assertTrue(home.isElementPresent(PartnerCompanyInfo.EDIT));
		Assert.assertTrue(home.isElementPresent(PartnerCompanyInfo.CHANGE_PARTNER_CONTACT));
		Assert.assertTrue(home.isElementPresent(PartnerCompanyInfo.ADD_BRAND));
		// 10. Click “Change” link
		home.click(PartnerCompanyInfo.CHANGE_PARTNER_CONTACT);
		/*
		 * VP: Verify that the Company edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(CompanyContact.getElementInfo()).getResult(), "Pass");
		// 11. Change the primary contact info to another one
		home.selectPartnerUserByEmail(partneruser);
		// Get primary contact name
		String ContactName = home.getTextByXpath(CompanyContact.NEW_CONTACT);
		// 12. Click “Save” link
		home.click(CompanyContact.SAVE);
		/*
		 * Verify that company info page is displayed with new primary contact info
		 */
		Assert.assertTrue(ContactName.contains(home.getTextByXpath(CompanyInfo.CONTACT_NAME)));
	}
	
	/*
	 * Verify that partner user can edit the company brand info when the “Edit brand info” privilege is enabled.
	 */
	@Test
	public void TCPPC_06(){
		result.addLog("ID : TCPPC_06 : Verify that partner user can edit the company brand info when the “Edit brand info” privilege is enabled.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Enable the  “Edit brand info” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP. Verify that the “Companies” page is displayed
			9. Navigate to “Companies” page
			VP: Verify that the company info page is displayed
			10. Click on a brand name link
			VP: Verify that the “Edit” link is displayed in the Brand info page
			11. Click on “Edit” link
			12. Change some brand info in Brand edit page
			13. Click “Save” link
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.selectUserByEmail(partneruser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Edit brand info” privilege
		home.enablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.Edit_brand_info);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as a partner user successfully
		home.login(partneruser, password);
		// 9. Navigate to “Companies” page
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// VP: Verify that the company page display the partner's company info correctly
		Assert.assertEquals(home.getTextByXpath(CompanyInfo.NAME), partner_company_name);
		// 10. Click on a brand name
		home.selectABrandByName( partner_brand_name_1);
		/*
		 * VP: Verify that the “Edit” link is displayed in the Brand info page
		 */
		Assert.assertTrue(home.isElementPresent(BrandInfo.EDIT_BRAND));
		// 11. Click on “Edit” link
		home.click(BrandInfo.EDIT_BRAND);
		// 12. Change some brand info in Brand edit page
		String newTagLine = RandomStringUtils.randomNumeric(10);
		home.editData(AddBrand.BRAND_TAG_LINE, newTagLine);
		// 13. Click “Save” link
		home.click(AddBrand.SAVE);
		/*
		 * Verify that the brand info page is displayed with new value
		 */
		Assert.assertEquals(home.getTextByXpath(BrandInfo.BRAND_TAG_LINE),newTagLine);
	}
	
	/*
	 * Verify that partner user can delete the company brand when the “Edit brand info” privilege is enabled.
	 */
	@Test
	public void TCPPC_07(){
		
		result.addLog("ID : TCPPC_07 : Verify that partner user can delete the company brand when the “Edit brand info” privilege is enabled.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Enable the  “Edit brand info” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP. Verify that the “Companies” page is displayed
			9. Navigate to “Companies” page
			VP: Verify that the company info page is displayed
			10. Click on a brand name link
			VP: Verify that the “Delete” link is displayed in the Brand info page
			11. Click on “Delete” link
			12. Click “Delete” on the delete confirmation dialog
		 */
		/*
		 * PreCondition: Create new brand
		 */
		// Navigate to companies page
		home.click(Xpath.LINK_COMPANY);
		// Select a company on table
		home.selectACompanyByName(partner_company_name);
		// Click Add brand link
		home.click(CompanyInfo.ADD_BRAND);
		// Create new brand
		Hashtable<String, String> data = TestData.brandDraft();
		home.addBrand(AddBrand.getHash(), data);
		/*
		 * ***********************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.selectUserByEmail(partneruser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Edit brand info” privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_brand_info);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as a partner user successfully
		home.login(partneruser, password);
		// 9. Navigate to “Companies” page
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// VP: Verify that the company page display the partner's company info correctly
		Assert.assertEquals("Pass", home.existsElement(PartnerCompanyInfo.getListXpath()).getResult());
		// 10. Click on a brand name
		home.selectABrandByName( data.get("name"));
		// VP: Verify that the “Delete” link is displayed in the Brand info page
		Assert.assertTrue(home.existsElement(BrandInfo.DELETE_LINK));
		// 11. Click on “Delete” link
		// 12. Click “Delete” on the delete confirmation dialog
		home.doDelete(BrandInfo.DELETE_LINK);
		/*
		 * The portal redirects to 061 Company Info page and the company's brand
		 * is deleted successfully
		 */
		Assert.assertFalse(home.checkBrandExist( data.get("name")));
	}
	
	/*
	 *Verify that partner user cannot edit or delete the company brand when the “Edit brand info” privilege is disabled.
	 */
	@Test
	public void TCPPC_08(){
		
		result.addLog("ID : TCPPC_08 :Verify that partner user cannot edit or delete the company brand when the "
				+ "“Edit brand info” privilege is disabled.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Disable the  “Edit brand info” privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP. Verify that the “Companies” page is displayed
			9. Navigate to “Companies” page
			VP: Verify that the company info page is displayed
			10. Click on a brand name link
		 */
		
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4.Select a partner user from the Users table
		home.dtsSelectUserByEmail(partneruser);
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Disable the “Edit brand info” privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_brand_info);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as a partner user successfully
		home.login(partneruser, password);
		// 9. Navigate to “Companies” page
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// VP: Verify that the company page display the partner's company info correctly
		Assert.assertEquals("Pass", home.existsElement(PartnerCompanyInfo.getListXpath()).getResult());
		// 10. Click on a brand name
		home.selectABrand(CompanyInfo.BRAND_LIST);
		// partner user cannot edit or delete the company brand
		Assert.assertFalse(home.existsElement(BrandInfo.EDIT_BRAND));
		Assert.assertFalse(home.existsElement(BrandInfo.DELETE_LINK));
	}
	
	/*
	 * Verify that new brand could be added successfully when the “Edit Company Info” privilege is enabled and “Edit brand info” privilege is disabled.
	 */
	@Test
	public void TCPPC_09() {
		result.addLog("ID : TCPPC_09 : Verify that new brand could be added successfully when the “Edit Company Info” privilege is enabled and “Edit brand info” privilege is disabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click “Edit” link
			6. Disable the  “Edit brand info” Privilege
			7. Enable “Edit Company Info” privilege
			8. Log out DTS portal
			9. Log into DTS portal as above partner user successfully
			VP. Verify that the “Companies” page is displayed
			10. Navigate to “Companies” page
			VP: Verify that the company info page is displayed
			11. Click “Add” link
			12. Fill valid value into all required fields
			13. Click “Save” link
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a partner user from the Users table
		home.dtsSelectUserByEmail(partneruser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the  “Edit brand info” Privilege
		home.disablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.Edit_brand_info);
		// 7. Enable the  “Edit Company Info” privilege
		home.enablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above partner user successfully
		home.login(partneruser, password);
		/*
		 * VP. Verify that the “Companies” page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_PARTNER_COMPANY));
		// 10. Navigate to “Companies” page
		home.click(Xpath.LINK_PARTNER_COMPANY);
		/*
		 *  VP: Verify that the company info page displays
		 */
		Assert.assertEquals(home.existsElement(PartnerCompanyInfo.getListXpath()).getResult(), "Pass");
		// 11. Click “Add” link
		home.click(PartnerCompanyInfo.ADD_BRAND);
		// 12. Fill valid value into all required fields
		// 13. Click “Save” link
		Hashtable<String,String> data = TestData.brandDraft();
		home.addBrand(AddBrand.getHash(), data);
		/*
		 * Verify that new brand is added successfully
		 */
		home.click(Xpath.LINK_PARTNER_COMPANY);
		Assert.assertTrue(home.checkBrandExist(data.get("name")));
	}
	
}
