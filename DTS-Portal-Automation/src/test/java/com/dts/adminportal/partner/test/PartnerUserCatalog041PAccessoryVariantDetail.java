package com.dts.adminportal.partner.test;

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
import dts.com.adminportal.model.PartnerAddVariant;
import dts.com.adminportal.model.PartnerVariantInfo;
import dts.com.adminportal.model.VariantInfo;
import dts.com.adminportal.model.Xpath;

public class PartnerUserCatalog041PAccessoryVariantDetail extends CreatePage{
	private HomeController home;
	
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
		
	}
	@BeforeMethod
	public void loginBeforeTest() {
		home.login(superpartneruser, superpartnerpassword);
	}
	
	/*
	 * Verify that the correct 041P Accessory Variant Detail page is displayed properly after adding new accessory variant.
	 */
	@Test
	public void TC041PAVD_01(){
		result.addLog("ID : TC041PAVD_01 : Verify that the correct 041P Accessory Variant Detail page is displayed properly after adding new accessory variant");
		/*
		 	Pre-condition: Partner user has "Add and manage Products" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			VP. Verify that 040P product Detail page is displayed
			4. Click "Add New Variant" link in "Model Actions" module
			VP. The 052P Model Variant Edit page is displayed.
			5. Fill valid value into all requires fields
			6. Click "Save" link
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		/*
		 * VP. Verify that 040P product Detail page is displayed
		 */
		Assert.assertEquals(home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 4. Click "Add New Variant" link in "Model Actions" module
		home.click(PartnerAccessoryInfo.ADD_VARIANT);
		/*
		 * VP. The 052P Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(PartnerAddVariant.getElementsInfo()).getResult(), "Pass");
		// 5. Fill valid value into all requires fields
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.variantDraft();
		home.addVariant(PartnerAddVariant.getHash(), data);
		/*
		 * Verify that The correct 041P product Variant Detail page is displayed
		 */
		Assert.assertEquals(home.existsElement(PartnerVariantInfo.getElementsInfo()).getResult(), "Pass");
	}
	
	/*
	 * Verify that the correct 041P Accessory Variant Detail page is displayed properly when selecting from Model Variant list.
	 */
	@Test
	public void TC041PAVD_02(){
		result.addLog("ID : TC041DAVD_02: Verify that the correct 041D Accessory Variant Detail page is displayed properly when selecting from Model Variant list");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights. The product has at least one variant.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			VP. Verify that 040P product Detail page is displayed
			4. Select an product variant in Model Variant module
		 */
		// 2. Navigate to "Accessories" page 
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table 
		home.selectAnAccessory();
		/*
		 * VP. Verify that 040P Accessory Detail page is displayed 
		 */
		Assert.assertEquals(home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// Click on Add Variant link
		home.click(PartnerAccessoryInfo.ADD_VARIANT);
		// Create variant
		Hashtable<String,String> data = TestData.variantDraft();
		home.addVariant(PartnerAddVariant.getHash(), data);
		// Go back to the accessory
		home.click(PartnerVariantInfo.PRODUCT_LINK);
		// 4. Select an accessory variant in Model Variant module
		home.selectAVariantbyName(data.get("name"));
		/*
		 * Verify that The correct 041P Accessory Variant Detail page is displayed properly
		 */
		Assert.assertEquals(home.existsElement(PartnerVariantInfo.getElementsInfo()).getResult(), "Pass");
	}
	
	/*
	 * Verify that the Publishing Process and Model Variant Actions modules are displayed in 041P Accessory Variant Details page.
	 */
	@Test
	public void TC041PAVD_03(){
		result.addLog("ID : Verify that the Publishing Process and Model Variant Actions modules are displayed in 041P Accessory Variant Details page.");
		/*
		  	Pre-condition: Partner user has "Add and manage Products" rights. The product has at least one variant.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			VP. Verify that 040P product Detail page is displayed
			4. Select an product variant in Model Variant module
		 */
		// 2. Navigate to "Accessories" page 
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		/*
		 * VP. Verify that 040P Accessory Detail page is displayed
		 */
		Assert.assertEquals(home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// Click on Add Variant link
		home.click(PartnerAccessoryInfo.ADD_VARIANT);
		// Create variant
		Hashtable<String, String> data = TestData.variantDraft();
		home.addVariant(PartnerAddVariant.getHash(), data);
		// Go back to the accessory
		home.click(PartnerVariantInfo.PRODUCT_LINK);
		// 4. Select an accessory variant in Model Variant module
		home.selectAVariantbyName(data.get("name"));
		/*
		 * Verify that The Publishing Process and Model Variant Actions modules
		 * are displayed in 041P product Variant Details page
		 */
		Assert.assertTrue(home.isElementPresent(PartnerVariantInfo.PUBLISHING_PROCESS_CONTAINER));
		Assert.assertTrue(home.isElementPresent(PartnerVariantInfo.ACTION_CONTAINER));
	}
	
	/*
	 * Verify that the Primary Catalog Image section is displayed in order from left to right.
	 */
	@Test
	public void TC041PAVD_04(){
		result.addLog("ID : TC041PAVD_04: Verify that the Primary Catalog Image section is displayed in order from left to right.");
		/*
		    1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
			VP. Verify that 040P Accessory Detail page is displayed
			4. Click "Add New Variant" link in "Model Actions" module
			VP. The 052P Model Variant Edit page is displayed
			5. Fill valid value into all requires fields
			6. Upload three size for custom primary  catalog images
			7. Click "Save" link
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		/*
		 * VP. Verify that 040P Accessory Detail page is displayed
		 */
		Assert.assertEquals(home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 4. Click "Add New Variant" link in "Model Actions" module
		home.click(PartnerAccessoryInfo.ADD_VARIANT);
		/*
		 * VP. The 052P Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(PartnerAddVariant.getElementsInfo()).getResult(), "Pass");
		// 5. Fill valid value into all requires fields
		// 6. Upload three size for custom primary  catalog images
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.variantImage();
		home.addVariant(PartnerAddVariant.getHash(), data);
		/*
		 * Verify that The 040P Accessory Variant Details page is displayed and the primary catalog image displays in order from left to right: 160x160,290x290 and 664x664
		 */
		Assert.assertEquals(home.existsElement(PartnerVariantInfo.getElementsInfo()).getResult(), "Pass");
		Assert.assertTrue(home.isImageDisplayLeftToRight(PartnerVariantInfo.IMAGE_TABLE));
	}
	
	 /*
	  * Verify that the Lightbox style popup with the picture showing in full size is displayed when clicking on the primay catalog images.  
	  */
	@Test
	public void TC041PAVD_05(){
		result.addLog("ID : TC041PAVD_05: Verify that the Lightbox style popup with the picture showing in full size is displayed when clicking on the primay catalog images.");
		/*
		PrePre-condition: Partner user has "Add and manage Products" rights.
		
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Select an product from Products table
		VP. Verify that 040P product Detail page is displayed
		4. Click "Add New Variant" link in "Model Actions" module
		VP. The 052P Model Variant Edit page is displayed.
		5. Fill valid value into all requires fields
		6. Upload three size for custom primary  catalog images
		7. Click "Save" link
		VP: The 041P product Variant Detail page is displayed with three size of primary catalog images
		8. Click on picture of each primary catalog image
		 */
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		// Click Add Accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		Hashtable<String, String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		//VP: The 040P product Detail page is displayed with three size of primary catalog images
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
		//click Add new variant
		home.click(AccessoryInfo.ADD_VARIANT);
		Hashtable<String,String> datavariant=TestData.variantImage();
		home.addVariant(AddVariant.getHash(), datavariant);
		Assert.assertEquals(home.getTextByXpath(VariantInfo.DISPLAYNAME_DEFAULT), datavariant.get("name"));
		// Click on picture of each primary catalog image
		// 250x250
		home.click(VariantInfo.CATALOG_IMAGE_THUMBNAIL_250);
		home.waitForElementClickable(VariantInfo.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(VariantInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(VariantInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[0].replaceAll(".jpg", "")));
		home.click(VariantInfo.LIGHTBOX_CLOSE);
		//500x500
		home.click(VariantInfo.CATALOG_IMAGE_THUMBNAIL_500);
		home.waitForElementClickable(VariantInfo.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(VariantInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(VariantInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[1].replaceAll(".jpg", "")));
		home.click(VariantInfo.LIGHTBOX_CLOSE);
		//1000x1000
		home.click(VariantInfo.CATALOG_IMAGE_THUMBNAIL_1000);
		home.waitForElementClickable(VariantInfo.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(VariantInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(VariantInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[2].replaceAll(".jpg", "")));
		home.click(VariantInfo.LIGHTBOX_CLOSE);
		
		// Delete accessory
		home.click(VariantInfo.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the Lightbox style popup with the picture showing in full size is displayed when clicking on the image of Marketing Material
	 */
	@Test
	public void TC041PAVD_06(){
		result.addLog("ID : TC041PAVD_06: Verify that the Lightbox style popup with the picture showing in full size is displayed when clicking on the image of Marketing Material");
		/*
		  	Pre-condition: Partner user has "Add and manage accessories" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
			VP. Verify that 040P Accessory Detail page is displayed
			4. Click "Add New Variant" link in "Model Actions" module
			VP. The 052P Model Variant Edit page is displayed.
			5. Fill valid value into all requires fields
			6. Upload an image for marketing material successfully
			7. Click "Save" link
			VP: The 041P Accessory Variant Detail page is displayed with three size of primary catalog images
			8. Click on above marketing material picture thumbnail
		 */
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		/*
		 * VP. Verify that 040P Accessory Detail page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		// 4. Click "Add New Variant" link in "Model Actions" module
		home.click(PartnerAccessoryInfo.ADD_VARIANT);
		/*
		 * VP. The 052P Model Variant Edit page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		// 5. Fill valid value into all requires fields
		// 6. Upload an image for marketing material successfully
		// 7. Click "Save" link
		/*
		 * Init data
		 */
		Hashtable<String,String> data = TestData.variantDraft();
		data.put("add marketing", Constant.IMG_NAME[0]);
		home.addVariant(PartnerAddVariant.getHash(), data);
		/*
		 * VP: The 041P Accessory Variant Detail page is displayed with three size of primary catalog images
		 */
		Assert.assertTrue(home.isElementPresent(VariantInfo.TITLE_NAME));
		// 8. Click on above marketing material picture thumbnail
		home.click(driver.findElement(By.xpath(PartnerVariantInfo.MARKETING_MATERIALS)).findElement(By.tagName("img")));
		home.waitForElementClickable(PartnerVariantInfo.LIGHTBOX_STYLE_IMAGE);
		/*
		 * Verify that The Lightbox style popup with the correct picture showing in full size is displayed 
		 */
		Assert.assertTrue(home.isElementPresent(PartnerVariantInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(PartnerVariantInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(PartnerAddVariant.IMG_NAME[0].replaceAll(".jpg", "")));
	}
	
	/*
	 * Verify that marketing material file could be download successfully by clicking on its item.
	 */
	@Test
	public void TC041PAVD_07(){
		result.addLog("ID : Verify that marketing material file could be download successfully by clicking on its item.");
		/*
		    1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
			VP. Verify that 040P Accessory Detail page is displayed
			4. Click "Add New Variant" link in "Model Actions" module
			VP. The 052P Model Variant Edit page is displayed.
			5. Fill valid value into all requires fields
			6. Upload a file for marketing material successfully
			7. Click "Save" link
			VP: The 041P Accessory Variant Detail page is displayed 
			8.Click on uploaded marketing material file
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		/*
		 * VP. Verify that 040P Accessory Detail page is displayed
		 */
		Assert.assertEquals(home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 4. Click "Add New Variant" link in "Model Actions" module
		home.click(PartnerAccessoryInfo.ADD_VARIANT);
		/*
		 * VP. The 052P Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(PartnerAddVariant.getElementsInfo()).getResult(), "Pass");
		// 5. Fill valid value into all requires fields
		// 6. Upload a file for marketing material successfully
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.variantMarketing();
		home.addVariant(PartnerAddVariant.getHash(), data);
		/*
		 * VP: The 041P Accessory Variant Detail page is displayed 
		 */
		Assert.assertEquals(home.existsElement(PartnerVariantInfo.getElementsInfo()).getResult(), "Pass");
		// 8.Click on uploaded marketing material file
		Boolean isFileDownloaded = home.downloadFile(data.get("add marketing"));
		/*
		 * Verify that The marketing material file could be download successfully
		 */
		Assert.assertTrue(isFileDownloaded);
	}
	

	/*
	 Verify that the accessory variant could be published successfully. 
	 */
	@Test
	public void TC041PAVD_09(){
		result.addLog("ID : TC041PAVD_09 : Verify that the accessory variant could be published successfully.");
		/*
		1. Log into DTS portal as a Partner user
		2. Navigate to "Accessories" page
		3. Select a published accessory from accessories table
		VP. Verify that 040D Accessory Detail page is displayed
		4. Click "Add New Variant" link in "Model Actions" module
		VP. The 052D Model Variant Edit page is displayed.
		5. Fill valid value into all requires fields
		6. Upload variant tuning file successfully
		7. Upload three size of product catalog images successfully
		8. Upload a file for marketing material successfully
		9. Click "Save" link
		VP: The 041D Accessory Variant Detail page is displayed 
		10. Go through Step 1: Tuning Approval and Step 2: Marketing Approval of Publishing Process successfully
		VP: Verify that the Publish button is enabled in step 3 of Publishing Process
		11. Click “Publish” button
		*/
		/*
		 * Create a published accessory 
		 */
		// Log out DTS portal 
		home.logout();
		// Log into DTS portal as a Partner user
		home.login(superUsername, superUserpassword);
		// Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// Select a published accessory from accessories table
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create accessory
		Hashtable<String,String> data = TestData.accessoryPublish();
		String AccessoryName = data.get("name");
		home.createAccessoryPublish(AddAccessory.getHash(), data);
		// Logout user
		home.logout();
		// 1. Log into DTS portal as a Partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 2. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select a published accessory from accessories table
		home.selectAnaccessorybyName(AccessoryName);
		/*
		 * VP. Verify that 040D Accessory Detail page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		// 4. Click "Add New Variant" link in "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. The 052D Model Variant Edit page is displayed.
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));	
		// 5. Fill valid value into all requires fields
		// 6. Upload variant tuning file successfully
		// 7. Upload three size of product catalog images successfully
		// 8. Upload a file for marketing material successfully
		// 9. Click "Save" link
		Hashtable<String, String> dataVariant = TestData.variantPublish();
		String variant_name = dataVariant.get("name");
		home.addVariant(AddVariant.getHash(), dataVariant);
		/*
		 * VP: The 041D Accessory Variant Detail page is displayed 
		 */
		Assert.assertTrue(home.isElementPresent(VariantInfo.TITLE_NAME));	
		// 10. Go through Step 1: Tuning Approval and Step 2: Marketing Approval of Publishing Process successfully
		// Click Request Tuning link
		home.click(VariantInfo.REQUEST_TUNING_REVIEW);
		// Log out DTS portal 
		home.logout();
		// Log into DTS portal as a Partner user
		home.login(superUsername, superUserpassword);
		// Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		//Select a published accessory from accessories table
		home.selectAnaccessorybyName(AccessoryName);
		//Select a variant above
		home.selectAVariantbyName(variant_name);
		//change the tuning rating
		home.click(VariantInfo.EDIT_VARIANT);
		home.selectOptionByName(AddVariant.TUNING_RATING, AddVariant.tuningRatingOption[2]);
		home.click(AddVariant.SAVE);
		//Tuning Approval and Marketing Approval
		home.click(AccessoryInfo.APPROVE_TUNING);
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		home.click(AccessoryInfo.APPROVE_MARKETING);

		/*
		 * VP: Verify that the Publish button is enabled in step 3 of Publishing Process
		 */
		boolean button_enabled = home.getAtribute(AccessoryInfo.PUBLISH, "class").contains("button button-warning");
		Assert.assertTrue(button_enabled);
		//Log out DTS portal 
		home.logout();
		// Log into DTS portal as a Partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select a published accessory from accessories table
		home.selectAnaccessorybyName(AccessoryName);
		home.selectAVariantbyName(variant_name);
		//11. Click “Publish” button
		/*
		 * VP: Verify that the Publish button is enabled in step 3 of Publishing Process
		 */
		boolean button_enabled1 = home.getAtribute(AccessoryInfo.PUBLISH, "class").contains("button button-warning");
		Assert.assertTrue(button_enabled1);
		
		home.click(AccessoryInfo.PUBLISH);
		//Verify that the accessory variant is published.
		Assert.assertEquals(home.getTextByXpath(VariantInfo.PUBLISHED_STATUS), VariantInfo.PUBLISHED_STRING);
		
		//Teardown: Delete product
		home.click(VariantInfo.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 Verify that the “Published Variant Profile” link is displayed when the product variant is published. 
	 */
	@Test
	public void TC041PAVD_11(){
		result.addLog("ID : TC041PAVD_11 : Verify that the “Published Variant Profile” link is displayed when the product variant is published.");
		/*
		Pre-condition: User has "Add and manage Products" rights.

		1. Log into DTS portal as a Partner user
		2. Navigate to "Products" page
		3. Select a published product from Products table
		4. Click "Add New Variant" link
		VP. Verify that the 052P Model Variant Edit Page is displayed
		5. Fill valid value into all required fields
		6. Upload all necessary file
		7. Click save link
		VP: The 041P product Variant Detail page displays and the “Published Variant Profile” link is not displayed in “Actions” module
		8. Pass through two step of Publishing Process
		9. Click “Publish” button
		Verify that the product variant is published successfully and the “Published Variant Profile” link is displayed in “Actions” module
		*/
		/*
		 * Create a published accessory 
		 */
		// Log out DTS portal 
		home.logout();
		// Log into DTS portal as a Partner user
		home.login(superUsername, superUserpassword);
		// Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// Select a published accessory from accessories table
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create accessory
		Hashtable<String,String> data = TestData.accessoryPublish();
		String AccessoryName = data.get("name");
		home.createAccessoryPublish(AddAccessory.getHash(), data);
		// Logout user
		home.logout();
		// 1. Log into DTS portal as a Partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 2. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select a published accessory from accessories table
		home.selectAnaccessorybyName(AccessoryName);
		/*
		 * VP. Verify that 040D Accessory Detail page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		// 4. Click "Add New Variant" link in "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. The 052D Model Variant Edit page is displayed.
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));	
		// 5. Fill valid value into all requires fields
		// 6. Upload variant tuning file successfully
		// 7. Upload three size of product catalog images successfully
		// 8. Upload a file for marketing material successfully
		// 9. Click "Save" link
		Hashtable<String, String> dataVariant = TestData.variantPublish();
		String variant_name = dataVariant.get("name");
		home.addVariant(AddVariant.getHash(), dataVariant);
		/*
		 * VP: The 041D Accessory Variant Detail page is displayed 
		 */
		Assert.assertTrue(home.isElementPresent(VariantInfo.TITLE_NAME));	
		// 10. Go through Step 1: Tuning Approval and Step 2: Marketing Approval of Publishing Process successfully
		// Click Request Tuning link
		home.click(VariantInfo.REQUEST_TUNING_REVIEW);
		// Log out DTS portal 
		home.logout();
		// Log into DTS portal as a Partner user
		home.login(superUsername, superUserpassword);
		// Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		//Select a published accessory from accessories table
		home.selectAnaccessorybyName(AccessoryName);
		//Select a variant above
		home.selectAVariantbyName(variant_name);
		//change the tuning rating
		home.click(VariantInfo.EDIT_VARIANT);
		home.selectOptionByName(AddVariant.TUNING_RATING, AddVariant.tuningRatingOption[2]);
		home.click(AddVariant.SAVE);
		//Tuning Approval and Marketing Approval
		home.click(AccessoryInfo.APPROVE_TUNING);
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		home.click(AccessoryInfo.APPROVE_MARKETING);
		//Log out DTS portal 
		home.logout();
		// Log into DTS portal as a Partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select a published accessory from accessories table
		home.selectAnaccessorybyName(AccessoryName);
		home.selectAVariantbyName(variant_name);
		//11. Click “Publish” button
		/*
		 *VP: The 041P product Variant Detail page displays and the “Published Variant Profile” link is not displayed in “Actions” module
		 */
		Assert.assertFalse(home.isElementPresent(VariantInfo.PUBLISHED_VARIANT_PROFILE_LINK));
		home.click(AccessoryInfo.PUBLISH);
		//Verify that the accessory variant is published.
		Assert.assertEquals(home.getTextByXpath(VariantInfo.PUBLISHED_STATUS), VariantInfo.PUBLISHED_STRING);
		Assert.assertTrue(home.isElementPresent(VariantInfo.PUBLISHED_VARIANT_PROFILE_LINK));
		//Teardown: Delete product
		home.click(VariantInfo.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 Verify that user is able to download product variant profile successfully when clicking on the “Published Variant Profile” link. 
	 */
	@Test
	public void TC041PAVD_12(){
		result.addLog("ID : TC041PAVD_12 : Verify that user is able to download product variant profile successfully when clicking on the “Published Variant Profile” link.");
		/*
		Pre-condition: User has "Add and manage Products" rights.

		1. Log into DTS portal as a Partner user
		2. Navigate to "Products" page
		3. Select a published product from Products table
		4. Click "Add New Variant" link
		VP. Verify that the 052P Model Variant Edit Page is displayed
		5. Fill valid value into all required fields
		6. Upload all necessary file
		7. Click save link
		VP: The 041P Product Variant Detail page displays and the “Published Variant Profile” link is not displayed in “Actions” module
		8. Pass through two step of Publishing Process
		9. Click “Publish” button
		VP: The product variant is published successfully and the “Published Variant Profile” link is displayed in “Actions” module
		10. Click on “Published Variant Profile” link.
		*/
		/*
		 * Create a published accessory 
		 */
		// Log out DTS portal 
		home.logout();
		// Log into DTS portal as a Partner user
		home.login(superUsername, superUserpassword);
		// Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// Select a published accessory from accessories table
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create accessory
		Hashtable<String,String> data = TestData.accessoryPublish();
		String AccessoryName = data.get("name");
		home.createAccessoryPublish(AddAccessory.getHash(), data);
		// Logout user
		home.logout();
		// 1. Log into DTS portal as a Partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 2. Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select a published accessory from accessories table
		home.selectAnaccessorybyName(AccessoryName);
		/*
		 * VP. Verify that 040D Accessory Detail page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		// 4. Click "Add New Variant" link in "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. The 052D Model Variant Edit page is displayed.
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));	
		// 5. Fill valid value into all requires fields
		// 6. Upload variant tuning file successfully
		// 7. Upload three size of product catalog images successfully
		// 8. Upload a file for marketing material successfully
		// 9. Click "Save" link
		Hashtable<String, String> dataVariant = TestData.variantPublish();
		String variant_name = dataVariant.get("name");
		home.addVariant(AddVariant.getHash(), dataVariant);
		/*
		 * VP: The 041D Accessory Variant Detail page is displayed 
		 */
		Assert.assertTrue(home.isElementPresent(VariantInfo.TITLE_NAME));	
		// 10. Go through Step 1: Tuning Approval and Step 2: Marketing Approval of Publishing Process successfully
		// Click Request Tuning link
		home.click(VariantInfo.REQUEST_TUNING_REVIEW);
		// Log out DTS portal 
		home.logout();
		// Log into DTS portal as a Partner user
		home.login(superUsername, superUserpassword);
		// Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		//Select a published accessory from accessories table
		home.selectAnaccessorybyName(AccessoryName);
		//Select a variant above
		home.selectAVariantbyName(variant_name);
		//change the tuning rating
		home.click(VariantInfo.EDIT_VARIANT);
		home.selectOptionByName(AddVariant.TUNING_RATING, AddVariant.tuningRatingOption[2]);
		home.click(AddVariant.SAVE);
		//Tuning Approval and Marketing Approval
		home.click(AccessoryInfo.APPROVE_TUNING);
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		home.click(AccessoryInfo.APPROVE_MARKETING);
		//Log out DTS portal 
		home.logout();
		// Log into DTS portal as a Partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to “Accessories” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select a published accessory from accessories table
		home.selectAnaccessorybyName(AccessoryName);
		home.selectAVariantbyName(variant_name);
		//11. Click “Publish” button
		home.click(AccessoryInfo.PUBLISH);
		//Verify that the accessory variant is published.
		Assert.assertEquals(home.getTextByXpath(VariantInfo.PUBLISHED_STATUS), VariantInfo.PUBLISHED_STRING);
		Assert.assertTrue(home.isElementPresent(VariantInfo.PUBLISHED_VARIANT_PROFILE_LINK));
		Assert.assertTrue(home.downloadPublishedAccessoryProfile());
		//Teardown: Delete product
		home.click(VariantInfo.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
}
