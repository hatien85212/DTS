package com.dts.adminportal.controller;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.testng.Assert;

import autoitx4java.AutoItX;

import com.dts.adminportal.util.DTSUtil;
import com.dts.adminportal.util.DateUtil;
import com.dts.adminportal.util.DbHandler;
import com.dts.adminportal.util.FileUtil;
import com.dts.adminportal.util.MailUtil;
import com.dts.adminportal.util.Result;
import com.dts.adminportal.util.StringUtilDts;
import com.jacob.com.LibraryLoader;

import dts.com.adminportal.model.AccessoryInfo;
import dts.com.adminportal.model.AccessoryMain;
import dts.com.adminportal.model.AddAccessory;
import dts.com.adminportal.model.AddCompany;
import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.AddVariant;
import dts.com.adminportal.model.AudioRoutes;
import dts.com.adminportal.model.AudioTuning;
import dts.com.adminportal.model.BrandInfo;
import dts.com.adminportal.model.CompanyInfo;
import dts.com.adminportal.model.Constant;
import dts.com.adminportal.model.ContactInfo;
import dts.com.adminportal.model.DTSSitePrivilege;
import dts.com.adminportal.model.DataPartnerAccessoriesTable;
import dts.com.adminportal.model.DeviceEdit;
import dts.com.adminportal.model.DeviceInfo;
import dts.com.adminportal.model.DeviceList;
import dts.com.adminportal.model.InputSpecifications;
import dts.com.adminportal.model.LanguagePackage;
import dts.com.adminportal.model.PartnerContact;
import dts.com.adminportal.model.PartnerHomePage;
import dts.com.adminportal.model.PartnerUserInfo;
import dts.com.adminportal.model.PromotionInfo;
import dts.com.adminportal.model.PromotionList;
import dts.com.adminportal.model.PromotionPackage;
import dts.com.adminportal.model.SitePrivilege;
import dts.com.adminportal.model.TuningFile;
import dts.com.adminportal.model.UserInfo;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.UsersList;
import dts.com.adminportal.model.VariantInfo;
import dts.com.adminportal.model.XPartnerShipPackage;
import dts.com.adminportal.model.Xpath;

public class UsersController extends LoginController {
	protected Select select;
	protected String xpathListItemPartner = Xpath.dropdownMenuInnerSelectpicker;
	public AutoItX AutoItX;

	public UsersController(WebDriver driver, URI siteBase) {
		super(driver, siteBase);
		String jacobDllVersionToUse = System.getProperty("sun.arch.data.model");
		if (jacobDllVersionToUse.contains("32")) {
			jacobDllVersionToUse = "jacob-1.18-M2-x86.dll";
		} else {
			jacobDllVersionToUse = "jacob-1.18-M2-x64.dll";
		}
		File file = new File("lib/jacob/jacobm2/1.0", jacobDllVersionToUse);
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
		AutoItX = new AutoItX();
	}

	/**
	 * Select or deselect brand privilege in table 
	 * if select is true: uncheck all brand privilege except the given privilege 
	 * if select is false: check all brand privilege except the given privilege
	 * @param privilege String
	 * @param brandPrivilege String
	 * @param select boolean
	 * @return Boolean
	 */
	public Boolean selectBrandPrivilegeInTable(String privilege, String brandPrivilege, boolean select) {
		try {
			// Get table
			WebElement table = driver.findElement(By.tagName("tbody"));
			// Get all rows
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if (columns.get(0).getText().contains(privilege)) {
					// Privilege found
					WebElement brand_groupbox = columns.get(1).findElement(By.tagName("button"));
					// Click on brand_groupbox
					brand_groupbox.click();
					waitForAjax();
					List<WebElement> brand_privileges = columns.get(1).findElement(By.tagName("ul"))
							.findElements(By.tagName("li"));
					// Uncheck all brand privileges
					WebElement AllBrandPrivilege = brand_privileges.get(0).findElement(By.tagName("input"));
					Boolean isAllBrandChecked = AllBrandPrivilege.isSelected();
					if (isAllBrandChecked) {
						AllBrandPrivilege.click();
						waitForAjax();
					} else {
						AllBrandPrivilege.click();
						waitForAjax();
						AllBrandPrivilege.click();
						waitForAjax();
					}
					if (!select) {
						AllBrandPrivilege.click();
						waitForAjax();
					}
					// Select brand privilege
					for (WebElement brand_privilege : brand_privileges) {
						if (brand_privilege.getText().contains(brandPrivilege)) {
							result.addLog("Select brand privilege: " + brandPrivilege);
							brand_privilege.findElement(By.tagName("input")).click();
							waitForAjax();
							brand_groupbox.click();
							waitForAjax();
							return true;
						}
					}
					result.addLog("Brand privilege not found");
					return false;
				}
			}
			result.addLog("Site privilege not found");
			return false;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElement is present");
			return false;
		}
	}

	/**
	 * Select or deselect brand privilege 
	 * if select is true: uncheck all brand privilege except the given privilege 
	 * if select is false: check all brand privilege except the given privilege
	 * @param privilege String
	 * @param brandPrivilege String
	 * @param select boolean
	 * @return Boolean
	 */
	public Boolean selectBrandPrivilege(String xpath, String brandPrivilege, boolean select) {
		try {
			// Get brand privileges groupbox
			WebElement brand_groupbox = driver.findElement(By.xpath(xpath)).findElement(By.tagName("button"));
			// Click on brand_groupbox
			brand_groupbox.click();
			waitForAjax();
			List<WebElement> brand_privileges = driver.findElement(By.xpath(xpath)).findElement(By.tagName("ul"))
					.findElements(By.tagName("li"));
			// Uncheck all privileges
			WebElement AllBrandPrivilege = brand_privileges.get(0).findElement(By.tagName("input"));
			Boolean isAllBrandChecked = AllBrandPrivilege.isSelected();
			if (isAllBrandChecked) {
				AllBrandPrivilege.click();
				waitForAjax();
			} else {
				AllBrandPrivilege.click();
				waitForAjax();
				AllBrandPrivilege.click();
				waitForAjax();
			}
			if (!select) {
				AllBrandPrivilege.click();
				waitForAjax();
			}
			// Select brand privilege
			for (WebElement brand_privilege : brand_privileges) {
				if (brand_privilege.getText().contains(brandPrivilege)) {
					result.addLog("Select brand privilege: " + brandPrivilege);
					brand_privilege.findElement(By.tagName("input")).click();
					waitForAjax();
					brand_groupbox.click();
					waitForAjax();
					return true;
				}
			}
			result.addLog("Brand privilege not found");
			return false;

		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElement: " + xpath);
			return false;
		}
	}

}