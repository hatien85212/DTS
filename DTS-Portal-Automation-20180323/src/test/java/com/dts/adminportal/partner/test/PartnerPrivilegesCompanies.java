package com.dts.adminportal.partner.test;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddBrand;
import com.dts.adminportal.model.AddCompany;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.BrandInfo;
import com.dts.adminportal.model.CompanyContact;
import com.dts.adminportal.model.CompanyInfo;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PartnerCompanyEdit;
import com.dts.adminportal.model.PartnerCompanyInfo;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.UserEdit;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.util.TestData;

public class PartnerPrivilegesCompanies extends BasePage{
	
	@Override
	protected void initData() {
	}	

//	@BeforeMethod
//	public void loginBeforeTest() {
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//	}
		
	/*
	 * Verify that the partner user can view the company and brand info when the �Edit Company Info�, �Edit brand info�  are disabled.
	 */
	@Test
	public void TCPPC_01(){
		userControl.addLog("ID : TCPPC_01 : Verify that the partner user can view the company and brand info when the �Edit Company Info�, �Edit brand info�  are disabled.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Disable the  �Edit Company Info�, �Edit brand info� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP. Verify that the �Companies� page is displayed
			9. Navigate to �Companies� page
			VP: Verify that the company page display the partner's company info correctly
			10. Click on a brand name
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4.Select a partner user from the Users table
		userControl.dtsSelectUserByEmail(PARTNER_USER);
		// 5. Click "Edit" link
		userControl.click(UserMgmt.EDIT);	
		// 6.Disable the �Edit Company Info�, �Edit brand info� privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_Company_Info.getName());
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_brand_info.getName());
		userControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		userControl.logout();
		// 8. Log into DTS portal as a partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		/*
		 * VP. Verify that the �Companies� page is displayed
		 */
		Assert.assertTrue(userControl.isElementPresent(PageHome.LINK_PARTNER_COMPANY));
		// 9. Navigate to �Companies� page
		userControl.click(PageHome.LINK_PARTNER_COMPANY);
		/*
		 * VP: Verify that the company page display the partner's company info correctly
		 */
		Assert.assertEquals(userControl.existsElement(PartnerCompanyInfo.getListXpath()), true);
		// 10. Click on a brand name
		companyControl.selectABrandByName( PARTNER_BRAND_NAME_1);
		/*
		 * Verify that the partner's company brand info display correctly
		 */
		Assert.assertEquals(userControl.existsElement(BrandInfo.getAllField()), true);
	}
	
