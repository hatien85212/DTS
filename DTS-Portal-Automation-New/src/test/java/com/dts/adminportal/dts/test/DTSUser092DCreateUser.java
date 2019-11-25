package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddBrand;
import com.dts.adminportal.model.AddCompany;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.Companies;
import com.dts.adminportal.model.CompanyInfo;
import com.dts.adminportal.model.DTSHome;
import com.dts.adminportal.model.DTSSitePrivilege;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PageLogin;
import com.dts.adminportal.model.PartnerHomePage;
import com.dts.adminportal.model.PartnerUserMgmt;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.UserEdit;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.model.UsersList;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.MailUtil;
import com.dts.adminportal.util.TestData;

public class DTSUser092DCreateUser extends BasePage{
	
	String invite_message_title = "Your Invitation to DTS Headphone:X Partner Portal";
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
	}
	
	/*
	 Verify that the similar privileges are validated when the privilege is selected or unselected
	 */
	@Test
	public void TC092DaCUB_01(){
		userControl.addLog("ID : TC092DaCUB_01 : Verify that the similar privileges are validated when the privilege is selected or unselected");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid value into all required fields
			6. Select "DTS Inc." item in "Company" field
			7. Disable any privilege
			8. Enable back above privilege

		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Users" tab
		userControl.click(PageHome.LINK_USERS);
		// 4. Click "Add New User" link
		userControl.click(UsersList.ADD_USER);
		// 5. Fill valid value into all required fields
		// 6. Select "DTS Inc." item in "Company" field
		Hashtable<String,String> data = TestData.dtsUser();
		data.remove("save");
		userControl.addUser(AddUser.getDTSUser(), data);
		/* 
		  	"VP:All Site and DTS Site Privileges are enabled.
			VP: The site privileges have following rights:
			Add and manage Products
			Publish and suspend Products
			Request product tunings
			Add and manage devices
			Publish and suspend devices
			Add and manage apps
			Publish and suspend apps
			Edit Company Info
			Edit brand info
			Add and manage users
			VP:The DTS Site Privilege is displayed with folowing options:
			Approve product tunings
			Approve marketing info
			Manage publication credit
			Add and manage promotions
			Add and manage company acccounts
			Add and mange DTS users"

		 */
		ArrayList<String> privileges = userControl.getPrivileges(AddUser.DTS_PRIVILEGES_TABLE);
		Assert.assertTrue(ListUtil.containsAll(privileges, Privileges.dtsPrivileges.getNames()));
		//7. Disable any privilege
		ArrayList<DTSSitePrivilege> list = userControl.getDTSSitePrivileges(AddUser.DTS_PRIVILEGES_TABLE);
		Assert.assertTrue(list.size() > 0);
		userControl.enableDTSSitePrivilege(list.get(0));
		/*
		 * The similar DTS site privilege notification is enabled.
		 */
		Assert.assertTrue(userControl.isCheckBoxEnable(list.get(0).notifications));
		// Un-select a DTS site privilege
		userControl.disableDTSSitePrivilege(list.get(0));
		/*
		 * The similar DTS site privilege notification is disabled.
		 */
		Assert.assertFalse(userControl.isCheckBoxEnable(list.get(0).notifications));
		// VP: The similar DTS site privilege notification is disabled.

		//8. Enable back above privilege
		userControl.enableDTSSitePrivilege(list.get(0));
		// The similar DTS site privilege notification is enabled.
		Assert.assertTrue(userControl.isCheckBoxEnable(list.get(0).notifications));
		

	}
	/*
	 Verify that the "Given Name", "Family Name", "Email", "Company" and "Phone" are required when creating new users
	 */
	@Test
	public void TC092DaCUB_04(){
		userControl.addLog("ID : TC092DaCUB_04 : Verify that the 'Given Name', 'Family Name', 'Email', 'Company' and 'Phone' are required when creating new users");
		
		/*
			Pre-condition: User has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Click "Save" link without filling information into  
			"Given Name", "Family Name", "Email", "Company" and "Phone"
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//3. Click "Users" tab
		userControl.click(PageHome.LINK_USERS);
		// 4. Click "Add New User" link
		userControl.click(UsersList.ADD_USER);
		userControl.selectOptionByName(AddUser.BTN_COMPANY, "Select Company");
		// 5. Click "Save"
		userControl.click(AddUser.SAVE);
		/*
		 * There is an error message  displayed next to 
		 * "Given Name", 
		 * "Family Name", 
		 * "Email", 
		 * "Company" and 
		 * "Phone" 
		 * which mentions to requirement of information.
		 */
		Assert.assertTrue(userControl.isElementPresent(AddUser.GIVEN_NAME_MESSAGE));
		Assert.assertTrue(userControl.isElementPresent(AddUser.FAMILY_NAME_MESSAGE));
		Assert.assertTrue(userControl.isElementPresent(AddUser.EMAIL_MESSAGE));
		Assert.assertTrue(userControl.isElementPresent(AddUser.COMPANY_MESSAGE));
		Assert.assertTrue(userControl.isElementPresent(AddUser.PHONE_MESSAGE));
	}
	/*
	 Verify that new user could not be created without selecting company.
	 */
	@Test
	public void TC092DaCUB_06(){
		userControl.addLog("ID : TC092DaCUB_06 : Verify that new user could not be created without selecting company.");
		
		/*
			Pre-condition: User has "Add and Manage Users" rights.

			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill all valid values into required fields
			6. Do not select company
			7. Click "Save" link
			VP: There is an error message displayed which mentions requirement of selecting company.
			8. Change email address which is assigned to another user's account 
			9. Click "Save" link
			VP: There is an error message displayed which mentions to an already exists email address. The message look like "An account with this email address already exists"

		 */
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Users" tab
		userControl.click(PageHome.LINK_USERS);
		// 4. Click "Add New User" link
		userControl.click(UsersList.ADD_USER);
		// 5. Fill all valid values into required fields
		// 6. Do not select company
		// 7. Click "Save" link
		Hashtable<String, String> usertest = TestData.dtsUser();
		usertest.remove("company");
		usertest.put("company", "Select Company");
		usertest.remove("brand");
		userControl.addUser(AddUser.getDTSUser(), usertest);
		/*
		 VP: There is an error message displayed which mentions requirement of selecting company.
		 */
		Assert.assertTrue(userControl.isElementPresent(AddUser.COMPANY_MESSAGE));
		//8. Change email address which is assigned to another user's account 
		userControl.editData(AddUser.EMAIL, DTS_USER);
		//9. Click "Save" link
		userControl.click(AddUser.SAVE);
		/*
			There is an error message displayed which mentions to an already exists email address. 
			The message look like "An account with this email address already exists"
		*/
		Assert.assertEquals(userControl.getTextByXpath(AddUser.EMAIL_MESSAGE),AddUser.messages.Account_Existed.getName());
	}
	/*
	 * Verify that the user could not add new user with invalid email address format
	 */
	@Test
	public void TC092DaCUB_10(){
		userControl.addLog("ID : TC092DaCUB_10 : Verify that the user could not add new user with invalid email address format");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill all valid values into required fields except Email
			6. Click "Save" link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Users" tab
		userControl.click(PageHome.LINK_USERS);
		// 4. Click "Add New User" link
		userControl.click(UsersList.ADD_USER);
		// 5. Fill all valid values into required fields except Email
		Hashtable<String,String> data = TestData.dtsUser();
		data.put("email", RandomStringUtils.randomAlphanumeric(20) + ".example.com");
		data.remove("save");
		userControl.addUser(AddUser.getDTSUser(), data);
		// 6. Click "Save" link
		userControl.click(AddUser.SAVE);
		/*
		 * Verify that There is an error message displayed which mention to the incorrect format of email address
		 */
		Assert.assertTrue(userControl.checkMessageDisplay(UserEdit.ERROR_EMAIL));
	}
	
	/*
	 * Verify that new user could be created without assigning site notifications
	 */
	@Test
	public void TC092DaCUB_13(){
		userControl.addLog("ID : TC092DaCUB_13 : Verify that new user could be created without assigning site notifications");
		/*
			Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click "Add New User" link
			5. Fill valid values into required fields
			6. Assign any site privilege for new user
			7. Do not assign any site notification
			8. Click "Save" link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		//3. Click "Users" tab
		userControl.click(PageHome.LINK_USERS);
		// 4. Click "Add New User" link
		userControl.click(UsersList.ADD_USER);
		// 5. Fill valid values into required fields
		// 6. Assign any site privilege for new user
		Hashtable<String,String> data = TestData.partnerUser();
		data.remove("save");
		userControl.addUser(AddUser.getDTSUser(), data);
		// 7. Do not assign any site notification
		userControl.uncheckAllNotification(AddUser.PRIVILEGES_TABLE);
		// 8. Click "Save" link
		userControl.click(AddUser.SAVE);
		/*
		 * Verify that New user could be created successfully without assigning site notification
		 */
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.messages.Success.getName()));
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.messages.Success_Email_Info.getName()));
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.messages.Success_Email_Invite.getName()));
		// Delete user
		userControl.click(PageHome.LINK_USERS);
		userControl.deleteUserByEmail(data.get("email"));
	}
	/*
	 * Verify that the brand privileges are displayed correctly when editing user
	 */
	@Test
	public void TC092DaCUB_16(){
		userControl.addLog("ID : TC092DaCUB_16 : Verify that the brand privileges are displayed correctly when editing user");
		/*
			Pre-condition: partner user has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Click "Users" tab
			4. Click ï¿½Add New Userï¿½ link
			5. Fill valid value into all required fields
			6. Assign a specific brand for each user's privileges
			7. Click "Save" link
			VP: Verify that new user is created successfully
			8. Navigate to ï¿½Usersï¿½ page again
			9. Select the new user from Users table
			10. Click ï¿½Editï¿½ link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Click "Users" tab
		userControl.click(PageHome.LINK_USERS);
		// 4. Click ï¿½Add New Userï¿½ link
		userControl.click(UsersList.ADD_USER);
		// 5. Fill valid value into all required fields
		// 6. Assign a specific brand for each user's privileges
		// 7. Click "Save" link
		Hashtable<String,String> data = TestData.dtsUser();
		userControl.addUser(AddUser.getDTSUser(), data);
		/*
		 * VP: Verify that new user is created successfully
		 */
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.messages.Success.getName()));
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.messages.Success_Email_Info.getName()));
		Assert.assertTrue(userControl.checkMessageDisplay(AddUser.messages.Success_Email_Invite.getName()));
		// 8. Navigate to ï¿½Usersï¿½ page again
		userControl.click(PageHome.LINK_USERS);
		// 9. Select the new user from Users table
		userControl.dtsSelectUserByEmail(data.get("email"));
		// 10. Click ï¿½Editï¿½ link
		userControl.click(UserMgmt.EDIT);
		/*
		 * Verify that The assigned brands for each privilege of user are displayed correctly
		 */
		Assert.assertTrue(userControl.isBrandPrivilegeSelected(AddUser.BRAND_PRIVILEGES, data.get("brand")));
		// Delete user
		userControl.click(AddUser.CANCEL);
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	/*
	 * Verify that step 2 "Contact Person" of Create Company process is validated correctly
	 */
	@Test
	public void TC092DbCUN_01() {
		userControl.addLog("ID TC092DbCUN_01 : Verify that step 2 'Contact Person' of Create Company process is validated correctly");
		/*
		  	Pre-condition: User has "Add and Manage Users" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Process through step 1 of the adding new conpany
			4. Navigate to step 2 "Contact Person" of "Create Company" process
			VP: The company name is displayed as read only
			VP: The ""Cancel"" link is in step 2 ""Contact Person"" is displayed.
			VP: Site Privileges options are auto enabled "
			5. Click "Save" link without filling information into  "Given Name", "Family Name", "Email"
			VP: There is an error message  displayed next to ""Given Name"", ""Family Name"", ""Email"" which mentions to requirement of information.
			6. Fill all valid values into required fields except Email"
			7. Click "Save" link
			VP: There is an error message displayed which mention to the incorrect format of email address.
		*/
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Process through step 1 of the adding new company
		userControl.click(PageHome.LINK_COMPANY);
		// Click on Add Company link
		userControl.click(Companies.ADD_COMPANY);
		// 4. Navigate to step 2 "Contact Person" of "Create Company" process
		Hashtable<String,String> data = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), data);	
		// Check if PDPP-1185 found
