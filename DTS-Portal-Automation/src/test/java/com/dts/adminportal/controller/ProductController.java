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


public class ProductController extends LoginController {
	protected Select select;
	protected String xpathListItemPartner = Xpath.dropdownMenuInnerSelectpicker;
	public AutoItX AutoItX;
	
	public ProductController(WebDriver driver, URI siteBase) {
		super(driver, siteBase);
		String jacobDllVersionToUse = System.getProperty("sun.arch.data.model");
		if (jacobDllVersionToUse.contains("32")){
		jacobDllVersionToUse = "jacob-1.18-M2-x86.dll";
		} 
		else {
		jacobDllVersionToUse = "jacob-1.18-M2-x64.dll";
		}
		File file = new File("lib/jacob/jacobm2/1.0", jacobDllVersionToUse);
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
		AutoItX = new AutoItX();
	}
	
	public Result addAccessories(Hashtable<String, String> hashXpath,
			Hashtable<String, String> data) {
		try {
			waitForAjax();
			if (isElementPresent(hashXpath.get("id"))) {
				editData(hashXpath.get("id"), data.get("id"));
			}
			if (isElementPresent(hashXpath.get("company"))) {
				selectOptionByName(hashXpath.get("company"),
						data.get("company"));
			}
			if (isElementPresent(hashXpath.get("brand"))) {
				selectOptionByName(hashXpath.get("brand"), data.get("brand"));
			}
			editData(hashXpath.get("name"), data.get("name"));
			editData(hashXpath.get("number"), data.get("number"));
			editData(hashXpath.get("ean"), data.get("ean"));
			editData(hashXpath.get("upc"), data.get("upc"));
			selectOptionByName(hashXpath.get("type"), data.get("type"));
			selectInputChannel(hashXpath.get("wired"), data.get("wired"));
			selectInputChannel(hashXpath.get("bluetooth"),data.get("bluetooth"));
			editData(hashXpath.get("description"), data.get("description"));
			// upload tuning file
			uploadFile(hashXpath.get("add tunning"), data.get("add tunning"));
			// Select tuning rating
			if (isElementPresent(hashXpath.get("tuning rating"))
					&& data.get("tuning rating") != null) {
				selectOptionByName(hashXpath.get("tuning rating"),
						data.get("tuning rating"));
			}
			// upload image file
			uploadFile(hashXpath.get("img1"), data.get("img1"));
			uploadFile(hashXpath.get("img2"), data.get("img2"));
			uploadFile(hashXpath.get("img3"), data.get("img3"));
			// upload marketing file
			if (data.get("add marketing") != ""
					&& data.get("add marketing") != null) {
				uploadFile(hashXpath.get("add marketing"),
						data.get("add marketing"));
				selectFirstOption(hashXpath.get("marketing meterial type"));

			}
			// save
			if (data.containsKey("save")) {
				click(hashXpath.get("save"));
			}
		} catch (NoSuchElementException e) {
			result.addLog(e.toString());
			result.setResult("Fail");
		}
		return result;
	}
	
