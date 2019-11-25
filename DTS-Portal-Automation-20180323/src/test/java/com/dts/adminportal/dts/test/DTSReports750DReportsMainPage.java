package com.dts.adminportal.dts.test;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Locale;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.ActiveBrand;
import com.dts.adminportal.model.AddBrand;
import com.dts.adminportal.model.AddCompany;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.BrandInfo;
import com.dts.adminportal.model.Companies;
import com.dts.adminportal.model.CompanyInfo;
import com.dts.adminportal.model.DTSHome;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.PublishedProduct;
import com.dts.adminportal.model.Reports;
import com.dts.adminportal.model.VariantInfo;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.TestData;

public class DTSReports750DReportsMainPage extends BasePage {
	
	@Override
	protected void initData() {
		
	}
	
	/*
	 * Verify that the Reports page under DTS's view displays properly
	 */
	@Test
	public void TC750DRP_01(){
		userControl.addLog("ID : TC750DRP_01 : Verify that the Reports page under DTS's view displays properly");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Reports" page
			VP: The title label ""Reports"" displays on the top of the page
			VP: The ""Product Reports"" module always displays ""Active Brands"", ""Published Products"" links with count number and ""Last Updated"" information label
			VP: The count numbers display next to ""Active Brands"" and ""Published Products"" links:
				- Active Brands: Total number of unique accessory brands in the headphone database
				- Published Products: Total number of headphones in the database"

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		// VP: The title label ""Reports"" displays on the top of the page
		Assert.assertTrue(reportsControl.getTextByXpath(Reports.LABEL).contains(Reports.REPORTS_PAGE));
		// VP: The ""Product Reports"" module always displays ""Active Brands"", ""Published Products"" links with count number and ""Last Updated"" information label
		// VP: The count numbers display next to ""Active Brands"" and ""Published Products"" links
		Assert.assertTrue(userControl.existsElement(Reports.getListInfoReport()));
		
	}
	
	/*
	 * Verify that the the portal redirects to correct pages when clicking on "Active Brands" and "Published Products" links
	 */
	@Test
	public void TC750DRP_02(){
		userControl.addLog("ID : TC750DRP_02 : Verify that the the portal redirects to correct pages when clicking on 'Active Brands' and 'Published Products' links");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Reports" page
			VP: The 750D Reports Page is displayed
			4. Click on "Active Brands" link
			VP: The 761D Reports - Active HP Brands Page is displayed
			5. Navigate to "Reports" page
			VP: The 750D Reports Page is displayed
			6. Click on "Publis.hed Products" link
			VP: The 762D Reports - Published Products Page is displayed

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		// VP: The 750D Reports Page is displayed
		Assert.assertTrue(productControl.isElementPresent(Reports.LABEL));
		Assert.assertTrue(userControl.existsElement(Reports.getListInfoReport()));
		// 4. Click on "Active Brands" link
		reportsControl.click(Reports.ACTIVE_BRANDS);
		// VP: The 761D Reports - Active HP Brands Page is displayed
		Assert.assertTrue(productControl.isElementPresent(ActiveBrand.TITLE_ACTIVE_BRAND));
		// 5. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		// VP: The 750D Reports Page is displayed
		Assert.assertTrue(productControl.isElementPresent(Reports.LABEL));
		Assert.assertTrue(userControl.existsElement(Reports.getListInfoReport()));
		// 6. Click on "Published Products" link
		reportsControl.click(Reports.PUBLISHED_PRODUCTS);
		// VP: The 762D Reports - Published Products Page is displayed
		Assert.assertTrue(productControl.isElementPresent(PublishedProduct.TITLE_PUBLISHED_PRODUCT));
	}
	
	/*
	 * Verify that the "Reports" tab does not display under Partner user view
	 */
	@Test
	public void TC750DRP_03(){
		userControl.addLog("ID : TC750DRP_03 : Verify that the 'Reports' tab does not display under Partner user view");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Reports" page
			VP: The 750D Reports Page is displayed
			4. Switch to Partner view in Partner List on top corner of the page
			VP: The portal redirect to Home page and "Reports" tab is not displayed
			5. Sign out the DTS user
			6. Log into DTS portal as a Partner user successfully
			VP: The "Reports" tab is not displayed

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		// VP: The 750D Reports Page is displayed
		Assert.assertTrue(productControl.isElementPresent(Reports.LABEL));
		Assert.assertTrue(userControl.existsElement(Reports.getListInfoReport()));
		// 4. Switch to Partner view in Partner List on top corner of the page
		reportsControl.selectOptionInDropdown(DTSHome.PARTNERS_LIST_DROPDOWN, PARTNER_COMPANY_NAME);
		// VP: The portal redirect to Home page and "Reports" tab is not displayed
		
		// 5. Sign out the DTS user
		loginControl.logout();
		// 6. Log into DTS portal as a Partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// VP: The "Reports" tab is not displayed
		Assert.assertFalse(reportsControl.isElementPresent(PageHome.LINK_REPORTS));
	}
	
