package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AddBrand;
import com.dts.adminportal.model.AddCompany;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.Companies;
import com.dts.adminportal.model.CompanyInfo;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.DeviceEdit;
import com.dts.adminportal.model.DeviceList;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.model.UsersList;
import com.dts.adminportal.util.DateUtil;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.TestData;

public class DTSCompanies061DCompaniesInfo extends BasePage {
	
	String filter = "Suspended";
	
	@Override
	protected void initData() {
	}	

	/*
	 * Verify that the partner's company page displays correct information
	 */
	@Test
	public void TC061DaCI_01() {
		companyControl.addLog("ID TC061DaCI_01 : Verify that the partner's company page displays correct information");
		/*
		  	Pre-condition: User has full site privileges rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			VP: Verify that the partner's company info page is displayed correctly which including: company name, ""Company Info"", ""Official Corporate Name"", ""Corporate Address"", ""Primary Partner Contact"", ""Email"", ""Phone"", ""Brands"", brand logo, brand name and ""Publication Credits"" overview module.
			VP: The ""Actions"" module is  displayed which includes ""Suspend"" and ""Delete"" links.
			VP: The Brand section displays the brand's image thumbnail and brand name link."
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click 'Companies' tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Select any company
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		/*
		 * Verify that the partner's company info page is displayed correctly
		 */
		Assert.assertEquals(companyControl.existsElement(CompanyInfo.getListElement()), true);
		/*
		 * The "Actions" module is  displayed which includes "Suspend" and "Delete" links.
		 */
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.ACTION_MODULE));
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.DELETE));
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.SUSPEND));
		/*
		 * Verify that the Brand section displays the brand's image thumbnail and brand name link.
		 */
		Assert.assertTrue(companyControl.checkBrandExist(PARTNER_BRAND_NAME_1));
	}
	
	/*
	 * Verify that when a company is suspended , the associated users are also suspended
	 */
	@Test
	public void TC061DaCI_05() {
		companyControl.addLog("ID TC061DaCI_05 : Verify that when a company is suspended , the associated users are also suspended");
		/*
	 		Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to ï¿½Companiesï¿½ page
			3. Select a company from companies list
			4. Click ï¿½Suspendï¿½ link in 061Da Company info page
			5. Click ï¿½Suspendï¿½ button on Suspend confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the Suspended company is displayed in Suspended list
			6. Navigate to ï¿½Usersï¿½ page
			7. List out the ï¿½Companyï¿½ filter combobox
		*/
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Companies page
		companyControl.click(PageHome.LINK_COMPANY);
		// Click Add company link
		companyControl.click(Companies.ADD_COMPANY);
		// Create new company
		Hashtable<String,String> dataCompany = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), dataCompany);
		// Create user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		dataUser.remove("company");
		userControl.addUser(AddUser.getPartnerUser(), dataUser);
		/*
		 * **********************************************************
		 */
		// 2. Navigate to ï¿½Companiesï¿½ page
		companyControl.click(PageHome.LINK_COMPANY);
		// 3. Select a company from companies list
		companyControl.selectACompanyByName(dataCompany.get("name"));
		// 4. Click ï¿½Suspendï¿½ link in 061Da Company info page
		companyControl.click(CompanyInfo.SUSPEND);
		// 5. Click ï¿½Suspendï¿½ button on Suspend confirmation dialog
		companyControl.selectConfirmationDialogOption("Suspend");
		/*
		 * VP. Verify that the portal is redirected to companies list page and the Suspended company is displayed in Suspended list
		 */
		Assert.assertEquals(companyControl.existsElement(Companies.getListElements()), true);
		companyControl.selectOptionByName(Companies.FILTER, "Suspended");
		Assert.assertTrue(companyControl.checkACompanyExistByName(dataCompany.get("name")));
		// 6. Navigate to ï¿½Usersï¿½ page
		companyControl.click(PageHome.LINK_USERS);
		// 7. List out the ï¿½Companyï¿½ filter combobox
		ArrayList<String> companyList = companyControl.getAllComboboxOption(UsersList.FILTER_COMPANY);
		/*
		 * Verify that the suspended company is not displayed in ï¿½Companyï¿½ filter combobox and  associated user of the suspended company are displayed in suspended list
		 */
		Assert.assertFalse(ListUtil.checkListContain(companyList, dataCompany.get("name")));
		companyControl.selectOptionByName(UsersList.FILTER_USER, "Suspended");
		Assert.assertTrue(userControl.checkUserExistByEmail(dataUser.get("email")));
		// Delete company
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.selectOptionByName(Companies.FILTER, "Suspended");
		companyControl.deleteACompanyByName(dataCompany.get("name"));
	}
	
	/*
	 * Verify that the suspended company is not displayed in Company combobox when adding new users
	 */
	@Test
	public void TC061DaCI_06() {
		companyControl.addLog("ID TC061DaCI_06 : Verify that the suspended company is not displayed in Company combobox when adding new users");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to ï¿½Companiesï¿½ page
			3. Select a company from companies list
			4. Click ï¿½Suspendï¿½ link in 061Da Company info page
			5. Click ï¿½Suspendï¿½ button on delete confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the suspended company is displayed in Suspended list
			6. Navigate to ï¿½Usersï¿½ page
			7. Click ï¿½Add New Userï¿½ link
			8. List out the ï¿½Companyï¿½ compbobox
		*/
		
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to company page
		companyControl.click(PageHome.LINK_COMPANY);
		// Click Add company link
		companyControl.click(Companies.ADD_COMPANY);
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
		companyControl.addCompany(AddCompany.getHash(), data);
		// 2. Navigate to ï¿½Companiesï¿½ page
		companyControl.click(PageHome.LINK_COMPANY);
		// 3. Select a company from companies list
		companyControl.selectACompanyByName(companyName);
		// 4. Click ï¿½Suspendï¿½ link in 061Da Company info page
		companyControl.click(CompanyInfo.SUSPEND);
		// 5. Click ï¿½Suspendï¿½ button on delete confirmation dialog
		companyControl.switchWindowClickOption("Suspend");
		/*
		 * VP. Verify that the portal is redirected to companies list page 
		 */
		Assert.assertEquals(companyControl.existsElement(Companies.getListElements()), true);
		/*
		 * Verify that The suspended company is displayed in Suspended list
		 */
		companyControl.selectFilterByName(Companies.FILTER, filter);
		Assert.assertTrue(companyControl.checkACompanyExistByName(companyName));
		// 6. Navigate to ï¿½Usersï¿½ page
		companyControl.click(PageHome.LINK_USERS);
		// 7. Click ï¿½Add New Userï¿½ link
		companyControl.click(UsersList.ADD_USER);
		// 8. List out the ï¿½Companyï¿½ compbobox
		ArrayList<String> companyList = companyControl.getAllComboboxOption(AddUser.BTN_COMPANY);
		/*
		 * Verify that the suspended company is not displayed in ï¿½Companyï¿½ combobox
		 */
		Assert.assertFalse(ListUtil.checkListContain(companyList, companyName));
		// Delete company
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.selectOptionByName(Companies.FILTER, filter);
		companyControl.deleteACompanyByName(companyName);
	}
	
	/*
	 * Verify that when a company is suspended , the associated Products are also suspended
	 */
	@Test
	public void TC061DaCI_07() {
		companyControl.addLog("ID TC061DaCI_07 : Verify that when a company is suspended , the associated Products are also suspended");
		companyControl.addErrorLog("PDPP-1313 - 030D Catalog All Active Models: Products of suspended company disappear even in 'Suspened' list");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to ï¿½Companiesï¿½ page
			3. Select a company from companies list
			4. Click ï¿½Suspendï¿½ link in 061Da Company info page
			5. Click ï¿½Suspendï¿½ button on Suspend confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the Suspend company is displayed in Suspended list
			6. Navigate to ï¿½Productsï¿½ page
		*/
		/*
		 * PreCondition: Company has at least one brand and one product
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Companies page
		companyControl.click(PageHome.LINK_COMPANY);
		// Click Add company link
		companyControl.click(Companies.ADD_COMPANY);
		// Create new company
		Hashtable<String,String> dataCompany = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), dataCompany);
		// Create user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		dataUser.remove("company");
		userControl.addUser(AddUser.getPartnerUser(), dataUser);
		// Create brand
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), dataBrand);
		// Navigate to Product page
		companyControl.click(PageHome.linkAccessories);
		// Click Add product link
		companyControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		dataProduct.put("company", dataCompany.get("name"));
		dataProduct.put("brand", dataBrand.get("name"));
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		/*
		 * *****************************************************
		 */
		// 2. Navigate to ï¿½Companiesï¿½ page
		companyControl.click(PageHome.LINK_COMPANY);
		// 3. Select a company from companies list
		companyControl.selectACompanyByName(dataCompany.get("name"));
		// 4. Click ï¿½Suspendï¿½ link in 061Da Company info page
		companyControl.click(CompanyInfo.SUSPEND);
		// 5. Click ï¿½Suspendï¿½ button on Suspend confirmation dialog
		companyControl.selectConfirmationDialogOption("Suspend");
		/*
		 * VP. Verify that the portal is redirected to companies list page and the Suspend company is displayed in Suspended list
		 */
		Assert.assertEquals(companyControl.existsElement(Companies.getListElements()), true);
		companyControl.selectOptionByName(Companies.FILTER, "Suspended");
		Assert.assertTrue(companyControl.checkACompanyExistByName(dataCompany.get("name")));
		// 6. Navigate to ï¿½Productsï¿½ page
		companyControl.click(PageHome.linkAccessories);
		/*
		 * Verify that the associated product of the Suspended company are not displayed in Active Products list but it's displayed in Suspended list
		 */
		companyControl.selectOptionByName(ProductModel.PRODUCT_FILTER, "Suspended");
		// Check if PDPP-1313 found
		if(!productControl.checkAnAccessoryExistByName(dataProduct.get("name"))){
			companyControl.addErrorLog("PDPP-1313 - 030D Catalog All Active Models: Products of suspended company disappear even in 'Suspened' list");
			Assert.assertTrue(false);
		}
		Assert.assertTrue(productControl.checkAnAccessoryExistByName(dataProduct.get("name")));
		// Delete company
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.selectOptionByName(Companies.FILTER, "Suspended");
		companyControl.deleteACompanyByName(dataCompany.get("name"));
	}
	
	
	/*
	 * Verify that the suspended company is not displayed in Company combobox when adding new products
	 */
	@Test
	public void TC061DaCI_08() {
		companyControl.addLog("ID TC061DaCI_08 : Verify that the suspended company is not displayed in Company combobox when adding new products");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to ï¿½Companiesï¿½ page
			3. Select a company from companies list
			4. Click ï¿½Suspendï¿½ link in 061Da Company info page
			5. Click ï¿½Suspendï¿½ button on Suspend confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the suspended company is displayed in Suspended list
			6. Navigate to ï¿½Productsï¿½ page
			7. Click ï¿½Add Productï¿½ link
			8. List out the ï¿½Companyï¿½ combobox
		*/
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to company page
		companyControl.click(PageHome.LINK_COMPANY);
		// Click Add company link
		companyControl.click(Companies.ADD_COMPANY);
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
		companyControl.addCompany(AddCompany.getHash(), data);
		// 2. Navigate to ï¿½Companiesï¿½ page
		companyControl.click(PageHome.LINK_COMPANY);
		// 3. Select a company from companies list
		companyControl.selectACompanyByName(companyName);
		// 4. Click ï¿½Suspendï¿½ link in 061Da Company info page
		companyControl.click(CompanyInfo.SUSPEND);
		// 5. Click ï¿½Suspendï¿½ button on delete confirmation dialog
		companyControl.switchWindowClickOption("Suspend");
		/*
		 * VP. Verify that the portal is redirected to companies list page
		 */
		Assert.assertEquals(companyControl.existsElement(Companies.getListElements()), true);
		/*
		 * Verify that The suspended company is displayed in Suspended list
		 */
		companyControl.selectFilterByName(Companies.FILTER, filter);
		Assert.assertTrue(companyControl.checkACompanyExistByName( companyName));
		// 6. Navigate to ï¿½Productsï¿½ page
		companyControl.click(PageHome.linkAccessories);
		// 7. Click ï¿½Add Productï¿½ link
		companyControl.click(ProductModel.ADD_PRODUCT);
		// 8. List out the ï¿½Companyï¿½ combobox
		ArrayList<String> companyList = companyControl.getAllComboboxOption(AddEditProductModel.COMPANY);
		/*
		 * Verify that the Suspended company is not displayed in ï¿½Companyï¿½ combobox
		 */
		Assert.assertFalse(ListUtil.checkListContain(companyList, companyName));
		// Delete company
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.selectOptionByName(Companies.FILTER, filter);
		companyControl.deleteACompanyByName(companyName);
	}
	
	/*
	 * Verify that when a company is Suspended , the associated app/device are also Suspended
	 */
	@Test
	public void TC061DaCI_09() {
		companyControl.addLog("ID TC061DaCI_09 : Verify that when a company is Suspended , the associated app/device are also Suspended");
		companyControl.addErrorLog("PDPP-1313 - 030D Catalog All Active Models: Products of suspended company disappear even in 'Suspened' list");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to ï¿½Companiesï¿½ page
			3. Select a company from companies list
			4. Click ï¿½Suspendï¿½ link in 061Da Company info page
			5. Click ï¿½Suspendï¿½ button on Suspended confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the deleted company is displayed in Suspended list
			6. Navigate to ï¿½Apps & Devicesï¿½ page
			7. List out the ï¿½Companyï¿½ filter combobox
		*/
		/*
		 * PreCondition: Create new company which has at lease one app/device
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Companies page
		companyControl.click(PageHome.LINK_COMPANY);
		// Click Add company link
		companyControl.click(Companies.ADD_COMPANY);
		// Create new company
		Hashtable<String,String> dataCompany = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), dataCompany);
		// Create user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		dataUser.remove("company");
		userControl.addUser(AddUser.getPartnerUser(), dataUser);
		// Create brand
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), dataBrand);
		// Navigate to App & Device page
		companyControl.click(PageHome.linkDevice);
		// Click Add app or device link
		companyControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> dataDevice = TestData.appDeviceDraft();
		dataDevice.put("company", dataCompany.get("name"));
		dataDevice.put("brand", dataBrand.get("name"));
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice);
		/*
		 * **************************************************************
		 */
		// 2. Navigate to ï¿½Companiesï¿½ page
		companyControl.click(PageHome.LINK_COMPANY);
		// 3. Select a company from companies list
		companyControl.selectACompanyByName(dataCompany.get("name"));
		// 4. Click ï¿½Suspendï¿½ link in 061Da Company info page
		companyControl.click(CompanyInfo.SUSPEND);
		// 5. Click ï¿½Suspendï¿½ button on Suspend confirmation dialog
		companyControl.selectConfirmationDialogOption("Suspend");
		/*
		 * VP. Verify that the portal is redirected to companies list page and the Suspended company is displayed in Suspended list
		 */
		Assert.assertEquals(companyControl.existsElement(Companies.getListElements()), true);
		companyControl.selectOptionByName(Companies.FILTER, "Suspended");
		Assert.assertTrue(companyControl.checkACompanyExistByName( dataCompany.get("name")));
		// 6. Navigate to ï¿½Apps & Devicesï¿½ page
		companyControl.click(PageHome.linkDevice);
		// 7. List out the ï¿½Companyï¿½ filter combobox
		ArrayList<String> companyList = companyControl.getAllComboboxOption(DeviceList.COMPANY_LIST);
		/*
		 * Verify that the Suspended company is not displayed in ï¿½Companty filter combobox and associated app/device of the Suspended company are also suspended
		 */
		Assert.assertFalse(ListUtil.checkListContain(companyList, dataCompany.get("name")));
		// Check if PDPP-1313 found
		if(!appDeviceControl.checkAnAppDeviceExistByName(dataDevice.get("name"))){
			companyControl.addErrorLog("PDPP-1313 - 030D Catalog All Active Models: Products of suspended company disappear even in 'Suspened' list");
			Assert.assertTrue(false);
		}
		Assert.assertFalse(appDeviceControl.checkAnAppDeviceExistByName(dataDevice.get("name")));
		// Delete company
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.selectOptionByName(Companies.FILTER, "Suspended");
		companyControl.deleteACompanyByName(dataCompany.get("name"));
	}
	
	/*
	 * Verify that the Suspended company is not displayed in Company combobox when adding new app/device
	 */
	@Test
	public void TC061DaCI_10() {
		companyControl.addLog("ID TC061DaCI_10 : Verify that the Suspended company is not displayed in Company combobox when adding new app/device");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to ï¿½Companiesï¿½ page
			3. Select a company from companies list
			4. Click ï¿½Suspendï¿½ link in 061Da Company info page
			5. Click ï¿½Suspendï¿½ button on Suspended confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the Suspended company is displayed in Suspended list
			6. Navigate to ï¿½Apps & Devicesï¿½ page
			7. Click ï¿½Add App or Deviceï¿½ link
			8. List out the ï¿½Companyï¿½ filter combobox
		*/
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to company page
		companyControl.click(PageHome.LINK_COMPANY);
		// Click Add company link
		companyControl.click(Companies.ADD_COMPANY);
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
		companyControl.addCompany(AddCompany.getHash(), data);
		// 2. Navigate to ï¿½Companiesï¿½ page
		companyControl.click(PageHome.LINK_COMPANY);
		// 3. Select a company from companies list
		companyControl.selectACompanyByName(companyName);
		// 4. Click ï¿½Suspendï¿½ link in 061Da Company info page
		companyControl.click(CompanyInfo.SUSPEND);
		// 5. Click ï¿½Suspendï¿½ button on delete confirmation dialog
		companyControl.switchWindowClickOption("Suspend");
		/*
		 * VP. Verify that the portal is redirected to companies list page
		 */
		Assert.assertEquals(companyControl.existsElement(Companies.getListElements()), true);
		/*
		 * Verify that The suspended company is displayed in Suspended list
		 */
		companyControl.selectFilterByName(Companies.FILTER, filter);
		Assert.assertTrue(companyControl.checkACompanyExistByName( companyName));
		// 6. Navigate to ï¿½Apps & Devicesï¿½ page
		companyControl.click(PageHome.LINK_APP_DEVICES);
		// 7. Click ï¿½Add App or Deviceï¿½ link
		companyControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 8. List out the ï¿½Companyï¿½ filter combobox
		ArrayList<String> companyList = companyControl.getAllComboboxOption(DeviceEdit.COMPANY);
		/*
		 * Verify that the Suspended company is not displayed in ï¿½Companyï¿½ combobox
		 */
		Assert.assertFalse(ListUtil.checkListContain(companyList, companyName));
		// Delete company
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.selectOptionByName(Companies.FILTER, filter);
		companyControl.deleteACompanyByName(companyName);
	}
	
	/*
	 * Verify that when a suspended company is restored , the associated users are also restored
	 */
	@Test
	public void TC061DaCI_11() {
		companyControl.addLog("ID TC061DaCI_11 : Verify that when a suspended company is restored , the associated users are also restored");
		companyControl.addErrorLog("PDPP-1268: 630 Product Device detail: the associated products of restored company are not restored.");
		/*
		Pre-condition: Company has at least one brand and one user
		
		1. Log into DTS portal as a DTS user
		2. Navigate to ï¿½Companiesï¿½ page
		3. Select a company from companies list
		4. Click ï¿½Suspendï¿½ link in 061Da Company info page
		5. Click ï¿½Suspendï¿½ button on Suspend confirmation dialog
		VP. Verify that the portal is redirected to companies list page and the Suspended company is displayed in Suspended list
		6. Navigate to ï¿½Usersï¿½ page
		VP: Verify that the  associated user of the suspended company are not displayed in user list.
		7. Navigate to ï¿½Companiesï¿½ page
		8. Filter the Suspended companies
		9. Select suspended company above
		10. Click ï¿½Restoreï¿½ link
		11. Perform step 6 to 7 again
		VP: Verify that the associated users of the restored company are  displayed in user list again
		*/
				
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//Create new company
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.click(Companies.ADD_COMPANY);
		Hashtable<String,String> data_company= TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), data_company);
		// Create new user
		Hashtable<String,String> data_user =TestData.partnerUser();
		data_user.remove("company");
		userControl.addUser(AddUser.getPartnerUser(), data_user);
		// Get user info
		companyControl.click(PageHome.LINK_USERS);
		// Verify that the new created user is displayed in "Invited" users list but not in "Suspended" list
		companyControl.selectOptionByName(UsersList.FILTER_USER, UsersList.company_filter[2]);
		Assert.assertTrue(userControl.checkUserExistByEmail(data_user.get("email")));
		companyControl.selectOptionByName(UsersList.FILTER_USER, UsersList.company_filter[3]);
		Assert.assertFalse(userControl.checkUserExistByEmail(data_user.get("email")));
		// Navigate to company page
		companyControl.click(PageHome.LINK_COMPANY);
		// select company from companies list
		companyControl.selectACompanyByName(data_company.get("name"));
		// Suspend this company
		companyControl.click(CompanyInfo.SUSPEND);
		companyControl.selectConfirmationDialogOption("Suspend");
		
		//VP. Verify that the portal is redirected to companies list page and the Suspend company is displayed in Suspended list
		Assert.assertTrue(companyControl.isElementPresent(Companies.LABEL));
		// Navigate to User page
		companyControl.click(PageHome.LINK_USERS);
		// Verify that the new created user is not displayed in "Invited" users list but is displayed in "Suspended" list
		companyControl.selectOptionByName(UsersList.FILTER_USER, UsersList.company_filter[2]);
		Assert.assertFalse(userControl.checkUserExistByEmail(data_user.get("email")));
		companyControl.selectOptionByName(UsersList.FILTER_USER, UsersList.company_filter[3]);
		Assert.assertTrue(userControl.checkUserExistByEmail(data_user.get("email")));
