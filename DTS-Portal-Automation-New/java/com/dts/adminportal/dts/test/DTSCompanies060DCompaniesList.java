package com.dts.adminportal.dts.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddBrand;
import com.dts.adminportal.model.AddCompany;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.Companies;
import com.dts.adminportal.model.CompanyInfo;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.util.DateUtil;
import com.dts.adminportal.util.DbUtil;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.MailUtil;
import com.dts.adminportal.util.TestData;

public class DTSCompanies060DCompaniesList extends BasePage {

	@Override
	protected void initData() {
	}	
	
	/*
	 * Verify that the company list page displays proper information
	 */
	@Test
	public void TC060DCL_01() {
		companyControl.addLog("ID : TC060DCL_01 : Verify that the company list page displays proper information");
		/*
	  		Pre-condition: User has full site privileges rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			"VP: The 060D company list page is displayed
			VP:The companies table is only displayed active and invited companies with following columns:
			Company, Status, Partner Type, All Products, Published Products, Variants.
			VP: The Filter dropdown contains:
			All Active, Recently Added, Requesting Publication Credits, Low on Publication Credits, Out of Publication Credits,  Invitations, Non-Partners, Watch List, Contract Ending Soon, Inactive for 90 days, Excessive Aging and Suspended items."
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		/*
		 * Verify that The 060D company list page is displayed
		 */
		Assert.assertEquals(true, companyControl.existsElement(Companies.getListElements()));
		// Get table column headers
		ArrayList<String> columnHeaders = companyControl.getAllColumnHeader(Companies.TABLE);
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
		Assert.assertEquals(companyControl.getItemSelected(Companies.FILTER), "All Active");
		Assert.assertTrue(ListUtil.containsAll(list_expected, columnHeaders));
		
		// List out all items of Filter dropdown
		ArrayList<String> filterOptions = companyControl.getOptionList(Companies.FILTER);
		/*
		 * Verify that the company table is displayed with default filter 'All Active' correctly
		 */
		Assert.assertTrue(ListUtil.containsAll(filterOptions, Companies.option));
	}
	
	
	/*
	 * 	Verify that the company table is displayed with filter "Pending Invitation" correctly.
	 */
	@Test
	public void TC060DCL_04() {
		companyControl.addLog("ID TC060DCL_04 : Verify that the company table is displayed with filter 'Pending Invitation' correctly.");
		/*
		 * 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Set the Filter dropdown to "Pending Invitation" item.
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Set the Filter dropdown to "Pending Invitation" item.
		companyControl.selectOptionByName(Companies.FILTER, Companies.option[2]);
		/*
		 * The companies table is only displayed active and invited companies with following columns: Company, Status, Invitation Date, Time Elapsed
		 */
		ArrayList<String> list_expected = new ArrayList<String>();
		list_expected.add("Company");
		list_expected.add("Status");
		list_expected.add("Invitation Date");
		list_expected.add("Time Elapsed");
		ArrayList<String> list_current = companyControl.getAllColumnHeader(Companies.TABLE);
		Assert.assertTrue(ListUtil.containsAll(list_current, list_expected));
	}
	
	/*
	 * Verify that the company table is displayed with filter "Watch List" correctly
	 */
	@Test
	public void TC060DCL_05() {
		companyControl.addLog("ID TC060DCL_05 : Verify that the company table is displayed with filter 'Watch List' correctly");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Set the Filter dropdown to "Watch List" item
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Set the Filter dropdown to "Watch List" item
		companyControl.selectFilterByName(Companies.FILTER, Companies.option[4]);
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
		ArrayList<String> list_current = companyControl.getAllColumnHeader(Companies.TABLE);
		Assert.assertTrue(ListUtil.containsAll(list_current, list_expected));
	}
	
