package com.dts.adminportal.partner.test;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AccessoryInfo;
import dts.com.adminportal.model.AccessoryMain;
import dts.com.adminportal.model.AddAccessory;
import dts.com.adminportal.model.AddVariant;
import dts.com.adminportal.model.AccessoryInfo;
import dts.com.adminportal.model.PartnerAddAccessory;
import dts.com.adminportal.model.PartnerAddVariant;
import dts.com.adminportal.model.VariantInfo;
import dts.com.adminportal.model.TuningRequestForm;
import dts.com.adminportal.model.VariantInfo;
import dts.com.adminportal.model.Xpath;

public class PartnerUserCatalog040PTuningState extends CreatePage{
	private HomeController home;
	String AccessoryName = "Accessory"+RandomStringUtils.randomNumeric(4);
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}
	@BeforeMethod
	public void loginBeforeTest() {
		home.login(superpartneruser, superpartnerpassword);
	}
	
	/*
	 Verify that when there is no tuning is uploaded, the tuning action link is "Request DTS Tuning and tuning status is "UNSUBMITTED".
    */
	@Test
	public void TC040PTS_01 (){
		result.addLog("ID : TC040PTS_01 : Verify that when there is no tuning is uploaded, the tuning action link is 'Request DTS Tuning and tuning status is 'UNSUBMITTED'.");
		/*Pre-condition: Partner user has "Request accessory tunings" rights.

			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Fill valid value into all required fields
			5. Do not upload tuning
			6. Click "Save" link
		*/
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		//3. Clcik "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		//4,5. Fill valid value into all required fields and Do not upload tuning
		//6. Click "Save" link
		Hashtable<String, String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
	
		/*
		 Verify that when there is no tuning is uploaded, the tuning status is 'UNSUBMITTED'.
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.UNSUBMITTED);
		
		//Teardown
		home.doDelete(AccessoryInfo.DELETE);
		
	}
	
	/*
	 Verify that when the partner uploads tuning file, the tuning action link is "Request Tuning Review" and tuning status is "UNSUBMITTED".
	 */
	@Test
	public void TC040PTS_02() {
		result.addLog("ID : TC040PTS_02 : Verify that when the partner uploads tuning file, the tuning action link is 'Request Tuning Review' and tuning status is 'UNSUBMITTED'.");
		/*
		 Pre-Condition: partner user has "Add and manage accessories" rights.

			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Fill valid value into all required fields
			5. Upload valid tuning file
			6. Click "Save" link
		*/
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		//3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		//4,5. Fill valid value into all required fields and Upload valid tuning file
		Hashtable<String, String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		
		// Verify that when there is tuning is uploaded, the tuning status is 'UNSUBMITTED'.
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.UNSUBMITTED);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_TUNING_REVIEW), AccessoryInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
		
		//Teardown
		home.doDelete(AccessoryInfo.DELETE);
	}
	/*
	 Verify that the Tuning status is changed to "PENDING DTS REVIEW" and tuning action is "Cancel Request" after partner user request tuning review
	 */
	@Test
	public void TC040PTS_03() {
		result.addLog("ID : TC040OTS_03 : Verify that the Tuning status is changed to 'PENDING DTS REVIEW' and tuning action is 'Cancel Request' after partner user request tuning review");
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
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		//4,5. Fill valid value into all required fields and Upload valid tuning file
		Hashtable<String, String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		
		// Verify that when there is tuning is uploaded, the tuning status is 'UNSUBMITTED'.
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.UNSUBMITTED);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_TUNING_REVIEW), AccessoryInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
		
		//7. Click "Request Tuning Review"  link
		home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		//VP: Verify that the Tuning status is changed to 'PENDING DTS REVIEW' and tuning action is 'Cancel Request' after partner user request tuning review
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.PENDING_DTS_APPROVAL);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.CANCEL_REQUEST_TUNING), AccessoryInfo.TUNING_CANCEL_REQUEST_ACTION_STRING);
	
		//Teardown
		home.doDelete(AccessoryInfo.DELETE);
	}
		/*
		 Verify that the tuning status is changed to "UNSUBMITTED" and tuning action is "Request Tuning Review" after canceling the request tuning review
		 */
		@Test
		public void TC040PTS_04() {
			result.addLog("ID : TC040OTS_04 : Verify that the tuning status is changed to 'UNSUBMITTED' and tuning action is 'Request Tuning Review' after canceling the request tuning review");
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
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		//4,5. Fill valid value into all required fields and Upload valid tuning file
		Hashtable<String, String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
					
		// Verify that when there is tuning is uploaded, the tuning status is 'UNSUBMITTED'.
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.UNSUBMITTED);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_TUNING_REVIEW), AccessoryInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
		
		//7. Click "Request Tuning Review"  link
		home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		//VP: Verify that the Tuning status is changed to 'PENDING DTS REVIEW' and tuning action is 'Cancel Request' after partner user request tuning review
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.PENDING_DTS_APPROVAL);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.CANCEL_REQUEST_TUNING), AccessoryInfo.TUNING_CANCEL_REQUEST_ACTION_STRING);
		//8. Click "Cancel Request" link
		home.click(AccessoryInfo.CANCEL_REQUEST_TUNING);
		
		//Verify that the tuning status is changed to "UNSUBMITTED" and tuning action is "Request Tuning Review" after canceling the request tuning review
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.UNSUBMITTED);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_REVISED_TUNING), AccessoryInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);	
	
		//Teardown
		home.doDelete(AccessoryInfo.DELETE);
		}
		
	/*
	 * Verify that the tuning status is changed to 'APPROVED' and tuning action link is 'Download Certification Badges' after the DTS  admin approves the Partner tuning.
	 */
	@Test
	public void TC040PTS_05() {
		result.addLog("ID : TC040PTS_05 : Verify that the tuning status is changed to 'APPROVED' and tuning action link is 'Download Certification Badges' after the DTS  admin approves the Partner tuning.");

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
			13. Log out DTS portal
			14. Log into DTS portal as above partner user
			15. Navigate to "Accessories" page
			16. Select and open above accessory
		 */
			
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click add accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		//4,5. Fill valid value into all required fields and Upload valid tuning file
		Hashtable<String, String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
					
		// Verify that when there is tuning is uploaded, the tuning status is 'UNSUBMITTED'.
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.UNSUBMITTED);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_TUNING_REVIEW), AccessoryInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
					
		//7. Click "Request Tuning Review"  link
		home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		//	8. Log out DTS portal
		//	9. Log into DTS portal as DTS admin
		home.logout();
		home.login(superUsername, superUserpassword);
		//	10. Navigate to "Accessories" page
		//	11. Select above accessory which its tuning is pending for DTS review
		home.click(Xpath.linkAccessories);
		home.selectAnaccessorybyName(data.get("name"));
		home.click(AccessoryInfo.EDIT_MODE);
		home.selectOptionByName(AddAccessory.TUNING_RATING,AddAccessory.tuningRatingOption[2]);
		home.click(AddAccessory.SAVE);
		//	12. Click "Approve Tuning" link
		home.click(AccessoryInfo.APPROVE_TUNING);
