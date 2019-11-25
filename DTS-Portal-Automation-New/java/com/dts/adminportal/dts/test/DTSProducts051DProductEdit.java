package com.dts.adminportal.dts.test;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.LanguagePackage;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.UserEdit;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.model.VariantInfo;
import com.dts.adminportal.util.DateUtil;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.LogReporter;
import com.dts.adminportal.util.TestData;

public class DTSProducts051DProductEdit extends BasePage{
	
	@Override
	protected void initData() {
	}	
	
	/*
	 * Verify that the Adding New Model shows up all necessary fields properly.
	 */
	@Test
	public void TC051DAE_01(){
		LogReporter.addLog("ID : 051DAE_01 : Verify that the Adding New Model shows up all necessary fields properly.");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			"VP: 051 product Edit page is showed up with: DTS Tracking Number, Brand dropdown, Model Name text field, Languages dropdown, Model Number, EAN/UPC text field, Type dropdown, Input Specifications table, Release date calendar, Aliases text field, Description text field, Tuning upload file link, Headphone:X Tuning Rating label, Product Catalog Images table and Marketing Material table.
			VP: The DTS Tracking Number is already generated and it is unediable.
			VP: Type dropdown contains ""Earbuds"", ""In-Ear"", ""On-Ear"" and ""Over-Ear"" items.
			VP: Headphone:X Tuning Rating combobox contains ""Undetermined(default)"", ""No Certification"", ""A"" and ""A+"" items
			VP: Headphone:X Inside combobox contains ""Not Applicable (N/A)"" and ""Embedded in Hardware"" items."
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to Accessory page
		productControl.click(PageHome.linkAccessories);
		// 3. Click add accessory link
		productControl.click(ProductModel.ADD_PRODUCT);
		/*
		 * Verify that 051 product Edit page is showed up
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getProductElementFieldIds()), true);
		/*
		 Verify that the DTS Tracking Number is generated automatically when adding new accessory
		 */
		// DTS Tracking Number is already generated
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.TRACKING_ID).length() > 0);	
		// DTS Tracking Number is uneditable.
		Assert.assertFalse(productControl.canEdit(AddEditProductModel.TRACKING_ID));
		/*
		 *  Verify that Headphone: X Tuning Rating and Headphone:X inside are displayed as dropdown list and user can set their items successfully
		 */
		Assert.assertTrue(productControl.isElementEditable(AddEditProductModel.TUNING_RATING));
		Assert.assertTrue(productControl.isElementEditable(AddEditProductModel.HEADPHONEX_INSIDE));
		/*
		 Verify Type dropdown contains "Earbuds", "In-Ear", "On-Ear" and "Over-Ear" items
		 */
		ArrayList<String> types = productControl.getOptionList(AddEditProductModel.MODEL_TYPE);
		Assert.assertTrue(ListUtil.containsAll(types, AddEditProductModel.ProductType.getNames()));
		/*
		 * VP: Verify that the 051D product Edit page is displayed
		 */
		// 4. List out the Headphone:X Tuning Rating combobox
		ArrayList<String> tuningOptions = productControl.getAllComboboxOption(AddEditProductModel.TUNING_RATING);
		/*
		 * Verify that Headphone:X Tuning Rating combobox contains "Undetermined(default)", "No Certification", "A" and "A+" items
		 */
		
		Assert.assertTrue(ListUtil.containsAll(tuningOptions, AddEditProductModel.TuningRatingOption.getNames()));
		/*
		 * VP: Verify that the 051D product Edit page is displayed
		 */
		// 4. List out theHeadphone:X Inside combobox
		ArrayList<String> headPhoneOptions = productControl.getAllComboboxOption(AddEditProductModel.HEADPHONEX_INSIDE);
		/*
		 * Headphone:X Inside combobox contains "Not Applicable (N/A)" and "Embedded in Hardware" items
		 */
		Assert.assertTrue(ListUtil.containsAll(headPhoneOptions, AddEditProductModel.HeadphoneInsideOption.getNames()));
	}
	
	
	/*
	 * Verify that the Headphone: X Tuning Rating and Headphone:X inside are read only for DTS users who do not have "Approve accessory tunings" privilege.
	 */
	@Test
	public void TC051DAE_03(){
		LogReporter.addLog("ID : 051DAE_03 : Verify that the Headphone: X Tuning Rating and Headphone:X inside are read only for DTS users who do not have 'Approve accessory tunings' privilege");
		/*
			Pre-condition: DTS user does not have "Approve product tunings" privilege.
			1. Log into DTS portal as a DTS user
			2. Navigate to "products" page
			3. Click "Add product" link
		 */
		
		/*
		 * Pre-condition: DTS user does not have "Approve product tunings" privilege.
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to user page
		productControl.click(PageHome.LINK_USERS);
		// Select DTS user
		userControl.dtsSelectUserByEmail(DTS_USER);
		// Click Edit link
		productControl.click(UserMgmt.EDIT);
		// Disable "Approve product tunings" privilege
		userControl.disablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.Approve_product_tuning);
		productControl.click(AddUser.SAVE);
		// Log out
		productControl.logout();
		/*
		 * ********************************************************************
		 */
		// 1. Log into DTS portal as a DTS user
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// 2. Navigate to "products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click "Add product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		/*
		 * Verify that Headphone: X Tuning Rating and Headphone:X inside are read only
		 */
		Assert.assertFalse(productControl.isElementEditable(AddEditProductModel.TUNING_RATING_DISABLE));
		Assert.assertFalse(productControl.isElementEditable(AddEditProductModel.HEADPHONEX_INSIDE_DISABLE));

	}
	
	/*
	 * Verify that the tuning file is validated for uploading
	 */
	@Test
	public void TC051DAE_05(){
		LogReporter.addLog("ID : 051DAE_05 : Verify that the tuning file is validated for uploading");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Upload an invalid tuning file
			VP:An error message is displayed and the invalid tuning file is not added.
			5. Upload a valid tuning zip file
			VP: The uploaded tuning link is displayed as "DTS Tuning".
			6. Delete this tuning file
			7. Upload another hpxtt tuning file
			VP: The tuning hpxtt file is upload successfully.
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click "Add product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		//4. Upload an invalid tuning file
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_Invalid.getName());
		/*
		 * Verify that An error message is displayed and the invalid tuning file is not added
		 */	
		WebElement failedTuning = driver.findElement(By.xpath(AddEditProductModel.UPLOADED_TUNING)).findElement(By.tagName("p"));
		Assert.assertEquals(productControl.getElementText(failedTuning), "! Invalid file");
		// 5. Upload a valid tuning zip file
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_TUNING, AddEditProductModel.FileUpload.Tuning_ZIP.getName());
		/*
		 * Verify that The tuning zip file is upload successfully
		 */	
		Assert.assertTrue(productControl.isTunningUploaded(AddEditProductModel.UPLOADED_TUNING, AddEditProductModel.FileUpload.Tuning_ZIP.getName()));
		
		// 4. Upload a tuning file successfully
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		/*
		 * Verify that The upload tuning link is displayed "DTS Tuning"
		 */
		WebElement uploadedTuning = driver.findElement(By.xpath(AddEditProductModel.UPLOADED_TUNING)).findElement(By.className("mauploadTuning"));
		Assert.assertEquals(productControl.getElementText(uploadedTuning), "DTS Tuning");
		productControl.click(AddEditProductModel.DELETE_UPLOADED_TUNING);
		productControl.selectConfirmationDialogOption("Delete");
		// 7. Upload a valid tuning hpxtt file
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		/*
		 * Verify that The tuning zip file is upload successfully
		 */	
		Assert.assertTrue(productControl.isTunningUploaded(AddEditProductModel.UPLOADED_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName()));
	}
	
	/*
	 * Verify that the EAN and UPC are validated for the already exist EAN/UPC code
	 */
	@Test
	public void TC051DAE_10(){
		LogReporter.addLog("ID : 051DAE_10 : Verify that the EAN and UPC are validated for the already exist EAN/UPC code");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Accessory page
		productControl.click(PageHome.linkAccessories);
		// Click add accessory link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		String productName = data.get("name");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to Accessory page
		productControl.click(PageHome.linkAccessories);  
		// 3. Click add accessory link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill value into EAN and UPC text fields which is saved for another product.
		// 5. Click "Save" link
		data.put("name", "Test" + RandomStringUtils.randomNumeric(6));
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Check if PDPP-467 found
		if(!productControl.checkMessageDisplay("EAN must be unique")){
			productControl.addErrorLog("PDPP-467: 051D Accessory Edit : The warning message is not displayed when adding duplicate EAN/UPC");
			Assert.assertTrue(false);
		}
		/*
		 * Verify that A warning message "Warning: A model already exist with this EAN and UPC code"
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("EAN must be unique"));
		Assert.assertTrue(productControl.checkMessageDisplay("UPC must be unique"));
		// Delete product
		productControl.deleteAccessorybyName(productName);
	}
	
	/*
	 * Verify that the Model Name with (Default) English is required when adding new product.
	 */	
	@Test
	public void TC051DAE_11(){
		LogReporter.addLog("ID : 051DAE_11 : Verify that the Model Name with (Default) English is required when adding new product.");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			"VP: The ""- Select -"" item is the default value of Model Name language dropdown
			VP: The Model Name language dropdown contains items: - Select -, Chinese - Simplified, Chinese - Traditional, French, German, Italian, Japanese, Korean, Russian and Spanish."
			4. Select another language for Model Name
			"VP: Another language dropdown is displayed
			VP: The hint text ""Inherits default"" is displayed in model name text field."
			5. Select the same item for second language dropdown
			VP: A warning message is displayed which notifying for the duplicating of languages.
			6. Type value into model name input field
			7. Click on Trashcan icon
			VP: The language dropdown is restore to default "Select" value and the input field is cleared 
			8. Fill valid value into all required fields but leaving Model Name empty
			9. Click "Save" link
			A warning message is displayed which notifying the model name is empty.
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click "Add product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		/*
		 * Verify that The "- Select -" item is the default value of Model Name language dropdown
		 */
		Assert.assertEquals(productControl.getItemSelected(AddEditProductModel.LANGUAGE_DROPDOWN(1)), AddEditProductModel.ProductLanguage.SELECT.getName());
		// List out the Model Name language dropdown
		// use 0 here mean: //*[@id='lang-div-container']/div/select
		ArrayList<String> languageOptions = productControl.getAllComboboxOption(AddEditProductModel.LANGUAGE_DROPDOWN(0));
		/*
		 * The Model Name language dropdown contains items: - Select -, Chinese - Simplified, Chinese - Traditional, French, German, Italian, Japanese, Korean, Russian and Spanish
		 */
		Assert.assertTrue(ListUtil.containsAll(languageOptions, AddEditProductModel.ProductLanguage.getNames()));
		// Select another language for Model Name
		productControl.selectOptionByName(AddEditProductModel.LANGUAGE_DROPDOWN(1), AddEditProductModel.ProductLanguage.CHINESE_TRA.getName());
		/*
		 Verify that the hint text "Inherits default" is displayed for an empty fields of each model name language
		 */ 
		Assert.assertEquals(productControl.getAtributeValue(AddEditProductModel.EMPTY_LANGUAGE_FIELD, "placeholder"), "inherits default");
		/*
		 * Verify that another language dropdown will display when selecting a model name language
		 */
		List<LanguagePackage> languagePackages = productControl.getLanguagePackage(AddEditProductModel.LANGUAGE_CONTAINER);
		Assert.assertTrue(languagePackages.size() > 1);
		// 5. Select the same item for second language dropdown
		productControl.selectOptionByName(languagePackages.get(languagePackages.size() - 1).languagedropbox,
				AddEditProductModel.ProductLanguage.CHINESE_TRA.getName());
		/*
		 * A warning message is displayed which notifying for the duplicating of languages
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("Language is duplicated"));
		// 5. Type value into model name input field
		productControl.editData(AddEditProductModel.OTHER_LANGUAGE_NAME(1), "just a test");
		// 6. Click on Trashcan icon 
		productControl.click(AddEditProductModel.TRASH_ICON(1));
		/*
		 * Verify that The language dropdown is restore to default "Select" value and the input field is cleared 
		 */
		Assert.assertEquals(productControl.getItemSelected("//*[@id='lang-div-container']/div[1]/select"), "- Select -");
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.OTHER_LANGUAGE_NAME(1)), "");
		
		
		// Fill valid value into all required fields but leaving Model Name empty
		// Click "Save" link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		data.remove("name");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * Verify that A warning message is displayed which notifying the model name is empty
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("Model name is required."));
	}
	
	/*
	 * Verify that three size of Primary Catalog Images are validated after uploading and clicking on thumbnail/file name
	 */
	@Test
	public void TC051DAE_20(){
		LogReporter.addLog("ID : 051DAE_20 : Verify that three size of Primary Catalog Images are validated after uploading and clicking on thumbnail/file name");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Upload image for each size of Primary Catalog Image successfully
			"VP: The correct image thumbnail is displayed next to image name link.
			VP: Verify that the image file name and  file size is displayed below the catalog image file name link"
			5. Click "Save" link
			VP: Verify that new product is added successfully
			6. Click "Edit" link
			VP: The uploaded date and file size are displayed below the image name for each size of Primary Catalog Image.
			7. Click on image thumbnail and file name of each image's size
			VP: A lightbox style popup with the picture showing in full size is displayed
			8. Delete the uploaded image by clicking on trashcan icon
			9. Upload image again
			Verify that new image is uploaded successfully
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click "Add product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Upload image for each size of Primary Catalog Image successfully
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_1000, AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		/*
		 * Verify that The correct image thumbnail is displayed next to image name link
		 */
		Assert.assertTrue(productControl.isElementDisplayHorizontal(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250, AddEditProductModel.CATALOG_IMAGE_UPLOAD_INFO_250));
		Assert.assertTrue(productControl.isElementDisplayHorizontal(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_500, AddEditProductModel.CATALOG_IMAGE_UPLOAD_INFO_500));
		Assert.assertTrue(productControl.isElementDisplayHorizontal(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000, AddEditProductModel.CATALOG_IMAGE_UPLOAD_INFO_1000));
		
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_INFO_250).contains(AddEditProductModel.FileUpload.IMG_250_JPG.getName()));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_INFO_500).contains(AddEditProductModel.FileUpload.IMG_500_JPG.getName()));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_INFO_1000).contains(AddEditProductModel.FileUpload.IMG_1000_JPG.getName()));
		
		Assert.assertTrue(productControl.isElementDisplayVertically(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_250, AddEditProductModel.CATALOG_IMAGE_FILE_SIZE_250));
		Assert.assertTrue(productControl.isElementDisplayVertically(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_500, AddEditProductModel.CATALOG_IMAGE_FILE_SIZE_500));
		Assert.assertTrue(productControl.isElementDisplayVertically(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_1000, AddEditProductModel.CATALOG_IMAGE_FILE_SIZE_1000));
		
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// 5. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: Verify that new product is added successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), data.get("name"));
		// 6. Click "Edit" link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// Get the current date
		String date = DateUtil.getADateGreaterThanToday("dd MMM yyyy", 0);
		/*
		 * The uploaded date and file size are displayed below the image name for each size of Primary Catalog Image
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_INFO_250).contains(date));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_INFO_500).contains(date));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_INFO_1000).contains(date));
		
		Assert.assertTrue(productControl.isElementDisplayVertically(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_250, AddEditProductModel.CATALOG_IMAGE_FILE_SIZE_250));
		Assert.assertTrue(productControl.isElementDisplayVertically(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_500, AddEditProductModel.CATALOG_IMAGE_FILE_SIZE_500));
		Assert.assertTrue(productControl.isElementDisplayVertically(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_1000, AddEditProductModel.CATALOG_IMAGE_FILE_SIZE_1000));
		
		// Click on image thumbnail 250
		productControl.click(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250);
		productControl.waitForElementClickable(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(AddEditProductModel.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_250_JPG.getName().replaceAll(".jpg", "")));
		// Close lightbox image
		productControl.click(AddEditProductModel.LIGHTBOX_CLOSE);
		productControl.waitForElementDisappear(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		// Click on image thumbnail 500
		productControl.click(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_500);
		productControl.waitForElementClickable(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(AddEditProductModel.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_500_JPG.getName().replaceAll(".jpg", "")));
		// Close lightbox image
		productControl.click(AddEditProductModel.LIGHTBOX_CLOSE);
		productControl.waitForElementDisappear(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		// Click on image thumbnail 500
		productControl.click(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000);
		productControl.waitForElementClickable(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(AddEditProductModel.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_1000_JPG.getName().replaceAll(".jpg", "")));
		// Close lightbox image
		productControl.click(AddEditProductModel.LIGHTBOX_CLOSE);
		productControl.waitForElementDisappear(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		
		// Click on file name 250
		productControl.click(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_250);
		productControl.waitForElementClickable(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(AddEditProductModel.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_250_JPG.getName().replaceAll(".jpg", "")));
		// Close lightbox image
		productControl.click(AddEditProductModel.LIGHTBOX_CLOSE);
		productControl.waitForElementDisappear(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		// Click on file name 500
		productControl.click(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_500);
		productControl.waitForElementClickable(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(AddEditProductModel.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_500_JPG.getName().replaceAll(".jpg", "")));
		// Close lightbox image
		productControl.click(AddEditProductModel.LIGHTBOX_CLOSE);
		productControl.waitForElementDisappear(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		// Click on file name 1000
		productControl.click(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_1000);
		productControl.waitForElementClickable(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(AddEditProductModel.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_1000_JPG.getName().replaceAll(".jpg", "")));
		// Close lightbox image
		productControl.click(AddEditProductModel.LIGHTBOX_CLOSE);
		productControl.waitForElementDisappear(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		
		// Delete the uploaded image by clicking on trashcan icon
		productControl.deleteAllUploadedImage(AddEditProductModel.IMAGE_CATALOG);
		// Upload image again
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.IMG_NAME[0]);
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.IMG_NAME[1]);
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_1000, AddEditProductModel.IMG_NAME[2]);
		/*
		 * Verify that new image is uploaded successfully
		 */
		Assert.assertTrue(productControl.isImageUploaded(AddEditProductModel.IMAGE250_DISPLAY, AddEditProductModel.IMG_NAME[0]));
		Assert.assertTrue(productControl.isImageUploaded(AddEditProductModel.IMAGE500_DISPLAY, AddEditProductModel.IMG_NAME[1]));
		Assert.assertTrue(productControl.isImageUploaded(AddEditProductModel.IMAGE1000_DISPLAY, AddEditProductModel.IMG_NAME[2]));
		
		// Delete product
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the tuning file, three primary catalog image and  marketing material file could be uploaded successfully by drapping and dropping.
	 */
	@Test
	public void TC051DAE_25(){
		LogReporter.addLog("ID : 051DAE_25 : Verify that the tuning file, three primary catalog image and  "
				+ "marketing material file could be uploaded successfully by dragging and dropping.");
		/*
			Pre-condition: User has "Add and manage accessories" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Drag and drop a file into tuning file, three primary catalog images and Marketing Material area.
			New marketing material file is uploaded successfully
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click "Add Accessory" link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Drag and drop a file into tuning file, three primary catalog images and Marketing Material area.
		productControl.dragDropFile(AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		productControl.dragDropFile(AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		productControl.dragDropFile(AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		productControl.dragDropFile(AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		productControl.dragDropFile(AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		
		/*
		 * Verify that all files are uploaded successfully
		 */
		Assert.assertTrue(productControl.isMarketingUploaded(AddEditProductModel.MARKETING_FILE_CONTAINER, AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName()));
		
		Assert.assertTrue(productControl.isTunningUploaded(AddEditProductModel.UPLOADED_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName()));
		
		Assert.assertTrue(productControl.isImageUploaded(AddEditProductModel.IMAGE250_DISPLAY, AddEditProductModel.IMG_NAME[0]));
		Assert.assertTrue(productControl.isImageUploaded(AddEditProductModel.IMAGE500_DISPLAY, AddEditProductModel.IMG_NAME[1]));
		Assert.assertTrue(productControl.isImageUploaded(AddEditProductModel.IMAGE1000_DISPLAY, AddEditProductModel.IMG_NAME[2]));
	}
	
	/*
	 * Verify that the "Connection Type" and "Support Input Channels" module are validated properly
	 */
	@Test
	public void TC051DAE_27(){
		LogReporter.addLog("ID : 051DAE_27 : Verify that the 'Connection Type' and 'Support Input Channels' module are validated properly");
		/*
		1. Log into DTS portal as a DTS user
		2. Navigate to "Products" page
		3. Click "Add product" link
		VP: The Input Specifications section inludes "Connection Type" and "Support Input Channels" module.
		4. Fill valid value into all required fields but do not check for any connection type
		5. Click "Save" link
		VP: An error message displays which notifying user to select at least one connection type
		6. Fill valid value into all required fields but do not select any supported input channels value
		7. Click "Save" link
		VP: An error message displays which notifying user to select at least one Supported Input Channels value
		*/
		
		// 2.Navigate to Accessory page
		productControl.click(PageHome.linkAccessories);
		// 3.Click add accessory link
		productControl.click(ProductModel.ADD_PRODUCT);
		/*
		 Verify that the Input Specifications section inludes "Connection Type" and "Support Input Channels" module.
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.WIRED_CHECKBOX));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.WIRED_DROPBOX));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.BLUETOOTH_CHECKBOX));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.BLUETOOTH_DROPBOX));
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		data.remove("wired");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * An error message displays which notifying user to select at least one connection type
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("Connection Type is required"));
		
		// Select both wire and bluetooth connection type but leave the content channel capability empty
		// Leave the content channel empty
		productControl.selectInputChannel(AddEditProductModel.WIRED_CONTAINER, "none");
		productControl.selectInputChannel(AddEditProductModel.BLUETOOTH_CONTAINER, "none");
		// 5. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * Verify that at least one Supported Input Channels count is required when creating an accessory.
		 */
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.WIRED_MESSAGE), AddEditProductModel.INPUT_CHANNEL_ERROR_STRING);
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.BLUETOOTH_MESSAGE), AddEditProductModel.INPUT_CHANNEL_ERROR_STRING);
	}
	
	
	/*
	 * Verify that the Adding New Model Variant shows up all necessary fields properly.
	 */
	
	@Test
	public void TC051DAE_35(){
		LogReporter.addLog("ID : 051DAE_35 : Verify that the Adding New Model Variant shows up all necessary fields properly.");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP: 052 Model Variant Edit page is showed up with: Model Name text field, Languages dropdown, EAN/UPC text field, 
			Descriptor, Input Specifications, Release date, Tuning ( Use Parent's Tuning checkbox and Upload Custom Tuning link), 
			Headphone: X Tuning Rating, Primary Catalog Image section and Marketing Material section.
			VP: Tuning section is displayed with "Use Parent's Tuning" checkbox and "Upload Custom Tuning" link
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select an product from Products table
		productControl.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 5. Click "Add New Variant" link from "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * Verify that 052 Model Variant Edit page is showed up
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getVariantElementsInfo()), true);	
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.PARENT_TUNNING_LINK));	
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.UPLOAD_CUSTOM_TUNNING));
		// Delete product
		productControl.click(AddEditProductModel.CANCEL_PRODUCT);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that user can upload tuning, primary catalog image and marketing materials by dragging and dropping file into drag and drop area.
	 */
	@Test
	public void TC051DAE_39(){
		LogReporter.addLog("ID : 051DAE_39 : Verify that user can upload tuning, primary catalog image and "
				+ "marketing materials by dragging and dropping file into drag and drop area.");
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
			The tuning file, three primary catalog image and marketing material are upload successfully.
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select an product from Products table
		String productName = productControl.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), productName);
		// 5. Click "Add New Variant" link from "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.VARIANT_TITLE));
		// 6. Click "Upload Custom Tuning" link and custom images
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_TUNNING);
		//Click upload custom primary catalog images
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_IMAGE);
		/*
		 * VP. Verify that the "Add Tuning File" is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.ADD_TUNNING));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.TUNING_DRAG_DROP_AREA));
		
		// Drag and drop a file into tuning file, three primary catalog images and Marketing Material area.
		productControl.dragDropFile(AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		//drag and drop images
		productControl.dragDropFile(AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		productControl.dragDropFile(AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		productControl.dragDropFile(AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		//Marketing files
		productControl.dragDropFile(AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		
		/*
		 * Verify that all files is uploaded successfully
		 */
		Assert.assertTrue(productControl.isTunningUploaded(AddEditProductModel.UPLOADED_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName()));
		
		Assert.assertTrue(productControl.isMarketingUploaded(AddEditProductModel.MARKETING_FILE_CONTAINER, AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName()));
		
		Assert.assertTrue(productControl.isImageUploaded(AddEditProductModel.IMAGE250_DISPLAY, AddEditProductModel.IMG_NAME[0]));
		Assert.assertTrue(productControl.isImageUploaded(AddEditProductModel.IMAGE500_DISPLAY, AddEditProductModel.IMG_NAME[1]));
		Assert.assertTrue(productControl.isImageUploaded(AddEditProductModel.IMAGE1000_DISPLAY, AddEditProductModel.IMG_NAME[2]));
		
	}
	
	/*
	 * Verify that the tuning file is validated for uploading
	 */
	@Test
	public void TC051DAE_41(){
		LogReporter.addLog("ID : 051DAE_41 : Verify that the tuning file is validated for uploading");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			VP: Verify that the 040D product Detail page is displayed
			4. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			5. Click "Upload Custom Tuning" link
			VP. Verify that the "Add Tuning File" item is displayed.
			6. Click "Select File" button
			7. Upload an invalid tuning file
			VP: An error message is displayed and the invalid tuning file is not added.
			8. Upload a valid tuning zip file
			VP: Tuning zip file is uploaded successfully
			9. Delete this tuning file
			10. Upload an hpxtt tuning file
			The valid hpxtt tuning file is uploaded successfully, and the uploaded tuning file called "DTS Tuning".
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select an product from Products table
		productControl.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 5. Click "Add New Variant" link from "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getVariantElementsInfo()), true);
		// 6. Click "Upload Custom Tuning" link
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_TUNNING);
		/*
		 * VP. Verify that the "Add Tuning File" item is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.ADD_TUNNING));
		// 7. Click "Select File" button
		// 8. Upload an invalid tuning file
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_Invalid.getName());
		/*
		 * Verify that An error message is displayed and the invalid tuning file is not added
		 */
		WebElement failedTuning = driver.findElement(By.xpath(AddEditProductModel.UPLOADED_TUNING)).findElement(By.tagName("p"));
		Assert.assertEquals(productControl.getElementText(failedTuning), "! Invalid file");
		
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_ZIP.getName());
		/*
		 * Verify that The tuning file is upload successfully
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.UPLOADED_TUNING));
		
		productControl.click(AddEditProductModel.DELETE_UPLOADED_TUNING);
		productControl.selectConfirmationDialogOption("Delete");
		
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		/*
		 * Verify that The Tuning section is displayed with new uploaded tuning file and it is called "DTS Tuning"
		 */
		WebElement uploadedTuning = driver.findElement(By.xpath(AddEditProductModel.UPLOADED_TUNING)).findElement(By.className("mauploadTuning"));
		Assert.assertEquals(productControl.getElementText(uploadedTuning), "DTS Tuning");
	}
	

	/*
	 * Verify that the uploaded tuning file is deleted if user select the "User Parent's Tuning" option
	 */
	@Test
	public void TC051DAE_44(){
		LogReporter.addLog("ID : 051DAE_44 : Verify that the uploaded tuning file is deleted if user select the \"User Parent's Tuning\" option");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Select an product from Products table
			VP:  Verify that the 040P product Detail page is displayed
			4. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052P Model Variant Edit page is displayed.
			5. Click "Upload Custom Tuning" link
			6. Upload a valid tuning file
			VP: Verify that the custom tuning file is uploaded successfully.
			7. Click on "Use Parent's Tuning" link
			VP: The Delete Tuning confirmation dialog is displayed with Delete and Cancel button.
			8. Click on the "Delete" button
			The uploaded tuning file is deleted.
		*/
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select an product from Products table
		productControl.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 5. Click "Add New Variant" link from "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getVariantElementsInfo()), true);
		// 6. Click "Upload Custom Tuning" link
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_TUNNING);
		// 7. Upload a valid tuning file
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		/*
		 * VP: Verify that the custom tuning file is uploaded successfully
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.UPLOADED_TUNING));
		// 8. Click on "Use Parent's Tuning" link
		productControl.click(AddEditProductModel.PARENT_TUNNING_LINK);
		productControl.waitForElementClickable(PageHome.BTN_CONFIRMATION_DANGER);
		/*
		 * Verify that The Delete Tuning confirmation dialog is displayed with Delete and Cancel button
		 */
		Assert.assertEquals(productControl.getTextByXpath(PageHome.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(productControl.getTextByXpath(PageHome.BTN_CONFIRMATION_CANCEL), "Cancel");
		productControl.selectConfirmationDialogOption("Delete");
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.UPLOADED_TUNING));
	}
	
	
	/*
	 * Verify that the EAN/UPC is validated for the already exist EAN/UPC code
	 */
	@Test
	public void TC051DAE_46(){
		LogReporter.addLog("ID : 051DAE_46 : Verify that the EAN/UPC is validated for the already exist EAN/UPC code");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product page
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		// Click Add new variant link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// Create variant
		Hashtable<String,String> dataVariant = TestData.variantData(false, false, false);
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		/*
		 * ****************************************************************
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select an product from Products table
		productControl.selectAccessorybyName(dataProduct.get("name"));
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 5. Click "Add New Variant" link from "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getVariantElementsInfo()), true);
		// 6. Fill value into EAN/UPC text field which is saved for another product
		// 7. Click "Save" link
		dataVariant.put("name", "NewTest" + RandomStringUtils.randomNumeric(6));
		productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
		/*
		 * Verify that A warning message "Warning: A model already exist with this EAN/UPC code"
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("EAN must be unique"));
		Assert.assertTrue(productControl.checkMessageDisplay("UPC must be unique"));
		// Delete product
		productControl.click(AddEditProductModel.CANCEL_PRODUCT);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the Model Name with (Default) English is required when adding new product.
	 */
	@Test
	public void TC051DAE_47(){
		LogReporter.addLog("ID : 051DAE_47: Verify that the Model Name with (Default) English is required when adding new product.");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			"VP. Verify that the 052D Model Variant Edit page is displayed and the ""- Select -"" item is the default value of Model Name language dropdown
			VP: The Model Name language dropdown contains items: - Select -, Chinese - Simplified, Chinese - Traditional, French, German, Italian, Japanese, Korean, Russian and Spanish."
			6. Set language dropdown to another language
			VP: Another language dropdown is displayed below
			7. Select the same item for second language dropdown
			VP:A warning message is displayed which notifying for the duplicating of languages.
			8. Set language dropdown to "Select"
			VP: The model name language text field is disabled.
			9. Type value into model name input field
			10. Click on Trashcan icon
			VP: The language dropdown is restore to default "Select" value and the input field is cleared
			11. Fill valid value into all required fields but leaving Model Name empty
			12. Click "Save" link
			A warning message is displayed which notifying the model name is empty.
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select an product from Products table
		productControl.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 5. Click "Add New Variant" link from "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getVariantElementsInfo()), true);
		
		/*
		 * Verify that The "- Select -" item is the default value of Model Name language dropdown
		 */
		Assert.assertEquals(productControl.getItemSelected(AddEditProductModel.LANGUAGE_DROPDOWN(1)), AddEditProductModel.ProductLanguage.SELECT.getName());
		// List out the Model Name language dropdown
		ArrayList<String> languageOptions = productControl.getAllComboboxOption(AddEditProductModel.LANGUAGE_DROPDOWN(0));
		/*
		 * The Model Name language dropdown contains items: - Select -, Chinese - Simplified, Chinese - Traditional, French, German, Italian, Japanese, Korean, Russian and Spanish
		 */
		Assert.assertTrue(ListUtil.containsAll(languageOptions, AddEditProductModel.ProductLanguage.getNames()));
		// Select another language for Model Name
		productControl.selectOptionByName(AddEditProductModel.LANGUAGE_DROPDOWN(1), AddEditProductModel.ProductLanguage.CHINESE_TRA.getName());
		/*
		 Verify that the hint text "Inherits default" is displayed for an empty fields of each model name language
		 */ 
		Assert.assertEquals(productControl.getAtributeValue(AddEditProductModel.EMPTY_LANGUAGE_FIELD, "placeholder"), 
				AddEditProductModel.ProductModelName.INHERIT_DEFAULT.getName());
		/*
		 * Verify that another language dropdown will display when selecting a model name language
		 */
		List<LanguagePackage> languagePackages = productControl.getLanguagePackage(AddEditProductModel.LANGUAGE_CONTAINER);
		Assert.assertTrue(languagePackages.size() > 1);
		// 5. Select the same item for second language dropdown
		productControl.selectOptionByName(languagePackages.get(languagePackages.size() - 1).languagedropbox,
				AddEditProductModel.ProductLanguage.CHINESE_TRA.getName());
		/*
		 * A warning message is displayed which notifying for the duplicating of languages
		 */
		Assert.assertTrue(productControl.checkMessageDisplay("Language is duplicated"));
		
		productControl.selectOptionByName(languagePackages.get(languagePackages.size() - 1).languagedropbox,
				AddEditProductModel.ProductLanguage.SELECT.getName());
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.OTHER_LANGUAGE_NAME(languagePackages.size() - 1)), "");
		
		
		// 5. Type value into model name input field
		productControl.editData(AddEditProductModel.OTHER_LANGUAGE_NAME(1), "just a test");
		// 6. Click on Trashcan icon 
		productControl.click(AddEditProductModel.TRASH_ICON(1));
		/*
		 * Verify that The language dropdown is restore to default "Select" value and the input field is cleared 
		 */
		Assert.assertEquals(productControl.getItemSelected(AddEditProductModel.LANGUAGE_DROPDOWN(1)), AddEditProductModel.ProductLanguage.SELECT.getName());
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.OTHER_LANGUAGE_NAME(1)), "");
		
		// 6. Fill valid value into all required fields but leaving Model Name empty
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.variantData(false, false, false);
		data.remove("name");
		productControl.addVariant(AddEditProductModel.getVariantHash(), data);
		/*
		 * Verify that A warning message is displayed which notifying the model name is empty
		 */
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.ProductModelName.MODEL_NAME_REQUIRED.getName()));
	}
	
	/*
	 * Verify that the Input Specifications section inludes "Connection Type" and "Support Input Channels" module but they are disabled
	 */
	@Test
	public void TC051DAE_55(){
		LogReporter.addLog("ID : TC051DAE_55 : Verify that the Input Specifications section inludes 'Connection Type' and 'Support Input Channels' module but they are disabled");
		/*
			Pre-condition: User has "Add and manage Products" rights.
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select an product from Products table
		String productName = productControl.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), productName);
		// 5. Click "Add New Variant" link from "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 *  Verify that the 052D Model Variant Edit page is displayed and the Input Specifications section inludes "Connection Type" and "Support Input Channels" module but they are disabled
		 */
		Assert.assertFalse(productControl.isElementEditable(AddEditProductModel.INPUT_SPECIFICATIONS));
	}
	
	/*
	 * Verify that user can upload primary images file by dragging and dropping file into "Add Custom Image File" area
	 */
	@Test
	public void TC051DAE_63(){
		LogReporter.addLog("ID : 051DAE_63 : Verify that user can upload primary images file by dragging and dropping file into 'Add Custom Image File' area");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select an product from Products table
		String productName = productControl.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), productName);
		// 5. Click "Add New Variant" link from "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.PRODUCT_TITLE));
		// 6. Click "Upload Custom Images" link
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_IMAGE);
		/*
		 * VP. Verify that the "Add Custom Image File" is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.SELECT_FILE_IMAGE_250));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.IMAGE250_DRAG_DROP_AREA));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.SELECT_FILE_IMAGE_500));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.IMAGE500_DRAG_DROP_AREA));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.SELECT_FILE_IMAGE_1000));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.IMAGE1000_DRAG_DROP_AREA));
		// 7. Drag and Drop a tuning file into Add Custom Image File area
		productControl.dragDropFile(AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		/*
		 * Verify that The image file is upload successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_250)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
	}
	
	/*
	 * Verify that user can upload primary image file via "Select File" button of "Add Custom Image File" area successfully
	 */
	@Test
	public void TC051DAE_64(){
		LogReporter.addLog("ID : 051DAE_64 : Verify that user can upload primary image file via 'Select File' button of 'Add Custom Image File' area successfully");
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
			The image is upload successfully.
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select an product from Products table
		String productName = productControl.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), productName);
		// 5. Click "Add New Variant" link from "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.PRODUCT_TITLE));
		// 6. Click "Upload Custom Images" link
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_IMAGE);
		/*
		 * VP. Verify that the "Add Custom Image File" is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.SELECT_FILE_IMAGE_250));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.IMAGE250_DRAG_DROP_AREA));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.SELECT_FILE_IMAGE_500));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.IMAGE500_DRAG_DROP_AREA));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.SELECT_FILE_IMAGE_1000));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.IMAGE1000_DRAG_DROP_AREA));
		// 7. Click "Select File" button
		// 8. Upload an image file via dialog box
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_1000, AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		/*
		 * Verify that The image is upload successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_250)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_500)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_1000)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
	}
	
	/*
	 * Verify that the appropriate confirmation dialog for discarding the custom image is displayed when the "Use Parent's Images" is checked after the custom image file has been uploaded successfully
	 */
	@Test
	public void TC051DAE_66(){
		LogReporter.addLog("ID : 051DAE_66 : Verify that the appropriate confirmation dialog for discarding the custom image is displayed when the 'Use Parent's Images' is checked after the custom image file has been uploaded successfully");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select an product from Products table
		productControl.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 5. Click "Add New Variant" link from "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getVariantElementsInfo()), true);
		// 6. Click "Upload Custom Images" link
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_IMAGE);
		/*
		 *  VP. Verify that the "Add Custom Image File" is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.SELECT_FILE_IMAGE_250));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.SELECT_FILE_IMAGE_500));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.SELECT_FILE_IMAGE_1000));
		// 7. Upload an image file
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.IMG_NAME[0]);
		/*
		 * VP: Verify that the image is uploaded successfully
		 */
		Assert.assertTrue(productControl.isImageUploaded(AddEditProductModel.IMAGE250_DISPLAY, AddEditProductModel.IMG_NAME[0]));
		// 8. Click on the "Use Parent's Images" link
		productControl.click(AddEditProductModel.PARENT_IMAGE_LINK);
		productControl.waitForElementClickable(PageHome.BTN_CONFIRMATION_DANGER);
		/*
		 * Verify that The appropriate confirmation dialog for discarding the custom image  is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(PageHome.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(productControl.getTextByXpath(PageHome.BTN_CONFIRMATION_CANCEL), "Cancel");
	}
	
	/*
	 * Verify that the three size of Primary Catalog Image display in an order list with thumbnail
	 */
	@Test
	public void TC051DAE_67(){
		LogReporter.addLog("ID : 051DAE_67 : Verify that the three size of Primary Catalog Image display in an order list with thumbnail" );
		/*
		  	1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select an product from Products table
			4. Verify that the 040D product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052D Model Variant Edit page is displayed.
			6. Click "Upload Custom Images" link
			VP. Verify that the "Add Custom Image File" is displayed.
			7. Upload three size of image successfully
			VP: The correct image thumbnail is displayed next to image name. 
			VP: Three size of Primary Catalog Image display display in vertical order list: 250x250 pixels, 500x500 pixels, 1000x1000 pixels."
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2.Navigate to Accessory page
		productControl.click(PageHome.linkAccessories);
		// 3.Select an accessory from accessories table
		productControl.selectAnAccessory();
		// 4. Click "Add New Variant" link from "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getVariantElementsInfo()), true);
		// 6. Click "Upload Custom Images" link
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_IMAGE);
		// 7. Upload three size of image successfully
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_1000, AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		/*
		 * Verify that The correct image thumbnail is displayed next to image name
		 */
		Assert.assertTrue(productControl.isImageUploaded(AddEditProductModel.IMAGE250_DISPLAY, AddEditProductModel.FileUpload.IMG_250_JPG.getName()));
		Assert.assertTrue(productControl.isImageUploaded(AddEditProductModel.IMAGE500_DISPLAY, AddEditProductModel.FileUpload.IMG_500_JPG.getName()));
		Assert.assertTrue(productControl.isImageUploaded(AddEditProductModel.IMAGE1000_DISPLAY, AddEditProductModel.FileUpload.IMG_1000_JPG.getName()));
		
		/*
		 Verify that the three size of Primary Catalog Image display in an order list
		 */
		productControl.isItemInorderList(AddEditProductModel.BRAND_LOGO_TABLE, AddEditProductModel.SIZE_LIST);
	}
	
	
	/*
	 * Verify that the lightbox style popup is displayed when clicking on each primay catalog image and they could be replaced by others.
	 */
	@Test
	public void TC051DAE_69(){
		LogReporter.addLog("ID : 051DAE_69 : Verify that the lightbox style popup is displayed when clicking on each primay catalog image and they could be replaced by others.");
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
			VP: Verify that the image file name and  file size is displayed below the catalog image file name link
			8. Click "Save" link
			VP: Verify that new variant is added successfully
			VP: The uploaded date and file size are displayed below the image name for each size of Primary Catalog Image
			9. Click "Edit" link
			10. Click on image thumbnail/file name of each size
			VP: A lightbox style popup with the picture showing in full size is displayed
			11. Delete the uploaded image by clicking on trashcan icon
			12. Upload another image
			New image could be upload successfully.
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select an product from Products table
		String producName = productControl.selectAnAccessory();
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), producName);
		// 5. Click "Add New Variant" link from "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.PRODUCT_TITLE));
		// 6. Click "Upload Custom Images" link
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_IMAGE);
		// 7. Upload three size of image successfully
		Hashtable<String,String> data = TestData.variantData(false, false, true);
		data.remove("save");
		productControl.addVariant(AddEditProductModel.getVariantHash(), data);
		/*
		 * VP: Verify that the image file name and  file size is displayed below the catalog image file name link
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_INFO_250).contains(AddEditProductModel.FileUpload.IMG_250_JPG.getName()));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_INFO_500).contains(AddEditProductModel.FileUpload.IMG_500_JPG.getName()));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_INFO_1000).contains(AddEditProductModel.FileUpload.IMG_1000_JPG.getName()));
		Assert.assertTrue(productControl.isElementDisplayVertically(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_1000, AddEditProductModel.CATALOG_IMAGE_FILE_SIZE_1000));
		Assert.assertTrue(productControl.isElementDisplayVertically(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_500, AddEditProductModel.CATALOG_IMAGE_FILE_SIZE_500));
		Assert.assertTrue(productControl.isElementDisplayVertically(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_250, AddEditProductModel.CATALOG_IMAGE_FILE_SIZE_250));
		// 8. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: Verify that new variant is added successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TITLE_NAME), data.get("name"));
		// Get the current date
		String date = DateUtil.getADateGreaterThanToday("dd MMM yyyy", 0);
		/*
		 * Verify that The uploaded date and file size are displayed below the image name for each size of Primary Catalog Image
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_INFO_250).contains(date));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_INFO_500).contains(date));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_INFO_1000).contains(date));
		Assert.assertTrue(productControl.isElementDisplayVertically(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_250, AddEditProductModel.CATALOG_IMAGE_FILE_SIZE_250));
		Assert.assertTrue(productControl.isElementDisplayVertically(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_500, AddEditProductModel.CATALOG_IMAGE_FILE_SIZE_500));
		Assert.assertTrue(productControl.isElementDisplayVertically(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_1000, AddEditProductModel.CATALOG_IMAGE_FILE_SIZE_1000));
		
		// 9. Click "Edit" link
		productControl.click(VariantInfo.EDIT_VARIANT);		

		// Click on image thumbnail 250
		productControl.click(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250);
		productControl.waitForElementClickable(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(AddEditProductModel.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_250_JPG.getName().replaceAll(".jpg", "")));
		// Close lightbox image
		productControl.click(AddEditProductModel.LIGHTBOX_CLOSE);
		productControl.waitForElementDisappear(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		// Click on image thumbnail 500
		productControl.click(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_500);
		productControl.waitForElementClickable(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(AddEditProductModel.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_500_JPG.getName().replaceAll(".jpg", "")));
		// Close lightbox image
		productControl.click(AddEditProductModel.LIGHTBOX_CLOSE);
		productControl.waitForElementDisappear(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		// Click on image thumbnail 500
		productControl.click(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000);
		productControl.waitForElementClickable(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(AddEditProductModel.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_1000_JPG.getName().replaceAll(".jpg", "")));
		// Close lightbox image
		productControl.click(AddEditProductModel.LIGHTBOX_CLOSE);
		productControl.waitForElementDisappear(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		
		// Click on file name 250
		productControl.click(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_250);
		productControl.waitForElementClickable(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(AddEditProductModel.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_250_JPG.getName().replaceAll(".jpg", "")));
		// Close lightbox image
		productControl.click(AddEditProductModel.LIGHTBOX_CLOSE);
		productControl.waitForElementDisappear(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		// Click on file name 500
		productControl.click(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_500);
		productControl.waitForElementClickable(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(AddEditProductModel.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_500_JPG.getName().replaceAll(".jpg", "")));
		// Close lightbox image
		productControl.click(AddEditProductModel.LIGHTBOX_CLOSE);
		productControl.waitForElementDisappear(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		// Click on file name 1000
		productControl.click(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_1000);
		productControl.waitForElementClickable(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		// VP: A lightbox style popup with the picture showing in full size is displayed
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(AddEditProductModel.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_1000_JPG.getName().replaceAll(".jpg", "")));
		// Close lightbox image
		productControl.click(AddEditProductModel.LIGHTBOX_CLOSE);
		productControl.waitForElementDisappear(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		
		// Delete the uploaded image by clicking on trashcan icon
		productControl.deleteAllUploadedImage(AddEditProductModel.IMAGE_CATALOG);
		// Upload image again
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.IMG_NAME[0]);
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.IMG_NAME[1]);
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_1000, AddEditProductModel.IMG_NAME[2]);
		/*
		 * Verify that new image is uploaded successfully
		 */
		Assert.assertTrue(productControl.isImageUploaded(AddEditProductModel.IMAGE250_DISPLAY, AddEditProductModel.IMG_NAME[0]));
		Assert.assertTrue(productControl.isImageUploaded(AddEditProductModel.IMAGE500_DISPLAY, AddEditProductModel.IMG_NAME[1]));
		Assert.assertTrue(productControl.isImageUploaded(AddEditProductModel.IMAGE1000_DISPLAY, AddEditProductModel.IMG_NAME[2]));
		
	}

	
	/*
	 * 	Verify that the ï¿½Companyï¿½ and ï¿½Brandsï¿½ combo boxes are editable when adding new product
	 */
	
	@Test
	public void TC051DAE_75(){
		LogReporter.addLog("ID : 051DAE_75 : Verify that the ï¿½Companyï¿½ and ï¿½Brandsï¿½ combo boxes are editable when adding new product");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click ï¿½Add Productï¿½ link
		*/	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click ï¿½Add Productï¿½ link
		productControl.click(ProductModel.ADD_PRODUCT);
		/*
		 * Verify that The ï¿½Companiesï¿½ and ï¿½Brandsï¿½ combo boxes are displayed and editabled
		 */
		Assert.assertTrue(productControl.isElementEditable(AddEditProductModel.COMPANY));
		Assert.assertTrue(productControl.isElementEditable(AddEditProductModel.BRAND));
	}
	
	/*
	 * 	Verify that the ï¿½Companyï¿½ combobox is readonly and ï¿½Brandsï¿½ combo is editable when editing existing products
	 */
	@Test
	public void TC051DAE_76(){
		LogReporter.addLog("ID : 051DAE_76: Verify that the ï¿½Companyï¿½ combobox is readonly and ï¿½Brandsï¿½ combo is editable when editing existing products");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select a product from products list
			4. Click ï¿½Editï¿½ link
		*/	
		/*
		 * PreCondition: Create new product
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.linkAccessories);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * *************************************************************
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select a product from products list
		productControl.selectAccessorybyName(data.get("name"));
		// 4. Click ï¿½Editï¿½ link
		productControl.click(ProductDetailModel.EDIT_MODE);
		/*
		 * Verify that The ï¿½Companiesï¿½ combobox is disabled
		 */
		Assert.assertFalse(productControl.isElementEditable(AddEditProductModel.COMPANY));
		//Delete product
		productControl.click(AddEditProductModel.CANCEL_PRODUCT);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the EAN/UPC are validated when adding new product
	 */
	@Test
	public void TC051DAE_77(){
		LogReporter.addLog("ID : 051DAE_77: Verify that the EAN/UPC are validated when adding new product");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click “Add Products” link
			4. Fill valid value into all required fields except EAN/UPC
			5. Fill value which including characters and digits into EAN/UPC fields
			6. Click “Save” link
			VP: Verify that new product is unable to save due to the error message inline of EAN field
			7. Enter a string larger than 13 digits into EAN field and more than 12 digits into UPC field
			8. Click “Save” link
			VP: Verify that there is an error message displayed next to the EAN /UPC fields
			9. Enter exactly 13 digits into EAN and 12 digits into UPC fields
			10. Click "Save" link
			New product is created successfully
		*/	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click ï¿½Add Productsï¿½ link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields except EAN/UPC
		// Fill value which including characters and digits into EAN/UPC field
		// 6. Click ï¿½Saveï¿½ link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		data.put("ean", "abc" + RandomStringUtils.randomAlphanumeric(10));
		data.put("upc", "xyz" + RandomStringUtils.randomAlphanumeric(9));
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * Verify that new product is unable to save due to the error message inline of EAN field
		 */
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.EanUpcErrorMessage.EAN_ONLY_DIGITS.getName()));
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.EanUpcErrorMessage.UPC_ONLY_DIGITS.getName()));
		// 7. Enter a string larger than 13 digits into EAN field and 12 digits into UPC field
		productControl.editData(AddEditProductModel.EAN, RandomStringUtils.randomNumeric(15));
		productControl.editData(AddEditProductModel.UPC, RandomStringUtils.randomNumeric(15));
		
		// 8. Click ï¿½Saveï¿½ link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: Verify that new product is unable to save due to the error message inline of EAN/UPC fields
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.EanUpcErrorMessage.EAN_EXCEED_CHAR_LIMIT.getName()));
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.EanUpcErrorMessage.UPC_EXCEED_CHAR_LIMIT.getName()));
		//	9. Enter a string exactly 13 digits into EAN/UPC fields
		productControl.editData(AddEditProductModel.EAN, RandomStringUtils.randomNumeric(13));
		productControl.editData(AddEditProductModel.UPC, RandomStringUtils.randomNumeric(12));
		//	10. Click ï¿½Saveï¿½
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		//	VP: New product is created successfully
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME),(data.get("name")));		
		//Teardown:
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
		
	/*
	 * Verify that the EAN/UPC are validated when adding new variant
	 * 
	 */
	@Test
	public void TC051DAE_81(){
		LogReporter.addLog("ID : 051DAE_81: Verify that the EAN/UPC are validated when adding new variant");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Select a product from products list
			4. Click "Add variant" link
			5. Fill valid value into all required fields except EAN/UPC
			6. Fill value which including characters and digits into EAN/UPC fields
			7. Click “Save” link
			VP: Verify that new product is unable to save due to the error message inline of EAN field
			8. Enter a string larger than 13 digits into EAN field and more than 12 digits into UPC field
			9. Click “Save” link
			VP: Verify that there is an error message displayed next to the EAN /UPC fields
			10. Enter exactly 13 digits into EAN and 12 digits into UPC fields
			11. Click "Save" link
			New variant is created successfully
		*/	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select a product from products list
		productControl.selectAnAccessory();
		// 4. Click "Add Variant" link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 5. Fill value which including characters and digits into EAN/UPC field
		// 6. Fill value which including characters and digits into EAN/UPC field
		// 7. Click ï¿½Saveï¿½ link
		Hashtable<String,String> data = TestData.variantData(false, false, false);
		data.put("ean", RandomStringUtils.randomAlphanumeric(13));
		data.put("upc", RandomStringUtils.randomAlphanumeric(12));
		productControl.addVariant(AddEditProductModel.getVariantHash(), data);
		/*
		 * Verify that new product is unable to save due to the error message inline of EAN field
		 */
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.EanUpcErrorMessage.EAN_ONLY_DIGITS.getName()));
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.EanUpcErrorMessage.UPC_ONLY_DIGITS.getName()));
		// 7. Enter a string larger than 13 digits into EAN field and 12 digits into UPC field
		productControl.editData(AddEditProductModel.EAN, RandomStringUtils.randomNumeric(15));
		productControl.editData(AddEditProductModel.UPC, RandomStringUtils.randomNumeric(15));
		
		// 8. Click ï¿½Saveï¿½ link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: Verify that new product is unable to save due to the error message inline of EAN/UPC fields
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.EanUpcErrorMessage.EAN_EXCEED_CHAR_LIMIT.getName()));
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.EanUpcErrorMessage.UPC_EXCEED_CHAR_LIMIT.getName()));
		//	9. Enter a string exactly 13 digits into EAN/UPC fields
		productControl.editData(AddEditProductModel.EAN, RandomStringUtils.randomNumeric(13));
		productControl.editData(AddEditProductModel.UPC, RandomStringUtils.randomNumeric(12));
		//	10. Click ï¿½Saveï¿½
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		//	New variant is created successfully
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.DISPLAYNAME_DEFAULT), data.get("name"));;
		
		//Teardown:
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/**
	 * Verify that user is able to upload tuning file, primary catalog image and marketing material again when the file being upload occurs error of product
	 */
	@Test
	public void TC051DAE_85(){
		LogReporter.addLog("ID: 051DAE_85: Verify that user is able to upload tuning file, primary catalog image and marketing material again when the file being upload occurs error of product");
		productControl.addErrorLog("not yet implemented");
		/*
		 	1. Log into DTS portal as a DTS admin
			2. Navigate to "Products" page
			3. Click “Add Products” link
			4. Upload a tuning file
			5. Corrupt the network while the file is uploading
			VP: Verify that the "File upload error" message and Retry link are displayed
			6. Connect network successfully
			7. Click "Save" link
			8. Try to upload another tuning file
			VP: The valid tuning file is uploaded successfully
			9. Repeat from step 4 to 7 for primary catalog image and marketing material files.
			Tuning file, primary catalog images and marketing material file could be uploaded again.
		 */
		Assert.assertTrue(false);
	}
	
	
	
	/*
	 * Verify that user is able to upload tuning file, primary catalog image and marketing material again when canceling the file being upload of product
	 */
	@Test
	public void TC051DAE_87() {
		LogReporter.addLog("ID : 051DAE_87: Verify that user is able to upload tuning file, primary catalog image"
				+ " and marketing material again when canceling the file being upload of product");
		/*
			1. Log into DTS portal as a DTS admin
			2. Navigate to "Products" page
			3. Click “Add Products” link
			4. Upload a tuning file
			5. Cancel uploading file
			VP: The message "File upload canceled" and Retry link is displayed
			6. Click "Retry" link
			7. Try to upload new tuning file again
			VP: New tuning file is uploaded successfully
			8. Repeat from step 4 to 7 for primary catalog images and marketing material files.
			Tuning file, primary catalog images and marketing material file could be uploaded again.
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click ï¿½Add Productsï¿½ link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Upload a tuning file
		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[0]);
		productControl.uploadFileInterupt(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 5. Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName());
		// VP: The message "File upload canceled" and Retry link is displayed
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.TUNING_UPLOAD_MESSAGE)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_CANCELED.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_TUNING));
		// 6. Click "Retry" link
		// 7. Try to upload new tuning file again
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(productControl.isTunningUploaded(AddEditProductModel.UPLOADED_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName()));
		
		// Upload three size of primary catalog image
		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[1]);
		productControl.uploadFileInterupt(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName());
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_250)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_CANCELED.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_IMAGE_250));
		// 6. Click "Retry" link
		// 7. Try to upload another catalog image again
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_IMAGE_250, AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		/*
		 * Verify that Three size of catalog image are uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_250)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		
		// Upload a marketing material file
		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[4]);
		productControl.uploadFileInterupt(AddEditProductModel.ADD_MARKETING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName());
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.MARKETING_UPLOAD_MESSAGE)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_CANCELED.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_MARKETING));
		// Click "Retry" link
		// Try to upload new marketing material again
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_MARKETING, AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.MARKETING_UPLOAD_MESSAGE)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		
	}
	
	/*
	 * Verify that user is able to upload wrong dimension for primary catalog image of product
	 */
	@Test
	public void TC051DAE_89() {
		LogReporter.addLog("ID : 051DAE_89: Verify that user is able to upload wrong demension for primary catalog image of product");
		/*
			1. Log into DTS portal as a DTSadmin
			2. Navigate to "Products" page
			3. Click ï¿½Add Productsï¿½ link
			4. Upload a wrong image demension for 250x250 primary catalog image section
			5. Upload a wrong image demension for 500x500 primary catalog image section
			6. Upload a wrong image demension for 1000x100 primary catalog image section
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click ï¿½Add Productsï¿½ link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Upload a wrong image demension for 250x250 primary catalog image section
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		// 5. Upload a wrong image demension for 500x500 primary catalog image section
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		// 6. Upload a wrong image demension for 1000x100 primary catalog image section
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_1000, AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		/*
		 * Demenson of Three size of primary catalog image are automatically
		 * rezied and upload successfully and the message
		 * "Automaticall resized. Original XX x YY px" is displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_250)
				.contains(AddEditProductModel.UploadFileMessage.FILE_1000_PX.getName()));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_500)
				.contains(AddEditProductModel.UploadFileMessage.FILE_1000_PX.getName()));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_1000)
				.contains(AddEditProductModel.UploadFileMessage.FILE_250_PX.getName()));
	}
	
	/*
	 * Verify that user is able to upload wrong demension for primary catalog image of variant
	 */
	@Test
	public void TC051DAE_96() {
		LogReporter.addLog("ID : 051DAE_96: Verify that user is able to upload wrong demension for primary catalog image of variant");
		/*
			1. Log into DTS portal as a DTS admin
			2. Navigate to "Products" page
			3. Select a product from products table
			4. Click "Add new variant" link
			5. Upload a wrong image demension for 250x250 primary catalog image section
			6. Upload a wrong image demension for 500x500 primary catalog image section
			7. Upload a wrong image demension for 1000x100 primary catalog image section
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select a product from products table
		productControl.selectAnAccessory();
		// 4. Click "Add new variant" link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// Click Upload custom image link
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_IMAGE);
		// 5. Upload a wrong image demension for 250x250 primary catalog image section
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		// 6. Upload a wrong image demension for 500x500 primary catalog image section
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		// 7. Upload a wrong image demension for 1000x100 primary catalog image section
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_1000, AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		/*
		 * Demenson of Three size of primary catalog image are automatically
		 * rezied and upload successfully and the message
		 * "Automaticall resized. Original XX x YY px" is displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_250)
				.contains(AddEditProductModel.UploadFileMessage.FILE_1000_PX.getName()));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_500)
				.contains(AddEditProductModel.UploadFileMessage.FILE_1000_PX.getName()));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_1000)
				.contains(AddEditProductModel.UploadFileMessage.FILE_250_PX.getName()));
	}
	
	/*
	 * Verify that user is able to upload tuning file, primary catalog image and marketing material again when canceling the file being upload of variant
	 */
	@Test
	public void TC051DAE_98() {
		LogReporter.addLog("ID : 051DAE_98: Verify that user is able to upload tuning file, primary catalog "
				+ "image and marketing material again when canceling the file being upload of variant");
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select a product from products table
		productControl.selectAnAccessory();
		// 4. Click "Add new variant" link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 5. Upload a marketing material file
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_TUNNING);
		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[0]);
		productControl.uploadFileInterupt(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 5. Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName());
		// VP: The message "File upload canceled" and Retry link is displayed
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.TUNING_UPLOAD_MESSAGE)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_CANCELED.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_TUNING));
		// 6. Click "Retry" link
		// 7. Try to upload new tuning file again
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(productControl.isTunningUploaded(AddEditProductModel.UPLOADED_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName()));
		
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_IMAGE);
		// Upload three size of primary catalog image
		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[1]);
		productControl.uploadFileInterupt(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName());
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_250)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_CANCELED.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_IMAGE_250));
		// 6. Click "Retry" link
		// 7. Try to upload another catalog image again
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_IMAGE_250, AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		/*
		 * Verify that Three size of catalog image are uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_250)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		
		// Upload a marketing material file
		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[4]);
		productControl.uploadFileInterupt(AddEditProductModel.ADD_MARKETING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName());
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.MARKETING_UPLOAD_MESSAGE)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_CANCELED.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_MARKETING));
		// Click "Retry" link
		// Try to upload new marketing material again
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_MARKETING, AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.MARKETING_UPLOAD_MESSAGE)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		
	}
	
	
}