//		if(!userControl.isElementPresent(AddUser.BTN_COMPANY)){
//			userControl.addErrorLog("PDPP-1185: 092Db New User Account Form: The known company context is not displayed when adding new partner companies");
//			Assert.assertTrue(false);
//		}
		/*
		 * 	VP: Verify that The company name is displayed as read only
		 */
//		Assert.assertEquals(userControl.getItemSelected(AddUser.BTN_COMPANY), data.get("name"));
		Assert.assertTrue(companyControl.getTextByXpath(AddUser.BTN_COMPANY).contains(data.get("name")));
		Assert.assertFalse(userControl.isElementEditable(AddUser.BTN_COMPANY));
		/*
		 *	 VP: Verify that the "Cancel" link is hidden in step 2 "Contact Person" of "Create Company" process
		 */
		Assert.assertFalse(userControl.isElementPresent(AddUser.CANCEL));
		/*
		 * Verify that Site Privileges options are selected 
		 */
		Assert.assertTrue(userControl.isAllPrivilegeSelected(AddUser.PRIVILEGES_TABLE));
		// 5. Click "Save" link without filling information into  "Given Name", "Family Name", "Email"
		userControl.click(AddUser.SAVE);
		/*
		  	VP: There is an error message  displayed next to ""Given Name"", ""Family Name"", ""Email"" which mentions to requirement of information.
		 */
		Assert.assertTrue(userControl.isElementPresent(AddUser.GIVEN_NAME_MESSAGE));
		Assert.assertTrue(userControl.isElementPresent(AddUser.FAMILY_NAME_MESSAGE));
		Assert.assertTrue(userControl.isElementPresent(AddUser.EMAIL_MESSAGE));
		// 6. Fill all valid values into required fields except Email"
		// 7. Click "Save" link
		Hashtable<String,String> dataUser = TestData.partnerUser();
		dataUser.remove("save");
		dataUser.remove("email");
		dataUser.remove("company");
		dataUser.put("email", RandomStringUtils.randomAlphanumeric(20) + ".example.com");
		userControl.addUser(AddUser.getPartnerUser(), dataUser);
		userControl.click(AddUser.SAVE);
		/*
		 * Verify that There is an error message displayed which mention to the incorrect format of email address
		 */
		Assert.assertTrue(userControl.checkMessageDisplay(UserEdit.ERROR_EMAIL));			
		// Delete company
		userControl.click(PageHome.LINK_COMPANY);
		companyControl.selectACompanyByName(data.get("name"));
		userControl.doDelete(CompanyInfo.DELETE);
		
	}
	/*
	 * Verify that user is able to log into partner portal after finishing  step 2 "Contact Person" of Create Company process after validating for activation
	 */
	@Test
	public void TC092DbCUN_11() {
		userControl.addLog("ID TC092DbCUN_11 : Verify that user is able to log into partner portal after finishing  step 2 'Contact Person' of Create Company process after validating for activation");
		/*
	  		1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Process through step 1 of the adding new conpany
			4. Navigate to step 2 "Contact Person" of "Create Company" process
			5. Fill valid values into required fields
			6. Assign a site privilege for new user
			7. Click "Save" link	
			8. Log out DTS portal
			
			9. Open invited user's mailbox which is used to register user
			VP: The invitation message is sent to user
			
			10. Open the invitation email
			VP: The invitation email contains activation link
			
			11. Click on invitation link
			VP: The DTS activation page is launched with “Email”, “Password”, and “Confirm Password” fields.
			
			12. Fill valid value into all fields but the value of Password is not matched with Confirm Password
			13. Click on Sign In link
			VP: An error message “Password does not matched” displays and DTS User is not activated successfully
			
			14. Fill valid value into all fields but the registered email is not correct
			15. Click on Sign In link
			VP: An error message “Cannot active account, the link may be not valid or expried” displays and DTS User is not activated successfully
			
			16. Fill valid value into all fields
			17. Click on Sign In link
			VP: The portal is redirected to Home Page and the activation user is already signed in
			
			18. Click on Partner user's email on top right corner
			19. Select “User Account” item
			VP: The portal is redirected to User Info page and the user status is changed to “Active”.
		
			20. Repeat from step 9 to 11 and from 16 to 17 again
			VP: An error message “Cannot active account, the link may be not valid or expried” displays

		*/
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get email message count before create company user
		int messageCount = MailUtil.getEmailMessageCount(YAHOO_IMAP_SERVER, DTS_EMAIL, EMAIL_PASSWORD);
		// Click "Users" tab
		userControl.click(PageHome.LINK_USERS);
		
//		// (Delete)/Edit user if exist
//		boolean exist = userControl.checkUserExistByEmail(DTS_EMAIL);
//		if(exist)
//		{
//			//Edit email of personal contact
//			userControl.dtsSelectUserByEmail(DTS_EMAIL);
//			userControl.click(UserInfo.EDIT);
//			Hashtable<String,String> editUser = TestData.dtsUser();
//			userControl.editData(UserEdit.EMAIL,editUser.get("email"));
//			userControl.click(UserEdit.SAVE);
//			//userControl.deleteUserByEmail(DTS_EMAIL);	
//		}
		userControl.changeEmailOfUser(DTS_EMAIL);
		// 3. Process through step 1 of the adding new company
		userControl.click(PageHome.LINK_COMPANY);
		// Click on Add Company link
		userControl.click(Companies.ADD_COMPANY);
		// 4. Navigate to step 2 "Contact Person" of "Create Company" process
		Hashtable<String,String> data = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), data);	
		Assert.assertTrue(companyControl.getTextByXpath(AddUser.BTN_COMPANY).contains(data.get("name")));
		// 5. Fill valid values into required fields
		// 6. Assign at least one site privilege
		// 7. Click "Save" link
		Hashtable<String,String> datauser = TestData.dtsUser();
		datauser.put("email", DTS_EMAIL);
		datauser.remove("company");
		userControl.addUser(AddUser.getDTSUser(), datauser);
