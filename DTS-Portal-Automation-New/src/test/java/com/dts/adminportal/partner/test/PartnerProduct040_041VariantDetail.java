package com.dts.adminportal.partner.test;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

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
import com.dts.adminportal.util.DbUtil;
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
//		boolean isDownloaded = productControl.downloadFile(productControl.getTextByXpath(ProductDetailModel.MARKETING_DOWNLOAD_LINK));
		boolean isDownloaded = productControl.downloadFile(AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
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
	
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
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
		Assert.assertTrue(productControl.checkMessageDisplay(Constant.TUNING_APPROVAL.EAN_UPC_is_required.getName()));
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
	}
		 

	 /*
	  * Verify that user is able to download product profile successfully when clicking on the �Published product Profile� link.
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
			8. Click �Save� link
			VP. Verify that the �Published product Profile� link is not displayed in �Actions� module
			9. Click on image thumbnail or file name of the image of marketing material
			VP: Verify that the lightbox is displayed in full size
			10. Go through Step 1: Tuning Approval of Publishing Process
			11. Go through step 2: Marketing Approval state
			12. Click �Publish� button
			VP. Verify that the �Published product Profile� link is displayed in �Actions� module
			13. Click on �Published product Profile� link.
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
		//8. Click �Save� link
		/*
		 * Init data
		 * Create accessory
		 */
		Hashtable <String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
//		data.remove("add marketing");
		data.put("add marketing", AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		//VP. Verify that the �Published product Profile� link is not displayed in �Actions� module
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
		//12. Click �Publish� button

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
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
//		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
//		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		productWf.approveMarketing();
		//	13. Click �Publish� button
		productControl.click(ProductDetailModel.PUBLISH);
		
		//Verify that the 'Published product Profile' link is displayed when the product is published in DTS user side
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME), data.get("name"));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE));
		
		//Log out DTS portal
		productControl.logout();
		// Log into DTD portal as partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		//Navigate to products page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select above product from products list
		productControl.selectAccessorybyName(data.get("name"));
		//Verify that the 'Published product Profile' link is displayed when the product is published in partner user side
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME), data.get("name"));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE));
		//13. Click on �Published product Profile� link.
		Boolean isDownloaded = productControl.downloadFile(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE);
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
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),productData);
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
		
		
		//Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(productData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		
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
			9. Click �Publish� button
			VP: Verify that the product variant is published successfully and the �Published Variant Profile� link is displayed in �Actions� module
			10. Click on �Published Variant Profile� link.
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataproduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataproduct);
		
		/*
		 * VP. Verify that 040P Accessory Detail page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// log out 
		loginControl.logout();
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productControl.click(PageHome.linkAccessories);
		
		productControl.selectAccessorybyName(dataproduct.get("name"));
		productControl.click(ProductDetailModel.EDIT_MODE);
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING,AddEditProductModel.TuningRatingOption.A.getName());
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		productWf.approveMarketing();
		productControl.click(ProductDetailModel.PUBLISH);
		Hashtable<String,String> data = TestData.variantData(false, true, true);
		productWf.addNewVariant(data);
		/*
		 * VP: The 041P Accessory Variant Detail page is displayed 
		 */
		Assert.assertEquals(productControl.existsElement(PartnerVariantInfo.getElementsInfo()), true);
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.MARKETING_DOWNLOAD_LINK));
		// 8.Click on uploaded marketing material file
