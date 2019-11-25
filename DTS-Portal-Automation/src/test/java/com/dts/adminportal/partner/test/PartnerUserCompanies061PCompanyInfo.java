package com.dts.adminportal.partner.test;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AddBrand;
import dts.com.adminportal.model.BrandInfo;
import dts.com.adminportal.model.CompanyInfo;
import dts.com.adminportal.model.PartnerCompanyInfo;
import dts.com.adminportal.model.Xpath;

public class PartnerUserCompanies061PCompanyInfo extends CreatePage{
	private HomeController home;
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}
	@BeforeMethod
	public void loginBeforeTest() {
		home.login(superpartneruser, superpartnerpassword);
	}
	
	/*
	 * Verify that the partner's company page displays correct information
	 */
	@Test
	public void TC061PCI_01(){
		result.addLog("ID : TC061PCI_01 : Verify that the partner's company page displays correct information");
		/*
		 	Pre-condition: partner user has full site privileges rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
		 */
		
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		/*
		 * Verify that the partner's company info page is displayed correctly which including: 
		 * company name, 
		 * "Company Info", 
		 * "Official Corporate Name", 
		 * "Corporate Address", 
		 * "Primary Partner Contact", 
		 * "Email", 
		 * "Phone", 
		 * "Brands", 
		 * brand logo, 
		 * brand name and 
		 * "Model Allocation Info" 
		 * overview module.
		 */
		result = home.existsElement(PartnerCompanyInfo.getListXpath());
		Assert.assertEquals("Pass", result.getResult());
	}
	
	/*
	 * Verify that the Brand section displays the brand's image thumbnail and brand name link
	 */
	@Test
	public void TC061PCI_04(){
		result.addLog("ID : TC061PCI_04 : Verify that the Brand section displays the brand's image thumbnail and brand name link");
		/*
			Pre-condition: partner user has "Edit brand info" rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
		*/
		/*
		 * PreCondition: Create new brand
		 */
		// Navigate to companies page
		home.click(Xpath.LINK_PARTNER_COMPANY);
		// Click Add brand link
		home.click(CompanyInfo.ADD_BRAND);
		// Create new brand
		Hashtable<String,String> data = TestData.brandFull();
		home.addBrand(AddBrand.getHash(), data);
		/*
		 * ********************************************
		 */
		// 3. Click "Companies" tab
		home.click(Xpath.LINK_PARTNER_COMPANY);
		/*
		 * Verify that The Brand section displays the brand's image thumbnail and brand name link
		 */
		Assert.assertTrue(home.checkBrandExist(data.get("name")));
		// Delete brand
		home.selectABrandByName(data.get("name"));
		home.doDelete(BrandInfo.DELETE_LINK);
	}
}
