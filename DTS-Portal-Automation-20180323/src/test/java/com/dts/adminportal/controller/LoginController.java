package com.dts.adminportal.controller;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;

import com.dts.adminportal.model.PageLogin;
import com.dts.adminportal.wrappers.BaseElement;
import com.dts.adminportal.wrappers.ButtonElement;
import com.dts.adminportal.wrappers.TextBoxElement;

public class LoginController extends BaseController {
	private String loggedinUser;
	private boolean isLogedIn;
	
	public LoginController(WebDriver driver) {
		super(driver);
	}
	
	public void clickOnForgotSignInInfo() {		
		addLog("Click forgot password link.");
		new BaseElement(driver, By.xpath(PageLogin.xpath_forgot_sign_in_info)).click();
	}
	
	public boolean isDirectedToForgotPage(){
		if(!driver.getCurrentUrl().contains(PageLogin.urlForgot)){		
			return false;
		}
		return true;		
	}
	
	public boolean isInvalidEmailOrPassword(){
		return driver.getPageSource().contains(PageLogin.loginError);
	}
	
	public boolean isLoginSuccessful(String userName){
		UsersController userControl = new UsersController(driver);
		if(userName.equalsIgnoreCase(userControl.getUserAccount())){
			return true;
		}
		return false;
	}
	
	public boolean login(String username, String password) {
		if (!checkLogIn(username)) {
			boolean isPass = false;
			try {
				waitForAjax();
				addLog("Enter username: " + username);
				new TextBoxElement(driver, By.xpath(PageLogin.xpath_username)).enterText(username);			
				
				addLog("Enter password: " + password);
				new TextBoxElement(driver, By.xpath(PageLogin.xpath_password)).enterText(password);			
				
				addLog("Click login : " + PageLogin.xpath_login_btn);			
				new ButtonElement(driver, By.xpath(PageLogin.xpath_login_btn)).click();
				
				addLog("Wait for loading");
				if (isInvalidEmailOrPassword()) {			
					return false;
				}
				waitForAjax();
				if (isLoginSuccessful(username)) {
					this.isLogedIn = true;
					this.loggedinUser = username;
					isPass = true;
					addLog("On Home page");
				}
			} catch (NoSuchElementException e) {			
				addLog("NoSuchElementException on Login");
			} catch (TimeoutException e) {
				addErrorLog("Page load time out exception");
				addLog("Refresh browser");
				driver.navigate().refresh();
			} catch (UnreachableBrowserException e) {			
				addErrorLog("Unreacheable browser exception");
				addLog("Refresh browser");
				driver.navigate().refresh();
			}
			return isPass;
		}
		return true;
	}
	
	private boolean checkLogIn(String username) {
		if (!this.isLogedIn) {
			return false;
		}
		if (!username.equals(this.loggedinUser)) {
			logout();
			return false;
		}
		return true;
	}
}
