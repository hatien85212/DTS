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

public class APISecurityPromotions extends BasePage{
	private String csrfToken;
	private String cookie;
	private JSONObject jsonData;
	
	@Override
	protected void initData() {
		jsonData = APIUtil.parseDataToJsonObject(APIData.JSON_DATA_FILE);		
	}

	/*
	 * Verify that DTS user is unable to add new promotion without having "Add and manage promotions" privilege
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIPR_01(){
		promotionControl.addLog("ID : TCAPIPR_01 : Verify that DTS user is unable to add new promotion without having 'Add and manage promotions' privilege");
		/*
		 	Precondition: DTS user does not have "Add and manage promotions" privilege.
			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/promotion HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		promotionControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		promotionControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_promotions.getName());
		promotionControl.click(AddUser.SAVE);
		// Logout user
		promotionControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = promotionControl.getCSRFToken();
		cookie = promotionControl.getCookie();
		// Get published product ID
		JSONObject publishedProduct = APIUtil.parseDataToJsonObject(jsonData.get(APIData.PUBLISHED_PRODUCT_DATA).toString());
		String productID = publishedProduct.get("dtsId").toString();
		// Create request body
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_GLOBAL_PROMOTION_DATA).toString());
		requestBody.put("dtsId", RandomStringUtils.randomAlphanumeric(30));
		requestBody.put("accessoryDtsId", productID);
		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/promotion HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_PROMOTION_URL, csrfToken, cookie, requestBody.toJSONString());
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that DTS user is unable to edit promotion without having "Add and manage promotions" privilege
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIPR_02(){
		promotionControl.addLog("ID : TCAPIPR_02 : Verify that DTS user is unable to edit promotion without having 'Add and manage promotions' privilege");
		/*
		 	Precondition: DTS user does not have "Add and manage promotions" privilege.
			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/promotion HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		promotionControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		promotionControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_promotions.getName());
		promotionControl.click(AddUser.SAVE);
		// Logout user
		promotionControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = promotionControl.getCSRFToken();
		cookie = promotionControl.getCookie();
		// Get Promotion ID	and product ID
		JSONObject promotion = APIUtil.parseDataToJsonObject(jsonData.get(APIData.GLOBAL_PROMOTION_DATA).toString());
		String promotionID = promotion.get("id").toString();
		String productID = promotion.get("accessoryDtsId").toString();
		// Create request body
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.EDIT_GLOBAL_PROMOTION_DATA).toString());
		requestBody.put("id", promotionID);
		requestBody.put("accessoryDtsId", productID);
		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/promotion HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.EDIT_PROMOTION_URL, csrfToken, cookie, requestBody.toJSONString());
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that DTS user is unable to publish promotion without having "Add and manage promotions" privilege
	 */
	@Test
	public void TCAPIPR_03(){
		promotionControl.addLog("ID : TCAPIPR_03 : Verify that DTS user is unable to publish promotion without having 'Add and manage promotions' privilege");
		/*
		 	Precondition: DTS user does not have ""Add and manage promotions" privilege.
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/publishPromo/208 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		promotionControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		promotionControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_promotions.getName());
		promotionControl.click(AddUser.SAVE);
		// Logout user
		promotionControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = promotionControl.getCSRFToken();
		cookie = promotionControl.getCookie();
		// Get Promotion ID	and product ID
		JSONObject promotion = APIUtil.parseDataToJsonObject(jsonData.get(APIData.GLOBAL_PROMOTION_DATA).toString());
		String promotionID = promotion.get("id").toString();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishPromo/208 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_PROMOTION_URL(promotionID), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that DTS user is unable to Delete promotion without having "Add and manage promotions" privilege
	 */
	@Test
	public void TCAPIPR_04(){
		promotionControl.addLog("ID : TCAPIPR_04 : Verify that DTS user is unable to Delete promotion without having 'Add and manage promotions' privilege");
		/*
		 	Precondition: DTS user does not have "Add and manage promotions" privilege.
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/delPromo/211 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		promotionControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		promotionControl.click(UserMgmt.EDIT);
		// Disable "Manage audio routes" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_promotions.getName());
		promotionControl.click(AddUser.SAVE);
		// Logout user
		promotionControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = promotionControl.getCSRFToken();
		cookie = promotionControl.getCookie();
		// Get Promotion ID	and product ID
		JSONObject promotion = APIUtil.parseDataToJsonObject(jsonData.get(APIData.GLOBAL_PROMOTION_DATA).toString());
		String promotionID = promotion.get("id").toString();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishPromo/208 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.DELETE_PROMOTION_URL(promotionID), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to get promotions list with status "Active"
	 */
	@Test
	public void TCAPIPR_05(){
		promotionControl.addLog("ID : TCAPIPR_05 : Verify that Partner user is unable to get promotions list with status 'Active'");
		/*
		 	Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/getAllPromotions?status=Active HTTP/1.1
		 */
		// Login DTS portal as Partner user
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = promotionControl.getCSRFToken();
		cookie = promotionControl.getCookie();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/getAllPromotions?status=Active HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.VIEW_LIST_PROMOTION_URL, csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to get promotions list with status "Active" and a specific brand
	 */
	@Test
	public void TCAPIPR_06(){
		promotionControl.addLog("ID : TCAPIPR_06 : Verify that Partner user is unable to get promotions list with status 'Active' and a specific brand");
		/*
		 	Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/getAllPromotions?brandid=22&status=Active HTTP/1.1
		 */
		// Login DTS portal as Partner user
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = promotionControl.getCSRFToken();
		cookie = promotionControl.getCookie();
		// Get brand ID
		JSONObject brand = APIUtil.parseDataToJsonObject(jsonData.get(APIData.BRAND_DATA).toString());
		String brandID = brand.get("id").toString();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/getAllPromotions?brandid=22&status=Active HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.VIEW_LIST_PROMOTION_URL + "&brandid=" + brandID, csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to add new promotion
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIPR_07(){
		promotionControl.addLog("ID : TCAPIPR_07 : Verify that Partner user is unable to add new promotion");
		/*
		 	Send a sample POST request using Partner's session and CSRF token as following:
			POST http://devportal.dts.com/saap/api/promotion HTTP/1.1
		 */
		// Login DTS portal as Partner user
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = promotionControl.getCSRFToken();
		cookie = promotionControl.getCookie();
		// Get published product ID
		JSONObject publishedProduct = APIUtil.parseDataToJsonObject(jsonData.get(APIData.PUBLISHED_PRODUCT_DATA).toString());
		String productID = publishedProduct.get("dtsId").toString();
		// Create request body
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_GLOBAL_PROMOTION_DATA).toString());
		requestBody.put("dtsId", RandomStringUtils.randomAlphanumeric(30));
		requestBody.put("accessoryDtsId", productID);
		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/promotion HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_PROMOTION_URL, csrfToken, cookie, requestBody.toJSONString());
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to edit promotion
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIPR_08(){
		promotionControl.addLog("ID : TCAPIPR_08 : Verify that Partner user is unable to edit promotion");
		/*
		 	Send a sample POST request using Partner's session and CSRF token as following
			POST http://devportal.dts.com/saap/api/promotion HTTP/1.1
		 */
		// Login DTS portal as Partner user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = promotionControl.getCSRFToken();
		cookie = promotionControl.getCookie();
		// Get Promotion ID	and product ID
		JSONObject promotion = APIUtil.parseDataToJsonObject(jsonData.get(APIData.GLOBAL_PROMOTION_DATA).toString());
		long promotionID = Long.parseLong(promotion.get("id").toString());
		String productID = promotion.get("accessoryDtsId").toString();
		// Create request body
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.EDIT_GLOBAL_PROMOTION_DATA).toString());
		requestBody.put("id", promotionID);
		requestBody.put("accessoryDtsId", productID);
		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/promotion HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.EDIT_PROMOTION_URL, csrfToken, cookie, requestBody.toJSONString());
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to publish promotion
	 */
	@Test
	public void TCAPIPR_09(){
		promotionControl.addLog("ID : TCAPIPR_09 : Verify that Partner user is unable to publish promotion");
		/*
			Send a sample POST request using Partner's session and CSRF token as following
			GET http://devportal.dts.com/saap/api/publishPromo/208 HTTP/1.1
		 */
		// Login DTS portal as Partner user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = promotionControl.getCSRFToken();
		cookie = promotionControl.getCookie();
		// Get Promotion ID	and product ID
		JSONObject promotion = APIUtil.parseDataToJsonObject(jsonData.get(APIData.GLOBAL_PROMOTION_DATA).toString());
		String promotionID = promotion.get("id").toString();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishPromo/208 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_PROMOTION_URL(promotionID), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to Delete promotion
	 */
	@Test
	public void TCAPIPR_10(){
		promotionControl.addLog("ID : TCAPIPR_10 : Verify that Partner user is unable to Delete promotion");
		/*
			Send a sample POST request using Partner's session and CSRF token as following
			GET http://devportal.dts.com/saap/api/delPromo/211 HTTP/1.1
		 */
		// Login DTS portal as Partner user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = promotionControl.getCSRFToken();
		cookie = promotionControl.getCookie();
		// Get Promotion ID	and product ID
		JSONObject promotion = APIUtil.parseDataToJsonObject(jsonData.get(APIData.GLOBAL_PROMOTION_DATA).toString());
		String promotionID = promotion.get("id").toString();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishPromo/208 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.DELETE_PROMOTION_URL(promotionID), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
}
