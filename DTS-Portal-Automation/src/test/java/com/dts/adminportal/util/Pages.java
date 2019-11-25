package com.dts.adminportal.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import dts.com.adminportal.model.Constant;

public abstract class Pages {
	protected WebDriver driver;
	protected static Wait<WebDriver> wait;
	public void init() {
		driver = new FirefoxDriver();
		driver.get(Constant.siteBase);
	}

	public static Boolean waitForLoadElement(final String element,
			WebDriver driver) {
		wait = new WebDriverWait(driver, 30);
		return wait.until(new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.xpath(element)) != null;
			}
		});
	}

	public void close() {
		// TODO Auto-generated method stub
		driver.close();
	}
	
}
