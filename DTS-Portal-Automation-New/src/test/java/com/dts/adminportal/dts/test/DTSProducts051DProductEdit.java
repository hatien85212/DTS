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
		userControl.disablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Approve_product_tunings.getName());
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
		productControl.logout();
		// Enable "Approve product tunings" privilege again
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to user page
		productControl.click(PageHome.LINK_USERS);
		// Select DTS user
		userControl.dtsSelectUserByEmail(DTS_USER);
		// Click Edit link
		productControl.click(UserMgmt.EDIT);
		userControl.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Approve_product_tunings.getName());
		productControl.click(AddUser.SAVE);
		

	}
	
	/*
	 * Verify that the product and variant tuning file are validated for uploading
	 */
	@Test
	public void TC051DAE_05(){
		LogReporter.addLog("ID : 051DAE_05 : Verify that the product and variant tuning file are validated for uploading");
		/*
			1. Log into DTS portal as a DTS user	
			2. Navigate to "Products" page	
			3. Click "Add product" link	
			4. Upload an invalid tuning file	
			VP:An error message is displayed and the invalid tuning file is not added.
			5. Upload a valid tuning zip file	
			VP: The uploaded tuning link is displayed as "DTS Tuning".
			6. Delete this tuning file	
			7. Upload a lineout only hpxtt tuning file	
			VP: The tuning hpxtt file is upload successfully.
			8. Select the connection type "Bluetooth"	
			9. Click 'Save' link	
			VP: The error message is shown.
			10. Select the connection type "LineOut"	
			11. Click "Save" link	
			VP: The error message is shown.
			12. Delete this tuning file	
			13. Upload a bluetooth only hpxtt tuning file	
			14. Unselect the connection type "Bluetooth"	
			15. Select the connection type "Wired" only	
			16. Click 'Save' link	
			VP: The error message is shown.
			17. Select the connection type "Bluetooth"	
			18. Click "Save" link	
			VP: The error message is shown.
			19. Delete this tuning file	
			20. Upload a tuning file containing both bluetooth0 and lineout0	
			21. Click 'Save' link	
			VP: The tuning file is uploaded successfully and product is saved
			22. Click "Edit Verdion" link	
			23. Unselect the connection type "Bluetooth"	
			24. Click "Save" link	
			VP: The tuning file is uploaded successfully and product is saved
			25. Repeat from step 22 to step 24 but the connection type "Bluetooth" is selected and "LineOut" is unselected	
			VP: The tuning file is uploaded successfully and product is saved
			26. Click "Edit Version" link	
			27. Delete tuning file 	
			28. Upload a bluetooth only hpxtt tuning file	
			29. Click "Save" link	
			VP: The tuning file is uploaded successfully and product is saved
			30. Repeat from step 26 to step 29 with the lineout only hpxtt tuning file but the connection type "LineOut" is selected and "Bluetooth" is unselected	
			VP: The tuning file is uploaded successfully and product is saved
			31. Click "Add New Variant" link	
			32. Fill valid values into required fields	
			33. Click "Upload Custom Tuning" link	
			34. Repeat from step 4 to step 6	
			35. Upload a bluetooth only hpxtt tuning file	
			36. Click "Save" link	
			VP: The error message is shown.
			37. Upload a lineout only hpxtt tuning file	
			38. Click "Save" link	
			VP: The tuning file is uploaded successfully and variant is saved
			40. Click "Edit Version" link	
			41. Upload a tuning file containing both bluetooth0 and lineout0	
			42. Click "Save" link	
			VP: The tuning file is uploaded successfully and variant is saved
			43. Click product model link	
			44. Click "Edit Version" link	
			45. Set the connection type to "Bluetooth"	
			46. Click "Save" link	
			47. Select above variant	
			48. Click "Edit Version" link	
			49. Click "Upload Custom Tuning" link	
			50. Upload a lineout only hpxtt tuning file	
			51. Click "Save" link	
			VP: The error message is shown.
			52. Upload a bluetooth only hpxtt tuning file	
			53. Click "Save" link	
			VP: The tuning file is uploaded successfully and variant is saved
			54. Click "Edit Version" link	
			55. Upload a tuning file containing both bluetooth0 and lineout0	
			56. Click "Save" link	
			VP: The tuning file is uploaded successfully and variant is saved
			57. Repeat from step 43 to step 49 with the connection type both lineout and bluetooth	
			58. Upload a lineout only hpxtt tuning file	
			59. Click "Save" link	
			VP: The error message is shown.
			60. Upload a bluetooth only hpxtt tuning file	
			61. Click "Save" link	
			VP: The error message is shown.
			62. Delete tuning file
			63. Upload a tuning file containing both bluetooth0 and lineout0	
			64. Click "Save" link	
			VP: The tuning file is uploaded successfully and variant is saved

		*/
		
		//1. Log into DTS portal as a DTS user	
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//2. Navigate to "Products" page	
		productControl.click(PageHome.linkAccessories);
		//3. Click "Add product" link	
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataPro = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		dataPro.remove("save");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataPro);
		//4. Upload an invalid tuning file	
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_Invalid.getName());
		/*
		 * VP:An error message is displayed and the invalid tuning file is not added.
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.TUNING_MESSAGE));
		//5. Upload a valid tuning zip file	
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_TUNING, AddEditProductModel.FileUpload.Tuning_ZIP.getName());
		/*
		 * VP: The uploaded tuning link is displayed as "DTS Tuning".
		 */
		Assert.assertTrue(productControl.isTunningUploaded(AddEditProductModel.UPLOADED_TUNING, AddEditProductModel.FileUpload.Tuning_ZIP.getName()));
		//6. Delete this tuning file
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);	
		//7. Upload a lineout only hpxtt tuning file
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName());
		/*
		 * VP: The tuning hpxtt file is upload successfully.
		 */
		Assert.assertTrue(productControl.isTunningUploaded(AddEditProductModel.UPLOADED_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName()));
		//8. Select the connection type "Bluetooth"
		productControl.selectACheckbox(AddEditProductModel.BLUETOOTH_CHECKBOX);
		//9. Click 'Save' link	
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: The error message is shown.
		 */
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.TUNING_MESSAGE_POPUP),AddEditProductModel.UploadFileMessage.CONNECTION_TYPE_NOTMATHCH.getName());
		productControl.selectConfirmationDialogOption("OK");
		//10. Select the connection type "LineOut"	
		productControl.selectACheckbox(AddEditProductModel.WIRED_CHECKBOX);
		//11. Click "Save" link	
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: The error message is shown.
		 */
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.TUNING_MESSAGE_POPUP),AddEditProductModel.UploadFileMessage.CONNECTION_TYPE_NOTMATHCH.getName());
		productControl.selectConfirmationDialogOption("OK");
		//12. Delete this tuning file
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		//13. Upload a bluetooth only hpxtt tuning file	
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.Tuning_HPXTT_Bluetooth.getName());
		//14. Unselect the connection type "Bluetooth"
		productControl.uncheckACheckbox(AddEditProductModel.BLUETOOTH_CHECKBOX);
		//15. Select the connection type "Wired" only	
		productControl.selectACheckbox(AddEditProductModel.WIRED_CHECKBOX);
		//16. Click 'Save' link	
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: The error message is shown.
		 */
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.TUNING_MESSAGE_POPUP),AddEditProductModel.UploadFileMessage.CONNECTION_TYPE_NOTMATHCH.getName());
		productControl.selectConfirmationDialogOption("OK");
		//17. Select the connection type "Bluetooth"	
		productControl.selectACheckbox(AddEditProductModel.BLUETOOTH_CHECKBOX);
		//18. Click "Save" link	
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: The error message is shown.
		 */
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.TUNING_MESSAGE_POPUP),AddEditProductModel.UploadFileMessage.CONNECTION_TYPE_NOTMATHCH.getName());
		productControl.selectConfirmationDialogOption("OK");
		//19. Delete this tuning file	
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		//20. Upload a tuning file containing both bluetooth0 and lineout0	
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.Tuning_HPXTT_BothLineBlue.getName());
		//21. Click 'Save' link	
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: The tuning file is uploaded successfully and product is saved
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		//22. Click "Edit Verdion" link	
		productControl.click(ProductDetailModel.EDIT_MODE);
		//23. Unselect the connection type "Bluetooth"	
		productControl.uncheckACheckbox(AddEditProductModel.BLUETOOTH_CHECKBOX);
		//24. Click "Save" link	
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: The tuning file is uploaded successfully and product is saved
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		//25. Repeat from step 22 to step 24 but the connection type "Bluetooth" is selected and "LineOut" is unselected
		productControl.click(ProductDetailModel.EDIT_MODE);
		productControl.selectACheckbox(AddEditProductModel.BLUETOOTH_CHECKBOX);
		productControl.uncheckACheckbox(AddEditProductModel.WIRED_CHECKBOX);
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: The tuning file is uploaded successfully and product is saved
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		//26. Click "Edit Version" link
		productControl.click(ProductDetailModel.EDIT_MODE);
		//27. Delete tuning file 
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		//28. Upload a bluetooth only hpxtt tuning file	
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.Tuning_HPXTT_Bluetooth.getName());
		//29. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: The tuning file is uploaded successfully and product is saved
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		//30. Repeat from step 26 to step 29 with the lineout only hpxtt tuning file but the connection type "LineOut" is selected and "Bluetooth" is unselected	
		productControl.click(ProductDetailModel.EDIT_MODE);
		productControl.selectACheckbox(AddEditProductModel.WIRED_CHECKBOX);
		productControl.uncheckACheckbox(AddEditProductModel.BLUETOOTH_CHECKBOX);
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName());
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: The tuning file is uploaded successfully and product is saved
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		//31. Click "Add New Variant" link	
		productControl.click(ProductDetailModel.ADD_VARIANT);
		//32. Fill valid values into required fields
		Hashtable<String,String> variantData = TestData.variantData(false, false, false);
		variantData.remove("save");
		productControl.addVariant(AddEditProductModel.getVariantHash(),variantData);
		//33. Click "Upload Custom Tuning" link	
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_TUNNING);
		//34. Repeat from step 4 to step 6	
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_Invalid.getName());
		/*
		 * VP:An error message is displayed and the invalid tuning file is not added.
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.TUNING_MESSAGE));
		//Upload a valid tuning zip file	
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_TUNING, AddEditProductModel.FileUpload.Tuning_ZIP.getName());
		/*
		 * VP: The uploaded tuning link is displayed as "DTS Tuning".
		 */
		Assert.assertTrue(productControl.isTunningUploaded(AddEditProductModel.UPLOADED_TUNING, AddEditProductModel.FileUpload.Tuning_ZIP.getName()));
		//Delete this tuning file
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);	
		//35. Upload a bluetooth only hpxtt tuning file	
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.Tuning_HPXTT_Bluetooth.getName());
		//36. Click "Save" link	
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * VP: The error message is shown.
		 */
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.TUNING_MESSAGE_POPUP),AddEditProductModel.UploadFileMessage.CONNECTION_TYPE_NOTMATHCH.getName());
		productControl.selectConfirmationDialogOption("OK");
		//37. Upload a lineout only hpxtt tuning file
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName());
		//38. Click "Save" link	
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * VP: The tuning file is uploaded successfully and variant is saved
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		//40. Click "Edit Version" link	
		productControl.click(VariantInfo.EDIT_VARIANT);
		//41. Upload a tuning file containing both bluetooth0 and lineout0	
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.Tuning_HPXTT_BothLineBlue.getName());
		//42. Click "Save" link	
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * VP: The tuning file is uploaded successfully and variant is saved
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		//43. Click product model link
		productControl.click(VariantInfo.PRODUCT_LINK);
		//44. Click "Edit Version" link	
		productControl.click(ProductDetailModel.EDIT_MODE);
		//45. Set the connection type to "Bluetooth"	
		productControl.selectACheckbox(AddEditProductModel.BLUETOOTH_CHECKBOX);
		productControl.uncheckACheckbox(AddEditProductModel.WIRED_CHECKBOX);
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		//46. Click "Save" link	
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		//47. Select above variant	
		productControl.selectAVariantbyName(variantData.get("name"));
		//48. Click "Edit Version" link	
		productControl.click(VariantInfo.EDIT_VARIANT);
		//49. Click "Upload Custom Tuning" link	
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_TUNNING);
		//50. Upload a lineout only hpxtt tuning file	
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName());
		//51. Click "Save" link	
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * VP: The error message is shown.
		 */
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.TUNING_MESSAGE_POPUP),AddEditProductModel.UploadFileMessage.CONNECTION_TYPE_NOTMATHCH.getName());
		productControl.selectConfirmationDialogOption("OK");
		//52. Upload a bluetooth only hpxtt tuning file	
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.Tuning_HPXTT_Bluetooth.getName());
		//53. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * VP: The tuning file is uploaded successfully and variant is saved
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		//54. Click "Edit Version" link	
		productControl.click(VariantInfo.EDIT_VARIANT);
		//55. Upload a tuning file containing both bluetooth0 and lineout0	
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.Tuning_HPXTT_BothLineBlue.getName());
		//56. Click "Save" link	
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * VP: The tuning file is uploaded successfully and variant is saved
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		//57. Repeat from step 43 to step 49 with the connection type both lineout and bluetooth
		productControl.click(VariantInfo.PRODUCT_LINK);
		//Click "Edit Version" link	
		productControl.click(ProductDetailModel.EDIT_MODE);
		//Set the connection type to "Bluetooth"	
		productControl.selectACheckbox(AddEditProductModel.BLUETOOTH_CHECKBOX);
		productControl.selectACheckbox(AddEditProductModel.WIRED_CHECKBOX);
		//Click "Save" link	
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		//Select above variant	
		productControl.selectAVariantbyName(variantData.get("name"));
		//Click "Edit Version" link	
		productControl.click(VariantInfo.EDIT_VARIANT);
		//Click "Upload Custom Tuning" link	
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_TUNNING);
		//58. Upload a lineout only hpxtt tuning file
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName());
		//59. Click "Save" link	
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * VP: The error message is shown.
		 */
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.TUNING_MESSAGE_POPUP),AddEditProductModel.UploadFileMessage.CONNECTION_TYPE_NOTMATHCH.getName());
		productControl.selectConfirmationDialogOption("OK");
		//60. Upload a bluetooth only hpxtt tuning file	
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.Tuning_HPXTT_Bluetooth.getName());
		//61. Click "Save" link	
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * VP: The error message is shown.
		 */
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.TUNING_MESSAGE_POPUP),AddEditProductModel.UploadFileMessage.CONNECTION_TYPE_NOTMATHCH.getName());
		productControl.selectConfirmationDialogOption("OK");
		//62. Delete tuning file
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		//63. Upload a tuning file containing both bluetooth0 and lineout0
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.Tuning_HPXTT_BothLineBlue.getName());
		//64. Click "Save" link	
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * VP: The tuning file is uploaded successfully and variant is saved
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// Delete product
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
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
		if(!productControl.checkMessageDisplay(AddEditProductModel.EanUpcErrorMessage.EAN_UNIQUE.getName())){
			productControl.addErrorLog("PDPP-467: 051D Accessory Edit : The warning message is not displayed when adding duplicate EAN/UPC");
			Assert.assertTrue(false);
		}
		/*
		 * Verify that A warning message "Warning: A model already exist with this EAN and UPC code"
		 */
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.EanUpcErrorMessage.EAN_UNIQUE.getName()));
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.EanUpcErrorMessage.UPC_UNIQUE.getName()));
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
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.ProductModelName.LANGUAGE_DUPLICATE.getName()));
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
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.ProductModelName.MODEL_NAME_REQUIRED.getName()));
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
		data.remove("save");
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
		productControl.dragDropFile(AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName());
		productControl.dragDropFile(AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		productControl.dragDropFile(AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		productControl.dragDropFile(AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		productControl.dragDropFile(AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		
		/*
		 * Verify that all files are uploaded successfully
		 */
		Assert.assertTrue(productControl.isMarketingUploaded(AddEditProductModel.MARKETING_FILE_CONTAINER, AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName()));
		
		Assert.assertTrue(productControl.isTunningUploaded(AddEditProductModel.UPLOADED_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName()));
		
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
		
		
		//1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
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
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.required.Connection_Type_is_required.getName()));
		
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
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),productData);
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
		productControl.click(AddEditProductModel.CANCEL_VARIANT);
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
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> proData =  TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),proData);
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME), proData.get("name"));
		// 5. Click "Add New Variant" link from "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.VARIANT_TITLE));
		// 6. Click "Upload Custom Tuning" link and custom images
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_TUNNING);
		
		/*
		 * VP. Verify that the "Add Tuning File" is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.ADD_TUNNING));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.TUNING_DRAG_DROP_AREA));
		
		// Drag and drop a file into tuning file, three primary catalog images and Marketing Material area.
		productControl.dragDropFile(AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName());
		//Click upload custom primary catalog images
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_IMAGE);
//		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA.Image_Drag_Drop_Area_250.getName());
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_IMAGE);
		//drag and drop images
		productControl.dragDropFile(AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		productControl.dragDropFile(AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		productControl.dragDropFile(AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		//Marketing files
		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA.Marketing_Drag_Drop_Area.getName());
		productControl.dragDropFile(AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		/*
		 * Verify that all files is uploaded successfully
		 */
		Assert.assertTrue(productControl.isTunningUploaded(AddEditProductModel.UPLOADED_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName()));
		
		Assert.assertTrue(productControl.isMarketingUploaded(AddEditProductModel.MARKETING_FILE_CONTAINER, AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName()));
		
		Assert.assertTrue(productControl.isImageUploaded(AddEditProductModel.IMAGE250_DISPLAY, AddEditProductModel.IMG_NAME[0]));
		Assert.assertTrue(productControl.isImageUploaded(AddEditProductModel.IMAGE500_DISPLAY, AddEditProductModel.IMG_NAME[1]));
		Assert.assertTrue(productControl.isImageUploaded(AddEditProductModel.IMAGE1000_DISPLAY, AddEditProductModel.IMG_NAME[2]));
		
		// delete product
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(proData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
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
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> proData =  TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),proData);
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
		Assert.assertTrue(productControl.getElementText(failedTuning).contains(AddEditProductModel.UploadFileMessage.INVALID_FILE.getName()));
		
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_TUNING, AddEditProductModel.FileUpload.Tuning_ZIP.getName());
		/*
		 * Verify that The tuning file is upload successfully
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.UPLOADED_TUNING));
		
		productControl.click(AddEditProductModel.DELETE_UPLOADED_TUNING);
		productControl.selectConfirmationDialogOption("Delete");
		
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName());
		/*
		 * Verify that The Tuning section is displayed with new uploaded tuning file and it is called "DTS Tuning"
		 */
		WebElement uploadedTuning = driver.findElement(By.xpath(AddEditProductModel.UPLOADED_TUNING)).findElement(By.className("mauploadTuning"));
		Assert.assertEquals(productControl.getElementText(uploadedTuning),AddEditProductModel.tuningname.DTS_Tuning.getName());
		
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(proData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
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
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Select an product from Products table
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> proData =  TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),proData);
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
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName());
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
		
		// Delete product
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(proData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		
		
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
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.EanUpcErrorMessage.EAN_UNIQUE.getName()));
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.EanUpcErrorMessage.UPC_UNIQUE.getName()));
		// Delete product
		productControl.click(AddEditProductModel.CANCEL_VARIANT);
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
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),productData);
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
		
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(productData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		
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
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.VARIANT_TITLE));
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
			9. Click "Edit" link
			VP: The uploaded date and file size are displayed below the image name for each size of Primary Catalog Image
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
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),productData);
		// 4. Verify that the 040D product Detail page is displayed
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TITLE_NAME),productData.get("name"));
		// 5. Click "Add New Variant" link from "Model Actions" module
		productControl.click(ProductDetailModel.ADD_VARIANT);
		/*
		 * VP. Verify that the 052D Model Variant Edit page is displayed
		 */
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.VARIANT_TITLE));
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
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * VP: Verify that new variant is added successfully
		 */
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TITLE_NAME), data.get("name"));
		// 9. Click "Edit" link
		productControl.click(VariantInfo.EDIT_VARIANT);
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
		//10. Click on image thumbnail/file name of each size
		/*
		 * VP: A lightbox style popup with the picture showing in full size is displayed
		 */
		
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
		//11. Delete the uploaded image by clicking on trashcan icon
		productControl.deleteAllUploadedImage(AddEditProductModel.IMAGE_CATALOG);
		//12. Upload another image
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.IMG_NAME[0]);
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.IMG_NAME[1]);
		productControl.uploadFile(AddEditProductModel.SELECT_FILE_IMAGE_1000, AddEditProductModel.IMG_NAME[2]);
		/*
		 * Verify that new image is uploaded successfully
		 */
		Assert.assertTrue(productControl.isImageUploaded(AddEditProductModel.IMAGE250_DISPLAY, AddEditProductModel.IMG_NAME[0]));
		Assert.assertTrue(productControl.isImageUploaded(AddEditProductModel.IMAGE500_DISPLAY, AddEditProductModel.IMG_NAME[1]));
		Assert.assertTrue(productControl.isImageUploaded(AddEditProductModel.IMAGE1000_DISPLAY, AddEditProductModel.IMG_NAME[2]));
		
		//Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(productData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
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
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),productData);
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
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		// VP: Verify that new product is unable to save due to the error message inline of EAN/UPC fields
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.EanUpcErrorMessage.EAN_EXCEED_CHAR_LIMIT.getName()));
		Assert.assertTrue(productControl.checkMessageDisplay(AddEditProductModel.EanUpcErrorMessage.UPC_EXCEED_CHAR_LIMIT.getName()));
		//	9. Enter a string exactly 13 digits into EAN/UPC fields
		productControl.editData(AddEditProductModel.EAN, RandomStringUtils.randomNumeric(13));
		productControl.editData(AddEditProductModel.UPC, RandomStringUtils.randomNumeric(12));
		//	10. Click ï¿½Saveï¿½
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		//	New variant is created successfully
		Assert.assertEquals(productControl.getTextByXpath(VariantInfo.DISPLAYNAME_DEFAULT), data.get("name"));;

		//Teardown:
		productControl.click(VariantInfo.PRODUCT_LINK);
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that user is able to upload tuning file, primary catalog image and marketing material again when the file being upload occurs error of product
	 */
	@Test
	public void TC051DAE_85() throws InterruptedException{
		LogReporter.addLog("ID: 051DAE_85: Verify that user is able to upload tuning file, primary catalog image and marketing material again when the file being upload occurs error of product");
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
			VP: The valid tuning file is uploaded successfully
			9. Repeat from step 4 to 8 for primary catalog image and marketing material files.
			VP: Tuning file, primary catalog images and marketing material file could be uploaded again.
		 */
//		1. Log into DTS portal as a DTS admin
		loginControl.login(SUPER_USER_NAME,SUPER_USER_PASSWORD);
//		2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
//		3. Click “Add Products” link
		productControl.click(ProductModel.ADD_PRODUCT);
//		5. Corrupt the network while the file is uploading
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
		// Corrupt the network while the file is uploading
		productControl.interuptNetwork();
		Thread.sleep(5000);
		productControl.uploadFileInterupt(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		productControl.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddEditProductModel.UploadFileMessage.UPLOAD_ERROR.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_IMAGE_250));
		// Connect network successfully
		productControl.connectNetwork();
		// Click "Retry" link
		// Try to another 250x250 file
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_IMAGE_250, AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		/*
		 * Verify that primary catalog image file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_250).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		
		/* Repeat uploadiing for 500x500*/
		// Corrupt the network while the file is uploading
		productControl.interuptNetwork();

		productControl.uploadFileInterupt(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		productControl.waitForAjax();

		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddEditProductModel.UploadFileMessage.UPLOAD_ERROR.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_IMAGE_500));
		// Connect network successfully
		productControl.connectNetwork();
		Thread.sleep(5000);
		// Click "Retry" link
		// Try to another 500x500 file
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_IMAGE_500, AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		productControl.waitForAjax();
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_500).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		
		// Corrupt the network while the file is uploading
		productControl.interuptNetwork();
		/* Repeat uploadiing for 1000x1000*/
		
		productControl.uploadFileInterupt(AddEditProductModel.SELECT_FILE_IMAGE_1000, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());

		productControl.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddEditProductModel.UploadFileMessage.UPLOAD_ERROR.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_IMAGE_1000));
		// Connect network successfully
		productControl.connectNetwork();
		Thread.sleep(5000);
		// Click "Retry" link
		// Try to another 1000x1000 file file
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_IMAGE_1000, AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_1000).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		
		// Repeat from step 4 to 8 with primary catalog images and marketing material files.
		//with marketing material file
		// Corrupt the network while the file is uploading
		productControl.interuptNetwork();
		// Upload a marketing file
		productControl.uploadFileInterupt(AddEditProductModel.ADD_MARKETING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());

		productControl.waitForAjax();
		/*
		 * VP: Verify that the "File upload error" message and Retry link are displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.MARKETING_UPLOAD_MESSAGE).contains(AddEditProductModel.UploadFileMessage.UPLOAD_ERROR.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_MARKETING));
		// Connect network successfully
		productControl.connectNetwork();
		Thread.sleep(5000);
		// Click "Retry" link
		// Try to another maketing material file
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_MARKETING, AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		/*
		 * Verify that New marketing material file is uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath("//div" + AddEditProductModel.MARKETING_UPLOAD_MESSAGE).contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));		
		
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
//		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA.Tuning_Drag_Drop_Area.getName());
		productControl.uploadFileInterupt(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 5. Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName(), "hpxtt");
		// VP: The message "File upload canceled" and Retry link is displayed
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.TUNING_UPLOAD_MESSAGE)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_CANCELED.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_TUNING));
		// 6. Click "Retry" link
		// 7. Try to upload new tuning file again
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName());
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(productControl.isTunningUploaded(AddEditProductModel.UPLOADED_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName()));
		
		// Upload three size of primary catalog image
//		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA.Image_Drag_Drop_Area_500.getName());
		productControl.uploadFileInterupt(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName(), "jpg");
		/*
		 * VP: The message "File upload canceled" and Retry link is displayed
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_500)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_CANCELED.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_IMAGE_500));
		// 6. Click "Retry" link
		// 7. Try to upload another catalog image again
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_IMAGE_500, AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		/*
		 * Verify that Three size of catalog image are uploaded successfully
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.CATALOG_IMAGE_UPLOAD_MESSAGE_500)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_SUCCESS.getName()));
		
		// Upload a marketing material file
//		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA.Marketing_Drag_Drop_Area.getName());
		productControl.uploadFileInterupt(AddEditProductModel.ADD_MARKETING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName(), "dtscs");
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
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> proData =  TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),proData);
		// 4. Click "Add new variant" link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		// 5. Upload a marketing material file
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_TUNNING);
//		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA.Tuning_Drag_Drop_Area.getName());
		productControl.uploadFileInterupt(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 5. Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName(), "jpg");
		// VP: The message "File upload canceled" and Retry link is displayed
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.TUNING_UPLOAD_MESSAGE)
				.contains(AddEditProductModel.UploadFileMessage.UPLOAD_CANCELED.getName()));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.RETRY_UPLOAD_TUNING));
		// 6. Click "Retry" link
		// 7. Try to upload new tuning file again
		productControl.uploadFile(AddEditProductModel.RETRY_UPLOAD_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName());
		/*
		 * Verify that New tuning file is uploaded successfully
		 */
		Assert.assertTrue(productControl.isTunningUploaded(AddEditProductModel.UPLOADED_TUNING, AddEditProductModel.FileUpload.Tuning_HPXTT_LineOut.getName()));
		
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_IMAGE);
		// Upload three size of primary catalog image
