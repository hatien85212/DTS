package com.dts.adminportal.partner.test;

import java.util.Hashtable;

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
import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.TuningRequestForm;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.Xpath;

public class PartnerUserCatalog043PaTurningRequestForm extends CreatePage {
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
	 * Verify that the user who does not have "Request accessory tunings" rights
	 * could not request DTS tuning
	 */
	@Test
	public void TC043PaTR_01() {
		result.addLog("ID : 043PaTR_01 : Verify that the user who does not have 'Request accessory tunings' rights could not request DTS tuning");
		/*
		 * 1. Navigate to DTS portal 
		 * 2. Log into DTS portal as a DTS user successfully 
		 * 3. Navigate to "Users" page 
		 * 4. Select a user from users table 
		 * VP: Verify that the 090D User Info page is displayed 
		 * 5. Click "Edit" link 
		 * 6. Enable all site privileges except "Request accessory tunings" for above user 
		 * 7. Log out DTS portal 
		 * 8. Log into DTS portal as above partner user 
		 * 9. Navigate to "Accessories" page 
		 * 10. Click "Add Accessory" link 
		 * 11. Fill valid value into all required fields 
		 * 12. Do not upload tuning 
		 * 13. Click "Save" link
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Select a user from users table
		home.selectUserByEmail(partneruser);
		/*
		 * VP: Verify that the 090D User Info page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.USER_INFO_PAGE_TITLE));
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Enable all site privileges except "Request accessory tunings" for above user
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Request_product_tunings);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner user
		home.login(partneruser,	password);
		// 9. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 10. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 11. Fill valid value into all required fields
		// 12. Do not upload tuning
		// 13. Click "Save" link
		Hashtable<String, String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * Verify that the correct 040P Accessory Detail page is displayed and
		 * the link "Request DTS tuning" is not displayed in Step 1: Tuning
		 * Approval of Publishing Process
		 */
		Assert.assertFalse(home.isElementPresent(AccessoryInfo.REQUEST_TUNING));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}

