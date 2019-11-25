package com.dts.adminportal.partner.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.util.ListUtil;

public class PartnerProduct052PModelVariantEdit extends BasePage{
	
	@Override
	protected void initData() {
	}	

	
//	@BeforeMethod
//	public void loginBeforeTest() {
//		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//	}
	
	/*
	 * Verify that the Adding New Model Variant shows up all necessary fields properly.
	 */
	@Test
	public void TC052PMVE_01(){
		productControl.addLog("ID : TC052PMVE_01 : Verify that the Adding New Model Variant shows up all necessary fields properly.");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Click "Add New Variant" link from "Model Actions" module
			5. Click "Upload Custom Tuning" link
			VP: 052 Model Variant Edit page is showed up with: Model Name text field, Languages dropdown, EAN/UPC text field, Descriptor, Input Specifications, Release date, Tuning ( Use Parent's Tuning checkbox and Upload Custom Tuning link), Headphone: X Tuning Rating, Primary Catalog Image section and Marketing Material section.
			VP: 052 Model Variant Edit page is showed up and the Headphone: X Tuning Rating is uneditable
			VP: 052 Model Variant Edit page is showed up and Tuning section is displayed with "Use Parent's Tuning" checkbox and "Upload Custom Tuning" link
			VP: The Input Specifications section inludes "Connection Type" and "Support Input Channels" module but they are disabled.			
		 */

		/*
		 * **************************************************************
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		productControl.selectAnAccessory();

		// 4. Click "Add New Variant" link from "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
//		VP: 052 Model Variant Edit page is showed up with: Model Name text field, Languages dropdown, EAN/UPC text field, Descriptor, Input Specifications, Release date, Tuning ( Use Parent's Tuning checkbox and Upload Custom Tuning link), Headphone: X Tuning Rating, Primary Catalog Image section and Marketing Material section.
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getVariantElementsInfo()), true);
		
		//VP: 052 Model Variant Edit page is showed up and the Headphone: X Tuning Rating is uneditable
		Assert.assertFalse(productControl.canEditHeadphoneRating(PageHome.HEADPHONE_TUNING_RATING));

		//VP: 052 Model Variant Edit page is showed up and Tuning section is displayed with "Use Parent's Tuning" checkbox and "Upload Custom Tuning" link
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.UPLOAD_CUSTOM_TUNNING));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.PARENT_TUNNING_LINK));
		
		//VP: The Input Specifications section includes "Connection Type" and "Support Input Channels" module but they are disabled.
		Assert.assertFalse(productControl.isElementEditable(AddEditProductModel.VARIANT_INPUT_SPECIFICATIONS));
		
	}
	
	
	/*
	 Verify that user can upload tuning file, primary catalog image and marketing material file by dragging and dropping file into "Add Tuning File" area.
	 */
	@Test
	public void TC052PMVE_02(){
		productControl.addLog("ID : TC052PMVE_02 : Verify that user can upload tuning file, primary"
				+ " catalog image and marketing material file by dragging and dropping file into 'Add Tuning File' area.");
		productControl.addErrorLog("Failed by drag drop, need to fix");
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
			8. Repeat from step 6 to 7 for primary catalog image and marketing material files.
			VP:The tuning file is upload successfully.
			VP: Three primary catalog images are uploaded successfully
			VP: Marketing material files are uploaded successfully
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		productControl.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		//5. Click "Add New Variant" link from "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		//VP. Verify that the 052P Model Variant Edit page is displayed
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.VARIANT_TITLE));
		//Click "Upload Custom Tuning" link 
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_TUNNING);
		// Verify that the 'Add Tuning File' item is displayed with 'drag and drop' area and 'Select File' button when clicking on 'Upload Custom Tuning' link.
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.ADD_TUNNING));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.TUNING_DRAG_DROP_AREA));
		//7. Drag and Drop a tuning file into Add Tuning File area
		productControl.dragDropFile(AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		
		//Verify that user can upload tuning file by dragging and dropping file into "Add Tuning File" area.
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.TUNING_UPLOAD_MESSAGE).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		//8. Repeat from step 6 to 7 for primary catalog image and marketing material files.
		//Click "Upload Custom Image" link
		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[1]);
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_IMAGE);
		//  Drag and Drop a tuning file into Add Custom Image File area
		productControl.dragDropFile(AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250));
		
		// For Marketing material
		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[4]);
		productControl.click(AddEditProductModel.ADD_MARKETING);
		productControl.dragDropFile(AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		
		//VP: Marketing material files are uploaded successfully
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.MARKETING_FILE_CONTAINER));
		Assert.assertTrue(productControl.isMarketingUploaded(AddEditProductModel.MARKETING_FILE_CONTAINER,AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName()));
		
	}
	
	/*
	 * Verify that the tuning file is validated after uploading.
	 */
	@Test
	public void TC052PMVE_03(){
		productControl.addLog("ID : TC052PMVE_03 :Verify that the tuning file is validated after uploading.");
	  /*
			Pre-condition: Partner user has "Add and manage accessories" rights.
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Verify that the 040P product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module
			VP. Verify that the 052P Model Variant Edit page is displayed.
			6. Click "Upload Custom Tuning" link
			7. Upload an invalid tuning file
			VP: An error message is displayed and the invalid tuning file is not added.
			8. Click "Retry" link to upload another valid tuning zip file
			VP: New tuning zip file is uploaded successfully and it's called "Partner Tuning"
			9. Click "Use Parent's Tuning" link
			10. Click "Delete" button on delete confirmation popup
			VP: The custom uploading tuning file section is disappreard
			11. Click "Upload Custom Tuning" link again
			12. Upload another hpxtt tuning file
			VP: The hpxtt tuning file is uploaded successfully and it's callsed "Partner Tuning"
			13. Click on Trash icon to delete custom tuning file
			VP: A delete confirmation popup is displayed
			14. Click "Delete" button on confirmation popup
			VP: A custom tuning file is deleted successfully
	   */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		productControl.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 5. Click "Add New Variant" link from "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP: Verify that the 052P Model Variant Edit page is displayed
		 */
		Assert.assertEquals(productControl.existsElement(AddEditProductModel.getVariantElementsInfo()), true);
		// 6. Click "Upload Custom Tuning" link
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_TUNNING);
		// 7. Upload an invalid tuning file
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_Invalid.getName());
		/*
		 * Verify that An error message is displayed and the invalid tuning file is not added
		 */
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.UploadFileMessage.INVALID_FILE.getName()));
//		8. Click "Retry" link to upload another valid tuning zip file
		productControl.click(AddEditProductModel.RETRY_UPLOAD_TUNING);
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_ZIP.getName()	);
//		VP: New tuning zip file is uploaded successfully and it's called "Partner Tuning"
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.VARIANT_UPLOADED_TUNING_NAME), AddEditProductModel.tuningname[0]);
		
