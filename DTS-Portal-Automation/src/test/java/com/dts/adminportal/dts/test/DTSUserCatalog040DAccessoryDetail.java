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
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.PartnerAccessoryInfo;
import dts.com.adminportal.model.PartnerAddAccessory;
import dts.com.adminportal.model.Xpath;

public class DTSUserCatalog040DAccessoryDetail extends CreatePage{
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
	 * Verify that the correct 040D product Detail page is displayed when selecting an product from Products table
	 */
	@Test
	public void TC040DAD_01(){
		result.addLog("ID : TC040DAD_01: Verify that the correct 040D product Detail page is displayed when selecting an product from Products table");
		/*
		 	Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		/*
		 * Verify that The 040D Products Details Page of selected product is displayed
		 */
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
	}
	
	/*
	 * Verify that the correct 040D Accessory Detail page is displayed after adding new accessory.
	 */
	@Test
	public void TC040DAD_02(){
		result.addLog("ID : TC040DAD_02: Verify that the correct 040D product Detail page is displayed after adding new product");
		/*
		 	Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			VP. Verify that the 051D product Edit Page is displayed
			4. Fill valid value into all required fields.
			5. Click "Save" link
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(), "Pass");
		// 4. Fill valid value into all required fields
		// 5. Click "Save" link
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * Verify that The 040D Products Details Page of new product is displayed
		 */
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
	}
	
