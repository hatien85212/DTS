package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddBrand;
import com.dts.adminportal.model.AddCompany;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.BrandInfo;
import com.dts.adminportal.model.Companies;
import com.dts.adminportal.model.CompanyContact;
import com.dts.adminportal.model.CompanyInfo;
import com.dts.adminportal.model.DTSHome;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PartnerCompanyInfo;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.UserEdit;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.TestData;

public class DTSPrivilegesCompanies extends BasePage {

	@Override
	protected void initData() {
	}	
	
	/*
	 *Verify that the DTS user can view the company and brand info when the �Edit Company Info�, �Edit brand info� and �Add and manage company accounts� are disabled.
	 */
	@Test
	public void PDC_01(){
		companyControl.addLog("ID : PDC_01 : Verify that the DTS user can view the company and brand info when the �Edit Company Info�, �Edit brand info� and �Add and manage company accounts� are disabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the  �Edit Company Info�, �Edit brand info� and �Add and manage company accounts� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Companies� page is displayed
			9. Navigate to �Companies� page
			VP: Verify that the companies list page is displayed
			10. Select a company from companies table
			VP: Verify that the company page display the DTS's company info correctly
			11. Click on a brand name
		 */
		// 2. Log into DTS portal as a super admin successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		companyControl.click(PageHome.LINK_USERS);
		// 4.Select a partner user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click "Edit" link
		companyControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		// 6.Disable the  �Edit Company Info�, �Edit brand info� and �Add and manage company accounts� privileges
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_Company_Info.getName());
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_brand_info.getName());
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_company_accounts.getName());
		companyControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		companyControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 9. Navigate to �Companies� page
		companyControl.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals(true, companyControl.existsElement(Companies.getListElements()));
		// 10. Select a company from companies table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		// VP: Verify that the company page display the DTS's company info correctly
		Assert.assertEquals(true, companyControl.existsElement(CompanyInfo.getListElement()));
		// 11. Click on a brand name
		companyControl.selectABrand(CompanyInfo.BRAND_LIST);
		// Verify that the DTS's company brand info display correctly.
		Assert.assertEquals(companyControl.existsElement(BrandInfo.getAllField()), true);
		
	}
	
	/*
	 * Verify that DTS user can add new brand when the �Edit Company Info� privilege is enabled
	 */
	@Test
	public void PDC_02(){
		companyControl.addLog("ID : PDC_02 : Verify that DTS user can add new brand when the �Edit Company Info� privilege is enabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Edit Company Info� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Companies� tab is displayed
			9. Navigate to �Companies� page
			VP: Verify that the companies list page is displayed
			10. Select a company from companies table
			VP: Verify that the company info page displays  and the �Edit�, �Change� and �Add New Brand� link are also displayed.
			11. Click �Add� link
			VP: Verify that the �Edit Brand Info� page is displayed.
			12. Fill valid value into all required fields
			13. Click �Save� link
		 */
		// 2. Log into DTS portal as a super admin successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		companyControl.click(PageHome.LINK_USERS);
		// 4.Select a partner user from the Users table
		userControl.selectUserInfoByEmail(DTS_USER);
		// 5. Click "Edit" link
		companyControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		// 6. Enable the  �Edit Company Info� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_Company_Info.getName());
		companyControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		companyControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// VP. Verify that the �Companies� tab is displayed
		Assert.assertTrue(companyControl.isElementPresent(DTSHome.LINK_COMPANY));
		// 9. Navigate to �Companies� page
		companyControl.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals(true, companyControl.existsElement(Companies.getListElements()));
		// 10. Select a company from companies table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		// VP: Verify that the company info page displays  and the �Edit�, �Change� and �Add New Brand� link are also displayed.
		Assert.assertEquals("Edit", companyControl.getTextByXpath(CompanyInfo.EDIT));
		Assert.assertEquals("Change", companyControl.getTextByXpath(CompanyInfo.CHANGE_PARTNER_CONTACT));
		Assert.assertEquals("Add New Brand", companyControl.getTextByXpath(CompanyInfo.ADD_BRAND));
		// 11. Click �Add� link
		companyControl.click(CompanyInfo.ADD_BRAND);
		// VP: Verify that the �Edit Brand Info� page is displayed.
		Assert.assertEquals(true, companyControl.existsElement(AddBrand.getListAddBrand()));
		// 12. Fill valid value into all required fields
		// 13. Click �Save� link
		Hashtable<String,String> data = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), data);
		// Verify that new brand is added successfully.
		Assert.assertTrue(companyControl.getTextByXpath(BrandInfo.NAME).contains(data.get("name")));
	}
	
	/*
	 * Verify that DTS user cannot edit company, add new brand and change primary contact when the �Edit Company Info� privilege is disabled
	 */
	@Test
	public void PDC_03(){
		companyControl.addLog("ID : PDC_03 : Verify that DTS user cannot edit company, add new brand and change primary contact when the �Edit Company Info� privilege is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the  �Edit Company Info� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Companies� tab is displayed
			9. Navigate to �Companies� page
			VP: Verify that the companies list page is displayed
			10. Select a company from companies table
		 */
		// 2. Log into DTS portal as a super admin successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		companyControl.click(PageHome.LINK_USERS);
		// 4.Select a partner user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click "Edit" link
		companyControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		// 6. Enable the �Edit Company Info� privilege
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_Company_Info.getName());
		companyControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		companyControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		//VP. Verify that the �Companies� tab is displayed
		Assert.assertTrue(companyControl.isElementPresent(DTSHome.LINK_COMPANY));
		// 9. Navigate to �Companies� page
		companyControl.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals(true, companyControl.existsElement(Companies.getListElements()));
		// 10. Select a company from companies table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		// Verify that the company info page is displayed, but the �Edit�, �Change� and �Add New Brand� link are not displayed.
		Assert.assertEquals(true, companyControl.existsElement(CompanyInfo.getListElement()));
		Assert.assertFalse(companyControl.isElementPresent(CompanyInfo.EDIT));
		Assert.assertFalse(companyControl.isElementPresent(CompanyInfo.CHANGE_PARTNER_CONTACT));
		Assert.assertFalse(companyControl.isElementPresent(CompanyInfo.ADD_BRAND));
	}
	/*
	 *Verify that DTS user can edit company info when the �Edit Company Info� privilege is enabled.
	 */
	@Test
	public void PDC_04(){
		companyControl.addLog("ID : PDC_04 : Verify that DTS user can edit company info when the �Edit Company Info� privilege is enabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Edit Company Info� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Companies� page is displayed
			9. Navigate to �Companies� page
			VP: Verify that the companies list page is displayed
			10. Select a company from companies table
			VP: Verify that the company info page displays  and the �Edit�, �Change� and �Add� link are also displayed.
			11. Click �Edit� link
			VP: Verify that the Company edit page is displayed.
			12. Change some company info
			13. Click �Save� link
		 */
		// 2. Log into DTS portal as a super admin successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		companyControl.click(PageHome.LINK_USERS);
		// 4.Select a partner user from the Users table
		userControl.selectUserInfoByEmail(DTS_USER);
		// 5. Click "Edit" link
		companyControl.click(UserMgmt.EDIT);	
		userControl.enableAllPrivileges();
		// 6. Enable the  �Edit Company Info� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_Company_Info.getName());
		companyControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		companyControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		//VP. Verify that the �Companies� tab is displayed
		Assert.assertTrue(companyControl.isElementPresent(DTSHome.LINK_COMPANY));
		// 9. Navigate to �Companies� page
		companyControl.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals(true, companyControl.existsElement(Companies.getListElements()));
		// 10. Select a company from companies table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		// Verify that the company info page is displayed, but the �Edit�, �Change� and �Add New Brand� link are not displayed.
		Assert.assertEquals("Edit", companyControl.getTextByXpath(CompanyInfo.EDIT));
		Assert.assertEquals("Change", companyControl.getTextByXpath(CompanyInfo.CHANGE_PARTNER_CONTACT));
		Assert.assertEquals("Add New Brand", companyControl.getTextByXpath(CompanyInfo.ADD_BRAND));
		//11. Click �Edit� link
		companyControl.click(CompanyInfo.EDIT);
		//VP: Verify that the Company edit page is displayed.
		Assert.assertEquals(true, companyControl.existsElement(AddCompany.getHash()));
		//12. Change some company info
		String address1 = RandomStringUtils.randomAlphabetic(10);
		companyControl.editData(AddCompany.ADDRESS1, address1);
		//13. Click �Save� link
		companyControl.click(AddCompany.SAVE);
		//Verify that company info page is displayed with new info
		Assert.assertEquals(companyControl.getTextByXpath(CompanyInfo.ADDRESS1),address1);
	}
	
	/*
	 *Verify that DTS user can change the primary contact info when the �Edit Company Info� privilege is enabled.
	 */
	@Test
	public void PDC_05(){
		companyControl.addLog("ID : PDC_05 : Verify that DTS user can change the primary contact info when the �Edit Company Info� privilege is enabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Edit Company Info� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Companies� page is displayed
			9. Navigate to �Companies� page
			VP: Verify that the companies list page is displayed
			10. Select a company from companies table
			VP: Verify that the company info page displays  and the �Edit�, �Change� and �Add� link are also displayed.
			11. Click �Change� link
			VP: Verify that the Change Primary Partner Contact page is displayed.
			12. Change the primary contact info to another one
			13. Click �Save� link
		 */
		// 2. Log into DTS portal as a super admin successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		companyControl.click(PageHome.LINK_USERS);
		// 4.Select a partner user from the Users table
		userControl.selectUserInfoByEmail(DTS_USER);
		// 5. Click "Edit" link
		companyControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		// 6. Enable the  �Edit Company Info� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_Company_Info.getName());
		companyControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		companyControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		//VP. Verify that the �Companies� tab is displayed
		Assert.assertTrue(companyControl.isElementPresent(PageHome.LINK_COMPANY));
		// 9. Navigate to �Companies� page
		companyControl.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals(true, companyControl.existsElement(Companies.getListElements()));
		// 10. Select a company from companies table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		// VP: Verify that the company info page displays  and the �Edit�, �Change� and �Add� link are also displayed.
		Assert.assertEquals(companyControl.getTextByXpath(CompanyInfo.EDIT), "Edit");
		Assert.assertEquals(companyControl.getTextByXpath(CompanyInfo.CHANGE_PARTNER_CONTACT), "Change");
		Assert.assertEquals(companyControl.getTextByXpath(CompanyInfo.ADD_BRAND), "Add New Brand");
		// 11. Click �Change� link
		companyControl.click(CompanyInfo.CHANGE_PARTNER_CONTACT);
		// VP: Verify that the Change Primary Partner Contact page is displayed.
		Assert.assertEquals(true, companyControl.existsElement(CompanyContact.getElementInfo()));
		// 12. Change the primary contact info to another one
		userControl.selectUserInfoByEmail(PARTNER_USER);
		// 13. Click �Save� link
		String newContact = companyControl.getTextByXpath(CompanyContact.NEW_CONTACT);
		companyControl.click(CompanyContact.SAVE);
		// Verify that company info page is displayed with new primary contact info
		ArrayList<String> list = new ArrayList<String>();
		list.add(companyControl.getTextByXpath(PartnerCompanyInfo.CONTACT_NAME));
		list.add(companyControl.getTextByXpath(PartnerCompanyInfo.PHONE).replaceAll("\\+\\s", "\\+"));
		list.add(companyControl.getTextByXpath(PartnerCompanyInfo.EMAIL));
		Assert.assertEquals(true, companyControl.existsElement(CompanyInfo.getListElement()));
		Assert.assertTrue(ListUtil.containsListText(newContact, list));
	}
	
	/*
	 * Verify that DTS user can edit the company brand info when the �Edit brand info� privilege is enabled.
	 */
	@Test
	public void PDC_06(){
		companyControl.addLog("ID : PDC_06 : Verify that DTS user can edit the company brand info when the �Edit brand info� privilege is enabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Edit brand info� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Companies� tab is displayed
			9. Navigate to �Companies� page
			VP: Verify that the companies list page is displayed
			10. Select a company from companies table
			VP: Verify that the company info page is displayed
			11. Click on a brand name link
			VP: Verify that the �Edit� link is displayed in the Brand info page
			12. Click on �Edit� link
			13. Change some brand info in Brand edit page
			14. Click �Save� link
		 */
		// 2. Log into DTS portal as a super admin successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		companyControl.click(PageHome.LINK_USERS);
		// 4.Select a partner user from the Users table
		userControl.selectUserInfoByEmail(DTS_USER);
		// 5. Click "Edit" link
		companyControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		// 6. Enable the  �Edit brand info� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_brand_info.getName());
		companyControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		companyControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		//VP. Verify that the �Companies� tab is displayed
		Assert.assertTrue(companyControl.isElementPresent(DTSHome.LINK_COMPANY));
		// 9. Navigate to �Companies� page
		companyControl.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals(true, companyControl.existsElement(Companies.getListElements()));
		// 10. Select a company from companies table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		// VP: Verify that the company info page displays
		Assert.assertEquals(true, companyControl.existsElement(CompanyInfo.getListElement()));
		// 11. Click on a brand name link
		companyControl.selectABrand(CompanyInfo.BRAND_LIST);
		// VP: Verify that the �Edit� link is displayed in the Brand info page
		Assert.assertTrue(companyControl.isElementPresent(BrandInfo.EDIT_BRAND));
		// 12. Click on �Edit� link
		companyControl.click(BrandInfo.EDIT_BRAND);
		// 13. Change some brand info in Brand edit page
		String brandOverview = RandomStringUtils.randomAlphabetic(10);
		companyControl.type(BrandInfo.BRAND_OVERVIEW, brandOverview);
		// 14. Click �Save� link
		companyControl.click(BrandInfo.SAVE);
		// Verify that the brand info page is displayed with new value.
		Assert.assertEquals(companyControl.getTextByXpath(BrandInfo.BRAND_OVERVIEW), brandOverview);
	}
	
	/*
	 * Verify that DTS user can delete the company brand when the �Edit brand info� privilege is enabled.
	 */
	@Test
	public void PDC_07(){
		companyControl.addLog("ID : PDC_07 : Verify that DTS user can delete the company brand when the �Edit brand info� privilege is enabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Edit brand info� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Companies� page is displayed
			9. Navigate to �Companies� page
			VP: Verify that the companies list page is displayed
			10. Select a company from companies table
			VP: Verify that the company info page is displayed
			11. Click on a brand name link
			VP: Verify that the �Delete� link is displayed in the Brand info page
			12. Click on �Delete� link
			13. Click �Delete� on the delete confirmation dialog
		 */
		/*
		 * Pre-condition: Create a new brand
		 */
		// 2. Log into DTS portal as a super admin successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		companyControl.click(PageHome.LINK_COMPANY);
		// Select a company from table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		// Click Add brand link
		companyControl.click(CompanyInfo.ADD_BRAND);
		// Create brand
		Hashtable<String,String> data = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), data);
		/*
		 * *****************************************************************
		 */
		// 3. Navigate to "Users" page
		companyControl.click(PageHome.LINK_USERS);
		// 4.Select a partner user from the Users table
		userControl.selectUserInfoByEmail(DTS_USER);
		// 5. Click "Edit" link
		companyControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		// 6. Enable the  �Edit brand info� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_brand_info.getName());
		companyControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		companyControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		//VP. Verify that the �Companies� tab is displayed
		Assert.assertTrue(companyControl.isElementPresent(DTSHome.LINK_COMPANY));
		// 9. Navigate to �Companies� page
		companyControl.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals(true, companyControl.existsElement(Companies.getListElements()));
		// 10. Select a company from companies table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		// VP: Verify that the company info page displays
		Assert.assertEquals(true, companyControl.existsElement(CompanyInfo.getListElement()));
		// 11. Click on a brand name link
		companyControl.selectABrandByName( data.get("name"));
		// VP: Verify that the �Delete� link is displayed in the Brand info page
		Assert.assertTrue(companyControl.isElementPresent(BrandInfo.DELETE_LINK));
		// 12. Click on �Delete� link
		// 13. Click �Delete� on the delete confirmation dialog
		companyControl.doDelete(BrandInfo.DELETE_LINK);
		// Verify that the company info page is displayed without the deleted brand.
		Assert.assertFalse(companyControl.checkBrandExist( data.get("name")));
	}
	
	/*
	 * Verify that DTS user cannot edit or delete the company brand when the �Edit brand info� privilege is disabled.
	 */
	@Test
	public void PDC_08(){
		companyControl.addLog("ID : PDC_08 : Verify that DTS user cannot edit or delete the company brand when the �Edit brand info� privilege is disabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the  �Edit brand info� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Companies� page is displayed
			9. Navigate to �Companies� page
			VP: Verify that the companies list page is displayed
			10. Select a company from companies table
			VP: Verify that the company info page is displayed
			11. Click on a brand name link
		 */
		// 2. Log into DTS portal as a super admin successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		companyControl.click(PageHome.LINK_USERS);
		// 4.Select a partner user from the Users table
		userControl.selectUserInfoByEmail(DTS_USER);
		// 5. Click "Edit" link
		companyControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		// 6. Enable the  �Edit brand info� privilege
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE,Privileges.privileges.Edit_brand_info.getName());
		companyControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		companyControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		//VP. Verify that the �Companies� tab is displayed
		Assert.assertTrue(companyControl.isElementPresent(DTSHome.LINK_COMPANY));
		// 9. Navigate to �Companies� page
		companyControl.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals(true, companyControl.existsElement(Companies.getListElements()));
		// 10. Select a company from companies table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		// VP: Verify that the company info page displays
		Assert.assertEquals(true, companyControl.existsElement(CompanyInfo.getListElement()));
		//11. Click on a brand name link
		companyControl.selectABrand(CompanyInfo.BRAND_LIST);
		// Check if PDPP-902 found
		if(companyControl.isElementPresent(BrandInfo.EDIT_BRAND)){
			companyControl.addErrorLog("PDPP-902: 063D Brand Info: DTS user is able to manage brands although the 'Edit brand info' privilege is disabled");
			Assert.assertTrue(false);
		}
		/*
		 * Verify that the brand info page is displayed without the �Edit� and �Delete� link
		 */
		Assert.assertEquals(true, companyControl.existsElement(BrandInfo.getAllField()));
		Assert.assertFalse(companyControl.isElementPresent(BrandInfo.EDIT_BRAND));
		Assert.assertFalse(companyControl.isElementPresent(BrandInfo.DELETE_LINK));
	}	
	
	/*
	 * Verify that DTS user can add new company when the �Add and manage company accounts� privilege is enabled
	 */
	@Test
	public void PDC_09(){
		companyControl.addLog("ID : PDC_09 : Verify that DTS user can add new company when the �Add and manage company accounts� privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage company accounts� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Companies� page is displayed
			9. Navigate to �Companies� page
			VP: Verify that the �Add Company� link is displayed
			10. Click on �Add Company� link
			11. Process through 3 steps of creating new company
		 */
		// 2. Log into DTS portal as a super admin successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		companyControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		companyControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		// 6. Enable the  �Add and manage company accounts� privilege
		userControl.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_company_accounts.getName());
		companyControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		companyControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the �Companies� page is displayed
		 */
		Assert.assertTrue(companyControl.isElementPresent(PageHome.LINK_COMPANY));
		// 9. Navigate to �Companies� page
		companyControl.click(PageHome.LINK_COMPANY);
		/*
		 * VP: Verify that the �Add Company� link is displayed
		 */
		Assert.assertTrue(companyControl.isElementPresent(Companies.ADD_COMPANY));
		// 10. Click on �Add Company� link
		companyControl.click(Companies.ADD_COMPANY);
		// 11. Process through 3 steps of creating new company
		Hashtable<String,String> data = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), data);
		/*
		 * Verify that the new company is created successfully
		 */
		companyControl.click(PageHome.LINK_COMPANY);
		Assert.assertTrue(companyControl.checkACompanyExistByName( data.get("name")));
		// Delete company
		companyControl.deleteACompanyByName(data.get("name"));
	}
	
	/*
	 * Verify that DTS user cannot add new company when the �Add and manage company accounts� privilege is disabled.
	 */
	@Test
	public void PDC_10(){
		companyControl.addLog("ID : PDC_10 : Verify that DTS user cannot add new company when the �Add and manage company accounts� privilege is disabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the  �Add and manage company accounts� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Companies� tab is displayed
			9. Navigate to �Companies� page
			
		 */
		// 2. Log into DTS portal as a super admin successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		companyControl.click(PageHome.LINK_USERS);
		// 4.Select a partner user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click "Edit" link
		companyControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		// 6. Disable the  �Add and manage company accounts� privilege
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_company_accounts.getName());
		companyControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		companyControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		//VP. Verify that the �Companies� tab is displayed
		Assert.assertTrue(companyControl.isElementPresent(DTSHome.LINK_COMPANY));
		// 9. Navigate to �Companies� page
		companyControl.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed but "Add Company" link is not displayed
		Assert.assertEquals(true, companyControl.existsElement(Companies.getListElements()));
		Assert.assertFalse(companyControl.isElementPresent(Companies.ADD_COMPANY));
	}
	
	/*
	 * Verify that DTS user can suspend company when the �Add and manage company accounts� privilege is enabled.
	 */
	@Test
	public void PDC_11(){
		companyControl.addLog("ID : PDC_11 : Verify that DTS user can suspend  company when the �Add and manage company accounts� privilege is enabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage company accounts� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Companies� page is displayed
			9. Navigate to �Companies� page
			10. Select a company from companies table
			VP: Verify that the �Suspend� link is displayed
			11. Click on �Suspend� link
			VP: Verify that the suspend confirmation dialog is displayed
			12. Click on �Suspend� button on suspend confirmation dialog
		 */
		// 2. Log into DTS portal as a super admin successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		/*
		 * Pre-condition: Create a new company
		 */
		companyControl.click(PageHome.LINK_COMPANY);
		// Click add company link
		companyControl.click(Companies.ADD_COMPANY);
		// Create new company
		Hashtable<String,String> data = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), data);
		/*
		 * ***************************************************
		 */
		// 3. Navigate to "Users" page
		companyControl.click(PageHome.LINK_USERS);
		// 4.Select a partner user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click "Edit" link
		companyControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		// 6. Enable the  �Add and manage company accounts� privilege
		userControl.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE,Privileges.dtsPrivileges.Add_and_manage_company_accounts.getName());
		companyControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		companyControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		//VP. Verify that the �Companies� tab is displayed
		Assert.assertTrue(companyControl.isElementPresent(DTSHome.LINK_COMPANY));
		// 9. Navigate to �Companies� page
		companyControl.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals(true, companyControl.existsElement(Companies.getListElements()));
		// 10. Select a company from companies table
		companyControl.selectACompanyByName(data.get("name"));
		// VP: Verify that the �Suspend� link is displayed
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.SUSPEND));
		// 11. Click on �Suspend� link
		companyControl.click(CompanyInfo.SUSPEND);
		companyControl.waitForElementClickable(PageHome.BTN_CONFIRMATION_DANGER);
		/*
		 * VP: Verify that the Suspend confirmation dialog is displayed
		 */
		Assert.assertTrue(companyControl.checkConfirmationDialog(data.get("name"), "Suspend"));
		//12. Click on �Suspend� button on suspend confirmation dialog
		companyControl.selectConfirmationDialogOption("Suspend");
		/*
		 * Verify that the company is suspended
		 */
		companyControl.selectOptionByName(Companies.FILTER, Companies.option.Suspended.getName());
		Assert.assertTrue(companyControl.checkACompanyExistByName( data.get("name")));
		// Delete company
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.selectOptionByName(Companies.FILTER, Companies.option.Suspended.getName());
		companyControl.selectACompanyByName(data.get("name"));
		companyControl.doDelete(CompanyInfo.DELETE);
	}
	
	/*
	 * Verify that DTS user cannot suspend company when the �Add and manage company accounts� privilege is disabled.
	 */
	@Test
	public void PDC_12(){
		companyControl.addLog("ID : PDC_12 : Verify that DTS user cannot suspend company when the �Add and manage company accounts� privilege is disabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage company accounts� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Companies� page is displayed
			9. Navigate to �Companies� page
			10. Select a company from companies table
		 */
		// 2. Log into DTS portal as a super admin successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		companyControl.click(PageHome.LINK_USERS);
		// 4.Select a partner user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click "Edit" link
		companyControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		// 6. Disable the  �Add and manage company accounts� privilege
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE,Privileges.dtsPrivileges.Add_and_manage_company_accounts.getName());
		companyControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		companyControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		//VP. Verify that the �Companies� tab is displayed
		Assert.assertTrue(companyControl.isElementPresent(DTSHome.LINK_COMPANY));
		// 9. Navigate to �Companies� page
		companyControl.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals(true, companyControl.existsElement(Companies.getListElements()));
		// 10. Select a company from companies table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		// Verify that the �Suspend� link is not displayed in company info page
		Assert.assertEquals(companyControl.existsElement(CompanyInfo.getListElement()), true);
		Assert.assertFalse(companyControl.isElementPresent(CompanyInfo.SUSPEND));	
	}
	
	/*
	 * Verify that DTS user can delete company when the �Add and manage company accounts� privilege is enabled.
	 */
	@Test
	public void PDC_13(){
		companyControl.addLog("ID : PDC_13 : Verify that DTS user can delete  company when the �Add and manage company accounts� privilege is enabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage company accounts� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Companies� page is displayed
			9. Navigate to �Companies� page
			10. Select a company from companies table
			VP: Verify that the �Delete� link is displayed
			11. Click on �Delete� link
			VP: Verify that the delete confirmation dialog is displayed
			12. Click on �Delete� button on suspend confirmation dialog
		 */
		// 2. Log into DTS portal as a super admin successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		/*
		 * Pre-condition: Create a new company
		 */
		companyControl.click(PageHome.LINK_COMPANY);
		// Click add company link
		companyControl.click(Companies.ADD_COMPANY);
		// Create new company
		Hashtable<String,String> data = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), data);
		/*
		 * ***************************************************
		 */
		// 3. Navigate to "Users" page
		companyControl.click(PageHome.LINK_USERS);
		// 4.Select a partner user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click "Edit" link
		companyControl.click(UserMgmt.EDIT);	
		userControl.enableAllPrivileges();
		// 6. Enable the  �Add and manage company accounts� privilege
		userControl.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_company_accounts.getName());
		companyControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		companyControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		//VP. Verify that the �Companies� tab is displayed
		Assert.assertTrue(companyControl.isElementPresent(DTSHome.LINK_COMPANY));
		// 9. Navigate to �Companies� page
		companyControl.click(DTSHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals(true, companyControl.existsElement(Companies.getListElements()));
		// 10. Select a company from companies table
		companyControl.selectACompanyByName(data.get("name"));
		// VP: Verify that the �Delete� link is displayed
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.DELETE));
		// 11. Click on �Delete� link
		companyControl.click(CompanyInfo.DELETE);
		companyControl.waitForElementClickable(PageHome.BTN_CONFIRMATION_DANGER);
		// VP: Verify that the delete confirmation dialog is displayed
		Assert.assertTrue(companyControl.checkConfirmationDialog(data.get("name"), "Delete"));
		//12. Click on �Delete� button on suspend confirmation dialog
		companyControl.selectConfirmationDialogOption("Delete");
		// Verify that the deleted company is not displayed in the companies list.
		Assert.assertFalse(companyControl.checkACompanyExistByName( data.get("name")));
	}
	
	/*
	 * Verify that DTS user cannot delete company when the �Add and manage company accounts� privilege is disabled.
	 */
	@Test
	public void PDC_14(){
		companyControl.addLog("ID : PDC_14 : Verify that DTS user cannot delete company when the �Add and manage company accounts� privilege is disabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the  �Add and manage company accounts� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Companies� tab is displayed
			9. Navigate to �Companies� page
			10. Select a company from companies table
		 */
		// 2. Log into DTS portal as a super admin successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		companyControl.click(PageHome.LINK_USERS);
		// 4.Select a partner user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click "Edit" link
		companyControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		// 6. Disable the  �Add and manage company accounts� privilege
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_company_accounts.getName());
		companyControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		companyControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		//VP. Verify that the �Companies� tab is displayed
		Assert.assertTrue(companyControl.isElementPresent(PageHome.LINK_COMPANY));
		// 9. Navigate to �Companies� page
		companyControl.click(PageHome.LINK_COMPANY);
		// VP: Verify that the companies list page is displayed
		Assert.assertEquals(true, companyControl.existsElement(Companies.getListElements()));
		// 10. Select a company from companies table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		// VP : Verify that the �Delete� link is not displayed in company info page
		Assert.assertEquals(companyControl.existsElement(CompanyInfo.getListElement()), true);
		Assert.assertFalse(companyControl.isElementPresent(CompanyInfo.DELETE));
	}
	
}