//		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA.Image_Drag_Drop_Area_250.getName());
		productControl.uploadFileInterupt(AddEditProductModel.SELECT_FILE_IMAGE_250, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName(), "jpg");
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
//		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA.Marketing_Drag_Drop_Area.getName());
		productControl.uploadFileInterupt(AddEditProductModel.ADD_MARKETING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// Cancel uploading file
		productControl.clickImage(AddEditProductModel.FileUpload.X_Cancel_Upload.getName(), "dtscs");
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
		
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(proData.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
		
	}
	
	/*
	 * Verify that the user can not set "HeadphoneX Rating" to "Undetermined" after the tuning is approved
	 */
	@Test
	public void TC051DAE_99() {
		LogReporter.addLog("ID : 051DAE_99: Verify that the user can not set 'HeadphoneX Rating' to 'Undetermined' after the tuning is approved");
	
		/*1. Navigate to DTS portal	
		2. Log into DTS portal as a DTS user successfully	
		3. Navigate to "Products" list page	
		4. Click "Add Product" link	
		5. Fill valid value into all required fields	
		6. Upload tuning file successfully	
		7. Change the HeadphoneX tuning rating to "A"	
		8. Click "Save" link	
		9. Expand the "Partner Actions" link in Step 1: Tuning Approval	
		10. Click "Approve Tuning" link	
		11. Click "Edit Version" link	
		12. Change the HeadphonX tuning rating to "Undetermined"	
		13. Click "Save" link	
		VP: An error message displays and the product is not be saved until another tuning rating is choosen
		*/
		
//		2. Log into DTS portal as a DTS user successfully
		loginControl.login(DTS_USER,DTS_PASSWORD);
//		3. Navigate to "Products" list page	
		productControl.click(PageHome.linkAccessories);
//		4. Click "Add Product" link	
		productControl.click(ProductModel.ADD_PRODUCT);
//		5. Fill valid value into all required fields	
//		6. Upload tuning file successfully	
//		7. Change the HeadphoneX tuning rating to "A"	
//		8. Click "Save" link	
		Hashtable<String,String> dataPro = TestData.productData(PARTNER_COMPANY_NAME,PARTNER_BRAND_NAME_1,true,true,false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),dataPro);
//		9. Expand the "Partner Actions" link in Step 1: Tuning Approval	
//		10. Click "Approve Tuning" link	
		productWf.approveTuning();
//		11. Click "Edit Version" link	
		productControl.click(ProductDetailModel.EDIT_MODE);
//		12. Change the HeadphonX tuning rating to "Undetermined"	
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING,AddEditProductModel.TuningRatingOption.UNDETERMINED.getName());
//		13. Click "Save" link	
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: An error message displays and the product is not be saved until another tuning rating is choosen
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.TUNING_MESSAGE_POPUP).contains(AddEditProductModel.required.Approved_tuning_not_be_with_Undetermined.getName()));
	}
	
	/*
	 * Verify that the user can not set "HeadphoneX Rating" to "Undetermined" after the tuning of variant is approved
	 */
	@Test
	public void TC051DAE_100() {
		LogReporter.addLog("ID : 051DAE_100: Verify that the user can not set 'HeadphoneX Rating' to 'Undetermined' after the tuning of variant is approved");
	
		/*  1. Navigate to DTS portal	
			2. Log into DTS portal as a DTS user successfully	
			3. Navigate to "Products" list page	
			4. Select published product from products list	
			5. Click "Add new variant" link	
			6. Fill valid value into all required fields	
			7. Upload tuning file successfully	
			8. Change the HeadphoneX tuning rating to "A"	
			9. Click "Save" link	
			10. Expand the "Partner Actions" link in Step 1: Tuning Approval	
			11. Click "Approve Tuning" link	
			12. Click "Edit Version" link	
			13. Change the HeadphonX tuning rating to "Undetermined"	
			14. Click "Save" link	
			VP: An error message displays and the product is not be saved until another tuning rating is choosen

		*/
		
//		2. Log into DTS portal as a DTS user successfully
		loginControl.login(DTS_USER,DTS_PASSWORD);
//		3. Navigate to "Products" list page	
		productControl.click(PageHome.linkAccessories);
//		4. Select published product from products list	
		productControl.click(ProductModel.ADD_PRODUCT);
		Hashtable<String,String> dataPro = TestData.productData(PARTNER_COMPANY_NAME,PARTNER_BRAND_NAME_1,true,true,true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(),dataPro);
//		5. Click "Add new variant" link	
		productControl.click(ProductDetailModel.ADD_VARIANT);
//		6. Fill valid value into all required fields	
//		7. Upload tuning file successfully	
//		8. Change the HeadphoneX tuning rating to "A"	
//		9. Click "Save" link	
		Hashtable<String,String> dataVar = TestData.variantData(true,false,false);
		productControl.addVariant(AddEditProductModel.getVariantHash(),dataVar);
//		10. Expand the "Partner Actions" link in Step 1: Tuning Approval	
//		11. Click "Approve Tuning" link	
		productWf.approveTuning();
//		12. Click "Edit Version" link	
		productControl.click(VariantInfo.EDIT_VARIANT);
//		13. Change the HeadphonX tuning rating to "Undetermined"	
		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING,AddEditProductModel.TuningRatingOption.UNDETERMINED.getName());
