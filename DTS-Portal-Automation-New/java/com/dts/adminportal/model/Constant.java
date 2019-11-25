package com.dts.adminportal.model;

import java.util.ArrayList;


public class Constant {
	public static final int TIME_WAIT = 30;
	public static final int TIME_SLEEP = 5000;
	public static final String password = "fa3!";	
	public static final String siteBase = "devportal.dts.com";
	public static final String forgetError = "The requested resource is not available.";
	public static final String COPYRIGHT = "� Copyright 2013 DTS Inc.\n5220 Las Virgenes Road, Calabasas, CA 91302 T: +1 818.436.1000 | Online Support Request";
	// View As
	public static final String dtsInc = "DTS Inc.";
	public static final String skullcandyInc = "Skullcandy Inc.";
	public static final String FRENDS = "FRENDS";
	public static final String hiQAudio = "HiQ Audio";
	public static final String beaniez = "Beaniez";
	public static final String panasonic = "Panasonic";
	public static final ArrayList<String> getAllPartners() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(dtsInc);
		arrayList.add(skullcandyInc);
		arrayList.add(FRENDS);
		arrayList.add(hiQAudio);
		arrayList.add(beaniez);
		arrayList.add(panasonic);
		return arrayList;
	}
	// accessory Filter Select
	public static final String accessoryAll = "All";
	public static final String ACCESSORY_ALL_ACTIVE = "All Active";
	public static final String ACCESSORY_RECENTLY_ADDED = "Recently Added";
	public static final String ACCESSORY_DRAFT = "Draft";
	public static final String accessoryUnderTuningReview = "Under Tuning Review";
	public static final String READY_FOR_MARKETING = "Ready for Marketing";
	public static final String ACCESSORY_READY_TO_PUBLISH = "Ready to Published";
	public static final String ACCESSORY_READY_TO_PUBLISH_OVERDUE = "Ready to Published - Overdue";
	public static final String ACCESSORY_PUBLISHED = "Published";
	public static final String ACCESSORY_SUSPENDED = "Suspended";
	public static final String ACCESSORY_TUNING_REVOKED = "Tuning Revoked";
	public static final String ACCESSORY_MARKETING_REVOKED = "Marketing Revoked";
	public static final String ACCESSORY_NEEDATTENTION = "Needs Attention";
	
	// dts category filter
	public static final String categoryAll = "All";
	public static final String categoryDJ = "DJ";
	public static final String categoryGaming = "GAMING";
	public static final String categoryHighEnd = "High End";
	public static final String categoryHomeAudio = "HOME_AUDIO";
	public static final String categoryMusicEnt = "MUSIC_ENT";
	public static final String categorySports = "SPORTS";
	public static final String categoryTravel = "TRAVEL";
	public static final String categoryTV = "TV";
	// user Filter Select
	public static final String userActive = "Active";
	public static final String userPending = "Pending";
	public static final String userLocked = "Locked";
	public static final String userInactive = "Inactive";
	public static final String userUnknown = "Unknown";
	// company Filter Select
	public static final String companyAllActive = "All Active";
	public static final String companyDateAdded = "Date Added";
	public static final String companyPendingInvitation = "Pending Invitations";
	public static final String companyWatchList = "Watch List";
	public static final String companyContractEndingSoon = "Contract Ending Soon";

	// Home heading text
	public static final String headingTextCatalog = "Catalog";
	public static final String headingTextTuniungstatus = "Tuning Status";
	public static final String headingTextCompanies = "Companies";
	public static final String headingTextMaketingApprovalStatus = "Marketing Approval Status";
	public static final String headingTextModelAllocationStatus = "Model Allocation Status";
	// Home Catalog heading text
	public static final String homeAddAccessoryText = "Add Accessory";
	public static final String homeRecentlyAddedText = "Recently Added";
	public static final String homeDraftText = "Draft";
	public static final String homeReadyToPublishText = "Ready to Published";
	public static final String homeOverdueText = "Overdue";
	public static final String HOME_RECENTLY_PUBLISHED = "Recently Published";
	public static final String HOME_NEEDS_ATTENTION = "Needs Attention";
	public static final String HOME_SUSPENDED = "Suspended";

	public static final ArrayList<String> getAllHomeCatalogHeadingText() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(homeAddAccessoryText);
		arrayList.add(homeRecentlyAddedText);
		arrayList.add(homeDraftText);
		arrayList.add(homeReadyToPublishText);
		arrayList.add(homeOverdueText);
		arrayList.add(HOME_RECENTLY_PUBLISHED);
		arrayList.add(HOME_NEEDS_ATTENTION);
		arrayList.add(HOME_SUSPENDED);
		return arrayList;
	}

	// Home Tunning Status text
	public static final String DTS_REQUEST_PENDING = "DTS Request Pending";
	public static final String PENDING_PARTNER_REVIEW = "Partner Tuning Review";
	public static final String DTS_TUNING_REVIEW = "DTS Tuning Review";
	public static final String HEADPHONE_A_PLUS = "Headphone:X Rating A+";
	public static final String HEADPHONE_A = "Headphone:X Rating A";
	public static final String TUNING_DECLINED = "Tuning Declined";
	public static final String TUNING_REVOKED = "Tuning Revoked";
	public static final String TUNING_REQUEST_OVERDUE = "Tuning Request - Overdue";
	public static final String PARTNER_TUNING_REVIEW_OVERDUE = "Partner Tuning Review - Overdue";
	public static final String PARTNER_TUNING_REVIEW = "Partner Tuning Review";
	public static final String DTS_TUNING_REVIEW_OVERDUE = "DTS Tuning Review - Overdue";
	public static final ArrayList<String> getAllHomeTuningStatusHeadingText() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(DTS_REQUEST_PENDING);
		arrayList.add(homeOverdueText);
		arrayList.add(PENDING_PARTNER_REVIEW);
		arrayList.add(homeOverdueText);
		arrayList.add(DTS_TUNING_REVIEW);
		arrayList.add(homeOverdueText);
		arrayList.add(HEADPHONE_A_PLUS);
		arrayList.add(HEADPHONE_A);
		arrayList.add(TUNING_DECLINED);
		arrayList.add(TUNING_REVOKED);
		return arrayList;
	}

	// Home Companise text
	public static final String homePendingInvitationsText = "Pending Invitations";
	public static final String homeWatchListText = "Watch List";
	public static final String homeContractEndingSoonText = "Contract Ending Soon";
	public static final String homeInactiveFor90DaysText = "Inactive for 90 Days";
