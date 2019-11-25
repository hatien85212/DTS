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

public class APISecurityAudioRoutes extends BasePage{
	private String csrfToken;
	private String cookie;
	private JSONObject jsonData;
	
	@Override
	protected void initData() {
		jsonData = APIUtil.parseDataToJsonObject(APIData.JSON_DATA_FILE);
	}
	
	/*
	 * Verify that DTS user is unable to update audio route without having "Manage audio routes" privilege
	 */
	@Test
	public void TCAPIAR_01(){
		audioControl.addLog("ID : TCAPIAR_01 : Verify that DTS user is unable to update audio route without having 'Manage audio routes' privilege");
		/*
		 	Precondition: DTS user does not have "Manage audio routes" privilege.
			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/route HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		audioControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_USER);
		// Click Edit link
		audioControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Manage_audio_routes);
		audioControl.click(AddUser.SAVE);
		// Logout user
		audioControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = audioControl.getCSRFToken();
		cookie = audioControl.getCookie();
		// Send a sample POST request as following:	POST http://devportal.dts.com/saap/api/route HTTP/1.1
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.AUDIO_ROUTE_DATA).toString());
		JSONObject requestResult = APIUtil.sendPost(APIData.UPDATE_AUDIO_ROUTES_URL, csrfToken, cookie, requestBody.toJSONString());
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to get list of audio route
	 */
	@Test
	public void TCAPIAR_02(){
		audioControl.addLog("ID : TCAPIAR_02 : Verify that Partner user is unable to get list of audio route");
		/*
			Send a sample GET request using JssionID and CSRF token of partner user  as following:
			GET http://devportal.dts.com/saap/api/route HTTP/1.1
		 */
		// Log into DTS portal as Partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Get user CSRF token and cookie
		csrfToken = audioControl.getCSRFToken();
		cookie = audioControl.getCookie();
		
		// Send a sample GET request using JssionID and CSRF token of partner user  as following: GET http://devportal.dts.com/saap/api/route HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.GET_AUDIO_ROUTES_LIST_URL, csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to get audio route detail
	 */
	@Test
	public void TCAPIAR_03(){
		audioControl.addLog("ID : TCAPIAR_03 : Verify that Partner user is unable to get audio route detail");
		/*
			Send a sample GET request using JssionID and CSRF token of partner user  as following:
			GET http://devportal.dts.com/saap/api/route/154?revision=0 HTTP/1.1
		 */
		// Log into DTS portal as partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Get user CSRF token and cookie
		csrfToken = audioControl.getCSRFToken();
		cookie = audioControl.getCookie();
		// Send a sample GET request using JssionID and CSRF token of partner user  as following:
		// GET http://devportal.dts.com/saap/api/route/154?revision=0 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.AUDIO_ROUTE_DETAIL_URL, csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to update audio routes
	 */
	@Test
	public void TCAPIAR_04(){
		audioControl.addLog("ID : TCAPIAR_04 : Verify that Partner user is unable to update audio routes");
		/*
			Send a sample POST  request using JssionID and CSRF token of partner user  as following:
			POST http://devportal.dts.com/saap/api/route HTTP/1.1
		 */
		// Log into DTS portal as partner user
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// Get user CSRF token and cookie
		csrfToken = audioControl.getCSRFToken();
		cookie = audioControl.getCookie();
		// Send a sample POST  request using JssionID and CSRF token of partner user  as following:
		// POST http://devportal.dts.com/saap/api/route HTTP/1.1
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.AUDIO_ROUTE_DATA).toString());
		JSONObject requestResult = APIUtil.sendPost(APIData.UPDATE_AUDIO_ROUTES_URL, csrfToken, cookie, requestBody.toJSONString());
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
}
