package com.dts.adminportal.controller;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;

import com.dts.adminportal.model.AddCompany;
import com.dts.adminportal.model.BrandInfo;
import com.dts.adminportal.model.Companies;
import com.dts.adminportal.model.CompanyInfo;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.XLicenseProductPackage;
import com.dts.adminportal.model.XPartnerShipPackage;
import com.dts.adminportal.util.DateUtil;
import com.dts.adminportal.wrappers.BaseElement;
import com.dts.adminportal.wrappers.ButtonElement;
import com.dts.adminportal.wrappers.CheckBoxElement;
import com.dts.adminportal.wrappers.ComboBoxElement;
import com.dts.adminportal.wrappers.LinkTextElement;
import com.dts.adminportal.wrappers.TextBoxElement;

public class CompanyController extends BaseController {
	
	public CompanyController(WebDriver driver) {
		super(driver);
	}
	
	public boolean addCompany(Hashtable<String, String> hashXpath,
			Hashtable<String, String> data) {
		boolean result = true;
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
			addLog(e.toString());
			result = false;
			e.printStackTrace();
		}
		return result;
	}
	
	public Boolean selectDateInCalendar(String xpath, String date1) {// date must follow format MMMM d, yyyy
		try {
			// Get day, month and year
			SimpleDateFormat sdfAmerica = new SimpleDateFormat("MMMM d, yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
			Date date2 = formatter.parse(date1);
			String date = sdfAmerica.format(date2);
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
				addLog("The date is invalid");
				return false;
			}
			// Select month and year
			List<WebElement> th_columns = th_row.findElements(By.tagName("th"));
			addLog("Select month & year: " + month + " " + year);
			while (!th_columns.get(1).getText().contains(month + " " + year)) {
				th_columns.get(2).click();
				waitForAjax();
			}
			// Select day
			List<WebElement> days = table.findElement(By.tagName("tbody"))
					.findElements(By.className("day"));
			for (WebElement each_day : days) {
				if ((each_day.getText().equals(day)) && (!each_day.getAttribute("class").contains("disabled"))) {
					addLog("Select day: " + day);
					each_day.click();
					waitForAjax();
					return true;
				}
			}
			addLog("Day: " + day + " not found");
			return false;
		} catch (NoSuchElementException e) {
			addLog("No element: " + xpath);
			return false;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean selectABrand(String brandList) {
		try {
			// Get the brand list
			WebElement brand_list = driver.findElement(By.xpath(brandList)); 
			// Get a brand
			WebElement aBrand = brand_list.findElement(By.tagName("div"));
			// Click on brand name
			addLog("click on a brand");
			aBrand.findElement(By.tagName("a")).click();
			waitForAjax();
					
		} catch (NoSuchElementException e) {
			System.err.println("No element exception");
			return false;
		}
		return true;
	}
	
	// TODO move to wrapper class
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
					addLog("Select brand: " + brandname);
					brand_name_tag.click();
					waitForAjax();
					return true;
				}
			}
			addLog("Brand: " + brandname + " not found");
			return false;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElement :" + Xpath);
		}
		return false;
	}
	
	// TODO move to wrapper class
	public boolean selectACompanyByName(String companyName) {
		try {
			// Get page size to click next page
			Thread.sleep(4000);
			int size = getPageSize(PageHome.COMPANY_LIST_TABLE_INFO);
//			clickText("Last");
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
						addLog("Select company: " + companyName);
						columns.get(0).findElement(By.tagName("a")).click();
						waitForAjax();
						return true;
					}
				}
				clickText("Next");
			}
			addLog("Company:" + companyName + " not found");
			return false;

		} catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException");
		} catch (InterruptedException e) {
			return false;
		}
		return false;
	}
	
	public Integer getCompanyIdByCharacter(String name){
		try {
			Thread.sleep(2000);
			int size = getPageSize(PageHome.COMPANY_LIST_TABLE_INFO);
			addLog("Comany page size: " + size);
			clickText("Last");
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if ((columns.get(0).getText().contains(name)) && (columns.get(0).getText().length() == 9)) {
						addLog("Select company : " + columns.get(0).getText());
						String id = columns.get(0).findElement(By.tagName("a")).getAttribute("value");
						System.out.println(id);
						return Integer.valueOf(id);
					}
				}
//				clickText("Next");
				clickText("Previous");
			}
			addErrorLog("compaay: " + name + " not found");
		} catch (NoSuchElementException e) {
			addErrorLog("Company: " + name + " not found");
			return 0;
		} catch (InterruptedException e) {
			return 0;
		}
		return 0;
	}
		
	public void deleteACompanyByName(String companyName) {
		// Select a company
		selectACompanyByName(companyName);
		scrollTo(0, 0);
		// Delete
		doDelete(CompanyInfo.DELETE);
	}	
	
	// TODO is this function only use for company? Or we can make it generic?
	public boolean checkTimeEnding(String tableBody, int index, int days) {
		boolean result = true;
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
					addLog("Time is before Now : " + time);
					result = false;
					return result;
				}
			}
			// check time ending
			for (String time : listTimes) {
				if (!DateUtil.isBetween(time, days)) {
					addLog("Time doesn't within " + days + " rolling day");
					result = false;
					return result;
				} else {
					addLog("Time OK!");
				}
			}
			// click next page
			totalPage--;
			if (totalPage > 0) {
				try {
					driver.findElement(By.linkText("Next")).click();
					waitForAjax();
				} catch (NoSuchElementException e) {
					addLog("NoSuchElementException : Next");
					result = false;
				}
			}
		}
		return result;
	}
	
	// TODO: not yet checked
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
				addLog(dayTime);
				listText.add(dayTime);
			}
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException");
		}
		return listText;
	}
	
	// TODO 
	private int totalPage() {
		try {
			WebElement page = driver.findElement(By.xpath(PageHome.PAGE_INDEX));
			List<WebElement> pages = page.findElements(By.tagName("a"));
			return pages.size();
		} catch (NoSuchElementException e) {
			addLog("No Element : " + PageHome.PAGE_INDEX);
		}
		return 0;
	}
	
	// TODO: move following method to Wrapper class - TableElement
	public ArrayList<String> getTableRowValue(String tbody, int index) {
		try {
			waitForAjax();
			WebElement body = driver.findElement(By.xpath(tbody)).findElement(By.tagName("tbody"));
			// get row value from index
			ArrayList<String> list = new ArrayList<String>();
			List<WebElement> rows = body.findElements(By.tagName("tr"));
			List<WebElement> columns = rows.get(index).findElements(By.tagName("td"));
			for (int i = 0; i < columns.size(); i++) {
				addLog("Text: " + columns.get(i).getText());
				list.add(columns.get(i).getText());
			}
			return list;
		} catch (NoSuchElementException e) {
			addLog("No element exception");
			return null;
		}
	}
	
	// TODO: do we need a new class for all these things: addBrand, addProduct,
	// AddUser , etc.
	public boolean addBrand(Hashtable<String, String> hashXpath, Hashtable<String, String> data) {
		boolean result = true;
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
			addLog("NoSuchElementException on addBrand ");
			result = false;
		}
		return result;
	}
	
	// TODO move to wrapper class or make generic and move to BaseController
	public boolean checkACompanyExistByName(String companyName) {
		try {
			// Get page size to click next page
			int size = getPageSize(PageHome.COMPANY_LIST_TABLE_INFO);
			for (int i = 0; i < size; i++) {
				// Get the table
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (int j = 0; j < rows.size(); j++) {
					// Get all column from all rows
					List<WebElement> columns = rows.get(j).findElements(By.tagName("td"));
					// Get company name from first columns
					String company_name = columns.get(0).getText();
					if (company_name.equals(companyName)) {
						addLog("Company: " + companyName + " exists");
						return true;
					}
				}
				clickText("Next");
			}
			addLog("Company: " + companyName + " not exist");
			return false;

		} catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException");
		}
		return false;
	}
	
	public boolean checkBrandExist(String brandname) {
		String Xpath = CompanyInfo.BRAND_LIST;
		try {
			// Get brand list
			WebElement brandlist = driver.findElement(By.xpath(Xpath));
			// Get all brands
			List<WebElement> brands = brandlist.findElements(By.tagName("div"));
			for (int i = 0; i < brands.size(); i++) {
				// Get brand name
				WebElement brand_name_tag = brands.get(i).findElement(By.tagName("a"));
				String brand_name = brand_name_tag.getText();
				if (brand_name.equals(brandname)) {
					addLog("Brand: " + brandname + " exists");
					return true;
				}
			}
			addLog("Brand: " + brandname + " does not exist");
			return false;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElement: " + Xpath);
		}
		return false;
	}
	
	public ArrayList<XPartnerShipPackage> getPartnershipPackage(String Xpath) {
		waitForAjax();
		ArrayList<XPartnerShipPackage> list = new ArrayList<XPartnerShipPackage>();
		try {
			// Get all brands in the brand table
			WebElement partnershipTable = driver.findElement(By.xpath(Xpath));
			List<WebElement> all_li = partnershipTable.findElements(By.tagName("li"));
			for (WebElement each_li : all_li) {
				XPartnerShipPackage partnerPackage = new XPartnerShipPackage();
				partnerPackage.type = each_li.findElement(By.tagName("span")).getText();
				addLog("Partnership: " + partnerPackage.type);
				partnerPackage.deletelink = each_li.findElement(By.tagName("a"));
				list.add(partnerPackage);
			}
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException : " + Xpath);
		}
		return list;
	}
	public ArrayList<XLicenseProductPackage> getLicenseProductPackage(String Xpath) {
		waitForAjax();
		ArrayList<XLicenseProductPackage> list = new ArrayList<XLicenseProductPackage>();
		try {
			// Get all brands in the brand table
			WebElement licenseproductTable = driver.findElement(By.xpath(Xpath));
			List<WebElement> all_li = licenseproductTable.findElements(By.tagName("li"));
			for (WebElement each_li : all_li) {
				XLicenseProductPackage productPackage = new XLicenseProductPackage();
				productPackage.type = each_li.findElement(By.tagName("span")).getText();
				addLog("License product: " + productPackage.type);
				productPackage.deletelink = each_li.findElement(By.tagName("a"));
				list.add(productPackage);
			}
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException : " + Xpath);
		}
		return list;
	}
	// TODO make generic and move to BaseController
	public int getRowIndexByEmail(String xpath, String email) {
		try {
			// Get table
			WebElement tbody = driver.findElement(By.xpath(xpath)).findElement(By.tagName("tbody"));
			// Get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (int i = 0; i < rows.size(); i++) {
				List<WebElement> columns = rows.get(i).findElements(By.tagName("td"));
				if (columns.get(columns.size() - 2).getText().contains(email)) {
					addLog("The row index is: " + i);
					return i;
				}
			}
			addLog("Not found row index for email: " + email);
			return 0;

		} catch (NoSuchElementException e) {
			addLog("NoSuchElement: " + xpath);
		}
		return 0;
	}

	// TODO make generic and move to BaseController
	public int getRowIndexByName(String xpath, String name) {
		try {
			// Get table
			WebElement tbody = driver.findElement(By.xpath(xpath)).findElement(By.tagName("tbody"));
			// Get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for(int i = 0; i < rows.size(); i++){
				List<WebElement> columns = rows.get(i).findElements(By.tagName("td"));
				if(columns.get(0).getText().equals(name)){
					addLog("The row index is: " + i );
					return i;
				}
			}
			addLog("Not found row index for name: " + name);
			return 0;
			
		} catch (NoSuchElementException e) {
			addLog("NoSuchElement: " + xpath);
		}
		return 0;
	}
	
	// TODO 
	public Boolean deleteAllBrandExceptOne(String xpath, String brandName) {
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
				addLog("Delete brand: " + name);
				doDelete(BrandInfo.DELETE_LINK);
			}
		} catch (NoSuchElementException e) {
			addLog("No Element: " + xpath);
			return false;
		}
		return true;
	}
	
	public Boolean checkConfirmationDialog(String objectName, String option) {
		try {
			Thread.sleep(3000);
			option = option.toLowerCase();
			String message = "Are you sure you want to " + option + " " + objectName;
			Boolean check_message = getElementText(driver.findElement(By.className("modal-body"))).contains(message);
			option = WordUtils.capitalize(option);
			Boolean button1 = isElementPresent(PageHome.BTN_CONFIRMATION_CANCEL);
			Boolean button2 = getTextByXpath(PageHome.BTN_CONFIRMATION_DANGER).equals(option);
			if (check_message == button1 == button2 == true) {
				addLog("Confirmation dialog displays with all information correctly");
				return true;
			}
			addLog("Confirmation dialog does not display with all information correctly");
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}	
	
	public Boolean isPartnershipsEditable(String xpath) {
		// Get partnerships value
		try {
			Boolean flag = null;
			List<WebElement> values = driver.findElement(By.xpath(xpath)).findElements(By.tagName("li"));
			// Check if partnerships value is editable
			for (WebElement value : values) {
				if (value.getText().contains("Delete")) {
					addLog("Partnership: " + value.findElement(By.tagName("span")).getText() + " is editable");
					flag = true;
				} else {
					addLog("Partnership: " + value.findElement(By.tagName("span")).getText() + " is not editable");
					flag = false;
				}
			}
			return flag;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElement: " + xpath);
			return null;
		}
	}
	
	// TODO there are some functions with same use as below function -> make generic and move to BaseController
	public ArrayList<String> getUserInfoByIndex(String xpath, int index) {
		try {
			WebElement table = driver.findElement(By.xpath(xpath));
			WebElement headers = table.findElement(By.tagName("thead")).findElement(By.tagName("tr"));
			List<WebElement> each_header = headers.findElements(By.tagName("th"));
			WebElement body = table.findElement(By.tagName("tbody"));
			// Get the contact row
			List<WebElement> rows = body.findElements(By.tagName("tr"));
			// Get all columns of each row
			List<WebElement> columns = rows.get(index).findElements(By.tagName("td"));
			ArrayList<String> user_info = new ArrayList<String>();
			// Put info of each column into array
			for (int i = 0; i < columns.size(); i++) {
				if (each_header.get(i).getText().contains("Title")) {
					continue;
				}
				addLog("Add user info to array: " + columns.get(i).getText());
				user_info.add(columns.get(i).getText());

			}
			return user_info;

		} catch (NoSuchElementException e) {
			addLog("NoSuchElement: " + xpath);
		}
		return null;
	}
	
	public void dragDropBrandImage(String fileName,String fileType) {
		try {
			ScreenRegion screen = new DesktopScreenRegion();
			Mouse mouse = new DesktopMouse();
			Robot robot = new Robot();
			robot.mouseMove(500,500);
			ScreenRegion dragDropArea = null;
			Target target = null;
			ScreenLocation location = null;
			File path = new File(System.getProperty("user.dir") + File.separator + "asset");
			File path_target = new File(System.getProperty("user.dir") + File.separator + "asset/target");
			// Get file ext
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());	
			System.out.println(fileExt);
			// Scroll up to top page
			//autoTool.mouseWheel(50);
			// scroll to the element
//			autoTool.mouseWheel("up",4);
			int i = 0;
			while(i < 20){
				Thread.sleep(300);
				target = new ImageTarget(new File(path_target + File.separator +  "ZBrand" + fileType));
//				target = new ImageTarget(new File(path + File.separator + "ImageDragDropArea.PNG"));
				target.setMinScore(0.9);
				dragDropArea = screen.find(target);
				if (dragDropArea == null) {
					i++;
					autoTool.mouseWheel("down",3);
					Thread.sleep(500);
				} else {
					break;
				}
				
			}
			if (dragDropArea != null) {
				location = dragDropArea.getRelativeScreenLocation(400,40);
			} else {
				System.out.println("abc");
			}

			// Open directory contain file
			Desktop.getDesktop().open(path);
			// Move window to the right corner
			int width = screen.getWidth();
			autoTool.winWait("asset");
			autoTool.winActivate("asset");
			autoTool.winMove("asset", "", width - 600, 0, 600, 600);
			Thread.sleep(2000);
			// Drag and Drop file to Marketing Container
			ScreenRegion dragDropFile = null;
			int j=0;
			while(dragDropFile == null && j<20){
				autoTool.mouseWheel("down",3);
				Thread.sleep(300);
//				dragDropFile = sreen.find(new ImageTarget(new File(path
//						+ File.separator + fileName + ".png")));
				Target imageTarget = new ImageTarget(new File(path_target + File.separator + fileName + ".png"));
				imageTarget.setMinScore(0.9);
				dragDropFile = screen.wait(imageTarget, 1000);
				addLog("Drag and drop file: " + fileName
						+ " to Drag Drop Area");
				j++;
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
	
	public boolean checkInfoCompanyByName(String option, String companyName, String status, String type, String totalProduct, String totalPublishedProduct, String totalVariant) {
		try {
			int size = getPageSize(PageHome.COMPANY_LIST_TABLE_INFO);
			addLog("page size: " + size);
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By.tagName("td"));
					if (columns.get(0).getText().equals(companyName)) {
						addLog("Found Company: " + companyName);
						if (option.equals(Companies.option.Recently_Added.getName())) {
							if ((columns.get(1).getText().equals(status)) && (columns.get(2).getText().equals(type)) && 
									(columns.get(4).getText().equals(totalProduct)) && (columns.get(5).getText().equals(totalPublishedProduct)) &&
									(columns.get(6).getText().equals(totalVariant))) {
								return true;
							} else {
								return false;
							}
						} else {
							if ((columns.get(1).getText().equals(status)) && (columns.get(2).getText().equals(type)) && 
									(columns.get(3).getText().equals(totalProduct)) && (columns.get(4).getText().equals(totalPublishedProduct)) &&
									(columns.get(5).getText().equals(totalVariant))) {
								return true;
							} else {
								return false;
							}
						}
					}
				}
				clickText("Next");
			}
			addErrorLog("Element: " + companyName + " not found");
		} catch (NoSuchElementException e) {
			return false;
		}
		return false;
	}
	public void inputBrand(String xpath, String data) {
		try {
			editData(xpath, data);
			int i = 0;
			while (i < 10) {
				if (getAtributeValue("//*[@id='ui-id-1']", "style").contains("display: block")) {
					break;
				}
				else {
					i++;
					Thread.sleep(1000);
				}
			}
			click("//*[@id='ui-id-1']/li[1]");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
	public void inputInboxHP(String xpath, String data) {
		try {
			editData(xpath, data);
			int i = 0;
			while (i < 10) {
				if (getAtributeValue("//*[@id='ui-id-2']", "style").contains("display: block")) {
					break;
				}
				else {
					i++;
					Thread.sleep(1000);
				}
			}
			click("//*[@id='ui-id-2']/li[1]");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean addLicenseeCompany(Hashtable<String, String> data, boolean isEnableLicensee) {
		new TextBoxElement(driver, By.xpath(AddCompany.SALEFORCE_ID)).enterText(data.get("id"));
		new ComboBoxElement(driver, By.xpath(AddCompany.BTN_PARTNER_TYPE)).clickByText(data.get("type"));
		// Select date
		click(AddCompany.btn_Date);
		selectDateInCalendar(AddCompany.DATEPICKER_TABLE, data.get("date"));
		new TextBoxElement(driver, By.xpath(AddCompany.OFFICIAL_CORP_NAME)).enterText(data.get("name"));
		new TextBoxElement(driver, By.xpath(AddCompany.ADDRESS1)).enterText(data.get("address"));
		new TextBoxElement(driver, By.xpath(AddCompany.CITY)).enterText(data.get("city"));
		new TextBoxElement(driver, By.xpath(AddCompany.STATE)).enterText(data.get("state"));
		new ComboBoxElement(driver, By.xpath(AddCompany.BTN_COUNTRY)).clickByText(data.get("country"));
		new ComboBoxElement(driver, By.xpath(AddCompany.PARTNERSHIPS)).clickByText(data.get("partnerships"));
		new ButtonElement(driver, By.xpath(AddCompany.ADD)).click();
		if (isEnableLicensee) {
			new BaseElement(driver, By.xpath(CompanyInfo.LICENSEE_DISABLE)).click();
			new ComboBoxElement(driver, By.xpath(AddCompany.LICENSEE_PRODUCT)).clickByText(data.get("licensedProduct"));
			new ButtonElement(driver, By.xpath(AddCompany.ADD_LICENSEE_PRODUCT)).click();
		}
		// save
		new LinkTextElement(driver, By.xpath(AddCompany.SAVE)).click();
		waitForAjax();
		return true;
	}

	public void enableLicensedProduct() {
		CheckBoxElement e = new CheckBoxElement(driver, By.xpath(AddCompany.LICENSEE_CONTROL));
		if (!e.isChecked()) {
			driver.findElement(By.xpath(CompanyInfo.LICENSEE_DISABLE)).click();
		}
	}
}