//		13. Log out DTS portal
//		14. Log into DTS portal as above partner user
		home.logout();
		home.login(superpartneruser, superpartnerpassword);
//		15. Navigate to "Accessories" page
//		16. Select and open above accessory
		home.click(Xpath.linkAccessories);
		home.selectAnaccessorybyName(data.get("name"));
		//Verify that the tuning status is changed to 'APPROVED' and tuning action link is 'Download Certification Badges' after the DTS  admin approves the Partner tuning.
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.APPROVED);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DOWNLOAD_CERTIFICATE), AccessoryInfo.DOWNLOAD_CERT_BADGES_ACTION_STRING);
		
		//Teardown
		// Delete accessory
		home.doDelete(AccessoryInfo.DELETE);
	}
		
	/*
	 * Verify that the variant tuning status is "Using parent model's" when adding new variant with the inherited tuning file from its parent.
	 */
	@Test
	public void TC040PTS_06() {
		result.addLog(" ID : TC040PTS_06 : Verify that the variant tuning status is 'Using parent model's' when adding new variant with the inherited tuning file from its parent.");
		
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
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields
		// 5. Upload valid tuning file
		// 6. Click "Save" link
		Hashtable<String, String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: Verify that the tuning action link is "Request Tuning Review"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_TUNING_REVIEW), AccessoryInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
		// 7. Click "Request Tuning Review" link
		home.click(AddAccessory.REQUEST_TUNING_REVIEW);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as DTS admin
		home.login(superUsername, superUserpassword);
		// 10. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 11. Select above accessory which its tuning is pending for DTS review
		home.selectAnaccessorybyName(data.get("name"));
		// Select tuning rating
		home.click(AccessoryInfo.EDIT_MODE);
		home.clickOptionByIndex(AddAccessory.TUNING_RATING, 2);
		home.click(AddAccessory.SAVE);
		// 12. Click "Decline Tuning" link
		home.click(AccessoryInfo.DECLINE_TUNING);
		// 13. Log out DTS portal
		home.logout();
		// 14. Log into DTS portal as above partner user
		home.login(superpartneruser, superpartnerpassword);
		// 15. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 16. Select and open above accessory
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * Verify The tuning status is changed to "DECLINED" and there is no tuning action displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "DTS DECLINED");
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.PARTNER_ACTIONS_STEP_1));
		// Delete accessory
		home.doDelete(AccessoryInfo.DELETE);
	}
		
		/*
		 * Verify that the tuning status is changed to "REVOKED" when the DTS  admin revoke the Partner tuning
		 */
		@Test
		public void TC040PTS_07() {
			result.addLog(" ID : TC040PTS_07 : Verify that the tuning status is changed to 'REVOKED' when the DTS  admin revoke the Partner tuning");
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
		
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields
		// 5. Upload valid tuning file
		// 6. Click "Save" link
		/*
		 * Init data
		 */
		Hashtable<String, String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: Verify that the tuning action link is "Request Tuning Review"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_TUNING_REVIEW), AccessoryInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
		// 7. Click "Request Tuning Review" link
		home.click(AccessoryInfo.REQUEST_TUNING);
		// 8. Log out DTS portal
		home.logout();
		// 9. Log into DTS portal as DTS admin
		home.login(superUsername, superUserpassword);
		// 10. Navigate to "Accessories" page
		home.click(Xpath.linkAccessories);
		// 11. Select above accessory which its tuning is pending for DTS review
		home.selectAnaccessorybyName(data.get("name"));
		// Select tuning rating
		home.click(AccessoryInfo.EDIT_MODE);
		home.clickOptionByIndex(AddAccessory.TUNING_RATING, 2);
		home.click(AddAccessory.SAVE);
		// 12. Click "Approve Tuning" link
		home.click(AccessoryInfo.APPROVE_TUNING);
		/*
		 * VP: Verify that the tuning action link is changed to "Revoke Tuning"
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.REVOKE_TUNING));
		// 13. Click "Revoke Tuning" link
		home.click(AccessoryInfo.REVOKE_TUNING);
		// 14. Log out DTS portal
		home.logout();
		// 15. Log into DTS portal as above partner user again
		home.login(partneruser, password);
		// 16. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 17. Select and open above accessory
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * The tuning status is changed to "REVOKED" and there is no action link displayed for Tuning Approval
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.REVOKED);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_TUNING), "");
	}	
			
		/*
		 Verify that the tuning status is changed to 'UNSUBMITTED' and its action is 'Request DTS Tuning' when partner user removes tuning file
		*/
		@Test
		public void TC040PTS_08() {
			result.addLog(" ID : TC040PTS_08 : Verify that the tuning status is changed to 'UNSUBMITTED' and its action is 'Request DTS Tuning' when partner user removes tuning file");
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
			// 2. Navigate to "Accessories" page
			home.click(Xpath.LINK_PARTNER_ACCESSORIES);
			// 3. Click add accessory link
			home.click(AccessoryMain.ADD_ACCESSORY);
			//4,5. Fill valid value into all required fields and Upload valid tuning file
			Hashtable<String, String> data = TestData.accessoryTuning();
			home.addAccessoriesPartner(AddAccessory.getHash(), data);
						
			// Verify that when there is tuning is uploaded, the tuning status is 'UNSUBMITTED'.
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.UNSUBMITTED);
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_TUNING_REVIEW), AccessoryInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
						
			//7. Click "Request Tuning Review"  link
			home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
			//	8. Log out DTS portal
			//	9. Log into DTS portal as DTS admin
			home.logout();
			home.login(superUsername, superUserpassword);
			//	10. Navigate to "Accessories" page
			//	11. Select above accessory which its tuning is pending for DTS review
			home.click(Xpath.linkAccessories);
			home.selectAnaccessorybyName(data.get("name"));
			home.click(AccessoryInfo.EDIT_MODE);
			home.selectOptionByName(AddAccessory.TUNING_RATING,AddAccessory.tuningRatingOption[2]);
			home.click(AddAccessory.SAVE);
			//	12. Click "Approve Tuning" link
			home.click(AccessoryInfo.APPROVE_TUNING);
