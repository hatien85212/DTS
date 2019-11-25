
package dts.com.adminportal.model;

import java.util.ArrayList;

public class CompanyContact {
	public static final String CONTACT_TABLE = "//*[@id='AdminUserListTable']";
	public static final String CURRENT_CONTACT = "//*[@id='old-user-action-body']";
	public static final String NEW_CONTACT = "//*[@id='new-user-action-body']";
	
	public static final ArrayList<String> getElementInfo() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(CONTACT_TABLE);
		arrayList.add(CURRENT_CONTACT);
		arrayList.add(NEW_CONTACT);
		return arrayList;
	}
	
	public static final String SAVE = "//*[@id='save-user-change']";
	public static final String CANCEL = "//*[@id='cancel-user-change']";
}
