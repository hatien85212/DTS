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
import com.dts.adminportal.model.DeviceEdit;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.UserEdit;
import com.dts.adminportal.model.UserInfo;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.model.VariantInfo;
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
		VP: he Headphone: X Tuning Rating is hidden.
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
		
		//VP: the Headphone: X Tuning Rating is hidden
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.TUNING_RATING));
		
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
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName());
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
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		userControl.click(PageHome.LINK_USERS);
		userControl.selectUserInfoByEmail(DTS_USER);
		userControl.click(UserInfo.EDIT);
		userControl.enableAllPrivileges();
		userControl.click(UserEdit.SAVE);
		userControl.logout();
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
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
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
				AddEditProductModel.ProductModelName.LANGUAGE_DUPLICATE.getName());
		
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
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(data.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		
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
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName());
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.TUNING_UPLOAD_MESSAGE).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		Assert.assertTrue(productControl.isTunningUploaded(AddEditProductModel.UPLOADED_TUNING,AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName()));
		// Drag and drop for primary catalog image
		//Three size of primary catalog image are upload successfully.					
		//250x250
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		//500x500
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		//1000x1000
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_1000, AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		// Drap and drop a file into Marketing Material area
		productControl.uploadFile(AddEditProductModel.ADD_MARKETING, AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		Assert.assertTrue(productControl.isMarketingUploaded(AddEditProductModel.MARKETING_FILE_CONTAINER, AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName()));
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
		// VP: Verify that the similar Supported Input Channels is enabled.
		WebElement wired_dropdown = driver.findElement(By.xpath(AddEditProductModel.CONTENT_CHANNEL_WIRED_DROPDOWN));
		Assert.assertTrue(wired_dropdown.isEnabled());
		System.out.println("Select Input Channel: Bluetooth Container");
		productControl.selectInputChannel(AddEditProductModel.BLUETOOTH_CONTAINER, "none");
		// VP: Verify that the similar Supported Input Channels is enabled.
		WebElement bluet_dropdown = driver.findElement(By.xpath(AddEditProductModel.CONTENT_CHANNEL_BLUET_DROPDOWN));
		Assert.assertTrue(bluet_dropdown.isEnabled());;
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
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CONNECTION_TYPE_MESSAGE).contains(AddEditProductModel.required.Connection_Type_is_required.getName()));
		
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
		data.put("name",PARTNER_BRAND_NAME_2);
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
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click �Add Products� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 5. Upload a tuning file
		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA.Tuning_Drag_Drop_Area.getName());
		//productControl.uploadFileInterupt(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 6. Cancel uploading file
		//productControl.click(AddEditProductModel.CANCEL_UPLOAD_TUNING);
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName(), "dts");
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		//Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.TUNING_UPLOAD_MESSAGE)
				//.contains(AddEditProductModel.UploadFileMessage.UPLOAD_CANCELED.getName()));
		//Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_TUNING));
		// 7. Click "Retry" link
		// 8. Try to upload new tuning file again
		//productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName());
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		//Assert.assertTrue(productControl.isTunningUploaded(AddEditProductModel.UPLOADED_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName()));
		
		// 9. Repeat from step 4 to 7 with primary catalog images and arketing material files.
		// VP: The tuning file, primary catalog images and marketing material files are able to upload again.
		
		// For primary catalog images
		// 4. Upload a tuning file