//		7. Navigate to ï¿½Companiesï¿½ page
//		8. Filter the Suspended companies
//		9. Select suspended company above
//		10. Click ï¿½Restoreï¿½ link
//		11. Perform step 6 to 7 again
		
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.selectOptionByName(Companies.FILTER,Companies.option[8]);
		companyControl.selectACompanyByName(data_company.get("name"));
		companyControl.click(CompanyInfo.RESTORE);
		companyControl.selectConfirmationDialogOption("Restore");
		// Navigate to User page
		companyControl.click(PageHome.LINK_USERS);
		// Verify that the new created user is displayed in "Invited" users list but not in "Suspended" list
		companyControl.selectOptionByName(UsersList.FILTER_USER, UsersList.company_filter[2]);
		Assert.assertTrue(userControl.checkUserExistByEmail(data_user.get("email")));
		companyControl.selectOptionByName(UsersList.FILTER_USER, UsersList.company_filter[3]);
		Assert.assertFalse(userControl.checkUserExistByEmail(data_user.get("email")));
		//Verify user status
		userControl.selectUserInfoByEmail(data_user.get("email"));
		Assert.assertTrue(companyControl.getTextByXpath(UserMgmt.STATUS).contains(UsersList.company_filter[2]));
		
		//Teardown: delete this company
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.selectACompanyByName(data_company.get("name"));
		companyControl.doDelete(CompanyInfo.DELETE);
	}
	
	
	/*
	 * Verify that the restored company is displayed in Company combobox again when adding new users
	 */
	@Test
	public void TC061DaCI_12() {
		companyControl.addLog("ID TC061DaCI_12 : Verify that the restored company is displayed in Company combobox again when adding new users");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to ï¿½Companiesï¿½ page
			3. Select a company from companies list
			4. Click ï¿½Suspendï¿½ link in 061Da Company info page
			5. Click ï¿½Suspendï¿½ button on delete confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the deleted company is displayed in Suspended list
			6. Navigate to ï¿½Usersï¿½ page
			7. Click ï¿½Add New Userï¿½ link
			8. List out the ï¿½Companyï¿½ compbobox
			VP: Verify that the deleted company is not displayed in ï¿½Companyï¿½ combobox
			9. Navigate to ï¿½Companiesï¿½ page
			10. Filter the Suspended companies
			11. Select suspended company above
			12. Click ï¿½Restoreï¿½ link
			13. Perform step 6 to 7 again
		*/
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to company page
		companyControl.click(PageHome.LINK_COMPANY);
		// Click Add company link
		companyControl.click(Companies.ADD_COMPANY);
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
		companyControl.addCompany(AddCompany.getHash(), data);
		// 2. Navigate to ï¿½Companiesï¿½ page
		companyControl.click(PageHome.LINK_COMPANY);
		// 3. Select a company from companies list
		companyControl.selectACompanyByName(companyName);
		// 4. Click ï¿½Suspendï¿½ link in 061Da Company info page
		companyControl.click(CompanyInfo.SUSPEND);
		// 5. Click ï¿½Suspendï¿½ button on delete confirmation dialog
		companyControl.switchWindowClickOption("Suspend");
		/*
		 * VP. Verify that the portal is redirected to companies list page
		 */
		Assert.assertEquals(companyControl.existsElement(Companies.getListElements()), true);
		/*
		 * Verify that The suspended company is displayed in Suspended list
		 */
		companyControl.selectFilterByName(Companies.FILTER, filter);
		Assert.assertTrue(companyControl.checkACompanyExistByName( companyName));
		// 6. Navigate to ï¿½Usersï¿½ page
		companyControl.click(PageHome.LINK_USERS);
		// 7. Click ï¿½Add New Userï¿½ link
		companyControl.click(UsersList.ADD_USER);
		// 8. List out the ï¿½Companyï¿½ compbobox
		ArrayList<String> companyList = companyControl.getAllComboboxOption(AddUser.BTN_COMPANY);
		/*
		 * VP: Verify that the suspended company is not displayed in ï¿½Companyï¿½ combobox
		 */
		Assert.assertFalse(ListUtil.checkListContain(companyList, companyName));
		// 9. Navigate to ï¿½Companiesï¿½ page
		companyControl.click(PageHome.LINK_COMPANY);
		// 10. Filter the Suspended companies
		companyControl.selectOptionByName(Companies.FILTER, filter);
		// 11. Select suspended company above
		companyControl.selectACompanyByName(companyName);
		// 12. Click ï¿½Restoreï¿½ link
		companyControl.click(CompanyInfo.RESTORE);
		companyControl.switchWindowClickOption("Restore");
		// 13. Perform step 6 to 7 again
		// Navigate to user page
		companyControl.click(PageHome.LINK_USERS);
		// Click ï¿½Add New Userï¿½ link
		companyControl.click(UsersList.ADD_USER);
		/*
		 * Verify that the restored company is displayed in ï¿½Companyï¿½ combobox again
		 */
		companyList = companyControl.getAllComboboxOption(AddUser.BTN_COMPANY);
		Assert.assertTrue(ListUtil.checkListContain(companyList, companyName));
		// Delete company
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.deleteACompanyByName(companyName);
	}
	
	/*
	 * Verify that when a suspended company is restored , the associated Products are also restored.
	 */
	@Test
	public void TC061DaCI_13() {
		companyControl.addLog("ID TC061DaCI_13 : Verify that when a suspended company is restored , the associated Products are also restored.");
		companyControl.addErrorLog("PDPP-1268: 630 Product Device detail: the associated products of restored company are not restored.");
		/*
		Pre-condition: Company has at least one brand and one user
		
		1. Log into DTS portal as a DTS user
		2. Navigate to ï¿½Companiesï¿½ page
		3. Select a company from companies list
		4. Click ï¿½Suspendï¿½ link in 061Da Company info page
		5. Click ï¿½Suspendï¿½ button on Suspend confirmation dialog
		VP. Verify that the portal is redirected to companies list page and the Suspend company is displayed in Suspended list
		6. Navigate to ï¿½Productsï¿½ page
		7. List out the ï¿½Brandï¿½ filter combobox
		VP: Verify that the brand of Suspended company is not displayed in ï¿½Brandsï¿½ filter combobox and associated product of the Suspended company are not displayed in Products list.
		8. Navigate to ï¿½Companiesï¿½ page
		9. Filter the Suspended companies
		10. Select suspended company above
		11. Click ï¿½Restoreï¿½ link
		12. Perform step 6 to 7 again
		VP: Verify that the brand of restored company is  displayed in ï¿½Brandsï¿½ filter combobox and associated product of the restored company are displayed in Products list.
		*/
		
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//Create new company
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.click(Companies.ADD_COMPANY);
		Hashtable<String,String> data_company= TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), data_company);
		// Create new user
		Hashtable<String,String> data_user =TestData.partnerUser();
		data_user.remove("company");
		userControl.addUser(AddUser.getPartnerUser(), data_user);
		// Create new brand
		Hashtable<String,String> data_brand = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), data_brand);
		
		String company = data_company.get("name");
		String brand = data_brand.get("name");
		
		// Create new product
		companyControl.click(PageHome.linkAccessories);
		companyControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> data_product = TestData.productData(company,brand, false, false, false);
		//data_product.remove("company");
		//data_product.remove("brand");
		//data_product.put("company", company );
		//data_product.put("brand", brand);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data_product);
		String product_name = data_product.get("name");
				
		// Navigate to company page
		companyControl.click(PageHome.LINK_COMPANY);
		// select company of above product
		companyControl.selectACompanyByName(company);
		// Suspend this company
		companyControl.click(CompanyInfo.SUSPEND);
		companyControl.selectConfirmationDialogOption("Suspend");
		
		//VP. Verify that the portal is redirected to companies list page and the Suspend company is displayed in Suspended list
		Assert.assertTrue(companyControl.isElementPresent(Companies.LABEL));
		// Navigate to product page
		companyControl.click(PageHome.linkAccessories);
		// VP: Verify that the associated product of the Suspended company are not displayed in Active Products list but it's displayed in Suspended list
		//Verify that the associated product is not displayed in active product list
		Assert.assertFalse(productControl.checkAnAccessoryExistByName(product_name));
		// Filter the products list as "Suspended"
		companyControl.selectOptionByName(ProductModel.PRODUCT_FILTER, Constant.HOME_SUSPENDED);
		// Verify that the associated product is displayed in suspended list
		Assert.assertTrue(productControl.checkAnAccessoryExistByName(product_name));
