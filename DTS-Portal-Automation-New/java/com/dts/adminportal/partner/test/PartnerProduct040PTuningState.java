package com.dts.adminportal.partner.test;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.VariantInfo;
import com.dts.adminportal.util.TestData;

public class PartnerProduct040PTuningState extends BasePage{
	
	String AccessoryName = "Accessory"+RandomStringUtils.randomNumeric(4);
	@Override
	protected void initData() {
	}	

	
	/*
	 Verify that when there is no tuning is uploaded, the tuning action link is "Request DTS Tuning and tuning status is "UNSUBMITTED".
    */
	@Test
	public void TC040PTS_01 (){
		productControl.addLog("ID : TC040PTS_01 : Verify that when there is no tuning is uploaded, the tuning action link is 'Request DTS Tuning and tuning status is 'UNSUBMITTED'.");
		/*Pre-condition: Partner user has "Request accessory tunings" rights.

			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Fill valid value into all required fields
			5. Do not upload tuning
			6. Click "Save" link
		*/
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		//3. Click "Add Accessory" link
		productControl.click(ProductModel.ADD_PRODUCT);
		//4,5. Fill valid value into all required fields and Do not upload tuning
		//6. Click "Save" link
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
	
		/*
		 Verify that when there is no tuning is uploaded, the tuning status is 'UNSUBMITTED'.
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME), data.get("name"));
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
		
		//Teardown
		productControl.doDelete(ProductDetailModel.DELETE);
		
	}
	
	/*
	 Verify that when the partner uploads tuning file, the tuning action link is "Request Tuning Review" and tuning status is "UNSUBMITTED".
	 */
	@Test
	public void TC040PTS_02() {
		productControl.addLog("ID : TC040PTS_02 : Verify that when the partner uploads tuning file, the tuning action link is 'Request Tuning Review' and tuning status is 'UNSUBMITTED'.");
		/*
		 Pre-Condition: partner user has "Add and manage accessories" rights.

			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Fill valid value into all required fields
			5. Upload valid tuning file
			6. Click "Save" link
		*/
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		//3. Click "Add Accessory" link
		productControl.click(ProductModel.ADD_PRODUCT);
		//4,5. Fill valid value into all required fields and Upload valid tuning file
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		
		// Verify that when there is tuning is uploaded, the tuning status is 'UNSUBMITTED'.
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME), data.get("name"));
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), 
				ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_TUNING_REVIEW), 
				ProductDetailModel.ProductActions.REQUEST_TUNING_REVIEW.getName());
		
		//Teardown
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	/*
	 Verify that the Tuning status is changed to "PENDING DTS REVIEW" and tuning action is "Cancel Request" after partner user request tuning review
	 */
	@Test
	public void TC040PTS_03() {
		productControl.addLog("ID : TC040OTS_03 : Verify that the Tuning status is changed to 'PENDING DTS REVIEW' and tuning action is 'Cancel Request' after partner user request tuning review");
		/*
		 Pre-Condition: partner user has "Add and manage accessories" rights.
		1. Log into DTS portal as a partner user
		2. Navigate to "Accessories" page
		3. Click "Add Accessory" link
		4. Fill valid value into all required fields
		5. Upload valid tuning file
		6. Click "Save" link
		VP: Verify that the tuning action link is "Request Tuning Review" and the tuning status is "UNSUBMITTED"
		7. Click "Request Tuning Review"  link
		*/
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click add accessory link
		productControl.click(ProductModel.ADD_PRODUCT);
		//4,5. Fill valid value into all required fields and Upload valid tuning file
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		
		// Verify that when there is tuning is uploaded, the tuning status is 'UNSUBMITTED'.
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME), data.get("name"));
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), 
				ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_TUNING_REVIEW), 
				ProductDetailModel.ProductActions.REQUEST_TUNING_REVIEW.getName());
		
		//7. Click "Request Tuning Review"  link
		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
		//VP: Verify that the Tuning status is changed to 'PENDING DTS REVIEW' and tuning action is 'Cancel Request' after partner user request tuning review
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), 
				ProductDetailModel.ProductStatus.PENDING_DTS_APPROVAL.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.CANCEL_REQUEST_TUNING), 
				ProductDetailModel.ProductActions.TUNING_CANCEL_REQUEST.getName());
	
		//Teardown
		productControl.doDelete(ProductDetailModel.DELETE);
	}
	/*
	 Verify that the tuning status is changed to "UNSUBMITTED" and tuning action is "Request Tuning Review" after canceling the request tuning review
	*/
	@Test
	public void TC040PTS_04() {
		productControl.addLog("ID : TC040OTS_04 : Verify that the tuning status is changed "
				+ "to 'UNSUBMITTED' and tuning action is 'Request Tuning Review' after "
				+ "canceling the request tuning review");
		/*
		 * Pre-Condition: partner user has "Add and manage accessories" rights.
		 * 1. Log into DTS portal as a partner user 
		 * 2. Navigate to "Accessories" page 
		 * 3. Click "Add Accessory" link 
		 * 4. Fill valid value into all required fields 
		 * 5. Upload valid tuning file 
		 * 6. Click "Save" link 
		 * VP: Verify that the tuning action link is "Request Tuning Review" and the tuning status is "UNSUBMITTED" 
		 * 7. Click "Request Tuning Review" link
		 * VP: Tuning status is changed to "PENDING DTS REVIEW" and tuning action is "Cancel Request" 
		 * 8. Click "Cancel Request" link
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click add accessory link
		productControl.click(ProductModel.ADD_PRODUCT);
		//4,5. Fill valid value into all required fields and Upload valid tuning file
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
					
		// Verify that when there is tuning is uploaded, the tuning status is 'UNSUBMITTED'.
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME), data.get("name"));
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), 
				ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_TUNING_REVIEW), 
				ProductDetailModel.ProductActions.REQUEST_TUNING_REVIEW.getName());
		
		//7. Click "Request Tuning Review"  link
		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
		//VP: Verify that the Tuning status is changed to 'PENDING DTS REVIEW' and tuning action is 'Cancel Request' after partner user request tuning review
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), 
				ProductDetailModel.ProductStatus.PENDING_DTS_APPROVAL.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.CANCEL_REQUEST_TUNING), 
				ProductDetailModel.ProductActions.TUNING_CANCEL_REQUEST.getName());
		//8. Click "Cancel Request" link
		productControl.click(ProductDetailModel.CANCEL_REQUEST_TUNING);
		
		//Verify that the tuning status is changed to "UNSUBMITTED" and tuning action is "Request Tuning Review" after canceling the request tuning review
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), 
				ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_TUNING_REVIEW), 
				ProductDetailModel.ProductActions.REQUEST_TUNING_REVIEW.getName());	
	
		//Teardown
		productControl.doDelete(ProductDetailModel.DELETE);
		}
		
	/*
	 * Verify that user is able to download the Certification Badge after the tuning file is approved
	 */
	@Test
	public void TC040PTS_05() {
		productControl.addLog("ID : TC040PTS_05 : Verify that the tuning status is changed"
				+ " to 'APPROVED' and tuning action link is 'Download Certification Badges' "
				+ "after the DTS  admin approves the Partner tuning.");

		/*
		  	Pre-Condition: partner user has "Add and manage accessories" rights.
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Click "Add product" link
			4. Fill valid value into all required fields
			5. Upload valid tuning file
			6. Click "Save" link
			VP: Verify that the tuning action link is  "Request Tuning Review"
			7. Click  "Request Tuning Review" link
			8. Log out DTS portal
			9. Log into DTS portal as DTS admin
			10. Navigate to "Accessories" page
			11. Select above product which its tuning is pending for DTS review
			12. Click "Approve Tuning" link
			13. Log out DTS portal
			14. Log into DTS portal as above partner user
			15. Navigate to "Accessories" page
			16. Select and open above product
			The  tuning status is changed to "APPROVED" and tuning action link is "Download Certification Badges"
			17. Click on the "Download Certification Badge" link
			Partner user is able to download the Certification Badge sucessfully
		 */
//		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//		// 2. Navigate to "Accessories" page
//		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//		// 3. Click add accessory link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		//4,5. Fill valid value into all required fields and Upload valid tuning file
//		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//					
//		// Verify that when there is tuning is uploaded, the tuning status is 'UNSUBMITTED'.
//		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME), data.get("name"));
//		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
//		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_TUNING_REVIEW), ProductDetailModel.ProductActions.REQUEST_TUNING_REVIEW.getName());
//					
//		//7. Click "Request Tuning Review"  link
//		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
//		//	8. Log out DTS portal
//		//	9. Log into DTS portal as DTS admin
//		productControl.logout();
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		//	10. Navigate to "Accessories" page
//		//	11. Select above accessory which its tuning is pending for DTS review
//		productControl.click(PageHome.linkAccessories);
//		productControl.selectAccessorybyName(data.get("name"));
//		productControl.click(ProductDetailModel.EDIT_MODE);
//		productControl.selectOptionByName(AddEditProductModel.TUNING_RATING,AddEditProductModel.TuningRatingOption.A.getName());
//		productControl.click(AddEditProductModel.SAVE);
//		//	12. Click "Approve Tuning" link
//		productControl.click(ProductDetailModel.APPROVE_TUNING);
//		13. Log out DTS portal
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productWf.addProductAndTunningAndMarketing(data, false);
//		14. Log into DTS portal as above partner user
		productControl.logout();
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//		15. Navigate to "Accessories" page
//		16. Select and open above accessory
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(data.get("name"));
		//Verify that the tuning status is changed to 'APPROVED' and tuning action link is 'Download Certification Badges' after the DTS  admin approves the Partner tuning.
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), 
				ProductDetailModel.ProductStatus.APPROVED.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.DOWNLOAD_CERTIFICATE), 
				ProductDetailModel.ProductActions.DOWNLOAD_CERT_BADGES.getName());
		// implement download certificate function here