//			13. Log out DTS portal
//			14. Log into DTS portal as above partner user
			home.logout();
			home.login(superpartneruser, superpartnerpassword);
//			15. Navigate to "Accessories" page
//			16. Select and open above accessory
			home.click(Xpath.linkAccessories);
			home.selectAnaccessorybyName(data.get("name"));
			//Verify that the tuning status is changed to 'APPROVED' and tuning action link is 'Download Certification Badges' after the DTS  admin approves the Partner tuning.
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.APPROVED);
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DOWNLOAD_CERTIFICATE), AccessoryInfo.DOWNLOAD_CERT_BADGES_ACTION_STRING);
			//Edit product
			home.click(AccessoryInfo.EDIT_MODE);
			//remove tuning
			home.doDelete(AddAccessory.DELETE_UPLOADED_TUNING);
			//Click Save
			home.click(AddAccessory.SAVE);
			
			//Verify that the tuning status is changed to 'UNSUBMITTED' and its action is 'Request DTS Tuning' when partner user removes tuning file
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.UNSUBMITTED);
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_TUNING), AccessoryInfo.REQUEST_DTS_TUNING_ACTION_STRING);
		}
		/*
		 Verify that the variant tuning status is "Using parent's model" when adding new variant with the inherited tuning file from its parent.
		*/
		@Test
		public void TC040PTS_09() {
			result.addLog("ID : TC040PTS_09: Verify that the variant tuning status is 'Using parent's model' when adding new variant with the inherited tuning file from its parent.");

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
			// 2. Navigate to "Accessories" page
			home.click(Xpath.LINK_PARTNER_ACCESSORIES);
			// 3. Click add accessory link
			home.click(AccessoryMain.ADD_ACCESSORY);
			//4,5. Fill valid value into all required fields and Upload valid tuning file
			Hashtable<String, String> data = TestData.accessoryTuning();
			home.addAccessoriesPartner(AddAccessory.getHash(), data);
						
			// Verify that when there is tuning is uploaded, the tuning status is 'UNSUBMITTED'.
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.UNSUBMITTED);
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_TUNING_REVIEW), AccessoryInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
						
			//7. Click "Request Tuning Review"  link
			home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
			//	8. Log out DTS portal
			//	9. Log into DTS portal as DTS admin
			home.logout();
			home.login(superUsername, superUserpassword);
			//	10. Navigate to "Accessories" page
			//	11. Select above accessory which its tuning is pending for DTS review
			home.click(Xpath.linkAccessories);
			home.selectAnaccessorybyName(data.get("name"));
			home.click(AccessoryInfo.EDIT_MODE);
			home.selectOptionByName(AddAccessory.TUNING_RATING,AddAccessory.tuningRatingOption[2]);
			home.click(AddAccessory.SAVE);
			//	12. Click "Approve Tuning" link
			home.click(AccessoryInfo.APPROVE_TUNING);
//						13. Log out DTS portal
//						14. Log into DTS portal as above partner user
			home.logout();
			home.login(superpartneruser, superpartnerpassword);
//						15. Navigate to "Accessories" page
//						16. Select and open above accessory
			home.click(Xpath.linkAccessories);
			home.selectAnaccessorybyName(data.get("name"));
			//Verify that the tuning status is changed to 'APPROVED' and tuning action link is 'Download Certification Badges' after the DTS  admin approves the Partner tuning.
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.APPROVED);
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DOWNLOAD_CERTIFICATE), AccessoryInfo.DOWNLOAD_CERT_BADGES_ACTION_STRING);
			// click "Add new variant" link
			home.click(AccessoryInfo.ADD_VARIANT);
			Hashtable <String, String> datavariant=TestData.variantTuningParent();
			home.addVariant(AddVariant.getHash(), datavariant);
			//The variant tuning status is "Using parent model's" and there is no more action link in this section
			Assert.assertEquals(home.getTextByXpath(VariantInfo.TITLE_NAME), datavariant.get("name"));
			Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.USING_PARENT_TUNING);
			Assert.assertFalse(home.isElementPresent(VariantInfo.PARTNER_ACTIONS_STEP_1));
			
			//Teardown: Delete product
			home.click(VariantInfo.PRODUCT_LINK);
			home.doDelete(AccessoryInfo.DELETE);
						
		}
		/*
		 Verify that when the partner uploads custom tuning file for variant, the tuning action link is "Request Tuning Review" and tuning status is "UNSUBMITTED".
		*/
		@Test
		public void TC040PTS_10() {
			result.addLog("ID : TC040PTS_10 : Verify that the variant tuning status is 'Using parent's model' when adding new variant with the inherited tuning file from its parent.");

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
			// 2. Navigate to "Accessories" page
			home.click(Xpath.LINK_PARTNER_ACCESSORIES);
			// 3. Click add accessory link
			home.click(AccessoryMain.ADD_ACCESSORY);
			//4,5. Fill valid value into all required fields and Upload valid tuning file
			Hashtable<String, String> data = TestData.accessoryTuning();
			home.addAccessoriesPartner(AddAccessory.getHash(), data);
						
			// Verify that when there is tuning is uploaded, the tuning status is 'UNSUBMITTED'.
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.UNSUBMITTED);
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_TUNING_REVIEW), AccessoryInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
						
			//7. Click "Request Tuning Review"  link
			home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
			//	8. Log out DTS portal
			//	9. Log into DTS portal as DTS admin
			home.logout();
			home.login(superUsername, superUserpassword);
			//	10. Navigate to "Accessories" page
			//	11. Select above accessory which its tuning is pending for DTS review
			home.click(Xpath.linkAccessories);
			home.selectAnaccessorybyName(data.get("name"));
			home.click(AccessoryInfo.EDIT_MODE);
			home.selectOptionByName(AddAccessory.TUNING_RATING,AddAccessory.tuningRatingOption[2]);
			home.click(AddAccessory.SAVE);
			//	12. Click "Approve Tuning" link
			home.click(AccessoryInfo.APPROVE_TUNING);
//									13. Log out DTS portal
//									14. Log into DTS portal as above partner user
			home.logout();
			home.login(superpartneruser, superpartnerpassword);
