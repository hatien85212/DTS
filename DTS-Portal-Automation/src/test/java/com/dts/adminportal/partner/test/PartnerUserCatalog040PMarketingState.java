package com.dts.adminportal.partner.test;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AccessoryInfo;
import dts.com.adminportal.model.AccessoryMain;
import dts.com.adminportal.model.AddAccessory;
import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.DTSHome;
import dts.com.adminportal.model.AccessoryInfo;
import dts.com.adminportal.model.PartnerAddAccessory;
import dts.com.adminportal.model.PartnerHomePage;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.Xpath;

public class PartnerUserCatalog040PMarketingState extends CreatePage {
	private HomeController home;
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}
	
	/*
	 Verify that the Marketing status is changed from "CLOSED" to "UNSUBMITTED" when the tuning status is "APPROVED"
	*/
	@Test
	public void TC040PMS_01() {
		result.addLog("ID : 040PMS_01 : Verify that the Marketing status is changed from 'CLOSED' to 'UNSUBMITTED' when the tuning status is 'APPROVED' ");
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
		home.login(superpartneruser, superpartnerpassword);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields
		// 5. Upload valid tuning file
		// 6. Click "Save" link
		Hashtable<String, String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*Verify that the tuning action link is "Request Tuning Review"*/
		Assert.assertNotNull(home.isElementPresent(AccessoryInfo.REQUEST_TUNING), "requestTuningAction is exist");
		//7. Click "Request Tuning Review"  link
		home.click(AddAccessory.REQUEST_TUNING_REVIEW);
		//8. Log out DTS portal
		home.logout();
		//9. Log into DTS portal as DTS admin
		home.login(superUsername,superUserpassword);
		//10. Navigate to "Accessories" page
		home.click(DTSHome.LINK_ACCESSORIES);
		//11. Select above accessory which its tuning is pending for DTS review
		home.selectAnaccessorybyName(data.get("name"));
		//Change the tuning rating
		home.click(AccessoryInfo.EDIT_MODE);
		home.selectOptionByName(AddAccessory.TUNING_RATING, AddAccessory.tuningRatingOption[2]);
		home.click(AddAccessory.SAVE);
		//12. Click "Approve Tuning" link
		home.click(AccessoryInfo.APPROVE_TUNING);	
		//13. Log out DTS portal
		home.logout();
		//14. Log into DTS portal as above partner user
		home.login(partneruser, password);
		//15. Navigate to "Accessories" page
		home.click(PartnerHomePage.LINK_PARTNER_ACCESSORIES);
		//16. Select and open above accessory
		home.selectAnaccessorybyName(data.get("name"));
		// Verify The  tuning status is changed to "APPROVED" and the marketing status is changed to "UNSUBMITTED"
		String tuningStatusChanged = home.getTextByXpath(Xpath.TUNING_TITLE);
		Assert.assertEquals(tuningStatusChanged, AccessoryInfo.APPROVED);
		Assert.assertEquals(home.getTextByXpath(Xpath.MARKETING_TITLE), AccessoryInfo.UNSUBMITTED);
	}
	
	/*
	 * Verify that the user who does not have "Publish and suspend accessories" right could not access marketing actions
	 */
	@Test
	public void TC040PMS_02() {
		result.addLog("ID : 040PMS_02 : Verify that the user who does not have 'Publish and suspend accessories' right could not access marketing actions");
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
		home.login(superUsername, superUserpassword);

		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table
		home.dtsSelectUserByEmail(partneruser);
		/*
		 * VP: Verify that the 090D User Info page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.TITLE));
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Enable all site privileges except "Publish and suspend accessories" for above user
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges[1]);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as a partner user
		home.login(partneruser, password);
		// 9. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 10. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 11. Fill valid value into all required fields
		// 12. Upload valid tuning file
		// 13. Upload marketing material
		// 14. Click "Save" link
		/*
		 * Init data
		 */
		Hashtable<String, String> data = TestData.accessoryTuning();
		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		// 15. Go through the tuning approval
		// Click Request Review Tuning link
		home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		// Log out user
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to Accessories page
		home.click(Xpath.linkAccessories);
		// Select accessories create above
		home.selectAnaccessorybyName(data.get("name"));
		// Select tuning rating
		home.click(AccessoryInfo.EDIT_MODE);
		home.clickOptionByIndex(AddAccessory.TUNING_RATING, 2);
		home.click(AddAccessory.SAVE);
		// Approve tuning
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Log out user
		home.logout();
		// Log into DTS portal as Partner user above
		home.login(partneruser, password);
		// Navigate to Accessories page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select accessory above
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" but there is no action for marketing approval section
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.APPROVED);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), AccessoryInfo.UNSUBMITTED);
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.MARKETING_ACTION));
		//Teardown:
		
		home.logout();
		home.login(superpartneruser,superpartnerpassword);
		
		// Delete accessory
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		home.deleteAnaccessorybyName(data.get("name"));
		//Restore user privileges
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a user from users table
		home.dtsSelectUserByEmail(partneruser);
		home.click(UserMgmt.EDIT);
		// 6. Enable all site privileges except "Publish and suspend accessories" for above user
		home.enableAllPrivilege();
		home.click(AddUser.SAVE);	
	
	}
	
	/*
	 * Verify that the "Request Marketing Review" link displays only after the partner uploads marketing material
	 */
	@Test
	public void TC040PMS_03() {
		result.addLog("ID : 040PMS_03 : Verify that the 'Request Marketing Review' link displays only after the partner uploads marketing material");
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
		home.login(superpartneruser, superpartnerpassword);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields
		// 5. Upload valid tuning file
		// 6. Do not upload marketing material
		// 7. Click "Save" link
		/*
		 * Init data
		 */
		Hashtable<String, String> data = TestData.accessoryTuning();
		home.addAccessoriesPartner(PartnerAddAccessory.getHash(), data);
		// 8. Go through the tuning approval
		// Click Request Review Tuning link
		home.click(AccessoryInfo.REQUEST_TUNING);
		// Log out user
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to Accessories page
		home.click(Xpath.linkAccessories);
		// Select accessories create above
		home.selectAnaccessorybyName(data.get("name"));
		// Select tuning rating
		home.click(AccessoryInfo.EDIT_MODE);
		home.clickOptionByIndex(AddAccessory.TUNING_RATING, 2);
		home.click(AddAccessory.SAVE);
		// Approve tuning
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Log out user
		home.logout();
		// Log into DTS portal as Partner user above
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to Accessories page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Select accessory above
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" but there is no action for marketing approval section
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.APPROVED);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), AccessoryInfo.UNSUBMITTED);
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.MARKETING_ACTION));
		// 9. Click "Edit" link
		home.click(AccessoryInfo.EDIT_MODE);
		// 10. Upload marketing material
		home.uploadFile(AddAccessory.ADD_MARKETING, Constant.AUDIO_ROUTE_FILE);
		home.clickOptionByIndex(AddAccessory.MARKETING_METERIAL_TYPE, 1);
		// 11. Click "Save" link
		home.click(AddAccessory.SAVE);
		/*
		 * Verify that The "Request Marketing Review" link is displayed
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_ACTION), AccessoryInfo.REQUEST_MARKETING_REVIEW_ACTION_STRING);
		// Delete accessory
		home.doDelete(AccessoryInfo.DELETE);
	}	
		
		
	/*
	 * Verify that the Marketing status is changed to "Pending DTS Review" and the action is "Cancel Request" when partner click "Request Marketing Review".
	 */
	@Test
	public void TC040PMS_04() {
		result.addLog("ID : 040PMS_02 : Verify that the Marketing status is changed to 'Pending DTS Review' and the action is 'Cancel Request' when partner click 'Request Marketing Review'.");
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
		home.login(superpartneruser, superpartnerpassword);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields
		// 5. Upload valid tuning file
		// 6. Upload marketing material
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.accessoryPublish();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		//8. Go through the tuning approval
		home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		// Log out
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to product page
		home.click(Xpath.linkAccessories);
		// Select product above
		home.selectAnaccessorybyName(data.get("name"));
		// Select tuning rating
		home.click(AccessoryInfo.EDIT_MODE);
		home.selectOptionByName(AddAccessory.TUNING_RATING, AddAccessory.tuningRatingOption[2]);
		home.click(AddAccessory.SAVE);
		// Approve tuning
		home.click(AccessoryInfo.APPROVE_TUNING);
		// Log out
		home.logout();
		// Log into DTS portal as partner user above
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to Product page
		home.click(Xpath.linkAccessories);
		// Select product above
		home.selectAnaccessorybyName(data.get("name"));
		/*
		 * VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and its action is "Request Marketing Review"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), AccessoryInfo.UNSUBMITTED);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_ACTION), AccessoryInfo.REQUEST_MARKETING_REVIEW_ACTION_STRING);
		// 9. Click "Request Marketing Review" link
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		/*
		 * Verify that The Marketing status is changed to "Pending DTS Review" and the action is "Cancel Request"
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), "PENDING DTS REVIEW");
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_ACTION), AccessoryInfo.MARKETING_CANCEL_REQUEST_ACTION_STRING);
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the Marketing status is changed to "APPROVED" and there is no action displayed when the marketing material is approved by DTS admin.
	 */
	@Test
	public void TC040PMS_05() {
		result.addLog("ID : 040PMS_05 : Verify that the Marketing status is changed to 'APPROVED' and there is no action displayed when the marketing material is approved by DTS admin");
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
		home.login(superpartneruser, superpartnerpassword);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		//3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		//4. Fill valid value into all required fields
		//5. Upload valid tuning file
		//6. Upload marketing material
		//7. Click "Save" link
		/*
		 * Init data
		 */
		Hashtable<String, String> data = TestData.accessoryTuning();
		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		//8. Go through the tuning approval
		home.logout();
		home.login(superUsername, superUserpassword);
		home.click(DTSHome.LINK_ACCESSORIES);
		home.selectAnaccessorybyName(data.get("name"));
		home.click(AccessoryInfo.EDIT_MODE);
		home.selectOptionByName(AddAccessory.TUNING_RATING, AddAccessory.tuningRatingOption[2]);
		home.click(AddAccessory.SAVE);
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		home.click(AccessoryInfo.APPROVE_TUNING);
		home.logout();
		home.login(partneruser, password);
		home.click(PartnerHomePage.LINK_PARTNER_ACCESSORIES);
		home.selectAnaccessorybyName(data.get("name"));
		//	VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and its action is "Request Marketing Review"
		
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.APPROVED);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), AccessoryInfo.UNSUBMITTED);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_ACTION), AccessoryInfo.REQUEST_MARKETING_REVIEW_ACTION_STRING);
		//	9. Click "Request Marketing Review" link
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		// 10. Log out DTS portal
		home.logout();
		// 11. Log into DTS portal as DTS admin
		home.login(superUsername, superUserpassword);
		// 12. Navigate to "Accessories" page
		home.click(DTSHome.LINK_ACCESSORIES);
		// 13. Select and open above accessory
		home.selectAnaccessorybyName(data.get("name"));
		// 14. Click "Approve Marketing" link
		home.click(AccessoryInfo.APPROVE_MARKETING);
		// 15. Log out DTS portal
		home.logout();
		// 16. Log into DTS poratl as above partner user
		home.login(partneruser, password);
		// 17. Navigate to "Accessories" page
		home.click(PartnerHomePage.LINK_PARTNER_ACCESSORIES);
		// 18. Select and open avove accessory
		home.selectAnaccessorybyName(data.get("name"));
		// Verify marketing status is changed to "APPROVED" and its action is empty
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), AccessoryInfo.APPROVED);
		Assert.assertEquals(home.checkMaketingActionsDisplayed(), true);
		// Delete an Accessory after add
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 Verify that the marketing status is changed to "DECLINED" when the DTS admin declines marketing material
	*/
	@Test
	public void TC040PMS_06() {
		result.addLog("ID : 040PMS_06 : Verify that the marketing status is changed to 'DECLINED' when the DTS admin declines marketing material");
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
		home.login(superpartneruser, superpartnerpassword);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		//3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields
		// 5. Upload valid tuning file
		// 6. Upload marketing material
		// 7. Click "Save" link
		/*
		 * Init data
		 */
		Hashtable<String, String> data = TestData.accessoryTuning();
		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		//8. Go through the tuning approval
		home.logout();
		home.login(superUsername, superUserpassword);
		home.click(DTSHome.LINK_ACCESSORIES);
		home.selectAnaccessorybyName(data.get("name"));
		home.click(AccessoryInfo.EDIT_MODE);
		home.selectOptionByName(AddAccessory.TUNING_RATING, AddAccessory.tuningRatingOption[2]);
		home.click(AddAccessory.SAVE);
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		home.click(AccessoryInfo.APPROVE_TUNING);
		home.logout();
		home.login(partneruser, password);
		home.click(PartnerHomePage.LINK_PARTNER_ACCESSORIES);
		home.selectAnaccessorybyName(data.get("name"));
		//VP: Verify that the tuning is approved, the marketing status is changed to "UNSUBMITTED" and its action is "Request Marketing Review"
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.APPROVED);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), AccessoryInfo.UNSUBMITTED);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_ACTION), AccessoryInfo.REQUEST_MARKETING_REVIEW_ACTION_STRING);
		
		//9. Click "Request Marketing Review" link
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		//10. Log out DTS portal
		home.logout();
		//11. Log into DTS portal as DTS admin
		home.login(superUsername, superUserpassword);
		//12. Navigate to "Accessories" page
		home.click(DTSHome.LINK_ACCESSORIES);
		//13. Select and open above accessory
		home.selectAnaccessorybyName(data.get("name"));
		//14. Click "Decline Marketing" link
		home.click(AccessoryInfo.DECLINE_MARKETING);
		//15. Log out DTS portal
		home.logout();
		//16. Log into DTS portal as above partner user
		home.login(partneruser, password);
		//17. Navigate to "Accessories" page
		home.click(PartnerHomePage.LINK_PARTNER_ACCESSORIES);
		//18. Select and open above accessory
		home.selectAnaccessorybyName(data.get("name"));
		//The Marketing status is changed to "DECLINED" and there is no more action in marketing approval section
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS),AccessoryInfo.DECLINED);
		Assert.assertEquals(home.checkMaketingActionsDisplayed(), true);
		// Delete an Accessory after add
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 Verify that the marketing status is changed to "REVOKED" and the action "Request Marketing Review" is displayed when the DTS admin revokes the approved marketing material
	*/
	@Test
	public void TC040PMS_07() {
		result.addLog("ID : 040PMS_07 : Verify that the marketing status is changed to 'REVOKED' and the action 'Request Marketing Review' is displayed when the DTS admin revokes the approved marketing material");
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
		home.login(superpartneruser, superpartnerpassword);
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		//3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		/*
		 * Init data
		 */
		Hashtable<String, String> data = TestData.accessoryTuning();
		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		//Log out DTS portal
		home.logout();
		//Log into DTS portal as a DTS admin successfully
		home.login(superUsername, superUserpassword);
		//Navigate to "Accessories" page
		home.click(DTSHome.LINK_ACCESSORIES);
		//Select and open above accessory
		home.selectAnaccessorybyName(data.get("name"));
		home.click(AccessoryInfo.EDIT_MODE);
		home.selectOptionByName(AddAccessory.TUNING_RATING, AddAccessory.tuningRatingOption[2]);
		home.click(AddAccessory.SAVE);
		//Approve Tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		home.click(AccessoryInfo.APPROVE_TUNING);
		//Approve Marketing
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		home.click(AccessoryInfo.APPROVE_MARKETING);
		// 3. Select an marketing approved accessory from accessories table
		// 4. Click "Revoke Marketing Approval" link
		home.click(AccessoryInfo.REVOKE_MARKETING_APPROVAL);
		// 5. Log out DTS portal
		home.logout();
		// 6. Log into DTS portal as partner user
		home.login(partneruser, password);
		// 7. Navigate to "Accessories" page
		home.click(PartnerHomePage.LINK_PARTNER_ACCESSORIES);
		// 8. Select above revoked marketing accessory
		home.selectAnaccessorybyName(data.get("name"));
		//Verify The Marketing status is changed to "REVOKED" and its action is "Request Marketing Review"
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), AccessoryInfo.REVOKED);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_MARKETING_REVIEW), AccessoryInfo.REQUEST_MARKETING_REVIEW_ACTION_STRING);
	}
	
	/*
	 Verify that the approved marketing material is changed to "CLOSED" and there is no action in marketing approval section when the approved tuning file is removed
	*/
	@Test
	public void TC040PMS_08() {
		result.addLog("ID : 040PMS_08 : Verify that the approved marketing material is changed to 'CLOSED' and there is no action in marketing approval section when the approved tuning file is removed");
		/*
			Pre-condition:  The partner user has "Publish and suspend accessories" rights. The tuning approval is approved. The marketing approval is approved.

				1. Log into DTS portal as partner user
				2. Navigate to "Accessories" page
				3. Select an accessory from accessories table which both tuning and marketing approval are "APPROVED"
				4. Click “Edit” link
				5. Delete the approved tuning file
				6. Click “Save” link
		*/
		home.login(superpartneruser, superpartnerpassword);
		// Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// Click Add Accessory link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// Create accessory
		Hashtable<String, String> data = TestData.accessoryTuning();
		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		//Log out DTS portal
		home.logout();
		//Log into DTS portal as a DTS admin successfully
		home.login(superUsername, superUserpassword);
		//Navigate to "Accessories" page
		home.click(DTSHome.LINK_ACCESSORIES);
		//Select and open above accessory
		home.selectAnaccessorybyName(data.get("name"));
		home.click(AccessoryInfo.EDIT_MODE);
		home.selectOptionByName(AddAccessory.TUNING_RATING, AddAccessory.tuningRatingOption[2]);
		home.click(AddAccessory.SAVE);
		//Approve Tuning
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		home.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		home.click(AccessoryInfo.APPROVE_TUNING);
		//Approve Marketing
		home.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		home.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		home.click(AccessoryInfo.APPROVE_MARKETING);
		home.logout();
		//1. Log into DTS portal as partner user
		home.login(partneruser, password);
		//2. Navigate to "Accessories" page
		home.click(PartnerHomePage.LINK_PARTNER_ACCESSORIES);
		//3. Select an accessory from accessories table which both tuning and marketing approval are "APPROVED"
		home.selectAnaccessorybyName(data.get("name"));
		//4. Click “Edit” link
		home.click(AccessoryInfo.EDIT_MODE);
		//5. Delete the approved tuning file
		home.doDelete(AddAccessory.DELETE_UPLOADED_TUNING);
		//6. Click “Save” link
		home.click(AddAccessory.SAVE);
		//Verify The tuning status is changed to "UNSUBMITTED" and marketing status is changed to "CLOSED" and its action is empty.
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.TUNING_STATUS), AccessoryInfo.UNSUBMITTED);
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.MARKETING_STATUS), AccessoryInfo.CLOSED);
		Assert.assertEquals(home.checkMaketingActionsDisplayed(), true);
	}
	
}