//		9. Click "Use Parent's Tuning" link
		productControl.click(AddEditProductModel.PARENT_TUNNING_LINK);
//		10. Click "Delete" button on delete confirmation popup
		productControl.selectConfirmationDialogOption("Delete");
//		VP: The custom uploading tuning file section is disappeared
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.ADD_TUNNING));
//		11. Click "Upload Custom Tuning" link again
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_TUNNING);
//		12. Upload another hpxtt tuning file
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
//		VP: The hpxtt tuning file is uploaded successfully and it's callsed "Partner Tuning"
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.VARIANT_UPLOADED_TUNING_NAME), AddEditProductModel.tuningname[0]);
//		13. Click on Trash icon to delete custom tuning file
		productControl.click(AddEditProductModel.DELETE_UPLOADED_TUNING);
//		14. Click "Delete" button on confirmation popup
		productControl.selectConfirmationDialogOption("Delete");
//		VP: A custom tuning file is deleted successfully
		Assert.assertFalse(productControl.checkMessageDisplay(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
	}
	
	

	
	/*
	 * Verify that the EAN/UPC is validated for the already exist EAN/UPC code.
	 */
	@Test
	public void TC052PMVE_04(){
		productControl.addLog("ID : TC052PMVE_04 :Verify that the EAN/UPC is validated for the already exist EAN/UPC code.");
		productControl.addErrorLog("PDPP-467: 051D Accessory Edit : The warning message is not displayed when adding duplicate EAN/UPC");
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
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Click Add product link
		productControl.selectAnAccessory();
		String EANValue = productControl.getTextByXpath(ProductDetailModel.EAN);
		String UPCValue = productControl.getTextByXpath(ProductDetailModel.UPC);
		// 5. Click "Add New Variant" link from "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		//VP: Verify that the 052P Model Variant Edit page is displayed
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.VARIANT_TITLE));	
		// 6. Fill valid value into all required fields
		String variantName = RandomStringUtils.randomAlphanumeric(10);
		productControl.editData(AddEditProductModel.MODEL_NAME, variantName);
		productControl.editData(AddEditProductModel.EAN, EANValue);
		productControl.editData(AddEditProductModel.UPC, UPCValue);
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		//Verify that the EAN/UPC is validated for the already exist EAN/UPC code.
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.EAN_ERROR_MESSAGE), AddEditProductModel.EanUpcErrorMessage.EAN_UNIQUE.getName());
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.UPC_ERROR_MESSAGE), AddEditProductModel.EanUpcErrorMessage.UPC_UNIQUE.getName());
	}
	
	/*
	 * Verify that the default and external mode name language are validated correctly.
	 */
	@Test
	public void TC052PMVE_05(){
		productControl.addLog("ID : TC052PMVE_05 :Verify that the default and external mode name language are validated correctly.");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select a product from product list
			4. Click "Add Variant" link
			VP: The "- Select -" item is the default value of Model Name language dropdown, the hint text "Inherits default" is displayed in external model name text field.
			5. List out the Model Name language dropdown
			VP:  The Model Name language dropdown contains items: - Select -, Chinese - Simplified, Chinese - Traditional, French, German, Italian, Japanese, Korean, Russian and Spanish.
			6. Set language dropdown to another item other than "Select"
			VP: Another language dropdown is displayed
			7. Select the same item for second language dropdown
			VP: A warning message is displayed which notifying for the duplicating of languages.
			8. Set 1st language dropdown to "Select"
			VP: The external model name language text field is disabled.
			9. Type value into 2nd model name input field
			10. Click on Trashcan icon
			VP: The language dropdown is restore to default "Select" value and the input field is cleared  
			11. Leave the Default model name empty
			12. Click "Save" link
		*/
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		productControl.selectAnAccessory();
		// 4. Click "Add Variant" link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		//VP: The "- Select -" item is the default value of Model Name language dropdown, the hint text "Inherits default" is displayed in external model name text field.
		Assert.assertEquals(productControl.getItemSelected(AddEditProductModel.LANGUAGE_DROPDOWN(0)), AddEditProductModel.ProductLanguage.SELECT.getName());
		//5. List out the Model Name language dropdown
		//VP:  The Model Name language dropdown contains items: - Select -, Chinese - Simplified, Chinese - Traditional, French, German, Italian, Japanese, Korean, Russian and Spanish.
		ListUtil.containsAll(productControl.getAllComboboxOption(AddEditProductModel.LANGUAGE_DROPDOWN(0)), AddEditProductModel.ProductLanguage.getNames());
		
//		6. Set language dropdown to another item other than "Select"
//		VP: Another language dropdown is displayed
		// get number of language language dropdown is displayed before select another language
		int before = productControl.getNumElementDisplayedByText(AddEditProductModel.ProductModelName.INHERIT_DEFAULT.getName());
		System.out.print("before" +before);
		productControl.selectOptionByName(AddEditProductModel.LANGUAGE_DROPDOWN(0), AddEditProductModel.ProductLanguage.CHINESE_SIM.getName());
		//VP: Another language dropdown is displayed
		int after = productControl.getNumElementDisplayedByText(AddEditProductModel.ProductModelName.INHERIT_DEFAULT.getName());
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
				AddEditProductModel.ProductModelName.INHERIT_DEFAULT.getName());
	
		// 10. Leave the Default model name empty
		// 11. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		//VP: A warning message is displayed which notifying the model name is empty.
		String msgErr = productControl.getTextByXpath(AddEditProductModel.MODEL_NAME_ERR);
		Assert.assertEquals(msgErr, AddEditProductModel.ProductModelName.MODEL_NAME_REQUIRED.getName());
	}
	

	/*
	 * Verify the primary catalog images section displays properly.
	 */
	@Test
	public void TC052PMVE_06() throws InterruptedException{
		productControl.addLog("ID : TC052PMVE_06 : Verify the primary catalog images section displays properly.");
		/*
			Pre-condition: Partner user has "Add and manage Products" rights.
			
			1. Log into DTS portal as a partner admin
			2. Navigate to "Accessories" page
			3. Select an product from Products table
			4. Verify that the 040P product Detail page is displayed
			5. Click "Add New Variant" link from "Model Actions" module

			VP: 052 Model Variant Edit page is showed up and Primary Catalog Image section is displayed with "Use Parent's Images" checkbox and "Upload Custom Images" link
			6. Click on "Upload Custom Images" link
			VP: The  "Add Custom Image" item is displayed with "drag and drop" area and "Select File" button and Three size of Primary Catalog Image display display in order: 250x250 pixels, 500x500 pixels, 1000x1000 pixels.
			 7. Upload three size of image successfully
			VP: The correct image thumbnail is displayed next to image name of each imag size and The uploaded date and file size are displayed below the image name for each size of Primary Catalog Image.
			8. Click on image thumbnail of each size
			VP: A lightbox style popup with the picture showing in full size is displayed
			9. Click on the file name of each size
			VP: A lightbox style popup with the picture showing in full size is displayed
			10. Delete the uploaded image by clicking on trashcan icon
			11. Upload another image
			VP: New images are uploaded successfully
			12.Tick to check the "Use Parent's Images" checkbox.
			VP: The Delete Images confirmation dialog is displayed.
			13. Click "Delete" on confirmation dialog

		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		productControl.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		//5. Click "Add New Variant" link from "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// Verify that the Primary Catalog Image section is displayed with "Use Parent's Images" checkbox and "Upload Custom Images" link
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.PARENT_IMAGE_LINK));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.UPLOAD_CUSTOM_IMAGE));
		//6. Click on "Upload Custom Images" link
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_IMAGE);
		//VP: The  "Add Custom Image" item is displayed with "drag and drop" area and "Select File" button and Three size of Primary Catalog Image display display in order: 250x250 pixels, 500x500 pixels, 1000x1000 pixels.
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.SELECT_FILE_IMAGE_250));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.SELECT_FILE_IMAGE_500));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.SELECT_FILE_IMAGE_1000));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.IMAGE250_DRAG_DROP_AREA));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.IMAGE500_DRAG_DROP_AREA));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.IMAGE1000_DRAG_DROP_AREA));
		// 7. Upload three size of image successfully

		//Upload image for each size of Primary Catalog Image successfully
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.IMG_NAME[0]);
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.IMG_NAME[1]);
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_1000, AddEditProductModel.IMG_NAME[2]);
		//VP: The correct image thumbnail is displayed next to image name of each imag size and 
		Assert.assertTrue(productControl.isElementDisplayHorizontal(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250, AddEditProductModel.CATALOG_IMAGE_UPLOAD_INFO_250));
		Assert.assertTrue(productControl.isElementDisplayHorizontal(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_500, AddEditProductModel.CATALOG_IMAGE_UPLOAD_INFO_500));
		Assert.assertTrue(productControl.isElementDisplayHorizontal(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000, AddEditProductModel.CATALOG_IMAGE_UPLOAD_INFO_1000));
		//The uploaded date and file size are displayed below the image name for each size of Primary Catalog Image.
		Assert.assertTrue(productControl.isElementDisplayVertically(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_250, AddEditProductModel.CATALOG_IMAGE_FILE_SIZE_250));
		Assert.assertTrue(productControl.isElementDisplayVertically(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_500, AddEditProductModel.CATALOG_IMAGE_FILE_SIZE_500));
		Assert.assertTrue(productControl.isElementDisplayVertically(AddEditProductModel.CATALOG_IMAGE_TITLE_LINK_1000, AddEditProductModel.CATALOG_IMAGE_FILE_SIZE_1000));
		//8. Click on image thumbnail of each size
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
		
		//9. Click on the file name of each size
		//VP: A lightbox style popup with the picture showing in full size is displayed
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
		
		//10. Delete the uploaded image by clicking on trashcan icon
		productControl.doDelete(AddEditProductModel.CATALOG_IMAGE_TRASH_250);
		productControl.doDelete(AddEditProductModel.CATALOG_IMAGE_TRASH_500);
		productControl.doDelete(AddEditProductModel.CATALOG_IMAGE_TRASH_1000);
		
		//11. Upload another image
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.IMG_NAME[0]);
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.IMG_NAME[1]);
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_1000, AddEditProductModel.IMG_NAME[2]);
		//VP: New images are uploaded successfully
		Assert.assertTrue(productControl.isElementPresent(driver.findElement(
				By.xpath(AddEditProductModel.IMAGE250_DISPLAY)).findElement(
				By.tagName("img"))));
		Assert.assertTrue(productControl.isElementPresent(driver.findElement(
				By.xpath(AddEditProductModel.IMAGE500_DISPLAY)).findElement(
				By.tagName("img"))));
		Assert.assertTrue(productControl.isElementPresent(driver.findElement(
				By.xpath(AddEditProductModel.IMAGE1000_DISPLAY)).findElement(
				By.tagName("img"))));

		//12.Tick to check the "Use Parent's Images" checkbox.
		productControl.click(AddEditProductModel.PARENT_IMAGE_LINK);
		//VP: The Delete Images confirmation dialog is displayed.
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.UploadFileMessage.USE_PARENT.getName()));
		//13. Click "Delete" on confirmation dialog
		productControl.selectConfirmationDialogOption("Delete");
		//The "Add Custom Images" item is hidden.
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.CATALOG_IMAGE_UPLOADER_250));
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.CATALOG_IMAGE_UPLOADER_500));
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.CATALOG_IMAGE_UPLOADER_1000));

	}	

	
	/*
	 * Verify that the EAN/UPC is validated when adding new product
	 */
	@Test
	public void TC052PMVE_07(){
		productControl.addLog("ID : TC052PMVE_07 : Verify that the EAN/UPC is validated when adding new product");
	   /*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Select a product from products list
			4. Click �Add Variant� link
			5. Fill value which including characters and digits into EAN and UPC fields
			6. Click �Save� link
			VP: Verify that new product is unable to save due to the error message inline of EAN/UPC fields
			7. Enter a digit string larger than 13 digits into EAN field and larger than 12 digits into UPC field
			8. Click "Save' link
			VP:  Verify that there is an error message displayed next to the EAN /UPC field
			9. Enter a digits string exactly 13 digits into EAN field and 12 digits into UPC field
			10. Click "Save" link
			VP: There is no error message for EAN/UPC displayed
		*/
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select a product from Products table
		productControl.selectAnAccessory();
		// 4. Click �Add New Variant� link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		//5. Fill value which including characters and digits into EAN and UPC fields
		productControl.editData(AddEditProductModel.EAN, RandomStringUtils.randomAlphanumeric(13));
		productControl.editData(AddEditProductModel.UPC, RandomStringUtils.randomAlphanumeric(12));
		//6. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		//VP: Verify that new product is unable to save due to the error message inline of EAN/UPC fields
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel
				.EanUpcErrorMessage.UPC_ONLY_DIGITS.getName()));
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel
				.EanUpcErrorMessage.EAN_ONLY_DIGITS.getName()));
		//7. Enter a digit string larger than 13 digits into EAN field and larger than 12 digits into UPC field
		productControl.editData(AddEditProductModel.EAN, RandomStringUtils.randomNumeric(14));
		productControl.editData(AddEditProductModel.UPC, RandomStringUtils.randomNumeric(13));
		//8. Click "Save' link
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		//VP:  Verify that there is an error message displayed next to the EAN /UPC field
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel
				.EanUpcErrorMessage.UPC_EXCEED_CHAR_LIMIT.getName()));
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel
				.EanUpcErrorMessage.EAN_EXCEED_CHAR_LIMIT.getName()));
		
		//9. Enter a digits string exactly 13 digits into EAN field and 12 digits into UPC field
		productControl.editData(AddEditProductModel.EAN, RandomStringUtils.randomNumeric(13));
		productControl.editData(AddEditProductModel.UPC, RandomStringUtils.randomNumeric(12));
		//10. Click "Save' link
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		//VP: There is no error message for EAN/UPC displayed
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.UPC_ERROR_MESSAGE));
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.EAN_ERROR_MESSAGE));
	}
	

	/*
	 * Verify that the tuning file, primay catalog images and markating material files could be upload again after canceling uploading tuning file
	 */
	@Test
	public void TC052PMVE_08() {
		productControl.addLog("ID : TC052PMVE_08: Verify that the tuning file, primay catalog images and markating material files could be upload again after canceling uploading tuning file");
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
			9. Repeat from step 5 to 8 with primary catalog images and marketing material files.
			VP: New tuning file, primary catalog iamges and marketing material files are uploaded successfully
		*/
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select a product from products table
		productControl.selectAnAccessory();
		// Click "Add new variant" link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 5. Upload a tuning file
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_TUNNING);
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
		// 8. Try to upload catalog image again
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.TUNING_UPLOAD_MESSAGE).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		
		//9. Repeat from step 5 to 8 with primary catalog images and marketing material files.
		//VP: New tuning file, primary catalog iamges and marketing material files are uploaded successfully
		//For primary catalog images
		// 5. Upload a tuning file
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_IMAGE);
		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[1]);
		productControl.uploadFileInterupt(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 6. Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName());
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_250)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_CANCELED.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_IMAGE_250));
		// 7. Click "Retry" link
		// 8. Try to upload catalog image again
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
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddEditProductModel.UploadFileMessage.UPLOAD_CANCELED.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_IMAGE_1000));
		// 7. Click "Retry" link
		// 8. Try to upload catalog image again
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_IMAGE_1000, AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		
		//For Marketing material
		// 5. Upload a tuning file
		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA[4]);
		productControl.uploadFileInterupt(AddEditProductModel.ADD_MARKETING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 6. Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName());
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.MARKETING_UPLOAD_MESSAGE).contains(AddEditProductModel.UploadFileMessage.UPLOAD_CANCELED.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_MARKETING));
		// 7. Click "Retry" link
		// 8. Try to upload catalog image again
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_MARKETING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.MARKETING_UPLOAD_MESSAGE).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		
	}

	
	/*
	 * Verify that user can upload primary images file by dragging and dropping file into "Add Custom Image File" area.
	 */
	@Test
	public void TC052PMVE_09(){
		productControl.addLog("ID : TC052PMVE_09 : Verify that user is able to upload wrong demension for primary catalog image");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Select a product from products table
			4. Click "Add new variant" link
			5. Upload a wrong image demension for 250x250 primary catalog image section
			6. Upload a wrong image demension for 500x500 primary catalog image section
			7. Upload a wrong image demension for 1000x100 primary catalog image section
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select an accessory from accessories table
		productControl.selectAnAccessory();
		// 4. Verify that the 040P Accessory Detail page is displayed
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 5. Click "Add New Variant" link from "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// VP: Verify that the 052P Model Variant Edit page is displayed
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.VARIANT_TITLE));	
		//6. Click "Upload Custom Images" link
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_IMAGE);
		// 5. Upload a wrong image demension for 250x250 primary catalog image section
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.IMG_NAME[1]);
		// 6. Upload a wrong image demension for 500x500 primary catalog image section
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.IMG_NAME[2]);
		// 7. Upload a wrong image demension for 1000x100 primary catalog image section
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_1000, AddEditProductModel.IMG_NAME[0]);
		/*
		 * Demenson of Three size of primary catalog image are automatically rezied and upload successfully and the message "Automaticall resized. Original XX x YY px" is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_250));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddEditProductModel.UploadFileMessage.FILE_500_PX.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_500));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddEditProductModel.UploadFileMessage.FILE_1000_PX.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddEditProductModel.UploadFileMessage.FILE_250_PX.getName()));
	}	
	
	/*
	 * Verify that user is able to upload tuning file, primary catalog image and marketing material file again when the uploaded file occurs error
	 */
