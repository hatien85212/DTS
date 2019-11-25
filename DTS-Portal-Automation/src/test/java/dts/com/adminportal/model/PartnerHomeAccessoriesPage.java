package dts.com.adminportal.model;

public class PartnerHomeAccessoriesPage {
	public static final String FILTER = "//*[@id='accessoryFilterSelect']";
	public static String option[]  = {	
		"All Active",					// 0
		"Draft",						// 1
		"Tuning Request Pending",		// 2
		"Partner Tuning Review",		// 3
		"DTS Tuning Review",			// 4
		"Ready for Marketing",			// 5
		"DTS Marketing Review",			// 6
		"Ready to Published",			// 7
		"Published",					// 8
		"Needs Attention",				// 9
		"Suspended",					// 10
		"Tuning Revoked",				// 11
		"Marketing Revoked"				// 12
	  };
	
	public static final String TBODY = "//*[@id='BrandAccessoriesTable']/tbody";
	public static final String Variant = "//*[@id='BrandAccessoriesTable']/tbody/tr[1]/td[3]";
}
