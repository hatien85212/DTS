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
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.PartnerAccessoryInfo;
import dts.com.adminportal.model.Xpath;

public class PartnerUserCatalog040PAccessoryDetail extends CreatePage{
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
	  * Verify that the correct 040P Accessory Detail page is displayed when selecting an accessory from accessories table.
	  */
	@Test
	public void TC040PAD_01(){
		result.addLog("ID : TC040PAD_01 : As DTS partner user, Select any accessory in Accessories page to see details");
		/*
		 1. Log into DTS portal as a partner admin 
		 2. Navigate to "Accessories" page 
		 3. Select an accessory from accessories table
		 */

		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		/*
		 * Verify that the correct 040P Accessory Detail page is displayed when selecting an accessory from accessories table.
		 */
		Assert.assertEquals(home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult(), "Pass");
	}
	
	/*
	 * Verify that the correct 040D Accessory Detail page is displayed after adding new accessory.
	 */
	@Test
	public void TC040PAD_02(){
		result.addLog("ID : TC040PAD_04: Verify that the correct 040D Accessory Detail page is displayed after adding new accessory.");
		/*
		 	Pre-condition: User has "Add and manage accessories" rights.

			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			VP. Verify that the 051D Accessory Edit Page is displayed
			4. Fill valid value into all required fields.
			5. Click "Save" link
		 */
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// VP. Verify that the 051D Accessory Edit Page is displayed
		Assert.assertTrue(home.isElementPresent(AddAccessory.TITLE));
		// 4. Fill valid value into all required fields.
		// init data
		Hashtable<String, String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * The 040D Accessories Details Page of new accessory is displayed.
		 */
		
		Assert.assertTrue(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME).contains(data.get("name")));
		
		//Clean Up
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the Publishing Process, Model Actions and Model Variant module are displayed in 040D Accessory Details page.
	 */
	@Test
	public void TC040PAD_04(){
		result.addLog("ID : TC040DAD_04: Verify that the Publishing Process, Model Actions and Model Variant module are displayed in 040D Accessory Details page.");
		/*
		 Pre-condition: User has "Add and manage accessories" rights. 
		 1. Log into DTS portal as a DTS user 
		 2. Navigate to "Accessories" page 
		 3. Select an accessory from accessories table
		 */
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessoryDTS();
		/*
		 * The Publishing Process, 
		 * Model Actions and 
		 * Model Variant module is displayed in 040D Accessory Details page.
		 */
		Assert.assertTrue(home.existsElement(AccessoryInfo.actions));
	}
	