//									15. Navigate to "Accessories" page
//									16. Select and open above accessory
			home.click(Xpath.linkAccessories);
			home.selectAnaccessorybyName(data.get("name"));
			//Verify that the tuning status is changed to 'APPROVED' and tuning action link is 'Download Certification Badges' after the DTS  admin approves the Partner tuning.
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.APPROVED);
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DOWNLOAD_CERTIFICATE), AccessoryInfo.DOWNLOAD_CERT_BADGES_ACTION_STRING);
			// click "Add new variant" link
			home.click(AccessoryInfo.ADD_VARIANT);
			Hashtable <String, String> datavariant=TestData.variantTuning();
			home.addVariant(AddVariant.getHash(), datavariant);
			//The variant tuning status is "Using parent model's" and there is no more action link in this section
			Assert.assertEquals(home.getTextByXpath(VariantInfo.TITLE_NAME), datavariant.get("name"));
			Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.UNSUBMITTED);
			Assert.assertEquals(home.getTextByXpath(VariantInfo.REQUEST_DTS_TUNING),VariantInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
			
			//Teardown: Delete product
			home.click(VariantInfo.PRODUCT_LINK);
			home.doDelete(AccessoryInfo.DELETE);
		}
		/*
		 Verify that the variant tuning status is changed to "PENDING DTS REVIEW" and variant tuning action is "Cancel Request" after partner user request variant tuning review
		 */
		@Test
		public void TC040PTS_11() {
			result.addLog("ID : TC040PTS_11 : Verify that the variant tuning status is changed to 'PENDING DTS REVIEW' and variant tuning action is 'Cancel Request' after partner user request variant tuning review");

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
			// 2. Navigate to "Accessories" page
			home.click(Xpath.LINK_PARTNER_ACCESSORIES);
			// 3. Click add accessory link
			home.click(AccessoryMain.ADD_ACCESSORY);
			//4,5. Fill valid value into all required fields and Upload valid tuning file
			Hashtable<String, String> data = TestData.accessoryTuning();
			home.addAccessoriesPartner(AddAccessory.getHash(), data);
						
			// Verify that when there is tuning is uploaded, the tuning status is 'UNSUBMITTED'.
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.UNSUBMITTED);
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_TUNING_REVIEW), AccessoryInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
						
			//7. Click "Request Tuning Review"  link
			home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
			//	8. Log out DTS portal
			//	9. Log into DTS portal as DTS admin
			home.logout();
			home.login(superUsername, superUserpassword);
			//	10. Navigate to "Accessories" page
			//	11. Select above accessory which its tuning is pending for DTS review
			home.click(Xpath.linkAccessories);
			home.selectAnaccessorybyName(data.get("name"));
			home.click(AccessoryInfo.EDIT_MODE);
			home.selectOptionByName(AddAccessory.TUNING_RATING,AddAccessory.tuningRatingOption[2]);
			home.click(AddAccessory.SAVE);
			//	12. Click "Approve Tuning" link
			home.click(AccessoryInfo.APPROVE_TUNING);
//												13. Log out DTS portal
//												14. Log into DTS portal as above partner user
			home.logout();
			home.login(superpartneruser, superpartnerpassword);
//												15. Navigate to "Accessories" page
//												16. Select and open above accessory
			home.click(Xpath.linkAccessories);
			home.selectAnaccessorybyName(data.get("name"));
			//Verify that the tuning status is changed to 'APPROVED' and tuning action link is 'Download Certification Badges' after the DTS  admin approves the Partner tuning.
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.APPROVED);
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DOWNLOAD_CERTIFICATE), AccessoryInfo.DOWNLOAD_CERT_BADGES_ACTION_STRING);
			// click "Add new variant" link
			home.click(AccessoryInfo.ADD_VARIANT);
			Hashtable <String, String> datavariant=TestData.variantTuning();
			home.addVariant(AddVariant.getHash(), datavariant);
			//The variant tuning status is "Using parent model's" and there is no more action link in this section
			Assert.assertEquals(home.getTextByXpath(VariantInfo.TITLE_NAME), datavariant.get("name"));
			Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.UNSUBMITTED);
			Assert.assertEquals(home.getTextByXpath(VariantInfo.REQUEST_TUNING_REVIEW),VariantInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
			//8. Click "Request Tuning Review"  link
			home.click(VariantInfo.REQUEST_TUNING_REVIEW);
			//The variant tuning status is changed to "PENDING DTS REVIEW" and its action is "Cancel Request"
			Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.PENDING_DTS_APPROVAL);
			Assert.assertEquals(home.getTextByXpath(VariantInfo.CANCEL_REQUEST_TUNING),VariantInfo.TUNING_CANCEL_REQUEST_ACTION_STRING);
			
			//Teardown: Delete product
			home.click(VariantInfo.PRODUCT_LINK);
			home.doDelete(AccessoryInfo.DELETE);
		}
		/*
		 Verify that the variant tuning status is changed to "UNSUBMITTED" and its action is "Request Tuning Review" after canceling the request tuning review
		 */
		@Test
		public void TC040PTS_12() {
			result.addLog("ID : TC040PTS_12 : Verify that the variant tuning status is changed to 'UNSUBMITTED' and its action is 'Request Tuning Review' after canceling the request tuning review");

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
			// 2. Navigate to "Accessories" page
			home.click(Xpath.LINK_PARTNER_ACCESSORIES);
			// 3. Click add accessory link
			home.click(AccessoryMain.ADD_ACCESSORY);
			//4,5. Fill valid value into all required fields and Upload valid tuning file
			Hashtable<String, String> data = TestData.accessoryTuning();
			home.addAccessoriesPartner(AddAccessory.getHash(), data);
						
			// Verify that when there is tuning is uploaded, the tuning status is 'UNSUBMITTED'.
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.UNSUBMITTED);
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_TUNING_REVIEW), AccessoryInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
						
			//7. Click "Request Tuning Review"  link
			home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
			//	8. Log out DTS portal
			//	9. Log into DTS portal as DTS admin
			home.logout();
			home.login(superUsername, superUserpassword);
			//	10. Navigate to "Accessories" page
			//	11. Select above accessory which its tuning is pending for DTS review
			home.click(Xpath.linkAccessories);
			home.selectAnaccessorybyName(data.get("name"));
			home.click(AccessoryInfo.EDIT_MODE);
			home.selectOptionByName(AddAccessory.TUNING_RATING,AddAccessory.tuningRatingOption[2]);
			home.click(AddAccessory.SAVE);
			//	12. Click "Approve Tuning" link
			home.click(AccessoryInfo.APPROVE_TUNING);
