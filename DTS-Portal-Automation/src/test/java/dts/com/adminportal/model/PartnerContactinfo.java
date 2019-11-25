package dts.com.adminportal.model;

import java.util.ArrayList;
import java.util.Hashtable;

public class PartnerContactinfo {
	public static final String  Current_Contact_Person = "//*[@id='old-user-action-header']";
	public static final String  Current_Contact_body = "//*[@id='old-user-action-body']";
	public static final String New_Contact_Persion = "//*[@id='new-user-action-header']";
	public static final String Actions = "//*[@id='create-new-user-action-header']";
	public static final String active_user_table = "//*[@id='AdminUserListTable']";
	
	public static final String First_name = "//*[@id='AdminUserListTable']/thead/tr/th[1]"; 
	public static final String Last_name = "//*[@id='AdminUserListTable']/thead/tr/th[2]"; 
	public static final String Phone = "//*[@id='AdminUserListTable']/thead/tr/th[3]"; 
	public static final String title = "//*[@id='AdminUserListTable']/thead/tr/th[4]"; 
	public static final String email = "//*[@id='AdminUserListTable']/thead/tr/th[5]"; 
	public static final Hashtable<String, String> gettableactive(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("firstname",First_name  );
		data.put("lastname",Last_name );
		data.put("phone",Phone  );
		data.put("title",title   );
		data.put("email",email  );
		
		return data;
	}
	
	//"//*[@id='lang-div-container']/div/select";
	//id('lang-div-container')/x:div[1]/x:select
	

	public static final ArrayList<String> getListXpath() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(Current_Contact_Person);
		arrayList.add(New_Contact_Persion);
		arrayList.add(Actions);
		arrayList.add(active_user_table);
		
		
		return arrayList;
	}
	
	public static final String requires[] = {
		"//*[@id='official-corp-name-msg']",
		
		
		};
	// 
	public static final String SAVE = "//*[@id='save-user-change']";
	public static final String CANCEL = "//*[@id='cancel-user-change']";
	public static final Hashtable<String, String> getAction(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("save", SAVE);
		data.put("cancel", CANCEL);
		
		return data;
	}
	
	
	
	
}
