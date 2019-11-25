package com.dts.adminportal.model;

import java.util.ArrayList;

public class PageHome {
	public static final String LOADING = "/html/body";
	public static final String logoImg = "/html/body/div[2]/div/div[1]/div/div[1]/a/img"; 
	public static final String logoImgHome = "html/body/div[1]/div[1]/a/img";
	public static final ArrayList<String> getListElementLogoDTS() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(logoImgHome);
		return arrayList;
	}

	// logout
	public static final String lbLogout = "//*[@id='signinId']";
	public static final String btnLogout = "//*[@id='loggedInAs']";
	public static final String submitLogout = "//*[@id='logout']";
	public static final String loginAccount = "//*[@id='userAccount']";
	public static final String dropdownItemLogout = "/html/body/div[2]/div/div[1]/div/div[2]/div/ul/li/ul"; ///html/body/div[1]/div/div[1]/div/div[2]/div/ul[2]/li/ul
	// main											
	public static final String linkHome = "//*[@id='nav_link_home_dts']/a";
	public static final String linkAccessories = ".//div[@style='']/li[contains(@id,'nav_link_accessories')]/a";
	public static final String linkDevice = "//*[@id='nav_link_devices_dts']/a";
	public static final String linkPromotions = "//*[@id='nav_link_promotions_dts']/a";
	public static final String linkAudioroutes = "//*[@id='nav_link_audioroutes_dts']/a";
	public static final String LINK_USERS = "//div[@style='']/li[contains(@id,'nav_link_users')]/a";
	public static final String LINK_COMPANY = "//*[@id='nav_link_company_dts']/a";
	public static final ArrayList<String> getListElementMenu() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(linkHome);
		arrayList.add(linkAccessories);
		arrayList.add(linkDevice);
		arrayList.add(linkPromotions);
		arrayList.add(linkAudioroutes);
		arrayList.add(LINK_USERS);
		arrayList.add(LINK_COMPANY);
		return arrayList;
	}
	// info
	public static final String COPY_RIGHT_INFORMATION = "//*[@id='main-container']/footer/p";
	// link Home
	public static final String onlineSupportRequest = "//*[@id='main-container']/footer/p/a";
	public static final String dropDownListViewAs = "/html/body/div[2]/div/div[1]/div/div[2]/div/div[2]/div/button";
	public static final String firstItemViewAs = "/html/body/div[1]/div/div[1]/div/div[2]/div/ul[1]/div/button/span[1]";
	public static final String dropdownMenuViewAs = "/html/body/div[1]/div/div[1]/div/div[2]/div/ul[1]/div/div/ul";
	public static final String dropdownMenuInnerSelectpicker = "/html/body/div[2]/div/div[1]/div/div[2]/div/div[2]/div/div/ul";
	public static final String selectPanasonic = "/html/body/div[1]/div/div[1]/div/div[2]/div/ul[1]/div/div/ul/li[6]/a/span";
	// Home heading tab
	public static final String headingCatalog = "//*[@id='home-row-1']/div[1]/div[1]/strong";
	public static final String headingTuningStatus = "//*[@id='home-row-1']/div[2]/div[1]/strong";
	public static final String headingCompanies = "//*[@id='home-row-2']/div[1]/div[1]/strong";
	public static final String headingMaketingApprovalStatus = "//*[@id='home-row-2']/div[2]/div[1]/strong";
	public static final String headingModelAllocationStatus = "//*[@id='home-row-2']/div[3]/div[1]/strong";
	// Home/Catalog
	public static final String catalogAddAccessory = "//*[@id='catalog-add-accessory']";
	public static final String catalogRecentlyAdded = "//*[@id='catalog-recently-added']";
	public static final String catalogDraft = "//*[@id='catalog-draft']";
	public static final String catalogReadyPublish = "//*[@id='catalog-ready-publish']";
	public static final String catalogOverdue = "//*[@id='catalog-overdue']";
	public static final String catalogRecentlyPublished = "//*[@id='catalog-recently-published']";
	public static final String catalogNeedAttention = "//*[@id='catalog-need-attention']";
	public static final String catalogSuspended = "//*[@id='catalog-suspended']";
	// Span Home Catalog
	public static final String headingCatalogHome = "//*[@id='home-row-1']/div[1]";
	public static final String headingTuningStatusHome = "//*[@id='home-row-1']/div[2]";
	public static final String headingCompaniesHome = "//*[@id='home-row-2']/div[1]";
	public static final String headingMaketingApprovalStatusHome = "//*[@id='home-row-2']/div[2]";
	public static final String headingModelAllocationStatusHome = "//*[@id='home-row-2']/div[3]";
	// Home/Tuning Status
	public static final String CATALOG_DTS_REQUEST_PENDING = "//*[@id='catalog-tuning-request']";
	public static final String CATALOG_TUNING_REQUEST_OVERDUE = "//*[@id='catalog-tuning-request-overdue']";
	public static final String catalogTuningPendingPartReview = "//*[@id='catalog-tuning-pending-part-review']";
	public static final String CATALOG_TUNING_PENDING_PART_REVIEW_OVERDUE = "//*[@id='catalog-tuning-pending-part-review-overdue']";
	public static final String catalogTuningPendingDtsReview = "//*[@id='catalog-tuning-pending-dts-review']";
	public static final String CATALOG_TUNING_PENDING_DTS_REVIEW_OVERDUE = "//*[@id='catalog-tuning-pending-dts-review-overdue']";
	public static final String catalogTuningHeadphoneLevelAPlus = "//*[@id='catalog-tuning-headphone-level-a-plus']";
	public static final String catalogTuningHeadphoneLevelA = "//*[@id='catalog-tuning-headphone-level-a']";
	public static final String catalogTuningDeclined = "//*[@id='catalog-tuning-declined']";
	public static final String catalogTuningRevoked = "//*[@id='catalog-tuning-revoked']";
	// Home/Companies
	public static final String homeCompaniesView = "//*[@id='home-companies-view']";
	public static final String homeCompanyRecentlyAdded = "//*[@id='home-company-recently-added']";
	public static final String homeCompanyPendingInvite = "//*[@id='home-company-pending-invite']";
	public static final String homeCompanyWatchList = "//*[@id='home-company-watch-list']";
	public static final String homeCompanyContractEnding = "//*[@id='home-company-contract-ending']";
	public static final String homeCompanyInactive = "//*[@id='home-company-inactive']";
	public static final String homeCompanySuspended = "//*[@id='home-company-suspended']";
