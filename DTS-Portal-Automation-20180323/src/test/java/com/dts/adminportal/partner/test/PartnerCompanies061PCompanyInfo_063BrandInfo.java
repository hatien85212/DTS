package com.dts.adminportal.partner.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddBrand;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.BrandInfo;
import com.dts.adminportal.model.CompanyInfo;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PartnerCompanyInfo;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.TestData;

public class PartnerCompanies061PCompanyInfo_063BrandInfo extends BasePage{
	
	@Override
	protected void initData() {
	}	

//	@BeforeMethod
//	public void loginBeforeTest() {
//		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//	}
	
	/*
	 * Verify that the partner's company page displays correct information
	 */
	@Test
	public void TC061PCI_01(){
		companyControl.addLog("ID : TC061PCI_01 : Verify that the partner's company page displays correct information");
		/*
		 	Pre-condition: partner user has full site privileges rights.
			1. Navigate to DTS portal
			2. Log into DTS portal as a partner user successfully
			3. Click "Companies" tab
		 */
		
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_PARTNER_COMPANY);
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
		Assert.assertEquals(true, companyControl.existsElement(PartnerCompanyInfo.getListXpath()));
	}
	
//	/*
//	 * Verify that the Brand section displays the brand's image thumbnail and brand name link
//	 */
//	@Test
//	public void TC061PCI_04(){
//		companyControl.addLog("ID : TC061PCI_04 : Verify that the Brand section displays the brand's image thumbnail and brand name link");
//		/*
//			Pre-condition: partner user has "Edit brand info" rights.
//			1. Navigate to DTS portal
//			2. Log into DTS portal as a partner user successfully
//			3. Click "Companies" tab
//		*/
//		/*
//		 * PreCondition: Create new brand
//		 */
//		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
//		// Navigate to companies page
//		companyControl.click(PageHome.LINK_PARTNER_COMPANY);
//		// Click Add brand link
//		companyControl.click(CompanyInfo.ADD_BRAND);
//		// Create new brand
//		Hashtable<String,String> data = TestData.brandFull();
//		companyControl.addBrand(AddBrand.getHash(), data);
//		/*
//		 * ********************************************
//		 */
//		// 3. Click "Companies" tab
//		companyControl.click(PageHome.LINK_PARTNER_COMPANY);
//		/*
//		 * Verify that The Brand section displays the brand's image thumbnail and brand name link
//		 */
//		Assert.assertTrue(companyControl.checkBrandExist(data.get("name")));
//		// Delete brand
//		companyControl.selectABrandByName(data.get("name"));
//		companyControl.doDelete(BrandInfo.DELETE_LINK);
//	}
	
	/*
	 * Verify that the portal is redirected to 061P Company Info page
	 */
	@Test
	public void TC063PBI_01() {
		companyControl.addLog("ID : 063PBI_01 : Verify that the portal is redirected to 061P Company Info page");
		/*
				1. Navigate to DTS portal
				2. Log into DTS portal as a partner user successfully
				3. Click "Companies" tab
				4. Click on name of Brand
				VP: The 063P Brand Info page displays: Company name, Brand name, Brand Tag Line, Consumer Brand Aliases, Website, Brand Overview, Copyright and Trademark Notice, Consumer Brand Logo and Actions module.
				5. Click on Company name link
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_PARTNER_COMPANY);
		//Get company name
		String company_name= companyControl.getTextByXpath(CompanyInfo.CONTACT_NAME);
		// 4. Click on name of Brand
		companyControl.selectABrand(CompanyInfo.BRAND_LIST);
		//VP: The 063P Brand Info page displays: Company name, Brand name, Brand Tag Line, Consumer Brand Aliases, Website, Brand Overview, Copyright and Trademark Notice, Consumer Brand Logo and Actions module.
		Assert.assertEquals(companyControl.existsElement(BrandInfo.getAllField()), true);
		Assert.assertTrue(companyControl.isElementPresent(BrandInfo.ACTION_MODULE));
		// 5. Click on Company name link
		companyControl.click(BrandInfo.COMPANY_NAME);
		//Portal is redirect to 061P Company Info page
		Assert.assertTrue(companyControl.getTextByXpath(CompanyInfo.CONTACT_NAME).contains(company_name));
	
	}


	/*
	 * Verify that the brand info page displays properly.
	 */
	@Test
	public void TC063PBI_02() {
		companyControl.addLog("ID : 063PBI_02 : Verify that the brand info page displays properly.");
		/*
		Pre-Condition: Partner user has "Edit brand info" rights.
		
		1. Navigate to DTS portal
		2. Log into DTS portal as a partner user successfully
		3. Click "Companies" tab
		4. Select brand which have at least 1 logo image from brand list
		VP:Verify the the 063P Brand Info page is displayed
		5. Click "Delete" link
		VP: Verify that the confirmation popup is showed up with "Cancel" and "Delete" button
		6. Click "Cancel" button
		VP: The delete confirmation popup disappears, the portal still stays at 063P Brand Info and the company's brand will not be deleted.
		7. Click on on brand logo
		VP: The lightbox style popup with the correct picture showing in full size is displayed 
		8. Click "Edit" link
		VP: Verify that the 072P Edit Brand Info page is displayed, all brand's information is displayed in correct position.
		9. Click "Cancel" link
		10. Click on "Delete" link
		11. Click on "Delete" buton on delete confirmation dialog
		VP: Brand is deleted successfully
		 */
		loginControl.login(SUPER_PARTNER_USER, SUPER_PARTNER_PASSWORD);
		// 3. Click "Companies" tab
		companyControl.click(PageHome.LINK_PARTNER_COMPANY);
		//Create new brand
		companyControl.click(CompanyInfo.ADD_BRAND);
		Hashtable<String, String> branddata= TestData.brandFull();
		companyControl.addBrand(AddBrand.getHash(), branddata);
		/*
		 * Verify that the 063P Brand Info page is displayed
		 */
		Assert.assertEquals(companyControl.existsElement(BrandInfo.getAllField()), true);
		// 5. Click "Delete" link
		companyControl.click(BrandInfo.DELETE_LINK);
		companyControl.waitForElementClickable(PageHome.BTN_CONFIRMATION_DANGER);
		/*
		 * A confirmation popup with "Cancel" and "Delete" button is displayed.
		 */
		Assert.assertEquals(companyControl.getTextByXpath(PageHome.BTN_CONFIRMATION_DANGER), "Delete");
		Assert.assertEquals(companyControl.getTextByXpath(PageHome.BTN_CONFIRMATION_CANCEL), "Cancel");
		// 6. Click "Cancel" button
		companyControl.selectConfirmationDialogOption("Cancel");
		/*
		 * The delete confirmation popup disappears, the portal still stays at
		 * 063P Brand Info and the company's brand will not be deleted.
		 */
		Assert.assertEquals(companyControl.existsElement(BrandInfo.getAllField()), true);
			
		//7. Click on on brand logo
		//VP: The lightbox style popup with the correct picture showing in full size is displayed 
		// 250x250
		companyControl.click(BrandInfo.BRAND_LOGO_250);
		companyControl.waitForElementClickable(BrandInfo.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(companyControl.isElementPresent(BrandInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(companyControl.getAtributeValue(BrandInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_250_JPG.getName().replaceAll(".jpg", "")));
		companyControl.click(BrandInfo.LIGHTBOX_CLOSE);
		//500x500
		companyControl.click(BrandInfo.BRAND_LOGO_500);
		companyControl.waitForElementClickable(BrandInfo.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(companyControl.isElementPresent(BrandInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(companyControl.getAtributeValue(BrandInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_500_JPG.getName().replaceAll(".jpg", "")));
		companyControl.click(BrandInfo.LIGHTBOX_CLOSE);
		//1000x1000
		companyControl.click(BrandInfo.BRAND_LOGO_1000);
		companyControl.waitForElementClickable(BrandInfo.LIGHTBOX_STYLE_IMAGE);
		Assert.assertTrue(companyControl.isElementPresent(BrandInfo.LIGHTBOX_STYLE_IMAGE));
		Assert.assertTrue(companyControl.getAtributeValue(BrandInfo.LIGHTBOX_STYLE_IMAGE, "src").contains(AddEditProductModel.FileUpload.IMG_1000_JPG.getName().replaceAll(".jpg", "")));
		companyControl.click(BrandInfo.LIGHTBOX_CLOSE);
		// 8. Click "Edit" link
		// 	Get all company' brand info before editing
		ArrayList<String> brandInfo = companyControl.getTextListByXpaths(BrandInfo.getBrandInfo());
		companyControl.click(BrandInfo.EDIT_BRAND);
		// Get all brand info in Edit page
		ArrayList<String> brandEdit = new ArrayList<String>();
		brandEdit.add("Default (English): " + companyControl.getAtributeValue(AddBrand.NAME, "value"));
		brandEdit.add(companyControl.getAtributeValue(AddBrand.BRAND_TAG_LINE, "value"));
		brandEdit.add(companyControl.getAtributeValue(AddBrand.CONSUMER_ALIAS, "value"));
		brandEdit.add(companyControl.getAtributeValue(AddBrand.WEBSITE, "value"));
		brandEdit.add(companyControl.getAtributeValue(AddBrand.BRAND_OVERVIEW, "value"));
		brandEdit.add(companyControl.getAtributeValue(AddBrand.COPYRIGHT_AND_TRADEMARK_NOTICE, "value"));
		/*
		 * Verify that the 072P Edit Brand Info page is displayed, all brand's information is displayed in correct position
		 */
		Assert.assertTrue(ListUtil.containsAll(brandInfo, brandEdit));
		
		//9. Click "Cancel" link
		companyControl.click(AddBrand.CANCEL);
		//10. Click on "Delete" link
		companyControl.click(BrandInfo.DELETE_LINK);
		// 11. Click on "Delete" buton on delete confirmation dialog
		companyControl.selectConfirmationDialogOption("Delete");
		//VP: Portal redirects to Company Info page and Brand is deleted successfully
		Assert.assertTrue(companyControl.isElementPresent(CompanyInfo.NAME));
		Assert.assertFalse(companyControl.checkBrandExist(branddata.get("name")));
		
	}
}
