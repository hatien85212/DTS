package com.dts.adminportal.security.test;

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

public class APISecurityAppsDevices extends BasePage{
	private String csrfToken;
	private String cookie;
	private JSONObject jsonData;
	
	@Override
	protected void initData() {
		jsonData = APIUtil.parseDataToJsonObject(APIData.JSON_DATA_FILE);
	}

	/*
	 * Verify that DTS user is unable to add new device without having "Add and manage apps and devices" privilege
	 */
	@Test
	public void TCAPIAD_01(){
		appDeviceControl.addLog("ID : TCAPIAD_01 : Verify that DTS user is unable to add new device without having 'Add and manage apps and devices' privilege");
		/*
		 	Precondition: DTS user does not have "Add and manage apps and devices" privilege.
			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/devices HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		appDeviceControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		appDeviceControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_apps_devices);
		appDeviceControl.click(AddUser.SAVE);
		// Logout user
		appDeviceControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = appDeviceControl.getCSRFToken();
		cookie = appDeviceControl.getCookie();
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
		appDeviceControl.addLog("ID : TCAPIAD_02 : Verify that DTS user is unable to edit device without having 'Add and manage apps and devices' privilege");
		/*
		 	Precondition: DTS user does not have "Add and manage apps and devices" privilege.
			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/devices HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		appDeviceControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		appDeviceControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_apps_devices);
		appDeviceControl.click(AddUser.SAVE);
		// Logout user
		appDeviceControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = appDeviceControl.getCSRFToken();
		cookie = appDeviceControl.getCookie();
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
		appDeviceControl.addLog("ID : TCAPIAD_03 : Verify that DTS user is unable to publish device without having 'Publish and suspend apps and devices' privilege");
		/*
		 	Precondition: DTS user does not have "Publish and suspend apps and devices" privilege.
			Send a sample POST request as following:
			GET http://devportal.dts.com/saap/api/publishDevice?devId=985 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		appDeviceControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		appDeviceControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Publish_suspend_apps_devices);
		appDeviceControl.click(AddUser.SAVE);
		// Logout user
		appDeviceControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = appDeviceControl.getCSRFToken();
		cookie = appDeviceControl.getCookie();
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
		appDeviceControl.addLog("ID : TCAPIAD_04 : Verify that DTS user is unable to suspend device without having 'Publish and suspend apps and devices' privilege");
		/*
		 	Precondition: DTS user does not have "Publish and suspend apps and devices" privilege.
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/susDevice/721?revision=0 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		appDeviceControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		appDeviceControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Publish_suspend_apps_devices);
		appDeviceControl.click(AddUser.SAVE);
		// Logout user
		appDeviceControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = appDeviceControl.getCSRFToken();
		cookie = appDeviceControl.getCookie();
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
		appDeviceControl.addLog("ID : TCAPIAD_05 : Verify that DTS user is unable to restore device without having 'Publish and suspend apps and devices' privilege");
		/*
		 	Precondition: DTS user does not have "Publish and suspend apps and devices" privilege.
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/restoreDevice/721?revision=0 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		appDeviceControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		appDeviceControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Publish_suspend_apps_devices);
		appDeviceControl.click(AddUser.SAVE);
		// Logout user
		appDeviceControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = appDeviceControl.getCSRFToken();
		cookie = appDeviceControl.getCookie();
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
		appDeviceControl.addLog("ID : TCAPIAD_06 : Verify that DTS user is unable to delete device without having 'Publish and suspend apps and devices' privilege");
		/*
		 	Precondition: DTS user does not have "Publish and suspend apps and devices" privilege.
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/delDevice/197?revision=1 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		appDeviceControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		appDeviceControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Publish_suspend_apps_devices);
		appDeviceControl.click(AddUser.SAVE);
		// Logout user
		appDeviceControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = appDeviceControl.getCSRFToken();
		cookie = appDeviceControl.getCookie();
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
		appDeviceControl.addLog("ID : TCAPIAD_07 : Verify that Partner user is unable to get list apps/devices");
		/*
		 	Send a sample GET request using Partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/devices HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = appDeviceControl.getCSRFToken();
		cookie = appDeviceControl.getCookie();
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
		appDeviceControl.addLog("ID : TCAPIAD_08 : Verify that Partner user is unable to get an app/device's detail");
		/*
		 	Send a sample GET request using Partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/devices/197?revision=0 HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = appDeviceControl.getCSRFToken();
		cookie = appDeviceControl.getCookie();
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
		appDeviceControl.addLog("ID : TCAPIAD_09 : Verify that Partner user is unable to add new device");
		/*
		 	Send a sample POST request using partner's sesssion and CSRF token as following:
			POST http://devportal.dts.com/saap/api/devices HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = appDeviceControl.getCSRFToken();
		cookie = appDeviceControl.getCookie();
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
		appDeviceControl.addLog("ID : TCAPIAD_10 : Verify that Partner user is unable to edit device");
		/*
		 	Send a sample POST request using partner's sesssion and CSRF token as following:
			POST http://devportal.dts.com/saap/api/devices HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = appDeviceControl.getCSRFToken();
		cookie = appDeviceControl.getCookie();
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
		appDeviceControl.addLog("ID : TCAPIAD_11 : Verify that Partner user is unable to publish device");
		/*
		 	Send a sample POST request as following:
			GET http://devportal.dts.com/saap/api/publishDevice?devId=985 HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = appDeviceControl.getCSRFToken();
		cookie = appDeviceControl.getCookie();
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
		appDeviceControl.addLog("ID : TCAPIAD_12 : Verify that Partner user is unable to suspend device");
		/*
		 	Send a sample GET request using partner's session and CSRF token as following:
			GET http://devportal.dts.com/saap/api/susDevice/721?revision=0 HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = appDeviceControl.getCSRFToken();
		cookie = appDeviceControl.getCookie();
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
		appDeviceControl.addLog("ID : TCAPIAD_13 : Verify that Partner user is unable to restore device");
		/*
		 	Send a sample GET request using partner's sesssion and CSRF token as following:
			GET http://devportal.dts.com/saap/api/restoreDevice/721?revision=0 HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = appDeviceControl.getCSRFToken();
		cookie = appDeviceControl.getCookie();
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
		appDeviceControl.addLog("ID : TCAPIAD_14 : Verify that Partner  user is unable to delete device");
		/*
		 	Send a sample GET request using partner's sesssion and CSRF token as following:
			GET http://devportal.dts.com/saap/api/delDevice/197?revision=1 HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = appDeviceControl.getCSRFToken();
		cookie = appDeviceControl.getCookie();
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
