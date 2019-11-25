package com.dts.adminportal.security.workflow;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.controller.ProductController;
import com.dts.adminportal.controller.UsersController;
import com.dts.adminportal.security.util.APIData;
import com.dts.adminportal.security.util.APIUtil;
import com.dts.adminportal.util.CreatePage;

import dts.com.adminportal.model.AccessoryInfo;
import dts.com.adminportal.model.AccessoryMain;
import dts.com.adminportal.model.AddAccessory;
import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.AddVariant;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.VariantInfo;
import dts.com.adminportal.model.Xpath;
import junit.framework.Assert;

public class APISecurityProductWorkflow extends CreatePage{
	public String csrfToken;
	public String cookie;
	public JSONObject jsonData;
	public HomeController homeControl;
	public UsersController userControl;
	public ProductController productControl;

	public APISecurityProductWorkflow(HomeController homeControl, ProductController productControl, UsersController userControl, JSONObject jsonData){
		this.homeControl = homeControl;
		this.userControl = userControl;
		this.productControl = productControl;
		this.jsonData = jsonData;
	}
	
	
	
	public void preconditionAndEnableAllPrivilege(String userMail){
		loginAndSetTokenAndCookie(superUsername, superUserpassword);
		enableAllPrivilegeOfUser(userMail);
		homeControl.click(AddUser.SAVE);
	}
	