//		10. Select suspended company above
//		11. Click ï¿½Restoreï¿½ link
//		12. Perform step 6 to 7 again
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.selectOptionByName(Companies.FILTER,Companies.option[8]);
		companyControl.selectACompanyByName(company);
		companyControl.click(CompanyInfo.RESTORE);
		companyControl.selectConfirmationDialogOption("Restore");
		// Navigate to product page
		companyControl.click(PageHome.linkAccessories);
		//Verify that the brand of restored company is  displayed in ï¿½Brandsï¿½ filter combobox and associated product of the restored company are displayed in Products list.
		//Verify that the associated product is not displayed in active product list
		Assert.assertTrue(productControl.checkAnAccessoryExistByName(product_name));
		// Filter the products list as "Suspended"
		companyControl.selectOptionByName(ProductModel.PRODUCT_FILTER, Constant.HOME_SUSPENDED);
		// Verify that the associated product is displayed in suspended list
		Assert.assertFalse(productControl.checkAnAccessoryExistByName(product_name));
		
		//Teardown: Delete company
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.selectACompanyByName(company);
		companyControl.doDelete(CompanyInfo.DELETE);
		
	}
	
	/*
	 * Verify that the restored company is displayed in Company combobox again when adding new products
	 */
	@Test
	public void TC061DaCI_14() {
		companyControl.addLog("ID TC061DaCI_14 : Verify that the restored company is displayed in Company combobox again when adding new products");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to ï¿½Companiesï¿½ page
			3. Select a company from companies list
			4. Click ï¿½Suspendï¿½ link in 061Da Company info page
			5. Click ï¿½Suspendï¿½ button on Suspend confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the deleted company is displayed in Suspended list
			6. Navigate to ï¿½Productsï¿½ page
			7. Click ï¿½Add Productï¿½ link
			8. List out the ï¿½Companyï¿½ combobox
			VP: Verify that the Suspended company is not displayed in ï¿½Companyï¿½ combobox
			9. Navigate to ï¿½Companiesï¿½ page
			10. Filter the Suspended companies
			11. Select suspended company above
			12. Click ï¿½Restoreï¿½ link
			13. Perform step 6 to 7 again
		*/
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to company page
		companyControl.click(PageHome.LINK_COMPANY);
		// Click Add company link
		companyControl.click(Companies.ADD_COMPANY);
		//Create company
		Hashtable<String,String> dataCompany = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), dataCompany);
		// Create user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		dataUser.remove("company");
		userControl.addUser(AddUser.getPartnerUser(), dataUser);
		// Create brand
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), dataBrand);
		/*
		 * ***************************************************************
		 */
		// 2. Navigate to ï¿½Companiesï¿½ page
		companyControl.click(PageHome.LINK_COMPANY);
		// 3. Select a company from companies list
		companyControl.selectACompanyByName(dataCompany.get("name"));
		// 4. Click ï¿½Suspendï¿½ link in 061Da Company info page
		companyControl.click(CompanyInfo.SUSPEND);
		// 5. Click ï¿½Suspendï¿½ button on delete confirmation dialog
		companyControl.selectConfirmationDialogOption("Suspend");
		/*
		 * VP. Verify that the portal is redirected to companies list page
		 */
		Assert.assertEquals(companyControl.existsElement(Companies.getListElements()), true);
		/*
		 * Verify that The suspended company is displayed in Suspended list
		 */
		companyControl.selectFilterByName(Companies.FILTER, filter);
		Assert.assertTrue(companyControl.checkACompanyExistByName( dataCompany.get("name")));
		// 6. Navigate to ï¿½Productsï¿½ page
		companyControl.click(PageHome.linkAccessories);
		// 7. Click ï¿½Add Productï¿½ link
		companyControl.click(ProductModel.ADD_PRODUCT);
		// 8. List out the ï¿½Companyï¿½ combobox
		ArrayList<String> companyList = companyControl.getAllComboboxOption(AddEditProductModel.COMPANY);
		/*
		 * VP: Verify that the Suspended company is not displayed in ï¿½Companyï¿½ combobox
		 */
		Assert.assertFalse(ListUtil.checkListContain(companyList, dataCompany.get("name")));
		// 9. Navigate to ï¿½Companiesï¿½ page
		companyControl.click(PageHome.LINK_COMPANY);
		// 10. Filter the Suspended companies
		companyControl.selectFilterByName(Companies.FILTER, filter);
		// 11. Select suspended company above
		companyControl.selectACompanyByName(dataCompany.get("name"));
		// 12. Click ï¿½Restoreï¿½ link
		companyControl.click(CompanyInfo.RESTORE);
		companyControl.selectConfirmationDialogOption("Restore");
		// 13. Perform step 6 to 7 again
		// Navigate to ï¿½Productsï¿½ page
		companyControl.click(PageHome.linkAccessories);
		// Click ï¿½Add Productï¿½ link
		companyControl.click(ProductModel.ADD_PRODUCT);
		/*
		 * Verify that the restored company is displayed in ï¿½Companyï¿½ combobox
		 */
		companyList = companyControl.getAllComboboxOption(AddEditProductModel.COMPANY);
		Assert.assertTrue(ListUtil.checkListContain(companyList, dataCompany.get("name")));
		// Delete company
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.deleteACompanyByName(dataCompany.get("name"));
	}
	
	/*
	 * Verify that when a suspended company is restored , the associated app/device are also restored.
	 */
	@Test
	public void TC061DaCI_15() {
		companyControl.addLog("ID TC061DaCI_15 : Verify that when a suspended company is restored , the associated app/device are also restored.");
		companyControl.addErrorLog("PDPP-1313 - 030D Catalog All Active Models: Products of suspended company disappear even in 'Suspened' list");
		/*
		Pre-condition: Company has at least one brand and one user

		1. Log into DTS portal as a DTS user
		2. Navigate to ï¿½Companiesï¿½ page
		3. Select a company from companies list
		4. Click ï¿½Suspendï¿½ link in 061Da Company info page
		5. Click ï¿½Suspendï¿½ button on Suspended confirmation dialog
		VP. Verify that the portal is redirected to companies list page and the deleted company is displayed in Suspended list
		6. Navigate to ï¿½Apps & Devicesï¿½ page
		7. List out the ï¿½Companyï¿½ filter combobox
		VP: Verify that the Suspended company is not displayed in ï¿½Companty filter combobox and associated app/device of the Suspended company are not displayed in apps/devices list.
		8. Navigate to ï¿½Companiesï¿½ page
		9. Filter the Suspended companies
		10. Select suspended company above
		11. Click ï¿½Restoreï¿½ link
		12. Perform step 6 to 7 again
		VP: Verify that the restored company is displayed in ï¿½Companty filter combobox and associated app/device of the restored company are also displayed in apps/devices list again.
		*/
		
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//Create new company
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.click(Companies.ADD_COMPANY);
		Hashtable<String,String> data_company= TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), data_company);
		// Create new user
		Hashtable<String,String> data_user =TestData.partnerUser();
		data_user.remove("company");
		userControl.addUser(AddUser.getPartnerUser(), data_user);
		// Create new brand
		Hashtable<String,String> data_brand = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), data_brand);
		
		String company = data_company.get("name");
		String brand = data_brand.get("name");
				
		//Create new app/device
		companyControl.click(PageHome.LINK_APP_DEVICES);
		companyControl.click(DeviceList.CREATE_NEW_DEVICE);
		Hashtable<String,String> data_appdevice = TestData.appDeviceDraft();
		data_appdevice.remove("company");
		data_appdevice.remove("brand");
		data_appdevice.put("company", company);
		data_appdevice.put("brand", brand);
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data_appdevice);
		
		String data_device = data_appdevice.get("name");
		
		// Navigate to company page
		companyControl.click(PageHome.LINK_COMPANY);
		// select company of above product
		companyControl.selectACompanyByName(company);
		// Suspend this company
		companyControl.click(CompanyInfo.SUSPEND);
		companyControl.selectConfirmationDialogOption("Suspend");
		
		//VP. Verify that the portal is redirected to companies list page and the Suspend company is displayed in Suspended list
		Assert.assertTrue(companyControl.isElementPresent(Companies.LABEL));
		// Navigate to app/device page
		companyControl.click(PageHome.LINK_APP_DEVICES);
		// Check if PDPP-1313 found
		if(!appDeviceControl.checkAnAppDeviceExistByName(data_appdevice.get("name"))){
			companyControl.addErrorLog("PDPP-1313 - 030D Catalog All Active Models: Products of suspended company disappear even in 'Suspened' list");
			Assert.assertTrue(false);
		}
		// VP: Verify that the associated product of the Suspended company are not displayed in Active Products list but it's displayed in Suspended list
		// Verify that the associated product is not displayed in active product list
		Assert.assertFalse(appDeviceControl.checkAnAppDeviceExistByName(data_device));
		// 8. Navigate to ï¿½Companiesï¿½ page
		// 9. Filter the Suspended companies
		// 10. Select suspended company above
		// 11. Click ï¿½Restoreï¿½ link
		// 12. Perform step 6 to 7 again
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.selectOptionByName(Companies.FILTER,Companies.option[8]);
		companyControl.selectACompanyByName(company);
		companyControl.click(CompanyInfo.RESTORE);
		companyControl.selectConfirmationDialogOption("Restore");
		// Navigate to app/device page
		companyControl.click(PageHome.LINK_APP_DEVICES);
