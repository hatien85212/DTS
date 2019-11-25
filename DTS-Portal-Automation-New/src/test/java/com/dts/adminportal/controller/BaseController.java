package com.dts.adminportal.controller;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.testng.Assert;
import org.testng.Reporter;

import com.dts.adminportal.autotools.GuiAutoToolLinux;
import com.dts.adminportal.autotools.GuiAutoToolWin;
import com.dts.adminportal.autotools.IGuiAutoTool;
import com.dts.adminportal.model.AccessoryVariants;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.DeviceEdit;
import com.dts.adminportal.model.LanguagePackage;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PageLogin;
import com.dts.adminportal.model.PromotionList;
import com.dts.adminportal.util.FileUtil;
import com.dts.adminportal.util.StringUtil;
import com.dts.adminportal.wrappers.BaseElement;
import com.dts.adminportal.wrappers.ButtonElement;

public class BaseController {
	
	public IGuiAutoTool autoTool;		
	protected String url;
	protected WebDriver driver;
	protected Wait<WebDriver> wait;
	JavascriptExecutor js = (JavascriptExecutor) driver;
	
	public enum enumOsType{
		Windows,
		Linux,
		MacOS,
		Unknown;
	}
		
	public BaseController(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);				
		wait = new WebDriverWait(driver, Constant.TIME_WAIT);
		initializeGuiAutoTool();
	}
	
	private void initializeGuiAutoTool(){
		switch(getOsType()){
		case Windows:
			autoTool = GuiAutoToolWin.getGuiAutoToolInstance();
			break;
		case Linux:
			autoTool = GuiAutoToolLinux.getGuiAutoToolInstance();
			break;
		
		default:
			autoTool = GuiAutoToolWin.getGuiAutoToolInstance();
			break;					
		}
	}

	private enumOsType getOsType() {
		enumOsType osType = enumOsType.Unknown;
		String osname = System.getProperty("os.name").toLowerCase();
		if (osname.contains("unix") || osname.contains("linux")) {
			osType = enumOsType.Linux;
		} else if (osname.contains("windows")) {
			osType = enumOsType.Windows;
		} else if (osname.contains("mac os")) {
			osType = enumOsType.MacOS;
		}
		return osType;
	}
	
	public WebDriver getDriver(){
		return driver;
	}
				
	public void addLog(String logmsg) {
		Reporter.log(logmsg + "</br>", true);
	}
	
	public void addErrorLog(String logmsg) {
		Reporter.log("<font color='red'> " + logmsg + " </font></br>", true);
	}
	
	public void addSuccessLog(String logmsg) {
		Reporter.log("<font color='green'> " + logmsg + " </font></br>", true);
	}
	
	public void waitForAjax() {
		System.err.println("Checking active ajax calls by calling jquery.active ...");
		try {
			if (driver instanceof JavascriptExecutor) {
				JavascriptExecutor jsDriver = (JavascriptExecutor) driver;				
				for (int i = 0; i < Constant.TIME_WAIT; i++) {
					Object numberOfAjaxConnections = jsDriver.executeScript("return jQuery.active");
					// return should be a number
					if (numberOfAjaxConnections instanceof Long) {
						Long n = (Long) numberOfAjaxConnections;
						System.err.println("Number of active jquery ajax calls: " + n);
						if (n.longValue() == 0L)
							break;
					}
					Thread.sleep(5000);
				}
			} else {
				System.err.println("Web driver: " + driver
						+ " cannot execute javascript");
			}
		} catch (InterruptedException e) {
			System.err.println(e);
		}
	}
	
	public boolean waitForLoadElement(final String element) {
		waitForAjax();
		System.err.println("On wait for load...");
		try {
			// wait element display
			if(driver.findElements(By.xpath(element)).size() > 0){
				addLog("Element displayed : "+element);
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
	public boolean waitElement(String xpath){
		WebDriverWait wait = new WebDriverWait(driver, 3000);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		return element.isEnabled();
	}
	public boolean clickAndWaitElementDisplayed(String xpath, String element) {
		boolean result = true;
		try {
			waitForLoadElement(xpath);
			addLog("Click "+xpath);
			driver.findElement(By.xpath(xpath)).click();
			waitForLoadElement(element);
			Thread.sleep(300);
		} catch (NoSuchElementException e) {
			addLog("No element "+element);
			result = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean existsElement(ArrayList<String> elements){		
		addLog("Check all element by ArrayList");
		waitForAjax();
		for(String element : elements){
			boolean isPresent = driver.findElements(By.xpath(element)).size() > 0;
			if(!isPresent){
				addLog("Element " + element + " doesn't existed!");				
				return false;
			}
		}
		return true;
	}
	public boolean existsElement(Hashtable<String, String> elementsInfo) {		
		addLog("start function existsElement: " + Calendar.getInstance().getTime().toString());
		waitForAjax();
		boolean result = true;
		addLog("Check all element by HashTable");
		if(elementsInfo.size() == 0){
			System.err.println("Element empty");
			result = false;
			return result;
		}
		for(String element : elementsInfo.keySet()){
			boolean isPresent = driver.findElements(By.xpath(elementsInfo.get(element))).size() > 0;
			if(!isPresent){
				addLog("Element " + element + " doesn't existed!");
				result = false;
			}else{
				
				addLog("Element "+ elementsInfo.get(element) +" displayed!");
			}
		}
		addLog("result is :" + result);
		addLog("end function existsElement: " + Calendar.getInstance().getTime().toString());
		return result;
	}
	
	public void type(String xpath, String data) {
		try {
			waitForAjax();
			driver.findElement(By.xpath(xpath)).clear();
			addLog("change data : "+data);
			driver.findElement(By.xpath(xpath)).sendKeys(data);
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at editData :  "+ xpath);
		}
		
	}

//	public boolean click(String xpath) {
//		boolean isPass = false;
//		waitForAjax();
//		try {
//			addLog("Click : " + xpath);
//			driver.findElement(By.xpath(xpath)).click();
//			waitForAjax();
//			try {
//				if (driver.findElement(By.xpath(AddUser.BRAND_PRIVILEGES)).isDisplayed()) {
//					//Thread.sleep(3000);
//					waitForAjax();
//					isPass = true;
//				}
//			} catch (NoSuchElementException e) {
//			}
//		} catch (NoSuchElementException e) {
//			addLog("No element exception : " + xpath);
//			Assert.assertTrue(false);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		return isPass;
//	}
	
	public void click(String xpath) {
		waitForAjax();
		try {
			addLog("Click : " + xpath);
			driver.findElement(By.xpath(xpath)).click();
			waitForAjax();
//			try {
//				if (driver.findElement(By.xpath(AddUser.BRAND_PRIVILEGES))
//						.isDisplayed()) {
//					Thread.sleep(3000);
//					waitForAjax();
//				}
//			} catch (NoSuchElementException e) {
//			} catch (InterruptedException e){				
//			}
		} catch (NoSuchElementException e) {
			addLog("No element exception : " + xpath);
			Assert.assertTrue(false);
		}
	}
	
	public String getTextByXpath(String xpath) {
		String text = "";
		try {
			text = driver.findElement(By.xpath(xpath)).getText();
			addLog("Text : " + text);
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at getTextByXpath :  "+ xpath);
		}
		return text;
	}
	
	public Boolean checkEmptyByXpath(String xpath) {
		try {
			if(driver.findElement(By.xpath(xpath)).getText().length() == 0) {
				return true;
			};
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at getTextByXpath :  "+ xpath);
		}
		return false;
	}
	
//	public ArrayList<String> getAllTextByXpath(String xpath) {
//		
//		ArrayList<String> all_text = new ArrayList<String>();
//		try {
//			text = driver.findElement(By.xpath(xpath)).getText();
//			addLog("Text : " + text);
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at getTextByXpath :  "+ xpath);
//		}
//		return ;
//	}
	public String getTextByXpath(WebElement element) {
		try {
			waitForAjax();
			String text = element.getText();
			addLog("Text : " + text);
			return text;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at getTextByXpath : " + element);
		}
		return "";
	}
	
	public String getAtributeValue(String xpath, String attribute) {
		try {
			waitForAjax();
			String text = driver.findElement(By.xpath(xpath)).getAttribute(attribute);
			addLog("Text : " + text);
			return text;
		} catch (NoSuchElementException e) {
			addErrorLog("NoSuchElementException at getTextByXpath : " + xpath);
			return "";
		}
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
	            addLog(e.getMessage());
	        }
	}

	public String getCurrentURL() {
		addLog(driver.getCurrentUrl());
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
	
	//-----------------------methods from homeController
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
	
	public void editData(WebElement element, String data) {
		try {
			waitForAjax();
			element.clear();
			addLog("change data : " + data);
			element.sendKeys(data);
			waitForAjax();
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at editData :  " + element);
		}
	}
	
	public boolean selectOptionByName(String xpath, String type) {
		boolean result = false;
		if (type != "" && type != null) {
			try {
				waitForAjax();
				WebElement dropDownListBox = driver.findElement(By.xpath(xpath));
				Select clickThis = new Select(dropDownListBox);
				addLog("click :" + type);
				clickThis.selectByVisibleText(type);
				// wait data loading
				waitForAjax();
				result = true;
			} catch (NoSuchElementException e) {
				addLog("No element exception: " + xpath);
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
			addLog("click : item index " + indexnumber);
			clickThis.selectByIndex(indexnumber);
			String textSelected = getItemSelected(xpath);
			addLog("click : " + textSelected);
			// wait data loading
			waitForAjax();
			return textSelected;
		} catch (NoSuchElementException e) {
			addErrorLog("No such elemetn exception");
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
					addLog("Item selected : " + selected);
					return selected;
				}
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException at readField : " + xpath);
		}
		return "";
	}
	
	public boolean uploadFile(String xpath, String fileName) {
		if (fileName == "" | fileName == null) {
			return false;
		}
		try {
			// Click on upload link
			WebElement webElement = driver.findElement(By.xpath(xpath));
			webElement.click();
			// Init data
			String uri = System.getProperty("user.dir") + File.separator + "asset" + File.separator;
			// Upload file
			String filePath = uri + fileName;
			addLog("Upload file : " + filePath);
			String uploadWindow = "File Upload";
			autoTool.winWait(uploadWindow, "", 10);
			Thread.sleep(1000);
			autoTool.winActivate(uploadWindow);
			autoTool.ControlSetText(uploadWindow, "", "[Class:Edit]", filePath.trim().toString());
			Thread.sleep(3000);
			autoTool.controlClick(uploadWindow, "", "[Text:&Open]");
			Thread.sleep(2000);
			if(autoTool.winExists(uploadWindow)){
				addLog("File not found or not invalid");
				String winID = autoTool.winGetHandle(uploadWindow);
				autoTool.controlClick("[Handle:" + winID + "]", "", "[Text:OK]");
				Thread.sleep(1000);
				autoTool.controlClick(uploadWindow, "", "[Text:Cancel]");
				return false;
			}
			waitForAjax();
			return true;
		} catch (NoSuchElementException e) {
			addLog("No element exception: " + xpath);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean uploadTuningAppDevice(String xpath, String fileName) {
		if (fileName == "" | fileName == null) {
			return false;
		}
		try {
			// Click on upload link
			WebElement webElement = driver.findElement(By.xpath(xpath));
			webElement.click();
			// Init data
//			File path = new File(System.getProperty("user.dir") + File.separator + "asset/AppDevice_tuning_file");
			String uri = System.getProperty("user.dir") + File.separator + "asset" + File.separator + "AppDevicetuningfile" + File.separator;
			// Upload file
			String filePath = uri + fileName;
			addLog("Upload file : " + filePath);
			String uploadWindow = "File Upload";
			autoTool.winWait(uploadWindow, "", 10);
			Thread.sleep(1000);
			autoTool.winActivate(uploadWindow);
			autoTool.ControlSetText(uploadWindow, "", "[Class:Edit]", filePath.trim().toString());
			Thread.sleep(3000);
			autoTool.controlClick(uploadWindow, "", "[Text:&Open]");
			Thread.sleep(2000);
			if(autoTool.winExists(uploadWindow)){
				addLog("File not found or not invalid");
				String winID = autoTool.winGetHandle(uploadWindow);
				autoTool.controlClick("[Handle:" + winID + "]", "", "[Text:OK]");
				Thread.sleep(1000);
				autoTool.controlClick(uploadWindow, "", "[Text:Cancel]");
				return false;
			}
			waitForAjax();
			return true;
		} catch (NoSuchElementException e) {
			addLog("No element exception: " + xpath);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean uploadTuningAudioRoute(String xpath, String fileName) {
		if (fileName == "" | fileName == null) {
			return false;
		}
		try {
			// Click on upload link
			WebElement webElement = driver.findElement(By.xpath(xpath));
			webElement.click();
			// Init data
//			File path = new File(System.getProperty("user.dir") + File.separator + "asset/AppDevice_tuning_file");
			String uri = System.getProperty("user.dir") + File.separator + "asset" + File.separator + "AudioRoutetuningfile" + File.separator;
			// Upload file
			String filePath = uri + fileName;
			addLog("Upload file : " + filePath);
			String uploadWindow = "File Upload";
			autoTool.winWait(uploadWindow, "", 10);
			Thread.sleep(1000);
			autoTool.winActivate(uploadWindow);
			autoTool.ControlSetText(uploadWindow, "", "[Class:Edit]", filePath.trim().toString());
			Thread.sleep(3000);
			autoTool.controlClick(uploadWindow, "", "[Text:&Open]");
			Thread.sleep(2000);
			if(autoTool.winExists(uploadWindow)){
				addLog("File not found or not invalid");
				String winID = autoTool.winGetHandle(uploadWindow);
				autoTool.controlClick("[Handle:" + winID + "]", "", "[Text:OK]");
				Thread.sleep(1000);
				autoTool.controlClick(uploadWindow, "", "[Text:Cancel]");
				return false;
			}
			waitForAjax();
			return true;
		} catch (NoSuchElementException e) {
			addLog("No element exception: " + xpath);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean uploadFileInterupt(String xpath, String fileName) {
		if (fileName == "" | fileName == null) {
			return false;
		}
		try {
			// Move mouse to Upload button
//			new Actions(driver).moveToElement(driver.findElement(By.xpath(AddEditProductModel.TUNING_DRAG_DROP_AREA))).perform();
			// Click on upload link
			WebElement webElement = driver.findElement(By.xpath(xpath));
			webElement.click();
			// Init data
			String uri = System.getProperty("user.dir") + File.separator + "asset" + File.separator;
			// Upload file
			String filePath = uri + fileName;
			addLog("Upload file : " + filePath);
			String uploadWindow = "File Upload";
			autoTool.winWait(uploadWindow, "", 10);
			Thread.sleep(500);
			autoTool.winActivate(uploadWindow);
			autoTool.ControlSetText(uploadWindow, "", "[Class:Edit]", filePath);
			Thread.sleep(500);
			autoTool.controlClick(uploadWindow, "", "[Text:&Open]");
			return true;
		} catch (NoSuchElementException e) {
			addLog("No element exception: " + xpath);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isElementPresent(String xpath) {
		try {
			boolean isDisplayed = driver.findElement(By.xpath(xpath)).isDisplayed();
			if (isDisplayed) {
				addLog("Element displayed : " + xpath);
				return true;
			} else {
				addLog("Element doesn't existed : " + xpath);
				return false;
			}
		} catch (NoSuchElementException e) {
			addLog("Element doesn't existed : " + xpath);
			return false;
		}
	}
	
	public boolean isElementPresent(WebElement element) {
		try {
			if (element == null) {
				return false;
			}
			boolean isDisplayed = element.isDisplayed();
			if (isDisplayed) {
				addLog("Element displayed : " + element.getText());
				return true;
			} else {
				addLog("Element doesn't existed : " + element.getText());
				return false;
			}
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException on isElementPresent " + element.getText());
			return false;
		}
	}
	
	public boolean selectFirstOption(String xpath) {
		try {
			waitForAjax();
			WebElement dropDownListBox = driver.findElement(By.xpath(xpath));
			Select clickThis = new Select(dropDownListBox);
			addLog("click  index 0");
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
							addLog("Select input channel: " + channel);
							taglabel.click();
							waitForAjax();
							break;
						}else{
							addLog("Input channel: " + channel + " is already selected");
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
	
	public int getPageSize() {
		try {
			String text = "";
			if (driver.findElements(By.xpath(PageHome.BRAND_ACCESSORY_TABLE_INFO)).size() > 0) {
				text = driver.findElement(By.xpath(PageHome.BRAND_ACCESSORY_TABLE_INFO)).getText();
			} else if (driver.findElements(By.xpath(AccessoryVariants.VARIANT_TABLE_INFO)).size() > 0) {
				text = driver.findElement(By.xpath(AccessoryVariants.VARIANT_TABLE_INFO)).getText();
			} else if (driver.findElements(By.xpath(PageHome.PRODUCT_TABLE_INFO)).size() > 0) {
				text = driver.findElement(By.xpath(PageHome.PRODUCT_TABLE_INFO)).getText();
			} else if (driver.findElements(By.xpath(PageHome.COMPANY_LIST_TABLE_INFO)).size() > 0) {
				text = driver.findElement(By.xpath(PageHome.COMPANY_LIST_TABLE_INFO)).getText();
			} else if (driver.findElements(By.xpath(PageHome.ADMIN_USER_LIST_TABLE_INFO)).size() > 0) {
				text = driver.findElement(By.xpath(PageHome.ADMIN_USER_LIST_TABLE_INFO)).getText();
			} else if (driver.findElements(By.xpath(PromotionList.PROMOTION_TABLE_INFO)).size() > 0) {
				text = driver.findElement(By.xpath(PromotionList.PROMOTION_TABLE_INFO)).getText();
			}
			int size = StringUtil.getPageSize(text);
			return size;
		} catch (NoSuchElementException e) {
			System.err.println("-------NoSuchElementException------- : getPageSize ");
		}
		return 0;
	}
	
	/**
	 * get size of the input table id
	 * @param tableNameInfo get from class: PageHome (BRAND_TABLE_INFO, LINK_APP_DEVICES, LINK_AUDIO_ROUTES,
	 * COMPANY_LIST_TABLE_INFO, PRODUCT_TABLE_INFO, ADMIN_USER_LIST_TABLE_INFO, PROMOTION_TABLE_INFO)
	 * @return
	 */
	public int getPageSize(String tableNameInfo) {
		try {
			String text = "";
			if (driver.findElements(By.xpath(tableNameInfo)).size() > 0) {
				text = driver.findElement(By.xpath(tableNameInfo)).getText();
			} 
			addLog("page size text: " + text);
			int size = StringUtil.getPageSize(text);
			return size;
		} catch (NoSuchElementException e) {
			addErrorLog("-------NoSuchElementException------- : getPageSize");
		}
		return 0;
	}
	
	public int getTotalElement(String tableNameInfo) {
		try {
			String text = "";
			if (driver.findElements(By.xpath(tableNameInfo)).size() > 0) {
				text = driver.findElement(By.xpath(tableNameInfo)).getText();
			}
			addLog("text: " + text);
			int total = StringUtil.getTotalElementOfPage(text);
			return total;
		} catch (NoSuchElementException e) {
			addErrorLog("-------NoSuchElementException------- : getPageSize");
		}
		return 0;
	}
	
	public void clickText(String text) {
		try {
			waitForAjax();
			addLog("Click : " + text);
//			Thread.sleep(2000);
			driver.findElement(By.linkText(text)).click();
//			Thread.sleep(1000);
			waitForAjax();
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : " + text);
		} 
//		catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
	public void editData(String editXpath, String data) {
		if (data != null) {
			try {
				waitForAjax();
				WebElement element = driver.findElement(By.xpath(editXpath));
				//note: work around because element.clear() wont' work in some cases
				element.sendKeys(Keys.BACK_SPACE);
				element.sendKeys(Keys.BACK_SPACE);
				element.clear();
				addLog("change data : " + data);
//				driver.findElement(By.xpath(editXpath)).sendKeys(data);
				element.sendKeys(data);
				waitForAjax();
			} catch (NoSuchElementException e) {
				addLog("NoSuchElementException at editData :  "
						+ editXpath);
			}
		}
	}
	
	public void doDelete(String xpath) {
		click(xpath);
		selectConfirmationDialogOption("Delete");
	}
	
	public void selectConfirmationDialogOption(String option) {
		try {
			Thread.sleep(3000);
			addLog("Select option: " + option);
			click(driver.findElement(By.xpath("//*[@href='javascript:;' and text() = '" + option + "']")));
			Thread.sleep(2000);
			waitForAjax();
		} catch (NoSuchElementException e) {
			addLog("No such element exception");
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void doSuspend(String xpath) {
		click(xpath);
		selectConfirmationDialogOption("Suspend");
	}
	
	public void doRestore(String xpath) {
		click(xpath);
		selectConfirmationDialogOption("Restore");
	}
	
	public void click(WebElement element) {
		try {
			addLog("Click element: //*[@id=" + element.getAttribute("id") + "]");
			element.click();
			waitForAjax();
		} catch (NoSuchElementException e) {
			addLog("Element: " + element + " is not present");
		}
	}

	public boolean existsElement(String[] requiresXpath) {
		boolean flag = true;
		for (String xpath : requiresXpath) {
			if (!isElementPresent(xpath)) {
				flag = false;
			}
		}
		return flag;
	}
	
	public boolean waitForElementClickable(String xpath) {
		try {
			addLog("Wait for element: " + xpath + " clickable");
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			return true;

		} catch (NoSuchElementException e) {
			addLog("No such element: " + xpath);
			return false;
		}
	}
	
	public boolean waitForElementDisappear(String xpath) {
		try {
			addLog("Wait for element: " + xpath + " disappear");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
			return true;

		} catch (NoSuchElementException e) {
			addLog("No such element: " + xpath);
			return false;
		}
	}
	
	public boolean isElementEditable(String xpath){ // Use only for textbox or combobox
		try {
			String value = driver.findElement(By.xpath(xpath)).getAttribute("value");
			String disabled = driver.findElement(By.xpath(xpath)).getAttribute("disabled");
			if (value != null) {
				if(disabled == null){
					addLog("Element: " + xpath + " is editable");
					return true;
				}
				addLog("Element: " + xpath + " is not editable");
				return false;
			}
			addLog("Element: " + xpath + " is not editable");
			return false;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElement: " + xpath);
			return false;
		}
	}
	
	public String getUserAccount(){
		return new BaseElement(driver, By.xpath(PageHome.lbLogout)).getText();
	}
	
	public boolean logout() {
		boolean isPass = false;
		addLog("click drop-down logout");
		new ButtonElement(driver, By.xpath(PageHome.btnLogout)).click();
		addLog("Wait for drop-down clickable");
		try {
			boolean isHome = waitForLoadElement(PageHome.submitLogout);
			if (isHome == true) {
				addLog("Click logout");
				new ButtonElement(driver, By.xpath(PageHome.submitLogout)).click();
			}
		} catch (ElementNotVisibleException e) {
			addLog(e.getMessage());
		}
		try {
			boolean isLoginPage = waitForLoadElement(PageLogin.xpath_username);
			if (isLoginPage == true) {
				addLog("Logout successful");				
				isPass = true;
			}
		} catch (ElementNotVisibleException e) {
			addLog(e.getMessage());			
		}
		return isPass;
	}
	
	public boolean getItemSelectedByXpath(String xpath) {
		addLog("- Find element By xpath : " + xpath);
		boolean result = true;
		try {
			waitForAjax();
			String select = readField(xpath);
			if (select.isEmpty()) {
				System.err.println("is empty");
				result = false;
			} else {
				addLog(select);
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException at getItemSelectedByXpath : "
							+ xpath);
			result = false;
		}
		return result;
	}
	
	public boolean checkItemSelectedByXpath(String xpath, String expect) {
		addLog("- Find element By xpath : " + xpath);
		boolean result = true;
		try {
			waitForAjax();
			String select = readField(xpath);
			if (select.isEmpty() || !select.equalsIgnoreCase(expect)) {
				System.err.println("is empty");
				result = false;
			} else {
				addLog(select);
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException at getItemSelectedByXpath : "
							+ xpath);
			result = false;
		}
		return result;
	}
	
	public ArrayList<String> getOptionList(String xpath) {
		ArrayList<String> arrayList = new ArrayList<String>();
		waitForAjax();
		try {
			WebElement footer = driver.findElement(By.xpath(xpath));
			// footer.click();
			waitForAjax();
			List<WebElement> columns = footer
					.findElements(By.tagName("option"));
			addLog("Option : ");
			for (WebElement column : columns) {
				arrayList.add(column.getText());
				addLog("\t" + column.getText());
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException at getAllOption : "
					+ xpath);
		}
		HashSet<String> hs = new HashSet<String>();
		hs.addAll(arrayList);
		arrayList.clear();
		arrayList.addAll(hs);
		return arrayList;
	}
	
	public boolean verifyValueColumn(String xpath, String headerText,
			String pattern) {
		try {
//			driver.findElement(By.linkText("First")).click();
			// sleep for load data
			waitForAjax();
			// get size page
			int size = getPageSize();
			System.out.println("size: " + size);
			if (size == 0) {
				addLog("No data for verify");
				return false;
			}
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 0; i < size; i++) {
				ArrayList<String> arrayList = getDataByHeaderText(xpath,
						headerText);
				if (arrayList.size() > 0) {
					addLog("Check All status is : " + pattern);
					for (String item : arrayList) {
						if (!item.equals(pattern)) {
							addLog("Status is not correct : " + item);
							return false;
						}
					}
				} else {
					addLog("No Data for verify");
					return false;
				}
				size --;
				if (size > 0) {
					driver.findElement(By.linkText("Next")).click();
				}
			}
			addLog("Column value is correct: " + pattern);
			return true;

		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at verifyValueColumn : "
					+ xpath);
		}
		return false;
	}
	
	public boolean verifyValuesColumn(String xpath, String headerText,
			String pattern1, String pattern2, String pattern3) {
		try {
//			driver.findElement(By.linkText("First")).click();
			// sleep for load data
			waitForAjax();
			// get size page
			int size = getPageSize();
			if (size == 0) {
				addLog("No data for verify");
				return false;
			}
			for (int i = 0; i < size; i++) {
				ArrayList<String> arrayList = getDataByHeaderText(xpath,
						headerText);
				if (arrayList.size() > 0) {
					addLog("Check All status is : " + pattern1 +" or "+pattern2 + " or " + pattern3);
					for (String item : arrayList) {
						if (!item.equals(pattern1) & !item.equals(pattern2) & !item.equals(pattern3)) {
							addLog("Status is not correct : " + item);
							return false;
						}
						addLog("Column value is correct: " + item);
					}
				} else {
					addLog("No Data for verify");
					return false;
				}
				if (size > 1) {
					driver.findElement(By.linkText("Next")).click();
				}
				
			}
			return true;

		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at verifyValueColumn : "
					+ xpath);
		}
		return false;
	}
	
	public ArrayList<String> getDataByHeaderText(String xpath, String headerText) {
		ArrayList<String> arrayList = new ArrayList<String>();
		try {
			waitForAjax();
			int index = 0;
			// step 1 : get Index
			index = getIndexOfHeaderText(xpath, headerText);
			
			System.out.println("index: " + index);
			if (index > -1) {
				arrayList = getListByHeaderText(xpath, index);
			}
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at getDataByHeaderText : "
					+ xpath);
		}
		return arrayList;
	}

	private ArrayList<String> getListByHeaderText(String xpath, int index) {
		ArrayList<String> arrayList = new ArrayList<String>();
		try {
			int size = getPageSize();
			while (size > 0) {
				waitForAjax();
				WebElement tbody = driver.findElement(By.xpath(xpath)).findElement(By.tagName("tbody"));
				List<WebElement> trRows = tbody.findElements(By
						.tagName("tr"));
				for (WebElement trRow : trRows) {
					List<WebElement> tdRows = trRow.findElements(By
							.tagName("td"));
					for (WebElement tdRow : tdRows) {
						if (tdRows.indexOf(tdRow) == index) {
							arrayList.add(tdRow.getText());
						}
					}
				}
				size--;
				if(size == 0){
					break;
				}
				clickText("Next");
			}
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at getListByHeaderText : "
					+ xpath);
		}
		return arrayList;
	}

	public int getIndexOfHeaderText(String xpath, String headerText) {
		try {
			waitForAjax();
			WebElement webElement = driver.findElement(By.xpath(xpath));
			List<WebElement> rows = webElement.findElements(By.tagName("th"));
			for (WebElement row : rows) {
				if (row.getText().equals(headerText)) {
					return rows.indexOf(row);
				}
			}
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at getIndexOfHeaderText : "
					+ xpath);
		}
		return -1;
	}
	
	public ArrayList<String> getTextListByXpaths(ArrayList<String> listXpath) {
		try {
			ArrayList<String> list = new ArrayList<String>();
			for(String item : listXpath){
				String text = driver.findElement(By.xpath(item)).getText();
				addLog("Text: " + text);
				list.add(text);
			}
			return list;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at getTextByXpath : " + listXpath);
		}
		return null;
	}

	public void switchWindowClickOption(String option) {
		waitForAjax();
		// Get all available windows
		Set<String> availableWindows = driver.getWindowHandles();
		if (!availableWindows.isEmpty()) {
			for (String windowId : availableWindows) {
				WebDriver popup = driver.switchTo().window(windowId);
				addLog("Click " + option);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				// Find the option in currently windows
				List<WebElement> elements = popup.findElements(By
						.linkText(option));
				for (WebElement webElement : elements) {
					System.out.println("Find " + option);
					String href = webElement.getAttribute("href");
					// Click the option when it contains the javascript
					if (href != null && href.contains("javascript:;")) {
						System.out.println("Click " + option);
						webElement.click();
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						waitForAjax();
					}
				}
				break;
			}
		}
	}

//	public boolean isElementPresent(WebElement element) {
//		try {
//			if (element == null) {
//				return false;
//			}
//			boolean isDisplayed = element.isDisplayed();
//			if (isDisplayed) {
//				System.out.println("Element displayed : " + element.getText());
//				return true;
//			} else {
//				System.out.println("Element doesn't existed : " + element.getText());
//				return false;
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException on isElementPresent "
//					+ element.getText());
//			return false;
//		}
//	}
	
	/**
	 * Click on the link text of an item which has name equals the input string
	 * @param name string to compare with value in column
	 * @param tableXpath xpath of destination table
	 * @param nameColPos position of column 
	 * @return true if click successfully
	 */
	public boolean selectItemByName(String name, String tableXpath, int nameColPos) {
		try {
			// Get page size to click next page
			int size = getPageSize(tableXpath);
			for (int i = 0; i < size; i++) {
				// Get the table
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (int j = 0; j < rows.size(); j++) {
					// Get all column from all rows
					List<WebElement> columns = rows.get(j).findElements(
							By.tagName("td"));
					// Get company name from first columns
					String company_name = columns.get(nameColPos).getText();
					// Click company if equal
					if (company_name.equals(name)) {
						addLog("Select item: " + name);
						columns.get(nameColPos).findElement(By.tagName("a")).click();
						waitForAjax();
						return true;
					}
				}
				clickText("Next");
			}
			addLog("item:" + name + " not found");
			return false;

		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException in selectItemByName");
			System.out.println("NoSuchElementException");
		}
		return false;
	}
	
	public boolean checkMessageDisplay(String message) {
		String text = driver.getPageSource();
		if (text.contains(message)) {
			addLog("Message: " + message + " found");
			return true;
		} else {
			addLog("Message: " + message + " not found");
			return false;
		}
	}
	
	public boolean checkMessageDisplay(ArrayList<String> messages) {
		String text = driver.getPageSource();
		for (String message : messages) {
			if (text.contains(message)) {
				addLog("Message: " + message + " found");
			} else {
				addLog("Message: " + message + " not found");
				return false;
			}
		}
		return true;
	}
	
	// TODO Move to wrapper class
	public String selectFilterByName(String xpath, String type) {
		try {
			waitForAjax();
			WebElement dropDownListBox = driver.findElement(By.xpath(xpath));
			if (type.length() == 0) {
				// select last option
				List<WebElement> options = dropDownListBox.findElements(By
						.tagName("option"));
				type = options.get(options.size() - 1).getText();
				System.out.println("Last option : " + type);
			}
			Select clickThis = new Select(dropDownListBox);
			addLog("click :" + type);
			clickThis.selectByVisibleText(type);
			waitForAjax();
		} catch (NoSuchElementException e) {
			System.err.println("No such element exception : selectOption");
		}
		return type;
	}
	
//	public boolean downloadFile(String filename) {
//		try {
//			System.out.println("File name (First): "+ filename);
//			String fileExt = filename.substring(filename.lastIndexOf("."), filename.length());
//			System.out.println("FileExt: " + fileExt);
//			// Cut off file extension
//			filename = filename.replaceAll(fileExt, "");
//			System.out.println("File name (Second): "+filename);
//			// Find the file link
//			// should change the input filename to input xpath
//			WebElement file_link = driver.findElement(By
//					.xpath("//a[contains(@href,'" + filename + "')]"));
//			// Click on download link
//			click(file_link);
//			// Get file name generated when download to local
//			autoTool.winWait("[Class:MozillaDialogClass]", "", 10);
//			String file_name_download = autoTool.winGetTitle("[Class:MozillaDialogClass]").replace("Opening ", "");
//			// Check if file exist and delete
//			String file_path = System.getProperty("user.home") + "\\Downloads\\" + file_name_download;
//			System.out.println("File path: "+file_path);
//			File file = new File(file_path);
//			if(file.exists()){
//				file.delete();
//			}
//			// Download file
//			addLog("Start download file: " + file_name_download);
//			handleFirefoxDownloadDialog("Save File");
//			for (int i = 1; i <= 10; i++) {
//				Thread.sleep(1000);
//				if (FileUtil.FileExist(file_path)) {
//					addLog("File: " + file_name_download + " is downloaded successfully");
//					return true;
//				}
//			}
//			addLog("File: " + file_name_download + "is not downloaded successfully");
//			return false;
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElement is present");
//			return false;
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
	
	public boolean isFileDownloaded(String downloadPath, String fileName) {
		boolean flag = false;
	    File dir = new File(downloadPath);
	    File[] dir_contents = dir.listFiles();
	    System.out.println(fileName);    
	    for (int i = 0; i < dir_contents.length; i++) {
	        if(dir_contents[i].getName().contains(fileName+".part"))
	        {
	        	System.out.println(dir_contents[i].getName()+" is existed");
	        	return flag=false;
	        }
	        else flag = true;
	            }
	    return flag;
	}
	public boolean downloadFile(String downloadLinkXpath) {
		try {
			if (downloadLinkXpath.contains(".dtscs")) {
				String filename = downloadLinkXpath; 
				System.out.println("File name (First): "+ filename);
				String fileExt = filename.substring(filename.lastIndexOf("."), filename.length());
				// Cut off file extension
				filename = filename.replace(fileExt, "");
				// Find the file link
				// should change the input filename to input xpath
				WebElement file_link = driver.findElement(By
						.xpath("//a[contains(@href,'" + filename + "')]"));
				System.out.println(filename);
				
				// Click on download link
				click(file_link);
				// Get file name generated when download to local
				autoTool.winWait("[Class:MozillaDialogClass]", "", 10);
		
				String file_name_download = autoTool.winGetTitle("[Class:MozillaDialogClass]").replace("Opening ", "");
				// Check if file exist and delete
				String file_path = System.getProperty("user.home") + "\\Downloads\\" + file_name_download;
				System.out.println("File path: "+file_path);
				File file = new File(file_path);
				if(file.exists()){
					file.delete();
				}
				// Download file
				addLog("Start download file: " + file_name_download);
				handleFirefoxDownloadDialog("Save File");
				for (int i = 1; i <= 15; i++) {
					Thread.sleep(1000);
					if (FileUtil.FileExist(file_path)) {
						addLog("File: " + file_name_download + " is downloaded successfully");
						return true;
					}
				}
				addLog("File: " + file_name_download + " is not downloaded successfully");
				return false;
			}
			
			else {
				String link = getAtributeValue(downloadLinkXpath, "href");
				//Get download file name
				int SlashId= link.lastIndexOf("/");		   
			    String filename = link.substring(SlashId+1);	
			    System.out.println(filename);
				System.out.println("Download File: "+filename);
				String file_path = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + filename;
				System.out.println("File path: "+file_path);
				// Delete file if exist
				if (FileUtil.FileExist(file_path)) {
					FileUtil.DeleteFile(file_path);
				}
				// Download file
				addLog("Start download file:" + filename);
				click(driver.findElement(By.xpath(downloadLinkXpath)));
				handleFirefoxDownloadDialog("Save File");
				System.out.println(file_path);
				Thread.sleep(4000);
				waitForAjax();
				for (int i = 1; i <= 30; i++) {
					Thread.sleep(6000);
					waitForAjax();
					if (isFileDownloaded(System.getProperty("user.home") + File.separator +"Downloads",filename)) {
						addLog("File: " + filename + " is downloaded successfully");
						return true;
					}
				}
			}	
		} catch (NoSuchElementException e) {
			addLog("No such element exception");
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public boolean handleFirefoxDownloadDialog(String option) {
		  try {
		   autoTool.winWait("[Class:MozillaDialogClass]", "", 10);
		   autoTool.winActivate("[Class:MozillaDialogClass]");
		   Thread.sleep(3000);
		   addLog("Select option: " + option);
		   if (option.equals("Open with")) {
		    autoTool.send("!o", false);
		   } else {
		    autoTool.send("!s", false);
		   }
		   Thread.sleep(5000);
		   autoTool.send("{ENTER}", false);
		   return true;
		  } catch (InterruptedException e) {
		   e.printStackTrace();
		   return false;
		  }
		 }
	
	public boolean checkAmountOfDisplayedItemOnTable(String xpath, int itemAmount){
		try {
//			int total_item = getTotalItem(xpath);
//			System.out.println("-----------------"
//					+ "Total item----------------------"
//					+ total_item);
			int page_size = getPageSize(xpath);
			
			int item_per_page = getPerPage(xpath);
			System.out.println("-----------------"
					+ "Total item----------------------"
					+ item_per_page);
			if ((page_size > 1) && (item_per_page == itemAmount)) {
				addLog("Maximum item amount display per page is: "
						+ itemAmount);
				return true;
//			} else if (total_item < itemAmount && item_per_page == total_item) {
//				addLog("Maximum item amount display per page is: "
//						+ itemAmount);
//				return true;
//			} else {
//				addLog("Item amount on table does not display correctly");
//				return false;
//			}
			} 
			if ((page_size == 1) && (item_per_page <= itemAmount)) {
				return true;
			}
		} catch (NoSuchElementException e) {
			addLog("No Element: " + xpath);
			return false;
		}
		return false;
	}
	
	public int getTotalItem(String xpath) {
		try {
			String text = getTextByXpath(xpath);
			return StringUtil.getNumAtIndex(text, 2);
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : getTotalItem");
		}
		return 0;
	}

	public int getPerPage(String xpath) {
		try {
			String text = getTextByXpath(xpath);
			return StringUtil.getNumAtIndex(text, 1);
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : getPerPage");
		}
		return 0;
	}
	
	public void dragDropFile(String fileName) {
		try {
			ScreenRegion screen = new DesktopScreenRegion();
			Mouse mouse = new DesktopMouse();
			Robot robot = new Robot();
			robot.mouseMove(200, 200);
			ScreenRegion dragDropArea = null;
			Target target = null;
			ScreenLocation location = null;
			File path = new File(System.getProperty("user.dir") + File.separator + "asset");
			File path_target = new File(System.getProperty("user.dir") + File.separator + "asset/target");
			// Get file ext
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			System.out.println(fileExt);
			// Scroll up to top page
			// autoTool.mouseWheel(50);
			// scroll to the element
//			autoTool.mouseWheel("up", 3);
			int i = 0;
			while (i < 20) {
				Thread.sleep(300);
				if (fileExt.equals("hpxtt") || fileExt.equals("zip")) {
					target = new ImageTarget(new File(path_target + File.separator + "Tuning.png"));

				} else if (fileExt.equals("jpg") || fileExt.equals("png")) {
//					target = new ImageTarget(new File(path + File.separator + "ImageDragDropArea.PNG"));
					System.out.println("image: " + fileName);
					target = new ImageTarget(new File(path_target + File.separator +  "Z" + fileName));
				} else {
					target = new ImageTarget(new File(path_target + File.separator + "Marketing.PNG"));
				}
				target.setMinScore(0.8);
				dragDropArea = screen.find(target);
				if (dragDropArea == null) {
					i++;
					autoTool.mouseWheel("down",3);
					Thread.sleep(500);
				} else {
					break;
				}
			}
//			if (fileExt.equals("jpg") || fileExt.equals("png")) {
//				location = dragDropArea.getRelativeScreenLocation(400,40);
//			} else {
//				location = dragDropArea.getCenter();
//			}
			if (dragDropArea != null) {
				location = dragDropArea.getRelativeScreenLocation(400, 30);
			} else {
				System.out.println("Don't");
			}
			// Open directory contain file
			Desktop.getDesktop().open(path);
			// Move window to the right corner
			int width = screen.getWidth();
			autoTool.winWait("asset");
			autoTool.winActivate("asset");
			autoTool.winMove("asset", "", width - 600, 0, 600, 600);
			Thread.sleep(300);
			// Drag and Drop file to Marketing Container
			ScreenRegion dragDropFile = null;
			int j = 0;
			while (j < 20) {
				Target imageTarget = new ImageTarget(new File(path_target + File.separator + fileName + ".png"));
				imageTarget.setMinScore(0.9);
				dragDropFile = screen.wait(imageTarget, 1000);
				addLog("Drag and drop file: " + fileName + " to Drag Drop Area");
				if (dragDropFile == null) {
					autoTool.mouseWheel("down", 2);
					Thread.sleep(1000);
					j++;
				} else
					break;
			}
			mouse.drag(dragDropFile.getCenter());
			mouse.drop(location);
			Thread.sleep(3000);
			waitForAjax();
			// Close window
			autoTool.winClose("asset");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean deleteAllUploadedImage(String xpath) {
		try {
			WebElement image = driver.findElement(By
					.xpath(xpath)).findElement(By.className("icon-trash-icon"));
			while(image.isDisplayed()){
				addLog("Delete image");
				image.click();
				waitForAjax();
				selectConfirmationDialogOption("Delete");
				image = driver.findElement(By
						.xpath(xpath)).findElement(By.className("icon-trash-icon"));
			}
		} catch (NoSuchElementException e) {
			addLog("Uploaded images are deleted successfully");
			return true;
		}
		return true;
	}
	
	// TODO move to wrapper class
	public boolean selectACheckbox(String xpath) {
		try {
			WebElement checkbox = driver.findElement(By.xpath(xpath));
			if (!checkbox.isSelected()) {
				addLog("Select checkbox: " + xpath);
				checkbox.click();
				waitForAjax();
				return true;
			}
			addLog("Checkbox: " + xpath + " is already selected");
			return true;
		} catch (NoSuchElementException e) {
			addLog("No such element: " + xpath);
			return false;
		}
	}
	
	// TODO move to wrapper class
	public boolean uncheckACheckbox(String xpath) {
		try {
			WebElement checkbox = driver.findElement(By.xpath(xpath));
			if (checkbox.isSelected()) {
				addLog("Uncheck checkbox: " + xpath);
				checkbox.click();
				waitForAjax();
				return true;
			}
			addLog("Checkbox: " + xpath + " is already unchecked");
			return true;
		} catch (NoSuchElementException e) {
			addLog("No such element: " + xpath);
			return false;
		}
	}
	
	// TODO: need to refactor the following method
	public String getElementText(WebElement element) {
		try {
			waitForAjax();
			String text = element.getText();
			addLog("Text : " + text);
			return text;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at getTextByXpath : "
					+ element);
		}
		return "";
	}
	
	public ArrayList<String> getAllComboboxOption(String xpath) {
		try {
			// Get combobox
			WebElement combobox = driver.findElement(By.xpath(xpath));
			Select select = new Select(combobox);
			// Get options
			ArrayList<String> list = new ArrayList<String>();
			List<WebElement> options = select.getOptions();
			for (WebElement option : options) {
				addLog("Option: " + option.getText());
				list.add(option.getText());
			}
			return list;

		} catch (NoSuchElementException e) {
			addLog("No element: " + xpath);
			return null;
		}
	}
	
	public ArrayList<String> getAllColumnHeader(String xpath) {
		try {
			ArrayList<String> list = new ArrayList<String>();
			// Get thead section
			WebElement thead = driver.findElement(By.xpath(xpath)).findElement(
					By.tagName("thead"));
			// Get all headers
			List<WebElement> headers = thead.findElements(By.tagName("th"));
			for (WebElement header : headers) {
				addLog("Header text: " + header.getText());
				list.add(header.getText());
			}
			return list;
		} catch (NoSuchElementException e) {
			addLog("No such element: " + xpath);
			return null;
		}
	}
	
	// TODO: move following method to Wrapper class - TableElement
	public ArrayList<String> getTableHeader(String xpath) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			WebElement tbody = driver.findElement(By.xpath(xpath));
			List<WebElement> rows = tbody.findElements(By.tagName("th"));
			for (WebElement row : rows) {
				data.add(row.getText());
			}
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : getHeadColumByXpath ");
		}
		return data;
	}
	
	// TODO: move following method to Wrapper class
	public void selectReportsFilterByText(String xpath, String option) {
		try {
			new Select(driver.findElement(By.xpath(xpath))).selectByVisibleText(option);
			waitForAjax();
		} catch (NoSuchElementException e) {
		}

	}
	
	// TODO not yet checked
	public boolean canEdit(String xpath) {
		try {
			waitForLoadElement(xpath);
			WebElement element = driver.findElement(By.xpath(xpath));
			String beforeText = element.getText();
			element.sendKeys(RandomStringUtils.randomNumeric(20));
			String afterText = element.getText();
			return !beforeText.equals(afterText);
		} catch (NoSuchElementException e) {
			System.err.println("No elemetn exception : canEdit");
			return false;
		}
	}
	
	public List<LanguagePackage> getLanguagePackage(String xpath) {
		List<LanguagePackage> list = new ArrayList<LanguagePackage>();
		try {
			WebElement anotherpackage = driver.findElement(By.xpath(xpath));
			List<WebElement> eachpackage = anotherpackage.findElements(By.tagName("div"));

			for (WebElement item : eachpackage) {
				LanguagePackage languagepackage = new LanguagePackage();
				languagepackage.languagedropbox = item.findElement(By.tagName("select"));
				languagepackage.name = item.findElement(By.tagName("input"));
				languagepackage.trash = item.findElement(By.tagName("i"));
				WebElement spanElem = item.findElement(By.tagName("span")); 
				if (!(spanElem.getAttribute("style")).contains("none")) {
					System.out.println("Set msg : -----------------------");
					languagepackage.warningmessage = spanElem.getAttribute("innerHTML");
					System.out.println("check here : " + languagepackage.warningmessage);
				} else {
					languagepackage.warningmessage = "none";
				}
				list.add(languagepackage);
			}
		} catch (NoSuchElementException e) {
		}
		addLog("Number of language packages: " + list.size());
		return list;
	}
	
	public boolean selectOptionByName(WebElement element, String type) {
		try {
			waitForAjax();
			Select clickThis = new Select(element);
			addLog("click :" + type);
			clickThis.selectByVisibleText(type);
			// wait data loading
			waitForAjax();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	// TODO Move to wrapper class or make generic
	public ArrayList<String> getInputSpecificationHeader(String xpath) {
		try {
			ArrayList<String> listText = new ArrayList<String>();
			// Get first row
			WebElement firstRow = driver.findElement(By.xpath(xpath)).findElements(By.tagName("tr")).get(0);
			// Get headers
			List<WebElement> headers = firstRow.findElements(By.tagName("th"));
			for (WebElement header : headers) {
				// Get header text
				String headerText = header.getText();
				addLog("Header text: " + headerText);
				listText.add(headerText);
			}
			return listText;
		} catch (NoSuchElementException e) {
			addLog("No element: " + xpath);
			return null;
		}
	}

	public boolean isImageDisplayLeftToRight(String xpath) {
		try {
			int size = 0;
			// Get image table
			WebElement table = driver.findElement(By.xpath(xpath)).findElement(By.tagName("tbody"));
			WebElement row = table.findElements(By.tagName("tr")).get(0);
			// Get all image display
			List<WebElement> images = row.findElements(By.tagName("td"));
			for (int i = 0; i < images.size(); i++) {
				// Get image text
				String img_text = images.get(i).getText();
				addLog(img_text);
				// Get image size
				int endIndex = img_text.indexOf("x");
				int img_size = Integer.parseInt(img_text.substring(0, endIndex));
				if (img_size < size) {
					addLog("Image does not display from left to right in order 250x250,500x500,1000x1000");
					return false;
				} else {
					addLog("The image no " + i + " has size: " + img_size);
					img_size = size;
				}
			}
			addLog("Image displays from left to right in order 250x250,500x500,1000x1000");
			return true;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElement: " + xpath);
			return false;
		}
	}
	
	public boolean isElementDisplayVertically(String fisrtelement, String secondelement) {

		WebElement first = driver.findElement(By.xpath(fisrtelement));
		WebElement second = driver.findElement(By.xpath(secondelement));

		int posY1 = first.getLocation().y;
		int posY2 = second.getLocation().y;

		if (posY1 == posY2 || posY1 > posY2) {
			addLog("Second element does not display below first element");
			addLog("1st Y position = " + posY1);
			addLog("2nd Y position = " + posY2);
			return false;
		} else {
			addLog("Second element displays below first element");
			addLog("1st X position = " + posY1);
			addLog("2nd X position = " + posY2);
			return true;
		}

	}
	
	public boolean isElementDisplayHorizontal(String fisrtelement, String secondelement) {

		WebElement first = driver.findElement(By.xpath(fisrtelement));
		WebElement second = driver.findElement(By.xpath(secondelement));

		int posX1 = first.getLocation().x;
		int posX2 = second.getLocation().x;

		if (posX1 == posX2 || posX1 > posX2) {
			addLog("Second element does not display next to first element");
			addLog("1st X position = " + posX1);
			addLog("2nd X position = " + posX2);
			return false;
		} else {
			addLog("Second element displays next to first element");
			addLog("1st X position = " + posX1);
			addLog("2nd X position = " + posX2);
			return true;
		}

	}

	public boolean isImageDisplayInOrder(String xpath) {
		try {
			int size = 0;
			int pos = 0;
			// Get all image in image catalog
			List<WebElement> img_div = driver.findElement(By.xpath(xpath))
					.findElements(By.className("tuning-material-div"));
			for (int i = 0; i < img_div.size(); i++) {
				WebElement img = img_div.get(i).findElements(By.tagName("span")).get(0);
				// Get image text
				String img_text = img.getText();
				addLog(img_text);
				// Get image size
				int beginIndex = img_text.indexOf("(");
				int endIndex = img_text.indexOf("x");
				int img_size = Integer.parseInt(img_text.substring(beginIndex + 1, endIndex));
				if (img_size < size && img.getLocation().y < pos) {
					addLog("Image does not display in order 250x250,500x500,1000x1000");
					return false;
				} else {
					addLog("The image no " + i + " has size: " + img_size);
					addLog("The postion y of " + i + " = " + img.getLocation().y);
					img_size = size;
					pos = img.getLocation().y;
				}
			}
			addLog("Image displays in order 250x250,500x500,1000x1000");
			return true;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElement: " + xpath);
			return false;
		}
	}

	public void inputData(String xpath, String data) {
		try {
			editData(xpath, data);
			int i = 0;
			while (i < 10) {
				if (getAtributeValue("//*[@id='ui-id-1']", "style").contains("display: block")) {
					click("//*[@id='ui-id-1']/li[1]");
					break;
				}
				else {
					i++;
					Thread.sleep(1000);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void clickImage(String imageName){
		try {
			Thread.sleep(500);
			ScreenRegion screen = new DesktopScreenRegion();
//			Mouse mouse = new DesktopMouse();
			File path = new File(System.getProperty("user.dir") + "\\asset");
			ScreenRegion image = null;
			int t=0;
			while(image == null && t < 5){
				Thread.sleep(1000);
//				dragDropFile = sreen.find(new ImageTarget(new File(path + File.separator +"Promo"+ source + ".png")));
				Target imageTarget = new ImageTarget(new File(path + "\\" +  imageName));
				imageTarget.setMinScore(0.9);
				image = screen.wait(imageTarget, 1000);
				t++;
			}
//			mouse.click(image.getCenter());
			autoTool.send("{ENTER}", false);
			waitForAjax();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//------------------------------------------------------------------------
	
	public String getCSRFToken(){
		return driver.findElement(By.xpath("//meta[@name='_csrf']")).getAttribute("content");
	}
	
	public String getCookie(){
		return driver.manage().getCookies().toString();
	}
	
	public void myWait(long time){
		try {
			wait(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ArrayList<String> getDeviceHPListOnDB(String productType){
		ArrayList<String> deviceheadphones= new ArrayList<String>();
		if(productType.equals(DeviceEdit.Product_Types.HPX_High.getType())){
			deviceheadphones.add(DeviceEdit.DB_Attached_List.Attached0.getName());
			deviceheadphones.add(DeviceEdit.DB_Attached_List.Attached2.getName());
			deviceheadphones.add(DeviceEdit.DB_Attached_List.Attached3.getName());
			deviceheadphones.add(DeviceEdit.DB_Attached_List.Attached4.getName());
			deviceheadphones.add(DeviceEdit.DB_Attached_List.Attached5.getName());
			deviceheadphones.add(DeviceEdit.DB_Attached_List.Attached6.getName());
			deviceheadphones.add(DeviceEdit.DB_Attached_List.Attached7.getName());
			deviceheadphones.add(DeviceEdit.DB_Attached_List.Attached8.getName());
		}
		else if(productType.equals(DeviceEdit.Product_Types.HPX_Medium.getType())){
			deviceheadphones.add(DeviceEdit.DB_Attached_List.Attached0.getName());
			deviceheadphones.add(DeviceEdit.DB_Attached_List.Attached2.getName());
			deviceheadphones.add(DeviceEdit.DB_Attached_List.Attached3.getName());
			deviceheadphones.add(DeviceEdit.DB_Attached_List.Attached4.getName());
		}
		else if(productType.equals(DeviceEdit.Product_Types.HPX_Low.getType())){
			deviceheadphones.add(DeviceEdit.DB_Attached_List.Attached2.getName());
			deviceheadphones.add(DeviceEdit.DB_Attached_List.Attached3.getName());
			deviceheadphones.add(DeviceEdit.DB_Attached_List.Attached4.getName());
		}
		
		return deviceheadphones;
	}
}
