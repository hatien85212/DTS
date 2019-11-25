package dts.com.adminportal.model;

import java.util.Hashtable;

public class PartnerEditBrands {
	public static final String  LANGUAGES = "//*[@id='lang-div-container']";
	public static final String  BRAND_NAME = "//*[@id='brandName']";
	public static final String LINE = "//*[@id='brandTagLine']";
	public static final String CONSUMER = "//*[@id='consumerAlias']";
	public static final String WEB_SITE = "//*[@id='webSite']";
	public static final String OVERVIEW =  "//*[@id='brandOverview']";
	public static final String COPYRIGHT =  "//*[@id='copyrightAndTrademarkNotice']"; 
	public static final String  SIZE_160x160 = "//*[@id='logo124Image']";
	public static final String  IMAGE_160x160 = "//*[@id='Chrysanthemum.jpg']";
	public static final String  SIZE_290X290 = "//*[@id='logo256Image']";
	public static final String  IMAGE_290x290 = "//*[@id='Desert.jpg']";
	public static final String  SIZE_664X664 = "//*[@id='logo512Image']";
	public static final String  IMAGE_664x664 = "//*[@id='Hydrangeas.jpg']";
	public static final Hashtable<String, String> getelement(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("Languages", LANGUAGES );
		data.put("Brand name",BRAND_NAME);
		data.put("Line",LINE  );
		data.put("Consumer",CONSUMER  );	
		data.put("Web Site",WEB_SITE  );
		data.put("Overview", OVERVIEW );
		data.put("Copyright",  COPYRIGHT );
		data.put("size 160x160", SIZE_160x160);
		data.put("size 290X290", SIZE_290X290);
		data.put("size 664X66", SIZE_664X664);
		
		return data;
	}
	public static final Hashtable<String, String> getImage160(){
		Hashtable<String, String> img160 = new Hashtable<String, String>();
		img160.put("Image160x160", IMAGE_160x160 );
		return img160;
	}
	public static final Hashtable<String, String> getImage290(){
		Hashtable<String, String> img290 = new Hashtable<String, String>();
		img290.put("Image290x290", IMAGE_290x290 );
		return img290;
	}
	public static final Hashtable<String, String> getImage664(){
		Hashtable<String, String> img664 = new Hashtable<String, String>();
		img664.put("Image290x290", IMAGE_664x664 );
		return img664;
	}
	
	public static final String requires[] = {
		"//*[@id='official-corp-name-msg']",
		};
	// 
	public static final String SAVE = "//*[@id='create-company-brand']";
	public static final String CANCEL = "//*[@id='cancel-company-brand']";
	public static final Hashtable<String, String> getAction(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("save", SAVE);
		data.put("cancel", CANCEL);
		
		return data;
	}
	
	public static final String IMG_NAME[]={
		"Chrysanthemum.jpg",
		"Desert.jpg",
		"Hydrangeas.jpg"
	};
	public static final String DELETE160 = "//*[@id='logo124Delete']";
	public static final String DELETE290 = "//*[@id='logo256Delete']";
	public static final String DELETE664 = "//*[@id='logo512Delete']";
	
}
