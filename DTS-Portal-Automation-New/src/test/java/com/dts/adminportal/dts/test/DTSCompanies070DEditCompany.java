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
import com.dts.adminportal.model.Companies;
import com.dts.adminportal.model.CompanyContact;
import com.dts.adminportal.model.CompanyEdit;
import com.dts.adminportal.model.CompanyInfo;
import com.dts.adminportal.model.DeviceEdit;
import com.dts.adminportal.model.DeviceInfo;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PageLogin;
import com.dts.adminportal.model.PartnerCompanyEdit;
import com.dts.adminportal.model.PartnerCompanyInfo;
import com.dts.adminportal.model.PartnerUserMgmt;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.XPartnerShipPackage;
import com.dts.adminportal.model.XLicenseProductPackage;
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
//		companyControl.addErrorLog("PDPP-726: 070 Edit Company: The company's corporate address is not implemented as amazon's friendly address form");
//		Assert.assertTrue(false);
		Assert.assertTrue(companyControl.getTextByXpath(PartnerCompanyEdit.INSADD1).contains(PartnerCompanyEdit.INSADD1_TEXT));
		Assert.assertTrue(companyControl.getTextByXpath(PartnerCompanyEdit.INSADD2).contains(PartnerCompanyEdit.INSADD2_TEXT));
		Assert.assertTrue(companyControl.getTextByXpath(PartnerCompanyEdit.INSADD3).contains(PartnerCompanyEdit.INSADD3_TEXT));
		
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
			5. Click �Edit� link
			6. Add more Headphone:X Partnerships
			VP: The Headphone:X Partnerships is able to add more.
			7. Click �Delete� link
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
		companyControl.selectOptionByName(AddCompany.PARTNERSHIPS, AddCompany.Partnership_List.Devices_Supporting_Headphone.getName());
		companyControl.click(AddCompany.ADD);
		// Get all Partnerships
		ArrayList<XPartnerShipPackage> partnershipPackages = companyControl.getPartnershipPackage(AddCompany.PARTNERSHIPS_TABLE);
		/*
		 * VP: The Headphone:X Partnerships is able to add more
		 */
		Assert.assertEquals(partnershipPackages.get(partnershipPackages.size() - 1).type, AddCompany.Partnership_List.Devices_Supporting_Headphone.getName());
		// 7. Click �Delete� link
		companyControl.click(partnershipPackages.get(partnershipPackages.size() - 1).deletelink);
		/*
		 * VP: The Headphone:X Partnerships is able to delete
		 */
		Assert.assertTrue(!companyControl.getTextByXpath(AddCompany.PARTNERSHIPS_TABLE).contains(AddCompany.Partnership_List.Devices_Supporting_Headphone.getName()));
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
			7. Click �Save� link.
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Click "Add Company" link
		companyControl.click(Companies.ADD_COMPANY);
		// 5. Fill valid value into all required fields
		// 6. Do not add partnership type
		// 7. Click �Save� link
		Hashtable<String,String> data = TestData.validCompany();
		data.remove("partnerships");
		companyControl.addCompany(AddCompany.getHash(), data);
		/*
		 * There is an error message which notifying that at least one partnership type is required when adding new company
		 */
		Assert.assertTrue(companyControl.checkMessageDisplay(PageLogin.errMessage.At_least_1_partnership_type.getName()));
	}
	/*
	 * Verify on DTS user Company Edit page new option Licensee Device show up
	 */
	@Test
	public void TC070DBEC_06() {
		companyControl.addLog("ID TC070DBEC_06 : Verify on DTS user Company Edit page new option Licensee Device show up");
		/*
			1. Log into DTS portal as DTS user
			2. Navigate to "Company'' page
			3. Click to '' Company'' tab
			4. Click "Add Company" link
			VP: The Licensee Devive box show up
		*/
		// 1. Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Company'' page
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Click "Add Company" link
		companyControl.click(Companies.ADD_COMPANY);
		// VP: The Licensee Devive box show up
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.LICENSEE_DISABLE));
	}
	/*
	 * Verify canbe switch beween button "Enable" and "Disable" of Licensee Device on DTS user company Edit page
	 */
	@Test
	public void TC070DBEC_07() {
		companyControl.addLog("ID TC070DBEC_07 : Verify canbe switch beween button Enable and Disable of Licensee Device on DTS user company Edit page");
		/*
			1. Log into DTS portal as DTS user
			2. Navigate to "Company'' page
			3. Click to '' Company'' tab
			4. Click "Add Company" link
			5. Navigate the License Device box
			VP: Canbe switch between "Enable" and "Disable" button
		*/
		// 1. Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Company'' page
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Click "Add Company" link
		companyControl.click(Companies.ADD_COMPANY);
		// VP: Canbe switch between "Enable" and "Disable" button
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.LICENSEE_DISABLE));
		companyControl.click(CompanyInfo.LICENSEE_DISABLE);
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.LICENSEE_ENABLE));
	}
	/*
	 * Verify when Licensee Device have  "Licensed Product" and ""Licensed Headphone"
	 */
	@Test
	public void TC070DBEC_08() {
		companyControl.addLog("ID TC070DBEC_08 : Verify when Licensee Device have Licensed Product and Licensed Headphone");
		/*
			1. Log into DTS portal as DTS user
			2. Navigate to "Company'' page
			3. Click to '' Company'' tab
			4. Click "Add Company" link
			5. Click to "Enable" button Licensee Device
			6. Navigate the License Device box
			VP: Canbe switch between "Enable" and "Disable" button
		*/
		// 1. Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Company'' page
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Click "Add Company" link
		companyControl.click(Companies.ADD_COMPANY);
		// 5. Click to "Enable" button Licensee Device
		companyControl.click(CompanyInfo.LICENSEE_DISABLE);
		// 6. Navigate the License Device box
		// VP:  Licensee Device have  "Licensed Product" and ""Licensed Headphone"
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.LICENSEE_PRODUCT_LABEL));
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.LICENSEE_HEADPHONE_LABEL));
	}
	/*
	 * Verify Licened Product combo box include those option: - Select -, DTS Audio Processing, DTS:X Premium, DTS:X Ultra
	 */
	@Test
	public void TC070DBEC_09() {
		companyControl.addLog("ID TC070DBEC_09 : Verify Licened Product combo box include those option: - Select -, DTS Audio Processing, DTS:X Premium, DTS:X Ultra");
		/*
			1. Log into DTS portal as DTS user
			2. Navigate to "Company'' page
			3. Click to '' Company'' tab
			4. Click "Add Company" link
			5. Click to "Enable" button Licensee Device
			6. Navigate the Licened Product box
			VP: Licened Product combo box include those option: - Select -, DTS Audio Processing, DTS:X Premium, DTS:X Ultra
		*/
		// 1. Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Company'' page
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Click "Add Company" link
		companyControl.click(Companies.ADD_COMPANY);
		// 5. Click to "Enable" button Licensee Device
		companyControl.click(CompanyInfo.LICENSEE_DISABLE);
		// 6. Navigate the License Device box
		// VP: Licened Product combo box include those option: - Select -, DTS Audio Processing, DTS:X Premium, DTS:X Ultra
		Assert.assertTrue(!companyControl.getTextByXpath(AddCompany.LISENSEE_PRODUCT_TABLE).contains(AddCompany.Licensee_Product_List.HPX_LOW.getName()));
		Assert.assertTrue(!companyControl.getTextByXpath(AddCompany.LISENSEE_PRODUCT_TABLE).contains(AddCompany.Licensee_Product_List.HPX_HIGH.getName()));
		Assert.assertTrue(!companyControl.getTextByXpath(AddCompany.LISENSEE_PRODUCT_TABLE).contains(AddCompany.Licensee_Product_List.HPX_MEDIUM.getName()));
	}
	/*
	 * Verify DTS user canbe add product type on Licensed Product
	 */
	@Test
	public void TC070DBEC_10() {
		companyControl.addLog("ID TC070DBEC_10 : Verify DTS user canbe add product type on Licensed Product");
		/*
			1. Log into DTS portal as DTS user
			2. Navigate to "Company'' page
			3. Click to '' Company'' tab
			4. Click "Add Company" link
			5. Click to "Enable" button Licensee Device
			6. Choose Product type: DTS Audio Processing &HPX Low
			7. Click to "Add" button
			VP: The product type should be add to the list.
		*/
		// 1. Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Company'' page
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Click "Add Company" link
		companyControl.click(Companies.ADD_COMPANY);
		// 5. Click to "Enable" button Licensee Device
		companyControl.click(CompanyInfo.LICENSEE_DISABLE);
		// 6. Choose Product type: DTS Audio Processing &HPX Low
		companyControl.selectOptionByName(AddCompany.LICENSEE_PRODUCT, AddCompany.Licensee_Product_List.HPX_LOW.getName());
		// 7. Click to "Add" button
		companyControl.click(AddCompany.ADD);
		// VP: The product type should be add to the list.
		Assert.assertTrue(!companyControl.getTextByXpath(AddCompany.LISENSEE_PRODUCT_TABLE).contains(AddCompany.Licensee_Product_List.HPX_LOW.getName()));
	}
	/*
	 * Verify DTS user canbe delete with Licensed Product on page create company
	 */
	@Test
	public void TC070DBEC_11() {
		companyControl.addLog("ID TC070DBEC_11 : Verify DTS user canbe delete with Licensed Product on page create company");
		/*
			1. Log into DTS portal as DTS user
			2. Navigate to "Company'' page
			3. Click to '' Company'' tab
			4. Click "Add Company" link
			5. Click to "Enable" button Licensee Device
			6. Choose Product type: DTS Audio Processing &HPX Low
			7. Click to "Add" button
			8. Click to "Delete" button
			VP: The ''DTS Audio Processing &HPX Low'' already deleted
		*/
		// 1. Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Company'' page
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Click "Add Company" link
		companyControl.click(Companies.ADD_COMPANY);
		// 5. Click to "Enable" button Licensee Device
		companyControl.click(CompanyInfo.LICENSEE_DISABLE);
		// 6. Choose Product type: DTS Audio Processing &HPX Low
		companyControl.selectOptionByName(AddCompany.LICENSEE_PRODUCT, AddCompany.Licensee_Product_List.HPX_LOW.getName());
		// 7. Click to "Add" button
		companyControl.click(AddCompany.ADD_LICENSEE_PRODUCT);
		// Get all LicenseeProductPackages
		ArrayList<XLicenseProductPackage> LicenseeProductPackages = companyControl.getLicenseProductPackage(AddCompany.LISENSEE_PRODUCT_TABLE);
		// 8. Click to "Delete" button
		companyControl.click(LicenseeProductPackages.get(LicenseeProductPackages.size() - 1).deletelink);
		// VP: The ''DTS Audio Processing &HPX Low'' already deleted
		Assert.assertTrue(!companyControl.getTextByXpath(AddCompany.LISENSEE_PRODUCT_TABLE).contains(AddCompany.Licensee_Product_List.HPX_LOW.getName()));
	}
	
	/*
	 * Verify that the Licened Product is required at least one product type when adding new companies.
	 */
	@Test
	public void TC070DBEC_12() {
		companyControl.addLog("ID TC070DBEC_12 : Verify that the Licened Product is required at least one product type when adding new companies.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Click "Add Company" link
			5. Click to "Enable" button Licensee Device
			6. Navigate the ''Licensed Product'' combo box
			8. Click to "Save" button
			VP: the the ''Licensed Product'' combo box show up message "At least 1 product type is required for a company" 
		*/
		// 1. Navigate to DTS portal
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Click "Add Company" link
		companyControl.click(Companies.ADD_COMPANY);
		// 5. Click to "Enable" button Licensee Device
		companyControl.click(CompanyInfo.LICENSEE_DISABLE);
		// 6. Navigate the ''Licensed Product'' combo box
		// 7. Click �Save� link
		// Hashtable<String,String> data = TestData.licenseCompany();
		companyControl.click(AddCompany.SAVE);
		/*
		 * There is an error message which notifying that at least one partnership type is required when adding new company
		 */
		Assert.assertTrue(companyControl.checkMessageDisplay(PageLogin.errMessage.At_least_1_license_production_type.getName()));
}
	/*
	 * Verify canbe adding brand and product name on "Licensed Headphone''
	 */
	@Test
	public void TC070DBEC_13() {
		companyControl.addLog("ID TC070DBEC_13 : Canbe add the brand and product on text box");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Click "Add Company" link
			5. Click to "Enable" button Licensee Device
			6. Navigate the ''Licensed Product'' combo box
			VP: Canbe add the brand and product on text box
		*/
		// 1. Navigate to DTS portal
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Create a published product
		companyControl.click(PageHome.linkAccessories);
		companyControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndPublish(dataProduct);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Click "Add Company" link
		companyControl.click(Companies.ADD_COMPANY);
		// 5. Click to "Enable" button Licensee Device
		companyControl.click(CompanyInfo.LICENSEE_DISABLE);
		// 6. Navigate the ''Licensed Product'' combo box
		// VP: Canbe add the brand and product on text box
		companyControl.inputInboxHP(CompanyInfo.LICENSE_HEADPHONE, dataProduct.get("name"));
}
	/*
	 * Verify when choose "Disable" Licensee Device donot have those option on Site Privileges: Add and manage Apps & Devices,Publish and suspend apps and devices, Add and manage promotions on ''contact person'' page
	 */
	@Test
	public void TC070DBEC_14() {
		companyControl.addLog("ID TC070DBEC_14 : Verify when choose Disable Licensee Device donot have those option on Site Privileges: Add and manage Apps & Devices,Publish and suspend apps and devices, Add and manage promotions on ''contact person'' page");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Click "Add Company" link
			5. Click to "Disable" button Licensee Device
			6. Fill valid value into all required fields
			7. Navigate the ''Brand Privileges''
			VP: Privileges collumn donot have those optiom: Add and manage Apps & Devices,Publish and suspend apps and devices, Add and manage promotions 
		*/
		// 1. Navigate to DTS portal
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Click "Add Company" link
		companyControl.click(Companies.ADD_COMPANY);
		// 5. Click to "Disable" button Licensee Device
		// 6. Fill valid value into all required fields
		// Create company
		Hashtable<String,String> data = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), data);
		// 7. Navigate the ''Brand Privileges''
		// VP: Privileges collumn donot have those optiom: Add and manage Apps & Devices,Publish and suspend apps and devices, Add and manage promotions 
		Assert.assertFalse(companyControl.isElementPresent(Privileges.license_privileges.Add_and_manage_Apps_Devices.getName()));
		Assert.assertFalse(companyControl.isElementPresent(Privileges.license_privileges.Publish_and_suspend_apps_and_devices.getName()));
		Assert.assertFalse(companyControl.isElementPresent(Privileges.license_privileges.Add_and_manage_promotions.getName()));
}
	/*
	 * Verify when choose "Enable" Licensee Device donot have those option on Site Privileges: Add and manage Apps & Devices,Publish and suspend apps and devices, Add and manage promotions
	 */
	@Test
	public void TC070DBEC_15() {
		companyControl.addLog("ID TC070DBEC_15 : Verify when choose Disable Licensee Device donot have those option on Site Privileges: Add and manage Apps & Devices,Publish and suspend apps and devices, Add and manage promotions on ''contact person'' page");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Click "Add Company" link
			5. Click to "Enable" button Licensee Device
			6. Fill valid value into all required fields
			7. Navigate the ''Brand Privileges''
			VP: VP: Privileges collumn have those option: Add and manage Apps & Devices,Publish and suspend apps and devices, Add and manage promotions
		*/
		// 1. Navigate to DTS portal
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Click "Add Company" link
		companyControl.click(Companies.ADD_COMPANY);
		// 5. Click to "Enable" button Licensee Device
		companyControl.click(CompanyInfo.LICENSEE_DISABLE);
		// 6. Fill valid value into all required fields
		// Create company
		Hashtable<String,String> data = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), data);
		companyControl.selectOptionByName(AddCompany.LICENSEE_PRODUCT, AddCompany.Licensee_Product_List.HPX_LOW.getName());
		companyControl.click(AddCompany.ADD_LICENSEE_PRODUCT);
		companyControl.click(AddCompany.SAVE);
		// 7. Navigate the ''Brand Privileges''
		// VP: Privileges collumn donot have those optiom: Add and manage Apps & Devices,Publish and suspend apps and devices, Add and manage promotions 
		String PrivilegeTable = userControl.getTextByXpath(PartnerUserMgmt.SITE_PRIVILEGES_TABLE);
		Assert.assertTrue(PrivilegeTable.contains(Privileges.license_privileges.Add_and_manage_Apps_Devices.getName()) && PrivilegeTable.contains("All brands"));
		Assert.assertTrue(PrivilegeTable.contains(Privileges.license_privileges.Publish_and_suspend_apps_and_devices.getName()) && PrivilegeTable.contains("All brands"));
		Assert.assertTrue(PrivilegeTable.contains(Privileges.license_privileges.Add_and_manage_promotions.getName()));
}
	/*
	 * Verify on Brand Privileges DTS user contact Person page have new option : Add and manage Apps & Devices,Publish and suspend apps and devices, Add and manage promotions canbe checkbox and choose the brand
	 */
	@Test
	public void TC070DBEC_16() {
		companyControl.addLog("ID TC070DBEC_16 : Verify on Brand Privileges DTS user contact Person page have new option : Add and manage Apps & Devices,Publish and suspend apps and devices, Add and manage promotions canbe checkbox and choose the brand");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Click "Add Company" link
			5. Click to "Enable" button Licensee Device
			6. Fill valid value into all required fields
			7. Navigate the ''Brand Privileges''
			VP: New option Add and manage Apps 
			& Devices,Publish and suspend apps and devices, Add and manage promotions can be:
			- Check and uncheck on site privileges collumn
			- Choose the Brand for company
			- Check and uncheck for Notification
		*/
		// 1. Navigate to DTS portal
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Click "Add Company" link
		companyControl.click(Companies.ADD_COMPANY);
		// 5. Click to "Enable" button Licensee Device
		companyControl.click(CompanyInfo.LICENSEE_DISABLE);
		// 6. Fill valid value into all required fields
		// Create company
		Hashtable<String,String> data = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), data);
		companyControl.selectOptionByName(AddCompany.LICENSEE_PRODUCT, AddCompany.Licensee_Product_List.HPX_LOW.getName());
		companyControl.click(AddCompany.ADD_LICENSEE_PRODUCT);
		companyControl.click(AddCompany.SAVE);
		// 7. Navigate the ''Brand Privileges''
		// VP: New option Add and manage Apps 
		// & Devices,Publish and suspend apps and devices, Add and manage promotions can be:
		//	- Check and uncheck on site privileges collumn
		//	- Choose the Brand for company
		//	- Check and uncheck for Notification
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.license_privileges.Add_and_manage_Apps_Devices.getName());
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.license_privileges.Publish_and_suspend_apps_and_devices.getName());
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.license_privileges.Add_and_manage_promotions.getName());
}
	
	/*
	 * Verify canbe edit old company become license company
	 */
	@Test
	public void TC070DAEC_17() {
		companyControl.addLog("ID TC070DAEC_17 : Verify canbe edit old company become license company");
		/*
		  	Pre-condition: User has full site privileges rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Choose one company donot have license.
			5. Click �Edit� link
			6. Click to "Enable" button Licensee Device
			7. Fill valid value into all required fields
			8. Click to "Save" button
			VP: the company came to company infor page Have Licensee Device information 
		*/
		/*
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
		// Click to "Enable" button Licensee Device
		companyControl.click(CompanyInfo.LICENSEE_DISABLE);
		// 6. Add more Headphone:X Partnerships
		companyControl.selectOptionByName(AddCompany.LICENSEE_PRODUCT, AddCompany.Licensee_Product_List.HPX_LOW.getName());
		companyControl.click(AddCompany.ADD_LICENSEE_PRODUCT);
		// 8. Click to "Save" button
		companyControl.click(AddCompany.SAVE);
		// VP: the company came to company infor page Have Licensee Device information 
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.LICENSE_DEVICE));
		
	}
	/*
	 * Verify canbe add license user for old company on company info edit page
	 */
	@Test
	public void TC070DAEC_18() {
		companyControl.addLog("ID TC070DAEC_18 : Verify canbe add license user for old company on company info edit page");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Choose one company donot have license.
			5. Click �Edit� link
			6. Click to "Enable" button Licensee Device
			7. Fill valid value into all required fields
			8. Click to "Save" button
			9. Navigate the Primary Partner Contact 
			VP: The license user information show up.
			- The ''Add User'' button change to '' Change'' button
		*/
		/*
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// Click Add Company link
		companyControl.click(Companies.ADD_COMPANY);
		// Create company
		Hashtable<String,String> data = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), data);
		// Create User
		Hashtable<String,String> dataUser = TestData.partnerUser();
		dataUser.remove("company");
		dataUser.remove("email");
		dataUser.put("email", PARTNER_DTS_EMAIL);
		userControl.addUser(AddUser.getPartnerUser(), dataUser);
		// Create new brand
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), dataBrand);
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
		// Click to "Enable" button Licensee Device
		companyControl.click(CompanyInfo.LICENSEE_DISABLE);
		// 6. Add more Headphone:X Partnerships
		companyControl.selectOptionByName(AddCompany.LICENSEE_PRODUCT, AddCompany.Licensee_Product_List.HPX_LOW.getName());
		companyControl.click(AddCompany.ADD_LICENSEE_PRODUCT);
		companyControl.click(CompanyEdit.SAVE);
		// 8. Click to "Save" button
		// VP: The license user information show up.
		// - The ''Add User'' button change to '' Change'' button
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.CONTACT_NAME));
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.CHANGE_PARTNER_CONTACT));
		companyControl.doDelete(CompanyInfo.DELETE);
	}
	/*
	 * Verify canbe save the contact person page with new option on Brand Privileges
	 */
	@Test
	public void TC070DAEC_19() {
		companyControl.addLog("ID TC070DAEC_19 : Verify canbe save the contact person page with new option on Brand Privileges");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Choose one company donot have license.
			5. Click �Edit� link
			6. Click to "Enable" button Licensee Device
			7. Fill valid value into all required fields except ''Licence Device'' box
			8. Click to "Save" button
			VP: The page cannot be save due to didnot choose any Product type on License product combo box
		*/
		/*
		 */
		// 1. Navigate to DTS portal
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Click "Add Company" link
		companyControl.click(Companies.ADD_COMPANY);
		// 5. Click to "Enable" button Licensee Device
		companyControl.click(CompanyInfo.LICENSEE_DISABLE);
		// 6. Fill valid value into all required fields
		// 7. Fill valid value into all required fields except ''Licence Device'' box
		Hashtable<String,String> data = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), data);
		//companyControl.selectOptionByName(AddCompany.LICENSEE_PRODUCT, AddCompany.Licensee_Product_List.HPX_LOW.getName());
		//companyControl.click(AddCompany.ADD_LICENSEE_PRODUCT);
		// 8. Click to "Save" button
		companyControl.click(AddCompany.SAVE);
		// VP: The page cannot be save due to didnot choose any Product type on License product combo box
		Assert.assertTrue(companyControl.checkMessageDisplay(PageLogin.errMessage.At_least_1_license_production_type.getName()));
	}
}