//		14. Click "Save" link	
		productControl.click(AddEditProductModel.SAVE_VARIANT);
		/*
		 * VP: An error message displays and the product is not be saved until another tuning rating is choosen
		 */
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.TUNING_MESSAGE_POPUP).contains(AddEditProductModel.required.Approved_tuning_not_be_with_Undetermined.getName()));
		
		// Delete data
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(dataPro.get("name"));
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the "Bluetooth Device Name" section work correctly
	 */
	@Test
	public void TC051DAE_101() throws InterruptedException {
		LogReporter.addLog("ID : 051DAE_101: Verify that the 'Bluetooth Device Name' section work correctly");
		
		/*
		1. Log into DTS portal as a DTS user	
		2. Navigate to "Products" page	
		3. Click "Add product" link	
		4. Fill valid value into all required fields	
		5. Upload tuning file successfully	
		6. Select "Bluetooth" in "Connection Type" 	
		VP: The "Bluetooth Device Name" section is displayed with instruction label 
		"Enter one or more strings which can uniquely identify this product based on its Bluetooths "Device Name" metadata value." is displayed
		7. Input a bluetooth device name into "Bluetooth Device Name" field	
		8. Click "Add" button	
		VP: The BT device name is added successfully with a delete icon
		9. Unselect "Bluetooth" in "Connection Type"	
		VP: The "Bluetooth Device Name" section is not displayed
		10. Select "Wired" in "Connection Type"	
		VP: The "Bluetooth Device Name" section is not displayed
		11. Select "Bluetooth" in "Connection Type" again	
		VP: The "Bluetooth Device Name" section is displayed with instruction label 
		"Enter one or more strings which can uniquely identify this product based on its Bluetooths "Device Name" metadata value." is displayed
		12. Input a bluetooth device name into "Bluetooth Device Name" field	
		13. Press "Enter" key from keyboard	
		VP: The BT device name is added successfully with a delete icon
		14. Add the same bluetooth device name in step 12 	
		VP: An error message is displayed to notify that BT device name is existed
		15. Add a bluetooth device name with whitespaces into successfully	
		VP: The BT device name is trimed with a delete icon
		16. Click "Save" link	
		VP: The correct BT device names are displayed in "Bluetooth Device Name"
		17. Click "Edit Version" link	
		18. Delete a Bluetooth Device Name successfully	
		VP: The BT device name is deleted successfully
		19. Click "Save" link	
		VP: The deleted BT device name is not displayed
		 */
		
//		1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		2. Navigate to "Products" page	
		productControl.click(PageHome.linkAccessories);
//		3. Click "Add product" link	
		productControl.click(ProductModel.ADD_PRODUCT);
//		4. Fill valid value into all required fields
		Hashtable<String,String> dataPro = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		dataPro.remove("wired");
		dataPro.remove("save");
		dataPro.put("bluetooth","1 (Mono)");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(),dataPro);
