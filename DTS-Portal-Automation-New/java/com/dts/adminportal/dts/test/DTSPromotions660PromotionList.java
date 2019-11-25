package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AddBrand;
import com.dts.adminportal.model.AddPromotion;
import com.dts.adminportal.model.CompanyInfo;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PromotionInfo;
import com.dts.adminportal.model.PromotionList;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.TestData;

public class DTSPromotions660PromotionList extends BasePage {

	@Override
	protected void initData() {
	}	
	/*
	 * Verify that the promotion table contains “Promo ID”, “Brand”, “Product Name”, “Target” and “Published” columns.
	 */
	@Test
	public void TC660DPL_01(){
		promotionControl.addLog("ID : TC660DPL_01 : Verify that the promotion table contains “Promo ID”, “Brand”, “Product Name”, “Target” and “Published” columns.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			VP: Verify that the promotion table contains “Promo ID”, “Brand”, “Product Name”, “Target” and “Published” columns.
			VP: Verify that the promotion table display total 25 items per page.
			VP: There is a number of total items in promotions list on  bottom left corner and pagination on bottom right corner of promotions list.
			VP: The default filtering value of Brand is  “All Brands” and Status is “Active”
			VP: All brand are displayed in alphabet order
			VP: Verify thatThere is a Brand and a Status filtering above the promotions list
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Promotions" page
		promotionControl.click(PageHome.linkPromotions);
		// Get all column headers
		ArrayList<String> headers = promotionControl.getAllColumnHeader(PromotionList.PROMOTION_TABLE);
		/*
		 	VP: Verify that the promotion table contains “Promo ID”, “Brand”, “Product Name”, “Target” and “Published” columns.
		*/
		Assert.assertTrue(ListUtil.containsAll(headers, PromotionList.columnHeaders));
		/*
		 	VP: Verify that the promotion table display total 25 items per page.
		*/
		Assert.assertTrue(promotionControl.checkAmountOfDisplayedItemOnTable(PromotionList.PROMOTION_TABLE_INFO, 25));
		// Check if PDPP-1191 found
		if(promotionControl.getPerPage(PromotionList.PROMOTION_TABLE_INFO) < 25){
			promotionControl.addErrorLog("PDPP-1289: 660 Promotion List: The promotion table only displays 10 items per page");
			Assert.assertTrue(false);
		}
		/*
		 	VP: There is a number of total items in promotions list on  bottom left corner and pagination on bottom right corner of promotions list.
		*/
		Assert.assertTrue(promotionControl.isElementPresent(PromotionList.PROMOTION_TABLE_INFO));
		Assert.assertTrue(promotionControl.isElementPresent(PromotionList.TABLE_PAGINATE));
		/*
		 	VP: The default filtering value of Brand is  “All Brands” and Status is “Active”
		*/
		Assert.assertEquals(promotionControl.getItemSelected(PromotionList.BRAND_FILTER), "All Brands");
		Assert.assertEquals(promotionControl.getItemSelected(PromotionList.STATUS_FILTER), "Active");
//		// List out the "Brand" filtering
//		ArrayList<String> listBeforeSorting = promotionControl.getAllComboboxOption(PromotionList.BRAND_FILTER);
//		// Get list after sorting
//		ArrayList<String> listAfterSorting = listBeforeSorting;
//		Collections.sort(listAfterSorting);
//		/*
//		 	VP: All brand are displayed in alphabet order
//		*/
//		Assert.assertTrue(listBeforeSorting.equals(listAfterSorting));
		/*
		 	VP: Verify thatThere is a Brand and a Status filtering above the promotions list
		*/
		Assert.assertTrue(promotionControl.isElementPresent(PromotionList.BRAND_FILTER));
	}
	
	/*
	 * Verify that the promotion table display total 25 items per page
	 */
//	@Test
//	public void TC660DPL_02(){
//		promotionControl.addLog("ID : TC660DPL_02 : Verify that the promotion table display total 25 items per page");
//		/*
//			1. Navigate to DTS portal
//			2. Log into DTS portal as a DTS user successfully
//			3. Navigate to "Promotions" page
//		 */
//		// 3. Navigate to "Promotions" page
//		promotionControl.click(PageHome.linkPromotions);
//		// Check if PDPP-1191 found
//		if(promotionControl.getPerPage(PromotionList.PROMOTION_TABLE_INFO) < 25){
//			promotionControl.addErrorLog("PDPP-1289: 660 Promotion List: The promotion table only displays 10 items per page");
//			Assert.assertTrue(false);
//		}
//		/*
//		 * Verify that the promotion table display total 25 items per page
//		 */
//		Assert.assertTrue(promotionControl.checkAmountOfDisplayedItemOnTable(PromotionList.PROMOTION_TABLE_INFO, 25));
//	}
//	
	/*
	 * Verify that the total items number and pagination are displayed in Promotion List page
	 */
//	@Test
//	public void TC660DPL_03(){
//		promotionControl.addLog("ID : TC660DPL_03 : Verify that the total items number and pagination are displayed in Promotion List page");
//		/*
//			1. Navigate to DTS portal
//			2. Log into DTS portal as a DTS user successfully
//			3. Navigate to "Promotions" page
//		 */
//		// 3. Navigate to "Promotions" page
//		promotionControl.click(PageHome.linkPromotions);
//		/*
//		 * There is a number of total items in promotions list on bottom left corner and pagination on bottom right corner of promotions list
//		 */
//		Assert.assertTrue(promotionControl.isElementPresent(PromotionList.PROMOTION_TABLE_INFO));
//		Assert.assertTrue(promotionControl.isElementPresent(PromotionList.TABLE_PAGINATE));
//	}
	
	/*
	 * Verify that there is a Brand and a Status filtering displayed in Promotion list page
	 */
//	@Test
//	public void TC660DPL_04(){
//		promotionControl.addLog("ID : TC660DPL_04 : Verify that there is a Brand and a Status filtering displayed in Promotion list page");
//		/*
//			1. Navigate to DTS portal
//			2. Log into DTS portal as a DTS user successfully
//			3. Navigate to "Promotions" page
//		 */
//		// 3. Navigate to "Promotions" page
//		promotionControl.click(PageHome.linkPromotions);
//		/*
//		 * Verify thatThere is a Brand and a Status filtering above the promotions list
//		 */
//		Assert.assertTrue(promotionControl.isElementPresent(PromotionList.BRAND_FILTER));
//	}
	
	/*
	 * Verify that when the Promotion page is loaded, the default filtering value is  ï¿½All Brandsï¿½ and ï¿½Activeï¿½
	 */
//	@Test
//	public void TC660DPL_07(){
//		promotionControl.addLog("ID : TC660DPL_07 : Verify that when the Promotion page is loaded, the default filtering value is  ï¿½All Brandsï¿½ and ï¿½Activeï¿½");
//		/*
//			1. Navigate to DTS portal
//			2. Log into DTS portal as a DTS user successfully
//			3. Navigate to "Promotions" page
//		 */
//		// 3. Navigate to "Promotions" page
//		promotionControl.click(PageHome.linkPromotions);
//		/*
//		 * The default filtering value of Brand is  ï¿½All Brandsï¿½ and Status is ï¿½Activeï¿½
//		 */
//		Assert.assertEquals(promotionControl.getItemSelected(PromotionList.BRAND_FILTER), "All Brands");
//		Assert.assertEquals(promotionControl.getItemSelected(PromotionList.STATUS_FILTER), "Active");
//	}
	
	/*
	   Verify that only brands which have at least 1 promotion is displayed in “Brands” filtering.
	 */
	@Test
	public void TC660DPL_08(){
		promotionControl.addLog("ID : TC660DPL_07 : Verify that only brands which have at least 1 promotion is displayed in “Brands” filtering");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. List out the "Brand" filtering 
			VP: Only brands which have at least 1 promotion is displayed in “Brand” filtering
		 */
		/*
		  Pre-condition: Create a new empty brand and a promotion for an existed brand 
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Companies page
		promotionControl.click(PageHome.LINK_COMPANY);
		// Select a company
		companyControl.selectACompanyByName(PARTNER_COMPANY_NAME);
		// Click Add New Brand link
		promotionControl.click(CompanyInfo.ADD_BRAND);
		// Add new brand
		Hashtable<String,String> dataBrand = TestData.brandDraft();
		companyControl.addBrand(AddBrand.getHash(), dataBrand);
		// Navigate to product page
		promotionControl.click(PageHome.linkAccessories);
		// Click Add product link
		promotionControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String, String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		// Navigate to Promotion page
		promotionControl.click(PageHome.linkPromotions);
		// Click Add promotion link
		promotionControl.click(PromotionList.ADD_PROMO);
		// Create new promotion
		Hashtable<String, String> dataPromotion = TestData.promotionGlobal(dataProduct.get("brand") + " " + dataProduct.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataPromotion);
		/*
		 * *****************************************************************
		 */
		// 3. Navigate to "Promotions" page
		promotionControl.click(PageHome.linkPromotions);
		// 4. List out the "Brand" filtering
		ArrayList<String> listBrand = promotionControl.getAllComboboxOption(PromotionList.BRAND_FILTER);
		Assert.assertTrue(ListUtil.checkListContain(listBrand, PARTNER_BRAND_NAME_1));
		Assert.assertFalse(ListUtil.checkListContain(listBrand, dataBrand.get("name")));
		// Delete promotion
		promotionControl.selectAPromotionByName(PromotionList.PROMOTION_TABLE, dataPromotion.get("name"));
		promotionControl.doDelete(PromotionInfo.DELETE);
	}
	
	/*
	 * Verify that items in "Brand" filtering is displayed in alphabet order
	 */
	@Test
	public void TC660DPL_10(){
		promotionControl.addLog("ID : TC660DPL_09 : Verify that items in “Brand” filtering is displayed in alphabet order");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. List out the "Brand" filtering
			VP: All brand are displayed in alphabet order 
			VP: Promotions with expiration day close current day is displayed on top
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Promotions" page
		promotionControl.click(PageHome.linkPromotions);
		// 4. List out the "Brand" filtering
		ArrayList<String> listBeforeSorting = promotionControl.getAllComboboxOption(PromotionList.BRAND_FILTER);
		// Get list after sorting
		ArrayList<String> listAfterSorting = listBeforeSorting;
		Collections.sort(listAfterSorting);
		/*
		 	VP: All brand are displayed in alphabet order
		 */
		Assert.assertTrue(listBeforeSorting.equals(listAfterSorting));
		/*
		 	VP: Promotions with expiration day close current day is displayed on top
		 */
		// Check if PDPP-1229 found
		promotionControl.selectOptionByName(PromotionList.STATUS_FILTER, "Expired");
		if(promotionControl.getTextByXpath(PromotionList.PROMOTION_TABLE).contains("No data available in table")){
			promotionControl.addErrorLog("PDPP-1229 - 660. Promos List Page");
			Assert.assertTrue(false);
		}
	}
	
	/*
	 * Verify that when the Promotion page is loaded, the default sorting is based  on the expiration day with the closest day on top
	 */
//	@Test
//	public void TC660DPL_10(){
//		promotionControl.addLog("ID : TC660DPL_10 : Verify that when the Promotion page is loaded, the default sorting is based  on the expiration day with the closest day on top");
//		/*
//			1. Navigate to DTS portal
//			2. Log into DTS portal as a DTS user successfully
//			3. Navigate to "Promotions" page
//			4. List out the "Brand" filtering 
//			VP: Promotions with expiration day close current day is displayed on top
//		 */
//		// 3. Navigate to "Promotions" page
//		promotionControl.click(PageHome.linkPromotions);
//		// Check if PDPP-1229 found
//		promotionControl.selectOptionByName(PromotionList.STATUS_FILTER, "Expired");
//		if(promotionControl.getTextByXpath(PromotionList.PROMOTION_TABLE).contains("No data available in table")){
//			promotionControl.addErrorLog("PDPP-1229 - 660. Promos List Page");
//			Assert.assertTrue(false);
//		}
//	}
	
	/*
	 * Verify that the promotions list only display expired promotion when filtering with "Expired" status
	 */
	@Test
	public void TC660DPL_11(){
		promotionControl.addLog("ID : TC660DPL_11 : Verify that the promotions list only display expired promotion when filtering with “Expired” status");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Set the Status filtering to “Expired”
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Promotions" page
		promotionControl.click(PageHome.linkPromotions);
		// 4. Set the Status filtering to “Expired”
		promotionControl.selectOptionByName(PromotionList.STATUS_FILTER, "Expired");
		// Check if PDPP-1229 found
		if(promotionControl.getTextByXpath(PromotionList.PROMOTION_TABLE).contains("No data available in table")){
			promotionControl.addErrorLog("PDPP-1229 - 660. Promos List Page");
			Assert.assertTrue(false);
		}
		
	}
	
	/*
	 * Verify that the promotion table is filtered by brand when clicking on brand name link
	 */
	@Test
	public void TC660DPL_12(){
		promotionControl.addLog("ID : TC660DPL_12 : Verify that the promotion table is filtered by brand when clicking on brand name link");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Click on a brand name link 
		 */
		/*
		 * Pre-condition: Create a promotion for an existed brand 
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		promotionControl.click(PageHome.linkAccessories);
		// Click Add product link
		promotionControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		// Navigate to Promotion page
		promotionControl.click(PageHome.linkPromotions);
		// Click Add promotion link
		promotionControl.click(PromotionList.ADD_PROMO);
		// Create promotion
		Hashtable<String,String> dataGlobalPromotion = TestData.promotionGlobal(dataProduct.get("brand") + " " + dataProduct.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataGlobalPromotion);
		/*
		 * *****************************************************************
		 */
		// 3. Navigate to "Promotions" page
		promotionControl.click(PageHome.linkPromotions);
		// 4. Click on a brand name link
		promotionControl.selectBrandFilterByClickOnLink(PromotionList.PROMOTION_TABLE, PARTNER_BRAND_NAME_1);
		/*
		 * Verify that Only promotions of clicked brand are displayed in promotion list and the Brand filter combobox is set to clicked brand
		 */
		Assert.assertTrue(promotionControl.verifyValueColumn(PromotionList.PROMOTION_TABLE, "Brand", PARTNER_BRAND_NAME_1));
		Assert.assertEquals(promotionControl.getItemSelected(PromotionList.BRAND_FILTER), PARTNER_BRAND_NAME_1);
		// Delete promotion
		promotionControl.selectAPromotionByName(PromotionList.PROMOTION_TABLE, dataGlobalPromotion.get("name"));
		promotionControl.doDelete(PromotionInfo.DELETE);
	}
	
	/*
	 * Verify that the portal redirect to 680 Promo Detail page when clicking on Model Name link
	 */
	@Test
	public void TC660DPL_13(){
		promotionControl.addLog("ID : TC660DPL_13 : Verify that the portal redirect to 680 Promo Detail page when clicking on Model Name link");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Click on a model name link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Promotions" page
		promotionControl.click(PageHome.linkPromotions);
		// 4. Click on a model name link
		promotionControl.selectAnItemOnTable();
		/*
		 * Verify that Portal redirects to 680 Promo Detail pgae
		 */
		Assert.assertEquals(promotionControl.existsElement(PromotionInfo.getElementInfo()), true);
	}
	
	/*
	 * Verify that the portal redirect to 640 Product Detail Page: Device when clicking on Target link
	 */
	@Test
	public void TC660DPL_14(){
		promotionControl.addLog("ID : TC660DPL_14 : Verify that the portal redirect to 640 Product Detail Page: Device when clicking on Target link");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Promotions" page
			4. Click on a Target product name link
		 */
		/*
		 * PreCondition: Create new global promotion
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		promotionControl.click(PageHome.linkAccessories);
		// Click Add product link
		promotionControl.click(ProductModel.ADD_PRODUCT);
		// Create 1st published product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		// Navigate to Promotion page
		promotionControl.click(PageHome.linkPromotions);
		// Click Add promo link
		promotionControl.click(PromotionList.ADD_PROMO);
		// Create Global promotion
		Hashtable<String,String> dataGlobalPromotion = TestData.promotionGlobal(dataProduct.get("brand") + " " + dataProduct.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataGlobalPromotion);
		/*
		 * ******************************************************
		 */
		// 3. Navigate to "Promotions" page
		promotionControl.click(PageHome.linkPromotions);
		// 4. Click on a Target product name link
		promotionControl.selectProductInPromotionList(dataProduct.get("name"));
		/*
		 * Portal redirects to 640 Product Detail Page: Device
		 */
		Assert.assertEquals(promotionControl.getTextByXpath(ProductDetailModel.TITLE_NAME), dataProduct.get("name"));
		// Delete product
		promotionControl.doDelete(ProductDetailModel.DELETE);
	}
}
