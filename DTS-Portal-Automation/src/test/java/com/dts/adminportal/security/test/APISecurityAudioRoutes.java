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

public class APISecurityAudioRoutes extends CreatePage{
	private String csrfToken;
	private String cookie;
	private JSONObject jsonData;
	private HomeController home;
	
	@BeforeClass
	public void beforeTest(){
		home = new HomeController(driver, siteBase);
		jsonData = APIUtil.parseDataToJsonObject("JsonData.json");
	}
	
	/*
	 * Verify that DTS user is unable to update audio route without having "Manage audio routes" privilege
	 */
	@Test
	public void TCAPIAR_01(){
		result.addLog("ID : TCAPIAR_01 : Verify that DTS user is unable to update audio route without having 'Manage audio routes' privilege");
		/*
		 	Precondition: DTS user does not have "Manage audio routes" privilege.
			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/route HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUser);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Manage_audio_routes);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(dtsUser, dtsPass);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
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
		result.addLog("ID : TCAPIAR_02 : Verify that Partner user is unable to get list of audio route");
		/*
			Send a sample GET request using JssionID and CSRF token of partner user  as following:
			GET http://devportal.dts.com/saap/api/route HTTP/1.1
		 */
		// Log into DTS portal as Partner user
		home.login(superpartneruser, superpartnerpassword);
		// Get user CSRF token and cookie
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		
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
		result.addLog("ID : TCAPIAR_03 : Verify that Partner user is unable to get audio route detail");
		/*
			Send a sample GET request using JssionID and CSRF token of partner user  as following:
			GET http://devportal.dts.com/saap/api/route/154?revision=0 HTTP/1.1
		 */
		// Log into DTS portal as partner user
		home.login(superpartneruser, superpartnerpassword);
		// Get user CSRF token and cookie
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
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
		result.addLog("ID : TCAPIAR_04 : Verify that Partner user is unable to update audio routes");
		/*
			Send a sample POST  request using JssionID and CSRF token of partner user  as following:
			POST http://devportal.dts.com/saap/api/route HTTP/1.1
		 */
		// Log into DTS portal as partner user
		home.login(superpartneruser, superpartnerpassword);
		// Get user CSRF token and cookie
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
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