//		productControl.click(ProductDetailModel.DOWNLOAD_CERTIFICATE);
		
		//Teardown
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
		
		// Assert.assertTrue(false);
	}
		
	/*
	 * Verify that the tuning status is changed to "DECLINED" and there is no tuning action link displayed after the DTS  admin declines the Partner tuning. 
	 */
	@Test
	public void TC040PTS_06() {
		productControl.addLog(" ID : TC040PTS_06 : Verify that the tuning status is changed "
				+ "to 'DECLINED' and there is no tuning action link displayed after the DTS  "
				+ "admin declines the Partner tuning. ");
		
		/*Pre-Condition: partner user has "Add and manage accessories" rights.
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
			12. Click "Decline Tuning" link
			13. Log out DTS portal
			14. Log into DTS portal as above partner user
			15. Navigate to "Accessories" page
			16. Select and open above accessory
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 2. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 4. Fill valid value into all required fields
		// 5. Upload valid tuning file
		// 6. Click "Save" link
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
		/*
		 * VP: Verify that the tuning action link is "Request Tuning Review"
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_TUNING_REVIEW), ProductDetailModel.ProductActions.REQUEST_TUNING_REVIEW.getName());
		// 7. Click "Request Tuning Review" link
		productControl.click(AddEditProductModel.REQUEST_TUNING_REVIEW);
		// 8. Log out DTS portal
		productControl.logout();
		// 9. Log into DTS portal as DTS admin
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 10. Navigate to "Accessories" page
		productControl.click(PageHome.linkAccessories);
		// 11. Select above accessory which its tuning is pending for DTS review
		productControl.selectAccessorybyName(data.get("name"));
		// Select tuning rating
		productControl.click(ProductDetailModel.EDIT_MODE);
		productControl.clickOptionByIndex(AddEditProductModel.TUNING_RATING, 2);
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		// 12. Click "Decline Tuning" link
		productWf.declineTuning();
		
//		productControl.click(ProductDetailModel.DECLINE_TUNING);
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//		productWf.addNewProduct(data);
//		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
//		productWf.declineTuning();
		// 13. Log out DTS portal
		productControl.logout();
		// 14. Log into DTS portal as above partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 15. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 16. Select and open above accessory
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * Verify The tuning status is changed to "DECLINED" and there is no tuning action displayed
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "DECLINED");
		Assert.assertFalse(productControl.isElementPresent(ProductDetailModel.PARTNER_ACTIONS_STEP_1));
		// Delete accessory
		productControl.doDelete(ProductDetailModel.DELETE);
	}
		
	/*
	 * Verify that the tuning status is changed to "REVOKED" when the DTS  admin revoke the Partner tuning
	 */
	@Test
	public void TC040PTS_07() {
		productControl.addLog(" ID : TC040PTS_07 : Verify that the tuning status is changed to 'REVOKED' when the DTS  admin revoke the Partner tuning");
		/*
			Pre-Condition: partner user has "Add and manage accessories" rights.
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
			VP: Verify that the tuning action link is changed to "Revoke Tuning"
			13. Click "Revoke Tuning" link
			14. Log out DTS portal
			15. Log into DTS portal as above partner user again
			16. Navigate to "Accessories" page
			17. Select and open above accessory
		*/
//			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//		// 2. Navigate to "Accessories" page
//		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//		// 3. Click "Add Accessory" link
//		productControl.click(ProductModel.ADD_PRODUCT);
//		// 4. Fill valid value into all required fields
//		// 5. Upload valid tuning file
//		// 6. Click "Save" link
//		/*
//		 * Init data
//		 */
//		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//		/*
//		 * VP: Verify that the tuning action link is "Request Tuning Review"
//		 */
//		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_TUNING_REVIEW), ProductDetailModel.ProductActions.REQUEST_TUNING_REVIEW.getName());
//		// 7. Click "Request Tuning Review" link
//		productControl.click(ProductDetailModel.REQUEST_TUNING);
//		// 8. Log out DTS portal
//		productControl.logout();
//		// 9. Log into DTS portal as DTS admin
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//		// 10. Navigate to "Accessories" page
//		productControl.click(PageHome.linkAccessories);
//		// 11. Select above accessory which its tuning is pending for DTS review
//		productControl.selectAccessorybyName(data.get("name"));
//		// Select tuning rating
//		productControl.click(ProductDetailModel.EDIT_MODE);
//		productControl.clickOptionByIndex(AddEditProductModel.TUNING_RATING, 2);
//		productControl.click(AddEditProductModel.SAVE);
//		// 12. Click "Approve Tuning" link
//		productControl.click(ProductDetailModel.APPROVE_TUNING);
		
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
		productWf.addNewProduct(data);
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		loginControl.logout();
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productControl.click(PageHome.linkAccessories);
		productControl.selectAccessorybyName(data.get("name"));
		productControl.click(ProductDetailModel.EDIT_MODE);
		productControl.clickOptionByIndex(AddEditProductModel.TUNING_RATING, 2);
		productControl.click(AddEditProductModel.SAVE_PRODUCT);
		productWf.approveTuning();
		/*
		 * VP: Verify that the tuning action link is changed to "Revoke Tuning"
		 */
		Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.REVOKE_TUNING));
		// 13. Click "Revoke Tuning" link
		productWf.revokeTuning();
		// 14. Log out DTS portal
		productControl.logout();
		// 15. Log into DTS portal as above partner user again
		loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
		// 16. Navigate to "Accessories" page
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		// 17. Select and open above accessory
		productControl.selectAccessorybyName(data.get("name"));
		/*
		 * The tuning status is changed to "REVOKED" and there is no action link displayed for Tuning Approval
		 */
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.REVOKED.getName());
		Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_TUNING), "");
	}	
			
		/*
		 Verify that the tuning status is changed to 'UNSUBMITTED' and its action is 'Request DTS Tuning' when partner user removes tuning file
		*/
		@Test
		public void TC040PTS_08() {
			productControl.addLog(" ID : TC040PTS_08 : Verify that the tuning status is changed "
					+ "to 'UNSUBMITTED' and its action is 'Request DTS Tuning' when partner user "
					+ "removes tuning file");
			/*Pre-Condition: partner user has "Add and manage accessories" rights. The tuning approval is approved.
				
				1. Log into DTS portal as a partner user
				2. Navigate to "Accessories" page
				3. Select an approved tuning accessory from accessories table
				VP: Verify that the tuning approval status of selected accessory is approved
				4. Click "Delete" link beside tuning link
					-Click "Edit" button beside name's accessory
					-Click "Delete" popup is displayed after click "delete" link
					-Click "save"
				
			 */
//			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//			// 2. Navigate to "Accessories" page
//			productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//			// 3. Click add accessory link
//			productControl.click(ProductModel.ADD_PRODUCT);
//			//4,5. Fill valid value into all required fields and Upload valid tuning file
//			Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//			productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//						
//			// Verify that when there is tuning is uploaded, the tuning status is 'UNSUBMITTED'.
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME), data.get("name"));
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_TUNING_REVIEW), ProductDetailModel.ProductActions.REQUEST_TUNING_REVIEW.getName());
//						
//			//7. Click "Request Tuning Review"  link
//			productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
//			//	8. Log out DTS portal
//			//	9. Log into DTS portal as DTS admin
//			productControl.logout();
//			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			//	10. Navigate to "Accessories" page
//			//	11. Select above accessory which its tuning is pending for DTS review
//			productControl.click(PageHome.linkAccessories);
//			productControl.selectAccessorybyName(data.get("name"));
//			productControl.click(ProductDetailModel.EDIT_MODE);
//			productControl.selectOptionByName(AddEditProductModel.TUNING_RATING,AddEditProductModel.TuningRatingOption.A.getName());
//			productControl.click(AddEditProductModel.SAVE);
//			//	12. Click "Approve Tuning" link
//			productControl.click(ProductDetailModel.APPROVE_TUNING);
			
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
			productWf.addProductAndTunningAndMarketing(data, false);
