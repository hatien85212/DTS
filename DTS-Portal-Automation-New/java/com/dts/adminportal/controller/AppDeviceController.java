package com.dts.adminportal.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.jar.Attributes.Name;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.dts.adminportal.model.DeviceEdit;
import com.dts.adminportal.model.DeviceInfo;
import com.dts.adminportal.model.DeviceList;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.model.PromotionInfo;
import com.dts.adminportal.model.RoomModelInfo;
import com.dts.adminportal.util.ListUtil;

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
			int size = getPageSize(PageHome.PRODUCT_TABLE_INFO);
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By.tagName("td"));
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
	
	
	public void dragAndDrop(String sourceElement, String destinationElement) {
		WebElement source = appDeviceControl.driver.findElement(By.xpath(sourceElement));
		WebElement destination = appDeviceControl.driver.findElement(By.xpath(destinationElement));
		
		try {
			if (source.isDisplayed() && destination.isDisplayed()) {
				Actions action = new Actions(driver);
				action.dragAndDrop(source, destination).build().perform();
			} else {
				System.out.println("Element was not displayed to drag");
			}
		} catch (StaleElementReferenceException e) {
			System.out.println("Element with " + sourceElement + "or" + destinationElement + "is not attached to the page document "
					+ e.getStackTrace());
		} catch (NoSuchElementException e) {
			System.out.println("Element " + sourceElement + "or" + destinationElement + " was not found in DOM "+ e.getStackTrace());
		} catch (Exception e) {
			System.out.println("Error occurred while performing drag and drop operation "+ e.getStackTrace());
		}
	}
	
	public void dragAndDrop(WebElement source, WebElement destination) {
		//WebElement source = appDeviceControl.driver.findElement(By.xpath(sourceElement));
		//WebElement destination = appDeviceControl.driver.findElement(By.xpath(destinationElement));
		
		try {
			if (source.isDisplayed() && destination.isDisplayed()) {
				Actions action = new Actions(driver);
				action.dragAndDrop(source, destination).build().perform();
			} else {
				System.out.println("Element was not displayed to drag");
			}
		} catch (StaleElementReferenceException e) {
			//System.out.println("Element with " + sourceElement + "or" + destinationElement + "is not attached to the page document "
			//		+ e.getStackTrace());
		} catch (NoSuchElementException e) {
			//System.out.println("Element " + sourceElement + "or" + destinationElement + " was not found in DOM "+ e.getStackTrace());
		} catch (Exception e) {
			System.out.println("Error occurred while performing drag and drop operation "+ e.getStackTrace());
		}
	}
	
	public ArrayList<String> getListPromo(String publish_status){
		String temp = appDeviceControl.getTextByXpath(DeviceInfo.devicePromtionsContainer(publish_status));
		ArrayList<String> list_promo  = new ArrayList<String>(Arrays.asList(temp.split(" Expires: Never")));
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
	public boolean checkListOrder(ArrayList<String> listPromo,ArrayList<String> listOrder){
		for(int i=0; i<listPromo.size(); i++){
			if(listOrder.get(i).contains(listPromo.get(i))){
				return true;
			}
		}
		return false;
	}
	
	public boolean isPromotionOrder(int sourceIndex,int destinationIndex,ArrayList<String> before,ArrayList<String> after){
		sourceIndex = sourceIndex -1;
		destinationIndex = destinationIndex -1;
		if(before.get(destinationIndex).equals(after.get(sourceIndex)) && before.get(sourceIndex).equals((after).get(destinationIndex))){
			return true;
		}
		return false;
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
	
}