//		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA.Image_Drag_Drop_Area_250.getName());
		productControl.uploadFileInterupt(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 5. Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName(), "jpg");
		//productControl.click(AddEditProductModel.CANCEL_UPLOAD_TUNING);
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
		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA.Image_Drag_Drop_Area_500.getName());
		productControl.uploadFileInterupt(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 6. Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName(), "jpg");
		//productControl.click(AddEditProductModel.CANCEL_UPLOAD_TUNING);
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
		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA.Image_Drag_Drop_Area_1000.getName());
		productControl.uploadFileInterupt(AddEditProductModel.SELECT_FILE_IMAGE_1000, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 6. Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName(), "jpg");
		//productControl.click(AddEditProductModel.CANCEL_UPLOAD_TUNING);
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
		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA.Marketing_Drag_Drop_Area.getName());
		productControl.uploadFileInterupt(AddEditProductModel.ADD_MARKETING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 5. Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName(), "dtscs");
		//productControl.click(AddEditProductModel.CANCEL_UPLOAD_TUNING);
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
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName());
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
	
	/*
	 * Verify that the Headphone: X Tuning Rating  is hidden on the Product Detail and Edit pages when the value equals "Undetermined."
	 */
	@Test
	public void TC051PAE_18() throws InterruptedException{
		productControl.addLog("ID : TC051PAE_18 : Verify that the Headphone: X Tuning Rating  is hidden on the Product Detail and Edit pages when the value equals 'Undetermined.' ");
	
		/*
		1. Log into DTS portal as a partner admin	
		2. Navigate to "Accessories" page	
		3. Click "Add product" link	
		4. Fill valid values into all required fields	
		VP: The Headphone: X Tuning Rating is hidden.
		5. Click "Save" link	
		VP: The Headphone: X Tuning Rating is hidden.
		6. Click "Request  Tuning Review" link	
		7. Log out DTS portal	
		8. Log into DTS portal as a DTS admin	
		9. Select above product 	
		VP: The Headphone: X Tuning Rating is Undetermined
		10. Click "Edit Version" link	
		11. Set "Headphone:X Tuning Rating" to A	
		12. Log out DTS portal	
		13. Log into DTS portal as a partner admin again	
		14. Select above product 	
		VP: The Headphone: X Tuning Rating is A
		15. Click "Edit Version" link	
		VP: The Headphone: X Tuning Rating is A
		VP: The Headphone: X Tuning Rating is displayed as read only

		*/
		
//		1. Log into DTS portal as a partner admin	
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
//		2. Navigate to "Accessories" page	
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//		3. Click "Add product" link
		productControl.click(ProductModel.ADD_PRODUCT);
//		4. Fill valid values into all required fields
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		dataProduct.remove("save");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),dataProduct);
		/*
		 * VP: The Headphone: X Tuning Rating is hidden.
		 */
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.TUNING_RATING));
//		5. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: The Headphone: X Tuning Rating is hidden.
		 */
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.TUNING_RATING));
//		6. Click "Request  Tuning Review" link
		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
//		7. Log out DTS portal
		loginControl.logout();
//		8. Log into DTS portal as a DTS admin
		loginControl.login(DTS_USER, DTS_PASSWORD);
