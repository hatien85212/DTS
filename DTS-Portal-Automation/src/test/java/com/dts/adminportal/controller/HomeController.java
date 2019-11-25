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

public class HomeController extends LoginController {
	protected Select select;
	protected String xpathListItemPartner = Xpath.dropdownMenuInnerSelectpicker;
	public AutoItX AutoItX;
	
	public HomeController(WebDriver driver, URI siteBase) {
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

	public Result getItemSelectedByXpath(String xpath) {
		result.addLog("- Find element By xpath : " + xpath);
		try {
			waitForAjax();
			String select = readField(xpath);
			if (select.isEmpty()) {
				System.err.println("is empty");
				result.setResult("Fail");
			} else {
				result.setResult(select);
			}
		} catch (NoSuchElementException e) {
			System.err
					.println("NoSuchElementException at getItemSelectedByXpath : "
							+ xpath);
			result.setResult("Fail");
		}
		return result;
	}

	public String getCurrentPartner(String xpath) {
		try {
			waitForAjax();
			WebElement footer = driver.findElement(By.xpath(xpath));
			List<WebElement> columns = footer.findElements(By.tagName("li"));
			for (WebElement column : columns) {
				if (column.getAttribute("class").equals("selected")) {
					WebElement webElement = column.findElement(By.tagName("a"));
					result.addLog(webElement.getAttribute("text"));
					return webElement.getAttribute("text");
					
				}
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException at getCurrentPartner : "
					+ xpath);
		}
		return "";
	}

	public Result isCorrectItemSelected(String xpath, String textInput) {
		// wait elenment display
		waitForAjax();
		String itemSelected = readField(xpath);
		result.addLog("-Check fiter selected");
		if (!itemSelected.equals(textInput)) {
			result.addLog("Fiter select was wrong , selected : " + itemSelected);
			result.setResult("Fail");
		} else {
			result.addLog("Filter selected : " + itemSelected);
			result.setResult("Pass");
		}
		return result;
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
			System.err
					.println("NoSuchElementException at readField : " + xpath);
		}
		return "";
	}

