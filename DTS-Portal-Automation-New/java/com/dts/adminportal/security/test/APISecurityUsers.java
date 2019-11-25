package com.dts.adminportal.security.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.security.util.APIData;
import com.dts.adminportal.security.util.APIUtil;

import junit.framework.Assert;

public class APISecurityUsers extends BasePage{
	private String csrfToken;
	private String cookie;
	private JSONObject jsonData;
	
	@Override
	protected void initData() {
		jsonData = APIUtil.parseDataToJsonObject(APIData.JSON_DATA_FILE);		
	}

/*
	 * Verify that DTS user is unable to add partner user when "Add and manage User" is disabled and "Add and manage DTS user" is enabled
	 */
	//@SuppressWarnings("unchecked")
	@Test
	public void APIU_01(){
		userControl.addLog("ID : APIU_01 : Verify that DTS user is unable to add partner user when 'Add and manage User' is disabled and 'Add and manage DTS user' is enabled");
		/*
		 	Precondition: The "Add and manage User" is disabled and "Add and manage DTS user" is enabled

			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/account HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();

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
		userControl.addLog("ID : APIU_02 : Verify that DTS user is unable to edit partner user when 'Add and manage User' is disabled and 'Add and manage DTS user' is enabled");
		/*
		 	Precondition: The "Add and manage User" is disabled and "Add and manage DTS user" is enabled

			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/account HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();

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
		userControl.addLog("ID : APIU_03 : Verify that DTS user is unable to add DTS user when 'Add and manage User' is enabled and 'Add and manage DTS user' is disabled");
		/*
		 	Precondition: Precondition: The "Add and manage User" is enabled and "Add and manage DTS user" is disabled

			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/account HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();

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
		userControl.addLog("ID : APIU_04 : Verify that DTS user is unable to edit DTS user when 'Add and manage User' is enabled and 'Add and manage DTS user' is disabled");
		/*
		 	Precondition: Precondition: The "Add and manage User" is enabled and "Add and manage DTS user" is disabled

			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/account HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();

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
		userControl.addLog("ID : APIU_05 : Verify that DTS user is unable to get users list when both 'Add and manage User' and 'Add and manage DTS user' are disabled:");
		/*
			Precondition: Both "Add and manage User" and "Add and manage DTS user" are disabled
			
			GET http://devportal.dts.com/saap/api/users HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();

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
		userControl.addLog("ID : APIU_06 : Verify that Partner user is unable to add partner user when 'Add and manage User' is disabled");
		/*
			Precondition: The "Add and manage User" privilege is disabled
			
			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/account HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();

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
		userControl.addLog("ID : APIU_07 : Verify that Partner user is unable to edit partner user when 'Add and manage User' is disabled");
		/*
			Precondition: The "Add and manage User" privilege is disabled
			
			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/account HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();

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
		userControl.addLog("ID : APIU_08 : Verify that Partner user is unable to add DTS user when the 'Add and manage User' privilege is enabled or disabled");
		/*
		Precondition: The "Add and manage User" privilege is enabled or disabled
		
		Send a sample POST request using partner's session and CSRF token as following:
		POST http://devportal.dts.com/saap/api/account HTTP/1.1
		 */
		//When the "Add and manage User" privilege is enabled
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		//home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();

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
		userControl.logout();
		//When the "Add and manage User" privilege is enabled
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();

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
		userControl.addLog("ID : APIU_09 : Verify that Partner user is unable to edit DTS user when the 'Add and manage User' privilege is enabled or disabled");
		/*
		Precondition: The "Add and manage User" privilege is enabled or disabled
		
		Send a sample POST request using partner's session and CSRF token as following:
		POST http://devportal.dts.com/saap/api/account HTTP/1.1
		 */
		//When the "Add and manage User" privilege is enabled
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		//home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();

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
		userControl.logout();
		//When the "Add and manage User" privilege is enabled
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();

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
		userControl.addLog("ID : APIU_10 : Verify that Partner user is unable to get list of all user including user of DTS and other company when the 'Add and manage User' privilege is enabled.");
		/*
		Precondition: The "Add and manage User" privilege is enabled or disabled
		
		Send a sample POST request using partner's session and CSRF token as following:
		POST http://devportal.dts.com/saap/api/account HTTP/1.1
		 */
		//When the "Add and manage User" privilege is enabled
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		//home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();

		// send get request to get list users
		JSONObject requestResult = APIUtil.sendGet(APIData.GET_USER_LIST_URL, csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertFalse(requestResult.toString().contains(DTS_USER));
		Assert.assertFalse(requestResult.toString().contains(PARTNER_USER));
	}
	
	/*
	 * Verify that Partner user is unable to get detail of other user including user of DTS and other company when the "Add and manage User" privilege is enabled.
	 */
	//@SuppressWarnings("unchecked")
	@Test
	public void APIU_11(){
		userControl.addLog("ID : APIU_11 : Verify that Partner user is unable to get detail of other user including user of DTS and other company when the 'Add and manage User' privilege is enabled.");
		/*
			Send a sample GET request using partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/users/34?time=1433846601848&userId=34 HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();

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
		userControl.addLog("ID : APIU_12 : Verify that Partner and DTS user are unable to change user own email address when both 'Add and Manage User' and 'Add and Manage DTS user' privileges are disabled.");
		/*
			Send a sample GET request using partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/users/34?time=1433846601848&userId=34 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Change privilege for partner user
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		//Change privielege for dts user
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		userControl.click(AddUser.SAVE);
		//log out
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();

		String requestBody = jsonData.get(APIData.EDIT_PARTNER_USER_OWN).toString();
		// send get request to get list users
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie,requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.toString(), APIData.ERROR_MESSAGE_CHANGE_USER_OWN_EMAIL);
		//log out
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();

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
		userControl.addLog("ID : APIU_13 : Verify that Partner is unable to reset password of DTS user or user of another company when the 'Add and manage users' privilege is enabled or disabled");
		/*
			Send a sample GET request using partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/resetUserPassWord/36 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Enable "Add and manage user" privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
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
		userControl.logout();
		// Log into DTS portal as partner user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.logout();
		// Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest("DELETE", APIData.DELETE_USER_URL(newDTSUser.get("id").toString()), csrfToken, cookie, "");
		APIUtil.sendHTTPRequest("DELETE",APIData.DELETE_USER_URL(newPartnerUser.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that DTS is unable to reset password of Partner when the "Add and manage users" privilege is disabled
	 */
	@Test
	public void APIU_14(){
		userControl.addLog("ID : APIU_14 : Verify that DTS is unable to reset password of Partner when the 'Add and manage users' privilege is disabled");
		/*
			Send a sample GET request using partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/resetUserPassWord/36 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Add and manage user" privilege
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
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
		userControl.logout();
		// Log into DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Send a sample GET request using partner's session and CSRF token as following:
		// GET http://devportal.dts.com/saap/api/resetUserPassWord/36 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.RESET_USER_PASSWORD_URL(newPartnerUser.get("id").toString()), csrfToken, cookie);
		// Check if PDPP-1373 found
		if(requestResult == null){
			userControl.addErrorLog("PDPP-1373 - API Validation: api/resetUserPassWord: DTS user is able to reset password of a Partner user when \"Add and manage user\" privilege is disabled");
			Assert.assertTrue(false);
		}
		/*
		 * {"apiErrorCode":"INTERNAL_ERROR","message":"Permission denied!"} 
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		userControl.logout();
		// Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest("DELETE", APIData.DELETE_USER_URL(newPartnerUser.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that DTS is unable to reset password of other DTS users when the "Add and manage DTS users" privilege is disabled
	 */
	@Test
	public void APIU_15(){
		userControl.addLog("ID : APIU_15 : Verify that DTS is unable to reset password of other DTS users when the 'Add and manage DTS users' privilege is disabled");
		/*
			Send a sample GET request using partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/resetUserPassWord/36 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Add and manage user" privilege
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		userControl.click(AddUser.SAVE);
		// Create new DTS user
		String dtsEmail = RandomStringUtils.randomAlphabetic(20) + "@mailinator.com";
		String dtsUserData = jsonData.get(APIData.ADD_DTS_USER).toString();
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "userEmail", dtsEmail);
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "email", dtsEmail);
		JSONObject newDTSUser = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, dtsUserData);
		// Log out
		userControl.logout();
		// Log into DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.logout();
		// Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest("DELETE", APIData.DELETE_USER_URL(newDTSUser.get("id").toString()), csrfToken, cookie, "");
	}

/*
	 * Verify that Partner user is unable to suspend a primary contact account.
	 */
	@Test
	public void APIU_16(){
		userControl.addLog("ID : APIU_16 : Verify that Partner user is unable to suspend a primary contact account.");
		/*
			Send a sample GET request using partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/resetUserPassWord/36 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Add and manage user" privilege
		userControl.enableAllPrivileges();
		userControl.click(AddUser.SAVE);
		// Log out
		userControl.logout();
		// Log into DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.addLog("ID : APIU_17 : Verify that Partner user is unable to delete a primary contact account.");
		/*
			Send a sample GET request using partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/resetUserPassWord/36 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Add and manage user" privilege
		userControl.enableAllPrivileges();
		userControl.click(AddUser.SAVE);
		// Log out
		userControl.logout();
		// Log into DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.addLog("ID : APIU_18 : Verify that DTS is unable to suspend another DTS user when the 'Add and manage DTS users' privilege is disabled");
		/*
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=SUSPENDED HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Add and manage user" privilege
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		userControl.click(AddUser.SAVE);
		// Create new DTS user
		String dtsEmail = RandomStringUtils.randomAlphabetic(20) + "@mailinator.com";
		String dtsUserData = jsonData.get(APIData.ADD_DTS_USER).toString();
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "userEmail", dtsEmail);
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "email", dtsEmail);
		JSONObject newDTSUser = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, dtsUserData);
		// Log out
		userControl.logout();
		// Log into DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.logout();
		// Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest("DELETE", APIData.DELETE_USER_URL(newDTSUser.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that DTS is unable to delete another DTS user when the "Add and manage DTS users" privilege is disabled
	 */
	@Test
	public void APIU_19(){
		userControl.addLog("ID : APIU_19 : Verify that DTS is unable to delete another DTS user when the 'Add and manage DTS users' privilege is disabled");
		/*
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=INACTIVE HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Add and manage user" privilege
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		userControl.click(AddUser.SAVE);
		// Create new DTS user
		String dtsEmail = RandomStringUtils.randomAlphabetic(20) + "@mailinator.com";
		String dtsUserData = jsonData.get(APIData.ADD_DTS_USER).toString();
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "userEmail", dtsEmail);
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "email", dtsEmail);
		JSONObject newDTSUser = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, dtsUserData);
		// Log out
		userControl.logout();
		// Log into DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.logout();
		// Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest("DELETE", APIData.DELETE_USER_URL(newDTSUser.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that DTS is unable to restore another DTS user when the "Add and manage DTS users" privilege is disabled
	 */
	@Test
	public void APIU_20(){
		userControl.addLog("ID : APIU_20 : Verify that DTS is unable to restore another DTS user when the 'Add and manage DTS users' privilege is disabled");
		/*
			Send a sample GET request using partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/updateUserStatus?userId=1339&time=1436786256862&account_status=ACTIVE HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Add and manage user" privilege
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_DTS_user);
		userControl.click(AddUser.SAVE);
		// Create new DTS user
		String dtsEmail = RandomStringUtils.randomAlphabetic(20) + "@mailinator.com";
		String dtsUserData = jsonData.get(APIData.ADD_DTS_USER).toString();
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "userEmail", dtsEmail);
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "email", dtsEmail);
		JSONObject newDTSUser = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, dtsUserData);
		// Log out
		userControl.logout();
		// Log into DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.logout();
		// Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_USER_URL(newDTSUser.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that DTS is unable to suspend a partner user account when the "Add and manage users" privilege is disabled
	 */
	@Test
	public void APIU_21(){
		userControl.addLog("ID : APIU_21 : Verify that DTS is unable to suspend a partner user account when the 'Add and manage users' privilege is disabled");
		/*
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=SUSPENDED HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Add and manage user" privilege
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
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
		userControl.logout();
		// Log into DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.logout();
		// Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_USER_URL(newPartnerUser.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that DTS is unable to delete a partner user account when the "Add and manage users" privilege is disabled
	 */
	@Test
	public void APIU_22(){
		userControl.addLog("ID : APIU_22 : Verify that DTS is unable to delete a partner user account when the 'Add and manage users' privilege is disabled");
		/*
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=SUSPENDED HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Add and manage user" privilege
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
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
		userControl.logout();
		// Log into DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=SUSPENDED HTTP/1.1
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_USER_URL(newPartnerUser.get("id").toString()), csrfToken, cookie, "");
		// Check if PDPP-1373 found
		if(requestResult == null){
			userControl.addErrorLog("PDPP-1375 - API Validation: api/deleteUser: DTS User is able to delete a Partner User when \"Add and manage user\" privilege is disabled");
			Assert.assertTrue(false);
		}
		/*
		 * {"apiErrorCode":"INTERNAL_ERROR","message":"Permission denied!"} 
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		userControl.logout();
		// Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_USER_URL(newPartnerUser.get("id").toString()), csrfToken, cookie, "");
	}
		
	/*
	 * Verify that DTS is unable to restore a partner user account when the "Add and manage users" privilege is disabled
	 */
	@Test
	public void APIU_23(){
		userControl.addLog("ID : APIU_23 : Verify that DTS is unable to restore a partner user account when the 'Add and manage users' privilege is disabled");
		/*
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/updateUserStatus?userId=1339&time=1436786256862&account_status=ACTIVE HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Add and manage user" privilege
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
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
		userControl.logout();
		// Log into DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.logout();
		// Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_USER_URL(newPartnerUser.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to suspend a DTS user when the "Add and manage users" privilege is enabled
	 */
	@Test
	public void APIU_24(){
		userControl.addLog("ID : APIU_24 : Verify that Partner user is unable to suspend a DTS user when the 'Add and manage users' privilege is enabled");
		/*
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=SUSPENDED HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Enable "Add and manage user" privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// Create new DTS user
		String dtsEmail = RandomStringUtils.randomAlphabetic(20) + "@mailinator.com";
		String dtsUserData = jsonData.get(APIData.ADD_DTS_USER).toString();
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "userEmail", dtsEmail);
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "email", dtsEmail);
		JSONObject newDTSUser = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, dtsUserData);
		// Log out
		userControl.logout();
		// Log into DTS portal as Partner user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.logout();
		// Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_USER_URL(newDTSUser.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to delete a DTS user when the "Add and manage users" privilege is enabled
	 */
	@Test
	public void APIU_25(){
		userControl.addLog("ID : APIU_25 : Verify that Partner user is unable to delete a DTS user when the \"Add and manage users\" privilege is enabled");
		/*
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=SUSPENDED HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Enable "Add and manage user" privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// Create new DTS user
		String dtsEmail = RandomStringUtils.randomAlphabetic(20) + "@mailinator.com";
		String dtsUserData = jsonData.get(APIData.ADD_DTS_USER).toString();
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "userEmail", dtsEmail);
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "email", dtsEmail);
		JSONObject newDTSUser = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, dtsUserData);
		// Log out
		userControl.logout();
		// Log into DTS portal as Partner user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.logout();
		// Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_USER_URL(newDTSUser.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to restore a DTS user when the "Add and manage users" privilege is enabled
	 */
	@Test
	public void APIU_26(){
		userControl.addLog("ID : APIU_26 : Verify that Partner user is unable to restore a DTS user when the \"Add and manage users\" privilege is enabled");
		/*
			Send a sample GET request using partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/updateUserStatus?userId=1339&time=1436786256862&account_status=ACTIVE HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Enable "Add and manage user" privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
		// Create new DTS user
		String dtsEmail = RandomStringUtils.randomAlphabetic(20) + "@mailinator.com";
		String dtsUserData = jsonData.get(APIData.ADD_DTS_USER).toString();
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "userEmail", dtsEmail);
		dtsUserData = APIUtil.replaceStringValue(dtsUserData, "email", dtsEmail);
		JSONObject newDTSUser = APIUtil.sendPost(APIData.ADD_USER_URL, csrfToken, cookie, dtsUserData);
		// Log out
		userControl.logout();
		// Log into DTS portal as Partner user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.logout();
		// Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_USER_URL(newDTSUser.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to suspend a user of his company when the "Add and manage users" privilege is disabled.
	 */
	@Test
	public void APIU_27(){
		userControl.addLog("ID : APIU_27 : Verify that Partner user is unable to suspend a user of his company when the \"Add and manage users\" privilege is disabled.");
		/*
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/updateUserStatus?userId=40&time=1436785159084&account_status=SUSPENDED HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Add and manage user" privilege
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Add_and_manage_user);
		userControl.click(AddUser.SAVE);
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
		userControl.logout();
		// Log into DTS portal as Partner user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.logout();
		// Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Delete user
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_USER_URL(newPartnerUser.get("id").toString()), csrfToken, cookie, "");
	}
}