//	public static final String homeGhostsText = "Ghosts";

	public static final ArrayList<String> getAllHomeCompaniesText() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(homeRecentlyAddedText);
		arrayList.add(homePendingInvitationsText);
		arrayList.add(homeWatchListText);
		arrayList.add(homeContractEndingSoonText);
		arrayList.add(homeInactiveFor90DaysText);
		arrayList.add(HOME_SUSPENDED);
//		arrayList.add(homeGhostsText);
		return arrayList;
	}

	// Home Maketing Approval Status
	public static final String homeReadyForMarketingText = "Ready for Marketing";
	public static final String DTS_MARKETING_REVIEW = "DTS Marketing Review";
	public static final String MARKETING_OVERDUE = "DTS Marketing Review - Overdue";
	public static final String MARKETING_DECLINED = "Declined";
	public static final String MARKETING_DECLINED_STATUS = "Marketing Declined";
	public static final String MARKETING_REVOKED = "Revoked";
	public static final String MARKETING_REVOKED_STATUS = "Marketing Revoked";
	public static final ArrayList<String> getAllHomeMaketingApprovalStatusText() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(homeReadyForMarketingText);
		arrayList.add(DTS_MARKETING_REVIEW);
		arrayList.add(homeOverdueText);
		arrayList.add(MARKETING_DECLINED);
		arrayList.add(MARKETING_REVOKED);
		return arrayList;
	}
	
	// Home Model Allocation Status
	public static final String homeRequestingAdditionalUnitsText = "Requesting Additional Units";
	public static final String homeOutOfUnitsText = "Out of Units";
	public static final String homeLowUnitsText = "Low Units";

	public static final ArrayList<String> getAllHomeModelAllocationStatusText() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(homeRequestingAdditionalUnitsText);
		arrayList.add(homeOutOfUnitsText);
		arrayList.add(homeLowUnitsText);
		return arrayList;
	}
	// Home/Catalog/Partner
	 public static final String partnerCatalog = "Catalog";
	 public static final String partnerAddAccessory = "Add Accessory";
	 public static final String partnerDraft = "Draft";
	 public static final String partnerReadyToPublish = "Ready to Publish";
	 public static final String partnerNeedsAttention = "Needs Attention";
	 public static final String partnerSuspended = "Suspended";
	 public static final ArrayList<String> getListCatalogPartnerText() {
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add(partnerAddAccessory);
			arrayList.add(partnerDraft);
			arrayList.add(partnerReadyToPublish);
			arrayList.add(partnerNeedsAttention);
			arrayList.add(partnerSuspended);
			return arrayList;
		}
	 public static final String partnerTuningStatus = "Tuning Status";
	 public static final String TUNING_REQUEST_PENDING = "Tuning Request Pending";
	 public static final String partnerPartnerTuningReview = "Partner Tuning Review";
	 public static final String partnerDTSTuningReview = "DTS Tuning Review";
	 public static final String partnerRevoked = "Revoked";
	 public static final ArrayList<String> getListTuningStatusPartnerText() {
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add(TUNING_REQUEST_PENDING);
			arrayList.add(partnerPartnerTuningReview);
			arrayList.add(partnerDTSTuningReview);
			arrayList.add(partnerRevoked);
			return arrayList;
		}
	 public static final String partnerModelAllocationStatus = "Model Allocation Status";
	 public static final String partnerContactDTS = "Contact DTS";
	 public static final ArrayList<String> getListModelAllocationStatusPartnerText() {
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add(partnerContactDTS);
			return arrayList;
		}
	 public static final String partnerMarketingApprovalStatus = "Marketing Approval Status";
	 public static final String partnerReadyForMarketingReview = "Ready for Marketing Review";
	 public static final String partnerDTSMarketingReview = "DTS Marketing Review";
	 public static final ArrayList<String> getListMarketingApprovalStatusPartnerText() {
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add(partnerReadyForMarketingReview);
			arrayList.add(partnerDTSMarketingReview);
			arrayList.add(partnerRevoked);
			return arrayList;
		}
	 // Catalog
	 public static final String urlAddAccessory = "catalog#reportType=add";
	 public static final String lbAccessoryText = "Adding New Model";
	 public static final String urlRecentlyAdded = "catalog#reportType=recently_added";
	 public static final String URL_READY_PUBLISH = "catalog#reportType=ready_to_published";
	 public static final String URL_DRAFT = "catalog#reportType=draft";
	 public static final String URL_CATALOG_OVERDUE = "catalog#reportType=ready_to_published_overdue";
	 public static final String urlRecentlyPublished = "catalog#reportType=recently_published";
	 public static final String URL_NEED_ATTENTION = "catalog#reportType=needs_attention";
	 public static final String urlSuspended = "catalog#reportType=suspended";
	 
	 public static final String URL_PARTNER_REVIEW = "catalog#reportType=partner_tuning_review";
	 public static final String urlCompanyRecentlyAdded = "company#reportType=recently-added";
	 public static final String urlCompanyPendingInvite = "company#reportType=pending-invite";
	 public static final String urlCompanyContractEndingSoon = "company#reportType=contract-ending";
	 public static final String urlCompanyInactive = "company#reportType=inactive";
	 public static final String urlCompanySuspended = "company#reportType=suspended";
	 public static final String urlCompanyGhosts = "company#reportType=ghosts";
	 
	 public static final String URL_WATCH_LIST = "company#reportType=watch_list";
	 public static final String urlReadyForMarketing = "marketing#reportType=ready_for_marketing";
	 public static final String urlDtsMarketingReview = "marketing#reportType=dts_marketing_review";
	 public static final String urlMarketingOverdue = "marketing#reportType=overdue";
	 public static final String urlMarketingDeclined = "marketing#reportType=declined";
	 public static final String urlMarketingRevoked = "marketing#reportType=revoked";
	 public static final String urlRequestingAdditionalUnits = "model#reportType=requesting_additional_units";
	 public static final String urlOutofUnits = "model#reportType=out_of_units";
	 public static final String urlLowUnits = "model#reportType=low_units";
	 
	 public static final String logoutOptionUserAccount = "User Account";
	 public static final String logoutOptionLogout  = "Log out";
	 public static final ArrayList<String> getListLogoutOptionText() {
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add(logoutOptionUserAccount);
			arrayList.add(logoutOptionLogout);
			return arrayList;
	 }
	 
	 // DTS INI Home/Tuning Status/link
	 public static final String URL_TUNING_DTS_REVIEW = "catalog#reportType=dts_tuning_review";
	 public static final String URL_TUNING_RATING_AA = "catalog#reportType=headphone_x_rating_aa";
	 public static final String URL_TUNING_RATING_A = "catalog#reportType=headphone_x_rating_a";
	 public static final String URL_TUNING_DECLINE = "catalog#reportType=tuning_declined";
	 public static final String URL_TUNING_REVOKE = "catalog#reportType=tuning_revoked";
	 public static final String URL_PARTNER_TUNING_REVIEW_OVERDUE = "catalog#reportType=partner_tuning_review_overdue";
	 public static final String URL_DTS_TUNING_REVIEW_OVERDUE = "catalog#reportType=dts_tuning_review_overdue";
	 // Company
	 public static final String status = "Status";
	 public static final String active = "Active";
	 public static final String invited = "Invited";
	 public static final String TUNNING_STATUS = "Tuning Status";
	 // Partner Catalog
	 public static final String URL_PARTNER_ADD_ACCESSORY = "catalog#reportType=add";
	 public static final String URL_PARTNER_READY_PUBLISH = "catalog#reportType=ready-publish";
	 public static final String URL_PARTNER_NEED_ATTENTION = "catalog#reportType=need-attention";
	 public static final String URL_PARTNER_CATALOG_SUSPENDED = "catalog#reportType=catalog-suspended";
	 // Partner Tuning Status
	 public static final String URL_PARTNER_TUNING_REQUEST = "catalog#reportType=tuning-request";
	 public static final String URL_PARTNER_TUNING_REVIEW = "catalog#reportType=partner-tuning-review";
	 public static final String URL_PARTNER_DTS_TUNING_REVIEW = "catalog#reportType=dts-tuning-review";
	 public static final String URL_TUNING_REVOKED = "catalog#reportType=tuning-revoked";
	 public static final String URL_TUNING_RESQUEST_PENDING = "catalog#reportType=tuning_request_pending";
	 public static final String URL_TUNING_RESQUEST_PENDING_OVERDUE = "catalog#reportType=tuning_request_overdue";
	 // URL Marketing Approval Status
	 public static final String URL_READY_FOR_MARKETING = "catalog#reportType=ready_for_marketing";
	 public static final String URL_DTS_MARKETING_REVIEW = "catalog#reportType=dts_marketing_review";
	 public static final String URL_DTS_MARKETING_REVIEW_OVERDUE = "catalog#reportType=dts_marketing_review_overdue";
	 public static final String URL_MARKETING_DECLINED = "saap/catalog#reportType=marketing_declined";
	 public static final String URL_MARKETING_REVOKED = "catalog#reportType=marketing_revoked";
	 // Filter Reports : DTS Inc.
	 /*
	  * All Active
		Draft
		Tuning Request Pending
		Partner Tuning Review
		DTS Tuning Review
		
		Ready for Marketing
		DTS Marketing Review
		Ready to Published
		Published
		Needs Attention
		
		Suspended
		Recently Published
		Recently Added
		Tuning Request - Overdue
		Partner Tuning Review - Overdue
		
		DTS Tuning Review - Overdue
		Tuning Declined
		Tuning Revoked
		Headphone:X Rating A+
		Headphone:X Rating A
		
		DTS Marketing Review - Overdue
		Marketing Declined
		Marketing Revoked
		Ready to Published - Overdue
	  */
	 public static final ArrayList<String> getListFilterDtsReports() {
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add(ACCESSORY_ALL_ACTIVE);
			arrayList.add(HOME_RECENTLY_PUBLISHED);
			arrayList.add(ACCESSORY_RECENTLY_ADDED);
			arrayList.add(ACCESSORY_DRAFT);
			arrayList.add(TUNING_REQUEST_PENDING);
			arrayList.add(TUNING_REQUEST_OVERDUE);
			arrayList.add(PARTNER_TUNING_REVIEW_OVERDUE);
			arrayList.add(PARTNER_TUNING_REVIEW);
			arrayList.add(DTS_TUNING_REVIEW);
			arrayList.add(DTS_TUNING_REVIEW_OVERDUE);
			arrayList.add(TUNING_DECLINED);
			arrayList.add(TUNING_REVOKED);
			arrayList.add(HEADPHONE_A_PLUS);
			arrayList.add(HEADPHONE_A);
			arrayList.add(READY_FOR_MARKETING);
			arrayList.add(DTS_MARKETING_REVIEW);
			arrayList.add(MARKETING_OVERDUE);
			arrayList.add(MARKETING_DECLINED_STATUS);
			arrayList.add(MARKETING_REVOKED_STATUS);
			arrayList.add(ACCESSORY_READY_TO_PUBLISH);
			arrayList.add(ACCESSORY_READY_TO_PUBLISH_OVERDUE);
			arrayList.add(HOME_NEEDS_ATTENTION);
			arrayList.add(HOME_SUSPENDED);
			return arrayList;
	 }
	 // Filter Reports Partner
	 /*
	  	All Active
		Draft
		Tuning Request Pending
		Partner Tuning Review
		DTS Tuning Review
		Ready for Marketing
		DTS Marketing Review
		Ready to Published
		Published
		Needs Attention
		Suspended
	*/
	 public static final ArrayList<String> getListPartnerFilterDtsReports() {
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add(ACCESSORY_ALL_ACTIVE);
			arrayList.add(ACCESSORY_DRAFT);
			arrayList.add(TUNING_REQUEST_PENDING);
			arrayList.add(PENDING_PARTNER_REVIEW);
			arrayList.add(DTS_TUNING_REVIEW);
			arrayList.add(READY_FOR_MARKETING);
			arrayList.add(DTS_MARKETING_REVIEW);
			arrayList.add(ACCESSORY_READY_TO_PUBLISH);
			arrayList.add(ACCESSORY_PUBLISHED);
			arrayList.add(HOME_NEEDS_ATTENTION);
			arrayList.add(ACCESSORY_SUSPENDED);
			arrayList.add(ACCESSORY_TUNING_REVOKED);
			arrayList.add(ACCESSORY_MARKETING_REVOKED);
			return arrayList;
	 }
	 // data grid
	 public static final String VERSION = "Version";
	 public static final String MARKETING_STATUS = "Marketing Status";
	 public static final String HEADPHONE_X_RATING = "Headphone X Rating";
	 // Tuning Status
	 public static final String PENDING_PARTNER_APPROVAL = "Pending Partner Approval";
	 public static final String PENDING_DTS_APPROVAL = "Pending DTS Review";
	 public static final String APPROVED = "Approved";
	 public static final String REVOKED_BY_DTS = "Revoked by DTS";
	 public static final String PARTNER_DECLINED = "Partner Declined";
	 public static final String DTS_DECLINED = "DTS Declined";
	 public static final ArrayList<String> getListTuningDeclined() {
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add(PARTNER_DECLINED);
			arrayList.add(DTS_DECLINED);
			return arrayList;
	 }
	 public static final ArrayList<String> getListNeedsAttention() {
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add(MARKETING_DECLINED);
			arrayList.add(MARKETING_REVOKED);
			return arrayList;
	 }
	 // Marketing Status
	 public static final String UNSUBMITTED = "Unsubmitted";
	 public static final String PENDING_DTS_MARKETING_REVIEW = "DTS Marketing Request Review";
	 // HEADPHONE X RATING status
	 public static final String RATINGAPLUS = "A+";
	 public static final String RATINGA = "A";
	 // Message error
	 public static final String ADD_ACCESSORIES_ERROR = "Please select company from view as.";
	 public static final String ERROR_SAVE_ACCESSORIES = "is required";
	 
	 public static final String PENDING_DTS_REVIEW = "Pending DTS Review";
	 
	 // Standard Accessories
	 public static final String EARBUDS = "Earbuds";
	 public static final String OVER_EAR = "Over-Ear";
	 public static final String AMPERIOR = "Amperior";
	 public static final ArrayList<String> getStandardAccessories() {
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add(EARBUDS);
			arrayList.add(OVER_EAR);
			arrayList.add(AMPERIOR);
			return arrayList;
	 }
	 // Accessories header
	 public static final String PICTURE = "Picture";
	 public static final String MODEL = "Model";
	 public static final String VARIANTS = "Variants";
	 public static final String PUBLISHED = "Published";