//	public static final String homeCompanyGhosts = "//*[@id='home-company-ghosts']";
	// Home/Marketing Approval Status
	public static final String HOME_MARKETING_UNSUBMITTED = "//*[@id='home-marketing-unsubmitted']";
	public static final String MARKETING_DTS_PENDING = "//*[@id='home-marketing-dts-pending']";
	public static final String MARKETING_DTS_PENDING_OVERDUE = "//*[@id='home-marketing-dts-pending-overdue']";
	public static final String MARKETING_DECLINED = "//*[@id='home-marketing-declined']";
	public static final String MARKETING_REVOKED = "//*[@id='home-marketing-revoked']";
	// Home/Model Allocation Status
	public static final String modelAllocRequest = "//*[@id='model-alloc-request']";
	public static final String modelAllocOutOfUnits = "//*[@id='model-alloc-out-of-units']";
	public static final String modelAllocLowUnits = "//*[@id='model-alloc-low-units']";
	// Accessories
	public static final String accessoryFilterSelect = "//*[@id='accessoryFilterSelect']";
//	public static final String categoryList = "//*[@id='category-list']";
	public static final String createNewAccessory = "//*[@id='create-new-accessory']";
	public static final String BRAND_ACCESSORIES_TABLE_FIRST = "//*[@id='BrandAccessoriesTable_first']";
	public static final String BRAND_ACCESSORIES_TABLE_PREVIOUS = "//*[@id='BrandAccessoriesTable_previous']";
	public static final String BRAND_ACCESSORIES_TABLE_NEXT = "//*[@id='BrandAccessoriesTable_next']";
	public static final String BRAND_ACCESSORIES_TABLE_LAST = "//*[@id='BrandAccessoriesTable_last']";
	public static final String TUNING_STATUS_APPROVAL = "//*[@id='tuning-title']/span";

	public static final ArrayList<String> getListAccessories() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(accessoryFilterSelect);
//		arrayList.add(categoryList);
		arrayList.add(createNewAccessory);
		arrayList.add(BRAND_ACCESSORIES_TABLE_FIRST);
		arrayList.add(BRAND_ACCESSORIES_TABLE_PREVIOUS);
		arrayList.add(BRAND_ACCESSORIES_TABLE_NEXT);
		arrayList.add(BRAND_ACCESSORIES_TABLE_LAST);
		return arrayList;
	}

	public static ArrayList<String> getListAccessoriesPartner() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(accessoryFilterSelect);
		arrayList.add(createNewAccessory);
		arrayList.add(BRAND_ACCESSORIES_TABLE_FIRST);
		arrayList.add(BRAND_ACCESSORIES_TABLE_PREVIOUS);
		arrayList.add(BRAND_ACCESSORIES_TABLE_NEXT);
		arrayList.add(BRAND_ACCESSORIES_TABLE_LAST);
		return arrayList;
	}

	// User
	public static final String userFilterSelect = "//*[@id='userFilterSelect']";
	public static final String userCompanyList = "//*[@id='company-list']";
	public static final String createNewUser = "//*[@id='create-new-user']";
	public static final String userCreateSucess="//*[@id='user-panel']/div/h5";
	// Company/link
	public static final String companyFilterSelect = "//*[@id='companyFilterSelect']";
	public static final String createNewCompany = "//*[@id='create-new-company']";
	public static final String companyListTableFirst = "//*[@id='CompanyListTable_first']";
	public static final String companyListTablePrevious = "//*[@id='CompanyListTable_previous']";
	public static final String companyListTableNext = "//*[@id='CompanyListTable_next']";
	public static final String companyListTableLast = "//*[@id='CompanyListTable_last']";

	public static final ArrayList<String> listCompanyLinkElement() {
		ArrayList<String> arrayList = new ArrayList<String>();
		// link
		arrayList.add(companyFilterSelect);
		arrayList.add(createNewCompany);
		arrayList.add(companyListTableFirst);
		arrayList.add(companyListTablePrevious);
		arrayList.add(companyListTableNext);
		arrayList.add(companyListTableLast);
		return arrayList;
	}

	// add-accessory
	public static final String lbAddaccessory = "//*[@id='add-accessory-form']/fieldset/span";
	public static final String displayName = "//*[@id='display-name']";
	public static final String displayNumber = "//*[@id='display-number']";
	public static final String upc = "//*[@id='upc']";
	public static final String dropDowModelType = "//*[@id='modelType']";
	// add-accessoty/connection-Type
	public static final String checkboxWired = "//*[@id='checkboxWired']";
	public static final String checkboxWiredUSBData = "//*[@id='checkboxWiredUSBData']";
	public static final String checkboxBluetooth = "//*[@id='checkboxBluetooth']";
	public static final String checkboxPlayFI = "//*[@id='checkboxPlay-FI']";
	public static final String stereoCapabilitySelection1 = "//*[@id='stereoCapabilitySelection1']";
	public static final String stereoCapabilitySelection2 = "//*[@id='stereoCapabilitySelection2']";
