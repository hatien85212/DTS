package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.DeviceEdit;
import com.dts.adminportal.model.DeviceInfo;
import com.dts.adminportal.model.DeviceList;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.TestData;

public class DTSAppsDevices630DDevicesList extends BasePage{

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
	}
	
	/*
	 * Verify that the filtering of Apps & Devices page contain proper items.
	 */
	@Test
	public void TC630DaADL_01(){
		userControl.addLog("ID : TC630DaADL_01 : Verify that the filtering of Apps & Devices page contain proper items.");
		/*///
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
			"VP: The Apps &  Devices page is loaded and the default "Company"" filter is ""All Companies"",
				default ""Brand"" filter is ""All Brands"" and default ""Type"" filter is ""All Types"".
			VP: The ""Type"" filter contains ""All Types"", ""Apps"" and ""Devices"" items
			VP: The 630Da Apps & Devices List is displayed and the apps & devices table contains ""Company"", 
				""Brand"", ""Product Name"", ""Type"", ""Published"" and ""DTS Tracking ID"" columns.
			VP:The 630Da Apps & Devices List is displayed. apps & devices table show up total 25 
				items per page and there is a ""Total:{number of item per page""  label at left bottom of table."
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Apps & Devices" page
		userControl.click(PageHome.LINK_APP_DEVICES);
		/*
			VP:The Apps &  Devices page is loaded and the default ""Company"" filter is ""All Companies"",
			default ""Brand"" filter is ""All Brands"" and default ""Type"" filter is ""All Types"".
		 */
		// default "Company" filter is "All Companies"
		String defautCompany = userControl.getItemSelected(DeviceList.COMPANY_LIST);
		Assert.assertEquals(defautCompany, DeviceList.COMPANY_DEFAULT_FILTER);
		// default "Brand" filter is "All Brands" and
		String defautBrand = userControl.getItemSelected(DeviceList.BRAND_LIST);
		Assert.assertEquals(defautBrand, DeviceList.BRAND_DEFAULT_FILTER);
		// default "Type" filter is "All Types".
		String defautType = userControl.getItemSelected(DeviceList.TYPE_LIST);
		Assert.assertEquals(defautType, DeviceList.TYPE_DEFAULT_FILTER);
		//List out all items of "Type" filter dropdown
		userControl.click(DeviceList.TYPE_LIST);
		/*
		 *	 VP: The ""Type"" filter contains ""All Types"", ""Apps"" and ""Devices"" items
		 */
		ArrayList<String> brands = appDeviceControl.getOptionList(DeviceList.TYPE_LIST);
		Assert.assertTrue(ListUtil.containsAll(brands, DeviceList.brands));
		/*
		 * VP: The 630Da Apps & Devices List is displayed and the apps & devices table contains ""Company"", 
			""Brand"", ""Product Name"", ""Type"" and ""Published"" columns.
		 */
		//Navigate to "Apps & Devices" page
		userControl.click(PageHome.LINK_APP_DEVICES);
		// The 630Da Apps & Devices List is displayed
		Assert.assertEquals(true, userControl.existsElement(DeviceList.getListXpath()));
		/*
		 * The apps & devices table contains 
		 * "Company", "Brand", "Product Name", "Type", "Published" and "DTS Tracking ID" columns.
		 */
		ArrayList<String> list = appDeviceControl.getTableHeader(DeviceList.THEAD);
		Assert.assertTrue(ListUtil.containsAll(list, DeviceList.theads));
		/*
		 * VP:The 630Da Apps & Devices List is displayed. apps & devices table show up total 25 
			items per page and there is a ""Total:{number of item per page""  label at left bottom of table.
		 */
		//Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//The 630Da Apps & Devices List is displayed
		Assert.assertEquals(true, userControl.existsElement(DeviceList.getListXpath()));
		// apps & devices table show up total 25 items per page 
		// int total = appDeviceControl.getTotalItem(DeviceList.PRODUCT_TABLE_INFO);
		// Assert.assertTrue(total > 25, "Check at least 26 apps & devices");
		int perPage = appDeviceControl.getPerPage(DeviceList.PRODUCT_TABLE_INFO);
		Assert.assertTrue(perPage == 25, "Check apps & devices table show up total 25 items per page");
	}
	/*
	 * Verify that the apps & devices table only shows up all devices of specific company when clicking on company name in apps & device table.
	 */
	@Test
	public void TC630DaADL_06(){
		userControl.addLog("ID : TC630DaADL_06 : Verify that the apps & devices table "
				+ "only shows up all devices of specific company "
				+ "when clicking on company name in apps & device table.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
			4. Click on a company name in apps & devices table.
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Apps & Devices" page
		userControl.click(PageHome.LINK_APP_DEVICES);
		// 4. Click on a company name in apps & devices table.
		String company = appDeviceControl.selectTableAt(0, 0);
		/*
		 * apps & devices table only shows up all devices of selected company. 
		 */
		ArrayList<String> companies = appDeviceControl.getColumsByIndex(1);
		Boolean checkOnly = ListUtil.containOnlyString(companies, company);
		Assert.assertTrue(checkOnly);
	}
	
	/*
	 * Verify that the apps & devices table only shows up all devices of specific brand when clicking on a brand name in apps & device table.
	 */
	@Test
	public void TC630DaADL_07(){
		userControl.addLog("ID : TC630DaADL_07 : Verify that the apps & devices table only shows up "
				+ "all devices of specific brand "
				+ "when clicking on a brand name in apps & device table.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
			4. Click on a brand name in apps & devices table.
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Apps & Devices" page
		userControl.click(PageHome.LINK_APP_DEVICES);
		// 4. Click on a brand name in apps & devices table.
		String brand = appDeviceControl.selectTableAt(0, 1);
		/*
		 * apps & devices table only shows up all devices of selected brand.  
		 */
		ArrayList<String> brands = appDeviceControl.getColumsByIndex(2);
		Boolean checkOnly = ListUtil.containOnlyString(brands, brand);
		Assert.assertTrue(checkOnly);
	}
	
	/*
	 * Verify that the portal is redirected to 640D Device Detail page when clicking on a product name.
	 */
	@Test
	public void TC630DaADL_08(){
		userControl.addLog("ID : TC630DaADL_08 : Verify that the portal is redirected to "
				+ "640D Device Detail page when clicking on a product name.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
			4. Click on a product name in apps & devices table.
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Apps & Devices" page
		userControl.click(PageHome.LINK_APP_DEVICES);
		// 4. Click on a product name in apps & devices table.
		String product = appDeviceControl.selectTableAt(0, 2);
		/*
		 * The portal is redirected to 640D Device Detail page. 
		 */
		String productDetail = userControl.getTextByXpath(DeviceInfo.NAME);
		Assert.assertTrue(product.equals(productDetail));
	}
	
	/*
	 * Verify that "DTS Tracking ID" column displays correct value of device's DTS UUID
	 */
	@Test
	public void TC630DaADL_09(){
		userControl.addLog("ID : TC630DaADL_09 : Verify that 'DTS Tracking ID' column displays correct value of device's DTS UUID");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
			4. Click "Add App or Device" link
			5. Fill all values into required fields
			6. Click "Save" link
			7. Notice "DTS Tracking ID" of device
			8. Navigate to "Apps & Devices" page
			9. Find above device
			VP: Verify that "DTS Tracking ID" column displays device's DTS Tracking ID matching with correct device's name
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Apps & Devices" page
		userControl.click(PageHome.LINK_APP_DEVICES);
		// 4. Click "Add App or Device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 5. Fill all values into required fields
		Hashtable<String,String> dataDevice = TestData.appDeviceDraft();
		dataDevice.remove("save");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), dataDevice);
		// 6. Click "Save" link
		appDeviceControl.click(DeviceEdit.SAVE);
		// 7. Notice "DTS Tracking ID" of device
		String DTS_Tracking = appDeviceControl.getTextByXpath(DeviceEdit.DTS_TRACKING_ID);
		// 8. Navigate to "Apps & Devices" page
		userControl.click(PageHome.LINK_APP_DEVICES);
		// 9. Find above device
		// VP: Verify that "DTS Tracking ID" column displays device's DTS Tracking ID matching with correct device's name
		Assert.assertTrue(appDeviceControl.checkValueOfProductTable(dataDevice.get("name"), DTS_Tracking, 5));
		appDeviceControl.selectADeviceByName(dataDevice.get("name"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);	
	}
		
	/*
	 * Verify that the apps & devices table only shows up items of the selected  company and brand after filtering.
	 */
	@Test
	public void TC630DbADL_01(){
		appDeviceControl.addLog("ID : TC630DbADL_01 : Verify that the apps & devices table only shows up items of the selected  company and brand after filtering.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
				VP: The 630Da Apps & Devices List is displayed
			4. Select a company from "Company" filter
				VP: The apps & devices table only shows up items of selected company.
			5. List out the "Brand" filter.
				VP: The "Brand" filter only contains brands of selected "Company" filter.

		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		/*
		 *  VP: The TC630Da Apps & Devices List is displayed		
		 */
		Assert.assertEquals(true, appDeviceControl.existsElement(DeviceList.getListXpath()));
		// 4. Select a company from "Company" filter
		String company = appDeviceControl.selectFilterByName(DeviceList.COMPANY_LIST, "");
		System.out.println(company);
		ArrayList<String> columns = appDeviceControl.getColumsByIndex(1);
		/*
		 * VP: The apps & devices table only shows up items of selected company.
		 */
		Boolean isTrue = ListUtil.containOnlyString(columns, company);
		Assert.assertTrue(isTrue);
		//5. List out the "Brand" filter.
		appDeviceControl.click(DeviceList.BRAND_LIST);
		/*
		  * The "Brand" filter only contains brands of selected "Company" filter.
		  */
		ArrayList<String> brands = appDeviceControl.getColumsByIndex(2);
		System.out.println(brands.get(0).toString());
		ArrayList<String> brandList = appDeviceControl.getAllComboboxOption(DeviceList.BRAND_LIST);
		System.out.println(brandList.get(0).toString());
		Assert.assertTrue(ListUtil.containsAll(brandList, brands));

	}
	/*
	 * Verify that the apps & devices table shows up properly items of 
	 * the selected "Company" and "Brand" for All type, App or Device.
	 */
	@Test
	public void TC630DbADL_04(){
		appDeviceControl.addLog("ID : TC630DbADL_04 : VVerify that the apps & devices table shows up properly items of the selected 'Company' and 'Brand' for All type, App or Device");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
				VP: The 630Da Apps & Devices List is displayed
			4. Select a company from "Company" filter
			5. Select a brand from "Brand" filter.
			6. Set the "Type" filter to "All Types"
				VP: The apps & devices table shows up all items of both App and Device items 
				of selected company from "Company" and "Brand" filters.
			7. Set the "Type" filter to "Device"
				VP: The apps & devices table only shows up Device items of selected company from "Company" and "Brand" filters.
			8. Set the "Type" filter to "App"
				VP: The apps & devices table only shows up App items of selected company from "Company" and "Brand" filters.

		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		/*
		 * VP: The TC630Da Apps & Devices List is displayed
		 */
		Assert.assertEquals(true, appDeviceControl.existsElement(DeviceList.getListXpath()));
		// 4. Select a company from "Company" filter
		String company = appDeviceControl.selectFilterByName(DeviceList.COMPANY_LIST, "");
		// 5. Select a brand from "Brand" filter.
		String brand = appDeviceControl.selectFilterByName(DeviceList.BRAND_LIST, "");
		// 6. Set the "Type" filter to "All Types"
		appDeviceControl.selectFilterByName(DeviceList.TYPE_LIST, "All Types");
		/*
		 * VP: The apps & devices table shows up all items of both App and Device items 
		 * of selected company from "Company" and "Brand" filters.
		 */
		// check company
		ArrayList<String> columCompany = appDeviceControl.getColumsByIndex(1);
		Boolean isTrue = ListUtil.containOnlyString(columCompany, company);
		Assert.assertTrue(isTrue);
		// check brand
		ArrayList<String> columBrand = appDeviceControl.getColumsByIndex(2);
		isTrue = ListUtil.containOnlyString(columBrand, brand);
		Assert.assertTrue(isTrue);
//		//check type
		ArrayList<String> columType = appDeviceControl.getColumsByIndex(4);
//		isTrue = ListUtil.containOnlyString(columType,"Devices");
//		isTrue = ListUtil.containOnlyString(columType,"Apps");
//		Assert.assertTrue(isTrue);
//		Assert.assertTrue(isTrue);
		//7. Set the "Type" filter to "Device"
		String TypeDevice = appDeviceControl.selectFilterByNames(DeviceList.TYPE_LIST, "Devices");
		columType = appDeviceControl.getColumsByIndex(4);
		/*
		 * VP: The apps & devices table only shows up Device items of selected company from "Company" and "Brand" filters.
		 */
		//check type
		isTrue = ListUtil.containOnlyStringAppDevice(columType, TypeDevice);
		Assert.assertTrue(isTrue);
	    //8. Set the "Type" filter to "App"
		String TypeApp = appDeviceControl.selectFilterByNames(DeviceList.TYPE_LIST, "Apps");
		columType = appDeviceControl.getColumsByIndex(4);
		/*
		 * VP: The apps & devices table only shows up App items of selected company from "Company" and "Brand" filters.
		 */
		//check type
		isTrue = ListUtil.containOnlyStringAppDevice(columType,TypeApp);
		Assert.assertTrue(isTrue);
	}

	/*
	 * Verify that the apps & devices table shows up Device or App items of all companies and brands correctly.
	 */
	@Test
	public void TC630DbADL_06(){
		appDeviceControl.addLog("ID : TC630DbADL_06 : Verify that the apps & devices table shows up Device or App items of all companies and brands correctly.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
				VP: The 630Da Apps & Devices List is displayed
			4. Set the "Company" filter to "All Companies"
			5. Set the item of "Brand" filter to "All Brands"
			6. Set the "Type" filter to "Device"
				VP: The apps & devices table shows up only "Device" items of all companies and all brands.
			7. Set the "Type" filter to "App"
				VP: The apps & devices table shows up only "App" items of all companies and all brands.
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		/*
		 * VP: The TC630Da Apps & Devices List is displayed
		 */
		Assert.assertEquals(true, appDeviceControl.existsElement(DeviceList.getListXpath()));
		// Select a company from "Company" filter
		String company = appDeviceControl.selectFilterByName(DeviceList.COMPANY_LIST, "");
		// Select a brand from "Brand" filter.
		String brand = appDeviceControl.selectFilterByName(DeviceList.BRAND_LIST, "");
		//4. Set the "Company" filter to "All Companies"
	    company = appDeviceControl.selectFilterByName(DeviceList.COMPANY_LIST, "All Companies");
		//5. Set the item of "Brand" filter to "All Brands"
	    brand = appDeviceControl.selectFilterByName(DeviceList.BRAND_LIST, "All Brands");
		//6. Set the "Type" filter to "Device"
	    String typeDevice = appDeviceControl.selectFilterByNames(DeviceList.TYPE_LIST, "Devices");
		/*
		 * VP: The apps & devices table shows up only "Device" items of all companies and all brands.
		 */
	    String select6[] = {company, brand, typeDevice};
		Boolean check = appDeviceControl.checkFilterWithData(select6);
		Assert.assertTrue(check);
		//7. Set the "Type" filter to "App"
		String typeApp = appDeviceControl.selectFilterByNames(DeviceList.TYPE_LIST, "Apps");
		/*
		 * VP: The apps & devices table shows up only "App" items of all companies and all brands.
		 */
		String select7[] = {company, brand, typeApp};
		check = appDeviceControl.checkFilterWithData(select7);
		Assert.assertTrue(check);
	}
	/*
	 * Verify that the apps & devices table shows up all brands and types of selected "Company" and "Type'.
	 */
	@Test
	public void TC630DbADL_08(){
		appDeviceControl.addLog("ID : TC630DbADL_08 : Verify that the apps & devices table shows up all brands and types of selected 'Company' and 'Type'");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
				VP: The 630Da Apps & Devices List is displayed
			4. Select a company from "Company" filter.
			5. Set the item of "Brand" filter to "All Brands"
			6. Set the "Type" filter to "All Types"
				VP: The apps & devices table shows up all brands and all type of selected "Company" filter
			7. Set the "Type" filter to "Device"
				VP: The apps & devices table shows up only "Device" type of all brands of selected company "Company" filter
			8. Set the "Type" filter to "App"
				VP: The apps & devices table shows up only "App" type of all brands of selected company "Company" filter

		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// VP: The TC630Da Apps & Devices List is displayed
		Assert.assertEquals(true, appDeviceControl.existsElement(DeviceList.getListXpath()));
		// Select a type from "type" filter
		String type = appDeviceControl.selectFilterByName(DeviceList.TYPE_LIST, "");
		// Select a brand from "Brand" filter.
		String brand = appDeviceControl.selectFilterByName(DeviceList.BRAND_LIST, "");
		// 4. Select a company from "Company" filter.
		String company = appDeviceControl.selectFilterByName(DeviceList.COMPANY_LIST, "");
		// 5. Set the item of "Brand" filter to "All Brands"
		brand = appDeviceControl.selectFilterByName(DeviceList.BRAND_LIST, "All Brands");
		// 6. Set the "Type" filter to "All Types"
		type = appDeviceControl.selectFilterByName(DeviceList.TYPE_LIST, "All Types");
		/*
		 * VP: The apps & devices table shows up all brands and all type of selected company "Company" filter
		 */
		String select6[] = {company, brand, type};
		Boolean check = appDeviceControl.checkFilterWithData(select6);
		Assert.assertTrue(check);
		//7. Set the "Type" filter to "Device"
		type = appDeviceControl.selectFilterByNames(DeviceList.TYPE_LIST, "Devices");
		/*
		 * VP: The apps & devices table shows up only "Device" type of all brands of selected company "Company" filter
		 */
		String select7[] = {company, brand, type};
		check = appDeviceControl.checkFilterWithData(select7);
		Assert.assertTrue(check);
	    //8. Set the "Type" filter to "App"
		type = appDeviceControl.selectFilterByNames(DeviceList.TYPE_LIST, "Apps");
		/*
		 * VP: The apps & devices table shows up only "App" type of all brands of selected company "Company" filter
		 */
		String select8[] = {company, brand, type};
		check = appDeviceControl.checkFilterWithData(select8);
		Assert.assertTrue(check);
	}
	
	
}
