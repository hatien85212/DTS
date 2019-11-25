package com.dts.adminportal.security.test;

import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.Companies;
import com.dts.adminportal.model.DeviceEdit;
import com.dts.adminportal.model.DeviceList;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.security.util.APIData;
import com.dts.adminportal.security.util.APIUtil;
import com.dts.adminportal.util.TestData;
//import com.gargoylesoftware.htmlunit.Page;

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
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.license_privileges.Add_and_manage_Apps_Devices.getName());
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_apps_and_devices.getName());
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
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.license_privileges.Add_and_manage_Apps_Devices.getName());
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_apps_and_devices.getName());
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
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.license_privileges.Publish_and_suspend_apps_and_devices.getName());
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Publish_and_suspend_apps_and_devices.getName());
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
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.license_privileges.Publish_and_suspend_apps_and_devices.getName());
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Publish_and_suspend_apps_and_devices.getName());
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
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Publish_and_suspend_apps_and_devices.getName());
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
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Publish_and_suspend_apps_and_devices.getName());
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
	
	/*
	 * Verify that Partner user is unable to edit device
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIAD_15_1(){
		appDeviceControl.addLog("ID : TCAPIAD_15 : Verify that the custom audio route is validated for only one audio route file when updating app/device");
		/*
		 	Send a sample POST request using partner's sesssion and CSRF token as following:
			POST http://devportal.dts.com/saap/api/devices HTTP/1.1
		 */
		// Login DTS portal as DTS user above
//		productWf.preconditionAndEnableAllPrivilege(DTS_SECURITY_USER);
//		loginControl.logout();
		productWf.preconditionAndEnableAllPrivilege(DTS_SECURITY_USER);
		appDeviceControl.click(PageHome.linkDevice);
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Hashtable<String,String> data = TestData.appDevicePublish();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		appDeviceControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		Integer DeviceId = appDeviceControl.getDeviceIdByName(data.get("name"));
		appDeviceControl.selectADeviceByName(data.get("name"));
		JSONObject requestResult2 = APIUtil.sendGet("http://devportal.dts.com/saap/api/devices/" + DeviceId +"?revision=0", productWf.csrfToken, productWf.cookie);
		
		JSONArray deviceRoutes = (JSONArray) requestResult2.get("deviceRoutes");
		JSONObject default_tuning = (JSONObject) deviceRoutes.get(0);
		String deviceRouteId = default_tuning.get("id").toString();
		String audioRouteId = default_tuning.get("assetId").toString();
		String audioRouteType = default_tuning.get("audioRouteType").toString();
		JSONObject tuning = (JSONObject) default_tuning.get("tuning");
		String mediaId = tuning.get("id").toString();
		
//		String requestBody = jsonData.get(APIData.EDIT_DEVICE_DATA).toString();
		JSONObject deviceData = APIUtil.parseDataToJsonObject(jsonData.get(APIData.EDIT_DEVICE_DATA).toString());
		JSONArray arrayTuning = (JSONArray) deviceData.get("custumTunings");
		JSONObject jsonTuning = (JSONObject) arrayTuning.get(0);
		String deviceRouteId1 = jsonTuning.get("deviceRouteId").toString();
		String audioRouteId1 = jsonTuning.get("audioRouteId").toString();
		String audioRouteType1 = jsonTuning.get("audioRouteType").toString();
		String mediaId1 = jsonTuning.get("mediaId").toString();
		deviceRouteId1 = deviceRouteId;
		audioRouteId1 = audioRouteId;
		audioRouteType1 = audioRouteType;
		mediaId1 = mediaId;
		jsonTuning.put("deviceRouteId", deviceRouteId1);
		jsonTuning.put("audioRouteId", audioRouteId1);
		jsonTuning.put("audioRouteType", audioRouteType1);
		jsonTuning.put("mediaId", mediaId1);
		deviceData.put("custumTunings", arrayTuning);
		deviceData.put("name", data.get("name"));
		JSONArray customTuning = (JSONArray) deviceData.get("custumTunings");
		JSONObject tuning0 = (JSONObject) customTuning.get(0);
		JSONObject tuning1 = new JSONObject();
		int routeId = Integer.parseInt(deviceRouteId);
		int routeId1 = routeId + 1;
//		tuning0.put("deviceRouteId", Integer.toString(routeId));
		tuning1.put("deviceRouteId", Integer.toString(routeId1));
		tuning1.put("audioRouteId", audioRouteId1);
		tuning1.put("audioRouteType", audioRouteType1);
		tuning1.put("mediaId", mediaId1);
		customTuning.add(1, tuning1);
