package com.dts.adminportal.model;

import org.openqa.selenium.WebElement;

public class DTSSitePrivilege {
	public String value;
	public WebElement privileges;
	public WebElement notifications;
	public DTSSitePrivilege() {
		super();
		this.value = "";
		this.privileges = null;
		this.notifications = null;
	}
}
