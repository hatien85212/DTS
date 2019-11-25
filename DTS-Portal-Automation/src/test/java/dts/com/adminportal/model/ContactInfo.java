package dts.com.adminportal.model;

import org.openqa.selenium.WebElement;

public class ContactInfo {
	public WebElement firstnamelink ;
	public WebElement lastnamelink;
	public String title;
	public String phone;
	public String email;
	public ContactInfo() {
		this.firstnamelink = null;
		this.lastnamelink = null;
		this.title = "";
		this.phone = "";
		this.email = "";
	}
}
