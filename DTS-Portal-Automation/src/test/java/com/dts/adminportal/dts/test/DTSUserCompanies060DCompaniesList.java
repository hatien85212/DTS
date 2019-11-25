package com.dts.adminportal.dts.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.DTSUtil;
import com.dts.adminportal.util.DateUtil;
import com.dts.adminportal.util.DbHandler;
import com.dts.adminportal.util.MailUtil;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AccessoryInfo;
import dts.com.adminportal.model.AccessoryMain;
import dts.com.adminportal.model.AddAccessory;
import dts.com.adminportal.model.AddBrand;
import dts.com.adminportal.model.AddCompany;
import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.AddVariant;
import dts.com.adminportal.model.Companies;
import dts.com.adminportal.model.CompanyInfo;
import dts.com.adminportal.model.Xpath;

public class DTSUserCompanies060DCompaniesList extends CreatePage {
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
	 * Verify that the company list page is displayed when selecting "Company" tab
	 */
	@Test
	public void TC060DCL_01() {
		result.addLog("ID : TC060DCL_01 : Verify that the company list page is displayed when selecting 'Company' tab");
		/*
	  		Pre-condition: User has full site privileges rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		/*
		 * Verify that The 060D company list page is displayed
		 */
		Assert.assertEquals("Pass", home.existsElement(Companies.getListElements()).getResult());
	}
	
	/*
	 * Verify that the company table is displayed with default filter "All Active" correctly
	 */
	@Test
	public void TC060DCL_02() {
		result.addLog("ID : TC060DCL_02 : Verify that the company table is displayed with default filter 'All Active' correctly");
		/*
		  	Pre-condition: User has full site privileges rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// Get table column headers
		ArrayList<String> columnHeaders = home.getAllColumnHeader(Companies.TABLE);
		/*
		 * The companies table is only displayed active and invited companies
		 * with following columns: Company, Status, Partner Type, All Products,
		 * Published Products, Variants.
		 */
		ArrayList<String> list_expected = new ArrayList<String>();
		list_expected.add("Company");
		list_expected.add("Status");
		list_expected.add("Partner Type");
		list_expected.add("All Products");
		list_expected.add("Published Products");
		list_expected.add("Variants");
		Assert.assertEquals(home.getItemSelected(Companies.FILTER), "All Active");
		Assert.assertTrue(DTSUtil.containsAll(list_expected, columnHeaders));
	}
	
	/*
	 * Verify that the Filter dropdown contains filter items fully
	 */
	@Test
	public void TC060DCL_03() {
		result.addLog("ID TC060DCL_03 : Verify that the Filter dropdown contains filter items fully");
		/*
		  	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. List out all items of Filter dropdown
		*/
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. List out all items of Filter dropdown
		ArrayList<String> filterOptions = home.getAllOption(Companies.FILTER);
		/*
		 * Verify that the company table is displayed with default filter 'All Active' correctly
		 */
		Assert.assertTrue(DTSUtil.containsAll(filterOptions, Companies.option));
	}
	
	/*
	 * 	Verify that the company table is displayed with filter "Pending Invitation" correctly.
	 */
	@Test
	public void TC060DCL_04() {
		result.addLog("ID TC060DCL_04 : Verify that the company table is displayed with filter 'Pending Invitation' correctly.");
		/*
		 * 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Set the Filter dropdown to "Pending Invitation" item.
		 */
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Set the Filter dropdown to "Pending Invitation" item.
		home.selectOptionByName(Companies.FILTER, Companies.option[2]);
		/*
		 * The companies table is only displayed active and invited companies with following columns: Company, Status, Invitation Date, Time Elapsed
		 */
		ArrayList<String> list_expected = new ArrayList<String>();
		list_expected.add("Company");
		list_expected.add("Status");
		list_expected.add("Invitation Date");
		list_expected.add("Time Elapsed");
		ArrayList<String> list_current = home.getAllColumnHeader(Companies.TABLE);
		Assert.assertTrue(DTSUtil.containsAll(list_current, list_expected));
	}
	