//		boolean isFileDownloaded = productControl.downloadFile(productControl.getTextByXpath(ProductDetailModel.MARKETING_DOWNLOAD_LINK));
		boolean isFileDownloaded = productControl.downloadFile(AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		/*
		 * Verify that The marketing material file could be download successfully
		 */
		Assert.assertTrue(isFileDownloaded);
		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		productControl.click(VariantInfo.REQUEST_MARKETING_REVIEW);
		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		productControl.click(ProductDetailModel.REQUEST_MARKETING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		productControl.click(VariantInfo.PUBLISH);	
		//Log out DTS portal 
		productControl.logout();
		// Log into DTS portal as a Partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to �Accessories� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select a published accessory from accessories table
		productControl.selectAccessorybyName(dataproduct.get("name"));
		productControl.selectAVariantbyName(data.get("name"));
		//VP: Verify that the product variant is published successfully and the �Published Variant Profile� link is displayed in �Actions� module
		//10. Click on �Published Variant Profile� link.
		//Verify that the download window popup is displayed and the product variant profile is downloaded successfully.
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.PUBLISHED_STATUS), VariantInfo.PUBLISHED_STRING);
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.PUBLISHED_VARIANT_PROFILE_LINK));
		Assert.assertTrue(productControl.downloadFile(VariantInfo.PUBLISHED_VARIANT_PROFILE_LINK));
		
		// Delete data
		productControl.doDelete(ProductDetailModel.DELETE_VARIANT);
		productControl.doDelete(ProductDetailModel.DELETE);
		
	}
	
	/*
	 * Verify that 040D Product Detail page and 041 Variant Detail page display �Input Specification� section correctly
	 */
	@Test
	public void TC040PAD_05(){
		productControl.addLog("ID : TC040PAD_05: Verify that 040D Product Detail page and 041 Variant Detail page display �Input Specification� section correctly");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click �Add Product� link
			4. Fill valid value into all required fields
			5. Select the connection type "USB" only
			6. Click �Save� link
			VP: Verify that �Input Specification� section contains only USB
			7. Click �Edit Version� link
			8. Select the connection type include �Wired�, �Bluetooth� and �USB�
			9. Click �Save� link
			VP: Verify that �Input Specification� section contains Lineout, Bluetooth and USB
			10. Click �Add New Variant� link
			11. Fill valid value into all required fields
			12. Click �Save� link
			VP: Verify that �Input Specification� section contains Lineout, Bluetooth and USB
		 */
		
		// 1. Log into DTS portal as a Partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click �Add Product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		// 5. Select the connection type "USB" only
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		dataProduct.remove("save");
		dataProduct.remove("wired");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		productControl.selectACheckbox(AddEditProductModel.USB_CHECKBOX);
		// 6. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: Verify that �Input Specification� section contains only USB
		Assert.assertTrue(productControl.getTextByXpath(ProductDetailModel.INPUT_SPECIFICATION_PANEL).contains(AddEditProductModel.ConnectionType.USB.getName()));
		Assert.assertFalse(productControl.getTextByXpath(ProductDetailModel.INPUT_SPECIFICATION_PANEL).contains(AddEditProductModel.ConnectionType.WIRED.getName()));
		Assert.assertFalse(productControl.getTextByXpath(ProductDetailModel.INPUT_SPECIFICATION_PANEL).contains(AddEditProductModel.ConnectionType.BLUETOOTH.getName()));
		// 7. Click �Edit Version� link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 8. Select the connection type include �Wired�, �Bluetooth� and �USB�
		productControl.selectACheckbox(AddEditProductModel.WIRED_CHECKBOX);
		productControl.selectACheckbox(AddEditProductModel.BLUETOOTH_CHECKBOX);
		// 9. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: Verify that �Input Specification� section contains Lineout, Bluetooth and USB
		Assert.assertTrue(productControl.getTextByXpath(ProductDetailModel.INPUT_SPECIFICATION_PANEL).contains(AddEditProductModel.ConnectionType.USB.getName()));
		Assert.assertTrue(productControl.getTextByXpath(ProductDetailModel.INPUT_SPECIFICATION_PANEL).contains(AddEditProductModel.ConnectionType.WIRED.getName()));
		Assert.assertTrue(productControl.getTextByXpath(ProductDetailModel.INPUT_SPECIFICATION_PANEL).contains(AddEditProductModel.ConnectionType.BLUETOOTH.getName()));
		// 10. Click �Add New Variant� link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 11. Fill valid value into all required fields
		// 12. Click �Save� link
		Hashtable<String,String> variantData = TestData.variantData(false, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(),variantData);
		// VP: Verify that �Input Specification� section contains Lineout, Bluetooth and USB
		Assert.assertTrue(productControl.getTextByXpath(ProductDetailModel.INPUT_SPECIFICATION_PANEL).contains(AddEditProductModel.ConnectionType.USB.getName()));
		Assert.assertTrue(productControl.getTextByXpath(ProductDetailModel.INPUT_SPECIFICATION_PANEL).contains(AddEditProductModel.ConnectionType.WIRED.getName()));
		Assert.assertTrue(productControl.getTextByXpath(ProductDetailModel.INPUT_SPECIFICATION_PANEL).contains(AddEditProductModel.ConnectionType.BLUETOOTH.getName()));
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the connection type contains �USB�, Published Product Profile will include USB channel and route type will be AR_OUT_USB
	 */
	@Test
	public void TC040PAD_06(){
		productControl.addLog("ID : TC040PAD_06: Verify that the connection type contains �USB�, Published Product Profile will include USB channel and route type will be AR_OUT_USB");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click �Add Product� link
			4. Fill valid value into all required fields
			5. Select the connection type "USB" only
			6. Upload a USB only hpxtt tuning file
			7. Upload three catalog images
			8. Upload a marketing material file
			9. Click �Save� link
			VP: Verify that Product is saved successfully
			10. Publish above product
			11. Download �Published Product Profile� link
			VP: Verify that published profile include USB channel and route type will be AR_OUT_USB

		 */
		
		// 1. Log into DTS portal as a Partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click �Add Product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		// 5. Select the connection type "USB" only
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, true, true);
		dataProduct.remove("save");
		dataProduct.remove("wired");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		productControl.selectACheckbox(AddEditProductModel.USB_CHECKBOX);
		// 6. Upload a USB only hpxtt tuning file
		// 7. Upload three catalog images
		// 8. Upload a marketing material file
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT_USB.getName());
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, AddEditProductModel.TuningRatingOption.A.getName());
		// 9. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: Verify that Product is saved successfully
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 10. Publish above product
		loginControl.logout();
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.TUNING_RATING_A);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		productWf.approveMarketing();
		loginControl.logout();
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.click(ProductDetailModel.PUBLISH);
		// 11. Download �Published Product Profile� link
		String publish_profile = reportsControl.getPath(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE);
		Assert.assertTrue(productControl.checkPublishProfile(publish_profile, "usb", "AR_OUT_USB"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the connection type contains �USB�, Published Variant Profile will include USB channel and route type will be AR_OUT_USB
	 */
	@Test
	public void TC041PAVD_03(){
		productControl.addLog("ID : TC041PAVD_03: Verify that the connection type contains �USB�, Published Variant Profile will include USB channel and route type will be AR_OUT_USB");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click �Add Product� link
			4. Fill valid value into all required fields
			5. Select the connection type "USB" only
			6. Upload a USB only hpxtt tuning file
			7. Upload three catalog images
			8. Upload a marketing material file
			9. Click �Save� link
			VP: Verify that Product is saved successfully
			10. Publish above product
			11. Click �Add New Variant� link
			12. Fill valid value into all required fields
			13. Upload a USB only hpxtt tuning file
			14. Click �Save� link
			15. Publish above variant
			16. Download �Published Variant Profile� link
			VP: Verify that published profile include USB channel and route type will be AR_OUT_USB

		 */
		
		// 1. Log into DTS portal as a Partner user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click �Add Product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		// 5. Select the connection type "USB" only
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, true, true);
		dataProduct.remove("save");
		dataProduct.remove("wired");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		productControl.selectACheckbox(AddEditProductModel.USB_CHECKBOX);
		// 6. Upload a USB only hpxtt tuning file
		// 7. Upload three catalog images
		// 8. Upload a marketing material file
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT_USB.getName());
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, AddEditProductModel.TuningRatingOption.A.getName());
		// 9. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: Verify that Product is saved successfully
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 10. Publish above product
		productWf.approveTuning();
		productWf.approveMarketing();
		productControl.click(ProductDetailModel.PUBLISH);
		// 11. Click �Add New Variant� link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 12. Fill valid value into all required fields
		Hashtable<String,String> variantData = TestData.variantData(false, true, true);
		variantData.remove("save");
		productControl.addVariant(AddEditProductModel.getVariantHash(),variantData);
		// 13. Upload a USB only hpxtt tuning file
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_TUNNING);
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT_USB.getName());
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, AddEditProductModel.TuningRatingOption.A.getName());
		// 14. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		String variant_uuid = productControl.getTextByXpath(VariantInfo.TRACKING_ID);
		// 15. Publish above variant
		productWf.approveTuning();
		productWf.approveMarketing();
		productControl.click(ProductDetailModel.PUBLISH);
		loginControl.logout();
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		productControl.selectAVariantbyName(variantData.get("name"));
		// 16. Download �Published Variant Profile� link