	/*
	Verify that the company table is displayed with filter "Contract Ending Soon" correctly.
	 */
	@Test
	public void TC060DCL_06() {
		companyControl.addLog("ID TC060DCL_06 : Verify that the company table is displayed with filter 'Contract Ending Soon' correctly.");
		companyControl.addErrorLog("PDPP-126: Failed by Bug");
		/*
		 * 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Companies" tab
			4. Set the Filter dropdown to "Contract Ending Soon" item.
		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		
		// 4. Set the Filter dropdown to "Contract Ending Soon" item.
		companyControl.selectOptionByName(Companies.FILTER, Companies.option[8]);
		/*
		 * Companies which are Contract Ending within the next 90
		 */
		Assert.assertEquals(true, companyControl.checkTimeEnding(Companies.TABLE_BODY, 3, 90));
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Set the Filter dropdown to "Inactive for 90 days" item.
		companyControl.selectOptionByName(Companies.FILTER, Companies.option[6]);
		/*
		 * The companies table is only displayed active and invited companies with following columns: Company, Status,Last Actice Date and Time Elapsed
		 */
		ArrayList<String> list_expected = new ArrayList<String>();
		list_expected.add("Company");
		list_expected.add("Status");
		list_expected.add("Last Active Date");
		list_expected.add("Time Elapsed");
		ArrayList<String> list_current = companyControl.getAllColumnHeader(Companies.TABLE);
		Assert.assertTrue(ListUtil.containsAll(list_current, list_expected));
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 4. Set the Filter dropdown to "Excessive Aging" item.
		companyControl.selectOptionByName(Companies.FILTER, Companies.option[7]);
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
		ArrayList<String> list_current = companyControl.getAllColumnHeader(Companies.TABLE);
		Assert.assertTrue(ListUtil.containsAll(list_current, list_expected));
	}
	