//	public static final String dropDowAccessoryCategory = "//*[@id='accessoryCategory']";
	public static final String releaseDate = "//*[@id='releaseDate']";
	public static final String salesStatusSelection1 = "//*[@id='salesStatusSelection1']";
	public static final String salesStatusSelection2 = "//*[@id='salesStatusSelection2']";
	public static final String aliases = "//*[@id='aliases']";
	public static final String description = "//*[@id='description']";
	public static final String addTuningModelBtn = "//*[@id='add-tuning-model-btn']";
	public static final String headphoneTuningRatingUnd = "//*[@id='headphone-tuning-rating']/label[1]/input";
	public static final String headphoneTuningRatingNoC = "//*[@id='headphone-tuning-rating']/label[2]/input";
	public static final String headphoneTuningRatingA = "//*[@id='headphone-tuning-rating']/label[3]/input";
	public static final String headphoneTuningRatingAPlus = "//*[@id='headphone-tuning-rating']/label[4]/input";
	public static final String headphoneTuningRatingNotApp = "//*[@id='headphone-tuning-rating']/label[1]/input";
	public static final String headphoneTuningRatingTrue = "//*[@id='headphone-tuning-rating']/label[2]/input";
	public static final String logo124Image = "//*[@id='logo124Image']";
	public static final String addMarketingMaterialBtn = "//*[@id='add-marketing-material-btn']";
	// ... Actions 1
	public static final String saveAccessory = "//*[@id='save-accessory']";
	public static final String cancelAccessory = "//*[@id='cancel-accessory']";
	public static final String EDIT_SAVE_ACCESSORY = "//*[@id='edit-save-account']";
	// ... Action 2
	public static final String imageGuideline = "//*[@id='image-guideline']";
	public static final String tuningGuideline = "//*[@id='tuning-guideline']";
	public static final String downloadTuningTool = "//*[@id='download-tuning-tool']";
	public static final String marketingGuideline = "//*[@id='marketing-guideline']";
	public static final String relatedFaq = "//*[@id='related-faq']";

	public static final ArrayList<String> getListAddAccessoryElement() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(lbAddaccessory);
		arrayList.add(displayName);
		arrayList.add(displayNumber);
		arrayList.add(upc);
		arrayList.add(dropDowModelType);
		arrayList.add(checkboxWired);
		arrayList.add(checkboxWiredUSBData);
		arrayList.add(checkboxBluetooth);
		arrayList.add(checkboxPlayFI);
		arrayList.add(stereoCapabilitySelection1);
		arrayList.add(stereoCapabilitySelection2);
//		arrayList.add(dropDowAccessoryCategory);
		arrayList.add(releaseDate);
		arrayList.add(salesStatusSelection1);
		arrayList.add(salesStatusSelection2);
		arrayList.add(aliases);
		arrayList.add(description);
		arrayList.add(addTuningModelBtn);
		arrayList.add(headphoneTuningRatingUnd);
		arrayList.add(headphoneTuningRatingNoC);
		arrayList.add(headphoneTuningRatingA);
		arrayList.add(headphoneTuningRatingAPlus);
		arrayList.add(headphoneTuningRatingNotApp);
		arrayList.add(headphoneTuningRatingTrue);
		arrayList.add(logo124Image);
		arrayList.add(addMarketingMaterialBtn);
		// Action 1
		arrayList.add(saveAccessory);
		arrayList.add(cancelAccessory);
		// Action 2
		arrayList.add(imageGuideline);
		arrayList.add(tuningGuideline);
		arrayList.add(downloadTuningTool);
		arrayList.add(marketingGuideline);
		arrayList.add(relatedFaq);
		return arrayList;
	}

	// ArrayList
	public static final ArrayList<String> getAllLinkHome() {
		ArrayList<String> arrayList = new ArrayList<String>();
		// link
		arrayList.add(linkHome);
		arrayList.add(linkAccessories);
		arrayList.add(linkAudioroutes);
		arrayList.add(LINK_COMPANY);
		arrayList.add(linkDevice);
		arrayList.add(linkPromotions);
		arrayList.add(LINK_USERS);
		return arrayList;
	}

	public static final ArrayList<String> getListCatalogHome() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(catalogAddAccessory);
		arrayList.add(catalogRecentlyAdded);
		arrayList.add(catalogDraft);
		arrayList.add(catalogReadyPublish);
		arrayList.add(catalogOverdue);
		arrayList.add(catalogRecentlyPublished);
		arrayList.add(catalogNeedAttention);
		arrayList.add(catalogSuspended);
		return arrayList;
	}

	public static final ArrayList<String> getListTuningStatusHome() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(CATALOG_DTS_REQUEST_PENDING);
		arrayList.add(CATALOG_TUNING_REQUEST_OVERDUE);
		arrayList.add(catalogTuningPendingPartReview);
		arrayList.add(CATALOG_TUNING_PENDING_PART_REVIEW_OVERDUE);
		arrayList.add(catalogTuningPendingDtsReview);
		arrayList.add(CATALOG_TUNING_PENDING_DTS_REVIEW_OVERDUE);
		arrayList.add(catalogTuningHeadphoneLevelAPlus);
		arrayList.add(catalogTuningHeadphoneLevelA);
		arrayList.add(catalogTuningDeclined);
		arrayList.add(catalogTuningRevoked);
		return arrayList;
	}

	public static final ArrayList<String> getListCompanies() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(homeCompanyRecentlyAdded);
		arrayList.add(homeCompanyPendingInvite);
		arrayList.add(homeCompanyWatchList);
		arrayList.add(homeCompanyContractEnding);
		arrayList.add(homeCompanyInactive);
		arrayList.add(homeCompanySuspended);