	/*
	 * Verify that the "Last Updated" label displays correct information when create or delete product
	 */
	@Test
	public void TC750DRP_04() throws InterruptedException{
		userControl.addLog("ID : TC750DRP_04 : Verify that the 'Last Updated' label displays correct information when create or delete product");
		/*
			
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Products" page
			4. Create published product successfully
			5. Navigate to "Reports" page
			VP: The ""Last Updated"" label displays the time that the product was added  
			VP: The count numbers next to Published Products links are changed correctly
			6. Navigate to "Products" page
			7. Select above product
			8. Suspend product
			9. Navigate to "Reports" page
			VP: The ""Last Updated"" label displays the time that the product was suspended
			VP: The count numbers next to Published Products links are changed correctly
			10. Navigate to "Products" page
			11. Select above suspended product
			12. Restore above product
			13. Navigate to "Reports" page
			VP: The ""Last Updated"" label displays the time that product was restored
			VP: The count numbers next to Published Products links are changed correctly
			14. Navigate to "Products" page
			15. Select above product 
			16. Delete above product successfully
			17. Navigate to "Reports" page
			VP: The ""Last Updated"" label displays the time that the product has removed
			VP: The count number next to Published Products link is changed correctly

		 */
		
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			// Navigate to "Report" page
			reportsControl.click(PageHome.LINK_REPORTS);
			// get number Products Published
			int numberProduct1 = Integer.parseInt(reportsControl.getTextByXpath(Reports.NUMBER_PUBLISHED_PRODUCTS));
			// 3. Navigate to "Products" page
			// 4. Create published product successfully
			Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
			productWf.addProductAndPublish(dataProduct);
//			String dateDB = reportsControl.getPublisedDateTime(connection);
			reportsControl.click(PageHome.LINK_REPORTS);
			String dateDB = reportsControl.getPublisedDateTime();
//			String Tracking_ID = productControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
//			String dateDB = reportsControl.getPublisedDateProduct(Tracking_ID);
			// 5. Navigate to "Reports" page
			reportsControl.click(PageHome.LINK_REPORTS);	
			// VP: The ""Last Updated"" label displays the time that the product was added 
			Assert.assertTrue(dateDB.equals(reportsControl.getTextByXpath(Reports.REPORT_UPDATED)));
			// VP: The count numbers next to Published Products links are changed correctly
			// get number Products
			int numberProduct2 = Integer.parseInt(reportsControl.getTextByXpath(Reports.NUMBER_PUBLISHED_PRODUCTS));
			Assert.assertEquals(numberProduct2, numberProduct1 + 1);
			// 6. Navigate to "Products" page
			productControl.click(PageHome.linkAccessories);
			// 7. Select above product
			productControl.selectAccessorybyName(dataProduct.get("name"));
			// 8. Suspend product
			productControl.doSuspend(ProductDetailModel.SUSPEND);
			// 9. Navigate to "Reports" page
			reportsControl.click(PageHome.LINK_REPORTS);
			String dateDBUpdate1 = reportsControl.getPublisedDateTime();
			// VP: The ""Last Updated"" label displays the time that the product was suspended
			Assert.assertTrue(dateDBUpdate1.equals(reportsControl.getTextByXpath(Reports.REPORT_UPDATED)));
			// VP: The count numbers next to Published Products links are changed correctly
			// get number Products
			int numberProduct3 = Integer.parseInt(reportsControl.getTextByXpath(Reports.NUMBER_PUBLISHED_PRODUCTS));
			Assert.assertEquals(numberProduct3, numberProduct2 - 1);
			// 10. Navigate to "Products" page
			productControl.click(PageHome.linkAccessories);
			// 11. Select above suspended product
			productControl.selectFilterByName(ProductModel.PRODUCT_FILTER, ProductModel.ProductStatus.PUBLISH_SUSPENDED.getName());
			productControl.selectAccessorybyName(dataProduct.get("name"));
			// 12. Restore above product
			productControl.click(ProductDetailModel.UNSUSPEND);
			// 13. Navigate to "Reports" page
			reportsControl.click(PageHome.LINK_REPORTS);
			String dateDBUpdate2 = reportsControl.getPublisedDateTime();
			// VP: The ""Last Updated"" label displays the time that product was restored
			Assert.assertTrue(dateDBUpdate2.equals(reportsControl.getTextByXpath(Reports.REPORT_UPDATED)));
			// VP: The count numbers next to Published Products links are changed correctly
			// get number Products
			int numberProduct4 = Integer.parseInt(reportsControl.getTextByXpath(Reports.NUMBER_PUBLISHED_PRODUCTS));
			Assert.assertEquals(numberProduct4, numberProduct3 + 1);
			// 14. Navigate to "Products" page
			productControl.click(PageHome.linkAccessories);
			// 15. Select above product 
			productControl.selectAccessorybyName(dataProduct.get("name"));
			// 16. Delete above product successfully
			productControl.doDelete(ProductDetailModel.DELETE);		
			// 17. Navigate to "Reports" page
			reportsControl.click(PageHome.LINK_REPORTS);
			String dateDBUpdate3 = reportsControl.getPublisedDateTime();
			// VP: The ""Last Updated"" label displays the time that the product has removed
			Assert.assertTrue(dateDBUpdate3.equals(reportsControl.getTextByXpath(Reports.REPORT_UPDATED)));
			// VP: The count numbers next to Published Products links are changed correctly
			// get number Products
			int numberProduct5 = Integer.parseInt(reportsControl.getTextByXpath(Reports.NUMBER_PUBLISHED_PRODUCTS));
			Assert.assertEquals(numberProduct5, numberProduct4 - 1);
		
	}
	
