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
import com.dts.adminportal.util.DateUtil;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AccessoryMain;
import dts.com.adminportal.model.AddAccessory;
import dts.com.adminportal.model.AddBrand;
import dts.com.adminportal.model.AddCompany;
import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.BrandInfo;
import dts.com.adminportal.model.Companies;
import dts.com.adminportal.model.CompanyInfo;
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.DeviceEdit;
import dts.com.adminportal.model.DeviceList;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.UsersList;
import dts.com.adminportal.model.Xpath;

public class DTSUserCompanies061DaCompaniesInfo extends CreatePage {
	private HomeController home;
	String filter = "Suspended";
	
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}

	@BeforeMethod
	public void loginBeforeTest() {
		home.login(superUsername, superUserpassword);
	}
	
	/*
	 * Verify that the partner's company page displays correct information
	 */
	@Test
	public void TC061DaCI_01() {
		result.addLog("ID TC061DaCI_01 : Verify that the partner's company page displays correct information");
		/*
		  	Pre-condition: User has full site privileges rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click 'Companies' tab
			4. Select any company
		*/
		// 3. Click 'Companies' tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select any company
		home.selectACompanyByName(partner_company_name);
		/*
		 * Verify that the partner's company info page is displayed correctly
		 */
		Assert.assertEquals(home.existsElement(CompanyInfo.getListElement()).getResult(), "Pass");
	}
	
	/*
	 * Verify that the 'Actions' module is  displayed with 'Suspend' and 'Delete' links.
	 */
	@Test
	public void TC061DaCI_02() {
		result.addLog("ID TC061DaCI_02 : Verify that the 'Actions' module is  displayed with 'Suspend' and 'Delete' links.");
		/*
		  	Pre-condition: User has full site privileges rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click 'Companies' tab
			4. Select any company
		*/
		// 3. Click 'Companies' tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select any company
		home.selectACompanyByName(partner_company_name);
		/*
		 * The "Actions" module is  displayed which includes "Suspend" and "Delete" links.
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.ACTION_MODULE));
		Assert.assertTrue(home.isElementPresent(CompanyInfo.DELETE));
		Assert.assertTrue(home.isElementPresent(CompanyInfo.SUSPEND));
	}
	
	/*
	 * Verify that the Brand section displays the brand's image thumbnail and brand name link.
	 */
	@Test
	public void TC061DaCI_03() {
		result.addLog("ID TC061DaCI_03 : Verify that the Brand section displays the brand's image thumbnail and brand name link.");
		/*
		  	Pre-condition: User has full site privileges rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click 'Companies' tab
			4. Select any company
		*/
		// 3. Click 'Companies' tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select any company
		home.selectACompanyByName(partner_company_name);
		/*
		 * Verify that the Brand section displays the brand's image thumbnail and brand name link.
		 */
		Assert.assertTrue(home.checkBrandExist(partner_brand_name_1));
		
	}
	
	/*
	 * Verify that the user who has "Edit brand info" site privilege could add more company's brand
	 */
	@Test
	public void TC061DaCI_04() {
		result.addLog("ID TC061DaCI_04 : Verify that the user who has 'Edit brand info' site privilege could add more company's brand");
		/*
	 		Pre-condition: partner user has "Edit brand info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Select a company on table
			VP: The "Add Brand" link is displayed
			5. Click "Add Brand" link
			6. Fill valid value into all required fields.
			7. Click "Save" link
		*/
		// 3. Click 'Companies' tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Select a company on table
		home.selectACompanyByName(partner_company_name);
		/*
		 * VP: The "Add Brand" link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(CompanyInfo.ADD_BRAND));
		// 5. Click "Add Brand" link
		home.click(CompanyInfo.ADD_BRAND);
		// 6. Fill valid value into all required fields.
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.brandDraft();
		home.addBrand(AddBrand.getHash(), data);
		/*
		 * Verify that New company's brand is created successfully
		 */
		Assert.assertEquals(home.existsElement(BrandInfo.getAllField()).getResult(), "Pass");
		// Delete brand
		home.doDelete(BrandInfo.DELETE_LINK);
	}
	
	/*
	 * Verify that when a company is suspended , the associated users are also suspended
	 */
	@Test
	public void TC061DaCI_05() {
		result.addLog("ID TC061DaCI_05 : Verify that when a company is suspended , the associated users are also suspended");
		/*
	 		Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to “Companies” page
			3. Select a company from companies list
			4. Click “Suspend” link in 061Da Company info page
			5. Click “Suspend” button on Suspend confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the Suspended company is displayed in Suspended list
			6. Navigate to “Users” page
			7. List out the “Company” filter combobox
		*/
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		// Navigate to Companies page
		home.click(Xpath.LINK_COMPANY);
		// Click Add company link
		home.click(Companies.ADD_COMPANY);
		// Create new company
		Hashtable<String,String> dataCompany = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), dataCompany);
		// Create user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		home.addUser(AddUser.getPartnerUser(), dataUser);
		/*
		 * **********************************************************
		 */
		// 2. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 3. Select a company from companies list
		home.selectACompanyByName(dataCompany.get("name"));
		// 4. Click “Suspend” link in 061Da Company info page
		home.click(CompanyInfo.SUSPEND);
		// 5. Click “Suspend” button on Suspend confirmation dialog
		home.selectConfirmationDialogOption("Suspend");
		/*
		 * VP. Verify that the portal is redirected to companies list page and the Suspended company is displayed in Suspended list
		 */
		Assert.assertEquals(home.existsElement(Companies.getListElements()).getResult(), "Pass");
		home.selectOptionByName(Companies.FILTER, "Suspended");
		Assert.assertTrue(home.checkACompanyExistByName(dataCompany.get("name")));
		// 6. Navigate to “Users” page
		home.click(Xpath.LINK_USERS);
		// 7. List out the “Company” filter combobox
		ArrayList<String> companyList = home.getAllComboboxOption(UsersList.FILTER_COMPANY);
		/*
		 * Verify that the suspended company is not displayed in “Company” filter combobox and  associated user of the suspended company are displayed in suspended list
		 */
		Assert.assertFalse(DTSUtil.checkListContain(companyList, dataCompany.get("name")));
		home.selectOptionByName(UsersList.FILTER_USER, "Suspended");
		Assert.assertTrue(home.checkUserExistByEmail(dataUser.get("email")));
		// Delete company
		home.click(Xpath.LINK_COMPANY);
		home.selectOptionByName(Companies.FILTER, "Suspended");
		home.deleteACompanyByName(dataCompany.get("name"));
	}
	
	/*
	 * Verify that the suspended company is not displayed in Company combobox when adding new users
	 */
	@Test
	public void TC061DaCI_06() {
		result.addLog("ID TC061DaCI_06 : Verify that the suspended company is not displayed in Company combobox when adding new users");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to “Companies” page
			3. Select a company from companies list
			4. Click “Suspend” link in 061Da Company info page
			5. Click “Suspend” button on delete confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the suspended company is displayed in Suspended list
			6. Navigate to “Users” page
			7. Click “Add New User” link
			8. List out the “Company” compbobox
		*/
		
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		// Navigate to company page
		home.click(Xpath.LINK_COMPANY);
		// Click Add company link
		home.click(Companies.ADD_COMPANY);
		/*
		 * Init data
		 */
		Hashtable<String,String> data = new Hashtable<String,String>();
		String companyName = "Test" + RandomStringUtils.randomNumeric(5);
		String date = DateUtil.getADateGreaterThanToday("MMMM d, yyyy", 3);
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("type", "Partner");
		data.put("date", date);
		data.put("name", companyName);
		data.put("address", RandomStringUtils.randomAlphanumeric(20));
		data.put("city", RandomStringUtils.randomAlphabetic(10));
		data.put("state", RandomStringUtils.randomAlphabetic(10));
		data.put("country", "Vietnam");
		data.put("partnerships", AddCompany.PARTNERSHIP_LIST[0]);
		/*
		 * Create company
		 */
		home.addCompany(AddCompany.getHash(), data);
		// 2. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 3. Select a company from companies list
		home.selectACompanyByName(companyName);
		// 4. Click “Suspend” link in 061Da Company info page
		home.click(CompanyInfo.SUSPEND);
		// 5. Click “Suspend” button on delete confirmation dialog
		home.switchWindowClickOption("Suspend");
		/*
		 * VP. Verify that the portal is redirected to companies list page 
		 */
		Assert.assertEquals(home.existsElement(Companies.getListElements()).getResult(), "Pass");
		/*
		 * Verify that The suspended company is displayed in Suspended list
		 */
		home.selectFilterByName(Companies.FILTER, filter);
		Assert.assertTrue(home.checkACompanyExistByName(companyName));
		// 6. Navigate to “Users” page
		home.click(Xpath.LINK_USERS);
		// 7. Click “Add New User” link
		home.click(UsersList.ADD_USER);
		// 8. List out the “Company” compbobox
		ArrayList<String> companyList = home.getAllComboboxOption(AddUser.BTN_COMPANY);
		/*
		 * Verify that the suspended company is not displayed in “Company” combobox
		 */
		Assert.assertFalse(DTSUtil.checkListContain(companyList, companyName));
		// Delete company
		home.click(Xpath.LINK_COMPANY);
		home.selectOptionByName(Companies.FILTER, filter);
		home.deleteACompanyByName(companyName);
	}
	
	/*
	 * Verify that when a company is suspended , the associated Products are also suspended
	 */
	@Test
	public void TC061DaCI_07() {
		result.addLog("ID TC061DaCI_07 : Verify that when a company is suspended , the associated Products are also suspended");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to “Companies” page
			3. Select a company from companies list
			4. Click “Suspend” link in 061Da Company info page
			5. Click “Suspend” button on Suspend confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the Suspend company is displayed in Suspended list
			6. Navigate to “Products” page
		*/
		/*
		 * PreCondition: Company has at least one brand and one product
		 */
		// Navigate to Companies page
		home.click(Xpath.LINK_COMPANY);
		// Click Add company link
		home.click(Companies.ADD_COMPANY);
		// Create new company
		Hashtable<String,String> dataCompany = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), dataCompany);
		// Create user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		home.addUser(AddUser.getPartnerUser(), dataUser);
		// Create brand
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		home.addBrand(AddBrand.getHash(), dataBrand);
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> dataProduct = TestData.accessoryDraft();
		dataProduct.put("company", dataCompany.get("name"));
		dataProduct.put("brand", dataBrand.get("name"));
		home.addAccessoriesPartner(AddAccessory.getHash(), dataProduct);
		/*
		 * *****************************************************
		 */
		// 2. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 3. Select a company from companies list
		home.selectACompanyByName(dataCompany.get("name"));
		// 4. Click “Suspend” link in 061Da Company info page
		home.click(CompanyInfo.SUSPEND);
		// 5. Click “Suspend” button on Suspend confirmation dialog
		home.selectConfirmationDialogOption("Suspend");
		/*
		 * VP. Verify that the portal is redirected to companies list page and the Suspend company is displayed in Suspended list
		 */
		Assert.assertEquals(home.existsElement(Companies.getListElements()).getResult(), "Pass");
		home.selectOptionByName(Companies.FILTER, "Suspended");
		Assert.assertTrue(home.checkACompanyExistByName(dataCompany.get("name")));
		// 6. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		/*
		 * Verify that the associated product of the Suspended company are not displayed in Active Products list but it's displayed in Suspended list
		 */
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, "Suspended");
		// Check if PDPP-1313 found
		if(!home.checkAnAccessoryExistByName(dataProduct.get("name"))){
			result.addErrorLog("PDPP-1313 - 030D Catalog All Active Models: Products of suspended company disappear even in 'Suspened' list");
			Assert.assertTrue(false);
		}
		Assert.assertTrue(home.checkAnAccessoryExistByName(dataProduct.get("name")));
		// Delete company
		home.click(Xpath.LINK_COMPANY);
		home.selectOptionByName(Companies.FILTER, "Suspended");
		home.deleteACompanyByName(dataCompany.get("name"));
	}
	
	
	/*
	 * Verify that the suspended company is not displayed in Company combobox when adding new products
	 */
	@Test
	public void TC061DaCI_08() {
		result.addLog("ID TC061DaCI_08 : Verify that the suspended company is not displayed in Company combobox when adding new products");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to “Companies” page
			3. Select a company from companies list
			4. Click “Suspend” link in 061Da Company info page
			5. Click “Suspend” button on Suspend confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the suspended company is displayed in Suspended list
			6. Navigate to “Products” page
			7. Click “Add Product” link
			8. List out the “Company” combobox
		*/
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		// Navigate to company page
		home.click(Xpath.LINK_COMPANY);
		// Click Add company link
		home.click(Companies.ADD_COMPANY);
		/*
		 * Init data
		 */
		Hashtable<String,String> data = new Hashtable<String,String>();
		String companyName = "Test" + RandomStringUtils.randomNumeric(5);
		String date = DateUtil.getADateGreaterThanToday("MMMM d, yyyy", 3);
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("type", "Partner");
		data.put("date", date);
		data.put("name", companyName);
		data.put("address", RandomStringUtils.randomAlphanumeric(20));
		data.put("city", RandomStringUtils.randomAlphabetic(10));
		data.put("state", RandomStringUtils.randomAlphabetic(10));
		data.put("country", "Vietnam");
		data.put("partnerships", AddCompany.PARTNERSHIP_LIST[0]);
		/*
		 * Create company
		 */
		home.addCompany(AddCompany.getHash(), data);
		// 2. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 3. Select a company from companies list
		home.selectACompanyByName(companyName);
		// 4. Click “Suspend” link in 061Da Company info page
		home.click(CompanyInfo.SUSPEND);
		// 5. Click “Suspend” button on delete confirmation dialog
		home.switchWindowClickOption("Suspend");
		/*
		 * VP. Verify that the portal is redirected to companies list page
		 */
		Assert.assertEquals(home.existsElement(Companies.getListElements()).getResult(), "Pass");
		/*
		 * Verify that The suspended company is displayed in Suspended list
		 */
		home.selectFilterByName(Companies.FILTER, filter);
		Assert.assertTrue(home.checkACompanyExistByName( companyName));
		// 6. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 7. Click “Add Product” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 8. List out the “Company” combobox
		ArrayList<String> companyList = home.getAllComboboxOption(AddAccessory.COMPANY);
		/*
		 * Verify that the Suspended company is not displayed in “Company” combobox
		 */
		Assert.assertFalse(DTSUtil.checkListContain(companyList, companyName));
		// Delete company
		home.click(Xpath.LINK_COMPANY);
		home.selectOptionByName(Companies.FILTER, filter);
		home.deleteACompanyByName(companyName);
	}
	
	/*
	 * Verify that when a company is Suspended , the associated app/device are also Suspended
	 */
	@Test
	public void TC061DaCI_09() {
		result.addLog("ID TC061DaCI_09 : Verify that when a company is Suspended , the associated app/device are also Suspended");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to “Companies” page
			3. Select a company from companies list
			4. Click “Suspend” link in 061Da Company info page
			5. Click “Suspend” button on Suspended confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the deleted company is displayed in Suspended list
			6. Navigate to “Apps & Devices” page
			7. List out the “Company” filter combobox
		*/
		/*
		 * PreCondition: Create new company which has at lease one app/device
		 */
		// Navigate to Companies page
		home.click(Xpath.LINK_COMPANY);
		// Click Add company link
		home.click(Companies.ADD_COMPANY);
		// Create new company
		Hashtable<String,String> dataCompany = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), dataCompany);
		// Create user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		home.addUser(AddUser.getPartnerUser(), dataUser);
		// Create brand
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		home.addBrand(AddBrand.getHash(), dataBrand);
		// Navigate to App & Device page
		home.click(Xpath.linkDevice);
		// Click Add app or device link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> dataDevice = TestData.appDeviceDraft();
		dataDevice.put("company", dataCompany.get("name"));
		dataDevice.put("brand", dataBrand.get("name"));
		home.createNewDevice(DeviceEdit.getHash(), dataDevice);
		/*
		 * **************************************************************
		 */
		// 2. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 3. Select a company from companies list
		home.selectACompanyByName(dataCompany.get("name"));
		// 4. Click “Suspend” link in 061Da Company info page
		home.click(CompanyInfo.SUSPEND);
		// 5. Click “Suspend” button on Suspend confirmation dialog
		home.selectConfirmationDialogOption("Suspend");
		/*
		 * VP. Verify that the portal is redirected to companies list page and the Suspended company is displayed in Suspended list
		 */
		Assert.assertEquals(home.existsElement(Companies.getListElements()).getResult(), "Pass");
		home.selectOptionByName(Companies.FILTER, "Suspended");
		Assert.assertTrue(home.checkACompanyExistByName( dataCompany.get("name")));
		// 6. Navigate to “Apps & Devices” page
		home.click(Xpath.linkDevice);
		// 7. List out the “Company” filter combobox
		ArrayList<String> companyList = home.getAllComboboxOption(DeviceList.COMPANY_LIST);
		/*
		 * Verify that the Suspended company is not displayed in “Companty filter combobox and associated app/device of the Suspended company are also suspended
		 */
		Assert.assertFalse(DTSUtil.checkListContain(companyList, dataCompany.get("name")));
		// Check if PDPP-1313 found
		if(!home.checkAnAppDeviceExistByName(dataDevice.get("name"))){
			result.addErrorLog("PDPP-1313 - 030D Catalog All Active Models: Products of suspended company disappear even in 'Suspened' list");
			Assert.assertTrue(false);
		}
		Assert.assertFalse(home.checkAnAppDeviceExistByName(dataDevice.get("name")));
		// Delete company
		home.click(Xpath.LINK_COMPANY);
		home.selectOptionByName(Companies.FILTER, "Suspended");
		home.deleteACompanyByName(dataCompany.get("name"));
	}
	
	/*
	 * Verify that the Suspended company is not displayed in Company combobox when adding new app/device
	 */
	@Test
	public void TC061DaCI_10() {
		result.addLog("ID TC061DaCI_10 : Verify that the Suspended company is not displayed in Company combobox when adding new app/device");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to “Companies” page
			3. Select a company from companies list
			4. Click “Suspend” link in 061Da Company info page
			5. Click “Suspend” button on Suspended confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the Suspended company is displayed in Suspended list
			6. Navigate to “Apps & Devices” page
			7. Click “Add App or Device” link
			8. List out the “Company” filter combobox
		*/
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		// Navigate to company page
		home.click(Xpath.LINK_COMPANY);
		// Click Add company link
		home.click(Companies.ADD_COMPANY);
		/*
		 * Init data
		 */
		Hashtable<String,String> data = new Hashtable<String,String>();
		String companyName = "Test" + RandomStringUtils.randomNumeric(5);
		String date = DateUtil.getADateGreaterThanToday("MMMM d, yyyy", 3);
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("type", "Partner");
		data.put("date", date);
		data.put("name", companyName);
		data.put("address", RandomStringUtils.randomAlphanumeric(20));
		data.put("city", RandomStringUtils.randomAlphabetic(10));
		data.put("state", RandomStringUtils.randomAlphabetic(10));
		data.put("country", "Vietnam");
		data.put("partnerships", AddCompany.PARTNERSHIP_LIST[0]);
		/*
		 * Create company
		 */
		home.addCompany(AddCompany.getHash(), data);
		// 2. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 3. Select a company from companies list
		home.selectACompanyByName(companyName);
		// 4. Click “Suspend” link in 061Da Company info page
		home.click(CompanyInfo.SUSPEND);
		// 5. Click “Suspend” button on delete confirmation dialog
		home.switchWindowClickOption("Suspend");
		/*
		 * VP. Verify that the portal is redirected to companies list page
		 */
		Assert.assertEquals(home.existsElement(Companies.getListElements()).getResult(), "Pass");
		/*
		 * Verify that The suspended company is displayed in Suspended list
		 */
		home.selectFilterByName(Companies.FILTER, filter);
		Assert.assertTrue(home.checkACompanyExistByName( companyName));
		// 6. Navigate to “Apps & Devices” page
		home.click(Xpath.LINK_APP_DEVICES);
		// 7. Click “Add App or Device” link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 8. List out the “Company” filter combobox
		ArrayList<String> companyList = home.getAllComboboxOption(DeviceEdit.COMPANY);
		/*
		 * Verify that the Suspended company is not displayed in “Company” combobox
		 */
		Assert.assertFalse(DTSUtil.checkListContain(companyList, companyName));
		// Delete company
		home.click(Xpath.LINK_COMPANY);
		home.selectOptionByName(Companies.FILTER, filter);
		home.deleteACompanyByName(companyName);
	}
	
	/*
	 * Verify that when a suspended company is restored , the associated users are also restored
	 */
	@Test
	public void TC061DaCI_11() {
		result.addLog("ID TC061DaCI_11 : Verify that when a suspended company is restored , the associated users are also restored");
		result.addErrorLog("PDPP-1268: 630 Product Device detail: the associated products of restored company are not restored.");
		/*
		Pre-condition: Company has at least one brand and one user
		
		1. Log into DTS portal as a DTS user
		2. Navigate to “Companies” page
		3. Select a company from companies list
		4. Click “Suspend” link in 061Da Company info page
		5. Click “Suspend” button on Suspend confirmation dialog
		VP. Verify that the portal is redirected to companies list page and the Suspended company is displayed in Suspended list
		6. Navigate to “Users” page
		VP: Verify that the  associated user of the suspended company are not displayed in user list.
		7. Navigate to “Companies” page
		8. Filter the Suspended companies
		9. Select suspended company above
		10. Click “Restore” link
		11. Perform step 6 to 7 again
		VP: Verify that the associated users of the restored company are  displayed in user list again
		*/
				
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		
		//Create new company
		home.click(Xpath.LINK_COMPANY);
		home.click(Companies.ADD_COMPANY);
		Hashtable<String,String> data_company= TestData.validCompany();
		home.addCompany(AddCompany.getHash(), data_company);
		// Create new user
		Hashtable<String,String> data_user =TestData.partnerUser();
		home.addUser(AddUser.getPartnerUser(), data_user);
		// Get user info
		home.click(Xpath.LINK_USERS);
		// Verify that the new created user is displayed in "Invited" users list but not in "Suspended" list
		home.selectOptionByName(UsersList.FILTER_USER, UsersList.company_filter[2]);
		Assert.assertTrue(home.checkUserExistByEmail(data_user.get("email")));
		home.selectOptionByName(UsersList.FILTER_USER, UsersList.company_filter[3]);
		Assert.assertFalse(home.checkUserExistByEmail(data_user.get("email")));
		// Navigate to company page
		home.click(Xpath.LINK_COMPANY);
		// select company from companies list
		home.selectACompanyByName(data_company.get("name"));
		// Suspend this company
		home.click(CompanyInfo.SUSPEND);
		home.selectConfirmationDialogOption("Suspend");
		
		//VP. Verify that the portal is redirected to companies list page and the Suspend company is displayed in Suspended list
		Assert.assertTrue(home.isElementPresent(Companies.LABEL));
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Verify that the new created user is not displayed in "Invited" users list but is displayed in "Suspended" list
		home.selectOptionByName(UsersList.FILTER_USER, UsersList.company_filter[2]);
		Assert.assertFalse(home.checkUserExistByEmail(data_user.get("email")));
		home.selectOptionByName(UsersList.FILTER_USER, UsersList.company_filter[3]);
		Assert.assertTrue(home.checkUserExistByEmail(data_user.get("email")));
//		7. Navigate to “Companies” page
//		8. Filter the Suspended companies
//		9. Select suspended company above
//		10. Click “Restore” link
//		11. Perform step 6 to 7 again
		
		home.click(Xpath.LINK_COMPANY);
		home.selectOptionByName(Companies.FILTER,Companies.option[8]);
		home.selectACompanyByName(data_company.get("name"));
		home.click(CompanyInfo.RESTORE);
		home.selectConfirmationDialogOption("Restore");
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Verify that the new created user is displayed in "Invited" users list but not in "Suspended" list
		home.selectOptionByName(UsersList.FILTER_USER, UsersList.company_filter[2]);
		Assert.assertTrue(home.checkUserExistByEmail(data_user.get("email")));
		home.selectOptionByName(UsersList.FILTER_USER, UsersList.company_filter[3]);
		Assert.assertFalse(home.checkUserExistByEmail(data_user.get("email")));
		//Verify user status
		home.selectUserByEmail(data_user.get("email"));
		Assert.assertTrue(home.getTextByXpath(UserMgmt.STATUS).contains(UsersList.company_filter[2]));
		
		//Teardown: delete this company
		home.click(Xpath.LINK_COMPANY);
		home.selectACompanyByName(data_company.get("name"));
		home.doDelete(CompanyInfo.DELETE);
	}
	
	
	/*
	 * Verify that the restored company is displayed in Company combobox again when adding new users
	 */
	@Test
	public void TC061DaCI_12() {
		result.addLog("ID TC061DaCI_12 : Verify that the restored company is displayed in Company combobox again when adding new users");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to “Companies” page
			3. Select a company from companies list
			4. Click “Suspend” link in 061Da Company info page
			5. Click “Suspend” button on delete confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the deleted company is displayed in Suspended list
			6. Navigate to “Users” page
			7. Click “Add New User” link
			8. List out the “Company” compbobox
			VP: Verify that the deleted company is not displayed in “Company” combobox
			9. Navigate to “Companies” page
			10. Filter the Suspended companies
			11. Select suspended company above
			12. Click “Restore” link
			13. Perform step 6 to 7 again
		*/
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		// Navigate to company page
		home.click(Xpath.LINK_COMPANY);
		// Click Add company link
		home.click(Companies.ADD_COMPANY);
		/*
		 * Init data
		 */
		Hashtable<String,String> data = new Hashtable<String,String>();
		String companyName = "Test" + RandomStringUtils.randomNumeric(5);
		String date = DateUtil.getADateGreaterThanToday("MMMM d, yyyy", 3);
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("type", "Partner");
		data.put("date", date);
		data.put("name", companyName);
		data.put("address", RandomStringUtils.randomAlphanumeric(20));
		data.put("city", RandomStringUtils.randomAlphabetic(10));
		data.put("state", RandomStringUtils.randomAlphabetic(10));
		data.put("country", "Vietnam");
		data.put("partnerships", AddCompany.PARTNERSHIP_LIST[0]);
		/*
		 * Create company
		 */
		home.addCompany(AddCompany.getHash(), data);
		// 2. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 3. Select a company from companies list
		home.selectACompanyByName(companyName);
		// 4. Click “Suspend” link in 061Da Company info page
		home.click(CompanyInfo.SUSPEND);
		// 5. Click “Suspend” button on delete confirmation dialog
		home.switchWindowClickOption("Suspend");
		/*
		 * VP. Verify that the portal is redirected to companies list page
		 */
		Assert.assertEquals(home.existsElement(Companies.getListElements()).getResult(), "Pass");
		/*
		 * Verify that The suspended company is displayed in Suspended list
		 */
		home.selectFilterByName(Companies.FILTER, filter);
		Assert.assertTrue(home.checkACompanyExistByName( companyName));
		// 6. Navigate to “Users” page
		home.click(Xpath.LINK_USERS);
		// 7. Click “Add New User” link
		home.click(UsersList.ADD_USER);
		// 8. List out the “Company” compbobox
		ArrayList<String> companyList = home.getAllComboboxOption(AddUser.BTN_COMPANY);
		/*
		 * VP: Verify that the suspended company is not displayed in “Company” combobox
		 */
		Assert.assertFalse(DTSUtil.checkListContain(companyList, companyName));
		// 9. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 10. Filter the Suspended companies
		home.selectOptionByName(Companies.FILTER, filter);
		// 11. Select suspended company above
		home.selectACompanyByName(companyName);
		// 12. Click “Restore” link
		home.click(CompanyInfo.RESTORE);
		home.switchWindowClickOption("Restore");
		// 13. Perform step 6 to 7 again
		// Navigate to user page
		home.click(Xpath.LINK_USERS);
		// Click “Add New User” link
		home.click(UsersList.ADD_USER);
		/*
		 * Verify that the restored company is displayed in “Company” combobox again
		 */
		companyList = home.getAllComboboxOption(AddUser.BTN_COMPANY);
		Assert.assertTrue(DTSUtil.checkListContain(companyList, companyName));
		// Delete company
		home.click(Xpath.LINK_COMPANY);
		home.deleteACompanyByName(companyName);
	}
	
	/*
	 * Verify that when a suspended company is restored , the associated Products are also restored.
	 */
	@Test
	public void TC061DaCI_13() {
		result.addLog("ID TC061DaCI_13 : Verify that when a suspended company is restored , the associated Products are also restored.");
		result.addErrorLog("PDPP-1268: 630 Product Device detail: the associated products of restored company are not restored.");
		/*
		Pre-condition: Company has at least one brand and one user
		
		1. Log into DTS portal as a DTS user
		2. Navigate to “Companies” page
		3. Select a company from companies list
		4. Click “Suspend” link in 061Da Company info page
		5. Click “Suspend” button on Suspend confirmation dialog
		VP. Verify that the portal is redirected to companies list page and the Suspend company is displayed in Suspended list
		6. Navigate to “Products” page
		7. List out the “Brand” filter combobox
		VP: Verify that the brand of Suspended company is not displayed in “Brands” filter combobox and associated product of the Suspended company are not displayed in Products list.
		8. Navigate to “Companies” page
		9. Filter the Suspended companies
		10. Select suspended company above
		11. Click “Restore” link
		12. Perform step 6 to 7 again
		VP: Verify that the brand of restored company is  displayed in “Brands” filter combobox and associated product of the restored company are displayed in Products list.
		*/
		
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		
		//Create new company
		home.click(Xpath.LINK_COMPANY);
		home.click(Companies.ADD_COMPANY);
		Hashtable<String,String> data_company= TestData.validCompany();
		home.addCompany(AddCompany.getHash(), data_company);
		// Create new user
		Hashtable<String,String> data_user =TestData.partnerUser();
		home.addUser(AddUser.getPartnerUser(), data_user);
		// Create new brand
		Hashtable<String,String> data_brand = TestData.brandDraft();
		home.addBrand(AddBrand.getHash(), data_brand);
		
		String company = data_company.get("name");
		String brand = data_brand.get("name");
		
		// Create new product
		home.click(Xpath.linkAccessories);
		Hashtable<String,String> data_product = TestData.accessoryDraft();
		data_product.remove("company");
		data_product.remove("brand");
		data_product.put("company", company );
		data_product.put("brand", brand);
		home.addAccessoriesPartner(AddAccessory.getHash(), data_product);
		String product_name = data_product.get("name");
				
		// Navigate to company page
		home.click(Xpath.LINK_COMPANY);
		// select company of above product
		home.selectACompanyByName(company);
		// Suspend this company
		home.click(CompanyInfo.SUSPEND);
		home.selectConfirmationDialogOption("Suspend");
		
		//VP. Verify that the portal is redirected to companies list page and the Suspend company is displayed in Suspended list
		Assert.assertTrue(home.isElementPresent(Companies.LABEL));
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// VP: Verify that the associated product of the Suspended company are not displayed in Active Products list but it's displayed in Suspended list
		//Verify that the associated product is not displayed in active product list
		Assert.assertFalse(home.checkAnAccessoryExistByName(product_name));
		// Filter the products list as "Suspended"
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, Constant.HOME_SUSPENDED);
		// Verify that the associated product is displayed in suspended list
		Assert.assertTrue(home.checkAnAccessoryExistByName(product_name));
