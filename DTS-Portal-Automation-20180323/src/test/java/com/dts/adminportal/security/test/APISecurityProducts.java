package com.dts.adminportal.security.test;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.UserInfo;
import com.dts.adminportal.model.VariantInfo;
import com.dts.adminportal.security.util.APIData;
import com.dts.adminportal.security.util.APIUtil;
import com.dts.adminportal.util.TestData;

import junit.framework.Assert;

public class APISecurityProducts extends BasePage{
	private JSONObject jsonData;
	
	@Override
	protected void initData() {
		jsonData = APIUtil.parseDataToJsonObject(APIData.JSON_DATA_FILE);		
	}
		
	/*
	 * Verify that DTS user is unable to add, edit and create new products version when the "Add and manage products" privilege is disabled
	 */
	@Test
	public void APIPV_01(){
		productControl.addLog("ID : APIPV_01 : Verify that DTS user is unable to add, edit and create new products version when the \"Add and manage products\" privilege is disabled");
		/*
		 	Precondition: The "Add and manage products" privilege is disabled
			POST http://devportal.dts.com/saap/api/assets HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.privileges.Add_and_manage_products.getName(), false);
		JSONObject productData = productWf.createNewProduct(null, null, DTS_USER_SER_ID, jsonData);
		System.out.println("productData: " + productData);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_PRODUCT_URL, productWf.csrfToken, productWf.cookie, productData);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: nothing to clean up
		 */
		productControl.logout();
	}
	
	/*
	 * Verify that DTS user is unable to add, edit and create new variant version when the "Add and manage products" privilege is disabled
	 */
	@Test
	public void APIPV_02(){
		productControl.addLog("ID : APIPV_02 : Verify that DTS user is unable to add, edit and create new variant version when the \"Add and manage products\" privilege is disabled");
		/*
		 	Precondition: The "Add and manage products" privilege is disabled
			POST http://devportal.dts.com/saap/api/addSKU HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.privileges.Add_and_manage_products.getName(), false);
		JSONObject productData = productWf.createNewProduct(null, null, DTS_USER_SER_ID, jsonData);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_VARIANT_PRODUCT_URL, productWf.csrfToken, productWf.cookie, productData);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: nothing to clean up
		 */
		productControl.logout();
	}
	
	/*
	 * Verify that Partner user is unable to add, edit and create new products version when the "Add and manage products" privilege is disabled
	 */
	@Test
	public void APIPV_03(){
		productControl.addLog("ID : APIPV_03 : Verify that Partner user is unable to add, edit and create new products version when the \"Add and manage products\" privilege is disabled");
		/*
		 	Precondition: The "Add and manage products" privilege is disabled
			POST http://devportal.dts.com/saap/api/assets HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(PARTNER_DTS_SECURITY_USER, Privileges.privileges.Add_and_manage_products.getName(), false);
		JSONObject productData = productWf.createNewProduct(null, null, PARTNER_DTS_USER_SER_ID, jsonData);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_PRODUCT_URL, productWf.csrfToken, productWf.cookie, productData);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: nothing to clean up
		 */
		productControl.logout();
	}
	
	/*
	 * Verify that Partner user is unable to add, edit and create new variant version when the "Add and manage products" privilege is disabled
	 */
	@Test
	public void APIPV_04(){
		productControl.addLog("ID : APIPV_04 : Verify that Partner user is unable to add, edit and create new variant version when the \"Add and manage products\" privilege is disabled");
		/*
		 	Precondition: The "Add and manage products" privilege is disabled
			POST http://devportal.dts.com/saap/api/addSKU HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(PARTNER_DTS_SECURITY_USER, Privileges.privileges.Add_and_manage_products.getName(), false);
		JSONObject productData = productWf.createNewProduct(null, null, PARTNER_DTS_USER_SER_ID, jsonData);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_VARIANT_PRODUCT_URL, productWf.csrfToken, productWf.cookie, productData);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: nothing to clean up
		 */
		productControl.logout();
	}
	