//															13. Log out DTS portal
//															14. Log into DTS portal as above partner user
			home.logout();
			home.login(superpartneruser, superpartnerpassword);
//															15. Navigate to "Accessories" page
//															16. Select and open above accessory
			home.click(Xpath.linkAccessories);
			home.selectAnaccessorybyName(data.get("name"));
			//Verify that the tuning status is changed to 'APPROVED' and tuning action link is 'Download Certification Badges' after the DTS  admin approves the Partner tuning.
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.APPROVED);
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DOWNLOAD_CERTIFICATE), AccessoryInfo.DOWNLOAD_CERT_BADGES_ACTION_STRING);
			// click "Add new variant" link
			home.click(AccessoryInfo.ADD_VARIANT);
			Hashtable <String, String> datavariant=TestData.variantTuning();
			home.addVariant(AddVariant.getHash(), datavariant);
			//The variant tuning status is "Using parent model's" and there is no more action link in this section
			Assert.assertEquals(home.getTextByXpath(VariantInfo.TITLE_NAME), datavariant.get("name"));
			Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.UNSUBMITTED);
			Assert.assertEquals(home.getTextByXpath(VariantInfo.REQUEST_TUNING_REVIEW),VariantInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
			//8. Click "Request Tuning Review"  link
			home.click(VariantInfo.REQUEST_TUNING_REVIEW);
			//The variant tuning status is changed to "PENDING DTS REVIEW" and its action is "Cancel Request"
			Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.PENDING_DTS_APPROVAL);
			Assert.assertEquals(home.getTextByXpath(VariantInfo.CANCEL_REQUEST_TUNING),VariantInfo.TUNING_CANCEL_REQUEST_ACTION_STRING);
			
			//Click Click "Cancel Request" link
			home.click(VariantInfo.CANCEL_REQUEST_TUNING);
			//Variant tuning status is changed to "UNSUBMITTED" and tuning action is "Request Tuning Review"
			Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.UNSUBMITTED);
			Assert.assertEquals(home.getTextByXpath(VariantInfo.REQUEST_TUNING_REVIEW),VariantInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
			
			//Teardown: Delete product
			home.click(VariantInfo.PRODUCT_LINK);
			home.doDelete(AccessoryInfo.DELETE);
		}
		
		/*
		 * Verify that the variant tuning status is changed to "APPROVED" and its action link is "Download Certification Badges"  after the DTS  admin approves the Partner tuning
		 */
		@Test
		public void TC040PTS_13() {
			result.addLog("ID : TC040PTS_13 : Verify that the variant tuning status is changed to 'APPROVED' and its action link is 'Download Certification Badges'  after the DTS  admin approves the Partner tuning");
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
			// 2. Navigate to "Accessories" page
			home.click(Xpath.LINK_PARTNER_ACCESSORIES);
			// Click Add product link
			home.click(AccessoryMain.ADD_ACCESSORY);
			// Create new product
			Hashtable<String,String> dataProduct = TestData.accessoryDraft();
			home.addAccessoriesPartner(PartnerAddAccessory.getHash(), dataProduct);
			// 3. Select a product from product table
			// 4. Click "Add New Variant" link in "Model Actions" module
			home.click(AccessoryInfo.ADD_VARIANT);
			// 5. Fill valid value into all required fields
			// 6. Upload custom variant tuning file
			// 7. Click "Save" link
			Hashtable<String,String> dataVariant = TestData.variantTuning();
			home.addVariant(AddVariant.getHash(), dataVariant);
			/*
			 * VP: Verify that the variant tuning action link is  "Request Tuning Review"
			 */
			Assert.assertEquals(home.getTextByXpath(VariantInfo.REQUEST_TUNING_REVIEW),VariantInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
			// 8. Click "Request Tuning Review"  link
			home.click(VariantInfo.REQUEST_TUNING_REVIEW);
			// 9. Log out DTS portal
			home.logout();
			// 10. Log into DTS portal as DTS admin
			home.login(superUsername, superUserpassword);
			// 11. Navigate to "Accessories" page
			home.click(Xpath.linkAccessories);
			// 12. Select above accessory
			home.selectAnaccessorybyName(dataProduct.get("name"));
			// 13. Select above accessory's variant
			home.selectAVariantbyName(dataVariant.get("name"));
			// Select tuning rating
			home.click(VariantInfo.EDIT_VARIANT);
			home.selectOptionByName(AddVariant.TUNING_RATING, "A");
			home.click(AddVariant.SAVE);
			// 14. Click "Approve Tuning" link
			home.click(VariantInfo.APPROVE_TUNING);
			// 15. Log out DTS portal
			home.logout();
			// 16. Log into DTS portal as above partner user
			home.login(superpartneruser, superpartnerpassword);
			// 17. Navigate to "Accessories" page
			home.click(Xpath.LINK_PARTNER_ACCESSORIES);
			// 18. Select above accessory
			home.selectAnaccessorybyName(dataProduct.get("name"));
			// 19. Select above accessory's variant
			home.selectAVariantbyName(dataVariant.get("name"));
			/*
			 * The variant tuning status is changed to "APPROVED" and its action link is "Download Certification Badges"
			 */
			Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.APPROVED);
			Assert.assertEquals(home.getTextByXpath(VariantInfo.DOWNLOAD_CERTIFICATE), VariantInfo.DOWNLOAD_CERT_BADGES_ACTION_STRING);
			// Delete product
			home.click(VariantInfo.PRODUCT_LINK);
			home.doDelete(AccessoryInfo.DELETE);
		}
	
		/*
		 * Verify that the variant tuning status is changed to "DECLINED" and there is no tuning action link displayed after the DTS  admin declines the Partner's variant tuning
		 */
		@Test
		public void TC040PTS_14() {
			result.addLog("ID : TC040PTS_14 : Verify that the variant tuning status is changed to 'DECLINED' and there is no tuning action link displayed after the DTS  admin declines the Partner's variant tuning");
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
			// 2. Navigate to "Accessories" page
			home.click(Xpath.LINK_PARTNER_ACCESSORIES);
			// Click Add product link
			home.click(AccessoryMain.ADD_ACCESSORY);
			// Create new product
			Hashtable<String, String> dataProduct = TestData.accessoryDraft();
			home.addAccessoriesPartner(PartnerAddAccessory.getHash(), dataProduct);
			// 3. Select a product from product table
			// 4. Click "Add New Variant" link in "Model Actions" module
			home.click(AccessoryInfo.ADD_VARIANT);
			// 5. Fill valid value into all required fields
			// 6. Upload custom variant tuning file
			// 7. Click "Save" link
			Hashtable<String, String> dataVariant = TestData.variantTuning();
			home.addVariant(AddVariant.getHash(), dataVariant);
			/*
			 * VP: Verify that the variant tuning action link is
			 * "Request Tuning Review"
			 */
			Assert.assertEquals(home.getTextByXpath(VariantInfo.REQUEST_TUNING_REVIEW),VariantInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
			// 8. Click  "Request Tuning Review" link
			home.click(VariantInfo.REQUEST_TUNING_REVIEW);
			// 9. Log out DTS portal
			home.logout();
			// 10. Log into DTS portal as DTS admin
			home.login(superUsername, superUserpassword);
			// 11. Navigate to "Accessories" page
			home.click(Xpath.linkAccessories);
			// 12. Select above product
			home.selectAnaccessorybyName(dataProduct.get("name"));
			// 13. Select above product's variant
			home.selectAVariantbyName(dataVariant.get("name"));
			// 14. Click "Decline Tuning" link
			home.click(VariantInfo.DECLINE_TUNING);
			// 15. Log out DTS portal
			home.logout();
			// 16. Log into DTS portal as above partner user
			home.login(superpartneruser, superpartnerpassword);
			// 17. Navigate to "Accessories" page
			home.click(Xpath.LINK_PARTNER_ACCESSORIES);
			// 18. Select above product
			home.selectAnaccessorybyName(dataProduct.get("name"));
			// 19. Select above product's variant
			home.selectAVariantbyName(dataVariant.get("name"));
			/*
			 * Verify that The variant tuning status is changed to "DECLINED" and
			 * there is no tuning action displayed
			 */
			Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS),VariantInfo.DECLINED);
			Assert.assertFalse(home.isElementPresent(VariantInfo.PARTNER_ACTIONS_STEP_1));
			// Delete product
			home.click(VariantInfo.PRODUCT_LINK);
			home.doDelete(AccessoryInfo.DELETE);
		}
		
		/*
		 * Verify that the tuning status is changed to "REVOKED" when the DTS  admin revoke the Partner tuning
		 */
		@Test
		public void TC040PTS_15() {
			result.addLog("ID : TC040PTS_15 : Verify that the tuning status is changed to 'REVOKED' when the DTS  admin revoke the Partner tuning");
			
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
			// 2. Navigate to "Accessories" page
			home.click(Xpath.LINK_PARTNER_ACCESSORIES);
			// 3. Click add accessory link
			home.click(AccessoryMain.ADD_ACCESSORY);
			//4,5. Fill valid value into all required fields and Upload valid tuning file
			Hashtable<String, String> data = TestData.accessoryTuning();
			home.addAccessoriesPartner(AddAccessory.getHash(), data);
						
			// Verify that when there is tuning is uploaded, the tuning status is 'UNSUBMITTED'.
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DISPLAY_NAME), data.get("name"));
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.UNSUBMITTED);
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_TUNING_REVIEW), AccessoryInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
						
			//7. Click "Request Tuning Review"  link
			home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
			//	8. Log out DTS portal
			//	9. Log into DTS portal as DTS admin
			home.logout();
			home.login(superUsername, superUserpassword);
			//	10. Navigate to "Accessories" page
			//	11. Select above accessory which its tuning is pending for DTS review
			home.click(Xpath.linkAccessories);
			home.selectAnaccessorybyName(data.get("name"));
			home.click(AccessoryInfo.EDIT_MODE);
			home.selectOptionByName(AddAccessory.TUNING_RATING,AddAccessory.tuningRatingOption[2]);
			home.click(AddAccessory.SAVE);
			//	12. Click "Approve Tuning" link
			home.click(AccessoryInfo.APPROVE_TUNING);
			//	13. Log out DTS portal
			//	14. Log into DTS portal as above partner user
			home.logout();
			home.login(superpartneruser, superpartnerpassword);
			//	15. Navigate to "Accessories" page
			//	16. Select and open above accessory
			home.click(Xpath.linkAccessories);
			home.selectAnaccessorybyName(data.get("name"));
			//Verify that the tuning status is changed to 'APPROVED' and tuning action link is 'Download Certification Badges' after the DTS  admin approves the Partner tuning.
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.APPROVED);
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.DOWNLOAD_CERTIFICATE), AccessoryInfo.DOWNLOAD_CERT_BADGES_ACTION_STRING);
			// click "Add new variant" link
			home.click(AccessoryInfo.ADD_VARIANT);
			Hashtable <String, String> datavariant=TestData.variantTuning();
			home.addVariant(AddVariant.getHash(), datavariant);
			//The variant tuning status is "Using parent model's" and there is no more action link in this section
			Assert.assertEquals(home.getTextByXpath(VariantInfo.TITLE_NAME), datavariant.get("name"));
			Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.UNSUBMITTED);
			Assert.assertEquals(home.getTextByXpath(VariantInfo.REQUEST_TUNING_REVIEW),VariantInfo.REQUEST_TUNING_REVIEW_ACTION_STRING);
			//8. Click "Request Tuning Review"  link
			home.click(VariantInfo.REQUEST_TUNING_REVIEW);
			//The variant tuning status is changed to "PENDING DTS REVIEW" and its action is "Cancel Request"
			Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.PENDING_DTS_APPROVAL);
			Assert.assertEquals(home.getTextByXpath(VariantInfo.CANCEL_REQUEST_TUNING),VariantInfo.TUNING_CANCEL_REQUEST_ACTION_STRING);
			
			//Log out DTS portal
			home.logout();
			//Log into DTS Portal as DTS user
			//	10. Navigate to "Accessories" page
			home.login(superUsername, superUserpassword);
			home.click(Xpath.linkAccessories);
			//	11. Select above product from Products table
			home.selectAnaccessorybyName(data.get("name"));
			//	12. Select above product's variant
			home.selectAVariantbyName(datavariant.get("name"));
			// Change the tuning rating
			home.click(VariantInfo.EDIT_VARIANT);
			home.selectOptionByName(AddVariant.TUNING_RATING,AddVariant.tuningRatingOption[0] );
			home.click(AddVariant.SAVE);
			//	13. Click "Approve Tuning" link
			home.click(VariantInfo.APPROVE_TUNING);
			// VP: Verify that the variant tuning action link is changed to "Revoke Tuning"
			Assert.assertEquals(home.getTextByXpath(VariantInfo.REVOKE_TUNING), VariantInfo.REVOKE_TUNING_ACTION_STRING);
			//14. Click "Revoke Tuning" link
			home.click(VariantInfo.REVOKE_TUNING);
