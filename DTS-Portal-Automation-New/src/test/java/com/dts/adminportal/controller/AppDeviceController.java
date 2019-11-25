package com.dts.adminportal.controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.Relative;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.dts.adminportal.model.DeviceEdit;
import com.dts.adminportal.model.DeviceInfo;
import com.dts.adminportal.model.DeviceList;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.SQLiteDbUtil;
import com.dts.pb.dcc.Accessorypb.Accessory;
import com.dts.pb.dcc.Specpb.OutputChain;
import com.dts.pb.dcc.dtscs.Dtscs;

public class AppDeviceController extends BaseController{
	BaseController appDeviceControl;
	public AppDeviceController(WebDriver driver){
		super(driver);
		appDeviceControl = new BaseController(driver);
	}
	
	// TODO need to update
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
			addLog("Select App/Device: " + columns.get(2).getText());
			columns.get(2).findElement(By.tagName("a")).click();
			waitForAjax();
			Thread.sleep(2000);
			waitForAjax();
			return list;
		} catch (NoSuchElementException e) {
			addLog("No element exception");
			return null;
		} catch(InterruptedException e){
			e.printStackTrace();
			return null;
		}
	}
	
	// TODO move to generic function in baseController
	public boolean checkAnAppDeviceExistByName(String name) {
		String xpath= DeviceList.TABLE;
		try {
			int size = getPageSize(PageHome.PRODUCT_TABLE_INFO);
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
						addLog("App/Device: " + name + " exist");
						return true;
					}
				}
				clickText("Next");
			}
			addLog("App/Device: " + name + " not exist");
			return false;

		} catch (NoSuchElementException e) {
			addLog("No such element: " + xpath);
			return false;
		}
	}
	
	public boolean checkValueOfProductTable(String name, String DTS_Tracking, int num) {
		String xpath= DeviceList.TABLE;
		try {
			int size = getPageSize(PageHome.PRODUCT_TABLE_INFO);
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
						addLog("App/Device: " + name + " exist");
						if (row.findElements(By.tagName("td")).get(num).getText().equals(DTS_Tracking)){
							return true;
						}
					}
				}
				clickText("Next");
			}
			addLog("App/Device: " + name + " not exist");
			return false;

		} catch (NoSuchElementException e) {
			addLog("No such element: " + xpath);
			return false;
		}
	}
	
	// TODO Replace below function with generic function in basePage
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
				addLog("Option: " + li.getText());
				list.add(li.getText());
					}
			// Close the dropdown
			groupBox.findElement(By.tagName("button")).click();
			waitForAjax();
			return list;
		} catch (NoSuchElementException e) {
			addLog("No element: " + xpath);
			return null;
		}
	}
	
	// 
	public ArrayList<String> getColumsByIndex(int i) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			int sizePage = getPageSize();
			for (int j = 0; j < sizePage; j++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> colums = row.findElements(By.tagName("td"));
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
	
	public String selectFilterByNames(String xpath, String type) {
		String result = null;
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
			if (type == "Devices") {
				result = "Device";
			}
			else if (type == "Apps"){
				result = "App";
			}
		} catch (NoSuchElementException e) {
			System.err.println("No such element exception : selectOption");
		}
		return result;
	}
	
	// TODO 
	public Boolean checkFilterWithData(String[] select) {
		if (select[0] != DeviceList.COMPANY_DEFAULT_FILTER) {
			// check all data shows up only company
			ArrayList<String> companies = getColumsByIndex(DeviceList.COMPANY_COLUMN);
			Boolean isCorrectCompany = ListUtil.containOnlyString(companies, select[0]);
			if (!isCorrectCompany) {
				return isCorrectCompany;
			}
		}
		if (select[1] != DeviceList.BRAND_DEFAULT_FILTER) {
			// check all data shows up only company
			ArrayList<String> brands = getColumsByIndex(DeviceList.BRAND_COLUMN);
			Boolean isCorrectBrand = ListUtil.containOnlyString(brands, select[1]);
			if (!isCorrectBrand) {
				return isCorrectBrand;
			}
		}
		if (select[2] != DeviceList.TYPE_DEFAULT_FILTER) {
			// check all data shows up only company
			ArrayList<String> types = getColumsByIndex(DeviceList.TYPE_COLUMN);
			Boolean isCorrectType = ListUtil.containOnlyString(types, select[2]);
			if (!isCorrectType) {
				return isCorrectType;
			}
		}
		return true;
	}
	
	// TODO
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
	
	// TODO move to wrapper class
	public ArrayList<String> selectADeviceByName(String name) {
		try {
			Thread.sleep(4000);
			int size = getPageSize(PageHome.PRODUCT_TABLE_INFO);
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By.tagName("td"));
					System.out.println("Text: " + columns.get(2).getText());
					if (columns.get(2).getText().equals(name)) {
						for (WebElement column : columns) {
							list.add(column.getText());
						}
						addLog("Select app/device: " + name);
						columns.get(2).findElement(By.tagName("a")).click();
						waitForAjax();
						Thread.sleep(2000);
						waitForAjax();
						return list;
					}
				}
				clickText("Next");
			}
			addLog("App/Device: " + name + " not found");
			return null;
		} catch (NoSuchElementException e) {
			addLog("No element exception");
			return null;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	// TODO move to wrapper class
	public ArrayList<String> selectALicenseDeviceByName(String name) {
		try {
			Thread.sleep(4000);
			int size = getPageSize(PageHome.PRODUCT_TABLE_INFO);
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By.tagName("td"));
					System.out.println("Text: " + columns.get(1).getText());
					if (columns.get(1).getText().equals(name)) {
						for (WebElement column : columns) {
							list.add(column.getText());
						}
						addLog("Select app/device: " + name);
						columns.get(1).findElement(By.tagName("a")).click();
						waitForAjax();
						Thread.sleep(2000);
						waitForAjax();
						return list;
					}
				}
				clickText("Next");
			}
			addLog("App/Device: " + name + " not found");
			return null;
		} catch (NoSuchElementException e) {
			addLog("No element exception");
			return null;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Integer getDeviceIdByName(String name){
		try {
			Thread.sleep(5000);
			int size = getPageSize(PageHome.PRODUCT_TABLE_INFO);
			addLog("Device page size: " + size);
			clickText("Last");
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if (columns.get(2).getText().equals(name)) {
						addLog("Select device : " + name);
						String id = columns.get(2).findElement(By.tagName("a")).getAttribute("value");
						System.out.println(id);
						return Integer.valueOf(id);
					}
				}
//				clickText("Next");
				clickText("Previous");
			}
			addErrorLog("Accessory: " + name + " not found");
		} catch (NoSuchElementException e) {
			addErrorLog("Product: " + name + " not found");
			return 0;
		} catch (InterruptedException e) {
			return 0;
		}
		return 0;
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
	
	// TODO move to wrapper class or make generic and move to BaseController
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
	
	// move to other class
	public ArrayList<String> createNewDevice(Hashtable<String, String> hashXpath, Hashtable<String, String> data) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			// Fill data
			editData(hashXpath.get("id"), data.get("id"));
			selectOptionByName(hashXpath.get("type"), data.get("type"));
			selectOptionByName(hashXpath.get("eagle version"), data.get("eagle version"));
			selectOptionByName(hashXpath.get("product type"), data.get("product type"));
			selectOptionByName(hashXpath.get("company"), data.get("company"));
			selectOptionByName(hashXpath.get("brand"), data.get("brand"));
			editData(hashXpath.get("name"), data.get("name"));
			selectCheckboxInDropdown(hashXpath.get("os"), data.get("os"));
			editData(hashXpath.get("license"), data.get("license"));
			// Upload default audio route
			uploadFile(hashXpath.get("audio route"), data.get("audio route"));
			if (data.get("product type") != null){
				if (data.get("product type").equals("HPX High")) {
					selectRoomModel(DeviceEdit.CHECK_BOX_MCROOM);
				}
				// Select Featured Accessory Promotions
				if (!data.get("product type").equals("HPX Low")) {
					editData(hashXpath.get("promotion slot"), data.get("promotion slot"));
					if (data.get("global promotion") != null && data.get("global promotion") == "OFF") {
						click(hashXpath.get("global promotion"));
					}
				}
			}
			
			
			// Upload custom tuning
			if (data.containsKey("custom tuning")) {
				clickOptionByIndex(DeviceEdit.AUDIO_ROUTE_TYPE, 1);
				clickOptionByIndex(DeviceEdit.AUDIO_ROUTE, 1);
				uploadFile(hashXpath.get("custom tuning"), data.get("custom tuning"));
			}
			
			if (data.containsKey("save")) {
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
			addLog("No element exception");
			return null;
		}
	}
	// for eagle 2.0
	public ArrayList<String> createNewDeviceEagle20(Hashtable<String, String> hashXpath, Hashtable<String, String> data) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			// Fill data
			editData(hashXpath.get("id"), data.get("id"));
			selectOptionByName(hashXpath.get("type"), data.get("type"));
			selectOptionByName(hashXpath.get("eagle version"), data.get("eagle version"));
			selectOptionByName(hashXpath.get("product type"), data.get("product type"));
			selectOptionByName(hashXpath.get("company"), data.get("company"));
			selectOptionByName(hashXpath.get("brand"), data.get("brand"));
			editData(hashXpath.get("name"), data.get("name"));
			selectCheckboxInDropdown(hashXpath.get("os"), data.get("os"));
			//editData(hashXpath.get("license"), data.get("license"));
			// Upload default audio route
			uploadFile(hashXpath.get("audio route"), data.get("audio route"));
			if (data.get("product type") != null){
				if (data.get("product type").equals("HPX High")) {
					selectRoomModel(DeviceEdit.CHECK_BOX_MCROOM);
				}
				// Select Featured Accessory Promotions
				if (!data.get("product type").equals("HPX Low")) {
					editData(hashXpath.get("promotion slot"), data.get("promotion slot"));
					if (data.get("global promotion") != null && data.get("global promotion") == "OFF") {
						click(hashXpath.get("global promotion"));
					}
				}
			}
			
			
			// Upload custom tuning
