package com.dts.adminportal.security.test;


import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONArray;
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
		// Select a DTS User from table
		userControl.selectUserInfoByEmail(DTS_USER);
		// Click Edit link
		audioControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Manage_audio_routes.getName());
		audioControl.click(AddUser.SAVE);
		// Logout user
		audioControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(DTS_USER, DTS_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = audioControl.getCSRFToken();
		cookie = audioControl.getCookie();
		System.out.println("csrfToken: " + csrfToken);
		System.out.println("cookie: " + cookie);
		// Send a sample POST request as following:	POST http://devportal.dts.com/saap/api/route HTTP/1.1
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.AUDIO_ROUTE_DATA).toString());
		JSONObject requestResult = APIUtil.sendPost(APIData.UPDATE_AUDIO_ROUTES_URL, csrfToken, cookie, requestBody.toJSONString());
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		loginControl.logout();
		
		// Teardown
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		audioControl.click(PageHome.LINK_USERS);
		// Select a DTS User from table
		userControl.selectUserInfoByEmail(DTS_USER);
		// Click Edit link
		audioControl.click(UserMgmt.EDIT);
		// Enable "Manage audio routes" privileges again
		userControl.enableAllPrivileges();
		audioControl.click(AddUser.SAVE);
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

	/*
	 * Verify that DTS user is able to update audio route with valid value of tuning, product images, connection type, model name and local name when the "Manage audio routes" privilege is enabled.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@Test
	public void TCAPIAR_05(){
		audioControl.addLog("ID : TCAPIAR_05: Verify that DTS user is able to update audio route with valid value of tuning, product images, connection type, model name and local name when the 'Manage audio routes' privilege is enabled.");
		/*
		 	Pre-condition: The "Manage audio routes" privilege of DTS user is enabled
		 	Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/route HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		audioControl.click(PageHome.linkAudioroutes);
		JSONObject requestResult2 = APIUtil.sendGet("http://devportal.dts.com/saap/api/route", productWf.csrfToken, productWf.cookie);
		JSONArray Accessories = (JSONArray) requestResult2.get("standardAccessories");
		JSONObject modelName = (JSONObject)Accessories.get(0);
//		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
//		audioControl.click(AudioRoutesInfo.EDIT);
		// Send GET request when click on Over_Ear_Headphones
		JSONObject requestResult3 = APIUtil.sendGet("http://devportal.dts.com/saap/api/route/" + modelName.get("id") +"?revision=" + modelName.get("revision"), productWf.csrfToken, productWf.cookie);
		JSONArray localename = (JSONArray) requestResult3.get("localeNames");
		for (int i = 0; i < localename.size(); i++) {
		     JSONObject locale = (JSONObject)localename.get(i);
		     locale.remove("createdOn");
		     locale.remove("languageCodeDisplay");
		     locale.remove("updatedOn");
		     locale.remove("lockVersion");
		}
		// Send a sample POST request as following:	POST http://devportal.dts.com/saap/api/route HTTP/1.1
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.AUDIO_ROUTE_DATA).toString());
		requestBody.remove("salesforceId");
		requestBody.put("salesforceId", RandomStringUtils.randomNumeric(7));
		requestBody.remove("images");
		requestBody.put("images", requestResult3.get("images"));
		requestBody.remove("tunings");
		requestBody.put("tunings", requestResult3.get("tunings"));
		requestBody.remove("localeNames");
//		requestBody.put("localeNames", requestResult3.get("localeNames"));
		audioControl.click(PageHome.linkAudioroutes);
//		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		JSONObject requestResult = APIUtil.sendPost(APIData.UPDATE_AUDIO_ROUTES_URL, productWf.csrfToken, productWf.cookie, requestBody);
		/*
		 * The audio route is updated successfully
		 */
		
	}
	
	/*
	 * Verify that DTS user is able to update audio route with valid value of tuning, product images, connection type, model name and local name when the "Manage audio routes" privilege is enabled.
	 */
	@SuppressWarnings({ "unchecked" })
	@Test
	public void TCAPIAR_06(){
		audioControl.addLog("ID : TCAPIAR_06: Verify that DTS user is  unable to update audio route with invalid tuning id");
		/*
		 	Pre-condition: The "Manage audio routes" privilege of DTS user is enabled
		 	Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/route HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		audioControl.click(PageHome.linkAudioroutes);
		JSONObject requestResult2 = APIUtil.sendGet("http://devportal.dts.com/saap/api/route", productWf.csrfToken, productWf.cookie);
		JSONArray Accessories = (JSONArray) requestResult2.get("standardAccessories");
		JSONObject modelName = (JSONObject)Accessories.get(0);
//		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
//		audioControl.click(AudioRoutesInfo.EDIT);
		// Send GET request when click on Over_Ear_Headphones
		JSONObject requestResult3 = APIUtil.sendGet("http://devportal.dts.com/saap/api/route/" + modelName.get("id") +"?revision=" + modelName.get("revision"), productWf.csrfToken, productWf.cookie);
		JSONArray localename = (JSONArray) requestResult3.get("localeNames");
		for (int i = 0; i < localename.size(); i++) {
		     JSONObject locale = (JSONObject)localename.get(i);
		     locale.remove("createdOn");
		     locale.remove("languageCodeDisplay");
		     locale.remove("updatedOn");
		     locale.remove("lockVersion");
		}
		// Send a sample POST request as following:	POST http://devportal.dts.com/saap/api/route HTTP/1.1
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.AUDIO_ROUTE_DATA).toString());
//		requestBody.remove("localeNames");
		requestBody.remove("salesforceId");
		requestBody.put("salesforceId", RandomStringUtils.randomNumeric(7));
		requestBody.remove("images");
		requestBody.put("images", requestResult3.get("images"));
		requestBody.remove("tunings");
		requestBody.put("tunings", requestResult3.get("tunings"));
		requestBody.remove("localeNames");
//		requestBody.put("localeNames", requestResult3.get("localeNames"));
		JSONArray images = (JSONArray) requestBody.get("images");
		JSONObject image = (JSONObject) images.get(0);
		Object id_image = image.get("id");
		JSONArray tunings = (JSONArray) requestBody.get("tunings");
		JSONObject tuning = (JSONObject) tunings.get(0);
		Object id_tuning = tuning.get("id");
		id_tuning = id_image;
		tuning.put("id", id_tuning);
		requestBody.put("tunings", tunings);
//		JSONObject requestBody11 = APIUtil.parseDataToJsonObject(jsonData.get(APIData.CHECK_DATA).toString());
//		audioControl.click(PageHome.linkAudioroutes);
//		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		JSONObject requestResult = APIUtil.sendPost(APIData.UPDATE_AUDIO_ROUTES_URL, productWf.csrfToken, productWf.cookie, requestBody);
		/*
		 * The audio route is updated successfully
		 */
		Assert.assertTrue(requestResult.get("message").toString().contains("Invalid type of tuning"));
	}
	
