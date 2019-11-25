package com.dts.adminportal.partner.test;

import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.DTSUtil;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AccessoryInfo;
import dts.com.adminportal.model.AccessoryMain;
import dts.com.adminportal.model.AddAccessory;
import dts.com.adminportal.model.AddVariant;
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.LanguagePackage;
import dts.com.adminportal.model.PartnerAccessoryInfo;
import dts.com.adminportal.model.PartnerAddVariant;
import dts.com.adminportal.model.PartnerVariantInfo;
import dts.com.adminportal.model.VariantInfo;
import dts.com.adminportal.model.Xpath;

public class PartnerUserCatalog052PModelVariantEdit extends CreatePage{
	private HomeController home;
	
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}
	
	@BeforeMethod
	public void loginBeforeTest() {
		result = home.login(superpartneruser, superpartnerpassword);
	}
	
	/*
	 * Verify that the Adding New Model Variant shows up all necessary fields properly.
	 */
	@Test
	public void TC052PMVE_01(){
		result.addLog("ID : TC052PMVE_01 : Verify that the Adding New Model Variant shows up all necessary fields properly.");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Verify that the 040P product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
		 */
		/*
		 * PreCondition: Create new product
		 */
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * **************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), data.get("name"));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * Verify that 052 Model Variant Edit page is showed up
		 */
		Assert.assertEquals(home.existsElement(PartnerAddVariant.getElementsInfo()).getResult(), "Pass");
		// Delete product
		home.click(AddVariant.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 Verify that the Headphone: X Tuning Rating is read only.
	 */
	@Test
	public void TC052PMVE_02(){
		String Edit="Can edit headphone tuning rating";
		result.addLog("ID : TC052PMVE_02 : Verify that the Headphone: X Tuning Rating is read only.");
		/*
		Pre-condition: Partner user has "Add and manage accessories" rights.

			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
			4. Verify that the 040P Accessory Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed
		result = home.existsElement(PartnerAccessoryInfo.getElementsInfo());
		Assert.assertEquals("Pass", result.getResult());
		//5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		//052 Model Variant Edit page is showed up and the Headphone: X Tuning Rating is uneditable.
		Assert.assertFalse(home.canEditHeadphoneRating(Xpath.HEADPHONE_TUNING_RATING) ,Edit );
	}
	
	/*
	 Verify that the Tuning section is displayed with "Use Parent's Tuning" checkbox and "Upload Custom Tuning" link
	 */
	@Test
	public void TC052PMVE_03(){
		result.addLog("ID : TC052PMVE_03 : Verify that the Tuning section is displayed with 'Use Parent's Tuning' checkbox and 'Upload Custom Tuning' link");
		/*
		Pre-condition: Partner user has "Add and manage accessories" rights.

			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
			4. Verify that the 040P Accessory Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed
		result = home.existsElement(PartnerAccessoryInfo.getElementsInfo());
		Assert.assertEquals("Pass", result.getResult());
		//5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		// Verify that the Tuning section is displayed with 'Use Parent's Tuning' checkbox and 'Upload Custom Tuning' link
		Assert.assertTrue(home.isElementPresent(PartnerAddVariant.UPLOAD_CUSTOM_TUNNING));
		Assert.assertTrue(home.isElementPresent(PartnerAddVariant.PARENT_TUNNING_CHECKBOX));
	}
	
	/*
	 Verify that the 'Add Tuning File' item is displayed with 'drag and drop' area and 'Select File' button when clicking on 'Upload Custom Tuning' link.
	 */
	@Test
	public void TC052PMVE_04(){
		result.addLog("ID : TC052PMVE_04 : Verify that the 'Add Tuning File' item is displayed with 'drag and drop' area and 'Select File' button when clicking on 'Upload Custom Tuning' link.");
		/*
		Pre-condition: Partner user has "Add and manage Products" rights.

			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Verify that the 040P product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052P Model Variant Edit page is displayed.
			6. Click "Upload Custom Tuning" link
			Verify that the 'Add Tuning File' item is displayed with 'drag and drop' area and 'Select File' button when clicking on 'Upload Custom Tuning' link
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		//5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		//VP. Verify that the 052P Model Variant Edit page is displayed
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		//Click "Upload Custom Tuning" link 
		home.click(AddVariant.UPLOAD_CUSTOM_TUNNING);
		// Verify that the 'Add Tuning File' item is displayed with 'drag and drop' area and 'Select File' button when clicking on 'Upload Custom Tuning' link.
		Assert.assertTrue(home.isElementPresent(AddVariant.ADD_TUNNING));
		Assert.assertTrue(home.isElementPresent(AddVariant.TUNING_DRAG_DROP_AREA));
	}
	
	/*
	 Verify that user can upload tuning file by dragging and dropping file into "Add Tuning File" area.
	 */
	@Test
	public void TC052PMVE_05(){
		result.addLog("ID : TC052PMVE_05 : Verify that user can upload tuning file by dragging and dropping file into 'Add Tuning File' area.");
		/*
		Pre-condition: Partner user has "Add and manage Products" rights.

		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Select an product from Products table
		4. Verify that the 040P product Detail page is displayed
		5. Click "Add New Variant" link from "Model Actions" module
		VP. Verify that the 052P Model Variant Edit page is displayed.
		6. Click "Upload Custom Tuning" link
		VP. Verify that the "Add Tuning File" is displayed.
		7. Drag and Drop a tuning file into Add Tuning File area
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		//5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		//VP. Verify that the 052P Model Variant Edit page is displayed
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		//Click "Upload Custom Tuning" link 
		home.click(AddVariant.UPLOAD_CUSTOM_TUNNING);
		// Verify that the 'Add Tuning File' item is displayed with 'drag and drop' area and 'Select File' button when clicking on 'Upload Custom Tuning' link.
		Assert.assertTrue(home.isElementPresent(AddVariant.ADD_TUNNING));
		Assert.assertTrue(home.isElementPresent(AddVariant.TUNING_DRAG_DROP_AREA));
		//7. Drag and Drop a tuning file into Add Tuning File area
		home.dragDropFile(Constant.tuning_hpxtt);
		
		//Verify that user can upload tuning file by dragging and dropping file into "Add Tuning File" area.
		Assert.assertTrue(home.getTextByXpath(AddVariant.TUNING_UPLOAD_MESSAGE).contains(AddVariant.UPLOAD_FILE_MESSSAGE[0]));
	}
	
	/*
	 * Verify that user can upload tuning file via 'Select File' button of 'Add Tuning File' area successfully
	 */
	@Test
	public void TC052PMVE_06(){
		result.addLog("ID : TC052PMVE_06 : Verify that user can upload tuning file via 'Select File' button of 'Add Tuning File' area successfully");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Verify that the 040P product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052P Model Variant Edit page is displayed.
			6. Click "Upload Custom Tuning" link
			VP. Verify that the "Add Tuning File" is displayed.
			7. Click "Select File" button
			8. Upload a valid tuning file via dialog box
		 */
		/*
		 * PreCondition: Create new product
		 */
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * **************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), data.get("name"));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		// VP. Verify that the 052P Model Variant Edit page is displayed
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		// 6. Click "Upload Custom Tuning" link
		home.click(AddVariant.UPLOAD_CUSTOM_TUNNING);
		// VP. Verify that the "Add Tuning File" is displayed.
		Assert.assertTrue(home.isElementPresent(AddVariant.ADD_TUNNING));
		// 7. Click "Select File" button
		// 8. Upload a valid tuning file via dialog box
		home.uploadFile(AddVariant.ADD_TUNNING, Constant.tuning_hpxtt);
		/*
		 * Verify that The tuning file is upload successfully
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddVariant.UPLOAD_FILE_MESSSAGE[0]));
		// Delete product
		home.click(AddVariant.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the invalid tuning file could not be uploaded
	 */
	@Test
	public void TC052PMVE_07(){
		result.addLog("ID : TC052PMVE_07 : Verify that the invalid tuning file could not be uploaded");
	  /*
			Pre-condition: Partner user has "Add and manage accessories" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
			4. Verify that the 040P Accessory Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052P Model Variant Edit page is displayed.
			6. Upload an invalid tuning file
		*/
		/*
		 * PreCondition: Create new product
		 */
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * **************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), data.get("name"));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP: Verify that the 052P Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(PartnerAddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Upload an invalid tuning file
		home.click(AddVariant.UPLOAD_CUSTOM_TUNNING);
		home.uploadFile(AddVariant.ADD_TUNNING, Constant.tuning_invalid);
		/*
		 * Verify that An error message is displayed and the invalid tuning file is not added
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddVariant.UPLOAD_FILE_MESSSAGE[1]));
		// Delete product
		home.click(AddVariant.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the valid tuning zip file could be uploaded successfully
	 */
	@Test
	public void TC052PMVE_08(){
		result.addLog("ID : TC052PMVE_08 : Verify that the valid tuning zip file could be uploaded successfully");
	   /*
			Pre-condition: Partner user has "Add and manage accessories" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
			4. Verify that the 040P Accessory Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052P Model Variant Edit page is displayed.
			6. Upload a valid tuning zip file
		*/
		/*
		 * PreCondition: Create new product
		 */
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * **************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), data.get("name"));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP: Verify that the 052P Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(PartnerAddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Upload a valid tuning zip file
		home.click(AddVariant.UPLOAD_CUSTOM_TUNNING);
		home.uploadFile(AddVariant.ADD_TUNNING, Constant.tuning_zip);
		/*
		 * Verify that The tuning zip file is upload successfully
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddVariant.UPLOAD_FILE_MESSSAGE[0]));
		// Delete product
		home.click(AddVariant.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the "Upload Custom Tuning" item is hidden when user selects "Use Parent's Tuning".
	 */
	@Test
	public void TC052PMVE_09(){
		result.addLog("ID : TC052PMVE_09 : Verify that the 'Upload Custom Tuning' item is hidden when user selects 'Use Parent's Tuning'.");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Verify that the 040P product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052P Model Variant Edit page is displayed.
			6. Click "Upload Custom Tuning" link
			VP. Verify that the "Add Tuning File" item is displayed.
			7. Click "Use Parent's Tuning" link
		 */
		/*
		 * PreCondition: Create new product
		 */
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * **************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), data.get("name"));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		// VP: Verify that the 052P Model Variant Edit page is displayed
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		// 6. Click "Upload Custom Tuning" link
		home.click(AddVariant.UPLOAD_CUSTOM_TUNNING);
		/*
		 * VP. Verify that the "Add Tuning File" item is displayed.
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.ADD_TUNNING));
		// 7. Click "Use Parent's Tuning" link
		home.click(AddVariant.PARENT_TUNNING_CHECKBOX);
		/*
		 * Verify that The "Add Tuning File" item is hidden
		 */
		Assert.assertFalse(home.isElementPresent(AddVariant.ADD_TUNNING));
		// Delete product
		home.click(AddVariant.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
		
	}
	
	/*
	 * Verify that the Delete Tuning confirmation dialog is displayed when click on trash icon after the custom tuning file has been uploaded successfully
	 */
	@Test
	public void TC052PMVE_10(){
		result.addLog("ID : TC052PMVE_10 : Verify that the Delete Tuning confirmation dialog is displayed when click on trash icon after the custom tuning file has been uploaded successfully");
		/*
			Pre-condition: Partner user has "Add and manage accessories" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
			4. Verify that the 040P Accessory Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052P Model Variant Edit page is displayed.
			6. Click "Upload Custom Tuning" link
			7. Upload a valid tuning file
			VP: Verify that the custom tuning file is uploaded successfully.
			8. Click on trash icon to delete custom tuning
		 */
		/*
		 * PreCondition: Create new product
		 */
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * **************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), data.get("name"));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052P Model Variant Edit page is displayed.
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		// 6. Click "Upload Custom Tuning" link
		// 7. Upload a valid tuning file
		home.click(AddVariant.UPLOAD_CUSTOM_TUNNING);
		home.uploadFile(AddVariant.ADD_TUNNING, Constant.tuning_zip);
		/*
		 * VP: Verify that the custom tuning file is uploaded successfully
		 */
		Assert.assertTrue(home.isTunningUploaded(AddVariant.UPLOADED_TUNING, Constant.tuning_zip));
		// 8. Click on trash icon to delete custom tuning
		home.click(AddVariant.DELETE_TUNING);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		/*
		 * Verify that The Delete Tuning confirmation dialog is displayed with Delete and Cancel button
		 */
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_CANCEL), "Cancel");
		// Delete product
		home.click(Xpath.BTN_CONFIRMATION_CANCEL);
		home.click(AddVariant.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that when a partner user upload tuning file, the upload tuning link is called "Partner Tuning".
	 */
	@Test
	public void TC052PMVE_11(){
		result.addLog("ID : TC052PMVE_11 :Verify that when a partner user upload tuning file, the upload tuning link is called 'Partner Tuning'");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Verify that the 040P product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052P Model Variant Edit page is displayed.
			6. Fill valid value into all required fields
			7. Upload a valid tuning file successfully
		*/
		/*
		 * PreCondition: Create new product
		 */
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * **************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), data.get("name"));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		//VP: Verify that the 052P Model Variant Edit page is displayed
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));	
		// 6. Fill valid value into all required fields
		// 7. Upload a valid tuning file successfully
		home.click(AddVariant.UPLOAD_CUSTOM_TUNNING);
		home.uploadFile(AddVariant.ADD_TUNNING, Constant.tuning_hpxtt);
		/*
		 * Verify that The Tuning section is displayed with new uploaded tuning file and it is called "Partner Tuning"
		 */
		Assert.assertEquals(home.getTextByXpath(AddVariant.UPLOADED_TUNING_NAME), "Partner Tuning");
		// Delete product
		home.click(AddVariant.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the EAN/UPC is validated for the already exist EAN/UPC code.
	 */
	@Test
	public void TC052PMVE_12(){
		result.addLog("ID : TC052PMVE_12 :Verify that the EAN/UPC is validated for the already exist EAN/UPC code.");
		result.addErrorLog("PDPP-467: 051D Accessory Edit : The warning message is not displayed when adding duplicate EAN/UPC");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Verify that the 040P product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052P Model Variant Edit page is displayed.
			6. Fill value into EAN/UPC text field which is saved for another product.
			7. Click "Save" link
		*/
		/*
		 * PreCondition: Create new product
		 */
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		home.selectAnAccessory();
		String EANValue = home.getTextByXpath(AccessoryInfo.EAN);
		String UPCValue = home.getTextByXpath(AccessoryInfo.UPC);
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		//VP: Verify that the 052P Model Variant Edit page is displayed
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));	
		// 6. Fill valid value into all required fields
		String variantName = RandomStringUtils.randomAlphanumeric(10);
		home.editData(AddVariant.NAME, variantName);
		home.editData(AddVariant.EAN, EANValue);
		home.editData(AddVariant.UPC, UPCValue);
		home.click(AddVariant.SAVE);
		//Verify that the EAN/UPC is validated for the already exist EAN/UPC code.
		Assert.assertEquals(home.getTextByXpath(AddVariant.EAN_ERROR_MESSAGE), AddVariant.eanErrMessage[2]);
		Assert.assertEquals(home.getTextByXpath(AddVariant.UPC_ERROR_MESSAGE), AddVariant.upcErrMessage[2]);
		
	}
	
	/*
	 * Verify that the Model Name with (Default) English is required when adding new accessory.
	 */
	@Test
	public void TC052PMVE_13(){
		result.addLog("ID : TC052PMVE_13 :Verify that the Model Name with (Default) English is required when adding new accessory.");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Verify that the 040P product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052P Model Variant Edit page is displayed.
			6. Fill valid value into all required fields but leaving Model Name empty
			7. Click "Save" link
		*/
		/*
		 * PreCondition: Create new product
		 */
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * **************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), data.get("name"));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		// VP: Verify that the 052P Model Variant Edit page is displayed
		Assert.assertEquals(home.existsElement(PartnerAddVariant.getElementsInfo()).getResult(), "Pass");	
		// 6. Fill valid value into all required fields but leaving Model Name empty
		// 7. Click "Save" link
		Hashtable<String,String> dataVariant = TestData.variantDraft();
		dataVariant.remove("name");
		home.addVariant(AddVariant.getHash(), dataVariant);
		/*
		 * A warning message is displayed which notifying the model name is empty
		 */
		Assert.assertTrue(home.checkMessageDisplay("Model name is required."));
		// Delete product
		home.click(AddVariant.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the Model Name with (Default) English is required when adding new accessory.
	 */
	@Test
	public void TC052PMVE_14(){
		result.addLog("ID : TC052PMVE_14 :Verify that the '- Select -' item is the default value of Model Name language dropdown");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Verify that the 040P product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
		*/
		/*
		 * PreCondition: Create new product
		 */
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		// VP: Verify that the 052P Model Variant Edit page is displayed
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));	
		//Verify that the 052P Model Variant Edit page is displayed and the "- Select -" item is the default value of Model Name language dropdown
		Assert.assertEquals(home.getItemSelected(AddVariant.LANGUAGE_DROPDOWN),AddVariant.LANGUAGE_LIST[0]);
		
	}
	
	/*
	 * Verify that the Model Name with (Default) English is required when adding new accessory.
	 */
	@Test
	public void TC052PMVE_15(){
		result.addLog("ID : TC052PMVE_15 :Verify that the Model Name language dropdown contains 10 items properly");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Verify that the 040P product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP: Verify that the 052P Model Variant Edit page is displayed
			6. List out the Model Name language dropdown
		*/
		/*
		 * PreCondition: Create new product
		 */
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		// VP: Verify that the 052P Model Variant Edit page is displayed
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));	
		//The Model Name language dropdown contains items: - Select -, Chinese - Simplified, Chinese - Traditional, French, German, Italian, Japanese, Korean, Russian and Spanish.
		DTSUtil.containsAll(home.getAllComboboxOption(AddVariant.LANGUAGE_DROPDOWN), AddVariant.LANGUAGE_LIST);
	}
	
	
	/*
	 * Verify that the hint text "Inherits default" is displayed for an empty fields of each external model name language.
	 */
	@Test
	public void TC052PMVE_16(){
		
		result.addLog("ID : TC052PMVE_16 :Verify that the hint text 'Inherits default' is displayed for an empty fields of each external model name language.");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Verify that the 040P product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP: Verify that the 052P Model Variant Edit page is displayed
			6. Select another language for Model Name
		*/
		/*
		 * PreCondition: Create new product
		 */
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * **************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), data.get("name"));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		// VP: Verify that the 052P Model Variant Edit page is displayed
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Select another language for Model Name
		home.selectOptionByName(AddVariant.LANGUAGE_DROPDOWN, AddVariant.LANGUAGE_LIST[2]);
		// Get all language packages
		List<LanguagePackage> languagePackages = home.getLanguagePackage(AddVariant.LANGUAGE_CONTAINER);
		/*
		 * Verify that The hint text "Inherits default" is displayed in model name text field
		 */
		Assert.assertEquals(home.getAtribute(languagePackages.get(0).name, "placeholder"), "inherits default");
		// Delete product
		home.click(AddVariant.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the external language model name text field is disabled when the language dropdown is "Select".
	 */
	@Test
	public void TC052PMVE_17(){
		result.addLog("ID : TC052PMVE_17 :Verify that the external language model name text field is disabled when the language dropdown is 'Select'.");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Verify that the 040P product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP: Verify that the 052P Model Variant Edit page is displayed
			6. Set language dropdown to another language
			7. Set language dropdown to "Select"
		*/
		/*
		 * PreCondition: Create new product
		 */
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * **************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), data.get("name"));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		// VP: Verify that the 052P Model Variant Edit page is displayed
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Select another language for Model Name
		// 7. Set language dropdown to "Select"
		home.selectOptionByName(AddVariant.LANGUAGE_DROPDOWN, AddVariant.LANGUAGE_LIST[0]);
		/*
		 * Verify that The model name language text field is disabled	
		 */
		Assert.assertFalse(home.isElementEditable(AddVariant.LANGUAGE_NAME_TEXT));
		// Delete product
		home.click(AddVariant.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that another language dropdown will display when selecting a model name language.
	 */
	@Test
	public void TC052PMVE_18(){
		result.addLog("ID : TC052PMVE_18 :Verify that another language dropdown will display when selecting a model name language");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Verify that the 040P product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP: Verify that the 052P Model Variant Edit page is displayed
			6. Set the language dropdown to another language
		*/
		/*
		 * PreCondition: Create new product
		 */
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * **************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), data.get("name"));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		// VP: Verify that the 052P Model Variant Edit page is displayed
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Set the language dropdown to another language
		home.selectOptionByName(AddVariant.LANGUAGE_DROPDOWN, AddVariant.LANGUAGE_LIST[3]);
		/*
		 * Verify that Another language dropdown is displayed below
		 */
		int languageContainers = home.getLanguageContainerIndex(AddVariant.LANGUAGE_CONTAINER);
		Assert.assertTrue(languageContainers > 1);
		// Delete product
		home.click(AddVariant.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that a warning message is displayed for duplicating language dropdowns
	 */
	@Test
	public void TC052PMVE_19(){
		result.addLog("ID : TC052PMVE_19 : Verify that a warning message is displayed for duplicating language dropdowns");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Verify that the 040P product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP: Verify that the 052P Model Variant Edit page is displayed
			6. Select language dropdown to another item other than "Select"
			VP: Verify that new language dropdown is displayed.
			7. Select the same item for second language dropdown
		*/
		/*
		 * PreCondition: Create new product
		 */
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * **************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), data.get("name"));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		// VP: Verify that the 052P Model Variant Edit page is displayed
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Select language dropdown to another item other than "Select"
		home.selectOptionByName(AddVariant.LANGUAGE_DROPDOWN, AddVariant.LANGUAGE_LIST[3]);
		/*
		 * VP: Verify that new language dropdown is displayed
		 */
		List<LanguagePackage> languagePakages = home.getLanguagePackage(AddVariant.LANGUAGE_CONTAINER);
		Assert.assertTrue(languagePakages.size() > 1);
		// 7. Select the same item for second language dropdown
		home.selectOptionByName(languagePakages.get(1).languagedropbox, AddVariant.LANGUAGE_LIST[3]);
		/*
		 * Verify that A warning message is displayed which notifying for the duplicating of languages
		 */
		Assert.assertTrue(home.checkMessageDisplay("Language is duplicated"));
		// Delete product
		home.click(AddVariant.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
	}

	/*
	 * Verify that the language dropdown is restore to default 'Select' value and the input field is cleared when selecting the Trashcan icon
	 */
	@Test
	public void TC052PMVE_20(){
		result.addLog("ID : TC052PMVE_20 :Verify that the language dropdown is restore to default 'Select' value and the input field is cleared when selecting the Trashcan icon.");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.

			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Verify that the 040P product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP: Verify that the 052P Model Variant Edit page is displayed
			6. Select language dropdown to another item other than "Select"
			7. Type value into model name input field
			6. Click on Trashcan icon
		*/
		/*
		 * PreCondition: Create new product
		 */
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		// VP: Verify that the 052P Model Variant Edit page is displayed
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));	
		//The Model Name language dropdown contains items: - Select -, Chinese - Simplified, Chinese - Traditional, French, German, Italian, Japanese, Korean, Russian and Spanish.
		home.selectOptionByName(AddVariant.LANGUAGE_DROPDOWN, AddVariant.LANGUAGE_LIST[1]);
		home.type(AddVariant.OTHER_LANGUAGE_NAME, "Other language");
		home.click(AddVariant.TRASH_ICON);
		//Verify that the language dropdown is restore to default 'Select' value and the input field is cleared when selecting the Trashcan icon
		Assert.assertEquals(home.getItemSelected(AddVariant.LANGUAGE_DROPDOWN),AddVariant.LANGUAGE_LIST[0]);
		Assert.assertFalse(home.isElementEditable(AddVariant.LANGUAGE_NAME_TEXT));
		//DTSUtil.containsAll(home.getAllComboboxOption(AddVariant.LANGUAGE_DROPDOWN), AddVariant.LANGUAGE_LIST);
	}
	
	/*
	 * Verify that the Input Specifications section inludes "Connection Type" and "Support Input Channels" module but they are disabled.
	 */
	@Test
	public void TC052PMVE_21(){
		result.addLog("ID : TC052PMVE_21 :Verify that the Input Specifications section inludes 'Connection Type' and 'Support Input Channels' module but they are disabled.");
		/*
		Pre-condition: Partner user has "Add and manage Products" rights.
		
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Select an product from Products table
		4. Verify that the 040D product Detail page is displayed
		5. Click "Add New Variant" link from "Model Actions" module
		*/
		/*
		 * PreCondition: Create new product
		 */
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		// VP: Verify that the 052P Model Variant Edit page is displayed
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));	
		//The Input Specifications section inludes "Connection Type" and "Support Input Channels" module but they are disabled.
		Assert.assertFalse(home.isElementEditable(AddVariant.INPUT_SPECIFICATIONS));
	}
	
	/*
	 * Verify that the Primary Catalog Image section is displayed with "Use Parent's Images" checkbox and "Upload Custom Images" link
	 */
	@Test
	public void TC052PMVE_22(){
		result.addLog("ID : TC052PMVE_22 : Verify that the Primary Catalog Image section is displayed with 'Use Parent's Images' checkbox and 'Upload Custom Images' link");
		/*
		Pre-condition: Partner user has "Add and manage accessories" rights.

		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Select an accessory from accessories table
		4. Verify that the 040P Accessory Detail page is displayed
		5. Click "Add New Variant" link from "Model Actions" module
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed
		result = home.existsElement(PartnerAccessoryInfo.getElementsInfo());
		Assert.assertEquals("Pass", result.getResult());
		//5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		// Verify that the Primary Catalog Image section is displayed with "Use Parent's Images" checkbox and "Upload Custom Images" link
		Assert.assertTrue(home.isElementPresent(PartnerAddVariant.PARENT_IMAGE_CHECKBOX));
		Assert.assertTrue(home.isElementPresent(PartnerAddVariant.UPLOAD_CUSTOM_IMAGE));
	}	
	
	/*
	 * Verify that the "Add Custom Image" item is displayed with "drag and drop" area and "Select File" button when clicking on "Upload Custom Images" link.
	 */
	@Test
	public void TC052PMVE_23(){
		result.addLog("ID : TC052PMVE_23 : Verify that the 'Add Custom Image' item is displayed with 'drag and drop' area and 'Select File' button when clicking on 'Upload Custom Images' link.");
		/*
		Pre-condition: Partner user has "Add and manage Products" rights.
		
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Select an product from Products table
		4. Verify that the 040P product Detail page is displayed
		5. Click "Add New Variant" link from "Model Actions" module
		VP. Verify that the 052P Model Variant Edit page is displayed.
		6. Click "Upload Custom Images" link
		 */
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		// VP: Verify that the 052P Model Variant Edit page is displayed
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));	
		//6. Click "Upload Custom Images" link
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		// Verify that the "Add Custom Image" item is displayed with "drag and drop" area and "Select File" button when clicking on "Upload Custom Images" link.
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_250));
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_500));
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_1000));
		Assert.assertTrue(home.isElementPresent(AddVariant.IMAGE250_DRAG_DROP_AREA));
		Assert.assertTrue(home.isElementPresent(AddVariant.IMAGE500_DRAG_DROP_AREA));
		Assert.assertTrue(home.isElementPresent(AddVariant.IMAGE1000_DRAG_DROP_AREA));
		
	}	
	
	/*
	 * Verify that user can upload primary images file by dragging and dropping file into "Add Custom Image File" area.
	 */
	@Test
	public void TC052PMVE_24(){
		result.addLog("ID : TC052PMVE_24 : Verify that user can upload primary images file by dragging and dropping file into 'Add Custom Image File' area.");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Verify that the 040P product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052P Model Variant Edit page is displayed.
			6. Click "Upload Custom Images" link
			VP. Verify that the "Add Custom Image File" is displayed.
			7. Drag and Drop a tuning file into Add Custom Image File area
		 */
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		// VP: Verify that the 052P Model Variant Edit page is displayed
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));	
		//6. Click "Upload Custom Images" link
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		//7. Drag and Drop a tuning file into Add Custom Image File area
		home.dragDropFile(Constant.IMG_NAME[0]);
		Assert.assertTrue(home.isElementPresent(AddVariant.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250));
		
		
	}	
	
	/*
	 * Verify that user can upload primary image file via "Select File" button of "Add Custom Image File" area successfully
	 */
	@Test
	public void TC052PMVE_25(){
		result.addLog("ID : TC052PMVE_25 : Verify that user can upload primary image file via 'Select File' button of 'Add Custom Image File' area successfully");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Verify that the 040P product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052P Model Variant Edit page is displayed.
			6. Click "Upload Custom Images" link
			VP. Verify that the "Add Custom Image File" item is displayed.
			7. Click "Select File" button
			8. Upload an image file via dialog box
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Create new product successfully
		home.click(AccessoryMain.ADD_ACCESSORY);
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		//VP: Verify that the product info page is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		//5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		//VP: verify that the add variant screen is displayed
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		//6. Click "Upload Custom Images" link
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		//VP: Verify that all three "Select file" link is displayed for three catalog image
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_250));
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_500));
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_1000));
		// 7. Click "Select File" button
		// 8. Upload an image file via dialog box
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_250, Constant.IMG_NAME[0]);
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_500, Constant.IMG_NAME[1]);
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_1000, Constant.IMG_NAME[2]);
		// VP: Three images are upload successfully.
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddVariant.UPLOAD_FILE_MESSSAGE[0]));
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddVariant.UPLOAD_FILE_MESSSAGE[0]));
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddVariant.UPLOAD_FILE_MESSSAGE[0]));
		//Teardown: Delete product
		home.deleteAnaccessorybyName(data.get("name"));
	}	
	
	/*
	 * Verify that the "Add Custom Image" item is hidden when user selects "Use Parent's Images"
	 */
	@Test
	public void TC052PMVE_26(){
		result.addLog("ID : TC052PMVE_26 : Verify that the 'Add Custom Image' item is hidden when user selects 'Use Parent's Images'");
		/*
		Pre-condition: Partner user has "Add and manage accessories" rights.

		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Select an accessory from accessories table
		4. Verify that the 040P Accessory Detail page is displayed
		5. Click "Add New Variant" link from "Model Actions" module
		VP. Verify that the 052P Model Variant Edit page is displayed.
		6. Click "Upload Custom Images" link
		VP. Verify that the "Add Tuning File" item is displayed.
		7. Tick to check the "Use Parent's Images" checkbox.
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Create new product successfully
		home.click(AccessoryMain.ADD_ACCESSORY);
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		//VP: Verify that the product info page is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		//5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		//VP: verify that the add variant screen is displayed
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		//7. Select the "Use Custom Images" checkbox.
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		// VP: Verify that the upload custom image section is displayed
		Assert.assertTrue(home.isElementPresent(AddVariant.CATALOG_IMAGE_UPLOADER_250));
		Assert.assertTrue(home.isElementPresent(AddVariant.CATALOG_IMAGE_UPLOADER_500));
		Assert.assertTrue(home.isElementPresent(AddVariant.CATALOG_IMAGE_UPLOADER_1000));
		//8. Select "Use Parent's Images" option
		home.click(AddVariant.PARENT_IMAGE_CHECKBOX);
		//VP: Verify that three size of adding custom image is hidden
		Assert.assertFalse(home.isElementPresent(AddVariant.CATALOG_IMAGE_UPLOADER_250));
		Assert.assertFalse(home.isElementPresent(AddVariant.CATALOG_IMAGE_UPLOADER_500));
		Assert.assertFalse(home.isElementPresent(AddVariant.CATALOG_IMAGE_UPLOADER_1000));
		
		//Teardown: Delete product
		home.deleteAnaccessorybyName(data.get("name"));
	}
	
	/*
	 * Verify that the Delete Custom Image  confirmation dialog is displayed when the "Use Parent's Images" is checked after the custom image file has been uploaded successfully
	 */
	@Test
	public void TC052PMVE_27(){
		result.addLog("ID : TC052PMVE_27 : Verify that the Delete Custom Image  confirmation dialog is displayed when the 'Use Parent's Images' is checked after the custom image file has been uploaded successfully");
		/*
		 Pre-condition: Partner user has "Add and manage accessories" rights.
		 1. Log into DTS portal as a partner admin 
		 2. Navigate to "Accessories" page 
		 3. Select an accessory from accessories table 
		 4. Verify that the 040P Accessory Detail page is displayed 
		 5. Click "Add New Variant" link from "Model Actions" module 
		 VP. Verify that the 052P Model Variant Edit page is displayed. 
		 6. Click "Upload Custom Images" link 
		 VP. Verify that the "Add Custom Image File" is displayed. 
		 7. Upload an image file 
		 VP: Verify that the image is uploaded successfully. 
		 8. Tick to check the "Use Parent's Images" checkbox.
		 */
		
		// 2. Navigate to "Accessories" page 
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed 
		Assert.assertEquals(home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052P Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(PartnerAddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Click "Upload Custom Images" link 
		home.click(PartnerAddVariant.UPLOAD_CUSTOM_IMAGE);
		/*
		 * VP. Verify that the "Add Custom Image File" is displayed
		 */
		home.isElementPresent(PartnerAddVariant.SELECT_FILE_IMAGE_250);
		home.isElementPresent(PartnerAddVariant.SELECT_FILE_IMAGE_500);
		home.isElementPresent(PartnerAddVariant.SELECT_FILE_IMAGE_1000);
		// 7. Upload an image file 
		home.uploadFile(PartnerAddVariant.SELECT_FILE_IMAGE_250, PartnerAddVariant.IMG_NAME[0]);
		/*
		 * VP: Verify that the image is uploaded successfully
		 */
		Assert.assertTrue(home.isElementPresent(driver.findElement(By.xpath(PartnerAddVariant.IMAGE250_DISPLAY)).findElement(By.tagName("img"))));
		// 8. Tick to check the "Use Parent's Images" checkbox
		home.click(PartnerAddVariant.PARENT_IMAGE_CHECKBOX);
		/*
		 * Verify that The Delete Images confirmation dialog is displayed
		 */
		String message = "Use Parent's Images will discard the custom files.";
		home.checkMessageDisplay(message);
	}

	/*
	 * Three size of Primary Catalog Image display display in order: 250x250 pixels, 500x500 pixels, 1000x1000 pixels
	 */
	@Test
	public void TC052PMVE_28(){
		result.addLog("ID : TC052PMVE_28 : Verify that the three size of Primary Catalog Image display in an order list");
	   /*
		Pre-condition: Partner user has "Add and manage accessories" rights.
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Select an accessory from accessories table
		4. Verify that the 040P Accessory Detail page is displayed
		5. Click "Add New Variant" link from "Model Actions" module
		VP. Verify that the 052P Model Variant Edit page is displayed.
		6. Click "Upload Custom Images" link
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052P Model Variant Edit page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		// 6. Click "Upload Custom Images" link
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		/*
		 * Three size of Primary Catalog Image display display in order: 250x250 pixels, 500x500 pixels, 1000x1000 pixels
		 */
		Assert.assertTrue(home.isImageDisplayInOrder(PartnerAddVariant.IMAGE_CATALOG));
	}
	
		
	/*
	 * Verify that the three size of Primary Catalog Image display in an order list
	 */
	@Test
	public void TC052PMVE_29(){
		result.addLog("ID : TC052PMVE_29 : Verify that the image thumbnail is displayed correcty for each Primary Catalog Image.");
	   /*
		Pre-condition: Partner user has "Add and manage Products" rights.

		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Select an product from Products table
		4. Verify that the 040P product Detail page is displayed
		5. Click "Add New Variant" link from "Model Actions" module
		VP. Verify that the 052P Model Variant Edit page is displayed.
		6. Click "Upload Custom Images" link
		VP. Verify that the "Add Custom Image File" is displayed.
		7. Upload three size of image successfully
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052P Model Variant Edit page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		// 6. Click "Upload Custom Images" link
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		//Upload image for each size of Primary Catalog Image successfully
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_250, AddVariant.IMG_NAME[0]);
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_500, AddVariant.IMG_NAME[1]);
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_1000, AddVariant.IMG_NAME[2]);
		/*
		 * Three size of Primary Catalog Image display display in order: 250x250 pixels, 500x500 pixels, 1000x1000 pixels
		 */
		Assert.assertTrue(home.isElementDisplayHorizontal(AddVariant.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250, AddVariant.CATALOG_IMAGE_UPLOAD_INFO_250));
		Assert.assertTrue(home.isElementDisplayHorizontal(AddVariant.CATALOG_IMAGE_UPLOAD_THUMBNAIL_500, AddVariant.CATALOG_IMAGE_UPLOAD_INFO_500));
		Assert.assertTrue(home.isElementDisplayHorizontal(AddVariant.CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000, AddVariant.CATALOG_IMAGE_UPLOAD_INFO_1000));
	}
	
	/*
	 * Verify that the uploaded date and file size are displayed below the image name for each size of Primary Catalog Image.
	 */
	@Test
	public void TC052PMVE_30(){
		result.addLog("ID : TC052PMVE_30 : Verify that the uploaded date and file size are displayed below the image name for each size of Primary Catalog Image.");
	   /*
		Pre-condition: Partner user has "Add and manage Products" rights.

		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Select an product from Products table
		4. Verify that the 040P product Detail page is displayed
		5. Click "Add New Variant" link from "Model Actions" module
		VP. Verify that the 052P Model Variant Edit page is displayed.
		6. Click "Upload Custom Images" link
		VP. Verify that the "Add Custom Image File" is displayed.
		7. Upload three size of image successfully
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		home.click(AccessoryInfo.ADD_VARIANT);
		// 5. Click "Add New Variant" link from "Model Actions" module
		Hashtable<String,String> data = TestData.variantImage();
		home.addVariant(AddVariant.getHash(), data);
		// Edit new variant
		home.click(VariantInfo.EDIT_VARIANT);
		//Upload image for each size of Primary Catalog Image successfully
		
		/*
		 * The uploaded date and file size are displayed below the image name for each size of Primary Catalog Image. 
		 */
		Assert.assertTrue(home.isElementDisplayVertically(AddVariant.CATALOG_IMAGE_TITLE_LINK_250, AddVariant.CATALOG_IMAGE_FILE_SIZE_250));
		Assert.assertTrue(home.isElementDisplayVertically(AddVariant.CATALOG_IMAGE_TITLE_LINK_500, AddVariant.CATALOG_IMAGE_FILE_SIZE_500));
		Assert.assertTrue(home.isElementDisplayVertically(AddVariant.CATALOG_IMAGE_TITLE_LINK_1000, AddVariant.CATALOG_IMAGE_FILE_SIZE_1000));
	}
	
	/*
	 * Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on inage thumbnail
	 */
	@Test
	public void TC052PMVE_31() throws InterruptedException{
		result.addLog("ID : TC052PMVE_31 : Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on inage thumbnail");
	   /*
		Pre-condition: Partner user has "Add and manage Products" rights.

		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Select an product from Products table
		4. Verify that the 040P product Detail page is displayed
		5. Click "Add New Variant" link from "Model Actions" module
		VP. Verify that the 052P Model Variant Edit page is displayed.
		6. Click "Upload Custom Images" link
		VP. Verify that the "Add Custom Image File" is displayed.
		7. Upload three size of image successfully
		8. Click on image thumbnail of each size
		*/
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		// VP. Verify that the 040P product Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		// 4. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		// VP. Verify that the 052P Model Variant Edit page is displayed.
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		// 5. Click "Upload Custom Images" link
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		// VP:Verify that the "Add Custom Image File" is displayed.
		Assert.assertTrue(home.isElementPresent(AddVariant.CATALOG_IMAGE_UPLOADER_250));
		Assert.assertTrue(home.isElementPresent(AddVariant.CATALOG_IMAGE_UPLOADER_500));
		Assert.assertTrue(home.isElementPresent(AddVariant.CATALOG_IMAGE_UPLOADER_1000));
		
		// 5.  Upload image for each size of Primary Catalog Image successfully
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_250, AddVariant.IMG_NAME[0]);
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_500, AddVariant.IMG_NAME[1]);
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_1000, AddVariant.IMG_NAME[2]);
		// 5. Click on image thumbnail of each size
		//Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on inage thumbnail
		//Verify lightbox image is displayed for 250x250
		home.click(AddVariant.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250);
		//wait for ligtbox display
		home.waitForElementClickable(AddVariant.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AddVariant.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddVariant.LIGHTBOX_STYLE_IMAGE, "src").contains(AddVariant.IMG_NAME[0].replaceAll(".jpg", "")));
		
		//Close the lightbox fot 250x250
		home.click(AddVariant.LIGHTBOX_CLOSE);
		Thread.sleep(3000);

		//Verify lightbox image is displayed for 500x500	
		home.click(AddVariant.CATALOG_IMAGE_UPLOAD_THUMBNAIL_500);
		//wait for ligtbox display
		home.waitForElementClickable(AddVariant.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AddVariant.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddVariant.LIGHTBOX_STYLE_IMAGE, "src").contains(AddVariant.IMG_NAME[1].replaceAll(".jpg", "")));
		
		//Close the lightbox fot 500x500
		home.click(AddVariant.LIGHTBOX_CLOSE);
		Thread.sleep(3000);
		
		//Verify lightbox image is displayed for 1000x1000
		home.click(AddVariant.CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000);
		//wait for ligtbox display
		home.waitForElementClickable(AddVariant.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AddVariant.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddVariant.LIGHTBOX_STYLE_IMAGE, "src").contains(AddVariant.IMG_NAME[2].replaceAll(".jpg", "")));
		home.click(AddVariant.LIGHTBOX_CLOSE);
		
		//Cancel saving
		home.click(AddVariant.CANCEL);
		
	}
	
	/*
	 * Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on file name.
	 */
	@Test
	public void TC052PMVE_32() throws InterruptedException{
		result.addLog("ID : TC052PMVE_32 : Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on file name.");
	   /*
		Pre-condition: Partner user has "Add and manage Products" rights.

		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Select an product from Products table
		4. Verify that the 040P product Detail page is displayed
		5. Click "Add New Variant" link from "Model Actions" module
		VP. Verify that the 052P Model Variant Edit page is displayed.
		6. Click "Upload Custom Images" link
		VP. Verify that the "Add Custom Image File" is displayed.
		7. Upload three size of image successfully
		5. Click on file name of each size
		*/
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		// VP. Verify that the 040P product Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		// 4. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		// VP. Verify that the 052P Model Variant Edit page is displayed.
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		// 5. Click "Upload Custom Images" link
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		// VP:Verify that the "Add Custom Image File" is displayed.
		Assert.assertTrue(home.isElementPresent(AddVariant.CATALOG_IMAGE_UPLOADER_250));
		Assert.assertTrue(home.isElementPresent(AddVariant.CATALOG_IMAGE_UPLOADER_500));
		Assert.assertTrue(home.isElementPresent(AddVariant.CATALOG_IMAGE_UPLOADER_1000));
		
		// 5.  Upload image for each size of Primary Catalog Image successfully
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_250, AddVariant.IMG_NAME[0]);
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_500, AddVariant.IMG_NAME[1]);
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_1000, AddVariant.IMG_NAME[2]);
		// 5. Click on image thumbnail of each size
		//Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on inage thumbnail
		//Verify lightbox image is displayed for 250x250
		home.click(AddVariant.CATALOG_IMAGE_TITLE_LINK_250);
		//wait for ligtbox display
		home.waitForElementClickable(AddVariant.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AddVariant.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddVariant.LIGHTBOX_STYLE_IMAGE, "src").contains(AddVariant.IMG_NAME[0].replaceAll(".jpg", "")));
		
		//Close the lightbox fot 250x250
		home.click(AddVariant.LIGHTBOX_CLOSE);
		Thread.sleep(3000);

		//Verify lightbox image is displayed for 500x500	
		home.click(AddVariant.CATALOG_IMAGE_TITLE_LINK_500);
		//wait for ligtbox display
		home.waitForElementClickable(AddVariant.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AddVariant.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddVariant.LIGHTBOX_STYLE_IMAGE, "src").contains(AddVariant.IMG_NAME[1].replaceAll(".jpg", "")));
		
		//Close the lightbox fot 500x500
		home.click(AddVariant.LIGHTBOX_CLOSE);
		Thread.sleep(3000);
		
		//Verify lightbox image is displayed for 1000x1000
		home.click(AddVariant.CATALOG_IMAGE_TITLE_LINK_1000);
		//wait for ligtbox display
		home.waitForElementClickable(AddVariant.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AddVariant.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddVariant.LIGHTBOX_STYLE_IMAGE, "src").contains(AddVariant.IMG_NAME[2].replaceAll(".jpg", "")));
		home.click(AddVariant.LIGHTBOX_CLOSE);
		
		//Cancel saving
		home.click(AddVariant.CANCEL);
		
	}
	
	/*
	 * Verify that three size of Primary Catalog Image could be replaced by another successfully
	 */
	@Test
	public void TC052PMVE_33(){
		result.addLog("ID : TC052PMVE_33 : Verify that three size of Primary Catalog Image could be replaced by another successfully");
	   /*
		Pre-condition: Partner user has "Add and manage accessories" rights.
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Select an accessory from accessories table
		4. Verify that the 040P Accessory Detail page is displayed
		5. Click "Add New Variant" link from "Model Actions" module
		VP. Verify that the 052P Model Variant Edit page is displayed.
		6. Click "Upload Custom Images" link
		VP. Verify that the "Add Custom Image File" is displayed.
		7. Upload three size of image successfully
		8. Delete the uploaded image by clicking on trashcan icon
		9. Upload another image
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(PartnerAccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052P Model Variant Edit page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		// 6. Click "Upload Custom Images" link
		home.click(PartnerAddVariant.UPLOAD_CUSTOM_IMAGE);
		/*
		 * VP. Verify that the "Add Custom Image File" is displayed
		 */
		home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_250);
		home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_500);
		home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_1000);
		// 7. Upload three size of image successfully
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_250, AddVariant.IMG_NAME[0]);
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_500, AddVariant.IMG_NAME[1]);
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_1000, AddVariant.IMG_NAME[2]);
		// 8. Delete the uploaded image by clicking on trashcan icon
		home.doDelete(AddVariant.CATALOG_IMAGE_TRASH_250);
		home.doDelete(AddVariant.CATALOG_IMAGE_TRASH_500);
		home.doDelete(AddVariant.CATALOG_IMAGE_TRASH_1000);
		
		// 9. Upload another image
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_250, AddVariant.IMG_NAME[0]);
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_500, AddVariant.IMG_NAME[1]);
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_1000, AddVariant.IMG_NAME[2]);
		/*
		 * Verify that New image could be upload successfully
		 */
		Assert.assertTrue(home.isElementPresent(driver.findElement(
				By.xpath(PartnerAddVariant.IMAGE250_DISPLAY)).findElement(
				By.tagName("img"))));
		Assert.assertTrue(home.isElementPresent(driver.findElement(
				By.xpath(PartnerAddVariant.IMAGE500_DISPLAY)).findElement(
				By.tagName("img"))));
		Assert.assertTrue(home.isElementPresent(driver.findElement(
				By.xpath(PartnerAddVariant.IMAGE1000_DISPLAY)).findElement(
				By.tagName("img"))));
	}
	
	/*
	 * Verify that the marketing material file could be uploaded successfully by drapping and dropping
	 */
	@Test
	public void TC052PMVE_34(){
		result.addLog("ID : TC052PMVE_34 : Verify that the marketing material file could be uploaded successfully by drapping and dropping");
	  
		/*
			Pre-condition: Partner user has "Add and manage accessories" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
			4. Verify that the 040P Accessory Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052P Model Variant Edit page is displayed.
			6. Drag and drop a file into Marketing Material area
		 */
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertEquals(home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(PartnerAccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052P Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(PartnerAddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Drag and drop a file into Marketing Material area
		home.dragDropFile(Constant.AUDIO_ROUTE_FILE);
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(home.isElementPresent(PartnerAddVariant.MARKETING_FILE_CONTAINER));
		Assert.assertTrue(home.isMarketingUploaded(PartnerAddVariant.MARKETING_FILE_CONTAINER,Constant.AUDIO_ROUTE_FILE));
	}
	
	/*
	 * Verify that the marketing material file could be uploaded successfully via "Select File" button
	 */
	@Test
	public void TC052PMVE_35(){
		result.addLog("ID : TC052PMVE_35 : Verify that the marketing material file could be uploaded successfully via 'Select File' button");
	   /*
		Pre-condition: Partner user has "Add and manage accessories" rights.
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Select an accessory from accessories table
		4. Verify that the 040P Accessory Detail page is displayed
		5. Click "Add New Variant" link from "Model Actions" module
		VP. Verify that the 052P Model Variant Edit page is displayed.
		6. Click "Upload Custom Images" link
		VP. Verify that the "Add Custom Image File" is displayed.
		7. Upload three size of image successfully
		8. Click "Select File" in Marketing Material section
		9. Select a file to upload
		*/
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertEquals(home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(PartnerAccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052P Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(PartnerAddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Click "Upload Custom Images" link
		home.click(PartnerAddVariant.UPLOAD_CUSTOM_IMAGE);
		/*
		 * VP. Verify that the "Add Custom Image File" is displayed
		 */
		home.isElementPresent(PartnerAddVariant.SELECT_FILE_IMAGE_250);
		home.isElementPresent(PartnerAddVariant.SELECT_FILE_IMAGE_500);
		home.isElementPresent(PartnerAddVariant.SELECT_FILE_IMAGE_1000);
		// 7. Upload three size of image successfully
		home.uploadFile(PartnerAddVariant.SELECT_FILE_IMAGE_250, PartnerAddVariant.IMG_NAME[0]);
		home.uploadFile(PartnerAddVariant.SELECT_FILE_IMAGE_500, PartnerAddVariant.IMG_NAME[1]);
		home.uploadFile(PartnerAddVariant.SELECT_FILE_IMAGE_1000, PartnerAddVariant.IMG_NAME[2]);
		// 8. Click "Select File" in Marketing Material section
		// 9. Select a file to upload
		home.uploadFile(PartnerAddVariant.ADD_MARKETING, Constant.AUDIO_ROUTE_FILE);
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(home.isMarketingUploaded(PartnerAddVariant.MARKETING_FILE_CONTAINER, Constant.AUDIO_ROUTE_FILE));
	}
	
	/*
	 * Verify that the valid tuning hpxtt file could be uploaded successfully
	 */
	@Test
	public void TC052PMVE_36(){
		result.addLog("ID : TC052PMVE_36 : Verify that the valid tuning hpxtt file could be uploaded successfully");
	   /*
			Pre-condition: Partner user has "Add and manage accessories" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
			4. Verify that the 040P Accessory Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052P Model Variant Edit page is displayed.
			6. Upload a valid tuning hpxtt file
		*/
		/*
		 * PreCondition: Create new product
		 */
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * **************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), data.get("name"));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP: Verify that the 052P Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Upload a valid tuning hpxtt file
		home.click(AddVariant.UPLOAD_CUSTOM_TUNNING);
		home.uploadFile(AddVariant.ADD_TUNNING, Constant.tuning_hpxtt);
		/*
		 * The tuning hpxtt file is upload successfully
		 */
		Assert.assertTrue(home.isTunningUploaded(AddVariant.UPLOADED_TUNING, Constant.tuning_hpxtt));
		// Delete product
		home.click(AddVariant.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the EAN value only allows for digits value
	 */
	@Test
	public void TC052PMVE_37(){
		result.addLog("ID : TC052PMVE_37 : Verify that the EAN value only allows for digits value");
	   /*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Select a product from Products table
			4. Click “Add New Variant” link
			5. Fill valid value into all required fields except EAN
			6. Fill value which including characters and digits into EAN field
			7. Click “Save” link
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select a product from Products table
		home.selectAnAccessory();
		// 4. Click “Add New Variant” link
		home.click(PartnerAccessoryInfo.ADD_VARIANT);
		// 5. Fill valid value into all required fields except EAN
		// 6. Fill value which including characters and digits into EAN field
		// 7. Click “Save” link
		Hashtable<String, String> data = TestData.variantDraft();
		data.put("ean", RandomStringUtils.randomAlphanumeric(10));
		home.addVariant(PartnerAddVariant.getHash(), data);
		/*
		 * Verify that new variant is unable to save due to the error message inline of EAN field
		 */
		home.checkMessageDisplay(PartnerAddVariant.eanErrMessage[0]);
	}
	
	/*
	 * Verify that the UPC value only allows for digits value
	 */
	@Test
	public void TC052PMVE_38(){
		result.addLog("ID : TC052PMVE_38 : Verify that the UPC value only allows for digits value");
	   /*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Select a product from Products table
			4. Click “Add New Variant” link
			5. Fill valid value into all required fields except UPC
			6. Fill value which including characters and digits into UPC field
			7. Click “Save” link
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select a product from Products table
		home.selectAnAccessory();
		// 4. Click “Add New Variant” link
		home.click(PartnerAccessoryInfo.ADD_VARIANT);
		// 5. Fill valid value into all required fields except UPC
		// 6. Fill value which including characters and digits into UPC field
		// 7. Click “Save” link
		Hashtable<String, String> data = TestData.variantDraft();
		data.put("upc", RandomStringUtils.randomAlphanumeric(10));
		home.addVariant(PartnerAddVariant.getHash(), data);
		/*
		 * Verify that new variant is unable to save due to the error message inline of UPC field
		 */
		Assert.assertTrue(home.checkMessageDisplay(PartnerAddVariant.upcErrMessage[0]));
	}
	
	/*
	 * Verify that the EAN value accepts less than or equal 13 digits string
	 */
	@Test
	public void TC052PMVE_39(){
		result.addLog("ID : TC052PMVE_39 : Verify that the EAN value accepts less than or equal 13 digits string");
	   /*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Select a product from Products table
			4. Click “Add New Variant” link
			5. Fill valid value into all required fields except EAN
			6. Enter a string less than 13 digits into EAN field
			7. Click “Save” link
			VP: New variant is created successfully
			8. Enter a string larger than 13 digits into EAN field
			9. Click “Save” link
			VP: Verify that there is an error message displayed next to the EAN field
			10. Enter a string exactly 13 digits into EAN field
			11. Click “Save”
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select a product from Products table
		home.selectAnAccessory();
		// 4. Click “Add New Variant” link
		home.click(PartnerAccessoryInfo.ADD_VARIANT);
		// 5. Fill valid value into all required fields except EAN
		// 6. Enter a string less than 13 digits into EAN field
		// 7. Click “Save” link
		Hashtable<String, String> data = TestData.variantDraft();
		data.put("ean", RandomStringUtils.randomNumeric(10));
		home.addVariant(PartnerAddVariant.getHash(), data);
		/*
		 * VP: New variant is created successfully
		 */
		Assert.assertEquals(home.existsElement(PartnerVariantInfo.getElementsInfo()).getResult(), "Pass");
		// Navigate to previous product
		home.click(PartnerVariantInfo.PRODUCT_LINK);
		// Click Add New Variant link
		home.click(PartnerAccessoryInfo.ADD_VARIANT);
		// 8. Enter a string larger than 13 digits into EAN field
		// 9. Click “Save” link
		data = TestData.variantDraft();
		data.put("ean", RandomStringUtils.randomNumeric(15));
		home.addVariant(PartnerAddVariant.getHash(), data);
		/*
		 * VP: Verify that there is an error message displayed next to the EAN field
		 */
		Assert.assertTrue(home.checkMessageDisplay(PartnerAddVariant.eanErrMessage[1]));
		// 10. Enter a string exactly 13 digits into EAN field
		home.editData(PartnerAddVariant.EAN, RandomStringUtils.randomNumeric(13));
		// 11. Click “Save”
		home.click(PartnerAddVariant.SAVE);
		/*
		 * Verify that New variant is created successfully
		 */
		Assert.assertEquals(home.existsElement(PartnerVariantInfo.getElementsInfo()).getResult(), "Pass");
	}
	
	/*
	 * Verify that the UPC value accepts less than or equal 13 digits string
	 */
	@Test
	public void TC052PMVE_40(){
		result.addLog("ID : TC052PMVE_40 : Verify that the UPC value accepts less than or equal 13 digits string");
	   /*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Select a product from Products table
			4. Click “Add New Variant” link
			5. Fill valid value into all required fields except UPC
			6. Enter a string less than 12 digits into UPC field
			7. Click “Save” link
			VP: New variant is created successfully
			8. Enter a string larger than 12 digits into UPC field
			9. Click “Save” link
			VP: Verify that there is an error message displayed next to the UPC field
			10. Enter a string exactly 12 digits into UPC field
			11. Click “Save”
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select a product from Products table
		home.selectAnAccessory();
		// 4. Click “Add New Variant” link
		home.click(PartnerAccessoryInfo.ADD_VARIANT);
		// 5. Fill valid value into all required fields except UPC
		// 6. Enter a string less than 12 digits into UPC field
		// 7. Click “Save” link
		Hashtable<String, String> data = TestData.variantDraft();
		data.put("upc", RandomStringUtils.randomNumeric(10));
		home.addVariant(PartnerAddVariant.getHash(), data);
		/*
		 * VP: New variant is created successfully
		 */
		Assert.assertEquals(home.existsElement(PartnerVariantInfo.getElementsInfo()).getResult(), "Pass");
		// Navigate to previous product
		home.click(PartnerVariantInfo.PRODUCT_LINK);
		// Click Add New Variant link
		home.click(PartnerAccessoryInfo.ADD_VARIANT);
		// 8. Enter a string larger than 12 digits into UPC field
		// 9. Click “Save” link
		data = TestData.variantDraft();
		data.put("upc", RandomStringUtils.randomNumeric(15));
		home.addVariant(PartnerAddVariant.getHash(), data);
		/*
		 * VP: Verify that there is an error message displayed next to the UPC field
		 */
		Assert.assertTrue(home.checkMessageDisplay(PartnerAddVariant.upcErrMessage[1]));
		// 10. Enter a string exactly 12 digits into UPC field
		home.editData(PartnerAddVariant.UPC, RandomStringUtils.randomNumeric(12));
		// 11. Click “Save”
		home.click(PartnerAddVariant.SAVE);
		/*
		 * Verify that New variant is created successfully
		 */
		Assert.assertEquals(home.existsElement(PartnerVariantInfo.getElementsInfo()).getResult(), "Pass");
	}
	
	/*
	 * Verify that the tuning file could upload again after canceling uploading tuning file
	 */
	@Test
	public void TC052PMVE_43() {
		result.addLog("ID : TC052PMVE_43: Verify that the tuning file could upload again after canceling uploading tuning file");
		/*
		1. Log into DTS portal as a partner admin
		2. Navigate to "Products" page
		3. Select a product from products table
		4. Click "Add new variant" link
		5. Upload a tuning file
		6. Cancel uploading file
		VP: The message "File upload canceled" and Retry link is displayed
		7. Click "Retry" link
		8. Try to upload new tuning file again
		VP: New tuning file is uploaded successfully
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select a product from products table
		home.selectAnAccessory();
		// Click "Add new variant" link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 5. Upload a tuning file
		home.click(AddVariant.UPLOAD_CUSTOM_TUNNING);
		home.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[0]);
		home.uploadFileInterupt(AddVariant.ADD_TUNNING, Constant.test_upload_cancel);
		// 6. Cancel uploading file
		home.clickImage(Constant.IMG_NAME[7]);
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(home.getTextByXpath(AddVariant.TUNING_UPLOAD_MESSAGE).contains(AddVariant.UPLOAD_FILE_MESSSAGE[2]));
		Assert.assertTrue(home.isElementPresent(AddVariant.RETRY_UPLOAD_TUNING));
		// 7. Click "Retry" link
		// 8. Try to upload catalog image again
		home.uploadFile(AddVariant.RETRY_UPLOAD_TUNING, Constant.tuning_hpxtt);
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath(AddVariant.TUNING_UPLOAD_MESSAGE).contains(AddVariant.UPLOAD_FILE_MESSSAGE[0]));
	}
	
	/*
	 * Verify that three size of catalog images could upload again after canceling uploading.
	 */
	@Test
	public void TC052PMVE_44() {
		result.addLog("ID : TC052PMVE_44: Verify that three size of catalog images could upload again after canceling uploading.");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Select a product from products table
			4. Click "Add new variant" link
			5. Upload three size of primary catalog image
			6. Cancel uploading file
			VP: The message "File upload canceled" and Retry link is displayed
			7. Click "Retry" link
			8. Try to upload another catalog image again
			VP: Three size of catalog image are uploaded successfully
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click “Add Products” link
		home.selectAnAccessory();
		// Click "Add new variant" link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 5. Upload a tuning file
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		home.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[1]);
		home.uploadFileInterupt(AddVariant.SELECT_FILE_IMAGE_250, Constant.test_upload_cancel);
		// 6. Cancel uploading file
		home.clickImage(Constant.IMG_NAME[7]);
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddVariant.UPLOAD_FILE_MESSSAGE[2]));
		Assert.assertTrue(home.isElementPresent(AddVariant.RETRY_UPLOAD_IMAGE_250));
		// 7. Click "Retry" link
		// 8. Try to upload catalog image again
		home.uploadFile(AddVariant.RETRY_UPLOAD_IMAGE_250, Constant.IMG_NAME[0]);
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddVariant.UPLOAD_FILE_MESSSAGE[0]));
		
		//500x500
		home.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[2]);
		home.uploadFileInterupt(AddVariant.SELECT_FILE_IMAGE_500, Constant.test_upload_cancel);
		// 6. Cancel uploading file
		home.clickImage(Constant.IMG_NAME[7]);
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddVariant.UPLOAD_FILE_MESSSAGE[2]));
		Assert.assertTrue(home.isElementPresent(AddVariant.RETRY_UPLOAD_IMAGE_500));
		// 7. Click "Retry" link
		// 8. Try to upload catalog image again
		home.uploadFile(AddVariant.RETRY_UPLOAD_IMAGE_500, Constant.IMG_NAME[1]);
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddVariant.UPLOAD_FILE_MESSSAGE[0]));
		
		//1000x1000
		home.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[3]);
		home.uploadFileInterupt(AddVariant.SELECT_FILE_IMAGE_1000, Constant.test_upload_cancel);
		// 6. Cancel uploading file
		home.clickImage(Constant.IMG_NAME[7]);
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddVariant.UPLOAD_FILE_MESSSAGE[2]));
		Assert.assertTrue(home.isElementPresent(AddVariant.RETRY_UPLOAD_IMAGE_1000));
		// 7. Click "Retry" link
		// 8. Try to upload catalog image again
		home.uploadFile(AddVariant.RETRY_UPLOAD_IMAGE_1000, Constant.IMG_NAME[2]);
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddVariant.UPLOAD_FILE_MESSSAGE[0]));
		
	}
	
	
	/*
	 * Verify that user can upload primary images file by dragging and dropping file into "Add Custom Image File" area.
	 */
	@Test
	public void TC052PMVE_45(){
		result.addLog("ID : TC052PMVE_45 : Verify that user is able to upload wrong demension for primary catalog image");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Select a product from products table
			4. Click "Add new variant" link
			5. Upload a wrong image demension for 250x250 primary catalog image section
			6. Upload a wrong image demension for 500x500 primary catalog image section
			7. Upload a wrong image demension for 1000x100 primary catalog image section
		 */
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		// VP: Verify that the 052P Model Variant Edit page is displayed
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));	
		//6. Click "Upload Custom Images" link
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		// 5. Upload a wrong image demension for 250x250 primary catalog image section
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_250, AddVariant.IMG_NAME[1]);
		// 6. Upload a wrong image demension for 500x500 primary catalog image section
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_500, AddVariant.IMG_NAME[2]);
		// 7. Upload a wrong image demension for 1000x100 primary catalog image section
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_1000, AddVariant.IMG_NAME[0]);
		/*
		 * Demenson of Three size of primary catalog image are automatically rezied and upload successfully and the message "Automaticall resized. Original XX x YY px" is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250));
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddVariant.UPLOAD_FILE_MESSSAGE[5]));
		Assert.assertTrue(home.isElementPresent(AddVariant.CATALOG_IMAGE_UPLOAD_THUMBNAIL_500));
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddVariant.UPLOAD_FILE_MESSSAGE[6]));
		Assert.assertTrue(home.isElementPresent(AddVariant.CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000));
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddVariant.UPLOAD_FILE_MESSSAGE[4]));
	}	
	
	/*
	 * Verify that the marketing material could upload again after canceling uploading file
	 */
	@Test
	public void TC052PMVE_47() {
		result.addLog("ID : TC052PMVE_47: Verify that the marketing material could upload again after canceling uploading file");
		/*
		1. Log into DTS portal as a partner admin
		2. Navigate to "Products" page
		3. Select a product from products table
		4. Click "Add new variant" link
		5. Upload a marketing material file
		6. Cancel uploading file
		VP: The message "File upload canceled" and Retry link is displayed
		7. Click "Retry" link
		8. Try to upload new marketing material again
		VP: New tuning file is uploaded successfully
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select a product from products table
		home.selectAnAccessory();
		// Click "Add new variant" link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 5. Upload a tuning file
		home.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[4]);
		home.uploadFileInterupt(AddVariant.ADD_MARKETING, Constant.test_upload_cancel);
		// 6. Cancel uploading file
		home.clickImage(Constant.IMG_NAME[7]);
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddVariant.MARKETING_UPLOAD_MESSAGE).contains(AddVariant.UPLOAD_FILE_MESSSAGE[2]));
		Assert.assertTrue(home.isElementPresent(AddVariant.RETRY_UPLOAD_MARKETING));
		// 7. Click "Retry" link
		// 8. Try to upload catalog image again
		home.uploadFile(AddVariant.RETRY_UPLOAD_MARKETING, Constant.tuning_hpxtt);
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddAccessory.MARKETING_UPLOAD_MESSAGE).contains(AddVariant.UPLOAD_FILE_MESSSAGE[0]));
	}
}
