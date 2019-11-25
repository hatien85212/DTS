package com.dts.adminportal.dts.test;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.DTSUtil;

import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.DeviceInfo;
import dts.com.adminportal.model.DeviceList;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UserEdit;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.Xpath;

public class DTSUserAppsDevices630DaDevicesList extends CreatePage{
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
	 * Verify that the Apps & Devices page loads with "All Companies", "All Brands" and "All Types" filter.
	 */
	@Test
	public void TC630DaADL_01(){
		result.addLog("ID : TC630DaADL_01 : Verify that the Apps & Devices page loads with 'All Companies', 'All Brands' and 'All Types' filter.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
		 */
		// 3. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		/*
		 * The Apps &  Devices page is loaded and the 
		 * default "Company" filter is "All Companies", 
		 * default "Brand" filter is "All Brands" and 
		 * default "Type" filter is "All Types".
		 */
		// default "Company" filter is "All Companies"
		String defautCompany = home.getItemSelected(DeviceList.COMPANY_LIST);
		Assert.assertEquals(defautCompany, DeviceList.COMPANY_DEFAULT_FILTER);
		// default "Brand" filter is "All Brands" and
		String defautBrand = home.getItemSelected(DeviceList.BRAND_LIST);
		Assert.assertEquals(defautBrand, DeviceList.BRAND_DEFAULT_FILTER);
		// default "Type" filter is "All Types".
		String defautType = home.getItemSelected(DeviceList.TYPE_LIST);
		Assert.assertEquals(defautType, DeviceList.TYPE_DEFAULT_FILTER);
	}
	
	/*
	 * Verify that the "Actions" module is not displayed when the user has no "Add and manage apps and devices" privilege
	 */
	@Test
	public void TC630DaADL_02(){
		result.addLog("ID : TC630DaADL_02 : Verify that the 'Actions' module is not displayed when the user has no 'Add and manage apps and devices' privilege");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully (fae@dts.com/dts99)
			3. Navigate to "Users" page
			4. Select a dts admin user from users table
			VP: The 090D User Info page is displayed
			5. Click "Edit" link in "Actions" module
			6. Disable the "Add and manage authorized products" privilege
			7. Click "Save" link
			8. Log out DTS portal
			9. Log into DTS portal as above DTS admin user
			10. Navgate to "Apps & Devices" page
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a dts admin user from users table
		home.dtsSelectUserByEmail(dtsUser);
		/*
		 * VP: The 090D User Info page is displayed
		 */
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Edit" link in "Actions" module
		home.click(UserMgmt.EDIT);
		// 6. Disable the "Add and manage authorized products" privilege
		home.disablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.Add_manage_apps_devices);
		// 7. Click "Save" link
		home.click(UserEdit.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS admin user
		home.login(dtsUser, dtsPass);
		// 10. Navgate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		/*
		 * Verify that The 603Da Apps & Devices List page is displayed without "Actions" module
		 */
		Assert.assertFalse(home.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
	}
	
	/*
	 * Verify that the "Actions" module only displays for those who have "Add and manage apps and devices” privilege
	 */
	@Test
	public void TC630DaADL_03(){
		result.addLog("ID : TC630DaADL_03 : Verify that the ”Actions” module only displays for those who have ”Add and manage apps and devices” privilege");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully (fae@dts.com/dts99)
			3. Navigate to "Users" page
			4. Select a dts admin user from users table
			VP: The 090D User Info page is displayed
			5. Click "Edit" link in "Actions" module
			6. Enable the "Add and manage apps and devices" privilege
			7. Click "Save" link
			8. Log out DTS portal
			9. Log into DTS portal as above DTS admin user
			10. Navigate to "Apps & Devices" page
		 */
		
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a dts admin user from users table
		home.dtsSelectUserByEmail(dtsUser);
		/*
		 * VP: The 090D User Info page is displayed
		 */
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Edit" link in "Actions" module
		home.click(UserMgmt.EDIT);
		// 6. Enable the "Add and manage apps and devices" privilege
		home.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.Add_manage_apps_devices);
		// 7. Click "Save" link
		home.click(AddUser.SAVE);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as above DTS admin user
		home.login(dtsUser, dtsPass);
		// 10. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		/*
		 * Verify that The 603Da Apps & Devices List page is displayed which including the "Actions" module with its "Add App or Device" link
		 */
		Assert.assertEquals(home.existsElement(DeviceList.getListXpath()).getResult(), "Pass");
		Assert.assertTrue(home.isElementPresent(DeviceList.ACTION_MODULE));
		Assert.assertTrue(home.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
	}
	
	
	/*
	 * Verify that the "Type" filter contains "All Types", "Apps" and "Devices" items
	 */
	@Test
	public void TC630DaADL_04(){
		result.addLog("ID : TC630DaADL_04 : Verify that the 'Type' filter contains 'All Types', 'Apps' and 'Devices' items");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
			VP: The TC630Da Apps & Devices List is displayed
			4. List out all items of "Type" filter dropdown
		 */
		// 3. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// VP: The TC630Da Apps & Devices List is displayed
		result = home.existsElement(DeviceList.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
		// 4. List out all items of "Type" filter dropdown
		home.click(DeviceList.TYPE_LIST);
		/*
		 * The "Type" filter contains 
		 * "All Types", 
		 * "Apps" and 
		 * "Devices" items
		 */
		ArrayList<String> brands = home.getAllOption(DeviceList.TYPE_LIST);
		Assert.assertTrue(DTSUtil.containsAll(brands, DeviceList.brands));
	}
	
	/*
	 * Verify that the apps & devices table contains "Company", "Brand", "Product Name", "Type" and "Published" columns.
	 */
	@Test
	public void TC630DaADL_05(){
		result.addLog("ID : TC630DaADL_05 : Verify that the apps & devices table contains 'Company', 'Brand', 'Product Name', 'Type' and 'Published' columns.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
		 */
		// 3. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// VP: The TC630Da Apps & Devices List is displayed
		result = home.existsElement(DeviceList.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
		/*
		 * The 630Da Apps & Devices List is displayed and the apps & devices table contains 
		 * "Company", "Brand", "Product Name", "Type" and "Published" columns.
		 */
		ArrayList<String> list = home.getHeadColumByXpath(DeviceList.THEAD);
		Assert.assertTrue(DTSUtil.containsAll(list, DeviceList.theads));
	}
	
	/*
	 * Verify that the apps & devices table only shows up all devices of specific company when clicking on company name in apps & device table.
	 */
	@Test
	public void TC630DaADL_06(){
		result.addLog("ID : TC630DaADL_06 : Verify that the apps & devices table "
				+ "only shows up all devices of specific company "
				+ "when clicking on company name in apps & device table.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
			4. Click on a company name in apps & devices table.
		 */
		// 3. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 4. Click on a company name in apps & devices table.
		String company = home.selectTableAt(0, 0);
		/*
		 * apps & devices table only shows up all devices of selected company. 
		 */
		ArrayList<String> companies = home.getColumsByIndex(1);
		Boolean checkOnly = DTSUtil.containOnlyString(companies, company);
		Assert.assertTrue(checkOnly);
	}
	
	/*
	 * Verify that the apps & devices table only shows up all devices of specific brand when clicking on a brand name in apps & device table.
	 */
	@Test
	public void TC630DaADL_07(){
		result.addLog("ID : TC630DaADL_07 : Verify that the apps & devices table only shows up "
				+ "all devices of specific brand "
				+ "when clicking on a brand name in apps & device table.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
			4. Click on a brand name in apps & devices table.
		 */
		// 3. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 4. Click on a brand name in apps & devices table.
		String brand = home.selectTableAt(0, 1);
		/*
		 * apps & devices table only shows up all devices of selected brand.  
		 */
		ArrayList<String> brands = home.getColumsByIndex(2);
		Boolean checkOnly = DTSUtil.containOnlyString(brands, brand);
		Assert.assertTrue(checkOnly);
	}
	
	/*
	 * Verify that the portal is redirected to 640D Device Detail page when clicking on a product name.
	 */
	@Test
	public void TC630DaADL_08(){
		result.addLog("ID : TC630DaADL_08 : Verify that the portal is redirected to "
				+ "640D Device Detail page when clicking on a product name.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Apps & Devices" page
			4. Click on a product name in apps & devices table.
		 */
		// 3. Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// 4. Click on a product name in apps & devices table.
		String product = home.selectTableAt(0, 2);
		/*
		 * The portal is redirected to 640D Device Detail page. 
		 */
		String productDetail = home.getTextByXpath(DeviceInfo.NAME);
		Assert.assertTrue(product.equals(productDetail));
	}
}