//		String publish_profile = reportsControl.getPath(VariantInfo.PUBLISHED_VARIANT_PROFILE_LINK);
		Assert.assertTrue(productControl.downloadFile(VariantInfo.PUBLISHED_VARIANT_PROFILE_LINK));
		String publish_profile = System.getProperty("user.home") + "\\Downloads\\" + "profile_" + variant_uuid + ".dtscs";
		// VP: Verify that published profile include USB channel and route type will be AR_OUT_USB
		Assert.assertTrue(productControl.checkPublishProfile(publish_profile, "usb", "AR_OUT_USB"));
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the connection type contains �USB� only, New Tuning Template File in Product page will include invalid information
	 */
	@Test
	public void TC040PAD_07(){
		productControl.addLog("ID : TC040PAD_07: Verify that the connection type contains �USB� only, New Tuning Template File in Product page will include invalid information");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click �Add Product� link
			4. Fill valid value into all required fields
			5. Select the connection type "USB" only
			6. Click �Save� link
			VP: Verify that Product is saved successfully
			7. Click �New Tuning Template File� link
			VP: Verify that tuning template is downloaded successfully
			8. Open tuning template file
			VP: Verify that New Tuning Template File will include USB and other product information

		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click �Add Product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		// 5. Select the connection type "USB" only
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, true, true);
		dataProduct.remove("save");
		dataProduct.remove("wired");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		productControl.selectACheckbox(AddEditProductModel.USB_CHECKBOX);
		// 6. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		String UUID = productControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
		// VP: Verify that Product is saved successfully
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 7. Click �New Tuning Template File� link
		// VP: Verify that tuning template is downloaded successfully
		Boolean isDownload = productControl.downloadFile(ProductDetailModel.TUNING_TEMPLATE);
		Assert.assertTrue(isDownload);
		// 8. Open tuning template file
		// VP: Verify that New Tuning Template File will include USB and other product information
		//String brand_uuid = DbUtil.selectStatment("Select dts_id from brand where name = '" + dataProduct.get("brand") + "'");
		//Assert.assertFalse(productControl.checkInforTuningTemplate(UUID, dataProduct.get("type").toLowerCase(), brand_uuid.trim()));
		Assert.assertTrue(productControl.checkRouteTuningTemplate(UUID, "usb", "AR_OUT_USB"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the connection type contains �Wired�, �Bluetooth� and �USB�; New Tuning Template File in Product page will include valid product information
	 */
	@Test
	public void TC040PAD_08(){
		productControl.addLog("ID : TC040PAD_08: Verify that the connection type contains �Wired�, �Bluetooth� and �USB�; New Tuning Template File in Product page will include valid product information");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click �Add Product� link
			4. Fill valid value into all required fields
			5. Select �Wired�, �Bluetooth� and �USB� in connection type section
			6. Click �Save� link
			VP: Verify that Product is saved successfully
			7. Click �New Tuning Template File� link
			VP: Verify that tuning template is downloaded successfully
			8. Open tuning template file
			VP: Verify that New Tuning Template File will include Lineout, Bluetooth, USB and other product information

		 */
		
		// 1. Log into DTS portal as a Partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click �Add Product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		// 5. Select �Wired�, �Bluetooth� and �USB� in connection type section
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, true, true);
		dataProduct.remove("save");
		dataProduct.remove("wired");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		productControl.selectACheckbox(AddEditProductModel.WIRED_CHECKBOX);
		productControl.selectACheckbox(AddEditProductModel.BLUETOOTH_CHECKBOX);
		productControl.selectACheckbox(AddEditProductModel.USB_CHECKBOX);
		// 6. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: Verify that Product is saved successfully
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		String UUID = productControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
		// 7. Click �New Tuning Template File� link
		// VP: Verify that tuning template is downloaded successfully
//		String template_file = productControl.downloadTuningTemplate(UUID, ProductDetailModel.TUNING_TEMPLATE);
		Assert.assertTrue(productControl.downloadFile(ProductDetailModel.TUNING_TEMPLATE));
		// 8. Open tuning template file
		// VP: Verify that New Tuning Template File will include Lineout, Bluetooth, USB and other product information
		//String brand_uuid = DbUtil.selectStatment("Select dts_id from brand where name = '" + dataProduct.get("brand") + "'");
		//Assert.assertTrue(productControl.checkInforTuningTemplate(UUID, dataProduct.get("type").toLowerCase(), brand_uuid.trim()));
		Assert.assertTrue(productControl.checkRouteTuningTemplate(UUID, "usb", "AR_OUT_USB"));
		Assert.assertTrue(productControl.checkRouteTuningTemplate(UUID, "bluetooth0", "AR_OUT_BLUETOOTH"));
		Assert.assertTrue(productControl.checkRouteTuningTemplate(UUID, "line-out0", "AR_OUT_LINEOUT"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the connection type contains �Wired�, �Bluetooth� and �USB�; New Tuning Template File in Variant page will include valid product information
	 */
	@Test
	public void TC041PAVD_04(){
		productControl.addLog("ID : TC041PAVD_04: Verify that the connection type contains �Wired�, �Bluetooth� and �USB�; New Tuning Template File in Variant page will include valid product information");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click �Add Product� link
			4. Fill valid value into all required fields
			5. Select �Wired�, �Bluetooth� and �USB� in connection type section
			6. Click �Save� link
			VP: Verify that Product is saved successfully
			7. Click �Add New Variant� link
			8. Fill valid value into all required fields
			9. Click �Save� link
			VP: Verify that Variant is saved successfully
			10. Click �New Tuning Template File� link
			VP: Verify that tuning template is downloaded successfully
			11. Open tuning template file
			VP: Verify that New Tuning Template File will include Lineout, Bluetooth, USB and other variant information

		 */
		
		// 1. Log into DTS portal as a Partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click �Add Product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		// 5. Select the connection type "USB" only
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, true, true);
		dataProduct.remove("save");
		dataProduct.remove("wired");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		productControl.selectACheckbox(AddEditProductModel.WIRED_CHECKBOX);
		productControl.selectACheckbox(AddEditProductModel.BLUETOOTH_CHECKBOX);
		productControl.selectACheckbox(AddEditProductModel.USB_CHECKBOX);
		// 6. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: Verify that Product is saved successfully
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 7. Click �Add New Variant� link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 8. Fill valid value into all required fields
		Hashtable<String,String> variantData = TestData.variantData(false, true, true);
		variantData.remove("save");
		productControl.addVariant(AddEditProductModel.getVariantHash(),variantData);
		// 9. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		// VP: Verify that Variant is saved successfully
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.TITLE_NAME));
		String UUID = productControl.getTextByXpath(VariantInfo.TRACKING_ID);
		// 10. Click �New Tuning Template File� link
		// VP: Verify that tuning template is downloaded successfully
		Boolean isDownload = productControl.downloadFile(VariantInfo.TUNING_TEMPLATE);
		Assert.assertTrue(isDownload);
		// 11. Open tuning template file
		// VP: Verify that New Tuning Template File will include USB and other product information
		//String brand_uuid = DbUtil.selectStatment("Select dts_id from brand where name = '" + dataProduct.get("brand") + "'");
		//Assert.assertTrue(productControl.checkInforTuningTemplate(UUID, dataProduct.get("type").toLowerCase(), brand_uuid.trim()));
		Assert.assertTrue(productControl.checkRouteTuningTemplate(UUID, "usb", "AR_OUT_USB"));
		Assert.assertTrue(productControl.checkRouteTuningTemplate(UUID, "bluetooth0", "AR_OUT_BLUETOOTH"));
		Assert.assertTrue(productControl.checkRouteTuningTemplate(UUID, "line-out0", "AR_OUT_LINEOUT"));
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the Published Product Profile includes both Eagle Version 1.4 and 2.0
	 */
	@Test
	public void TC040PAD_09(){
		productControl.addLog("ID : TC040PAD_09: Verify that the Published Product Profile includes both Eagle Version 1.4 and 2.0");
		/*
			1. Log into DTS portal as a Partner super user
			2. Navigate to "Products� page
			3. Click �Add Product� link
			4. Fill valid value into all required fields
			5. Click �Save� link
			VP: Verify that Product is created successfully
			6. Click �Request Tuning Review� link
			7. Log out DTS portal
			8. Log into DTS portal as a DTS super user
			9. Navigate to "Products� page
			10. Select above Product
			11. Approve product tuning
			12. Approve product marketing
			13. Publish above product
			14. Log out DTS portal
			15. Log into DTS portal as a Partner super user
			16. Navigate to "Products� page
			17. Select above Product again
			18. Download �Published Product Profile� link
			VP: Verify that Published Product Profile includes both Eagle Version 1.4 and 2.0

		 */
		
		// 1. Log into DTS portal as a Partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click �Add Product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		// 5. Click �Save� link
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		// VP: Verify that Product is saved successfully
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 6. Click �Request Tuning Review� link
		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
		// 7. Log out DTS portal
		loginControl.logout();
		// 8. Log into DTS portal as a DTS super user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 9. Navigate to "Products� page
		productControl.click(PageHome.linkAccessories);
		// 10. Select above Product
		productControl.selectAccessorybyName(dataProduct.get("name"));
		// 11. Approve product tuning
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.TUNING_RATING_A);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		// 12. Approve product marketing
		productWf.approveMarketing();
		// 13. Publish above product
		productControl.click(ProductDetailModel.PUBLISH);
		// 14. Log out DTS portal
		loginControl.logout();
		// 15. Log into DTS portal as a Partner super user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 16. Navigate to "Products� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 17. Select above Product again
		productControl.selectAccessorybyName(dataProduct.get("name"));
		String UUID = productControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
		// 18. Download �Published Product Profile� link
		Assert.assertTrue(productControl.downloadFile(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE));
		// VP: Verify that Published Product Profile includes both Eagle Version 1.4 and 2.0
		Assert.assertTrue(productControl.isIncludeTwoEagleVersion(UUID));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the Published Variant Profile includes both Eagle Version 1.4 and 2.0
	 */
	@Test
	public void TC041PAVD_05(){
		productControl.addLog("ID : TC041PAVD_05: Verify that the Published Variant Profile includes both Eagle Version 1.4 and 2.0");
		/*
			1. Log into DTS portal as a Partner super user
			2. Navigate to "Products� page
			3. Click �Add Product� link
			4. Fill valid value into all required fields
			5. Click �Save� link
			VP: Verify that Product is created successfully
			6. Click �Add New Variant� link
			7. Fill valid value into all required fields
			8. Click �Save� link
			9. Log out DTS portal
			10. Log into DTS portal as a DTS super user
			11. Navigate to "Products� page
			12. Select above product
			13. Publish product
			14. Select above variant
			15. Publish variant
			16. Log out DTS portal
			17.  Log into DTS portal as a Partner super user
			18. Navigate to "Products� page
			19. Select above product
			20. Select above variant
			21. Download �Published Variant Profile� link
			VP: Verify that Published Variant Profile includes both Eagle Version 1.4 and 2.0

		 */
		
		// 1. Log into DTS portal as a Partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click �Add Product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		// 5. Click �Save� link
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
		// VP: Verify that Product is saved successfully
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 6. Click �Add New Variant� link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 7. Fill valid value into all required fields
		// 8. Click �Save� link
		Hashtable<String,String> dataVariant = TestData.variantData(true, true, true);
		productControl.addVariant(AddEditProductModel.getVariantHash(),dataVariant);
		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
		// 9. Log out DTS portal
		loginControl.logout();
		// 10. Log into DTS portal as a DTS super user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 11. Navigate to "Products� page
		productControl.click(PageHome.linkAccessories);
		// 12. Select above product
		productControl.selectAccessorybyName(dataProduct.get("name"));
		// 13. Publish product
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.TUNING_RATING_A);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		productWf.approveMarketing();
		productControl.click(ProductDetailModel.PUBLISH);
		// 14. Select above variant
		productControl.selectAVariantbyName(dataVariant.get("name"));
		// 15. Publish variant
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.TUNING_RATING_A);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		productWf.approveMarketing();
		productControl.click(VariantInfo.PUBLISH);
		// 16. Log out DTS portal
		loginControl.logout();
		// 17.  Log into DTS portal as a Partner super user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 18. Navigate to "Products� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 19. Select above product
		productControl.selectAccessorybyName(dataProduct.get("name"));
		// 20. Select above variant
		productControl.selectAVariantbyName(dataVariant.get("name"));
		// 21. Download �Published Variant Profile� link
		Assert.assertTrue(productControl.downloadFile(VariantInfo.PUBLISHED_VARIANT_PROFILE_LINK));
		// VP: Verify that Published Variant Profile includes both Eagle Version 1.4 and 2.0
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
}