//	 public static final String TUNNING_STATUS = "Tunning Status";
//	 public static final String HEADPHONE_X_RATING = "Headphone X Rating";
//	 public static final String MARKETING_STATUS = "Marketing Status";
//	 public static final String VERSION = "Version";
	 public static final ArrayList<String> getHeaderAccessoriesTable(){
		 ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add(PICTURE);
			arrayList.add(MODEL);
			arrayList.add(VARIANTS);
			arrayList.add(PUBLISHED);
			arrayList.add(TUNNING_STATUS);
			arrayList.add(HEADPHONE_X_RATING);
			arrayList.add(MARKETING_STATUS);
			arrayList.add(VERSION);
			return arrayList;
	 }

	// public static final String fileGPB = "tuning-based-on-GPB.zip";
	 //public static final String fileJson2 = "tuning based on JSON2.zip";
	 //public static final String fileGPB2 = "tuning based on GPB2.zip";
	 public static final String NAME_ERR = "Model name is required.";
	 public static final String IMAGE_NAME = "Desert.jpg";
	 public static final String ERROR_ALERT = "Internal Server Error";
	 
	 public static final String IMG_DRAG_DROP_AREA[]={
			"TuningDragDropArea.PNG",
			"ImageDragDropArea250x250.JPG",
			"ImageDragDropArea500x500.JPG",
			"ImageDragDropArea1000x1000.JPG",
			"MarketingDragDropArea.PNG"
		};
	 	 
	 public static final String TUNING_APPROVAL[]={
		 "* Headphone:X Tuning Rating is required.",
		 "* EAN/UPC is required.",
		 
	 };
	 public static enum netWorkState {
			RELEASE("release"),RENEW("renew");

			private final String type;

			netWorkState(String type) {
				this.type = type;
			}

			public String getName() {
				return this.type;
			}

			public static String[] getNames() {
				netWorkState[] types = values();
				String[] result = new String[types.length];
				for (int i = 0; i < types.length; i++) {
					result[i] = types[i].getName();
				}
				return result;
			}
		}
	 

	 
	 
}