//		5. Upload tuning file successfully
//		productControl.uploadFile(AddEditProductModel.ADD_TUNNING,AddEditProductModel.FileUpload.Tuning_HPXTT_Bluetooth.getName());
//		6. Select "Bluetooth" in "Connection Type" 	
		/*
		 * VP: The "Bluetooth Device Name" section is displayed with instruction label 
		 * "Enter one or more strings which can uniquely identify this product based on its Bluetooths "Device Name" metadata value." is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.BT_DEVICE_NAME_INS),AddEditProductModel.BT_DEVICE_NAME_TEXTINS);
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.BT_DEVICE_NAME_TITLE));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.BT_DEVICE_NAME_FIELD));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.BT_DEVICE_NAME_ADD));
//		7. Input a bluetooth device name into "Bluetooth Device Name" field
		productControl.editData(AddEditProductModel.BT_DEVICE_NAME_FIELD,"Bluethooth Device 1");
//		8. Click "Add" button
		productControl.click(AddEditProductModel.BT_DEVICE_NAME_ADD);
		/*
		 * VP: The BT device name is added successfully with a delete icon
		 */
		Assert.assertTrue(productControl.isElementPresent(productControl.getXpathBTDeviceName("Bluethooth Device 1")));
		Assert.assertTrue(productControl.isElementPresent(productControl.getXpathTrashBTDeviceName("Bluethooth Device 1")));