//	@Test
	public void TC052PMVE_10() {
		productControl.addLog("ID : TC052PMVE_10 : Verify that user is able to upload tuning file, primary catalog image and marketing material file again when the uploaded file occurs error");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Select a product from products table
			4. Click "Add new variant" link
			5. Upload a tuning file
			6. Corrupt the network while the file is uploading
			VP: Verify that the "File upload error" message and Retry link are displayed
			7. Connect network successfully
			8. Click "Save" link
			9. Try to upload another tuning file
			10. Repeat from step 5 to 9 with primary catalog images and marketing material files.
			Tuning file, primary catalog images and marketing material files are able to upload again after errors.
		*/
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select a product from products table
		productControl.selectAnAccessory();
		// 4. Click "Add new variant" link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 5. Upload a tuning file
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_TUNNING);
		productControl.uploadFileInterupt(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 6. Corrupt the network while the file is uploading
		//productControl.interuptNetwork();
		productControl.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.TUNING_UPLOAD_MESSAGE).contains(AddEditProductModel.UploadFileMessage.UPLOAD_ERROR.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_TUNING));
		// 6. Connect network successfully
		//productControl.connectNetwork();
		// 7. Click "Retry" link
		// 8. Try to another maketing material file
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT.getName());
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.TUNING_UPLOAD_MESSAGE).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		
		//For Primary catalog images
		// 5. Upload a primary catalog image files
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_IMAGE);
		productControl.uploadFileInterupt(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 5. Corrupt the network while the file is uploading
		//Runtime.getRuntime().exec(Constant.RELEASE_NETWORK);
		//productControl.interuptNetwork();
		productControl.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddEditProductModel.UploadFileMessage.UPLOAD_ERROR.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_IMAGE_250));
		// 6. Connect network successfully
		//productControl.connectNetwork();
		// 7. Click "Retry" link
		// 8. Try to another maketing material file
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_IMAGE_250, AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		
		/* Repeat uploadiing for 500x500*/
		
		productControl.uploadFileInterupt(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 5. Corrupt the network while the file is uploading
		//Runtime.getRuntime().exec(Constant.RELEASE_NETWORK);
		//productControl.interuptNetwork();
		productControl.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddEditProductModel.UploadFileMessage.UPLOAD_ERROR.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_IMAGE_500));
		// 6. Connect network successfully
		//Runtime.getRuntime().exec(Constant.RENEW_NETWORK);
		//productControl.connectNetwork();
		// 7. Click "Retry" link
		// 8. Try to another maketing material file
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_IMAGE_500, AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		
		/* Repeat uploadiing for 1000x1000*/
		
		productControl.uploadFileInterupt(AddEditProductModel.SELECT_FILE_IMAGE_1000, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 5. Corrupt the network while the file is uploading
		//Runtime.getRuntime().exec(Constant.RELEASE_NETWORK);
		//productControl.interuptNetwork();
		productControl.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddEditProductModel.UploadFileMessage.UPLOAD_ERROR.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_IMAGE_1000));
		// 6. Connect network successfully
		//Runtime.getRuntime().exec(Constant.RENEW_NETWORK);
		//productControl.connectNetwork();
		// 7. Click "Retry" link
		// 8. Try to another maketing material file
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_IMAGE_1000, AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		
		// For marketing
		// 5. Upload a marketing file
		productControl.uploadFileInterupt(AddEditProductModel.ADD_MARKETING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 6. Corrupt the network while the file is uploading
		//productControl.interuptNetwork();
		productControl.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.MARKETING_UPLOAD_MESSAGE).contains(AddEditProductModel.UploadFileMessage.UPLOAD_ERROR.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_MARKETING));
		// 7. Connect network successfully
		//productControl.connectNetwork();
		// 8. Click "Retry" link
		// 9. Try to another maketing material file
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_MARKETING, AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.MARKETING_UPLOAD_MESSAGE).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
	}

	
}