//		// Check if PDPP-1185 found
//		if(!userControl.isElementPresent(AddUser.BTN_COMPANY)){
//			userControl.addErrorLog("PDPP-1185: 092Db New User Account Form: The known company context is not displayed when adding new partner companies");
//			Assert.assertTrue(false);
//		}
		Hashtable<String, String> brand = TestData.brandDraft();
		//brand.remove("alias");
		brand.remove("cancel");
		companyControl.addBrand(AddBrand.getHash(),brand);
		// 8. Log out DTS portal
		loginControl.logout();
		// 9. Open invited user's mailbox which is used to register user 
		MailUtil.waitForNewInboxMessage(YAHOO_IMAP_SERVER, DTS_EMAIL, EMAIL_PASSWORD, messageCount);
		String emailTitle = MailUtil.getNewEmailSubject(YAHOO_IMAP_SERVER, DTS_EMAIL, EMAIL_PASSWORD);	
		/*
		 * Verify that The invitation message is sent to user
		 */
		Assert.assertEquals(emailTitle, invite_message_title);
		// 10. Open the invitation email
		String link_active = MailUtil.getLinkActive(YAHOO_IMAP_SERVER, DTS_EMAIL, EMAIL_PASSWORD);
		/*
		 * VP: The invitation email contains activation link
		 */
		Assert.assertNotNull(link_active);
		// 11. Click on invitation link
		driver.get(link_active);
		/*
		 * Verify that The DTS activation page is launched with "Email","Password" and "Confirm Password" fields
		 */
		Assert.assertEquals(userControl.existsElement(PageLogin.getActivationInfoPage()), true);
		// 12. Fill valid value into all fields but the value of Password is not matched with Confirm Password
		userControl.editData(PageLogin.USERNAME, datauser.get("email"));
		userControl.editData(PageLogin.PASSWORD, NEW_ACTIVE_USER_PASSWORD);
		userControl.editData(PageLogin.CONFIRM_PASSWORD, EMAIL_PASSWORD);
		// 13. Click on Sign In link
		userControl.click(PageLogin.SIGN_IN);
		/*
		 * Verify that An error message "Password does not matched" displays and
		 * DTS User is not activated successfully
		 */
		Assert.assertTrue(userControl.checkMessageDisplay(PageLogin.errMessage.Password_does_not_matched.getName()));
		// 14. Fill valid value into all fields but the registered email is not correct
		// 15. Click on Sign In link
		userControl.activeUser(DTS_USER, NEW_ACTIVE_USER_PASSWORD);
		/*
		 	VP: An error message “Cannot activate the account. We don't recognize this email” displays and DTS User is not activated successfully		
		 */
		Assert.assertTrue(userControl.checkMessageDisplay(PageLogin.errMessage.Cannot_recognize_this_email.getName()));
		
		// 16. Fill valid value into all fields
		// 17. Click on Sign In link	
		userControl.activeUser(DTS_EMAIL, NEW_ACTIVE_USER_PASSWORD);
		/*
		 * Verify that The portal is redirected to Home Page and the activated user is already signed in
		 */
		Assert.assertEquals(userControl.existsElement(PartnerHomePage.getElementInfo()), true);
		Assert.assertEquals(userControl.getTextByXpath(DTSHome.SIGNIN_EMAIL), DTS_EMAIL);
		// 18. Click on Partner user's email on top right corner
		userControl.click(DTSHome.SIGNIN_EMAIL);
		// 19. Select "User Account" item
		userControl.click(DTSHome.USER_ACCOUNT);
		/*
		 * Verify that The portal is redirected to User Info page and the user status is changed to ï¿½Activeï¿½
		 */
		Assert.assertEquals(userControl.existsElement(PartnerUserMgmt.getElementsInfo()), true);
		Assert.assertEquals(userControl.getTextByXpath(UserMgmt.STATUS), "Active");
		
		// 20. Repeat from step 9 to 11 and from 16 to 17 again
		// Log out DTS portal
		loginControl.logout();
			// 9. Open invited user's mailbox which is used to register user 	
			// 10. Open the invitation email
			// 11. Click on invitation link
		driver.get(link_active);
			// 16. Fill valid value into all fields
			// 17. Click on Sign In link	
		userControl.activeUser(DTS_EMAIL, NEW_ACTIVE_USER_PASSWORD);
		//VP: An error message “Cannot active account, the link may be not valid or expried” displays
		Assert.assertTrue(userControl.checkMessageDisplay(PageLogin.errMessage.Cannot_active_account.getName()));
		