	/*
	 * Verify that the EAN/UPC displays as "(None)" when its value is empty
	 */
	@Test
	public void TC040DAD_03(){
		result.addLog("ID : TC040DAD_03: Verify that the EAN/UPC displays as '(None)' when its value is empty");
		/*
		 	Pre-condition: User has "Add and manage products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "products" page
			3. Click "Add product" link
			VP. Verify that the 051D product Edit Page is displayed
			4. Fill valid value into all fields but leaves the "EAN/UPC" empty
			5. Click "Save" link
		 */
		// 2. Navigate to "products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(), "Pass");
		// 4. Fill valid value into all fields but leaves the "EAN/UPC" empty
		// 5. Click "Save" link
		Hashtable<String,String> data = TestData.accessoryDraft();
		data.remove("ean");
		data.remove("upc");
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * Verify that The 040D products Details Page of new product is displayed and the "EAN/UPC" section displays "EAN/UPC:(None)"
		 */
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.EAN), "(None)");
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.UPC), "(None)");
	}
	
	/*
	 * Verify that the Publishing Process, Model Actions and Model Variant module are displayed in 040D Accessory Details page
	 */
	@Test
	public void TC040DAD_04(){
		result.addLog("ID : TC040DAD_04: Verify that the Publishing Process, Model Actions and Model Variant module are displayed in 040D Accessory Details page");
		/*
		 	Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		/*
		 * The Publishing Process, 
		 * Model Actions and 
		 * Model Variant module is displayed in 040D Accessory Details page.
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.PUBLISHING_PROCESS));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.MODEL_ACTIONS));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.MODEL_VARIANTS));
	}
	
	/*
	 * Verify that the "Request Marketing Review" link is disabled when the "EAN/UPC" is empty although the Tuning status is approved
	 */
	@Test
	public void TC040DAD_05(){
		result.addLog("ID : TC040DAD_05: Verify that the 'Request Marketing Review' link is disabled when the 'EAN/UPC' is empty although the Tuning status is approved");
		/*
		 	Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			VP. Verify that the 051D product Edit Page is displayed
			4. Fill valid value into all fields but leaves the "EAN/UPC" empty
			5. Upload Tuning and Marketing material successfully
			6. Click "Save" link
			VP: Verify that the 040D Products Details page of new product is displayed
			VP: Verify that the Tuning approval status is changed to "Pending Partner Approval"
			7. Click "Approve Tuning" link from Step 1: Tuning Approval section
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(), "Pass");
		// 4. Fill valid value into all fields but leaves the "EAN/UPC" empty
		// 5. Upload Tuning and Marketing material successfully
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.accessoryPublish();
		data.remove("ean");
		data.remove("upc");
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: Verify that the 040D Products Details page of new product is displayed
		 */
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		/*
		 * VP: Verify that the Tuning approval status is changed to "Pending Partner Approval"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "PENDING PARTNER APPROVAL");
		// 7. Click "Approve Tuning" link from Step 1: Tuning Approval section
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * Verify that the Tuning Approval status is changed to "APPROVED" and the "Request Marketing Review" link in step 2: Marketing Approval is disabled and the error message "*EAN/UPC is required." is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "APPROVED");
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		Assert.assertEquals(home.getAtribute(AccessoryInfo.REQUEST_MARKETING_REVIEW, "action"), "");
		Assert.assertTrue(home.checkMessageDisplay("* EAN/UPC is required."));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the Primary Catalog Image section is displayed in ordered from left to right.
	 */
	@Test
	public void TC040DAD_06(){
		result.addLog("ID : TC040DAD_06: Verify that the Primary Catalog Image section is displayed in ordered from left to right.");
		/*
		 	Pre-condition: User has "Add and manage accessories" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
		 */
		/*
		 * PreCondition: Create new product
		 */
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> data = TestData.accessoryImage();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * *************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Select an accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * Verify that The 040D Accessory Details page is displayed and the primary catalog image dislays in order from left to right: 160x160,290x290 and 664x664
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), data.get("name"));
		Assert.assertTrue(home.isImageDisplayLeftToRight(AccessoryInfo.IMAGE_CATALOG));
		// Delete accessory
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the Lightbox style popup with the picture showing in full size is displayed when clicking on the primary catalog images
	 */
	@Test
	public void TC040DAD_07(){
		result.addLog("ID : TC040DAD_07: Verify that the Lightbox style popup with the picture showing in full size is displayed when clicking on the primary catalog images");
		/*
		 	Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			VP. Verify that the 051D product Edit Page is displayed
			4. Fill valid value into all fields
			5. Upload all three size of primary catalog images successfully
			6. Click "Save" link
			VP: The 040D product Detail page is displayed with three size of primary catalog images
			7. Click on picture of each primary catalog image
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddAccessory.TITLE));
		// 4. Fill valid value into all fields
		// 5. Upload all three size of primary catalog images successfully
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.accessoryImage();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// VP: The 040D product Detail page is displayed with three size of primary catalog images
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), data.get("name"));
		// 7. Click on image thumbnail 250
		home.click(AccessoryInfo.CATALOG_IMAGE_THUMBNAIL_250);
		home.waitForElementClickable(AccessoryInfo.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AccessoryInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[0].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(AccessoryInfo.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(AccessoryInfo.LIGHTBOX_STYLE_IMAGE);
		// Click on image thumbnail 500
		home.click(AccessoryInfo.CATALOG_IMAGE_THUMBNAIL_500);
		home.waitForElementClickable(AccessoryInfo.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AccessoryInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[1].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(AccessoryInfo.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(AccessoryInfo.LIGHTBOX_STYLE_IMAGE);
		// Click on image thumbnail 500
		home.click(AccessoryInfo.CATALOG_IMAGE_THUMBNAIL_1000);
		home.waitForElementClickable(AccessoryInfo.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AccessoryInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[2].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(AccessoryInfo.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(AccessoryInfo.LIGHTBOX_STYLE_IMAGE);
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	 /*
	  * Verify that the Lightbox style popup with the picture showing in full size is displayed when clicking on the image of Marketing Material
	  */
	@Test
	public void TC040DAD_08(){
		result.addLog("ID : TC040DAD_08: Verify that the Lightbox style popup with the picture showing in full size is displayed when clicking on the image of Marketing Material");
		/*
			Pre-condition: User has "Add and manage accessories" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			VP. Verify that the 051D Accessory Edit Page is displayed
			4. Fill valid value into all fields
			5. Upload an image for marketing material successfully
			6. Click "Save" link
			VP: The 040D Accessory Detail page is displayed
			7. Click on above picture thumbnail
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP. Verify that the 051D Accessory Edit Page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(), "Pass");
		// 4. Fill valid value into all fields
		// 5. Upload an image for marketing material successfully
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.accessoryMarketing();
		data.put("add marketing", Constant.IMG_NAME[0]);
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: The 040D Accessory Detail page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), data.get("name"));
		// 7. Click on above picture thumbnail
		home.click(driver.findElement(By.xpath(AccessoryInfo.MARKETING_MATERIALS)).findElement(By.tagName("img")));
		home.waitForElementClickable(AccessoryInfo.LIGHTBOX_STYLE_IMAGE);
		/*
		 * Verify that The Lightbox style popup with the correct picture showing in full size is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AccessoryInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[0].replaceAll(".jpg", "")));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that marketing material file could be download successfully by clicking on its item
	 */
	@Test
	public void TC040DAD_09(){
		result.addLog("ID : TC040DAD_09: Verify that marketing material file could be download successfully by clicking on its item");
		/*
		 	Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			VP. Verify that the 051D product Edit Page is displayed
			4. Fill valid value into all fields
			5. Upload a file for marketing material successfully
			6. Click "Save" link
			VP: The 040D product Detail page is displayed
			7. Click on uploaded marketing material file
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(), "Pass");
		// 4. Fill valid value into all fields
		// 5. Upload a file for marketing material successfully
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.accessoryMarketing();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: The 040D product Detail page is displayed
		 */
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 7. Click on uploaded marketing material file
		Boolean isFileDownloaded = home.downloadFile(data.get("add marketing"));
		/*
		 * Verify that The marketing material file could be download successfully
		 */
		Assert.assertTrue(isFileDownloaded);
		// Delete accessory
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the "Partner Actions" link is displayed in Step 1: Tuning Approval of Publishing Process
	 */
	@Test
	public void TC040DAD_10(){
		result.addLog("ID : TC040DAD_10: Verify that the 'Partner Actions' link is displayed in Step 1: Tuning Approval of Publishing Process");
		/*
		 	Pre-condition: User has "Add and manage accessories" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			VP. Verify that the 051D Accessory Edit Page is displayed
			4. Fill valid value into all fields
			5. Upload a  tuning file successfully
			6. Click "Save" link
			VP: The 040D Accessory Detail page is displayed
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP. Verify that the 051D Accessory Edit Page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(),"Pass");
		// 4. Fill valid value into all fields
		// 5. Upload a tuning file successfully
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: The 040D Accessory Detail page is displayed
		 */
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		/*
		 * Verify that The "Partner Actions" link is displayed in Step 1: Tuning Approval of Publishing Process.
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.PARTNER_ACTIONS_STEP_1));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the "Partner Actions" link is displayed in Step 2 : Marketing Approval of Publishing Process
	 */
	@Test
	public void TC040DAD_11(){
		result.addLog("ID : TC040DAD_11: Verify that the 'Partner Actions' link is displayed in Step 2 : Marketing Approval of Publishing Process");
		/*
		 	Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			VP. Verify that the 051D product Edit Page is displayed
			4. Fill valid value into all fields
			5. Upload a  tuning file successfully
			6. Upload a marketing material file
			7. Click "Save" link
			8. Go through Step 1: Tuning Approval of Publishing Process
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(),"Pass");
		// 4. Fill valid value into all required fields
		// 5. Upload a  tuning file successfully
		// 6. Upload a marketing material file
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// 8. Go through Step 1: Tuning Approval of Publishing Process
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * Verify that The "Partner Actions" link is displayed in Step 2: Marketing Approval of Publishing Process
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.PARTNER_ACTIONS_STEP_2));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the “Published Accessory Profile” link is displayed when the accessory is published
	 */
	@Test
	public void TC040DAD_12(){
		result.addLog("ID : TC040DAD_12: Verify that the “Published Accessory Profile” link is displayed when the accessory is published");
		/*
			Pre-condition: User has "Add and manage accessories" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Click "Add Product" link
			VP. Verify that the 051D Accessory Edit Page is displayed
			4. Fill valid value into all fields
			5. Upload a  tuning file successfully
			6. Upload a marketing material file
			7. Upload three product primary catalog image successfully
			8. Click “Save” link
			9. Verify that the “Published Accessory Profile” link is not displayed in “Actions” module
			10. Go through Step 1: Tuning Approval of Publishing Process
			11. Go through step 2: Marketing Approval state
			13. Click “Publish” button
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add Product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP. Verify that the 051D Accessory Edit Page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(), "Pass");
		// 4. Fill valid value into all fields
		// 5. Upload a  tuning file successfully
		// 6. Upload a marketing material file
		// 7. Upload three product primary catalog image successfully
		// 8. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// 9. Verify that the “Published Accessory Profile” link is not displayed in “Actions” module
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.PUBLISHED_ACCESSORY_PROFILE));
		// 10. Go through Step 1: Tuning Approval of Publishing Process
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		// 11. Go through step 2: Marketing Approval state
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		home.click(AccessoryInfo.APPROVE_MARKETING);
		// 12. Click “Publish” button
		home.click(AccessoryInfo.PUBLISH);
		/*
		 * Verify that the “Published Accessory Profile” link is displayed in “Actions” module
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.PUBLISHED_ACCESSORY_PROFILE));
		// Delete accessory
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that user is able to download accessory profile successfully when clicking on the “Published Accessory Profile” link
	 */
	@Test
	public void TC040DAD_13(){
		result.addLog("ID : TC040DAD_13: Verify that user is able to download accessory profile successfully when clicking on the “Published Accessory Profile” link");
		/*
			Pre-condition: User has "Add and manage accessories" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Click "Add Product" link
			VP. Verify that the 051D Accessory Edit Page is displayed
			4. Fill valid value into all fields
			5. Upload a  tuning file successfully
			6. Upload a marketing material file
			7. Upload three product primary catalog image successfully
			8. Click “Save” link
			VP. Verify that the “Published Accessory Profile” link is not displayed in “Actions” module
			9. Go through Step 1: Tuning Approval of Publishing Process
			10. Go through step 2: Marketing Approval state
			11. Click “Publish” button
			VP. Verify that the “Published Accessory Profile” link is displayed in “Actions” module
			12. Click on “Published Accessory Profile” link.
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add Product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP. Verify that the 051D Accessory Edit Page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo())
				.getResult(), "Pass");
		// 4. Fill valid value into all fields
		// 5. Upload a tuning file successfully
		// 6. Upload a marketing material file
		// 7. Upload three product primary catalog image successfully
		// 8. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * Verify that the “Published Accessory Profile” link is not displayed in “Actions” module
		 */
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.PUBLISHED_ACCESSORY_PROFILE));
		// 9. Go through Step 1: Tuning Approval of Publishing Process
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		// 10. Go through step 2: Marketing Approval state
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		home.click(AccessoryInfo.APPROVE_MARKETING);
		// 11. Click “Publish” button
		home.click(AccessoryInfo.PUBLISH);
		/*
		 * Verify that the “Published Accessory Profile” link is displayed in “Actions” module
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.PUBLISHED_ACCESSORY_PROFILE));
		// 12. Click on “Published Accessory Profile” link
		Boolean isDownloaded = home.downloadPublishedAccessoryProfile();
		/*
		 * Verify that the download window popup is displayed and the accessory profile is downloaded successfully
		 */
		Assert.assertTrue(isDownloaded);
		// Delete accessory
		home.doDelete(AccessoryInfo.DELETE);
	 }
	
	/*
	 * Verify that the Partner Tuning is unable to approve when the product Heaphone:X Tuning is not rated
	 */
	@Test
	public void TC040DAD_14(){
		result.addLog("ID : TC040DAD_14: Verify that the Partner Tuning is unable to approve when the product Heaphone:X Tuning is not rated");
		/*
			Pre-condition: User has "Approve product tunings" rights.
			1. Log into DTS portal as a Partner user successfully
			2. Navigate to "Products" page
			3. Click "Add Product" link
			VP. Verify that the 051D product Edit Page is displayed
			4. Fill valid value into all fields
			5. Upload a  tuning file successfully
			6. Click “Save” link
			VP: Verify that the status of Step 1: Tuning Approval is “UNSUBMITTED” and the “Request Tuning Review” link is displayed
			7. Click “Request Tuning Review” link
			8. Log out DTS portal
			9. Log into DTS portal as DTS user successfully
			10. Navigate to “Products” page
			11. Select above product from products table
			VP: Verify that there is a notification message “Headphone:X Tuning Rating is required” displayed below “Approve Tuning” link of step 1: Tuning Approval
			12. Click “Approve Tuning” link
		 */
		// Log out
		home.logout();
		// 1. Log into DTS portal as a Partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 2. Navigate to "Products" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertEquals(home.existsElement(PartnerAddAccessory.getElementInfo()).getResult(), "Pass");
		// 4. Fill valid value into all fields
		// 5. Upload a  tuning file successfully
		// 6. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(PartnerAddAccessory.getHash(), data);
		/*
		 * VP: Verify that the status of Step 1: Tuning Approval is “UNSUBMITTED” and the “Request Tuning Review” link is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(PartnerAccessoryInfo.TUNING_STATUS), "UNSUBMITTED");
		Assert.assertTrue(home.isElementPresent(PartnerAccessoryInfo.REQUEST_TUNING));
		// 7. Click “Request Tuning Review” link
		home.click(PartnerAccessoryInfo.REQUEST_TUNING);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as DTS user successfully
		home.login(superUsername, superUserpassword);
		// 10. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 11. Select above product from products table
		home.selectAnaccessorybyName(data.get("name"));
		// VP: Verify that there is a notification message “Headphone:X Tuning Rating is required” displayed below “Approve Tuning” link of step 1: Tuning Approval
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_RATING_WARNING_PARTNER), "* Headphone:X Tuning Rating is required.");
		// 12. Click “Approve Tuning” link
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * Verify that the DTS user is unable to approve Partner Tuning file
		 */
		Assert.assertNotEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "APPROVED");
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.APPROVE_TUNING));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS Tuning is unable to approve when the product Heaphone:X Tuning is not rated
	 */
	@Test
	public void TC040DAD_15(){
		result.addLog("ID : TC040DAD_15: Verify that the DTS Tuning is unable to approve when the product Heaphone:X Tuning is not rated");
		/*
			Pre-condition: User has "Approve product tunings" rights.
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3. Click "Add Product" link
			VP. Verify that the 051D product Edit Page is displayed
			4. Fill valid value into all fields
			5. Upload a  tuning file successfully
			6. Click “Save” link
			VP: Verify that the status of Step 1: Tuning Approval is “PENDING PARTNER APPROVAL” and the “Partner Actions” link is displayed
			7. Expend the “Partner Actions” link
			VP: Verify that there is a notification message “Headphone:X Tuning Rating is required” displayed below “Approve Tuning” link of step 1: Tuning Approval
			8. Click “Approve Tuning” link
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add Product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(), "Pass");
		// 4. Fill valid value into all fields
		// 5. Upload a  tuning file successfully
		// 6. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryDraft();
		data.put("add tunning", Constant.tuning_hpxtt);
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: Verify that the status of Step 1: Tuning Approval is “PENDING PARTNER APPROVAL” and the “Partner Actions” link is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "PENDING PARTNER APPROVAL");
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.PARTNER_ACTIONS_STEP_1));
		// 7. Expend the “Partner Actions” link
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		/*
		 * VP: Verify that there is a notification message “Headphone:X Tuning Rating is required” displayed below “Approve Tuning” link of step 1: Tuning Approval
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_RATING_WARNING_DTS), "* Headphone:X Tuning Rating is required.");
		// 8. Click “Approve Tuning” link
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * Verify that the DTS user is unable to approve DTS Tuning file
		 */
		Assert.assertNotEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "APPROVED");
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.APPROVE_TUNING));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the product is unable to publish the Headphone:X Tuning Rating is still “Undetermined” in step 3: Publishing
	 */
	@Test
	public void TC040DAD_16(){
		result.addLog("ID : TC040DAD_16: Verify that the product is unable to publish the Headphone:X Tuning Rating is still “Undetermined” in step 3: Publishing");
		/*
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3. Click "Add Product" link
			VP. Verify that the 051D product Edit Page is displayed
			4. Fill valid value into all fields
			5. Upload a tuning file successfully
			6. Leave the Headphone:X Tuning as “Undetermined”
			7. Click “Save” link
			8. Log out DTS portal
			9. Log into DTS portal as a partner user successfully
			10. Navigate to “Products” page
			11. Select above product from Products table
			12. Click “Approve Tuning” link
			13. Pass through step 2: Marketing Approval 
			VP: Verify that there is a notification message “* Pending DTS Headphone X Rating.” displayed in step 3: Publishing for partner user side and the “PUBLISH” button is disabled.
			14. Log out DTS portal
			15. Log into DTS portal as a DTS user successfully
			16. Navigate to “Products” page
			17. Select above product from Products table.
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add Product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(), "Pass");
		// 4. Fill valid value into all fields
		// 5. Upload a tuning file successfully
		// 6. Leave the Headphone:X Tuning as “Undetermined”
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryPublish();
		data.remove("tuning rating");
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as a partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 10. Navigate to “Products” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 11. Select above product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		// 12. Click “Approve Tuning” link
		home.click(PartnerAccessoryInfo.APPROVE_TUNING);
		// 13. Pass through step 2: Marketing Approval 
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		// Log out DTS portal
		home.logout();
		// Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Select product above
		home.selectAnaccessorybyName(data.get("name"));
		// Approve marketing
		home.click(AccessoryInfo.APPROVE_MARKETING);
		// Log out DTS portal
		home.logout();
		// Log in DTS portal as partner user
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select product above
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that there is a notification message “* Pending DTS Headphone X Rating.” displayed in step 3: Publishing for partner user side and the “PUBLISH” button is disabled
		 */
		Assert.assertTrue(home.checkMessageDisplay("* Pending DTS Headphone X Rating."));
		Assert.assertEquals(home.getAtribute(PartnerAccessoryInfo.PUBLISH, "class"), "button disabled");
		// 14. Log out DTS portal
		home.logout();
		// 15. Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// 16. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 17. Select above product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that there is a notification message “* Headphone:X Tuning Rating is required.” displayed in step 3: Publishing for DTS user side and the “PUBLISH” button is disabled
		 */
		Assert.assertTrue(home.checkMessageDisplay("* Headphone:X Tuning Rating is required."));
		Assert.assertEquals(home.getAtribute(AccessoryInfo.PUBLISH, "class"), "button disabled");
		// Delete product
		home.doDelete(PartnerAccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that DTS user can publish product after changing “Headphone:X Rating” value in  step 3: Publishing
	 */
	@Test
	public void TC040DAD_17(){
		result.addLog("ID : TC040DAD_17: Verify that DTS user can publish product after changing “Headphone:X Rating” value in  step 3: Publishing");
		/*
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3. Click "Add Product" link
			VP. Verify that the 051D product Edit Page is displayed
			4. Fill valid value into all fields
			5. Upload a tuning file successfully
			6. Leave the Headphone:X Tuning as “Undetermined”
			7. Click “Save” link
			8. Log out DTS portal
			9. Log into DTS portal as a partner user successfully
			10. Navigate to “Products” page
			11. Select above product from Products table
			12. Click “Approve Tuning” link
			13. Pass through step 2: Marketing Approval 
			VP: Verify that there is a notification message “* Pending DTS Headphone X Rating.” displayed in step 3: Publishing for partner user side and the “PUBLISH” button is disabled.
			14. Log out DTS portal
			15. Log into DTS portal as a DTS user successfully
			16. Navigate to “Products” page
			17. Select above product from Products table.
			VP:  Verify that there is a notification message “* Headphone:X Tuning Rating is required.” displayed in step 3: Publishing for DTS user side and the “PUBLISH” button is disabled.
			18. Click “Edit” link
			19. Change the Headphone:X Tuning Rating value to another
			20. Click “Save” link
			21. Click “PUBLISH” button
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add Product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(), "Pass");
		// 4. Fill valid value into all fields
		// 5. Upload a tuning file successfully
		// 6. Leave the Headphone:X Tuning as “Undetermined”
		// 7. Click “Save” link
		Hashtable<String, String> data = TestData.accessoryPublish();
		data.remove("tuning rating");
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as a partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 10. Navigate to “Products” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 11. Select above product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		// 12. Click “Approve Tuning” link
		home.click(PartnerAccessoryInfo.APPROVE_TUNING);
		// 13. Pass through step 2: Marketing Approval
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		// Log out DTS portal
		home.logout();
		// Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Select product above
		home.selectAnaccessorybyName(data.get("name"));
		// Approve marketing
		home.click(AccessoryInfo.APPROVE_MARKETING);
		// Log out DTS portal
		home.logout();
		// Log in DTS portal as partner user
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select product above
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that there is a notification message “* Pending DTS
		 * Headphone X Rating.” displayed in step 3: Publishing for partner user
		 * side and the “PUBLISH” button is disabled
		 */
		Assert.assertTrue(home.checkMessageDisplay("* Pending DTS Headphone X Rating."));
		Assert.assertEquals(home.getAtribute(PartnerAccessoryInfo.PUBLISH, "class"), "button disabled");
		// 14. Log out DTS portal
		home.logout();
		// 15. Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// 16. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 17. Select above product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that there is a notification message “* Headphone:X Tuning
		 * Rating is required.” displayed in step 3: Publishing for DTS user
		 * side and the “PUBLISH” button is disabled
		 */
		Assert.assertTrue(home.checkMessageDisplay("* Headphone:X Tuning Rating is required."));
		Assert.assertEquals(home.getAtribute(AccessoryInfo.PUBLISH, "class"), "button disabled");
		// 18. Click “Edit” link
		home.click(AccessoryInfo.EDIT_MODE);
		// 19. Change the Headphone:X Tuning Rating value to another
		home.selectOptionByName(AddAccessory.TUNING_RATING, "A");
		// 20. Click “Save” link
		home.click(AddAccessory.SAVE);
		// 21. Click “PUBLISH” button
		home.click(AccessoryInfo.PUBLISH);
		/*
		 * Verify that the product is published successfully
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.PUBLISH_STATUS), "PUBLISHED");
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that partner user is unable to publish product until the DTS user changes “Headphone:X Rating” value in  step 3: Publishing
	 */
	@Test
	public void TC040DAD_18(){
		result.addLog("ID : TC040DAD_18: Verify that partner user is unable to publish product until the DTS user changes “Headphone:X Rating” value in  step 3: Publishing");
		/*
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3. Click "Add Product" link
			VP. Verify that the 051D product Edit Page is displayed
			4. Fill valid value into all fields
			5. Upload a tuning file successfully
			6. Leave the Headphone:X Tuning as “Undetermined”
			7. Click “Save” link
			8. Log out DTS portal
			9. Log into DTS portal as a partner user successfully
			10. Navigate to “Products” page
			11. Select above product from Products table
			12. Click “Approve Tuning” link
			13. Pass through step 2: Marketing Approval 
			VP: Verify that there is a notification message “* Pending DTS Headphone X Rating.” displayed in step 3: Publishing for partner user side and the “PUBLISH” button is disabled.
			14. Log out DTS portal
			15. Log into DTS portal as a DTS user successfully
			16. Navigate to “Products” page
			17. Select above product from Products table.
			VP:  Verify that there is a notification message “* Headphone:X Tuning Rating is required.” displayed in step 3: Publishing for DTS user side and the “PUBLISH” button is disabled.
			18. Click “Edit” link
			19. Change the Headphone:X Tuning Rating value to another
			20. Click “Save” link
			VP: Verfy that the “PUBLISH” button is enabled.
			21. Log out DTS portal
			22. Execute steps from 9 to 11 again
			23. Click “PUBLISH” button
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add Product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(), "Pass");
		// 4. Fill valid value into all fields
		// 5. Upload a tuning file successfully
		// 6. Leave the Headphone:X Tuning as “Undetermined”
		// 7. Click “Save” link
		Hashtable<String, String> data = TestData.accessoryPublish();
		data.remove("tuning rating");
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as a partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		// 10. Navigate to “Products” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 11. Select above product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		// 12. Click “Approve Tuning” link
		home.click(PartnerAccessoryInfo.APPROVE_TUNING);
		// 13. Pass through step 2: Marketing Approval
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		// Log out DTS portal
		home.logout();
		// Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Select product above
		home.selectAnaccessorybyName(data.get("name"));
		// Approve marketing
		home.click(AccessoryInfo.APPROVE_MARKETING);
		// Log out DTS portal
		home.logout();
		// Log in DTS portal as partner user
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select product above
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that there is a notification message “* Pending DTS
		 * Headphone X Rating.” displayed in step 3: Publishing for partner user
		 * side and the “PUBLISH” button is disabled
		 */
		Assert.assertTrue(home.checkMessageDisplay("* Pending DTS Headphone X Rating."));
		Assert.assertEquals(home.getAtribute(PartnerAccessoryInfo.PUBLISH, "class"), "button disabled");
		// 14. Log out DTS portal
		home.logout();
		// 15. Log into DTS portal as a DTS user successfully
		home.login(superUsername, superUserpassword);
		// 16. Navigate to “Products” page
		home.click(Xpath.linkAccessories);
		// 17. Select above product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that there is a notification message “* Headphone:X Tuning
		 * Rating is required.” displayed in step 3: Publishing for DTS user
		 * side and the “PUBLISH” button is disabled
		 */
		Assert.assertTrue(home.checkMessageDisplay("* Headphone:X Tuning Rating is required."));
		Assert.assertEquals(home.getAtribute(AccessoryInfo.PUBLISH, "class"),"button disabled");
		// 18. Click “Edit” link
		home.click(AccessoryInfo.EDIT_MODE);
		// 19. Change the Headphone:X Tuning Rating value to another
		home.selectOptionByName(AddAccessory.TUNING_RATING, "A");
		// 20. Click “Save” link
		home.click(AddAccessory.SAVE);
		/*
		 * VP: Verfy that the “PUBLISH” button is enabled
		 */
		Assert.assertEquals(home.getAtribute(AccessoryInfo.PUBLISH, "class"), "button button-warning");
		// 21. Log out DTS portal
		home.logout();
		// 22. Execute steps from 9 to 11 again
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to “Products” page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select above product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		// 23. Click “PUBLISH” button
		home.click(PartnerAccessoryInfo.PUBLISH);
		/*
		 * Verify that the product is published successfully
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.PUBLISH_STATUS), "PUBLISHED");
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
}