	/*
	 * Verify that the "Last Updated" label displays correct information when create or delete product
	 */
	@Test
	public void TC750DRP_05() throws InterruptedException{
		userControl.addLog("ID : TC750DRP_04 : Verify that the 'Last Updated' label displays correct information when create or delete product");
		/*
			
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Product" page
			4. Click "Add Product" link
			5. Create published product successfully 
			6. Click “Add New Variant” link
			7. Create a published variant successfully
			8. Navigate to "Report" page
			VP: The ""Last Updated"" label displays the time that the variant was added
			VP: The count number next to Published Products link is changed correctly
			9. Navigate to "Products" page
			10. Select above product
			11. Select and suspend above variant
			12. Navigate to "Reports" page
			VP: The ""Last Updated"" label displays the time that the variant was suspended
			VP: The count number next to Published Products link is changed correctly
			13. Navigate to "Products" page
			14.  Select above product
			15. Select and un-suspend above variant
			16. Navigate to "Reports" page
			VP: The ""Last Updated"" label displays the time that the variant was un-suspended
			VP: The count number next to Published Products link is changed correctly
			17. Navigate to "Products" page
			18. Select above product
			19. Select and delete above variant
			20. Navigate to "Reports" page
			VP: The ""Last Updated"" label displays the time that the variant was deleted
			VP: The count number next to Published Products link is changed correctly

		 */
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Report" page
		reportsControl.click(PageHome.LINK_REPORTS);
		// get number Products Published
		int numberProduct1 = Integer.parseInt(reportsControl.getTextByXpath(Reports.NUMBER_PUBLISHED_PRODUCTS));
		// 4. Click “Published Products” link
//		reportsControl.click(Reports.PUBLISHED_PRODUCTS);
//		// 5. Select a product from “Published Products” table
//		String productName = reportsControl.selectTheFirstElementPublishedProduct("Product");
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndPublish(dataProduct);
		
		// 6. Click “Add New Variant” link
		// 7. Create a published variant successfully
		Hashtable<String,String> dataVariant = TestData.variantData(true, true, true);
		productWf.addVariantAndPublish(dataVariant);
		// 8. Navigate to "Report" page
		reportsControl.click(PageHome.LINK_REPORTS);
		String dateDBUpdate1 = reportsControl.getPublisedDateTime();
		// VP: The ""Last Updated"" label displays the time that the variant was added
		Assert.assertTrue(dateDBUpdate1.equals(reportsControl.getTextByXpath(Reports.REPORT_UPDATED)));
		// VP: The count number next to Published Products link is changed correctly
		// get number Products
		int numberProduct2 = Integer.parseInt(reportsControl.getTextByXpath(Reports.NUMBER_PUBLISHED_PRODUCTS));
		Assert.assertEquals(numberProduct2, numberProduct1 + 2);
		// 9. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 10. Select above product
		productControl.selectAccessorybyName(dataProduct.get("name"));
		// 11. Select and suspend above variant
		productControl.selectAVariantbyName(dataVariant.get("name"));
		productControl.doSuspend(VariantInfo.SUSPEND);
		// 12. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		String dateDBUpdate2 = reportsControl.getPublisedDateTime();
		// The ""Last Updated"" label displays the time that the variant was suspended
		Assert.assertTrue(dateDBUpdate2.equals(reportsControl.getTextByXpath(Reports.REPORT_UPDATED)));
		// VP: The count number next to Published Products link is changed correctly
		// get number Products
		int numberProduct3 = Integer.parseInt(reportsControl.getTextByXpath(Reports.NUMBER_PUBLISHED_PRODUCTS));
		Assert.assertEquals(numberProduct3, numberProduct2 - 1);
		// 13. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 14.  Select above product
		productControl.selectAccessorybyName(dataProduct.get("name"));
		// 15. Select and un-suspend above variant
		productControl.selectAVariantbyName(dataVariant.get("name"));
		productControl.click(VariantInfo.UNSUSPEND);
		// 16. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		String dateDBUpdate3 = reportsControl.getPublisedDateTime();
		// The ""Last Updated"" label displays the time that the variant was un-suspended
		Assert.assertTrue(dateDBUpdate3.equals(reportsControl.getTextByXpath(Reports.REPORT_UPDATED)));
		// VP: The count number next to Published Products link is changed correctly
		// get number Products
		int numberProduct4 = Integer.parseInt(reportsControl.getTextByXpath(Reports.NUMBER_PUBLISHED_PRODUCTS));
		Assert.assertEquals(numberProduct4, numberProduct3 + 1);
		// 17. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 18. Select above product
		productControl.selectAccessorybyName(dataProduct.get("name"));
		// 19. Select and delete above variant
		productControl.selectAVariantbyName(dataVariant.get("name"));
		productControl.doDelete(VariantInfo.DELETE_VARIANT);
		// 20. Navigate to "Reports" page
		productControl.click(PageHome.LINK_REPORTS);
		String dateDBUpdate4 = reportsControl.getPublisedDateTime();
		// VP: The ""Last Updated"" label displays the time that the variant was deleted
		Assert.assertTrue(dateDBUpdate4.equals(reportsControl.getTextByXpath(Reports.REPORT_UPDATED)));
		// VP: The count number next to Published Products link is changed correctly
		// get number Products
		int numberProduct5 = Integer.parseInt(reportsControl.getTextByXpath(Reports.NUMBER_PUBLISHED_PRODUCTS));
		Assert.assertEquals(numberProduct5, numberProduct4 - 1);
		
		// Delete Product
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the "Last Updated" label displays correct information when create or delete company/ brand
	 */
	@Test
	public void TC750DRP_06() throws InterruptedException{
		userControl.addLog("ID : TC750DRP_06 : Verify that the 'Last Updated' label displays correct information when create or delete company/ brand");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Report" page
			4. Click “Active Brands” link
			5. Select a company from “Active Brands” table
			6. Click “Add New Brand” link
			7. Add a new brand successfully
			8. Create published product for above brand successfully 
			9. Navigate to "Reports" page
			VP: The ""Last Updated"" label displays the time that brand were added
			VP: The count numbers next to Active Brands links are changed correctly
			10. Navigate to “Company” page
			11. Select above company
			12. Suspend above company
			13. Navigate to "Reports" page
			VP: The "Last Updated" label displays the time that company was suspended
			14. Navigate to “Company” page
			15. Restore above company
			16. Navigate to "Reports" page
			VP: The ""Last Updated"" label displays the time that company was restored
			VP: The count numbers next to Active Brands links are changed correctly
			17. Navigate to “Company” page
			18. Select above company
			19. Select and delete above brand
			20. Navigate to "Reports" page
			VP: The ""Last Updated"" label displays the time that the brand was removed
			VP: The count number next to Active Brands link is changed correctly
		 
		 */
			
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Report" page
		reportsControl.click(PageHome.LINK_REPORTS);
		// get number Products Published
		int numberBrand1 = Integer.parseInt(reportsControl.getTextByXpath(Reports.NUMBER_ACTIVE_BRANDS));
		// 4. Click “Active Brands” link
		reportsControl.click(Reports.ACTIVE_BRANDS);
		// 5. Select a company from “Active Brands” table
		String companyName = reportsControl.selectTheFirstElemenTActiveBrand("Company");
		// 6. Click “Add New Brand” link
		companyControl.click(CompanyInfo.ADD_BRAND);
		// 7. Add a new brand successfully
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), dataBrand);
		// 8. Create published product for above brand successfully 
		Hashtable<String,String> dataProduct = TestData.productData(companyName, dataBrand.get("name"), true, true, true);
		productWf.addProductAndPublish(dataProduct);
		// 9. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		String dateDBUpdate1 = reportsControl.getPublisedDateTime();
		// VP: The ""Last Updated"" label displays the time that brand were added
		Assert.assertTrue(dateDBUpdate1.equals(reportsControl.getTextByXpath(Reports.REPORT_UPDATED)));
		// VP: The count numbers next to Active Brands links are changed correctly
		// get number Brands
		int numberBrand2 = Integer.parseInt(reportsControl.getTextByXpath(Reports.NUMBER_ACTIVE_BRANDS));
		Assert.assertEquals(numberBrand2, numberBrand1 + 1);
		// 10. Navigate to “Company” page
		companyControl.click(PageHome.LINK_COMPANY);
		// 11. Select above company
		companyControl.selectACompanyByName(companyName);
		// 12. Suspend above company
		companyControl.doSuspend(CompanyInfo.SUSPEND);
		// 13. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		String dateDBUpdate2 = reportsControl.getPublisedDateTime();
		// VP: The "Last Updated" label displays the time that company was suspended
		Assert.assertTrue(dateDBUpdate2.equals(reportsControl.getTextByXpath(Reports.REPORT_UPDATED)));
		// 14. Navigate to “Company” page
		companyControl.click(PageHome.LINK_COMPANY);
		// 15. Restore above company
		companyControl.selectFilterByName(Companies.FILTER, Companies.option.Suspended.getName());
		companyControl.selectACompanyByName(companyName);
		companyControl.doRestore(CompanyInfo.RESTORE);
		// 16. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		String dateDBUpdate3 = reportsControl.getPublisedDateTime();
		// VP: The ""Last Updated"" label displays the time that company was restored
		Assert.assertTrue(dateDBUpdate3.equals(reportsControl.getTextByXpath(Reports.REPORT_UPDATED)));
		// VP: The count numbers next to Active Brands links are changed correctly
		// get number Brands
		int numberBrand3 = Integer.parseInt(reportsControl.getTextByXpath(Reports.NUMBER_ACTIVE_BRANDS));
		Assert.assertEquals(numberBrand3, numberBrand2);
		// 17. Navigate to “Company” page
		companyControl.click(PageHome.LINK_COMPANY);
		// 18. Select above company
		companyControl.selectACompanyByName(companyName);
		// 19. Select and delete above brand
		companyControl.selectABrandByName(dataBrand.get("name"));
		companyControl.doDelete(BrandInfo.DELETE_LINK);
		// 20. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		String dateDBUpdate4 = reportsControl.getPublisedDateTime();
		// VP: The ""Last Updated"" label displays the time that the brand was removed
		Assert.assertTrue(dateDBUpdate4.equals(reportsControl.getTextByXpath(Reports.REPORT_UPDATED)));
		// VP: The count numbers next to Active Brands links are changed correctly
		// get number Brands
		int numberBrand4 = Integer.parseInt(reportsControl.getTextByXpath(Reports.NUMBER_ACTIVE_BRANDS));
		Assert.assertEquals(numberBrand4, numberBrand3 - 1);
}
	
	/*
	 * Verify that the Reports - Active HP Brands page under DTS's view displays properly
	 */
	@Test
	public void TC761DRAB_01(){
		userControl.addLog("ID : TC761DRAB_01 : Verify that the Reports - Active HP Brands page under DTS's view displays properly");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Reports" page
			4. Click on "Active Brands" link
			VP: The title label ""Active Brands"" displays on the top of the page
			VP: The Actions module is displayed with ""Download Report (CSV)"" link
			VP: The Active Brands table have four columns ""Company"" ""Brand"" ""Products"" and ""Last Updated""
			VP:  The total number of active brand is displayed on the bottom of the table

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		// 4. Switch to Partner view in Partner List on top corner of the page
		reportsControl.click(Reports.ACTIVE_BRANDS);
		// VP: The title label ""Active Brands"" displays on the top of the page
		Assert.assertTrue(reportsControl.getTextByXpath(ActiveBrand.TITLE_ACTIVE_BRAND).contains(ActiveBrand.ACTIVE_BRAND));
		// VP: The Actions module is displayed with ""Download Report (CSV)"" link
		Assert.assertTrue(reportsControl.isElementPresent(ActiveBrand.DOWNLOAD_REPORT_BRAND));
		// VP: The Active Brands table have four columns ""Company"" ""Brand"" ""Products"" and ""Last Updated""
		ArrayList<String> colums = reportsControl.getTableHeader(ActiveBrand.ACTIVE_BRAND_THREAD);
		Assert.assertTrue(ListUtil.containsAll(colums, ActiveBrand.getHeaderActiveBrandsTableDtsUser()));
		// VP:  The total number of active brand is displayed on the bottom of the table
		Assert.assertTrue(reportsControl.isElementPresent(ActiveBrand.ACTIVE_BRAND_TABLE_INFO));
	}
	
	/*
	 * Verify that Active Brands table displays items by default sort Brand
	 */
	@Test
	public void TC761DRAB_02(){
		userControl.addLog("ID : TC761DRAB_02 : Verify that Active Brands table displays items by default sort Brand");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Reports" page
			4. Click on "Active Brands" link
			VP: The "Brands" column displays items in alphanumeric order

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		// 4. Click on "Active Brands" link
		reportsControl.click(Reports.ACTIVE_BRANDS);
		// VP: The "Brands" column displays items in alphanumeric order
		ArrayList<String> listBeforSorting = reportsControl.getListElementActiveBrand("Brand");
		ArrayList<String> listAfterSorting = new ArrayList<String>(listBeforSorting);
		Collections.copy(listAfterSorting, listBeforSorting);
		System.out.println("Before" + listBeforSorting);
		Collections.sort(listAfterSorting, Collator.getInstance(new Locale("en","US")));
		System.out.println("After" + listAfterSorting);
		Assert.assertTrue(listBeforSorting.equals(listAfterSorting));
	}
	
	/*
	 * Verify that the portal redirects to 061D Company Detail Page when clicking on the Company link in Active Brands table
	 */
	@Test
	public void TC761DRAB_03(){
		userControl.addLog("ID : TC761DRAB_03 : Verify that the portal redirects to 061D Company Detail Page when clicking on the Company link in Active Brands table");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Reports" page
			4. Click on "Active Brands" link
			5. Click on a Company link in Active Brands table
			VP: The portal redirects to 061D Company Detail Page with correct information of selected company

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		// 4. Click on "Active Brands" link
		reportsControl.click(Reports.ACTIVE_BRANDS);
		// 5. Click on a Company link in Active Brands table
		String nameCompany = reportsControl.selectTheFirstElemenTActiveBrand("Company");
		// VP: The portal redirects to 061D Company Detail Page with correct information of selected company
		Assert.assertTrue(companyControl.getTextByXpath(CompanyInfo.NAME).contains(nameCompany));
	}
	
	/*
	 * Verify that the portal redirects to 762D Reports - Published Products Page when clicking on the Brand link in Active Brands table
	 */
	@Test
	public void TC761DRAB_04(){
		userControl.addLog("ID : TC761DRAB_04 : Verify that the portal redirects to 762D Reports - Published Products Page when clicking on the Brand link in Active Brands table");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Reports" page
			4. Click on "Active Brands" link
			5. Click on a Brand link in Active Brands table
			VP: The portal redirects to 762D Reports - Published Products Page with correct Published Products filtered by selected brand

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		// 4. Click on "Active Brands" link
		reportsControl.click(Reports.ACTIVE_BRANDS);
		// 5. Click on a Brand link in Active Brands table
		String nameBrand = reportsControl.selectTheFirstElemenTActiveBrand("Brand");
		Assert.assertTrue(productControl.isElementPresent(PublishedProduct.TITLE_PUBLISHED_PRODUCT));
		// VP: The portal redirects to 762D Reports - Published Products Page with correct Published Products filtered by selected brand
		Assert.assertTrue(reportsControl.getTextByXpath(PublishedProduct.BRAND_LIST).contains(nameBrand));
	}
	
	/*
	 * Verify that the "Download Report (CSV)" link in Actions module works correctly
	 */
	@Test
	public void TC761DRAB_05(){
		userControl.addLog("ID : TC761DRAB_05 : Verify that the 'Download Report (CSV)' link in Actions module works correctly");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Reports" page
			4. Click on "Active Brands" link
			5. Click on the "Download Report (CSV)" link in Actions module
			VP: The user can download Active Brands Report successfully
			6. Open above Active Brands report 
			VP: The report displays list of Headphone brands in the database including both brand and number of headphones in that brand with header table

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		// 4. Click on "Active Brands" link
		reportsControl.click(Reports.ACTIVE_BRANDS);
		// 5. Click on the "Download Report (CSV)" link in Actions module
		// VP: The user can download Active Brands Report successfully
		String file_path = reportsControl.getFilePathDownload(ActiveBrand.DOWNLOAD_REPORT_BRAND);
		// 6. Open above Active Brands report
		String[][] dataCSV = reportsControl.getDataCSV(file_path);
		String[][] data = reportsControl.getElementActiveBrand();
		// VP: The report displays list of Headphone brands in the database including both brand and number of headphones in that brand with header table
		Assert.assertTrue(reportsControl.checkDataReportActiveBrand(dataCSV, data));
	}
	
	/*
	 * Verify that the Active Brand table displays correct data
	 */
	@Test
	public void TC761DRAB_06(){
		userControl.addLog("ID : TC761DRAB_06 : Verify that the Active Brand table displays correct data");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Reports" page
			4. Click on "Active Brands" link
			5. Click on a Company link in Active Brands table
			VP: The portal redirects to 061D Company Detail Page with correct information of selected company and the status of selected company is Active
			6. Suspend above company successfully
			7. Repeat from step 3 to step 4
			VP: Above company and associated brands are not displayed in Active Brand table
			8. Navigate to "Company" page
			9. Select an active company
			10. Add new brand successfully
			11.  Repeat from step 3 to step 4
			VP: Above brand is not displayed in Active Brand table

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		// 4. Click on "Active Brands" link
		reportsControl.click(Reports.ACTIVE_BRANDS);
		// 5. Click on a Company link in Active Brands table
		// Create a new company
		reportsControl.click(PageHome.LINK_COMPANY);
		companyControl.click(Companies.ADD_COMPANY);
		Hashtable<String,String> dataCompany1 = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), dataCompany1);
		// Create a user
		Hashtable<String,String> dataUser = TestData.partnerUser();
		dataUser.remove("company");
		userControl.addUser(AddUser.getPartnerUser(), dataUser);
		// Create new brand
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), dataBrand);
		// Create a published Product
		Hashtable<String,String> dataProduct = TestData.productData(dataCompany1.get("name"), dataBrand.get("name") , true, true, true);
		productControl.click(PageHome.linkAccessories);
		productControl.click(ProductModel.ADD_PRODUCT);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(),dataProduct);
		companyControl.click(PageHome.LINK_REPORTS);
		reportsControl.click(Reports.ACTIVE_BRANDS);
		reportsControl.selectElementByNameActiveBrand("Company", dataCompany1.get("name"));
		// VP: The portal redirects to 061D Company Detail Page with correct information of selected company and the status of selected company is Active
		Assert.assertTrue(companyControl.getTextByXpath(CompanyInfo.NAME).contains(dataCompany1.get("name")));
		// 6. Suspend above company successfully
		companyControl.click(CompanyInfo.SUSPEND);
		companyControl.selectConfirmationDialogOption("Suspend");
		// 7. Repeat from step 3 to step 4
		reportsControl.click(PageHome.LINK_REPORTS);
		reportsControl.click(Reports.ACTIVE_BRANDS);
		// VP: Above company and associated brands are not displayed in Active Brand table
		Assert.assertFalse(reportsControl.checkAnElementExistByNameActiveBrand("Company", dataCompany1.get("name")));
		Assert.assertFalse(reportsControl.checkAnElementExistByNameActiveBrand("Brand", dataBrand.get("name")));
		// 8. Navigate to "Company" page
		reportsControl.click(PageHome.LINK_COMPANY);
		// 9. Select an active company
		companyControl.click(Companies.ADD_COMPANY);
		Hashtable<String,String> dataCompany2 = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), dataCompany2);
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.selectACompanyByName(dataCompany2.get("name"));
		// 10. Add new brand successfully
		companyControl.click(CompanyInfo.ADD_BRAND);
		Hashtable<String,String> dataBrand2 = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), dataBrand2);
		// 11. Repeat from step 3 to step 4
		reportsControl.click(PageHome.LINK_REPORTS);
		reportsControl.click(Reports.ACTIVE_BRANDS);
		// VP: Above brand is not displayed in Active Brand table
		Assert.assertFalse(reportsControl.checkAnElementExistByNameActiveBrand("Brand", dataBrand2.get("name")));
		
		// Delete Company
		reportsControl.click(PageHome.LINK_COMPANY);
		companyControl.selectFilterByName(Companies.FILTER, Companies.option.Suspended.getName());
		companyControl.selectACompanyByName(dataCompany1.get("name"));
		companyControl.doDelete(CompanyInfo.DELETE);
		reportsControl.click(PageHome.LINK_COMPANY);
		companyControl.selectACompanyByName(dataCompany2.get("name"));
		companyControl.doDelete(CompanyInfo.DELETE);
		
