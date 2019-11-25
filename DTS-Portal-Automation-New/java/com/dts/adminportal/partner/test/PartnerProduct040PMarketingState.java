package com.dts.adminportal.partner.test;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.DTSHome;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.util.TestData;

public class PartnerProduct040PMarketingState extends BasePage {
	
	@Override
	protected void initData() {
	}	

	
	/*
	 Verify that the Marketing status is changed from "CLOSED" to "UNSUBMITTED" when the tuning status is "APPROVED"
	*/
	@Test
	public void TC040PMS_01() {
		productControl.addLog("ID : 040PMS_01 : Verify that the Marketing status is changed from 'CLOSED' to 'UNSUBMITTED' when the tuning status is 'APPROVED' ");
		/*
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Fill valid value into all required fields
			5. Upload valid tuning file
			6. Click "Save" link
			VP: Verify that the tuning action link is  "Request Tuning Review"
			7. Click  "Request Tuning Review" link
			8. Log out DTS portal
			9. Log into DTS portal as DTS admin
			10. Navigate to "Accessories" page
			11. Select above accessory which its tuning is pending for DTS review
			12. Click "Approve Tuning" link
			13. Log out DTS portal
			14. Log into DTS portal as above partner user
			15. Navigate to "Accessories" page
			16. Select and open above accessory
		*/
		// 1. Log into DTS portal as a partner user
//		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//		// 2. Navigate to "Accessories" page
//		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//		// 3. Click "Add Accessory" link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		// 4. Fill valid value into all required fields
//		// 5. Upload valid tuning file
//		// 6. Click "Save" link
//		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//		/*Verify that the tuning action link is "Request Tuning Review"*/
//		Assert.assertNotNull(productControl.isElementPresent(ProductDetailModel.REQUEST_TUNING), "requestTuningAction is exist");
//		//7. Click "Request Tuning Review"  link
//		productControl.click(AddEditProductModel.REQUEST_TUNING_REVIEW);
//		//8. Log out DTS portal
//		productControl.logout();
//		//9. Log into DTS portal as DTS admin
//		loginControl.login(SUPER_USER_NAME,SUPER_USER_PASSWORD);
//		//10. Navigate to "Accessories" page
//		productControl.click(DTSHome.LINK_ACCESSORIES);
//		//11. Select above accessory which its tuning is pending for DTS review
//		productControl.selectAccessorybyName(data.get("name"));
//		//Change the tuning rating
//		productControl.click(ProductDetailModel.EDIT_MODE);
//		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, AddEditProductModel.TuningRatingOption.A.getName());
//		productControl.click(AddEditProductModel.SAVE);
//		//12. Click "Approve Tuning" link
//		productControl.click(ProductDetailModel.APPROVE_TUNING);	
		
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productWf.addProductAndTunningAndMarketing(data, false);
		
		//13. Log out DTS portal
		productControl.logout();
		//14. Log into DTS portal as above partner user
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		//15. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		//16. Select and open above accessory
		productControl.selectAccessorybyName(data.get("name"));
		// Verify The  tuning status is changed to "APPROVED" and the marketing status is changed to "UNSUBMITTED"
		String tuningStatusChanged = productControl.getTextByXpath(PageHome.TUNING_TITLE);
		Assert.assertEquals(tuningStatusChanged, ProductDetailModel.ProductStatus.APPROVED.getName());
		Assert.assertEquals(productControl.getTextByXpath(PageHome.MARKETING_TITLE), ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
	}
	
	/*
	 * Verify that the user who does not have "Publish and suspend accessories" right could not access marketing actions
	 */
	@Test
	public void TC040PMS_02() {
		productControl.addLog("ID : 040PMS_02 : Verify that the user who does not have"
				+ " 'Publish and suspend accessories' right could not access marketing actions");
		/*
		Pre-condition: the tuning approval is approved.
		1. Navigate to DTS portal
		2. Log into DTS portal as a DTS user successfully
		3. Navigate to "Users" page
		4. Select a user from users table
		VP: Verify that the 090D User Info page is displayed
		5. Click "Edit" link
		6. Enable all site privileges except "Publish and suspend accessories" for above user
		7. Log out DTS portal
		8. Log into DTS portal as a partner user
		9. Navigate to "Accessories" page
		10. Click "Add Accessory" link
		11. Fill valid value into all required fields
		12. Upload valid tuning file
		13. Upload marketing material
		14. Click "Save" link
		15. Go through the tuning approval
		*/
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//
//		// 3. Navigate to "Users" page
//		productControl.click(PageHome.LINK_USERS);
//		// 4. Select a user from users table
//		userControl.dtsSelectUserByEmail(PARTNER_USER);
//		/*
//		 * VP: Verify that the 090D User Info page is displayed
//		 */
//		Assert.assertTrue(productControl.isElementPresent(UserMgmt.TITLE));
//		// 5. Click "Edit" link
//		productControl.click(UserMgmt.EDIT);
//		// 6. Enable all site privileges except "Publish and suspend accessories" for above user
//		userControl.enableAllPrivileges();
//		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges[1]);
//		productControl.click(AddUser.SAVE);
//		// 7. Log out DTS portal
//		productControl.logout();
//		// 8. Log into DTS portal as a partner user
//		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
//		// 9. Navigate to "Accessories" page
//		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//		// 10. Click "Add Accessory" link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		// 11. Fill valid value into all required fields
//		// 12. Upload valid tuning file
//		// 13. Upload marketing material
//		// 14. Click "Save" link
//		/*
//		 * Init data
//		 */
//		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//		// 15. Go through the tuning approval
//		// Click Request Review Tuning link
//		productControl.click(ProductDetailModel.REQUEST_TUNING);
//		// Log out user
//		productControl.logout();
//		// Log into DTS portal as DTS user
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// Navigate to Accessories page
//		productControl.click(PageHome.linkAccessories);
//		// Select accessories create above
//		productControl.selectAccessorybyName(data.get("name"));
//		// Select tuning rating
//		productControl.click(ProductDetailModel.EDIT_MODE);
//		productControl.clickOptionByIndex(AddEditProductModel.TUNING_RATING, 2);
//		productControl.click(AddEditProductModel.SAVE);
//		// Approve tuning
//		productControl.click(ProductDetailModel.APPROVE_TUNING);
		
		userWf.loginAndDisablePrivilege(PARTNER_USER, Privileges.Publish_and_suspend_products, false);
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productWf.addProductAndTunningAndMarketing(data, false);
		// Log out user
		productControl.logout();
		// Log into DTS portal as Partner user above
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// Navigate to Accessories page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select accessory above
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" but there is no action for marketing approval section
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.APPROVED.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.MARKETING_ACTION));
		//Teardown:
		