//		arrayList.add(homeCompanyGhosts);
		return arrayList;
	}

	public static final ArrayList<String> getListMarketingApprovalStatus() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(HOME_MARKETING_UNSUBMITTED);
		arrayList.add(MARKETING_DTS_PENDING);
		arrayList.add(MARKETING_DTS_PENDING_OVERDUE);
		arrayList.add(MARKETING_DECLINED);
		arrayList.add(MARKETING_REVOKED);
		return arrayList;
	}

	public static final ArrayList<String> getListModelAllocationStatus() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(modelAllocRequest);
		arrayList.add(modelAllocOutOfUnits);
		arrayList.add(modelAllocLowUnits);
		return arrayList;
	}

	public static final ArrayList<String> getAllElementsHome() {
		ArrayList<String> arrayLists = new ArrayList<String>();
		// add to all
		arrayLists.addAll(getListCatalogHome());
		arrayLists.addAll(getListTuningStatusHome());
		arrayLists.addAll(getListCompanies());
		arrayLists.addAll(getListMarketingApprovalStatus());
		arrayLists.addAll(getListModelAllocationStatus());
		arrayLists.addAll(getAllLinkHome());
		arrayLists.add(logoImgHome);
		arrayLists.add(onlineSupportRequest);
		arrayLists.add(btnLogout);
		return arrayLists;
	}
	// Get all links home partner
	public static final String LINK_PARTNER_HOME = ".//*[@id='nav_link_home_partner']/a";
	public static final String LINK_PARTNER_ACCESSORIES = ".//div[@style='']/li[contains(@id,'nav_link_accessories')]/a";
	public static final String LINK_PARTNER_USER = "//div[@style='']/li[contains(@id,'nav_link_users')]/a";
	public static final String LINK_PARTNER_COMPANY = ".//*[@id='nav_link_company_partner']/a";
	public static final ArrayList<String> getAllLinkHomePartner() {
		ArrayList<String> arrayList = new ArrayList<String>();
		// link
		arrayList.add(LINK_PARTNER_HOME);
		arrayList.add(LINK_PARTNER_ACCESSORIES);
		arrayList.add(LINK_PARTNER_USER);
		arrayList.add(LINK_PARTNER_COMPANY);
		return arrayList;
	}
	// Home/Catalog/Partner
	public static final String headingCatalogPartnerHome = "//*[@id='home-catalog-view']";
	public static final String PARTNER_CATALOG_ADD_ACCESSORY = "//*[@id='partner-catalog-add-accessory']";
	public static final String PARTNER_CATALOG_DRAFT = "//*[@id='partner-catalog-draft']";
	public static final String PARTNER_CATALOG_READY_PUBLISH = "//*[@id='catalog-ready-publish']";
	public static final String PARTNER_CATALOG_NEED_ATTENTION = "//*[@id='partner-catalog-need-attention']";
	public static final String PARTNER_CATALOG_SUSPENDED = "//*[@id='partner-catalog-suspended']";
	public static final ArrayList<String> getListCatalogPartner() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(PARTNER_CATALOG_ADD_ACCESSORY);
		arrayList.add(PARTNER_CATALOG_DRAFT);
		arrayList.add(PARTNER_CATALOG_READY_PUBLISH);
		arrayList.add(PARTNER_CATALOG_NEED_ATTENTION);
		arrayList.add(PARTNER_CATALOG_SUSPENDED);
		return arrayList;
	}
	// Partner Tuning Status
	public static final String headingTuningStatusPartner = "//*[@id='home-tuning-view']";
	public static final String PARTNER_CATALOG_TUNING_REQUEST = "//*[@id='partner-catalog-tuning-request']";
	public static final String PARTNER_TUNING_REVIEW = "//*[@id='partner-partner-tuning-review']";
	public static final String PARTNER_DTS_TUNING_REVIEW = "//*[@id='partner-dts-tuning-review']";
	public static final String PARTNER_TUNING_REVOKE = "//*[@id='partner-catalog-tuning-revoked']";
	public static final ArrayList<String> getListTuningPartner() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(PARTNER_CATALOG_TUNING_REQUEST);
		arrayList.add(PARTNER_TUNING_REVIEW);
		arrayList.add(PARTNER_DTS_TUNING_REVIEW);
		arrayList.add(PARTNER_TUNING_REVOKE);
		return arrayList;
	}
	// Home/Model Allocation Status/Partner
	public static final String headingModelAllocationPartner = "//*[@id='home-model-alloc-view']";
	public static final String PARTNER_MODEL_ALLOC_CONTACT = "//*[@id='model-alloc-contact']";
	public static final ArrayList<String> getListModelAllocationPartner() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(PARTNER_MODEL_ALLOC_CONTACT);
		return arrayList;
	}
	// Home/Marketing Approvel Status/Partner
	public static final String headingMarketingApprovalStatusPartner = "//*[@id='home-marketing-view']";
