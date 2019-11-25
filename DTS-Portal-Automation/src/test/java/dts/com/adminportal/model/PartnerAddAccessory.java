package dts.com.adminportal.model;

import java.util.Hashtable;

public class PartnerAddAccessory extends AddAccessory{
	
	public static final Hashtable<String, String> getElementInfo(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("brand", BRAND);
		data.put("name", NAME);
		data.put("number", MODEL_NUMBER);
		data.put("upc", UPC);
		data.put("type", TYPE);
		data.put("noise cancelling", NOISE_CANCELLING);
		data.put("input specifications", INPUT_SPECIFICATIONS);
		data.put("date", DATE);
		data.put("aliases", ALIASES);
		data.put("description", DESCRIPTION);
		data.put("tuning", TUNING_FILE_CONTAINER);
		data.put("tuning rating", TUNING_RATING_DISABLE);
		data.put("image", IMAGE_CATALOG);
		data.put("marketing", MARKETING_MATERIAL_DIV);
		data.put("save", SAVE);
		data.put("cancel", CANCEL);
		return data;
	}
	public static final String language[] = {	
		"- Select -", 
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