//		9. Unselect "Bluetooth" in "Connection Type"
		productControl.uncheckACheckbox(AddEditProductModel.BLUETOOTH_CHECKBOX);
		/*
		 * VP: The "Bluetooth Device Name" section is not displayed
		 */
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.BT_DEVICE_NAME_TITLE));
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.BT_DEVICE_NAME_FIELD));
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.BT_DEVICE_NAME_ADD));
//		10. Select "Wired" in "Connection Type"	
		productControl.selectInputChannel(AddEditProductModel.WIRED_CONTAINER,AddEditProductModel.InputContentChannel.SINGLE_CHANNEL.getName());
		/*
		 * VP: The "Bluetooth Device Name" section is not displayed
		 */
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.BT_DEVICE_NAME_TITLE));
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.BT_DEVICE_NAME_FIELD));
		Assert.assertFalse(productControl.isElementPresent(AddEditProductModel.BT_DEVICE_NAME_ADD));
//		11. Select "Bluetooth" in "Connection Type" again	
		productControl.selectInputChannel(AddEditProductModel.BLUETOOTH_CONTAINER,AddEditProductModel.InputContentChannel.SINGLE_CHANNEL.getName());
		/*
		 * VP: The "Bluetooth Device Name" section is displayed with instruction label "Enter one or more strings which can uniquely identify this product based on its Bluetooths "Device Name" metadata value." is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.BT_DEVICE_NAME_INS),AddEditProductModel.BT_DEVICE_NAME_TEXTINS);
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.BT_DEVICE_NAME_TITLE));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.BT_DEVICE_NAME_FIELD));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.BT_DEVICE_NAME_ADD));
//		12. Input a bluetooth device name into "Bluetooth Device Name" field
		productControl.editData(AddEditProductModel.BT_DEVICE_NAME_FIELD,"Bluethooth Device 2");
		Thread.sleep(2000);
//		13. Press "Enter" key from keyboard
//		productControl.autoTool.send("{ENTER}",false);
		productControl.click(AddEditProductModel.BT_DEVICE_NAME_ADD);
		/*
		 * VP: The BT device name is added successfully with a delete icon
		 */
		Assert.assertTrue(productControl.isElementPresent(productControl.getXpathBTDeviceName("Bluethooth Device 2")));
		Assert.assertTrue(productControl.isElementPresent(productControl.getXpathTrashBTDeviceName("Bluethooth Device 2")));
