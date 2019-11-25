package com.dts.adminportal.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LinkTextElement extends BaseElement {

	public LinkTextElement(WebDriver driver, By searchBy) {
		super(driver, searchBy);
	}
	
	@Override
	public void click() {
		if (isReady) {
			addLog("Click on " + element);
			element.click();
		}
	}
}
