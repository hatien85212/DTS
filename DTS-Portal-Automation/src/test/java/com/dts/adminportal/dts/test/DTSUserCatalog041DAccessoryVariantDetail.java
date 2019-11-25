package com.dts.adminportal.dts.test;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AccessoryInfo;
import dts.com.adminportal.model.AccessoryMain;
import dts.com.adminportal.model.AddAccessory;
import dts.com.adminportal.model.AddVariant;
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.PartnerAccessoryInfo;
import dts.com.adminportal.model.PartnerVariantInfo;
import dts.com.adminportal.model.VariantInfo;
import dts.com.adminportal.model.Xpath;

public class DTSUserCatalog041DAccessoryVariantDetail extends CreatePage{
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
	 * Verify that the correct 041D product Variant Detail page is displayed properly after adding new product variant
	 */
	@Test
	public void TC041DAVD_01(){
		result.addLog("ID : TC041DAVD_01: Verify that the correct 041D product Variant Detail page is displayed properly after adding new product variant");
		/*
	 		Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			VP. Verify that 040D product Detail page is displayed
			4. Click "Add New Variant" link in "Model Actions" module
			VP. The 052D Model Variant Edit page is displayed.
			5. Fill valid value into all requires fields
			6. Click "Save" link
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		String productName = home.selectAnAccessory();
		/*
		 * VP. Verify that 040D product Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), productName);
		// 4. Click "Add New Variant" link in "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. The 052D Model Variant Edit page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		// 5. Fill valid value into all requires fields
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.variantDraft();
		home.addVariant(AddVariant.getHash(), data);
		/*
		 * Verify that The correct 041D product Variant Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.TITLE_NAME), data.get("name"));
		Assert.assertEquals(home.existsElement(VariantInfo.getElementsInfo()).getResult(), "Pass");
	}
	
	/*
	 * Verify that the correct 041D product Variant Detail page is displayed properly when selecting from Model Variant list
	 */
	@Test
	public void TC041DAVD_02(){
		result.addLog("ID : TC041DAVD_02: Verify that the correct 041D product Variant Detail page is displayed properly when selecting from Model Variant list");
		/*
	 		Pre-condition: User has "Add and manage Products" rights. The product has at least one variant.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			VP. Verify that 040D product Detail page is displayed
			4. Select an product variant in Model Variant module
		*/
		/*
		 * PreCondition: Create new product which has at lease one variant
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String, String> dataProduct = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), dataProduct);
		// Click Add new variant link
		home.click(AccessoryInfo.ADD_VARIANT);
		// Create new variant
		Hashtable<String,String> dataVariant = TestData.variantDraft();
		home.addVariant(AddVariant.getHash(), dataVariant);
		/*
		 * **************************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnaccessorybyName(dataProduct.get("name"));
		/*
		 * VP. Verify that 040D product Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), dataProduct.get("name"));
		// 4. Select an product variant in Model Variant module
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * The correct  041D product Variant Detail page is displayed properly
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.TITLE_NAME), dataVariant.get("name"));
		Assert.assertEquals(home.existsElement(VariantInfo.getElementsInfo()).getResult(), "Pass");
		// Delete product
		home.click(VariantInfo.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the Publishing Process and Model Variant Actions modules are displayed in 041D product Variant Details page
	 */
	@Test
	public void TC041DAVD_03(){
		result.addLog("ID : TC041DAVD_03: Verify that the Publishing Process and Model Variant Actions modules are displayed in 041D product Variant Details page");
		/*
	 		Pre-condition: User has "Add and manage Products" rights. The product has at least one variant.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			VP. Verify that 040D product Detail page is displayed
			4. Select an product variant in Model Variant module
		*/
		/*
		 * PreCodition: User has "Add and manage Products" rights. The product has at least one variant
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> dataProduct = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), dataProduct);
		// Click Add new variant link
		home.click(AccessoryInfo.ADD_VARIANT);
		// Create new variant
		Hashtable<String,String> dataVariant = TestData.variantDraft();
		home.addVariant(AddVariant.getHash(), dataVariant);
		/*
		 * **************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Select an accessory from accessories table
		home.selectAnaccessorybyName(dataProduct.get("name"));
		/*
		 * VP. Verify that 040D product Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), dataProduct.get("name"));
		// 4. Select an accessory variant in Model Variant module
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * The Publishing Process and Model Variant Actions modules are displayed in 041D product Variant Details page
		 */
		Assert.assertTrue(home.isElementPresent(VariantInfo.PUBLISHING_PROCESS_CONTAINER));
		Assert.assertTrue(home.isElementPresent(VariantInfo.ACTION_CONTAINER));
		// Delete product
		home.click(VariantInfo.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the Primary Catalog Image section is displayed in order from left to right
	 */
	@Test
	public void TC041DAVD_04(){
		result.addLog("ID : TC041DAVD_04: Verify that the Primary Catalog Image section is displayed in order from left to right");
		/*
		  	Pre-condition: User has "Add and manage accessories" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
			VP. Verify that 040D Accessory Detail page is displayed
			4. Click "Add New Variant" link in "Model Actions" module
			VP. The 052D Model Variant Edit page is displayed.
			5. Fill valid value into all requires fields
			6. Upload three size for custom primary  catalog images
			7. Click "Save" link
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Select an accessory from accessories table
		String productName = home.selectAnAccessory();
		/*
		 * VP. Verify that 040D Accessory Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), productName);
		// 4. Click "Add New Variant" link in "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. The 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 5. Fill valid value into all requires fields
		// 6. Upload three size for custom primary  catalog images
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.variantImage();
		home.addVariant(AddVariant.getHash(), data);
		/*
		 * Verify that The 040D product Variant Details page is displayed and the primary catalog image dislays in order from left to right: 250x250,500x500 and 1000x1000
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.TITLE_NAME), data.get("name"));
		Assert.assertTrue(home.isImageDisplayLeftToRight(VariantInfo.IMAGE_TABLE));
	}
	
	/*
	 * Verify that the Lightbox style popup with the picture showing in full size is displayed when clicking on the primary catalog images
	 */
	@Test
	public void TC041DAVD_05(){
		result.addLog("ID : TC041DAVD_05: Verify that the Lightbox style popup with the picture showing in full size is displayed when clicking on the primary catalog images");
		/*
		  	Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			VP. Verify that 040D product Detail page is displayed
			4. Click "Add New Variant" link in "Model Actions" module
			VP. The 052D Model Variant Edit page is displayed.
			5. Fill valid value into all requires fields
			6. Upload three size for custom primary  catalog images
			7. Click "Save" link
			VP: The 041D product Variant Detail page is displayed with three size of primary catalog images
			8. Click on picture of each primary catalog image
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Select an accessory from accessories table
		String productName = home.selectAnAccessory();
		/*
		 * VP. Verify that 040D Accessory Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), productName);
		// 4. Click "Add New Variant" link in "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. The 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 5. Fill valid value into all requires fields
		// 6. Upload three size for custom primary  catalog images
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.variantImage();
		home.addVariant(AddVariant.getHash(), data);
		/*
		 * VP: The 041D product Variant Detail page is displayed with three size of primary catalog images
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.TITLE_NAME), data.get("name"));
		// 8. Click on image thumbnail 250
		home.click(VariantInfo.CATALOG_IMAGE_THUMBNAIL_250);
		home.waitForElementClickable(VariantInfo.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(VariantInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(VariantInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[0].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(VariantInfo.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(VariantInfo.LIGHTBOX_STYLE_IMAGE);
		// Click on image thumbnail 500
		home.click(VariantInfo.CATALOG_IMAGE_THUMBNAIL_500);
		home.waitForElementClickable(VariantInfo.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(VariantInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(VariantInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[1].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(VariantInfo.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(VariantInfo.LIGHTBOX_STYLE_IMAGE);
		// Click on image thumbnail 500
		home.click(VariantInfo.CATALOG_IMAGE_THUMBNAIL_1000);
		home.waitForElementClickable(VariantInfo.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(VariantInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(VariantInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[2].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(VariantInfo.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(VariantInfo.LIGHTBOX_STYLE_IMAGE);
	}
	
	/*
	 * Verify that the Lightbox style popup with the picture showing in full size is displayed when clicking on the image of Marketing Material
	 */
	@Test
	public void TC041DAVD_06(){
		result.addLog("ID : TC041DAVD_06: Verify that the Lightbox style popup with the picture showing in full size is displayed when clicking on the image of Marketing Material");
		/*
		  	Pre-condition: User has "Add and manage accessories" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
			VP. Verify that 040D Accessory Detail page is displayed
			4. Click "Add New Variant" link in "Model Actions" module
			VP. The 052D Model Variant Edit page is displayed.
			5. Fill valid value into all requires fields
			6. Upload an image for marketing material successfully
			7. Click "Save" link
			VP: The 041D Accessory Variant Detail page is displayed with three size of primary catalog images
			9. Click on above marketing material picture thumbnail
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Select an accessory from accessories table
		String productName = home.selectAnAccessory();
		/*
		 * VP. Verify that 040D Accessory Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), productName);
		// 4. Click "Add New Variant" link in "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. The 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 5. Fill valid value into all requires fields
		// 6. Upload an image for marketing material successfully
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.variantMarketing();
		data.put("add marketing", Constant.IMG_NAME[0]);
		home.addVariant(AddVariant.getHash(), data);
		/*
		 * VP: The 041D Accessory Variant Detail page is displayed with three size of primary catalog images
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.TITLE_NAME), data.get("name"));
		// 8. Click on above marketing material picture thumbnail
		home.click(driver.findElement(By.xpath(VariantInfo.MARKETING_MATERIALS)).findElement(By.tagName("img")));
		home.waitForElementClickable(VariantInfo.LIGHTBOX_STYLE_IMAGE);
		/*
		 * Verify that The Lightbox style popup with the correct picture showing in full size is displayed 
		 */
		Assert.assertTrue(home.isElementPresent(VariantInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(VariantInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(AddVariant.IMG_NAME[0].replaceAll(".jpg", "")));
	}
	
	/*
	 * Verify that marketing material file could be download successfully by clicking on its item
	 */
	@Test
	public void TC041DAVD_07(){
		result.addLog("ID : TC041DAVD_07: Verify that marketing material file could be download successfully by clicking on its item");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			VP. Verify that 040D product Detail page is displayed
			4. Click "Add New Variant" link in "Model Actions" module
			VP. The 052D Model Variant Edit page is displayed.
			5. Fill valid value into all requires fields
			6. Upload a file for marketing material successfully
			7. Click "Save" link
			VP: The 041D product Variant Detail page is displayed 
			8.Click on uploaded marketing material file
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		/*
		 * VP. Verify that 040D product Detail page is displayed
		 */
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 4. Click "Add New Variant" link in "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. The 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 5. Fill valid value into all requires fields
		// 6. Upload a file for marketing material successfully
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.variantMarketing();
		home.addVariant(AddVariant.getHash(), data);
		/*
		 * VP: The 041D product Variant Detail page is displayed 
		 */
		Assert.assertEquals(home.existsElement(VariantInfo.getElementsInfo()).getResult(), "Pass");
		// 8. Click on uploaded marketing material file
		Boolean isFileDownloaded = home.downloadFile(data.get("add marketing"));
		/*
		 * Verify that The marketing material file could be download successfully
		 */
		Assert.assertTrue(isFileDownloaded);
	}
	
	/*
	 * Verify that the “Use Parent’s Tuning” link is not displayed in 041D product Variant Detail page
	 */
	@Test
	public void TC041DAVD_08(){
		result.addLog("ID : TC041DAVD_08: Verify that the “Use Parent’s Tuning” link is not displayed in 041D product Variant Detail page");
		/*
	 		Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			VP. Verify that 040D product Detail page is displayed
			4. Click "Add New Variant" link in "Model Actions" module
			VP. The 052D Model Variant Edit page is displayed.
			5. Fill valid value into all requires fields without ticking on "Use Parent's Tuning" checkbox or upload a custom tuning
			6. Click "Save" link
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Select an accessory from accessories table
		String productName = home.selectAnAccessory();
		/*
		 * VP. Verify that 040D product Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), productName);
		// 4. Click "Add New Variant" link in "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. The 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 5. Fill valid value into all requires fields without ticking on "Use Parent's Tuning" checkbox or upload a custom tuning
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.variantDraft();
		home.addVariant(AddVariant.getHash(), data);
		/*
		 * Verify that the “Use Parent’s Tuning” link is not displayed in 041D Accessory Variant Detail page
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), "USING PARENT'S MODEL");
	}
	
	/*
	 * Verify that the "Partner Actions" link is displayed in Step 1: Tuning Approval of Publishing Process
	 */
	@Test
	public void TC041DAVD_10(){
		result.addLog("ID : TC041DAVD_10: Verify that the 'Partner Actions' link is displayed in Step 1: Tuning Approval of Publishing Process");
		/*
	 		Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			VP. Verify that 040D product Detail page is displayed
			4. Click "Add New Variant" link in "Model Actions" module
			VP. The 052D Model Variant Edit page is displayed.
			5. Fill valid value into all requires fields
			6. Don't use parent's tuning
			7. Click "Save" link
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Select an accessory from accessories table
		String productName = home.selectAnAccessory();
		/*
		 * VP. Verify that 040D Accessory Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), productName);
		// 4. Click "Add New Variant" link in "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. The 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 5. Fill valid value into all requires fields
		// 6. Upload a file for marketing material successfully
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.variantTuning();
		home.addVariant(AddVariant.getHash(), data);
		/*
		 * Verify that The "Partner Actions" link is displayed in Step 1: Tuning Approval of Publishing Process
		 */
		Assert.assertTrue(home.isElementPresent(VariantInfo.PARTNER_ACTIONS_STEP_1));
	}
	
	/*
	 * Verify that the "Partner Actions" link is displayed in Step 2 : Marketing Approval of Publishing Process
	 */
	@Test
	public void TC041DAVD_11(){
		result.addLog("ID : TC041DAVD_11: Verify that the 'Partner Actions' link is displayed in Step 2 : Marketing Approval of Publishing Process");
		/*
	 		Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			VP. Verify that 040D product Detail page is displayed
			4. Click "Add New Variant" link in "Model Actions" module
			VP. The 052D Model Variant Edit page is displayed.
			5. Fill valid value into all requires fields
			6. Upload variant tuning file successfully
			7. Upload a file for marketing material successfully
			8. Click "Save" link
			VP: The 041D product Variant Detail page is displayed 
			9. Go through Step 1: Tuning Approval of Publishing Process
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		/*
		 * VP. Verify that 040D product Detail page is displayed
		 */
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 4. Click "Add New Variant" link in "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. The 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 5. Fill valid value into all requires fields
		// 6. Upload variant tuning file successfully
		// 7. Upload a file for marketing material successfully
		// 8. Click "Save" link
		Hashtable<String,String> data = TestData.variantPublish();
		home.addVariant(AddVariant.getHash(), data);
		/*
		 * VP: The 041D product Variant Detail page is displayed 
		 */
		Assert.assertEquals(home.existsElement(VariantInfo.getElementsInfo()).getResult(), "Pass");
		// 9. Go through Step 1: Tuning Approval of Publishing Process
		home.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		home.click(VariantInfo.APPROVE_TUNING);
		/*
		 * Verify that The "Partner Actions" link is displayed in Step 2: Marketing Approval of Publishing Process
		 */
		Assert.assertTrue(home.isElementPresent(VariantInfo.PARTNER_ACTIONS_STEP_2));
	}
	
	/*
	 * Verify that the "Partner Actions" link is displayed in Step 2 : Marketing Approval of Publishing Process
	 */
	@Test
	public void TC041DAVD_12(){
		result.addLog("ID : TC041DAVD_12: Verify that the 'Partner Actions' link is displayed in Step 2 : Marketing Approval of Publishing Process");
		/*
	 		1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select a published product from Products table
			VP. Verify that 040D product Detail page is displayed
			4. Click "Add New Variant" link in "Model Actions" module
			VP. The 052D Model Variant Edit page is displayed.
			5. Fill valid value into all requires fields
			6. Upload variant tuning file successfully
			7. Upload three size of product catalog images successfully
			8. Upload a file for marketing material successfully
			9. Click "Save" link
			VP: The 041D product Variant Detail page is displayed 
			10. Go through Step 1: Tuning Approval and Step 2: Marketing Approval of Publishing Process successfully
			VP: Verify that the Publish button is enabled in step 3 of Publishing Process
			11. Click “Publish” button
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		String productName = home.selectAnAccessory();
		/*
		 * VP. Verify that 040D product Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), productName);
		// 4. Click "Add New Variant" link in "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. The 052D Model Variant Edit page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		// 5. Fill valid value into all requires fields
		// 6. Upload variant tuning file successfully
		// 7. Upload three size of product catalog images successfully
		// 8. Upload a file for marketing material successfully
		// 9. Click "Save" link
		Hashtable<String,String> data = TestData.variantPublish();
		home.addVariant(AddVariant.getHash(), data);
		/*
		 * VP: The 041D product Variant Detail page is displayed 
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.TITLE_NAME), data.get("name"));
		// 10. Go through Step 1: Tuning Approval and Step 2: Marketing Approval of Publishing Process successfully
		// Approve tuning
		home.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		home.click(VariantInfo.APPROVE_TUNING);
		// Approve marketing
		home.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		home.click(VariantInfo.REQUEST_MARKETING_REVIEW);
		home.click(VariantInfo.APPROVE_MARKETING);
		/*
		 * VP: Verify that the Publish button is enabled in step 3 of Publishing Process
		 */
		Assert.assertEquals(home.getAtribute(VariantInfo.PUBLISH, "class"), "button button-warning");
		// 11. Click “Publish” button
		home.click(VariantInfo.PUBLISH);
		/*
		 * Verify that the product variant is published
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.PUBLISHED_STATUS), "Published");
	}
	
	/*
	 * Verify that the Partner Tuning is unable to approve when the product variant Heaphone:X Tuning is not rated
	 */
	@Test
	public void TC041DAVD_13(){
		result.addLog("ID : TC041DAVD_13: Verify that the Partner Tuning is unable to approve when the product variant Heaphone:X Tuning is not rated");
		/*
			Pre-condition: User has "Approve product tunings" rights.
			1. Log into DTS portal as a Partner user successfully
			2. Navigate to "Products" page
			3. Select a published product from products table
			4. Click “Add New Variant” link
			5. Fill valid value into all required fields
			6. Upload a custom tuning file successfully
			7. Click “Save” link
			VP: Verify that the status of Step 1: Tuning Approval is “UNSUBMITTED” and the “Request Tuning Review” link is displayed
			8. Click “Request Tuning Review” link
			9. Log out DTS portal
			10. Log into DTS portal as DTS user successfully
			11. Navigate to “Products” page
			12. Select above product from products table
			13. Select above variant from variant list
			VP: Verify that there is a notification message “Headphone:X Tuning Rating is required” displayed below “Approve Tuning” link of step 1: Tuning Approval
			14. Click “Approve Tuning” link
		*/
		// Log out
		home.logout();
		// 1. Log into DTS portal as a Partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 2. Navigate to "Products" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select a published product from products table
		home.selectAnAccessory();
		String accessoryName = home.getTextByXpath(AccessoryInfo.TITLE_NAME);
		// 4. Click “Add New Variant” link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 5. Fill valid value into all required fields
		// 6. Upload a custom tuning file successfully
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.variantTuning();
		home.addVariant(AddVariant.getHash(), data);
		/*
		 * VP: Verify that the status of Step 1: Tuning Approval is “UNSUBMITTED” and the “Request Tuning Review” link is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), "UNSUBMITTED");
		Assert.assertTrue(home.isElementPresent(VariantInfo.REQUEST_TUNING_REVIEW));
		// 8. Click “Request Tuning Review” link
		home.click(VariantInfo.REQUEST_TUNING_REVIEW);
		// 9. Log out DTS portal
		home.logout();
		// 10. Log into DTS portal as DTS user successfully
		home.login(superUsername, superUserpassword);
		// 11. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 12. Select above product from products table
		home.selectAnaccessorybyName(accessoryName);
		// 13. Select above variant from variant list
		home.selectAVariantbyName(data.get("name"));
		/*
		 * VP: Verify that there is a notification message “Headphone:X Tuning Rating is required” displayed below “Approve Tuning” link of step 1: Tuning Approval
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_RATING_WARNING_PARTNER), "* Headphone:X Tuning Rating is required.");
		// 14. Click “Approve Tuning” link
		home.click(VariantInfo.APPROVE_TUNING);
		/*
		 * Verify that the DTS user is unable to approve Partner Tuning file
		 */
		Assert.assertNotEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), "APPROVED");
		Assert.assertTrue(home.isElementPresent(VariantInfo.APPROVE_TUNING));
	}
	
	/*
	 * Verify that the DTS Tuning is able to approve without rating tuning when the Heaphone:X Tuning product variant use Parent tuning rating
	 */
	@Test
	public void TC041DAVD_14(){
		result.addLog("ID : TC041DAVD_14: Verify that the DTS Tuning is able to approve without rating tuning when the Heaphone:X Tuning product variant use Parent tuning rating");
		/*
			Pre-condition: User has "Approve product tunings" rights.
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3. Select a published product from products table
			4. Click “Add New Variant” link
			5. Fill valid value into all fields
			6. Upload a custom tuning file successfully
			7. Use parent headphone:X tuning rating
			8. Click “Save” link
			VP: Verify that the status of Step 1: Tuning Approval is “PENDING PARTNER APPROVAL” and the “Partner Actions” link is displayed
			9. Expend the “Partner Actions” link
			VP: Verify that the notification message “Headphone:X Tuning Rating is required” is not displayed below “Approve Tuning” link of step 1: Tuning Approval
			10. Click “Approve Tuning” link
		*/
		
		/*
		 * Pre-condition: Create a publish accessory
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create accessory
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), data);
		/*
		 * *************************************************************
		 */
		// 4. Click “Add New Variant” link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 5. Fill valid value into all fields
		// 6. Upload a custom tuning file successfully
		// 7. Use parent headphone:X tuning rating
		// 8. Click “Save” link
		Hashtable<String,String> dataVariant = TestData.variantTuning();
		dataVariant.remove("tuning rating");
		home.addVariant(AddVariant.getHash(), dataVariant);
		/*
		 * VP: Verify that the status of Step 1: Tuning Approval is “PENDING PARTNER APPROVAL” and the “Partner Actions” link is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), "PENDING PARTNER APPROVAL");
		Assert.assertTrue(home.isElementPresent(VariantInfo.PARTNER_ACTIONS_STEP_1));
		// 9. Expend the “Partner Actions” link
		home.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		/*
		 * VP: Verify that the notification message “Headphone:X Tuning Rating is required” is not displayed below “Approve Tuning” link of step 1: Tuning Approval
		 */
		Assert.assertFalse(home.isElementPresent(VariantInfo.TUNING_RATING_WARNING_DTS));
		// 10. Click “Approve Tuning” link
		home.click(VariantInfo.APPROVE_TUNING);
		/*
		 * Verify that the DTS user is able to approve DTS Tuning file
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), "APPROVED");
		// Delete product
		home.click(VariantInfo.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS Tuning is unable to approve when the product variant's Heaphone:X Tuning  is “Undetermined”
	 */
	@Test
	public void TC041DAVD_15(){
		result.addLog("ID : TC041DAVD_15: Verify that the DTS Tuning is unable to approve when the product variant's Heaphone:X Tuning  is “Undetermined”");
		/*
			Pre-condition: User has "Approve product tunings" rights.
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3. Select a published product from products table
			4. Click “Add New Variant” link
			5. Fill valid value into all fields
			6. Upload a custom tuning file successfully
			7. Change the HeadphoneX: Tuning rating to “Undetermined”
			8. Click “Save” link
			VP: Verify that the status of Step 1: Tuning Approval is “PENDING PARTNER APPROVAL” and the “Partner Actions” link is displayed
			9. Expend the “Partner Actions” link
			VP: Verify that there is a notification message “Headphone:X Tuning Rating is required” displayed below “Approve Tuning” link of step 1: Tuning Approval
			10. Click “Approve Tuning” link
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select a published product from products table
		home.selectAnAccessory();
		// 4. Click “Add New Variant” link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 5. Fill valid value into all fields
		// 6. Upload a custom tuning file successfully
		// 7. Change the HeadphoneX: Tuning rating to “Undetermined”
		// 8. Click “Save” link
		Hashtable<String,String> data = TestData.variantTuning();
		data.put("tuning rating", "Undetermined");
		home.addVariant(AddVariant.getHash(), data);
		/*
		 * VP: Verify that the status of Step 1: Tuning Approval is “PENDING PARTNER APPROVAL” and the “Partner Actions” link is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), "PENDING PARTNER APPROVAL");
		Assert.assertTrue(home.isElementPresent(VariantInfo.PARTNER_ACTIONS_STEP_1));
		// 9. Expend the “Partner Actions” link
		home.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		/*
		 * VP: Verify that there is a notification message “Headphone:X Tuning Rating is required” displayed below “Approve Tuning” link of step 1: Tuning Approval
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_RATING_WARNING_DTS), "* Headphone:X Tuning Rating is required.");
		// 10. Click “Approve Tuning” link
		home.click(VariantInfo.APPROVE_TUNING);
		/*
		 * Verify that the DTS user is unable to approve DTS Tuning file
		 */
		Assert.assertNotEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), "APPROVED");
		Assert.assertTrue(home.isElementPresent(VariantInfo.APPROVE_TUNING));
	}
	
	/*
	 * Verify that the product variant is unable to publish the Headphone:X Tuning Rating is still “Undetermined” in step 3: Publishing
	 */
	@Test
	public void TC041DAVD_16(){
		result.addLog("ID : TC041DAVD_16: Verify that the product variant is unable to publish the Headphone:X Tuning Rating is still “Undetermined” in step 3: Publishing");
		/*
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3.Select a published product from Products table
			VP. Verify that the 051D product Edit Page is displayed
			4. Click “Add New Variant” link
			5. Fill valid value into all fields
			6. Upload a custom tuning file successfully
			7. Leave the Headphone:X Tuning as “Undetermined”
			8. Click “Save” link
			9. Log out DTS portal
			10. Log into DTS portal as a partner user successfully
			11. Navigate to “Products” page
			12. Select above product from Products table
			13. Select above variant from variants list
			14. Click “Approve Tuning” link
			15. Pass through step 2: Marketing Approval 
			VP: Verify that there is a notification message “* Pending DTS Headphone X Rating.” displayed in step 3: Publishing for partner user side and the “PUBLISH” button is disabled.
			16. Log out DTS portal
			17. Log into DTS portal as a DTS user successfully
			18. Navigate to “Products” page
			19. Select above product from Products table.
			20. Select above variant from variants list
		*/
		/*
		 * Pre-condition: Create published product
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create published product
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), data);
		/*
		 * *************************************************
		 */
		// 2. Navigate to "Products" page
		// 3.Select a published product from Products table
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 4. Click “Add New Variant” link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 5. Fill valid value into all fields
		// 6. Upload a custom tuning file successfully
		// 7. Leave the Headphone:X Tuning as “Undetermined”
		// 8. Click “Save” link
		Hashtable<String, String> dataVariant = TestData.variantPublish();
		dataVariant.put("tuning rating", "Undetermined");
		home.addVariant(AddVariant.getHash(), dataVariant);
		// 9. Log out DTS portal
		home.logout();
		// 10. Log into DTS portal as a partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 11. Navigate to “Products” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 12. Select above product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		// 13. Select above variant from variants list
		home.selectAVariantbyName(dataVariant.get("name"));
		// 14. Click “Approve Tuning” link
		home.click(PartnerVariantInfo.APPROVE_TUNING);
		// 15. Pass through step 2: Marketing Approval
		home.click(PartnerVariantInfo.REQUEST_MARKETING_REVIEW);
		// Log out DTS portal
		home.logout();
		// Log in DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Select product above
		home.selectAnaccessorybyName(data.get("name"));
		// Select variant above
		home.selectAVariantbyName(dataVariant.get("name"));
		// Approve marketing
		home.click(VariantInfo.APPROVE_MARKETING);
		// Log out DTS portal
		home.logout();
		// Log in DTS portal as DTS user
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select product above
		home.selectAnaccessorybyName(data.get("name"));
		// Select variant above
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that there is a notification message “* Pending DTS Headphone X Rating.” displayed in step 3: Publishing for partner user side and the “PUBLISH” button is disabled
		 */
		Assert.assertTrue(home.checkMessageDisplay("* Pending DTS Headphone X Rating."));
		Assert.assertEquals(home.getAtribute(AccessoryInfo.PUBLISH, "class"), "button disabled");
		// 16. Log out DTS portal
		home.logout();
		// 17. Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// 18. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 19. Select above product from Products table.
		home.selectAnaccessorybyName(data.get("name"));
		// 20. Select above variant from variants list
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 *  Verify that there is a notification message “* Headphone:X Tuning Rating is required.” displayed in step 3: Publishing for DTS user side and the “PUBLISH” button is disabled
		 */
		Assert.assertTrue(home.checkMessageDisplay("* Headphone:X Tuning Rating is required."));
		Assert.assertEquals(home.getAtribute(AccessoryInfo.PUBLISH, "class"), "button disabled");
		// Delete product
		home.click(VariantInfo.PRODUCT_LINK);
		home.doDelete(PartnerAccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that DTS user can publish product variant after changing “Headphone:X Rating” value in  step 3: Publishing
	 */
	@Test
	public void TC041DAVD_17(){
		result.addLog("ID : TC041DAVD_17: Verify that DTS user can publish product variant after changing “Headphone:X Rating” value in  step 3: Publishing");
		/*
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3. Select a published product from Products table
			VP. Verify that the 051D product Edit Page is displayed
			4. Click “Add New Variant” link
			5. Fill valid value into all required fields
			6. Upload a custom tuning file successfully
			7. Leave the Headphone:X Tuning as “Undetermined”
			8. Click “Save” link
			9. Log out DTS portal
			10. Log into DTS portal as a partner user successfully
			11. Navigate to “Products” page
			12. Select above product from Products table
			13. Select above variant from variants list
			14. Click “Approve Tuning” link
			15. Pass through step 2: Marketing Approval 
			VP: Verify that there is a notification message “* Pending DTS Headphone X Rating.” displayed in step 3: Publishing for partner user side and the “PUBLISH” button is disabled.
			16. Log out DTS portal
			17. Log into DTS portal as a DTS user successfully
			18. Navigate to “Products” page
			19. Select above product from Products table
			20. Select above variant from variants list
			VP:  Verify that there is a notification message “* Headphone:X Tuning Rating is required.” displayed in step 3: Publishing for DTS user side and the “PUBLISH” button is disabled.
			21. Click “Edit” link
			22. Change the Headphone:X Tuning Rating value to another
			23. Click “Save” link
			24. Click “PUBLISH” button
		*/
		/*
		 * Pre-condition: Create published product
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create published product
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), data);
		/*
		 * *************************************************
		 */
		// 2. Navigate to "Products" page
		// 3.Select a published product from Products table
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 4. Click “Add New Variant” link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 5. Fill valid value into all fields
		// 6. Upload a custom tuning file successfully
		// 7. Leave the Headphone:X Tuning as “Undetermined”
		// 8. Click “Save” link
		Hashtable<String, String> dataVariant = TestData.variantPublish();
		dataVariant.put("tuning rating", "Undetermined");
		home.addVariant(AddVariant.getHash(), dataVariant);
		// 9. Log out DTS portal
		home.logout();
		// 10. Log into DTS portal as a partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 11. Navigate to “Products” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 12. Select above product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		// 13. Select above variant from variants list
		home.selectAVariantbyName(dataVariant.get("name"));
		// 14. Click “Approve Tuning” link
		home.click(PartnerVariantInfo.APPROVE_TUNING);
		// 15. Pass through step 2: Marketing Approval
		home.click(PartnerVariantInfo.REQUEST_MARKETING_REVIEW);
		// Log out DTS portal
		home.logout();
		// Log in DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Select product above
		home.selectAnaccessorybyName(data.get("name"));
		// Select variant above
		home.selectAVariantbyName(dataVariant.get("name"));
		// Approve marketing
		home.click(VariantInfo.APPROVE_MARKETING);
		// Log out DTS portal
		home.logout();
		// Log in DTS portal as DTS user
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select product above
		home.selectAnaccessorybyName(data.get("name"));
		// Select variant above
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that there is a notification message “* Pending DTS Headphone X Rating.” displayed in step 3: Publishing for partner user side and the “PUBLISH” button is disabled
		 */
		Assert.assertTrue(home.checkMessageDisplay("* Pending DTS Headphone X Rating."));
		Assert.assertEquals(home.getAtribute(AccessoryInfo.PUBLISH, "class"), "button disabled");
		// 16. Log out DTS portal
		home.logout();
		// 17. Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// 18. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 19. Select above product from Products table.
		home.selectAnaccessorybyName(data.get("name"));
		// 20. Select above variant from variants list
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 *  Verify that there is a notification message “* Headphone:X Tuning Rating is required.” displayed in step 3: Publishing for DTS user side and the “PUBLISH” button is disabled
		 */
		Assert.assertTrue(home.checkMessageDisplay("* Headphone:X Tuning Rating is required."));
		Assert.assertEquals(home.getAtribute(AccessoryInfo.PUBLISH, "class"), "button disabled");
		// 21. Click “Edit” link
		home.click(VariantInfo.EDIT_VARIANT);
		// 22. Change the Headphone:X Tuning Rating value to another
		home.selectOptionByName(AddVariant.TUNING_RATING, "A");
		// 23. Click “Save” link
		home.click(AddVariant.SAVE);
		// 24. Click “PUBLISH” button
		home.click(VariantInfo.PUBLISH);
		/*
		 * Verify that the product is published successfully
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.PUBLISHED_STATUS), "Published");
		// Delete product
		home.click(VariantInfo.PRODUCT_LINK);
		home.doDelete(PartnerAccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that partner user is unable to publish product variant until the DTS user changes “Headphone:X Rating” value in  step 3: Publishing
	 */
	@Test
	public void TC041DAVD_18(){
		result.addLog("ID : TC041DAVD_18: Verify that partner user is unable to publish product variant until the DTS user changes “Headphone:X Rating” value in  step 3: Publishing");
		/*
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3. Select a published product from Products table
			VP. Verify that the 051D product Edit Page is displayed
			4. Click “Add New Variant” link
			5. Fill valid value into all required fields
			6. Upload a custom tuning file successfully
			7. Leave the Headphone:X Tuning as “Undetermined”
			8. Click “Save” link
			9. Log out DTS portal
			10. Log into DTS portal as a partner user successfully
			11. Navigate to “Products” page
			12. Select above product from Products table
			13. Select above variant from variants list
			14. Click “Approve Tuning” link
			15. Pass through step 2: Marketing Approval 
			VP: Verify that there is a notification message “* Pending DTS Headphone X Rating.” displayed in step 3: Publishing for partner user side and the “PUBLISH” button is disabled.
			16. Log out DTS portal
			17. Log into DTS portal as a DTS user successfully
			18. Navigate to “Products” page
			19. Select above product from Products table.
			20. Select above variant from variants list
			VP:  Verify that there is a notification message “* Headphone:X Tuning Rating is required.” displayed in step 3: Publishing for DTS user side and the “PUBLISH” button is disabled.
			21. Click “Edit” link
			22. Change the Headphone:X Tuning Rating value to another
			23. Click “Save” link
			VP: Verfy that the “PUBLISH” button is enabled.
			24. Log out DTS portal
			25. Execute steps from 10 to 13 again
			26. Click “PUBLISH” button
		*/
		/*
		 * Pre-condition: Create published product
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create published product
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), data);
		/*
		 * *************************************************
		 */
		// 2. Navigate to "Products" page
		// 3.Select a published product from Products table
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 4. Click “Add New Variant” link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 5. Fill valid value into all fields
		// 6. Upload a custom tuning file successfully
		// 7. Leave the Headphone:X Tuning as “Undetermined”
		// 8. Click “Save” link
		Hashtable<String, String> dataVariant = TestData.variantPublish();
		dataVariant.put("tuning rating", "Undetermined");
		home.addVariant(AddVariant.getHash(), dataVariant);
		// 9. Log out DTS portal
		home.logout();
		// 10. Log into DTS portal as a partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 11. Navigate to “Products” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 12. Select above product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		// 13. Select above variant from variants list
		home.selectAVariantbyName(dataVariant.get("name"));
		// 14. Click “Approve Tuning” link
		home.click(PartnerVariantInfo.APPROVE_TUNING);
		// 15. Pass through step 2: Marketing Approval
		home.click(PartnerVariantInfo.REQUEST_MARKETING_REVIEW);
		// Log out DTS portal
		home.logout();
		// Log in DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Select product above
		home.selectAnaccessorybyName(data.get("name"));
		// Select variant above
		home.selectAVariantbyName(dataVariant.get("name"));
		// Approve marketing
		home.click(VariantInfo.APPROVE_MARKETING);
		// Log out DTS portal
		home.logout();
		// Log in DTS portal as DTS user
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select product above
		home.selectAnaccessorybyName(data.get("name"));
		// Select variant above
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that there is a notification message “* Pending DTS Headphone X Rating.” displayed in step 3: Publishing for partner user side and the “PUBLISH” button is disabled
		 */
		Assert.assertTrue(home.checkMessageDisplay("* Pending DTS Headphone X Rating."));
		Assert.assertEquals(home.getAtribute(AccessoryInfo.PUBLISH, "class"), "button disabled");
		// 16. Log out DTS portal
		home.logout();
		// 17. Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// 18. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 19. Select above product from Products table.
		home.selectAnaccessorybyName(data.get("name"));
		// 20. Select above variant from variants list
		home.selectAVariantbyName(dataVariant.get("name"));
		/*
		 *  Verify that there is a notification message “* Headphone:X Tuning Rating is required.” displayed in step 3: Publishing for DTS user side and the “PUBLISH” button is disabled
		 */
		Assert.assertTrue(home.checkMessageDisplay("* Headphone:X Tuning Rating is required."));
		Assert.assertEquals(home.getAtribute(AccessoryInfo.PUBLISH, "class"), "button disabled");
		// 21. Click “Edit” link
		home.click(VariantInfo.EDIT_VARIANT);
		// 22. Change the Headphone:X Tuning Rating value to another
		home.selectOptionByName(AddVariant.TUNING_RATING, "A");
		// 23. Click “Save” link
		home.click(AddVariant.SAVE);
		/*
		 * VP: Verify that the “PUBLISH” button is enabled.
		 */
		Assert.assertEquals(home.getAtribute(VariantInfo.PUBLISH, "class"), "button button-warning");
		// 24. Log out DTS portal
		home.logout();
		// 25. Execute steps from 10 to 13 again
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select product above
		home.selectAnaccessorybyName(data.get("name"));
		// Select variant above
		home.selectAVariantbyName(dataVariant.get("name"));
		// 26. Click “PUBLISH” button
		home.click(PartnerVariantInfo.PUBLISH);
		/*
		 * Verify that the product is published successfully
		 */
		Assert.assertEquals(home.getTextByXpath(PartnerVariantInfo.PUBLISHED_STATUS), "Published");
		// Delete product
		home.click(VariantInfo.PRODUCT_LINK);
		home.doDelete(PartnerAccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the "Request Marketing Review" link is disabled when the "EAN/UPC" is empty although the Tuning status is approved
	 */
	@Test
	public void TC041DAVD_19(){
		result.addLog("ID : TC041DAVD_19: Verify that the 'Request Marketing Review' link is disabled when the 'EAN/UPC' is empty although the Tuning status is approved");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select a published product from Products table
			4. Click "Add New Variant" link
			VP. Verify that the 051D product Edit Page is displayed
			5. Fill valid value into all fields but leaves the "EAN/UPC" empty
			6. Upload Tuning and Marketing material successfully
			7. Click "Save" link
			8. Click "Approve Tuning" link from Step 1: Tuning Approval section
		*/
		/*
		 * Pre-condition: Create published product
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create published product
		Hashtable<String,String> dataProduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct);
		/*
		 * *************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select a published product from Products table
		home.selectAnaccessorybyName(dataProduct.get("name"));
		// 4. Click "Add New Variant" link
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		// 5. Fill valid value into all fields but leaves the "EAN/UPC" empty
		// 6. Upload Tuning and Marketing material successfully
		// 7. Click "Save" link
		Hashtable<String,String> dataVariant = TestData.variantPublish();
		dataVariant.remove("ean");
		dataVariant.remove("upc");
		home.addVariant(AddVariant.getHash(), dataVariant);
		// 8. Click "Approve Tuning" link from Step 1: Tuning Approval section
		home.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		home.click(VariantInfo.APPROVE_TUNING);
		/*
		 * Verify that the Tuning Approval status is changed to "APPROVED" and
		 * the "Request Marketing Review" link in step 2: Marketing Approval is
		 * disabled and the error message "*EAN/UPC is required." is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), "APPROVED");
		home.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		home.click(VariantInfo.REQUEST_MARKETING_REVIEW);
		Assert.assertEquals(home.getTextByXpath(VariantInfo.MARKETING_STATUS), "UNSUBMITTED");
		Assert.assertTrue(home.checkMessageDisplay("* EAN/UPC is required."));
		// Delete product
		home.click(VariantInfo.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the “Published Variant Profile” link is displayed when the product variant is published
	 */
	@Test
	public void TC041DAVD_20(){
		result.addLog("ID : TC041DAVD_20: Verify that the “Published Variant Profile” link is displayed when the product variant is published");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select a published product from Products table
			4. Click "Add New Variant" link
			VP. Verify that the 051D product Edit Page is displayed
			5. Fill valid value into all required fields
			6. Upload all necessary file
			7. Click save link
			VP: The 041D product Variant Detail page displays and the “Published Variant Profile” link is not displayed in “Actions” module
			8. Pass through two step of Publishing Process
			9. Click “Publish” button
		*/
		/*
		 * PreCondition: Create new published product
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create published product
		Hashtable<String,String> dataProduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct);
		/*
		 * ***********************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select a published product from Products table
		home.selectAnaccessorybyName(dataProduct.get("name"));
		// 4. Click "Add New Variant" link
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		// 5. Fill valid value into all required fields
		// 6. Upload all necessary file
		// 7. Click save link
		Hashtable<String,String> dataVariant = TestData.variantPublishUseParent();
		home.addVariant(AddVariant.getHash(), dataVariant);
		/*
		 * VP: The 041D product Variant Detail page displays and the “Published Variant Profile” link is not displayed in “Actions” module
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.TITLE_NAME), dataVariant.get("name"));
		Assert.assertFalse(home.isElementPresent(VariantInfo.PUBLISHED_VARIANT_PROFILE_LINK));
		// 8. Pass through two step of Publishing Process
		home.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		home.click(VariantInfo.REQUEST_MARKETING_REVIEW);
		home.click(VariantInfo.APPROVE_MARKETING);
		// 9. Click “Publish” button
		home.click(VariantInfo.PUBLISH);
		/*
		 * Verify that the product variant is published successfully and the “Published Variant Profile” link is displayed in “Actions” module
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.PUBLISHED_STATUS), "Published");
		Assert.assertTrue(home.isElementPresent(VariantInfo.PUBLISHED_VARIANT_PROFILE_LINK));
		// Delete product
		home.click(VariantInfo.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that user is able to download product variant profile successfully when clicking on the “Published Variant Profile” link
	 */
	@Test
	public void TC041DAVD_21(){
		result.addLog("ID : TC041DAVD_21: Verify that user is able to download product variant profile successfully when clicking on the “Published Variant Profile” link.");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select a published product from Products table
			4. Click "Add New Variant" link
			VP. Verify that the 051D product Edit Page is displayed
			5. Fill valid value into all required fields
			6. Upload all necessary file
			7. Click save link
			VP: The 041D product Variant Detail page displays and the “Published Variant Profile” link is not displayed in “Actions” module
			8. Pass through two step of Publishing Process
			9. Click “Publish” button
			VP: The product variant is published successfully and the “Published Variant Profile” link is displayed in “Actions” module
			10. Click on “Published Variant Profile” link
		*/
		/*
		 * PreCondition: Create new published product
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create published product
		Hashtable<String,String> dataProduct = TestData.accessoryPublish();
		home.createAccessoryPublish(AddAccessory.getHash(), dataProduct);
		/*
		 * ***********************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select a published product from Products table
		home.selectAnaccessorybyName(dataProduct.get("name"));
		// 4. Click "Add New Variant" link
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		// 5. Fill valid value into all required fields
		// 6. Upload all necessary file
		// 7. Click save link
		Hashtable<String,String> dataVariant = TestData.variantPublishUseParent();
		home.addVariant(AddVariant.getHash(), dataVariant);
		/*
		 * VP: The 041D product Variant Detail page displays and the “Published Variant Profile” link is not displayed in “Actions” module
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.TITLE_NAME), dataVariant.get("name"));
		Assert.assertFalse(home.isElementPresent(VariantInfo.PUBLISHED_VARIANT_PROFILE_LINK));
		// 8. Pass through two step of Publishing Process
		home.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		home.click(VariantInfo.REQUEST_MARKETING_REVIEW);
		home.click(VariantInfo.APPROVE_MARKETING);
		// 9. Click “Publish” button
		home.click(VariantInfo.PUBLISH);
		/*
		 * VP: The product variant is published successfully and the “Published Variant Profile” link is displayed in “Actions” module
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.PUBLISHED_STATUS), "Published");
		Assert.assertTrue(home.isElementPresent(VariantInfo.PUBLISHED_VARIANT_PROFILE_LINK));
		// 10. Click on “Published Variant Profile” link
		Boolean isDownloaded = home.downloadPublishedAccessoryProfile();
		/*
		 * Verify that the download window popup is displayed and the product variant profile is downloaded successfully
		 */
		Assert.assertTrue(isDownloaded);
		// Delete product
		home.click(VariantInfo.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
}