//		// Delete user
//		userControl.click(PageHome.LINK_USERS);
//		userControl.dtsSelectUserByEmail(data.get("email"));
//		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
	}
	
	/*
	 * Verify that a reset password email is sent when user clicks on reset password link
	 */
	@Test
	public void TC092DbCUN_18() {
		userControl.addLog("ID TC092DbCUN_18 : Verify that a reset password email is sent when user clicks on reset password link");
		/*
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
			
			10. Fill valid value into all fields but the value of Password is not matched with Confirm Password
			11. Click on Sign In link
			VP: An error message “Password does not matched” displays and the password is not reset successfully
			
			12. Fill valid value into all fields but the registered email is not correct
			13. Click on Sign In link
			VP: An error message “Cannot reset password, the link may be not valid or expried” displays and the password is not reset successfully
			
			14. Fill valid value into all fields
			15. Click on Sign In link
			VP: User password is reset successfully and the portal is redirected to Home Page
			
			16. Click on DTS user's email on top right corner
			17. Select “User Account” item
			VP: The portal is redirected to User Info page and the user status is changed to “Active”.
			
			18. Log out partner portal
			19. Repeat from step 7 to 9
			20. Fill valid value into all fields
			21. Click on Sign In link
			An error message “Cannot reset password, the link may be not valid or expried” displays

		*/
		// 2. Log into DTS portal as a DTS user successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		/*
		 * Pre-condition: Create new DTS user
		 */
		// Get email message count before create user
		int messageCount = MailUtil.getEmailMessageCount(YAHOO_IMAP_SERVER,DTS_EMAIL, EMAIL_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Rename email if exist
		userControl.changeEmailOfUser(DTS_EMAIL);
		userControl.click(PageHome.LINK_USERS);
		// Click Add new user link
		userControl.click(UsersList.ADD_USER);
		// Create user
		Hashtable<String, String> data = TestData.dtsUser();
		data.remove("email");
		data.put("email", DTS_EMAIL);
		userControl.addUser(AddUser.getDTSUser(), data);
		/*
		 * ************************************************
		 */
		// 3. Click "Users" tab
		userControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS User on User table
		userControl.dtsSelectUserByEmail(data.get("email"));
		/*
		 * VP: The Reset Password link is displayed on User Info page
		 */
		Assert.assertTrue(userControl.isElementPresent(UserMgmt.RESET_PASSWORD));
		// 5. Click on Reset Password link
		userControl.click(UserMgmt.RESET_PASSWORD);
		userControl.selectConfirmationDialogOption("Reset");
		// 6. Log out DTS portal
		// 7. Open user mailbox that use to reset password
		MailUtil.waitForNewInboxMessage(YAHOO_IMAP_SERVER, DTS_EMAIL, EMAIL_PASSWORD, messageCount + 1);
		String emailTitle = MailUtil.getNewEmailSubject(YAHOO_IMAP_SERVER, DTS_EMAIL, EMAIL_PASSWORD);
		/*
		 * Verify that A reset password email is sent to user
		 */
		Assert.assertTrue(emailTitle.contains("Reset password"));
		// 8. Open reset password email
		String link_reset = MailUtil.getLinkActive(YAHOO_IMAP_SERVER, DTS_EMAIL, EMAIL_PASSWORD);
		/*
		 * VP: The reset password email contains reset password link
		 */
		Assert.assertNotNull(link_reset);
		// 9. Click on reset password link
		driver.get(link_reset);
		/*
		 * Verify that The DTS activation page is launched with "Email",
		 * "Password" and "Confirm Password" fields
		 */
		Assert.assertEquals(userControl.existsElement(PageLogin.getActivationInfoPage()), true);
		// 10. Fill valid value into all fields but the value of Password is not matched with Confirm Password
		userControl.editData(PageLogin.USERNAME, DTS_EMAIL);
		userControl.editData(PageLogin.PASSWORD, NEW_ACTIVE_USER_PASSWORD);
		userControl.editData(PageLogin.CONFIRM_PASSWORD, EMAIL_PASSWORD);
		// 11. Click on Sign In link
		userControl.click(PageLogin.SIGN_IN);
		/*
		 * Verify that An error message ï¿½Password does not matchedï¿½ displays and the password is not reset successfully
		 */
		Assert.assertTrue(userControl.checkMessageDisplay(PageLogin.errMessage.Password_does_not_matched.getName()));
		// 12. Fill valid value into all fields but the registered email is not correct
		// 13. Click on Sign In link
		userControl.activeUser(PARTNER_USER, NEW_ACTIVE_USER_PASSWORD);
		/*
		 * Verify that An error message ï¿½Cannot reset password, the link may be not valid or expriedï¿½ displays and the password is not reset successfully
		 */
		Assert.assertTrue(userControl.checkMessageDisplay(PageLogin.errMessage.Cannot_reset_password.getName()));
		// 14. Fill valid value into all fields
		// 15. Click on Sign In link
		userControl.activeUser(DTS_EMAIL, NEW_ACTIVE_USER_PASSWORD);
		/*
		 * Verify that User password is reset successfully and the portal is redirected to Home Page
		 */
		Assert.assertEquals(userControl.existsElement(DTSHome.getElementInfo()), true);		
		// 16. Click on DTS user's email on top right corner
		userControl.click(DTSHome.SIGNIN_EMAIL);
		// 17. Select "User Account" item
		userControl.click(DTSHome.USER_ACCOUNT);
		/*
		 * Verify that The portal is redirected to User Info page and the user status is changed to ï¿½Activeï¿½
		 */
		Assert.assertEquals(userControl.existsElement(UserMgmt.getElementsInfo()), true);
		Assert.assertEquals(userControl.getTextByXpath(UserMgmt.STATUS), "Active");		
	}
	
	/*
	 * Verify that DTS User tab is displayed properly
	 */
	@Test
	public void TC092DbCUN_19() {
		userControl.addLog("ID TC092DbCUN_12 : Verify that DTS User tab is displayed properly");
		/*	
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to "Company" page
			VP: The 060D company list page is displayed
			4. Click “Add Company” company link
			5. Finish step 1 of Create Company process successfully
			6. Click “DTS User” tab
			VP: Verify that “DTS User” tab is displayed properly
			VP: Verify that the “Save” link is displayed in “Contact” module
		
		*/
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Company" page
		companyControl.click(PageHome.LINK_COMPANY);
		// VP: The 060D company list page is displayed
		Assert.assertEquals(true, companyControl.existsElement(Companies.getListElements()));
		// 4. Click “Add Company” company link
		companyControl.click(Companies.ADD_COMPANY);
		// 5. Finish step 1 of Create Company process successfully
		Hashtable<String,String> data = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), data);
		// 6. Click “DTS User” tab
		companyControl.click(AddCompany.USER_PANEL);
		Assert.assertTrue(companyControl.existsElement(AddCompany.getUserTabInfo()));
		Assert.assertTrue(companyControl.isElementPresent(AddUser.SAVE));
		
	}
	
	/*
	 * Verify that DTS User tab is displayed properly
	 */
	@Test
	public void TC092DbCUN_20() throws InterruptedException {
		userControl.addLog("ID TC092DbCUN_13 : Verify that DTS User tab is displayed properly");
		/*	
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “Users” tab
			4. Click “Add New User” link
			5. Create new partner user successfully
			6. Navigate to “Company” tab
			7. Click “Add Company” company link
			8. Finish step 1 of Create Company process successfully
			9. Click “DTS User” tab
			10. Fill email of partner user which was registered above
			VP: Verify that don't have any user appear on combo-box
		
		*/
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to “Users” tab
		companyControl.click(PageHome.LINK_USERS);
		// 4. Click “Add New User” link
		userControl.click(UsersList.ADD_USER);
		// 5. Create new partner user successfully
		Hashtable<String,String> dataPartner = TestData.partnerUser();
		userControl.addUser(AddUser.getPartnerUser(), dataPartner);
		
//		int messageCount = MailUtil.getEmailMessageCount(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD);
//		MailUtil.waitForNewInboxMessage(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL, EMAIL_PASSWORD, messageCount);
//		userControl.activeNewUserViaEmail(YAHOO_IMAP_SERVER, PARTNER_DTS_EMAIL,EMAIL_PASSWORD, NEW_ACTIVE_USER_PASSWORD);
		
//		
//		userControl.activeUser(dataPartner.get("email"), BasePage.NEW_ACTIVE_USER_PASSWORD);
		// 6. Navigate to “Company” tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 7. Click “Add Company” company link
		companyControl.click(Companies.ADD_COMPANY);
		// 8. Finish step 1 of Create Company process successfully
		Hashtable<String,String> dataCompany = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), dataCompany);
		// 9. Click “DTS User” tab
		companyControl.click(AddCompany.USER_PANEL);
		// 10. Fill email of partner user which was registered above
		companyControl.editData(AddCompany.EMAIL_DTS_FILTER, dataPartner.get("email"));
		Thread.sleep(3000);
		// VP: Verify that don't have any user appear on combo-box
		Assert.assertFalse(companyControl.isElementPresent("//*[@id='ui-id-1']"));
		companyControl.click(PageHome.LINK_USERS);
		userControl.dtsSelectUserByEmail(dataPartner.get("email"));
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
		
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.selectACompanyByName(dataCompany.get("name"));
		companyControl.doDelete(CompanyInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user can appear on combo-box when enter it's email
	 */
	@Test
	public void TC092DbCUN_21() throws InterruptedException {
		userControl.addLog("ID TC092DbCUN_14 : Verify that the DTS user can appear on combo-box when enter it's email");
		/*	
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a DTS user successfully
			3. Navigate to “Users” tab
			4. Click “Add New User” link
			5. Create new  successfully
			6. Navigate to “Company” tab
			7. Click “Add Company” company link
			8. Finish step 1 of Create Company process successfully
			9. Click “DTS User” tab
			10. Fill first three characters of email which was registered above
			VP: Verify that DTS User above appear on combo-box 
			11. Empty input element
			12. Fill email of DTS user which was registered above
			VP: Verify that DTS User above appear on combo-box 
			13. Empty input element
			14. Fill email (change to upper-case character) which was registered above
			VP: Verify that DTS User above appear on combo-box 
		
		*/
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to “Users” tab
		companyControl.click(PageHome.LINK_USERS);
		// 4. Click “Add New User” link
		userControl.click(UsersList.ADD_USER);
		// 5. Create new partner user successfully
		Hashtable<String,String> dataDTSUser = TestData.dtsUser();
		userControl.addUser(AddUser.getPartnerUser(), dataDTSUser);
		String DTS_user = dataDTSUser.get("firstName") + " " + dataDTSUser.get("lastName") + " - " + dataDTSUser.get("email");
		// 6. Navigate to “Company” tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 7. Click “Add Company” company link
		companyControl.click(Companies.ADD_COMPANY);
		// 8. Finish step 1 of Create Company process successfully
		Hashtable<String,String> dataCompany = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), dataCompany);
		// 9. Click “DTS User” tab
		companyControl.click(AddCompany.USER_PANEL);
		// 10. Fill first three characters of email which was registered above
		companyControl.editData(AddCompany.EMAIL_DTS_FILTER, dataDTSUser.get("email").substring(0, 3));
		Thread.sleep(2000);
		// VP: Verify that DTS User above appear on combo-box 
