package com.dts.adminportal.model;

import org.openqa.selenium.WebElement;

public class AudioTuning {
	public String name;
	public WebElement link;
	public WebElement delete;
	public AudioTuning() {
		super();
		this.name = "";
		this.link = null;
		this.delete = null;
	}
}
