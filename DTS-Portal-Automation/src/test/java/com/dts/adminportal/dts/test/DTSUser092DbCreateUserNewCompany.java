package com.dts.adminportal.dts.test;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.MailUtil;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AddCompany;
import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.Companies;
import dts.com.adminportal.model.CompanyInfo;
import dts.com.adminportal.model.DTSHome;
import dts.com.adminportal.model.LoginPage;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.UsersList;
import dts.com.adminportal.model.Xpath;

public class DTSUser092DbCreateUserNewCompany extends CreatePage {
	private HomeController home;
	Boolean flag = true;
	String invite_message_title = "Your Invitation to DTS Headphone:X Partner Portal";
	
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}

	@BeforeMethod
	public void loginBeforeTest() {
		home.login(superUsername, superUserpassword);
	}

	/*
	 * Verify that the company name is displayed as read only when creating user
	 */
	@Test
	public void TC092DbCUN_01() {
		result.addLog("ID TC092DbCUN_01 : Verify that the company name is displayed as read only when creating user");
		/*
		  	Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Process through step 1 of the adding new conpany
			4. Navigate to step 2 "Contact Person" of "Create Company" process
		*/

		// 3. Process through step 1 of the adding new company
		home.click(Xpath.LINK_COMPANY);
		// Click on Add Company link
		home.click(Companies.ADD_COMPANY);
		// 4. Navigate to step 2 "Contact Person" of "Create Company" process
		Hashtable<String,String> data = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), data);
		// Check if PDPP-1185 found
		if(!home.isElementPresent(AddUser.BTN_COMPANY)){
			result.addErrorLog("PDPP-1185: 092Db New User Account Form: The known company context is not displayed when adding new partner companies");
			Assert.assertTrue(false);
		}
		/*
		 * Verify that The company name is displayed as read only
		 */
		Assert.assertEquals(home.getItemSelected(AddUser.BTN_COMPANY), data.get("name"));
		Assert.assertFalse(home.isElementEditable(AddUser.BTN_COMPANY));
		// Delete company
		home.click(Xpath.LINK_COMPANY);
		home.selectACompanyByName(data.get("name"));
		home.doDelete(CompanyInfo.DELETE);
	}
	
	/*
	 * Verify that the "Cancel" link is hidden in step 2 "Contact Person" of "Create Company" process
	 */
	@Test
	public void TC092DbCUN_02() {
		result.addLog("ID TC092DbCUN_02 : Verify that the 'Cancel' link is hidden in step 2 'Contact Person' of 'Create Company' process.");
		/*
		  	Pre-condition: User has full site privileges rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Process through step 1 of the adding new conpany
			4. Navigate to step 2 "Contact Person" of "Create Company" process
		*/
		// 3. Process through step 1 of the adding new company
		home.click(Xpath.LINK_COMPANY);
		// Click on Add Company link
		home.click(Companies.ADD_COMPANY);
		// 4. Navigate to step 2 "Contact Person" of "Create Company" process
		Hashtable<String,String> data = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), data);
		/*
		 * Verify that the "Cancel" link is hidden in step 2 "Contact Person" of "Create Company" process
		 */
		Assert.assertFalse(home.isElementPresent(AddUser.CANCEL));
		// Delete company
		home.click(Xpath.LINK_COMPANY);
		home.selectACompanyByName(data.get("name"));
		home.doDelete(CompanyInfo.DELETE);
	}
	
	/*
	 * Verify that the "Given Name", "Family Name", "Email" are required when creating new users
	 */
	@Test
	public void TC092DbCUN_03() {
		result.addLog("ID TC092DbCUN_03 : Verify that the 'Cancel' link is hidden in step 2 'Contact Person' of 'Create Company' process.");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Process through step 1 of the adding new conpany
			4. Navigate to step 2 "Contact Person" of "Create Company" process
			5. Click "Save" link without filling information into  "Given Name", "Family Name", "Email"
		*/
		// 3. Process through step 1 of the adding new company
		home.click(Xpath.LINK_COMPANY);
		// Click on Add Company link
		home.click(Companies.ADD_COMPANY);
		// 4. Navigate to step 2 "Contact Person" of "Create Company" process
		Hashtable<String, String> data = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), data);
		// 5. Click "Save" link without filling information into  "Given Name", "Family Name", "Email"
		home.click(AddUser.SAVE);
		/*
		 * Verify that There is an error message  displayed next to "Given Name", "Family Name", "Email" which mentions to requirement of information
		 */
		Assert.assertTrue(home.existsElement(AddUser.REQUIRES_PARTNER));
		// Delete company
		home.click(Xpath.LINK_COMPANY);
		home.selectACompanyByName(data.get("name"));
		home.doDelete(CompanyInfo.DELETE);
	}
	
	/*
	 * Verify that all Site Privileges options are selected by default when creating new users.
	 */
	@Test
	public void TC092DbCUN_04() {
		result.addLog("ID TC092DbCUN_04 : Verify that all Site Privileges options are selected by default when creating new users.");
		/*
	  		1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Process through step 1 of the adding new conpany
			4. Navigate to step 2 "Contact Person" of "Create Company" process
		*/
		// 3. Process through step 1 of the adding new company
		home.click(Xpath.LINK_COMPANY);
		// Click on Add Company link
		home.click(Companies.ADD_COMPANY);
		// 4. Navigate to step 2 "Contact Person" of "Create Company" process
		Hashtable<String, String> data = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), data);
		/*
		 * Verify that Site Privileges options are selected 
		 */
		Assert.assertTrue(home.isAllPrivilegeSelected(AddUser.PRIVILEGES_TABLE));
		// Delete company
		home.click(Xpath.LINK_COMPANY);
		home.selectACompanyByName(data.get("name"));
		home.doDelete(CompanyInfo.DELETE);
	}
	
	/*
	 * Verify that the user could not add new user with invalid email address format.
	 */
	@Test
	public void TC092DbCUN_05() {
		result.addLog("ID TC092DbCUN_05 : Verify that the user could not add new user with invalid email address format.");
		/*
		  	Pre-condition: User has full site privileges rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Process through step 1 of the adding new company
			4. Navigate to step 2 "Contact Person" of "Create Company" process
			5. Fill all valid values into required fields except Email
			6. Click "Save" link
		*/
		// 3. Process through step 1 of the adding new company
		home.click(Xpath.LINK_COMPANY);
		// Click on Add Company link
		home.click(Companies.ADD_COMPANY);
		// 4. Navigate to step 2 "Contact Person" of "Create Company" process
		Hashtable<String, String> dataCompany = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), dataCompany);
		// 5. Fill all valid values into required fields except Email
		// 6. Click "Save" link
		Hashtable<String,String> dataUser = TestData.partnerUser();
		dataUser.put("email", RandomStringUtils.randomAlphanumeric(20) + ".example.com");
		home.addUser(AddUser.getPartnerUser(), dataUser);
		/*
		 * Verify that There is an error message displayed which mention to the incorrect format of email address
		 */
		Assert.assertTrue(home.checkMessageDisplay("! Email is not right"));
		// Delete company
		home.click(Xpath.LINK_COMPANY);
		home.selectACompanyByName(dataCompany.get("name"));
		home.doDelete(CompanyInfo.DELETE);
	}
	
	/*
	 * Verify that new user could be created successfully with a site privilege.
	 */
	@Test
	public void TC092DbCUN_07() {
		result.addLog("ID TC092DbCUN_07 : Verify that new user could be created successfully with a site privilege.");
		/*
		  	Pre-condition: User has full site privileges rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Process through step 1 of the adding new conpany
			4. Navigate to step 2 "Contact Person" of "Create Company" process
			5. Fill valid values into required fields
			6. Assign any site privilege for new user
			7. Click "Save" link
		*/
		// 3. Process through step 1 of the adding new company
		home.click(Xpath.LINK_COMPANY);
		// Click on Add Company link
		home.click(Companies.ADD_COMPANY);
		// 4. Navigate to step 2 "Contact Person" of "Create Company" process
		Hashtable<String, String> dataCompany = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), dataCompany);
		// 5. Fill valid values into required fields
		// 6. Assign any site privilege for new user
		// 7. Click "Save" link
		Hashtable<String, String> dataUser = TestData.partnerUser();
		home.addUser(AddUser.getPartnerUser(), dataUser);
		/*
		 * Verify that New user could be created successfully	
		 */
		home.click(Xpath.LINK_USERS);
		Assert.assertTrue(home.checkUserExistByEmail(dataUser.get("email")));
		// Delete company
		home.click(Xpath.LINK_COMPANY);
		home.selectACompanyByName(dataCompany.get("name"));
		home.doDelete(CompanyInfo.DELETE);
	}
	
	/*
	 * Verify that new user could be created without assigning site notifications
	 */
	@Test
	public void TC092DbCUN_08() {
		result.addLog("ID TC092DbCUN_08 : Verify that new user could be created without assigning site notifications");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Process through step 1 of the adding new conpany
			4. Navigate to step 2 "Contact Person" of "Create Company" process
			5. Fill valid values into required fields
			6. Assign any site privilege for new user
			7. Do not assign any site notification
			8. Click "Save" link
		*/
		// 3. Process through step 1 of the adding new company
		home.click(Xpath.LINK_COMPANY);
		// Click on Add Company link
		home.click(Companies.ADD_COMPANY);
		// 4. Navigate to step 2 "Contact Person" of "Create Company" process
		Hashtable<String, String> dataCompany = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), dataCompany);
		// 5. Fill valid values into required fields
		// 6. Assign any site privilege for new user
		Hashtable<String, String> dataUser = TestData.partnerUser();
		dataUser.remove("save");
		home.addUser(AddUser.getPartnerUser(), dataUser);
		// 7. Do not assign any site notification
		home.uncheckAllNotification(AddUser.PRIVILEGES_TABLE);
		// 8. Click "Save" link
		home.click(AddUser.SAVE);
		/*
		 * Verify that New user could be created successfully without assigning site notification
		 */
		home.click(Xpath.LINK_USERS);
		Assert.assertTrue(home.checkUserExistByEmail(dataUser.get("email")));
		// Delete company
		home.click(Xpath.LINK_COMPANY);
		home.selectACompanyByName(dataCompany.get("name"));
		home.doDelete(CompanyInfo.DELETE);
	}
	
	/*
	 * Verify that the similar site notification is also be enabled when assigning a site privilege
	 */
	@Test
	public void TC092DbCUN_09() {
		result.addLog("ID TC092DbCUN_09 : Verify that the similar site notification is also be enabled when assigning a site privilege");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Process through step 1 of the adding new company
			4. Navigate to step 2 "Contact Person" of "Create Company" process
			5. Fill valid values into required fields
			6. Assign a site privilege for new user
		*/
		// 3. Process through step 1 of the adding new company
		home.click(Xpath.LINK_COMPANY);
		// Click on Add Company link
		home.click(Companies.ADD_COMPANY);
		// 4. Navigate to step 2 "Contact Person" of "Create Company" process
		Hashtable<String, String> dataCompany = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), dataCompany);
		// 5. Fill valid values into required fields
		Hashtable<String, String> dataUser = TestData.partnerUser();
		dataUser.remove("save");
		home.addUser(AddUser.getPartnerUser(), dataUser);
		// 6. Assign a site privilege for new user
		home.disableAllPrivilege(AddUser.PRIVILEGES_TABLE);
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		/*
		 * Verify that The similar site notification of assigned site privilege is enabled
		 */
		Assert.assertTrue(home.isNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info));
		// Delete company
		home.click(Xpath.LINK_COMPANY);
		home.selectACompanyByName(dataCompany.get("name"));
		home.doDelete(CompanyInfo.DELETE);
	}
	
	/*
	 * Verify that the similar site notification is also be disabled when disabling a site privilege
	 */
	@Test
	public void TC092DbCUN_10() {
		result.addLog("ID TC092DbCUN_10 : Verify that the similar site notification is also be disabled when disabling a site privilege");
		/*
	  		1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Process through step 1 of the adding new conpany
			4. Navigate to step 2 "Contact Person" of "Create Company" process
			5. Fill valid values into required fields
			6. Assign a site privilege for new user
			VP: The similar site notification is also checked
			7. Disable above site privilege
		*/
		// 3. Process through step 1 of the adding new company
		home.click(Xpath.LINK_COMPANY);
		// Click on Add Company link
		home.click(Companies.ADD_COMPANY);
		// 4. Navigate to step 2 "Contact Person" of "Create Company" process
		Hashtable<String, String> dataCompany = TestData.validCompany();
		home.addCompany(AddCompany.getHash(), dataCompany);
		// 5. Fill valid values into required fields
		Hashtable<String, String> dataUser = TestData.partnerUser();
		dataUser.remove("save");
		home.addUser(AddUser.getPartnerUser(), dataUser);
		// 6. Assign a site privilege for new user
		home.disableAllPrivilege(AddUser.PRIVILEGES_TABLE);
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		/*
		 * VP: The similar site notification is also checked
		 */
		Assert.assertTrue(home.isNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info));
		// 7. Disable above site privilege
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		/*
		 * Verify that The similar site notification of assigned site privilege is also disabled
		 */
		Assert.assertFalse(home.isNotificationCheckboxEnable(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info));
		// Delete company
		home.click(Xpath.LINK_COMPANY);
		home.selectACompanyByName(dataCompany.get("name"));
		home.doDelete(CompanyInfo.DELETE);
	}
	
	/*
	 * Verify that invitation message is sent to the invited user
	 */
	@Test
	public void TC092DbCUN_11() {
		result.addLog("ID TC092DbCUN_11 : Verify that invitation message is sent to the invited user");
		/*
	  		Pre-condition: DTS user has "Add and Manage DTS Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign at least one site privilege
			7. Click "Save" link
			VP: The "Success" page is displayed.
			8. Log out DTS portal
			9. Open invited user's mailbox which is used to register user
		*/
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server, dts_email, email_password);
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// Delete user if exist
		home.deleteUserByEmail(dts_email);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill valid values into required fields
		// 6. Assign at least one site privilege
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.dtsUser();
		data.put("email", dts_email);
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * VP: The "Success" page is displayed
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// 8. Log out DTS portal
		// 9. Open invited user's mailbox which is used to register user
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, dts_email, email_password, messageCount);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, dts_email, email_password);
		/*
		 * Verify that The invitation message is sent to user
		 */
		Assert.assertEquals(emailTitle, invite_message_title);
		// Delete user
		home.click(Xpath.LINK_USERS);
		home.dtsSelectUserByEmail(data.get("email"));
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*
	 * Verify that browser redirects to DTS activation page when clicking on invitation link
	 */
	@Test
	public void TC092DbCUN_12() {
		result.addLog("ID TC092DbCUN_12 : Verify that browser redirects to DTS activation page when clicking on invitation link");
		/*
	  		Pre-condition: DTS user has "Add and Manage DTS Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign at least one site privilege
			7. Click "Save" link
			VP: The "Success" page is displayed.
			8. Log out DTS portal
			9. Open invited user's mailbox which is used to register user
			VP. Verify that the invitation email is sent to user
			10. Open the invitation email
			VP: The invitation email contains activation link
			11. Click on invitation link
		*/
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server, dts_email, email_password);
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// Delete user if exist
		home.deleteUserByEmail(dts_email);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill valid values into required fields
		// 6. Assign at least one site privilege
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.dtsUser();
		data.put("email", dts_email);
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * VP: The "Success" page is displayed
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// 8. Log out DTS portal
		// 9. Open invited user's mailbox which is used to register user
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, dts_email, email_password, messageCount);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, dts_email, email_password);
		/*
		 * Verify that The invitation message is sent to user
		 */
		Assert.assertEquals(emailTitle, invite_message_title);
		// 10. Open the invitation email
		String link_active = MailUtil.getLinkActive(yahoo_imap_server, dts_email, email_password);
		/*
		 * VP: The invitation email contains activation link
		 */
		Assert.assertNotNull(link_active);
		// 11. Click on invitation link
		driver.get(link_active);
		/*
		 * Verify that The DTS activation page is launched with “Email”,
		 * “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		/*
		 *  Delete user
		 */
		// Navigate to home page
		driver.get(siteBase.toString());
		home.waitForAjax();
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select DTS user above
		home.dtsSelectUserByEmail(data.get("email"));
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*
	 * Verify that DTS User can be activated successfully
	 */
	@Test
	public void TC092DbCUN_13() {
		result.addLog("ID TC092DbCUN_13 : Verify that DTS User can be actived successfully");
		/*
	  		Pre-condition: DTS user has "Add and Manage DTS Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign at least one site privilege
			7. Click "Save" link
			VP: The "Success" page is displayed.
			8. Log out DTS portal
			9. Open invited user's mailbox which is used to register user
			VP. Verify that the invitation email is sent to user
			10. Open the invitation email
			VP: The invitation email contains activation link
			11. Click on invitation link
			VP: The DTS activation page is launched with “Email”, “Password”, and “Confirm Password” fields
			12. Fill valid value into all fields
			13. Click on Sign In link
		*/
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server, dts_email, email_password);
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// Delete user if exist
		home.deleteUserByEmail(dts_email);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill valid values into required fields
		// 6. Assign at least one site privilege
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.dtsUser();
		data.put("email", dts_email);
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * VP: The "Success" page is displayed
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// 8. Log out DTS portal
		// 9. Open invited user's mailbox which is used to register user
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, dts_email, email_password, messageCount);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, dts_email, email_password);
		/*
		 * Verify that The invitation message is sent to user
		 */
		Assert.assertEquals(emailTitle, invite_message_title);
		// 10. Open the invitation email
		String link_active = MailUtil.getLinkActive(yahoo_imap_server, dts_email, email_password);
		/*
		 * VP: The invitation email contains activation link
		 */
		Assert.assertNotNull(link_active);
		// 11. Click on invitation link
		driver.get(link_active);
		/*
		 * Verify that The DTS activation page is launched with “Email”,
		 * “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		// 12. Fill valid value into all fields
		// 13. Click on Sign In link	
		home.activeUser(dts_email, new_active_user_password);
		/*
		 * Verify that The portal is redirected to Home Page and the activated user is already signed in
		 */
		Assert.assertEquals(home.existsElement(DTSHome.getElementInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(DTSHome.SIGNIN_EMAIL), dts_email);
		/*
		 *  Delete user
		 */
		home.logout();
		// Log in DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select DTS user above
		home.dtsSelectUserByEmail(data.get("email"));
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*
	 * Verify that DTS User cannot be activated when the value of Password and Confirm Password fields are not matched
	 */
	@Test
	public void TC092DbCUN_14() {
		result.addLog("ID TC092DbCUN_14 : Verify that DTS User can be activated successfully");
		/*
	  		Pre-condition: DTS user has "Add and Manage DTS Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign at least one site privilege
			7. Click "Save" link
			VP: The "Success" page is displayed.
			8. Log out DTS portal
			9. Open invited user's mailbox which is used to register user
			VP. Verify that the invitation email is sent to user
			10. Open the invitation email
			VP: The invitation email contains activation link
			11. Click on invitation link
			VP: The DTS activation page is launched with “Email”, “Password”, and “Confirm Password” fields
			12. Fill valid value into all fields but the value of Password is not matched with Confirm Password
			13. Click on Sign In link
		*/
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server,	dts_email, email_password);
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// Delete user if exist
		home.deleteUserByEmail(dts_email);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill valid values into required fields
		// 6. Assign at least one site privilege
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.dtsUser();
		data.put("email", dts_email);
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * VP: The "Success" page is displayed
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// 8. Log out DTS portal
		// 9. Open invited user's mailbox which is used to register user
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, dts_email, email_password, messageCount);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, dts_email, email_password);
		/*
		 * Verify that The invitation message is sent to user
		 */
		Assert.assertEquals(emailTitle, invite_message_title);
		// 10. Open the invitation email
		String link_active = MailUtil.getLinkActive(yahoo_imap_server, dts_email, email_password);
		/*
		 * VP: The invitation email contains activation link
		 */
		Assert.assertNotNull(link_active);
		// 11. Click on invitation link
		driver.get(link_active);
		/*
		 * Verify that The DTS activation page is launched with “Email”,
		 * “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		// 12. Fill valid value into all fields but the value of Password is not matched with Confirm Password
		home.editData(LoginPage.USERNAME, data.get("email"));
		home.editData(LoginPage.PASSWORD, data.get(new_active_user_password));
		home.editData(LoginPage.CONFIRM_PASSWORD, email_password);
		// 13. Click on Sign In link
		home.click(LoginPage.SIGN_IN);
		/*
		 * Verify that An error message “Password does not matched” displays and
		 * DTS User is not activated successfully
		 */
		Assert.assertTrue(home.checkMessageDisplay(LoginPage.errMessage[2]));
		/*
		 *  Delete user
		 */
		// Navigate to home page
		driver.get(siteBase.toString());
		home.waitForAjax();
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select DTS user above
		home.dtsSelectUserByEmail(data.get("email"));
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*
	 * Verify that DTS User cannot be activated when the registered email is not correct
	 */
	@Test
	public void TC092DbCUN_15() {
		result.addLog("ID TC092DbCUN_15 : Verify that DTS User cannot be activated when the registered email is not correct");
		/*
	  		Pre-condition: DTS user has "Add and Manage DTS Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign at least one site privilege
			7. Click "Save" link
			VP: The "Success" page is displayed.
			8. Log out DTS portal
			9. Open invited user's mailbox which is used to register user
			VP. Verify that the invitation email is sent to user
			10. Open the invitation email
			VP: The invitation email contains activation link
			11. Click on invitation link
			VP: The DTS activation page is launched with “Email”, “Password”, and “Confirm Password” fields
			12. Fill valid value into all fields but the registered email is not correct
			13. Click on Sign In link
		*/
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server,dts_email, email_password);
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// Delete user if exist
		home.deleteUserByEmail(dts_email);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill valid values into required fields
		// 6. Assign at least one site privilege
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.dtsUser();
		data.put("email", dts_email);
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * VP: The "Success" page is displayed
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// 8. Log out DTS portal
		// 9. Open invited user's mailbox which is used to register user
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, dts_email,email_password, messageCount);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server,dts_email, email_password);
		/*
		 * Verify that The invitation message is sent to user
		 */
		Assert.assertEquals(emailTitle, invite_message_title);
		// 10. Open the invitation email
		String link_active = MailUtil.getLinkActive(yahoo_imap_server,dts_email, email_password);
		/*
		 * VP: The invitation email contains activation link
		 */
		Assert.assertNotNull(link_active);
		// 11. Click on invitation link
		driver.get(link_active);
		/*
		 * Verify that The DTS activation page is launched with “Email”,
		 * “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		// 12. Fill valid value into all fields but the registered email is not correct
		// 13. Click on Sign In link
		home.activeUser(dtsUser, new_active_user_password);
		/*
		 * Verify that An error message “Cannot active account, the link may be not valid or expried” displays and DTS User is not activated successfully		
		 */
		Assert.assertTrue(home.checkMessageDisplay(LoginPage.errMessage[0]));
		/*
		 * Delete user
		 */
		// Navigate to home page
		driver.get(siteBase.toString());
		home.waitForAjax();
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select DTS user above
		home.dtsSelectUserByEmail(data.get("email"));
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*
	 * Verify that the activation link is expired after the registered user is activated
	 */
	@Test
	public void TC092DbCUN_16() {
		result.addLog("ID TC092DbCUN_16 : Verify that the activation link is expired after the registered user is activated");
		/*
	  		Pre-condition: DTS user has "Add and Manage DTS Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign at least one site privilege
			7. Click "Save" link
			VP: The "Success" page is displayed.
			8. Log out DTS portal
			9. Open invited user's mailbox which is used to register user
			VP. Verify that the invitation email is sent to user
			10. Open the invitation email
			VP: The invitation email contains activation link
			11. Click on invitation link
			VP: The DTS activation page is launched with “Email”, “Password”, and “Confirm Password” fields
			12. Fill valid value into all fields
			13. Click on Sign In link
			VP: The portal is redirected to Home Page and the activated user is already signed in
			14. Repeat from step 8 to 13
		*/
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server,dts_email, email_password);
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// Delete user if exist
		home.deleteUserByEmail(dts_email);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill valid values into required fields
		// 6. Assign at least one site privilege
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.dtsUser();
		data.put("email", dts_email);
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 *  VP: The "Success" page is displayed
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// 8. Log out DTS portal
		// 9. Open invited user's mailbox which is used to register user
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, dts_email,email_password, messageCount);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server,dts_email, email_password);
		/*
		 *  VP: Verify that The invitation message is sent to user
		 */
		Assert.assertEquals(emailTitle, invite_message_title);
		// 10. Open the invitation email
		String link_active = MailUtil.getLinkActive(yahoo_imap_server,dts_email, email_password);
		 /*
		  *  VP: The invitation email contains activation link
		  */
		Assert.assertNotNull(link_active);
		// 11. Click on invitation link
		driver.get(link_active);
		/*
		 *  Verify that The DTS activation page is launched with “Email”, “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		// 12. Fill valid value into all fields
		// 13. Click on Sign In link
		home.activeUser(dts_email, new_active_user_password);
		/*
		 * Verify that The portal is redirected to Home Page and the activated
		 * user is already signed in
		 */
		Assert.assertEquals(home.existsElement(DTSHome.getElementInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(DTSHome.SIGNIN_EMAIL), dts_email);
		// 14. Repeat from step 8 to 13
		driver.get(link_active);
		// Fill valid value into all fields
		// Click on Sign In link
		home.activeUser(dts_email, new_active_user_password);
		/*
		 * Verify that An error message “Cannot active account, the link may be not valid or expried” displays
		 */
		Assert.assertTrue(home.checkMessageDisplay(LoginPage.errMessage[0]));
		/*
		 * Delete user
		 */
		// Navigate to home page
		driver.get(siteBase.toString());
		home.waitForAjax();
		// Log out
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select DTS user above
		home.dtsSelectUserByEmail(data.get("email"));
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*
	 * Verify that user status changes to “Active” after the activation is successful
	 */
	@Test
	public void TC092DbCUN_17() {
		result.addLog("ID TC092DbCUN_17 : Verify that user status changes to “Active” after the activation is successful");
		/*
	  		Pre-condition: DTS user has "Add and Manage DTS Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign at least one site privilege
			7. Click "Save" link
			VP: The "Success" page is displayed.
			8. Log out DTS portal
			9. Open invited user's mailbox which is used to register user
			VP. Verify that the invitation email is sent to user
			10. Open the invitation email
			VP: The invitation email contains activation link
			11. Click on invitation link
			VP: The DTS activation page is launched with “Email”, “Password”, and “Confirm Password” fields
			12. Fill valid value into all fields
			13. Click on Sign In link
			VP: The portal is redirected to Home Page and the activation user is already signed in
			14. Click on DTS user's email on top right corner
			15. Select “User Account” item
		*/
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server,dts_email, email_password);
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// Delete user if exist
		home.deleteUserByEmail(dts_email);
		// 4. Click "Add New User" link
		home.click(UsersList.ADD_USER);
		// 5. Fill valid values into required fields
		// 6. Assign at least one site privilege
		// 7. Click "Save" link
		Hashtable<String, String> data = TestData.dtsUser();
		data.put("email", dts_email);
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * VP: The "Success" page is displayed
		 */
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[0]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[1]));
		Assert.assertTrue(home.checkMessageDisplay(AddUser.Success_Message[2]));
		// 8. Log out DTS portal
		// 9. Open invited user's mailbox which is used to register user
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, dts_email,email_password, messageCount);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server,dts_email, email_password);
		/*
		 * Verify that The invitation message is sent to user
		 */
		Assert.assertEquals(emailTitle, invite_message_title);
		// 10. Open the invitation email
		String link_active = MailUtil.getLinkActive(yahoo_imap_server,dts_email, email_password);
		/*
		 * VP: The invitation email contains activation link
		 */
		Assert.assertNotNull(link_active);
		// 11. Click on invitation link
		driver.get(link_active);
		/*
		 * Verify that The DTS activation page is launched with “Email”,
		 * “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		// 12. Fill valid value into all fields
		// 13. Click on Sign In link
		home.activeUser(dts_email, new_active_user_password);
		/*
		 * Verify that The portal is redirected to Home Page and the activation
		 * user is already signed in
		 */
		Assert.assertEquals(home.existsElement(DTSHome.getElementInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(DTSHome.SIGNIN_EMAIL), dts_email);
		// 14. Click on DTS user's email on top right corner
		home.click(DTSHome.SIGNIN_EMAIL);
		// 15. Select “User Account” item
		home.click(DTSHome.USER_ACCOUNT);
		/*
		 * Verify that The portal is redirected to User Info page and the user status is changed to “Active”
		 */
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(UserMgmt.STATUS), "Active");
		/*
		 * Delete user
		 */
		home.logout();
		// Log into DTS portal as DTS super user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select DTS user above
		home.dtsSelectUserByEmail(data.get("email"));
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*
	 * Verify that a reset password email is sent when user clicks on reset password link
	 */
	@Test
	public void TC092DbCUN_18() {
		result.addLog("ID TC092DbCUN_18 : Verify that a reset password email is sent when user clicks on reset password link");
		/*
	  		Pre-condition: DTS user has "Add and Manage DTS Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a DTS User on User table
			VP: The Reset Password link is displayed on User Info page
			5. Click on Reset Password link
			6. Log out DTS portal
			7. Open user mailbox that use to reset password
		*/
		/*
		 * Pre-condition: Create new DTS user
		 */
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server,dts_email, email_password);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Delete user if exist
		home.deleteUserByEmail(dts_email);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String, String> data = TestData.dtsUser();
		data.put("email", dts_email);
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * ************************************************
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS User on User table
		home.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: The Reset Password link is displayed on User Info page
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.RESET_PASSWORD));
		// 5. Click on Reset Password link
		home.click(UserMgmt.RESET_PASSWORD);
		home.selectConfirmationDialogOption("Reset");
		// 6. Log out DTS portal
		// 7. Open user mailbox that use to reset password
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, dts_email, email_password, messageCount + 1);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, dts_email, email_password);
		/*
		 * Verify that A reset password email is sent to user
		 */
		Assert.assertTrue(emailTitle.contains("Reset password"));
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*
	 * Verify that browser redirects to DTS activation page when clicking on reset link in reset password email
	 */
	@Test
	public void TC092DbCUN_19() {
		result.addLog("ID TC092DbCUN_19 : Verify that browser redirects to DTS activation page when clicking on reset link in reset password email");
		/*
	  		Pre-condition: DTS user has "Add and Manage DTS Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a DTS User on User table
			VP: The Reset Password link is displayed on User Info page
			5. Click on Reset Password link
			6. Log out DTS portal
			7. Open user mailbox that has use to reset password
			VP: A reset password email is sent to user.
			8. Open reset password email
			VP: The reset password email contains reset password link
			9. Click on reset password link
		*/
		/*
		 * Pre-condition: Create new DTS user
		 */
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server,dts_email, email_password);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Delete user if exist
		home.deleteUserByEmail(dts_email);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String, String> data = TestData.dtsUser();
		data.put("email", dts_email);
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * ************************************************
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS User on User table
		home.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: The Reset Password link is displayed on User Info page
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.RESET_PASSWORD));
		// 5. Click on Reset Password link
		home.click(UserMgmt.RESET_PASSWORD);
		home.selectConfirmationDialogOption("Reset");
		// 6. Log out DTS portal
		// 7. Open user mailbox that use to reset password
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, dts_email, email_password, messageCount + 1);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, dts_email, email_password);
		/*
		 * Verify that A reset password email is sent to user
		 */
		Assert.assertTrue(emailTitle.contains("Reset password"));
		// 8. Open reset password email
		String link_reset = MailUtil.getLinkActive(yahoo_imap_server, dts_email, email_password);
		/*
		 * VP: The reset password email contains reset password link
		 */
		Assert.assertNotNull(link_reset);
		// 9. Click on reset password link
		driver.get(link_reset);
		/*
		 * Verify that The DTS activation page is launched with “Email”,
		 * “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		/*
		 *  Delete user
		 */
		// Navigate to home page
		driver.get(siteBase.toString());
		home.waitForAjax();
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select DTS user above
		home.dtsSelectUserByEmail(data.get("email"));
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*
	 * Verify that DTS User can reset password successfully
	 */
	@Test
	public void TC092DbCUN_20() {
		result.addLog("ID TC092DbCUN_20 : Verify that DTS User can reset password successfully");
		/*
	  		Pre-condition: DTS user has "Add and Manage DTS Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select an DTS User on User table
			VP: The Reset Password link is displayed on User Info page
			5. Click on Reset Password link
			6. Log out DTS portal
			7. Open user mailbox that has use to reset password
			VP: A reset password email is sent to user.
			8. Open reset password email
			VP: The reset password email contains reset password link
			9. Click on reset password link
			VP: The DTS activation page is launched with “Email”, “Password”, and “Confirm Password” fields.
			10. Fill valid value into all fields
			11. Click on Sign In link
		*/
		/*
		 * Pre-condition: Create new DTS user
		 */
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server,dts_email, email_password);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Delete user if exist
		home.deleteUserByEmail(dts_email);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String, String> data = TestData.dtsUser();
		data.put("email", dts_email);
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * ************************************************
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS User on User table
		home.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: The Reset Password link is displayed on User Info page
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.RESET_PASSWORD));
		// 5. Click on Reset Password link
		home.click(UserMgmt.RESET_PASSWORD);
		home.selectConfirmationDialogOption("Reset");
		// 6. Log out DTS portal
		// 7. Open user mailbox that use to reset password
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, dts_email, email_password, messageCount + 1);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, dts_email, email_password);
		/*
		 * Verify that A reset password email is sent to user
		 */
		Assert.assertTrue(emailTitle.contains("Reset password"));
		// 8. Open reset password email
		String link_reset = MailUtil.getLinkActive(yahoo_imap_server, dts_email, email_password);
		/*
		 * VP: The reset password email contains reset password link
		 */
		Assert.assertNotNull(link_reset);
		// 9. Click on reset password link
		driver.get(link_reset);
		/*
		 * Verify that The DTS activation page is launched with “Email”,
		 * “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		// 10. Fill valid value into all fields
		// 11. Click on Sign In link
		home.activeUser(dts_email, new_active_user_password);
		/*
		 * Verify that User password is reset successfully and the portal is redirected to Home Page
		 */
		Assert.assertEquals(home.existsElement(DTSHome.getElementInfo()).getResult(), "Pass");
		/*
		 *  Delete user
		 */
		home.logout();
		// Log in DTS portal as DTS super user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select DTS user above
		home.dtsSelectUserByEmail(data.get("email"));
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*
	 * Verify that the reset password link is expired after user resets password successfully
	 */
	@Test
	public void TC092DbCUN_21() {
		result.addLog("ID TC092DbCUN_21 : Verify that the reset password link is expired after user resets password successfully");
		/*
	  		Pre-condition: DTS user has "Add and Manage DTS Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select a DTS User on User table
			VP: The Reset Password link is displayed on User Info page
			5. Click on Reset Password link
			6. Log out DTS portal
			7. Open user mailbox that has use to reset password
			VP: A reset password email is sent to user.
			8. Open reset password email
			VP: The reset password email contains reset password link
			9. Click on reset password link
			VP: The DTS activation page is launched with “Email”, “Password”, and “Confirm Password” fields.
			10. Fill valid value into all fields
			11. Click on Sign In link
			VP: User password is reset successfully and the portal is redirected to Home Page
			12. Repeat from step 6 to 11
		*/
		/*
		 * Pre-condition: Create new DTS user
		 */
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server,dts_email, email_password);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Delete user if exist
		home.deleteUserByEmail(dts_email);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String, String> data = TestData.dtsUser();
		data.put("email", dts_email);
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * ************************************************
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS User on User table
		home.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: The Reset Password link is displayed on User Info page
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.RESET_PASSWORD));
		// 5. Click on Reset Password link
		home.click(UserMgmt.RESET_PASSWORD);
		home.selectConfirmationDialogOption("Reset");
		// 6. Log out DTS portal
		// 7. Open user mailbox that use to reset password
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, dts_email, email_password, messageCount + 1);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, dts_email, email_password);
		/*
		 * Verify that A reset password email is sent to user
		 */
		Assert.assertTrue(emailTitle.contains("Reset password"));
		// 8. Open reset password email
		String link_reset = MailUtil.getLinkActive(yahoo_imap_server, dts_email, email_password);
		/*
		 * VP: The reset password email contains reset password link
		 */
		Assert.assertNotNull(link_reset);
		// 9. Click on reset password link
		driver.get(link_reset);
		/*
		 * Verify that The DTS activation page is launched with “Email”,
		 * “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		// 10. Fill valid value into all fields
		// 11. Click on Sign In link
		home.activeUser(dts_email, new_active_user_password);
		/*
		 * Verify that User password is reset successfully and the portal is redirected to Home Page
		 */
		Assert.assertEquals(home.existsElement(DTSHome.getElementInfo()).getResult(), "Pass");
		// 12. Repeat from step 6 to 11
		driver.get(link_reset);
		// Fill valid value into all fields
		// Click on Sign In link
		home.activeUser(dts_email, new_active_user_password);
		/*
		 * Verify that An error message “Cannot reset password, the link may be not valid or expried” displays
		 */
		Assert.assertTrue(home.checkMessageDisplay(LoginPage.errMessage[1]));
		/*
		 * Delete user
		 */
		// Navigate to home page
		driver.get(siteBase.toString());
		home.waitForAjax();
		// Log out
		home.logout();
		// Log into DTS portal as DTS super user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select DTS user above
		home.dtsSelectUserByEmail(data.get("email"));
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*
	 * Verify that user cannot reset password when the value of Password and Confirm Password fields are not matched
	 */
	@Test
	public void TC092DbCUN_22() {
		result.addLog("ID TC092DbCUN_22 : Verify that user cannot reset password when the value of Password and Confirm Password fields are not matched");
		/*
	  		Pre-condition: DTS user has "Add and Manage DTS Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select an Active DTS User on User table
			VP: The Reset Password link is displayed on User Info page
			5. Click on Reset Password link
			6. Log out DTS portal
			7. Open user mailbox that has use to reset password
			VP: A reset password email is sent to user.
			8. Open reset password email
			VP: The reset password email contains reset password link
			9. Click on reset password link
			VP: The DTS activation page is launched with “Email”, “Password”, and “Confirm Password” fields.
			10. Fill valid value into all fields but the value of Password is not matched with Confirm Password
			11. Click on Sign In link
		*/
		/*
		 * Pre-condition: Create new DTS user
		 */
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server,dts_email, email_password);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Delete user if exist
		home.deleteUserByEmail(dts_email);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String, String> data = TestData.dtsUser();
		data.put("email", dts_email);
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * ************************************************
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS User on User table
		home.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: The Reset Password link is displayed on User Info page
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.RESET_PASSWORD));
		// 5. Click on Reset Password link
		home.click(UserMgmt.RESET_PASSWORD);
		home.selectConfirmationDialogOption("Reset");
		// 6. Log out DTS portal
		// 7. Open user mailbox that use to reset password
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, dts_email, email_password, messageCount + 1);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, dts_email, email_password);
		/*
		 * Verify that A reset password email is sent to user
		 */
		Assert.assertTrue(emailTitle.contains("Reset password"));
		// 8. Open reset password email
		String link_reset = MailUtil.getLinkActive(yahoo_imap_server, dts_email, email_password);
		/*
		 * VP: The reset password email contains reset password link
		 */
		Assert.assertNotNull(link_reset);
		// 9. Click on reset password link
		driver.get(link_reset);
		/*
		 * Verify that The DTS activation page is launched with “Email”,
		 * “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		// 10. Fill valid value into all fields but the value of Password is not matched with Confirm Password
		home.editData(LoginPage.USERNAME, dts_email);
		home.editData(LoginPage.PASSWORD, new_active_user_password);
		home.editData(LoginPage.CONFIRM_PASSWORD, email_password);
		// 11. Click on Sign In link
		home.click(LoginPage.SIGN_IN);
		/*
		 * Verify that An error message “Password does not matched” displays and the password is not reset successfully
		 */
		Assert.assertTrue(home.checkMessageDisplay(LoginPage.errMessage[2]));
		/*
		 *  Delete user
		 */
		// Navigate to home page
		driver.get(siteBase.toString());
		home.waitForAjax();
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select DTS user above
		home.dtsSelectUserByEmail(data.get("email"));
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*
	 * Verify that user cannot reset password when the registered email is not correct
	 */
	@Test
	public void TC092DbCUN_23() {
		result.addLog("ID TC092DbCUN_23 : Verify that user cannot reset password when the registered email is not correct");
		/*
	  		Pre-condition: DTS user has "Add and Manage DTS Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select an Active DTS User on User table
			VP: The Reset Password link is displayed on User Info page
			5. Click on Reset Password link
			6. Log out DTS portal
			7. Open user mailbox that has use to reset password
			VP: A reset password email is sent to user.
			8. Open reset password email
			VP: The reset password email contains reset password link
			9. Click on reset password link
			VP: The DTS activation page is launched with “Email”, “Password”, and “Confirm Password” fields.
			10. Fill valid value into all fields but the registered email is not correct
			11. Click on Sign In link
		*/
		/*
		 * Pre-condition: Create new DTS user
		 */
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server,dts_email, email_password);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Delete user if exist
		home.deleteUserByEmail(dts_email);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String, String> data = TestData.dtsUser();
		data.put("email", dts_email);
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * ************************************************
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS User on User table
		home.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: The Reset Password link is displayed on User Info page
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.RESET_PASSWORD));
		// 5. Click on Reset Password link
		home.click(UserMgmt.RESET_PASSWORD);
		home.selectConfirmationDialogOption("Reset");
		// 6. Log out DTS portal
		// 7. Open user mailbox that use to reset password
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, dts_email, email_password, messageCount + 1);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, dts_email, email_password);
		/*
		 * Verify that A reset password email is sent to user
		 */
		Assert.assertTrue(emailTitle.contains("Reset password"));
		// 8. Open reset password email
		String link_reset = MailUtil.getLinkActive(yahoo_imap_server, dts_email, email_password);
		/*
		 * VP: The reset password email contains reset password link
		 */
		Assert.assertNotNull(link_reset);
		// 9. Click on reset password link
		driver.get(link_reset);
		/*
		 * Verify that The DTS activation page is launched with “Email”,
		 * “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		// 10. Fill valid value into all fields but the registered email is not correct
		// 11. Click on Sign In link
		home.activeUser(partneruser, new_active_user_password);
		/*
		 * Verify that An error message “Cannot reset password, the link may be not valid or expried” displays and the password is not reset successfully
		 */
		Assert.assertTrue(home.checkMessageDisplay(LoginPage.errMessage[1]));
		// Navigate to home page
		driver.get(siteBase.toString());
		home.waitForAjax();
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select DTS user above
		home.dtsSelectUserByEmail(data.get("email"));
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*
	 * Verify that user status changes to “Active” after resetting password successfully
	 */
	@Test
	public void TC092DbCUN_24() {
		result.addLog("ID TC092DbCUN_24 : Verify that user status changes to “Active” after resetting password successfully");
		/*
	  		Pre-condition: DTS user has "Add and Manage DTS Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Select an DTS User on User table
			VP: The Reset Password link is displayed on User Info page
			5. Click on Reset Password link
			6. Log out DTS portal
			7. Open user mailbox that has use to reset password
			VP: A reset password email is sent to user.
			8. Open reset password email
			VP: The reset password email contains reset password link
			9. Click on reset password link
			VP: The DTS activation page is launched with “Email”, “Password”, and “Confirm Password” fields.
			10. Fill valid value into all fields
			11. Click on Sign In link
			VP: User password is reset successfully and the portal is redirected to Home Page
			12. Click on DTS user's email on top right corner
			13. Select “User Account” item
		*/
		/*
		 * Pre-condition: Create new DTS user
		 */
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(yahoo_imap_server,dts_email, email_password);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Delete user if exist
		home.deleteUserByEmail(dts_email);
		// Click Add new user link
		home.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String, String> data = TestData.dtsUser();
		data.put("email", dts_email);
		home.addUser(AddUser.getDTSUser(), data);
		/*
		 * ************************************************
		 */
		// 3. Click "Users" tab
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS User on User table
		home.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: The Reset Password link is displayed on User Info page
		 */
		Assert.assertTrue(home.isElementPresent(UserMgmt.RESET_PASSWORD));
		// 5. Click on Reset Password link
		home.click(UserMgmt.RESET_PASSWORD);
		home.selectConfirmationDialogOption("Reset");
		// 6. Log out DTS portal
		// 7. Open user mailbox that use to reset password
		MailUtil.waitForNewInboxMessage(yahoo_imap_server, dts_email, email_password, messageCount + 1);
		String emailTitle = MailUtil.getNewEmailSubject(yahoo_imap_server, dts_email, email_password);
		/*
		 * Verify that A reset password email is sent to user
		 */
		Assert.assertTrue(emailTitle.contains("Reset password"));
		// 8. Open reset password email
		String link_reset = MailUtil.getLinkActive(yahoo_imap_server, dts_email, email_password);
		/*
		 * VP: The reset password email contains reset password link
		 */
		Assert.assertNotNull(link_reset);
		// 9. Click on reset password link
		driver.get(link_reset);
		/*
		 * Verify that The DTS activation page is launched with “Email”,
		 * “Password”, and “Confirm Password” fields
		 */
		Assert.assertEquals(home.existsElement(LoginPage.getActivationInfoPage()).getResult(), "Pass");
		// 10. Fill valid value into all fields
		// 11. Click on Sign In link
		home.activeUser(dts_email, new_active_user_password);
		/*
		 * Verify that User password is reset successfully and the portal is redirected to Home Page
		 */
		Assert.assertEquals(home.existsElement(DTSHome.getElementInfo()).getResult(), "Pass");
		// 12. Click on DTS user's email on top right corner
		home.click(DTSHome.SIGNIN_EMAIL);
		// 13. Select “User Account” item
		home.click(DTSHome.USER_ACCOUNT);
		/*
		 * Verify that The portal is redirected to User Info page and the user status is changed to “Active”
		 */
		Assert.assertEquals(home.existsElement(UserMgmt.getElementsInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(UserMgmt.STATUS), "Active");
		/*
		 *  Delete user
		 */
		home.logout();
		// Log in DTS portal as DTS super user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select DTS user above
		home.dtsSelectUserByEmail(data.get("email"));
		// Delete user
		home.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
}
