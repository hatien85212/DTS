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

public class APISecurityCompanies extends CreatePage {
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
	 * Verify that DTS user is unable to update company info without "Edit Company Info" privilege
	 */
	@Test
	public void TCAPIC_01(){
		result.addLog("ID : TCAPIC_01 : Verify that DTS user is unable to update company info without 'Edit Company Info' privilege");
		/*
		 	Precondition: DTS user does not have "Edit Company Info" privilege
			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/partner HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Edit Company Info" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Create request body
		String requestBody = jsonData.get(APIData.EDIT_COMPANY_DATA).toString();
		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/partner HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.EDIT_COMPANY_URL, csrfToken, cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that DTS user is unable to change primary contact without "Edit Company Info" privilege
	 */
	@Test
	public void TCAPIC_02(){
		result.addLog("ID : TCAPIC_02 : Verify that DTS user is unable to change primary contact without 'Edit Company Info' privilege");
		/*
		 	Precondition: DTS user does not have "Edit Company Info" privilege
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/changeMainContact?time=1436377936810&partnerid=23&userId=1118 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Edit Company Info" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get company ID and user ID
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.COMPANY_DATA).toString());
		String companyID = company.get("id").toString();
		JSONObject user = APIUtil.parseDataToJsonObject(jsonData.get(APIData.PARTNER_USER_DATA).toString());
		String userID = user.get("id").toString();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/changeMainContact?time=1436377936810&partnerid=23&userId=1118 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.CHANGE_PRIMARY_CONTACT_URL(companyID, userID), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that DTS user is unable to use account of other company to change primary contact when the "Edit Company Info" privilege is enabled.
	 */
	@Test
	public void TCAPIC_03(){
		result.addLog("ID : TCAPIC_03: Verify that DTS user is unable to use account of other company to change primary contact when the 'Edit Company Info' privilege is enabled.");
		/*
		 	Precondition: DTS user does not have "Edit Company Info" privilege
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/changeMainContact?time=1436377936810&partnerid=23&userId=1118 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Edit Company Info" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get company ID and user ID
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.COMPANY_DATA).toString());
		String companyID = company.get("id").toString();
		JSONObject user = APIUtil.parseDataToJsonObject(jsonData.get(APIData.PARTNER_USER_DATA).toString());
		String userID = user.get("id").toString();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/changeMainContact?time=1436377936810&partnerid=23&userId=1118 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.CHANGE_PRIMARY_CONTACT_URL(companyID, userID), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that DTS user is unable to  add new brand without "Edit Company Info" privilege
	 */
	@Test
	public void TCAPIC_04(){
		result.addLog("ID : TCAPIC_04: Verify that DTS user is unable to add new brand without 'Edit Company Info' privilege");
		/*
		 	Precondition: DTS user does not have "Edit Company Info" privilege
			
			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/brands HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Edit Company Info" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get company ID and user ID
		
		JSONObject branddata = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_BRAND_DATA).toString());
		branddata.put("name", RandomStringUtils.randomAlphabetic(10));
		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/brands HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_BRAND_URL, csrfToken, cookie, branddata);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that DTS user is unable to delete brand of a company without "Edit Brand Info" privilege
	 */
	@Test
	public void TCAPIC_06(){
		result.addLog("ID : TCAPIC_06 : Verify that DTS user is unable to delete brand of a company without 'Edit Brand Info' privilege");
		/*
		 	Precondition: DTS user does not have "Edit Brand Info" privilege
			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/brands/52/status?status=INACTIVE HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get a Company ID
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.COMPANY_DATA).toString());
		String companyID = company.get("id").toString();
		// Add new brand for the company above
		JSONObject dataBrand = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_BRAND_DATA).toString());
		dataBrand.put("partnerId", companyID);
		dataBrand.put("name", "Brand-Security" + RandomStringUtils.randomNumeric(6));
//		dataBrand = APIUtil.replaceStringValue(dataBrand, "partnerId", compayID);
//		dataBrand = APIUtil.replaceStringValue(dataBrand, "brandName", "Brand-Security" + RandomStringUtils.randomNumeric(6));
		JSONObject newBrand = APIUtil.sendPost(APIData.ADD_BRAND_URL, csrfToken, cookie, dataBrand);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Edit Company Info" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_brand_info);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/brands/52/status?status=INACTIVE HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.DELETE_BRAND_URL(newBrand.get("id").toString()), csrfToken, cookie, "");
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete brand
		 */
		// Log out
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete brand
		APIUtil.sendPost(APIData.DELETE_BRAND_URL(newBrand.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to add new company without "Add and manage company accounts" privilege
	 */
	@Test
	public void TCAPIC_07(){
		result.addLog("ID : TCAPIC_07 : Verify that DTS user is unable to  add new company without 'Add and manage company accounts' privilege");
		/*
		 	Precondition: DTS user does not have "Add and manage company accounts" privilege
			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/partner HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage company accounts" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_company_account);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Create request body
		String requestBody = jsonData.get(APIData.ADD_COMPANY_DATA).toString();
		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/devices HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_COMPANY_URL, csrfToken, cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	
	
	/*
	 * Verify that DTS user is unable to  suspend company without "Add and manage company accounts" privilege
	 */
	@Test
	public void TCAPIC_08(){
		result.addLog("ID : TCAPIC_08 : Verify that DTS user is unable to  suspend company without 'Add and manage company accounts' privilege");
		/*
		 	Precondition: DTS user does not have "Add and manage company accounts" privilege
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/suspartner/47?time=1436387161026&id=47 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Create new company
		String dataCompany = jsonData.get(APIData.ADD_COMPANY_DATA).toString();
		dataCompany = APIUtil.replaceStringValue(dataCompany, "dtsId", RandomStringUtils.randomAlphanumeric(30));
		dataCompany = APIUtil.replaceStringValue(dataCompany, "corpName", "TestCorp-Security" + RandomStringUtils.randomNumeric(6));
		JSONObject newCompany = APIUtil.sendPost(APIData.ADD_COMPANY_URL, csrfToken, cookie, dataCompany);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage company accounts" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_company_account);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/suspartner/47?time=1436387161026&id=47 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_COMPANY_URL(newCompany.get("id").toString()), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete company
		 */
		home.logout();
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete company
		APIUtil.sendGet(APIData.DELETE_COMPANY_URL(newCompany.get("id").toString()), csrfToken, cookie);
	}
	
	/*
	 * Verify that DTS user is unable to  restore company without "Add and manage company accounts" privilege
	 */
	@Test
	public void TCAPIC_09(){
		result.addLog("ID : TCAPIC_09 : Verify that DTS user is unable to  restore company without 'Add and manage company accounts' privilege");
		/*
		 	Precondition: DTS user does not have "Add and manage company accounts" privilege
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/respartner/470?time=1436389197051&id=470 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage company accounts" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_company_account);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get company ID
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.SUSPENDED_COMPANY_DATA).toString());
		String companyID = company.get("id").toString();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/respartner/470?time=1436389197051&id=470 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.RESTORE_COMPANY_URL(companyID), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that DTS user is unable to  delete company without "Add and manage company accounts" privilege
	 */
	@Test
	public void TCAPIC_10(){
		result.addLog("ID : TCAPIC_10 : Verify that DTS user is unable to  delete company without 'Add and manage company accounts' privilege");
		/*
		 	Precondition: DTS user does not have "Add and manage company accounts" privilege
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/delpartner/451?time=1436389511235&id=451 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Create new company
		String dataCompany = jsonData.get(APIData.ADD_COMPANY_DATA).toString();
		dataCompany = APIUtil.replaceStringValue(dataCompany, "dtsId", RandomStringUtils.randomAlphanumeric(30));
		dataCompany = APIUtil.replaceStringValue(dataCompany, "corpName", "TestCorp-Security" + RandomStringUtils.randomNumeric(6));
		JSONObject newCompany = APIUtil.sendPost(APIData.ADD_COMPANY_URL, csrfToken, cookie, dataCompany);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(dtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage company accounts" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_company_account);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(dtsUserSecurity, dtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/delpartner/451?time=1436389511235&id=451 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.DELETE_COMPANY_URL(newCompany.get("id").toString()), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete company
		 */
		home.logout();
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete company
		APIUtil.sendGet(APIData.DELETE_COMPANY_URL(newCompany.get("id").toString()), csrfToken, cookie);
	}
	
	/*
	 * Verify that Partner user is unable to update company info without "Edit Company Info" privilege
	 */
	@Test
	public void TCAPIC_11(){
		result.addLog("ID : TCAPIC_11 : Verify that Partner user is unable to update company info without 'Edit Company Info' privilege");
		/*
		 	Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/partner HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage company accounts" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Create request body
		String requestBody = jsonData.get(APIData.EDIT_COMPANY_DATA).toString();
		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/partner HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.EDIT_COMPANY_URL, csrfToken, cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to change primary contact without "Edit Company Info" privilege
	 */
	@Test
	public void TCAPIC_12(){
		result.addLog("ID : TCAPIC_12 : Verify that Partner user is unable to change primary contact without 'Edit Company Info' privilege");
		/*
		 	Send a sample GET request using partner's session and csrf token as following:
			GET http://devportal.dts.com/saap/api/changeMainContact?time=1436377936810&partnerid=23&userId=1118 HTTP/1.1
		 */
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage company accounts" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get company ID and user ID
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.COMPANY_DATA).toString());
		String companyID = company.get("id").toString();
		JSONObject user = APIUtil.parseDataToJsonObject(jsonData.get(APIData.PARTNER_USER_DATA).toString());
		String userID = user.get("id").toString();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/changeMainContact?time=1436377936810&partnerid=23&userId=1118 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.CHANGE_PRIMARY_CONTACT_URL(companyID, userID), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to use account of other company to change primary contact when the "Edit Company Info" privilege is enabled.
	 */
	@Test
	public void TCAPIC_13(){
		result.addLog("ID : TCAPIC_13 : Verify that Partner user is unable to use account of other company to change primary contact when the 'Edit Company Info' privilege is enabled.");
		/*
		 	Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/partners/changeMainContact?time=1436377936810&partnerid=23&userId=1118 HTTP/1.1
		 */
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Enable "Add and manage company accounts" privileges
		home.enableAllPrivilege();
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as partner user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get company ID and user ID
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/partners/changeMainContact?time=1441620028091&partnerid=23&userId=3143 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.CHANGE_PRIMARY_CONTACT_URL(pdtsUserSerCompId, dtsUserSerId), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user cannot change primary contact for another company when the "Edit Company Info" privilege is enabled
	 */
	@Test
	public void TCAPIC_14(){
		result.addLog("ID : TCAPIC_14 : Verify that Partner user cannot change primary contact for another company when the 'Edit Company Info' privilege is enabled");
		/*
		 	Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/partners/changeMainContact?time=1436377936810&partnerid=23&userId=1118 HTTP/1.1
		 */
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Enable "Add and manage company accounts" privileges
		home.enableAllPrivilege();
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as partner user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get company ID and user ID
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/partners/changeMainContact?time=1441620028091&partnerid=23&userId=3143 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.CHANGE_PRIMARY_CONTACT_URL(partnerAutoId, partneruserId), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to add new brand for user own company without "Edit Company Info" privilege
	 */
	@Test
	public void TCAPIC_15(){
		result.addLog("ID : TCAPIC_15 : Verify that Partner user is unable to add new brand for user own company without 'Edit Company Info' privilege");
		/*
		 	Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/brands HTTP/1.1
		 */
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage company accounts" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Create request body
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_BRAND_DATA).toString());
		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/promotion HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_BRAND_URL, csrfToken, cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to edit brand of user own company without "Edit Brand Info" privilege
	 */
	@Test
	public void TCAPIC_16(){
		result.addLog("ID : TCAPIC_16 : Verify that Partner user is unable to edit brand of user own company without 'Edit Brand Info' privilege");
		/*
		 	Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/brands HTTP/1.1
		 */
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage company accounts" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_brand_info);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Create request body
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.EDIT_BRAND_DATA).toString());
		requestBody.put("name", "Brand-Security" + RandomStringUtils.randomNumeric(6));
		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/promotion HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.EDIT_BRAND_URL, csrfToken, cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to delete brand of user own company without "Edit Brand Info" privilege
	 */
	@Test
	public void TCAPIC_17(){
		result.addLog("ID : TCAPIC_17 : Verify that Partner user is unable to delete brand of user own company without 'Edit Brand Info' privilege");
		/*
		 	Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/brands/delete/590 HTTP/1.1
		 */
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get a Company ID
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.COMPANY_DATA).toString());
		String companyID = company.get("id").toString();
		// Add new brand for the company above
		JSONObject dataBrand = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_BRAND_DATA).toString());
		dataBrand.put("partnerId", companyID);
		dataBrand.put("name", "Brand-Security" + RandomStringUtils.randomNumeric(6));
		JSONObject newBrand = APIUtil.sendPost(APIData.ADD_BRAND_URL, csrfToken, cookie, dataBrand);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Disable "Add and manage company accounts" privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_brand_info);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Send a sample POST request using partner's session and csrf token as following:
		// POST http://devportal.dts.com/saap/api/brands/delete/590 HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.DELETE_BRAND_URL(newBrand.get("id").toString()), csrfToken, cookie, "");
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete brand
		 */
		// Log out
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete brand
		APIUtil.sendPost(APIData.DELETE_BRAND_URL(newBrand.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to add new brand for other companies when the  "Edit Company Info" privilege is enabled
	 */
	@Test
	public void TCAPIC_18(){
		result.addLog("ID : TCAPIC_18 : Verify that Partner user is unable to add new brand for other companies when the  'Edit Company Info' privilege is enabled");
		/*
		 	Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/brands HTTP/1.1
		 */
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Enable "Edit Company Info" privileges
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_company_info);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get other company ID
		JSONObject otherCompany = APIUtil.parseDataToJsonObject(jsonData.get(APIData.OTHER_COMPANY_DATA).toString());
		String companyID = otherCompany.get("id").toString();
		// Create request body
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_BRAND_DATA).toString());
		requestBody.put("partnerId", companyID);
		requestBody.put("name", "Brand-Security" + RandomStringUtils.randomNumeric(6));
		// Send a sample POST request using partner's session and csrf token as following:
		// POST http://devportal.dts.com/saap/api/brands HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_BRAND_URL, csrfToken, cookie, requestBody);
		// Check if Issue PDPP-1328 found
		if (!requestResult.containsKey("message")) {
			result.addErrorLog("PDPP-1328: 092Db New user account form: DTS user is able to add new brand although the \"Edit Company Info\" privilege is disabled and \"Add and manage company accounts\" privilege is enabled");
		}
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to edit brand for other companies when the "Edit Brand Info" privilege is enabled
	 */
	@Test
	public void TCAPIC_19(){
		result.addLog("ID : TCAPIC_19 : Verify that Partner user is unable to edit brand for other companies when the 'Edit Brand Info' privilege is enabled");
		/*
		 	Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/brands HTTP/1.1
		 */
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Enable "Edit Brand Info" privileges
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_brand_info);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get other company ID
		JSONObject otherCompany = APIUtil.parseDataToJsonObject(jsonData.get(APIData.OTHER_COMPANY_DATA).toString());
		String companyID = otherCompany.get("id").toString();
		// Get other brand ID
		JSONObject otherBrand = APIUtil.parseDataToJsonObject(jsonData.get(APIData.OTHER_BRAND_DATA).toString());
		String brandID = otherBrand.get("id").toString();
		// Create request body
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.EDIT_BRAND_DATA).toString());
		requestBody.put("partnerId", companyID);
		requestBody.put("name", "Brand-Security" + RandomStringUtils.randomNumeric(6));
		requestBody.put("id", brandID);
		// Send a sample POST request using partner's session and csrf token as following:
		// POST http://devportal.dts.com/saap/api/brands HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.EDIT_BRAND_URL, csrfToken, cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to delete brand for other companies when the  "Edit Brand Info" privilege is enabled
	 */
	@Test
	public void TCAPIC_20(){
		result.addLog("ID : TCAPIC_20 : Verify that Partner user is unable to delete brand for other companies when the  'Edit Brand Info' privilege is enabled");
		/*
		 	Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/brands/52/status?status=INACTIVE HTTP/1.1
		 */
		home.login(superUsername, superUserpassword);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a Partner User from table
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Enable "Edit Brand Info" privileges
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_brand_info);
		home.click(AddUser.SAVE);
		// Logout user
		home.logout();
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get other brand ID
		JSONObject otherBrand = APIUtil.parseDataToJsonObject(jsonData.get(APIData.OTHER_BRAND_DATA).toString());
		String brandID = otherBrand.get("id").toString();
		// Send a sample POST request using partner's session and csrf token as following:
		// POST http://devportal.dts.com/saap/api/brands/52/status?status=INACTIVE HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.DELETE_BRAND_URL(brandID), csrfToken, cookie, "");
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to add new company
	 */
	@Test
	public void TCAPIC_21(){
		result.addLog("ID : TCAPIC_21 : Verify that Partner user is unable to add new company");
		/*
		 	Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/partner HTTP/1.1
		 */
		// Login DTS portal as Partner user
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Create request body
		String requestBody = jsonData.get(APIData.ADD_COMPANY_DATA).toString();
		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/devices HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_COMPANY_URL, csrfToken, cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to suspend other companies
	 */
	@Test
	public void TCAPIC_22(){
		result.addLog("ID : TCAPIC_22 : Verify that Partner user is unable to suspend other companies");
		/*
		 	Send a sample GET request using partner's session and csrf token as following:
			GET http://devportal.dts.com/saap/api/suspartner/47?time=1436387161026&id=47 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Create new company
		String dataCompany = jsonData.get(APIData.ADD_COMPANY_DATA).toString();
		dataCompany = APIUtil.replaceStringValue(dataCompany, "dtsId", RandomStringUtils.randomAlphanumeric(30));
		dataCompany = APIUtil.replaceStringValue(dataCompany, "corpName", "TestCorp-Security" + RandomStringUtils.randomNumeric(6));
		JSONObject newCompany = APIUtil.sendPost(APIData.ADD_COMPANY_URL, csrfToken, cookie, dataCompany);
		// Log out
		home.logout();
		// Login DTS portal as Partner user
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/suspartner/47?time=1436387161026&id=47 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_COMPANY_URL(newCompany.get("id").toString()), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete company
		 */
		home.logout();
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete company
		APIUtil.sendGet(APIData.DELETE_COMPANY_URL(newCompany.get("id").toString()), csrfToken, cookie);
	}
	
	/*
	 * Verify that Partner user is unable to restore other companies
	 */
	@Test
	public void TCAPIC_23(){
		result.addLog("ID : TCAPIC_23 : Verify that Partner user is unable to restore other companies");
		/*
		 	Send a sample GET request using partner's session and csrf token as following:
			GET http://devportal.dts.com/saap/api/respartner/470?time=1436389197051&id=470 HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get company ID
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.SUSPENDED_COMPANY_DATA).toString());
		String companyID = company.get("id").toString();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/respartner/470?time=1436389197051&id=470 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.RESTORE_COMPANY_URL(companyID), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to delete other companies
	 */
	@Test
	public void TCAPIC_24(){
		result.addLog("ID : TCAPIC_24 : Verify that Partner user is unable to delete other companies");
		/*
		 	Send a sample GET request using partner's session and csrf token as following:
			GET http://devportal.dts.com/saap/api/delpartner/451?time=1436389511235&id=451 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Create new company
		String dataCompany = jsonData.get(APIData.ADD_COMPANY_DATA).toString();
		dataCompany = APIUtil.replaceStringValue(dataCompany, "dtsId", RandomStringUtils.randomAlphanumeric(30));
		dataCompany = APIUtil.replaceStringValue(dataCompany, "corpName", "TestCorp-Security" + RandomStringUtils.randomNumeric(6));
		JSONObject newCompany = APIUtil.sendPost(APIData.ADD_COMPANY_URL, csrfToken, cookie, dataCompany);
		// Log out
		home.logout();
		// Login DTS portal as Partner user
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/delpartner/451?time=1436389511235&id=451 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.DELETE_COMPANY_URL(newCompany.get("id").toString()), csrfToken, cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete company
		 */
		home.logout();
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete company
		APIUtil.sendGet(APIData.DELETE_COMPANY_URL(newCompany.get("id").toString()), csrfToken, cookie);
	}
	
	/*
	 * Verify that Partner user is unable to edit another brand which is not assigned to manage when the "Edit brand info" privilege is enabled
	 */
	@Test
	public void TCAPIC_25(){
		result.addLog("ID : TCAPIC_25 : Verify that Partner user is unable to edit another brand which is not assigned to manage when the 'Edit brand info' privilege is enabled");
		/*
		 	Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/brands HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get a Company ID
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.COMPANY_DATA).toString());
		String companyID = company.get("id").toString();
		// Add new brand for the company above
		JSONObject dataBrand = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_BRAND_DATA).toString());
		dataBrand.put("partnerId", companyID);
		dataBrand.put("name", "Brand-Security" + RandomStringUtils.randomNumeric(6));
		JSONObject newBrand = APIUtil.sendPost(APIData.ADD_BRAND_URL, csrfToken, cookie, dataBrand);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a partner user of company above
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Assign for user only has one brand privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_brand_info);
		home.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, partner_security_brand_name);
		// Click Save link
		home.click(AddUser.SAVE);
		// Log out
		home.logout();
		// Log into DTS portal as Partner user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Create request body
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_BRAND_DATA).toString());
		requestBody.put("partnerId", companyID);
		requestBody.put("name", "Brand-Security" + RandomStringUtils.randomNumeric(6));
		requestBody.put("id", newBrand.get("id").toString());
		// Send a sample POST request using partner's session and csrf token as following:
		// POST http://devportal.dts.com/saap/api/brands HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.EDIT_BRAND_URL, csrfToken, cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Re-assign all brand privilege for user above
		 */
		// Log out
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete brand
		APIUtil.sendPost(APIData.DELETE_BRAND_URL(newBrand.get("id").toString()), csrfToken, cookie, "");
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a partner user above
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Assign for user only has one brand privilege
		home.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, "All brands");
		// Click Save link
		home.click(AddUser.SAVE);
	}
	
	/*
	 * Verify that Partner user is unable to delete another brand which is not assigned when the "Edit brand info" privilege is enabled
	 */
	@Test
	public void TCAPIC_26(){
		result.addLog("ID : TCAPIC_26 : Verify that Partner user is unable to delete another brand which is not assigned when the 'Edit brand info' privilege is enabled");
		/*
		 	Precondition: The partner user is only assigned for a specific brand of "Edit brand info" privilege.
			Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/brands/52/status?status=INACTIVE HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get a Company ID
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.COMPANY_DATA).toString());
		String companyID = company.get("id").toString();
		// Add new brand for the company above
		JSONObject dataBrand = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_BRAND_DATA).toString());
		dataBrand.put("partnerId", companyID);
		dataBrand.put("name", "Brand-Security" + RandomStringUtils.randomNumeric(6));
		JSONObject newBrand = APIUtil.sendPost(APIData.ADD_BRAND_URL, csrfToken, cookie, dataBrand);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a partner user of company above
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Assign for user only has one brand privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_brand_info);
		home.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, partner_security_brand_name);
		// Click Save link
		home.click(AddUser.SAVE);
		// Log out
		home.logout();
		// Log into DTS portal as Partner user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Send a sample POST request using partner's session and csrf token as following:
		// POST http://devportal.dts.com/saap/api/brands/52/status?status=INACTIVE HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.DELETE_BRAND_URL(newBrand.get("id").toString()), csrfToken, cookie, "");
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Re-assign all brand privilege for user above
		 */
		// Log out
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete brand
		APIUtil.sendPost(APIData.DELETE_BRAND_URL(newBrand.get("id").toString()), csrfToken, cookie, "");
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a partner user above
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Assign for user only has one brand privilege
		home.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, "All brands");
		// Click Save link
		home.click(AddUser.SAVE);
	}
	
	/*
	 * Verify that Partner user is unable to edit brand of other companies when the "Edit brand info" privilege is enabled with "all brands"
	 */
	@Test
	public void TCAPIC_27(){
		result.addLog("ID : TCAPIC_27 : Verify that Partner user is unable to edit brand of other companies when the 'Edit brand info' privilege is enabled with 'all brands'");
		/*
		 	Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/brands HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get a Company ID
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.OTHER_COMPANY_DATA).toString());
		String companyID = company.get("id").toString();
		// Add new brand for the company above
		JSONObject dataBrand = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_BRAND_DATA).toString());
		dataBrand.put("partnerId", companyID);
		dataBrand.put("name", "Brand-Security" + RandomStringUtils.randomNumeric(6));
		JSONObject newBrand = APIUtil.sendPost(APIData.ADD_BRAND_URL, csrfToken, cookie, dataBrand);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a partner user of company above
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Assign for user only has one brand privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_brand_info);
		// Click Save link
		home.click(AddUser.SAVE);
		// Log out
		home.logout();
		// Log into DTS portal as Partner user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Create request body
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_BRAND_DATA).toString());
		requestBody.put("partnerId", companyID);
		requestBody.put("name", "Brand-Security" + RandomStringUtils.randomNumeric(6));
		requestBody.put("id", newBrand.get("id").toString());
		// Send a sample POST request using partner's session and csrf token as following:
		// POST http://devportal.dts.com/saap/api/brands HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.EDIT_BRAND_URL, csrfToken, cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Re-assign all brand privilege for user above
		 */
		// Log out
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete brand
		APIUtil.sendPost(APIData.DELETE_BRAND_URL(newBrand.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to delete brand of other companies when the "Edit brand info" privilege is enabled with "all brands"
	 */
	@Test
	public void TCAPIC_28(){
		result.addLog("ID : TCAPIC_28 : Verify that Partner user is unable to delete brand of other companies when the 'Edit brand info' privilege is enabled with 'all brands'");
		/*
			Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/brands/52/status?status=INACTIVE HTTP/1.1
		 */
		// Login DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Get a Company ID
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.OTHER_COMPANY_DATA).toString());
		String companyID = company.get("id").toString();
		// Add new brand for the company above
		JSONObject dataBrand = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_BRAND_DATA).toString());
		dataBrand.put("partnerId", companyID);
		dataBrand.put("name", "Brand-Security" + RandomStringUtils.randomNumeric(6));
		JSONObject newBrand = APIUtil.sendPost(APIData.ADD_BRAND_URL, csrfToken, cookie, dataBrand);
		// Navigate to User page
		home.click(Xpath.LINK_USERS);
		// Select a partner user of company above
		home.selectUserByEmail(pdtsUserSecurity);
		// Click Edit link
		home.click(UserMgmt.EDIT);
		// Assign for user only has one brand privilege
		home.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.Edit_brand_info);
		// Click Save link
		home.click(AddUser.SAVE);
		// Log out
		home.logout();
		// Log into DTS portal as Partner user above
		home.login(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Send a sample POST request using partner's session and csrf token as following:
		// POST http://devportal.dts.com/saap/api/brands/52/status?status=INACTIVE HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.DELETE_BRAND_URL(newBrand.get("id").toString()), csrfToken, cookie, "");
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete brand
		 */
		// Log out
		home.logout();
		// Log into DTS portal as DTS user
		home.login(superUsername, superUserpassword);
		// Get csrf token and cookie of user
		csrfToken = home.getCSRFToken();
		cookie = home.getCookie();
		// Delete brand
		APIUtil.sendPost(APIData.DELETE_BRAND_URL(newBrand.get("id").toString()), csrfToken, cookie, "");
	}
}