	public Boolean addVariant(Hashtable<String, String> hashXpath,
			Hashtable<String, String> data) {
		try {
			waitForAjax();
			if(isElementPresent(hashXpath.get("id"))){
				editData(hashXpath.get("id"), data.get("id"));
			}
			editData(hashXpath.get("name"), data.get("name"));
			editData(hashXpath.get("ean"), data.get("ean"));
			editData(hashXpath.get("upc"), data.get("upc"));
			editData(hashXpath.get("descriptor"), data.get("descriptor"));
			// Upload tuning
			if (data.get("add tunning") != null) {
				if (data.get("add tunning") == "use parent") {
					click(AddVariant.PARENT_TUNNING_CHECKBOX);
				} else {
					click(AddVariant.UPLOAD_CUSTOM_TUNNING);
					uploadFile(hashXpath.get("add tunning"),
							data.get("add tunning"));
				}
			}
			// Select tuning rating
			if(isElementPresent(hashXpath.get("tuning rating")) && data.get("tuning rating") != null){
				selectOptionByName(hashXpath.get("tuning rating"), data.get("tuning rating"));
			}
			// Upload image
			if (data.get("img1") != null) {
				if (data.get("img1") == "use parent") {
					click(AddVariant.PARENT_IMAGE_CHECKBOX);
				} else {
					click(AddVariant.UPLOAD_CUSTOM_IMAGE);
					uploadFile(hashXpath.get("img1"), data.get("img1"));
					uploadFile(hashXpath.get("img2"), data.get("img2"));
					uploadFile(hashXpath.get("img3"), data.get("img3"));
				}
			}
			// Upload marketing
			if (data.get("add marketing") != ""
					&& data.get("add marketing") != null) {
				uploadFile(hashXpath.get("add marketing"),
						data.get("add marketing"));
				selectFirstOption(hashXpath.get("marketing meterial type"));
			}
			// Click save link
			if(data.containsKey("save")){
				click(hashXpath.get("save"));
			}
			return true;
		} catch (NoSuchElementException e) {
			result.addLog(e.toString());
			return false;
		}
	}
	