//		Assert.assertTrue(companyControl.isElementPresent("//*[@id='ui-id-1']"));
		Assert.assertTrue(companyControl.getTextByXpath(AddCompany.COMBO_BOX).contains(DTS_user));
		// 11. Empty input element
		companyControl.editData(AddCompany.EMAIL_DTS_FILTER, "");
		// 12. Fill email of DTS user which was registered above
		companyControl.editData(AddCompany.EMAIL_DTS_FILTER, dataDTSUser.get("email"));
		Thread.sleep(2000);
		// VP: Verify that DTS User above appear on combo-box
		Assert.assertTrue(companyControl.getTextByXpath(AddCompany.COMBO_BOX).contains(DTS_user));
		// 13. Empty input element
		companyControl.editData(AddCompany.EMAIL_DTS_FILTER, "");
		// 14. Fill email (change to upper-case character) which was registered above
		companyControl.editData(AddCompany.EMAIL_DTS_FILTER, dataDTSUser.get("email").toUpperCase());
		// VP: Verify that DTS User above appear on combo-box
		Assert.assertTrue(companyControl.getTextByXpath(AddCompany.COMBO_BOX).contains(DTS_user));
		
		// Delete User
		companyControl.click(PageHome.LINK_USERS);
		userControl.dtsSelectUserByEmail(dataDTSUser.get("email"));
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
		// Delete Company
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.selectACompanyByName(dataCompany.get("name"));
		companyControl.doDelete(CompanyInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user can appear on combo-box when enter it's email
	 */
	@Test
	public void TC092DbCUN_22() throws InterruptedException {
		userControl.addLog("ID TC092DbCUN_15 : Verify that the DTS user can appear on combo-box when enter it's email");
		/*	
		 	1. Repeat step 1 to 9 of test case: 092DbNUA_03
			10. Fill first seven characters of first_name which was registered above
			VP: Verify that DTS User above appear on combo-box 
			11. Empty input element
			VP: Verify that don't have any user appear
			12. Fill first seven characters of last_name which was registered above
			VP: Verify that DTS User above appear on combo-box 
			13. Empty input element
			14. Fill first_name (change to upper-case character) of user which was registered
			VP: Verify that DTS User above appear on combo-box 
			15. Empty input element
			16. Fill last_name (change to lower-case character) of user which was registered
			VP: Verify that DTS User above appear on combo-box 

		*/
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to “Users” tab
		companyControl.click(PageHome.LINK_USERS);
		// 4. Click “Add New User” link
		userControl.click(UsersList.ADD_USER);
		// 5. Create new partner user successfully
		Hashtable<String,String> dataDTSUser = TestData.dtsUser();
		userControl.addUser(AddUser.getPartnerUser(), dataDTSUser);
		String DTS_user = dataDTSUser.get("firstName") + " " + dataDTSUser.get("lastName") + " - " + dataDTSUser.get("email");
		// 6. Navigate to “Company” tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 7. Click “Add Company” company link
		companyControl.click(Companies.ADD_COMPANY);
		// 8. Finish step 1 of Create Company process successfully
		Hashtable<String,String> dataCompany = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), dataCompany);
		// 9. Click “DTS User” tab
		companyControl.click(AddCompany.USER_PANEL);
		// 10. Fill first seven characters of first_name which was registered above
		companyControl.editData(AddCompany.EMAIL_DTS_FILTER, dataDTSUser.get("firstName").substring(0, 7));
		Thread.sleep(2000);
		// VP: Verify that DTS User above appear on combo-box
