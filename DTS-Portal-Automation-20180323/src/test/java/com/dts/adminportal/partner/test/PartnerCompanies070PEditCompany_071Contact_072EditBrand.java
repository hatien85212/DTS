package com.dts.adminportal.partner.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddBrand;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.BrandInfo;
import com.dts.adminportal.model.CompanyContact;
import com.dts.adminportal.model.CompanyInfo;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PartnerCompanyEdit;
import com.dts.adminportal.model.PartnerCompanyInfo;
import com.dts.adminportal.model.PartnerContactinfo;
import com.dts.adminportal.model.PartnerEditBrands;
import com.dts.adminportal.model.PrimaryPartnerContact;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.UserEdit;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.TestData;

public class PartnerCompanies070PEditCompany_071Contact_072EditBrand extends BasePage{
	
	@Override
	protected void initData() {
	}	

	/*
	 *Verify that the "Edit Company Info" page displays company's info correctly. 
	 */
	@Test
	public void TC070PEC_01(){
		companyControl.addLog("ID : TC070PEC_01 : Verify that the 'Edit Company Info' page displays company's info correctly.");
		/*

			Pre-condition: partner user has "Edit Company Info" rights.
			
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			VP: Verify that the "Edit" link is displayed next to "Company Info" label
			4. Click "Edit" link
			VP: Verify that the "Edit Company Info" page displays company's info correctly with following content:
				Official Corporate Name, Corporate Address includes: Address Line 1, Address Line 2, Address Line 3, City/Town, State/Province/Region, ZIP/Postal Code and Country field.
		*/
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_PARTNER_COMPANY);
		//VP: Verify that the "Edit" link is displayed next to "Company Info" label
		Assert.assertEquals(true, companyControl.existsElement(PartnerCompanyInfo.getListXpath()));
		//4. Click "Edit" link
		companyControl.click(PartnerCompanyInfo.EDIT);
		