//	public static final String PARTNER_MARKETING_UNSUBMITTED = "//*[@id='partner-home-marketing-unsubmitted']";
//	public static final String PARTNER_MARKETING_DTS_PENDING = "//*[@id='home-marketing-dts-pending']";
	public static final String READY_FOR_MARKETING_REVIEW = "//*[@id='partner-home-marketing-ready-for-review']";
	public static final String DTS_MARKETING_REVIEW = "//*[@id='partner-home-marketing-dts-pending']";
	public static final String PARTNER_MARKETING_REVOKED = "//*[@id='partner-home-marketing-revoked']";
	public static final ArrayList<String> getListMarketingApprovelPartner() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(READY_FOR_MARKETING_REVIEW);
		arrayList.add(DTS_MARKETING_REVIEW);
		arrayList.add(PARTNER_MARKETING_REVOKED);
		return arrayList;
	}
	// GET ALL ELEMENT AT HOME PARTNER
	public static final ArrayList<String> getAllElementsPartner() {
		ArrayList<String> arrayLists = new ArrayList<String>();
		// add to all
		arrayLists.addAll(getListCatalogPartner());
		arrayLists.addAll(getListTuningPartner());
		arrayLists.addAll(getListModelAllocationPartner());
		arrayLists.addAll(getListMarketingApprovelPartner());
		arrayLists.addAll(getAllLinkHomePartner());
		arrayLists.add(logoImgHome);
		arrayLists.add(onlineSupportRequest);
		arrayLists.add(btnLogout);
		return arrayLists;
	}
	// Brand Accessories Table
	public static final String BRAND_ACCESSORIES_TABLE = "//*[@id='BrandAccessoriesTable']/tbody";
	public static final String BRAND_ACCESSORIES = "//*[@id='BrandAccessoriesTable']";
	public static final String BRAND_ACCESSORIES_FIRST = "//*[@id='BrandAccessoriesTable']/tbody/tr[1]/td[2]/a";
	public static final String BRAND_ACCESSORIES_ROW = "//*[@id='BrandAccessoriesTable']/tbody/tr[1]";
	// Company List Table
	public static final String compayListTable = "//*[@id='CompanyListTable']";
	public static final String GLOBAL_ALERT = "//*[@id='global-alert']/span/text()";
	// Page 
	public static final String BRAND_ACCESSORIES_TABLE_PAGINATE = "//*[@id='BrandAccessoriesTable_paginate']/span";
	public static final String PAGINATE = "//*[@id='BrandAccessoriesTable_paginate']";
	// Accessories details page
	public static final String DISPLAY_MODEL = "//*[@id='display-model']";
	public static final String VARIENT_COUNT = "//*[@id='varient-count']";
	public static final String PUBLISHING_TITLE = "//*[@id='publishing-title']/span";
	public static final String PUBLICATION_STATUS = "//*[@id='publication-status']";
	public static final String TUNING_TITLE = "//*[@id='tuning-title']/span";
	public static final String HEADPHONE_TUNING_RATING = "//*[@id='headphone-tuning-rating']";
	public static final String MARKETING_TITLE = "//*[@id='marketing-title']/span";
	public static final ArrayList<String> getListAccessoriesDetail() {
		ArrayList<String> arrayLists = new ArrayList<String>();
		arrayLists.add(DISPLAY_MODEL);
		arrayLists.add(VARIENT_COUNT);
		arrayLists.add(PUBLISHING_TITLE);
		arrayLists.add(PUBLICATION_STATUS);
		arrayLists.add(TUNING_TITLE);
		arrayLists.add(HEADPHONE_TUNING_RATING);
		arrayLists.add(MARKETING_TITLE);
		return arrayLists;
	}
	// Variant Model
	public static final String MODEL_VARIANTS = "//*[@id='model-variants']";
	public static final String VARIANT_DESCRIPTOR = "//*[@id='variant-descriptor']"; 
	// Variant UI
	public static final String SHOW_ACCESSORY_FORM = "//*[@id='show-accessory-form']/fieldset/ul";
	public static final String MODEL_VARIATION = "//*[@id='show-accessory-form']/fieldset/div[1]";
	public static final String DTS_TRACKING_ID = "//*[@id='show-accessory-form']/fieldset/div[2]";
	public static final String SALESFORCE_ID = "//*[@id='show-accessory-form']/fieldset/div[3]";
	public static final String PUBLICATIONSTATUS = "//*[@id='show-accessory-form']/fieldset/div[4]";
	public static final String EAN_UPC = "//*[@id='show-accessory-form']/fieldset/div[5]";
	public static final String DISPLAY_NAME = "//*[@id='show-accessory-form']/fieldset/div[6]";
	public static final String DESCRIPTOR = "//*[@id='show-accessory-form']/fieldset/div[7]";
	public static final String CONNECTION_TYPE = "//*[@id='show-accessory-form']/fieldset/div[8]";
	public static final String RELEASE_DATE = "//*[@id='show-accessory-form']/fieldset/div[9]";
	public static final String TUNING = "//*[@id='show-accessory-form']/fieldset/div[10]";
	public static final String HEADPHONEX= "//*[@id='show-accessory-form']/fieldset/div[11]";
	public static final String IMG_FILE_VARIANT = "//*[@id='show-accessory-form']/fieldset/div[12]";
	public static final String PRODUCT_IMAGES = "//*[@id='show-accessory-form']/fieldset/div[13]";