	/*
	 * Verify that DTS user is unable to publish product when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_05(){
		productControl.addLog("ID : APIPV_05 : Verify that DTS user is unable to publish product when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		 */
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.privileges.Publish_and_suspend_products.getName(), false);
//		productWf.preconditionAndEnableAllPrivilege(DTS_SECURITY_USER);
		// Create new product and approve tuning and marketing
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addProductAndTunningAndMarketing(productData, true);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to publish variant when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_06(){
		productControl.addLog("ID : APIPV_06 : Verify that DTS user is unable to publish variant when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.privileges.Publish_and_suspend_products.getName(), false);
		
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addProductAndPublish(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, true, true);
		productWf.addVariantAndTunningAndMarketing(variantData, true);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_VARIANT_PRODUCT_URL(variantId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
		
	}

	/*
	 * Verify that Partner user is unable to publish product when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_07(){
		productControl.addLog("ID : APIPV_07 : Verify that Partner user is unable to publish product when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		 */
		productWf.preconditionAndDisablePrivilege(PARTNER_DTS_SECURITY_USER, Privileges.privileges.Publish_and_suspend_products.getName(), false);
		// Create new product and approve tuning and marketing
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addProductAndTunningAndMarketing(productData, true);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to publish variant when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_08(){
		productControl.addLog("ID : APIPV_08 : Verify that Partner user is unable to publish variant when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(PARTNER_DTS_SECURITY_USER, Privileges.privileges.Publish_and_suspend_products.getName(), false);
		
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addProductAndPublish(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, true, true);
		productWf.addVariantAndTunningAndMarketing(variantData, true);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_VARIANT_PRODUCT_URL(variantId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to publish product of brand which is not assigned to manage when the  "Publish and suspend products" privilege is enabled
	 */
	@Test
	public void APIPV_09(){
		productControl.addLog("ID : APIPV_09 : Verify that Partner user is unable to publish product of brand which is not assigned to manage when the \"Publish and suspend products\" privilege is enabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled
			GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.1
		 */
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productWf.enableAllPrivilegeOfUser(PARTNER_DTS_SECURITY_USER);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, BasePage.SECURITY_BRAND_1, false);
		productControl.click(AddUser.SAVE);
		// Create new product and approve tuning and marketing
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addProductAndTunningAndMarketing(productData, true);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to publish variant of brand which is not assigned to manage when the  "Publish and suspend products" privilege is enabled
	 */
	@Test
	public void APIPV_10(){
		productControl.addLog("ID : APIPV_10 : Verify that Partner user is unable to publish variant of brand which is not assigned to manage when the \"Publish and suspend products\" privilege is enabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productWf.enableAllPrivilegeOfUser(PARTNER_DTS_SECURITY_USER);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, BasePage.SECURITY_BRAND_1, false);
		productControl.click(AddUser.SAVE);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addProductAndPublish(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, true, true);
		productWf.addVariantAndTunningAndMarketing(variantData, true);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_VARIANT_PRODUCT_URL(variantId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to publish product of other companies when the �"Publish and suspend products" privilege is enabled with all brands
	 */
	@Test
	public void APIPV_11(){
		productControl.addLog("ID : APIPV_11 : Verify that Partner user is unable to publish product of other companies when the \"Publish and suspend products\" privilege is enabled with all brands");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled
			GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		// Create new product and approve tuning and marketing
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_2, BasePage.SECURITY_BRAND_2, true, true, true);
		productWf.addProductAndTunningAndMarketing(productData, true);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to publish variant of other companies when the  "Publish and suspend products" privilege is enabled with all brands
	 */
	@Test
	public void APIPV_12(){
		productControl.addLog("ID : APIPV_12 : Verify that Partner user is unable to publish variant of other companies when the  \"Publish and suspend products\" privilege is enabled with all brands");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_2, BasePage.SECURITY_BRAND_2, true, true, true);
		productWf.addProductAndPublish(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, true, true);
		productWf.addVariantAndTunningAndMarketing(variantData, true);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=8149&revision=0&time=1481165845198 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_VARIANT_PRODUCT_URL(variantId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to suspend product when the  "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_13(){
		productControl.addLog("ID : APIPV_13 : Verify that DTS user is unable to suspend product when the  \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.privileges.Publish_and_suspend_products.getName(), false);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addProductAndPublish(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to suspend variant when the  "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_14(){
		productControl.addLog("ID : APIPV_14 : Verify that DTS user is unable to suspend variant when the  \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.privileges.Publish_and_suspend_products.getName(), false);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addProductAndPublish(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, true, true);
		productWf.addVariantAndPublish(variantData);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_VARIANT_PRODUCT_URL(variantId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to suspend product when the  "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_15(){
		productControl.addLog("ID : APIPV_15 : Verify that Partner user is unable to suspend product when the  \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(PARTNER_DTS_SECURITY_USER, Privileges.privileges.Publish_and_suspend_products.getName(), false);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addProductAndPublish(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));

		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to suspend variant when the  "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_16(){
		productControl.addLog("ID : APIPV_16 : Verify that Partner user is unable to suspend variant when the  \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(PARTNER_DTS_SECURITY_USER, Privileges.privileges.Publish_and_suspend_products.getName(), false);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addProductAndPublish(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, true, true);
		productWf.addVariantAndPublish(variantData);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_VARIANT_PRODUCT_URL(variantId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to suspend product of brand which is not assigned to manage when the  "Publish and suspend products" privilege is enabled
	 */
	@Test
	public void APIPV_17(){
		productControl.addLog("ID : APIPV_17 : Verify that Partner user is unable to suspend product of brand which is not assigned to manage when the \"Publish and suspend products\" privilege is enabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled for a limit brand
			GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		 */
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productWf.enableAllPrivilegeOfUser(PARTNER_DTS_SECURITY_USER);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, BasePage.SECURITY_BRAND_1, false);
		productControl.click(AddUser.SAVE);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addProductAndPublish(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));

		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to suspend variant of brand which is not assigned to manage when the  "Publish and suspend products" privilege is enabled
	 */
	@Test
	public void APIPV_18(){
		productControl.addLog("ID : APIPV_18 : Verify that Partner user is unable to suspend variant of brand which is not assigned to manage when the \"Publish and suspend products\" privilege is enabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productWf.enableAllPrivilegeOfUser(PARTNER_DTS_SECURITY_USER);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, BasePage.SECURITY_BRAND_1, false);
		productControl.click(AddUser.SAVE);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addProductAndPublish(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, true, true);
		productWf.addVariantAndPublish(variantData);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_VARIANT_PRODUCT_URL(variantId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to suspend product of other companies when the �"Publish and suspend products" privilege is enabled with all brandss
	 */
	@Test
	public void APIPV_19(){
		productControl.addLog("ID : APIPV_19 : Verify that Partner user is unable to suspend product of other companies when the \"Publish and suspend products\" privilege is enabled with all brands");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled with all brands
			GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_2, BasePage.SECURITY_BRAND_2, true, true, true);
		productWf.addProductAndPublish(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));

		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to suspend variant of other company when the "Publish and suspend products" privilege is enabled with all brands
	 */
	@Test
	public void APIPV_20(){
		productControl.addLog("ID : APIPV_20 : Verify that Partner user is unable to suspend variant of other companies when the  \"Publish and suspend products\" privilege is enabled with all brands");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled for all brands
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_2, BasePage.SECURITY_BRAND_2, true, true, true);
		productWf.addProductAndPublish(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, true, true);
		productWf.addVariantAndPublish(variantData);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_VARIANT_PRODUCT_URL(variantId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}

	/*
	 * Verify that DTS user is unable to Restore product when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_21(){
		productControl.addLog("ID : APIPV_21 : Verify that DTS user is unable to Restore product when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845722545 HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.privileges.Publish_and_suspend_products.getName(), false);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addProductAndSuspend(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, ProductModel.ProductStatus.PUBLISH_SUSPENDED.getName());
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));

		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=27543&assetRevision=0&time=1481169358948 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to Restore variant when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_22(){
		productControl.addLog("ID : APIPV_22 : Verify that DTS user is unable to Restore variant when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.privileges.Publish_and_suspend_products.getName(), false);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addProductAndPublish(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, true, true);
		productWf.addVariantAndSuspend(variantData);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_VARIANT_PRODUCT_URL(variantId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}


	/*
	 * Verify that Partner user is unable to Restore product when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_23(){
		productControl.addLog("ID : APIPV_23 : Verify that Partner user is unable to Restore product when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845722545 HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(PARTNER_DTS_SECURITY_USER, Privileges.privileges.Publish_and_suspend_products.getName(), false);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addProductAndSuspend(productData);
		productControl.click(PageHome.linkAccessories);
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, ProductModel.ProductStatus.PUBLISH_SUSPENDED.getName());
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));

		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to Restore variant when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_24(){
		productControl.addLog("ID : APIPV_24 : Verify that Partner user is unable to Restore variant when the �\"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(PARTNER_DTS_SECURITY_USER, Privileges.privileges.Publish_and_suspend_products.getName(), false);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addProductAndPublish(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, true, true);
		productWf.addVariantAndSuspend(variantData);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_VARIANT_PRODUCT_URL(variantId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}


	/*
	 * Verify that Partner user is unable to restore product of brand which is not assigned to manage when the  "Publish and suspend products" privilege is enabled
	 */
	@Test
	public void APIPV_25(){
		productControl.addLog("ID : APIPV_25 : Verify that Partner user is unable to restore product of brand which is not assigned to manage when the \"Publish and suspend products\" privilege is enabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled for a limit brand
			GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845722545 HTTP/1.1
		 */
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productWf.enableAllPrivilegeOfUser(PARTNER_DTS_SECURITY_USER);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, BasePage.SECURITY_BRAND_1, false);
		productControl.click(AddUser.SAVE);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addProductAndSuspend(productData);
		productControl.click(PageHome.linkAccessories);
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, ProductModel.ProductStatus.PUBLISH_SUSPENDED.getName());
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));

		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to restore variant of brand which is not assigned to manage when the  "Publish and suspend products" privilege is enabled
	 */
	@Test
	public void APIPV_26(){
		productControl.addLog("ID : APIPV_26 : Verify that Partner user is unable to restore variant of brand which is not assigned to manage when the \"Publish and suspend products\" privilege is enabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		Assert.assertTrue(userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, SECURITY_BRAND_1, false));
		productControl.click(AddUser.SAVE);
		
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, SECURITY_BRAND_1, true, true, true);
		productWf.addProductAndPublish(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, true, true);
		productWf.addVariantAndSuspend(variantData);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_VARIANT_PRODUCT_URL(variantId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	

	/*
	 * Verify that Partner user is unable to restore product of other companies when the "Publish and suspend products" privilege is enabled with all brandss
	 */
	@Test
	public void APIPV_27(){
		productControl.addLog("ID : APIPV_27 : Verify that Partner user is unable to restore product of other companies when the \"Publish and suspend products\" privilege is enabled with all brands");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled with all brands
			GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_2, BasePage.SECURITY_BRAND_2, true, true, true);
		productWf.addProductAndSuspend(productData);
		productControl.click(PageHome.linkAccessories);
		productControl.selectOptionByName(ProductModel.PRODUCT_FILTER, ProductModel.ProductStatus.PUBLISH_SUSPENDED.getName());
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));

		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.SUSPEND_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to restore variant of other company when the "Publish and suspend products" privilege is enabled with all brands
	 */
	@Test
	public void APIPV_28(){
		productControl.addLog("ID : APIPV_28 : Verify that Partner user is unable to restore variant of other companies when the \"Publish and suspend products\" privilege is enabled with all brands");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled for all brands
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_2, BasePage.SECURITY_BRAND_2, true, true, true);
		productWf.addProductAndPublish(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, true, true);
		productWf.addVariantAndSuspend(variantData);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.PUBLISH_VARIANT_PRODUCT_URL(variantId.toString()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}


	/*
	 * Verify that DTS user is unable to delete product when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_29(){
		productControl.addLog("ID : APIPV_29 : Verify that DTS user is unable to delete product when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/deleteModel?assetid=3895&revision=0&time=1436846963281&partnerid=49 HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.privileges.Publish_and_suspend_products.getName(), false);
		// Create new product and approve tuning and marketing
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/deleteModel?assetid=3895&revision=0&time=1436846963281&partnerid=49 HTTP/1.1
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.DELETE_METHOD,
				APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken,
				productWf.cookie, "");
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to delete variant when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_30(){
		productControl.addLog("ID : APIPV_30 : Verify that DTS user is unable to delete variant when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/deleteVariantModel?variantId=1256&revision=0&time=1436846943073&partnerid=49 HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.privileges.Publish_and_suspend_products.getName(), false);
		
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, true, true);
		productWf.addNewVariant(variantData);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, 
				APIData.DELETE_VARIANT_PRODUCT_URL(variantId.toString()), 
				productWf.csrfToken, productWf.cookie, "");
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
		
	}


	/*
	 * Verify that Partner  user is unable to delete product when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_31(){
		productControl.addLog("ID : APIPV_31 : Verify that Partner  user is unable to delete product when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/deleteModel?assetid=3895&revision=0&time=1436846963281&partnerid=49 HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(PARTNER_DTS_SECURITY_USER, Privileges.privileges.Publish_and_suspend_products.getName(), false);
		// Create new product and approve tuning and marketing
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/deleteModel?assetid=3895&revision=0&time=1436846963281&partnerid=49 HTTP/1.1
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.DELETE_METHOD,
				APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken,
				productWf.cookie, "");
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to delete variant when the "Publish and suspend products" privilege is disabled
	 */
	@Test
	public void APIPV_32(){
		productControl.addLog("ID : APIPV_32 : Verify that Partner  user is unable to delete variant when the \"Publish and suspend products\" privilege is disabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is disabled
			GET http://devportal.dts.com/saap/api/deleteVariantModel?variantId=1256&revision=0&time=1436846943073&partnerid=49 HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(PARTNER_DTS_SECURITY_USER, Privileges.privileges.Publish_and_suspend_products.getName(), false);
		
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, true, true);
		productWf.addNewVariant(variantData);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, 
				APIData.DELETE_VARIANT_PRODUCT_URL(variantId.toString()), 
				productWf.csrfToken, productWf.cookie, "");
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
		
	}


	/*
	 * Verify that Partner user is unable to delete product of brand which is not assigned to manage when the  "Publish and suspend products" privilege is enabled
	 */
	@Test
	public void APIPV_33(){
		productControl.addLog("ID : APIPV_33 : Verify that Partner user is unable to delete product of brand which is not assigned to manage when the \"Publish and suspend products\" privilege is enabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled for a limit brand
			GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		 */
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productWf.enableAllPrivilegeOfUser(PARTNER_DTS_SECURITY_USER);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, BasePage.SECURITY_BRAND_1, false);
		productControl.click(AddUser.SAVE);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));

		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.DELETE_METHOD,
				APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken,
				productWf.cookie, "");
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to delete variant of brand which is not assigned to manage when the  "Publish and suspend products" privilege is enabled
	 */
	@Test
	public void APIPV_34(){
		productControl.addLog("ID : APIPV_34 : Verify that Partner user is unable to delete variant of brand which is not assigned to manage when the \"Publish and suspend products\" privilege is enabled");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productWf.enableAllPrivilegeOfUser(PARTNER_DTS_SECURITY_USER);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, BasePage.SECURITY_BRAND_1, false);
		productControl.click(AddUser.SAVE);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, true, true);
		productWf.addVariantAndPublish(variantData);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.DELETE_METHOD,
				APIData.DELETE_VARIANT_PRODUCT_URL(variantId.toString()), productWf.csrfToken,
				productWf.cookie, "");
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to delete product of other companies when the "Publish and suspend products" privilege is enabled with all brandss
	 */
	@Test
	public void APIPV_35(){
		productControl.addLog("ID : APIPV_35 : Verify that Partner user is unable to delete product of other companies when the \"Publish and suspend products\" privilege is enabled with all brands");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled with all brands
			GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_2, BasePage.SECURITY_BRAND_2, true, true, true);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));

		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/suspendModel?assetid=4039&revision=0&time=1436845469254&partnerid=53 HTTP/1.1
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.DELETE_METHOD,
				APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken,
				productWf.cookie, "");
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to delete variant of other company when the "Publish and suspend products" privilege is enabled with all brands
	 */
	@Test
	public void APIPV_36(){
		productControl.addLog("ID : APIPV_36 : Verify that Partner user is unable to delete variant of other companies when the  \"Publish and suspend products\" privilege is enabled with all brands");
		/*
		 	Precondition: The "Publish and suspend products" privilege is enabled for all brands
			GET http://devportal.dts.com/saap/api/publishAssetVariant?variantId=1306&revision=0&time=1436846728391 HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_2, BasePage.SECURITY_BRAND_2, true, true, true);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, true, true);
		productWf.addVariantAndPublish(variantData);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendHTTPRequest(APIData.DELETE_METHOD,
				APIData.DELETE_VARIANT_PRODUCT_URL(variantId.toString()), productWf.csrfToken,
				productWf.cookie, "");
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}


	/*
	 * Verify that DTS user is unable to Request product tuning when the "Request product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_37(){
		productControl.addLog("ID : APIPV_37 : Verify that DTS user is unable to Request product tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=37&time=1436847596040&partnerid=23&assetid=4040 HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.privileges.Can_request_DTS_tune_products.getName(), false);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, false, false, false);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(productId.toString(), 
				true, APIData.TuningAction.DTS_REQUEST_PENDING.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to Request variant tuning when the  "Request product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_38(){
		productControl.addLog("ID : APIPV_38 : Verify that DTS user is unable to Request variant tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=36&time=1436857799878&partnerid=23&variantid=1307 HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.privileges.Can_request_DTS_tune_products.getName(), false);
		
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, false, false, false);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		productWf.addNewVariant(variantData);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(variantId.toString(), 
				false, APIData.TuningAction.DTS_REQUEST_PENDING.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
		
	}

	/*
	 * Verify that Partner user is unable to Request product tuning when the  "Request product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_39(){
		productControl.addLog("ID : APIPV_39 : Verify that Partner user is unable to Request product tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=37&time=1436847596040&partnerid=23&assetid=4040 HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(PARTNER_DTS_SECURITY_USER, Privileges.privileges.Can_request_DTS_tune_products.getName(), false);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, false, false, false);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(productId.toString()
				,true, APIData.TuningAction.DTS_REQUEST_PENDING.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to Request variant tuning when the  "Request product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_40(){
		productControl.addLog("ID : APIPV_40 : Verify that Partner user is unable to Request variant tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=36&time=1436857799878&partnerid=23&variantid=1307 HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(PARTNER_DTS_SECURITY_USER, Privileges.privileges.Can_request_DTS_tune_products.getName(), false);
		
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, false, false, false);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		productWf.addNewVariant(variantData);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(variantId.toString(), 
				false, APIData.TuningAction.DTS_REQUEST_PENDING.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
		
	}
	
	/*
	 * Verify that Partner user is unable to approve product tuning when the  "Request product tunings" privilege is disabled
	 */
@Test
	public void APIPV_41(){
		productControl.addLog("ID : APIPV_41 : Verify that Partner user is unable to approve product tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateTuningStatus?assetId=4039&time=1436844887023&revision=0&tuning_status=Approved HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(PARTNER_DTS_SECURITY_USER, Privileges.privileges.Can_request_DTS_tune_products.getName(), false);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, false, false);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(productId.toString(), true, APIData.TuningAction.APPROVE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}	
	
/*
	 * Verify that Partner user is unable to approve variant tuning when the  "Request product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_42(){
		productControl.addLog("ID : APIPV_42 : Verify that Partner user is unable to approve variant tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1306&revision=0&time=1436846425054&tuning_status=Approved HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(PARTNER_DTS_SECURITY_USER, Privileges.privileges.Can_request_DTS_tune_products.getName(), false);
		
		// Create new Product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, false, false);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		productWf.addNewVariant(variantData);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(variantId.toString(), false, APIData.TuningAction.APPROVE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
		
	}
	
	/*
	 * Verify that Partner user is unable to Decline product tuning when the  "Request product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_43(){
		productControl.addLog("ID : APIPV_41 : Verify that Partner user is unable to Decline product tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateTuningStatus?assetId=4039&revision=0&time=1436845180645&tuning_status=partner_declined HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(PARTNER_DTS_SECURITY_USER, Privileges.privileges.Can_request_DTS_tune_products.getName(), false);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, false, false);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(productId.toString(), 
				true, APIData.TuningAction.PARTNER_DECLINE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to Decline variant tuning when the  "Request product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_44(){
		productControl.addLog("ID : APIPV_42 : Verify that Partner user is unable to Decline variant tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1306&time=1436845942332&revision=0&tuning_status=partner_declined HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(PARTNER_DTS_SECURITY_USER, Privileges.privileges.Can_request_DTS_tune_products.getName(), false);
		
		// Create new Product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, false, false);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		productWf.addNewVariant(variantData);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(variantId.toString(), 
				false, APIData.TuningAction.PARTNER_DECLINE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
		
	}

	/*
	 * Verify that Partner user is unable to request revised product tuning when the  "Request product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_45(){
		productControl.addLog("ID : APIPV_45 : Verify that Partner user is unable to request revised product tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=2&time=1438070912695&partnerid=24&assetid=5259 HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(PARTNER_DTS_SECURITY_USER, Privileges.privileges.Can_request_DTS_tune_products.getName(), false);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, false, false);
		productWf.addNewProduct(productData);
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.DECLINE_TUNING);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(productId.toString(), 
				true, APIData.TuningAction.DTS_REQUEST_PENDING.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to request revised variant tuning when the  "Request product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_46(){
		productControl.addLog("ID : APIPV_46 : Verify that Partner user is unable to request revised variant tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=37&time=1438074176141&partnerid=23&variantid=1706
		 */
		productWf.preconditionAndDisablePrivilege(PARTNER_DTS_SECURITY_USER, Privileges.privileges.Can_request_DTS_tune_products.getName(), false);
		
		// Create new Product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, false, false);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		productWf.addNewVariant(variantData);
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.DECLINE_TUNING);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(variantId.toString(), 
				false, APIData.TuningAction.DTS_REQUEST_PENDING.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
		
	}
	
	/*
	 * Verify that Partner user is unable to revoke product tuning when the  "Request product tunings" privilege is disabled
	 */
	public void APIPV_47(){
		productControl.addLog("ID : APIPV_47 : Verify that Partner user is unable to revoke product tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			http://devportal.dts.com/saap/api/updateTuningStatus?assetId=8386&revision=0&time=1441695381555&tuning_status=Revoked
		 */
		productWf.preconditionAndDisablePrivilege(PARTNER_DTS_SECURITY_USER, Privileges.privileges.Can_request_DTS_tune_products.getName(), false);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, false, false);
		productWf.addProductAndTunningAndMarketing(productData, false);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(productId.toString(), 
				true, APIData.TuningAction.REVOKE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to revoke variant tuning when the  "Request product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_48(){
		productControl.addLog("ID : APIPV_48 : Verify that Partner user is unable to revoke variant tuning when the \"Request product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Request product tunings" privilege is disabled
			http://devportal.dts.com/saap/api/updateTuningStatus?assetId=8386&revision=0&time=1441695381555&tuning_status=Revoked
		 */
		productWf.preconditionAndDisablePrivilege(PARTNER_DTS_SECURITY_USER, Privileges.privileges.Can_request_DTS_tune_products.getName(), false);
		
		// Create new Product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, false, false);
		productWf.addProductAndTunningAndMarketing(productData, false);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		productWf.addVariantAndTunningAndMarketing(variantData, false);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(variantId.toString(), 
				false, APIData.TuningAction.REVOKE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
		
	}
	
	/*
	 * Verify that DTS user is unable to Approve product tuning when the  "Approve product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_49(){
		productControl.addLog("ID : APIPV_49 : Verify that DTS user is unable to Approve product tuning when the \"Approve product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Approve product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateTuningStatus?assetId=4039&time=1436844887023&revision=0&tuning_status=Approved HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.dtsPrivileges.Approve_product_tunings.getName(), true);
		productControl.logout();
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, false, false);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.click(ProductModel.ADD_PRODUCT);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), productData);
		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
//		home.click(TuningRequestForm.AGREE);
//		home.click(TuningRequestForm.SUBMIT);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		productWf.editTuningRating(productData.get("name"));
//		secureProductWf.updateTuningApproval(productData.get("name"), AccessoryInfo.APPROVE_TUNING);
		
		// Send a sample GET request as following:
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(productId.toString(), true, APIData.TuningAction.APPROVE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to Approve variant tuning when the "Approve product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_52(){
		productControl.addLog("ID : APIPV_52 : Verify that DTS user is unable to Approve variant tuning when the \"Approve product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Approve product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1306&revision=0&time=1436846425054&tuning_status=Approved HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.dtsPrivileges.Approve_product_tunings.getName(), true);
		productControl.logout();
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Create new Product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, false, false);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.click(ProductModel.ADD_PRODUCT);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), productData);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		productWf.addNewVariant(variantData);
		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		productWf.editTuningRating(productData.get("name"));
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(variantId.toString(), false, APIData.TuningAction.APPROVE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete added product
		 */
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
		
	}
		
	/*
	 * Verify that DTS user is unable to Revoke product tuning when the "Approve product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_50(){
		productControl.addLog("ID : APIPV_50 : Verify that DTS user is unable to Revoke product tuning when the \"Approve product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Approve product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateTuningStatus?assetId=4039&revision=0&time=1436845041392&tuning_status=Revoked HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.dtsPrivileges.Approve_product_tunings.getName(), true);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, false, false);
		productWf.addProductAndTunningAndMarketing(productData, false);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(productId.toString(), 
				true, APIData.TuningAction.REVOKE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete added product
		 */
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to Revoke variant tuning when the "Approve product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_51(){
		productControl.addLog("ID : APIPV_51 : Verify that DTS user is unable to Revoke variant tuning when the \"Approve product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Approve product tunings" privilege is disabled
			http://devportal.dts.com/saap/api/updateTuningStatus?assetId=8386&revision=0&time=1441695381555&tuning_status=Revoked
		 */
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.dtsPrivileges.Approve_product_tunings.getName(), true);
		
		// Create new Product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, false, false);
		productWf.addProductAndTunningAndMarketing(productData, false);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		productWf.addVariantAndTunningAndMarketing(variantData, false);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(variantId.toString(),
				false, APIData.TuningAction.REVOKE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete added product
		 */
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
		
	}

	/*
	 * Verify that DTS user is unable to Decline product tuning when the  "Approve product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_53(){
		productControl.addLog("ID : APIPV_53 : Verify that DTS user is unable to Decline product tuning when the \"Approve product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Approve product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateTuningStatus?assetId=4039&revision=0&time=1436845180645&tuning_status=partner_declined HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.dtsPrivileges.Approve_product_tunings.getName(), true);
		productControl.logout();
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, false, false);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.click(ProductModel.ADD_PRODUCT);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), productData);
		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		productWf.editTuningRating(productData.get("name"));
//		secureProductWf.updateTuningApproval(productData.get("name"), AccessoryInfo.APPROVE_TUNING);
		
		// Send a sample GET request as following:
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(productId.toString(), 
				true, APIData.TuningAction.PARTNER_DECLINE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete added product
		 */
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to Decline variant tuning when the "Approve product tunings" privilege is disabled
	 */
	@Test
	public void APIPV_54(){
		productControl.addLog("ID : APIPV_54 : Verify that DTS user is unable to Decline variant tuning when the \"Approve product tunings\" privilege is disabled");
		/*
		 	Precondition: The "Approve product tunings" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=4039&revision=0&time=1436845180645&tuning_status=partner_declined HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.dtsPrivileges.Approve_product_tunings.getName(), true);
		productControl.logout();
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Create new Product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, false, false);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.click(ProductModel.ADD_PRODUCT);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), productData);
		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		productWf.addNewVariant(variantData);
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		productWf.editTuningRating(productData.get("name"));
		productWf.updateTuningApproval(productData.get("name"), ProductDetailModel.APPROVE_TUNING);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(variantId.toString(),
				false, APIData.TuningAction.PARTNER_DECLINE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete added product
		 */
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
		
	}

	/*
	 * Verify that DTS user is unable to Approve product marketing when the "Approve marketing information" privilege is disabled
	 */
	@Test
	public void APIPV_55(){
		productControl.addLog("ID : APIPV_55 : Verify that DTS user is unable to Approve product marketing when the \"Approve marketing information\" privilege is disabled");
		/*
		 	Precondition: The "Approve marketing information" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateTuningStatus?assetId=4039&revision=0&time=1436845180645&tuning_status=partner_declined HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.dtsPrivileges.Approve_marketing_information.getName(), true);
		productControl.logout();
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, false);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.click(ProductModel.ADD_PRODUCT);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), productData);
		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		productWf.editTuningAndDoMarketingByDTSUser(productData.get("name"), null);
//		home.click(AccessoryInfo.APPROVE_MARKETING);
		
		// Send a sample GET request as following:
		JSONObject requestResult = APIUtil.sendGet(
				APIData.MARKETING_ACTION_URL(productId.toString(), true, APIData.MarketingAction.APPROVED.getName()), 
				productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete added product
		 */
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to Approve variant marketing when the "Approve marketing information" privilege is disabled
	 */
	@Test
	public void APIPV_56(){
		productControl.addLog("ID : APIPV_56 : Verify that DTS user is unable to Approve variant marketing when the \"Approve marketing information\" privilege is disabled");
		/*
		 	Precondition: The "Approve marketing information" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateMarketingStatus?assetId=4039&time=1436844981082&revision=0&marketing_status=Approved HTTP/1.1
		 */
//		secureProductWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.Approve_marketing_info, true);
		productWf.preconditionAndEnableAllPrivilege(DTS_SECURITY_USER);
		productControl.logout();
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Create new Product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, false);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.click(ProductModel.ADD_PRODUCT);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), productData);
		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(false, true, false);
		productWf.addNewVariant(variantData);
//		home.click(AccessoryInfo.REQUEST_TUNING);
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		productWf.editTuningAndDoMarketingByDTSUser(productData.get("name"), ProductDetailModel.APPROVE_MARKETING);
		Assert.assertTrue(productControl.selectVariantbyName(variantData.get("name")));
		productWf.requestAndDoMarketingByDTSUser(null);
		productControl.logout();
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.dtsPrivileges.Approve_marketing_information.getName(), true);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(
				APIData.MARKETING_ACTION_URL(variantId.toString(), false, APIData.MarketingAction.APPROVED.getName()), 
				productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete added product
		 */
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
		
	}
	
	/*
	 * Verify that DTS user is unable to Revoke product marketing when the "Approve marketing information" privilege is disabled
	 */
	@Test
	public void APIPV_57(){
		productControl.addLog("ID : APIPV_57 : Verify that DTS user is unable to Revoke product marketing when the \"Approve marketing information\" privilege is disabled");
		/*
		 	Precondition: The "Approve marketing information" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateMarketingStatus?assetId=4039&revision=0&time=1436845002728&marketing_status=Revoked HTTP/1.1
		 */
//		secureProductWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.Approve_marketing_info, true);
		productWf.preconditionAndEnableAllPrivilege(DTS_SECURITY_USER);
		productControl.logout();
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, false);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.click(ProductModel.ADD_PRODUCT);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), productData);
		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
		
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		productWf.editTuningAndDoMarketingByDTSUser(productData.get("name"), ProductDetailModel.APPROVE_MARKETING);
		productControl.logout();
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.dtsPrivileges.Approve_marketing_information.getName(), true);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		JSONObject requestResult = APIUtil.sendGet(
				APIData.MARKETING_ACTION_URL(productId.toString(), true, APIData.MarketingAction.REVOKED.getName()), 
				productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete added product
		 */
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to Revoke variant marketing when the "Approve marketing information" privilege is disabled
	 */
	@Test
	public void APIPV_58(){
		productControl.addLog("ID : APIPV_58 : Verify that DTS user is unable to Revoke variant marketing when the \"Approve marketing information\" privilege is disabled");
		/*
		 	Precondition: The "Approve marketing information" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateMarketingStatus?assetId=4039&time=1436844981082&revision=0&marketing_status=Revoke HTTP/1.1
		 */
//		secureProductWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.Approve_marketing_info, true);
		productWf.preconditionAndEnableAllPrivilege(DTS_SECURITY_USER);
		productControl.logout();
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Create new Product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, false);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.click(ProductModel.ADD_PRODUCT);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), productData);
		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(false, true, false);
		productWf.addNewVariant(variantData);
//		home.click(AccessoryInfo.REQUEST_TUNING);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		productWf.editTuningAndDoMarketingByDTSUser(productData.get("name"), ProductDetailModel.APPROVE_MARKETING);
		Assert.assertTrue(productControl.selectVariantbyName(variantData.get("name")));
		productWf.requestAndDoMarketingByDTSUser(ProductDetailModel.APPROVE_MARKETING);
		productControl.logout();
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.dtsPrivileges.Approve_marketing_information.getName(), true);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(
				APIData.MARKETING_ACTION_URL(variantId.toString(), false, APIData.MarketingAction.REVOKED.getName()),
				productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete added product
		 */
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
		
	}
	
	/*
	 * Verify that DTS user is unable to Decline product marketing when the "Approve marketing information" privilege is disabled
	 */
	@Test
	public void APIPV_59(){
		productControl.addLog("ID : APIPV_59 : Verify that DTS user is unable to Decline product marketing when the \"Approve marketing information\" privilege is disabled");
		/*
		 	Precondition: The "Approve marketing information" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateMarketingStatus?assetId=4039&time=1436845298945&revision=0&marketing_status=Declined HTTP/1.1
		 */
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.dtsPrivileges.Approve_marketing_information.getName(), true);
		productControl.logout();
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, false);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.click(ProductModel.ADD_PRODUCT);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), productData);
		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
		
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		productWf.editTuningAndDoMarketingByDTSUser(productData.get("name"), null);
//		home.click(AccessoryInfo.APPROVE_MARKETING);
		
		// Send a sample GET request as following:
		JSONObject requestResult = APIUtil.sendGet(
				APIData.MARKETING_ACTION_URL(productId.toString(), true, APIData.MarketingAction.DECLINED.getName()), 
				productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete added product
		 */
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that DTS user is unable to decline variant marketing when the "Approve marketing information" privilege is disabled
	 */
	@Test
	public void APIPV_60(){
		productControl.addLog("ID : APIPV_56 : Verify that DTS user is unable to decline variant marketing when the \"Approve marketing information\" privilege is disabled");
		/*
		 	Precondition: The "Approve marketing information" privilege is disabled
			GET http://devportal.dts.com/saap/api/updateMarketingStatus?assetId=4039&time=1436844981082&revision=0&marketing_status=Declined HTTP/1.1
		 */
//		secureProductWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.Approve_marketing_info, true);
		productWf.preconditionAndEnableAllPrivilege(DTS_SECURITY_USER);
		productControl.logout();
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Create new Product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, false);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.click(ProductModel.ADD_PRODUCT);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), productData);
		productControl.click(ProductDetailModel.REQUEST_TUNING_REVIEW);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(false, true, false);
		productWf.addNewVariant(variantData);
//		home.click(AccessoryInfo.REQUEST_TUNING);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		productWf.editTuningAndDoMarketingByDTSUser(productData.get("name"), ProductDetailModel.APPROVE_MARKETING);
		Assert.assertTrue(productControl.selectVariantbyName(variantData.get("name")));
		productWf.requestAndDoMarketingByDTSUser(null);
		productControl.logout();
		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.dtsPrivileges.Approve_marketing_information.getName(), true);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(
				APIData.MARKETING_ACTION_URL(variantId.toString(), false, APIData.MarketingAction.DECLINED.getName()), 
				productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete added product
		 */
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
		
	}

	/*
	 * Verify that Partner user is unable to add, edit and create new product version for another company
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void APIPV_61(){
		productControl.addLog("ID : APIPV_61 : Verify that Partner user is unable to add, edit and create new product version for another company");
		/*
			POST http://devportal.dts.com/saap/api/assets HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_2, BasePage.SECURITY_BRAND_2, true, true, true);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.click(ProductModel.ADD_PRODUCT);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), productData);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		JSONObject productData2 = productWf.createNewProduct(null, null, PARTNER_DTS_USER_SER_ID, jsonData);
		System.out.println("ProductData: " + productData2);
		productData2.put("modelName", "Test" + RandomStringUtils.randomNumeric(6));
		productData2.put("id", productId);
		productData2.put("partnerId", DTS_USER_SER_COMP_ID);
		productData2.put("brandId", "11");
		productData2.put("ean", productData.get("ean"));
		productData2.put("upc", productData.get("upc"));
		productData2.put("updatedBy", PARTNER_DTS_USER_SER_ID);
		productData2.put("revision", null);
		productData2.put("tuningRating", "A");
		productData2.put("salesforceId", "");
		productData2.put("modelNum", "");
		productData2.put("assetType", "Headphone");
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		
		productControl.click(PageHome.linkAccessories);
		productControl.click(ProductModel.ADD_PRODUCT);
		JSONObject requestResult1 = APIUtil.sendGet("http://devportal.dts.com/saap/api/assets/getDtsid", productWf.csrfToken, productWf.cookie);
		String dtsId = requestResult1.get("result").toString();
		productData2.put("keyDtsId", dtsId);
		System.out.println(productData2);
		// Send a sample GET request as following:
		// POST http://devportal.dts.com/saap/api/assets HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_PRODUCT_URL, productWf.csrfToken, productWf.cookie, productData2);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete added product
		 */
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to add, edit and create new version variant for another company
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void APIPV_62(){
		productControl.addLog("ID : APIPV_62 : Verify that Partner user is unable to add, edit and create new version variant for another company");
		/*
			POST http://devportal.dts.com/saap/api/addSKU HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_2, BasePage.SECURITY_BRAND_2, true, true, true);
		productWf.addProductAndPublish(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
//		Hashtable<String,String> variantData = TestData.variantData(true, true, true);
//		productWf.addVariantAndPublish(variantData);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
//		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
//		Assert.assertNotSame(0, variantId);
		JSONObject variantData2 = productWf.createNewVariant(productId.toString(), jsonData);
		System.out.println("VariantData: " + variantData2);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		
		JSONObject requestResult1 = APIUtil.sendGet("http://devportal.dts.com/saap/api/assets/getDtsid", productWf.csrfToken, productWf.cookie);
		String dtsId = requestResult1.get("result").toString();
		variantData2.put("keyDtsId", dtsId);
		System.out.println("variantData2: " + variantData2);
		
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_VARIANT_PRODUCT_URL, productWf.csrfToken, productWf.cookie, variantData2);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete added product
		 */
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}

	/*
	 * Verify that Partner user is unable to add, edit and create new product version for a brand which is not assigned to manage.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void APIPV_63(){
		productControl.addLog("ID : APIPV_63 : Verify that Partner user is unable to add, edit and create new product version for a brand which is not assigned to manage.");
		/*
			POST http://devportal.dts.com/saap/api/assets HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, BasePage.SECURITY_BRAND_1, false);
		productControl.click(AddUser.SAVE);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addProductAndPublish(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		JSONObject productData2 = productWf.createNewProduct(null, null, PARTNER_DTS_USER_SER_ID, jsonData);
		productData2.put("modelName", "Test" + RandomStringUtils.randomNumeric(6));
		productData2.put("id", productId);
		productData2.put("partnerId", PARTNER_DTS_USER_SER_COMP_ID);
		productData2.put("brandId",  securityBrandId2);
		productData2.put("ean", productData.get("ean"));
		productData2.put("upc", productData.get("upc"));
		productData2.put("updatedBy", PARTNER_DTS_USER_SER_ID);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
		
		// Send a sample GET request as following:
		// POST http://devportal.dts.com/saap/api/assets HTTP/1.1
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_PRODUCT_URL, productWf.csrfToken, productWf.cookie, productData2);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete added product
		 */
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to add, edit and create new variant version for a brand which is not assigned to manage.
	 */
	@Test
	public void APIPV_64(){
		productControl.addLog("ID : APIPV_64 : Verify that Partner user is unable to add, edit and create new variant version for a brand which is not assigned to manage.");
		/*
			POST http://devportal.dts.com/saap/api/addSKU HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, BasePage.SECURITY_BRAND_1, false);
		productControl.click(AddUser.SAVE);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addProductAndPublish(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		JSONObject variantData2 = productWf.createNewVariant(productId.toString(), jsonData);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_VARIANT_PRODUCT_URL, productWf.csrfToken, productWf.cookie, variantData2);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete added product
		 */
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to request product tuning of another company when the "Request product tunings" is enabled with all brands
	 */
	@Test
	public void APIPV_65(){
		productControl.addLog("ID : APIPV_65 : Verify that Partner user is unable to request product tuning of another company when the \"Request product tunings\" is enabled with all brands");
		/*
			GET http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=37&time=1436847596040&partnerid=23&assetid=4040 HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		// securityInvalidBrand is used for select all brand in the Brand Privilege of user
		Assert.assertFalse(userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityInvalidBrand, false));
		productControl.click(AddUser.SAVE);
		// Create new Product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_2, BasePage.SECURITY_BRAND_2, false, false, false);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(productId.toString(), 
				true, APIData.TuningAction.DTS_REQUEST_PENDING.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete added product
		 */
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to approve product tuning of another company when the "Request product tunings" is enabled with all brands
	 */
	@Test
	public void APIPV_66(){
		productControl.addLog("ID : APIPV_66 : Verify that Partner user is unable to approve product tuning of another company when the \"Request product tunings\" is enabled with all brands");
		/*
			POST http://devportal.dts.com/saap/api/addSKU HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		// securityInvalidBrand is used for select all brand in the Brand Privilege of user
		Assert.assertFalse(userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityInvalidBrand, false));
		productControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_2, BasePage.SECURITY_BRAND_2, true, false, false);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(productId.toString(), true, APIData.TuningAction.APPROVE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to decline product tuning of another company when the "Request product tunings" is enabled with all brands
	 */
	@Test
	public void APIPV_67(){
		productControl.addLog("ID : APIPV_67 : Verify that Partner user is unable to decline product tuning of another company when the \"Request product tunings\" is enabled with all brands");
		/*
			POST http://devportal.dts.com/saap/api/addSKU HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		// securityInvalidBrand is used for select all brand in the Brand Privilege of user
		Assert.assertFalse(userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityInvalidBrand, false));
		productControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_2, BasePage.SECURITY_BRAND_2, true, false, false);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(productId.toString(),
				true, APIData.TuningAction.PARTNER_DECLINE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to request revised product tuning of another company when the "Request product tunings" is enabled with all brands
	 */
	@Test
	public void APIPV_68(){
		productControl.addLog("ID : APIPV_68 : Verify that Partner user is unable to request revised product tuning of another company when the \"Request product tunings\" is enabled with all brands");
		/*
			POST http://devportal.dts.com/saap/api/addSKU HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		// securityInvalidBrand is used for select all brand in the Brand Privilege of user
		Assert.assertFalse(userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityInvalidBrand, false));
		productControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_2, BasePage.SECURITY_BRAND_2, true, false, false);
		productWf.addNewProduct(productData);
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.DECLINE_TUNING);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(productId.toString(),
				true, APIData.TuningAction.DTS_REQUEST_PENDING.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to revoke product tuning of another company when the "Request product tunings" is enabled with all brands
	 */
	@Test
	public void APIPV_69(){
		productControl.addLog("ID : APIPV_69 : Verify that Partner user is unable to revoke product tuning of another company when the \"Request product tunings\" is enabled with all brands");
		/*
			POST http://devportal.dts.com/saap/api/addSKU HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		// securityInvalidBrand is used for select all brand in the Brand Privilege of user
		Assert.assertFalse(userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityInvalidBrand, false));
		productControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_2, BasePage.SECURITY_BRAND_2, true, false, false);
		productWf.addProductAndTunningAndMarketing(productData, false);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(productId.toString(), 
				true, APIData.TuningAction.REVOKE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to request variant tuning of another company when the "Request product tunings" is enabled with all brands
	 */
	@Test
	public void APIPV_70(){
		productControl.addLog("ID : APIPV_70 : Verify that Partner user is unable to request variant tuning of another company when the \"Request product tunings\" is enabled with all brands");
		/*
			GET http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=36&time=1436857799878&partnerid=23&variantid=1307 HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		// securityInvalidBrand is used for select all brand in the Brand Privilege of user
		Assert.assertFalse(userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityInvalidBrand, false));
		productControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_2, BasePage.SECURITY_BRAND_1, false, false, false);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.click(ProductModel.ADD_PRODUCT);
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), productData);
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		productWf.addNewVariant(variantData);
		
		productControl.click(PageHome.LINK_PARTNER_ACCESSORIES);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(variantId.toString(), 
				false, APIData.TuningAction.DTS_REQUEST_PENDING.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to approve variant tuning of another company when the "Request product tunings" is enabled with all brands
	 */
	@Test
	public void APIPV_71(){
		productControl.addLog("ID : APIPV_71 : Verify that Partner user is unable to approve variant tuning of another company when the \"Request product tunings\" is enabled with all brands");
		/*
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1307&revision=0&time=1436855986876&tuning_status=Approved HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		// securityInvalidBrand is used for select all brand in the Brand Privilege of user
		Assert.assertFalse(userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityInvalidBrand, false));
		productControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_2, BasePage.SECURITY_BRAND_2, true, false, false);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		productWf.addNewVariant(variantData);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(variantId.toString(), false, APIData.TuningAction.APPROVE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to decline variant tuning of another company when the "Request product tunings" is enabled with all brands
	 */
	@Test
	public void APIPV_72(){
		productControl.addLog("ID : APIPV_72 : Verify that Partner user is unable to decline variant tuning of another company when the \"Request product tunings\" is enabled with all brands");
		/*
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1307&time=1436856041266&revision=0&tuning_status=dts_declined HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		// securityInvalidBrand is used for select all brand in the Brand Privilege of user
		Assert.assertFalse(userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityInvalidBrand, false));
		productControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_2, BasePage.SECURITY_BRAND_2, true, false, false);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		productWf.addNewVariant(variantData);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(variantId.toString(), 
				false, APIData.TuningAction.PARTNER_DECLINE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to request revised variant tuning of another company when the "Request product tunings" is enabled with all brands
	 */
	@Test
	public void APIPV_73(){
		productControl.addLog("ID : APIPV_73 : Verify that Partner user is unable to request revised variant tuning of another company when the \"Request product tunings\" is enabled with all brands");
		/*
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=4039&revision=0&time=1436845041392&tuning_status=Revoked HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		// securityInvalidBrand is used for select all brand in the Brand Privilege of user
		Assert.assertFalse(userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityInvalidBrand, false));
		productControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_2, BasePage.SECURITY_BRAND_2, true, false, false);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		productWf.addNewVariant(variantData);
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.DECLINE_TUNING);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(variantId.toString(),
				false, APIData.TuningAction.DTS_REQUEST_PENDING.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to revoke variant tuning of another company when the "Request product tunings" is enabled with all brands
	 */
	@Test
	public void APIPV_74(){
		productControl.addLog("ID : APIPV_74 : Verify that Partner user is unable to revoke variant tuning of another company when the \"Request product tunings\" is enabled with all brands");
		/*
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=4039&revision=0&time=1436845041392&tuning_status=Revoked HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		// securityInvalidBrand is used for select all brand in the Brand Privilege of user
		Assert.assertFalse(userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, securityInvalidBrand, false));
		productControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_2, BasePage.SECURITY_BRAND_2, true, false, false);
		productWf.addProductAndTunningAndMarketing(productData, false);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		productWf.addVariantAndTunningAndMarketing(variantData, false);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(variantId.toString(), 
				false, APIData.TuningAction.REVOKE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to request product tuning of brands which are not assigned to manage.
	 */
	@Test
	public void APIPV_75(){
		productControl.addLog("ID : APIPV_75 : Verify that Partner user is unable to request product tuning of brands which are not assigned to manage.");
		/*
			GET http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=36&time=1436857799878&partnerid=23&variantid=1307 HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		Assert.assertTrue(userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, SECURITY_BRAND_1, false));
		productControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, false, false, false);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(productId.toString(),
				true, APIData.TuningAction.DTS_REQUEST_PENDING.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to approve product tuning of brands which are not assigned to manage.
	 */
	@Test
	public void APIPV_76(){
		productControl.addLog("ID : APIPV_76 : Verify that Partner user is unable to approve product tuning of brands which are not assigned to manage.");
		/*
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1307&revision=0&time=1436855986876&tuning_status=Approved HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		Assert.assertTrue(userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, SECURITY_BRAND_1, false));
		productControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, false, false);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1307&revision=0&time=1436855986876&tuning_status=Approved HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(productId.toString(), true, APIData.TuningAction.APPROVE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to decline product tuning of brands which are not assigned to manage.
	 */
	@Test
	public void APIPV_77(){
		productControl.addLog("ID : APIPV_77 : Verify that Partner user is unable to decline product tuning of brands which are not assigned to manage.");
		/*
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1307&time=1436856041266&revision=0&tuning_status=dts_declined HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		Assert.assertTrue(userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, SECURITY_BRAND_1, false));
		productControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, false, false);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(productId.toString(), 
				true, APIData.TuningAction.PARTNER_DECLINE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to request revised product tuning of brands which are not assigned to manage.
	 */
	@Test
	public void APIPV_78(){
		productControl.addLog("ID : APIPV_78 : Verify that Partner user is unable to request revised product tuning of brands which are not assigned to manage.");
		/*
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=4039&revision=0&time=1436845041392&tuning_status=Revoked HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		Assert.assertTrue(userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, SECURITY_BRAND_1, false));
		productControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, false, false);
		productWf.addNewProduct(productData);
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.DECLINE_TUNING);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(productId.toString(),
				true, APIData.TuningAction.DTS_REQUEST_PENDING.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to revoke product tuning of brands which are not assigned to manage.
	 */
	@Test
	public void APIPV_79(){
		productControl.addLog("ID : APIPV_79 : Verify that Partner user is unable to revoke product tuning of brands which are not assigned to manage.");
		/*
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=4039&revision=0&time=1436845041392&tuning_status=Revoked HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		Assert.assertTrue(userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, SECURITY_BRAND_1, false));
		productControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, SECURITY_BRAND_1, true, false, false);
		productWf.addProductAndTunningAndMarketing(productData, false);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertNotSame(0, productId);
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=4039&revision=0&time=1436845041392&tuning_status=Revoked HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(productId.toString(), 
				true, APIData.TuningAction.REVOKE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to request variant tuning of brands which are not assigned to manage.
	 */
	@Test
	public void APIPV_80(){
		productControl.addLog("ID : APIPV_80 : Verify that Partner user is unable to request variant tuning of brands which are not assigned to manage.");
		/*
			GET http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=36&time=1436857799878&partnerid=23&variantid=1307 HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		Assert.assertTrue(userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, SECURITY_BRAND_1, false));
		productControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, false, false, false);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		productWf.addNewVariant(variantData);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(variantId.toString(),
				false, APIData.TuningAction.DTS_REQUEST_PENDING.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to approve variant tuning of brands which are not assigned to manage.
	 */
	@Test
	public void APIPV_81(){
		productControl.addLog("ID : APIPV_81 : Verify that Partner user is unable to approve variant tuning of brands which are not assigned to manage.");
		/*
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1307&revision=0&time=1436855986876&tuning_status=Approved HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		Assert.assertTrue(userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, SECURITY_BRAND_1, false));
		productControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, false, false);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		productWf.addNewVariant(variantData);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1307&revision=0&time=1436855986876&tuning_status=Approved HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(variantId.toString(), false, APIData.TuningAction.APPROVE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to approve variant tuning of brands which are not assigned to manage.
	 */
	@Test
	public void APIPV_82(){
		productControl.addLog("ID : APIPV_82 : Verify that Partner user is unable to approve variant tuning of brands which are not assigned to manage.");
		/*
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1307&time=1436856041266&revision=0&tuning_status=dts_declined HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		Assert.assertTrue(userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, SECURITY_BRAND_1, false));
		productControl.click(AddUser.SAVE);
		
		// Create new product
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, SECURITY_BRAND_1, true, false, false);
		productWf.addNewProduct(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
		
		// Create new Variant of above product
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		productWf.addNewVariant(variantData);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);
		
		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=1307&time=1436856041266&revision=0&tuning_status=dts_declined HTTP/1.1
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(variantId.toString(), 
				false, APIData.TuningAction.PARTNER_DECLINE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}	
	
	/*
	 * Verify that Partner user is unable to request revised variant tuning of brands which are not assigned to manage.
	 */
	@Test
	public void APIPV_83(){
		productControl.addLog("ID : APIPV_83 : Verify that Partner user is unable to request revised variant tuning of brands which are not assigned to manage.");
		/*
			http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=36&time=1441941223516&partnerid=49&variantid=264
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, BasePage.SECURITY_BRAND_1, false);
		productControl.click(AddUser.SAVE);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addProductAndPublish(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
				
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		productWf.addNewVariant(variantData);
		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		productControl.click(VariantInfo.DECLINE_TUNING);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// http://devportal.dts.com/saap/api/dtsTuningRequest?acceptance=true&tuningStatus=dts_request_pending&revision=0&additionalInfo=&userId=36&time=1441941223516&partnerid=49&variantid=264
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(variantId.toString(),
				false, APIData.TuningAction.DTS_REQUEST_PENDING.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete added product
		 */
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}
	
	/*
	 * Verify that Partner user is unable to revoke variant tuning of brands which are not assigned to manage.
	 */
	@Test
	public void APIPV_84(){
		productControl.addLog("ID : APIPV_84 : Verify that Partner user is unable to revoke variant tuning of brands which are not assigned to manage.");
		/*
			GET http://devportal.dts.com/saap/api/updateTuningStatus?variantId=4039&revision=0&time=1436845041392&tuning_status=Revoked HTTP/1.1
		 */
		productWf.preconditionAndEnableAllPrivilege(PARTNER_DTS_SECURITY_USER);
		productControl.click(UserInfo.EDIT);
		userControl.selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, BasePage.SECURITY_BRAND_1, false);
		productControl.click(AddUser.SAVE);
		// Create new Product and tuning and marketing and publish it
		Hashtable<String,String> productData = TestData.productData(BasePage.SECURITY_COMPANY_1, BasePage.SECURITY_BRAND_1, true, true, true);
		productWf.addProductAndPublish(productData);
		productControl.click(PageHome.linkAccessories);
		Integer productId = productControl.getProductIdByName(productData.get("name"));
		Assert.assertTrue(productControl.selectProductByName(productData.get("name")));
				
		// Create new Variant of above product and tuning and marketing it
		Hashtable<String,String> variantData = TestData.variantData(true, false, false);
		productWf.addVariantAndTunningAndMarketing(variantData, false);
		
		productControl.click(PageHome.linkAccessories);
		productControl.selectProductByName(productData.get("name"));
		Integer variantId = productControl.getVariantIdbyName(variantData.get("name"));
		Assert.assertNotSame(0, variantId);

		productControl.logout();
		productWf.loginAndSetTokenAndCookie(PARTNER_DTS_SECURITY_USER, PARTNER_DTS_SECURITY_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
		JSONObject requestResult = APIUtil.sendGet(APIData.TUNING_ACTION_URL(variantId.toString(),
				false, APIData.TuningAction.REVOKE.getName()), productWf.csrfToken, productWf.cookie);
		/*
		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
		 */
		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
		/*
		 * PostCondition: Delete added product
		 */
		productControl.logout();
		// Log into DTS portal as DTS user
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Delete product
		APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
	}		

//	/*
//	 * Verify that DTS user is unable to add, edit and create new products version when the "Add and manage products" privilege is disabled
//	 */
//	@SuppressWarnings({ "unused", "unchecked" })
//	@Test
//	public void Add_Product(){
//		productControl.addLog("ID : APIPV_01 : Verify that DTS user is unable to add, edit and create new products version when the \"Add and manage products\" privilege is disabled");
//		/*
//		 	Precondition: The "Add and manage products" privilege is disabled
//			POST http://devportal.dts.com/saap/api/assets HTTP/1.1
//		 */
////		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.privileges.Add_and_manage_products.getName(), false);
////		productWf.loginAndSetTokenAndCookie(BasePage.SUPER_USER_NAME, BasePage.SUPER_USER_PASSWORD);
////		productWf.enableAllPrivilegeOfUser(DTS_SECURITY_USER);
////		JSONObject productData = productWf.createNewProduct(null, null, DTS_USER_SER_ID, jsonData);
////		System.out.println("productData: " + productData);
////		productControl.logout();
//		productWf.loginAndSetTokenAndCookie(DTS_SECURITY_USER, DTS_SECURITY_PASSWORD);
//		// Send a sample GET request as following:
//		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
////		productControl.click(PageHome.linkAccessories);
////		productControl.click(ProductModel.ADD_PRODUCT);
////		JSONObject requestResult1 = APIUtil.sendGet("http://devportal.dts.com/saap/api/assets/getDtsid", productWf.csrfToken, productWf.cookie);
////		String dtsId = requestResult1.get("result").toString();
////		productData.put("keyDtsId", dtsId);
////		System.out.println("productData: " + productData);
////		
////		JSONObject requestResult = APIUtil.sendPost(APIData.ADD_PRODUCT_URL, productWf.csrfToken, productWf.cookie, productData);
//		
//		productControl.click(PageHome.linkAccessories);
//		productControl.selectAccessorybyName("Test540617");
//		productControl.click(ProductDetailModel.EDIT_MODE);
//		long time = Calendar.getInstance().getTime().getTime();
//		JSONObject requestResult3 = APIUtil.sendGet("http://devportal.dts.com/saap/api/assets/27728?revision=0&time=" + time, productWf.csrfToken, productWf.cookie);
//		JSONObject result = (JSONObject) requestResult3.get("result");
//		
//		JSONObject productEdit = productWf.editProducts(null, null, DTS_USER_SER_ID, jsonData);
//		productEdit.remove("tunings");
//		productEdit.remove("images");
//		productEdit.put("tunings", result.get("tunings"));
//		productEdit.put("images", result.get("images"));
//		System.out.println("product Eidt: " + productEdit);
//		JSONObject requestResult11 = APIUtil.sendPost(APIData.ADD_PRODUCT_URL, productWf.csrfToken, productWf.cookie, productEdit);
//		/*
//		 * {"apiErrorCode":"PERMISSION_DENY","message":"You don't have permission."}
//		 */
////		Assert.assertEquals(APIData.ERROR_MESSAGE, requestResult.get("message").toString());
//		/*
//		 * PostCondition: nothing to clean up
//		 */
//		productControl.logout();
//	}
	
	
	/*
	 * Verify that DTS user is unable to add, edit and create new products version when the "Add and manage products" privilege is disabled
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	@Test
	public void Add_Product(){
		productControl.addLog("ID : APIPV_01 : Verify that DTS user is unable to add, edit and create new products version when the \"Add and manage products\" privilege is disabled");
		/*
		 	Precondition: The "Add and manage products" privilege is disabled
			POST http://devportal.dts.com/saap/api/assets HTTP/1.1
		 */
//		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.privileges.Add_and_manage_products.getName(), false);
//		productWf.loginAndSetTokenAndCookie(BasePage.SUPER_USER_NAME, BasePage.SUPER_USER_PASSWORD);
//		productWf.enableAllPrivilegeOfUser(DTS_SECURITY_USER);
		JSONObject productData = productWf.createNewProduct(null, null, DTS_USER_SER_ID, jsonData);
//		System.out.println("productData: " + productData);
//		productControl.logout();
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Send a sample GET request as following:
		// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
//		productControl.click(PageHome.linkAccessories);
//		productControl.click(ProductModel.ADD_PRODUCT);
		for (int i = 0; i < 1100; i++) {
			JSONObject requestResult1 = APIUtil.sendGet("http://10.20.13.64:8080/saap/api/assets/getDtsid", productWf.csrfToken, productWf.cookie);
			String dtsId = requestResult1.get("result").toString();
			productData.put("keyDtsId", dtsId);
			productData.put("partnerId", "23");
			productData.put("brandId", "29");
			productData.put("upc", "");
			productData.put("ean", "");
			productData.put("modelName", "Test" + RandomStringUtils.randomNumeric(6));
			System.out.println("i: " + i);
			
			JSONObject requestResult = APIUtil.sendPost("http://10.20.13.64:8080/saap/api/assets", productWf.csrfToken, productWf.cookie, productData);
		}
	}
	
	@Test
	public void Del_Product(){
		productControl.addLog("ID : APIPV_01 : Verify that DTS user is unable to add, edit and create new products version when the \"Add and manage products\" privilege is disabled");
		/*
		 	Precondition: The "Add and manage products" privilege is disabled
			POST http://devportal.dts.com/saap/api/assets HTTP/1.1
		 */
//		productWf.preconditionAndDisablePrivilege(DTS_SECURITY_USER, Privileges.privileges.Add_and_manage_products.getName(), false);
//		productWf.loginAndSetTokenAndCookie(BasePage.SUPER_USER_NAME, BasePage.SUPER_USER_PASSWORD);
//		productWf.enableAllPrivilegeOfUser(DTS_SECURITY_USER);
		productWf.loginAndSetTokenAndCookie(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		for (int i = 0; i < 500; i++) {
			productControl.click(PageHome.linkAccessories);
			Integer productId = productControl.getProductIdByCharacter("SQATest");
			// Send a sample GET request as following:
			// GET http://devportal.dts.com/saap/api/publishAsset?assetId=4039&assetRevision=0&time=1436845425543 HTTP/1.11
			APIUtil.sendHTTPRequest(APIData.DELETE_METHOD, APIData.DELETE_PRODUCT_URL(productId.toString()), productWf.csrfToken, productWf.cookie, "");
			System.out.println("i: " + i);
		}
		
	}
	
}