//			13. Log out DTS portal
			productControl.logout();
			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//			15. Navigate to "Accessories" page
//			16. Select and open above accessory
			productControl.click(PageHome.linkAccessories);
			productControl.selectAccessorybyName(data.get("name"));
			//Verify that the tuning status is changed to 'APPROVED' and tuning action link is 'Download Certification Badges' after the DTS  admin approves the Partner tuning.
			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), 
					ProductDetailModel.ProductStatus.APPROVED.getName());
			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.DOWNLOAD_CERTIFICATE), 
					ProductDetailModel.ProductActions.DOWNLOAD_CERT_BADGES.getName());
			//Edit product
			productControl.click(ProductDetailModel.EDIT_MODE);
			//remove tuning
			productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
			//Click Save
			productControl.click(AddEditProductModel.SAVE_PRODUCT);
			
			//Verify that the tuning status is changed to 'UNSUBMITTED' and its action is 'Request DTS Tuning' when partner user removes tuning file
			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME), data.get("name"));
			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), 
					ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_TUNING), 
					ProductDetailModel.ProductActions.REQUEST_DTS_TUNING.getName());
		}
		/*
		 Verify that the variant tuning status is "Using parent's model" when adding new variant with the inherited tuning file from its parent.
		*/
		@Test
		public void TC040PTS_09() {
			productControl.addLog("ID : TC040PTS_09: Verify that the variant tuning status "
					+ "is 'Using parent's model' when adding new variant with the inherited "
					+ "tuning file from its parent.");

			/*Pre-Condition: partner user has "Add and manage accessories" rights. The tuning approval is approved.
					1. Log into DTS portal as a partner user
					2. Navigate to "Accessories" page
					3. Select an approved tuning accessory from accessories table
					VP: Verify that the tuning approval status of selected accessory is approved
					4. Click "Add New Variant" link in "Model Actions" module
					5. Fill valid value into all required fields
					6. Tick to check for "Use Parent's Tuning" checkbox
					7. Click "Save" link
			*/
//			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//			// 2. Navigate to "Accessories" page
//			productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//			// 3. Click add accessory link
//			productControl.click(ProductModel.ADD_PRODUCT);
//			//4,5. Fill valid value into all required fields and Upload valid tuning file
//			Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//			productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//						
//			// Verify that when there is tuning is uploaded, the tuning status is 'UNSUBMITTED'.
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME), data.get("name"));
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_TUNING_REVIEW), ProductDetailModel.ProductActions.REQUEST_TUNING_REVIEW.getName());
//						
//			//7. Click "Request Tuning Review"  link
//			productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
//			//	8. Log out DTS portal
//			//	9. Log into DTS portal as DTS admin
//			productControl.logout();
//			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			//	10. Navigate to "Accessories" page
//			//	11. Select above accessory which its tuning is pending for DTS review
//			productControl.click(PageHome.linkAccessories);
//			productControl.selectAccessorybyName(data.get("name"));
//			productControl.click(ProductDetailModel.EDIT_MODE);
//			productControl.selectOptionByName(AddEditProductModel.TUNING_RATING,AddEditProductModel.TuningRatingOption.A.getName());
//			productControl.click(AddEditProductModel.SAVE);
//			//	12. Click "Approve Tuning" link
//			productControl.click(ProductDetailModel.APPROVE_TUNING);
////						13. Log out DTS portal
////						14. Log into DTS portal as above partner user
			
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
			productWf.addProductAndTunningAndMarketing(data, false);
			productControl.logout();
			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//			15. Navigate to "Accessories" page
//			16. Select and open above accessory
			productControl.click(PageHome.linkAccessories);
			productControl.selectAccessorybyName(data.get("name"));
			//Verify that the tuning status is changed to 'APPROVED' and tuning action link is 'Download Certification Badges' after the DTS  admin approves the Partner tuning.
			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), 
					ProductDetailModel.ProductStatus.APPROVED.getName());
			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.DOWNLOAD_CERTIFICATE), 
					ProductDetailModel.ProductActions.DOWNLOAD_CERT_BADGES.getName());
			// click "Add new variant" link
			productControl.click(ProductDetailModel.ADD_VARIANT);
			Hashtable <String, String> dataVariant= TestData.variantData(false, false, false);
			productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
			//The variant tuning status is "Using parent model's" and there is no more action link in this section
			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TITLE_NAME), dataVariant.get("name"));
			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.USING_PARENT_TUNING);
			Assert.assertFalse(productControl.isElementPresent(VariantInfo.PARTNER_ACTIONS_STEP_1));
			
			//Teardown: Delete product
			productControl.click(VariantInfo.PRODUCT_LINK);
			productControl.doDelete(ProductDetailModel.DELETE);
						
		}
		/*
		 Verify that when the partner uploads custom tuning file for variant, the tuning action link is "Request Tuning Review" and tuning status is "UNSUBMITTED".
		*/
		@Test
		public void TC040PTS_10() {
			productControl.addLog("ID : TC040PTS_10 : Verify that when the partner uploads "
					+ "custom tuning file for variant, the tuning action link is 'Request Tuning "
					+ "Review' and tuning status is 'UNSUBMITTED'.");

			/*Pre-Condition: partner user has "Add and manage accessories" rights. The tuning approval is approved.

				1. Log into DTS portal as a partner user
				2. Navigate to "Accessories" page
				3. Select an approved tuning accessory from accessories table
				VP: Verify that the tuning approval status of selected accessory is approved
				4. Click "Add New Variant" link in "Model Actions" module
				5. Fill valid value into all required fields
				6. Upload custom variant tuning file
				7. Click "Save" link
			*/
//			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//			// 2. Navigate to "Accessories" page
//			productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//			// 3. Click add accessory link
//			productControl.click(ProductModel.ADD_PRODUCT);
//			//4,5. Fill valid value into all required fields and Upload valid tuning file
//			Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//			productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//						
//			// Verify that when there is tuning is uploaded, the tuning status is 'UNSUBMITTED'.
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME), data.get("name"));
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_TUNING_REVIEW), ProductDetailModel.ProductActions.REQUEST_TUNING_REVIEW.getName());
//						
//			//7. Click "Request Tuning Review"  link
//			productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
//			//	8. Log out DTS portal
//			//	9. Log into DTS portal as DTS admin
//			productControl.logout();
//			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			//	10. Navigate to "Accessories" page
//			//	11. Select above accessory which its tuning is pending for DTS review
//			productControl.click(PageHome.linkAccessories);
//			productControl.selectAccessorybyName(data.get("name"));
//			productControl.click(ProductDetailModel.EDIT_MODE);
//			productControl.selectOptionByName(AddEditProductModel.TUNING_RATING,AddEditProductModel.TuningRatingOption.A.getName());
//			productControl.click(AddEditProductModel.SAVE);
//			//	12. Click "Approve Tuning" link
//			productControl.click(ProductDetailModel.APPROVE_TUNING);
////									13. Log out DTS portal
////									14. Log into DTS portal as above partner user
//			productControl.logout();
//			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
////									15. Navigate to "Accessories" page
////									16. Select and open above accessory
//			productControl.click(PageHome.linkAccessories);
//			productControl.selectAccessorybyName(data.get("name"));
//			//Verify that the tuning status is changed to 'APPROVED' and tuning action link is 'Download Certification Badges' after the DTS  admin approves the Partner tuning.
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.APPROVED.getName());
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.DOWNLOAD_CERTIFICATE), ProductDetailModel.ProductActions.DOWNLOAD_CERT_BADGES.getName());
//			// click "Add new variant" link
//			productControl.click(ProductDetailModel.ADD_VARIANT);
//			Hashtable <String, String> datavariant=TestData.variantData(true, false, false);
//			productControl.addVariant(AddEditProductModel.getVariantHash(), datavariant);
			
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			Hashtable<String, String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
			Hashtable<String,String> dataVariant = TestData.variantData(true, false, false);
			productWf.addProductAndTunningAndMarketing(dataProduct, false);
