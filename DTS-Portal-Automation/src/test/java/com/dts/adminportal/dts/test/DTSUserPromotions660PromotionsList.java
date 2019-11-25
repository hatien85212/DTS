package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

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
import dts.com.adminportal.model.AddBrand;
import dts.com.adminportal.model.AddPromotion;
import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.CompanyInfo;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.PromotionInfo;
import dts.com.adminportal.model.PromotionList;
import dts.com.adminportal.model.UserEdit;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.Xpath;

public class DTSUserPromotions660PromotionsList extends CreatePage {
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
	 * Verify that the promotion table contains “Promo ID”, “Brand”, “Model Name”, “Target”, “Published” and “Expiration” columns
	 */
	@Test
	public void TC660DPL_01(){
		result.addLog("ID : TC660DPL_01 : Verify that the promotion table contains “Promo ID”, “Brand”, “Model Name”, “Target”, “Published” and “Expiration” columns");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
		 */
		// 3. Navigate to "Promotions" page
		home.click(Xpath.linkPromotions);
		// Get all column headers
		ArrayList<String> headers = home.getAllColumnHeader(PromotionList.PROMOTION_TABLE);
		/*
		 * Verify that the promotion table contains “Promo ID”, “Brand”, “Model Name”, “Target”, “Published” and “Expiration” columns.
		 */
		Assert.assertTrue(DTSUtil.containsAll(headers, PromotionList.columnHeaders));
	}
	
	/*
	 * Verify that the promotion table display total 25 items per page
	 */
	@Test
	public void TC660DPL_02(){
		result.addLog("ID : TC660DPL_02 : Verify that the promotion table display total 25 items per page");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
		 */
		// 3. Navigate to "Promotions" page
		home.click(Xpath.linkPromotions);
		// Check if PDPP-1191 found
		if(home.getPerPage(PromotionList.PROMOTION_TABLE_INFO) < 25){
			result.addErrorLog("PDPP-1289: 660 Promotion List: The promotion table only displays 10 items per page");
			Assert.assertTrue(false);
		}
		/*
		 * Verify that the promotion table display total 25 items per page
		 */
		Assert.assertTrue(home.checkItemAmountDisplayOnTable(PromotionList.PROMOTION_TABLE_INFO, 25));
	}
	
	/*
	 * Verify that the total items number and pagination are displayed in Promotion List page
	 */
	@Test
	public void TC660DPL_03(){
		result.addLog("ID : TC660DPL_03 : Verify that the total items number and pagination are displayed in Promotion List page");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
		 */
		// 3. Navigate to "Promotions" page
		home.click(Xpath.linkPromotions);
		/*
		 * There is a number of total items in promotions list on bottom left corner and pagination on bottom right corner of promotions list
		 */
		Assert.assertTrue(home.isElementPresent(PromotionList.PROMOTION_TABLE_INFO));
		Assert.assertTrue(home.isElementPresent(PromotionList.TABLE_PAGINATE));
	}
	
	/*
	 * Verify that there is a Brand and a Status filtering displayed in Promotion list page
	 */
	@Test
	public void TC660DPL_04(){
		result.addLog("ID : TC660DPL_04 : Verify that there is a Brand and a Status filtering displayed in Promotion list page");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
		 */
		// 3. Navigate to "Promotions" page
		home.click(Xpath.linkPromotions);
		/*
		 * Verify thatThere is a Brand and a Status filtering above the promotions list
		 */
		Assert.assertTrue(home.isElementPresent(PromotionList.BRAND_FILTER));
	}
	
