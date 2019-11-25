package com.dts.adminportal.model;

import java.util.ArrayList;

public class DTSHome {
	// navigation bar
	public static final String VIEW_AS = "//*[@id='view-as']";
	public static final String PARTNERS_LIST = "/html/body/div[2]/div/div[1]/div/div[2]/div/div[2]/div/button";
	public static final String PARTNERS_LIST_DROPDOWN = "//*[@class = 'btn-group bootstrap-select']";
	public static final String DTS_MENU = "//*[@id='dts_menu']";
	public static final String SIGNIN_EMAIL = "//*[@id='signinId']";
	public static final String USER_ACCOUNT = "//*[@id='userAccount']";
	public static final String LOGOUT = "//*[@id='logout']";
	// logout
	public static final String lbLogout = "//*[@id='signinId']";
	public static final String btnLogout = "//*[@id='loggedInAs']";
	public static final String submitLogout = "//*[@id='logout']";
	public static final String loginAccount = "//*[@id='userAccount']";
	// main											
	public static final String LINK_HOME = "//*[@id='nav_link_home_dts']/a";
	public static final String LINK_ACCESSORIES = "//*[@id='nav_link_accessories_dts']/a";
	public static final String LINK_DEVICE = "//*[@id='nav_link_devices_dts']/a";
	public static final String LINK_PROMOTIONS = "//*[@id='nav_link_promotions_dts']/a";
	public static final String LINK_AUDIOROUTES = "//*[@id='nav_link_audioroutes_dts']/a";
	public static final String LINK_USERS = "//*[@id='nav_link_users_dts']/a";
	public static final String LINK_COMPANY = "//*[@id='nav_link_company_dts']/a";
	public static final ArrayList<String> getTab() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(LINK_HOME);
		arrayList.add(LINK_ACCESSORIES);
		arrayList.add(LINK_DEVICE);
		arrayList.add(LINK_PROMOTIONS);
		arrayList.add(LINK_AUDIOROUTES);
		arrayList.add(LINK_USERS);
		arrayList.add(LINK_COMPANY);
		return arrayList;
	}
	// info
	public static final String COPY_RIGHT_INFORMATION = "//*[@id='main-container']/footer/p";
	// link 
	public static final String ONLINE_SUPPORT_REQUEST = "//*[@id='main-container']/footer/p/a";
	public static final String DROPDOWN_LIST_VIEW_AS = "/html/body/div[2]/div/div[1]/div/div[2]/div/div[2]/div/button";
	// /Catalog
	public static final String CATALOG_VIEW = "//*[@id='home-catalog-view']";
	public static final String CATALOG_ADD_ACCESSORY = "//*[@id='catalog-add-accessory']";
	public static final String CATALOG_RECENTLY_ADDED = "//*[@id='catalog-recently-added']";
	public static final String CATALOG_DRAFT = "//*[@id='catalog-draft']";
	public static final String CATALOG_READY_PUBLISH = "//*[@id='catalog-ready-publish']";
	public static final String CATALOG_OVERDUE = "//*[@id='catalog-overdue']";
	public static final String CATALOG_RECENTLY_PUBLISHED = "//*[@id='catalog-recently-published']";
	public static final String CATALOG_NEED_ATTENTION = "//*[@id='catalog-need-attention']";
	public static final String CATALOG_SUSPENDED = "//*[@id='catalog-suspended']";
	public static final ArrayList<String> getCatalog() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(CATALOG_ADD_ACCESSORY);
		arrayList.add(CATALOG_RECENTLY_ADDED);
		arrayList.add(CATALOG_DRAFT);
		arrayList.add(CATALOG_READY_PUBLISH);
		arrayList.add(CATALOG_OVERDUE);
		arrayList.add(CATALOG_RECENTLY_PUBLISHED);