//		14. Add the same bluetooth device name in step 12 
		productControl.editData(AddEditProductModel.BT_DEVICE_NAME_FIELD,"Bluethooth Device 2");
		productControl.click(AddEditProductModel.BT_DEVICE_NAME_ADD);
		/*
		 * VP: An error message is displayed to notify that BT device name is existed
		 */
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.BT_DEVICES_MESSAGE),AddEditProductModel.required.Device_name_is_already_existed.getName());
//		15. Add a bluetooth device name with whitespaces into successfully
		productControl.editData(AddEditProductModel.BT_DEVICE_NAME_FIELD,"           Bluethooth Device 3              ");
		productControl.click(AddEditProductModel.BT_DEVICE_NAME_ADD);
		/*
		 * VP: The BT device name is trimed with a delete icon
		 */
		Assert.assertTrue(productControl.isElementPresent(productControl.getXpathBTDeviceName("Bluethooth Device 3")));
		Assert.assertTrue(productControl.isElementPresent(productControl.getXpathTrashBTDeviceName("Bluethooth Device 3")));
//		16. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: The correct BT device names are displayed in "Bluetooth Device Name"
		 */
		Assert.assertTrue(productControl.isBTDeviceNameDisplayed("Bluethooth Device 1"));
		Assert.assertTrue(productControl.isBTDeviceNameDisplayed("Bluethooth Device 2"));
		Assert.assertTrue(productControl.isBTDeviceNameDisplayed("Bluethooth Device 3"));
