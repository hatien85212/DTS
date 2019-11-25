package com.dts.adminportal.dts.test;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AddPromotion;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.PromotionInfo;
import com.dts.adminportal.model.PromotionList;
import com.dts.adminportal.model.UserEdit;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.util.TestData;

public class DTSPrivilegesPromotions extends BasePage{
	
	@Override
	protected void initData() {
	}	
	
	/*
	 * Verify that the DTS user can view a promotion's info when both �Add and manage promotions� privilege is disabled
	 */
	@Test
	public void PPRO_01(){
		promotionControl.addLog("ID : PPRO_01 : Verify that the DTS user can view a promotion's info when both �Add and manage promotions� privilege is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the  �Add and manage promotions� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Promotions� page is displayed
			9. Navigate to �Promotions� page
			VP: Verify that the promotion list page is displayed
			10. Select a promotion from promotions list
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		promotionControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.selectUserInfoByEmail(DTS_USER);
		// 5. Click �Edit� link
		promotionControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		// 6. Disable the  �Add and manage promotions� privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_promotions.getName());
		promotionControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		promotionControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the �Promotions� page is displayed
		 */
		Assert.assertTrue(promotionControl.isElementPresent(PageHome.linkPromotions));
		// 9. Navigate to �Promotions� page
		promotionControl.click(PageHome.linkPromotions);
		/*
		 * VP: Verify that the promotion list page is displayed
		 */
		Assert.assertEquals(promotionControl.existsElement(PromotionList.getElementInfo()), true);
		// 10. Select a promotion from promotions list
		String promotionName = promotionControl.selectAPromotion();
		/*
		 * Verify that the promotion's info page is displayed
		 */
		Assert.assertEquals(promotionControl.getTextByXpath(PromotionInfo.PROMOTION_NAME), promotionName);
		Assert.assertEquals(promotionControl.existsElement(PromotionInfo.getElementInfo()), true);
	}
	
	/*
	 * Verify that the DTS user cannot add new, edit, publish, suspend or delete promotions when both �Add and manage promotions� privilege is disabled
	 */
	@Test
	public void PPRO_02(){
		promotionControl.addLog("ID : PPRO_02 : Verify that the DTS user cannot add new, edit, publish, suspend or delete promotions when both �Add and manage promotions� privilege is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the  �Add and manage promotions� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Promotions� page is displayed
			9. Navigate to �Promotions� page
			VP: Verify that the promotion list page is displayed without �Add Promo� link
			10. Select a promotion from promotions list
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		promotionControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.selectUserInfoByEmail(DTS_USER);
		// 5. Click �Edit� link
		promotionControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		// 6. Disable the  �Add and manage promotions� privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_promotions.getName());
		promotionControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		promotionControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the �Promotions� page is displayed
		 */
		Assert.assertTrue(promotionControl.isElementPresent(PageHome.linkPromotions));
		// 9. Navigate to �Promotions� page
		promotionControl.click(PageHome.linkPromotions);
		/*
		 * VP: Verify that the promotion list page is displayed without �Add Promo� link
		 */
		Assert.assertEquals(promotionControl.existsElement(PromotionList.getElementInfo()), true);
		Assert.assertFalse(promotionControl.isElementPresent(PromotionList.ADD_PROMO));
		// 10. Select a promotion from promotions list
		String promotionName = promotionControl.selectAPromotion();
		/*
		 * Verify that the promotion info page is displayed without the �Edit�, �Publish� and �Delete� links
		 */
		Assert.assertEquals(promotionControl.getTextByXpath(PromotionInfo.PROMOTION_NAME), promotionName);
		Assert.assertFalse(promotionControl.isElementPresent(PromotionInfo.EDIT));
		Assert.assertFalse(promotionControl.isElementPresent(PromotionInfo.PUBLISH));
		Assert.assertFalse(promotionControl.isElementPresent(PromotionInfo.DELETE));
	}
	
	/*
	 * Verify that the DTS user can add new promotions when the �Add and manage promotions� privilege is enabled
	 */
	@Test
	public void PPRO_03(){
		promotionControl.addLog("ID : PPRO_03 : Verify that the DTS user can add new promotions when the �Add and manage promotions� privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage promotions� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Promotions� page is displayed
			9. Navigate to �Promotions� page
			VP: Verify that the promotion list page is displayed with �Add Promo� link
			10. Click �Add Promo� link
			11. Fill valid value into all required fields
			12. Click �Save� link
		 */
		/*
		 * Create a product for promo
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		promotionControl.click(PageHome.linkAccessories);
		// Click Add product link
		promotionControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		/*
		 * *********************************************************
		 */
		// 3. Navigate to "Users" page
		promotionControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		promotionControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		// 6. Enable the  �Add and manage promotions� privileges
		userControl.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_promotions.getName());
		promotionControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		promotionControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the �Promotions� page is displayed
		 */
		Assert.assertTrue(promotionControl.isElementPresent(PageHome.linkPromotions));
		// 9. Navigate to �Promotions� page
		promotionControl.click(PageHome.linkPromotions);
		/*
		 * VP: Verify that the promotion list page is displayed with �Add Promo� link
		 */
		Assert.assertEquals(promotionControl.existsElement(PromotionList.getElementInfo()), true);
		Assert.assertTrue(promotionControl.isElementPresent(PromotionList.ADD_PROMO));
		// 10. Click �Add Promo� link
		promotionControl.click(PromotionList.ADD_PROMO);
		// 11. Fill valid value into all required fields
		// 12. Click �Save� link
		Hashtable<String,String> dataPromotion = TestData.promotionGlobal(dataProduct.get("brand") + " " + dataProduct.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataPromotion);
		/*
		 * Verify that the new promotion info page is displayed correctly
		 */
		Assert.assertEquals(promotionControl.existsElement(PromotionInfo.getElementInfo()), true);
		// Delete promotion
		promotionControl.doDelete(PromotionInfo.DELETE);
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.deleteAccessorybyName(dataProduct.get("name"));
	}
	
	/*
	 * Verify that the DTS user can edit promotions when the �Add and manage promotions� privilege is enabled
	 */
	@Test
	public void PPRO_04(){
		promotionControl.addLog("ID : PPRO_04 : Verify that the DTS user can edit promotions when the �Add and manage promotions� privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage promotions� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Promotions� page is displayed
			9. Navigate to �Promotions� page
			VP: Verify that the promotion list page is displayed
			10. Select a promotion from promotions list
			VP: Verify that the �Edit� link is displayed in promotion info page
			11. Click �Edit� link
			12. Change some promotion's value
			13. Click �Save� link
		 */
		/*
		 * Create a product for promo
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		promotionControl.click(PageHome.linkAccessories);
		// Click Add product link
		promotionControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		/*
		 * Create new promotion
		 */
		// Navigate to Promotion page
		promotionControl.click(PageHome.linkPromotions);
		// Click Add promo link
		promotionControl.click(PromotionList.ADD_PROMO);
		// Create new promotion
		Hashtable<String,String> dataPromotion = TestData.promotionGlobal(dataProduct.get("brand") + " " + dataProduct.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataPromotion);
		/*
		 * *********************************************************
		 */
		// 3. Navigate to "Users" page
		promotionControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.selectUserInfoByEmail(DTS_USER);
		// 5. Click �Edit� link
		promotionControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		// 6. Disable the �Add and manage promotions� privileges
		userControl.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_promotions.getName());
		promotionControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		promotionControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the �Promotions� page is displayed
		 */
		Assert.assertTrue(promotionControl.isElementPresent(PageHome.linkPromotions));
		// 9. Navigate to �Promotions� page
		promotionControl.click(PageHome.linkPromotions);
		/*
		 * VP: Verify that the promotion list page is displayed
		 */
		Assert.assertEquals(promotionControl.existsElement(PromotionList.getElementInfo()), true);
		// 10. Select a promotion from promotions list
		promotionControl.selectAPromotionByName(PromotionList.PROMOTION_TABLE, dataPromotion.get("name"));
		/*
		 * VP: Verify that the �Edit� link is displayed in promotion info page
		 */
		Assert.assertEquals(promotionControl.getTextByXpath(PromotionInfo.PROMOTION_NAME), dataPromotion.get("name"));
		Assert.assertTrue(promotionControl.isElementPresent(PromotionInfo.EDIT));
		// 11. Click �Edit� link
		promotionControl.click(PromotionInfo.EDIT);
		// 12. Change some promotion's value
		String newSalesforceID = RandomStringUtils.randomNumeric(9);
		promotionControl.editData(AddPromotion.SALESFORCE_ID, newSalesforceID);
		// 13. Click �Save� link
		promotionControl.click(AddPromotion.SAVE_EDITMODE);
		promotionControl.waitForElementClickable(PromotionInfo.DELETE);
		/*
		 * Verify that the promotion info page is displayed with new information correctly
		 */
		Assert.assertEquals(promotionControl.getTextByXpath(PromotionInfo.SALESFORCE_ID), newSalesforceID);
		// Delete promotion
		productControl.deleteAccessorybyName(dataProduct.get("name"));
	}
	
	/*
	 * Verify that the DTS user can publish promotions when the �Add and manage promotions� privilege is enabled
	 */
	@Test
	public void PPRO_05(){
		promotionControl.addLog("ID : PPRO_05 : Verify that the DTS user can publish promotions when the �Add and manage promotions� privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage promotions� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Promotions� page is displayed
			9. Navigate to �Promotions� page
			VP: Verify that the promotion list page is displayed
			10. Click �Add Promo� link
			11. Fill valid value into all required fields
			12. Click �Save� link
			VP: Verify that the new promotion info page is displayed with �Publish� link
			13. Click �Publish� link
		 */
		/*
		 * Create a product for promo
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		promotionControl.click(PageHome.linkAccessories);
		// Click Add product link
		promotionControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		/*
		 * *********************************************************
		 */
		// 3. Navigate to "Users" page
		promotionControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		promotionControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		// 6. Enable the  �Add and manage promotions� privileges
		userControl.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_promotions.getName());
		promotionControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		promotionControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the �Promotions� page is displayed
		 */
		Assert.assertTrue(promotionControl.isElementPresent(PageHome.linkPromotions));
		// 9. Navigate to �Promotions� page
		promotionControl.click(PageHome.linkPromotions);
		/*
		 * VP: Verify that the promotion list page is displayed with �Add Promo� link
		 */
		Assert.assertEquals(promotionControl.existsElement(PromotionList.getElementInfo()), true);
		Assert.assertTrue(promotionControl.isElementPresent(PromotionList.ADD_PROMO));
		// 10. Click �Add Promo� link
		promotionControl.click(PromotionList.ADD_PROMO);
		// 11. Fill valid value into all required fields
		// 12. Click �Save� link
		Hashtable<String,String> dataPromotion = TestData.promotionGlobal(dataProduct.get("brand") + " " + dataProduct.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataPromotion);
		/*
		 * VP: Verify that the new promotion info page is displayed with �Publish� link
		 */
		Assert.assertEquals(promotionControl.existsElement(PromotionInfo.getElementInfo()), true);
		Assert.assertTrue(promotionControl.isElementPresent(PromotionInfo.PUBLISH));
		// 13. Click �Publish� link
		promotionControl.click(PromotionInfo.PUBLISH);
		/*
		 * Verify that the portal redirect to 660 Promos list page and the promotion status is changed to published
		 */
		Assert.assertEquals(promotionControl.getTextByXpath(PromotionInfo.PUBLISH_STATUS), "PUBLISHED");
		// Delete promotion
		promotionControl.doDelete(PromotionInfo.DELETE);
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.deleteAccessorybyName(dataProduct.get("name"));
	}
	
	/*
	 * Verify that the DTS user can delete promotions when the �Add and manage promotions� privilege is enabled
	 */
	@Test
	public void PPRO_06(){
		promotionControl.addLog("ID : PPRO_06 : Verify that the DTS user can delete promotions when the �Add and manage promotions� privilege is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage promotions� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Promotions� page is displayed
			9. Navigate to �Promotions� page
			VP: Verify that the promotion list page is displayed
			10. Select a promotion from promotions list
			VP: Verify that the �Delete� link is displayed in promotion info page
			11. Click �Delete� link
		 */
		/*
		 * Create a product for promo
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to product page
		promotionControl.click(PageHome.linkAccessories);
		// Click Add product link
		promotionControl.click(ProductModel.ADD_PRODUCT);
		// Create product
		Hashtable<String,String> dataProduct = TestData.productData(PARTNER_COMPANY_NAME, PARTNER_BRAND_NAME_1, true, true, true);
		productWf.createAccessoryPublish(AddEditProductModel.getProductInputFieldIdsHash(), dataProduct);
		/*
		 * Create new promotion
		 */
		// Navigate to Promotion page
		promotionControl.click(PageHome.linkPromotions);
		// Click Add promo link
		promotionControl.click(PromotionList.ADD_PROMO);
		// Create new promotion
		Hashtable<String,String> dataPromotion = TestData.promotionGlobal(dataProduct.get("brand") + " " + dataProduct.get("name"));
		promotionControl.addPromotion(AddPromotion.getHash(), dataPromotion);
		/*
		 * *********************************************************
		 */
		// 3. Navigate to "Users" page
		promotionControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.selectUserInfoByEmail(DTS_USER);
		// 5. Click �Edit� link
		promotionControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		// 6. Enable the  �Add and manage promotions� privileges
		userControl.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_promotions.getName());
		promotionControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		promotionControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the �Promotions� page is displayed
		 */
		Assert.assertTrue(promotionControl.isElementPresent(PageHome.linkPromotions));
		// 9. Navigate to �Promotions� page
		promotionControl.click(PageHome.linkPromotions);
		/*
		 * VP: Verify that the promotion list page is displayed
		 */
		Assert.assertEquals(promotionControl.existsElement(PromotionList.getElementInfo()), true);
		// 10. Select a promotion from promotions list
		promotionControl.selectAPromotionByName(PromotionList.PROMOTION_TABLE, dataPromotion.get("name"));
		/*
		 * VP: Verify that the �Delete� link is displayed in promotion info page
		 */
		Assert.assertTrue(promotionControl.isElementPresent(PromotionInfo.DELETE));
		// 11. Click �Delete� link
		promotionControl.doDelete(PromotionInfo.DELETE);
		/*
		 * Verify that the portal redirects to promotion main page and the the deleted promotion is not displayed in promotion list
		 */
		Assert.assertEquals(promotionControl.existsElement(PromotionList.getElementInfo()), true);
		Assert.assertFalse(promotionControl.checkPromotionExistByName(PromotionList.PROMOTION_TABLE, dataPromotion.get("name")));
	
		// Delete product
		productControl.click(PageHome.linkAccessories);
		productControl.deleteAccessorybyName(dataProduct.get("name"));

	}

}
