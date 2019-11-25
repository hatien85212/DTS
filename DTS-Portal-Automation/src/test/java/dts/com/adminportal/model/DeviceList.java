package dts.com.adminportal.model;

import java.util.ArrayList;

public class DeviceList {
	public static final String COMPANY_LABEL = "//*[@id='main-container']/div/div[1]/span[1]";
	public static final String COMPANY_LIST = "//*[@id='company-list']";
	public static final String BRAND_LABEL  = "//*[@id='main-container']/div/div[1]/span[2]";
	public static final String BRAND_LIST = "//*[@id='brand-list']";
	public static final String TYPE_LABEL = "//*[@id='main-container']/div/div[1]/span[3]";
	public static final String TYPE_LIST  = "//*[@id='type-list']";
	public static final String TABLE = "//*[@id='product_table']";
	public static final String THEAD = "//*[@id='product_table']/thead";
	public static final String TBODY = "//*[@id='product_table']/tbody";
	public static final String ACTION_MODULE = "//*[@class='create-new-company-catalog-action']";
	public static final String CREATE_NEW_DEVICE = "//*[@id='create-device']";
	public static final String FOOTER = "//*[@id='main-container']/footer/p";
	public static final ArrayList<String> getListXpath(){
		ArrayList<String> list = new ArrayList<String>();
		list.add(COMPANY_LABEL);
		list.add(COMPANY_LIST);
		list.add(BRAND_LABEL);
		list.add(BRAND_LIST);
		list.add(TYPE_LABEL);
		list.add(TYPE_LIST);
		list.add(TABLE);
		list.add(FOOTER);
		return list;
	}
	public static final String COMPANY_DEFAULT_FILTER = "All Companies";
	public static final String BRAND_DEFAULT_FILTER = "All Brands";
	public static final String TYPE_DEFAULT_FILTER = "All Types";
	
	public static final String brands[] = {
											"All Types",
											"Apps",
											"Devices"
										  };
	public static final String theads[] = {
											"Company", 
											"Brand", 
											"Product Name", 
											"Type", 
											"Published"
	};
	
	public static final String filter[] = {
		COMPANY_LIST,
		BRAND_LIST,
		TYPE_LIST
	};
	
	public static final int COMPANY_COLUMN = 1;
	public static final int BRAND_COLUMN = 2;
	public static final int TYPE_COLUMN = 4;
	public static final String  PRODUCT_TABLE_INFO = "//*[@id='product_table_info']";
}