//		Assert.assertTrue(companyControl.getTextByXpath(AddCompany.COMBO_BOX).contains(dataDTSUser.get("email")));
		Assert.assertTrue(companyControl.getTextByXpath(AddCompany.COMBO_BOX).contains(DTS_user));
		// 11. Empty input element
		companyControl.editData(AddCompany.EMAIL_DTS_FILTER, "");
		Assert.assertTrue(companyControl.getTextByXpath(AddCompany.EMAIL_DTS_FILTER).contains(""));
		// 12. Fill first seven characters of last_name which was registered above
		companyControl.editData(AddCompany.EMAIL_DTS_FILTER, dataDTSUser.get("lastName").substring(0, 7));
		Thread.sleep(2000);
		// VP: Verify that DTS User above appear on combo-box 
		Assert.assertTrue(companyControl.getTextByXpath(AddCompany.COMBO_BOX).contains(DTS_user));
		// 13. Empty input element
		companyControl.editData(AddCompany.EMAIL_DTS_FILTER, "");
		// 14. Fill first_name (change to upper-case character) of user which was registered
		companyControl.editData(AddCompany.EMAIL_DTS_FILTER, dataDTSUser.get("firstName").toUpperCase());
		Thread.sleep(2000);
		// VP: Verify that DTS User above appear on combo-box 
		Assert.assertTrue(companyControl.getTextByXpath(AddCompany.COMBO_BOX).contains(DTS_user));
		// 15. Empty input element
		companyControl.editData(AddCompany.EMAIL_DTS_FILTER, "");
		// 16. Fill last_name (change to lower-case character) of user which was registered
		companyControl.editData(AddCompany.EMAIL_DTS_FILTER, dataDTSUser.get("lastName").toLowerCase());
		Thread.sleep(2000);
		// VP: Verify that DTS User above appear on combo-box 
		Assert.assertTrue(companyControl.getTextByXpath(AddCompany.COMBO_BOX).contains(DTS_user));
		// Delete User
		companyControl.click(PageHome.LINK_USERS);
		userControl.dtsSelectUserByEmail(dataDTSUser.get("email"));
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
		// Delete Company
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.selectACompanyByName(dataCompany.get("name"));
		companyControl.doDelete(CompanyInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user can appear on combo-box when enter it's email
	 */
	@Test
	public void TC092DbCUN_23() throws InterruptedException {
		userControl.addLog("ID TC092DbCUN_16 : Verify that the DTS user can appear on combo-box when enter it's email");
		/*	
		 	1. Repeat step 1 to 9 of test case: 092DbNUA_03
			10. Fill email of user which was registered above
			VP: Verify that DTS User above appear on combo-box 
			11. Choose email appear on combo-box
			VP: Verify that user appear on input element
			VP: Verify that close button appear on the right side of user

		*/
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to “Users” tab
		companyControl.click(PageHome.LINK_USERS);
		// 4. Click “Add New User” link
		userControl.click(UsersList.ADD_USER);
		// 5. Create new partner user successfully
		Hashtable<String,String> dataDTSUser = TestData.dtsUser();
		userControl.addUser(AddUser.getPartnerUser(), dataDTSUser);
		String DTS_user = dataDTSUser.get("firstName") + " " + dataDTSUser.get("lastName") + " - " + dataDTSUser.get("email");
		// 6. Navigate to “Company” tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 7. Click “Add Company” company link
		companyControl.click(Companies.ADD_COMPANY);
		// 8. Finish step 1 of Create Company process successfully
		Hashtable<String,String> dataCompany = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), dataCompany);
		// 9. Click “DTS User” tab
		companyControl.click(AddCompany.USER_PANEL);
		// 10. Fill email of user which was registered above
		companyControl.editData(AddCompany.EMAIL_DTS_FILTER, dataDTSUser.get("email"));
		Thread.sleep(2000);
		// VP: Verify that DTS User above appear on combo-box
		Assert.assertTrue(companyControl.getTextByXpath(AddCompany.COMBO_BOX).contains(DTS_user));
		// 11. Choose email appear on combo-box
		companyControl.click(AddCompany.COMBO_BOX_USER);
		// VP: Verify that user appear on input element
		Assert.assertTrue(companyControl.getTextByXpath(AddCompany.SELECTED_EMAIL).contains(DTS_user));
		// VP: Verify that close button appear on the right side of user
		Assert.assertTrue(companyControl.isElementPresent(AddCompany.ICON_REMOVE));
		
		// Delete User
		companyControl.click(PageHome.LINK_USERS);
		userControl.dtsSelectUserByEmail(dataDTSUser.get("email"));
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
		// Delete Company
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.selectACompanyByName(dataCompany.get("name"));
		companyControl.doDelete(CompanyInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user can appear on combo-box when enter it's email
	 */
	@Test
	public void TC092DbCUN_24() throws InterruptedException {
		userControl.addLog("ID TC092DbCUN_17 : Verify that the DTS user can appear on combo-box when enter it's email");
		/*	
		 	1. Repeat step 1 to 9 of test case: 092DbNUA_03
			10. Fill email of user which was registered above
			VP: Verify that DTS User above appear on combo-box 
			11. Choose email appear on combo-box
			VP: Verify that user appear on input element
			12. Click on close button on the right side 
			VP: Verify that input element is empty
		*/
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to “Users” tab
		companyControl.click(PageHome.LINK_USERS);
		// 4. Click “Add New User” link
		userControl.click(UsersList.ADD_USER);
		// 5. Create new partner user successfully
		Hashtable<String,String> dataDTSUser = TestData.dtsUser();
		userControl.addUser(AddUser.getPartnerUser(), dataDTSUser);
		String DTS_user = dataDTSUser.get("firstName") + " " + dataDTSUser.get("lastName") + " - " + dataDTSUser.get("email");
		// 6. Navigate to “Company” tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 7. Click “Add Company” company link
		companyControl.click(Companies.ADD_COMPANY);
		// 8. Finish step 1 of Create Company process successfully
		Hashtable<String,String> dataCompany = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), dataCompany);
		// 9. Click “DTS User” tab
		companyControl.click(AddCompany.USER_PANEL);
		// 10. Fill email of user which was registered above
		companyControl.editData(AddCompany.EMAIL_DTS_FILTER, dataDTSUser.get("email"));
		Thread.sleep(2000);
		// VP: Verify that DTS User above appear on combo-box
		Assert.assertTrue(companyControl.getTextByXpath(AddCompany.COMBO_BOX).contains(DTS_user));
		// 11. Choose email appear on combo-box
		companyControl.click(AddCompany.COMBO_BOX_USER);
		// VP: Verify that user appear on input element
		Assert.assertTrue(companyControl.getTextByXpath(AddCompany.SELECTED_EMAIL).contains(DTS_user));
		// 12. Click on close button on the right side 
		companyControl.click(AddCompany.ICON_REMOVE);
		// VP: Verify that input element is empty
		Assert.assertTrue(companyControl.checkEmptyByXpath(AddCompany.EMAIL_DTS_FILTER));
		
		// Delete User
		companyControl.click(PageHome.LINK_USERS);
		userControl.dtsSelectUserByEmail(dataDTSUser.get("email"));
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
		// Delete Company
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.selectACompanyByName(dataCompany.get("name"));
		companyControl.doDelete(CompanyInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user can appear on combo-box when enter it's email
	 */
	@Test
	public void TC092DbCUN_25() throws InterruptedException {
		userControl.addLog("ID TC092DbCUN_17 : Verify that the DTS user can appear on combo-box when enter it's email");
		/*	
		 	1. Repeat step 1 to 9 of test case: 092DbNUA_03
			10. Fill email of user which was registered above
			VP: Verify that DTS User above appear on combo-box 
			11. Choose email appear on combo-box
			VP: Verify that user appear on input element
			12. Click save 
			VP: Verify that step 2 "Contact Person" of Create Company process is completed
		*/
		
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to “Users” tab
		companyControl.click(PageHome.LINK_USERS);
		// 4. Click “Add New User” link
		userControl.click(UsersList.ADD_USER);
		// 5. Create new partner user successfully
		Hashtable<String,String> dataDTSUser = TestData.dtsUser();
		userControl.addUser(AddUser.getPartnerUser(), dataDTSUser);
		String DTS_user = dataDTSUser.get("firstName") + " " + dataDTSUser.get("lastName") + " - " + dataDTSUser.get("email");
		// 6. Navigate to “Company” tab
		companyControl.click(PageHome.LINK_COMPANY);
		// 7. Click “Add Company” company link
		companyControl.click(Companies.ADD_COMPANY);
		// 8. Finish step 1 of Create Company process successfully
		Hashtable<String,String> dataCompany = TestData.validCompany();
		companyControl.addCompany(AddCompany.getHash(), dataCompany);
		// 9. Click “DTS User” tab
		companyControl.click(AddCompany.USER_PANEL);
		// 10. Fill email of user which was registered above
		companyControl.editData(AddCompany.EMAIL_DTS_FILTER, dataDTSUser.get("email"));
		Thread.sleep(2000);
		// VP: Verify that DTS User above appear on combo-box
		Assert.assertTrue(companyControl.getTextByXpath(AddCompany.COMBO_BOX).contains(DTS_user));
		// 11. Choose email appear on combo-box
		companyControl.click(AddCompany.COMBO_BOX_USER);
		// VP: Verify that user appear on input element
		Assert.assertTrue(companyControl.getTextByXpath(AddCompany.SELECTED_EMAIL).contains(DTS_user));
		// 12. Click save 
		companyControl.click(AddUser.SAVE);
		// VP: Verify that step 2 "Contact Person" of Create Company process is completed
		Assert.assertTrue(companyControl.getTextByXpath(AddCompany.PROCESS_STEP_2).contains(AddCompany.COMPLETED));
		
		// Delete User
		companyControl.click(PageHome.LINK_USERS);
		userControl.dtsSelectUserByEmail(dataDTSUser.get("email"));
		userControl.doDelete(UserMgmt.DELETE_ACCOUNT);
		// Delete Company
		companyControl.click(PageHome.LINK_COMPANY);
		companyControl.selectACompanyByName(dataCompany.get("name"));
		companyControl.doDelete(CompanyInfo.DELETE);
	}
	
}