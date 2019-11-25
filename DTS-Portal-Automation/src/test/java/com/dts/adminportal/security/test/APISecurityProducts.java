package com.dts.adminportal.security.test;

import junit.framework.Assert;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.controller.ProductController;
import com.dts.adminportal.controller.UsersController;
import com.dts.adminportal.security.util.APIData;
import com.dts.adminportal.security.util.APIUtil;
import com.dts.adminportal.security.workflow.APISecurityProductWorkflow;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AccessoryInfo;
import dts.com.adminportal.model.AccessoryMain;
import dts.com.adminportal.model.AddAccessory;
import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.AddVariant;
import dts.com.adminportal.model.PartnerAccessoryInfo;
import dts.com.adminportal.model.PartnerAddAccessory;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.TuningRequestForm;
import dts.com.adminportal.model.UserInfo;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.VariantInfo;
import dts.com.adminportal.model.Xpath;

public class APISecurityProducts extends CreatePage{
//	private String csrfToken;
//	private String cookie;
	private JSONObject jsonData;
	private HomeController homeControl;
	private UsersController userControl;
	private ProductController productControl;
	private APISecurityProductWorkflow secureProductWf;
	
	@BeforeClass
	public void beforeTest(){
		homeControl = new HomeController(driver, siteBase);
		userControl = new UsersController(driver, siteBase);
		productControl = new ProductController(driver, siteBase);
		jsonData = APIUtil.parseDataToJsonObject(APIData.JSON_DATA_FILE);
		secureProductWf = new APISecurityProductWorkflow(homeControl, productControl, userControl, jsonData);
	}

	/*
	 * Verify that DTS user is unable to add, edit and create new products version when the "Add and manage products" privilege is disabled
	 */
	@Test
	public void APIPV_01(){
		result.addLog("ID : APIPV_01 : Verify that DTS user is unable to add, edit and create new products version when the \"Add and manage products\" privilege is disabled");
		/*
		 	Precondition: The "Add and manage products" privilege is disabled
			POST http://devportal.dts.com/saap/api/assets HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(dtsUser, Privileges.Add_and_manage_products, false);
		JSONObject productData = secureProductWf.createNewProduct(null, null);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUser, dtsPass);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_PRODUCT_URL, secureProductWf.csrfToken, secureProductWf.cookie, productData);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: nothing to clean up
		 */
		homeControl.logout();
	}
	
	/*
	 * Verify that DTS user is unable to add, edit and create new variant version when the "Add and manage products" privilege is disabled
	 */
	@Test
	public void APIPV_02(){
		result.addLog("ID : APIPV_02 : Verify that DTS user is unable to add, edit and create new variant version when the \"Add and manage products\" privilege is disabled");
		/*
		 	Precondition: The "Add and manage products" privilege is disabled
			POST http://devportal.dts.com/saap/api/addSKU HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(dtsUser, Privileges.Add_and_manage_products, false);
		JSONObject productData = secureProductWf.createNewProduct(null, null);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUser, dtsPass);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_VARIANT_PRODUCT_URL, secureProductWf.csrfToken, secureProductWf.cookie, productData);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: nothing to clean up
		 */
		homeControl.logout();
	}
	
	/*
	 * Verify that Partner user is unable to add, edit and create new products version when the "Add and manage products" privilege is disabled
	 */
	@Test
	public void APIPV_03(){
		result.addLog("ID : APIPV_03 : Verify that Partner user is unable to add, edit and create new products version when the \"Add and manage products\" privilege is disabled");
		/*
		 	Precondition: The "Add and manage products" privilege is disabled
			POST http://devportal.dts.com/saap/api/assets HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Add_and_manage_products, false);
		JSONObject productData = secureProductWf.createNewProduct(null, null);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_PRODUCT_URL, secureProductWf.csrfToken, secureProductWf.cookie, productData);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: nothing to clean up
		 */
		homeControl.logout();
	}
	
	/*
	 * Verify that Partner user is unable to add, edit and create new variant version when the "Add and manage products" privilege is disabled
	 */
	@Test
	public void APIPV_04(){
		result.addLog("ID : APIPV_04 : Verify that Partner user is unable to add, edit and create new variant version when the \"Add and manage products\" privilege is disabled");
		/*
		 	Precondition: The "Add and manage products" privilege is disabled
			POST http://devportal.dts.com/saap/api/addSKU HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Add_and_manage_products, false);
		JSONObject productData = secureProductWf.createNewProduct(null, null);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_VARIANT_PRODUCT_URL, secureProductWf.csrfToken, secureProductWf.cookie, productData);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: nothing to clean up
		 */
		homeControl.logout();
	}
	
