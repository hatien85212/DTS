package com.dts.adminportal.partner.test;

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
import dts.com.adminportal.model.AddVariant;
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.PartnerAccessoryInfo;
import dts.com.adminportal.model.PartnerAddAccessory;
import dts.com.adminportal.model.PartnerAddVariant;
import dts.com.adminportal.model.PartnerVariantInfo;
import dts.com.adminportal.model.TuningRequestForm;
import dts.com.adminportal.model.VariantInfo;
import dts.com.adminportal.model.Xpath;

public class PartnerUserCatalog040DraftPublished extends CreatePage{
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
	 * Verify that partner user can edit accessory when the tuning status is "UNSUBMITTED".
	 */
	 
	@Test
	public void TC040PDP_01(){
		result.addLog("ID : TC040PDP_01 : Verify that partner user can edit accessory when the tuning status is 'UNSUBMITTED'");
		/*
		 *  1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Fill valid value into all required fields
			5. Click "Save" link
			VP: Verify that the tuning status is "UNSUBMITTED" and the "Edit" link is displayed
			6. Click "Edit" link
			7. Change accessory's value
			8. Click "Save" link
		 */
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields
		// 5. Click "Save" link
		Hashtable<String, String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: Verify that the tuning status is "UNSUBMITTED" and the "Edit" link is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "UNSUBMITTED");
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.EDIT_MODE));
		// 6. Click "Edit" link
		home.click(AccessoryInfo.EDIT_MODE);
		// 7.Change accessory's value
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		home.editData(AddAccessory.NAME, newName);
		// 8. Click "Save" link
		home.click(AddAccessory.SAVE);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), newName);
		// Delete accessory
		home.doDelete(AccessoryInfo.DELETE);
	 }
	
	/*
	 * Verify that partner user can edit accessory when the tuning status is "Pending DTS Review"
	 */
	@Test
	public void TC040PDP_02(){
		result.addLog("ID : TC040PDP_02 :VVerify that partner user can edit accessory when the tuning status is 'Pending DTS Review'");
		/*
		 	Pre-Condition: partner user has "Add and manage Products" rights.
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Click "Add product" link
			4. Fill valid value into all required fields
			5. Upload valid tuning file
			6. Click "Save" link
			7. Click "Request Tuning Review"
			VP: Verify that the tuning status is "Pending DTS Review"
			8. Click "Edit" link
			9. Change product's information
			10. Click "Save" link
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields
		// 5. Upload valid tuning file
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(PartnerAddAccessory.getHash(), data);
		// 7. Click "Request Tuning Review"
		home.click(PartnerAccessoryInfo.REQUEST_TUNING);
		/*
		 * VP: Verify that the tuning status is "Pending DTS Review"
		 */
		Assert.assertEquals(home.getTextByXpath(PartnerAccessoryInfo.TUNING_STATUS), "PENDING DTS APPROVAL");
		// 8. Click "Edit" link
		home.click(PartnerAccessoryInfo.EDIT_MODE);
		// 9. Change product's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		home.editData(PartnerAddAccessory.NAME, newName);
		// 10. Click "Save" link
		home.click(PartnerAddAccessory.SAVE);
		/*
		 * Verify that The product's information is changed successfully
		 */
		Assert.assertEquals(home.getTextByXpath(PartnerAccessoryInfo.ACCESSORY_NAME), newName);
		// Delete product
		home.doDelete(PartnerAccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that partner user can edit accessory when the tuning status is "DTS Request Pending"
	 */
	@Test
	public void TC040PDP_03(){
		result.addLog("ID : TC040PDP_03 :Verify that partner user can edit accessory when the tuning status is 'DTS Request Pending'");
		/*
		 	1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Fill valid value into all required fields
			5. Do not upload valid tuning file
			6. Click "Save" link
			7. Click "Request Tuning Review"
			VP: Verify that the “Tuning Request Form” page is displayed
			8. Submit the “Tuning Request Form” page successfully
			VP: Verify that the tuning status is "DTS Request Pending"
			9. Click "Edit" link
			10. Change accessory's information
			11. Click "Save" link
		 */
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields
		// 5. Do not upload valid tuning file
		// 6. Click "Save" link
		Hashtable<String, String> data = TestData.accessoryDraft();	
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		//7. Click "Request Tuning Review"
		home.click(AccessoryInfo.REQUEST_TUNING);
		/*
		 * VP: Verify that the “Tuning Request Form” page is displayed
		 */
		Assert.assertEquals(home.existsElement(TuningRequestForm.APPROVE_TUNNING_FORM()).getResult(), "Pass");
		//8. Submit the “Tuning Request Form” page successfully
		home.click(TuningRequestForm.AGREE);
		home.click(TuningRequestForm.SUBMIT);
		//VP: Verify that the tuning status is "DTS REQUEST PENDING"
		home.click(TuningRequestForm.PRODUCT_LINK);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "DTS REQUEST PENDING");
		//9. Click "Edit" link
		home.click(AccessoryInfo.EDIT_MODE);
		//10. Change accessory's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(5);
		home.editData(AddAccessory.NAME, newName);
		//11. Click "Save" link
		home.click(AddAccessory.SAVE);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), newName);	
		// Delete accessory
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that partner user can edit accessory when the tuning status is "APPROVED"
	 */
	@Test
	public void TC040PDP_04(){
		result.addLog("ID : TC040PDP_04 :Verify that partner user can edit accessory when the tuning status is 'APPROVED'");
		/*
		  	Pre-Condition: partner user has "Add and manage Products" rights.
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Select an product from Products table which its tuning status is approved
			4. Click "Edit" link
			5. Change product's information
			6. Click "Save" link
		 */
		/*
		 * Pre-condition: Create a product which tuning status is approved
		 */
		// Log out
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Approve tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Log out
		home.logout();
		// Log into DTS portal as Partner user above
		home.login(superpartneruser, superpartnerpassword);
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an product from Products table which its tuning status is approved
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * Verify that tuning status is Approved
		 */
		Assert.assertEquals(home.getTextByXpath(PartnerAccessoryInfo.TUNING_STATUS), "APPROVED");
		// 4. Click "Edit" link
		home.click(PartnerAccessoryInfo.EDIT_MODE);
		// 5. Change product's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		home.editData(PartnerAddAccessory.NAME, newName);
		// 6. Click "Save" link
		home.click(PartnerAddAccessory.SAVE);
		/*
		 * Verify that The product's information is changed successfully
		 */
		Assert.assertEquals(home.getTextByXpath(PartnerAccessoryInfo.ACCESSORY_NAME), newName);
		// Delete product
		home.doDelete(PartnerAccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that partner user can edit accessory when the tuning status is "DECLINED"
	 */
	@Test
	public void TC040PDP_05(){
		result.addLog("ID : TC040PDP_05: Verify that partner user can edit accessory when the tuning status is 'DECLINED'");
		/*
		 	Pre-Condition: partner user has "Add and manage accessories" rights.
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table which its tuning status is declined
			4. Click "Edit" link
			5. Change accessory's information
			6. Click "Save" link
		 */
		
		/*
		 *  Pre-Condition: Create new accessory with tuning status is declined
		 */
		// Log out
		home.logout();
		// Log in DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to accessories page
		home.click(Xpath.linkAccessories);
		// Click Add Accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create accessory
		Hashtable<String,String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Decline tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.DECLINE_TUNING);
		/*
		 * Verify that tuning status is "DECLINED"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "DECLINED");
		// Log out
		home.logout();
		/*
		 * **********************************************************************
		 */
		// 1. Log into DTS portal as a partner user
		home.login(superpartneruser, superpartnerpassword);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table which its tuning status is declined
		home.selectAnaccessorybyName(data.get("name"));
		// 4. Click "Edit" link
		home.click(AccessoryInfo.EDIT_MODE);
		// 5. Change accessory's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		home.editData(AddAccessory.NAME, newName);
		// 6. Click "Save" link
		home.click(AddAccessory.SAVE);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), newName);
		// Delete accessory
		home.doDelete(AccessoryInfo.DELETE);
	}	
	
	/*
	 * Verify that partner user can edit accessory's variant when the variant tuning status is "UNSUBMITTED".
	 */
	@Test
	public void TC040PDP_06(){
		result.addLog("ID : TC040PDP_06 :Verify that partner user can edit accessory's variant when the variant tuning status is 'UNSUBMITTED'.");
		/*
		  	Pre-Condition: partner user has "Add and manage Products" rights.
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Click "Add New Variant" link
			5. Fill valid value into all required fields
			6. Click "Save" link
			VP: Verify that the variant tuning status is "UNSUBMITTED" and the "Edit" link is displayed
			7. Click "Edit" link
			8. Change product's value
			9. Click "Save" link
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		// 4. Click "Add New Variant" link
		home.click(PartnerAccessoryInfo.ADD_VARIANT);
		// 5. Fill valid value into all required fields
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.variantTuning();
		home.addVariant(PartnerAddVariant.getHash(), data);
		/*
		 * VP: Verify that the variant tuning status is "UNSUBMITTED" and the "Edit" link is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(PartnerVariantInfo.TUNING_STATUS), "UNSUBMITTED");
		Assert.assertTrue(home.isElementPresent(PartnerVariantInfo.EDIT_VARIANT));
		// 7. Click "Edit" link
		home.click(PartnerVariantInfo.EDIT_VARIANT);
		// 8. Change product's value
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		home.editData(PartnerAddVariant.NAME, newName);
		// 9. Click "Save" link
		home.click(PartnerAddVariant.SAVE);
		/*
		 * Verify that The product's information is changed successfully
		 */
		Assert.assertEquals(home.getTextByXpath(PartnerVariantInfo.DISPLAYNAME_DEFAULT), newName);
		Assert.assertEquals(home.getTextByXpath(PartnerVariantInfo.TITLE_NAME), newName);
		
	}
	
	/*
	 * Verify that partner user can edit accessory's variant when the variant tuning status is "Pending DTS Review"
	 */
	@Test
	public void TC040PDP_07(){
		result.addLog("ID : TC040PDP_07 :Verify that partner user can edit accessory's variant when the variant tuning status is 'Pending DTS Review'");
		/*
			Pre-Condition: partner user has "Add and manage accessories" rights.
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
			4. Click "Add New Variant" link
			5. Fill valid value into all required fields
			6. Upload variant tuning file
			7. Click "Save" link
			8. Click "Request Tuning Review"
			VP: Verify that the variant tuning status is "Pending DTS Review"
			9. Click "Edit" link
			10. Change accessory's value
			11. Click "Save" link
		 */
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Click "Add New Variant" link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 5. Fill valid value into all required fields
		// 6. Upload variant tuning file
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.variantTuning();
		home.addVariant(PartnerAddVariant.getHash(), data);
		//8. Click "Request Tuning Review"
		home.click(VariantInfo.REQUEST_TUNING_REVIEW);
		/*
		 * VP: Verify that the variant tuning status is "Pending DTS Review"
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), "PENDING DTS APPROVAL");
		// 9. Click "Edit" link
		home.click(VariantInfo.EDIT_VARIANT);
		// 10. Change accessory's value
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		home.editData(AddVariant.NAME, newName);
		// 11. Click "Save" link
		home.click(AddVariant.SAVE);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.DISPLAYNAME_DEFAULT), newName);
		Assert.assertEquals(home.getTextByXpath(PartnerVariantInfo.TITLE_NAME), newName);

	}
	
	/*
	 * Verify that partner user can edit accessory's variant when the variant tuning status is "DTS Request Pending"
	 */
	@Test
	public void TC040PDP_08(){
		result.addLog("ID : TC040PDP_08 :Verify that partner user can edit accessory's variant when the variant tuning status is 'DTS Request Pending'");
		/* 
			Pre-Condition: partner user has "Add and manage Products" rights.
			
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Click "Add New Variant" link
			5. Fill valid value into all required fields
			6. Do not upload variant tuning file
			7. Click "Save" link
			8. Click "Request DTS Tuning"
			VP: Verify that the variant tuning status is "DTS Request Pending"
			9. Click "Edit" link
			10. Change product's information
			11. Click "Save" link
		 */
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Click "Add New Variant" link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 5. Fill valid value into all required fields
		// 6. Do not Upload variant tuning file
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.variantDraft();
		data.remove("save");
		home.addVariant(AddVariant.getHash(), data);
		home.click(AddVariant.UPLOAD_CUSTOM_TUNNING);
		home.click(AddVariant.SAVE);
		//home.addVariant(PartnerAddVariant.getHash(), data);
		// 8. Click "Request Tuning"
		home.click(VariantInfo.REQUEST_DTS_TUNING);
		/*
		 * VP: Verify that the “Tuning Request Form” page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(TuningRequestForm.FORM_NAME));
		// 9. Submit the “Tuning Request Form” page successfully
		home.click(TuningRequestForm.AGREE);
		home.click(TuningRequestForm.SUBMIT);
		/*
		 * VP: Verify that the variant tuning status is "DTS REQUEST PENDING"
		 */
		home.click(TuningRequestForm.PRODUCT_LINK);
		Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.DTS_REQUEST_PENDING_STRING);
		// 10. Click "Edit" link
		home.click(VariantInfo.EDIT_VARIANT);
		// 11. Change accessory's value
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		home.editData(AddVariant.NAME, newName);
		// 12. Click "Save" link
		home.click(AddVariant.SAVE);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.DISPLAYNAME_DEFAULT),newName);
		Assert.assertEquals(home.getTextByXpath(PartnerVariantInfo.TITLE_NAME), newName);

	}
	
	/*
	 * Verify that partner user can edit accessory when the tuning status is "APPROVED"
	 */
	 
	@Test
	public void TC040PDP_09(){
		result.addLog("ID : TC040PDP_09 :Verify that partner user can edit accessory when the tuning status is 'APPROVED'");
		/*
		  	Pre-Condition: partner user has "Add and manage accessories" rights. 
		  	The accessory has at least one variant which its variant tuning status is "APPROVED"
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
			4. Select an accessory's variant which its variant tuning status is "APPROVED"
			5. Click "Edit" link
			6. Change accessory's information
			7. Click "Save" link
		 */
		
		/*
		 * Pre-Condition: partner user has "Add and manage accessories" rights. The accessory has at least one variant which its variant tuning status is "APPROVED"
		 */
		// Log out
		home.logout();
		// Log in DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to accessories page
		home.click(Xpath.linkAccessories);
		// Click Add Accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create accessory
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Click Add new variant link
		home.click(AccessoryInfo.ADD_VARIANT);
		// Create new variant
		Hashtable<String,String> dataVariant = TestData.variantTuning();
		home.addVariant(AddVariant.getHash(), dataVariant);
		// Approve tuning
		home.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		home.click(VariantInfo.APPROVE_TUNING);
		// Log out user
		home.logout();
		/*
		 * ***************************************************************************************
		 */
		// 1. Log into DTS portal as a partner user
		home.login(superpartneruser, superpartnerpassword);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		// 4. Select an accessory's variant which its variant tuning status is "APPROVED"
		home.selectAVariantbyName(dataVariant.get("name"));
		// 5. Click "Edit" link
		home.click(VariantInfo.EDIT_VARIANT);
		// 6. Change accessory's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		home.editData(AddVariant.NAME, newName);
		// 7. Click "Save" link
		home.click(AddVariant.SAVE);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.DISPLAYNAME_DEFAULT), newName);
		Assert.assertEquals(home.getTextByXpath(PartnerVariantInfo.TITLE_NAME), newName);
		// Delete accessory
		home.click(VariantInfo.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that partner user can edit accessory when the tuning status is "DECLINED"
	 */
	@Test
	public void TC040PDP_10(){
		result.addLog("ID : TC040PDP_10: Verify that partner user can edit accessory when the tuning status is 'DECLINED'");
		/*
		 * Pre-Condition: partner user has "Add and manage accessories" rights. The accessory has at least one variant which its variant tuning status is "DECLINED" 
		 * 1. Log into DTS portal as a partner user 
		 * 2. Navigate to "Accessories" page 
		 * 3. Select an accessory from accessories table 
		 * 4. Select an accessory's variant which its variant tuning status is "DECLINED" 
		 * 5. Click "Edit" link 
		 * 6. Change accessory's information 
		 * 7. Click "Save" link
		 */
		/*
		 *  Pre-Condition: Create new accessory has one variant with tuning status is declined
		 */
		// Log out
		home.logout();
		// Log in DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to accessories page
		home.click(Xpath.linkAccessories);
		// Click Add Accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create accessory
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Click Add new variant link
		home.click(AccessoryInfo.ADD_VARIANT);
		// Create new variant
		Hashtable<String,String> dataVariant = TestData.variantTuning();
		home.addVariant(AddVariant.getHash(), dataVariant);
		// Decline tuning
		home.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		home.click(VariantInfo.DECLINE_TUNING);
		/*
		 * Verify that tuning status is DECLINED
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), "DECLINED");
		// Log out user
		home.logout();
		/*
		 * ****************************************************************
		 */
		// 1. Log into DTS portal as a partner user 
		home.login(superpartneruser, superpartnerpassword);
		// 2. Navigate to "Accessories" page 
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		// 4. Select an accessory's variant which its variant tuning status is "DECLINED" 
		home.selectAVariantbyName(dataVariant.get("name"));
		// 5. Click "Edit" link 
		home.click(VariantInfo.EDIT_VARIANT);
		// 6. Change accessory's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		home.editData(AddVariant.NAME, newName);
		// 7. Click "Save" link
		home.click(AddVariant.SAVE);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.DISPLAYNAME_DEFAULT), newName);
		Assert.assertEquals(home.getTextByXpath(PartnerVariantInfo.TITLE_NAME), newName);
		// Delete accessory
		home.click(VariantInfo.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}	
	
	/*
	 * Verify that partner user can edit accessory when the tuning status is "Using parent model's"
	 */
	@Test
	public void TC040PDP_11(){
		result.addLog("ID : TC040PDP_11: Verify that partner user can edit accessory when the tuning status is 'Using parent model's'");
		/*
			Pre-Condition: partner user has "Add and manage accessories" rights.
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
			4. Click "Add New Variant" link
			5. Fill valid value into all required fields
			6. Tick to check the "Use Parent's Tuning" checkbox
			7. Click "Save" link
			VP: Verify that the variant tuning status is "Using parent model's"
			8. Click "Edit" link
			9. Change accessory's information
			10. Click "Save" link
		 */
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Click "Add New Variant" link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 5. Fill valid value into all required fields
		// 6. Tick to check the "Use Parent's Tuning" checkbox
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.variantTuningParent();
		home.addVariant(AddVariant.getHash(), data);
		/*
		 * 	VP: Verify that the variant tuning status is "Using parent's model"
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), "USING PARENT'S MODEL");
		// 8. Click "Edit" link
		home.click(VariantInfo.EDIT_VARIANT);
		// 9. Change accessory's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		home.editData(AddVariant.NAME, newName);
		// 10. Click "Save" link
		home.click(AddVariant.SAVE);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.DISPLAYNAME_DEFAULT), newName);
		Assert.assertEquals(home.getTextByXpath(PartnerVariantInfo.TITLE_NAME), newName);
	}
	
	/*
	 * Verify that partner user can edit accessory when the marketing approval status is "CLOSED"
	 */
	@Test
	public void TC040PDP_12(){
		result.addLog("ID : TC040PDP_12: Verify that partner user can edit accessory when the marketing approval status is 'CLOSED'");
		/*
		 	Pre-Condition: partner user has "Add and manage accessories" rights.
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table which its tunining status is any other than "APPROVED"
			VP: Verify that the marketing approval is "CLOSED"
			4. Click "Edit" link
			5. Change accessory's information
			6. Click "Save" link
		 */
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table which its tunining status is any other than "APPROVED"
		// Click Add Accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new accessory
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: Verify that the marketing approval is "CLOSED"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "CLOSED");
		// 4. Click "Edit" link
		home.click(AccessoryInfo.EDIT_MODE);
		// 5. Change accessory's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		home.editData(AddAccessory.NAME, newName);
		//6. Click "Save" link
		home.click(AddAccessory.SAVE);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), newName);
		
		// Delete accessory
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that partner user can edit accessory when the marketing approval status is "UNSUBMITTED"
	 */
	@Test
	public void TC040PDP_13(){
		result.addLog("ID : TC040PDP_13: Verify that partner user can edit accessory when the marketing approval status is 'UNSUBMITTED'");
		/*
		 	1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Fill valid value into all required fields
			5. Upload valid tuning file
			6. Click "Save" link
			VP: Verify that the tuning action link is  "Request Tuning Review"
			7. Click  "Request Tuning Review" link
			Submit the “Tuning Request Form” page successfully
			8. Log out DTS portal
			9. Log into DTS portal as DTS admin
			10. Navigate to "Accessories" page
			11. Select above accessory which its tuning is pending for DTS review
			12. Click "Approve Tuning" link
			13. Log out DTS portal
			14. Log into DTS portal as above partner user
			15. Navigate to "Accessories" page
			16. Select and open above accessory
			VP: The marketing approval status is "UNSUBMITTED"
			17. Click "Edit" link
			18. Change accessory's information
			19. Click "Save" link
		 */
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(Xpath.createNewAccessory);
		// 4. Fill valid value into all required fields
		// 5. Upload valid tuning file
		// 6. Click "Save" link
		Hashtable<String, String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		//7. Click "Request Tuning Review"
		home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		//8. Log out DTS portal
		home.logout();
		//9. Log into DTS portal as DTS admin
		home.login(superUsername, superUserpassword);
		//10. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		//11. Select above accessory which its tuning is pending for DTS review
		home.selectAnaccessorybyName(data.get("name"));
		// Select tuning rating
		home.click(AccessoryInfo.EDIT_MODE);
		home.clickOptionByIndex(AddAccessory.TUNING_RATING, 2);
		home.click(AddAccessory.SAVE);
		//12. Click "Approve Tuning" link
		home.click(VariantInfo.APPROVE_TUNING);
		//13. Log out DTS portal
		home.logout();
		//14. Log into DTS portal as above partner user
		home.login(superpartneruser, superpartnerpassword);
		//15. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		//16. Select and open above accessory
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: The marketing approval status is "UNSUBMITTED"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "UNSUBMITTED");
		//17. Click "Edit" link
		home.click(AccessoryInfo.EDIT_MODE);
		//18. Change accessory's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		home.editData(AddAccessory.NAME, newName);
		//19. Click "Save" link
		home.click(AddAccessory.SAVE);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), newName);
		// Delete accessory
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that partner user can not edit accessory when the marketing approval status is "Pending DTS Review"
	 */
	@Test
	public void TC040PDP_14(){
		result.addLog("ID : TC040PDP_14: Verify that partner user can not edit accessory when the marketing approval status is 'Pending DTS Review'");
		/*
		 * Pre-condition: the tuning approval is approved. the partner user has "Publish and suspend accessories" rights.
		 * 1. Log into DTS portal as a partner user
		 * 2. Navigate to "Accessories" page
		 * 3. Click "Add Accessory" link
		 * 4. Fill valid value into all required fields
		 * 5. Upload valid tuning file
		 * 6. Upload marketing material
		 * 7. Click "Save" link
		 * 8. Go through the tuning approval
		 * VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and its action is "Request Marketing Review"
		 * 9. Click "Request Marketing Review" link
		 */
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields
		// 5. Upload valid tuning file
		// 6. Upload marketing material
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.accessoryTuning();
		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// 8. Go through the tuning approval
		// Click Request Tuning Review link
		home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		// Logout user
		home.logout();
		// Re-login as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to Accessories page
		home.click(Xpath.linkAccessories);
		// Select created accessory above
		home.selectAnaccessorybyName(data.get("name"));
		// Select tuning rating
		home.click(AccessoryInfo.EDIT_MODE);
		home.clickOptionByIndex(AddAccessory.TUNING_RATING, 2);
		home.click(AddAccessory.SAVE);
		// Approve tuning
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Log out dts user
		home.logout();
		// Re-login as Partner user
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to Accessories page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select the accessory above that has tuning approval
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and its action is "Request Marketing Review"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "APPROVED");
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "UNSUBMITTED");
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_ACTION), "Request Marketing Review");
		// 9. Click "Request Marketing Review" link
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		/*
		 *  Verify that the marketing approval status is "Peding DTS Review" and there is no "Edit" link displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "PENDING DTS REVIEW");
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.EDIT_MODE));
		// Delete accessory
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that partner user can edit accessory when the marketing approval status is "Approved"
	 */
	@Test
	public void TC040PDP_15(){
		result.addLog("ID : TC040PDP_15: Verify that partner user can edit accessory when the marketing approval status is 'Approved'");
		/*
		Pre-condition: the tuning approval is approved. the partner user has "Publish and suspend accessories" rights.
		1. Log into DTS portal as a partner user
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		4. Fill valid value into all required fields
		5. Upload valid tuning file
		6. Upload marketing material
		7. Click "Save" link
		8. Go through the tuning approval
		VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and its action is "Request Marketing Review"
		9. Click "Request Marketing Review" link
		10. Log out DTS portal
		11. Log into DTS portal as DTS admin
		12. Navigate to "Accessories" page
		13. Select and open above accessory
		14. Click "Approve Marketing" link
		15. Log out DTS portal
		16. Log into DTS poratl as above partner user
		17. Select and open avove accessory
		VP: Verify that the marketing approval is "Approved"
		18. Click "Edit" link
		19. Change accessory's information
		20. Click "Save" link
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields
		// 5. Upload valid tuning file
		// 6. Upload marketing material
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.accessoryTuning();
		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// 8. Go through the tuning approval
		// Click Request Tuning Review link
		home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		// Logout user
		home.logout();
		// Re-login as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to Accessories page
		home.click(Xpath.linkAccessories);
		// Select created accessory above
		home.selectAnaccessorybyName(data.get("name"));
		// Select tuning rating
		home.click(AccessoryInfo.EDIT_MODE);
		home.clickOptionByIndex(AddAccessory.TUNING_RATING, 2);
		home.click(AddAccessory.SAVE);
		// Approve tuning
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Logout dts user
		home.logout();
		// Re-login as Partner user
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to Accessories page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select the accessory above that has tuning approval
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and its action is "Request Marketing Review"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "APPROVED");
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "UNSUBMITTED");
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_MARKETING_REVIEW), "Request Marketing Review");
		// 9. Click "Request Marketing Review" link
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		// 10. Log out DTS portal
		home.logout();
		// 11. Log into DTS portal as DTS admin
		home.login(superUsername, superUserpassword);
		// 12. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 13. Select and open above accessory
		home.selectAnaccessorybyName(data.get("name"));
		// 14. Click "Approve Marketing" link
		home.click(AccessoryInfo.APPROVE_MARKETING);
		// 15. Log out DTS portal
		home.logout();
		// 16. Log into DTS portal as above partner user
		home.login(superpartneruser, superpartnerpassword);
		// 17. Select and open above accessory
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the marketing approval is "Approved"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "APPROVED");
		// 18. Click "Edit" link
		home.click(AccessoryInfo.EDIT_MODE);
		// 19. Change accessory's information
		String newName = "Edit" + RandomStringUtils.randomNumeric(6);
		home.editData(AddAccessory.NAME, newName);
		// 20. Click "Save" link
		home.click(AddAccessory.SAVE);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), newName);
		// Delete accessory
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that partner user can edit accessory when the marketing approval status is "DECLINED"
	 */
	@Test
	public void TC040PDP_16(){
		result.addLog("ID : TC040PDP_16: Verify that partner user can edit accessory when the marketing approval status is 'DECLINED'");
		/*
			Pre-condition: the tuning approval is approved. the partner user has "Publish and suspend accessories" rights.
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Fill valid value into all required fields
			5. Upload valid tuning file
			6. Upload marketing material
			7. Click "Save" link
			8. Go through the tuning approval
			VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and its action is "Request Marketing Review"
			9. Click "Request Marketing Review" link
			10. Log out DTS portal
			11. Log into DTS portal as DTS admin
			12. Navigate to "Accessories" page
			13. Select and open above accessory
			14. Click "Decline Marketing" link
			15. Log out DTS portal
			16. Log into DTS portal as above partner user
			17. Select and open above accessory
			VP: Verify that the marketing approval is "DECLINED"
			18. Click "Edit" link
			19. Change accessory's information
			20. Click "Save" link
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields
		// 5. Upload valid tuning file
		// 6. Upload marketing material
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.accessoryPublish();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// 8. Go through the tuning approval
		// Click Request Tuning Review link
		home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		// Logout user
		home.logout();
		// Re-login as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to Accessories page
		home.click(Xpath.linkAccessories);
		// Select created accessory above
		home.selectAnaccessorybyName(data.get("name"));
		// Approve tuning
		// change tuning rating before approving
		home.click(AccessoryInfo.EDIT_MODE);
		home.selectFirstOption(AddAccessory.TUNING_RATING);
		home.click(AddAccessory.SAVE);
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Logout dts user
		home.logout();
		// Re-login as Partner user
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to Accessories page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select the accessory above that has tuning approval
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the tuning is approved, the marketing status is
		 * changed to "UNSUBMITTED" and its action is "Request Marketing Review"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS),
				"APPROVED");
		Assert.assertEquals(
				home.getTextByXpath(AccessoryInfo.MARKETING_STATUS),
				"UNSUBMITTED");
		Assert.assertEquals(
				home.getTextByXpath(AccessoryInfo.REQUEST_MARKETING_REVIEW),
				"Request Marketing Review");
		// 9. Click "Request Marketing Review" link
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		// 10. Log out DTS portal
		home.logout();
		// 11. Log into DTS portal as DTS admin
		home.login(superUsername, superUserpassword);
		// 12. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 13. Select and open above accessory
		home.selectAnaccessorybyName(data.get("name"));
		// 14. Click "Decline Marketing" link
		home.click(AccessoryInfo.DECLINE_MARKETING);
		// 15. Log out DTS portal
		home.logout();
		// 16. Log into DTS portal as above partner user
		home.login(superpartneruser, superpartnerpassword);
		// 17. Select and open above accessory
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the marketing approval is "DECLINED"
		 */
		Assert.assertEquals(
				home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "DECLINED");
		// 18. Click "Edit" link
		home.click(AccessoryInfo.EDIT_MODE);
		// 19. Change accessory's information
		String newData = "Edit"+ RandomStringUtils.randomNumeric(6);
		home.editData(AddAccessory.NAME, newData);
		// 20. Click "Save" link
		home.click(AddAccessory.SAVE);
		/*
		 * Verify that The accessory's information is changed successfully
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), newData);
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the "PUBLISH" button is only displayed if three type o accessory's primary image is uploaded even when both tuning and marketing approval is approved
	 */
	@Test
	public void TC040PDP_17(){
		result.addLog("ID : TC040PDP_17: Verify that the 'PUBLISH' button is only displayed if three type o accessory's primary image is uploaded even when both tuning and marketing approval is approved");
		/*
		Pre-Condition: partner user has "Add and manage accessories" rights.
		1. Log into DTS portal as a partner user
		2. Navigate to "Accessories" page
		3. Select an accessory from accessories table which both tuning and marketing approval are "APPROVED" but three kind of primary images is are not uploaded
		 */
		
		/**
		 ** Create accessory which both tuning and marketing approval are "APPROVED"
		 **/
		// Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		//  Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * Init data
		 */
		Hashtable<String, String> data = TestData.accessoryTuning();
		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Click Request Tuning Review link
		home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		// Logout user
		home.logout();
		// Re-login as DTS user
		home.login(dtsUser, dtsPass);
		// Navigate to Accessories page
		home.click(Xpath.linkAccessories);
		// Select created accessory above
		home.selectAnaccessorybyName(data.get("name"));
		// Approve tuning
		// change tuning rating before approving
		home.click(AccessoryInfo.EDIT_MODE);
		home.selectFirstOption(AddAccessory.TUNING_RATING);
		home.click(AddAccessory.SAVE);
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Logout dts user
		home.logout();
		// Re-login as Partner user
		home.login(partneruser, password);
		// Navigate to Accessories page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select the accessory above that has tuning approval
		home.selectAnaccessorybyName(data.get("name"));
		// Click "Request Marketing Review" link
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		// Log out DTS portal
		home.logout();
		// Log into DTS portal as DTS admin
		home.login(dtsUser, dtsPass);
		// Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// Select and open above accessory
		home.selectAnaccessorybyName(data.get("name"));
		// Click "Approve Marketing" link
		home.click(AccessoryInfo.APPROVE_MARKETING);
		// Log out DTS portal
		home.logout();
		// Log into DTS portal as above partner user
		home.login(partneruser, password);
		/**
		 ** Create accessory which both tuning and marketing approval are "APPROVED" done
		 **/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table which both tuning and marketing approval are "APPROVED" but three kind of primary images is are not uploaded
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * Verify that The "PUBLISH" button is disabled
		 */
		boolean button_disabled = home.getAtribute(AccessoryInfo.PUBLISH, "class").contains("disabled");
		Assert.assertTrue(button_disabled);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.ERROR_MESS_STEP_3), AccessoryInfo.ERROR_MESS_IMG);
	}
	
	/*
	 * Verify that the partner user can publish accessory successfully
	 */
	@Test
	public void TC040PDP_18(){
		result.addLog("ID : TC040PDP_18: Verify that the partner user can publish accessory successfully");
		/*
		Pre-Condition: partner user has "Add and manage accessories" rights.
		1. Log into DTS portal as a partner user
		2. Navigate to "Accessories" page
		3. Select an accessory from accessories table which both tuning and marketing approval are "APPROVED" but three kind of primary images is are  uploaded
		VP: The "PUBLISH" button is enabled
		4. Click "PUBLISH" button
		*/
		/**
		 ** Create accessory which both tuning and marketing approval are "APPROVED" and three kind of primary images are uploaded
		 **/
		// Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		//  Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * Init data & Create accessory
		 */
		Hashtable <String,String> data = TestData.accessoryPublish();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Logout user
		home.logout();
		// Re-login as DTS user
		home.login(dtsUser, dtsPass);
		// Navigate to Accessories page
		home.click(Xpath.linkAccessories);
		// Select created accessory above
		home.selectAnaccessorybyName(data.get("name"));
		// Select tuning rating
		home.click(AccessoryInfo.EDIT_MODE);
		home.selectFirstOption(AddAccessory.TUNING_RATING);
		home.click(AddAccessory.SAVE);
		// Approve Tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Approve Marketing
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		home.click(AccessoryInfo.APPROVE_MARKETING);
		// Logout dts user
		home.logout();
		// Re-login as Partner user
		home.login(partneruser, password);
		/**
		 ** Create accessory which both tuning and marketing approval are "APPROVED" done
		 **/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table which both tuning and marketing approval are "APPROVED" but three kind of primary images is are uploaded
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: The "PUBLISH" button is enabled
		 */
		boolean button_enabled = home.getAtribute(AccessoryInfo.PUBLISH, "class").contains("button-warning");
		Assert.assertTrue(button_enabled);
		// 4. Click "PUBLISH" button
		home.click(AccessoryInfo.PUBLISH);
		/*
		 * Verify that The status of Step 3: Publishing is "PUBLISHED"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.PUBLISH_STATUS), "PUBLISHED");
	}

}	
	
	
	
	
	
	
	
	
