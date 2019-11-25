package com.dts.adminportal.dts.test;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
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
import dts.com.adminportal.model.TuningRequestForm;
import dts.com.adminportal.model.Xpath;

public class DTSUserCatalog040DTuningState extends CreatePage{
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
	 * Verify that when there is no tuning is uploaded, the tuning status is "UNSUBMITTED"
	 */
	@Test
	public void TC040DTS_01(){
		result.addLog("ID : TC040DTS_01: Verify that when there is no tuning is uploaded, the tuning status is 'UNSUBMITTED'");
		/*
	 		Pre-condition: User has "Request product tunings" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Fill valid value into all required fields
			5. Do not upload tuning
			6. Click "Save" link
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields
		// 5. Do not upload tuning
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * Verify that The 040D product Detail page is displayed and the tuning status is "UNSUBMITTED"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "UNSUBMITTED");
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that when the partner request tuning file, the tuning action link is "Upload Tuning" and tuning status is "DTS REQUEST"
	 */
	@Test
	public void TC040DTS_02(){
		result.addLog("ID : TC040DTS_02: Verify that when the partner request tuning file, the tuning action link is 'Upload Tuning' and tuning status is 'DTS REQUEST'");
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
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Request tuning for partner
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.REQUEST_TUNING);
		// Submit tuning form
		home.selectACheckbox(TuningRequestForm.AGREE);
		home.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select an product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * The 040D product Detail page is displayed and the tuning action link is "Upload Tuning", tuning status is "DTS REQUEST PENDING"
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.UPLOAD_TUNING));
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "DTS REQUEST PENDING");
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the portal is redirect to 051 Model Edit page when clicking on "Upload Tuning" link in step 1 : Tuning Approval
	 */
	@Test
	public void TC040DTS_03(){
		result.addLog("ID : TC040DTS_03: Verify that the portal is redirect to 051 Model Edit page when clicking on 'Upload Tuning' link in step 1 : Tuning Approval");
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
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Request tuning for partner
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.REQUEST_TUNING);
		// Submit tuning form
		home.selectACheckbox(TuningRequestForm.AGREE);
		home.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		// 5. Click "Upload Tuning" link
		home.click(AccessoryInfo.UPLOAD_TUNING);
		/*
		 * Verify that The 051 Model Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(), "Pass");
		// Delete product
		home.click(AddAccessory.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the "Cancel Request" link is displayed when clicking on "Partner Actions" link when tuning status is "DTS Request Pending"
	 */
	@Test
	public void TC040DTS_04(){
		result.addLog("ID : TC040DTS_04: Verify that the 'Cancel Request' link is displayed when clicking on 'Partner Actions' link when tuning status is 'DTS Request Pending'");
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
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Request tuning for partner
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.REQUEST_TUNING);
		// Submit tuning form
		home.selectACheckbox(TuningRequestForm.AGREE);
		home.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		// 5. Click "Partner Actions" link
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		/*
		 * Verify that The "Cancel Request" link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.CANCEL_REQUEST_TUNING));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the tuning approval is changed to "UNSUBMIT" when the DTS user clicks on "Cancel Request" link
	 */
	@Test
	public void TC040DTS_05(){
		result.addLog("ID : TC040DTS_05: Verify that the tuning approval is changed to 'UNSUBMIT' when the DTS user clicks on 'Cancel Request' link");
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
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Request tuning for partner
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.REQUEST_TUNING);
		// Submit tuning form
		home.selectACheckbox(TuningRequestForm.AGREE);
		home.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		// 5. Click "Partner Actions" link
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		// 6. Click "Cancel Request" link
		home.click(AccessoryInfo.CANCEL_REQUEST_TUNING);
		/*
		 * Verify that Tuning approval is changed to "UNSUBMITTED" 
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "UNSUBMITTED");
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the Tuning Approval is changed to "Pending Partner Approval" when DTS user upload tuning file
	 */
	@Test
	public void TC040DTS_06(){
		result.addLog("ID : TC040DTS_06: Verify that the Tuning Approval is changed to 'Pending Partner Approval' when DTS user upload tuning file");
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
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Request tuning for partner
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.REQUEST_TUNING);
		// Submit tuning form
		home.selectACheckbox(TuningRequestForm.AGREE);
		home.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		// 5. Click "Upload Tuning" link
		home.click(AccessoryInfo.UPLOAD_TUNING);
		// 6. Upload a tuning file successfully
		home.uploadFile(AddAccessory.ADD_TUNNING, Constant.tuning_hpxtt);
		home.click(AddAccessory.SAVE);
		/*
		 * Verify that The Tuning Approval is changed to "Pending Partner Approval"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "PENDING PARTNER APPROVAL");
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the "Send Reminder", "Edit Headphone:X Certification" and Partner Actions" links are displayed when the tuning status is "Pending Partner Approval"
	 */
	@Test
	public void TC040DTS_07(){
		result.addLog("ID : TC040DTS_07: Verify that the 'Send Reminder', 'Edit Headphone:X Certification' and Partner Actions links are displayed when the tuning status is 'Pending Partner Approval'");
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
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Request tuning for partner
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.REQUEST_TUNING);
		// Submit tuning form
		home.selectACheckbox(TuningRequestForm.AGREE);
		home.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		// 5. Click "Upload Tuning" link
		home.click(AccessoryInfo.UPLOAD_TUNING);
		// 6. Upload a tuning file successfully
		home.uploadFile(AddAccessory.ADD_TUNNING, Constant.tuning_hpxtt);
		home.click(AddAccessory.SAVE);
		/*
		 * Verify that the tuning status is changed to "Pending Partner Approval" and  the "Send Reminder", "Edit Headphone:X Certification" and Partner Actions" links are displayed in step 1: Tuning Approval
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "PENDING PARTNER APPROVAL");
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.SEND_REMINDER));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.EDIT_HEADPHONE_CERTIFICATION));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.PARTNER_ACTIONS_STEP_1));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the Tuning status is changed to "Approved" and its action are "Revoke Tuning" and "Edit Heaphone: X Certification" links when DTS user clicks on "Approve Tuning" link
	 */
	@Test
	public void TC040DTS_08(){
		result.addLog("ID : TC040DTS_08: Verify that the Tuning status is changed to 'Approved' and its action are 'Revoke Tuning' and 'Edit Heaphone: X Certification' links when DTS user clicks on 'Approve Tuning' link");
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
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Request tuning for partner
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.REQUEST_TUNING);
		// Submit tuning form
		home.selectACheckbox(TuningRequestForm.AGREE);
		home.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		// 5. Click "Upload Tuning" link
		home.click(AccessoryInfo.UPLOAD_TUNING);
		// 6. Upload a tuning file successfully
		home.uploadFile(AddAccessory.ADD_TUNNING, Constant.tuning_hpxtt);
		home.selectOptionByName(AddAccessory.TUNING_RATING, "A");
		home.click(AddAccessory.SAVE);
		// 7. Expand the "Partner Actions" link
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		// 8. Click "Approve Tuning" link
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * Verify that Tuning status is changed to "Approved" and its action are "Revoke Tuning" and "Edit Headphone: X Certification" links
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "APPROVED");
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.EDIT_HEADPHONE_CERTIFICATION));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.REVOKE_TUNING));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that clicking on "Edit Headphone:X Certification" link loads the 051D Model Edit page
	 */
	@Test
	public void TC040DTS_09(){
		result.addLog("ID : TC040DTS_09: Verify that clicking on 'Edit Headphone:X Certification' link loads the 051D Model Edit page");
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
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Request tuning for partner
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.REQUEST_TUNING);
		// Submit tuning form
		home.selectACheckbox(TuningRequestForm.AGREE);
		home.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		// 5. Click "Upload Tuning" link
		home.click(AccessoryInfo.UPLOAD_TUNING);
		// 6. Upload a tuning file successfully
		home.uploadFile(AddAccessory.ADD_TUNNING, Constant.tuning_hpxtt);
		home.selectOptionByName(AddAccessory.TUNING_RATING, "A");
		home.click(AddAccessory.SAVE);
		// 7. Expand the "Partner Actions" link
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		// 8. Click "Approve Tuning" link
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * Verify that Tuning status is changed to "Approved" and its action are "Revoke Tuning" and "Edit Headphone: X Certification" links
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "APPROVED");
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.EDIT_HEADPHONE_CERTIFICATION));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.REVOKE_TUNING));
		// 9. Click "Edit Headphone: X Certification" links
		home.click(AccessoryInfo.EDIT_HEADPHONE_CERTIFICATION);
		/*
		 * Verify that 051D Model Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(), "Pass");
		// Delete product
		home.click(AddAccessory.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the tuning status is changed to "REVOKED" when the DTS admin revokes the Partner tuning
	 */
	@Test
	public void TC040DTS_10(){
		result.addLog("ID : TC040DTS_10: Verify that the tuning status is changed to 'REVOKED' when the DTS admin revokes the Partner tuning");
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
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Request tuning for partner
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.REQUEST_TUNING);
		// Submit tuning form
		home.selectACheckbox(TuningRequestForm.AGREE);
		home.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		// 5. Click "Upload Tuning" link
		home.click(AccessoryInfo.UPLOAD_TUNING);
		// 6. Upload a tuning file successfully
		home.uploadFile(AddAccessory.ADD_TUNNING, Constant.tuning_hpxtt);
		home.selectOptionByName(AddAccessory.TUNING_RATING, "A");
		home.click(AddAccessory.SAVE);
		/*
		 * VP: Verify that the tuning status is changed to "PENDING PARTNER APPROVAL"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "PENDING PARTNER APPROVAL");
		// 7. Expand the "Partner Actions" link
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		// 8. Click "Approve Tuning" link
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * Verify that Tuning status is changed to "Approved" and its action are "Revoke Tuning" and "Edit Headphone: X Certification" links
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "APPROVED");
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.EDIT_HEADPHONE_CERTIFICATION));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.REVOKE_TUNING));
		// 9. Click "Revoke Tuning" links
		home.click(AccessoryInfo.REVOKE_TUNING);
		/*
		 * Verify that Tuning status is changed to "REVOKED"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "REVOKED");
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the tuning status is "DECLINED" when the partner decline the DTS Tuning
	 */
	@Test
	public void TC040DTS_11(){
		result.addLog("ID : TC040DTS_11: Verify that the tuning status is 'DECLINED' when the partner decline the DTS Tuning");
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
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Request tuning for partner
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.REQUEST_TUNING);
		// Submit tuning form
		home.selectACheckbox(TuningRequestForm.AGREE);
		home.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		// 5. Click "Upload Tuning" link
		home.click(AccessoryInfo.UPLOAD_TUNING);
		// 6. Upload a tuning file successfully
		home.uploadFile(AddAccessory.ADD_TUNNING, Constant.tuning_hpxtt);
		home.selectOptionByName(AddAccessory.TUNING_RATING, "A");
		home.click(AddAccessory.SAVE);
		/*
		 * VP: Verify that the tuning status is changed to "PENDING PARTNER APPROVAL"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "PENDING PARTNER APPROVAL");
		// 7. Expand the "Partner Actions" link
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		// 8. Click "Decline Tuning" link
		home.click(AccessoryInfo.DECLINE_TUNING);
		/*
		 * Verify that The tuning status is changed to "DECLINED"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "DECLINED");
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the tuning status is changed to "DTS REQUEST PENDING" when the partner request revised tuning
	 */
	@Test
	public void TC040DTS_12(){
		result.addLog("ID : TC040DTS_12: Verify that the tuning status is changed to 'DTS REQUEST PENDING' when the partner request revised tuning");
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
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Request tuning for partner
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.REQUEST_TUNING);
		// Submit tuning form
		home.selectACheckbox(TuningRequestForm.AGREE);
		home.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		// 5. Click "Upload Tuning" link
		home.click(AccessoryInfo.UPLOAD_TUNING);
		// 6. Upload a tuning file successfully
		home.uploadFile(AddAccessory.ADD_TUNNING, Constant.tuning_hpxtt);
		home.selectOptionByName(AddAccessory.TUNING_RATING, "A");
		home.click(AddAccessory.SAVE);
		// 7. Expand the "Partner Actions" link
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		// 8. Click "Decline Tuning" link
		home.click(AccessoryInfo.DECLINE_TUNING);
		// 9. Expand the "Partner Actions" link
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		// 10. Click "Request Revised Tuning"
		home.click(AccessoryInfo.REQUEST_REVISED_TUNING);
		// 11. Submit Tuning request form
		home.selectACheckbox(TuningRequestForm.AGREE);
		home.click(TuningRequestForm.SUBMIT);
		/*
		 * Verify that The tuning status is changed to "DTS Request Pending" and its action is "Upload Tuning"
		 */
		home.click(TuningRequestForm.PRODUCT_LINK);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "DTS REQUEST PENDING");
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.UPLOAD_TUNING));
		//Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the tuning status is "DECLINED" when partner cancel the revision request
	 */
	@Test
	public void TC040DTS_13(){
		result.addLog("ID : TC040DTS_13: Verify that the tuning status is 'DECLINED' when partner cancel the revision request");
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
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Request tuning for partner
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.REQUEST_TUNING);
		// Submit tuning form
		home.selectACheckbox(TuningRequestForm.AGREE);
		home.click(TuningRequestForm.SUBMIT);
		/*
		 * *******************************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Filter product for Tuning Request Pending
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		// 5. Click "Upload Tuning" link
		home.click(AccessoryInfo.UPLOAD_TUNING);
		// 6. Upload a tuning file successfully
		home.uploadFile(AddAccessory.ADD_TUNNING, Constant.tuning_hpxtt);
		home.selectOptionByName(AddAccessory.TUNING_RATING, "A");
		home.click(AddAccessory.SAVE);
		/*
		 * VP: Verify that the tuning status is changed to "PENDING PARTNER APPROVAL"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "PENDING PARTNER APPROVAL");
		// 7. Expand the "Partner Actions" link
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		// 8. Click "Decline Tuning" link
		home.click(AccessoryInfo.DECLINE_TUNING);
		/*
		 *  VP. The  tuning status is changed to "DECLINED"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "DECLINED");
		// 9. Expand the "Partner Actions" link
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		// 10. Click "Request Revised Tuning"
		home.click(AccessoryInfo.REQUEST_REVISED_TUNING);
		// Submit Tuning request form
		home.selectACheckbox(TuningRequestForm.AGREE);
		home.click(TuningRequestForm.SUBMIT);
		// Navigate to Product Detail page
		home.click(TuningRequestForm.PRODUCT_LINK);
		/*
		 * VP: The  tuning status is changed to "DTS Request Pending" and its action is "Upload Tuning"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "DTS REQUEST PENDING");
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.UPLOAD_TUNING));
		// 11. Expand the "Partner Actions" link
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		// 12. Click "Cancel Request" link
		home.click(AccessoryInfo.CANCEL_REQUEST_TUNING);
		// Check if PDPP-1265 found
		if(home.getTextByXpath(AccessoryInfo.TUNING_STATUS).equals("UNSUBMITTED")){
			result.addErrorLog("PDPP-1265: 040 Product Detail: Tuning status in Tuning Approval process is 'Unsubmitted' instead of 'Declined' after Partner User cancels 'Request Revised Tuning'");
			Assert.assertTrue(false);
		}
		/*
		 * Verify that The tuning status is changed to "DECLINED"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "DECLINED");
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
		
		
	}
	
	/*
	 * Verify that the user can edit product when the marketing approval status is "UNSUBMITTED"
	 */
	@Test
	public void TC040DTS_14(){
		result.addLog("ID : TC040DTS_14: Verify that the user can edit product when the marketing approval status is 'UNSUBMITTED'");
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
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields
		// 5. Upload valid tuning file
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// 7. Go through the tuning approval
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "APPROVED");
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "UNSUBMITTED");
		// 8. Click "Edit" link
		home.click(AccessoryInfo.EDIT_MODE);
		// 9. Change product's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		home.editData(AddAccessory.NAME, newName);
		// 10. Click "Save" link
		home.click(AddAccessory.SAVE);
		/*
		 * Verify that The product's information is changed successfully
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), newName);
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the user can not edit product when the marketing approval status is "Pending DTS Review"
	 */
	@Test
	public void TC040DTS_15(){
		result.addLog("ID : TC040DTS_15: Verify that the user can not edit product when the marketing approval status is 'Pending DTS Review'");
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
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields 
		// 5. Upload valid tuning file
		// 6. Upload marketing material
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// 8. Go through the tuning approval
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and the partner action is "Request Marketing Review"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "APPROVED");
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "UNSUBMITTED");
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_ACTION), "Request Marketing Review");
		// 9. Click "Request Marketing Review" link
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		/*
		 *  Verify that the marketing approval status is "Pending DTS Review" and there is no "Edit" link displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "PENDING DTS REVIEW");
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.EDIT_MODE));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the user can edit product when the marketing approval status is "Approved"
	 */
	@Test
	public void TC040DTS_16(){
		result.addLog("ID : TC040DTS_16: Verify that the user can edit product when the marketing approval status is 'Approved'");
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
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields 
		// 5. Upload valid tuning file
		// 6. Upload marketing material
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// 8. Go through the tuning approval
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and the partner action is "Request Marketing Review"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "APPROVED");
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "UNSUBMITTED");
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_ACTION), "Request Marketing Review");
		// 9. Go through the Marketing approval process
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		home.click(AccessoryInfo.APPROVE_MARKETING);
		/*
		 * VP: Verify that the marketing approval status is "Approved"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "APPROVED");
		// 10. Click "Edit" link
		home.click(AccessoryInfo.EDIT_MODE);
		// 11. Change product's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		home.editData(AddAccessory.NAME, newName);
		// 12. Click "Save" link
		home.click(AddAccessory.SAVE);
		/*
		 * The product's information is changed successfully
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), newName);
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the user can edit product when the marketing approval status is "DECLINED"
	 */
	@Test
	public void TC040DTS_17(){
		result.addLog("ID : TC040DTS_17: Verify that the user can edit product when the marketing approval status is 'DECLINED'");
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
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields 
		// 5. Upload valid tuning file
		// 6. Upload marketing material
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// 8. Go through the tuning approval
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and the partner action is "Request Marketing Review"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "APPROVED");
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "UNSUBMITTED");
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_ACTION), "Request Marketing Review");
		// 9. Click "Request Marketing Review" link
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		// 10. Click "Decline Marketing" link
		home.click(AccessoryInfo.DECLINE_MARKETING);
		/*
		 * VP: Verify that the marketing approval status is "DECLINED"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "DECLINED");
		// 11. Click "Edit" link
		home.click(AccessoryInfo.EDIT_MODE);
		// 12. Change product's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		home.editData(AddAccessory.NAME, newName);
		// 13. Click "Save" link
		home.click(AddAccessory.SAVE);
		/*
		 * Verify that The product's information is changed successfully
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), newName);
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the "PUBLISH" button is disabled if three type o product's primary image are not uploaded even when both tuning and marketing approval is approved
	 */
	@Test
	public void TC040DTS_18(){
		result.addLog("ID : TC040DTS_18: Verify that the 'PUBLISH' button is disabled if three type o product's primary image are not uploaded even when both tuning and marketing approval is approved");
		/*
	 		Pre-Condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table which both tuning and marketing approval are "APPROVED" but three kind of primary images are not uploaded
		 */
		/*
		 * PreCondition: Create product which tuning and marketing status is approved but three kinds of image are not uploaded
		 */
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryTuning();
		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Approve tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);	
		// Approve marketing
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		home.click(AccessoryInfo.APPROVE_MARKETING);
		/*
		 * *************************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table which both tuning and marketing approval are "APPROVED" but three kind of primary images are not uploaded
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * Verify that The "PUBLISH" button is disabled
		 */
		Assert.assertEquals(home.getAtribute(AccessoryInfo.PUBLISH, "class"), "button disabled");
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user user can publish product successfully
	 */
	@Test
	public void TC040DTS_19(){
		result.addLog("ID : TC040DTS_19: Verify that the DTS user user can publish product successfully");
		/*
	 		Pre-Condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table which both tuning and marketing approval are "APPROVED" and three kind of primary images is are uploaded
			VP: The "PUBLISH" button is enabled
			4. Click "PUBLISH" button
		 */
		/*
		 * PreCondition: Create product which is ready to publish
		 */
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Approve tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);	
		// Approve marketing
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		home.click(AccessoryInfo.APPROVE_MARKETING);
		/*
		 * *************************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table which both tuning and marketing approval are "APPROVED" and three kind of primary images is are uploaded
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: The "PUBLISH" button is enabled
		 */
		Assert.assertEquals(home.getAtribute(AccessoryInfo.PUBLISH, "class"), "button button-warning");
		// 4. Click "PUBLISH" button
		home.click(AccessoryInfo.PUBLISH);
		/*
		 * Verify that The status of Step 3: Publishing is "PUBLISHED"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.PUBLISH_STATUS), "PUBLISHED");
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user can use the request revised tuning feature of partner user after the DTS tuning is revoked
	 */
	@Test
	public void TC040DTS_20(){
		result.addLog("ID : TC040DTS_20: Verify that the DTS user can use the request revised tuning feature of partner user after the DTS tuning is revoked");
		/*
	 		1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click “Add Product' link
			4. Fill valid value into all required fields
			5. Upload tuning file successfully
			6. Click “Save” link
			VP: Verify that the status of Tuning Approval state is “PENDING PARTNER APPROVAL”
			7. Expand the “Partner Action” link 
			8. Click “Approve Tuning” link
			VP: Verify that the status of Tuning Approval state is “APPROVED” and the “Revoke Tuning” link is also displayed
			9. Click “Revoke Tuning” link
			VP: Verify that the status of Tuning Approval sate now is changed to “REVOKED” and the “Request Revised Tuning” link is displayed below the “Partner Actions” link
			10. Click “Request Revised Tuning” link
			VP: Verify that the Request tuning form is displayed
			11. Tick to check on the term check box
			12. Click “Submit” link
			13. Click on the product name link in top page in order to return to product detail page
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click “Add Product' link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields
		// 5. Upload tuning file successfully
		// 6. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: Verify that the status of Tuning Approval state is “PENDING PARTNER APPROVAL”
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.PENDING_PARTNER_APPROVAL);
		// 7. Expand the “Partner Action” link 
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		// 8. Click “Approve Tuning” link
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * VP: Verify that the status of Tuning Approval state is “APPROVED” and the “Revoke Tuning” link is also displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.APPROVED);
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.REVOKE_TUNING));
		// 9. Click “Revoke Tuning” link
		home.click(AccessoryInfo.REVOKE_TUNING);
		/*
		 * VP: Verify that the status of Tuning Approval sate now is changed to “REVOKED” and the “Request Revised Tuning” link is displayed below the “Partner Actions” link
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.REVOKED);
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.REQUEST_REVISED_TUNING));
		// 10. Click “Request Revised Tuning” link
		home.click(AccessoryInfo.REQUEST_REVISED_TUNING);
		/*
		 * VP: Verify that the Request tuning form is displayed
		 */
		Assert.assertEquals(home.existsElement(TuningRequestForm.APPROVE_TUNNING_FORM()).getResult(), "Pass");
		// 11. Tick to check on the term check box
		home.selectACheckbox(TuningRequestForm.AGREE);
		// 12. Click “Submit” link
		home.click(TuningRequestForm.SUBMIT);
		// 13. Click on the product name link in top page in order to return to product detail page
		home.click(TuningRequestForm.PRODUCT_LINK);
		/*
		 * Verify that the status of Tuning Approval is now changed to “DTS REQUEST PENDING” and its action links are “Upload Tuning” and the “Cancel Request” link is displayed below the “Partner Actions” link
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.DTS_REQUEST_PENDING);
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.UPLOAD_TUNING));
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.CANCEL_REQUEST_TUNING));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the status of step 1: Tuning Approval is changed to “REMOVED” after  the revoked DTS tuning file is removed.
	 */
	@Test
	public void TC040DTS_21(){
		result.addLog("ID : TC040DTS_21: Verify that the status of step 1: Tuning Approval is changed to “REMOVED” after  the revoked DTS tuning file is removed.");
		result.addErrorLog("PDPP-1072: 040D Tuning State: The 'UNSUBMITTED' instead of 'REMOVED' state is displayed after the revoked DTS tuning file is removed.");
		/*
	 		1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click “Add Product' link
			4. Fill valid value into all required fields
			5. Upload tuning file successfully
			6. Click “Save” link
			VP: Verify that the status of Tuning Approval state is “PENDING PARTNER APPROVAL”
			7. Expand the “Partner Action” link 
			8. Click “Approve Tuning” link
			VP: Verify that the status of Tuning Approval state is “APPROVED” and the “Revoke Tuning” link is also displayed
			9. Click “Revoke Tuning” link
			VP: Verify that the status of Tuning Approval sate now is changed to “REVOKED” and the “Request Revised Tuning” link is displayed below the “Partner Actions” link
			10. Click “Edit” link
			11. Delete the DTS tuning file
			12. Click “Save” link
			VP: The status of Step 1: Tuning Approval is changed to “REMOVED” and the "Request Revised Tuning" link is displayed below the “Partner Actions” link.
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click “Add Product' link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields
		// 5. Upload tuning file successfully
		// 6. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: Verify that the status of Tuning Approval state is “PENDING PARTNER APPROVAL”
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.PENDING_PARTNER_APPROVAL);
		// 7. Expand the “Partner Action” link 
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		// 8. Click “Approve Tuning” link
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * VP: Verify that the status of Tuning Approval state is “APPROVED” and the “Revoke Tuning” link is also displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.APPROVED);
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.REVOKE_TUNING));
		// 9. Click “Revoke Tuning” link
		home.click(AccessoryInfo.REVOKE_TUNING);
		/*
		 * VP: Verify that the status of Tuning Approval sate now is changed to “REVOKED” and the “Request Revised Tuning” link is displayed below the “Partner Actions” link
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.REVOKED);
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.REQUEST_REVISED_TUNING));
		// 10. Click “Edit” link
		home.click(AccessoryInfo.EDIT_MODE);
		// 11. Delete the DTS tuning file
		home.doDelete(AddAccessory.DELETE_UPLOADED_TUNING);
		//12. Click “Save” link
		home.click(AddAccessory.SAVE);
		/*
		 *The status of Step 1: Tuning Approval is changed to “REMOVED” and the "Request Revised Tuning" link is displayed below the “Partner Actions” link.
		 */
		Assert.assertEquals(home.existsElement(TuningRequestForm.APPROVE_TUNNING_FORM()).getResult(), "Pass");
		// 11. Tick to check on the term check box
		home.selectACheckbox(TuningRequestForm.AGREE);
		// 12. Click “Submit” link
		home.click(TuningRequestForm.SUBMIT);
		// 13. Click on the product name link in top page in order to return to product detail page
		home.click(TuningRequestForm.PRODUCT_LINK);
		/*
		 * The status of Step 1: Tuning Approval is changed to “REMOVED” and the "Request Revised Tuning" link is displayed below the “Partner Actions” link.
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.REMOVED);
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_REVISED_TUNING), AccessoryInfo.TUNING_REQUEST_REVISED);
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
}