	/*
	 * Verify that the company table is displayed with filter "Watch List" correctly
	 */
	@Test
	public void TC060DCL_05() {
		result.addLog("ID TC060DCL_05 : Verify that the company table is displayed with filter 'Watch List' correctly");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Set the Filter dropdown to "Watch List" item
		 */
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Set the Filter dropdown to "Watch List" item
		home.selectFilterByName(Companies.FILTER, Companies.option[4]);
		/*
		 * The companies table is only displayed active and invited companies with following columns: Company, Status, Watch Items, All, Published and Variant columns
		 */
		ArrayList<String> list_expected = new ArrayList<String>();
		list_expected.add("Company");
		list_expected.add("Status");
		list_expected.add("Watch Items");
		list_expected.add("All Products");
		list_expected.add("Published Products");
		list_expected.add("Variants");
		ArrayList<String> list_current = home.getAllColumnHeader(Companies.TABLE);
		Assert.assertTrue(DTSUtil.containsAll(list_current, list_expected));
	}
	
	/*
	Verify that the company table is displayed with filter "Contract Ending Soon" correctly.
	 */
	@Test
	public void TC060DCL_06() {
		result.addLog("ID TC060DCL_06 : Verify that the company table is displayed with filter 'Contract Ending Soon' correctly.");
		/*
		 * 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Set the Filter dropdown to "Contract Ending Soon" item.
		 */
		// 4. Set the Filter dropdown to "Contract Ending Soon" item.
		home.selectOptionByName(Companies.FILTER, Companies.option[8]);
		/*
		 * Companies which are Contract Ending within the next 90
		 */
		result = home.checkTimeEnding(Companies.TABLE_BODY, 3, 90);
		Assert.assertEquals("Pass", result.getResult());
	}
	
	/*
	 * Verify that the company table is displayed with filter "Inactive for 90 days" correctly.
	 */
	@Test
	public void TC060DCL_07() {
		Reporter.log("ID TC060DCL_07 : Verify that the company table is displayed with filter 'Inactive for 90 days' correctly");
		/*
	 *	 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Set the Filter dropdown to "Inactive for 90 days" item.
		 */
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Set the Filter dropdown to "Inactive for 90 days" item.
		home.selectOptionByName(Companies.FILTER, Companies.option[6]);
		/*
		 * The companies table is only displayed active and invited companies with following columns: Company, Status,Last Actice Date and Time Elapsed
		 */
		ArrayList<String> list_expected = new ArrayList<String>();
		list_expected.add("Company");
		list_expected.add("Status");
		list_expected.add("Last Active Date");
		list_expected.add("Time Elapsed");
		ArrayList<String> list_current = home.getAllColumnHeader(Companies.TABLE);
		Assert.assertTrue(DTSUtil.containsAll(list_current, list_expected));
	}
	