//			productWf.addNewVariant(dataVariant);
			
			loginControl.logout();
			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
			productControl.click(PageHome.linkAccessories);
			productControl.selectProductByName(dataProduct.get("name"));
			productWf.addNewVariant(dataVariant);
//			productControl.selectAVariantbyName(dataVariant.get("name"));
			//The variant tuning status is "Using parent model's" and there is no more action link in this section
			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TITLE_NAME), dataVariant.get("name"));
			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.UNSUBMITTED);
			Assert.assertFalse(productControl.isElementPresent(VariantInfo.REQUEST_DTS_TUNING));
//			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.REQUEST_DTS_TUNING),VariantInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
			
			//Teardown: Delete product
			productControl.click(VariantInfo.PRODUCT_LINK);
			productControl.doDelete(ProductDetailModel.DELETE);
		}
		
		/*
		 Verify that the variant tuning status is changed to "PENDING DTS REVIEW" and variant tuning action is "Cancel Request" after partner user request variant tuning review
		 */
		@Test
		public void TC040PTS_11() {
			productControl.addLog("ID : TC040PTS_11 : Verify that the variant tuning status is "
					+ "changed to 'PENDING DTS REVIEW' and variant tuning action is 'Cancel Request' "
					+ "after partner user request variant tuning review");

			/*Pre-Condition: partner user has "Add and manage accessories" rights. The tuning approval is approved.

				1. Log into DTS portal as a partner user
				2. Navigate to "Accessories" page
				3. Select an approved tuning accessory from accessories table
				VP: Verify that the tuning approval status of selected accessory is approved
				4. Click "Add New Variant" link in "Model Actions" module
				5. Fill valid value into all required fields
				6. Upload custom variant tuning file
				7. Click "Save" link
				VP: Verify that the variant tuning action link is "Request Tuning Review" and the tuning status is "UNSUBMITTED"
				8. Click "Request Tuning Review"  link
				-Click "Agree" and "submit" link
			*/
//			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//			// 2. Navigate to "Accessories" page
//			productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//			// 3. Click add accessory link
//			productControl.click(ProductModel.ADD_PRODUCT);
//			//4,5. Fill valid value into all required fields and Upload valid tuning file
//			Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//			productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//						
//			// Verify that when there is tuning is uploaded, the tuning status is 'UNSUBMITTED'.
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME), data.get("name"));
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_TUNING_REVIEW), ProductDetailModel.ProductActions.REQUEST_TUNING_REVIEW.getName());
//						
//			//7. Click "Request Tuning Review"  link
//			productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
//			//	8. Log out DTS portal
//			//	9. Log into DTS portal as DTS admin
//			productControl.logout();
//			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			//	10. Navigate to "Accessories" page
//			//	11. Select above accessory which its tuning is pending for DTS review
//			productControl.click(PageHome.linkAccessories);
//			productControl.selectAccessorybyName(data.get("name"));
//			productControl.click(ProductDetailModel.EDIT_MODE);
//			productControl.selectOptionByName(AddEditProductModel.TUNING_RATING,AddEditProductModel.TuningRatingOption.A.getName());
//			productControl.click(AddEditProductModel.SAVE);
//			//	12. Click "Approve Tuning" link
//			productControl.click(ProductDetailModel.APPROVE_TUNING);
////												13. Log out DTS portal
////												14. Log into DTS portal as above partner user
//			productControl.logout();
//			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
////												15. Navigate to "Accessories" page
////												16. Select and open above accessory
//			productControl.click(PageHome.linkAccessories);
//			productControl.selectAccessorybyName(data.get("name"));
//			//Verify that the tuning status is changed to 'APPROVED' and tuning action link is 'Download Certification Badges' after the DTS  admin approves the Partner tuning.
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.APPROVED.getName());
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.DOWNLOAD_CERTIFICATE), ProductDetailModel.ProductActions.DOWNLOAD_CERT_BADGES.getName());
//			// click "Add new variant" link
//			productControl.click(ProductDetailModel.ADD_VARIANT);
//			Hashtable <String, String> datavariant=TestData.variantData(true, false, false);
//			productControl.addVariant(AddEditProductModel.getVariantHash(), datavariant);
			
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			Hashtable<String, String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
			Hashtable<String,String> dataVariant = TestData.variantData(true, false, false);
			productWf.addProductAndTunningAndMarketing(dataProduct, false);
//			productWf.addNewVariant(dataVariant);
			
			loginControl.logout();
			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
			productControl.click(PageHome.linkAccessories);
			productControl.selectProductByName(dataProduct.get("name"));
			productWf.addNewVariant(dataVariant);
