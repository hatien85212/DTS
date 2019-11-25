package com.dts.adminportal.partner.test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
import dts.com.adminportal.model.AddBrand;
import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.CompanyInfo;
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.LanguagePackage;
import dts.com.adminportal.model.PartnerAccessoryInfo;
import dts.com.adminportal.model.PartnerAddAccessory;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.Xpath;

public class PartnerUserCatalog051PAccessoryEdit extends CreatePage{
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
	 * Verify that the Adding New Model shows up all necessary fields properly.
	 */
	@Test
	public void TC051PAE_01(){
		result.addLog("ID : TC051PAE_01 : Verify that the Adding New Model shows up all necessary fields properly.");
		/*
		Pre-condition: Partner user has "Add and manage accessories" rights.
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * 051 Accessory Edit page is showed up with:  
		 * Brand dropdown, 
		 * Model Name text field, 
		 * Languages dropdown, 
		 * Model Number, 
		 * EAN/UPC text field, 
		 * Type dropdown, 
		 * Input Specifications table, 
		 * Release date calendar, 
		 * Aliases text field, 
		 * Description text field,
		 * Tuning upload file link, 
		 * Headphone:X Tuning Rating label, 
		 * Product Catalog Images table and 
		 * Marketing Material table.
		 */
		result = home.existsElement(PartnerAddAccessory.getElementInfo());
		Assert.assertEquals("Pass", result.getResult());
	}
	/*
	 Verify that the DTS Tracking Number is generated automatically when editing an accessory.
	 */
	@Test
	public void TC051PAE_02(){
		result.addLog("ID : TC051PAE_02 : Verify that the DTS Tracking Number is generated automatically when editing an accessory.");
		/*
			Pre-condition: Partner user has "Add and manage accessories" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
			VP: The Accessory info page is displayed
			4. Click "Edit" link
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
		 * ****************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		// VP: The Accessory info page is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.DISPLAY_NAME));
		// 4. Click "Edit" link
		home.click(AccessoryInfo.EDIT_MODE);
		/*
		 * 051 Accessory Edit page is showed up and the  
		 * DTS Tracking Number is already generated and it is un-editable.
		 */
		// Check DTS Tracking Number is already generated
		String trackingNumber = home.getTextByXpath(AddAccessory.TRACKING_ID);
		Assert.assertTrue(trackingNumber.length() > 0);
		// Check DTS Tracking Number un-editable
		Assert.assertFalse(home.isElementEditable(AddAccessory.TRACKING_ID));
		// Delete product
		home.click(AddAccessory.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 Verify that the Headphone: X Tuning Rating is read only
	 */
	@Test
	public void TC051PAE_03(){
		result.addLog("ID : TC051PAE_03 : Verify that the Headphone: X Tuning Rating is read only");
		/*
		Pre-condition: Partner user has "Add and manage accessories" rights.
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		 */
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * Verify that 051 Accessory Edit page is showed up and the Headphone: X Tuning Rating is uneditable
		 */
		Assert.assertEquals(home.existsElement(PartnerAddAccessory.getElementInfo()).getResult(), "Pass");
		Assert.assertTrue(home.isElementPresent(PartnerAddAccessory.TUNING_RATING_DISABLE));
		Assert.assertFalse(home.isElementEditable(PartnerAddAccessory.TUNING_RATING_DISABLE));
	}
	
	/*
	 * Verify that when a partner user upload tuning file, the upload tuning link is called "Partner Tuning"
	 */
	@Test
	public void TC051PAE_04(){
		result.addLog("ID : TC051PAE_04 : Verify that when a partner user upload tuning file, the upload tuning link is called 'Partner Tuning'");
		/*
		 Pre-condition: Partner user has "Add and manage accessories" rights.
		 1. Log into DTS portal as a partner admin 
		 2. Navigate to "Accessories" page 
		 3. Click "Add Accessory" link 
		 4. Upload a tuning file successfully
		 */
		
		// 2. Navigate to "Accessories" page 
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link 
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Upload a tuning file successfully
		home.uploadFile(AddAccessory.ADD_TUNNING, Constant.tuning_zip);
		/*
		 * Verify that The upload tuning link is displayed "Partner Tuning"
		 */
		WebElement uploadedTuning = driver.findElement(By.xpath(AddAccessory.UPLOADED_TUNING)).findElement(By.className("mauploadTuning"));
		Assert.assertEquals(home.getTextByXpath(uploadedTuning), "Partner Tuning");
	}
	
	/*
	 * Verify that the invalid tuning file could not be uploaded
	 */
	@Test
	public void TC051PAE_06(){
		result.addLog("ID : TC051PAE_06 : Verify that the invalid tuning file could not be uploaded.");
		/*
			Pre-condition: Partner user has "Add and manage accessories" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
			4. Verify that the 040P Accessory Detail page is displayed
			5. Upload an invalid tuning file
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
		 * *************************************************************
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 *  VP: The Accessory info page is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), data.get("name"));
		// 4. Click "Edit" link
		home.click(AccessoryInfo.EDIT_MODE);
		// 5. Try to upload an invalid tuning file which is based on JSON
		home.uploadFile(AddAccessory.ADD_TUNNING, Constant.tuning_invalid);
		/*
		 * An error message is displayed and the invalid tuning file is not added.
		 */
		Assert.assertTrue(home.getTextByXpath(AddAccessory.TUNING_UPLOAD_MESSAGE).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[1]));
		// Delete product
		home.click(AddAccessory.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 Verify that the valid tuning hpxtt file  could be uploaded successfully.
	 */
	@Test
	public void TC051PAE_07(){
		result.addLog("ID : TC051PAE_07 : Verify that the valid tuning hpxtt file  could be uploaded successfully.");
		/*
		Pre-condition: Partner user has "Add and manage accessories" rights.

		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Add new accessory
		4. Verify that the 040P Accessory Detail page is displayed
		5. Try to upload a valid tuning file which is based on GPB
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. add new accessory
		home.click(AccessoryMain.ADD_ACCESSORY);
		
		// 5. Try to upload a valid hpxtt tuning file
		home.uploadFile(AddAccessory.ADD_TUNNING, Constant.tuning_hpxtt);
		home.click(PartnerAddAccessory.SAVE);
		/*
		 * Verify that the valid tuning hpxtt file  could be uploaded successfully.
		 */
		
		Assert.assertTrue(home.isTunningUploaded(PartnerAddAccessory.UPLOADED_TUNING, Constant.tuning_hpxtt));
	}
	
	/*
	 Verify that the valid tuning zip file  could be uploaded successfully.
	 */
	@Test
	public void TC051PAE_08(){
		result.addLog("ID : TC051PAE_08 : Verify that the valid tuning zip file  could be uploaded successfully.");
		/*
		Pre-condition: Partner user has "Add and manage accessories" rights.

		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Select an accessory from accessories table
		4. Verify that the 040P Accessory Detail page is displayed
		5. Try to upload a valid tuning file which is based on GPB
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. add new accessory
		home.click(AccessoryMain.ADD_ACCESSORY);
		
		// 5. Try to upload a valid hpxtt tuning file
		home.uploadFile(AddAccessory.ADD_TUNNING, Constant.tuning_zip);
		home.click(PartnerAddAccessory.SAVE);
		/*
		 * Verify that the valid tuning hpxtt file  could be uploaded successfully.
		 */
		
		Assert.assertTrue(home.isTunningUploaded(PartnerAddAccessory.UPLOADED_TUNING, Constant.tuning_zip));
	}
	/*
	 Verify that the Type dropdown contains "Earbuds", "In-Ear", "On-Ear" and "Over-Ear".
	 */
	@Test
	public void TC051PAE_09(){
		result.addLog("ID : TC051PAE_09 : Verify that the Type dropdown contains Earbuds, In-Ear, On-Ear and Over-Ear");
		/*
		 * Pre-condition: Partner user has "Add and manage accessories" rights.

		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		4. List out the Type dropdown
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. List out the Type dropdown
		home.click(PartnerAddAccessory.TYPE);
		// Type dropdown contains "Earbuds", "In-Ear", "On-Ear" and "Over-Ear" items.
		ArrayList<String> options = home.getAllOption(PartnerAddAccessory.TYPE);
		Boolean isCorrectType = DTSUtil.containsAll(options, Constant.getType());
		Assert.assertTrue(isCorrectType);
	}
	
	/*
	 Verify that the EAN/UPC is validated for the already exist EAN/UPC code.
	 */
	@Test
	public void TC051PAE_10(){
		result.addLog("ID : TC051PAE_10 : Verify that the EAN/UPC is validated for the already exist EAN/UPC code"
				+ "Pre-condition: Partner user has 'Add and manage accessories' rights." );
		/*
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		4. Fill value into EAN/UPC text field which is saved for another accessory.
		5. Click "Save" link
		*/
		home.logout();
		home.login(dtsUser,dtsPass);
		home.click(Xpath.linkAccessories);
		home.click(AccessoryMain.ADD_ACCESSORY);
		Hashtable<String,String> data= TestData.accessoryPublish();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		home.logout();
		home.login(superpartneruser, superpartnerpassword);
		// Preconditions : Add an accessory and fill the EAN/UPC field with any code (e.g 123456)  
		
		//2. Navigate to Accessory page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);		
		// 3. Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		
		Hashtable<String,String> data1= TestData.appDeviceDraft();
		data1.put("ean", data.get("ean"));
		data1.put("upc",data.get("upc"));
		home.addAccessoriesPartner(AddAccessory.getHash(), data1);
		//Verify that the EAN/UPC is validated for the already exist EAN/UPC code.
		Assert.assertEquals(home.getTextByXpath(AddAccessory.EAN_ERROR_MESSAGE), AddAccessory.eanErrMessage[2]);
		Assert.assertEquals(home.getTextByXpath(AddAccessory.UPC_ERROR_MESSAGE), AddAccessory.upcErrMessage[2]);
		
		//Teardown:
		//Delete product:
		home.click(Xpath.linkAccessories);
		home.selectAnaccessorybyName(data.get("name"));
		home.doDelete(AccessoryInfo.DELETE);
		
	}
	
	/*
	 Verify that the Model Name with (Default) English is required when adding new accessory.
	 */
	@Test
	public void TC051PAE_11(){
		result.addLog("ID : TC051PAE_11 : Verify that the Model Name with (Default) English is required when adding new accessory.");
		/*
		Pre-condition: Partner user has "Add and manage accessories" rights.

		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		4. Fill valid value into all required fields but leaving Model Name empty
		5. Click "Save" link
		*/
		// 2. Navigate to "Accessories" page
		//Navigate to Accessory page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		
		// 3. Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields but leaving Model Name empty
		Hashtable<String, String> data = TestData.accessoryDraft();
		data.remove("name");
		home.addAccessoriesPartner(PartnerAddAccessory.getHash(), data);
		
		String msgErr = home.getTextByXpath(PartnerAddAccessory.NAME_ERR);
		Assert.assertEquals(msgErr, Constant.NAME_ERR);
	}
	
	/*
	 Verify that the "-Select-" item is the default value of Model Name language dropdown
	 */
	@Test
	public void TC051PAE_12(){
		result.addLog("ID : TC051PAE_12	 : Verify that the '-Select-' item is the default value of Model Name language dropdown");
		/*
		Pre-condition: Partner user has "Add and manage accessories" rights.

		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// The "- Select -" item is the default value of Model Name language dropdown
		String select = home.getItemSelected(PartnerAddAccessory.LANGUAGE_DROPDOWN);
		Assert.assertEquals(select, "- Select -");
	}
	/*
	 Verify that the Model Name language dropdown contains 10 items properly
	 */
	@Test
	public void TC051PAE_13(){
		result.addLog("ID : TC051PAE_13 : Verify that the tuning file based on JSON could be uploaded successfully.");
		/*
		Pre-condition: Partner user has "Add and manage accessories" rights.
		
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		4. List out the Model Name language dropdown		
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. List out the Model Name language dropdown
		home.click(PartnerAddAccessory.LANGUAGE_DROPDOWN);
		/*
		 * The Model Name language dropdown contains items: 
		 * - Select -, 
		 * Chinese - Simplified, 
		 * Chinese - Traditional, 
		 * French, 
		 * German, 
		 * Italian, 
		 * Japanese, 
		 * Korean, 
		 * Russian and 
		 * Spanish.
		 */
		ArrayList<String> options = home.getAllOption(PartnerAddAccessory.LANGUAGE_DROPDOWN);
		Boolean isCorrectType = DTSUtil.containsAll(options, AddAccessory.LANGUAGE_LIST);
		Assert.assertTrue(isCorrectType);
	}
	/*
	 Verify that the hint text "Inherits default" is displayed 
	 for an empty fields of each external model name language.
	 */
	@Test
	public void TC051PAE_14(){
		result.addLog("ID : TC051PAE_14	 : Verify that the hint text 'Inherits default' is displayed for an empty fields of each external model name language.");
		/*
		Pre-condition: Partner user has "Add and manage accessories" rights.

		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		4. Select another language for Model Name
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Select another language for Model Name
		home.selectOptionByName(PartnerAddAccessory.LANGUAGE_DROPDOWN, PartnerAddAccessory.language[1]);
		// The hint text "Inherits default" is displayed in external model name text field.
		String hintText = home.getHintText(PartnerAddAccessory.OTHER_LANGUAGE_NAME);
		Assert.assertEquals(hintText, "inherits default");
	}
	
	/*
	 * Verify that the external language model name text field is disabled when the language dropdown is "Select".
	 */
	@Test
	public void TC051PAE_15(){
		result.addLog("ID : TC051PAE_15	 : Verify that the external language model name text field is disabled when the language dropdown is 'Select'.");
		/*
		Pre-condition: Partner user has "Add and manage accessories" rights.

		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		4. Set language dropdown to another language
		5. Set language dropdown to "Select"
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Select another language for Model Name
		home.selectOptionByName(PartnerAddAccessory.LANGUAGE_DROPDOWN, PartnerAddAccessory.language[1]);
		// 5. Set language dropdown to "Select"
		home.selectOptionByName(PartnerAddAccessory.LANGUAGE_DROPDOWN, PartnerAddAccessory.language[0]);
		// The external model name language text field is disabled.
		String attribute = home.getAtribute(PartnerAddAccessory.OTHER_LANGUAGE_NAME, "disabled");
		Assert.assertEquals(attribute, "true");
	}
	
	/*
	 * Verify that another language dropdown will display when selecting a model name language.
	 */
	@Test
	public void TC051PAE_16(){
		result.addLog("ID : TC051PAE_16	 : Verify that another language dropdown will display when selecting a model name language.");
		/*
		Pre-condition: Partner user has "Add and manage accessories" rights.

		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		4. Set language dropdown to another item other than "Select"
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// get number of language language dropdown is displayed before select another language
		int after = home.getNumElementDisplayedByText("inherits default");
		// 4. Select another language for Model Name
		home.selectOptionByName(PartnerAddAccessory.LANGUAGE_DROPDOWN, PartnerAddAccessory.language[1]);
		// Another language dropdown is displayed
		int before = home.getNumElementDisplayedByText("inherits default");
		Assert.assertEquals(after + 1, before);
	}
	
	/*
	 Verify that a warning message is displayed for duplicating language dropdowns
	 */
	@Test
	public void TC051PAE_17(){
		result.addLog("ID : TC051PAE_17 : Verify that a warning message is displayed for duplicating language dropdowns"
				+ "Pre-condition: Partner user has 'Add and manage accessories' rights." );
		/*
		 * 	1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Select language dropdown to another item other than "Select"
			VP: Verify that new language dropdown is displayed.
			5. Select the same item for second language dropdown
		 */
				
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// get number of language language dropdown is displayed before select another language
		int currentSize = home.getNumElementDisplayedByText("inherits default");
		// 4. Select another language for Model Name
		home.selectOptionByName(PartnerAddAccessory.LANGUAGE_DROPDOWN, PartnerAddAccessory.language[1]);
		// VP: Verify that new language dropdown is displayed.
		// to do
		// Get the language package
		List<LanguagePackage> languagepackage = home.getLanguagePackage(PartnerAddAccessory.LANGUAGE_CONTAINER);
		// 5. Select the same item for second language dropdown
		home.selectOptionByName(languagepackage.get(currentSize).languagedropbox, PartnerAddAccessory.language[1]);
		// Get text of the  warning message is displayed at the first package
		languagepackage = home.getLanguagePackage(PartnerAddAccessory.LANGUAGE_CONTAINER);
		String warning = languagepackage.get(0).warningmessage;
		System.out.println(warning);
		/*
		 Verify that a warning message is displayed for duplicating language dropdowns
		 */
		Assert.assertEquals(warning,AddAccessory.DUPLICATE_WARNING_MESSAGE);
	}
	
	/*
	 Verify that another language dropdown will display when selecting a model name language
	 */
	@Test
	public void TC051PAE_18(){
		result.addLog("ID : TC051PAE_18 : Verify that the language dropdown is restore to default 'Select' value and the input field is cleared when selecting the Trashcan icon"
				+ "Pre-condition: User has 'Add and manage accessories' rights." );
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Select language dropdown to another item other than "Select"
			5. Type value into model nam input field
			6. Click on Trashcan icon
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);		
		// 4. Set language dropdown to another item other than "Select"
		home.selectOptionByName(PartnerAddAccessory.LANGUAGE_DROPDOWN, PartnerAddAccessory.language[1]);
		// 5. Type value into model nam input field 
		home.type(PartnerAddAccessory.OTHER_LANGUAGE_NAME, "just a test");
		// 6. Click on Trashcan icon 
		home.click(PartnerAddAccessory.TRASH_ICON);
		
		/*
		 The language dropdown is restore to default "Select" value and the input field is cleared 
		 */
		Assert.assertEquals(home.getItemSelected(PartnerAddAccessory.LANGUAGE_DROPDOWN), PartnerAddAccessory.language[0]);
		// The input field is cleared
		List<LanguagePackage> languagepackage = home.getLanguagePackage(PartnerAddAccessory.LANGUAGE_CONTAINER);
		Assert.assertEquals(languagepackage.get(0).name.getText(), "");
	}
	
	/*
	 * Verify that the three size of Primary Catalog Image display in an order list
	 */
	@Test
	public void TC051PAE_19(){
		result.addLog("ID : TC051PAE_19 : Verify that the three size of Primary Catalog Image display in an order list");
	   /*
		Pre-condition: Partner user has "Add and manage accessories" rights.
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		*/
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * Verify that Three size of Primary Catalog Image display display in order: 250x250 pixels, 500x500 pixels, 1000x1000 pixels.
		 */
		Assert.assertTrue(home.isImageDisplayInOrder(AddAccessory.IMAGE_CATALOG));
	}
	
	/*
	 * Verify that the image thumbnail is displayed correctly for each Primary Catalog Image.
	 */
	@Test
	public void TC051PAE_20(){
		result.addLog("ID : TC051PAE_20 : Verify that the image thumbnail is displayed correctly for each Primary Catalog Image.");
	   /*
		Pre-condition: Partner user has "Add and manage Products" rights.

			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Click "Add product" link
			4. Upload image for each size of Primary Catalog Image successfully
			VP: The correct image thumbnail is displayed next to image name. 
		*/
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		//Upload image for each size of Primary Catalog Image successfully
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_250, AddAccessory.IMG_NAME[0]);
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_500, AddAccessory.IMG_NAME[1]);
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_1000, AddAccessory.IMG_NAME[2]);
		
		/*
		 * The correct image thumbnail is displayed next to image name. 
		 */
		Assert.assertTrue(home.isElementDisplayHorizontal(AddAccessory.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250, AddAccessory.CATALOG_IMAGE_UPLOAD_INFO_250));
		Assert.assertTrue(home.isElementDisplayHorizontal(AddAccessory.CATALOG_IMAGE_UPLOAD_THUMBNAIL_500, AddAccessory.CATALOG_IMAGE_UPLOAD_INFO_500));
		Assert.assertTrue(home.isElementDisplayHorizontal(AddAccessory.CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000, AddAccessory.CATALOG_IMAGE_UPLOAD_INFO_1000));
	}
	
	/*
	 * Verify that the uploaded date and file size are displayed below the image name for each size of Primary Catalog Image.
	 */
	@Test
	public void TC051PAE_21(){
		result.addLog("ID : TC051PAE_21 : Verify that the uploaded date and file size are displayed below the image name for each size of Primary Catalog Image.");
	   /*
		Pre-condition: Partner user has "Add and manage Products" rights.

				1. Log into DTS portal as a partner admin
				2. Navigate to "Accessories" page
				3. Click "Add product" link
				4. Fill valid value into all required fields
				5. Upload image for each size of Primary Catalog Image successfully
				6. Click "Save" link
				VP: Verify that the product info page is displayed
				7. Click "Edit" link in product detail page
				VP: The product edit page is displayed and the uploaded date and file size are displayed below the image name for each size of Primary Catalog Image.
		*/
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields
		// 5. Upload image for each size of Primary Catalog Image successfully
		// 6. Click "Save" link
		Hashtable<String,String> data = TestData.accessoryImage();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		//VP: Verify that the product info page is displayed
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.TITLE_NAME));
		// 7. Click "Edit" link in product detail page
		home.click(AccessoryInfo.EDIT_MODE);
		/*
		 * The uploaded date and file size are displayed below the image name for each size of Primary Catalog Image. 
		 */
		Assert.assertTrue(home.isElementDisplayVertically(AddAccessory.CATALOG_IMAGE_TITLE_LINK_250, AddAccessory.CATALOG_IMAGE_FILE_SIZE_250));
		Assert.assertTrue(home.isElementDisplayVertically(AddAccessory.CATALOG_IMAGE_TITLE_LINK_500, AddAccessory.CATALOG_IMAGE_FILE_SIZE_500));
		Assert.assertTrue(home.isElementDisplayVertically(AddAccessory.CATALOG_IMAGE_TITLE_LINK_1000, AddAccessory.CATALOG_IMAGE_FILE_SIZE_1000));
		
		//Teardown: Delete product
		home.deleteAnaccessorybyName(data.get("name"));
	}
	
	/*
	 * Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on inage thumbnail
	 */
	@Test
	public void TC051PAE_22() throws InterruptedException{
		result.addLog("ID : TC051PAE_22 : Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on inage thumbnail");
	   /*
		Pre-condition: Partner user has "Add and manage Products" rights.
			
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Click "Add product" link
			4. Upload image for each size of Primary Catalog Image successfully
			5. Click on image thumbnail of each size
		*/
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4.  Upload image for each size of Primary Catalog Image successfully
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_250, AddAccessory.IMG_NAME[0]);
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_500, AddAccessory.IMG_NAME[1]);
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_1000, AddAccessory.IMG_NAME[2]);
		// 5. Click on image thumbnail of each size
		//Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on inage thumbnail
		//Verify lightbox image is displayed for 250x250
		home.click(AddAccessory.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250);
		//wait for ligtbox display
		home.waitForElementClickable(AddAccessory.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AddAccessory.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddAccessory.LIGHTBOX_STYLE_IMAGE, "src").contains(AddAccessory.IMG_NAME[0].replaceAll(".jpg", "")));
		
		//Close the lightbox fot 250x250
		home.click(AddAccessory.LIGHTBOX_CLOSE);
		Thread.sleep(3000);

		//Verify lightbox image is displayed for 500x500	
		home.click(AddAccessory.CATALOG_IMAGE_UPLOAD_THUMBNAIL_500);
		//wait for ligtbox display
		home.waitForElementClickable(AddAccessory.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AddAccessory.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddAccessory.LIGHTBOX_STYLE_IMAGE, "src").contains(AddAccessory.IMG_NAME[1].replaceAll(".jpg", "")));
		
		//Close the lightbox fot 500x500
		home.click(AddAccessory.LIGHTBOX_CLOSE);
		Thread.sleep(3000);
		
		//Verify lightbox image is displayed for 1000x1000
		home.click(AddAccessory.CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000);
		//wait for ligtbox display
		home.waitForElementClickable(AddAccessory.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AddAccessory.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddAccessory.LIGHTBOX_STYLE_IMAGE, "src").contains(AddAccessory.IMG_NAME[2].replaceAll(".jpg", "")));
		home.click(AddAccessory.LIGHTBOX_CLOSE);
		
		//Cancel saving
		home.click(AddAccessory.CANCEL);
		
	}
	
	/*
	 * Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on file name.
	 */
	@Test
	public void TC051PAE_23() throws InterruptedException{
		result.addLog("ID : TC051PAE_23 : Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on file name.");
	   /*
		Pre-condition: Partner user has "Add and manage Products" rights.

		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Click "Add product" link
		4. Upload image for each size of Primary Catalog Image successfully
		5. Click on file name of each size
		*/
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4.  Upload image for each size of Primary Catalog Image successfully
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_250, AddAccessory.IMG_NAME[0]);
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_500, AddAccessory.IMG_NAME[1]);
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_1000, AddAccessory.IMG_NAME[2]);
		// 5. Click on file name of each size
		//Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on inage thumbnail
		//Verify lightbox image is displayed for 250x250
		home.click(AddAccessory.CATALOG_IMAGE_TITLE_LINK_250);
		//wait for ligtbox display
		home.waitForElementClickable(AddAccessory.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AddAccessory.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddAccessory.LIGHTBOX_STYLE_IMAGE, "src").contains(AddAccessory.IMG_NAME[0].replaceAll(".jpg", "")));
		
		//Close the lightbox fot 250x250
		home.click(AddAccessory.LIGHTBOX_CLOSE);
		Thread.sleep(3000);

		//Verify lightbox image is displayed for 500x500	
		home.click(AddAccessory.CATALOG_IMAGE_TITLE_LINK_500);
		//wait for ligtbox display
		home.waitForElementClickable(AddAccessory.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AddAccessory.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddAccessory.LIGHTBOX_STYLE_IMAGE, "src").contains(AddAccessory.IMG_NAME[1].replaceAll(".jpg", "")));
		
		//Close the lightbox fot 500x500
		home.click(AddAccessory.LIGHTBOX_CLOSE);
		Thread.sleep(3000);
		
		//Verify lightbox image is displayed for 1000x1000
		home.click(AddAccessory.CATALOG_IMAGE_TITLE_LINK_1000);
		//wait for ligtbox display
		home.waitForElementClickable(AddAccessory.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(home.isElementPresent(AddAccessory.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddAccessory.LIGHTBOX_STYLE_IMAGE, "src").contains(AddAccessory.IMG_NAME[2].replaceAll(".jpg", "")));
		home.click(AddAccessory.LIGHTBOX_CLOSE);
		
		//Cancel saving
		home.click(AddAccessory.CANCEL);
		
	}
	
	/*
	 * Verify that three size of Primary Catalog Image could be replaced by another successfully
	 */
	@Test
	public void TC051PAE_24(){
		result.addLog("ID : TC051PAE_24 : Verify that three size of Primary Catalog Image could be replaced by another successfully");
	   /*
		Pre-condition: Partner user has "Add and manage accessories" rights.
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		4. Upload image for each size of Primary Catalog Image successfully
		5. Delete the uploaded image by clicking on trashcan icon
		6. Upload image again
		*/

		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Upload image for each size of Primary Catalog Image successfully
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_250, AddAccessory.IMG_NAME[0]);
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_500, AddAccessory.IMG_NAME[1]);
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_1000, AddAccessory.IMG_NAME[2]);
		// 5. Delete the uploaded image by clicking on trashcan icon
		home.deleteAllUploadedImage(AddAccessory.IMAGE_CATALOG);
		// 6. Upload image again
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_250, AddAccessory.IMG_NAME[0]);
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_500, AddAccessory.IMG_NAME[1]);
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_1000, AddAccessory.IMG_NAME[2]);
		/*
		 * Verify that new image is uploaded successfully
		 */
		Assert.assertTrue(home.isElementPresent(driver.findElement(
				By.xpath(AddAccessory.IMAGE250_DISPLAY)).findElement(
				By.tagName("img"))));
		Assert.assertTrue(home.isElementPresent(driver.findElement(
				By.xpath(AddAccessory.IMAGE500_DISPLAY)).findElement(
				By.tagName("img"))));
		Assert.assertTrue(home.isElementPresent(driver.findElement(
				By.xpath(AddAccessory.IMAGE1000_DISPLAY)).findElement(
				By.tagName("img"))));
	}
	
	/*
	 * Verify that the marketing material file could be uploaded successfully by drapping and dropping
	 */
	@Test
	public void TC051PAE_25(){
		result.addLog("ID : TC051PAE_25 : Verify that the marketing material file could be uploaded successfully by drapping and dropping");
	   /*
			Pre-condition: Partner user has "Add and manage accessories" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Drap and drop a file into Marketing Material area
		*/
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Drap and drop a file into Marketing Material area
		home.dragDropFile(Constant.AUDIO_ROUTE_FILE);
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(home.isElementPresent(AddAccessory.MARKETING_FILE_CONTAINER));
		Assert.assertTrue(home.isMarketingUploaded(AddAccessory.MARKETING_FILE_CONTAINER,Constant.AUDIO_ROUTE_FILE));
	}
	
	/*
	 Verify that the marketing material file could be uploaded successfully via 'Select File' button
	 */
	@Test
	public void TC051PAE_26(){
		result.addLog("ID : 051PAE_26 : Verify that the marketing material file could be uploaded successfully via 'Select File' button"
				+ "Pre-condition: User has 'Add and manage accessories' rights." );
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		4. Click "Select File" in Marketing Material section
		5. Select a file to upload
		*/	
		// 2.Navigate to Accessory page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3.Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4.Fill valid value into all required fields but leaving Model Name empty
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("brand", "testing");
		data.put("name", RandomStringUtils.randomNumeric(2));
		data.put("number", RandomStringUtils.randomNumeric(5));
		data.put("upc", RandomStringUtils.randomNumeric(5));
		data.put("type", "In-Ear");
		data.put("wired","Mono");
		data.put("bluetooth","");
		data.put("date", "");
		data.put("aliases", "Test ALIASES");
		data.put("add tunning", "");
		data.put("img", "");
		data.put("add marketing", "");
		data.put("description", "Test DESCRIPTION");
		home.fillAddAccessoriesDTS(AddAccessory.getHash(), data);
		// 5. Select a file to upload
		String filename = "Chrysanthemum.jpg";
		home.uploadFile(AddAccessory.ADD_MARKETING,filename);
		
		/*
		 Verify that the marketing material file could be uploaded successfully via 'Select File' button
		 */
		home.isMarketingUploaded(AddAccessory.MARKETING_FILE_CONTAINER,filename);
		Assert.assertTrue(home.isElementPresent(AddAccessory.MARKETING_FILE_CONTAINER));
	}
	/*
	 * Verify that the Input Specifications section inludes "Connection Type" and "Support Input Channels" module.
	 */
	@Test
	public void TC051PAE_27(){
		result.addLog("ID : TC051PAE_27	 : Verify that the Input Specifications section inludes 'Connection Type' and \"Support Input Channels\" module..");
		/*
		Pre-condition: Partner user has "Add and manage accessories" rights.

		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * The Input Specifications section inludes 
		 * "Connection Type" and
		 * "Support Input Channels" module.
		 */
		ArrayList <String> header=home.getInputSpecificationHeader(PartnerAddAccessory.INPUT_SPECIFICATIONS);
		Assert.assertTrue(DTSUtil.containsAll(header, AddAccessory.inputSpecificationHeader));
		
	}
	
	/*
	 * Verify that at least one connection type is required when creating an accessory.
	 */
	@Test
	public void TC051PAE_28(){
		result.addLog("ID : TC051PAE_28   : Verify that at least one connection type is required when creating an accessory.");
		
		/*Pre-condition: Partner user has "Add and manage accessories" rights.

		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		4. Fill valid value into all required fields but do not check for any connection type
		5. Click "Save" link
		
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields but do not check for any connection type

		home.selectOptionByName(AddAccessory.BRAND, "testing");
		home.editData(AddAccessory.NAME, RandomStringUtils.randomNumeric(5));
		home.editData(AddAccessory.MODEL_NUMBER, RandomStringUtils.randomNumeric(9));
		home.editData(AddAccessory.UPC, RandomStringUtils.randomNumeric(5));
		home.selectOptionByName(AddAccessory.TYPE, "In-Ear");
		home.selectOptionByName(AddAccessory.DATE, "");
		home.editData(AddAccessory.ALIASES, "Test ALIASES");
		home.selectOptionByName(AddAccessory.ADD_TUNNING, "");
		home.selectOptionByName(AddAccessory.SELECT_FILE_IMAGE_250, "");
		home.selectOptionByName(AddAccessory.ADD_MARKETING, "");
		home.editData(AddAccessory.DESCRIPTION, "Test DESCRIPTION");
		// 5. Click "Save" link
		home.click(AddAccessory.SAVE);
		// Verify that at least one connection type is required when creating an accessory
		String isdisplayed = home.getAtribute(AddAccessory.CONNECTION_REQUIRE_MESSAGE, "style");
		Assert.assertFalse(isdisplayed.contains("display: none"));
	}
	
	/*
	 Verify that at least one Supported Input Channels count is required when creating an accessory.
	 */
	@Test
	public void TC051PAE_29(){
		result.addLog("ID : 051PAE_29 : Verify that at least one Supported Input Channels count is required when creating an accessory.");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Fill valid value into all required fields but do not select any supported input channels value
			5. Click "Save" link
		*/
		// 2.Navigate to Accessory page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3.Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields but do not check for any connection type
		Hashtable<String, String> data = TestData.accessoryDraft();
		data.remove("wired");
		data.remove("save");
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		//Select both wire and bluetooth connection type but leave the content channel capability empty
		//Leave the content channel empty
		home.selectInputChannel(AddAccessory.WIRED_CONTAINER, "none");
		home.selectInputChannel(AddAccessory.BLUETOOTH_CONTAINER, "none");
		//Click "Save" link
		home.click(AddAccessory.SAVE);
		/*
		 * Verify that at least one Supported Input Channels count is required when creating an accessory.
		 */
		Assert.assertEquals(home.getTextByXpath(AddAccessory.WIRED_MESSAGE), AddAccessory.INPUT_CHANNEL_ERROR_STRING);
		Assert.assertEquals(home.getTextByXpath(AddAccessory.BLUETOOTH_MESSAGE), AddAccessory.INPUT_CHANNEL_ERROR_STRING);
	}
	
	/*
	 * Verify that the Supported Input Channels combobox is enabled when the Connection Type is checked.
	 */	@Test
	public void TC051PAE_30(){
		result.addLog("ID : TC051PAE_30	 : Verify that the Supported Input Channels combobox is enabled when the Connection Type is checked.");
		/*
		Pre-condition: Partner user has "Add and manage accessories" rights.

			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			VP: Verify that the 051P Accessory Edit page is displayed
			4. Tick to check for a Connection Type
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		//VP: Verify that the 051P Accessory Edit page is displayed
		Assert.assertTrue(home.isElementPresent(PartnerAddAccessory.INPUT_SPECIFICATIONS));
		// 4. Tick to check for a Connection Type
		home.selectACheckbox(AddAccessory.WIRED_CHECKBOX);
		home.selectACheckbox(AddAccessory.BLUETOOTH_CHECKBOX);
		/*
		 * Verify that the similar Supported Input Channels is enabled.
		 */
		Assert.assertTrue(home.isElementEditable(AddAccessory.WIRED_DROPBOX));
		Assert.assertTrue(home.isElementEditable(AddAccessory.BLUETOOTH_DROPBOX));
	}
	
	/*
	 * Verify that the Supported Input Channels combobox is disabled when the Connection Type is unchecked.
	 */
	@Test
	public void TC051PAE_31(){
		result.addLog("ID : TC051PAE_31	 : Verify that the Supported Input Channels combobox is disabled when the Connection Type is unchecked.");
		/*
		Pre-condition: Partner user has "Add and manage accessories" rights.

			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			VP: Verify that the 051P Accessory Edit page is displayed
			4. Uncheck a Connection Type
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		//VP: Verify that the 051P Accessory Edit page is displayed
		Assert.assertTrue(home.isElementPresent(PartnerAddAccessory.INPUT_SPECIFICATIONS));
		// 4. Tick to check for a Connection Type
		home.selectACheckbox(AddAccessory.WIRED_CHECKBOX);
		home.selectACheckbox(AddAccessory.BLUETOOTH_CHECKBOX);
		// VP: Verify that the input channel are enabled
		Assert.assertTrue(home.isElementEditable(AddAccessory.WIRED_DROPBOX));
		Assert.assertTrue(home.isElementEditable(AddAccessory.BLUETOOTH_DROPBOX));
		// Tick to uncheck the connection type
		home.uncheckACheckbox(AddAccessory.WIRED_CHECKBOX);
		home.uncheckACheckbox(AddAccessory.BLUETOOTH_CHECKBOX);
		// VP: Verify that the input channel are enabled
		Assert.assertFalse(home.isElementEditable(AddAccessory.WIRED_DROPBOX));
		Assert.assertFalse(home.isElementEditable(AddAccessory.BLUETOOTH_DROPBOX));
	}
	
	/*
	 * Verify that the “Company” combo box is not displayed when adding new product
	 */
	@Test
	public void TC051PAE_32(){
		result.addLog("ID : TC051PAE_32	 : Verify that the “Company” combo box is not displayed when adding new product");
		/*
		1. Log into DTS portal as a partner admin
		2. Navigate to "Products" page
		3. Click "Add Product" link
		*/
		
		// 2. Navigate to "Products" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * Verify that The Edit product page is displayed but the “Company” combobox is hidden
		 */
		Assert.assertEquals(home.existsElement(PartnerAddAccessory.getElementInfo()).getResult(), "Pass");
		Assert.assertFalse(home.isElementPresent(PartnerAddAccessory.COMPANY));
	}
	
	/*
	 * Verify that the “Brands” combo box is editable if the company only has multiple brands and partner user have privilege to create multiple brands when adding new product
	 */
	@Test
	public void TC051PAE_33(){
		result.addLog("ID : TC051PAE_33	 : Verify that the “Brands” combo box is editable if the company only has multiple brands and partner user have privilege to create multiple brands when adding new product");
		/*
		Pre-condition: The company has multiple brands and partner user has privilege to create multiple brands
		1. Log into DTS portal as a partner admin
		2. Navigate to "Products" page
		3. Click "Add Product" link
		*/
		
		// 2. Navigate to "Products" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * Verify that The  “Brands”  combobox is displayed and editable
		 */
		Assert.assertTrue(home.isElementEditable(PartnerAddAccessory.BRAND));
	}
	
	/*
	 * Verify that the “Brands” combo box is not displayed if the company only has one brand when adding new product
	 */
	@Test
	public void TC051PAE_34(){
		result.addLog("ID : TC051PAE_34	: Verify that the “Brands” combo box is not displayed if the company only has one brand when adding new product");
		/*
			Pre-condition: The company only has one brand
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Click "Add Product" link
		*/
		/*
		 * Pre-condition: The company only has one brand
		 */
		// Navigate to Company page
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// Delete all brands except brand "TestCorp-Brand1"
		home.deleteAllBrandExceptOne(CompanyInfo.BRAND_LIST, partner_brand_name_1);
		/*
		 * ******************************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * Verify that The  “Company” and “Brands”  comboboxes are not displayed
		 */
		Assert.assertFalse(home.isElementPresent(PartnerAddAccessory.COMPANY));
		Assert.assertFalse(home.isElementPresent(PartnerAddAccessory.BRAND));
		/*
		 *  Post-condition: Create brand "TestCorp-Brand2"
		 */
		// Navigate to company page
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// Click add new brand link
		home.click(CompanyInfo.ADD_BRAND);
		// Create brand
		Hashtable<String,String> data = TestData.brandDraft();
		data.put("name", "TestCorp-Brand2");
		home.addBrand(AddBrand.getHash(), data);
		/*
		 * *******************************************************************
		 */
	}
	
	/*
	 * Verify that the “Brands” combo box is not displayed if the partner user has privilege to create one brand when adding new product
	 */
	@Test
	public void TC051PAE_35(){
		result.addLog("ID : TC051PAE_35	 : Verify that the “Brands” combo box is not displayed if the partner user has privilege to create one brand when adding new product");
		/*
		Pre-condition: The company has multiple brand but the partner user only has privilege to create one brand
		1. Log into DTS portal as a partner admin
		2. Navigate to "Products" page
		3. Click "Add Product" link
		*/
		
		/*
		 * Pre-condition: The company has multiple brand but the partner user only has privilege to create one brand
		 */
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		// Select an exist pattner user
		home.selectUserByEmail(partneruser);
		// Enable "Add and manage accessories" privilege with has one brand privilege
		home.click(UserMgmt.EDIT);
		home.enableAllPrivilege();
		home.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, partner_brand_name_1);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Log in DTS portal as partner user above
		home.login(partneruser, password);
		/*
		 * End Pre-condition
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * The  “Company” and “Brands”  comboboxes are not displayed
		 */
		Assert.assertFalse(home.isElementPresent(PartnerAddAccessory.COMPANY));
		Assert.assertFalse(home.isElementPresent(PartnerAddAccessory.BRAND));
		
		//Teardown: Reenable all user brand privilege
		home.logout();
		home.login(superpartneruser, superpartnerpassword);
		home.click(Xpath.LINK_PARTNER_USER);
		// Select an exist pattner user
		home.selectUserByEmail(partneruser);
		// Enable "Add and manage accessories" privilege with has one brand privilege
		home.click(UserMgmt.EDIT);
		home.enableAllPrivilege();
		home.click(AddUser.SAVE);
	}
	
	/*
	 * Verify that the “Brands” combo box is editable if the company only has multiple brands and partner user have privilege to create multiple brands when editing existing product
	 */
	@Test
	public void TC051PAE_36(){
		result.addLog("ID : TC051PAE_36	 : Verify that the “Brands” combo box is editable if the company only has multiple brands and partner user have privilege to create multiple brands when editing existing product");
		/*
			Pre-condition: The company has multiple brands and partner user has privilege to create multiple brands
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Select a product from products list
			4. Click “Edit” link
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
		 * ********************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select a product from products list
		home.selectAnaccessorybyName(data.get("name"));
		// 4. Click “Edit” link
		home.click(PartnerAccessoryInfo.EDIT_MODE);
		/*
		 * Verify that The  “Brands”  combobox is displayed and editable
		 */
		Assert.assertTrue(home.isElementEditable(PartnerAddAccessory.BRAND_LIST));
		// Delete product
		home.click(AddAccessory.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
	}	
	
	/*
	 * Verify that the “Brands” combo box is displayed as read only if the company only has one brand when editing existing product
	 */
	@Test
	public void TC051PAE_37(){
		result.addLog("ID : TC051PAE_37	: Verify that the “Brands” combo box is displayed as read only if the company only has one brand when editing existing product");
		/*
			Pre-condition: The company only has one brand
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Select a product from products list
			4. Click “Edit” link
		*/
		/*
		 * Pre-condition: The company only has one brand
		 */
		// Navigate to Company page
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// Delete all brands except brand "TestCorp-Brand1"
		home.deleteAllBrandExceptOne(CompanyInfo.BRAND_LIST, partner_brand_name_1);
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> dataProduct = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), dataProduct);
		/*
		 * ******************************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select a product from products list
		home.selectAnaccessorybyName(dataProduct.get("name"));
		// 4. Click “Edit” link
		home.click(PartnerAccessoryInfo.EDIT_MODE);
		/*
		 * Verify that The  “Brands”  combobox is displayed as read only
		 */
		Assert.assertFalse(home.isElementEditable(PartnerAddAccessory.BRAND));
		// Delete product
		home.click(AddAccessory.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
		/*
		 *  Post-condition: Create brand "TestCorp-Brand2"
		 */
		// Navigate to company page
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// Click add new brand link
		home.click(CompanyInfo.ADD_BRAND);
		// Create brand
		Hashtable<String,String> data = TestData.brandDraft();
		data.put("name", partner_brand_name_2);
		home.addBrand(AddBrand.getHash(), data);
		/*
		 * *******************************************************************
		 */
	}
		
	/*
	 * Verify that the “Brands” combo box is displayed as read if the partner user has privilege to manage one brand when editing existing product
	 */
	@Test
	public void TC051PAE_38(){
		result.addLog("ID : TC051PAE_38	 : Verify that the “Brands” combo box is displayed as read if the partner user has privilege to manage one brand when editing existing product");
		/*
			Pre-condition: The company has multiple brand but the partner user only has privilege to create one brand
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Select a product from products list which under mange of partner user
			4. Click “Edit” link
		*/
		/*
		 * Pre-condition: The company has multiple brand but the partner user only has privilege to create one brand
		 */
		// Navigate to User page
		home.click(Xpath.LINK_PARTNER_USER);
		// Select an exist pattner user
		home.selectUserByEmail(partneruser);
		// Enable "Add and manage accessories" privilege with has one brand privilege
		home.click(UserMgmt.EDIT);
		home.enableAllPrivilege();
		home.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, partner_brand_name_1);
		home.click(AddUser.SAVE);
		// Navigate to Product page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create new product
		Hashtable<String,String> dataProduct = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), dataProduct);
		// Logout user
		home.logout();
		// Log in DTS portal as partner user above
		home.login(partneruser, password);
		/*
		 * End Pre-condition
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Product" link
		home.selectAnaccessorybyName(dataProduct.get("name"));
		// 4. Click “Edit” link
		home.click(AccessoryInfo.EDIT_MODE);
		// Verify that The  “Brands”  combobox is displayed as read only.
		Assert.assertFalse(home.isElementPresent(AddAccessory.COMPANY));
		Assert.assertFalse(home.isElementEditable(AddAccessory.BRAND));
		// Delete product
		home.click(AddAccessory.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
		/*
		 * Teardown: Re-enable all user brand privilege
		 */
		home.logout();
		home.login(superpartneruser, superpartnerpassword);
		home.click(Xpath.LINK_PARTNER_USER);
		// Select an exist pattner user
		home.selectUserByEmail(partneruser);
		// Enable "Add and manage accessories" privilege with has one brand privilege
		home.click(UserMgmt.EDIT);
		home.enableAllPrivilege();
		home.click(AddUser.SAVE);
	}
	
	/*
	 * Verify that the EAN value only allows for numeric value
	 */
	@Test
	public void TC051PAE_39(){
		result.addLog("ID : TC051PAE_39	 : Verify that the EAN value only allows for numeric  value");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Click “Add Products” link
			4. Fill valid value into all required fields except EAN
			5. Fill value which including characters and numeric into EAN field
			6. Click “Save” link
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click “Add Products” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields except EAN
		// 5. Fill value which including characters and numeric into EAN field
		// 6. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryDraft();
		data.put("ean", RandomStringUtils.randomAlphanumeric(13));
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * Verify that new product is unable to save due to the error message inline of EAN field
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddAccessory.eanErrMessage[0]));
	}
	
	/*
	 * Verify that the UPC value only allows for numeric  value
	 */
	@Test
	public void TC051PAE_40(){
		result.addLog("ID : TC051PAE_40	 : Verify that the UPC value only allows for numeric value");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Click “Add Products” link
			4. Fill valid value into all required fields except UPC
			5. Fill value which including characters and numeric into UPC field
			6. Click “Save” link
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click “Add Products” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields except UPC
		// 5. Fill value which including characters and numeric into UPC field
		// 6. Click “Save” link
		Hashtable<String, String> data = TestData.accessoryDraft();
		data.put("upc", RandomStringUtils.randomAlphanumeric(12));
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * Verify that new product is unable to save due to the error message inline of UPC field
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddAccessory.upcErrMessage[0]));
	}
	
	/*
	 * Verify that the EAN value accepts less than or equal 13 digits string
	 */
	@Test
	public void TC051PAE_41(){
		result.addLog("ID : TC051PAE_41 : Verify that the EAN value accepts less than or equal 13 digits string");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Click “Add Products” link
			4. Fill valid value into all required fields except EAN
			5. Enter a string less than 13 digits into EAN field
			6. Click “Save” link
			VP: New product is created successfully
			7. Enter a string larger than 13 digits into EAN field
			8. Click “Save” link
			VP: Verify that there is an error message displayed next to the EAN field
			9. Enter a string exactly 13 digits into EAN field
			10. Click “Save”
		*/
		
		// 2. Navigate to "Products" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click “Add Products” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields except UPC
		// 5. Enter a string less than 13 digits into EAN field
		// 6. Click “Save” link
		Hashtable<String, String> data = TestData.accessoryDraft();
		data.put("ean", RandomStringUtils.randomNumeric(14));
		data.remove("upc");
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: An error message is displayed that notify the ean value could not exceed 13 digits
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddAccessory.eanErrMessage[1]));
		// Delete product
		// 7. Enter 13 digits into EAN field
		// 8. Click “Save” link
		home.editData(AddAccessory.EAN, RandomStringUtils.randomNumeric(13));
		home.click(AddAccessory.SAVE);
		//Verify that new product is saved successfully
		Assert.assertEquals(home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult(), "Pass");
		
		// Edit above product
		home.click(AccessoryInfo.EDIT_MODE);
		// change the ean value less than 13 digits
		home.editData(AddAccessory.EAN, RandomStringUtils.randomNumeric(12));
		// Click Save
		home.click(AddAccessory.SAVE);;
		//Verify that new product is saved successfully
		Assert.assertEquals(home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult(), "Pass");
		
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the UPC value accepts less than or equal 12 digits string
	 */
	@Test
	public void TC051PAE_42(){
		result.addLog("ID : TC051PAE_42 : Verify that the UPC value accepts less than or equal 12 digits string");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Click “Add Products” link
			4. Fill valid value into all required fields except UPC
			5. Enter a string less than 12 digits into UPC field
			6. Click “Save” link
			VP: New product is created successfully	
			7. Enter a string larger than 12 digits into UPC field
			8. Click “Save” link
			VP: Verify that there is an error message displayed next to the UPC field
			9. Enter a string exactly 12 digits into UPC field
			10. Click “Save”
		*/
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click “Add Products” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields except UPC
		// 5. Enter a string less than 13 digits into EAN field
		// 6. Click “Save” link
		Hashtable<String, String> data = TestData.accessoryDraft();
		data.put("upc", RandomStringUtils.randomNumeric(13));
		data.remove("ean");
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: An error message is displayed that notify the ean value could not exceed 13 digits
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddAccessory.upcErrMessage[1]));
		// Delete product
		// 7. Enter 13 digits into EAN field
		// 8. Click “Save” link
		home.editData(AddAccessory.UPC, RandomStringUtils.randomNumeric(12));
		home.click(AddAccessory.SAVE);
		//Verify that new product is saved successfully
		Assert.assertEquals(home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult(), "Pass");
			
		// Edit above product
		home.click(AccessoryInfo.EDIT_MODE);
		// change the ean value less than 13 digits
		home.editData(AddAccessory.UPC, RandomStringUtils.randomNumeric(11));
		// Click Save
		home.click(AddAccessory.SAVE);;
		//Verify that new product is saved successfully
		Assert.assertEquals(home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult(), "Pass");
		
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	
	
	/*
	 * Verify that the tuning file could upload again after canceling uploading tuning file
	 */
	@Test
	public void TC051PAE_45() {
		result.addLog("ID : TC051PAE_45: Verify that the tuning file could upload again after canceling uploading tuning file");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Click “Add Products” link
			4. Upload a tuning file
			5. Cancel uploading file
			VP: The message "File upload canceled" and Retry link is displayed
			6. Click "Retry" link
			7. Try to upload new tuning file again
			VP: New tuning file is uploaded successfully
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click “Add Products” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Upload a tuning file
		home.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[0]);
		home.uploadFileInterupt(AddAccessory.ADD_TUNNING, Constant.test_upload_cancel);
		// 6. Cancel uploading file
		home.clickImage(Constant.IMG_NAME[7]);
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(home.getTextByXpath(AddAccessory.TUNING_UPLOAD_MESSAGE).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[2]));
		Assert.assertTrue(home.isElementPresent(AddAccessory.RETRY_UPLOAD_TUNING));
		// 7. Click "Retry" link
		// 8. Try to upload new tuning file again
		home.uploadFile(AddAccessory.RETRY_UPLOAD_TUNING, Constant.tuning_hpxtt);
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(home.isTunningUploaded(AddAccessory.UPLOADED_TUNING, Constant.tuning_hpxtt));
	}
	
	/*
	 * Verify that three size of catalog images could upload again after canceling uploading.
	 */
	@Test
	public void TC051PAE_46() {
		result.addLog("ID : TC051PAE_46: Verify that three size of catalog images could upload again after canceling uploading.");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Click “Add Products” link
			4. Upload three size of primary catalog image
			5. Cancel uploading file
			VP: The message "File upload canceled" and Retry link is displayed
			6. Click "Retry" link
			7. Try to upload another catalog image again
			VP: Three size of catalog image are uploaded successfully
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click “Add Products” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Upload a tuning file
		home.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[1]);
		home.uploadFileInterupt(AddAccessory.SELECT_FILE_IMAGE_250, Constant.test_upload_cancel);
		// 6. Cancel uploading file
		home.clickImage(Constant.IMG_NAME[7]);
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[2]));
		Assert.assertTrue(home.isElementPresent(AddAccessory.RETRY_UPLOAD_IMAGE_250));
		// 7. Click "Retry" link
		// 8. Try to upload catalog image again
		home.uploadFile(AddAccessory.RETRY_UPLOAD_IMAGE_250, Constant.IMG_NAME[0]);
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[0]));
		
		//500x500
		home.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[2]);
		home.uploadFileInterupt(AddAccessory.SELECT_FILE_IMAGE_500, Constant.test_upload_cancel);
		// 6. Cancel uploading file
		home.clickImage(Constant.IMG_NAME[7]);
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[2]));
		Assert.assertTrue(home.isElementPresent(AddAccessory.RETRY_UPLOAD_IMAGE_500));
		// 7. Click "Retry" link
		// 8. Try to upload catalog image again
		home.uploadFile(AddAccessory.RETRY_UPLOAD_IMAGE_500, Constant.IMG_NAME[1]);
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[0]));
		
		//1000x1000
		home.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[3]);
		home.uploadFileInterupt(AddAccessory.SELECT_FILE_IMAGE_1000, Constant.test_upload_cancel);
		// 6. Cancel uploading file
		home.clickImage(Constant.IMG_NAME[7]);
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[2]));
		Assert.assertTrue(home.isElementPresent(AddAccessory.RETRY_UPLOAD_IMAGE_1000));
		// 7. Click "Retry" link
		// 8. Try to upload catalog image again
		home.uploadFile(AddAccessory.RETRY_UPLOAD_IMAGE_1000, Constant.IMG_NAME[2]);
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[0]));
		
	}
	
	/*
	 * Verify that user is able to upload wrong demension for primary catalog image
	 */
	@Test
	public void TC051PAE_47(){
		result.addLog("ID : TC051PAE_47 : Verify that user is able to upload wrong demension for primary catalog image");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Click “Add Products” link
			4. Upload a wrong image demension for 250x250 primary catalog image section
			5. Upload a wrong image demension for 500x500 primary catalog image section
			6. Upload a wrong image demension for 1000x100 primary catalog image section
		*/
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click “Add Products” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Upload a wrong image demension for 250x250 primary catalog image section
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_250, AddAccessory.IMG_NAME[1]);
		// 5. Upload a wrong image demension for 500x500 primary catalog image section
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_500, AddAccessory.IMG_NAME[2]);
		// 6. Upload a wrong image demension for 1000x100 primary catalog image section
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_1000, AddAccessory.IMG_NAME[0]);
		
		/*
		 * VP: Verify that user is able to upload wrong demension for primary catalog image
		 */
		Assert.assertTrue(home.isElementPresent(AddAccessory.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250));
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[5]));
		Assert.assertTrue(home.isElementPresent(AddAccessory.CATALOG_IMAGE_UPLOAD_THUMBNAIL_500));
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[6]));
		Assert.assertTrue(home.isElementPresent(AddAccessory.CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000));
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains( AddAccessory.UPLOAD_FILE_MESSSAGE[4]));
	}
	
	/*
	 * Verify that the marketing material could upload again after canceling uploading file
	 */
	@Test
	public void TC051PAE_49() {
		result.addLog("ID : TC051PAE_49: Verify that the marketing material could upload again after canceling uploading file");
		/*
		1. Log into DTS portal as a partner admin
		2. Navigate to "Products" page
		3. Click “Add Products” link
		4. Upload a marketing material file
		5. Cancel uploading file
		VP: The message "File upload canceled" and Retry link is displayed
		6. Click "Retry" link
		7. Try to upload new marketing material again
		VP: New marketing material file is uploaded successfully
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click “Add Products” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Upload a tuning file
		home.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[4]);
		home.uploadFileInterupt(AddAccessory.ADD_MARKETING, Constant.test_upload_cancel);
		// 6. Cancel uploading file
		home.clickImage(Constant.IMG_NAME[7]);
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddAccessory.MARKETING_UPLOAD_MESSAGE).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[2]));
		Assert.assertTrue(home.isElementPresent(AddAccessory.RETRY_UPLOAD_MARKETING));
		// 7. Click "Retry" link
		// 8. Try to upload catalog image again
		home.uploadFile(AddAccessory.RETRY_UPLOAD_MARKETING, Constant.AUDIO_ROUTE_FILE);
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddAccessory.MARKETING_UPLOAD_MESSAGE).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[0]));
	}
	
	/*
	 * Verify that user can upload tuning file by dragging and dropping file into "Add Tuning File" area.
	 */
	@Test
	public void TC051PAE_50(){
		result.addLog("ID : TC051PAE_50 : Verify that user can upload tuning file by dragging and dropping file into 'Add Tuning File' area.");
	   /*
			Pre-condition: Partner user has "Add and manage Products" rights.
			
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Click "Add Product" link
			4. Verify that the add product page is displayed
			5. Drag and Drop a tuning file into Add Tuning File area
			
			VP: The tuning file is upload successfully.
		*/
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Drap and drop a file into Marketing Material area
		home.dragDropFile(Constant.tuning_hpxtt);
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath(AddAccessory.TUNING_UPLOAD_MESSAGE).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[0]));
		Assert.assertTrue(home.isTunningUploaded(AddAccessory.UPLOADED_TUNING,Constant.tuning_hpxtt));
	}
	
	/*
	 * Verify that user can upload three size of primary catalog images by dragging and dropping file into each primary image uploading area.
	 */
	@Test
	public void TC051PAE_51(){
		result.addLog("ID : TC051PAE_51 : Verify that user can upload three size of primary catalog images by dragging and dropping file into each primary image uploading area.");
	   /*
			Pre-condition: Partner user has "Add and manage Products" rights.

			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Click "Add Product" link
			4. Verify that the add product page is displayed
			5. Drag and Drop each three size of primary catalog images.
			VP Three size of primary catalog image are upload successfully.
		*/
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 5. Drag and Drop each three size of primary catalog images
		//Three size of primary catalog image are upload successfully.
		//250x250
		home.dragDropFile(Constant.IMG_NAME[0]);
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[0]));
		//500x500
		home.dragDropFile(Constant.IMG_NAME[1]);
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[0]));
		
		//1000x1000
		home.dragDropFile(Constant.IMG_NAME[2]);
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[0]));
		
	}
	
}
