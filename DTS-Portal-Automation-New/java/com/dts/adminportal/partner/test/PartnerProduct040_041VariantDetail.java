package com.dts.adminportal.partner.test;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BaseController;
import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PartnerVariantInfo;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.TuningRequestForm;
import com.dts.adminportal.model.TuningRequestFormSuccess;
import com.dts.adminportal.model.VariantInfo;
import com.dts.adminportal.util.TestData;

public class PartnerProduct040_041VariantDetail extends BasePage{

	@Override
	protected void initData() {
	}	
	
	 /*
	  * Verify that the Lightbox style popup with the correct picture showing in full size is displayed  when clicking on file names or thumbnails
	  */
	@Test
	public void TC040PAD_01(){
		productControl.addLog("ID : Verify that the Lightbox style popup with the correct picture showing "
				+ "in full size is displayed  when clicking on file names or thumbnails");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Fill valid value into all required fields.
			5. Upload three primary catalog images
			6. Upload a document file for  marketing material
			7. Click "Save" link
			VP: The 040P Accessories Details Page of new product is displayed.
			And the Publishing Process, Model Actions and Model Variant module is displayed in 040P product Details page. 
			And The 040P product Details page is displayed and the primary catalog image dislays in order from left to right: 250x250,500x500 and 1000x1000
			8. Click on the file name of uploaded document file
			VP: The marketing material is downloaded successfully
			9. Click on thumbnail of each primary catalog image and  file name
			VP: The Lightbox style popup with the correct picture showing in full size is displayed  when clicking on file names or thumbnails
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		//4. Fill valid value into all required fields.
		//5. Upload three primary catalog images
		//6. Upload a document file for  marketing material
		//7. Click "Save" link
		Hashtable<String,String> data =TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		//VP. Verify that the 051P product Edit Page is displayed
		//And the Publishing Process, Model Actions and Model Variant module is displayed in 040P product Details page. 
		//And The 040P product Details page is displayed and the primary catalog image displays in order from left to right: 250x250,500x500 and 1000x1000
		Assert.assertTrue(productControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME).contains(data.get("name")));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PUBLISHING_PROCESS));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.MODEL_ACTIONS));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.MODEL_VARIANTS));
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME), data.get("name"));
		Assert.assertTrue(productControl.isElementDisplayHorizontal(ProductDetailModel.CATALOG_IMAGE_THUMBNAIL_250, ProductDetailModel.CATALOG_IMAGE_THUMBNAIL_500));
		Assert.assertTrue(productControl.isElementDisplayHorizontal(ProductDetailModel.CATALOG_IMAGE_THUMBNAIL_500, ProductDetailModel.CATALOG_IMAGE_THUMBNAIL_1000));
		//8. Click on the file name of uploaded document file
		//VP: The marketing material is downloaded successfully
		boolean isDownloaded = productControl.downloadFile(data.get("add marketing"));
		Assert.assertTrue(isDownloaded);
		//9. Click on thumbnail of each primary catalog image and  file name
		//VP: The lightbox style popup is displayed in full size
		// Click on picture of each primary catalog image
		// 250x250
		productControl.click(ProductDetailModel.CATALOG_IMAGE_THUMBNAIL_250);
		productControl.waitForElementClickable(ProductDetailModel.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(ProductDetailModel.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.SIZE_LIST[0]));
		productControl.click(ProductDetailModel.LIGHTBOX_CLOSE);
		//500x500
		productControl.click(ProductDetailModel.CATALOG_IMAGE_THUMBNAIL_500);
		productControl.waitForElementClickable(ProductDetailModel.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(ProductDetailModel.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.SIZE_LIST[1]));
		productControl.click(ProductDetailModel.LIGHTBOX_CLOSE);
		//1000x1000
		productControl.click(ProductDetailModel.CATALOG_IMAGE_THUMBNAIL_1000);
		productControl.waitForElementClickable(ProductDetailModel.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(ProductDetailModel.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.SIZE_LIST[2]));
		productControl.click(ProductDetailModel.LIGHTBOX_CLOSE);
	
	}
	
	
	/*
	 * Verify that the "Request Marketing Review" link is disabled when the "EAN/UPC" is empty although the Tuning status is approved
	 */
	@Test
	public void TC040PAD_02(){
		productControl.addLog("ID : TC040DAD_02: Verify that the 'Request Marketing Review' link is disabled "
				+ "when the 'EAN/UPC' is empty although the Tuning status is approved");
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
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		productControl.click(ProductModel.ADD_PRODUCT);
		/*
		 * VP. Verify that the 051P Accessory Edit Page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.PRODUCT_TITLE));
		// 4. Fill valid value into all fields but leaves the "EAN/UPC" empty
		// 5. Upload Tuning and Marketing material successfully
		// 6. Click "Save" link
		/*
		 * Init data
		 * Create accessory
		 */
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		data.remove("ean");
		data.remove("upc");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);

		/*
		 * VP: Verify that the 040P Accessories Details page of new accessory is displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME).contains(data.get("name")));
		// 7. Click "Request Tuning Review" link in Step 1: Tuning Approval
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		/*
		 * VP: Verify that the Tuning approval status is changed to "Pending DTS Review"
		 */
		Assert.assertEquals(ProductDetailModel.ProductStatus.PENDING_DTS_APPROVAL.getName(), productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS));
		// 8. Log out DTS portal
		productControl.logout();
		// 9. Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 10. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 11. Select the above accessory from the accessories table
		productControl.selectAccessorybyName(data.get("name"));
		// 12. Verify that the 040D Accessory Detail page is displayed
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 13. Click "Approve Tuning" link from Step 1: Tuning Approval section
		// Select tuning rate
		productControl.click(ProductDetailModel.EDIT_MODE);
		productControl.clickOptionByIndex(AddEditProductModel.TUNING_RATING, 2);
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// Approve tuning
//		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productWf.approveTuning();
		/*
		 * VP: Verify that the Tuning Approval status is changed to "APPROVED"
		 */
		Assert.assertTrue(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS)
				.equalsIgnoreCase(ProductDetailModel.ProductStatus.APPROVED.getName()));
		// 14. Log out DTS portal
		productControl.logout();
		// 15. Log into DTS portal as above partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 16. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 17. Select above accessory from accessories table
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * The "Request Marketing Review" link in step 2: Marketing Approval is disabled and the error message "An EAN/UPC code is required for Marketing review" is displayed below the EAN/UPC section
		 */
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.MARKETING_ACTION, "action"), "");
		Assert.assertTrue(productControl.checkMessageDisplay(Constant.TUNING_APPROVAL[1]));
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
	}
		 

	 /*
	  * Verify that user is able to download product profile successfully when clicking on the “Published product Profile” link.
	  */
	@Test
	public void TC040PAD_03() throws InterruptedException{
		productControl.addLog("ID : TC040DAD_03: Verify that user is able to download product profile "
				+ "successfully when clicking on the 'Published product Profile' link.");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click "Add Product" link
			4. Fill valid value into all fields
			5. Upload a  tuning file successfully
			6. Upload a marketing material file
			7. Upload three product primary catalog image successfully
			8. Click “Save” link
			VP. Verify that the “Published product Profile” link is not displayed in “Actions” module
			9. Click on image thumbnail or file name of the image of marketing material
			VP: Verify that the lightbox is displayed in full size
			10. Go through Step 1: Tuning Approval of Publishing Process
			11. Go through step 2: Marketing Approval state
			12. Click “Publish” button
			VP. Verify that the “Published product Profile” link is displayed in “Actions” module
			13. Click on “Published product Profile” link.
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		productControl.click(ProductModel.ADD_PRODUCT);
		//4. Fill valid value into all fields
		//5. Upload a  tuning file successfully
		//6. Upload an image for marketing material file
		//7. Upload three product primary catalog image successfully
		//8. Click “Save” link
		/*
		 * Init data
		 * Create accessory
		 */
		Hashtable <String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
//		data.remove("add marketing");
		data.put("add marketing", AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		//VP. Verify that the “Published product Profile” link is not displayed in “Actions” module
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE));
				
		//9. Click on image thumbnail or file name of the image of marketing material
		productControl.click(driver.findElement(By.xpath(ProductDetailModel.MARKETING_MATERIALS)).findElement(By.tagName("img")));
		//VP: Verify that the lightbox is displayed in full size
		productControl.waitForElementClickable(ProductDetailModel.LIGHTBOX_STYLE_IMAGE);
		/*
		 * Verify that The Lightbox style popup with the correct picture showing in full size is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(ProductDetailModel.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.IMG_NAME[0].replaceAll(".jpg", "")));
		
		//close light box
		productControl.click(ProductDetailModel.LIGHTBOX_CLOSE);
		//wait for element disappeared
		Thread.sleep(3000);
		// check display lightbox again
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.LIGHTBOX_STYLE_IMAGE));
		
		//10. Go through Step 1: Tuning Approval of Publishing Process
		//11. Go through step 2: Marketing Approval state
		//12. Click “Publish” button

		// log out portal
		productControl.logout();
		// Log into DTS portal as dts user to process through 3 steps of product approval
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//Navigate to products page
		productControl.click(PageHome.linkAccessories);
		// Select above product from products list
		productControl.selectAccessorybyName(data.get("name"));
		//	10. Go through Step 1: Tuning Approval of Publishing Process
		// Change the tuning rating
		productControl.click(ProductDetailModel.EDIT_MODE);
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, AddEditProductModel.TuningRatingOption.A.getName());
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// Verify that the accessory info page is displayed.
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME), data.get("name"));
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);;
//		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productWf.approveTuning();
		//	11. Go through step 2: Marketing Approval state
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		//	13. Click ï¿½Publishï¿½ button
		productControl.click(ProductDetailModel.PUBLISH);
		
		//Verify that the 'Published product Profile' link is displayed when the product is published in DTS user side
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME), data.get("name"));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE));
		
		//Log out DTS portal
		productControl.logout();
		// Log into DTD portal as partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		//Navigate to products page
		productControl.click(PageHome.linkAccessories);
		// Select above product from products list
		productControl.selectAccessorybyName(data.get("name"));
		//Verify that the 'Published product Profile' link is displayed when the product is published in partner user side
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME), data.get("name"));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE));
		//13. Click on “Published product Profile” link.

		Boolean isDownloaded = productControl.downloadPublishedAccessoryProfile();
		Assert.assertTrue(isDownloaded);
		//Tear down: Delete Products
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the requesting DTS tuning process is validated correctly
	 */
	@Test
	public void TC040PAD_04() {
		productControl.addLog("ID : 040PAD_04 : Verify that the requesting DTS tuning process is validated correctly");
		/*
			Pre-Condition: Partner user has "Request product tunings" rights.
			
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Click "Add product" link
			4. Fill valid value into all required fields
			5. Do not upload tuning
			6. Click "Save" link
			VP: The correct 040P product Detail page is displayed and the "Request DTS Tuning" link is displayed.
			7. Click Request DTS Tuning" link
			VP: The 043Pa Tuning Request Form page is displayed which including:  form name, sub-title, message, term checkbox, product data and additional message, and action module
			8. Do not check for "I understand the terms of this submission" checkbox
			9. Click "Submit" link
			VP: An error message "Please select your agreement" is displayed below the term condition check box and the request form could not be submitted.
			10. Click on the product name link on top of page
			VP: The portal is redirected to correct product detail page.
			11. Click Request DTS Tuning" link again
			12. Click "Cancel" link in 043Pa Tuning request page again
			VP: The portal redirects to 040P product Detail page 
			13. Click Request DTS Tuning" link again
			14. Type valid value into all fields
			15. Tick to check the "I understand the terms of this submission" checkbox
			16. Click "Submit" link
			VP: The 043Pb Tuning Request Form Success page is displayed and the entered information from 043Pa Tuning Request Form is displayed correctly.
			17. Click on product name link on top of page. 
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		// 5. Do not upload tuning
		// 6. Click "Save" link
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * VP: The correct 040P Accessory Detail page is displayed and the "Request DTS Tuning" link is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.REQUEST_TUNING));
		// 7. Click Request DTS Tuning link
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		/*
		 * The 043Pa Tuning Request Form page is displayed which including:  form name, sub-title, message, term checkbox, accessory data and additional message.
		 */
		Assert.assertEquals(productControl.existsElement(TuningRequestForm.APPROVE_TUNNING_FORM()), true);
		
		//8. Do not check for "I understand the terms of this submission" checkbox
		//9. Click "Submit" link
		productControl.click(TuningRequestForm.SUBMIT);
		//VP: An error message "Please select your agreement" is displayed below the term condition check box and the request form could not be submitted.
		Assert.assertTrue(productControl.isElementPresent(TuningRequestForm.ERROR_MESSAGE));
		//10. Click on the product name link on top of page
		//VP: The portal is redirected to correct product detail page.
		productControl.click(TuningRequestForm.PRODUCT_LINK);
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.REQUEST_TUNING));
		
		//11. Click Request DTS Tuning" link again
		//12. Click "Cancel" link in 043Pa Tuning request page again
		//VP: The portal redirects to 040P product Detail page
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		productControl.click(TuningRequestForm.CANCEL);
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.REQUEST_TUNING));
		
		//13. Click Request DTS Tuning" link again
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		//14. Type valid value into all fields
		String addinfo=RandomStringUtils.randomAlphanumeric(10);
		productControl.editData(TuningRequestForm.ADDITIONALINFO, addinfo );
		//15. Tick to check the "I understand the terms of this submission" checkbox
		productControl.click(TuningRequestForm.AGREE);
		//16. Click "Submit" link
		productControl.click(TuningRequestForm.SUBMIT);
		//VP: The 043Pb Tuning Request Form Success page is displayed and the entered information from 043Pa Tuning Request Form is displayed correctly.
		Assert.assertTrue(productControl.isElementPresent(TuningRequestFormSuccess.SUCCESS_MESSAGE));
		Assert.assertTrue(productControl.getTextByXpath(TuningRequestFormSuccess.ADDITIONAL_INFO).contains(addinfo));
		
		//17. Click on product name link on top of page
		//VP: The portal is redirected to correct product detail page.
		productControl.click(TuningRequestFormSuccess.PRODUCT_LINK);
		Assert.assertTrue(productControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME).contains(data.get("name")));
		
		//Teardown:
		// Delete product
