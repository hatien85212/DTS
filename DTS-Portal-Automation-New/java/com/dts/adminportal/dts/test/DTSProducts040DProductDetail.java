package com.dts.adminportal.dts.test;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PartnerVariantInfo;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.VariantInfo;
import com.dts.adminportal.util.TestData;

public class DTSProducts040DProductDetail extends BasePage{
	
	@Override
	protected void initData() {
	}	
	
	/*
	 * Verify that the product detail page displays properly after creating
	 */
	@Test
	public void TC040DAD_03(){
		productControl.addLog("ID : TC040DAD_03: Verify that the product detail page displays properly after creating");
		/*
		 	Pre-condition: User has "Add and manage products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			VP. Verify that the 051D product Edit Page is displayed
			4. Fill valid value into all fields but leaves the "EAN” and “UPC" empty
			5. Click "Save" link
			"VP: The Publishing Process, Model Actions and Model Variant module is displayed in 040D product Details page.
			VP: The 040D Products Details Page of new product is displayed and the ""EAN/UPC"" section displays “EAN:(None)"" and '""UPC:(None)""”
			VP: The 040D product Details page is displayed and the primary catalog image dislays in order from left to right: 250x250,500x500 and 1000x1000"
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click "Add product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getProductElementFieldIds()), true);
		// 4. Fill valid value into all fields but leaves the "EAN/UPC" empty
		// 5. Click "Save" link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, true);
		data.remove("ean");
		data.remove("upc");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		
		/*
		 * The Publishing Process, 
		 * Model Actions and 
		 * Model Variant module is displayed in 040D Accessory Details page.
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PUBLISHING_PROCESS));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.MODEL_ACTIONS));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.MODEL_VARIANTS));
		/*
		 * Verify that The 040D products Details Page of new product is displayed and the "EAN/UPC" section displays "EAN/UPC:(None)"
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.EAN), "(None)");
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.UPC), "(None)");
		/*
		 * Verify that The 040D Accessory Details page is displayed and the primary catalog image dislays in order from left to right: 160x160,290x290 and 664x664
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), data.get("name"));
		Assert.assertTrue(productControl.isImageDisplayLeftToRight(ProductDetailModel.IMAGE_CATALOG));
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the "Request Marketing Review" link is disabled when the "EAN/UPC" is empty although the Tuning status is approved
	 */
	@Test
	public void TC040DAD_05(){
		productControl.addLog("ID : TC040DAD_05: Verify that the 'Request Marketing Review' link is disabled when the 'EAN/UPC' is empty although the Tuning status is approved");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click "Add product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getProductElementFieldIds()), true);
		// 4. Fill valid value into all fields but leaves the "EAN/UPC" empty
		// 5. Upload Tuning and Marketing material successfully
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		data.remove("ean");
		data.remove("upc");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * VP: Verify that the 040D Products Details page of new product is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		/*
		 * VP: Verify that the Tuning approval status is changed to "Pending Partner Approval"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "PENDING PARTNER APPROVAL");
		// 7. Click "Approve Tuning" link from Step 1: Tuning Approval section
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		/*
		 * Verify that the Tuning Approval status is changed to "APPROVED" and the "Request Marketing Review" link in step 2: Marketing Approval is disabled and the error message "*EAN/UPC is required." is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "APPROVED");
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.REQUEST_MARKETING_REVIEW, "action"), "");
		Assert.assertTrue(productControl.checkMessageDisplay("* EAN/UPC is required."));
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that marketing material file could be download successfully by clicking on its item
	 */
	@Test
	public void TC040DAD_09(){
		productControl.addLog("ID : TC040DAD_09: Verify that marketing material file could be download successfully by clicking on its item");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click "Add product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getProductElementFieldIds()), true);
		// 4. Fill valid value into all fields
		// 5. Upload a file for marketing material successfully
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, true, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * VP: The 040D product Detail page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 7. Click on uploaded marketing material file
		Boolean isFileDownloaded = productControl.downloadFile(ProductDetailModel.MARKETING_DOWNLOAD_LINK);
		/*
		 * Verify that The marketing material file could be download successfully
		 */
		Assert.assertTrue(isFileDownloaded);
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the "Partner Actions" link is displayed in Step 1: Tuning Approval of Publishing Process
	 */
	@Test
	public void TC040DAD_10(){
		productControl.addLog("ID : TC040DAD_10: Verify that the 'Partner Actions' link is displayed in Step 1: Tuning Approval of Publishing Process");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click "Add Accessory" link
		productControl.click(ProductModel.ADD_PRODUCT);
		/*
		 * VP. Verify that the 051D Accessory Edit Page is displayed
		 */
		Assert.assertEquals(true, productControl.existsElement(AddEditProductModel.getProductElementFieldIds()));
		// 4. Fill valid value into all fields
		// 5. Upload a tuning file successfully
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * VP: The 040D Accessory Detail page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		/*
		 * Verify that The "Partner Actions" link is displayed in Step 1: Tuning Approval of Publishing Process.
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PARTNER_ACTIONS_STEP_1));
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the "Partner Actions" link is displayed in Step 2 : Marketing Approval of Publishing Process
	 */
	@Test
	public void TC040DAD_11(){
		productControl.addLog("ID : TC040DAD_11: Verify that the 'Partner Actions' link is displayed in Step 2 : Marketing Approval of Publishing Process");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click "Add product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertEquals(true, productControl.existsElement(AddEditProductModel.getProductElementFieldIds()));
		// 4. Fill valid value into all required fields
		// 5. Upload a  tuning file successfully
		// 6. Upload a marketing material file
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 8. Go through Step 1: Tuning Approval of Publishing Process
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		/*
		 * Verify that The "Partner Actions" link is displayed in Step 2: Marketing Approval of Publishing Process
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PARTNER_ACTIONS_STEP_2));
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the ï¿½Published Accessory Profileï¿½ link is displayed when the accessory is published
	 */
	@Test
	public void TC040DAD_12(){
		productControl.addLog("ID : TC040DAD_12: Verify that the ï¿½Published Accessory Profileï¿½ link is displayed when the accessory is published");
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
			8. Click ï¿½Saveï¿½ link
			9. Verify that the ï¿½Published Accessory Profileï¿½ link is not displayed in ï¿½Actionsï¿½ module
			10. Go through Step 1: Tuning Approval of Publishing Process
			11. Go through step 2: Marketing Approval state
			13. Click ï¿½Publishï¿½ button
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click "Add Product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		/*
		 * VP. Verify that the 051D Accessory Edit Page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getProductElementFieldIds()), true);
		// 4. Fill valid value into all fields
		// 5. Upload a  tuning file successfully
		// 6. Upload a marketing material file
		// 7. Upload three product primary catalog image successfully
		// 8. Click ï¿½Saveï¿½ link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 9. Verify that the ï¿½Published Accessory Profileï¿½ link is not displayed in ï¿½Actionsï¿½ module
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE));
		// 10. Go through Step 1: Tuning Approval of Publishing Process
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		// 11. Go through step 2: Marketing Approval state
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		// 12. Click ï¿½Publishï¿½ button
		productControl.click(ProductDetailModel.PUBLISH);
		/*
		 * Verify that the ï¿½Published Accessory Profileï¿½ link is displayed in ï¿½Actionsï¿½ module
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE));
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that user is able to download accessory profile successfully when clicking on the ï¿½Published Accessory Profileï¿½ link
	 */
	@Test
	public void TC040DAD_13(){
		productControl.addLog("ID : TC040DAD_13: Verify that user is able to download accessory profile successfully when clicking on the ï¿½Published Accessory Profileï¿½ link");
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
			8. Click ï¿½Saveï¿½ link
			VP. Verify that the ï¿½Published Accessory Profileï¿½ link is not displayed in ï¿½Actionsï¿½ module
			9. Go through Step 1: Tuning Approval of Publishing Process
			10. Go through step 2: Marketing Approval state
			11. Click ï¿½Publishï¿½ button
			VP. Verify that the ï¿½Published Accessory Profileï¿½ link is displayed in ï¿½Actionsï¿½ module
			12. Click on ï¿½Published Accessory Profileï¿½ link.
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click "Add Product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		/*
		 * VP. Verify that the 051D Accessory Edit Page is displayed
		 */
		Assert.assertEquals(true, productControl.existsElement(AddEditProductModel.getProductElementFieldIds()));
		// 4. Fill valid value into all fields
		// 5. Upload a tuning file successfully
		// 6. Upload a marketing material file
		// 7. Upload three product primary catalog image successfully
		// 8. Click ï¿½Saveï¿½ link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * Verify that the ï¿½Published Accessory Profileï¿½ link is not displayed in ï¿½Actionsï¿½ module
		 */
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE));
		// 9. Go through Step 1: Tuning Approval of Publishing Process
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		// 10. Go through step 2: Marketing Approval state
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		// 11. Click ï¿½Publishï¿½ button
		productControl.click(ProductDetailModel.PUBLISH);
		/*
		 * Verify that the ï¿½Published Accessory Profileï¿½ link is displayed in ï¿½Actionsï¿½ module
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE));
		// 12. Click on ï¿½Published Accessory Profileï¿½ link
		Boolean isDownloaded = productControl.downloadPublishedAccessoryProfile();
		/*
		 * Verify that the download window popup is displayed and the accessory profile is downloaded successfully
		 */
		Assert.assertTrue(isDownloaded);
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
	 }
	
