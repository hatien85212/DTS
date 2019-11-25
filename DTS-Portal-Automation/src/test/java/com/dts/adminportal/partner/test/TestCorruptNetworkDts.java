package com.dts.adminportal.partner.test;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;

import dts.com.adminportal.model.AccessoryInfo;
import dts.com.adminportal.model.AccessoryMain;
import dts.com.adminportal.model.AddAccessory;
import dts.com.adminportal.model.AddVariant;
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.Xpath;

public class TestCorruptNetworkDts extends CreatePage{
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
	 * Verify that user is able to upload tuning file again when the uploaded file occurs error
	 */
	@Test
	public void TC051DAE_85() throws IOException{
		result.addLog("ID : 051DAE_85: Verify that user is able to upload tuning file again when the uploaded file occurs error");
		/*
			1. Log into DTS portal as a DTS admin
			2. Navigate to "Products" page
			3. Click “Add Products” link
			4. Upload a tuning file
			5. Corrupt the network while the file is uploading
			VP: Verify that the "File upload error" message and Retry link are displayed
			6. Connect network successfully
			7. Click "Retry" link
			8. Try to upload another tuning file
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click “Add Products” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Upload a tuning file
		home.uploadFileInterupt(AddAccessory.ADD_TUNNING, Constant.test_upload_cancel);
		// 5. Corrupt the network while the file is uploading
		Runtime.getRuntime().exec(Constant.RELEASE_NETWORK);
		home.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(home.getTextByXpath(AddAccessory.TUNING_UPLOAD_MESSAGE).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[3]));
		Assert.assertTrue(home.isElementPresent(AddAccessory.RETRY_UPLOAD_TUNING));
		// 6. Connect network successfully
		Runtime.getRuntime().exec(Constant.RENEW_NETWORK);
		// 7. Click "Retry" link
		// 8. Try to upload another tuning file
		home.uploadFile(AddAccessory.RETRY_UPLOAD_TUNING, Constant.tuning_hpxtt);
		/*
		 * The valid tuning file is uploaded successfully
		 */
		Assert.assertTrue(home.isTunningUploaded(AddAccessory.UPLOADED_TUNING, Constant.tuning_hpxtt));
	}
	