	/*
	 * Verify that the company table is displayed with filter "Excessive Aging" correctly
	 */
	@Test
	public void TC060DCL_08() {
		Reporter.log("ID TC060DCL_08 : Verify that the company table is displayed with filter 'Excessive Aging' correctly");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Set the Filter dropdown to "Excessive Aging" item.
		 */
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_COMPANY);
		// 4. Set the Filter dropdown to "Excessive Aging" item.
		home.selectOptionByName(Companies.FILTER, Companies.option[7]);
		/*
		 * The companies table is only displayed active and invited companies with following columns: Company, Status, All, DTS Tuning, Partner Tuning, Marketing
		 */
		ArrayList<String> list_expected = new ArrayList<String>();
		list_expected.add("Company");
		list_expected.add("Status");
		list_expected.add("DTS Tuning Review");
		list_expected.add("Partner Tuning Review");
		list_expected.add("Marketing");
		list_expected.add("All Products");
		ArrayList<String> list_current = home.getAllColumnHeader(Companies.TABLE);
		Assert.assertTrue(DTSUtil.containsAll(list_current, list_expected));
	}
	
	/*
	 * Verify that the invited partner user displays correct info when filtering report for “All Active”, “Recently Added”, “Watch List”, “Contract Ending Soon” and “Suspended”
	 */
	@Test
	public void TC060DCL_09() {
		Reporter.log("ID TC060DCL_09 : Verify that the invited partner user displays correct info when filtering report for “All Active”, “Recently Added”, “Watch List”, “Contract Ending Soon” and “Suspended”");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Companies" page
			4. Create new invited partner company successfully
			5. Navigate to “Products” page
			6. Add new product for newly created company above successfully
			7. Publish new product successfully
			8. Add new variant for above product successfully
			9. Navigate to “Companies” page
			10. Set the report filtering as “All Active”, “Recently Added”, “Watch List”, “Contract Ending Soon”
			VP: Verify that the value of “Status”, “Partner Type”, “Watch Items”, “Contract Expiration”, “Date Added” “All Products”, “Published Products” and “Variants” is displayed correctly.
			11. Suspend above company successfully
			12. Set the report filtering as “Suspended”
		 */
		// 3. Navigate to "Companies" page
		home.click(Xpath.LINK_COMPANY);
		// Click Add company link
		home.click(Companies.ADD_COMPANY);
		// 4. Create new invited partner company successfully
		Hashtable<String,String> dataCompany = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), dataCompany);
		// Create new user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		home.addUser(AddUser.getPartnerUser(), dataUser);
		// Create new brand
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		home.addBrand(AddBrand.getHash(), dataBrand);
		// 5. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 6. Add new product for newly created company above successfully
		// 7. Publish new product successfully
		Hashtable<String,String> dataProduct = TestData.accessoryPublish();
		dataProduct.put("company", dataCompany.get("name"));
		dataProduct.put("brand", dataBrand.get("name"));
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct);
		// Click Add new variant link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 8. Add new variant for above product successfully
		Hashtable<String,String> dataVariant = TestData.variantDraft();
		home.addVariant(AddVariant.getHash(), dataVariant);
		// 9. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 10. Set the report filtering as “All Active”
		home.selectFilterByName(Companies.FILTER, Companies.option[0]);
		/*
		 * VP: Verify that the value of “Status”, “Partner Type”, “All Products”, “Published Products” and “Variants” is displayed correctly
		 */
		Assert.assertTrue(home.checkACompanyExistByName(dataCompany.get("name")));
		ArrayList<String> companyInfo = home.getTableRowValue(Companies.TABLE, home.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(1), "Invited");
		Assert.assertEquals(companyInfo.get(2), "Partner");
		Assert.assertEquals(companyInfo.get(3), "1");
		Assert.assertEquals(companyInfo.get(4), "1");
		Assert.assertEquals(companyInfo.get(5), "1");
		// Set the report filtering as “Recently Added”
		home.selectFilterByName(Companies.FILTER, Companies.option[1]);
		/*
		 * VP: Verify that the value of “Date Added” is displayed correctly
		 */
		Assert.assertTrue(home.checkACompanyExistByName(dataCompany.get("name")));
		companyInfo = home.getTableRowValue(Companies.TABLE, home.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(3), new SimpleDateFormat("MMMM dd, yyyy").format(new Date()));
		// Set the invited date of company above back to 3 months
		String date = DateUtil.getADateGreaterThanToday("yyyy-MM-dd", -100);
		String sql = "update partner set invited_at = '" + date + "' where name = '" + dataCompany.get("name") + "'";
		DbHandler.updateStatment(sql);
		// Set the report filtering as “Watch List”
		home.selectFilterByName(Companies.FILTER, Companies.option[4]);
		/*
		 * VP: Verify that the value of “Watch Items” is displayed correctly
		 */
		Assert.assertTrue(home.checkACompanyExistByName(dataCompany.get("name")));
		companyInfo = home.getTableRowValue(Companies.TABLE, home.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(2), "Invited Status 90+ Days");
		// Set the report filtering as “Contract Ending Soon”
		home.selectFilterByName(Companies.FILTER, Companies.option[5]);
		/*
		 * VP: Verify that the value of “Contract Expiration” is displayed correctly
		 */
		Assert.assertTrue(home.checkACompanyExistByName(dataCompany.get("name")));
		companyInfo = home.getTableRowValue(Companies.TABLE, home.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(2), DateUtil.getADateGreaterThanToday("MMMM dd, yyyy", 3));
		// 11. Suspend above company successfully
		home.selectACompanyByName(dataCompany.get("name"));
		home.click(CompanyInfo.SUSPEND);
		home.selectConfirmationDialogOption("Suspend");
		// 12. Set the report filtering as “Suspended”
		home.selectFilterByName(Companies.FILTER, Companies.option[8]);
		/*
		 *  Verify that the value of “Status” is displayed correctly
		 */
		Assert.assertTrue(home.checkACompanyExistByName(dataCompany.get("name")));
		companyInfo = home.getTableRowValue(Companies.TABLE, home.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(1), "Suspended");
		// Delete company
		home.deleteACompanyByName(dataCompany.get("name"));
	}
	
	/*
	 * Verify that the invited partner user displays correct info when filtering report for “All Active”, “Recently Added”, “Watch List”, “Contract Ending Soon” and “Suspended”
	 */
	@Test
	public void TC060DCL_10() {
		Reporter.log("ID TC060DCL_10 : Verify that the invited partner user displays correct info when filtering report for “All Active”, “Recently Added”, “Watch List”, “Contract Ending Soon” and “Suspended”");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Companies" page
			4. Create new partner company successfully
			5. Active user of partner company successfully
			6. Navigate to “Products” page
			7. Add new product for newly created company above successfully
			8 Publish new product successfully
			9. Add new variant for above product successfully
			10. Navigate to “Companies” page
			11. Set the report filtering as “All Active”, “Recently Added”, “Contract Ending Soon”
			VP: Verify that the value of “Status”, “Partner Type”, “Watch Items”, “Contract Expiration”, “Date Added” “All Products”, “Published Products” and “Variants” are displayed correctly.
			12. Log out DTS portal
			13. Log into DTS portal as activated user successfully
			14. Repeat from steps 6th to 9th
			15. Log out DTS portal
			16. Log into DTS portal as DTS user successfully
			17. Navigate to “Companies” page
			18. Set the report filtering as “All Active”, “Recently Added”, “Contract Ending Soon”
			VP: Verify that the value of “Status”, “Partner Type”, “Watch Items”, “Contract Expiration”, “Date Added” “All Products”, “Published Products” and “Variants” are displayed correctly.
			19. Suspend above company successfully
			20. Set the report filtering as “Suspended”
		 */
		/*
		 * PreCondition: Prepare for create new active user
		 */
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server, pdts_email, email_password);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Delete user if exist
		home.deleteUserByEmail(pdts_email);
		/*
		 * *************************************************
		 */
		// 3. Navigate to "Companies" page
		home.click(Xpath.LINK_COMPANY);
		// Click Add company link
		home.click(Companies.ADD_COMPANY);
		result.addErrorLog("PDPP-1321 - 092Pa Create User Blank: Partner user with the same email address cannot be created again after deleting its own company");
		Assert.assertTrue(false);
		// 4. Create new partner company successfully
		Hashtable<String,String> dataCompany = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), dataCompany);
		// Create new user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		dataUser.put("email", pdts_email);
		home.addUser(AddUser.getPartnerUser(), dataUser);
		// Create new brand
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		home.addBrand(AddBrand.getHash(), dataBrand);
		// 5. Active user of partner company successfully
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, pdts_email, email_password, messageCount);
		home.activeNewUserViaEmail(yahoo_imap_server, pdts_email,email_password, new_active_user_password);
		// 6. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 7. Add new product for newly created company above successfully
		// 8. Publish new product successfully
		Hashtable<String,String> dataProduct1 = TestData.accessoryPublish();
		dataProduct1.put("company", dataCompany.get("name"));
		dataProduct1.put("brand", dataBrand.get("name"));
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct1);
		// Click Add new variant link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 9. Add new variant for above product successfully
		Hashtable<String,String> dataVariant1 = TestData.variantDraft();
		home.addVariant(AddVariant.getHash(), dataVariant1);
		// 10. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 11. Set the report filtering as “All Active”
		home.selectFilterByName(Companies.FILTER, Companies.option[0]);
		/*
		 * VP: Verify that the value of “Status”, “Partner Type”, “All Products”, “Published Products” and “Variants” is displayed correctly
		 */
		Assert.assertTrue(home.checkACompanyExistByName(dataCompany.get("name")));
		ArrayList<String> companyInfo = home.getTableRowValue(Companies.TABLE, home.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(1), "Active");
		Assert.assertEquals(companyInfo.get(2), "Partner");
		Assert.assertEquals(companyInfo.get(3), "1");
		Assert.assertEquals(companyInfo.get(4), "1");
		Assert.assertEquals(companyInfo.get(5), "1");
		// Set the report filtering as “Recently Added”
		home.selectFilterByName(Companies.FILTER, Companies.option[1]);
		/*
		 * VP: Verify that the value of “Date Added” is displayed correctly
		 */
		Assert.assertTrue(home.checkACompanyExistByName(dataCompany.get("name")));
		companyInfo = home.getTableRowValue(Companies.TABLE, home.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(3), new SimpleDateFormat("MMMM dd, yyyy").format(new Date()));
		// Set the report filtering as “Contract Ending Soon”
		home.selectFilterByName(Companies.FILTER, Companies.option[5]);
		/*
		 * VP: Verify that the value of “Contract Expiration” is displayed correctly
		 */
		Assert.assertTrue(home.checkACompanyExistByName(dataCompany.get("name")));
		companyInfo = home.getTableRowValue(Companies.TABLE, home.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(2), DateUtil.getADateGreaterThanToday("MMMM dd, yyyy", 3));
		// 12. Log out DTS portal
		home.logout();
		// 13. Log into DTS portal as activated user successfully
		home.login(pdts_email, new_active_user_password);
		// 14. Repeat from steps 6th to 9th
		// Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Add new product for newly created company above successfully
		Hashtable<String,String> dataProduct2 = TestData.accessoryDraft();
		dataProduct2.put("company", dataCompany.get("name"));
		dataProduct2.put("brand", dataBrand.get("name"));
		home.addAccessoriesPartner(AddAccessory.getHash(), dataProduct2);
		// Click Add new variant link
		home.click(AccessoryInfo.ADD_VARIANT);
		// Add new variant for above product successfully
		Hashtable<String,String> dataVariant2 = TestData.variantDraft();
		home.addVariant(AddVariant.getHash(), dataVariant2);
		// 15. Log out DTS portal
		home.logout();
		// 16. Log into DTS portal as DTS user successfully
		home.login(superUsername, superUserpassword);
		// 17. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 11. Set the report filtering as “All Active”
		home.selectFilterByName(Companies.FILTER, Companies.option[0]);
		/*
		 * VP: Verify that the value of “Status”, “Partner Type”, “All Products”, “Published Products” and “Variants” is displayed correctly
		 */
		Assert.assertTrue(home.checkACompanyExistByName(dataCompany.get("name")));
		companyInfo = home.getTableRowValue(Companies.TABLE, home.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(1), "Active");
		Assert.assertEquals(companyInfo.get(2), "Partner");
		Assert.assertEquals(companyInfo.get(3), "2");
		Assert.assertEquals(companyInfo.get(4), "1");
		Assert.assertEquals(companyInfo.get(5), "2");
		// Set the report filtering as “Recently Added”
		home.selectFilterByName(Companies.FILTER, Companies.option[1]);
		/*
		 * VP: Verify that the value of “Date Added” is displayed correctly
		 */
		Assert.assertTrue(home.checkACompanyExistByName(dataCompany.get("name")));
		companyInfo = home.getTableRowValue(Companies.TABLE, home.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(3), new SimpleDateFormat("MMMM dd, yyyy").format(new Date()));
		// Set the report filtering as “Contract Ending Soon”
		home.selectFilterByName(Companies.FILTER, Companies.option[5]);
		/*
		 * VP: Verify that the value of “Contract Expiration” is displayed correctly
		 */
		Assert.assertTrue(home.checkACompanyExistByName(dataCompany.get("name")));
		companyInfo = home.getTableRowValue(Companies.TABLE, home.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(2), DateUtil.getADateGreaterThanToday("MMMM dd, yyyy", 3));
	 	// 19. Suspend above company successfully
		home.selectACompanyByName(dataCompany.get("name"));
		home.click(CompanyInfo.SUSPEND);
		home.selectConfirmationDialogOption("Suspend");
		// 20. Set the report filtering as “Suspended”
		home.selectFilterByName(Companies.FILTER, Companies.option[8]);
		/*
		 *  Verify that the value of “Status” is displayed correctly
		 */
		Assert.assertTrue(home.checkACompanyExistByName(dataCompany.get("name")));
		companyInfo = home.getTableRowValue(Companies.TABLE, home.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(1), "Suspended");
		// Delete company
		home.deleteACompanyByName(dataCompany.get("name"));
	}
	
	/*
	 * Verify that the activated non-partner user displays correct info when filtering report for “All Active”, “Recently Added”, “Non-Partner”, “Watch List” and “Suspended”
	 */
	@Test
	public void TC060DCL_11() {
		Reporter.log("ID TC060DCL_11 : Verify that the activated non-partner user displays correct info when filtering report for “All Active”, “Recently Added”, “Non-Partner”, “Watch List” and “Suspended”");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Companies" page
			4. Create new activated non-partner company successfully
			5. Navigate to “Products” page
			6. Add new product for newly created company above successfully
			7. Publish new product successfully
			8. Add new variant for above product successfully
			9. Navigate to “Companies” page
			10. Set the report filtering as “All Active”, “Recently Added”, “Non-Partner”, “Watch List”
			VP:  Verify that the value of “Status”, “Partner Type”, “Watch Items”, “Date Added”, “All Products”, “Published Products” and “Variants” are displayed correctly.
			11. Suspend above company successfully
			12. Set the report filtering as “Suspended”
		 */
		// 3. Navigate to "Companies" page
		home.click(Xpath.LINK_COMPANY);
		// Click Add company link
		home.click(Companies.ADD_COMPANY);
		result.addErrorLog("PDPP-1321 - 092Pa Create User Blank: Partner user with the same email address cannot be created again after deleting its own company");
		Assert.assertTrue(false);
	}
	
	/*
	 * Verify that the invited partner user displays correct info when filtering report for “Pending Invitations”
	 */
	@Test
	public void TC060DCL_12() throws ParseException {
		Reporter.log("ID TC060DCL_12 : Verify that the invited partner user displays correct info when filtering report for “Pending Invitations”");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Companies" page
			4. Create new invited partner company successfully
			5. Navigate to “Companies” page
			6. Set the report filtering as “Pending Invitations”
			VP:  Verify that the value of “Status”, “Invitations Date” and “Time Elapsed” are displayed correctly
			7. Change the Invitation Date of above company
			8. Set the report filtering as “Pending Invitations”
		 */
		// 3. Navigate to "Companies" page
		home.click(Xpath.LINK_COMPANY);
		// Click Add company link
		home.click(Companies.ADD_COMPANY);
		// 4. Create new invited partner company successfully
		Hashtable<String,String> dataCompany = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), dataCompany);
		// 5. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 6. Set the report filtering as “Pending Invitations”
		home.selectFilterByName(Companies.FILTER, Companies.option[2]);
		/*
		 * VP:  Verify that the value of “Status”, “Invitations Date” and “Time Elapsed” are displayed correctly
		 */
		Assert.assertTrue(home.checkACompanyExistByName(dataCompany.get("name")));
		ArrayList<String> companyInfo = home.getTableRowValue(Companies.TABLE, home.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(1), "Invited");
		Assert.assertEquals(companyInfo.get(2), new SimpleDateFormat("MMMM dd, yyyy").format(new Date()));
		Assert.assertEquals((int) Float.parseFloat(companyInfo.get(3).replace(" days", "")), 0);
		// 7. Change the Invitation Date of above company
		String dateChange = DateUtil.getADateGreaterThanToday("yyyy-MM-dd", -30);
		String sql = "update partner set invited_at = '" + dateChange + "' where name = '" + dataCompany.get("name") + "'";
		DbHandler.updateStatment(sql);
		// 8. Set the report filtering as “Pending Invitations”
		home.click(Xpath.LINK_COMPANY);
		home.selectFilterByName(Companies.FILTER, Companies.option[2]);
		/*
		 * Verify that the value of  “Invitations Date” and “Time Elapsed” are calculated and displayed correctly
		 */
		Assert.assertTrue(home.checkACompanyExistByName(dataCompany.get("name")));
		companyInfo = home.getTableRowValue(Companies.TABLE, home.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(1), "Invited");
		// Convert date change to new format
		dateChange = DateUtil.formatDate("yyyy-MM-dd", "MMMM dd, yyyy", dateChange);
		Assert.assertEquals(companyInfo.get(2), dateChange);
		// Get the distance between invited date and now
		Date dateInvited = new SimpleDateFormat("MMMM dd, yyyy").parse(dateChange);
		Date dateNow = new Date();
		long distance = DateUtil.getDateDiff(dateInvited, dateNow, TimeUnit.DAYS);
		Assert.assertEquals((int) Float.parseFloat(companyInfo.get(3).replace(" days", "")), distance);
		// Delete company
		home.deleteACompanyByName(dataCompany.get("name"));
	}
	
	/*
	 * Verify that the invited partner user displays correct info when filtering report for “Inactive for 90 days”
	 */
	@Test
	public void TC060DCL_13() {
		Reporter.log("ID TC060DCL_13 : Verify that the invited partner user displays correct info when filtering report for “Inactive for 90 days”");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Companies" page
			4. Create new invited partner company successfully
			5. Navigate to “Companies” page
			6. Set the report filtering as “Inactive for 90 days”
			VP: Verify that the value of “Status”, “Last Active Date” and “Time Elapsed” are displayed correctly
			7. Change the value of “Last Active Date” of above company
			8. Set the report filtering as “Pending Invitations”
		 */
		// 3. Navigate to "Companies" page
		home.click(Xpath.LINK_COMPANY);
		// Click Add company link
		home.click(Companies.ADD_COMPANY);
		// 4. Create new invited partner company successfully
		Hashtable<String,String> dataCompany = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), dataCompany);
		// 5. Navigate to “Companies” page
		home.click(Xpath.LINK_COMPANY);
		// 6. Set the report filtering as “Inactive for 90 days”
		home.selectFilterByName(Companies.FILTER, Companies.option[6]);
		result.addErrorLog("PDPP-1210: 060D Companies List: Caculating for \"Inactive for 90 days\" and \"Execessive Aging\" are not implemented yet");
		Assert.assertTrue(false);
	}
	
}