	public Boolean selectProductByName(String name) {
		try {
			int size = getPageSize();
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if (columns.get(1).getText().equals(name)) {
						result.addLog("Select accessory : " + name);
						columns.get(1).findElement(By.tagName("a")).click();
						waitForAjax();
						return true;
					}
				}
				clickText("Next");
			}
			result.addErrorLog("Accessory: " + name + " not found");
		} catch (NoSuchElementException e) {
			return false;
		}
		return false;
	}
	
	public Integer getProductIdByName(String name){
		try {
			int size = getPageSize();
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if (columns.get(1).getText().equals(name)) {
						result.addLog("Select accessory : " + name);
						String id = columns.get(1).findElement(By.tagName("a")).getAttribute("value");
						System.out.println(id);
						return Integer.valueOf(id);
					}
				}
				clickText("Next");
			}
			result.addErrorLog("Accessory: " + name + " not found");
		} catch (NoSuchElementException e) {
			result.addErrorLog("Product: " + name + " not found");
			return 0;
		}
		return 0;
	}
	
	public Boolean selectVariantbyName(String name) {
		try {
			// Get the variant container
			WebElement model_variant = driver.findElement(By.xpath(AccessoryInfo.MODEL_VARIANTS)).findElement(By.tagName("span"));
			// Get all variant
			List<WebElement> variants = model_variant.findElements(By.tagName("a"));
			for (WebElement variant : variants) {
				// Get the text from link
				if (variant.getText().equals(name)) {
					result.addLog("Select variant: " + name);
					variant.click();
					waitForAjax();
					return true;
				}
			}
			result.addLog("Variant: " + name + " not found");
			return false;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException: " + AccessoryInfo.MODEL_VARIANTS);
			return false;
		}
	}
	
	public Integer getVariantIdbyName(String name) {
		try {
			// Get the variant container
			WebElement model_variant = driver.findElement(By.xpath(AccessoryInfo.MODEL_VARIANTS)).findElement(By.tagName("span"));
			// Get all variant
			List<WebElement> variants = model_variant.findElements(By
					.tagName("a"));
			for (WebElement variant : variants) {
				// Get the text from link
				System.out.println(variant.getText());
				if (variant.getText().contains(name)) {
					result.addLog("Select variant: " + name);
					String variantId = variant.getAttribute("id");
					System.out.println(variantId);
					Integer result = Integer.valueOf(variantId.split(",")[0]); 
					System.out.println(result);
					return result;
				}
			}
			result.addLog("Variant: " + name + " not found");
			return 0;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException: " + AccessoryInfo.MODEL_VARIANTS);
			return 0;
		}
	}

	public boolean isLinkExist(String Xpath) {
		try {
			WebElement control = driver.findElement(By.xpath(Xpath));
			String isHref = control.getAttribute("href");
			if (isHref != null) {
				return true;
			}
		} catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException" + Xpath);
		}
		return false;
	}
	
	public void editData(String editXpath, String data) {
		if (data != null) {
			try {
				waitForAjax();
				driver.findElement(By.xpath(editXpath)).clear();
				result.addLog("change data : " + data);
				driver.findElement(By.xpath(editXpath)).sendKeys(data);
				waitForAjax();
				// Work around due to issue PDPP-836
				try {
					if (driver.findElement(By.xpath(AddUser.BRAND_PRIVILEGES)).isDisplayed()) {
						if (driver.findElement(By.xpath(AddUser.BRAND_PRIVILEGES))
								.findElement(By.tagName("button")).getText()
								.equals("None selected")) {
							selectBrandPrivilege(AddUser.BRAND_PRIVILEGES,"All brands");
						}
					}
				} catch (NoSuchElementException e) {
				}
				// End work around
			} catch (NoSuchElementException e) {
				result.addLog("NoSuchElementException at editData :  "
						+ editXpath);
			}
		}
	}

	public Boolean uploadFile(String xpath, String fileName) {
		if (fileName == "" | fileName == null) {
			return false;
		}
		try {
			// Click on upload link
			WebElement webElement = driver.findElement(By.xpath(xpath));
			webElement.click();
			// Init data
			String uri = System.getProperty("user.dir") + "\\asset\\";
			// Upload file
			String filePath = uri + fileName;
			result.addLog("Upload file : " + filePath);
			String uploadWindow = "File Upload";
			AutoItX.winWait(uploadWindow, "", 10);
			Thread.sleep(1000);
			AutoItX.winActivate(uploadWindow);
			AutoItX.ControlSetText(uploadWindow, "", "[Class:Edit]", filePath);
			Thread.sleep(1000);
			AutoItX.controlClick(uploadWindow, "", "[Text:&Open]");
			Thread.sleep(2000);
			if(AutoItX.winExists(uploadWindow)){
				result.addLog("File not found or not invalid");
				String winID = AutoItX.winGetHandle(uploadWindow);
				AutoItX.controlClick("[Handle:" + winID + "]", "", "[Text:OK]");
				Thread.sleep(1000);
				AutoItX.controlClick(uploadWindow, "", "[Text:Cancel]");
				Assert.assertTrue(false);
			}
			waitForAjax();
			return true;
		} catch (NoSuchElementException e) {
			result.addLog("No element exception: " + xpath);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean uploadFileInterupt(String xpath, String fileName) {
		if (fileName == "" | fileName == null) {
			return false;
		}
		try {
			// Move mouse to Upload button
			new Actions(driver).moveToElement(driver.findElement(By.xpath(AddAccessory.TUNING_DRAG_DROP_AREA))).perform();
			// Click on upload link
			WebElement webElement = driver.findElement(By.xpath(xpath));
			webElement.click();
			// Init data
			String uri = System.getProperty("user.dir") + "\\asset\\";
			// Upload file
			String filePath = uri + fileName;
			result.addLog("Upload file : " + filePath);
			String uploadWindow = "File Upload";
			AutoItX.winWait(uploadWindow, "", 10);
			Thread.sleep(1000);
			AutoItX.winActivate(uploadWindow);
			AutoItX.ControlSetText(uploadWindow, "", "[Class:Edit]", filePath);
			Thread.sleep(1000);
			AutoItX.controlClick(uploadWindow, "", "[Text:&Open]");
			return true;
		} catch (NoSuchElementException e) {
			result.addLog("No element exception: " + xpath);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void editData(WebElement element, String data) {
		try {
			waitForAjax();
			element.clear();
			result.addLog("change data : " + data);
			element.sendKeys(data);
			waitForAjax();
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at editData :  " + element);
		}
	}
	
	public Result selectOptionByName(String xpath, String type) {
		if (type != "" && type != null) {
			try {
				waitForAjax();
				WebElement dropDownListBox = driver
						.findElement(By.xpath(xpath));
				Select clickThis = new Select(dropDownListBox);
				result.addLog("click :" + type);
				clickThis.selectByVisibleText(type);
				// wait data loading
				waitForAjax();
			} catch (NoSuchElementException e) {
				result.addLog("No element exception: " + xpath);
				result.setResult("Fail");
			}
		}
		return result;
	}

	public String clickOptionByIndex(String xpath, int indexnumber) {
		try {
			waitForAjax();
			WebElement dropDownListBox = driver.findElement(By.xpath(xpath));
			Select clickThis = new Select(dropDownListBox);
			System.out.print(clickThis);
			result.addLog("click : item index " + indexnumber);
			clickThis.selectByIndex(indexnumber);
			String textSelected = getItemSelected(xpath);
			result.addLog("click : " + textSelected);
			// wait data loading
			waitForAjax();
			return textSelected;
		} catch (NoSuchElementException e) {
			result.setResult("Fail");
		}
		return "";
	}
	
	public String getItemSelected(String xpath) {
		return readField(xpath);
	}
	
	public String readField(String xpath) {
		try {
			waitForAjax();
			WebElement footer = driver.findElement(By.xpath(xpath));
			List<WebElement> columns = footer
					.findElements(By.tagName("option"));
			for (WebElement column : columns) {
				if (column.isSelected()) {
					String selected = column.getText();
					result.addLog("Item selected : " + selected);
					return selected;
				}
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException at readField : " + xpath);
		}
		return "";
	}
	
	public Boolean selectBrandPrivilegeInTable(String privilege, String brandPrivilege){
		try{
		// Get table	
		WebElement table = driver.findElement(By.tagName("tbody"));
		// Get all rows
		List<WebElement> rows = table.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if (columns.get(0).getText().contains(privilege)) {
					// Privilege found
					WebElement brand_groupbox = columns.get(1).findElement(
							By.tagName("button"));
					// Click on brand_groupbox
					brand_groupbox.click();
					waitForAjax();
					List<WebElement> brand_privileges = columns.get(1)
							.findElement(By.tagName("ul"))
							.findElements(By.tagName("li"));
					// Uncheck all brand privileges
					WebElement AllBrandPrivilege = brand_privileges.get(0)
							.findElement(By.tagName("input"));
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
					// Select brand privilege
					for (WebElement brand_privilege : brand_privileges) {
						if (brand_privilege.getText().contains(brandPrivilege)) {
							result.addLog("Select brand privilege: "
									+ brandPrivilege);
							brand_privilege.findElement(By.tagName("input"))
									.click();
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
	
	public Boolean selectBrandPrivilege(String xpath, String brandPrivilege) {
		try {
			// Get brand privileges groupbox
			WebElement brand_groupbox = driver.findElement(By.xpath(xpath)).findElement(
					By.tagName("button"));
			// Click on brand_groupbox
			brand_groupbox.click();
			waitForAjax();
			List<WebElement> brand_privileges = driver.findElement(By.xpath(xpath)).findElement(By.tagName("ul"))
					.findElements(By.tagName("li"));
			// Uncheck all privileges
			WebElement AllBrandPrivilege = brand_privileges.get(0).findElement(By.tagName("input"));
			Boolean isAllBrandChecked = AllBrandPrivilege.isSelected();
			if(isAllBrandChecked){
				AllBrandPrivilege.click();
				waitForAjax();
			}else{
				AllBrandPrivilege.click();
				waitForAjax();
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
	
	public Boolean isElementPresent(String xpath) {
		try {
			Boolean isDisplayed = driver.findElement(By.xpath(xpath))
					.isDisplayed();
			if (isDisplayed) {
				result.addLog("Element displayed : " + xpath);
				return true;
			} else {
				result.addLog("Element doesn't existed : " + xpath);
				return false;
			}
		} catch (NoSuchElementException e) {
			result.addLog("Element doesn't existed : " + xpath);
			return false;
		}
	}
	
	public Boolean selectFirstOption(String xpath) {
		try {
			waitForAjax();
			WebElement dropDownListBox = driver.findElement(By.xpath(xpath));
			Select clickThis = new Select(dropDownListBox);
			result.addLog("click  index 0");
			clickThis.selectByIndex(1);
			// wait data loading
			waitForAjax();
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}
	
	public void selectInputChannel(String xpath, String channel) {

		try {

			// Do action if the channel != ""
			if (channel != "" && channel != null) {
				WebElement container = driver.findElement(By.xpath(xpath));
				// Get all columns
				List<WebElement> all_columns = container.findElements(By.tagName("td"));
				// Get checkbox columns
				WebElement checkbox = all_columns.get(0).findElement(By.tagName("label"));
				// Get dropbox columns
				WebElement dropbox = all_columns.get(1).findElement(By.tagName("div"));

				// Check if the dropbox is disable => enable the connection type
				// first
				if (dropbox.findElement(By.tagName("button"))
						.getAttribute("class").contains("disabled")) {
					checkbox.click();
					waitForAjax();
				}

				// Enable the connection type checkbox
				dropbox.findElement(By.tagName("button")).click();

				// Locate to the item
				WebElement tag_ul = dropbox.findElement(By.tagName("ul"));
				List<WebElement> all_li = tag_ul.findElements(By.tagName("li"));
				// Un-select all options
				if (all_li.get(0).findElement(By.tagName("input")).isSelected()) {
					all_li.get(0).findElement(By.tagName("input")).click();
					waitForAjax();
				}
				if(channel.toLowerCase().equals("none")){
					return;
				}

				for (WebElement each_li : all_li) {
					WebElement taglabel = each_li.findElement(By.tagName("label"));
					// Get HTML text and check it contains the options
					String option_name = taglabel.getText();
					if (option_name.equals(channel)) {
						if(!taglabel.findElement(By.tagName("input")).isSelected()){
							result.addLog("Select input channel: " + channel);
							taglabel.click();
							waitForAjax();
							break;
						}else{
							result.addLog("Input channel: " + channel + " is already selected");
							break;
						}
					}
				}
				// Close the dropbox
				dropbox.click();
				waitForAjax();
			}

		} catch (NoSuchElementException e) {
			System.out.println(e);
		}

	}
	
	// ----------------- PRIVATE FUNCTION--------------------
	private int getPageSize() {
		try {
			String text = "";
			if (driver.findElements(By.xpath(Xpath.TABLE_INFO)).size() > 0) {
				text = driver.findElement(By.xpath(Xpath.TABLE_INFO)).getText();
			} else if (driver.findElements(By.xpath(Xpath.PRODUCT_TABLE_INFO))
					.size() > 0) {
				text = driver.findElement(By.xpath(Xpath.PRODUCT_TABLE_INFO))
						.getText();
			} else if (driver.findElements(
					By.xpath(Xpath.COMPANY_LIST_TABLE_INFO)).size() > 0) {
				text = driver.findElement(
						By.xpath(Xpath.COMPANY_LIST_TABLE_INFO)).getText();
			} else if (driver.findElements(
					By.xpath(Xpath.ADMIN_USER_LIST_TABLE_INFO)).size() > 0) {
				text = driver.findElement(
						By.xpath(Xpath.ADMIN_USER_LIST_TABLE_INFO)).getText();
			}else if (driver.findElements(
					By.xpath(PromotionList.PROMOTION_TABLE_INFO)).size() > 0) {
				text = driver.findElement(
						By.xpath(PromotionList.PROMOTION_TABLE_INFO)).getText();
			}
			
			int size = StringUtilDts.getPageSize(text);
			return size;
		} catch (NoSuchElementException e) {
			System.err
					.println("-------NoSuchElementException------- : getPageSize ");
		}
		return 0;
	}
	
	public void clickText(String text) {
		try {
			waitForAjax();
			result.addLog("Click : " + text);
			Thread.sleep(2000);
			driver.findElement(By.linkText(text)).click();
			Thread.sleep(1000);
			waitForAjax();
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : " + text);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
