package com.dts.adminportal.dts.test;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PartnerVariantInfo;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.VariantInfo;
import com.dts.adminportal.util.DbUtil;
import com.dts.adminportal.util.TestData;

public class DTSProducts040DProductDetail extends BasePage{
	
	@Override
	protected void initData() {
	}	
	
	/*
	 * Verify that the product detail page displays properly after creating
	 */
	@Test
	public void TC040DAD_03() throws NoSuchMethodException, SecurityException{
		productControl.addLog("ID : TC040DAD_03: Verify that the product detail page displays properly after creating");
		/*
		 	Pre-condition: User has "Add and manage products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			VP. Verify that the 051D product Edit Page is displayed
			4. Fill valid value into all fields but leaves the "EAN� and �UPC" empty
			5. Click "Save" link
			"VP: The Publishing Process, Model Actions and Model Variant module is displayed in 040D product Details page.
			VP: The 040D Products Details Page of new product is displayed and the ""EAN/UPC"" section displays �EAN:(None)"" and '""UPC:(None)""�
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
//		DTSProducts040DProductDetail cl = new DTSProducts040DProductDetail();
//		Class c = cl.getClass();
//		String TCname = productControl.nameBefore(c,"TC040DAD_03");
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
		productWf.approveTuning();
		/*
		 * Verify that the Tuning Approval status is changed to "APPROVED" and the "Request Marketing Review" link in step 2: Marketing Approval is disabled and the error message "*EAN/UPC is required." is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS),ProductDetailModel.ProductStatus.APPROVED.getName());
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.REQUEST_MARKETING_REVIEW, "action"), "");
		Assert.assertTrue(productControl.checkMessageDisplay("* EAN/UPC is required."));
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that user is able to download the Certification Badge after the tuning file is approved
	 */
	@Test
	public void TC040DAD_07(){
		productControl.addLog("ID : TC040DAD_07:Verify that user is able to download the Certification Badge after the tuning file is approved");
		
		/*1. Log into DTS portal as a DTS user
		2. Navigate to "Products" page
		3. Click "Add product" link
		4. Fill valid 
		5. Upload
		6. Click "Save" link
		7. Approve the uploaded tuning file successfully
		VP: The  tuning status is changed to "APPROVED" and tuning action link is "Download Certification Badges"
		8. Expand the �Partner Actions� link
		9. Click on the "Download Certification Badge" link
		VP: DTS user is able to download the Certification Badge sucessfully
		*/
		
		//1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		//3. Click "Add product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		//4. Fill valid required fields
		//5. Upload tuning file successfully
		//6. Click "Save" link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),data);
		//7. Approve the uploaded tuning file successfully
		productWf.approveTuning();
		/*
		 * VP: The  tuning status is changed to "APPROVED" and tuning action link is "Download Certification Badges"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS),ProductDetailModel.ProductStatus.APPROVED.getName());
		
		//8. Expand the �Partner Actions� link
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.DOWNLOAD_CERTIFICATE));
		//9. Click on the "Download Certification Badge" link
		boolean isDownloaded = productControl.downloadFile(ProductDetailModel.DOWNLOAD_CERTIFICATE);
		/*
		 * VP: DTS user is able to download the Certification Badge sucessfully
		 */
		Assert.assertTrue(isDownloaded);
		
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
		productWf.approveTuning();
		/*
		 * Verify that The "Partner Actions" link is displayed in Step 2: Marketing Approval of Publishing Process
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PARTNER_ACTIONS_STEP_2));
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the �Published Accessory Profile� link is displayed when the accessory is published
	 */
	@Test
	public void TC040DAD_12(){
		productControl.addLog("ID : TC040DAD_12: Verify that the �Published Accessory Profile� link is displayed when the accessory is published");
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
			8. Click �Save� link
			9. Verify that the �Published Accessory Profile� link is not displayed in �Actions� module
			10. Go through Step 1: Tuning Approval of Publishing Process
			11. Go through step 2: Marketing Approval state
			13. Click �Publish� button
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
		// 8. Click �Save� link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 9. Verify that the �Published Accessory Profile� link is not displayed in �Actions� module
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE));
		// 10. Go through Step 1: Tuning Approval of Publishing Process
		productWf.approveTuning();
		// 11. Go through step 2: Marketing Approval state
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
//		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
//		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		productWf.approveMarketing();
		// 12. Click �Publish� button
		productControl.click(ProductDetailModel.PUBLISH);
		/*
		 * Verify that the �Published Accessory Profile� link is displayed in �Actions� module
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE));
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that user is able to download accessory profile successfully when clicking on the �Published Accessory Profile� link
	 */
	@Test
	public void TC040DAD_13(){
		productControl.addLog("ID : TC040DAD_13: Verify that user is able to download accessory profile successfully when clicking on the �Published Accessory Profile� link");
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
			8. Click �Save� link
			VP. Verify that the �Published Accessory Profile� link is not displayed in �Actions� module
			9. Go through Step 1: Tuning Approval of Publishing Process
			10. Go through step 2: Marketing Approval state
			11. Click �Publish� button
			VP. Verify that the �Published Accessory Profile� link is displayed in �Actions� module
			12. Click on �Published Accessory Profile� link.
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
		// 8. Click �Save� link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * Verify that the �Published Accessory Profile� link is not displayed in �Actions� module
		 */
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE));
		// 9. Go through Step 1: Tuning Approval of Publishing Process
		productWf.approveTuning();
		// 10. Go through step 2: Marketing Approval state
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
//		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
//		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		productWf.approveMarketing();
		// 11. Click �Publish� button
		productControl.click(ProductDetailModel.PUBLISH);
		/*
		 * Verify that the �Published Accessory Profile� link is displayed in �Actions� module
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE));
		// 12. Click on �Published Accessory Profile� link
		Boolean isDownloaded = productControl.downloadFile(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE);
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
			6. Click �Save� link
			VP: Verify that the status of Step 1: Tuning Approval is �UNSUBMITTED� and the �Request Tuning Review� link is displayed
			7. Click �Request Tuning Review� link
			8. Log out DTS portal
			9. Log into DTS portal as DTS user successfully
			10. Navigate to �Products� page
			11. Select above product from products table
			VP: Verify that there is a notification message �Headphone:X Tuning Rating is required� displayed below �Approve Tuning� link of step 1: Tuning Approval
			12. Click �Approve Tuning� link
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
		// 6. Click �Save� link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * VP: Verify that the status of Step 1: Tuning Approval is �UNSUBMITTED� and the �Request Tuning Review� link is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "UNSUBMITTED");
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.REQUEST_TUNING));
		// 7. Click �Request Tuning Review� link
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		// 8. Log out DTS portal
		productControl.logout();
		// 9. Log into DTS portal as DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 10. Navigate to �Products� page
		productControl.click(PageHome.linkAccessories);
		// 11. Select above product from products table
		productControl.selectAccessorybyName(data.get("name"));
		// VP: Verify that there is a notification message �Headphone:X Tuning Rating is required� displayed below �Approve Tuning� link of step 1: Tuning Approval
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_RATING_WARNING_PARTNER), "* Headphone:X Tuning Rating is required.");
		// 12. Click �Approve Tuning� link
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		/*
		 * Verify that the DTS user is unable to approve Partner Tuning file
		 */
		Assert.assertNotEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS),ProductDetailModel.ProductStatus.APPROVED.getName());
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
			6. Click �Save� link
			VP: Verify that the status of Step 1: Tuning Approval is �PENDING PARTNER APPROVAL� and the �Partner Actions� link is displayed
			7. Expend the �Partner Actions� link
			VP: Verify that there is a notification message �Headphone:X Tuning Rating is required� displayed below �Approve Tuning� link of step 1: Tuning Approval
			8. Click �Approve Tuning� link
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
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		data.remove("save");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 5. Upload a  tuning file successfully
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName());
		// 6. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: Verify that the status of Step 1: Tuning Approval is �PENDING PARTNER APPROVAL� and the �Partner Actions� link is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS),ProductDetailModel.ProductStatus.PENDING_PARTNER_APPROVAL.getName());
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PARTNER_ACTIONS_STEP_1));
		// 7. Expend the �Partner Actions� link
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		/*
		 * VP: Verify that there is a notification message �Headphone:X Tuning Rating is required� displayed below �Approve Tuning� link of step 1: Tuning Approval
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_RATING_WARNING_DTS),ProductDetailModel.RATING_ERROR_MESS);
		// 8. Click �Approve Tuning� link
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		/*
		 * Verify that the DTS user is unable to approve DTS Tuning file
		 */
		Assert.assertNotEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS),ProductDetailModel.ProductStatus.APPROVED.getName());
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.APPROVE_TUNING));
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the product is unable to publish the Headphone:X Tuning Rating is still �Undetermined� in step 3: Publishing
	 */
	@Test
	public void TC040DAD_16(){
		productControl.addLog("ID : TC040DAD_16: Verify that the product is unable to publish the Headphone:X Tuning Rating is still �Undetermined� in step 3: Publishing");
		/*
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3. Click "Add Product" link
			VP. Verify that the 051D product Edit Page is displayed
			4. Fill valid value into all fields
			5. Upload a tuning file successfully
			6. Leave the Headphone:X Tuning as �Undetermined�
			7. Click �Save� link
			8. Log out DTS portal
			9. Log into DTS portal as a partner user successfully
			10. Navigate to �Products� page
			11. Select above product from Products table
			12. Click �Approve Tuning� link
			13. Pass through step 2: Marketing Approval 
			VP: Verify that there is a notification message �* Pending DTS Headphone X Rating.� displayed in step 3: Publishing for partner user side and the �PUBLISH� button is disabled.
			14. Log out DTS portal
			15. Log into DTS portal as a DTS user successfully
			16. Navigate to �Products� page
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
		// 6. Leave the Headphone:X Tuning as �Undetermined�
		// 7. Click �Save� link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		data.remove("tuning rating");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 8. Log out DTS portal
		productControl.logout();
		// 9. Log into DTS portal as a partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 10. Navigate to �Products� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 11. Select above product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 12. Click �Approve Tuning� link
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
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
		productControl.click(ProductDetailModel.REQUEST_MARKETING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		// Log out DTS portal
		productControl.logout();
		// Log in DTS portal as partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select product above
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that there is a notification message �* Pending DTS Headphone X Rating.� displayed in step 3: Publishing for partner user side and the �PUBLISH� button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Pending DTS Headphone X Rating."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// 14. Log out DTS portal
		productControl.logout();
		// 15. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 16. Navigate to �Products� page
		productControl.click(PageHome.linkAccessories);
		// 17. Select above product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that there is a notification message �* Headphone:X Tuning Rating is required.� displayed in step 3: Publishing for DTS user side and the �PUBLISH� button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Headphone:X Tuning Rating is required."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that DTS user can publish product after changing �Headphone:X Rating� value in  step 3: Publishing
	 */
	@Test
	public void TC040DAD_17(){
		productControl.addLog("ID : TC040DAD_17: Verify that DTS user can publish product after changing �Headphone:X Rating� value in  step 3: Publishing");
		/*
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3. Click "Add Product" link
			VP. Verify that the 051D product Edit Page is displayed
			4. Fill valid value into all fields
			5. Upload a tuning file successfully
			6. Leave the Headphone:X Tuning as �Undetermined�
			7. Click �Save� link
			8. Log out DTS portal
			9. Log into DTS portal as a partner user successfully
			10. Navigate to �Products� page
			11. Select above product from Products table
			12. Click �Approve Tuning� link
			13. Pass through step 2: Marketing Approval 
			VP: Verify that there is a notification message �* Pending DTS Headphone X Rating.� displayed in step 3: Publishing for partner user side and the �PUBLISH� button is disabled.
			14. Log out DTS portal
			15. Log into DTS portal as a DTS user successfully
			16. Navigate to �Products� page
			17. Select above product from Products table.
			VP:  Verify that there is a notification message �* Headphone:X Tuning Rating is required.� displayed in step 3: Publishing for DTS user side and the �PUBLISH� button is disabled.
			18. Click �Edit� link
			19. Change the Headphone:X Tuning Rating value to another
			20. Click �Save� link
			21. Click �PUBLISH� button
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
		// 6. Leave the Headphone:X Tuning as �Undetermined�
		// 7. Click �Save� link
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		data.remove("tuning rating");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 8. Log out DTS portal
		productControl.logout();
		// 9. Log into DTS portal as a partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 10. Navigate to �Products� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 11. Select above product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 12. Click �Approve Tuning� link
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
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
		productControl.click(ProductDetailModel.REQUEST_MARKETING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		// Log out DTS portal
		productControl.logout();
		// Log in DTS portal as partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select product above
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that there is a notification message �* Pending DTS
		 * Headphone X Rating.� displayed in step 3: Publishing for partner user
		 * side and the �PUBLISH� button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Pending DTS Headphone X Rating."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// 14. Log out DTS portal
		productControl.logout();
		// 15. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 16. Navigate to �Products� page
		productControl.click(PageHome.linkAccessories);
		// 17. Select above product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that there is a notification message �* Headphone:X Tuning
		 * Rating is required.� displayed in step 3: Publishing for DTS user
		 * side and the �PUBLISH� button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Headphone:X Tuning Rating is required."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// 18. Click �Edit� link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 19. Change the Headphone:X Tuning Rating value to another
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, "A");
		// 20. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// 21. Click �PUBLISH� button
		productControl.click(ProductDetailModel.PUBLISH);
		/*
		 * Verify that the product is published successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PUBLISH_STATUS), "PUBLISHED");
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that partner user is unable to publish product until the DTS user changes �Headphone:X Rating� value in  step 3: Publishing
	 */
	@Test
	public void TC040DAD_18(){
		productControl.addLog("ID : TC040DAD_18: Verify that partner user is unable to publish product until the DTS user changes �Headphone:X Rating� value in  step 3: Publishing");
		/*
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3. Click "Add Product" link
			VP. Verify that the 051D product Edit Page is displayed
			4. Fill valid value into all fields
			5. Upload a tuning file successfully
			6. Leave the Headphone:X Tuning as �Undetermined�
			7. Click �Save� link
			8. Log out DTS portal
			9. Log into DTS portal as a partner user successfully
			10. Navigate to �Products� page
			11. Select above product from Products table
			12. Click �Approve Tuning� link
			13. Pass through step 2: Marketing Approval 
			VP: Verify that there is a notification message �* Pending DTS Headphone X Rating.� displayed in step 3: Publishing for partner user side and the �PUBLISH� button is disabled.
			14. Log out DTS portal
			15. Log into DTS portal as a DTS user successfully
			16. Navigate to �Products� page
			17. Select above product from Products table.
			VP:  Verify that there is a notification message �* Headphone:X Tuning Rating is required.� displayed in step 3: Publishing for DTS user side and the �PUBLISH� button is disabled.
			18. Click �Edit� link
			19. Change the Headphone:X Tuning Rating value to another
			20. Click �Save� link
			VP: Verfy that the �PUBLISH� button is enabled.
			21. Log out DTS portal
			22. Execute steps from 9 to 11 again
			23. Click �PUBLISH� button
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
		// 6. Leave the Headphone:X Tuning as �Undetermined�
		// 7. Click �Save� link
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		data.remove("tuning rating");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 8. Log out DTS portal
		productControl.logout();
		// 9. Log into DTS portal as a partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 10. Navigate to �Products� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 11. Select above product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 12. Click �Approve Tuning� link
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
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
		productControl.click(ProductDetailModel.REQUEST_MARKETING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		// Log out DTS portal
		productControl.logout();
		// Log in DTS portal as partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select product above
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that there is a notification message �* Pending DTS
		 * Headphone X Rating.� displayed in step 3: Publishing for partner user
		 * side and the �PUBLISH� button is disabled
		 */
//		Assert.assertTrue(productControl.checkMessageDisplay("* Pending DTS Headphone X Rating."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// 14. Log out DTS portal
		productControl.logout();
		// 15. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 16. Navigate to �Products� page
		productControl.click(PageHome.linkAccessories);
		// 17. Select above product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that there is a notification message �* Headphone:X Tuning
		 * Rating is required.� displayed in step 3: Publishing for DTS user
		 * side and the �PUBLISH� button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Headphone:X Tuning Rating is required."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"),"button disabled");
		// 18. Click �Edit� link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 19. Change the Headphone:X Tuning Rating value to another
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, "A");
		// 20. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: Verfy that the �PUBLISH� button is enabled
		 */
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button button-warning");
		// 21. Log out DTS portal
		productControl.logout();
		// 22. Execute steps from 9 to 11 again
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to �Products� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select above product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 23. Click �PUBLISH� button
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
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), productData);
		/*
		 * VP. Verify that 040D product Detail page is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME),productData.get("name"));
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
		
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(productData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
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
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), productData);
		/*
		 * VP. Verify that 040D Accessory Detail page is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), productData.get("name"));
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
		// delete product
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(productData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
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
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), productData);
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
		
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(productData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the �Use Parent�s Tuning� link is not displayed in 041D product Variant Detail page
	 */
	@Test
	public void TC041DAVD_08(){
		productControl.addLog("ID : TC041DAVD_08: Verify that the �Use Parent�s Tuning� link is not displayed in 041D product Variant Detail page");
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
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), productData);
		/*
		 * VP. Verify that 040D product Detail page is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), productData.get("name"));
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
		 * Verify that the �Use Parent�s Tuning� link is not displayed in 041D Accessory Variant Detail page
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), "USING PARENT'S MODEL");
		
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(productData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
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
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), productData);
		/*
		 * VP. Verify that 040D Accessory Detail page is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), productData.get("name"));
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
		
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(productData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
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
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), productData);
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
		productWf.approveTuning();
		/*
		 * Verify that The "Partner Actions" link is displayed in Step 2: Marketing Approval of Publishing Process
		 */
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.PARTNER_ACTIONS_STEP_2));
		
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(productData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
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
			11. Click �Publish� button
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select an product from Products table
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String, String> publishedPro = TestData.productData(PARTNER_COMPANY_NAME,PARTNER_BRAND_NAME_1,true,true,true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(),publishedPro);
		/*
		 * VP. Verify that 040D product Detail page is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME),publishedPro.get("name"));
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
		productWf.approveTuning();
		// Approve marketing
//		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
//		productControl.click(VariantInfo.REQUEST_MARKETING_REVIEW);
//		productControl.click(VariantInfo.APPROVE_MARKETING);
		productWf.approveMarketing();
		/*
		 * VP: Verify that the Publish button is enabled in step 3 of Publishing Process
		 */
		//Assert.assertEquals(productControl.getAtributeValue(VariantInfo.PUBLISH, "class"), "button button-warning");
		// 11. Click �Publish� button
		productControl.click(VariantInfo.PUBLISH);
		/*
		 * Verify that the product variant is published
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.PUBLISHED_STATUS), "Published");
		
		// Delete product
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
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
			4. Click �Add New Variant� link
			5. Fill valid value into all required fields
			6. Upload a custom tuning file successfully
			7. Click �Save� link
			VP: Verify that the status of Step 1: Tuning Approval is �UNSUBMITTED� and the �Request Tuning Review� link is displayed
			8. Click �Request Tuning Review� link
			9. Log out DTS portal
			10. Log into DTS portal as DTS user successfully
			11. Navigate to �Products� page
			12. Select above product from products table
			13. Select above variant from variant list
			VP: Verify that there is a notification message �Headphone:X Tuning Rating is required� displayed below �Approve Tuning� link of step 1: Tuning Approval
			14. Click �Approve Tuning� link
		*/
		// 1. Log into DTS portal as a Partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select a published product from products table
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),productData);
		// 4. Click �Add New Variant� link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 5. Fill valid value into all required fields
		// 6. Upload a custom tuning file successfully
		// 7. Click �Save� link
		Hashtable<String,String> data = TestData.variantData(true, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), data);
		/*
		 * VP: Verify that the status of Step 1: Tuning Approval is �UNSUBMITTED� and the �Request Tuning Review� link is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), "UNSUBMITTED");
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.REQUEST_TUNING_REVIEW));
		// 8. Click �Request Tuning Review� link
		productControl.click(VariantInfo.REQUEST_TUNING_REVIEW);
		// 9. Log out DTS portal
		productControl.logout();
		// 10. Log into DTS portal as DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 11. Navigate to �Products� page
		productControl.click(PageHome.linkAccessories);
		// 12. Select above product from products table
		productControl.selectAccessorybyName(productData.get("name"));
		// 13. Select above variant from variant list
		productControl.selectAVariantbyName(data.get("name"));
		/*
		 * VP: Verify that there is a notification message �Headphone:X Tuning Rating is required� displayed below �Approve Tuning� link of step 1: Tuning Approval
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_RATING_WARNING_PARTNER), "* Headphone:X Tuning Rating is required.");
		// 14. Click �Approve Tuning� link
		productControl.click(VariantInfo.APPROVE_TUNING);
		/*
		 * Verify that the DTS user is unable to approve Partner Tuning file
		 */
		Assert.assertNotEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS),ProductDetailModel.ProductStatus.APPROVED.getName());
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.APPROVE_TUNING));
		
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(productData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
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
			4. Click �Add New Variant� link
			5. Fill valid value into all fields
			6. Upload a custom tuning file successfully
			7. Use parent headphone:X tuning rating
			8. Click �Save� link
			VP: Verify that the status of Step 1: Tuning Approval is �PENDING PARTNER APPROVAL� and the �Partner Actions� link is displayed
			9. Expend the �Partner Actions� link
			VP: Verify that the notification message �Headphone:X Tuning Rating is required� is not displayed below �Approve Tuning� link of step 1: Tuning Approval
			10. Click �Approve Tuning� link
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
		// 4. Click �Add New Variant� link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 5. Fill valid value into all fields
		// 6. Upload a custom tuning file successfully
		// 7. Use parent headphone:X tuning rating
		// 8. Click �Save� link
		Hashtable<String,String> dataVariant = TestData.variantData(true, false, false);
//		dataVariant.remove("tuning rating");
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		/*
		 * VP: Verify that the status of Step 1: Tuning Approval is �PENDING PARTNER APPROVAL� and the �Partner Actions� link is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), "PENDING PARTNER APPROVAL");
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.PARTNER_ACTIONS_STEP_1));
		// 9. Expend the �Partner Actions� link
		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		/*
		 * VP: Verify that the notification message �Headphone:X Tuning Rating is required� is not displayed below �Approve Tuning� link of step 1: Tuning Approval
		 */
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.TUNING_RATING_WARNING_DTS));
		// 10. Click �Approve Tuning� link
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		/*
		 * Verify that the DTS user is able to approve DTS Tuning file
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS),ProductDetailModel.ProductStatus.APPROVED.getName());
		// Delete product
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the DTS Tuning is unable to approve when the product variant's Heaphone:X Tuning  is �Undetermined�
	 */
	@Test
	public void TC041DAVD_15(){
		productControl.addLog("ID : TC041DAVD_15: Verify that the DTS Tuning is unable to approve when the product variant's Heaphone:X Tuning  is �Undetermined�");
		/*
			Pre-condition: User has "Approve product tunings" rights.
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3. Select a published product from products table
			4. Click �Add New Variant� link
			5. Fill valid value into all fields
			6. Upload a custom tuning file successfully
			7. Change the HeadphoneX: Tuning rating to �Undetermined�
			8. Click �Save� link
			VP: Verify that the status of Step 1: Tuning Approval is �PENDING PARTNER APPROVAL� and the �Partner Actions� link is displayed
			9. Expend the �Partner Actions� link
			VP: Verify that there is a notification message �Headphone:X Tuning Rating is required� displayed below �Approve Tuning� link of step 1: Tuning Approval
			10. Click �Approve Tuning� link
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select a published product from products table
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),productData);
		// 4. Click �Add New Variant� link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 5. Fill valid value into all fields
		// 6. Upload a custom tuning file successfully
		// 7. Change the HeadphoneX: Tuning rating to �Undetermined�
		// 8. Click �Save� link
		Hashtable<String,String> data = TestData.variantData(true, false, false);
		data.put("tuning rating", "Undetermined");
		productControl.addVariant(AddEditProductModel.getVariantHash(), data);
		/*
		 * VP: Verify that the status of Step 1: Tuning Approval is �PENDING PARTNER APPROVAL� and the �Partner Actions� link is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), "PENDING PARTNER APPROVAL");
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.PARTNER_ACTIONS_STEP_1));
		// 9. Expend the �Partner Actions� link
		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		/*
		 * VP: Verify that there is a notification message �Headphone:X Tuning Rating is required� displayed below �Approve Tuning� link of step 1: Tuning Approval
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_RATING_WARNING_DTS), "* Headphone:X Tuning Rating is required.");
		// 10. Click �Approve Tuning� link
		productControl.click(VariantInfo.APPROVE_TUNING);
		/*
		 * Verify that the DTS user is unable to approve DTS Tuning file
		 */
		Assert.assertNotEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS),ProductDetailModel.ProductStatus.APPROVED.getName());
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.APPROVE_TUNING));
		
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(productData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the product variant is unable to publish the Headphone:X Tuning Rating is still �Undetermined� in step 3: Publishing
	 */
	@Test
	public void TC041DAVD_16(){
		productControl.addLog("ID : TC041DAVD_16: Verify that the product variant is unable to publish the Headphone:X Tuning Rating is still �Undetermined� in step 3: Publishing");
		/*
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3.Select a published product from Products table
			VP. Verify that the 051D product Edit Page is displayed
			4. Click �Add New Variant� link
			5. Fill valid value into all fields
			6. Upload a custom tuning file successfully
			7. Leave the Headphone:X Tuning as �Undetermined�
			8. Click �Save� link
			9. Log out DTS portal
			10. Log into DTS portal as a partner user successfully
			11. Navigate to �Products� page
			12. Select above product from Products table
			13. Select above variant from variants list
			14. Click �Approve Tuning� link
			15. Pass through step 2: Marketing Approval 
			VP: Verify that there is a notification message �* Pending DTS Headphone X Rating.� displayed in step 3: Publishing for partner user side and the �PUBLISH� button is disabled.
			16. Log out DTS portal
			17. Log into DTS portal as a DTS user successfully
			18. Navigate to �Products� page
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
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * *************************************************
		 */
		// 2. Navigate to "Products" page
		// 3.Select a published product from Products table
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 4. Click �Add New Variant� link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 5. Fill valid value into all fields
		// 6. Upload a custom tuning file successfully
		// 7. Leave the Headphone:X Tuning as �Undetermined�
		// 8. Click �Save� link
		Hashtable<String, String> dataVariant = TestData.variantData(true, true, true);
		dataVariant.put("tuning rating", "Undetermined");
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		// 9. Log out DTS portal
		productControl.logout();
		// 10. Log into DTS portal as a partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 11. Navigate to �Products� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 12. Select above product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 13. Select above variant from variants list
		productControl.selectAVariantbyName(dataVariant.get("name"));
		// 14. Click �Approve Tuning� link
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
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
		productControl.click(ProductDetailModel.REQUEST_MARKETING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
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
		 * VP: Verify that there is a notification message �* Pending DTS Headphone X Rating.� displayed in step 3: Publishing for partner user side and the �PUBLISH� button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Pending DTS Headphone X Rating."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// 16. Log out DTS portal
		productControl.logout();
		// 17. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 18. Navigate to �Products� page
		productControl.click(PageHome.linkAccessories);
		// 19. Select above product from Products table.
		productControl.selectAccessorybyName(data.get("name"));
		// 20. Select above variant from variants list
		productControl.selectAVariantbyName(dataVariant.get("name"));
		/*
		 *  Verify that there is a notification message �* Headphone:X Tuning Rating is required.� displayed in step 3: Publishing for DTS user side and the �PUBLISH� button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Headphone:X Tuning Rating is required."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// Delete product
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that DTS user can publish product variant after changing �Headphone:X Rating� value in  step 3: Publishing
	 */
	@Test
	public void TC041DAVD_17(){
		productControl.addLog("ID : TC041DAVD_17: Verify that DTS user can publish product variant after changing �Headphone:X Rating� value in  step 3: Publishing");
		/*
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3. Select a published product from Products table
			VP. Verify that the 051D product Edit Page is displayed
			4. Click �Add New Variant� link
			5. Fill valid value into all required fields
			6. Upload a custom tuning file successfully
			7. Leave the Headphone:X Tuning as �Undetermined�
			8. Click �Save� link
			9. Log out DTS portal
			10. Log into DTS portal as a partner user successfully
			11. Navigate to �Products� page
			12. Select above product from Products table
			13. Select above variant from variants list
			14. Click �Approve Tuning� link
			15. Pass through step 2: Marketing Approval 
			VP: Verify that there is a notification message �* Pending DTS Headphone X Rating.� displayed in step 3: Publishing for partner user side and the �PUBLISH� button is disabled.
			16. Log out DTS portal
			17. Log into DTS portal as a DTS user successfully
			18. Navigate to �Products� page
			19. Select above product from Products table
			20. Select above variant from variants list
			VP:  Verify that there is a notification message �* Headphone:X Tuning Rating is required.� displayed in step 3: Publishing for DTS user side and the �PUBLISH� button is disabled.
			21. Click �Edit� link
			22. Change the Headphone:X Tuning Rating value to another
			23. Click �Save� link
			24. Click �PUBLISH� button
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
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * *************************************************
		 */
		// 2. Navigate to "Products" page
		// 3.Select a published product from Products table
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 4. Click �Add New Variant� link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 5. Fill valid value into all fields
		// 6. Upload a custom tuning file successfully
		// 7. Leave the Headphone:X Tuning as �Undetermined�
		// 8. Click �Save� link
		Hashtable<String, String> dataVariant = TestData.variantData(true, true, true);
		dataVariant.remove("tuning rating");
		dataVariant.put("tuning rating", "Undetermined");
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		// 9. Log out DTS portal
		productControl.logout();
		// 10. Log into DTS portal as a partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 11. Navigate to �Products� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 12. Select above product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 13. Select above variant from variants list
		productControl.selectAVariantbyName(dataVariant.get("name"));
		// 14. Click �Approve Tuning� link
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
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
		productControl.click(ProductDetailModel.REQUEST_MARKETING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
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
		 * VP: Verify that there is a notification message �* Pending DTS Headphone X Rating.� displayed in step 3: Publishing for partner user side and the �PUBLISH� button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Pending DTS Headphone X Rating."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// 16. Log out DTS portal
		productControl.logout();
		// 17. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 18. Navigate to �Products� page
		productControl.click(PageHome.linkAccessories);
		// 19. Select above product from Products table.
		productControl.selectAccessorybyName(data.get("name"));
		// 20. Select above variant from variants list
		productControl.selectAVariantbyName(dataVariant.get("name"));
		/*
		 *  Verify that there is a notification message �* Headphone:X Tuning Rating is required.� displayed in step 3: Publishing for DTS user side and the �PUBLISH� button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Headphone:X Tuning Rating is required."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// 21. Click �Edit� link
		productControl.click(VariantInfo.EDIT_VARIANT);
		// 22. Change the Headphone:X Tuning Rating value to another
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, "A");
		// 23. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		// 24. Click �PUBLISH� button
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
	 * Verify that partner user is unable to publish product variant until the DTS user changes �Headphone:X Rating� value in  step 3: Publishing
	 */
	@Test
	public void TC041DAVD_18(){
		productControl.addLog("ID : TC041DAVD_18: Verify that partner user is unable to publish product variant until the DTS user changes �Headphone:X Rating� value in  step 3: Publishing");
		/*
			1. Log into DTS portal as a DTS user successfully
			2. Navigate to "Products" page
			3. Select a published product from Products table
			VP. Verify that the 051D product Edit Page is displayed
			4. Click �Add New Variant� link
			5. Fill valid value into all required fields
			6. Upload a custom tuning file successfully
			7. Leave the Headphone:X Tuning as �Undetermined�
			8. Click �Save� link
			9. Log out DTS portal
			10. Log into DTS portal as a partner user successfully
			11. Navigate to �Products� page
			12. Select above product from Products table
			13. Select above variant from variants list
			14. Click �Approve Tuning� link
			15. Pass through step 2: Marketing Approval 
			VP: Verify that there is a notification message �* Pending DTS Headphone X Rating.� displayed in step 3: Publishing for partner user side and the �PUBLISH� button is disabled.
			16. Log out DTS portal
			17. Log into DTS portal as a DTS user successfully
			18. Navigate to �Products� page
			19. Select above product from Products table.
			20. Select above variant from variants list
			VP:  Verify that there is a notification message �* Headphone:X Tuning Rating is required.� displayed in step 3: Publishing for DTS user side and the �PUBLISH� button is disabled.
			21. Click �Edit� link
			22. Change the Headphone:X Tuning Rating value to another
			23. Click �Save� link
			VP: Verfy that the �PUBLISH� button is enabled.
			24. Log out DTS portal
			25. Execute steps from 10 to 13 again
			26. Click �PUBLISH� button
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
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * *************************************************
		 */
		// 2. Navigate to "Products" page
		// 3.Select a published product from Products table
		/*
		 * VP. Verify that the 051D product Edit Page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 4. Click �Add New Variant� link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 5. Fill valid value into all fields
		// 6. Upload a custom tuning file successfully
		// 7. Leave the Headphone:X Tuning as �Undetermined�
		// 8. Click �Save� link
		Hashtable<String, String> dataVariant = TestData.variantData(true, true, true);
		dataVariant.remove("tuning rating");
		dataVariant.put("tuning rating", "Undetermined");
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		// 9. Log out DTS portal
		productControl.logout();
		// 10. Log into DTS portal as a partner user successfully
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 11. Navigate to �Products� page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 12. Select above product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 13. Select above variant from variants list
		productControl.selectAVariantbyName(dataVariant.get("name"));
		// 14. Click �Approve Tuning� link
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
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
		productControl.click(ProductDetailModel.REQUEST_MARKETING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
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
		 * VP: Verify that there is a notification message �* Pending DTS Headphone X Rating.� displayed in step 3: Publishing for partner user side and the �PUBLISH� button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Pending DTS Headphone X Rating."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// 16. Log out DTS portal
		productControl.logout();
		// 17. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 18. Navigate to �Products� page
		productControl.click(PageHome.linkAccessories);
		// 19. Select above product from Products table.
		productControl.selectAccessorybyName(data.get("name"));
		// 20. Select above variant from variants list
		productControl.selectAVariantbyName(dataVariant.get("name"));
		/*
		 *  Verify that there is a notification message �* Headphone:X Tuning Rating is required.� displayed in step 3: Publishing for DTS user side and the �PUBLISH� button is disabled
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("* Headphone:X Tuning Rating is required."));
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// 21. Click �Edit� link
		productControl.click(VariantInfo.EDIT_VARIANT);
		// 22. Change the Headphone:X Tuning Rating value to another
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, "A");
		// 23. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * VP: Verify that the �PUBLISH� button is enabled.
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
		// 26. Click �PUBLISH� button
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
		productWf.approveTuning();
		/*
		 * Verify that the Tuning Approval status is changed to "APPROVED" and
		 * the "Request Marketing Review" link in step 2: Marketing Approval is
		 * disabled and the error message "*EAN/UPC is required." is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS),ProductDetailModel.ProductStatus.APPROVED.getName());
		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		productControl.click(VariantInfo.REQUEST_MARKETING_REVIEW);
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.MARKETING_STATUS), "UNSUBMITTED");
		Assert.assertTrue(productControl.checkMessageDisplay("* EAN/UPC is required."));
		// Delete product
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that user is able to download product variant profile successfully when clicking on the �Published Variant Profile� link.
	 */
	@Test
	public void TC041DAVD_20(){
		productControl.addLog("ID : TC041DAVD_20: Verify that user is able to download product variant "
				+ "profile successfully when clicking on the �Published Variant Profile� link.");
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
			VP: The 041D product Variant Detail page displays and the �Published Variant Profile� link is not displayed in �Actions� module
			8. Pass through two step of Publishing Process
			9. Click �Publish� button
			VP: The product variant is published successfully and the �Published Variant Profile� link is displayed in �Actions� module
			10. Click on �Published Variant Profile� link.
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
		 * VP: The 041D product Variant Detail page displays and the �Published Variant Profile� link is not displayed in �Actions� module
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TITLE_NAME), dataVariant.get("name"));
		Assert.assertFalse(productControl.isElementPresent(VariantInfo.PUBLISHED_VARIANT_PROFILE_LINK));
		// 8. Pass through two step of Publishing Process
		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		productControl.click(VariantInfo.REQUEST_MARKETING_REVIEW);
		productControl.click(VariantInfo.APPROVE_MARKETING);
		productControl.click(ProductDetailModel.REQUEST_MARKETING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		// 9. Click �Publish� button
		productControl.click(VariantInfo.PUBLISH);
		/*
		 * Verify that the product variant is published successfully and the �Published Variant Profile� link is displayed in �Actions� module
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.PUBLISHED_STATUS), "Published");
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.PUBLISHED_VARIANT_PROFILE_LINK));
		// 10. Click on �Published Variant Profile� link
		boolean isDownloaded = productControl.downloadFile(VariantInfo.PUBLISHED_VARIANT_PROFILE_LINK);
		/*
		 * Verify that the download window popup is displayed and the product variant profile is downloaded successfully
		 */
		Assert.assertTrue(isDownloaded);
		// Delete product
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that 040D Product Detail page and 041 Variant Detail page display �Input Specification� secsion correctly
	 */
	@Test
	public void TC040DAD_19(){
		productControl.addLog("ID : TC040DAD_19: Verify that 040D Product Detail page and 041 Variant Detail page display �Input Specification� secsion correctly");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click �Add Product� link
			4. Fill valid value into all required fields
			5. Select the connection type "USB" only
			6. Click �Save� link
			VP: Verify that �Input Specification� section contains only USB
			7. Click �Edit Version� link
			8. 
			Select the connection type include �Wired�, �Bluetooth� and �USB�
			9. Click �Save� link
			VP: Verify that �Input Specification� section contains Lineout, Bluetooth and USB
			10. Click �Add New Variant� link
			11. Fill valid value into all required fields
			12. Click �Save� link
			VP: Verify that �Input Specification� section contains Lineout, Bluetooth and USB
		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
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
	 * Verify that the connection type contains �USB�, Published Product Profile will include USB channel, USB-C headphones 3D and AEQ data
	 */
	@Test
	public void TC040DAD_20(){
		productControl.addLog("ID : TC040DAD_20: Verify that the connection type contains �USB�, Published Product Profile will include USB channel, USB-C headphones 3D and AEQ data");
		/*
			1. Log into DTS portal as a DTS user
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
			VP: Verify that published profile include USB-C headphones 3D, AEQ , multichRoom, frontRoom and wideRoom data

		 */
		
		// 1. Log into DTS portal as a DTS user
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
		// 11. Download �Published Product Profile� link
		String publish_profile = reportsControl.getPath(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE);
		// VP: Verify that published profile include USB channel and route type will be AR_OUT_USB
		Assert.assertTrue(productControl.checkPublishProfile(publish_profile, "usb", "AR_OUT_USB"));
		// VP: Verify that published profile include USB-C headphones 3D, AEQ , multichRoom, frontRoom and wideRoom data
		
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the connection type contains �USB�, Published Variant Profile will include USB channel and route type will be AR_OUT_USB
	 */
	@Test
	public void TC040DAD_21(){
		productControl.addLog("ID : TC040DAD_21: Verify that the connection type contains �USB�, Published Variant Profile will include USB channel and route type will be AR_OUT_USB");
		/*
			1. Log into DTS portal as a DTS user
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
		
		// 1. Log into DTS portal as a DTS user
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
		// 15. Publish above variant
		productWf.approveTuning();
		productWf.approveMarketing();
		productControl.click(ProductDetailModel.PUBLISH);
		// 16. Download �Published Variant Profile� link
		String publish_profile = reportsControl.getPath(VariantInfo.PUBLISHED_VARIANT_PROFILE_LINK);
		// VP: Verify that published profile include USB channel and route type will be AR_OUT_USB
		Assert.assertTrue(productControl.checkPublishProfile(publish_profile, "usb", "AR_OUT_USB"));
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the connection type contains �USB� only, New Tuning Template File in Product page will include invalid information
	 */
	@Test
	public void TC040DAD_22(){
		productControl.addLog("ID : TC040DAD_22: Verify that the connection type contains �USB� only, New Tuning Template File in Product page will include invalid information");
		/*
			1. Log into DTS portal as a DTS user
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
		// 6. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: Verify that Product is saved successfully
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		String UUID = productControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
		// 7. Click �New Tuning Template File� link
		// VP: Verify that tuning template is downloaded successfully
		Boolean isDownload = productControl.downloadFile(ProductDetailModel.TUNING_TEMPLATE);
		Assert.assertTrue(isDownload);
		// 8. Open tuning template file
		// VP: Verify that New Tuning Template File will include USB and other product information
		String brand_uuid = DbUtil.selectStatment("Select dts_id from brand where name = '" + dataProduct.get("brand") + "'");
		Assert.assertTrue(productControl.checkInforTuningTemplate(UUID, dataProduct.get("type").toLowerCase(), brand_uuid.trim()));
		Assert.assertTrue(productControl.checkRouteTuningTemplate(UUID, "usb", "AR_OUT_USB"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the connection type contains �Wired�, �Bluetooth� and �USB�; New Tuning Template File in Product page will include valid product information
	 */
	@Test
	public void TC040DAD_23(){
		productControl.addLog("ID : TC040DAD_23: Verify that the connection type contains �Wired�, �Bluetooth� and �USB�; New Tuning Template File in Product page will include valid product information");
		/*
			1. Log into DTS portal as a DTS user
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
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
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
		Boolean isDownload = productControl.downloadFile(ProductDetailModel.TUNING_TEMPLATE);
		Assert.assertTrue(isDownload);
		// 8. Open tuning template file
		// VP: Verify that New Tuning Template File will include USB and other product information
		String brand_uuid = DbUtil.selectStatment("Select dts_id from brand where name = '" + dataProduct.get("brand") + "'");
 
		Assert.assertTrue(productControl.checkRouteTuningTemplate(UUID, "usb", "AR_OUT_USB"));
		Assert.assertTrue(productControl.checkRouteTuningTemplate(UUID, "bluetooth0", "AR_OUT_BLUETOOTH"));
		Assert.assertTrue(productControl.checkRouteTuningTemplate(UUID, "line-out0", "AR_OUT_LINEOUT"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the connection type contains �Wired�, �Bluetooth� and �USB�; New Tuning Template File in Variant page will include valid product information
	 */
	@Test
	public void TC040DAD_24(){
		productControl.addLog("ID : TC040DAD_24: Verify that the connection type contains �Wired�, �Bluetooth� and �USB�; New Tuning Template File in Variant page will include valid product information");
		/*
			1. Log into DTS portal as a DTS user
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
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click �Add Product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		// 5. Select the connection type "USB" only
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
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
		Hashtable<String,String> variantData = TestData.variantData(false, false, false);
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
		String brand_uuid = DbUtil.selectStatment("Select dts_id from brand where name = '" + dataProduct.get("brand") + "'");
		Assert.assertTrue(productControl.checkInforTuningTemplate(UUID, dataProduct.get("type").toLowerCase(), brand_uuid.trim()));
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
	public void TC040DAD_25(){
		productControl.addLog("ID : TC040DAD_25: Verify that the Published Product Profile includes both Eagle Version 1.4 and 2.0");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products� page
			3. Click �Add Product� link
			4. Fill valid value into all required fields
			5. Click �Save� link
			VP: Verify that Product is created successfully
			6. Publish above Product
			7. Download �Published Product Profile� link
			VP: Verify that Published Product Profile includes both Eagle Version 1.4 and 2.0

		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		// 3. Click �Add Product� link
		// 4. Fill valid value into all required fields
		// 5. Click �Save� link
		// 6. Publish above Product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndPublish(dataProduct);
		// VP: Verify that Product is saved successfully
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		String UUID = productControl.getTextByXpath(ProductDetailModel.DTS_TRACKING_ID);
		// 7. Download �Published Product Profile� link
		Assert.assertTrue(productControl.downloadFile(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE));
		// VP: Verify that Published Product Profile includes both Eagle Version 1.4 and 2.0
		Assert.assertTrue(productControl.isIncludeTwoEagleVersion(UUID));
		// Delete Product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the Published Variant Profile includes both Eagle Version 1.4 and 2.0
	 */
	@Test
	public void TC041DAVD_21(){
		productControl.addLog("ID : TC041DAVD_21: Verify that the Published Variant Profile includes both Eagle Version 1.4 and 2.0");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products� page
			3. Click �Add Product� link
			4. Fill valid value into all required fields
			5. Click �Save� link
			VP: Verify that Product is created successfully
			6. Publish above Product
			7. Click �Add New Variant� link
			8. Fill valid value into all required fields
			9. Click �Save� link
			VP: Verify that Variant is created successfully
			10. Publish above variant
			11. Download �Published Variant Profile� link
			VP: Verify that Published Variant Profile includes both Eagle Version 1.4 and 2.0


		 */
		
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click �Add Product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		// 5. Click �Save� link
		// 6. Publish above Product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndPublish(dataProduct);
		// VP: Verify that Product is saved successfully
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 7. Click �Add New Variant� link
		// 8. Fill valid value into all required fields
		// 9. Click �Save� link
		Hashtable<String,String> dataVariant = TestData.variantData(true, true, true);
		productWf.addVariantAndPublish(dataVariant);
		// VP: Verify that Variant is created successfully
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.TITLE_NAME));
		String UUID = productControl.getTextByXpath(VariantInfo.TRACKING_ID);
		// 10. Publish above variant
		// 11. Download �Published Variant Profile� link
		Assert.assertTrue(productControl.downloadFile(VariantInfo.PUBLISHED_VARIANT_PROFILE_LINK));
		// VP: Verify that Published Product Profile includes both Eagle Version 1.4 and 2.0
		Assert.assertTrue(productControl.isIncludeTwoEagleVersion(UUID));
		// Delete Product
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	
}