	/*
	 * Verify that the "Request DTS Tuning" link is displayed for user who has
	 * "Request accessory tunings" rights
	 */
	@Test
	public void TC043PaTR_02() {
		result.addLog("ID : 043PaTR_02 : Verify that the 'Request DTS Tuning' link is displayed for user who has 'Request accessory tunings' rights.");
		/*
		 * 1. Navigate to DTS portal 
		 * 2. Log into DTS portal as a DTS user successfully 
		 * 3. Navigate to "Users" page 
		 * 4. Select a user from users table 
		 * VP: Verify that the 090D User Info page is displayed 
		 * 5. Click "Edit" link 
		 * 6. Enable all site privileges including "Request accessory tunings" for above user 
		 * 7. Log out DTS portal 
		 * 8. Log into DTS portal as above partner user 
		 * 9. Navigate to "Accessories" page 
		 * 10. Click "Add Accessory" link 
		 * 11. Fill valid value into all required fields 
		 * 12. Do not upload tuning 
		 * 13. Click "Save" link
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Select a user from users table
		home.selectUserByEmail(partneruser);
		/*
		 * VP: Verify that the 090D User Info page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.USER_INFO_PAGE_TITLE));
		// 5. Click "Edit" link
		home.click(UserMgmt.EDIT);
		// 6. Enable all site privileges including "Request accessory tunings" for above user
		home.enableAllPrivilege();
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above partner user
		home.login(partneruser,	password);
		// 9. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 10. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 11. Fill valid value into all required fields
		// 12. Do not upload tuning
		// 13. Click "Save" link
		Hashtable<String, String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * The correct 040P product Detail page is displayed and the link "Request DTS tuning" is displayed in Step 1: Tuning Approval of Publishing Process
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.REQUEST_TUNING));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the Tuning Request Form includes form name, sub-title, message, term checkbox, accessory data and additional message.
	 */
	@Test
	public void TC043PaTR_03() {
		result.addLog("ID : 043PaTR_03 : Verify that the Tuning Request Form includes form name, sub-title, message, term checkbox, accessory data and additional message");
		/*
		 * Pre-Condition: Partner user has "Request accessory tunings" rights.
		 * 1. Log into DTS portal as a partner user
		 * 2. Navigate to "Accessories" page
		 * 3. Click "Add Accessory" link
		 * 4. Fill valid value into all required fields
		 * 5. Do not upload tuning
		 * 6. Click "Save" link
		 * VP: The correct 040P Accessory Detail page is displayed and the "Request DTS Tuning" link is displayed.
		 * 7. Click Request DTS Tuning link
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields
		// 5. Do not upload tuning
		// 6. Click "Save" link
		Hashtable<String, String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: The correct 040P Accessory Detail page is displayed and the "Request DTS Tuning" link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.REQUEST_TUNING));
		// 7. Click Request DTS Tuning link
		home.click(AccessoryInfo.REQUEST_TUNING);
		/*
		 * The 043Pa Tuning Request Form page is displayed which including:  form name, sub-title, message, term checkbox, accessory data and additional message.
		 */
		Assert.assertEquals(home.existsElement(TuningRequestForm.APPROVE_TUNNING_FORM()).getResult(), "Pass");
		// Delete product
		home.click(TuningRequestForm.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the portal redirect to 040P Accessory Detail page when user clicking "Cancel" link in 043Pa Tuning Request Form page.
	 */
	@Test
	public void TC043PaTR_04() {
		result.addLog("ID : 043PaTR_04 : Verify that the portal redirect to 040P Accessory Detail page when user clicking 'Cancel' link in 043Pa Tuning Request Form page");
		/*
		 * Pre-Condition: Partner user has "Request accessory tunings" rights.
		 * 1. Log into DTS portal as a partner user
		 * 2. Navigate to "Accessories" page
		 * 3. Click "Add Accessory" link
		 * 4. Fill valid value into all required fields
		 * 5. Do not upload tuning
		 * 6. Click "Save" link
		 * VP: The correct 040P Accessory Detail page is displayed and the "Request DTS Tuning" link is displayed.
		 * 7. Click Request DTS Tuning" link
		 * VP: The 043Pa Tuning Request Form page is displayed with "Cancel" link
		 * 8. Click "Cancel" link
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields
		// 5. Do not upload tuning
		// 6. Click "Save" link
		Hashtable<String, String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: The correct 040P Accessory Detail page is displayed and the "Request DTS Tuning" link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.REQUEST_TUNING));
		// 7. Click Request DTS Tuning link
		home.click(AccessoryInfo.REQUEST_TUNING);
		/*
		 * VP: The 043Pa Tuning Request Form page is displayed with "Cancel" link
		 */
		Assert.assertEquals(home.existsElement(TuningRequestForm.APPROVE_TUNNING_FORM()).getResult(), "Pass");
		// 8. Click "Cancel" link
		home.click(TuningRequestForm.CANCEL);
		/*
		 * Verify that The portal redirect to 040P Accessory Detail page 
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.REQUEST_TUNING));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 *Verify that user can not submit tuning request form without checking on term condition checkbox
	 */
	@Test
	public void TC043PaTR_05() {
		result.addLog("ID : 043PaTR_05 : Verify that user can not submit tuning request form without checking on term condition checkbox.");
		/*
		 * Pre-Condition: Partner user has "Request accessory tunings" rights.
		 * 1. Log into DTS portal as a partner user
		 * 2. Navigate to "Accessories" page
		 * 3. Click "Add Accessory" link
		 * 4. Fill valid value into all required fields
		 * 5. Do not upload tuning
		 * 6. Click "Save" link
		 * VP: The correct 040P Accessory Detail page is displayed and the "Request DTS Tuning" link is displayed.
		 * 7. Click Request DTS Tuning" link
		 * VP: The 043Pa Tuning Request Form page is displayed
		 * 8. Do not check for "I understand the terms of this submission" checkbox
		 * 9. Click "Submit" link
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields
		// 5. Do not upload tuning
		// 6. Click "Save" link
		Hashtable<String, String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: The correct 040P Accessory Detail page is displayed and the "Request DTS Tuning" link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.REQUEST_TUNING));
		// 7. Click Request DTS Tuning link
		home.click(AccessoryInfo.REQUEST_TUNING);
		/*
		 * VP: The 043Pa Tuning Request Form page is displayed
		 */
		Assert.assertEquals(home.existsElement(TuningRequestForm.APPROVE_TUNNING_FORM()).getResult(), "Pass");
		// 8. Do not check for "I understand the terms of this submission" checkbox
		// 9. Click "Submit" link
		home.click(TuningRequestForm.SUBMIT);
		/*
		 * Verify that An error message "Please select your agreement" is displayed below the term condition check box and the request form could not be submitted
		 */
		Assert.assertTrue(home.isElementPresent(TuningRequestForm.ERROR_MESSAGE));
		// Delete product
		home.click(TuningRequestForm.PRODUCT_LINK);
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that clicking on Model name in 043Pa Tuning Request Form will return to it detail page
	 */
	@Test
	public void TC043PaTR_06() {
		result.addLog("ID : 043PaTR_06 : Verify that clicking on Model name in 043Pa Tuning Request Form will return to it detail page");
		/*
		 * Pre-Condition: Partner user has "Request accessory tunings" rights.
		 * 1. Log into DTS portal as a partner user
		 * 2. Navigate to "Accessories" page
		 * 3. Click "Add Accessory" link
		 * 4. Fill valid value into all required fields
		 * 5. Do not upload tuning
		 * 6. Click "Save" link
		 * VP: The correct 040P Accessory Detail page is displayed and the "Request DTS Tuning" link is displayed.
		 * 7. Click Request DTS Tuning" link
		 * VP: The 043Pa Tuning Request Form page is displayed
		 * 8. Click on the accessory name link on top of page
		 */
		// 2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		// 3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		// 4. Fill valid value into all required fields
		// 5. Do not upload tuning
		// 6. Click "Save" link
		Hashtable<String, String> data = TestData.accessoryDraft();
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: The correct 040P Accessory Detail page is displayed and the "Request DTS Tuning" link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.REQUEST_TUNING));
		// 7. Click Request DTS Tuning link
		home.click(AccessoryInfo.REQUEST_TUNING);
		/*
		 * VP: The 043Pa Tuning Request Form page is displayed
		 */
		Assert.assertEquals(home.existsElement(TuningRequestForm.APPROVE_TUNNING_FORM()).getResult(), "Pass");
		// 8. Click on the accessory name link on top of page
		home.click(TuningRequestForm.PRODUCT_LINK);
		/*
		 * The portal is redirected to correct accessory detail page
		 */
		Assert.assertTrue(home.isElementPresent(AccessoryInfo.REQUEST_TUNING));
		// Delete product
		home.doDelete(AccessoryInfo.DELETE);
	}
}
