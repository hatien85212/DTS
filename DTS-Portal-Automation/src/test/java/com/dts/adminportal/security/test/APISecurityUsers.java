package com.dts.adminportal.security.test;

import junit.framework.Assert;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.security.util.APIData;
import com.dts.adminportal.security.util.APIUtil;
import com.dts.adminportal.util.CreatePage;

import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.Xpath;

public class APISecurityUsers extends CreatePage{
	private String csrfToken;
	private String cookie;
	private JSONObject jsonData;
	private HomeController home;
	
	@BeforeClass
	public void beforeTest(){
		home = new HomeController(driver, siteBase);
		jsonData = APIUtil.parseDataToJsonObject(APIData.JSON_DATA_FILE);
	}
	
	/*
	 * Verify that DTS user is unable to add partner user when "Add and manage User" is disabled and "Add and manage DTS user" is enabled
	 */
	//@SuppressWarnings("unchecked")
	@Test
	public void APIU_01(){
		result.addLog("ID : APIU_01 : Verify that DTS user is unable to add partner user when 'Add and manage User' is disabled and 'Add and manage DTS user' is enabled");
		/*
		 	Precondition: The "Add and manage User" is disabled and "Add and manage DTS user" is enabled

			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/account HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();

		// Create request body
		String requestBody = jsonData.get(APIData.ADD_PARTNER_USER).toString();

		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/account HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that DTS user is unable to edit partner user when "Add and manage User" is disabled and "Add and manage DTS user" is enabled
	 */
	//@SuppressWarnings("unchecked")
	@Test
	public void APIU_02(){
		result.addLog("ID : APIU_02 : Verify that DTS user is unable to edit partner user when 'Add and manage User' is disabled and 'Add and manage DTS user' is enabled");
		/*
		 	Precondition: The "Add and manage User" is disabled and "Add and manage DTS user" is enabled

			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/account HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();

		// Create request body
		String requestBody = jsonData.get(APIData.EDIT_PARTNER_USER).toString();

		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/account HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that DTS user is unable to add DTS user when "Add and manage User" is enabled and "Add and manage DTS user" is disabled
	 */
	//@SuppressWarnings("unchecked")
	@Test
	public void APIU_03(){
		result.addLog("ID : APIU_03 : Verify that DTS user is unable to add DTS user when 'Add and manage User' is enabled and 'Add and manage DTS user' is disabled");
		/*
		 	Precondition: Precondition: The "Add and manage User" is enabled and "Add and manage DTS user" is disabled

			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/account HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();

		// Create request body
		String requestBody = jsonData.get(APIData.ADD_DTS_USER).toString();

		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/account HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that DTS user is unable to edit DTS user when"Add and manage User" is enabled and "Add and manage DTS user" is disabled
	 */
	//@SuppressWarnings("unchecked")
	@Test
	public void APIU_04(){
		result.addLog("ID : APIU_04 : Verify that DTS user is unable to edit DTS user when 'Add and manage User' is enabled and 'Add and manage DTS user' is disabled");
		/*
		 	Precondition: Precondition: The "Add and manage User" is enabled and "Add and manage DTS user" is disabled

			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/account HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();

		// Create request body
		String requestBody = jsonData.get(APIData.EDIT_DTS_USER).toString();

		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/account HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that DTS user is unable to get users list when both "Add and manage User" and "Add and manage DTS user" are disabled:
	 */
	//@SuppressWarnings("unchecked")
	@Test
	public void APIU_05(){
		result.addLog("ID : APIU_05 : Verify that DTS user is unable to get users list when both 'Add and manage User' and 'Add and manage DTS user' are disabled:");
		/*
			Precondition: Both "Add and manage User" and "Add and manage DTS user" are disabled
			
			GET http://devportal.dts.com/saap/api/users HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();

		// Send a sample GET request as following:
		// POST http://devportal.dts.com/saap/api/account HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.GET_USER_LIST_URL, csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to add partner user when "Add and manage User" is disabled
	 */
	//@SuppressWarnings("unchecked")
	@Test
	public void APIU_06(){
		result.addLog("ID : APIU_06 : Verify that Partner user is unable to add partner user when 'Add and manage User' is disabled");
		/*
			Precondition: The "Add and manage User" privilege is disabled
			
			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/account HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();

		// Create request body
				String requestBody = jsonData.get(APIData.ADD_PARTNER_USER).toString();

		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/account HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to edit partner user when "Add and manage User" is disabled
	 */
	//@SuppressWarnings("unchecked")
	@Test
	public void APIU_07(){
		result.addLog("ID : APIU_07 : Verify that Partner user is unable to edit partner user when 'Add and manage User' is disabled");
		/*
			Precondition: The "Add and manage User" privilege is disabled
			
			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/account HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();

		// Create request body
				String requestBody = jsonData.get(APIData.EDIT_PARTNER_USER).toString();

		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/account HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to add DTS user when the "Add and manage User" privilege is enabled or disabled
	 */
	//@SuppressWarnings("unchecked")
	@Test
	public void APIU_08(){
		result.addLog("ID : APIU_08 : Verify that Partner user is unable to add DTS user when the 'Add and manage User' privilege is enabled or disabled");
		/*
		Precondition: The "Add and manage User" privilege is enabled or disabled
		
		Send a sample POST request using partner's session and CSRF token as following:
		POST http://devportal.dts.com/saap/api/account HTTP/1.1
		 */
		//When the "Add and manage User" privilege is enabled
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		home.enableAllPrivilege();
		//home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();

		// Create request body
		String requestBody = jsonData.get(APIData.ADD_DTS_USER).toString();

		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/account HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		
		// Logout user
		home.logout();
		//When the "Add and manage User" privilege is enabled
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();

		// Create request body
		String requestBody1 = jsonData.get(APIData.ADD_DTS_USER).toString();

		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/account HTTP/1.1
		JSONObject requestResult1 = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, requestBody1);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult1.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to edit DTS user when the "Add and manage User" privilege is enabled or disabled
	 */
	//@SuppressWarnings("unchecked")
	@Test
	public void APIU_09(){
		result.addLog("ID : APIU_09 : Verify that Partner user is unable to edit DTS user when the 'Add and manage User' privilege is enabled or disabled");
		/*
		Precondition: The "Add and manage User" privilege is enabled or disabled
		
		Send a sample POST request using partner's session and CSRF token as following:
		POST http://devportal.dts.com/saap/api/account HTTP/1.1
		 */
		//When the "Add and manage User" privilege is enabled
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		home.enableAllPrivilege();
		//home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();

		// Create request body
		String requestBody = jsonData.get(APIData.EDIT_DTS_USER).toString();

		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/account HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		
		// Logout user
		home.logout();
		//When the "Add and manage User" privilege is enabled
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();

		// Create request body
		String requestBody1 = jsonData.get(APIData.EDIT_DTS_USER).toString();

		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/account HTTP/1.1
		JSONObject requestResult1 = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, requestBody1);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult1.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to get detail of other user including user of DTS and other company when the "Add and manage User" privilege is enabled.
	 */
	//@SuppressWarnings("unchecked")
	@Test
	public void APIU_10(){
		result.addLog("ID : APIU_10 : Verify that Partner user is unable to get list of all user including user of DTS and other company when the 'Add and manage User' privilege is enabled.");
		/*
		Precondition: The "Add and manage User" privilege is enabled or disabled
		
		Send a sample POST request using partner's session and CSRF token as following:
		POST http://devportal.dts.com/saap/api/account HTTP/1.1
		 */
		//When the "Add and manage User" privilege is enabled
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		home.enableAllPrivilege();
		//home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();

		// send get request to get list users
		JSONObject requestResult = APIUtil.sendGet(APIData.GET_USER_LIST_URL, csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertFalse(requestResult.toString().contains(dtsUser));
		Assert.assertFalse(requestResult.toString().contains(partneruser));
	}
	
	/*
	 * Verify that Partner user is unable to get detail of other user including user of DTS and other company when the "Add and manage User" privilege is enabled.
	 */
	//@SuppressWarnings("unchecked")
	@Test
	public void APIU_11(){
		result.addLog("ID : APIU_11 : Verify that Partner user is unable to get detail of other user including user of DTS and other company when the 'Add and manage User' privilege is enabled.");
		/*
			Send a sample GET request using partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/users/34?time=1433846601848&userId=34 HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();

		// send get request to get list users
		JSONObject requestResult = APIUtil.sendGet(APIData.GET_USER_DETAIL_URL, csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner and DTS user are unable to change user own email address when both "Add and Manage User" and "Add and Manage DTS user" privileges are disabled.
	 */
	//@SuppressWarnings("unchecked")
	@Test
	public void APIU_12(){
		result.addLog("ID : APIU_12 : Verify that Partner and DTS user are unable to change user own email address when both 'Add and Manage User' and 'Add and Manage DTS user' privileges are disabled.");
		/*
			Send a sample GET request using partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/users/34?time=1433846601848&userId=34 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Change privilege for partner user
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		//Change privielege for dts user
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		home.click(AddUser.SAVE);
		//log out
		home.logout();
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();

		String requestBody = jsonData.get(APIData.EDIT_PARTNER_USER_OWN).toString();
		// send get request to get list users
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie,requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.toString(), APIData.ERROR_MESSAGE_CHANGE_USER_OWN_EMAIL);
		//log out
		home.logout();
		// Login DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();

		String requestBody1 = jsonData.get(APIData.EDIT_DTS_USER_OWN).toString();
		// send get request to get list users
		JSONObject requestResult1 = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie,requestBody1);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult1.toString(), APIData.ERROR_MESSAGE_CHANGE_USER_OWN_EMAIL);
	}
	
	/*
	 * Verify that Partner is unable to reset password of DTS user or user of another company when the "Add and manage users" privilege is enabled or disabled
	 */
	@Test
	public void APIU_13(){
		result.addLog("ID : APIU_13 : Verify that Partner is unable to reset password of DTS user or user of another company when the 'Add and manage users' privilege is enabled or disabled");
		/*
			Send a sample GET request using partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/resetUserPassWord/36 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Enable "Add and manage user" privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// Create new DTS user
		String dtsEmail = RandomStringUtils.randomAlphabetic(20) + "@mailinator.com";
		String dtsUserData = jsonData.get(APIData.ADD_DTS_USER).toString();
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "userEmail", dtsEmail);
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "email", dtsEmail);
		JSONObject newDTSUser = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, dtsUserData);
		// Get a company ID
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.OTHER_COMPANY_DATA).toString());
		String companyID = company.get("id").toString();
		// Create new Partner user
		String partnerEmail = RandomStringUtils.randomAlphabetic(20) + "@mailinator.com";
		String partnerUserData = jsonData.get(APIData.ADD_PARTNER_USER).toString();
		partnerUserData = APIUtil.replaceStringValue(partnerUserData, "partnerId", companyID);
		partnerUserData = APIUtil.replaceStringValue(partnerUserData, "userEmail", partnerEmail);
		partnerUserData = APIUtil.replaceStringValue(partnerUserData, "email", partnerEmail);
		JSONObject newPartnerUser = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, partnerUserData);
		// Log out
		home.logout();
		// Log into DTS portal as partner user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Send a sample GET request using partner's session and CSRF token as following:
		// GET http://devportal.dts.com/saap/api/resetUserPassWord/36 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.RESET_USER_PASSWORD_URL(newDTSUser.get("id").toString()), csrfToken, cookie);
		JSONObject requestResult1 = APIUtil.sendGet(APIData.RESET_USER_PASSWORD_URL(newPartnerUser.get("id").toString()), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"INTERNAL_ERROR","message":"Permission denied!"} 
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		Assert.assertEquals(requestResult1.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest("DELETE", APIData.DELETE_USER_URL(newDTSUser.get("id").toString()), csrfToken, cookie, "");
		APIUtil.sendHTTPRequest("DELETE",APIData.DELETE_USER_URL(newPartnerUser.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that DTS is unable to reset password of Partner when the "Add and manage users" privilege is disabled
	 */
	@Test
	public void APIU_14(){
		result.addLog("ID : APIU_14 : Verify that DTS is unable to reset password of Partner when the 'Add and manage users' privilege is disabled");
		/*
			Send a sample GET request using partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/resetUserPassWord/36 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage user" privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// Get a company ID
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.OTHER_COMPANY_DATA).toString());
		String companyID = company.get("id").toString();
		// Create new Partner user
		String partnerEmail = RandomStringUtils.randomAlphabetic(20) + "@mailinator.com";
		String partnerUserData = jsonData.get(APIData.ADD_PARTNER_USER).toString();
		partnerUserData = APIUtil.replaceStringValue(partnerUserData, "partnerId", companyID);
		partnerUserData = APIUtil.replaceStringValue(partnerUserData, "userEmail", partnerEmail);
		partnerUserData = APIUtil.replaceStringValue(partnerUserData, "email", partnerEmail);
		JSONObject newPartnerUser = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, partnerUserData);
		// Log out
		home.logout();
		// Log into DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Send a sample GET request using partner's session and CSRF token as following:
		// GET http://devportal.dts.com/saap/api/resetUserPassWord/36 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.RESET_USER_PASSWORD_URL(newPartnerUser.get("id").toString()), csrfToken, cookie);
		// Check if PDPP-1373 found
		if(requestResult == null){
			result.addErrorLog("PDPP-1373 - API Validation: api/resetUserPassWord: DTS user is able to reset password of a Partner user when \"Add and manage user\" privilege is disabled");
			Assert.assertTrue(false);
		}
		/*
		 * {"apiErrorCode":"INTERNAL_ERROR","message":"Permission denied!"} 
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest("DELETE", APIData.DELETE_USER_URL(newPartnerUser.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that DTS is unable to reset password of other DTS users when the "Add and manage DTS users" privilege is disabled
	 */
	@Test
	public void APIU_15(){
		result.addLog("ID : APIU_15 : Verify that DTS is unable to reset password of other DTS users when the 'Add and manage DTS users' privilege is disabled");
		/*
			Send a sample GET request using partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/resetUserPassWord/36 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage user" privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		home.click(AddUser.SAVE);
		// Create new DTS user
		String dtsEmail = RandomStringUtils.randomAlphabetic(20) + "@mailinator.com";
		String dtsUserData = jsonData.get(APIData.ADD_DTS_USER).toString();
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "userEmail", dtsEmail);
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "email", dtsEmail);
		JSONObject newDTSUser = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, dtsUserData);
		// Log out
		home.logout();
		// Log into DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Send a sample GET request using partner's session and CSRF token as following:
		// GET http://devportal.dts.com/saap/api/resetUserPassWord/36 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.RESET_USER_PASSWORD_URL(newDTSUser.get("id").toString()), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"INTERNAL_ERROR","message":"Permission denied!"} 
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest("DELETE", APIData.DELETE_USER_URL(newDTSUser.get("id").toString()), csrfToken, cookie, "");
	}
	
	
	/*
	 * Verify that Partner user is unable to suspend a primary contact account.
	 */
	@Test
	public void APIU_16(){
		result.addLog("ID : APIU_16 : Verify that Partner user is unable to suspend a primary contact account.");
		/*
			Send a sample GET request using partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/resetUserPassWord/36 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage user" privilege
		home.enableAllPrivilege();
		home.click(AddUser.SAVE);
		// Log out
		home.logout();
		// Log into DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
//		Send a sample PUT request using partner's session and CSRF token as following:
//		PUT http://devportal.dts.com/saap/api/suspendUser/2432 HTTP/1.1
		JSONObject requestPut = APIUtil.sendHTTPRequest(APIData.PUT_METHOD, APIData.SUSPEND_USER_URL(partneruserId), csrfToken, cookie, "");
		/*
		 * {"apiErrorCode":"INTERNAL_ERROR","message":"Permission denied!"} 
		 */
		Assert.assertEquals(requestPut.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to delete a primary contact account.
	 */
	@Test
	public void APIU_17(){
		result.addLog("ID : APIU_17 : Verify that Partner user is unable to delete a primary contact account.");
		/*
			Send a sample GET request using partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/resetUserPassWord/36 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage user" privilege
		home.enableAllPrivilege();
		home.click(AddUser.SAVE);
		// Log out
		home.logout();
		// Log into DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
//		Send a sample PUT request using partner's session and CSRF token as following:
//		PUT http://devportal.dts.com/saap/api/suspendUser/2432 HTTP/1.1
		JSONObject requestPut = APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_USER_URL(partneruserId), csrfToken, cookie, "");
		/*
		 * {"apiErrorCode":"INTERNAL_ERROR","message":"Permission denied!"} 
		 */
		Assert.assertEquals(requestPut.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that DTS is unable to suspend another DTS user when the "Add and manage DTS users" privilege is disabled
	 */
	@Test
	public void APIU_18(){
		result.addLog("ID : APIU_18 : Verify that DTS is unable to suspend another DTS user when the 'Add and manage DTS users' privilege is disabled");
		/*
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=SUSPENDED HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage user" privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		home.click(AddUser.SAVE);
		// Create new DTS user
		String dtsEmail = RandomStringUtils.randomAlphabetic(20) + "@mailinator.com";
		String dtsUserData = jsonData.get(APIData.ADD_DTS_USER).toString();
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "userEmail", dtsEmail);
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "email", dtsEmail);
		JSONObject newDTSUser = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, dtsUserData);
		// Log out
		home.logout();
		// Log into DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=SUSPENDED HTTP/1.1
		JSONObject requestResult = APIUtil.sendHTTPRequest("PUT", APIData.SUSPEND_USER_URL(newDTSUser.get("id").toString()), csrfToken, cookie, "");
		/*
		 * {"apiErrorCode":"INTERNAL_ERROR","message":"Permission denied!"} 
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest("DELETE", APIData.DELETE_USER_URL(newDTSUser.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that DTS is unable to delete another DTS user when the "Add and manage DTS users" privilege is disabled
	 */
	@Test
	public void APIU_19(){
		result.addLog("ID : APIU_19 : Verify that DTS is unable to delete another DTS user when the 'Add and manage DTS users' privilege is disabled");
		/*
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=INACTIVE HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage user" privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		home.click(AddUser.SAVE);
		// Create new DTS user
		String dtsEmail = RandomStringUtils.randomAlphabetic(20) + "@mailinator.com";
		String dtsUserData = jsonData.get(APIData.ADD_DTS_USER).toString();
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "userEmail", dtsEmail);
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "email", dtsEmail);
		JSONObject newDTSUser = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, dtsUserData);
		// Log out
		home.logout();
		// Log into DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=SUSPENDED HTTP/1.1
		JSONObject requestResult = APIUtil.sendHTTPRequest("DELETE", APIData.DELETE_USER_URL(newDTSUser.get("id").toString()), csrfToken, cookie, "");
		/*
		 * {"apiErrorCode":"INTERNAL_ERROR","message":"Permission denied!"} 
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest("DELETE", APIData.DELETE_USER_URL(newDTSUser.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that DTS is unable to restore another DTS user when the "Add and manage DTS users" privilege is disabled
	 */
	@Test
	public void APIU_20(){
		result.addLog("ID : APIU_20 : Verify that DTS is unable to restore another DTS user when the 'Add and manage DTS users' privilege is disabled");
		/*
			Send a sample GET request using partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/updateUserStatus?userId=1339&time=1436786256862&account_status=ACTIVE HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage user" privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		home.click(AddUser.SAVE);
		// Create new DTS user
		String dtsEmail = RandomStringUtils.randomAlphabetic(20) + "@mailinator.com";
		String dtsUserData = jsonData.get(APIData.ADD_DTS_USER).toString();
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "userEmail", dtsEmail);
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "email", dtsEmail);
		JSONObject newDTSUser = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, dtsUserData);
		// Log out
		home.logout();
		// Log into DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Send a sample GET request using partner's session and CSRF token as following:
		// GET http://devportal.dts.com/saap/api/updateUserStatus?userId=1339&time=1436786256862&account_status=ACTIVE HTTP/1.1
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.PUT_METHOD, APIData.RESTORE_USER_URL(newDTSUser.get("id").toString()), csrfToken, cookie, "");
		/*
		 * {"apiErrorCode":"INTERNAL_ERROR","message":"Permission denied!"} 
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_USER_URL(newDTSUser.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that DTS is unable to suspend a partner user account when the "Add and manage users" privilege is disabled
	 */
	@Test
	public void APIU_21(){
		result.addLog("ID : APIU_21 : Verify that DTS is unable to suspend a partner user account when the 'Add and manage users' privilege is disabled");
		/*
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=SUSPENDED HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage user" privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// Get a company ID
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.OTHER_COMPANY_DATA).toString());
		String companyID = company.get("id").toString();
		// Create new Partner user
		String partnerEmail = RandomStringUtils.randomAlphabetic(20) + "@mailinator.com";
		String partnerUserData = jsonData.get(APIData.ADD_PARTNER_USER).toString();
		partnerUserData = APIUtil.replaceStringValue(partnerUserData, "partnerId", companyID);
		partnerUserData = APIUtil.replaceStringValue(partnerUserData, "userEmail", partnerEmail);
		partnerUserData = APIUtil.replaceStringValue(partnerUserData, "email", partnerEmail);
		JSONObject newPartnerUser = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, partnerUserData);
		// Log out
		home.logout();
		// Log into DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=SUSPENDED HTTP/1.1
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.PUT_METHOD, APIData.SUSPEND_USER_URL(newPartnerUser.get("id").toString()), csrfToken, cookie, "");
		/*
		 * {"apiErrorCode":"INTERNAL_ERROR","message":"Permission denied!"} 
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_USER_URL(newPartnerUser.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that DTS is unable to delete a partner user account when the "Add and manage users" privilege is disabled
	 */
	@Test
	public void APIU_22(){
		result.addLog("ID : APIU_22 : Verify that DTS is unable to delete a partner user account when the 'Add and manage users' privilege is disabled");
		/*
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=SUSPENDED HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage user" privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// Get a company ID
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.OTHER_COMPANY_DATA).toString());
		String companyID = company.get("id").toString();
		// Create new Partner user
		String partnerEmail = RandomStringUtils.randomAlphabetic(20) + "@mailinator.com";
		String partnerUserData = jsonData.get(APIData.ADD_PARTNER_USER).toString();
		partnerUserData = APIUtil.replaceStringValue(partnerUserData, "partnerId", companyID);
		partnerUserData = APIUtil.replaceStringValue(partnerUserData, "userEmail", partnerEmail);
		partnerUserData = APIUtil.replaceStringValue(partnerUserData, "email", partnerEmail);
		JSONObject newPartnerUser = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, partnerUserData);
		// Log out
		home.logout();
		// Log into DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=SUSPENDED HTTP/1.1
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_USER_URL(newPartnerUser.get("id").toString()), csrfToken, cookie, "");
		// Check if PDPP-1373 found
		if(requestResult == null){
			result.addErrorLog("PDPP-1375 - API Validation: api/deleteUser: DTS User is able to delete a Partner User when \"Add and manage user\" privilege is disabled");
			Assert.assertTrue(false);
		}
		/*
		 * {"apiErrorCode":"INTERNAL_ERROR","message":"Permission denied!"} 
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_USER_URL(newPartnerUser.get("id").toString()), csrfToken, cookie, "");
	}
		
	/*
	 * Verify that DTS is unable to restore a partner user account when the "Add and manage users" privilege is disabled
	 */
	@Test
	public void APIU_23(){
		result.addLog("ID : APIU_23 : Verify that DTS is unable to restore a partner user account when the 'Add and manage users' privilege is disabled");
		/*
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/updateUserStatus?userId=1339&time=1436786256862&account_status=ACTIVE HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage user" privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// Get a company ID
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.OTHER_COMPANY_DATA).toString());
		String companyID = company.get("id").toString();
		// Create new Partner user
		String partnerEmail = RandomStringUtils.randomAlphabetic(20) + "@mailinator.com";
		String partnerUserData = jsonData.get(APIData.ADD_PARTNER_USER).toString();
		partnerUserData = APIUtil.replaceStringValue(partnerUserData, "partnerId", companyID);
		partnerUserData = APIUtil.replaceStringValue(partnerUserData, "userEmail", partnerEmail);
		partnerUserData = APIUtil.replaceStringValue(partnerUserData, "email", partnerEmail);
		JSONObject newPartnerUser = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, partnerUserData);
		// Log out
		home.logout();
		// Log into DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/updateUserStatus?userId=1339&time=1436786256862&account_status=ACTIVE HTTP/1.1
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.PUT_METHOD, APIData.RESTORE_USER_URL(newPartnerUser.get("id").toString()), csrfToken, cookie, "");
		/*
		 * {"apiErrorCode":"INTERNAL_ERROR","message":"Permission denied!"} 
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_USER_URL(newPartnerUser.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to suspend a DTS user when the "Add and manage users" privilege is enabled
	 */
	@Test
	public void APIU_24(){
		result.addLog("ID : APIU_24 : Verify that Partner user is unable to suspend a DTS user when the 'Add and manage users' privilege is enabled");
		/*
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=SUSPENDED HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Enable "Add and manage user" privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// Create new DTS user
		String dtsEmail = RandomStringUtils.randomAlphabetic(20) + "@mailinator.com";
		String dtsUserData = jsonData.get(APIData.ADD_DTS_USER).toString();
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "userEmail", dtsEmail);
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "email", dtsEmail);
		JSONObject newDTSUser = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, dtsUserData);
		// Log out
		home.logout();
		// Log into DTS portal as Partner user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=SUSPENDED HTTP/1.1
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.PUT_METHOD, APIData.SUSPEND_USER_URL(newDTSUser.get("id").toString()), csrfToken, cookie, "");
		/*
		 * {"apiErrorCode":"INTERNAL_ERROR","message":"Permission denied!"} 
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_USER_URL(newDTSUser.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to delete a DTS user when the "Add and manage users" privilege is enabled
	 */
	@Test
	public void APIU_25(){
		result.addLog("ID : APIU_25 : Verify that Partner user is unable to delete a DTS user when the \"Add and manage users\" privilege is enabled");
		/*
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=SUSPENDED HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Enable "Add and manage user" privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// Create new DTS user
		String dtsEmail = RandomStringUtils.randomAlphabetic(20) + "@mailinator.com";
		String dtsUserData = jsonData.get(APIData.ADD_DTS_USER).toString();
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "userEmail", dtsEmail);
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "email", dtsEmail);
		JSONObject newDTSUser = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, dtsUserData);
		// Log out
		home.logout();
		// Log into DTS portal as Partner user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=SUSPENDED HTTP/1.1
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_USER_URL(newDTSUser.get("id").toString()), csrfToken, cookie, "");
		/*
		 * {"apiErrorCode":"INTERNAL_ERROR","message":"Permission denied!"} 
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_USER_URL(newDTSUser.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to restore a DTS user when the "Add and manage users" privilege is enabled
	 */
	@Test
	public void APIU_26(){
		result.addLog("ID : APIU_26 : Verify that Partner user is unable to restore a DTS user when the \"Add and manage users\" privilege is enabled");
		/*
			Send a sample GET request using partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/updateUserStatus?userId=1339&time=1436786256862&account_status=ACTIVE HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Enable "Add and manage user" privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// Create new DTS user
		String dtsEmail = RandomStringUtils.randomAlphabetic(20) + "@mailinator.com";
		String dtsUserData = jsonData.get(APIData.ADD_DTS_USER).toString();
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "userEmail", dtsEmail);
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "email", dtsEmail);
		JSONObject newDTSUser = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, dtsUserData);
		// Log out
		home.logout();
		// Log into DTS portal as Partner user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=SUSPENDED HTTP/1.1
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.PUT_METHOD, APIData.RESTORE_USER_URL(newDTSUser.get("id").toString()), csrfToken, cookie, "");
		/*
		 * {"apiErrorCode":"INTERNAL_ERROR","message":"Permission denied!"} 
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_USER_URL(newDTSUser.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to suspend a user of his company when the "Add and manage users" privilege is disabled.
	 */
	@Test
	public void APIU_27(){
		result.addLog("ID : APIU_27 : Verify that Partner user is unable to suspend a user of his company when the \"Add and manage users\" privilege is disabled.");
		/*
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=SUSPENDED HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage user" privilege
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		home.click(AddUser.SAVE);
		// Get a company ID
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.COMPANY_DATA).toString());
		String companyID = company.get("id").toString();
		// Create new Partner user
		String partnerEmail = RandomStringUtils.randomAlphabetic(20) + "@mailinator.com";
		String partnerUserData = jsonData.get(APIData.ADD_PARTNER_USER).toString();
		partnerUserData = APIUtil.replaceStringValue(partnerUserData, "partnerId", companyID);
		partnerUserData = APIUtil.replaceStringValue(partnerUserData, "userEmail", partnerEmail);
		partnerUserData = APIUtil.replaceStringValue(partnerUserData, "email", partnerEmail);
		JSONObject newPartnerUser = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, partnerUserData);
		// Log out
		home.logout();
		// Log into DTS portal as Partner user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=SUSPENDED HTTP/1.1
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.PUT_METHOD, APIData.SUSPEND_USER_URL(newPartnerUser.get("id").toString()), csrfToken, cookie, "");
		/*
		 * {"apiErrorCode":"INTERNAL_ERROR","message":"Permission denied!"} 
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_USER_URL(newPartnerUser.get("id").toString()), csrfToken, cookie, "");
	}
}
