package com.dts.adminportal.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.dts.adminportal.util.Result;

import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.Xpath;

public class LoginController {
	protected WebDriver driver;
	protected URI siteBase;
	protected Wait<WebDriver> wait;
	Result result = new Result();
	protected String usernameXpath = Xpath.username;
	protected String passwordXpath = Xpath.password;
	protected String btnLoginXpath = Xpath.login;
	protected String btnLogoutXpath = Xpath.btnLogout;
	protected String btnSubmitLogoutXpath = Xpath.submitLogout;
	protected String lblUser = Xpath.lbLogout;

	public LoginController(WebDriver driver, URI siteBase) {
		super();
		PageFactory.initElements(driver, this);
		this.driver = driver;
		this.siteBase = siteBase;
		wait = new WebDriverWait(driver, Constant.TIME_WAIT);
	}

	public WebDriver submit(String user, String pass) {
		driver.findElement(By.xpath(Xpath.username)).sendKeys(user);
		driver.findElement(By.xpath(Xpath.password)).sendKeys(pass);
		driver.findElement(By.xpath(Xpath.login)).click();
		
		return driver;
	}

	public WebDriver getDrv() {
		return driver;
	}

	public URI getSiteBase() {
		return siteBase;
	}

	public Boolean waitForLoadElement(final String element) {
		waitForAjax();
		System.err.println("On wait for load...");
		try {
			// wait element display
			if(driver.findElements(By.xpath(element)).size() > 0){
				result.addLog("Element displayed : "+element);
				return true;
			}else {
				System.err.println("Element doesn't existed : "+element);
				return false;
			}
		} catch (NoClassDefFoundError e) {
			e.printStackTrace();
		} 
		return false;
	}
	public Boolean waitElement(String xpath){
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		return element.isEnabled();
	}
	public Result clickAndWaitElementDisplayed(String xpath, String element) {
		try {
			result.setResult("Pass");
			waitForLoadElement(xpath);
			result.addLog("Click "+xpath);
			driver.findElement(By.xpath(xpath)).click();
			waitForLoadElement(element);
			Thread.sleep(300);
		} catch (NoSuchElementException e) {
			result.addLog("No element "+element);
			result.setResult("Fail");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}
	public Result getForgotLink() {
		result.addLog("Click forgot password link.");
		driver.findElement(By.xpath(Xpath.forgot)).click();
		if(!driver.getCurrentUrl().contains(Constant.urlForgot)){
			result.setResult("Fail");
		}else{
			result.setResult("Pass");
		}
		return result;
	}
	public Boolean checkLoginSusscess() {
		if (driver.findElement(By.xpath(Xpath.linkHome)).isDisplayed()) {
			return true;
		}
		return false;
	}

	public Result getUserNameDisplayed(String xpath, String username) {
		String getName =  driver.findElement(By.xpath(xpath)).getText();
		result.addLog("Username displayed is : "+getName);
		if(getName.equals(username)){
			result.setResult("Pass");
		}else {
			result.setResult("Fail");
		}
		return result;
	}

	public boolean checkListItemsLogout(String xpath,
			ArrayList<String> arrayList) {
		WebElement footer = driver.findElement(By.xpath(xpath));
		List<WebElement> columns = footer.findElements(By.tagName("a"));
		for (WebElement column : columns) {
			if (arrayList.contains(column.getAttribute("text"))) {
				arrayList.remove(column.getAttribute("text"));
			} else {
				System.out.println("fail " + column.getAttribute("text"));
			}
		}
		if (arrayList.size() > 0) {
			return false;
		}
		return true;
	}

	public Result login(String username, String password) {
		try {
			waitForAjax();
			result.addLog("Enter username: " + username);
			driver.findElement(By.xpath(usernameXpath)).sendKeys(username);
			result.addLog("Enter password: " + password);
			driver.findElement(By.xpath(passwordXpath)).sendKeys(password);
			result.addLog("Click login : " + btnLoginXpath);
			driver.findElement(By.xpath(btnLoginXpath)).click();
			result.addLog("Wait for loading");
			if (driver.getPageSource().contains(Constant.loginError)) {
				result.setResult("Fail");
				return result;
			}
			waitForAjax();
			if (driver.findElements(By.xpath(Xpath.LOADING)).size() > 0) {
				result.setResult("Pass");
				result.addLog("On Home page");
			} else {
				result.setResult("Fail");
			}
		} catch (NoSuchElementException e) {
			result.setResult("Fail");
			result.addLog("NoSuchElementException on Login");
		} catch (TimeoutException e) {
			result.setResult("Fail");
			result.addErrorLog("Page load time out exception");
			result.addLog("Refresh browser");
			driver.navigate().refresh();
		} catch (UnreachableBrowserException e) {
			result.setResult("Fail");
			result.addErrorLog("Unreacheable browser exception");
			result.addLog("Refresh browser");
			driver.navigate().refresh();
		}
		return result;
	}

	public Result logout() {
		// click drop-down logout
		result.addLog("click drop-down logout");
		driver.findElement(By.xpath(btnLogoutXpath)).click();
		// wait for element clickable
		result.addLog("Wait for drop-down clickable");
		try {
			Boolean isHome = waitForLoadElement(btnSubmitLogoutXpath);
			if (isHome == true) {
				result.addLog("Click logout");
				driver.findElement(By.xpath(btnSubmitLogoutXpath)).click();
			}
		} catch (ElementNotVisibleException e) {
			result.addLog(e.getMessage());
		}
		try {
			Boolean isLoginPage = waitForLoadElement(usernameXpath);
			if (isLoginPage == true) {
				result.addLog("Logout successful");
				result.setResult("Pass");
			}
		} catch (ElementNotVisibleException e) {
			result.addLog(e.getMessage());
			result.setResult("Fail");
		}
		return result;
	}
	public Result checkUserManager(String xpath, String xpathItemLogout, ArrayList<String> listManager){
		try {
			Boolean isHome = waitForLoadElement(xpath);
			if (isHome == true) {
				result.addLog("On Home page");
//				driver.findElement(By.xpath(xpath)).click();
			}
		} catch (ElementNotVisibleException e) {
			result.addLog(e.getMessage());
		}
		Boolean isCorrectListUserManager = checkListItemsLogout(xpathItemLogout, listManager);
		if(isCorrectListUserManager){
			result.setResult("Pass");
		}else {
			result.setResult("Fail");
		}
		return result;
	}

	public String getUserNameDisplayed() {
		return driver.findElement(By.xpath(lblUser)).getText();
	}

	public Result verifyLoginUI(ArrayList<String> arrayList) {
		// check login form
		result = existsElement(arrayList);
		return result;
	}
	public Result existsElement(ArrayList<String> elements){
		result.setResult("Pass");
		result.addLog("Check all element by ArrayList");
		waitForAjax();
		for(String element : elements){
			Boolean isPresent = driver.findElements(By.xpath(element)).size() > 0;
			if(!isPresent){
				result.addLog("Element " + element + " doesn't existed!");
				result.setResult("Fail");
			}else{
				result.addLog("Element: " + element + " displayed!");
			}
		}
		return result;
	}
	public Result existsElement(Hashtable<String, String> elementsInfo) {
		waitForAjax();
		result.setResult("Pass");
		result.addLog("Check all element by HashTable");
		if(elementsInfo.size() == 0){
			System.err.println("Element empty");
			return result;
		}
		for(String element : elementsInfo.keySet()){
			Boolean isPresent = driver.findElements(By.xpath(elementsInfo.get(element))).size() > 0;
			if(!isPresent){
				result.addLog("Element " + element + " doesn't existed!");
				result.setResult("Fail");
			}else{
				result.addLog("Element "+ elementsInfo.get(element) +" displayed!");
			}
		}
		result.addLog("result is :" + result.getResult());
		return result;
	}

	public void waitForAjax() {
		System.err.println("Checking active ajax calls by calling jquery.active ...");
		try {
			if (driver instanceof JavascriptExecutor) {
				JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
				for (int i = 0; i < Constant.TIME_WAIT; i++) {
					Object numberOfAjaxConnections = jsDriver
							.executeScript("return jQuery.active");
					// return should be a number
					if (numberOfAjaxConnections instanceof Long) {
						Long n = (Long) numberOfAjaxConnections;
						System.err.println("Number of active jquery ajax calls: " + n);
						if (n.longValue() == 0L)
							break;
					}
					Thread.sleep(1000);
				}
			} else {
				System.err.println("Web driver: " + driver
						+ " cannot execute javascript");
			}
		} catch (InterruptedException e) {
			System.err.println(e);
		}
	}

	public void type(String xpath, String data) {
		try {
			waitForAjax();
			driver.findElement(By.xpath(xpath)).clear();
			result.addLog("change data : "+data);
			driver.findElement(By.xpath(xpath)).sendKeys(data);
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at editData :  "+ xpath);
		}
		
	}

	public void click(String xpath) {
		waitForAjax();
		try {
			result.addLog("Click : " + xpath);
			driver.findElement(By.xpath(xpath)).click();
			waitForAjax();
			try {
				if (driver.findElement(By.xpath(AddUser.BRAND_PRIVILEGES))
						.isDisplayed()) {
					Thread.sleep(3000);
					waitForAjax();
				}
			} catch (NoSuchElementException e) {
			}
		} catch (NoSuchElementException e) {
			result.addLog("No element exception : " + xpath);
			Assert.assertTrue(false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getTextByXpath(String xpath) {
		String text = "";
		try {
			text = driver.findElement(By.xpath(xpath)).getText();
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at getTextByXpath :  "+ xpath);
		}
		return text;
	}

	public void switchWindow() {
		try {
	           Set<String> handles = driver.getWindowHandles();
	           String newTab = null;
	           for(String handle : handles){
	        	   newTab = handle;
	           }
	           driver.switchTo().window(newTab);
	        } catch( Exception e ) {
	            result.addLog(e.getMessage());
	        }
	}

	public String getCurrentURL() {
		result.addLog(driver.getCurrentUrl());
		return driver.getCurrentUrl();
	}
	public String getAttributesOfElement(String xpath, String att) {
		String displayResult = "";
		try{
			WebElement webElement = driver.findElement(By.xpath(xpath));
			displayResult = webElement.getAttribute(att);
		}catch(NoSuchElementException e){
			
		}
		return displayResult;
	}
}