//		10. Select suspended company above
//		11. Click “Restore” link
//		12. Perform step 6 to 7 again
		home.click(Xpath.LINK_COMPANY);
		home.selectOptionByName(Companies.FILTER,Companies.option[8]);
		home.selectACompanyByName(company);
		home.click(CompanyInfo.RESTORE);
		home.selectConfirmationDialogOption("Restore");
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		//Verify that the brand of restored company is  displayed in “Brands” filter combobox and associated product of the restored company are displayed in Products list.
		//Verify that the associated product is not displayed in active product list
		Assert.assertTrue(home.checkAnAccessoryExistByName(product_name));
		// Filter the products list as "Suspended"
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, Constant.HOME_SUSPENDED);
		// Verify that the associated product is displayed in suspended list
		Assert.assertFalse(home.checkAnAccessoryExistByName(product_name));
		
		//Teardown: Delete company
		home.click(Xpath.LINK_COMPANY);
		home.selectACompanyByName(company);
		home.doDelete(CompanyInfo.DELETE);
		
	}
	
	/*
	 * Verify that the restored company is displayed in Company combobox again when adding new products
	 */
	@Test
	public void TC061DaCI_14() {
		result.addLog("ID TC061DaCI_14 : Verify that the restored company is displayed in Company combobox again when adding new products");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to “Companies” page
			3. Select a company from companies list
			4. Click “Suspend” link in 061Da Company info page
			5. Click “Suspend” button on Suspend confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the deleted company is displayed in Suspended list
			6. Navigate to “Products” page
			7. Click “Add Product” link
			8. List out the “Company” combobox
			VP: Verify that the Suspended company is not displayed in “Company” combobox
			9. Navigate to “Companies” page
			10. Filter the Suspended companies
			11. Select suspended company above
			12. Click “Restore” link
			13. Perform step 6 to 7 again
		*/
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		// Navigate to company page
		home.click(Xpath.LINK_COMPANY);
		// Click Add company link
		home.click(Companies.ADD_COMPANY);
		//Create company
		Hashtable<String,String> dataCompany = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), dataCompany);
		// Create user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		home.addUser(AddUser.getPartnerUser(), dataUser);
		// Create brand
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		home.addBrand(AddBrand.getHash(), dataBrand);
		/*
		 * ***************************************************************
		 */
		// 2. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 3. Select a company from companies list
		home.selectACompanyByName(dataCompany.get("name"));
		// 4. Click “Suspend” link in 061Da Company info page
		home.click(CompanyInfo.SUSPEND);
		// 5. Click “Suspend” button on delete confirmation dialog
		home.selectConfirmationDialogOption("Suspend");
		/*
		 * VP. Verify that the portal is redirected to companies list page
		 */
		Assert.assertEquals(home.existsElement(Companies.getListElements()).getResult(), "Pass");
		/*
		 * Verify that The suspended company is displayed in Suspended list
		 */
		home.selectFilterByName(Companies.FILTER, filter);
		Assert.assertTrue(home.checkACompanyExistByName( dataCompany.get("name")));
		// 6. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 7. Click “Add Product” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 8. List out the “Company” combobox
		ArrayList<String> companyList = home.getAllComboboxOption(AddAccessory.COMPANY);
		/*
		 * VP: Verify that the Suspended company is not displayed in “Company” combobox
		 */
		Assert.assertFalse(DTSUtil.checkListContain(companyList, dataCompany.get("name")));
		// 9. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 10. Filter the Suspended companies
		home.selectFilterByName(Companies.FILTER, filter);
		// 11. Select suspended company above
		home.selectACompanyByName(dataCompany.get("name"));
		// 12. Click “Restore” link
		home.click(CompanyInfo.RESTORE);
		home.selectConfirmationDialogOption("Restore");
		// 13. Perform step 6 to 7 again
		// Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// Click “Add Product” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * Verify that the restored company is displayed in “Company” combobox
		 */
		companyList = home.getAllComboboxOption(AddAccessory.COMPANY);
		Assert.assertTrue(DTSUtil.checkListContain(companyList, dataCompany.get("name")));
		// Delete company
		home.click(Xpath.LINK_COMPANY);
		home.deleteACompanyByName(dataCompany.get("name"));
	}
	
	/*
	 * Verify that when a suspended company is restored , the associated app/device are also restored.
	 */
	@Test
	public void TC061DaCI_15() {
		result.addLog("ID TC061DaCI_15 : Verify that when a suspended company is restored , the associated app/device are also restored.");
		/*
		Pre-condition: Company has at least one brand and one user

		1. Log into DTS portal as a DTS user
		2. Navigate to “Companies” page
		3. Select a company from companies list
		4. Click “Suspend” link in 061Da Company info page
		5. Click “Suspend” button on Suspended confirmation dialog
		VP. Verify that the portal is redirected to companies list page and the deleted company is displayed in Suspended list
		6. Navigate to “Apps & Devices” page
		7. List out the “Company” filter combobox
		VP: Verify that the Suspended company is not displayed in “Companty filter combobox and associated app/device of the Suspended company are not displayed in apps/devices list.
		8. Navigate to “Companies” page
		9. Filter the Suspended companies
		10. Select suspended company above
		11. Click “Restore” link
		12. Perform step 6 to 7 again
		VP: Verify that the restored company is displayed in “Companty filter combobox and associated app/device of the restored company are also displayed in apps/devices list again.
		*/
		
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		
		//Create new company
		home.click(Xpath.LINK_COMPANY);
		home.click(Companies.ADD_COMPANY);
		Hashtable<String,String> data_company= TestData.validCompany();
		home.addCompany(AddCompany.getHash(), data_company);
		// Create new user
		Hashtable<String,String> data_user =TestData.partnerUser();
		home.addUser(AddUser.getPartnerUser(), data_user);
		// Create new brand
		Hashtable<String,String> data_brand = TestData.brandDraft();
		home.addBrand(AddBrand.getHash(), data_brand);
		
		String company = data_company.get("name");
		String brand = data_brand.get("name");
				
		//Create new app/device
		home.click(Xpath.LINK_APP_DEVICES);
		home.click(DeviceList.CREATE_NEW_DEVICE);
		Hashtable<String,String> data_appdevice = TestData.appDeviceDraft();
		data_appdevice.remove("company");
		data_appdevice.remove("brand");
		data_appdevice.put("company", company);
		data_appdevice.put("brand", brand);
		home.createNewDevice(DeviceEdit.getHash(), data_appdevice);
		
		String data_device = data_appdevice.get("name");
		
		// Navigate to company page
		home.click(Xpath.LINK_COMPANY);
		// select company of above product
		home.selectACompanyByName(company);
		// Suspend this company
		home.click(CompanyInfo.SUSPEND);
		home.selectConfirmationDialogOption("Suspend");
		
		//VP. Verify that the portal is redirected to companies list page and the Suspend company is displayed in Suspended list
		Assert.assertTrue(home.isElementPresent(Companies.LABEL));
		// Navigate to app/device page
		home.click(Xpath.LINK_APP_DEVICES);
		// Check if PDPP-1313 found
		if(!home.checkAnAppDeviceExistByName(data_appdevice.get("name"))){
			result.addErrorLog("PDPP-1313 - 030D Catalog All Active Models: Products of suspended company disappear even in 'Suspened' list");
			Assert.assertTrue(false);
		}
		// VP: Verify that the associated product of the Suspended company are not displayed in Active Products list but it's displayed in Suspended list
		// Verify that the associated product is not displayed in active product list
		Assert.assertFalse(home.checkAnAppDeviceExistByName(data_device));
		// 8. Navigate to “Companies” page
		// 9. Filter the Suspended companies
		// 10. Select suspended company above
		// 11. Click “Restore” link
		// 12. Perform step 6 to 7 again
		home.click(Xpath.LINK_COMPANY);
		home.selectOptionByName(Companies.FILTER,Companies.option[8]);
		home.selectACompanyByName(company);
		home.click(CompanyInfo.RESTORE);
		home.selectConfirmationDialogOption("Restore");
		// Navigate to app/device page
		home.click(Xpath.LINK_APP_DEVICES);
//		Verify that the associated app/device of the restored company are also displayed in apps/devices list again.
		Assert.assertTrue(home.checkAnAppDeviceExistByName(data_device));
				
		//Teardown: Delete company
		home.click(Xpath.LINK_COMPANY);
		home.selectACompanyByName(company);
		home.doDelete(CompanyInfo.DELETE);
		
	}
	
	/*
	 * Verify that the restored company is displayed in Company combobox again when adding new app/device
	 */
	@Test
	public void TC061DaCI_16() {
		result.addLog("ID TC061DaCI_16 : Verify that the restored company is displayed in Company combobox again when adding new app/device");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to “Companies” page
			3. Select a company from companies list
			4. Click “Suspend” link in 061Da Company info page
			5. Click “Suspend” button on Suspended confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the Suspended company is displayed in Suspended list
			6. Navigate to “Apps & Devices” page
			7. Click “Add App or Device” link
			8. List out the “Company” filter combobox
			VP: Verify that the Suspended company is not displayed in “Company” combobox
			9. Navigate to “Companies” page
			10. Filter the Suspended companies
			11. Select suspended company above
			12. Click “Restore” link
			13. Perform step 6 to 7 again
		*/
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		// Navigate to company page
		home.click(Xpath.LINK_COMPANY);
		// Click Add company link
		home.click(Companies.ADD_COMPANY);
		// Create company
		Hashtable<String,String> dataCompany = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), dataCompany);
		// Create user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		home.addUser(AddUser.getPartnerUser(), dataUser);
		// Create brand
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		home.addBrand(AddBrand.getHash(), dataBrand);
		// 2. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 3. Select a company from companies list
		home.selectACompanyByName(dataCompany.get("name"));
		// 4. Click “Suspend” link in 061Da Company info page
		home.click(CompanyInfo.SUSPEND);
		// 5. Click “Suspend” button on delete confirmation dialog
		home.selectConfirmationDialogOption("Suspend");
		/*
		 * VP. Verify that the portal is redirected to companies list page
		 */
		Assert.assertEquals(home.existsElement(Companies.getListElements()).getResult(), "Pass");
		/*
		 * Verify that The suspended company is displayed in Suspended list
		 */
		home.selectFilterByName(Companies.FILTER, filter);
		Assert.assertTrue(home.checkACompanyExistByName( dataCompany.get("name")));
		// 6. Navigate to “Apps & Devices” page
		home.click(Xpath.LINK_APP_DEVICES);
		// 7. Click “Add App or Device” link		
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 8. List out the “Company” filter combobox
		ArrayList<String> companyList = home.getAllComboboxOption(DeviceEdit.COMPANY);
		/*
		 * VP: Verify that the Suspended company is not displayed in “Company” combobox
		 */
		Assert.assertFalse(DTSUtil.checkListContain(companyList, dataCompany.get("name")));
		// 9. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 10. Filter the Suspended companies
		home.selectFilterByName(Companies.FILTER, filter);
		// 11. Select suspended company above
		home.selectACompanyByName(dataCompany.get("name"));
		// 12. Click “Restore” link
		home.click(CompanyInfo.RESTORE);
		home.selectConfirmationDialogOption("Restore");
		// 13. Perform step 6 to 7 again
		// Navigate to “Apps & Devices” page
		home.click(Xpath.LINK_APP_DEVICES);
		// Click “Add App or Device” link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * Verify that the restored company is displayed in “Company” combobox again
		 */
		companyList = home.getAllComboboxOption(DeviceEdit.COMPANY);
		Assert.assertTrue(DTSUtil.checkListContain(companyList, dataCompany.get("name")));
		// Delete company
		home.click(Xpath.LINK_COMPANY);
		home.deleteACompanyByName(dataCompany.get("name"));
	}
	
	/*
	 * Verify that when a company is deleted , the associated users are also deleted
	 */
	@Test
	public void TC061DaCI_17() {
		result.addLog("ID TC061DaCI_17 : Verify that when a company is deleted , the associated users are also deleted");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to “Companies” page
			3. Select a company from companies list
			4. Click “Delete” link in 061Da Company info page
			5. Click “Delete” button on delete confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the deleted company is not displayed in companies list
			6. Navigate to “Users” page
			7. List out the “Company” filter combobox
		*/
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		// Navigate to Companies page
		home.click(Xpath.LINK_COMPANY);
		// Click Add company link
		home.click(Companies.ADD_COMPANY);
		// Create new company
		Hashtable<String,String> dataCompany = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), dataCompany);
		// Create user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		home.addUser(AddUser.getPartnerUser(), dataUser);
		/*
		 * **********************************************************
		 */
		// 2. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 3. Select a company from companies list
		home.selectACompanyByName(dataCompany.get("name"));
		// 4. Click “Delete” link in 061Da Company info page
		// 5. Click “Delete” button on delete confirmation dialog
		home.doDelete(CompanyInfo.DELETE);
		/*
		 * VP. Verify that the portal is redirected to companies list page and the deleted company is not displayed in companies list
		 */
		Assert.assertEquals(home.existsElement(Companies.getListElements()).getResult(), "Pass");
		Assert.assertFalse(home.checkACompanyExistByName( dataCompany.get("name")));
		// 6. Navigate to “Users” page
		home.click(Xpath.LINK_USERS);
		// 7. List out the “Company” filter combobox
		ArrayList<String> companyList = home.getAllComboboxOption(UsersList.FILTER_COMPANY);
		/*
		 * Verify that the deleted company is not displayed in “Company” filter combobox and  associated user of the deleted company are not displayed in user list
		 */
		Assert.assertFalse(DTSUtil.checkListContain(companyList, dataCompany.get("name")));
		Assert.assertFalse(home.checkUserExistByEmail(dataUser.get("email")));
	}
	
	/*
	 * Verify that the deleted company is not displayed in Company combobox when adding new users
	 */
	@Test
	public void TC061DaCI_18() {
		result.addLog("ID TC061DaCI_18 : Verify that the deleted company is not displayed in Company combobox when adding new users");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to “Companies” page
			3. Select a company from companies list
			4. Click “Delete” link in 061Da Company info page
			5. Click “Delete” button on delete confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the deleted company is not displayed in companies list
			6. Navigate to “Users” page
			7. Click “Add New User” link
			8. List out the “Company” compbobox
		*/
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		// Navigate to company page
		home.click(Xpath.LINK_COMPANY);
		// Click Add company link
		home.click(Companies.ADD_COMPANY);
		/*
		 * Init data
		 */
		Hashtable<String,String> data = new Hashtable<String,String>();
		String companyName = "Test" + RandomStringUtils.randomNumeric(5);
		String date = DateUtil.getADateGreaterThanToday("MMMM d, yyyy", 3);
		data.put("id", RandomStringUtils.randomNumeric(10));
		data.put("type", "Partner");
		data.put("date", date);
		data.put("name", companyName);
		data.put("address", RandomStringUtils.randomAlphanumeric(20));
		data.put("city", RandomStringUtils.randomAlphabetic(10));
		data.put("state", RandomStringUtils.randomAlphabetic(10));
		data.put("country", "Vietnam");
		data.put("partnerships", AddCompany.PARTNERSHIP_LIST[0]);
		/*
		 * Create company
		 */
		home.addCompany(AddCompany.getHash(), data);
		// 2. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 3. Select a company from companies list
		home.selectACompanyByName(companyName);
		// 4. Click “Delete” link in 061Da Company info page
		// 5. Click “Delete” button on delete confirmation dialog
		home.doDelete(CompanyInfo.DELETE);
		/*
		 * VP. Verify that the portal is redirected to companies list page
		 */
		Assert.assertEquals(home.existsElement(Companies.getListElements()).getResult(), "Pass");
		/*
		 * Verify that The deleted company is not displayed in Companies list
		 */
		Assert.assertFalse(home.checkACompanyExistByName( companyName));
		// 6. Navigate to “Users” page
		home.click(Xpath.LINK_USERS);
		// 7. Click “Add New User” link
		home.click(UsersList.ADD_USER);
		// 8. List out the “Company” compbobox
		ArrayList<String> companyList = home.getAllComboboxOption(AddUser.BTN_COMPANY);
		/*
		 * Verify that the deleted company is not displayed in “Company” combobox
		 */
		Assert.assertFalse(DTSUtil.checkListContain(companyList, companyName));
	}
	
	/*
	 * Verify that when a company is deleted , the associated Products are also deleted
	 */
	@Test
	public void TC061DaCI_19() {
		result.addLog("ID TC061DaCI_19 : Verify that when a company is deleted , the associated Products are also deleted");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to “Companies” page
			3. Select a company from companies list
			4. Click “Delete” link in 061Da Company info page
			5. Click “Delete” button on delete confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the deleted company is not displayed in companies list
			6. Navigate to “Products” page
			7. List out the “Brand” filter combobox
		*/
		/*
		 * PreCondition: Create new company which has at lease one product
		 */
		// Navigate to Companies page
		home.click(Xpath.LINK_COMPANY);
		// Click Add company link
		home.click(Companies.ADD_COMPANY);
		// Create new company
		Hashtable<String,String> dataCompany = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), dataCompany);
		// Create user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		home.addUser(AddUser.getPartnerUser(), dataUser);
		// Create brand
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		home.addBrand(AddBrand.getHash(), dataBrand);
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> dataProduct = TestData.accessoryDraft();
		dataProduct.put("company", dataCompany.get("name"));
		dataProduct.put("brand", dataBrand.get("name"));
		home.addAccessoriesPartner(AddAccessory.getHash(), dataProduct);
		/*
		 * **************************************************************
		 */
		// 2. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 3. Select a company from companies list
		home.selectACompanyByName(dataCompany.get("name"));
		// 4. Click “Delete” link in 061Da Company info page
		// 5. Click “Delete” button on delete confirmation dialog
		home.doDelete(CompanyInfo.DELETE);
		/*
		 * VP. Verify that the portal is redirected to companies list page and the deleted company is not displayed in companies list
		 */
		Assert.assertEquals(home.existsElement(Companies.getListElements()).getResult(), "Pass");
		Assert.assertFalse(home.checkACompanyExistByName( dataCompany.get("name")));
		// 6. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 7. List out the “Brand” filter combobox
		ArrayList<String> brandList = home.getAllComboboxOption(AccessoryMain.BRAND_FILTER);
		/*
		 * Verify that the brand of deleted company is not displayed in “Brands” filter combobox and associated product of the deleted company are not displayed in Products list
		 */
		Assert.assertFalse(DTSUtil.checkListContain(brandList, dataBrand.get("name")));
		Assert.assertFalse(home.checkAnAccessoryExistByName(dataProduct.get("name")));
	}
	
	/*
	 * Verify that the deleted company is not displayed in Company combobox when adding new products
	 */
	@Test
	public void TC061DaCI_20() {
		result.addLog("ID TC061DaCI_20 : Verify that the deleted company is not displayed in Company combobox when adding new products");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to “Companies” page
			3. Select a company from companies list
			4. Click “Delete” link in 061Da Company info page
			5. Click “Delete” button on delete confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the deleted company is not displayed in companies list
			6. Navigate to “Products” page
			7. Click “Add Product” link
			8. List out the “Company” combobox
		*/
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		// Navigate to company page
		home.click(Xpath.LINK_COMPANY);
		// Click Add company link
		home.click(Companies.ADD_COMPANY);
		 // Create company
		Hashtable<String,String> data = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), data);
		// Create user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		home.addUser(AddUser.getPartnerUser(), dataUser);
		// Create brand
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		home.addBrand(AddBrand.getHash(), dataBrand);
		/*
		 * *****************************************************************
		 */
		// 2. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 3. Select a company from companies list
		String companyName = data.get("name");
		home.selectACompanyByName(companyName);
		// 4. Click “Delete” link in 061Da Company info page
		// 5. Click “Delete” button on delete confirmation dialog
		home.doDelete(CompanyInfo.DELETE);
		home.waitForElementClickable(Companies.FILTER);
		/*
		 * VP. Verify that the portal is redirected to companies list page
		 */
		Assert.assertEquals(home.existsElement(Companies.getListElements()).getResult(), "Pass");
		/*
		 * Verify that The deleted company is not displayed in Companies list
		 */
		Assert.assertFalse(home.checkACompanyExistByName( companyName));
		// 6. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 7. Click “Add Product” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 8. List out the “Company” combobox
		ArrayList<String> companyList = home.getAllComboboxOption(AddAccessory.COMPANY);
		/*
		 * Verify that the deleted company is not displayed in “Company” combobox
		 */
		Assert.assertFalse(DTSUtil.checkListContain(companyList, companyName));
	}
	
	/*
	 * Verify that when a company is deleted , the associated app/device are also deleted
	 */
	@Test
	public void TC061DaCI_21() {
		result.addLog("ID TC061DaCI_21 : Verify that when a company is deleted , the associated app/device are also deleted");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to “Companies” page
			3. Select a company from companies list
			4. Click “Delete” link in 061Da Company info page
			5. Click “Delete” button on delete confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the deleted company is not displayed in companies list
			6. Navigate to “Apps & Devices” page
			7. List out the “Company” filter combobox
		*/
		/*
		 * PreCondition: Create new company which has at lease one app/device
		 */
		// Navigate to Companies page
		home.click(Xpath.LINK_COMPANY);
		// Click Add company link
		home.click(Companies.ADD_COMPANY);
		// Create new company
		Hashtable<String,String> dataCompany = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), dataCompany);
		// Create user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		home.addUser(AddUser.getPartnerUser(), dataUser);
		// Create brand
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		home.addBrand(AddBrand.getHash(), dataBrand);
		// Navigate to App & Device page
		home.click(Xpath.linkDevice);
		// Click Add app or device link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> dataDevice = TestData.appDeviceDraft();
		dataDevice.put("company", dataCompany.get("name"));
		dataDevice.put("brand", dataBrand.get("name"));
		home.createNewDevice(DeviceEdit.getHash(), dataDevice);
		/*
		 * **************************************************************
		 */
		// 2. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 3. Select a company from companies list
		home.selectACompanyByName(dataCompany.get("name"));
		// 4. Click “Delete” link in 061Da Company info page
		// 5. Click “Delete” button on delete confirmation dialog
		home.doDelete(CompanyInfo.DELETE);
		/*
		 * VP. Verify that the portal is redirected to companies list page and the deleted company is not displayed in companies list
		 */
		Assert.assertEquals(home.existsElement(Companies.getListElements()).getResult(), "Pass");
		Assert.assertFalse(home.checkACompanyExistByName( dataCompany.get("name")));
		// 6. Navigate to “Apps & Devices” page
		home.click(Xpath.linkDevice);
		// 7. List out the “Company” filter combobox
		ArrayList<String> companyList = home.getAllComboboxOption(DeviceList.COMPANY_LIST);
		/*
		 * Verify that the deleted company is not displayed in “Companty filter combobox and associated app/device of the deleted company are not displayed in apps/devices list
		 */
		Assert.assertFalse(DTSUtil.checkListContain(companyList, dataCompany.get("name")));
		Assert.assertFalse(home.checkAnAppDeviceExistByName( dataDevice.get("name")));
	}
	
	/*
	 * Verify that the deleted company is not displayed in Company combobox when adding new app/device
	 */
	@Test
	public void TC061DaCI_22() {
		result.addLog("ID TC061DaCI_22 : Verify that the deleted company is not displayed in Company combobox when adding new app/device");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to “Companies” page
			3. Select a company from companies list
			4. Click “Delete” link in 061Da Company info page
			5. Click “Delete” button on delete confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the deleted company is not displayed in companies list
			6. Navigate to “Apps & Devices” page
			7. Click “Add App or Device” link
			8. List out the “Company” filter combobox
		*/
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		// Navigate to company page
		home.click(Xpath.LINK_COMPANY);
		// Click Add company link
		home.click(Companies.ADD_COMPANY);
		 // Create company
		Hashtable<String,String> data = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), data);
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 3. Select a company from companies list
		String companyName = data.get("name");
		home.selectACompanyByName(companyName);
		// 4. Click “Delete” link in 061Da Company info page
		// 5. Click “Delete” button on delete confirmation dialog
		home.doDelete(CompanyInfo.DELETE);
		home.waitForElementClickable(Companies.FILTER);
		/*
		 * VP. Verify that the portal is redirected to companies list page
		 */
		Assert.assertEquals(home.existsElement(Companies.getListElements()).getResult(), "Pass");
		/*
		 * Verify that The deleted company is not displayed in Companies list
		 */
		Assert.assertFalse(home.checkACompanyExistByName( companyName));
		// 6. Navigate to “Apps & Devices” page
		home.click(Xpath.LINK_APP_DEVICES);
		// 7. Click “Add App or Device” link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 8. List out the “Company” filter combobox
		ArrayList<String> companyList = home.getAllComboboxOption(DeviceEdit.COMPANY);
		/*
		 * Verify that the deleted company is not displayed in “Company” combobox
		 */
		Assert.assertFalse(DTSUtil.checkListContain(companyList, companyName));
	}
	
}
