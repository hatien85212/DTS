package com.dts.adminportal.security.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.Companies;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.security.util.APIData;
import com.dts.adminportal.security.util.APIUtil;
import com.dts.adminportal.util.DbUtil;

import junit.framework.Assert;

public class APISecurityCompanies extends BasePage {
	private String csrfToken;
	private String cookie;
	private JSONObject jsonData;
	
	@Override
	protected void initData() {
		jsonData = APIUtil.parseDataToJsonObject(APIData.JSON_DATA_FILE);
	}
	
	/*
	 * Verify that DTS user is unable to update company info without "Edit Company Info" privilege
	 */
//	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIC_01(){
		userControl.addLog("ID : TCAPIC_01 : Verify that DTS user is unable to update company info without 'Edit Company Info' privilege");
		/*
		 	Precondition: DTS user does not have "Edit Company Info" privilege
			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/partner HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Edit Company Info" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_Company_Info.getName());
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
//		long time = Calendar.getInstance().getTime().getTime();
//		JSONObject requestResult1 = APIUtil.sendGet("http://devportal.dts.com/saap/api/partner/558?time=" + time, productWf.csrfToken, productWf.cookie);
		// Create request body
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.EDIT_COMPANY_DATA).toString());
//		requestBody.remove("mainContact");
//		requestBody.put("mainContact", requestResult1.get("mainContact"));
//		requestBody.remove("partnerships");
//		requestBody.put("partnerships", requestResult1.get("mainContact"));
		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/partner HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.EDIT_COMPANY_URL, productWf.csrfToken, productWf.cookie, requestBody);
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
		userControl.addLog("ID : TCAPIC_02 : Verify that DTS user is unable to change primary contact without 'Edit Company Info' privilege");
		/*
		 	Precondition: DTS user does not have "Edit Company Info" privilege
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/changeMainContact?time=1436377936810&partnerid=23&userId=1118 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Edit Company Info" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_Company_Info.getName());
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.addLog("ID : TCAPIC_03: Verify that DTS user is unable to use account of other company to change primary contact when the 'Edit Company Info' privilege is enabled.");
		/*
		 	Precondition: DTS user does not have "Edit Company Info" privilege
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/changeMainContact?time=1436377936810&partnerid=23&userId=1118 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Edit Company Info" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_Company_Info.getName());
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIC_04(){
		userControl.addLog("ID : TCAPIC_04: Verify that DTS user is unable to add new brand without 'Edit Company Info' privilege");
		/*
		 	Precondition: DTS user does not have "Edit Company Info" privilege
			
			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/brands HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Edit Company Info" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_Company_Info.getName());
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login( DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIC_06(){
		userControl.addLog("ID : TCAPIC_06 : Verify that DTS user is unable to delete brand of a company without 'Edit Brand Info' privilege");
		/*
		 	Precondition: DTS user does not have "Edit Brand Info" privilege
			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/brands/52/status?status=INACTIVE HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Edit Company Info" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_brand_info.getName());
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.logout();
		// Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Delete brand
		APIUtil.sendPost(APIData.DELETE_BRAND_URL(newBrand.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to add new company without "Add and manage company accounts" privilege
	 */
	@Test
	public void TCAPIC_07(){
		userControl.addLog("ID : TCAPIC_07 : Verify that DTS user is unable to  add new company without 'Add and manage company accounts' privilege");
		/*
		 	Precondition: DTS user does not have "Add and manage company accounts" privilege
			Send a sample POST request as following:
			POST http://devportal.dts.com/saap/api/partner HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Add and manage company accounts" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_company_accounts.getName());
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		// Create request body
//		String requestBody = jsonData.get(APIData.ADD_COMPANY_DATA).toString();
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_COMPANY_DATA).toString());
		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/devices HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_COMPANY_URL, productWf.csrfToken, productWf.cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	
	
	/*
	 * Verify that DTS user is unable to  suspend company without "Add and manage company accounts" privilege
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIC_08(){
		userControl.addLog("ID : TCAPIC_08 : Verify that DTS user is unable to  suspend company without 'Add and manage company accounts' privilege");
		/*
		 	Precondition: DTS user does not have "Add and manage company accounts" privilege
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/suspartner/47?time=1436387161026&id=47 HTTP/1.1
		 */
		// Login DTS portal as DTS user
//		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		// Create new company
		productControl.click(PageHome.LINK_COMPANY);
		productControl.click(Companies.ADD_COMPANY);
		JSONObject requestResult1 = APIUtil.sendGet("http://devportal.dts.com/saap/api/assets/getDtsid", productWf.csrfToken, productWf.cookie);
		String dtsId = requestResult1.get("result").toString();
		JSONObject dataCompany = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_COMPANY_DATA).toString());
		dataCompany.put("keyDtsId", dtsId);
		dataCompany.put("name", "TestCorp-Security" + RandomStringUtils.randomNumeric(6));
		JSONObject newCompany = APIUtil.sendPost(APIData.ADD_COMPANY_URL, productWf.csrfToken, productWf.cookie, dataCompany);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Add and manage company accounts" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_company_accounts.getName());
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/suspartner/47?time=1436387161026&id=47 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_COMPANY_URL(newCompany.get("id").toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete company
		 */
		userControl.logout();
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Delete company
		APIUtil.sendGet(APIData.DELETE_COMPANY_URL(newCompany.get("id").toString()), csrfToken, cookie);
	}
	
	/*
	 * Verify that DTS user is unable to  restore company without "Add and manage company accounts" privilege
	 */
	@Test
	public void TCAPIC_09(){
		userControl.addLog("ID : TCAPIC_09 : Verify that DTS user is unable to  restore company without 'Add and manage company accounts' privilege");
		/*
		 	Precondition: DTS user does not have "Add and manage company accounts" privilege
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/respartner/470?time=1436389197051&id=470 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Add and manage company accounts" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_company_accounts.getName());
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIC_10(){
		userControl.addLog("ID : TCAPIC_10 : Verify that DTS user is unable to  delete company without 'Add and manage company accounts' privilege");
		/*
		 	Precondition: DTS user does not have "Add and manage company accounts" privilege
			Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/delpartner/451?time=1436389511235&id=451 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Create new company
		productControl.click(PageHome.LINK_COMPANY);
		productControl.click(Companies.ADD_COMPANY);
		JSONObject requestResult1 = APIUtil.sendGet("http://devportal.dts.com/saap/api/assets/getDtsid", productWf.csrfToken, productWf.cookie);
		String dtsId = requestResult1.get("result").toString();
		JSONObject dataCompany = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_COMPANY_DATA).toString());
		dataCompany.put("keyDtsId", dtsId);
		dataCompany.put("name", "TestCorp-Security" + RandomStringUtils.randomNumeric(6));
		JSONObject newCompany = APIUtil.sendPost(APIData.ADD_COMPANY_URL, productWf.csrfToken, productWf.cookie, dataCompany);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Add and manage company accounts" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_company_accounts.getName());
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/delpartner/451?time=1436389511235&id=451 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.DELETE_COMPANY_URL(newCompany.get("id").toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete company
		 */
		userControl.logout();
		// Login DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		// Delete company
		APIUtil.sendGet(APIData.DELETE_COMPANY_URL(newCompany.get("id").toString()), productWf.csrfToken, productWf.cookie);
	}
	
	/*
	 * Verify that Partner user is unable to update company info without "Edit Company Info" privilege
	 */
	@Test
	public void TCAPIC_11(){
		userControl.addLog("ID : TCAPIC_11 : Verify that Partner user is unable to update company info without 'Edit Company Info' privilege");
		/*
		 	Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/partner HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Add and manage company accounts" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_Company_Info.getName());
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.addLog("ID : TCAPIC_12 : Verify that Partner user is unable to change primary contact without 'Edit Company Info' privilege");
		/*
		 	Send a sample GET request using partner's session and csrf token as following:
			GET http://devportal.dts.com/saap/api/changeMainContact?time=1436377936810&partnerid=23&userId=1118 HTTP/1.1
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Add and manage company accounts" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_Company_Info.getName());
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.addLog("ID : TCAPIC_13 : Verify that Partner user is unable to use account of other company to change primary contact when the 'Edit Company Info' privilege is enabled.");
		/*
		 	Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/partners/changeMainContact?time=1436377936810&partnerid=23&userId=1118 HTTP/1.1
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Enable "Add and manage company accounts" privileges
		userControl.enableAllPrivileges();
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as partner user above
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get company ID and user ID
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/partners/changeMainContact?time=1441620028091&partnerid=23&userId=3143 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.CHANGE_PRIMARY_CONTACT_URL(PARTNER_DTS_USER_SER_COM_ID_OTHER, PARTNER_DTS_USER_SER_ID), productWf.csrfToken, productWf.cookie);
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
		userControl.addLog("ID : TCAPIC_14 : Verify that Partner user cannot change primary contact for another company when the 'Edit Company Info' privilege is enabled");
		/*
		 	Send a sample GET request as following:
			GET http://devportal.dts.com/saap/api/partners/changeMainContact?time=1436377936810&partnerid=23&userId=1118 HTTP/1.1
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Enable "Add and manage company accounts" privileges
		userControl.enableAllPrivileges();
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as partner user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIC_15(){
		userControl.addLog("ID : TCAPIC_15 : Verify that Partner user is unable to add new brand for user own company without 'Edit Company Info' privilege");
		/*
		 	Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/brands HTTP/1.1
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Add and manage company accounts" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_Company_Info.getName());
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Create request body
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_BRAND_DATA).toString());
		requestBody.put("name", "Brand-Security" + RandomStringUtils.randomNumeric(5));
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
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIC_16(){
		userControl.addLog("ID : TCAPIC_16 : Verify that Partner user is unable to edit brand of user own company without 'Edit Brand Info' privilege");
		/*
		 	Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/brands HTTP/1.1
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Add and manage company accounts" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_brand_info.getName());
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		// Create request body
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.EDIT_BRAND_DATA).toString());
		requestBody.put("name", SECURITY_BRAND_1);
		requestBody.put("id", securityBrandId2);
		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/promotion HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.EDIT_BRAND_URL, productWf.csrfToken, productWf.cookie, requestBody);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to delete brand of user own company without "Edit Brand Info" privilege
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIC_17(){
		userControl.addLog("ID : TCAPIC_17 : Verify that Partner user is unable to delete brand of user own company without 'Edit Brand Info' privilege");
		/*
		 	Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/brands/delete/590 HTTP/1.1
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Get a Company ID
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.COMPANY_DATA).toString());
		String companyID = company.get("id").toString();
		// Add new brand for the company above
		JSONObject dataBrand = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_BRAND_DATA).toString());
		dataBrand.put("partnerId", companyID);
		dataBrand.put("name", "Brand-Security" + RandomStringUtils.randomNumeric(6));
		JSONObject newBrand = APIUtil.sendPost(APIData.ADD_BRAND_URL, csrfToken, cookie, dataBrand);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Disable "Add and manage company accounts" privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_brand_info.getName());
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.logout();
		// Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Delete brand
		APIUtil.sendPost(APIData.DELETE_BRAND_URL(newBrand.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to add new brand for other companies when the  "Edit Company Info" privilege is enabled
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIC_18(){
		userControl.addLog("ID : TCAPIC_18 : Verify that Partner user is unable to add new brand for other companies when the  'Edit Company Info' privilege is enabled");
		/*
		 	Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/brands HTTP/1.1
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Enable "Edit Company Info" privileges
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_Company_Info.getName());
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get other company ID
		JSONObject otherCompany = APIUtil.parseDataToJsonObject(jsonData.get(APIData.OTHER_COMPANY_DATA).toString());
		String companyID = otherCompany.get("id").toString();
		// Create request body
		JSONObject requestBody = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_BRAND_DATA).toString());
		requestBody.put("partnerId", companyID);
		requestBody.put("name", "Brand-Security" + RandomStringUtils.randomNumeric(6));
		
		// id 702: "TestCorp-Security1"
		// id 558: "TestCorp-Security"
		// Send a sample POST request using partner's session and csrf token as following:
		// POST http://devportal.dts.com/saap/api/brands HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_BRAND_URL, productWf.csrfToken, productWf.cookie, requestBody);
		// Verify that new brand not exist on list brand of other company
		Assert.assertFalse(DbUtil.selectStatment("SELECT partner_id FROM brand WHERE name = '" + requestBody.get("name") + "'").equals(companyID));
		// Delete brand
		APIUtil.sendPost(APIData.DELETE_BRAND_URL(requestResult.get("id").toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to edit brand for other companies when the "Edit Brand Info" privilege is enabled
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIC_19(){
		userControl.addLog("ID : TCAPIC_19 : Verify that Partner user is unable to edit brand for other companies when the 'Edit Brand Info' privilege is enabled");
		/*
		 	Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/brands HTTP/1.1
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Enable "Edit Brand Info" privileges
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_brand_info.getName());
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
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
		JSONObject requestResult = APIUtil.sendPost(APIData.EDIT_BRAND_URL, productWf.csrfToken, productWf.cookie, requestBody);
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
		userControl.addLog("ID : TCAPIC_20 : Verify that Partner user is unable to delete brand for other companies when the  'Edit Brand Info' privilege is enabled");
		/*
		 	Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/brands/52/status?status=INACTIVE HTTP/1.1
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a Partner User from table
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Enable "Edit Brand Info" privileges
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_brand_info.getName());
		userControl.click(AddUser.SAVE);
		// Logout user
		userControl.logout();
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.addLog("ID : TCAPIC_21 : Verify that Partner user is unable to add new company");
		/*
		 	Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/partner HTTP/1.1
		 */
		// Login DTS portal as Partner user
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		JSONObject dataCompany = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_COMPANY_DATA).toString());
		// Send a sample POST request as following:
		// POST http://devportal.dts.com/saap/api/devices HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_COMPANY_URL, productWf.csrfToken, productWf.cookie, dataCompany);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
	}
	
	/*
	 * Verify that Partner user is unable to suspend other companies
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIC_22(){
		userControl.addLog("ID : TCAPIC_22 : Verify that Partner user is unable to suspend other companies");
		/*
		 	Send a sample GET request using partner's session and csrf token as following:
			GET http://devportal.dts.com/saap/api/suspartner/47?time=1436387161026&id=47 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Create new company
		productControl.click(PageHome.LINK_COMPANY);
		productControl.click(Companies.ADD_COMPANY);
		JSONObject requestResult1 = APIUtil.sendGet("http://devportal.dts.com/saap/api/assets/getDtsid", productWf.csrfToken, productWf.cookie);
		String dtsId = requestResult1.get("result").toString();
		JSONObject dataCompany = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_COMPANY_DATA).toString());
		dataCompany.put("keyDtsId", dtsId);
		dataCompany.put("name", "TestCorp-Security" + RandomStringUtils.randomNumeric(6));
		JSONObject newCompany = APIUtil.sendPost(APIData.ADD_COMPANY_URL, productWf.csrfToken, productWf.cookie, dataCompany);
		// Log out
		userControl.logout();
		// Login DTS portal as Partner user
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/suspartner/47?time=1436387161026&id=47 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_COMPANY_URL(newCompany.get("id").toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete company
		 */
		userControl.logout();
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Delete company
		APIUtil.sendGet(APIData.DELETE_COMPANY_URL(newCompany.get("id").toString()), csrfToken, cookie);
	}
	
	/*
	 * Verify that Partner user is unable to restore other companies
	 */
	@Test
	public void TCAPIC_23(){
		userControl.addLog("ID : TCAPIC_23 : Verify that Partner user is unable to restore other companies");
		/*
		 	Send a sample GET request using partner's session and csrf token as following:
			GET http://devportal.dts.com/saap/api/respartner/470?time=1436389197051&id=470 HTTP/1.1
		 */
		// Login DTS portal as DTS user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIC_24(){
		userControl.addLog("ID : TCAPIC_24 : Verify that Partner user is unable to delete other companies");
		/*
		 	Send a sample GET request using partner's session and csrf token as following:
			GET http://devportal.dts.com/saap/api/delpartner/451?time=1436389511235&id=451 HTTP/1.1
		 */
		// Login DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Create new company
		productControl.click(PageHome.LINK_COMPANY);
		productControl.click(Companies.ADD_COMPANY);
		JSONObject requestResult1 = APIUtil.sendGet("http://devportal.dts.com/saap/api/assets/getDtsid", productWf.csrfToken, productWf.cookie);
		String dtsId = requestResult1.get("result").toString();
		JSONObject dataCompany = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_COMPANY_DATA).toString());
		dataCompany.put("keyDtsId", dtsId);
		dataCompany.put("name", "TestCorp-Security" + RandomStringUtils.randomNumeric(6));
		JSONObject newCompany = APIUtil.sendPost(APIData.ADD_COMPANY_URL, productWf.csrfToken, productWf.cookie, dataCompany);
		// Log out
		userControl.logout();
		// Login DTS portal as Partner user
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/delpartner/451?time=1436389511235&id=451 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.DELETE_COMPANY_URL(newCompany.get("id").toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PEMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete company
		 */
		userControl.logout();
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Delete company
		APIUtil.sendGet(APIData.DELETE_COMPANY_URL(newCompany.get("id").toString()), csrfToken, cookie);
	}
	
	/*
	 * Verify that Partner user is unable to edit another brand which is not assigned to manage when the "Edit brand info" privilege is enabled
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIC_25(){
		userControl.addLog("ID : TCAPIC_25 : Verify that Partner user is unable to edit another brand which is not assigned to manage when the 'Edit brand info' privilege is enabled");
		/*
		 	Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/brands HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Get a Company ID
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.COMPANY_DATA).toString());
		String companyID = company.get("id").toString();
		// Add new brand for the company above
		JSONObject dataBrand = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_BRAND_DATA).toString());
		dataBrand.put("partnerId", companyID);
		dataBrand.put("name", "Brand-Security" + RandomStringUtils.randomNumeric(6));
		JSONObject newBrand = APIUtil.sendPost(APIData.ADD_BRAND_URL, csrfToken, cookie, dataBrand);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a partner user of company above
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Assign for user only has one brand privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_brand_info.getName());
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, PARTNER_SECURITY_BRAND_NAME);
		// Click Save link
		userControl.click(AddUser.SAVE);
		// Log out
		userControl.logout();
		// Log into DTS portal as Partner user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.logout();
		// Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Delete brand
		APIUtil.sendPost(APIData.DELETE_BRAND_URL(newBrand.get("id").toString()), csrfToken, cookie, "");
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a partner user above
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Assign for user only has one brand privilege
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, "All brands");
		// Click Save link
		userControl.click(AddUser.SAVE);
	}
	
	/*
	 * Verify that Partner user is unable to delete another brand which is not assigned when the "Edit brand info" privilege is enabled
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIC_26(){
		userControl.addLog("ID : TCAPIC_26 : Verify that Partner user is unable to delete another brand which is not assigned when the 'Edit brand info' privilege is enabled");
		/*
		 	Precondition: The partner user is only assigned for a specific brand of "Edit brand info" privilege.
			Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/brands/52/status?status=INACTIVE HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Get a Company ID
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.COMPANY_DATA).toString());
		String companyID = company.get("id").toString();
		// Add new brand for the company above
		JSONObject dataBrand = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_BRAND_DATA).toString());
		dataBrand.put("partnerId", companyID);
		dataBrand.put("name", "Brand-Security" + RandomStringUtils.randomNumeric(6));
		JSONObject newBrand = APIUtil.sendPost(APIData.ADD_BRAND_URL, csrfToken, cookie, dataBrand);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a partner user of company above
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Assign for user only has one brand privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_brand_info.getName());
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, PARTNER_SECURITY_BRAND_NAME);
		// Click Save link
		userControl.click(AddUser.SAVE);
		// Log out
		userControl.logout();
		// Log into DTS portal as Partner user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.logout();
		// Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Delete brand
		APIUtil.sendPost(APIData.DELETE_BRAND_URL(newBrand.get("id").toString()), csrfToken, cookie, "");
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a partner user above
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Assign for user only has one brand privilege
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, "All brands");
		// Click Save link
		userControl.click(AddUser.SAVE);
	}
	
	/*
	 * Verify that Partner user is unable to edit brand of other companies when the "Edit brand info" privilege is enabled with "all brands"
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIC_27(){
		userControl.addLog("ID : TCAPIC_27 : Verify that Partner user is unable to edit brand of other companies when the 'Edit brand info' privilege is enabled with 'all brands'");
		/*
		 	Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/brands HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Get a Company ID
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.OTHER_COMPANY_DATA).toString());
		String companyID = company.get("id").toString();
		// Add new brand for the company above
		JSONObject dataBrand = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_BRAND_DATA).toString());
		dataBrand.put("partnerId", companyID);
		dataBrand.put("name", "Brand-Security" + RandomStringUtils.randomNumeric(6));
		JSONObject newBrand = APIUtil.sendPost(APIData.ADD_BRAND_URL, csrfToken, cookie, dataBrand);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a partner user of company above
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Assign for user only has one brand privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_brand_info.getName());
		// Click Save link
		userControl.click(AddUser.SAVE);
		// Log out
		userControl.logout();
		// Log into DTS portal as Partner user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.logout();
		// Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Delete brand
		APIUtil.sendPost(APIData.DELETE_BRAND_URL(newBrand.get("id").toString()), csrfToken, cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to delete brand of other companies when the "Edit brand info" privilege is enabled with "all brands"
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void TCAPIC_28(){
		userControl.addLog("ID : TCAPIC_28 : Verify that Partner user is unable to delete brand of other companies when the 'Edit brand info' privilege is enabled with 'all brands'");
		/*
			Send a sample POST request using partner's session and csrf token as following:
			POST http://devportal.dts.com/saap/api/brands/52/status?status=INACTIVE HTTP/1.1
		 */
		// Login DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Get a Company ID
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.OTHER_COMPANY_DATA).toString());
		String companyID = company.get("id").toString();
		// Add new brand for the company above
		JSONObject dataBrand = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_BRAND_DATA).toString());
		dataBrand.put("partnerId", companyID);
		dataBrand.put("name", "Brand-Security" + RandomStringUtils.randomNumeric(6));
		JSONObject newBrand = APIUtil.sendPost(APIData.ADD_BRAND_URL, csrfToken, cookie, dataBrand);
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a partner user of company above
		userControl.selectUserInfoByEmail(PARTNER_DTS_SECURITY_USER);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// Assign for user only has one brand privilege
		userControl.enablePrivilege(AddUser.PRIVILEGES_TABLE, Privileges.privileges.Edit_brand_info.getName());
		// Click Save link
		userControl.click(AddUser.SAVE);
		// Log out
		userControl.logout();
		// Log into DTS portal as Partner user above
		loginControl.login(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
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
		userControl.logout();
		// Log into DTS portal as DTS user
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Get csrf token and cookie of user
		csrfToken = userControl.getCSRFToken();
		cookie = userControl.getCookie();
		// Delete brand
		APIUtil.sendPost(APIData.DELETE_BRAND_URL(newBrand.get("id").toString()), csrfToken, cookie, "");
	}
}