//		17. Click "Edit Version" link
		productControl.click(ProductDetailModel.EDIT_MODE);
//		18. Delete a Bluetooth Device Name successfully	
		productControl.click(productControl.getXpathTrashBTDeviceName("Bluethooth Device 2"));
		/*
		 * VP: The BT device name is deleted successfully
		 */
		Assert.assertFalse(productControl.isElementPresent(productControl.getXpathBTDeviceName("Bluethooth Device 2")));
//		19. Click "Save" link	
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * VP: The deleted BT device name is not displayed
		 */
		Assert.assertTrue(productControl.isBTDeviceNameDisplayed("Bluethooth Device 1"));
		Assert.assertFalse(productControl.isBTDeviceNameDisplayed("Bluethooth Device 2"));
		Assert.assertTrue(productControl.isBTDeviceNameDisplayed("Bluethooth Device 3"));
		
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that user is able to upload tuning file, primary catalog image and marketing material again when canceling the file being upload of variant
	 */
	@Test
	public void TC051DAE_102() throws InterruptedException {
		LogReporter.addLog("ID : 051DAE_102: Verify that the confirmation dialog is displayed when user hits 'Save' "
				+ "link while the tuning file or primary catalog images are uploading when add product");
		/*
			1. Log into DTS portal as a DTS admin
			2. Navigate to "Products" page
			3. Click "Add Product" link
			4. Upload tuning file
			5. Click "Save" link while the tuning file is uploading
			VP: The confirmation dialog is displayed
			6. Upload primary catalog images
			7. Click "Save" link while the primary catalog images are uploading
			VP: The confirmation dialog is displayed
			8. Upload marketing materials file
			9. Click "Save" link while the marketing material file is uploading
			VP: The confirmation dialog is displayed
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click "Add Product" link
		productControl.click(ProductModel.ADD_PRODUCT);
//		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA.Tuning_Drag_Drop_Area.getName());
		// 4. Upload tuning file
		productControl.uploadFileInterupt(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 5. Click "Save" link while the tuning file is uploading
		productControl.clickSaveLinkWhileUploading(AddEditProductModel.FileUpload.X_Cancel_Upload.getName(), AddEditProductModel.IMAGE_SAVE, "hpxtt");
		// VP: The confirmation dialog is displayed
//		Assert.assertTrue(productControl.checkShowPopup("TuningPopup.PNG"));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.POPUP_ERROR));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.POPUP_ERROR).contains(AddEditProductModel.MESSAGE_TUNING));
		productControl.click(AddEditProductModel.CLOSE_POPUP);
		// 6. Upload primary catalog images.
//		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA.Image_Drag_Drop_Area_500.getName());
		productControl.uploadFileInterupt(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 7. Click "Save" link while the primary catalog images are uploading
		productControl.clickSaveLinkWhileUploading(AddEditProductModel.FileUpload.X_Cancel_Upload.getName(), AddEditProductModel.IMAGE_SAVE, "jpg");
		// VP: The confirmation dialog is displayed
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.POPUP_ERROR));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.POPUP_ERROR).contains(AddEditProductModel.MESSAGE_IMAGE));
		productControl.click(AddEditProductModel.CLOSE_POPUP);
		// 8. Upload marketing materials file
//		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA.Marketing_Drag_Drop_Area.getName());
		productControl.uploadFileInterupt(AddEditProductModel.ADD_MARKETING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 9. Click "Save" link while the marketing material file is uploading
		productControl.clickSaveLinkWhileUploading(AddEditProductModel.FileUpload.X_Cancel_Upload.getName(), AddEditProductModel.IMAGE_SAVE, "dtscs");
		// VP: The confirmation dialog is displayed
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.POPUP_ERROR));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.POPUP_ERROR).contains(AddEditProductModel.MESSAGE_MARKETING));
		productControl.click(AddEditProductModel.CLOSE_POPUP);
		productControl.click(AddEditProductModel.CANCEL_PRODUCT);
	}
	
	/*
	 * Verify that user is able to upload tuning file, primary catalog image and marketing material again when canceling the file being upload of variant
	 */
	@Test
	public void TC051DAE_103() throws InterruptedException {
		LogReporter.addLog("ID : 051DAE_103: Verify that the confirmation dialog is displayed when user hits 'Save' "
				+ "link while the tuning file or primary catalog images are uploading when add variant");
		/*
			1. Log into DTS portal as a DTS admin
			2. Navigate to "Products" page
			3. Select a product from products table
			4. Click "Add New Variant" link
			5. Upload tuning file
			6. Click "Save" link while the tuning file is uploading
			VP: The confirmation dialog is displayed
			7. Upload primary catalog images
			8. Click "Save" link while the primary catalog images are uploading
			VP: The confirmation dialog is displayed
			9. Upload marketing materials file
			10. Click "Save" link while the marketing material file is uploading
			VP: The confirmation dialog is displayed
		*/
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Select a product from products table
		productControl.selectAnAccessory();
		// 4. Click "Add New Variant" link
		productControl.click(ProductDetailModel.ADD_VARIANT);
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_TUNNING);
//		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA.Tuning_Drag_Drop_Area.getName());
		// 5. Upload tuning file
		productControl.uploadFileInterupt(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 6. Click "Save" link while the tuning file is uploading
		productControl.clickSaveLinkWhileUploading(AddEditProductModel.FileUpload.X_Cancel_Upload.getName(), AddEditProductModel.IMAGE_SAVE, "hpxtt");
		// VP: The confirmation dialog is displayed
//		Assert.assertTrue(productControl.checkShowPopup("TuningPopup.PNG"));
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.POPUP_ERROR));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.POPUP_ERROR).contains(AddEditProductModel.MESSAGE_TUNING));
		productControl.click(AddEditProductModel.CLOSE_POPUP);
		// 7. Upload primary catalog images.
		productControl.click(AddEditProductModel.UPLOAD_CUSTOM_IMAGE);
//		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA.Image_Drag_Drop_Area_500.getName());
		productControl.uploadFileInterupt(AddEditProductModel.SELECT_FILE_IMAGE_500, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 8. Click "Save" link while the primary catalog images are uploading
		productControl.clickSaveLinkWhileUploading(AddEditProductModel.FileUpload.X_Cancel_Upload.getName(), AddEditProductModel.IMAGE_SAVE, "jpg");
		// VP: The confirmation dialog is displayed
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.POPUP_ERROR));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.POPUP_ERROR).contains(AddEditProductModel.MESSAGE_IMAGE));
		productControl.click(AddEditProductModel.CLOSE_POPUP);
		// 9. Upload marketing materials file