//		9. Select above product 
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		/*
		 * VP: The Headphone: X Tuning Rating is Undetermined
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_RATING),"Undetermined");
//		10. Click "Edit Version" link
		productControl.click(ProductDetailModel.EDIT_MODE);
//		11. Set "Headphone:X Tuning Rating" to A	
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING,AddEditProductModel.TuningRatingOption.A.getName());
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
//		12. Log out DTS portal
		loginControl.logout();
//		13. Log into DTS portal as a partner admin again
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
//		14. Select above product 
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(dataProduct.get("name"));
		/*
		 * VP: The Headphone: X Tuning Rating is A
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_RATING),"A");
//		15. Click "Edit Version" link
		productControl.click(ProductDetailModel.EDIT_MODE);
		/*
		 * VP: The Headphone: X Tuning Rating is A
		 */
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.TUNING_RATING_DISABLE),"A");
		/*
		 * VP: The Headphone: X Tuning Rating is displayed as read only
		 */
		Assert.assertFalse(productControl.isElementEditable(AddEditProductModel.TUNING_RATING_DISABLE));
		
		// Delete data
		productControl.click(AddEditProductModel.CANCEL_PRODUCT);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that �Input Specification� section is displayed correctly
	 */
	@Test
	public void TC051PAE_19() {
		productControl.addLog("ID : TC051PAE_19 : Verify that �Input Specification� secsion is displayed correctly");
	
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click �Add Product� link
			VP: Verify that �Input Specification� section include checkboxes: �Wired�, �Bluetooth� and �USB�

		*/
		
		// 1. Log into DTS portal as a Partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click �Add Product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// VP: Verify that �Input Specification� section include checkboxes: �Wired�, �Bluetooth� and �USB�
		String tuningOptions = productControl.getTextByXpath(AddEditProductModel.INPUT_SPECIFICATIONS_TABLE);
		Assert.assertTrue(ListUtil.containsListText(tuningOptions, AddEditProductModel.ConnectionType.getNames()));
		
	}
	
	/*
	 * Verify that Product Edit page validate for uploading with new connection type
	 */
	@Test
	public void TC051PAE_20() {
		productControl.addLog("ID : TC051PAE_20: Verify that Product Edit page validate for uploading with new connection type");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click �Add Product� link
			4. Fill valid value into all required fields
			5. Select the connection type "USB" only
			6. Upload a bluetooth only hpxtt tuning file
			7. Click �Save� link
			VP: The error message is shown.
			8. Upload a USB only hpxtt tuning file
			9. Click �Save� link
			VP: Verify that Product is saved successfully
			10. Click �Edit Version� link
			11. Delete tuning file
			12. Select the connection type "USB" and �Wired�
			13. Upload a USB only hpxtt tuning file
			14. Click �Save� link
			VP: The error message is shown.
			15. Repeat from step 11 to step 14 with tuning file contains both Lineout and Bluetooth
			VP: The error message is shown.
			16. Repeat from step 11 to step 14 with tuning file contains both Lineout and USB
			VP: Verify that Product is saved successfully
			17. Click �Edit Version� link
			18. Repeat from step 11 to step 14 with connection type include �Wired�, �Bluetooth�, �USB�; but tuning file contains only Bluetooth and USB
			VP: The error message is shown.
			19. Delete tuning file
			20. Upload tuning file contains Lineout, Bluetooth and USB
			VP: Verify that Product is saved successfully

		*/
		// 1. Log into DTS portal as a Partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click �Add Product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productData.remove("save");
		productData.remove("wired");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),productData);
		// 5. Select the connection type "USB" only
		productControl.selectACheckbox(AddEditProductModel.USB_CHECKBOX);
		// 6. Upload a bluetooth only hpxtt tuning file
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT_Bluetooth.getName());
		// 7. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: The error message is shown.
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.TUNING_MESSAGE_POPUP),AddEditProductModel.UploadFileMessage.CONNECTION_TYPE_NOTMATHCH.getName());
		productControl.click(AddEditProductModel.CLOSE_POPUP);
		// 8. Upload a USB only hpxtt tuning file
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT_USB.getName());
		// 9. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: Verify that Product is saved successfully
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 10. Click �Edit Version� link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 11. Delete tuning file
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		// 12. Select the connection type "USB" and �Wired�
		productControl.selectACheckbox(AddEditProductModel.WIRED_CHECKBOX);
		// 13. Upload a USB only hpxtt tuning file
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT_USB.getName());
		// 14. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: The error message is shown.
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.TUNING_MESSAGE_POPUP),AddEditProductModel.UploadFileMessage.CONNECTION_TYPE_NOTMATHCH.getName());
		// 15. Repeat from step 11 to step 14 with tuning file contains both Lineout and Bluetooth
		productControl.click(AddEditProductModel.CLOSE_POPUP);
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT_BothLineBlue.getName());
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: The error message is shown.
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.TUNING_MESSAGE_POPUP),AddEditProductModel.UploadFileMessage.CONNECTION_TYPE_NOTMATHCH.getName());
		// 16. Repeat from step 11 to step 14 with tuning file contains both Lineout and USB
		productControl.click(AddEditProductModel.CLOSE_POPUP);
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT_BothLineUSB.getName());
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: Verify that Product is saved successfully
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 17. Click �Edit Version� link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 18. Repeat from step 11 to step 14 with connection type include �Wired�, �Bluetooth�, �USB�; but tuning file contains only Bluetooth and USB
		productControl.selectACheckbox(AddEditProductModel.BLUETOOTH_CHECKBOX);
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT_BothBlueUSB.getName());
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: The error message is shown.
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.TUNING_MESSAGE_POPUP),AddEditProductModel.UploadFileMessage.CONNECTION_TYPE_NOTMATHCH.getName());
		// 19. Delete tuning file
		productControl.click(AddEditProductModel.CLOSE_POPUP);
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		// 20. Upload tuning file contains Lineout, Bluetooth and USB
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT_BothBlueLineUSB.getName());
		// VP: Verify that Product is saved successfully
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// Delete Product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that Variant Edit page validate for uploading with new connection type
	 */
	@Test
	public void TC051PAE_21() {
		productControl.addLog("ID : TC051PAE_21: Verify that Variant Edit page validate for uploading with new connection type");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click �Add Product� link
			4. Fill valid value into all required fields
			5. Select the connection type "USB" only
			6. Click �Save� link
			VP: Verify that Product is saved successfully
			7. Click �Add New Variant� link
			8. Fill valid value into all required fields
			9. Upload a bluetooth only hpxtt tuning file
			10. Click "Save" link
			VP: The error message is shown.
			11. Delete tuning file
			12. Upload a USB only hpxtt tuning file
			13. Click "Save" link
			VP: Verify that variant is saved successfully
			14. Click Product Model link
			15. Click �Edit Version� link
			16. Select the connection type "USB" and �Lineout�
			17. Click �Save� link
			VP: Verify that Product is saved successfully
			18. Repeat from step 7 to step 9 with tuning file contains only USB
			VP: The error message is shown.
			19. Repeat from step 7 to step 9 with tuning file contains only USB and Lineout
			VP: Verify that variant is saved successfully

		*/
		// 1. Log into DTS portal as a Partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click �Add Product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productData.remove("save");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), productData);
		// 5. Select the connection type "USB" only
		productControl.selectACheckbox(AddEditProductModel.USB_CHECKBOX);
		productControl.uncheckACheckbox(AddEditProductModel.WIRED_CHECKBOX);
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
		// 9. Upload a bluetooth only hpxtt tuning file
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_TUNNING);
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT_Bluetooth.getName());
		// VP: The error message is shown.
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.TUNING_MESSAGE_POPUP),AddEditProductModel.UploadFileMessage.CONNECTION_TYPE_NOTMATHCH.getName());
		// 10. Delete tuning file
		productControl.click(AddEditProductModel.CLOSE_POPUP);
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		// 11. Upload a USB only hpxtt tuning file
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT_USB.getName());
		// VP: Verify that variant is saved successfully
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.TITLE_NAME));
		// 12. Click Product Model link
		productControl.click(VariantInfo.PRODUCT_LINK);
		// 13. Click �Edit Version� link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 14. Select the connection type "USB" and �Lineout�
		productControl.selectACheckbox(AddEditProductModel.WIRED_CHECKBOX);
		// 15. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: Verify that Product is saved successfully
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 16. Repeat from step 7 to step 9 with tuning file contains only USB
		productControl.click(ProductDetailModel.ADD_VARIANT);
		Hashtable<String,String> variantData1 = TestData.variantData(false, false, false);
		variantData1.remove("save");
		productControl.addVariant(AddEditProductModel.getVariantHash(),variantData1);
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_TUNNING);
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT_USB.getName());
		// VP: The error message is shown.
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.TUNING_MESSAGE_POPUP),AddEditProductModel.UploadFileMessage.CONNECTION_TYPE_NOTMATHCH.getName());
		// 17. Repeat from step 7 to step 9 with tuning file contains only USB and Lineout
		productControl.click(AddEditProductModel.CLOSE_POPUP);
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT_BothLineUSB.getName());
		// VP: Verify that variant is saved successfully
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		Assert.assertTrue(productControl.isElementPresent(VariantInfo.TITLE_NAME));
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the "USB Device Name" section work correctly
	 */
	@Test
	public void TC051PAE_22() throws InterruptedException {
		productControl.addLog("ID : TC051PAE_22: Verify that the 'USB Device Name' section work correctly");
		/*
			1. Log into DTS portal as a Partner user
			2. Navigate to "Products" page
			3. Click "Add product" link
			4. Fill valid value into all required fields
			5. Upload tuning file successfully
			6. Select "USB" in "Connection Type"
			VP: The "USB Device Name" section is displayed with instruction label "Enter one or more strings which can uniquely identify this product based on its Bluetooths "Device Name" metadata value."
			7. Input a USB Device Name into "USB Device Name" field
			8. Click "Add" button
			VP: The BT device name is added successfully with a delete icon
			9. Unselect "USB" in "Connection Type"
			VP: The "USB Device Name" section is not displayed
			10. Select "Wired" in "Connection Type"
			VP: The "USB Device Name" section is not displayed
			11. Select "USB" in "Connection Type" again
			VP: The "USB Device Name" section is not displayed with instruction label "Enter one or more strings which can uniquely identify this product based on its Bluetooths "Device Name" metadata value." is displayed
			12. Input a USB Device Name into "USB Device Name" field
			13. Press "Enter" key from keyboard
			VP: The USB device name is added successfully with a delete icon
			14. Add the same USB Device Name in step 12
			VP: An error message is displayed to notify that BT device name is existed
			15.Add a USB Device Name with whitespaces into successfully
			VP: The USB device name is trimed with a delete icon
			16. Click "Save" link
			VP: The correct USB device names are displayed in "USB Device Name"
			17. Click "Edit Version" link
			18. Delete a USB Device Name successfully
			VP: The USB device name is deleted successfully
			19. Click "Save" link
			VP: The deleted USB device name is not displayed

		*/
		// 1. Log into DTS portal as a Partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click �Add Product� link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productData.remove("save");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), productData);
		// 5. Upload tuning file successfully
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT_BothBlueLineUSB.getName());
		// 6. Select "USB" in "Connection Type"
		productControl.selectACheckbox(AddEditProductModel.USB_CHECKBOX);
		// VP: The "USB Device Name" section is displayed with instruction label "Enter one or more strings which can uniquely identify this product based on its Bluetooths "Device Name" metadata value."
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.USB_DEVICE_NAME_TITLE));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.USB_DEVICE_NAME_INS).contains(AddEditProductModel.USB_DEVICE_NAME_TEXTINS));
		// 7. Input a USB Device Name into "USB Device Name" field
		productControl.editData(AddEditProductModel.USB_DEVICE_NAME_FIELD, "USB Device 1");
		// 8. Click "Add" button
		productControl.click(AddEditProductModel.USB_DEVICE_NAME_ADD);
		// VP: The BT device name is added successfully with a delete icon
		Assert.assertTrue(productControl.isElementPresent(productControl.getXpathBTDeviceName("USB Device 1")));
		Assert.assertTrue(productControl.isElementPresent(productControl.getXpathTrashBTDeviceName("USB Device 1")));
		// 9. Unselect "USB" in "Connection Type"
		productControl.uncheckACheckbox(AddEditProductModel.USB_CHECKBOX);
		// VP: The "USB Device Name" section is not displayed
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.USB_DEVICE_NAME_TITLE));
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.USB_DEVICE_NAME_FIELD));
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.USB_DEVICE_NAME_ADD));
		// 10. Select "Wired" in "Connection Type"
		productControl.selectACheckbox(AddEditProductModel.WIRED_CHECKBOX);
		// VP: The "USB Device Name" section is not displayed
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.USB_DEVICE_NAME_TITLE));
		// 11. Select "USB" in "Connection Type" again
		productControl.selectACheckbox(AddEditProductModel.USB_CHECKBOX);
		// VP: The "USB Device Name" section is displayed with instruction label "Enter one or more strings which can uniquely identify this product based on its Bluetooths "Device Name" metadata value." is displayed
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.USB_DEVICE_NAME_TITLE));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.USB_DEVICE_NAME_INS).contains(AddEditProductModel.USB_DEVICE_NAME_TEXTINS));
		// 12. Input a USB Device Name into "USB Device Name" field
		productControl.editData(AddEditProductModel.USB_DEVICE_NAME_FIELD, "USB Device 2");
		// 13. Press "Enter" key from keyboard
		Thread.sleep(3000);
		productControl.autoTool.send("{ENTER}",false);
		// VP: The USB device name is added successfully with a delete icon
		Assert.assertTrue(productControl.isElementPresent(productControl.getXpathBTDeviceName("USB Device 2")));
		Assert.assertTrue(productControl.isElementPresent(productControl.getXpathTrashBTDeviceName("USB Device 2")));
		// 14. Add the same USB Device Name in step 12
		productControl.editData(AddEditProductModel.USB_DEVICE_NAME_FIELD,"USB Device 2");
		productControl.click(AddEditProductModel.USB_DEVICE_NAME_ADD);
		// VP: An error message is displayed to notify that BT device name is existed
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.USB_DEVICES_MESSAGE),AddEditProductModel.required.Device_name_is_already_existed.getName());
		// 15.Add a USB Device Name with white spaces into successfully
		productControl.editData(AddEditProductModel.USB_DEVICE_NAME_FIELD,"           USB Device 3              ");
		productControl.click(AddEditProductModel.USB_DEVICE_NAME_ADD);
		// VP: The USB device name is trimed with a delete icon
		Assert.assertTrue(productControl.isElementPresent(productControl.getXpathBTDeviceName("USB Device 3")));
		Assert.assertTrue(productControl.isElementPresent(productControl.getXpathTrashBTDeviceName("USB Device 3")));
		// 16. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: The correct USB device names are displayed in "USB Device Name"
		Assert.assertTrue(productControl.getTextByXpath(ProductDetailModel.USB_DEVICE_NAME).contains("USB Device 1"));
		Assert.assertTrue(productControl.getTextByXpath(ProductDetailModel.USB_DEVICE_NAME).contains("USB Device 2"));
		Assert.assertTrue(productControl.getTextByXpath(ProductDetailModel.USB_DEVICE_NAME).contains("USB Device 3"));
		// 17. Click "Edit Version" link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 18. Delete a USB Device Name successfully
		productControl.click(productControl.getXpathTrashBTDeviceName("USB Device 2"));
		// VP: The USB device name is deleted successfully
		Assert.assertFalse(productControl.getTextByXpath(AddEditProductModel.USB_DEVICE_NAME).contains("USB Device 2"));
		// 19. Click "Save" link	
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: The deleted USB device name is not displayed
		Assert.assertTrue(productControl.getTextByXpath(ProductDetailModel.USB_DEVICE_NAME).contains("USB Device 1"));
		Assert.assertFalse(productControl.getTextByXpath(ProductDetailModel.USB_DEVICE_NAME).contains("USB Device 2"));
		Assert.assertTrue(productControl.getTextByXpath(ProductDetailModel.USB_DEVICE_NAME).contains("USB Device 3"));
	}
}