//			productControl.selectAVariantbyName(dataVariant.get("name"));
			
			//The variant tuning status is "Using parent model's" and there is no more action link in this section
			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TITLE_NAME), dataVariant.get("name"));
			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.UNSUBMITTED);
			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.REQUEST_TUNING_REVIEW),VariantInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
			//8. Click "Request Tuning Review"  link
			productControl.click(VariantInfo.REQUEST_TUNING_REVIEW);
			//The variant tuning status is changed to "PENDING DTS REVIEW" and its action is "Cancel Request"
			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.PENDING_DTS_APPROVAL);
			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.CANCEL_REQUEST_TUNING),VariantInfo.TUNING_CANCEL_REQUEST_ACTION_STRING);
			
			//Teardown: Delete product
			productControl.click(VariantInfo.PRODUCT_LINK);
			productControl.doDelete(ProductDetailModel.DELETE);
		}
		/*
		 Verify that the variant tuning status is changed to "UNSUBMITTED" and its action is "Request Tuning Review" after canceling the request tuning review
		 */
		@Test
		public void TC040PTS_12() {
			productControl.addLog("ID : TC040PTS_12 : Verify that the variant tuning status is changed to 'UNSUBMITTED' and its action is 'Request Tuning Review' after canceling the request tuning review");

			/*Pre-Condition: partner user has "Add and manage accessories" rights. The tuning approval is approved.

					1. Log into DTS portal as a partner user
					2. Navigate to "Accessories" page
					3. Select an approved tuning accessory from accessories table
					VP: Verify that the tuning approval status of selected accessory is approved
					4. Click "Add New Variant" link in "Model Actions" module
					5. Fill valid value into all required fields
					6. Upload custom variant tuning file
					7. Click "Save" link
					VP: Verify that the tuning action link is "Request Tuning Review" and the tuning status is "UNSUBMITTED"
					8. Click "Request Tuning Review"  link
					-Click "Agree" and "submit" link
					VP: Tuning status is changed to "PENDING DTS REVIEW" and tuning action is "Cancel Request"
					9. Click "Cancel Request" link
			*/
//			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//			// 2. Navigate to "Accessories" page
//			productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//			// 3. Click add accessory link
//			productControl.click(ProductModel.ADD_PRODUCT);
//			//4,5. Fill valid value into all required fields and Upload valid tuning file
//			Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//			productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//						
//			// Verify that when there is tuning is uploaded, the tuning status is 'UNSUBMITTED'.
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME), data.get("name"));
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_TUNING_REVIEW), ProductDetailModel.ProductActions.REQUEST_TUNING_REVIEW.getName());
//						
//			//7. Click "Request Tuning Review"  link
//			productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
//			//	8. Log out DTS portal
//			//	9. Log into DTS portal as DTS admin
//			productControl.logout();
//			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			//	10. Navigate to "Accessories" page
//			//	11. Select above accessory which its tuning is pending for DTS review
//			productControl.click(PageHome.linkAccessories);
//			productControl.selectAccessorybyName(data.get("name"));
//			productControl.click(ProductDetailModel.EDIT_MODE);
//			productControl.selectOptionByName(AddEditProductModel.TUNING_RATING,AddEditProductModel.TuningRatingOption.A.getName());
//			productControl.click(AddEditProductModel.SAVE);
//			//	12. Click "Approve Tuning" link
//			productControl.click(ProductDetailModel.APPROVE_TUNING);
////															13. Log out DTS portal
////															14. Log into DTS portal as above partner user
//			productControl.logout();
//			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
////															15. Navigate to "Accessories" page
////															16. Select and open above accessory
//			productControl.click(PageHome.linkAccessories);
//			productControl.selectAccessorybyName(data.get("name"));
//			//Verify that the tuning status is changed to 'APPROVED' and tuning action link is 'Download Certification Badges' after the DTS  admin approves the Partner tuning.
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.APPROVED.getName());
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.DOWNLOAD_CERTIFICATE), ProductDetailModel.ProductActions.DOWNLOAD_CERT_BADGES.getName());
//			// click "Add new variant" link
//			productControl.click(ProductDetailModel.ADD_VARIANT);
//			Hashtable <String, String> datavariant=TestData.variantData(true, false, false);
//			productControl.addVariant(AddEditProductModel.getVariantHash(), datavariant);
			
			Hashtable<String, String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
			Hashtable<String,String> dataVariant = TestData.variantData(true, false, false);
			loginControl.login(PARTNER_USER, PARTNER_PASSWORD);
			productWf.addNewProduct(dataProduct);
			productControl.click(ProductDetailModel.REQUEST_TUNING);
			loginControl.logout();
			
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			productControl.click(PageHome.linkAccessories);
			productControl.selectAccessorybyName(dataProduct.get("name"));
			productControl.click(ProductDetailModel.EDIT_MODE);
			productControl.clickOptionByIndex(AddEditProductModel.TUNING_RATING, 2);
			productControl.click(AddEditProductModel.SAVE_PRODUCT);
			productWf.approveTuning();
			
			loginControl.logout();
			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
			productControl.click(PageHome.linkAccessories);
			productControl.selectProductByName(dataProduct.get("name"));
			productWf.addNewVariant(dataVariant);
			
			//The variant tuning status is "Using parent model's" and there is no more action link in this section
			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TITLE_NAME), dataVariant.get("name"));
			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.UNSUBMITTED);
			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.REQUEST_TUNING_REVIEW),VariantInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
			//8. Click "Request Tuning Review"  link
			productControl.click(VariantInfo.REQUEST_TUNING_REVIEW);
			//The variant tuning status is changed to "PENDING DTS REVIEW" and its action is "Cancel Request"
			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.PENDING_DTS_APPROVAL);
			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.CANCEL_REQUEST_TUNING),VariantInfo.TUNING_CANCEL_REQUEST_ACTION_STRING);
			
			//Click Click "Cancel Request" link
			productControl.click(VariantInfo.CANCEL_REQUEST_TUNING);
			//Variant tuning status is changed to "UNSUBMITTED" and tuning action is "Request Tuning Review"
			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.UNSUBMITTED);
			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.REQUEST_TUNING_REVIEW),VariantInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
			
			//Teardown: Delete product
			productControl.click(VariantInfo.PRODUCT_LINK);
			productControl.doDelete(ProductDetailModel.DELETE);
		}
		
		/*
		 * Verify that the variant tuning status is changed to "APPROVED" and its action link is "Download Certification Badges"  after the DTS  admin approves the Partner tuning
		 */
		@Test
		public void TC040PTS_13() {
			productControl.addLog("ID : TC040PTS_13 : Verify that the variant tuning status is changed"
					+ " to 'APPROVED' and its action link is 'Download Certification Badges'  after "
					+ "the DTS  admin approves the Partner tuning");
			/*
				Pre-Condition: partner user has "Add and manage Products" rights. The tuning approval is approved.
				1. Log into DTS portal as a partner user
				2. Navigate to "Accessories" page
				3. Select a product from product table
				4. Click "Add New Variant" link in "Model Actions" module
				5. Fill valid value into all required fields
				6. Upload custom variant tuning file
				7. Click "Save" link
				VP: Verify that the variant tuning action link is  "Request Tuning Review"
				8. Click  "Request Tuning Review" link
				9. Log out DTS portal
				10. Log into DTS portal as DTS admin
				11. Navigate to "Accessories" page
				12. Select above product
				13. Select above product's variant
				14. Click "Approve Tuning" link
				15. Log out DTS portal
				16. Log into DTS portal as above partner user
				17. Navigate to "Accessories" page
				18. Select above product
				19. Select above product's variant
			 */	
//			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//			// 2. Navigate to "Accessories" page
//			productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//			// Click Add product link
//			productControl.click(ProductModel.ADD_PRODUCT);
//			// Create new product
//			Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
//			productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
//			// 3. Select a product from product table
//			// 4. Click "Add New Variant" link in "Model Actions" module
//			productControl.click(ProductDetailModel.ADD_VARIANT);
//			// 5. Fill valid value into all required fields
//			// 6. Upload custom variant tuning file
//			// 7. Click "Save" link
//			Hashtable<String,String> dataVariant = TestData.variantData(true, false, false);
//			productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
//			/*
//			 * VP: Verify that the variant tuning action link is  "Request Tuning Review"
//			 */
//			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.REQUEST_TUNING_REVIEW),VariantInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
//			// 8. Click "Request Tuning Review"  link
//			productControl.click(VariantInfo.REQUEST_TUNING_REVIEW);
//			// 9. Log out DTS portal
//			productControl.logout();
//			// 10. Log into DTS portal as DTS admin
//			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			// 11. Navigate to "Accessories" page
//			productControl.click(PageHome.linkAccessories);
//			// 12. Select above accessory
//			productControl.selectAccessorybyName(dataProduct.get("name"));
//			// 13. Select above accessory's variant
//			productControl.selectAVariantbyName(dataVariant.get("name"));
//			// Select tuning rating
//			productControl.click(VariantInfo.EDIT_VARIANT);
//			productControl.selectOptionByName(AddVariant.TUNING_RATING, "A");
//			productControl.click(AddEditProductModel.SAVE_VARIANT);
//			// 14. Click "Approve Tuning" link
//			productControl.click(VariantInfo.APPROVE_TUNING);
			
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			Hashtable<String, String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
			Hashtable<String,String> dataVariant = TestData.variantData(true, false, false);
			productWf.addProductAndTunningAndMarketing(dataProduct, false);
			productWf.addVariantAndTunningAndMarketing(dataVariant, false);
			
			// 15. Log out DTS portal
			productControl.logout();
			// 16. Log into DTS portal as above partner user
			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
			// 17. Navigate to "Accessories" page
			productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
			// 18. Select above accessory
			productControl.selectAccessorybyName(dataProduct.get("name"));
			// 19. Select above accessory's variant
			productControl.selectAVariantbyName(dataVariant.get("name"));
			/*
			 * The variant tuning status is changed to "APPROVED" and its action link is "Download Certification Badges"
			 */
			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.APPROVED);
			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.DOWNLOAD_CERTIFICATE), VariantInfo.DOWNLOAD_CERT_BADGES_ACTION_STRING);
			// Delete product
			productControl.click(VariantInfo.PRODUCT_LINK);
			productControl.doDelete(ProductDetailModel.DELETE);
		}
	
		/*
		 * Verify that the variant tuning status is changed to "DECLINED" and there is no tuning action link displayed after the DTS  admin declines the Partner's variant tuning
		 */
		@Test
		public void TC040PTS_14() {
			productControl.addLog("ID : TC040PTS_14 : Verify that the variant tuning status is changed"
					+ " to 'DECLINED' and there is no tuning action link displayed after the DTS  admin"
					+ " declines the Partner's variant tuning");
			/*
				Pre-Condition: partner user has "Add and manage Products" rights. The tuning approval is approved.
				1. Log into DTS portal as a partner user
				2. Navigate to "Accessories" page
				3. Select an approved tuning product from Products table
				VP: Verify that the tuning approval status of selected product is approved
				4. Click "Add New Variant" link in "Model Actions" module
				5. Fill valid value into all required fields
				6. Upload custom variant tuning file
				7. Click "Save" link
				VP: Verify that the variant tuning action link is  "Request Tuning Review"
				8. Click  "Request Tuning Review" link
				9. Log out DTS portal
				10. Log into DTS portal as DTS admin
				11. Navigate to "Accessories" page
				12. Select above product
				13. Select above product's variant
				14. Click "Decline Tuning" link
				15. Log out DTS portal
				16. Log into DTS portal as above partner user
				17. Navigate to "Accessories" page
				18. Select above product
				19. Select above product's variant
			 */	
//			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//			// 2. Navigate to "Accessories" page
//			productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//			// Click Add product link
//			productControl.click(ProductModel.ADD_PRODUCT);
//			// Create new product
//			Hashtable<String, String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
//			productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
//			// 3. Select a product from product table
//			// 4. Click "Add New Variant" link in "Model Actions" module
//			productControl.click(ProductDetailModel.ADD_VARIANT);
//			// 5. Fill valid value into all required fields
//			// 6. Upload custom variant tuning file
//			// 7. Click "Save" link
//			Hashtable<String, String> dataVariant = TestData.variantData(true, false, false);
//			productControl.addVariant(AddEditProductModel.getVariantHash(), dataVariant);
//			/*
//			 * VP: Verify that the variant tuning action link is
//			 * "Request Tuning Review"
//			 */
//			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.REQUEST_TUNING_REVIEW),VariantInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
//			// 8. Click  "Request Tuning Review" link
//			productControl.click(VariantInfo.REQUEST_TUNING_REVIEW);
//			// 9. Log out DTS portal
//			productControl.logout();
//			// 10. Log into DTS portal as DTS admin
//			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			// 11. Navigate to "Accessories" page
//			productControl.click(PageHome.linkAccessories);
//			// 12. Select above product
//			productControl.selectAccessorybyName(dataProduct.get("name"));
//			// 13. Select above product's variant
//			productControl.selectAVariantbyName(dataVariant.get("name"));
//			// 14. Click "Decline Tuning" link
//			productControl.click(VariantInfo.DECLINE_TUNING);
			 
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			Hashtable<String, String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
			Hashtable<String,String> dataVariant = TestData.variantData(true, false, false);
			productWf.addProductAndTunningAndMarketing(dataProduct, false);
			// 15. Log out DTS portal
			productControl.logout();
			// 16. Log into DTS portal as above partner user
			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
			productControl.click(PageHome.linkAccessories);
			productControl.selectProductByName(dataProduct.get("name"));
			productWf.addNewVariant(dataVariant);
			//productWf.requestDTSTuning();
			productControl.click(ProductDetailModel.REQUEST_TUNING);
						
			productControl.logout();
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			productControl.click(PageHome.linkAccessories);
			productControl.selectProductByName(dataProduct.get("name"));
			productControl.selectVariantbyName(dataVariant.get("name"));
			productWf.declineTuning();
			/*
			 * Verify that The variant tuning status is changed to "DECLINED" and
			 * there is no tuning action displayed
			 */
			// the status should be DECLINED, not DTS_DECLINED
			Assert.assertTrue(productControl.getTextByXpath(VariantInfo.TUNING_STATUS).
					contains(ProductDetailModel.ProductStatus.DECLINED.getName()));
			Assert.assertFalse(productControl.isElementPresent(VariantInfo.PARTNER_ACTIONS_STEP_1));
			// Delete product
			productControl.click(VariantInfo.PRODUCT_LINK);
			productControl.doDelete(ProductDetailModel.DELETE);
		}
		
		/*
		 * Verify that the tuning status is changed to "REVOKED" when the DTS  admin revoke the Partner tuning
		 */
		@Test
		public void TC040PTS_15() {
			productControl.addLog("ID : TC040PTS_15 : Verify that the tuning status is "
					+ "changed to 'REVOKED' when the DTS  admin revoke the Partner tuning");
			
		   /*
		 	Pre-Condition: partner user has "Add and manage accessories" rights. The tuning approval is approved.
			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Select an approved tuning accessory from accessories table
			VP: Verify that the tuning approval status of selected accessory is approved
			4. Click "Add New Variant" link in "Model Actions" module
			5. Fill valid value into all required fields
			6. Upload custom variant tuning file
			7. Click "Save" link
			VP: Verify that the variant tuning action link is  "Request Tuning Review"
			8. Click  "Request Tuning Review" link
			9. Log out DTS portal
			10. Log into DTS portal as DTS admin
			11. Navigate to "Accessories" page
			12. Select above accessory from accessories table
			13. Select above accessory's variant
			14. Click "Approve Tuning" link
			VP: Verify that the variant tuning action link is changed to "Revoke Tuning"
			15. Click "Revoke Tuning" link
			16. Log out DTS portal
			17. Log into DTS portal as above partner user again
			18. Navigate to "Accessories" page
			19. Select above accessory
			20. Select above accessory's variant
		    */	
		
			/*
			 * Pre-condition: create an accessory that has tuning approval is approved
			 */
//			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//			// 2. Navigate to "Accessories" page
//			productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//			// 3. Click add accessory link
//			productControl.click(ProductModel.ADD_PRODUCT);
//			//4,5. Fill valid value into all required fields and Upload valid tuning file
//			Hashtable<String, String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//			productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//						
//			// Verify that when there is tuning is uploaded, the tuning status is 'UNSUBMITTED'.
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.PRODUCT_NAME), data.get("name"));
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_TUNING_REVIEW), ProductDetailModel.ProductActions.REQUEST_TUNING_REVIEW.getName());
//						
//			//7. Click "Request Tuning Review"  link
//			productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
//			//	8. Log out DTS portal
//			//	9. Log into DTS portal as DTS admin
//			productControl.logout();
//			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			//	10. Navigate to "Accessories" page
//			//	11. Select above accessory which its tuning is pending for DTS review
//			productControl.click(PageHome.linkAccessories);
//			productControl.selectAccessorybyName(data.get("name"));
//			productControl.click(ProductDetailModel.EDIT_MODE);
//			productControl.selectOptionByName(AddEditProductModel.TUNING_RATING,AddEditProductModel.TuningRatingOption.A.getName());
//			productControl.click(AddEditProductModel.SAVE);
//			//	12. Click "Approve Tuning" link
//			productControl.click(ProductDetailModel.APPROVE_TUNING);
//			//	13. Log out DTS portal
//			//	14. Log into DTS portal as above partner user
//			productControl.logout();
//			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//			//	15. Navigate to "Accessories" page
//			//	16. Select and open above accessory
//			productControl.click(PageHome.linkAccessories);
//			productControl.selectAccessorybyName(data.get("name"));
//			//Verify that the tuning status is changed to 'APPROVED' and tuning action link is 'Download Certification Badges' after the DTS  admin approves the Partner tuning.
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.APPROVED.getName());
//			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.DOWNLOAD_CERTIFICATE), ProductDetailModel.ProductActions.DOWNLOAD_CERT_BADGES.getName());
//			// click "Add new variant" link
//			productControl.click(ProductDetailModel.ADD_VARIANT);
//			Hashtable <String, String> datavariant=TestData.variantData(true, false, false);
//			productControl.addVariant(AddEditProductModel.getVariantHash(), datavariant);
//			//The variant tuning status is "Using parent model's" and there is no more action link in this section
//			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TITLE_NAME), datavariant.get("name"));
//			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.UNSUBMITTED);
//			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.REQUEST_TUNING_REVIEW),VariantInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
//			//8. Click "Request Tuning Review"  link
//			productControl.click(VariantInfo.REQUEST_TUNING_REVIEW);
//			//The variant tuning status is changed to "PENDING DTS REVIEW" and its action is "Cancel Request"
//			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.PENDING_DTS_APPROVAL);
//			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.CANCEL_REQUEST_TUNING),VariantInfo.TUNING_CANCEL_REQUEST_ACTION_STRING);
//			
//			//Log out DTS portal
//			productControl.logout();
//			//Log into DTS Portal as DTS user
//			//	10. Navigate to "Accessories" page
//			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			productControl.click(PageHome.linkAccessories);
//			//	11. Select above product from Products table
//			productControl.selectAccessorybyName(data.get("name"));
//			//	12. Select above product's variant
//			productControl.selectAVariantbyName(datavariant.get("name"));
//			// Change the tuning rating
//			productControl.click(VariantInfo.EDIT_VARIANT);
//			productControl.selectOptionByName(AddVariant.TUNING_RATING,AddVariant.tuningRatingOption[0] );
//			productControl.click(AddEditProductModel.SAVE_VARIANT);
//			//	13. Click "Approve Tuning" link
//			productControl.click(VariantInfo.APPROVE_TUNING);
			
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			Hashtable<String, String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
			Hashtable<String,String> dataVariant = TestData.variantData(true, false, false);
			productWf.addProductAndTunningAndMarketing(dataProduct, false);