	/*
	 * Verify that the "Request Marketing Review" link is disabled when the "EAN/UPC" is empty although the Tuning status is approved
	 */
	@Test
	public void TC040PAD_05(){
		result.addLog("ID : TC040DAD_05: Verify that the 'Request Marketing Review' link is disabled when the 'EAN/UPC' is empty although the Tuning status is approved");
		/*
		Pre-condition: Partner user has "Add and manage accessories" rights.
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		VP. Verify that the 051P Accessory Edit Page is displayed
		4. Fill valid value into all fields but leaves the "EAN/UPC" empty
		5. Upload Tuning and Marketing material successfully
		6. Click "Save" link
		VP: Verify that the 040P Accessories Details page of new accessory is displayed
		7. Click "Request Tuning Review" link in Step 1: Tuning Approval
		VP: Verify that the Tuning approval status is changed to "Pending DTS APPROVAL"
		8. Log out DTS portal
		9. Log into DTS portal as DTS user
		10. Navigate to "Accessories" page
		11. Select the above accessory from the accessories table
		12. Verify that the 040D Accessory Detail page is displayed
		13. Click "Approve Tuning" link from Step 1: Tuning Approval section
		VP: Verify that the Tuning Approval status is changed to "APPROVED"
		14. Log out DTS portal
		15. Log into DTS portal as above partner user
		16. Navigate to "Accessories" page
		17. Select above accessory from accessories table
		 */
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP. Verify that the 051P Accessory Edit Page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddAccessory.TITLE));
		// 4. Fill valid value into all fields but leaves the "EAN/UPC" empty
		// 5. Upload Tuning and Marketing material successfully
		// 6. Click "Save" link
		/*
		 * Init data
		 * Create accessory
		 */
		Hashtable<String, String> data = TestData.accessoryPublish();
		data.remove("ean");
		data.remove("upc");
		home.addAccessoriesPartner(AddAccessory.getHash(), data);

		/*
		 * VP: Verify that the 040P Accessories Details page of new accessory is displayed
		 */
		Assert.assertTrue(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME).contains(data.get("name")));
		// 7. Click "Request Tuning Review" link in Step 1: Tuning Approval
		home.click(PartnerAccessoryInfo.REQUEST_TUNING);
		/*
		 * VP: Verify that the Tuning approval status is changed to "Pending DTS Review"
		 */
		Assert.assertTrue(home.getTextByXpath(PartnerAccessoryInfo.TUNING_STATUS).equalsIgnoreCase(AccessoryInfo.PENDING_DTS_APPROVAL));
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// 10. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 11. Select the above accessory from the accessories table
		home.selectAnaccessorybyName(data.get("name"));
		// 12. Verify that the 040D Accessory Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		// 13. Click "Approve Tuning" link from Step 1: Tuning Approval section
		// Select tuning rate
		home.click(AccessoryInfo.EDIT_MODE);
		home.clickOptionByIndex(AddAccessory.TUNING_RATING, 2);
		home.click(AddAccessory.SAVE);
		// Approve tuning
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * VP: Verify that the Tuning Approval status is changed to "APPROVED"
		 */
		Assert.assertTrue(home.getTextByXpath(AccessoryInfo.TUNING_STATUS).equalsIgnoreCase(AccessoryInfo.APPROVED));
		// 14. Log out DTS portal
		home.logout();
		// 15. Log into DTS portal as above partner user
		home.login(superpartneruser, superpartnerpassword);
		// 16. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 17. Select above accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * The "Request Marketing Review" link in step 2: Marketing Approval is disabled and the error message "An EAN/UPC code is required for Marketing review" is displayed below the EAN/UPC section
		 */
		Assert.assertEquals(home.getAtribute(PartnerAccessoryInfo.MARKETING_ACTION1, "action"), "");
		Assert.assertTrue(home.checkMessageDisplay(Constant.TUNING_APPROVAL[1]));
		// Delete accessory
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	 
	 /*
	  * Verify that the Primary Catalog Image section is displayed in order from left to right.
	  */
	@Test
	public void TC040PAD_06(){
		result.addLog("ID : TC040DAD_06: Verify that the Publishing Process, Model Actions and Model Variant module are displayed in 040D Accessory Details page.");
		/*
		 Pre-condition: Partner user has "Add and manage accessories" rights.
		 1. Log into DTS portal as a partner admin 
		 2. Navigate to "Accessories" page 
		 3. Select an accessory from accessories table
		 */
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		/*
		 * Create accessory that has three images are already uploaded
		 */
		// Click Add Accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * Init data
		 */
		Hashtable<String, String> data = TestData.accessoryImage();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * Verify that The 040P Accessory Details page is displayed and the primary catalog image dislays in order from left to right: 160x160,290x290 and 664x664
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
		Assert.assertTrue(home.isElementDisplayHorizontal(AccessoryInfo.CATALOG_IMAGE_THUMBNAIL_250, AccessoryInfo.CATALOG_IMAGE_THUMBNAIL_500));
		Assert.assertTrue(home.isElementDisplayHorizontal(AccessoryInfo.CATALOG_IMAGE_THUMBNAIL_500, AccessoryInfo.CATALOG_IMAGE_THUMBNAIL_1000));
		// Delete accessory
		home.doDelete(PartnerAccessoryInfo.DELETE);
	}
	
	 /*
	  * Verify that the Lightbox style popup with the picture showing in full size is displayed when clicking on the primay catalog images. 
	  */
	@Test
	public void TC040PAD_07(){
		result.addLog("ID : TC040DAD_07: Verify that the Lightbox style popup with the picture showing in full size is displayed when clicking on the primay catalog images.");
		/*
		Pre-condition: Partner user has "Add and manage Products" rights.
		
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Click "Add product" link
		VP. Verify that the 051P product Edit Page is displayed
		4. Fill valid value into all fields
		5. Upload all three size of primary catalog images successfully
		6. Click "Save" link
		VP: The 040P product Detail page is displayed with three size of primary catalog images
		7. Click on picture of each primary catalog image
		 */
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		// Click Add Accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		Hashtable<String, String> data = TestData.accessoryImage();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		//VP: The 040P product Detail page is displayed with three size of primary catalog images
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
		// Click on picture of each primary catalog image
		// 250x250
		home.click(AccessoryInfo.CATALOG_IMAGE_THUMBNAIL_250);
		home.waitForElementClickable(AccessoryInfo.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AccessoryInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(AccessoryInfo.IMG_NAME[0].replaceAll(".jpg", "")));
		home.click(AccessoryInfo.LIGHTBOX_CLOSE);
		//500x500
		home.click(AccessoryInfo.CATALOG_IMAGE_THUMBNAIL_500);
		home.waitForElementClickable(AccessoryInfo.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AccessoryInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(AccessoryInfo.IMG_NAME[1].replaceAll(".jpg", "")));
		home.click(AccessoryInfo.LIGHTBOX_CLOSE);
		//1000x1000
		home.click(AccessoryInfo.CATALOG_IMAGE_THUMBNAIL_1000);
		home.waitForElementClickable(AccessoryInfo.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AccessoryInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(AccessoryInfo.IMG_NAME[2].replaceAll(".jpg", "")));
		home.click(AccessoryInfo.LIGHTBOX_CLOSE);
		
		// Delete accessory
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	
	 /*
	  * Verify that the Lightbox style popup with the picture showing in full size is displayed when clicking on the image of Marketing Material
	  */
	@Test
	public void TC040PAD_08() throws InterruptedException{
		result.addLog("ID : TC040DAD_08: Verify that the Lightbox style popup with the picture showing in full size is displayed when clicking on the image of Marketing Material");
		/*
			Pre-conPre-condition: Partner user has "Add and manage accessories" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			VP. Verify that the 051P Accessory Edit Page is displayed
			4. Fill valid value into all fields
			5. Upload an image for marketing material successfully
			6. Click "Save" link
			VP: The 040P Accessory Detail page is displayed
			7. Click on above picture thumbnail
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP. Verify that the 051P Accessory Edit Page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddAccessory.TITLE));
		// 4. Fill valid value into all fields
		// 5. Upload an image for marketing material successfully
		// 6. Click "Save" link
		/*
		 * Init data
		 * Create accessory
		 */
		Hashtable <String,String> data = TestData.accessoryMarketing();
		data.remove("add marketing");
		data.put("add marketing", Constant.IMG_NAME[0]);
		home.addAccessoriesPartner(AddAccessory.getHash(), data);

		Assert.assertFalse(home.isElementPresent(AccessoryInfo.LIGHTBOX_STYLE_IMAGE));
		//Verify that the lightbox is not displayed
		
		// 7. Click on above picture thumbnail
		home.click(driver.findElement(By.xpath(AccessoryInfo.MARKETING_MATERIALS)).findElement(By.tagName("img")));
		home.waitForElementClickable(AccessoryInfo.LIGHTBOX_STYLE_IMAGE);
		/*
		 * Verify that The Lightbox style popup with the correct picture showing in full size is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AccessoryInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(AddAccessory.IMG_NAME[0].replaceAll(".jpg", "")));
		
		//close light box
		home.click(AccessoryInfo.LIGHTBOX_CLOSE);
		//wait for element disappeared
		Thread.sleep(3000);
		// check display lightbox again
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.LIGHTBOX_STYLE_IMAGE));
		
		//Tear down: Delete Products
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	 /*
	  * Verify that marketing material file could be download successfully by clicking on its item
	  */
	@Test
	public void TC040PAD_09(){
		result.addLog("ID : TC040DAD_09: Verify that marketing material file could be download successfully by clicking on its item");
		/*
		 Pre-condition: Partner user has "Add and manage accessories" rights.
		 1. Log into DTS portal as a partner admin 
		 2. Navigate to "Accessories" page 
		 3. Click "Add Accessory" link 
		 VP. Verify that the 051P Accessory Edit Page is displayed 
		 4. Fill valid value into all fields 
		 5. Upload a file for marketing material successfully 
		 6. Click "Save" link 
		 VP: The 040P Accessory Detail page is displayed 
		 7. Click on uploaded marketing material file
		 */
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP. Verify that the 051P Accessory Edit Page is displayed 
		 */
		Assert.assertTrue(home.isElementPresent(AddAccessory.TITLE));
		// 4. Fill valid value into all fields
		// 5. Upload a file for marketing material successfully
		// 6. Click "Save" link 
		/*
		 * Init data
		 * Create accessory
		 */
		Hashtable<String, String> data = TestData.accessoryMarketing();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);

		/*
		 * VP: The 040P Accessory Detail page is displayed 
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		// 7. Click on uploaded marketing material file
		
		Boolean isDownloaded = home.downloadFile(data.get("add marketing"));
		Assert.assertTrue(isDownloaded);
		
		//Teardown:
		home.doDelete(AccessoryInfo.DELETE);
		
	}
	
	@Test
	public void TC040PAD_10(){
		result.addLog("ID : TC040DAD_10: Verify that the 'Published product Profile' link is displayed when the product is published.");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click "Add Product" link
			VP. Verify that the 051P Product Edit Page is displayed
			4. Fill valid value into all fields
			5. Upload a  tuning file successfully
			6. Upload a marketing material file
			7. Upload three product primary catalog image successfully
			8. Click “Save” link
			9. Verify that the “Published product Profile” link is not displayed in “Actions” module
			10. Go through Step 1: Tuning Approval of Publishing Process
			11. Go through step 2: Marketing Approval state
			13. Click “Publish” button
		 */
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP. Verify that the 051P Accessory Edit Page is displayed 
		 */
		Assert.assertTrue(home.isElementPresent(AddAccessory.TITLE));
		// 4. Fill valid value into all fields
		// 5. Upload a file for marketing material successfully
		// 6. Click "Save" link 
		/*
		 * Init data
		 * Create accessory
		 */
		Hashtable<String, String> data = TestData.accessoryPublish();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);

		/*
		 * VP: The 040P Accessory Detail page is displayed 
		 * VP: Verify that the “Published product Profile” link is not displayed in “Actions” module
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.PUBLISHED_ACCESSORY_PROFILE));
		// log out portal
		home.logout();
		// Log into DTS portal as dts user to process through 3 steps of product approval
		home.login(superUsername, superUserpassword);
		//Navigate to products page
		home.click(Xpath.linkAccessories);
		// Select above product from products list
		home.selectAnaccessorybyName(data.get("name"));
		//	10. Go through Step 1: Tuning Approval of Publishing Process
		// Change the tuning rating
		home.click(AccessoryInfo.EDIT_MODE);
		home.selectOptionByName(AddAccessory.TUNING_RATING, AddAccessory.tuningRatingOption[2]);
		home.click(AddAccessory.SAVE);
		// Verify that the accessory info page is displayed.
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);;
		home.click(AccessoryInfo.APPROVE_TUNING);
		//	11. Go through step 2: Marketing Approval state
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		home.click(AccessoryInfo.APPROVE_MARKETING);
		//	13. Click “Publish” button
		home.click(AccessoryInfo.PUBLISH);
		
		//Verify that the 'Published product Profile' link is displayed when the product is published in DTS user side
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.PUBLISHED_ACCESSORY_PROFILE));
		
		//Log out DTS portal
		home.logout();
		// Log into DTD portal as partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		//Navigate to products page
		home.click(Xpath.linkAccessories);
		// Select above product from products list
		home.selectAnaccessorybyName(data.get("name"));
		//Verify that the 'Published product Profile' link is displayed when the product is published in partner user side
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.PUBLISHED_ACCESSORY_PROFILE));

		//Teardown:
		home.doDelete(AccessoryInfo.DELETE);
		
	}
	@Test
	public void TC040PAD_11(){
		result.addLog("ID : TC040DAD_11: Verify that user is able to download product profile successfully when clicking on the 'Published product Profile' link.");
		/*
		Pre-condition: User has "Add and manage Products" rights.

		1. Log into DTS portal as a Partner user
		2. Navigate to "Products" page
		3. Click "Add Product" link
		VP. Verify that the 051P Product Edit Page is displayed
		4. Fill valid value into all fields
		5. Upload a  tuning file successfully
		6. Upload a marketing material file
		7. Upload three product primary catalog image successfully
		8. Click “Save” link
		VP. Verify that the “Published product Profile” link is not displayed in “Actions” module
		9. Go through Step 1: Tuning Approval of Publishing Process
		10. Go through step 2: Marketing Approval state
		11. Click “Publish” button
		VP. Verify that the “Published product Profile” link is displayed in “Actions” module
		12. Click on “Published product Profile” link.
		 */
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP. Verify that the 051P Accessory Edit Page is displayed 
		 */
		Assert.assertTrue(home.isElementPresent(AddAccessory.TITLE));
		// 4. Fill valid value into all fields
		// 5. Upload a file for marketing material successfully
		// 6. Click "Save" link 
		/*
		 * Init data
		 * Create accessory
		 */
		Hashtable<String, String> data = TestData.accessoryPublish();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);

		/*
		 * VP: The 040P Accessory Detail page is displayed 
		 * VP: Verify that the “Published product Profile” link is not displayed in “Actions” module
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.PUBLISHED_ACCESSORY_PROFILE));
		// log out portal
		home.logout();
		// Log into DTS portal as dts user to process through 3 steps of product approval
		home.login(superUsername, superUserpassword);
		//Navigate to products page
		home.click(Xpath.linkAccessories);
		// Select above product from products list
		home.selectAnaccessorybyName(data.get("name"));
		//	10. Go through Step 1: Tuning Approval of Publishing Process
		// Change the tuning rating
		home.click(AccessoryInfo.EDIT_MODE);
		home.selectOptionByName(AddAccessory.TUNING_RATING, AddAccessory.tuningRatingOption[2]);
		home.click(AddAccessory.SAVE);
		// Verify that the accessory info page is displayed.
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);;
		home.click(AccessoryInfo.APPROVE_TUNING);
		//	11. Go through step 2: Marketing Approval state
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		home.click(AccessoryInfo.APPROVE_MARKETING);
		//	13. Click “Publish” button
		home.click(AccessoryInfo.PUBLISH);
		
		//Verify that the 'Published product Profile' link is displayed when the product is published in DTS user side
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.PUBLISHED_ACCESSORY_PROFILE));
		
		//Log out DTS portal
		home.logout();
		// Log into DTD portal as partner user successfully
		home.login(superpartneruser, superpartnerpassword);
		//Navigate to products page
		home.click(Xpath.linkAccessories);
		// Select above product from products list
		home.selectAnaccessorybyName(data.get("name"));
		//Verify that the 'Published product Profile' link is displayed when the product is published in partner user side
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.PUBLISHED_ACCESSORY_PROFILE));
		
		//12. Click on “Published product Profile” link.
		Boolean isDownloaded = home.downloadPublishedAccessoryProfile();
		Assert.assertTrue(isDownloaded);

		//Teardown:
		home.doDelete(AccessoryInfo.DELETE);
		
	}
}