//		// Delete Product
//		reportsControl.click(PageHome.linkAccessories);
//		productControl.selectAccessorybyName(dataProduct.get("name"));
//		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the Products column displays correct data
	 */
	@Test
	public void TC761DRAB_07(){
		userControl.addLog("ID : TC761DRAB_07 : Verify that the Products column displays correct data");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Reports" page
			4. Click on "Active Brands" link
			5. Click on a Brand link in Active Brands table
			VP: The portal redirects to 762D Reports - Published Products Page with correct Published Products filtered by selected brand
			6. Check number of products on Published Products table
			VP: Verify that the Product's column on Active Brands table displays correct data

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		// 4. Click on "Active Brands" link
		reportsControl.click(Reports.ACTIVE_BRANDS);
		// 5. Click on a Brand link in Active Brands table
		// 6. Check number of products on Published Products table
		String total_Products = reportsControl.getNumberProductsOfTheFirstBrand();
		String nameBrand = reportsControl.selectTheFirstElemenTActiveBrand("Brand");
		// VP: The portal redirects to 762D Reports - Published Products Page with correct Published Products filtered by selected brand
		Assert.assertTrue(productControl.isElementPresent(PublishedProduct.TITLE_PUBLISHED_PRODUCT));
		Assert.assertTrue(reportsControl.getTextByXpath(PublishedProduct.BRAND_LIST).contains(nameBrand));
		// VP: Verify that the Product's column on Active Brands table displays correct data
		Assert.assertTrue(total_Products.equals("" + reportsControl.getTotalOfProductByFiltering()));
	}
	
	/*
	 * Verify that the Last Updated column displays correct data
	 */
	@Test
	public void TC761DRAB_08(){
		userControl.addLog("ID : TC761DRAB_08 : Verify that the Last Updated column displays correct data");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Reports" page
			4. Click on "Active Brands" link
			5. Click on a Company link in Active Brands table
			VP: The portal redirects to 061D Company Detail Page with correct information of selected company
			6. Select a brand which has at least one published product
			VP:Verify the the 063D Brand Info page is displayed
			7. Click Edit Link
			VP: Verify that the "Edit Brand Info" page is displayed
			8. Change data on Brand Info page
			9. Save Brand Info
			10.  Repeat from step 3 to step 4
			VP: Verify that the Last Updated column displays the time that the brand was updated

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		// 4. Click on "Active Brands" link
		reportsControl.click(Reports.ACTIVE_BRANDS);
		// 5. Click on a Company link in Active Brands table
//		reportsControl.selectElementByNameActiveBrand("Company", dataCompany.get("name"));
		String nameBrand = reportsControl.getNameElementOfTheFirstActiveBrand("Brand");
		String nameCompany = reportsControl.getNameElementOfTheFirstActiveBrand("Company");
		reportsControl.selectTheFirstElemenTActiveBrand("Company");
		// VP: The portal redirects to 061D Company Detail Page with correct information of selected company
		Assert.assertTrue(companyControl.getTextByXpath(CompanyInfo.NAME).contains(nameCompany));
		// 6. Select a brand which has at least one published product
		companyControl.selectABrandByName(nameBrand);
		// VP:Verify the the 063D Brand Info page is displayed
		Assert.assertEquals(companyControl.existsElement(BrandInfo.getAllField()), true);
		// 7. Click Edit Link
		companyControl.click(BrandInfo.EDIT_BRAND);
		// VP: Verify that the "Edit Brand Info" page is displayed
		Assert.assertEquals(companyControl.existsElement(AddBrand.getHash()), true);
		// 8. Change data on Brand Info page
		companyControl.editData(AddBrand.WEBSITE, RandomStringUtils.randomAlphanumeric(20));
		// 9. Save Brand Info
		companyControl.click(BrandInfo.SAVE);
		String Tracking_ID = productControl.getTextByXpath(BrandInfo.DTS_TRACKING_ID);
		String dateDB = reportsControl.getPublisedDateTimeBrand(Tracking_ID);
		// 10.  Repeat from step 3 to step 4
		reportsControl.click(PageHome.LINK_REPORTS);
		reportsControl.click(Reports.ACTIVE_BRANDS);
		// VP: Verify that the Last Updated column displays the time that the brand was updated
		String datePublished = reportsControl.getDateOfActiveBrandByName(nameBrand);
		Assert.assertTrue(datePublished.equals(dateDB));
	}
	
	/*
	 * Verify that the Reports - Published Products page under DTS's view displays properly
	 */
	@Test
	public void TC762DRPP_01(){
		userControl.addLog("ID : TC762DRPP_01 : Verify that the Reports - Published Products page under DTS's view displays properly");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Reports" page
			4. Click on "Published Products" link
			VP: The title label ""Published Products"" displays on the top of the page
			VP: The Brand filter and ""Brand"" text label are displayed with default filter ""All Brands""
			VP: The Actions module is displayed with ""Download Report (CSV)"" link
			VP: The Active Brands table have six columns ""Picture"" ""Product"" ""Brand"" ""EAN"" ""UPC"" and ""Published""
			VP: The Brand filter combo-box displays brand items in alphanumeric order
			VP:  The total number of published products is displayed on the bottom of the table
			
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		// 4. Click on "Published Products" link
		reportsControl.click(Reports.PUBLISHED_PRODUCTS);
		// VP: The title label ""Published Products"" displays on the top of the page
		Assert.assertTrue(reportsControl.getTextByXpath(PublishedProduct.TITLE_PUBLISHED_PRODUCT).contains(PublishedProduct.PUBLISHED_PRODUCT));
		// VP: The Actions module is displayed with ""Download Report (CSV)"" link
		Assert.assertTrue(reportsControl.isElementPresent(PublishedProduct.DOWNLOAD_REPORT_PRODUCT));
		// VP: The Active Brands table have six columns ""Picture"" ""Product"" ""Brand"" ""EAN"" ""UPC"" and ""Published""
		ArrayList<String> colums = reportsControl.getTableHeader(PublishedProduct.PUBLISHED_PRODUCTS_THREAD);
		Assert.assertTrue(ListUtil.containsAll(colums, PublishedProduct.getHeaderPublishedProductsTableDtsUser()));
		// VP: The Brand filter combo-box displays brand items in alphanumeric order
		ArrayList<String> listBeforeSorting = promotionControl.getAllComboboxOption(PublishedProduct.BRAND_LIST);
		listBeforeSorting.remove(0);
		ArrayList<String> listAfterSorting = new ArrayList<String>(listBeforeSorting);
		Collections.copy(listAfterSorting, listBeforeSorting);
		Collections.sort(listAfterSorting, Collator.getInstance(new Locale("en","US")));
		Assert.assertTrue(listBeforeSorting.equals(listAfterSorting));
		// VP:  The total number of published products is displayed on the bottom of the table
		Assert.assertTrue(reportsControl.isElementPresent(PublishedProduct.PUBLISHED_PRODUCT_TABLE_INFO));
		
	}
	
	/*
	 * Verify that Published Products table displays items by default sort Brand then Product
	 */
	@Test
	public void TC762DRPP_02(){
		userControl.addLog("ID : TC762DRPP_02 : Verify that Published Products table displays items by default sort Brand then Product");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Reports" page
			4. Click on "Published Products" link
			VP: The Published Products table displays items sorted by alphanumeric order following Brand then Product

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		// 4. Click on "Published Products" link
		reportsControl.click(Reports.PUBLISHED_PRODUCTS);
		// VP: The Published Products table displays items sorted by alphanumeric order following Brand then Product
		ArrayList<String> listBeforSorting = reportsControl.getListElementPublishedProduct("Brand");
		ArrayList<String> listAfterSorting = new ArrayList<String>(listBeforSorting);
		Collections.copy(listAfterSorting, listBeforSorting);
		Collections.sort(listAfterSorting, Collator.getInstance(new Locale("en","US")));
		Assert.assertTrue(listBeforSorting.equals(listAfterSorting));
		Assert.assertTrue(reportsControl.checkAlphabetProduct());
	}
	
	/*
	 * Verify that the portal redirects to 040D/041D Product Detail Page when clicking on the Product link in Published Products table
	 */
	@Test
	public void TC762DRPP_03(){
		userControl.addLog("ID : TC762DRPP_03 : Verify that the portal redirects to 040D/041D Product Detail Page when clicking on the Product link in Published Products table");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Reports" page
			4. Click on "Published Products" link
			5. Click on a product link in Published Products table
			VP: The portal redirects to 040D/041D Product Detail Page with correct information of selected product

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Create new published Product
		// 3. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		// 4. Click on "Published Products" link
		reportsControl.click(Reports.PUBLISHED_PRODUCTS);
		// 5. Click on a product link in Published Products table
		String nameProduct = reportsControl.selectTheFirstElementPublishedProduct("Product");
		// VP: The portal redirects to 040D/041D Product Detail Page with correct information of selected product
		Assert.assertTrue(companyControl.getTextByXpath(ProductDetailModel.TITLE_NAME).contains(nameProduct));
	}
	
	/*
	 * Verify that the Published Products table is filtered by brand when clicking on brand name link
	 */
	@Test
	public void TC762DRPP_04(){
		userControl.addLog("ID : TC762DRPP_04 : Verify that the Published Products table is filtered by brand when clicking on brand name link");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Reports" page
			4. Click on "Published Products" link
			5. Click on a Brand link in Published Products table
			VP: The Published Products table displays items filtered by selected brand

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		// 4. Click on "Published Products" link
		reportsControl.click(Reports.PUBLISHED_PRODUCTS);
		// 5. Click on a product link in Published Products table
		String nameBrand = reportsControl.selectTheFirstElementPublishedProduct("Brand");
		// VP: The Published Products table displays items filtered by selected brand
		Assert.assertTrue(reportsControl.getTextByXpath(PublishedProduct.BRAND_LIST).contains(nameBrand));
			
	}
	
	/*
	 * Verify that the "Download Report (CSV)" link in Actions module works correctly
	 */
	@Test
	public void TC762DRPP_05(){
		userControl.addLog("ID : TC762DRPP_05 : Verify that the 'Download Report (CSV)' link in Actions module works correctly");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Reports" page
			4. Click on "Published Products" link
			5. Click on the "Download Report (CSV)" link in Actions module
			VP: The user can download Published Products Report successfully
			6. Open above Published Products report 
			VP: The report displays list of all headphones in the database sorted by Brand and including EAN/UPC identifier with eight column headers: "Model Number", "Bluetooth device name", 
			"Product type", "Input Specification", "Noise Canceling", "HP:X Rating", "HP:X Inside" and "Release Date"

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		// 4. Click on "Published Products" link
		reportsControl.click(Reports.PUBLISHED_PRODUCTS);
		// 5. Click on the "Download Report (CSV)" link in Actions module
		// VP: The user can download Published Products Report successfully
		String file_path = reportsControl.getFilePathDownload(PublishedProduct.DOWNLOAD_REPORT_PRODUCT);
		// 6. Open above Published Products report
		// VP: The report displays list of all headphones in the database sorted by Brand and including EAN/UPC identifier with eight column headers: "Model Number", "Bluetooth device name", "Product type", "Input Specification", "Noise Canceling", "HP:X Rating", "HP:X Inside" and "Release Date"
		ArrayList<String> arrayBrand = reportsControl.getDataCSVPublishedProduct(file_path);
		ArrayList<String> arrayBrandAfterSort = new ArrayList<String>(arrayBrand);
		Collections.copy(arrayBrandAfterSort, arrayBrand);
		Collections.sort(arrayBrandAfterSort, Collator.getInstance(new Locale("en","US")));
		Assert.assertTrue(arrayBrand.equals(arrayBrandAfterSort));
		ArrayList<String> listHeader = reportsControl.getAllHeaderPublishedProduc(file_path);
		Assert.assertTrue(reportsControl.checkListContainAnother(listHeader,  PublishedProduct.allHeaderPublishedProduct()));
		
	}
	
	/*
	 * Verify that the Published Products table displays correct data
	 */
	@Test
	public void TC762DRPP_06(){
		userControl.addLog("ID : TC762DRPP_06 : Verify that the Published Products table displays correct data");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Reports" page
			4. Click on "Published Products" link
			5. Click on a product link in Published Products table
			VP: The portal redirects to 040D/041D Product Detail Page with correct information of selected product
			6. Suspend above product successfully
			7. Repeat from step 3 to step 4 
			VP: Above product  is not displayed in Published Products table
			8. Click "Add a variant" link
			9. Create a published variant successfully
			10. Repeat from step 3 to step 4 
			VP: Above variant  is displayed in Published Products table

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Create new published Product
		Hashtable<String,String> dataProduct1 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndPublish(dataProduct1);
		// 3. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		// 4. Click on "Published Products" link
		reportsControl.click(Reports.PUBLISHED_PRODUCTS);
		// 5. Click on a product link in Published Products table
		reportsControl.selectElementByNamePublishedProduct("Product", dataProduct1.get("name"));
		// VP: The portal redirects to 040D/041D Product Detail Page with correct information of selected product
		Assert.assertTrue(companyControl.getTextByXpath(ProductDetailModel.TITLE_NAME).contains(dataProduct1.get("name")));
		// 6. Suspend above product successfully
		productControl.click(ProductDetailModel.SUSPEND);
		productControl.selectConfirmationDialogOption("Suspend");
		// 7. Repeat from step 3 to step 4
		reportsControl.click(PageHome.LINK_REPORTS);
		reportsControl.click(Reports.PUBLISHED_PRODUCTS);
		Assert.assertFalse(reportsControl.checkAnElementExistByNamePublishedProduct("Product", dataProduct1.get("name")));
		// 8. Click "Add a variant" link
		// 9. Create a published variant successfully
		Hashtable<String,String> dataProduct2 = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndPublish(dataProduct2);
		Hashtable<String,String> dataVariant = TestData.variantData(true, true, true);
		productWf.addVariantAndPublish(dataVariant);
		// 10. Repeat from step 3 to step 4 
		reportsControl.click(PageHome.LINK_REPORTS);
		reportsControl.click(Reports.PUBLISHED_PRODUCTS);
		Assert.assertFalse(reportsControl.checkAnElementExistByNamePublishedProduct("Product", dataVariant.get("name")));
		// Delete Product
		reportsControl.click(PageHome.linkAccessories);
		productControl.selectFilterByName(ProductModel.PRODUCT_FILTER, ProductModel.ProductStatus.PUBLISH_SUSPENDED.getName());
		productControl.selectAccessorybyName(dataProduct1.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		
		reportsControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(dataProduct2.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the Published column displays correct data
	 */
	@Test
	public void TC762DRPP_07(){
		userControl.addLog("ID : TC762DRPP_07 : Verify that the Published column displays correct data");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Products" page
			4. Create published product successfully
			5. Navigate to "Reports" page
			6. Click on "Published Products" link
			7. Find above product on Published Products table
			VP: Verify that the datetime on Published column displays correctly

		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Products" page
		// 4. Create published product successfully
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndPublish(dataProduct);
		String Tracking_ID = productControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
		String dateDB = reportsControl.getPublisedDateProduct(Tracking_ID);
		// 5. Navigate to "Reports" page
		reportsControl.click(PageHome.LINK_REPORTS);
		// 6. Click on "Published Products" link
		reportsControl.click(Reports.PUBLISHED_PRODUCTS);
		// 7. Find above product on Published Products table
		String datePublished = reportsControl.getDateOfPublishedProductByName(dataProduct.get("name"));	
		// VP: Verify that the datetime on Published column displays correctly
		Assert.assertTrue(datePublished.equals(dateDB));
		reportsControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		
	}
	
}
