package com.dts.adminportal.security.test;

import junit.framework.Assert;

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

public class APISecurityAppsDevices extends CreatePage{
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
	 * Verify that DTS user is unable to add new device without having "Add and manage apps and devices" privilege
	 */
	@Test
	public void TCAPIAD_01(){
		result.addLog("ID : TCAPIAD_01 : Verify that DTS user is unable to add new device without having 'Add and manage apps and devices' privilege");
		/*
		 	Precondition: DTS user does not have "Add and manage apps and devices" privilege.
			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/devices HTTP/1.1
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
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_apps_devices);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Create request body
		String requestBody = jsonData.get(APIData.ADD_DEVICE_DATA).toString();
		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/devices HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_DEVICE_URL, csrfToken, cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that DTS user is unable to edit device without having "Add and manage apps and devices" privilege
	 */
	@Test
	public void TCAPIAD_02(){
		result.addLog("ID : TCAPIAD_02 : Verify that DTS user is unable to edit device without having 'Add and manage apps and devices' privilege");
		/*
		 	Precondition: DTS user does not have "Add and manage apps and devices" privilege.
			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/devices HTTP/1.1
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
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_apps_devices);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Create request body
		String requestBody = jsonData.get(APIData.EDIT_DEVICE_DATA).toString();
		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/devices HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.EDIT_DEVICE_URL, csrfToken, cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that DTS user is unable to publish device without having "Publish and suspend apps and devices" privilege
	 */
	@Test
	public void TCAPIAD_03(){
		result.addLog("ID : TCAPIAD_03 : Verify that DTS user is unable to publish device without having 'Publish and suspend apps and devices' privilege");
		/*
		 	Precondition: DTS user does not have "Publish and suspend apps and devices" privilege.
			Send a sample POST request as following:
			GET http://devportal.dts.com/saap/api/publishDevice?devId=985 HTTP/1.1
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
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Publish_suspend_apps_devices);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get device ID
		JSONObject device = APIUtil.parseDataToJsonObject(jsonData.get(APIData.DEVICE_DATA).toString());
		String deviceID = device.get("id").toString();
		// Send a sample POST request as following:
		// GET http://devportal.dts.com/saap/api/publishDevice?devId=985 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_DEVICE_URL(deviceID), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that DTS user is unable to suspend device without having "Publish and suspend apps and devices" privilege
	 */
	@Test
	public void TCAPIAD_04(){
		result.addLog("ID : TCAPIAD_04 : Verify that DTS user is unable to suspend device without having 'Publish and suspend apps and devices' privilege");
		/*
		 	Precondition: DTS user does not have "Publish and suspend apps and devices" privilege.
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/susDevice/721?revision=0 HTTP/1.1
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
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Publish_suspend_apps_devices);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get device ID
		JSONObject device = APIUtil.parseDataToJsonObject(jsonData.get(APIData.PUBLISHED_DEVICE_DATA).toString());
		String deviceID = device.get("id").toString();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/susDevice/721?revision=0 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_DEVICE_URL(deviceID), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that DTS user is unable to restore device without having "Publish and suspend apps and devices" privilege
	 */
	@Test
	public void TCAPIAD_05(){
		result.addLog("ID : TCAPIAD_05 : Verify that DTS user is unable to restore device without having 'Publish and suspend apps and devices' privilege");
		/*
		 	Precondition: DTS user does not have "Publish and suspend apps and devices" privilege.
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/restoreDevice/721?revision=0 HTTP/1.1
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
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Publish_suspend_apps_devices);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get device ID
		JSONObject device = APIUtil.parseDataToJsonObject(jsonData.get(APIData.SUSPENDED_DEVICE_DATA).toString());
		String deviceID = device.get("id").toString();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/restoreDevice/721?revision=0 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.RESTORE_DEVICE_URL(deviceID), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that DTS user is unable to delete device without having "Publish and suspend apps and devices" privilege
	 */
	@Test
	public void TCAPIAD_06(){
		result.addLog("ID : TCAPIAD_06 : Verify that DTS user is unable to delete device without having 'Publish and suspend apps and devices' privilege");
		/*
		 	Precondition: DTS user does not have "Publish and suspend apps and devices" privilege.
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/delDevice/197?revision=1 HTTP/1.1
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
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Publish_suspend_apps_devices);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get device ID
		JSONObject device = APIUtil.parseDataToJsonObject(jsonData.get(APIData.DEVICE_DATA).toString());
		String deviceID = device.get("id").toString();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/delDevice/197?revision=1 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.DELETE_DEVICE_URL(deviceID), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to get list apps/devices
	 */
	@Test
	public void TCAPIAD_07(){
		result.addLog("ID : TCAPIAD_07 : Verify that Partner user is unable to get list apps/devices");
		/*
		 	Send a sample GET request using Partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/devices HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Send a sample GET request using Partner's session and CSRF token as following:
		// GET http://devportal.dts.com/saap/api/devices HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.GET_LIST_DEVICE_URL, csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to get an app/device's detail
	 */
	@Test
	public void TCAPIAD_08(){
		result.addLog("ID : TCAPIAD_08 : Verify that Partner user is unable to get an app/device's detail");
		/*
		 	Send a sample GET request using Partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/devices/197?revision=0 HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get device ID
		JSONObject device = APIUtil.parseDataToJsonObject(jsonData.get(APIData.DEVICE_DATA).toString());
		String deviceID = device.get("id").toString();
		// Send a sample GET request using Partner's session and CSRF token as following:
		// GET http://devportal.dts.com/saap/api/devices/197?revision=0 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.GET_DEVICE_DETAIL_URL(deviceID), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to add new device
	 */
	@Test
	public void TCAPIAD_09(){
		result.addLog("ID : TCAPIAD_09 : Verify that Partner user is unable to add new device");
		/*
		 	Send a sample POST request using partner's sesssion and CSRF token as following:
			POST http://devportal.dts.com/saap/api/devices HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Create request body
		String requestBody = jsonData.get(APIData.ADD_DEVICE_DATA).toString();
		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/devices HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_DEVICE_URL, csrfToken, cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to edit device
	 */
	@Test
	public void TCAPIAD_10(){
		result.addLog("ID : TCAPIAD_10 : Verify that Partner user is unable to edit device");
		/*
		 	Send a sample POST request using partner's sesssion and CSRF token as following:
			POST http://devportal.dts.com/saap/api/devices HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Create request body
		String requestBody = jsonData.get(APIData.EDIT_DEVICE_DATA).toString();
		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/devices HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.EDIT_DEVICE_URL, csrfToken, cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to publish device
	 */
	@Test
	public void TCAPIAD_11(){
		result.addLog("ID : TCAPIAD_11 : Verify that Partner user is unable to publish device");
		/*
		 	Send a sample POST request as following:
			GET http://devportal.dts.com/saap/api/publishDevice?devId=985 HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get device ID
		JSONObject device = APIUtil.parseDataToJsonObject(jsonData.get(APIData.DEVICE_DATA).toString());
		String deviceID = device.get("id").toString();
		// Send a sample POST request as following:
		// GET http://devportal.dts.com/saap/api/publishDevice?devId=985 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_DEVICE_URL(deviceID), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to suspend device
	 */
	@Test
	public void TCAPIAD_12(){
		result.addLog("ID : TCAPIAD_12 : Verify that Partner user is unable to suspend device");
		/*
		 	Send a sample GET request using partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/susDevice/721?revision=0 HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get device ID
		JSONObject device = APIUtil.parseDataToJsonObject(jsonData.get(APIData.PUBLISHED_DEVICE_DATA).toString());
		String deviceID = device.get("id").toString();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/susDevice/721?revision=0 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_DEVICE_URL(deviceID), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to restore device
	 */
	@Test
	public void TCAPIAD_13(){
		result.addLog("ID : TCAPIAD_13 : Verify that Partner user is unable to restore device");
		/*
		 	Send a sample GET request using partner's sesssion and CSRF token as following:
			GET http://devportal.dts.com/saap/api/restoreDevice/721?revision=0 HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get device ID
		JSONObject device = APIUtil.parseDataToJsonObject(jsonData.get(APIData.SUSPENDED_DEVICE_DATA).toString());
		String deviceID = device.get("id").toString();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/restoreDevice/721?revision=0 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.RESTORE_DEVICE_URL(deviceID), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner  user is unable to delete device
	 */
	@Test
	public void TCAPIAD_14(){
		result.addLog("ID : TCAPIAD_14 : Verify that Partner  user is unable to delete device");
		/*
		 	Send a sample GET request using partner's sesssion and CSRF token as following:
			GET http://devportal.dts.com/saap/api/delDevice/197?revision=1 HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get device ID
		JSONObject device = APIUtil.parseDataToJsonObject(jsonData.get(APIData.DEVICE_DATA).toString());
		String deviceID = device.get("id").toString();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/delDevice/197?revision=1 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.DELETE_DEVICE_URL(deviceID), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	

}
