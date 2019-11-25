package com.dts.adminportal.workflow;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;

import com.dts.adminportal.controller.AudioRouteController;
import com.dts.adminportal.controller.BaseController;
import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.controller.LoginController;
import com.dts.adminportal.controller.ProductController;
import com.dts.adminportal.controller.UsersController;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.RoomModelInfo;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.model.VariantInfo;
import com.dts.adminportal.security.util.APIData;
import com.dts.adminportal.security.util.APIUtil;

import junit.framework.Assert;

public class ProductWorkflow{
	public String csrfToken;
	public String cookie;
	public UsersController userControl;
	public ProductController productControl;
	public LoginController loginControl;	
	public ProductWorkflow(LoginController loginControl, ProductController productControl, UsersController userControl){
		this.loginControl = loginControl;
		this.userControl = userControl;
		this.productControl = productControl;
	
	}
		
	/**
	 * Do the following step:
	 * 1 - Login as super user and set token and cookie for it
	 * 2 - Go to user page and enable all privileges of input user
	 * 3 - Save
	 * @param userMail
	 */
	public void preconditionAndEnableAllPrivilege(String userMail){
		loginAndSetTokenAndCookie(BasePage.SUPER_USER_NAME, BasePage.SUPER_USER_PASSWORD);
		enableAllPrivilegeOfUser(userMail);
		productControl.click(AddUser.SAVE);
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
		loginAndSetTokenAndCookie(BasePage.SUPER_USER_NAME, BasePage.SUPER_USER_PASSWORD);
		enableAllPrivilegeOfUser(userMail);
		if(isDTSPrivilege){
			userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, privilege);
		} else {
			userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, privilege);
		}
		productControl.click(AddUser.SAVE);
	}
	
	/**
	 * First step: login to dts page
	 * Second step: Set token and cookie
	 * @param username
	 * @param password
	 */
	public void loginAndSetTokenAndCookie(String username, String password){
		loginControl.login(username, password);
		csrfToken = productControl.getCSRFToken();
		cookie = productControl.getCookie();
	}
	
	/**
	 * Do the following step:
	 * 1 - click on User page link
	 * 2 - Select input user
	 * 3 - click on edit user
	 * 4 - Enable all privilege of user 
	 * note: Not yet click on save button
	 * @param userMail
	 */
	public void enableAllPrivilegeOfUser(String userMail){
		// Navigate to User page
		productControl.click(PageHome.LINK_USERS);
		// Select a DTS User from table
		userControl.selectUserInfoByEmail(userMail);
		// Click Edit link
		productControl.click(UserMgmt.EDIT);
		// enable all privilege
		userControl.enableAllPrivileges();
	}
	
	/**
	 * Add new product and return that product
	 * @param companyData data of company in JsonData file. Input null to get the default company data
	 * @param brandData data of company in JsonData file. Input null to get the default brand data
	 * @return JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject createNewProduct(String companyData, String brandData, String userId, JSONObject jsonData){
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
		productData.put("updatedBy", userId);
		productData.put("createdBy", userId);
		return productData;
	}
	
	/**
	 * Add new variant and return that product
	 * @return JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject createNewVariant(String productId, JSONObject jsonData){
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
	
	public JSONObject sendPostAddProduct(String userId, JSONObject jsonData){
		JSONObject productData = createNewProduct(null, null, userId, jsonData);
		return APIUtil.sendPost(APIData.ADD_PRODUCT_URL, csrfToken, cookie, productData);
	}
	
	/**
	 * step 1: Click on product link
	 * step 2: Click add product
	 * Step 3: add data to input field
	 * @param data
	 */
	public void addNewProduct(Hashtable<String,String> data){
		// 1. Click on Product menu link
		productControl.click(PageHome.linkAccessories);
		// 2. Click "Add product" link
		productControl.click(ProductModel.ADD_PRODUCT);
		// 3. Fill valid value into all required fields
		// 4. Upload valid tuning file and marketing file
		// 5. Click "Save" link
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), data);
	}
	
	/**
	 * Add new variant
	 * **NOTE** Need to navigate to specified product before calling this method
	 * @param data
	 */
	public void addNewVariant(Hashtable<String,String> data){
		productControl.click(ProductDetailModel.ADD_VARIANT);
		Assert.assertTrue(productControl.isElementPresent(AddEditProductModel.VARIANT_TITLE));
		// Fill valid value into all requires fields
		// Click "Save" link
		productControl.addVariant(AddEditProductModel.getVariantHash(), data);
	}
	
	/**
	 * Add new Product and tuning
	 * step 1: Click on product link
	 * step 2: Click add product
	 * Step 3: add data to input field
	 * step 4: save
	 * step 5: approve tunning
	 * step 6: approve marketing if marketing is true
	 * @param data new product data
	 * @param marketing marketing the product if true
	 */
	public void addProductAndTunningAndMarketing(Hashtable<String,String> data, boolean marketing){
		addNewProduct(data);
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		if(marketing){
			productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
			productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
			productControl.click(ProductDetailModel.APPROVE_MARKETING);
		}
	}
	
	/**
	 * Add new variant and tuning
	 * @param data new variant data
	 * @param marketing marketing the variant if true
	 */
	public void addVariantAndTunningAndMarketing(Hashtable<String,String> data, boolean marketing){
		addNewVariant(data);
		if (!"use parent".equals(data.get("add tunning"))) {
			productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
			productControl.click(VariantInfo.APPROVE_TUNING);
			productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
			productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		}
		if(marketing){
			productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_2);
			productControl.click(VariantInfo.REQUEST_MARKETING_REVIEW);
			productControl.click(VariantInfo.APPROVE_MARKETING);
		}
	}
	
	/**
	 * step 1: Click on product link
	 * step 2: Click add product
	 * Step 3: add data to input field
	 * step 4: save
	 * step 5: approve tunning
	 * step 6: approve marketing
	 * step 7: publish product
	 * @param data
	 */
	public void addProductAndPublish(Hashtable<String,String> data){
		addProductAndTunningAndMarketing(data, true);
		productControl.click(ProductDetailModel.PUBLISH_PRODUCT);
	}
	
	public void addVariantAndPublish(Hashtable<String,String> data){
		addVariantAndTunningAndMarketing(data, true);
		productControl.click(ProductDetailModel.PUBLISH_PRODUCT);
	}
	
	public void addProductAndSuspend(Hashtable<String,String> data){
		addProductAndPublish(data);
		productControl.click(ProductDetailModel.SUSPEND);
		// 20151013 add step: click on confirmation dialog
		productControl.selectConfirmationDialogOption("Suspend");
	}
	
	public void addVariantAndSuspend(Hashtable<String,String> data){
		addVariantAndPublish(data);
		productControl.click(ProductDetailModel.SUSPEND_VARIANT);
		// 20151013 add step: click on confirmation dialog
//		productControl.selectConfirmationDialogOption("Suspend");
	}
	
	/**
	 * required:
	 * + Login by dts user
	 * + Add new product by dts user
	 * step 1: Click on Decline Tuning
	 * step 2: Fill required message
	 * step 3: Click submit
	 * **NOTE: should have productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1) 
	 * before calling this method when tuning file is uploaded by DTS user 
	 */
	public void declineTuning(){
		productControl.click(ProductDetailModel.DECLINE_TUNING);
		productControl.editData(ProductDetailModel.DECLINE_TUNING_REQUIRED_MESS, "decline tuning mess");
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
	}
	
	/**
	 * required:
	 * + Login by dts user
	 * + Must be at product detail page
	 * + Product must have approved tuning status and not yet marketing
	 * step 1: Click on Revoke link
	 * step 2: Click on agree check box
	 * step 3: Fill required message
	 * step 4: Click submit
	 */
	public void revokeTuning(){
		productControl.click(ProductDetailModel.REVOKE_TUNING);
		productControl.click(ProductDetailModel.AGREE_SUBMISSION);
		productControl.editData(ProductDetailModel.DECLINE_TUNING_REQUIRED_MESS, "revoke tuning mess");
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
	}
	
	/**
	 * required:
	 * + Must be at product detail page
	 * + Product must have uploaded tuning file
	 * step 1: Click on approve tuning
	 * step 2: Click on submit button
	 * step 3: Click on title to be back to product detail page
	 * **NOTE: should have productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1) 
	 * before calling this method when tuning file is uploaded by DTS user and also approved 
	 * dts user 
	 */
	public void approveTuning(){
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
	}
	
	/**
	 * step 1: Click on Request dts tuning
	 * step 2: Click on Agree checkbox
	 * step 3: Click on submit button
	 * step 4: Click on title to be back to product detail page
	 */
	public void requestDTSTuning(){
		productControl.click(ProductDetailModel.REQUEST_TUNING);
		productControl.click(ProductDetailModel.AGREE_SUBMISSION);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
	}
		
	
	//----------------OLD FUNCTIONS---------------------
	// TODO: change below method with existed methods
	public void createVariantPublish(Hashtable<String,String> hashXpath, Hashtable<String,String> data) { // Should login with
		productControl.addVariant(hashXpath, data);
		// Publish variant
		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		productControl.click(VariantInfo.APPROVE_TUNING);
		productControl.click(VariantInfo.PARTNER_ACTIONS_STEP_2);		
		productControl.click(VariantInfo.REQUEST_MARKETING_REVIEW);		
		productControl.click(VariantInfo.APPROVE_MARKETING);
		productControl.click(VariantInfo.PUBLISH);
	}
	
	// TODO: change below method with existed methods	
	public void createAccessoryPublish(Hashtable<String,String> hashXpath, Hashtable<String,String> data) { // Should login
		productControl.addProduct(hashXpath, data);
		// Publish accessory
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_1);
		productControl.click(ProductDetailModel.APPROVE_TUNING);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		productControl.click(ProductDetailModel.APPROVE_MARKETING);
		productControl.click(ProductDetailModel.PUBLISH);
	}
	
	/**
	 * Update product tuning status: change tuning rating to A and save
	 * @param productName name of product
	 */
	public void editTuningRating(String productName){
		productControl.click(PageHome.linkAccessories);
		Assert.assertEquals(true, productControl.selectProductByName(productName));
		productControl.click(ProductDetailModel.EDIT_MODE);
		Hashtable<String,String> editData = new Hashtable<String, String>();
		editData.put("tuning rating", "A");
		editData.put("save", "yes");
		productControl.addProduct(AddEditProductModel.getProductInputFieldIdsHash(), editData);
	}

	/**
	 * Approve or decline the request tuning
	 * @param productName name of product
	 * @param tuningAction AccessoryInfo.APPROVE_TUNING or AccessoryInfo.DECLINE_TUNING
	 */
	public void updateTuningApproval(String productName, String tuningAction){
		productControl.click(PageHome.linkAccessories);
		Assert.assertEquals(true, productControl.selectProductByName(productName));
		productControl.click(tuningAction);
		productControl.click(ProductDetailModel.REQUEST_TUNING_SUBMIT);
		productControl.click(ProductDetailModel.MODEL_TUNING_ACTION_DISPLAY);
	}
	
	/**
	 * request and approve marketing by DTS user
	 * Must be navigated to selected item page before calling this method
	 * @param marketingAction AccessoryInfo.APPROVE_MARKETING or AccessoryInfo.DECLINE_MARKETING or null if dont do anything
	 */
	public void requestAndDoMarketingByDTSUser(String marketingAction){
		productControl.click(ProductDetailModel.PARTNER_ACTIONS_STEP_2);
		productControl.click(ProductDetailModel.REQUEST_MARKETING_REVIEW);
		if(marketingAction != null)
			productControl.click(marketingAction);
	}
	
	/**
	 * Add and approve tuning and approve marketing of request tuning product
	 * @param productName
	 * @param marketingAction AccessoryInfo.APPROVE_MARKETING or AccessoryInfo.DECLINE_MARKETING or null if dont do anything
	 */
	public void editTuningAndDoMarketingByDTSUser(String productName, String marketingAction){
		editTuningRating(productName);
		updateTuningApproval(productName, ProductDetailModel.APPROVE_TUNING);
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
