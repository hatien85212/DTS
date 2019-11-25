package com.dts.adminportal.partner.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.DTSUtil;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.PartnerListUser;
import dts.com.adminportal.model.UsersList;
import dts.com.adminportal.model.Xpath;

public class PartnerUser092PaCreateUserBlank extends CreatePage{
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
	 * Verify that the "Given Name", "Family Name", "Email" and "Phone" are required when creating new users
	 */
	@Test
	public void TC092PaCUB_01(){
		result.addLog("ID : TC092PaCUB_01 : Verify that the 'Given Name', 'Family Name', 'Email' and 'Phone' are required when creating new users");
		/*
			Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Click "Save" link without filling information into  "Given Name", "Family Name", "Email" and "Phone"
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Click "Save" link without filling information into  "Given Name", "Family Name", "Email" and "Phone"
		home.click(AddUser.SAVE);
		/*
		 * Verify that There is an error message  displayed next to "Given Name", "Family Name", "Email" and "Phone" that mention to these fields are required information
		 */
		Assert.assertTrue(home.existsElement(AddUser.REQUIRES_PARTNER));
	}
	
	/*
	 * Verify that the site privileges display full rights.
	 */
	@Test
	public void TC092PaCUB_02(){
		result.addLog("ID : TC092PaCUB_02 : Verify that the site privileges display full rights.");
		/*
			Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Click "Add New User" link
		home.click(PartnerListUser.ADD_USER);
		/*
		 * Verify that 092Pa Create User Blank is displayed
		 */
		Assert.assertEquals(home.existsElement(AddUser.getPartnerUser()).getResult(), "Pass");
		/*
		 	The site privileges have following rights: 
		 	Add and manage accessories
			Publish and suspend accessories
			Request accessory tunings
			View publication credits
			Edit Company Info
			Edit brand info
			Add and manage users
		 */
		ArrayList<String> privileges = home.getPrivileges(AddUser.PRIVILEGES_TABLE);
		Assert.assertTrue(DTSUtil.containsAll(privileges, AddUser.PRIVILEGES));
	}
	
	/*
	 * Verify that new user could not be created with already exists email address.
	 */
	@Test
	public void TC092PaCUB_03(){
		result.addLog("ID : TC092PaCUB_07 : Verify that new user could not be created with already exists email address.");
		
		/*
			Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill all valid values into required fields except Email
			6. Fill an email address into "Email" text field which is assigned to another user's account 
			7. Click "Save" link
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill all valid values into required fields except Email
		// 6. Fill an email address into "Email" text field which is assigned to another user's account
		Hashtable<String,String> data = TestData.partnerUser();
		data.put("email", partneruser);
		data.remove("save");
		home.addUser(AddUser.getPartnerUser(), data);
		// 7. Click "Save"
		home.click(AddUser.SAVE);
		/*
		 * Verify that There is an error message displayed which mentions to an already exists email address
		 */
		Assert.assertTrue(home.checkMessageDisplay("! Account with this email address already exists"));
		
	}
	
	/*
	 * Verify that partner user could not add new user with invalid email address format.
	 */
	@Test
	public void TC092PaCUB_04(){
		result.addLog("ID : TC092PaCUB_04 : Verify that partner user could not add new user with invalid email address format.");
		/*
			Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill all valid values into required fields except Email
			6. Click "Save" link
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill all valid values into required fields except Email
		Hashtable<String,String> data = TestData.partnerUser();
		data.put("email", RandomStringUtils.randomAlphanumeric(20) + ".example.com");
		data.remove("save");
		home.addUser(AddUser.getPartnerUser(), data);
		// 6. Click "Save"
		home.click(AddUser.SAVE);
		/*
		 * Verify that There is an error message displayed which mention to the incorrect format of email address
		 */
		Assert.assertTrue(home.checkMessageDisplay("! Email is not right"));
	}

}