	/*
	 * Verify that the “Actions” module is not displayed when the “Add and manage promotions” privilege is disabled
	 */
	@Test
	public void TC660DPL_05(){
		result.addLog("ID : TC660DPL_05 : Verify that the “Actions” module is not displayed when the “Add and manage promotions” privilege is disabled");
		/*
			1. Log into DTS portal as a super user successfully
			2. Navigate to “Users” page
			3. Select a DTS user from Users list
			4. Click “Edit” link
			5. Disable the “Add and manage promotions” privilege
			6. Click “Save” link
			7. Log out DTS portal successfully
			8. Log into DTS portal as above DTS user successfully
			9. Navigate to “Promotions” page
		 */
		// 2. Navigate to “Users” page
		home.click(Xpath.LINK_USERS);
		// 3. Select a DTS user from Users list
		home.dtsSelectUserByEmail(dtsUser);
		// 4. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 5. Disable the “Add and manage promotions” privilege
		home.enableAllPrivilege();
		home.disablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.Add_manage_promos);
		// 6. Click “Save” link
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal successfully
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 9. Navigate to “Promotions” page
		home.click(Xpath.linkPromotions);
		/*
		 * Verify that The “Actions” module is not displayed
		 */
		Assert.assertFalse(home.isElementPresent(PromotionList.ACTION_MODULE));
	}
	
	/*
	 * Verify that the “Actions” module is displayed when the “Add and manage promotions” privilege is enabled
	 */
	@Test
	public void TC660DPL_06(){
		result.addLog("ID : TC660DPL_06 : Verify that the “Actions” module is displayed when the “Add and manage promotions” privilege is enabled");
		/*
			1. Log into DTS portal as a super user successfully
			2. Navigate to “Users” page
			3. Select a DTS user from Users list
			4. Click “Edit” link
			5. Enable the “Add and manage promotions” privilege
			6. Click “Save” link
			7. Log out DTS portal successfully
			8. Log into DTS portal as above DTS user successfully
			9. Navigate to “Promotions” page
		 */
		// 2. Navigate to “Users” page
		home.click(Xpath.LINK_USERS);
		// 3. Select a DTS user from Users list
		home.dtsSelectUserByEmail(dtsUser);
		// 4. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 5. Enable the “Add and manage promotions” privilege
		// 6. Click “Save” link
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_promos);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal successfully
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		// 9. Navigate to “Promotions” page
		home.click(Xpath.linkPromotions);
		/*
		 * Verify that The “Actions” module and  is displayed along with “Add Promo” link
		 */
		Assert.assertTrue(home.isElementPresent(PromotionList.ACTION_MODULE));
		Assert.assertTrue(home.isElementPresent(PromotionList.ADD_PROMO));
	}
	
	/*
	 * Verify that when the Promotion page is loaded, the default filtering value is  “All Brands” and “Active”
	 */
	@Test
	public void TC660DPL_07(){
		result.addLog("ID : TC660DPL_07 : Verify that when the Promotion page is loaded, the default filtering value is  “All Brands” and “Active”");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
		 */
		// 3. Navigate to "Promotions" page
		home.click(Xpath.linkPromotions);
		/*
		 * The default filtering value of Brand is  “All Brands” and Status is “Active”
		 */
		Assert.assertEquals(home.getItemSelected(PromotionList.BRAND_FILTER), "All Brands");
		Assert.assertEquals(home.getItemSelected(PromotionList.STATUS_FILTER), "Active");
	}
	
	/*
	 * Verify that only brands which have at least 1 promotion is displayed in “Brands” filtering
	 */
	@Test
	public void TC660DPL_08(){
		result.addLog("ID : TC660DPL_07 : Verify that only brands which have at least 1 promotion is displayed in “Brands” filtering");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. List out the “Brand” filtering 
		 */
		/*
		 * Pre-condition: Create a new empty brand and a promotion for an existed brand 
		 */
		// Navigate to Companies page
		home.click(Xpath.LINK_COMPANY);
		// Select a company
		home.selectACompanyByName(partner_company_name);
		// Click Add New Brand link
		home.click(CompanyInfo.ADD_BRAND);
		// Add new brand
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		home.addBrand(AddBrand.getHash(), dataBrand);
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String, String> dataProduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct);
		// Navigate to Promotion page
		home.click(Xpath.linkPromotions);
		// Click Add promotion link
		home.click(PromotionList.ADD_PROMO);
		// Create new promotion
		Hashtable<String, String> dataPromotion = TestData.promotionGlobal(dataProduct.get("brand") + " " + dataProduct.get("name"));
		home.addPromotion(AddPromotion.getHash(), dataPromotion);
		/*
		 * *****************************************************************
		 */
		// 3. Navigate to "Promotions" page
		home.click(Xpath.linkPromotions);
		// 4. List out the “Brand” filtering
		ArrayList<String> listBrand = home.getAllComboboxOption(PromotionList.BRAND_FILTER);
		Assert.assertTrue(DTSUtil.checkListContain(listBrand, partner_brand_name_1));
		Assert.assertFalse(DTSUtil.checkListContain(listBrand, dataBrand.get("name")));
		// Delete promotion
		home.selectAPromotionByName(PromotionList.PROMOTION_TABLE, dataPromotion.get("name"));
		home.doDelete(PromotionInfo.DELETE);
	}
	
	/*
	 * Verify that items in “Brand” filtering is displayed in alphabet order
	 */
	@Test
	public void TC660DPL_09(){
		result.addLog("ID : TC660DPL_09 : Verify that items in “Brand” filtering is displayed in alphabet order");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. List out the “Brand” filtering 
		 */
		// 3. Navigate to "Promotions" page
		home.click(Xpath.linkPromotions);
		// 4. List out the “Brand” filtering
		ArrayList<String> listBeforeSorting = home.getAllComboboxOption(PromotionList.BRAND_FILTER);
		// Get list after sorting
		ArrayList<String> listAfterSorting = listBeforeSorting;
		Collections.sort(listAfterSorting);
		/*
		 * All brand are displayed in alphabet order
		 */
		Assert.assertTrue(listBeforeSorting.equals(listAfterSorting));
	}
	
	/*
	 * Verify that when the Promotion page is loaded, the default sorting is based  on the expiration day with the closest day on top
	 */
	@Test
	public void TC660DPL_10(){
		result.addLog("ID : TC660DPL_10 : Verify that when the Promotion page is loaded, the default sorting is based  on the expiration day with the closest day on top");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. List out the “Brand” filtering 
		 */
		// 3. Navigate to "Promotions" page
		home.click(Xpath.linkPromotions);
		// Check if PDPP-1229 found
		home.selectOptionByName(PromotionList.STATUS_FILTER, "Expired");
		if(home.getTextByXpath(PromotionList.PROMOTION_TABLE).contains("No data available in table")){
			result.addErrorLog("PDPP-1229 - 660. Promos List Page");
			Assert.assertTrue(false);
		}
	}
	
	/*
	 * Verify that the promotions list only display expired promotion when filtering with “Expired” status
	 */
	@Test
	public void TC660DPL_11(){
		result.addLog("ID : TC660DPL_11 : Verify that the promotions list only display expired promotion when filtering with “Expired” status");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Set the Status filtering to “Expired”
		 */
		// 3. Navigate to "Promotions" page
		home.click(Xpath.linkPromotions);
		// 4. Set the Status filtering to “Expired”
		home.selectOptionByName(PromotionList.STATUS_FILTER, "Expired");
		// Check if PDPP-1229 found
		if(home.getTextByXpath(PromotionList.PROMOTION_TABLE).contains("No data available in table")){
			result.addErrorLog("PDPP-1229 - 660. Promos List Page");
			Assert.assertTrue(false);
		}
		
	}
	
	/*
	 * Verify that the promotion table is filtered by brand when clicking on brand name link
	 */
	@Test
	public void TC660DPL_12(){
		result.addLog("ID : TC660DPL_12 : Verify that the promotion table is filtered by brand when clicking on brand name link");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Click on a brand name link 
		 */
		/*
		 * Pre-condition: Create a promotion for an existed brand 
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String, String> dataProduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct);
		// Navigate to Promotion page
		home.click(Xpath.linkPromotions);
		// Click Add promotion link
		home.click(PromotionList.ADD_PROMO);
		// Create promotion
		Hashtable<String, String> dataPromotion = TestData.promotionGlobal(dataProduct.get("brand") + " " + dataProduct.get("name"));
		home.addPromotion(AddPromotion.getHash(), dataPromotion);
		/*
		 * *****************************************************************
		 */
		// 3. Navigate to "Promotions" page
		home.click(Xpath.linkPromotions);
		// 4. Click on a brand name link
		home.selectBrandFilterByClickOnLink(PromotionList.PROMOTION_TABLE, partner_brand_name_1);
		/*
		 * Verify that Only promotions of clicked brand are displayed in
		 * promotion list and the Brand filter combobox is set to clicked brand
		 */
		Assert.assertTrue(home.verifyValueColumn(PromotionList.PROMOTION_TABLE, "Brand", partner_brand_name_1));
		Assert.assertEquals(home.getItemSelected(PromotionList.BRAND_FILTER), partner_brand_name_1);
		// Delete promotion
		home.selectAPromotionByName(PromotionList.PROMOTION_TABLE, dataPromotion.get("name"));
		home.doDelete(PromotionInfo.DELETE);
	}
	
	/*
	 * Verify that the portal redirect to 680 Promo Detail page when clicking on Model Name link
	 */
	@Test
	public void TC660DPL_13(){
		result.addLog("ID : TC660DPL_13 : Verify that the portal redirect to 680 Promo Detail page when clicking on Model Name link");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Click on a model name link
		 */
		
		// 3. Navigate to "Promotions" page
		home.click(Xpath.linkPromotions);
		// 4. Click on a model name link
		home.selectAnItemOnTable();
		/*
		 * Verify that Portal redirects to 680 Promo Detail pgae
		 */
		Assert.assertEquals(home.existsElement(PromotionInfo.getElementInfo()).getResult(), "Pass");
	}
	
	/*
	 * Verify that the portal redirect to 640 Product Detail Page: Device when clicking on Target link
	 */
	@Test
	public void TC660DPL_14(){
		result.addLog("ID : TC660DPL_14 : Verify that the portal redirect to 640 Product Detail Page: Device when clicking on Target link");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Click on a Target product name link
		 */
		/*
		 * PreCondition: Create new global promotion
		 */
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create 1st published product
		Hashtable<String,String> dataProduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct);
		// Navigate to Promotion page
		home.click(Xpath.linkPromotions);
		// Click Add promo link
		home.click(PromotionList.ADD_PROMO);
		// Create Global promotion
		Hashtable<String,String> dataGlobalPromotion = TestData.promotionGlobal(dataProduct.get("brand") + " " + dataProduct.get("name"));
		home.addPromotion(AddPromotion.getHash(), dataGlobalPromotion);
		/*
		 * ******************************************************
		 */
		// 3. Navigate to "Promotions" page
		home.click(Xpath.linkPromotions);
		// 4. Click on a Target product name link
		home.selectProductInPromotionList(dataProduct.get("name"));
		/*
		 * Portal redirects to 640 Product Detail Page: Device
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), dataProduct.get("name"));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the portal redirect to 690Da Promo Edit page when clicking on “Add Promo” link
	 */
	@Test
	public void TC660DPL_15(){
		result.addLog("ID : TC660DPL_15 : Verify that the “Actions” module is displayed when the “Add and manage promotions” privilege is enabled");
		/*
			Pre-condition: DTS user has “Add and manage promotions” privilege
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Click “Add Promo' link
		 */
	
		// 3. Navigate to "Promotions" page
		home.click(Xpath.linkPromotions);
		// 4. Click “Add Promo' link
		home.click(PromotionList.ADD_PROMO);
		/*
		 * Verify that The 690Da Promo Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddPromotion.getElementInfo()).getResult(), "Pass");
	}
}
