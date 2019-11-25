package com.dts.adminportal.model;

import org.openqa.selenium.WebElement;

public class TuningFile {
	public String name;
	public String href;
	public String link;
	public String btnDelete;
	public WebElement delete;
	public WebElement download;
	public TuningFile() {
		super();
		this.name = "";
		this.href = "";
		this.link = "";
		this.btnDelete = "";
		this.delete = null;
		this.download = null;
	}
}