//		arrayList.add(CATALOG_NEED_ATTENTION);
//		arrayList.add(CATALOG_SUSPENDED);
		return arrayList;
	}
	// /Tuning Status
	public static final String TUNING_DTS_REQUEST_PENDING = "//*[@id='catalog-tuning-request']";
	public static final String TUNING_REQUEST_OVERDUE = "//*[@id='catalog-tuning-request-overdue']";
	public static final String TUNING_PENDING_PARTREVIEW = "//*[@id='catalog-tuning-pending-part-review']";
	public static final String TUNING_PENDING_PART_REVIEW_OVERDUE = "//*[@id='catalog-tuning-pending-part-review-overdue']";
	public static final String TUNING_PENDING_DTS_REVIEW = "//*[@id='catalog-tuning-pending-dts-review']";
	public static final String TUNING_PENDING_DTS_REVIEW_OVERDUE = "//*[@id='catalog-tuning-pending-dts-review-overdue']";
	public static final String TUNING_HEADPHONE_LEVEL_A_PLUS = "//*[@id='catalog-tuning-headphone-level-a-plus']";
	public static final String TUNING_HEADPHONE_LEVEL_A = "//*[@id='catalog-tuning-headphone-level-a']";
	public static final String TUNING_DECLINED = "//*[@id='catalog-tuning-declined']";
	public static final String TUNING_REVOKED = "//*[@id='catalog-tuning-revoked']";
	public static final ArrayList<String> getTuning() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(TUNING_DTS_REQUEST_PENDING);
		arrayList.add(TUNING_REQUEST_OVERDUE);
		arrayList.add(TUNING_PENDING_PARTREVIEW);
		arrayList.add(TUNING_PENDING_PART_REVIEW_OVERDUE);
		arrayList.add(TUNING_PENDING_DTS_REVIEW);
		arrayList.add(TUNING_PENDING_DTS_REVIEW_OVERDUE);
		arrayList.add(TUNING_HEADPHONE_LEVEL_A_PLUS);
		arrayList.add(TUNING_HEADPHONE_LEVEL_A);
//		arrayList.add(TUNING_DECLINED);
//		arrayList.add(TUNING_REVOKED);
		return arrayList;
	}
	// /Companies
	public static final String COMPANY_VIEW = "//*[@id='home-companies-view']";
	public static final String COMPANY_RECENTLY_ADDED = "//*[@id='home-company-recently-added']";
	public static final String COMPANY_PENDING_INVITE = "//*[@id='home-company-pending-invite']";
	public static final String COMPANY_WATCH_LIST = "//*[@id='home-company-watch-list']";
	public static final String COMPANY_CONTRACT_ENDING = "//*[@id='home-company-contract-ending']";
	public static final String COMPANY_INACTIVE = "//*[@id='home-company-inactive']";
	public static final String COMPANY_SUSPENDED = "//*[@id='home-company-suspended']";
	public static final ArrayList<String> getCompany() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(COMPANY_VIEW);
		arrayList.add(COMPANY_RECENTLY_ADDED);
		arrayList.add(COMPANY_PENDING_INVITE);
		arrayList.add(COMPANY_WATCH_LIST);
		arrayList.add(COMPANY_CONTRACT_ENDING);
		arrayList.add(COMPANY_INACTIVE);
		arrayList.add(COMPANY_SUSPENDED);
		return arrayList;
	}
	// /Marketing Approval Status
	public static final String MARKETING_UNSUBMITTED = "//*[@id='home-marketing-unsubmitted']";
	public static final String MARKETING_DTS_PENDING = "//*[@id='home-marketing-dts-pending']";
	public static final String MARKETING_DTS_PENDING_OVERDUE = "//*[@id='home-marketing-dts-pending-overdue']";
	public static final String MARKETING_DECLINED = "//*[@id='home-marketing-declined']";
	public static final String MARKETING_REVOKED = "//*[@id='home-marketing-revoked']";
	public static final ArrayList<String> getMarketing() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(MARKETING_UNSUBMITTED);
		arrayList.add(MARKETING_DTS_PENDING);
		arrayList.add(MARKETING_DTS_PENDING_OVERDUE);
//		arrayList.add(MARKETING_DECLINED);
//		arrayList.add(MARKETING_REVOKED);
		return arrayList;
	}
	// /Model Allocation Status
	public static final String MODEL_ALLOC_REQUEST = "//*[@id='model-alloc-request']";
	public static final String MODEL_ALLOC_OUT_OF_UNITS = "//*[@id='model-alloc-out-of-units']";
	public static final String MODEL_ALLOC_LOW_UNITS = "//*[@id='model-alloc-low-units']";
	public static final ArrayList<String> getModel() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(MODEL_ALLOC_REQUEST);
		arrayList.add(MODEL_ALLOC_OUT_OF_UNITS);
		arrayList.add(MODEL_ALLOC_LOW_UNITS);
		return arrayList;
	}
	public static final ArrayList<String> getAll() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.addAll(getCatalog());
		arrayList.addAll(getTuning());
		arrayList.addAll(getCompany());
		arrayList.addAll(getMarketing());
		arrayList.addAll(getModel());
		arrayList.addAll(getTab());
		return arrayList;
	}
	
	public static ArrayList<String> getElementInfo(){
		ArrayList<String> list = new ArrayList<String>();
		list.add(SIGNIN_EMAIL);
		list.add(PARTNERS_LIST);
		list.add(DTS_MENU);
		list.add(CATALOG_VIEW);
		list.add(COMPANY_VIEW);
		return list;
		
	}
	
	
}
