package dts.com.adminportal.model;

import java.util.ArrayList;
import java.util.Hashtable;

public class AddBrand {
	public static final String NAME = "//*[@id='brandName']";
	public static final String BRAND_TAG_LINE = "//*[@id='brandTagLine']";
	public static final String CONSUMER_ALIAS = "//*[@id='consumerAlias']";
	public static final String WEBSITE = "//*[@id='webSite']";
	public static final String BRAND_OVERVIEW = "//*[@id='brandOverview']";
	public static final String COPYRIGHT_AND_TRADEMARK_NOTICE = "//*[@id='copyrightAndTrademarkNotice']";
	public static final String  IMAGE250 = "//*[@id='logo124Image']";
	public static final String  IMAGE250_DISPLAY = "//*[@id='logo124ImageShow']//a[1]/img";
	public static final String  DELELE_IMAGE250 = "//*[@id='logo124Delete']";
	public static final String  IMAGE500 = "//*[@id='logo256Image']";
	public static final String  IMAGE500_DISPLAY = "//*[@id='logo256ImageShow']//a[1]/img";
	public static final String  DELELE_IMAGE500 = "//*[@id='logo256Delete']";
	public static final String  IMAGE1000 = "//*[@id='logo512Image']";
	public static final String  IMAGE1000_DISPLAY = "//*[@id='logo512ImageShow']//a[1]/img";
	public static final String  DELELE_IMAGE1000 = "//*[@id='logo512Delete']";
	public static final String  IMAGETABLE = "//*[@id='brandLogoTable']";
	public static final String LIGHTBOX_STYLE_IMAGE = "//img[@class = 'lb-image']";
	public static final String LIGHTBOX_CLOSE ="//*[@id='lightbox']/div[2]/div/div[2]/a";
	
	public static final ArrayList<String> getListAddBrand() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(NAME);
		arrayList.add(BRAND_TAG_LINE);
		arrayList.add(CONSUMER_ALIAS);
		arrayList.add(WEBSITE);
		arrayList.add(BRAND_OVERVIEW);
		arrayList.add(COPYRIGHT_AND_TRADEMARK_NOTICE);
		return arrayList;
	}
	public static final String SAVE = "//*[@id='create-company-brand']";
	public static final String CANCEL = "//*[@id='cancel-company-brand']";
	
	public static final Hashtable<String, String> getHash(){
		Hashtable<String, String> brand = new Hashtable<String, String>();
		brand.put("name", NAME);
		brand.put("tag", BRAND_TAG_LINE);
		brand.put("alias", CONSUMER_ALIAS);
		brand.put("website", WEBSITE);
		brand.put("overview", BRAND_OVERVIEW);
		brand.put("notice", COPYRIGHT_AND_TRADEMARK_NOTICE);
		brand.put("img1", IMAGE250);
		brand.put("img2", IMAGE500);
		brand.put("img3", IMAGE1000);
		brand.put("save", SAVE);
		brand.put("cancel", CANCEL);
		return brand;
	}
	
	public static final String IMG_NAME[]={
		"Chrysanthemum.jpg",
		"Desert.jpg",
		"Hydrangeas.jpg"
	};
}