//	public static final String = "";
	
	// On Variant UI
	public static final String DELETEVARIANT = "//*[@id='deleteVariant']";
	public static final String EDIT_VARIANT = "//*[@id='edit-variant']";
	// On Edit Variant
	public static final String EDIT_DESCRIPTOR = "//*[@id='descriptor']";
	
	// List element accessories detail
	public static final String PICTURE_ACCESSORIES_DETAIL = "//*[@id='product-uploaded-image']";
	public static final String MODEL_ACCESSORIES_DETAIL = "//*[@id='model-name']/span[1]";
	public static final String VARIANT_ACCESSORIES_DETAIL = "//*[@id='varient-count']";
	public static final String PUBLISHED_ACCESSORIES_DETAIL = "//*[@id='publishing-title']/span";
	public static final String VERSION_ACCESSORIES_DETAIL = "//*[@id='publication-status']";
	public static final String TUNING_STATUS_ACCESSORIES_DETAIL = "//*[@id='tuning-title']/span";
	public static final String HEADING_X_RATING_ACCESSORIES_DETAIL = "//*[@id='headphone-tuning-rating']";
	public static final String MARKETING_STATUS_ACCESSORIES_DETAIL = "//*[@id='marketing-title']/span";
	public static final ArrayList<String> getListElementAccessoriesDetailPage() {
		ArrayList<String> arrayLists = new ArrayList<String>();
		arrayLists.add(DELETEVARIANT);
		arrayLists.add(EDIT_VARIANT);
		arrayLists.addAll(getListPublishedProcessUI());
		arrayLists.addAll(getListModelVariationActions());
		return arrayLists;
	}
	public static final String MARKETING_MATERIALS = "//*[@id='marketing-materials']";
	public static final String EANUPC_ACCESSORIES_DETAIL = "//*[@id='variant-eanupc-number']";
	public static final ArrayList<String> getListAccessoriesOnDetailPage() {
		ArrayList<String> arrayLists = new ArrayList<String>();
		arrayLists.add(PICTURE_ACCESSORIES_DETAIL);
		arrayLists.add(MODEL_ACCESSORIES_DETAIL);
		arrayLists.add(VARIANT_ACCESSORIES_DETAIL);
		arrayLists.add(PUBLISHED_ACCESSORIES_DETAIL);
		arrayLists.add(VERSION_ACCESSORIES_DETAIL);
		arrayLists.add(TUNING_STATUS_ACCESSORIES_DETAIL);
		arrayLists.add(HEADING_X_RATING_ACCESSORIES_DETAIL);
		arrayLists.add(MARKETING_STATUS_ACCESSORIES_DETAIL);
		return arrayLists;
	}
	
	// Published Process UI
	public static final String TUNING_PROCESS = "//*[@id='create-new-company-action-header']/strong";
	public static final String TUNING_APPROVAL = "//*[@id='tuning-title']/span";
	public static final String MARKETING_APPROVAL = "//*[@id='marketing-title']/span";
	public static final String PUBLISHING_BUTTON = "//*[@id='publishModel']";
	public static final String DELETE_VARIANT = "//*[@id='deleteVariant']";
	
	public static final ArrayList<String> getListPublishedProcessUI() {
		ArrayList<String> arrayLists = new ArrayList<String>();
		//arrayLists.add(TUNING_PROCESS);
		arrayLists.add(TUNING_APPROVAL);
		arrayLists.add(MARKETING_APPROVAL);
		arrayLists.add(PUBLISHING_BUTTON);
		arrayLists.add(DELETE_VARIANT);
		return arrayLists;
	}

	// MODEL VARIATION ACTIONS
	public static final String MODEL_VARIATION_ACTIONS = "//*[@id='create-new-company-action-header']/strong";
	public static final String DOWNLOAD_PRODUCT_IMAGES = "//*[@id='downloadProductImages']";
	public static final String PREVIEW_DTS_CO_BRANDING_SITE = "//*[@id='previewDtsCoBrandingSite']";
	public static final String SEND_SAMPLE_MODEL_TO_DTS = "//*[@id='sendSampleModelToDts']";
	public static final String VIEW_FEATURED_ACCESSORY_PROMO = "//*[@id='viewFeaturedAccessoryPromo']";
	
	public static final ArrayList<String> getListModelVariationActions() {
		ArrayList<String> arrayLists = new ArrayList<String>();
		arrayLists.add(MODEL_VARIATION_ACTIONS);
		arrayLists.add(DOWNLOAD_PRODUCT_IMAGES);
		arrayLists.add(PREVIEW_DTS_CO_BRANDING_SITE);
		arrayLists.add(SEND_SAMPLE_MODEL_TO_DTS);
		arrayLists.add(VIEW_FEATURED_ACCESSORY_PROMO);
		return arrayLists;
	}
	// On detail accessories page
	public static final String EDIT_MODE = "//*[@id='edit-model']";
	public static final String EANUPC_NUMBER = "//*[@id='eanupc-number']";
	
	public static final String TUNING_TITTLE_ACCESSORIES_DETAIL_PAGE= "//*[@id='tuning-title']/span";
	public static final String MARKETING_TITTLE_ACCESSORIES_DETAIL_PAGE= "//*[@id='marketing-title']/span";
	public static final String BNT_PUBLISH_MODE = "//*[@id='publishModel']";
	public static final String IMAGE_FILE = "//*[@id='show-accessory-form']/fieldset/div[19]";
	
	//On New User Created Success Message page
	public static final String SUCCESS_USER_EMAIL = "//*[@id='user-email']/a/span";
	public static final String SUCCESS_USER_PANEL = "//*[@id='user-panel']/div/p/span[1]/span";
	public static final String SUCCESS_USER_PHONE = "//*[@id='user-phone']/span";
	
	//*[@id="downloadProductImages"]
	// On edit accessories page
	public static final String MODEL_TITLE_EDIT_MODE = "//*[@id='model-title']";
	public static final String DTS_TRACKING_ID_EDIT_MODE = "//*[@id='add-accessory-form']/fieldset/div[2]/label";
	public static final String DTS_TRACKING_ID_VALUE_TITLE_EDIT_MODE = "//*[@id='dts-tracking-id']";
	
	public static final String COMPANY_TITLE_EDIT_MODE = "//*[@id='add-accessory-form']/fieldset/div[3]/label";
	public static final String COMPANY_TITLE_VALUE_TITLE_EDIT_MODE = "//*[@id='companyList']";
	
	public static final String BRAND_TITLE_EDIT_MODE = "//*[@id='add-accessory-form']/fieldset/div[4]/label";
	public static final String BRAND_LIST_VALUE_TITLE_EDIT_MODE = "//*[@id='brandList']";
	
	public static final String SALESFORCE_ID_TITLE_EDIT_MODE = "//*[@id='add-accessory-form']/fieldset/div[5]/label";
	public static final String SALESFORCE_ID_VALUE_TITLE_EDIT_MODE = "//*[@id='salesforce-id']";
	
	public static final String DISPLAY_NAME_TITLE_EDIT_MODE = "//*[@id='add-accessory-form']/fieldset/div[6]/label";
	public static final String DISPLAY_NAME_DEFAULT_LANGUAGE_TITLE_EDIT_MODE = "//*[@id='display-name-div']/div[1]/label";
	public static final String DISPLAY_NAME_VALUE_TITLE_EDIT_MODE = "//*[@id='display-name']";
	
	public static final String MODEL_NUMBER_TITLE_EDIT_MODE = "//*[@id='add-accessory-form']/fieldset/div[7]/label";
	public static final String MODEL_NUMBER_VALUE_TITLE_EDIT_MODE = "//*[@id='display-number']";
	
	public static final String EAN_UPC_TITLE_EDIT_MODE = "//*[@id='add-accessory-form']/fieldset/div[8]/label";
	public static final String EAN_UPC_VALUE_TITLE_EDIT_MODE = "//*[@id='upc']";
	
	public static final String TYPE_TITLE_EDIT_MODE = "//*[@id='add-accessory-form']/fieldset/div[9]/label";
	public static final String TYPE_VALUE_TITLE_EDIT_MODE = "//*[@id='modelType']";
	
	public static final String CONNECTION_TYPE_TITLE_EDIT_MODE = "//*[@id='add-accessory-form']/fieldset/div[10]/label";
	public static final String CHECKBOX_WIRED_VALUE_TITLE_EDIT_MODE = "//*[@id='checkboxWired']";
	public static final String LABEL_CHECKBOX_WIRED_VALUE_TITLE_EDIT_MODE = "//*[@id='connection-type-div']/label[1]";
	public static final String CHECKBOX_WIRED_USB_DATA_VALUE_TITLE_EDIT_MODE = "//*[@id='checkboxWiredUSBData']";
	public static final String LABEL_CHECKBOX_WIRED_USB_DATA_VALUE_TITLE_EDIT_MODE = "//*[@id='connection-type-div']/label[2]";
	public static final String CHECKBOX_BLUETOOTH_EDIT_MODE = "//*[@id='checkboxBluetooth']";
	public static final String LABEL_CHECKBOX_BLUETOOTH_EDIT_MODE = "//*[@id='connection-type-div']/label[3]";
	public static final String CHECKBOX_PLAY_FI_EDIT_MODE = "//*[@id='checkboxPlay-FI']";
	public static final String LABEL_CHECKBOX_PLAY_FI_EDIT_MODE = "//*[@id='connection-type-div']/label[4]";
	
	public static final String STEREO_CAPABILITY_VALUE_TITLE_EDIT_MODE = "//*[@id='add-accessory-form']/fieldset/div[11]/label";
	public static final String STEREO_CAPABILITY_SELECTION1_EDIT_MODE = "//*[@id='stereoCapabilitySelection1']";
	public static final String STEREO_CAPABILITY_SELECTION2_EDIT_MODE = "//*[@id='stereoCapabilitySelection2']";
	
	public static final String LABEL_RELEASE_DATE_EDIT_MODE = "//*[@id='add-accessory-form']/fieldset/div[12]/label";
	public static final String RELEASE_DATE_VALUE_TITLE_EDIT_MODE = "//*[@id='releaseDate']";
	
	public static final String SALES_STATUS_LABEL_EDIT_MODE = "//*[@id='add-accessory-form']/fieldset/div[13]/label";
	public static final String SALES_STATUS_SELECTION1_EDIT_MODE = "//*[@id='salesStatusSelection1']";
	public static final String SALES_STATUS_SELECTION2_EDIT_MODE = "//*[@id='salesStatusSelection2']";
	
	public static final String SAVE_EDIT_MODE = "//*[@id='save-accessory']";
	public static final String CANCEL_EDIT_MODE = "//*[@id='cancel-accessory']";
	public static final String IMAGE_GUIDELINE_EDIT_MODE = "//*[@id='image-guideline']";
	public static final String TUNING_GUIDELINE_EDIT_MODE = "//*[@id='tuning-guideline']";
	public static final String DOWNLOAD_TUNING_TOOL_EDIT_MODE = "//*[@id='download-tuning-tool']";
	public static final String MARKETING_GUIDELINE_EDIT_MODE = "//*[@id='marketing-guideline']";
	public static final String RELATED_FAQ_EDIT_MODE = "//*[@id='related-faq']";
	
	public static final ArrayList<String> getListAccessoriesEditUI() {
		ArrayList<String> arrayLists = new ArrayList<String>();
		arrayLists.add(MODEL_TITLE_EDIT_MODE);
		arrayLists.add(DTS_TRACKING_ID_EDIT_MODE);
		arrayLists.add(DTS_TRACKING_ID_VALUE_TITLE_EDIT_MODE);
		
		arrayLists.add(COMPANY_TITLE_EDIT_MODE);
		arrayLists.add(COMPANY_TITLE_VALUE_TITLE_EDIT_MODE);
		
		arrayLists.add(BRAND_TITLE_EDIT_MODE);
		arrayLists.add(BRAND_LIST_VALUE_TITLE_EDIT_MODE);
		
		arrayLists.add(SALESFORCE_ID_TITLE_EDIT_MODE);
		arrayLists.add(SALESFORCE_ID_VALUE_TITLE_EDIT_MODE);
		
		arrayLists.add(DISPLAY_NAME_TITLE_EDIT_MODE);
		arrayLists.add(DISPLAY_NAME_DEFAULT_LANGUAGE_TITLE_EDIT_MODE);
		arrayLists.add(DISPLAY_NAME_VALUE_TITLE_EDIT_MODE);
		
		arrayLists.add(MODEL_NUMBER_TITLE_EDIT_MODE);
		arrayLists.add(MODEL_NUMBER_VALUE_TITLE_EDIT_MODE);
		
		arrayLists.add(EAN_UPC_TITLE_EDIT_MODE);
		arrayLists.add(EAN_UPC_VALUE_TITLE_EDIT_MODE);
		
		arrayLists.add(TYPE_TITLE_EDIT_MODE);
		arrayLists.add(TYPE_VALUE_TITLE_EDIT_MODE);
		
		arrayLists.add(CONNECTION_TYPE_TITLE_EDIT_MODE);
		arrayLists.add(CHECKBOX_WIRED_VALUE_TITLE_EDIT_MODE);
		arrayLists.add(LABEL_CHECKBOX_WIRED_VALUE_TITLE_EDIT_MODE);
		arrayLists.add(CHECKBOX_WIRED_USB_DATA_VALUE_TITLE_EDIT_MODE);
		arrayLists.add(LABEL_CHECKBOX_WIRED_USB_DATA_VALUE_TITLE_EDIT_MODE);
		arrayLists.add(CHECKBOX_BLUETOOTH_EDIT_MODE);
		arrayLists.add(LABEL_CHECKBOX_BLUETOOTH_EDIT_MODE);
		arrayLists.add(CHECKBOX_PLAY_FI_EDIT_MODE);
		arrayLists.add(LABEL_CHECKBOX_PLAY_FI_EDIT_MODE);
		
		arrayLists.add(STEREO_CAPABILITY_VALUE_TITLE_EDIT_MODE);
		arrayLists.add(STEREO_CAPABILITY_SELECTION1_EDIT_MODE);
		arrayLists.add(STEREO_CAPABILITY_SELECTION2_EDIT_MODE);
		
		arrayLists.add(LABEL_RELEASE_DATE_EDIT_MODE);
		arrayLists.add(RELEASE_DATE_VALUE_TITLE_EDIT_MODE);
		
		arrayLists.add(SALES_STATUS_LABEL_EDIT_MODE);
		arrayLists.add(SALES_STATUS_SELECTION1_EDIT_MODE);
		arrayLists.add(SALES_STATUS_SELECTION2_EDIT_MODE);
		
		arrayLists.add(SAVE_EDIT_MODE);
		arrayLists.add(CANCEL_EDIT_MODE);
		arrayLists.add(IMAGE_GUIDELINE_EDIT_MODE);
		arrayLists.add(TUNING_GUIDELINE_EDIT_MODE);
		arrayLists.add(DOWNLOAD_TUNING_TOOL_EDIT_MODE);
		arrayLists.add(MARKETING_GUIDELINE_EDIT_MODE);
		arrayLists.add(RELATED_FAQ_EDIT_MODE);
		return arrayLists;
	}
	
	// accessory form
	public static final String ACCESSORY_FORM = "//*[@id='show-accessory-form']";
	// popup delete
	//public static final String POPUP_DELETE = "/html/body/div[4]/div[2]/a[2]";
	public static final String POPUP_DELETE = "html/body/div[6]/div[2]/a[2]";
	// page index
	public static final String PAGE_INDEX = "//*[@id='CompanyListTable_paginate']/span";
	
	// Add Accessories
	public static final String BTN_GROUP_BRAND = "//*[@id='site-privileges']/div[1]/div";
	public static final String ACCESSORY_THREAD = "//*[@id='BrandAccessoriesTable']/thead/tr";
	
	// page info
	public static final String BRAND_ACCESSORY_TABLE_INFO = "//*[@id='BrandAccessoriesTable_info']";
	public static final String LINK_APP_DEVICES = "//*[@id='nav_link_devices_dts']/a";
	public static final String LINK_AUDIO_ROUTES = "//*[@id='nav_link_audioroutes_dts']";
	public static final String COMPANY_LIST_TABLE_INFO = "//*[@id='CompanyListTable_info']";
	public static final String PRODUCT_TABLE_INFO = "//*[@id='product_table_info']";
	public static final String ADMIN_USER_LIST_TABLE_INFO = "//*[@id='AdminUserListTable_info']";
	public static final String PROMOTION_TABLE_INFO = "//*[@id='promo_table_info']";
	
	// Confirmation dialog
	public static final String BTN_CONFIRMATION_DANGER = "//a[@class='btn btn-danger' and @href='javascript:;']"; // Include delete button or suspend button
	public static final String BTN_CONFIRMATION_CANCEL = "//a[@class='btn null' and @href='javascript:;']";

	
	
	public static final ArrayList<String>accessoriesOnDetailPage() {
		ArrayList<String> arrayLists = new ArrayList<String>();
		arrayLists.add(DISPLAY_MODEL);
		arrayLists.add(MODEL_ACCESSORIES_DETAIL);
		arrayLists.add(VARIANT_ACCESSORIES_DETAIL);
		arrayLists.add(VERSION_ACCESSORIES_DETAIL);
		arrayLists.add(TUNING_STATUS_ACCESSORIES_DETAIL);
		arrayLists.add(HEADING_X_RATING_ACCESSORIES_DETAIL);
		arrayLists.add(MARKETING_STATUS_ACCESSORIES_DETAIL);
		return arrayLists;
	}
}