//		tuning1 = (JSONObject) customTuning.get(1);
		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/devices HTTP/1.1
//		JSONObject requestResult1 = APIUtil.sendGet("http://devportal.dts.com/saap/api/devices/0", productWf.csrfToken, productWf.cookie);
//		String dtsId = requestResult1.get("keyDtsId").toString();
//		deviceData.put("keyDtsId", dtsId);
		System.out.println("data: " + deviceData);
		JSONObject requestResult = APIUtil.sendPost(APIData.EDIT_DEVICE_URL, productWf.csrfToken, productWf.cookie, deviceData);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
//		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to edit device
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIAD_15(){
		appDeviceControl.addLog("ID : TCAPIAD_15 : Verify that the custom audio route is validated for only one audio route file when updating app/device");
		/*
		 	Send a sample POST request using partner's sesssion and CSRF token as following:
			POST http://devportal.dts.com/saap/api/devices HTTP/1.1
		 */
		// Login DTS portal as DTS user above
//		productWf.preconditionAndEnableAllPrivilege(DTS_SECURITY_USER);
//		loginControl.logout();
		productWf.preconditionAndEnableAllPrivilege(DTS_SECURITY_USER);
		appDeviceControl.click(PageHome.linkDevice);
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Hashtable<String,String> data = TestData.appDevicePublish();
		data.remove("save");
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE_TYPE,DeviceEdit.routes.Line_out0.getName()); // LineOut0
		appDeviceControl.selectOptionByName(DeviceEdit.AUDIO_ROUTE,DeviceEdit.Standard_Accessories.Earbuds.getName()); // Earbuds
		appDeviceControl.uploadTuningAppDevice(DeviceEdit.ADD_CUSTOM_TUNING, AddEditProductModel.FileUpload.Earbuds_Lineout.getName());
		data.put("save", "yes");
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		appDeviceControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		Integer DeviceId = appDeviceControl.getDeviceIdByName(data.get("name"));
		appDeviceControl.selectADeviceByName(data.get("name"));
		JSONObject requestResult2 = APIUtil.sendGet("http://devportal.dts.com/saap/api/devices/" + DeviceId +"?revision=0", productWf.csrfToken, productWf.cookie);
		JSONArray deviceRoutes = (JSONArray) requestResult2.get("deviceRoutes");
		JSONObject tuning0 = (JSONObject) deviceRoutes.get(0);
		JSONObject tuning = (JSONObject) tuning0.get("tuning");
		String mediaId = tuning.get("id").toString();
		JSONObject deviceData = APIUtil.parseDataToJsonObject(jsonData.get(APIData.EDIT_DEVICE_DATA).toString());
		deviceData.put("name", requestResult2.get("name"));
		deviceData.put("id", requestResult2.get("id"));
		JSONArray arrayTuning = (JSONArray) deviceData.get("custumTunings");
		JSONObject jsonTuning0 = (JSONObject) arrayTuning.get(0);
		arrayTuning.add(1, jsonTuning0);
		JSONObject jsonTuning1 = (JSONObject) arrayTuning.get(0);
		String mediaId0 = jsonTuning0.get("mediaId").toString();
		String mediaId1 = jsonTuning1.get("mediaId").toString();
		mediaId0 = mediaId;
		mediaId1 = mediaId;
		jsonTuning0.put("mediaId", mediaId0);
		jsonTuning1.put("mediaId", mediaId1);
		JSONObject requestResult = APIUtil.sendPost(APIData.EDIT_DEVICE_URL, productWf.csrfToken, productWf.cookie, deviceData);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