	/**
	 * Step:
	 * Login to dts with super user account
	 * Go to user page and Enable all privileges of input user
	 * Disable input privilege
	 * Save when finish
	 * @param userMail 
	 * @param privilege
	 */
	public void preconditionAndDisablePrivilege(String userMail, String privilege, boolean isDTSPrivilege){
		loginAndSetTokenAndCookie(superUsername, superUserpassword);
		enableAllPrivilegeOfUser(userMail);
		if(isDTSPrivilege){
			homeControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, privilege);
		} else {
			homeControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, privilege);
		}
		homeControl.click(AddUser.SAVE);
	}
	
	/**
	 * First step: login to dts page
	 * Second step: Set token and cookie
	 * @param username
	 * @param password
	 */
	public void loginAndSetTokenAndCookie(String username, String password){
		homeControl.login(username, password);
		csrfToken = homeControl.getCSRFToken();
		cookie = homeControl.getCookie();
	}
	
	/**
	 * Enable all privilege of a user
	 * @param userMail
	 */
	public void enableAllPrivilegeOfUser(String userMail){
		// Navigate to User page
		homeControl.click(Xpath.LINK_USERS);
		// Select a DTS User from table
		homeControl.selectUserByEmail(userMail);
		// Click Edit link
		homeControl.click(UserMgmt.EDIT);
		// enable all privilege
		homeControl.enableAllPrivilege();
	}
	
	/**
	 * Add new product and return that product
	 * @param companyData data of company in JsonData file. Input null to get the default company data
	 * @param brandData data of company in JsonData file. Input null to get the default brand data
	 * @return JSONObject
	 */
	public JSONObject createNewProduct(String companyData, String brandData){
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.COMPANY_DATA).toString());
		if (companyData != null && !"".equals(companyData)){
			company = APIUtil.parseDataToJsonObject(jsonData.get(companyData).toString());
		}
		Long companyID = Long.parseLong(company.get("id").toString());
		// Get brand ID
		JSONObject brand = APIUtil.parseDataToJsonObject(jsonData.get(APIData.BRAND_DATA).toString());
		if (brandData != null && !"".equals(brandData)){
			brand = APIUtil.parseDataToJsonObject(jsonData.get(brandData).toString());
		}
		String brandID = brand.get("id").toString();
		// Create new product
		JSONObject productData = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_PRODUCT_DRAFT).toString());
		productData.put("partnerId", companyID);
		productData.put("modelName", "Test" + RandomStringUtils.randomNumeric(6));
		productData.put("brandId", brandID);
		productData.put("ean", RandomStringUtils.randomNumeric(12));
		productData.put("upc", RandomStringUtils.randomNumeric(11));
		return productData;
	}
	
	/**
	 * Add new variant and return that product
	 * @return JSONObject
	 */
	public JSONObject createNewVariant(String productId){
		JSONObject company = APIUtil.parseDataToJsonObject(jsonData.get(APIData.COMPANY_DATA).toString());
		Long companyID = Long.parseLong(company.get("id").toString());
		// Get brand ID
		JSONObject brand = APIUtil.parseDataToJsonObject(jsonData.get(APIData.BRAND_DATA).toString());
		String brandID = brand.get("id").toString();
		// Create new product
		JSONObject variantData = APIUtil.parseDataToJsonObject(jsonData.get(APIData.ADD_VARIANT_DRAFT).toString());
		variantData.put("partnerId", companyID);
		variantData.put("name", "Test" + RandomStringUtils.randomNumeric(6));
		variantData.put("id", brandID);
		variantData.put("assetId", productId);
		return variantData;
	}
	
	public JSONObject sendPostAddProduct(){
		JSONObject productData = createNewProduct(null, null);
		return APIUtil.sendPost(APIData.ADD_PRODUCT_URL, csrfToken, cookie, productData);
	}
	
	public void addNewProduct(Hashtable<String,String> data){
		// 1. Click on Product menu link
		homeControl.click(Xpath.linkAccessories);
		// 2. Click "Add product" link
		homeControl.click(AccessoryMain.ADD_ACCESSORY);
		// 3. Fill valid value into all required fields
		// 4. Upload valid tuning file and marketing file
		// 5. Click "Save" link
		productControl.addAccessories(AddAccessory.getHash(), data);
	}
	
	public void addNewVariant(Hashtable<String,String> data){
		homeControl.click(AccessoryInfo.ADD_VARIANT);
		Assert.assertTrue(homeControl.isElementPresent(AddVariant.TITLE));
		// Fill valid value into all requires fields
		// Click "Save" link
		productControl.addVariant(AddVariant.getHash(), data);
	}
	
	/**
	 * Add new Product and tuning
	 * @param data new product data
	 * @param marketing marketing the product if true
	 */
	public void addProductAndTunningAndMarketing(Hashtable<String,String> data, boolean marketing){
		addNewProduct(data);
		homeControl.click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		homeControl.click(AccessoryInfo.APPROVE_TUNING);
		if(marketing){
			homeControl.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
			homeControl.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
			homeControl.click(AccessoryInfo.APPROVE_MARKETING);
		}
	}
	
	/**
	 * Add new variant and tuning
	 * @param data new variant data
	 * @param marketing marketing the variant if true
	 */
	public void addVariantAndTunningAndMarketing(Hashtable<String,String> data, boolean marketing){
		addNewVariant(data);
		homeControl.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		homeControl.click(VariantInfo.APPROVE_TUNING);
		if(marketing){
			homeControl.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
			homeControl.click(VariantInfo.REQUEST_MARKETING_REVIEW);
			homeControl.click(VariantInfo.APPROVE_MARKETING);
		}
	}
	
	public void addProductAndPublish(Hashtable<String,String> data){
		addProductAndTunningAndMarketing(data, true);
		homeControl.click(AccessoryInfo.PUBLISH_PRODUCT);
	}
	
	public void addVariantAndPublish(Hashtable<String,String> data){
		addVariantAndTunningAndMarketing(data, true);
		homeControl.click(AccessoryInfo.PUBLISH_PRODUCT);
	}
	
	public void addProductAndSuspend(Hashtable<String,String> data){
		addProductAndPublish(data);
		homeControl.click(AccessoryInfo.SUSPEND);
	}
	
	public void addVariantAndSuspend(Hashtable<String,String> data){
		addVariantAndPublish(data);
		homeControl.click(AccessoryInfo.SUSPEND_VARIANT);
	}
	
	/**
	 * Update product tuning status: change tuning rating to A and save
	 * @param productName name of product
	 */
	public void editTuningRating(String productName){
		homeControl.click(Xpath.linkAccessories);
		Assert.assertEquals(true, productControl.selectProductByName(productName).booleanValue());
		homeControl.click(AccessoryInfo.EDIT_MODE);
		Hashtable<String,String> editData = new Hashtable<String, String>();
		editData.put("tuning rating", "A");
		editData.put("save", "yes");
		productControl.addAccessories(AddAccessory.getHash(), editData);
	}

	/**
	 * Approve or decline the request tuning
	 * @param productName name of product
	 * @param tuningAction AccessoryInfo.APPROVE_TUNING or AccessoryInfo.DECLINE_TUNING
	 */
	public void updateTuningApproval(String productName, String tuningAction){
		homeControl.click(Xpath.linkAccessories);
		Assert.assertEquals(true, productControl.selectProductByName(productName).booleanValue());
		homeControl.click(tuningAction);
	}
	
	/**
	 * request and approve marketing by DTS user
	 * Must be navigated to selected item page before calling this method
	 * @param marketingAction AccessoryInfo.APPROVE_MARKETING or AccessoryInfo.DECLINE_MARKETING or null if dont do anything
	 */
	public void requestAndDoMarketingByDTSUser(String marketingAction){
		homeControl.click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		homeControl.click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		if(marketingAction != null)
			homeControl.click(marketingAction);
	}
	
	/**
	 * Add and approve tuning and approve marketing of request tuning product
	 * @param productName
	 * @param marketingAction AccessoryInfo.APPROVE_MARKETING or AccessoryInfo.DECLINE_MARKETING or null if dont do anything
	 */
	public void editTuningAndDoMarketingByDTSUser(String productName, String marketingAction){
		editTuningRating(productName);
		updateTuningApproval(productName, AccessoryInfo.APPROVE_TUNING);
		requestAndDoMarketingByDTSUser(marketingAction);
	}
	
	
	//--------------------GET-SET Methods----------------------
	public String getCsrfToken() {
		return csrfToken;
	}

	public String getCookie() {
		return cookie;
	}
	
}