//			15. Log out DTS portal
			productControl.logout();
			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//			17. Navigate to "Accessories" page
			productControl.click(PageHome.linkAccessories);
//			18. Select above product
//			19. Select above product's variant
			productControl.selectAccessorybyName(dataProduct.get("name"));
			productWf.addNewVariant(dataVariant);
			productControl.click(ProductDetailModel.REQUEST_TUNING);
			productControl.logout();
			
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			productControl.click(PageHome.linkAccessories);
			//	11. Select above product from Products table
			productControl.selectAccessorybyName(dataProduct.get("name"));
			//	12. Select above product's variant
			productControl.selectAVariantbyName(dataVariant.get("name"));
			// Change the tuning rating
			productControl.click(VariantInfo.EDIT_VARIANT);
			productControl.selectOptionByName(AddEditProductModel.TUNING_RATING, AddEditProductModel.TuningRatingOption.A.getName());
			productControl.click(AddEditProductModel.SAVE_VARIANT);
			//	13. Click "Approve Tuning" link
			productWf.approveTuning();
			
			// VP: Verify that the variant tuning action link is changed to "Revoke Tuning"
			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.REVOKE_TUNING), VariantInfo.REVOKE_TUNING_ACTION_STRING);
			//14. Click "Revoke Tuning" link