		//VP: Verify that the "Edit Company Info" page displays company's info correctly with following content:
		//Official Corporate Name, Corporate Address includes: Address Line 1, Address Line 2, Address Line 3, City/Town, State/Province/Region, ZIP/Postal Code and Country field.
		//VP: The Headphone:X Partnerships is un-editable.
		Assert.assertEquals(true, companyControl.existsElement(PartnerCompanyEdit.getListXpath()));
		Assert.assertFalse(companyControl.isPartnershipsEditable(PartnerCompanyEdit.PARTNERSHIPS_TABLE));
	}
	
	/*
	 * Verify that the company's info could be changed successfully when clicking "Save" link in "Edit Company Info" page.
	 */
	
	@Test
	public void TC070PEC_02(){
		companyControl.addLog("ID : TC070PEC_02 : Verify that the company's info could be changed successfully when clicking 'Save' link in 'Edit Company Info' page.");
		/*
		Pre-condition: partner user has "Edit Company Info" rights.
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click "Companies" tab
		VP: Verify that the "Edit" link is displayed next to "Company Info" label
		4. Click "Edit" link
		VP: Verify that the "Edit Company Info" page is displayed
		5. Leave the "Official Corporate Name" field blank
		6. Click "Save" link
		VP: User is unable to save due to an error message which mentioning that Official Corporate Name is required.
		7. Change another company name
		8. Click "Cancel" link
		VP: The 061P Company Page is displayed with no change
		9. Click "Edit" link again
		10. Change another company name
		11. Click "Save" link
		VP: The 061P Company Page is displayed with correct info changed.
		*/
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_PARTNER_COMPANY);
		//VP: Verify that the "Edit" link is displayed next to "Company Info" label
		Assert.assertEquals(true, companyControl.existsElement(PartnerCompanyInfo.getListXpath()));
		//4. Click "Edit" link
		companyControl.click(PartnerCompanyInfo.EDIT);
		//VP: Verify that the "Edit Company Info" page is displayed
		Assert.assertEquals(true, companyControl.existsElement(PartnerCompanyEdit.getListXpath()));
		//6. Leave the "Official Corporate Name" field blank
		companyControl.type(PartnerCompanyEdit.OFFICIAL_CORP_NAME, "");
		//7. Click "Save" link
		companyControl.click(PartnerCompanyEdit.SAVE);
		// VP: User is unable to save due to an error message which mentioning that Official Corporate Name is required.
		Assert.assertTrue(companyControl.checkMessageDisplay("Corporate Name is required"));
		//7. Change another company name
		//8. Click "Cancel" link
		//VP: The 061P Company Page is displayed with no change
		companyControl.editData(PartnerCompanyEdit.OFFICIAL_CORP_NAME, RandomStringUtils.randomAlphabetic(10));
		companyControl.click(PartnerCompanyEdit.CANCEL_EDIT);
		Assert.assertTrue(companyControl.getTextByXpath(CompanyInfo.CORP_NAME).contains(PARTNER_COMPANY_NAME));
		//9. Click "Edit" link again
		companyControl.click(PartnerCompanyInfo.EDIT);
		//10. Change address of company
		String address = RandomStringUtils.randomAlphabetic(10);
		companyControl.editData(PartnerCompanyEdit.ADDRESS1, address);
		//11. Click "Save" link
		companyControl.click(PartnerCompanyEdit.SAVE);
		//The 061P Company Page is displayed with correct info changed.
		Assert.assertTrue(companyControl.getTextByXpath(PartnerCompanyInfo.ADDRESS1).contains(address));
		
	}
	
		
	/*
	 * Verify that the generic international friendly address form based on Amazon.com's form design is used for company's Corporate Address
	 */
	@Test
	public void TC070PEC_03(){
		companyControl.addLog("ID : TC070PEC_03 : Verify that the generic international friendly address form based on Amazon.com's form design is used for company's Corporate Address");
		companyControl.addErrorLog("PDPP-726: 070 Edit Company: The company's corporate address is not implemented as amazon's friendly address form.");
		/*
			Pre-condition: partner user has "Edit Company Info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			VP: Verify that the "Edit" link is displayed next to "Company Info" label
			4. Click "Edit" link
			"VP:Verify that the ""Edit Company Info"" page is displayed and
				The company's Corporate Address includes:
				Address Line 1, Address Line 2, Address Line 3, City/Town
				,State/Province/Region, ZIP/Postal Code text fields and Country dropdown list.
				Below each address line has an instruction:
				Address Line 1: Street address, P.O Box, c/o
				Address Line 2: Suite, unit, building, floor, etc.
				Address Line 3: Attn, other info.
			"
		*/
		
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_PARTNER_COMPANY);
		/*
		 * VP: Verify that the "Edit" link is displayed next to "Company Info" label
		 */
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.EDIT));
		// 4. Click "Edit" link
		companyControl.click(CompanyInfo.EDIT);
		companyControl.addErrorLog("PDPP-726: 070 Edit Company: The company's corporate address is not implemented as amazon's friendly address form");
		/*
		 * VP: Verify that the "Edit Company Info" page is displayed
		 */
		Assert.assertEquals(true, companyControl.existsElement(PartnerCompanyEdit.getListXpath()));
		Assert.assertTrue(companyControl.getTextByXpath(PartnerCompanyEdit.INSADD1).contains(PartnerCompanyEdit.INSADD1_TEXT));
		Assert.assertTrue(companyControl.getTextByXpath(PartnerCompanyEdit.INSADD2).contains(PartnerCompanyEdit.INSADD2_TEXT));
		Assert.assertTrue(companyControl.getTextByXpath(PartnerCompanyEdit.INSADD3).contains(PartnerCompanyEdit.INSADD3_TEXT));
	}
	
	/*
	 * Verify that the 071P primary contact displays proper information.
	 */
	@Test
	public void TC071PaC_01(){
		companyControl.addLog("ID TC071PaC_01: Verify that the 071P primary contact displays proper information.");
		/*
			Pre-condition: partner user has "Edit Company Info" rights.

		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click "Companies" tab
		VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		4. Click "Change" link
		VP: Verify that the "Current Contact Person", "New Contact Persion", an active user table and "Actions" modules are displayed in "Change Primary Partner Contact" page.
		VP: The Current Contact Person module displays the current contact name, Email and Phone number correctly.
		VP: The "New Contact Person" module displays a notification message which mention user should select a new person from active users table such as "Select a person from the list below".
		VP: The active users table displays including "First Name", "Last Name", "Title", "Phone" and "Email" column
		VP: The active users table shows up to 50 users.
		5. Click "Save" link
		VP: The warning message displays to inform user select a new contact person.
		6. Click "Cancel" link
		 */
		loginControl.login(BasePage.SUPER_USER_NAME, BasePage.SUPER_USER_PASSWORD);
		userWf.enableAllPrivilegeOfUser(PARTNER_USER);
		companyControl.click(UserEdit.SAVE);
		loginControl.logout();
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_PARTNER_COMPANY);
		//VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		Assert.assertEquals(true, companyControl.existsElement(PartnerCompanyInfo.getListXpath()));
		//4. Click "Change" link
		//Get data before edit