	/*
	 * Verify that the invited partner user displays correct info when filtering report for 'All Active', 'Recently Added', 'Watch List', 'Contract Ending Soon' and 'Suspended'
	 */
	@Test
	public void TC060DCL_09() {
		Reporter.log("ID TC060DCL_09 : Verify that the invited partner user displays correct info when filtering report for 'All Active', 'Recently Added', 'Watch List', 'Contract Ending Soon' and 'Suspended'");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Companies" page
			4. Create new invited partner company successfully
			5. Navigate to 'Products' page
			6. Add new product for newly created company above successfully
			7. Publish new product successfully
			8. Add new variant for above product successfully
			9. Navigate to 'Companies' page
			10. Set the report filtering as 'All Active', 'Recently Added', 'Watch List', 'Contract Ending Soon'
			VP: Verify that the value of 'Status', 'Partner Type', 'Watch Items', 'Contract Expiration', 'Date Added' 'All Products', 'Published Products' and 'Variants' is displayed correctly.
			11. Suspend above company successfully
			12. Set the report filtering as 'Suspended'
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Companies" page
		companyControl.click(PageHome.LINK_COMPANY);
		// Click Add company link
		companyControl.click(Companies.ADD_COMPANY);
		// 4. Create new invited partner company successfully
		Hashtable<String,String> dataCompany = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), dataCompany);
		// Create new user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		dataUser.remove("company");
		userControl.addUser(AddUser.getPartnerUser(), dataUser);
		// Create new brand
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), dataBrand);
		// 5. Navigate to 'Products' page
		companyControl.click(PageHome.linkAccessories);
		// Click Add product link
		companyControl.click(ProductModel.ADD_PRODUCT);
		// 6. Add new product for newly created company above successfully
		// 7. Publish new product successfully
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		dataProduct.put("company", dataCompany.get("name"));
		dataProduct.put("brand", dataBrand.get("name"));
		//productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(),dataProduct);
		// Click Add new variant link
		companyControl.click(ProductDetailModel.ADD_VARIANT);
		// 8. Add new variant for above product successfully
		Hashtable<String,String> dataVariant = TestData.variantData(false, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		// 9. Navigate to 'Companies' page
		companyControl.click(PageHome.LINK_COMPANY);
		// 10. Set the report filtering as 'All Active'
		companyControl.selectFilterByName(Companies.FILTER, Companies.option[0]);
		/*
		 * VP: Verify that the value of 'Status', 'Partner Type', 'All Products', 'Published Products' and 'Variants' is displayed correctly
		 */
		Assert.assertTrue(companyControl.checkACompanyExistByName(dataCompany.get("name")));
		ArrayList<String> companyInfo = companyControl.getTableRowValue(Companies.TABLE, companyControl.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(1), "Invited");
		Assert.assertEquals(companyInfo.get(2), "Partner");
		Assert.assertEquals(companyInfo.get(3), "1");
		Assert.assertEquals(companyInfo.get(4), "1");
		Assert.assertEquals(companyInfo.get(5), "1");
		// Set the report filtering as 'Recently Added'
		companyControl.selectFilterByName(Companies.FILTER, Companies.option[1]);
		/*
		 * VP: Verify that the value of 'Date Added' is displayed correctly
		 */
		Assert.assertTrue(companyControl.checkACompanyExistByName(dataCompany.get("name")));
		companyInfo = companyControl.getTableRowValue(Companies.TABLE, companyControl.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(3), new SimpleDateFormat("MMMM dd, yyyy").format(new Date()));
		// Set the invited date of company above back to 3 months
		String date = DateUtil.getADateGreaterThanToday("yyyy-MM-dd", -100);
		String sql = "update partner set invited_at = '" + date + "' where name = '" + dataCompany.get("name") + "'";
		DbUtil.updateStatment(sql);
		// Set the report filtering as 'Watch List'
		companyControl.selectFilterByName(Companies.FILTER, Companies.option[4]);
		/*
		 * VP: Verify that the value of 'Watch Items' is displayed correctly
		 */
		Assert.assertTrue(companyControl.checkACompanyExistByName(dataCompany.get("name")));
		companyInfo = companyControl.getTableRowValue(Companies.TABLE, companyControl.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(2), "Invited Status 90+ Days");
		// Set the report filtering as 'Contract Ending Soon'
		companyControl.selectFilterByName(Companies.FILTER, Companies.option[5]);
		/*
		 * VP: Verify that the value of 'Contract Expiration' is displayed correctly
		 */
		Assert.assertTrue(companyControl.checkACompanyExistByName(dataCompany.get("name")));
		companyInfo = companyControl.getTableRowValue(Companies.TABLE, companyControl.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(2), DateUtil.getADateGreaterThanToday("MMMM dd, yyyy", 3));
		// 11. Suspend above company successfully
		companyControl.selectACompanyByName(dataCompany.get("name"));
		companyControl.click(CompanyInfo.SUSPEND);
		companyControl.selectConfirmationDialogOption("Suspend");
		// 12. Set the report filtering as 'Suspended'
		companyControl.selectFilterByName(Companies.FILTER, Companies.option[8]);
		/*
		 *  Verify that the value of 'Status' is displayed correctly
		 */
		Assert.assertTrue(companyControl.checkACompanyExistByName(dataCompany.get("name")));
		companyInfo = companyControl.getTableRowValue(Companies.TABLE, companyControl.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(1), "Suspended");
		// Delete company
		companyControl.deleteACompanyByName(dataCompany.get("name"));
	}
	
	/*
	 * Verify that the invited partner user displays correct info when filtering report for 'All Active', 'Recently Added', 'Watch List', 'Contract Ending Soon' and 'Suspended'
	 */
	@Test
	public void TC060DCL_10() {
		companyControl.addLog("ID TC060DCL_10 : Verify that the invited partner user displays correct info when filtering report for 'All Active', 'Recently Added', 'Watch List', 'Contract Ending Soon' and 'Suspended'");
		companyControl.addErrorLog("PDPP-1321: Failed by bug");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Companies" page
			4. Create new partner company successfully
			5. Active user of partner company successfully
			6. Navigate to 'Products' page
			7. Add new product for newly created company above successfully
			8 Publish new product successfully
			9. Add new variant for above product successfully
			10. Navigate to 'Companies' page
			11. Set the report filtering as 'All Active', 'Recently Added', 'Contract Ending Soon'
			VP: Verify that the value of 'Status', 'Partner Type', 'Watch Items', 'Contract Expiration', 'Date Added' 'All Products', 'Published Products' and 'Variants' are displayed correctly.
			12. Log out DTS portal
			13. Log into DTS portal as activated user successfully
			14. Repeat from steps 6th to 9th
			15. Log out DTS portal
			16. Log into DTS portal as DTS user successfully
			17. Navigate to 'Companies' page
			18. Set the report filtering as 'All Active', 'Recently Added', 'Contract Ending Soon'
			VP: Verify that the value of 'Status', 'Partner Type', 'Watch Items', 'Contract Expiration', 'Date Added' 'All Products', 'Published Products' and 'Variants' are displayed correctly.
			19. Suspend above company successfully
			20. Set the report filtering as 'Suspended'
		 */
		/*
		 * PreCondition: Prepare for create new active user
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD);
		// Navigate to User page
		companyControl.click(PageHome.LINK_USERS);
		// Delete user if exist
		userControl.deleteUserByEmail(PARTNER_DTS_EMAIL);
		/*
		 * *************************************************
		 */
		// 3. Navigate to "Companies" page
		companyControl.click(PageHome.LINK_COMPANY);
		// Click Add company link
		companyControl.click(Companies.ADD_COMPANY);
		companyControl.addErrorLog("PDPP-1321 - 092Pa Create User Blank: Partner user with the same email address cannot be created again after deleting its own company");
		Assert.assertTrue(false);
		// 4. Create new partner company successfully
		Hashtable<String,String> dataCompany = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), dataCompany);
		// Create new user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		dataUser.remove("company");
		dataUser.put("email", PARTNER_DTS_EMAIL);
		userControl.addUser(AddUser.getPartnerUser(), dataUser);
		// Create new brand
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), dataBrand);
		// 5. Active user of partner company successfully
		MailUtil.waitForNewInboxMessage(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD, messageCount);
		userControl.activeNewUserViaEmail(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL,EMAIL_PASSWORD, NEW_ACTIVE_USER_PASSWORD);
		// 6. Navigate to 'Products' page
		companyControl.click(PageHome.linkAccessories);
		// Click Add product link
		companyControl.click(ProductModel.ADD_PRODUCT);
		// 7. Add new product for newly created company above successfully
		// 8. Publish new product successfully
		Hashtable<String,String> dataProduct1 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		dataProduct1.put("company", dataCompany.get("name"));
		dataProduct1.put("brand", dataBrand.get("name"));
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct1);
		// Click Add new variant link
		companyControl.click(ProductDetailModel.ADD_VARIANT);
		// 9. Add new variant for above product successfully
		Hashtable<String,String> dataVariant1 = TestData.variantData(false, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant1);
		// 10. Navigate to 'Companies' page
		companyControl.click(PageHome.LINK_COMPANY);
		// 11. Set the report filtering as 'All Active'
		companyControl.selectFilterByName(Companies.FILTER, Companies.option[0]);
		/*
		 * VP: Verify that the value of 'Status', 'Partner Type', 'All Products', 'Published Products' and 'Variants' is displayed correctly
		 */
		Assert.assertTrue(companyControl.checkACompanyExistByName(dataCompany.get("name")));
		ArrayList<String> companyInfo = companyControl.getTableRowValue(Companies.TABLE, companyControl.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(1), "Active");
		Assert.assertEquals(companyInfo.get(2), "Partner");
		Assert.assertEquals(companyInfo.get(3), "1");
		Assert.assertEquals(companyInfo.get(4), "1");
		Assert.assertEquals(companyInfo.get(5), "1");
		// Set the report filtering as 'Recently Added'
		companyControl.selectFilterByName(Companies.FILTER, Companies.option[1]);
		/*
		 * VP: Verify that the value of 'Date Added' is displayed correctly
		 */
		Assert.assertTrue(companyControl.checkACompanyExistByName(dataCompany.get("name")));
		companyInfo = companyControl.getTableRowValue(Companies.TABLE, companyControl.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(3), new SimpleDateFormat("MMMM dd, yyyy").format(new Date()));
		// Set the report filtering as 'Contract Ending Soon'
		companyControl.selectFilterByName(Companies.FILTER, Companies.option[5]);
		/*
		 * VP: Verify that the value of 'Contract Expiration' is displayed correctly
		 */
		Assert.assertTrue(companyControl.checkACompanyExistByName(dataCompany.get("name")));
		companyInfo = companyControl.getTableRowValue(Companies.TABLE, companyControl.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(2), DateUtil.getADateGreaterThanToday("MMMM dd, yyyy", 3));
		// 12. Log out DTS portal
		companyControl.logout();
		// 13. Log into DTS portal as activated user successfully
		loginControl.login(PARTNER_DTS_EMAIL, NEW_ACTIVE_USER_PASSWORD);
		// 14. Repeat from steps 6th to 9th
		// Navigate to 'Products' page
		companyControl.click(PageHome.linkAccessories);
		// Click Add product link
		companyControl.click(ProductModel.ADD_PRODUCT);
		// Add new product for newly created company above successfully
		Hashtable<String,String> dataProduct2 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		dataProduct2.put("company", dataCompany.get("name"));
		dataProduct2.put("brand", dataBrand.get("name"));
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct2);
		// Click Add new variant link
		companyControl.click(ProductDetailModel.ADD_VARIANT);
		// Add new variant for above product successfully
		Hashtable<String,String> dataVariant2 = TestData.variantData(false, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant2);
		// 15. Log out DTS portal
		companyControl.logout();
		// 16. Log into DTS portal as DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 17. Navigate to 'Companies' page
		companyControl.click(PageHome.LINK_COMPANY);
		// 11. Set the report filtering as 'All Active'
		companyControl.selectFilterByName(Companies.FILTER, Companies.option[0]);
		/*
		 * VP: Verify that the value of 'Status', 'Partner Type', 'All Products', 'Published Products' and 'Variants' is displayed correctly
		 */
		Assert.assertTrue(companyControl.checkACompanyExistByName(dataCompany.get("name")));
		companyInfo = companyControl.getTableRowValue(Companies.TABLE, companyControl.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(1), "Active");
		Assert.assertEquals(companyInfo.get(2), "Partner");
		Assert.assertEquals(companyInfo.get(3), "2");
		Assert.assertEquals(companyInfo.get(4), "1");
		Assert.assertEquals(companyInfo.get(5), "2");
		// Set the report filtering as 'Recently Added'
		companyControl.selectFilterByName(Companies.FILTER, Companies.option[1]);
		/*
		 * VP: Verify that the value of 'Date Added' is displayed correctly
		 */
		Assert.assertTrue(companyControl.checkACompanyExistByName(dataCompany.get("name")));
		companyInfo = companyControl.getTableRowValue(Companies.TABLE, companyControl.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(3), new SimpleDateFormat("MMMM dd, yyyy").format(new Date()));
		// Set the report filtering as 'Contract Ending Soon'
		companyControl.selectFilterByName(Companies.FILTER, Companies.option[5]);
		/*
		 * VP: Verify that the value of 'Contract Expiration' is displayed correctly
		 */
		Assert.assertTrue(companyControl.checkACompanyExistByName(dataCompany.get("name")));
		companyInfo = companyControl.getTableRowValue(Companies.TABLE, companyControl.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(2), DateUtil.getADateGreaterThanToday("MMMM dd, yyyy", 3));
	 	// 19. Suspend above company successfully
		companyControl.selectACompanyByName(dataCompany.get("name"));
		companyControl.click(CompanyInfo.SUSPEND);
		companyControl.selectConfirmationDialogOption("Suspend");
		// 20. Set the report filtering as 'Suspended'
		companyControl.selectFilterByName(Companies.FILTER, Companies.option[8]);
		/*
		 *  Verify that the value of 'Status' is displayed correctly
		 */
		Assert.assertTrue(companyControl.checkACompanyExistByName(dataCompany.get("name")));
		companyInfo = companyControl.getTableRowValue(Companies.TABLE, companyControl.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(1), "Suspended");
		// Delete company
		companyControl.deleteACompanyByName(dataCompany.get("name"));
	}
	
	/*
	 * Verify that the activated non-partner user displays correct info when filtering report for 'All Active', 'Recently Added', 'Non-Partner', 'Watch List' and 'Suspended'
	 */
	@Test
	public void TC060DCL_11() {
		Reporter.log("ID TC060DCL_11 : Verify that the activated non-partner user displays correct info when filtering report for ï¿½All Activeï¿½, ï¿½Recently Addedï¿½, ï¿½Non-Partnerï¿½, ï¿½Watch Listï¿½ and ï¿½Suspendedï¿½");
		companyControl.addErrorLog("PDPP-1321: Failed by bug");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Companies" page
			4. Create new activated non-partner company successfully
			5. Navigate to 'Products' page
			6. Add new product for newly created company above successfully
			7. Publish new product successfully
			8. Add new variant for above product successfully
			9. Navigate to 'Companies' page
			10. Set the report filtering as 'All Active', 'Recently Added', 'Non-Partner', 'Watch List'
			VP:  Verify that the value of 'Status', 'Partner Type', 'Watch Items', 'Date Added', 'All Products', 'Published Products' and 'Variants' are displayed correctly.
			11. Suspend above company successfully
			12. Set the report filtering as 'Suspended'
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Companies" page
		companyControl.click(PageHome.LINK_COMPANY);
		// Click Add company link
		companyControl.click(Companies.ADD_COMPANY);
		companyControl.addErrorLog("PDPP-1321 - 092Pa Create User Blank: Partner user with the same email address cannot be created again after deleting its own company");
		Assert.assertTrue(false);
	}
	
	/*
	 * Verify that the invited partner user displays correct info when filtering report for 'Pending Invitations'
	 */
	@Test
	public void TC060DCL_12() throws ParseException {
		companyControl.addLog("ID TC060DCL_12 : Verify that the invited partner user displays correct info when filtering report for 'Pending Invitations'");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Companies" page
			4. Create new invited partner company successfully
			5. Navigate to 'Companies' page
			6. Set the report filtering as 'Pending Invitations'
			VP:  Verify that the value of 'Status', 'Invitations Date' and 'Time Elapsed' are displayed correctly
			7. Change the Invitation Date of above company
			8. Set the report filtering as 'Pending Invitations'
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Companies" page
		companyControl.click(PageHome.LINK_COMPANY);
		// Click Add company link
		companyControl.click(Companies.ADD_COMPANY);
		// 4. Create new invited partner company successfully
		Hashtable<String,String> dataCompany = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), dataCompany);
		// 5. Navigate to 'Companies' page
		companyControl.click(PageHome.LINK_COMPANY);
		// 6. Set the report filtering as 'Pending Invitations'
		companyControl.selectFilterByName(Companies.FILTER, Companies.option[2]);
		/*
		 * VP:  Verify that the value of 'Status', 'Invitations Date' and 'Time Elapsed' are displayed correctly
		 */
		Assert.assertTrue(companyControl.checkACompanyExistByName(dataCompany.get("name")));
		ArrayList<String> companyInfo = companyControl.getTableRowValue(Companies.TABLE, companyControl.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
		Assert.assertEquals(companyInfo.get(1), "Invited");
		Assert.assertEquals(companyInfo.get(2), new SimpleDateFormat("MMMM dd, yyyy").format(new Date()));
		Assert.assertEquals((int) Float.parseFloat(companyInfo.get(3).replace(" days", "")), 0);
		// 7. Change the Invitation Date of above company
		String dateChange = DateUtil.getADateGreaterThanToday("yyyy-MM-dd", -30);
		String sql = "update partner set invited_at = '" + dateChange + "' where name = '" + dataCompany.get("name") + "'";
		DbUtil.updateStatment(sql);
		// 8. Set the report filtering as 'Pending Invitations'
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.selectFilterByName(Companies.FILTER, Companies.option[2]);
		/*
		 * Verify that the value of  'Invitations Date' and 'Time Elapsed' are calculated and displayed correctly
		 */
		Assert.assertTrue(companyControl.checkACompanyExistByName(dataCompany.get("name")));
		companyInfo = companyControl.getTableRowValue(Companies.TABLE, companyControl.getRowIndexByName(Companies.TABLE, dataCompany.get("name")));
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
		companyControl.deleteACompanyByName(dataCompany.get("name"));
	}
	
	/*
	 * Verify that the invited partner user displays correct info when filtering report for 'Inactive for 90 days'
	 */
	@Test
	public void TC060DCL_13() {
		Reporter.log("ID TC060DCL_13 : Verify that the invited partner user displays correct info when filtering report for 'Inactive for 90 days'");
		companyControl.addErrorLog("PDPP-1210: 060D Companies List: Caculating for \"Inactive for 90 days\" and \"Execessive Aging\" are not implemented yet");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Companies" page
			4. Create new invited partner company successfully
			5. Navigate to 'Companies' page
			6. Set the report filtering as 'Inactive for 90 days'
			VP: Verify that the value of 'Status', 'Last Active Date' and 'Time Elapsed' are displayed correctly
			7. Change the value of 'Last Active Date' of above company
			8. Set the report filtering as 'Pending Invitations'
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Companies" page
		companyControl.click(PageHome.LINK_COMPANY);
		// Click Add company link
		companyControl.click(Companies.ADD_COMPANY);
		// 4. Create new invited partner company successfully
		Hashtable<String,String> dataCompany = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), dataCompany);
		// 5. Navigate to 'Companies' page
		companyControl.click(PageHome.LINK_COMPANY);
		// 6. Set the report filtering as 'Inactive for 90 days'
		companyControl.selectFilterByName(Companies.FILTER, Companies.option[6]);
		companyControl.addErrorLog("PDPP-1210: 060D Companies List: Caculating for \"Inactive for 90 days\" and \"Execessive Aging\" are not implemented yet");
		Assert.assertTrue(false);
	}
	
	/*
	 * Verify that the company type does not change from Partner to Non-Partner when partner user change his company info.
	 */
	@Test
	public void TC060DCL_14(){
		companyControl.addLog("ID 060DCL_14: Verify that the company type does not change from Partner to Non-Partner when partner user change his company info.");
		companyControl.addErrorLog("Not yet implemented");
	}
	
	
}