//			15. Log out DTS portal
//			16. Log into DTS portal as above partner user again
			home.logout();
			home.login(superpartneruser, superpartnerpassword);
//			17. Navigate to "Accessories" page
			home.click(Xpath.linkAccessories);
//			18. Select above product
//			19. Select above product's variant
			home.selectAnaccessorybyName(data.get("name"));
			home.selectAVariantbyName(datavariant.get("name"));			
			//The variant tuning status is changed to "REVOKED" and there is no action link displayed for Tuning Approval section
			Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.REVOKED );
			Assert.assertFalse(home.isElementPresent(VariantInfo.PARTNER_ACTIONS_STEP_1));
			
			//Teardown: Delete product
			home.click(VariantInfo.PRODUCT_LINK);
			home.doDelete(AccessoryInfo.DELETE);
		}	
		
		/*
		 * Verify that the variant tuning status is changed to "UNSUBMITTED" and its action is "Request DTS Tuning" when partner user removes variant tuning file
		 */
		@Test
		public void TC040PTS_16() {
			result.addLog("ID : TC040PTS_16 : Verify that the variant tuning status is changed to 'UNSUBMITTED' and its action is 'Request DTS Tuning' when partner user removes variant tuning file");
			/*
				Pre-Condition: partner user has "Add and manage Products" rights. The variant tuning approval is approved.
				1. Log into DTS portal as a partner user
				2. Navigate to "Accessories" page
				3. Select an approved tuning product from Products table
				VP: Verify that the tuning approval status of selected product is approved
				4. Select above product's variant which tuning is approved
				5. Click “Edit” link
				6. Delete the approved tuning file
				7. Click “Save” link
			*/
			/*
			 * Pre-condition: Create a product variant which tuning is approved
			 */
			// Navigate to product page
			home.click(Xpath.LINK_PARTNER_ACCESSORIES);
			// Click Add product link
			home.click(AccessoryMain.ADD_ACCESSORY);
			// Create product
			Hashtable<String,String> dataProduct = TestData.accessoryDraft();
			home.addAccessoriesPartner(PartnerAddAccessory.getHash(), dataProduct);
			// Click Add New Variant link
			home.click(AccessoryInfo.ADD_VARIANT);
			// Create variant
			Hashtable<String,String> dataVariant = TestData.variantTuning();
			home.addVariant(PartnerAddVariant.getHash(), dataVariant);
			// Click on Request Tuning Review link
			home.click(VariantInfo.REQUEST_TUNING_REVIEW);
			// Log out
			home.logout();
			// Log in DTS portal as DTS user
			home.login(superUsername, superUserpassword);
			// Navigate to product page
			home.click(Xpath.linkAccessories);
			// Select product above
			home.selectAnaccessorybyName(dataProduct.get("name"));
			// Select product variant above
			home.selectAVariantbyName(dataVariant.get("name"));
			// Select tuning rating
			home.click(VariantInfo.EDIT_VARIANT);
			home.selectOptionByName(AddVariant.TUNING_RATING, "A");
			home.click(AddVariant.SAVE);
			// Approve tuning
			home.click(VariantInfo.APPROVE_TUNING);
			// Log out
			home.logout();
			// Log into DTS portal as partner user above
			home.login(superpartneruser, superpartnerpassword);
			/*
			 * *********************************************************************************
			 */
			// 2. Navigate to "Accessories" page
			home.click(Xpath.LINK_PARTNER_ACCESSORIES);
			// 3. Select an approved tuning product from Products table
			home.selectAnaccessorybyName(dataProduct.get("name"));
			// 4. Select above product's variant which tuning is approved
			home.selectAVariantbyName(dataVariant.get("name"));
			// 5. Click “Edit” link
			home.click(VariantInfo.EDIT_VARIANT);
			// 6. Delete the approved tuning file
			home.doDelete(PartnerAddVariant.DELETE_TUNING);
			// 7. Click “Save” link
			home.click(PartnerAddVariant.SAVE);
			/*
			 * Verify that The variant tuning status is changed to "UNSUBMITTED" and its action is "Request DTS Tuning"
			 */
			Assert.assertEquals(home.getTextByXpath(VariantInfo.TUNING_STATUS), VariantInfo.UNSUBMITTED);
			Assert.assertEquals(home.getTextByXpath(VariantInfo.REQUEST_DTS_TUNING), VariantInfo.REQUEST_DTS_TUNING_ACTION_STRING);
			// Delete product
			home.click(VariantInfo.PRODUCT_LINK);
			home.doDelete(AccessoryInfo.DELETE);
		}
		
		/*
		 * Verify that the partner user can request revised tuning after the DTS tuning is revoked
		 */
		@Test
		public void TC040PTS_17() {
			result.addLog("ID : TC040PTS_17 : Verify that the partner user can request revised tuning after the DTS tuning is revoked");
			
			/*
				1. Log into DTS portal as a DTS user
				2. Navigate to "Products" page
				3. Click “Add Product' link
				4. Fill valid value into all required fields
				5. Upload tuning file successfully
				6. Click “Save” link
				7. Expand the “Partner Action” link 
				8. Click “Approve Tuning” link
				9. Click “Revoke Tuning” link
				10. Log out DTS portal
				11. Log into DTS portal as partner user
				12. Navigate to “Products” page
				13. Select above product from Products table
				14. Verify that the status of Tuning Approval sate now is changed to “REVOKED” and its action link is “Request Revised Tuning”
				15. Click “Request Revised Tuning” link
				VP: Verify that the Request tuning form is displayed
				16. Tick to check on the term check box
				17. Click “Submit” link
				18. Click on the product name link in top page in order to return to product detail page
			*/
			// Log out
			home.logout();
			// 1. Log into DTS portal as a DTS user
			home.login(superUsername, superUserpassword);
			// 2. Navigate to "Products" page
			home.click(Xpath.linkAccessories);
			// 3. Click “Add Product' link
			home.click(AccessoryMain.ADD_ACCESSORY);
			// 4. Fill valid value into all required fields
			// 5. Upload tuning file successfully
			// 6. Click “Save” link
			Hashtable<String,String> data = TestData.accessoryTuning();
			home.addAccessoriesPartner(AddAccessory.getHash(), data);
			// 7. Expand the “Partner Action” link 
			home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
			// 8. Click “Approve Tuning” link
			home.click(AccessoryInfo.APPROVE_TUNING);
			// 9. Click “Revoke Tuning” link
			home.click(AccessoryInfo.REVOKE_TUNING);
			// 10. Log out DTS portal
			home.logout();
			// 11. Log into DTS portal as partner user
			home.login(superpartneruser, superpartnerpassword);
			// 12. Navigate to “Products” page
			home.click(Xpath.LINK_PARTNER_ACCESSORIES);
			// 13. Select above product from Products table
			home.selectAnaccessorybyName(data.get("name"));
			// 14. Verify that the status of Tuning Approval state now is changed to “REVOKED” and its action link is “Request Revised Tuning”
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.REVOKED);
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_REVISED_TUNING),AccessoryInfo.TUNING_REQUEST_REVISED);
			// 15. Click “Request Revised Tuning” link
			home.click(AccessoryInfo.REQUEST_REVISED_TUNING);
			/*
			 * VP: Verify that the Request tuning form is displayed
			 */
			Assert.assertEquals(home.existsElement(TuningRequestForm.APPROVE_TUNNING_FORM()).getResult(), "Pass");
			// 16. Tick to check on the term check box
			home.click(TuningRequestForm.AGREE);
			// 17. Click “Submit” link
			home.click(TuningRequestForm.SUBMIT);
			// 18. Click on the product name link in top page in order to return to product detail page
			home.click(TuningRequestForm.PRODUCT_LINK);
			/*
			 * Verify that the status of Tuning Approval is now changed to “DTS REQUEST PENDING” and its action link is “Cancel Request”
			 */
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.DTS_REQUEST_PENDING);
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.CANCEL_REQUEST_TUNING), AccessoryInfo.TUNING_CANCEL_REQUEST_ACTION_STRING);
			// Delete product
			home.doDelete(AccessoryInfo.DELETE);
		}
		
		/*
		 * Verify that the status of step 1: Tuning Approval is changed to “REMOVED” after  the revoked DTS tuning file is removed.
		 */
		@Test
		public void TC040PTS_18() {
			result.addLog("ID : TC040PTS_18 : Verify that the status of step 1: Tuning Approval is changed to 'REMOVED' after  the revoked DTS tuning file is removed.");
			result.addErrorLog("PDPP-1072: 040D Tuning State: The 'UNSUBMITTED' instead of 'REMOVED' state is displayed after the revoked DTS tuning file is removed.");
			/*
				1. Log into DTS portal as a DTS user
				2. Navigate to "Products" page
				3. Click “Add Product' link
				4. Fill valid value into all required fields
				5. Upload tuning file successfully
				6. Click “Save” link
				VP: Verify that the status of Tuning Approval state is “PENDING PARTNER APPROVAL”
				7. Expand the “Partner Action” link 
				8. Click “Approve Tuning” link
				VP: Verify that the status of Tuning Approval state is “APPROVED” and the “Revoke Tuning” link is also displayed
				9. Click “Revoke Tuning” link
				VP: Verify that the status of Tuning Approval sate now is changed to “REVOKED” and the “Request Revised Tuning” link is displayed below the “Partner Actions” link
				10. Log out DTS portal
				11. Log into DTS portal as partner user
				12. Navigate to “Products” page
				13. Select above product from Products table
				14. Verify that the status of Tuning Approval sate now is changed to “REVOKED” and its action link is “Request Revised Tuning”
				15. Click “Edit” link
				16. Delete the DTS tuning file
				17. Click “Save” link
				VP: The status of Step 1: Tuning Approval is changed to “REMOVED” and its action link is "Request Revised Tuning".
			*/
			// Log out
			home.logout();
			// 1. Log into DTS portal as a DTS user
			home.login(superUsername, superUserpassword);
			// 2. Navigate to "Products" page
			home.click(Xpath.linkAccessories);
			// 3. Click “Add Product' link
			home.click(AccessoryMain.ADD_ACCESSORY);
			// 4. Fill valid value into all required fields
			// 5. Upload tuning file successfully
			// 6. Click “Save” link
			Hashtable<String,String> data = TestData.accessoryTuning();
			home.addAccessoriesPartner(AddAccessory.getHash(), data);
			/*
			 * VP: Verify that the status of Tuning Approval state is “PENDING PARTNER APPROVAL”
			 */
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), "PENDING PARTNER APPROVAL");
			// 7. Expand the “Partner Action” link 
			home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
			// 8. Click “Approve Tuning” link
			home.click(AccessoryInfo.APPROVE_TUNING);
			/*
			 * VP: Verify that the status of Tuning Approval state is “APPROVED” and the “Revoke Tuning” link is also displayed
			 */
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.APPROVED);
			Assert.assertTrue(home.isElementPresent(AccessoryInfo.REVOKE_TUNING));
			// 9. Click “Revoke Tuning” link
			home.click(AccessoryInfo.REVOKE_TUNING);
			// Expand the "Partner Action" link
			home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
			/*
			 * VP: Verify that the status of Tuning Approval sate now is changed to “REVOKED” and the “Request Revised Tuning” link is displayed below the “Partner Actions” link
			 */
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.REVOKED);
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_REVISED_TUNING), AccessoryInfo.TUNING_REQUEST_REVISED);
			// 10. Log out DTS portal
			home.logout();
			// 11. Log into DTS portal as partner user
			home.login(superpartneruser, superpartnerpassword);
			// 12. Navigate to “Products” page
			home.click(Xpath.LINK_PARTNER_ACCESSORIES);
			// 13. Select above product from Products table
			home.selectAnaccessorybyName(data.get("name"));
			// 14. Verify that the status of Tuning Approval state now is changed to “REVOKED” and its action link is “Request Revised Tuning”
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.REVOKED);
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_REVISED_TUNING), AccessoryInfo.TUNING_REQUEST_REVISED);
			// 15. Click “Edit” link
			home.click(AccessoryInfo.EDIT_MODE);
			//16. Delete the DTS tuning file
			home.doDelete(AddAccessory.DELETE_UPLOADED_TUNING);
			// 17. Click “Save” link
			home.click(AddAccessory.SAVE);
			
			/*
			 * The status of Step 1: Tuning Approval is changed to “REMOVED” and its action link is "Request Revised Tuning".
			 */
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS),AccessoryInfo.REMOVED);
			Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_REVISED_TUNING), AccessoryInfo.TUNING_REQUEST_REVISED);
			// Delete product
			home.doDelete(AccessoryInfo.DELETE);
		}
		
		
}
