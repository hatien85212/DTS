package com.dts.adminportal.partner.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddBrand;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.CompanyInfo;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.TestData;


public class PartnerProduct051PAccessoryEdit extends BasePage{
	
	@Override
	protected void initData() {
	}	

//	@BeforeMethod
//	public void loginBeforeTest() {
//		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//	}
	/*
	 * Verify that the Adding New Model shows up all necessary fields properly.
	 */
	@Test
	public void TC051PAE_01(){
		productControl.addLog("ID : TC051PAE_01 : Verify that the Adding New Model shows up all necessary fields properly.");
		/*
		Pre-condition: Partner user has "Add and manage accessories" rights.
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		VP: 051 product Edit page is showed up with: DTS Tracking Number, Brand dropdown, Model Name text field, Languages dropdown, Model Number, EAN/UPC text field, Type dropdown, Input Specifications table, Release date calendar, Aliases text field, Description text field, Tuning upload file link, Headphone:X Tuning Rating label, Product Catalog Images table and Marketing Material table but the �Company� combobox is hidden
		VP: the  DTS Tracking Number is already generated and it is unediable.
		VP: he Headphone: X Tuning Rating is uneditable.
		VP: Type dropdown contains "Earbuds", "In-Ear", "On-Ear" and "Over-Ear" items.
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		productControl.click(ProductModel.ADD_PRODUCT);
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
		 * but not for company combobox
		 */
			
//		Assert.assertEquals(true, productControl.existsElement(AddEditProductModel.getProductElementFieldIds()));
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.COMPANY));
		
		 //DTS Tracking Number is already generated and it is un-editable.
		String trackingNumber = productControl.getTextByXpath(AddEditProductModel.TRACKING_ID);
		Assert.assertTrue(trackingNumber.length() > 0);
		// Check DTS Tracking Number un-editable
		Assert.assertFalse(productControl.isElementEditable(AddEditProductModel.TRACKING_ID));
		
		//VP: the Headphone: X Tuning Rating is uneditable
		Assert.assertFalse(productControl.isElementEditable(AddEditProductModel.TUNING_RATING_DISABLE));
		
		// VP: Type dropdown contains "Earbuds", "In-Ear", "On-Ear" and "Over-Ear" items.
		productControl.click(AddEditProductModel.MODEL_TYPE);
		// Type dropdown contains "Earbuds", "In-Ear", "On-Ear" and "Over-Ear" items.
		ArrayList<String> options = productControl.getAllComboboxOption(AddEditProductModel.MODEL_TYPE);
		Boolean isCorrectType = ListUtil.containsAll(options, AddEditProductModel.getProductType());
		Assert.assertTrue(isCorrectType);
	
		
	}
	
	/*
	 * Verify that the tuning file is validated after uploading.
	 */
	@Test
	public void TC051PAE_02(){
		productControl.addLog("ID : TC051PAE_02 : Verify that the tuning file is validated after uploading.");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Upload an invalid tuning file
			VP: An error message is displayed and the invalid tuning file is not added.
			5. Upload a hpxtt tuning file
			VP:  valid tuning hpxtt file  could be uploaded successfully.
			6. Delete this tuning file
			7. Upload a valid tuning zip file
			VP: A valid tuning zip file is uploaded successfully and it's called "Partner Tuning".	
		*/
		/*
		 * PreCondition: Create new product
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Try to upload an invalid tuning file
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_Invalid.getName());
		//VP: An error message is displayed and the invalid tuning file is not added.		
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.TUNING_UPLOAD_MESSAGE).trim().toString().contains(AddEditProductModel.UploadFileMessage.INVALID_FILE.getName()));
		productControl.click(AddEditProductModel.RETRY_UPLOAD_TUNING);
		//5. Upload a hpxtt tuning file
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		//VP:  valid tuning hpxtt file  could be uploaded successfully.
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.TUNING_UPLOAD_MESSAGE).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		//6. Delete this tuning file
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		//7. Upload a valid zip file
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_ZIP.getName());
		//VP:A valid zip tuning file is uploaded successfully and it's called "Partner Tuning".
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.TUNING_UPLOAD_MESSAGE).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		WebElement uploadedTuning = driver.findElement(By.xpath(AddEditProductModel.UPLOADED_TUNING)).findElement(By.className("mauploadTuning"));
		Assert.assertEquals(productControl.getTextByXpath(uploadedTuning), "Partner Tuning");
		
	}
	
	/*
	 Verify that the EAN/UPC is validated for the already exist EAN/UPC code.
	 */
	@Test
	public void TC051PAE_03(){
		productControl.addLog("ID : TC051PAE_03 : Verify that the EAN/UPC is validated for the already exist EAN/UPC code"
				+ "Pre-condition: Partner user has 'Add and manage accessories' rights." );
		productControl.addLog("Failed by bug PDPP-467: 051D Accessory Edit : The warning message is not displayed when adding duplicate EAN/UPC");
		/*
		1. Log into DTS portal as a partner admin
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		4. Fill value into EAN/UPC text field which is saved for another accessory.
		5. Click "Save" link
		*/
		loginControl.login(DTS_USER, DTS_PASSWORD);
		productControl.click(PageHome.linkAccessories);
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> data= TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		productControl.logout();
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Preconditions : Add an accessory and fill the EAN/UPC field with any code (e.g 123456)  
		
		//2. Navigate to Accessory page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);		
		// 3. Click add accessory link
		productControl.click(ProductModel.ADD_PRODUCT);
		
		Hashtable<String,String> data1= TestData.appDeviceDraft();
		data1.put("ean", data.get("ean"));
		data1.put("upc",data.get("upc"));
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data1);
		//Verify that the EAN/UPC is validated for the already exist EAN/UPC code.
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.EAN_ERROR_MESSAGE), AddEditProductModel.EanUpcErrorMessage.EAN_UNIQUE.getName());
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.UPC_ERROR_MESSAGE), AddEditProductModel.EanUpcErrorMessage.UPC_UNIQUE.getName());
		
		//Teardown:
		//Delete product:
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		
	}
	
	/*
	 Verify that the default and external mode name language are validated correctly.
	 */
	@Test
	public void TC051PAE_04(){
		productControl.addLog("ID : TC051PAE_04 : Verify that the default and external mode name language are validated correctly.");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Click "Add product" link
			VP: The "- Select -" item is the default value of Model Name language dropdown, the hint text "Inherits default" is displayed in external model name text field.
			4. List out the Model Name language dropdown
			VP:  The Model Name language dropdown contains items: - Select -, Chinese - Simplified, Chinese - Traditional, French, German, Italian, Japanese, Korean, Russian and Spanish.
			5. Set language dropdown to another item other than "Select"
			VP: Another language dropdown is displayed
			6. Select the same item for second language dropdown
			VP: A warning message is displayed which notifying for the duplicating of languages.
			7. Set 1st language dropdown to "Select"
			VP: The external model name language text field is disabled.
			8. Type value into 2nd model name input field
			9. Click on Trashcan icon
			VP: The language dropdown is restore to default "Select" value and the input field is cleared  
			10. Leave the Default model name empty
			11. Click "Save" link
		*/
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click add accessory link
		productControl.click(ProductModel.ADD_PRODUCT);
		//VP: The "- Select -" item is the default value of Model Name language dropdown, the hint text "Inherits default" is displayed in external model name text field.
		Assert.assertEquals(productControl.getItemSelected(AddEditProductModel.LANGUAGE_DROPDOWN(0)), AddEditProductModel.ProductLanguage.SELECT.getName());
		
		//4. List out the Model Name language dropdown
//		productControl.click(AddEditProductModel.LANGUAGE_DROPDOWN(0));
		// VP:  The Model Name language dropdown contains items: - Select -, Chinese - Simplified, Chinese - Traditional, French, German, Italian, Japanese, Korean, Russian and Spanish.
		ArrayList<String> options = productControl.getAllComboboxOption(AddEditProductModel.LANGUAGE_DROPDOWN(0));
		Assert.assertTrue(ListUtil.containsAll(options, AddEditProductModel.ProductLanguage.getNames()));
		
		//5. Set language dropdown to another item other than "Select"
		// get number of language language dropdown is displayed before select another language
		int before = productControl.getNumElementDisplayedByText(AddEditProductModel.ModelName[2]);
		System.out.print("before" +before);
		productControl.selectOptionByName(AddEditProductModel.LANGUAGE_DROPDOWN(0), AddEditProductModel.ProductLanguage.CHINESE_SIM.getName());
		//VP: Another language dropdown is displayed
		int after = productControl.getNumElementDisplayedByText(AddEditProductModel.ModelName[2]);
		System.out.print("after" + after);
		Assert.assertEquals(after , before +1);
		
		//6. Select the same item for second language dropdown
		productControl.selectOptionByName(AddEditProductModel.LANGUAGE_DROPDOWN(2), AddEditProductModel.ProductLanguage.CHINESE_SIM.getName());
		//VP: A warning message is displayed which notifying for the duplicating of languages.
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.LANG_ERROR_MSG(1)),
				AddEditProductModel.DUPLICATE_WARNING_MESSAGE);
		
		// 7. Set 1st language dropdown to "Select"
		productControl.selectOptionByName(AddEditProductModel.LANGUAGE_DROPDOWN(1), AddEditProductModel.ProductLanguage.SELECT.getName());
		//VP: The external model name language text field is disabled.
		Assert.assertFalse(productControl.isElementEditable(AddEditProductModel.OTHER_LANGUAGE_NAME(1)));
		
		//8. Type value into 2nd model name input field
		productControl.editData(AddEditProductModel.OTHER_LANGUAGE_NAME(2), RandomStringUtils.random(5));
		//9. Click on Trashcan icon
		productControl.click(AddEditProductModel.TRASH_ICON(2));
		//VP: The language dropdown is restore to default "Select" value and the input field is cleared  
		Assert.assertEquals(productControl.getItemSelected(AddEditProductModel.LANGUAGE_DROPDOWN(1)), AddEditProductModel.ProductLanguage.SELECT.getName());
		Assert.assertEquals(productControl.getAtributeValue(AddEditProductModel.OTHER_LANGUAGE_NAME(1), "placeholder"), 
				AddEditProductModel.ModelName[2]);
	
		// 10. Leave the Default model name empty
		// 11. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		//VP: A warning message is displayed which notifying the model name is empty.
		String msgErr = productControl.getTextByXpath(AddEditProductModel.MODEL_NAME_ERR);
		Assert.assertEquals(msgErr, AddEditProductModel.ModelName[0]);
	}
	
	/*
	 * Verify that the three size of Primary Catalog Image are validated correctly.
	 */
	@Test
	public void TC051PAE_05() throws InterruptedException{
		productControl.addLog("ID : TC051PAE_05 : Verify that the three size of Primary Catalog Image are validated correctly.");
	   /*
			Pre-condition: Partner user has "Add and manage Products" rights.
			
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Click "Add product" link
			4. Fill valid value into all required fields
			VP: Three size of Primary Catalog Image display display in order: 250x250 pixels, 500x500 pixels, 1000x1000 pixels.
			5. Upload image for each size of Primary Catalog Image successfully
			VP: The correct image thumbnail is displayed next to image name.
			6. Click on image thumbnail of each size
			VP: A lightbox style popup with the picture showing in full size is displayed
			7. Click on file name of each size
			VP: A lightbox style popup with the picture showing in full size is displayed
			8. Delete the uploaded image by clicking on trashcan icon
			9. Upload image again
			VP: Verify that new image is uploaded successfully
			10. Click "Save" link
			11. Click "Edit" link in product detail page
		*/
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		data.remove("save");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		// Verify that Three size of Primary Catalog Image display display in order: 250x250 pixels, 500x500 pixels, 1000x1000 pixels.
		Assert.assertTrue(productControl.isImageDisplayInOrder(AddEditProductModel.IMAGE_CATALOG));
		
		//Upload image for each size of Primary Catalog Image successfully
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.IMG_NAME[0]);
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.IMG_NAME[1]);
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_1000, AddEditProductModel.IMG_NAME[2]);
		// VP: The correct image thumbnail is displayed next to image name. 
		Assert.assertTrue(productControl.isElementDisplayHorizontal(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250, AddEditProductModel.CATALOG_IMAGE_UPLOAD_INFO_250));
		Assert.assertTrue(productControl.isElementDisplayHorizontal(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_500, AddEditProductModel.CATALOG_IMAGE_UPLOAD_INFO_500));
		Assert.assertTrue(productControl.isElementDisplayHorizontal(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000, AddEditProductModel.CATALOG_IMAGE_UPLOAD_INFO_1000));
		
		// 5. Click on image thumbnail of each size
		//Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on inage thumbnail
		//Verify lightbox image is displayed for 250x250
		productControl.click(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250);
		//wait for ligtbox display
		productControl.waitForElementClickable(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(AddEditProductModel.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.IMG_NAME[0].replaceAll(".jpg", "")));
		
		//Close the lightbox fot 250x250
		productControl.click(AddEditProductModel.LIGHTBOX_CLOSE);
		Thread.sleep(3000);

		//Verify lightbox image is displayed for 500x500	
		productControl.click(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_500);
		//wait for ligtbox display
		productControl.waitForElementClickable(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(AddEditProductModel.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.IMG_NAME[1].replaceAll(".jpg", "")));
		
		//Close the lightbox fot 500x500
		productControl.click(AddEditProductModel.LIGHTBOX_CLOSE);
		Thread.sleep(3000);
		
		//Verify lightbox image is displayed for 1000x1000
		productControl.click(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000);
		//wait for ligtbox display
		productControl.waitForElementClickable(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(AddEditProductModel.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.IMG_NAME[2].replaceAll(".jpg", "")));
		productControl.click(AddEditProductModel.LIGHTBOX_CLOSE);
		
		// 5. Click on file name of each size
		//Verify that a lightbox style popup with the picture showing in full size is displayed when clicking on file name
		//Verify lightbox image is displayed for 250x250
		productControl.click(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_250);
		//wait for ligtbox display
		productControl.waitForElementClickable(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(AddEditProductModel.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.IMG_NAME[0].replaceAll(".jpg", "")));
		
		//Close the lightbox fot 250x250
		productControl.click(AddEditProductModel.LIGHTBOX_CLOSE);
		Thread.sleep(3000);

		//Verify lightbox image is displayed for 500x500	
		productControl.click(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_500);
		//wait for ligtbox display
		productControl.waitForElementClickable(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(AddEditProductModel.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.IMG_NAME[1].replaceAll(".jpg", "")));
		
		//Close the lightbox fot 500x500
		productControl.click(AddEditProductModel.LIGHTBOX_CLOSE);
		Thread.sleep(3000);
		
		//Verify lightbox image is displayed for 1000x1000
		productControl.click(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_1000);
		//wait for ligtbox display
		productControl.waitForElementClickable(AddEditProductModel.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(productControl.getAtributeValue(AddEditProductModel.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.IMG_NAME[2].replaceAll(".jpg", "")));
		productControl.click(AddEditProductModel.LIGHTBOX_CLOSE);
		
		// 5. Delete the uploaded image by clicking on trashcan icon
		productControl.deleteAllUploadedImage(AddEditProductModel.IMAGE_CATALOG);
		// 6. Upload image again
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.IMG_NAME[0]);
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.IMG_NAME[1]);
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_1000, AddEditProductModel.IMG_NAME[2]);
		/*
		 * Verify that new image is uploaded successfully
		 */
		Assert.assertTrue(productControl.isElementPresent(driver.findElement(
				By.xpath(AddEditProductModel.IMAGE250_DISPLAY)).findElement(
				By.tagName("img"))));
		Assert.assertTrue(productControl.isElementPresent(driver.findElement(
				By.xpath(AddEditProductModel.IMAGE500_DISPLAY)).findElement(
				By.tagName("img"))));
		Assert.assertTrue(productControl.isElementPresent(driver.findElement(
				By.xpath(AddEditProductModel.IMAGE1000_DISPLAY)).findElement(
				By.tagName("img"))));

		// 10. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// 11. Click "Edit" link in product detail page
		productControl.click(ProductDetailModel.EDIT_MODE);
		//VP: The product edit page is displayed and the uploaded date and file size are displayed below the image name for each size of Primary Catalog Image.
		Assert.assertTrue(productControl.isElementDisplayVertically(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_250, AddEditProductModel.CATALOG_IMAGE_FILE_SIZE_250));
		Assert.assertTrue(productControl.isElementDisplayVertically(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_500, AddEditProductModel.CATALOG_IMAGE_FILE_SIZE_500));
		Assert.assertTrue(productControl.isElementDisplayVertically(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_1000, AddEditProductModel.CATALOG_IMAGE_FILE_SIZE_1000));
		
		//Teardown: Delete product
		productControl.deleteAccessorybyName(data.get("name"));
		
	}
	
	/*
	 * Verify that the tuning file, primary catalog images and marketing material could be uploaded by dragging and dropping.
	 */
	@Test
	public void TC051PAE_06(){
		productControl.addLog("ID : TC051PAE_06 : Verify that the tuning file, primary catalog images and marketing material could be uploaded by dragging and dropping.");
		productControl.addErrorLog("Drag drop file failed");
	   /*
			Pre-condition: Partner user has "Add and manage Products" rights.
			
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Click "Add product" link
			4. Try to upload file from Drag and Drop area of Tuning, primary catalog image and Marketing Material.
			VP: New tuning file, three size of primary catalog image and  marketing material file is uploaded successfully
		*/
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		productControl.click(ProductModel.ADD_PRODUCT);
		//4.  Drag and drop for tuning file
		productControl.dragDropFile(AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.TUNING_UPLOAD_MESSAGE).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		Assert.assertTrue(productControl.isTunningUploaded(AddEditProductModel.UPLOADED_TUNING,AddEditProductModel.FileUpload.Tuning_HPXTT.getName()));
		
		
		// Drag and drop for primary catalog image
		//Three size of primary catalog image are upload successfully.					
		//250x250
		productControl.dragDropFile(AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		//500x500
		productControl.dragDropFile(AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		//1000x1000
		productControl.dragDropFile(AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		// Drap and drop a file into Marketing Material area
		productControl.dragDropFile(AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());

		//Verify that New marketing material file is uploaded successfully
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.MARKETING_FILE_CONTAINER));
		Assert.assertTrue(productControl.isMarketingUploaded(AddEditProductModel.MARKETING_FILE_CONTAINER,AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName()));
				
		
	}
	
	
	/*
	 * Verify that the Input Specifications section is validated when adding new product.
	 */
	@Test
	public void TC051PAE_07(){
		productControl.addLog("ID : TC051PAE_07: Verify that the Input Specifications section is validated when adding new product.");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Click "Add product" link
			VP: The Input Specifications section inludes "Connection Type" and "Support Input Channels" module.
			4. Fill valid value into all required fields
			5. Select a connection type but do not select the content channel capability
			VP: Verify that the similar Supported Input Channels is enabled.
			6. Click "Save" link
			VP: An error message displays which notifying user to select at least one Supported Input Channels value
			7. Tick to uncheck the connection type
			VP: The similar Content Channel Capability is disabled
			8. Click "Save" link	
			VP: An error message displays which notifying user to select at least one connection type
		*/
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		productControl.click(ProductModel.ADD_PRODUCT);
		/*
		 * The Input Specifications section inludes 
		 * "Connection Type" and
		 * "Support Input Channels" module.
		 */
		ArrayList <String> header=productControl.getInputSpecificationHeader(AddEditProductModel.INPUT_SPECIFICATIONS);
		Assert.assertTrue(ListUtil.containsAll(header, AddEditProductModel.inputSpecificationHeader));
		//4. Fill valid value into all required fields
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		data.remove("wired");
		data.remove("save");
		System.out.println("<----------------------->");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);		
		// 5. Select a connection type but do not select the content channel capability
		System.out.println("Select Input Channel: Wired Container");
		productControl.selectInputChannel(AddEditProductModel.WIRED_CONTAINER, "none");
		System.out.println("Select Input Channel: Bluetooth Container");
		productControl.selectInputChannel(AddEditProductModel.BLUETOOTH_CONTAINER, "none");
		
		// VP: Verify that the similar Supported Input Channels is enabled.
		Assert.assertTrue(productControl.isElementEditable(AddEditProductModel.CONTENT_CHANNEL_WIRED_DROPDOWN));
		Assert.assertTrue(productControl.isElementEditable(AddEditProductModel.CONTENT_CHANNEL_BLUET_DROPDOWN));
		//6. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: An error message displays which notifying user to select at least one Supported Input Channels value
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.WIRED_MESSAGE), AddEditProductModel.INPUT_CHANNEL_ERROR_STRING);
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.BLUETOOTH_MESSAGE), AddEditProductModel.INPUT_CHANNEL_ERROR_STRING);
		// 7. Tick to uncheck the connection type
		productControl.uncheckACheckbox(AddEditProductModel.WIRED_CHECKBOX);
		productControl.uncheckACheckbox(AddEditProductModel.BLUETOOTH_CHECKBOX);
		// VP: The similar Content Channel Capability is disabled
		Assert.assertFalse(productControl.isElementEditable(AddEditProductModel.CONTENT_CHANNEL_WIRED_DROPDOWN));
		Assert.assertFalse(productControl.isElementEditable(AddEditProductModel.CONTENT_CHANNEL_BLUET_DROPDOWN));
		// 8. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		//An error message displays which notifying user to select at least one connection type
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CONNECTION_TYPE_MESSAGE).contains(AddEditProductModel.CONNECTION_TYPE_STRING));
	}
	
	
	/*
	 * Verify that the �Brands� combo box is editable if the company only has multiple brands and partner user have privilege to create multiple brands when adding new product
	 */
	@Test
	public void TC051PAE_08(){
		productControl.addLog("ID : TC051PAE_08: Verify that the �Brands� combo box is editable if the company only has multiple brands and partner user have privilege to create multiple brands when adding new product");
		/*
		Pre-condition: The company has multiple brands and partner user has privilege to create multiple brands
		1. Log into DTS portal as a partner admin
		2. Navigate to "Products" page
		3. Click "Add Product" link
		*/
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		/*
		 * Verify that The  �Brands�  combobox is displayed and editable
		 */
		Assert.assertTrue(productControl.isElementEditable(AddEditProductModel.BRAND));
	}
	
	/*
	 * Verify that the �Brands� combo box is not displayed if the company only has one brand when adding new product
	 */
	@Test
	public void TC051PAE_09(){
		productControl.addLog("ID : TC051PAE_09	: Verify that the �Brands� combo box is not displayed if the company only has one brand when adding new product");
		/*
			Pre-condition: The company only has one brand
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Click "Add Product" link
		*/
		/*
		 * Pre-condition: The company only has one brand
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to Company page
		productControl.click(PageHome.LINK_PARTNER_COMPANY);
		// Delete all brands except brand "TestCorp-Brand1"
		companyControl.deleteAllBrandExceptOne(CompanyInfo.BRAND_LIST, PARTNER_BRAND_NAME_1);
		/*
		 * ******************************************************************
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		/*
		 * Verify that The  �Company� and �Brands�  comboboxes are not displayed
		 */
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.COMPANY));
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.BRAND));
		/*
		 *  Post-condition: Create brand "TestCorp-Brand2"
		 */
		// Navigate to company page
		productControl.click(PageHome.LINK_PARTNER_COMPANY);
		// Click add new brand link
		productControl.click(CompanyInfo.ADD_BRAND);
		// Create brand
		Hashtable<String,String> data = TestData.brandDraft();
		data.put("name", "TestCorp-Brand2");
		companyControl.addBrand(AddBrand.getHash(), data);
		/*
		 * *******************************************************************
		 */
	}
	
	/*
	 * Verify that the �Brands� combo box is not displayed if the partner user has privilege to create one brand when adding new product
	 */
	@Test
	public void TC051PAE_10(){
		productControl.addLog("ID : TC051PAE_10	 : Verify that the �Brands� combo box is not displayed if the partner user has privilege to create one brand when adding new product");
		/*
		Pre-condition: The company has multiple brand but the partner user only has privilege to create one brand
		1. Log into DTS portal as a partner admin
		2. Navigate to "Products" page
		3. Click "Add Product" link
		*/
		
		/*
		 * Pre-condition: The company has multiple brand but the partner user only has privilege to create one brand
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to User page
		productControl.click(PageHome.LINK_PARTNER_USER);
		// Select an exist pattner user
		userControl.selectUserInfoByEmail(PARTNER_USER);
		// Enable "Add and manage accessories" privilege with has one brand privilege
		productControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, PARTNER_BRAND_NAME_1);
		productControl.click(AddUser.SAVE);
		// Logout user
		productControl.logout();
		// Log in DTS portal as partner user above
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		/*
		 * End Pre-condition
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		/*
		 * The  �Company� and �Brands�  comboboxes are not displayed
		 */
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.COMPANY));
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.BRAND));
		
		//Teardown: Reenable all user brand privilege
		productControl.logout();
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		productControl.click(PageHome.LINK_PARTNER_USER);
		// Select an exist pattner user
		userControl.selectUserInfoByEmail(PARTNER_USER);
		// Enable "Add and manage accessories" privilege with has one brand privilege
		productControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		productControl.click(AddUser.SAVE);
	}
	
	/*
	 * Verify that the �Brands� combo box is editable if the company only has multiple brands and partner user have privilege to create multiple brands when editing existing product
	 */
	@Test
	public void TC051PAE_11(){
		productControl.addLog("ID : TC051PAE_11	 : Verify that the �Brands� combo box is editable if the company only has multiple brands and partner user have privilege to create multiple brands when editing existing product");
		/*
			Pre-condition: The company has multiple brands and partner user has privilege to create multiple brands
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Select a product from products list
			4. Click �Edit� link
		*/
		/*
		 * PreCondition: Create new product
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create new product
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * ********************************************
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select a product from products list
		productControl.selectAccessorybyName(data.get("name"));
		// 4. Click �Edit� link
		productControl.click(ProductDetailModel.EDIT_MODE);
		/*
		 * Verify that The  �Brands�  combobox is displayed and editable
		 */
		Assert.assertTrue(productControl.isElementEditable(AddEditProductModel.BRAND_LIST));
		// Delete product
		productControl.click(AddEditProductModel.CANCEL_PRODUCT);
		productControl.doDelete(ProductDetailModel.DELETE);
	}	
	
	/*
	 * Verify that the �Brands� combo box is displayed as read only if the company only has one brand when editing existing product
	 */
	@Test
	public void TC051PAE_12(){
		productControl.addLog("ID : TC051PAE_12	: Verify that the �Brands� combo box is displayed as read only if the company only has one brand when editing existing product");
		/*
			Pre-condition: The company only has one brand
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Select a product from products list
			4. Click �Edit� link
		*/
		/*
		 * Pre-condition: The company only has one brand
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to Company page
		productControl.click(PageHome.LINK_PARTNER_COMPANY);
		// Delete all brands except brand "TestCorp-Brand1"
		companyControl.deleteAllBrandExceptOne(CompanyInfo.BRAND_LIST, PARTNER_BRAND_NAME_1);
		// Navigate to Product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create new product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		/*
		 * ******************************************************************
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select a product from products list
		productControl.selectAccessorybyName(dataProduct.get("name"));
		// 4. Click �Edit� link
		productControl.click(ProductDetailModel.EDIT_MODE);
		/*
		 * Verify that The  �Brands�  combobox is displayed as read only
		 */
		Assert.assertFalse(productControl.isElementEditable(AddEditProductModel.BRAND));
		// Delete product
		productControl.click(AddEditProductModel.CANCEL_PRODUCT);
		productControl.doDelete(ProductDetailModel.DELETE);
		/*
		 *  Post-condition: Create brand "TestCorp-Brand2"
		 */
		// Navigate to company page
		productControl.click(PageHome.LINK_PARTNER_COMPANY);
		// Click add new brand link
		productControl.click(CompanyInfo.ADD_BRAND);
		// Create brand
		Hashtable<String,String> data = TestData.brandDraft();
		data.put("name", PARTNER_BRAND_NAME_2);
		companyControl.addBrand(AddBrand.getHash(), data);
		/*
		 * *******************************************************************
		 */
	}
		
	/*
	 * Verify that the �Brands� combo box is displayed as read if the partner user has privilege to manage one brand when editing existing product
	 */
	@Test
	public void TC051PAE_13(){
		productControl.addLog("ID : TC051PAE_13	 : Verify that the �Brands� combo box is displayed as read if the partner user has privilege to manage one brand when editing existing product");
		/*
			Pre-condition: The company has multiple brand but the partner user only has privilege to create one brand
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Select a product from products list which under mange of partner user
			4. Click �Edit� link
		*/
		/*
		 * Pre-condition: The company has multiple brand but the partner user only has privilege to create one brand
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to User page
		productControl.click(PageHome.LINK_PARTNER_USER);
		// Select an exist pattner user
		userControl.selectUserInfoByEmail(PARTNER_USER);
		// Enable "Add and manage accessories" privilege with has one brand privilege
		productControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, PARTNER_BRAND_NAME_1);
		productControl.click(AddUser.SAVE);
		// Navigate to Product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		productControl.click(ProductModel.ADD_PRODUCT);
		// Create new product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		// Logout user
		productControl.logout();
		// Log in DTS portal as partner user above
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		/*
		 * End Pre-condition
		 */
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Product" link
		productControl.selectAccessorybyName(dataProduct.get("name"));
		// 4. Click �Edit� link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// Verify that The  �Brands�  combobox is displayed as read only.
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.COMPANY));
		Assert.assertFalse(productControl.isElementEditable(AddEditProductModel.BRAND));
		// Delete product
		productControl.click(AddEditProductModel.CANCEL_PRODUCT);
		productControl.doDelete(ProductDetailModel.DELETE);
		/*
		 * Teardown: Re-enable all user brand privilege
		 */
		productControl.logout();
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		productControl.click(PageHome.LINK_PARTNER_USER);
		// Select an exist pattner user
		userControl.selectUserInfoByEmail(PARTNER_USER);
		// Enable "Add and manage accessories" privilege with has one brand privilege
		productControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		productControl.click(AddUser.SAVE);
	}
	
	/*
	 * Verify that the EAN/UPC is validated when adding new product
	 */
	@Test
	public void TC051PAE_14(){
		productControl.addLog("ID : TC051PAE_14: Verify that the EAN/UPC is validated when adding new product");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Click �Add Products� link
			4. Fill value which including characters and digits into EAN and UPC fields
			5. Click �Save� link
			VP: Verify that new product is unable to save due to the error message inline of EAN/UPC fields
			6. Enter a string larger than 13 digits into EAN field and larger than 12 digits into UPC field
			7. Click "Save' link
			VP:  Verify that there is an error message displayed next to the EAN /UPC field
			8. Enter a digits string exactly 13 digits into EAN field and 12 digits into UPC field
			9. Click "Save" link
			VP: New product is created successfully
		*/
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click �Add Products� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4.  Fill value which including characters and digits into EAN and UPC fields
		// 5.  Click �Save� link
		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		data.put("ean", RandomStringUtils.randomAlphanumeric(13));
		data.put("upc", RandomStringUtils.randomAlphanumeric(12));
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		//VP: Verify that new product is unable to save due to the error message inline of EAN/UPC fields
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.EanUpcErrorMessage.EAN_ONLY_DIGITS.getName()));
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.EanUpcErrorMessage.UPC_ONLY_DIGITS.getName()));
		
		//	6. Enter a string larger than 13 digits into EAN field and larger than 12 digits into UPC field
		productControl.editData(AddEditProductModel.EAN, RandomStringUtils.randomNumeric(14));
		productControl.editData(AddEditProductModel.UPC, RandomStringUtils.randomNumeric(13));
		//7. Click "Save' link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP:  Verify that there is an error message displayed next to the EAN /UPC field
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.EanUpcErrorMessage.UPC_EXCEED_CHAR_LIMIT.getName()));
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.EanUpcErrorMessage.EAN_EXCEED_CHAR_LIMIT.getName()));
		
		// 8. Enter a digits string exactly 13 digits into EAN field and 12 digits into UPC field
		productControl.editData(AddEditProductModel.EAN, RandomStringUtils.randomNumeric(13));
		productControl.editData(AddEditProductModel.UPC, RandomStringUtils.randomNumeric(12));
		// 9. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: New product is created successfully
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		
		//Teardown: Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
		
	}
	
		
	/*
	 * Verify that the tuning file could upload again after canceling uploading tuning file
	 */
	@Test
	public void TC051PAE_15() {
		productControl.addLog("ID : TC051PAE_15: Verify that the tuning file could upload again after canceling uploading tuning file");
		productControl.addErrorLog("UploadFileInterupt failed");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Click �Add Products� link
			4. Upload a tuning file
			5. Cancel uploading file
			VP: The message "File upload canceled" and Retry link is displayed
			6. Click "Retry" link
			7. Try to upload new tuning file again
			VP: New tuning file is uploaded successfully
			9. Repeat from step 4 to 7 with primary catalog images and arketing material files.
			VP: The tuning file, primary catalog images and marketing material files are able to upload again.
		*/
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click �Add Products� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 5. Upload a tuning file
		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[0]);
		productControl.uploadFileInterupt(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 6. Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName());
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.TUNING_UPLOAD_MESSAGE)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_CANCELED.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_TUNING));
		// 7. Click "Retry" link
		// 8. Try to upload new tuning file again
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(productControl.isTunningUploaded(AddEditProductModel.UPLOADED_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName()));
		
		// 9. Repeat from step 4 to 7 with primary catalog images and arketing material files.
		// VP: The tuning file, primary catalog images and marketing material files are able to upload again.
		
		// For primary catalog images
		// 4. Upload a tuning file
		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[1]);
		productControl.uploadFileInterupt(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 5. Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName());
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_250)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_CANCELED.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_IMAGE_250));
		// 6. Click "Retry" link
		// 7. Try to upload catalog image again
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_IMAGE_250, AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		
		//500x500
		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[2]);
		productControl.uploadFileInterupt(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 6. Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName());
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_500)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_CANCELED.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_IMAGE_500));
		// 7. Click "Retry" link
		// 8. Try to upload catalog image again
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_IMAGE_500, AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		
		//1000x1000
		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[3]);
		productControl.uploadFileInterupt(AddEditProductModel.SELECT_FILE_IMAGE_1000, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 6. Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName());
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_1000)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_CANCELED.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_IMAGE_1000));
		// 7. Click "Retry" link
		// 8. Try to upload catalog image again
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_IMAGE_1000, AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		
		// For Marketing materials
		// 4. Upload a tuning file
		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[4]);
		productControl.uploadFileInterupt(AddEditProductModel.ADD_MARKETING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 5. Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName());
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.MARKETING_UPLOAD_MESSAGE)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_CANCELED.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_MARKETING));
		// 6. Click "Retry" link
		// 7. Try to upload catalog image again
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_MARKETING, AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.MARKETING_UPLOAD_MESSAGE).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
				
	}
	
	/*
	 * Verify that user is able to upload wrong demension for primary catalog image
	 */
	@Test
	public void TC051PAE_16(){
		productControl.addLog("ID : TC051PAE_16 : Verify that user is able to upload wrong demension for primary catalog image");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Click �Add Products� link
			4. Upload a wrong image demension for 250x250 primary catalog image section
			5. Upload a wrong image demension for 500x500 primary catalog image section
			6. Upload a wrong image demension for 1000x100 primary catalog image section
		*/
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click �Add Products� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Upload a wrong image demension for 250x250 primary catalog image section
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.IMG_NAME[1]);
		// 5. Upload a wrong image demension for 500x500 primary catalog image section
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.IMG_NAME[2]);
		// 6. Upload a wrong image demension for 1000x100 primary catalog image section
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_1000, AddEditProductModel.IMG_NAME[0]);
		
		/*
		 * VP: Verify that user is able to upload wrong demension for primary catalog image
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_250)
				.contains(AddEditProductModel.UploadFileMessage.FILE_500_PX.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_500));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_500)
				.contains(AddEditProductModel.UploadFileMessage.FILE_1000_PX.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_1000)
				.contains( AddEditProductModel.UploadFileMessage.FILE_250_PX.getName()));
	}	
	
	/*
	 * Verify that user is able to upload tuning file, primary catalog image and marketing material file again when the uploaded file occurs error
	 */
	@Test
	public void TC051PAE_17() throws InterruptedException{
		productControl.addLog("ID : TC051PAE_17 : Verify that user is able to upload tuning file, primary catalog image and marketing material file again when the uploaded file occurs error");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Click �Add Products� link
			4. Upload a tuning file
			5. Corrupt the network while the file is uploading
			VP: Verify that the "File upload error" message and Retry link are displayed
			6. Connect network successfully
			7. Click "Save" link
			8. Try to upload another tuning file
			VP: The valid tuning file is uploaded successfully
			9. Repeat from step 4 to 8 with primary catalog images and marketing material files.
			VP: Tuning file, primary catalog images and marketing material files are able to upload again after errors.
		*/
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click �Add Products� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 5. Corrupt the network while the file is uploading
		productControl.interuptNetwork();

		// 4. Upload a tuning file
		productControl.uploadFileInterupt(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		productControl.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.TUNING_UPLOAD_MESSAGE).contains(AddEditProductModel.UploadFileMessage.UPLOAD_ERROR.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_TUNING));
		// 6. Connect network successfully
		productControl.connectNetwork();
		// 7. Click "Retry" link
		// 8. Try to another tuning file
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.TUNING_UPLOAD_MESSAGE).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));

		//9. Repeat from step 4 to 8 with primary catalog images and marketing material files.
		//with primary catalog images
		// 5. Corrupt the network while the file is uploading
		productControl.interuptNetwork();
		Thread.sleep(5000);
		productControl.uploadFileInterupt(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		productControl.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddEditProductModel.UploadFileMessage.UPLOAD_ERROR.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_IMAGE_250));
		// 6. Connect network successfully
		productControl.connectNetwork();
		// 7. Click "Retry" link
		// 8. Try to another 250x250 file
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_IMAGE_250, AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		/*
		 * Verify that primary catalog image file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		
		/* Repeat uploadiing for 500x500*/
		// 5. Corrupt the network while the file is uploading
		productControl.interuptNetwork();

		productControl.uploadFileInterupt(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		productControl.waitForAjax();

		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddEditProductModel.UploadFileMessage.UPLOAD_ERROR.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_IMAGE_500));
		// 6. Connect network successfully
		productControl.connectNetwork();

		// 7. Click "Retry" link
		// 8. Try to another 500x500 file
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_IMAGE_500, AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		productControl.waitForAjax();
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		
		// 5. Corrupt the network while the file is uploading
		productControl.interuptNetwork();
		/* Repeat uploadiing for 1000x1000*/
		
		productControl.uploadFileInterupt(AddEditProductModel.SELECT_FILE_IMAGE_1000, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());

		productControl.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddEditProductModel.UploadFileMessage.UPLOAD_ERROR.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_IMAGE_1000));
		// 6. Connect network successfully
		productControl.connectNetwork();
		// 7. Click "Retry" link
		// 8. Try to another 1000x1000 file file
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_IMAGE_1000, AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		
		//9. Repeat from step 4 to 8 with primary catalog images and marketing material files.
		//with marketing material file
		// 5. Corrupt the network while the file is uploading
		productControl.interuptNetwork();
		// 4. Upload a marketing file
		productControl.uploadFileInterupt(AddEditProductModel.ADD_MARKETING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());

		productControl.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.MARKETING_UPLOAD_MESSAGE).contains(AddEditProductModel.UploadFileMessage.UPLOAD_ERROR.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_MARKETING));
		// 6. Connect network successfully
		productControl.connectNetwork();
		// 7. Click "Retry" link
		// 8. Try to another maketing material file
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_MARKETING, AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.MARKETING_UPLOAD_MESSAGE).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
	}
	
}