	public ArrayList<String> getAllOption(String xpath) {
		ArrayList<String> arrayList = new ArrayList<String>();
		waitForAjax();
		try {
			WebElement footer = driver.findElement(By.xpath(xpath));
			// footer.click();
			waitForAjax();
			List<WebElement> columns = footer
					.findElements(By.tagName("option"));
			result.addLog("Option : ");
			for (WebElement column : columns) {
				arrayList.add(column.getText());
				result.addLog("\t" + column.getText());
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

	public Result selectPartnerByName(String xpath, String name) {
		// step 1 : Select partner
		waitForAjax();
		try {
			result.addLog("-Click dropdown menu view as for choose partner");
			driver.findElement(By.xpath(xpath)).click();// Xpath.dropDownListViewAs
			WebElement ul = driver.findElement(By.xpath(xpathListItemPartner));
			if (!selectValueFromUnorderedList(ul, name)) {
				System.err.println("Select failed : " + name);
				result.setResult("Fail");
			}
		} catch (NoSuchElementException e) {
			System.err
					.println("NoSuchElementException at selectPartnerByName : "
							+ xpath);
			result.setResult("Fail");
		}
		return result;
	}

	public Boolean selectValueFromUnorderedList(WebElement unorderedList,
			final String value) {
		try {
			waitForAjax();
			List<WebElement> options = unorderedList.findElements(By
					.tagName("li"));
			for (WebElement option : options) {
				if (value.equals(option.getText())) {
					result.addLog("-Click partner : " + value);
					option.findElement(By.tagName("a")).click();
					waitForAjax();
					return true;
				}
			}
		} catch (NoSuchElementException e) {
			System.err
					.println("NoSuchElementException at selectValueFromUnorderedList : "
							+ unorderedList);
		}
		return false;
	}

	public Result checkHeadingWithEelementDisplayed(String xpath,
			ArrayList<String> arrayList) {
		waitForAjax();
		try {
			WebElement footer = driver.findElement(By.xpath(xpath));
			List<WebElement> columns = footer.findElements(By.tagName("a"));
			result.addLog("Column size : " + columns.size());
			for (WebElement column : columns) {
				if (column.isDisplayed()) {
					String text = column.getText().replaceAll(
							"\\s+\\(\\d+\\)\\s*", "");
					if (arrayList.contains(text)) {
						arrayList.remove(text);
						result.addLog("Element " + column.getText()
								+ " Displayed");
					} else {
						result.addLog("Element " + column.getText()
								+ " doesn't existed!");
					}
				}
			}
		} catch (NoSuchElementException e) {
			System.err
					.println("NoSuchElementException on checkHeadingWithEelementDisplayed : "
							+ xpath);
		}
		// check element doesn't existed!
		if (arrayList.size() > 0) {
			result.setResult("Fail");
		}
		return result;
	}

	public Result getFirstItemSelectedByXpath(String xpath) {
		try {
			waitForAjax();
			WebElement firstColumn = driver.findElement(By.xpath(xpath));
			if (firstColumn.getText().isEmpty()) {
				result.setResult("Fail");
			} else {
				result.setResult(firstColumn.getText());
			}
		} catch (NoSuchElementException e) {
			System.err
					.println("NoSuchElementException on getFirstItemSelectedByXpath : "
							+ xpath);
			result.setResult("Fail");
		}
		return result;
	}

	public Result verifyLinkAndElements(String xpath, String url,
			ArrayList<String> listElement) {
		result.addLog("- Click link : " + xpath);
		try {
			waitForAjax();
			driver.findElement(By.xpath(xpath)).click();
			waitForAjax();
			result.addLog("- Check All element display");
			result = existsElement(listElement);
		} catch (NoSuchElementException e) {
			result.addLog("No element " + xpath);
			result.setResult("Fail");
			return result;
		}
		return result;
	}

	public Result checkURL(String url, String currentURL) {
		waitForAjax();
		if (!currentURL.contains(url)) {
			result.addLog("url incorrect");
			result.setResult("Fail");
		} else {
			result.addLog("url correct : " + driver.getCurrentUrl());
			result.setResult("Pass");
		}
		return result;
	}

	public Boolean checkElemenExist(ArrayList<String> arrayList) {
		// add wait element
		waitForAjax();
		for (String item : arrayList) {
			try {
				Boolean isPresent = driver.findElements(By.xpath(item)).size() > 0;
				if (!isPresent) {
					result.addLog("Element " + item + " doesn't existed!");
					return false;
				}
			} catch (NoSuchElementException e) {
				result.addLog("NoSuchElementException at checkElemenExist");
				return false;
			}
		}
		return true;
	}

	public Result checkVersion(String xpath, String status) {
		try {
			waitForAjax();
			WebElement footer = driver.findElement(By.xpath(xpath));
			List<WebElement> columns = footer.findElements(By.tagName("tr"));
			ArrayList<String> arrayList = getAllVersion(xpath, columns.size());
			// check arraylist
			for (String item : arrayList) {
				if (!item.contains(status)) {
					result.addLog("-Version incorrect");
					result.setResult("Fail");
					return result;
				}
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at checkVersion : " + xpath);
			result.setResult("Fail");
		}
		return result;
	}

	private ArrayList<String> getAllVersion(String xpath, int size) {
		ArrayList<String> arrayList = new ArrayList<String>();
		try {
			waitForAjax();
			for (int i = 1; i <= size; i++) {
				result.addLog(xpath + "/tr[" + i + "]/td[5]");
				String colVersion = xpath + "/tr[" + i + "]/td[5]";
				if (driver.findElements(By.xpath(colVersion)).size() > 0) {
					arrayList.add(driver.findElement(By.xpath(colVersion))
							.getText());
				} else {
					result.addLog("No Data!");
				}
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at getAllVersion : " + xpath);
		}
		return arrayList;
	}

	public Boolean verifyValueColumn(String xpath, String headerText,
			String pattern) {
		try {
			driver.findElement(By.linkText("First")).click();
			// sleep for load data
			waitForAjax();
			// get size page
			int size = getPageSize();
			if (size == 0) {
				result.addLog("No data for verify");
				return false;
			}
			for (int i = 0; i < size; i++) {
				ArrayList<String> arrayList = getDataByHeaderText(xpath,
						headerText);
				if (arrayList.size() > 0) {
					result.addLog("Check All status is : " + pattern);
					for (String item : arrayList) {
						if (!item.equals(pattern)) {
							result.addLog("Status is not correct : " + item);
							return false;
						}
					}
				} else {
					result.addLog("No Data for verify");
					return false;
				}
				driver.findElement(By.linkText("Next")).click();
			}
			result.addLog("Column value is correct: " + pattern);
			return true;

		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at verifyValueColumn : "
					+ xpath);
		}
		return false;
	}

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

	public Result verifyValueColumn(String xpath, String headerText,
			ArrayList<String> listPattern) {
		try {
			driver.findElement(By.linkText("First")).click();
			waitForAjax();
			// get size page
			int size = getPageSize();
			for (int i = 0; i < size; i++) {
				waitForAjax();
				ArrayList<String> arrayList = getDataByHeaderText(xpath,
						headerText);
				if (arrayList.size() > 0) {
					for (String item : arrayList) {
						if (!listPattern.contains(item)) {
							result.addLog("Status is not correct : " + item);
							result.setResult("Fail");
							return result;
						}
					}
				} else {
					result.addLog("No Data!!!");
				}
				driver.findElement(By.linkText("Next")).click();
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at verifyValueColumn : "
					+ xpath);
			result.setResult("Fail");
		}
		return result;
	}

	public ArrayList<String> getDataByHeaderText(String xpath, String headerText) {
		ArrayList<String> arrayList = new ArrayList<String>();
		try {
			waitForAjax();
			int index = 0;
			// step 1 : get Index
			index = getIndexOfHeaderText(xpath, headerText);
			if (index > -1) {
				arrayList = getListByHeaderText(xpath, index);
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at getDataByHeaderText : "
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
			result.addLog("NoSuchElementException at getListByHeaderText : "
					+ xpath);
		}
		return arrayList;
	}

	private int getIndexOfHeaderText(String xpath, String headerText) {
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
			result.addLog("NoSuchElementException at getIndexOfHeaderText : "
					+ xpath);
		}
		return -1;
	}

	public Result selectAndVerifyElementDisplay(String viewAs, String partner,
			String header, ArrayList<String> listElement) {
		try {
			waitForAjax();
			// click view as and chosse partner
			result = selectPartnerByName(viewAs, partner);
			if (result.getResult().equals("Fail")) {
				return result;
			} else {
				result = verifyElementsByXpath(header, listElement);
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at selectAndVerifyElementDisplay : "
					+ header);
			result.setResult("Fail");
		}
		return result;
	}

	public Result verifyElementsByXpath(String xpath, ArrayList<String> elements) {
		// check & get element by xpath
		WebElement heading;
		try {
			waitForAjax();
			heading = driver.findElement(By.xpath(xpath));
			result.addLog("Found %d elements " + heading.getSize());
			for (String element : elements) {
				Boolean isPresent = heading.findElements(By.xpath(element))
						.size() > 0;
				if (!isPresent) {
					result.addLog("Element " + element + " doesn't existed!");
					result.setResult("Fail");
				} else {
					result.addLog("Element " + element + "displayed!");
				}
			}
		} catch (NoSuchElementException e) {
			result.addLog("Element " + xpath + "doesn't existed!");
			result.setResult("Fail");
			return result;
		}
		return result;
	}

	public Result verifyOption(String xpath, ArrayList<String> listElements) {
		try {
			waitForAjax();
			result.addLog("click filter : " + xpath);
			ArrayList<String> arrayList = getAllOption(xpath);
			// compare two array list
			Boolean match = listElements.containsAll(arrayList);
			if (!match) {
				result.setResult("Fail");
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at verifyOption : " + xpath);
			result.setResult("Fail");
		}
		return result;
	}

	public Result clickOption(String xpath, String name) {
		result.addLog("Find & click dropdow menu : " + xpath + " option : "
				+ name);
		try {
			waitForAjax();
			WebElement select = driver.findElement(By.xpath(xpath));
			List<WebElement> options = select
					.findElements(By.tagName("option"));
			for (WebElement option : options) {
				if (name.equals(option.getText())) {
					result.addLog("Click " + name);
					option.click();
					waitForAjax();
				}
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at clickOption : " + xpath);
			result.setResult("Fail");
		}
		return result;
	}

	public Result checkAlertMsg(String createnewaccessoryXpath,
			String globalAlertXpath, String addAccessoriesError) {
		try {
			waitForAjax();
			driver.findElement(By.xpath(createnewaccessoryXpath)).click();
			waitForAjax();
			if (driver.findElement(By.xpath(globalAlertXpath)).getText()
					.equals(addAccessoriesError)) {
				result.addLog("Message error : " + addAccessoriesError);
				result.setResult("Pass");
			} else {
				result.addLog("Message error : "
						+ driver.findElement(By.xpath(globalAlertXpath))
								.getText());
				result.setResult("Fail");
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at checkAlertMsg : "
					+ createnewaccessoryXpath);
			result.setResult("Fail");
		}
		return result;
	}

	// click next, check page index++
	public Result checkNext(String xpath, String next) {
		int sizePage;
		int currentPageTemp;
		try {
			waitForAjax();
			sizePage = getSizePage(xpath);
			currentPageTemp = getCurrentPage(xpath);
			result.addLog("Click next : " + next);
			driver.findElement(By.xpath(next)).click();
			waitForAjax();
			if (currentPageTemp != sizePage
					&& getCurrentPage(xpath) != currentPageTemp + 1) {
				result.setResult("Fail");
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at checkNext : " + xpath);
			result.setResult("Fail");
		}
		return result;
	}

	// click previous, check page index--
	public Result checkPrevious(String xpath, String previous) {
		int currentPageTemp;
		try {
			waitForAjax();
			currentPageTemp = getCurrentPage(xpath);
			result.addLog("Click Previous : " + previous);
			driver.findElement(By.xpath(previous)).click();
			waitForAjax();
			if (currentPageTemp != 1
					&& getCurrentPage(xpath) != currentPageTemp - 1) {
				result.setResult("Fail");
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at checkPrevious : " + xpath);
			result.setResult("Fail");
		}
		return result;
	}

	// click last, check page is last index
	public Result checkLast(String xpath, String last) {
		int sizePage;
		try {
			waitForAjax();
			sizePage = getSizePage(xpath);
			result.addLog("Click Last : " + last);
			driver.findElement(By.xpath(last)).click();
			waitForAjax();
			if (getCurrentPage(xpath) >= sizePage) {
				result.setResult("Pass");
			} else {
				result.setResult("Fail");
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at checkLast : " + xpath);
			result.setResult("Fail");
		}
		return result;
	}

	// click first, check page is first index (1)
	public Result checkFirst(String xpath, String first) {
		try {
			waitForAjax();
			result.addLog("Click First : " + first);
			driver.findElement(By.xpath(first)).click();
			waitForAjax();
			if (getCurrentPage(xpath) != 1) {
				result.setResult("Fail");
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at checkFirst : " + xpath);
			result.setResult("Fail");
		}
		return result;
	}

	private int getCurrentPage(String xpath) {
		try {
			waitForAjax();
			WebElement page = driver.findElement(By.xpath(xpath));
			WebElement currentPage = page.findElement(By
					.className("paginate_active"));
			return Integer.parseInt(currentPage.getText());
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at getCurrentPage : " + xpath);
		}
		return 0;
	}

	private int getSizePage(String xpath) {
		try {
			waitForAjax();
			WebElement page = driver.findElement(By.xpath(xpath));
			List<WebElement> pages = page.findElements(By.tagName("a"));
			return pages.size();
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at getCurrentPage : " + xpath);
		}
		return 0;
	}

	public ArrayList<String> getListDataRow(String rowXpath) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			waitForAjax();
			WebElement row = driver.findElement(By.xpath(rowXpath));
			List<WebElement> elements = row.findElements(By.tagName("td"));
			for (WebElement element : elements) {
				result.addLog(element.getText());
				list.add(element.getText());
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at getListDataRow : "
					+ rowXpath);
		}
		return list;
	}

	public Result verifyListDataWithListXpath(ArrayList<String> listData,
			ArrayList<String> listXpath) {
		waitForAjax();
		String text = new String();
		// check 0 : if variants is 0 -> ""
		if (listData.get(2).equals("0")) {
			System.out.println("check 0");
			listData.set(2, "");
		}
		for (int i = 1; i < listData.size(); i++) {
			text = "";
			try {
				if (driver.findElements(By.xpath(listXpath.get(i - 1))).size() < 1) {
					break;
				}
				text = driver.findElement(By.xpath(listXpath.get(i - 1)))
						.getText();
				if (listData.get(i).equalsIgnoreCase(text)
						|| text.equalsIgnoreCase("CLOSED")
						|| text.contains(listData.get(i))
						|| text.equalsIgnoreCase("Pending DTS Review")
						|| text.equalsIgnoreCase("UNSUBMITTED")
						|| text.equalsIgnoreCase("")) {
					result.addLog("Data correct " + text + ":"
							+ listData.get(i));
				} else {
					result.setResult("Fail");
					result.addLog("Data incorrect " + text + ":"
							+ listData.get(i));
				}
			} catch (NoSuchElementException e) {
				result.addLog("NoSuchElementException :  "
						+ listXpath.get(i - 1));
				result.setResult("Fail");
			}
		}
		return result;
	}

	public Result selectItemAt(String modelVariants, int index, String waitItem) {
		try {
			waitForAjax();
			WebElement variant = driver.findElement(By.xpath(modelVariants));
			List<WebElement> listVariants = variant.findElements(By
					.tagName("a"));
			if (listVariants.size() == 0) {
				result.addLog("No variant!!!");
				result.setResult("Fail");
			} else if (index > listVariants.size()) {
				result.addLog("Out of range");
				result.setResult("Fail");
			} else {
				result.addLog("select at index : " + index);
				result.setExpected(listVariants.get(index).getText());
				listVariants.get(index).click();
				// wait element display
				waitForAjax();
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at selectItemAt :  "
					+ modelVariants);
			result.setResult("Fail");
		}
		return result;
	}

	public Result clickAndChooseOption(String xpath, String option) {
		// click
		try {
			waitForAjax();
			result.addLog("click : " + xpath);
			driver.findElement(By.xpath(xpath)).click();
			waitForAjax();
			Set<String> availableWindows = driver.getWindowHandles();
			if (!availableWindows.isEmpty()) {
				for (String windowId : availableWindows) {
					WebDriver popup = driver.switchTo().window(windowId);
					popup.findElement(By.linkText(option)).click();
					waitForAjax();
				}
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at selectItemAt :  " + xpath);
			result.setResult("Fail");
		}
		return result;
	}

	public Result editVariantSave(String editVariant, String fieldXpath,
			String data, String editXpath) {
		try {
			waitForAjax();
			// GET DATA
			result.addLog("click : " + editVariant);
			result = clickAndWaitElementDisplayed(editVariant, editXpath);
			editData(editXpath, data);
			driver.findElement(By.linkText("Save")).click();
			waitForAjax();
			if (driver.findElement(By.xpath(fieldXpath)).getText().equals(data)) {
				result.addLog("Data have been changed successful : " + data);
			} else {
				result.addLog("can't change data : " + data);
				result.setResult("Fail");
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at editVariantSave :  "
					+ fieldXpath);
			result.setResult("Fail");
		}
		return result;
	}

	public Result editVariantCancel(String editVariant, String fieldXpath,
			String data, String editXpath) {
		try {
			waitForAjax();
			// GET DATA
			String temp = driver.findElement(By.xpath(fieldXpath)).getText();
			result.addLog("click : " + editVariant);
			result = clickAndWaitElementDisplayed(editVariant, editXpath);
			editData(editXpath, data);
			driver.findElement(By.linkText("Cancel")).click();
			waitForAjax();
			if (driver.findElement(By.xpath(fieldXpath)).getText().equals(temp)) {
				result.addLog("Data doesn't changed : " + data);
			} else {
				result.addLog("Data changed when cancel : " + data);
				result.setResult("Fail");
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at editVariantCancel :  "
					+ fieldXpath);
			result.setResult("Fail");
		}
		return result;
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

	public ArrayList<String> getAccessoriesDetail(
			ArrayList<String> listAccessoriesOnDetailPage) {
		ArrayList<String> listAccessoriesDetail = new ArrayList<String>();
		String currentItem = listAccessoriesOnDetailPage.get(0);
		try {
			waitForAjax();
			for (String item : listAccessoriesOnDetailPage) {
				currentItem = item;
				result.addLog(driver.findElement(By.xpath(item)).getText());
				listAccessoriesDetail.add(driver.findElement(By.xpath(item))
						.getText());
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException : " + currentItem);
		}
		return listAccessoriesDetail;
	}

	public Boolean arrayListContainsAllIgnoreCase(
			ArrayList<String> listAccessoriesDetail, ArrayList<String> listData) {
		for (int i = 0; i < listAccessoriesDetail.size(); i++) {
			if (listAccessoriesDetail.get(i).toLowerCase().contains("closed")) {
				result.addLog("Case CLOSE : " + listAccessoriesDetail.get(i));
				break;
			} else if (listAccessoriesDetail.get(i).equals("")) {
				result.addLog("Case empty : " + listAccessoriesDetail.get(i));
				break;
			} else if (!listAccessoriesDetail.get(i).toLowerCase()
					.contains(listData.get(i).toLowerCase())) {
				result.addLog("Case fail : "
						+ listAccessoriesDetail.get(i).toLowerCase() + ":"
						+ listData.get(i).toLowerCase());
				return false;
			}
		}
		return true;
	}

	// Get text by Xpath
	public String getTextByXpath(String xpath) {
		try {
			waitForAjax();
			String text = driver.findElement(By.xpath(xpath)).getText();
			result.addLog("Text : " + text);
			
			return text;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at getTextByXpath : " + xpath);
		}
		return "";
	}
	
	// Get text by Xpath
		public ArrayList<String> getTextByXpath(ArrayList<String> listXpath) {
			try {
				ArrayList<String> list = new ArrayList<String>();
				for(String item : listXpath){
					String text = driver.findElement(By.xpath(item)).getText();
					result.addLog("Text: " + text);
					list.add(text);
				}
				return list;
			} catch (NoSuchElementException e) {
				result.addLog("NoSuchElementException at getTextByXpath : " + listXpath);
			}
			return null;
		}

	// Get text by Element
	public String getTextByXpath(WebElement element) {
		try {
			waitForAjax();
			String text = element.getText();
			result.addLog("Text : " + text);
			return text;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at getTextByXpath : "
					+ element);
		}
		return "";
	}

	public Result editAccessoriesAndSave(String xpath, String data,
			String resultXpath) {
		// get data before edit
		try {
			waitForAjax();
			editData(xpath, data);
			result.addLog("Click Save");
			driver.findElement(By.linkText("Save")).click();
			waitForAjax();
			if (driver.findElement(By.xpath(resultXpath)).getText()
					.equals(data)) {
				result.addLog("Data changed : " + data);
			} else {
				result.addLog("can't change data : " + data);
				result.setResult("Fail");
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at editAccessoriesAndSave : "
					+ xpath);
			result.setResult("Fail");
		}
		return result;
	}

	public Result editAccessoriesAndCancel(String xpath, String xpathResult,
			String url) {
		try {
			waitForAjax();
			String data = RandomStringUtils.randomAlphabetic(20);
			editData(xpath, data);
			result.addLog("Click Cancel");
			driver.findElement(By.linkText("Cancel")).click();
			waitForAjax();
			if (driver.getCurrentUrl().equals(url)
					&& !driver.findElement(By.xpath(xpathResult)).getText()
							.equals(data)) {
				result.addLog("Data not changed!");
				return result;
			} else {
				result.setResult("Fail");
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException : " + xpathResult);
			System.err.println("Data changed or back to incorrect page!");
			result.setResult("Fail");
		}
		return result;
	}

	public Result clickAndCheckElements(String xpath,
			ArrayList<String> allElements) {
		try {
			waitForAjax();
			driver.findElement(By.xpath(xpath)).click();
			// wait loading
			waitForAjax();
			result = existsElement(allElements);
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at clickAndCheckElements : "
					+ xpath);
			result.setResult("Fail");
		}
		return result;
	}

	public Result selectAndCheckMessage(String save, String errorMsg) {
		try {
			waitForAjax();
			driver.findElement(By.xpath(save)).click();
			waitForAjax();
			if (driver.getPageSource().contains(errorMsg)) {
				result.addLog("Error message displayed!");
				return result;
			} else {
				result.setResult("Fail");
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at clickAndCheckElements : "
					+ save);
			result.setResult("Fail");
		}
		return result;
	}

	public Result checkValue(String xpath, String value) {
		try {
			waitForAjax();
			if (driver.findElement(By.xpath(xpath)).getText().contains(value)) {
				result.addLog("Value correct : " + value);
			} else {
				result.addLog("Value incorrect : " + value);
				result.setResult("Fail");
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException : " + xpath);
			result.setResult("Fail");
		}
		return result;
	}

	public int getNumOfImageMarketingMaterials(String marketingMaterials) {
		try {
			waitForAjax();
			WebElement img = driver.findElement(By.xpath(marketingMaterials));
			return img.findElements(By.tagName("a")).size() / 2;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at getNumOfImageMarketingMaterials : "
					+ marketingMaterials);
		}
		return 0;
	}

	public Result clickDeleteImgMarketingMaterials(String marketingMaterials) {
		int currentSize = getNumOfImageMarketingMaterials(marketingMaterials);
		try {
			waitForAjax();
			WebElement img = driver.findElement(By.xpath(marketingMaterials));
			List<WebElement> elements = img.findElements(By.tagName("a"));
			for (WebElement webElement : elements) {
				if (webElement.getText().equals("Delete")) {
					webElement.click();
					waitForAjax();
					break;
				}
			}
			Set<String> availableWindows = driver.getWindowHandles();
			if (!availableWindows.isEmpty()) {
				for (String windowId : availableWindows) {
					WebDriver popup = driver.switchTo().window(windowId);
					result.addLog("Click Delete");
					popup.findElement(By.linkText("Delete")).click();
					waitForAjax();
				}
			}
			if (currentSize == getNumOfImageMarketingMaterials(marketingMaterials) - 1) {
				result.addLog("Deleted");
				return result;
			}
		} catch (NoSuchElementException e) {
			result.setResult("Fail");
			result.addLog("NoSuchElementException : " + marketingMaterials);
		}
		return result;
	}

	public Result clickAnAccessoryHaveImage(String body, String imageFile,
			String paginate) {
		// find accessory have image and click
		Boolean flag = false;
		try {
			// body display
			waitForAjax();
			// click next
			driver.findElement(By.linkText("Last")).click();
			// sleep for load data
			waitForAjax();
			// get list all row
			WebElement table = driver.findElement(By.xpath(body));
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				WebElement img = row.findElement(By.tagName("img"));
				if (img.getAttribute("src").length() > 0) {
					System.out.println("src : " + img.getAttribute("src"));
					WebElement link = row.findElement(By.tagName("a"));
					result.addLog("Click accessory : " + link.getText());
					link.click();
					waitForAjax();
					flag = true;
					break;
				} else {
					System.err.println("src = null");
				}
			}
		} catch (NoSuchElementException e) {
			result.setResult("Fail");
			result.addLog("NoSuchElementException at clickAnAccessoryHaveImage: "
					+ body);
		}
		if (!flag) {
			result.setExpected("No Image");
		}
		return result;
	}

	private int getSizePaginate(String paginate) {
		try {
			waitForAjax();
			System.out.println("on : getSizePaginate");
			WebElement page = driver.findElement(By.xpath(paginate));
			WebElement table = page.findElement(By.tagName("span"));
			List<WebElement> pages = table.findElements(By.tagName("a"));
			return pages.size();
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at getSizePaginate : "
					+ paginate);
		}
		return 0;
	}

	public Result deleteImageFileAndCheck(String imageFile) {
		System.out.println("On : deleteImageFileAndCheck");
		try {
			waitForAjax();
			WebElement imageRow = driver.findElement(By.xpath(imageFile));
			int totalImg = imageRow.findElements(By.tagName("img")).size();
			if (totalImg > 0) {
				WebElement img = imageRow.findElement(By.tagName("img"));
				String src = img.getAttribute("src");
				result.addLog("src : " + src);
				// get link delete and click
				WebElement delete = imageRow.findElement(By.tagName("a"));
				if (delete.getText().equals("Delete")) {
					delete.click();
					// wait pop up
					waitForAjax();
					switchWindowClickOption("Delete");
					// wait delete
					waitForAjax();
					// check img deleted
					// find element
					imageRow = driver.findElement(By.xpath(imageFile));
					int remainImg = imageRow.findElements(By.tagName("img"))
							.size();
					if (totalImg > remainImg) {
						result.addLog("Image deleted");
					} else {
						result.addLog("Can't delete img : " + src);
						result.setResult("Fail");
					}
				}
			} else {
				result.addLog("No image file for delete");
			}

		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException : " + imageFile);
			result.setResult("Fail");
		}
		return result;
	}

	public void switchWindowClickOption(String option) {
		waitForAjax();
		// Get all available windows
		Set<String> availableWindows = driver.getWindowHandles();
		if (!availableWindows.isEmpty()) {
			for (String windowId : availableWindows) {
				WebDriver popup = driver.switchTo().window(windowId);
				result.addLog("Click " + option);
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

	public Result findAndGoPageExistVariant(String tbodyXpath, String paginate) {
		try {
			waitForAjax();
			// find row exist variant
			// count pages
			int sizePage = getSizePaginate(paginate);
			// check variant page
			for (int i = 0; i < sizePage; i++) {
				if (checkExistVariant(tbodyXpath, 2)) {
					result.addLog("On page exist Variant");
					return result;
				} else {
					result.addLog("Next Page");
					driver.findElement(By.linkText("Next")).click();
					// wait for load
					waitForAjax();
				}
			}
			// doesn't existed page have variant
			result.addLog("doesn't existed page have variant");
			result.setResult("Fail");
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at findAndGoPageExistVariant : "
					+ tbodyXpath);
			result.setResult("Fail");
		}
		return result;
	}

	private boolean checkExistVariant(String tbodyXpath, int index) {
		try {
			waitForAjax();
			// get tbody element
			WebElement tbody = driver.findElement(By.xpath(tbodyXpath));
			// get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			// get each column of row
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if (Integer.parseInt(columns.get(index).getText()) > 0) {
					result.addLog("Variant existed");
					return true;
				}
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException on checkExistVariant");
		}
		return false;
	}

	public Result findElementExistVariantAndClick(String brandAccessoriesTable,
			String modelVariants) {
		Boolean flag = false;
		try {
			waitForAjax();
			WebElement linkElement = getRowExistVariant(brandAccessoriesTable);
			if (linkElement != null) {
				// click
				linkElement.findElement(By.tagName("a")).click();
				// wait
				waitForAjax();
				flag = true;
			} else {
				result.addLog("Doesn't existed variant");
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at findElementExistVariantAndClick : "
					+ brandAccessoriesTable);
			result.setResult("Fail");
		}
		if (!flag) {
			result.setExpected("No Variant");
		}
		return result;
	}

	private WebElement getRowExistVariant(String tbodyXpath) {
		WebElement rowVariant = null;
		try {
			waitForAjax();
			// get tbody element
			// get tbody element
			WebElement tbody = driver.findElement(By.xpath(tbodyXpath));
			// get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			// get each column of row
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if (Integer.parseInt(columns.get(2).getText()) > 0) {
					result.addLog("Variant existed, name "
							+ columns.get(1).getText());
					rowVariant = columns.get(1);
					break;
				}
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException on checkExistVariant "
					+ tbodyXpath);
		}
		return rowVariant;
	}

	public Result clickLinkAndWaitElementDisplayed(String modelVariants,
			String editVariant) {
		result.addLog("on : clickLinkAndWaitElementDisplayed");
		Boolean flag = false;
		try {
			waitForAjax();
			WebElement model = driver.findElement(By.xpath(modelVariants));
			List<WebElement> variants = model.findElements(By.tagName("a"));
			if (variants.size() > 0) {
				result.addLog("Click variant");
				variants.get(0).click();
				waitForAjax();
				flag = true;
			} else {
				result.addLog("No variant existed!");
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException " + modelVariants);
			result.setResult("Fail");
		}
		if (!flag) {
			result.setExpected("No Variant");
		}
		return result;
	}

	public Result deleteMaketingMaterialFile(String marketingMaterials) {
		result.addLog("On : deleteMaketingMaterialFile");
		try {
			waitForAjax();
			WebElement maketingImgWebElement = driver.findElement(By
					.xpath(marketingMaterials));
			List<WebElement> hrefs = maketingImgWebElement.findElements(By
					.tagName("a"));
			int currentSize = hrefs.size();
			if (hrefs.size() > 1) {
				if (hrefs.get(1).getText().equals("Delete")) {
					hrefs.get(1).click();
					waitForAjax();
					// wait popup
					switchWindowClickOption("Delete");
					// sleep for reload
					waitForAjax();
					// check img delete
					if (driver.findElements(By.xpath(marketingMaterials))
							.size() > 0) {
						maketingImgWebElement = driver.findElement(By
								.xpath(marketingMaterials));
						hrefs = maketingImgWebElement.findElements(By
								.tagName("a"));
						if (hrefs.size() < currentSize) {
							result.addLog("Delete sucessful");
						} else {
							result.addLog("Can't delete");
							result.setResult("Fail");
						}
					}
				} else {
					result.addLog("No image");
				}
			} else {
				result.addLog("No image for delete");
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException " + marketingMaterials);
			result.setResult("Fail");

		}
		return result;
	}

	public Result clickAndCheckElements(String xpath, String[] option) {
		try {
			waitForAjax();
			driver.findElement(By.xpath(xpath)).click();
			// wait loading
			waitForAjax();
			result = elementExist(option);
		} catch (NoSuchElementException e) {
			System.err
					.println("NoSuchElementException at  clickAndCheckElements : "
							+ xpath);
			result.setResult("Fail");
		}
		return result;
	}

	private Result elementExist(String[] elements) {
		result.addLog("Check all element by array");
		waitForAjax();
		for (String element : elements) {
			Boolean isPresent = driver.findElements(By.xpath(element)).size() > 0;
			if (!isPresent) {
				result.addLog("Element " + element + " doesn't existed!");
				result.setResult("Fail");
			} else {
				result.addLog("Element "
						+ driver.findElement(By.xpath(element)).getText()
						+ " displayed!");
			}
		}
		return result;
	}

	public Result clickOptionAndCheckFilterSelected(String xpath, String name) {
		waitForAjax();
		// Select Report Filter and choose "name" item and waitting for page
		// load
		result = clickOption(xpath, name);
		// chech Filter have default value "name"
		if (result.getResult().equals("Pass")) {
			result = isCorrectItemSelected(xpath, name);
			return result;
		} else {
			result.addLog("Can't click option");
			return result;
		}
	}

	public Result checkTimeAdded(String tableBody, int index, int days) {
		waitForAjax();
		// get total page
		int totalPage = totalPage();
		// get data column index
		ArrayList<String> listTimes = new ArrayList<String>();
		for (int i = 0; i < totalPage; i++) {
			listTimes = getListTextAt(tableBody, index);
			// check time within "days (14)" rolling days
			for (String time : listTimes) {
				if (!DateUtil.isBetween(time, days)) {
					result.addLog("Time doesn't within 14 rolling day");
					result.setResult("Fail");
					return result;
				} else {
					result.addLog("Time OK!");
				}
			}
			// click next page
			try {
				driver.findElement(By.linkText("Next")).click();
				waitForAjax();
			} catch (NoSuchElementException e) {
				result.addLog("NoSuchElementException : Next");
				result.setResult("Fail");
			}
		}
		return result;
	}

	private ArrayList<String> getListTextAt(String tableBody, int index) {
		ArrayList<String> listText = new ArrayList<String>();
		try {
			waitForAjax();
			WebElement tbody = driver.findElement(By.xpath(tableBody));
			// get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				String dayTime = columns.get(index).getText();
				result.addLog(dayTime);
				listText.add(dayTime);
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException");
			result.setResult("Fail");
		}
		return listText;
	}

	private int totalPage() {
		try {
			WebElement page = driver.findElement(By.xpath(Xpath.PAGE_INDEX));
			List<WebElement> pages = page.findElements(By.tagName("a"));
			return pages.size();
		} catch (NoSuchElementException e) {
			result.addLog("No Element : " + Xpath.PAGE_INDEX);
		}
		return 0;
	}

	public Result checkType(String tableBody, int index, String type) {
		waitForAjax();
		// get total page
		int totalPage = totalPage();
		// get data column index
		ArrayList<String> listType = new ArrayList<String>();
		for (int i = 0; i < totalPage; i++) {
			listType = getListTextAt(tableBody, index);
			// check time within "days (14)" rolling days
			for (String item : listType) {
				if (!item.equals(type)) {
					result.addLog("Type not match : " + item + " : " + type);
					result.setResult("Fail");
					return result;
				} else {
					result.addLog("Type match : " + item + " : " + type);
				}
			}
			// click next page
			try {
				driver.findElement(By.linkText("Next")).click();
				waitForAjax();
			} catch (NoSuchElementException e) {
				result.addLog("NoSuchElementException : Next");
				result.setResult("Fail");
			}
		}
		return result;
	}

	public ArrayList<String> getAllPartner(String menuViewAs) {
		ArrayList<String> partners = new ArrayList<String>();
		try {
			waitForAjax();
			result.addLog("-Click dropdown menu view as for choose partner");
			driver.findElement(By.xpath(menuViewAs)).click();
			WebElement ul = driver.findElement(By.xpath(xpathListItemPartner));
			List<WebElement> options = ul.findElements(By.tagName("li"));
			for (WebElement option : options) {
				result.addLog(option.getText());
				partners.add(option.getText());
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at getAllPartner : "
					+ menuViewAs);
			result.setResult("Fail");
		}
		return partners;
	}

	public String getStringByXpath(String xpath) {
		String text = "";
		try {
			waitForAjax();
			text = driver.findElement(By.xpath(xpath)).getText();
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException : " + xpath);
		}
		return text;
	}

	public Result checkTimeEnding(String tableBody, int index, int days) {
		waitForAjax();
		// get total page
		int totalPage = totalPage();
		// get data column index
		ArrayList<String> listTimes = new ArrayList<String>();
		for (int i = 0; i < totalPage; i++) {
			listTimes = getListTextAt(tableBody, index);
			// check time
			for (String time : listTimes) {
				if (DateUtil.isBeforeNow(time)) {
					result.addLog("Time is before Now : " + time);
					result.setResult("Fail");
					return result;
				}
			}
			// check time ending
			for (String time : listTimes) {
				if (!DateUtil.isBetween(time, days)) {
					result.addLog("Time doesn't within " + days
							+ " rolling day");
					result.setResult("Fail");
					return result;
				} else {
					result.addLog("Time OK!");
				}
			}
			// click next page
			try {
				driver.findElement(By.linkText("Next")).click();
				waitForAjax();
			} catch (NoSuchElementException e) {
				result.addLog("NoSuchElementException : Next");
				result.setResult("Fail");
			}
		}
		return result;
	}

	public Hashtable<String, String> getHashDataCompanyAt(String tableBody,
			int index) {
		Hashtable<String, String> data = new Hashtable<String, String>();
		try {
			// wait for data load
			waitForAjax();
			// get table
			WebElement table = driver.findElement(By.xpath(tableBody));
			// get all row
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			// get row at index
			if (index > rows.size()) {
				result.addLog("Out of range");
				return null;
			} else {
				WebElement rowAt = rows.get(index);
				List<WebElement> columns = rowAt.findElements(By.tagName("td"));
				result.addLog("size : " + columns.size());
				// set data to hash
				data.put("Company", columns.get(0).getText());
				data.put("Status", columns.get(1).getText());
				data.put("Partner Type", columns.get(2).getText());
				data.put("All Models", columns.get(3).getText());
				data.put("Published Models", columns.get(4).getText());
				data.put("Variants", columns.get(5).getText());
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException : getHashDataCompanyAt");
		}
		return data;
	}

	public Result clickDataAt(String tableBody, int index, String edit) {
		// get table
		WebElement table = driver.findElement(By.xpath(tableBody));
		// get all row
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		// get row at index
		if (index > rows.size()) {
			result.addLog("Out of range");
			result.setResult("Fail");
			return result;
		} else {
			WebElement rowAt = rows.get(index);
			List<WebElement> columns = rowAt.findElements(By.tagName("td"));
			// set data to hash
			result.addLog("Click : " + columns.get(0).getText());
			columns.get(0).findElement(By.tagName("a")).click();
			waitForAjax();
		}
		return result;
	}

	public Boolean checkMatchData(ArrayList<String> listInfo,
			Hashtable<String, String> hash) {
		try {
			waitForAjax();
			String name = driver.findElement(By.xpath(listInfo.get(0)))
					.getText();
			result.addLog("name : " + name);
			String status = driver.findElement(By.xpath(listInfo.get(1)))
					.getText();
			result.addLog("status : " + status);
			String partnerType = driver.findElement(By.xpath(listInfo.get(2)))
					.getText();
			result.addLog("partner Type : " + partnerType);
			if (!name.equals(hash.get("Company"))) {
				result.addLog("Name doesn't match : " + name + " : "
						+ hash.get("Company"));
				return false;
			} else if (!status.equals(hash.get("Status"))) {
				result.addLog("Status doesn't match : " + status + " : "
						+ hash.get("Status"));
				return false;
			} else if (!partnerType.equals(hash.get("Partner Type"))) {
				result.addLog("Partner Type doesn't match : " + partnerType
						+ " : " + hash.get("Partner Type"));
				return false;
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException");
			return false;
		}
		return true;
	}

	public Result addCompany(Hashtable<String, String> hashXpath,
			Hashtable<String, String> data) {
		try {
			waitForAjax();
			editData(hashXpath.get("id"), data.get("id"));
			selectOptionByName(hashXpath.get("type"), data.get("type"));
			// Select date
			click(hashXpath.get("date"));
			selectDateInCalendar(AddCompany.DATEPICKER_TABLE, data.get("date"));
			editData(hashXpath.get("name"), data.get("name"));
			editData(hashXpath.get("address"), data.get("address"));
			editData(hashXpath.get("city"), data.get("city"));
			editData(hashXpath.get("state"), data.get("state"));
			editData(hashXpath.get("zip"), data.get("zip"));
			selectOptionByName(hashXpath.get("country"), data.get("country"));
			selectOptionByName(hashXpath.get("partnerships"),
					data.get("partnerships"));
			waitForAjax();
			driver.findElement(By.xpath(hashXpath.get("add"))).click();
			waitForAjax();
			// save
			click(hashXpath.get("save"));
			waitForAjax();
		} catch (NoSuchElementException e) {
			result.addLog(e.toString());
			result.setResult("Fail");
			e.printStackTrace();
		}
		return result;
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

	public ArrayList<UserInfo> getUsers(String tbody) {
		ArrayList<UserInfo> users = new ArrayList<UserInfo>();
		try {
			waitForAjax();
			waitForLoadElement(tbody);
			WebElement body = driver.findElement(By.xpath(tbody));
			// get all rows
			List<WebElement> rows = body.findElements(By.tagName("tr"));
			// get data
			for (WebElement row : rows) {
				// get all colums
				List<WebElement> colums = row.findElements(By.tagName("td"));
				UserInfo info = new UserInfo();
				if (colums.size() == 6) {
					info.setFirstName(colums.get(0).getText());
					info.setLastName(colums.get(1).getText());
					info.setCompany(colums.get(2).getText());
					info.setTitle(colums.get(3).getText());
					info.setPhone(colums.get(4).getText());
					info.setEmail(colums.get(5).getText());
					// add to array list
					users.add(info);
				}

			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException on getUsers");
		}
		return users;
	}

	public ArrayList<PartnerUserInfo> getPartnerUsers(String tbody) {
		ArrayList<PartnerUserInfo> users = new ArrayList<PartnerUserInfo>();
		try {
			waitForAjax();
			waitForLoadElement(tbody);
			WebElement body = driver.findElement(By.xpath(tbody));
			// get all rows
			List<WebElement> rows = body.findElements(By.tagName("tr"));
			// get data
			for (WebElement row : rows) {
				// get all colums
				List<WebElement> colums = row.findElements(By.tagName("td"));
				PartnerUserInfo info = new PartnerUserInfo();
				if (colums.size() == 5) {
					info.setFirstName(colums.get(0).getText());
					info.setLastName(colums.get(1).getText());
					info.setTitle(colums.get(2).getText());
					info.setEmail(colums.get(3).getText());
					info.setPhone(colums.get(4).getText());
					// add to array list
					users.add(info);
				}

			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException on getPartnerUsers");
		}
		return users;
	}
	
	
	
	public String getTableCellValue(String xpath, int row, int column) {
		try {
			WebElement tbody = driver.findElement(By.xpath(xpath)).findElement(
					By.tagName("tbody"));
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			List<WebElement> columns = rows.get(row).findElements(
					By.tagName("td"));
			String cell_value = columns.get(column).getText();
			result.addLog("Value at row: " + row + " and colume: " + column
					+ " is: " + cell_value);
			return cell_value;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException: " + xpath);
			return "";
		}
	}
	
	public ArrayList<String> getTableRowValue(String tbody, int index) {
		try {
			waitForAjax();
			WebElement body = driver.findElement(By.xpath(tbody)).findElement(
					By.tagName("tbody"));
			// get row value from index
			ArrayList<String> list = new ArrayList<String>();
			List<WebElement> rows = body.findElements(By.tagName("tr"));
			List<WebElement> columns = rows.get(index).findElements(
					By.tagName("td"));
			for (int i = 0; i < columns.size(); i++) {
				result.addLog("Text: " + columns.get(i).getText());
				list.add(columns.get(i).getText());
			}
			return list;
		} catch (NoSuchElementException e) {
			result.addLog("No element exception");
			return null;
		}
	}

	public Result selectRowAt(String tbody, int index, String waitElement) {
		try {
			waitForAjax();
			WebElement body = driver.findElement(By.xpath(tbody));
			// get all rows
			List<WebElement> rows = body.findElements(By.tagName("tr"));
			// get data
			if (rows.size() >= index) {
				// get row at index
				WebElement rowAt = rows.get(index)
						.findElement(By.tagName("td"));
				WebElement link = rowAt.findElement(By.tagName("a"));
				link.click();
				waitForAjax();
			} else {
				result.addLog("Out of range or no data");
			}

		} catch (NoSuchElementException e) {
			result.setResult("Fail");
			result.addLog("NoSuchElementException");
		}
		return result;
	}

	public Result checkPartnerUserInfo(PartnerUserInfo userInfo,
			Hashtable<String, String> elementsInfo) {
		try {
			waitForAjax();
			String firstname = driver
					.findElement(By.xpath(elementsInfo.get("firstname"))).getText();
			String lastname = driver
					.findElement(By.xpath(elementsInfo.get("lastname"))).getText();
			String title = driver.findElement(
					By.xpath(elementsInfo.get("title"))).getText();
			String phone = driver.findElement(
					By.xpath(elementsInfo.get("phone"))).getText();
			String email = driver.findElement(
					By.xpath(elementsInfo.get("email"))).getText();
			// check name
			if (firstname.contains(userInfo.getFirstName())
					&& lastname.contains(userInfo.getLastName())) {
				result.addLog("name correct : " + firstname + " " + lastname);
			} else {
				result.addLog("name inccorect : "  + firstname + " " + lastname + " : "
						+ userInfo.getFirstName() + " "
						+ userInfo.getLastName());
				result.setResult("Fail");
			}
			// check title
			if (title.contains(userInfo.getTitle())) {
				result.addLog("title correct : " + title);
			} else {
				result.addLog("title inccorect : " + title + " : "
						+ userInfo.getTitle());
				result.setResult("Fail");
			}
			// check phone
			if (phone.contains(userInfo.getPhone())) {
				result.addLog("phone correct : " + phone);
			} else {
				result.addLog("phone inccorect : " + phone + " : "
						+ userInfo.getPhone());
				result.setResult("Fail");
			}
			// check email
			if (email.contains(userInfo.getEmail())) {
				result.addLog("email correct : " + email);
			} else {
				result.addLog("email inccorect : " + email + " : "
						+ userInfo.getEmail());
				result.setResult("Fail");
			}
		} catch (NoSuchElementException e) {
			result.setResult("Fail");
			result.addLog("NoSuchElementException");
		}
		return result;
	}

	public Result checkUesrInfo(UserInfo userInfo,
			Hashtable<String, String> elementsInfo) {
		try {
			waitForAjax();
			String name = driver
					.findElement(By.xpath(elementsInfo.get("name"))).getText();
			String company = driver.findElement(
					By.xpath(elementsInfo.get("company"))).getText();
			String title = driver.findElement(
					By.xpath(elementsInfo.get("title"))).getText();
			String phone = driver.findElement(
					By.xpath(elementsInfo.get("phone"))).getText();
			String email = driver.findElement(
					By.xpath(elementsInfo.get("email"))).getText();
			// check name
			if (name.contains(userInfo.getFirstName())
					&& name.contains(userInfo.getLastName())) {
				result.addLog("name correct : " + name);
			} else {
				result.addLog("name inccorect : " + name + " : "
						+ userInfo.getFirstName() + " "
						+ userInfo.getLastName());
				result.setResult("Fail");
			}
			// check company
			if (company.contains(userInfo.getCompany())) {
				result.addLog("company correct : " + company);
			} else {
				result.addLog("company inccorect : " + company + " : "
						+ userInfo.getCompany());
				result.setResult("Fail");
			}
			// check title
			if (title.contains(userInfo.getTitle())) {
				result.addLog("title correct : " + title);
			} else {
				result.addLog("title inccorect : " + title + " : "
						+ userInfo.getTitle());
				result.setResult("Fail");
			}
			// check phone
			if (phone.contains(userInfo.getPhone())) {
				result.addLog("phone correct : " + phone);
			} else {
				result.addLog("phone inccorect : " + phone + " : "
						+ userInfo.getPhone());
				result.setResult("Fail");
			}
			// check email
			if (email.contains(userInfo.getEmail())) {
				result.addLog("email correct : " + email);
			} else {
				result.addLog("email inccorect : " + email + " : "
						+ userInfo.getEmail());
				result.setResult("Fail");
			}
		} catch (NoSuchElementException e) {
			result.setResult("Fail");
			result.addLog("NoSuchElementException");
		}
		return result;
	}

	public Boolean addUser(Hashtable<String,String> hashXpath, Hashtable<String,String> data) {
		// Add user
		try {
			waitForAjax();
			editData(hashXpath.get("firstName"), data.get("firstName"));
			editData(hashXpath.get("lastName"), data.get("lastName"));
			if (hashXpath.containsKey("company")) {
				selectOptionByName(hashXpath.get("company"),data.get("company"));
			}
			editData(hashXpath.get("title"), data.get("title"));
			editData(hashXpath.get("email"), data.get("email"));
			editData(hashXpath.get("code"), data.get("code"));
			editData(hashXpath.get("phone"), data.get("phone"));
			selectBrandPrivilege(hashXpath.get("brand"), data.get("brand"));
			// save
			if (data.containsKey("save")) {
				click(hashXpath.get("save"));
				// If user is not created successfully
				if(!checkMessageDisplay(AddUser.Success_Message[0])){
					Assert.assertTrue(false);
				}
			}
			waitForAjax();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			return null;
		}
		return true;
	}

	public Result addBrand(Hashtable<String, String> hashXpath,
			Hashtable<String, String> data) {
		try {
			waitForAjax();
			editData(hashXpath.get("name"), data.get("name"));
			editData(hashXpath.get("tag"), data.get("tag"));
			editData(hashXpath.get("alias"), data.get("alias"));
			editData(hashXpath.get("website"), data.get("website"));
			editData(hashXpath.get("overview"), data.get("overview"));
			editData(hashXpath.get("notice"), data.get("notice"));
			// Upload image
			uploadFile(hashXpath.get("img1"), data.get("img1"));
			uploadFile(hashXpath.get("img2"), data.get("img2"));
			uploadFile(hashXpath.get("img3"), data.get("img3"));
			// Save
			if (data.containsKey("save")) {
				driver.findElement(By.xpath(hashXpath.get("save"))).click();
				waitForAjax();
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException on addBrand ");
			result.setResult("Fail");
		}
		return result;
	}

	public Result verifyData(Hashtable<String, String> hash,
			Hashtable<String, String> info) {
		try {
			waitForAjax();
			Set<String> keys = hash.keySet();
			for (String key : keys) {
				System.out.println(key);
				String data = driver.findElement(By.xpath(hash.get(key)))
						.getText();
				if (data.contains(info.get(key))) {
					result.addLog("data correct ".concat(data).concat(" : ")
							.concat(info.get(key)));
				} else {
					result.addLog("data incorrect ".concat(data).concat(" : ")
							.concat(info.get(key)));
					result.setResult("Fail");
				}
			}
		} catch (NoSuchElementException e) {
			result.addLog(e.toString());
			result.setResult("Fail");
		}
		return result;
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

	public Boolean isElementPresent(WebElement element) {
		try {
			if (element == null) {
				return false;
			}
			Boolean isDisplayed = element.isDisplayed();
			if (isDisplayed) {
				System.out.println("Element displayed : " + element.getText());
				return true;
			} else {
				System.out.println("Element doesn't existed : "
						+ element.getText());
				return false;
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException on isElementPresent "
					+ element.getText());
			return false;
		}
	}

	public HashMap<String, String> getItemUserMenu(String xpath) {
		HashMap<String, String> data = new HashMap<String, String>();
		try {
			WebElement footer = driver.findElement(By.xpath(xpath));
			List<WebElement> columns = footer.findElements(By.tagName("a"));
			for (int i = 1; i <= columns.size(); i++) {
				data.put("option " + i, columns.get(i - 1).getAttribute("text"));
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException on getItemUserMenu " + xpath);
		}
		return data;
	}

	public ArrayList<DataPartnerAccessoriesTable> getListObject(String tbody) {
		ArrayList<DataPartnerAccessoriesTable> users = new ArrayList<DataPartnerAccessoriesTable>();
		try {
			waitForAjax();
			waitForLoadElement(tbody);
			WebElement body = driver.findElement(By.xpath(tbody));
			// get all rows
			List<WebElement> rows = body.findElements(By.tagName("tr"));
			// get data
			for (WebElement row : rows) {
				// get all colums
				List<WebElement> colums = row.findElements(By.tagName("td"));
				DataPartnerAccessoriesTable info = new DataPartnerAccessoriesTable();
				if (colums.size() == 8) {
					info.setPicture(colums.get(0).getText());
					info.setModel(colums.get(1).getText());
					info.setVariants(colums.get(2).getText());
					info.setPublished(colums.get(3).getText());
					info.setVersion(colums.get(4).getText());
					info.setTuning(colums.get(5).getText());
					info.setHeadphone(colums.get(6).getText());
					info.setMarketing(colums.get(7).getText());
					// add to array list
					users.add(info);
				}
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException on getUsers");
		}
		return users;
	}

	public Result addAccessoriesPartner(Hashtable<String, String> hashXpath,
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
			selectInputChannel(hashXpath.get("bluetooth"),
					data.get("bluetooth"));
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

	public Boolean fillAddAccessoriesDTS(Hashtable<String, String> hashXpath,
			Hashtable<String, String> data) {
		try {
			waitForAjax();
			selectOptionByName(hashXpath.get("company"), data.get("company"));
			selectOptionByName(hashXpath.get("brand"), data.get("brand"));
			selectInputChannel(hashXpath.get("wired"), data.get("wired"));
			selectInputChannel(hashXpath.get("bluetooth"),
					data.get("bluetooth"));
			editData(hashXpath.get("id"), data.get("id"));
			editData(hashXpath.get("name"), data.get("name"));
			editData(hashXpath.get("number"), data.get("number"));
			editData(hashXpath.get("upc"), data.get("upc"));
			selectOptionByName(hashXpath.get("type"), data.get("type"));
			editData(hashXpath.get("description"), data.get("description"));
			uploadFile(hashXpath.get("add tunning"), data.get("add tunning"));
			uploadFile(hashXpath.get("add marketing"),
					data.get("add marketing"));
			return true;
		} catch (NoSuchElementException e) {
			result.addLog(e.toString());
		}
		return false;
	}

	public Boolean fillAddAccessoriesPartner(
			Hashtable<String, String> hashXpath, Hashtable<String, String> data) {
		try {
			waitForAjax();
			selectFirstOption(hashXpath.get("brand"));
			selectInputChannel(hashXpath.get("wired"), data.get("wired"));
			selectInputChannel(hashXpath.get("bluetooth"),
					data.get("bluetooth"));
			editData(hashXpath.get("name"), data.get("name"));
			editData(hashXpath.get("number"), data.get("number"));
			editData(hashXpath.get("upc"), data.get("upc"));
			selectOptionByName(hashXpath.get("type"), data.get("type"));
			editData(hashXpath.get("description"), data.get("description"));

			return true;
		} catch (NoSuchElementException e) {
			result.addLog(e.toString());
		}
		return false;
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

	public Result partnerSelectUserByEmail(String email) {
		Boolean flag = true;
		int limit = getPageSize();
		try {
			while (flag) {
				if (driver.getPageSource().contains(email)) {
					flag = false;
					// find and click user by email
					// step 1 : find element tbody
					WebElement tbody = driver.findElement(By.tagName("tbody"));
					// step 2 : find all row
					List<WebElement> rows = tbody
							.findElements(By.tagName("tr"));
					// step 3 : for all row get element contain email and click
					for (WebElement row : rows) {
						// get all colums
						List<WebElement> colums = row.findElements(By
								.tagName("td"));
						if (colums.size() == 5) {
							if (colums.get(4).getText().contains(email)) {
								// found element
								// click element
								WebElement link = colums.get(0).findElement(
										By.tagName("a"));
								link.click();
								waitForAjax();
								return result;
							}
						}

					}
				}
				limit--;
				if (limit < 0) {
					flag = false;
				}
				driver.findElement(By.linkText("Next")).click();
				waitForAjax();
			}
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : " + email);
			result.setResult("Fail");
		}
		return result;
	}

	public Boolean existsElement(String name) {
		return isElementPresent(name);
	}

	public Boolean disablePrivilege(String xpath, String privilege) {
		try {
			// get table
			WebElement tbody = driver.findElement(By.xpath(xpath)).findElement(By.tagName("tbody"));
			// get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if (columns.get(0).getText().contains(privilege)) {
					// check privilege
					WebElement checkbox = columns.get(0).findElement(By.tagName("input"));
					if (checkbox.isSelected()) {
						result.addLog("Disable : " + privilege);
						checkbox.click();
						waitForAjax();
						return true;
					}
					result.addLog("Privilege disabled, break");
					return true;
				}
			}
			result.addLog("Privilege: " + privilege + " not found");
			return false;
		} catch (NoSuchElementException e) {
			result.addLog("No element exception");
			return false;
		}
	}

	public Boolean enablePrivilege(String xpath, String privilege) {
		try {
			// get table
			WebElement tbody = driver.findElement(By.xpath(xpath)).findElement(
					By.tagName("tbody"));
			// get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if (columns.get(0).getText().contains(privilege)) {
					// check privilege
					WebElement checkbox = columns.get(0).findElement(By.tagName("input"));
					if (!checkbox.isSelected()) {
						result.addLog("Enable: " + privilege);
						checkbox.click();
						waitForAjax();
						if(columns.size() == 3 && columns.get(1).getText().equals("None selected")){
							selectBrandPrivilegeInTable(privilege, "All brands");
						}
					}else{
						result.addLog("Privilege enabled, break");
					}
					return true;
				}
			}
			result.addLog("Privilege: " + privilege + " not found");
			return false;
			
		} catch (NoSuchElementException e) {
			result.addLog("No element exception");
			return false;
		}
	}
	
	public Boolean enablePrivilege(String xpath, ArrayList<String> privileges) {
		try {
			// get table
			WebElement tbody = driver.findElement(By.xpath(xpath)).findElement(
					By.tagName("tbody"));
			// get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (String privilege : privileges) {
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if (columns.get(0).getText().contains(privilege)) {
						// check privilege
						WebElement privilegeCheckbox = columns.get(0)
								.findElement(By.tagName("input"));
						if (!privilegeCheckbox.isSelected()) {
							result.addLog("Enable: " + privilege);
							privilegeCheckbox.click();
							waitForAjax();
							if (columns.size() == 3	&& columns.get(1).getText().equals("None selected")){
								selectBrandPrivilegeInTable(privilege,
										"All Brand");
							}
							break;
						} else {
							result.addLog("Privilege enabled, break");
							break;
						}
					}
				}
			}
			// Work around due to issue PDPP-836
			if (driver.findElement(By.xpath(AddUser.BRAND_PRIVILEGES))
					.findElement(By.tagName("button")).getText()
					.equals("None selected")) {
				selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, "All Brand");
			}
			// End work around
			clickText("Save");
		} catch (NoSuchElementException e) {
			result.addLog("No element exception");
			return false;
		}
		return true;
	}

	public String selectAnAccessory() {
		String accessoryName = "";
		try {
			// get table
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			// get a row
			WebElement row = tbody.findElements(By.tagName("tr")).get(0);
			// Select an accessory
			WebElement link = row.findElement(By.tagName("a"));
			result.addLog("Select accessory: " + link.getText());
			accessoryName = link.getText();
			link.click();
			waitForAjax();
		} catch (NoSuchElementException e) {
			result.addLog("No element exception");
			return null;
		}
		return accessoryName;
	}

	public Boolean selectAnAccessoryDTS() {
		try {
			// get table
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			// get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				WebElement link = row.findElement(By.tagName("a"));
				link.click();
				waitForAjax();
				break;
			}
		} catch (NoSuchElementException e) {
			System.err.println("No element exception");
			return false;
		}
		return true;
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

	public ArrayList<String> getStandardAccessory() {
		ArrayList<String> data = new ArrayList<String>();
		try {
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				String text = columns.get(1).getText();
				data.add(text);
			}
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : getStandardAccessory ");
		}
		return data;
	}

	public ArrayList<String> getHeadColumByXpath(String xpath) {
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

	public Boolean isTextPresent(String text) {
		/*
		 * int size = driver.findElements(By.linkText(text)).size(); if(size >
		 * 0){ System.out.println(text + " is present"); return true; }else{
		 * System.err.println(text + " doesn't existed"); return false; }
		 */
		try {
			if (driver.findElement(By.xpath("//*[contains(.,'" + text + "')]")) != null) {
				return true;
			}
		} catch (NoSuchElementException e) {
			return false;
		}

		return driver.getPageSource().contains(text);
	}

	public void selectReportsFilterByText(String xpath, String option) {
		try {
			new Select(driver.findElement(By.xpath(xpath)))
					.selectByVisibleText(option);
			waitForAjax();
		} catch (NoSuchElementException e) {
		}

	}

	public Boolean canEdit(String xpath) {
		try {
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

	public Boolean canEditHeadphoneRating(String xpath) {
		try {
			List<WebElement> inputs = driver.findElements(By.xpath(xpath));
			if (inputs.size() > 1) {
				return false;
			} else {
				return canEdit(xpath);
			}
		} catch (NoSuchElementException e) {
			System.err.println("No element exception");
		}
		return false;
	}

	public Boolean deleteFileByXpath(String xpath) {
		try {
			WebElement webElement = driver.findElement(By.xpath(xpath));
			WebElement del = webElement.findElement(By.linkText("Delete"));
			del.click();
			switchWindowClickOption("Delete");
			waitForAjax();
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
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
	
	public static boolean testInet() {
	    Socket sock = new Socket();
	    InetSocketAddress addr = new InetSocketAddress("devportal.dts.com",80);
	    try {
	        sock.connect(addr,3000);
	        return true;
	    } catch (IOException e) {
	        return false;
	    } finally {
	        try {sock.close();}
	        catch (IOException e) {}
	    }
	}
	

	public void interuptNetwork() {
		try {
			Boolean network = testInet();
			while (network) {
				Process rt = Runtime.getRuntime()
						.exec(Constant.RELEASE_NETWORK);
				rt.waitFor();
				network = testInet();
			}
			result.addLog("Network connection status = " + network);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void connectNetwork() {
		try {
			Boolean network = testInet();
			while (network == false) {
				Process rt = Runtime.getRuntime().exec(Constant.RENEW_NETWORK);
				rt.waitFor();
				network = testInet();
			}
			result.addLog("Network connection status = " + network);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getItemSelected(String xpath) {
		return readField(xpath);
	}

	public String getHintText(String xpath) {
		try {
			waitForAjax();
			String text = driver.findElement(By.xpath(xpath)).getAttribute(
					"placeholder");
			result.addLog("Text : " + text);
			return text;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at getTextByXpath : " + xpath);
			return "";
		}
	}

	public String getAtribute(String xpath, String attribute) {
		try {
			waitForAjax();
			String text = driver.findElement(By.xpath(xpath)).getAttribute(
					attribute);
			result.addLog("Text : " + text);
			return text;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at getTextByXpath : " + xpath);
			return "";
		}
	}

	public int getNumElementDisplayedByText(String text) {
		int i = 0;
		try {
			List<WebElement> elements = driver
					.findElements(By.tagName("input"));
			for (WebElement webElement : elements) {
				if (webElement.getAttribute("placeholder").equals(text)) {
					i++;
				}
			}
		} catch (NoSuchElementException e) {
		}
		return i;
	}

	public Boolean enableAllPrivilege() {
		try {
			// get table
			List<WebElement> tbodys = driver.findElements(By.tagName("tbody"));
			// get all rows
			for (WebElement tbody : tbodys) {
				if(!tbody.isDisplayed()){
					break;
				}
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					// check privilege
					WebElement privilegeCheckbox = columns.get(0).findElement(
							By.tagName("input"));
					if (!privilegeCheckbox.isSelected()) {
						result.addLog("Enable privilege : "
								+ columns.get(0).getText());
						privilegeCheckbox.click();
						waitForAjax();
					}
				}
			}
			// Select brand privileges
			selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, "All brands");
		} catch (NoSuchElementException e) {
			result.addLog("No element exception");
			return false;
		}
		return true;
	}

	public String getInnerHTML(String xpath) {
		try {
			waitForAjax();
			String text = driver.findElement(By.xpath(xpath)).getAttribute(
					"innerHTML");
			result.addLog("innerHTML : " + text);
			return text;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at getTextByXpath : " + xpath);
			return "";
		}
	}

	public String getInnerHTML(WebElement web) {
		try {
			waitForAjax();
			String text = web.getAttribute("innerHTML");
			result.addLog("innerHTML : " + text);
			return text;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at getTextByXpath : " + web);
			return "";
		}
	}

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
			result.addLog("click :" + type);
			clickThis.selectByVisibleText(type);
			waitForAjax();
		} catch (NoSuchElementException e) {
			System.err.println("No such element exception : selectOption");
		}
		return type;
	}

	public ArrayList<String> getColumsByIndex(int i) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			int sizePage = getPageSize();
			for (int j = 0; j < sizePage; j++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> colums = row
							.findElements(By.tagName("td"));
					list.add(colums.get(i - 1).getText());
				}
			}
			clickText("Next");
		} catch (NoSuchElementException e) {
			System.err.println("No such element excetion : getColumnsByIndex");
		}
		HashSet<String> hs = new HashSet<String>();
		hs.addAll(list);
		list.clear();
		list.addAll(hs);
		return list;
	}

	public Boolean checkFilterWithData(String[] select) {
		if (select[0] != DeviceList.COMPANY_DEFAULT_FILTER) {
			// check all data shows up only company
			ArrayList<String> companies = getColumsByIndex(DeviceList.COMPANY_COLUMN);
			Boolean isCorrectCompany = DTSUtil.containOnlyString(companies,
					select[0]);
			if (!isCorrectCompany) {
				return isCorrectCompany;
			}
		}
		if (select[1] != DeviceList.BRAND_DEFAULT_FILTER) {
			// check all data shows up only company
			ArrayList<String> brands = getColumsByIndex(DeviceList.BRAND_COLUMN);
			Boolean isCorrectBrand = DTSUtil.containOnlyString(brands,
					select[1]);
			if (!isCorrectBrand) {
				return isCorrectBrand;
			}
		}
		if (select[2] != DeviceList.TYPE_DEFAULT_FILTER) {
			// check all data shows up only company
			ArrayList<String> types = getColumsByIndex(DeviceList.TYPE_COLUMN);
			Boolean isCorrectType = DTSUtil.containOnlyString(types, select[2]);
			if (!isCorrectType) {
				return isCorrectType;
			}
		}
		return true;
	}

	public String selectADeviceAt(int i) {
		String device = "";
		try {
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			if (rows.size() < i) {
				return "";
			}
			WebElement rowAt = rows.get(i);
			List<WebElement> columns = rowAt.findElements(By.tagName("td"));
			device = columns.get(2).getText();
			System.out.println("Click : " + device);
			columns.get(2).findElement(By.tagName("a")).click();
			waitForAjax();
		} catch (NoSuchElementException e) {
		}
		return device;
	}

	public ArrayList<String> getAllLinkA(String xpath) {
		ArrayList<String> links = new ArrayList<String>();
		try {
			WebElement action = driver.findElement(By.xpath(xpath));
			List<WebElement> actions = action.findElements(By.tagName("a"));
			for (WebElement webElement : actions) {
				String act = webElement.getText();
				links.add(act);
			}
		} catch (NoSuchElementException e) {
			System.err.println("No such element exception : getAllLinkA");
		}
		return links;
	}

	public Boolean checkDataEquals(String titleCompany, String company) {
		try {
			WebElement title = driver.findElement(By.xpath(titleCompany));
			String titleText = title.getText();

			WebElement data = driver.findElement(By.xpath(company));
			String dataText = data.getText();
			return titleText.equals(dataText);
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public Boolean checkAllStringPresent(String[] requires) {
		String source = driver.getPageSource();
		for (String req : requires) {
			System.out.println("Check : " + req);
			if (!source.contains(req)) {
				System.err.println(req + " doesn't displayed");
				return false;
			}
		}
		return true;
	}

	public String[] getConfirm() {
		String confirm[] = new String[2];
		int i = 0;
		try {
			List<WebElement> links = driver.findElements(By.tagName("a"));
			for (WebElement webElement : links) {
				String href = webElement.getAttribute("href");
				if (href != null && href.contains("javascript:;")) {
					confirm[i] = webElement.getText();
					System.out.println("Confirm : " + confirm[i]);
					i++;
				}
			}
		} catch (NoSuchElementException e) {
			System.err.println("No Such element exception : getConfirm");
		}
		return confirm;
	}

	public String getStatuSwitch(String xpath) {
		String status = getTextByXpath(xpath);
		return status;
	}

	public Boolean fillDeviceEdit(HashMap<String, String> data) {
		try {
			editData(DeviceEdit.SALESFORCE_ID, data.get("id"));
			selectOptionByName(DeviceEdit.TYPE, data.get("type"));
			selectOptionByName(DeviceEdit.COMPANY, data.get("company"));
			selectOptionByName(DeviceEdit.BRAND, data.get("brand"));
			editData(DeviceEdit.NAME, data.get("name"));
			selectOptionByName(DeviceEdit.OS_LIST, data.get("os"));
			editData(DeviceEdit.INIT_KEY_NAME, data.get("keyName"));
			// editData(DeviceEdit.KEY_VALUE_INPUT, data.get("keyValue"));
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}

	public Boolean isClickable(String[] links) {
		try {
			for (String xpath : links) {
				WebElement element = driver.findElement(By.xpath(xpath));
				if (!element.isEnabled()) {
					return false;
				}
			}
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}

	public ArrayList<String> selectADeviceByName(String name) {
		try {
			int size = getPageSize();
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if (columns.get(2).getText().equals(name)) {
						for (WebElement column : columns) {
							list.add(column.getText());
						}
						result.addLog("Select app/device: " + name);
						columns.get(2).findElement(By.tagName("a")).click();
						waitForAjax();
						Thread.sleep(2000);
						waitForAjax();
						return list;
					}
				}
				clickText("Next");
			}
			result.addLog("App/Device: " + name + " not found");
			return null;
		} catch (NoSuchElementException e) {
			result.addLog("No element exception");
			return null;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Boolean selectAnaccessorybyName(String name) {
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

	public Boolean clickEditDevices() {
		try {
			if (isTextPresent("Create New Version")) {
				clickText("Create New Version");
			} else if (isTextPresent("Edit Version")) {
				clickText("Edit Version");
			}
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}

	public String selectACompanyAt(int i) {
		String company = "";
		try {
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			if (rows.size() < i) {
				return "";
			}
			WebElement rowAt = rows.get(i);
			List<WebElement> columns = rowAt.findElements(By.tagName("td"));
			company = columns.get(0).getText();
			System.out.println("Click : " + company);
			columns.get(0).findElement(By.tagName("a")).click();
			waitForAjax();
		} catch (NoSuchElementException e) {
			return "";
		}
		return company;
	}

	public Boolean selectACompanyByName(String companyName) {
		try {
			// Get page size to click next page
			int size = getPageSize();
			for (int i = 0; i < size; i++) {
				// Get the table
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (int j = 0; j < rows.size(); j++) {
					// Get all column from all rows
					List<WebElement> columns = rows.get(j).findElements(
							By.tagName("td"));
					// Get company name from first columns
					String company_name = columns.get(0).getText();
					// Click company if equal
					if (company_name.equals(companyName)) {
						result.addLog("Select company: " + companyName);
						columns.get(0).findElement(By.tagName("a")).click();
						waitForAjax();
						return true;
					}
				}
				clickText("Next");
			}
			result.addLog("Company:" + companyName + " not found");
			return false;

		} catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException");
		}
		return false;
	}

	public Boolean checkACompanyExistByName(String companyName) {
		try {
			// Get page size to click next page
			int size = getPageSize();
			for (int i = 0; i < size; i++) {
				// Get the table
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (int j = 0; j < rows.size(); j++) {
					// Get all column from all rows
					List<WebElement> columns = rows.get(j).findElements(
							By.tagName("td"));
					// Get company name from first columns
					String company_name = columns.get(0).getText();
					if (company_name.equals(companyName)) {
						result.addLog("Company: " + companyName + " exists");
						return true;
					}
				}
				clickText("Next");
			}
			result.addLog("Company: " + companyName + " not exist");
			return false;

		} catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException");
		}
		return false;
	}

	public String selectTableAt(int row, int column) {
		String data = "";
		try {
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			if (rows.size() < row) {
				return "";
			}
			WebElement rowAt = rows.get(row);
			List<WebElement> columns = rowAt.findElements(By.tagName("td"));
			data = columns.get(column).getText();
			System.out.println("Click : " + data);
			columns.get(column).findElement(By.tagName("a")).click();
			waitForAjax();
		} catch (NoSuchElementException e) {
			return "";
		}
		return data;
	}

	public TuningFile getTuningFile(String xpath) {
		TuningFile tuningFile = new TuningFile();
		try {
			WebElement anotherRoute = driver.findElement(By.xpath(xpath));
			WebElement tuning = anotherRoute.findElement(By.tagName("div"));
			List<WebElement> links = tuning.findElements(By.tagName("a"));
			if (links.size() == 2) {
				tuningFile.name = links.get(0).getAttribute("download");
				tuningFile.href = links.get(0).getAttribute("href");
				tuningFile.link = links.get(0).getText();
				tuningFile.btnDelete = links.get(1).getText();
				tuningFile.download = links.get(0);
				tuningFile.delete = links.get(1);
			}
		} catch (NoSuchElementException e) {
		}
		return tuningFile;
	}

	public void click(WebElement element) {
		try {
			result.addLog("Click element: //*[@id=" + element.getAttribute("id") + "]");
			element.click();
			waitForAjax();
		} catch (NoSuchElementException e) {
			result.addLog("Element: " + element + " is not present");
		}
	}

	public Boolean existsElement(String[] requiresXpath) {
		Boolean flag = true;
		for (String xpath : requiresXpath) {
			if (!isElementPresent(xpath)) {
				flag = false;
			}
		}
		return flag;
	}

	public ArrayList<String> createNewDevice(Hashtable<String,String> hashXpath, Hashtable<String,String> data) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			// Fill data
			editData(hashXpath.get("id"), data.get("id"));
			selectOptionByName(hashXpath.get("type"), data.get("type"));
			selectOptionByName(hashXpath.get("company"), data.get("company"));
			selectOptionByName(hashXpath.get("brand"), data.get("brand"));
			editData(hashXpath.get("name"), data.get("name"));
			selectCheckboxInDropdown(hashXpath.get("os"), data.get("os"));
			editData(hashXpath.get("license"), data.get("license"));
			// Upload default audio route
			uploadFile(hashXpath.get("audio route"), data.get("audio route"));
			// Upload custom tuning
			if(data.containsKey("custom tuning")){
				clickOptionByIndex(DeviceEdit.AUDIO_ROUTE_TYPE, 1);
				clickOptionByIndex(DeviceEdit.AUDIO_ROUTE, 1);
				uploadFile(hashXpath.get("custom tuning"), data.get("custom tuning"));
			}
			// Select Featured Accessory Promotions
			editData(hashXpath.get("promotion slot"), data.get("promotion slot"));
			if (data.get("global promotion") != null && data.get("global promotion") == "OFF") {
				click(hashXpath.get("global promotion"));
			}
			if(data.containsKey("save")){
				// click Save
				click(DeviceEdit.SAVE);
				// Get data after create
				if (data.get("id") != null) {
					list.add(getTextByXpath(DeviceInfo.SALESFORCE_ID));
				}
				list.add(getTextByXpath(DeviceInfo.TYPE).replace("s", ""));
				list.add(getTextByXpath(DeviceInfo.COMPANY));
				list.add(getTextByXpath(DeviceInfo.BRAND));
				list.add(getTextByXpath(DeviceInfo.NAME));
				if (data.get("os") != null) {
					list.add(getTextByXpath(DeviceInfo.OS));
				}
				list.add(getTextByXpath(DeviceInfo.INIT_KEY_NAME));
			}
			return list;
		} catch (NoSuchElementException e) {
			result.addLog("No element exception");
			return null;
		}
	}

	public void doDelete(String xpath) {
		click(xpath);
		selectConfirmationDialogOption("Delete");
	}

	public String getGlobleAlert(String globalAlert) {
		System.out.println("get Globle Alert");
		String error = "";
		try {
			int timeWait = 20;
			while (timeWait > 0) {
				if (isElementPresent(globalAlert)) {
					error = getTextByXpath(globalAlert);
					System.out.println("Error : " + error);
				}
				Thread.sleep(500);
				timeWait--;
			}
		} catch (NoSuchElementException e) {
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return error;
	}

	public void takeScreenShot(Boolean flag) {
		// take screen shot when fail
		System.out.println("Take screen shot if fail : " + flag);
		if (!flag) {
			try {
				System.out.println("Take screen shot");
				File scrnsht = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
				String destDir = "target/surefire-reports/screenshots";
				DateFormat dateFormat = new SimpleDateFormat(
						"dd_MMM_yyyy__hh_mm_ssaa");
				String destFile = dateFormat.format(new Date()) + ".png";
				org.apache.commons.io.FileUtils.copyFile(scrnsht, new File(
						destDir + "/" + destFile));
				result.addLog("View error : <a href=../screenshots/" + destFile
						+ ">Screenshot</a>");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Boolean selectRowByText(String dtsUser) {
		int size = getPageSize();
		try {
			for (int i = 0; i < size; i++) {
				if (driver.getPageSource().contains(dtsUser)) {
					// find and click user by email
					// step 1 : find element tbody
					WebElement tbody = driver.findElement(By.tagName("tbody"));
					// step 2 : find all row
					List<WebElement> rows = tbody
							.findElements(By.tagName("tr"));
					// step 3 : for all row get element contain email and click
					for (WebElement row : rows) {
						// get all colums
						List<WebElement> colums = row.findElements(By
								.tagName("td"));
						if (colums.size() > 0) {
							for (WebElement colum : colums) {
								if (colum.getText().contains(dtsUser)) {
									// found element
									// click element
									WebElement link = colums.get(0)
											.findElement(By.tagName("a"));
									link.click();
									waitForAjax();
									return true;
								}
							}
						}
					}
					driver.findElement(By.linkText("Next")).click();
					waitForAjax();
				} else {
					driver.findElement(By.linkText("Next")).click();
				}
			}
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : " + dtsUser);
			result.setResult("Fail");
		}
		return false;
	}

	public int getSizeTableList() {
		try {
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			return rows.size();
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : getSizeTableList");
		}
		return 0;
	}

	public int getTotalItem(String xpath) {
		try {
			String text = getTextByXpath(xpath);
			return StringUtilDts.getNumAtIndex(text, 2);
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : getTotalItem");
		}
		return 0;
	}

	public int getPerPage(String xpath) {
		try {
			String text = getTextByXpath(xpath);
			return StringUtilDts.getNumAtIndex(text, 1);
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : getPerPage");
		}
		return 0;
	}

	public String getTuningFileNameForAudioRoutes(String xpath, String tag) {
		try {
			WebElement element = driver.findElement(By.xpath(xpath));
			WebElement webElement = element.findElement(By.tagName(tag));
			return webElement.getAttribute("download");
		} catch (NoSuchElementException e) {
			System.err
					.println("No element exception : getTuningFileNameForAudioRoutes");
		}
		return "";
	}

	public ArrayList<String> getListWebElementByTag(String xpath, String tag) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			List<WebElement> list = driver.findElement(By.xpath(xpath))
					.findElements(By.tagName(tag));
			for (WebElement item : list) {
				data.add(item.getText());
			}
			return data;
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : getListWebElementByTag");
		}
		return null;
	}

	public WebElement getWebElementByText(String xpath, String name) {
		try {
			WebElement element = driver.findElement(By.xpath(xpath));
			List<WebElement> links = element.findElements(By.tagName("a"));
			for (WebElement link : links) {
				if (link.getText().equals(name)) {
					return link;
				}
			}
		} catch (NoSuchElementException e) {
		}
		return null;
	}

	public boolean isStepActive(String processStep) {
		try {
			String getClass = driver.findElement(By.xpath(processStep))
					.getAttribute("class");
			System.out.println("Class : " + getClass);
			return getClass.contains("process-step-container step-active");
		} catch (NoSuchElementException e) {
		}
		return false;
	}

	public boolean deleteAnaccessorybyName(String name) {
		click(Xpath.linkAccessories);
		selectAnaccessorybyName(name);
		doDelete(AccessoryInfo.DELETE);
		try {
			if (driver.findElement(By.xpath(AccessoryMain.ACCESSORY_TABLE))
					.isDisplayed()) {
				result.addSuccessLog("Product: " + name
						+ " is deleted successfully");
				return true;
			}
			result.addErrorLog("Product: " + name
					+ " is not deleted successfully");
			return false;
		} catch (NoSuchElementException e) {
			result.addErrorLog("Product: " + name
					+ " is not deleted successfully");
			return false;
		}
	}

	public List<LanguagePackage> getLanguagePackage(String xpath) {
		List<LanguagePackage> list = new ArrayList<LanguagePackage>();
		try {
			WebElement anotherpackage = driver.findElement(By.xpath(xpath));
			List<WebElement> eachpackage = anotherpackage.findElements(By
					.tagName("div"));

			for (WebElement item : eachpackage) {
				LanguagePackage languagepackage = new LanguagePackage();
				languagepackage.languagedropbox = item.findElement(By
						.tagName("select"));
				languagepackage.name = item.findElement(By.tagName("input"));
				languagepackage.trash = item.findElement(By.tagName("i"));
				if (!getAtribute(item.findElement(By.tagName("span")), "style")
						.contains("none")) {
					System.out.println("Set msg : -----------------------");
					languagepackage.warningmessage = getInnerHTML(item
							.findElement(By.tagName("span")));
					System.out.println("check here : "
							+ languagepackage.warningmessage);
				} else {
					languagepackage.warningmessage = "none";
				}
				list.add(languagepackage);
			}
		} catch (NoSuchElementException e) {
		}
		result.addLog("Number of language packages: " + list.size());
		return list;
	}

	public ArrayList<String> addAllLanguage(String Xpath) {
		try {
			waitForAjax();
			ArrayList<String> list = new ArrayList<String>();
			WebElement anotherpackage = driver.findElement(By.xpath(Xpath));

			for (int i = 0; i < 9; i++) {
				List<WebElement> all_package = anotherpackage.findElements(By
						.tagName("div"));
				result.addLog("Add language: "
						+ AudioRoutes.LANGUAGE_LIST[i + 1] + " : "
						+ AudioRoutes.NAME_LIST[i + 1]);
				selectOptionByName(
						all_package.get(i).findElement(By.tagName("select")),
						AudioRoutes.LANGUAGE_LIST[i + 1]);
				editData(all_package.get(i).findElement(By.tagName("input")),
						AudioRoutes.NAME_LIST[i + 1]);
				list.add(AudioRoutes.LANGUAGE_LIST[i + 1] + " : "
						+ AudioRoutes.NAME_LIST[i + 1]);
			}
			return list;

		} catch (NoSuchElementException e) {
			result.addErrorLog("NoSuchElementException" + Xpath);
			return null;
		}
	}

	public String getAtribute(WebElement element, String atr) {
		try {
			waitForAjax();
			String text = element.getAttribute(atr);
			result.addLog("Text : " + text);
			return text;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at getTextByXpath : " + element);
			return "";
		}
	}

	public boolean selectOptionByName(WebElement element, String type) {
		try {
			waitForAjax();
			Select clickThis = new Select(element);
			result.addLog("click :" + type);
			clickThis.selectByVisibleText(type);
			// wait data loading
			waitForAjax();
			return true;
		} catch (NoSuchElementException e) {
			result.setResult("Fail");
		}
		return false;

	}

	public boolean isItemInorderList(String xpath, String[] sizes) {
		try {
			WebElement element = driver.findElement(By.xpath(xpath));
			element = element.findElement(By.tagName("tbody"));
			// get all rows
			List<WebElement> rows = element.findElements(By.tagName("tr"));
			// get list colums of first rows
			List<WebElement> colums = rows.get(0)
					.findElements(By.tagName("td"));
			if (colums.size() == 3) {
				// verify size in order 160->290->566
				if (colums.get(0).getText().equals(sizes[0])
						&& colums.get(1).getText().equals(sizes[1])
						&& colums.get(2).getText().equals(sizes[2])) {
					return true;
				}
			}
		} catch (NoSuchElementException e) {
		}
		return false;
	}

	public boolean isRadioChecked(String xpath, int index) {
		try {
			WebElement element = driver.findElement(By.xpath(xpath));
			// get all radio buttons
			List<WebElement> all_buttons = element.findElements(By
					.tagName("label"));
			for (int i = 0; i < all_buttons.size(); i++) {
				Boolean status = all_buttons.get(index).isEnabled();
				if (status.equals("checked")) {
					return status;
				}
			}
		} catch (NoSuchElementException e) {
		}
		return false;
	}

	public Boolean isMarketingUploaded(String xpath, String filename) {
		try {
			List<WebElement> containers = driver.findElement(By.xpath(xpath))
					.findElements(By.tagName("tbody"));
			for (WebElement marketingFile : containers) {
				if (marketingFile.findElement(By.className("edit-media-space"))
						.getText().contains(filename)) {
					result.addLog("Marketing file: " + filename
							+ " is uploaded successfully");
					return true;
				}
			}
			result.addLog("Marketing file: " + filename
					+ " is not uploaded successfully");
			return false;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElement: " + xpath);
			return null;
		}
	}

	public Boolean isTunningUploaded(String xpath, String filename) {
		try {
			WebElement tuningUploaded = driver.findElement(By.xpath(xpath));
			if (tuningUploaded.getText().contains(filename) && tuningUploaded.getText().contains("File upload successful")){
				result.addLog("Tuning: " + filename
						+ " is uploaded successfully");
				return true;
			}
			result.addLog("Tuning: " + filename + " is not uploaded successfully");
			return false;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElemet: " + xpath);
			return null;
		}
	}

	public Boolean isImageUploaded(String xpath, String imagename) {
		try {
			WebElement image = driver.findElement(By.xpath(xpath)).findElement(By.tagName("img"));
			if(image.getAttribute("src").contains(imagename.replaceAll(".jpg", ""))){
				result.addLog("Image: " + imagename + " is uploaded successfully");
				return true;
			}
			result.addLog("Image: " + imagename + " is not uploaded successfully");
		} catch (NoSuchElementException e) {
			result.addLog("No element: " + xpath);
		}
		return false;
	}

	public Boolean dtsSelectUserByEmail(String email) {
		try {
			clickText("First");
			int limit = getPageSize();
			while (limit > 0) {
				// Get table
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				if (tbody.getText().contains(email)) {
					// Get all rows
					List<WebElement> rows = tbody.findElements(By.tagName("tr"));
					for (WebElement row : rows) {
						// Get all columns
						List<WebElement> colums = row.findElements(By.tagName("td"));
						if (colums.get(colums.size() - 2).getText().equals(email)) {
							// Found email
							// Select user by email
							result.addLog("Select user has email: " + email);
							colums.get(0).findElement(By.tagName("a")).click();
							waitForAjax();
							return true;
						}
					}
				}
				limit--;
				if (limit == 0) {
					break;
				}
				clickText("Next");
			}
			result.addLog("User has email: " + email + " not found");
			Assert.assertTrue(false);
			return false;
		} catch (NoSuchElementException e) {
			result.addLog("No element exception");
			Assert.assertTrue(false);
			return false;
		}
	}
	
	public ArrayList<String> selectUserByEmail(String email) {
		try {
			clickText("First");
			int limit = getPageSize();
			while (limit > 0) {
				ArrayList<String> list = new ArrayList<String>();
				// Get table
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				if (tbody.getText().contains(email)) {
					// Get all rows
					List<WebElement> rows = tbody.findElements(By.tagName("tr"));
					for (WebElement row : rows) {
						// Get all columns
						List<WebElement> colums = row.findElements(By.tagName("td"));
						if (colums.get(colums.size() - 2).getText().equals(email)) {
							// Get user data
							for(WebElement column : colums){
								list.add(column.getText());
							}
							// Select user by email
							result.addLog("Select user has email: " + email);
							colums.get(0).findElement(By.tagName("a")).click();
							waitForAjax();
							return list;
						}
					}
				}
				limit--;
				if (limit == 0) {
					break;
				}
				clickText("Next");
			}
			result.addErrorLog("User has email: " + email + " not found");
			Assert.assertTrue(false);
			return null;
		} catch (NoSuchElementException e) {
			result.addLog("No element exception");
			Assert.assertTrue(false);
			return null;
		}
	}

	public Boolean deleteUserByEmail(String email) {
		if (checkUserExistByEmail(email)) {
			selectUserByEmail(email);
			doDelete(UserMgmt.DELETE_ACCOUNT);
			if (isElementPresent(UsersList.USER_TABLE)) {
				result.addLog("User has email: " + email
						+ " is deleted successfully");
				return true;
			}
			result.addLog("User has email: " + email
					+ " is not deleted successfully");
			return false;
		}
		return false;
	}

	public Boolean isShowDialog() {
		String confirm[] = new String[2];
		int i = 0;
		try {
			List<WebElement> links = driver.findElements(By.tagName("a"));
			for (WebElement webElement : links) {
				String href = webElement.getAttribute("href");
				if (href != null && href.contains("javascript:;")) {
					confirm[i] = webElement.getText();
					System.out.println("Confirm : " + confirm[i]);
					i++;
				}
			}
		} catch (NoSuchElementException e) {
			System.err.println("No Such elemetn exception : getConfirm");
		}
		return confirm[0].equals("Cancel");
	}

	public String getWarningMsg(WebElement element) {
		String warning = getInnerHTML(element);
		String attr = getAtribute(element, "style");
		if (attr.contains("none")) {
			return "";
		}
		return warning;
	}

	public boolean checkBrandExist(String brandname) {
		String Xpath = CompanyInfo.BRAND_LIST;
		try {
			// Get brand list 
			WebElement brandlist = driver.findElement(By.xpath(Xpath));
			// Get all brands
			List<WebElement> brands = brandlist.findElements(By
					.tagName("div"));
			for (int i = 0; i < brands.size(); i++) {
				// Get brand name
				WebElement brand_name_tag = brands.get(i).findElement(
						By.tagName("a"));
				String brand_name = brand_name_tag.getText();
				if (brand_name.equals(brandname)) {
					result.addLog("Brand: " + brandname + " exists");
					return true;
				}
			}
			result.addLog("Brand: " + brandname + " does not exist");
			return false;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElement: " + Xpath);
		}
		return false;
	}

	public boolean checkBrandimagexist(String Xpath, String imageName) {
		try {
			// Get all brands in the brand table
			WebElement brandtable = driver.findElement(By.xpath(Xpath));
			WebElement brandcontainer = brandtable.findElement(By
					.tagName("tbody"));
			// Get each rows of brand table
			List<WebElement> each_brand = brandcontainer.findElements(By
					.tagName("tr"));
			for (int i = 0; i < each_brand.size(); i++) {
				// Get each columns of each row
				List<WebElement> image_column = each_brand.get(i).findElements(
						By.tagName("td"));
				// Get brand name
				WebElement brand_image_tag = image_column.get(0).findElement(
						By.tagName("a"));
				String brand_image = brand_image_tag.getText();
				if (brand_image.contains(imageName)) {
					return true;
				}
			}
		} catch (NoSuchElementException e) {
			System.out.println("Failed to locate Element");
		}
		return false;
	}

	public Boolean deleteAudioTuningFile(String xpath) {
		try {
			WebElement tuning = driver.findElement(By.xpath(xpath));
			List<WebElement> links = tuning.findElements(By.tagName("a"));
			for (WebElement link : links) {
				if (link.getText().equals("Delete")) {
					doDelete(link);
				}
			}
		} catch (NoSuchElementException e) {
			System.err
					.println("NoSuchElementException : deleteAudioTuningFile");
		}
		return false;
	}

	private void doDelete(WebElement link) {
		click(link);
		switchWindowClickOption("Delete");
	}

	public void doDeleteXpartner(WebElement link) {
		click(link);
		switchWindowClickOption("Delete Partnership");
	}

	public AudioTuning getAudioTuning(String xpath) {
		AudioTuning audioTuning = new AudioTuning();
		try {
			WebElement tuning = driver.findElement(By.xpath(xpath));
			List<WebElement> links = tuning.findElements(By.tagName("a"));
			if (links.size() > 0) {
				audioTuning.name = links.get(0).getText();
				audioTuning.link = links.get(0);
				audioTuning.delete = links.get(1);
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException : getAudioTuning");
			return null;
		}
		return audioTuning;
	}

	public boolean selectABrandByName(String brandname) {
		String Xpath=CompanyInfo.BRAND_LIST;
		try {
			
			// Get brand list
			WebElement brandlist = driver.findElement(By.xpath(Xpath));
			// Get all brands of brand list
			List<WebElement> brands = brandlist.findElements(By
					.tagName("div"));
			for (int i = 0; i < brands.size(); i++) {
				// Get brand name
				WebElement brand_name_tag = brands.get(i).findElement(
						By.tagName("a"));
				String brand_name = brand_name_tag.getText();
				if (brand_name.equals(brandname)) {
					result.addLog("Select brand: " + brandname);
					brand_name_tag.click();
					waitForAjax();
					return true;
				}
			}
			result.addLog("Brand: " + brandname + " not found");
			return false;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElement :" + Xpath);
		}
		return false;

	}

	public ArrayList<String> getPrivileges(String xpath) {
		ArrayList<String> privileges = new ArrayList<String>();
		try {
			WebElement table = driver.findElement(By.xpath(xpath)).findElement(
					By.tagName("tbody"));
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				result.addLog("Privilege: " + columns.get(0).getText());
				privileges.add(columns.get(0).getText());
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException : getPrivileges");
			return null;
		}
		return privileges;
	}

	public boolean fillDataAddUser(Hashtable<String, String> hashXpath,
			Hashtable<String, String> data) {
		try {
			waitForAjax();
			editData(hashXpath.get("firstName"), data.get("firstName"));
			editData(hashXpath.get("lastName"), data.get("lastName"));
			if (data.containsKey("company")) {
				selectOptionByName(hashXpath.get("company"),
						data.get("company"));
			}
			editData(hashXpath.get("title"), data.get("title"));
			editData(hashXpath.get("email"), data.get("email"));
			editData(hashXpath.get("code"), data.get("code"));
			editData(hashXpath.get("phone"), data.get("phone"));
			waitForAjax();
			return true;
		} catch (NoSuchElementException e) {
			result.addLog(e.toString());
			result.setResult("Fail");
			e.printStackTrace();
		}
		return false;
	}

	public ArrayList<DTSSitePrivilege> getDTSSitePrivileges(String xpath) {
		ArrayList<DTSSitePrivilege> privileges = new ArrayList<DTSSitePrivilege>();
		try {
			WebElement table = driver.findElement(By.xpath(xpath));
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if (columns.size() == 2) {
					DTSSitePrivilege userPrivilege = new DTSSitePrivilege();
					userPrivilege.value = columns.get(0).getText();
					userPrivilege.privileges = columns.get(0);
					userPrivilege.notifications = columns.get(1);
					privileges.add(userPrivilege);
				}
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException : getAddUserPrivileges");
		}
		return privileges;
	}

	public Boolean enableDTSSitePrivilege(DTSSitePrivilege userPrivilege) {
		try {
			String attr = userPrivilege.notifications.getAttribute("innerHTML");
			if (attr.contains("disabled")) {
				click(userPrivilege.privileges.findElement(By.tagName("input")));
				return true;
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException : enableUserPrivilege");
		}
		return false;
	}

	public Boolean enableSitePrivilege(SitePrivilege userPrivilege) {
		try {
			String attr = userPrivilege.notifications.getAttribute("innerHTML");
			if (attr.contains("disabled")) {
				click(userPrivilege.privileges);
				return true;
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException : enableUserPrivilege");
		}
		return false;
	}

	public boolean isCheckBoxEnable(WebElement checkBox) {
		try {
			return !checkBox.getAttribute("innerHTML").contains("disabled");
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException : checkBoxEnable");
		}
		return false;
	}

	public Boolean disableDTSSitePrivilege(DTSSitePrivilege userPrivilege) {
		try {
			String attr = userPrivilege.notifications.getAttribute("innerHTML");
			if (!attr.contains("disabled")) {
				click(userPrivilege.privileges.findElement(By.tagName("input")));
				return true;
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException : disableUserPrivilege");
		}
		return false;

	}

	public boolean isSelectedAllPrivileges(ArrayList<DTSSitePrivilege> list) {
		try {
			for (DTSSitePrivilege userPrivilege : list) {
				String attr = userPrivilege.notifications
						.getAttribute("innerHTML");
				if (attr.contains("disabled")) {
					return false;
				} else {
					System.out.println(userPrivilege.value + " is selected");
				}
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException : enableUserPrivilege");
		}
		return true;
	}

	public ArrayList<SitePrivilege> getSitePrivileges(String xpath) {
		ArrayList<SitePrivilege> privileges = new ArrayList<SitePrivilege>();
		SitePrivilege userPrivilege = new SitePrivilege();
		try {
			WebElement table = driver.findElement(By.xpath(xpath));
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if (columns.size() == 3) {
					userPrivilege.value = columns.get(0).getText();
					userPrivilege.privileges = columns.get(0);
					userPrivilege.brand = columns.get(1);
					userPrivilege.notifications = columns.get(2);
					privileges.add(userPrivilege);
				}
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException : getAddUserPrivileges");
		}
		return privileges;
	}

	public Boolean disableSitePrivilege(ArrayList<SitePrivilege> list) {
		try {
			for (SitePrivilege userPrivilege : list) {
				if (!disableSitePrivilege(userPrivilege)) {
					return false;
				}
			}
			return true;
		} catch (NoSuchElementException e) {
			System.err
					.println("NoSuchElementException : disableDTSSitePrivilege");
		}
		return false;

	}
	public Boolean uncheckNotification(ArrayList<SitePrivilege> list) {
		try {
			for (SitePrivilege userPrivilege : list) {
				String attr = userPrivilege.notifications
						.getAttribute("innerHTML");
				if (!attr.contains("disabled")) {
					click(userPrivilege.notifications.findElement(By
							.tagName("input")));
				}
			}
			return true;
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException : uncheckNotification");
		}
		return false;
	}

	public Boolean disableSitePrivilege(SitePrivilege sitePrivilege) {
		try {
			String attr = sitePrivilege.notifications.getAttribute("innerHTML");
			if (!attr.contains("disabled")) {
				click(sitePrivilege.privileges.findElement(By.tagName("input")));
			}
			return true;
		} catch (NoSuchElementException e) {
			System.err
					.println("NoSuchElementException : disableDTSSitePrivilege");
		}
		return false;
	}

	public XPartnerShipPackage getPartnershipPackage(String Xpath, int index) {
		waitForAjax();
		XPartnerShipPackage partnerPackage = new XPartnerShipPackage();
		try {
			// Get all brands in the brand table
			WebElement brandtable = driver.findElement(By.xpath(Xpath));
			List<WebElement> each_li = brandtable
					.findElements(By.tagName("li"));
			if (each_li.size() > index) {
				partnerPackage.type = each_li.get(index)
						.findElement(By.tagName("span")).getText();
				partnerPackage.deletelink = each_li.get(index).findElement(
						By.tagName("a"));
			}
		} catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException : ");
		}
		return partnerPackage;
	}
	
	public ArrayList<XPartnerShipPackage> getPartnershipPackage(String Xpath) {
		waitForAjax();
		ArrayList<XPartnerShipPackage> list = new ArrayList<XPartnerShipPackage>();
		try {
			// Get all brands in the brand table
			WebElement partnershipTable = driver.findElement(By.xpath(Xpath));
			List<WebElement> all_li = partnershipTable
					.findElements(By.tagName("li"));
			for (WebElement each_li : all_li) {
				XPartnerShipPackage partnerPackage = new XPartnerShipPackage();
				partnerPackage.type = each_li.findElement(By.tagName("span")).getText();
				result.addLog("Partnership: " + partnerPackage.type);
				partnerPackage.deletelink = each_li.findElement(By.tagName("a"));
				list.add(partnerPackage);
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException : " + Xpath);
		}
		return list;
	}

	public void selectAVariant(String Xpath) {
		try {
			// Get the Xpath
			WebElement model_variant = driver.findElement(By.xpath(Xpath));
			// Get the link
			click(model_variant.findElement(By.tagName("a")));
		} catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException");
		}
	}

	public Boolean selectAVariantbyName(String name) {
		try {
			// Get the variant container
			WebElement model_variant = driver.findElement(By.xpath(AccessoryInfo.MODEL_VARIANTS)).findElement(By.tagName("span"));
			// Get all variant
			List<WebElement> variants = model_variant.findElements(By
					.tagName("a"));
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

	public void selectContactPersonAt(String Xpath, int index) {
		try {
			waitForAjax();
			// Find the contact person package
			WebElement webElement = driver.findElement(By.xpath(Xpath));
			webElement = webElement.findElement(By.tagName("tbody"));
			// Get all rows
			List<WebElement> rows = webElement.findElements(By.tagName("tr"));
			// Get info of the first column
			WebElement first_column = rows.get(index).findElement(
					By.tagName("td"));
			String first_name = first_column.findElement(By.tagName("a"))
					.getText();
			System.out.println("Clicking : " + first_name);
			click(first_column.findElement(By.tagName("a")));
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException : " + Xpath);
		}

	}

	public ContactInfo getContactPersonInfoAt(String xpath, int index) {
		List<ContactInfo> list = new ArrayList<ContactInfo>();
		ContactInfo contactpackage = new ContactInfo();
		try {
			// Get the contact row
			WebElement element = driver.findElement(By.xpath(xpath));
			WebElement body = element.findElement(By.tagName("tbody"));
			List<WebElement> eachpackage_row = body.findElements(By
					.tagName("tr"));
			// Get all columns of each row
			List<WebElement> eachpackage_column = eachpackage_row.get(index)
					.findElements(By.tagName("td"));
			// Put info of each column into array
			contactpackage.firstnamelink = eachpackage_column.get(0)
					.findElement(By.tagName("a"));
			contactpackage.lastnamelink = eachpackage_column.get(1)
					.findElement(By.tagName("a"));
			contactpackage.title = eachpackage_column.get(2).getText();
			contactpackage.phone = eachpackage_column.get(3).getText();
			contactpackage.email = eachpackage_column.get(4).getText();
			list.add(contactpackage);
		} catch (NoSuchElementException e) {
		}
		return contactpackage;
	}

	public ArrayList<String> getAudioRouteName(String Xpath) {
		waitForAjax();
		ArrayList<String> arrayList = new ArrayList<String>();
		try {
			// Find the AudioRoute name container
			WebElement element = driver.findElement(By.xpath(Xpath));
			// Get all spans
			List<WebElement> each_span = element.findElements(By
					.tagName("span"));
			// Get text from each span
			for (WebElement item : each_span) {
				String name = item.getText();
				arrayList.add(name);
			}
		} catch (NoSuchElementException e) {
		}
		return arrayList;
	}

	public void deleteACompanyByName(String companyName) {
		// Select a company
		selectACompanyByName(companyName);
		// Delete
		doDelete(CompanyInfo.DELETE);
	}

	public Boolean isAllCheckboxDisabled(String Xpath) {
		waitForAjax();
		try {
			// Get the contact row
			WebElement element = driver.findElement(By.xpath(Xpath));
			WebElement body = element.findElement(By.tagName("tbody"));
			// Get all row
			List<WebElement> all_row = body.findElements(By.tagName("tr"));
			for (WebElement row : all_row) {
				// Get all column
				List<WebElement> all_column = row
						.findElements(By.tagName("td"));
				// Get column size
				int columnNum = all_column.size();
				// Get checkbox status
				String checkboxStatus = getAtribute(
						all_column.get(columnNum - 1).findElement(
								By.tagName("input")), "disabled");
				if (!checkboxStatus.contains("true")) {
					return false;
				}
			}
		} catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException :" + Xpath);
			return false;
		}
		return true;
	}

	public void uncheckAllNotification(String Xpath) {
		waitForAjax();
		try {
			// Get the contact row
			WebElement element = driver.findElement(By.xpath(Xpath)).findElement(By.tagName("tbody"));
			// Get all row
			List<WebElement> all_row = element.findElements(By.tagName("tr"));
			for (WebElement row : all_row) {
				// Get all column
				List<WebElement> all_column = row
						.findElements(By.tagName("td"));
				// Get column size
				int columnNum = all_column.size();
				// Get checkbox status
				WebElement checkbox = all_column.get(columnNum - 1)
						.findElement(By.tagName("input"));
				Boolean isSelected = checkbox.isSelected();
				if (isSelected) {
					result.addLog("Uncheck notification: " + checkbox.getAttribute("id"));
					checkbox.click();
					waitForAjax();
				}
			}
		} catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException :" + Xpath);
		}

	}

	public void checkSitePrivilegesByText(String Xpath, String SiteName) {
		waitForAjax();
		try {
			// Get the contact row
			WebElement element = driver.findElement(By.xpath(Xpath));
			// Get all row
			List<WebElement> all_row = element.findElements(By.tagName("tr"));
			for (WebElement row : all_row) {
				// Get all column
				List<WebElement> all_column = row
						.findElements(By.tagName("td"));
				// Get checkbox status
				WebElement checkbox = all_column.get(0).findElement(
						By.tagName("input"));
				Boolean isChecked = checkbox.isSelected();
				String label = all_column.get(0).getText();// getAttribute("value");
				if (label.equals(SiteName) && !isChecked) {
					checkbox.click();
					break;
				}
			}
		} catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException :" + Xpath);
		}
	}

	public void uncheckSitePrivilegesByText(String Xpath, String SiteName) {
		waitForAjax();
		try {
			// Get the contact row
			WebElement element = driver.findElement(By.xpath(Xpath));
			// Get all row
			List<WebElement> all_row = element.findElements(By.tagName("tr"));
			for (WebElement row : all_row) {
				// Get all column
				List<WebElement> all_column = row
						.findElements(By.tagName("td"));
				String label = row.findElement(By.tagName("label")).getText();
				// Get checkbox status
				WebElement checkbox = all_column.get(0).findElement(
						By.tagName("input"));
				Boolean isChecked = checkbox.isSelected();
				if (label.equals(SiteName) && isChecked) {
					checkbox.click();
				}
			}
		} catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException :" + Xpath);
		}
	}

	public Boolean isNotificationCheckboxDisable(String Xpath, String SiteName) {
		waitForAjax();
		try {
			// Get the contact row
			WebElement element = driver.findElement(By.xpath(Xpath)).findElement(By.tagName("tbody"));
			// Get all row
			List<WebElement> all_row = element.findElements(By.tagName("tr"));
			for (WebElement row : all_row) {
				// Get all column
				List<WebElement> all_column = row
						.findElements(By.tagName("td"));
				String label = row.findElement(By.tagName("label")).getText();
				int columnNum = all_column.size();
				// Get checkbox status

				WebElement checkbox = all_column.get(columnNum - 1)
						.findElement(By.tagName("input"));
				String disabled = checkbox.getAttribute("disabled");
				if (label.equals(SiteName)) {
					if (!disabled.isEmpty() && disabled.contains("true")) {
						return true;
					} else {
						return false;
					}
				}
			}
		} catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException :" + Xpath);
		}
		return false;
	}

	public String getTuningProcessStatus() {
		waitForAjax();
		try {
			// Locate to Xpath
			WebElement element = driver.findElement(By
					.xpath(AccessoryInfo.TUNING_APPROVAL_PROCESS));
			// Get status of tunning process
			String status = element.findElement(By.tagName("span")).getText();
			return status;
		} catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException" + e);
		}
		return "";
	}

	public void closeCurrentWindow() {
		waitForAjax();
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_F4);
			robot.delay(200);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_F4);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void selectInputChannel(String xpath, String channel) {

		try {

			// Do action if the channel != ""
			if (channel != "" && channel != null) {
				WebElement container = driver.findElement(By.xpath(xpath));
				// Get all columns
				List<WebElement> all_columns = container.findElements(By
						.tagName("td"));
				// Get checkbox columns
				WebElement checkbox = all_columns.get(0).findElement(
						By.tagName("label"));
				// Get dropbox columns
				WebElement dropbox = all_columns.get(1).findElement(
						By.tagName("div"));

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

	public void setGlobalPromotions(String Xpath, String status) {
		waitForAjax();
		System.out.println(Xpath);
		WebElement global_container = driver.findElement(By.xpath(Xpath));
		String current_status = global_container.getAttribute("class");
		if (current_status.contains(status)) {
			System.out.println("global promotion is " + status);
			return;
		}
		driver.findElement(By.xpath(DeviceEdit.GLOBAL_PROMOTIONS_SWITCH))
				.click();
	}

	public Boolean checkAnAccessoryExistByName(String name) {
		try {
			int size = getPageSize();
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if (columns.get(1).getText().equals(name)) {
						result.addLog("Product: " + name + " exists");
						return true;
					}
				}
				clickText("Next");
			}
			result.addLog("Product: " + name + " not exist");

		} catch (NoSuchElementException e) {
			result.addLog("No Element is present");
		}
		return false;
	}

	public void fillAddUser(Hashtable<String, String> hashXpath,
			Hashtable<String, String> data) {
		waitForAjax();
		editData(hashXpath.get("firstName"), data.get("firstName"));
		editData(hashXpath.get("lastName"), data.get("lastName"));
		selectOptionByName(hashXpath.get("company"), data.get("company"));
		editData(hashXpath.get("title"), data.get("title"));
		editData(hashXpath.get("email"), data.get("email"));
		editData(hashXpath.get("code"), data.get("code"));
		editData(hashXpath.get("phone"), data.get("phone"));
	}

	public ArrayList<InputSpecifications> getInputSpecifications(String xpath) {
		try {
			System.out.println("getInputSpecifications by xpath : " + xpath);
			ArrayList<InputSpecifications> list = new ArrayList<InputSpecifications>();
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			if (rows.size() < 2) {
				return null;
			}
			for (int i = 1; i < rows.size(); i++) {
				List<WebElement> columns = rows.get(i).findElements(
						By.tagName("td"));
				InputSpecifications inputSpecifications = new InputSpecifications();
				inputSpecifications.connectiontype = columns.get(0);
				inputSpecifications.supportedinputchannels = columns.get(1);
				list.add(inputSpecifications);
			}
			return list;
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public Boolean checkAdded(String xpath, List<String> data) {
		waitForAjax();
		boolean check = false;
		try {
			// Get the contact row
			WebElement element = driver.findElement(By.xpath(xpath));
			// Get all row
			List<WebElement> all_row = element.findElements(By.tagName("tr"));
			for (WebElement row : all_row) {
				// Get all column

				List<WebElement> all_column = row
						.findElements(By.tagName("td"));
				WebElement afName = all_column.get(0).findElement(
						By.tagName("a"));
				WebElement alName = all_column.get(1).findElement(
						By.tagName("a"));
				WebElement sTitle = all_column.get(2).findElement(
						By.tagName("span"));
				WebElement sPhone = all_column.get(3).findElement(
						By.tagName("span"));
				WebElement smail = all_column.get(4).findElement(
						By.tagName("span"));
				String fName = afName.getAttribute("name");
				String lName = alName.getAttribute("name");
				String title = sTitle.getText();
				String phone = sPhone.getText();
				String mail = smail.getText();
				if (fName.equals(data.get(0)) && lName.equals(data.get(1))
						&& title.equals(data.get(2))
						&& phone.equals(data.get(3))
						&& mail.equals(data.get(4))) {
					return true;
				} else {
					check = false;
				}

			}
		} catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException :" + xpath);
		}
		return check;

	}

	public Result checkUserAdded(String xpath, List<String> data, String index) {
		try {
			waitForAjax();
			int sizePage = getSizePage(index);
			// check variant page
			for (int i = 0; i < sizePage; i++) {
				if (checkAdded(xpath, data)) {
					result.addLog("On page exist Variant");
					return result;
				} else {
					result.addLog("Next Page");
					driver.findElement(By.linkText("Next")).click();
					// wait for load
					waitForAjax();
				}
			}
		} catch (NoSuchElementException e) {
			result.setResult("Fail");
			result.addLog("NoSuchElementException");
		}
		return result;
	}

	public Boolean isNotificationCheckboxEnable(String xpath, String Privilege) {
		try {
			// Get table
			WebElement table = driver.findElement(By.xpath(xpath)).findElement(
					By.tagName("tbody"));
			// Get rows
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			// Get columns
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if (columns.get(0).getText().contains(Privilege)) {
					WebElement checkbox = columns.get(columns.size() - 1).findElement(
							By.tagName("input"));
					if (checkbox.getAttribute("disabled") == null) {
						result.addLog("Notification checkbox of privilege: "
								+ Privilege + " is enabled");
						return true;
					} else {
						result.addLog("Notification checkbox of privilege: "
								+ Privilege + " is not enabled");
						return false;
					}
				}
			}
			result.addLog("Privilege not found");
			return false;

		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElement " + xpath);
			return false;
		}
	}

	public Boolean enableAllnotification() {
		try {
			// get table
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			// get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				// check privilege
				String attr = columns.get(2).getAttribute("innerHTML");
				System.out.println(attr);
				if (attr.contains("disabled")) {
					System.out.println("Enable : " + columns.get(2).getText());
					columns.get(2).click();
					waitForAjax();
				}
			}
			clickText("Save");
		} catch (NoSuchElementException e) {
			System.err.println("No element exception");
			return false;
		}
		return true;
	}

	public Result partnerSelectUserByTuningStatusNotPublished(
			String tuningStatus) {
		Boolean flag = true;
		String isPublished = "Published";
		int limit = getPageSize();
		try {
			while (flag) {
				if (driver.getPageSource().contains(tuningStatus)) {
					flag = false;
					WebElement tbody = driver.findElement(By.tagName("tbody"));
					// step 2 : find all row
					List<WebElement> rows = tbody
							.findElements(By.tagName("tr"));
					// step 3 : for all row get element contain email and click
					for (WebElement row : rows) {
						// get all colums
						List<WebElement> colums = row.findElements(By
								.tagName("td"));
						if (colums.size() == 8) {
							if (colums.get(5).getText().contains(tuningStatus)
									&& !colums.get(3).getText()
											.contains(isPublished)) {
								// found element
								// click element
								WebElement link = colums.get(1).findElement(
										By.tagName("a"));
								link.click();
								waitForAjax();
								return result;
							}
						}

					}
				}
				limit--;
				if (limit < 0) {
					flag = false;
				}
				driver.findElement(By.linkText("Next")).click();
				waitForAjax();
			}
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : " + tuningStatus);
			result.setResult("Fail");
		}
		return result;
	}

	public Result selectUserByTuningStatusHaveVariant(String tuningStatus) {
		Boolean flag = true;
		String isPublished = "Published";
		int limit = getPageSize();
		try {
			while (flag) {
				if (driver.getPageSource().contains(tuningStatus)) {
					flag = false;
					// find and click user by email
					// step 1 : find element tbody
					WebElement tbody = driver.findElement(By.tagName("tbody"));
					// step 2 : find all row
					List<WebElement> rows = tbody
							.findElements(By.tagName("tr"));
					// step 3 : for all row get element contain email and click
					for (WebElement row : rows) {
						// get all colums
						List<WebElement> colums = row.findElements(By
								.tagName("td"));
						if (colums.size() == 8) {
							if (colums.get(5).getText().contains(tuningStatus)
									&& !colums.get(3).getText()
											.contains(isPublished)
									&& Integer
											.parseInt(colums.get(2).getText()) > 0) {
								// found element
								// click element
								WebElement link = colums.get(1).findElement(
										By.tagName("a"));
								link.click();
								waitForAjax();
								return result;
							}
						}

					}
				}
				limit--;
				if (limit < 0) {
					flag = false;
				}
				driver.findElement(By.linkText("Next")).click();
				waitForAjax();
			}
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : " + tuningStatus);
			result.setResult("Fail");
		}
		return result;
	}

	public Result selectUserByTuningStatus(String tuningStatus) {
		Boolean flag = true;
		int limit = getPageSize();
		try {
			while (flag) {
				if (driver.getPageSource().contains(tuningStatus)) {
					flag = false;
					// find and click user by email
					// step 1 : find element tbody
					WebElement tbody = driver.findElement(By.tagName("tbody"));
					// step 2 : find all row
					List<WebElement> rows = tbody
							.findElements(By.tagName("tr"));
					// step 3 : for all row get element contain email and click
					for (WebElement row : rows) {
						// get all colums
						List<WebElement> colums = row.findElements(By
								.tagName("td"));
						if (colums.size() == 8) {
							if (colums.get(5).getText().contains(tuningStatus)) {
								// found element
								// click element
								WebElement link = colums.get(1).findElement(
										By.tagName("a"));
								link.click();
								waitForAjax();
								return result;
							}
						}

					}
				}
				limit--;
				if (limit < 0) {
					flag = false;
				}
				driver.findElement(By.linkText("Next")).click();
				waitForAjax();
			}
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : " + tuningStatus);
			result.setResult("Fail");
		}
		return result;
	}

	public String test(ArrayList<String> elements) {
		result.addLog("Check all element by ArrayList");
		waitForAjax();
		for (String element : elements) {
			Boolean isPresent = driver.findElements(By.xpath(element)).size() > 0;
			if (!isPresent) {
				result.addLog("Element " + element + " doesn't existed!");
				result.setResult("Fail");
			} else {
				result.addLog("Element \""
						+ driver.findElement(By.xpath(element)).getText()
						+ "\" displayed!");
				return driver.findElement(By.xpath(element)).getText();
			}
		}
		return null;
	}

	public PartnerContact getPartnerContact(String xpath) {
		PartnerContact contact = new PartnerContact();
		String text = getTextByXpath(xpath);
		String name = text.substring(0, text.indexOf("Email"));
		String email = text.substring(text.indexOf("Email"));
		Properties pro = new Properties();
		try {
			pro.load(new StringReader(email));
		} catch (IOException e) {
			e.printStackTrace();
		}
		contact.name = name;
		contact.email = pro.getProperty("Email");
		contact.phone = pro.getProperty("Phone");
		return contact;
	}

	public boolean clickFirstVariant() {
		String modelVariantId = "model-variants";
		try {
			WebElement element = driver.findElement(By
					.className(modelVariantId));
			List<WebElement> modelVariants = element.findElements(By
					.tagName("a"));
			modelVariants.get(0).click();
			return true;
		} catch (NoSuchElementException e) {
			System.out.println(e.getCause());
		}
		return false;
	}

	public Result selectUserByTuningMarketApproved(boolean imageChoosed) {
		Boolean flag = true;
		String isApproved = "Approved";
		int limit = getPageSize();
		try {
			while (flag) {
				if (driver.getPageSource().contains(isApproved)) {
					flag = false;

					WebElement tbody = driver.findElement(By.tagName("tbody"));
					// step 2 : find all row
					List<WebElement> rows = tbody
							.findElements(By.tagName("tr"));
					// step 3 : for all row get element contain email and click
					for (WebElement row : rows) {
						// get all colums
						List<WebElement> colums = row.findElements(By
								.tagName("td"));
						if (colums.size() == 8) {
							if (colums.get(5).getText().contains(isApproved)
									&& colums.get(7).getText()
											.contains(isApproved)) {
								if (imageChoosed) {
									colums.get(0)
											.findElement(By.tagName("img"))
											.getAttribute("src");
								} else {
									colums.get(0)
											.findElement(By.tagName("img"))
											.getAttribute("src");
								}
								WebElement link = colums.get(1).findElement(
										By.tagName("a"));
								link.click();
								waitForAjax();
								return result;
							}
						}

					}
				}
				limit--;
				if (limit < 0) {
					flag = false;
				}
				driver.findElement(By.linkText("Next")).click();
				waitForAjax();
			}
		} catch (NoSuchElementException e) {
			System.err.println("No element exception");
			result.setResult("Fail");
		}
		return result;
	}

	public Result partnerSelectUserByTuningStatusAndName(String tuningStatus,
			String name) {
		Boolean flag = true;
		int limit = getPageSize();
		try {
			while (flag) {
				if (driver.getPageSource().contains(tuningStatus)) {
					flag = false;
					WebElement tbody = driver.findElement(By.tagName("tbody"));
					// step 2 : find all row
					List<WebElement> rows = tbody
							.findElements(By.tagName("tr"));
					// step 3 : for all row get element contain email and click
					for (WebElement row : rows) {
						// get all colums
						List<WebElement> colums = row.findElements(By
								.tagName("td"));
						if (colums.size() == 8) {
							if (colums.get(5).getText().contains(tuningStatus)
									&& colums.get(1).getText().contains(name)) {
								// found element
								// click element
								WebElement link = colums.get(1).findElement(
										By.tagName("a"));
								link.click();
								waitForAjax();
								return result;
							}
						}

					}
				}
				limit--;
				if (limit < 0) {
					flag = false;
				}
				driver.findElement(By.linkText("Next")).click();
				waitForAjax();
			}
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : " + tuningStatus);
			result.setResult("Fail");
		}
		return result;
	}

	public Result partnerSelectUserByName(String name) {
		Boolean flag = true;
		int limit = getPageSize();
		try {
			while (flag) {
				if (driver.getPageSource().contains(name)) {
					flag = false;
					WebElement tbody = driver.findElement(By.tagName("tbody"));
					// step 2 : find all row
					List<WebElement> rows = tbody
							.findElements(By.tagName("tr"));
					// step 3 : for all row get element contain email and click
					for (WebElement row : rows) {
						// get all colums
						List<WebElement> colums = row.findElements(By
								.tagName("td"));
						if (colums.size() == 8) {
							if (colums.get(1).getText().contains(name)) {
								// found element
								// click element
								WebElement link = colums.get(1).findElement(
										By.tagName("a"));
								link.click();
								waitForAjax();
								return result;
							}
						}

					}
				}
				limit--;
				if (limit < 0) {
					flag = false;
				}
				driver.findElement(By.linkText("Next")).click();
				waitForAjax();
			}
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : " + name);
			result.setResult("Fail");
		}
		return result;
	}

	public Boolean fillAddAccessoriesUploadTuning(
			Hashtable<String, String> hashXpath, Hashtable<String, String> data) {
		try {
			waitForAjax();
			selectOptionByName(hashXpath.get("brand"), data.get("brand"));
			selectInputChannel(hashXpath.get("wired"), data.get("wired"));
			selectInputChannel(hashXpath.get("bluetooth"),
					data.get("bluetooth"));
			editData(hashXpath.get("name"), data.get("name"));
			editData(hashXpath.get("number"), data.get("number"));
			editData(hashXpath.get("upc"), data.get("upc"));
			selectOptionByName(hashXpath.get("type"), data.get("type"));
			editData(hashXpath.get("description"), data.get("description"));
			uploadFile(hashXpath.get("add tunning"), data.get("add tunning"));
			uploadFile(hashXpath.get("add marketing"),
					data.get("add marketing"));
			return true;
		} catch (NoSuchElementException e) {
			result.addLog(e.toString());
		}
		return false;
	}

	public Boolean selectAnaccessorybyTuningStatus(String tuningstatus) {
		try {
			System.out.println("Select accessory : " + tuningstatus);
			int size = getPageSize();
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if (columns.get(5).getText().equals(tuningstatus)
							&& !columns.get(7).getText()
									.equals("DTS Marketing Request Reivew")
							&& !columns.get(3).getText().equals("Published")) {
						columns.get(1).findElement(By.tagName("a")).click();
						waitForAjax();
						return true;
					}
				}
				clickText("Next");
			}
		} catch (NoSuchElementException e) {
			return false;
		}
		return false;
	}

	public Boolean selectAnaccessorybyTuningStatusApproved(String tuningstatus) {
		try {
			System.out.println("Select accessory : " + tuningstatus);
			int size = getPageSize();
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if (columns.get(5).getText().equals(tuningstatus)) {
						columns.get(1).findElement(By.tagName("a")).click();
						waitForAjax();
						return true;
					}
				}
				clickText("Next");
			}
		} catch (NoSuchElementException e) {
			return false;
		}
		return false;
	}

	public Boolean fillAddNewVariant(Hashtable<String, String> hashXpath,
			Hashtable<String, String> data) {
		try {
			waitForAjax();
			editData(hashXpath.get("upc"), data.get("upc"));
			editData(hashXpath.get("name"), data.get("name"));
			return true;
		} catch (NoSuchElementException e) {
			result.addLog(e.toString());
		}
		return false;
	}

	public boolean checkTuningActionsDisplayed() {
		boolean chechTuning = false;
		WebElement e = driver.findElement(By.id("tuning-action-id"));
		List<WebElement> e1 = e.findElements(By.tagName("a"));
		for (int i = 0; i < e1.size(); i++) {
			String tmp = e1.get(i).getText();
			if (tmp.equalsIgnoreCase("")) {
				chechTuning = true;
			} else {
				return false;
			}

		}
		return chechTuning;
	}

	public void createNewAccessory() {
		// Navigate to “Accessories” page
		click(PartnerHomePage.LINK_PARTNER_ACCESSORIES);
		// VP. Verify that the “Add Accessory” link is displayed inside
		// “Actions” module and the accessories table is also displayed.
		existsElement(Xpath.createNewAccessory);
		// Click "Add Accessory" link
		click(Xpath.createNewAccessory);
		// Fill valid value into all required fields.
		// init data
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("company", "TestCorp");
		data.put("brand", "test corp");
		data.put("id", RandomStringUtils.randomNumeric(9));
		String displayName = "Test" + RandomStringUtils.randomNumeric(4);
		data.put("name", displayName);
		data.put("number", RandomStringUtils.randomNumeric(5));
		data.put("upc", RandomStringUtils.randomNumeric(5));
		data.put("type", "In-Ear");
		data.put("wired", "1 (Mono)");
		data.put("bluetooth", "");
		data.put("date", "");
		data.put("aliases", "Test ALIASES");
		data.put("description", "Test DESCRIPTION");
		data.put("add tunning", "");
		data.put("img", "");
		data.put("add marketing", "");
		// Add
		fillAddAccessoriesDTS(AddAccessory.getHash(), data);
		// Click “Save” link
		click(AddAccessory.SAVE);

	}

	public WebElement getElementByTag(String xpath, String tag, int index) {
		List<WebElement> elements;
		try {
			elements = driver.findElement(By.xpath(xpath)).findElements(
					By.tagName("a"));
		} catch (NoSuchElementException e) {
			System.out.println(e.getCause());
			return null;
		}
		if (elements.size() >= index + 1) {
			return elements.get(index);
		} else {
			return null;
		}
	}

	public void createAccessoryPublish(Hashtable<String,String> hashXpath, Hashtable<String,String> data) { // Should login
		addAccessoriesPartner(hashXpath, data);
		// Publish accessory
		click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		click(AccessoryInfo.APPROVE_TUNING);
		click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		click(AccessoryInfo.APPROVE_MARKETING);
		click(AccessoryInfo.PUBLISH);

	}

	public void createNewVersionAccessory(String AccessoryName,
			String nameVersion) { // Should login with DTS user
		/*
		 * Init data
		 */
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("company", "TestCorp");
		data.put("name", AccessoryName);
		data.put("number", RandomStringUtils.randomNumeric(5));
		data.put("upc", RandomStringUtils.randomNumeric(5));
		data.put("type", "In-Ear");
		data.put("wired", "2 (Stereo)");
		data.put("description", "Test DESCRIPTION");
		data.put("add tunning", Constant.tuning_hpxtt);
		data.put("img1", AddAccessory.IMG_NAME[0]);
		data.put("img2", AddAccessory.IMG_NAME[1]);
		data.put("img3", AddAccessory.IMG_NAME[2]);
		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
		/*
		 * Create accessory
		 */
		addAccessoriesPartner(AddAccessory.getHash(), data);
		// Publish accessory
		click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
		click(AccessoryInfo.APPROVE_TUNING);
		click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
		click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
		click(AccessoryInfo.APPROVE_MARKETING);
		click(AccessoryInfo.PUBLISH);
		// click Create New Version
		click(AccessoryInfo.EDIT_MODE);
		switchWindowClickOption("Ok");
		// Change name
		type(AddAccessory.NAME, nameVersion);
		// SAVE
		click(AddAccessory.SAVE);

	}

	public Boolean selectPartnerUserByEmail(String email) {
		Boolean flag = true;
		int limit = getPageSize();
		try {
			while (flag) {
				// step 1 : find element tbody
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				if (tbody.getText().contains(email)) {
					flag = false;
					// step 2 : find all row
					List<WebElement> rows = tbody
							.findElements(By.tagName("tr"));
					// step 3 : for all row get element contain email and click
					for (WebElement row : rows) {
						// get all colums
						List<WebElement> colums = row.findElements(By
								.tagName("td"));
						if (colums.size() == 5) {
							if (colums.get(4).getText().equals(email)) {
								result.addLog("found email");
								// found element
								// click element
								WebElement link = colums.get(0).findElement(
										By.tagName("a"));
								result.addLog("Select user has email: " + email);
								link.click();
								waitForAjax();
								return true;
							}
						}

					}
				}
				limit--;
				if (limit < 0) {
					flag = false;
				}
				driver.findElement(By.linkText("Next")).click();
				waitForAjax();
			}
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : " + email);
			result.setResult("Fail");
		}
		return false;
	}

	public boolean checkMaketingActionsDisplayed() {
		boolean chechMaketing = false;
		WebElement e = driver.findElement(By.id("marketing-action-id"));
		List<WebElement> e1 = e.findElements(By.tagName("a"));
		for (int i = 0; i < e1.size(); i++) {
			String tmp = e1.get(i).getText();
			if (tmp.equalsIgnoreCase("")) {
				chechMaketing = true;
			} else {
				return false;
			}
		}
		return chechMaketing;
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

	public Boolean checkUserExistByEmail(String email) {
		Boolean flag = true;
		int limit = getPageSize();
		try {
			while (flag) {
				// step 1 : find element tbody
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				if (tbody.getText().contains(email)) {
					flag = false;
					// find and click user by email
					// step 2 : find all row
					List<WebElement> rows = tbody.findElements(By.tagName("tr"));
					// step 3 : for all row get element contain email and click
					for (WebElement row : rows) {
						// get all colums
						List<WebElement> columns = row.findElements(By.tagName("td"));
						if (columns.get(columns.size() - 2).getText().equals(email)) {
							// found element
							result.addLog("User has email: " + email + " exists");
							return true;
						}
					}
				}
				limit--;
				if (limit < 0) {
					flag = false;
				}
				driver.findElement(By.linkText("Next")).click();
				waitForAjax();
			}
			result.addLog("User has email: " + email + " not exist");
			return false;
		} catch (NoSuchElementException e) {
			result.addLog("No element exception");
			return null;
		}
		
	}

	public void createVariantPublish(Hashtable<String,String> hashXpath, Hashtable<String,String> data) { // Should login with
		addVariant(hashXpath, data);
		// Publish variant
		click(VariantInfo.PARTNER_ACTIONS_STEP_1);
		click(VariantInfo.APPROVE_TUNING);
		click(VariantInfo.PARTNER_ACTIONS_STEP_2);
		click(VariantInfo.REQUEST_MARKETING_REVIEW);
		click(VariantInfo.APPROVE_MARKETING);
		click(VariantInfo.PUBLISH);
	}

	public Boolean disablePrivileges(String xpath, ArrayList<String> list) {
		waitForAjax();
		try {
			// get table
			WebElement tbody = driver.findElement(By.xpath(xpath)).findElement(By.tagName("tbody"));
			// get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (String privilege : list) {
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if (columns.get(0).getText().contains(privilege)) {
						// check privilege
						String attr;
						if(columns.size() == 3){
							attr = columns.get(2).getAttribute("innerHTML");
						}else{
							attr = columns.get(1).getAttribute("innerHTML");
						}
						if (!attr.contains("disabled")) {
							result.addLog("Disable : " + privilege);
							columns.get(0).findElement(By.tagName("label")).click();
							waitForAjax();
							break;
						} else {
							result.addLog("Privilege " + privilege + " disabled, break");
							break;
						}
					}
				}
			}
			clickText("Save");
			for (String privilege : list) {
				if (driver.getPageSource().contains(privilege)) {
					System.err.println("Can't disable " + privilege);
					Assert.assertFalse(true);
				}
			}
			return true;
		} catch (NoSuchElementException e) {
			System.err.println("No element exception");
			return false;
		}
	}

	public Boolean disableAllPrivilege(String xpath) {
		try {
			// get table
			WebElement tbody = driver.findElement(By.xpath(xpath)).findElement(By.tagName("tbody"));
			// get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				// check privilege
				WebElement privilege = columns.get(0).findElement(By.tagName("input"));
				if (privilege.isSelected()) {
					result.addLog("Disable : " + columns.get(0).getText());
					privilege.click();
					waitForAjax();
				}
			}
		} catch (NoSuchElementException e) {
			result.addLog("No element exception");
			return false;
		}
		return true;
	}
	
	public Boolean selectABrand(String brandList) {
		try {
			// Get the brand list
			WebElement brand_list = driver.findElement(By.xpath(brandList)); 
			// Get a brand
			WebElement aBrand = brand_list.findElement(By.tagName("div"));
			// Click on brand name
			result.addLog("click on a brand");
			aBrand.findElement(By.tagName("a")).click();
			waitForAjax();
					
		} catch (NoSuchElementException e) {
			System.err.println("No element exception");
			return false;
		}
		return true;
	}
	
	public Boolean isNotificationCheckboxSelected(String xpath, String Privilege) {
		try {
			// Get table
			WebElement table = driver.findElement(By.xpath(xpath)).findElement(
					By.tagName("tbody"));
			// Get rows
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			// Get columns
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if (columns.get(0).getText().contains(Privilege)) {
					WebElement checkbox = columns.get(2).findElement(
							By.tagName("input"));
					if (checkbox.isSelected()) {
						result.addLog("Notification checkbox of privilege: "
								+ Privilege + " is selected");
						return true;
					} else {
						result.addLog("Notification checkbox of privilege: "
								+ Privilege + " is not selected");
						return false;
					}
				}
			}
			result.addLog("Privilege not found");
			return false;

		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElement " + xpath);
			return false;
		}
	}
	
	public Boolean isAllNotificationCheckboxSelected(String xpath){
		ArrayList<String> privileges = getPrivileges(xpath);
		for(String privilege : privileges){
			if(!isNotificationCheckboxSelected(xpath, privilege)){
				return false;
			}
		}
		return true;
	}

	
	public int getLanguageContainerIndex(String xpath){
		try{
			// Get languages
			List<WebElement> languages = driver.findElement(By.xpath(xpath)).findElements(By.tagName("div"));
			// Get language index
			int index = languages.size();
			result.addLog("The language index is: " + index);
			return index;
		}catch(NoSuchElementException e){
			result.addLog("NoSuchElement " + xpath);
			return 0;
		}
	}
	
	public int getPromotionContainerIndex(String xpath){
		try{
			// Get languages
			List<WebElement> promotions = driver.findElement(By.xpath(xpath)).findElements(By.className("control-group"));
			// Get language index
			int index = promotions.size();
			result.addLog("The promotion index is: " + index);
			return index;
		}catch(NoSuchElementException e){
			result.addLog("NoSuchElement " + xpath);
			return 0;
		}
	}
	
	public Boolean isAllNotificationCheckboxDisable(String xpath) {
		try {
			// Get table
			WebElement table = driver.findElement(By.xpath(xpath)).findElement(
					By.tagName("tbody"));
			// Get rows
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			// Get columns
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				WebElement checkbox = columns.get(columns.size() - 1).findElement(
						By.tagName("input"));
				if (checkbox.getAttribute("disabled") != null) {
					result.addLog("Notification checkbox: " + columns.get(0).getText() + " is disabled");
				} else {
					result.addLog("Notification checkbox: " + columns.get(0).getText() + " is enabled");
					return false;
				}
			}
			return true;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElement " + xpath);
			return false;
		}
	}

	public Boolean isUserInfoEnable(ArrayList<String> list) {
		try {
			for (String UserInfo : list) {
				String disable = getAtribute(UserInfo, "disable");
				if (disable == null) {
					result.addLog("User info: " + UserInfo
							+ " is enable and editable");
				} else {
					result.addLog("User info: " + UserInfo
							+ " is disable and uneditable");
					return false;
				}
			}
			return true;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElement is present");
			return false;
		}
	}
	
	public Boolean isAllPrivilegeEnable(String table) {
		try {
			// Get table
			WebElement tbody = driver.findElement(By.xpath(table)).findElement(
					By.tagName("tbody"));
			// Get rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				// Get columns
				List<WebElement> columns = row.findElements(By.tagName("td"));
				// Get privilege
				WebElement privilege = columns.get(0);
				String disable = privilege.findElement(By.tagName("input"))
						.getAttribute("disabled");
				if (disable == null) {
					result.addLog("Privilege: " + privilege.getText()
							+ " is enable and editable");
				} else {
					result.addLog("Privilege: " + privilege.getText()
							+ " is disable and uneditable");
					return false;
				}
			}
			return true;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElement is present");
			return false;
		}
	}
	
	public Boolean isAllPrivilegeSelected(String table) {
		try {
			// Get table
			WebElement tbody = driver.findElement(By.xpath(table)).findElement(
					By.tagName("tbody"));
			// Get rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				// Get columns
				List<WebElement> columns = row.findElements(By.tagName("td"));
				// Get privilege
				WebElement privilege = columns.get(0);
				Boolean isSelected = privilege.findElement(By.tagName("input"))
						.isSelected();
				if (isSelected) {
					result.addLog("Privilege: " + privilege.getText()
							+ " is selected");
				} else {
					result.addLog("Privilege: " + privilege.getText()
							+ " is not selected");
					return false;
				}
			}
			return true;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElement is present");
			return false;
		}
	}
	
	public String getABrandName(String Xpath) {
		try {
			// Get brand list
			WebElement brandlist = driver.findElement(By.xpath(Xpath));
			// Get a brand tag
			WebElement brandName_tag = brandlist.findElement(By.tagName("div"))
					.findElement(By.tagName("a"));
			// Get brand name
			String brandName = brandName_tag.getText();
			result.addLog("Brand name: " + brandName);
			return brandName;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElement: " + Xpath);
		}
		return "";
	}

	public int getRowIndexByEmail(String xpath, String email) {
		try {
			// Get table
			WebElement tbody = driver.findElement(By.xpath(xpath)).findElement(By.tagName("tbody"));
			// Get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for(int i = 0; i < rows.size(); i++){
				List<WebElement> columns = rows.get(i).findElements(By.tagName("td"));
				if(columns.get(columns.size() - 2).getText().contains(email)){
					result.addLog("The row index is: " + i );
					return i;
				}
			}
			result.addLog("Not found row index for email: " + email);
			return 0;
			
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElement: " + xpath);
		}
		return 0;
	}
	
	public int getRowIndexByName(String xpath, String name) {
		try {
			// Get table
			WebElement tbody = driver.findElement(By.xpath(xpath)).findElement(By.tagName("tbody"));
			// Get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for(int i = 0; i < rows.size(); i++){
				List<WebElement> columns = rows.get(i).findElements(By.tagName("td"));
				if(columns.get(0).getText().equals(name)){
					result.addLog("The row index is: " + i );
					return i;
				}
			}
			result.addLog("Not found row index for name: " + name);
			return 0;
			
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElement: " + xpath);
		}
		return 0;
	}
	
	public ArrayList<String> getUserInfoByIndex(String xpath, int index) {
		try {
			
			WebElement table = driver.findElement(By.xpath(xpath));
			WebElement headers = table.findElement(By.tagName("thead")).findElement(By.tagName("tr"));
			List<WebElement> each_header = headers.findElements(By.tagName("th"));
			WebElement body = table.findElement(By.tagName("tbody"));
			// Get the contact row
			List<WebElement> rows = body.findElements(By.tagName("tr"));
			// Get all columns of each row
			List<WebElement> columns = rows.get(index).findElements(
					By.tagName("td"));
			ArrayList<String> user_info = new ArrayList<String>();
			// Put info of each column into array
			for (int i = 0; i < columns.size(); i++) {
				if(each_header.get(i).getText().contains("Title")){
					continue;
				}
				result.addLog("Add user info to array: " + columns.get(i).getText() );
				user_info.add(columns.get(i).getText());
				
			}
			return user_info;

		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElement: " + xpath);
		}
		return null;
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

	public Boolean checkMessageDisplay(String message) {
		String text = driver.getPageSource();
		if (text.contains(message)) {
			result.addLog("Message: " + message + " found");
			return true;
		} else {
			result.addLog("Message: " + message + " not found");
			return false;
		}
	}
	
	public Boolean checkMessageDisplay(ArrayList<String> messages) {
		String text = driver.getPageSource();
		for (String message : messages) {
			if (text.contains(message)) {
				result.addLog("Message: " + message + " found");
			} else {
				result.addLog("Message: " + message + " not found");
				return false;
			}
		}
		return true;
	}
	
	public Boolean downloadFile(String filename) {
		try {
			String fileExt = filename.substring(filename.lastIndexOf("."), filename.length());
			// Cut off file extension
			filename = filename.replaceAll(fileExt, "");
			// Find the file link
			WebElement file_link = driver.findElement(By
					.xpath("//a[contains(@href,'" + filename + "')]"));
			// Click on download link
			click(file_link);
			// Get file name generated when download to local
			AutoItX.winWait("[Class:MozillaDialogClass]", "", 10);
			String file_name_download = AutoItX.winGetTitle("[Class:MozillaDialogClass]").replace("Opening ", "");
			// Check if file exist and delete
			String file_path = System.getProperty("user.home") + "\\Downloads\\" + file_name_download;
			File file = new File(file_path);
			if(file.exists()){
				file.delete();
			}
			// Download file
			result.addLog("Start download file: " + file_name_download);
			handleFirefoxDownloadDialog("Save File");
			for (int i = 1; i <= 10; i++) {
				Thread.sleep(1000);
				if (FileUtil.FileExist(file_path)) {
					result.addLog("File: " + file_name_download + " is downloaded successfully");
					return true;
				}
			}
			result.addLog("File: " + file_name_download + "is not downloaded successfully");
			return false;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElement is present");
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void deleteAllProductWithPublishedStatus(String status){
		selectFilterByName(Xpath.accessoryFilterSelect, status);
		while(driver.findElement(By.xpath((AccessoryMain.ACCESSORY_TABLE))).isDisplayed()){
			selectAnAccessory();
			doDelete(AccessoryInfo.DELETE);
			selectFilterByName(Xpath.accessoryFilterSelect, status);
		}
		result.addLog("All product with status: " + status + " are deleted successfully");
	}
	
	public Boolean checkItemAmountDisplayOnTable(String xpath, int itemAmount){
		try {
			int total_item = getTotalItem(xpath);
			System.out.println("-----------------"
					+ "Total item----------------------"
					+ total_item);
			int item_per_page = getPerPage(xpath);
			System.out.println("-----------------"
					+ "Total item----------------------"
					+ item_per_page);
			if (total_item >= itemAmount && item_per_page == itemAmount) {
				result.addLog("Maximum item amount display per page is: "
						+ itemAmount);
				return true;
			} else if (total_item < itemAmount && item_per_page == total_item) {
				result.addLog("Maximum item amount display per page is: "
						+ itemAmount);
				return true;
			} else {
				result.addLog("Item amount on table does not display correctly");
				return false;
			}
		} catch (NoSuchElementException e) {
			result.addLog("No Element: " + xpath);
			return false;
		}
	}
	
	public Boolean selectARandomUser() {
		try {
			// Get table
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			// Get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			// Generate a random user on User table
			Random r = new Random();
		    int user_amount = rows.size() - 1;
		    int random_user = r.nextInt(user_amount);
		    // Get another random user if the selected random user is super user
		    if(rows.get(random_user).findElement(By.tagName("a")).getText().equals("fae")){
		    	random_user = r.nextInt(user_amount);
		    }
			// Select random user
		    result.addLog("Select user: " + random_user + " has name: " + rows.get(random_user).findElement(By.tagName("a")).getText());
			rows.get(random_user).findElement(By.tagName("a")).click();
			waitForAjax();
		} catch (NoSuchElementException e) {
			System.err.println("No element exception");
			return false;
		}
		return true;
	}
	
	
	
	public Boolean isElementEditable(String xpath){ // Use only for textbox or combobox
		try {
			String value = driver.findElement(By.xpath(xpath)).getAttribute("value");
			String disabled = driver.findElement(By.xpath(xpath)).getAttribute("disabled");
			if (value != null) {
				if(disabled == null){
					result.addLog("Element: " + xpath + " is editable");
					return true;
				}
				result.addLog("Element: " + xpath + " is not editable");
				return false;
			}
			result.addLog("Element: " + xpath + " is not editable");
			return false;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElement: " + xpath);
			return null;
		}
	}

	public Boolean checkAllUserStatus(String status) {
		String sql;
		try {
			List<WebElement> rows = driver.findElement(By.tagName("tbody"))
					.findElements(By.tagName("tr"));
			if (rows.get(0).getText().equals("No data available in table")) {
				result.addLog("No data for verifying");
				return false;
			}
			ArrayList<String> emailList = getDataByHeaderText(
					UsersList.USER_TABLE, "Email");
			for (String item : emailList) {
				System.out.println(item);
			}
			if (emailList.contains("fae@dts.com")) {
				emailList.remove("fae@dts.com");
			}
			if (status.equals("All Accounts")) {
				sql = "SELECT EMAIL FROM ACCOUNT";
			} else {
				sql = "SELECT EMAIL FROM ACCOUNT WHERE STATUS = '"
						+ status.toUpperCase() + "'";
			}
			String emailReturned = DbHandler.selectStatment(sql);
			System.out.println(emailReturned);

			if (DTSUtil.containsListText(emailReturned, emailList)) {

				result.addLog("All user status is correct");
				return true;
			}
			result.addLog("All user status is not correct");
			return false;
		} catch (NoSuchElementException e) {
			result.addLog("No such element present");
			return false;
		}
	}
	
	public Boolean isImageDisplayInOrder(String xpath){
		try{
		int size = 0;
		int pos = 0;
		// Get all image in image catalog
		List<WebElement> img_div = driver.findElement(By.xpath(xpath)).findElements(By.className("tuning-material-div"));
		for(int i=0; i < img_div.size(); i++){
			WebElement img = img_div.get(i).findElements(By.tagName("span")).get(0);
			// Get image text
			String img_text = img.getText();
			result.addLog(img_text);
			// Get image size
			int beginIndex = img_text.indexOf("(");
			int endIndex = img_text.indexOf("x");
			int img_size = Integer.parseInt(img_text.substring(beginIndex + 1, endIndex));
			if(img_size < size && img.getLocation().y < pos){
				result.addLog("Image does not display in order 250x250,500x500,1000x1000");
				return false;
			}else{
				result.addLog("The image no " + i + " has size: " + img_size);
				result.addLog("The postion y of " + i + " = " + img.getLocation().y);
				img_size = size;
				pos = img.getLocation().y;
			}
		}
		result.addLog("Image displays in order 250x250,500x500,1000x1000");
		return true;
		}catch(NoSuchElementException e){
			result.addLog("NoSuchElement: " + xpath);
			return false;
		}
	}

	public Boolean isElementDisplayHorizontal(String fisrtelement,
			String secondelement) {

		WebElement first = driver.findElement(By.xpath(fisrtelement));
		WebElement second = driver.findElement(By.xpath(secondelement));

		int posX1 = first.getLocation().x;
		int posX2 = second.getLocation().x;

		if (posX1 == posX2 || posX1 > posX2) {
			result.addLog("Second element does not display next to first element");
			result.addLog("1st X position = " + posX1);
			result.addLog("2nd X position = " + posX2);
			return false;
		} else {
			result.addLog("Second element displays next to first element");
			result.addLog("1st X position = " + posX1);
			result.addLog("2nd X position = " + posX2);
			return true;
		}

	}

	public Boolean isElementDisplayVertically(String fisrtelement,
			String secondelement) {

		WebElement first = driver.findElement(By.xpath(fisrtelement));
		WebElement second = driver.findElement(By.xpath(secondelement));

		int posY1 = first.getLocation().y;
		int posY2 = second.getLocation().y;

		if (posY1 == posY2 || posY1 > posY2) {
			result.addLog("Second element does not display below first element");
			result.addLog("1st Y position = " + posY1);
			result.addLog("2nd Y position = " + posY2);
			return false;
		} else {
			result.addLog("Second element displays below first element");
			result.addLog("1st X position = " + posY1);
			result.addLog("2nd X position = " + posY2);
			return true;
		}

	}
	
	public Boolean isImageDisplayLeftToRight(String xpath){
		try{
		int size = 0;
		// Get image table
		WebElement table = driver.findElement(By.xpath(xpath)).findElement(By.tagName("tbody"));
		WebElement row = table.findElements(By.tagName("tr")).get(0);
		// Get all image display
		List<WebElement> images = row.findElements(By.tagName("td"));
		for(int i=0; i < images.size(); i++){
			// Get image text
			String img_text = images.get(i).getText();
			result.addLog(img_text);
			// Get image size
			int endIndex = img_text.indexOf("x");
			int img_size = Integer.parseInt(img_text.substring(0, endIndex));
			if(img_size < size){
				result.addLog("Image does not display from left to right in order 250x250,500x500,1000x1000");
				return false;
			}else{
				result.addLog("The image no " + i + " has size: " + img_size);
				img_size = size;
			}
		}
		result.addLog("Image displays from left to right in order 250x250,500x500,1000x1000");
		return true;
		}catch(NoSuchElementException e){
			result.addLog("NoSuchElement: " + xpath);
			return false;
		}
	}
	
	public Boolean isPartnershipsEditable(String xpath) {
		// Get partnerships value
		try {
			Boolean flag = null;
			List<WebElement> values = driver.findElement(By.xpath(xpath))
					.findElements(By.tagName("li"));
			// Check if partnerships value is editable
			for (WebElement value : values) {
				if (value.getText().contains("Delete")) {
					result.addLog("Partnership: "
							+ value.findElement(By.tagName("span")).getText()
							+ " is editable");
					flag = true;
				} else {
					result.addLog("Partnership: "
							+ value.findElement(By.tagName("span")).getText()
							+ " is not editable");
					flag = false;
				}
			}
			return flag;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElement: " + xpath);
			return null;
		}
	}
	
	public Boolean verifyColumnNotContainValue(String xpath, String headerText,
			String pattern) {
		try {
			clickText("First");
			ArrayList<String> arrayList = getDataByHeaderText(xpath, headerText);
			if (arrayList.size() > 0) {
				for (String item : arrayList) {
					if (item.equals(pattern)) {
						result.addLog("Column value contains : " + pattern);
						return false;
					}
				}
				result.addLog("Column value not contain: " + pattern);
				return true;
			} else {
				result.addLog("No Data for verify");
				return false;
			}
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException at verifyValueColumn : "
					+ xpath);
		}
		return false;
	}
	
	public Boolean selectAnItemOnTable() {
		try {
			// get table
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			// get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				WebElement link = row.findElement(By.tagName("a"));
				link.click();
				waitForAjax();
				break;
			}
		} catch (NoSuchElementException e) {
			result.addLog("No element exception");
			return false;
		}
		return true;
	}
	
	public String selectAPromotion() {
		String promotionName = null;
		try {
			// get table
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			// get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				// Select promotion
				promotionName = columns.get(1).getText() + " "
						+ columns.get(2).getText();
				result.addLog("Select promotion: " + promotionName);
				columns.get(0).findElement(By.tagName("a")).click();
				waitForAjax();
				break;
			}
		} catch (NoSuchElementException e) {
			result.addLog("No element exception");
			return null;
		}
		return promotionName;
	}
	
	public Boolean addPromotion(Hashtable<String,String> hashXpath, Hashtable<String,String> data){
		try{
		// Enter saleforceID
		editData(hashXpath.get("salesforceid"), data.get("salesforceid"));
		// Select promotion type
		selectOptionByName(hashXpath.get("promotion type"), data.get("promotion type"));
		// Select brand and device
		if(!data.get("promotion type").equals("Global")){
			selectOptionByName(hashXpath.get("device brand"), data.get("device brand"));
			selectOptionByName(hashXpath.get("device name"), data.get("device name"));
		}
		// Enter product 
		editData(hashXpath.get("name"), data.get("name"));
		Thread.sleep(2000);
		AutoItX.send("{ENTER}", false);
		waitForAjax();
		// Click save
		if(data.containsKey("save")){
			click(hashXpath.get("save"));
			Thread.sleep(2000);
			waitForAjax();
		}
		return true;
		
		}catch(NoSuchElementException e){
			result.addLog("No element is present");
			return false;
		}catch(InterruptedException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public void dragDropFile(String fileName) {
		try {
			ScreenRegion sreen = new DesktopScreenRegion();
			Mouse mouse = new DesktopMouse();
			ScreenRegion dragDropArea = null;
			Target target = null;
			ScreenLocation location = null;
			File path = new File(System.getProperty("user.dir") + "\\asset");
			// Get file ext
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1,
					fileName.length());
			// Scroll up to top page
			AutoItX.mouseWheel("up", 50);
			// scroll to the element
			int i = 0;
			while(dragDropArea == null && i < 20){
				AutoItX.mouseWheel("down", 5);
				Thread.sleep(300);
				if (fileExt.equals("hpxtt") || fileExt.equals("zip")) {
					target = new ImageTarget(new File(path + "\\TuningDragDropArea.png"));
				} else if (fileExt.equals("jpg")) {
					target = new ImageTarget(new File(path + "\\ImageDragDropArea" + fileName));
				} else {
					target = new ImageTarget(new File(path + "\\MarketingDragDropArea.png"));
				}
				target.setMinScore(0.9);
				dragDropArea = sreen.find(target);
				i++;
			}
			if(fileExt.equals("jpg")){
				location = dragDropArea.getRelativeScreenLocation(400,40);
			}else{
				location = dragDropArea.getCenter();
			}
			// Open directory contain file
			Desktop.getDesktop().open(path);
			// Move window to the right corner
			int width = sreen.getWidth();
			AutoItX.winWait("asset");
			AutoItX.winActivate("asset");
			AutoItX.winMove("asset", "", width - 600, 0, 600, 600);
			Thread.sleep(2000);
			
			// Drag and Drop file to Marketing Container
			ScreenRegion dragDropFile = sreen.find(new ImageTarget(new File(path
					+ "\\" + fileName + ".png")));
			result.addLog("Drag and drop file: " + fileName
					+ " to Drag Drop Area");
			mouse.drag(dragDropFile.getCenter());
			mouse.drop(location);
			Thread.sleep(3000);
			waitForAjax();
			// Close window
			AutoItX.winClose("asset");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Boolean deleteAllUploadedImage(String xpath) {
		try {
			WebElement image = driver.findElement(By
					.xpath(xpath)).findElement(By.className("icon-trash-icon"));
			while(image.isDisplayed()){
				result.addLog("Delete image");
				image.click();
				waitForAjax();
				selectConfirmationDialogOption("Delete");
				image = driver.findElement(By
						.xpath(xpath)).findElement(By.className("icon-trash-icon"));
			}
		} catch (NoSuchElementException e) {
			result.addLog("Uploaded images are deleted successfully");
			return true;
		}
		return true;
	}
	
	public ArrayList<String> getInputSpecificationHeader(String xpath) {
		try {
			ArrayList<String> listText = new ArrayList<String>();
			// Get first row
			WebElement firstRow = driver.findElement(By.xpath(xpath))
					.findElements(By.tagName("tr")).get(0);
			// Get headers
			List<WebElement> headers = firstRow.findElements(By.tagName("th"));
			for (WebElement header : headers) {
				// Get header text
				String headerText = header.getText();
				result.addLog("Header text: " + headerText);
				listText.add(headerText);
			}
			return listText;
		} catch (NoSuchElementException e) {
			result.addLog("No element: " + xpath);
			return null;
		}
	}
	
	public Boolean selectACheckbox(String xpath) {
		try {
			WebElement checkbox = driver.findElement(By.xpath(xpath));
			if (!checkbox.isSelected()) {
				result.addLog("Select checkbox: " + xpath);
				checkbox.click();
				waitForAjax();
				return true;
			}
			result.addLog("Checkbox: " + xpath + " is already selected");
			return true;
		} catch (NoSuchElementException e) {
			result.addLog("No such element: " + xpath);
			return false;
		}
	}
	
	public Boolean uncheckACheckbox(String xpath) {
		try {
			WebElement checkbox = driver.findElement(By.xpath(xpath));
			if (checkbox.isSelected()) {
				result.addLog("Uncheck checkbox: " + xpath);
				checkbox.click();
				waitForAjax();
				return true;
			}
			result.addLog("Checkbox: " + xpath + " is already unchecked");
			return true;
		} catch (NoSuchElementException e) {
			result.addLog("No such element: " + xpath);
			return false;
		}
	}
	
	public Boolean deleteAllBrandExceptOne(String xpath, String brandName){
		try {
			// Get brand list
			WebElement brandList = driver.findElement(By.xpath(xpath));
			// Get all brand
			List<WebElement> brands = brandList.findElements(By.tagName("div"));
			ArrayList<String> list = new ArrayList<String>();
			for (WebElement brand : brands) {
				list.add(brand.getText());
			}
			for (String name : list) {
				if (name.equals(brandName)) {
					continue;
				}
				selectABrandByName(name);
				result.addLog("Delete brand: " + name);
				doDelete(BrandInfo.DELETE_LINK);
			}
		} catch (NoSuchElementException e) {
			result.addLog("No Element: " + xpath);
			return false;
		}
		return true;
	}
	
	public ArrayList<String> selectAnAppDevice() {
		try {
			// get table
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			// get selected App/Device info
			ArrayList<String> list = new ArrayList<String>();
			List<WebElement> columns = tbody.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td"));
			for (WebElement column : columns) {
				list.add(column.getText());
			}
			// Select App/Device
			result.addLog("Select App/Device: " + columns.get(2).getText());
			columns.get(2).findElement(By.tagName("a")).click();
			waitForAjax();
			Thread.sleep(2000);
			waitForAjax();
			return list;
		} catch (NoSuchElementException e) {
			result.addLog("No element exception");
			return null;
		} catch(InterruptedException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public Boolean selectCheckboxInDropdown(String xpath, String name){
		try {
			if(name == null | name == ""){
				return null;
			}
			// Get dropdown box
			WebElement groupBox = driver.findElement(By.xpath(xpath));
			// Click on dropdown
			groupBox.findElement(By.tagName("button")).click();
			waitForAjax();
			// Select checkbox
			List<WebElement> checkboxs = groupBox.findElement(By.tagName("ul"))
					.findElements(By.tagName("li"));
			for (WebElement checkbox : checkboxs) {
				if (checkbox.getText().contains(name)) {
					if (!checkbox.findElement(By.tagName("input")).isSelected()) {
						result.addLog("Select checkbox: " + name);
						checkbox.findElement(By.tagName("input")).click();
						waitForAjax();
						break;
					}
					result.addLog("Checkbox: " + name + " is already selected");
					break;
				}
			}
			// Close the dropdown
			groupBox.findElement(By.tagName("button")).click();
			waitForAjax();
			return true;
		} catch (NoSuchElementException e) {
			result.addLog("No element: " + xpath);
			return null;
		}
	}
	
	public Boolean selectOptionInDropdown(String xpath, String name){
		try {
			if(name == null | name == ""){
				return null;
			}
			// Get dropdown box
			WebElement groupBox = driver.findElement(By.xpath(xpath));
			// Click on dropdown
			groupBox.findElement(By.tagName("button")).click();
			waitForAjax();
			// Select checkbox
			List<WebElement> options = groupBox.findElement(By.tagName("ul"))
					.findElements(By.tagName("li"));
			for (WebElement option : options) {
				if (option.getText().contains(name)) {
						result.addLog("Select option: " + name);
						option.findElement(By.tagName("a")).click();
						waitForAjax();
						return true;
					}
			}
			result.addLog("Option: " + name + " not found");
			return false;
		} catch (NoSuchElementException e) {
			result.addLog("No element: " + xpath);
			return null;
		}
	}
	
	public Boolean checkAnAppDeviceExistByName(String name) {
		String xpath= DeviceList.TABLE;
		try {
			int size = getPageSize();
			for (int i = 0; i < size; i++) {
				// Get app/device table
				WebElement table = driver.findElement(By.xpath(xpath)).findElement(By.tagName("tbody"));
				// Get all rows
				List<WebElement> rows = table.findElements(By.tagName("tr"));
				// Check App/Device exist
				for (WebElement row : rows) {
					WebElement device = row.findElements(By.tagName("td")).get(
							2);
					if (device.getText().equals(name)) {
						result.addLog("App/Device: " + name + " exist");
						return true;
					}
				}
				clickText("Next");
			}
			result.addLog("App/Device: " + name + " not exist");
			return false;

		} catch (NoSuchElementException e) {
			result.addLog("No such element: " + xpath);
			return null;
		}
	}
	
	public Boolean waitForElementClickable(String xpath) {
		try {
			result.addLog("Wait for element: " + xpath + " clickable");
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			return true;

		} catch (NoSuchElementException e) {
			result.addLog("No such element: " + xpath);
			return false;
		}
	}
	
	public Boolean waitForElementDisappear(String xpath) {
		try {
			result.addLog("Wait for element: " + xpath + " disappear");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
			return true;

		} catch (NoSuchElementException e) {
			result.addLog("No such element: " + xpath);
			return false;
		}
	}
	
	public Boolean checkConfirmationDialog(String objectName, String option) {
		try {
			Thread.sleep(3000);
			option = option.toLowerCase();
			String message = "Are you sure you want to " + option + " "
					+ objectName;
			Boolean check_message = getTextByXpath(
					driver.findElement(By.className("modal-body"))).contains(
					message);
			option = WordUtils.capitalize(option);
			Boolean button1 = isElementPresent(Xpath.BTN_CONFIRMATION_CANCEL);
			Boolean button2 = getTextByXpath(Xpath.BTN_CONFIRMATION_DANGER)
					.equals(option);
			if (check_message == button1 == button2 == true) {
				result.addLog("Confirmation dialog displays with all information correctly");
				return true;
			}
			result.addLog("Confirmation dialog does not display with all information correctly");
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean downloadPublishedAccessoryProfile() {
		try {
			// Get id of published accessory
			String id = getTextByXpath(driver.findElement(By.xpath("//*[@id='dts-tracking-id' or @id='dts-id']")));
			String file_path = System.getProperty("user.home") + "\\Downloads\\profile_" + id + ".dtscs";
			// Delete file if exist
			if (FileUtil.FileExist(file_path)) {
				FileUtil.DeleteFile(file_path);
			}
			// Download Published Accessory Profile
			result.addLog("Start download Published Accessory Profile:" + id);
			click(driver.findElement(By.xpath("//*[@id='publishedAccessoryProfile' or @id='publishedVariantProfile']")));
			handleFirefoxDownloadDialog("Save File");
			for (int i = 1; i <= 10; i++) {
				Thread.sleep(1000);
				if (FileUtil.FileExist(file_path)) {
					result.addLog("Profile: " + id + " is downloaded successfully");
					return true;
				}
			}
			result.addLog("Profile: " + id + " is not downloaded successfully");
			return false;
		} catch (NoSuchElementException e) {
			result.addLog("No such element exception");
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Boolean selectDateInCalendar(String xpath, String date) {// date must follow format MMMM d, yyyy
		try {
			// Get day, month and year
			String month = date.substring(0, date.indexOf(" "));
			String day = date.substring(date.indexOf(" ") + 1,
					date.indexOf(","));
			String year = date.substring(date.indexOf(",") + 1, date.length()).trim();
			// Get table
			WebElement table = driver.findElement(By.xpath(xpath));
			// Get row contain month & year data
			WebElement th_row = table.findElement(By.tagName("thead"))
					.findElements(By.tagName("tr")).get(0);
			// Get today button
			WebElement today = table.findElement(By.tagName("tfoot"))
					.findElement(By.className("today"));
			// Check if date input is not valid
			click(today);
			Date currentDate = new Date();
			Date newDate = new SimpleDateFormat("MMMM d, yyyy").parse(date);
			if (newDate.compareTo(currentDate) < 0) {
				result.addLog("The date is invalid");
				return false;
			}
			// Select month and year
			List<WebElement> th_columns = th_row.findElements(By.tagName("th"));
			result.addLog("Select month & year: " + month + " " + year);
			while (!th_columns.get(1).getText().contains(month + " " + year)) {
				th_columns.get(2).click();
				waitForAjax();
			}
			// Select day
			List<WebElement> days = table.findElement(By.tagName("tbody"))
					.findElements(By.className("day"));
			for (WebElement each_day : days) {
				if (each_day.getText().equals(day)) {
					result.addLog("Select day: " + day);
					each_day.click();
					waitForAjax();
					return true;
				}
			}
			result.addLog("Day: " + day + " not found");
			return false;
		} catch (NoSuchElementException e) {
			result.addLog("No element: " + xpath);
			return false;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
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
				result.addLog("Option: " + option.getText());
				list.add(option.getText());
			}
			return list;

		} catch (NoSuchElementException e) {
			result.addLog("No element: " + xpath);
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
				result.addLog("Header text: " + header.getText());
				list.add(header.getText());
			}
			return list;
		} catch (NoSuchElementException e) {
			result.addLog("No such element: " + xpath);
			return null;
		}
	}
	
	public Boolean isElementEditable(ArrayList<String> listXpath){
		try{
		for(String element : listXpath){
			if(!isElementEditable(element)){
				return false;
			}
		}
		return true;
		}catch(NoSuchElementException e){
			result.addLog("No such element exception");
			return null;
		}
	}
	
	public Boolean isAllNotificationCheckboxEnable(String xpath) {
		try {
			// Get table
			WebElement table = driver.findElement(By.xpath(xpath)).findElement(
					By.tagName("tbody"));
			// Get rows
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			// Get columns
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				WebElement checkbox = columns.get(columns.size() - 1).findElement(
						By.tagName("input"));
				if (!checkbox.isDisplayed()){
					continue;
				}
				if (checkbox.getAttribute("disabled") == null) {
					result.addLog("Notification checkbox: " + columns.get(0).getText() + " is enabled");
				} else {
					result.addLog("Notification checkbox: " + columns.get(0).getText() + " is disabled");
					return false;
				}
			}
			return true;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElement " + xpath);
			return false;
		}
	}
	
	public Boolean isAllPrivilegeDisable(String table) {
		try {
			// Get table
			WebElement tbody = driver.findElement(By.xpath(table)).findElement(
					By.tagName("tbody"));
			// Get rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				// Get columns
				List<WebElement> columns = row.findElements(By.tagName("td"));
				// Get privilege
				WebElement privilege = columns.get(0);
				if (!privilege.isDisplayed()){
					continue;
				}
				String disable = privilege.findElement(By.tagName("input"))
						.getAttribute("disabled");
				if (disable != null) {
					result.addLog("Privilege: " + privilege.getText()
							+ " is disabled and uneditable");
				} else {
					result.addLog("Privilege: " + privilege.getText()
							+ " is enabled and editable");
					return false;
				}
			}
			return true;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElement is present");
			return false;
		}
	}
	
	public WebDriver openNewFireFoxBrowser(){
		WebDriver driver = new FirefoxDriver();
		driver.switchTo().defaultContent();
		driver.manage().window().maximize();
		return driver;
	}
	
	public void activeUser(String email, String password) {
		try {
			// Enter email
			result.addLog("Enter email: " + email);
			driver.findElement(By.id("username")).sendKeys(email);
			// Enter password
			result.addLog("Enter password: " + password);
			driver.findElement(By.id("password")).sendKeys(password);
			// Confirm password
			result.addLog("Enter confirm password: " + password);
			driver.findElement(By.id("confirmpassword")).sendKeys(password);
			// Click Sign in link
			result.addLog("Click sign in link");
			driver.findElement(By.xpath("//*[@type = 'submit']")).click();
			waitForAjax();
			Thread.sleep(2000);
			waitForAjax();
		} catch (NoSuchElementException e) {
			result.addLog("No element exception");
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public Boolean activeNewUserViaEmail(String host, String email, String emailPassword, String userPassword){
		result.addLog("Start active user via email");
		try {
			// Get link active user via email
			String link = MailUtil.getLinkActive(host, email, emailPassword);
			// Open new firefox browser for active user
			WebDriver driver = openNewFireFoxBrowser();
			HomeController home = new HomeController(driver, siteBase);
			driver.get(link);
			// Active user
			home.activeUser(email, userPassword);
			if (driver.findElement(By.id("signinId")).getText()
					.equals(email)) {
				result.addLog("User with email: " + email
						+ " is actived successfully");
				// Close Firefox browser
				driver.quit();
				return true;
			} else {
				result.addLog("User with email: " + email
						+ " is not actived successfully");
				driver.quit();
				Assert.assertTrue(false);
			}
		} catch (NoSuchElementException e) {
			result.addLog("No such element");
		}
		return false;
	}
		
	public Boolean isPromotionPublished(String xpath, String promotionID) {
		try {
			int size = getPageSize();
			while (size > 0) {
				// Get table
				WebElement table = driver.findElement(By.xpath(xpath))
						.findElement(By.tagName("tbody"));
				// Get all rows
				List<WebElement> rows = table.findElements(By.tagName("tr"));
				// Find promotion ID
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if (columns.get(0).getText().equals(promotionID)) {
						// Promotion ID found
						// Check publish status
						if (columns.get(4).getText() != "") {
							result.addLog("Promotion ID: " + promotionID
									+ " is published");
							return true;
						}
						result.addLog("Promotion ID: " + promotionID
								+ " is not published");
						return false;
					}
				}
				size--;
				if (size == 0) {
					break;
				}
				clickText("Next");
			}
			result.addLog("Promotion ID: " + promotionID + " not found");
			return false;
		} catch (NoSuchElementException e) {
			result.addLog("No element: " + xpath);
			return null;
		}
	}
	
	public Boolean selectAPromotionByName(String xpath, String promotionName) {
		try {
			int size = getPageSize();
			while (size > 0) {
				// Get table
				WebElement table = driver.findElement(By.xpath(xpath))
						.findElement(By.tagName("tbody"));
				// Get all rows
				List<WebElement> rows = table.findElements(By.tagName("tr"));
				// Find promotion Name
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if ((columns.get(1).getText() + " " + columns.get(2).getText()).equals(promotionName)) {
						// Promotion Name found
						// Select promotion
						result.addLog("Select Promotion Name: " + promotionName);
						columns.get(0).findElement(By.tagName("a")).click();
						waitForAjax();
						return true;
					}
				}
				size--;
				if (size == 0) {
					break;
				}
				clickText("Next");
			}
			result.addLog("Promotion Name: " + promotionName + " not found");
			return false;
		} catch (NoSuchElementException e) {
			result.addLog("No element: " + xpath);
			return null;
		}
	}
	
	public Boolean checkPromotionExistByName(String xpath, String promotionName) {
		try {
			int size = getPageSize();
			while (size > 0) {
				// Get table
				WebElement table = driver.findElement(By.xpath(xpath))
						.findElement(By.tagName("tbody"));
				// Get all rows
				List<WebElement> rows = table.findElements(By.tagName("tr"));
				// Find promotion Name
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if ((columns.get(1).getText() + " " + columns.get(2).getText()).equals(promotionName)) {
						// Promotion Name found
						result.addLog("Promotion Name: " + promotionName
								+ " exists");
						return true;
					}
				}
				size--;
				if (size == 0) {
					break;
				}
				clickText("Next");
			}
			result.addLog("Promotion Name: " + promotionName + " not exist");
			return false;
		} catch (NoSuchElementException e) {
			result.addLog("No element: " + xpath);
			return null;
		}
	}
	
	public Boolean selectBrandFilterByClickOnLink(String xpath,
			String brandOption) {
		try {
			// Get table
			WebElement table = driver.findElement(By.xpath(xpath)).findElement(
					By.tagName("tbody"));
			// Get all rows
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			// Find brand name link
			for (WebElement row : rows) {
				WebElement brand_link = row.findElements(By.tagName("td")).get(1);
				if (brand_link.getText().equals(brandOption)) {
					// Brand filter option found
					// Select brand filter
					result.addLog("Click on brand name link: " + brandOption);
					brand_link.findElement(By.tagName("a")).click();
					waitForAjax();
					return true;
				}
			}
			result.addLog("Brand name link: " + brandOption + " not found");
			return false;
		} catch (NoSuchElementException e) {
			result.addLog("No element: " + xpath);
			return false;
		}
	}
	
	public Boolean isBrandPrivilegeSelected(String xpath, String privilege) {
		try {
			Thread.sleep(3000);
			// Get brand privileges groupbox
			WebElement brand_groupbox = driver.findElement(By.xpath(xpath))
					.findElement(By.tagName("button"));
			// Click on brand_groupbox
			brand_groupbox.click();
			waitForAjax();
			// Get all brand privileges
			List<WebElement> brand_privileges = driver.findElement(
					By.xpath(xpath)).findElements(By.tagName("li"));
			// Check brand privilege selected
			for (WebElement brand_privilege : brand_privileges) {
				if (brand_privilege.getText().equals(privilege)) {
					// Found privilege
					if (brand_privilege.findElement(By.tagName("input"))
							.isSelected()) {
						result.addLog("Privilege: " + privilege
								+ " is selected");
						return true;
					}
					result.addLog("Privilege: " + privilege
							+ " is not selected");
					return false;
				}
			}
			result.addLog("Privilege: " + privilege + " not found");
			return false;
		} catch (NoSuchElementException e) {
			result.addLog("No such element: " + xpath);
			return false;
		} catch(InterruptedException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean handleFirefoxDownloadDialog(String option) {
		try {
			AutoItX.winWait("[Class:MozillaDialogClass]", "", 10);
			AutoItX.winActivate("[Class:MozillaDialogClass]");
			Thread.sleep(1000);
			result.addLog("Select option: " + option);
			if (option.equals("Open with")) {
				AutoItX.send("!o", false);
			} else {
				AutoItX.send("!s", false);
			}
			Thread.sleep(1000);
			AutoItX.send("{ENTER}", false);
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void selectConfirmationDialogOption(String option) {
		try {
			Thread.sleep(3000);
			result.addLog("Select option: " + option);
			click(driver.findElement(By.xpath("//*[@href='javascript:;' and text() = '" + option + "']")));
			Thread.sleep(2000);
			waitForAjax();
		} catch (NoSuchElementException e) {
			result.addLog("No such element exception");
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
		
	public Boolean checkVariantExistbyName(String xpath, String name) {
		try {
			// Get variant container
			WebElement model_variant = driver.findElement(By.xpath(xpath));
			// Get all variants
			List<WebElement> variants = model_variant.findElement(By
					.tagName("span")).findElements(By.tagName("a"));
			for (WebElement variant : variants) {
				// Get the text from link
				if (variant.getText().equals(name)) {
					result.addLog("Variant: " + name + " exists");
					return true;
				}
			}
			result.addLog("Variant: " + name + " not exist");
			return false;
		} catch (NoSuchElementException e) {
			result.addLog("NoSuchElementException: " + xpath);
			return null;
		}
	}
	
	public ArrayList<String> getAllOptionsInDropDown(String xpath){
		ArrayList<String> list = new ArrayList<String>();
		try {
			// Get dropdown box
			WebElement groupBox = driver.findElement(By.xpath(xpath));
			// Click on dropdown
			groupBox.findElement(By.tagName("button")).click();
			waitForAjax();
			// Get option
			List<WebElement> all_li = groupBox.findElement(By.tagName("ul"))
					.findElements(By.tagName("li"));
			for (WebElement li : all_li) {
				result.addLog("Option: " + li.getText());
				list.add(li.getText());
					}
			// Close the dropdown
			groupBox.findElement(By.tagName("button")).click();
			waitForAjax();
			return list;
		} catch (NoSuchElementException e) {
			result.addLog("No element: " + xpath);
			return null;
		}
	}
	
	public void selectAnAudioRouteByName(String name){
		// Get audio route container
		List<WebElement> audioRoutes = driver.findElement(By.id("router-audio-brand-info")).findElements(By.tagName("a"));
		for(WebElement audioRoute : audioRoutes){
			if(audioRoute.getText().equals(name)){
				result.addLog("Select audio route: " + name);
				audioRoute.click();
				waitForAjax();
				return;
			}
		}
		result.addErrorLog("Audio route: " + name + " not found");
	}
	
	public void clickImage(String imageName){
		try {
			Thread.sleep(2000);
			ScreenRegion s = new DesktopScreenRegion();
			Mouse m = new DesktopMouse();
			File path = new File(System.getProperty("user.dir") + "\\asset");
			ScreenRegion image = s.find(new ImageTarget(new File(path
					+ "\\" +  imageName)));
			if(image == null){
				image = scrollMouseUntilImageVisible(imageName);
			}
			m.click(image.getCenter());
			waitForAjax();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public ScreenRegion scrollMouseUntilImageVisible(String imageName) {
		try {
			int i = 0;
			ScreenRegion sreen = new DesktopScreenRegion();
			Mouse mouse = new DesktopMouse();
			ScreenRegion image = null;
			Target target = null;
			File path = new File(System.getProperty("user.dir") + "\\asset");
			target = new ImageTarget(new File(path + "\\" + imageName));
			target.setMinScore(0.9);
			image = sreen.find(target);
			while (image == null && i < 10) {
				mouse.wheel(1, 3);
				Thread.sleep(300);
				image = sreen.find(target);
				i++;
			}
			return image;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Boolean checkADevicePromotionExistByName(String name){
		// Get all Device Promotions 
		List<WebElement> devicePromotions = driver.findElement(By.xpath("//*[@data-bind='foreach: promotions']")).findElements(By.tagName("a"));
		for(WebElement devicePromotion : devicePromotions){
			if(devicePromotion.getText().equals(name)){
				result.addSuccessLog("Device promotion: " + name + " exists");
				return true;
			}
		}
		result.addErrorLog("Device promotion: " + name + " not exist");
		return false;
	}
	
	public void deleteAPromotionByName(String name) {
		click(Xpath.linkPromotions);
		selectAPromotionByName(PromotionList.PROMOTION_TABLE, name);
		doDelete(PromotionInfo.DELETE);
		try {
			if (driver.findElement(By.xpath(PromotionList.PROMOTION_TABLE))
					.isDisplayed()) {
				result.addSuccessLog("Promotion: " + name
						+ " is deleted successfully");
				return;
			}
			result.addErrorLog("Promotion: " + name
					+ " is not deleted successfully");
		} catch (NoSuchElementException e) {
			result.addErrorLog("Promotion: " + name
					+ " is not deleted successfully");
		}
	}
	
	public ArrayList<PromotionPackage> getPromotionPackage(
			String promotionContainerXpath) {
		ArrayList<PromotionPackage> list = new ArrayList<PromotionPackage>();
			// Get all promotion package
			List<WebElement> promotionPackages = driver.findElement(
					By.xpath(promotionContainerXpath)).findElements(
					By.name("promotion_div"));
			for (WebElement promotionPackage : promotionPackages) {
				PromotionPackage p = new PromotionPackage();
				p.Txt_PromotionID = promotionPackage.findElement(By
						.className("promtiondtsId-box"));
				p.promotionName = promotionPackage.findElement(By
						.name("promotion_product_name"));
				p.warningMessage = promotionPackage.findElement(By
						.name("promotion-error-message"));
				p.deleteLink = promotionPackage.findElement(By
						.linkText("Delete"));
				list.add(p);
			}
			result.addLog("Number of promotion package is: " + list.size());
		return list;
	}
	
	public void deleteADevicePromotionByName(String promotionContainerXpath, String name){
		// Get promotion packages
		ArrayList<PromotionPackage> list = getPromotionPackage(promotionContainerXpath);
		for(PromotionPackage eachPackage : list){
			if(eachPackage.promotionName.getText().contains(name.replace(" ", " - "))){
				result.addLog("Delete promotion: " + name);
				eachPackage.deleteLink.click();
				selectConfirmationDialogOption("Delete");
				return;
			}
		}
		result.addErrorLog("Promotion: " + name + " not found");
	}
	
	public void selectProductInPromotionList(String name) {
		try {
			int size = getPageSize();
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if (columns.get(2).getText().equals(name)) {
						result.addLog("Select product: " + name);
						columns.get(2).findElement(By.tagName("a")).click();
						waitForAjax();
						return;
					}
				}
				clickText("Next");
			}
			result.addLog("Product: " + name + " not found");
		} catch (NoSuchElementException e) {
			result.addLog("No element exception");
		}
	}
	
	public String getCSRFToken(){
		return driver.findElement(By.xpath("//meta[@name='_csrf']")).getAttribute("content");
	}
	
	public String getCookie(){
		return driver.manage().getCookieNamed("JSESSIONID").toString();
	}
	
}