//		String Phone_primary=companyControl.getTextByXpath(PartnerCompanyInfo.PHONE);
//		String email = companyControl.getTextByXpath(PartnerCompanyInfo.EMAIL);
		String name_primary=companyControl.getTextByXpath(PartnerCompanyInfo.CONTACT_NAME);
		String email_primary=companyControl.getTextByXpath(PartnerCompanyInfo.EMAIL);
		String phone_primary=companyControl.getTextByXpath(PartnerCompanyInfo.PHONE);
		companyControl.click(PartnerCompanyInfo.CHANGE_PARTNER_CONTACT);
		//VP:  Verify that the "Change Primary Partner Contact" page displays "Current Contact Person", 
		//+ "New Contact Person", an active users table and "Actions" module.
		Assert.assertEquals(true, companyControl.existsElement(PartnerContactinfo.getListXpath()));
		//VP:  Verify that the "Current Contact Person" module in "Change Primary Partner Contact" page displays contact name, email and phone number correctly.
		Assert.assertEquals(companyControl.existsElement(CompanyContact.getElementInfo()), true);
		String current_contact = companyControl.getTextByXpath(CompanyContact.CURRENT_CONTACT);
		Assert.assertTrue(current_contact.contains(name_primary)
				&& current_contact.contains(email_primary)
				&& current_contact.contains(phone_primary));
		//VP: The "New Contact Person" module displays a notification message which mention user should select a new person from active users table such as "Select a person from the list below".
		String message = "Select a different user from the list of active user accounts";
		Assert.assertTrue(companyControl.checkMessageDisplay(message));
		//VP: The active users table displays including "First Name", "Last Name", "Title", "Phone" and "Email" column
		Assert.assertEquals(true, companyControl.existsElement(PartnerContactinfo.gettableactive()));
		//VP: The active users table shows up to 50 users.
		Assert.assertTrue(companyControl.checkAmountOfDisplayedItemOnTable(PrimaryPartnerContact.TOTAL_ITEM,50));
		
		//5. Click "Save" link
		companyControl.click(CompanyContact.SAVE);
		//Verify that The warning message displays to inform user select a new contact person
		String message1 = "Select a person from the list below";
		Assert.assertTrue(companyControl.checkMessageDisplay(message1));
		//6. Click "Cancel" link
		companyControl.click(CompanyContact.CANCEL);
		// VP:The 061P Company Page is displayed with previous primary partner contact info. 
		Assert.assertTrue(companyControl.getTextByXpath(PartnerCompanyInfo.PHONE).contains(phone_primary));
		Assert.assertTrue(companyControl.getTextByXpath(PartnerCompanyInfo.EMAIL).contains(email_primary));
		
	}
	
	/*
	 * Verify that the partner's Primary Partner Contact info is changed  successfully
	 */
	@Test
	public void TC071PaC_02(){
		companyControl.addLog("ID : TC071PaC_02: Verify that the partner's Primary Partner Contact info is changed  successfully");
		/*
		Pre-condition: partner user has "Edit Company Info" rights.
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click "Companies" tab
		VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		4. Click "Change" link
		VP: Verify that the "Change Primary Partner Contact" page is displayed
		5. Select a different user from active users table
		VP: The contact name, email and phone number of selected user is displayed correctly in "New Contact Person: module
		6. Click "Save" link
		VP: The 061P Company Page is displayed with new Primary Partner Contact info.
		 */
		
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_PARTNER_COMPANY);
		/*
		 * VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		 */
		Assert.assertTrue(companyControl.isElementPresent(PartnerCompanyInfo.PRIMARY_PARTNER_CONTACT));
		Assert.assertTrue(companyControl.isElementPresent(PartnerCompanyInfo.CHANGE_PARTNER_CONTACT));
		// 4. Click "Change" link
		companyControl.click(PartnerCompanyInfo.CHANGE_PARTNER_CONTACT);
		/*
		 * VP: Verify that the "Change Primary Partner Contact" page is displayed
		 */
		Assert.assertEquals(companyControl.existsElement(CompanyContact.getElementInfo()), true);
		// 5. Select a different user from active users table
		userControl.selectUserInfoByEmail(PARTNER_USER);
		/*
		 * Verify that The contact name, email and phone number of selected user is displayed correctly in "New Contact Person" module
		 */
		// Get user info
		int row_index = companyControl.getRowIndexByEmail(CompanyContact.CONTACT_TABLE, PARTNER_USER);
		ArrayList<String> user_info = companyControl.getUserInfoByIndex(CompanyContact.CONTACT_TABLE, row_index);
		// Get info in "New Contact Person" module
		String new_contact_info = companyControl.getTextByXpath(CompanyContact.NEW_CONTACT);
		Assert.assertTrue(ListUtil.containsListText(new_contact_info, user_info));
		//6. Click "Save" link
		//VP: The 061P Company Page is displayed with new Primary Partner Contact info.
		companyControl.click(CompanyContact.SAVE);
		ArrayList<String> list = new ArrayList<String>();
		list.add(companyControl.getTextByXpath(PartnerCompanyInfo.CONTACT_NAME));
		list.add(companyControl.getTextByXpath(PartnerCompanyInfo.PHONE).replaceAll("\\+\\s", "\\+"));
		list.add(companyControl.getTextByXpath(PartnerCompanyInfo.EMAIL));
		Assert.assertTrue(ListUtil.containsListText(new_contact_info, list));
	}
		
	/*
	 * Verify that the partner's Primary Partner Contact info is not changed when user cancels saving change after selecting new contact in "Change Primary Partner Contact" page
	 */
	@Test
	public void TC071PaC_03(){
		companyControl.addLog("ID : TC071PaC_03: Verify that the partner's Primary Partner Contact info is not changed when user cancels saving change after selecting new contact in 'Change Primary Partner Contact' page");
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
		
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_PARTNER_COMPANY);
		/*
		 * VP: Verify that the "Change" link is displayed next to "Primary Partner Contact" label
		 */
		Assert.assertTrue(companyControl.isElementPresent(PartnerCompanyInfo.PRIMARY_PARTNER_CONTACT));
		Assert.assertTrue(companyControl.isElementPresent(PartnerCompanyInfo.CHANGE_PARTNER_CONTACT));
		// 4. Click "Change" link
		// Get primary contact info
		String contact_name = companyControl.getTextByXpath(PartnerCompanyInfo.CONTACT_NAME);
		String phone_number = companyControl.getTextByXpath(PartnerCompanyInfo.PHONE);
		String email = companyControl.getTextByXpath(PartnerCompanyInfo.EMAIL);
		// Click "Change" link
		companyControl.click(PartnerCompanyInfo.CHANGE_PARTNER_CONTACT);
		/*
		 * VP: Verify that the "Change Primary Partner Contact" page is displayed
		 */
		Assert.assertEquals(companyControl.existsElement(CompanyContact.getElementInfo()), true);
		// 5. Select a different user from active users table
		userControl.selectPartnerUserByEmail(PARTNER_USER);
		// 6. Click "Cancel" link
		companyControl.click(CompanyContact.CANCEL);
		/*
		 * Verify that The 061P Company Page is displayed with previous primary partner contact info
		 */
		Assert.assertEquals(companyControl.getTextByXpath(PartnerCompanyInfo.CONTACT_NAME), contact_name);
		Assert.assertEquals(companyControl.getTextByXpath(PartnerCompanyInfo.PHONE), phone_number);
		Assert.assertEquals(companyControl.getTextByXpath(PartnerCompanyInfo.EMAIL), email);
	}
	
	/*
	 *Verify that the Edit Brand Info page displays properly.
	 */
	@Test
	public void TC072PEBI_01(){
		companyControl.addLog("ID : TC072PEBI_01:Verify that the Edit Brand Info page displays properly.");
		/*
		 	
			Pre-condition: partner user has "Edit Company Info" privilege but does not have "Edit brand info" privielge

			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			VP: Verify that the "Add" link is displayed next to "Brands" label
			4. Click "Add" link
			VP: Verify that the "Edit Brand Info" page is displayed which including:
			Languages dropdown, Brand name text field, Brand Tag Line text field, Consumer Brand Aliases text field, Web Site text field, Brand Overview text field, Copyright and Trademark Notice text field and Consumer Brand Logo thumbnail with three types( 250x250, 500x500, 1000x1000)
			5. Fill valid value into all fields of "Edit Brand Info" page
			6. Click "Cancel" link in the Action module
			VP: The portal redirect to previous page and there is no new brand added
			7. Repeat from step 4 to 5
			8. Click "Save" link
		 */
		//Precondition:
		userWf.loginAndDisablePrivilege(PARTNER_USER, Privileges.privileges.Edit_brand_info.getName(), false);
		// Log out
		companyControl.logout();
		/*
		 * ********************************************************************
		 */
		// 2. Log into DTS portal as a partner user successfully
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_PARTNER_COMPANY);
		//VP: Verify that the "Add" link is displayed next to "Brands" label
		Assert.assertTrue(companyControl.isElementPresent(PartnerCompanyInfo.ADD_BRAND));
		//4.Click "Add" link 
		companyControl.click(PartnerCompanyInfo.ADD_BRAND);
		//VP: Verify that the Edit Brand Info page displays properly.
		Assert.assertEquals(true, companyControl.existsElement(PartnerEditBrands.getelement()));
		
		//5. Fill valid value into all fields of "Edit Brand Info" page
		Hashtable<String,String> data = TestData.brandDraft();
		data.remove("save");
		companyControl.addBrand(AddBrand.getHash(), data);
		
		//6. Click cancel link
		companyControl.click(AddBrand.CANCEL);
		//VP: The portal redirect to previous page and there is no new brand added
		Assert.assertTrue(companyControl.getTextByXpath(PartnerCompanyInfo.CORP_NAME).contains(PARTNER_COMPANY_NAME));
		Assert.assertFalse(companyControl.checkBrandExist(data.get("name")));		
		
		
		//7. Repeat from step 4 to 5
		//8. Click "Save" link
		companyControl.click(PartnerCompanyInfo.ADD_BRAND);
		Hashtable<String,String> data1 = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), data1);
		
		/*
		 * Verify that The 063P Brand Ingo Page is displayed with new information correctly without "Edit" and "Delete" link
		 */
		Assert.assertEquals(companyControl.existsElement(BrandInfo.getAllField()), true);
		Assert.assertFalse(companyControl.isElementPresent(BrandInfo.EDIT_BRAND));
		Assert.assertFalse(companyControl.isElementPresent(BrandInfo.DELETE_LINK));
		
		// Delete brand
		loginControl.logout();
		userWf.loginAndEnableAllPrivilege(PARTNER_USER);
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		companyControl.selectABrandByName(data1.get("name"));
		companyControl.doDelete(BrandInfo.DELETE_LINK);
				
	}
	
	
	/*
	 * Verify that three resolution types of CONSUMER Brand Logos could be uploaded again after deleted.
	 */
	@Test
	public void TC072PEBI_02(){
		companyControl.addLog("ID : TC072PEBI_02: Verify that three resolution types of CONSUMER Brand Logos could be uploaded again after deleted");
		/*
			Pre-condition: partner user has "Edit Company Info" rights only.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
			VP: Verify that the "Add" link is displayed next to "Brands" label
			4. Click "Add" link
			VP: Verify that the "Edit Brand Info" page is displayed
			5. Fill valid value into all fields of "Edit Brand Info" page
			6. Upload image for 160x160 resolution type
			VP: Verify that the image for 160x160 is uploaded successfully
			7. Upload image for 290x290 resolution type
			VP: Verify that the image for 290x290 is uploaded successfully
			8. Upload image for 664x664 resolution type
			VP: Verify that the image for 664x664 is uploaded successfully
			9. Delete all brand's logo types
			10. Repeat from step 6 to 8
		 */
		
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_PARTNER_COMPANY);
		//VP: Verify that the "Add" link is displayed next to "Brands" label
		Assert.assertTrue(companyControl.isElementPresent(PartnerCompanyInfo.ADD_BRAND));
		// 4.Click "Add" link 
		companyControl.click(PartnerCompanyInfo.ADD_BRAND);
		//VP: Verify that the Edit Brand Info page displays properly.
		Assert.assertEquals(companyControl.existsElement(AddBrand.getListAddBrand()), true);
		// 5. Fill valid value into all fields of "Edit Brand Info" page
		// 6. Upload image for 160x160 resolution type
		companyControl.uploadFile(AddBrand.ADD_IMAGE250, AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		/*
		 * VP: Verify that the image for 160x160 is uploaded successfully
		 */
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE250_DISPLAY, "src").contains(AddEditProductModel.SIZE_LIST[0]));
		// 7. Upload image for 290x290 resolution type
		companyControl.uploadFile(AddBrand.ADD_IMAGE500, AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		/*
		 * VP: Verify that the image for 290x290 is uploaded successfully
		 */
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE500_DISPLAY, "src").contains(AddEditProductModel.SIZE_LIST[1]));
		// 8. Upload image for 664x664 resolution type
		companyControl.uploadFile(AddBrand.ADD_IMAGE1000, AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		/*
		 * VP: Verify that the image for 664x664 is uploaded successfully
		 */
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE1000_DISPLAY, "src").contains(AddEditProductModel.SIZE_LIST[2]));
		//9. Delete all brand's logo types
		companyControl.doDelete(AddBrand.DELELE_IMAGE250);
		companyControl.doDelete(AddBrand.DELELE_IMAGE500);
		companyControl.doDelete(AddBrand.DELELE_IMAGE1000);
		//10. Repeat from step 6 to 8
		companyControl.uploadFile(AddBrand.ADD_IMAGE250, AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		companyControl.uploadFile(AddBrand.ADD_IMAGE500, AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		companyControl.uploadFile(AddBrand.ADD_IMAGE1000, AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		/*
		 * Verify that All three logo types are uploaded successfully
		 */
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE250_DISPLAY, "src").contains(AddEditProductModel.SIZE_LIST[0]));
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE500_DISPLAY, "src").contains(AddEditProductModel.SIZE_LIST[1]));
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.IMAGE1000_DISPLAY, "src").contains(AddEditProductModel.SIZE_LIST[2]));
	}
	
	/*
	 * Verify that the lightbox style popup is displayed when clicking on three size of brand logo
	 */
	@Test
	public void TC072PEBI_03(){
		companyControl.addLog("ID : TC072PEBI_03: Verify that the lightbox style popup is displayed when clicking on three size of brand logo");
		/*
			Pre-condition: User has "Edit Company Info" rights only.

		1. Navigate to DTS portal
		2. Log into DTS portal as a Partner user successfully
		3. Click "Companies" tab
		VP: Verify that the "Add" link is displayed next to "Brands" label
		4. Click "Add" link
		VP: Verify that the "Edit Brand Info" page is displayed
		5. Fill valid value into all fields of "Edit Brand Info" page
		6. Upload image for both three size of brand logo successfully
		7. Click on each brand logo image
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_PARTNER_COMPANY);
		// 4.Click "Add" link 
		companyControl.click(PartnerCompanyInfo.ADD_BRAND);
		// 6. Upload image for 160x160 resolution type
		companyControl.uploadFile(AddBrand.ADD_IMAGE250, AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		// 7. Upload image for 290x290 resolution type
		companyControl.uploadFile(AddBrand.ADD_IMAGE500, AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		// 8. Upload image for 664x664 resolution type
		companyControl.uploadFile(AddBrand.ADD_IMAGE1000, AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		// Click on picture of each primary catalog image
		// 250x250
		companyControl.click(AddBrand.IMAGE250_DISPLAY);
		companyControl.waitForElementClickable(AddBrand.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(companyControl.isElementPresent(AddBrand.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_250_JPG.getName().replaceAll(".jpg", "")));
		companyControl.click(AddBrand.LIGHTBOX_CLOSE);
		//500x500
		companyControl.click(AddBrand.IMAGE500_DISPLAY);
		companyControl.waitForElementClickable(AddBrand.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(companyControl.isElementPresent(AddBrand.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_500_JPG.getName().replaceAll(".jpg", "")));
		companyControl.click(AddBrand.LIGHTBOX_CLOSE);
		//1000x1000
		companyControl.click(AddBrand.IMAGE1000_DISPLAY);
		companyControl.waitForElementClickable(AddBrand.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(companyControl.isElementPresent(AddBrand.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(companyControl.getAtributeValue(AddBrand.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_1000_JPG.getName().replaceAll(".jpg", "")));
		companyControl.click(AddBrand.LIGHTBOX_CLOSE);
	}
	
}
	