//			productControl.click(VariantInfo.REVOKE_TUNING);
			productWf.revokeTuning();
			
//			productControl.selectAVariantbyName(dataVariant.get("name"));			
			//The variant tuning status is changed to "REVOKED" and there is no action link displayed for Tuning Approval section
			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.REVOKED );
			Assert.assertFalse(productControl.isElementPresent(VariantInfo.PARTNER_ACTIONS_STEP_1));
			
			//Teardown: Delete product
			productControl.click(VariantInfo.PRODUCT_LINK);
			productControl.doDelete(ProductDetailModel.DELETE);
		}	
		
		/*
		 * Verify that the variant tuning status is changed to "UNSUBMITTED" and its action is "Request DTS Tuning" when partner user removes variant tuning file
		 */
		@Test
		public void TC040PTS_16() {
			productControl.addLog("ID : TC040PTS_16 : Verify that the variant tuning status is "
					+ "changed to 'UNSUBMITTED' and its action is 'Request DTS Tuning' when "
					+ "partner user removes variant tuning file");
			/*
				Pre-Condition: partner user has "Add and manage Products" rights. The variant tuning approval is approved.
				1. Log into DTS portal as a partner user
				2. Navigate to "Accessories" page
				3. Select an approved tuning product from Products table
				VP: Verify that the tuning approval status of selected product is approved
				4. Select above product's variant which tuning is approved
				5. Click ï¿½Editï¿½ link
				6. Delete the approved tuning file
				7. Click ï¿½Saveï¿½ link
			*/
			/*
			 * Pre-condition: Create a product variant which tuning is approved
			 */
//			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//			// Navigate to product page
//			productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
//			// Click Add product link
//			productControl.click(ProductModel.ADD_PRODUCT);
//			// Create product
//			Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, false, false, false);
//			productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
//			// Click Add New Variant link
//			productControl.click(ProductDetailModel.ADD_VARIANT);
//			// Create variant
//			Hashtable<String,String> dataVariant = TestData.variantData(true, false, false);
//			productControl.addVariant(PartnerAddEditProductModel.getVariantHash(), dataVariant);
//			// Click on Request Tuning Review link
//			productControl.click(VariantInfo.REQUEST_TUNING_REVIEW);
//			// Log out
//			productControl.logout();
//			// Log in DTS portal as DTS user
//			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			// Navigate to product page
//			productControl.click(PageHome.linkAccessories);
//			// Select product above
//			productControl.selectAccessorybyName(dataProduct.get("name"));
//			// Select product variant above
//			productControl.selectAVariantbyName(dataVariant.get("name"));
//			// Select tuning rating
//			productControl.click(VariantInfo.EDIT_VARIANT);
//			productControl.selectOptionByName(AddVariant.TUNING_RATING, "A");
//			productControl.click(AddEditProductModel.SAVE_VARIANT);
//			// Approve tuning
//			productControl.click(VariantInfo.APPROVE_TUNING);
//			// Log out
			
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			Hashtable<String, String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
			Hashtable<String,String> dataVariant = TestData.variantData(true, false, false);
			productWf.addProductAndTunningAndMarketing(dataProduct, false);
			productWf.addVariantAndTunningAndMarketing(dataVariant, false);
			
			productControl.logout();
			// Log into DTS portal as partner user above
			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
			/*
			 * *********************************************************************************
			 */
			// 2. Navigate to "Accessories" page
			productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
			// 3. Select an approved tuning product from Products table
			productControl.selectAccessorybyName(dataProduct.get("name"));
			// 4. Select above product's variant which tuning is approved
			productControl.selectAVariantbyName(dataVariant.get("name"));
			// 5. Click ï¿½Editï¿½ link
			productControl.click(VariantInfo.EDIT_VARIANT);
			// 6. Delete the approved tuning file
			productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
			// 7. Click ï¿½Saveï¿½ link
			productControl.click(AddEditProductModel.SAVE_VARIANT);
			/*
			 * Verify that The variant tuning status is changed to "UNSUBMITTED" and its action is "Request DTS Tuning"
			 */
			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.UNSUBMITTED);
			Assert.assertEquals(productControl.getTextByXpath(VariantInfo.REQUEST_DTS_TUNING), VariantInfo.REQUEST_DTS_TUNING_ACTION_STRING);
			// Delete product
			productControl.click(VariantInfo.PRODUCT_LINK);
			productControl.doDelete(ProductDetailModel.DELETE);
		}
		
		/*
		 * Verify that the partner user can request revised tuning after the DTS tuning is revoked
		 */
		@Test
		public void TC040PTS_17() {
			productControl.addLog("ID : TC040PTS_17 : Verify that the partner user can request "
					+ "revised tuning after the DTS tuning is revoked");
			
			/*
				1. Log into DTS portal as a DTS user
				2. Navigate to "Products" page
				3. Click ï¿½Add Product' link
				4. Fill valid value into all required fields
				5. Upload tuning file successfully
				6. Click ï¿½Saveï¿½ link
				VP: Verify that the status of Tuning Approval state is ï¿½PENDING PARTNER APPROVALï¿½
				7. Expand the ï¿½Partner Actionï¿½ link 
				8. Click ï¿½Approve Tuningï¿½ link
				VP: Verify that the status of Tuning Approval state is ï¿½APPROVEDï¿½ and the ï¿½Revoke Tuningï¿½ link is also displayed
				9. Click ï¿½Revoke Tuningï¿½ link
				VP: Verify that the status of Tuning Approval sate now is changed to ï¿½REVOKEDï¿½ and the ï¿½Request Revised Tuningï¿½ link is displayed below the ï¿½Partner Actionsï¿½ link
				10. Log out DTS portal
				11. Log into DTS portal as partner user
				12. Navigate to ï¿½Productsï¿½ page
				13. Select above product from Products table
				14. Verify that the status of Tuning Approval sate now is changed to ï¿½REVOKEDï¿½ and its action link is ï¿½Request Revised Tuningï¿½
				15. Click ï¿½Request Revised Tuningï¿½ link
				VP: Verify that the Request tuning form is displayed
				16. Tick to check on the term check box
				17. Click ï¿½Submitï¿½ link
				18. Click on the product name link in top page in order to return to product detail page
			*/
			// 1. Log into DTS portal as a DTS user
//			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
//			// 2. Navigate to "Products" page
//			productControl.click(PageHome.linkAccessories);
//			// 3. Click ï¿½Add Product' link
//			productControl.click(ProductModel.ADD_PRODUCT);
//			// 4. Fill valid value into all required fields
//			// 5. Upload tuning file successfully
//			// 6. Click ï¿½Saveï¿½ link
//			Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
//			productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
//			// 7. Expand the ï¿½Partner Actionï¿½ link 
//			productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
//			// 8. Click ï¿½Approve Tuningï¿½ link
//			productControl.click(ProductDetailModel.APPROVE_TUNING);
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			Hashtable<String, String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
			productWf.addProductAndTunningAndMarketing(dataProduct, false);
			// 9. Click ï¿½Revoke Tuningï¿½ link
			
			productWf.revokeTuning();
			// 10. Log out DTS portal
			productControl.logout();
			// 11. Log into DTS portal as partner user
			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
			// 12. Navigate to ï¿½Productsï¿½ page
			productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
			// 13. Select above product from Products table
			productControl.selectAccessorybyName(dataProduct.get("name"));
			// 14. Verify that the status of Tuning Approval state now is changed to ï¿½REVOKEDï¿½ and its action link is ï¿½Request Revised Tuningï¿½
			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.REVOKED.getName());
			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_REVISED_TUNING),ProductDetailModel.ProductStatus.TUNING_REQUEST_REVISED.getName());
			// 15. Click ï¿½Request Revised Tuningï¿½ link
			productControl.click(ProductDetailModel.REQUEST_REVISED_TUNING);