		productControl.logout();
		loginControl.login(SUPER_PARTNER_USER,SUPER_PARTNER_PASSWORD);
		
		// Delete accessory
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.deleteAccessorybyName(data.get("name"));
		//Restore user privileges
		// 3. Navigate to "Users" page
		productControl.click(PageHome.LINK_USERS);
		// 4. Select a user from users table
		userControl.dtsSelectUserByEmail(PARTNER_USER);
		productControl.click(UserMgmt.EDIT);
		// 6. Enable all site privileges except "Publish and suspend accessories" for above user
		userControl.enableAllPrivileges();
		productControl.click(AddUser.SAVE);	
	
	}
	
	/*
	 * Verify that the "Request Marketing Review" link displays only after the partner uploads marketing material
	 */
	@Test
	public void TC040PMS_03() {
		productControl.addLog("ID : 040PMS_03 : Verify that the 'Request Marketing Review' link "
				+ "displays only after the partner uploads marketing material");
		/*
		Pre-condition: the tuning approval is approved.
		1. Log into DTS portal as a partner user
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		4. Fill valid value into all required fields
		5. Upload valid tuning file
		6. Do not upload marketing material
		7. Click "Save" link
		8. Go through the tuning approval
		VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" but there is no action for marketing appoval section
		9. Click "Edit" link
		10. Upload marketing material
		11. Click "Save" link
		*/
	
		// 1. Log into DTS portal as a partner user
//		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//		// 2. Navigate to "Accessories" page
//		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//		// 3. Click "Add Accessory" link
//		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		// 5. Upload valid tuning file
		// 6. Do not upload marketing material
		// 7. Click "Save" link
		/*
		 * Init data
		 */
//		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//		// 8. Go through the tuning approval
//		// Click Request Review Tuning link
//		productControl.click(ProductDetailModel.REQUEST_TUNING);
//		// Log out user
//		productControl.logout();
//		// Log into DTS portal as DTS user
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// Navigate to Accessories page
//		productControl.click(PageHome.linkAccessories);
//		// Select accessories create above
//		productControl.selectAccessorybyName(data.get("name"));
//		// Select tuning rating
//		productControl.click(ProductDetailModel.EDIT_MODE);
//		productControl.clickOptionByIndex(AddEditProductModel.TUNING_RATING, 2);
//		productControl.click(AddEditProductModel.SAVE);
//		// Approve tuning
//		productControl.click(ProductDetailModel.APPROVE_TUNING);
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productWf.addProductAndTunningAndMarketing(data, false);
		// Log out user
		productControl.logout();
		// Log into DTS portal as Partner user above
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to Accessories page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// Select accessory above
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" but there is no action for marketing approval section
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.APPROVED.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.MARKETING_ACTION));
		// 9. Click "Edit" link
		productControl.click(ProductDetailModel.EDIT_MODE);
		// 10. Upload marketing material
		productControl.uploadFile(AddEditProductModel.ADD_MARKETING, AddEditProductModel.FileUpload.AUDIO_ROUTE_FILE.getName());
		productControl.clickOptionByIndex(AddEditProductModel.MARKETING_METERIAL_TYPE, 1);
		// 11. Click "Save" link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		/*
		 * Verify that The "Request Marketing Review" link is displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_ACTION), ProductDetailModel.ProductActions.REQUEST_MARKETING_REVIEW.getName());
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
	}	
		
		
	/*
	 * Verify that the Marketing status is changed to "Pending DTS Review" and the action is "Cancel Request" when partner click "Request Marketing Review".
	 */
	@Test
	public void TC040PMS_04() {
		productControl.addLog("ID : 040PMS_02 : Verify that the Marketing status is changed "
				+ "to 'Pending DTS Review' and the action is 'Cancel Request' when partner "
				+ "click 'Request Marketing Review'.");
		/*
			Pre-condition: the tuning approval is approved. the partner user has "Publish and suspend Products" rights.
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Click "Add product" link
			4. Fill valid value into all required fields
			5. Upload valid tuning file
			6. Upload marketing material
			7. Click "Save" link
			8. Go through the tuning approval
			VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and its action is "Request Marketing Review"
			9. Click "Request Marketing Review" link
		*/
		// 1. Log into DTS portal as a partner user
//		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//		// 2. Navigate to "Accessories" page
//		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//		// 3. Click "Add Accessory" link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		// 4. Fill valid value into all required fields
//		// 5. Upload valid tuning file
//		// 6. Upload marketing material
//		// 7. Click "Save" link
//		Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//		//8. Go through the tuning approval
//		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
//		// Log out
//		productControl.logout();
//		// Log into DTS portal as DTS user
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// Navigate to product page
//		productControl.click(PageHome.linkAccessories);
//		// Select product above
//		productControl.selectAccessorybyName(data.get("name"));
//		// Select tuning rating
//		productControl.click(ProductDetailModel.EDIT_MODE);
//		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, AddEditProductModel.TuningRatingOption.A.getName());
//		productControl.click(AddEditProductModel.SAVE);
//		// Approve tuning
//		productControl.click(ProductDetailModel.APPROVE_TUNING);
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.addProductAndTunningAndMarketing(data, false);
		// Log out
		productControl.logout();
		// Log into DTS portal as partner user above
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Navigate to Product page
		productControl.click(PageHome.linkAccessories);
		// Select product above
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and its action is "Request Marketing Review"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), 
				ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_ACTION), 
				ProductDetailModel.ProductActions.REQUEST_MARKETING_REVIEW.getName());
		// 9. Click "Request Marketing Review" link
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		/*
		 * Verify that The Marketing status is changed to "Pending DTS Review" and the action is "Cancel Request"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), "PENDING DTS REVIEW");
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_ACTION), ProductDetailModel.ProductActions.MARKETING_CANCEL_REQUEST.getName());
		// Delete product
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 * Verify that the Marketing status is changed to "APPROVED" and there is no action displayed when the marketing material is approved by DTS admin.
	 */
	@Test
	public void TC040PMS_05() {
		productControl.addLog("ID : 040PMS_05 : Verify that the Marketing status is changed "
				+ "to 'APPROVED' and there is no action displayed when the marketing material is approved by DTS admin");
		/*
			Pre-condition: the tuning approval is approved. the partner user has "Publish and suspend accessories" rights.
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Fill valid value into all required fields
			5. Upload valid tuning file
			6. Upload marketing material
			7. Click "Save" link
			8. Go through the tuning approval
			VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and its action is "Request Marketing Review"
			9. Click "Request Marketing Review" link
			10. Log out DTS portal
			11. Log into DTS portal as DTS admin
			12. Navigate to "Accessories" page
			13. Select and open above accessory
			14. Click "Approve Marketing" link
			15. Log out DTS portal
			16. Log into DTS portal as above partner user
			17. Navigate to "Accessories" page
			18. Select and open above accessory
		*/
//		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//		// 2. Navigate to "Accessories" page
//		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//		//3. Click "Add Accessory" link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		//4. Fill valid value into all required fields
//		//5. Upload valid tuning file
//		//6. Upload marketing material
//		//7. Click "Save" link
//		/*
//		 * Init data
//		 */
//		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//		//8. Go through the tuning approval
//		productControl.logout();
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		productControl.click(DTSHome.LINK_ACCESSORIES);
//		productControl.selectAccessorybyName(data.get("name"));
//		productControl.click(ProductDetailModel.EDIT_MODE);
//		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, AddEditProductModel.TuningRatingOption.A.getName());
//		productControl.click(AddEditProductModel.SAVE);
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
//		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
//		productControl.click(ProductDetailModel.APPROVE_TUNING);
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, false);
		productWf.addProductAndTunningAndMarketing(data, false);
		
		productControl.logout();
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(data.get("name"));
		//	VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and its action is "Request Marketing Review"
		
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.APPROVED.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_ACTION), ProductDetailModel.ProductActions.REQUEST_MARKETING_REVIEW.getName());
		//	9. Click "Request Marketing Review" link
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		// 10. Log out DTS portal
		productControl.logout();
		// 11. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 12. Navigate to "Accessories" page
		productControl.click(DTSHome.LINK_ACCESSORIES);
		// 13. Select and open above accessory
		productControl.selectAccessorybyName(data.get("name"));
		// 14. Click "Approve Marketing" link
		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		// 15. Log out DTS portal
		productControl.logout();
		// 16. Log into DTS poratl as above partner user
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 17. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 18. Select and open avove accessory
		productControl.selectAccessorybyName(data.get("name"));
		// Verify marketing status is changed to "APPROVED" and its action is empty
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), ProductDetailModel.ProductStatus.APPROVED.getName());
		Assert.assertEquals(productControl.checkMaketingActionsDisplayed(), true);
		// Delete an Accessory after add
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 Verify that the marketing status is changed to "DECLINED" when the DTS admin declines marketing material
	*/
	@Test
	public void TC040PMS_06() {
		productControl.addLog("ID : 040PMS_06 : Verify that the marketing status is changed"
				+ " to 'DECLINED' when the DTS admin declines marketing material");
		/*
			Pre-condition: the tuning approval is approved. the partner user has "Publish and suspend accessories" rights.

				1. Log into DTS portal as a partner user
				2. Navigate to "Accessories" page
				3. Click "Add Accessory" link
				4. Fill valid value into all required fields
				5. Upload valid tuning file
				6. Upload marketing material
				7. Click "Save" link
				8. Go through the tuning approval
				VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and its action is "Request Marketing Review"
				9. Click "Request Marketing Review" link
				10. Log out DTS portal
				11. Log into DTS portal as DTS admin
				12. Navigate to "Accessories" page
				13. Select and open above accessory
				14. Click "Decline Marketing" link
				15. Log out DTS portal
				16. Log into DTS portal as above partner user
				17. Navigate to "Accessories" page
				18. Select and open above accessory
		*/
//		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//		// 2. Navigate to "Accessories" page
//		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//		//3. Click "Add Accessory" link
//		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		// 5. Upload valid tuning file
		// 6. Upload marketing material
		// 7. Click "Save" link
		/*
		 * Init data
		 */
//		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//		//8. Go through the tuning approval
//		productControl.logout();
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		productControl.click(DTSHome.LINK_ACCESSORIES);
//		productControl.selectAccessorybyName(data.get("name"));
//		productControl.click(ProductDetailModel.EDIT_MODE);
//		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, AddEditProductModel.TuningRatingOption.A.getName());
//		productControl.click(AddEditProductModel.SAVE);
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
//		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
//		productControl.click(ProductDetailModel.APPROVE_TUNING);
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, false);
		productWf.addProductAndTunningAndMarketing(data, false);
		
		productControl.logout();
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectAccessorybyName(data.get("name"));
		//VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and its action is "Request Marketing Review"
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.APPROVED.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_ACTION), ProductDetailModel.ProductActions.REQUEST_MARKETING_REVIEW.getName());
		
		//9. Click "Request Marketing Review" link
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		//10. Log out DTS portal
		productControl.logout();
		//11. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//12. Navigate to "Accessories" page
		productControl.click(DTSHome.LINK_ACCESSORIES);
		//13. Select and open above accessory
		productControl.selectAccessorybyName(data.get("name"));
		//14. Click "Decline Marketing" link
		productControl.click(ProductDetailModel.DECLINE_MARKETING);
		//15. Log out DTS portal
		productControl.logout();
		//16. Log into DTS portal as above partner user
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		//17. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		//18. Select and open above accessory
		productControl.selectAccessorybyName(data.get("name"));
		//The Marketing status is changed to "DECLINED" and there is no more action in marketing approval section
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS),ProductDetailModel.ProductStatus.DECLINED.getName());
		Assert.assertEquals(productControl.checkMaketingActionsDisplayed(), true);
		// Delete an Accessory after add
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	
	/*
	 Verify that the marketing status is changed to "REVOKED" and the action "Request Marketing Review" is displayed when the DTS admin revokes the approved marketing material
	*/
	@Test
	public void TC040PMS_07() {
		productControl.addLog("ID : 040PMS_07 : Verify that the marketing status is changed"
				+ " to 'REVOKED' and the action 'Request Marketing Review' is displayed when "
				+ "the DTS admin revokes the approved marketing material");
		/*
			Pre-condition:  The partner user has "Publish and suspend accessories" rights. The tuning approval is approved. The marketing approval is approved.

				1. Log into DTS portal as DTS admin
				2. Navigate to "Accessories" page
				3. Select an marketing approved accessory from accessories table
				4. Click "Revoke Marketing Approval" link
				5. Log out DTS portal
				6. Log into DTS portal as partner user
				7. Navigate to "Accessories" page
				8. Select above revoked marketing accessory
		*/
//		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//		// 2. Navigate to "Accessories" page
//		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//		//3. Click "Add Accessory" link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		/*
//		 * Init data
//		 */
//		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//		//Log out DTS portal
//		productControl.logout();
//		//Log into DTS portal as a DTS admin successfully
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		//Navigate to "Accessories" page
//		productControl.click(DTSHome.LINK_ACCESSORIES);
//		//Select and open above accessory
//		productControl.selectAccessorybyName(data.get("name"));
//		productControl.click(ProductDetailModel.EDIT_MODE);
//		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, AddEditProductModel.TuningRatingOption.A.getName());
//		productControl.click(AddEditProductModel.SAVE);
//		//Approve Tuning
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
//		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
//		productControl.click(ProductDetailModel.APPROVE_TUNING);
//		//Approve Marketing
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
//		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
//		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		// 3. Select an marketing approved accessory from accessories table
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, false);
		productWf.addProductAndTunningAndMarketing(data, true);
		// 4. Click "Revoke Marketing Approval" link
		productControl.click(ProductDetailModel.REVOKE_MARKETING_APPROVAL);
		// 5. Log out DTS portal
		productControl.logout();
		// 6. Log into DTS portal as partner user
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 7. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 8. Select above revoked marketing accessory
		productControl.selectAccessorybyName(data.get("name"));
		//Verify The Marketing status is changed to "REVOKED" and its action is "Request Marketing Review"
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), ProductDetailModel.ProductStatus.REVOKED.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_MARKETING_REVIEW), ProductDetailModel.ProductActions.REQUEST_MARKETING_REVIEW.getName());
	}
	
	/*
	 Verify that the approved marketing material is changed to "CLOSED" and there is no action in marketing approval section when the approved tuning file is removed
	*/
	@Test
	public void TC040PMS_08() {
		productControl.addLog("ID : 040PMS_08 : Verify that the approved marketing material "
				+ "is changed to 'CLOSED' and there is no action in marketing approval section "
				+ "when the approved tuning file is removed");
		/*
			Pre-condition:  The partner user has "Publish and suspend accessories" rights. The tuning approval is approved. The marketing approval is approved.

				1. Log into DTS portal as partner user
				2. Navigate to "Accessories" page
				3. Select an accessory from accessories table which both tuning and marketing approval are "APPROVED"
				4. Click �Edit� link
				5. Delete the approved tuning file
				6. Click �Save� link
		*/
//		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//		// Navigate to "Accessories" page
//		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//		// Click Add Accessory link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		// Create accessory
//		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//		//Log out DTS portal
//		productControl.logout();
//		//Log into DTS portal as a DTS admin successfully
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		//Navigate to "Accessories" page
//		productControl.click(DTSHome.LINK_ACCESSORIES);
//		//Select and open above accessory
//		productControl.selectAccessorybyName(data.get("name"));
//		productControl.click(ProductDetailModel.EDIT_MODE);
//		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, AddEditProductModel.TuningRatingOption.A.getName());
//		productControl.click(AddEditProductModel.SAVE);
//		//Approve Tuning
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
//		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
//		productControl.click(ProductDetailModel.APPROVE_TUNING);
//		//Approve Marketing
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
//		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
//		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, false);
		productWf.addProductAndTunningAndMarketing(data, true);
		productControl.logout();
		//1. Log into DTS portal as partner user
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		//2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		//3. Select an accessory from accessories table which both tuning and marketing approval are "APPROVED"
		productControl.selectAccessorybyName(data.get("name"));
		//4. Click �Edit� link
		productControl.click(ProductDetailModel.EDIT_MODE);
		//5. Delete the approved tuning file
		productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
		//6. Click �Save� link
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		//Verify The tuning status is changed to "UNSUBMITTED" and marketing status is changed to "CLOSED" and its action is empty.
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.MARKETING_STATUS), ProductDetailModel.ProductStatus.CLOSED.getName());
		Assert.assertEquals(productControl.checkMaketingActionsDisplayed(), true);
	}
	
}