	/*
	 * Verify that user is able to upload primary catalog image file again when the uploaded file occurs error
	 */
	@Test
	public void TC051DAE_86() throws IOException{
		result.addLog("ID : 051DAE_86: Verify that user is able to upload primary catalog image file again when the uploaded file occurs error");
		/*
			1. Log into DTS portal as a DTS admin
			2. Navigate to "Products" page
			3. Click “Add Products” link
			4. Upload three size of primary catalog image
			5. Corrupt the network while the files are uploading
			VP: Verify that the "File upload error" message and Retry link are displayed for each image
			6. Connect network successfully
			7. Click "Retry" link
			8. Try to upload three another images
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click “Add Products” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Upload three size of primary catalog image
		home.uploadFileInterupt(AddAccessory.SELECT_FILE_IMAGE_250, Constant.test_upload_cancel);
		// 5. Corrupt the network while the files are uploading
		Runtime.getRuntime().exec(Constant.RELEASE_NETWORK);
		home.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed for each image
		 */
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[3]));
		Assert.assertTrue(home.isElementPresent(AddAccessory.RETRY_UPLOAD_IMAGE_250));
		// 6. Connect network successfully
		Runtime.getRuntime().exec(Constant.RENEW_NETWORK);
		// 7. Click "Retry" link
		// 8. Try to upload three another images
		home.uploadFile(AddAccessory.RETRY_UPLOAD_IMAGE_250, Constant.IMG_NAME[0]);
		/*
		 * All three catalog images are uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath(AddAccessory.CATALOG_IMAGE_UPLOAD_INFO_250).contains(Constant.IMG_NAME[0]));
	}
	
	/*
	 * Verify that user is able to upload marketing material again when the uploaded file occurs error
	 */
	@Test
	public void TC051DAE_90() throws IOException {
		result.addLog("ID : 051DAE_90: Verify that user is able to upload marketing material again when the uploaded file occurs error");
		/*
			1. Log into DTS portal as a DTS admin
			2. Navigate to "Products" page
			3. Click “Add Products” link
			4. Upload a marketing material file
			5. Corrupt the network while the files are uploading
			VP: Verify that the "File upload error" message and Retry link is displayed
			6. Connect network successfully
			7. Click "Retry" link
			8. Try to another maketing material file
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Click “Add Products” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Upload a marketing material file
		home.uploadFileInterupt(AddAccessory.ADD_MARKETING, Constant.test_upload_cancel);
		// 5. Corrupt the network while the file is uploading
		Runtime.getRuntime().exec(Constant.RELEASE_NETWORK);
		home.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddAccessory.MARKETING_UPLOAD_MESSAGE).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[3]));
		Assert.assertTrue(home.isElementPresent(AddAccessory.RETRY_UPLOAD_MARKETING));
		// 6. Connect network successfully
		Runtime.getRuntime().exec(Constant.RENEW_NETWORK);
		// 7. Click "Retry" link
		// 8. Try to another maketing material file
		home.uploadFile(AddAccessory.RETRY_UPLOAD_MARKETING, Constant.AUDIO_ROUTE_FILE);
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddAccessory.MARKETING_UPLOAD_MESSAGE).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[0]));
	}
	
	/*
	 * Verify that user is able to upload tuning file again when the uploaded file occurs error
	 */
	@Test
	public void TC051DAE_92() throws IOException {
		result.addLog("ID : 051DAE_92: Verify that user is able to upload tuning file again when the uploaded file occurs error");
		/*
			1. Log into DTS portal as a DTS admin
			2. Navigate to "Products" page
			3. Select a product from products table
			4. Click "Add new variant" link
			5. Upload a tuning file
			6. Corrupt the network while the file is uploading
			VP: Verify that the "File upload error" message and Retry link are displayed
			7. Connect network successfully
			8. Click "Retry" link
			9. Try to upload another tuning file
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select a product from products table
		home.selectAnAccessory();
		// 4. Click "Add new variant" link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 5. Upload a tuning file
		home.click(AddVariant.UPLOAD_CUSTOM_TUNNING);
		home.uploadFileInterupt(AddVariant.ADD_TUNNING, Constant.test_upload_cancel);
		// 6. Corrupt the network while the file is uploading
		Runtime.getRuntime().exec(Constant.RELEASE_NETWORK);
		home.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(home.getTextByXpath(AddVariant.TUNING_UPLOAD_MESSAGE).contains(AddVariant.UPLOAD_FILE_MESSSAGE[3]));
		Assert.assertTrue(home.isElementPresent(AddVariant.RETRY_UPLOAD_TUNING));
		// 7. Connect network successfully
		Runtime.getRuntime().exec(Constant.RENEW_NETWORK);
		// 8. Click "Retry" link
		// 9. Try to upload another tuning file
		home.uploadFile(AddVariant.RETRY_UPLOAD_TUNING, Constant.tuning_hpxtt);
		/*
		 * Verify that The valid tuning file is uploaded successfully
		 */
		Assert.assertTrue(home.isTunningUploaded(AddVariant.UPLOADED_TUNING, Constant.tuning_hpxtt));
	}
	
	/*
	 * Verify that user is able to upload primary catalog image file again when the uploaded file occurs error
	 */
	@Test
	public void TC051DAE_93() throws IOException {
		result.addLog("ID : 051DAE_93: Verify that user is able to upload primary catalog image file again when the uploaded file occurs error");
		/*
			1. Log into DTS portal as a DTS admin
			2. Navigate to "Products" page
			3. Select a product from products table
			4. Click "Add new variant" link
			5. Upload three size of primary catalog image
			6. Corrupt the network while the files are uploading
			VP: Verify that the "File upload error" message and Retry link are displayed for each image
			7. Connect network successfully
			8. Click "Retry" link
			9. Try to upload three another images
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select a product from products table
		home.selectAnAccessory();
		// 4. Click "Add new variant" link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 5. Upload three size of primary catalog image
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		home.uploadFileInterupt(AddVariant.SELECT_FILE_IMAGE_250, Constant.test_upload_cancel);
		// 6. Corrupt the network while the file is uploading
		Runtime.getRuntime().exec(Constant.RELEASE_NETWORK);
		home.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed for each image
		 */
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddVariant.UPLOAD_FILE_MESSSAGE[3]));
		Assert.assertTrue(home.isElementPresent(AddVariant.RETRY_UPLOAD_IMAGE_250));
		// 7. Connect network successfully
		Runtime.getRuntime().exec(Constant.RENEW_NETWORK);
		// 8. Click "Retry" link
		// 9. Try to upload three another images
		home.uploadFile(AddVariant.RETRY_UPLOAD_IMAGE_250, Constant.IMG_NAME[0]);
		/*
		 * Verify that All three catalog images are uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath(AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddVariant.UPLOAD_FILE_MESSSAGE[0]));
	}
	
	/*
	 * Verify that user is able to upload marketing material again when the uploaded file occurs error
	 */
	@Test
	public void TC051DAE_97() throws IOException {
		result.addLog("ID : 051DAE_97: Verify that user is able to upload marketing material again when the uploaded file occurs error");
		/*
			1. Log into DTS portal as a DTS admin
			2. Navigate to "Products" page
			3. Select a product from products table
			4. Click "Add new variant" link
			5. Upload a marketing material file
			6. Corrupt the network while the files are uploading
			VP: Verify that the "File upload error" message and Retry link is displayed
			7. Connect network successfully
			8. Click "Save" link
			9. Try to another maketing material file
		*/
		// 2. Navigate to "Products" page
		home.click(Xpath.linkAccessories);
		// 3. Select a product from products table
		home.selectAnAccessory();
		// 4. Click "Add new variant" link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 5. Upload a marketing material file
		home.uploadFileInterupt(AddVariant.ADD_MARKETING, Constant.test_upload_cancel);
		// 6. Corrupt the network while the files are uploading
		Runtime.getRuntime().exec(Constant.RELEASE_NETWORK);
		home.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link is displayed
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddVariant.MARKETING_UPLOAD_MESSAGE).contains(AddVariant.UPLOAD_FILE_MESSSAGE[3]));
		Assert.assertTrue(home.isElementPresent(AddVariant.RETRY_UPLOAD_MARKETING));
		// 7. Connect network successfully
		Runtime.getRuntime().exec(Constant.RENEW_NETWORK);
		// 8. Click "Retry" link
		// 9. Try to another maketing material file
		home.uploadFile(AddVariant.RETRY_UPLOAD_MARKETING, Constant.AUDIO_ROUTE_FILE);
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddVariant.MARKETING_UPLOAD_MESSAGE).contains(AddVariant.UPLOAD_FILE_MESSSAGE[0]));
	}
}