//			/*
//			 * VP: Verify that the Request tuning form is displayed
//			 */
//			Assert.assertEquals(productControl.existsElement(TuningRequestForm.APPROVE_TUNNING_FORM()), true);
//			// 16. Tick to check on the term check box
//			productControl.click(TuningRequestForm.AGREE);
//			// 17. Click ï¿½Submitï¿½ link
//			productControl.click(TuningRequestForm.SUBMIT);
//			// 18. Click on the product name link in top page in order to return to product detail page
//			productControl.click(TuningRequestForm.PRODUCT_LINK);
			/*
			 * Verify that the status of Tuning Approval is now changed to ï¿½DTS REQUEST PENDINGï¿½ and its action link is ï¿½Cancel Requestï¿½
			 */
			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.DTS_REQUEST_PENDING.getName());
			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.CANCEL_REQUEST_TUNING), ProductDetailModel.ProductActions.TUNING_CANCEL_REQUEST.getName());
			// Delete product
			productControl.doDelete(ProductDetailModel.DELETE);
		}
		
		/*
		 * Verify that the status of step 1: Tuning Approval is changed to ï¿½REMOVEDï¿½ after  the revoked DTS tuning file is removed.
		 */
		@Test
		public void TC040PTS_18() {
			productControl.addLog("ID : TC040PTS_18 : Verify that the status of step 1: Tuning Approval is changed to 'REMOVED' after  the revoked DTS tuning file is removed.");
			productControl.addErrorLog("PDPP-1072: 040D Tuning State: The 'UNSUBMITTED' instead of 'REMOVED' state is displayed after the revoked DTS tuning file is removed.");
			/*
				1. Log into DTS portal as a DTS user
				2. Navigate to "Products" page
				3. Click ï¿½Add Product' link
				4. Fill valid value into all required fields
				5. Upload tuning file successfully
				6. Click ï¿½Saveï¿½ link
				VP: Verify that the status of Tuning Approval state is ï¿½PENDING PARTNER APPROVALï¿½
				7. Expand the ï¿½Partner Actionï¿½ link 
				8. Click ï¿½Approve Tuningï¿½ link
				VP: Verify that the status of Tuning Approval state is ï¿½APPROVEDï¿½ and the ï¿½Revoke Tuningï¿½ link is also displayed
				9. Click ï¿½Revoke Tuningï¿½ link
				VP: Verify that the status of Tuning Approval sate now is changed to ï¿½REVOKEDï¿½ and the ï¿½Request Revised Tuningï¿½ link is displayed below the ï¿½Partner Actionsï¿½ link
				10. Log out DTS portal
				11. Log into DTS portal as partner user
				12. Navigate to ï¿½Productsï¿½ page
				13. Select above product from Products table
				14. Verify that the status of Tuning Approval sate now is changed to ï¿½REVOKEDï¿½ and its action link is ï¿½Request Revised Tuningï¿½
				15. Click ï¿½Editï¿½ link
				16. Delete the DTS tuning file
				17. Click ï¿½Saveï¿½ link
				VP: The status of Step 1: Tuning Approval is changed to ï¿½REMOVEDï¿½ and its action link is "Request Revised Tuning".
			*/
			// 1. Log into DTS portal as a DTS user
			loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
			// 2. Navigate to "Products" page
			productControl.click(PageHome.linkAccessories);
			// 3. Click ï¿½Add Product' link
			productControl.click(ProductModel.ADD_PRODUCT);
			// 4. Fill valid value into all required fields
			// 5. Upload tuning file successfully
			// 6. Click ï¿½Saveï¿½ link
			Hashtable<String,String> data = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, false, false);
			productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
			/*
			 * VP: Verify that the status of Tuning Approval state is ï¿½PENDING PARTNER APPROVALï¿½
			 */
			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), "PENDING PARTNER APPROVAL");
			// 7. Expand the ï¿½Partner Actionï¿½ link 
			productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
			// 8. Click ï¿½Approve Tuningï¿½ link
			//productControl.click(ProductDetailModel.APPROVE_TUNING);
			productWf.approveTuning();
			/*
			 * VP: Verify that the status of Tuning Approval state is ï¿½APPROVEDï¿½ and the ï¿½Revoke Tuningï¿½ link is also displayed
			 */
			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.APPROVED.getName());
			Assert.assertTrue(productControl.isElementPresent(ProductDetailModel.REVOKE_TUNING));
			// 9. Click ï¿½Revoke Tuningï¿½ link
			productWf.revokeTuning();
			// Expand the "Partner Action" link
			productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
			/*
			 * VP: Verify that the status of Tuning Approval sate now is changed to ï¿½REVOKEDï¿½ and the ï¿½Request Revised Tuningï¿½ link is displayed below the ï¿½Partner Actionsï¿½ link
			 */
			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.REVOKED.getName());
			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_REVISED_TUNING), ProductDetailModel.ProductStatus.TUNING_REQUEST_REVISED.getName());
			// 10. Log out DTS portal
			productControl.logout();
			// 11. Log into DTS portal as partner user
			loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
			// 12. Navigate to ï¿½Productsï¿½ page
			productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
			// 13. Select above product from Products table
			productControl.selectAccessorybyName(data.get("name"));
			// 14. Verify that the status of Tuning Approval state now is changed to ï¿½REVOKEDï¿½ and its action link is ï¿½Request Revised Tuningï¿½
			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS), ProductDetailModel.ProductStatus.REVOKED.getName());
			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_REVISED_TUNING), ProductDetailModel.ProductStatus.TUNING_REQUEST_REVISED.getName());
			// 15. Click ï¿½Editï¿½ link
			productControl.click(ProductDetailModel.EDIT_MODE);
			//16. Delete the DTS tuning file
			productControl.doDelete(AddEditProductModel.DELETE_UPLOADED_TUNING);
			// 17. Click ï¿½Saveï¿½ link
			productControl.click(AddEditProductModel.SAVE_PRODUCT);
			
			/*
			 * The status of Step 1: Tuning Approval is changed to ï¿½REMOVEDï¿½ and its action link is "Request Revised Tuning".
			 */
			//Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS),ProductDetailModel.ProductStatus.REMOVED.getName());
			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.TUNING_STATUS),ProductDetailModel.ProductStatus.UNSUBMITTED.getName());
			//Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_REVISED_TUNING), ProductDetailModel.ProductStatus.TUNING_REQUEST_REVISED.getName());
			Assert.assertEquals(productControl.getTextByXpath(ProductDetailModel.REQUEST_REVISED_TUNING), ProductDetailModel.ProductActions.REQUEST_DTS_TUNING.getName());
			// Delete product
			productControl.doDelete(ProductDetailModel.DELETE);
		}
		
		
}
