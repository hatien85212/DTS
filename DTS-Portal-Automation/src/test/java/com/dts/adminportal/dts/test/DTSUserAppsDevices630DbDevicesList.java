package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.DTSUtil;
import dts.com.adminportal.model.DeviceList;
import dts.com.adminportal.model.Xpath;

public class DTSUserAppsDevices630DbDevicesList extends CreatePage{
	private HomeController home;
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}
	@BeforeMethod
	public void loginBeforeTest() {
		home.login("fae@dts.com", "fa3!");
	}
	
	/*
	 * Verify that the apps & devices table only shows up items of the selected "Company" filter.
	 */
	@Test
	public void TC630DbADL_01(){
		result.addLog("ID : TC630DbADL_01 : Verify that the apps & devices table only shows up items of the selected 'Company' filter.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
			VP: The 630Da Apps & Devices List is displayed
			4. Select a company from "Company" filter
		*/
		// 3. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// VP: The TC630Da Apps & Devices List is displayed
		result = home.existsElement(DeviceList.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
		/*
		 * The apps & devices table only shows up items of selected company.
		 */
		String company = home.selectFilterByName(DeviceList.COMPANY_LIST, "");
		System.out.println(company);
		
		ArrayList<String> columns = home.getColumsByIndex(1);
		Boolean isTrue = DTSUtil.containOnlyString(columns, company);
		Assert.assertTrue(isTrue);
	}
	
	/*
	 * Verify that the "Brand" filter only contains the brands of selected "Company" filter.
	 */
	@Test
	public void TC630DbADL_02(){
		result.addLog("ID : TC630DbADL_02 : Verify that the 'Brand' filter only contains the brands of selected 'Company' filter.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
			VP: The 630Da Apps & Devices List is displayed
			4. Select a company from "Company" filter
			5. List out the "Brand" filter.
		*/
		// 3. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// VP: The TC630Da Apps & Devices List is displayed
		result = home.existsElement(DeviceList.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
		// 4. Select a company from "Company" filter
		home.selectFilterByName(DeviceList.COMPANY_LIST, "");
		// 5. List out the "Brand" filter.
		home.click(DeviceList.BRAND_LIST);
		/*
		 * The "Brand" filter only contains brands of selected "Company" filter.
		 */
		ArrayList<String> brands = home.getColumsByIndex(2);
		ArrayList<String> branList = home.getAllOption(DeviceList.BRAND_LIST);
		Assert.assertTrue(DTSUtil.containsAll(branList, brands));
	}
	
	/*
	 * Verify that the apps & devices table shows up all types of the selected "Company" and "Brand" filters.
	 */
	@Test
	public void TC630DbADL_03(){
		result.addLog("ID : TC630DbADL_03 : Verify that the apps & devices table shows up all types of the selected 'Company' and 'Brand' filters.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
			VP: The 630Da Apps & Devices List is displayed
			4. Select a company from "Company" filter
			5. Select a brand from "Brand" filter.
			6. Set the "Type" filter to "All Types"
		*/
		// 3. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// VP: The TC630Da Apps & Devices List is displayed
		result = home.existsElement(DeviceList.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
		// 4. Select a company from "Company" filter
		String company = home.selectFilterByName(DeviceList.COMPANY_LIST, "");
		// 5. Select a brand from "Brand" filter.
		String brand = home.selectFilterByName(DeviceList.BRAND_LIST, "");
		// 6. Set the "Type" filter to "All Types"
		home.selectFilterByName(DeviceList.TYPE_LIST, "All Types");
		/*
		 * The apps & devices table shows up all items of both App and Device items 
		 * of selected company from "Company" and "Brand" filters.
		 */
		// check company
		ArrayList<String> columCompany = home.getColumsByIndex(1);
		Boolean isTrue = DTSUtil.containOnlyString(columCompany, company);
		Assert.assertTrue(isTrue);
		// check brand
		ArrayList<String> columBrand = home.getColumsByIndex(2);
		isTrue = DTSUtil.containOnlyString(columBrand, brand);
		Assert.assertTrue(isTrue);
	}
	
	/*
	 * Verify that the apps & devices table only shows up App items of the selected "Company" and "Brand" filters.
	 */
	@Test
	public void TC630DbADL_04(){
		result.addLog("ID : TC630DbADL_04 : Verify that the apps & devices table only shows up App items of the selected 'Company' and 'Brand' filters.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
			VP: The 630Da Apps & Devices List is displayed
			4. Select a company from "Company" filter
			5. Select a brand from "Brand" filter.
			6. Set the "Type" filter to "App"
		*/
		// 3. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// VP: The TC630Da Apps & Devices List is displayed
		result = home.existsElement(DeviceList.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
		// 4. Select a company from "Company" filter
		String company = home.selectFilterByName(DeviceList.COMPANY_LIST, "");
		// 5. Select a brand from "Brand" filter.
		String brand = home.selectFilterByName(DeviceList.BRAND_LIST, "");
		// 6. Set the "Type" filter to "App"
		String type = home.selectFilterByName(DeviceList.TYPE_LIST, "Apps");
		/*
		 * The apps & devices table only shows up 
		 * App items of selected company from "Company" and "Brand" filters.
		 */
		// check company
		ArrayList<String> columCompany = home.getColumsByIndex(1);
		Boolean isTrue = DTSUtil.containOnlyString(columCompany, company);
		Assert.assertTrue(isTrue);
		// check brand
		ArrayList<String> columBrand = home.getColumsByIndex(2);
		isTrue = DTSUtil.containOnlyString(columBrand, brand);
		Assert.assertTrue(isTrue);
		// check type
		ArrayList<String> columType = home.getColumsByIndex(4);
		isTrue = DTSUtil.containOnlyString(columType, type);
		Assert.assertTrue(isTrue);
	}
	
	/*
	 * Verify that the apps & devices table only shows up Device items of the selected "Company" and "Brand" filters.
	 */
	@Test
	public void TC630DbADL_05(){
		result.addLog("ID : TC630DbADL_05 : Verify that the apps & devices table only shows up Device items of the selected 'Company' and 'Brand' filters.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
			VP: The 630Da Apps & Devices List is displayed
			4. Select a company from "Company" filter
			5. Select a brand from "Brand" filter.
			6. Set the "Type" filter to "Device"
		*/
		// 3. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// VP: The TC630Da Apps & Devices List is displayed
		result = home.existsElement(DeviceList.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
		// 4. Select a company from "Company" filter
		String company = home.selectFilterByName(DeviceList.COMPANY_LIST, "");
		// 5. Select a brand from "Brand" filter.
		String brand = home.selectFilterByName(DeviceList.BRAND_LIST, "");
		// 6. Set the "Type" filter to "Devices"
		String type = home.selectFilterByName(DeviceList.TYPE_LIST, "Devices");
		/*
		 * The apps & devices table only shows up 
		 * Devices items of selected company from "Company" and "Brand" filters.
		 */
		// check company
		ArrayList<String> columCompany = home.getColumsByIndex(1);
		Boolean isTrue = DTSUtil.containOnlyString(columCompany, company);
		Assert.assertTrue(isTrue);
		// check brand
		ArrayList<String> columBrand = home.getColumsByIndex(2);
		isTrue = DTSUtil.containOnlyString(columBrand, brand);
		Assert.assertTrue(isTrue);
		// check type
		ArrayList<String> columType = home.getColumsByIndex(4);
		isTrue = DTSUtil.containOnlyString(columType, type);
		Assert.assertTrue(isTrue);
	}
	
	/*
	 * Verify that the apps & devices table shows up only Device items of all companies and brands correctly.
	 */
	@Test
	public void TC630DbADL_06(){
		result.addLog("ID : TC630DbADL_06 : Verify that the apps & devices table shows up only Device items of all companies and brands correctly.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
			VP: The 630Da Apps & Devices List is displayed
			4. Set the "Company" filter to "All Companies"
			5. Set the item of "Brand" filter to "All Brands"
			6. Set the "Type" filter to "Devices"
		*/
		// 3. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// VP: The TC630Da Apps & Devices List is displayed
		result = home.existsElement(DeviceList.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
		// Select a company from "Company" filter
		String company = home.selectFilterByName(DeviceList.COMPANY_LIST, "");
		// Select a brand from "Brand" filter.
		String brand = home.selectFilterByName(DeviceList.BRAND_LIST, "");
		// 6. Set the "Type" filter to "Devices"
		String type = home.selectFilterByName(DeviceList.TYPE_LIST, "Devices");
		// 4. Set the "Company" filter to "All Companies"
		company = home.selectFilterByName(DeviceList.COMPANY_LIST, "All Companies");
		// 5. Set the item of "Brand" filter to "All Brands"
		brand = home.selectFilterByName(DeviceList.BRAND_LIST, "All Brands");
		/*
		 * The apps & devices table shows up only "Device" items of all companies and all brands.
		 */
		String select[] = {company, brand, type};
		Boolean check = home.checkFilterWithData(select);
		Assert.assertTrue(check);
	}
	
	/*
	 * Verify that the apps & devices table shows up only App items of all companies and brands correctly.
	 */
	@Test
	public void TC630DbADL_07(){
		result.addLog("ID : TC630DbADL_07 : Verify that the apps & devices table shows up only App items of all companies and brands correctly.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
			VP: The 630Da Apps & Devices List is displayed
			4. Set the "Company" filter to "All Companies"
			5. Set the item of "Brand" filter to "All Brands"
			6. Set the "Type" filter to "App"
		*/
		// 3. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// VP: The TC630Da Apps & Devices List is displayed
		result = home.existsElement(DeviceList.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
		// Select a company from "Company" filter
		String company = home.selectFilterByName(DeviceList.COMPANY_LIST, "");
		// Select a brand from "Brand" filter.
		String brand = home.selectFilterByName(DeviceList.BRAND_LIST, "");
		// 6. Set the "Type" filter to "Apps"
		String type = home.selectFilterByName(DeviceList.TYPE_LIST, "Apps");
		// 4. Set the "Company" filter to "All Companies"
		company = home.selectFilterByName(DeviceList.COMPANY_LIST, "All Companies");
		// 5. Set the item of "Brand" filter to "All Brands"
		brand = home.selectFilterByName(DeviceList.BRAND_LIST, "All Brands");
		/*
		 * The apps & devices table shows up only "Apps" items of all companies and all brands.
		 */
		String select[] = {company, brand, type};
		Boolean check = home.checkFilterWithData(select);
		Assert.assertTrue(check);
	}
	
	/*
	 * Verify that the apps & devices table shows up all brands and all type of selected company "Company" filter
	 */
	@Test
	public void TC630DbADL_08(){
		result.addLog("ID : TC630DbADL_08 : Verify that the apps & devices table shows up all brands and all type of selected company 'Company' filter");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
			VP: The 630Da Apps & Devices List is displayed
			4. Select a company from "Company" filter.
			5. Set the item of "Brand" filter to "All Brands"
			6. Set the "Type" filter to "All Types"
		*/
		// 3. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// VP: The TC630Da Apps & Devices List is displayed
		result = home.existsElement(DeviceList.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
		// Select a type from "type" filter
		String type = home.selectFilterByName(DeviceList.TYPE_LIST, "");
		// Select a brand from "Brand" filter.
		String brand = home.selectFilterByName(DeviceList.BRAND_LIST, "");
		// 4. Select a company from "Company" filter.
		String company = home.selectFilterByName(DeviceList.COMPANY_LIST, "");
		// 5. Set the item of "Brand" filter to "All Brands"
		brand = home.selectFilterByName(DeviceList.BRAND_LIST, "All Brands");
		// 6. Set the "Type" filter to "All Types"
		type = home.selectFilterByName(DeviceList.TYPE_LIST, "All Types");
		/*
		 * The apps & devices table shows up all brands and all type of selected company "Company" filter
		 */
		String select[] = {company, brand, type};
		Boolean check = home.checkFilterWithData(select);
		Assert.assertTrue(check);
	}
	
	/*
	 * Verify that the apps & devices table shows up only "Device" type of all brands of selected company "Company" filter
	 */
	@Test
	public void TC630DbADL_09(){
		result.addLog("ID : TC630DbADL_09 : Verify that the apps & devices table shows up only 'Device' type of all brands of selected company 'Company' filter");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
			VP: The 630Da Apps & Devices List is displayed
			4. Select a company from "Company" filter.
			5. Set the item of "Brand" filter to "All Brands"
			6. Set the "Type" filter to "Device"
		*/
		// 3. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// VP: The TC630Da Apps & Devices List is displayed
		result = home.existsElement(DeviceList.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
		// 4. Select a company from "Company" filter.
		String company = home.selectFilterByName(DeviceList.COMPANY_LIST, "");
		// 5. Set the item of "Brand" filter to "All Brands"
		String brand = home.selectFilterByName(DeviceList.BRAND_LIST, "All Brands");
		// 6. Set the "Type" filter to "Devices"
		String type = home.selectFilterByName(DeviceList.TYPE_LIST, "Devices");
		/*
		 * The apps & devices table shows up only "Devices" type of all brands of selected company "Company" filter
		 */
		String select[] = {company, brand, type};
		Boolean check = home.checkFilterWithData(select);
		Assert.assertTrue(check);
	}
	
	/*
	 * Verify that the apps & devices table shows up only "Apps" type of all brands of selected company "Company" filter
	 */
	@Test
	public void TC630DbADL_10(){
		result.addLog("ID : TC630DbADL_10 : Verify that the apps & devices table shows up only 'Apps' type of all brands of selected company 'Company' filter");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
			VP: The 630Da Apps & Devices List is displayed
			4. Select a company from "Company" filter.
			5. Set the item of "Brand" filter to "All Brands"
			6. Set the "Type" filter to "Apps"
		*/
		// 3. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// VP: The TC630Da Apps & Devices List is displayed
		result = home.existsElement(DeviceList.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
		// 4. Select a company from "Company" filter.
		String company = home.selectFilterByName(DeviceList.COMPANY_LIST, "");
		// 5. Set the item of "Brand" filter to "All Brands"
		String brand = home.selectFilterByName(DeviceList.BRAND_LIST, "All Brands");
		// 6. Set the "Type" filter to "Apps"
		String type = home.selectFilterByName(DeviceList.TYPE_LIST, "Apps");
		/*
		 * The apps & devices table shows up only "Apps" type of all brands of selected company "Company" filter
		 */
		String select[] = {company, brand, type};
		Boolean check = home.checkFilterWithData(select);
		Assert.assertTrue(check);
	}
	
	/*
	 * Verify that the apps & devices table show up total 25 items per page.
	 */
	@Test
	public void TC630DbADL_11(){
		result.addLog("ID : TC630DbADL_11 : Verify that the apps & devices table show up total 25 items per page.");
		/*
		 	Pre-Condition: There are at least 26 apps & devices.

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices"
		*/
		// 3. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		/*
		 * The 630Da Apps & Devices List is displayed. 
		 * apps & devices table show up total 25 items per page 
		 * and there is a "Total:{number of item per page"  label at left bottom of table.
		 */
		// apps & devices table show up total 25 items per page 
		int total = home.getTotalItem(DeviceList.PRODUCT_TABLE_INFO);
		Assert.assertTrue(total > 25, "Check at least 26 apps & devices");
		int perPage = home.getPerPage(DeviceList.PRODUCT_TABLE_INFO);
		Assert.assertTrue(perPage == 25, "Check apps & devices table show up total 25 items per page");
	}
}