//		Verify that the associated app/device of the restored company are also displayed in apps/devices list again.
		Assert.assertTrue(appDeviceControl.checkAnAppDeviceExistByName(data_device));
				
		//Teardown: Delete company
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.selectACompanyByName(company);
		companyControl.doDelete(CompanyInfo.DELETE);
		
	}
	
	/*
	 * Verify that the restored company is displayed in Company combobox again when adding new app/device
	 */
	@Test
	public void TC061DaCI_16() {
		companyControl.addLog("ID TC061DaCI_16 : Verify that the restored company is displayed in Company combobox again when adding new app/device");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to ï¿½Companiesï¿½ page
			3. Select a company from companies list
			4. Click ï¿½Suspendï¿½ link in 061Da Company info page
			5. Click ï¿½Suspendï¿½ button on Suspended confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the Suspended company is displayed in Suspended list
			6. Navigate to ï¿½Apps & Devicesï¿½ page
			7. Click ï¿½Add App or Deviceï¿½ link
			8. List out the ï¿½Companyï¿½ filter combobox
			VP: Verify that the Suspended company is not displayed in ï¿½Companyï¿½ combobox
			9. Navigate to ï¿½Companiesï¿½ page
			10. Filter the Suspended companies
			11. Select suspended company above
			12. Click ï¿½Restoreï¿½ link
			13. Perform step 6 to 7 again
		*/
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to company page
		companyControl.click(PageHome.LINK_COMPANY);
		// Click Add company link
		companyControl.click(Companies.ADD_COMPANY);
		// Create company
		Hashtable<String,String> dataCompany = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), dataCompany);
		// Create user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		dataUser.remove("company");
		userControl.addUser(AddUser.getPartnerUser(), dataUser);
		// Create brand
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), dataBrand);
		// 2. Navigate to ï¿½Companiesï¿½ page
		companyControl.click(PageHome.LINK_COMPANY);
		// 3. Select a company from companies list
		companyControl.selectACompanyByName(dataCompany.get("name"));
		// 4. Click ï¿½Suspendï¿½ link in 061Da Company info page
		companyControl.click(CompanyInfo.SUSPEND);
		// 5. Click ï¿½Suspendï¿½ button on delete confirmation dialog
		companyControl.selectConfirmationDialogOption("Suspend");
		/*
		 * VP. Verify that the portal is redirected to companies list page
		 */
		Assert.assertEquals(companyControl.existsElement(Companies.getListElements()), true);
		/*
		 * Verify that The suspended company is displayed in Suspended list
		 */
		companyControl.selectFilterByName(Companies.FILTER, filter);
		Assert.assertTrue(companyControl.checkACompanyExistByName( dataCompany.get("name")));
		// 6. Navigate to ï¿½Apps & Devicesï¿½ page
		companyControl.click(PageHome.LINK_APP_DEVICES);
		// 7. Click ï¿½Add App or Deviceï¿½ link		
		companyControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 8. List out the ï¿½Companyï¿½ filter combobox
		ArrayList<String> companyList = companyControl.getAllComboboxOption(DeviceEdit.COMPANY);
		/*
		 * VP: Verify that the Suspended company is not displayed in ï¿½Companyï¿½ combobox
		 */
		Assert.assertFalse(ListUtil.checkListContain(companyList, dataCompany.get("name")));
		// 9. Navigate to ï¿½Companiesï¿½ page
		companyControl.click(PageHome.LINK_COMPANY);
		// 10. Filter the Suspended companies
		companyControl.selectFilterByName(Companies.FILTER, filter);
		// 11. Select suspended company above
		companyControl.selectACompanyByName(dataCompany.get("name"));
		// 12. Click ï¿½Restoreï¿½ link
		companyControl.click(CompanyInfo.RESTORE);
		companyControl.selectConfirmationDialogOption("Restore");
		// 13. Perform step 6 to 7 again
		// Navigate to ï¿½Apps & Devicesï¿½ page
		companyControl.click(PageHome.LINK_APP_DEVICES);
		// Click ï¿½Add App or Deviceï¿½ link
		companyControl.click(DeviceList.CREATE_NEW_DEVICE);
		/*
		 * Verify that the restored company is displayed in ï¿½Companyï¿½ combobox again
		 */
		companyList = companyControl.getAllComboboxOption(DeviceEdit.COMPANY);
		Assert.assertTrue(ListUtil.checkListContain(companyList, dataCompany.get("name")));
		// Delete company
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.deleteACompanyByName(dataCompany.get("name"));
	}
	
	/*
	 * Verify that when a company is deleted , the associated users are also deleted
	 */
	@Test
	public void TC061DaCI_17() {
		companyControl.addLog("ID TC061DaCI_17 : Verify that when a company is deleted , the associated users are also deleted");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to ï¿½Companiesï¿½ page
			3. Select a company from companies list
			4. Click ï¿½Deleteï¿½ link in 061Da Company info page
			5. Click ï¿½Deleteï¿½ button on delete confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the deleted company is not displayed in companies list
			6. Navigate to ï¿½Usersï¿½ page
			7. List out the ï¿½Companyï¿½ filter combobox
		*/
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Companies page
		companyControl.click(PageHome.LINK_COMPANY);
		// Click Add company link
		companyControl.click(Companies.ADD_COMPANY);
		// Create new company
		Hashtable<String,String> dataCompany = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), dataCompany);
		// Create user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		dataUser.remove("company");
		userControl.addUser(AddUser.getPartnerUser(), dataUser);
		/*
		 * **********************************************************
		 */
		// 2. Navigate to ï¿½Companiesï¿½ page
		companyControl.click(PageHome.LINK_COMPANY);
		// 3. Select a company from companies list
		companyControl.selectACompanyByName(dataCompany.get("name"));
		// 4. Click ï¿½Deleteï¿½ link in 061Da Company info page
		// 5. Click ï¿½Deleteï¿½ button on delete confirmation dialog
		companyControl.doDelete(CompanyInfo.DELETE);
		/*
		 * VP. Verify that the portal is redirected to companies list page and the deleted company is not displayed in companies list
		 */
		Assert.assertEquals(companyControl.existsElement(Companies.getListElements()), true);
		Assert.assertFalse(companyControl.checkACompanyExistByName( dataCompany.get("name")));
		// 6. Navigate to ï¿½Usersï¿½ page
		companyControl.click(PageHome.LINK_USERS);
		// 7. List out the ï¿½Companyï¿½ filter combobox
		ArrayList<String> companyList = companyControl.getAllComboboxOption(UsersList.FILTER_COMPANY);
		/*
		 * Verify that the deleted company is not displayed in ï¿½Companyï¿½ filter combobox and  associated user of the deleted company are not displayed in user list
		 */
		Assert.assertFalse(ListUtil.checkListContain(companyList, dataCompany.get("name")));
		Assert.assertFalse(userControl.checkUserExistByEmail(dataUser.get("email")));
	}
	
	/*
	 * Verify that the deleted company is not displayed in Company combobox when adding new users
	 */
	@Test
	public void TC061DaCI_18() {
		companyControl.addLog("ID TC061DaCI_18 : Verify that the deleted company is not displayed in Company combobox when adding new users");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to ï¿½Companiesï¿½ page
			3. Select a company from companies list
			4. Click ï¿½Deleteï¿½ link in 061Da Company info page
			5. Click ï¿½Deleteï¿½ button on delete confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the deleted company is not displayed in companies list
			6. Navigate to ï¿½Usersï¿½ page
			7. Click ï¿½Add New Userï¿½ link
			8. List out the ï¿½Companyï¿½ compbobox
		*/
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to company page
		companyControl.click(PageHome.LINK_COMPANY);
		// Click Add company link
		companyControl.click(Companies.ADD_COMPANY);
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
		companyControl.addCompany(AddCompany.getHash(), data);
		// 2. Navigate to ï¿½Companiesï¿½ page
		companyControl.click(PageHome.LINK_COMPANY);
		// 3. Select a company from companies list
		companyControl.selectACompanyByName(companyName);
		// 4. Click ï¿½Deleteï¿½ link in 061Da Company info page
		// 5. Click ï¿½Deleteï¿½ button on delete confirmation dialog
		companyControl.doDelete(CompanyInfo.DELETE);
		/*
		 * VP. Verify that the portal is redirected to companies list page
		 */
		Assert.assertEquals(companyControl.existsElement(Companies.getListElements()), true);
		/*
		 * Verify that The deleted company is not displayed in Companies list
		 */
		Assert.assertFalse(companyControl.checkACompanyExistByName( companyName));
		// 6. Navigate to ï¿½Usersï¿½ page
		companyControl.click(PageHome.LINK_USERS);
		// 7. Click ï¿½Add New Userï¿½ link
		companyControl.click(UsersList.ADD_USER);
		// 8. List out the ï¿½Companyï¿½ compbobox
		ArrayList<String> companyList = companyControl.getAllComboboxOption(AddUser.BTN_COMPANY);
		/*
		 * Verify that the deleted company is not displayed in ï¿½Companyï¿½ combobox
		 */
		Assert.assertFalse(ListUtil.checkListContain(companyList, companyName));
	}
	
	/*
	 * Verify that when a company is deleted , the associated Products are also deleted
	 */
	@Test
	public void TC061DaCI_19() {
		companyControl.addLog("ID TC061DaCI_19 : Verify that when a company is deleted , the associated Products are also deleted");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to ï¿½Companiesï¿½ page
			3. Select a company from companies list
			4. Click ï¿½Deleteï¿½ link in 061Da Company info page
			5. Click ï¿½Deleteï¿½ button on delete confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the deleted company is not displayed in companies list
			6. Navigate to ï¿½Productsï¿½ page
			7. List out the ï¿½Brandï¿½ filter combobox
		*/
		/*
		 * PreCondition: Create new company which has at lease one product
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Companies page
		companyControl.click(PageHome.LINK_COMPANY);
		// Click Add company link
		companyControl.click(Companies.ADD_COMPANY);
		// Create new company
		Hashtable<String,String> dataCompany = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), dataCompany);
		// Create user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		dataUser.remove("company");
		userControl.addUser(AddUser.getPartnerUser(), dataUser);
		// Create brand
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), dataBrand);
		// Navigate to Product page
		companyControl.click(PageHome.linkAccessories);
		// Click Add product link
		companyControl.click(ProductModel.ADD_PRODUCT);
		// Create new product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		dataProduct.put("company", dataCompany.get("name"));
		dataProduct.put("brand", dataBrand.get("name"));
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		/*
		 * **************************************************************
		 */
		// 2. Navigate to ï¿½Companiesï¿½ page
		companyControl.click(PageHome.LINK_COMPANY);
		// 3. Select a company from companies list
		companyControl.selectACompanyByName(dataCompany.get("name"));
		// 4. Click ï¿½Deleteï¿½ link in 061Da Company info page
		// 5. Click ï¿½Deleteï¿½ button on delete confirmation dialog
		companyControl.doDelete(CompanyInfo.DELETE);
		/*
		 * VP. Verify that the portal is redirected to companies list page and the deleted company is not displayed in companies list
		 */
		Assert.assertEquals(companyControl.existsElement(Companies.getListElements()), true);
		Assert.assertFalse(companyControl.checkACompanyExistByName( dataCompany.get("name")));
		// 6. Navigate to ï¿½Productsï¿½ page
		companyControl.click(PageHome.linkAccessories);
		// 7. List out the ï¿½Brandï¿½ filter combobox
		ArrayList<String> brandList = companyControl.getAllComboboxOption(ProductModel.BRAND_FILTER);
		/*
		 * Verify that the brand of deleted company is not displayed in ï¿½Brandsï¿½ filter combobox and associated product of the deleted company are not displayed in Products list
		 */
		Assert.assertFalse(ListUtil.checkListContain(brandList, dataBrand.get("name")));
		Assert.assertFalse(productControl.checkAnAccessoryExistByName(dataProduct.get("name")));
	}
	
	/*
	 * Verify that the deleted company is not displayed in Company combobox when adding new products
	 */
	@Test
	public void TC061DaCI_20() {
		companyControl.addLog("ID TC061DaCI_20 : Verify that the deleted company is not displayed in Company combobox when adding new products");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to ï¿½Companiesï¿½ page
			3. Select a company from companies list
			4. Click ï¿½Deleteï¿½ link in 061Da Company info page
			5. Click ï¿½Deleteï¿½ button on delete confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the deleted company is not displayed in companies list
			6. Navigate to ï¿½Productsï¿½ page
			7. Click ï¿½Add Productï¿½ link
			8. List out the ï¿½Companyï¿½ combobox
		*/
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to company page
		companyControl.click(PageHome.LINK_COMPANY);
		// Click Add company link
		companyControl.click(Companies.ADD_COMPANY);
		 // Create company
		Hashtable<String,String> data = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), data);
		// Create user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		dataUser.remove("company");
		userControl.addUser(AddUser.getPartnerUser(), dataUser);
		// Create brand
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), dataBrand);
		/*
		 * *****************************************************************
		 */
		// 2. Navigate to ï¿½Companiesï¿½ page
		companyControl.click(PageHome.LINK_COMPANY);
		// 3. Select a company from companies list
		String companyName = data.get("name");
		companyControl.selectACompanyByName(companyName);
		// 4. Click ï¿½Deleteï¿½ link in 061Da Company info page
		// 5. Click ï¿½Deleteï¿½ button on delete confirmation dialog
		companyControl.doDelete(CompanyInfo.DELETE);
		companyControl.waitForElementClickable(Companies.FILTER);
		/*
		 * VP. Verify that the portal is redirected to companies list page
		 */
		Assert.assertEquals(companyControl.existsElement(Companies.getListElements()), true);
		/*
		 * Verify that The deleted company is not displayed in Companies list
		 */
		Assert.assertFalse(companyControl.checkACompanyExistByName( companyName));
		// 6. Navigate to ï¿½Productsï¿½ page
		companyControl.click(PageHome.linkAccessories);
		// 7. Click ï¿½Add Productï¿½ link
		companyControl.click(ProductModel.ADD_PRODUCT);
		// 8. List out the ï¿½Companyï¿½ combobox
		ArrayList<String> companyList = companyControl.getAllComboboxOption(AddEditProductModel.COMPANY);
		/*
		 * Verify that the deleted company is not displayed in ï¿½Companyï¿½ combobox
		 */
		Assert.assertFalse(ListUtil.checkListContain(companyList, companyName));
	}
	
	/*
	 * Verify that when a company is deleted , the associated app/device are also deleted
	 */
	@Test
	public void TC061DaCI_21() {
		companyControl.addLog("ID TC061DaCI_21 : Verify that when a company is deleted , the associated app/device are also deleted");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to ï¿½Companiesï¿½ page
			3. Select a company from companies list
			4. Click ï¿½Deleteï¿½ link in 061Da Company info page
			5. Click ï¿½Deleteï¿½ button on delete confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the deleted company is not displayed in companies list
			6. Navigate to ï¿½Apps & Devicesï¿½ page
			7. List out the ï¿½Companyï¿½ filter combobox
		*/
		/*
		 * PreCondition: Create new company which has at lease one app/device
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Companies page
		companyControl.click(PageHome.LINK_COMPANY);
		// Click Add company link
		companyControl.click(Companies.ADD_COMPANY);
		// Create new company
		Hashtable<String,String> dataCompany = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), dataCompany);
		// Create user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		dataUser.remove("company");
		userControl.addUser(AddUser.getPartnerUser(), dataUser);
		// Create brand
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), dataBrand);
		// Navigate to App & Device page
		companyControl.click(PageHome.linkDevice);
		// Click Add app or device link
		companyControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> dataDevice = TestData.appDeviceDraft();
		dataDevice.put("company", dataCompany.get("name"));
		dataDevice.put("brand", dataBrand.get("name"));
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice);
		/*
		 * **************************************************************
		 */
		// 2. Navigate to ï¿½Companiesï¿½ page
		companyControl.click(PageHome.LINK_COMPANY);
		// 3. Select a company from companies list
		companyControl.selectACompanyByName(dataCompany.get("name"));
		// 4. Click ï¿½Deleteï¿½ link in 061Da Company info page
		// 5. Click ï¿½Deleteï¿½ button on delete confirmation dialog
		companyControl.doDelete(CompanyInfo.DELETE);
		/*
		 * VP. Verify that the portal is redirected to companies list page and the deleted company is not displayed in companies list
		 */
		Assert.assertEquals(companyControl.existsElement(Companies.getListElements()), true);
		Assert.assertFalse(companyControl.checkACompanyExistByName( dataCompany.get("name")));
		// 6. Navigate to ï¿½Apps & Devicesï¿½ page
		companyControl.click(PageHome.linkDevice);
		// 7. List out the ï¿½Companyï¿½ filter combobox
		ArrayList<String> companyList = companyControl.getAllComboboxOption(DeviceList.COMPANY_LIST);
		/*
		 * Verify that the deleted company is not displayed in ï¿½Companty filter combobox and associated app/device of the deleted company are not displayed in apps/devices list
		 */
		Assert.assertFalse(ListUtil.checkListContain(companyList, dataCompany.get("name")));
		Assert.assertFalse(appDeviceControl.checkAnAppDeviceExistByName( dataDevice.get("name")));
	}
	
	/*
	 * Verify that the deleted company is not displayed in Company combobox when adding new app/device
	 */
	@Test
	public void TC061DaCI_22() {
		companyControl.addLog("ID TC061DaCI_22 : Verify that the deleted company is not displayed in Company combobox when adding new app/device");
		/*
			Pre-condition: Company has at least one brand and one user
			1. Log into DTS portal as a DTS user
			2. Navigate to ï¿½Companiesï¿½ page
			3. Select a company from companies list
			4. Click ï¿½Deleteï¿½ link in 061Da Company info page
			5. Click ï¿½Deleteï¿½ button on delete confirmation dialog
			VP. Verify that the portal is redirected to companies list page and the deleted company is not displayed in companies list
			6. Navigate to ï¿½Apps & Devicesï¿½ page
			7. Click ï¿½Add App or Deviceï¿½ link
			8. List out the ï¿½Companyï¿½ filter combobox
		*/
		/*
		 * Pre-condition: Company has at least one brand and one user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to company page
		companyControl.click(PageHome.LINK_COMPANY);
		// Click Add company link
		companyControl.click(Companies.ADD_COMPANY);
		 // Create company
		Hashtable<String,String> data = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), data);
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to ï¿½Companiesï¿½ page
		companyControl.click(PageHome.LINK_COMPANY);
		// 3. Select a company from companies list
		String companyName = data.get("name");
		companyControl.selectACompanyByName(companyName);
		// 4. Click ï¿½Deleteï¿½ link in 061Da Company info page
		// 5. Click ï¿½Deleteï¿½ button on delete confirmation dialog
		companyControl.doDelete(CompanyInfo.DELETE);
		companyControl.waitForElementClickable(Companies.FILTER);
		/*
		 * VP. Verify that the portal is redirected to companies list page
		 */
		Assert.assertEquals(companyControl.existsElement(Companies.getListElements()), true);
		/*
		 * Verify that The deleted company is not displayed in Companies list
		 */
		Assert.assertFalse(companyControl.checkACompanyExistByName( companyName));
		// 6. Navigate to ï¿½Apps & Devicesï¿½ page
		companyControl.click(PageHome.LINK_APP_DEVICES);
		// 7. Click ï¿½Add App or Deviceï¿½ link
		companyControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 8. List out the ï¿½Companyï¿½ filter combobox
		ArrayList<String> companyList = companyControl.getAllComboboxOption(DeviceEdit.COMPANY);
		/*
		 * Verify that the deleted company is not displayed in ï¿½Companyï¿½ combobox
		 */
		Assert.assertFalse(ListUtil.checkListContain(companyList, companyName));
	}
	
}
