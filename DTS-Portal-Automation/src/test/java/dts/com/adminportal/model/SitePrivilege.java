package dts.com.adminportal.model;

import org.openqa.selenium.WebElement;

public class SitePrivilege {
	public String value;
	public WebElement privileges;
	public WebElement brand;
	public WebElement notifications;
	public SitePrivilege() {
		super();
		this.value = "";
		this.privileges = null;
		this.brand = null;
		this.notifications = null;
	}
}
