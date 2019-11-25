package com.dts.adminportal.dts.test;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.TuningRequestForm;
import com.dts.adminportal.util.TestData;
import com.dts.adminportal.workflow.ProductWorkflow;

public class DTSProducts040DTuningState extends BasePage{
	
	@Override
	protected void initData() {
	}	
	
	/*
	 * Verify that when there is no tuning is uploaded, the tuning status is "UNSUBMITTED"
	 */
	@Test
	public void TC040DTS_01(){
		productControl.addLog("ID : TC040DTS_01: Verify that when there is no tuning is uploaded, the tuning status is 'UNSUBMITTED'");
		/*
	 		Pre-condition: User has "Request product tunings" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Fill valid value into all required fields
			5. Do not upload tuning
			6. Click "Save" link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click "Add product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		// 5. Do not upload tuning
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * Verify that The 040D product Detail page is displayed and the tuning status is "UNSUBMITTED"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "UNSUBMITTED");
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
		//productControl.deleteAllProductWithPublishedStatus("Published");
	}
	
	/*
	 * Verify that when the partner request tuning file, the tuning action link is "Upload Tuning" and tuning status is "DTS REQUEST"
	 */
	@Test
	public void TC040DTS_02(){
		productControl.addLog("ID : TC040DTS_02: Verify that when the partner request tuning file, the tuning action link is 'Upload Tuning' and tuning status is 'DTS REQUEST'");
		/*
	 		Pre-Condition: Partner user request tuning file.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Filter product for Tuning Request Pending
			4. Select an product from Products table
		 */
		/*
		 * PreCondition: Create product Which has tuning status is DTS request pending
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Request tuning for partner
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		// Submit tuning form
		productControl.selectACheckbox(TuningRequestForm.AGREE);
		productControl.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select an product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * The 040D product Detail page is displayed and the tuning action link is "Upload Tuning", tuning status is "DTS REQUEST PENDING"
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.UPLOAD_TUNING));
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "DTS REQUEST PENDING");
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the portal is redirect to 051 Model Edit page when clicking on "Upload Tuning" link in step 1 : Tuning Approval
	 */
	@Test
	public void TC040DTS_03(){
		productControl.addLog("ID : TC040DTS_03: Verify that the portal is redirect to 051 Model Edit page when clicking on 'Upload Tuning' link in step 1 : Tuning Approval");
		/*
	 		Pre-Condition: Partner user request tuning file.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Filter product for DTS Request Status
			4. Select a product from Products table.
			5. Click "Upload Tuning" link
		 */
		/*
		 * PreCondition: Create product Which has tuning status is DTS request pending
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Request tuning for partner
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		// Submit tuning form
		productControl.selectACheckbox(TuningRequestForm.AGREE);
		productControl.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 5. Click "Upload Tuning" link
		productControl.click(ProductDetailModel.UPLOAD_TUNING);
		/*
		 * Verify that The 051 Model Edit page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getProductElementFieldIds()), true);
		// Delete product
		productControl.click(AddEditProductModel.CANCEL_PRODUCT);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the "Cancel Request" link is displayed when clicking on "Partner Actions" link when tuning status is "DTS Request Pending"
	 */
	@Test
	public void TC040DTS_04(){
		productControl.addLog("ID : TC040DTS_04: Verify that the 'Cancel Request' link is displayed when clicking on 'Partner Actions' link when tuning status is 'DTS Request Pending'");
		/*
	 		Pre-Condition: Partner user request tuning file.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Filter product for DTS Request  Status
			4. Select a product from Products table
			5. Click "Partner Actions" link
		 */
		/*
		 * PreCondition: Create product Which has tuning status is DTS request pending
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Request tuning for partner
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		// Submit tuning form
		productControl.selectACheckbox(TuningRequestForm.AGREE);
		productControl.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 5. Click "Partner Actions" link
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		/*
		 * Verify that The "Cancel Request" link is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.CANCEL_REQUEST_TUNING));
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the tuning approval is changed to "UNSUBMIT" when the DTS user clicks on "Cancel Request" link
	 */
	@Test
	public void TC040DTS_05(){
		productControl.addLog("ID : TC040DTS_05: Verify that the tuning approval is changed to 'UNSUBMIT' when the DTS user clicks on 'Cancel Request' link");
		productControl.addErrorLog("PDPP-1436 - 040D Product detail: DTS user is unable to use the Partner's "
				+ "Cancel Request action to cancel the Request DTS Tuning.");
		/*
	 		Pre-Condition: Partner user request tuning file.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Filter product for Tuning Request Pending
			4. Select a product from Products table.
			5. Expend the "Partner Actions" link
			6. Click "Cancel Request" link
		 */
		/*
		 * PreCondition: Create product Which has tuning status is DTS request pending
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Request tuning for partner
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		// Submit tuning form
		productControl.selectACheckbox(TuningRequestForm.AGREE);
		productControl.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 5. Click "Partner Actions" link
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		// 6. Click "Cancel Request" link
		productControl.click(ProductDetailModel.CANCEL_REQUEST_TUNING);
		productControl.editData(TuningRequestForm.ADDITIONALINFO,"Cancel Request");
		productControl.click(TuningRequestForm.SUBMIT);
		productControl.click(TuningRequestForm.PRODUCT_LINK);
		/*
		 * Verify that Tuning approval is changed to "UNSUBMITTED" 
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "UNSUBMITTED");
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the Tuning Approval is changed to "Pending Partner Approval" when DTS user upload tuning file
	 */
	@Test
	public void TC040DTS_06(){
		productControl.addLog("ID : TC040DTS_06: Verify that the Tuning Approval is changed to 'Pending Partner Approval' when DTS user upload tuning file");
		/*
	 		Pre-Condition: Partner user request tuning file.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Filter product for DTS Request  Status
			4. Select a product from Products table.
			5. Click "Upload Tuning" link
			6. Upload a tuning file successfully.
		 */
		/*
		 * PreCondition: Create product Which has tuning status is DTS request pending
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Request tuning for partner
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		// Submit tuning form
		productControl.selectACheckbox(TuningRequestForm.AGREE);
		productControl.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 5. Click "Upload Tuning" link
		productControl.click(ProductDetailModel.UPLOAD_TUNING);
		// 6. Upload a tuning file successfully
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * Verify that The Tuning Approval is changed to "Pending Partner Approval"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "PENDING PARTNER APPROVAL");
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the "Send Reminder", "Edit Headphone:X Certification" and Partner Actions" links are displayed when the tuning status is "Pending Partner Approval"
	 */
	@Test
	public void TC040DTS_07(){
		productControl.addLog("ID : TC040DTS_07: Verify that the 'Send Reminder', 'Edit Headphone:X Certification' and Partner Actions links are displayed when the tuning status is 'Pending Partner Approval'");
		/*
	 		Pre-Condition: Partner user request tuning file.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Filter product for DTS Request  Status
			4. Select a product from Products table.
			5. Click "Upload Tuning" link
			6. Upload a tuning file successfully
		 */
		/*
		 * PreCondition: Create product Which has tuning status is DTS request pending
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Request tuning for partner
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		// Submit tuning form
		productControl.selectACheckbox(TuningRequestForm.AGREE);
		productControl.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 5. Click "Upload Tuning" link
		productControl.click(ProductDetailModel.UPLOAD_TUNING);
		// 6. Upload a tuning file successfully
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * Verify that the tuning status is changed to "Pending Partner Approval" and  the "Send Reminder", "Edit Headphone:X Certification" and Partner Actions" links are displayed in step 1: Tuning Approval
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "PENDING PARTNER APPROVAL");
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.SEND_REMINDER));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.EDIT_HEADPHONE_CERTIFICATION));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PARTNER_ACTIONS_STEP_1));
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the Tuning status is changed to "Approved" and its action are "Revoke Tuning" and "Edit Heaphone: X Certification" links when DTS user clicks on "Approve Tuning" link
	 */
	@Test
	public void TC040DTS_08(){
		productControl.addLog("ID : TC040DTS_08: Verify that the Tuning status is changed to 'Approved' and its action are 'Revoke Tuning' and 'Edit Heaphone: X Certification' links when DTS user clicks on 'Approve Tuning' link");
		/*
	 		Pre-Condition: Partner user request tuning file.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Filter product for Tuning Request Pending
			4. Select a product from Products table.
			5. Click "Upload Tuning" link
			6. Upload a tuning file successfully
			7. Expand the "Partner Actions" link
			8. Click "Approve Tuning" link
		 */
		/*
		 * PreCondition: Create product Which has tuning status is DTS request pending
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Request tuning for partner
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		// Submit tuning form
		productControl.selectACheckbox(TuningRequestForm.AGREE);
		productControl.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 5. Click "Upload Tuning" link
		productControl.click(ProductDetailModel.UPLOAD_TUNING);
		// 6. Upload a tuning file successfully
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, "A");
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// 7. Expand the "Partner Actions" link
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		// 8. Click "Approve Tuning" link
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		/*
		 * Verify that Tuning status is changed to "Approved" and its action are "Revoke Tuning" and "Edit Headphone: X Certification" links
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "APPROVED");
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.EDIT_HEADPHONE_CERTIFICATION));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.REVOKE_TUNING));
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that clicking on "Edit Headphone:X Certification" link loads the 051D Model Edit page
	 */
	@Test
	public void TC040DTS_09(){
		productControl.addLog("ID : TC040DTS_09: Verify that clicking on 'Edit Headphone:X Certification' link loads the 051D Model Edit page");
		/*
	 		Pre-Condition: Partner user request tuning file.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Filter product for DTS Request  Status
			4. Select a product from Products table.
			5. Click "Upload Tuning" link
			6. Upload a tuning file successfully
			7. Expand the "Partner Actions" link
			8. Click "Approve Tuning" link
			VP: Verify that Tuning status is changed to "Approved" and its action are "Revoke Tuning" and "Edit Headphone: X Certification" links
			9. Click "Edit Headphone: X Certification" links
		 */
		/*
		 * PreCondition: Create product Which has tuning status is DTS request pending
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Request tuning for partner
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		// Submit tuning form
		productControl.selectACheckbox(TuningRequestForm.AGREE);
		productControl.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 5. Click "Upload Tuning" link
		productControl.click(ProductDetailModel.UPLOAD_TUNING);
		// 6. Upload a tuning file successfully
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, "A");
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// 7. Expand the "Partner Actions" link
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		// 8. Click "Approve Tuning" link
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		/*
		 * Verify that Tuning status is changed to "Approved" and its action are "Revoke Tuning" and "Edit Headphone: X Certification" links
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "APPROVED");
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.EDIT_HEADPHONE_CERTIFICATION));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.REVOKE_TUNING));
		// 9. Click "Edit Headphone: X Certification" links
		productControl.click(ProductDetailModel.EDIT_HEADPHONE_CERTIFICATION);
		/*
		 * Verify that 051D Model Edit page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getProductElementFieldIds()), true);
		// Delete product
		productControl.click(AddEditProductModel.CANCEL_PRODUCT);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the tuning status is changed to "REVOKED" when the DTS admin revokes the Partner tuning
	 */
	@Test
	public void TC040DTS_10(){
		productControl.addLog("ID : TC040DTS_10: Verify that the tuning status is changed to 'REVOKED' when the DTS admin revokes the Partner tuning");
		/*
	 		Pre-Condition: Partner user request tuning file.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Filter product for Tuning Request Pending
			4. Select a product from Products table.
			5. Click "Upload Tuning" link
			6. Upload a tuning file successfully
			VP: Verify that the tuning status is changed to " PENDING PARTNER APPROVAL"
			7. Expand the "Partner Actions" link
			8. Click "Approve Tuning" link
			VP: Verify that Tuning status is changed to "Approved" and its action are "Revoke Tuning" and "Edit Headphone: X Certification" links
			9. Click "Revoke Tuning" links
		 */
		/*
		 * PreCondition: Create product Which has tuning status is DTS request pending
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Request tuning for partner
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		// Submit tuning form
		productControl.selectACheckbox(TuningRequestForm.AGREE);
		productControl.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 5. Click "Upload Tuning" link
		productControl.click(ProductDetailModel.UPLOAD_TUNING);
		// 6. Upload a tuning file successfully
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, "A");
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: Verify that the tuning status is changed to "PENDING PARTNER APPROVAL"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "PENDING PARTNER APPROVAL");
		// 7. Expand the "Partner Actions" link
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		// 8. Click "Approve Tuning" link
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		/*
		 * Verify that Tuning status is changed to "Approved" and its action are "Revoke Tuning" and "Edit Headphone: X Certification" links
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "APPROVED");
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.EDIT_HEADPHONE_CERTIFICATION));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.REVOKE_TUNING));
		// 9. Click "Revoke Tuning" links
		productWf.revokeTuning();
		/*
		 * Verify that Tuning status is changed to "REVOKED"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "REVOKED");
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the tuning status is "DECLINED" when the partner decline the DTS Tuning
	 */
	@Test
	public void TC040DTS_11(){
		productControl.addLog("ID : TC040DTS_11: Verify that the tuning status is 'DECLINED' when the partner decline the DTS Tuning");
		/*
	 		Pre-Condition: Partner user request tuning file.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Filter product for Tuning Request Pending
			4. Select a product from Products table.
			5. Click "Upload Tuning" link
			6. Upload a tuning file successfully
			VP: Verify that the tuning status is changed to "PENDING PARTNER APPROVAL"
			7. Expand the "Partner Actions" link
			8. Click "Decline Tuning" link
		 */
		/*
		 * PreCondition: Create product Which has tuning status is DTS request pending
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Request tuning for partner
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		// Submit tuning form
		productControl.selectACheckbox(TuningRequestForm.AGREE);
		productControl.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 5. Click "Upload Tuning" link
		productControl.click(ProductDetailModel.UPLOAD_TUNING);
		// 6. Upload a tuning file successfully
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, "A");
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: Verify that the tuning status is changed to "PENDING PARTNER APPROVAL"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "PENDING PARTNER APPROVAL");
		// 7. Expand the "Partner Actions" link
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		// 8. Click "Decline Tuning" link
		productWf.declineTuning();
		/*
		 * Verify that The tuning status is changed to "DECLINED"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "DECLINED");
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the tuning status is changed to "DTS REQUEST PENDING" when the partner request revised tuning
	 */
	@Test
	public void TC040DTS_12(){
		productControl.addLog("ID : TC040DTS_12: Verify that the tuning status is changed to 'DTS REQUEST PENDING' when the partner request revised tuning");
		/*
	 		Pre-Condition: Partner user request tuning file.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Filter product for Tuning Request Pending
			4. Select a product from Products table.
			5. Click "Upload Tuning" link
			6. Upload a tuning file successfully
			7. Expand the "Partner Actions" link
			8. Click "Decline Tuning" link
			9. Expand the "Partner Actions" link
			10. Click "Request Revised Tuning"
			11. Submit Tuning request form
		 */
		/*
		 * PreCondition: Create product Which has tuning status is DTS request pending
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Request tuning for partner
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		// Submit tuning form
		productControl.selectACheckbox(TuningRequestForm.AGREE);
		productControl.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 5. Click "Upload Tuning" link
		productControl.click(ProductDetailModel.UPLOAD_TUNING);
		// 6. Upload a tuning file successfully
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, "A");
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// 7. Expand the "Partner Actions" link
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		// 8. Click "Decline Tuning" link
		productWf.declineTuning();
		// 9. Expand the "Partner Actions" link
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		// 10. Click "Request Revised Tuning"
		productControl.click(ProductDetailModel.REQUEST_REVISED_TUNING);
		// 11. Submit Tuning request form
		/*
		 * Verify that The tuning status is changed to "DTS Request Pending" and its action is "Upload Tuning"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "DTS REQUEST PENDING");
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.UPLOAD_TUNING));
		//Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the tuning status is "DECLINED" when partner cancel the revision request
	 */
	@Test
	public void TC040DTS_13(){
		productControl.addLog("ID : TC040DTS_13: Verify that the tuning status is 'DECLINED' when partner cancel the revision request");
		/*
	 		Pre-Condition: Partner user request tuning file.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Filter product for Tuning Request Pending
			4. Select a product from Products table.
			5. Click "Upload Tuning" link
			6. Upload a tuning file successfully
			VP: Verify that the tuning status is changed to "PENDING PARTNER APPROVAL"
			7. Expand the "Partner Actions" link
			8. Click "Decline Tuning" link
			VP. The  tuning status is changed to "DECLINED".
			9. Expand the "Partner Actions" link
			10. Click "Request Revised Tuning".
			VP: The  tuning status is changed to "DTS Request Pending" and its action is "Upload Tuning"
			11. Expand the "Partner Actions" link
			12. Click "Cancel Request" link
		 */
		/*
		 * PreCondition: Create product Which has tuning status is DTS request pending
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Request tuning for partner
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		// Submit tuning form
		productControl.selectACheckbox(TuningRequestForm.AGREE);
		productControl.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		// 5. Click "Upload Tuning" link
		productControl.click(ProductDetailModel.UPLOAD_TUNING);
		// 6. Upload a tuning file successfully
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, "A");
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: Verify that the tuning status is changed to "PENDING PARTNER APPROVAL"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "PENDING PARTNER APPROVAL");
		// 7. Expand the "Partner Actions" link
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		// 8. Click "Decline Tuning" link
		productWf.declineTuning();
		/*
		 *  VP. The  tuning status is changed to "DECLINED"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "DECLINED");
		// 9. Expand the "Partner Actions" link
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		// 10. Click "Request Revised Tuning"
		productControl.click(ProductDetailModel.REQUEST_REVISED_TUNING);

		/*
		 * VP: The  tuning status is changed to "DTS Request Pending" and its action is "Upload Tuning"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "DTS REQUEST PENDING");
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.UPLOAD_TUNING));
		// 11. Expand the "Partner Actions" link
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		// 12. Click "Cancel Request" link
		productControl.click(ProductDetailModel.REQUEST_TUNING_CANCEL);
		// Check if PDPP-1265 found
		if(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS).equals("UNSUBMITTED")){
			productControl.addErrorLog("PDPP-1265: 040 Product Detail: Tuning status in Tuning Approval process is 'Unsubmitted' instead of 'Declined' after Partner User cancels 'Request Revised Tuning'");
			Assert.assertTrue(false);
		}
		/*
		 * Verify that The tuning status is changed to "DECLINED"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "DECLINED");
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
		
		
	}
	
	/*
	 * Verify that the user can edit product when the marketing approval status is "UNSUBMITTED"
	 */
	@Test
	public void TC040DTS_14(){
		productControl.addLog("ID : TC040DTS_14: Verify that the user can edit product when the marketing approval status is 'UNSUBMITTED'");
		/*
	 		1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Fill valid value into all required fields
			5. Upload valid tuning file
			6. Click "Save" link
			7. Go through the tuning approval
			VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED"
			8. Click "Edit" link
			9. Change product's information
			10. Click "Save" link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click "Add product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		// 5. Upload valid tuning file
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 7. Go through the tuning approval
		productWf.approveTuning();
		/*
		 * VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "APPROVED");
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "UNSUBMITTED");
		// 8. Click "Edit" link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 9. Change product's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		productControl.editData(AddEditProductModel.MODEL_NAME, newName);
		// 10. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * Verify that The product's information is changed successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), newName);
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the user can not edit product when the marketing approval status is "Pending DTS Review"
	 */
	@Test
	public void TC040DTS_15(){
		productControl.addLog("ID : TC040DTS_15: Verify that the user can not edit product when the marketing approval status is 'Pending DTS Review'");
		/*
	 		Pre-condition: the tuning approval is approved. the User has "Publish and suspend Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Fill valid value into all required fields
			5. Upload valid tuning file
			6. Upload marketing material
			7. Click "Save" link
			8. Go through the tuning approval
			VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and the partner action is "Request Marketing Review"
			9. Click "Request Marketing Review" link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click "Add product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields 
		// 5. Upload valid tuning file
		// 6. Upload marketing material
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 8. Go through the tuning approval
		productWf.approveTuning();
		/*
		 * VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and the partner action is "Request Marketing Review"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "APPROVED");
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "UNSUBMITTED");
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_ACTION), "Request Marketing Review");
		// 9. Click "Request Marketing Review" link
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		/*
		 *  Verify that the marketing approval status is "Pending DTS Review" and there is no "Edit" link displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "PENDING DTS REVIEW");
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.EDIT_MODE));
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the user can edit product when the marketing approval status is "Approved"
	 */
	@Test
	public void TC040DTS_16(){
		productControl.addLog("ID : TC040DTS_16: Verify that the user can edit product when the marketing approval status is 'Approved'");
		/*
	 		Pre-condition: the tuning approval is approved. the User has "Publish and suspend Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Fill valid value into all required fields
			5. Upload valid tuning file
			6. Upload marketing material
			7. Click "Save" link
			8. Go through the tuning approval
			VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and the partner action is "Request Marketing Review"
			9. Go through the Marketing approval process
			VP: Verify that the marketing approval status is "Approved"
			10. Click "Edit" link
			11. Change product's information
			12. Click "Save" link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click "Add product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields 
		// 5. Upload valid tuning file
		// 6. Upload marketing material
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 8. Go through the tuning approval
		productWf.approveTuning();
		/*
		 * VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and the partner action is "Request Marketing Review"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "APPROVED");
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "UNSUBMITTED");
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_ACTION), "Request Marketing Review");
		// 9. Go through the Marketing approval process
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		/*
		 * VP: Verify that the marketing approval status is "Approved"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "APPROVED");
		// 10. Click "Edit" link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 11. Change product's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		productControl.editData(AddEditProductModel.MODEL_NAME, newName);
		// 12. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * The product's information is changed successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), newName);
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the user can edit product when the marketing approval status is "DECLINED"
	 */
	@Test
	public void TC040DTS_17(){
		productControl.addLog("ID : TC040DTS_17: Verify that the user can edit product when the marketing approval status is 'DECLINED'");
		/*
			Pre-condition: the tuning approval is approved. the User has "Publish and suspend Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Fill valid value into all required fields
			5. Upload valid tuning file
			6. Upload marketing material
			7. Click "Save" link
			8. Go through the tuning approval
			VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and the partner action is "Request Marketing Review"
			9. Click "Request Marketing Review" link
			10. Click "Decline Marketing" link
			VP: Verify that the marketing approval status is "DECLINED"
			11. Click "Edit" link
			12. Change product's information
			13. Click "Save" link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click "Add product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields 
		// 5. Upload valid tuning file
		// 6. Upload marketing material
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 8. Go through the tuning approval
		productWf.approveTuning();
		/*
		 * VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and the partner action is "Request Marketing Review"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "APPROVED");
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "UNSUBMITTED");
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_ACTION), "Request Marketing Review");
		// 9. Click "Request Marketing Review" link
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		// 10. Click "Decline Marketing" link
		productControl.click(ProductDetailModel.DECLINE_MARKETING);
		/*
		 * VP: Verify that the marketing approval status is "DECLINED"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "DECLINED");
		// 11. Click "Edit" link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 12. Change product's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		productControl.editData(AddEditProductModel.MODEL_NAME, newName);
		// 13. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * Verify that The product's information is changed successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), newName);
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the "PUBLISH" button is disabled if three type o product's primary image are not uploaded even when both tuning and marketing approval is approved
	 */
	@Test
	public void TC040DTS_18(){
		productControl.addLog("ID : TC040DTS_18: Verify that the 'PUBLISH' button is disabled if three type o product's primary image are not uploaded even when both tuning and marketing approval is approved");
		/*
	 		Pre-Condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table which both tuning and marketing approval are "APPROVED" but three kind of primary images are not uploaded
		 */
		/*
		 * PreCondition: Create product which tuning and marketing status is approved but three kinds of image are not uploaded
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		data.put("add marketing", AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Approve tuning
		productWf.approveTuning();
		// Approve marketing
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		/*
		 * *************************************************************
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select an product from Products table which both tuning and marketing approval are "APPROVED" but three kind of primary images are not uploaded
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * Verify that The "PUBLISH" button is disabled
		 */
		Assert.assertEquals(productControl.getAtributeValue(ProductDetailModel.PUBLISH, "class"), "button disabled");
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the DTS user can use the request revised tuning feature of partner user after the DTS tuning is revoked
	 */
	@Test
	public void TC040DTS_20(){
		productControl.addLog("ID : TC040DTS_20: Verify that the DTS user can use the request revised tuning feature of partner user after the DTS tuning is revoked");
		/*
	 		1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click �Add Product' link
			4. Fill valid value into all required fields
			5. Upload tuning file successfully
			6. Click �Save� link
			VP: Verify that the status of Tuning Approval state is �PENDING PARTNER APPROVAL�
			7. Expand the �Partner Action� link 
			8. Click �Approve Tuning� link
			VP: Verify that the status of Tuning Approval state is �APPROVED� and the �Revoke Tuning� link is also displayed
			9. Click �Revoke Tuning� link
			VP: Verify that the status of Tuning Approval sate now is changed to �REVOKED� and the �Request Revised Tuning� link is displayed below the �Partner Actions� link
			10. Click �Request Revised Tuning� link
			VP: Verify that the Request tuning form is displayed
			11. Tick to check on the term check box
			12. Click �Submit� link
			13. Click on the product name link in top page in order to return to product detail page
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click �Add Product' link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		// 5. Upload tuning file successfully
		// 6. Click �Save� link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * VP: Verify that the status of Tuning Approval state is �PENDING PARTNER APPROVAL�
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.PENDING_PARTNER_APPROVAL.getName());
		// 7. Expand the �Partner Action� link 
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		// 8. Click �Approve Tuning� link
		productWf.approveTuning();
		/*
		 * VP: Verify that the status of Tuning Approval state is �APPROVED� and the �Revoke Tuning� link is also displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.APPROVED.getName());
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.REVOKE_TUNING));
		// 9. Click �Revoke Tuning� link
		productWf.revokeTuning();
		/*
		 * VP: Verify that the status of Tuning Approval sate now is changed to �REVOKED� and the �Request Revised Tuning� link is displayed below the �Partner Actions� link
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.REVOKED.getName());
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.REQUEST_REVISED_TUNING));
		// 10. Click �Request Revised Tuning� link
		productControl.click(ProductDetailModel.REQUEST_REVISED_TUNING);
		/*
		 * VP: Verify that the Request tuning form is displayed
		 */
//		Assert.assertEquals(productControl.existsElement(TuningRequestForm.APPROVE_TUNNING_FORM()), true);
//		// 11. Tick to check on the term check box
//		productControl.selectACheckbox(TuningRequestForm.AGREE);
//		// 12. Click �Submit� link
//		productControl.click(TuningRequestForm.SUBMIT);
		// 13. Click on the product name link in top page in order to return to product detail page
		//productControl.click(TuningRequestForm.PRODUCT_LINK);
		/*
		 * Verify that the status of Tuning Approval is now changed to �DTS REQUEST PENDING� and its action links are �Upload Tuning� and the �Cancel Request� link is displayed below the �Partner Actions� link
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.DTS_REQUEST_PENDING.getName());
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.UPLOAD_TUNING));
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.CANCEL_REQUEST_TUNING));
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the status of step 1: Tuning Approval is changed to �REMOVED� after  the revoked DTS tuning file is removed.
	 */
	@Test
	public void TC040DTS_21(){
		productControl.addLog("ID : TC040DTS_21: Verify that the status of step 1: Tuning Approval is changed to �REMOVED� after  the revoked DTS tuning file is removed.");
		productControl.addErrorLog("PDPP-1072: 040D Tuning State: The 'UNSUBMITTED' instead of 'REMOVED' state is displayed after the revoked DTS tuning file is removed.");
		/*
	 		1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click �Add Product' link
			4. Fill valid value into all required fields
			5. Upload tuning file successfully
			6. Click �Save� link
			VP: Verify that the status of Tuning Approval state is �PENDING PARTNER APPROVAL�
			7. Expand the �Partner Action� link 
			8. Click �Approve Tuning� link
			VP: Verify that the status of Tuning Approval state is �APPROVED� and the �Revoke Tuning� link is also displayed
			9. Click �Revoke Tuning� link
			VP: Verify that the status of Tuning Approval sate now is changed to �REVOKED� and the �Request Revised Tuning� link is displayed below the �Partner Actions� link
			10. Click �Edit� link
			11. Delete the DTS tuning file
			12. Click �Save� link
			VP: The status of Step 1: Tuning Approval is changed to �REMOVED� and the "Request Revised Tuning" link is displayed below the �Partner Actions� link.
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click �Add Product' link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		// 5. Upload tuning file successfully
		// 6. Click �Save� link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * VP: Verify that the status of Tuning Approval state is �PENDING PARTNER APPROVAL�
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.PENDING_PARTNER_APPROVAL.getName());
		// 7. Expand the �Partner Action� link 
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		// 8. Click �Approve Tuning� link
		productWf.approveTuning();
		/*
		 * VP: Verify that the status of Tuning Approval state is �APPROVED� and the �Revoke Tuning� link is also displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.APPROVED.getName());
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.REVOKE_TUNING));
		// 9. Click �Revoke Tuning� link
		productWf.revokeTuning();
		/*
		 * VP: Verify that the status of Tuning Approval sate now is changed to �REVOKED� and the �Request Revised Tuning� link is displayed below the �Partner Actions� link
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.REVOKED.getName());
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.REQUEST_REVISED_TUNING));
		// 10. Click �Edit� link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 11. Delete the DTS tuning file
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		//12. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 *The status of Step 1: Tuning Approval is changed to �REMOVED� and the "Request Revised Tuning" link is displayed below the �Partner Actions� link.
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.REMOVED.getName());
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_REVISED_TUNING), ProductDetailModel.ProductStatus.TUNING_REQUEST_REVISED.getName());
		//Assert.assertEquals(productControl.existsElement(TuningRequestForm.APPROVE_TUNNING_FORM()), true);
//		// 11. Tick to check on the term check box
//		productControl.selectACheckbox(TuningRequestForm.AGREE);
//		// 12. Click �Submit� link
//		productControl.click(TuningRequestForm.SUBMIT);
//		// 13. Click on the product name link in top page in order to return to product detail page
//		productControl.click(TuningRequestForm.PRODUCT_LINK);
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the marketing state is changed to "UNSUBMITTED" when the tuning status is "APPROVED"
	 */
	@Test
	public void TC040DMS_01(){
		productControl.addLog("ID : TC040DMS_01 : Verify that the marketing state is changed to 'UNSUBMITTED' when the tuning status is 'APPROVED'");
		/*
			Pre-Condition: Partner user request tuning file.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Filter product for DTS Request Status
			4. Select a product from Products table.
			VP: Verify that the marketing status is "CLOSED"
			5. Click "Upload Tuning" link
			6. Upload a tuning file successfully
			7. Expand the "Partner Actions" link
			8. Click "Approve Tuning" link
			VP: Verify that the Tuning status is changed to "APPROVED" and the Marketing status is also changed to "UNSUBMITTED"

		 */
		/*
		 * PreCondition: Create product that partner user has request tuning file
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create new product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Expand Partner Action link
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		// Click Request DTS Tuning link
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		// Complete Tuning Submit form
		productControl.selectACheckbox(TuningRequestForm.AGREE);
		productControl.click(TuningRequestForm.SUBMIT);
		/*
		 * ******************************************************
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Filter product for DTS Request Status
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the marketing status is "CLOSED"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "CLOSED");
		// 5. Click "Upload Tuning" link
		productControl.click(ProductDetailModel.UPLOAD_TUNING);
		// 6. Upload a tuning file successfully
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, "A");
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// 7. Expand the "Partner Actions" link
		// 8. Click "Approve Tuning" link
		productWf.approveTuning();
		/*
		 * Verify that the Tuning status is changed to "APPROVED" and the Marketing status is also changed to "UNSUBMITTED"
		 */
		//Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.APPROVED);
		//Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), ProductDetailModel.ProductStatus.UNSUBMITTED);
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * 	Verify that the Marketing status is changed to " DTS Review", the "Approve Marketing" and "Declined Marketing" links are also displayed when the partner upload marketing materials
	 */
	@Test
	public void TC040DMS_02(){
		productControl.addLog("ID : TC040DMS_02 : Verify that the Marketing status is changed to 'DTS Review', the 'Approve Marketing' and 'Declined Marketing' links are also displayed when the partner upload marketing materials");
		/*
			Pre-Condition: The tuning state is approved.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Select a accessory from accessories table which the tuning state is "APPROVED" and the marketing material is not uploaded yet.
			VP: Verify that the marketing status is "UNSUBMITTED"
			4. Upload a marketing material file
			VP: Verify that the  "Partner Actions" link in step 2: Marketing Approval is displayed
			5. Expand the "Partner Actions" link in step 2: Marketing Approval
			6. Click "Request Marketing Review" link
		 */
		/*
		 * Pre-Condition: The tuning state is approved
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to accessories page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Click Add Accessory link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create accessory
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Approve tuning
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select a accessory from accessories table which the tuning state is "APPROVED" and the marketing material is not uploaded yet.
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the marketing status is "UNSUBMITTED"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "UNSUBMITTED");
		// 4. Upload a marketing material file
		productControl.click(ProductDetailModel.EDIT_MODE);
		productControl.uploadFile(AddEditProductModel.ADD_MARKETING, AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		productControl.clickOptionByIndex(AddEditProductModel.MARKETING_METERIAL_TYPE, 1);
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: Verify that the  "Partner Actions" link in step 2: Marketing Approval is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PARTNER_ACTIONS_STEP_2));
		// 5. Expand the "Partner Actions" link in step 2: Marketing Approval
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		// 6. Click "Request Marketing Review" link
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		/*
		 * Verify that Marketing status is changed to "PENDING DTS REVIEW", the "Approve Marketing" and "Declined Marketing" links are also displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "PENDING DTS REVIEW");
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.APPROVE_MARKETING));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.DECLINE_MARKETING));
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
	}	
		
	/*
	 * Verify that the Marketing status is changed to "APPROVED" and its action is "Revoke Marketing Approval" when DTS Marketing approve the marketing materials
	 */
	@Test
	public void TC040DMS_03(){
		productControl.addLog("ID : TC040DMS_03 : Verify that the Marketing statsus is changed to 'APPROVED' and its action is 'Revoke Marketing Approval' when DTS Marketing approve the marketing materials");
		/*
			Pre-Condition: The tuning state is approved.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Select a accessory from accessories table which the tuning state is "APPROVED" and the marketing material is not uploaded yet.
			VP: Verify that the marketing status is "UNSUBMITTED"
			4. Upload a marketing material file
			VP: Verify that the  "Partner Actions" link in step 2: Marketing Approval is displayed
			5. Expand the "Partner Actions" link in step 2 : Marketing Approval
			6. Click "Request Marketing Review" link
			VP:Marketing status is changed to "PENDING DTS REVIEW", the "Approve Marketing" and "Declined Marketing" links are also displayed 
			7. Click "Approve Marketing" link
		 */
		/*
		 * Pre-Condition: The tuning state is approved
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to accessories page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Click Add Accessory link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create accessory
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Approve tuning
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select a accessory from accessories table which the tuning state is "APPROVED" and the marketing material is not uploaded yet.
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the marketing status is "UNSUBMITTED"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "UNSUBMITTED");
		// 4. Upload a marketing material file
		productControl.click(ProductDetailModel.EDIT_MODE);
		productControl.uploadFile(AddEditProductModel.ADD_MARKETING, AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		productControl.clickOptionByIndex(AddEditProductModel.MARKETING_METERIAL_TYPE, 1);
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: Verify that the  "Partner Actions" link in step 2: Marketing Approval is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PARTNER_ACTIONS_STEP_2));
		// 5. Expand the "Partner Actions" link in step 2: Marketing Approval
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		// 6. Click "Request Marketing Review" link
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		/*
		 * Verify that Marketing status is changed to "PENDING DTS REVIEW", the "Approve Marketing" and "Declined Marketing" links are also displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "PENDING DTS REVIEW");
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.APPROVE_MARKETING));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.DECLINE_MARKETING));
		// 7. Click "Approve Marketing" link
		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		/*
		 * Verify that Marketing status is changed to "APPROVED" and its action is "Revoke Marketing Approval" 
		 */
		Assert.assertTrue(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS).equalsIgnoreCase(ProductDetailModel.ProductStatus.APPROVED.getName()));
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_ACTION), "Revoke Marketing Approval");
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the Marketing status is changed to "DECLINED" when DTS Marketing declines the marketing materials
	 */
	@Test
	public void TC040DMS_04(){
		productControl.addLog("ID : TC040DMS_04 : Verify that the Marketing statsus is changed to 'DECLINED' when DTS Marketing declines the marketing materials");
		/*
			Pre-Condition: The tuning state is approved.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Select a accessory from accessories table which the tuning state is "APPROVED" and the marketing material is not uploaded yet.
			VP: Verify that the marketing status is "UNSUBMITTED"
			4. Upload a marketing material file
			VP: Verify that the  "Partner Actions" link in step 2: Marketing Approval is displayed
			5. Expand the "Partner Actions" link in step 2 : Marketing Approval
			6. Click "Request Marketing Review" link
			VP:Marketing status is changed to " DTS Review", the "Approve Marketing" and "Declined Marketing" links are also displayed 
			7. Click "Decline Marketing" link
		 */
		/*
		 * Pre-Condition: The tuning state is approved
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to accessories page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Click Add Accessory link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create accessory
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Approve tuning
		productWf.approveTuning();
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select a accessory from accessories table which the tuning state is "APPROVED" and the marketing material is not uploaded yet.
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the marketing status is "UNSUBMITTED"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "UNSUBMITTED");
		// 4. Upload a marketing material file
		productControl.click(ProductDetailModel.EDIT_MODE);
		productControl.uploadFile(AddEditProductModel.ADD_MARKETING, AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		productControl.clickOptionByIndex(AddEditProductModel.MARKETING_METERIAL_TYPE, 1);
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: Verify that the  "Partner Actions" link in step 2: Marketing Approval is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PARTNER_ACTIONS_STEP_2));
		// 5. Expand the "Partner Actions" link in step 2: Marketing Approval
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		// 6. Click "Request Marketing Review" link
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		/*
		 * Verify that Marketing status is changed to "PENDING DTS REVIEW", the "Approve Marketing" and "Declined Marketing" links are also displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "PENDING DTS REVIEW");
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.APPROVE_MARKETING));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.DECLINE_MARKETING));
		// 7. Click "Decline Marketing" link
		productControl.click(ProductDetailModel.DECLINE_MARKETING);
		/*
		 * Verify that Marketing status is changed to "DECLINED"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "DECLINED");
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the Marketing status is changed to "REVOKED" when DTS Marketing revokes the marketing materials after approved
	 */
	@Test
	public void TC040DMS_05(){
		productControl.addLog("ID : TC040DMS_05 : Verify that the Marketing status is changed to 'REVOKED' when DTS Marketing revokes the marketing materials after approved");
		/*
			Pre-Condition: The tuning state is approved.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Select a accessory from accessories table which the tuning state is "APPROVED" and the marketing material is not uploaded yet.
			VP: Verify that the marketing status is "UNSUBMITTED"
			4. Upload a marketing material file
			VP: Verify that the  "Partner Actions" link in step 2: Marketing Approval is displayed
			5. Expand the "Partner Actions" link in step 2 : Marketing Approval
			6. Click "Request Marketing Review" link
			VP:Marketing status is changed to " DTS Review", the "Approve Marketing" and "Declined Marketing" links are also displayed 
			7. Click "Approve Marketing" link
			VP: Marketing status is changed to "APPROVED" and its action is "Revoke Marketing Approval" 
			8. Click "Revoke Marketing Approval" link
		 */
		/*
		 * Pre-Condition: The tuning state is approved
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to accessories page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Click Add Accessory link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create accessory
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Approve tuning
		productWf.approveTuning();
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select a accessory from accessories table which the tuning state is "APPROVED" and the marketing material is not uploaded yet.
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the marketing status is "UNSUBMITTED"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "UNSUBMITTED");
		// 4. Upload a marketing material file
		productControl.click(ProductDetailModel.EDIT_MODE);
		productControl.uploadFile(AddEditProductModel.ADD_MARKETING, AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		productControl.clickOptionByIndex(AddEditProductModel.MARKETING_METERIAL_TYPE, 1);
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: Verify that the  "Partner Actions" link in step 2: Marketing Approval is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PARTNER_ACTIONS_STEP_2));
		// 5. Expand the "Partner Actions" link in step 2: Marketing Approval
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		// 6. Click "Request Marketing Review" link
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		/*
		 * Verify that Marketing status is changed to "PENDING DTS REVIEW", the "Approve Marketing" and "Declined Marketing" links are also displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "PENDING DTS REVIEW");
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.APPROVE_MARKETING));
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.DECLINE_MARKETING));
		// 7. Click "Approve Marketing" link
		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		/*
		 * VP: Marketing status is changed to "APPROVED" and its action is "Revoke Marketing Approval" 
		 */
		Assert.assertTrue(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS).equalsIgnoreCase(ProductDetailModel.ProductStatus.APPROVED.getName()));
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_ACTION), "Revoke Marketing Approval");
		// 8. Click "Revoke Marketing Approval" link
		productControl.click(ProductDetailModel.REVOKE_MARKETING_APPROVAL);
		/*
		 * Verify that Marketing status is changed to "REVOKED" and the �Partner Actions� with �Request Marketing Review� links are displayed below
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "REVOKED");
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.PARTNER_ACTIONS_STEP_2));
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_ACTION), "Request Marketing Review");
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
	}
}
