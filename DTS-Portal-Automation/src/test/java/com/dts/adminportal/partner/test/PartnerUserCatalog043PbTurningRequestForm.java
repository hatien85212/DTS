package com.dts.adminportal.partner.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.DTSUtil;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AccessoryInfo;
import dts.com.adminportal.model.AccessoryMain;
import dts.com.adminportal.model.AddAccessory;
import dts.com.adminportal.model.PartnerAccessoryInfo;
import dts.com.adminportal.model.TuningRequestForm;
import dts.com.adminportal.model.TuningRequestFormSuccess;
import dts.com.adminportal.model.Xpath;


public class PartnerUserCatalog043PbTurningRequestForm extends CreatePage{
	private HomeController home;
		
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}

	@BeforeMethod
	public void loginBeforeTest() {
		home.login(partneruser, password);
	}
	/*
	 * Verify that clicking on Model name in 043Pb Tuning Request Form Success page will return to it detail page
	 */
	@Test
	public void TC043PbTR_01() {
		result.addLog("ID : TC043PbTR_01 : Verify that clicking on Model name in 043Pb Tuning Request Form Success page will return to its detail page");
		//result.addLog("Failed by PDPP-494: 043Pa Tuning Request Form: The duplicated content is displayed when partner user requests DTS tuning file.");
		/*
		 Pre-Condition: Partner user has "Request accessory tunings" rights.

			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Fill valid value into all required fields
			5. Do not upload tuning
			6. Click "Save" link
			VP: The correct 040P Accessory Detail page is displayed and the "Request DTS Tuning" link is displayed.
			7. Click Request DTS Tuning" link
			VP: The 043Pa Tuning Request Form page is displayed
			8. Tick to check "I understand the terms of this submission" checkbox
			9. Click "Submit" link
			VP: The 043Pb Tuning Request Form Success page is displayed
			10. Click on accessory name link on top of page.
		 */
		//2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		//3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		//4. Fill valid value into all required fields
		//5. Do not upload tuning
		//6. Click "Save" link
		/*
		 * Init data
		 */
		Hashtable<String, String> data = TestData.accessoryDraft();
		// Fill value
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		/*
		 * VP: The correct 040P Accessory Detail page is displayed and the "Request DTS Tuning" link is displayed.
		 */
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_TUNING), AccessoryInfo.REQUEST_DTS_TUNING_ACTION_STRING);
		//7. Click Request DTS Tuning" link
		home.click(AccessoryInfo.REQUEST_TUNING);
		//VP: The 043Pa Tuning Request Form page is displayed
		Assert.assertEquals(home.existsElement(TuningRequestForm.APPROVE_TUNNING_FORM()).getResult(), "Pass");
		//8. Tick to check "I understand the terms of this submission" checkbox
		home.click(TuningRequestForm.AGREE);
		//9. Click "Submit" link
		home.click(TuningRequestForm.SUBMIT);
		/*
		 * VP: The 043Pb Tuning Request Form Success page is displayed
		 */
		Assert.assertEquals(home.existsElement(TuningRequestFormSuccess.getSuccessPageInfo()).getResult(),"Pass");
		//10. Click on accessory name link on top of page.
		home.click(TuningRequestForm.PRODUCT_LINK);
		/*
		 * The portal is redirected to correct accessory detail page.
		 */
		Assert.assertEquals("Pass", home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult());
		// Delete Accessory
		home.doDelete(AccessoryInfo.DELETE);
	}
	
	/*
	 * Verify that the entered values in 043Pa Tuning Request Form will display correctly in 043Pb Tuning Request Form Success page
	 */
	@Test
	public void TC043PbTR_02() {
		result.addLog("ID : TC043PbTR_02 : Verify that the entered values in 043Pa Tuning Request Form will display correctly in 043Pb Tuning Request Form Success page");
		//result.addLog("Failed by PDPP-494: 043Pa Tuning Request Form: The duplicated content is displayed when partner user requests DTS tuning file.");
		/*
		 Pre-Condition: Partner user has "Request accessory tunings" rights.

			1. Log into DTS portal as a partner user
			2. Navigate to "Accessories" page
			3. Click "Add Accessory" link
			4. Fill valid value into all required fields
			5. Do not upload tuning
			6. Click "Save" link
			7. The correct 040P Accessory Detail page is displayed and the "Request DTS Tuning" link is displayed.
			8. Click Request DTS Tuning" link
			VP: The 043Pa Tuning Request Form page is displayed
			9. Fill valid value into all fields
			10. Tick to check "I understand the terms of this submission" checkbox
			11. Click "Submit" link
		 */
		//2. Navigate to "Accessories" page
		home.click(Xpath.LINK_PARTNER_ACCESSORIES);
		//3. Click "Add Accessory" link
		home.click(AccessoryMain.ADD_ACCESSORY);
		//4. Fill valid value into all required fields
		//5. Do not upload tuning
		//6. Click "Save" link
		/*
		Init data
		*/
		Hashtable<String, String> data = TestData.accessoryDraft();
		// Fill value
		home.addAccessoriesPartner(AddAccessory.getHash(), data);
		ArrayList<String> list_accessory_info = new ArrayList<String>();
		list_accessory_info.add(data.get("name"));
		list_accessory_info.add(data.get("number"));
		list_accessory_info.add(data.get("upc"));
		//7. The correct 040P Accessory Detail page is displayed and the "Request DTS Tuning" link is displayed.
		Assert.assertEquals("Pass", home.existsElement(PartnerAccessoryInfo.getElementsInfo()).getResult());
		Assert.assertEquals(home.getTextByXpath(AccessoryInfo.REQUEST_TUNING), "Request DTS Tuning");
		//8. Click Request DTS Tuning" link
		home.click(AccessoryInfo.REQUEST_TUNING);
		/*
		 * VP: The 043Pa Tuning Request Form page is displayed
		 */
		Assert.assertEquals(home.existsElement(TuningRequestForm.APPROVE_TUNNING_FORM()).getResult(), "Pass");
		//9. Fill valid value into all fields
		//10. Tick to check "I understand the terms of this submission" checkbox
		home.click(TuningRequestForm.AGREE);
		//11. Click "Submit" link
		home.click(TuningRequestForm.SUBMIT);
		/*
		 * The 043Pb Tuning Request Form Success page is displayed and the entered information from 043Pa Tuning Request Form is displayed correctly
		 */
		String accessory_info = home.getTextByXpath(TuningRequestForm.ACCESSORY_INFO);
		Assert.assertEquals(home.existsElement(TuningRequestFormSuccess.getSuccessPageInfo()).getResult(),"Pass");
		Assert.assertTrue(DTSUtil.containsListText(accessory_info, list_accessory_info));
	}
}
