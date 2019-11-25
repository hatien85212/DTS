package dts.com.adminportal.model;


public class PartnerAddVariant extends AddVariant{
	public static final String NAME_ERR = "//*[@id='display-name-msg']";
	
	public static final String requires[] = {
		"//*[@id='company-select-msg']",
		"//*[@id='brand-select-msg']",
		"//*[@id='display-name-msg']",
		"//*[@id='model-number-msg']", // 3
		"//*[@id='upc-msg']",
		"//*[@id='model-type-msg']",
		"//*[@id='desc-name-msg']",
		"//*[@id='lang-div-container']/div[1]/span"
		};
	
	public static final String language[] = {	
		"-Select-", 
		"Chinese (Simplified)", 
		"Chinese (Traditional)",
		"French", 
		"German", 
		"Italian", 
		"Japanese", 
		"Korean", 
		"Russian", 
		"Spanish"
	  };
	
	
}