	/*
	 * Verify that DTS user is unable to publish product when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_05(){
		result.addLog("ID : APIPV_05 : Verify that DTS user is unable to publish product when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		 */
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Publish_and_suspend_products, false);
		// Create new product and approve tuning and marketing
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndTunningAndMarketing(productData, true);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to publish variant when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_06(){
		result.addLog("ID : APIPV_06 : Verify that DTS user is unable to publish variant when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Publish_and_suspend_products, false);
		
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndPublish(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantPublish();
		secureProductWf.addVariantAndTunningAndMarketing(variantData, true);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_VARIANT_PRODUCT_URL(variantId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
		
	}

	/*
	 * Verify that Partner user is unable to publish product when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_07(){
		result.addLog("ID : APIPV_07 : Verify that Partner user is unable to publish product when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		 */
		secureProductWf.preconditionAndDisablePrivilege(pdtsUserSecurity, Privileges.Publish_and_suspend_products, false);
		// Create new product and approve tuning and marketing
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndTunningAndMarketing(productData, true);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to publish variant when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_08(){
		result.addLog("ID : APIPV_08 : Verify that Partner user is unable to publish variant when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(pdtsUserSecurity, Privileges.Publish_and_suspend_products, false);
		
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndPublish(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantPublish();
		secureProductWf.addVariantAndTunningAndMarketing(variantData, true);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_VARIANT_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to publish product of brand which is not assigned to manage when the  "Publish and suspend products" privilege is enabled
	 */
	@Test
	public void APIPV_09(){
		result.addLog("ID : APIPV_09 : Verify that Partner user is unable to publish product of brand which is not assigned to manage when the \"Publish and suspend products\" privilege is enabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled
			GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.1
		 */
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		secureProductWf.enableAllPrivilegeOfUser(pdtsUserSecurity);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, CreatePage.securityBrand1, false);
		homeControl.click(AddUser.SAVE);
		// Create new product and approve tuning and marketing
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndTunningAndMarketing(productData, true);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to publish variant of brand which is not assigned to manage when the  "Publish and suspend products" privilege is enabled
	 */
	@Test
	public void APIPV_10(){
		result.addLog("ID : APIPV_10 : Verify that Partner user is unable to publish variant of brand which is not assigned to manage when the \"Publish and suspend products\" privilege is enabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		secureProductWf.enableAllPrivilegeOfUser(pdtsUserSecurity);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, CreatePage.securityBrand1, false);
		homeControl.click(AddUser.SAVE);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndPublish(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantPublish();
		secureProductWf.addVariantAndTunningAndMarketing(variantData, true);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_VARIANT_PRODUCT_URL(variantId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to publish product of other companies when the "Publish and suspend products" privilege is enabled with all brands
	 */
	@Test
	public void APIPV_11(){
		result.addLog("ID : APIPV_11 : Verify that Partner user is unable to publish product of other companies when the \"Publish and suspend products\" privilege is enabled with all brands");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled
			GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		// Create new product and approve tuning and marketing
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany2, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndTunningAndMarketing(productData, true);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to publish variant of other companies when the  "Publish and suspend products" privilege is enabled with all brands
	 */
	@Test
	public void APIPV_12(){
		result.addLog("ID : APIPV_12 : Verify that Partner user is unable to publish variant of other companies when the  \"Publish and suspend products\" privilege is enabled with all brands");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndPublish(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantPublish();
		secureProductWf.addVariantAndTunningAndMarketing(variantData, true);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_PRODUCT_URL(variantId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to suspend product when the  "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_13(){
		result.addLog("ID : APIPV_13 : Verify that DTS user is unable to suspend product when the  \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Publish_and_suspend_products, false);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndPublish(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to suspend variant when the  "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_14(){
		result.addLog("ID : APIPV_14 : Verify that DTS user is unable to suspend variant when the  \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Publish_and_suspend_products, false);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndPublish(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantPublish();
		secureProductWf.addVariantAndPublish(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_VARIANT_PRODUCT_URL(variantId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to suspend product when the  "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_15(){
		result.addLog("ID : APIPV_15 : Verify that Partner user is unable to suspend product when the  \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(pdtsUserSecurity, Privileges.Publish_and_suspend_products, false);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndPublish(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));

		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to suspend variant when the  "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_16(){
		result.addLog("ID : APIPV_16 : Verify that Partner user is unable to suspend variant when the  \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(pdtsUserSecurity, Privileges.Publish_and_suspend_products, false);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndPublish(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantPublish();
		secureProductWf.addVariantAndPublish(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_VARIANT_PRODUCT_URL(variantId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to suspend product of brand which is not assigned to manage when the  "Publish and suspend products" privilege is enabled
	 */
	@Test
	public void APIPV_17(){
		result.addLog("ID : APIPV_17 : Verify that Partner user is unable to suspend product of brand which is not assigned to manage when the \"Publish and suspend products\" privilege is enabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled for a limit brand
			GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		 */
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		secureProductWf.enableAllPrivilegeOfUser(pdtsUserSecurity);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, CreatePage.securityBrand1, false);
		homeControl.click(AddUser.SAVE);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndPublish(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));

		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to suspend variant of brand which is not assigned to manage when the  "Publish and suspend products" privilege is enabled
	 */
	@Test
	public void APIPV_18(){
		result.addLog("ID : APIPV_18 : Verify that Partner user is unable to suspend variant of brand which is not assigned to manage when the \"Publish and suspend products\" privilege is enabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		secureProductWf.enableAllPrivilegeOfUser(pdtsUserSecurity);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, CreatePage.securityBrand1, false);
		homeControl.click(AddUser.SAVE);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndPublish(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantPublish();
		secureProductWf.addVariantAndPublish(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_VARIANT_PRODUCT_URL(variantId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to suspend product of other companies when the "Publish and suspend products" privilege is enabled with all brandss
	 */
	@Test
	public void APIPV_19(){
		result.addLog("ID : APIPV_19 : Verify that Partner user is unable to suspend product of other companies when the \"Publish and suspend products\" privilege is enabled with all brands");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled with all brands
			GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany2, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndPublish(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));

		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to suspend variant of other company when the "Publish and suspend products" privilege is enabled with all brands
	 */
	@Test
	public void APIPV_20(){
		result.addLog("ID : APIPV_20 : Verify that Partner user is unable to suspend variant of other companies when the \"Publish and suspend products\" privilege is enabled with all brands");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled for all brands
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany2, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndPublish(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantPublish();
		secureProductWf.addVariantAndPublish(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_VARIANT_PRODUCT_URL(variantId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}

	/*
	 * Verify that DTS user is unable to Restore product when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_21(){
		result.addLog("ID : APIPV_21 : Verify that DTS user is unable to Restore product when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845722545 HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Publish_and_suspend_products, false);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndSuspend(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));

		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to Restore variant when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_22(){
		result.addLog("ID : APIPV_22 : Verify that DTS user is unable to Restore variant when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Publish_and_suspend_products, false);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndPublish(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantPublish();
		secureProductWf.addVariantAndSuspend(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_VARIANT_PRODUCT_URL(variantId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}


	/*
	 * Verify that Partner user is unable to Restore product when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_23(){
		result.addLog("ID : APIPV_23 : Verify that Partner user is unable to Restore product when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845722545 HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(pdtsUserSecurity, Privileges.Publish_and_suspend_products, false);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndSuspend(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));

		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to Restore variant when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_24(){
		result.addLog("ID : APIPV_24 : Verify that Partner user is unable to Restore variant when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(pdtsUserSecurity, Privileges.Publish_and_suspend_products, false);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndPublish(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantPublish();
		secureProductWf.addVariantAndSuspend(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_VARIANT_PRODUCT_URL(variantId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}


	/*
	 * Verify that Partner user is unable to restore product of brand which is not assigned to manage when the  "Publish and suspend products" privilege is enabled
	 */
	@Test
	public void APIPV_25(){
		result.addLog("ID : APIPV_25 : Verify that Partner user is unable to restore product of brand which is not assigned to manage when the \"Publish and suspend products\" privilege is enabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled for a limit brand
			GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845722545 HTTP/1.1
		 */
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		secureProductWf.enableAllPrivilegeOfUser(pdtsUserSecurity);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, CreatePage.securityBrand1, false);
		homeControl.click(AddUser.SAVE);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndSuspend(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));

		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to restore variant of brand which is not assigned to manage when the  "Publish and suspend products" privilege is enabled
	 */
	@Test
	public void APIPV_26(){
		result.addLog("ID : APIPV_26 : Verify that Partner user is unable to restore variant of brand which is not assigned to manage when the \"Publish and suspend products\" privilege is enabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		Assert.assertEquals(true, userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityBrand1, false).booleanValue());
		homeControl.click(AddUser.SAVE);
		
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndPublish(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantPublish();
		secureProductWf.addVariantAndSuspend(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_VARIANT_PRODUCT_URL(variantId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	

	/*
	 * Verify that Partner user is unable to restore product of other companies when the "Publish and suspend products" privilege is enabled with all brandss
	 */
	@Test
	public void APIPV_27(){
		result.addLog("ID : APIPV_27 : Verify that Partner user is unable to restore product of other companies when the \"Publish and suspend products\" privilege is enabled with all brands");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled with all brands
			GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany2, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndSuspend(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));

		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to restore variant of other company when the "Publish and suspend products" privilege is enabled with all brands
	 */
	@Test
	public void APIPV_28(){
		result.addLog("ID : APIPV_28 : Verify that Partner user is unable to restore variant of other companies when the \"Publish and suspend products\" privilege is enabled with all brands");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled for all brands
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndPublish(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantPublish();
		secureProductWf.addVariantAndSuspend(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_PRODUCT_URL(variantId.toString()), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}


	/*
	 * Verify that DTS user is unable to delete product when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_29(){
		result.addLog("ID : APIPV_29 : Verify that DTS user is unable to delete product when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/deleteModel?assetid=3895&revision=0&time=1436846963281&partnerid=49 HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Publish_and_suspend_products, false);
		// Create new product and approve tuning and marketing
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/deleteModel?assetid=3895&revision=0&time=1436846963281&partnerid=49 HTTP/1.1
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.DELETE_METHOD,
				APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken,
				secureProductWf.cookie, "");
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to delete variant when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_30(){
		result.addLog("ID : APIPV_30 : Verify that DTS user is unable to delete variant when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/deleteVariantModel?variantId=1256&revision=0&time=1436846943073&partnerid=49 HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Publish_and_suspend_products, false);
		
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantPublish();
		secureProductWf.addNewVariant(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, 
				APIData.DELETE_VARIANT_PRODUCT_URL(variantId.toString()), 
				secureProductWf.csrfToken, secureProductWf.cookie, "");
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
		
	}


	/*
	 * Verify that Partner  user is unable to delete product when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_31(){
		result.addLog("ID : APIPV_31 : Verify that Partner  user is unable to delete product when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/deleteModel?assetid=3895&revision=0&time=1436846963281&partnerid=49 HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(pdtsUserSecurity, Privileges.Publish_and_suspend_products, false);
		// Create new product and approve tuning and marketing
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/deleteModel?assetid=3895&revision=0&time=1436846963281&partnerid=49 HTTP/1.1
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.DELETE_METHOD,
				APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken,
				secureProductWf.cookie, "");
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to delete variant when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_32(){
		result.addLog("ID : APIPV_32 : Verify that Partner  user is unable to delete variant when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/deleteVariantModel?variantId=1256&revision=0&time=1436846943073&partnerid=49 HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(pdtsUserSecurity, Privileges.Publish_and_suspend_products, false);
		
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantPublish();
		secureProductWf.addNewVariant(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, 
				APIData.DELETE_VARIANT_PRODUCT_URL(variantId.toString()), 
				secureProductWf.csrfToken, secureProductWf.cookie, "");
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
		
	}


	/*
	 * Verify that Partner user is unable to delete product of brand which is not assigned to manage when the  "Publish and suspend products" privilege is enabled
	 */
	@Test
	public void APIPV_33(){
		result.addLog("ID : APIPV_33 : Verify that Partner user is unable to delete product of brand which is not assigned to manage when the \"Publish and suspend products\" privilege is enabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled for a limit brand
			GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		 */
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		secureProductWf.enableAllPrivilegeOfUser(pdtsUserSecurity);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, CreatePage.securityBrand1, false);
		homeControl.click(AddUser.SAVE);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));

		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.DELETE_METHOD,
				APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken,
				secureProductWf.cookie, "");
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to delete variant of brand which is not assigned to manage when the  "Publish and suspend products" privilege is enabled
	 */
	@Test
	public void APIPV_34(){
		result.addLog("ID : APIPV_34 : Verify that Partner user is unable to delete variant of brand which is not assigned to manage when the \"Publish and suspend products\" privilege is enabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		secureProductWf.enableAllPrivilegeOfUser(pdtsUserSecurity);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, CreatePage.securityBrand1, false);
		homeControl.click(AddUser.SAVE);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantPublish();
		secureProductWf.addVariantAndPublish(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.DELETE_METHOD,
				APIData.DELETE_VARIANT_PRODUCT_URL(variantId.toString()), secureProductWf.csrfToken,
				secureProductWf.cookie, "");
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to delete product of other companies when the "Publish and suspend products" privilege is enabled with all brandss
	 */
	@Test
	public void APIPV_35(){
		result.addLog("ID : APIPV_35 : Verify that Partner user is unable to delete product of other companies when the \"Publish and suspend products\" privilege is enabled with all brands");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled with all brands
			GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany2, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));

		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.DELETE_METHOD,
				APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken,
				secureProductWf.cookie, "");
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to delete variant of other company when the "Publish and suspend products" privilege is enabled with all brands
	 */
	@Test
	public void APIPV_36(){
		result.addLog("ID : APIPV_36 : Verify that Partner user is unable to delete variant of other companies when the \"Publish and suspend products\" privilege is enabled with all brands");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled for all brands
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany2, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantPublish();
		secureProductWf.addVariantAndPublish(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.DELETE_METHOD,
				APIData.DELETE_VARIANT_PRODUCT_URL(variantId.toString()), secureProductWf.csrfToken,
				secureProductWf.cookie, "");
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}


	/*
	 * Verify that DTS user is unable to Request product tuning when the "Request product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_37(){
		result.addLog("ID : APIPV_37 : Verify that DTS user is unable to Request product tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=37&time=1436847596040&partnerid=23&assetid=4040 HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Request_product_tunings, false);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, false, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.REQUEST_TUNING_URL(productId.toString(), dtsUserSerId, dtsUserSerCompId, true), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to Request variant tuning when the  "Request product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_38(){
		result.addLog("ID : APIPV_38 : Verify that DTS user is unable to Request variant tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=36&time=1436857799878&partnerid=23&variantid=1307 HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Request_product_tunings, false);
		
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, false, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		secureProductWf.addNewVariant(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.REQUEST_TUNING_URL(variantId.toString(), dtsUserSerId, dtsUserSerCompId, false), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
		
	}

	/*
	 * Verify that Partner user is unable to Request product tuning when the  "Request product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_39(){
		result.addLog("ID : APIPV_39 : Verify that Partner user is unable to Request product tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=37&time=1436847596040&partnerid=23&assetid=4040 HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(pdtsUserSecurity, Privileges.Request_product_tunings, false);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, false, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.REQUEST_TUNING_URL(productId.toString(), pdtsUserSerId, pdtsUserSerCompId, true), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to Request variant tuning when the  "Request product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_40(){
		result.addLog("ID : APIPV_40 : Verify that Partner user is unable to Request variant tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=36&time=1436857799878&partnerid=23&variantid=1307 HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(pdtsUserSecurity, Privileges.Request_product_tunings, false);
		
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, false, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		secureProductWf.addNewVariant(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.REQUEST_TUNING_URL(variantId.toString(), pdtsUserSerId, pdtsUserSerCompId, false), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
		
	}

	/*
	 * Verify that Partner user is unable to approve product tuning when the  "Request product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_41(){
		result.addLog("ID : APIPV_41 : Verify that Partner user is unable to approve product tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateTuningStatus?assetId=4039&time=1436844887023&revision=0&tuning_status=Approved HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(pdtsUserSecurity, Privileges.Request_product_tunings, false);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.APPROVE_TUNING_URL(productId.toString(), true), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to approve variant tuning when the  "Request product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_42(){
		result.addLog("ID : APIPV_42 : Verify that Partner user is unable to approve variant tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1306&revision=0&time=1436846425054&tuning_status=Approved HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(pdtsUserSecurity, Privileges.Request_product_tunings, false);
		
		// Create new Product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		secureProductWf.addNewVariant(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.APPROVE_TUNING_URL(variantId.toString(), false), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
		
	}
	
	/*
	 * Verify that Partner user is unable to Decline product tuning when the  "Request product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_43(){
		result.addLog("ID : APIPV_41 : Verify that Partner user is unable to Decline product tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateTuningStatus?assetId=4039&revision=0&time=1436845180645&tuning_status=partner_declined HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(pdtsUserSecurity, Privileges.Request_product_tunings, false);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.DECLINE_TUNING_URL(productId.toString(), true), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to Decline variant tuning when the  "Request product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_44(){
		result.addLog("ID : APIPV_42 : Verify that Partner user is unable to Decline variant tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1306&time=1436845942332&revision=0&tuning_status=partner_declined HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(pdtsUserSecurity, Privileges.Request_product_tunings, false);
		
		// Create new Product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		secureProductWf.addNewVariant(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.DECLINE_TUNING_URL(variantId.toString(), false), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
		
	}

	/*
	 * Verify that Partner user is unable to request revised product tuning when the  "Request product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_45(){
		result.addLog("ID : APIPV_45 : Verify that Partner user is unable to request revised product tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=2&time=1438070912695&partnerid=24&assetid=5259 HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(pdtsUserSecurity, Privileges.Request_product_tunings, false);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		homeControl.click(AccessoryInfo.DECLINE_TUNING);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request
		JSONObject requestResult = APIUtil.sendGet(APIData.REQUEST_REVISE_TUNING_URL(productId.toString(), pdtsUserSerId, pdtsUserSerCompId, true), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to request revised variant tuning when the  "Request product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_46(){
		result.addLog("ID : APIPV_46 : Verify that Partner user is unable to request revised variant tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=37&time=1438074176141&partnerid=23&variantid=1706
		 */
		secureProductWf.preconditionAndDisablePrivilege(pdtsUserSecurity, Privileges.Request_product_tunings, false);
		
		// Create new Product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		secureProductWf.addNewVariant(variantData);
		homeControl.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		homeControl.click(AccessoryInfo.DECLINE_TUNING);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.REQUEST_REVISE_TUNING_URL(variantId.toString(), pdtsUserSerId, pdtsUserSerCompId, false), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
		
	}
	
	/*
	 * Verify that Partner user is unable to revoke product tuning when the  "Request product tunings" privilege is disabled
	 */
	public void APIPV_47(){
		result.addLog("ID : APIPV_47 : Verify that Partner user is unable to revoke product tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			http://devportal.dts.com/saap/api/updateTuningStatus?assetId=8386&revision=0&time=1441695381555&tuning_status=Revoked
		 */
		secureProductWf.preconditionAndDisablePrivilege(pdtsUserSecurity, Privileges.Request_product_tunings, false);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addProductAndTunningAndMarketing(productData, false);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.REVOKE_TUNING_URL(productId.toString(), true), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to revoke variant tuning when the  "Request product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_48(){
		result.addLog("ID : APIPV_48 : Verify that Partner user is unable to revoke variant tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			http://devportal.dts.com/saap/api/updateTuningStatus?assetId=8386&revision=0&time=1441695381555&tuning_status=Revoked
		 */
		secureProductWf.preconditionAndDisablePrivilege(pdtsUserSecurity, Privileges.Request_product_tunings, false);
		
		// Create new Product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addProductAndTunningAndMarketing(productData, false);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		secureProductWf.addVariantAndTunningAndMarketing(variantData, false);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		JSONObject requestResult = APIUtil.sendGet(APIData.REVOKE_TUNING_URL(variantId.toString(), false), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
		
	}
	
	/*
	 * Verify that DTS user is unable to Approve product tuning when the  "Approve product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_49(){
		result.addLog("ID : APIPV_49 : Verify that DTS user is unable to Approve product tuning when the \"Approve product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Approve product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateTuningStatus?assetId=4039&time=1436844887023&revision=0&tuning_status=Approved HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Approve_product_tuning, true);
		homeControl.logout();
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
//		home.click(TuningRequestForm.AGREE);
//		home.click(TuningRequestForm.SUBMIT);
		
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		secureProductWf.editTuningRating(productData.get("name"));
//		secureProductWf.updateTuningApproval(productData.get("name"), AccessoryInfo.APPROVE_TUNING);
		
		// Send a sample GET request as following:
		JSONObject requestResult = APIUtil.sendGet(APIData.APPROVE_TUNING_URL(productId.toString(), true), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to Approve variant tuning when the "Approve product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_52(){
		result.addLog("ID : APIPV_52 : Verify that DTS user is unable to Approve variant tuning when the \"Approve product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Approve product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1306&revision=0&time=1436846425054&tuning_status=Approved HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Approve_product_tuning, true);
		homeControl.logout();
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Create new Product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		secureProductWf.addNewVariant(variantData);
		homeControl.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		secureProductWf.editTuningRating(productData.get("name"));
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.APPROVE_TUNING_URL(variantId.toString(), false), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
		
	}
		
	/*
	 * Verify that DTS user is unable to Revoke product tuning when the "Approve product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_50(){
		result.addLog("ID : APIPV_50 : Verify that DTS user is unable to Revoke product tuning when the \"Approve product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Approve product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateTuningStatus?assetId=4039&revision=0&time=1436845041392&tuning_status=Revoked HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Approve_product_tuning, true);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addProductAndTunningAndMarketing(productData, false);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.REVOKE_TUNING_URL(productId.toString(), true), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to Revoke variant tuning when the "Approve product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_51(){
		result.addLog("ID : APIPV_51 : Verify that DTS user is unable to Revoke variant tuning when the \"Approve product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Approve product tunings" privilege is disabled
			http://devportal.dts.com/saap/api/updateTuningStatus?assetId=8386&revision=0&time=1441695381555&tuning_status=Revoked
		 */
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Approve_product_tuning, true);
		
		// Create new Product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addProductAndTunningAndMarketing(productData, false);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		secureProductWf.addVariantAndTunningAndMarketing(variantData, false);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		JSONObject requestResult = APIUtil.sendGet(APIData.REVOKE_TUNING_URL(variantId.toString(), false), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
		
	}

	/*
	 * Verify that DTS user is unable to Decline product tuning when the  "Approve product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_53(){
		result.addLog("ID : APIPV_53 : Verify that DTS user is unable to Decline product tuning when the \"Approve product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Approve product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateTuningStatus?assetId=4039&revision=0&time=1436845180645&tuning_status=partner_declined HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Approve_product_tuning, true);
		homeControl.logout();
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		secureProductWf.editTuningRating(productData.get("name"));
//		secureProductWf.updateTuningApproval(productData.get("name"), AccessoryInfo.APPROVE_TUNING);
		
		// Send a sample GET request as following:
		JSONObject requestResult = APIUtil.sendGet(APIData.DECLINE_TUNING_URL(productId.toString(), true), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to Approve variant tuning when the "Approve product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_54(){
		result.addLog("ID : APIPV_54 : Verify that DTS user is unable to Approve variant tuning when the \"Approve product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Approve product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=4039&revision=0&time=1436845180645&tuning_status=partner_declined HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Approve_product_tuning, true);
		homeControl.logout();
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Create new Product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		secureProductWf.addNewVariant(variantData);
		homeControl.click(AccessoryInfo.REQUEST_TUNING);
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		secureProductWf.editTuningRating(productData.get("name"));
		secureProductWf.updateTuningApproval(productData.get("name"), AccessoryInfo.APPROVE_TUNING);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.DECLINE_TUNING_URL(variantId.toString(), false), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
		
	}

	/*
	 * Verify that DTS user is unable to Approve product marketing when the "Approve marketing information" privilege is disabled
	 */
	@Test
	public void APIPV_55(){
		result.addLog("ID : APIPV_55 : Verify that DTS user is unable to Approve product marketing when the \"Approve marketing information\" privilege is disabled");
		/*
		 	Precondition: The "Approve marketing information" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateTuningStatus?assetId=4039&revision=0&time=1436845180645&tuning_status=partner_declined HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Approve_marketing_info, true);
		homeControl.logout();
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		secureProductWf.editTuningAndDoMarketingByDTSUser(productData.get("name"), null);
//		homeControl.click(AccessoryInfo.APPROVE_MARKETING);
		
		// Send a sample GET request as following:
		JSONObject requestResult = APIUtil.sendGet(APIData.APPROVE_MARKETING_URL(productId.toString(), true), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to Approve variant marketing when the "Approve marketing information" privilege is disabled
	 */
	@Test
	public void APIPV_56(){
		result.addLog("ID : APIPV_56 : Verify that DTS user is unable to Approve variant marketing when the \"Approve marketing information\" privilege is disabled");
		/*
		 	Precondition: The "Approve marketing information" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateMarketingStatus?assetId=4039&time=1436844981082&revision=0&marketing_status=Approved HTTP/1.1
		 */
//		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Approve_marketing_info, true);
		secureProductWf.preconditionAndEnableAllPrivilege(dtsUserSecurity);
		homeControl.logout();
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Create new Product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(false, true, false);
		secureProductWf.addNewVariant(variantData);
//		homeControl.click(AccessoryInfo.REQUEST_TUNING);
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		secureProductWf.editTuningAndDoMarketingByDTSUser(productData.get("name"), AccessoryInfo.APPROVE_MARKETING);
		Assert.assertTrue(productControl.selectVariantbyName(variantData.get("name")));
		secureProductWf.requestAndDoMarketingByDTSUser(null);
		homeControl.logout();
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Approve_marketing_info, true);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.APPROVE_MARKETING_URL(variantId.toString(), false), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
		
	}
	
	/*
	 * Verify that DTS user is unable to Revoke product marketing when the "Approve marketing information" privilege is disabled
	 */
	@Test
	public void APIPV_57(){
		result.addLog("ID : APIPV_57 : Verify that DTS user is unable to Revoke product marketing when the \"Approve marketing information\" privilege is disabled");
		/*
		 	Precondition: The "Approve marketing information" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateMarketingStatus?assetId=4039&revision=0&time=1436845002728&marketing_status=Revoked HTTP/1.1
		 */
//		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Approve_marketing_info, true);
		secureProductWf.preconditionAndEnableAllPrivilege(dtsUserSecurity);
		homeControl.logout();
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		secureProductWf.editTuningAndDoMarketingByDTSUser(productData.get("name"), AccessoryInfo.APPROVE_MARKETING);
		homeControl.logout();
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Approve_marketing_info, true);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		JSONObject requestResult = APIUtil.sendGet(APIData.REVOKE_MARKETING_URL(productId.toString(), true), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to Revoke variant marketing when the "Approve marketing information" privilege is disabled
	 */
	@Test
	public void APIPV_58(){
		result.addLog("ID : APIPV_58 : Verify that DTS user is unable to Revoke variant marketing when the \"Approve marketing information\" privilege is disabled");
		/*
		 	Precondition: The "Approve marketing information" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateMarketingStatus?assetId=4039&time=1436844981082&revision=0&marketing_status=Revoke HTTP/1.1
		 */
//		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Approve_marketing_info, true);
		secureProductWf.preconditionAndEnableAllPrivilege(dtsUserSecurity);
		homeControl.logout();
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Create new Product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(false, true, false);
		secureProductWf.addNewVariant(variantData);
//		homeControl.click(AccessoryInfo.REQUEST_TUNING);
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		secureProductWf.editTuningAndDoMarketingByDTSUser(productData.get("name"), AccessoryInfo.APPROVE_MARKETING);
		Assert.assertTrue(productControl.selectVariantbyName(variantData.get("name")));
		secureProductWf.requestAndDoMarketingByDTSUser(AccessoryInfo.APPROVE_MARKETING);
		homeControl.logout();
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Approve_marketing_info, true);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.APPROVE_MARKETING_URL(variantId.toString(), false), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
		
	}
	
	/*
	 * Verify that DTS user is unable to Decline product marketing when the "Approve marketing information" privilege is disabled
	 */
	@Test
	public void APIPV_59(){
		result.addLog("ID : APIPV_59 : Verify that DTS user is unable to Decline product marketing when the \"Approve marketing information\" privilege is disabled");
		/*
		 	Precondition: The "Approve marketing information" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateMarketingStatus?assetId=4039&time=1436845298945&revision=0&marketing_status=Declined HTTP/1.1
		 */
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Approve_marketing_info, true);
		homeControl.logout();
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		secureProductWf.editTuningAndDoMarketingByDTSUser(productData.get("name"), null);
//		homeControl.click(AccessoryInfo.APPROVE_MARKETING);
		
		// Send a sample GET request as following:
		JSONObject requestResult = APIUtil.sendGet(APIData.DECLINE_MARKETING_URL(productId.toString(), true), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to Approve variant marketing when the "Approve marketing information" privilege is disabled
	 */
	@Test
	public void APIPV_60(){
		result.addLog("ID : APIPV_56 : Verify that DTS user is unable to Approve variant marketing when the \"Approve marketing information\" privilege is disabled");
		/*
		 	Precondition: The "Approve marketing information" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateMarketingStatus?assetId=4039&time=1436844981082&revision=0&marketing_status=Declined HTTP/1.1
		 */
//		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Approve_marketing_info, true);
		secureProductWf.preconditionAndEnableAllPrivilege(dtsUserSecurity);
		homeControl.logout();
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Create new Product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(AccessoryInfo.REQUEST_TUNING_REVIEW);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(false, true, false);
		secureProductWf.addNewVariant(variantData);
//		homeControl.click(AccessoryInfo.REQUEST_TUNING);
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		secureProductWf.editTuningAndDoMarketingByDTSUser(productData.get("name"), AccessoryInfo.APPROVE_MARKETING);
		Assert.assertTrue(productControl.selectVariantbyName(variantData.get("name")));
		secureProductWf.requestAndDoMarketingByDTSUser(null);
		homeControl.logout();
		secureProductWf.preconditionAndDisablePrivilege(dtsUserSecurity, Privileges.Approve_marketing_info, true);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(dtsUserSecurity, dtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.DECLINE_MARKETING_URL(variantId.toString(), false), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
		
	}

	/*
	 * Verify that Partner user is unable to add, edit and create new product version for another company
	 */
	@Test
	public void APIPV_61(){
		result.addLog("ID : APIPV_61 : Verify that Partner user is unable to add, edit and create new product version for another company");
		/*
			POST http://devportal.dts.com/saap/api/assets HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany2, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndPublish(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		JSONObject productData2 = secureProductWf.createNewProduct(null, null);
		productData2.put("modelName", "Test" + RandomStringUtils.randomNumeric(6));
		productData2.put("id", productId);
		productData2.put("partnerId", dtsUserSerCompId);
		productData2.put("brandId", CreatePage.securityBrandId2);
		productData2.put("ean", productData.get("ean"));
		productData2.put("upc", productData.get("upc"));
		productData2.put("updatedBy", pdtsUserSerId);
		productData2.put("revision", null);
		productData2.put("tuningRating", "A");
		productData2.put("salesforceId", "");
		productData2.put("modelNum", "");
		productData2.put("assetType", "Headphone");
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		
		// Send a sample GET request as following:
		// POST http://devportal.dts.com/saap/api/assets HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_PRODUCT_URL, secureProductWf.csrfToken, secureProductWf.cookie, productData2);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to add, edit and create new version variant for another company
	 */
	@Test
	public void APIPV_62(){
		result.addLog("ID : APIPV_62 : Verify that Partner user is unable to add, edit and create new version variant for another company");
		/*
			POST http://devportal.dts.com/saap/api/addSKU HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany2, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndPublish(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantPublish();
		secureProductWf.addVariantAndPublish(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		JSONObject variantData2 = secureProductWf.createNewVariant(productId.toString());
		variantData2.put("modelName", "Test" + RandomStringUtils.randomNumeric(6));
		variantData2.put("partnerId", dtsUserSerCompId);
		variantData2.put("brandId", securityBrandId2);
		variantData2.put("ean", variantData.get("ean"));
		variantData2.put("upc", variantData.get("upc"));
		variantData2.put("updatedBy", pdtsUserSerId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_VARIANT_PRODUCT_URL, secureProductWf.csrfToken, secureProductWf.cookie, variantData2);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}

	/*
	 * Verify that Partner user is unable to add, edit and create new product version for a brand which is not assigned to manage.
	 */
	@Test
	public void APIPV_63(){
		result.addLog("ID : APIPV_63 : Verify that Partner user is unable to add, edit and create new product version for a brand which is not assigned to manage.");
		/*
			POST http://devportal.dts.com/saap/api/assets HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, CreatePage.securityBrand1, false);
		homeControl.click(AddUser.SAVE);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndPublish(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		JSONObject productData2 = secureProductWf.createNewProduct(null, null);
		productData2.put("modelName", "Test" + RandomStringUtils.randomNumeric(6));
		productData2.put("id", productId);
		productData2.put("partnerId", dtsUserSerCompId);
		productData2.put("brandId", CreatePage.securityBrandId1);
		productData2.put("ean", productData.get("ean"));
		productData2.put("upc", productData.get("upc"));
		productData2.put("updatedBy", pdtsUserSerId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, dtsUserSecurityPassword);
		
		// Send a sample GET request as following:
		// POST http://devportal.dts.com/saap/api/assets HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_PRODUCT_URL, secureProductWf.csrfToken, secureProductWf.cookie, productData2);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to add, edit and create new product version for a brand which is not assigned to manage.
	 */
	@Test
	public void APIPV_64(){
		result.addLog("ID : APIPV_64 : Verify that Partner user is unable to add, edit and create new product version for a brand which is not assigned to manage.");
		/*
			POST http://devportal.dts.com/saap/api/addSKU HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, CreatePage.securityBrand1, false);
		homeControl.click(AddUser.SAVE);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndPublish(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantPublish();
		secureProductWf.addVariantAndPublish(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		JSONObject variantData2 = secureProductWf.createNewVariant(productId.toString());
		variantData2.put("modelName", "Test" + RandomStringUtils.randomNumeric(6));
		variantData2.put("partnerId", dtsUserSerCompId);
		variantData2.put("brandId", securityBrandId1);
		variantData2.put("ean", variantData.get("ean"));
		variantData2.put("upc", variantData.get("upc"));
		variantData2.put("updatedBy", pdtsUserSerId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_VARIANT_PRODUCT_URL, secureProductWf.csrfToken, secureProductWf.cookie, variantData2);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to request product tuning of another company when the "Request product tunings" is enabled with all brands
	 */
	@Test
	public void APIPV_65(){
		result.addLog("ID : APIPV_65 : Verify that Partner user is unable to request product tuning of another company when the \"Request product tunings\" is enabled with all brands");
		/*
			GET http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=37&time=1436847596040&partnerid=23&assetid=4040 HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		// securityInvalidBrand is used for select all brand in the Brand Privilege of user
		Assert.assertEquals(false, userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityInvalidBrand, false).booleanValue());
		homeControl.click(AddUser.SAVE);
		// Create new Product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany2, CreatePage.securityBrand1, false, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.REQUEST_TUNING_URL(productId.toString(), pdtsUserSerId, pdtsUserSerCompId, true), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to approve product tuning of another company when the "Request product tunings" is enabled with all brands
	 */
	@Test
	public void APIPV_66(){
		result.addLog("ID : APIPV_66 : Verify that Partner user is unable to approve product tuning of another company when the \"Request product tunings\" is enabled with all brands");
		/*
			POST http://devportal.dts.com/saap/api/addSKU HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		// securityInvalidBrand is used for select all brand in the Brand Privilege of user
		Assert.assertEquals(false, userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityInvalidBrand, false).booleanValue());
		homeControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany2, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.APPROVE_TUNING_URL(productId.toString(), true), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to decline product tuning of another company when the "Request product tunings" is enabled with all brands
	 */
	@Test
	public void APIPV_67(){
		result.addLog("ID : APIPV_67 : Verify that Partner user is unable to decline product tuning of another company when the \"Request product tunings\" is enabled with all brands");
		/*
			POST http://devportal.dts.com/saap/api/addSKU HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		// securityInvalidBrand is used for select all brand in the Brand Privilege of user
		Assert.assertEquals(false, userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityInvalidBrand, false).booleanValue());
		homeControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany2, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.DECLINE_TUNING_URL(productId.toString(), true), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to request revised product tuning of another company when the "Request product tunings" is enabled with all brands
	 */
	@Test
	public void APIPV_68(){
		result.addLog("ID : APIPV_68 : Verify that Partner user is unable to request revised product tuning of another company when the \"Request product tunings\" is enabled with all brands");
		/*
			POST http://devportal.dts.com/saap/api/addSKU HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		// securityInvalidBrand is used for select all brand in the Brand Privilege of user
		Assert.assertEquals(false, userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityInvalidBrand, false).booleanValue());
		homeControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany2, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		homeControl.click(AccessoryInfo.DECLINE_TUNING);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request
		JSONObject requestResult = APIUtil.sendGet(APIData.REQUEST_REVISE_TUNING_URL(productId.toString(), pdtsUserSerId, pdtsUserSerCompId, true), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to revoke product tuning of another company when the "Request product tunings" is enabled with all brands
	 */
	@Test
	public void APIPV_69(){
		result.addLog("ID : APIPV_69 : Verify that Partner user is unable to revoke product tuning of another company when the \"Request product tunings\" is enabled with all brands");
		/*
			POST http://devportal.dts.com/saap/api/addSKU HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		// securityInvalidBrand is used for select all brand in the Brand Privilege of user
		Assert.assertEquals(false, userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityInvalidBrand, false).booleanValue());
		homeControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany2, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addProductAndTunningAndMarketing(productData, false);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.REVOKE_TUNING_URL(productId.toString(), true), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to request variant tuning of another company when the "Request product tunings" is enabled with all brands
	 */
	@Test
	public void APIPV_70(){
		result.addLog("ID : APIPV_70 : Verify that Partner user is unable to request variant tuning of another company when the \"Request product tunings\" is enabled with all brands");
		/*
			GET http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=36&time=1436857799878&partnerid=23&variantid=1307 HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		// securityInvalidBrand is used for select all brand in the Brand Privilege of user
		Assert.assertEquals(false, userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityInvalidBrand, false).booleanValue());
		homeControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany2, CreatePage.securityBrand1, false, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		secureProductWf.addNewVariant(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.REQUEST_TUNING_URL(variantId.toString(), pdtsUserSerId, pdtsUserSerCompId, false), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to approve variant tuning of another company when the "Request product tunings" is enabled with all brands
	 */
	@Test
	public void APIPV_71(){
		result.addLog("ID : APIPV_71 : Verify that Partner user is unable to approve variant tuning of another company when the \"Request product tunings\" is enabled with all brands");
		/*
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1307&revision=0&time=1436855986876&tuning_status=Approved HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		// securityInvalidBrand is used for select all brand in the Brand Privilege of user
		Assert.assertEquals(false, userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityInvalidBrand, false).booleanValue());
		homeControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany2, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		secureProductWf.addNewVariant(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.APPROVE_TUNING_URL(variantId.toString(), false), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to decline variant tuning of another company when the "Request product tunings" is enabled with all brands
	 */
	@Test
	public void APIPV_72(){
		result.addLog("ID : APIPV_72 : Verify that Partner user is unable to decline variant tuning of another company when the \"Request product tunings\" is enabled with all brands");
		/*
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1307&time=1436856041266&revision=0&tuning_status=dts_declined HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		// securityInvalidBrand is used for select all brand in the Brand Privilege of user
		Assert.assertEquals(false, userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityInvalidBrand, false).booleanValue());
		homeControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany2, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		secureProductWf.addNewVariant(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.DECLINE_TUNING_URL(variantId.toString(), false), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to request revised variant tuning of another company when the "Request product tunings" is enabled with all brands
	 */
	@Test
	public void APIPV_73(){
		result.addLog("ID : APIPV_73 : Verify that Partner user is unable to request revised variant tuning of another company when the \"Request product tunings\" is enabled with all brands");
		/*
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=4039&revision=0&time=1436845041392&tuning_status=Revoked HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		// securityInvalidBrand is used for select all brand in the Brand Privilege of user
		Assert.assertEquals(false, userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityInvalidBrand, false).booleanValue());
		homeControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany2, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		secureProductWf.addNewVariant(variantData);
		homeControl.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		homeControl.click(AccessoryInfo.DECLINE_TUNING);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.REQUEST_REVISE_TUNING_URL(variantId.toString(), pdtsUserSerId, pdtsUserSerCompId, false), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to revoke variant tuning of another company when the "Request product tunings" is enabled with all brands
	 */
	@Test
	public void APIPV_74(){
		result.addLog("ID : APIPV_74 : Verify that Partner user is unable to revoke variant tuning of another company when the \"Request product tunings\" is enabled with all brands");
		/*
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=4039&revision=0&time=1436845041392&tuning_status=Revoked HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		// securityInvalidBrand is used for select all brand in the Brand Privilege of user
		Assert.assertEquals(false, userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityInvalidBrand, false).booleanValue());
		homeControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany2, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addProductAndTunningAndMarketing(productData, false);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		secureProductWf.addVariantAndTunningAndMarketing(variantData, false);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		JSONObject requestResult = APIUtil.sendGet(APIData.REVOKE_TUNING_URL(variantId.toString(), false), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to request product tuning of brands which are not assigned to manage.
	 */
	@Test
	public void APIPV_75(){
		result.addLog("ID : APIPV_75 : Verify that Partner user is unable to request product tuning of brands which are not assigned to manage.");
		/*
			GET http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=36&time=1436857799878&partnerid=23&variantid=1307 HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		Assert.assertEquals(true, userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityBrand1, false).booleanValue());
		homeControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, false, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.REQUEST_TUNING_URL(productId.toString(), pdtsUserSerId, pdtsUserSerCompId, true), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to approve product tuning of brands which are not assigned to manage.
	 */
	@Test
	public void APIPV_76(){
		result.addLog("ID : APIPV_76 : Verify that Partner user is unable to approve product tuning of brands which are not assigned to manage.");
		/*
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1307&revision=0&time=1436855986876&tuning_status=Approved HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		Assert.assertEquals(true, userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityBrand1, false).booleanValue());
		homeControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1307&revision=0&time=1436855986876&tuning_status=Approved HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.APPROVE_TUNING_URL(productId.toString(), true), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to decline product tuning of brands which are not assigned to manage.
	 */
	@Test
	public void APIPV_77(){
		result.addLog("ID : APIPV_77 : Verify that Partner user is unable to decline product tuning of brands which are not assigned to manage.");
		/*
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1307&time=1436856041266&revision=0&tuning_status=dts_declined HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		Assert.assertEquals(true, userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityBrand1, false).booleanValue());
		homeControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.DECLINE_TUNING_URL(productId.toString(), true), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to request revised product tuning of brands which are not assigned to manage.
	 */
	@Test
	public void APIPV_78(){
		result.addLog("ID : APIPV_78 : Verify that Partner user is unable to request revised product tuning of brands which are not assigned to manage.");
		/*
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=4039&revision=0&time=1436845041392&tuning_status=Revoked HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		Assert.assertEquals(true, userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityBrand1, false).booleanValue());
		homeControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		homeControl.click(AccessoryInfo.DECLINE_TUNING);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request
		JSONObject requestResult = APIUtil.sendGet(APIData.REQUEST_REVISE_TUNING_URL(productId.toString(), pdtsUserSerId, pdtsUserSerCompId, true), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to revoke product tuning of brands which are not assigned to manage.
	 */
	@Test
	public void APIPV_79(){
		result.addLog("ID : APIPV_79 : Verify that Partner user is unable to revoke product tuning of brands which are not assigned to manage.");
		/*
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=4039&revision=0&time=1436845041392&tuning_status=Revoked HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		Assert.assertEquals(true, userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityBrand1, false).booleanValue());
		homeControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addProductAndTunningAndMarketing(productData, false);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=4039&revision=0&time=1436845041392&tuning_status=Revoked HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.REVOKE_TUNING_URL(productId.toString(), true), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to request variant tuning of brands which are not assigned to manage.
	 */
	@Test
	public void APIPV_80(){
		result.addLog("ID : APIPV_80 : Verify that Partner user is unable to request variant tuning of brands which are not assigned to manage.");
		/*
			GET http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=36&time=1436857799878&partnerid=23&variantid=1307 HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		Assert.assertEquals(true, userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityBrand1, false).booleanValue());
		homeControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, false, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		secureProductWf.addNewVariant(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		JSONObject requestResult = APIUtil.sendGet(APIData.REQUEST_TUNING_URL(variantId.toString(), pdtsUserSerId, pdtsUserSerCompId, false), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to approve variant tuning of brands which are not assigned to manage.
	 */
	@Test
	public void APIPV_81(){
		result.addLog("ID : APIPV_81 : Verify that Partner user is unable to approve variant tuning of brands which are not assigned to manage.");
		/*
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1307&revision=0&time=1436855986876&tuning_status=Approved HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		Assert.assertEquals(true, userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityBrand1, false).booleanValue());
		homeControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		secureProductWf.addNewVariant(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1307&revision=0&time=1436855986876&tuning_status=Approved HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.APPROVE_TUNING_URL(variantId.toString(), false), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to approve variant tuning of brands which are not assigned to manage.
	 */
	@Test
	public void APIPV_82(){
		result.addLog("ID : APIPV_82 : Verify that Partner user is unable to approve variant tuning of brands which are not assigned to manage.");
		/*
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1307&time=1436856041266&revision=0&tuning_status=dts_declined HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		Assert.assertEquals(true, userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityBrand1, false).booleanValue());
		homeControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, false, false);
		secureProductWf.addNewProduct(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		secureProductWf.addNewVariant(variantData);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1307&time=1436856041266&revision=0&tuning_status=dts_declined HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.DECLINE_TUNING_URL(variantId.toString(), false), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}	
	
	/*
	 * Verify that Partner user is unable to request revised variant tuning of brands which are not assigned to manage.
	 */
	@Test
	public void APIPV_83(){
		result.addLog("ID : APIPV_83 : Verify that Partner user is unable to request revised variant tuning of brands which are not assigned to manage.");
		/*
			http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=36&time=1441941223516&partnerid=49&variantid=264
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		userControl.selectBrandPrivilegeInTable(AddUser.PRIVILEGES[2], CreatePage.securityBrand1, false);
		homeControl.click(AddUser.SAVE);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndPublish(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
				
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		secureProductWf.addNewVariant(variantData);
		homeControl.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		homeControl.click(VariantInfo.DECLINE_TUNING);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=36&time=1441941223516&partnerid=49&variantid=264
		JSONObject requestResult = APIUtil.sendGet(APIData.REQUEST_REVISE_TUNING_URL(variantId.toString(),pdtsUserSerId.toString(),pdtsUserSerCompId.toString(), false), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to revoke variant tuning of brands which are not assigned to manage.
	 */
	@Test
	public void APIPV_84(){
		result.addLog("ID : APIPV_84 : Verify that Partner user is unable to revoke variant tuning of brands which are not assigned to manage.");
		/*
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=4039&revision=0&time=1436845041392&tuning_status=Revoked HTTP/1.1
		 */
		secureProductWf.preconditionAndEnableAllPrivilege(pdtsUserSecurity);
		homeControl.click(UserInfo.EDIT);
		userControl.selectBrandPrivilegeInTable(AddUser.PRIVILEGES[2], CreatePage.securityBrand1, false);
		homeControl.click(AddUser.SAVE);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(CreatePage.securityCompany1, CreatePage.securityBrand1, true, true, true);
		secureProductWf.addProductAndPublish(productData);
		homeControl.click(Xpath.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
				
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		secureProductWf.addVariantAndTunningAndMarketing(variantData, false);
		
		homeControl.click(Xpath.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		homeControl.logout();
		secureProductWf.loginAndSetTokenAndCookie(pdtsUserSecurity, pdtsUserSecurityPassword);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.REVOKE_TUNING_URL(variantId.toString(),false), secureProductWf.csrfToken, secureProductWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(requestResult.get("message").toString(), APIData.ERROR_MESSAGE);
		/*
		 * PostCondition: Delete user
		 */
		homeControl.logout();
		// Log into DTS portal as DTS user
		secureProductWf.loginAndSetTokenAndCookie(superUsername, superUserpassword);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), secureProductWf.csrfToken, secureProductWf.cookie, "");
	}

	
}
