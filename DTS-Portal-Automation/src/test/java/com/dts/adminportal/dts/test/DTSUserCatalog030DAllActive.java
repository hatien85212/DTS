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
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AccessoryInfo;
import dts.com.adminportal.model.AccessoryMain;
import dts.com.adminportal.model.AddAccessory;
import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.DTSHome;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.TuningRequestForm;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.Xpath;

public class DTSUserCatalog030DAllActive extends CreatePage{
	private HomeController home;
	
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}
	
	@BeforeMethod
	public void loginBeforeTest() {
		result = home.login(superUsername, superUserpassword);
	}
	
	/*
	 * Verify that the user who does not have "Add and manage accessories" right can not add new accessory
	 */
	@Test
	public void TC030DCAA_01(){
		result.addLog("ID : TC030DCAA_01 : Verify that partner user who does not have 'Add and manage accessories' right can not add new accessory");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from users table whose's company is "DTS Inc."
			VP: Verify that the 090D User Info page is displayed
			5. Click "Edit" link
			6. Disable "Add and manage accessories" site privilege for above user
			7. Log out DTS portal
			8. Log into DTS portal as above user admin
			9. Navigate to "Accessories" page
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table
		home.selectUserByEmail(dtsUser);
		// VP: Verify that the 090D User Info page is displayed
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Disable "Add and manage accessories" site privilege for above user
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above user admin
		home.login(dtsUser, dtsPass);
		// 9. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		/*
		 * Verify that 030P All Active page is displayed without "Add Accessory" link.
		 */
		Assert.assertFalse(home.isElementPresent(AccessoryMain.ADD_ACCESSORY));
	}
	
	/*
	 * Verify that the user who does not have "Add and manage accessories" right can not edit accessories.
	 */
	@Test
	public void TC030DCAA_02(){
		result.addLog("Verify that the user who does not have 'Add and manage accessories' right can not edit accessories.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a user from users table
			VP: Verify that the 090D User Info page is displayed
			5. Click "Edit" link
			6. Disable "Add and manage accessories" site privilege for above user
			7. Log out DTS portal
			8. Log into DTS portal as above partner admin
			9. Navigate to "Accessories" page
			10. Select an accessory from accessories table
		 */
		/*
		 * PreCondition: Create a new product
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * *******************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table
		home.selectUserByEmail(dtsUser);
		// VP: Verify that the 090D User Info page is displayed
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Disable "Add and manage accessories" site privilege for above user
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner admin
		home.login(dtsUser, dtsPass);
		// 9. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 10. Select an accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * Verify that 040D Accessory Detail page is displayed without "Edit" link.
		 */
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.EDIT_MODE));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the user who has "Add and manage products" right can create new products
	 */
	@Test
	public void TC030DCAA_03(){
		result.addLog("ID : 030DCAA_03 : Verify that the user who has 'Add and manage products' right can create new products");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a DTS user from users table whose's company is "DTS Inc."
			VP: Verify that the 090D User Info page is displayed
			5. Click "Edit" link
			6. Enable "Add and manage products" site privilege for above user
			7. Log out DTS portal
			8. Log into DTS portal as above partner admin
			9. Navigate to "products" page
			VP: Verify that the "Add product" link is displayed
			10. Click "Add product" link
			VP: Verify that the "Adding New Model" page is displayed
			11. Fill valid value into all required fields
			12. Click "Save" link
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from users table whose's company is "DTS Inc."
		home.dtsSelectUserByEmail(dtsUser);
		/*
		 * VP: Verify that the 090D User Info page is displayed
		 */
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Enable "Add and manage products" site privilege for above user
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner admin
		home.login(dtsUser, dtsPass);
		// 9. Navigate to "products" page
		home.click(Xpath.linkAccessories);
		/*
		 * VP: Verify that the "Add product" link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryMain.ADD_ACCESSORY));
		// 10. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP: Verify that the "Adding New Model" page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(), "Pass");
		// 11. Fill valid value into all required fields
		// 12. Click "Save" link
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * Verify that New product is created successfully
		 */
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the user who has "Add and manage products" right can edit products
	 */
	@Test
	public void TC030DCAA_04(){
		result.addLog("ID : 030DCAA_04 : Verify that the user who has 'Add and manage products' right can edit products");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Users" page
			4. Select a user from users table
			VP: Verify that the 090D User Info page is displayed
			5. Click "Edit" link
			6. Enable "Add and manage products" site privilege for above user
			7. Log out DTS portal
			8. Log into DTS portal as above partner admin
			9. Navigate to "products" page
			10. Select an product from products table
			VP: Verify that 040D product Detail page is displayed with "Edit" link
			11. Click "Edit" link
			12. Verify that 051D product Edit Page is displayed
			13. Change any of product's information
			14. Click "Save" link
		 */
		/*
		 * Precondition: Create new product
		 */
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * *************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table
		home.selectUserByEmail(dtsUser);
		/*
		 * VP: Verify that the 090D User Info page is displayed
		 */
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Enable "Add and manage products" site privilege for above user
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner admin
		home.login(dtsUser, dtsPass);
		// 9. Navigate to "products" page
		home.click(Xpath.linkAccessories);
		// 10. Select an product from products table
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that 040D product Detail page is displayed with "Edit" link
		 */
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.EDIT_MODE));
		// 11. Click "Edit" link
		home.click(AccessoryInfo.EDIT_MODE);
		// 12. Verify that 051D product Edit Page is displayed
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(), "Pass");
		// 13. Change any of product's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		home.editData(AddAccessory.NAME, newName);
		// 14. Click "Save" link
		home.click(AddAccessory.SAVE);
		/*
		 *  Verify that 040D product Detail page is displayed with new product's information
		 */
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), newName);
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the default catalog page displays with "All Active" report filter.
	 */
	@Test
	public void TC030DCAA_05(){
		result.addLog("ID : TC030DCAA_05 : Verify that the default catalog page displays with 'All Active' report filter.");
		/*
		 * 	1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
		 */
		// 2. Navigate to "Accessories" page
		home.click(DTSHome.LINK_ACCESSORIES);
		// 030P All Active page displays, 
		// and default item of report filter is "All Active".
		String filter = home.getItemSelectedByXpath(Xpath.accessoryFilterSelect).getResult();
		Assert.assertEquals(filter, "All Active");
	}
	
	/*
	 * Verify that the report filter dropdown contains correct items.
	 */
	@Test
	public void TC030DCAA_06(){
		result.addLog("ID : TC030DCAA_06 : Verify that the report filter dropdown contains correct items.");
		/*
		 * 	1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. List out all items of report filter dropdown
		 */
		// 2. Navigate to "Accessories" page
		home.click(DTSHome.LINK_ACCESSORIES);
		// 3. List out all items of report filter dropdown
		ArrayList<String> arrayList = home.getAllOption(Xpath.accessoryFilterSelect);
		/*
		 * Verify that the report filter includes: 
		 * All Active, 
		 * Recently Published, 
		 * Recently Added, 
		 * Draft, 
		 * Tuning Request Pending, 
		 * Tuning Request - Overdue, 
		 * Partner Tuning Review, 
		 * Tuning Declined, 
		 * Tuning Revoked, 
		 * Headphone:X Rating A+, 
		 * Headphone:X Rating A, 
		 * Ready for Marketing, 
		 * DTS Marketing Review,
		 * DTS Marketing Review - Overdue, 
		 * Marketing Declined, 
		 * Marketing Revoked, 
		 * Ready to Publish, 
		 * Ready to Published - Overdue, 
		 * Need Attention, 
		 * Suspended.
		 */
		Boolean match = Constant.getListFilterDtsReports().containsAll(arrayList);
		Assert.assertTrue(match);
	}
	
	/*
	 * Verify that the default items of "Brand" filter dropdown is "All Brands".
	 */
	@Test
	public void TC030DCAA_07(){
		result.addLog("ID : TC030DCAA_07 : Verify that the default items of 'Brand' filter dropdown is 'All Brands'");
		/*
		   1. Log into DTS portal as a DTS user
		   2. Navigate to "Accessories" page
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		/*
		 * The Brand filter dropdown is displayed and the default item is "All Brands"
		 */
		Assert.assertEquals(home.getItemSelected(AccessoryMain.BRAND_FILTER), "All Brands");
	}
	
	/*
	 * Verify that the accessories table lists maximum 50 items per page.
	 */
	@Test
	public void TC030DCAA_08(){
		result.addLog("ID : TC030DCAA_08 : Verify that the accessories table lists maximum 50 items per page.");
		/*
		 	1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		/*
		 * Verify that the accessories table list maximum 50 items per page.
		 */
		Assert.assertTrue(home.checkItemAmountDisplayOnTable(AccessoryMain.ACCESSORY_TABLE_INFO, 50));
	}
	
	/*
	 * Verify that the accessories table is hidden and displays text "No models match these criteria" when the report filter is empty.
	 */
	@Test
	public void TC030DCAA_10(){
		result.addLog("ID : TC030DCAA_10 : Verify that the accessories table is hidden and displays text 'No models match these criteria' when the report filter is empty");
		/*
		 	1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// Clean all product with suspended status
		home.deleteAllProductWithPublishedStatus("Suspended");
		/*
		 * Verify that the accessories table is hidden and displays text "No models match these criteria" when the report filter is empty.
		 */
		Assert.assertFalse(home.isElementPresent(AccessoryMain.ACCESSORY_TABLE));
		Assert.assertTrue(home.checkMessageDisplay("No models match these criteria"));
	}
	
	/*
	 * Verify that the accessories table display eight columns properly
	 */
	@Test
	public void TC030DCAA_11(){
		result.addLog("ID : TC030DCAA_11 : Verify that the accessories table display eight columns properly");
		/*
		 * 	1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
		 */
		// 2. Navigate to "Accessories" page
		home.click(DTSHome.LINK_ACCESSORIES);
		/*Verify that the accessories table have eight columns 
		 * "Picture", 
		 * "Model", 
		 * "Variant", 
		 * "Published", 
		 * "Version", 
		 * "Tuning Status", 
		 * "Headphone X Rating" and 
		 * "Marketing Status"
		 * 
		 */
		ArrayList<String> colums = home.getHeadColumByXpath(Xpath.ACCESSORY_THREAD);
		Boolean match = DTSUtil.containsAll(colums, Constant.getHeaderAccessoriesTable());
		Assert.assertTrue(match);
	}
	
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Draft".
	 */
	@Test
	public void TC030DCAA_12(){
		result.addLog("ID : TC030DCAA_12 : Verify that the accessories table lists out correct items when filtering with 'Draft'.");
		/*
		 	1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Change report filter to "Draft"
		 */
		// 2. Navigate to "Accessories" page
		home.click(DTSHome.LINK_ACCESSORIES);
		// 3. Change report filter to "Draft"
		home.selectReportsFilterByText(Xpath.accessoryFilterSelect, "Draft");
		/*
		 * The accessories table only lists out accessories which version status is Draft.
		 */
		Assert.assertTrue(home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.VERSION, Constant.ACCESSORY_DRAFT));
	}
	
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Tuning Request Pending".
	 */
	@Test
	public void TC030DCAA_13(){
		result.addLog("ID : TC030DCAA_13 : Verify that the accessories table lists out correct items when filtering with 'Tuning Request Pending'");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Change report filter to "Tuning Request Pending"
		 */
		/*
		 * PreCondition: Create product has status "Tuning Request Pending"
		 */
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Expand partner action link
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		// Click Request DTS Tuning link
		home.click(AccessoryInfo.REQUEST_TUNING);
		// Approve submit form
		home.selectACheckbox(TuningRequestForm.AGREE);
		home.click(TuningRequestForm.SUBMIT);
		/*
		 * ***************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Change report filter to "Tuning Request Pending"
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, Constant.TUNING_REQUEST_PENDING);
		/* 
		 * The accessories table only lists out accessories which 
		 * version status is Draft and the 
		 * Tuning status is DTS request pending.
		 */
		// version status is Draft
		Assert.assertTrue(home.verifyValueColumn(AccessoryMain.ACCESSORY_TABLE, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		// Tuning status is DTS request pending.
		Assert.assertTrue(home.verifyValueColumn(AccessoryMain.ACCESSORY_TABLE, Constant.TUNNING_STATUS, Constant.DTS_REQUEST_PENDING));
		// Delete product
		home.selectAnaccessorybyName(data.get("name"));
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Partner Tuning Review".
	 */
	@Test
	public void TC030DCAA_14(){
		result.addLog("ID : TC030DCAA_14 : Verify that the accessories table lists out correct items when filtering with 'Partner Tuning Review'");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Change report filter to "Partner Tuning Review"
		 */
		/*
		 * PreCondition: Create product has status "Partner Tuning Review"
		 */
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * ***************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Change report filter to "Partner Tuning Review"
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, Constant.partnerPartnerTuningReview);
		/* 
		 The accessories table only lists out accessories which 
		 version status is Draft and the 
		 Tuning status is Pending Partner Approval.
		 */
		Assert.assertTrue(home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		Assert.assertTrue(home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.TUNNING_STATUS, Constant.PENDING_PARTNER_APPROVAL));
		// Delete product
		home.selectAnaccessorybyName(data.get("name"));
		home.doDelete(AccessoryInfo.DELETE);
	
	}
	
	/*
	 * Verify that the accessories table lists out correct items when filtering with "DTS Tuning Review".
	 */
	@Test
	public void TC030DCAA_15(){
		result.addLog("ID : TC030DCAA_15 : Verify that the accessories table lists out correct items when filtering with 'DTS Tuning Review'.");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Change report filter to "DTS Tuning Review"
		 */
		/*
		 * Precondition: Create product has status "DTS Tuning Review"
		 */
		// Log out
		home.logout();
		// Log into DTS portal as partner user
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Click Request tuning review link
		home.click(AccessoryInfo.REQUEST_TUNING);
		// Log out
		home.logout();
		/*
		 * ****************************************************************
		 */
		// 1. Log into DTS portal as a DTS user
		home.login(superUsername, superUserpassword);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Change report filter to "DTS Tuning Review"
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, "DTS Tuning Review");
		/* 
		 * The Products table only lists out Products which version status is Draft and the Tuning status is  DTS Approval		
		 */
		Assert.assertTrue(home.verifyValueColumn(AccessoryMain.ACCESSORY_TABLE, "Version", "Draft"));
		Assert.assertTrue(home.verifyValueColumn(AccessoryMain.ACCESSORY_TABLE, "Tuning Status", "Pending DTS Review"));
		// Delete product
		home.selectAnaccessorybyName(data.get("name"));
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Ready for Marketing".
	 */
	@Test
	public void TC030DCAA_16(){
		result.addLog("ID : TC030DCAA_16 : Verify that the accessories table lists out correct items when filtering with 'Ready for Marketing'.");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Change report filter to"Ready for Marketing"
		 */
		/*
		 * Precondition: Create product has status "Ready for Marketing"
		 */
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryTuning();
		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Approve tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Change report filter to"Ready for Marketing"
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, Constant.READY_FOR_MARKETING);
		// Check if PDPP-1222 found
		if(!home.checkAnAccessoryExistByName(data.get("name"))){
			result.addErrorLog("PDPP-1222: 030 Catalog: All Active Model: The 'Ready for Marketing' filtering does not filter products that marketing material is uploaded");
			Assert.assertTrue(false);
		}
		/* 
		 * The Products table only lists out Products which version status is Draft, Tuning status must be Approved and Marketing status must be Unsubmitted
		 */
		Assert.assertTrue(home.verifyValueColumn(AccessoryMain.ACCESSORY_TABLE, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		Assert.assertTrue(home.verifyValueColumn(AccessoryMain.ACCESSORY_TABLE, Constant.TUNNING_STATUS, Constant.APPROVED));
		Assert.assertTrue(home.verifyValueColumn(AccessoryMain.ACCESSORY_TABLE, Constant.MARKETING_STATUS, Constant.UNSUBMITTED));
	}
	
	/*
	 * Verify that the accessories table lists out correct items when filtering with "DTS Marketing Review".
	 */
	@Test
	public void TC030DCAA_17(){
		result.addLog("ID : TC030DCAA_17 : Verify that the accessories table lists out correct items when filtering with 'DTS Marketing Review'.");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Change report filter to  "DTS Marketing Review"
		 */
		/*
		 * PreCondition: Create product has status "DTS Marketing Review"
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryTuning();
		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Approve tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Request marketing review
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		/*
		 * *****************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Change report filter to "DTS Marketing Review"
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, "DTS Marketing Review");
		/* 
		 * The Products table only lists out Products which version status is Draft, Tuning status is Approved and Marketing status is  DTS Marketing Review
		 */
		Assert.assertTrue(home.verifyValueColumn(AccessoryMain.ACCESSORY_TABLE, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		Assert.assertTrue(home.verifyValueColumn(AccessoryMain.ACCESSORY_TABLE, Constant.TUNNING_STATUS, Constant.APPROVED));
		Assert.assertTrue(home.verifyValueColumn(AccessoryMain.ACCESSORY_TABLE, Constant.MARKETING_STATUS, Constant.PENDING_DTS_MARKETING_REVIEW));
		// Delete product
		home.selectAnaccessorybyName(data.get("name"));
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Ready to Publish".
	 */
	@Test
	public void TC030DCAA_18(){
		result.addLog("ID : TC030DCAA_18 : Verify that the accessories table lists out correct items when filtering with 'Ready to Publish'.");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Change report filter to "Ready to Publish"
		 */
		/*
		 * Precondition: Create product has status "Ready to Publish"
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String, String> data = TestData.accessoryPublish();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Approve tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Approve marketing
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		home.click(AccessoryInfo.APPROVE_MARKETING);
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Change report filter to "Ready to Publish"
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, "Ready to Published");
		/* 
		 * The Products table only lists out Products which version status is Draft, Tuning and Marketing status must both be Approved
		 */
		Assert.assertTrue(home.verifyValueColumn(AccessoryMain.ACCESSORY_TABLE, Constant.VERSION, Constant.ACCESSORY_DRAFT));
		Assert.assertTrue(home.verifyValueColumn(AccessoryMain.ACCESSORY_TABLE, Constant.TUNNING_STATUS, Constant.APPROVED));
		Assert.assertTrue(home.verifyValueColumn(AccessoryMain.ACCESSORY_TABLE, Constant.MARKETING_STATUS, Constant.APPROVED));
		// Delete product
		home.selectAnaccessorybyName(data.get("name"));
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the Products table lists out correct items when filtering with "Published"
	 */
	@Test
	public void TC030DCAA_19(){
		result.addLog("ID : TC030DCAA_19 : Verify that the accessories table lists out correct items when filtering with 'Published'");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Change report filter to"Published"
		 */
		/*
		 * Precondition: Create product has status "Published"
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String, String> data = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), data);
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Change report filter to "Ready to Publish"
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, "Recently Published");
		/* 
		 * The Products table only lists out Products which Published status is Published
		 */
		Assert.assertTrue(home.verifyValueColumn(AccessoryMain.ACCESSORY_TABLE, Constant.PUBLISHED, Constant.PUBLISHED));
		// Delete product
		home.selectAnaccessorybyName(data.get("name"));
		home.doDelete(AccessoryInfo.DELETE);		
	}
	
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Need Attention".
	 */
	@Test
	public void TC030DCAA_20(){
		
		result.addLog("ID : TC030DCAA_20 : Verify that the accessories table lists out correct items when filtering with 'Need Attention'.");
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Accessories" page
		3. Change report filter to"Need Attention"
		*/
		
		// 2. Navigate to "Accessories" page
		home.click(DTSHome.LINK_ACCESSORIES);
		Assert.assertEquals("Pass", result.getResult());
		// 3. Change report filter to"Ready to Publish"
		home.selectReportsFilterByText(Xpath.accessoryFilterSelect, "Need Attention");
		// Check Tunning status is Declined or Revoked
		result = home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.TUNNING_STATUS, Constant.getListNeedsAttention());
		Assert.assertEquals("Pass", result.getResult());
		// Check Marketing status is Declined or Revoked
		result = home.verifyValueColumn(Xpath.BRAND_ACCESSORIES, Constant.MARKETING_STATUS, Constant.getListNeedsAttention());
		Assert.assertEquals("Pass", result.getResult());
	}
	
	/*
	 * Verify that the accessories table lists out correct items when filtering with "Suspended"
	 */
	@Test
	public void TC030DCAA_21(){
		result.addLog("ID : TC030DCAA_21 : Verify that the accessories table lists out correct items when filtering with 'Suspended'");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Change report filter to "Suspended"
		 */
		/*
		 * Precondition: Create product has status "Suspended"
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String, String> data = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), data);
		// Suspend product
		home.click(AccessoryInfo.SUSPEND);
		home.selectConfirmationDialogOption("Suspend");
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Change report filter to "Ready to Publish"
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, "Suspended");
		/* 
		 * The Products table only lists out Products which Published status is Published
		 */
		Assert.assertTrue(home.verifyValueColumn(AccessoryMain.ACCESSORY_TABLE, Constant.PUBLISHED, Constant.ACCESSORY_SUSPENDED));
		// Delete product
		home.selectAnaccessorybyName(data.get("name"));
		home.doDelete(AccessoryInfo.DELETE);		
	}
}