	/*
	 * Verify that the Partner Tuning is unable to approve when the product Heaphone:X Tuning is not rated
	 */
	@Test
	public void TC040DAD_14(){
		productControl.addLog("ID : TC040DAD_14: Verify that the Partner Tuning is unable to approve when the product Heaphone:X Tuning is not rated");
		/*
			Pre-condition: User has "Approve product tunings" rights.
			1. Log into DTS portal as a Partner user successfully
			2. Navigate to "Products" page
			3. Click "Add Product" link
			VP. Verify that the 051D product Edit Page is displayed
			4. Fill valid value into all fields
			5. Upload a  tuning file successfully
			6. Click ï¿½Saveï¿½ link
			VP: Verify that the status of Step 1: Tuning Approval is ï¿½UNSUBMITTEDï¿½ and the ï¿½Request Tuning Reviewï¿½ link is displayed
			7. Click ï¿½Request Tuning Reviewï¿½ link
			8. Log out DTS portal
			9. Log into DTS portal as DTS user successfully
			10. Navigate to ï¿½Productsï¿½ page
			11. Select above product from products table
			VP: Verify that there is a notification message ï¿½Headphone:X Tuning Rating is requiredï¿½ displayed below ï¿½Approve Tuningï¿½ link of step 1: Tuning Approval
			12. Click ï¿½Approve Tuningï¿½ link
		 */
		// 1. Log into DTS portal as a Partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.SAVE_PRODUCT));
		// 4. Fill valid value into all fields
		// 5. Upload a  tuning file successfully
		// 6. Click ï¿½Saveï¿½ link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * VP: Verify that the status of Step 1: Tuning Approval is ï¿½UNSUBMITTEDï¿½ and the ï¿½Request Tuning Reviewï¿½ link is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "UNSUBMITTED");
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.REQUEST_TUNING));
		// 7. Click ï¿½Request Tuning Reviewï¿½ link
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		// 8. Log out DTS portal
		productControl.logout();
		// 9. Log into DTS portal as DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 10. Navigate to ï¿½Productsï¿½ page
		productControl.click(PageHome.linkAccessories);
		// 11. Select above product from products table
		productControl.selectAccessorybyName(data.get("name"));
		// VP: Verify that there is a notification message ï¿½Headphone:X Tuning Rating is requiredï¿½ displayed below ï¿½Approve Tuningï¿½ link of step 1: Tuning Approval
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_RATING_WARNING_PARTNER), "* Headphone:X Tuning Rating is required.");
		// 12. Click ï¿½Approve Tuningï¿½ link
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		/*
		 * Verify that the DTS user is unable to approve Partner Tuning file
		 */
		Assert.assertNotEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "APPROVED");
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.APPROVE_TUNING));
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the DTS Tuning is unable to approve when the product Heaphone:X Tuning is not rated
	 */
	@Test
	public void TC040DAD_15(){
		productControl.addLog("ID : TC040DAD_15: Verify that the DTS Tuning is unable to approve when the product Heaphone:X Tuning is not rated");
		/*
			Pre-condition: User has "Approve product tunings" rights.
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3. Click "Add Product" link
			VP. Verify that the 051D product Edit Page is displayed
			4. Fill valid value into all fields
			5. Upload a  tuning file successfully
			6. Click ï¿½Saveï¿½ link
			VP: Verify that the status of Step 1: Tuning Approval is ï¿½PENDING PARTNER APPROVALï¿½ and the ï¿½Partner Actionsï¿½ link is displayed
			7. Expend the ï¿½Partner Actionsï¿½ link
			VP: Verify that there is a notification message ï¿½Headphone:X Tuning Rating is requiredï¿½ displayed below ï¿½Approve Tuningï¿½ link of step 1: Tuning Approval
			8. Click ï¿½Approve Tuningï¿½ link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click "Add Product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getProductElementFieldIds()), true);
		// 4. Fill valid value into all fields
		// 5. Upload a  tuning file successfully
		// 6. Click ï¿½Saveï¿½ link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		data.put("add tunning", AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * VP: Verify that the status of Step 1: Tuning Approval is ï¿½PENDING PARTNER APPROVALï¿½ and the ï¿½Partner Actionsï¿½ link is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "PENDING PARTNER APPROVAL");
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PARTNER_ACTIONS_STEP_1));
		// 7. Expend the ï¿½Partner Actionsï¿½ link
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		/*
		 * VP: Verify that there is a notification message ï¿½Headphone:X Tuning Rating is requiredï¿½ displayed below ï¿½Approve Tuningï¿½ link of step 1: Tuning Approval
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_RATING_WARNING_DTS), "* Headphone:X Tuning Rating is required.");
		// 8. Click ï¿½Approve Tuningï¿½ link
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		/*
		 * Verify that the DTS user is unable to approve DTS Tuning file
		 */
		Assert.assertNotEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "APPROVED");
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.APPROVE_TUNING));
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the product is unable to publish the Headphone:X Tuning Rating is still ï¿½Undeterminedï¿½ in step 3: Publishing
	 */
	@Test
	public void TC040DAD_16(){
		productControl.addLog("ID : TC040DAD_16: Verify that the product is unable to publish the Headphone:X Tuning Rating is still ï¿½Undeterminedï¿½ in step 3: Publishing");
		/*
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3. Click "Add Product" link
			VP. Verify that the 051D product Edit Page is displayed
			4. Fill valid value into all fields
			5. Upload a tuning file successfully
			6. Leave the Headphone:X Tuning as ï¿½Undeterminedï¿½
			7. Click ï¿½Saveï¿½ link
			8. Log out DTS portal
			9. Log into DTS portal as a partner user successfully
			10. Navigate to ï¿½Productsï¿½ page
			11. Select above product from Products table
			12. Click ï¿½Approve Tuningï¿½ link
			13. Pass through step 2: Marketing Approval 
			VP: Verify that there is a notification message ï¿½* Pending DTS Headphone X Rating.ï¿½ displayed in step 3: Publishing for partner user side and the ï¿½PUBLISHï¿½ button is disabled.
			14. Log out DTS portal
			15. Log into DTS portal as a DTS user successfully
			16. Navigate to ï¿½Productsï¿½ page
			17. Select above product from Products table.
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click "Add Product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getProductElementFieldIds()), true);
		// 4. Fill valid value into all fields
		// 5. Upload a tuning file successfully
		// 6. Leave the Headphone:X Tuning as ï¿½Undeterminedï¿½
		// 7. Click ï¿½Saveï¿½ link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		data.remove("tuning rating");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 8. Log out DTS portal
		productControl.logout();
		// 9. Log into DTS portal as a partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 10. Navigate to ï¿½Productsï¿½ page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 11. Select above product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 12. Click ï¿½Approve Tuningï¿½ link
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		// 13. Pass through step 2: Marketing Approval 
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		// Log out DTS portal
		productControl.logout();
		// Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Select product above
		productControl.selectAccessorybyName(data.get("name"));
		// Approve marketing
		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		// Log out DTS portal
		productControl.logout();
		// Log in DTS portal as partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select product above
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that there is a notification message ï¿½* Pending DTS Headphone X Rating.ï¿½ displayed in step 3: Publishing for partner user side and the ï¿½PUBLISHï¿½ button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Pending DTS Headphone X Rating."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// 14. Log out DTS portal
		productControl.logout();
		// 15. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 16. Navigate to ï¿½Productsï¿½ page
		productControl.click(PageHome.linkAccessories);
		// 17. Select above product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that there is a notification message ï¿½* Headphone:X Tuning Rating is required.ï¿½ displayed in step 3: Publishing for DTS user side and the ï¿½PUBLISHï¿½ button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Headphone:X Tuning Rating is required."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that DTS user can publish product after changing ï¿½Headphone:X Ratingï¿½ value in  step 3: Publishing
	 */
	@Test
	public void TC040DAD_17(){
		productControl.addLog("ID : TC040DAD_17: Verify that DTS user can publish product after changing ï¿½Headphone:X Ratingï¿½ value in  step 3: Publishing");
		/*
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3. Click "Add Product" link
			VP. Verify that the 051D product Edit Page is displayed
			4. Fill valid value into all fields
			5. Upload a tuning file successfully
			6. Leave the Headphone:X Tuning as ï¿½Undeterminedï¿½
			7. Click ï¿½Saveï¿½ link
			8. Log out DTS portal
			9. Log into DTS portal as a partner user successfully
			10. Navigate to ï¿½Productsï¿½ page
			11. Select above product from Products table
			12. Click ï¿½Approve Tuningï¿½ link
			13. Pass through step 2: Marketing Approval 
			VP: Verify that there is a notification message ï¿½* Pending DTS Headphone X Rating.ï¿½ displayed in step 3: Publishing for partner user side and the ï¿½PUBLISHï¿½ button is disabled.
			14. Log out DTS portal
			15. Log into DTS portal as a DTS user successfully
			16. Navigate to ï¿½Productsï¿½ page
			17. Select above product from Products table.
			VP:  Verify that there is a notification message ï¿½* Headphone:X Tuning Rating is required.ï¿½ displayed in step 3: Publishing for DTS user side and the ï¿½PUBLISHï¿½ button is disabled.
			18. Click ï¿½Editï¿½ link
			19. Change the Headphone:X Tuning Rating value to another
			20. Click ï¿½Saveï¿½ link
			21. Click ï¿½PUBLISHï¿½ button
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click "Add Product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getProductElementFieldIds()), true);
		// 4. Fill valid value into all fields
		// 5. Upload a tuning file successfully
		// 6. Leave the Headphone:X Tuning as ï¿½Undeterminedï¿½
		// 7. Click ï¿½Saveï¿½ link
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		data.remove("tuning rating");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 8. Log out DTS portal
		productControl.logout();
		// 9. Log into DTS portal as a partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 10. Navigate to ï¿½Productsï¿½ page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 11. Select above product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 12. Click ï¿½Approve Tuningï¿½ link
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		// 13. Pass through step 2: Marketing Approval
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		// Log out DTS portal
		productControl.logout();
		// Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Select product above
		productControl.selectAccessorybyName(data.get("name"));
		// Approve marketing
		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		// Log out DTS portal
		productControl.logout();
		// Log in DTS portal as partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select product above
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that there is a notification message ï¿½* Pending DTS
		 * Headphone X Rating.ï¿½ displayed in step 3: Publishing for partner user
		 * side and the ï¿½PUBLISHï¿½ button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Pending DTS Headphone X Rating."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// 14. Log out DTS portal
		productControl.logout();
		// 15. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 16. Navigate to ï¿½Productsï¿½ page
		productControl.click(PageHome.linkAccessories);
		// 17. Select above product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that there is a notification message ï¿½* Headphone:X Tuning
		 * Rating is required.ï¿½ displayed in step 3: Publishing for DTS user
		 * side and the ï¿½PUBLISHï¿½ button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Headphone:X Tuning Rating is required."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// 18. Click ï¿½Editï¿½ link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 19. Change the Headphone:X Tuning Rating value to another
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, "A");
		// 20. Click ï¿½Saveï¿½ link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// 21. Click ï¿½PUBLISHï¿½ button
		productControl.click(ProductDetailModel.PUBLISH);
		/*
		 * Verify that the product is published successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PUBLISH_STATUS), "PUBLISHED");
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that partner user is unable to publish product until the DTS user changes ï¿½Headphone:X Ratingï¿½ value in  step 3: Publishing
	 */
	@Test
	public void TC040DAD_18(){
		productControl.addLog("ID : TC040DAD_18: Verify that partner user is unable to publish product until the DTS user changes ï¿½Headphone:X Ratingï¿½ value in  step 3: Publishing");
		/*
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3. Click "Add Product" link
			VP. Verify that the 051D product Edit Page is displayed
			4. Fill valid value into all fields
			5. Upload a tuning file successfully
			6. Leave the Headphone:X Tuning as ï¿½Undeterminedï¿½
			7. Click ï¿½Saveï¿½ link
			8. Log out DTS portal
			9. Log into DTS portal as a partner user successfully
			10. Navigate to ï¿½Productsï¿½ page
			11. Select above product from Products table
			12. Click ï¿½Approve Tuningï¿½ link
			13. Pass through step 2: Marketing Approval 
			VP: Verify that there is a notification message ï¿½* Pending DTS Headphone X Rating.ï¿½ displayed in step 3: Publishing for partner user side and the ï¿½PUBLISHï¿½ button is disabled.
			14. Log out DTS portal
			15. Log into DTS portal as a DTS user successfully
			16. Navigate to ï¿½Productsï¿½ page
			17. Select above product from Products table.
			VP:  Verify that there is a notification message ï¿½* Headphone:X Tuning Rating is required.ï¿½ displayed in step 3: Publishing for DTS user side and the ï¿½PUBLISHï¿½ button is disabled.
			18. Click ï¿½Editï¿½ link
			19. Change the Headphone:X Tuning Rating value to another
			20. Click ï¿½Saveï¿½ link
			VP: Verfy that the ï¿½PUBLISHï¿½ button is enabled.
			21. Log out DTS portal
			22. Execute steps from 9 to 11 again
			23. Click ï¿½PUBLISHï¿½ button
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click "Add Product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getProductElementFieldIds()), true);
		// 4. Fill valid value into all fields
		// 5. Upload a tuning file successfully
		// 6. Leave the Headphone:X Tuning as ï¿½Undeterminedï¿½
		// 7. Click ï¿½Saveï¿½ link
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		data.remove("tuning rating");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 8. Log out DTS portal
		productControl.logout();
		// 9. Log into DTS portal as a partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 10. Navigate to ï¿½Productsï¿½ page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 11. Select above product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 12. Click ï¿½Approve Tuningï¿½ link
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		// 13. Pass through step 2: Marketing Approval
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		// Log out DTS portal
		productControl.logout();
		// Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Select product above
		productControl.selectAccessorybyName(data.get("name"));
		// Approve marketing
		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		// Log out DTS portal
		productControl.logout();
		// Log in DTS portal as partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select product above
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that there is a notification message ï¿½* Pending DTS
		 * Headphone X Rating.ï¿½ displayed in step 3: Publishing for partner user
		 * side and the ï¿½PUBLISHï¿½ button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Pending DTS Headphone X Rating."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// 14. Log out DTS portal
		productControl.logout();
		// 15. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 16. Navigate to ï¿½Productsï¿½ page
		productControl.click(PageHome.linkAccessories);
		// 17. Select above product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that there is a notification message ï¿½* Headphone:X Tuning
		 * Rating is required.ï¿½ displayed in step 3: Publishing for DTS user
		 * side and the ï¿½PUBLISHï¿½ button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Headphone:X Tuning Rating is required."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"),"button disabled");
		// 18. Click ï¿½Editï¿½ link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 19. Change the Headphone:X Tuning Rating value to another
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, "A");
		// 20. Click ï¿½Saveï¿½ link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: Verfy that the ï¿½PUBLISHï¿½ button is enabled
		 */
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button button-warning");
		// 21. Log out DTS portal
		productControl.logout();
		// 22. Execute steps from 9 to 11 again
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to ï¿½Productsï¿½ page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select above product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 23. Click ï¿½PUBLISHï¿½ button
		productControl.click(ProductDetailModel.PUBLISH);
		/*
		 * Verify that the product is published successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PUBLISH_STATUS), "PUBLISHED");
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the correct 041D product Variant Detail page is displayed properly after adding new product variant
	 */
	@Test
	public void TC041DAVD_01(){
		productControl.addLog("ID : TC041DAVD_01: Verify that the correct 041D product Variant Detail page is displayed properly after adding new product variant");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select an product from Products table
		String productName = productControl.selectAnAccessory();
		/*
		 * VP. Verify that 040D product Detail page is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), productName);
		// 4. Click "Add New Variant" link in "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP. The 052D Model Variant Edit page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.VARIANT_TITLE));
		// 5. Fill valid value into all requires fields
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.variantData(false, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), data);
		/*
		 * Verify that The correct 041D product Variant Detail page is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TITLE_NAME), data.get("name"));
		Assert.assertEquals(productControl.existsElement(VariantInfo.getElementsInfo()), true);
	}
	
	/*
	 * Verify that the Primary Catalog Image section is displayed in order from left to right
	 */
	@Test
	public void TC041DAVD_04(){
		productControl.addLog("ID : TC041DAVD_04: Verify that the Primary Catalog Image section is displayed in order from left to right");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select an accessory from accessories table
		String productName = productControl.selectAnAccessory();
		/*
		 * VP. Verify that 040D Accessory Detail page is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), productName);
		// 4. Click "Add New Variant" link in "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP. The 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getVariantElementsInfo()), true);
		// 5. Fill valid value into all requires fields
		// 6. Upload three size for custom primary  catalog images
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.variantData(false, false, true);
		productControl.addVariant(AddEditProductModel.getVariantHash(), data);
		/*
		 * Verify that The 040D product Variant Details page is displayed and the primary catalog image dislays in order from left to right: 250x250,500x500 and 1000x1000
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TITLE_NAME), data.get("name"));
		Assert.assertTrue(productControl.isImageDisplayLeftToRight(VariantInfo.IMAGE_TABLE));
	}
	
	/*
	 * Verify that marketing material file could be download successfully by clicking on its item
	 */
	@Test
	public void TC041DAVD_07(){
		productControl.addLog("ID : TC041DAVD_07: Verify that marketing material file could be download successfully by clicking on its item");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select an product from Products table
		productControl.selectAnAccessory();
		/*
		 * VP. Verify that 040D product Detail page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 4. Click "Add New Variant" link in "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP. The 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getVariantElementsInfo()), true);
		// 5. Fill valid value into all requires fields
		// 6. Upload a file for marketing material successfully
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.variantData(false, true, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), data);
		/*
		 * VP: The 041D product Variant Detail page is displayed 
		 */
		Assert.assertEquals(productControl.existsElement(VariantInfo.getElementsInfo()), true);
		// 8. Click on uploaded marketing material file
		Boolean isFileDownloaded = productControl.downloadFile(data.get("add marketing"));
		/*
		 * Verify that The marketing material file could be download successfully
		 */
		Assert.assertTrue(isFileDownloaded);
	}
	
	/*
	 * Verify that the ï¿½Use Parentï¿½s Tuningï¿½ link is not displayed in 041D product Variant Detail page
	 */
	@Test
	public void TC041DAVD_08(){
		productControl.addLog("ID : TC041DAVD_08: Verify that the ï¿½Use Parentï¿½s Tuningï¿½ link is not displayed in 041D product Variant Detail page");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select an accessory from accessories table
		String productName = productControl.selectAnAccessory();
		/*
		 * VP. Verify that 040D product Detail page is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), productName);
		// 4. Click "Add New Variant" link in "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP. The 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getVariantElementsInfo()), true);
		// 5. Fill valid value into all requires fields without ticking on "Use Parent's Tuning" checkbox or upload a custom tuning
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.variantData(false, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), data);
		/*
		 * Verify that the ï¿½Use Parentï¿½s Tuningï¿½ link is not displayed in 041D Accessory Variant Detail page
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), "USING PARENT'S MODEL");
	}
	
	/*
	 * Verify that the "Partner Actions" link is displayed in Step 1: Tuning Approval of Publishing Process
	 */
	@Test
	public void TC041DAVD_10(){
		productControl.addLog("ID : TC041DAVD_10: Verify that the 'Partner Actions' link is displayed in Step 1: Tuning Approval of Publishing Process");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select an accessory from accessories table
		String productName = productControl.selectAnAccessory();
		/*
		 * VP. Verify that 040D Accessory Detail page is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), productName);
		// 4. Click "Add New Variant" link in "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP. The 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getVariantElementsInfo()), true);
		// 5. Fill valid value into all requires fields
		// 6. Upload a file for marketing material successfully
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.variantData(true, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), data);
		/*
		 * Verify that The "Partner Actions" link is displayed in Step 1: Tuning Approval of Publishing Process
		 */
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.PARTNER_ACTIONS_STEP_1));
	}
	
	/*
	 * Verify that the "Partner Actions" link is displayed in Step 2 : Marketing Approval of Publishing Process
	 */
	@Test
	public void TC041DAVD_11(){
		productControl.addLog("ID : TC041DAVD_11: Verify that the 'Partner Actions' link is displayed in Step 2 : Marketing Approval of Publishing Process");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select an product from Products table
		productControl.selectAnAccessory();
		/*
		 * VP. Verify that 040D product Detail page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 4. Click "Add New Variant" link in "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP. The 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getVariantElementsInfo()), true);
		// 5. Fill valid value into all requires fields
		// 6. Upload variant tuning file successfully
		// 7. Upload a file for marketing material successfully
		// 8. Click "Save" link
		Hashtable<String,String> data = TestData.variantData(true, true, true);
		productControl.addVariant(AddEditProductModel.getVariantHash(), data);
		/*
		 * VP: The 041D product Variant Detail page is displayed 
		 */
		Assert.assertEquals(productControl.existsElement(VariantInfo.getElementsInfo()), true);
		// 9. Go through Step 1: Tuning Approval of Publishing Process
		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		productControl.click(VariantInfo.APPROVE_TUNING);
		/*
		 * Verify that The "Partner Actions" link is displayed in Step 2: Marketing Approval of Publishing Process
		 */
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.PARTNER_ACTIONS_STEP_2));
	}
	
	/*
	 * Verify that the "Partner Actions" link is displayed in Step 2 : Marketing Approval of Publishing Process
	 */
	@Test
	public void TC041DAVD_12(){
		productControl.addLog("ID : TC041DAVD_12: Verify that the 'Partner Actions' link is displayed in Step 2 : Marketing Approval of Publishing Process");
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
			11. Click ï¿½Publishï¿½ button
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select an product from Products table
		String productName = productControl.selectAnAccessory();
		/*
		 * VP. Verify that 040D product Detail page is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), productName);
		// 4. Click "Add New Variant" link in "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP. The 052D Model Variant Edit page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.VARIANT_TITLE));
		// 5. Fill valid value into all requires fields
		// 6. Upload variant tuning file successfully
		// 7. Upload three size of product catalog images successfully
		// 8. Upload a file for marketing material successfully
		// 9. Click "Save" link
		Hashtable<String,String> data = TestData.variantData(true, true, true);
		productControl.addVariant(AddEditProductModel.getVariantHash(), data);
		/*
		 * VP: The 041D product Variant Detail page is displayed 
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TITLE_NAME), data.get("name"));
		// 10. Go through Step 1: Tuning Approval and Step 2: Marketing Approval of Publishing Process successfully
		// Approve tuning
		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		productControl.click(VariantInfo.APPROVE_TUNING);
		// Approve marketing
		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		productControl.click(VariantInfo.REQUEST_MARKETING_REVIEW);
		productControl.click(VariantInfo.APPROVE_MARKETING);
		/*
		 * VP: Verify that the Publish button is enabled in step 3 of Publishing Process
		 */
		Assert.assertEquals(productControl.getAtributeValue(VariantInfo.PUBLISH, "class"), "button button-warning");
		// 11. Click ï¿½Publishï¿½ button
		productControl.click(VariantInfo.PUBLISH);
		/*
		 * Verify that the product variant is published
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.PUBLISHED_STATUS), "Published");
	}
	
	/*
	 * Verify that the Partner Tuning is unable to approve when the product variant Heaphone:X Tuning is not rated
	 */
	@Test
	public void TC041DAVD_13(){
		productControl.addLog("ID : TC041DAVD_13: Verify that the Partner Tuning is unable to approve when the product variant Heaphone:X Tuning is not rated");
		/*
			Pre-condition: User has "Approve product tunings" rights.
			1. Log into DTS portal as a Partner user successfully
			2. Navigate to "Products" page
			3. Select a published product from products table
			4. Click ï¿½Add New Variantï¿½ link
			5. Fill valid value into all required fields
			6. Upload a custom tuning file successfully
			7. Click ï¿½Saveï¿½ link
			VP: Verify that the status of Step 1: Tuning Approval is ï¿½UNSUBMITTEDï¿½ and the ï¿½Request Tuning Reviewï¿½ link is displayed
			8. Click ï¿½Request Tuning Reviewï¿½ link
			9. Log out DTS portal
			10. Log into DTS portal as DTS user successfully
			11. Navigate to ï¿½Productsï¿½ page
			12. Select above product from products table
			13. Select above variant from variant list
			VP: Verify that there is a notification message ï¿½Headphone:X Tuning Rating is requiredï¿½ displayed below ï¿½Approve Tuningï¿½ link of step 1: Tuning Approval
			14. Click ï¿½Approve Tuningï¿½ link
		*/
		// 1. Log into DTS portal as a Partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select a published product from products table
		productControl.selectAnAccessory();
		String accessoryName = productControl.getTextByXpath(ProductDetailModel.TITLE_NAME);
		// 4. Click ï¿½Add New Variantï¿½ link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 5. Fill valid value into all required fields
		// 6. Upload a custom tuning file successfully
		// 7. Click ï¿½Saveï¿½ link
		Hashtable<String,String> data = TestData.variantData(true, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), data);
		/*
		 * VP: Verify that the status of Step 1: Tuning Approval is ï¿½UNSUBMITTEDï¿½ and the ï¿½Request Tuning Reviewï¿½ link is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), "UNSUBMITTED");
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.REQUEST_TUNING_REVIEW));
		// 8. Click ï¿½Request Tuning Reviewï¿½ link
		productControl.click(VariantInfo.REQUEST_TUNING_REVIEW);
		// 9. Log out DTS portal
		productControl.logout();
		// 10. Log into DTS portal as DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 11. Navigate to ï¿½Productsï¿½ page
		productControl.click(PageHome.linkAccessories);
		// 12. Select above product from products table
		productControl.selectAccessorybyName(accessoryName);
		// 13. Select above variant from variant list
		productControl.selectAVariantbyName(data.get("name"));
		/*
		 * VP: Verify that there is a notification message ï¿½Headphone:X Tuning Rating is requiredï¿½ displayed below ï¿½Approve Tuningï¿½ link of step 1: Tuning Approval
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_RATING_WARNING_PARTNER), "* Headphone:X Tuning Rating is required.");
		// 14. Click ï¿½Approve Tuningï¿½ link
		productControl.click(VariantInfo.APPROVE_TUNING);
		/*
		 * Verify that the DTS user is unable to approve Partner Tuning file
		 */
		Assert.assertNotEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), "APPROVED");
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.APPROVE_TUNING));
	}
	
	/*
	 * Verify that the DTS Tuning is able to approve without rating tuning when the Heaphone:X Tuning product variant use Parent tuning rating
	 */
	@Test
	public void TC041DAVD_14(){
		productControl.addLog("ID : TC041DAVD_14: Verify that the DTS Tuning is able to approve without rating tuning when the Heaphone:X Tuning product variant use Parent tuning rating");
		/*
			Pre-condition: User has "Approve product tunings" rights.
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3. Select a published product from products table
			4. Click ï¿½Add New Variantï¿½ link
			5. Fill valid value into all fields
			6. Upload a custom tuning file successfully
			7. Use parent headphone:X tuning rating
			8. Click ï¿½Saveï¿½ link
			VP: Verify that the status of Step 1: Tuning Approval is ï¿½PENDING PARTNER APPROVALï¿½ and the ï¿½Partner Actionsï¿½ link is displayed
			9. Expend the ï¿½Partner Actionsï¿½ link
			VP: Verify that the notification message ï¿½Headphone:X Tuning Rating is requiredï¿½ is not displayed below ï¿½Approve Tuningï¿½ link of step 1: Tuning Approval
			10. Click ï¿½Approve Tuningï¿½ link
		*/
		
		/*
		 * Pre-condition: Create a publish accessory
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create accessory
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * *************************************************************
		 */
		// 4. Click ï¿½Add New Variantï¿½ link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 5. Fill valid value into all fields
		// 6. Upload a custom tuning file successfully
		// 7. Use parent headphone:X tuning rating
		// 8. Click ï¿½Saveï¿½ link
		Hashtable<String,String> dataVariant = TestData.variantData(true, false, false);
		dataVariant.remove("tuning rating");
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		/*
		 * VP: Verify that the status of Step 1: Tuning Approval is ï¿½PENDING PARTNER APPROVALï¿½ and the ï¿½Partner Actionsï¿½ link is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), "PENDING PARTNER APPROVAL");
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.PARTNER_ACTIONS_STEP_1));
		// 9. Expend the ï¿½Partner Actionsï¿½ link
		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		/*
		 * VP: Verify that the notification message ï¿½Headphone:X Tuning Rating is requiredï¿½ is not displayed below ï¿½Approve Tuningï¿½ link of step 1: Tuning Approval
		 */
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.TUNING_RATING_WARNING_DTS));
		// 10. Click ï¿½Approve Tuningï¿½ link
		productControl.click(VariantInfo.APPROVE_TUNING);
		/*
		 * Verify that the DTS user is able to approve DTS Tuning file
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), "APPROVED");
		// Delete product
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the DTS Tuning is unable to approve when the product variant's Heaphone:X Tuning  is ï¿½Undeterminedï¿½
	 */
	@Test
	public void TC041DAVD_15(){
		productControl.addLog("ID : TC041DAVD_15: Verify that the DTS Tuning is unable to approve when the product variant's Heaphone:X Tuning  is ï¿½Undeterminedï¿½");
		/*
			Pre-condition: User has "Approve product tunings" rights.
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3. Select a published product from products table
			4. Click ï¿½Add New Variantï¿½ link
			5. Fill valid value into all fields
			6. Upload a custom tuning file successfully
			7. Change the HeadphoneX: Tuning rating to ï¿½Undeterminedï¿½
			8. Click ï¿½Saveï¿½ link
			VP: Verify that the status of Step 1: Tuning Approval is ï¿½PENDING PARTNER APPROVALï¿½ and the ï¿½Partner Actionsï¿½ link is displayed
			9. Expend the ï¿½Partner Actionsï¿½ link
			VP: Verify that there is a notification message ï¿½Headphone:X Tuning Rating is requiredï¿½ displayed below ï¿½Approve Tuningï¿½ link of step 1: Tuning Approval
			10. Click ï¿½Approve Tuningï¿½ link
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select a published product from products table
		productControl.selectAnAccessory();
		// 4. Click ï¿½Add New Variantï¿½ link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 5. Fill valid value into all fields
		// 6. Upload a custom tuning file successfully
		// 7. Change the HeadphoneX: Tuning rating to ï¿½Undeterminedï¿½
		// 8. Click ï¿½Saveï¿½ link
		Hashtable<String,String> data = TestData.variantData(true, false, false);
		data.put("tuning rating", "Undetermined");
		productControl.addVariant(AddEditProductModel.getVariantHash(), data);
		/*
		 * VP: Verify that the status of Step 1: Tuning Approval is ï¿½PENDING PARTNER APPROVALï¿½ and the ï¿½Partner Actionsï¿½ link is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), "PENDING PARTNER APPROVAL");
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.PARTNER_ACTIONS_STEP_1));
		// 9. Expend the ï¿½Partner Actionsï¿½ link
		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		/*
		 * VP: Verify that there is a notification message ï¿½Headphone:X Tuning Rating is requiredï¿½ displayed below ï¿½Approve Tuningï¿½ link of step 1: Tuning Approval
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_RATING_WARNING_DTS), "* Headphone:X Tuning Rating is required.");
		// 10. Click ï¿½Approve Tuningï¿½ link
		productControl.click(VariantInfo.APPROVE_TUNING);
		/*
		 * Verify that the DTS user is unable to approve DTS Tuning file
		 */
		Assert.assertNotEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), "APPROVED");
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.APPROVE_TUNING));
	}
	
	/*
	 * Verify that the product variant is unable to publish the Headphone:X Tuning Rating is still ï¿½Undeterminedï¿½ in step 3: Publishing
	 */
	@Test
	public void TC041DAVD_16(){
		productControl.addLog("ID : TC041DAVD_16: Verify that the product variant is unable to publish the Headphone:X Tuning Rating is still ï¿½Undeterminedï¿½ in step 3: Publishing");
		/*
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3.Select a published product from Products table
			VP. Verify that the 051D product Edit Page is displayed
			4. Click ï¿½Add New Variantï¿½ link
			5. Fill valid value into all fields
			6. Upload a custom tuning file successfully
			7. Leave the Headphone:X Tuning as ï¿½Undeterminedï¿½
			8. Click ï¿½Saveï¿½ link
			9. Log out DTS portal
			10. Log into DTS portal as a partner user successfully
			11. Navigate to ï¿½Productsï¿½ page
			12. Select above product from Products table
			13. Select above variant from variants list
			14. Click ï¿½Approve Tuningï¿½ link
			15. Pass through step 2: Marketing Approval 
			VP: Verify that there is a notification message ï¿½* Pending DTS Headphone X Rating.ï¿½ displayed in step 3: Publishing for partner user side and the ï¿½PUBLISHï¿½ button is disabled.
			16. Log out DTS portal
			17. Log into DTS portal as a DTS user successfully
			18. Navigate to ï¿½Productsï¿½ page
			19. Select above product from Products table.
			20. Select above variant from variants list
		*/
		/*
		 * Pre-condition: Create published product
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create published product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * *************************************************
		 */
		// 2. Navigate to "Products" page
		// 3.Select a published product from Products table
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 4. Click ï¿½Add New Variantï¿½ link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 5. Fill valid value into all fields
		// 6. Upload a custom tuning file successfully
		// 7. Leave the Headphone:X Tuning as ï¿½Undeterminedï¿½
		// 8. Click ï¿½Saveï¿½ link
		Hashtable<String, String> dataVariant = TestData.variantData(true, true, true);
		dataVariant.put("tuning rating", "Undetermined");
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		// 9. Log out DTS portal
		productControl.logout();
		// 10. Log into DTS portal as a partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 11. Navigate to ï¿½Productsï¿½ page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 12. Select above product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 13. Select above variant from variants list
		productControl.selectAVariantbyName(dataVariant.get("name"));
		// 14. Click ï¿½Approve Tuningï¿½ link
		productControl.click(PartnerVariantInfo.APPROVE_TUNING);
		// 15. Pass through step 2: Marketing Approval
		productControl.click(PartnerVariantInfo.REQUEST_MARKETING_REVIEW);
		// Log out DTS portal
		productControl.logout();
		// Log in DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Select product above
		productControl.selectAccessorybyName(data.get("name"));
		// Select variant above
		productControl.selectAVariantbyName(dataVariant.get("name"));
		// Approve marketing
		productControl.click(VariantInfo.APPROVE_MARKETING);
		// Log out DTS portal
		productControl.logout();
		// Log in DTS portal as DTS user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select product above
		productControl.selectAccessorybyName(data.get("name"));
		// Select variant above
		productControl.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that there is a notification message ï¿½* Pending DTS Headphone X Rating.ï¿½ displayed in step 3: Publishing for partner user side and the ï¿½PUBLISHï¿½ button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Pending DTS Headphone X Rating."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// 16. Log out DTS portal
		productControl.logout();
		// 17. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 18. Navigate to ï¿½Productsï¿½ page
		productControl.click(PageHome.linkAccessories);
		// 19. Select above product from Products table.
		productControl.selectAccessorybyName(data.get("name"));
		// 20. Select above variant from variants list
		productControl.selectAVariantbyName(dataVariant.get("name"));
		/*
		 *  Verify that there is a notification message ï¿½* Headphone:X Tuning Rating is required.ï¿½ displayed in step 3: Publishing for DTS user side and the ï¿½PUBLISHï¿½ button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Headphone:X Tuning Rating is required."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// Delete product
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that DTS user can publish product variant after changing ï¿½Headphone:X Ratingï¿½ value in  step 3: Publishing
	 */
	@Test
	public void TC041DAVD_17(){
		productControl.addLog("ID : TC041DAVD_17: Verify that DTS user can publish product variant after changing ï¿½Headphone:X Ratingï¿½ value in  step 3: Publishing");
		/*
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3. Select a published product from Products table
			VP. Verify that the 051D product Edit Page is displayed
			4. Click ï¿½Add New Variantï¿½ link
			5. Fill valid value into all required fields
			6. Upload a custom tuning file successfully
			7. Leave the Headphone:X Tuning as ï¿½Undeterminedï¿½
			8. Click ï¿½Saveï¿½ link
			9. Log out DTS portal
			10. Log into DTS portal as a partner user successfully
			11. Navigate to ï¿½Productsï¿½ page
			12. Select above product from Products table
			13. Select above variant from variants list
			14. Click ï¿½Approve Tuningï¿½ link
			15. Pass through step 2: Marketing Approval 
			VP: Verify that there is a notification message ï¿½* Pending DTS Headphone X Rating.ï¿½ displayed in step 3: Publishing for partner user side and the ï¿½PUBLISHï¿½ button is disabled.
			16. Log out DTS portal
			17. Log into DTS portal as a DTS user successfully
			18. Navigate to ï¿½Productsï¿½ page
			19. Select above product from Products table
			20. Select above variant from variants list
			VP:  Verify that there is a notification message ï¿½* Headphone:X Tuning Rating is required.ï¿½ displayed in step 3: Publishing for DTS user side and the ï¿½PUBLISHï¿½ button is disabled.
			21. Click ï¿½Editï¿½ link
			22. Change the Headphone:X Tuning Rating value to another
			23. Click ï¿½Saveï¿½ link
			24. Click ï¿½PUBLISHï¿½ button
		*/
		/*
		 * Pre-condition: Create published product
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create published product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * *************************************************
		 */
		// 2. Navigate to "Products" page
		// 3.Select a published product from Products table
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 4. Click ï¿½Add New Variantï¿½ link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 5. Fill valid value into all fields
		// 6. Upload a custom tuning file successfully
		// 7. Leave the Headphone:X Tuning as ï¿½Undeterminedï¿½
		// 8. Click ï¿½Saveï¿½ link
		Hashtable<String, String> dataVariant = TestData.variantData(true, true, true);
		dataVariant.put("tuning rating", "Undetermined");
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		// 9. Log out DTS portal
		productControl.logout();
		// 10. Log into DTS portal as a partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 11. Navigate to ï¿½Productsï¿½ page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 12. Select above product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 13. Select above variant from variants list
		productControl.selectAVariantbyName(dataVariant.get("name"));
		// 14. Click ï¿½Approve Tuningï¿½ link
		productControl.click(PartnerVariantInfo.APPROVE_TUNING);
		// 15. Pass through step 2: Marketing Approval
		productControl.click(PartnerVariantInfo.REQUEST_MARKETING_REVIEW);
		// Log out DTS portal
		productControl.logout();
		// Log in DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Select product above
		productControl.selectAccessorybyName(data.get("name"));
		// Select variant above
		productControl.selectAVariantbyName(dataVariant.get("name"));
		// Approve marketing
		productControl.click(VariantInfo.APPROVE_MARKETING);
		// Log out DTS portal
		productControl.logout();
		// Log in DTS portal as DTS user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select product above
		productControl.selectAccessorybyName(data.get("name"));
		// Select variant above
		productControl.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that there is a notification message ï¿½* Pending DTS Headphone X Rating.ï¿½ displayed in step 3: Publishing for partner user side and the ï¿½PUBLISHï¿½ button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Pending DTS Headphone X Rating."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// 16. Log out DTS portal
		productControl.logout();
		// 17. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 18. Navigate to ï¿½Productsï¿½ page
		productControl.click(PageHome.linkAccessories);
		// 19. Select above product from Products table.
		productControl.selectAccessorybyName(data.get("name"));
		// 20. Select above variant from variants list
		productControl.selectAVariantbyName(dataVariant.get("name"));
		/*
		 *  Verify that there is a notification message ï¿½* Headphone:X Tuning Rating is required.ï¿½ displayed in step 3: Publishing for DTS user side and the ï¿½PUBLISHï¿½ button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Headphone:X Tuning Rating is required."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// 21. Click ï¿½Editï¿½ link
		productControl.click(VariantInfo.EDIT_VARIANT);
		// 22. Change the Headphone:X Tuning Rating value to another
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, "A");
		// 23. Click ï¿½Saveï¿½ link
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		// 24. Click ï¿½PUBLISHï¿½ button
		productControl.click(VariantInfo.PUBLISH);
		/*
		 * Verify that the product is published successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.PUBLISHED_STATUS), "Published");
		// Delete product
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that partner user is unable to publish product variant until the DTS user changes ï¿½Headphone:X Ratingï¿½ value in  step 3: Publishing
	 */
	@Test
	public void TC041DAVD_18(){
		productControl.addLog("ID : TC041DAVD_18: Verify that partner user is unable to publish product variant until the DTS user changes ï¿½Headphone:X Ratingï¿½ value in  step 3: Publishing");
		/*
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3. Select a published product from Products table
			VP. Verify that the 051D product Edit Page is displayed
			4. Click ï¿½Add New Variantï¿½ link
			5. Fill valid value into all required fields
			6. Upload a custom tuning file successfully
			7. Leave the Headphone:X Tuning as ï¿½Undeterminedï¿½
			8. Click ï¿½Saveï¿½ link
			9. Log out DTS portal
			10. Log into DTS portal as a partner user successfully
			11. Navigate to ï¿½Productsï¿½ page
			12. Select above product from Products table
			13. Select above variant from variants list
			14. Click ï¿½Approve Tuningï¿½ link
			15. Pass through step 2: Marketing Approval 
			VP: Verify that there is a notification message ï¿½* Pending DTS Headphone X Rating.ï¿½ displayed in step 3: Publishing for partner user side and the ï¿½PUBLISHï¿½ button is disabled.
			16. Log out DTS portal
			17. Log into DTS portal as a DTS user successfully
			18. Navigate to ï¿½Productsï¿½ page
			19. Select above product from Products table.
			20. Select above variant from variants list
			VP:  Verify that there is a notification message ï¿½* Headphone:X Tuning Rating is required.ï¿½ displayed in step 3: Publishing for DTS user side and the ï¿½PUBLISHï¿½ button is disabled.
			21. Click ï¿½Editï¿½ link
			22. Change the Headphone:X Tuning Rating value to another
			23. Click ï¿½Saveï¿½ link
			VP: Verfy that the ï¿½PUBLISHï¿½ button is enabled.
			24. Log out DTS portal
			25. Execute steps from 10 to 13 again
			26. Click ï¿½PUBLISHï¿½ button
		*/
		/*
		 * Pre-condition: Create published product
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create published product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * *************************************************
		 */
		// 2. Navigate to "Products" page
		// 3.Select a published product from Products table
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 4. Click ï¿½Add New Variantï¿½ link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 5. Fill valid value into all fields
		// 6. Upload a custom tuning file successfully
		// 7. Leave the Headphone:X Tuning as ï¿½Undeterminedï¿½
		// 8. Click ï¿½Saveï¿½ link
		Hashtable<String, String> dataVariant = TestData.variantData(true, true, true);
		dataVariant.put("tuning rating", "Undetermined");
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		// 9. Log out DTS portal
		productControl.logout();
		// 10. Log into DTS portal as a partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 11. Navigate to ï¿½Productsï¿½ page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 12. Select above product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 13. Select above variant from variants list
		productControl.selectAVariantbyName(dataVariant.get("name"));
		// 14. Click ï¿½Approve Tuningï¿½ link
		productControl.click(PartnerVariantInfo.APPROVE_TUNING);
		// 15. Pass through step 2: Marketing Approval
		productControl.click(PartnerVariantInfo.REQUEST_MARKETING_REVIEW);
		// Log out DTS portal
		productControl.logout();
		// Log in DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Select product above
		productControl.selectAccessorybyName(data.get("name"));
		// Select variant above
		productControl.selectAVariantbyName(dataVariant.get("name"));
		// Approve marketing
		productControl.click(VariantInfo.APPROVE_MARKETING);
		// Log out DTS portal
		productControl.logout();
		// Log in DTS portal as DTS user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select product above
		productControl.selectAccessorybyName(data.get("name"));
		// Select variant above
		productControl.selectAVariantbyName(dataVariant.get("name"));
		/*
		 * VP: Verify that there is a notification message ï¿½* Pending DTS Headphone X Rating.ï¿½ displayed in step 3: Publishing for partner user side and the ï¿½PUBLISHï¿½ button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Pending DTS Headphone X Rating."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// 16. Log out DTS portal
		productControl.logout();
		// 17. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 18. Navigate to ï¿½Productsï¿½ page
		productControl.click(PageHome.linkAccessories);
		// 19. Select above product from Products table.
		productControl.selectAccessorybyName(data.get("name"));
		// 20. Select above variant from variants list
		productControl.selectAVariantbyName(dataVariant.get("name"));
		/*
		 *  Verify that there is a notification message ï¿½* Headphone:X Tuning Rating is required.ï¿½ displayed in step 3: Publishing for DTS user side and the ï¿½PUBLISHï¿½ button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Headphone:X Tuning Rating is required."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// 21. Click ï¿½Editï¿½ link
		productControl.click(VariantInfo.EDIT_VARIANT);
		// 22. Change the Headphone:X Tuning Rating value to another
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, "A");
		// 23. Click ï¿½Saveï¿½ link
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * VP: Verify that the ï¿½PUBLISHï¿½ button is enabled.
		 */
		Assert.assertEquals(productControl.getAtributeValue(VariantInfo.PUBLISH, "class"), "button button-warning");
		// 24. Log out DTS portal
		productControl.logout();
		// 25. Execute steps from 10 to 13 again
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select product above
		productControl.selectAccessorybyName(data.get("name"));
		// Select variant above
		productControl.selectAVariantbyName(dataVariant.get("name"));
		// 26. Click ï¿½PUBLISHï¿½ button
		productControl.click(PartnerVariantInfo.PUBLISH);
		/*
		 * Verify that the product is published successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(PartnerVariantInfo.PUBLISHED_STATUS), "Published");
		// Delete product
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the "Request Marketing Review" link is disabled when the "EAN/UPC" is empty although the Tuning status is approved
	 */
	@Test
	public void TC041DAVD_19(){
		productControl.addLog("ID : TC041DAVD_19: Verify that the 'Request Marketing Review' link is disabled when the 'EAN/UPC' is empty although the Tuning status is approved");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create published product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		/*
		 * *************************************************
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select a published product from Products table
		productControl.selectAccessorybyName(dataProduct.get("name"));
		// 4. Click "Add New Variant" link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.VARIANT_TITLE));
		// 5. Fill valid value into all fields but leaves the "EAN/UPC" empty
		// 6. Upload Tuning and Marketing material successfully
		// 7. Click "Save" link
		Hashtable<String,String> dataVariant = TestData.variantData(true, true, true);
		dataVariant.remove("ean");
		dataVariant.remove("upc");
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		// 8. Click "Approve Tuning" link from Step 1: Tuning Approval section
		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		productControl.click(VariantInfo.APPROVE_TUNING);
		/*
		 * Verify that the Tuning Approval status is changed to "APPROVED" and
		 * the "Request Marketing Review" link in step 2: Marketing Approval is
		 * disabled and the error message "*EAN/UPC is required." is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), "APPROVED");
		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		productControl.click(VariantInfo.REQUEST_MARKETING_REVIEW);
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.MARKETING_STATUS), "UNSUBMITTED");
		Assert.assertTrue(productControl.checkMessageDisplay("* EAN/UPC is required."));
		// Delete product
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that user is able to download product variant profile successfully when clicking on the “Published Variant Profile” link.
	 */
	@Test
	public void TC041DAVD_20(){
		productControl.addLog("ID : TC041DAVD_20: Verify that user is able to download product variant "
				+ "profile successfully when clicking on the “Published Variant Profile” link.");
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
			10. Click on “Published Variant Profile” link.
			VP: Verify that the download window popup is displayed and the product variant profile is downloaded successfully.
		*/
		/*
		 * PreCondition: Create new published product
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		// Create published product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndPublish(dataProduct);
		/*
		 * ***********************************************************
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select a published product from Products table
		productControl.selectAccessorybyName(dataProduct.get("name"));
		// 4. Click "Add New Variant" link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.VARIANT_TITLE));
		// 5. Fill valid value into all required fields
		// 6. Upload all necessary file
		// 7. Click save link
		Hashtable<String,String> dataVariant = TestData.variantData(false, true, true);
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		/*
		 * VP: The 041D product Variant Detail page displays and the ï¿½Published Variant Profileï¿½ link is not displayed in ï¿½Actionsï¿½ module
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TITLE_NAME), dataVariant.get("name"));
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.PUBLISHED_VARIANT_PROFILE_LINK));
		// 8. Pass through two step of Publishing Process
		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		productControl.click(VariantInfo.REQUEST_MARKETING_REVIEW);
		productControl.click(VariantInfo.APPROVE_MARKETING);
		// 9. Click ï¿½Publishï¿½ button
		productControl.click(VariantInfo.PUBLISH);
		/*
		 * Verify that the product variant is published successfully and the ï¿½Published Variant Profileï¿½ link is displayed in ï¿½Actionsï¿½ module
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.PUBLISHED_STATUS), "Published");
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.PUBLISHED_VARIANT_PROFILE_LINK));
		// 10. Click on ï¿½Published Variant Profileï¿½ link
		boolean isDownloaded = productControl.downloadPublishedAccessoryProfile();
		/*
		 * Verify that the download window popup is displayed and the product variant profile is downloaded successfully
		 */
		Assert.assertTrue(isDownloaded);
		// Delete product
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
}
