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

public class TestCorruptNetworkPartner extends CreatePage{
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
	 * Verify that user is able to upload tuning file again when the uploaded file occurs error
	 */
	@Test
	public void TC051PAE_43() throws IOException{
		result.addLog("ID : TC051PAE_43 : Verify that user is able to upload tuning file again when the uploaded file occurs error");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Click “Add Products” link
			4. Upload a tuning file
			5. Corrupt the network while the file is uploading
			VP: Verify that the "File upload error" message and Retry link are displayed
			6. Connect network successfully
			7. Click "Save" link
			8. Try to upload another tuning file
			//The valid tuning file is uploaded successfully
		*/
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
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
		Assert.assertTrue(home.getTextByXpath("//div" + AddAccessory.TUNING_UPLOAD_MESSAGE).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[3]));
		Assert.assertTrue(home.isElementPresent(AddAccessory.RETRY_UPLOAD_TUNING));
		// 6. Connect network successfully
		Runtime.getRuntime().exec(Constant.RENEW_NETWORK);
		// 7. Click "Retry" link
		// 8. Try to another maketing material file
		home.uploadFile(AddAccessory.RETRY_UPLOAD_TUNING, Constant.tuning_hpxtt);
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddAccessory.TUNING_UPLOAD_MESSAGE).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[0]));
	}
	
	/*
	 * Verify that user is able to upload primary catalog image file again when the uploaded file occurs error
	 */
	@Test
	public void TC051PAE_44() throws IOException, InterruptedException{
		result.addLog("ID : TC051PAE_44 : Verify that user is able to upload primary catalog image file again when the uploaded file occurs error");
		/*
		1. Log into DTS portal as a partner admin
		2. Navigate to "Products" page
		3. Click “Add Products” link
		4. Upload three size of primary catalog image
		5. Corrupt the network while the files are uploading
		VP: Verify that the "File upload error" message and Retry link are displayed for each image
		6. Connect network successfully
		7. Click "Save" link
		8. Try to upload three another images
		*/
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click “Add Products” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Upload a tuning file
		home.uploadFileInterupt(AddAccessory.SELECT_FILE_IMAGE_250, Constant.test_upload_cancel);
		// 5. Corrupt the network while the file is uploading
		home.interuptNetwork();
		home.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[3]));
		Assert.assertTrue(home.isElementPresent(AddAccessory.RETRY_UPLOAD_IMAGE_250));
		// 6. Connect network successfully
		home.connectNetwork();
		// 7. Click "Retry" link
		// 8. Try to another maketing material file
		home.uploadFile(AddAccessory.RETRY_UPLOAD_IMAGE_250, Constant.IMG_NAME[0]);
		/*
		 * Verify that primary catalog image file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[0]));
		
		/* Repeat uploadiing for 500x500*/
		
		home.uploadFileInterupt(AddAccessory.SELECT_FILE_IMAGE_500, Constant.test_upload_cancel);
		// 5. Corrupt the network while the file is uploading
		home.interuptNetwork();
		home.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[3]));
		Assert.assertTrue(home.isElementPresent(AddAccessory.RETRY_UPLOAD_IMAGE_500));
		// 6. Connect network successfully
		//Runtime.getRuntime().exec(Constant.RENEW_NETWORK);
		home.connectNetwork();
		// 7. Click "Retry" link
		// 8. Try to another maketing material file
		home.uploadFile(AddAccessory.RETRY_UPLOAD_IMAGE_500, Constant.IMG_NAME[1]);
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[0]));
		
		/* Repeat uploadiing for 1000x1000*/
		
		home.uploadFileInterupt(AddAccessory.SELECT_FILE_IMAGE_1000, Constant.test_upload_cancel);
		// 5. Corrupt the network while the file is uploading
		//Runtime.getRuntime().exec(Constant.RELEASE_NETWORK);
		home.interuptNetwork();
		home.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[3]));
		Assert.assertTrue(home.isElementPresent(AddAccessory.RETRY_UPLOAD_IMAGE_1000));
		// 6. Connect network successfully
		//Runtime.getRuntime().exec(Constant.RENEW_NETWORK);
		home.connectNetwork();
		// 7. Click "Retry" link
		// 8. Try to another maketing material file
		home.uploadFile(AddAccessory.RETRY_UPLOAD_IMAGE_1000, Constant.IMG_NAME[2]);
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddAccessory.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[0]));
	}
	
	/*
	 * Verify that user is able to upload marketing material again when the uploaded file occurs error
	 */
	@Test
	public void TC051PAE_48() {
		result.addLog("ID : TC051PAE_48 : Verify that user is able to upload marketing material again when the uploaded file occurs error");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Click “Add Products” link
			4. Upload a marketing material file
			5. Corrupt the network while the files are uploading
			VP: Verify that the "File upload error" message and Retry link is displayed
			6. Connect network successfully
			7. Click "Save" link
			8. Try to another maketing material file
			//New marketing material file is uploaded successfully
		*/
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click “Add Products” link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Upload a marketing file
		home.uploadFileInterupt(AddAccessory.ADD_MARKETING, Constant.test_upload_cancel);
		// 5. Corrupt the network while the file is uploading
		home.interuptNetwork();
		home.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddAccessory.MARKETING_UPLOAD_MESSAGE).contains(AddAccessory.UPLOAD_FILE_MESSSAGE[3]));
		Assert.assertTrue(home.isElementPresent(AddAccessory.RETRY_UPLOAD_MARKETING));
		// 6. Connect network successfully
		home.connectNetwork();
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
	public void TC052PMVE_41() {
		result.addLog("ID : TC052PMVE_41 : Verify that user is able to upload tuning file again when the uploaded file occurs error");
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
			//The valid tuning file is uploaded successfully
		*/
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select a product from products table
		home.selectAnAccessory();
		// 4. Click "Add new variant" link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 5. Upload a tuning file
		home.click(AddVariant.UPLOAD_CUSTOM_TUNNING);
		home.uploadFileInterupt(AddVariant.ADD_TUNNING, Constant.test_upload_cancel);
		// 6. Corrupt the network while the file is uploading
		home.interuptNetwork();
		home.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddVariant.TUNING_UPLOAD_MESSAGE).contains(AddVariant.UPLOAD_FILE_MESSSAGE[3]));
		Assert.assertTrue(home.isElementPresent(AddVariant.RETRY_UPLOAD_TUNING));
		// 6. Connect network successfully
		home.connectNetwork();
		// 7. Click "Retry" link
		// 8. Try to another maketing material file
		home.uploadFile(AddVariant.RETRY_UPLOAD_TUNING, Constant.tuning_hpxtt);
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddVariant.TUNING_UPLOAD_MESSAGE).contains(AddVariant.UPLOAD_FILE_MESSSAGE[0]));
	}
	
	/*
	 * Verify that user is able to upload primary catalog image file again when the uploaded file occurs error
	 */
	@Test
	public void TC052PMVE_42() {
		result.addLog("ID : TC052PMVE_42 : Verify that user is able to upload primary catalog image file again when the uploaded file occurs error");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Select a product from products table
			4. Click "Add new variant" link
			5. Upload three size of primary catalog image
			6. Corrupt the network while the files are uploading
			VP: Verify that the "File upload error" message and Retry link are displayed for each image
			7. Connect network successfully
			8. Click "Save" link
			9. Try to upload three another images
			VP: All three catalog images are uploaded successfully
		*/
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select a product from products table
		home.selectAnAccessory();
		// 4. Click "Add new variant" link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 5. Upload a tuning file
		home.click(AddVariant.UPLOAD_CUSTOM_IMAGE);
		home.uploadFileInterupt(AddVariant.SELECT_FILE_IMAGE_250, Constant.test_upload_cancel);
		// 5. Corrupt the network while the file is uploading
		//Runtime.getRuntime().exec(Constant.RELEASE_NETWORK);
		home.interuptNetwork();
		home.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddVariant.UPLOAD_FILE_MESSSAGE[3]));
		Assert.assertTrue(home.isElementPresent(AddVariant.RETRY_UPLOAD_IMAGE_250));
		// 6. Connect network successfully
		home.connectNetwork();
		// 7. Click "Retry" link
		// 8. Try to another maketing material file
		home.uploadFile(AddVariant.RETRY_UPLOAD_IMAGE_250, Constant.IMG_NAME[0]);
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddVariant.UPLOAD_FILE_MESSSAGE[0]));
		
		/* Repeat uploadiing for 500x500*/
		
		home.uploadFileInterupt(AddVariant.SELECT_FILE_IMAGE_500, Constant.test_upload_cancel);
		// 5. Corrupt the network while the file is uploading
		//Runtime.getRuntime().exec(Constant.RELEASE_NETWORK);
		home.interuptNetwork();
		home.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddVariant.UPLOAD_FILE_MESSSAGE[3]));
		Assert.assertTrue(home.isElementPresent(AddVariant.RETRY_UPLOAD_IMAGE_500));
		// 6. Connect network successfully
		//Runtime.getRuntime().exec(Constant.RENEW_NETWORK);
		home.connectNetwork();
		// 7. Click "Retry" link
		// 8. Try to another maketing material file
		home.uploadFile(AddVariant.RETRY_UPLOAD_IMAGE_500, Constant.IMG_NAME[1]);
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddVariant.UPLOAD_FILE_MESSSAGE[0]));
		
		/* Repeat uploadiing for 1000x1000*/
		
		home.uploadFileInterupt(AddVariant.SELECT_FILE_IMAGE_1000, Constant.test_upload_cancel);
		// 5. Corrupt the network while the file is uploading
		//Runtime.getRuntime().exec(Constant.RELEASE_NETWORK);
		home.interuptNetwork();
		home.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddVariant.UPLOAD_FILE_MESSSAGE[3]));
		Assert.assertTrue(home.isElementPresent(AddVariant.RETRY_UPLOAD_IMAGE_1000));
		// 6. Connect network successfully
		//Runtime.getRuntime().exec(Constant.RENEW_NETWORK);
		home.connectNetwork();
		// 7. Click "Retry" link
		// 8. Try to another maketing material file
		home.uploadFile(AddVariant.RETRY_UPLOAD_IMAGE_1000, Constant.IMG_NAME[2]);
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddVariant.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddVariant.UPLOAD_FILE_MESSSAGE[0]));
	}
	
	/*
	 * Verify that user is able to upload marketing material again when the uploaded file occurs error
	 */
	@Test
	public void TC052PMVE_46() {
		result.addLog("ID : TC052PMVE_42 : Verify that user is able to upload marketing material again when the uploaded file occurs error");
		/*
			1. Log into DTS portal as a partner admin
			2. Navigate to "Products" page
			3. Select a product from products table
			4. Click "Add new variant" link
			5. Upload a marketing material file
			6. Corrupt the network while the files are uploading
			VP: Verify that the "File upload error" message and Retry link is displayed
			7. Connect network successfully
			8. Click "Save" link
			9. Try to another maketing material file
			VP: Verify that user is able to upload marketing material again when the uploaded file occurs error
		*/
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Select a product from products table
		home.selectAnAccessory();
		// 4. Click "Add new variant" link
		home.click(AccessoryInfo.ADD_VARIANT);
		// 4. Upload a marketing file
		home.uploadFileInterupt(AddVariant.ADD_MARKETING, Constant.test_upload_cancel);
		// 5. Corrupt the network while the file is uploading
		home.interuptNetwork();
		home.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddVariant.MARKETING_UPLOAD_MESSAGE).contains(AddVariant.UPLOAD_FILE_MESSSAGE[3]));
		Assert.assertTrue(home.isElementPresent(AddVariant.RETRY_UPLOAD_MARKETING));
		// 6. Connect network successfully
		home.connectNetwork();
		// 7. Click "Retry" link
		// 8. Try to another maketing material file
		home.uploadFile(AddVariant.RETRY_UPLOAD_MARKETING, Constant.AUDIO_ROUTE_FILE);
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(home.getTextByXpath("//div" + AddVariant.MARKETING_UPLOAD_MESSAGE).contains(AddVariant.UPLOAD_FILE_MESSSAGE[0]));
	}

}