//	/*
//	 * Verify that Partner user is unable to get audio route detail
//	 */
//	@Test
//	public void TCAPIAR_07(){
//		audioControl.addLog("ID : TCAPIAR_03 : Verify that Partner user is unable to get audio route detail");
//		/*
//			Send a sample GET request using JssionID and CSRF token of partner user  as following:
//			GET http://devportal.dts.com/saap/api/route/154?revision=0 HTTP/1.1
//		 */
//		// Log into DTS portal as partner user
////		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//		productWf.loginAndSetTokenAndCookie(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//		// Get user CSRF token and cookie
//		// Send a sample GET request using JssionID and CSRF token of partner user  as following:
//		// GET http://devportal.dts.com/saap/api/route/154?revision=0 HTTP/1.1
////		JSONObject requestResult = APIUtil.sendPostUpload(APIData.AUDIO_ROUTE_DETAIL_URL, productWf.csrfToken, productWf.cookie, data);
//		/*
//		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
//		 */
//		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
//	}
	
	
	
	/*
	 * Verify that DTS user is able to update audio route with valid value of tuning, product images, connection type, model name and local name when the "Manage audio routes" privilege is enabled.
	 */
	@SuppressWarnings({ "unchecked" })
	@Test
	public void TCAPIAR_08(){
		audioControl.addLog("ID : TCAPIAR_08: Verify that DTS user is unable to update catalog image using id of audio route.");
		/*
		 	Pre-condition: The "Manage audio routes" privilege of DTS user is enabled
		 	Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/route HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		audioControl.click(PageHome.linkAudioroutes);
		JSONObject requestResult2 = APIUtil.sendGet("http://devportal.dts.com/saap/api/route", productWf.csrfToken, productWf.cookie);
		JSONArray Accessories = (JSONArray) requestResult2.get("standardAccessories");
		JSONObject modelName = (JSONObject)Accessories.get(0);
//		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
//		audioControl.click(AudioRoutesInfo.EDIT);
		// Send GET request when click on Over_Ear_Headphones
		JSONObject requestResult3 = APIUtil.sendGet("http://devportal.dts.com/saap/api/route/" + modelName.get("id") +"?revision=" + modelName.get("revision"), productWf.csrfToken, productWf.cookie);
		JSONArray localename = (JSONArray) requestResult3.get("localeNames");
		for (int i = 0; i < localename.size(); i++) {
		     JSONObject locale = (JSONObject)localename.get(i);
		     locale.remove("createdOn");
		     locale.remove("languageCodeDisplay");
		     locale.remove("updatedOn");
		     locale.remove("lockVersion");
		}
		// Send a sample POST request as following:	POST http://devportal.dts.com/saap/api/route HTTP/1.1
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.AUDIO_ROUTE_DATA).toString());
		requestBody.remove("salesforceId");
		requestBody.put("salesforceId", RandomStringUtils.randomNumeric(7));
		requestBody.remove("images");
		requestBody.put("images", requestResult3.get("images"));
		requestBody.remove("tunings");
		requestBody.put("tunings", requestResult3.get("tunings"));
		requestBody.remove("localeNames");
		requestBody.put("localeNames", requestResult3.get("localeNames"));
		JSONArray tunings = (JSONArray) requestBody.get("tunings");
		JSONObject tuning = (JSONObject) tunings.get(0);
		Object id_tuning = tuning.get("id");
		
		JSONArray images = (JSONArray) requestBody.get("images");
		JSONObject image = (JSONObject) images.get(0);
		Object id_image = image.get("id");
		id_image = id_tuning;
		image.put("id", id_image);
		requestBody.put("images", images);
//		JSONObject requestBody11 = APIUtil.parseDataToJsonObject(jsonData.get(APIData.CHECK_DATA).toString());
//		audioControl.click(PageHome.linkAudioroutes);
//		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		JSONObject requestResult = APIUtil.sendPost(APIData.UPDATE_AUDIO_ROUTES_URL, productWf.csrfToken, productWf.cookie, requestBody);
		/*
		 * The audio route is updated successfully
		 */
		Assert.assertTrue(requestResult.get("message").toString().contains("Invalid file type"));
	}
	
	/*
	 * Verify that DTS user is able to update audio route with valid value of tuning, product images, connection type, model name and local name when the "Manage audio routes" privilege is enabled.
	 */
	@SuppressWarnings({ "unchecked" })
	@Test
	public void TCAPIAR_09(){
		audioControl.addLog("ID : TCAPIAR_09: Verify that DTS user is unable to update audio route with invalid value of Input specification");
		/*
		 	Pre-condition: The "Manage audio routes" privilege of DTS user is enabled
		 	Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/route HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		audioControl.click(PageHome.linkAudioroutes);
		JSONObject requestResult2 = APIUtil.sendGet("http://devportal.dts.com/saap/api/route", productWf.csrfToken, productWf.cookie);
		JSONArray Accessories = (JSONArray) requestResult2.get("standardAccessories");
		JSONObject modelName = (JSONObject)Accessories.get(0);
//		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
//		audioControl.click(AudioRoutesInfo.EDIT);
		// Send GET request when click on Over_Ear_Headphones
		JSONObject requestResult3 = APIUtil.sendGet("http://devportal.dts.com/saap/api/route/" + modelName.get("id") +"?revision=" + modelName.get("revision"), productWf.csrfToken, productWf.cookie);
		JSONArray localename = (JSONArray) requestResult3.get("localeNames");
		for (int i = 0; i < localename.size(); i++) {
		     JSONObject locale = (JSONObject)localename.get(i);
		     locale.remove("createdOn");
		     locale.remove("languageCodeDisplay");
		     locale.remove("updatedOn");
		     locale.remove("lockVersion");
		}
		// Send a sample POST request as following:	POST http://devportal.dts.com/saap/api/route HTTP/1.1
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.AUDIO_ROUTE_DATA).toString());
		requestBody.remove("salesforceId");
		requestBody.put("salesforceId", RandomStringUtils.randomNumeric(7));
		requestBody.remove("images");
		requestBody.put("images", requestResult3.get("images"));
		requestBody.remove("tunings");
		requestBody.put("tunings", requestResult3.get("tunings"));
		requestBody.remove("localeNames");
//		requestBody.put("localeNames", requestResult3.get("localeNames"));
		requestBody.put("connectionType", RandomStringUtils.randomAlphabetic(5));
//		JSONArray images = (JSONArray) requestBody.get("images");
//		JSONObject image0 = (JSONObject) images.get(0);
//		image0.remove("id");
		audioControl.click(PageHome.linkAudioroutes);
//		audioControl.selectAnAudioRouteByName(AudioRoutes.Standard_Routes.Over_Ear_Headphones.getName());
		JSONObject requestResult = APIUtil.sendPost(APIData.UPDATE_AUDIO_ROUTES_URL, productWf.csrfToken, productWf.cookie, requestBody.toJSONString());
		Assert.assertTrue(requestResult.get("message").toString().contains("Connection type is invalid"));
		
	}
	
	/*
	 * Verify that DTS user is unable to update audio route with empty name
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIAR_10(){
		audioControl.addLog("ID : TCAPIAR_10: Verify that DTS user is unable to update audio route with empty name.");
		/*
		 	Pre-condition: The "Manage audio routes" privilege of DTS user is enabled.
		 	Send a sample POST request to update audio route with empty name following:
			POST http://devportal.dts.com/saap/api/route HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		audioControl.click(PageHome.linkAudioroutes);
		JSONObject requestResult2 = APIUtil.sendGet("http://devportal.dts.com/saap/api/route", productWf.csrfToken, productWf.cookie);
		JSONArray Accessories = (JSONArray) requestResult2.get("standardAccessories");
		JSONObject modelName = (JSONObject)Accessories.get(0);
		// Get csrf token and cookie of user
		JSONObject requestResult = APIUtil.sendGet("http://devportal.dts.com/saap/api/route/" + modelName.get("id") +"?revision=" + modelName.get("revision"), productWf.csrfToken, productWf.cookie);
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.AUDIO_ROUTE_DATA).toString());
		requestBody.remove("images");
		requestBody.put("images", requestResult.get("images"));
		requestBody.remove("tunings");
		requestBody.put("tunings", requestResult.get("tunings"));
		requestBody.remove("localeNames");
		requestBody.remove("modelName");
		requestBody.put("modelName", "");
		// Send a sample POST request as following:	POST http://devportal.dts.com/saap/api/route HTTP/1.1
		JSONObject requestResult1 = APIUtil.sendPost(APIData.UPDATE_AUDIO_ROUTES_URL, productWf.csrfToken, productWf.cookie, requestBody);
		Assert.assertTrue(requestResult1.get("message").toString().contains("Display name cannot be empty"));
	}

}
