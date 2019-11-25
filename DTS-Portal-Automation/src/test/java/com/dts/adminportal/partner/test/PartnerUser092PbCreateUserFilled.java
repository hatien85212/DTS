package com.dts.adminportal.partner.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.Result;

import dts.com.adminportal.model.AddPartnerUser;
import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.PartnerListUser;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UsersList;
import dts.com.adminportal.model.Xpath;

public class PartnerUser092PbCreateUserFilled extends CreatePage {
	
	private HomeController home;
	
	private String mail ;
	private String givenName;
	private String familyName;
	private String title;
	private String phone;
	private List<String> userData;
	
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}

	@BeforeMethod
	public void loginBeforeTest() {
		home.login(superpartneruser, superpartnerpassword);
	}
	
	/*
	 * Verify that new user could be created successfully with a site privilege.
	 */
	@Test 
	public void TC092PbCUF_02(){
		String expected="Pass";
		mail = RandomStringUtils.randomAlphabetic(15)+"@infonam.com";
		givenName=RandomStringUtils.randomAlphabetic(10);
		familyName=RandomStringUtils.randomAlphabetic(5);
		title=RandomStringUtils.randomAlphabetic(20);
		phone=RandomStringUtils.randomNumeric(10);
		userData= new ArrayList<String>();
		userData.add(givenName);
		userData.add(familyName);
		userData.add(title);
		userData.add(phone);
		userData.add(mail);
		result.addLog("ID : TC092PbCUF_02 : Verify that new user could be created successfully with a site privilege");
		
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign any site privilege for new user
			7. Click "Save" link
		*/
		
		// Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// Click "Add New User" link
		home.click(Xpath.createNewUser);
		//Fill valid values into required fields
		home.editData(AddUser.GIVEN_NAME,  givenName);
		home.editData(AddUser.FAMILY_NAME,  familyName);
		home.editData(AddUser.TITLE,  title);
		home.editData(AddUser.EMAIL,  mail);
		home.editData(AddUser.PHONE, phone);
		//uncheck some site privileges for new user
		home.disableAllPrivilege(AddPartnerUser.PRIVILEGES_TABLE);
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Publish_and_suspend_products);
		//click "Save"
		home.click(AddUser.SAVE);
		// Check "Sucess message"
		Assert.assertEquals("Success",home.getTextByXpath(Xpath.userCreateSucess));
		// Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		Result real=home.checkUserAdded(PartnerListUser.TBODY, userData,UsersList.INDEX);
		Assert.assertEquals(real.getResult(),expected);
		// Teardown
		// Delete user
		home.deleteUserByEmail(mail);
	}
	
	/*
	 * Verify that new user could be created without assigning site notifications
	 */
	@Test 
	public void TC092PbCUF_03(){
		String expected="Pass";
		mail = RandomStringUtils.randomAlphabetic(15)+"@infonam.com";
		givenName=RandomStringUtils.randomAlphabetic(10);
		familyName=RandomStringUtils.randomAlphabetic(5);
		title=RandomStringUtils.randomAlphabetic(20);
		phone=RandomStringUtils.randomNumeric(10);
		userData= new ArrayList<String>();
		userData.add(givenName);
		userData.add(familyName);
		userData.add(title);
		userData.add(phone);
		userData.add(mail);
		result.addLog("ID : TC092PbCUF_03 : Verify that new user could be created without assigning site notifications");
		
		/*		
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Enable all privileges
			7. Disable all site privilege notification
			8. Click "Save" link
		*/
		
		// Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// Click "Add New User" link
		home.click(Xpath.createNewUser);
		//Fill valid values into required fields
		home.editData(AddUser.GIVEN_NAME,  givenName);
		home.editData(AddUser.FAMILY_NAME,  familyName);
		home.editData(AddUser.TITLE,  title);
		home.editData(AddUser.EMAIL,  mail);
		home.editData(AddUser.PHONE, phone);
		//6. Enable all privileges
		home.enableAllPrivilege();
		//7. Disable all site privilege notification
		home.uncheckAllNotification(AddUser.PRIVILEGES_TABLE);
		
		//click "Save"
		home.click(AddUser.SAVE);
		// Check "Sucess message"
		Assert.assertEquals("Success", home.getTextByXpath(Xpath.userCreateSucess));
		// Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		Result real=home.checkUserAdded(PartnerListUser.TBODY, userData,UsersList.INDEX);
		Assert.assertEquals(real.getResult(),expected);
		
		// Teardown
		// Delete user
		home.deleteUserByEmail(mail);
		
	}
	
	/*
	 * Verify that the similar site notification is also be enabled when assigning a site privilege.
	 */
	@Test 
	public void TC092PbCUF_04(){
		mail = RandomStringUtils.randomAlphabetic(15)+"@infonam.com";
		givenName=RandomStringUtils.randomAlphabetic(10);
		familyName=RandomStringUtils.randomAlphabetic(5);
		title=RandomStringUtils.randomAlphabetic(20);
		phone=RandomStringUtils.randomNumeric(10);
		result.addLog("ID : TC092PbCUF_04 : Verify that the similar site notification is also be enabled when assigning a site privilege.");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign a site privilege for new user 
		 */	
		//4.Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// Click "Add New User" link
		home.click(Xpath.createNewUser);
		//5.Fill valid values into required fields
		home.editData(AddUser.GIVEN_NAME,  givenName);
		home.editData(AddUser.FAMILY_NAME,  familyName);
		home.editData(AddUser.TITLE,  title);
		home.editData(AddUser.EMAIL,  mail);
		home.editData(AddUser.PHONE, phone);
		
		//6. Assign a site privilege for new user
		home.enableAllPrivilege();
		
		boolean real1=home.isNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		boolean real2= home.isNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE, Privileges.Edit_brand_info);
		boolean real3= home.isNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE, Privileges.Publish_and_suspend_products);
		boolean real4= home.isNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE, Privileges.Request_product_tunings);
		Assert.assertEquals(real1, true);
		Assert.assertEquals(real2, true);
		Assert.assertEquals(real3, true);
		Assert.assertEquals(real4, true);
	}
	
	/*
	 * Verify that the similar site notification is also be disabled when disabling a site privilege.
	 */
	@Test 
	public void TC092PbCUF_05(){
//		mail = RandomStringUtils.randomAlphabetic(15)+"@infonam.com";
//		givenName=RandomStringUtils.randomAlphabetic(10);
//		familyName=RandomStringUtils.randomAlphabetic(5);
//		title=RandomStringUtils.randomAlphabetic(20);
//		phone=RandomStringUtils.randomNumeric(10);
		result.addLog("ID : TC092PbCUF_05 : Verify that the similar site notification is also be disabled when disabling a site privilege.");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign a site privilege for new user
			VP: The similar site notification is also checked
			7. Disable above site privilege
			VP: The similar site notification of assigned site privilege is also disabled.
			
		 */
		// Click "Users" tab
		home.click(Xpath.LINK_PARTNER_USER);
		// Click "Add New User" link
		home.click(Xpath.createNewUser);
		//Fill valid values into required fields
		home.editData(AddUser.GIVEN_NAME,  givenName);
		home.editData(AddUser.FAMILY_NAME,  familyName);
		home.editData(AddUser.TITLE,  title);
		home.editData(AddUser.EMAIL,  mail);
		home.editData(AddUser.PHONE, phone);
		
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE,Privileges.Add_and_manage_products);
		
		//Verify the similar site notification is also disabled
		boolean real1=home.isNotificationCheckboxDisable(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_products);
		Assert.assertEquals(real1, true);

	}
}