//			if (data.containsKey("custom tuning")) {
//				clickOptionByIndex(DeviceEdit.AUDIO_ROUTE_TYPE, 1);
//				clickOptionByIndex(DeviceEdit.AUDIO_ROUTE, 1);
//				uploadFile(hashXpath.get("custom tuning"), data.get("custom tuning"));
//			}
			
			if (data.containsKey("save")) {
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
			addLog("No element exception");
			return null;
		}
	}
	
	public ArrayList<String> createADeviceOneBrand(Hashtable<String, String> hashXpath, Hashtable<String, String> data, String nameBrand) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			// Fill data
			editData(hashXpath.get("id"), data.get("id"));
			selectOptionByName(hashXpath.get("type"), data.get("type"));
			selectOptionByName(hashXpath.get("eagle version"), data.get("eagle version"));
			selectOptionByName(hashXpath.get("product type"), data.get("product type"));
			selectOptionByName(hashXpath.get("company"), data.get("company"));
			selectOptionByName(hashXpath.get("brand"), data.get("brand"));
			editData(hashXpath.get("name"), data.get("name"));
			selectCheckboxInDropdown(hashXpath.get("os"), data.get("os"));
			editData(hashXpath.get("license"), data.get("license"));
			// Upload default audio route
			uploadFile(hashXpath.get("audio route"), data.get("audio route"));
			selectRoomModel(DeviceEdit.CHECK_BOX_MCROOM);
			// Upload custom tuning
			if (data.containsKey("custom tuning")) {
				clickOptionByIndex(DeviceEdit.AUDIO_ROUTE_TYPE, 1);
				clickOptionByIndex(DeviceEdit.AUDIO_ROUTE, 1);
				uploadFile(hashXpath.get("custom tuning"), data.get("custom tuning"));
			}
			// Select Featured Accessory Promotions
			editData(hashXpath.get("promotion slot"), data.get("promotion slot"));
			if (data.get("global promotion") != null && data.get("global promotion") == "OFF") {
				click(hashXpath.get("global promotion"));
			}
			click(DeviceEdit.LARGE_IMAGE);
			click(DeviceEdit.MEDIUM_IMAGE);
			click(DeviceEdit.ONLY_BRANDS);
			inputData(DeviceEdit.BRAND_FILTER, nameBrand);
			Thread.sleep(2000);
			waitForAjax();
			if (data.containsKey("save")) {
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
			addLog("No element exception");
			return null;
		}catch(InterruptedException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public  boolean isDisable(){
		List<WebElement> deletebuts = driver.findElements(By.xpath("//*[contains(@id,'icon-trash-')]"));
		for (WebElement deletebut : deletebuts){
			if(deletebut.isEnabled()){
				return true;
			}
		}
		return false;
	}
	public boolean selectRoomModel(String xpath) {
		try {
			List<WebElement> checkboxs = driver.findElements(By.name("roommodels"));
            for (WebElement checkbox : checkboxs){
            	if (!checkbox.isSelected() && checkbox.isEnabled()) {
    				addLog("Select checkbox: " + xpath);
    				checkbox.click();
    				waitForAjax();
    				return true;
    			}
            }
			addLog("Checkbox: " + xpath + " is already selected");
			return true;
		} catch (NoSuchElementException e) {
			addLog("No such element: " + xpath);
			return false;
		}
	}
	
	// TODO make generic and move to BaseController
	public Boolean selectCheckboxInDropdown(String xpath, String name) {
		try {
			if (name == null | name == "") {
				return null;
			}
			// Get dropdown box
			WebElement groupBox = driver.findElement(By.xpath(xpath));
			// Click on dropdown
			groupBox.findElement(By.tagName("button")).click();
			waitForAjax();
			// Select checkbox
			List<WebElement> checkboxs = groupBox.findElement(By.tagName("ul")).findElements(By.tagName("li"));
			for (WebElement checkbox : checkboxs) {
				if (checkbox.getText().contains(name)) {
					if (!checkbox.findElement(By.tagName("input")).isSelected()) {
						addLog("Select checkbox: " + name);
						checkbox.findElement(By.tagName("input")).click();
						waitForAjax();
						break;
					}
					addLog("Checkbox: " + name + " is already selected");
					break;
				}
			}
			// Close the dropdown
			groupBox.findElement(By.tagName("button")).click();
			waitForAjax();
			return true;
		} catch (NoSuchElementException e) {
			addLog("No element: " + xpath);
			return null;
		}
	}
	
	public Boolean checkADevicePromotionExistByName(String name) {
		// Get all Device Promotions
		List<WebElement> devicePromotions = driver.findElement(By.xpath("//*[@data-bind='foreach: promotions']"))
				.findElements(By.tagName("a"));
		for (WebElement devicePromotion : devicePromotions) {
			if (devicePromotion.getText().equals(name)) {
				addSuccessLog("Device promotion: " + name + " exists");
				return true;
			}
		}
		addErrorLog("Device promotion: " + name + " not exist");
		return false;
	}
	
	public Boolean checkSortByAlphabet(String Xpath){
		String check_item = appDeviceControl.getTextByXpath(Xpath);
		ArrayList<String> check_arr = new ArrayList<String>(Arrays.asList(check_item.split("</br>")));
		ArrayList<String> sorted_arr = new ArrayList<String>(Arrays.asList(check_item.split("</br>")));
		Collections.sort(sorted_arr, String.CASE_INSENSITIVE_ORDER);
		if(check_arr.equals(sorted_arr))
			return true;
		return false;
	}
	
	public void dragDropPromo(int source,int des){
		try{
			ScreenRegion screen = new DesktopScreenRegion();
			Mouse mouse = new DesktopMouse();
			Robot robot = new Robot();
			robot.mouseMove(500,500);
			ScreenRegion dragDropArea = null;
//			ScreenLocation location = null;
			File path = new File(System.getProperty("user.dir") + File.separator + "asset");
//			location = dragDropArea.getCenter();
			ScreenRegion dragDropFile = null;
//			if(source<des){
//				des=des+1;
//			}
//			System.out.println(des);
			int t=0;
			Thread.sleep(3000);
			while(dragDropFile == null && t<20){
				autoTool.mouseWheel("down", 4);
				Thread.sleep(300);
//				dragDropFile = sreen.find(new ImageTarget(new File(path + File.separator +"Promo"+ source + ".png")));
				Target imageTarget = new ImageTarget(new File(path + File.separator +"Promo"+ source + ".png"));
				imageTarget.setMinScore(0.9);
				dragDropFile = screen.wait(imageTarget, 1000);
//				dragDropFile = screen.find(imageTarget);
				t++;
			}
			mouse.drag(dragDropFile.getCenter());
			int i =0;
			Thread.sleep(2000);
			while(dragDropArea == null && i<20){
				autoTool.mouseWheel("down",2);
				Thread.sleep(300);
//				dragDropArea = screen.find(new ImageTarget(new File(path + File.separator +"Promo"+des+".png")));
				Target imageTarget = new ImageTarget(new File(path + File.separator +"Promo"+ des + ".png"));
				imageTarget.setMinScore(0.9);
				dragDropArea = screen.wait(imageTarget, 1000);
			}
//			mouse.drop(dragDropArea.getCenter()); 
			Thread.sleep(3000);
			waitForAjax();
			mouse.drop(Relative.to(dragDropArea.getCenter()).right(60).above(10).left(20).getScreenLocation());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public ArrayList<String> getListPromo(String publish_status){
		String temp = appDeviceControl.getTextByXpath(DeviceInfo.devicePromtionsContainer(publish_status));
		ArrayList<String> list_promo  = new ArrayList<String>(Arrays.asList(temp.split("Expires: Never")));
		for (int i = 0; i < list_promo.size(); i++) {
			String promo = list_promo.get(i).replace("\n", "").replace("\r", "").trim();
			list_promo.set(i, promo);
		}
		return list_promo;
	}
	
	public ArrayList<String> getListOrder(){
		List<WebElement> list_order = driver.findElements(By.name("promotion_product_name"));
		ArrayList<String> name_promo = new ArrayList<String>();
		for(int i=0; i<list_order.size();i++){
			name_promo.add(appDeviceControl.getTextByXpath(list_order.get(i)));
		}
		return name_promo;
		
	}
	public boolean isPromotionOrder(int sourceIndex,int destinationIndex,ArrayList<String> before,ArrayList<String> after){
		sourceIndex = sourceIndex -1;
		destinationIndex = destinationIndex -1;
		for(int i=0;i<before.size();i++){
			System.out.println(i+before.get(i));
			System.out.println(i+after.get(i));
		}
		if(sourceIndex>destinationIndex){
			if(before.get(destinationIndex).equals(after.get(destinationIndex+1)) && before.get(sourceIndex).equals((after.get(destinationIndex))))
				return true;
		}
		if(sourceIndex<destinationIndex){
			if(before.get(sourceIndex).equals(after.get(destinationIndex)) && before.get(destinationIndex).equals(after.get(destinationIndex-1)))
				return true;
		}
		return false;
	}
	
	public boolean checkListOrder(ArrayList<String> listPromo,ArrayList<String> listOrder){
		boolean flag = true;
		for(int i=0; i<listPromo.size(); i++){
			if (listPromo.get(i) != null && listOrder.get(i) != null) {
				System.out.println("Promo: " + listPromo.get(i).toString().trim());
				System.out.println("Order: " + listOrder.get(i).substring(1,listOrder.get(i).length()-1).trim());
				if(!(listOrder.get(i).substring(1,listOrder.get(i).length()-1)).toString().trim().equals((listPromo.get(i).toString()).trim())){
					System.out.println("Failed");
					flag = false;
					return flag;
				}
			}
	}
	return flag;
}
	
	public void editVersion() {
		String publish_status = appDeviceControl.getTextByXpath(DeviceInfo.PUBLICATION_STATUS);
		if(publish_status.equals("Published"))
		{
			appDeviceControl.click(DeviceInfo.CREATE_NEW_VERSION);
			appDeviceControl.selectConfirmationDialogOption("OK");
		} else appDeviceControl.click(DeviceInfo.EDIT);
	}
	
	public WebElement getPromotionSlot(int num){
		String xpath = "//label[contains(text(),'Promo "+num+"')]/parent::div[@class='control-label']";
		WebElement promo_slot = driver.findElement(By.xpath(xpath));
		return promo_slot;
	}
	
	public boolean isReorderIconNextToPromo(){
		List<WebElement> forms = driver.findElements(By.name("promotion_div"));
		int count = forms.size();
		for(int i=0;i<count;i++){
			if(!appDeviceControl.isElementDisplayVertically(DeviceEdit.promotionslotlabel(i+1),DeviceEdit.reorder_icon(i+1))){
				return true;
			}	
		}
		return false;
	}
	
	public boolean isNotContainsNonPartner(ArrayList<String> nonPartner){
		ArrayList<String> listCompany = appDeviceControl.getOptionList(DeviceEdit.COMPANY);
		listCompany.retainAll(nonPartner);
		if(listCompany.size() > 0){
			System.err.println("Non-partner displayed : ");
			for (String item : listCompany) {
				System.err.println("\t"+item);
			}
			return false;
		}
		return true;
	}
	
	public String getOfflineDBName(String dtsID){
		String dbName = "offlinedatabase_"+dtsID+".db";
		return dbName;
	}
	public String getOfflineINITName(String dtsID){
		String dbName = "dccinit_"+dtsID+".db";
		return dbName;
	}
	public String getOfflineDBNameUI(String dtsID){
		String dbName = "offlinedatabase_ui_"+dtsID+".db";
		return dbName;
	}
	public String getOfflineDBNameTuning(String dtsID){
		String dbName = "offlinedatabase_tuning_"+dtsID+".db";
		return dbName;
	}
	public boolean isIncludedBrand(String dbName,String brandName){
		System.out.println("sqlite: " + dbName);
		SQLiteDbUtil sqlite = new SQLiteDbUtil(dbName);
		ArrayList<String> isIncludeBrand = sqlite.getColumnData("select d.BrandId,b.Name from DeviceBrand d join Brand b on d.BrandId = b.Id where BrandId in ( select Id from Brand where Name = '"+brandName+"')", "Name", null);
		System.out.println("isincludeBrand: " + isIncludeBrand);
		System.out.println("New brand is "+brandName);
		if(isIncludeBrand.get(0).contains(brandName)){
			System.out.println("New brand "+isIncludeBrand.get(0)+" is included in DeviceBrand table.");
			return true;
		}
		return false;	
	}
	
	public boolean isDBContainListOrder(String dbName,ArrayList<String> listPromo){
		boolean flag = true;
		SQLiteDbUtil sqlite = new SQLiteDbUtil(dbName);
		ArrayList<String> listOrderDB = sqlite.getColumnData("select d.ListOrder,p.Id,p.Name from Product p join DeviceFeaturedProduct d on p.Id=d.ProductId where p.Id in (select ProductId from DeviceFeaturedProduct)", "Name", null);
		if (listOrderDB.size() == 0) {
			return false;
		}
		for(int i=0;i<listPromo.size();i++){
			if(!listPromo.get(i).contains(listOrderDB.get(i))){
				System.out.println("Promotion on UX: "+listPromo.get(i)+"Promotion on DB: "+listOrderDB.get(i)+" not matched.");
				return flag = false;
			}
		}
		return flag;
	}
	
	public boolean isDBEmptyFeaturedHP(String dbName){
		SQLiteDbUtil sqlite = new SQLiteDbUtil(dbName);
		ArrayList<String> listOrderDB = sqlite.getColumnData("select d.ListOrder,p.Id,p.Name from Product p join DeviceFeaturedProduct d on p.Id=d.ProductId where p.Id in (select ProductId from DeviceFeaturedProduct)", "Name", null);
		if (listOrderDB.size() == 0) {
			return true;
		} else {
			return false;
		}

	}
	
	public boolean isContainMultiCHRoom(String dbName){
		SQLiteDbUtil sqlite = new SQLiteDbUtil(dbName);
		ArrayList<String> listMultiCH = sqlite.getColumnData("select Id from DeviceMultiChannelRoomModel", "Id", null);
		if (listMultiCH.size() != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getSizeMultiCHRoom(String dbName){
		try {
			Thread.sleep(3000);
			int size = 0;
			SQLiteDbUtil sqlite = new SQLiteDbUtil(dbName);
			ArrayList<String> listMultiCH = sqlite.getColumnData("select Id from DeviceMultiChannelRoomModel", "Id", null);
			size = listMultiCH.size();
			return size;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}

	public float getFileSizeinMB(String fileName){
		String url = System.getProperty("user.home")+"/Downloads/"+fileName;
		File file = new File(url);
		float size = file.length();
		float sizeMB = size / (float) (1024 * 1024);
		return sizeMB;
		//float sizeKB = size/ (float) 1024;
	}
	
	public boolean isNotExceed1MB(String fileName){
		float sizeMB = getFileSizeinMB(fileName);
		if(sizeMB > 1){
			return false;
		}
		return true;
	}
		
	public boolean isDBContainsInbox(String dbName, String inboxUUID){
		SQLiteDbUtil sqlite = new SQLiteDbUtil(dbName);
		ArrayList<String> inboxCol = sqlite.getColumnData("select InboxUuid from Device", "InboxUuid", null);
		if(inboxCol.get(0).equals(inboxUUID)){
			return true;
		}
		return false;
	}
	
	public boolean isDBTableEmptyData(String dbName, String sql,String col){
		SQLiteDbUtil sqlite = new SQLiteDbUtil(dbName);
		ArrayList<String> ColumnData = sqlite.getColumnData(sql, col, null);
		if (ColumnData.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean getBinFile(String dbName, String sql, String col,String dts_id){
		SQLiteDbUtil sqlite = new SQLiteDbUtil(dbName);
		boolean check = sqlite.getBlobData(col, sql, dts_id);
		return check;
	}
	// Check profile (data from db) only contain one ealge version (1.4 or 2.0) corresponding with app&device
	public boolean isMapEagleVersion(String dtsId, String eagle_Version) {
		boolean check = false;
		int i = 0;
		Dtscs dtscs;
		String file = System.getProperty("user.home") + "/Downloads/profile_" + dtsId + ".dtscs";
		try {
			dtscs = Dtscs.parseFrom(
					new FileInputStream(new File(file)));
			List<Accessory> listAccessory = dtscs.getProfile(0).getAccessoryCollectionList();
			for (Accessory accessory : listAccessory) {
				List<OutputChain> listOutputChains = accessory.getSpec().getClassification(0).getSupportedOutputChainsList();
				for (OutputChain outputChains : listOutputChains) {
					System.out.println("Has eagle 1.4---"+ outputChains.getRoute().getTech(0).hasEagleV11());
					System.out.println("Has eagle 2.0---" + outputChains.getRoute().getTech(0).hasEagleV20());
					if ((((outputChains.getRoute().getTech(0).hasEagleV11()==false)||(outputChains.getRoute().getTech(0).hasEagleV20()==true)) && (eagle_Version == DeviceEdit.Eagle_Version.Eagle1_4.getName()) ) || 
							(((outputChains.getRoute().getTech(0).hasEagleV20()==false)||(outputChains.getRoute().getTech(0).hasEagleV11()==true)) && (eagle_Version == DeviceEdit.Eagle_Version.Eagle2_0.getName()))) {
						System.out.println(i);
						return false;
					} else {
						check = true;
						System.out.println(i);
						i++;
					}
				}	
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return check;
	}
	
	public boolean isDBContainsData(String dbName, String sql,String col,String data){
		SQLiteDbUtil sqlite = new SQLiteDbUtil(dbName);
		ArrayList<String> ColumnData = sqlite.getColumnData(sql, col, null);
		for(int i=0;i<ColumnData.size();i++){
			if(ColumnData.get(i) != null) {
				if(ColumnData.get(i).contains(data)){
					System.out.println("Data : "+data+" is contained in "+col+" column");
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isDBTableContainsData(String dbName, String sql, String col1, String col2, String data1, String data2){
		SQLiteDbUtil sqlite = new SQLiteDbUtil(dbName);
		ArrayList<String> ColumnData = sqlite.getColumnData(sql, col1, col2);
		for(int i=0;i<ColumnData.size();i++){
			if(ColumnData.get(i) != null) {
				if(ColumnData.get(i).contains(data1 + " " + data2)){
					System.out.println("Data : " + data1 + " and " + data2 + " is contained in DB");
					return true;
				}
			}
		}
		return false; 
	}
	
	public boolean isColumnEmptyData(String dbName, String sql, String col){
		boolean check = true;
		SQLiteDbUtil sqlite = new SQLiteDbUtil(dbName);
		ArrayList<String> ColumnData = sqlite.getColumnData(sql, col, null);
		System.out.println("size: " + ColumnData.size());
		for(int i=0;i<ColumnData.size();i++){
			if(ColumnData.get(i) != null) {
				return false;
			}
		}
		return check;
	}
	
	public WebElement getPromoSlot(int num){
		List<WebElement> promos = driver.findElements(By.cssSelector(".textleft"));
		for(int i=0;i<promos.size();i++){
			System.out.println("Promotion" +num);
			System.out.println(promos.get(i).getText());
			if(promos.get(i).getText().equals("Promo "+num+":")){
				return promos.get(i);
			}
		}
		return null;
	}
	
	public void inputSpecifiedBrand(String brandName){
		appDeviceControl.editData(DeviceEdit.BRAND_FILTER,brandName);
		WebElement brand_filter = driver.findElement(By.xpath(DeviceEdit.BRAND_FILTER));
		brand_filter.sendKeys(Keys.PAGE_DOWN);
		appDeviceControl.autoTool.send("{ENTER}",false);
	}
	
	// Read file
	public ArrayList<String> getDataProfile(String file) {
        BufferedReader br = null;
        String line = "";
        ArrayList<String> arrayBrand = new ArrayList<String>();
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
            		arrayBrand.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return arrayBrand;
	}
	
	// Check number of promotion
	public int checkNumberofPromotion(String dtsId) {
		String file = System.getProperty("user.home") + "/Downloads/profile_" + dtsId + ".dtscs";
		int count = 0;
		Dtscs dtscs;
		try {
			dtscs = Dtscs.parseFrom(
					new FileInputStream(new File(file)));
			List<Accessory> listAccessory = dtscs.getProfile(0).getAccessoryCollectionList();
			for (Accessory accessory : listAccessory) { 
				String type = accessory.getCertification().getType();
				if (type == "featured") {
					count++;
				}
			}
			return count;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
        System.out.println("Done");
        return count;
	}
	
	// Check promotion
	public boolean checkPromotion(ArrayList<String> listPromo, String dtsId) {
		boolean check = true;
		String file = System.getProperty("user.home") + "/Downloads/profile_" + dtsId + ".dtscs";
		String modelName;
		String brandName;
		String promo;
		Dtscs dtscs;
		try {
			dtscs = Dtscs.parseFrom(
					new FileInputStream(new File(file)));
			List<Accessory> listAccessory = dtscs.getProfile(0).getAccessoryCollectionList();
			for (Accessory accessory : listAccessory) {
				String type = accessory.getCertification().getType();
				if (type == "featured") {
					modelName = accessory.getModelName();
					brandName = accessory.getBrandName();
					promo = brandName + " - " + modelName;
					System.out.println("promo: " + promo);
					if (!listPromo.contains(promo)) {
						return false;
					}
				}
			}
//				org.apache.commons.io.FileUtils.writeStringToFile(new File(output), dtscs.toString());
			return check;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Done");
        return check;
	}
	
	// Check route file
	public boolean checkRouteFile(String dtsId, String audioRoute) {
		boolean check = false;
		String file = System.getProperty("user.home") + "/Downloads/profile_" + dtsId + ".dtscs";
		String route;
		Dtscs dtscs;
		try {
			dtscs = Dtscs.parseFrom(
					new FileInputStream(new File(file)));
			List<Accessory> listAccessory = dtscs.getProfile(0).getAccessoryCollectionList();
			for (Accessory accessory : listAccessory) {
				route = accessory.getSpec().getClassification(0).getSupportedOutputChains(0).getRoute().getRoute().toString();
				if (route.equals(audioRoute)) {
					System.out.println(audioRoute+" is contained in file");
					return true;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(audioRoute+" is not contained in file");
        return check;
	}
	// Check Type file
	public boolean checkTypeFile(String dtsId, String audioType) {
		boolean check = false;
		String file = System.getProperty("user.home") + "/Downloads/profile_" + dtsId + ".dtscs";
		String type;
		Dtscs dtscs;
		try {
			dtscs = Dtscs.parseFrom(
					new FileInputStream(new File(file)));
			List<Accessory> listAccessory = dtscs.getProfile(0).getAccessoryCollectionList();
			for (Accessory accessory : listAccessory) {
				type = accessory.getSpec().getClassification(0).getType().toString();
				if (type.equals(audioType)) {
					System.out.println(audioType+" is contained in file");
					return true;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(audioType+" is not contained in file");
        return check;
	}

	public List<String> getDeviceIdByCharacter(String name){
		List<String> list = new ArrayList<String>();
		try {
			Thread.sleep(4000);
			int size = getPageSize(PageHome.PRODUCT_TABLE_INFO);
			addLog("Device page size: " + size);
//			clickText("Last");
//			clickText("Previous");
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if ((columns.get(2).getText().contains(name)) && (columns.get(2).getText().length() == 12)) {
						addLog("Select Device : " + columns.get(2).getText());
						String id = columns.get(2).findElement(By.tagName("a")).getAttribute("value");
						System.out.println(id);
						String href = columns.get(2).findElement(By.tagName("a")).getAttribute("href");
						String revision = href.substring(href.length()-1);
						list.add(id);
						list.add(revision);
						return list;
					}
				}
				clickText("Next");
//				clickText("Previous");
			}
			addErrorLog("Device: " + name + " not found");
		} catch (NoSuchElementException e) {
			addErrorLog("Device: " + name + " not found");
//			return 0;
		} catch (InterruptedException e) {
	
		}
		return list;
	}

	public boolean ischeckACheckbox(String xpath) {
		try {
			WebElement checkbox = driver.findElement(By.xpath(xpath));
			if (checkbox.isSelected()) {
				return true;
			} else {
				return false;
			}
		} catch (NoSuchElementException e) {
			addLog("No such element: " + xpath);
			return false;
		}
	}
	public boolean checkModelNameAndRoute(String dtsId, String modelName, String audioRoute) {
		boolean check = false;
		String file = System.getProperty("user.home") + "/Downloads/profile_" + dtsId + ".dtscs";
		String model;
		String route;
		Dtscs dtscs;
		try {
			dtscs = Dtscs.parseFrom(
					new FileInputStream(new File(file)));
			List<Accessory> listAccessory = dtscs.getProfile(0).getAccessoryCollectionList();
			for (Accessory accessory : listAccessory) {
				model = accessory.getModelName().toString();
				if (model.equals(modelName)) {
					System.out.println(modelName+" is contained in file");
					route= accessory.getSpec().getClassification(0).getSupportedOutputChains(0).getRoute().getRoute().toString();
					if(route.equals(audioRoute)){
				        System.out.println(audioRoute+" is contained in file");
						return true;
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(audioRoute+" is not contained in file");
        return check;
	}
}