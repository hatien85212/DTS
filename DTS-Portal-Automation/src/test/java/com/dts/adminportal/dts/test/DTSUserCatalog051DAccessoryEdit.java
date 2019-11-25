package com.dts.adminportal.dts.test;
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
import com.dts.adminportal.util.DateUtil;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AccessoryInfo;
import dts.com.adminportal.model.AccessoryMain;
import dts.com.adminportal.model.AddAccessory;
import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.AddVariant;
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.LanguagePackage;
import dts.com.adminportal.model.PartnerAccessoryInfo;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UserEdit;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.VariantInfo;
import dts.com.adminportal.model.Xpath;

public class DTSUserCatalog051DAccessoryEdit extends CreatePage{
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
	 * Verify that the Adding New Model shows up all necessary fields properly
	 */
	@Test
	public void TC051DAE_01(){
		result.addLog("ID : 051DAE_01 : Verify that the Adding New Model shows up all necessary fields properly");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
		 */
		// 2. Navigate to Accessory page
		home.click(Xpath.linkAccessories);
		// 3. Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * Verify that 051 product Edit page is showed up
		 */
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(), "Pass");
	}
	
	/*
	 * Verify that the DTS Tracking Number is generated automatically when adding new accessory
	 */
	@Test
	public void TC051DAE_02(){
		result.addLog("ID : 051DAE_02 : Verify that the DTS Tracking Number is generated automatically when adding new accessory"
				+ "Pre-condition: User has 'Add and manage accessories' rights." );
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		 */
		// 2. Navigate to Accessory page
		home.click(Xpath.linkAccessories);
		// 3. Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 Verify that the DTS Tracking Number is generated automatically when adding new accessory
		 */
		// DTS Tracking Number is already generated
		Assert.assertTrue(home.existsElement(AddAccessory.TRACKING_ID));
		Assert.assertTrue(home.getTextByXpath(AddAccessory.TRACKING_ID).length() > 0);	
		// DTS Tracking Number is uneditable.
		Assert.assertFalse(home.canEdit(AddAccessory.TRACKING_ID));
	}
	
	/*
	 * Verify that the Headphone: X Tuning Rating and Headphone:X inside are read only for DTS users who do not have "Approve accessory tunings" privilege.
	 */
	@Test
	public void TC051DAE_03(){
		result.addLog("ID : 051DAE_03 : Verify that the Headphone: X Tuning Rating and Headphone:X inside are read only for DTS users who do not have 'Approve accessory tunings' privilege");
		/*
			Pre-condition: DTS user does not have "Approve product tunings" privilege.
			1. Log into DTS portal as a DTS user
			2. Navigate to "products" page
			3. Click "Add product" link
		 */
		
		/*
		 * Pre-condition: DTS user does not have "Approve product tunings" privilege.
		 */
		// Navigate to user page
		home.click(Xpath.LINK_USERS);
		// Select DTS user
		home.dtsSelectUserByEmail(dtsUser);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Approve product tunings" privilege
		home.disablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.Approve_product_tuning);
		home.click(AddUser.SAVE);
		// Log out
		home.logout();
		/*
		 * ********************************************************************
		 */
		// 1. Log into DTS portal as a DTS user
		home.login(dtsUser, dtsPass);
		// 2. Navigate to "products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * Verify that Headphone: X Tuning Rating and Headphone:X inside are read only
		 */
		Assert.assertFalse(home.isElementEditable(AddAccessory.TUNING_RATING_DISABLE));
		Assert.assertFalse(home.isElementEditable(AddAccessory.HEADPHONEX_INSIDE_DISABLE));

	}

	/*
	 * Verify that the DTS users who have "Approve product tunings" privilege could edit the Headphone: X Tuning Rating and Headphone:X inside fields
	 */
	@Test
	public void TC051DAE_04(){
		result.addLog("ID : 051DAE_04 : Verify that the DTS users who have 'Approve product tunings' privilege could edit the Headphone: X Tuning Rating and Headphone:X inside fields");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 *  Verify that Headphone: X Tuning Rating and Headphone:X inside are displayed as dropdown list and user can set their items successfully
		 */
		Assert.assertTrue(home.isElementEditable(AddAccessory.TUNING_RATING));
		Assert.assertTrue(home.isElementEditable(AddAccessory.HEADPHONEX_INSIDE));
	}
	
	/*
	 * Verify that when a DTS user upload tuning file, the upload tuning link is called "DTS Tuning".
	 */
	@Test
	public void TC051DAE_05(){
		result.addLog("ID : 051DAE_05 : Verify that when a DTS user upload tuning file, the upload tuning link is called 'DTS Tuning'");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Upload a tuning file successfully
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Upload a tuning file successfully
		home.uploadFile(AddAccessory.ADD_TUNNING, Constant.tuning_hpxtt);
		/*
		 * Verify that The upload tuning link is displayed "DTS Tuning"
		 */
		WebElement uploadedTuning = driver.findElement(By.xpath(AddAccessory.UPLOADED_TUNING)).findElement(By.className("mauploadTuning"));
		Assert.assertEquals(home.getTextByXpath(uploadedTuning), "DTS Tuning");
	}
	
	/*
	 * Verify that the invalid tuning file could not be uploaded
	 */
	@Test
	public void TC051DAE_06(){
		result.addLog("ID : 051DAE_06 : Verify that the invalid tuning file could not be uploaded");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click Add product link
			4. Verify that the 051D Product Edit page is displayed
			5. Upload an invalid tuning file
		 */
		// 2. Navigate to Accessory page
		home.click(Xpath.linkAccessories);
		// 3. Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Verify that the 051D Product Edit page is displayed
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(), "Pass");
		// 5. Upload an invalid tuning file
		home.uploadFile(AddAccessory.ADD_TUNNING, Constant.tuning_invalid);
		/*
		 * Verify that An error message is displayed and the invalid tuning file is not added
		 */	
		WebElement failedTuning = driver.findElement(By.xpath(AddAccessory.UPLOADED_TUNING)).findElement(By.tagName("p"));
		Assert.assertEquals(home.getTextByXpath(failedTuning), "! Invalid file");
	}
	
	/*
	 * Verify that the valid tuning zip file could be uploaded successfully
	 */
	@Test
	public void TC051DAE_07(){
		result.addLog("ID : 051DAE_07 : Verify that the valid tuning zip file could be uploaded successfully");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click Add product link
			VP: Verify that the 051D Product Edit page is displayed
			4. Upload a valid tuning zip file
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP: Verify that the 051D Product Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(), "Pass");
		// 4. Upload a valid tuning zip file
		home.uploadFile(AddAccessory.ADD_TUNNING, Constant.tuning_zip);
		/*
		 * Verify that The tuning zip file is upload successfully
		 */	
		Assert.assertTrue(home.isTunningUploaded(AddAccessory.UPLOADED_TUNING, Constant.tuning_zip));
	}
	
	/*
	 * Verify that the valid tuning hpxtt file could be uploaded successfully
	 */
	@Test
	public void TC051DAE_08(){
		result.addLog("ID : 051DAE_08 : Verify that the valid tuning hpxtt file could be uploaded successfully");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click Add product link
			VP: Verify that the 051D Product Edit page is displayed
			4. Upload a valid tuning hpxtt file
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP: Verify that the 051D Product Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(), "Pass");
		// 4. Upload a valid tuning zip file
		home.uploadFile(AddAccessory.ADD_TUNNING, Constant.tuning_hpxtt);
		/*
		 * Verify that The tuning zip file is upload successfully
		 */	
		Assert.assertTrue(home.isTunningUploaded(AddAccessory.UPLOADED_TUNING, Constant.tuning_hpxtt));
	}
	
	/*
	 Verify Type dropdown contains "Earbuds", "In-Ear", "On-Ear" and "Over-Ear" items
	 */
	@Test
	public void TC051DAE_09(){
		result.addLog("ID : 051DAE_09 : Verify that the Type dropdown contains 'Earbuds', 'In-Ear', 'On-Ear' and 'Over-Ear'."
				+ "Pre-condition: User has 'Add and manage accessories' rights." );
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		4. List out the Type dropdown
		*/
		
		// 2. Navigate to Accessory page
		home.click(Xpath.linkAccessories);
		// 3. Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. List out the Type dropdown
	
		/*
		 Verify Type dropdown contains "Earbuds", "In-Ear", "On-Ear" and "Over-Ear" items
		 */
		ArrayList<String> types = home.getAllOption(AddAccessory.TYPE);
		Assert.assertTrue(DTSUtil.containsAll(types, AddAccessory.TYPE_LIST));
		
	}
	
	/*
	 * Verify that the EAN and UPC are validated for the already exist EAN/UPC code
	 */
	@Test
	public void TC051DAE_10(){
		result.addLog("ID : 051DAE_10 : Verify that the EAN and UPC are validated for the already exist EAN/UPC code");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Fill value into EAN and UPC text fields which is saved for another product.
			5. Click "Save" link
		*/
		/*
		 * Precondition: Create new product
		 */
		// Navigate to Accessory page
		home.click(Xpath.linkAccessories);
		// Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryDraft();
		String productName = data.get("name");
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to Accessory page
		home.click(Xpath.linkAccessories);  
		// 3. Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill value into EAN and UPC text fields which is saved for another product.
		// 5. Click "Save" link
		data.put("name", "Test" + RandomStringUtils.randomNumeric(6));
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Check if PDPP-467 found
		if(!home.checkMessageDisplay("EAN must be unique")){
			result.addErrorLog("PDPP-467: 051D Accessory Edit : The warning message is not displayed when adding duplicate EAN/UPC");
			Assert.assertTrue(false);
		}
		/*
		 * Verify that A warning message "Warning: A model already exist with this EAN and UPC code"
		 */
		Assert.assertTrue(home.checkMessageDisplay("EAN must be unique"));
		Assert.assertTrue(home.checkMessageDisplay("UPC must be unique"));
		// Delete product
		home.deleteAnaccessorybyName(productName);
	}
	
	/*
	 * Verify that the Model Name with (Default) English is required when adding new product
	 */	
	@Test
	public void TC051DAE_11(){
		result.addLog("ID : 051DAE_11 : Verify that the Model Name with (Default) English is required when adding new product");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Fill valid value into all required fields but leaving Model Name empty
			5. Click "Save" link
		*/
				
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields but leaving Model Name empty
		// 5. Click "Save" link
		Hashtable<String,String> data = TestData.accessoryDraft();
		data.remove("name");
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * Verify that A warning message is displayed which notifying the model name is empty
		 */
		Assert.assertTrue(home.checkMessageDisplay("Model name is required."));
	}
	
	/*
	 * Verify that the "-Select-" item is the default value of Model Name language dropdown
	 */	
	@Test
	public void TC051DAE_12(){
		result.addLog("ID : 051DAE_12 : Verify that the '-Select-' item is the default value of Model Name language dropdown");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
		*/
		// 2.Navigate to Accessory page
		home.click(Xpath.linkAccessories);
		// 3.Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * Verify that The "- Select -" item is the default value of Model Name language dropdown
		 */
		Assert.assertEquals(home.getItemSelected(AddAccessory.LANGUAGE_DROPDOWN), AddAccessory.LANGUAGE_LIST[0]);
	}
	
	/*
	 * Verify that the Model Name language dropdown contains 10 items properly
	 */
	@Test
	public void TC051DAE_13(){
		result.addLog("ID : 051DAE_13 : Verify that the Model Name language dropdown contains 10 items properly");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. List out the Model Name language dropdown
		*/
		// 2.Navigate to Accessory page
		home.click(Xpath.linkAccessories);
		// 3.Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. List out the Model Name language dropdown
		ArrayList<String> languageOptions = home.getAllComboboxOption(AddAccessory.LANGUAGE_DROPDOWN);
		/*
		 * The Model Name language dropdown contains items: - Select -, Chinese - Simplified, Chinese - Traditional, French, German, Italian, Japanese, Korean, Russian and Spanish
		 */
		Assert.assertTrue(DTSUtil.containsAll(languageOptions, AddAccessory.LANGUAGE_LIST));
	}
	
	/*
	 Verify that the hint text "Inherits default" is displayed for an empty fields of each model name language
	 */
	@Test
	public void TC051DAE_14(){
		result.addLog("ID : 051DAE_14 : Verify that the hint text 'Inherits default' is displayed for an empty fields of each model name language"
				+ "Pre-condition: User has 'Add and manage accessories' rights." );
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		4. Select another language for Model Name
		*/
				
		// 2.Navigate to Accessory page
		home.click(Xpath.linkAccessories);
		// 3.Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Select another language for Model Name
		home.selectOptionByName(AddAccessory.LANGUAGE_DROPDOWN, AddAccessory.LANGUAGE_LIST[2]);
		/*
		 Verify that the hint text "Inherits default" is displayed for an empty fields of each model name language
		 */ 
		Assert.assertEquals(home.getAtribute(AddAccessory.EMPTY_LANGUAGE_FIELD, "placeholder"), "inherits default");
	}
	
	/*
	 Verify that the external language model name text field is disabled when the language dropdown is 'Select'
	 */
	@Test
	public void TC051DAE_15(){
		result.addLog("ID : 051DAE_15 : Verify that the external language model name text field is disabled when the language dropdown is 'Select'"
				+ "Pre-condition: User has 'Add and manage accessories' rights." );
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		4. Set language dropdown to "Select"
		*/
				
		// 2.Navigate to Accessory page
		home.click(Xpath.linkAccessories);
		// 3.Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Select another language for Model Name
		home.selectOptionByName(AddAccessory.LANGUAGE_DROPDOWN, AddAccessory.LANGUAGE_LIST[0]);
		/*
		 Verify that the external language model name text field is disabled when the language dropdown is 'Select'
		 */ 
		Assert.assertEquals(home.getAtribute(AddAccessory.EMPTY_LANGUAGE_FIELD, "disabled"),"true");
	}
	
	/*
	 * Verify that another language dropdown will display when selecting a model name language
	 */
	@Test
	public void TC051DAE_16(){
		result.addLog("ID : 051DAE_16 : Verify that another language dropdown will display when selecting a model name language");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Set language dropdown to another item other than "Select"
		*/
		// 2.Navigate to Accessory page
		home.click(Xpath.linkAccessories);
		// 3.Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Set language dropdown to another item other than "Select"
		home.selectOptionByName(AddAccessory.LANGUAGE_DROPDOWN, AddAccessory.LANGUAGE_LIST[3]);
		/*
		 * Verify that another language dropdown will display when selecting a model name language
		 */
		int languageIndex = home.getLanguageContainerIndex(AddAccessory.LANGUAGE_CONTAINER);
		Assert.assertTrue(languageIndex > 1);
	}
	
	/*
	 * Verify that a warning message is displayed for duplicating language dropdowns
	 */
	@Test
	public void TC051DAE_17(){
		result.addLog("ID : 051DAE_17 : Verify that a warning message is displayed for duplicating language dropdowns");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Select language dropdown to another item other than "Select"
			VP: Verify that new language dropdown is displayed.
			5. Select the same item for second language dropdown
		*/
		// 2.Navigate to Accessory page
		home.click(Xpath.linkAccessories);
		// 3.Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Set language dropdown to another item other than "Select"
		home.selectOptionByName(AddAccessory.LANGUAGE_DROPDOWN,	AddAccessory.LANGUAGE_LIST[3]);
		/*
		 * Verify that another language dropdown will display when selecting a model name language
		 */
		List<LanguagePackage> languagePackages = home.getLanguagePackage(AddAccessory.LANGUAGE_CONTAINER);
		Assert.assertTrue(languagePackages.size() > 1);
		// 5. Select the same item for second language dropdown
		home.selectOptionByName(languagePackages.get(languagePackages.size() - 1).languagedropbox,	AddAccessory.LANGUAGE_LIST[3]);
		/*
		 * A warning message is displayed which notifying for the duplicating of languages
		 */
		Assert.assertTrue(home.checkMessageDisplay("Language is duplicated"));
	}
	
	/*
	 * Verify that the language dropdown is restore to default "Select" value and the input field is cleared when selecting the Trashcan icon
	 */
	@Test
	public void TC051DAE_18(){
		result.addLog("ID : 051DAE_18 : Verify that the language dropdown is restore to default 'Select' value and the input field is cleared when selecting the Trashcan icon");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Select language drop-down to another item other than "Select"
			5. Type value into model name input field
			6. Click on Trash-can icon
		*/
		// 2.Navigate to Accessory page
		home.click(Xpath.linkAccessories);
		// 3.Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Set language drop-down to another item other than "Select"
		home.selectOptionByName(AddAccessory.LANGUAGE_DROPDOWN, AddAccessory.LANGUAGE_LIST[3]);
		// 5. Type value into model name input field
		home.editData(AddAccessory.OTHER_LANGUAGE_NAME, "just a test");
		// 6. Click on Trashcan icon 
		home.click(AddAccessory.TRASH_ICON);
		/*
		 * Verify that The language dropdown is restore to default "Select" value and the input field is cleared 
		 */
		Assert.assertEquals(home.getItemSelected("//*[@id='lang-div-container']/div[1]/select"), "- Select -");
		Assert.assertEquals(home.getTextByXpath(AddAccessory.OTHER_LANGUAGE_NAME), "");
	}
	
	/*
	 * Verify that the three size of Primary Catalog Image display in an order list
	 */
	@Test
	public void TC051DAE_19(){
		result.addLog("ID : 051DAE_19 : Verify that the three size of Primary Catalog Image display in an order list");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * Three size of Primary Catalog Image display display in vertical order list: 250x250 pixels, 500x500 pixels, 1000x1000 pixels
		 */
		Assert.assertTrue(home.isImageDisplayInOrder(AddAccessory.IMAGE_CATALOG));
	}
	
	/*
	 * Verify that the image thumbnail is displayed correctly for each Primary Catalog Image
	 */
	@Test
	public void TC051DAE_20(){
		result.addLog("ID : 051DAE_20 : Verify that the image thumbnail is displayed correctly for each Primary Catalog Image");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Upload image for each size of Primary Catalog Image successfully
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Upload image for each size of Primary Catalog Image successfully
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_250, Constant.IMG_NAME[0]);
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_500, Constant.IMG_NAME[1]);
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_1000, Constant.IMG_NAME[2]);
		/*
		 * Verify that The correct image thumbnail is displayed next to image name link
		 */
		Assert.assertTrue(home.isElementDisplayHorizontal(AddAccessory.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250, AddAccessory.CATALOG_IMAGE_UPLOAD_INFO_250));
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_INFO_250).contains(Constant.IMG_NAME[0]));
		Assert.assertTrue(home.isElementDisplayHorizontal(AddAccessory.CATALOG_IMAGE_UPLOAD_THUMBNAIL_500, AddAccessory.CATALOG_IMAGE_UPLOAD_INFO_500));
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_INFO_500).contains(Constant.IMG_NAME[1]));
		Assert.assertTrue(home.isElementDisplayHorizontal(AddAccessory.CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000, AddAccessory.CATALOG_IMAGE_UPLOAD_INFO_1000));
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_INFO_1000).contains(Constant.IMG_NAME[2]));
	}
	
	/*
	 * Verify that the image file name, uploaded date and file size are displayed below the image name for each size of Primary Catalog Image
	 */
	@Test
	public void TC051DAE_21(){
		result.addLog("ID : 051DAE_21 : Verify that the image file name, uploaded date and file size are displayed below the image name for each size of Primary Catalog Image");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Upload image for each size of Primary Catalog Image successfully
			VP: Verify that the image file name and  file size is displayed below the catalog image file name link
			5. Click "Save" link
			VP: Verify that new product is added successfully
			6. Click "Edit" link
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Upload image for each size of Primary Catalog Image successfully
		Hashtable<String,String> data = TestData.accessoryImage();
		data.remove("save");
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: Verify that the image file name and  file size is displayed below the catalog image file name link
		 */
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_INFO_250).contains(Constant.IMG_NAME[0]));
		Assert.assertTrue(home.isElementDisplayVertically(AddAccessory.CATALOG_IMAGE_TITLE_LINK_250, AddAccessory.CATALOG_IMAGE_FILE_SIZE_250));
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_INFO_500).contains(Constant.IMG_NAME[1]));
		Assert.assertTrue(home.isElementDisplayVertically(AddAccessory.CATALOG_IMAGE_TITLE_LINK_500, AddAccessory.CATALOG_IMAGE_FILE_SIZE_500));
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_INFO_1000).contains(Constant.IMG_NAME[2]));
		Assert.assertTrue(home.isElementDisplayVertically(AddAccessory.CATALOG_IMAGE_TITLE_LINK_1000, AddAccessory.CATALOG_IMAGE_FILE_SIZE_1000));
		// 5. Click "Save" link
		home.click(AddAccessory.SAVE);
		/*
		 * VP: Verify that new product is added successfully
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), data.get("name"));
		// 6. Click "Edit" link
		home.click(AccessoryInfo.EDIT_MODE);
		// Get the current date
		String date = DateUtil.getADateGreaterThanToday("dd MMM yyyy", 0);
		/*
		 * The uploaded date and file size are displayed below the image name for each size of Primary Catalog Image
		 */
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_INFO_250).contains(date));
		Assert.assertTrue(home.isElementDisplayVertically(AddAccessory.CATALOG_IMAGE_TITLE_LINK_250, AddAccessory.CATALOG_IMAGE_FILE_SIZE_250));
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_INFO_500).contains(date));
		Assert.assertTrue(home.isElementDisplayVertically(AddAccessory.CATALOG_IMAGE_TITLE_LINK_500, AddAccessory.CATALOG_IMAGE_FILE_SIZE_500));
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_INFO_1000).contains(date));
		Assert.assertTrue(home.isElementDisplayVertically(AddAccessory.CATALOG_IMAGE_TITLE_LINK_1000, AddAccessory.CATALOG_IMAGE_FILE_SIZE_1000));
		// Delete product
		home.click(AddAccessory.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on inage thumbnail
	 */
	@Test
	public void TC051DAE_22(){
		result.addLog("ID : 051DAE_22 : Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on inage thumbnail");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Upload image for each size of Primary Catalog Image successfully
			5. Click on image thumbnail of each size
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Upload image for each size of Primary Catalog Image successfully
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_250, Constant.IMG_NAME[0]);
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_500, Constant.IMG_NAME[1]);
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_1000, Constant.IMG_NAME[2]);
		// 5. Click on image thumbnail 250
		home.click(AddAccessory.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250);
		home.waitForElementClickable(AddAccessory.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(AddAccessory.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddAccessory.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[0].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(AddAccessory.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(AddAccessory.LIGHTBOX_STYLE_IMAGE);
		// Click on image thumbnail 500
		home.click(AddAccessory.CATALOG_IMAGE_UPLOAD_THUMBNAIL_500);
		home.waitForElementClickable(AddAccessory.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(AddAccessory.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddAccessory.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[1].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(AddAccessory.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(AddAccessory.LIGHTBOX_STYLE_IMAGE);
		// Click on image thumbnail 500
		home.click(AddAccessory.CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000);
		home.waitForElementClickable(AddAccessory.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(AddAccessory.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddAccessory.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[2].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(AddAccessory.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(AddAccessory.LIGHTBOX_STYLE_IMAGE);
	}
	
	/*
	 * Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on file name
	 */
	@Test
	public void TC051DAE_23(){
		result.addLog("ID : 051DAE_23 : Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on file name");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Upload image for each size of Primary Catalog Image successfully
			5. Click on file name of each size
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Upload image for each size of Primary Catalog Image successfully
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_250, Constant.IMG_NAME[0]);
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_500, Constant.IMG_NAME[1]);
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_1000, Constant.IMG_NAME[2]);
		// 5. Click on file name 250
		home.click(AddAccessory.CATALOG_IMAGE_TITLE_LINK_250);
		home.waitForElementClickable(AddAccessory.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(AddAccessory.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddAccessory.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[0].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(AddAccessory.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(AddAccessory.LIGHTBOX_STYLE_IMAGE);
		// Click on file name 500
		home.click(AddAccessory.CATALOG_IMAGE_TITLE_LINK_500);
		home.waitForElementClickable(AddAccessory.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(AddAccessory.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddAccessory.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[1].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(AddAccessory.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(AddAccessory.LIGHTBOX_STYLE_IMAGE);
		// Click on file name 1000
		home.click(AddAccessory.CATALOG_IMAGE_TITLE_LINK_1000);
		home.waitForElementClickable(AddAccessory.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(AddAccessory.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddAccessory.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[2].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(AddAccessory.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(AddAccessory.LIGHTBOX_STYLE_IMAGE);
	}
	
	/*
	 * Verify that three size of Primary Catalog Image could be replaced by another successfully
	 */
	@Test
	public void TC051DAE_24(){
		result.addLog("ID : 051DAE_24 : Verify that three size of Primary Catalog Image could be replaced by another successfully");
		/*
			Pre-condition: User has "Add and manage accessories" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Upload image for each size of Primary Catalog Image successfully
			5. Delete the uploaded image by clicking on trashcan icon
			6. Upload image again
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
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
		Assert.assertTrue(home.isImageUploaded(AddAccessory.IMAGE250_DISPLAY, AddAccessory.IMG_NAME[0]));
		Assert.assertTrue(home.isImageUploaded(AddAccessory.IMAGE500_DISPLAY, AddAccessory.IMG_NAME[1]));
		Assert.assertTrue(home.isImageUploaded(AddAccessory.IMAGE1000_DISPLAY, AddAccessory.IMG_NAME[2]));
	}
	
	/*
	 * Verify that the marketing material file could be uploaded successfully by dragging and dropping
	 */
	@Test
	public void TC051DAE_25(){
		result.addLog("ID : 051DAE_25 : Verify that the marketing material file could be uploaded successfully by drapping and dropping");
		/*
			Pre-condition: User has "Add and manage accessories" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Drag and drop a file into Marketing Material area.
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Drag and drop a file into Marketing Material area.
		home.dragDropFile(Constant.AUDIO_ROUTE_FILE);
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(home.isMarketingUploaded(AddAccessory.MARKETING_FILE_CONTAINER, Constant.AUDIO_ROUTE_FILE));
	}
	
	/*
	 * Verify that the marketing material file could be uploaded successfully via 'Select File' button
	 */
	@Test
	public void TC051DAE_26(){
		result.addLog("ID : 051DAE_26 : Verify that the marketing material file could be uploaded successfully via 'Select File' button");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Click "Select File" in Marketing Material section
			5. Select a file to upload
		*/	
		// 2.Navigate to Accessory page
		home.click(Xpath.linkAccessories);
		// 3.Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Click "Select File" in Marketing Material section
		// 5. Select a file to upload
		home.uploadFile(AddAccessory.ADD_MARKETING, Constant.AUDIO_ROUTE_FILE);
		/*
		 * Verify that the marketing material file could be uploaded successfully via 'Select File' button
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddAccessory.MARKETING_UPLOAD_MESSAGE).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[0]));
	}
	
	/*
	 * Verify that the Input Specifications section inludes "Connection Type" and "Support Input Channels" module.
	 */
	@Test
	public void TC051DAE_27(){
		result.addLog("ID : 051DAE_27 : Verify that the Input Specifications section inludes 'Connection Type' and 'Support Input Channels' module.");
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		*/
		
		// 2.Navigate to Accessory page
		home.click(Xpath.linkAccessories);
		// 3.Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
			
		/*
		 Verify that the Input Specifications section inludes "Connection Type" and "Support Input Channels" module.
		 */
		Assert.assertTrue(home.isElementPresent(AddAccessory.WIRED_CHECKBOX));
		Assert.assertTrue(home.isElementPresent(AddAccessory.WIRED_DROPBOX));
		Assert.assertTrue(home.isElementPresent(AddAccessory.BLUETOOTH_CHECKBOX));
		Assert.assertTrue(home.isElementPresent(AddAccessory.BLUETOOTH_DROPBOX));
	}
	
	/*
	 * Verify that at least one connection type is required when creating an product
	 */
	@Test
	public void TC051DAE_28(){
		result.addLog("ID : 051DAE_28 : Verify that at least one connection type is required when creating an product");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Fill valid value into all required fields but do not check for any connection type
			5. Click "Save" link
		*/
		
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields but do not check for any connection type
		// 5. Click "Save" link
		Hashtable<String,String> data = TestData.accessoryDraft();
		data.remove("wired");
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * An error message displays which notifying user to select at least one connection type
		 */
		Assert.assertTrue(home.checkMessageDisplay("Connection Type is required"));
	}
	
	/*
	 * Verify that at least one Supported Input Channels count is required when creating an accessory.
	 */
	@Test
	public void TC051DAE_29(){
		result.addLog("ID : 051DAE_29 : Verify that at least one Supported Input Channels count is required when creating an accessory.");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Fill valid value into all required fields but do not select any supported input channels value
			5. Click "Save" link
		*/
		// 2.Navigate to Accessory page
		home.click(Xpath.linkAccessories);
		// 3.Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields but do not check for any connection type
		Hashtable<String, String> data = TestData.accessoryDraft();
		data.remove("wired");
		data.remove("save");
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// Select both wire and bluetooth connection type but leave the content channel capability empty
		// Leave the content channel empty
		home.selectInputChannel(AddAccessory.WIRED_CONTAINER, "none");
		home.selectInputChannel(AddAccessory.BLUETOOTH_CONTAINER, "none");
		// 5. Click "Save" link
		home.click(AddAccessory.SAVE);
		/*
		 * Verify that at least one Supported Input Channels count is required when creating an accessory.
		 */
		Assert.assertEquals(home.getTextByXpath(AddAccessory.WIRED_MESSAGE), AddAccessory.INPUT_CHANNEL_ERROR_STRING);
		Assert.assertEquals(home.getTextByXpath(AddAccessory.BLUETOOTH_MESSAGE), AddAccessory.INPUT_CHANNEL_ERROR_STRING);
	}
	
	/*
	 * Verify that the Supported Input Channels combobox is enabled when the Connection Type is checked.
	 */
	@Test
	public void TC051DAE_30(){
		result.addLog("ID : 051DAE_30 : Verify that the Supported Input Channels combobox is enabled when the Connection Type is checked."
				+ "Pre-condition: User has 'Add and manage accessories' rights." );
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			VP: Verify that the 051D Accessory Edit page is displayed
			4. Tick to check for a Connection Type
		*/
		// 2.Navigate to Accessory page
		home.click(Xpath.linkAccessories);
		// 3.Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Tick to check for a Connection Type
		home.click(AddAccessory.WIRED_CHECKBOX);
		home.click(AddAccessory.BLUETOOTH_CHECKBOX);
		
		/*
		 Verify that at least one connection type is required when creating an accessory.
		 */
		Assert.assertFalse(home.getAtribute(AddAccessory.WIRED_DROPBOX, "class").contains("disabled"));
		Assert.assertFalse(home.getAtribute(AddAccessory.BLUETOOTH_DROPBOX, "class").contains("disabled"));
	}
	
	/*
	 * Verify that the Supported Input Channels combobox is disabled when the Connection Type is unchecked.
	 */
	@Test
	public void TC051DAE_31(){
		result.addLog("ID : 051DAE_31 : Verify that the Supported Input Channels combobox is disabled when the Connection Type is unchecked."
				+ "Pre-condition: User has 'Add and manage accessories' rights." );
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		VP: Verify that the 051D Accessory Edit page is displayed
		4. Uncheck a Connection Type
		*/
		
		// 2.Navigate to Accessory page
		home.click(Xpath.linkAccessories);
		// 3.Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		
		/*
		 Verify that the Supported Input Channels combobox is disabled when the Connection Type is unchecked.
		 */
		Assert.assertTrue(home.getAtribute(AddAccessory.WIRED_DROPBOX, "class").contains("disabled"));
		Assert.assertTrue(home.getAtribute(AddAccessory.BLUETOOTH_DROPBOX, "class").contains("disabled"));
	}
	
	/*
	 Verify that the supported input channels combobox support multi-selecting items 
	 */
	@Test
	public void TC051DAE_32(){
		result.addLog("ID : 051DAE_32 : Verify that the supported input channels combobox support multi-selecting items"
				+ "Pre-condition: User has 'Add and manage accessories' rights." );
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		VP: Verify that the 051D Accessory Edit page is displayed
		4. Select items in supported input channels combobox
		*/
		
		// 2.Navigate to Accessory page
		home.click(Xpath.linkAccessories);
		// 3.Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP: Verify that the 051D Accessory Edit page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddAccessory.TITLE));
		// 4. Select items in supported input channels combobox
		home.selectInputChannel(AddAccessory.WIRED_CONTAINER, "1 (Single Channel)");
		home.selectInputChannel(AddAccessory.WIRED_CONTAINER, "2 (Stereo)");
		/*
		 Verify that the supported input channels combobox support multi-selecting items 
		 */
		Assert.assertTrue(home.getAtribute(AddAccessory.MONO_CHANNEL, "class").contains("active"));
		Assert.assertTrue(home.getAtribute(AddAccessory.STEREO_CHANNEL, "class").contains("active"));
	}
	
	
	/*
	 * Verify that the Headphone:X Tuning Rating combobox contains "Undetermined(default)", "No Certification", "A" and "A+" items
	 */
	@Test
	public void TC051DAE_33(){
		result.addLog("ID : 051DAE_33 : Verify that the Headphone:X Tuning Rating combobox contains 'Undetermined(default)'', 'No Certification', 'A' and 'A+' items");
		/*
			Pre-condition: User has "Add and manage products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "products" page
			3. Click "Add product" link
			VP: Verify that the 051D product Edit page is displayed
			4. List out the Headphone:X Tuning Rating combobox.
		*/
		
		// 2.Navigate to Accessory page
		home.click(Xpath.linkAccessories);
		// 3.Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP: Verify that the 051D product Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(), "Pass");
		// 4. List out the Headphone:X Tuning Rating combobox
		ArrayList<String> options = home.getAllComboboxOption(AddAccessory.TUNING_RATING);
		/*
		 * Verify that Headphone:X Tuning Rating combobox contains "Undetermined(default)", "No Certification", "A" and "A+" items
		 */
		Assert.assertTrue(DTSUtil.containsAll(options, AddAccessory.tuningRatingOption));
	}
	
	/*
	 * Verify that the Headphone:X Inside combobox contains "Not Applicable (N/A)" and "Embedded in Hardware" items
	 */
	@Test
	public void TC051DAE_34(){
		result.addLog("ID : 051DAE_34 : Verify that the Headphone:X Inside combobox contains 'Not Applicable (N/A)' and 'Embedded in Hardware' items");
		/*
			Pre-condition: User has "Add and manage products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "products" page
			3. Click "Add product" link
			VP: Verify that the 051D product Edit page is displayed
			4. List out theHeadphone:X Inside combobox
		*/
		
		// 2. Navigate to "products" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * VP: Verify that the 051D product Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddAccessory.getElementInfo()).getResult(), "Pass");
		// 4. List out theHeadphone:X Inside combobox
		ArrayList<String> options = home.getAllComboboxOption(AddAccessory.HEADPHONEX_INSIDE);
		/*
		 * Headphone:X Inside combobox contains "Not Applicable (N/A)" and "Embedded in Hardware" items
		 */
		Assert.assertTrue(DTSUtil.containsAll(options, AddAccessory.headphoneInsideOption));
	}
	
	/*
	 * Verify that the Adding New Model Variant shows up all necessary fields properly
	 */
	
	@Test
	public void TC051DAE_35(){
		result.addLog("ID : 051DAE_35 : Verify that the Adding New Model Variant shows up all necessary fields properly");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);  
		/*
		 * Verify that 052 Model Variant Edit page is showed up
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");		
	}
	
	/*
	 * Verify that the DTS users who have "Approve product tunings" privilege could edit the Headphone: X Tuning Rating  for the product variant
	 */
	@Test
	public void TC051DAE_36(){
		result.addLog("ID : 051DAE_36 : Verify that the DTS users who have 'Approve product tunings' privilege could edit the Headphone: X Tuning Rating  for the product variant");
		/*
			Pre-condition: User has "Approve product tuning" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "products" page
			3. Select an product from products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant " link from "Model Actions" module
		 */
		// 2. Navigate to "products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from products table
		home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 *  Verify that Headphone: X Tuning Rating is displayed as dropdown list and user can set their items successfully
		 */
		Assert.assertTrue(home.isElementEditable(AddVariant.TUNING_RATING));
	}
	
	/*
	 * Verify that the Tuning section is displayed with "Use Parent's Tuning" checkbox and "Upload Custom Tuning" link
	 */
	@Test
	public void TC051DAE_37(){
		result.addLog("ID : 051DAE_37 : Verify that the Tuning section is displayed with 'Use Parent's Tuning' checkbox and 'Upload Custom Tuning' link");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
		*/
		/*
		 * PreCondition: Create new product
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * ******************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);  
		/*
		 * Verify that 052 Model Variant Edit page is showed up and Tuning
		 * section is displayed with "Use Parent's Tuning" checkbox and
		 * "Upload Custom Tuning" link
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.PARENT_TUNNING_CHECKBOX));	
		Assert.assertTrue(home.isElementPresent(AddVariant.UPLOAD_CUSTOM_TUNNING));
		// Delete product
		home.click(AddVariant.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the "Add Tuning File" item is displayed with "drag and drop" area and "Select File" button when clicking on "Upload Custom Tuning" link
	 */
	@Test
	public void TC051DAE_38(){
		result.addLog("ID : 051DAE_38 : Verify that the 'Add Tuning File' item is displayed with 'drag and drop' area and 'Select File' button when clicking on 'Upload Custom Tuning' link");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			6. Click "Upload Custom Tuning" link
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		String productName = home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), productName);
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		// 6. Click "Upload Custom Tuning" link
		home.click(AddVariant.UPLOAD_CUSTOM_TUNNING);
		/*
		 * The  "Add Tuning File" item is displayed with "drag and drop" area and "Select File" button
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.ADD_TUNNING));
		Assert.assertTrue(home.isElementPresent(AddVariant.TUNING_DRAG_DROP_AREA));
	}
	
	/*
	 * Verify that user can upload tuning file by dragging and dropping file into "Add Tuning File" area
	 */
	@Test
	public void TC051DAE_39(){
		result.addLog("ID : 051DAE_39 : Verify that user can upload tuning file by dragging and dropping file into 'Add Tuning File' area");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			6. Click "Upload Custom Tuning" link
			VP. Verify that the "Add Tuning File" is displayed.
			7. Drag and Drop a tuning file into Add Tuning File area
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		String productName = home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), productName);
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		// 6. Click "Upload Custom Tuning" link
		home.click(AddVariant.UPLOAD_CUSTOM_TUNNING);
		/*
		 * VP. Verify that the "Add Tuning File" is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.ADD_TUNNING));
		Assert.assertTrue(home.isElementPresent(AddVariant.TUNING_DRAG_DROP_AREA));
		// 7. Drag and Drop a tuning file into Add Tuning File area
		home.dragDropFile(Constant.tuning_hpxtt);
		/*
		 * Verify that The tuning file is upload successfully
		 */
		Assert.assertTrue(home.isTunningUploaded(AddVariant.UPLOADED_TUNING, Constant.tuning_hpxtt));
	}
	
	/*
	 * Verify that user can upload tuning file via "Select File" button of "Add Tuning File" area successfully
	 */
	@Test
	public void TC051DAE_40(){
		result.addLog("ID : 051DAE_40 : Verify that user can upload tuning file via 'Select File' button of 'Add Tuning File' area successfully" );
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			6. Click "Upload Custom Tuning" link
			VP. Verify that the "Add Tuning File" is displayed.
			7. Click "Select File" button
			8. Upload a valid tuning file via dialog box
		*/
		/*
		 * PreCondition: Create new product
		 */
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * ******************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnaccessorybyName(data.get("name"));
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);  
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Click "Upload Custom Tuning" link
		home.click(AddVariant.UPLOAD_CUSTOM_TUNNING);
		/*
		 * VP. Verify that the "Add Tuning File" is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.ADD_TUNNING));
		// 7. Click "Select File" button
		// 8. Upload a valid tuning file via dialog box
		home.uploadFile(AddVariant.ADD_TUNNING, Constant.tuning_hpxtt);
		/*
		 * Verify that The tuning file is upload successfully
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.UPLOADED_TUNING));
		// Delete product
		home.click(AddVariant.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the invalid tuning file could not be uploaded
	 */
	
	@Test
	public void TC051DAE_41(){
		result.addLog("ID : 051DAE_41 : Verify that the invalid tuning file could not be uploaded");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			6. Click "Upload Custom Tuning" link
			VP. Verify that the "Add Tuning File" item is displayed.
			7. Click "Select File" button
			8. Upload an invalid tuning file
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Click "Upload Custom Tuning" link
		home.click(AddVariant.UPLOAD_CUSTOM_TUNNING);
		/*
		 * VP. Verify that the "Add Tuning File" item is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.ADD_TUNNING));
		// 7. Click "Select File" button
		// 8. Upload an invalid tuning file
		home.uploadFile(AddVariant.ADD_TUNNING, Constant.tuning_invalid);
		/*
		 * Verify that An error message is displayed and the invalid tuning file is not added
		 */
		WebElement failedTuning = driver.findElement(By.xpath(AddVariant.UPLOADED_TUNING)).findElement(By.tagName("p"));
		Assert.assertEquals(home.getTextByXpath(failedTuning), "! Invalid file");
	}
	
	/*
	 * Verify that the valid tuning file could be uploaded successfully
	 */
	@Test
	public void TC051DAE_42(){
		result.addLog("ID : 051DAE_42 : Verify that the valid tuning file could be uploaded successfully");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			6. Click "Upload Custom Tuning" link
			VP. Verify that the "Add Tuning File" item is displayed.
			7. Click "Select File" button
			8. Upload a valid tuning file via dialog box
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Click "Upload Custom Tuning" link
		home.click(AddVariant.UPLOAD_CUSTOM_TUNNING);
		/*
		 * VP. Verify that the "Add Tuning File" item is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.ADD_TUNNING));
		// 7. Click "Select File" button
		// 8. Upload a valid tuning file via dialog box
		home.uploadFile(AddVariant.ADD_TUNNING, Constant.tuning_hpxtt);
		/*
		 * Verify that The tuning file is upload successfully
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.UPLOADED_TUNING));
	}
	
	/*
	 * Verify that the "Add Tuning File" item is hidden when user selects "Use Parent's Tuning"
	 */
	
	@Test
	public void TC051DAE_43(){
		result.addLog("ID : 051DAE_43 : Verify that the 'Add Tuning File' item is hidden when user selects 'Use Parent's Tuning'" );
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			6. Click "Upload Custom Tuning" link
			VP. Verify that the "Add Tuning File" item is displayed.
			7. Tick to check the "Use Parent's Tuning" checkbox.
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Click "Upload Custom Tuning" link
		home.click(AddVariant.UPLOAD_CUSTOM_TUNNING);
		/*
		 * VP. Verify that the "Add Tuning File" item is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.ADD_TUNNING));
		// 7. Tick to check the "Use Parent's Tuning" checkbox
		home.click(AddVariant.PARENT_TUNNING_CHECKBOX);
		/*
		 * Verify that The "Add Tuning File" item is hidden
		 */
		Assert.assertFalse(home.isElementPresent(AddVariant.ADD_TUNNING));
	}
	
	/*
	 * Verify that the appropriate confirmation dialog for discarding the custom tuning file is displayed when the "Use Parent's Tuning" is checked after the custom tuning file has been uploaded successfully
	 */
	@Test
	public void TC051DAE_44(){
		result.addLog("ID : 051DAE_44 : Verify that the appropriate confirmation dialog for discarding the custom tuning file is displayed when the 'Use Parent's Tuning' is checked after the custom tuning file has been uploaded successfully");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040P product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052P Model Variant Edit page is displayed.
			6. Click "Upload Custom Tuning" link
			7. Upload a valid tuning file
			VP: Verify that the custom tuning file is uploaded successfully.
			8. Click on "Use Parent's Tuning" link
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Click "Upload Custom Tuning" link
		home.click(AddVariant.UPLOAD_CUSTOM_TUNNING);
		// 7. Upload a valid tuning file
		home.uploadFile(AddVariant.ADD_TUNNING, Constant.tuning_hpxtt);
		/*
		 * VP: Verify that the custom tuning file is uploaded successfully
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.UPLOADED_TUNING));
		// 8. Click on "Use Parent's Tuning" link
		home.click(AddVariant.PARENT_TUNNING_CHECKBOX);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		/*
		 * Verify that The Delete Tuning confirmation dialog is displayed with Delete and Cancel button
		 */
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_CANCEL), "Cancel");
	}
	
	/*
	 * Verify that when a DTS user upload tuning file, the upload tuning link is called "DTS Tuning"
	 */
	@Test
	public void TC051DAE_45(){
		result.addLog("ID : 051DAE_45 : Verify that when a DTS user upload tuning file, the upload tuning link is called 'DTS Tuning'");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			6. Fill valid value into all required fields
			7. Upload a valid tuning file successfully
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Fill valid value into all required fields
		// 7. Upload a valid tuning file successfully
		home.click(AddVariant.UPLOAD_CUSTOM_TUNNING);
		home.uploadFile(AddVariant.ADD_TUNNING, Constant.tuning_hpxtt);
		/*
		 * Verify that The Tuning section is displayed with new uploaded tuning file and it is called "DTS Tuning"
		 */
		WebElement uploadedTuning = driver.findElement(By.xpath(AddVariant.UPLOADED_TUNING)).findElement(By.className("mauploadTuning"));
		Assert.assertEquals(home.getTextByXpath(uploadedTuning), "DTS Tuning");
	}
	
	/*
	 * Verify that the EAN/UPC is validated for the already exist EAN/UPC code
	 */
	@Test
	public void TC051DAE_46(){
		result.addLog("ID : 051DAE_46 : Verify that the EAN/UPC is validated for the already exist EAN/UPC code");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			6. Fill value into EAN/UPC text field which is saved for another product.
			7. Click "Save" link
		*/
		/*
		 * PreCondition: Create new product which has at lease on variant
		 */
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product page
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> dataProduct = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), dataProduct);
		// Click Add new variant link
		home.click(AccessoryInfo.ADD_VARIANT);
		// Create variant
		Hashtable<String,String> dataVariant = TestData.variantDraft();
		home.addVariant(AddVariant.getHash(), dataVariant);
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnaccessorybyName(dataProduct.get("name"));
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Fill value into EAN/UPC text field which is saved for another product
		// 7. Click "Save" link
		dataVariant.put("name", "NewTest" + RandomStringUtils.randomNumeric(6));
		home.addVariant(AddVariant.getHash(), dataVariant);
		/*
		 * Verify that A warning message "Warning: A model already exist with this EAN/UPC code"
		 */
		Assert.assertTrue(home.checkMessageDisplay("EAN must be unique"));
		Assert.assertTrue(home.checkMessageDisplay("UPC must be unique"));
		// Delete product
		home.click(AddVariant.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the Model Name with (Default) English is required when adding new product
	 */
	@Test
	public void TC051DAE_47(){
		result.addLog("ID : 051DAE_47: Verify that the Model Name with (Default) English is required when adding new product");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			6. Fill valid value into all required fields but leaving Model Name empty
			7. Click "Save" link
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Fill valid value into all required fields but leaving Model Name empty
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.variantDraft();
		data.remove("name");
		home.addVariant(AddVariant.getHash(), data);
		/*
		 * Verify that A warning message is displayed which notifying the model name is empty
		 */
		Assert.assertTrue(home.checkMessageDisplay("Model name is required."));
	}
	
	/*
	 * Verify that the "-Select-" item is the default value of Model Name language dropdown
	 */
	@Test
	public void TC051DAE_48(){
		result.addLog("ID : 051DAE_48: Verify that the '-Select-' item is the default value of Model Name language dropdown");
		/*
			PreCondition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * Verify that the 052D Model Variant Edit page is displayed and the "- Select -" item is the default value of Model Name language dropdown
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getItemSelected(AddVariant.LANGUAGE_DROPDOWN), "- Select -");
	}
	
	/*
	 * Verify that the Model Name language dropdown contains 10 items properly
	 */
	@Test
	public void TC051DAE_49(){
		result.addLog("ID : 051DAE_49: Verify that the Model Name language dropdown contains 10 items properly");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP: Verify that the 052D Model Variant Edit page is displayed
			6. List out the Model Name language dropdown
		*/
		
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP: Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. List out the Model Name language dropdown
		ArrayList<String> allOptions = home.getAllOption(AddVariant.LANGUAGE_DROPDOWN);
		/*
		 Verify that the Model Name language dropdown contains 10 items properly
		 */
		Assert.assertTrue(DTSUtil.containsAll(allOptions, AddVariant.LANGUAGE_LIST));
	}
	
	/*
	 * Verify that the hint text "Inherits default" is displayed for an empty fields of each model name language
	 */
	
	@Test
	public void TC051DAE_50(){
		result.addLog("ID : 051DAE_50: Verify that the hint text 'Inherits default' is displayed for an empty fields of each model name language");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP: Verify that the 052D Model Variant Edit page is displayed
			6. Select another language for Model Name
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP: Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Select another language for Model Name
		home.selectOptionByName(AddVariant.LANGUAGE_DROPDOWN, AddVariant.LANGUAGE_LIST[2]);
		// Get all language packages
		List<LanguagePackage> languagePackages = home.getLanguagePackage(AddVariant.LANGUAGE_CONTAINER);
		/*
		 * Verify that The hint text "Inherits default" is displayed in model name text field
		 */
		Assert.assertEquals(home.getAtribute(languagePackages.get(0).name, "placeholder"), "inherits default");
	}
	
	/*
	 * Verify that the external language model name text field is disabled when the language dropdown is "Select"
	 */
	@Test
	public void TC051DAE_51(){
		result.addLog("ID : 051DAE_51: Verify that the external language model name text field is disabled when the language dropdown is 'Select'");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP: Verify that the 052D Model Variant Edit page is displayed
			6. Set language dropdown to another language
			7. Set language dropdown to "Select"
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP: Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Select another language for Model Name
		// 7. Set language dropdown to "Select"
		home.selectOptionByName(AddVariant.LANGUAGE_DROPDOWN, AddVariant.LANGUAGE_LIST[0]);
		/*
		 * Verify that The model name language text field is disabled	
		 */
		Assert.assertFalse(home.isElementEditable(AddVariant.LANGUAGE_NAME_TEXT));
	}
	
	/*
	 * Verify that another language dropdown will display when selecting a model name language
	 */
	@Test
	public void TC051DAE_52(){
		result.addLog("ID : TC051DAE_52 : Verify that another language dropdown will display when selecting a model name language");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP: Verify that the 052D Model Variant Edit page is displayed
			6. Set the language dropdown to another language
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP: Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Set the language dropdown to another language
		home.selectOptionByName(AddVariant.LANGUAGE_DROPDOWN, AddVariant.LANGUAGE_LIST[3]);
		/*
		 * Verify that Another language dropdown is displayed below
		 */
		int languageContainers = home.getLanguageContainerIndex(AddVariant.LANGUAGE_CONTAINER);
		Assert.assertTrue(languageContainers > 1);
	}
	
	/*
	 * Verify that a warning message is displayed for duplicating language dropdowns
	 */
	@Test
	public void TC051DAE_53(){
		result.addLog("ID : TC051DAE_53 : Verify that a warning message is displayed for duplicating language dropdowns");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP: Verify that the 052D Model Variant Edit page is displayed
			6. Select language dropdown to another item other than "Select"
			VP: Verify that new language dropdown is displayed.
			7. Select the same item for second language dropdown
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP: Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Set the language dropdown to another language
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
	}
	
	/*
	 * Verify that the language dropdown is restore to default "Select" value and the input field is cleared when selecting the Trashcan icon
	 */
	@Test
	public void TC051DAE_54(){
		result.addLog("ID : TC051DAE_54 : Verify that the language dropdown is restore to default 'Select' value and the input field is cleared when selecting the Trashcan icon");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP: Verify that the 052D Model Variant Edit page is displayed
			6. Select language dropdown to another item other than "Select"
			7. Type value into model name input field
			8. Click on Trashcan icon
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP: Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Select language dropdown to another item other than "Select"
		home.selectOptionByName(AddVariant.LANGUAGE_DROPDOWN, AddVariant.LANGUAGE_LIST[3]);
		// Get language packages
		List<LanguagePackage> languagePakages = home.getLanguagePackage(AddVariant.LANGUAGE_CONTAINER);
		// 7. Type value into model name input field
		home.editData(languagePakages.get(0).name, "Just a test");
		// 8. Click on Trashcan icon
		home.click(languagePakages.get(0).trash);
		/*
		 * Verify that The language dropdown is restore to default "Select" value and the input field is cleared
		 */
		Assert.assertEquals(home.getItemSelected("//*[@id='lang-div-container']/div[1]/select"), "- Select -");
		Assert.assertEquals(home.getTextByXpath(languagePakages.get(0).name), "");
	}
	
	/*
	 * Verify that the Input Specifications section inludes "Connection Type" and "Support Input Channels" module but they are disabled
	 */
	@Test
	public void TC051DAE_55(){
		result.addLog("ID : TC051DAE_55 : Verify that the Input Specifications section inludes 'Connection Type' and 'Support Input Channels' module but they are disabled");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		String productName = home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), productName);
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 *  Verify that the 052D Model Variant Edit page is displayed and the Input Specifications section inludes "Connection Type" and "Support Input Channels" module but they are disabled
		 */
		Assert.assertFalse(home.isElementEditable(AddVariant.INPUT_SPECIFICATIONS));
	}
	
	/*
	 * Verify that the Primary Catalog Image section is displayed with "Use Parent's Images" checkbox and "Upload Custom Images" link 
	 */
	@Test
	public void TC051DAE_61(){
		result.addLog("ID : 051DAE_61 : Verify that the Primary Catalog Image section is displayed with 'Use Parent's Images' checkbox and 'Upload Custom Images' link");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
		*/
		// 2.Navigate to Accessory page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * 052 Model Variant Edit page is showed up and Primary Catalog Image section is displayed with "Use Parent's Images" checkbox and "Upload Custom Images" link
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.UPLOAD_CUSTOM_IMAGE));
		Assert.assertTrue(home.isElementPresent(AddVariant.PARENT_IMAGE_CHECKBOX));
	}
	
	/*
	 * Verify that the "Add Custom Image" item is displayed with "drag and drop" area and "Select File" button when clicking on "Upload Custom Images" link
	 */
	@Test
	public void TC051DAE_62(){
		result.addLog("ID : 051DAE_62 : Verify that the 'Add Custom Image' item is displayed with 'drag and drop' area and 'Select File' button when clicking on 'Upload Custom Images' link");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			6. Click "Upload Custom Images" link
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		String productName = home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), productName);
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		// 6. Click "Upload Custom Images" link
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		/*
		 * Verify that The "Add Custom Image" item is displayed with "drag and drop" area and "Select File" button
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_250));
		Assert.assertTrue(home.isElementPresent(AddVariant.IMAGE250_DRAG_DROP_AREA));
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_500));
		Assert.assertTrue(home.isElementPresent(AddVariant.IMAGE500_DRAG_DROP_AREA));
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_1000));
		Assert.assertTrue(home.isElementPresent(AddVariant.IMAGE1000_DRAG_DROP_AREA));
	}
	
	/*
	 * Verify that user can upload primary images file by dragging and dropping file into "Add Custom Image File" area
	 */
	@Test
	public void TC051DAE_63(){
		result.addLog("ID : 051DAE_63 : Verify that user can upload primary images file by dragging and dropping file into 'Add Custom Image File' area");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			6. Click "Upload Custom Images" link
			VP. Verify that the "Add Custom Image File" is displayed.
			7. Drag and Drop a tuning file into Add Custom Image File area
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		String productName = home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), productName);
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		// 6. Click "Upload Custom Images" link
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		/*
		 * VP. Verify that the "Add Custom Image File" is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_250));
		Assert.assertTrue(home.isElementPresent(AddVariant.IMAGE250_DRAG_DROP_AREA));
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_500));
		Assert.assertTrue(home.isElementPresent(AddVariant.IMAGE500_DRAG_DROP_AREA));
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_1000));
		Assert.assertTrue(home.isElementPresent(AddVariant.IMAGE1000_DRAG_DROP_AREA));
		// 7. Drag and Drop a tuning file into Add Custom Image File area
		home.dragDropFile(Constant.IMG_NAME[0]);
		/*
		 * Verify that The image file is upload successfully
		 */
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddVariant.UPLOAD_FILE_MESSSAGE[0]));
	}
	
	/*
	 * Verify that user can upload primary image file via "Select File" button of "Add Custom Image File" area successfully
	 */
	@Test
	public void TC051DAE_64(){
		result.addLog("ID : 051DAE_64 : Verify that user can upload primary image file via 'Select File' button of 'Add Custom Image File' area successfully");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			6. Click "Upload Custom Images" link
			VP. Verify that the "Add Custom Image File" item is displayed.
			7. Click "Select File" button
			8. Upload an image file via dialog box
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		String productName = home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), productName);
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		// 6. Click "Upload Custom Images" link
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		/*
		 * VP. Verify that the "Add Custom Image File" is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_250));
		Assert.assertTrue(home.isElementPresent(AddVariant.IMAGE250_DRAG_DROP_AREA));
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_500));
		Assert.assertTrue(home.isElementPresent(AddVariant.IMAGE500_DRAG_DROP_AREA));
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_1000));
		Assert.assertTrue(home.isElementPresent(AddVariant.IMAGE1000_DRAG_DROP_AREA));
		// 7. Click "Select File" button
		// 8. Upload an image file via dialog box
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_250, Constant.IMG_NAME[0]);
		/*
		 * Verify that The image is upload successfully
		 */
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddVariant.UPLOAD_FILE_MESSSAGE[0]));
	}
	
	/*
	 * Verify that the "Add Custom Image" item is hidden when user selects "Use Parent's Images"
	 */
	@Test
	public void TC051DAE_65(){
		result.addLog("ID : 051DAE_65 : Verify that the 'Add Custom Image' item is hidden when user selects 'Use Parent's Images'");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			6. Click "Upload Custom Images" link
			VP. Verify that the "Add Tuning File" item is displayed.
			7. Click on the "Use Parent's Images" link
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Click "Upload Custom Images" link
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		/*
		 *  VP. Verify that the "Add Tuning File" item is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_250));
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_500));
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_1000));
		// 7. Click on the "Use Parent's Images" link
		home.click(AddVariant.PARENT_IMAGE_CHECKBOX);
		/*
		 * Verify that The "Add Custom Images" item is hidden
		 */
		Assert.assertFalse(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_250));
		Assert.assertFalse(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_500));
		Assert.assertFalse(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_1000));
	}
	
	/*
	 * Verify that the appropriate confirmation dialog for discarding the custom image is displayed when the "Use Parent's Images" is checked after the custom image file has been uploaded successfully
	 */
	@Test
	public void TC051DAE_66(){
		result.addLog("ID : 051DAE_66 : Verify that the appropriate   confirmation dialog for discarding the custom image is displayed when the 'Use Parent's Images' is checked after the custom image file has been uploaded successfully");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			6. Click "Upload Custom Images" link
			VP. Verify that the "Add Custom Image File" is displayed.
			7. Upload an image file
			VP: Verify that the image is uploaded successfully.
			8. Click on the "Use Parent's Images" link
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Click "Upload Custom Images" link
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		/*
		 *  VP. Verify that the "Add Custom Image File" is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_250));
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_500));
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_1000));
		// 7. Upload an image file
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_250, AddVariant.IMG_NAME[0]);
		/*
		 * VP: Verify that the image is uploaded successfully
		 */
		Assert.assertTrue(home.isImageUploaded(AddVariant.IMAGE250_DISPLAY, AddVariant.IMG_NAME[0]));
		// 8. Click on the "Use Parent's Images" link
		home.click(AddVariant.PARENT_IMAGE_CHECKBOX);
		home.waitForElementClickable(Xpath.BTN_CONFIRMATION_DANGER);
		/*
		 * Verify that The appropriate confirmation dialog for discarding the custom image  is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(home.getTextByXpath(Xpath.BTN_CONFIRMATION_CANCEL), "Cancel");
	}
	
	/*
	 Verify that the three size of Primary Catalog Image display in an order list
	 */
	@Test
	public void TC051DAE_67(){
		result.addLog("ID : 051DAE_67 : Verify that the three size of Primary Catalog Image display in an order list"
				+ "Pre-condition: User has 'Add and manage accessories' rights." );
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Accessories" page
		3. Select an accessory from accessories table
		4. Verify that the 040D Accessory Detail page is displayed
		5. Click "Add New Variant" link from "Model Actions" module
		6. Click "Upload Custom Images" link
		7. Upload three size of image successfully
		*/
				
		// 2.Navigate to Accessory page
		home.click(Xpath.linkAccessories);
		// 3.Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		
		/*
		 Verify that the three size of Primary Catalog Image display in an order list
		 */
		home.isItemInorderList(AddVariant.IMAGETABLE,AddVariant.SIZE_LIST);
	}
	
	/*
	 * Verify that the image thumbnail is displayed correctly for each Primary Catalog Image
	 */
	@Test
	public void TC051DAE_68(){
		result.addLog("ID : 051DAE_68 : Verify that the image thumbnail is displayed correctly for each Primary Catalog Image");
		/*
			Pre-condition: User has "Add and manage products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "products" page
			3. Select an product from products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			6. Click "Upload Custom Images" link
			VP. Verify that the "Add Custom Image File" is displayed.
			7. Upload three size of image successfully
		*/
		// 2. Navigate to "products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from products table
		home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Click "Upload Custom Images" link
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		// 7. Upload three size of image successfully
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_250, Constant.IMG_NAME[0]);
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_500, Constant.IMG_NAME[1]);
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_1000, Constant.IMG_NAME[2]);
		/*
		 * Verify that The correct image thumbnail is displayed next to image name
		 */
		Assert.assertTrue(home.isImageUploaded(AddVariant.IMAGE250_DISPLAY, Constant.IMG_NAME[0]));
		Assert.assertTrue(home.isImageUploaded(AddVariant.IMAGE500_DISPLAY, Constant.IMG_NAME[1]));
		Assert.assertTrue(home.isImageUploaded(AddVariant.IMAGE1000_DISPLAY, Constant.IMG_NAME[2]));
	}
	
	/*
	 * Verify that the image file name, uploaded date and file size are displayed below the image name for each size of Primary Catalog Image
	 */
	@Test
	public void TC051DAE_69(){
		result.addLog("ID : 051DAE_69 : Verify that the image file name, uploaded date and file size are displayed below the image name for each size of Primary Catalog Image");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			6. Click "Upload Custom Images" link
			VP. Verify that the "Add Custom Image File" is displayed.
			7. Upload three size of image successfully
			VP: Verify that the image file name and  file size is displayed below the catalog image file name link
			8. Click "Save" link
			VP: Verify that new variant is added successfully
			9. Click "Edit" link
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		String producName = home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), producName);
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		// 6. Click "Upload Custom Images" link
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		// 7. Upload three size of image successfully
		Hashtable<String,String> data = TestData.variantImage();
		data.remove("save");
		home.addVariant(AddVariant.getHash(), data);
		/*
		 * VP: Verify that the image file name and  file size is displayed below the catalog image file name link
		 */
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_INFO_250).contains(Constant.IMG_NAME[0]));
		Assert.assertTrue(home.isElementDisplayVertically(AddVariant.CATALOG_IMAGE_TITLE_LINK_250, AddVariant.CATALOG_IMAGE_FILE_SIZE_250));
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_INFO_500).contains(Constant.IMG_NAME[1]));
		Assert.assertTrue(home.isElementDisplayVertically(AddVariant.CATALOG_IMAGE_TITLE_LINK_500, AddVariant.CATALOG_IMAGE_FILE_SIZE_500));
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_INFO_1000).contains(Constant.IMG_NAME[2]));
		Assert.assertTrue(home.isElementDisplayVertically(AddVariant.CATALOG_IMAGE_TITLE_LINK_1000, AddVariant.CATALOG_IMAGE_FILE_SIZE_1000));
		// 8. Click "Save" link
		home.click(AddVariant.SAVE);
		/*
		 * VP: Verify that new variant is added successfully
		 */
		Assert.assertEquals(home.getTextByXpath(VariantInfo.TITLE_NAME), data.get("name"));
		// 9. Click "Edit" link
		home.click(VariantInfo.EDIT_VARIANT);
		// Get the current date
		String date = DateUtil.getADateGreaterThanToday("dd MMM yyyy", 0);
		/*
		 * Verify that The uploaded date and file size are displayed below the image name for each size of Primary Catalog Image
		 */
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_INFO_250).contains(date));
		Assert.assertTrue(home.isElementDisplayVertically(AddVariant.CATALOG_IMAGE_TITLE_LINK_250, AddVariant.CATALOG_IMAGE_FILE_SIZE_250));
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_INFO_500).contains(date));
		Assert.assertTrue(home.isElementDisplayVertically(AddVariant.CATALOG_IMAGE_TITLE_LINK_500, AddVariant.CATALOG_IMAGE_FILE_SIZE_500));
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_INFO_1000).contains(date));
		Assert.assertTrue(home.isElementDisplayVertically(AddVariant.CATALOG_IMAGE_TITLE_LINK_1000, AddVariant.CATALOG_IMAGE_FILE_SIZE_1000));
	}
	
	/*
	 * Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on inage thumbnail
	 */
	@Test
	public void TC051DAE_70(){
		result.addLog("ID : 051DAE_70 : Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on inage thumbnail");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			6. Click "Upload Custom Images" link
			VP. Verify that the "Add Custom Image File" is displayed.
			7. Upload three size of image successfully
			8. Click on image thumbnail of each size
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		String producName = home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), producName);
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		// 6. Click "Upload Custom Images" link
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		/*
		 * VP. Verify that the "Add Custom Image File" is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_250));
		Assert.assertTrue(home.isElementPresent(AddVariant.IMAGE250_DRAG_DROP_AREA));
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_500));
		Assert.assertTrue(home.isElementPresent(AddVariant.IMAGE500_DRAG_DROP_AREA));
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_1000));
		Assert.assertTrue(home.isElementPresent(AddVariant.IMAGE1000_DRAG_DROP_AREA));
		// 7. Upload three size of image successfully
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_250, Constant.IMG_NAME[0]);
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_500, Constant.IMG_NAME[1]);
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_1000, Constant.IMG_NAME[2]);
		// 8. Click on image thumbnail 250
		home.click(AddVariant.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250);
		home.waitForElementClickable(AddVariant.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(AddVariant.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddVariant.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[0].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(AddVariant.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(AddVariant.LIGHTBOX_STYLE_IMAGE);
		// Click on image thumbnail 500
		home.click(AddVariant.CATALOG_IMAGE_UPLOAD_THUMBNAIL_500);
		home.waitForElementClickable(AddVariant.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(AddVariant.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddVariant.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[1].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(AddVariant.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(AddVariant.LIGHTBOX_STYLE_IMAGE);
		// Click on image thumbnail 500
		home.click(AddVariant.CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000);
		home.waitForElementClickable(AddVariant.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(AddVariant.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddVariant.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[2].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(AddVariant.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(AddVariant.LIGHTBOX_STYLE_IMAGE);
	}
	
	/*
	 * Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on file name
	 */
	@Test
	public void TC051DAE_71(){
		result.addLog("ID : 051DAE_71 : Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on file name");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			6. Click "Upload Custom Images" link
			VP. Verify that the "Add Custom Image File" is displayed.
			7. Upload three size of image successfully
			5. Click on file name of each size
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		String producName = home.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TITLE_NAME), producName);
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.TITLE));
		// 6. Click "Upload Custom Images" link
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		/*
		 * VP. Verify that the "Add Custom Image File" is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_250));
		Assert.assertTrue(home.isElementPresent(AddVariant.IMAGE250_DRAG_DROP_AREA));
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_500));
		Assert.assertTrue(home.isElementPresent(AddVariant.IMAGE500_DRAG_DROP_AREA));
		Assert.assertTrue(home.isElementPresent(AddVariant.SELECT_FILE_IMAGE_1000));
		Assert.assertTrue(home.isElementPresent(AddVariant.IMAGE1000_DRAG_DROP_AREA));
		// 7. Upload three size of image successfully
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_250, Constant.IMG_NAME[0]);
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_500, Constant.IMG_NAME[1]);
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_1000, Constant.IMG_NAME[2]);
		// 8. Click on file name 250
		home.click(AddVariant.CATALOG_IMAGE_TITLE_LINK_250);
		home.waitForElementClickable(AddVariant.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(AddVariant.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddVariant.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[0].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(AddVariant.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(AddVariant.LIGHTBOX_STYLE_IMAGE);
		// Click on file name 500
		home.click(AddVariant.CATALOG_IMAGE_TITLE_LINK_500);
		home.waitForElementClickable(AddVariant.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(AddVariant.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddVariant.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[1].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(AddVariant.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(AddVariant.LIGHTBOX_STYLE_IMAGE);
		// Click on file name 1000
		home.click(AddVariant.CATALOG_IMAGE_TITLE_LINK_1000);
		home.waitForElementClickable(AddVariant.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(home.isElementPresent(AddVariant.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(home.getAtribute(AddVariant.LIGHTBOX_STYLE_IMAGE, "src").contains(Constant.IMG_NAME[2].replaceAll(".jpg", "")));
		// Close lightbox image
		home.click(AddVariant.LIGHTBOX_CLOSE);
		home.waitForElementDisappear(AddVariant.LIGHTBOX_STYLE_IMAGE);
	}
	
	/*
	 * Verify that three size of Primary Catalog Image could be replaced by another successfully
	 */
	
	@Test
	public void TC051DAE_72(){
		result.addLog("ID : 051DAE_72 : Verify that three size of Primary Catalog Image could be replaced by another successfully");
		/*
			Pre-condition: User has "Add and manage accessories" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
			4. Verify that the 040D Accessory Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			6. Click "Upload Custom Images" link
			VP. Verify that the "Add Custom Image File" is displayed.
			7. Upload three size of image successfully
			8. Delete the uploaded image by clicking on trashcan icon
			9. Upload another image
		*/	
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Verify that the 040D Accessory Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed.
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Click "Upload Custom Images" link
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		// 7. Upload three size of image successfully
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_250, AddVariant.IMG_NAME[0]);
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_500, AddVariant.IMG_NAME[1]);
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_1000, AddVariant.IMG_NAME[2]);
		// 8. Delete the uploaded image by clicking on trashcan icon
		home.deleteAllUploadedImage(AddVariant.IMAGE_CATALOG);
		// 9. Upload another image
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_250, AddVariant.IMG_NAME[0]);
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_500, AddVariant.IMG_NAME[1]);
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_1000, AddVariant.IMG_NAME[2]);
		/*
		 * Verify that New image could be upload successfully
		 */
		Assert.assertTrue(home.isImageUploaded(AddVariant.IMAGE250_DISPLAY, AddVariant.IMG_NAME[0]));
		Assert.assertTrue(home.isImageUploaded(AddVariant.IMAGE500_DISPLAY, AddVariant.IMG_NAME[1]));
		Assert.assertTrue(home.isImageUploaded(AddVariant.IMAGE1000_DISPLAY, AddVariant.IMG_NAME[2]));

	}
	
	/*
	 * Verify that the marketing material file could be uploaded successfully by drapping and dropping
	 */
	
	@Test
	public void TC051DAE_73(){
		result.addLog("ID : 051DAE_73 : Verify that the marketing material file could be uploaded successfully by drapping and dropping");
		/*
			Pre-condition: User has "Add and manage accessories" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Select an accessory from accessories table
			4. Verify that the 040D Accessory Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			6. Click "Upload Custom Images" link
			VP. Verify that the "Add Custom Image File" is displayed.
			7. Upload three size of image successfully
			8. Drap and drop a file into Marketing Material area
		*/	
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Select an accessory from accessories table
		home.selectAnAccessory();
		// 4. Verify that the 040D Accessory Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Drap and drop a file into Marketing Material area
		home.dragDropFile(Constant.AUDIO_ROUTE_FILE);
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(home.isMarketingUploaded(AddVariant.MARKETING_FILE_CONTAINER, Constant.AUDIO_ROUTE_FILE));
	}
	
	/*
	 * Verify that the marketing material file could be uploaded successfully via 'Select File' button"
	 */
	@Test
	public void TC051DAE_74(){
		result.addLog("ID : 051DAE_74 : Verify that the marketing material file could be uploaded successfully via 'Select File' button");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			6. Click "Select File" in Marketing Material section
			7. Select a file to upload
		*/	
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select an product from Products table
		home.selectAnAccessory();
		// 4. Verify that the 040D Accessory Detail page is displayed
		Assert.assertEquals(home.existsElement(AccessoryInfo.getElementsInfo()).getResult(), "Pass");
		// 5. Click "Add New Variant" link from "Model Actions" module
		home.click(AccessoryInfo.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(home.existsElement(AddVariant.getElementsInfo()).getResult(), "Pass");
		// 6. Click "Select File" in Marketing Material section
		// 7. Select a file to upload
		home.uploadFile(AddVariant.ADD_MARKETING, Constant.AUDIO_ROUTE_FILE);
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(home.isMarketingUploaded(AddVariant.MARKETING_FILE_CONTAINER, Constant.AUDIO_ROUTE_FILE));
	}
	
	/*
	 * 	Verify that the “Company” and “Brands” combo boxes are editable when adding new product
	 */
	
	@Test
	public void TC051DAE_75(){
		result.addLog("ID : 051DAE_75 : Verify that the “Company” and “Brands” combo boxes are editable when adding new product");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click “Add Product” link
		*/	
		
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click “Add Product” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * Verify that The “Companies” and “Brands” combo boxes are displayed and editabled
		 */
		Assert.assertTrue(home.isElementEditable(AddAccessory.COMPANY));
		Assert.assertTrue(home.isElementEditable(AddAccessory.BRAND));
	}
	
	/*
	 * 	Verify that the “Company” combobox is readonly and “Brands” combo is editable when editing existing products
	 */
	@Test
	public void TC051DAE_76(){
		result.addLog("ID : 051DAE_76: Verify that the “Company” combobox is readonly and “Brands” combo is editable when editing existing products");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select a product from products list
			4. Click “Edit” link
		*/	
		/*
		 * PreCondition: Create new product
		 */
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Click Add product link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create product
		Hashtable<String,String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * *************************************************************
		 */
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select a product from products list
		home.selectAnaccessorybyName(data.get("name"));
		// 4. Click “Edit” link
		home.click(AccessoryInfo.EDIT_MODE);
		/*
		 * Verify that The “Companies” combobox is disabled
		 */
		Assert.assertFalse(home.isElementEditable(AddAccessory.COMPANY));
		//Delete product
		home.click(AddAccessory.CANCEL);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the product's EAN/UPC values are validated correctly
	 */
	@Test
	public void TC051DAE_77(){
		result.addLog("ID : 051DAE_77: Verify that the product's EAN/UPC values are validated correctly");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click “Add Products” link
			4. Fill valid value into all required fields except EAN/UPC
			5. Fill value which including characters and digits into EAN/UPC field
			6. Click “Save” link
			VP: Verify that new product is unable to save due to the error message inline of EAN/UPC fields
			7. Enter a string larger than 13 digits into EAN field and 12 digits into UPC field
			8. Click “Save” link
			VP: Verify that new product is unable to save due to the error message inline of EAN/UPC fields
			9. Enter a string exactly 13 digits into EAN/UPC fields
			10. Click “Save”
			VP: Verify that new product is unable to save due to the error message inline of EAN field
		*/	
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click “Add Products” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields except EAN/UPC
		// Fill value which including characters and digits into EAN/UPC field
		// 6. Click “Save” link
		Hashtable<String,String> data = TestData.accessoryDraft();
		data.put("ean", RandomStringUtils.randomAlphanumeric(13));
		data.put("upc", RandomStringUtils.randomAlphanumeric(12));
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * Verify that new product is unable to save due to the error message inline of EAN field
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddAccessory.eanErrMessage[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddAccessory.upcErrMessage[0]));
		// 7. Enter a string larger than 13 digits into EAN field and 12 digits into UPC field
		home.editData(AddAccessory.EAN, RandomStringUtils.randomNumeric(15));
		home.editData(AddAccessory.UPC, RandomStringUtils.randomNumeric(15));
		
		// 8. Click “Save” link
		home.click(AddAccessory.SAVE);
		// VP: Verify that new product is unable to save due to the error message inline of EAN/UPC fields
		Assert.assertTrue(home.checkMessageDisplay(AddAccessory.eanErrMessage[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddAccessory.upcErrMessage[1]));
		//	9. Enter a string exactly 13 digits into EAN/UPC fields
		home.editData(AddAccessory.EAN, RandomStringUtils.randomNumeric(13));
		home.editData(AddAccessory.UPC, RandomStringUtils.randomNumeric(12));
		//	10. Click “Save”
		home.click(AddAccessory.SAVE);
		//	VP: Verify that new product is unable to save due to the error message inline of EAN field
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));;
		
		//Teardown:
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	
		
	/*
	 * Verify that the variant's EAN/UPC values are validated correctly
	 */
	@Test
	public void TC051DAE_81(){
		result.addLog("ID : 051DAE_81: Verify that the variant's EAN/UPC values are validated correctly");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select a product from products list
			4. Click "Add Variant" link
			5. Fill valid value into all required fields except EAN/UPC
			6. Fill value which including characters and digits into EAN/UPC field
			7. Click “Save” link
			VP: Verify that new product is unable to save due to the error message inline of EAN/UPC fields
			8. Enter a string larger than 13 digits into EAN field and 12 digits into UPC field
			9. Click “Save” link
			VP: Verify that new variant is unable to save due to the error message inline of EAN/UPC fields
			10. Enter a string exactly 13 digits into EAN/UPC fields
			11. Click “Save”
			VP: Verify that new variant is able to save successfully
		*/	
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select a product from products list
		home.selectAnAccessory();
		// 4. Click "Add Variant" link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 5. Fill value which including characters and digits into EAN/UPC field
		// 6. Fill value which including characters and digits into EAN/UPC field
		// 7. Click “Save” link
		Hashtable<String,String> data = TestData.variantDraft();
		data.put("ean", RandomStringUtils.randomAlphanumeric(13));
		data.put("upc", RandomStringUtils.randomAlphanumeric(12));
		home.addVariant(AddVariant.getHash(), data);
		/*
		 * Verify that new product is unable to save due to the error message inline of EAN field
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddVariant.eanErrMessage[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddVariant.upcErrMessage[0]));
		// 7. Enter a string larger than 13 digits into EAN field and 12 digits into UPC field
		home.editData(AddVariant.EAN, RandomStringUtils.randomNumeric(15));
		home.editData(AddVariant.UPC, RandomStringUtils.randomNumeric(15));
		
		// 8. Click “Save” link
		home.click(AddVariant.SAVE);
		// VP: Verify that new product is unable to save due to the error message inline of EAN/UPC fields
		Assert.assertTrue(home.checkMessageDisplay(AddVariant.eanErrMessage[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddVariant.upcErrMessage[1]));
		//	9. Enter a string exactly 13 digits into EAN/UPC fields
		home.editData(AddVariant.EAN, RandomStringUtils.randomNumeric(13));
		home.editData(AddVariant.UPC, RandomStringUtils.randomNumeric(12));
		//	10. Click “Save”
		home.click(AddVariant.SAVE);
		//	VP: Verify that new product is unable to save due to the error message inline of EAN field
		Assert.assertEquals(home.getTextByXpath(VariantInfo.DISPLAYNAME_DEFAULT), data.get("name"));;
		
		//Teardown:
		home.click(VariantInfo.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the tuning file could upload again after canceling uploading tuning file
	 */
	@Test
	public void TC051DAE_87() {
		result.addLog("ID : 051DAE_87: Verify that the tuning file could upload again after canceling uploading tuning file");
		/*
			1. Log into DTS portal as a DTS admin
			2. Navigate to "Products" page
			3. Click “Add Products” link
			4. Upload a tuning file
			5. Cancel uploading file
			VP: The message "File upload canceled" and Retry link is displayed
			6. Click "Retry" link
			7. Try to upload new tuning file again
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click “Add Products” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Upload a tuning file
		home.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[0]);
		home.uploadFileInterupt(AddAccessory.ADD_TUNNING, Constant.test_upload_cancel);
		// 5. Cancel uploading file
		home.clickImage(Constant.IMG_NAME[7]);
		// VP: The message "File upload canceled" and Retry link is displayed
		Assert.assertTrue(home.getTextByXpath(AddAccessory.TUNING_UPLOAD_MESSAGE).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[2]));
		Assert.assertTrue(home.isElementPresent(AddAccessory.RETRY_UPLOAD_TUNING));
		// 6. Click "Retry" link
		// 7. Try to upload new tuning file again
		home.uploadFile(AddAccessory.RETRY_UPLOAD_TUNING, Constant.tuning_hpxtt);
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(home.isTunningUploaded(AddAccessory.UPLOADED_TUNING, Constant.tuning_hpxtt));
	}
	
	/*
	 * Verify that three size of catalog images could upload again after canceling uploading
	 */
	@Test
	public void TC051DAE_88() {
		result.addLog("ID : 051DAE_88: Verify that three size of catalog images could upload again after canceling uploading");
		/*
			1. Log into DTS portal as a DTS admin
			2. Navigate to "Products" page
			3. Click “Add Products” link
			4. Upload three size of primary catalog image
			5. Cancel uploading file
			VP: The message "File upload canceled" and Retry link is displayed
			6. Click "Retry" link
			7. Try to upload another catalog image again
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click “Add Products” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Upload three size of primary catalog image
		home.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[1]);
		home.uploadFileInterupt(AddAccessory.SELECT_FILE_IMAGE_250, Constant.test_upload_cancel);
		// 5. Cancel uploading file
		home.clickImage(Constant.IMG_NAME[7]);
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[2]));
		Assert.assertTrue(home.isElementPresent(AddAccessory.RETRY_UPLOAD_IMAGE_250));
		// 6. Click "Retry" link
		// 7. Try to upload another catalog image again
		home.uploadFile(AddAccessory.RETRY_UPLOAD_IMAGE_250, Constant.IMG_NAME[0]);
		/*
		 * Verify that Three size of catalog image are uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[0]));
	}
	
	/*
	 * Verify that user is able to upload wrong demension for primary catalog image
	 */
	@Test
	public void TC051DAE_89() {
		result.addLog("ID : 051DAE_89: Verify that user is able to upload wrong demension for primary catalog image");
		/*
			1. Log into DTS portal as a DTSadmin
			2. Navigate to "Products" page
			3. Click “Add Products” link
			4. Upload a wrong image demension for 250x250 primary catalog image section
			5. Upload a wrong image demension for 500x500 primary catalog image section
			6. Upload a wrong image demension for 1000x100 primary catalog image section
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click “Add Products” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Upload a wrong image demension for 250x250 primary catalog image section
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_250, Constant.IMG_NAME[2]);
		// 5. Upload a wrong image demension for 500x500 primary catalog image section
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_500, Constant.IMG_NAME[2]);
		// 6. Upload a wrong image demension for 1000x100 primary catalog image section
		home.uploadFile(AddAccessory.SELECT_FILE_IMAGE_1000, Constant.IMG_NAME[0]);
		/*
		 * Demenson of Three size of primary catalog image are automatically
		 * rezied and upload successfully and the message
		 * "Automaticall resized. Original XX x YY px" is displayed
		 */
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[6]));
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[6]));
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[4]));
	}
	
	/*
	 * Verify that the marketing material could upload again after canceling uploading file
	 */
	@Test
	public void TC051DAE_91() {
		result.addLog("ID : 051DAE_91: Verify that the marketing material could upload again after canceling uploading file");
		/*
			1. Log into DTS portal as a DTS admin
			2. Navigate to "Products" page
			3. Click “Add Products” link
			4. Upload a marketing material file
			5. Cancel uploading file
			VP: The message "File upload canceled" and Retry link is displayed
			6. Click "Retry" link
			7. Try to upload new marketing material again
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click “Add Products” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Upload a marketing material file
		home.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[4]);
		home.uploadFileInterupt(AddAccessory.ADD_MARKETING, Constant.test_upload_cancel);
		// 5. Cancel uploading file
		home.clickImage(Constant.IMG_NAME[7]);
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddAccessory.MARKETING_UPLOAD_MESSAGE).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[2]));
		Assert.assertTrue(home.isElementPresent(AddAccessory.RETRY_UPLOAD_MARKETING));
		// 6. Click "Retry" link
		// 7. Try to upload new marketing material again
		home.uploadFile(AddAccessory.RETRY_UPLOAD_MARKETING, Constant.AUDIO_ROUTE_FILE);
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddAccessory.MARKETING_UPLOAD_MESSAGE).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[0]));
	}
	
	/*
	 * Verify that the tuning file could upload again after canceling uploading tuning file
	 */
	@Test
	public void TC051DAE_94() {
		result.addLog("ID : 051DAE_94: Verify that the tuning file could upload again after canceling uploading tuning file");
		/*
			1. Log into DTS portal as a DTS admin
			2. Navigate to "Products" page
			3. Select a product from products table
			4. Click "Add new variant" link
			5. Upload a tuning file
			6. Cancel uploading file
			VP: The message "File upload canceled" and Retry link is displayed
			7. Click "Retry" link
			8. Try to upload new tuning file again
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select a product from products table
		home.selectAnAccessory();
		// 4. Click "Add new variant" link
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
		// 8. Try to upload new tuning file again
		home.uploadFile(AddVariant.RETRY_UPLOAD_TUNING, Constant.tuning_hpxtt);
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(home.isTunningUploaded(AddVariant.UPLOADED_TUNING, Constant.tuning_hpxtt));
	}
	
	/*
	 * Verify that three size of catalog images could upload again after canceling uploading
	 */
	@Test
	public void TC051DAE_95() {
		result.addLog("ID : 051DAE_95: Verify that three size of catalog images could upload again after canceling uploading");
		/*
			1. Log into DTS portal as a DTS admin
			2. Navigate to "Products" page
			3. Select a product from products table
			4. Click "Add new variant" link
			5. Upload three size of primary catalog image
			6. Cancel uploading file
			VP: The message "File upload canceled" and Retry link is displayed
			7. Click "Retry" link
			8. Try to upload another catalog image again
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select a product from products table
		home.selectAnAccessory();
		// 4. Click "Add new variant" link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 5. Upload three size of primary catalog image
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
		// 8. Try to upload another catalog image again
		home.uploadFile(AddVariant.RETRY_UPLOAD_IMAGE_250, Constant.IMG_NAME[0]);
		/*
		 * Verify that Three size of catalog image are uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddVariant.UPLOAD_FILE_MESSSAGE[0]));
	}
	
	/*
	 * Verify that user is able to upload wrong demension for primary catalog image
	 */
	@Test
	public void TC051DAE_96() {
		result.addLog("ID : 051DAE_96: Verify that user is able to upload wrong demension for primary catalog image");
		/*
			1. Log into DTS portal as a DTS admin
			2. Navigate to "Products" page
			3. Select a product from products table
			4. Click "Add new variant" link
			5. Upload a wrong image demension for 250x250 primary catalog image section
			6. Upload a wrong image demension for 500x500 primary catalog image section
			7. Upload a wrong image demension for 1000x100 primary catalog image section
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select a product from products table
		home.selectAnAccessory();
		// 4. Click "Add new variant" link
		home.click(AccessoryInfo.ADD_VARIANT);
		// Click Upload custom image link
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		// 5. Upload a wrong image demension for 250x250 primary catalog image section
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_250, Constant.IMG_NAME[2]);
		// 6. Upload a wrong image demension for 500x500 primary catalog image section
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_500, Constant.IMG_NAME[2]);
		// 7. Upload a wrong image demension for 1000x100 primary catalog image section
		home.uploadFile(AddVariant.SELECT_FILE_IMAGE_1000, Constant.IMG_NAME[0]);
		/*
		 * Demenson of Three size of primary catalog image are automatically
		 * rezied and upload successfully and the message
		 * "Automaticall resized. Original XX x YY px" is displayed
		 */
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddVariant.UPLOAD_FILE_MESSSAGE[6]));
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddVariant.UPLOAD_FILE_MESSSAGE[6]));
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddVariant.UPLOAD_FILE_MESSSAGE[4]));
	}
	
	/*
	 * Verify that the marketing material could upload again after canceling uploading file
	 */
	@Test
	public void TC051DAE_98() {
		result.addLog("ID : 051DAE_98: Verify that the marketing material could upload again after canceling uploading file");
		/*
			1. Log into DTS portal as a DTS admin
			2. Navigate to "Products" page
			3. Select a product from products table
			4. Click "Add new variant" link
			5. Upload a marketing material file
			6. Cancel uploading file
			VP: The message "File upload canceled" and Retry link is displayed
			7. Click "Retry" link
			8. Try to upload new marketing material again
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select a product from products table
		home.selectAnAccessory();
		// 4. Click "Add new variant" link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 5. Upload a marketing material file
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
		// 8. Try to upload new marketing material again
		home.uploadFile(AddVariant.RETRY_UPLOAD_MARKETING, Constant.AUDIO_ROUTE_FILE);
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddVariant.MARKETING_UPLOAD_MESSAGE).contains(AddVariant.UPLOAD_FILE_MESSSAGE[0]));
	}
	
	/*
	 * Verify that user can upload tuning file by dragging and dropping file into "Add Tuning File" area
	 */
	@Test
	public void TC051DAE_99() {
		result.addLog("ID : 051DAE_99: Verify that user can upload tuning file by dragging and dropping file into 'Add Tuning File' area");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Click "Add Product" link
			4. Verify that the add product page is displayed
			5. Drag and Drop a tuning file into Add Tuning File area
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add Product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Verify that the add product page is displayed
		Assert.assertTrue(home.isElementPresent(AddAccessory.TITLE));
		// 5. Drag and Drop a tuning file into Add Tuning File area
		home.dragDropFile(Constant.tuning_hpxtt);
		/*
		 * Verify that The tuning file is upload successfully
		 */
		Assert.assertTrue(home.isTunningUploaded(AddAccessory.UPLOADED_TUNING, Constant.tuning_hpxtt));
	}
	
	/*
	 * Verify that user can upload three size of primary catalog images by dragging and dropping file into each primary image uploading area
	 */
	@Test
	public void TC051DAE_100() {
		result.addLog("ID : 051DAE_100: Verify that user can upload three size of primary catalog images by dragging and dropping file into each primary image uploading area");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Accessories" page
			3. Click "Add Product" link
			4. Verify that the add product page is displayed
			5. Drag and Drop each three size of primary catalog images
		*/
		// 2. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 3. Click "Add Product" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Verify that the add product page is displayed
		Assert.assertTrue(home.isElementPresent(AddAccessory.TITLE));
		// 5. Drag and Drop each three size of primary catalog images
		home.dragDropFile(Constant.IMG_NAME[0]);
		home.dragDropFile(Constant.IMG_NAME[1]);
		home.dragDropFile(Constant.IMG_NAME[2]);
		/*
		 * Verify that Three size of primary catalog image are upload successfully
		 */
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[0]));
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[0]));
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[0]));
	}
	
	
}
