package com.dts.adminportal.dts.test;

import java.util.Hashtable;

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

public class DTSUserCatalog040DMarketingState extends CreatePage{
	private HomeController home;
	
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}
	
	@BeforeMethod
	public void loginBeforeTest(){
		home.login(superUsername, superUserpassword);
	}
	
	/*
	 * Verify that the marketing state is changed to "UNSUBMITTED" when the tuning status is "APPROVED"
	 */
	@Test
	public void TC040DMS_01(){
		result.addLog("ID : TC040DMS_01 : Verify that the marketing state is changed to 'UNSUBMITTED' when the tuning status is 'APPROVED'");
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
		 */
		/*
		 * PreCondition: Create product that partner user has request tuning file
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Expand Partner Action link
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		// Click Request DTS Tuning link
		home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		// Complete Tuning Submit form
		home.selectACheckbox(TuningRequestForm.AGREE);
		home.click(TuningRequestForm.SUBMIT);
		/*
		 * ******************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Filter product for DTS Request Status
		home.selectOptionByName(AccessoryMain.PRODUCT_FILTER, "Tuning Request Pending");
		// 4. Select a product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the marketing status is "CLOSED"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "CLOSED");
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
		 * Verify that the Tuning status is changed to "APPROVED" and the Marketing status is also changed to "UNSUBMITTED"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.APPROVED);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), AccessoryInfo.UNSUBMITTED);
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * 	Verify that the Marketing status is changed to " DTS Review", the "Approve Marketing" and "Declined Marketing" links are also displayed when the partner upload marketing materials
	 */
	@Test
	public void TC040DMS_02(){
		result.addLog("ID : TC040DMS_02 : Verify that the Marketing status is changed to 'DTS Review', the 'Approve Marketing' and 'Declined Marketing' links are also displayed when the partner upload marketing materials");
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
		// Navigate to accessories page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add Accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create accessory
		Hashtable<String,String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Approve tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Select a accessory from accessories table which the tuning state is "APPROVED" and the marketing material is not uploaded yet.
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the marketing status is "UNSUBMITTED"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "UNSUBMITTED");
		// 4. Upload a marketing material file
		home.click(AccessoryInfo.EDIT_MODE);
		home.uploadFile(AddAccessory.ADD_MARKETING, Constant.AUDIO_ROUTE_FILE);
		home.clickOptionByIndex(AddAccessory.MARKETING_METERIAL_TYPE, 1);
		home.click(AddAccessory.SAVE);
		/*
		 * VP: Verify that the  "Partner Actions" link in step 2: Marketing Approval is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.PARTNER_ACTIONS_STEP_2));
		// 5. Expand the "Partner Actions" link in step 2: Marketing Approval
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		// 6. Click "Request Marketing Review" link
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		/*
		 * Verify that Marketing status is changed to "PENDING DTS REVIEW", the "Approve Marketing" and "Declined Marketing" links are also displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "PENDING DTS REVIEW");
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.APPROVE_MARKETING));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.DECLINE_MARKETING));
		// Delete accessory
		home.doDelete(AccessoryInfo.DELETE);
	}	
		
	/*
	 * Verify that the Marketing status is changed to "APPROVED" and its action is "Revoke Marketing Approval" when DTS Marketing approve the marketing materials
	 */
	@Test
	public void TC040DMS_03(){
		result.addLog("ID : TC040DMS_03 : Verify that the Marketing statsus is changed to 'APPROVED' and its action is 'Revoke Marketing Approval' when DTS Marketing approve the marketing materials");
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
		// Navigate to accessories page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add Accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create accessory
		Hashtable<String,String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Approve tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Select a accessory from accessories table which the tuning state is "APPROVED" and the marketing material is not uploaded yet.
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the marketing status is "UNSUBMITTED"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "UNSUBMITTED");
		// 4. Upload a marketing material file
		home.click(AccessoryInfo.EDIT_MODE);
		home.uploadFile(AddAccessory.ADD_MARKETING, Constant.AUDIO_ROUTE_FILE);
		home.clickOptionByIndex(AddAccessory.MARKETING_METERIAL_TYPE, 1);
		home.click(AddAccessory.SAVE);
		/*
		 * VP: Verify that the  "Partner Actions" link in step 2: Marketing Approval is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.PARTNER_ACTIONS_STEP_2));
		// 5. Expand the "Partner Actions" link in step 2: Marketing Approval
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		// 6. Click "Request Marketing Review" link
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		/*
		 * Verify that Marketing status is changed to "PENDING DTS REVIEW", the "Approve Marketing" and "Declined Marketing" links are also displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "PENDING DTS REVIEW");
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.APPROVE_MARKETING));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.DECLINE_MARKETING));
		// 7. Click "Approve Marketing" link
		home.click(AccessoryInfo.APPROVE_MARKETING);
		/*
		 * Verify that Marketing status is changed to "APPROVED" and its action is "Revoke Marketing Approval" 
		 */
		Assert.assertTrue(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS).equalsIgnoreCase(AccessoryInfo.APPROVED));
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_ACTION), "Revoke Marketing Approval");
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the Marketing status is changed to "DECLINED" when DTS Marketing declines the marketing materials
	 */
	@Test
	public void TC040DMS_04(){
		result.addLog("ID : TC040DMS_04 : Verify that the Marketing statsus is changed to 'DECLINED' when DTS Marketing declines the marketing materials");
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
		// Navigate to accessories page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add Accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create accessory
		Hashtable<String,String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Approve tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Select a accessory from accessories table which the tuning state is "APPROVED" and the marketing material is not uploaded yet.
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the marketing status is "UNSUBMITTED"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "UNSUBMITTED");
		// 4. Upload a marketing material file
		home.click(AccessoryInfo.EDIT_MODE);
		home.uploadFile(AddAccessory.ADD_MARKETING, Constant.AUDIO_ROUTE_FILE);
		home.clickOptionByIndex(AddAccessory.MARKETING_METERIAL_TYPE, 1);
		home.click(AddAccessory.SAVE);
		/*
		 * VP: Verify that the  "Partner Actions" link in step 2: Marketing Approval is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.PARTNER_ACTIONS_STEP_2));
		// 5. Expand the "Partner Actions" link in step 2: Marketing Approval
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		// 6. Click "Request Marketing Review" link
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		/*
		 * Verify that Marketing status is changed to "PENDING DTS REVIEW", the "Approve Marketing" and "Declined Marketing" links are also displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "PENDING DTS REVIEW");
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.APPROVE_MARKETING));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.DECLINE_MARKETING));
		// 7. Click "Decline Marketing" link
		home.click(AccessoryInfo.DECLINE_MARKETING);
		/*
		 * Verify that Marketing status is changed to "DECLINED"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "DECLINED");
		// Delete accessory
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the Marketing status is changed to "REVOKED" when DTS Marketing revokes the marketing materials after approved
	 */
	@Test
	public void TC040DMS_05(){
		result.addLog("ID : TC040DMS_05 : Verify that the Marketing status is changed to 'REVOKED' when DTS Marketing revokes the marketing materials after approved");
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
		// Navigate to accessories page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add Accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create accessory
		Hashtable<String,String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Approve tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Select a accessory from accessories table which the tuning state is "APPROVED" and the marketing material is not uploaded yet.
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the marketing status is "UNSUBMITTED"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "UNSUBMITTED");
		// 4. Upload a marketing material file
		home.click(AccessoryInfo.EDIT_MODE);
		home.uploadFile(AddAccessory.ADD_MARKETING, Constant.AUDIO_ROUTE_FILE);
		home.clickOptionByIndex(AddAccessory.MARKETING_METERIAL_TYPE, 1);
		home.click(AddAccessory.SAVE);
		/*
		 * VP: Verify that the  "Partner Actions" link in step 2: Marketing Approval is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.PARTNER_ACTIONS_STEP_2));
		// 5. Expand the "Partner Actions" link in step 2: Marketing Approval
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		// 6. Click "Request Marketing Review" link
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		/*
		 * Verify that Marketing status is changed to "PENDING DTS REVIEW", the "Approve Marketing" and "Declined Marketing" links are also displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "PENDING DTS REVIEW");
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.APPROVE_MARKETING));
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.DECLINE_MARKETING));
		// 7. Click "Approve Marketing" link
		home.click(AccessoryInfo.APPROVE_MARKETING);
		/*
		 * VP: Marketing status is changed to "APPROVED" and its action is "Revoke Marketing Approval" 
		 */
		Assert.assertTrue(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS).equalsIgnoreCase(AccessoryInfo.APPROVED));
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_ACTION), "Revoke Marketing Approval");
		// 8. Click "Revoke Marketing Approval" link
		home.click(AccessoryInfo.REVOKE_MARKETING_APPROVAL);
		/*
		 * Verify that Marketing status is changed to "REVOKED" and the “Partner Actions” with “Request Marketing Review” links are displayed below
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "REVOKED");
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.PARTNER_ACTIONS_STEP_2));
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_ACTION), "Request Marketing Review");
		// Delete accessory
		home.doDelete(AccessoryInfo.DELETE);
	}
}