//		productControl.scrollMouseUntilImageVisible(Constant.IMG_DRAG_DROP_AREA.Marketing_Drag_Drop_Area.getName());
		productControl.uploadFileInterupt(AddEditProductModel.ADD_MARKETING, AddEditProductModel.FileUpload.File_Upload_Cancel.getName());
		// 10. Click "Save" link while the marketing material file is uploading
		productControl.clickSaveLinkWhileUploading(AddEditProductModel.FileUpload.X_Cancel_Upload.getName(), AddEditProductModel.IMAGE_SAVE, "dtscs");
		// VP: The confirmation dialog is displayed
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.POPUP_ERROR));
		Assert.assertTrue(productControl.getTextByXpath(AddEditProductModel.POPUP_ERROR).contains(AddEditProductModel.MESSAGE_MARKETING));
		productControl.click(AddEditProductModel.CLOSE_POPUP);
		productControl.click(AddEditProductModel.CANCEL_VARIANT);
	}
	
	/*
	 * Verify that “Input Specification” section is displayed correctly
	 */
	@Test
	public void TC051DAE_104() {
		LogReporter.addLog("ID : 051DAE_104: Verify that “Input Specification” section is displayed correctly");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click “Add Product” link
			VP: Verify that “Input Specification” section include checkboxes: “Wired”, “Bluetooth” and “USB”
		*/
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click “Add Product” link
		productControl.click(ProductModel.ADD_PRODUCT);
		// VP: Verify that “Input Specification” section include checkboxes: “Wired”, “Bluetooth” and “USB”
		String tuningOptions = productControl.getTextByXpath(AddEditProductModel.INPUT_SPECIFICATIONS_TABLE);
		Assert.assertTrue(ListUtil.containsListText(tuningOptions, AddEditProductModel.ConnectionType.getNames()));
	}
	
	/*
	 * Verify that Product Edit page validate for uploading with new connection type
	 */
	@Test
	public void TC051DAE_105() {
		LogReporter.addLog("ID : 051DAE_105: Verify that Product Edit page validate for uploading with new connection type");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click “Add Product” link
			4. Fill valid value into all required fields
			5. Select the connection type "USB" only
			6. Upload a bluetooth only hpxtt tuning file
			7. Click “Save” link
			VP: The error message is shown.
			8. Upload a USB only hpxtt tuning file
			9. Click “Save” link
			VP: Verify that Product is saved successfully
			10. Click “Edit Version” link
			11. Delete tuning file
			12. Select the connection type "USB" and “Wired”
			13. Upload a USB only hpxtt tuning file
			14. Click “Save” link
			VP: The error message is shown.
			15. Repeat from step 11 to step 14 with tuning file contains both Lineout and Bluetooth
			VP: The error message is shown.
			16. Repeat from step 11 to step 14 with tuning file contains both Lineout and USB
			VP: Verify that Product is saved successfully
			17. Click “Edit Version” link
			18. Repeat from step 11 to step 14 with connection type include “Wired”, “Bluetooth”, “USB”; but tuning file contains only Bluetooth and USB
			VP: The error message is shown.
			19. Delete tuning file
			20. Upload tuning file contains Lineout, Bluetooth and USB
			VP: Verify that Product is saved successfully

		*/
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click “Add Product” link
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
		// 7. Click “Save” link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: The error message is shown.
		Assert.assertEquals(productControl.getTextByXpath(AddEditProductModel.TUNING_MESSAGE_POPUP),AddEditProductModel.UploadFileMessage.CONNECTION_TYPE_NOTMATHCH.getName());
		productControl.click(AddEditProductModel.CLOSE_POPUP);
		// 8. Upload a USB only hpxtt tuning file
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT_USB.getName());
		// 9. Click “Save” link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: Verify that Product is saved successfully
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 10. Click “Edit Version” link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 11. Delete tuning file
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		// 12. Select the connection type "USB" and “Wired”
		productControl.selectACheckbox(AddEditProductModel.WIRED_CHECKBOX);
		// 13. Upload a USB only hpxtt tuning file
		productControl.uploadFile(AddEditProductModel.ADD_TUNNING, AddEditProductModel.FileUpload.Tuning_HPXTT_USB.getName());
		// 14. Click “Save” link
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
		// 17. Click “Edit Version” link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 18. Repeat from step 11 to step 14 with connection type include “Wired”, “Bluetooth”, “USB”; but tuning file contains only Bluetooth and USB
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
	public void TC051DAE_106() {
		LogReporter.addLog("ID : 051DAE_106: Verify that Variant Edit page validate for uploading with new connection type");
		/*
			1. Log into DTS portal as a DTS user
			2. Navigate to "Products" page
			3. Click “Add Product” link
			4. Fill valid value into all required fields
			5. Select the connection type "USB" only
			6. Click “Save” link
			VP: Verify that Product is saved successfully
			7. Click “Add New Variant” link
			8. Fill valid value into all required fields
			9. Upload a bluetooth only hpxtt tuning file
			10. Click "Save" link
			VP: The error message is shown.
			11. Delete tuning file
			12. Upload a USB only hpxtt tuning file
			13. Click "Save" link
			VP: Verify that variant is saved successfully
			14. Click Product Model link
			15. Click “Edit Version” link
			16. Select the connection type "USB" and “Lineout”
			17. Click “Save” link
			VP: Verify that Product is saved successfully
			18. Repeat from step 7 to step 9 with tuning file contains only USB
			VP: The error message is shown.
			19. Repeat from step 7 to step 9 with tuning file contains only USB and Lineout
			VP: Verify that variant is saved successfully

		*/
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click “Add Product” link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		Hashtable<String,String> productData = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productData.remove("save");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), productData);
		// 5. Select the connection type "USB" only
		productControl.selectACheckbox(AddEditProductModel.USB_CHECKBOX);
		productControl.uncheckACheckbox(AddEditProductModel.WIRED_CHECKBOX);
		// 6. Click “Save” link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// VP: Verify that Product is saved successfully
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.TITLE_NAME));
		// 7. Click “Add New Variant” link
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
		// 13. Click “Edit Version” link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 14. Select the connection type "USB" and “Lineout”
		productControl.selectACheckbox(AddEditProductModel.WIRED_CHECKBOX);
		// 15. Click “Save” link
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
	 * Verify that the 'USB Device Name' section work correctly
	 */
	@Test
	public void TC051DAE_107() throws InterruptedException {
		LogReporter.addLog("ID : 051DAE_107: Verify that the 'USB Device Name' section work correctly");
		/*
			1. Log into DTS portal as a DTS user
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
			VP: The "USB Device Name" section is displayed with instruction label "Enter one or more strings which can uniquely identify this product based on its Bluetooths "Device Name" metadata value." is displayed
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
		// 1. Log into DTS portal as a DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 2. Navigate to "Products" page
		productControl.click(PageHome.linkAccessories);
		// 3. Click “Add Product” link
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
