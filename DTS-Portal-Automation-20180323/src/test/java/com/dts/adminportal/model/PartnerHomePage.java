package com.dts.adminportal.model;

import java.util.ArrayList;
import java.util.HashMap;

public class PartnerHomePage {
	// top navigation bar
	public static final String LOGO = "/html/body/div[2]/div/div[1]/div/div[1]/a/img";
	public static final String NAVIGATION_BAR = "/html/body/div[2]/div/div[1]/div";
	public static final String DTS_PARTNER_NAME = "//*[@id='dts-partner-name']";
	public static final String EMAIL = "//*[@id='signinId']";
	public static final String PARTNER_MENU = "//*[@id='partner_menu']";
	public static final String PAGE_HEADER = "//*[@id='page-header']";
	public static final String subPageHeader = "Welcome, ";
	public static final String HOME_LOGO = "//*[@id='home-std']/div/div[1]/div[2]/img";
	public static final String PRODUCT_ACTION_MODULE = "//*[@id='home-production-action']";
	public static final String DROP_DOWN_MENU = "/html/body/div[2]/div/div[1]/div/div[2]/div/ul/li/ul";
	// error
	public static final String ERROR = "/html/body/div/form/div[1]/p";
	public static final String onlineSupportRequest = "//*[@id='main-container']/footer/p/a";
	public static final String logoImgHome = "/html/body/div[2]/div/div[1]/div/div[1]/a/img";
	public static final String btnLogout = "//*[@id='loggedInAs']";
	// Get all links home partner
	public static final String LINK_PARTNER_HOME = "//*[@id='nav_link_home_partner']/a";
	public static final String LINK_PARTNER_ACCESSORIES = "//*[@id='nav_link_accessories_partner']/a";
	public static final String LINK_PARTNER_USER = "//*[@id='nav_link_users_partner']/a";
	public static final String LINK_PARTNER_COMPANY = "//*[@id='nav_link_company_partner']/a";
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
	public static final String PARTNER_CATALOG_ADD_ACCESSORY = "//*[@id='partner-catalog-add-accessory']/a";
	public static final String PARTNER_CATALOG_DRAFT = "//*[@id='partner-catalog-draft']";
	public static final String PARTNER_CATALOG_READY_PUBLISH = "//*[@id='catalog-ready-publish']";
	public static final String PARTNER_CATALOG_NEED_ATTENTION = "//*[@id='catalog-need-attention']";
	public static final String PARTNER_CATALOG_SUSPENDED = "//*[@id='catalog-suspended']";
	
	public static final ArrayList<String> getListCatalog() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(PARTNER_CATALOG_ADD_ACCESSORY);
		arrayList.add(PARTNER_CATALOG_DRAFT);
		arrayList.add(PARTNER_CATALOG_READY_PUBLISH);
//		arrayList.add(PARTNER_CATALOG_NEED_ATTENTION);
//		arrayList.add(PARTNER_CATALOG_SUSPENDED);
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
//		arrayList.add(PARTNER_TUNING_REVOKE);
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
//		arrayList.add(PARTNER_MARKETING_REVOKED);
		return arrayList;
	}
	// GET ALL ELEMENT AT HOME PARTNER
	public static final ArrayList<String> getAllElements() {
		ArrayList<String> arrayLists = new ArrayList<String>();
		// add to all
		arrayLists.addAll(getListCatalog());
		arrayLists.addAll(getListTuningPartner());
		arrayLists.addAll(getListModelAllocationPartner());
		arrayLists.addAll(getListMarketingApprovelPartner());
		arrayLists.addAll(getAllLinkHomePartner());
		arrayLists.add(logoImgHome);
		arrayLists.add(onlineSupportRequest);
		arrayLists.add(btnLogout);
		return arrayLists;
	}
	public static final HashMap<String, String> GET_USER_MENU(){
		HashMap<String, String> menu = new HashMap<String, String>();
		menu.put("option 1", "User Account");
		menu.put("option 2", "Log out");
		return menu;
	}
	// module
	public static final String CATALOG = "//*[@id='home-row-1']/div[1]/div[1]/strong";
	public static final String TUNING_STATUS = "//*[@id='home-row-1']/div[2]/div[1]/strong";
	public static final String MODEL_ALLOCATION_STATUS = "//*[@id='home-row-2']/div[1]/div[1]/strong";
	public static final String MARKETING_APPROVAL_STATUS = "//*[@id='home-row-2']/div[2]/div[1]/strong";
	public static ArrayList<String> getModules() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(CATALOG);
		arrayList.add(TUNING_STATUS);
		arrayList.add(MODEL_ALLOCATION_STATUS);
		arrayList.add(MARKETING_APPROVAL_STATUS);
		return arrayList;
	}
	
	public static ArrayList<String> getElementInfo(){
		ArrayList<String> list = new ArrayList<String>();
		list.add(LOGO);
		list.add(DTS_PARTNER_NAME);
		list.add(EMAIL);
		list.add(PARTNER_MENU);
		list.add(PAGE_HEADER);
		list.add(HOME_LOGO);
		list.add(PRODUCT_ACTION_MODULE);
		return list;
	}
	
	public static ArrayList<String> listContentXpath(){
		ArrayList<String> list = new ArrayList<String>();
		list.add("//*[@id='home-std']/div/div[1]/div[3]/p/strong");
		list.add("//*[@id='home-std']/div/div[1]/div[4]/p");
		list.add("//*[@id='home-std']/div/div[1]/div[5]/p");
		list.add("//*[@id='home-std']/div/div[1]/div[6]/p");
		list.add("//*[@id='home-std']/div/div[1]/div[7]/p");
		list.add("//*[@id='home-std']/div/div[1]/div[8]/p");
		return list;
	}
	
	
	public static ArrayList<String> getContentMessage(){
		ArrayList<String> list = new ArrayList<String>();
		list.add("DTS Headphone:X + Your Headphones = Incredible, mind-blowing sound!");
		list.add("Upload your company's headphone data here. The global Headphone:X technology platform delivers the latest tunings, pictures, and related meta-data to consumers around the world in real time. Only DTS Headphone:X brings immsersive cinematic audio up to 11.1 channels over your headphones.");
		list.add("What is DTS Headphone:X?\n\"Headphone:X makes all of your content sound better.\"");
//		list.add("Headphone:X makes all of your content sound better.");
		list.add("Movies: Headphone:X delivers an immersive, cinematic audio experience with your headphones. An experience that consumers have come to expect from a full, 11.1 channel surround sound movie theater. But it doesn't just put a home theater audio experience in everyone's pockets, it works with music and games, too.");
		list.add("Music: If audio tracks are recorded in Headphone:X surround sound, consumers can enjoy the full benefit of Headphone:X technology. But it can also improve the overall quality of everyone's existing music as well. And your \"OEM EQ\" ensures that the consumer's audio experience with their existing music sounds exactly how you intended.");
		list.add("Gaming: Deeper and more immersive gaming experiences. Headphone:X gives gamers an unfair advantage by giving a new dimension to their hearing. So now gamers can hear their enemy before they see them.");
		return list;
	}
	public static enum TOP_NAVIGATION_MESSAGE {
		Welcome("Welcome, "),
		Welcome_Message_position("Welcome Message position: "),
		DTS_Logo_position("DTS Logo position: "),
		PDPP("PDPP-????");
		
		private final String message;

		TOP_NAVIGATION_MESSAGE (String message) {
			this.message = message;
		}

		public String getName() {
			return this.message;
		}
	};
	
	
}