//		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to edit device
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIAD_16(){
		appDeviceControl.addLog("ID : TCAPIAD_15 : Verify that the custom audio route is validated for only one audio route file when updating app/device");
		/*
		 	Send a sample POST request using partner's sesssion and CSRF token as following:
			POST http://devportal.dts.com/saap/api/devices HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
//		String requestBody = jsonData.get(APIData.EDIT_DEVICE_DATA).toString();
		JSONObject deviceData = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_DEVICE_DATA).toString());
		JSONObject requestResult1 = APIUtil.sendGet("http://devportal.dts.com/saap/api/devices/0", productWf.csrfToken, productWf.cookie);
		String keyDtsId = requestResult1.get("keyDtsId").toString();
		deviceData.put("keyDtsId", keyDtsId);
		deviceData.put("name", "Test" + RandomStringUtils.randomNumeric(6));
//		Tuning1.put("mediaId", "156775");
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_DEVICE_URL, productWf.csrfToken, productWf.cookie, deviceData);
		JSONObject deviceEdit = APIUtil.parseDataToJsonObject(jsonData.get(APIData.EDIT_DEVICE_DATA).toString());
		deviceEdit.put("id", requestResult.get("id"));
		deviceEdit.put("name", requestResult.get("name"));
		JSONArray customTuning = (JSONArray) deviceEdit.get("custumTunings");
		JSONObject tuning = (JSONObject) customTuning.get(0);
		tuning.put("mediaId", "156775");
		JSONObject requestResult2 = APIUtil.sendPost(APIData.ADD_DEVICE_URL, productWf.csrfToken, productWf.cookie, deviceEdit);
		/*
		 * {"apiErrorCode":"BAD_REQUEST","message":"<li>Missing tuning file or Another user has already edited this device.</li>"}
		 */
		Assert.assertTrue(requestResult2.get("message").toString().contains("Missing tuning file"));
//		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		// Delete Device
		JSONObject requestResult3 = APIUtil.sendGet("http://devportal.dts.com/saap/api/delDevice/" + requestResult.get("id") + "?revision=0", productWf.csrfToken, productWf.cookie);
		
	}
	

	@SuppressWarnings({ "unchecked", "unused" })
	@Test
	public void TCAdd_Device(){
		appDeviceControl.addLog("ID : TCAPIAD_15 : Verify that the custom audio route is validated for only one audio route file when updating app/device");
		/*
		 	Send a sample POST request using partner's sesssion and CSRF token as following:
			POST http://devportal.dts.com/saap/api/devices HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
//		String requestBody = jsonData.get(APIData.EDIT_DEVICE_DATA).toString();
		for (int i = 0; i < 50; i++) {
			JSONObject deviceData = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_DEVICE_DATA).toString());
			deviceData.put("name", "DeviceTest" + RandomStringUtils.randomNumeric(4));
			deviceData.put("brandId", "3916");
			deviceData.put("deviceProductType", "HPX_HIGH");
			JSONObject requestResult1 = APIUtil.sendGet("http://devportal.dts.com/saap/api/devices/0", productWf.csrfToken, productWf.cookie);
			String keyDtsId = requestResult1.get("keyDtsId").toString();
			deviceData.put("keyDtsId", keyDtsId);
			JSONObject requestResult = APIUtil.sendPost(APIData.ADD_DEVICE_URL, productWf.csrfToken, productWf.cookie, deviceData);
			System.out.println(i);
		}
		
		
	}
	
	@Test
	public void TCDelete_Com() throws InterruptedException{
		appDeviceControl.addLog("ID : TCAPIAD_15 : Verify that the custom audio route is validated for only one audio route file when updating app/device");
		/*
		 	Send a sample POST request using partner's sesssion and CSRF token as following:
			POST http://devportal.dts.com/saap/api/devices HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
//		String requestBody = jsonData.get(APIData.EDIT_DEVICE_DATA).toString();
		for (int i = 0; i < 50; i++) {
			companyControl.click(PageHome.LINK_COMPANY);
			Thread.sleep(1000);
			companyControl.selectFilterByName(Companies.FILTER, Companies.option.Suspended.getName());
			Integer id = companyControl.getCompanyIdByCharacter("Test");
			APIUtil.sendGet(APIData.DELETE_COMPANY_URL(id.toString()), productWf.csrfToken, productWf.cookie);
			System.out.println(i);
		}
		
		
	}
	
	@Test
	public void TCDelete_Device() throws InterruptedException{
		appDeviceControl.addLog("ID : TCAPIAD_15 : Verify that the custom audio route is validated for only one audio route file when updating app/device");
		/*
		 	Send a sample POST request using partner's sesssion and CSRF token as following:
			POST http://devportal.dts.com/saap/api/devices HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
//		String requestBody = jsonData.get(APIData.EDIT_DEVICE_DATA).toString();
		for (int i = 0; i < 100; i++) {
			companyControl.click(PageHome.LINK_APP_DEVICES);
			Thread.sleep(1000);
			List<String> list = appDeviceControl.getDeviceIdByCharacter("Device");
			System.out.println("List: " + list.get(0) + "  " + list.get(1));
			APIUtil.sendGet("http://devportal.dts.com/saap/api/delDevice/" + list.get(0) + "?revision=" + list.get(1), productWf.csrfToken, productWf.cookie);
			System.out.println(i);
		}
		
		
	}

}