	/*
	 *Verify that partner user can add new brand when the �Edit Company Info� privilege is enabled.
	 */
	@Test
	public void TCPPC_02(){
		userControl.addLog("ID : TCPPC_02 : Verify that partner user can add new brand when the �Edit Company Info� privilege is enabled.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Enable the  �Edit Company Info� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP. Verify that the �Companies� page is displayed
			9. Navigate to �Companies� page
			VP: Verify that the company info page displays  and the �Edit�, �Change� and �Add� link are also displayed.
			10. Click �Add� link
			VP: Verify that the �Edit Brand Info� page is displayed.
			11. Fill valid value into all required fields
			12. Click �Save� link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4.Select a partner user from the Users table
		userControl.dtsSelectUserByEmail(PARTNER_USER);
		// 5. Click "Edit" link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the  �Edit Company Info� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_Company_Info.getName());
		userControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		userControl.logout();
		// 8. Log into DTS portal as a partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 9. Navigate to �Companies� page
		userControl.click(PageHome.LINK_PARTNER_COMPANY);
		// VP: Verify that the company page display the partner's company info correctly
		userControl.isElementPresent(PartnerCompanyInfo.ADD_BRAND);
		// 10.Click �Add� link
		userControl.click(PartnerCompanyInfo.ADD_BRAND);
		// VP: Verify that the �Edit Brand Info� page is displayed.
		Assert.assertEquals(userControl.existsElement(AddBrand.getHash()), true);
		// 11. Fill valid value into all required fields
		// 12. Click �Save� link
		Hashtable<String,String> data = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), data);
		// Verify that the 063P Brand Info page is displayed
		Assert.assertEquals(userControl.existsElement(BrandInfo.getBrandInfo()), true);
	}
	
	/*
	 * Verify that partner user cannot edit company, add new brand and change primary contact when the �Edit Company Info� privilege is disabled.
	 */
	@Test
	public void TCPPC_03() {
		userControl.addLog("ID : TCPPC_03 : Verify that partner user cannot edit company, add new brand and change primary contact when the �Edit Company Info� privilege is disabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Disable the  �Edit Company Info� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP. Verify that the �Companies� page is displayed
			9. Navigate to �Companies� page
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4.Select a partner user from the Users table
		userControl.selectUserInfoByEmail(PARTNER_USER);
		// 5. Click "Edit" link
		userControl.click(UserMgmt.EDIT);
		// 6. Disable the  �Edit Company Info� privilege
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_Company_Info.getName());
		userControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		userControl.logout();
		// 8. Log into DTS portal as a partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 9. Navigate to �Companies� page
		userControl.click(PageHome.LINK_PARTNER_COMPANY);
		// partner user cannot edit company, add new brand and change primary contact
		Assert.assertFalse(userControl.isElementPresent(PartnerCompanyInfo.EDIT));
		Assert.assertFalse(userControl.isElementPresent(PartnerCompanyInfo.ADD_BRAND));
		Assert.assertFalse(userControl.isElementPresent(PartnerCompanyInfo.CHANGE_PARTNER_CONTACT));
	}
	
	/*
	 * Verify that partner user can  edit company info when the �Edit Company Info� privilege is enabled
	 */
	@Test
	public void TCPPC_04() {
		userControl.addLog("ID : TCPPC_04 : Verify that partner user can  edit company info when the �Edit Company Info� privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Enable the  �Edit Company Info� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP. Verify that the �Companies� page is displayed
			9. Navigate to �Companies� page
			VP: Verify that the company info page displays  and the �Edit�, �Change� and �Add� link are also displayed.
			10. Click �Edit� link
			VP: Verify that the Company edit page is displayed.
			11. Change some company info
			12. Click �Save� link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a partner user from the Users table
		userControl.selectUserInfoByEmail(PARTNER_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the  �Edit Company Info� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_Company_Info.getName());
		userControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		userControl.logout();
		// 8. Log into DTS portal as above partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		/*
		 * VP. Verify that the �Companies� page is displayed
		 */
		Assert.assertTrue(userControl.isElementPresent(PageHome.LINK_PARTNER_COMPANY));
		// 9. Navigate to �Companies� page
		userControl.click(PageHome.LINK_PARTNER_COMPANY);
		/*
		 *  VP: Verify that the company info page displays  and the �Edit�, �Change� and �Add� link are also displayed
		 */
		Assert.assertTrue(userControl.isElementPresent(CompanyInfo.EDIT));
		Assert.assertTrue(userControl.isElementPresent(CompanyInfo.CHANGE_PARTNER_CONTACT));
		Assert.assertTrue(userControl.isElementPresent(CompanyInfo.ADD_BRAND));
		// 10. Click �Edit� link
		userControl.click(PartnerCompanyInfo.EDIT);
		/*
		 * VP: Verify that the Company edit page is displayed
		 */
		Assert.assertEquals(userControl.existsElement(PartnerCompanyEdit.getListXpath()), true);
		// 11. Change some company info
		String newAdress = "New Address " + RandomStringUtils.randomNumeric(10);
		userControl.editData(AddCompany.ADDRESS1, newAdress);
		// 12. Click �Save� link
		userControl.click(AddCompany.SAVE);
		/*
		 * Verify that company info page is displayed with new info
		 */
		Assert.assertEquals(userControl.getTextByXpath(CompanyInfo.ADDRESS1), newAdress);
	}
	
	/*
	 * Verify that partner user can change the primary contact info when the �Edit Company Info� privilege is enabled
	 */
	@Test
	public void TCPPC_05(){
		
		userControl.addLog("ID : TCPPC_05 :Verify that partner user can change the primary contact info when the �Edit Company Info� privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Enable the  �Edit Company Info� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP. Verify that the �Companies� page is displayed
			9. Navigate to �Companies� page
			VP: Verify that the company info page displays  and the �Edit�, �Change� and �Add� link are also displayed.
			10. Click �Change� link
			VP: Verify that the Company edit page is displayed.
			11. Change the primary contact info to another one
			12. Click �Save� link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a partner user from the Users table
		userControl.selectUserInfoByEmail(PARTNER_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the  �Edit Company Info� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_Company_Info.getName());
		userControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		userControl.logout();
		// 8. Log into DTS portal as above partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		/*
		 * VP: Verify that the �Companies� page is displayed
		 */
		Assert.assertTrue(userControl.isElementPresent(PageHome.LINK_PARTNER_COMPANY));
		// 9. Navigate to �Companies� page
		userControl.click(PageHome.LINK_PARTNER_COMPANY);
		/*
		 * VP: Verify that the company info page displays  and the �Edit�, �Change� and �Add� link are also displayed
		 */
		Assert.assertEquals(userControl.existsElement(PartnerCompanyInfo.getListXpath()), true);
		Assert.assertTrue(userControl.isElementPresent(PartnerCompanyInfo.EDIT));
		Assert.assertTrue(userControl.isElementPresent(PartnerCompanyInfo.CHANGE_PARTNER_CONTACT));
		Assert.assertTrue(userControl.isElementPresent(PartnerCompanyInfo.ADD_BRAND));
		// 10. Click �Change� link
		userControl.click(PartnerCompanyInfo.CHANGE_PARTNER_CONTACT);
		/*
		 * VP: Verify that the Company edit page is displayed
		 */
		Assert.assertEquals(userControl.existsElement(CompanyContact.getElementInfo()), true);
		// 11. Change the primary contact info to another one
		userControl.selectPartnerUserByEmail(PARTNER_USER);
		// Get primary contact name
		String ContactName = userControl.getTextByXpath(CompanyContact.NEW_CONTACT);
		// 12. Click �Save� link
		userControl.click(CompanyContact.SAVE);
		/*
		 * Verify that company info page is displayed with new primary contact info
		 */
		Assert.assertTrue(ContactName.contains(userControl.getTextByXpath(CompanyInfo.CONTACT_NAME)));
	}
	
	/*
	 * Verify that partner user can edit the company brand info when the �Edit brand info� privilege is enabled.
	 */
	@Test
	public void TCPPC_06(){
		userControl.addLog("ID : TCPPC_06 : Verify that partner user can edit the company brand info when the �Edit brand info� privilege is enabled.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Enable the  �Edit brand info� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP. Verify that the �Companies� page is displayed
			9. Navigate to �Companies� page
			VP: Verify that the company info page is displayed
			10. Click on a brand name link
			VP: Verify that the �Edit� link is displayed in the Brand info page
			11. Click on �Edit� link
			12. Change some brand info in Brand edit page
			13. Click �Save� link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4.Select a partner user from the Users table
		userControl.selectUserInfoByEmail(PARTNER_USER);
		// 5. Click "Edit" link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the �Edit brand info� privilege
		userControl.enablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.privileges.Edit_brand_info.getName());
		userControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		userControl.logout();
		// 8. Log into DTS portal as a partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 9. Navigate to �Companies� page
		userControl.click(PageHome.LINK_PARTNER_COMPANY);
		// VP: Verify that the company page display the partner's company info correctly
		Assert.assertEquals(userControl.getTextByXpath(CompanyInfo.NAME), PARTNER_COMPANY_NAME);
		// 10. Click on a brand name
		companyControl.selectABrandByName( PARTNER_BRAND_NAME_1);
		/*
		 * VP: Verify that the �Edit� link is displayed in the Brand info page
		 */
		Assert.assertTrue(userControl.isElementPresent(BrandInfo.EDIT_BRAND));
		// 11. Click on �Edit� link
		userControl.click(BrandInfo.EDIT_BRAND);
		// 12. Change some brand info in Brand edit page
		String newTagLine = RandomStringUtils.randomNumeric(10);
		userControl.editData(AddBrand.BRAND_TAG_LINE, newTagLine);
		// 13. Click �Save� link
		userControl.click(AddBrand.SAVE);
		/*
		 * Verify that the brand info page is displayed with new value
		 */
		Assert.assertEquals(userControl.getTextByXpath(BrandInfo.BRAND_TAG_LINE),newTagLine);
	}
	
	/*
	 * Verify that partner user can delete the company brand when the �Edit brand info� privilege is enabled.
	 */
	@Test
	public void TCPPC_07(){
		
		userControl.addLog("ID : TCPPC_07 : Verify that partner user can delete the company brand when the �Edit brand info� privilege is enabled.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Enable the  �Edit brand info� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP. Verify that the �Companies� page is displayed
			9. Navigate to �Companies� page
			VP: Verify that the company info page is displayed
			10. Click on a brand name link
			VP: Verify that the �Delete� link is displayed in the Brand info page
			11. Click on �Delete� link
			12. Click �Delete� on the delete confirmation dialog
		 */
		/*
		 * PreCondition: Create new brand
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to companies page
		userControl.click(PageHome.LINK_COMPANY);
		// Select a company on table
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		// Click Add brand link
		userControl.click(CompanyInfo.ADD_BRAND);
		// Create new brand
		Hashtable<String, String> data = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), data);
		/*
		 * ***********************************************************
		 */
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4.Select a partner user from the Users table
		userControl.selectUserInfoByEmail(PARTNER_USER);
		// 5. Click "Edit" link
		userControl.click(UserMgmt.EDIT);
		// 6. Enable the �Edit brand info� privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_brand_info.getName());
		userControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		userControl.logout();
		// 8. Log into DTS portal as a partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 9. Navigate to �Companies� page
		userControl.click(PageHome.LINK_PARTNER_COMPANY);
		// VP: Verify that the company page display the partner's company info correctly
		Assert.assertEquals(true, userControl.existsElement(PartnerCompanyInfo.getListXpath()));
		// 10. Click on a brand name
		companyControl.selectABrandByName( data.get("name"));
		// VP: Verify that the �Delete� link is displayed in the Brand info page
		Assert.assertTrue(userControl.isElementPresent(BrandInfo.DELETE_LINK));
		// 11. Click on �Delete� link
		// 12. Click �Delete� on the delete confirmation dialog
		userControl.doDelete(BrandInfo.DELETE_LINK);
		/*
		 * The portal redirects to 061 Company Info page and the company's brand
		 * is deleted successfully
		 */
		Assert.assertFalse(companyControl.checkBrandExist( data.get("name")));
	}
	
	/*
	 *Verify that partner user cannot edit or delete the company brand when the �Edit brand info� privilege is disabled.
	 */
	@Test
	public void TCPPC_08(){
		userControl.addLog("ID : TCPPC_08 :Verify that partner user cannot edit or delete the company brand when the "
				+ "�Edit brand info� privilege is disabled.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Disable the  �Edit brand info� privilege
			7. Log out DTS portal
			8. Log into DTS portal as above partner user successfully
			VP. Verify that the �Companies� page is displayed
			9. Navigate to �Companies� page
			VP: Verify that the company info page is displayed
			10. Click on a brand name link
		 */
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4.Select a partner user from the Users table
		userControl.dtsSelectUserByEmail(PARTNER_USER);
		// 5. Click "Edit" link
		userControl.click(UserMgmt.EDIT);
		// 6. Disable the �Edit brand info� privilege
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_brand_info.getName());
		userControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		userControl.logout();
		// 8. Log into DTS portal as a partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 9. Navigate to �Companies� page
		userControl.click(PageHome.LINK_PARTNER_COMPANY);
		// VP: Verify that the company page display the partner's company info correctly
		Assert.assertEquals(true, userControl.existsElement(PartnerCompanyInfo.getListXpath()));
		// 10. Click on a brand name
		companyControl.selectABrand(CompanyInfo.BRAND_LIST);
		// partner user cannot edit or delete the company brand
		Assert.assertFalse(userControl.isElementPresent(BrandInfo.EDIT_BRAND));
		Assert.assertFalse(userControl.isElementPresent(BrandInfo.DELETE_LINK));
	}
	
	/*
	 * Verify that new brand could be added successfully when the �Edit Company Info� privilege is enabled and �Edit brand info� privilege is disabled.
	 */
	@Test
	public void TCPPC_09() {
		userControl.addLog("ID : TCPPC_09 : Verify that new brand could be added successfully when the �Edit Company Info� privilege is enabled and �Edit brand info� privilege is disabled.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Disable the  �Edit brand info� Privilege
			7. Enable �Edit Company Info� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above partner user successfully
			VP. Verify that the �Companies� page is displayed
			10. Navigate to �Companies� page
			VP: Verify that the company info page is displayed
			11. Click �Add� link
			12. Fill valid value into all required fields
			13. Click �Save� link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a partner user from the Users table
		userControl.dtsSelectUserByEmail(PARTNER_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Disable the  �Edit brand info� Privilege
		userControl.disablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.privileges.Edit_brand_info.getName());
		// 7. Enable the  �Edit Company Info� privilege
		userControl.enablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.privileges.Edit_Company_Info.getName());
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		/*
		 * VP. Verify that the �Companies� page is displayed
		 */
		Assert.assertTrue(userControl.isElementPresent(PageHome.LINK_PARTNER_COMPANY));
		// 10. Navigate to �Companies� page
		userControl.click(PageHome.LINK_PARTNER_COMPANY);
		/*
		 *  VP: Verify that the company info page displays
		 */
		Assert.assertEquals(userControl.existsElement(PartnerCompanyInfo.getListXpath()), true);
		// 11. Click �Add� link
		userControl.click(PartnerCompanyInfo.ADD_BRAND);
		// 12. Fill valid value into all required fields
		// 13. Click �Save� link
		Hashtable<String,String> data = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), data);
		/*
		 * Verify that new brand is added successfully
		 */
		userControl.click(PageHome.LINK_PARTNER_COMPANY);
		Assert.assertTrue(companyControl.checkBrandExist(data.get("name")));
	}
	/*
	 * Verify on DTS user Company Edit page new option Licensee Device show up.
	 */
	@Test
	public void TCPPC_10() {
		userControl.addLog("ID : TCPPC_10 : Verify on DTS user Company Edit page new option Licensee Device show up.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a partner user from the Users table
			5. Click �Edit� link
			6. Disable the  �Edit brand info� Privilege
			7. Enable �Edit Company Info� privilege
			8. Log out DTS portal
			9. Log into DTS portal as above partner user successfully
			VP. Verify that the �Companies� page is displayed
			10. Navigate to �Companies� page
			VP: Verify that the company info page is displayed
			11. Click �Add� link
			12. Fill valid value into all required fields
			13. Click �Save� link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a partner user from the Users table
		userControl.dtsSelectUserByEmail(PARTNER_USER);
		// 5. Click �Edit� link
		userControl.click(UserMgmt.EDIT);
		// 6. Disable the  �Edit brand info� Privilege
		userControl.disablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.privileges.Edit_brand_info.getName());
		// 7. Enable the  �Edit Company Info� privilege
		userControl.enablePrivilege(UserEdit.SITE_PRIVILEGES_TABLE, Privileges.privileges.Edit_Company_Info.getName());
		userControl.click(AddUser.SAVE);
		// 8. Log out DTS portal
		userControl.logout();
		// 9. Log into DTS portal as above partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		/*
		 * VP. Verify that the �Companies� page is displayed
		 */
		Assert.assertTrue(userControl.isElementPresent(PageHome.LINK_PARTNER_COMPANY));
		// 10. Navigate to �Companies� page
		userControl.click(PageHome.LINK_PARTNER_COMPANY);
		/*
		 *  VP: Verify that the company info page displays
		 */
		Assert.assertEquals(userControl.existsElement(PartnerCompanyInfo.getListXpath()), true);
		// 11. Click �Add� link
		userControl.click(PartnerCompanyInfo.ADD_BRAND);
		// 12. Fill valid value into all required fields
		// 13. Click �Save� link
		Hashtable<String,String> data = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), data);
		/*
		 * Verify that new brand is added successfully
		 */
		userControl.click(PageHome.LINK_PARTNER_COMPANY);
		Assert.assertTrue(companyControl.checkBrandExist(data.get("name")));
	}	
}