//		productControl.click(TuningRequestForm.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the correct 041P product Variant Detail page is displayed and worked properly after adding new product variant
	 */
	@Test
	public void TC041PAVD_01(){
		productControl.addLog("ID : TC041PAVD_01 : Verify that the correct 041P product Variant Detail "
				+ "page is displayed and worked properly after adding new product variant");
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
			VP: VP: The correct 041P product Variant Detail page is displayed
			VP: The Publishing Process and Model Variant Actions modules are displayed in 041P product Variant Details page.
			VP: The 040P product Variant Details page is displayed and the primary catalog image dislays in order from left to right: 250x250,500x500 and 1000x1000
			7. Click on picture of each primary catalog image
			VP: The Lightbox style popup with the correct picture showing in full size is displayed 
			8. Click on above marketing material picture thumbnail
			VP: The Lightbox style popup with the correct picture showing in full size is displayed
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select an product from Products table
		productControl.selectAnAccessory();
		/*
		 * VP. Verify that 040P product Detail page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 4. Click "Add New Variant" link in "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP. The 052P Model Variant Edit page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getVariantElementsInfo()), true);
		// 5. Fill valid value into all requires fields
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.variantData(true, true, true);
		data.remove("add marketing");
		data.put("add marketing", AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		productControl.addVariant(AddEditProductModel.getVariantHash(), data);
		/*
		 * Verify that The correct 041P product Variant Detail page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(PartnerVariantInfo.getElementsInfo()), true);
		//The Publishing Process and Model Variant Actions modules are displayed in 041P product Variant Details page.
		Assert.assertTrue(productControl.isElementPresent(PartnerVariantInfo.PUBLISHING_PROCESS_CONTAINER));
		Assert.assertTrue(productControl.isElementPresent(PartnerVariantInfo.ACTION_CONTAINER));
		
		//The 040P product Variant Details page is displayed and the primary catalog image displays in order from left to right: 250x250,500x500 and 1000x1000
		Assert.assertEquals(productControl.existsElement(PartnerVariantInfo.getElementsInfo()), true);
		Assert.assertTrue(productControl.isImageDisplayLeftToRight(PartnerVariantInfo.IMAGE_TABLE));
		
		//7. Click on picture of each primary catalog image
		//VP: The Lightbox style popup with the correct picture showing in full size is displayed
		// Click on picture of each primary catalog image
		// 250x250
		productControl.click(VariantInfo.CATALOG_IMAGE_THUMBNAIL_250);
		productControl.waitForElementClickable(VariantInfo.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(VariantInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_250_JPG.getName().replaceAll(".jpg", "")));
		productControl.click(VariantInfo.LIGHTBOX_CLOSE);
		//500x500
		productControl.click(VariantInfo.CATALOG_IMAGE_THUMBNAIL_500);
		productControl.waitForElementClickable(VariantInfo.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(VariantInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_500_JPG.getName().replaceAll(".jpg", "")));
		productControl.click(VariantInfo.LIGHTBOX_CLOSE);
		//1000x1000
		productControl.click(VariantInfo.CATALOG_IMAGE_THUMBNAIL_1000);
		productControl.waitForElementClickable(VariantInfo.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(VariantInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_1000_JPG.getName().replaceAll(".jpg", "")));
		productControl.click(VariantInfo.LIGHTBOX_CLOSE);
		
		//8. Click on above marketing material picture thumbnail
		//VP: The Lightbox style popup with the correct picture showing in full size is displayed
		productControl.click(driver.findElement(By.xpath(PartnerVariantInfo.MARKETING_MATERIALS)).findElement(By.tagName("img")));
		productControl.waitForElementClickable(PartnerVariantInfo.LIGHTBOX_STYLE_IMAGE);
		/*
		 * Verify that The Lightbox style popup with the correct picture showing in full size is displayed 
		 */
		Assert.assertTrue(productControl.isElementPresent(PartnerVariantInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(PartnerVariantInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.SIZE_LIST[0]));
		
	}
	
	
		

		
	/*
	 * Verify that marketing material file could be download successfully by clicking on its item.
	 */
	@Test
	public void TC041PAVD_02(){
		productControl.addLog("ID TC041PAVD_02 : Verify that marketing material file could be "
				+ "download successfully by clicking on its item.");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			VP. Verify that 040P product Detail page is displayed
			4. Click "Add New Variant" link in "Model Actions" module
			VP. The 052P Model Variant Edit page is displayed.
			5. Fill valid value into all requires fields
			6. Upload a file for marketing material successfully
			7. Click "Save" link
			VP: The 041P product Variant Detail page is displayed 
			8.Click on uploaded marketing material file
			VP: The marketing material file could be download successfully.
			8. Pass through two step of Publishing Process
			9. Click “Publish” button
			VP: Verify that the product variant is published successfully and the “Published Variant Profile” link is displayed in “Actions” module
			10. Click on “Published Variant Profile” link.
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		productControl.selectAnAccessory();
		
		/*
		 * VP. Verify that 040P Accessory Detail page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// Get product name
		String productname = productControl.getTextByXpath(ProductDetailModel.TITLE_NAME).trim().toString();
		
		loginControl.logout();
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productControl.click(PageHome.linkAccessories);
		//System.out.print(productname);
		
		productControl.selectAccessorybyName(productname);
		Hashtable<String,String> data = TestData.variantData(false, true, true);
		productWf.addNewVariant(data);
		/*
		 * VP: The 041P Accessory Variant Detail page is displayed 
		 */
		Assert.assertEquals(productControl.existsElement(PartnerVariantInfo.getElementsInfo()), true);
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.MARKETING_DOWNLOAD_LINK));
		// 8.Click on uploaded marketing material file
		//boolean isFileDownloaded = productControl.downloadFile(data.get("add marketing"));
		//boolean isFileDownloaded = productControl.downloadFile(ProductDetailModel.MARKETING_DOWNLOAD_LINK);		
		 //boolean isFileDownloaded = baseControl.downloadFile(ProductDetailModel.MARKETING_DOWNLOAD_LINK);
		boolean isFileDownloaded = productControl.downloadFile(ProductDetailModel.MARKETING_DOWNLOAD_LINK);
		 
	
		/*
		 * Verify that The marketing material file could be download successfully
		 */
		Assert.assertTrue(isFileDownloaded);
		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		productControl.click(VariantInfo.REQUEST_MARKETING_REVIEW);
		productControl.click(VariantInfo.APPROVE_MARKETING);
		productControl.click(ProductDetailModel.PUBLISH_PRODUCT);	
		
		//Log out DTS portal 
		productControl.logout();
		// Log into DTS portal as a Partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to ï¿½Accessoriesï¿½ page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select a published accessory from accessories table
		productControl.selectAccessorybyName(productname);
		productControl.selectAVariantbyName(data.get("name"));
		//VP: Verify that the product variant is published successfully and the “Published Variant Profile” link is displayed in “Actions” module
		//10. Click on “Published Variant Profile” link.
		//Verify that the download window popup is displayed and the product variant profile is downloaded successfully.
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.PUBLISHED_STATUS), VariantInfo.PUBLISHED_STRING);
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.PUBLISHED_VARIANT_PROFILE_LINK));
		Assert.assertTrue(productControl.downloadPublishedAccessoryProfile());
		
	}
}
